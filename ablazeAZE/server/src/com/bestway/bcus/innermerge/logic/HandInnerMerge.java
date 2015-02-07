/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HandInnerMerge {

	private CommonBaseCodeDao commonBaseCodeDao;

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}
	
	/**
	 * 检查所选择的数据能否进行10位归并 如果数据有效则并且归并后的10位商品编码全部为空返回0，归并后的10位商品编码只要有一不为空返回1。；否则，
	 * 如果有编码不同的数据返回-1； 申报计量单位不同返回-2； 商品名称不同返回-3； 如果全部归并的话 返回-4；
	 * 如果选择的数据的备案序号不同返回-5。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForTenMerge(List list) {
		Complex complex = null;
		CalUnit calUnit = null;
		String materielName = "";
		Integer memoNo = Integer.valueOf(-1);
		int n = 0;
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (data.getHsAfterComplex() != null) {
				n++;
			}
			if (i == 0) {
				complex = data.getMateriel().getComplex();
				calUnit = data.getMateriel().getCalUnit();
				materielName = data.getMateriel().getFactoryName();
				memoNo = data.getHsAfterTenMemoNo();
				continue;
			}

			if (data.getMateriel().getComplex() != null) {
				if (complex != null) {
					if (!data.getMateriel().getComplex().equals(complex)) {
						return -1;
					}
				}
				complex = data.getMateriel().getComplex();
			}
			//Edit by xxm 2006-22-24 新增十码归并，不需要归并前商品名称，工厂单位相同
			/*if (data.getMateriel().getCalUnit() != null) {
				if (calUnit != null) {
					if (!data.getMateriel().getCalUnit().equals(calUnit)) {
						return -2;
					}
				}
				calUnit = data.getMateriel().getCalUnit();
			}*/
			/*if (data.getMateriel().getFactoryName() != null
					&& !data.getMateriel().getFactoryName().equals("")) {
				if (materielName != null && !materielName.equals("")) {
					if (!data.getMateriel().getFactoryName().toLowerCase().equals(
							materielName.toLowerCase())) {
						return -3;
					}
				}
				materielName = data.getMateriel().getFactoryName();
			}*/
			if (data.getHsAfterTenMemoNo() != null) {
				if (memoNo != null) {
					if (!memoNo.equals(data.getHsAfterTenMemoNo())) {
						return -5;
					}

				}
				memoNo = data.getHsAfterTenMemoNo();
			}
		}
		if (n == list.size()) {
			return -4;
		}
		return 1;
		/*if (n > 0) {
			return 1;
		} else {
			return 0;
		}*/
	}

	/**
	 * 10位商品归并。
	 * 
	 * @param list
	 *            归并的数据（一笔或多笔）
	 * @param afterComplex
	 *            归并后的10位商品
	 * @param afterMemoUnit
	 *            归并后的备案单位
	 * @param afterTenMaterielName
	 *            归并后的商品名称
	 * @param afterTenMaterielSpec
	 *            归并后的商品规格
	 * @param isNew
	 *            如果为true代表list中的数据重新归并到一新类型中， 如果为false，list中的数据归并到
	 *            list中数据已有的类型中。
	 */
	public void tenInnerMerge(List list, Complex afterComplex,
			Unit afterMemoUnit, String afterTenMaterielName,
			String afterTenMaterielSpec, Unit afterLegalUnit,
			Unit afterSecondLegalUnit, boolean isNew) {
		if (list.size() < 1) {
			return;
		}
		String type = ((InnerMergeData) list.get(0)).getImrType();
		int memoNo = 0;
		if (isNew) {
			memoNo = commonBaseCodeDao.findTenInnerMergeNo(type) + 1;
		} else {
			for (int i = 0; i < list.size(); i++) {
				InnerMergeData data = (InnerMergeData) list.get(i);
				if (data.getHsAfterTenMemoNo() != null) {
					memoNo = data.getHsAfterTenMemoNo().intValue();
					break;
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsAfterComplex(afterComplex);
			data.setHsAfterMaterielTenName(afterTenMaterielName);
			data.setHsAfterMaterielTenSpec(afterTenMaterielSpec);
			if (data.getMateriel().getComplex() == null) {
				data.setHsAfterLegalUnit(afterComplex.getFirstUnit());
				data.getMateriel().setComplex(afterComplex);
				commonBaseCodeDao.saveOrUpdate(data.getMateriel());
			} else {
				data.setHsAfterLegalUnit(data.getMateriel().getComplex()
						.getFirstUnit());
			}
			if (data.getHsBeforeLegalUnit() == null) {
				data.setHsBeforeLegalUnit(afterLegalUnit);
			}
			data.setHsAfterMemoUnit(afterMemoUnit);
			data.setHsAfterLegalUnit(afterLegalUnit);
			data.setHsAfterSecondLegalUnit(afterSecondLegalUnit);
			data.setHsAfterTenMemoNo(Integer.valueOf(memoNo));
			data.setUpdateDate(new Date());
			commonBaseCodeDao.saveInnerMergeData(data);
		}
	}

	/**
	 * 取消10为归并
	 * 
	 * @param list
	 */
	public void unDoTenInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsAfterComplex(null);
			data.setHsAfterMaterielTenName(null);
			data.setHsAfterMaterielTenSpec(null);
			data.setHsAfterLegalUnit(null);
			data.setHsAfterMemoUnit(null);
			data.setHsAfterTenMemoNo(null);
			data.setHsFourNo(null);
			data.setHsFourMaterielName(null);
			data.setHsFourCode(null);
			data.setUpdateDate(new Date());
			
			commonBaseCodeDao.saveInnerMergeData(data);
		}
	}

	/**
	 * 检查能否进行撤消10位归并动作。 如果允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForUndoTenInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (data.getHsFourCode() != null
					&& !data.getHsFourCode().equals("")) {
				return -1;
			}
		}
		return 0;
	}
	
	public int checkDataEndTenInnerMerge(List list){
		for (int i=0;i<list.size();i++){
			InnerMergeData data = (InnerMergeData) list.get(i);
			boolean s = this.commonBaseCodeDao.isendtenInnerMerge(data);
			if (s && data.getHsFourNo() != null){
				return -1;
			}
		}
		return 0;
	}

	/**
	 * 检查所选择的数据能否进行4位归并。 如果检查结果允许归并 返回0； 如果所选择的数据其中有没有经过10位归并的 返回 -1；
	 * 如果所选择的数据的10位商品编码的前4位不同的返回 -2； 如果所选择的已经归并过的数据有不同编码序号的 返回-3; 如果全部已归并返回-4。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForFourInnerMerge(List list) {
		String frontFourData = "";
		Integer memoNo = Integer.valueOf(-1);
		int n = 0;
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (data.getHsAfterComplex() == null) {
				return -1;
			}
			if (data.getHsFourCode() != null
					&& !data.getHsFourCode().trim().equals("")) {
				n++;
			}

			if (i == 0) {

				frontFourData = data.getHsAfterComplex().getCode().substring(0,
						4);
				memoNo = data.getHsFourNo();
				continue;
			}
			if (!frontFourData.equals(data.getHsAfterComplex().getCode()
					.substring(0, 4))) {
				return -2;
			} else {
				frontFourData = data.getHsAfterComplex().getCode().substring(0,
						4);
			}
			if (data.getHsFourNo() != null) {
				if (memoNo != null) {
					if (!memoNo.equals(data.getHsFourNo())) {
						return -3;
					}
				}
				memoNo = data.getHsFourNo();
			}
		}
		if (n == list.size()) {
			return -4;
		}
		if (n > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 4位商品归并 10位商品编码的前4位相同，并且属于同一类商品。
	 * 
	 * @param list
	 * @param isNew
	 */
	public void fourInnerMerge(List list,String fourCommodityName, boolean isNew) {
		if (list.size() < 1) {
			return;
		}
		String type = ((InnerMergeData) list.get(0)).getImrType();
		int memoNo = 0;
		if (isNew) {
			memoNo = commonBaseCodeDao.findFourInnerMergeNo(type) + 1;
		} else {
			for (int i = 0; i < list.size(); i++) {
				InnerMergeData data = (InnerMergeData) list.get(i);
				if (data.getHsFourNo() != null) {
					memoNo = data.getHsFourNo().intValue();
					break;
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);			
			data.setHsFourNo(Integer.valueOf(memoNo));
			data.setUpdateDate(new Date());
			data.setHsFourCode(data.getHsAfterComplex().getCode().substring(0,
					4));
			data.setHsFourMaterielName(fourCommodityName);
			commonBaseCodeDao.saveInnerMergeData(data);
		}
	}

	/**
	 * 取消4位商品归并。
	 * 
	 * @param list
	 */
	public void undoFourInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setUpdateDate(new Date());
			data.setHsFourNo(null);
			data.setHsFourCode(null);
			data.setHsFourMaterielName(null);
			commonBaseCodeDao.saveInnerMergeData(data);
		}
	}
	

}
