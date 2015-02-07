package com.bestway.common.tools.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.bestway.common.CommonUtils;
import com.bestway.common.tools.entity.FileResource;

public final class Ftp {

	private static Log loger = LogFactory.getLog(Ftp.class);

	public static FTPClient getFtpClient(boolean binaryTransfer, String server,
			String userName, String password) {

		FTPClient ftp = new FTPClient();
		// FTPClientConfig conf = new
		// FTPClientConfig(FTPClientConfig.SYST_UNIX);
		// conf.setServerLanguageCode(Locale.getDefault().getDisplayLanguage());

//		ftp.setControlEncoding("GBK");
		ftp.setControlEncoding("UTF-8");

		try {
			int reply;
			ftp.connect(server);
			loger.info("Connected to " + server + ".");
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
				}
			}
			loger.error("Could not connect to server.");
			e.printStackTrace();
			throw new RuntimeException("|不能连接 "+server+" FTP 服务器 !!");
		}

		try {
			if (!ftp.login(userName, password)) {
				ftp.logout();
				throw new RuntimeException("|不能登陆 "+server+" FTP 服务器 !!");
			}

			loger.info("Remote system is " + ftp.getSystemName());

			if (binaryTransfer)
				ftp.setFileType(FTP.BINARY_FILE_TYPE);

			// Use passive mode as default because most of us are
			// behind firewalls these days.
			/**
			 * l.去掉enterLocalPassiveMode by lxr 2012-02-29，因为有企业不行
			 */
//			ftp.enterLocalActiveMode();
			// ftp.enterLocalPassiveMode();
			// ftp.enterRemotePassiveMode();

		} catch (Exception e) {
			loger.error("Server closed connection.");
			e.printStackTrace();
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
				}
			}
			throw new RuntimeException("|不能登录 "+server+" FTP 服务器 !!");
		}
		return ftp;
	}

	/**
	 * disconnect
	 */
	public static void disconnect(FTPClient ftp) {
		try {
			ftp.logout();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param ftp
	 * @param remote
	 * @param local
	 */
	public static final byte[] download(FTPClient ftp, String remote) {
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			ftp.retrieveFile(remote, output);
			output.flush();
		} catch (Exception ex) {
			throw new RuntimeException("Error download byte data : " + remote,
					ex);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
		return output.toByteArray();
	}

	/**
	 * �ϴ�
	 * 
	 * @param ftp
	 * @param remote
	 * @param local
	 */
	public static final StringBuffer upload(FTPClient ftp,
			List<String> remotes, List<byte[]> bytess) {
		StringBuffer sb = new StringBuffer(20);
		for (int i = 0; i < remotes.size(); i++) {
			String remote = remotes.get(i);
			byte[] bytes = bytess.get(i);
			ByteArrayInputStream input = null;
			String info = null;
			System.out.println("remoteName="+remote);
			try {
				input = new ByteArrayInputStream(bytes);
				if(!ftp.storeFile(remote, input)){
					throw new RuntimeException(remote+" 上传失败");
				}
				info = remote + " 上传成功";
				loger.info(info);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				info = remote + " 上传失败";
				loger.info("Error ftp storeFile byte data : " + remote);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
			}
			sb.append(info + "\n");
		}
		return sb;
	}

	/**
	 * ��ȡ��·���µ�������Ϣ�����б���
	 * 
	 * @param listFileResource
	 * @param mapDirectory
	 * @param filePath
	 * @throws Exception
	 */
	private static void fileResource(FTPClient ftpClient,
			List<FileResource> listFileResource, String directory) {
		try {
			if (directory == null || "".equals(directory.trim())) {
				return;
			}
			
//			String[] paths = CommonUtils.split(directory, "/");
//			String parentDir = "/";
//			for (int k = 0; k < paths.length; k++) {
//				
//				String tempDir = paths[k];
//				FTPFile[] ftpRootFiles = ftpClient.listFiles(parentDir);
//				boolean isExistDirectory = false;
//				for (int i = 0, n = ftpRootFiles.length; i < n; i++) {
//					FTPFile ftpFile = ftpRootFiles[i];
//					if (ftpFile.isDirectory()
//							&& ftpFile.getName().equals(tempDir)) {
//						isExistDirectory = true;
//						break;
//					}
//				}
//				if (!isExistDirectory) {
//					ftpClient.makeDirectory(tempDir);
//				}				
//				parentDir += k==0?paths[k]:"/"+paths[k] ;
//			}

			FTPFile[] ftpRootFiles = ftpClient.listFiles("/");
			boolean isExistDirectory = false;
			for (int i = 0, n = ftpRootFiles.length; i < n; i++) {
				FTPFile ftpFile = ftpRootFiles[i];
				if (ftpFile.isDirectory()
						&& ftpFile.getName().equals(directory)) {
					isExistDirectory = true;
					break;
				}
			}
			if (!isExistDirectory) {
				ftpClient.makeDirectory(directory);
			}
			
			FTPFile[] ftpFiles = ftpClient.listFiles(directory);
			//
			// ��b FileResource
			//
			for (int i = 0, n = ftpFiles.length; i < n; i++) {
				FTPFile ftpFile = ftpFiles[i];

				if (!ftpFile.isDirectory()) {
					FileResource fileResource = new FileResource();
					fileResource.setFileName(ftpFile.getName());
					fileResource.setFileLength(ftpFile.getSize());
					//
					// 修改时间
					//					
					String modifiyTime = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(ftpFile
							.getTimestamp().getTime());
					fileResource.setFilePath(directory + "/"
							+ ftpFile.getName());
					fileResource.setLastModifeTime(modifiyTime);
					listFileResource.add(fileResource);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException("|不能登录 FTP 服务器 !!");
		}
	}

	/**
	 * ������Դ�б����
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<FileResource> getResourceList(FTPClient ftpClient,
			String directory) {
		List<FileResource> listFileResource = new ArrayList<FileResource>();
		fileResource(ftpClient, listFileResource, directory);
		return listFileResource;
	}

	public static boolean deleteDirectory(FTPClient ftpClient, String directory) {
		try {
			boolean isDelete = ftpClient.removeDirectory(directory);
			if (isDelete) {
				loger.info(directory + " 删除成功 !");
			} else {
				loger.info(directory + " 不能删除 !");
			}
			return isDelete;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("FTP 目录 "+directory + "删除不成功!!");
		}

	}

	public static StringBuffer deleteAllFileResource(FTPClient ftpClient,
			String directory) {
		List<FileResource> listFileResource = new ArrayList<FileResource>();
		fileResource(ftpClient, listFileResource, directory);
		return deleteListFileResource(ftpClient, listFileResource);
	}

	public static StringBuffer deleteListFileResource(FTPClient ftpClient,
			List<FileResource> listFileResource) {
		StringBuffer sb = new StringBuffer(20);
		for (FileResource f : listFileResource) {
			String fileName = f.getFilePath();
			String info = null;
			try {

				boolean isDelete = ftpClient.deleteFile(fileName);
				if (isDelete) {
					info = fileName + " 删除成功 !";
				} else {
					info = fileName + " 不能删除 !";
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				info = fileName + " 删除失败 !";
			}
			loger.info(info);
			sb.append(info + "\n");
		}
		return sb;
	}

}
