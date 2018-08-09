package com.yoogun.core.infrastructure.exception;

import com.yoogun.core.application.dto.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Set;

/**
 *  统一异常处理器
 *  @author Liu Jun
 *  @since v1.0.0
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /**
     * 实现统一异常处理，数值格式化异常
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = NumberFormatException.class)
    @ResponseBody
    public Object numberFormatExceptionHandler(NumberFormatException exception, HttpServletRequest request) {
        logger.error("ExceptionHandler numberFormatExceptionHandler:", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return new JsonResult(false, "数据处理出现错误：无效的数值格式！");
        } else {	//否则重定向到错误页面
            return new ModelAndView("/500", "message", exception.getMessage());
        }
    }

    /**
     * 实现统一异常处理，SQL异常
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public Object sqlExceptionHandler(SQLException exception, HttpServletRequest request) {
        logger.error("ExceptionHandler sqlExceptionHandler:", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return new JsonResult(false, "数据库访问错误！");
        } else {	//否则重定向到错误页面
            return new ModelAndView("/500", "message", exception.getMessage());
        }
    }

    /**
     * 实现统一异常处理，NullPointer异常
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Object nullPointerExceptionHandler(NullPointerException exception, HttpServletRequest request) {
        logger.error("ExceptionHandler nullPointerExceptionHandler:", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return new JsonResult(false, "数据处理出现错误：空指针！");
        } else {	//否则重定向到错误页面
            return new ModelAndView("/500", "message", exception.getMessage());
        }
    }

    /**
     * 实现统一异常处理，数据约束冲突异常,约束由entity中注解确定
     * @param exception 捕获异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object constraintViolationExceptionHandler(ConstraintViolationException exception, HttpServletRequest request) {
        logger.error("ExceptionHandler constraintViolationExceptionHandler 捕获系统异常：", exception);
        System.out.println(exception.getMessage());
        Set<ConstraintViolation<?>> messages = exception.getConstraintViolations();
        final StringBuilder message = new StringBuilder("编辑数据异常：");
        if(messages != null) {
            messages.forEach(constraintViolation -> message.append(constraintViolation.getMessage()).append("，"));
        }
        String messageStr = StringUtils.removeEnd(message.toString(), "，");

        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", messageStr);
            return new JsonResult(false, messageStr);
        } else {	//否则重定向到错误页面
            return new ModelAndView("/500", "message", exception.getMessage());
        }
    }

    /**
     * 实现统一异常处理，系统自定义异常
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Object businessExceptionHandler(BusinessException exception, HttpServletRequest request) {
        logger.error("ExceptionHandler BusinessException Handler:", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return exception.getResult();
        } else {	//否则重定向到错误页面
            return new ModelAndView("/500", "message", exception.getMessage());
        }
    }

    /**
     * 实现集中异常处理
     * @param exception 捕获到的异常
     * @param request 请求体
     * @return 根据不同请求类型进行错误反馈或错误页跳转
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception exception, HttpServletRequest request) {
        logger.error("ExceptionHandler exceptionHandler 捕获系统异常：", exception);
        String contentType = request.getContentType();
        if (!StringUtils.isBlank(contentType) && contentType.contains("application/json")) {	//json类型的访问，返回JsonResult提示
            request.setAttribute("message", exception.getMessage());
            return new JsonResult(false, exception.getMessage());
        } else {	//否则重定向到错误页面
            return new ModelAndView("/500", "message", exception.getMessage());
        }
    }
}
