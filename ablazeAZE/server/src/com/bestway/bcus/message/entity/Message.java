package com.bestway.bcus.message.entity;

/**
 * 报文信息
 * @author sanvi
 *
 */
public class Message {
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 长度
	 */
	private int length;
	
	/**
	 * 值
	 */
	private String value;
	/**
	 * 英文名
	 * hcl 2010-09-10 add
	 */
	private String name;
	

	public Message(String title, int length) {
		this.title = title;
		this.length = length;
	}
	public Message(String title, String name,int length) {
		this.title = title;
		this.name=name;
		this.length = length;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
}
