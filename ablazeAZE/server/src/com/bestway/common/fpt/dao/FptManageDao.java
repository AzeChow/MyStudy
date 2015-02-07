package com.bestway.common.fpt.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoBase;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SendState;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptCancelBill;
import com.bestway.common.fpt.entity.FptDownData;
import com.bestway.common.fpt.entity.FptEmail;
import com.bestway.common.fpt.entity.FptEmailParamver;
import com.bestway.common.fpt.entity.FptInitBill;
import com.bestway.common.fpt.entity.FptInitBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.MakeFptBillCustomsDeclaration;
import com.bestway.common.fpt.entity.MakeFptBillFromCasBill;
import com.bestway.common.fpt.entity.TempCustomsEnvelopRequetBill;
import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;

public class FptManageDao extends BaseDao {
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
	 * 判断是否存在
	 * 
	 * @param appNo
	 * @param seqNo
	 * @return
	 */
	public List<FptAppHead> importFptAppisExists(String appNo, String seqNo,
			String ioFlag) {
		String hqlString = "SELECT a " + "FROM FptAppHead a "
				+ "WHERE a.company.id= ? " + "AND a.appNo = ? "
				+ "AND a.seqNo = ? " + "AND a.declareState = ?"
				+ "AND a.inOutFlag = ? ";
		return this.find(hqlString, new Object[] {
				CommonUtils.getCompany().getId(), appNo, seqNo,
				DeclareState.PROCESS_EXE, ioFlag });
	}

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	public FptParameterSet findFptParameterSet() {
		List list = this.find(
				"select a from FptParameterSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (FptParameterSet) list.get(0);
		} else {
			FptParameterSet parameterSet = new FptParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 获得关封申请单所有数据
	 */

	public List findFptAppHead() {
		return this
				.find("select a from FptAppHead as a where a.company.id=? order by a.inOutFlag",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 获得申请单所有数据
	 */
	public List<FptAppHead> findFptAppHeadByNotExceute(FptAppHead outHead) {
		return this.find("select a from FptAppHead as a where a.company.id=? "
				+
				// " and a.inOutFlag = ? and a.declareState not in (?,?) and
				// a.id != ? " +
				" and  a.declareState not in (?,?) and a.id != ? and a.inOutFlag = ? "
				+ "order by a.inOutFlag",
				new Object[] { CommonUtils.getCompany().getId(),
						DeclareState.PROCESS_EXE, DeclareState.WAIT_EAA,
						outHead.getId(), outHead.getInOutFlag() });
	}

	/**
	 * 获得关封申请单
	 */
	public FptAppHead findFptAppHeadById(Object id) {
		List list = this
				.find("select a from FptAppHead as a where a.company.id=? and a.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(), id });
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByOutCopAppNo(String outCopAppNo,
			String declareState) {
		List list = this.find(
				"select a from FptAppHead a where a.company= ? and "
						+ " a.outCopAppNo=?  and a.declareState=? ",
				new Object[] { CommonUtils.getCompany(), outCopAppNo,
						declareState });
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByInCopAppNo(String inCopAppNo,
			String declareState) {
		List list = this.find(
				"select a from FptAppHead a where a.company= ? and "
						+ " a.inCopAppNo=?  and a.declareState=? ",
				new Object[] { CommonUtils.getCompany(), inCopAppNo,
						declareState });
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByAppNo(String appNo, String declareState) {
		List list = this.find(
				"select a from FptAppHead a where a.company= ? and "
						+ " a.appNo=?  and a.declareState=? ", new Object[] {
						CommonUtils.getCompany(), appNo, declareState });
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/** 获得申请表标号下的所有申请单 */
	public FptAppHead findAllFptAppHeadByAppNo(String appNo) {
		List list = this.find(
				"select a from FptAppHead a where a.company= ? and "
						+ " a.appNo=? ",
				new Object[] { CommonUtils.getCompany(), appNo });
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 该帐册编号的合同是否存在
	 * 
	 * @param fptAppHead
	 *            合同备案表头
	 * @return boolean 帐册编号的合同是否存在，存在为true，否则为false
	 */
	public boolean isExistFptAppHeadByOutCopAppNo(FptAppHead fptAppHead) {
		if ("".equals(fptAppHead.getOutCopAppNo())) {
			return false;
		}
		List list = new ArrayList();
		if (fptAppHead.getId() == null || fptAppHead.getId().equals("")) {
			list = this
					.find("select a from FptAppHead a where a.company= ? and a.outCopAppNo=?   ",
							new Object[] { CommonUtils.getCompany(),
									fptAppHead.getOutCopAppNo() });
		} else {
			list = this.find(
					"select a from FptAppHead a where a.company= ? and a.outCopAppNo=? "
							+ " and a.id<>? ",
					new Object[] { CommonUtils.getCompany(),
							fptAppHead.getOutCopAppNo(), fptAppHead.getId() });
		}
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查找 FptAppHead 来自 outCopAppNo
	 */
	public FptAppHead findFptAppHeadByOutCopAppNo(String outCopAppNo) {
		List list = this.find("select a from FptAppHead as a "
				+ "	where a.outCopAppNo=? and a.company.id=? "
				+ "and a.declareState = ? order by a.inOutFlag ", new Object[] {
				outCopAppNo, CommonUtils.getCompany().getId(),
				DeclareState.PROCESS_EXE });
		if (!list.isEmpty()) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找 FptAppHead 来自 统一编号
	 */
	public FptAppHead findFptAppHeadBySeqNo(String seqNo) {
		List list = this.find("select a from FptAppHead as a "
				+ "	where a.seqNo=? and a.company.id=? "
				+ " and a.declareState = ? order by a.inOutFlag ",
				new Object[] { seqNo, CommonUtils.getCompany().getId(),
						DeclareState.PROCESS_EXE });
		if (!list.isEmpty()) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找 FptAppHead 来自 inCopAppNo
	 */
	public FptAppHead findFptAppHeadByInCopAppNo(String inCopAppNo) {
		List list = this.find("select a from FptAppHead as a "
				+ "	where a.inCopAppNo=? and a.company.id=? "
				+ "and a.declareState = ? order by a.inOutFlag ", new Object[] {
				inCopAppNo, CommonUtils.getCompany().getId(),
				DeclareState.PROCESS_EXE });
		if (!list.isEmpty()) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找 FptAppHead 来自 seqNo
	 */
	public FptAppHead findFptAppHeadForDownData(String seqNo, String inOutFlag,
			String declareState) {
		List list = this.find("select a from FptAppHead as a "
				+ "	where a.seqNo=? and a.company.id=? and a.inOutFlag=?"
				+ " and a.declareState=? ", new Object[] { seqNo,
				CommonUtils.getCompany().getId(), inOutFlag, declareState });
		if (!list.isEmpty()) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查询深加工结转参数设置
	 */
	public FptBillHead findFmFptParameterSet() {
		List list = this.find("select a from FptParameterSet as a "
				+ "	where  a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		if (!list.isEmpty()) {
			return (FptBillHead) list.get(0);
		}
		return null;
	}

	// /**
	// * 获得关封申请单所有数据来自进出货标志
	// */
	// public List findFptAppHeadByInOutFlag(String fptInOutFlag) {
	// return this.find("select a from FptAppHead as a "
	// + "	where a.inOutFlag=? and a.company.id=? order by a.inOutFlag ", new
	// Object[] {
	// fptInOutFlag, CommonUtils.getCompany().getId() });
	// }

	/**
	 * 获得关封申请单所有数据来自进出货标志
	 */
	public List findFptAppHeadByInOutFlag(String fptInOutFlag,
			String fptDeclareState, String emsDeclareState) {
		String hsql = "";
		hsql = "select a from FptAppHead a where a.company.id = ? and a.inOutFlag = ?";

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(fptInOutFlag);

		if (fptDeclareState != null && !"".equals(fptDeclareState)) {
			hsql += "and a.declareState=?";
			parameters.add(fptDeclareState);
		}

		if (emsDeclareState != null && !"".equals(emsDeclareState)) {
			if (FptInOutFlag.IN.equals(fptInOutFlag)) { // 转进
				hsql += "and a.inEmsNo in (select b.emsNo from Contract b where b.declareState = ?)";
				parameters.add(DeclareState.PROCESS_EXE);
			} else {// 转出
				hsql += "and a.emsNo in (select b.emsNo from Contract b where b.declareState = ?)";
				parameters.add(DeclareState.PROCESS_EXE);
			}
		}
		return find(hsql, parameters.toArray());
	}

	/**
	 * 获得关封申请单所有数据来客户供应商
	 */
	public List findFptAppHeadByScmCoc(ScmCoc scmCoc) {
		return this
				.find("select a from FptAppHead as a "
						+ "	where ( a.scmCoc is not null )"
						+ " and a.scmCoc.id=? and a.company.id=? order by a.inOutFlag ",
						new Object[] { scmCoc.getId(),
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
				.find("select a from FptAppHead as a where a.scmCoc.id = ? and a.company.id=?",
						new Object[] { scmCocId,
								CommonUtils.getCompany().getId() });
	}

	public District findDistrictByCode(String code) {
		List list = this.find("select a from District as a where a.code = ? ",
				new Object[] { code });
		if (list.size() > 0) {
			return (District) list.get(0);
		} else {
			return null;
		}
	}

	public Customs findCustomsByCode(String code) {
		List list = this.find("select a from Customs as a where a.code = ? ",
				new Object[] { code });
		if (list.size() > 0) {
			return (Customs) list.get(0);
		} else {
			return null;
		}
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

	public Unit findUnitByCode(String name) {
		List list = this.find("select a from Unit as a where a.code = ? ",
				new Object[] { name });
		if (list.size() > 0) {
			return (Unit) list.get(0);
		} else {
			return null;
		}
	}

	public Unit findUnitByName(String name) {
		List list = this.find("select a from Unit as a where a.name = ? ",
				new Object[] { name });
		if (list.size() > 0) {
			return (Unit) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获得关封申请单数据来来自选定用客户，且生效、存在未转关封单据的商品 的单据 CEB 代表 customsEnvelopBill 关封单据
	 * 
	 * @param scmCocId
	 * @return
	 */
	public List findCustomsEnvelopRequestBillByScmCocToCEB(String scmCocId) {
		return this
				.find("select a from FptAppHead as a "
						+ "where a.scmCoc.id = ? "
						+ "and a.company.id=? "
						+ "and a.isAvailability=? "
						+ " and a.id in "
						+ " (select b.id from "
						+ " FptAppItem t join t.customsEnvelopRequestBill  b "
						+ " where t.isTranCustomsEnvelop=? or t.isTranCustomsEnvelop is null )",
						new Object[] { scmCocId,
								CommonUtils.getCompany().getId(),
								new Boolean(true), new Boolean(false) });
	}

	/**
	 * 保存关封申请单
	 */
	public void saveFptAppHead(FptAppHead fptAppHead) {
		this.saveOrUpdate(fptAppHead);
	}

	/**
	 * 删除关封申请单
	 */
	public void deleteFptAppHead(FptAppHead fptAppHead) {
		this.delete(fptAppHead);
	}

	/**
	 * 删除关封申请单商品信息数据
	 */
	public void deleteFptAppItem(FptAppItem data) {
		this.delete(data);
	}

	/**
	 * 保存关封申请单商品信息数据
	 */
	public void saveFptAppItem(FptAppItem data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 保存关封申请单商品信息数据
	 */
	public void saveFptAppItem(List list) {
		for (int i = 0; i < list.size(); i++) {
			FptAppItem data = (FptAppItem) list.get(i);
			this.saveOrUpdate(data);
		}
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findFptAppItems(String parentId) {
		return this.find(
				"select b from FptAppItem b where b.fptAppHead.id = ? order by "
						+ "b.inOutFlag ASC, b.listNo ASC ",
				new Object[] { parentId });
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findFptAppItems(String parentId, String inOutFlag) {
		return this.find(
				"select b from FptAppItem b where b.fptAppHead.id = ? and b.inOutFlag = ? "
						+ "order by b.listNo ", new Object[] { parentId,
						inOutFlag });
	}

	/**
	 * 查找转厂已使用的量 by emsNo,seqNum (备案序号)
	 */
	public Double findUseAmoutBySeqNum(String inOutFlag, String emsNo,
			int seqNum, FptAppItem fptAppItem) {
		List<Object> parameters = new ArrayList<Object>();
		//
		// 还没有保存过
		//
		boolean isNew = (fptAppItem.getId() == null || fptAppItem.getId()
				.equals(""));
		String hsql = "";
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			if (isNew) {
				hsql = "  select SUM(a.qty) "
						+ "  from FptAppItem a "
						+ "       left join a.fptAppHead b "
						+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?"
						+ "  and a.inEmsNo = ? and a.trNo = ? "
						+ " and a.fptAppHead.declareState != ? "
						// 如果有正在执行和变更的记录,那么除掉正在执行的
						+ "  and b.id not in ( select c.id from FptAppHead c where c.inCopAppNo "
						+ "				in ( select d.inCopAppNo "
						+ "						from FptAppHead d group by d.inCopAppNo having count(*)>1 "
						+ "	 				) and c.declareState = ? " + // 正在执行
						"              )";
			} else {
				hsql = "  select SUM(a.qty) "
						+ "  from FptAppItem a "
						+ "       left join a.fptAppHead b "
						+ "  where a.id != ? and a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?"
						+ "  and a.inEmsNo = ? and a.trNo = ?  "
						+ " and a.fptAppHead.declareState != ? "
						// 如果有正在执行和变更的记录,那么除掉正在执行的
						+ "  and b.id not in ( select c.id from FptAppHead c where c.inCopAppNo "
						+ "				in ( select d.inCopAppNo "
						+ "						from FptAppHead d group by d.inCopAppNo having count(*)>1 "
						+ "	 				) and c.declareState = ? " + // 正在执行
						"              )";
				parameters.add(fptAppItem.getId());
			}
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(emsNo);
			parameters.add(seqNum);
			parameters.add(DeclareState.IS_CAN);
			parameters.add(DeclareState.PROCESS_EXE);

		} else {// 转出

			if (isNew) {
				hsql = "  select SUM(a.qty) "
						+ "  from FptAppItem a "
						+ "       left join a.fptAppHead b "
						+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?"
						+ "  and b.emsNo = ? and a.trNo = ? "
						+ " and a.fptAppHead.declareState != ? "
						// 如果有正在执行和变更的记录,那么除掉正在执行的
						+ "  and b.id not in ( select c.id from FptAppHead c where c.outCopAppNo "
						+ "				in ( select d.outCopAppNo "
						+ "						from FptAppHead d group by d.outCopAppNo having count(*)>1 "
						+ "	 				) and c.declareState = ? " + // 正在执行
						"              )";
			} else {
				hsql = "  select SUM(a.qty) "
						+ "  from FptAppItem a "
						+ "       left join a.fptAppHead b "
						+ "  where a.id != ? and a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?"
						+ "  and b.emsNo = ? and a.trNo = ?  "
						+ " and a.fptAppHead.declareState != ? "
						// 如果有正在执行和变更的记录,那么除掉正在执行的
						+ "  and b.id not in ( select c.id from FptAppHead c where c.outCopAppNo "
						+ "				in ( select d.outCopAppNo "
						+ "						from FptAppHead d group by d.outCopAppNo having count(*)>1 "
						+ "	 				) and c.declareState = ? " + // 正在执行
						"              )";
				parameters.add(fptAppItem.getId());
			}
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(emsNo);
			parameters.add(seqNum);
			parameters.add(DeclareState.IS_CAN);
			parameters.add(DeclareState.PROCESS_EXE);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return 0.0;
		} else {
			return (Double) list.get(0);
		}
	}

	/**
	 * 查找转厂正在执行已使用的量 by emsNo,seqNum (备案序号)
	 */
	public Double findUseAmoutByProcessExe(String inOutFlag, String emsNo,
			int seqNum) {
		List<Object> parameters = new ArrayList<Object>();
		//
		// 还没有保存过
		//
		String hsql = "";
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select SUM(a.qty) "
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?"
					+ "  and a.inEmsNo = ? and a.trNo = ? and b.declareState = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(emsNo);
			parameters.add(seqNum);
			parameters.add(DeclareState.PROCESS_EXE);

		} else {// 转出
			hsql = "  select SUM(a.qty) "
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?"
					+ "  and b.emsNo = ? and a.trNo = ?  and b.declareState = ?  ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(emsNo);
			parameters.add(seqNum);
			parameters.add(DeclareState.PROCESS_EXE);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return 0.0;
		} else {
			return (Double) list.get(0);
		}
	}

	/**
	 * 获得申请单信息来自参数对象 数据列如下: select b.outTradeName,
	 * a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)
	 */
	public List findFptAppItemsByParam(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.outTradeName, a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.outTradeName=? ";
				parameters.add(tradeCode);
			}
			if (beginDate != null) {
				hsql += " and b.inDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.inDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
		} else {// 转出
			hsql = "  select b.inTradeName, a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.inTradeName=? ";
				parameters.add(tradeCode);
			}
			if (beginDate != null) {
				hsql += " and b.outDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.outDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.codeTs.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.outTradeName, a.codeTs.code,a.name,a.spec,a.unit.name ";
		} else { // 转出

			hsql += " group by b.inTradeName, a.codeTs.code,a.name,a.spec,a.unit.name ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * HYQ 申请表定量(申请表编号+申请表流水号为KYE):
	 * b.emsNo,b.appNo,b.isCollated,b.endDate,b.outTradeName
	 * ,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name
	 */
	public List findFptAppItemsApplyQty(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.inEmsNo,b.appNo,b.isCollated,b.endDate,b.outTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "  left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.outTradeName=? ";
				parameters.add(tradeCode);
			}
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.inEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}
		} else {// 转出
			hsql = "  select b.emsNo,b.appNo,b.isCollated,b.endDate,b.inTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "  left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.inTradeName=? ";
				parameters.add(tradeCode);
			}
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.emsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.codeTs.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}

		if (appNo != null && !"".equals(appNo)) {
			hsql += " and b.appNo=? ";
			parameters.add(appNo);
		}

		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.inEmsNo,b.appNo,b.isCollated,b.endDate,b.outTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name "
					+ " order by b.appNo,a.listNo";
		} else { // 转出
			hsql += " group by b.emsNo,b.appNo,b.isCollated,b.endDate,b.inTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name "
					+ " order by b.appNo,a.listNo";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	public List findFptAppItemsApplyQty0(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 是否结案 */
		Boolean isCollated = param.getIsCollated();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.inEmsNo,b.appNo,b.isCollated,b.endDate,b.outTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "  left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.outTradeName=? ";
				parameters.add(tradeCode);
			}
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.inEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}

		} else {// 转出
			hsql = "  select b.emsNo,b.appNo,b.isCollated,b.endDate,b.inTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "  left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.inTradeName=? ";
				parameters.add(tradeCode);
			}
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.emsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}

		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.codeTs.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}

		if (appNo != null && !"".equals(appNo)) {
			hsql += " and b.appNo=? ";
			parameters.add(appNo);
		}

		hsql += "and b.isCollated = ? or b.isCollated = ?";
		parameters.add(false);
		parameters.add(null);

		// hsql += "and b.isCollated = ?";
		// parameters.add(false);

		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.inEmsNo,b.appNo,b.isCollated,b.endDate,b.outTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name "
					+ " order by b.appNo,a.listNo";
		} else { // 转出
			hsql += " group by b.emsNo,b.appNo,b.isCollated,b.endDate,b.inTradeName,a.listNo,a.trNo,a.codeTs.code,a.name,a.spec,a.unit.name "
					+ " order by b.appNo,a.listNo";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * HYQ 申请表定量(手册号+手册序号): a.emsNo,b.trNo,sum(b.qty)
	 */
	public List findFptAppItemsApplyQtyByEmsNoTrNo(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.inEmsNo,a.trNo,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "  left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.inEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}

		} else {// 转出
			hsql = "  select b.emsNo,a.trNo,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "  left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.emsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.inEmsNo,a.trNo "
					+ " order by b.inEmsNo,a.trNo ";
		} else { // 转出
			hsql += " group by b.emsNo,a.trNo " + " order by b.emsNo,a.trNo ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获得申请单信息来自参数对象 数据列如下: select b.outTradeName,
	 * a.codeTs.code,a.name,a.spec,a.unit.name,b.appNo,a.inEmsNo,SUM(a.qty)
	 */
	public List findFptAppItemsDetailByParam(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		// HH2013.11.19 添加项号 a.trNo
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.outTradeName, a.codeTs.code,a.name,a.spec,a.unit.name,b.appNo,a.inEmsNo,a.trNo,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.outTradeName=? ";
				parameters.add(tradeCode);
			}
			if (beginDate != null) {
				hsql += " and b.inDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.inDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and a.inEmsNo=? ";
				parameters.add(emsNo);
			}
		} else {// 转出
			hsql = "  select b.inTradeName, a.codeTs.code,a.name,a.spec,a.unit.name,b.appNo,b.emsNo,a.trNo,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.inTradeName=? ";
				parameters.add(tradeCode);
			}
			if (beginDate != null) {
				hsql += " and b.outDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.outDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and b.emsNo=? ";
				parameters.add(emsNo);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.codeTs.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		if (appNo != null && !"".equals(appNo)) {
			hsql += " and b.appNo=? ";
			parameters.add(appNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.outTradeName, a.codeTs.code,a.name,a.spec,a.unit.name,b.appNo,a.inEmsNo ,a.trNo";// HH2013.11.19
																													// 添加项号
																													// trNo
		} else { // 转出
			hsql += " group by b.inTradeName, a.codeTs.code,a.name,a.spec,a.unit.name,b.appNo,b.emsNo ,a.trNo";// HH2013.11.19
																												// 添加项号
																												// trNo
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 除掉客户供应商海关代码 获得申请单信息来自参数对象 数据列如下: select
	 * a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)
	 */
	public List findFptAppItemsByParam2(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			// if (tradeCode != null && !"".equals(tradeCode)) {
			// hsql += " and b.outTradeName=? ";
			// parameters.add(tradeCode);
			// }
			if (beginDate != null) {
				hsql += " and b.inDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.inDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
		} else {// 转出
			hsql = "  select  a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);

			// if (tradeCode != null && !"".equals(tradeCode)) {
			// hsql += " and b.inTradeName=? ";
			// parameters.add(tradeCode);
			// }
			if (beginDate != null) {
				hsql += " and b.outDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.outDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.codeTs.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		} else {
			//
			// 除掉变更的记录
			//
			hsql += " and b.declareState!=? ";
			parameters.add(DeclareState.CHANGING_EXE);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.codeTs.code,a.name,a.spec,a.unit.name ";
		} else { // 转出
			hsql += " group by a.codeTs.code,a.name,a.spec,a.unit.name ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * <<<<<<< .mine 除掉客户供应商海关代码 获得 DZSC(成品或料件) 合同定量 数据列如下: ======= 除掉客户供应商海关代码
	 * 获得申请单信息来自参数对象 数据列如下: select
	 * a.codeTs.code,a.name,a.spec,a.unit.name,b.emsNo,SUM(a.qty) >>>>>>>
	 * .r14120
	 */
	public List findFptAppItemsDetailByParam2(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select a.codeTs.code,a.name,a.spec,a.unit.name,a.inEmsNo,SUM(a.qty),a.trNo"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ?  ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			// if (tradeCode != null && !"".equals(tradeCode)) {
			// hsql += " and b.outTradeName=? ";
			// parameters.add(tradeCode);
			// }
			if (beginDate != null) {
				hsql += " and b.inDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.inDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and a.inEmsNo=? ";
				parameters.add(emsNo);
			}
		} else {// 转出
			hsql = "  select  a.codeTs.code,a.name,a.spec,a.unit.name,b.emsNo,SUM(a.qty),a.trNo"
					+ "  from FptAppItem a "
					+ "       left join a.fptAppHead b "
					+ "  where a.company.id=? and a.inOutFlag = ? and b.inOutFlag = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			// if (tradeCode != null && !"".equals(tradeCode)) {
			// hsql += " and b.inTradeName=? ";
			// parameters.add(tradeCode);
			// }
			if (beginDate != null) {
				hsql += " and b.outDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.outDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and b.emsNo=? ";
				parameters.add(emsNo);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.codeTs.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		} else {
			//
			// 除掉变更的记录
			//
			hsql += " and b.declareState!=? ";
			parameters.add(DeclareState.CHANGING_EXE);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.codeTs.code,a.name,a.spec,a.unit.name,a.inEmsNo,a.trNo ";
		} else { // 转出
			hsql += " group by a.codeTs.code,a.name,a.spec,a.unit.name,b.emsNo,a.trNo ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 除掉客户供应商海关代码 获得 DZSC(成品或料件) 合同定量 数据列如下:
	 */
	public List findContractAmoutByDzsc(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		String hsql = "";// ContractExg
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,SUM(a.amount)"
					+ "  from DzscEmsImgBill a "
					+ "       left join a.dzscEmsPorHead b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		} else {// 转出（成品)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,SUM(a.amount)"
					+ "  from DzscEmsExgBill a "
					+ "       left join a.dzscEmsPorHead b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}

		//
		// 共同的条件
		//
		// hsql += " and b.isContractEms=? ";
		// parameters.add(true);
		// if (beginDate != null) {
		// hsql += " and b.beginDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and b.beginDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		// if (projectType != null) {
		// hsql += " and b.projectType=? ";
		// parameters.add(projectType);
		// }
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		} else {
			//
			// 除掉变更的记录
			//
			hsql += " and b.declareState!=? ";
			parameters.add(DeclareState.CHANGING_EXE);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name ";
		} else { // 转出
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * <<<<<<< .mine 除掉客户供应商海关代码 获得 BCS (成品或料件) 合同定量 数据列如下: ======= 除掉客户供应商海关代码
	 * 获得 DZSC(成品或料件) 单个合同定量 数据列如下: >>>>>>> .r14120
	 */
	public List findSingleContractAmoutByDzsc(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		String hsql = "";// ContractExg
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,SUM(a.amount)"
					+ "  from DzscEmsImgBill a "
					+ "       left join a.dzscEmsPorHead b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		} else {// 转出（成品)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,SUM(a.amount)"
					+ "  from DzscEmsExgBill a "
					+ "       left join a.dzscEmsPorHead b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}

		//
		// 共同的条件
		//
		// hsql += " and b.isContractEms=? ";
		// parameters.add(true);
		// if (beginDate != null) {
		// hsql += " and b.beginDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and b.beginDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		// if (projectType != null) {
		// hsql += " and b.projectType=? ";
		// parameters.add(projectType);
		// }
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo=? ";
			parameters.add(emsNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		} else {
			//
			// 除掉变更的记录
			//
			hsql += " and b.declareState!=? ";
			parameters.add(DeclareState.CHANGING_EXE);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name,b.emsNo ";
		} else { // 转出
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name,b.emsNo, ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 除掉客户供应商海关代码 获得 BCS (成品或料件) 合同定量 数据列如下:
	 */
	public List findContractAmoutByBcs(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		String hsql = "";// ContractExg
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,SUM(a.amount)"
					+ "  from ContractImg a "
					+ "       left join a.contract b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		} else {// 转出（成品)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,SUM(a.exportAmount)"
					+ "  from ContractExg a "
					+ "       left join a.contract b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}
		//
		// 共同的条件
		//
		hsql += " and b.isContractEms=? ";
		parameters.add(true);
		// if (beginDate != null) {
		// hsql += " and b.beginDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and b.beginDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		// if (projectType != null) {
		// hsql += " and b.projectType=? ";
		// parameters.add(projectType);
		// }
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name ";
		} else { // 转出
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 除掉客户供应商海关代码 获得 BCS (成品或料件) 合同定量 数据列如下:
	 */
	public List findSingleContractAmoutByBcs(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		String hsql = "";// ContractExg
		//
		// 要统计对方公司明称
		//
		// HH2013.11.19 添加 a.credenceNo
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,a.credenceNo,SUM(a.amount)"
					+ "  from ContractImg a "
					+ "       left join a.contract b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		} else {// 转出（成品)
			hsql = "  select a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,a.credenceNo,SUM(a.exportAmount)"
					+ "  from ContractExg a "
					+ "       left join a.contract b "
					+ "  where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}
		//
		// 共同的条件
		//
		hsql += " and b.isContractEms=? ";
		parameters.add(true);
		// if (beginDate != null) {
		// hsql += " and b.beginDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and b.beginDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		// if (projectType != null) {
		// hsql += " and b.projectType=? ";
		// parameters.add(projectType);
		// }
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo=? ";
			parameters.add(emsNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,a.credenceNo "; // HH2013.11.19
																								// 添加
																								// a.credenceNo
		} else { // 转出
			hsql += " group by a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,a.credenceNo "; // HH2013.11.19
																								// 添加
																								// a.credenceNo
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * HYQ 合同定量 手册号+手册商品流水号 BCS
	 */
	public List findBcsByEmsNoSeqNum(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql = "  select b.emsNo,a.seqNum,a.credenceNo,b.endDate,a.complex.code,a.name,a.spec,a.unit.name,a.amount"
					+ " from ContractImg a "
					+ " left join a.contract b "
					+ " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());

		} else {// 转出（成品)
			hsql = "  select b.emsNo,a.seqNum,a.credenceNo,b.endDate,a.complex.code,a.name,a.spec,a.unit.name,a.exportAmount"
					+ " from ContractExg a "
					+ " left join a.contract b "
					+ " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}
		//
		// 共同的条件
		//
		hsql += " and b.isContractEms=? ";
		parameters.add(true);

		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo=? ";
			parameters.add(emsNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		}
		if (emsDeclareState != null && !"".equals(emsDeclareState)) {
			hsql += "and b.declareState = ? ";
			parameters.add(DeclareState.PROCESS_EXE);
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * HYQ 电子手册合同定量 获得 DZSC(成品或料件) 单个合同定量 数据列如下: >>>>>>> .r14120
	 */
	public List findDzscByEmsNoSeqNum(TempFptAppParam param) {
		List<Object> parameters = new ArrayList<Object>();

		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql = "  select b.emsNo,a.seqNum,a.tenSeqNum,b.deferDate,a.complex.code,a.name,a.spec,a.unit.name,a.amount"
					+ " from DzscEmsImgBill a "
					+ " left join a.dzscEmsPorHead b "
					+ " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		} else {// 转出（成品)
			hsql = "  select b.emsNo,a.seqNum,a.tenSeqNum,b.deferDate,a.complex.code,a.name,a.spec,a.unit.name,a.amount"
					+ " from DzscEmsExgBill a "
					+ " left join a.dzscEmsPorHead b "
					+ " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}

		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name=? ";
			parameters.add(name);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo=? ";
			parameters.add(emsNo);
		}
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(declareState);
		} else {
			//
			// 除掉变更的记录
			//
			hsql += " and b.declareState!=? ";
			parameters.add(DeclareState.CHANGING_EXE);
		}
		if (emsDeclareState != null && !"".equals(emsDeclareState)) {
			hsql += " and b.declareState=? ";
			parameters.add(DeclareState.PROCESS_EXE);
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获得已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,sum(a.commAmount)
	 */
	public List findCommInfoTotalAmount(TempFptAppParam param) {

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.complex.code,a.commName,a.commSpec,a.unit.name,sum(a.commAmount) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hsql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hsql += " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		hsql += " left outer join a.baseCustomsDeclaration as b "
				+ " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.IMPORT);
		} else {// 转出（成品)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.EXPORT);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		}
		hsql += " group by a.complex.code,a.commName,a.commSpec,a.unit.name ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,b.emsHeadH2k
	 * ,sum(a.commAmount)
	 */
	public List findSingleCommInfoTotalAmount(TempFptAppParam param) {

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.complex.code,a.commName,a.commSpec,a.unit.name,b.emsHeadH2k,sum(a.commAmount),a.commSerialNo ";
		switch (projectType) {
		case ProjectType.BCUS:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hsql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hsql += " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		hsql += " left outer join a.baseCustomsDeclaration as b "
				+ " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.IMPORT);
		} else {// 转出（成品)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.EXPORT);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		}
		hsql += " group by a.complex.code,a.commName,a.commSpec,a.unit.name,b.emsHeadH2k,a.commSerialNo ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得转厂已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,brief.name
	 * ,sum(a.commAmount)
	 */
	public List findCommInfoTotalAmountByTransferFactory(TempFptAppParam param) {

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.complex.code,a.commName,a.commSpec,a.unit.name,brief.name,sum(a.commAmount) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hsql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hsql += " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		hsql += " left outer join a.baseCustomsDeclaration as b "
				+ " left outer join b.customer as scmCoc "
				+ " left outer join scmCoc.brief as brief "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ "       and b.customer is not null "
				+ "       and scmCoc.brief is not null ";
		parameters.add(CommonUtils.getCompany().getId());

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.IMPORT);

			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);

		} else {// 转出（成品)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.EXPORT);

			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		}
		hsql += " group by a.complex.code,a.commName,a.commSpec,a.unit.name,brief.name ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得转厂已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,brief.name
	 * ,sum(a.commAmount)
	 */
	public List findSingleCommInfoTotalAmountByTransferFactory(
			TempFptAppParam param) {

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// // 客户供应商海关代码 */
		// String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.complex.code,a.commName,a.commSpec,a.unit.name,brief.name,b.emsHeadH2k,sum(a.commAmount) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hsql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hsql += " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		hsql += " left outer join a.baseCustomsDeclaration as b "
				+ " left outer join b.customer as scmCoc "
				+ " left outer join scmCoc.brief as brief "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ "       and b.customer is not null "
				+ "       and scmCoc.brief is not null ";
		parameters.add(CommonUtils.getCompany().getId());

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.IMPORT);

			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);

		} else {// 转出（成品)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.EXPORT);

			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		}
		hsql += " group by a.complex.code,a.commName,a.commSpec,a.unit.name,brief.name,b.emsHeadH2k ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * HYQ 获得转厂已报关的数量 key=b.emsHeadH2k,a.commSerialNo sum(a.commAmount)
	 */
	public List findDeclarationTransferFactoryByEmsNoSeqNum(
			TempFptAppParam param) {

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select b.emsHeadH2k,a.commSerialNo,sum(a.commAmount) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hsql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hsql += " from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		}
		hsql += " left outer join a.baseCustomsDeclaration as b "
				+ " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进（料件)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.IMPORT);

			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);

		} else {// 转出（成品)
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(ImpExpFlag.EXPORT);

			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		}

		if (emsDeclareState != null && !"".equals(emsDeclareState)) {
			switch (projectType) {
			// case ProjectType.BCUS:
			// break;
			case ProjectType.BCS:
				hsql += " and b.emsHeadH2k in (select c.emsNo from Contract c where c.declareState=? )";
				parameters.add(DeclareState.PROCESS_EXE);
				break;
			case ProjectType.DZSC:
				hsql += " and b.emsHeadH2k in (select c.emsNo from DzscEmsPorHead c where c.declareState=? )";
				parameters.add(DeclareState.PROCESS_EXE);
				break;
			}
		}
		hsql += " group by b.emsHeadH2k,a.commSerialNo ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec
	 * ,a.unit.name,SUM(a.qty)
	 */
	public List findFptBillItemsByParam(TempFptAppParam param,
			String fptBusinessType) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		String hsql = "";

		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "  left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.issueTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.receiveIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.receiveIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
		} else {// 转出
			hsql = "  select b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.receiveTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.issueIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.issueIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		hsql += " and b.appState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		// if (declareState != null
		// && DeclareState.PROCESS_EXE.equals(declareState)) {
		// hsql += " and b.appState=? ";
		// parameters.add(declareState);
		// }
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name ";
		} else { // 转出
			hsql += " group by b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * HYQ 深加工结转--收送货单--收发货数量(key = b.appNo+a.appGNo)
	 */
	public List findFptBillItemsSendReceiveQty(TempFptAppParam param,
			String fptBusinessType) {

		List<Object> parameters = new ArrayList<Object>();
		// 类型
		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.appNo,a.appGNo,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "  left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (beginDate != null) {
				hsql += " and b.receiveIsDeclaDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.receiveIsDeclaDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and a.inEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}
		} else {// 转出
			hsql = "  select b.appNo,a.appGNo,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "  left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (beginDate != null) {
				hsql += " and b.issueIsDeclaDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.issueIsDeclaDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.outEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}

		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.appState=? ";
			parameters.add(declareState);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.appNo,a.appGNo ";
		} else { // 转出
			hsql += " group by b.appNo,a.appGNo ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * HYQ 深加工结转--收送货单--收发货数量 (key = 手册号+手册序号(EmsNo+trGno))
	 */
	public List findFptBillItemsSendReceiveQtyByEmsNoTrNo(
			TempFptAppParam param, String fptBusinessType) {

		List<Object> parameters = new ArrayList<Object>();
		// 类型
		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		// 手册号
		String emsNo = param.getEmsNo();

		// 手册序号
		Integer trGno = param.getTrGno();

		// 手册状态 3：正在执行 nul：全部
		String emsDeclareState = param.getEmsDeclareState();

		String hsql = "";

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select a.inEmsNo,a.trGno,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "  left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and a.inEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}

			if (beginDate != null) {
				hsql += " and b.receiveIsDeclaDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.receiveIsDeclaDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsNo != null) {
				hsql += " and a.inEmsNo=? ";
				parameters.add(emsNo);
			}
		} else {// 转出
			hsql = "  select b.outEmsNo,a.trGno,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "  left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);
			if (emsDeclareState != null && !"".equals(emsDeclareState)) {
				hsql += " and b.outEmsNo in (select c.emsNo from Contract c where c.declareState = ?) ";
				parameters.add(DeclareState.PROCESS_EXE);
			}
			if (beginDate != null) {
				hsql += " and b.issueIsDeclaDate>=? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hsql += " and b.issueIsDeclaDate<=? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			if (emsNo != null) {
				hsql += " and b.outEmsNo=? ";
				parameters.add(emsNo);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (trGno != null) {
			hsql += " and a.trGno=? ";
			parameters.add(trGno);
		}

		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		if (declareState != null
				&& DeclareState.PROCESS_EXE.equals(declareState)) {
			hsql += " and b.appState=? ";
			parameters.add(declareState);
		}

		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by a.inEmsNo,a.trGno ";
		} else { // 转出
			hsql += " group by b.outEmsNo,a.trGno ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获得转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec
	 * ,a.unit.name,b.appNo,b.outEmsNo,SUM(a.qty)
	 */
	public List findFptBillItemsDetailByParam(TempFptAppParam param,
			String fptBusinessType) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,b.appNo,a.inEmsNo,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.issueTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.receiveIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.receiveIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and a.inEmsNo=? ";
				parameters.add(emsNo);
			}
		} else {// 转出
			hsql = "  select b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,b.appNo,b.outEmsNo,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.receiveTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.issueIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.issueIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and b.outEmsNo=? ";
				parameters.add(emsNo);
			}
		}

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (appNo != null && !"".equals(appNo)) {
			hsql += " and b.appNo=? ";
			parameters.add(appNo);
		}

		hsql += " and b.appState=? ";
		parameters.add(DeclareState.PROCESS_EXE);

		// if (declareState != null
		// && DeclareState.PROCESS_EXE.equals(declareState)) {
		// hsql += " and b.appState=? ";
		// parameters.add(declareState);
		// }
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name, b.appNo,a.inEmsNo";
		} else { // 转出
			hsql += " group by b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,b.appNo,b.outEmsNo ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获得未发转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec
	 * ,a.unit.name,,SUM(a.qty)
	 */
	public List findFptBillItemsByParam2(TempFptAppParam param,
			String fptBusinessType) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.issueTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.receiveIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.receiveIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
		} else {// 转出
			hsql = "  select b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.receiveTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.issueIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.issueIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
		}
		//
		// 新增的
		// 查询报错 暂时 屏蔽 下 后续修改 HH2013.10.30 could not resolve property: modifyMake
		// hsql += " and a.modifyMake=? ";
		// parameters.add(ModifyMarkState.ADDED);

		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		// if (declareState != null
		// && DeclareState.PROCESS_EXE.equals(declareState)) {
		// hsql += " and b.appState=? ";
		// parameters.add(declareState);
		// }else {
		// //
		// // 只加入可修改的的记录
		// //
		hsql += " and b.appState in (?,?) ";
		parameters.add(DeclareState.CHANGING_EXE);
		parameters.add(DeclareState.WAIT_EAA);
		// }
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name ";
		} else { // 转出
			hsql += " group by b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name ";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获得未发转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,
	 * b.appNo,a.inEmsNo,SUM(a.qty)
	 */
	public List findFptBillItemsDetailByParam2(TempFptAppParam param,
			String fptBusinessType) {
		List<Object> parameters = new ArrayList<Object>();

		Integer projectType = param.getProjectType();
		// 转入转出标记 0：转出，1：转入 */
		String inOutFlag = param.getInOutFlag();
		// 客户供应商海关代码 */
		String tradeCode = param.getTradeName();
		// 商品编码 */
		String complex = param.getComplex();
		// 商品名称 */
		String name = param.getName();
		// 申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
		String declareState = param.getDeclareState();
		// 开始时间 */
		Date beginDate = param.getBeginDate();
		// 结束时间 */
		Date endDate = param.getEndDate();
		// 申请表编号 */
		String appNo = param.getAppNo();
		// 手册编号/帐册编号 */
		String emsNo = param.getEmsNo();

		String hsql = "";
		//
		// 要统计对方公司明称
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转进
			hsql = "  select b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name, b.appNo,a.inEmsNo,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.issueTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.receiveIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.receiveIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and a.inEmsNo=? ";
				parameters.add(emsNo);
			}
		} else {// 转出
			hsql = "  select b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,b.appNo,b.outEmsNo,SUM(a.qty)"
					+ "  from FptBillItem a "
					+ "       left join a.fptBillHead b "
					+ "  where a.company.id=? and a.billSort = ? and b.billSort = ? and b.sysType = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(inOutFlag);
			parameters.add(inOutFlag);
			parameters.add(fptBusinessType);

			if (tradeCode != null && !"".equals(tradeCode)) {
				hsql += " and b.receiveTradeName=? ";
				parameters.add(tradeCode);
			}
			// if (beginDate != null) {
			// hsql += " and b.issueIsDeclaDate>=? ";
			// parameters.add(CommonUtils.getBeginDate(beginDate));
			// }
			// if (endDate != null) {
			// hsql += " and b.issueIsDeclaDate<=? ";
			// parameters.add(CommonUtils.getEndDate(endDate));
			// }
			if (emsNo != null && !"".equals(emsNo)) {
				hsql += " and b.outEmsNo=? ";
				parameters.add(emsNo);
			}
		}
		//
		// 新增的
		// HH 2013.10.31 Error:could not resolve property: modifyMake
		// 暂时隐藏modifyMake
		// hsql += " and a.modifyMake=? ";
		// parameters.add(ModifyMarkState.ADDED);
		//
		// 共同的条件
		//
		if (projectType != null) {
			hsql += " and b.projectType=? ";
			parameters.add(projectType);
		}
		if (complex != null) {
			hsql += " and a.complex.code=? ";
			parameters.add(complex);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (appNo != null && !"".equals(appNo)) {
			hsql += " and b.appNo=? ";
			parameters.add(appNo);
		}
		//
		// 只要是正在执行的时修要过滤,否则全部
		//
		// if (declareState != null
		// && DeclareState.PROCESS_EXE.equals(declareState)) {
		// hsql += " and b.appState=? ";
		// parameters.add(declareState);
		// }else {
		// //
		// // 只加入可修改的的记录
		// //
		hsql += " and b.appState in (?,?) ";
		parameters.add(DeclareState.CHANGING_EXE);
		parameters.add(DeclareState.WAIT_EAA);
		// }
		if (FptInOutFlag.IN.equals(inOutFlag)) { // 转入
			hsql += " group by b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name ,b.appNo,a.inEmsNo ";
		} else { // 转出
			hsql += " group by b.receiveTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name ,b.appNo,b.outEmsNo";
		}
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获得转厂申请单信息加载子表的记录
	 */
	public List findFptAppItemsByModifyMarkState(String parentId,
			String inOutFlag, String modifyMarkState) {
		return this.find(
				"select b from FptAppItem b where b.fptAppHead.id = ? and b.inOutFlag = ? "
						+ " and b.modifyMarkState = ? " + "order by b.listNo ",
				new Object[] { parentId, inOutFlag, modifyMarkState });
	}

	/**
	 * 获得转厂单据加载子表的记录
	 */

	public List findFptBillItems(String parentId, String inOutFlag) {
		return this.find(
				"select b from FptBillItem b where b.fptBillHead.id = ? and b.billSort = ? "
						+ " order by b.listNo ", new Object[] { parentId,
						inOutFlag });

	}

	/**
	 * 获得刚申报后,未修改的数据
	 * 
	 * @param parentId
	 * @param inOutFlag
	 * @return
	 */
	public List findFptAppItemsStateChanged(String parentId, String inOutFlag) {
		return this.find(
				"select b from FptAppItem b where b.fptAppHead.id = ? and b.inOutFlag = ?  "
						+ " and b.modifyMarkState<>? and b.company.id=? "
						+ " order by b.listNo ", new Object[] { parentId,
						inOutFlag, ModifyMarkState.UNCHANGE,
						CommonUtils.getCompany().getId() });
	}

	/** 获得最大的数值 */
	public Integer findMaxValueByFptAppItem(String parentId, String inOutFlag) {
		List list = this
				.find("select max(a.listNo) from FptAppItem as a "
						+ "	where a.fptAppHead.id = ? and a.inOutFlag=? and a.company.id=?",
						new Object[] { parentId, inOutFlag,
								CommonUtils.getCompany().getId() });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}

	/** 获得最大的数值除掉新增的值 */
	public Integer findMaxValueByFptAppItemExceptAdd(String parentId,
			String inOutFlag) {
		List list = this
				.find("select max(a.listNo) from FptAppItem as a "
						+ "	where a.fptAppHead.id = ? and a.inOutFlag=? and a.company.id=? "
						+ " and a.modifyMarkState <> ? ", new Object[] {
						parentId, inOutFlag, CommonUtils.getCompany().getId(),
						ModifyMarkState.ADDED });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}

	/** 获得 FptAppItem 来自序号 */
	public FptAppItem findFptAppItemByListNo(String parentId, Integer listNo,
			String inOutFlag) {
		List list = this
				.find("select a from FptAppItem as a "
						+ "	where a.fptAppHead.id = ? and a.inOutFlag=? and a.company.id=? and a.listNo = ? ",
						new Object[] { parentId, inOutFlag,
								CommonUtils.getCompany().getId(), listNo });
		if (list.size() <= 0 || list.get(0) == null) {
			return null;
		} else {
			return (FptAppItem) list.get(0);
		}
	}

	/** 获得转出 FptAppItem 来自序号集合 */
	public List<Integer> findFptAppItemListNoByOut(String parentId) {
		List list = this
				.find("select a.listNo from FptAppItem as a "
						+ "	where a.fptAppHead.id = ? and a.inOutFlag=? and a.company.id=? order by a.listNo",
						new Object[] { parentId, FptInOutFlag.OUT,
								CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 获得当前关封申请单的商品信息的个数
	 */
	public int findCustomsEnvelopRequestCommodityInfoCount(String parentId) {
		List list = this.find("select count(*) from FptAppItem b  "
				+ " where b.customsEnvelopRequestBill.id = ? ",
				new Object[] { parentId });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得最大的单据号来自进出货标志
	 */
	public List getMaxBillNoByImpExpGoodsFlag(boolean impExpGoodsFlag) {
		return this.find("select max(a.billNo) from FptAppHead as a "
				+ "	where a.inOutFlag=? and a.company.id=?", new Object[] {
				Boolean.valueOf(impExpGoodsFlag),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得关封申请单据商品信息来自父对象
	 */
	public List findCustomsEnvelopRequestCommodityInfoByParent(List parentList) {
		String hsql = "select b from FptAppItem b left "
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
					.find("select a.materiel.ptNo,a.isForbid from InnerMergeData a where a.imrType=? and a.company.id =?"
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
		String sql = "select a.materiel.ptNo,a.isForbid from InnerMergeData a where a.imrType=? and a.company.id =?"
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
		return this.find("select a from FptAppHead as a "
				+ "	where a.customsEnvelopBillId=? and a.company.id=?",
				new Object[] { customsEnvelopBillId,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * ---------------------------------------------------- 关封单据用到的方法
	 * -------------------------------------------------------
	 */
	// /**
	// * 获得关封单据所有数据
	// */
	// public List findCustomsEnvelopBill() {
	// return this.find(
	// "select a from CustomsEnvelopBill as a where a.company.id=?",
	// CommonUtils.getCompany().getId());
	// }
	/**
	 * 根据转厂申请单单据
	 */
	public FptAppHead findFptAppHeadBillByCode(String code, String billSort) {
		List list = this.find(
				"select a from FptAppHead as a where a.company.id=?"
						+ " and a.appNo=? and a.inOutFlag=? ", new Object[] {
						CommonUtils.getCompany().getId(), code, billSort });
		if (list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 结转单据取得结转申请表中的表头资料
	 * 
	 * @param isImportGoods
	 * @return
	 */
	public List findMaterielByFptAppHeadType(String isImportGoods) {
		List<Object> para = new ArrayList<Object>();
		String hsql = " select a from FptAppHead  a   where   "
				+ " a.inOutFlag=?   and  a.company.id=? and a.declareState=?  order  by appNo ";
		para.add(isImportGoods);
		para.add(CommonUtils.getCompany().getId());
		para.add(DeclareState.PROCESS_EXE);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据申请单编号获取申请单
	 * 
	 * @param request
	 * @param isImportGoods
	 * @param list
	 * @return
	 */
	public List findFptAppHead(String isImportGoods, List<String> list) {

		List dateSource = new ArrayList();

		List<Object> para = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select a from FptAppHead  a ");
		sb.append(" where a.inOutFlag=? and  a.company.id=? and a.declareState=? ");
		para.add(isImportGoods);
		para.add(CommonUtils.getCompany().getId());
		para.add(DeclareState.PROCESS_EXE);
		for (int i = 0; i < list.size(); i += 1000) {
			int maxIndex = i + 1000 > list.size() ? list.size() : i + 1000;
			String stu = " and a.appNo in ('"
					+ StringUtils.join(list.subList(i, maxIndex).toArray(),
							"','") + "') order  by a.appNo ";
			dateSource.addAll(this.find(sb.toString() + stu, para.toArray()));
		}

		return dateSource;
	}

	/**
	 * 结转单据取得结转申请表中的表体明细资料
	 */
	public List findMaterielByFptAppItemType(String isImportGoods, String appNo) {
		List<Object> para = new ArrayList<Object>();
		String hsql = " select a from FptAppItem  a "
				+ "where  a.fptAppHead.inOutFlag=? and a.inOutFlag=? "
				+ "and a.company.id=? and a.fptAppHead.appNo=? and a.fptAppHead.declareState=?";
		para.add(isImportGoods);
		para.add(isImportGoods);
		para.add(CommonUtils.getCompany().getId());
		para.add(appNo);
		para.add(DeclareState.PROCESS_EXE);
		return this.find(hsql, para.toArray());

	}

	/**
	 * 结转单据取得结转申请表中的表体明细资料
	 */
	public List findMaterielByFptAppItemType(String isImportGoods, List list) {

		List dateSource = new ArrayList();

		List<Object> para = new ArrayList<Object>();
		String hsql = " select a from FptAppItem  a "
				+ "where  a.fptAppHead.inOutFlag=? and a.inOutFlag=? "
				+ "and a.company.id=? and a.fptAppHead.declareState=?";
		para.add(isImportGoods);
		para.add(isImportGoods);
		para.add(CommonUtils.getCompany().getId());
		para.add(DeclareState.PROCESS_EXE);

		for (int i = 0; i < list.size(); i += 1000) {
			int maxIndex = i + 1000 > list.size() ? list.size() : i + 1000;
			String stu = " and a.listNo in ("
					+ StringUtils
							.join(list.subList(i, maxIndex).toArray(), ",")
					+ ") order  by a.listNo ";
			dateSource.addAll(this.find(hsql + stu, para.toArray()));
		}

		return dateSource;

	}

	/**
	 * 根据关封号获得关封单据
	 */
	public List findFptAppByAppNo(String appNo, String declareState) {
		return this.find("select a from FptAppHead as a where a.company.id=?"
				+ " and a.appNo=? and a.declareState=? ", new Object[] {
				CommonUtils.getCompany().getId(), appNo, declareState });
	}

	/**
	 * 查询结转申请单
	 * 
	 * @param inOutFlag
	 * @param declareState
	 * @param scmCocTradeCode
	 * @param isCollated
	 *            是否结案
	 * @return
	 */
	public List findFptAppHeadByScmCoc(String inOutFlag, String declareState,
			ScmCoc scmCoc, Boolean isCollated) {
		String hql = "select a from FptAppHead as a " + "	where a.inOutFlag=? "
				+ " and a.declareState=? and a.company.id=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(inOutFlag);
		parameters.add(declareState);
		parameters.add(CommonUtils.getCompany().getId());
		if (scmCoc != null) {
			hql += " and a.scmCoc.id=? ";
			parameters.add(scmCoc.getId());
		}
		if (isCollated != null) {
			hql += " and a.isCollated=? ";
			parameters.add(isCollated);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据报关单信息获得结转申请单明细数据
	 */
	public List findFptAppItemByCustomsDeclaration(int impExpType,
			String emsNo, ScmCoc scmCoc) {
		String hql = "select a from FptAppItem as a "
				+ "	where a.fptAppHead.inOutFlag=? "
				+ " and a.fptAppHead.declareState=? "
				+ " and a.fptAppHead.company.id=? "
				+ " and a.inOutFlag=a.fptAppHead.inOutFlag ";
		List<Object> parameters = new ArrayList<Object>();
		String inOutFlag = null;
		switch (impExpType) {
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
			inOutFlag = FptInOutFlag.IN;
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
			inOutFlag = FptInOutFlag.OUT;
			break;
		default:
			inOutFlag = FptInOutFlag.IN;
			break;
		}
		parameters.add(inOutFlag);
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(CommonUtils.getCompany().getId());
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			if (scmCoc != null && scmCoc.getBrief() != null) {
				hql += " and a.fptAppHead.inTradeCode=? ";// outTradeCode
				parameters.add(scmCoc.getBrief().getCode());
			}
			hql += " and a.fptAppHead.emsNo like ? ";
			parameters.add("%" + emsNo + "%");
		} else if (FptInOutFlag.IN.equals(inOutFlag)) {
			if (scmCoc != null && scmCoc.getBrief() != null) {
				hql += " and a.fptAppHead.outTradeCode=? ";// inTradeCode
				parameters.add(scmCoc.getBrief().getCode());
			}
			hql += " and a.inEmsNo like ? ";
			parameters.add("%" + emsNo + "%");
		}
		return this.find(hql, parameters.toArray());
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
					+ " and c.seqNum not in (select e.seqNum "
					+ " from CustomsEnvelopCommodityInfo e"
					+ " where e.customsEnvelopBill.id =? )"
					+ " order by c.seqNum ",
					new Object[] { emsNo, Integer.valueOf(SendState.SEND),
							false, parentId });
			// System.out.println("-----------list.size:"+list.size());
			return list;
		} else {
			List list = this
					.find("select c from EmsHeadH2kImg c  "
							+ "	where c.emsHeadH2k.emsNo=? and c.emsHeadH2k.declareState=? "
							+ " and c.emsHeadH2k.historyState=? "
							+ " and c.seqNum not in (select e.seqNum "
							+ " from CustomsEnvelopCommodityInfo e"
							+ " where e.customsEnvelopBill.id =? )"
							+ " order by c.seqNum ", new Object[] { emsNo,
							DeclareState.PROCESS_EXE, false, parentId });

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
					+ " and c.seqNum not in (select e.seqNum "
					+ " from CustomsEnvelopCommodityInfo e"
					+ " where e.customsEnvelopBill.id =?  )"
					+ " order by c.seqNum ",
					new Object[] { emsNo, Integer.valueOf(SendState.SEND),
							false, parentId });
			return list;
		} else {
			List list = this.find("select c from EmsHeadH2kExg c  "
					+ "	where c.emsHeadH2k.emsNo=? "
					+ " and c.emsHeadH2k.declareState=? "
					+ " and c.emsHeadH2k.historyState=? "
					+ " and c.seqNum not in (select e.seqNum "
					+ " from CustomsEnvelopCommodityInfo e"
					+ " where e.customsEnvelopBill.id =?  )"
					+ " order by c.seqNum ", new Object[] { emsNo,
					DeclareState.PROCESS_EXE, false, parentId });
			return list;
		}
	}

	/**
	 * * ---------------------------------------------------- 结转单据用到的方法
	 * -------------------------------------------------------
	 */

	// /**
	// * 获得结转所有单据
	// */
	// public List findTransferFactoryBill() {
	// return this.find(
	// "select a from TransferFactoryBill as a where a.company.id=?",
	// CommonUtils.getCompany().getId());
	// }
	/**
	 * 获得结转所有单据来自进出货标志
	 */
	public List findInOutFptBillHeadByType(String fptInOutFlag,
			String fptBusinessType, Date beginDate, Date endDate,
			String tradeCode, String state, String appNo, ScmCoc scmcoc) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FptBillHead as a "
				+ "	where  a.company.id=?  and a.billSort=? and a.sysType=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(fptInOutFlag);// 例如：FptInOutFlag.OUT
		parameters.add(fptBusinessType);// 例如：FptBusinessType.FPT_BILL
		if (tradeCode != null) {
			hql += " and a.receiveTradeName=? ";
			parameters.add(tradeCode);
		}
		// if (type == 0) { // 收发货单
		// if (isImport) {
		// parameters.add(FptInOutFlag.OUT);
		// parameters.add(FptBusinessType.FPT_BILL);
		// if (tradeCode != null) {
		// hql += " and a.receiveTradeName=? ";
		// parameters.add(tradeCode);
		// }
		// } else {
		// parameters.add(FptInOutFlag.IN);
		// parameters.add(FptBusinessType.FPT_BILL);
		// if (tradeCode != null) {
		// hql += " and a.issueTradeName=? ";
		// parameters.add(tradeCode);
		// }
		// }
		// } else { // 收退单
		// if (isImport) {
		// parameters.add(FptInOutFlag.OUT);
		// parameters.add(FptBusinessType.FPT_BILL_BACK);
		// if (tradeCode != null) {
		// hql += " and a.receiveTradeName=? ";
		// parameters.add(tradeCode);
		// }
		// } else {
		// parameters.add(FptInOutFlag.IN);
		// parameters.add(FptBusinessType.FPT_BILL_BACK);
		// if (tradeCode != null) {
		// hql += " and a.issueTradeName=? ";
		// parameters.add(tradeCode);
		// }
		// }
		// }
		if (beginDate != null) {
			hql += " and a.createDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.createDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (state != null) {
			hql += " and a.appState=? ";
			parameters.add(state);
		}
		if (appNo != null) {
			hql += " and a.appNo=? ";
			parameters.add(appNo);
		}
		if (scmcoc != null) {
			hql += " and a.customer.id=? ";
			parameters.add(scmcoc.getId());
		}
		return find(hql, parameters.toArray());
	}

	/**
	 * 获得结转所有单据来自进出货标志
	 */
	public List findFptBillHead(String sysType, String inOutFlag,
			Date beginDate, Date endDate, String declareState, String appNo,
			ScmCoc scmcoc) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FptBillHead as a "
				+ "	where  a.company.id=?  and a.billSort=? and a.sysType=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(inOutFlag);
		parameters.add(sysType);
		if (beginDate != null) {
			hql += " and a.createDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.createDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (declareState != null) {
			hql += " and a.appState=? ";
			parameters.add(declareState);
		}
		if (appNo != null) {
			hql += " and a.appNo=? ";
			parameters.add(appNo);
		}
		if (scmcoc != null) {
			hql += " and a.customer.id=? ";
			parameters.add(scmcoc.getId());
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、存在未转报关清单的商品 的单据
	 */
	public List findTransferFactoryBillByScmCocIdToATC(String scmCocId,
			String emsNo) {
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
	 * 根据转厂单头
	 * 
	 * @param emsNo
	 * 
	 * @return List
	 */
	public List findFptBillHeadByCopBillNo(String fptInOutFlag,
			String fptBusinessType, String copTrNo) {
		if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
			return this
					.find("select a from FptBillHead as a "
							+ "	where a.billSort=? and a.company.id=? and a.sysType=? and a.issueCopBillNo=?",
							new Object[] { FptInOutFlag.OUT,
									CommonUtils.getCompany().getId(),
									fptBusinessType, copTrNo });
		} else {
			return this
					.find("select a from FptBillHead as a "
							+ "	where a.billSort=? and a.company.id=? and a.sysType=? and a.receiveCopBillNo=?",
							new Object[] { FptInOutFlag.IN,
									CommonUtils.getCompany().getId(),
									fptBusinessType, copTrNo });
		}

	}

	/**
	 * 判断申请单编号是否存在
	 */
	public List findFptAppHeadAppNo(String fptInOutFlag, String appNo) {
		return this.find("select a from FptAppHead as a "
				+ "	where a.inOutFlag=? and a.company.id=? and a.appNo=? ",
				new Object[] { fptInOutFlag, CommonUtils.getCompany().getId(),
						appNo });
	}

	/**
	 * 查找条件是申报状态的单据
	 * 
	 * @param emsNo
	 *            企业编号
	 * @param declareState
	 *            申报状态
	 * @return
	 */
	public FptBillHead findfptBillHeadByBillNo(String billNo, String billSort) {
		List list = this.find("select a from FptBillHead a where a.company= ?"
				+ " and a.billNo=?  and a.billSort=? ", new Object[] {
				CommonUtils.getCompany(), billNo, billSort });
		if (list != null && list.size() > 0) {
			return (FptBillHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找条件是申报状态的单据
	 * 
	 * @param emsNo
	 *            企业编号
	 * @param declareState
	 *            申报状态
	 * @return
	 */
	public FptBillHead findfptBillHeadByEmsNo(String emsNo,
			String declareState, String billSort, String sysType) {
		List list = this
				.find("select a from FptBillHead a where a.company= ? and issueCopBillNo=?  and a.billSort=? and a.sysType=? and a.appState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								billSort, sysType, declareState });
		if (list != null && list.size() > 0) {
			return (FptBillHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找条件是申报状态的单据明细
	 * 
	 * @param parentId
	 * @return
	 */
	public List findFptBillItemByFptbillheadId(String parentId, String inOutFlag) {
		return this.find(
				"select a from FptBillItem a where a.fptBillHead.id = ? and a.billSort = ? "
						+ " order by a.listNo ", new Object[] { parentId,
						inOutFlag });
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public FptBillHead findFptBillHeadById(String id) {
		List list = this.find(
				"select a from FptBillHead a where a.company= ? and a.id=? ",
				new Object[] { CommonUtils.getCompany(), id });
		if (list.size() > 0) {
			return (FptBillHead) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据数据中心统一编号查找送货或退货单据
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public FptBillHead findFptBillHeadBySeqNo(String seqNo) {
		List list = this
				.find("select a from FptBillHead a where a.company= ? and a.seqNo=? ",
						new Object[] { CommonUtils.getCompany(), seqNo });
		if (list.size() > 0) {
			return (FptBillHead) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找条件是申报状态的单据明细
	 * 
	 * @param parentId
	 * @return
	 */
	public List findFptBillItemStateChanged(String parentId, String inOutFlag) {
		return this.find(
				"select a from FptBillItem a where a.fptBillHead.id = ? "
						+ "  and a.company.id=?  and a.billSort = ? "
						+ " order by a.listNo ", new Object[] { parentId,
						CommonUtils.getCompany().getId(), inOutFlag });
	}

	// /**
	// * 查找条件是申报状态的单据明细
	// *
	// * @param parentId
	// * @return
	// */
	// public List findFptBillDictItemStateChanged(String parentId,
	// String inOutFlag, String sysType) {
	// if (FptInOutFlag.OUT.equals(inOutFlag)
	// && FptBusinessType.FPT_BILL.equals(sysType)
	// || FptInOutFlag.IN.equals(inOutFlag)
	// && FptBusinessType.FPT_BILL_BACK.equals(sysType)) {
	// return this
	// .find("select a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,Sum(a.tradeQty),a.unit.name,Sum(a.qty),a.inEmsNo "
	// + " from FptBillItem a "
	// + " left join a.complex"
	// + " left join a.unit"
	// + " left join a.tradeUnit"
	// + " where a.fptBillHead.id = ?  "
	// + " and a.company.id=? and a.billSort = ? "
	// +
	// " group by a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,a.unit.name,a.inEmsNo"
	// + " order by a.inEmsNo ", new Object[] { parentId,
	// CommonUtils.getCompany().getId(), inOutFlag });
	// } else {
	// return this
	// .find("select a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,Sum(a.tradeQty),a.unit.name,Sum(a.qty),a.inEmsNo,a.outNo "
	// + " from FptBillItem a "
	// + " left join a.complex"
	// + " left join a.unit"
	// + " left join a.tradeUnit"
	// + " where a.fptBillHead.id = ?  "
	// + " and a.company.id=? and a.billSort = ? "
	// +
	// " group by a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,a.unit.name,a.inEmsNo,a.outNo"
	// + " order by a.inEmsNo ", new Object[] { parentId,
	// CommonUtils.getCompany().getId(), inOutFlag });
	// }
	//
	// }
	/**
	 * 查找条件是申报状态的单据明细
	 * 
	 * @param parentId
	 * @return
	 */
	public List findFptBillItemByFptBillheadId(String parentId, String inOutFlag) {
		return this.find(
				"select a from FptBillItem a where a.fptBillHead.id = ? and a.billSort = ? "
						+ " order by a.listNo ", new Object[] { parentId,
						inOutFlag });
	}

	/**
	 * 保存结转单据
	 */
	public FptBillHead saveFptBillHead(FptBillHead obj) {
		this.saveOrUpdate(obj);
		return obj;
	}

	/**
	 * 删除结转单据
	 */
	public void deleteFptBillHead(FptBillHead transferFactoryBill) {
		this.delete(transferFactoryBill);
	}

	/**
	 * 删除结转单据商品信息数据
	 */
	public void deleteFptBillItem(FptBillItem data) {
		this.delete(data);
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public void saveFptBillItem(FptBillItem commInfo) {
		this.saveOrUpdate(commInfo);
	}

	/**
	 * 查询结转单据流水号
	 * 
	 * @return
	 */
	public List findFptBillHeadSerialNumber(String fptInOutFlag) {
		return this
				.find("select a.serialNumber from FptBillHead a where a.company.id=? and a.billSort = ?  order by a.serialNumber desc",
						new Object[] { CommonUtils.getCompany().getId(),
								fptInOutFlag });
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
	public int getMaxFptBillItemListNo(FptBillHead head) {
		List list = this
				.find("select max(a.listNo) from FptBillItem as a "
						+ " where a.company.id=? and a.fptBillHead.id = ? and a.billSort=?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), head.getBillSort() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据EMS_NO查找正在执行的单据
	 * 
	 * @return List 是FptBillHead型，合同备案表头
	 */
	public FptBillHead findExingFptBillHeadByEmsNo(String copBillNo,
			String inOutFlag) {
		List<Object> parray = new ArrayList<Object>();
		String hql = "";
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			hql = "select a from FptBillHead a where a.company.id= ? "
					+ " and a.issueCopBillNo=? " + " and a.appState=? ";
		} else {
			hql = "select a from FptBillHead a where a.company.id= ?"
					+ " and a.receiveCopBillNo=? " + " and a.appState=? ";
		}
		parray.add(CommonUtils.getCompany().getId());
		parray.add(copBillNo);
		parray.add(DeclareState.PROCESS_EXE);
		List list = this.find(hql, parray.toArray());
		if (list.size() > 0) {
			return (FptBillHead) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public void saveFptBillItem(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	// /**
	// * 获得当前转厂进出口的商品信息的个数
	// */
	// public int findTransferFactoryCommodityInfoCount(String parentId) {
	// List list = this.find(
	// "select count(*) from TransferFactoryCommodityInfo b "
	// + " where b.transferFactoryBill.id = ? ",
	// new Object[] { parentId });
	// if (list.size() > 0 && list.get(0) != null) {
	// return Integer.parseInt(list.get(0).toString());
	// }
	// return 0;
	// }

	/**
	 * 获得结转单据信息加载子表的记录
	 */
	public List findFptBillItemByParentId(String parentId) {
		return this
				.find("select b from FptBillItem b where b.fptBillHead.id = ?  order by b.listNo",
						new Object[] { parentId });
	}

	// /**
	// * 获得结转单据信息加载子表的记录
	// */
	// public FptBillItem findTransferFactoryCommodityInfoById(
	// String commodityInfoId) {
	// List list = this.find(
	// "select b from TransferFactoryCommodityInfo b where b.id = ? ",
	// new Object[] { commodityInfoId });
	// if (list.isEmpty())
	// return null;
	// else
	// return (FptBillItem) list.get(0);
	// }

	// /**
	// * 获得最大的结转单据号来自进出货标志
	// */
	// public List getMaxTransferFactoryBillNoByImpExpGoodsFlag(
	// boolean impExpGoodsFlag) {
	// return this.find(
	// "select max(a.transferFactoryBillNo) from TransferFactoryBill as a "
	// + " where a.isImpExpGoods=? and a.company.id=?",
	// new Object[] { Boolean.valueOf(impExpGoodsFlag),
	// CommonUtils.getCompany().getId() });
	// }

	// /**
	// * 获得当前转厂进出口的商品信息的个数
	// */
	// public double findTransFactCommInfoAmount(FptBillItem commInfo) {
	// List list = this.find(
	// " select sum(a.transFactAmount) from TransferFactoryCommodityInfo a "
	// + " where a.transferFactoryBill.company.id = ? "
	// + " and a.transferFactoryBill.envelopNo=? "
	// + " and a.seqNum=? and a.id<>? and a.emsNo=? ",
	// new Object[] { CommonUtils.getCompany().getId(),
	// commInfo.getTransferFactoryBill().getAppNo(),
	// commInfo.getListNo(), commInfo.getId(),
	// commInfo.getEmsNo() });
	// if (list.size() == 0 || list.get(0) == null) {
	// return 0.0;
	// }
	// return Double.parseDouble(list.get(0).toString());
	// }

	/**
	 * 保存转厂初始化单据
	 */
	public void saveTransferFactoryInitBill(FptInitBill transferFactoryInitBill) {
		this.saveOrUpdate(transferFactoryInitBill);
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	public void saveTransferFactoryInitBillCommodityInfo(
			FptInitBillItem transferFactoryInitBillCommodityInfo) {
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
			FptInitBill transferFactoryInitBill) {
		this.delete(transferFactoryInitBill);
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	public void deleteTransferFactoryInitBillCommodityInfo(
			FptInitBillItem transferFactoryInitBillCommodityInfo) {
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
	public FptInitBill findTransferFactoryInitBillById(FptInitBill initBill) {
		List list = this
				.find("select a from TransferFactoryInitBill as a where a.company.id=? and a.id=?",
						new Object[] { CommonUtils.getCompany().getId(),
								initBill.getId() });
		return list.size() < 1 ? null : (FptInitBill) list.get(0);
	}

	/**
	 * 获取企业需要下载备案资料（FptDownData）
	 * 
	 * @param initBill
	 * @return
	 */
	public List findRecordationDataDownLoad(String downLoadState,
			String fptInOutFlag) {
		System.out.println(" download type:" + downLoadState);
		System.out.println(" inOutFlag:" + fptInOutFlag);
		List<Object> peramerets = new ArrayList<Object>();
		String hsql = "select a from FptDownData as a where a.company.id=?"
				+ "and a.downLoadState =? and inOutFlag =?";
		peramerets.add(CommonUtils.getCompany().getId());
		peramerets.add(downLoadState);
		peramerets.add(fptInOutFlag);
		System.out.println("hsql" + hsql);
		return this.find(hsql, peramerets.toArray());
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
			Date endDate, String fptInOutFlag, String declareState,
			String fptBusinessType, String code, String name, Integer seqNum,
			String scmcoc) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		hsql.append(" select a from  FptBillItem  a   where a.company.id =? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql.append(" and a.fptBillHead.issueDate>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.fptBillHead.issueDate<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (declareState != null) {
			hsql.append(" and a.fptBillHead.appState =? ");
			parameters.add(declareState);
		}
		if (fptBusinessType != null) {
			hsql.append(" and a.fptBillHead.sysType =? ");
			parameters.add(fptBusinessType);
		}

		if (code != null && !code.equals("")) {
			hsql.append(" and a.complex.code =? ");
			parameters.add(code);
		}
		if (name != null && !name.equals("")) {
			hsql.append(" and a.commName =? ");
			parameters.add(name);
		}
		if (seqNum != null) {
			hsql.append(" and a.trGno =? ");
			parameters.add(seqNum);
		}

		if (fptInOutFlag != null) {
			hsql.append(" and a.fptBillHead.billSort=?  ");
			parameters.add(fptInOutFlag);
			if (fptInOutFlag.equals(FptInOutFlag.IN)) {
				if (scmcoc != null && !scmcoc.trim().equals("")) {
					hsql.append(" and a.fptBillHead.issueTradeName=?  ");
					parameters.add(scmcoc);
				}
			} else if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
				if (scmcoc != null && !scmcoc.trim().equals("")) {
					hsql.append(" and a.fptBillHead.receiveTradeName=?  ");
					parameters.add(scmcoc);
				}
			}
		}
		System.out.println(hsql);
		return this.find(hsql.toString(), parameters.toArray());
	}

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
				+ " a.baseCustomsDeclaration.company.name ";
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
		hsql.append(" group by a.seqNum,a.emsNo,a.transferFactoryInitBill.envelopNo,"
				+ "a.transferFactoryInitBill.scmCoc.name ");
		return this.find(hsql.toString(), parameters.toArray());
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
	 * 有数据已转关封在关封申请单中
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(
			FptAppHead c) {
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
	public boolean hasDataCustomsDeclarationByTransFactBill(FptBillHead t) {
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
			FptBillHead t) {
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
	public List findMakeFptBillFromCasBillByFptBillItemId(String fptBillItemId) {
		return this.find(
				"select m from MakeFptBillFromCasBill m where m.company.id = ? "
						+ " and m.fptBillItemId = ?   ", new Object[] {
						CommonUtils.getCompany().getId(), fptBillItemId });
	}

	/**
	 * 保存由海关工厂单据生成转厂单据的中间表
	 */
	public void saveMakeTransferFactoryBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeFptBillFromCasBill m = (MakeFptBillFromCasBill) list.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除由海关工厂单据生成转厂单据的中间表
	 */
	public void deleteMakeFptBillFromCasBill(MakeFptBillFromCasBill m) {
		this.delete(m);
	}

	// /**
	// * 查找结转单据生成报关清单时的中间表信息来自关封相关的数据项
	// *
	// * @param c
	// * @return
	// */
	// public List findMakeApplyToCustomsInfoByCustomsEnvelopBill(
	// CustomsEnvelopBill c) {
	// return this
	// .find(
	// "select m from MakeApplyToCustomsInfo m "
	// + " where m.customsEnvelopCommodityInfo.id in "
	// + " (select e.id from CustomsEnvelopCommodityInfo e where
	// e.customsEnvelopBill.id = ?)",
	// new Object[] { c });
	// }

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

	public List findTransferFactoryCommodityInfoGroupBySeqNum(String emsNo,
			String scmcocCode, Date beginDate, Date endDate, Integer impExpType) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select a.transferFactoryBill.beginAvailability,"
				+ " a.transferFactoryBill.transferFactoryBillNo,"
				+ " a.transferFactoryBill.memo,a.transferFactoryBill.envelopNo,"
				+ " a.seqNum,a.commName,sum(a.transFactAmount)"
				+ " from TransferFactoryCommodityInfo a where a.company.id = ?"
				+ " and a.emsNo=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(emsNo);
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
				+ "a.seqNum,a.commName ";
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
			String emsNo, String envelopNo, Integer seqNum) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select sum(a.transFactAmount) from TransferFactoryCommodityInfo a"
				+ " where a.company.id = ? and a.emsNo=?"
				+ " and a.transferFactoryBill.envelopNo=? and a.seqNum = ? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(emsNo);
		para.add(envelopNo);
		para.add(seqNum);
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
			String envelopNo, Integer seqNum) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select sum(a.noTransferQuantity) from TransferFactoryInitBillCommodityInfo a"
				+ " where a.company.id = ? and a.emsNo=?"
				+ " and a.transferFactoryInitBill.envelopNo=?  and a.seqNum = ? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(emsNo);
		para.add(envelopNo);
		para.add(seqNum);
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
		List list = this.find(hsql, para.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double findCustomsDeclarationAmountByEnvelopNoAndSeqNum(
			Integer projectType, String emsNo, Integer seqNum,
			String scmcocCode, String envelopNo, Date beginDate,
			Integer impExpType) {
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
		hql += " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo =? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(envelopNo);
		// if (scmcocCode != null && !"".equals(scmcocCode)) {
		// hql += " and b.transferFactoryBill.scmCoc.code = ? ";
		// parameters.add(scmcocCode);
		// }
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate <? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}

		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		} else {
			return Double.valueOf(0);
		}
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
						+ " and a.seqNum not in (select e.seqNum "
						+ " from CustomsEnvelopCommodityInfo e"
						+ " where e.customsEnvelopBill.id =? "
						+ " and e.emsNo=? )order by a.seqNum ", new Object[] {
						emsNo, DzscState.EXECUTE,
						CommonUtils.getCompany().getId(), envelopId, emsNo });
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
						+ " and a.seqNum not in (select e.seqNum "
						+ " from CustomsEnvelopCommodityInfo e"
						+ " where e.customsEnvelopBill.id =? "
						+ " and e.emsNo=? ) order by a.seqNum ", new Object[] {
						emsNo, DzscState.EXECUTE,
						CommonUtils.getCompany().getId(), envelopId, emsNo });
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
						+ " and a.seqNum not in (select e.seqNum "
						+ " from CustomsEnvelopCommodityInfo e "
						+ " where e.customsEnvelopBill.id =? "
						+ " and e.emsNo=? ) order by a.seqNum ", new Object[] {
						emsNo, false, DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId(), envelopId, emsNo });
	}

	/**
	 * 查找料件 来自 归并关系
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractExgByEmsNo(String emsNo, String envelopId) {
		return this
				.find("select a from ContractExg a where a.contract.emsNo = ? "
						+ " and ( a.contract.isCancel=? ) and a.contract.declareState=? "
						+ " and a.contract.company.id=? "
						+ " and a.seqNum not in (select e.seqNum "
						+ " from CustomsEnvelopCommodityInfo e "
						+ " where e.customsEnvelopBill.id =? "
						+ " and e.emsNo=? ) order by a.seqNum ", new Object[] {
						emsNo, false, DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId(), envelopId, emsNo });
	}

	/**
	 * 查找料件 来自 归并关系
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractDzscInnerMergeDataByEmsNo(boolean isMaterial,
			String appNo, String isImportGoods) {
		String material = "";
		if (isMaterial) {
			material = MaterielType.MATERIEL;
		} else {
			material = MaterielType.FINISHED_PRODUCT;
		}
		return this
				.find("select b.listNo,a.materiel.ptNo,a.materiel.factoryName,a.materiel.factorySpec,"
						+ " b.trNo,b.codeTs.code,b.name, b.spec,b.unit.name,b.qty ,b.inEmsNo,a.materiel.calUnit,a.materiel.ptNetWeight from DzscInnerMergeData a,FptAppItem b "
						+ " left join  a.materiel "
						+ " left join  b.unit "
						+ " where a.dzscTenInnerMerge.tenSeqNum=b.trNo "
						+ " and a.imrType=? and b.inOutFlag=? and a.company.id=? and stateMark=? and b.fptAppHead.appNo=? "
						+ "  order by a.materiel.ptNo ", new Object[] {
						material, isImportGoods,
						CommonUtils.getCompany().getId(), "2", appNo });
	}

	/**
	 * 查找料件 来自 归并关系
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractInnerMergeDataByEmsNo(boolean isMaterial,
			String appNo, String isImportGoods) {
		String material = "";
		if (isMaterial) {
			material = MaterielType.MATERIEL;
		} else {
			material = MaterielType.FINISHED_PRODUCT;
		}
		return this
				.find("select b.listNo,a.materiel.ptNo,a.materiel.factoryName,a.materiel.factorySpec,"
						+ " b.trNo,b.codeTs.code,b.name, b.spec,b.unit.name,b.qty,b.inEmsNo,a.materiel.calUnit,a.materiel.ptNetWeight  from InnerMergeData a,FptAppItem b  "
						+ " left join  a.materiel "
						+ " left join  b.unit "
						+ " where  a.hsAfterTenMemoNo=b.trNo and b.inOutFlag=? and a.imrType=? and a.company.id=? and a.isExistMerger=? and b.fptAppHead.appNo=?"
						+ "  order by a.materiel.ptNo ", new Object[] {
						isImportGoods, material,
						CommonUtils.getCompany().getId(), true, appNo });
	}

	/**
	 * 查找合同料件 来自 手册编号
	 * 
	 * @param parentId
	 * @return
	 */
	public List findContractBcsInnerMergeByEmsNo(boolean isMaterial,
			String appNo, String isImportGoods) {
		String material = "";
		if (isMaterial) {
			material = MaterielType.MATERIEL;
		} else {
			material = MaterielType.FINISHED_PRODUCT;
		}
		return this
				.find("select  distinct b.listNo,a.materiel.ptNo,a.materiel.factoryName,a.materiel.factorySpec,"
						+ " b.trNo,b.codeTs.code,b.name, b.spec,b.unit.name,b.qty,b.inEmsNo,a.materiel.calUnit,a.materiel.ptNetWeight  from BcsInnerMerge a,FptAppItem b  "
						+ " left join  a.materiel "
						+ " left join  b.unit "
						+ " where a.bcsTenInnerMerge.seqNum=b.trNo and b.inOutFlag=? and a.materielType=? and a.company.id=? and b.fptAppHead.appNo=?  "
						+ "  order by a.materiel.ptNo ", new Object[] {
						isImportGoods, material,
						CommonUtils.getCompany().getId(), appNo });
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
			String envelopCode, Date beginDate, Date endDate) {
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
				+ " and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.customsEnvelopBillNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(seqNum);
		parameters.add(envelopCode);
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

	// /**
	// * 根据单据号查询关封商品明细
	// *
	// * @param customsEnvelopBillCode
	// * @return
	// */
	// public List findTempTransferFactoryCommInfo(String
	// customsEnvelopBillCode) {
	// return this.find("select a from CustomsEnvelopCommodityInfo a "
	// + " where a.customsEnvelopBill.customsEnvelopBillNo = ? "
	// + " and a.company.id=? ", new Object[] {
	// customsEnvelopBillCode, CommonUtils.getCompany().getId() });
	// }

	// /**
	// * 根据单据号查询关封商品明细
	// *
	// * @param customsEnvelopBillCode
	// * @return
	// */
	// public List findTransferFactoryCommInfoByEnvelpCode(
	// String customsEnvelopBillCode) {
	// return this.find("select a from CustomsEnvelopCommodityInfo a "
	// + " where a.customsEnvelopBill.customsEnvelopBillNo = ? "
	// + " and a.company.id=? ", new Object[] {
	// customsEnvelopBillCode, CommonUtils.getCompany().getId() });
	// }

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
		if (projectType == ProjectType.BCUS) {
			hql = " select max(a.serialNumber) from CustomsDeclarationCommInfo as a ";
		} else if (projectType == ProjectType.BCS) {
			hql = " select max(a.serialNumber) from BcsCustomsDeclarationCommInfo as a  ";
		} else if (projectType == ProjectType.DZSC) {
			hql = " select max(a.serialNumber) from DzscCustomsDeclarationCommInfo as a  ";
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
	 * 查询未报关单的结转单据明细
	 * 
	 */
	public List findTransFactBillMakeCustomsDeclarationDetail(FptBillHead headId) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from FptBillItem a where a.fptBillHead=? and a.company.id=? ";
		parameters.add(headId);
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param parentList
	 *            临时的进出口申请单
	 * @return 非结转进出口商品信息
	 */
	public List findFptBillCommodityInfoByParent(List parentList,
			String inOutFlag) {
		String hsql = "select a  from FptBillItem a "
				+ " where ( a.isCustomsDeclaration = ? or a.isCustomsDeclaration is null ) and a.billSort=?  ";
		String condition = "";
		List objs = new ArrayList();
		objs.add(new Boolean(false));
		objs.add(inOutFlag);
		for (int i = 0; i < parentList.size(); i++) {
			if (parentList.get(i) instanceof FptBillHead) {
				FptBillHead t = (FptBillHead) parentList.get(i);
				if (t.getIsSelected() == null || t.getIsSelected() == false) {
					continue;
				}
				if (i == 0) {
					condition += "  a.fptBillHead.id = ? ";
				} else {
					condition += "  or  a.fptBillHead.id = ? ";
				}
				objs.add(t.getId());
			}
		}
		if (condition.equals("") == false) {
			hsql += " and (" + condition + " )";
		}
		return this.find(hsql, objs.toArray());
	}

	/**
	 * 根据fptAppItem 的项号(归并序号)/sysType:收发货单2，退货单3来进行数量合并/申请编号 a.fptBillHead.appNo
	 * 
	 * @param headItemList
	 * @param inOutFlag
	 *            转入转出标志
	 * @return
	 */
	public List findFptBillCommodityInfoByHeadItem(List headItemList,
			String sysType, String inOutFlag) {
		StringBuilder hsql = new StringBuilder();
		hsql.append("select a  from FptBillItem a ")
				.append(" where ( a.isCustomsDeclaration = ? "
						+ "or a.isCustomsDeclaration is null ) and a.billSort=?  ")
				.append(" and a.fptBillHead.sysType=? and a.fptBillHead.appNo=?");

		List objs = new ArrayList();
		objs.add(new Boolean(false));
		objs.add(inOutFlag);
		String appNO = "";
		StringBuilder stb = new StringBuilder();
		if (headItemList.size() > 0) {
			for (int i = 0; i < headItemList.size(); i++) {
				if (headItemList.get(i) instanceof FptBillItem) {
					FptBillItem t = (FptBillItem) headItemList.get(i);
					if (t.getIsSelected() == null || t.getIsSelected() == false) {
						continue;
					}
					appNO = t.getFptBillHead().getAppNo();
					break;
				}
			}
			for (int i = 0; i < headItemList.size(); i++) {
				if (headItemList.get(i) instanceof FptBillItem) {
					FptBillItem t = (FptBillItem) headItemList.get(i);
					if (t.getIsSelected() == null || t.getIsSelected() == false) {
						continue;
					}
					if (t.getFptBillHead().getSysType().equals(sysType)) {
						stb.append("'" + t.getId() + "',");
					}
				}
			}
		}
		if (stb.length() > 0) {
			String stbString = stb.substring(0, stb.length() - 1);
			hsql.append(" and a.id in (" + stbString + ")");
		}
		objs.add(sysType);
		objs.add(appNO);

		return this.find(hsql.toString(), objs.toArray());
	}

	//
	// /** 获得最大的数值除掉新增的值 */
	// public Integer findMaxValueByFptBillItemExceptAdd(String parentId,
	// String inOutFlag) {
	// List list = this
	// .find("select max(a.listNo) from FptBillItem as a "
	// + "	where a.fptBillHead.id = ? and a.billSort=? and a.company.id=? ", new
	// Object[] { parentId,
	// inOutFlag, CommonUtils.getCompany().getId() });
	//
	// if (list.size() <= 0 || list.get(0) == null) {
	// return 0;
	// } else {
	// return (Integer) list.get(0);
	// }
	// }

	/** 获得最大的数值 */
	public Integer findMaxValueByFptBillItem(String parentId, String inOutFlag) {
		List list = this
				.find("select max(a.listNo) from FptBillItem as a "
						+ "	where a.fptBillHead.id = ? and a.billSort=? and a.company.id=?",
						new Object[] { parentId, inOutFlag,
								CommonUtils.getCompany().getId() });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}

	/**
	 * 查询未转报关单的结转单据
	 * 
	 * @param isImport
	 * @return
	 */
	public List findFptBillMakeCustomsDeclaration(String inOutFlag,
			String appNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select distinct a.fptBillHead "
				+ " from FptBillItem a "
				+ " where (a.isCustomsDeclaration=? or a.isCustomsDeclaration is null)"
				+ " and a.fptBillHead.billSort=? "
				+ " and a.fptBillHead.sysType in (?,?) "
				+ " and a.company.id=? "
				+ " and a.fptBillHead.appState = ? "
				+ " and a.fptBillHead.appNo = ? "
				+ " and isnull(a.fptBillHead.makeCustomsDeclarationCode,'') ='' ";
		parameters.add(false);
		parameters.add(inOutFlag);
		parameters.add(FptBusinessType.FPT_BILL);
		parameters.add(FptBusinessType.FPT_BILL_BACK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(appNo);
		// parameters.add(CommonUtils.convertStrToDate(ds));
		// parameters.add(CommonUtils.convertStrToDate(de));
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			if (beginDate != null) {
				hql += " and a.fptBillHead.issueIsDeclaDate  >= ? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hql += " and a.fptBillHead.issueIsDeclaDate <= ? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
		} else if (FptInOutFlag.IN.equals(inOutFlag)) {
			if (beginDate != null) {
				hql += " and a.fptBillHead.receiveIsDeclaDate >= ?  ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}
			if (endDate != null) {
				hql += " and a.fptBillHead.receiveIsDeclaDate <= ? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
		}
		// System.out.println("billsort:"+isImport+" sysType:"+sysType+"
		// backtype:"+backSysType+" id:"+CommonUtils.getCompany().getId()+"
		// appstate:"+DeclareState.PROCESS_EXE+" appnp:"+appNo);
		// System.out.println(hql);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询追加报关单结转单生成报关单时所有报关单
	 * 
	 * @param request
	 * @param flag
	 * @return
	 */

	public List findCustomsDeclarationByImpExpFlag(int flag, Integer projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCS:
			hql += " select a from BcsCustomsDeclaration a ";
			break;
		case ProjectType.BCUS:
			hql += " select a from CustomsDeclaration a ";
			break;
		case ProjectType.DZSC:
			hql += " select a from DzscCustomsDeclaration a ";
			break;
		}
		hql += " where  a.company.id= ? "
				+ "  and  a.impExpFlag =?   and  ( a.effective=?  or  a.effective  is null ) "
				+ "  and a.impExpType in(?,?)  order  by   a.emsHeadH2k,a.serialNumber ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				flag, new Boolean(false), ImpExpType.TRANSFER_FACTORY_EXPORT,
				ImpExpType.TRANSFER_FACTORY_IMPORT });
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
		if (produceType == 2) {// 料件
			if (isContractEms) {
				list = this
						.find("select distinct a from ContractImg a ,BcsDictPorImg b "
								+ " where a.credenceNo=b.seqNum "
								+ " and a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo "
								+ " and b.innerMergeSeqNum=? "
								+ " and a.company.id=? and a.contract.emsNo=?"
								+ " and a.contract.declareState=? "
								+ " and b.dictPorHead.declareState=? ",
								new Object[] { innerMergeSeqNum,
										CommonUtils.getCompany().getId(),
										emsNo, DeclareState.PROCESS_EXE,
										DeclareState.PROCESS_EXE });
			} else {
				list = this.find(
						"select a from ContractImg a where a.credenceNo=? "
								+ " and a.company.id=? and a.contract.emsNo=?"
								+ " and a.contract.declareState=? ",
						new Object[] { innerMergeSeqNum,
								CommonUtils.getCompany().getId(), emsNo,
								DeclareState.PROCESS_EXE });
			}
		} else if (produceType == 0) {// 成品
			if (isContractEms) {
				list = this
						.find("select distinct a from ContractExg a ,BcsDictPorExg b "
								+ " where a.credenceNo=b.seqNum "
								+ " and a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo "
								+ " and b.innerMergeSeqNum=? "
								+ " and a.company.id=? and a.contract.emsNo=?"
								+ " and a.contract.declareState=? "
								+ " and b.dictPorHead.declareState=? ",
								new Object[] { innerMergeSeqNum,
										CommonUtils.getCompany().getId(),
										emsNo, DeclareState.PROCESS_EXE,
										DeclareState.PROCESS_EXE });
			} else {
				list = this.find(
						"select a from ContractExg a where a.credenceNo=? "
								+ " and a.company.id=? and a.contract.emsNo=?"
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
							+ " and a.company.id=? and a.dzscEmsPorHead.emsNo=?"
							+ " and a.dzscEmsPorHead.declareState=? ",
							new Object[] { innerMergeSeqNum,
									CommonUtils.getCompany().getId(), emsNo,
									DzscState.EXECUTE });
		} else if (produceType == 0) {
			list = this
					.find("select a from DzscEmsExgBill a where a.tenSeqNum=? "
							+ " and a.company.id=? and a.dzscEmsPorHead.emsNo=?"
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

	// public List findCustomsEnvelopCommodityInfoBySomCoc(
	// CustomsEnvelopBill customsEnvelopBill, Boolean isCustomer) {
	// Boolean tempisCustomer = !(isCustomer == null ? false : isCustomer
	// .booleanValue());
	// return this.find(" select a from CustomsEnvelopCommodityInfo a "
	// + "where a.customsEnvelopBill=? and a.company.id=? "
	// + "and a.customsEnvelopBill.isImpExpGoods=?", new Object[] {
	// customsEnvelopBill, CommonUtils.getCompany().getId(),
	// tempisCustomer });
	//
	// }
	public List findScmManuFactory(String paramScoId) // 转厂客户供应商
	{
		return this.find("select a from ScmCoc a where  "
				+ "  a.company.id = ? and a.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), paramScoId });
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

	// public List findTransferFactoryCommodityInfoByBillNo(
	// String customsEnvelopBillNo, Integer seqNum, String code) {
	// return this.find(" select a from TransferFactoryCommodityInfo a "
	// + "where a.transferFactoryBill.envelopNo=? "
	// + "and a.company.id=? and a.seqNum=? and a.complex.code =?",
	// new Object[] { customsEnvelopBillNo,
	// CommonUtils.getCompany().getId(), seqNum, code });
	// }

	// public List findCustomsEnvelopBillByNotEndCaseAndScmCoc(ScmCoc scmCoc,
	// Boolean isCustomer) {
	// boolean isTempCustomer = !(isCustomer == null ? false : isCustomer
	// .booleanValue());
	// return this.find(
	// " select a.customsEnvelopBillNo from CustomsEnvelopBill a"
	// + " where a.isEndCase=? and a.company.id=? "
	// + "and a.scmCoc =? and a.isImpExpGoods=?",
	// new Object[] { false, CommonUtils.getCompany().getId(), scmCoc,
	// isTempCustomer });
	//
	// }

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
	 * @param appNo
	 * @return
	 */
	public List findFptAppEmsNo(String appNo) {
		return this.find(" select distinct a.inEmsNo from FptAppItem  a "
				+ "where a.fptAppHead.appNo=? "
				+ "and a.company.id=? order by a.inEmsNo ", new Object[] {
				appNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询结转单据中是否有商品信息
	 * 
	 * @param request
	 * @param parentId
	 * @return
	 */
	public List findFptBillItemCommodityInfo(String parentId, String inOutFlag) {
		return this
				.find("select b from FptBillItem b where b.fptBillHead.id = ? and b.billSort=? order by b.listNo ",
						new Object[] { parentId, inOutFlag });
	}

	//
	// public List findSumFptBillItemCommodityInfo(String parentId,
	// String inOutFlag, String sysType) {
	// if (FptInOutFlag.OUT.equals(inOutFlag)
	// && FptBusinessType.FPT_BILL.equals(sysType)
	// || FptInOutFlag.IN.equals(inOutFlag)
	// && FptBusinessType.FPT_BILL_BACK.equals(sysType)) {
	// return this
	// .find("select a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,Sum(a.tradeQty),a.unit.name,Sum(a.qty),a.inEmsNo "
	// + " from FptBillItem a "
	// + " left join a.complex"
	// + " left join a.unit"
	// + " left join a.tradeUnit"
	// + " where a.fptBillHead.id = ? and a.billSort=? "
	// +
	// " group by a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,a.unit.name,a.inEmsNo"
	// + " order by a.inEmsNo", new Object[] { parentId,
	// inOutFlag });
	// } else {
	// return this
	// .find("select a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,Sum(a.tradeQty),a.unit.name,Sum(a.qty),a.inEmsNo ,a.outNo"
	// + " from FptBillItem a "
	// + " left join a.complex"
	// + " left join a.unit"
	// + " left join a.tradeUnit"
	// + " where a.fptBillHead.id = ? and a.billSort=? "
	// +
	// " group by a.appGNo,a.trGno,a.complex.code,a.commName,a.commSpec,a.tradeUnit.name,a.unit.name,a.inEmsNo,a.outNo"
	// + " order by a.inEmsNo", new Object[] { parentId,
	// inOutFlag });
	// }
	//
	// }

	/**
	 * 用于结转单生成报关单
	 */
	public List findSumAfterFptBillItemCommodityInfo(FptBillItem fpt) {
		System.out.println(fpt.getFptBillHead().getAppNo() + "    trgno:"
				+ fpt.getTrGno() + "   comId:"
				+ CommonUtils.getCompany().getId());
		return this
				.find(" select a.trGno,a.complex.code,a.commName,a.commSpec,a.unit.name,a.qty,a.fptBillHead.sysType,a.fptBillHead.appNo "
						+ " from FptBillItem a "
						+ " left join a.complex"
						+ " left join a.unit"
						+ " where a.fptBillHead.appNo = ?  and a.trGno = ? and a.company.id=?  "
						+ " order by a.trGno ", new Object[] {
						fpt.getFptBillHead().getAppNo(), fpt.getTrGno(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 用于更新结转单
	 */
	public List findFptBillItemCommodityInfo(FptBillItem item) {
		return this
				.find(" select a  from FptBillItem a  where a.fptBillHead.appNo = ?  and a.trGno = ? and a.company.id=?  ",
						new Object[] { item.getFptBillHead().getAppNo(),
								item.getTrGno(),
								CommonUtils.getCompany().getId() });
	}

	// /**
	// * 获取进出货转厂单据中申报状态为正在执行的数据
	// *
	// * @param parentId
	// * @return
	// */
	//
	// public List findFptBillHeadForCancel() {
	// return this.find("select b from FptBillHead b where b.appState = ? "
	// + " and (b.isCancel=? or b.isCancel is null) "
	// + " and b.company.id=? and b.seqNo not in "
	// + "(select c.seqNo from FptCancelBill c where c.company.id=?)"
	// + " and ((b.sysType=? and b.billSort=?) "
	// + "       or (b.sysType=? and b.billSort=?))", new Object[] {
	// DeclareState.PROCESS_EXE, false,
	// CommonUtils.getCompany().getId(),
	// CommonUtils.getCompany().getId(), FptBusinessType.FPT_BILL,
	// FptInOutFlag.OUT, FptBusinessType.FPT_BILL_BACK,
	// FptInOutFlag.IN });
	// }

	/**
	 * 获取进出货转厂单据中申报状态为 正在执行、未撤销的单据
	 * 
	 * @param parentId
	 * @return
	 */

	public List findFptBillHeadForCancel(String fptBusinessType,
			String fptInOutFlag) {
		return this
				.find("select b from FptBillHead b where b.appState = ? "
						+ " and b.company.id=? and b.seqNo not in "
						+ " (select c.seqNo from FptCancelBill c where c.company.id=? and c.sysType=? and c.billSort=?)"
						+ " and b.sysType=? and b.billSort=? ", new Object[] {
						DeclareState.PROCESS_EXE,
						CommonUtils.getCompany().getId(),
						CommonUtils.getCompany().getId(), fptBusinessType,
						fptInOutFlag, fptBusinessType, fptInOutFlag });
	}

	/**
	 * 存储企业需要下载备案资料（RecordationDataDownLoad）
	 */
	public void saveFptDownData(FptDownData fptDownData) {
		this.saveOrUpdate(fptDownData);
	}

	/**
	 * 存储进出货转厂撤消单资料
	 */
	public void saveFptCancelBill(FptCancelBill fptCanelBill) {
		this.saveOrUpdate(fptCanelBill);
	}

	/**
	 * 删除备案下载资料
	 */
	public void deleteFptDownData(FptDownData fptDownData) {
		this.delete(fptDownData);
	}

	/**
	 * 删除进出货转厂撤消资料
	 */
	public void deleteFptCancelBill(FptCancelBill fptCanelBill) {
		this.delete(fptCanelBill);
	}

	/**
	 * 查询订单（条件过滤）
	 */
	public List findCustomOrderForToFptAppHead(List customOrders) {
		List para = new ArrayList();
		if (customOrders == null) {
			return new ArrayList();
		}
		String hsql = "  select  a  from CustomOrderDetail a "
				+ "  where  a.amount>0  and a.company.id=? ";
		para.add(CommonUtils.getCompany().getId());
		String contion = "";
		for (int i = 0; i < customOrders.size(); i++) {
			if (i == 0) {
				contion += " and  a.customOrder.id  in ( ? ";
				CustomOrder cms = (CustomOrder) customOrders.get(i);
				para.add(cms.getId());
			}
			if (i != 0 && (i != (customOrders.size() - 1))) {
				contion += " , ? ";
				CustomOrder cms = (CustomOrder) customOrders.get(i);
				para.add(cms.getId());
			}
			if (i == customOrders.size() - 1) {
				if (i == 0) {
					contion += "  ) ";
				} else {
					contion += " , ?  ) ";
					CustomOrder cms = (CustomOrder) customOrders.get(i);
					para.add(cms.getId());
				}
			}
		}
		hsql += contion;
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查询订单（条件过滤）
	 */
	public List findCustomOrderByIfok() {
		List para = new ArrayList();
		String hsql = "  select  a  from CustomOrder a    where a.ifok=?  "
				+ "   and a.company.id=? ";
		para.add(true);
		para.add(CommonUtils.getCompany().getId());
		return this.find(hsql, para.toArray());
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是RecordationDataDownLoad型，合同备案表头
	 */
	public FptDownData findFptDownDataById(String id) {
		List list = this.find(
				"select a from FptDownData a where a.company= ? and a.id=? ",
				new Object[] { CommonUtils.getCompany(), id });
		if (list.size() > 0) {
			return (FptDownData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 找出插销单据表里所有的信息
	 * 
	 * @param initBill
	 * @return
	 */
	public List findFptCancelBill(String sysType, String billSort) {
		// String hql =
		// "select a from FptCancelBill as a where a.company.id=? and a.sysType=? and a.billSort=? ";
		String hql = "  select a					" + " from FptCancelBill as a		"
				+ " where a.company.id=? 		" + "   and a.sysType=? 			"
				+ "   and a.billSort=? 			";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), sysType, billSort });
		return list;
	}

	/**
	 * 获得正在执行的合同的料件或成品 (bcs)
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcsContractDetailByProcessExe(String parentId,
			String emsNo, String inOutFlag, int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		//
		// 进口是 is materiel
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += "select a from  ContractImg a  left join  a.contract b"
					+ "  where a.company= ? " + "	and b.declareState in ( ?)";
		} else {
			hsql += "select a from  ContractExg a  left join  a.contract b"
					+ "  where a.company= ? " + "	and b.declareState in ( ?) ";
		}
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);

		//
		// 是否有
		//
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		//
		// 过滤已存在的
		//

		if (parentId != null && !"".equals(parentId)) {
			if (FptInOutFlag.IN.equals(inOutFlag)) {
				hsql += " and b.emsNo+str(a.seqNum) not in "
						+ "( select c.inEmsNo+str(c.trNo) from FptAppItem c where c.fptAppHead.id=? and c.inEmsNo is not null) ";
				paramters.add(parentId);
			} else {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from FptAppItem c where c.fptAppHead.id=? and c.trNo is not null) ";
				paramters.add(parentId);
			}
		}

		//
		// result collection is sort
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += " order by b.emsNo ,a.seqNum ";
		} else {
			hsql += " order by b.emsNo ,a.seqNum ";
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 获得正在执行的合同的料件或成品 (bcs) 被导入时使用
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcsContractDetailByProcessExeForImport(String parentId,
			String emsNo, String inOutFlag, int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		//
		// 进口是 is materiel
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += "select a from  ContractImg a  left join  a.contract b"
					+ "  where a.company= ? " + "	and b.declareState in ( ?)";
		} else {
			hsql += "select a from  ContractExg a  left join  a.contract b"
					+ "  where a.company= ? " + "	and b.declareState in ( ?) ";
		}
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);

		//
		// 是否有
		//
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		//
		// 过滤已存在的
		//
		/**
		 * if (parentId != null && !"".equals(parentId)) { if
		 * (FptInOutFlag.IN.equals(inOutFlag)) { hsql +=
		 * " and b.emsNo+str(a.seqNum) not in " +
		 * "( select c.inEmsNo+str(c.trNo) from FptAppItem c where c.fptAppHead.id=? and c.inEmsNo is not null) "
		 * ; paramters.add(parentId); } else { hsql += " and a.seqNum not in " +
		 * "( select c.trNo from FptAppItem c where c.fptAppHead.id=? and c.trNo is not null) "
		 * ; paramters.add(parentId); } }
		 **/
		//
		// result collection is sort
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += " order by b.emsNo ,a.seqNum ";
		} else {
			hsql += " order by b.emsNo ,a.seqNum ";
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 获得正在执行的电子手册通关备案里的料件或成品 (dzsc)
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findDzscEmsPorHeadDetailByProcessExe(String parentId,
			String emsNo, String inOutFlag, int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		//
		// 进口是 is materiel
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += "select a from  DzscEmsImgBill a  left join  a.dzscEmsPorHead b"
					+ "  where a.company= ? "
					+ "	and b.declareState in ( ? )  ";
		} else {
			hsql += "select a from  DzscEmsExgBill a  left join  a.dzscEmsPorHead b"
					+ "  where a.company= ? " + "	and b.declareState in ( ? ) ";
		}
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);

		//
		// 是否有
		//
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		//
		// 过滤已存在的
		//
		if (parentId != null && !"".equals(parentId)) {
			if (FptInOutFlag.IN.equals(inOutFlag)) {
				hsql += " and b.emsNo+str(a.seqNum) not in "
						+ "( select c.inEmsNo+str(c.trNo) from FptAppItem c where c.fptAppHead.id=? and c.inEmsNo is not null) ";
				paramters.add(parentId);
			} else {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from FptAppItem c where c.fptAppHead.id=? and c.trNo is not null) ";
				paramters.add(parentId);
			}
		}
		//
		// result collection is sort
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += " order by b.emsNo ,a.seqNum ";
		} else {
			hsql += " order by b.emsNo ,a.seqNum ";
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 获得正在执行的电子帐册里的料件或成品 (Bcus)
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class 进口是 is materiel
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcusEms2kDetailByProcessExe(String parentId, String emsNo,
			String inOutFlag, int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		//
		// 进口是 is materiel
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += "select a from  EmsHeadH2kImg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? and (a.isForbid is null or a.isForbid = ? ) ";
			paramters.add(CommonUtils.getCompany());
			paramters.add(false);
		} else {
			hsql += "select a from  EmsHeadH2kExg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? ";
			paramters.add(CommonUtils.getCompany());
		}

		if ("1".equals((getBpara(BcusParameter.EmsEdiH2kSend)))) {// 分批申报
			hsql += " and a.modifyMark = ? " + " and b.historyState=? ";
			paramters.add(ModifyMarkState.UNCHANGE);
			paramters.add(false);
		} else {
			hsql += " and b.declareState = ? " + " and b.historyState=? ";
			paramters.add(DeclareState.PROCESS_EXE);
			paramters.add(false);
		}
		//
		// 是否有
		//
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		//
		// 过滤已存在的
		//
		if (parentId != null && !"".equals(parentId)) {
			if (FptInOutFlag.IN.equals(inOutFlag)) {
				hsql += " and b.emsNo+str(a.seqNum) not in "
						+ "( select c.inEmsNo+str(c.trNo) from FptAppItem c where c.fptAppHead.id=? and c.inEmsNo is not null) ";
				paramters.add(parentId);
			} else {
				hsql += " and a.seqNum not in "
						+ "( select c.trNo from FptAppItem c where c.fptAppHead.id=? and c.trNo is not null) ";
				paramters.add(parentId);
			}
		}
		//
		// result collection is sort
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += " order by b.emsNo ,a.seqNum ";
		} else {
			hsql += " order by b.emsNo ,a.seqNum ";
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 获得海关编码中的物品
	 * 
	 * @param code
	 * @return
	 */
	public List<Complex> findComplexList(String code) {
		String hql = " SELECT c FROM Complex c WHERE c.code = ? ";
		return this.find(hql, code);
	}

	/**
	 * 获得单位
	 * 
	 * @param name
	 * @return
	 */
	public List<Unit> findUnitList(String name) {
		String hql = " SELECT u FROM Unit u WHERE u.name = ? ";
		return this.find(hql, name);
	}

	/**
	 * 获得单位
	 * 
	 * @param name
	 * @return
	 */
	public List<Unit> findUnitList() {
		String hql = " SELECT u FROM Unit u ";
		return this.find(hql);
	}

	/**
	 * 获得关封下的
	 * 
	 * @param fptAppHead
	 * @return
	 */
	public int deleteFptAppItem(FptAppHead fptAppHead) {
		String hql = " DELETE FptAppItem f WHERE f.fptAppHead.id = ? AND f.company.id = ? ";
		return this.batchUpdateOrDelete(hql, new Object[] { fptAppHead.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得正在执行的电子帐册里的料件或成品 (Bcus) 被导入时使用
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class 进口是 is materiel
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcusEms2kDetailByProcessExeForImport(String parentId,
			String emsNo, String inOutFlag, int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		//
		// 进口是 is materiel
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += "select a from  EmsHeadH2kImg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? and (a.isForbid is null or a.isForbid = ? ) ";
			paramters.add(CommonUtils.getCompany());
			paramters.add(false);
		} else {
			hsql += "select a from  EmsHeadH2kExg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? ";
			paramters.add(CommonUtils.getCompany());
		}

		if ("1".equals((getBpara(BcusParameter.EmsEdiH2kSend)))) {// 分批申报
			hsql += " and a.modifyMark = ? " + " and b.historyState=? ";
			paramters.add(ModifyMarkState.UNCHANGE);
			paramters.add(false);
		} else {
			hsql += " and b.declareState = ? " + " and b.historyState=? ";
			paramters.add(DeclareState.PROCESS_EXE);
			paramters.add(false);
		}
		//
		// 是否有
		//
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		//
		// 过滤已存在的
		//
		/**
		 * if (parentId != null && !"".equals(parentId)) { if
		 * (FptInOutFlag.IN.equals(inOutFlag)) { hsql +=
		 * " and b.emsNo+str(a.seqNum) not in " +
		 * "( select c.inEmsNo+str(c.trNo) from FptAppItem c where c.fptAppHead.id=? and c.inEmsNo is not null) "
		 * ; paramters.add(parentId); } else { hsql += " and a.seqNum not in " +
		 * "( select c.trNo from FptAppItem c where c.fptAppHead.id=? and c.trNo is not null) "
		 * ; paramters.add(parentId); } }
		 **/
		//
		// result collection is sort
		//
		if (FptInOutFlag.IN.equals(inOutFlag)) {
			hsql += " order by b.emsNo ,a.seqNum ";
		} else {
			hsql += " order by b.emsNo ,a.seqNum ";
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/** 返回参数设定 */
	private String getBpara(int type) {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	/**
	 * 各报关模式下，手册商品对应的料号
	 * 
	 * @param projectType
	 * @param materielType
	 * @param emsNo
	 * @return
	 */
	public List findInnerMergeByProjectType(int projectType,
			String materielType, String emsNo) {
		String hsql = "";
		List para = new ArrayList();
		switch (projectType) {
		case ProjectType.BCS:
			if (materielType.equals(MaterielType.MATERIEL)) {
				hsql += " select  distinct a.materiel.ptNo,c from"
						+ " BcsInnerMerge  a , BcsDictPorImg  b, ContractImg c "
						+ " where a.bcsTenInnerMerge.seqNum=b.innerMergeSeqNum and c.credenceNo=b.seqNum"
						+ "  and a.materielType=? and b.dictPorHead.declareState=? "
						+ " and c.contract.declareState=?  and c.contract.emsNo=?"
						+ " and b.company.id=? ";
				para.add(MaterielType.MATERIEL);
				para.add(DeclareState.PROCESS_EXE);
				para.add(DeclareState.PROCESS_EXE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql += " select  distinct a.materiel.ptNo,c from"
						+ " BcsInnerMerge  a , BcsDictPorExg  b, ContractExg c "
						+ " where a.bcsTenInnerMerge.seqNum=b.innerMergeSeqNum and c.credenceNo=b.seqNum"
						+ "  and a.materielType=? and b.dictPorHead.declareState=? "
						+ " and c.contract.declareState=?  and c.contract.emsNo=?"
						+ " and b.company.id=? ";
				para.add(MaterielType.FINISHED_PRODUCT);
				para.add(DeclareState.PROCESS_EXE);
				para.add(DeclareState.PROCESS_EXE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			}
			break;
		case ProjectType.DZSC:
			if (materielType.equals(MaterielType.MATERIEL)) {
				hsql += "  select  distinct a.materiel.ptNo, b from   DzscInnerMergeData   a  ,DzscEmsImgBill  b  "
						+ " where  a.dzscTenInnerMerge.tenSeqNum=b.seqNum "
						+ " and a.imrType=?    and  a.stateMark=? "
						+ " and  b.dzscEmsPorHead.declareState=?  and b.dzscEmsPorHead.emsNo=?"
						+ " and a.company.id=? ";
				para.add(MaterielType.MATERIEL);
				para.add(DzscState.EXECUTE);
				para.add(DzscState.EXECUTE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql += "  select  distinct a.materiel.ptNo,b from   DzscInnerMergeData   a  ,DzscEmsExgBill  b  "
						+ " where  a.dzscTenInnerMerge.tenSeqNum=b.seqNum "
						+ " and a.imrType=?    and  a.stateMark=? "
						+ " and  b.dzscEmsPorHead.declareState=?  and b.dzscEmsPorHead.emsNo=?"
						+ " and a.company.id=? ";
				para.add(MaterielType.FINISHED_PRODUCT);
				para.add(DzscState.EXECUTE);
				para.add(DzscState.EXECUTE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			}
			break;
		case ProjectType.BCUS:
			List list = this
					.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
							new Object[] { BcusParameter.EmsEdiH2kSend,
									CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				BcusParameter obj = (BcusParameter) list.get(0);
				String isEmsH2kSend = obj.getStrValue();
				if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
					if (materielType.equals(MaterielType.MATERIEL)) {
						hsql += " select distinct a.materiel.ptNo  ,c from   InnerMergeData   a  , "
								+ " EmsEdiMergerImgAfter b,EmsHeadH2kImg c "
								+ " where  a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
								+ " and a.imrType=?  "
								+ " and c.emsHeadH2k.emsNo=?"
								+ " and a.company.id=?  ";
						para.add(MaterielType.MATERIEL);
						para.add(emsNo);
						para.add(CommonUtils.getCompany().getId());
					} else if (materielType
							.equals(MaterielType.FINISHED_PRODUCT)) {
						hsql += " select distinct a.materiel.ptNo,c from InnerMergeData a  , "
								+ " EmsEdiMergerExgAfter b,EmsHeadH2kExg c "
								+ " where a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
								+ " and a.imrType=? "
								+ " and c.emsHeadH2k.emsNo=?"
								+ " and a.company.id=?  ";
						para.add(MaterielType.FINISHED_PRODUCT);
						para.add(emsNo);
						para.add(CommonUtils.getCompany().getId());
					}
				} else {
					if (materielType.equals(MaterielType.MATERIEL)) {
						hsql += " select distinct a.materiel.ptNo  ,c from   InnerMergeData   a  , "
								+ " EmsEdiMergerImgAfter b,EmsHeadH2kImg c "
								+ " where  a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
								+ " and a.imrType=?  and b.emsEdiMergerHead.declareState<> ? "
								+ " and c.emsHeadH2k.declareState <>?  and  c.emsHeadH2k.emsNo=?"
								+ " and a.company.id=?  ";
						para.add(MaterielType.MATERIEL);
						para.add(DeclareState.CHANGING_EXE);
						para.add(DeclareState.CHANGING_EXE);
						para.add(emsNo);
						para.add(CommonUtils.getCompany().getId());
					} else if (materielType
							.equals(MaterielType.FINISHED_PRODUCT)) {
						hsql += " select distinct a.materiel.ptNo,c from InnerMergeData a  , "
								+ " EmsEdiMergerExgAfter b,EmsHeadH2kExg c "
								+ " where a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
								+ " and a.imrType=?  and b.emsEdiMergerHead.declareState<> ? "
								+ " and c.emsHeadH2k.declareState <>?  and   c.emsHeadH2k.emsNo=?"
								+ " and a.company.id=?  ";
						para.add(MaterielType.FINISHED_PRODUCT);
						para.add(DeclareState.CHANGING_EXE);
						para.add(DeclareState.CHANGING_EXE);
						para.add(emsNo);
						para.add(CommonUtils.getCompany().getId());
					}
				}
			} else {
				if (materielType.equals(MaterielType.MATERIEL)) {
					hsql += " select distinct a.materiel.ptNo  ,c from   InnerMergeData   a  , "
							+ " EmsEdiMergerImgAfter b,EmsHeadH2kImg c "
							+ " where  a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
							+ " and a.imrType=?  and b.emsEdiMergerHead.declareState<> ? "
							+ " and c.emsHeadH2k.declareState <>?  and  c.emsHeadH2k.emsNo=?"
							+ " and a.company.id=?  ";
					para.add(MaterielType.MATERIEL);
					para.add(DeclareState.CHANGING_EXE);
					para.add(DeclareState.CHANGING_EXE);
					para.add(emsNo);
					para.add(CommonUtils.getCompany().getId());
				} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
					hsql += " select distinct a.materiel.ptNo,c from InnerMergeData a  , "
							+ " EmsEdiMergerExgAfter b,EmsHeadH2kExg c "
							+ " where a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
							+ " and a.imrType=?  and b.emsEdiMergerHead.declareState<> ? "
							+ " and c.emsHeadH2k.declareState <>?  and   c.emsHeadH2k.emsNo=?"
							+ " and a.company.id=?  ";
					para.add(MaterielType.FINISHED_PRODUCT);
					para.add(DeclareState.CHANGING_EXE);
					para.add(DeclareState.CHANGING_EXE);
					para.add(emsNo);
					para.add(CommonUtils.getCompany().getId());
				}
			}
			break;
		default:
			break;
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 各报关模式下，手册商品对应的料号
	 * 
	 * @param projectType
	 * @param materielType
	 * @param emsNo
	 * @return
	 */
	public List findSeqNumByProjectType(int projectType, String materielType,
			String emsNo) {
		String hsql = "";
		List para = new ArrayList();
		switch (projectType) {
		case ProjectType.BCS:
			if (materielType.equals(MaterielType.MATERIEL)) {
				hsql += " select  distinct a.materiel.ptNo,a.bcsTenInnerMerge.seqNum,"
						+ " b.innerMergeSeqNum, b.seqNum,c.credenceNo from"
						+ " BcsInnerMerge  a , BcsDictPorImg  b, ContractImg c "
						+ " where a.bcsTenInnerMerge.seqNum=b.innerMergeSeqNum and c.credenceNo=b.seqNum"
						+ "  and a.materielType=? and b.dictPorHead.declareState=? "
						+ " and c.contract.declareState=?  and c.contract.emsNo=?"
						+ " and b.company.id=? ";
				para.add(MaterielType.MATERIEL);
				para.add(DeclareState.PROCESS_EXE);
				para.add(DeclareState.PROCESS_EXE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql += " select  distinct a.materiel.ptNo,a.bcsTenInnerMerge.seqNum,"
						+ " b.innerMergeSeqNum, b.seqNum,c.credenceNo from"
						+ " BcsInnerMerge  a , BcsDictPorExg  b, ContractExg c "
						+ " where a.bcsTenInnerMerge.seqNum=b.innerMergeSeqNum and c.credenceNo=b.seqNum"
						+ "  and a.materielType=? and b.dictPorHead.declareState=? "
						+ " and c.contract.declareState=?  and c.contract.emsNo=?"
						+ " and b.company.id=? ";
				para.add(MaterielType.FINISHED_PRODUCT);
				para.add(DeclareState.PROCESS_EXE);
				para.add(DeclareState.PROCESS_EXE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			}
			break;
		case ProjectType.DZSC:
			if (materielType.equals(MaterielType.MATERIEL)) {
				hsql += "  select  distinct a.materiel.ptNo,a.dzscTenInnerMerge.tenSeqNum,b.seqNum "
						+ "  from   DzscInnerMergeData   a  ,DzscEmsImgBill  b  "
						+ " where  a.dzscTenInnerMerge.tenSeqNum=b.seqNum "
						+ " and a.imrType=?    and  a.stateMark=? "
						+ " and  b.dzscEmsPorHead.declareState=?  and b.dzscEmsPorHead.emsNo=?"
						+ " and a.company.id=? ";
				para.add(MaterielType.MATERIEL);
				para.add(DzscState.EXECUTE);
				para.add(DzscState.EXECUTE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql += "  select  distinct a.materiel.ptNo,a.dzscTenInnerMerge.tenSeqNum,b.seqNum "
						+ " from   DzscInnerMergeData   a  ,DzscEmsExgBill  b  "
						+ " where  a.dzscTenInnerMerge.tenSeqNum=b.seqNum "
						+ " and a.imrType=?    and  a.stateMark=? "
						+ " and  b.dzscEmsPorHead.declareState=?  and b.dzscEmsPorHead.emsNo=?"
						+ " and a.company.id=? ";
				para.add(MaterielType.FINISHED_PRODUCT);
				para.add(DzscState.EXECUTE);
				para.add(DzscState.EXECUTE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			}
			break;
		case ProjectType.BCUS:
			if (materielType.equals(MaterielType.MATERIEL)) {
				hsql += " select distinct a.materiel.ptNo  ,a.hsAfterTenMemoNo ,b.seqNum,c.seqNum "
						+ " from   InnerMergeData   a  ,  EmsEdiMergerImgAfter b,EmsHeadH2kImg c "
						+ " where  a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
						+ " and a.imrType=?  and b.emsEdiMergerHead.declareState<> ? "
						+ " and c.emsHeadH2k.declareState <>?  and  c.emsHeadH2k.emsNo=?"
						+ " and a.company.id=?  ";
				para.add(MaterielType.MATERIEL);
				para.add(DeclareState.CHANGING_EXE);
				para.add(DeclareState.CHANGING_EXE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql += " select a.materiel.ptNo  ,a.hsAfterTenMemoNo ,b.seqNum,c.seqNum "
						+ " from InnerMergeData a  , "
						+ " EmsEdiMergerExgAfter b,EmsHeadH2kExg c "
						+ " where a.hsAfterTenMemoNo=b.seqNum and c.seqNum =b.seqNum "
						+ " and a.imrType=?  and b.emsEdiMergerHead.declareState<> ? "
						+ " and c.emsHeadH2k.declareState <>?  and   c.emsHeadH2k.emsNo=?"
						+ " and a.company.id=?  ";
				para.add(MaterielType.FINISHED_PRODUCT);
				para.add(DeclareState.CHANGING_EXE);
				para.add(DeclareState.CHANGING_EXE);
				para.add(emsNo);
				para.add(CommonUtils.getCompany().getId());
			}
			break;
		default:
			break;
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 各报关模式查找所有正在执行手册
	 * 
	 * @param projectType
	 * @return
	 */
	public List<BaseEmsHead> findAllEmsExe(int projectType) {
		List<BaseEmsHead> rlist = new ArrayList();
		String hsql = "";
		List para = new ArrayList();
		switch (projectType) {
		case ProjectType.BCS:
			hsql = " select  a from  Contract a  where  a.declareState=? and a.company.id= ? ";
			para.add(DeclareState.PROCESS_EXE);
			para.add(CommonUtils.getCompany().getId());
			break;
		case ProjectType.BCUS:
			List list = this
					.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
							new Object[] { BcusParameter.EmsEdiH2kSend,
									CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				BcusParameter obj = (BcusParameter) list.get(0);
				String isEmsH2kSend = obj.getStrValue();
				if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
					hsql = " select  a from  EmsHeadH2k a where a.company.id= ?  ";
					para.add(CommonUtils.getCompany().getId());
				} else {
					hsql = " select  a from  EmsHeadH2k a where a.company.id= ?  and   a.declareState <> ? ";
					para.add(CommonUtils.getCompany().getId());
					para.add(DeclareState.CHANGING_EXE);
				}
			} else {
				hsql = " select  a from  EmsHeadH2k a where a.company.id= ?  and   a.declareState <> ? ";
				para.add(CommonUtils.getCompany().getId());
				para.add(DeclareState.CHANGING_EXE);
			}
			break;
		case ProjectType.DZSC:
			hsql = " select  a from  DzscEmsPorHead a  where  a.declareState=? and a.company.id= ? ";
			para.add(DzscState.EXECUTE);
			para.add(CommonUtils.getCompany().getId());
			break;
		default:
			break;
		}
		List tlist = this.find(hsql, para.toArray());
		for (int i = 0; i < tlist.size(); i++) {
			BaseEmsHead bh = (BaseEmsHead) tlist.get(i);
			rlist.add(bh);
		}
		return rlist;
	}

	/**
	 * <<<<<<< .mine 查询单据某一属性不重复值
	 * 
	 * @param prop
	 * @return
	 */
	public List findDistinctProperiesFromFptBillItem(String prop) {
		if (prop == null || prop.trim().equals("")) {
			System.out.println("prop==null");
			return new ArrayList();
		}
		return this.find("  select distinct a." + prop
				+ " from FptBillItem a where a.company.id=?  and a." + prop
				+ " is not null ", new Object[] { CommonUtils.getCompany()
				.getId() });
	}

	/**
	 * 查询单据某一属性不重复值
	 * 
	 * @param prop
	 * @return
	 */
	public List findDistinctTranceCustomsDeclaration(Integer projectType,
			String prop) {
		String hsql = "";
		List prar = new ArrayList();
		String tableName = " ";
		switch (projectType) {
		case ProjectType.BCS:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		case ProjectType.DZSC:
			tableName = " DzscCustomsDeclarationCommInfo ";
			break;
		case ProjectType.BCUS:
			tableName = " CustomsDeclarationCommInfo ";
			break;
		default:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		}
		hsql += " select distinct a." + prop + " from " + tableName
				+ " a where a.baseCustomsDeclaration.impExpType in (?,?) "
				+ " and  a.company.id=?   and a." + prop + " is not null ";
		prar.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		prar.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		prar.add(CommonUtils.getCompany().getId());
		System.out.println(hsql);
		return this.find(hsql, prar.toArray());
	}

	public void findFptBillItem(int projectType) {
		String hsql = "";
		List prar = new ArrayList();
		String tableName = " ";
		switch (projectType) {
		case ProjectType.BCS:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		case ProjectType.DZSC:
			tableName = " DzscCustomsDeclarationCommInfo ";
			break;
		case ProjectType.BCUS:
			tableName = " CustomsDeclarationCommInfo ";
			break;
		default:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		}
		hsql += " select b.emsHeadH2k,a.commSerialNo,b.customer.brief.code ,SUM(a.commAmount) from "
				+ tableName
				+ "  a  left join  a.baseCustomsDeclaration  b  "
				+ "group  by   a.commSerialNo,b.customer.brief.code ,b.emsHeadH2k "
				+ "  where   b.impExpType in (?,?)  and b.effective= ? and b.company.id=? ";
		prar.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		prar.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		prar.add(new Boolean(true));
		prar.add(CommonUtils.getCompany().getId());

	}

	public List findFptBillItem(Date beginDate, Date endDate,
			String fptInOutFlag, String declareState, String fptBusinessType,
			String code, String name, Integer seqNum, Integer projectType,
			String scmcocName) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		String scmcoc = "";
		String groupby = "";
		if (fptInOutFlag.equals(FptInOutFlag.IN)) {
			scmcoc = " a.fptBillHead.issueTradeName ";
			hsql.append(" select a.inEmsNo,a.trGno, "
					+ scmcoc
					+ "  , a.fptBillHead.sysType ,SUM(a.qty) from "
					+ " FptBillItem  a left join  a.complex left  join  a.tradeUnit  where  a.company.id =? ");
			groupby = " group by a.inEmsNo,a.trGno,a.fptBillHead.sysType , "
					+ scmcoc;
		} else if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			scmcoc = " a.fptBillHead.receiveTradeName  ";
			hsql.append(" select a.fptBillHead.outEmsNo, a.trGno, "
					+ scmcoc
					+ "  ,a.fptBillHead.sysType ,SUM(a.qty) from "
					+ " FptBillItem  a left join  a.complex left  join  a.tradeUnit where a.company.id =? ");
			groupby = " group by a.fptBillHead.outEmsNo,a.trGno, a.fptBillHead.sysType , "
					+ scmcoc;
		}
		// ---------------------------------------------------------------------

		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql.append(" and a.fptBillHead.issueDate>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.fptBillHead.issueDate<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (declareState != null) {
			hsql.append(" and a.fptBillHead.appState =? ");
			parameters.add(declareState);
		}
		if (fptBusinessType != null) {
			hsql.append(" and a.fptBillHead.sysType =? ");
			parameters.add(fptBusinessType);
		}
		if (code != null && !code.equals("")) {
			hsql.append(" and a.complex.code =? ");
			parameters.add(code);
		}
		if (name != null && !name.equals("")) {
			hsql.append(" and a.commName =? ");
			parameters.add(name);
		}
		if (seqNum != null) {
			hsql.append(" and a.trGno =? ");
			parameters.add(seqNum);
		}
		if (fptInOutFlag != null && !fptInOutFlag.equals("")) {
			hsql.append(" and a.fptBillHead.billSort=?  ");
			parameters.add(fptInOutFlag);
		}
		if (scmcocName != null && !scmcocName.equals("")) {
			hsql.append(" and  " + scmcoc + " =? ");
			parameters.add(scmcocName);
		}
		if (projectType != null && !projectType.equals("")) {
			hsql.append(" and a.fptBillHead.projectType=?  ");
			parameters.add(projectType);
		}
		hsql.append(groupby);
		hsql.append(" order by a.trGno asc , a.fptBillHead.sysType desc");
		System.out.println(hsql);
		return this.find(hsql.toString(), parameters.toArray());
	}

	public List findFptBillItemNameSpec(Date beginDate, Date endDate,
			String fptInOutFlag, String declareState, String fptBusinessType,
			String code, String name, Integer seqNum, Integer projectType,
			String scmcocName) {
		StringBuffer hsql = new StringBuffer("");
		List<Object> parameters = new ArrayList<Object>();
		String scmcoc = "";
		if (fptInOutFlag.equals(FptInOutFlag.IN)) {
			scmcoc = " a.fptBillHead.receiveTradeName ";
			hsql.append(" select a from   FptBillItem  a  where a.company.id =? ");
		} else if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			scmcoc = " a.fptBillHead.issueTradeName  ";
			hsql.append(" select a from   FptBillItem  a  where a.company.id =? ");
		}
		// ---------------------------------------------------------------------

		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql.append(" and a.fptBillHead.issueDate>=? ");
			beginDate = CommonUtils.getBeginDate(beginDate);
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql.append(" and a.fptBillHead.issueDate<=? ");
			endDate = CommonUtils.getEndDate(endDate);
			parameters.add(endDate);
		}
		if (declareState != null) {
			hsql.append(" and a.fptBillHead.appState =? ");
			parameters.add(declareState);
		}
		if (fptBusinessType != null) {
			hsql.append(" and a.fptBillHead.sysType =? ");
			parameters.add(fptBusinessType);
		}
		if (code != null && !code.equals("")) {
			hsql.append(" and a.complex.code =? ");
			parameters.add(code);
		}
		if (name != null && !name.equals("")) {
			hsql.append(" and a.commName =? ");
			parameters.add(name);
		}
		if (seqNum != null) {
			hsql.append(" and a.trGno =? ");
			parameters.add(seqNum);
		}
		if (fptInOutFlag != null && !fptInOutFlag.equals("")) {
			hsql.append(" and a.fptBillHead.billSort=?  ");
			parameters.add(fptInOutFlag);
		}
		if (scmcocName != null && !scmcocName.equals("")) {
			hsql.append(" and  " + scmcoc + " =? ");
			parameters.add(scmcocName);
		}
		if (projectType != null && !projectType.equals("")) {
			hsql.append(" and a.fptBillHead.projectType=?  ");
			parameters.add(projectType);
		}
		System.out.println(hsql);
		return this.find(hsql.toString(), parameters.toArray());
	}

	public List findCustomsDeclarationAmount(Date beginDate, Date endDate,
			int projectType, String fptInOutFlag) {
		String hsql = "";
		List prar = new ArrayList();
		String tableName = " ";
		switch (projectType) {
		case ProjectType.BCS:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		case ProjectType.DZSC:
			tableName = " DzscCustomsDeclarationCommInfo ";
			break;
		case ProjectType.BCUS:
			tableName = " CustomsDeclarationCommInfo ";
			break;
		default:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		}
		hsql += " select b.emsHeadH2k,a.commSerialNo,b.customer.brief.name ,SUM(a.commAmount) from "
				+ tableName
				+ "  a  left join  a.baseCustomsDeclaration  b  "

				+ "  where   b.impExpType = ?  and b.effective= ? and b.company.id=? ";
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			prar.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		} else if (fptInOutFlag.equals(FptInOutFlag.IN)) {
			prar.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		}
		prar.add(new Boolean(true));
		prar.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and b.declarationDate>=? ";
			beginDate = CommonUtils.getBeginDate(beginDate);
			prar.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and b.declarationDate<=? ";
			endDate = CommonUtils.getEndDate(endDate);
			prar.add(endDate);
		}
		hsql += " group  by  b.emsHeadH2k, a.commSerialNo,b.customer.brief.name  ";
		System.out.println(hsql);
		return this.find(hsql, prar.toArray());
	}

	/**
	 * ======= 获得当前转厂进出口的商品信息的个数 ======= 获得当前转厂进出口发收货数量
	 */
	public double findFptBillIssueOrReceiveAmount(FptBillItem commInfo,
			String inOutFlag) {
		List para = new ArrayList();
		String hsql = " select sum(a.qty) from FptBillItem a  "
				+ " where a.fptBillHead.company.id = ? "
				+ " and a.fptBillHead.appNo=? and a.fptBillHead.sysType=? "
				+ " and a.trGno=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(commInfo.getFptBillHead().getAppNo());
		para.add(FptBusinessType.FPT_BILL);
		para.add(commInfo.getTrGno());
		// para.add(commInfo.getId()); and a.id <>?
		if (inOutFlag.equals(FptInOutFlag.OUT)) {
			hsql += "  and a.billSort=? and a.fptBillHead.outEmsNo=? ";
			para.add(FptInOutFlag.OUT);
			para.add(commInfo.getFptBillHead().getOutEmsNo());
			hsql += " and a.fptBillHead.appState!=? ";
			para.add(DeclareState.IS_CANCELED);
		} else {
			hsql += " and a.billSort=?  and a.inEmsNo=?";
			para.add(FptInOutFlag.IN);
			para.add(commInfo.getInEmsNo());
		}
		List list = this.find(hsql, para.toArray());
		if (list.size() == 0 || list.get(0) == null) {
			return 0.0;
		}
		System.out.println("hsql" + hsql);
		return Double.parseDouble(list.get(0).toString());
	}

	/**
	 * 获得当前转厂进出口收退货的数量
	 */
	public double findFptBillReceiveAmount(FptBillItem commInfo,
			String inOutFlag) {
		List para = new ArrayList();
		String hsql = " select sum(a.qty) from FptBillItem a  "
				+ " where a.fptBillHead.company.id = ? "
				+ " and a.fptBillHead.appNo=? and a.fptBillHead.sysType=? "
				+ " and a.trGno=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(commInfo.getFptBillHead().getAppNo());
		para.add(FptBusinessType.FPT_BILL_BACK);
		para.add(commInfo.getTrGno());
		// para.add(commInfo.getId()); and a.id <>?
		if (inOutFlag.equals(FptInOutFlag.OUT)) {
			hsql += " and a.billSort=? and a.fptBillHead.outEmsNo=?";
			para.add(FptInOutFlag.OUT);
			para.add(commInfo.getFptBillHead().getOutEmsNo());
		} else {
			hsql += " and a.billSort=? and a.inEmsNo=?";
			para.add(FptInOutFlag.IN);
			para.add(commInfo.getInEmsNo());
			hsql += " and a.fptBillHead.appState!=?  ";
			para.add(DeclareState.IS_CANCELED);
		}
		List list = this.find(hsql, para.toArray());
		if (list.size() == 0 || list.get(0) == null) {
			return 0.0;
		}
		return Double.parseDouble(list.get(0).toString());
	}

	/**
	 * 取得申请表中的对应备案号的数量
	 */
	public double findFptAppItem(FptBillItem commInfo) {
		List para = new ArrayList();
		String hsql = "select Sum(b.qty) from FptAppItem b  "
				+ "where b.fptAppHead.company.id = ? "
				+ " and b.fptAppHead.appNo=? and b.trNo=? and b.fptAppHead.declareState =? and b.inOutFlag=? and b.fptAppHead.inOutFlag=?";
		para.add(CommonUtils.getCompany().getId());
		para.add(commInfo.getFptBillHead().getAppNo());
		para.add(commInfo.getTrGno());
		para.add(DeclareState.PROCESS_EXE);
		para.add(commInfo.getBillSort());
		para.add(commInfo.getBillSort());
		if (commInfo.getBillSort().equals(FptInOutFlag.OUT)) {
			hsql += " and b.fptAppHead.emsNo=?";
			para.add(commInfo.getFptBillHead().getOutEmsNo());
		} else {
			hsql += " and b.inEmsNo=?";
			para.add(commInfo.getInEmsNo());
		}
		List list = this.find(hsql, para.toArray());
		if (list.size() == 0 || list.get(0) == null) {
			return 0.0;
		}
		return Double.parseDouble(list.get(0).toString());
	}

	/**
	 * >>>>>>> .r14152 查询单据中心单据转结转单据的信息
	 * 
	 * @param isMaterial
	 * @param casBillNo
	 * @param ptNo
	 * @param fptCopNo
	 * @param beginCasDate
	 * @param endCasDate
	 * @param beginFptDate
	 * @param endFptDate
	 * @return
	 */
	public List findMakeFptBillFromCasBillInfo(boolean isMaterial,
			String emsNo, String casBillNo, String ptNo, String fptCopNo,
			String complexCode, Date beginCasDate, Date endCasDate,
			Date beginFptDate, Date endFptDate) {
		String hsql = "";
		List<Object> para = new ArrayList<Object>();
		String billDetailTableName = (isMaterial ? "BillDetailMateriel"
				: "BillDetailProduct");
		hsql += " select a,b,c from MakeFptBillFromCasBill a,FptBillItem b,"
				+ billDetailTableName + " c"
				+ " where a.fptBillItemId=b.id and a.billDetailId=c.id";
		if (casBillNo != null && !"".equals(casBillNo)) {
			hsql += " and c.billMaster.billNo=?";
			para.add(casBillNo);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and c.ptPart=?";
			para.add(ptNo);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			if (isMaterial) {
				hsql += " and b.inEmsNo=?";
			} else {
				hsql += " and b.fptBillHead.outEmsNo=?";
			}
			para.add(emsNo);
		}
		if (beginCasDate != null) {
			hsql += " and c.billMaster.validDate>=?";
			para.add(CommonUtils.getBeginDate(beginCasDate));
		}
		if (endCasDate != null) {
			hsql += " and c.billMaster.validDate<=?";
			para.add(CommonUtils.getBeginDate(endCasDate));
		}
		if (fptCopNo != null && !"".equals(fptCopNo)) {
			if (isMaterial) {
				hsql += " and b.fptBillHead.receiveCopBillNo=?";
			} else {
				hsql += " and b.fptBillHead.issueCopBillNo=?";
			}
			para.add(fptCopNo);
		}
		if (complexCode != null && !"".equals(complexCode)) {
			hsql += " and b.complex.code=?";
			para.add(complexCode);
		}
		if (beginFptDate != null) {
			if (isMaterial) {
				hsql += " and b.fptBillHead.receiveDate>=?";
			} else {
				hsql += " and b.fptBillHead.issueDate>=?";
			}
			para.add(CommonUtils.getBeginDate(beginFptDate));
		}
		if (endFptDate != null) {
			if (isMaterial) {
				hsql += " and b.fptBillHead.receiveDate<=?";
			} else {
				hsql += " and b.fptBillHead.issueDate<=?";
			}
			para.add(CommonUtils.getBeginDate(endFptDate));
		}
		hsql += " and a.company.id=? ";
		para.add(CommonUtils.getCompany().getId());
		return this.find(hsql, para.toArray());
	}

	/**
	 * --------------------------------------------结转单据对应报表查询------------------
	 * -----------
	 */

	/**
	 * 根据深加工转厂单据转成的报关单来对应结转单据报关单号
	 */
	public void reciveOrCancelGgdNum() {

	}

	/**
	 * 根据进出类型、单据类型,查找客户/供应商/合作伙伴资料
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findScmCocsByPara(String impExpFlagCode, String billTypeCode) {
		List parameters = new ArrayList();
		String hsql = " select a from ScmCoc a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpFlagCode != null) {
			if (impExpFlagCode.equals("0")) {// 出口
				if (billTypeCode != null
						&& (billTypeCode.equals("6")
								|| billTypeCode.equals("8")
								|| billTypeCode.equals("9")
								|| billTypeCode.equals("27") || billTypeCode
									.equals("28"))) {// 6为退料出口
					hsql += " and a.isTransferFactoryIn=? ";
					parameters.add(true);
				} else {
					hsql += " and a.isTransferFactoryOut=? ";
					parameters.add(true);
				}

			} else if (impExpFlagCode.equals("1")) {// 进口
				if (billTypeCode != null && billTypeCode.equals("2")) {// 2为退厂返工
					hsql += " and a.isTransferFactoryOut=? ";
					parameters.add(true);
				} else {
					hsql += " and a.isTransferFactoryIn=? ";
					parameters.add(true);
				}

			}
		}

		return this.find(hsql, parameters.toArray());
	}

	public List<TempObject> findBomNoByPara(boolean inOutFlag) {
		String billDetailMateriel = "";
		if (inOutFlag) {
			billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		} else {
			billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		}

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.ptPart,a.ptName from "
				+ billDetailMateriel + " a left join a.billMaster b "
				+ "  where a.company.id= ? "
				+ " and b.isValid=? and a.isTransferFactoryBill=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(true);
		if (inOutFlag) {// 结转进口
			hsql += " and b.billType.code in (?) ";
			parameters.add("1004");
		} else { // 结转出口
			hsql += " and b.billType.code in (?) ";
			parameters.add("2102");
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 取结取对应表中的单据号
	 * 
	 * @param inOutFlag
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @param billNo
	 * @param bomNo
	 * @return
	 */
	public List findBillNoByPara(boolean inOutFlag) {
		String billDetailMateriel = "";
		if (inOutFlag) {
			billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		} else {
			billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		}

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct b  from " + billDetailMateriel
				+ " a left join a.billMaster b " + "  where a.company.id= ? "
				+ " and b.isValid=? and a.isTransferFactoryBill=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(true);
		if (inOutFlag) {// 结转进口
			hsql += " and b.billType.code in (?) ";
			parameters.add("1004");
		} else { // 结转出口
			hsql += " and b.billType.code in (?) ";
			parameters.add("2102");
		}
		return this.find(hsql, parameters.toArray());
	}

	public List findMakeFptBillFromCasBill(boolean inOutFlag, ScmCoc scmCoc,
			Date beginDate, Date endDate, String billNo, String bomNo) {
		String billDetailMateriel = "";
		if (inOutFlag) {
			billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		} else {
			billDetailMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		}

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  a from " + billDetailMateriel
				+ " a left join a.billMaster b " + "  where a.company.id= ? "
				+ " and b.isValid=? and a.isTransferFactoryBill=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(true);
		if (inOutFlag) {// 结转进口
			hsql += " and b.billType.code in (?) ";
			parameters.add("1004");
		} else { // 结转出口
			hsql += " and b.billType.code in (?) ";
			parameters.add("2102");
		}
		if (scmCoc != null) {
			hsql += " and b.scmCoc.code=? ";
			parameters.add(scmCoc.getCode());
		}

		if (beginDate != null) {
			hsql += " and b.validDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and b.validDate<=? ";
			parameters.add(endDate);
		}
		if (billNo != null && !"".equals(billNo)) {
			hsql += " and b.billNo = ? ";
			parameters.add(billNo);
			System.out.println("billNo" + billNo);
		}
		if (bomNo != null && !"".equals(bomNo)) {
			hsql += " and a.ptPart = ? ";
			parameters.add(bomNo);
		}
		hsql += " order by a.billMaster.billNo ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 取得结转对应报表中的申请编号
	 * 
	 * @param inOutFlag
	 * @param appNo
	 * @param beginDate
	 * @param endDate
	 * @param scmCoc
	 * @param emsNo
	 * @return
	 */
	public List findMakeFptBillCustomsDeclaration(boolean inOutFlag,
			String appNo, Date beginDate, Date endDate, ScmCoc scmCoc,
			String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from FptBillItem "
				+ " a left join a.fptBillHead b " + "  where a.company.id= ? "
				+ " and b.appState=? and a.isCustomsDeclaration=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(true);
		if (inOutFlag) {// 结转进口
			hsql += " and b.billSort=?";
			parameters.add(FptInOutFlag.IN);

			if (emsNo != null) {
				hsql += " and a.inEmsNo=? ";
				parameters.add(emsNo);
			}

		} else { // 结转出口
			hsql += " and b.billSort=?";
			parameters.add(FptInOutFlag.OUT);
			if (emsNo != null) {
				hsql += " and b.outEmsNo=?  ";
				parameters.add(emsNo);
			}
		}
		if (scmCoc != null) {
			hsql += " and b.customer=? ";
			parameters.add(scmCoc);
		}
		if (beginDate != null) {
			hsql += " and b.createDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and b.createDate<=? ";
			parameters.add(endDate);
		}
		if (appNo != null) {
			hsql += " and b.appNo =? ";
			parameters.add(appNo);
		}
		System.out.println("hsql=" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找所有清单转报关单的数据
	 * 
	 * @param atcMergeAfterComInfoId
	 *            清单归并前Id
	 * @return
	 */
	public MakeFptBillCustomsDeclaration getCustomsFromMakeListToCustomsId(
			String fptBillHeadAppNo) {
		List parameters = new ArrayList();
		/**
		 * String hsql = " select a from MakeFptBillCustomsDeclaration a " +
		 * " where a.company.id = ? "; //HH2013.11.21 由于实体内没有fptBillHeadAppNo 字段
		 * 所以 移除and a.fptBillHeadAppNo=?
		 **/
		String hsql = " select b.fptBillHead.projectType , "
				+ " a.customsDeclarationCommInId , " + " b.fptBillHead.appNo "
				+ " from MakeFptBillCustomsDeclaration a ,FptBillItem b "
				+ " where a.fptBillItemId = b.id " + " and a.company.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(fptBillHeadAppNo);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty()) {
			return null;
		} else {
			Object[] objs = (Object[]) list.get(0);
			MakeFptBillCustomsDeclaration maks = new MakeFptBillCustomsDeclaration();
			maks.setProjectType((Integer) objs[0]);
			maks.setCustomsDeclarationCommInId((String) objs[1]);
			return maks;
		}
	}

	public BaseCustomsDeclarationCommInfo getCustomsFromMakeListToCustoms(
			String customsCommInfoId, Integer projectType) {
		List parameters = new ArrayList();
		String hsql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hsql += " from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hsql += " from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hsql += " from DzscCustomsDeclarationCommInfo as a";
			break;
		}
		hsql += " where a.company.id = ? and a.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(customsCommInfoId);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((BaseCustomsDeclarationCommInfo) list.get(0));
	}

	/**
	 * 查找所有申请单转清单的数据
	 * 
	 * @param impExpCommodityInfoId
	 *            申请单表体ID
	 * @return
	 */
	public String getfptBillItemString(String billDetail) {
		List parameters = new ArrayList();
		String hsql = " select a from MakeFptBillFromCasBill a "
				+ " where a.company.id = ? and a.billDetailId=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billDetail);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((MakeFptBillFromCasBill) list.get(0)).getFptBillItemId();
	}

	public FptBillItem getfptBillItemId(String fptBillItemId) {
		List parameters = new ArrayList();
		String hsql = " select a from FptBillItem a "
				+ " where a.company.id = ? and a.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(fptBillItemId);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((FptBillItem) list.get(0));
	}

	public List findTransferImpExpCustomsList(Date beginDate, Date endDate,
			Integer impexpType, String emsNo, String scmcoc, int projectType,
			Boolean effec, String code, String name, Integer seqNum) {
		String hsql = "";
		List prar = new ArrayList();
		String tableName = " ";
		switch (projectType) {
		case ProjectType.BCS:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		case ProjectType.DZSC:
			tableName = " DzscCustomsDeclarationCommInfo ";
			break;
		case ProjectType.BCUS:
			tableName = " CustomsDeclarationCommInfo ";
			break;
		default:
			tableName = " BcsCustomsDeclarationCommInfo  ";
			break;
		}
		hsql += " select a from " + tableName
				+ "  a  left join  a.baseCustomsDeclaration  b  "
				+ "  where  b.company.id=? ";
		prar.add(CommonUtils.getCompany().getId());
		if (impexpType != null) {
			if (impexpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				hsql += " and   b.impExpType = ? ";
				prar.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			} else if (impexpType == ImpExpType.TRANSFER_FACTORY_EXPORT) {
				hsql += " and   b.impExpType = ? ";
				prar.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			}
		}
		if (effec != null) {
			if (effec) {
				hsql += " and b.effective= ? ";
				prar.add(new Boolean(true));
			} else {
				hsql += " and b.effective= ? or b.effective is null ";
				prar.add(new Boolean(false));
			}
		}
		if (beginDate != null) {
			hsql += " and b.declarationDate>=? ";
			beginDate = CommonUtils.getBeginDate(beginDate);
			prar.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and b.declarationDate<=? ";
			endDate = CommonUtils.getEndDate(endDate);
			prar.add(endDate);
		}
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and b.emsHeadH2k=? ";
			prar.add(emsNo);
		}
		if (name != null && !name.trim().equals("")) {
			hsql += " and a.commName=? ";
			prar.add(name);
		}
		if (scmcoc != null && !scmcoc.trim().equals("")) {
			hsql += " and b.customer.brief.name =? ";
			prar.add(scmcoc);
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			prar.add(seqNum);
		}
		if (code != null && !code.trim().equals("")) {
			hsql += " and a.complex.code =? ";
			prar.add(code);
		}
		hsql += " order  by   a.commSerialNo   ";
		return this.find(hsql, prar.toArray());

	}

	/**
	 * 取得单据对应表
	 */
	public MakeBillCorrespondingInfoBase findMakeBillCorrespondingInfoBase(
			String billParaId, int impExpType) {
		List parameters = new ArrayList();
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）
		String tableName = BillUtil
				.getMakeBillCorrespondingInfoTableName(materielType);
		String hsql = " select a from " + tableName
				+ " a where a.company.id = ? and a.billDetailId=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billParaId);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((MakeBillCorrespondingInfoBase) list.get(0));
	}

	/**
	 * 抓取结转申请表里面的手册/账册号
	 * 
	 * @param fptAppHead
	 * @param inOutFlag
	 * @return
	 */
	public List findEmsNoFromFptApp(FptAppHead fptAppHead, String inOutFlag) {
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			List parameters = new ArrayList();
			String hsql = " select a.emsNo from FptAppHead a "
					+ " where a.company.id = ? and a.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(fptAppHead.getId());
			return find(hsql, parameters.toArray());
		} else {
			List parameters = new ArrayList();
			String hsql = " select distinct a.inEmsNo from FptAppItem a "
					+ " where a.fptAppHead.company.id = ? "
					+ " and a.fptAppHead.id=? "
					+ " and a.fptAppHead.inOutFlag=a.inOutFlag";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(fptAppHead.getId());
			return find(hsql, parameters.toArray());
		}
	}

	/** ---------------------------------------------邮件----------------* */
	/**
	 * 保存邮件参数
	 */
	public void saveFptEmailParamver(FptEmailParamver fptEmailParamver) {
		this.saveOrUpdate(fptEmailParamver);
	}

	/**
	 * 保存邮件
	 */
	public void saveFptEmail(FptEmail fptEmail) {
		this.saveOrUpdate(fptEmail);
	}

	/**
	 * 删除邮件
	 */
	public void deleteFptEmail(FptEmail fptEmail) {
		this.delete(fptEmail);
	}

	/**
	 * 修改状态
	 */
	public void deleteFptEmailState(FptEmail fptEmail) {
		this.batchUpdateOrDelete(
				"update FptEmail set mailIRType=? where id = ? ", new Object[] {
						fptEmail.emailDelete, fptEmail.getId() });
	}

	/**
	 * 查询邮件设置
	 */
	public FptEmailParamver FindFptEmailParamver() {
		List parameters = new ArrayList();
		String hsql = " select a from FptEmailParamver a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((FptEmailParamver) list.get(0));
	}

	/**
	 * 查询邮件
	 */
	public List FindFptEmail(String mailIRType) {
		List parameters = new ArrayList();
		String hsql = " select a from FptEmail a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (mailIRType != null && !"".equals(mailIRType)) {
			hsql += " and a.mailIRType=? ";
			parameters.add(mailIRType);
		}
		return find(hsql, parameters.toArray());

	}

	/**
	 * 查询收件人地址
	 */
	public List FindFptEmailToAress() {
		List parameters = new ArrayList();
		String hsql = " select distinct a.toEmailAdress from FptEmail a where a.company.id = ? and a.mailIRType=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(FptEmail.reciveEmail);
		return find(hsql, parameters.toArray());

	}

	/**
	 * 查出所邮件中所需要的资料
	 */
	public List findBillToFptEmail(String sysType, String state,
			String inoutFlag, String makeinout) {
		String hsql = "";
		List parameters = new ArrayList();
		if (FptBusinessType.FPT_BILL.equals(sysType)) {
			hsql = "select b from FptBillHead b where b.sysType=? and b.billSort=? and b.appState = ? and b.company.id = ?  ";
			parameters.add(false);
			parameters.add(FptBusinessType.FPT_BILL);
			parameters.add(inoutFlag);
		} else if (FptBusinessType.FPT_BILL_BACK.equals(sysType)) {
			hsql = "select b from FptBillHead b where b.sysType=? and b.billSort=? and b.appState = ? and b.company.id = ?  ";
			parameters.add(false);
			parameters.add(FptBusinessType.FPT_BILL_BACK);
			if ("1".equals(makeinout)) {
				parameters.add(FptInOutFlag.IN);
			} else {
				parameters.add(FptInOutFlag.OUT);
			}
		}
		parameters.add(state);
		parameters.add(CommonUtils.getCompany().getId());
		return find(hsql, parameters.toArray());
	}

	/**
	 * 查出所邮件中所需要的资料
	 */
	public List findAppBillToFptEmail(String state, String inoutFlag) {
		String hsql = "";
		List parameters = new ArrayList();
		hsql = "select b from FptAppHead b where b.declareState = ? and b.company.id = ? and b.inOutFlag=? ";
		parameters.add(state);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(inoutFlag);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 查询收件人地址
	 */
	public FptEmail findFptEmailParamId(String paramId) {
		List parameters = new ArrayList();
		String hsql = " select a from FptEmail a where a.company.id = ? and a.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(paramId);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((FptEmail) list.get(0));

	}

	public FptAppHead findfptAppHeadByCopBill(String CopBillNo, String inOutFlag) {
		List list = null;
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			list = this.find("select a from FptAppHead a where a.company= ?"
					+ " and a.outCopAppNo=?  and a.inOutFlag=? ", new Object[] {
					CommonUtils.getCompany(), CopBillNo, FptInOutFlag.OUT });
		} else {
			list = this.find("select a from FptAppHead a where a.company= ?"
					+ " and a.inCopAppNo=?  and a.inOutFlag=? ", new Object[] {
					CommonUtils.getCompany(), CopBillNo, FptInOutFlag.IN });
		}
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	public FptBillHead findfptBillHeadByCopBill(String CopBillNo,
			String inOutFlag) {
		List list = null;
		if (FptInOutFlag.OUT.equals(inOutFlag)) {
			list = this.find("select a from FptBillHead a where a.company= ?"
					+ " and a.issueCopBillNo=?  and a.billSort=? ",
					new Object[] { CommonUtils.getCompany(), CopBillNo,
							FptInOutFlag.OUT });
		} else {
			list = this.find("select a from FptBillHead a where a.company= ?"
					+ " and a.receiveCopBillNo=?  and a.billSort=? ",
					new Object[] { CommonUtils.getCompany(), CopBillNo,
							FptInOutFlag.IN });
		}
		if (list != null && list.size() > 0) {
			return (FptBillHead) list.get(0);
		}
		return null;
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是RecordationDataDownLoad型，合同备案表头
	 */
	public FptDownData findFptDownDataCopBillNO(String copBillNo) {
		List list = this
				.find("select a from FptDownData a where a.company= ? and a.outCopNo=? ",
						new Object[] { CommonUtils.getCompany(), copBillNo });
		if (list.size() > 0) {
			return (FptDownData) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据客户供应商查找FptComplex
	 */
	public List findFptComplex(ScmCoc scmcoc) {
		List list = this
				.find("select a from FptComplex a where a.company= ? and a.scmCoc=? ",
						new Object[] { CommonUtils.getCompany(), scmcoc });
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 根据客户供应商查找海关商品编码（经过过滤）
	 */
	public List findCustomsComplex(ScmCoc scmcoc) {
		List list = this
				.find(" select a from CustomsComplex a where  a.code not in ("
						+ "  select b.code from FptComplex b where b.company= ? and b.scmCoc=?   )",
						new Object[] { CommonUtils.getCompany(), scmcoc });
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 根据核销关封状态获得关封申请单所有数据
	 * 
	 * @param request
	 * @param scmCoc
	 *            客户/供应商
	 * @param fptInOutFlag
	 *            转厂标志
	 * @param isCollated
	 *            核销关封状态
	 * @return
	 */
	public List findFptAppHeadByIsCollatedAndScmCoc(ScmCoc scmCoc,
			Date strDate, Date endDate, String fptInOutFlag,
			Boolean isCollated, Boolean is_can) {
		List parameters = new ArrayList();
		String hsql = " select a from FptAppHead as a where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (scmCoc != null) {
			hsql += " and a.scmCoc.code = ? ";
			parameters.add(scmCoc.getCode());
		}
		if (fptInOutFlag != null || !"".equals(fptInOutFlag)) {
			hsql += " and a.inOutFlag = ? ";
			parameters.add(fptInOutFlag);
		}
		if (null != strDate) {
			hsql += " and a.outDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(strDate));
		}

		if (null != endDate) {
			hsql += " and a.outDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (is_can != null) {
			if (is_can) {
				hsql += " and a.declareState = ? ";
			} else {
				hsql += " and a.declareState != ? ";
			}
			parameters.add("c");
		}

		if (isCollated != null) {
			if (isCollated) {
				hsql += " and a.isCollated = ? ";
			} else {
				hsql += " and (a.isCollated = ? or a.isCollated = null)";
			}
			parameters.add(isCollated);
		}
		hsql += " order by a.inOutFlag ";
		List list = find(hsql, parameters.toArray());
		if (list.size() > 0) {
			return list;
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 查询是否存在
	 * 
	 * @param seqNo
	 *            电子口岸统一编号
	 * @param AppNo
	 *            申请表编号
	 * @param fptInOutFlag
	 * @return
	 */
	public List<Object[]> findExistsSeqNoOrAppNo(String seqNo, String appNo,
			String fptInOutFlag) {
		List parameters = new ArrayList();
		String hsql = " select a.id, a.seqNo, a.appNo from FptAppHead as a where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());

		if (seqNo != null) {
			hsql += " and a.seqNo = ? ";
			parameters.add(seqNo);
		}

		if (appNo != null) {
			hsql += " and a.appNo = ? ";
			parameters.add(appNo);
		}

		if (fptInOutFlag != null && !"".equals(fptInOutFlag)) {
			hsql += " and a.inOutFlag = ? ";
			parameters.add(fptInOutFlag);
		}

		return find(hsql, parameters.toArray());
	}

	/**
	 * 判断收货单中商品明细“发货序号是否存在”
	 * 
	 * @param head
	 * @param outNo
	 * @param fptInOutFlag
	 * @return
	 */
	public boolean isExistFptBillItemByOutNo(FptBillHead head, Integer outNo,
			String fptInOutFlag) {
		List list = new ArrayList();
		List parameters = new ArrayList();
		String hsql = " select a from FptBillItem as a where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (head != null) {
			hsql += "  and a.fptBillHead.id = ? ";
			parameters.add(head.getId());
		}
		if (outNo != null) {
			hsql += " and a.outNo = ? ";
			parameters.add(outNo);
		}
		if (fptInOutFlag != null || !"".equals(fptInOutFlag)) {
			hsql += " and a.billSort = ? ";
			parameters.add(fptInOutFlag);
		}
		list = find(hsql, parameters.toArray());
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查找是否 被收发货单据使用
	 * 
	 * @param head
	 * @param outNo
	 * @param fptInOutFlag
	 * @return
	 */
	public boolean isExistFptFptBillHeadByAppNo(String appNo,
			String fptInOutFlag) {
		List list = new ArrayList();
		List parameters = new ArrayList();
		String hsql = " select a from FptBillHead as a where a.company.id = ? and a.appNo = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(appNo);

		list = find(hsql, parameters.toArray());
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 分页条件查询合同料件的商品编码
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findImgComplexByContract(int index, int length,
			int projectType, String emsNo, String property, Object value,
			boolean isLike, Boolean isAll) {

		String tableName = "ContractImg";
		if (projectType == ProjectType.BCS) {
			tableName = " ContractImg a left join a.contract b ";
		} else if (projectType == ProjectType.DZSC) {
			tableName = " DzscEmsImgBill a left join a.dzscEmsPorHead b ";
		} else if (projectType == ProjectType.BCUS) {
			tableName = " EmsHeadH2kImg a left join a.emsHeadH2k b ";
		}
		List params = new ArrayList();
		String hql = "select a from " + tableName + " where a.company.id=? ";
		params.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and b.emsNo = ? ";
			params.add(emsNo);
		}
		if (isAll != null && !isAll) {
			hql += " and b.declareState = ? ";
			params.add(DeclareState.PROCESS_EXE);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  a." + property + " like ?  ";
				params.add(value + "%");
			} else {
				hql += " and  a." + property + " = ?  ";
				params.add(value);
			}
		}
		System.out.println("hql=" + hql);
		return super.findPageList(hql, params.toArray(), index, length);
	}

	/**
	 * 分页条件查询合同成品的商品编码
	 * 
	 * @param index
	 * @param length
	 * @param projectType
	 * @param emsNo
	 * @param isMaterial
	 * @return
	 */
	public List findExgComplexByContract(int index, int length,
			int projectType, String emsNo, String property, Object value,
			boolean isLike, Boolean isAll) {

		String tableName = "ContractExg";
		if (projectType == ProjectType.BCS) {
			tableName = " ContractExg a left join a.contract b ";
		} else if (projectType == ProjectType.DZSC) {
			tableName = " DzscEmsExgBill a left join a.dzscEmsPorHead b ";
		} else if (projectType == ProjectType.BCUS) {
			tableName = " EmsHeadH2kExg a left join a.emsHeadH2k b ";
		}
		List params = new ArrayList();
		String hql = "select a from " + tableName + " where a.company.id=? ";
		params.add(CommonUtils.getCompany().getId());

		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and b.emsNo = ? ";
			params.add(emsNo);
		}
		if (isAll != null && !isAll) {
			hql += " and b.declareState = ? ";
			params.add(DeclareState.PROCESS_EXE);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  a." + property + " like ?  ";
				params.add(value + "%");
			} else {
				hql += " and  a." + property + " = ?  ";
				params.add(value);
			}
		}
		System.out.println("hql=" + hql);
		return super.findPageList(hql, params.toArray(), index, length);
	}

	/**
	 * 查询手册合同成品来自成品序号
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public List findContractExgBySeqNum(String emsNo, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		hsql += "select a from  ContractExg a  left join  a.contract b"
				+ "  where a.company= ? " + "	and b.declareState in ( ?) ";
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);

		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}
		if (seqNum != null) {
			hsql += " and a.seqNum = ? ";
			paramters.add(seqNum);
		}
		hsql += " order by b.emsNo ,a.seqNum ";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查询账册成品来自成品序号
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public List findEmsHeadH2kExgBySeqNum(String emsNo, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from  EmsHeadH2kExg a  left join  a.emsHeadH2k b"
				+ "  where a.company= ? ";
		paramters.add(CommonUtils.getCompany());

		if ("1".equals((getBpara(BcusParameter.EmsEdiH2kSend)))) {// 分批申报
			hsql += " and a.modifyMark = ? " + " and b.historyState=? ";
			paramters.add(ModifyMarkState.UNCHANGE);
			paramters.add(false);
		} else {
			hsql += " and b.declareState = ? " + " and b.historyState=? ";
			paramters.add(DeclareState.PROCESS_EXE);
			paramters.add(false);
		}
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (seqNum != null) {
			hsql += " and a.seqNum = ? ";
			paramters.add(seqNum);
		}

		hsql += " order by b.emsNo ,a.seqNum ";
		return find(hsql, paramters.toArray());
	}

	/**
	 * 查询电子手册成品来自成品序号
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public List findDzscEmsExgBillBySeqNum(String emsNo, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from  DzscEmsExgBill a  left join  a.dzscEmsPorHead b"
				+ "  where a.company= ? " + "	and b.declareState in ( ? ) ";
		paramters.add(CommonUtils.getCompany());
		paramters.add(DeclareState.PROCESS_EXE);

		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and b.emsNo = ? ";
			paramters.add(emsNo);
		}

		if (seqNum != null) {
			hsql += " and a.seqNum = ? ";
			paramters.add(seqNum);
		}

		hsql += " order by b.emsNo ,a.seqNum ";

		return find(hsql, paramters.toArray());
	}
}