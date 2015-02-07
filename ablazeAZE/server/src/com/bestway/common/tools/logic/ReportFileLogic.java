package com.bestway.common.tools.logic;

import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.bestway.common.tools.entity.FileResource;

public class ReportFileLogic {

	/**
	 * ������Դ�б����
	 * 获取当前FTp 上的所有文件
	 * @param filePath
	 * @return
	 */
	public List<FileResource> getResourceList(String server, String userName,
			String password, String directory) {
		FTPClient ftp = Ftp.getFtpClient(true, server, userName, password);
		List<FileResource> list = Ftp.getResourceList(ftp, directory);
		Ftp.disconnect(ftp);
		return list;
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadFileResource(String server, String userName,
			String password, FileResource fileResource) {
		FTPClient ftp = Ftp.getFtpClient(true, server, userName, password);
		byte[] bytes = Ftp.download(ftp, fileResource.getFilePath());
		Ftp.disconnect(ftp);
		return bytes;
	}

	/**
	 * 上传插件
	 * 
	 */
	public StringBuffer uploadFileResource(String server, String userName,
			String password, List<String> remotes, List<byte[]> bytess) {
		FTPClient ftp = Ftp.getFtpClient(true, server, userName, password);
		StringBuffer sb = Ftp.upload(ftp, remotes, bytess);
		Ftp.disconnect(ftp);
		return sb;
	}

	/**
	 * 删除一个报表文件
	 */
	public StringBuffer deleteAllFileResource(String server, String userName,
			String password, String directory) {
		FTPClient ftp = Ftp.getFtpClient(true, server, userName, password);
		StringBuffer isOk = Ftp.deleteAllFileResource(ftp, directory);
		Ftp.disconnect(ftp);
		return isOk;
	}

	/**
	 * 删除一个报表文件
	 */
	public StringBuffer deleteFileResource(String server, String userName,
			String password, List<FileResource> fileResources) {
		FTPClient ftp = Ftp.getFtpClient(true, server, userName, password);
		StringBuffer sb = Ftp.deleteListFileResource(ftp, fileResources);
		Ftp.disconnect(ftp);
		return sb;
	}

}
