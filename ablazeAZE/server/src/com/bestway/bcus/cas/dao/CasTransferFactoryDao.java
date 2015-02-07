package com.bestway.bcus.cas.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 海关帐转厂DAO
 * @author hw
 *
 */
@SuppressWarnings("unchecked")
public class CasTransferFactoryDao extends BaseDao {
	/**
	 * 查询转厂收／送货明细表
	 * 
	 * @param billDetaiName
	 * @param billTypes
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<TempObject> findTransFactRecvSendCommName(String billDetaiName,
			String[] billTypes, ScmCoc scmCoc, String commName, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.hsName,a.hsSpec,a.billMaster.scmCoc.name,a.billMaster.scmCoc.id,a.billMaster.scmCoc.linkMan,a.billMaster.scmCoc.linkTel,a.billMaster.scmCoc.fax from " + billDetaiName
				+ " a where a.billMaster.isValid= ?"
				+ " and a.billMaster.company.id= ?";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if(commName != null && !"".equals(commName)) {
			hsql += " and a.hsName = ?";
			parameters.add(commName);
		}
		if (billTypes != null && billTypes.length > 0) {
			for (int i = 0; i < billTypes.length; i++) {
				if (i == 0) {
					hsql += " and ( a.billMaster.billType.code = ? ";
				} else if (i > 0 && i < billTypes.length - 1) {
					hsql += " or  a.billMaster.billType.code = ? ";
				} else if (i == billTypes.length - 1) {
					hsql += " or  a.billMaster.billType.code = ? ) ";
				}
				parameters.add(billTypes[i]);
			}
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		System.out.println(hsql);
		List<Object[]> list = this.find(hsql, parameters.toArray());
		List<TempObject> returnList = new ArrayList<TempObject>();
		for (Object[] item : list) {
			TempObject tmp = new TempObject();
			if (item[2] != null)
				tmp.setObject(item[2]);
			if (item[0] != null)
				tmp.setObject1(item[0]);
			if(item[1] != null) {
				tmp.setObject2(item[1]);
			}
			if(item[3] != null) {
				tmp.setObject3(item[3]);
			}
			if(item[4] != null) {
				tmp.setObject4(item[4]);
			}
			if(item[5] != null) {
				tmp.setObject5(item[5]);
			}
			if(item[6] != null) {
				tmp.setObject6(item[6]);
			}
			
			returnList.add(tmp);
		}
		return returnList;
	}

	/**
	 * 从报关单中查找转厂名称
	 * 
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCommNameFromCustomsDeclaration(Integer projectType,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String customsDeclarationName = "CustomsDeclarationCommInfo";
		if (ProjectType.BCUS == projectType) {
			customsDeclarationName = "CustomsDeclarationCommInfo";
		} else if (ProjectType.BCS == projectType) {
			customsDeclarationName = "BcsCustomsDeclarationCommInfo";
		} else if (ProjectType.DZSC == projectType) {
			customsDeclarationName = "DzscCustomsDeclarationCommInfo";
		}
		String hsql = "select distinct a.commName,a.commSpec,a.baseCustomsDeclaration.customer.name from "
				+ customsDeclarationName + " a "
				+ "where a.baseCustomsDeclaration.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if(scmCoc != null && scmCoc.getId() != null) {
			parameters.add(scmCoc.getId());
			hsql = hsql + " and a.baseCustomsDeclaration.customer.id = ?";
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List<Object[]> list = this.find(hsql, parameters.toArray());
		List returnList = new ArrayList();
		for (Object[] item : list) {
			String tmp = "";
			if (item[2] != null)
				tmp = (String) item[2];
			if (item[0] != null)
				tmp = tmp + ",," + (String) item[0];
			if(item[1] != null) {
				tmp = tmp + ",," + (String) item[1];
			}
			System.out.println(tmp);
			returnList.add(tmp);
		}
		return returnList;
	}

	/**
	 * 查询转厂收／送货明细表
	 * 
	 * @param billDetaiName
	 * @param billTypes
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findTransFactRecvSendDetailInfo(String billDetaiName,boolean isCustomNo,
			String[] billTypes, ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from "
				+ billDetaiName
				+ " a left join fetch a.billMaster where a.billMaster.isValid= ?"
				+ " and a.billMaster.company.id= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (billTypes != null && billTypes.length > 0) {
			for (int i = 0; i < billTypes.length; i++) {
				if (i == 0) {
					hsql += " and ( a.billMaster.billType.code = ? ";
				} else if (i > 0 && i < billTypes.length - 1) {
					hsql += " or  a.billMaster.billType.code = ? ";
				} else if (i == billTypes.length - 1) {
					hsql += " or  a.billMaster.billType.code = ? ) ";
				}
				parameters.add(billTypes[i]);
			}
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if(!isCustomNo){
			hsql += " and a.customNo is not null and a.customNo !='' ";
		}
		hsql += " order by a.billMaster.validDate ";
		return this.find(hsql, parameters.toArray());
	}

	public List findFactoryAndFactualCustomsRalationByHsName(String cusName) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from FactoryAndFactualCustomsRalation "
				+ " a where a.statCusNameRelationHsn.cusName= ?"
				+ " and a.company.id= ? ";
		parameters.add(cusName);
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	public List findFactoryAndFactualCustomsRalationLikeHsName(String cusName) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from FactoryAndFactualCustomsRalation "
				+ " a where a.statCusNameRelationHsn.cusName like ?"
				+ " and a.company.id= ? ";
		parameters.add("%" + cusName + "%");
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	public List findCustomsDeclarationAmountWeight(boolean isImport,
			Integer projectType, ScmCoc scmCoc,
			Date beginDate, Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (beginDate.compareTo(cal.getTime()) < 0) {
			beginDate.setTime(cal.getTimeInMillis());
		}
		String customsDeclarationName = "CustomsDeclarationCommInfo";
		if (ProjectType.BCUS == projectType) {
			customsDeclarationName = "CustomsDeclarationCommInfo";
		} else if (ProjectType.BCS == projectType) {
			customsDeclarationName = "BcsCustomsDeclarationCommInfo";
		} else if (ProjectType.DZSC == projectType) {
			customsDeclarationName = "DzscCustomsDeclarationCommInfo";
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.baseCustomsDeclaration.declarationDate,"
				+ " a.commAmount,a.netWeight,a.commName,a.commSpec from "
				+ customsDeclarationName
				+ " a where a.baseCustomsDeclaration.effective= ?"
				+ " and a.baseCustomsDeclaration.company.id= ? "
				+ " and a.baseCustomsDeclaration.impExpType= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		// if (commName != null) {
		// hsql += " and a.commName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.baseCustomsDeclaration.customer.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate ";
		return this.find(hsql, parameters.toArray());
	}

	public List findBillDetailSendAmountWeight(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String billDetaiName = (isImport ? "BillDetailMateriel"
				: "BillDetailProduct");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (beginDate.compareTo(cal.getTime()) < 0) {
			beginDate.setTime(cal.getTimeInMillis());
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.billMaster.validDate,a.hsAmount,a.netWeight,a.hsName,a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code = ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {
			parameters.add("1004");// 结转进口
		} else {
			parameters.add("2102");// 结转出口
		}
		// if (commName != null) {
		// hsql += " and a.hsName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.billMaster.validDate ";
		return this.find(hsql, parameters.toArray());
	}
	
	public List findBillDetailBackAmountWeight(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String billDetaiName = (isImport ? "BillDetailMateriel"
				: "BillDetailProduct");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (beginDate.compareTo(cal.getTime()) < 0) {
			beginDate.setTime(cal.getTimeInMillis());
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.billMaster.validDate,a.hsAmount,a.netWeight,a.hsName,a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {
			parameters.add("1106");
		} else {
			parameters.add("2009");
		}
		// if (commName != null) {
		// hsql += " and a.hsName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.billMaster.validDate ";
		return this.find(hsql, parameters.toArray());
	}

	public List findBillDetailPlusAmountWeight(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String billDetaiName = (isImport ? "BillDetailMateriel"
				: "BillDetailProduct");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (beginDate.compareTo(cal.getTime()) < 0) {
			beginDate.setTime(cal.getTimeInMillis());
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.billMaster.validDate,a.hsAmount,a.netWeight,a.hsName,a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {
			parameters.add("1015");// 已收货未结转期初单
		} else {
			parameters.add("2011");// 已交货未结转期初单
		}
		// if (commName != null) {
		// hsql += " and a.hsName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.billMaster.validDate ";
		return this.find(hsql, parameters.toArray());
	}

	public List findBillDetailMinusAmountWeight(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String billDetaiName = (isImport ? "BillDetailMateriel"
				: "BillDetailProduct");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (beginDate.compareTo(cal.getTime()) < 0) {
			beginDate.setTime(cal.getTimeInMillis());
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.billMaster.validDate,a.hsAmount,a.netWeight,a.hsName,a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {
			parameters.add("1016");// 未收货已结转期初单
		} else {
			parameters.add("2012");// 未交货已结转期初单
		}
		// if (commName != null) {
		// hsql += " and a.hsName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.billMaster.validDate ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找关封资料信息
	 * 
	 * @param isImport
	 * @param commName
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findEnvelopAmountWeight(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.customsEnvelopBill.beginAvailability,"
				+ " a.customsEnvelopBill.customsEnvelopBillNo,"
				+ " sum(a.ownerQuantity),a.ptName,a.ptSpec from "
				+ " CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.isAvailability= ? "
				+ " and a.customsEnvelopBill.company.id= ? "
				+ " and a.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(isImport);
		// if (commName != null) { v
		// hsql += " and a.ptName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.customsEnvelopBill.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.customsEnvelopBill.customsEnvelopBillNo,"
				+ " a.customsEnvelopBill.beginAvailability,a.ptName,a.ptSpec "
				+ " order by a.customsEnvelopBill.beginAvailability";

		//System.out.println("hsql=" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	public Double findEnvelopAmountWeights(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate,String customsEnvelopBillNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.transferQuantity)" + " from "
				+ " CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.isAvailability= ? "
				+ " and a.customsEnvelopBill.company.id= ? "
				+ " and a.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(isImport);
		// if (commName != null) {
		// hsql += " and a.ptName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.customsEnvelopBill.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (customsEnvelopBillNo != null) {
			hsql += " and a.customsEnvelopBill.customsEnvelopBillNo= ? ";
			parameters.add(customsEnvelopBillNo);
		}
		hsql += " group by a.customsEnvelopBill.customsEnvelopBillNo,"
				+ " a.customsEnvelopBill.beginAvailability,a.ptName,a.ptSpec "
				+ " order by a.customsEnvelopBill.beginAvailability";
		List list = this.find(hsql, parameters.toArray());
		if (list.size() == 0) {
			return 0.0;
		} else {
			return (Double) list.get(0);
		}

	}

	/**
	 * 深加工结转
	 * 
	 * @param isImport
	 * @param commName
	 * @param scmCoc
	 * @return
	 */
	public List findFptlopAmountWeight(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String inoutD = "";
		Integer inoutF = 0;
		if (isImport) {
			inoutD = "inDate ";
			inoutF = 1;
		} else {
			inoutD = "outDate ";
			inoutF = 0;
		}
		String hsql = "select a.fptAppHead." + inoutD + ","
				+ " a.fptAppHead.appNo," + " sum(a.qty),a.name,a.spec from "
				+ " FptAppItem  a  where  a.company.id= ? "
				+ " and a.inOutFlag=?  and a.fptAppHead.inOutFlag=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(inoutF.toString());
		parameters.add(inoutF.toString());
		// if (commName != null) {
		// hsql += " and a.ptName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.fptAppHead.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.fptAppHead." + inoutD + " >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.fptAppHead." + inoutD + " <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.fptAppHead.appNo ," + " a.fptAppHead." + inoutD
				+ ",a.name,a.spec " + " order by a.fptAppHead." + inoutD;
		return this.find(hsql, parameters.toArray());
	}

	public List findCustomsEnvelopBill(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.customsEnvelopBill,a.ptName,a.ptSpec from "
				+ " CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.isAvailability= ? "
				+ " and a.customsEnvelopBill.company.id= ? "
				+ " and a.customsEnvelopBill.isImpExpGoods=? ";
		// distinct
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(isImport);
		// if (commName != null) {
		// hsql += " and a.ptName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.customsEnvelopBill.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	public List findFptAppItem(boolean isImport,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		String inoutD = "";
		Integer inoutF = 0;
		if (isImport) {
			inoutD = "inDate ";
			inoutF = 1;
		} else {
			inoutD = "outDate ";
			inoutF = 0;
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.fptAppHead,a.name,a.spec from "
				+ " FptAppItem a "
				// + " where a.customsEnvelopBill.isAvailability= ? "
				+ " where a.fptAppHead.company.id= ? "
				+ " and a.inOutFlag=?  and a.fptAppHead.inOutFlag=?  ";
		// parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(inoutF.toString());
		parameters.add(inoutF.toString());
		// if (commName != null) {
		// hsql += " and a.ptName = ? ";
		// parameters.add(commName);
		// }
		if (scmCoc != null) {
			hsql += " and a.fptAppHead.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginDate != null) {
			hsql += " and a.fptAppHead." + inoutD + " >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.fptAppHead." + inoutD + " <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalSendAmount(boolean isImg, ScmCoc scmCoc, Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " sum(a.hsAmount),sum(a.netWeight),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? "
				+ " and a.billMaster.validDate >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			parameters.add("1004");
		} else {
			parameters.add("2102");
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalSendAmountNoScm(boolean isImg, Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " sum(a.hsAmount),sum(a.netWeight),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? "
				+ " and a.billMaster.validDate >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			parameters.add("1004");
		} else {
			parameters.add("2102");
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalBackAmount(boolean isImg, ScmCoc scmCoc, Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " sum(a.hsAmount),sum(a.netWeight),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? "
				+ " and a.billMaster.validDate >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			parameters.add("1106");
		} else {
			parameters.add("2009");
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalBackAmountNoScm(boolean isImg, Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " sum(a.hsAmount),sum(a.netWeight),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? "
				+ " and a.billMaster.validDate >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			parameters.add("1106");
		} else {
			parameters.add("2009");
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalAmountBy(boolean isImg, ScmCoc scmCoc,
			String billType, Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " sum(a.hsAmount),sum(a.netWeight),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? "
				+ " and a.billMaster.validDate >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billType);
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalAmountByNoScm(boolean isImg, String billType,
			Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " sum(a.hsAmount),sum(a.netWeight),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? "
				+ " and a.billMaster.validDate >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billType);
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalCustomsDeclarationAmountWeight(boolean isImg,
			Integer projectType, Date endDate, ScmCoc scmCoc) {
		String customsDeclarationName = "CustomsDeclarationCommInfo";
		if (ProjectType.BCUS == projectType) {
			customsDeclarationName = "CustomsDeclarationCommInfo";
		} else if (ProjectType.BCS == projectType) {
			customsDeclarationName = "BcsCustomsDeclarationCommInfo";
		} else if (ProjectType.DZSC == projectType) {
			customsDeclarationName = "DzscCustomsDeclarationCommInfo";
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.commName,a.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,"
				+ " sum(a.commAmount),sum(a.netWeight),a.commSpec from "
				+ customsDeclarationName
				+ " a where a.baseCustomsDeclaration.effective= ?"
				+ " and a.baseCustomsDeclaration.company.id= ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.impExpType= ?";
		// in(?,?)
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		// if (isImport) {
		// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		// } else {
		// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// }
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (isImg) {
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (scmCoc != null) {
			hsql += " and a.baseCustomsDeclaration.customer.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.complex.code,a.commName,a.commSpec,a.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalCustomsDeclarationAmountWeightNoScm(boolean isImg,
			Integer projectType, Date endDate) {
		String customsDeclarationName = "CustomsDeclarationCommInfo";
		if (ProjectType.BCUS == projectType) {
			customsDeclarationName = "CustomsDeclarationCommInfo";
		} else if (ProjectType.BCS == projectType) {
			customsDeclarationName = "BcsCustomsDeclarationCommInfo";
		} else if (ProjectType.DZSC == projectType) {
			customsDeclarationName = "DzscCustomsDeclarationCommInfo";
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.commName,a.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,"
				+ " sum(a.commAmount),sum(a.netWeight),a.commSpec from "
				+ customsDeclarationName
				+ " a where a.baseCustomsDeclaration.effective= ?"
				+ " and a.baseCustomsDeclaration.company.id= ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.impExpType= ?";
		// in(?,?)
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		// if (isImport) {
		// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		// } else {
		// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// }
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (isImg) {
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		//		
		// if (scmCoc != null) {
		// hsql += " and a.baseCustomsDeclaration.customer.id = ? ";
		// parameters.add(scmCoc.getId());
		// }
		hsql += " group by a.complex.code,a.commName,a.commSpec,a.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalEnvelopAmountWeight(Date endDate, ScmCoc scmCoc) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.ptName,a.unit.name,"
				+ " a.customsEnvelopBill.scmCoc.name,"
				+ " sum(a.ownerQuantity),a.ptSpec from "
				+ " CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.isAvailability= ? "
				+ " and a.customsEnvelopBill.company.id= ? "
				+ " and a.customsEnvelopBill.beginAvailability >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability<= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (scmCoc != null) {
			hsql += " and a.customsEnvelopBill.customs.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.complex.code,a.ptName,a.ptSpec,a.unit.name,"
				+ " a.customsEnvelopBill.scmCoc.name";
		return this.find(hsql, parameters.toArray());
	}

	public List findTotalEnvelopAmountWeightNoScm(Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.ptName,a.unit.name,"
				+ " a.customsEnvelopBill.scmCoc.name,"
				+ " sum(a.ownerQuantity),a.ptSpec from "
				+ " CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.isAvailability= ? "
				+ " and a.customsEnvelopBill.company.id= ? "
				+ " and a.customsEnvelopBill.beginAvailability >= ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		parameters.add(CommonUtils.getBeginDate(cal.getTime()));
		if (endDate != null) {
			hsql += " and a.customsEnvelopBill.beginAvailability<= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// if (scmCoc != null) {
		// hsql += " and a.customsEnvelopBill.customs.id = ? ";
		// parameters.add(scmCoc.getId());
		// }
		hsql += " group by a.complex.code,a.ptName,a.ptSpec,a.unit.name,"
				+ " a.customsEnvelopBill.scmCoc.name";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 深加工结转
	 * 
	 * @param isImport
	 * @param commName
	 * @param scmCoc
	 * @return
	 */
	public List findTotalFptAmountWeightIn(Date endDate, ScmCoc scmCoc) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.codeTs.code ,a.name ,a.unit.name,"
				+ " a.fptAppHead.scmCoc.name , sum(a.qty), a.spec from "
				+ " FptAppItem  a  where  a.company.id= ? "
				+ " and a.inOutFlag=?  and a.fptAppHead.inOutFlag=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(1).toString());
		parameters.add(new Integer(1).toString());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (endDate != null) {
			hsql += "  and    a.fptAppHead.inDate<= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (scmCoc != null) {
			hsql += " and a.fptAppHead.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.codeTs.code ,a.name ,a.unit.name,a.fptAppHead.scmCoc.name,a.spec ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 深加工结转
	 * 
	 * @param isImport
	 * @param commName
	 * @param scmCoc
	 * @return
	 */
	public List findTotalFptAmountWeightInNoScm(Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.codeTs.code ,a.name ,a.unit.name,"
				+ " a.fptAppHead.scmCoc.name , sum(a.qty), a.spec from "
				+ " FptAppItem  a  where  a.company.id= ? "
				+ " and a.inOutFlag=?  and a.fptAppHead.inOutFlag=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(1).toString());
		parameters.add(new Integer(1).toString());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (endDate != null) {
			hsql += "  and    a.fptAppHead.inDate<= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// if (scmCoc != null) {
		// hsql += " and a.fptAppHead.scmCoc.id = ? ";
		// parameters.add(scmCoc.getId());
		// }
		hsql += " group by a.codeTs.code ,a.name ,a.unit.name,a.fptAppHead.scmCoc.name,a.spec ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 深加工结转
	 * 
	 * @param isImport
	 * @param commName
	 * @param scmCoc
	 * @return
	 */
	public List findTotalFptAmountWeightOut(Date endDate, ScmCoc scmCoc) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.codeTs.code ,a.name ,a.unit.name,"
				+ " a.fptAppHead.scmCoc.name , sum(a.qty), a.spec from "
				+ " FptAppItem  a  where  a.company.id= ? "
				+ " and a.inOutFlag=?  and a.fptAppHead.inOutFlag=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(0).toString());
		parameters.add(new Integer(0).toString());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (endDate != null) {
			hsql += "  and    a.fptAppHead.outDate<= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (scmCoc != null) {
			hsql += " and a.fptAppHead.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.codeTs.code ,a.name ,a.unit.name,a.fptAppHead.scmCoc.name, a.spec";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 深加工结转
	 * 
	 * @param isImport
	 * @param commName
	 * @param scmCoc
	 * @return
	 */
	public List findTotalFptAmountWeightOutNoScm(Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.codeTs.code ,a.name ,a.unit.name,"
				+ " a.fptAppHead.scmCoc.name , sum(a.qty), a.spec from "
				+ " FptAppItem  a  where  a.company.id= ? "
				+ " and a.inOutFlag=?  and a.fptAppHead.inOutFlag=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Integer(0).toString());
		parameters.add(new Integer(0).toString());
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(CommonUtils.getYear()), 0, 1);
		if (endDate != null) {
			hsql += "  and    a.fptAppHead.outDate<= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// if (scmCoc != null) {
		// hsql += " and a.fptAppHead.scmCoc.id = ? ";
		// parameters.add(scmCoc.getId());
		// }
		hsql += " group by a.codeTs.code ,a.name ,a.unit.name,a.fptAppHead.scmCoc.name, a.spec";
		return this.find(hsql, parameters.toArray());
	}

	public List findLastBillMasterDate(boolean isImg, Date endDate,
			ScmCoc scmCoc) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " max(a.billMaster.validDate),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			parameters.add("1004");
		} else {
			parameters.add("2102");
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (scmCoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}

	public List findLastBillMasterDateNoScm(boolean isImg, Date endDate) {
		String billDetaiName = (isImg ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.complex.code,a.hsName,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " max(a.billMaster.validDate),a.hsSpec from "
				+ billDetaiName
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (isImg) {
			parameters.add("1004");
		} else {
			parameters.add("2102");
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		// if (scmCoc != null) {
		// hsql += " and a.billMaster.scmCoc.id = ? ";
		// parameters.add(scmCoc.getId());
		// }
		hsql += " group by a.complex.code,a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name ";
		return this.find(hsql, parameters.toArray());
	}
	
	
	
	
	/**
	 * 查询统计【单据】中的数量和重量（key = 供应商 + 海关商品名称 + 规格 + 月份）
	 * @param isMaterial
	 * @param commName
	 * @param commSpec
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @param groups
	 * @return
	 */
	public List<Object[]> findBillDetailSumAmountWeight(boolean isMaterial,
			String commName, String commSpec, ScmCoc scmCoc, Date beginDate,
			Date endDate, List<String> groups) {
		String tableName = (isMaterial ? "BillDetailMateriel"
				: "BillDetailProduct");
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
//		hql.append(
//				" select m.scmCoc.name, d.hsName, d.hsSpec, YEAR(m.validDate), MONTH(m.validDate),")
		hql.append(
				" select m.scmCoc.code, d.hsName, d.hsSpec, YEAR(m.validDate), MONTH(m.validDate),")
		.append("	d.hsUnit.name, m.billType.code, sum(d.hsAmount), sum(d.netWeight)")
		.append(" from " + tableName + " d")
		.append(" 	left join d.billMaster m")
		.append(" where m.validDate >= ? and m.validDate <= ?");
		params.add(beginDate);
		params.add(endDate);
		
		if(scmCoc != null) {
			hql.append(" and m.scmCoc.name = ?");
			params.add(scmCoc.getName());
		}
		
		if(CommonUtils.notEmpty(commName)) {
			hql.append(" and d.hsName = ?");
			params.add(commName);
		}
		
		if(CommonUtils.notEmpty(commSpec)) {
			hql.append(" and d.hsSpec = ?");
			params.add(commSpec);
		}
		if(isMaterial) {
			// 1004	结转进口	   1015 已收货未结转期初单      1106 结转料件退货单
			hql.append(" and m.billType.code in ('1004','1015','1106')");
		} else {
			// 2102	结转出口       2011 已交货未结转期初单      2009 结转成品退货单
			hql.append(" and m.billType.code in ('2102','2011','2009')");
		}
		
		
		hql
		.append(" 	and m.isValid = true and d.company.id = ?")
		//		.append(" group by m.scmCoc.name, d.hsName, d.hsSpec, YEAR(m.validDate), MONTH(m.validDate),")
		.append(" group by m.scmCoc.code, d.hsName, d.hsSpec, YEAR(m.validDate), MONTH(m.validDate),")
		.append(" 	d.hsUnit.name, m.billType.code");
		params.add(CommonUtils.getCompany().getId());
		System.out.println("================="+hql.toString());
		return find(hql.toString(), params.toArray());
	}
	
	/**
	 * 查询客户供应商资料
	 * @return
	 */
	public List findScmCoc(){
		return find("select a from ScmCoc a where a.company.id = ? ",
				new Object[]{CommonUtils.getCompany().getId()});
	} 
	
	/**
	 * 查询统计【报关单表体】中的数量和重量（key = 供应商 + 海关商品名称 + 规格 + 月份）
	 * @param isMaterial
	 * @param commName
	 * @param commSpec
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @param groups
	 * @return
	 */
	public List<Object[]> findCustomsDeclarationCommInfoSumAmountWeight(boolean isMaterial,
			String commName, String commSpec, ScmCoc scmCoc, Date beginDate,
			Date endDate, List<String> groups) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder();
		hql.append(
				" select d.customer.name, i.commName, i.commSpec, YEAR(d.impExpDate), MONTH(d.impExpDate),")
		.append("	i.unit.name, d.tradeMode.code, sum(i.commAmount), sum(i.netWeight)")
		.append(" from BcsCustomsDeclarationCommInfo i")
		.append(" 	left join i.baseCustomsDeclaration d")
		.append(" where d.tradeMode.code = ? and d.impExpDate >= ? and d.impExpDate <= ?");
		params.add("0654");
		params.add(beginDate);
		params.add(endDate);
		
		if(scmCoc != null) {
			hql.append(" and d.customer.name = ?");
			params.add(scmCoc.getName());
		}
		
		if(CommonUtils.notEmpty(commName)) {
			hql.append(" and i.commName = ?");
			params.add(commName);
		}
		
		if(CommonUtils.notEmpty(commSpec)) {
			hql.append(" and i.commSpec = ?");
			params.add(commSpec);
		}
		
		hql.append(" and d.impExpFlag = ?");
		params.add(isMaterial ? ImpExpFlag.IMPORT : ImpExpFlag.EXPORT);
		
		hql
		.append(" 	and d.effective = true and i.company.id = ?")
		.append(" group by d.customer.name, i.commName, i.commSpec, YEAR(d.impExpDate), MONTH(d.impExpDate),")
		.append(" 	i.unit.name, d.tradeMode.code");
		params.add(CommonUtils.getCompany().getId());
		
		 
		return find(hql.toString(), params.toArray());
	}
	
	
	/**
	 * 查询统计【报关单表体】中的数量和重量（key = 供应商 + 海关商品名称 + 规格 + 月份）
	 * @param isMaterial
	 * @param commName
	 * @param commSpec
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @param groups
	 * @return
	 */
	public List<Object[]> findCustomsCustomsEnvelopSumAmountWeight(boolean isMaterial,
			String commName, String commSpec, ScmCoc scmCoc, Date beginDate,
			Date endDate, List<String> groups) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder()
		.append(" select b.customsEnvelopBillNo, b.scmCoc.name, a.ptName, a.ptSpec,")
		.append(" 	a.unit.name, sum(a.ownerQuantity)")
		.append(" from CustomsEnvelopCommodityInfo a ")
		.append(" 	left join a.customsEnvelopBill b")
		.append(" where b.isAvailability= ? ")
		.append(" 	and b.company.id= ? ")
		.append(" 	and b.isImpExpGoods=? ");
		params.add(true);
		params.add(CommonUtils.getCompany().getId());
		params.add(isMaterial);
		
		
		if (scmCoc != null) {
			hql.append(" and b.scmCoc.name = ? ");
			params.add(scmCoc.getName());
		}
		if (beginDate != null) {
			hql.append(" and ((b.beginAvailability >= ? ");
			params.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql.append(" and b.beginAvailability <= ?) ");
			params.add(CommonUtils.getEndDate(endDate));
		}
		if (beginDate != null) {
			hql.append(" or (b.endAvailability >= ? ");
			params.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql.append(" and b.endAvailability <= ?)) ");
			params.add(CommonUtils.getEndDate(endDate));
		}
		hql
		.append(" group by b.customsEnvelopBillNo, b.scmCoc.name, a.ptName, a.ptSpec, a.unit.name ");
		
		
		return this.find(hql.toString(), params.toArray());
	}
	
	public List findBillDetailMateriel(Date beginDate,Date endDate,boolean isMaterial){
		List<Object> params = new ArrayList<Object>();
		String tableName = (isMaterial ? "BillDetailMateriel"
				: "BillDetailProduct");
		String hql = "select b.scmCoc.name,a.hsName,a.hsSpec,b.validDate,b.envelopNo,a.hsAmount "
				+ "from "+tableName+" a left join a.billMaster b where a.company.id= ? and b.billType.code=? ";
		params.add(CommonUtils.getCompany().getId());
		if(isMaterial){
			params.add("1015");
		}else{
			params.add("2011");
		}
		
		if (beginDate != null) {
			hql += " and b.validDate >=? ";
			params.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and b.validDate <=? ";
			params.add(CommonUtils.getEndDate(endDate));
		}
		
		return this.find(hql,params.toArray());
	}
}
