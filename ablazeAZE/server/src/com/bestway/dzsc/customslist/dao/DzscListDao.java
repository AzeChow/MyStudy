package com.bestway.dzsc.customslist.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscState;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.customslist.entity.DzscMakeApplyToCustoms;

/**
 * com.bestway.dzsc.customslist.dao.DzscListDao
 * 
 * @author yp
 * 
 */
public class DzscListDao extends BaseDao {

	/**
	 * 保存申请单转清单的中间表
	 * 
	 * @param list
	 *            申请单转清单的中间表
	 */
	public void saveDzscMakeApplyToCustoms(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscMakeApplyToCustoms makeApplyToCustoms = (DzscMakeApplyToCustoms) list
					.get(i);
			this.saveOrUpdate(makeApplyToCustoms);
		}
	}

	/**
	 * 删除由申请单转清单的中间表
	 * 
	 * @param m
	 *            申请单转清单的中间表
	 */
	public void deleteDzscMakeApplyToCustoms(DzscMakeApplyToCustoms m) {
		this.delete(m);
	}

	/**
	 * 查找申请单转清单的中间表,根据报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfoId
	 *            报关清单归并前商品信息Id
	 * @return List 是DzscMakeApplyToCustoms型，申请单转清单的中间表
	 */
	public List findDzscMakeApplyToCustomsByBeforeComInfo(
			String atcMergeBeforeComInfoId) {
		return this.find(
				"select m from DzscMakeApplyToCustoms m where m.company.id = ? "
						+ " and m.atcMergeBeforeComInfo.id = ?   ",
				new Object[] { CommonUtils.getCompany().getId(),
						atcMergeBeforeComInfoId });
	}

	/**
	 * 查找申请单转清单的中间表,根据报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfoId
	 *            报关清单归并前商品信息Id
	 * @return List 是DzscMakeApplyToCustoms型，申请单转清单的中间表
	 */
	public List findDzscMakeBillListCodeByRequestBill(
			ImpExpRequestBill impExpRequestBill) {
		return this
				.find(
						"select distinct a.atcMergeBeforeComInfo.afterComInfo.billList.listNo "
								+ " from DzscMakeApplyToCustoms a where a.company.id = ? "
								+ " and a.impExpCommodityInfo.impExpRequestBill.id = ?   ",
						new Object[] { CommonUtils.getCompany().getId(),
								impExpRequestBill.getId() });
	}

	/**
	 * 查找计量单位
	 * 
	 * @param ptUnitCode
	 *            单位编码
	 * @return Double 计量单位存在时返回单位对照比例因子，否则返回1
	 */
	public Double getUnitScale(String ptUnitCode) {
		if (ptUnitCode == null) {
			return Double.valueOf(1);
		}
		List list = this
				.find(
						"select a from CalUnit a where a.code = ? and a.company.id = ?",
						new Object[] { ptUnitCode,
								CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return ((CalUnit) list.get(0)).getScale();
		}
		return Double.valueOf(1);
	}

	/**
	 * 查找所有进出报关清单表头
	 * 
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findApplyToCustomsBillList() {
		return this.find(
				"select a from DzscCustomsBillList as a where a.company.id=?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 根据清单ID查找报关清单表头.
	 * 
	 * @param impExpType
	 *            清单类型
	 * @param billState
	 *            清单状态
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public DzscCustomsBillList findDzscCustomsBillListById(String id) {
		List list = this
				.find(
						"select a from DzscCustomsBillList as a where a.id= ? and a.company.id=?",
						new Object[] { id, CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (DzscCustomsBillList) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找所有存在的 Render Data Column
	 * 
	 * @return
	 */
	public ApplyCustomBillParameter findApplyCustomBillParameter() {
		List list = super.findNoCache(
				"from ApplyCustomBillParameter a where a.company = ? ",
				new Object[] { CommonUtils.getCompany() });
		if (list != null && list.size() > 0) {
			return (ApplyCustomBillParameter) (list.get(0));
		}
		return null;
	}

	/**
	 * 根据清单类型和清单状态 查找报关清单表头.
	 * 
	 * @param impExpType
	 *            清单类型
	 * @param billState
	 *            清单状态
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findDzscCustomsBillListByTypeAndState(int impExpType,
			int billState) {
		return this
				.find(
						"select a from DzscCustomsBillList as a where a.impExpType = ? and a.listState = ? and a.company.id=?",
						new Object[] { Integer.valueOf(impExpType),
								Integer.valueOf(billState),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据清单进出口标志和手册号查找报关清单表头.
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHead
	 *            手册表头
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findApplyToCustomsBillListByType(int impExpFlag,
			BaseEmsHead emsHead, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from DzscCustomsBillList as a where a.impExpFlag = ? and a.company.id=?"
				+ " and a.emsHeadH2k=? ";
		parameters.add(Integer.valueOf(impExpFlag));
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHead.getEmsNo());
		if (beginDate != null) {
			hql += " and a.createdDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.createdDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据清单进出口标志和手册号查找报关清单表头.
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHead
	 *            手册表头
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findDzscCustomsBillList(int impExpFlag, BaseEmsHead emsHead,
			String listNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from DzscCustomsBillList as a where a.impExpFlag = ? "
				+ " and a.company.id=?" + " and a.emsHeadH2k=? ";
		parameters.add(Integer.valueOf(impExpFlag));
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHead.getEmsNo());
		if (listNo != null && !"".equals(listNo.trim())) {
			hql += " and a.listNo like ? ";
			parameters.add("%" + listNo + "%");
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据ID查找报关清单表头.
	 * 
	 * @param Id
	 *            报关清单表头Id
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findApplyToCustomsBillListById(String Id) {
		return this
				.find(
						"select a from DzscCustomsBillList as a where a.id = ? and a.company.id=?",
						new Object[] { Id, CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据报关清单号查找报关清单.
	 * 
	 * @param listNo
	 *            报关清单号
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findApplyToCustomsBillListByListNo(String listNo) {
		return this
				.find(
						"select a from DzscCustomsBillList as a where a.listNo = ? and a.company.id=?",
						new Object[] { listNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找已转报关单的报关清单
	 * 
	 * @param emsNo
	 *            手册号
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findBillListMakedCustomsDeclaration(String emsNo) {
		return this.find(
				"select a from DzscCustomsBillList as a where a.isGenerateDeclaration = ? "
						+ " and a.emsHeadH2k=? and a.company.id=?",
				new Object[] { true, emsNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找未转报关单的报关清单.
	 * 
	 * @param emsNo
	 *            手册号
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findBillListNoMakeCustomsDeclaration(String emsNo,
			Integer impExpFlag) {
		return this
				.find(
						"select a from DzscCustomsBillList as a where a.isGenerateDeclaration = ?"
								+ " and a.emsHeadH2k=? and a.impExpFlag=? and a.company.id=?",
						new Object[] { false, emsNo, impExpFlag,
								CommonUtils.getCompany().getId() });
	}

	// /**
	// * 获取当天的最大清单号码
	// *
	// * @return String 当天的最大清单号码
	// */
	// public String getApplyToCustomsBillListMaxNo() {
	// String yearmonthday = "";
	// String serialNo = "";
	// Calendar calendar = Calendar.getInstance();
	// yearmonthday = CommonUtils.convertIntToStringByLength(calendar
	// .get(Calendar.YEAR), 4);
	// yearmonthday += CommonUtils.convertIntToStringByLength(calendar
	// .get(Calendar.MONTH) + 1, 2);
	// yearmonthday += CommonUtils.convertIntToStringByLength(calendar
	// .get(Calendar.DAY_OF_MONTH), 2);
	// List list = this.find(
	// "select max(a.listNo) from DzscCustomsBillList as a "
	// + " where a.listNo like ?", new Object[] { yearmonthday
	// + "%" });
	// if (list.size() < 1) {
	// serialNo = "0001";
	// } else if (list.get(0) == null) {
	// serialNo = "0001";
	// } else {
	// String temp = list.get(0).toString();
	// if (temp.trim().equals("")) {
	// serialNo = "0001";
	// } else {
	// int n = Integer.parseInt(temp.substring(temp.length() - 4, temp
	// .length())) + 1;
	// serialNo = CommonUtils.convertIntToStringByLength(n, 4);
	// }
	// }
	// return yearmonthday + serialNo;
	// }

	/**
	 * 删除报关清单
	 * 
	 */
	public void deleteApplyToCustomsBillList(
			DzscCustomsBillList applyToCustomsBillList) {
		this.delete(applyToCustomsBillList);
	}

	/**
	 * 保存报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	public void saveApplyToCustomsBillList(
			DzscCustomsBillList applyToCustomsBillList) {
		this.saveOrUpdate(applyToCustomsBillList);
	}

	/**
	 * 根据清单Id查询归并前商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListBeforeCommInfo型，清单归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByListID(
			DzscCustomsBillList applyToCustomsBillList) {
		return this.find("select a from DzscBillListBeforeCommInfo as a"
				+ " left join fetch a.materiel"
				+ " where a.afterComInfo.billList.id = ? order  by a.no  ",
				new Object[] { applyToCustomsBillList.getId() });
	}
	public List findComplexByFlag(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select  a.complex.code  from CheckupComplex  a    ";
		if (impexpfalg != null) {
			hsql += " where  a.impExpFlag = ? ";
			para.add(impexpfalg);
		}
		return this.find(hsql, para.toArray());
	}
	/**
	 * 根据清单Id查询归并前商品信息最大序号
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListBeforeCommInfo型，清单归并前商品信息
	 */
	public Integer findAtcMergeBeforeComInfoMaxNo(
			DzscCustomsBillList applyToCustomsBillList) {
		List list = this.find(
				"select max(a.no) from DzscBillListBeforeCommInfo as a"
						+ " where a.afterComInfo.billList.id = ? ",
				new Object[] { applyToCustomsBillList.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品信息
	 * @return List 是DzscBillListBeforeCommInfo型，清单归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByAfterID(
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		return this
				.find(
						"select a from DzscBillListBeforeCommInfo as a left join fetch a.materiel where a.afterComInfo.id = ? ",
						new Object[] { atcMergeAfterComInfo.getId() });
	}

	/**
	 * 获得归并前所有商品信息
	 */
	public List findAllAtcMergerBeforeComInfo(
			DzscCustomsBillList dzscCustomsBillList) {
		return this
				.find(
						"select a from DzscBillListBeforeCommInfo as a left join fetch a.materiel where a.afterComInfo.billList.id = ? order by a.no,a.afterComInfo.emsSerialNo asc",
						new Object[] { dzscCustomsBillList.getId() });
	}

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 *            清单归并前商品信息
	 */
	public void deleteAtcMergeBeforeComInfo(
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo) {
		this.delete(atcMergeBeforeComInfo);
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 *            清单归并前商品信息
	 */
	public void saveAtcMergeBeforeComInfo(
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo) {
		this.saveOrUpdate(atcMergeBeforeComInfo);
	}

	/**
	 * 根据清单Id查询清单归并后商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	public List findAtcMergeAfterComInfoByListID(
			DzscCustomsBillList applyToCustomsBillList) {
		return this
				.find(
						"select a from DzscBillListAfterCommInfo as a where a.billList.id = ?",
						new Object[] { applyToCustomsBillList.getId() });
	}

	/**
	 * 根据清单Id查询清单归并后商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	public int findAtcMergeAfterComInfoCountByListID(
			DzscCustomsBillList applyToCustomsBillList) {
		List list = this.find(
				" select count(a.id) from DzscBillListAfterCommInfo as a"
						+ " where a.billList.id = ?",
				new Object[] { applyToCustomsBillList.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据清单Id查询清单归并后商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	public DzscBillListAfterCommInfo findAtcMergeAfterComInfoByListID(
			DzscCustomsBillList applyToCustomsBillList, Integer seqNum) {
		List list = this.find(
				"select a from DzscBillListAfterCommInfo as a where a.billList.id = ?"
						+ "  and a.emsSerialNo=? ", new Object[] {
						applyToCustomsBillList.getId(), seqNum });
		if (list.size() > 0 && list.get(0) != null) {
			return (DzscBillListAfterCommInfo) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据清单Id、手册序号、商品信息编码，查询归并后商品信息
	 * 
	 * @param billList
	 *            报关清单表头
	 * @param emsSerialNo
	 *            手册序号
	 * @param complexCode
	 *            商品信息编码
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	public List findAtcMergeAfterComInfo(DzscCustomsBillList billList,
			String emsSerialNo, String complexCode) {
		return this
				.find(
						"select a from DzscBillListAfterCommInfo as a where a.billList.id=? and a.emsSerialNo = ? and a.complex.code=?",
						new Object[] { billList.getId(), emsSerialNo,
								complexCode });
	}

	/**
	 * 删除报关清单归并后商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品信息
	 */
	public void deleteAtcMergeAfterComInfo(
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		this.delete(atcMergeAfterComInfo);
	}

	/**
	 * 保存报关清单归并后商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品信息
	 */
	public void saveAtcMergeAfterComInfo(
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		this.saveOrUpdate(atcMergeAfterComInfo);
	}

	/**
	 * 获取报关清单归并后商品的数目，用来更新报关清单的项数。
	 * 
	 * @param billList
	 *            报关清单表头
	 * @return int 报关清单归并后商品的数目
	 */
	public int getAtcMergeAfterCommonNum(DzscCustomsBillList billList) {
		int num = 0;
		List list = this
				.find(
						"select count(*) from DzscBillListAfterCommInfo as a where a.billList.id = ? ",
						new Object[] { billList.getId() });
		if (list.size() > 0) {
			if (list.get(0) != null) {
				num = Integer.parseInt(list.get(0).toString());
			}
		}
		return num;
	}

	/**
	 * 查询归并关系备案成品信息(如果在报关清单中已存在的话，将其过滤)
	 * 
	 * @param billList
	 *            报关清单表头
	 * @return List 存放归并关系备案成品一些属性的信息
	 */
	public List findDeclaredProductInfo(DzscCustomsBillList billList) {
		return this
				.find(
						" select a.dzscEmsExgBill.seqNum,a.materiel,a.dzscEmsExgBill.complex.code,"
								+ " a.dzscEmsExgBill.unit,a.dzscEmsExgBill.complex.firstUnit,"
								+ " a.dzscEmsExgBill.complex.secondUnit,a.dzscEmsExgBill.name,a.dzscEmsExgBill.spec "
								+ " from DzscEmsPorMaterialExg a "
								+ " left join a.dzscEmsExgBill.unit "
								+ " left join a.dzscEmsExgBill.complex.firstUnit "
								+ " left join a.dzscEmsExgBill.complex.secondUnit "
								+ " where a.dzscEmsPorHead.declareState=? and a.dzscEmsPorHead.emsNo=?"
								+ " and a.materiel.ptNo not in (select d.materiel.ptNo from DzscBillListBeforeCommInfo as d where d.afterComInfo.billList.id=?"
								+ " and d.afterComInfo.billList.company.id=? )"
								+ " and a.company.id=? "
								+ " order by a.dzscEmsExgBill.seqNum,a.dzscEmsExgBill.complex.code,a.materiel.ptNo ",
						new Object[] { DzscState.EXECUTE,
								billList.getEmsHeadH2k(), billList.getId(),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询归并关系备案料件信息(如果在报关清单中已存在的话，将其过滤)
	 * 
	 * @param billList
	 *            报关清单表头
	 * @return List 存放归并关系备案料件一些属性的信息
	 */
	public List findDeclaredMaterielInfo(DzscCustomsBillList billList) {
		return this
				.find(
						" select a.dzscEmsImgBill.seqNum,a.materiel,a.dzscEmsImgBill.complex.code,"
								+ " a.dzscEmsImgBill.unit,a.dzscEmsImgBill.complex.firstUnit,"
								+ " a.dzscEmsImgBill.complex.secondUnit,a.dzscEmsImgBill.name,a.dzscEmsImgBill.spec "
								+ " from DzscEmsPorMaterialImg a "
								+ " left join a.dzscEmsImgBill.unit "
								+ " left join a.dzscEmsImgBill.complex.firstUnit "
								+ " left join a.dzscEmsImgBill.complex.secondUnit "
								+ " where a.dzscEmsPorHead.declareState=? and a.dzscEmsPorHead.emsNo=?"
								+ " and a.materiel.ptNo not in (select d.materiel.ptNo from DzscBillListBeforeCommInfo as d where d.afterComInfo.billList.id=?"
								+ " and d.afterComInfo.billList.company.id=? )"
								+ " and a.company.id=? "
								+ " order by a.dzscEmsImgBill.seqNum,a.dzscEmsImgBill.complex.code,a.materiel.ptNo ",
						new Object[] { DzscState.EXECUTE,
								billList.getEmsHeadH2k(), billList.getId(),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找商品信息
	 * 
	 * @param code
	 *            商品信息编码
	 * @return Complex 商品信息
	 */
	public Complex findComplex(String code) {
		return (Complex) this.load(Complex.class, code);
	}

	/**
	 * 根据报关清单取得报关单
	 * 
	 * @param listNo
	 *            报关清单号码
	 * @return List 是DzscCustomsDeclaration型，报关单头
	 */
	public List findCustomsDeclarationByBillList(String listNo) {
		return this
				.find(
						" from DzscCustomsDeclaration as a where a.billListId=? and a.company.id=?",
						new Object[] { listNo, CommonUtils.getCompany().getId() });
	}
}