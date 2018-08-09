package com.yoogun.base.application.service;

import com.yoogun.auth.infrastructure.AuthCache;
import com.yoogun.auth.infrastructure.BaseAuthService;
import com.yoogun.base.application.vo.SysFileVo;
import com.yoogun.base.domain.model.SysFile;
import com.yoogun.base.domain.model.SysFolderFile;
import com.yoogun.core.domain.model.BaseEntity;
import com.yoogun.core.infrastructure.BooleanTypeHandler;
import com.yoogun.core.infrastructure.exception.BusinessException;
import com.yoogun.utils.infrastructure.FastdfsUtils;
import com.yoogun.utils.infrastructure.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.csource.common.NameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 系统-文件-应用业务
 * @author Liu Jun at 2017-11-8 15:54:02
 * @since V1.0.0
 */
@Service
public class SysFileService extends BaseAuthService<SysFile> {

    @Resource
    private SysFolderService folderService;

    @Resource
    private SysFolderFileService folderFileService;

    /**
     * 上传文件
     * @param vo 文件相关新消息值对象
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void uploadFile(SysFileVo vo) {
        MultipartFile file = vo.getFile();
        NameValuePair[] metaDatas = generateMetaDatas(file);
        String[] storageInfos;
        try {
            storageInfos = FastdfsUtils.upload(file.getBytes(), metaDatas[1].getValue(), metaDatas);
        } catch (IOException e) {
            throw new BusinessException("文件读取出错！", e);
        }

        if(storageInfos == null) {
            throw new BusinessException("文件上传出错！");
        }

        this.saveFileData(vo, storageInfos); //保存文件数据

    }

    /**
     * 下载文件
     * @param folderFileId 文件ID
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public byte[] downloadFile(String folderFileId) {

        if(StringUtils.isBlank(folderFileId)) {
            return null;
        }

        String[] storageInfos = this.searchStorageInfos(folderFileId);

        if(storageInfos == null) {
            return null;
        }

        return FastdfsUtils.download(storageInfos[0], storageInfos[1]);

    }

    /**
     * 生成元数据
     * @param file 文件
     * @return 元数据
     */
    private NameValuePair[] generateMetaDatas(MultipartFile file) {

        NameValuePair[] metaDatas = new NameValuePair[4];
        metaDatas[0] =  new NameValuePair("fileName", FilenameUtils.getBaseName(file.getOriginalFilename()));
        metaDatas[1] =  new NameValuePair("fileExt", FilenameUtils.getExtension(file.getOriginalFilename()));
        metaDatas[2] =  new NameValuePair("fileLength", String.valueOf(file.getSize()));
        if(StringUtils.isNotBlank(AuthCache.accountId())) {
            metaDatas[3] =  new NameValuePair("fileAuthor", AuthCache.accountId());
        } else {
            metaDatas[3] =  new NameValuePair("fileAuthor", "");
        }

        return metaDatas;
    }

    /**
     * 保存文件数据
     *  首先校验MD5 判断是否存在相同文件
     *  如不存在文件，则新增文件信息数据
     *  之后新增文件夹与文件关系业务数据，文件夹可为空
     * @param vo 文件信息值对象
     * @param storageInfos 文件储存地址
     */
    private void saveFileData(SysFileVo vo, String[] storageInfos) {
        String md5 = FileUtils.md5(vo.getFile());
        SysFile fileData = this.searchByMd5(md5);   //根据MD5查询现有数据
        if(fileData == null) {  //相同MD5数据不存在，需要新增文件数据
            fileData = new SysFile();
            fileData.setId(UUID.randomUUID().toString().toUpperCase());
            fileData.setName(StringUtils.substringAfterLast(storageInfos[1], File.pathSeparator));  //文件的存储名称
            fileData.setMd5(md5);
            fileData.setExtension(FilenameUtils.getExtension(vo.getFile().getOriginalFilename()));
            fileData.setSize(vo.getSize());
            fileData.setRootAddress(storageInfos[0]);
            fileData.setRelativeAddress(storageInfos[1]);
            this.create(fileData);
        }

        // 新增文件夹与文件关系业务数据，文件夹可为空
        SysFolderFile folderFile = new SysFolderFile();
        folderFile.setFileId(fileData.getId());
        folderFile.setFileName(vo.getName());   //指定的文件名称
        folderFile.setBusinessDataId(vo.getBusinessDataId());
        folderFile.setBusinessKey(vo.getBusinessKey());
        folderFile.setFolderId(vo.getFolderId());
        folderFileService.create(folderFile);
    }

    /**
     * 根据MD5获取文件信息数据
     * @param md5 文件MD5
     * @return 文件信息数据
     */
    private SysFile searchByMd5(String md5) {
        if(StringUtils.isBlank(md5)) {
            return null;
        }
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        sql.WHERE("TENANT_ID = '" + AuthCache.tenantId() + "'");
        sql.WHERE(BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("MD5 = '" + md5 + "'");
        List<SysFile> files = this.search(sql);

        if(files == null || files.isEmpty()) {
            return null;
        } else {
            return files.get(0);
        }
    }

    /**
     * 获取文件储存信息数据
     * @param folderFileId 文件夹与文件关系ID
     * @return 文件信息数据
     */
    private String[] searchStorageInfos(String folderFileId) {
        if(StringUtils.isBlank(folderFileId)) {
            return null;
        }
        SQL sql = new SQL().SELECT("A.*").FROM("SYS_FILE A, SYS_FOLDER_FILE B");
        sql.WHERE("A.ID = B.FILE_ID");
        sql.WHERE("A.TENANT_ID = '" + AuthCache.tenantId() + "'");
        sql.WHERE("A." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("B.TENANT_ID = '" + AuthCache.tenantId() + "'");
        sql.WHERE("B." + BaseEntity.DELETE_PARAM + " = '" + BooleanTypeHandler.BOOL_FALSE + "'");
        sql.WHERE("B.ID = '" + folderFileId + "'");
        List<SysFile> files = this.search(sql);

        if(files == null || files.isEmpty()) {
            return null;
        } else {
            return new String[] { files.get(0).getRootAddress(), files.get(0).getRelativeAddress() };
        }
    }
}
