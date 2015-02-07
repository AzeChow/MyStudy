package com.bestway.sys.qp.action;

import java.util.List;

public interface SysQpAction {
	/**
	 * 判断企业是否有权限访问远程服务器
	 * http://whb.bsw.com.cn/khbinput/khb/input/  endTime=2011-01-01
	 * @param tradeCode
	 * @return
	 */
	boolean checkAllowInvoke(String tradeCode);
}
