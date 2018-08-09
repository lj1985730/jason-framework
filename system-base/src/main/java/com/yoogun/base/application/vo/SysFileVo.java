package com.yoogun.base.application.vo;

import com.yoogun.core.application.vo.TableParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统-文件-查询VO
 * @author Liu Jun at 2017-12-19 10:43:28
 * @since v1.0.0
 */
public final class SysFileVo extends TableParam {

    /**
     * 文件夹Id
     */
    private String folderId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 文件对象
     */
    private MultipartFile file;

    /**
     * 业务数据主键
     */
    private String businessDataId;

    /**
     * 业务键
     */
    private String businessKey;

    /**
     * 关联ID
     */
    private String folderFileId;

    public SysFileVo(HttpServletRequest request) {
        super(request);
        this.folderId = request.getParameter("folderId");
        this.name = request.getParameter("name");
        this.code = request.getParameter("code");
        this.businessDataId = request.getParameter("businessDataId");
        this.businessKey = request.getParameter("businessKey");
        this.folderFileId = request.getParameter("folderFileId");
    }

    public SysFileVo(HttpServletRequest request, MultipartFile file) {
        super(request);
        this.folderId = request.getParameter("folderId");
        this.name = request.getParameter("name");
        this.code = request.getParameter("code");
        this.businessDataId = request.getParameter("businessDataId");
        this.businessKey = request.getParameter("businessKey");
        this.folderFileId = request.getParameter("folderFileId");
        this.file = file;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getName() {
        if(name != null) {
            return name;
        }

        if(file.getOriginalFilename() != null) {
            return file.getOriginalFilename();
        }

        return null;
    }

    public String getCode() {
        return code;
    }

    public String getBusinessDataId() {
        return businessDataId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public MultipartFile getFile() {
        return file;
    }

    public Long getSize() {
        return file == null ? 0L : file.getSize();
    }

    public String getFolderFileId() {
        return folderFileId;
    }
}
