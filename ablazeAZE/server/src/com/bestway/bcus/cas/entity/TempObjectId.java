package com.bestway.bcus.cas.entity;

import java.util.UUID;

import com.bestway.common.BaseEntity;

/**
 * 临时对象表
 * @author ower
 *
 */
public class TempObjectId extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String objId = null;

	/**
	 * 唯一编号
	 */
	private Long uuid = null;

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

}
