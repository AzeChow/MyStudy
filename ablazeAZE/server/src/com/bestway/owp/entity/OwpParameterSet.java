package com.bestway.owp.entity;

import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.message.entity.CspParameterSet;

/**
 * 关封参数设置表头
 * @author ower
 *
 */
public class OwpParameterSet extends CspParameterSet {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	private Integer projectType = ProjectType.BCUS;

	
	/**
	 * @return the projectType
	 */
	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
}
