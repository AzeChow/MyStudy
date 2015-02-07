/*
 * Created on 2005-3-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DzscState;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * com.bestway.dzsc.contractexe.dao.DzscContractExeDao
 * 
 * @author Administrator
 */
public class DzscContractExeDao extends BaseEncDao {
	
	/**
	 * 查询没有报关的手册通关备案料件
	 * 
	 * @param baseCustomsDeclaration 报关单表头
	 * @return List 存放了通关备案料件的一些资料
	 */
	public List findDzscMaterialInfo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "select a.seqNum,a.complex,a.unit,a.name,a.spec,a.price,a.detailNote ,a.legalUnitGene,a.legalUnit2Gene from "
				+ " DzscEmsImgBill as a left join a.unit where "
				+ " a.dzscEmsPorHead.emsNo=? "
				+ " and a.dzscEmsPorHead.declareState=? "
				+ " and (a.isForbid<>? or a.isForbid=null) "
				+ " and a.dzscEmsPorHead.company.id=? ";
		return this.find(hql, new Object[] {
				((DzscCustomsDeclaration) baseCustomsDeclaration)
						.getEmsHeadH2k(), DzscState.EXECUTE,
						new Boolean(true),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询没有报关的手册通关备案成品
	 * 
	 * @param baseCustomsDeclaration 报关单表头
	 * @return List 存放了通关备案成品的一些资料
	 */
	public List findDzscProductInfo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = " select a.seqNum,a.complex,a.unit,a.name,a.spec,a.price ,a.id,a.legalUnitGene,a.legalUnit2Gene from "
				+ " DzscEmsExgBill as a left join a.unit  "
				+ " where a.dzscEmsPorHead.emsNo=? "
				+ " and a.dzscEmsPorHead.declareState=? "
				+ " and(a.isForbid<>? or a.isForbid=null) "
				+ " and a.dzscEmsPorHead.company.id=? ";
		return this.find(hql, new Object[] {
				((DzscCustomsDeclaration) baseCustomsDeclaration)
						.getEmsHeadH2k(), DzscState.EXECUTE,
						new Boolean(true),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回报关单中的最大商品序号
	 * 
	 * @param head 报关单表头
	 * @return String 最大商品序号
	 */
	public String getNum(DzscCustomsDeclaration head) {
		String num = "1";
		List list = this.find(
				"select max(a.commSerialNo) from DzscCustomsDeclarationCommInfo a "
						+ " where a.baseCustomsDeclaration.id=?",
				new Object[] { head.getId() });
		if (list != null && list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 返回报关单中的商品信息
	 * 
	 * @param complexNo 商品编码
	 * @param info 报关单表头
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料
	 */
	public List getRepeatComplex(String complexNo, DzscCustomsDeclaration info) {
		return this
				.find(
						"select a from DzscCustomsDeclarationCommInfo a where a.complex.code=? and a.baseCustomsDeclaration.id=?",
						new Object[] { complexNo, info.getId() });
	}

	// public Complex findComplexByCode(String code){
	// List list = this.find("from Complex a where a.code = ?",
	// new Object[]{code});
	// if (list != null && list.size()>0){
	// return (Complex) list.get(0);
	// }
	// return null;
	// }
	//    
	/**
	 * 获取系统参数设置中的其它参数设置
	 * 
	 * @return CompanyOther 其它参数设置
	 */
	public CompanyOther getSysCompanyOther() {
		List list = this.find("select a from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany() });
		if (list != null && list.size() > 0) {
			return (CompanyOther) list.get(0);
		}
		return null;
	}

	/**
	 * 获取其它参数设置中的重量参数
	 * 
	 * @return Double 重量参数
	 */
	public Double findweightpara() {
		List list = this.find(
				"select a.weightPara from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 根据商品编码返回Complex
	 * 
	 * @param complexCode 商品编码
	 * @return Complex 
	 */
	public Complex findComplexByCode(String complexCode) {
		return (Complex) this.load(Complex.class, complexCode);
	}
//	/**
//	 * 查询统计报关单的报关金额
//	 * @param commSerialNo
//	 * @param impExpFlag
//	 * @param impExpType
//	 * @param tradeCodes
//	 * @param emsNo
//	 * @param beginDate
//	 * @param endDate
//	 * @param state
//	 * @return
//	 */
//	public double findCommInfoTotalMoney(Integer commSerialNo,
//			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
//			String emsNo, Date beginDate, Date endDate, int state) {
//		List<Object> parameters = new ArrayList<Object>();
//		String hsql = " select  sum(a.commTotalPrice)  from  DzscCustomsDeclarationCommInfo as a "
//				+ " where a.baseCustomsDeclaration.company.id=? "
//				// + " and a.baseCustomsDeclaration.effective=? "
//				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
//		parameters.add(CommonUtils.getCompany().getId());
//		// parameters.add(new Boolean(true));
//		parameters.add(emsNo);
//		if (impExpFlag != null) {
//			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
//			parameters.add(impExpFlag);
//		}
//		if (impExpType != null) {
//			hsql += " and a.baseCustomsDeclaration.impExpType=?";
//			parameters.add(impExpType);
//		}
//		if (beginDate != null) {
//			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
//			parameters.add(CommonUtils.getBeginDate(beginDate));
//		}
//		if (endDate != null) {
//			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
//			parameters.add(CommonUtils.getEndDate(endDate));
//		}
//		if (commSerialNo != null) {
//			hsql += " and a.commSerialNo=?";
//			parameters.add(commSerialNo);
//		}
//		if (tradeCodes != null && tradeCodes.length > 0) {
//			hsql += " and ( ";
//			for (int i = 0; i < tradeCodes.length; i++) {
//				if (i == tradeCodes.length - 1) {
//					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
//				} else {
//					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
//				}
//				parameters.add(tradeCodes[i]);
//			}
//			hsql += " ) ";
//		}
//		if (state == CustomsDeclarationState.EFFECTIVED) {
//			hsql += " and a.baseCustomsDeclaration.effective=?";
//			parameters.add(true);
//		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
//			hsql += " and a.baseCustomsDeclaration.effective=?";
//			parameters.add(false);
//		}
//		List list = this.find(hsql, parameters.toArray());
//		if (list.size() < 1 || list.get(0) == null) {
//			return 0;
//		} else {
//			return Double.parseDouble(list.get(0).toString());
//		}
//	}
	/**
	 * 查询统计报关单数量，金额
	 */
	public double[] findCommInfoTotalAmountAndMoney(Integer impExpFlag,Integer commSerialNo,String emsNo
			,Integer impExpType, String[] tradeCodes,int state){
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount),sum(a.commTotalPrice)  from  DzscCustomsDeclarationCommInfo as a "
			+ " where a.baseCustomsDeclaration.company.id=? "
			+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(commSerialNo);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
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
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return new double[]{0d,0d};
		} else {
			Object[] rtn  = (Object[])list.get(0);
			if(rtn[0]==null){
				rtn[0] = 0d;
			}
			if(rtn[1]==null){
				rtn[1] = 0d;
			}
			return new double[]{Double.parseDouble(rtn[0].toString()),Double.parseDouble(rtn[1].toString())};
		}
	}
	
	/**
	 * 查询统计报关单的报关数量
	 * 
	 * @param commSerialNo 商品序号
	 * @param impExpFlag 进出口标志
	 * @param impExpType 进出口类型
	 * @param tradeCodes 贸易方式编码
	 * @param emsNo 手册号
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 生效类型
	 * @return double 报关数量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate, int state) {
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
			parameters.add(commSerialNo);
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
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}
	
	/**
	 * 查询统计报关单的报关数量
	 * 
	 * @param commSerialNo 商品序号
	 * @param impExpFlag 进出口标志
	 * @param impExpType 进出口类型
	 * @param tradeCodes 贸易方式编码
	 * @param emsNo 手册号
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 生效类型
	 * @return double 报关数量
	 */
	public double findCommInfoTotalAmountxiaokaitest(Integer commSerialNo,
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
			parameters.add(commSerialNo);
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
//		if (state == CustomsDeclarationState.EFFECTIVED) {
//			hsql += " and a.baseCustomsDeclaration.effective=?";
//			parameters.add(true);
//		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
//			hsql += " and a.baseCustomsDeclaration.effective=?";
//			parameters.add(false);
//		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
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
		if (list.size() > 0) {
			dirSet = (DzscParameterSet) list.get(0);
		} else {
			dirSet = new DzscParameterSet();
			this.saveOrUpdate(dirSet);
		}
		return dirSet;
	}

	/**
	 * 通过归并序号获取通关手册料件
	 * @param request
	 * @param dzscEmsPorHead
	 * @param temSeqNum
	 */
	public DzscEmsImgBill findDzscEmsImgBillBytenSeqNum(
			DzscEmsPorHead dzscEmsPorHead, Integer temSeqNum) {
		List paramterList = new ArrayList();
		String hsql = "select a from DzscEmsImgBill a"
				+ " where a.dzscEmsPorHead.declareState = ?"
				+ " and a.dzscEmsPorHead.id = ? "
				+ " and a.company.id = ? and " + " a.tenSeqNum =?";
		paramterList.add(DzscState.EXECUTE);
		paramterList.add(dzscEmsPorHead.getId());
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(temSeqNum);
		List<DzscEmsImgBill> bills = this.find(hsql, paramterList.toArray());
		if (bills != null && bills.size() >= 1) {
			return bills.get(0);
		}
		return null;
	}
	
	/**
	 * 通过归并序号获取通关手册料件
	 * @param request
	 * @param dzscEmsPorHead
	 * @param temSeqNum
	 */
	public DzscEmsExgBill findDzscEmsExgBillBytenSeqNum(
			DzscEmsPorHead dzscEmsPorHead, Integer temSeqNum) {
		List paramterList = new ArrayList();
		String hsql = "select a from DzscEmsExgBill a"
				+ " where a.dzscEmsPorHead.declareState = ?"
				+ " and a.dzscEmsPorHead.id = ? "
				+ " and a.company.id = ? and " + " a.tenSeqNum =?";
		paramterList.add(DzscState.EXECUTE);
		paramterList.add(dzscEmsPorHead.getId());
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(temSeqNum);
		List<DzscEmsExgBill> bills = this.find(hsql, paramterList.toArray());
		if (bills != null && bills.size() >= 1) {
			return bills.get(0);
		}
		return null;
	}
	
	
}
