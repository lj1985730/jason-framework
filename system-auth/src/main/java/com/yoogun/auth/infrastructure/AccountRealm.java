package com.yoogun.auth.infrastructure;

import com.yoogun.auth.application.service.AccountService;
import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.application.service.PersonService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.domain.model.Person;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

@Transactional
public class AccountRealm extends AuthorizingRealm {

    @Resource
    private AccountService accountService;

    @Resource
    private PersonService personService;

    @Resource
    private PermissionService permissionService;

    /**
     * 认证
     * @param authenticationToken 请求身份token
     * @return 认证信息
     */
    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String loginName = usernamePasswordToken.getUsername();
        Account account = accountService.searchByName(loginName);
        if(account == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if(account.getDeleted()) {
            throw new UnknownAccountException("账户已删除！");
        }
        if(account.getLocked()) {
            throw new LockedAccountException("账户被锁定！");
        }
        if(!account.getEnabled()) {
            throw new DisabledAccountException("账户尚未激活！");
        }
        if(!account.getIsAdmin()) {	//非管理员需要认证人员信息
            validPerson(account);
        }
        return new SimpleAuthenticationInfo(loginName, account.getPassword(), getName());
    }

    /**
     * 授权
     * @param principals 身份信息
     * @return 权限信息
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取账户
        String username = (String)principals.getPrimaryPrincipal();
        Account account = accountService.searchByName(username);

        //获取账户权限
        Set<String> permissions = permissionService.searchAllPermissions(account);

        //构建权限信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 验证人员信息
     * @param account 待验证账户
     */
    private void validPerson(Account account) {
        Person person = account.getPerson();

        if(person == null) {
            person = personService.searchById(account.getPersonId());
        }

        if(person.getDeleted() == null || person.getDeleted()) {
            throw new AuthenticationException("登录失败，该人员已删除！");
        }
        if(person.getState() == null || person.getState().equals("2")) {
            throw new AuthenticationException("登录失败，该人员已离职！");
        }
    }
}
