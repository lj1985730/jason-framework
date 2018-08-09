package com.yoogun.utils.infrastructure;

import com.yoogun.core.infrastructure.exception.BusinessException;
import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.InputStream;
import java.util.Properties;

/**
 * fastDFS 操作帮助类
 */
public class FastdfsUtils {

    private static StorageClient storageClient;

    static {
        InputStream in = null;
        Properties properties;
        try {
            in = FastdfsUtils.class.getClassLoader().getResourceAsStream("fastdfs-client.properties");
            if(in == null) {
                throw new BusinessException("未找到配置文件");
            }

            properties = new Properties();
            properties.load(in);

            ClientGlobal.initByProperties(properties); //初始化配置

            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
           throw new BusinessException("读取fastdfs配置出错！", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     *  文件上传
     * @param fileContent 文件内容
     * @param fileExtName 文件扩展名
     * @param valuePairs 元数据
     * @return 文件存储信息 [groupName, relativePath]
     */
    public static String[] upload(byte[] fileContent, String fileExtName, NameValuePair[] valuePairs) {
        try {
            return storageClient.upload_file(fileContent, fileExtName, valuePairs);
        } catch (Exception e) {
            throw new BusinessException("上传文件出错！", e);
        }
    }

    /**
     * 文件下载
     * @param groupName 存储组名
     * @param fileName 存储文件名
     * @return 文件内容
     */
    public static byte[] download(String groupName, String fileName) {
        try {
            return storageClient.download_file(groupName, fileName);
        } catch (Exception e) {
            throw new BusinessException("上传文件出错！", e);
        }
    }

    /**
     * 删除文件
     * @param groupName 存储组名
     * @param fileName 存储文件名
     * @return 删除操作结果
     */
    public static int delete(String groupName, String fileName) {
        try {
            return storageClient.delete_file(groupName, groupName);
        } catch (Exception e) {
            throw new BusinessException("上传文件出错！", e);
        }
    }

    /**
     * 获取客户端
     * @return 客户端
     */
    public static StorageClient getClient() {
        return storageClient;
    }
}
