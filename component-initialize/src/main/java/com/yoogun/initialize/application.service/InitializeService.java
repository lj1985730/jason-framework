package com.yoogun.initialize.application.service;

import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.initialize.infrastructure.SqlRecover;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

/**
 * 初始化-业务层抽象类
 * @author Liu Jun at 2017-12-7 09:04:47
 * @since v1.0.0
 */
public abstract class InitializeService extends BaseAuthService<BaseEntity> {

	private Logger logger = LoggerFactory.getLogger(InitializeAuthService.class);

	/**
	 * 初始化方法
	 * @param tenantId 租户ID
	 */
	public abstract void init(String tenantId);

	/**
	 * 构建sql重写器
	 * @param tenantId 企业ID
	 * @return sql重写器
	 */
	protected SqlRecover buildRecover(String tenantId) {
		return new SqlRecover(tenantId, null, null);
	}

	/**
	 *  执行脚本文件中的脚本
	 * @param recover 重写器
	 * @param filePath 文件路径包括文件全名
	 * @param repeat 重写外键次数,默认1次。自关联表可能需要重写2次以上
	 */
	protected void runSqlInScript(SqlRecover recover, String filePath, Integer... repeat) {
		File sqlFile = FileUtils.toFile(ClassLoader.getSystemResource("dbscripts" + filePath));
		try {
			List<String> lines = FileUtils.readLines(sqlFile, "UTF-8");
			String sql;
			for(String line : lines) {
				sql = recover.recover(line, repeat);
				if(StringUtils.isBlank(sql) || sql.startsWith("--")) {
					continue;
				}
				logger.debug("run sql: " + sql);
				baseEntityDao.sqlSearch(sql);
			}
		} catch (Exception e) {
			throw new BusinessException("执行脚本出错！", e);
		}
	}
}