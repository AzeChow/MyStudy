package com.bestway.bls.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.CollateBindDetail;
import com.bestway.bls.entity.CollateBindDetailList;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.FormType;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;

/**
 * BlsCheckCancelDao 单证核销,DAO
 * 
 * @author luosheng
 * 
 */
public class BlsCheckCancelDao extends BaseDao {

	// //////////////////////////////////////
	// CollateBind
	// CollateBindDetail
	// CollateBindDetailList
	// //////////////////////////////////////

	/**
	 * 获取当前公司
	 * 
	 * @return Company 当前公司，返回符合条件的第一条数据
	 */
	public Company findCompany() {
		List list = this.find("select a from Company a where a.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		return (Company) list.get(0);
	}

	public Brief findBrief(String code) {
		List list = this.find("select a from Brief a where a.code = ?",
				new Object[] { code });
		if (list.isEmpty()) {
			return null;
		}
		return (Brief) list.get(0);
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	public List<CollateBind> findCollateBind(int index, int length,
			String property, Object value, boolean isLike, Integer type) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from CollateBind "
				+ " as a  where  a.company= ? ";
		parameters.add(CommonUtils.getCompany());
		if (type == 0) {
			hsql += " and a.formType=? and a.collateFormType=? ";
			parameters.add(FormType.IM_MFT);
			parameters.add(FormType.IM_ENT);
		} else if (type == 1) {
			hsql += " and a.formType=? and a.collateFormType=? ";
			parameters.add(FormType.EX_MFT);
			parameters.add(FormType.EX_ENT);
		} else {
			hsql += " and a.formType=? and a.collateFormType=? ";
			parameters.add(FormType.EX_MFT);
			parameters.add(FormType.IM_MFT);
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findListNoCache(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 保存核销捆绑关系基本信息（表头）
	 * 
	 * @param obj
	 *            核销捆绑关系基本信息（表头）
	 */
	public void saveCollateBind(CollateBind obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）
	 * 
	 * @return 核销捆绑关系基本信息（表头）
	 */
	public List<CollateBind> findCollateBind() {
		String hsql = "select a from CollateBind as a where a.company.id = ? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）
	 * 
	 * @return 核销捆绑关系基本信息（表头）
	 */
	public List<CollateBind> findCollateBind(Date start, Date end, Integer type) {
		String hsql = "select a from CollateBind as a where a.company.id = ? ";
		List p = new ArrayList();
		p.add(CommonUtils.getCompany().getId());
		if (type == 0) {
			hsql += " and a.formType=? and a.collateFormType=? ";
			p.add(FormType.IM_MFT);
			p.add(FormType.IM_ENT);
		} else if (type == 1) {
			hsql += " and a.formType=? and a.collateFormType=? ";
			p.add(FormType.EX_MFT);
			p.add(FormType.EX_ENT);
		} else {
			hsql += " and a.formType=? and a.collateFormType=? ";
			p.add(FormType.EX_MFT);
			p.add(FormType.IM_MFT);
		}
		if (start != null && end == null) {
			hsql += " and a.createDate>=? ";
			p.add(start);
		} else if (start == null && end != null) {
			hsql += " and a.createDate<=? ";
			p.add(end);
		} else if (start != null && end != null) {
			hsql += " and a.createDate>=? and a.createDate<=? ";
			p.add(start);
			p.add(end);
		}
		return this.find(hsql, p.toArray());
	}

	/**
	 * 删除核销捆绑关系基本信息（表头）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表头）
	 */
	public void deleteCollateBind(List<CollateBind> list) {
		this.deleteAll(list);
	}

	/**
	 * 删除核销捆绑关系基本信息（表头）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表头）
	 */
	public void deleteCollateBind(CollateBind collateBind) {
		this.delete(collateBind);
	}

	/**
	 * 保存核销捆绑关系基本信息（表体）
	 * 
	 * @param obj
	 *            核销捆绑关系基本信息（表体）
	 */
	public void saveCollateBindDetail(CollateBindDetail obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 获得核销捆绑关系基本信息（表体）
	 * 
	 * @return 核销捆绑关系基本信息（表体）
	 */
	public List<CollateBindDetail> findCollateBindDetailByHead(
			String collateBindId) {
		String hsql = "select a from CollateBindDetail as a where a.company.id = ? "
				+ "and a.collateBind.id= ? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				collateBindId });
	}

	/**
	 * 获得核销捆绑关系基本信息（表体）
	 * 
	 * @param ioFlag
	 *            进出仓标志 "I"进仓 "O"出仓
	 * @return 核销捆绑关系基本信息（表体）
	 */
	public List<Object[]> findCollateBindDetail(String ioFlag,String formType, String collateFormType) {
		String hsql = "select a.collateBind.formID,a.gno from CollateBindDetail as a where ";
		List parameters = new ArrayList();
		if ("I".equals(ioFlag)) {
			hsql += " a.collateBind.formType=? and a.collateBind.collateFormType=? ";
			parameters.add(FormType.IM_MFT);
			parameters.add(FormType.IM_ENT);
		} else {
//			hsql += " a.collateBind.formType=? and (a.collateBind.collateFormType=? or a.collateBind.collateFormType=?) ";
			hsql += " a.collateBind.formType=? and (a.collateBind.collateFormType=?) ";
			parameters.add(formType);
			parameters.add(collateFormType);
//			parameters.add(FormType.IM_MFT);
		}
		hsql += " and a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	public List<CollateBind> findCollateBind(String formType,
			String collateFormType) {
		String hsql = " from CollateBind as a where a.formType=? and a.collateFormType=? and a.company.id=? ";
		return this.find(hsql, new Object[] { formType, collateFormType,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询进出仓单
	 * 
	 * @param ioFlag
	 * @return
	 */
	public List<StorageBillAfter> findStorageBillAfter(String ioFlag) {
		String hsql = "from StorageBillAfter as a where a.storageBill.ioFlag=? and a.storageBill.effective=? and a.storageBill.billType<>? and a.company.id = ? ";
		return this.find(hsql, new Object[] { ioFlag, true, "00",
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除核销捆绑关系基本信息（表体）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表体）
	 */
	public void deleteCollateBindDetail(List<CollateBindDetail> list) {
		this.deleteAll(list);
	}

	/**
	 * 删除核销捆绑关系基本信息（表体）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表体）
	 */
	public void deleteCollateBindDetail(CollateBindDetail collateBindDetail) {
		this.delete(collateBindDetail);
	}

	/** save 核销捆绑关系核销项信息 */
	public void saveCollateBindDetailList(CollateBindDetailList obj) {
		this.saveOrUpdate(obj);
	}

	/** find 核销捆绑关系核销项信息 */
	public List<CollateBindDetailList> findCollateBindDetailListByDetail(
			String collateBindDetailId) {
		String hsql = "select a from CollateBindDetailList as a where a.company.id = ? "
				+ "and a.collateBindDetail.id= ?";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				collateBindDetailId });
	}

	/** delete 核销捆绑关系核销项信息 */
	public void deleteCollateBindDetailList(List<CollateBindDetailList> list) {
		this.deleteAll(list);
	}

	/** delete 核销捆绑关系核销项信息 */
	public void deleteCollateBindDetailList(
			CollateBindDetailList collateBindDetailList) {
		this.delete(collateBindDetailList);
	}

	// ------------------------------
	// 获得单据中的已核销的数据
	// ------------------------------

	// public List<Delivery> findDelivery() {
	// String hsql = " select a from Delivery  a  where a.company.id =? " +
	// " and a.declareState is not null and a.declareState = ? ";
	// List<Object> para = new ArrayList<Object>();
	// para.add(CommonUtils.getCompany().getId());
	// para.add(DeclareState.PROCESS_EXE);
	// return this.find(hsql, para.toArray());
	// }

	// /** 获得正在执行的 StorageBill */
	// public List<StorageBill> findStorageBillByProcessExe(Integer flag) {
	// String hsql = " select a from StorageBill a where a.company.id =?  "
	// + "and a.delivery is not null "
	// + "and a.delivery.declareState = ? ";
	// // + " "
	// // + " not in ("
	// // + "            select c.formID from "
	// // +
	// "            CollateBind as c where c.company.id = ? and c.formID is not null "
	// // + "        )";
	// List<Object> para = new ArrayList<Object>();
	// para.add(CommonUtils.getCompany().getId());
	// para.add(DeclareState.WAIT_EAA);
	// if (flag == 0) {
	// hsql+="and a.ioFlag = ? and a.billNo not in "
	// +"(select c.formID from CollateBind as c "
	// +"where c.company.id = ? and c.formID is not null)";
	// para.add("I");
	// } else {
	// para.add("O");
	// }
	//		
	// para.add(CommonUtils.getCompany().getId());
	// return this.find(hsql, para.toArray());
	// }

	/** 获得正在执行的 StorageBill */
	public List<StorageBill> findStorageBillByProcessExe(Integer flag) {
		String hsql = " select a from StorageBill a where  a.company.id =?  "
				+ "and a.delivery is not null ";
		List<Object> para = new ArrayList<Object>();
		para.add(CommonUtils.getCompany().getId());
		if (flag == 0) {
			hsql += "and a.ioFlag = ? ";
			para.add("I");
		} else if (flag == 1) {
			hsql += "and a.ioFlag = ? ";
			para.add("O");
		} else if (flag == 2) {

		}
		hsql += "and a.delivery.declareState = ? "
				+ "and a.billNo"
				+ " not in ("
				+ "            select c.formID from "
				+ "            CollateBind as c where c.company.id = ? and c.formID is not null "
				+ "        )";
		para.add(DeclareState.WAIT_EAA);
		para.add(CommonUtils.getCompany().getId());
		return this.find(hsql, para.toArray());
	}

	// public List<StorageBill> findStorageBillbyDelivery(String deliveryId) {
	// String hsql = " select a from StorageBill a where  a.company.id =?  " +
	// "and a.delivery is not null " +
	// "and a.delivery.id = ? ";
	// List<Object> para = new ArrayList<Object>();
	// para.add(CommonUtils.getCompany().getId());
	// para.add(deliveryId);
	// return this.find(hsql, para.toArray());
	// }

	public List<StorageBillAfter> findStorageBillAfterByStorageBill(
			String storageBillId) {
		String hsql = " select a from StorageBillAfter a where  a.company.id =? "
				+ " and a.storageBill is not null "
				+ " and a.storageBill.id= ? " + "  ";
		List<Object> para = new ArrayList<Object>();
		para.add(CommonUtils.getCompany().getId());
		para.add(storageBillId);
		return this.find(hsql, para.toArray());
	}

	// public List<StorageBillBefore> findStorageBillBeforeForDelivery(
	// String storageBillAfterId) {
	// List<Object> para = new ArrayList<Object>();
	// String hsql =
	// " select a from StorageBillBefore  a  where a.company.id =? ";
	// hsql += " and  a.storageBillAfter.id =?  ";
	// para.add(CommonUtils.getCompany().getId());
	// para.add(storageBillAfterId);
	// return this.find(hsql, para.toArray());
	// }
	/**
	 * 根据进仓出仓，是否生效，申报状态查询车次资料
	 * 
	 * @param company
	 * @param inOut
	 * @return
	 */
	public List findCollateBind(Company company, String declareState) {
		List para = new ArrayList();
		String hsql = " select a from CollateBind  a "
				+ "where a.company.id =?" + " and a.declareState=? ";
		para.add(company.getId());
		para.add(declareState);
		return this.find(hsql, para.toArray());
	}

	public Integer findMaxNoFromCollateBindDetail(CollateBind collateBind) {
		List para = new ArrayList();
		Integer maxNo = 0;
		String hsql = "select max(a.gno) from CollateBindDetail a where a.company.id =? and a.collateBind=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(collateBind);
		List list = this.find(hsql, para.toArray());
		if (list.size() > 0) {
			maxNo = (Integer) list.get(0);
			System.out.println("maxNo=" + maxNo);
			return maxNo;
		} else {
			return 0;
		}
	}
}
