package com.yoogun.auth.infrastructure;

import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.application.service.BaseService;
import com.yoogun.core.domain.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器的CURD抽象父类<br>
 *  封装了增删改通用方法。
 * @author Liu Jun at 2018-2-1 09:23:02
 * @since v1.0.0
 */
public abstract class BaseCurdController<E extends BaseEntity> extends BaseController {

	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(BaseCurdController.class);

	@Autowired    //此处需要使用Autowired来保证按照类型选择Service
	protected BaseService<E> baseService;

	/**
	 * 通用新增操作
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult create(@RequestBody E entity) {
		baseService.create(entity);
		return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);
	}

	/**
	 * 通用更新操作
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.PUT)
	@ResponseBody
	public JsonResult modify(@RequestBody E entity) {
		baseService.modify(entity);
		return new JsonResult(JsonResult.ResultType.MODIFY_SUCCEED);
	}

	/**
	 * 通用删除操作
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult remove(@PathVariable("id") String id) {
		baseService.remove(id, AuthCache.accountId());
		return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
	}

}