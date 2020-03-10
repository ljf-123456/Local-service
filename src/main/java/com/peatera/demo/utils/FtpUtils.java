package com.peatera.demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {
	 public static boolean uploadFile(
	            String url,//FTP服务器hostname
	            int port,//FTP服务器端口
	            String username, // FTP登录账号
	            String password, //FTP登录密码
	            String path, //FTP服务器保存目录
	            String filename, //上传到FTP服务器上的文件名
	            InputStream input // 输入流
	    ) {
		 
		 System.out.println("url=:"+url+"--port=:"+port+"--username=:"+username+"--password=:"+password+"--path=:"+path+"--filename=:"+filename+"--in=:"+input);
	        boolean success = false;
	        FTPClient ftp = new FTPClient();
	        ftp.enterLocalPassiveMode();
	        ftp.setConnectTimeout(100000000);
	        try {
	            int reply;
	            System.out.println("进入文件上传");
	            ftp.connect(url, port);//连接FTP服务器
	            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
	            System.out.println(ftp.login(username, password));//登录
	            reply = ftp.getReplyCode();
	            System.out.println(reply);
	            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	            if (!FTPReply.isPositiveCompletion(reply)) {
	                ftp.disconnect();
	                System.out.println("FTP服务器 拒绝连接");
	                return success;
	            }
	            ftp.changeWorkingDirectory(path);
	            System.out.println("file:"+input);
	           System.out.println( ftp.storeFile(filename, input));
	 
	            input.close();
	            ftp.logout();
	            success = true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (ftp.isConnected()) {
	                try {
	                    ftp.disconnect();
	                } catch (IOException ioe) {
	                }
	            }
	        }
	        return success;
	    }
	 
	    /**
	     * 删除文件
	     * @param fileName 要删除的文件地址
	     * @return true/false
	     * @throws IOException
	     */
	    public static boolean delete(String fileName, FTPClient ftpClient) throws IOException {
	        return ftpClient.deleteFile(fileName);
	    }
	 
	 
	    /**
	     * 下载文件到指定目录
	     * @param ftpFile 文件服务器上的文件地址
	     * @param dstFile 输出文件的路径和名称
	     * @throws Exception
	     */
	    public static void downLoad(String ftpFile, String dstFile, FTPClient ftpClient) throws Exception {
	        if (StringUtils.isBlank(ftpFile)) {
	            throw new RuntimeException("ftpFile为空");
	        }
	        if (StringUtils.isBlank(dstFile)) {
	            throw new RuntimeException("dstFile为空");
	        }
	        File file = new File(dstFile);
	        FileOutputStream fos = new FileOutputStream(file);
	        ftpClient.retrieveFile(ftpFile, fos);
	        fos.flush();
	        fos.close();
	    }
	 
	    /**
	     * 从文件服务器获取文件流
	     * @param ftpFile 文件服务器上的文件地址
	     * @return {@link InputStream}
	     * @throws IOException
	     */
	    public static InputStream retrieveFileStream(String ftpFile, FTPClient ftpClient) throws IOException {
	        if (StringUtils.isBlank(ftpFile)) {
	            throw new RuntimeException("ftpFile为空");
	        }
	        return ftpClient.retrieveFileStream(ftpFile);
	    }
	 
	    //root @sx04551 21  /opt   106.12.214.205
	    
	    
	    //D:\mysql\_cairo.pyd
}
