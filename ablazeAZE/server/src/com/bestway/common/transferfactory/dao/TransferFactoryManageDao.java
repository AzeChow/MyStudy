package com.bestway.common.transferfactory.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.CustomsEnvelopRequestBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopRequestCommodityInfo;
import com.bestway.common.transferfactory.entity.MakeApplyToCustomsInfo;
import com.bestway.common.transferfactory.entity.MakeCustomsEnvelop;
import com.bestway.common.transferfactory.entity.MakeCustomsEnvelopRequest;
import com.bestway.common.transferfactory.entity.MakeTransferFactoryBill;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopRequetBill;
import com.bestway.common.transferfactory.entity.TempTransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransParameterSet;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBill;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBillCommodityInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;

public class TransferFactoryManageDao extends BaseDao {
	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findNameValues(int type) {
		return this
				.find("select a from ParameterSet a left join fetch a.company where a.company= ? and a.type=?",
						new Object[] { CommonUtils.getCompany(), type });
	}

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	public TransParameterSet findTransParameterSet() {
		List list = this.find(
				"select a from TransParameterSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (TransParameterSet) list.get(0);
		} else {
			TransParameterSet parameterSet = new TransParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 查找报关单该料件的合同使用量
	 * 
	 * @param request
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            是否进出
	 * @param emsNo
	 *            手册编号
	 * @return
	 */
	public Double findContractImgByConNoAndEmsNo(String emsNo, Integer seqNum) {
		String hsql = "select a from ContractImg a where a.contract.emsNo = ? and a.seqNum=? and a.company.id=? "
				+ " and a.contract.declareState=? order by a.seqNum ";
		List list = this.find(hsql, new Object[] { emsNo, seqNum,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });
		Double Amount = 0.0;
		if (list.size() > 0 && list.get(0) != null) {
			ContractImg contractImg = (ContractImg) list.get(0);
			Amount = contractImg.getAmount() == null ? 0.0 : contractImg
					.getAmount();
		}
		return Amount;
	}

	/**
	 * 查找报关单该料件的合同使用量
	 * 
	 * @param request
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            是否进出
	 * @param emsNo
	 *            手册编号
	 * @return
	 */
	public Double findContractExgByConNoAndEmsNo(String emsNo, Integer seqNum) {
		String hsql = "select a from ContractExg a where a.contract.emsNo = ? and a.seqNum=? and a.company.id=? "
				+ " and a.contract.declareState=? order by a.seqNum ";
		List list = this.find(hsql, new Object[] { emsNo, seqNum,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });
		Double Amount = 0.0;
		if (list.size() > 0 && list.get(0) != null) {
			ContractExg contractExg = (ContractExg) list.get(0);
			Amount = contractExg.getExportAmount() == null ? 0.0 : contractExg
					.getExportAmount();
		}
		return Amount;
	}

	// public Double findCECI(String emsNo, Integer seqNum, boolean
	// isImpExpGoods) {
	// String hsql =
	// "select a from CustomsEnvelopCommodityInfo a where a.emsNo = ? and a.seqNum=?"
	// + " and a.customsEnvelopBill.isImpExpGoods = ? and a.company.id=? ";
	// List list = this.find(hsql, new Object[] { emsNo, seqNum,
	// isImpExpGoods, CommonUtils.getCompany().getId() });
	// Double Amount = 0.0;
	// if (list.size() > 0 && list.get(0) != null) {
	// CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) list
	// .get(0);
	// Amount = c.getDispensabilityQuantity() == null ? 0.0 : c
	// .getDispensabilityQuantity();
	// }
	// return Amount;
	// }

	/**
	 * 查询已生效的报关单中各项商品数量总量
	 * 
	 * @param commSerialNo
	 *            料件序号
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return double 商品数量总量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer[] impExpType, String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null || impExpType.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpType.length; i++) {
				if (i == impExpType.length - 1) {
					hsql += "a.baseCustomsDeclaration.impExpType = ?";
				} else {
					hsql += "a.baseCustomsDeclaration.impExpType = ? or ";
				}
				parameters.add(impExpType[i]);
			}
			hsql += " ) ";
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 获得关封申请单所有数据
	 */
	public List findCustomsEnvelopRequestBill() {
		return this
				.find("select a from CustomsEnvelopRequestBill as a where a.company.id=?",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 获得关封申请单所有数据来自进出货标志
	 */
	public List findCustomsEnvelopRequestBillByImpExpGoodsFlag(
			boolean impExpGoodsFlag) {
		return this.find("select a from CustomsEnvelopRequestBill as a "
				+ "	where a.isImpExpGoods=? and a.company.id=?", new Object[] {
				Boolean.valueOf(impExpGoodsFlag),
				CommonUtils.getCompany().getId() });
	}

	private boolean getIsEmsH2kSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得关封申请单数据来自客户或供应商Id
	 */
	public List findCustomsEnvelopRequestBillByScmCocId(String scmCocId) {
		return this
				.find("select a from CustomsEnvelopRequestBill as a where a.scmCoc.id = ? and a.company.id=?",
						new Object[] { scmCocId,
								CommonUtils.getCompany().getId() });
	}

	public Complex findComplexByCode(String code) {
		List list = this.find("select a from Complex as a where a.code = ? ",
				new Object[] { code });
		if (list.size() > 0) {
			return (Complex) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 自动结案
	 * 
	 * @param no
	 */
	public void autoJieAn(BaseCustomsDeclaration baseCustomsDeclaration) {
		if (baseCustomsDeclaration.getCustomsEnvelopBillNo() == null
				|| "".equals(baseCustomsDeclaration.getCustomsEnvelopBillNo())) {
			return;
		}
		CustomsEnvelopBill bill = findCustomsEnvelopBillByCode(baseCustomsDeclaration
				.getCustomsEnvelopBillNo());
		if (bill != null) {
			bill.setIsEndCase(true);
			bill.setEndCaseDate(baseCustomsDeclaration.getImpExpDate());
		}
		this.saveCustomsEnvelopBill(bill);
	}

	/**
	 * 反自动结案
	 * 
	 * @param no
	 */
	public void unAutoJieAn(BaseCustomsDeclaration baseCustomsDeclaration) {
		if (baseCustomsDeclaration.getCustomsEnvelopBillNo() == null
				|| "".equals(baseCustomsDeclaration.getCustomsEnvelopBillNo())) {
			return;
		}
		CustomsEnvelopBill bill = findCustomsEnvelopBillByCode(baseCustomsDeclaration
				.getCustomsEnvelopBillNo());
		if (bill != null) {
			bill.setIsEndCase(false);
			bill.setEndCaseDate(null);
		}
		this.saveCustomsEnvelopBill(bill);
	}

	/**
	 * 获得关封申请单数据来来自选定用客户，且生效、存在未转关封单据的商品 的单据 CEB 代表 customsEnvelopBill 关封单据
	 * 
	 * @param scmCocId
	 * @return
	 */
	public List findCustomsEnvelopRequestBillByScmCocToCEB(String scmCocId) {
		return this
				.find("select a from CustomsEnvelopRequestBill as a "
						+ "where a.scmCoc.id = ? "
						+ "and a.company.id=? "
						+ "and a.isAvailability=? "
						+ " and a.id in "
						+ " (select b.id from "
						+ " CustomsEnvelopRequestCommodityInfo t join t.customsEnvelopRequestBill  b "
						+ " where t.isTranCustomsEnvelop=? or t.isTranCustomsEnvelop is null )",
						new Object[] { scmCocId,
								CommonUtils.getCompany().getId(),
								new Boolean(true), new Boolean(false) });
	}

	/**
	 * 保存关封申请单
	 */
	public void saveCustomsEnvelopRequestBill(
			CustomsEnvelopRequestBill customsEnvelopRequestBill) {
		this.saveOrUpdate(customsEnvelopRequestBill);
	}

	/**
	 * 删除关封申请单
	 */
	public void deleteCustomsEnvelopRequestBill(
			CustomsEnvelopRequestBill customsEnvelopRequestBill) {
		this.delete(customsEnvelopRequestBill);
	}

	/**
	 * 删除关封申请单商品信息数据
	 */
	public void deleteCustomsEnvelopRequestCommodityInfo(
			CustomsEnvelopRequestCommodityInfo data) {
		this.delete(data);
	}

	/**
	 * 保存关封申请单商品信息数据
	 */
	public void saveCustomsEnvelopRequestCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopRequestCommodityInfo data = (CustomsEnvelopRequestCommodityInfo) list
					.get(i);
			this.saveOrUpdate(data);
		}
	}

	/**
	 * 获得当前关封申请单的商品信息的个数
	 */
	public int findCustomsEnvelopRequestCommodityInfoCount(String parentId) {
		List list = this.find(
				"select count(*) from CustomsEnvelopRequestCommodityInfo b  "
						+ " where b.customsEnvelopRequestBill.id = ? ",
				new Object[] { parentId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findCustomsEnvelopRequestCommodityInfo(String parentId) {
		return this
				.find("select b from CustomsEnvelopRequestCommodityInfo b left join fetch b.materiel where b.customsEnvelopRequestBill.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfo() {
		return this.find("select b from CustomsEnvelopCommodityInfo b  "
				+ "where b.customsEnvelopBill.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public CustomsEnvelopCommodityInfo findCustomsEnvelopCommodityInfo(
			String envelopCode, String emsNo, Integer seqNum) {
		List list = this.find("select b from CustomsEnvelopCommodityInfo b  "
				+ "where b.customsEnvelopBill.company.id = ? "
				+ " and b.customsEnvelopBill.customsEnvelopBillNo=? "
				+ " and b.emsNo=? and b.seqNum=? ", new Object[] {
				CommonUtils.getCompany().getId(), envelopCode, emsNo, seqNum });
		if (list.size() > 0) {
			return (CustomsEnvelopCommodityInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfoNew(String envelopCode,
			String emsNo, Integer seqNum) {
		List list = this.find("select b from CustomsEnvelopCommodityInfo b  "
				+ "where b.customsEnvelopBill.company.id = ? "
				+ " and b.customsEnvelopBill.customsEnvelopBillNo=? "
				+ " and b.emsNo=? and b.seqNum=? ", new Object[] {
				CommonUtils.getCompany().getId(), envelopCode, emsNo, seqNum });
		return list;
	}

	/**
	 * 统计关封明细的数量
	 * 
	 * @param envelopCode
	 * @param emsNo
	 * @param seqNum
	 * @param ceseqNum
	 *            关封序号
	 * @return
	 */
	public double sumCustomsEnvelopCommodityAmount(String envelopCode,
			String emsNo, Integer seqNum, Integer ceseqNum) {

		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select sum(a.ownerQuantity) from CustomsEnvelopCommodityInfo a  "
				+ " where a.customsEnvelopBill.company.id = ? "
				+ " and  a.customsEnvelopBill.customsEnvelopBillNo=? "
				+ " and  a.emsNo=? and a.seqNum=? and a.ceseqNum = ?";

		parameters.add(CommonUtils.getCompany().getId());

		parameters.add(envelopCode);

		parameters.add(emsNo);

		parameters.add(seqNum);

		parameters.add(ceseqNum);

		List list = this.find(hsql, parameters.toArray());

		if (list.size() == 0 || list.get(0) == null) {
			return 0.0;
		}
		return Double.parseDouble(list.get(0).toString());
	}

	/**
	 * 获得最大的单据号来自进出货标志
	 */
	public List getMaxBillNoByImpExpGoodsFlag(boolean impExpGoodsFlag) {
		return this.find(
				"select max(a.billNo) from CustomsEnvelopRequestBill as a "
						+ "	where a.isImpExpGoods=? and a.company.id=?",
				new Object[] { Boolean.valueOf(impExpGoodsFlag),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得关封申请单据商品信息来自父对象
	 */
	public List findCustomsEnvelopRequestCommodityInfoByParent(List parentList) {
		String hsql = "select b from CustomsEnvelopRequestCommodityInfo b left "
				+ "outer join fetch b.materiel where ( b.isTranCustomsEnvelop = ? or b.isTranCustomsEnvelop is null ) ";
		String condition = "";
		List objs = new ArrayList();
		objs.add(new Boolean(false));
		for (int i = 0; i < parentList.size(); i++) {
			if (parentList.get(i) instanceof TempCustomsEnvelopRequetBill) {
				TempCustomsEnvelopRequetBill t = (TempCustomsEnvelopRequetBill) parentList
						.get(i);
				if (i == 0) {
					condition += "  b.customsEnvelopRequestBill.id = ? ";
				} else {
					condition += "  or  b.customsEnvelopRequestBill.id = ? ";
				}
				objs.add(t.getCustomsEnvelopRequestBill().getId());
			}
		}
		if (condition.equals("") == false) {
			hsql += " and (" + condition + " )";
		}
		return this.find(hsql, objs.toArray());
	}

	/**
	 * 获得来自正在执行的电子帐册中归并前的商品信息---成品
	 */
	public List findEmsEdiMergerExgBeforeByEms(EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead) {
		List<Object> para = new ArrayList<Object>();
		String sql = "select e.ptNo from EmsEdiMergerExgBefore e where  ";
		if (getIsMergerSend()) {
			sql += " e.sendState = ? and ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		sql += "  e.emsEdiMergerExgAfter.id in (select a.id from EmsEdiMergerExgAfter a,EmsHeadH2kExg b where "
				+ "  a.seqNum = b.seqNum and b.emsHeadH2k.id = ? and a.emsEdiMergerHead.id = ? ";
		para.add(emsH2k.getId());
		para.add(emsEdiMergerHead.getId());
		if (getIsMergerSend()) {
			sql += " and a.sendState = ? ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		if (getIsEmsH2kSend()) {
			sql += " and b.sendState  = ? ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		sql += " ) and e.company.id = ? ";
		para.add(CommonUtils.getCompany().getId());
		return this.find(sql, para.toArray());
	}

	/*
	 * 得到所有在电子帐册中已经备案的物料
	 */
	public List findInnerMergeDataByPtNo(EmsHeadH2k emsH2k, String type) {
		String emsMergerClass = "";
		if (type.equals(MaterielType.MATERIEL)) {
			emsMergerClass = "EmsHeadH2kImg";
		} else {
			emsMergerClass = "EmsHeadH2kExg";
		}
		if (getIsEmsH2kSend()) {
			return this
					.find("select a.materiel.ptNo from InnerMergeData a where a.imrType=? and a.company.id =?"
							+ " and a.hsAfterTenMemoNo in (select b.seqNum from "
							+ emsMergerClass
							+ " b where b.emsHeadH2k.id = ? and b.sendState = ?)",
							new Object[] { type,
									CommonUtils.getCompany().getId(),
									emsH2k.getId(),
									Integer.valueOf(SendState.SEND) });
		} else {
			return this
					.find("select a.materiel.ptNo from InnerMergeData a where a.imrType=? and a.company.id =?"
							+ " and a.hsAfterTenMemoNo in (select b.seqNum from "
							+ emsMergerClass + " b where b.emsHeadH2k.id = ?)",
							new Object[] { type,
									CommonUtils.getCompany().getId(),
									emsH2k.getId() });
		}
	}

	private boolean getIsMergerSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsSEND,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 得到所有在电子帐册和归并关系中备案中已经备案的物料
	 */
	public List findInnerMergeDataByPtNo(EmsEdiMergerHead mergerHead,
			EmsHeadH2k emsH2k, String type) {
		String EmsEdiH2kImgExg = "";
		String EmsEdiMergerImgExgBefore = "";
		String EmsEdiMergerImgExgAfter = "";
		if (type.equals(MaterielType.MATERIEL)) {
			EmsEdiH2kImgExg = "EmsHeadH2kImg";
			EmsEdiMergerImgExgBefore = "EmsEdiMergerImgBefore";
			EmsEdiMergerImgExgAfter = "emsEdiMergerImgAfter";
		} else {
			EmsEdiH2kImgExg = "EmsHeadH2kExg";
			EmsEdiMergerImgExgBefore = "EmsEdiMergerExgBefore";
			EmsEdiMergerImgExgAfter = "emsEdiMergerExgAfter";
		}
		List<Object> para = new ArrayList<Object>();
		String sql = "select a.materiel.ptNo from InnerMergeData a where a.imrType=? and a.company.id =?"
				+ " and a.hsAfterTenMemoNo in (select b.seqNum from "
				+ EmsEdiH2kImgExg + " b where b.emsHeadH2k.id = ?";
		para.add(type);
		para.add(CommonUtils.getCompany().getId());
		para.add(emsH2k.getId());
		if (getIsEmsH2kSend()) {
			sql += " and b.sendState = ? ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		sql += " ) and a.materiel.ptNo in (select c.ptNo from "
				+ EmsEdiMergerImgExgBefore + " c where c."
				+ EmsEdiMergerImgExgAfter + ".emsEdiMergerHead.id = ? ";
		para.add(mergerHead.getId());
		if (getIsMergerSend()) {
			sql += " and c.sendState = ? ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		sql += ")";
		return this.find(sql, para.toArray());
	}

	public List findEmsEdiMergerBeforeByPtNo(EmsEdiMergerHead emsEdiMergerHead,
			String type) {
		if (emsEdiMergerHead != null) {
			if (type.equals(MaterielType.MATERIEL)) {
				if (getIsMergerSend()) {
					return this
							.find("select e.ptNo from  EmsEdiMergerImgBefore e "
									+ " where e.emsEdiMergerImgAfter.emsEdiMergerHead.id = ? and e.company.id = ? and e.sendState = ?",
									new Object[] { emsEdiMergerHead.getId(),
											CommonUtils.getCompany().getId(),
											Integer.valueOf(SendState.SEND) });
				} else {
					return this
							.find("select e.ptNo from  EmsEdiMergerImgBefore e "
									+ " where e.emsEdiMergerImgAfter.emsEdiMergerHead.id = ? and e.company.id = ?",
									new Object[] { emsEdiMergerHead.getId(),
											CommonUtils.getCompany().getId() });
				}
			} else {
				if (getIsMergerSend()) {
					return this
							.find("select e.ptNo from  EmsEdiMergerExgBefore e "
									+ " where e.emsEdiMergerExgAfter.emsEdiMergerHead.id = ? and e.company.id = ? and e.sendState = ?",
									new Object[] { emsEdiMergerHead.getId(),
											CommonUtils.getCompany().getId(),
											Integer.valueOf(SendState.SEND) });
				} else {
					return this
							.find("select e.ptNo from  EmsEdiMergerExgBefore e "
									+ " where e.emsEdiMergerExgAfter.emsEdiMergerHead.id = ? and e.company.id = ?",
									new Object[] { emsEdiMergerHead.getId(),
											CommonUtils.getCompany().getId() });
				}

			}

		} else {
			return this
					.find("select a.materiel.ptNo from InnerMergeData a where a.materiel.scmCoi.coiProperty =? and a.company.id =?",
							new Object[] { type,
									CommonUtils.getCompany().getId() });
		}
	}

	/**
	 * 根据归并关系备案单头，归并序号，商品编码，查询归并前资料
	 * 
	 * @param emsEdiMergerHead
	 * @param seqNum
	 * @param complex
	 * @return
	 */
	public List findEmsEdiMergerBeforeByHead(EmsEdiMergerHead emsEdiMergerHead,
			Integer seqNum, String complex) {
		List<Object> lsResult = new ArrayList<Object>();
		List lsImg = null;
		if (getIsMergerSend()) {
			lsImg = this
					.find("select a from  EmsEdiMergerImgBefore a "
							+ " where a.emsEdiMergerImgAfter.emsEdiMergerHead.id = ? "
							+ " and a.emsEdiMergerImgAfter.seqNum=? "
							+ " and a.emsEdiMergerImgAfter.complex.code=? and a.company.id = ? and a.sendState = ?",
							new Object[] { emsEdiMergerHead.getId(), seqNum,
									complex, CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND) });
		} else {
			lsImg = this
					.find("select a from  EmsEdiMergerImgBefore a "
							+ " where a.emsEdiMergerImgAfter.emsEdiMergerHead.id = ? "
							+ " and a.emsEdiMergerImgAfter.seqNum=? "
							+ " and a.emsEdiMergerImgAfter.complex.code=? and a.company.id = ?",
							new Object[] { emsEdiMergerHead.getId(), seqNum,
									complex, CommonUtils.getCompany().getId() });
		}
		List lsExg = null;
		if (getIsMergerSend()) {
			lsExg = this
					.find("select a from  EmsEdiMergerExgBefore a "
							+ " where a.emsEdiMergerExgAfter.emsEdiMergerHead.id = ?"
							+ " and a.emsEdiMergerExgAfter.seqNum=? "
							+ " and a.emsEdiMergerExgAfter.complex.code=? and a.company.id = ? and a.sendState = ?",
							new Object[] { emsEdiMergerHead.getId(), seqNum,
									complex, CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND) });
		} else {
			lsExg = this
					.find("select a from  EmsEdiMergerExgBefore a "
							+ " where a.emsEdiMergerExgAfter.emsEdiMergerHead.id = ?"
							+ " and a.emsEdiMergerExgAfter.seqNum=? "
							+ " and a.emsEdiMergerExgAfter.complex.code=? and a.company.id = ?",
							new Object[] { emsEdiMergerHead.getId(), seqNum,
									complex, CommonUtils.getCompany().getId() });
		}
		lsResult.addAll(lsImg);
		lsResult.addAll(lsExg);
		return lsResult;
	}

	/**
	 * 根据归并关系备案单头，物料号码，查询归并后资料
	 * 
	 * @param emsEdiMergerHead
	 * @param ptNo
	 * @return
	 */
	public Object findEmsEdiMergerAfterByBefore(
			EmsEdiMergerHead emsEdiMergerHead, String ptNo) {
		List lsImg = this
				.find("select a.emsEdiMergerImgAfter from EmsEdiMergerImgBefore a "
						+ " where a.emsEdiMergerImgAfter.emsEdiMergerHead.id = ? "
						+ " and a.ptNo=? and a.company.id = ?", new Object[] {
						emsEdiMergerHead.getId(), ptNo,
						CommonUtils.getCompany().getId() });
		if (lsImg.size() > 0) {
			return lsImg.get(0);
		}
		List lsExg = this
				.find("select a.emsEdiMergerExgAfter from  EmsEdiMergerExgBefore a "
						+ " where a.emsEdiMergerExgAfter.emsEdiMergerHead.id = ?"
						+ " and a.ptNo=?  and a.company.id = ?", new Object[] {
						emsEdiMergerHead.getId(), ptNo,
						CommonUtils.getCompany().getId() });
		if (lsExg.size() > 0) {
			return lsExg.get(0);
		}
		return null;
	}

	/**
	 * 获得来自正在执行的电子帐册中归并前的商品信息---料件
	 */
	public List findEmsEdiMergerImgBeforeByEms(EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead) {
		List<Object> para = new ArrayList<Object>();
		String sql = "select e.ptNo from EmsEdiMergerImgBefore e where ";
		if (getIsMergerSend()) {
			sql += " e.sendState = ? and ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		sql += " e.emsEdiMergerImgAfter.id in (select a.id from EmsEdiMergerImgAfter a,EmsHeadH2kImg b where "
				+ " a.seqNum = b.seqNum and b.emsHeadH2k.id = ?  and a.emsEdiMergerHead.id = ? ";
		para.add(emsH2k.getId());
		para.add(emsEdiMergerHead.getId());
		if (getIsMergerSend()) {
			sql += " and a.sendState = ? ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		if (getIsEmsH2kSend()) {
			sql += " and b.sendState = ? ";
			para.add(Integer.valueOf(SendState.SEND));
		}
		sql += " ) and e.company.id = ? ";
		para.add(CommonUtils.getCompany().getId());
		return this.find(sql, para.toArray());
	}

	/**
	 * 根据物料号获得电子帐册中的对应关并后的数据---料件
	 */
	public EmsHeadH2kImg findEmsHeadH2kImgByPtNo(EmsHeadH2k emsH2k, String ptNo) {
		List list = this.find("select c from EmsHeadH2kImg c  "
				+ "	where c.emsHeadH2k.id=? and c.seqNum in "
				+ "(select e.seqNum "
				+ " from EmsEdiMergerImgBefore e where e.ptNo =? )",
				new Object[] { emsH2k.getId(), ptNo });
		if (list.size() <= 0) {
			return null;
		} else {
			return (EmsHeadH2kImg) list.get(0);
		}
	}

	/**
	 * 根据物料号获得电子帐册中的对应关并后的数据---成品
	 */
	public EmsHeadH2kExg findEmsHeadH2kExgByPtNo(EmsHeadH2k emsH2k, String ptNo) {
		List list = this.find("select c from EmsHeadH2kExg c  "
				+ "	where c.emsHeadH2k=? and c.seqNum in "
				+ "(select e.seqNum "
				+ " from EmsEdiMergerExgBefore e where e.ptNo =? )",
				new Object[] { emsH2k, ptNo });
		if (list.size() <= 0) {
			return null;
		} else {
			return (EmsHeadH2kExg) list.get(0);
		}
	}

	/**
	 * 根据关封单据id获得关封申请单中的已生成关封的记录
	 */
	public List findCustomsEnvelopRquestBillByCustomsEnvelopBillId(
			String customsEnvelopBillId) {
		return this.find("select a from CustomsEnvelopRequestBill as a "
				+ "	where a.customsEnvelopBillId=? and a.company.id=?",
				new Object[] { customsEnvelopBillId,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * ---------------------------------------------------- 关封单据用到的方法
	 * -------------------------------------------------------
	 */
	/**
	 * 获得关封单据所有数据
	 */
	public List findCustomsEnvelopBill() {
		return this.find(
				"select a from CustomsEnvelopBill as a where a.company.id=?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 根据关封号获得关封单据
	 */
	public CustomsEnvelopBill findCustomsEnvelopBillByCode(String code) {
		List list = this.find(
				"select a from CustomsEnvelopBill as a where a.company.id=?"
						+ " and a.customsEnvelopBillNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), code });
		if (list.size() > 0) {
			return (CustomsEnvelopBill) list.get(0);
		}
		return null;
	}

	/**
	 * 根据关封号获得关封单据
	 */
	public List findCustomsEnvelopBill(String code) {
		return this.find(
				"select a from CustomsEnvelopBill as a where a.company.id=?"
						+ " and a.customsEnvelopBillNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), code });
	}

	/**
	 * 获得关封单据所有数据
	 */
	public List findCustomsEnvelopBill(boolean impExpGoodsFlag,
			boolean isAvailability) {
		return this.find("select a from CustomsEnvelopBill as a "
				+ "	where a.isImpExpGoods=? "
				+ " and a.isAvailability=? and a.company.id=?", new Object[] {
				Boolean.valueOf(impExpGoodsFlag), isAvailability,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得关封单据所有数据
	 */
	public List findCustomsEnvelopBill(boolean isImport,
			boolean isAvailability, ScmCoc scmCoc) {
		String hql = "select a from CustomsEnvelopBill as a "
				+ "	where a.isImpExpGoods=? "
				+ " and a.isAvailability=? and a.company.id=? "
				+ " and (a.isEndCase=? or a.isEndCase is null)";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(isImport);
		parameters.add(isAvailability);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);
		if (scmCoc != null) {
			hql += " and a.scmCoc=? ";
			parameters.add(scmCoc);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 获得关封单据所有数据
	 */
	public List findCustomsEnvelopCommodityInfo(boolean isImport,
			boolean isAvailability, String emsNo, ScmCoc scmCoc) {
		String hql = "select a from CustomsEnvelopCommodityInfo as a "
				+ "	where a.customsEnvelopBill.isImpExpGoods=? "
				+ " and a.customsEnvelopBill.isAvailability=? "
				+ " and a.customsEnvelopBill.company.id=? "
				+ " and (a.customsEnvelopBill.isEndCase=? "
				+ " or a.customsEnvelopBill.isEndCase is null)"
				+ " and a.customsEnvelopBill.emsNo like ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(isImport);
		parameters.add(isAvailability);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);
		parameters.add("%" + emsNo + "%");
		if (scmCoc != null) {
			hql += " and a.customsEnvelopBill.scmCoc=? ";
			parameters.add(scmCoc);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 获得关封单据所有数据来自进出货标志
	 */
	public List findCustomsEnvelopBill(boolean impExpGoodsFlag, String billNo,
			ScmCoc sc, Date beginDate, Date endDate, Boolean isEndCase) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append("select a from CustomsEnvelopBill as a "
				+ "	where a.isImpExpGoods=? and a.company.id=?");
		parameters.add(Boolean.valueOf(impExpGoodsFlag));
		parameters.add(CommonUtils.getCompany().getId());
		if (billNo != null && (!billNo.equals(""))) {
			hsql.append("  and  a.customsEnvelopBillNo like ? ");
			parameters.add("%" + billNo + "%");
		}
		if (sc != null) {
			hsql.append("  and  a.scmCoc = ? ");
			parameters.add(sc);
		}
		if (beginDate != null) {
			hsql.append(" and a.beginAvailability >= ? ");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql.append(" and a.beginAvailability <= ? ");
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		if (isEndCase != null) {
			hsql.append(" and a.isEndCase = ? ");
			parameters.add(isEndCase);
		}
		return this.find(hsql.toString(), parameters.toArray());
	}

	/**
	 * 获得关封单据数据来自客户或供应商Id
	 */
	public List findCustomsEnvelopBillByScmCocId(String scmCocId) {
		return this
				.find("select a from CustomsEnvelopBill as a where a.scmCoc.id = ? and a.company.id=?",
						new Object[] { scmCocId,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得没有结案,没有过期,生效的关封单据
	 */
	public List findCustomsEnvelopBillByAvailability(String scmCocId) {
		return this
				.find("select a from CustomsEnvelopBill as a where a.scmCoc.id = ? and a.company.id=? "
						+ " and a.isEndCase = ? "
						+ "and a.isAvailability = ? "
						+ "and a.endAvailability >= ? "
						+ "and a.beginAvailability <= ? ", new Object[] {
						scmCocId, CommonUtils.getCompany().getId(),
						new Boolean(false), new Boolean(true), new Date(),
						new Date() });
	}

	/**
	 * 保存关封单据
	 */
	public void saveCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill) {
		this.saveOrUpdate(customsEnvelopBill);
	}

	/**
	 * 删除关封单据
	 */
	public void deleteCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill) {
		this.delete(customsEnvelopBill);
	}

	/**
	 * 删除关封单据商品信息数据
	 */
	public void deleteCustomsEnvelopCommodityInfo(
			CustomsEnvelopCommodityInfo data) {
		this.delete(data);
	}

	/**
	 * 保存关封单据商品信息数据
	 */
	public void saveCustomsEnvelopCommodityInfo(List list) {
		int ceseqNum = 1;
		if (list.size() > 0) {
			List ls = find(
					"select a from CustomsEnvelopCommodityInfo a where a.company.id=? and a.customsEnvelopBill=? order by a.ceseqNum desc",
					new Object[] {
							CommonUtils.getCompany().getId(),
							((CustomsEnvelopCommodityInfo) list.get(0))
									.getCustomsEnvelopBill() });
			if (ls.size() > 0) {
				CustomsEnvelopCommodityInfo info = (CustomsEnvelopCommodityInfo) ls
						.get(0);
				ceseqNum = info.getCeseqNum() == null ? 1
						: info.getCeseqNum() + 1;
			}

		}
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo data = (CustomsEnvelopCommodityInfo) list
					.get(i);
			if (data.getCeseqNum() == null) {
				data.setCeseqNum(ceseqNum);
				ceseqNum += ceseqNum;
			}
			this.saveOrUpdate(data);
		}
	}

	/**
	 * 获得当前关封单据的商品信息的个数
	 */
	public int findCustomsEnvelopCommodityInfoCount(String parentId) {
		List list = this.find(
				"select count(*) from CustomsEnvelopCommodityInfo b  "
						+ " where b.customsEnvelopBill.id = ? ",
				new Object[] { parentId });
		if (list == null || list.size() <= 0) {
			return 0;
		}
		return Integer.parseInt(list.get(0).toString());
	}

	/**
	 * 获得关封单据信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfo(String parentId) {
		return this
				.find("select b from CustomsEnvelopCommodityInfo b where b.customsEnvelopBill.id = ? order by b.ceseqNum ",
						new Object[] { parentId });
	}

	/**
	 * 获得关封单据信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfo(String parentId, String emsNo) {
		return this.find("select b from CustomsEnvelopCommodityInfo b "
				+ " where b.customsEnvelopBill.id = ? and b.emsNo=? ",
				new Object[] { parentId, emsNo });
	}

	/**
	 * 来自电子帐册料件--并通过封商品信息进行筛选
	 */
	public List findEmsMateriel(EmsHeadH2k emsH2k, String parentId) {
		return findEmsMaterielOrFinishedProduct(MaterielType.MATERIEL, emsH2k,
				parentId);
	}

	/**
	 * 来自电子帐册成品--并通过封商品信息进行筛选
	 */
	public List findEmsFinishedProduct(EmsHeadH2k emsH2k, String parentId) {
		return findEmsMaterielOrFinishedProduct(MaterielType.FINISHED_PRODUCT,
				emsH2k, parentId);
	}

	/**
	 * 来自电子帐册料件或成品--并通过封商品信息进行筛选
	 */
	private List findEmsMaterielOrFinishedProduct(String type,
			EmsHeadH2k emsH2k, String parentId) {
		List list = null;
		if (type.equals(MaterielType.MATERIEL)) {
			if (getIsEmsH2kSend()) {
				list = this
						.find("select c from EmsHeadH2kImg c  "
								+ "	where c.emsHeadH2k=? and c.seqNum not in "
								+ "(select e.seqNum "
								+ " from CustomsEnvelopCommodityInfo e where e.customsEnvelopBill.id =? ) and c.sendState = ?",
								new Object[] { emsH2k, parentId,
										Integer.valueOf(SendState.SEND) });
			} else {
				list = this
						.find("select c from EmsHeadH2kImg c  "
								+ "	where c.emsHeadH2k=? and c.seqNum not in "
								+ "(select e.seqNum "
								+ " from CustomsEnvelopCommodityInfo e where e.customsEnvelopBill.id =? )",
								new Object[] { emsH2k, parentId });
			}
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
			if (getIsEmsH2kSend()) {
				list = this
						.find("select c from EmsHeadH2kExg c  "
								+ "	where c.emsHeadH2k=? and c.seqNum not in "
								+ "(select e.seqNum "
								+ " from CustomsEnvelopCommodityInfo e where e.customsEnvelopBill.id = ?) and c.sendState = ?",
								new Object[] { emsH2k, parentId,
										Integer.valueOf(SendState.SEND) });
			} else {
				list = this
						.find("select c from EmsHeadH2kExg c  "
								+ "	where c.emsHeadH2k=? and c.seqNum not in "
								+ "(select e.seqNum "
								+ " from CustomsEnvelopCommodityInfo e where e.customsEnvelopBill.id = ?)",
								new Object[] { emsH2k, parentId });
			}
		}
		return list;
	}

	/**
	 * 来自电子帐册料件或成品--并通过封商品信息进行筛选
	 */
	public List findEmsHeadH2kImgByEmsNo(String emsNo, String parentId) {
		if (getIsEmsH2kSend()) {
			List list = this.find("select c from EmsHeadH2kImg c  "
					+ "	where c.emsHeadH2k.emsNo=? and c.sendState=? "
					+ " and c.emsHeadH2k.historyState=? "
					// + " and c.seqNum not in (select e.seqNum "
					// + " from CustomsEnvelopCommodityInfo e"
					// + " where e.customsEnvelopBill.id =? )"
					+ " order by c.seqNum ",
					new Object[] { emsNo, Integer.valueOf(SendState.SEND),
							false });
			// System.out.println("-----------list.size:"+list.size());
			return list;
		} else {
			List list = this
					.find("select c from EmsHeadH2kImg c  "
							+ "	where c.emsHeadH2k.emsNo=? and c.emsHeadH2k.declareState=? "
							+ " and c.emsHeadH2k.historyState=? "
							// +
							// " and c.seqNum not in (select e.seqNum "
							// + " from CustomsEnvelopCommodityInfo e"
							// + " where e.customsEnvelopBill.id =? )"
							+ " order by c.seqNum ", new Object[] { emsNo,
							DeclareState.PROCESS_EXE, false
					// ,parentId
							});

			return list;
		}
	}

	/**
	 * 来自电子帐册料件或成品--并通过封商品信息进行筛选
	 */
	public List findEmsHeadH2kExgByEmsNo(String emsNo, String parentId) {
		if (getIsEmsH2kSend()) {
			List list = this.find("select c from EmsHeadH2kExg c  "
					+ "	where c.emsHeadH2k.emsNo=? " + " and c.sendState=? "
					+ " and c.emsHeadH2k.historyState=? "
					// + " and c.seqNum not in (select e.seqNum "
					// + " from CustomsEnvelopCommodityInfo e"
					// + " where e.customsEnvelopBill.id =?  )"
					+ " order by c.seqNum ",
					new Object[] { emsNo, Integer.valueOf(SendState.SEND),
							false
					// , parentId
					});
			return list;
		} else {
			List list = this.find("select c from EmsHeadH2kExg c  "
					+ "	where c.emsHeadH2k.emsNo=? "
					+ " and c.emsHeadH2k.declareState=? "
					+ " and c.emsHeadH2k.historyState=? "
					// + " and c.seqNum not in (select e.seqNum "
					// + " from CustomsEnvelopCommodityInfo e"
					// + " where e.customsEnvelopBill.id =?  )"
					+ " order by c.seqNum ", new Object[] { emsNo,
					DeclareState.PROCESS_EXE, false
			// , parentId
					});
			return list;
		}
	}

	/**
	 * * ---------------------------------------------------- 结转单据用到的方法
	 * -------------------------------------------------------
	 */

	/**
	 * 获得结转所有单据
	 */
	public List findTransferFactoryBill() {
		return this.find(
				"select a from TransferFactoryBill as a where a.company.id=?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 获得结转所有单据来自进出货标志
	 */
	public List findTransferFactoryBillByImpExpGoodsFlag(boolean impExpGoodsFlag) {
		return this.find("select a from TransferFactoryBill as a "
				+ "	where a.isImpExpGoods=? and a.company.id=?", new Object[] {
				Boolean.valueOf(impExpGoodsFlag),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得结转所有单据来自客户或供应商Id
	 */
	public List findTransferFactoryBillByScmCocId(String scmCocId,
			boolean impExpGoods) {
		return this
				.find("select a from TransferFactoryBill as a where a.company.id=? and a.isImpExpGoods = ?"
						+ " and a.scmCoc.id = ? ", new Object[] {
						CommonUtils.getCompany().getId(),
						new Boolean(impExpGoods), scmCocId });
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、存在未转关封申请单的商品 的单据
	 */
	public List findTransferFactoryBillByScmCocIdToCER(String scmCocId) {
		return this
				.find("select a from TransferFactoryBill as a where a.scmCoc.id = ? "
						+ " and a.isAvailability = ? "
						+ " and a.company.id=? "
						+ " and a.id in "
						+ " (select b.id from "
						+ " TransferFactoryCommodityInfo t join t.transferFactoryBill  b "
						+ " where t.isTransferCustomsEnvelopReqeust=? or t.isTransferCustomsEnvelopReqeust is null )",
						new Object[] { scmCocId, new Boolean(true),
								CommonUtils.getCompany().getId(),
								new Boolean(false) });
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、存在未转报关清单的商品 的单据
	 */
	public List findTransferFactoryBillByScmCocIdToATC(String scmCocId,
			String emsNo) {
		// return this
		// .find(
		// "select a from TransferFactoryBill as a where a.scmCoc.id = ? "
		// + " and a.isAvailability = ? "
		// + " and a.company.id=? "
		// + " and a.id in "
		// + " (select b.id from "
		// + " TransferFactoryCommodityInfo t join t.transferFactoryBill b "
		// + " where t.isTransferATC=? or t.isTransferATC is null )",
		// new Object[] { scmCocId, new Boolean(true),
		// CommonUtils.getCompany().getId(),
		// new Boolean(false) });
		return this.find(
				"select a from TransferFactoryBill as a ,CustomsEnvelopBill as b "
						+ " where a.envelopNo=b.customsEnvelopBillNo "
						+ " and a.scmCoc.id = ? "
						+ " and a.isAvailability = ? and a.company.id=? "
						+ " and b.emsNo=? and b.company.id=? ", new Object[] {
						scmCocId, new Boolean(true),
						CommonUtils.getCompany().getId(), emsNo,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存结转单据
	 */
	public void saveTransferFactoryBill(TransferFactoryBill transferFactoryBill) {
		if (transferFactoryBill.getId() == null
				|| "".equals(transferFactoryBill.getId())) {
			List<TransferFactoryBill> list = this
					.find("from TransferFactoryBill where transferFactoryBillNo = '"
							+ transferFactoryBill.getTransferFactoryBillNo()
							+ "'");
			if (list != null && list.size() > 0) {
				throw new RuntimeException("单据编号不能重复!");
			}
		}
		this.saveOrUpdate(transferFactoryBill);
	}

	/**
	 * 删除结转单据
	 */
	public void deleteTransferFactoryBill(
			TransferFactoryBill transferFactoryBill) {
		this.delete(transferFactoryBill);
	}

	/**
	 * 删除结转单据商品信息数据
	 */
	public void deleteTransferFactoryCommodityInfo(
			TransferFactoryCommodityInfo data) {
		this.delete(data);
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public void saveTransferFactoryCommodityInfo(
			TransferFactoryCommodityInfo commInfo) {
		this.saveOrUpdate(commInfo);
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public void saveTransferFactoryCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			TransferFactoryCommodityInfo data = (TransferFactoryCommodityInfo) list
					.get(i);
			this.saveOrUpdate(data);
		}
	}

	/**
	 * 获得当前转厂进出口的商品信息的个数
	 */
	public int findTransferFactoryCommodityInfoCount(String parentId) {
		List list = this.find(
				"select count(*) from TransferFactoryCommodityInfo b  "
						+ " where b.transferFactoryBill.id = ? ",
				new Object[] { parentId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得结转单据信息加载子表的记录
	 */
	public List findTransferFactoryCommodityInfo(String parentId) {
		return this
				.find("select b from TransferFactoryCommodityInfo b where b.transferFactoryBill.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 获得结转单据信息加载子表的记录
	 */
	public TransferFactoryCommodityInfo findTransferFactoryCommodityInfoById(
			String commodityInfoId) {
		List list = this.find(
				"select b from TransferFactoryCommodityInfo b where b.id = ? ",
				new Object[] { commodityInfoId });
		if (list.isEmpty())
			return null;
		else
			return (TransferFactoryCommodityInfo) list.get(0);
	}

	/**
	 * 获得最大的结转单据号来自进出货标志 wss2010.09.08加是pattern参数
	 */
	public List getMaxTransferFactoryBillNoByImpExpGoodsFlag(
			boolean impExpGoodsFlag, String pattern) {
		return this
				.find("select max(a.transferFactoryBillNo) from TransferFactoryBill as a "
						+ "	where a.transferFactoryBillNo like ? and  a.isImpExpGoods=? and a.company.id=?",
						new Object[] { pattern + "%",
								Boolean.valueOf(impExpGoodsFlag),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得当前转厂进出口的商品信息的个数
	 */
	public double findTransFactCommInfoAmount(String envelopCode, String emsNo,
			Integer seqNum, String comInfoId) {

		List<Object> parameters = new ArrayList<Object>();

		// TODO 加一个新的查询条件 关封序号
		String hsql = "select sum(a.transFactAmount) from TransferFactoryCommodityInfo a  "
				+ " where a.transferFactoryBill.company.id = ? "
				+ " and a.transferFactoryBill.envelopNo=? "
				+ " and a.seqNum=? and a.emsNo=? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(envelopCode);
		parameters.add(seqNum);
		parameters.add(emsNo);
		// parameters.add(ceseqNum);

		if (comInfoId != null && !"".equals(comInfoId)) {
			hsql += "  and a.id<>?  ";
			parameters.add(comInfoId);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() == 0 || list.get(0) == null) {
			return 0.0;
		}
		return Double.parseDouble(list.get(0).toString());
	}

	// /**
	// * 获得当前转厂进出口的商品信息的个数
	// */
	// public double findTransFactCommInfoAmountNew(
	// TransferFactoryCommodityInfo commInfo) {
	// List list = this.find(
	// " select sum(a.transFactAmount) from TransferFactoryCommodityInfo a  "
	// + " where a.transferFactoryBill.company.id = ? "
	// + " and a.transferFactoryBill.envelopNo=? "
	// + " and a.seqNum=? and a.emsNo=? ", new Object[] {
	// CommonUtils.getCompany().getId(),
	// commInfo.getTransferFactoryBill().getEnvelopNo(),
	// commInfo.getSeqNum(), commInfo.getEmsNo() });
	// if (list.size() == 0 || list.get(0) == null) {
	// return 0.0;
	// }
	// return Double.parseDouble(list.get(0).toString());
	// }

	/**
	 * 保存转厂初始化单据
	 */
	public void saveTransferFactoryInitBill(
			TransferFactoryInitBill transferFactoryInitBill) {
		this.saveOrUpdate(transferFactoryInitBill);
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	public void saveTransferFactoryInitBillCommodityInfo(
			TransferFactoryInitBillCommodityInfo transferFactoryInitBillCommodityInfo) {
		this.saveOrUpdate(transferFactoryInitBillCommodityInfo);
	}

	// /**
	// * 保存转厂初始化单据商品信息
	// */
	// public void saveTransferFactoryInitBillCommodityInfoforCustoms(
	// TransferInitBillInfoForCustoms transferFactoryInitBillCommodityInfo) {
	// this.saveOrUpdate(transferFactoryInitBillCommodityInfo);
	// }

	/**
	 * 删除转厂初始化单据
	 */
	public void deleteTransferFactoryInitBill(
			TransferFactoryInitBill transferFactoryInitBill) {
		this.delete(transferFactoryInitBill);
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	public void deleteTransferFactoryInitBillCommodityInfo(
			TransferFactoryInitBillCommodityInfo transferFactoryInitBillCommodityInfo) {
		this.delete(transferFactoryInitBillCommodityInfo);
	}

	/**
	 * 获得所有初始化结转单据
	 */
	public List findTransferFactoryInitBill() {
		return this
				.find("select a from TransferFactoryInitBill as a where a.company.id=?",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 获得结转初始化单据-根据单据id
	 */
	public TransferFactoryInitBill findTransferFactoryInitBillById(
			TransferFactoryInitBill initBill) {
		List list = this
				.find("select a from TransferFactoryInitBill as a where a.company.id=? and a.id=?",
						new Object[] { CommonUtils.getCompany().getId(),
								initBill.getId() });
		return list.size() < 1 ? null : (TransferFactoryInitBill) list.get(0);
	}

	/**
	 * 获得结转所有单据
	 */
	public List findTransferFactoryInitBill(boolean isImpExpFlag) {
		return this
				.find("select a from TransferFactoryInitBill as a where a.company.id=? and a.isImpExpFlag=?",
						new Object[] { CommonUtils.getCompany().getId(),
								isImpExpFlag });
	}

	/**
	 * 取得最大转厂初始化单据号码+1
	 * 
	 * @return
	 */
	public String getTransferFactoryInitBillMaxCode() {
		String yearmonthday = "";
		String serialNo = "";
		Calendar calendar = Calendar.getInstance();
		yearmonthday = organizeStr(calendar.get(Calendar.YEAR), 4);
		yearmonthday += organizeStr(calendar.get(Calendar.MONTH) + 1, 2);
		yearmonthday += organizeStr(calendar.get(Calendar.DAY_OF_MONTH), 2);
		List list = this.find(
				"select max(a.transferFactoryInitBillCode) from TransferFactoryInitBill as a "
						+ " where a.transferFactoryInitBillCode like ?",
				new Object[] { yearmonthday + "%" });
		if (list.size() < 1) {
			serialNo = "0001";
		} else if (list.get(0) == null) {
			serialNo = "0001";
		} else {
			String temp = list.get(0).toString();
			if (temp.trim().equals("")) {
				serialNo = "0001";
			} else {
				int n = Integer.parseInt(temp.substring(temp.length() - 4,
						temp.length())) + 1;
				serialNo = organizeStr(n, 4);
			}
		}
		return yearmonthday + serialNo;
	}

	private String organizeStr(int m, int length) {
		String s = String.valueOf(m);
		int n = length - s.length();
		for (int i = 0; i < n; i++) {
			s = "0" + s;
		}
		return s;
	}

	// /**
	// * 获得转厂初始化单据信息加载父子表的记录
	// */
	// public TransferFactoryInitBill findTransferFactoryInitBill(String
	// parentId) {
	// List list = this
	// .findLists(
	// "from TransferFactoryInitBill as a left join fetch
	// a.transgerFactoryInitBillCommodityInfoes b left join fetch
	// b.materiel where a.id = ? and a.company.id=?",
	// new Object[] { parentId,
	// CommonUtils.getCompany().getId() });
	// if (list.size() <= 0 || list.get(0) == null) {
	// return null;
	// }
	// return (TransferFactoryInitBill) list.get(0);
	// }

	/**
	 * 获得转厂初始化单据信息加载子表的记录
	 */
	public List findTransferFactoryInitCommodityInfo(String parentId) {
		return this
				.find("select b from TransferFactoryInitBillCommodityInfo b where b.transferFactoryInitBill.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 获得转厂初始化单据信息加载子表的记录for 报关
	 */
	public List findTransferFactoryInitCommodityInfoForCustoms(String parentId) {
		return this
				.find("select b from TransferInitBillInfoForCustoms b "
						+ " left join fetch b.unit where b.transferFactoryInitBill.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 获得结转单据商品信息来自父对象
	 */
	public List findTransferFactoryCommodityInfoByParent(List parentList) {
		String hsql = "select b from TransferFactoryCommodityInfo b left "
				+ "outer join b.materiel where ( b.isTransferCustomsEnvelopReqeust = ? or b.isTransferCustomsEnvelopReqeust is null ) ";
		String condition = "";
		List objs = new ArrayList();
		objs.add(new Boolean(false));
		for (int i = 0; i < parentList.size(); i++) {
			if (parentList.get(i) instanceof TempTransferFactoryBill) {
				TempTransferFactoryBill t = (TempTransferFactoryBill) parentList
						.get(i);
				if (i == 0) {
					condition += "  b.transferFactoryBill.id = ? ";
				} else {
					condition += "  or  b.transferFactoryBill.id = ? ";
				}
				objs.add(t.getT().getId());
			}
		}
		if (condition.equals("") == false) {
			hsql += " and (" + condition + ")";
		}
		return this.find(hsql, objs.toArray());
	}

	/**
	 * 获得结转单据商品信息来自父对象(用于生成报关清单ToATC)
	 */
	public List findTransferFactoryCommodityInfoByParentToATC(List parentList) {
		String hsql = "select b from TransferFactoryCommodityInfo b "
				+ " where ( b.isTransferATC = ? or b.isTransferATC is null ) ";
		String condition = "";
		List objs = new ArrayList();
		objs.add(new Boolean(false));
		for (int i = 0; i < parentList.size(); i++) {
			if (parentList.get(i) instanceof TempTransferFactoryBill) {
				TempTransferFactoryBill t = (TempTransferFactoryBill) parentList
						.get(i);
				if (i == 0) {
					condition += "  b.transferFactoryBill.id = ? ";
				} else {
					condition += "  or  b.transferFactoryBill.id = ? ";
				}
				objs.add(t.getT().getId());
			}
		}
		if (condition.equals("") == false) {
			hsql += " and (" + condition + ")";
		}
		return this.find(hsql, objs.toArray());
	}

	/**
	 * 获得结转单据商品信息来自父对象(用于生成报关清单ToATC)
	 */
	public int findTransFactCommInfoToCustomsBillList(
			String transferFactoryBillId) {
		String hsql = "select count(b) from TransferFactoryCommodityInfo b "
				+ " where b.isTransferATC = ? "
				+ " and b.transferFactoryBill.id = ? ";
		List objs = new ArrayList();
		objs.add(new Boolean(true));
		objs.add(transferFactoryBillId);
		List list = this.find(hsql, objs.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 根据结转单据查询报关清单(联网监管)
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	public List findBcusCustomsBillListByTransFactBill(
			TransferFactoryBill transferFactoryBill) {
		String hsql = "select distinct a.billList from AtcMergeAfterComInfo a,"
				+ " TransferFactoryCommodityInfo b,MakeApplyToCustomsInfo c "
				+ " where a.id=c.mergeAfterCommInfoId "
				+ " and b.id=c.transFactCommInfo "
				+ " and b.transferFactoryBill.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(transferFactoryBill.getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据结转单据查询报关清单(电子手册)
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	public List findDzscCustomsBillListByTransFactBill(
			TransferFactoryBill transferFactoryBill) {
		String hsql = "select distinct a.billList from DzscBillListAfterCommInfo a,"
				+ " TransferFactoryCommodityInfo b,MakeApplyToCustomsInfo c "
				+ " where a.id=c.mergeAfterCommInfoId "
				+ " and b.id=c.transFactCommInfo "
				+ " and b.transferFactoryBill.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(transferFactoryBill.getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过结转商品信息的料号（ptNo) 获得归并系关中关并后的商品信息--成品
	 */
	public EmsEdiMergerExgAfter findEmsEdiMergerExgAfterByPtNo(
			EmsEdiMergerHead emsEdiMergerHead, String ptNo) {
		List list = this
				.find("select e from EmsEdiMergerExgAfter e where "
						+ " e.emsEdiMergerHead = ? and e.company = ? and e.id in "
						+ " (select a.emsEdiMergerExgAfter.id from EmsEdiMergerExgBefore a "
						+ " where a.ptNo = ? ) ", new Object[] {
						emsEdiMergerHead, CommonUtils.getCompany().getId(),
						ptNo });
		if (list == null || list.size() <= 0) {
			return null;
		}
		return (EmsEdiMergerExgAfter) list.get(0);
	}

	/**
	 * 通过结转商品信息的料号（ptNo) 获得归并系关中关并后的商品信息--料件
	 */
	public EmsEdiMergerImgAfter findEmsEdiMergerImgAfterByPtNo(
			EmsEdiMergerHead emsEdiMergerHead, String ptNo) {
		List list = this
				.find("select e from EmsEdiMergerImgAfter e where "
						+ " e.emsEdiMergerHead = ? and e.company = ? and e.id in "
						+ " (select a.emsEdiMergerImgAfter.id from EmsEdiMergerImgBefore a "
						+ " where a.ptNo = ? ) ", new Object[] {
						emsEdiMergerHead, CommonUtils.getCompany().getId(),
						ptNo });
		if (list == null || list.size() <= 0) {
			return null;
		}
		return (EmsEdiMergerImgAfter) list.get(0);
	}

	public InnerMergeData findInnerMergerByPtNo(String ptNo, String type) {
		List list = this
				.find("select a from InnerMergeData a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.materiel.ptNo = ? and a.company.id = ? and a.materiel.scmCoi.coiProperty = ? and a.hsAfterTenMemoNo is not null",
						new Object[] { ptNo, CommonUtils.getCompany().getId(),
								type });
		if (list != null && list.size() > 0) {
			return (InnerMergeData) list.get(0);
		} else {
			return null;
		}
	}

	public InnerMergeData findInnerMergerByPtNo(String ptNo) {
		List list = this.find("select a from InnerMergeData a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit"
				+ " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit"
				+ " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.materiel.ptNo = ? and a.company.id = ? "
				+ " and a.hsAfterTenMemoNo is not null", new Object[] { ptNo,
				CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (InnerMergeData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 转厂进出货明细表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billType
	 *            单据类型
	 * @param billCode
	 *            单据号码
	 * @param materielCode
	 *            料号
	 * @param materielName
	 *            名称
	 * @param emsNo
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */
	public List findTransferFactoryImpExpGoodsList(Date beginDate,
			Date endDate, Integer billType, String billCode,
			String materielCode, String materielName, String seqNum,
			String companyName) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append("select a.transferFactoryBill.beginAvailability,a.transferFactoryBill.isImpExpGoods,"
				+ " a.transferFactoryBill.transferFactoryBillNo,a.seqNum, "
				+ " a.complex.code,a.commName,a.transFactAmount,a.transferFactoryBill.company.name,a.unit.name, "
				+ " a.transferFactoryBill.envelopNo,a.transferFactoryBill.scmCoc.name "
				+ " from TransferFactoryCommodityInfo as a,"
				+ " CustomsEnvelopBill b "
				+ " where a.transferFactoryBill.envelopNo=b.customsEnvelopBillNo "
				+ " and a.transferFactoryBill.company.id=? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (billType != null) {
			hsql.append(" and a.transferFactoryBill.isImpExpGoods=? ");
			if (billType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				parameters.add(true);
			} else {
				parameters.add(false);
			}
		}
		if (beginDate != null) {
			hsql.append(" and a.transferFactoryBill.beginAvailability>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.transferFactoryBill.beginAvailability<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (billCode != null && !billCode.trim().equals("")) {
			hsql.append(" and a.transferFactoryBill.transferFactoryBillNo like ? ");
			parameters.add("%" + billCode + "%");
		}
		if (materielCode != null && !materielCode.trim().equals("")) {
			hsql.append(" and a.complex.code like ? ");
			parameters.add("%" + materielCode + "%");
		}
		if (materielName != null && !materielName.trim().equals("")) {
			hsql.append(" and a.commName like ? ");
			parameters.add("%" + materielName + "%");
		}
		if (seqNum != null && !seqNum.trim().equals("")) {
			hsql.append(" and a.seqNum =? ");
			parameters.add(Integer.valueOf(seqNum));
		}
		if (companyName != null && !companyName.trim().equals("")) {
			hsql.append(" and b.transCompany.name like ? ");
			parameters.add("%" + companyName + "%");
		}
		return this.find(hsql.toString(), parameters.toArray());
	}

	// /**
	// * @param endDate
	// * @return
	// */
	// private Date backRollDate(Date endDate) {
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// String strDate = dateFormat.format(endDate);
	// strDate += " 23:59:59";
	// try {
	// endDate = dateFormat.parse(strDate);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return endDate;
	// }
	//
	// /**
	// * @param beginDate
	// * @return
	// */
	// private Date frontRollDate(Date beginDate) {
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// String strDate = dateFormat.format(beginDate);
	// strDate += " 00:00:00";
	// try {
	// beginDate = dateFormat.parse(strDate);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// return beginDate;
	// }

	/**
	 * 结转进出口明细表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billType
	 *            报关单类型
	 * @param billCode
	 *            单据号码
	 * @param materielCode
	 *            料号
	 * @param materielName
	 *            名称
	 * @param seqNum
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */
	public List findTransferImpExpCustomsList(Date beginDate, Date endDate,
			Integer billType, String billCode, String materielCode,
			String materielName, String seqNum, String companyName) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		List<Object> lsAll = new ArrayList<Object>();
		String commonStr = " select a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.impExpType,"
				+ "a.baseCustomsDeclaration.serialNumber,a.commSerialNo , "
				+ " a.complex.code,a.commName,a.commAmount,a.unit.name,"
				+ " a.baseCustomsDeclaration.company.name, "
				+ " a.baseCustomsDeclaration.customsDeclarationCode,"
				+ " a.baseCustomsDeclaration.customer.name";
		hsql.append(" where a.baseCustomsDeclaration.company.id=? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (billType != null) {
			hsql.append(" and a.baseCustomsDeclaration.impExpType=? ");
			parameters.add(billType);
		}
		if (beginDate != null) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (billCode != null && !billCode.trim().equals("")) {
			hsql.append(" and a.baseCustomsDeclaration.serialNumber like ? ");
			parameters.add("%" + billCode + "%");
		}
		if (materielCode != null && !materielCode.trim().equals("")) {
			hsql.append(" and a.complex.code like ? ");
			parameters.add("%" + materielCode + "%");
		}
		if (materielName != null && !materielName.trim().equals("")) {
			hsql.append(" and a.commName like ? ");
			parameters.add("%" + materielName + "%");
		}
		if (seqNum != null && !seqNum.trim().equals("")) {
			hsql.append(" and a.commSerialNo= ? ");
			parameters.add(Integer.valueOf(seqNum));
		}
		// if (companyName != null && !companyName.trim().equals("")) {
		// hsql.append(" and a.baseCustomsDeclaration.company.name like ? ");
		// parameters.add("%" + companyName + "%");
		// }
		List lsBcus = this.find(commonStr
				+ " from CustomsDeclarationCommInfo a " + hsql.toString(),
				parameters.toArray());
		List lsBcs = this.find(commonStr
				+ " from BcsCustomsDeclarationCommInfo a " + hsql.toString(),
				parameters.toArray());
		List lsDzsc = this.find(commonStr
				+ " from DzscCustomsDeclarationCommInfo a " + hsql.toString(),
				parameters.toArray());
		lsAll.addAll(lsBcus);
		lsAll.addAll(lsBcs);
		lsAll.addAll(lsDzsc);
		return lsAll;
	}

	// ////////////////////////////下面的是转厂进出货状况表的查询
	/**
	 * 统计转厂进出货状况表的料品()号码
	 */
	public List findStatisticAnalyseMateriel(Date beginDate, Date endDate,
			Integer impExpType, String billCode, String materielCode,
			String materielName, String materielSpec, String companyName,
			String scmcode) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append(" select distinct a.complex.code,a.commName,a.unit.name,a.seqNum,"
				+ " a.transferFactoryBill.scmCoc.name,a.transferFactoryBill.envelopNo,a.emsNo "
				+ " from TransferFactoryCommodityInfo as a ,CustomsEnvelopBill b "
				+ " where a.transferFactoryBill.envelopNo=b.customsEnvelopBillNo"
				+ " and a.transferFactoryBill.company.id=? "
				+ " and a.transferFactoryBill.isImpExpGoods=? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			parameters.add(new Boolean(true));
		} else {
			parameters.add(new Boolean(false));
		}
		if (beginDate != null) {
			hsql.append(" and a.transferFactoryBill.beginAvailability>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.transferFactoryBill.beginAvailability<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (billCode != null && !billCode.trim().equals("")) {
			hsql.append(" and a.transferFactoryBill.transferFactoryBillNo like ? ");
			parameters.add("%" + billCode + "%");
		}
		if (materielCode != null && !materielCode.trim().equals("")) {
			hsql.append(" and a.complex.code like ? ");
			parameters.add("%" + materielCode + "%");
		}
		if (materielName != null && !materielName.trim().equals("")) {
			hsql.append(" and a.commName like ? ");
			parameters.add("%" + materielName + "%");
		}
		if (materielSpec != null && !materielSpec.trim().equals("")) {
			hsql.append(" and a.commSpec like ? ");
			parameters.add("%" + materielSpec + "%");
		}
		if (companyName != null && !companyName.trim().equals("")) {
			hsql.append(" and b.transCompany.name like ? ");
			parameters.add("%" + companyName + "%");
		}
		if (scmcode != null) {
			hsql.append(" and a.transferFactoryBill.scmCoc.code = ? ");
			parameters.add(scmcode);
		}
		hsql.append(" order by a.seqNum");
		return this.find(hsql.toString(), parameters.toArray());
	}

	/**
	 * 从转厂单据统计转厂资料
	 * 
	 * @param impExpType
	 * @param billCode
	 * @param materielCode
	 * @param materielName
	 * @param emsNo
	 * @param companyName
	 * @return
	 */
	public List findStatisticTotalImpExpGoodsQuantity(Date beginDate,
			Date endDate, Integer impExpType, String billCode,
			String materielCode, String materielName, String materielSpec,
			String companyName, String scmcode) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append(" select a.seqNum,a.emsNo,a.transferFactoryBill.envelopNo,"
				+ "a.transferFactoryBill.scmCoc.name, sum(a.transFactAmount) "
				+ " from TransferFactoryCommodityInfo as a "
				+ " where a.transferFactoryBill.company.id=? "
				+ " and a.transferFactoryBill.isImpExpGoods=? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			parameters.add(new Boolean(true));
		} else {
			parameters.add(new Boolean(false));
		}
		if (billCode != null && !billCode.trim().equals("")) {
			hsql.append(" and a.transferFactoryBill.transferFactoryBillNo like ? ");
			parameters.add("%" + billCode + "%");
		}
		if (beginDate != null) {
			hsql.append(" and a.transferFactoryBill.beginAvailability>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.transferFactoryBill.beginAvailability<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (materielCode != null && !materielCode.trim().equals("")) {
			hsql.append(" and a.complex.code like ? ");
			parameters.add("%" + materielCode + "%");
		}
		if (materielName != null && !materielName.trim().equals("")) {
			hsql.append(" and a.commName like ? ");
			parameters.add("%" + materielName + "%");
		}
		if (materielSpec != null && !materielSpec.trim().equals("")) {

			hsql.append(" and a.commSpec like ? ");
			parameters.add("%" + materielSpec + "%");
		}
		if (scmcode != null) {
			hsql.append(" and a.transferFactoryBill.scmCoc.code = ? ");
			parameters.add(scmcode);
		}
		hsql.append(" group by a.seqNum,a.emsNo,a.transferFactoryBill.envelopNo,"
				+ "a.transferFactoryBill.scmCoc.name ");
		return this.find(hsql.toString(), parameters.toArray());
	}

	public Double findTotalImpExpGoodsQuantity(Integer impExpType,
			String envelopNo, String emsNo, Integer seqNum,
			String oppoisteEnterpriseName) {
		String hsql = "";
		List<Object> parameters = new ArrayList<Object>();
		hsql = " select sum(a.transFactAmount) from TransferFactoryCommodityInfo a "
				+ " where a.transferFactoryBill.company.id=?  "
				+ "and a.transferFactoryBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			parameters.add(new Boolean(true));
		} else {
			parameters.add(new Boolean(false));
		}
		if (envelopNo != null) {
			hsql += " and a.transferFactoryBill.envelopNo = ?";
			parameters.add(envelopNo);
		}
		if (emsNo != null) {
			hsql += " and a.emsNo = ? ";
			parameters.add(emsNo);
		}
		if (seqNum != null) {
			hsql += " and a.seqNum = ? ";
			parameters.add(seqNum);
		}
		if (oppoisteEnterpriseName != null) {
			hsql += " and a.transferFactoryBill.scmCoc.name = ? ";
			parameters.add(oppoisteEnterpriseName);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 从报关单统计转厂资料
	 * 
	 * @param impExpType
	 * @param billCode
	 * @param materielCode
	 * @param materielName
	 * @param emsNo
	 * @param companyName
	 * @return
	 */
	public List findStatisticTotalTransferQuantity(Date beginDate,
			Date endDate, Integer impExpType, String billCode,
			String materielCode, String materielName, String materielSpec,
			String companyName, String scmcode, String envlopNo) {
		List<Object> lsAll = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append(" where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpType=? ");
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpType);
		if (beginDate != null) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (envlopNo != null) {// 注意要关封
			hsql.append(" and a.baseCustomsDeclaration.customsEnvelopBillNo=? ");
			parameters.add(envlopNo);
		}
		if (materielCode != null && !materielCode.trim().equals("")) {
			hsql.append(" and a.complex.code like ? ");
			parameters.add("%" + materielCode + "%");
		}
		if (materielName != null && !materielName.trim().equals("")) {
			hsql.append(" and a.commName like ? ");
			parameters.add("%" + materielName + "%");
		}
		if (materielSpec != null && !materielSpec.trim().equals("")) {
			hsql.append(" and a.commSpec like ? ");
			parameters.add("%" + materielSpec + "%");
		}
		if (scmcode != null) {
			hsql.append(" and a.baseCustomsDeclaration.customer.code = ? ");
			parameters.add(scmcode);
		}
		hsql.append(" group by a.commSerialNo,a.baseCustomsDeclaration.emsHeadH2k,"
				+ "a.baseCustomsDeclaration.customsEnvelopBillNo,"
				+ "a.baseCustomsDeclaration.customer.name ");
		List lsBcus = this
				.find(" select a.commSerialNo,a.baseCustomsDeclaration.emsHeadH2k,"
						+ "a.baseCustomsDeclaration.customsEnvelopBillNo,"
						+ "a.baseCustomsDeclaration.customer.name,sum(a.commAmount) "
						+ " from CustomsDeclarationCommInfo as a  "
						+ hsql.toString(), parameters.toArray());
		List lsDzsc = this
				.find(" select a.commSerialNo,a.baseCustomsDeclaration.emsHeadH2k,"
						+ "a.baseCustomsDeclaration.customsEnvelopBillNo,"
						+ "a.baseCustomsDeclaration.customer.name,sum(a.commAmount) "
						+ " from DzscCustomsDeclarationCommInfo as a  "
						+ hsql.toString(), parameters.toArray());
		List lsBcs = this
				.find(" select a.commSerialNo,a.baseCustomsDeclaration.emsHeadH2k,"
						+ "a.baseCustomsDeclaration.customsEnvelopBillNo,"
						+ "a.baseCustomsDeclaration.customer.name,sum(a.commAmount) "
						+ " from BcsCustomsDeclarationCommInfo as a  "
						+ hsql.toString(), parameters.toArray());
		lsAll.addAll(lsBcus);
		lsAll.addAll(lsBcs);
		lsAll.addAll(lsDzsc);
		return lsAll;
	}

	public List findStatisticTotalImpExpGoodsInitQuantity(Date beginDate,
			Date endDate, Integer impExpType, String billCode,
			String materielCode, String materielName, String materielSpec,
			String companyName, String scmcode) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append(" select a.seqNum,a.emsNo,a.transferFactoryInitBill.envelopNo,"
				+ "a.transferFactoryInitBill.scmCoc.name,sum(a.noTransferQuantity) "
				+ " from TransferFactoryInitBillCommodityInfo as a "
				+ " where a.transferFactoryInitBill.company.id=? ");
		hsql.append(" and a.transferFactoryInitBill.isImpExpFlag=? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			parameters.add(true);
		} else {
			parameters.add(false);
		}
		if (beginDate != null) {
			hsql.append(" and a.transferFactoryInitBill.effectiveDate>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.transferFactoryInitBill.effectiveDate<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (materielCode != null && !materielCode.trim().equals("")) {
			hsql.append(" and a.complex.code like ? ");
			parameters.add("%" + materielCode + "%");
		}
		if (materielName != null && !materielName.trim().equals("")) {
			hsql.append(" and a.commName like ? ");
			parameters.add("%" + materielName + "%");
		}
		if (materielSpec != null && !materielSpec.trim().equals("")) {
			hsql.append(" and a.commSpec like ? ");
			parameters.add("%" + materielSpec + "%");
		}
		if (scmcode != null) {
			hsql.append(" and a.transferFactoryInitBill.scmCoc.code = ? ");
			parameters.add(scmcode);
		}
		// if (companyName != null && !companyName.trim().equals("")) {
		// hsql.append(" and a.transferFactoryInitBill.company.name like ? ");
		// parameters.add("%" + companyName + "%");
		// }
		hsql.append(" group by a.seqNum,a.emsNo,a.transferFactoryInitBill.envelopNo,"
				+ "a.transferFactoryInitBill.scmCoc.name ");
		return this.find(hsql.toString(), parameters.toArray());
	}

	/**
	 * 查找生成报关清单的中间信息来自报关清单归并前的商品信息
	 */
	public List findMakeApplyToCustomsInfoByAfterCommInfoId(
			String mergeAfterCommInfoId) {
		return this.find(
				"select m from MakeApplyToCustomsInfo m where m.company.id = ? "
						+ " and m.mergeAfterCommInfoId = ?   ",
				new Object[] { CommonUtils.getCompany().getId(),
						mergeAfterCommInfoId });
	}

	/**
	 * 保存生成报关清单的中间信息
	 */
	public void saveMakeApplyToCustomsInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeApplyToCustomsInfo m = (MakeApplyToCustomsInfo) list.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除生成报关清单的中间信息
	 */
	public void deleteMakeApplyToCustomsInfo(MakeApplyToCustomsInfo m) {
		this.delete(m);
	}

	/**
	 * 获得关封分析数据来自hsql
	 */
	public List findCustomsEnvelopSpareAnalyes(String hsql, Object[] parameters) {
		return this.find(hsql, parameters);
	}

	/***************************************************************************
	 * 查询商品的已转厂数量
	 * 
	 * @param isImport
	 * @param customsName
	 * @param seqNum
	 * @param complex
	 * @return
	 */
	public double getCustomsEnvelopTransferQuantity(boolean isImport,
			String customsName, Integer seqNum, Complex complex,
			String customsEnvelopNo) {
		String hql = "select sum(a.commAmount) from " + customsName
				+ " a where a.company.id = ? "
				+ " and a.commSerialNo = ? and a.complex=? "
				+ " and a.baseCustomsDeclaration.impExpType =? "
				+ " and a.baseCustomsDeclaration.effective =? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(complex);
		if (isImport) {
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		} else {
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		parameters.add(true);
		parameters.add(customsEnvelopNo);
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查找由关封申请单生成关封单据的中间表来自关封单据商品信息
	 */
	public List findMakeCustomsEnvelopByCustomsEnvelopCommodityInfo(
			String customsEnvelopCommodityInfoId) {
		return this.find(
				"select m from MakeCustomsEnvelop m where m.company.id = ? "
						+ " and m.ceCommodityInfo.id = ?   ", new Object[] {
						CommonUtils.getCompany().getId(),
						customsEnvelopCommodityInfoId });
	}

	/**
	 * 保存由关封申请单生成关封单据的中间表
	 */
	public void saveMakeCustomsEnvelop(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeCustomsEnvelop m = (MakeCustomsEnvelop) list.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除由关封申请单生成关封单据的中间表
	 */
	public void deleteMakeCustomsEnvelop(MakeCustomsEnvelop m) {
		this.delete(m);
	}

	/**
	 * 查找由转厂单据生成关封申请单的中间表,来自关封申请单据商品信息
	 */
	public List findMakeCustomsEnvelopRequestByCustomsEnvelopRequestCommodityInfo(
			String customsEnvelopRequestCommodityInfoId) {
		return this.find(
				"select m from MakeCustomsEnvelopRequest m where m.company.id = ? "
						+ " and m.cerCommodityInfo.id = ?   ", new Object[] {
						CommonUtils.getCompany().getId(),
						customsEnvelopRequestCommodityInfoId });
	}

	/**
	 * 保存由转厂单据生成关封申请单的中间表
	 */
	public void saveMakeCustomsEnvelopRequest(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeCustomsEnvelopRequest m = (MakeCustomsEnvelopRequest) list
					.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除由转厂单据生成关封申请单的中间表
	 */
	public void deleteMakeCustomsEnvelopRequest(MakeCustomsEnvelopRequest m) {
		this.delete(m);
	}

	/**
	 * 有数据转报关清单在关封中
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasDataTransFactoryBillByEnvelopId(CustomsEnvelopBill c) {
		List list = this.find(
				"select count(*) from TransferFactoryBill a where a.company.id = ? "
						+ " and a.envelopNo = ?   ",
				new Object[] { CommonUtils.getCompany().getId(),
						c.getCustomsEnvelopBillNo() });
		if (list.size() <= 0 || list.get(0) == null) {
			return false;
		}
		if (Integer.parseInt(list.get(0).toString()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 有数据已转关封在关封申请单中
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(
			CustomsEnvelopRequestBill c) {
		List list = this
				.find("select count(*) from MakeCustomsEnvelop m where m.company.id = ? "
						+ " and m.cerCommodityInfo.customsEnvelopRequestBill.id = ?   ",
						new Object[] { CommonUtils.getCompany().getId(),
								c.getId() });
		if (list.size() <= 0 || list.get(0) == null) {
			return false;
		}
		if (Integer.parseInt(list.get(0).toString()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 有数据已转关封申请单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	public boolean hasDataCustomsDeclarationByTransFactBill(
			TransferFactoryBill t) {
		List list = this.find(
				"select count(*) from MakeCustomsDeclaration m where m.company.id = ? "
						+ " and m.transFactBillId.id = ?   ", new Object[] {
						CommonUtils.getCompany().getId(), t.getId() });
		if (list.size() <= 0 || list.get(0) == null) {
			return false;
		}
		if (Integer.parseInt(list.get(0).toString()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 有数据已报关清单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	public boolean hasDataTransferApplyToCustomsBillByTransferFactoryBill(
			TransferFactoryBill t) {
		List list = this.find(
				"select count(distinct m.id) from MakeApplyToCustomsInfo m,"
						+ " TransferFactoryCommodityInfo n where "
						+ " m.transFactCommInfo=n.id and m.company.id = ? "
						+ " and n.transferFactoryBill.id = ? ", new Object[] {
						CommonUtils.getCompany().getId(), t.getId() });
		if (list.size() <= 0 || list.get(0) == null) {
			return false;
		}
		if (Integer.parseInt(list.get(0).toString()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查找由海关工厂单据生成转厂单据的中间表,来自转厂单据商品信息
	 */
	public List findMakeTransferFactoryBillByTransferFactoryInfoId(
			String transferFactoryInfoId) {
		return this.find(
				"select m from MakeTransferFactoryBill m where m.company.id = ? "
						+ " and m.transFactBillId = ?   ",
				new Object[] { CommonUtils.getCompany().getId(),
						transferFactoryInfoId });
	}

	/**
	 * 保存由海关工厂单据生成转厂单据的中间表
	 */
	public void saveMakeTransferFactoryBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeTransferFactoryBill m = (MakeTransferFactoryBill) list.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除由海关工厂单据生成转厂单据的中间表
	 */
	public void deleteMakeTransferFactoryBill(MakeTransferFactoryBill m) {
		this.delete(m);
	}

	/**
	 * 查找结转单据生成报关清单时的中间表信息来自关封相关的数据项
	 * 
	 * @param c
	 * @return
	 */
	public List findMakeApplyToCustomsInfoByCustomsEnvelopBill(
			CustomsEnvelopBill c) {
		return this
				.find("select m from MakeApplyToCustomsInfo m "
						+ "	where m.customsEnvelopCommodityInfo.id in "
						+ " (select e.id from CustomsEnvelopCommodityInfo e where e.customsEnvelopBill.id = ?)",
						new Object[] { c });
	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public List findExportCustomsDeclaration(int projectType
	// Date beginDate,
	// Date endDate
	) {
		String hql = "select a.baseCustomsDeclaration.id,a.baseCustomsDeclaration.customsDeclarationCode,"
				+ "sum(a.commTotalPrice) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		default:
			hql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		// a.baseCustomsDeclaration.impExpFlag=? and
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and (a.baseCustomsDeclaration.customsEnvelopBillNo is null or a.baseCustomsDeclaration.customsEnvelopBillNo=?) "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode not in"
				+ " (select distinct b.customsDeclaredCode from customsEnvelopBill as b where b.company.id=? "
				// + " and b.customsDeclaredCode is not null and b.state!=? ) "
				+ " and a.baseCustomsDeclaration.declarationDate>=? and a.baseCustomsDeclaration.declarationDate<=? "
				+ " group by a.baseCustomsDeclaration.id,a.baseCustomsDeclaration.customsDeclarationCode ";
		return this.find(hql, new Object[] {
				// Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId(), "",
				CommonUtils.getCompany().getId(),
		// InvoiceState.CANCELED,
		// beginDate, endDate
				});
	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public List findImportCustomsDeclaration(int projectType
	// Date beginDate,
	// Date endDate
	) {
		String hql = "select a.baseCustomsDeclaration.id,a.baseCustomsDeclaration.customsDeclarationCode,"
				+ "sum(a.commTotalPrice) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		default:
			hql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		// a.baseCustomsDeclaration.impExpFlag=? and
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and (a.baseCustomsDeclaration.customsEnvelopBillNo is null or a.baseCustomsDeclaration.customsEnvelopBillNo=?) "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode not in"
				+ " (select distinct b.customsDeclaredCode from customsEnvelopBill as b where b.company.id=? "
				// + " and b.customsDeclaredCode is not null and b.state!=? ) "
				+ " and a.baseCustomsDeclaration.declarationDate>=? and a.baseCustomsDeclaration.declarationDate<=? "
				+ " group by a.baseCustomsDeclaration.id,a.baseCustomsDeclaration.customsDeclarationCode ";
		return this.find(hql, new Object[] {
				// Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId(), "",
				CommonUtils.getCompany().getId(),
		// InvoiceState.CANCELED,
		// beginDate, endDate
				});
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findAllCustomsEnvelopCommodityInfo(Date beginDate) {
		return this
				.find("select b from CustomsEnvelopCommodityInfo b  "
						+ "where b.company.id = ? and b.customsEnvelopBill.beginAvailability>=? order by b.customsEnvelopBill.scmCoc.code,b.customsEnvelopBill.beginAvailability",
						new Object[] { CommonUtils.getCompany().getId(),
								beginDate });
	}

	/**
	 * 查询转厂中关封的客户和厂商
	 * 
	 * @return
	 */
	public List findCustomsEnvelopScmCoc(boolean isImport) {
		return this.find("select distinct b.scmCoc from CustomsEnvelopBill b  "
				+ "where b.company.id = ? "
				+ " and b.isImpExpGoods=? order by b.scmCoc.code",
				new Object[] { CommonUtils.getCompany().getId(), isImport });
	}

	/**
	 * 查询转厂中关封的商品信息
	 * 
	 * @return
	 */
	public List findCustomsEnvelopComplex(boolean isImport, boolean isSeqNum) {
		if (isSeqNum) {
			return this.find(
					"select distinct b.seqNum,b.complex.code,b.ptName,b.ptSpec"
							+ " from CustomsEnvelopCommodityInfo b  "
							+ " where b.customsEnvelopBill.company.id = ? "
							+ " and b.customsEnvelopBill.isImpExpGoods=?"
							+ " order by b.complex.code", new Object[] {
							CommonUtils.getCompany().getId(), isImport });
		} else {
			return this.find(
					"select distinct b.complex.code,b.ptName,b.ptSpec "
							+ " from CustomsEnvelopCommodityInfo b  "
							+ " where b.customsEnvelopBill.company.id = ? "
							+ " and b.customsEnvelopBill.isImpExpGoods=?"
							+ " order by b.complex.code", new Object[] {
							CommonUtils.getCompany().getId(), isImport });
		}
	}

	/**
	 * 关封明细报表
	 * 
	 * @param envelopCode
	 * @param complexCode
	 * @param scmCocCode
	 * @param state
	 * @return
	 */
	public List findCustomsEnvelopList(String envelopCode, Integer seqNum,
			String complexCode, String scmCocCode, boolean isImport, int state,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select b from CustomsEnvelopCommodityInfo b  "
				+ " where b.customsEnvelopBill.company.id = ? "
				+ " and b.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(isImport);
		if (envelopCode != null && !"".equals(envelopCode)) {
			hql += " and b.customsEnvelopBill.customsEnvelopBillNo like ? ";
			parameters.add("%" + envelopCode + "%");
		}
		if (scmCocCode != null && !"".equals(scmCocCode)) {
			hql += " and b.customsEnvelopBill.scmCoc.code=? ";
			parameters.add(scmCocCode);
		}
		if (seqNum != null) {
			hql += " and b.seqNum=? ";
			parameters.add(seqNum);
		}
		if (complexCode != null && !"".equals(complexCode)) {
			hql += " and b.complex.code=? ";
			parameters.add(complexCode);
		}
		if (state == 0) {
			hql += " and b.customsEnvelopBill.isAvailability=? ";
			parameters.add(false);
		} else if (state == 1) {
			hql += " and b.customsEnvelopBill.isAvailability=? ";
			parameters.add(true);
		}
		if (beginDate != null) {

			hql += " and b.customsEnvelopBill.beginAvailability>=?  ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hql += " and b.customsEnvelopBill.beginAvailability<=?  ";
			parameters.add(endDate);

		}

		hql += " order by b.customsEnvelopBill.scmCoc.code,b.customsEnvelopBill.beginAvailability ";
		return this.find(hql, parameters.toArray());
	}

	public Double findPreviBalance(String ptName, String scmCocCode) {
		List list = this
				.find("select a.noTransferQuantity from TransferFactoryInitBillCommodityInfo a "
						+ " where a.commName = ? and  a.transferFactoryInitBill.scmCoc.code = ? "
						+ " and a.company.id = ? and a.transferFactoryInitBill.isEffective = ?",
						new Object[] { ptName, scmCocCode,
								CommonUtils.getCompany().getId(),
								new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double findBgdZcNum(String complex, String ptName, String scmCocCode) {
		List list = this
				.find("select a.commAmount from CustomsDeclarationCommInfo a where a.complex.code = ? and "
						+ "a.commName = ? and a.company.id = ? and a.baseCustomsDeclaration.impExpType=? and a.baseCustomsDeclaration.customer.code = ?"
						+ " and a.baseCustomsDeclaration.effective = ? ",
						new Object[] { complex, ptName,
								CommonUtils.getCompany().getId(),
								ImpExpType.TRANSFER_FACTORY_IMPORT, scmCocCode,
								new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double findThisCheckNum(Date beginDate, Date endDate,
			String scmcocCode, String complex, String ptName) {
		List list = this.find(
				"select sum(a.transFactAmount) from TransferFactoryCommodityInfo a "
						+ " where a.company.id = ? "
						+ " and a.transferFactoryBill.beginAvailability >=? "
						+ " and a.transferFactoryBill.beginAvailability <= ? "
						+ " and a.transferFactoryBill.scmCoc.code = ? "
						+ " and a.transferFactoryBill.isAvailability = ? ",
				new Object[] { CommonUtils.getCompany().getId(), beginDate,
						endDate, scmcocCode, new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double findThisCheckNum(Date beginDate, Date endDate,
			String scmcocCode, CustomsEnvelopCommodityInfo obj) {
		List list = this
				.find("select sum(a.transFactAmount) from TransferFactoryCommodityInfo a "
						+ " where a.company.id = ? "
						+ " and a.transferFactoryBill.beginAvailability >=? "
						+ " and a.transferFactoryBill.beginAvailability <= ? "
						+ " and a.transferFactoryBill.scmCoc.code = ? "
						+ " and a.transferFactoryBill.isAvailability = ? "
						+ " and a.emsNo=? "
						+ " and a.seqNum=? "
						+ " and a.complex.code=? and a.commName=? and a.commSpec=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								beginDate, endDate, scmcocCode,
								new Boolean(true), obj.getEmsNo(),
								obj.getSeqNum(), obj.getComplex().getCode(),
								obj.getPtName(), obj.getPtSpec() });
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 
	 * @param emsNo
	 * @param scmcocCode
	 * @param beginDate
	 * @param endDate
	 * @param spec
	 * @param hsName
	 * @param impExpType
	 * @return
	 */
	public List findTransferFactoryCommodityInfoGroupBySeqNum(String emsNo,
			String scmcocCode, Date beginDate, Date endDate, String spec,
			String hsName, Integer impExpType) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select a.transferFactoryBill.beginAvailability,"
				+ " a.transferFactoryBill.transferFactoryBillNo,"
				+ " a.transferFactoryBill.memo,a.transferFactoryBill.envelopNo,"
				+ " a.seqNum,a.commName,sum(a.transFactAmount),a.commSpec"
				+ " from TransferFactoryCommodityInfo a where a.company.id = ?";
		para.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and a.emsNo=?";
			para.add(emsNo);
		}
		if (scmcocCode != null && !"".equals(scmcocCode)) {
			hsql += " and a.transferFactoryBill.scmCoc.code = ? ";
			para.add(scmcocCode);
		}
		if (beginDate != null) {
			hsql += " and a.transferFactoryBill.beginAvailability >=?";
			para.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.transferFactoryBill.beginAvailability <=?";
			para.add(CommonUtils.getEndDate(endDate));
		}

		if (spec != null && !"".equals(spec)) {
			hsql += " and a.commSpec = ?";
			para.add(spec);
		}

		if (hsName != null && !"".equals(hsName)) {
			hsql += " and a.commName = ?";
			para.add(hsName);
		}

		if (impExpType != null) {
			hsql += " and a.transferFactoryBill.isImpExpGoods = ?";
			if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				para.add(true);
			} else {
				para.add(false);
			}
		}

		hsql += " group by a.transferFactoryBill.beginAvailability,"
				+ "a.transferFactoryBill.transferFactoryBillNo,"
				+ "a.transferFactoryBill.memo,a.transferFactoryBill.envelopNo,"
				+ "a.seqNum,a.commName,a.commSpec ";
		hsql += " order by a.seqNum";
		return this.find(hsql, para.toArray());

	}

	/**
	 * 抓取已送货数量
	 * 
	 * @param scmcocCode
	 * @param beginDate
	 * @param impExpType
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public Double findTransferFactoryCommodityInfoBeginGroupBySeqNum(
			String scmcocCode, Date beginDate, Integer impExpType,
			String emsNo, String envelopNo, Integer seqNum, String spec,
			String hsName) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select sum(a.transFactAmount) from TransferFactoryCommodityInfo a"
				+ " where a.company.id = ?"
				+ " and a.transferFactoryBill.envelopNo=?"
				+ " and a.seqNum = ? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(envelopNo);
		para.add(seqNum);
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and a.emsNo=?";
			para.add(emsNo);
		}
		if (scmcocCode != null && !"".equals(scmcocCode)) {
			hsql += " and a.transferFactoryBill.scmCoc.code = ? ";
			para.add(scmcocCode);
		}
		if (beginDate != null) {
			hsql += " and a.transferFactoryBill.beginAvailability <?";
			para.add(CommonUtils.getBeginDate(beginDate));
		}
		if (impExpType != null) {
			hsql += " and a.transferFactoryBill.isImpExpGoods = ?";
			if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				para.add(true);
			} else {
				para.add(false);
			}
		}
		if (spec != null && !"".equals(spec)) {
			hsql += " and a.commSpec = ?";
			para.add(spec);
		}

		if (hsName != null && !"".equals(hsName)) {
			hsql += " and a.commName = ?";
			para.add(hsName);
		}
		List list = this.find(hsql, para.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 抓取期初单的已送货未转厂数量
	 * 
	 * @param scmcocCode
	 * @param endDate
	 *            Date beginDate,
	 * @param impExpType
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public Double findTransFactInitCommInfoBeginGroupBySeqNum(
			String scmcocCode, Date endDate, Integer impExpType, String emsNo,
			String envelopNo, Integer seqNum, String spec, String hsName) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select sum(a.noTransferQuantity) from TransferFactoryInitBillCommodityInfo a"
				+ " where a.company.id = ?"
				+ " and a.transferFactoryInitBill.envelopNo=?  and a.seqNum = ? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(envelopNo);
		para.add(seqNum);
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and a.emsNo=?";
			para.add(emsNo);
		}
		if (scmcocCode != null && !"".equals(scmcocCode)) {
			hsql += " and a.transferFactoryInitBill.scmCoc.code = ? ";
			para.add(scmcocCode);
		}
		if (endDate != null) {
			hsql += " and a.transferFactoryInitBill.effectiveDate <=?";
			para.add(CommonUtils.getEndDate(endDate));
		}
		if (impExpType != null) {
			hsql += " and a.transferFactoryInitBill.isImpExpFlag = ?";
			if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				para.add(true);
			} else {
				para.add(false);
			}
		}

		if (spec != null && !"".equals(spec)) {
			hsql += " and a.commSpec = ?";
			para.add(spec);
		}

		if (hsName != null && !"".equals(hsName)) {
			hsql += " and a.commName = ?";
			para.add(hsName);
		}

		List list = this.find(hsql, para.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double findCustomsDeclarationAmountByEnvelopNoAndSeqNum(
			Integer projectType, String emsNo, Integer seqNum,
			String scmcocCode, String envelopNo, Date beginDate, String spec,
			String hsName, Integer impExpType) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select sum(a.commAmount) ";
		switch (projectType) {
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql += " from DzscCustomsDeclarationCommInfo a ";
			break;
		// default:
		// return 0.0;
		}
		// hql += " from CustomsDeclarationCommInfo a ";
		hql += " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo =? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(envelopNo);
		if (emsNo != null && !"".equals(emsNo)) {
			hql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		// if (scmcocCode != null && !"".equals(scmcocCode)) {
		// hql += " and b.transferFactoryBill.scmCoc.code = ? ";
		// parameters.add(scmcocCode);
		// }
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate <? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}

		if (spec != null && !"".equals(spec)) {
			hql += " and a.commSpec = ?";
			parameters.add(spec);
		}

		if (hsName != null && !"".equals(hsName)) {
			hql += " and a.commName = ?";
			parameters.add(hsName);
		}

		// if (impExpType != null) {
		// hql += " and b.transferFactoryBill.isImpExpGoods = ?";
		// if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
		// parameters.add(true);
		// } else {
		// parameters.add(false);
		// }
		// }
		// hql += " )";

		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		} else {
			return Double.valueOf(0);
		}
		// List<Object> parameters = new ArrayList<Object>();
		// String hql = "select sum(a.commAmount) ";
		// switch (projectType) {
		// case ProjectType.BCS:
		// hql += " from BcsCustomsDeclarationCommInfo a ";
		// break;
		// case ProjectType.BCUS:
		// hql += " from CustomsDeclarationCommInfo a ";
		// break;
		// case ProjectType.DZSC:
		// hql += " from DzscCustomsDeclarationCommInfo a ";
		// break;
		// // default:
		// // return 0.0;
		// }
		// // hql += " from CustomsDeclarationCommInfo a ";
		// hql += " where a.baseCustomsDeclaration.company.id=? "
		// + " and a.baseCustomsDeclaration.emsHeadH2k=? "
		// + " and a.commSerialNo=? "
		// + " and a.baseCustomsDeclaration.customsEnvelopBillNo in"
		// + " (select distinct b.transferFactoryBill.envelopNo "
		// + " from TransferFactoryCommodityInfo b"
		// + " where b.company.id = ? and b.emsNo=? ";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(emsNo);
		// parameters.add(seqNum);
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(emsNo);
		// if (scmcocCode != null && !"".equals(scmcocCode)) {
		// hql += " and b.transferFactoryBill.scmCoc.code = ? ";
		// parameters.add(scmcocCode);
		// }
		// if (endDate != null) {
		// hql += " and b.transferFactoryBill.beginAvailability <?";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		// if (impExpType != null) {
		// hql += " and b.transferFactoryBill.isImpExpGoods = ?";
		// if (impExpType.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
		// parameters.add(true);
		// } else {
		// parameters.add(false);
		// }
		// }
		// hql += " )";
		//
		// List list = this.find(hql, parameters.toArray());
		// if (list.size() > 0 && list.get(0) != null) {
		// return Double.valueOf(list.get(0).toString());
		// } else {
		// return Double.valueOf(0);
		// }
	}

	public Double findThisCheckWeight(Date beginDate, Date endDate,
			String scmcocCode, String complex, String ptName) {
		List list = this
				.find("select sum(a.netWeight*a.transFactAmount) from TransferFactoryCommodityInfo a "
						+ " where a.company.id = ? and a.transferFactoryBill.beginAvailability >=? "
						+ " and a.transferFactoryBill.beginAvailability <= ? "
						+ " and a.transferFactoryBill.scmCoc.code = ? "
						+ " and a.transferFactoryBill.isAvailability = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								beginDate, endDate, scmcocCode,
								new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double findThisCheckWeight(Date beginDate, Date endDate,
			String scmcocCode, CustomsEnvelopCommodityInfo obj) {
		List list = this
				.find("select sum(a.netWeight) from TransferFactoryCommodityInfo a "
						+ " where a.company.id = ? and a.transferFactoryBill.beginAvailability >=? "
						+ " and a.transferFactoryBill.beginAvailability <= ? "
						+ " and a.transferFactoryBill.scmCoc.code = ? "
						+ " and a.transferFactoryBill.isAvailability = ?"
						+ " and a.emsNo=? "
						+ " and a.seqNum=? "
						+ " and a.complex.code=? and a.commName=? and a.commSpec=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								beginDate, endDate, scmcocCode,
								new Boolean(true), obj.getEmsNo(),
								obj.getSeqNum(), obj.getComplex().getCode(),
								obj.getPtName(), obj.getPtSpec() });
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 查找海关合同料件 来自 手册号(正在执行)
	 * 
	 * @param parentId
	 * @return
	 */
	public List findDzscEmsImgBillByEmsNo(String emsNo, String envelopId) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.emsNo = ? "
						+ " and a.dzscEmsPorHead.declareState=? "
						+ " and a.dzscEmsPorHead.company.id=? "
						// + " and a.seqNum not in (select e.seqNum "
						// + " from CustomsEnvelopCommodityInfo e"
						// + " where e.customsEnvelopBill.id =? "
						// + " and e.emsNo=? )"
						+ "order by a.seqNum ", new Object[] { emsNo,
						DzscState.EXECUTE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找海关合同成品 来自 手册号(正在执行)
	 * 
	 * @param parentId
	 * @return
	 */
	public List findDzscEmsExgBillByEmsNo(String emsNo, String envelopId) {
		return this.find(
				"select a from DzscEmsExgBill a where a.dzscEmsPorHead.emsNo = ? "
						+ " and a.dzscEmsPorHead.declareState=? "
						+ " and a.dzscEmsPorHead.company.id=? "
						// + " and a.seqNum not in (select e.seqNum "
						// + " from CustomsEnvelopCommodityInfo e"
						// + " where e.customsEnvelopBill.id =? "
						// + " and e.emsNo=? ) "
						+ " order by a.seqNum ", new Object[] { emsNo,
						DzscState.EXECUTE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同料件 来自 手册编号
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractImgByEmsNo(String emsNo, String envelopId) {

		return this
				.find("select a from ContractImg a where a.contract.emsNo = ? "
						+ " and ( a.contract.isCancel=? ) and a.contract.declareState=? "
						+ " and a.contract.company.id=? "
						// + " and a.seqNum not in (select e.seqNum "
						// + " from CustomsEnvelopCommodityInfo e "
						// + " where e.customsEnvelopBill.id =? "
						// + " and e.emsNo=? ) "
						+ "order by a.seqNum ", new Object[] { emsNo, false,
						DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找合同料件 来自 手册编号
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractExgByEmsNo(String emsNo, String envelopId) {
		return this
				.find("select a from ContractExg a where a.contract.emsNo = ? "
						+ " and ( a.contract.isCancel=? ) and a.contract.declareState=? "
						+ " and a.contract.company.id=? "
						// + " and a.seqNum not in (select e.seqNum "
						// + " from CustomsEnvelopCommodityInfo e "
						// + " where e.customsEnvelopBill.id =? "
						// + " and e.emsNo=? )"
						+ " order by a.seqNum ", new Object[] { emsNo, false,
						DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据关封的商品信息,抓取报关单的申报日期
	 * 
	 * @param projectType
	 * @param emsNo
	 * @param seqNum
	 * @param complexCode
	 * @return
	 */
	public Date findCustomsDeclarationDateByEnvelop(Integer projectType,
			String emsNo, Integer seqNum, String complexCode, String envelopCode) {
		if (projectType == null) {
			return null;
		}
		String hql = "select max(a.baseCustomsDeclaration.declarationDate)";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql += " from DzscCustomsDeclarationCommInfo a ";
			break;
		// default:
		// return null;
		}
		hql += " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? and a.complex.code=? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(complexCode);
		parameters.add(envelopCode);
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0) {
			return (Date) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据关封的商品信息,抓取报关单的数量
	 * 
	 * @param projectType
	 * @param emsNo
	 * @param seqNum
	 * @param complexCode
	 * @return
	 */
	public double findCustomsDeclarationAmountByEnvelop(Integer projectType,
			String emsNo, Integer seqNum, String complexCode, String envelopCode) {
		if (projectType == null) {
			return 0;
		}
		String hql = "select sum(a.commAmount) ";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql += " from DzscCustomsDeclarationCommInfo a ";
			break;
		// default:
		// return 0.0;
		}
		hql += " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? and a.complex.code=? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(complexCode);
		parameters.add(envelopCode);
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		} else {
			return 0.0;
		}
	}

	public Double findCustomsDeclarationAmountByEnvelopNoAndSeqNum(
			Integer projectType, String emsNo, Integer seqNum,
			String envelopCode, Date beginDate, Date endDate, String spec,
			String hsName) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select sum(a.commAmount) ";
		switch (projectType) {
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql += " from DzscCustomsDeclarationCommInfo a ";
			break;
		// default:
		// return 0.0;
		}
		// hql += " from CustomsDeclarationCommInfo a ";
		hql += " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(envelopCode);

		switch (projectType) {
		case ProjectType.BCS:
			if (emsNo != null && !"".equals(emsNo)) {
				hql += "  and a.baseCustomsDeclaration.emsHeadH2k=? ";
				parameters.add(emsNo);
			}
			break;
		case ProjectType.BCUS:
			hql += "  and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
			break;
		case ProjectType.DZSC:
			if (emsNo != null && !"".equals(emsNo)) {
				hql += "  and a.baseCustomsDeclaration.emsHeadH2k=? ";
				parameters.add(emsNo);
			}
			break;
		}
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate >=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate <=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		} else {
			return Double.valueOf(0);
		}
	}

	/**
	 * 根据单据号查询关封商品明细
	 * 
	 * @param customsEnvelopBillCode
	 * @return
	 */
	public List findTempTransferFactoryCommInfo(boolean isImport, ScmCoc scmCoc) {
		String hql = "select a from CustomsEnvelopCommodityInfo as a "
				+ "  left join a.customsEnvelopBill b "
				+ "	where b.isImpExpGoods=? "
				+ " and b.isAvailability=? and a.company.id=? "
				+ " and (b.isEndCase=? or b.isEndCase is null)";

		System.out.println(hql + " **** >HQL< ");

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(isImport);
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);
		if (scmCoc != null) {
			hql += " and b.scmCoc=? ";
			parameters.add(scmCoc);
		}
		return this.find(hql, parameters.toArray());
		// return this.find("select a from CustomsEnvelopCommodityInfo a "
		// + " where a.customsEnvelopBill.customsEnvelopBillNo = ? "
		// + " and a.company.id=? ", new Object[] {
		// customsEnvelopBillCode, CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据单据号查询关封商品明细
	 * 
	 * @param customsEnvelopBillCode
	 * @return
	 */
	public List findTransferFactoryCommInfoByEnvelpCode(
			String customsEnvelopBillCode) {
		return this.find("select a from CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.customsEnvelopBillNo = ? "
				+ " and a.company.id=? ", new Object[] {
				customsEnvelopBillCode, CommonUtils.getCompany().getId() });
	}

	// /**
	// * 查询不在转厂起初单的商品编码
	// *
	// * @param initBillId
	// * @return
	// */
	// public List findComplexNotInInitBill(String initBillId, ScmCoc scmCoc)
	// {dd
	// String hql = "select a from CustomsEnvelopCommodityInfo a where a.seqNum
	// not in "
	// + "(select b.seqNum from TransferFactoryInitBillCommodityInfo b "
	// + " where b.transferFactoryInitBill.id = ? )"
	// + " and a.customsEnvelopBill.isAvailability=? ";
	// List<Object> parameters = new ArrayList<Object>();
	// parameters.add(initBillId);
	// parameters.add(true);
	// if (scmCoc != null) {
	// hql += " and a.customsEnvelopBill.scmCoc.id=? ";
	// parameters.add(scmCoc.getId());
	// }
	// return this.find(hql, parameters.toArray());
	// }

	/**
	 * 取得报关单流水号
	 * 
	 * @return
	 */
	public Integer getCustomsDeclarationSerialNumber(int projectType,
			int impExpFlag, String emsHeadH2k) {
		Integer no = new Integer(1);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select max(a.serialNumber) from CustomsDeclaration as a ";
			break;
		case ProjectType.BCS:
			hql = " select max(a.serialNumber) from BcsCustomsDeclaration as a  ";
			break;
		case ProjectType.DZSC:
			hql = " select max(a.serialNumber) from DzscCustomsDeclaration as a  ";
			break;
		default:
			hql = " select max(a.serialNumber) from CustomsDeclaration as a  ";
			break;
		}
		hql += " where a.impExpFlag=? and a.emsHeadH2k = ?";
		List list = this.find(hql, new Object[] { new Integer(impExpFlag),
				emsHeadH2k });
		if (list.size() < 1 || list.get(0) == null
				|| "".equals(list.get(0).toString().trim())) {
			no = new Integer(1);

		} else {
			String temp = list.get(0).toString();
			int n = Integer.parseInt(temp) + 1;
			no = new Integer(n);
		}
		return no;
	}

	/**
	 * 取得报关单商品流水号
	 * 
	 * @return
	 */
	public Integer getCustomsDeclarationCommInfoSerialNumber(int projectType,
			String customsDeclarationId) {
		Integer no = new Integer(1);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select max(a.serialNumber) from CustomsDeclarationCommInfo as a ";
			break;
		case ProjectType.BCS:
			hql = " select max(a.serialNumber) from BcsCustomsDeclarationCommInfo as a  ";
			break;
		case ProjectType.DZSC:
			hql = " select max(a.serialNumber) from DzscCustomsDeclarationCommInfo as a  ";
			break;
		default:
			hql = " select max(a.serialNumber) from CustomsDeclarationCommInfo as a  ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?";
		List list = this.find(hql, new Object[] { customsDeclarationId });
		if (list.size() < 1) {
			no = new Integer(1);
		} else if (list.get(0) == null) {
			no = new Integer(1);
		} else {
			String temp = list.get(0).toString();
			if (temp.trim().equals("")) {
				no = new Integer(1);
			} else {
				int n = Integer.parseInt(temp) + 1;
				no = new Integer(n);
			}
		}
		return no;
	}

	/**
	 * 查询未转报关单的结转单据
	 * 
	 * @param isImport
	 * @return
	 */
	public List findTransFactBillMakeCustomsDeclaration(boolean isImport,
			ScmCoc scmCoc) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from TransferFactoryBill a where a.isCustomsDeclaration=? "
				+ " and a.isApplyToCustomsBill=? "
				+ " and a.isImpExpGoods=? "
				+ " and a.isAvailability=? " + "and a.company.id=?";
		parameters.add(false);
		parameters.add(false);
		parameters.add(isImport);
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		if (scmCoc != null) {
			hql += " and scmCoc=? ";
			parameters.add(scmCoc);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据联网监管并后查询归并前的数据
	 * 
	 * @param tenSeqNum
	 * @param complex
	 * @return
	 */
	public List findBcusBeforeMaterialByAfter(Integer tenSeqNum, String complex) {
		return this.find(
				"select a from InnerMergeData a where a.hsAfterTenMemoNo=? "
						+ " and a.hsAfterComplex.code=? and a.company.id=?",
				new Object[] { false, DzscState.EXECUTE, tenSeqNum, complex,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据电子手册归并后查询归并前的数据
	 * 
	 * @param tenSeqNum
	 * @param complex
	 * @return
	 */
	public List findDzscBeforeMaterialByAfter(Integer tenSeqNum, String complex) {
		return this.find(
				"select a from DzscInnerMergeData a where a.isChange=? "
						+ " and a.stateMark=? and a.tenSeqNum=? "
						+ " and a.tenComplex.code=? and a.company.id=?",
				new Object[] { false, DzscState.EXECUTE, tenSeqNum, complex,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据电子手册归前后查询归并后的数据
	 * 
	 * @param tenSeqNum
	 * @param complex
	 * @return
	 */
	public DzscInnerMergeData findDzscMergeAfterByBefore(String ptNo) {
		List list = this.find(
				"select a from DzscInnerMergeData a where a.isChange=? "
						+ " and a.stateMark=? and a.materiel.ptNo=? "
						+ " and a.company.id=?", new Object[] { false,
						DzscState.EXECUTE, ptNo,
						CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (DzscInnerMergeData) list.get(0);
		}
		return null;
	}

	/**
	 * 根据电子手册归前后查询归并后的数据
	 * 
	 * @param tenSeqNum
	 * @param complex
	 * @return
	 */
	public BcsInnerMerge findBcsMergeAfterByBefore(String ptNo) {
		List list = this
				.find("select a from BcsInnerMerge a where a.materiel.ptNo=? "
						+ " and a.company.id=? and a.bcsTenInnerMerge is not null",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (BcsInnerMerge) list.get(0);
		}
		return null;
	}

	public List findContractImgByInnerMergeSeqNum(Integer innerMergeSeqNum,
			String emsNo, Integer produceType, boolean isContractEms) {
		List list = null;
		if (produceType == 2) {
			if (isContractEms) {
				list = this
						.find("select distinct a from ContractImg a ,BcsDictPorImg b "
								+ " where a.credenceNo=b.seqNum "
								+ " and a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo "
								+ " and b.innerMergeSeqNum=? "
								+ " and a.company.id=? and a.contract.emsNo=? "
								+ " and a.contract.declareState=? "
								+ " and b.dictPorHead.declareState=? ",
								new Object[] { innerMergeSeqNum,
										CommonUtils.getCompany().getId(),
										emsNo, DeclareState.PROCESS_EXE,
										DeclareState.PROCESS_EXE });
			} else {
				list = this.find(
						"select a from ContractImg a where a.credenceNo=? "
								+ " and a.company.id=? and a.contract.emsNo=? "
								+ " and a.contract.declareState=? ",
						new Object[] { innerMergeSeqNum,
								CommonUtils.getCompany().getId(), emsNo,
								DeclareState.PROCESS_EXE });
			}
		} else if (produceType == 0) {
			if (isContractEms) {
				list = this
						.find("select distinct a from ContractExg a ,BcsDictPorExg b "
								+ " where a.credenceNo=b.seqNum "
								+ " and a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo "
								+ " and b.innerMergeSeqNum=? "
								+ " and a.company.id=? and a.contract.emsNo=? "
								+ " and a.contract.declareState=? "
								+ " and b.dictPorHead.declareState=? ",
								new Object[] { innerMergeSeqNum,
										CommonUtils.getCompany().getId(),
										emsNo, DeclareState.PROCESS_EXE,
										DeclareState.PROCESS_EXE });
			} else {
				list = this.find(
						"select a from ContractExg a where a.credenceNo=? "
								+ " and a.company.id=? and a.contract.emsNo=? "
								+ " and a.contract.declareState=? ",
						new Object[] { innerMergeSeqNum,
								CommonUtils.getCompany().getId(), emsNo,
								DeclareState.PROCESS_EXE });
			}
		}
		return list;
	}

	public List findDzscImgExgByInnerMergeSeqNum(Integer innerMergeSeqNum,
			String emsNo, Integer produceType) {
		List list = null;
		if (produceType == 2) {
			list = this
					.find("select a from DzscEmsImgBill a where a.tenSeqNum=? "
							+ " and a.company.id=? and a.dzscEmsPorHead.emsNo=? "
							+ " and a.dzscEmsPorHead.declareState=? ",
							new Object[] { innerMergeSeqNum,
									CommonUtils.getCompany().getId(), emsNo,
									DzscState.EXECUTE });
		} else if (produceType == 0) {
			list = this
					.find("select a from DzscEmsExgBill a where a.tenSeqNum=? "
							+ " and a.company.id=? and a.dzscEmsPorHead.emsNo=? "
							+ " and a.dzscEmsPorHead.declareState=? ",
							new Object[] { innerMergeSeqNum,
									CommonUtils.getCompany().getId(), emsNo,
									DzscState.EXECUTE });
		}
		return list;
	}

	public List findCustomsEnvelopBillBySomCoc(ScmCoc scmcoc) {
		return this.find(
				" select a from CustomsEnvelopBill  a where a.scmCoc=?"
						+ " and a.company.id=? ", new Object[] { scmcoc,
						CommonUtils.getCompany().getId() });

	}

	public List findCustomsEnvelopCommodityInfoBySomCoc(
			CustomsEnvelopBill customsEnvelopBill, Boolean isCustomer) {
		Boolean tempisCustomer = !(isCustomer == null ? false : isCustomer
				.booleanValue());
		return this.find(" select a from CustomsEnvelopCommodityInfo  a "
				+ "where a.customsEnvelopBill=? and a.company.id=? "
				+ "and  a.customsEnvelopBill.isImpExpGoods=?", new Object[] {
				customsEnvelopBill, CommonUtils.getCompany().getId(),
				tempisCustomer });

	}

	public List findBillPriceMaintenanceByScmCoc(ScmCoc scmcoc,
			Boolean isCustomer) {
		return this.find(
				"select a from BillPriceMaintenance a where a.company.id=? "
						+ "and a.scmCoc=? and a.isCustomer = ? ", new Object[] {
						CommonUtils.getCompany().getId(), scmcoc, isCustomer });
	}

	public List findBillPriceMaintenanceByProjectType(int projectType) {
		return this.find(
				"select a from BillPriceMaintenance a where a.company.id=? "
						+ "and a.projectType=? ", new Object[] {
						CommonUtils.getCompany().getId(), projectType });
	}

	public List findCustomsEnvelopCommodityInfoBySomCocSeqNumCode(
			ScmCoc scmCoc, Integer seqNum, String code, Boolean isCustomer) {
		boolean isTempCustomer = !(isCustomer == null ? false : isCustomer
				.booleanValue());
		return this.find(" select a from CustomsEnvelopCommodityInfo  a "
				+ "where a.customsEnvelopBill.scmCoc=? "
				+ "and a.company.id=? "
				+ " and a.seqNum=? and a.complex.code=? "
				+ " and a.customsEnvelopBill.isEndCase=?"
				+ " and a.customsEnvelopBill.isImpExpGoods = ?", new Object[] {
				scmCoc, CommonUtils.getCompany().getId(), seqNum, code, false,
				isTempCustomer });
	}

	public List findTransferFactoryCommodityInfoByBillNo(
			String customsEnvelopBillNo, Integer seqNum, String code) {
		return this.find(" select a from TransferFactoryCommodityInfo a "
				+ "where a.transferFactoryBill.envelopNo=? "
				+ "and a.company.id=? and a.seqNum=? and a.complex.code =?",
				new Object[] { customsEnvelopBillNo,
						CommonUtils.getCompany().getId(), seqNum, code });
	}

	public List findCustomsEnvelopBillByNotEndCaseAndScmCoc(ScmCoc scmCoc,
			Boolean isCustomer) {
		boolean isTempCustomer = !(isCustomer == null ? false : isCustomer
				.booleanValue());
		return this.find(
				" select a.customsEnvelopBillNo from  CustomsEnvelopBill a"
						+ " where a.isEndCase=? and a.company.id=?  "
						+ "and a.scmCoc =? and a.isImpExpGoods=?",
				new Object[] { false, CommonUtils.getCompany().getId(), scmCoc,
						isTempCustomer });

	}

	public List findBcsCustomsDeclarationCommInfo(ScmCoc scmCoc,
			Integer seqNum, String code) {
		return this
				.find(" select a from BcsCustomsDeclarationCommInfo "
						+ " a where  a.company.id=? "
						+ " and a.commSerialNo=?  and a.complex.code=? "
						+ "and a.baseCustomsDeclaration.customer=? "
						+ "and a.baseCustomsDeclaration.impExpType in (?,?) "
						+ "and a.baseCustomsDeclaration.effective=? "
						+ " and a.baseCustomsDeclaration.customsEnvelopBillNo is null ",
						new Object[] { CommonUtils.getCompany().getId(),
								seqNum, code, scmCoc,
								ImpExpType.TRANSFER_FACTORY_EXPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, false });
	}

	public List findCustomsDeclarationCommInfo(ScmCoc scmCoc, Integer seqNum,
			String code) {
		return this
				.find(" select a from CustomsDeclarationCommInfo  a where  a.company.id=? "
						+ " and a.commSerialNo=?  and a.complex.code=? "
						+ "and a.baseCustomsDeclaration.customer=? "
						+ "and a.baseCustomsDeclaration.impExpType in (?,?) "
						+ "and a.baseCustomsDeclaration.effective=? "
						+ " and a.baseCustomsDeclaration.customsEnvelopBillNo is null ",
						new Object[] { CommonUtils.getCompany().getId(),
								seqNum, code, scmCoc,
								ImpExpType.TRANSFER_FACTORY_EXPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, false });
	}

	public List findDzscCustomsDeclarationCommInfo(ScmCoc scmCoc,
			Integer seqNum, String code) {
		return this
				.find(" select a from DzscCustomsDeclarationCommInfo  a where  a.company.id=? "
						+ " and a.commSerialNo=?  and a.complex.code=? "
						+ "and a.baseCustomsDeclaration.customer=? "
						+ "and a.baseCustomsDeclaration.impExpType in (?,?) "
						+ "and a.baseCustomsDeclaration.effective=?"
						+ " and a.baseCustomsDeclaration.customsEnvelopBillNo is null ",
						new Object[] { CommonUtils.getCompany().getId(),
								seqNum, code, scmCoc,
								ImpExpType.TRANSFER_FACTORY_EXPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, false });
	}

	public List findDistinctTrans(String year, Boolean isImpExpType) {
		String syear = String.valueOf(Integer.parseInt(year) - 1);// 上一年
		String xyear = String.valueOf(Integer.parseInt(year) + 1);// 下一年
		return this
				.find("select distinct a.seqNum,a.transferFactoryBill.scmCoc,a.memo from TransferFactoryCommodityInfo a where "
						+ " a.transferFactoryBill.beginAvailability >= '"
						+ syear
						+ "-01-01' and a.transferFactoryBill.beginAvailability < '"
						+ xyear
						+ "-01-01' and a.company.id = ? "
						+ " and a.transferFactoryBill.isAvailability = ?  and a.transferFactoryBill.isImpExpGoods=? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(true), isImpExpType });
	}

	public List getTransNum(String year, Boolean isImpExpType) {
		String syear = String.valueOf(Integer.parseInt(year) - 1);// 上一年
		String xyear = String.valueOf(Integer.parseInt(year) + 1);// 下一年
		return this
				.find("select distinct a.seqNum,a.transferFactoryBill.scmCoc,a.memo,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ syear
						+ "-01-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-01-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as beginNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-01-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-02-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as janNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-02-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-03-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as febNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-03-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-04-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as marNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-04-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-05-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as aprNum, "
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-05-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-06-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as mayNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-06-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-07-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as junNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-07-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-08-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as julNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-08-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-09-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as augNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-09-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-10-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as sepNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-10-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-11-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as octNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-11-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-12-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as movNum,"
						+ " (select sum(b.transFactAmount) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ "b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-12-01' and b.transferFactoryBill.beginAvailability < '"
						+ xyear
						+ "-01-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ? and b.transferFactoryBill.isImpExpGoods = ?) as decNum"
						+ " from TransferFactoryCommodityInfo a where a.transferFactoryBill.beginAvailability >= '"
						+ syear
						+ "-01-01' and a.transferFactoryBill.beginAvailability < '"
						+ xyear
						+ "-01-01' and a.transferFactoryBill.isAvailability = ? and a.company.id = ?  and a.transferFactoryBill.isImpExpGoods = ?",
						new Object[] { new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType });
	}

	public List getCustomsNum(String year, Boolean isImpExpType) {
		Integer ietype = 0;
		if (isImpExpType.equals(Boolean.valueOf(true))) {
			ietype = ImpExpType.TRANSFER_FACTORY_IMPORT;
		} else {
			ietype = ImpExpType.TRANSFER_FACTORY_EXPORT;
		}
		String syear = String.valueOf(Integer.parseInt(year) - 1);// 上一年
		String xyear = String.valueOf(Integer.parseInt(year) + 1);// 下一年
		return this
				.find("select distinct a.commSerialNo,a.baseCustomsDeclaration.customer,a.projectDept.name,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ syear
						+ "-01-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-01-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as beginNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-01-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-02-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as janNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-02-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-03-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as febNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-03-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-04-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as marNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-04-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-05-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as aprNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-05-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-06-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as mayNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-06-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-07-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?  and b.baseCustomsDeclaration.impExpType =?) as junNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-07-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-08-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as julNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-08-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-09-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as augNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-09-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-10-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as sepNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-10-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-11-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as octNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-11-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-12-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as movNum,"
						+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and b.projectDept = a.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-12-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ xyear
						+ "-01-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ?   and b.baseCustomsDeclaration.impExpType =?) as decNum  "
						+ " from CustomsDeclarationCommInfo a  where  a.baseCustomsDeclaration.declarationDate >= '"
						+ syear
						+ "-01-01' and a.baseCustomsDeclaration.declarationDate < '"
						+ xyear
						+ "-01-01' and a.baseCustomsDeclaration.effective = ? and a.company.id = ?   and a.baseCustomsDeclaration.impExpType =?",
						new Object[] { new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype });
	}

	public List getTransMoney(String year, Boolean isImpExpType) {
		String syear = String.valueOf(Integer.parseInt(year) - 1);// 上一年
		String xyear = String.valueOf(Integer.parseInt(year) + 1);// 下一年
		return this
				.find("select distinct a.seqNum,a.transferFactoryBill.scmCoc,a.memo,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ syear
						+ "-01-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-01-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as beginNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-01-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-02-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as janNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-02-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-03-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as febNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-03-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-04-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as marNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-04-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-05-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as aprNum, "
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-05-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-06-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as mayNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-06-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-07-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as junNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-07-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-08-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as julNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-08-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-09-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as augNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-09-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-10-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as sepNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-10-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-11-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as octNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-11-01' and b.transferFactoryBill.beginAvailability < '"
						+ year
						+ "-12-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as movNum,"
						+ " (select sum(b.transFactAmount * b.unitPrice * b.exchangeRate) from TransferFactoryCommodityInfo b where b.seqNum = a.seqNum and b.transferFactoryBill.scmCoc = a.transferFactoryBill.scmCoc and "
						+ " b.memo = a.memo and b.transferFactoryBill.beginAvailability >= '"
						+ year
						+ "-12-01' and b.transferFactoryBill.beginAvailability < '"
						+ xyear
						+ "-01-01' and b.transferFactoryBill.isAvailability = ? and b.company.id = ?  and b.transferFactoryBill.isImpExpGoods = ?) as decNum"
						+ " from TransferFactoryCommodityInfo a where a.transferFactoryBill.beginAvailability >= '"
						+ syear
						+ "-01-01' and a.transferFactoryBill.beginAvailability < '"
						+ xyear
						+ "-01-01' and a.transferFactoryBill.isAvailability = ? and a.company.id = ?  and a.transferFactoryBill.isImpExpGoods = ?",
						new Object[] { new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType,
								new Boolean(true),
								CommonUtils.getCompany().getId(), isImpExpType });
	}

	public List getCustomsMoney(String year, Boolean isImpExpType) {
		Integer ietype = 0;
		if (isImpExpType.equals(Boolean.valueOf(true))) {
			ietype = ImpExpType.TRANSFER_FACTORY_IMPORT;
		} else {
			ietype = ImpExpType.TRANSFER_FACTORY_EXPORT;
		}
		String syear = String.valueOf(Integer.parseInt(year) - 1);// 上一年
		String xyear = String.valueOf(Integer.parseInt(year) + 1);// 下一年
		return this
				.find("select distinct a.commSerialNo,a.baseCustomsDeclaration.customer,a.projectDept.name,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ syear
						+ "-01-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-01-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as beginNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-01-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-02-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as janNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-02-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-03-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as febNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-03-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-04-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as marNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-04-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-05-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as aprNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-05-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-06-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as mayNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-06-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-07-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as junNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-07-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-08-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as julNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-08-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-09-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as augNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-09-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-10-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as sepNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-10-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-11-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as octNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-11-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ year
						+ "-12-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as movNum,"
						+ " (select sum(b.dollarTotalPrice) from CustomsDeclarationCommInfo b where b.commSerialNo = a.commSerialNo and b.baseCustomsDeclaration.customer = a.baseCustomsDeclaration.customer "
						+ "and a.projectDept = b.projectDept and b.baseCustomsDeclaration.declarationDate >= '"
						+ year
						+ "-12-01' and b.baseCustomsDeclaration.declarationDate < '"
						+ xyear
						+ "-01-01' and b.baseCustomsDeclaration.effective = ? and b.company.id = ? and b.baseCustomsDeclaration.impExpType =?) as decNum  "
						+ " from CustomsDeclarationCommInfo a where a.baseCustomsDeclaration.declarationDate >= '"
						+ syear
						+ "-01-01' and a.baseCustomsDeclaration.declarationDate < '"
						+ xyear
						+ "-01-01' and a.baseCustomsDeclaration.effective = ? and a.company.id = ? and a.baseCustomsDeclaration.impExpType =?",
						new Object[] { new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype,
								new Boolean(true),
								CommonUtils.getCompany().getId(), ietype });
	}

	public List findEmsHeadH2kExg() {
		String classname = "";
		List list = null;
		if (getIsEmsH2kSend()) {
			classname = "EmsHeadH2kExg";
			list = this.find(
					" select a from " + classname + " as a "
							+ " where a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ " and a.sendState = ? " + "  order by a.seqNum",
					new Object[] { new Boolean(false),
							CommonUtils.getCompany().getId(),
							Integer.valueOf(SendState.SEND) });
		} else {
			classname = "EmsHeadH2kExg";
			list = this.find(
					" select a from " + classname + " as a "
							+ " where a.emsHeadH2k.declareState=? "
							+ " and a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ "  order by a.seqNum", new Object[] {
							DeclareState.PROCESS_EXE, new Boolean(false),
							CommonUtils.getCompany().getId() });
		}
		return list;
	}

	public List findEmsHeadH2kImg() {
		String classname = "";
		List list = null;
		if (getIsEmsH2kSend()) {
			classname = "EmsHeadH2kImg";
			list = this.find(
					" select a from " + classname + " as a "
							+ " where a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ " and a.sendState = ? " + "  order by a.seqNum",
					new Object[] { new Boolean(false),
							CommonUtils.getCompany().getId(),
							Integer.valueOf(SendState.SEND) });
		} else {
			classname = "EmsHeadH2kImg";
			list = this.find(
					" select a from " + classname + " as a "
							+ " where a.emsHeadH2k.declareState=? "
							+ " and a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ "  order by a.seqNum", new Object[] {
							DeclareState.PROCESS_EXE, new Boolean(false),
							CommonUtils.getCompany().getId() });
		}
		return list;
	}

	/**
	 * 根据关封号抓取关封里所有的手册号
	 * 
	 * @param envelopNo
	 * @return
	 */
	public List findCustomsEnvelopBillEmsNo(String envelopNo) {
		return this.find(
				" select distinct a.emsNo from CustomsEnvelopCommodityInfo  a "
						+ "where a.customsEnvelopBill.customsEnvelopBillNo=? "
						+ "and a.company.id=? order by a.emsNo ", new Object[] {
						envelopNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 
	 * 
	 */
	public List findImpExpCommodityInfo(String parentId) {
		return this
				.find("select b from TransferFactoryCommodityInfo b where b.transferFactoryBill.id = ? ",
						new Object[] { parentId });
	}

	/**
	 * 判断单据号是否已经存在
	 * 
	 * @param request
	 * @param no
	 * @return
	 */
	public boolean isExistsNo(String no) {
		// TODO Auto-generated method stub
		List list = this
				.find("from TransferFactoryBill a where a.company.id=? and a.transferFactoryBillNo=?",
						new Object[] { CommonUtils.getCompany().getId(), no });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得开始日期
	 * 
	 * @param projectType
	 * @param emsNo
	 * @return
	 */
	public List getContractOrEmsHeakOrDzscEmsPorHead(int projectType,
			String emsNo) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from EmsHeadH2k as a ";
			break;
		case ProjectType.BCS:
			hql = " select a from Contract as a  ";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscEmsPorHead as a  ";
			break;
		default:
			hql = " select a from Contract as a ";
			break;
		}
		hql += " where a.emsNo=? ";
		List list = this.find(hql, new Object[] { emsNo });
		return list;
	}

	/**
	 * 查找其他参数设置资料
	 * 
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	public List findCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		while (list.size() <= 0) {
			CompanyOther companyOther = new CompanyOther();
			companyOther.setCompany(CommonUtils.getCompany());
			this.getHibernateTemplate().saveOrUpdate(companyOther);
			System.out.println("--------------1:" + companyOther.getOptLock());
			list = this.find(
					"select a from CompanyOther a where a.company.id=?",
					new Object[] { CommonUtils.getCompany().getId() });
		}
		return list;
	}
}