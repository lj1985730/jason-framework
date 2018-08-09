package com.yoogun.utils.infrastructure;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.yoogun.core.infrastructure.exception.BusinessException;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;

/**
 * openOffice 帮助类
 * @author Wang Chong at 2018-04-04 11:03
 * @since V1.0.0
 */
public class Office2PDFUtil {

    private static Office2PDFUtil Office2PDFUtil;

    /**
     * 获取Office2PDFUtil实例
     */
    public static synchronized Office2PDFUtil getOffice2PDFInstance(){
        if (Office2PDFUtil == null){
            Office2PDFUtil = new Office2PDFUtil();
        }
        return Office2PDFUtil;
    }

    /**
     * 执行前，请启动openOffice服务
     * @param inputStream 输入流，用于接收文件
     * @param outputStream 输出流，用于响应页面
     * @param type 文件扩展名
     * @param host 主机 host
     * @param port 端口 port=8100
     */
    public static void fileConvertPdf(InputStream inputStream, OutputStream outputStream, String type, String host, int port){

        // 获得文件格式
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
        DocumentFormat docFormat;

        switch (type) {
            case "doc" :
                docFormat = formatReg.getFormatByFileExtension("doc");
                break;
            case "docx" :
                docFormat = formatReg.getFormatByFileExtension("doc");
                break;
            case "xls" :
                docFormat = formatReg.getFormatByFileExtension("xls");
                break;
            case "xlsx" :
                docFormat = formatReg.getFormatByFileExtension("xls");
                break;
            case "ppt" :
                docFormat = formatReg.getFormatByFileExtension("ppt");
                break;
            case "pdf" :
                docFormat = formatReg.getFormatByFileExtension("pdf");
                break;
            default:
                docFormat = formatReg.getFormatByFileExtension("doc");
                break;
        }

        //获取openOffice连接
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(host,port);
        try {
            connection.connect();
            StreamOpenOfficeDocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            converter.convert(inputStream, docFormat, outputStream, pdfFormat);
        } catch (ConnectException e) {
            throw new BusinessException("openOffice 连接异常",e);
        }finally {
            connection.disconnect();
        }
    }

    public static void previewPdfOrJpg(InputStream inputStream, HttpServletResponse response, String type){
        response.setCharacterEncoding("utf-8");
        switch (type) {
            case "pdf" :
                response.setContentType("application/pdf");
                break;
            default:
                response.setContentType("image/jpeg");
                break;
        }
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
             bis = new BufferedInputStream(inputStream);
             os = response.getOutputStream();
            int count;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = bis.read(buffer)) != -1){
                os.write(buffer, 0, count);
            }
            os.flush();
        }catch (IOException e) {
            throw new BusinessException("文件预览异常", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
