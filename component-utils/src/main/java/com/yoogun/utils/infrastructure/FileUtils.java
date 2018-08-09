package com.yoogun.utils.infrastructure;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yoogun.core.infrastructure.exception.BusinessException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理-帮助类，封装大部分工具性通用方法
 * @author liu jun
 * @since 2014-12-4 16:06:35
 */
public class FileUtils {
	
	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/*
	 * 上传临时文件保存路径：java.io.tmpdir
	 */
	public static final String UPLOAD_TEMP_DIR = System.getProperty("java.io.tmpdir");
	
	/**
	 * 系统定义文件路径分隔符：file.separator
	 */
	public static final String FILE_SPARATOR = File.separator;
	
	/**
	 * 根据原始文件名，获取临时文件名
	 * @param oriFileName 原文件名(.扩展名)
	 * @return 临时文件名(.扩展名)=原文件名_时间戳(.扩展名)
	 */
	public static String genTempFileName(String oriFileName) {
		String fileType = FilenameUtils.getExtension(oriFileName);
		if (StringUtils.isBlank(fileType)) {
			//临时文件存放路径,临时文件名=原文件名_时间戳
			return oriFileName + "_" + System.currentTimeMillis();
		} else {
			//临时文件存放路径,临时文件名.扩展名=原文件名_时间戳.扩展名
			return FilenameUtils.getBaseName(oriFileName) + "_" + System.currentTimeMillis() + "." + fileType;
		}
	}

	/**
	 * 根据临时文件名，获取原始文件名称
	 * @param tempFileName 临时文件名(.扩展名)=原文件名_时间戳(.扩展名)
	 * @return 原文件名(.扩展名)
	 */
	public static String getOriFileName(String tempFileName) {
		String tempFileType = FilenameUtils.getExtension(tempFileName);
		// 获取原始文件名(去掉临时文件名中时间戳部分)
		return StringUtils.substringBeforeLast(FilenameUtils.getBaseName(tempFileName), "_") + (!StringUtils.isBlank(tempFileType) ? "." + tempFileType : "");
	}
	
	
	/**
	 * 文件下载帮助方法
	 * @param response		返回应答
	 * @param fileStream	服务端文件流
	 * @param fileName		下载文件名
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, InputStream fileStream, String fileName) {
		String userAgent = request.getHeader("USER-AGENT");
		//入参文件名为空，设默认下载文件名："FILE" + 时间戳
		if(fileName == null) fileName = "FILE" + new Date().toString();
		BufferedInputStream br = null;
		byte[] buf = new byte[2048];
		int len;
		OutputStream out = null;
		try {
			//重置返回应答，设置应答header体
			response.reset();
			response.setContentType("charset=UTF-8");
			response.setContentType("application/octet-stream"); 
			response.setHeader("Pragma","No-cache"); 
			response.setHeader("Cache-Control","no-cache"); 
			response.setDateHeader("Expires", 0);
			br = new BufferedInputStream(fileStream);	//文件流
			
			String finalFileName;
		    if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
            }
			//attachment;执行下载
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + finalFileName + "\"");
			out = response.getOutputStream();
			while((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		} catch (UnsupportedEncodingException uee) {
			throw new BusinessException("帮助类文件下载,转换编码出错！", uee);
		} catch (Exception e) {
			throw new BusinessException("帮助类文件下载IO出错！", e);
		} finally {
			if(br != null) {
				IOUtils.closeQuietly(br);
			}
			if(out != null) {
				IOUtils.closeQuietly(out);
			}
		}
	}	

	/**
	 * 移动文件功能(包括移动文件夹到某个制定的目录中)
	 * 
	 * @param from 被移动的目录或文件物理路径,
	 * @param to 移动文件目标地址
	 */
	public static void fileMove(String from, String to) throws IOException {// 移动指定文件夹内的全部文件
	
		File moveDir = new File(to);// 创建目标目录
		if (!moveDir.exists()) {// 判断目标目录是否存在
			moveDir.mkdirs();// 不存在则创建
		}
		File dir = new File(from);
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();// 将文件或文件夹放入文件集
			if (files == null)// 判断文件集是否为空
				return;
			for (File file : files) {// 遍历文件集
				if (file.isDirectory()) {// 如果是文件夹或目录,则递归调用fileMove方法，直到获得目录下的文件
					fileMove(file.getPath(), to + File.separator + file.getName());// 递归移动文件
					file.delete();// 删除文件所在原目录
				}
				File moveFile = new File(moveDir.getPath() + File.separator// 将文件目录放入移动后的目录
						+ file.getName());
				if (moveFile.exists()) {// 目标文件夹下存在的话，删除
					moveFile.delete();
				}
				try {
					org.apache.commons.io.FileUtils.moveFile(file, moveFile);
				} catch (IOException ioe) {
					logger.error("文件移动出错！", ioe);
					throw ioe;
				}
			}
		}
		else {
			File moveFile = new File(moveDir.getPath() + File.separator// 将文件目录放入移动后的目录
					+ dir.getName());
			try {
				org.apache.commons.io.FileUtils.moveFile(dir, moveFile);
			} catch (IOException ioe) {
				logger.error("文件移动出错！", ioe);
				throw ioe;
			}
		}
	}
	
	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录(目录不存在则系统会自动创建目录)
	 * 
	 * @param zipFile 指定的ZIP压缩文件
	 * @param path 解压目录
	 * @param password ZIP文件的密码
	 * @return 解压后文件数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败  不合法的路径  抛出
	 */
	public static File [] unzip(String zipFile, String path, String password) throws ZipException {
		File file = new File(zipFile);
		return unzip(file, path, password);
	}
	
	/**
	 * 使用给定密码解压指定的ZIP压缩文件到当前目录
	 * 
	 * @param zipFile 指定的ZIP压缩文件
	 * @param password ZIP文件的密码
	 * @return  解压后文件数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 */
	public static File [] unzip(String zipFile, String password) throws ZipException {
		File file = new File(zipFile);
		File parentDir = file.getParentFile();
		return unzip(file, parentDir.getAbsolutePath(), password);
	}
	
	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * <p>
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 * @param zipFile 指定的ZIP压缩文件
	 * @param path 解压目录
	 * @param password ZIP文件的密码
	 * @return  解压后文件数组
	 * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
	 */
	@SuppressWarnings("unchecked")
	public static File [] unzip(File zipFile, String path, String password) throws ZipException {
		ZipFile zFile = new ZipFile(zipFile);
//		zFile.setFileNameCharset("GBK");
		if (!zFile.isValidZipFile()) {
			logger.error("压缩文件不合法,可能被损坏.", new ZipException("压缩文件不合法,可能被损坏."));
			throw new ZipException("压缩文件不合法,可能被损坏.");
		}
		File pathDir = new File(path);
		if (pathDir.isDirectory() && !pathDir.exists()) {
			pathDir.mkdir();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(password.toCharArray());
		}
		zFile.extractAll(path);
		
		List<FileHeader> headerList = zFile.getFileHeaders();
		List<File> extractedFileList = new ArrayList<>();
		for(FileHeader fileHeader : headerList) {
			if (!fileHeader.isDirectory()) {
				extractedFileList.add(new File(pathDir,fileHeader.getFileName()));
			}
		}
		File [] extractedFiles = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}
	
	/**
	 * 压缩指定文件到当前文件夹(压缩文件与当前文件同级目录)
	 * @param fileSrc 要压缩的指定文件
	 * @param rename 压缩制定别名 
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String fileSrc,String rename) {
		return zip(fileSrc,rename,null);
	}
	
	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 * @param fileSrc 要压缩的文件
	 * @param password 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String fileSrc, String rename, String password) {
		return zip(fileSrc, rename, null, password);
	}
	
	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 * @param fileSrc 要压缩的文件
	 * @param path 压缩文件存放路径
	 * @param password 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String fileSrc, String rename,String path, String password) {
		return zip(fileSrc, rename, path, true, password);
	}
	
	/**
	 * 使用给定密码压缩指定文件或文件夹到指定位置.
	 * <p>
	 * path可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />
	 * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />
	 * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,<br />
	 * 如果不是以路径分隔符(File.separator)结尾,则会补充路径分隔符作为目录.
	 * @param fileSrc 要压缩的文件或文件夹路径
	 * @param path 压缩文件存放路径
	 * @param isCreateDir true:压缩文件里创建目录,并可以压缩子文件夹中的所有文件;false:不创建目录,并且只压缩当前目录信息,目录中的子目录压缩信息为空.,仅在压缩文件为目录时有效.<br />
	 * 如果为false,将直接压缩目录下文件到压缩文件.
	 * @param password 压缩使用的密码
	 * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
	 */
	public static String zip(String fileSrc, String rename, String path, boolean isCreateDir, String password) {
		File srcFile = new File(fileSrc);
		path = buildDestinationZipFilePath(srcFile, rename, path);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);			// 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);	// 压缩级别
		if (!StringUtils.isEmpty(password)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);	// 加密方式
			parameters.setPassword(password.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(path);
			if (srcFile.isDirectory()) {
				// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
				if (!isCreateDir) {
					File [] subFiles = srcFile.listFiles();
					if(subFiles == null) {
						throw new BusinessException("未找到文件");
					}
					ArrayList<File> temp = new ArrayList<>();
					Collections.addAll(temp, subFiles);
					zipFile.addFiles(temp, parameters);
					return path;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return path;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 构建压缩文件存放路径,如果不存在将会创建
	 * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
	 * @param srcFile 源文件
	 * @param pathParam 压缩目标路径
	 * @return 正确的压缩文件存放路径
	 */
	private static String buildDestinationZipFilePath(File srcFile, String rename, String pathParam) {
		if(rename == null || "".equals(rename))	{	//重命名设置为空或""时
			if (srcFile.isDirectory()) {	//若srcFile为目录,则去目录名作为压缩文件名
				rename = srcFile.getName();
			}
			else {	//若srcFile为文件名,则取文件名作为压缩文件名
				rename = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
			}
		}
		if (StringUtils.isEmpty(pathParam)) {	//若压缩存放路径为空,则默认压缩当前文件目录下
			pathParam = srcFile.getParent() + File.separator + rename + ".zip";
		} else {
			createDestDirectoryIfNecessary(pathParam);	// 在指定路径不存在的情况下将其创建出来
			if (!pathParam.endsWith(File.separator)){
				pathParam += File.separator;
			}
			pathParam += rename + ".zip";
		}
		return pathParam;
	}
	
	/**
	 * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
	 * @param pathParam 指定的存放路径,有可能该路径并没有被创建
	 */
	private static void createDestDirectoryIfNecessary(String pathParam) {
		File destDir;
		if (pathParam.endsWith(File.separator)) {
			destDir = new File(pathParam);
		} else {
			//destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
			destDir = new File(pathParam + File.separator);
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}

	/**
	 *  计算MD5
	 * @param file 文件
	 * @return MD5
	 */
	public static String md5(File file) {
		try {
			return DigestUtils.md5Hex(new FileInputStream(file));
		} catch (IOException e) {
			throw new BusinessException("计算文件MD5出错！", e);
		}
	}

	/**
	 *  计算MD5
	 * @param file 文件
	 * @return MD5
	 */
	public static String md5(MultipartFile file) {
		try {
			return DigestUtils.md5Hex(file.getBytes());
		} catch (IOException e) {
			throw new BusinessException("计算文件MD5出错！", e);
		}
	}

}