package com.yoogun.auth.infrastructure;

import com.yoogun.core.infrastructure.SpringContextUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class RealmLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * spring初始化结束后操作
     * @param contextRefreshedEvent 上下文重置事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            final AccountRealm accountRealm = SpringContextUtils.getBean("accountRealm");
            final DefaultWebSecurityManager securityManager = SpringContextUtils.getBean("securityManager");
            securityManager.setRealm(accountRealm);
        } catch (Exception e) {
            System.out.println("Error loading: " + e.getMessage());
            throw new Error("Critical system error", e);
        }
    }
}
