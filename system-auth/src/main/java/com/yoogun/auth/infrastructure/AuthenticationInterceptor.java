package com.yoogun.auth.infrastructure;

import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.web.infrastructure.HttpErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 身份拦截器，用于实现身份，权限的校验。
 * @author Liu Jun
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return pcPreHandle(request, response);
	}

	/**
	 * PC客户端处理
	 * @return 是否通过
	 */
	private boolean pcPreHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return true;
	}


	/**
	 * 错误响应
	 * @param errorCode	错误代码枚举
	 * @param request	请求
	 * @param response	响应
	 */
	private void respondError(HttpErrorCode errorCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contentType = request.getContentType();
		if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) { // 如果是json，则返回json类型的数据。
			response.setContentType("application/json;charset=UTF-8");
			JsonResult jsonResult = new JsonResult(false, errorCode.getMessage());
			jsonResult.put("error_code", errorCode);
			response.getWriter().write(jsonResult.toJson());
			response.getWriter().flush();
			logger.error(errorCode.getMessage());
		} else { // 如果是页面，则直接跳转。
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}