/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code TemplatesfindMaterielFromInner
 * 
 * 
 */
package com.bestway.dzsc.dzscmanage.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.dao.DataAccessException;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHeadFas;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialExg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialImg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * com.bestway.dzsc.dzscmanage.dao.DzscDao
 * 
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class DzscDao extends BaseDao {

	/**
	 * 抓取物料表头
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findDzscMaterialHead() {
		return this.find(
				"select a from DzscMaterielHead as a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询报文收发存放路径设定
	 * 
	 * @return
	 */
	public DzscParameterSet findDzscParameterSet() {
		List<Object> parameters = new ArrayList<Object>();
		DzscParameterSet dirSet = null;
		String hql = "select a from DzscParameterSet a "
				+ " where a.company.id= ?";
		parameters.add(CommonUtils.getCompany().getId());
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			dirSet = (DzscParameterSet) list.get(0);
		} else {
			dirSet = new DzscParameterSet();
			this.saveOrUpdate(dirSet);
		}
		return dirSet;
	}

	/**
	 * 查找电子手册的物料基本资料，不在进出口申请单里
	 * 
	 * @param materialType
	 *            物料类别属性
	 * @param impExpRequestBillId
	 *            进出口申请单头Id
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是materiel型，电子手册的物料基本资料
	 */
	public List findMaterielByImpExpRequestBillType(String materialType,
			String impExpRequestBillId, int index, int length, String property,
			Object value, boolean isLike, boolean isFilter) {
		String hsql = null;
		if (isFilter) {
			hsql = "select a.materiel from MaterialApply a"
					+ " left join fetch a.materiel.complex "
					+ " where a.materiel.id not in (select c.materiel.id "
					+ " from ImpExpCommodityInfo c "
					+ " where c.impExpRequestBill.id = ? ) "
					+ " and a.materiel.scmCoi.coiProperty=? "
					+ " and a.company.id=? and a.stateMark=? ";
		} else {
			hsql = "select a.materiel from MaterialApply a"
					+ " left join fetch a.materiel.complex "
					+ " where a.materiel.id not in ( ? ) "
					+ " and a.materiel.scmCoi.coiProperty=? "
					+ " and a.company.id=? and a.stateMark=? ";
		}
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(impExpRequestBillId);
		paramters.add(materialType);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(DzscState.EXECUTE);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and a.materiel." + property + " like ? ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and a.materiel." + property + " = ? ";
				paramters.add(value);
			}
		}
		hsql += " order by a.materiel.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找自用商品编码
	 * 
	 * @return List 是Complex，自用商品编码
	 */
	public List findComplexForInnerMerge() {
		return this.find("select a from Complex a  "
		// " and a.code not in (select b.tenComplex.code from DzscInnerMerge b
				// where b.company.id=? )",
				);
	}

	/**
	 * 取得系统的其他参数设置
	 * 
	 * @return CompanyOther 其他参数设置,返回符合条件的第一条数据
	 */
	public CompanyOther getSysCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id =?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (CompanyOther) list.get(0);
		}
		return null;
	}

	/**
	 * 获取className里最大的流水号
	 * 
	 * @param className
	 *            表名
	 * @param seqNum
	 *            流水号
	 * @return Sting className里最大的流水号
	 */
	public String getNum(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a " + "where a.company= ?", new Object[] { CommonUtils
				.getCompany() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 获取DzscEmsImgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsImgWjNum(DzscEmsPorWjHead head) {
		List list = this.find("select max(a.seqNum) from DzscEmsImgWj as a "
				+ " where a.dzscEmsPorWjHead.id=? "
				+ " and a.dzscEmsPorWjHead.company= ?", new Object[] {
				head.getId(), CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			String num = list.get(0).toString();
			return Integer.parseInt(num) + 1;
		}
		return 1;
	}

	/**
	 * 获取DzscEmsImgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsExgWjNum(DzscEmsPorWjHead head) {
		List list = this.find("select max(a.seqNum) from DzscEmsExgWj as a "
				+ " where a.dzscEmsPorWjHead.id=?"
				+ " and a.dzscEmsPorWjHead.company= ?", new Object[] {
				head.getId(), CommonUtils.getCompany() });
		if (list.get(0) != null) {
			String num = list.get(0).toString();
			return Integer.parseInt(num) + 1;
		}
		return 1;
	}

	/**
	 * 获取DzscEmsImg里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsPorImgNum(DzscEmsPorHead head) {
		List list = this
				.find(
						"select max(a.seqNum) from DzscEmsImgBill as a "
								+ "where a.dzscEmsPorHead.id=? and a.dzscEmsPorHead.company= ?",
						new Object[] { head.getId(), CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			String num = list.get(0).toString();
			return Integer.parseInt(num) + 1;
		}
		return 1;
	}

	/**
	 * 获取DzscEmsImg里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsPorExgNum(DzscEmsPorHead head) {
		List list = this
				.find(
						"select max(a.seqNum) from DzscEmsExgBill as a "
								+ "where a.dzscEmsPorHead.id=? and a.dzscEmsPorHead.company= ?",
						new Object[] { head.getId(), CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			String num = list.get(0).toString();
			return Integer.parseInt(num) + 1;
		}
		return 1;
	}

	/**
	 * 根据手册号来抓取合同备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public List findDzscEmsPorWjHeadByEmsNo(String emsNo) {
		String hql = " select a from DzscEmsPorWjHead a "
				+ " where a.corrEmsNo=? and a.company.id=? ";
		return this.find(hql, new Object[] { emsNo,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据手册号来抓取通关备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadByEmsNo(String emsNo) {
		String hql = " select a from DzscEmsPorHead a "
				+ " where a.emsNo=? and a.company.id=? ";
		return this.find(hql, new Object[] { emsNo,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据手册号来抓取通关备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadByCopEmsNo(String copTrNo) {
		String hql = " select a from DzscEmsPorHead a "
				+ " where a.copTrNo=? and a.company.id=? ";
		return this.find(hql, new Object[] { copTrNo,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据手册号来抓取合同备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public DzscEmsPorWjHead findExingDzscEmsPorWjHeadByEmsNo(String emsNo) {
		String hql = " select a from DzscEmsPorWjHead a "
				+ " where a.emsNo=? and a.company.id=? "
				+ " and a.declareState=? ";
		List list = this.find(hql, new Object[] { emsNo,
				CommonUtils.getCompany().getId(), DzscState.EXECUTE });
		if (list.size() > 0) {
			return (DzscEmsPorWjHead) list.get(0);
		}
		return null;
	}

	/**
	 * 根据手册号来抓取合同备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public DzscEmsPorHead findExingDzscEmsPorHeadByEmsNo(String emsNo) {
		String hql = " select a from DzscEmsPorHead a "
				+ " where a.emsNo=? and a.company.id=? "
				+ " and a.declareState=? ";
		List list = this.find(hql, new Object[] { emsNo,
				CommonUtils.getCompany().getId(), DzscState.EXECUTE });
		if (list.size() > 0) {
			return (DzscEmsPorHead) list.get(0);
		}
		return null;
	}

	/**
	 * 获得最大的合同流水号
	 * 
	 * @return String 最大的合同流水号
	 */
	public String getMaxPreContractCodeSuffixByWJ() {
		String code = "";
		List list = this
				.find(
						"select max(a.preContractCodeSuffix) from DzscEmsPorWjHead as a where a.company= ? ",
						CommonUtils.getCompany());
		if (list.size() < 1) {
			code = CommonUtils.convertIntToStringByLength(1, 6);
		} else if (list.get(0) == null) {
			code = CommonUtils.convertIntToStringByLength(1, 6);
		} else {
			String temp = list.get(0).toString();
			if (temp.trim().equals("")) {
				code = CommonUtils.convertIntToStringByLength(1, 6);
			} else {
				int n = Integer.parseInt(temp) + 1;
				code = CommonUtils.convertIntToStringByLength(n, 6);
			}
		}
		return code;
	}

	/**
	 * 获得最大的手册流水号
	 * 
	 * @return String 最大的手册流水号
	 */
	public String getMaxPreContractCodeSuffixByCustom() {
		String code = "";
		List list = this
				.find(
						"select max(a.preContractCodeSuffix) from DzscEmsPorHead as a where a.company= ? ",
						CommonUtils.getCompany());
		if (list.size() < 1) {
			code = CommonUtils.convertIntToStringByLength(1, 6);
		} else if (list.get(0) == null) {
			code = CommonUtils.convertIntToStringByLength(1, 6);
		} else {
			String temp = list.get(0).toString();
			if (temp.trim().equals("")) {
				code = CommonUtils.convertIntToStringByLength(1, 6);
			} else {
				int n = Integer.parseInt(temp) + 1;
				code = CommonUtils.convertIntToStringByLength(n, 6);
			}
		}
		return code;
	}

	/**
	 * 根据物料号码,抓取未变更的归并数据
	 * 
	 * @param materialCode
	 *            料号
	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	 */
	public DzscInnerMergeData findInnerMergeDataByPtNo(String materialCode) {
		List list = this.find(
				"select a from DzscInnerMergeData as a where a.company.id=?"
						+ " and a.materiel.ptNo=? and a.isChange=? ",
				new Object[] { CommonUtils.getCompany().getId(), materialCode,
						false });
		if (list.size() > 0) {
			return (DzscInnerMergeData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取className里最大的流水号,不根据当前公司
	 * 
	 * @param className
	 *            表名
	 * @param seqNum
	 *            流水号
	 * @return Sting className里最大的流水号
	 */
	public String getNumNoCompany(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a ");
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 海关帐表体新增
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByType(String type) {
		return this
				.find(
						" select a from DzscInnerMergeData as a left join fetch a.materiel "
								+ "  where a.imrType = ? and a.isChange=?  and a.company.id=? ",
						new Object[] { type, false,
								CommonUtils.getCompany().getId() });

	}

	/**
	 * 根据归并后10码数据,抓取归并数据的物料部分
	 * 
	 * @param tenSeqNum
	 *            十位编码序号
	 * @param complexCode
	 *            商品代码
	 * @return List 是materiel型，归并数据的物料
	 */
	public List findInnerMergeDataByTen(Integer tenSeqNum, String complexCode) {
		List list = this.find(
				"select a.materiel from DzscInnerMergeData as a where a.company.id=?"
						+ " and a.dzscTenInnerMerge.tenSeqNum=? "
						+ " and a.dzscTenInnerMerge.tenComplex.code=? "
						+ " and a.stateMark=? ", new Object[] {
						CommonUtils.getCompany().getId(), tenSeqNum,
						complexCode, DzscState.EXECUTE });
		return list;
	}

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

	/**
	 * 当数据为Null时返回空格,否则符合原来的id
	 * 
	 * @param id
	 *            所要格式化的数据
	 * @return id
	 */
	private String fomatId(String id) {
		if (id == null) {
			return "";
		}
		return id;
	}

	/**
	 * 当数据为Null时返回0,否则符合原来的id
	 * 
	 * @param value
	 *            所要格式化的数据
	 * @return id
	 */
	private Double formatDouble(Double value) {
		if (value == null) {
			return Double.valueOf(0);
		}
		return value;
	}

	/**
	 * 查找通关备案表头
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHead(boolean isCancel) {
		String hql = "select a from DzscEmsPorHead a where a.company.id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (isCancel) {
			hql += " and a.declareState=? ";
		} else {
			hql += " and a.declareState<>? ";
		}
		parameters.add(DzscState.CHECK_CANCEL);
		hql += " order by a.emsNo,a.seqNum ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找通关备案表头只获取正在执行的
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 * @author 石小凯
	 * 
	 */
	public List findDzscEmsPorHeadProcess(boolean isCancel) {
		String hql = "select a from DzscEmsPorHead a where a.company.id = ? and declareState=3";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (isCancel) {
			hql += " and a.declareState=? ";
		} else {
			hql += " and a.declareState<>? ";
		}
		parameters.add(DzscState.CHECK_CANCEL);
		hql += " order by a.emsNo,a.seqNum ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找通关备案表头
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public DzscEmsPorHead findDzscEmsPorHeadById(String id) {
		List list = this
				.find(
						"select a from DzscEmsPorHead a where a.company.id = ? and a.id=? ",
						new Object[] { CommonUtils.getCompany().getId(), id });
		if (list.size() > 0) {
			return (DzscEmsPorHead) list.get(0);
		}
		return null;
	}

	/**
	 * 电子手册通关备案表头--变更状态
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadChange() {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id = ? and a.declareState = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.CHANGE });
	}

	/**
	 * 电子手册通关备案表头--执行状态
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadExcu() {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id= ? and a.declareState = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 电子手册通关备案表头--执行状态
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadExecute(Company company) {
		return this
				.find(
						"select a from DzscEmsPorHead a where  a.company.id= ?  and  a.declareState = ?",
						new Object[] { company.getId(), DzscState.EXECUTE });
	}

	/**
	 * 电子手册通关备案表头--变更 or 申报
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadApplyOrChange(Company company) {
		return this
				.find(
						"select a from DzscEmsPorHead a where  a.company.id= ?  and ( a.declareState = ? or a.declareState = ? )",
						new Object[] { company.getId(), DzscState.APPLY,
								DzscState.CHANGE });
	}

	/**
	 * 电子手册通关备案表头--执行状态
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadExcu(String emsNo) {
		return this.find(
				"select a from DzscEmsPorHead a where a.company.id= ? and a.declareState = ?"
						+ " and a.emsNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), DzscState.EXECUTE,
						emsNo });
	}

	/**
	 * 判断是否存在seqNum通关备案表头
	 * 
	 * @param ediNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findisExistHeadBySeqNum(String ediNo) {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id=? and a.emsNo = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(), ediNo });
	}

	/**
	 * 显示非正在执行的通关备案表头
	 * 
	 * @param ediNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findNotExcute(String ediNo) {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id=? and a.emsNo = ? and a.declareState <> ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(), ediNo,
								DzscState.EXECUTE });
	}

	/**
	 * 根据申报状态查找通关备案表头
	 * 
	 * @param dzscState
	 *            申报状态
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHead(String dzscState) {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id = ? and a.declareState = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								dzscState });
	}

	/**
	 * 保存通关备案表头
	 * 
	 * @param obj
	 *            通关备案表头
	 */
	public void saveDzscEmsPorHead(DzscEmsPorHead obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存通关备案表头
	 * 
	 * @param obj
	 *            通关备案表头
	 * @return obj 是DzscEmsPorHead型，通关备案表头
	 */
	public DzscEmsPorHead saveEmsPorHead(DzscEmsPorHead obj) {
		this.saveOrUpdate(obj);
		return obj;
	}

	// /**
	// * 返回合同表体备案(料件)
	// */
	// public List findEmsPorImg(DzscEmsPorHead head) {
	// return this
	// .find(
	// "from DzscEmsPorImg a where a.dzscEmsPorHead = ? and a.company.id = ?",
	// new Object[] { head, CommonUtils.getCompany().getId() });
	// }

	// /**
	// * 返回合同表体备案(成品)
	// */
	// public List findEmsPorExg(DzscEmsPorHead head) {
	// return this
	// .find(
	// "from DzscEmsPorExg a where a.dzscEmsPorHead = ? and a.company.id = ?",
	// new Object[] { head, CommonUtils.getCompany().getId() });
	// }

	/**
	 * 返回电子手册归并四码
	 * 
	 * @param type
	 *            物料类型
	 * @param dzscEmsPorHead
	 *            通关备案表头
	 * @return List 存放通关备案料件的属性
	 */
	public List findMerger4(String type, DzscEmsPorHead dzscEmsPorHead) {
		String tableName = "";
		if (type.equals(MaterielType.MATERIEL)) {
			tableName = "DzscEmsPorImg";
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
			tableName = "DzscEmsPorExg";
		}
		return this
				.find(
						"select distinct a.fourSeqNum,a.levyMode.code,a.fourPtName,a.fourPtSpec,a.fourUnit,a.lowPrice,"
								+ "a.fourComplex from DzscInnerMergeData a "
								+ " left join fetch a.levyMode"
								+ " left join fetch a.fourUnit"
								+ " where a.imrType = ? and a.company.id = ? and a.fourSeqNum is not null"
								+ " and a.fourSeqNum not in (select b.seqNum from "
								+ tableName
								+ " b where b.dzscEmsPorHead.id = ?) and a.head.type = ?",
						new Object[] { type, CommonUtils.getCompany().getId(),
								dzscEmsPorHead.getId(), DzscState.EXECUTE });
	}

	// /**
	// * 保存电子手册合同表体备案料件
	// */
	// public void saveDzscEmsPorImg(DzscEmsPorImg img) throws
	// DataAccessException {
	// this.saveOrUpdate(img);
	// }
	//
	// /**
	// * 删除电子手册合同表体备案料件
	// */
	// public void deleteDzscEmsPorImg(DzscEmsPorImg img) {
	// this.delete(img);
	// }
	//
	// /**
	// * 删除电子手册合同表体备案成品
	// */
	// public void deleteDzscEmsPorExg(DzscEmsPorExg exg) {
	// this.delete(exg);
	// }
	//
	// /**
	// * 保存电子手册合同表体备案成品
	// */
	// public void saveDzscEmsPorExg(DzscEmsPorExg exg) throws
	// DataAccessException {
	// this.saveOrUpdate(exg);
	// }

	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgBill(DzscEmsPorHead head) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgBillModifyMark(DzscEmsPorHead head,
			String modifyMark) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ? and a.modifyMark=? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), modifyMark });
	}

	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorExgBillModifyMark(DzscEmsPorHead head,
			String modifyMark) {
		return this
				.find(
						"select a from DzscEmsExgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ? and a.modifyMark=? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), modifyMark });
	}

	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public int findMaxEmsPorImgSeqNumExceptAdd(DzscEmsPorHead head) {
		List list = this
				.find(
						"select max(a.seqNum) from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ? and a.modifyMark<>?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), ModifyMarkState.ADDED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public int findMaxEmsPorExgSeqNumExceptAdd(DzscEmsPorHead head) {
		List list = this
				.find(
						"select max(a.seqNum) from DzscEmsExgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ? and a.modifyMark<>?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), ModifyMarkState.ADDED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgByParentId(DzscEmsPorHead head) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id=?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), head.getId() });

	}

	/**
	 * 进口金额合计
	 * 
	 * @param head
	 *            通关备案表头
	 * @return Double 手册进口金额合计
	 */
	public Double sumMoney(DzscEmsPorHead head) {
		List list = this
				.find(
						"select sum(a.money) from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list != null && list.size() > 0) {
			return formatDouble((Double) list.get(0));
		}
		return Double.valueOf(0);
	}

	/**
	 * 保存通关备案料件资料
	 * 
	 * @param obj
	 *            通关备案料件资料
	 */
	public void saveDzscEmsImgBill(DzscEmsImgBill obj)
			throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存通关备案料件资料
	 * 
	 * @param obj
	 *            通关备案料件资料
	 */
	public void saveDzscEmsImgBill(List List)
			throws DataAccessException {
		for (int i = 0; i < List.size(); i++) {
			this.saveOrUpdate(List.get(i));
		}
	}
	/**
	 * 删除通关备案料件资料
	 * 
	 * @param obj
	 *            通关备案料件资料
	 */
	public void deleteDzscEmsImgBill(DzscEmsImgBill obj) {
		this.delete(obj);
	}

	/**
	 * 删除通关备案BOM资料
	 * 
	 * @param obj
	 *            通关备案BOM资料
	 */
	public void deleteDzscEmsBomBill(DzscEmsBomBill obj) {
		this.delete(obj);
	}

	/**
	 * 删除通关备案成品资料
	 * 
	 * @param obj
	 *            通关备案成品资料
	 */
	public void deleteDzscEmsExgBill(DzscEmsExgBill obj) {
		deleteAllBomForExg(obj);
		this.delete(obj);
	}

	/**
	 * 返回通关备案BOM资料 根据通关备案表头
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findEmsPorBomBill(DzscEmsPorHead head) {
		return this
				.find(
						"select a from DzscEmsBomBill a where a.company.id = ? and a.dzscEmsExgBill.dzscEmsPorHead.id=?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回通关备案成品资料
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品资料
	 */
	public List findDzscEmsExgBill(DzscEmsPorHead head) {
		return this.find(
				"select a from DzscEmsExgBill a where a.company.id = ?"
						+ " and a.dzscEmsPorHead.id = ?"
						+ " order by a.seqNum ", new Object[] {
						CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 根据商品序号，查询成品商品信息
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @param exgSeqNum
	 *            商品序号
	 * @return List 是DzscEmsBomBill类型，电子手册手册Bom资料
	 */
	public List<DzscEmsBomBill> findBomByExg(String emsNo, Integer exgSeqNum) {
		String hsql = "select a from DzscEmsBomBill as a "
				+ " left join a.dzscEmsExgBill as b " + " where b.seqNum=? "
				+ " and b.dzscEmsPorHead.emsNo=?"
				+ " and b.dzscEmsPorHead.declareState=? "
				+ " and b.dzscEmsPorHead.company.id=? ";
		return this.find(hsql, new Object[] { Integer.valueOf(exgSeqNum),
				emsNo, DzscState.EXECUTE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询电子手册手册商品数量总量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return double 返回手册商品数量总量
	 */
	public double findCommInfoTotalAmountByXiao(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " left join a.baseCustomsDeclaration b "
				+ " where b.company.id=? "
				+ " and b.effective=? "
				+ " and b.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and b.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and b.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and b.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " b.tradeMode.code=? ";
				} else {
					hsql += " b.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 返回通关备案成品资料
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品资料
	 */
	public List findDzscEmsExgBillStateChanged(DzscEmsPorHead head) {
		return this.find(
				"select a from DzscEmsExgBill a where a.company.id = ?"
						+ " and a.dzscEmsPorHead.id = ?"
						+ " and a.modifyMark<>? order by a.seqNum ",
				new Object[] { CommonUtils.getCompany().getId(), head.getId(),
						ModifyMarkState.UNCHANGE });
	}

	/**
	 * 返回通关备案成品资料
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品资料
	 */
	public List findDzscEmsExgBillSeqNum(DzscEmsPorHead head) {
		return this.find(
				"select a.seqNum from DzscEmsExgBill a where a.company.id = ?"
						+ " and a.dzscEmsPorHead.id = ?"
						+ " order by a.seqNum ", new Object[] {
						CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 保存通关备案成品资料
	 * 
	 * @param obj
	 *            通关备案成品资料
	 */
	public void saveDzscEmsExgBill(DzscEmsExgBill obj)
			throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	/**
	 * 返回通关备案BOM资料
	 * 
	 * @param exg
	 *            通关备案成品资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findEmsPorBomBill(DzscEmsExgBill exg) {
		return this
				.find(
						"select a from DzscEmsBomBill a where a.company.id = ?  and a.dzscEmsExgBill.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								exg.getId() });
	}

	public DzscEmsImgBill getDzscEmsImgBillFromDzscEmsBomBill(String headId,
			Integer imgSeqNum) {
		List<DzscEmsImgBill> list = this
				.find(
						"select a from DzscEmsImgBill a where a.company.id = ?  and a.dzscEmsPorHead.id=? and a.seqNum=?",
						new Object[] { CommonUtils.getCompany().getId(),
								headId, imgSeqNum });
		if (list != null && list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 在通关备案的料件备案中查找料件，但是这些料件没有成为被指定成品的BOM资料
	 * 
	 * @param exg
	 *            通关备案成品资料
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgBillToBill(DzscEmsExgBill exg) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?"
								+ " and a.seqNum not in "
								+ " (select b.imgSeqNum from DzscEmsBomBill b where b.dzscEmsExgBill.id = ?)",
						new Object[] { CommonUtils.getCompany().getId(),
								exg.getDzscEmsPorHead().getId(), exg.getId() });
	}

	/**
	 * 保存通关备案BOM资料
	 * 
	 * @param bom
	 *            通关备案BOM资料
	 */
	public void saveDzscEmsBomBill(DzscEmsBomBill bom)
			throws DataAccessException {
		this.saveOrUpdate(bom);
	}

	//
	// /**
	// * 获取符合前四位商品编码
	// *
	// * @param code
	// * @param type
	// * 物料类型
	// * @return List 是DzscInnerMergeData型，归并数据
	// */
	// public List findFourComplex(String code, String type) {
	// return this
	// .find(
	// "select distinct a.tenComplex from DzscInnerMergeData a where a.imrType =
	// ?",
	// new Object[] { type });
	// }

	/**
	 * 使用该料件的成品列表
	 * 
	 * @param img
	 *            通关备案料件资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findExgBillForImg(DzscEmsImgBill img) {
		return this.find(
				"select a from DzscEmsBomBill a where a.imgSeqNum=? and a.complex=?"
						+ " and a.dzscEmsExgBill.dzscEmsPorHead.id=? ",
				new Object[] { img.getSeqNum(), img.getComplex(),
						img.getDzscEmsPorHead().getId() });
	}

	/**
	 * 判断在通关备案BOM资料中是否存在通关备案料件资料
	 * 
	 * @param img
	 *            通关备案料件资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findImgFromBom(DzscEmsImgBill img) {
		return this.find(
				"select a from DzscEmsBomBill a where a.dzscEmsExgBill.dzscEmsPorHead.id = ? "
						+ "and a.imgSeqNum = ?", new Object[] {
						img.getDzscEmsPorHead().getId(), img.getSeqNum() });
	}

	/**
	 * 删除成品对应所有单耗
	 * 
	 * @param exg
	 *            通关备案成品资料
	 */
	public void deleteAllBomForExg(DzscEmsExgBill exg) {
		this.deleteAll(findEmsPorBomBill(exg));
	}

	/**
	 * 检查是否重复变更
	 * 
	 * @param head
	 *            通关备案表头
	 * @param state
	 *            申报状态
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findIsRepeatChange(DzscEmsPorHead head, String state) {
		return this.find(
				"select a from DzscEmsPorHead a where a.company.id = ?"
						+ " and a.emsNo = ? and a.declareState = ?",
				new Object[] { CommonUtils.getCompany().getId(),
						head.getEmsNo(), state });
	}

	/**
	 * 删除电子手册合同
	 * 
	 * @param head
	 *            通关备案表头
	 */
	public void deleteDzscEmsPorHead(DzscEmsPorHead head) {
		this.delete(head);
	}

	/**
	 * 获取正在执行的通关备案表头
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findExeEmsPorHead() {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.company.id = ? and a.declareState = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImgPtNo(DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a.materiel.ptNo from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? ", new Object[] {
						CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExgPtNo(DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a.materiel.ptNo from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? ", new Object[] {
						CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExg(DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? "
						+ " order by a.dzscEmsExgBill.seqNum,a.materiel.ptNo ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExgStateChanged(
			DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? and a.modifyMark<>? "
						+ " order by a.dzscEmsExgBill.seqNum,a.materiel.ptNo ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId(), ModifyMarkState.UNCHANGE });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExgPtNoSeqNum(
			DzscEmsPorHead dzscEmsPorHead) {
		return this
				.find(
						" select a.dzscEmsExgBill.seqNum,a.materiel.ptNo,a.modifyMark"
								+ " from DzscEmsPorMaterialExg a where a.company.id = ? "
								+ " and a.dzscEmsPorHead.id=? "
								+ " order by a.dzscEmsExgBill.seqNum,a.materiel.ptNo ",
						new Object[] { CommonUtils.getCompany().getId(),
								dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExg(DzscEmsExgBill dzscEmsExgBill) {
		return this.find(
				"select a from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsExgBill.id=? ", new Object[] {
						CommonUtils.getCompany().getId(),
						dzscEmsExgBill.getId() });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public int findDzscEmsPorMaterialExgCount(DzscEmsExgBill dzscEmsExgBill) {
		List list = this.find(
				"select a from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsExgBill.id=? and a.modifyMark<>? ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsExgBill.getId(), ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExg(String emsNo, Materiel materiel) {
		return this.find(
				"select a from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.emsNo=? "
						+ " and a.materiel=? "
						+ " and a.dzscEmsPorHead.declareState = ?",
				new Object[] { CommonUtils.getCompany().getId(), emsNo,
						materiel, DzscState.EXECUTE });
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImg(DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? "
						+ " order by a.dzscEmsImgBill.seqNum,a.materiel.ptNo ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImgStateChanged(
			DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? and a.modifyMark<>? "
						+ " order by a.dzscEmsImgBill.seqNum,a.materiel.ptNo ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId(), ModifyMarkState.UNCHANGE });
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImgPtNoSeqNum(
			DzscEmsPorHead dzscEmsPorHead) {
		return this
				.find(
						" select a.dzscEmsImgBill.seqNum,a.materiel.ptNo,a.modifyMark"
								+ " from DzscEmsPorMaterialImg a where a.company.id = ? "
								+ " and a.dzscEmsPorHead.id=? "
								+ " order by a.dzscEmsImgBill.seqNum,a.materiel.ptNo ",
						new Object[] { CommonUtils.getCompany().getId(),
								dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImg(DzscEmsImgBill dzscEmsImgBill) {
		return this.find(
				"select a from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsImgBill.id=? ", new Object[] {
						CommonUtils.getCompany().getId(),
						dzscEmsImgBill.getId() });
	}

	/**
	 * 获取正在执行的通关备案料件归并个数（除去已删除的）
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public int findDzscEmsPorMaterialImgCount(DzscEmsImgBill dzscEmsImgBill) {
		List list = this.find(
				"select a from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsImgBill.id=? and a.modifyMark<>? ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsImgBill.getId(), ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImg(String emsNo, Materiel materiel) {
		return this.find(
				"select a from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.emsNo=? "
						+ " and a.materiel=? "
						+ " and a.dzscEmsPorHead.declareState = ?",
				new Object[] { CommonUtils.getCompany().getId(), emsNo,
						materiel, DzscState.EXECUTE });
	}

	/**
	 * 返回电子手册合同里的料件
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public List findEmsPorImgWj(DzscEmsPorWjHead head) {
		return this.find("select a from DzscEmsImgWj a where a.company.id = ? "
				+ " and a.dzscEmsPorWjHead.id = ?", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 返回电子手册合同里的料件项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public int findEmsPorImgWjCount(DzscEmsPorWjHead head) {
		List list = this
				.find(
						"select count(a.id) from DzscEmsImgWj a where a.company.id = ? "
								+ " and a.dzscEmsPorWjHead.id = ?  and a.modifyMark<>? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 返回电子手册合同里的料件项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public int findDzscEmsImgBillWjCount(DzscEmsPorHead head) {
		List list = this.find(
				"select count(a.id) from DzscEmsImgBill a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id = ?  and a.modifyMark<>? ",
				new Object[] { CommonUtils.getCompany().getId(), head.getId(),
						ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 返回电子手册合同里的料件
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public List findEmsPorImgWjStateChanged(DzscEmsPorWjHead head) {
		return this.find("select a from DzscEmsImgWj a where a.company.id = ? "
				+ "and a.dzscEmsPorWjHead.id = ?  and a.modifyMark<>? ",
				new Object[] { CommonUtils.getCompany().getId(), head.getId(),
						ModifyMarkState.UNCHANGE });
	}

	/**
	 * 返回合同备案成品
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgWj型，合同备案成品
	 */
	public List findEmsPorExgWj(DzscEmsPorWjHead head) {
		return this
				.find(
						"select a from DzscEmsExgWj a where a.company.id = ? and a.dzscEmsPorWjHead.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回电子手册合同里的成品项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public int findEmsPorExgWjCount(DzscEmsPorWjHead head) {
		List list = this
				.find(
						"select count(a.id) from DzscEmsExgWj a where a.company.id = ? "
								+ " and a.dzscEmsPorWjHead.id = ?  and a.modifyMark<>? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 返回合同备案成品
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgWj型，合同备案成品
	 */
	public List findEmsPorExgWjStateChanged(DzscEmsPorWjHead head) {
		return this.find("select a from DzscEmsExgWj a where a.company.id = ?"
				+ " and a.dzscEmsPorWjHead.id = ? and a.modifyMark<>? ",
				new Object[] { CommonUtils.getCompany().getId(), head.getId(),
						ModifyMarkState.UNCHANGE });
	}

	/**
	 * 保存合同备案表头
	 * 
	 * @param obj
	 *            合同备案表头
	 */
	public void saveEmsPorWj(DzscEmsPorWjHead obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存合同备案料件
	 * 
	 * @param obj
	 *            合同备案料件
	 */
	public void saveDzscEmsImgWj(DzscEmsImgWj obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param obj
	 *            合同备案成品
	 */
	public void saveDzscEmsExgWj(DzscEmsExgWj obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 返回电子手册归并4码
	 * 
	 * @param type
	 *            物料类型
	 * @param dzscEmsPorWjHead
	 *            通关备案表头
	 * @return List 存放电子手册商品归并资料的第三层资料（四位编码序号、四位编码名称、四位商品规格、四位商品单位、四位商品编码）
	 * 
	 */
	public List findMerger4ToWj(String type, DzscEmsPorWjHead dzscEmsPorWjHead) {
		String tableName = "";
		if (type.equals(MaterielType.MATERIEL)) {
			tableName = "DzscEmsImgWj";
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
			tableName = "DzscEmsExgWj";
		}
		return this
				.find(
						"select distinct a.dzscTenInnerMerge.dzscFourInnerMerge "
								+ " from DzscInnerMergeData a "
								+ " where a.imrType = ? and a.company.id = ? "
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge is not null"
								+ " and a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum not in "
								+ "( select b.fourSeqNum from "
								+ tableName
								+ " b where b.dzscEmsPorWjHead.id = ?) and a.stateMark = ?",
						new Object[] { type, CommonUtils.getCompany().getId(),
								dzscEmsPorWjHead.getId(), DzscState.EXECUTE });
		// return this
		// .find(
		// "select distinct
		// a.fourSeqNum,a.fourComplex,a.fourPtName,a.fourPtSpec,a.fourUnit "
		// + " from DzscInnerMergeData a "
		// + " left join a.fourUnit"
		// + " where a.imrType = ? and a.company.id = ? and a.fourSeqNum is not
		// null"
		// + " and a.fourSeqNum not in (select b.fourSeqNum from "
		// + tableName
		// + " b where b.dzscEmsPorWjHead.id = ?) and a.stateMark = ?",
		// new Object[] { type, CommonUtils.getCompany().getId(),
		// dzscEmsPorWjHead.getId(), DzscState.EXECUTE });
	}

	/**
	 * 返回归并关系四码distinct--合同备案新增上方2005-8-25
	 * 
	 * @param type
	 *            物料类型
	 * @return List 存放电子手册商品归并资料的第三层资料（四位编码序号、四位编码名称、四位商品规格、四位商品单位、四位商品编码）
	 */
	public List findMerger4Distinct(String type) {
		return this
				.find(
						"select distinct a.fourSeqNum,a.fourComplex,a.fourPtName,a.fourPtSpec,a.fourUnit.name "
								+ " from DzscInnerMergeData a "
								+ " left join a.fourUnit "
								+ " where a.imrType = ? and a.company.id = ? "
								+ " and a.fourSeqNum is not null and a.stateMark=? ",
						new Object[] { type, CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 返回归并关系十码distinct--合同备案新增下方2005-8-25
	 * 
	 * @param type
	 *            物料类型
	 * @param fourSeqNum
	 *            四位编码序号
	 * @return
	 * @return List 存放电子手册商品归并资料的第二层资料（十位编码序号、十位商品编码、十位商品名称、十位商品规格、十位商品单位）
	 */
	public List findMerger10Distince(String type, String fourSeqNum) {
		return this
				.find(
						"select distinct a.tenSeqNum,a.tenComplex.code,a.tenPtName,a.tenPtSpec,a.tenUnit.name "
								+ " from DzscInnerMergeData a"
								+ " left join a.tenComplex "
								+ " left join a.tenUnit "
								+ " where a.imrType = ? and a.company.id = ? and a.fourSeqNum is not null"
								+ " and a.fourSeqNum = ? and a.stateMark=? ",
						new Object[] { type, CommonUtils.getCompany().getId(),
								fourSeqNum, DzscState.EXECUTE });
	}

	/**
	 * 根据十吗得到十吗
	 * 
	 * @param type
	 *            物料类型
	 * @param tenseqnum
	 *            十位编码序号
	 * @return List 存放电子手册商品归并资料的第二层资料（十位编码序号、十位商品编码、十位商品名称、十位商品规格、十位商品单位）
	 */
	public List findMerger10BytenSeqnum(String type, String tenseqnum) {
		return this
				.find(
						"select distinct a.tenSeqNum,a.tenComplex.code,a.tenPtName,a.tenPtSpec,a.tenUnit.name "
								+ " from DzscInnerMergeData a "
								+ " left join a.tenComplex "
								+ " left join a.tenUnit "
								+ " where a.imrType = ? and a.company.id = ? "
								+ " and a.tenSeqNum = ? and a.stateMark=? ",
						new Object[] { type, CommonUtils.getCompany().getId(),
								tenseqnum, DzscState.EXECUTE });
	}

	//
	// /**
	// * 抓取商品归并的十码资料,十码不在通关备案里
	// *
	// * @param head
	// * 通关备案表头
	// * @param type
	// * 物料类型
	// * @return List 存放电子手册商品归并资料的第二层资料（十位编码序号、十位商品编码、十位商品名称、十位商品规格、十位商品单位）
	// */
	// public List findMerger10ForEmsPor(DzscEmsPorHead head, String type) {
	// List<Object> parameters = new ArrayList<Object>();
	// String hql = "select distinct
	// a.tenSeqNum,a.tenComplex,a.tenPtName,a.tenPtSpec,a.tenUnit "
	// + " from DzscInnerMergeData a "
	// + " left join a.tenComplex "
	// + " left join a.tenUnit "
	// + " where a.imrType = ? and a.company.id = ? "
	// + " and a.stateMark=? ";
	// parameters.add(type);
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(DzscState.EXECUTE);
	// if (type.equals(MaterielType.MATERIEL)) {
	// hql += " and a.tenSeqNum not in (select b.tenSeqNum "
	// + " from DzscEmsImgBill b where b.dzscEmsPorHead.id=? )";
	// parameters.add(head.getId());
	// } else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
	// hql += " and a.tenSeqNum not in (select b.tenSeqNum "
	// + " from DzscEmsExgBill b where b.dzscEmsPorHead.id=? )";
	// parameters.add(head.getId());
	// }
	// return this.find(hql, parameters.toArray());
	// }

	/**
	 * 从内部归并中抓取通关备案需要的数据,4码在合同备案里，十码不在通关备案里
	 * 
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            判断是否料件，true表示为料件
	 * @return List 存放了内部归并的十码和四码的资料
	 */
	public List findMerger10ForEmsPor(DzscEmsPorHead head, boolean isMaterial) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select distinct a.dzscTenInnerMerge "
				+ " from DzscInnerMergeData a "
				+ " left join a.dzscTenInnerMerge.tenComplex "
				+ " left join a.dzscTenInnerMerge.dzscFourInnerMerge "
				+ " where a.company.id = ? "
				+ " and a.stateMark=? and a.imrType = ?"
				+ " and a.dzscTenInnerMerge.dzscFourInnerMerge is not null ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DzscState.EXECUTE);
		if (isMaterial) {
			parameters.add(MaterielType.MATERIEL);
			hql += " and a.dzscTenInnerMerge.tenSeqNum not in (select b.tenSeqNum "
					+ " from DzscEmsImgBill b where b.dzscEmsPorHead.id=? )";
			parameters.add(head.getId());
			hql += " and a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum in "
					+ " (select b.fourSeqNum "
					+ " from DzscEmsImgWj b where b.dzscEmsPorWjHead.emsNo=? "
					+ " and b.dzscEmsPorWjHead.declareState=? "
					+ " and b.dzscEmsPorWjHead.company.id=? )";
			parameters.add(head.getCorrEmsNo());
			parameters.add(DzscState.EXECUTE);
			parameters.add(CommonUtils.getCompany().getId());
		} else {
			parameters.add(MaterielType.FINISHED_PRODUCT);
			hql += " and a.dzscTenInnerMerge.tenSeqNum not in (select b.tenSeqNum "
					+ " from DzscEmsExgBill b where b.dzscEmsPorHead.id=? )";
			parameters.add(head.getId());
			hql += " and a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum in "
					+ " (select b.fourSeqNum "
					+ " from DzscEmsExgWj b where b.dzscEmsPorWjHead.emsNo=? "
					+ " and b.dzscEmsPorWjHead.declareState=? "
					+ " and b.dzscEmsPorWjHead.company.id=? )";
			parameters.add(head.getCorrEmsNo());
			parameters.add(DzscState.EXECUTE);
			parameters.add(CommonUtils.getCompany().getId());
		}
		// hql += " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum,"
		// + " a.dzscTenInnerMerge.tenSeqNum";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 删除合同备案料件
	 * 
	 * @param obj
	 *            合同备案料件
	 */
	public void deleteDzscEmsImgWj(DzscEmsImgWj obj) {
		this.delete(obj);
	}

	/**
	 * 删除合同备案成品
	 * 
	 * @param obj
	 *            合同备案成品
	 */
	public void deleteDzscEmsExgWj(DzscEmsExgWj obj) {
		this.delete(obj);
	}

	/**
	 * 保存通关备案表头
	 * 
	 * @param obj
	 *            通关备案表头
	 * @return obj 通关备案表头
	 */
	public DzscEmsPorWjHead saveEmsPorWjHead(DzscEmsPorWjHead obj) {
		this.saveOrUpdate(obj);
		return obj;
	}

	/**
	 * 删除合同备案表头
	 * 
	 * @param head
	 *            合同备案表头
	 */
	public void deleteAllEmsPorWj(DzscEmsPorWjHead head) {
		this.deleteAll(findEmsPorExgWj(head));
		this.deleteAll(findEmsPorImgWj(head));
	}

	/**
	 * 删除合同备案表头
	 * 
	 * @param head
	 *            合同备案表头
	 */
	public void deleteDzscEmsPorWjHead(DzscEmsPorWjHead head) {
		this.delete(head);
	}

	/**
	 * 检查是否重复变更 -- 合同备案
	 * 
	 * @param head
	 *            通关备案表头
	 * @param state
	 *            申报状态
	 * @return List 是DzscEmsPorWjHead型，通关备案表头
	 */
	public List findIsRepeatChangeWj(DzscEmsPorWjHead head, String state) {
		return this.find(
				"select a from DzscEmsPorWjHead a where a.company.id = ? "
						+ " and a.emsNo = ? and a.declareState = ?",
				new Object[] { CommonUtils.getCompany().getId(),
						head.getEmsNo(), state });
	}

	/**
	 * 查询合同备案表头
	 * 
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public List findDzscEmsPorWjHead(boolean isCancel) {
		String hql = "select a from DzscEmsPorWjHead a where a.company.id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (isCancel) {
			hql += " and a.declareState=? ";
		} else {
			hql += " and a.declareState<>? ";
		}
		parameters.add(DzscState.CHECK_CANCEL);
		hql += " order by a.emsNo,a.seqNum";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询合同备案表头
	 * 
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public DzscEmsPorWjHead findDzscEmsPorWjHeadById(String id) {
		List list = this
				.find(
						"select a from DzscEmsPorWjHead a where a.company.id = ? and a.id=? ",
						new Object[] { CommonUtils.getCompany().getId(), id });
		if (list.size() > 0) {
			return (DzscEmsPorWjHead) list.get(0);
		}
		return null;
	}

	/**
	 * 根据合同号码查询合同备案进口总金额
	 * 
	 * @param contractNo
	 *            合同号码
	 * @return Double 进口总金额
	 */
	public Double findWjHeadMoneyByContractNo(String contractNo) {
		List list = this.find(
				"select a from DzscEmsPorWjHead a where a.company.id = ?"
						+ " and a.ieContractNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), contractNo });
		if (list.size() <= 0) {
			return 0.0;
		} else {
			return ((DzscEmsPorWjHead) list.get(0)).getImgAmount() == null ? 0.0
					: ((DzscEmsPorWjHead) list.get(0)).getImgAmount();
		}
	}

	/**
	 * 返回通关备案料件进口金额
	 * 
	 * @param img
	 *            通关备案料件
	 * @param head
	 *            通关备案表头
	 * @return Double 进口金额
	 */
	public Double findEmsPorImgBillMoney(DzscEmsImgBill img, DzscEmsPorHead head) {
		List list = this
				.find(
						"select sum(a.money) from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?"
								+ " and a.id <> ?", new Object[] {
								CommonUtils.getCompany().getId(), head.getId(),
								FormatNull(img.getId()) });
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 返回通关备案料件进口金额
	 * 
	 * @param head
	 *            通关备案表头
	 * @return Double 进口金额
	 */
	public Double findEmsPorImgBillMoney(DzscEmsPorHead head) {
		List list = this
				.find(
						"select sum(a.money) from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 返回通关备案成品出口金额
	 * 
	 * @param head
	 *            通关备案表头
	 * @return Double 出口金额
	 */
	public Double findEmsPorExgBillMoney(DzscEmsPorHead head) {
		List list = this
				.find(
						"select sum(a.money) from DzscEmsExgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 返回通关备案BOM总耗用量
	 * 
	 * @param head
	 *            通关备案表头
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return Double 总耗用量
	 */
	public Double findEmsPorImgTotalNum(DzscEmsPorHead head, Integer imgSeqNum) {
		List list = this
				.find(
						"select sum(a.amount) from DzscEmsBomBill a where a.dzscEmsExgBill.dzscEmsPorHead.id = ?"
								+ " and a.imgSeqNum = ?", new Object[] {
								head.getId(), imgSeqNum });
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 格式化数据，当String为Null是返回空格，否则返回原来的值
	 * 
	 * @param id
	 * @return id
	 */
	private String FormatNull(String id) {
		if (id == null) {
			return "";
		}
		return id;
	}

	/**
	 * 查找对应表的资料，根据code
	 * 
	 * @param className
	 *            对应表
	 * @param code
	 * @return List 是对应表型
	 */
	public List findCustomObject(String className, String code) {
		return this.find("select a from " + className + " a where a.code = ?",
				new Object[] { code });
	}

	/**
	 * 返回通关备案成品单耗为null的数据
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsBomBill型，通关备案BOm
	 */
	public List findDmIsNull(DzscEmsPorHead head) {
		return this
				.find(
						"select a from DzscEmsBomBill a where a.dzscEmsExgBill.dzscEmsPorHead = ? and (a.unitWare is null or a.unitWare = ?)",
						new Object[] { head, Double.valueOf(0) });
	}

	/**
	 * 返回通关备案料件数量为null的数据
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgBill型，通关备案料件
	 */
	public List findImgIsNull(DzscEmsPorHead head) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.dzscEmsPorHead = ? and (a.amount is null or a.amount = ?)",
						new Object[] { head, Double.valueOf(0) });
	}

	// 
	/**
	 * 返回通关备案成品数量为null的数据
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品
	 */
	public List findExgIsNull(DzscEmsPorHead head) {
		return this
				.find(
						"select a from DzscEmsExgBill a where a.dzscEmsPorHead = ? and (a.amount is null or a.amount = ?)",
						new Object[] { head, Double.valueOf(0) });
	}

	/**
	 * 查找合同备案合同料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            合同备案合同表头Id
	 * @return list 是DzscEmsImgWj型，合同备案料件
	 */
	public List findDzscEmsImgWjByParentId(String parentId) {
		//
		// from DzscEmsImgWj a where a.dzscEmsPorWjHead.id = ? and
		// a.no in (select b.imgSeqNum from DzscEmsBomWj b where
		// b.dzscEmsExgWj.dzscEmsPorWjHead.id = ? )
		//
		return this
				.find(
						"select a from DzscEmsImgWj a where a.dzscEmsPorWjHead.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 查找合同备案合同料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            合同备案合同表头Id
	 * @return list 是DzscEmsImgWj型，合同备案料件
	 */
	public List findDzscEmsExgWjByParentId(String parentId) {
		//
		// from DzscEmsImgWj a where a.dzscEmsPorWjHead.id = ? and
		// a.no in (select b.imgSeqNum from DzscEmsBomWj b where
		// b.dzscEmsExgWj.dzscEmsPorWjHead.id = ? )
		//
		return this
				.find(
						"select a from DzscEmsExgWj a where a.dzscEmsPorWjHead.id = ? ",
						new Object[] { parentId });
	}

	// /**
	// * 查找合同BOM 来自 合同成品ID
	// *
	// * @param parentId
	// * @return
	// */
	// public List findDzscEmsBomWjByContractParentId(String parentId) {
	// return this
	// .find(
	// "from DzscEmsBomWj a where a.dzscEmsExgWj.dzscEmsPorWjHead.id = ? ",
	// new Object[] { parentId });
	// }

	/**
	 * 查询通关备案成品分页
	 * 
	 * @param parentId
	 *            通关备案通关备案表头
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @return List 是DzscEmsExgBill型，通关备案成品
	 */
	public List findDzscEmsExgBill(String parentId, int index, int length) {
		return this
				.findPageList(
						"select c from DzscEmsExgBill c where c.dzscEmsPorHead.id=? order by c.seqNum ",
						parentId, index, length);
	}

	public List findDzscEmsExgBillUNCHANGE(String parentId) {
		return this
				.find(
						"select c from DzscEmsExgBill c where c.dzscEmsPorHead.id=? and c.modifyMark <> ? order by c.seqNum ",
						new Object[] { parentId, ModifyMarkState.UNCHANGE });
	}

	/**
	 * 查找通关备案成品 来自 手册号(正在执行)
	 * 
	 * @param emsNo
	 *            手册号
	 * @return List 是DzscEmsExgBill型，通关备案成品
	 */
	public List findDzscEmsExgBillByEmsNo(String emsNo) {
		return this.find(
				"select a from DzscEmsExgBill a where a.dzscEmsPorHead.emsNo = ? "
						+ " a.declareState=? "
						+ " and a.contract.company.id=? "
						+ " order by a.seqNum ", new Object[] { emsNo,
						DzscState.EXECUTE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询通关备案成品分页
	 * 
	 * @param headId
	 *            通关备案通关备案表头Id
	 * @param tenSeqNum
	 *            凭证序号
	 * @return List 是DzscEmsExgBill型，通关备案成品
	 */
	public List findDzscEmsExgBillByTenSeqNum(String headId, Integer tenSeqNum) {
		return this
				.find(
						"select c from DzscEmsExgBill c where c.dzscEmsPorHead.id=? and c.tenSeqNum=? ",
						new Object[] { headId, tenSeqNum });
	}

	/**
	 * 查找通关备案料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBill(DzscEmsPorHead head) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
						+ " order by a.seqNum ", new Object[] { head.getId() });
	}

	/**
	 * 查找通关备案料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBill(String headId) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
						+ " order by a.seqNum ", new Object[] { headId });
	}

	/**
	 * 查找通关备案料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillStateChanged(DzscEmsPorHead head) {
		return this.find("select a from DzscEmsImgBill a "
				+ "where a.dzscEmsPorHead.id = ? and a.modifyMark<>? "
				+ " order by a.seqNum ", new Object[] { head.getId(),
				ModifyMarkState.UNCHANGE });
	}

	/**
	 * 查找通关备案料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillSeqNum(DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a.seqNum from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
						+ " order by a.seqNum ", new Object[] { dzscEmsPorHead
						.getId() });
	}

	/**
	 * 查找通关备案料件 来自 手册号(正在执行)
	 * 
	 * @param emsNo
	 *            手册号
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillByEmsNo(String emsNo) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.emsNo = ? "
						+ " a.dzscEmsPorHead.declareState=? "
						+ " and a.company.id=? " + " order by a.seqNum ",
				new Object[] { emsNo, DzscState.EXECUTE,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找通关备案料件 根据 通关备案合同和料件序号
	 * 
	 * @param dzscEmsHeadId
	 *            通关备案通关备案表头Id
	 * @param seqNum
	 *            凭证序号
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillBySeqNum(String dzscEmsHeadId, Integer seqNum) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
						+ " and a.seqNum=? ", new Object[] { dzscEmsHeadId,
						seqNum });
	}

	/**
	 * 查找通关备案料件 根据 通关备案合同和料件序号
	 * 
	 * @param dzscEmsHeadId
	 *            通关备案通关备案表头Id
	 * @param tenSeqNum
	 *            凭证序号
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillByTenSeqNum(String dzscEmsHeadId,
			Integer tenSeqNum) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
						+ " and a.tenSeqNum=? ", new Object[] { dzscEmsHeadId,
						tenSeqNum });
	}

	/**
	 * 查找通关备案料件 根据 通关备案合同和料件序号
	 * 
	 * @param dzscEmsHeadId
	 *            通关备案通关备案表头Id
	 * @param seqNum
	 *            凭证序号
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsExgBillBySeqNum(String dzscEmsHeadId, Integer seqNum) {
		return this.find(
				"select a from DzscEmsExgBill a where a.dzscEmsPorHead.id = ? "
						+ " and a.seqNum=? ", new Object[] { dzscEmsHeadId,
						seqNum });
	}

	/**
	 * 获料件
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public DzscEmsImgBill findDzscEmsImgBillByEmsNoSeqNum(String emsNo,
			Integer seqNum) {
		List list = this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.emsNo = ? "
						+ " and a.dzscEmsPorHead.declareState=? "
						+ " and a.seqNum=? and a.dzscEmsPorHead.company.id=?",
				new Object[] { emsNo,  DzscState.EXECUTE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			return null;
		} else {
			return (DzscEmsImgBill) list.get(0);
		}
	}

	/**
	 * 获得成品
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public DzscEmsExgBill findDzscEmsExgBillByEmsNoSeqNum(String emsNo,
			Integer seqNum) {
		List list = this.find(
				"select a from DzscEmsExgBill a where a.dzscEmsPorHead.emsNo = ? "
						+ " and a.dzscEmsPorHead.declareState=? "
						+ " and a.seqNum=? and a.dzscEmsPorHead.company.id=?",
				new Object[] { emsNo,  DzscState.EXECUTE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			return null;
		} else {
			return (DzscEmsExgBill) list.get(0);
		}
	}

	/**
	 * 查找通关备案BOM 来自 通关备案成品ID
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsBomBill型，通关备案BOM
	 */
	public List findDzscEmsBomBill(String parentId) {
		return this.find("select a from DzscEmsBomBill a"
				+ " where a.dzscEmsExgBill.dzscEmsPorHead.id = ? ",
				new Object[] { parentId });
	}

	/**
	 * 查找通关备案BOM 来自 通关备案成品ID
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsBomBill型，通关备案BOM
	 */
	public List findDzscEmsBomBillByHeadId(String parentId) {

		if (parentId == null) {
			return new ArrayList();
		}
		return this.find(
				"select a from DzscEmsBomBill a left join a.dzscEmsExgBill b "
						+ "  left join b.dzscEmsPorHead c  where c.id=?  ",
				new Object[] { parentId });
	}

	/**
	 * 查找通关备案BOM 来自 通关备案成品SeqNum
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsBomBill型，通关备案BOM
	 */
	public List findDzscEmsBomBillByExgSeqNum(DzscEmsPorHead dzscEmsPorHead,
			Integer exgSeqNum) {
		return this.find("select a from DzscEmsBomBill a "
				+ " where a.dzscEmsExgBill.dzscEmsPorHead.id = ?"
				+ " and a.dzscEmsExgBill.seqNum=?  "
				+ " and a.dzscEmsExgBill.dzscEmsPorHead.company.id=? ",
				new Object[] { dzscEmsPorHead.getId(), exgSeqNum,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找通关备案BOM 来自 通关备案成品ID
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsBomBill型，通关备案BOM
	 */
	public List findDzscEmsBomBillStateChanged(DzscEmsPorHead head) {
		return this.find(
				" select a from DzscEmsBomBill a " +
				" left outer join fetch a.dzscEmsExgBill "+
                " left outer join fetch a.dzscEmsExgBill.dzscEmsPorHead " +
                " where a.dzscEmsExgBill.dzscEmsPorHead.id = ? "
						+ " and a.modifyMark<>? ", new Object[] { head.getId(),
						ModifyMarkState.UNCHANGE });
	}

	// /**
	// * 查找通关备案BOM 来自 通关备案成品ID
	// *
	// * @param parentId
	// * 通关备案通关备案表头Id
	// * @return List 是DzscEmsBomBill型，通关备案BOM
	// */
	// public List findDzscEmsBomBillImgSeqNum(DzscEmsPorHead dzscEmsPorHead) {
	// return this.find(
	// " select a.dzscEmsExgBill.seqNum,a.imgSeqNum from DzscEmsBomBill a"
	// + " where a.dzscEmsExgBill.dzscEmsPorHead.id = ? "
	// + " order by a.dzscEmsExgBill.seqNum,a.imgSeqNum",
	// new Object[] { dzscEmsPorHead.getId() });
	// }

	/**
	 * 查找通关备案BOM 来自 通关备案成品ID
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsBomBill型，通关备案BOM
	 */
	public List findDzscEmsBomBill(DzscEmsExgBill exgBill) {
		return this
				.find(
						"select a from DzscEmsBomBill a where a.dzscEmsExgBill.id = ? ",
						new Object[] { exgBill.getId() });
	}

	/**
	 * 当前通关备案成品记录总数
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public int findDzscEmsExgBillCount(String contractId) {
		List list = this.find(" select count(a.id) from DzscEmsExgBill as a "
				+ "  where a.dzscEmsPorHead.id=?  and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据手册号和4码编号查询通关备案成品记录总数
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public int findDzscEmsExgBillCount(String emsNo, Integer fourSeqNum) {
		List list = this
				.find(
						" select count(a.id) from DzscEmsExgBill as a ,"
								+ " DzscInnerMergeData as b"
								+ " where a.tenSeqNum=b.dzscTenInnerMerge.tenSeqNum and b.imrType=? "
								+ " and a.dzscEmsPorHead.emsNo=? "
								+ " and b.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum=? ",
						new Object[] { MaterielType.FINISHED_PRODUCT, emsNo,
								fourSeqNum });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前通关备案成品记录总数
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public List findDzscEmsImgBillExceptDeleted(String contractId) {
		return this.find(" select a from DzscEmsImgBill as a "
				+ "  where a.dzscEmsPorHead.id=? and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
	}

	/**
	 * 当前通关备案成品记录总数
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public int findDzscEmsImgBillCount(String contractId) {
		List list = this.find(" select count(a.id) from DzscEmsImgBill as a "
				+ "  where a.dzscEmsPorHead.id=? and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据手册号和4码编号查询通关备案成品记录总数
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public int findDzscEmsImgBillCount(String emsNo, Integer fourSeqNum) {
		List list = this
				.find(
						" select count(a.id) from DzscEmsExgBill as a, "
								+ " DzscInnerMergeData as b"
								+ " where a.tenSeqNum=b.dzscTenInnerMerge.tenSeqNum and b.imrType=? "
								+ " and a.dzscEmsPorHead.emsNo=? "
								+ " and b.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum=? ",
						new Object[] { MaterielType.MATERIEL, emsNo, fourSeqNum });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前通关备案成品记录总数
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public double findDzscEmsExgBillMoney(String contractId) {
		List list = this.find(" select sum(a.money) from DzscEmsExgBill as a "
				+ "  where a.dzscEmsPorHead.id=?  and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 当前通关备案成品金额
	 * 
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public double findDzscEmsImgBillMoney(String contractId) {
		List list = this.find(" select sum(a.money) from DzscEmsImgBill as a "
				+ "  where a.dzscEmsPorHead.id=? and a.modifyMark<>? ",
				new Object[] { contractId, ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 当前合同备案成品记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同备案成品记录总数
	 */
	public int findDzscEmsExgWjCount(String contractId) {
		List list = this.find(
				" select count(a.id) from DzscEmsExgWj as a "
						+ "  where a.dzscEmsPorWjHead.id=? "
						+ " and a.modifyMark<> ? ", new Object[] { contractId,
						ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前合同备案料件记录总数
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同备案成品记录总数
	 */
	public int findDzscEmsImgWjCount(String contractId) {
		List list = this.find(
				" select count(a.id) from DzscEmsImgWj as a "
						+ "  where a.dzscEmsPorWjHead.id=? "
						+ " and a.modifyMark<> ? ", new Object[] { contractId,
						ModifyMarkState.DELETED });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 当前通关备案料件来自通关备案成品
	 * 
	 * @param dzscEmsExgBillList
	 *            通关备案成品
	 * @return List 是DzscEmsImgBill型，通关备案料件
	 */
	public List<DzscEmsImgBill> findDzscEmsImgBill(List dzscEmsExgBillList) {
		if (dzscEmsExgBillList == null || dzscEmsExgBillList.size() <= 0) {
			return null;
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscEmsImgBill a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		for (int i = 0; i < dzscEmsExgBillList.size(); i++) {
			DzscEmsExgBill d = (DzscEmsExgBill) dzscEmsExgBillList.get(i);
			if (i == 0) {
				hsql += " and a.seqNum in ( select b.imgSeqNum "
						+ " from DzscEmsBomBill b where b.dzscEmsExgBill.id = ? ";
			} else {
				hsql += " or b.dzscEmsExgBill.id = ?  ";
			}
			if (i == dzscEmsExgBillList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(d.getId());
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 只显示四码不为空的商品归并信息
	 * 
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            数据的下标号
	 * @param ptNo
	 *            料号
	 * @return List 是DzscInnerMergeData型，商品归并资料
	 */
	public List findMaterielFromInner(String type, int firstIndex, String ptNo) {
		List<Object> paramters = new ArrayList<Object>();
		String sql = "select a.materiel from DzscInnerMergeData a "
				+ " where a.imrType = ? and a.company.id = ? "
				+ " and a.head.type = ?";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(DzscState.EXECUTE);
		if (ptNo != null) {
			sql += " and a.materiel.ptNo like '%" + ptNo + "%'";
		}
		return this.findPageList(sql, paramters.toArray(), firstIndex, 100);
	}

	/**
	 * 查找执行状态的商品归并资料 根据料号、物料类型
	 * 
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return integer，十位编码序号
	 */
	public Integer findSeqNumByPtNo(String ptNo, String type) {
		List list = this
				.find(
						"select a.tenSeqNum from DzscInnerMergeData a "
								+ " where a.imrType = ? and a.company.id = ? and a.materiel.ptNo = ? "
								+ " and a.head.type = ?", new Object[] { type,
								CommonUtils.getCompany().getId(), ptNo,
								DzscState.EXECUTE });
		if (list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return null;
	}

	/**
	 * 查找生效状态的通关备案料件或成品
	 * 
	 * @param seqNum
	 *            序号
	 * @param type
	 *            物料类型，当为MaterielType.MATERIEL时为料件
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsImgBill或DzscEmsExgBill，通关备案料件或成品
	 */
	public List findImgExgBillBySeqNum(Integer seqNum, String type, String emsNo) {
		String obj = "";
		if (type.equals(MaterielType.MATERIEL)) {
			obj = "DzscEmsImgBill";
		} else {
			obj = "DzscEmsExgBill";
		}
		return this.find("select a from " + obj
				+ " a where a.dzscEmsPorHead.emsNo=? "
				+ " and a.dzscEmsPorHead.company.id=? and a.seqNum = ? "
				+ " and a.dzscEmsPorHead.declareState = ?", new Object[] {
				emsNo, CommonUtils.getCompany().getId(), seqNum,
				DzscState.EXECUTE });
	}

	/**
	 * 根据手册号和商品的备案序号查询报关单的数目
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public int findDzscCustomsDeclarationCommInfoCount(String emsNo,
			Integer seqNum, boolean isImg) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = "select count(a.id) from DzscCustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			sql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			sql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		List list = this.find(sql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 由编码得到自用商品编码
	 * 
	 * @param code
	 *            代码
	 * @return complex 自用商品编码,返回符合条件的第一条数据
	 */
	public Complex getComplexByCode(String code) {
		List list = this.find(
				"select a from Complex a left join fetch a.firstUnit"
						+ " left join fetch a.secondUnit where a.code=?",
				new Object[] { code });
		if (list != null && list.size() > 0) {
			return (Complex) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存报关常用物料
	 * 
	 * @param m
	 *            报关常用物料
	 */
	public void saveMateriel(Materiel m) {
		this.saveOrUpdate(m);
	}

	/**
	 * 根据电子手册编号查询手册分册
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHeadFas型，手册分册
	 */
	public List findDzscEmsPorHeadFas(String emsNo) {
		String hql = "select a from DzscEmsPorHeadFas a where a.company.id=?"
				+ " and a.emsNo=? and a.historyState=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				emsNo, new Boolean(false) });
	}

	/**
	 * 根据分册内部编号、手册编号查询手册分册
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param copEmsNo
	 *            企业内部编号
	 * @return List 是DzscEmsPorHeadFas型，手册分册
	 */
	public List findDzscEmsPorHeadFasByCop(String emsNo, String copEmsNo) {
		String hql = "select a from DzscEmsPorHeadFas a where a.company.id=?"
				+ " and a.emsNo=? and a.copEmsNo=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				emsNo, copEmsNo });
	}

	/**
	 * 保存电子手册分册
	 * 
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 */
	public void saveDzscEmsPorHeadFas(DzscEmsPorHeadFas dzscEmsPorHeadFas) {
		this.saveOrUpdate(dzscEmsPorHeadFas);
	}

	/**
	 * 删除电子手册分册
	 * 
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 */
	public void deleteDzscEmsPorHeadFas(DzscEmsPorHeadFas dzscEmsPorHeadFas) {
		this.delete(dzscEmsPorHeadFas);
	}

	public List findDzscEmsPorHeadFasNotApply(String declareState) {
		String hql = "select a from DzscEmsPorHeadFas a where a.company.id=?"
				+ " and a.declareState=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				declareState });
	}

	public List findDzscEmsProHeadFasByFasEmsNoandState(String emsNo,
			String fasEmsNo, String declareState) {
		return this.find(
				" select  a from DzscEmsPorHeadFas a where a.company.id=? "
						+ "and a.emsNo=?  and a.fasEmsNo=? "
						+ "and a.declareState=?", new Object[] {
						CommonUtils.getCompany().getId(), emsNo, fasEmsNo,
						declareState });
	}

	/**
	 * 查询BOM中的料件
	 * 
	 * @param materielType
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findDzscEmsBomBillImg(DzscEmsPorHead dzscEmsPorHead, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from DzscEmsImgBill as a"
				+ " where a.company.id=? and a.dzscEmsPorHead.id=? ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(dzscEmsPorHead.getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.seqNum";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findDzscEmsBomBillByImg(DzscEmsPorHead dzscEmsPorHead,
			DzscEmsImgBill img) {
		return this.find(
				"select a from DzscEmsBomBill a where a.company.id = ?"
						+ " and a.imgSeqNum=? "
						+ " and a.dzscEmsExgBill.dzscEmsPorHead.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						img.getSeqNum(), dzscEmsPorHead.getId() });
	}

	public List findDzscEmsExgBillBySeqNum(String emsNo, String seqNum) {
		return this
				.find(
						"select a from DzscEmsExgBill a where a.company.id=?  "
								+ " and a.dzscEmsPorHead.declareState=? and a.dzscEmsPorHead.emsNo = ? and a.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE, emsNo,
								Integer.valueOf(seqNum) });
	}

	/**
	 * 查询单耗中某一料件的总数量
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param contractImgSeqNum
	 *            料件总表序号
	 * @return List 是int型，单耗中某一料件的总数量
	 */
	public Double findDzscEmsBomImgUsedAmount(DzscEmsPorHead dzscEmsPorHead,
			Integer imgSeqNum) {
		List list = this
				.find(
						"select sum(a.dzscEmsExgBill.amount*(a.unitWare/(1-a.ware/100.0))) from DzscEmsBomBill as a "
								+ " where a.company.id=? "
								+ " and a.dzscEmsExgBill.dzscEmsPorHead.id = ? "
								+ " and a.imgSeqNum=? and a.modifyMark<>? ",
						new Object[] { CommonUtils.getCompany().getId(),
								dzscEmsPorHead.getId(), imgSeqNum,
								ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找合同某项成品总金额来自合同成品Id
	 * 
	 * @param contractExgId
	 *            合同成品Id
	 * @return double 合同某项成品总金额
	 */
	public double findDzscEmsBomTotalPrice(DzscEmsExgBill dzscEmsExgBill) {
		List list = this.find("select sum(a.money) from DzscEmsBomBill a "
				+ " where a.company.id= ? and a.dzscEmsExgBill.id = ? "
				+ " and a.modifyMark<>? ", new Object[] {
				CommonUtils.getCompany().getId(), dzscEmsExgBill.getId(),
				ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		} else {
			return 0.0;
		}
	}

	// /**
	// * 根据通过备案头，归并序号查询通关备案成品
	// *
	// * @param dzscEmsPorHead
	// * @param code
	// * @param name
	// * @param spec
	// * @return
	// */
	// public DzscEmsExgBill findDzscDzscEmsExgBill(DzscEmsPorHead
	// dzscEmsPorHead,
	// Integer seqNum) {
	// List list = this.find(
	// "select a from DzscEmsExgBill a where a.dzscEmsPorHead=? and "
	// + "a.seqNum=?", new Object[] { dzscEmsPorHead,
	// seqNum });
	// if (list.size() > 0) {
	// return (DzscEmsExgBill) list.get(0);
	// } else {
	// return null;
	// }
	// }

	/**
	 * 根据通关备案头与料号查询通关备案归并成品
	 * 
	 * @param dzscEmsPorHead
	 * @param ptno
	 * @return
	 */
	public DzscEmsPorMaterialExg findDzscEmsPorMaterialExgByPtno(
			DzscEmsPorHead dzscEmsPorHead, String ptno) {
		List list = this.find(
				"select a from DzscEmsPorMaterialExg a where a.dzscEmsPorHead =? and "
						+ "a.materiel.ptNo=?", new Object[] { dzscEmsPorHead,
						ptno });
		if (list.size() > 0) {
			return (DzscEmsPorMaterialExg) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据料号查询电子手册归并
	 * 
	 * @param ptno
	 * @return
	 */
	public List findDzscInnerMergeDataByptno(String ptno) {
		List list = this
				.find(
						"select a from DzscInnerMergeData a where a.materiel.ptNo=? and  a.company=? ",
						new Object[] { ptno, CommonUtils.getCompany() });

		return list;
	}

	/**
	 * 根据通关备案头与料号查询通关备案归并料件
	 * 
	 * @param dzscEmsPorHead
	 * @param ptno
	 * @return
	 */
	public DzscEmsPorMaterialImg findDzscEmsPorMaterialImgByPtno(
			DzscEmsPorHead dzscEmsPorHead, String ptno) {
		List list = this.find(
				"select a from DzscEmsPorMaterialImg a where a.dzscEmsPorHead =? and "
						+ "a.materiel.ptNo=?", new Object[] { dzscEmsPorHead,
						ptno });
		if (list.size() > 0) {
			return (DzscEmsPorMaterialImg) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据通关备案成品以及料件序号查询通关备案BOM
	 * 
	 * @param dzscEmsExgBill
	 * @param imgSeqNum
	 * @return
	 */
	public DzscEmsBomBill findDzscEmsBomBill(DzscEmsExgBill dzscEmsExgBill,
			Integer imgSeqNum) {
		List list = this.find(
				"select a from DzscEmsBomBill a where a.dzscEmsExgBill=? and "
						+ "a.imgSeqNum=?", new Object[] { dzscEmsExgBill,
						imgSeqNum });
		if (list.size() > 0) {
			return (DzscEmsBomBill) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 按物料类型查询所有有四位归并的资料
	 * 
	 * @param materieltype
	 * @return
	 */
	public List findDzscInnerMergeData(String materieltype) {
		List list = this
				.find(
						"select a from DzscInnerMergeData a "
								+ "where a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum >0 and "
								+ "a.imrType =? and  a.company=?",
						new Object[] { materieltype, CommonUtils.getCompany() });
		return list;
	}

	public List findDzscContract(String declareState) {
		return this
				.find(
						"select a from DzscEmsPorHead a where a.declareState=? and a.company=?",
						new Object[] { declareState, CommonUtils.getCompany() });
	}

	/**
	 * 查询成品所耗用的料件的数量
	 * 
	 * @param contractExg
	 * @param contractImgSeqNum
	 * @return
	 */
	public List findDzscEmsImgBillAmountByExgUsed(DzscEmsPorHead dzscEmsPorHead) {
		return this
				.find(
						"select a.imgSeqNum,sum(a.dzscEmsExgBill.amount*(a.unitWare/(1-a.ware/100.0)))"
								+ " from DzscEmsBomBill  a "
								+ " where a.dzscEmsExgBill.dzscEmsPorHead.id =? "
								+ " and a.modifyMark<>? "
								+ " group by a.imgSeqNum",
						new Object[] { dzscEmsPorHead.getId(),
								ModifyMarkState.DELETED });
	}

	public List findCustomsComplex(String code) {
		return this.find("select c from CustomsComplex c where c.code = ?",
				new Object[] { code });
	}

	public List findTenInnerSeqNum() {
		return this.find(
				"select a from DzscTenInnerMerge a left join fetch a.dzscFourInnerMerge "
						+ " left join fetch a.tenComplex "
						+ " left join fetch a.dzscFourInnerMerge "
						+ " where  a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });

	}

	/**
	 * 根据合同和备案序号抓取报关单物料资料
	 * 
	 * @param isMaterial
	 *            料件判断，true为料件
	 * @param contract
	 *            合同备案表头
	 * @param seqNum
	 *            料件序号
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料
	 */
	public List findDzscCustomsDeclarationCommInfo(boolean isMaterial,
			DzscEmsPorHead billHead, Integer seqNum) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?  "
				+ " and a.commSerialNo=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billHead.getEmsNo());
		parameters.add(seqNum);
		if (isMaterial) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据手册号查给定的实体类
	 * 
	 * @param entityObject
	 *            要查找的实体类数据
	 * @param emsNo
	 *            手册编号
	 * @param projectType
	 *            帐册的类型
	 * @return List entityObject类型
	 */
	public List findEntityObjectByEmsNo(String entityObject, String emsNo,
			Integer projectType) {
		List param = new ArrayList();
		String hql = " select a from " + entityObject
				+ " a where a.company.id=? ";
		param.add(CommonUtils.getCompany().getId());
		if (emsNo != null) {
			hql += " and a.emsNo=? ";
			param.add(emsNo);
		}
		if (projectType != null) {
			hql += " and a.projectType=? ";
			param.add(projectType);
		}
		return this.find(hql, param.toArray());
	}

	/**
	 * 根据手册号查给定的实体类
	 * 
	 * @param entityObject
	 *            要查找的实体类数据
	 * @param emsHeadH2k
	 *            手册编号
	 * @param projectType
	 *            帐册的类型
	 * @return List entityObject类型
	 */
	public List findEntityObjectByEmsHeadH2kNo(String entityObject,
			String emsHeadH2k, Integer projectType) {
		List param = new ArrayList();
		String hql = " select a from " + entityObject
				+ " a where a.company.id=? ";
		param.add(CommonUtils.getCompany().getId());
		if (emsHeadH2k != null) {
			hql += " and a.emsHeadH2k=? ";
			param.add(emsHeadH2k);
		}
		if (projectType != null) {
			hql += " and a.projectType=? ";
			param.add(projectType);
		}
		return this.find(hql, param.toArray());
	}

	/**
	 * 根据手册号查关封表头
	 * 
	 * @param entityObject
	 *            要查找的实体类数据
	 * @param emsNo
	 *            手册编号
	 * @return List entityObject类型
	 */
	public List findCustomsEnvelopBillByEmsNo(String emsNo) {
		String hql = " select a from CustomsEnvelopBill a "
				+ " where a.emsNo like ? and a.company.id=? and a.projectType=? ";
		return this.find(hql, new Object[] { "%" + emsNo + "%",
				CommonUtils.getCompany().getId(), ProjectType.DZSC });
	}

	/**
	 * 根据合同备案手册号来抓取通关备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadByCorrEmsNo(String corrEmsNo) {
		String hql = " select a from DzscEmsPorHead a "
				+ " where a.corrEmsNo=? and a.company.id=? ";
		return this.find(hql, new Object[] { corrEmsNo,
				CommonUtils.getCompany().getId() });
	}

	public List findFecavBill(Company company) {
		String hql = " select a from FecavBill a " + " where a.company.id=? "
				+ " and a.exportDate is null "
				+ " and a.billState = ? order by a.code ";
		return this.find(hql, new Object[] { company.getId(), 3 });
	}

	/**
	 * 统计出口总金额
	 * 
	 * @param dzscEmsPorHead
	 * @return
	 */
	public Double getTotalPriceBExport(DzscEmsPorHead dzscEmsPorHead) {

		List list = this
				.find(
						"select sum(a.money) from"
								+ " DzscEmsExgBill a where a.dzscEmsPorHead.id=? and a.company.id = ? "
								+ " group by a.dzscEmsPorHead.id ",
						new Object[] { dzscEmsPorHead.getId(),
								CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;

	}

	/**
	 * 统计进口总金额
	 * 
	 * @param dzscEmsPorHead
	 * @return
	 */
	public Double getTotalPriceBImport(DzscEmsPorHead dzscEmsPorHead) {
		List list = this
				.find(
						"select sum(a.money) from"
								+ " DzscEmsImgBill a where a.dzscEmsPorHead.id=? and a.company.id= ? "
								+ " group by a.dzscEmsPorHead.id ",
						new Object[] { dzscEmsPorHead.getId(),
								CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找通关备案料件 来自 手册号(正在变更)
	 * 
	 * @param emsNo
	 *            手册号
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillByEmsNoChange(String emsNo) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.dzscEmsPorHead.copTrNo = ? "
								+ " and (a.dzscEmsPorHead.declareState=? or a.dzscEmsPorHead.declareState=? ) "
								+ " and a.company.id=? "
								+ " order by a.tenSeqNum ", new Object[] {
								emsNo, DzscState.CHANGE, DzscState.APPLY,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找通关备案料件 根据 通关备案合同
	 * 
	 * @param dzscEmsHeadId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBillByHead(DzscEmsPorHead dzscEmsHead) {
		return this
				.find(
						"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
								+ " and a.company.id=? ", new Object[] {
								dzscEmsHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找通关备案成品 根据 通关备案合同
	 * 
	 * @param dzscEmsHeadId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsExgBillByHead(DzscEmsPorHead dzscEmsHead) {
		return this
				.find(
						"select a from DzscEmsExgBill a where a.dzscEmsPorHead.id = ? "
								+ " and a.company.id=? ", new Object[] {
								dzscEmsHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据通关备案头通关备案归并成品对应的备案成品备案序号
	 * 
	 * @param emsHead
	 *            手册通关备案表头
	 * @return
	 */
	public List findExgMaterialAndSeqNum(DzscEmsPorHead emsHead) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = "select b.seqNum,a.materiel from DzscEmsPorMaterialExg a "
				+ " left join a.dzscEmsExgBill b " + " left join a.materiel c "
				+ " where a.company.id=? and a.dzscEmsPorHead.id=? "
				+ " and b.company.id=? and b.dzscEmsPorHead.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHead.getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHead.getId());
		return find(sql, parameters.toArray());

	}

	/**
	 * 根据通关备案头通关备案归并成品对应的备案成品备案序号
	 * 
	 * @param emsHead
	 *            手册通关备案表头
	 * @param ptno
	 * @return
	 */
	public List findImgMaterialAndSeqNum(DzscEmsPorHead emsHead) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = "select b.seqNum,a.materiel from DzscEmsPorMaterialImg a "
				+ " left join a.dzscEmsImgBill b " + " left join a.materiel c "
				+ " where a.company.id=? and a.dzscEmsPorHead.id=? "
				+ " and b.company.id=? and b.dzscEmsPorHead.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHead.getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHead.getId());
		return find(sql, parameters.toArray());
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImgId(DzscEmsPorHead dzscEmsPorHead) {
		return this.find(
				"select a.id from DzscEmsPorMaterialImg a where a.company.id = ? "
						+ " and a.dzscEmsPorHead.id=? "
						+ " order by a.dzscEmsImgBill.seqNum,a.materiel.ptNo ",
				new Object[] { CommonUtils.getCompany().getId(),
						dzscEmsPorHead.getId() });
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExgId(DzscEmsExgBill dzscEmsExgBill) {
		return this.find(
				"select a.id from DzscEmsPorMaterialExg a where a.company.id = ? "
						+ " and a.dzscEmsExgBill.id=? ", new Object[] {
						CommonUtils.getCompany().getId(),
						dzscEmsExgBill.getId() });
	}

	public List findDzscEmsBomBillBySeq(DzscEmsPorHead head, Integer be,
			Integer en) {
		List para = new ArrayList();
		String hsql = " select a from DzscEmsBomBill a left join a.dzscEmsExgBill b "
				+ " where a.company.id = ?  and b.dzscEmsPorHead.id=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(head.getId());
		if (be != null) {
			hsql += " and b.seqNum>=? ";
			para.add(be);
		}
		if (en != null) {
			hsql += " and b.seqNum<=? ";
			para.add(en);
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据进出口类型和手册号查找报关单物料
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param emsNo
	 *            手册号
	 * @return List DzscCustomsDeclarationCommInfo
	 */
	public List findDzscCustomsDeclarationCommInfo(Integer[] impExpType,
			String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a "
				+ " left join a.baseCustomsDeclaration b "
				+ " where a.company.id=?" + " and b.emsHeadH2k=?  "
				+ " and b.effective=? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(new Boolean(true));
		if (impExpType != null && impExpType.length > 1) {
			hsql += " and b.impExpType in( ";
			for (int i = 0; i < impExpType.length - 1; i++) {
				hsql += " ?, ";
				parameters.add(impExpType[i]);
			}
			hsql += " ?)";
			parameters.add(impExpType[impExpType.length - 1]);
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据进出口类型和手册号查找报关单物料
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param emsNo
	 *            手册号
	 * @return List DzscCustomsDeclarationCommInfo
	 */
	public List findDzscCustomsDeclarationCommInfoNew(Integer[] impExpType,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a "
				+ " left join a.baseCustomsDeclaration b "
				+ " where a.company.id=?" + " and b.emsHeadH2k=?  "
				+ " and b.effective=? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(new Boolean(true));
		if (beginDate != null) {
			hsql += " and b.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (impExpType != null && impExpType.length > 1) {
			hsql += " and b.impExpType in( ";
			for (int i = 0; i < impExpType.length - 1; i++) {
				hsql += " ?, ";
				parameters.add(impExpType[i]);
			}
			hsql += " ?)";
			parameters.add(impExpType[impExpType.length - 1]);
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询电子手册手册商品数量总量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进口标志
	 * @param impExpType
	 *            进口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return double 手册商品数量总量
	 */
	public double findCommInfoTotalAmount(String commSerialNo, String commName,
			String commSpec, String unitName, Double commUnitPrice,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.complex.code=?";
			parameters.add(commSerialNo);
		}
		// if (commName != null) {
		// hsql += " and a.commName=?";
		// parameters.add(commName);
		// }
		// if (commSpec != null) {
		// hsql += " and a.commSpec=?";
		// parameters.add(commSpec);
		// }
		if (unitName != null) {
			hsql += " and a.unit.name=?";
			parameters.add(unitName);
		}
		if (commUnitPrice != null) {
			hsql += " and a.commUnitPrice=?";
			parameters.add(Double.valueOf(commUnitPrice));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}

		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	public double findCommInfoTotalAmountOld(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}

		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	public DzscEmsExgBill getContractExgByContract(String contractId,
			Integer exgId, String contractNo) {
		List list = this
				.find(
						"from DzscEmsExgBill as a where a.dzscEmsPorHead.emsNo=? and a.seqNum=? and  a.company.id=?",
						new Object[] { contractId, exgId,
								CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (DzscEmsExgBill) list.get(0);
		}
		return null;
	}

	public void saveEmsPorImg(List list) {
		this.saveEmsPorImg(list);
	}

	/**
	 * 批量保存合同成品
	 * 
	 * @param list
	 *            合同成品
	 */
	public void saveDzscEmsExgBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 获取归并关系中的加工费比例
	 * 
	 * @param tenSeqNum
	 * @return
	 */
	public Double findMachinScaleByTemSeqNum(Integer tenSeqNum) {
		// TODO Auto-generated method stub
		String hsql = "select a.dzscTenInnerMerge.machinScale from DzscInnerMergeData a"
				+ " where a.imrType=? and a.stateMark=? and a.company.id=? and a.dzscTenInnerMerge.tenSeqNum=? ";
		List list = this.find(hsql, new Object[] {
				MaterielType.FINISHED_PRODUCT, "2",
				CommonUtils.getCompany().getId(), tenSeqNum });
		if (list != null && list.size() >= 1) {
			return (Double) list.get(0);
		}
		return 0d;
	}

	public List<DzscEmsBomBill> findDzscEmsBomBillByHead(DzscEmsPorHead head) {
		// TODO Auto-generated method stub
		return this
				.find(
						"from DzscEmsBomBill a where a.company.id=? and a.dzscEmsExgBill.dzscEmsPorHead.id=?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 查询单耗中某一料件的总数量
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param contractImgSeqNum
	 *            料件总表序号
	 * @return List 是int型，单耗中某一料件的总数量
	 */
	public Double findDzscEmsBomBillUsedAmount(String contractId, Integer seqNum) {
		List list = this.find("select sum(a.amount) from DzscEmsBomBill as a "
				+ " where a.company.id=? "
				+ " and a.dzscEmsExgBill.dzscEmsPorHead.id = ? "
				+ " and a.imgSeqNum=? and a.modifyMark<>? ", new Object[] {
				CommonUtils.getCompany().getId(), contractId, seqNum,
				ModifyMarkState.DELETED });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 在通关备案的料件备案中查找料件，但是这些料件没有成为被指定成品的BOM资料
	 * 
	 * @param parentId
	 *            通关备案合同表头Id
	 * @param parentId
	 *            通关备案合同成品Id
	 * @param imgSeqNum
	 *            通关备案料件总表序号
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public DzscEmsImgBill findEmsPorImgBillToBill(String parentId,
			String exgBillId, String imgSeqNum) {
		List ls = this
				.find(
						"select a from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?"
								+ " and a.seqNum not in "
								+ " (select b.imgSeqNum from DzscEmsBomBill b where b.dzscEmsExgBill.id = ?)"
								+ " and a.seqNum = ?", new Object[] {
								CommonUtils.getCompany().getId(), parentId,
								exgBillId, Integer.valueOf(imgSeqNum) });
		if (ls.size() <= 0) {
			return null;
		} else {
			return (DzscEmsImgBill) ls.get(0);
		}
	}

	public List findAllDzscEmsExgBill() {
		return this
				.find(
						"select a from DzscEmsExgBill a where a.company.id = ? and a.dzscEmsPorHead.declareState=?",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 根据成品删除单耗
	 * 
	 * @param dzscEmsExgBill
	 */
	public void deleteDzscEmsBomBill(DzscEmsPorHead head) {
		String hsql = "delete from DzscEmsBomBill where dzscEmsExgBill.id in ("
				+ " select id from DzscEmsExgBill where dzscEmsPorHead=?)";
		this.batchUpdateOrDelete(hsql, new Object[] { head });
	}

	/**
	 * 根据手册表头删除料件
	 * 
	 * @param head
	 */
	public void deleteDzscEmsImgBill(DzscEmsPorHead head) {
		String hsql = "delete from DzscEmsImgBill where dzscEmsPorHead=?";
		this.batchUpdateOrDelete(hsql, new Object[] { head });
	}

	/**
	 * 根据手册表头删除成品
	 * 
	 * @param head
	 */
	public void deleteDzscEmsExgBill(DzscEmsPorHead head) {
		String hsql = "delete from DzscEmsExgBill where dzscEmsPorHead=?";
		this.batchUpdateOrDelete(hsql, new Object[] { head });
	}

	/**
	 * 根据手册表头删除归并成品
	 * 
	 * @param head
	 */
	public void deleteDzscEmsPorMaterialExg(DzscEmsPorHead head) {
		String hsql = "delete from DzscEmsPorMaterialExg where dzscEmsPorHead=?";
		this.batchUpdateOrDelete(hsql, new Object[] { head });
	}

	/**
	 * 根据手册表头删除归并料件
	 * 
	 * @param head
	 */
	public void deleteDzscEmsPorMaterialImg(DzscEmsPorHead head) {
		String hsql = "delete from DzscEmsPorMaterialImg where dzscEmsPorHead=?";
		this.batchUpdateOrDelete(hsql, new Object[] { head });
	}

	/**
	 * 转抄其他合同单耗，如本合同无该料件则新增至本合同
	 * 
	 * @param newBomBill
	 *            2010-06-03 hcl
	 * @param from
	 */
	public void findEmsImgBill(DzscEmsBomBill newBomBill, DzscEmsExgBill from) {
		// 根据在本合同查找料件
		String hsql = "select a  from DzscEmsImgBill a where a.company.id=? and a.dzscEmsPorHead.id = ? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(newBomBill.getDzscEmsExgBill().getDzscEmsPorHead()
				.getId());
		List list = this.find(hsql, paramters.toArray());
		System.out.println("本合同list.size()=" + list.size());
		// 存放本合同所有料件Map
		Map<String, DzscEmsImgBill> toMap = new HashMap<String, DzscEmsImgBill>();
		String toKey = "";
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill img = (DzscEmsImgBill) list.get(i);
			toKey = getToKey(img);
			img = toMap.get(toKey);
			if (img == null) {
				img = (DzscEmsImgBill) list.get(i);		System.out.println("bomKey="+getBomKey(newBomBill));
				System.out.println("toKey="+toKey);
				toMap.put(toKey, img);
			}

		}
		DzscEmsImgBill img = toMap.get(getBomKey(newBomBill));
		if (img != null) {
			newBomBill.setImgSeqNum(img.getSeqNum());
			newBomBill.setPrice(img.getPrice());
			return;
		}
		// 如本合同没有此料件到From合同找

		hsql = "select a  from DzscEmsImgBill a where a.company.id=?  and a.dzscEmsPorHead.emsNo = ?"
				+ "and a.dzscEmsPorHead.declareState=? and a.seqNum=? ";
		List<Object> paramtersOther = new ArrayList<Object>();
		paramtersOther.add(CommonUtils.getCompany().getId()); 
		paramtersOther.add(from.getDzscEmsPorHead().getEmsNo());
		paramtersOther.add(DzscState.EXECUTE);
		paramtersOther.add(newBomBill.getImgSeqNum());
		List listOther = this.find(hsql, paramtersOther.toArray());
		System.out.println("listOther.size()=" + listOther.size());

//		try {
			if (listOther == null || listOther.size() == 0) {
//				Exception e = new Exception();
//				throw e;
				throw new RuntimeException ("转抄合同编号："+from.getDzscEmsPorHead().getEmsNo()+"\n有Bom(序号为："
						+newBomBill.getImgSeqNum()+")，但无该Bom的料件，该合同数据可能出错");
			}

			
//		} catch (Exception e) {
//			System.out.println("转抄合同有Bom，无改Bom料件，该合同数据可能出错");
//			e.printStackTrace();
//			return;
//		}
		img = new DzscEmsImgBill();
		try {
			PropertyUtils.copyProperties(img, (DzscEmsImgBill) (listOther
					.get(0)));
			System.out.println("img.getPrice()="+img.getPrice());

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		// 查找料件中最大的序号,并修改
		List MaxTenSeqList = this.find(
				"select max(a.seqNum)  from DzscEmsImgBill a where a.company.id=?  and "
						+ "a.dzscEmsPorHead.id = ?", new Object[] {
						CommonUtils.getCompany().getId(),
						newBomBill.getDzscEmsExgBill().getDzscEmsPorHead()
								.getId() });
		Integer MaxTenSeq = 0;
		if (MaxTenSeqList != null && null != MaxTenSeqList.get(0))
			MaxTenSeq = (Integer) (MaxTenSeqList.get(0));
		MaxTenSeq++;
		if(img==null)
			System.out.println("img=null");

		img.setDzscEmsPorHead(newBomBill.getDzscEmsExgBill()
				.getDzscEmsPorHead());
		img.setId(null);//区分from料件
		img.setSeqNum(MaxTenSeq);
		newBomBill.setImgSeqNum(MaxTenSeq);// 序号反写到Bom
		img.setAmount(newBomBill.getAmount());
		img.setPrice(newBomBill.getPrice());
			System.out.println("img.getPrice()="+img.getPrice());
		img.setMoney(newBomBill.getMoney());
		img.setModifyMark(ModifyMarkState.ADDED);
		this.saveOrUpdate(img);
		this.saveOrUpdate(newBomBill);
		return;

	}

	private String getBomKey(DzscEmsBomBill newBomBill) {
		return newBomBill.getComplex().getCode() + "/" + newBomBill.getName()
				+ "/" + (newBomBill.getSpec() == null ? "" : newBomBill
				.getSpec());
	}

	private String getToKey(DzscEmsImgBill img) {
		return img.getComplex().getCode() + "/" + img.getName() + "/"
				+ (img.getSpec() == null ? "" : img.getSpec());
	}

	/**
	 * 获取手册料件最大序号
	 * 
	 * @param to
	 * @return
	 */
	public List getMaxSeq(DzscEmsExgBill to) {
		String hsql = "select max(a.seqNum) from DzscEmsImgBill a where a.company.id=? and a.dzscEmsPorHead.id=?";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(to.getDzscEmsPorHead().getId());
		return this.find(hsql, paramters.toArray());
	}

	public List findEdiMaterielInfo(boolean isMaterial, String emsNo) {
		String classname = "";
		System.out.println("emsNo="+emsNo);
		List list = null;
			if (isMaterial) {
				classname = "DzscEmsImgBill";
				list = this.find(" select a from " + classname + " as a "
						+ " where a.dzscEmsPorHead.declareState=? "
						+ " and a.dzscEmsPorHead.company.id=? "
						+ " and a.dzscEmsPorHead.emsNo = ?  and (a.isForbid<>? or a.isForbid=null)" + "  order by a.seqNum",
						new Object[] { DzscState.EXECUTE,
								CommonUtils.getCompany().getId(),
								emsNo,new Boolean(true)});
			} else {
				classname = "DzscEmsExgBill";
				list = this.find(" select a from " + classname + " as a "
						+ " where a.dzscEmsPorHead.declareState=? "
						+ " and a.dzscEmsPorHead.company.id=? "
						+ " and a.dzscEmsPorHead.emsNo = ? and (a.isForbid<>? or a.isForbid=null)" + "  order by a.seqNum",
						new Object[] {  DzscState.EXECUTE,
								CommonUtils.getCompany().getId(),
								emsNo,new Boolean(true) });
			}
		return list;
	}
	/**
	 * 查找商品禁用信息
	 * 
	 * @param isMateriel
	 *            物料类别
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findCommdityForbid(String emsNo,boolean isMateriel, Date beginDate,
			Date endDate) {
		List list = new Vector();
		List<Object> parameters = new ArrayList<Object>();
		String sql = "";
		if (isMateriel) {
			sql = "select a from DzscCommdityForbid a where a.type = ? and a.company.id = ? " +
					"and a.emsNo=?";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		} else {
			sql = "select a from DzscCommdityForbid a where a.type = ? and a.company.id = ?"
				+"and a.emsNo=?";
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		}
		// sql += " order by Convert(Integer,a.seqNum)";
		sql += " order by   a.seqNum ";
		list = this.find(sql, parameters.toArray());
		return list;
	}

	public List getDzscEmsExgBill(String emsNo){
		String hql = "select a.seqNum ,a.machinPrice from DzscEmsExgBill a where a.company.id = ? and a.dzscEmsPorHead.emsNo = ? ";
		return this.find(hql,new Object[] {CommonUtils.getCompany().getId(),emsNo});
	}
}