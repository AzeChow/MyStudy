/*
 * Created on 2005-4-1
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bls.dao.BlsInnerMergeDao;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * com.bestway.bls.blsinnermerge.logic.BlsInnerMergeLogic
 * 
 * @author ls checked by 陈井彬 date 2008.11.29 内部归并LOGIC
 */
public class BlsInnerMergeLogic {
	/**
	 * 内部归并DAO接口
	 */
	private BlsInnerMergeDao blsInnerMergeDao = null;

	/**
	 * 工厂通用代码dao
	 */
	private MaterialManageDao materialManageDao;

	// /**
	// * 合同操作LOGIC
	// */
	// private ContractLogic contractLogic = null;

	/**
	 * 获取blsInnerMergeDao
	 * 
	 * @return blsInnerMergeDao
	 */
	public BlsInnerMergeDao getBlsInnerMergeDao() {
		return blsInnerMergeDao;
	}

	// /**
	// * 获取contractLogic
	// *
	// * @return contractLogic
	// */
	// public ContractLogic getContractLogic() {
	// return contractLogic;
	// }
	//
	// /**
	// * 设置contractLogic
	// *
	// * @param contractLogic
	// */
	// public void setContractLogic(ContractLogic contractLogic) {
	// this.contractLogic = contractLogic;
	// }

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
	 * 设置blsInnerMergeDao
	 * 
	 * @param blsInnerMergeDao
	 */
	public void setBlsInnerMergeDao(BlsInnerMergeDao blsInnerMergeDao) {
		this.blsInnerMergeDao = blsInnerMergeDao;
	}

	/**
	 * 自动从报关常用物料中导入资料
	 * 
	 * @param materielType
	 *            物料类型
	 */
	public void importInnerMergeDataFromMateriel() { // String materielType
		List list = this.blsInnerMergeDao.findMaterielForBlsInnerMerge(-1, -1);// materielType,
		for (int i = 0; i < list.size(); i++) {
			Materiel materiel = (Materiel) list.get(i);
			BlsInnerMerge b = new BlsInnerMerge();
			b.setCompany(materiel.getCompany());
			b.setMateriel(materiel);
			// b.setMaterielType(materielType);
			this.blsInnerMergeDao.saveBlsInnerMerge(b);
			/**
			 * 使用为真
			 */
			// materiel.setIsUseInBlsInnerMerge(true);
			this.blsInnerMergeDao.saveOrUpdate(materiel);
		}
	}

	/**
	 * 导入物料与报关对应表来自报关常用物料
	 * 
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List importInnerMergeDataFromMateriel(List materielList) {
		List<BlsInnerMerge> returnList = new ArrayList<BlsInnerMerge>();
		for (int i = 0; i < materielList.size(); i++) {
			Materiel materiel = (Materiel) materielList.get(i);
			/**
			 * 使用为真
			 */
			// materiel.setIsUseInBlsInnerMerge(true);
			this.blsInnerMergeDao.saveOrUpdate(materiel);

			BlsInnerMerge b = new BlsInnerMerge();
			b.setCompany(materiel.getCompany());
			// b.setMaterielType(materielType);
			b.setMateriel(materiel);
			this.blsInnerMergeDao.saveBlsInnerMerge(b);
			returnList.add(b);
		}
		return returnList;
	}

	/**
	 * 删除物料与报关对应表里的数据
	 * 
	 * @param blsInnerMerge
	 *            物料与报关对应表里的数据
	 */
	public void deleteBlsInnerMerge(BlsInnerMerge blsInnerMerge) {
		Materiel materiel = blsInnerMerge.getMateriel();
		// materiel.setIsUseInBlsInnerMerge(false);
		this.blsInnerMergeDao.saveOrUpdate(materiel);
		System.out.println(" materiel ----------------- ");
		this.blsInnerMergeDao.deleteBlsInnerMerge(blsInnerMerge);
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param blsInnerMergeList
	 *            物料与报关对应表里的数据
	 */
	public void deleteBlsInnerMerge(List<BlsInnerMerge> blsInnerMergeList) {
		for (BlsInnerMerge blsInnerMerge : blsInnerMergeList) {
			this.deleteBlsInnerMerge(blsInnerMerge);
		}
	}

	/**
	 * 自动归并,名称，规格，编码，单位相同
	 * 
	 * @param materielType
	 *            物料类型
	 */
	public void blsAutoMerge() {// String materielType
		List list = this.blsInnerMergeDao.findBlsInnerMerge();
		int maxTenInnerMergeNo = this.blsInnerMergeDao
				.getMaxTenBlsInnerMergeNo();
		Map<String, BlsInnerMerge> map = new HashMap<String, BlsInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge blsInnerMerge = (BlsInnerMerge) list.get(i);
			BlsTenInnerMerge b = blsInnerMerge.getBlsTenInnerMerge();
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
				map.put(key, blsInnerMerge);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge blsInnerMerge = (BlsInnerMerge) list.get(i);
			BlsTenInnerMerge blsTenInnerMerge = blsInnerMerge
					.getBlsTenInnerMerge();
			Materiel m = blsInnerMerge.getMateriel();
			String key = (m.getFactoryName() == null ? "" : m.getFactoryName())
					+ (m.getFactorySpec() == null ? "" : m.getFactorySpec())
					+ (m.getPtUnit() == null ? "" : m.getPtUnit().getCode())
					+ (m.getComplex() == null ? "" : m.getComplex().getCode());
			BlsInnerMerge tempB = map.get(key);
			if (tempB == null) {
				BlsTenInnerMerge b = new BlsTenInnerMerge();
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
				// b.setScmCoi(materielType);
				this.blsInnerMergeDao.saveBlsTenInnerMerge(b);
				blsInnerMerge.setBlsTenInnerMerge(b);
				this.blsInnerMergeDao.saveBlsInnerMerge(blsInnerMerge);
				map.put(key, blsInnerMerge);
			} else {
				blsInnerMerge.setBlsTenInnerMerge(tempB.getBlsTenInnerMerge());
				this.blsInnerMergeDao.saveBlsInnerMerge(blsInnerMerge);
			}
		}

	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	public void saveBlsInnerMerge(List list, BlsTenInnerMerge tempB) {
		if (list.size() < 1) {
			return;
		}
		List<BlsInnerMerge> lsBlsInnerMerge = this.blsInnerMergeDao
				.findBlsInnerMergeByTenInnerMerge(tempB);
		List<String> lsMaterielPtno = new ArrayList<String>();
		for (BlsInnerMerge blsInnerMerge : lsBlsInnerMerge) {
			lsMaterielPtno.add(blsInnerMerge.getMateriel() == null ? ""
					: blsInnerMerge.getMateriel().getPtNo());
		}
		String errInfo = "";
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge b = (BlsInnerMerge) list.get(i);
			String materielPtNo = (b.getMateriel() == null ? "" : b
					.getMateriel().getPtNo());
			if (!"".equals(materielPtNo.trim())
					&& lsMaterielPtno.contains(materielPtNo)) {
				errInfo += (materielPtNo + ";");
			}
		}
		if (!"".equals(errInfo.trim())) {
			throw new RuntimeException("以下料号\n" + errInfo + "\n已经和归并序号 "
					+ tempB.getSeqNum() + "进行归并关系对应，所以不能重复归并！");
		}
		this.blsInnerMergeDao.saveBlsTenInnerMerge2(tempB);
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge b = (BlsInnerMerge) list.get(i);

			/**
			 * 是修改
			 */
			if (b.getBlsTenInnerMerge() != null) {
				if (b.getBlsTenInnerMerge().getSeqNum().intValue() != tempB
						.getSeqNum().intValue()) {
					continue;
				}
			}
			b.setBlsTenInnerMerge(tempB);
			this.blsInnerMergeDao.saveBlsInnerMerge(b);
		}
	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	public void updateBlsInnerMerge(List list, BlsTenInnerMerge tempB) {
		if (list.size() < 1) {
			return;
		}
		this.blsInnerMergeDao.saveBlsTenInnerMerge2(tempB);
		// String materielType = ((BlsInnerMerge)
		// list.get(0)).getMaterielType();
		List<BlsInnerMerge> updateList = new ArrayList<BlsInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge b = (BlsInnerMerge) list.get(i);
			if (b.getBlsTenInnerMerge().getSeqNum() != null) { // 是修改
				if (b.getBlsTenInnerMerge().getSeqNum().intValue() != tempB
						.getSeqNum().intValue()) {
					continue;
				}
				updateList.add(b);
			}
			b.setBlsTenInnerMerge(tempB);
			this.blsInnerMergeDao.saveBlsInnerMerge(b);
		}
		// /**
		// * 获得将要同步的合同记录(成品，料件，单耗)
		// */
		// List tempList = this.blsInnerMergeDao
		// .findBlsInnerMergeInContract(tempB);
		//
		// if (MaterielType.MATERIEL.equals(materielType)) {
		// for (int i = 0; i < tempList.size(); i++) {
		// ContractImg c = (ContractImg) tempList.get(i);
		// c.setName(tempB.getName());
		// c.setSpec(tempB.getSpec());
		// c.setComplex(tempB.getComplex());
		// c.setUnit(tempB.getComUnit());
		// /**
		// * 同步的合同记录(料件，单耗)
		// */
		// contractLogic.saveContractImg(c);
		// }
		// } else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
		// for (int i = 0; i < tempList.size(); i++) {
		// ContractExg c = (ContractExg) tempList.get(i);
		// c.setName(tempB.getName());
		// c.setSpec(tempB.getSpec());
		// c.setComplex(tempB.getComplex());
		// c.setUnit(tempB.getComUnit());
		// /**
		// * 同步的合同记录(成品)
		// */
		// contractLogic.saveContractExg(c);
		// }
		// }//修改内部归并不需要同步合同 by kcb

	}

	/**
	 * 取消物料与报关对应表里的10码归并
	 * 
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 */
	public void unDoTenInnerMerge(List list, boolean isDeleteTenInnerMerge) {
		Map tenInnerMergeMap = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge b = (BlsInnerMerge) list.get(i);
			BlsTenInnerMerge tenInnerMerge = b.getBlsTenInnerMerge();
			b.setBlsTenInnerMerge(null);
			this.blsInnerMergeDao.saveBlsInnerMerge(b);
			// 撤消归并时,删除没用的归并后资料
			if (isDeleteTenInnerMerge
					&& blsInnerMergeDao.findBlsInnerMergeByTenInnerMerge(
							tenInnerMerge).isEmpty()) {
				blsInnerMergeDao.delete(tenInnerMerge);
			}
		}
	}

	/**
	 * 物料与报关对应表里十码重排
	 * 
	 * @param selectedRows
	 *            是BlsInnerMerge型，物料与报关对应表
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
		// String type = ((BlsInnerMerge)
		// selectedRows.get(0)).getMaterielType();
		List allRows = this.blsInnerMergeDao.findBlsInnerMerge();// type
		for (int i = 0; i < selectedRows.size(); i++) {
			BlsInnerMerge b = (BlsInnerMerge) selectedRows.get(i);
			int currentValue = b.getBlsTenInnerMerge().getSeqNum().intValue();
			if (i == 0) {
				minNo = b.getBlsTenInnerMerge().getSeqNum().intValue();
				maxNo = minNo;
				ht.put(b.getBlsTenInnerMerge().getSeqNum(), b
						.getBlsTenInnerMerge().getSeqNum());
				continue;
			}
			if (ht.get(b.getBlsTenInnerMerge().getSeqNum()) == null) {
				ht.put(b.getBlsTenInnerMerge().getSeqNum(), b
						.getBlsTenInnerMerge().getSeqNum());
			}
			if (minNo > b.getBlsTenInnerMerge().getSeqNum().intValue()) {
				minNo = b.getBlsTenInnerMerge().getSeqNum().intValue();
			}
			if (maxNo < b.getBlsTenInnerMerge().getSeqNum().intValue()) {
				maxNo = b.getBlsTenInnerMerge().getSeqNum().intValue();
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
			BlsInnerMerge b = (BlsInnerMerge) allRows.get(i);
			if (b.getBlsTenInnerMerge().getSeqNum() == null) {
				continue;
			}
			int currentNo = b.getBlsTenInnerMerge().getSeqNum().intValue();
			if (offset > 0) {// 向上重排
				if (ht.get(b.getBlsTenInnerMerge().getSeqNum()) != null) {// 重排选中的行数据
					for (int k = 0; k < selectedNo.length; k++) {
						if (currentNo == selectedNo[k]) {
							int toRowNo = toNo + k;
							b.getBlsTenInnerMerge().setSeqNum(toRowNo);
						}
					}
					this.blsInnerMergeDao.saveBlsTenInnerMerge(b
							.getBlsTenInnerMerge());
				} else {// 重排未选中的行数据
					if (selectedRowNum == 0) {
						continue;
					}
					if (currentNo >= toNo && currentNo < minNo) {
						b.getBlsTenInnerMerge().setSeqNum(
								currentNo + selectedRowNum);
						this.blsInnerMergeDao.saveBlsTenInnerMerge(b
								.getBlsTenInnerMerge());
					} else if (currentNo > minNo && currentNo < maxNo) {
						minMaxBetweenList.add(b);
					}
				}
			} else { // 向下重排
				if (ht.get(b.getBlsTenInnerMerge().getSeqNum()) != null) {// 重排选中的行数据
					for (int k = 0; k < selectedNo.length; k++) {
						if (currentNo == selectedNo[k]) {
							int toRowNo = toNo - (selectedNo.length - 1 - k);
							b.getBlsTenInnerMerge().setSeqNum(toRowNo);
						}
					}
					this.blsInnerMergeDao.saveBlsTenInnerMerge(b
							.getBlsTenInnerMerge());
				} else {
					if (selectedRowNum == 0) {
						continue;
					}
					if (currentNo <= toNo && currentNo > maxNo) {
						b.getBlsTenInnerMerge().setSeqNum(
								currentNo - selectedRowNum);
						this.blsInnerMergeDao.saveBlsTenInnerMerge(b
								.getBlsTenInnerMerge());
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
					BlsInnerMerge b = (BlsInnerMerge) minMaxBetweenList.get(i);
					int currentNo = b.getBlsTenInnerMerge().getSeqNum()
							.intValue();
					if (currentNo == i) {
						b.getBlsTenInnerMerge().setSeqNum(new Integer(n));
						this.blsInnerMergeDao.saveBlsTenInnerMerge(b
								.getBlsTenInnerMerge());
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
					BlsInnerMerge b = (BlsInnerMerge) minMaxBetweenList.get(i);
					int currentNo = b.getBlsTenInnerMerge().getSeqNum()
							.intValue();
					if (currentNo == i) {
						b.getBlsTenInnerMerge().setSeqNum(new Integer(n));
						this.blsInnerMergeDao.saveBlsTenInnerMerge(b
								.getBlsTenInnerMerge());
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
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	public List saveBlsTenInnerMerge(List list) {// , String materielType
		List lsResult = new ArrayList();
		// BlsParameterSet blsParameterSet = contractLogic.getContractDao()
		// .findBlsParameterSet();// 参数设置
		for (int i = 0; i < list.size(); i++) {
			Complex c = (Complex) list.get(i);
			BlsTenInnerMerge b = new BlsTenInnerMerge();
			Integer maxvalue = this.blsInnerMergeDao.getMaxTenBlsInnerMergeNo();// materielType
			b.setSeqNum(maxvalue + 1);
			// b.setId(String.valueOf(maxvalue + 1));
			b.setComplex(c);
			b.setName(c.getName());
			// b.setSpec(c.getSpec());
			// if (blsParameterSet.getIsControlLength() != null
			// && blsParameterSet.getIsControlLength()) {// 参数设置,是否要控制名称、规格的长度
			// b.setName(CommonUtils.controlLengthByGBK(b.getName(),
			// blsParameterSet.getBytesLength() == null ? 255
			// : blsParameterSet.getBytesLength()));// 名称
			// b.setSpec(CommonUtils.controlLengthByGBK(b.getSpec(),
			// blsParameterSet.getBytesLength() == null ? 255
			// : blsParameterSet.getBytesLength()));// 规格
			// }
			// b.setScmCoi(materielType);
			b.setCompany(CommonUtils.getCompany());
			this.blsInnerMergeDao.saveBlsTenInnerMerge(b);
			lsResult.add(b);
		}
		return lsResult;
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param list
	 *            是BlsTenInnerMerge型，报关商品资料
	 */
	public void deleteBlsTenInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			BlsTenInnerMerge c = (BlsTenInnerMerge) list.get(i);
			this.blsInnerMergeDao.deleteBlsTenInnerMerge(c);
		}
	}

	/**
	 * 删除不在合同备案和物料与报关对应表里的报关商品资料
	 * 
	 * @param list
	 *            是BlsTenInnerMerge型，报关商品资料
	 * @param items
	 *            物料类型
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	public List deleteBlsTenInnerMergeForContract(List list) {// , String items
		List listBlsTenInnerMerge = new Vector();
		List lsContractCredenceNo = null;
		List lsDictPorInnerMergeSeqNum = null;
		// if (MaterielType.FINISHED_PRODUCT.equals(items)) {
		// lsContractCredenceNo = this.blsInnerMergeDao
		// .getContractExgCredenceNo();
		// lsDictPorInnerMergeSeqNum = this.blsInnerMergeDao
		// .getBlsDictPorExgInnerMergeSeqNum();
		// } else {
		// lsContractCredenceNo = this.blsInnerMergeDao
		// .getContractImgCredenceNo();
		// lsDictPorInnerMergeSeqNum = this.blsInnerMergeDao
		// .getBlsDictPorImgInnerMergeSeqNum();
		// }
		List lsInnerMergeId = this.blsInnerMergeDao.getBlsInnerMergeForId();
		for (int i = 0; i < list.size(); i++) {
			BlsTenInnerMerge c = (BlsTenInnerMerge) list.get(i);
			Integer innerMergeSeqNum = c.getSeqNum();
			String id = c.getId();
			if (lsInnerMergeId.contains(id)) {
			} else {
				listBlsTenInnerMerge.add(c);
				this.blsInnerMergeDao.deleteBlsTenInnerMerge(c);
			}
		}
		return listBlsTenInnerMerge;
	}

	/**
	 * 初始化单位折算比例
	 */
	public void initUnitDedault() {
		List list = this.blsInnerMergeDao.finblsInnerMerge();
		if (list == null) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMerge blsinnerMerge = (BlsInnerMerge) list.get(i);
			if (blsinnerMerge.getMateriel() == null
					|| blsinnerMerge.getMateriel().getCalUnit() == null
					|| blsinnerMerge.getBlsTenInnerMerge() == null
					|| blsinnerMerge.getBlsTenInnerMerge().getComUnit() == null) {
				continue;
			}
			CalUnit materielcalUnit = (CalUnit) blsinnerMerge.getMateriel()
					.getCalUnit();
			Unit blsTenInnerMergeUnit = (Unit) blsinnerMerge
					.getBlsTenInnerMerge().getComUnit();

			Double convert = getConvert(materielcalUnit.getName(),
					blsTenInnerMergeUnit.getName());
			blsinnerMerge.setUnitConvert(convert);

			this.blsInnerMergeDao.saveBlsInnerMerge(blsinnerMerge);
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

}
