package com.yoogun.base.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.base.domain.model.SysConfig;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.application.service.BaseService;
import com.yoogun.core.application.vo.TableParam;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 系统-参数-应用业务
 * @author Liu Jun
 * @since 2016-8-2 22:43:06
 */
@Service
@Lazy(false)
public class SysConfigService extends BaseService<SysConfig> implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);

	@Value("#{authInitializeProperties['SUPER_TENANT_ID_KEY']}")
	public String SUPER_TENANT_ID_KEY;	//用友企业ID

	@Value("#{authInitializeProperties['SUPER_ADMIN_ID_KEY']}")
	public String SUPER_ADMIN_ID_KEY;	//超管账户ID

	@Value("#{authInitializeProperties['DEFAULT_PASSWORD_KEY']}")
	public String DEFAULT_PASSWORD_KEY;	//默认密码

	@Value("#{authInitializeProperties['DEFAULT_SALT_KEY']}")
	public String DEFAULT_SALT_KEY;	//默认盐值

	/**
	 * 管理企业ID
	 */
	public static String superTenantId;

	/**
	 * 超管ID
	 */
	public static String superAdminId;

	/**
	 * 默认密码
	 */
	public static String defaultPassword;

	/**
	 * 默认公盐
	 */
	public static String defaultSalt;

	/**
	 * 定时任务线程池
	 */
	private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	/**
	 * 系统配置集合
	 */
	static Map<String, Map<String, String>> configMap = new HashMap<>();

	/**
	 * Table分页查询
	 * @param tableParam 查询参数
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page<SysConfig> pageSearch(TableParam tableParam) {

		SQL sql = new SQL().SELECT(EntityUtils.selectSql(SysConfig.class)).FROM(tableName);

		sql.WHERE("EDITABLE = '" + BooleanTypeHandler.BOOL_TRUE + "'");

		//企业过滤
		if(!Objects.equals(AuthCache.tenantId(), PermissionService.superTenantId)) {
			sql.WHERE("TENANT_ID IN ('" + AuthCache.tenantId() + "', '" + PermissionService.superTenantId + "')");
		}

		if(StringUtils.isNotBlank(tableParam.getSearch())) {
			sql.WHERE("CFG_KEY LIKE '%" + BooleanTypeHandler.BOOL_FALSE + "%'");
		}

		return this.pageSearch(sql, tableParam.getOffset(), tableParam.getLimit());
	}

	/**
	 * 获取系统配置
	 * @param key   配置键
	 * @param defaultValue  默认值
	 * @return 系统配置
	 */
	public static String getConfigValue(String key, String defaultValue) {
		Map<String, String> superCfg = configMap.get(superTenantId);	//超管企业的配置，通用配置
		String val = null;
		if(superCfg != null) {
			val = superCfg.get(key);	//优先检索通用配置
		}
		if (StringUtils.isBlank(val)) {
			Map<String, String> selfCfg = configMap.get(AuthCache.tenantId());
			if(selfCfg != null) {
				val = selfCfg.get(key);	//无通用配置检索自己企业配置
			}
			if(StringUtils.isBlank(val)) {
				return defaultValue;
			}
			return val;
		} else {
			return val;
		}
	}

	/**
	 * 加载系统参数
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void loadSystemConfig() {
		List<SysConfig> configList = this.searchByProp("enabled", true);
		configMap.clear();
		String tenantId;
		Map<String, String> tenantMap;
		for (SysConfig conf : configList) {
			tenantId = conf.getTenantId();

			// 初始化核心的系统参数
			if(conf.getCfgKey().equals(SUPER_TENANT_ID_KEY)) {
				superTenantId = conf.getCfgValue();	//管理企业ID
			} else if(conf.getCfgKey().equals(SUPER_ADMIN_ID_KEY)) {
				superAdminId = conf.getCfgValue();	//超管ID
			} else if(conf.getCfgKey().equals(DEFAULT_PASSWORD_KEY)) {
				defaultPassword = conf.getCfgValue();	//默认密码
			} else if(conf.getCfgKey().equals(DEFAULT_SALT_KEY)) {
				defaultSalt = conf.getCfgValue();	//默认公盐
			}

			try {
				if(!configMap.containsKey(tenantId)) {
					tenantMap = new HashMap<>();
				} else {
					tenantMap = configMap.get(tenantId);
				}
				tenantMap.put(conf.getCfgKey(), conf.getCfgValue());
				logger.info("加载系统参数：" + tenantId + " - " + conf.getCfgKey() + ":" + conf.getCfgValue());
				configMap.put(tenantId, tenantMap);
			} catch (Exception ex) {
				throw new BusinessException("加载系统参数出错！");
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//每十分钟刷新一次系统配置.
		executorService.scheduleWithFixedDelay(this::loadSystemConfig, 0, 600, TimeUnit.SECONDS);
	}

	/**
	 * 关闭配置自动刷新服务.
	 */
	@PreDestroy
	public static void shutdown() {
		executorService.shutdown();
	}

	@Override
	public void beforeCreate(SysConfig entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());

		entity.setEnabled(true);
		entity.setEditable(true);
	}

	@Override
	public void beforeModify(SysConfig entity) {
		Account account = AuthCache.account();
		if(account == null) {
			throw new AuthenticationException("用户未登录");
		}
		entity.setLastModifyAccountId(account.getId());
		entity.setTenantId(AuthCache.tenantId());

		if(!entity.getEditable()) {
			throw new BusinessException("该项目禁止编辑！");
		}
	}

	@Override
	public void beforeRemove(SysConfig entity) {
		this.beforeModify(entity);
	}

	@Override
	protected void beforeRemove(String id) {
		SysConfig entity = this.searchById(id);
		if(!entity.getEditable()) {
			throw new BusinessException("该项目禁止编辑！");
		}
	}
}