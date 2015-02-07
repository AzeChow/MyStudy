/*
 * Created on 2005-4-1
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomExg;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsProductScaleInfo;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.logic.ContractLogic;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.logic.MaterialManageLogic;
import com.jnetdirect.a.m;

/**
 * com.bestway.bcs.bcsinnermerge.logic.BcsInnerMergeLogic
 * 
 * @author ls checked by 陈井彬 date 2008.11.29 内部归并LOGIC
 */
@SuppressWarnings("unchecked")
public class BcsInnerMergeLogic {
	/**
	 * 内部归并DAO接口
	 */
	private BcsInnerMergeDao bcsInnerMergeDao = null;

	/**
	 * 工厂通用代码dao
	 */
	private MaterialManageDao materialManageDao;

	/**
	 * 工厂通用代码Logic
	 */
	private MaterialManageLogic materialManageLogic = null;

	/**
	 * 合同操作LOGIC
	 */
	private ContractLogic contractLogic = null;

	/**
	 * 获取materialManageDao
	 * 
	 * @return
	 */
	public MaterialManageLogic getMaterialManageLogic() {
		return materialManageLogic;
	}

	/**
	 * 设置materialManageDao
	 * 
	 * @param materialManageDao
	 */
	public void setMaterialManageLogic(MaterialManageLogic materialManageLogic) {
		this.materialManageLogic = materialManageLogic;
	}

	/**
	 * 获取bcsInnerMergeDao
	 * 
	 * @return bcsInnerMergeDao
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 获取contractLogic
	 * 
	 * @return contractLogic
	 */
	public ContractLogic getContractLogic() {
		return contractLogic;
	}

	/**
	 * 设置contractLogic
	 * 
	 * @param contractLogic
	 */
	public void setContractLogic(ContractLogic contractLogic) {
		this.contractLogic = contractLogic;
	}

	/**
	 * 获取materialManageDao
	 * 
	 * @return
	 */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/**
	 * 设置materialManageDao
	 * 
	 * @param materialManageDao
	 */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 设置bcsInnerMergeDao
	 * 
	 * @param bcsInnerMergeDao
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	// /**
	// * 自动从报关常用物料中导入资料
	// *
	// * @param materielType
	// * 物料类型
	// */
	// public void importInnerMergeDataFromMateriel(String materielType) {
	// List list = this.bcsInnerMergeDao.findMaterielForBcsInnerMerge(
	// materielType, -1, -1);
	// for (int i = 0; i < list.size(); i++) {
	// Materiel materiel = (Materiel) list.get(i);
	// BcsInnerMerge b = new BcsInnerMerge();
	// b.setCompany(materiel.getCompany());
	// b.setMateriel(materiel);
	// b.setMaterielType(materielType);
	// this.bcsInnerMergeDao.saveBcsInnerMerge(b);
	// /**
	// * 使用为真
	// */
	// materiel.setIsUseInBcsInnerMerge(true);
	// this.bcsInnerMergeDao.saveOrUpdate(materiel);
	// }
	// }

	/**
	 * 导入物料与报关对应表来自报关常用物料
	 * 
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List importInnerMergeDataFromMateriel(List materielList,
			String materielType) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<BcsInnerMerge> returnList = new ArrayList<BcsInnerMerge>();
		for (int i = 0; i < materielList.size(); i++) {
			Materiel materiel = (Materiel) materielList.get(i);
			/**
			 * 使用为真
			 */
			materiel.setIsUseInBcsInnerMerge(true);
			this.bcsInnerMergeDao.saveOrUpdate(materiel);

			BcsInnerMerge b = new BcsInnerMerge();
			b.setUnitConvert(materiel.getUnitConvert());
			b.setCompany(materiel.getCompany());
			b.setMaterielType(materielType);
			boolean isGeting = true;
			String ptNo = materiel.getPtNo();
			//hwy判断新增的物料是否重复；
			isGeting = bcsInnerMergeDao.findisGeting(materielType,ptNo);
			if(!isGeting){
				b.setIsUsing(true);
			}
			b.setMateriel(materiel);
			b.setCreateDate(java.sql.Date.valueOf(bartDateFormat
					.format(new Date())));// 导入时间
			this.bcsInnerMergeDao.saveBcsInnerMerge(b);
			returnList.add(b);
		}
		return returnList;
	}

	/**
	 * 删除物料与报关对应表里的数据
	 * 
	 * @param bcsInnerMerge
	 *            物料与报关对应表里的数据
	 */
	public void deleteBcsInnerMerge(BcsInnerMerge bcsInnerMerge) {
		this.bcsInnerMergeDao.deleteBcsInnerMerge(bcsInnerMerge);
		
		Materiel materiel = bcsInnerMerge.getMateriel();
	
		//删除 按照物料是否在对应关系表中存在，来设置物料是否被使用字段
		List<BcsInnerMerge> ls = this.bcsInnerMergeDao.findBcsInnerMerge(materiel);
		
		materiel.setIsUseInBcsInnerMerge(ls != null && !ls.isEmpty());
		
		this.bcsInnerMergeDao.saveOrUpdate(materiel);
		
		System.out.println(" materiel ----------------- ");
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param bcsInnerMergeList
	 *            物料与报关对应表里的数据
	 */
	public void deleteBcsInnerMerge(List<BcsInnerMerge> bcsInnerMergeList) {
		for (BcsInnerMerge bcsInnerMerge : bcsInnerMergeList) {
			this.deleteBcsInnerMerge(bcsInnerMerge);
		}
	}

	/**
	 * 自动归并,名称，规格，编码，单位相同
	 * 
	 * @param materielType
	 *            物料类型
	 */
	public void bcsAutoMerge(String materielType) {
		List list = this.bcsInnerMergeDao.findBcsInnerMerge(materielType);
		int maxTenInnerMergeNo = this.bcsInnerMergeDao
				.getMaxTenBcsInnerMergeNo(materielType);
		Map<String, BcsInnerMerge> map = new HashMap<String, BcsInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) list.get(i);
			BcsTenInnerMerge b = bcsInnerMerge.getBcsTenInnerMerge();
			if (b == null) {
				continue;
			}
			if (b.getSeqNum() != null) {
				String key = (b.getName() == null ? "" : b.getName())
						+ (b.getSpec() == null ? "" : b.getSpec())
						+ (b.getComUnit() == null ? "" : b.getComUnit()
								.getCode())
						+ (b.getComplex() == null ? "" : b.getComplex()
								.getCode());
				map.put(key, bcsInnerMerge);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) list.get(i);
			BcsTenInnerMerge bcsTenInnerMerge = bcsInnerMerge
					.getBcsTenInnerMerge();
			Materiel m = bcsInnerMerge.getMateriel();
			String key = (m.getFactoryName() == null ? "" : m.getFactoryName())
					+ (m.getFactorySpec() == null ? "" : m.getFactorySpec())
					+ (m.getPtUnit() == null ? "" : m.getPtUnit().getCode())
					+ (m.getComplex() == null ? "" : m.getComplex().getCode());
			BcsInnerMerge tempB = map.get(key);
			if (tempB == null) {
				BcsTenInnerMerge b = new BcsTenInnerMerge();
				maxTenInnerMergeNo++;
				b.setSeqNum(maxTenInnerMergeNo);
				// b.setId(String.valueOf(maxTenInnerMergeNo));
				b.setComplex(m.getComplex());
				b.setCountry(m.getSysArea() == null ? null : m.getSysArea()
						.getCountry());
				b.setCurr(null);
				b.setLegalAmount(null);
				b.setName(m.getFactoryName());
				b.setSpec(m.getFactorySpec());
				b.setSecondLegalAmount(null);
				b.setComUnit(m.getPtUnit());
				b.setPrice(m.getPtPrice());
				b.setUnitWeight(m.getPtNetWeight());
				b.setCompany(CommonUtils.getCompany());
				b.setScmCoi(materielType);
				this.bcsInnerMergeDao.saveBcsTenInnerMerge(b);
				bcsInnerMerge.setBcsTenInnerMerge(b);
				this.bcsInnerMergeDao.saveBcsInnerMerge(bcsInnerMerge);
				map.put(key, bcsInnerMerge);
			} else {
				bcsInnerMerge.setBcsTenInnerMerge(tempB.getBcsTenInnerMerge());
				this.bcsInnerMergeDao.saveBcsInnerMerge(bcsInnerMerge);
			}
		}

	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	public void saveBcsInnerMerge(List list, BcsTenInnerMerge tempB) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (list.size() < 1) {
			return;
		}
		this.bcsInnerMergeDao.saveBcsTenInnerMerge2(tempB);
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge b = (BcsInnerMerge) list.get(i);

			/**
			 * 是修改
			 */
			if (b.getBcsTenInnerMerge() != null) {
				if (b.getBcsTenInnerMerge().getSeqNum().intValue() != tempB
						.getSeqNum().intValue()) {
					continue;
				}
			}
			b.setCreateDate(java.sql.Date
					.valueOf(bartDateFormat.format(new Date())));//导入时间
			b.setBcsTenInnerMerge(tempB);
			this.bcsInnerMergeDao.saveBcsInnerMerge(b);
		}
	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	public void updateBcsInnerMerge(List list, BcsTenInnerMerge tempB) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (list.size() < 1) {
			return;
		}
		this.bcsInnerMergeDao.saveBcsTenInnerMerge2(tempB);
		String materielType = ((BcsInnerMerge) list.get(0)).getMaterielType();
		List<BcsInnerMerge> updateList = new ArrayList<BcsInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge b = (BcsInnerMerge) list.get(i);
			if (b.getBcsTenInnerMerge().getSeqNum() != null) { // 是修改
				if (b.getBcsTenInnerMerge().getSeqNum().intValue() != tempB
						.getSeqNum().intValue()) {
					continue;
				}
				updateList.add(b);
			}
			b.setCreateDate(java.sql.Date.valueOf(bartDateFormat
					.format(new Date())));// 导入时间
			b.setBcsTenInnerMerge(tempB);
			this.bcsInnerMergeDao.saveBcsInnerMerge(b);
		}
	}

	/**
	 * 取消物料与报关对应表里的10码归并
	 * 
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 */
	public void unDoTenInnerMerge(List<BcsInnerMerge> list) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMerge b = list.get(i);
			b.setIsUsing(false);//标志为当前不使用
			b.setCreateDate(java.sql.Date.valueOf(bartDateFormat
					.format(new Date())));// 导入时间
//			BcsTenInnerMerge tenInnerMerge = b.getBcsTenInnerMerge();
			b.setBcsTenInnerMerge(null);
			this.bcsInnerMergeDao.saveBcsInnerMerge(b);
			
			// 2013-7-23 chl 当撤销对应关系时，不删除报关商品资料的数据。日本电产精密马达科技
//			if (bcsInnerMergeDao
//					.findBcsInnerMergeByTenInnerMerge(tenInnerMerge).isEmpty())// 撤消归并时,删除没用的归并后资料
//				bcsInnerMergeDao.delete(tenInnerMerge);
		}
	}

	/**
	 * 物料与报关对应表里十码重排
	 * 
	 * @param selectedRows
	 *            是BcsInnerMerge型，物料与报关对应表
	 * @param toNo
	 *            重排到的行号
	 */
	public void reSortMergeTenNo2(List selectedRows, int toNo) {
		int offset = 0;
		int[] selectedNo = null;
		int minNo = 0;// 获取选择行中的最小序号。
		int maxNo = 0;// 获取选择行中的最大序号。
		int selectedRowNum = 0;
		int n = 0;// 临时变量，为取得所选择的4位序号时候做hashtable的键值。
		Hashtable<Object, Object> ht = new Hashtable<Object, Object>();
		Set set = new HashSet();
		List minMaxBetweenList = new ArrayList();// 在选中行的之间的list

		String type = ((BcsInnerMerge) selectedRows.get(0)).getMaterielType();
		List allRows = this.bcsInnerMergeDao.findBcsInnerMerge(type);

		for (int i = 0; i < selectedRows.size(); i++) {
			BcsInnerMerge b = (BcsInnerMerge) selectedRows.get(i);
			int currentValue = b.getBcsTenInnerMerge().getSeqNum().intValue();
			if (i == 0) {
				minNo = b.getBcsTenInnerMerge().getSeqNum().intValue();
				maxNo = minNo;
				ht.put(b.getBcsTenInnerMerge().getSeqNum(), b
						.getBcsTenInnerMerge().getSeqNum());
				continue;
			}
			if (ht.get(b.getBcsTenInnerMerge().getSeqNum()) == null) {
				ht.put(b.getBcsTenInnerMerge().getSeqNum(), b
						.getBcsTenInnerMerge().getSeqNum());
			}
			if (minNo > b.getBcsTenInnerMerge().getSeqNum().intValue()) {
				minNo = b.getBcsTenInnerMerge().getSeqNum().intValue();
			}
			if (maxNo < b.getBcsTenInnerMerge().getSeqNum().intValue()) {
				maxNo = b.getBcsTenInnerMerge().getSeqNum().intValue();
			}
		}

		/**
		 * 取得偏移量 判断向上还是向下
		 */
		offset = minNo - toNo;

		/**
		 * 获得选择行的数据并按升序排列
		 */
		selectedRowNum = ht.size();
		selectedNo = new int[selectedRowNum];
		int j = 0;
		for (Enumeration e = ht.elements(); e.hasMoreElements();) {
			Integer integer = (Integer) e.nextElement();
			selectedNo[j] = integer.intValue();
			j++;
		}
		Arrays.sort(selectedNo);

		/**
		 * 重排记录
		 */
		for (int i = 0; i < allRows.size(); i++) {
			BcsInnerMerge b = (BcsInnerMerge) allRows.get(i);
			if (b.getBcsTenInnerMerge().getSeqNum() == null) {
				continue;
			}
			int currentNo = b.getBcsTenInnerMerge().getSeqNum().intValue();
			if (offset > 0) {// 向上重排
				if (ht.get(b.getBcsTenInnerMerge().getSeqNum()) != null) {// 重排选中的行数据
					for (int k = 0; k < selectedNo.length; k++) {
						if (currentNo == selectedNo[k]) {
							int toRowNo = toNo + k;
							b.getBcsTenInnerMerge().setSeqNum(toRowNo);
						}
					}
					this.bcsInnerMergeDao.saveBcsTenInnerMerge(b
							.getBcsTenInnerMerge());
				} else {// 重排未选中的行数据
					if (selectedRowNum == 0) {
						continue;
					}
					if (currentNo >= toNo && currentNo < minNo) {
						b.getBcsTenInnerMerge().setSeqNum(
								currentNo + selectedRowNum);
						this.bcsInnerMergeDao.saveBcsTenInnerMerge(b
								.getBcsTenInnerMerge());
					} else if (currentNo > minNo && currentNo < maxNo) {
						minMaxBetweenList.add(b);
					}
				}
			} else { // 向下重排
				if (ht.get(b.getBcsTenInnerMerge().getSeqNum()) != null) {// 重排选中的行数据
					for (int k = 0; k < selectedNo.length; k++) {
						if (currentNo == selectedNo[k]) {
							int toRowNo = toNo - (selectedNo.length - 1 - k);
							b.getBcsTenInnerMerge().setSeqNum(toRowNo);
						}
					}
					this.bcsInnerMergeDao.saveBcsTenInnerMerge(b
							.getBcsTenInnerMerge());
				} else {
					if (selectedRowNum == 0) {
						continue;
					}
					if (currentNo <= toNo && currentNo > maxNo) {
						b.getBcsTenInnerMerge().setSeqNum(
								currentNo - selectedRowNum);
						this.bcsInnerMergeDao.saveBcsTenInnerMerge(b
								.getBcsTenInnerMerge());
					} else if (currentNo > minNo && currentNo < maxNo) {
						minMaxBetweenList.add(b);
					}
				}
			}
		}
		/**
		 * 排列选中之间的数据记录
		 */
		if (offset > 0) {// 向上重排
			n = maxNo;
			for (int i = maxNo - 1; i > minNo; i--) {
				boolean isFlag = false;
				for (int l = 0; l < minMaxBetweenList.size(); l++) {
					BcsInnerMerge b = (BcsInnerMerge) minMaxBetweenList.get(i);
					int currentNo = b.getBcsTenInnerMerge().getSeqNum()
							.intValue();
					if (currentNo == i) {
						b.getBcsTenInnerMerge().setSeqNum(new Integer(n));
						this.bcsInnerMergeDao.saveBcsTenInnerMerge(b
								.getBcsTenInnerMerge());
						isFlag = true;
					}
				}
				if (isFlag == true) {
					n--;
				}
			}
		} else { // 向下重排
			n = minNo;
			for (int i = minNo + 1; i < maxNo; i++) {
				boolean isFlag = false;
				for (int l = 0; l < minMaxBetweenList.size(); l++) {
					BcsInnerMerge b = (BcsInnerMerge) minMaxBetweenList.get(i);
					int currentNo = b.getBcsTenInnerMerge().getSeqNum()
							.intValue();
					if (currentNo == i) {
						b.getBcsTenInnerMerge().setSeqNum(new Integer(n));
						this.bcsInnerMergeDao.saveBcsTenInnerMerge(b
								.getBcsTenInnerMerge());
						isFlag = true;
					}
				}
				if (isFlag == true) {
					n++;
				}
			}
		}

	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param list
	 *            是Complex型，自用商品编码
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List saveBcsTenInnerMerge(List list, String materielType) {
		List lsResult = new ArrayList();
		BcsParameterSet bcsParameterSet = contractLogic.getContractDao()
				.findBcsParameterSet();// 参数设置
		for (int i = 0; i < list.size(); i++) {
			Complex c = (Complex) list.get(i);
			BcsTenInnerMerge b = new BcsTenInnerMerge();
			Integer maxvalue = this.bcsInnerMergeDao
					.getMaxTenBcsInnerMergeNo(materielType);
			b.setSeqNum(maxvalue + 1);
			// b.setId(String.valueOf(maxvalue + 1));
			b.setComplex(c);
			b.setName(c.getName());
			// b.setSpec(c.getSpec());
			if (bcsParameterSet.getIsControlLength() != null
					&& bcsParameterSet.getIsControlLength()) {// 参数设置,是否要控制名称、规格的长度
				b.setName(CommonUtils.controlLengthByGBK(b.getName(),
						bcsParameterSet.getBytesLength() == null ? 255
								: bcsParameterSet.getBytesLength()));// 名称
				b.setSpec(CommonUtils.controlLengthByGBK(b.getSpec(),
						bcsParameterSet.getBytesLength() == null ? 255
								: bcsParameterSet.getBytesLength()));// 规格
			}
			b.setScmCoi(materielType);
			b.setCompany(CommonUtils.getCompany());
			this.bcsInnerMergeDao.saveBcsTenInnerMerge(b);
			lsResult.add(b);
		}
		return lsResult;
	}
	/**
	 * 添加报关商品资料来自于商品编码
	 * 
	 * @param list
	 *            是Complex型，自用商品编码
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List importBcsTenInnerMergeFromComplex(List list, String materielType){
		List lsResult = new ArrayList();
		BcsParameterSet bcsParameterSet = contractLogic.getContractDao()
				.findBcsParameterSet();// 参数设置
		for (int i = 0; i < list.size(); i++) {
			Complex c = (Complex) list.get(i);
			BcsTenInnerMerge b = new BcsTenInnerMerge();
			Integer maxvalue = this.bcsInnerMergeDao
					.getMaxTenBcsInnerMergeNo(materielType);
			b.setSeqNum(maxvalue + 1);
			// b.setId(String.valueOf(maxvalue + 1));
			b.setComplex(c);
			b.setName(c.getName());
			// b.setSpec(c.getSpec());
			if (bcsParameterSet.getIsControlLength() != null
					&& bcsParameterSet.getIsControlLength()) {// 参数设置,是否要控制名称、规格的长度
				b.setName(CommonUtils.controlLengthByGBK(b.getName(),
						bcsParameterSet.getBytesLength() == null ? 255
								: bcsParameterSet.getBytesLength()));// 名称
				b.setSpec(CommonUtils.controlLengthByGBK(b.getSpec(),
						bcsParameterSet.getBytesLength() == null ? 255
								: bcsParameterSet.getBytesLength()));// 规格
			}
			b.setScmCoi(materielType);
			b.setCompany(CommonUtils.getCompany());
			lsResult.add(b);
		}
		return lsResult;
	}
	

	/**
	 * 删除报关商品资料
	 * 
	 * @param list
	 *            是BcsTenInnerMerge型，报关商品资料
	 */
	public void deleteBcsTenInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			BcsTenInnerMerge c = (BcsTenInnerMerge) list.get(i);
			this.bcsInnerMergeDao.deleteBcsTenInnerMerge(c);
		}
	}

	/**
	 * 删除不在合同备案和物料与报关对应表里的报关商品资料
	 * 
	 * @param list
	 *            是BcsTenInnerMerge型，报关商品资料
	 * @param items
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List deleteBcsTenInnerMergeForContract(List list, String items) {
		List listBcsTenInnerMerge = new Vector();
		List lsContractCredenceNo = null;
		List lsDictPorInnerMergeSeqNum = null;
		if (MaterielType.FINISHED_PRODUCT.equals(items)) {
			lsContractCredenceNo = this.bcsInnerMergeDao
					.getContractExgCredenceNo();
			lsDictPorInnerMergeSeqNum = this.bcsInnerMergeDao
					.getBcsDictPorExgInnerMergeSeqNum();
		} else {
			lsContractCredenceNo = this.bcsInnerMergeDao
					.getContractImgCredenceNo();
			lsDictPorInnerMergeSeqNum = this.bcsInnerMergeDao
					.getBcsDictPorImgInnerMergeSeqNum();
		}
		List lsInnerMergeId = this.bcsInnerMergeDao.getBcsInnerMergeForId();
		for (int i = 0; i < list.size(); i++) {
			BcsTenInnerMerge c = (BcsTenInnerMerge) list.get(i);
			Integer innerMergeSeqNum = c.getSeqNum();
			String id = c.getId();
			if (lsContractCredenceNo.contains(innerMergeSeqNum)
					|| lsInnerMergeId.contains(id)
					|| lsDictPorInnerMergeSeqNum.contains(innerMergeSeqNum)) {
				// System.out.println("商品归并或合同中用到该笔数据，请先删除！！ "+credenceNo);
			} else {
				listBcsTenInnerMerge.add(c);
				this.bcsInnerMergeDao.deleteBcsTenInnerMerge(c);
			}
		}
		return listBcsTenInnerMerge;
	}

	/**
	 * 初始化单位折算比例
	 */
	public void initUnitDedault(String type) {
		List<BcsInnerMerge> list = this.bcsInnerMergeDao
				.finbcsInnerMergeByType(type);
		Map<String, Double> keyMap = new HashMap<String, Double>();
		if (list == null) {
			return;
		}
		List namelist = this.materialManageDao.findNameOfUnitCollate();
		for (int s = 0; s < namelist.size(); s++) {
			Object[] objs = (Object[]) namelist.get(s);

			String key = (objs[0] == null ? "" : objs[0].toString()) + "@"
					+ ((objs[1] == null ? "" : objs[1].toString()));

			keyMap.put(key, objs[2] == null ? 0.0 : Double.valueOf(objs[2]
					.toString()));
		}

		for (int i = 0; i < list.size(); i++) {
			// System.out.println("--->" + i);
			BcsInnerMerge bcsinnerMerge = (BcsInnerMerge) list.get(i);
			if (bcsinnerMerge.getMateriel() == null
					|| bcsinnerMerge.getMateriel().getCalUnit() == null
					|| bcsinnerMerge.getBcsTenInnerMerge() == null
					|| bcsinnerMerge.getBcsTenInnerMerge().getComUnit() == null) {
				continue;
			}
			CalUnit materielcalUnit = (CalUnit) bcsinnerMerge.getMateriel()
					.getCalUnit();
			Unit bcsTenInnerMergeUnit = (Unit) bcsinnerMerge
					.getBcsTenInnerMerge().getComUnit();
			String mname = materielcalUnit.getName() == null ? ""
					: materielcalUnit.getName();
			String bgName = bcsTenInnerMergeUnit.getName() == null ? ""
					: bcsTenInnerMergeUnit.getName();
			// 如果物料的计量单位 = 10位商品编码的单位
			if (mname.equals(bgName)) {
				bcsinnerMerge.setUnitConvert(1.0);
			} else {

				String nkey = mname + "@" + bgName;
				Double convert = keyMap.get(nkey);
				EnterpriseMaterial em = materialManageDao
						.findEnterpriseMaterial(bcsinnerMerge.getMateriel()
								.getPtNo());
				// 如果没有设置单位折算比
				if (convert == null || convert == 0) {
					// 如果物料单位折算比 != 0,null
					if (bcsinnerMerge.getUnitConvert() != null
							&& bcsinnerMerge.getUnitConvert() != 0) {
						// 更新到物料主档
						em.setUnitConvert(bcsinnerMerge.getUnitConvert());
						// 更新到常用工厂物料
						bcsinnerMerge.getMateriel().setUnitConvert(
								bcsinnerMerge.getUnitConvert());

						materialManageDao.saveEnterpriseMaterial(em);
						materialManageDao.saveMateriel(bcsinnerMerge
								.getMateriel());
					} else {
						if (em != null && em.getUnitConvert() != null
								&& em.getUnitConvert() != 0) {
							bcsinnerMerge.setUnitConvert(em.getUnitConvert());
						}
					}
				} else {
					bcsinnerMerge.setUnitConvert(convert);
					// 更新到物料主档
					em.setUnitConvert(convert);
					// 更新到常用工厂物料
					bcsinnerMerge.getMateriel().setUnitConvert(convert);

					materialManageDao.saveEnterpriseMaterial(em);
					materialManageDao.saveMateriel(bcsinnerMerge.getMateriel());
				}

			}

			this.bcsInnerMergeDao.saveBcsInnerMerge(bcsinnerMerge);
		}
	}

	/**
	 * 返回所有计量单位
	 * 
	 * @param materielName
	 * @param customsName
	 * @return
	 */
	private Double getConvert(String materielName, String customsName) {
		List list = materialManageDao.findCalUnitByUnitName(materielName,
				customsName);
		if (list != null && list.size() > 0) {
			return ((CalUnit) list.get(0)).getScale();
		}
		return Double.valueOf(0);
	}

	public Map getUnitConverName() {

		return null;
	}

	/**
	 * 查询不在报关单耗成品里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomExg() {
		List lsTenInnerMerge = this.bcsInnerMergeDao
				.findDzscTenInnerMerge(MaterielType.FINISHED_PRODUCT);
		// 检测，过滤已经添加的
		List listExistExgId = this.bcsInnerMergeDao
				.findBcsCustomsBomExgTenSeqNum();
		for (int i = lsTenInnerMerge.size() - 1; i >= 0; i--) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) lsTenInnerMerge
					.get(i);
			if (listExistExgId.contains(bcsInnerMerge.getBcsTenInnerMerge()
					.getSeqNum())) {
				lsTenInnerMerge.remove(i);
			}
		}
		// 检测，过滤重复归并序号的
		List resultList = new ArrayList();
		Integer seqNum = new Integer(-1);
		for (int i = 0; i < lsTenInnerMerge.size(); i++) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) lsTenInnerMerge
					.get(i);
			if (!seqNum.equals(bcsInnerMerge.getBcsTenInnerMerge().getSeqNum())) {
				resultList.add(bcsInnerMerge);
			}
			seqNum = bcsInnerMerge.getBcsTenInnerMerge().getSeqNum();
		}
		return resultList;
	}

	/**
	 * 根据归并增加报关单耗成品
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addBcsCustomsBomExgFromInnerMerge(List lsBcsInnerMerge) {
		// TODO Auto-generated method stub
		List<BcsCustomsBomExg> lsResult = new ArrayList<BcsCustomsBomExg>();
		for (int i = 0; i < lsBcsInnerMerge.size(); i++) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) lsBcsInnerMerge
					.get(i);
			BcsCustomsBomExg bcsCustomsBomExg = new BcsCustomsBomExg();
			bcsCustomsBomExg.setTenSeqNum(bcsInnerMerge.getBcsTenInnerMerge()
					.getSeqNum());
			bcsCustomsBomExg.setComplex(bcsInnerMerge.getBcsTenInnerMerge()
					.getComplex());
			bcsCustomsBomExg.setName(bcsInnerMerge.getBcsTenInnerMerge()
					.getName());
			bcsCustomsBomExg.setSpec(bcsInnerMerge.getBcsTenInnerMerge()
					.getSpec());
			bcsCustomsBomExg.setUnit(bcsInnerMerge.getBcsTenInnerMerge()
					.getComUnit());
			this.bcsInnerMergeDao.saveOrUpdate(bcsCustomsBomExg);
			lsResult.add(bcsCustomsBomExg);

		}
		return lsResult;
	}

	/**
	 * 删除报关单耗成品资料
	 * 
	 * @param dzscCustomsBomExg
	 */
	public void deleteBcsCustomsBomExg(List list) {
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsBomExg bcsCustomsBomExg = (BcsCustomsBomExg) list.get(i);
			this.bcsInnerMergeDao.deleteAll(this.bcsInnerMergeDao
					.findBcsCustomsBomDetail(bcsCustomsBomExg));
			this.bcsInnerMergeDao.delete(bcsCustomsBomExg);
		}
	}

	/**
	 * 根据报关单耗的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exg
	 * @return
	 */
	public List findMaterialExgByCustomsBomExg(BcsCustomsBomExg exg) {
		List lsResult = new ArrayList();
		List lsBcsInnerMerge = this.bcsInnerMergeDao
				.findBcsInnerMergeDataByTenSeqNum(exg.getTenSeqNum(),
						MaterielType.FINISHED_PRODUCT);
		if (lsBcsInnerMerge.size() <= 0) {
			throw new RuntimeException("在归并关系里找不到归并序号为：" + exg.getTenSeqNum()
					+ "的归并");
		}

		for (int i = 0; i < lsBcsInnerMerge.size(); i++) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) lsBcsInnerMerge
					.get(i);
			BcsProductScaleInfo info = new BcsProductScaleInfo();
			// 最大报关常用bom版本信息
			MaterialBomVersion mbv = materialManageDao.findMaxMaterielBomVersionByPtNo(bcsInnerMerge.getMateriel().getPtNo());
			info.setBcsCustomsBomExg(exg);
			info.setParentNo(bcsInnerMerge.getMateriel().getPtNo());
			info.setUnitConvert(bcsInnerMerge.getUnitConvert());
			info.setAmount(100d);
			if(mbv != null) {
				info.setVersion(mbv.getVersion() == null ? "0" : mbv
						.getVersion().toString());
				info.setEndDate(mbv.getEndDate());
			} else {
				info.setVersion("0");
			}
			
			lsResult.add(info);
		}

		return lsResult;
	}

	/**
	 * 自动计算报关单耗
	 * 
	 * @param list
	 * @return
	 */
	/*
	 * public List autoCalBcsCustomsBom(List list, BcsCustomsBomExg exg) { List
	 * lsResult = new ArrayList(); // key = 报关料件归并序号 Map<Integer,
	 * BcsCustomsBomDetail> hmEmsUnitUsedInfo = new HashMap<Integer,
	 * BcsCustomsBomDetail>();// 归并后单耗 for (int i = 0; i < list.size(); i++) {
	 * BcsProductScaleInfo info = (BcsProductScaleInfo) list.get(i); if
	 * (info.getAmount() == null || info.getAmount() <= 0) { continue; } // 商品料号
	 * String ptNo = info.getParentNo(); System.out.println("商品料号:"+ptNo); //
	 * 获取料号对应的报关常用工厂BOM料件第一个版本资料 List<MaterialBomDetail> lsDetail =
	 * this.bcsInnerMergeDao .findMaterialBomDetail(ptNo);
	 * System.out.println("第一个版本资料:" + lsDetail.size()); for (MaterialBomDetail
	 * materialBomDetail : lsDetail) { // 料件料号 String subPtNo =
	 * materialBomDetail.getMateriel().getPtNo(); // 料号对应的报关归并关系
	 * List<BcsInnerMerge> lsBcsInnerMerge = this.bcsInnerMergeDao
	 * .findBcsInnerMergeDataByTenPtNo(subPtNo, MaterielType.MATERIEL);
	 * System.out.println("归并关系：" + lsBcsInnerMerge.size()); if
	 * (lsBcsInnerMerge.size() == 0) {// 料号没有做对应 continue; } BcsInnerMerge
	 * bcsInnerMerge = lsBcsInnerMerge.get(0); // 报关bom BcsCustomsBomDetail
	 * customsBomDetail = null; if
	 * (hmEmsUnitUsedInfo.get(bcsInnerMerge.getBcsTenInnerMerge() .getSeqNum())
	 * != null) { customsBomDetail = hmEmsUnitUsedInfo.get(bcsInnerMerge
	 * .getBcsTenInnerMerge().getSeqNum()); } else { customsBomDetail = new
	 * BcsCustomsBomDetail(); customsBomDetail.setBcsCustomsBomExg(exg);
	 * customsBomDetail.setTenSeqNum(bcsInnerMerge
	 * .getBcsTenInnerMerge().getSeqNum());
	 * customsBomDetail.setComplex(bcsInnerMerge
	 * .getBcsTenInnerMerge().getComplex());
	 * customsBomDetail.setName(bcsInnerMerge .getBcsTenInnerMerge().getName());
	 * customsBomDetail.setSpec(bcsInnerMerge .getBcsTenInnerMerge().getSpec());
	 * customsBomDetail.setUnit(bcsInnerMerge
	 * .getBcsTenInnerMerge().getComUnit()); // //
	 * customsBomDetail.setCustomsNo(bcsInnerMerge //
	 * .getBcsTenInnerMerge().getCustomsNo());
	 * hmEmsUnitUsedInfo.put(bcsInnerMerge.getBcsTenInnerMerge() .getSeqNum(),
	 * customsBomDetail); } // 折算比例 double exgScale =
	 * CommonUtils.getDoubleExceptNull(bcsInnerMerge .getUnitConvert()); //对应比例
	 * double scale = 0; if (exgScale != 0) { scale = (CommonUtils
	 * .getDoubleExceptNull(bcsInnerMerge.getUnitConvert()) CommonUtils
	 * .getDoubleExceptNull(info.getScale())) / exgScale; }
	 * customsBomDetail.setUnitWare(CommonUtils
	 * .getDoubleExceptNull(customsBomDetail.getUnitWare()) +
	 * CommonUtils.getDoubleExceptNull(materialBomDetail .getUnitWaste())
	 * scale); // customsBomDetail.setWare(CommonUtils //
	 * .getDoubleExceptNull(customsBomDetail.getWare()) // +
	 * CommonUtils.getDoubleExceptNull(materialBomDetail // .getWaste()) scale);
	 * customsBomDetail.setUnitDosage(CommonUtils
	 * .getDoubleExceptNull(customsBomDetail.getUnitDosage()) +
	 * CommonUtils.getDoubleExceptNull(materialBomDetail .getUnitUsed()) scale);
	 * System.out.println(materialBomDetail.getUnitWaste());
	 * System.out.println(exgScale); System.out.println(scale); } }
	 * Collection<BcsCustomsBomDetail> details = hmEmsUnitUsedInfo.values();
	 * System.out.println("结果：" + details.size()); for (BcsCustomsBomDetail
	 * detail : details) { // bcsInnerMergeDao.saveOrUpdate(detail);
	 * detail.setUnitDosage(CommonUtils.getDoubleByDigit(detail
	 * .getUnitDosage(), 6));
	 * detail.setUnitWare(CommonUtils.getDoubleByDigit(detail .getUnitWare(),
	 * 6)); // 计算损耗 if (detail.getUnitWare() != 0) {
	 * detail.setWare(CommonUtils.getDoubleByDigit((detail .getUnitDosage() -
	 * detail.getUnitWare()) / detail.getUnitDosage() 100, 6)); } else {
	 * detail.setWare(0d); } lsResult.add(detail); } return lsResult; }
	 */

	public List autoCalBcsCustomsBom(List list, BcsCustomsBomExg exg, int bomStructureType) {
		List lsResult = new ArrayList();
		Map<String, Double> hmMaterialBomUnitWaste = new HashMap<String, Double>();// 归并前单耗总数量
		Map<String, Double> hmMaterialBomUnitUsed = new HashMap<String, Double>();// 归并前单项用量总数量
		Map<Integer, Double> hmEmsUnitWasteAmount = new HashMap<Integer, Double>();// 归并后单耗总数量
		Map<Integer, Double> hmEmsUnitUsedAmount = new HashMap<Integer, Double>();// 归并后单项用量总数量
		Map<Integer, BcsCustomsBomDetail> hmEmsUnitUsedInfo = new HashMap<Integer, BcsCustomsBomDetail>();// 归并后单耗
		for (int i = 0; i < list.size(); i++) {
			BcsProductScaleInfo info = (BcsProductScaleInfo) list.get(i);
			if (info.getAmount() == null || info.getAmount() <= 0) {
				continue;
			}
			Double exgAmount = 100.0;
			// CommonUtils
			// .getDoubleExceptNull(info.getAmount());
			String ptNo = info.getParentNo();
			Integer version = info.getVersion() == null ? 0 : Integer.parseInt( info.getVersion());
			List lsDetail = null;
			if(bomStructureType == BomStructureType.HAVE_VERSION_NO_DATE 
					|| bomStructureType == BomStructureType.NO_VERSION_NO_DATE) {
				lsDetail = this.bcsInnerMergeDao.findMaterialBomDetail(ptNo, version, null);
			} else {
				lsDetail = this.bcsInnerMergeDao.findMaterialBomDetail(ptNo, null, info.getEndDate());
			}
			
			System.out.println("----lsDetail=--" + lsDetail.size());

			for (int j = 0; j < lsDetail.size(); j++) {
				MaterialBomDetail materialBomDetail = (MaterialBomDetail) lsDetail
						.get(j);
				String subPtNo = materialBomDetail.getMateriel().getPtNo();
				//oldUnitWaste单耗
				Double oldUnitWaste = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitWaste
								.get(subPtNo));
				//oldUnitUsed 单项用量
				Double oldUnitUsed = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitUsed.get(subPtNo));
				
				hmMaterialBomUnitWaste.put(subPtNo, oldUnitWaste
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(materialBomDetail
								.getUnitWaste()));
				hmMaterialBomUnitUsed.put(subPtNo, oldUnitUsed
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(materialBomDetail
								.getUnitUsed()));
			}
		}
		Set<String> hsPtNo = hmMaterialBomUnitWaste.keySet();
		Iterator itDetail = hsPtNo.iterator();
		while (itDetail.hasNext()) {
			String detailPtNo = itDetail.next().toString();
			BcsInnerMerge innerMergeData = this.bcsInnerMergeDao
					.findBcsInnerMergeByTenPtNo(detailPtNo,
							MaterielType.MATERIEL);
			if (innerMergeData == null
					|| innerMergeData.getBcsTenInnerMerge() == null) {// 料号没有做对应
				continue;
			}
			Integer tenSeqNum = innerMergeData.getBcsTenInnerMerge()
					.getSeqNum();
			BcsCustomsBomDetail bomBill = new BcsCustomsBomDetail();
			Double value = 0.0;
			if (hmEmsUnitUsedInfo.get(tenSeqNum) == null) {
				BcsTenInnerMerge tenInnerMerge = innerMergeData
						.getBcsTenInnerMerge();
				bomBill.setBcsCustomsBomExg(exg);
				bomBill.setTenSeqNum(tenInnerMerge.getSeqNum());
				bomBill.setComplex(tenInnerMerge.getComplex());
				bomBill.setName(tenInnerMerge.getName());
				bomBill.setSpec(tenInnerMerge.getSpec());
				bomBill.setUnit(tenInnerMerge.getComUnit());
				hmEmsUnitUsedInfo.put(tenInnerMerge.getSeqNum(), bomBill);
			} else {
				bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
			}
			// 报关单项用量=，折算系数1*工厂单项用量1+，折算系数2*工厂单项用量2……
			// 报关单耗=折算系数1*工厂单耗1+折算系数2*工厂单耗2……
			// 这里先放折算系数
			value = innerMergeData.getUnitConvert() == null ? 0.0
					: innerMergeData.getUnitConvert();
			Double oldEmsUnitWaste = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double oldEmsUnitUsed = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			// 这里put进MAP时乘以折算系数
			hmEmsUnitWasteAmount.put(tenSeqNum, oldEmsUnitWaste
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitWaste
							.get(detailPtNo)
							* value));
			hmEmsUnitUsedAmount.put(tenSeqNum, oldEmsUnitUsed
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitUsed
							.get(detailPtNo)
							* value));
		}
		Iterator itBomImgSeqNum = hmEmsUnitUsedInfo.keySet().iterator();
		Double totalExgAmount = 100.0;
		// CommonUtils.getDoubleExceptNull(exg
		// .getAmount());
		while (itBomImgSeqNum.hasNext()) {
			Integer tenSeqNum = Integer.valueOf(itBomImgSeqNum.next()
					.toString());
			BcsCustomsBomDetail bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
			System.out.println("bomBill.getUniware=" + bomBill.getUnitWare());
			if (bomBill == null) {
				continue;
			}
			Double unitWasteAmount = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double unitUsedAmount = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			Double unitWaste = (totalExgAmount == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(unitWasteAmount / totalExgAmount, 6));
			Double unitUsed = (totalExgAmount == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(unitUsedAmount / totalExgAmount, 6));
			// Double waste = (unitWaste == 0 ? 0.0 : CommonUtils
			// .getDoubleByDigit(
			// ((unitUsed - unitWaste) / unitWaste) * 100, 6));
			// 先计算单耗跟单项用量，再计算损耗率
			Double waste = (unitWaste == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(
							((unitUsed - unitWaste) / unitUsed) * 100, 6));
			bomBill.setUnitWare(unitWaste);
			bomBill.setUnitDosage(unitUsed);
			bomBill.setWare(waste);

		}
		lsResult.addAll(hmEmsUnitUsedInfo.values());
		System.out.println("lsResult.size()" + lsResult.size());
		return lsResult;
	}

	/**
	 * 删除报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public void deleteBcsCustomsBomDetail(List list) {
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsBomDetail bcsCustomsBomDetail = (BcsCustomsBomDetail) list
					.get(i);
			this.bcsInnerMergeDao.delete(bcsCustomsBomDetail);
		}
	}

	public List findInnerMergeNotInCustomsBomDetail(
			BcsCustomsBomExg bcsCustomsBomExg) {
		List lsTenInnerMerge = this.bcsInnerMergeDao
				.findDzscTenInnerMerge(MaterielType.MATERIEL);
		List listExistDetailId = this.bcsInnerMergeDao
				.findBcsCustomsBomDetailTenSeqNum(bcsCustomsBomExg);
		for (int i = lsTenInnerMerge.size() - 1; i >= 0; i--) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) lsTenInnerMerge
					.get(i);
			if (listExistDetailId.contains(bcsInnerMerge.getBcsTenInnerMerge()
					.getSeqNum())) {
				lsTenInnerMerge.remove(i);
			}
		}
		return lsTenInnerMerge;
	}

	public List addBcsCustomsBomDetailFromInnerMerge(
			BcsCustomsBomExg bcsCustomsBomExg, List lsTenInnerMerge) {
		List<BcsCustomsBomDetail> lsResult = new ArrayList<BcsCustomsBomDetail>();
		for (int i = 0; i < lsTenInnerMerge.size(); i++) {
			BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) lsTenInnerMerge
					.get(i);
			BcsCustomsBomDetail bcsCustomsBomDetail = new BcsCustomsBomDetail();

			bcsCustomsBomDetail.setBcsCustomsBomExg(bcsCustomsBomExg);
			bcsCustomsBomDetail.setTenSeqNum(bcsInnerMerge
					.getBcsTenInnerMerge().getSeqNum());
			bcsCustomsBomDetail.setComplex(bcsInnerMerge.getBcsTenInnerMerge()
					.getComplex());
			bcsCustomsBomDetail.setName(bcsInnerMerge.getBcsTenInnerMerge()
					.getName());
			bcsCustomsBomDetail.setSpec(bcsInnerMerge.getBcsTenInnerMerge()
					.getSpec());
			bcsCustomsBomDetail.setUnit(bcsInnerMerge.getBcsTenInnerMerge()
					.getComUnit());
			this.bcsInnerMergeDao.saveOrUpdate(bcsCustomsBomDetail);
			lsResult.add(bcsCustomsBomDetail);
		}
		return lsResult;
	}

	/**
	 * 保存自动计算的报关单耗
	 * 
	 * @param list
	 * @param exg
	 */
	public void saveAutoCalBcsCustomsBomDetail(List list, BcsCustomsBomExg exg) {
		this.bcsInnerMergeDao.deleteAll(this.bcsInnerMergeDao
				.findBcsCustomsBomDetail(exg));
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsBomDetail bomBill = (BcsCustomsBomDetail) list.get(i);
			// bomBill.setBcsCustomsBomExg(exg);
			this.bcsInnerMergeDao.saveOrUpdate(bomBill);
		}

	}

	/**
	 * 转化报关Bom 2010-06月-08hcl修改
	 */
	public List converBom(ContractExg exg, List list) {
		ContractBom cb = null;
		List lists = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsBomDetail bsb = (BcsCustomsBomDetail) list.get(i);
			cb = new ContractBom();
			String name = bsb.getName() == null ? "" : bsb.getName();
			String spec = bsb.getSpec() == null ? "" : bsb.getSpec();
			Complex complex = bsb.getComplex();
			Double unitWare = bsb.getUnitWare() == null ? 0.0 : bsb
					.getUnitWare();
			Double ware = bsb.getWare() == null ? 0.0 : bsb.getWare();
			Double amount = exg.getExportAmount() == null ? 0.0 : exg
					.getExportAmount()
					* (bsb.getUnitDosage() == null ? 0.0 : bsb.getUnitDosage());
			Double unitDosage = bsb.getUnitDosage() == null ? 0.0 : bsb
					.getUnitDosage();
			Unit unit = bsb.getUnit();
			// 取已在合同中料件的序号
			List SeqList = this.bcsInnerMergeDao.getImgSeqNum(bsb, exg);
			Integer seqNum = null;
			System.out.println("SeqList.size()" + SeqList.size());
			if (SeqList.size() != 0)
				if (SeqList != null && null != SeqList.get(0))
					seqNum = (Integer) (SeqList.get(0));
			cb.setContractImgSeqNum(seqNum == null ? 0 : seqNum);
			cb.setComplex(complex);
			cb.setContractExg(exg);
			cb.setName(name);
			cb.setSpec(spec);
			cb.setUnit(unit);
			cb.setUnitWaste(unitWare);
			cb.setWaste(ware);
			cb.setUnitDosage(unitDosage);
			cb.setAmount(amount);
			cb.setModifyMark(ModifyMarkState.ADDED);
			this.bcsInnerMergeDao.saveOrUpdate(cb);
			// cb.set
			lists.add(cb);
		}
		return lists;
	}

	/**
	 * 保存工厂与物料对应表 同时反写物料物档与单据中心对应表中的单价，单重，单位折算系统
	 * 
	 * @param dim
	 */
	public Materiel saveBcsFactoryCustom(BcsInnerMerge dim) {

		// /更物料主物中的物料
		// 更新物料主档单位折算系数
		Materiel materiel = dim.getMateriel();
		materiel.setUnitConvert(dim.getUnitConvert());
//		materiel = materialManageLogic.saveMateriel(materiel);
		bcsInnerMergeDao.saveOrUpdateDirect(materiel);
		BcsTenInnerMerge bcsTenInnerMerge = dim.getBcsTenInnerMerge();
		bcsInnerMergeDao.saveOrUpdate(bcsTenInnerMerge);
		bcsInnerMergeDao.saveBcsInnerMerge(dim);
		return materiel;
	}
	
	
	/**
	 * 设置 bcsInnerMerges 为统计使用true,相同料号的其他对应设置为false
	 * @param bcsInnerMerges
	 * @return
	 */
	public List<BcsInnerMerge> saveBcsInnerMergeIsCounts(List<BcsInnerMerge> bcsInnerMerges) {
		BcsInnerMerge bcsInnerMerge = null;
		
		Set<String> ptNoSet = new HashSet<String>();
		Set<String> idSet = new HashSet<String>();
		String ptNo = null;
		StringBuilder errPtNo = new StringBuilder();
		for (int i = 0; i < bcsInnerMerges.size(); i++) {
			bcsInnerMerge = bcsInnerMerges.get(i);
			ptNo = bcsInnerMerge.getMateriel().getPtNo();
			
			if(!ptNoSet.contains(ptNo)) {
				ptNoSet.add(ptNo);
			} else {
				errPtNo.append(ptNo + ",");
			}
			
			idSet.add(bcsInnerMerge.getId());
		}
		
		if(errPtNo.length() > 0) {
			throw new RuntimeException(errPtNo.toString() + "同一料号不能设置多个当前使用的归并序号！");//工厂料号相同且【当前正在使用】的，不允许设置不同的归并序号统计
		}
		
		List<BcsInnerMerge> list = bcsInnerMergeDao.findBcsInnerMergeByPtNoSet(ptNoSet);
		for (int i = 0; i < list.size(); i++) {
			bcsInnerMerge = list.get(i);
			if(idSet.contains(bcsInnerMerge.getId())) {
				bcsInnerMerge.setIsUsing(true);
			} else {
				bcsInnerMerge.setIsUsing(false);
			}
		}
		
		bcsInnerMergeDao.batchSaveOrUpdate(list);
		
		return bcsInnerMerges;
	}
	
	/**
	 * 导入物料与报关对应表来自通关手册料件与成品
	 *  商品编码 + 商品名称 + 商品规格 + 计量单位 为Key进行重复导入判断
	 * @param materielType 物料类型
	 */
	public Integer importInnerMergeDataFromContract(String materielType,BcsDictPorHead bcsDictPorHead){		
		/**获取存在的报关商品信息判断重复Key (商品编码 +'/'+ 商品名称 +'/'+商品规格 +'/'+ 计量单位(code))**/
		List<String> keys = getBcsTenInnerMergeKeys(materielType);		
		/** 根据物料类别获取通关手册中的物料信息 **/
		List<?> materielLs = bcsInnerMergeDao.findContractMateriel(materielType,bcsDictPorHead);				
		List<BcsTenInnerMerge> resultLs = new ArrayList<BcsTenInnerMerge>();
		if(MaterielType.FINISHED_PRODUCT.equals(materielType)){	//成品
			for(int i = 0 ;i < materielLs.size();i++){
				addContractExgToTenInnerMergeList(keys, (BcsDictPorExg)materielLs.get(i), resultLs);
			}
		}else if(MaterielType.MATERIEL.equals(materielType)){	//料件			
			for(int i = 0 ;i < materielLs.size();i++){
				addContractImgToTenInnerMergeList(keys, (BcsDictPorImg)materielLs.get(i), resultLs);
			}
		}		
		//保存数据
		batchSaveBcsTenInnerMerges(resultLs,materielType);
		return resultLs.size();
	}
	
	/**
	 * 保存报关商品资料
	 * 
	 * @param list
	 *            是BcsTenInnerMerge型，报关商品信息
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List<BcsTenInnerMerge> batchSaveBcsTenInnerMerges(List<BcsTenInnerMerge> list, String materielType) {
		BcsTenInnerMerge b = null;
		Integer maxvalue = this.bcsInnerMergeDao.getMaxTenBcsInnerMergeNo(materielType);
		for (int i = 0; i < list.size(); i++) {
			b = list.get(i);
			b.setSeqNum(maxvalue + i+1);
			this.bcsInnerMergeDao.saveBcsTenInnerMerge(b);
		}
		return list;
	}
	/**
	 * 添加通关手册成品到报关商品 根据Key值，相同的不重复添加
	 * @param keys
	 * @param exg
	 * @param resultLs
	 */
	private void addContractExgToTenInnerMergeList(List<String> keys,BcsDictPorExg exg,List<BcsTenInnerMerge> resultLs){
		//Key (商品编码 +'/'+ 商品名称 +'/'+商品规格 +'/'+ 计量单位(code))
		String key = (exg.getComplex() == null ? "" : exg.getComplex().getCode())+"/"+exg.getCommName()+"/"+exg.getCommSpec()+"/"+ (exg.getComUnit() == null ? "" : exg.getComUnit().getCode());
		if(keys.contains(key))
			return;
		BcsTenInnerMerge btim = new BcsTenInnerMerge();
//		btim.setCountry(exg.getCountry());
		btim.setComUnit(exg.getComUnit());
		btim.setComplex(exg.getComplex());
		btim.setScmCoi(MaterielType.FINISHED_PRODUCT);
		btim.setName(exg.getCommName());
		btim.setSpec(exg.getCommSpec());
		btim.setCompany(CommonUtils.getCompany());
		resultLs.add(btim);
		keys.add(key);
	}
	/**
	 * 添加通关手册料件到报关商品 根据Key值，相同的不重复添加
	 * @param keys
	 * @param img
	 * @param resultLs
	 */
	private void addContractImgToTenInnerMergeList(List<String> keys,BcsDictPorImg img,List<BcsTenInnerMerge> resultLs){
		//Key (商品编码 +'/'+ 商品名称 +'/'+商品规格 +'/'+ 计量单位(code))
		String key = (img.getComplex() == null ? "" : img.getComplex().getCode())+"/"+img.getCommName()+"/"+img.getCommSpec()+"/"+ (img.getComUnit() == null ? "" : img.getComUnit().getCode());
		if(keys.contains(key))
			return;
		BcsTenInnerMerge btim = new BcsTenInnerMerge();
//		btim.setCountry(img.getCountry());
		btim.setComUnit(img.getComUnit());
		btim.setComplex(img.getComplex());
		btim.setScmCoi(MaterielType.MATERIEL);
		btim.setName(img.getCommName());
		btim.setSpec(img.getCommSpec());
		btim.setCompany(CommonUtils.getCompany());
		resultLs.add(btim);
		keys.add(key);
	}
	/**
	 * 获取存在的报关商品信息判断重复Key (商品编码 +'/'+ 商品名称 +'/'+商品规格 +'/'+ 计量单位(code))
	 * @param materielType
	 * @return
	 */
	private List<String> getBcsTenInnerMergeKeys(String materielType){
		List<BcsTenInnerMerge> btimLs = bcsInnerMergeDao.findBcsTenInnerMerge(materielType);	
		List<String> resultLs = new ArrayList<String>(btimLs.size());
		for(BcsTenInnerMerge t : btimLs){
			resultLs.add((t.getComplex() == null ? "" : t.getComplex().getCode())+"/"+t.getName()+"/"+t.getSpec()+"/"+ (t.getComUnit() == null ? "" : t.getComUnit().getCode()));
		}
		return resultLs;
	}
}
