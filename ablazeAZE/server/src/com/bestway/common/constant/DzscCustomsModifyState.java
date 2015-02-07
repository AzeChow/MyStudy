package com.bestway.common.constant;

public class DzscCustomsModifyState {
//	public static final String ADDED = "1";// 新增
//
//	public static final String MODIFIED = "2";// 修改
//
//	public static final String DELETED = "3";// 删除

	public static String getCustomsModifyState(String modifyMark) {
//		if (ModifyMarkState.ADDED.equals(modifyMark)) {
//			return DzscCustomsModifyState.ADDED;
//		} else if (ModifyMarkState.MODIFIED.equals(modifyMark)) {
//			return DzscCustomsModifyState.MODIFIED;
//		} else if (ModifyMarkState.DELETED.equals(modifyMark)) {
//			return DzscCustomsModifyState.DELETED;
//		}
//		return DzscCustomsModifyState.ADDED;
		return modifyMark;
	}
}
