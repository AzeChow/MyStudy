package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.common.BaseScmEntity;

/**
 * 报关单集装箱信息
 * 
 * @author refdom
 *
 */
public class BaseCustomsDeclarationContainer extends BaseScmEntity {
	/**
	 * 报关单头
	 */
	private BaseCustomsDeclaration baseCustomsDeclaration = null;
	
	/**
	 * 集装箱号码
	 */
	private String containerCode;

	/**
	 * 集装箱规格
	 */
	private SrtJzx srtJzx;

	/**
	 * 集装箱托架种类
	 */
	private SrtTj srttj;

	/**
	 * 取得报关单头
	 * @return 报关单头
	 */
	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}
	/**
	 * 设置报关单头
	 * @param customsDeclaration 报关单头
	 */
	public void setBaseCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.baseCustomsDeclaration = customsDeclaration;
	}
	/**
	 * 取得集装箱号码
	 * @return 集装箱号码
	 */
	public String getContainerCode() {
		return containerCode;
	}

	/**
	 * 设置集装箱规格
	 * @return 集装箱规格
	 */
	public SrtJzx getSrtJzx() {
		return srtJzx;
	}

	/**
	 * 设置集装箱规格
	 * @param srtJzx 集装箱规格
	 */
	public void setSrtJzx(SrtJzx srtJzx) {
		this.srtJzx = srtJzx;
	}

	/**
	 * 设置集装箱号码
	 * @param containerCode 集装箱号码
	 */
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	/**
	 * 取得集装箱托架种类
	 * @return 集装箱托架种类
	 */
	public SrtTj getSrttj() {
		return srttj;
	}

	/**
	 * 设置集装箱托架种类
	 * @param srttj 集装箱托架种类
	 */
	public void setSrttj(SrtTj srttj) {
		this.srttj = srttj;
	}
}
