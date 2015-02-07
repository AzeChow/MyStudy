package com.bestway.common.tools.entity;
/**
 * 文件信息
 * @author Administrator
 *
 */
public class FileResource implements java.io.Serializable {
	private static final long	serialVersionUID	= 1L;
	/**
	 * 文件名称
	 */
	private String				fileName			= null;
	/**
	 * 文件路径
	 */
	private String				filePath			= null;
	/**
	 * 
	 */
	private Long				lastModife			= null;
	/**
	 * 修改时间
	 */
	private String				lastModifeTime		= null;
	/**
	 * 文件长度
	 */
	private Long				fileLength			= null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getLastModife() {
		return lastModife;
	}

	public void setLastModife(Long lastModife) {
		this.lastModife = lastModife;
	}

	/**
	 * @return Returns the fileLength.
	 */
	public Long getFileLength() {
		return fileLength;
	}

	/**
	 * @param fileLength
	 *            The fileLength to set.
	 */
	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public String getLastModifeTime() {
		return lastModifeTime;
	}

	public void setLastModifeTime(String lastModifeTime) {
		this.lastModifeTime = lastModifeTime;
	}

	
	public Integer getKb(){
		int kb = Double.valueOf(
                (fileLength) / 1024).intValue() ;
		return kb<1 ? 1:kb;
	}
}
