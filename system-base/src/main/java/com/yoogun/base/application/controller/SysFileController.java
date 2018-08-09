package com.yoogun.base.application.controller;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.base.application.service.SysFileService;
import com.yoogun.base.application.service.SysFolderFileService;
import com.yoogun.base.application.vo.SysFileVo;
import com.yoogun.base.domain.model.SysFolderFile;
import com.yoogun.core.application.controller.BaseController;
import com.yoogun.core.application.dto.JsonResult;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.Office2PDFUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 系统-文件-控制层
 * @author Liu Jun at 2017-12-19 09:23:51
 * @since v1.0.0
 */
@Controller
@RequestMapping(value = "**/base")
class SysFileController extends BaseController {
	
	@Resource
	private SysFileService service;

	@Resource
	private SysFolderFileService folderFileService;

	/**
	 * 查询文件夹下的全部文件
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/files" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult pageSearch(HttpServletRequest request) {
		SysFileVo vo = new SysFileVo(request);
		return new JsonResult(folderFileService.pageSearch(vo));
	}

	/**
	 * 查询文件夹下的全部文件
	 * @param folderId 文件夹ID
	 * @return 分页数据
	 */
	@RequestMapping(value = { "/folder/{folderId}/files" }, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchFilesByFolder(@PathVariable String folderId) {
		return new JsonResult(folderFileService.searchFilesByFolder(folderId));
	}

	/**
	 * 文件上传
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/file" }, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult upload(HttpServletRequest request, MultipartFile file) {
		SysFileVo vo = new SysFileVo(request, file);
		service.uploadFile(vo);
		return new JsonResult(JsonResult.ResultType.CREATE_SUCCEED);
	}

	/**
	 * 文件下载
	 * @param id 逻辑文件主键（folderFileId）
	 * @return 文件流
	 */
	@RequestMapping(value = { "/file/{id}/attachment" }, method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable String id) {

		SysFolderFile file = folderFileService.searchById(id);
		if(file == null) {
			throw new BusinessException("未找到文件信息数据！");
		}

		byte[] fileContent = service.downloadFile(id);

		return super.createFileAttachmentResponse(fileContent, file.getFileName());
	}

	/**
	 * 文件预览
	 * @param id 逻辑文件主键（folderFileId）
	 * @author Wang Chong
	 */
	@RequestMapping(value = { "/file/{id}/inline" }, method = RequestMethod.GET)
	public void preview(@PathVariable String id, HttpServletResponse response) {
		SysFolderFile file = folderFileService.searchById(id);
		byte[] fileContent = service.downloadFile(id);
		if(file == null) {
			throw new BusinessException("未找到文件信息数据！");
		}
		String extension = StringUtils.substringAfterLast(file.getFileName(),".");
		InputStream inputStream = new ByteArrayInputStream(fileContent);
		if ("pdf".equals(extension) || "jpeg".equals(extension) || "jpg".equals(extension)){
			Office2PDFUtil.previewPdfOrJpg(inputStream,response,extension);
		} else {
			try {
				OutputStream outputStream = response.getOutputStream();
				Office2PDFUtil.fileConvertPdf(inputStream,outputStream,extension,"118.190.76.87",8100);
			} catch (Exception e){
				throw new BusinessException("文档预览异常",e);
			}
		}
	}

	/**
	 * 删除文件
	 * @return 操作结果
	 */
	@RequestMapping(value = { "/file/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonResult remove(@PathVariable("id") String id) {
		folderFileService.remove(id, AuthCache.accountId());
		return new JsonResult(JsonResult.ResultType.REMOVE_SUCCEED);
	}

	/**
	 * 根据业务主键查询逻辑文件主键（folderFileId）
	 * @param businessKey 业务类型KEY
	 * @param businessId 业务主键
	 * @return 逻辑文件主键（folderFileId）
	 */
	@RequestMapping(value = { "/fileIds"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult searchFileIdsByBusiness(@RequestParam String businessKey, @RequestParam String businessId) {
		return new JsonResult(folderFileService.searchFileIdsByBusiness(businessId, businessKey));
	}
}