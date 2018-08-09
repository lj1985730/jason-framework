/*
 * SubjectService.java
 * Copyright(C) 2018 大连用友软件有限公司
 * All right reserved.
 */
package com.yoogun.cms.admin.application.service;

import com.yoogun.auth.application.service.PermissionService;
import com.yoogun.auth.domain.model.Account;
import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.auth.infrastructure.BaseAuthTreeService;
import com.yoogun.chart.application.dto.EchartDto;
import com.yoogun.chart.infrastructure.echarts.AxisType;
import com.yoogun.core.application.dto.Page;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.EntityUtils;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.cms.admin.application.vo.SubjectVo;
import com.yoogun.cms.admin.domain.model.Subject;
import com.yoogun.utils.infrastructure.ExportCacheUtils;
import com.yoogun.utils.infrastructure.excel.ExcelExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * CMS-主题（栏目）-应用业务<br/>
 * 主要业务逻辑代码
 * @author Liu Jun at 2018-6-5 14:12:01
 * @since v1.0.0
 */
@Service
public class SubjectService extends BaseAuthTreeService<Subject> {

}