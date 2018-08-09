package com.yoogun.auth.infrastructure.exception;

import com.yoogun.core.application.dto.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 *  统一权限异常处理器
 *  @author Liu Jun
 *  @since v1.0.0
 */
@ControllerAdvice
public class AuthExceptionHandler {

    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

    /**
     * 实现统一异常处理，身份认证异常
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Object authenticationExceptionHandler(AuthenticationException exception, HttpServletRequest request) {
        logger.error("AuthExceptionHandler authenticationExceptionHandler:", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return new JsonResult(false, "身份认证异常：" + exception.getMessage());
        } else {	//否则重定向到错误页面
            return new ModelAndView("/401", "message", exception.getMessage());
        }
    }

    /**
     * 实现统一异常处理，权限异常
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public Object unauthorizedExceptionHandler(UnauthorizedException exception, HttpServletRequest request) {
        logger.error("AuthExceptionHandler unauthorizedExceptionHandler:", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return new JsonResult(false, "权限异常：当前账户没有此操作权限！");
        } else {	//否则重定向到错误页面
            return new ModelAndView("/401", "message", "权限异常：当前账户没有此操作权限！");
        }
    }

}
