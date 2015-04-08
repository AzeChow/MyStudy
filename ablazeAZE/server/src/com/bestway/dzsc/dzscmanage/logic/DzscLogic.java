/*
 * Created on 2004-7-29
 *getImg
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.innermerge.entity.TempMateriel;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DzscDelcareType;
import com.bestway.common.constant.DzscManageType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptInitBillItem;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.common.tools.logic.ToolsLogic;
import com.bestway.dzsc.checkcancel.dao.DzscContractCavDao;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscCommdityForbid;
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
import com.bestway.dzsc.dzscmanage.entity.TempDzscCommDataForEmsPor;
import com.bestway.dzsc.dzscmanage.entity.TempDzscCommMoneyAmountInfo;
import com.bestway.dzsc.dzscmanage.entity.TempDzscCustomsFactoryUnitWasteDiff;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscImgAndExgUsedDiffAmount;
import com.bestway.dzsc.dzscmanage.entity.TempDzscProductVersionInfo;
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.materialapply.dao.MaterialApplyDao;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.dzsc.message.logic.DzscMessageLogic;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.ImpCustomsDeclaration;

/**
 * 
 * 
 * com.bestway.dzsc.dzscmanage.logic.DzscLogic
 * 
 * @author Administrator checked by lm date 2010.02.06 电子手册商品归并-合同备案-手册通关管理
 *         Logic
 */

public class DzscLogic {
	private DzscDao dzscDao = null;

	private DzscInnerMergeDao dzscInnerMergeDao = null;

	private MaterialApplyDao materialApplyDao = null;

	private DzscContractCavDao dzscContractCavDao = null;

	private DzscMessageLogic dzscMessageLogic = null;

	private ToolsLogic toolsLogic = null;

	/**
	 * 获取materialApplyDayyyyyyo
	 * 
	 * @return materialApplyDao
	 */
	public MaterialApplyDao getMaterialApplyDao() {
		return materialApplyDao;
	}

	/**
	 * 设置materialApplyDao
	 * 
	 * @param materialApplyDao
	 */
	public void setMaterialApplyDao(MaterialApplyDao materialApplyDao) {
		this.materialApplyDao = materialApplyDao;
	}

	/**
	 * 获取dzscContractCavDao
	 * 
	 * @return dzscContractCavDao
	 */
	public DzscContractCavDao getDzscContractCavDao() {
		return dzscContractCavDao;
	}

	/**
	 * 设置dzscContractCavDao
	 * 
	 * @param dzscContractCavDao
	 */
	public void setDzscContractCavDao(DzscContractCavDao dzscContractCavDao) {
		this.dzscContractCavDao = dzscContractCavDao;
	}

	/**
	 * 获取dzscInnerMergeDao
	 * 
	 * @return
	 */
	public DzscInnerMergeDao getDzscInnerMergeDao() {
		return dzscInnerMergeDao;
	}

	/**
	 * 设置dzscInnerMergeDao
	 * 
	 * @param dzscInnerMergeDao
	 */
	public void setDzscInnerMergeDao(DzscInnerMergeDao dzscInnerMergeDao) {
		this.dzscInnerMergeDao = dzscInnerMergeDao;
	}

	/**
	 * 获取dzscMessageLogic
	 * 
	 * @return
	 */
	public DzscMessageLogic getDzscMessageLogic() {
		return dzscMessageLogic;
	}

	/**
	 * 设置dzscMessageLogic
	 * 
	 * @param dzscExportMessageLogic
	 */
	public void setDzscMessageLogic(DzscMessageLogic dzscExportMessageLogic) {
		this.dzscMessageLogic = dzscExportMessageLogic;
	}

	/**
	 * 获取toolsLogic
	 * 
	 * @return the toolsLogic
	 */
	public ToolsLogic getToolsLogic() {
		return toolsLogic;
	}

	/**
	 * 设置toolsLogic
	 * 
	 * @param toolsLogic
	 *            the toolsLogic to set
	 */
	public void setToolsLogic(ToolsLogic toolsLogic) {
		this.toolsLogic = toolsLogic;
	}

	/**
	 * 查找电子手册的物料基本资料，不在进出口申请单里，并把得到的结果加到临时物料基本资料里
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
	 *@param isFilter
	 *            是否过滤 TRUE过滤 FALSE 不过滤
	 * @return List 是tempMateriel型，电子手册的临时物料基本资料
	 */
	public List findMaterielByImpExpRequestBillType(String materialType,
			String impExpRequestBillId, int index, int length, String property,
			Object value, boolean isLike, boolean isFilter) {

		List lsResult = new ArrayList();
		List list = this.dzscDao.findMaterielByImpExpRequestBillType(
				materialType, impExpRequestBillId, index, length, property,
				value, isLike, isFilter);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(true);
			tempMateriel.setMateriel((Materiel) list.get(i));
			lsResult.add(tempMateriel);
		}
		return lsResult;
	}

	/**
	 * 判断物料是否备案
	 * 
	 * @param materiel
	 * @return
	 */
	private boolean checkMaterialIsPor(Materiel materiel) {
		return false;
	}

	/**
	 * 新建一个格式化的日期
	 * 
	 * @return Date static型，新建一个格式化的日期
	 */
	public static Date nowToStandDate() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(new Date());
		return java.sql.Date.valueOf(defaultDate);
	}

	/**
	 * 新增通关备案表头
	 * 
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DzscEmsPorHead newDzscEmsPorHead() {
		CompanyOther comOther = dzscDao.getSysCompanyOther();
		DzscEmsPorHead head = new DzscEmsPorHead();
		Company com = this.dzscDao.findCompany();
		head.setSeqNum(Integer.valueOf(dzscDao.getNum("DzscEmsPorHead",
				"seqNum")));
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getBuCode() == null || "".equals(com.getBuCode())) {
			stringBuffer.append("公司的经营单位编码不能为空\r\n");
		}
		if (com.getBuName() == null || "".equals(com.getBuName())) {
			stringBuffer.append("公司的经营单位名称不能为空\r\n");
		}
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		head.setTradeCode(com.getBuCode());
		head.setTradeName(com.getBuName());
		head.setMachCode(com.getCode());
		head.setMachName(com.getName());

		head.setOutTradeCo(com.getOutTradeCo());
		head.setEnterpriseAddress(com.getAddress());
		head.setLinkMan(com.getLinkman());
		head.setContactTel(com.getLinkman());
		head.setCustomNo(com.getMasterCustoms());
		head.setRedDep(com.getMasterFtc());
		head.setModifyMark(ModifyMarkState.ADDED);
		head.setDeclareState(DzscState.ORIGINAL);
		head.setCompany(com);
		if (comOther != null) {
			head.setSancEmsNo(comOther.getSancEmsNo());
			head.setIePort1(comOther.getIePort1());
			head.setIePort2(comOther.getIePort2());
			head.setIePort3(comOther.getIePort3());
			head.setIePort4(comOther.getIePort4());
			head.setIePort5(comOther.getIePort5());
			// head.setBargainType(comOther.getBargainType());
			head.setTrade(comOther.getTrade());
			head.setLevyKind(comOther.getLevyKind());
			head.setTradeCountry(comOther.getTradeCountry());
			head.setNote(comOther.getNote());
			// head.setBargainKind(comOther.getBargainKind());
			head.setTransac(comOther.getTransac());
			head.setCurr(comOther.getCommonCurr());
		}
		head.setAclUser(CommonUtils.getAclUser());
		head.setSaveDate(new Date());
		return this.dzscDao.saveEmsPorHead(head);
	}

	/**
	 * 申报通关备案表头（申报中）
	 * 
	 * @param head
	 *            通关备案表头
	 * @param taskId
	 * 
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DeclareFileInfo applyDzscEmsPorHead(DzscEmsPorHead head,
			String taskId) {
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		
		boolean isMaterielManageType = this.isMaterielManageType(head);
		DzscMaterielHead materielHead = null;
		
		if (isMaterielManageType) {
			List lsMaterialHead = this.dzscDao.findDzscMaterialHead();
			if ( lsMaterialHead.size() <= 0 ) {
				throw new RuntimeException("没有物料表头");
			}
			materielHead = (DzscMaterielHead) lsMaterialHead.get(0);
		}
		//long timer0=System.currentTimeMillis();
		checkEmsPorHeadForApply(head, materielHead);
		long timer1=System.currentTimeMillis();
		//System.out.print("开始检查料件，成品，单耗数量是否为空或者为0,耗用时间为："+(timer1-timer0)/1000 +" \n");
		
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscEmsPorHead> lsHead = new ArrayList<DzscEmsPorHead>();
		List lsExgBill = new ArrayList();
		List lsImgBill = new ArrayList();
		List lsBomBill = new ArrayList();
		List lsMaterialExg = new ArrayList();
		List lsMaterialImg = new ArrayList();

		Map<Integer, String> mapMaterialExg = new HashMap<Integer, String>();
		Map<Integer, String> mapMaterialImg = new HashMap<Integer, String>();

		if (isMaterielManageType) {
			List lsTemp = this.dzscDao.findDzscEmsPorMaterialExgPtNoSeqNum(head);
			for (int i = 0; i < lsTemp.size(); i++) {
				Object[] objs = (Object[]) lsTemp.get(i);
				if (objs[0] == null || objs[1] == null || objs[2] == null) {
					continue;
				}
				Integer seqNum = Integer.parseInt(objs[0].toString());
				String ptNo = objs[1].toString();
				String modifyMark = objs[2].toString();
				if (mapMaterialExg.get(seqNum) == null && !ModifyMarkState.DELETED.equals(modifyMark)) {
					mapMaterialExg.put(seqNum, ptNo);
				}
			}
			lsTemp = this.dzscDao.findDzscEmsPorMaterialImgPtNoSeqNum(head);
			for (int i = 0; i < lsTemp.size(); i++) {
				Object[] objs = (Object[]) lsTemp.get(i);
				if (objs[0] == null || objs[1] == null || objs[2] == null) {
					continue;
				}
				Integer seqNum = Integer.parseInt(objs[0].toString());
				String ptNo = objs[1].toString();
				String modifyMark = objs[2].toString();
				if (mapMaterialImg.get(seqNum) == null && !ModifyMarkState.DELETED.equals(modifyMark)) {
					mapMaterialImg.put(seqNum, ptNo);
				}
			}
		}
		long timer2=System.currentTimeMillis();
		System.out.print("把成品与料件的物料存放Map 所耗用时间为："+(timer2-timer1)/1000 +" \n");
		
		lsExgBill = this.dzscDao.findDzscEmsExgBillStateChanged(head);
		long timer3=System.currentTimeMillis();
		System.out.print("抓取通关成品表资料所耗用时间为："+(timer3-timer2)/1000 +" \n");
		if (isMaterielManageType) {
			for (int i = 0; i < lsExgBill.size(); i++) {
				DzscEmsExgBill exgBill = (DzscEmsExgBill) lsExgBill.get(i);
				if (exgBill.getAmount() == null || exgBill.getAmount() <= 0) {
					throw new RuntimeException("成品" + exgBill.getSeqNum().toString()
							+ "  " + exgBill.getName() + " 数量为空或等于零，所以不能备案");
				}	
				String ptNo = mapMaterialExg.get(exgBill.getSeqNum());
				if (ptNo == null || "".equals(ptNo.trim())) {
					throw new RuntimeException("备案序号为" + exgBill.getSeqNum()
							+ "的成品没有对应的归并前物料");
				}
				exgBill.setMaterialPtNo(ptNo);
			}
		} else {
			for (int i = 0; i < lsExgBill.size(); i++) {
				DzscEmsExgBill exgBill = (DzscEmsExgBill) lsExgBill.get(i);
				if (exgBill.getAmount() == null || exgBill.getAmount() <= 0) {
					throw new RuntimeException("成品" + exgBill.getSeqNum().toString()
							+ "  " + exgBill.getName() + " 数量为空或等于零，所以不能备案");
				}	
				exgBill.setTenSeqNum(null);
			}
		}
		long timer4=System.currentTimeMillis();
		System.out.print("检查成品是否有对应的归并前物料所耗用时间为："+(timer4-timer3)/1000 +" \n");
		lsImgBill = this.dzscDao.findDzscEmsImgBillStateChanged(head);
		long timer5=System.currentTimeMillis();
		System.out.print("抓取通关料件表资料所耗用时间为："+(timer5-timer4)/1000 +" \n");
		if (isMaterielManageType) {
			for (int i = 0; i < lsImgBill.size(); i++) {
				DzscEmsImgBill imgBill = (DzscEmsImgBill) lsImgBill.get(i);
				if (imgBill.getAmount() == null || imgBill.getAmount() <= 0) {
					throw new RuntimeException("料件" + imgBill.getSeqNum().toString()
							+ "  " + imgBill.getName() + " 数量为空或等于零，所以不能备案");
				}
				String ptNo = mapMaterialImg.get(imgBill.getSeqNum());
				if (ptNo == null || "".equals(ptNo.trim())) {
					throw new RuntimeException("备案序号为" + imgBill.getSeqNum()
							+ "的料件没有对应的归并前物料");
				}
				imgBill.setMaterialPtNo(ptNo);
			}
		} else {
			for (int i = 0; i < lsImgBill.size(); i++) {
				DzscEmsImgBill imgBill = (DzscEmsImgBill) lsImgBill.get(i);
				if (imgBill.getAmount() == null || imgBill.getAmount() <= 0) {
					throw new RuntimeException("料件" + imgBill.getSeqNum().toString()
							+ "  " + imgBill.getName() + " 数量为空或等于零，所以不能备案");
				}
				imgBill.setTenSeqNum(null);
			}
		}
		long timer6=System.currentTimeMillis();
		System.out.print("检查料件是否有对应的归并前物料所耗用时间为："+(timer6-timer5)/1000 +" \n");
		lsBomBill = this.dzscDao.findDzscEmsBomBillStateChanged(head);
		long timer7=System.currentTimeMillis();
		System.out.print("抓取通关BOM表资料 所耗用时间为："+(timer7-timer6)/1000 +" \n");
		for (int i = 0; i < lsBomBill.size(); i++) {
			DzscEmsBomBill bom = (DzscEmsBomBill) lsBomBill.get(i);
			if (bom.getUnitWare() == null || bom.getUnitWare() <= 0) {
				throw new RuntimeException("BOM 成品"
						+ bom.getDzscEmsExgBill().getSeqNum().toString() + "  "
						+ bom.getDzscEmsExgBill().getName() + "\n" + "对应料件 "
						+ bom.getImgSeqNum().toString() + "  " + bom.getName()
						+ " \n 数量为空或等于零，所以不能备案");
			}
		}
		long timer8=System.currentTimeMillis();
		System.out.print("检查BOM表单耗是否为空或等于零所耗用时间为："+(timer8-timer7)/1000 +" \n");
		
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}

		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info, head.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(head.getCopTrNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.EMS_POR_BILL);
		
		if (DzscState.CHANGE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DzscDelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setInputER(signInfo.getIdCard().substring(signInfo.getIdCard().length() - 4));
		head.setDeclareDate(new Date());
		head.setDeclareState(DzscState.APPLY);
		this.dzscDao.saveEmsPorHead(head);		
		long timer9=System.currentTimeMillis();
		System.out.print("保存签名信息 所耗用时间为"+(timer9-timer8)/1000 +" \n");
		lsHead.add(head);	
		lsMaterialExg = this.dzscDao.findDzscEmsPorMaterialExgStateChanged(head);
		long timer10=System.currentTimeMillis();
		System.out.print("抓取通关物料成品表资料 所耗用时间为："+(timer10-timer9)/1000 +" \n");
		lsMaterialImg = this.dzscDao.findDzscEmsPorMaterialImgStateChanged(head);
		long timer11=System.currentTimeMillis();
		System.out.print("抓取通关物料归并料件表资料 所耗用时间为："+(timer11-timer10)/1000 +" \n");
		String formatFile = "DzscEmsPorFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("DzscEmsPorHeadData", lsHead);
		hmData.put("DzscEmsPorExgData", lsExgBill);
		hmData.put("DzscEmsPorImgData", lsImgBill);
		hmData.put("DzscEmsPorBomData", lsBomBill);
		hmData.put("DzscEmsMaterialExgData", lsMaterialExg);
		hmData.put("DzscEmsMaterialImgData", lsMaterialImg);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 备案通关备案前确定里面的物料是否为空或数量等于零
	 * 
	 * @param head
	 *            通关备案表头
	 * @param materielHead
	 *            归并表头
	 */
	private void checkEmsPorHeadForApply(DzscEmsPorHead head,
			DzscMaterielHead materielHead) {
		if (DzscState.APPLY.equals(head.getDeclareState())) {
			throw new RuntimeException("此通关备案手册已经申报,不能重复申报");
		}
		if (head.getManageObject() == null
				|| "".equals(head.getManageObject().trim())) {
			throw new RuntimeException("通关备案表头的管理对象不能为空");
		}
		if (materielHead != null
				&& !head.getManageObject().equals(
						materielHead.getManageObject().trim())) {
			throw new RuntimeException("通关备案表头的管理对象和物料备案表头的管理对象不一致");
		}
//		List lsImg = this.dzscDao.findDzscEmsImgBill(head);
//		for (int i = 0; i < lsImg.size(); i++) {
//			DzscEmsImgBill img = (DzscEmsImgBill) lsImg.get(i);
//			if (img.getAmount() == null || img.getAmount() <= 0) {
//				throw new RuntimeException("料件" + img.getSeqNum().toString()
//						+ "  " + img.getName() + " 数量为空或等于零，所以不能备案");
//			}
//		}
//		List lsExg = this.dzscDao.findDzscEmsExgBill(head);
//		for (int i = 0; i < lsExg.size(); i++) {
//			DzscEmsExgBill exg = (DzscEmsExgBill) lsExg.get(i);
//			if (exg.getAmount() == null || exg.getAmount() <= 0) {
//				throw new RuntimeException("成品" + exg.getSeqNum().toString()
//						+ "  " + exg.getName() + " 数量为空或等于零，所以不能备案");
//			}
//		}
//		List lsBom = this.dzscDao.findDzscEmsBomBill(head.getId());
//		for (int i = 0; i < lsBom.size(); i++) {
//			DzscEmsBomBill bom = (DzscEmsBomBill) lsBom.get(i);
//			if (bom.getUnitWare() == null || bom.getUnitWare() <= 0) {
//				throw new RuntimeException("BOM 成品"
//						+ bom.getDzscEmsExgBill().getSeqNum().toString() + "  "
//						+ bom.getDzscEmsExgBill().getName() + "\n" + "对应料件 "
//						+ bom.getImgSeqNum().toString() + "  " + bom.getName()
//						+ " \n 数量为空或等于零，所以不能备案");
//			}
//		}
	}

	/**
	 * 变更通关备案
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param isChange
	 *            是否为变跟状态，true为变更状态
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DzscEmsPorHead dzscEmsPorHeadChange(DzscEmsPorHead emsHead,
			boolean isChange) {
		DzscEmsPorHead emsHeadChange = getPorHeadChangeBean(emsHead, isChange);
		dzscDao.saveDzscEmsPorHead(emsHeadChange);
		this.changeEmsPorImgBill(emsHead, emsHeadChange, isChange);
		// for (int i = 0; i < listDetailImgBill.size(); i++) {
		// dzscDao.saveOrUpdateDirect(((DzscEmsImgBill) listDetailImgBill
		// .get(i)));
		// }
		this.changeEmsPorExgBill(emsHead, emsHeadChange, isChange);
		// for (int i = 0; i < listDetailExgBill.size(); i++) {
		// dzscDao.saveOrUpdateDirect(((DzscEmsExgBill) listDetailExgBill
		// .get(i)));
		// }
		this.changeEmsPorMaterialExg(emsHead, emsHeadChange);// 变更归并成品
		this.changeEmsPorMaterialImg(emsHead, emsHeadChange);// 变更归并料件
		return emsHeadChange;
	}

	/**
	 * 通关备案表头变更
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param isChange
	 *            是否为变跟状态，true为变更状态
	 * @return DzscEmsPorHead 变更的通关备案表头
	 */
	private DzscEmsPorHead getPorHeadChangeBean(DzscEmsPorHead emsHead,
			boolean isChange) { // 电子备案表头变更
		DzscEmsPorHead emsHeadChanged = null; // 变更
		try {
			emsHeadChanged = (DzscEmsPorHead) BeanUtils.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		emsHeadChanged.setSeqNum(Integer.valueOf(dzscDao.getNum(
				"DzscEmsPorHead", "seqNum")));
		emsHeadChanged.setId(null);
		if (isChange) { // 变更
			emsHeadChanged.setDeclareState(DzscState.CHANGE);
			emsHeadChanged.setModifyMark(ModifyMarkState.MODIFIED);
		} else { // 转抄
			emsHeadChanged.setSeqNum(Integer.valueOf(dzscDao.getNum(
					"DzscEmsPorHead", "seqNum")));
			emsHeadChanged.setIeContractNo(null);
			emsHeadChanged.setImContractNo(null);
			emsHeadChanged.setEmsNo(null);
			emsHeadChanged.setCopTrNo(null);
			emsHeadChanged.setBeginDate(null);
			emsHeadChanged.setEndDate(null);
			emsHeadChanged.setDestroyDate(null);
			emsHeadChanged.setDeferDate(null);
			emsHeadChanged.setSancEmsNo(null);
			emsHeadChanged.setAgreementNo(null);
			emsHeadChanged.setImgAmount(null);
			emsHeadChanged.setExgAmount(null);
			emsHeadChanged.setApproveDate(null);
			emsHeadChanged.setIsImportFromQP(false);
			emsHeadChanged.setAclUser(CommonUtils.getAclUser());
			emsHeadChanged.setSaveDate(new Date());
			emsHeadChanged.setModifyMark(ModifyMarkState.ADDED);
			emsHeadChanged.setDeclareState(DzscState.ORIGINAL);
		}

		return emsHeadChanged;
	}

	/**
	 * 从内部归并中抓取通关备案需要的数据,经过合同备案4码的过滤
	 * 
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            是否为料件，true时为料件
	 * @return List 是TempDzscCommDataForEmsPor型，类型存放十码和四码的资料
	 */
	public List findMerger10ForDzscEmsPor(DzscEmsPorHead head,
			boolean isMaterial) {
		List lsResult = new ArrayList();
		List list = this.dzscDao.findMerger10ForEmsPor(head, isMaterial);
		for (int i = 0; i < list.size(); i++) {
			TempDzscCommDataForEmsPor tempData = new TempDzscCommDataForEmsPor();
			DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) list.get(i);
			tempData.setTenSeqNum(tenInnerMerge.getTenSeqNum());
			tempData.setTenComplex(tenInnerMerge.getTenComplex());
			tempData.setTenPtName(tenInnerMerge.getTenPtName());
			tempData.setTenPtSpec(tenInnerMerge.getTenPtSpec());
			tempData.setTenUnit(tenInnerMerge.getTenUnit());
			tempData.setMachinPrice(tenInnerMerge.getMachinPrice());
			tempData.setLegalUnitGene(tenInnerMerge.getLegalUnitGene());
			tempData.setLegalUnit2Gene(tenInnerMerge.getLegalUnit2Gene());
			tempData.setWeigthUnitGene(tenInnerMerge.getWeigthUnitGene());
			tempData.setFourSeqNum(tenInnerMerge.getDzscFourInnerMerge()
					.getFourSeqNum());
			tempData.setFourComplex(tenInnerMerge.getDzscFourInnerMerge()
					.getFourComplex());
			tempData.setFourPtName(tenInnerMerge.getDzscFourInnerMerge()
					.getFourPtName());
			tempData.setFourPtSpec(tenInnerMerge.getDzscFourInnerMerge()
					.getFourPtSpec());
			lsResult.add(tempData);
		}
		return lsResult;
	}

	/**
	 * 保存通关备案BOM资料
	 * 
	 * @param bom
	 *            通关备案BOM资料
	 */
	public void saveDzscEmsBomBill(DzscEmsBomBill bom) {
		if(bom.getId()!=null){
			DzscEmsBomBill oldBom = (DzscEmsBomBill) this.dzscDao.load(
					DzscEmsBomBill.class, bom.getId());
			if (oldBom != null) {
				if (!CommonUtils.compareObject(oldBom.getUnitWare(), bom
						.getUnitWare())
						|| !CommonUtils.compareObject(oldBom.getWare(), bom
								.getWare())
						|| !CommonUtils.compareObject(oldBom.getUnitDosage(), bom
								.getUnitDosage())) {
					if (ModifyMarkState.UNCHANGE.equals(bom.getModifyMark())) {
						bom.setModifyMark(ModifyMarkState.MODIFIED);
					}
				}
			}
		}
		
		this.dzscDao.saveDzscEmsBomBill(bom);
		this.writeBackDzscEmsImgBill(bom);
		this.writeBackDzscEmsExgBill(bom);
	}
	/**
	 * 保存通关备案BOM资料
	 * 
	 * @param bom
	 *            通关备案BOM资料
	 */
	public void saveImgToDzscEmsBomBill(DzscEmsBomBill bom) {
		DzscEmsBomBill oldBom = (DzscEmsBomBill) this.dzscDao.load(
				DzscEmsBomBill.class, bom.getId());
		if (oldBom != null) {
			if (!CommonUtils.compareObject(oldBom.getUnitWare(), bom
					.getUnitWare())
					|| !CommonUtils.compareObject(oldBom.getWare(), bom
							.getWare())
					|| !CommonUtils.compareObject(oldBom.getUnitDosage(), bom
							.getUnitDosage())) {
				if (ModifyMarkState.UNCHANGE.equals(bom.getModifyMark())) {
					bom.setModifyMark(ModifyMarkState.MODIFIED);
				}
			}
		}
		this.dzscDao.saveDzscEmsBomBill(bom);
		DzscParameterSet parameter=this.dzscDao.findDzscParameterSet();
		this.writeBackDzscEmsImgBill(bom);
		if(parameter.getUpdateEmsImgWriteBackExg()!=null&&parameter.getUpdateEmsImgWriteBackExg())
		this.writeBackDzscEmsExgBill(bom);
		
	}

	/**
	 * 统计通关备案的料件项数
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	private void statDzscEmsPorImgBillCount(DzscEmsPorHead head) {
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setMaterielItemCount(this.dzscDao.findDzscEmsImgBillCount(head
				.getId()));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveEmsPorHead(head);
	}

	/**
	 * 统计通关备案的料件项数
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	private void statDzscEmsPorExgBillCount(DzscEmsPorHead head) {
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setProductItemCount(this.dzscDao.findDzscEmsExgBillCount(head
				.getId()));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveEmsPorHead(head);
	}

	/**
	 * 统计通关备案的进口总值
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	private void statDzscEmsPorImgBillMoney(DzscEmsPorHead head) {
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setImgAmount(CommonUtils.getDoubleByDigit(this.dzscDao
				.findDzscEmsImgBillMoney(head.getId()), 5));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveEmsPorHead(head);
	}

	/**
	 * 统计通关备案的出口总值
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	private void statDzscEmsPorExgBillMoney(DzscEmsPorHead head) {
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setExgAmount(CommonUtils.getDoubleByDigit(this.dzscDao
				.findDzscEmsExgBillMoney(head.getId()), 5));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveEmsPorHead(head);
	}

	/**
	 * 根据查询得到的通关备案需要的数据，生成通关备案商品信息
	 * 
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            是否为料件，true时为料件
	 * @param list
	 *            是TempDzscCommDataForEmsPor型，类型存放十码和四码的资料
	 * @return List 是DzscEmsImgBill或DzscEmsExgBill型，通关备案料件、成品
	 */
	public List saveDzscEmsPorImgExgBill(DzscEmsPorHead head,
			boolean isMaterial, List list) {
		List lsResult = new ArrayList();
		if (isMaterial) {
			int maxImgSeqNum = this.dzscDao.getMaxDzscEmsPorImgNum(head);
			for (int i = 0; i < list.size(); i++) {
				TempDzscCommDataForEmsPor tempData = (TempDzscCommDataForEmsPor) list
						.get(i);
				DzscEmsImgBill img = new DzscEmsImgBill();
				img.setDzscEmsPorHead(head);
				img.setSeqNum(maxImgSeqNum + i);
				img.setTenSeqNum(tempData.getTenSeqNum());
				img.setComplex(tempData.getTenComplex());
				img.setName(tempData.getTenPtName());
				img.setSpec(tempData.getTenPtSpec());
				img.setUnit(tempData.getTenUnit());
				img.setLegalUnitGene(tempData.getLegalUnitGene());
				img.setLegalUnit2Gene(tempData.getLegalUnit2Gene());
				img.setWeigthUnitGene(tempData.getWeigthUnitGene());
				// img.setPrice(objs[5] == null ? null : Double.valueOf(objs[5]
				// .toString()));
				img.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsImgBill(img);
				List lsInnerMerge = this.dzscInnerMergeDao
						.findDzscInnerMergeDataByTenSeqNum(
								MaterielType.MATERIEL, img.getTenSeqNum());
				for (int j = 0; j < lsInnerMerge.size(); j++) {
					DzscInnerMergeData innerMergeData = (DzscInnerMergeData) lsInnerMerge
							.get(j);
					DzscEmsPorMaterialImg materialImg = new DzscEmsPorMaterialImg();
					materialImg.setMateriel(innerMergeData.getMateriel());
					materialImg.setDzscEmsImgBill(img);
					materialImg.setDzscEmsPorHead(head);
					materialImg.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveOrUpdate(materialImg);
				}
				lsResult.add(img);
			}
			statDzscEmsPorImgBillCount(head);
			statDzscEmsPorImgBillMoney(head);
		} else {
			int maxExgSeqNum = this.dzscDao.getMaxDzscEmsPorExgNum(head);
			for (int i = 0; i < list.size(); i++) {
				TempDzscCommDataForEmsPor tempData = (TempDzscCommDataForEmsPor) list
						.get(i);
				DzscEmsExgBill exg = new DzscEmsExgBill();
				exg.setDzscEmsPorHead(head);
				exg.setSeqNum(maxExgSeqNum + i);
				exg.setTenSeqNum(tempData.getTenSeqNum());
				exg.setComplex(tempData.getTenComplex());
				exg.setName(tempData.getTenPtName());
				exg.setSpec(tempData.getTenPtSpec());
				exg.setUnit(tempData.getTenUnit());
				exg.setLegalUnitGene(tempData.getLegalUnitGene());
				exg.setLegalUnit2Gene(tempData.getLegalUnit2Gene());
				exg.setWeigthUnitGene(tempData.getWeigthUnitGene());
				// exg.setPrice(objs[5] == null ? null : Double.valueOf(objs[5]
				// .toString()));
				exg.setMachinPrice(tempData.getMachinPrice());
				exg.setPrice(CommonUtils.getDoubleExceptNull(exg
						.getMachinPrice())
						+ CommonUtils.getDoubleExceptNull(exg.getImgMoney()));
				exg.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsExgBill(exg);
				List lsInnerMerge = this.dzscInnerMergeDao
						.findDzscInnerMergeDataByTenSeqNum(
								MaterielType.FINISHED_PRODUCT, exg
										.getTenSeqNum());
				for (int j = 0; j < lsInnerMerge.size(); j++) {
					DzscInnerMergeData innerMergeData = (DzscInnerMergeData) lsInnerMerge
							.get(j);
					DzscEmsPorMaterialExg materialExg = new DzscEmsPorMaterialExg();
					materialExg.setMateriel(innerMergeData.getMateriel());
					materialExg.setDzscEmsExgBill(exg);
					materialExg.setDzscEmsPorHead(head);
					materialExg.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveOrUpdate(materialExg);
				}
				lsResult.add(exg);
			}
			statDzscEmsPorExgBillCount(head);
			statDzscEmsPorExgBillMoney(head);
		}
		return lsResult;
	}

	/**
	 * 根据查询得到的通关备案需要的数据，生成通关备案商品信息
	 * 
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            是否为料件，true时为料件
	 * @param list
	 *            是TempDzscCommDataForEmsPor型，类型存放十码和四码的资料
	 * @return List 是DzscEmsImgBill或DzscEmsExgBill型，通关备案料件、成品
	 */
	public List saveDzscEmsPorImgExgByComplex(DzscEmsPorHead head,
			boolean isMaterial, List list) {
		List lsResult = new ArrayList();
		if (isMaterial) {
			int maxImgSeqNum = this.dzscDao.getMaxDzscEmsPorImgNum(head);
			for (int i = 0; i < list.size(); i++) {
				Complex complex = (Complex) list.get(i);
				DzscEmsImgBill img = new DzscEmsImgBill();
				img.setDzscEmsPorHead(head);
				img.setSeqNum(maxImgSeqNum + i);
				img.setComplex(complex);
				img.setName(complex.getName());
				img.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsImgBill(img);
				lsResult.add(img);
			}
			statDzscEmsPorImgBillCount(head);
			statDzscEmsPorImgBillMoney(head);
		} else {
			int maxExgSeqNum = this.dzscDao.getMaxDzscEmsPorExgNum(head);
			for (int i = 0; i < list.size(); i++) {
				Complex complex = (Complex) list.get(i);
				DzscEmsExgBill exg = new DzscEmsExgBill();
				exg.setDzscEmsPorHead(head);
				exg.setSeqNum(maxExgSeqNum + i);
				exg.setComplex(complex);
				exg.setName(complex.getName());
				exg.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsExgBill(exg);
				lsResult.add(exg);
			}
			statDzscEmsPorExgBillCount(head);
			statDzscEmsPorExgBillMoney(head);
		}
		return lsResult;
	}

	/**
	 * 通关备案料件变更
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param emsHeadChange
	 *            变更后的通关备案表头
	 * @param isChange
	 *            是否变更状态，true为变更状态
	 * @return List 是DzscEmsImgBill型，变更后的通关备案料件
	 */
	private void changeEmsPorImgBill(DzscEmsPorHead emsHead,
			DzscEmsPorHead emsHeadChange, boolean isChange) { // 料件清单变更
		// List list = null;
		// List emsInnerChanges = new Vector();
		List list = dzscDao.findEmsPorImgBill(emsHead); // 获取未变更
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill detail = (DzscEmsImgBill) list.get(i);
			DzscEmsImgBill detailChanged = null; // 变更
			try {
				detailChanged = (DzscEmsImgBill) BeanUtils.cloneBean(detail);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			detailChanged.setId(null);
			if (isChange) {
				detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				detailChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			detailChanged.setDzscEmsPorHead(emsHeadChange);
			detailChanged.setDutyRate(0.0);
			this.dzscDao.saveOrUpdateDirect(detailChanged);
			// emsInnerChanges.add(detailChanged);
		}
		// return emsInnerChanges;
	}

	/**
	 * 通关备案成品变更
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param emsHeadChange
	 *            变更后的通关备案表头
	 * @param isChange
	 *            是否变更状态，true为变更状态
	 * @return List 是DzscEmsExgBill型，变更后的通关备案成品
	 */
	private void changeEmsPorExgBill(DzscEmsPorHead emsHead,
			DzscEmsPorHead emsHeadChange, boolean isChange) { // 成品清单变更
		// List list = null;
		// List emsInnerChanges = new Vector();
		List list = dzscDao.findDzscEmsExgBill(emsHead); // 获取未变更
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgBill detail = (DzscEmsExgBill) list.get(i);
			DzscEmsExgBill detailChanged = null; // 变更
			try {
				detailChanged = (DzscEmsExgBill) BeanUtils.cloneBean(detail);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			detailChanged.setId(null);
			if (isChange) {
				detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				detailChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			detailChanged.setDzscEmsPorHead(emsHeadChange);
			dzscDao.saveOrUpdateDirect(detailChanged);
			changeEmsBomBill(detail, detailChanged, isChange);
			// emsInnerChanges.add(detailChanged);
		}
		// return emsInnerChanges;
	}

	/**
	 * 通关备案Bom变更
	 * 
	 * @param exg
	 *            通关备案成品
	 * @param exgChange
	 *            变更后的通关备案成品
	 * @param isChange
	 *            是否变更状态，true为变更状态
	 * @return List 是DzscEmsBomBill型，变更后的通关备案BOM
	 */
	private void changeEmsBomBill(DzscEmsExgBill exg, DzscEmsExgBill exgChange,
			boolean isChange) {
		// List listBom = null;
		// List emsInnerChanges = new Vector();
		List listBom = dzscDao.findEmsPorBomBill(exg); // bom未变更
		for (int j = 0; j < listBom.size(); j++) {
			DzscEmsBomBill bom = (DzscEmsBomBill) listBom.get(j);
			DzscEmsBomBill bomChanged = null; // 变更
			try {
				bomChanged = (DzscEmsBomBill) BeanUtils.cloneBean(bom);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			bomChanged.setId(null);
			if (isChange) {
				bomChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				bomChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			bomChanged.setDzscEmsExgBill(exgChange);
			bomChanged.setNonMnlRatio(bom.getNonMnlRatio()==null?0.0:bom.getNonMnlRatio());
			// emsInnerChanges.add(bomChanged);
			this.dzscDao.saveOrUpdateDirect(bomChanged);
		}
		// return emsInnerChanges;
	}

	/**
	 * 通关备案归并料件变更或复制
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param emsHeadChange
	 *            变更后的通关备案表头
	 * @param isChange
	 *            是否变更状态，true为变更状态
	 * @return List 是DzscEmsImgBill型，变更后的通关备案料件
	 */
	private List getEmsPorMaterialExgChangeBean(DzscEmsPorHead emsHead,
			DzscEmsPorHead emsHeadChange, boolean isChange) { // 料件清单变更
		List list = null;
		List emsInnerChanges = new Vector();
		list = dzscDao.findDzscEmsPorMaterialExg(emsHead); // 获取未变更
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialExg detail = (DzscEmsPorMaterialExg) list.get(i);
			DzscEmsPorMaterialExg detailChanged = null; // 变更
			try {
				detailChanged = (DzscEmsPorMaterialExg) BeanUtils
						.cloneBean(detail);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			List lsExgBill = this.dzscDao.findDzscEmsExgBillBySeqNum(
					emsHeadChange.getId(), detailChanged.getDzscEmsExgBill()
							.getSeqNum());
			// if (lsExgBill.size() > 0) {
			detailChanged.setDzscEmsExgBill((DzscEmsExgBill) lsExgBill.get(0));
			// }else{
			// throw new RuntimeException("");
			// }
			detailChanged.setId(null);
			if (isChange) {
				detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				detailChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			detailChanged.setDzscEmsPorHead(emsHeadChange);
			emsInnerChanges.add(detailChanged);
		}
		return emsInnerChanges;
	}

	/**
	 * 通关备案归并料件变更或复制
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param emsHeadChange
	 *            变更后的通关备案表头
	 * @param isChange
	 *            是否变更状态，true为变更状态
	 * @return List 是DzscEmsImgBill型，变更后的通关备案料件
	 */
	private List getEmsPorMaterialImgChangeBean(DzscEmsPorHead emsHead,
			DzscEmsPorHead emsHeadChange, boolean isChange) { // 料件清单变更
		List list = null;
		List emsInnerChanges = new Vector();
		list = dzscDao.findDzscEmsPorMaterialImg(emsHead); // 获取未变更
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialImg detail = (DzscEmsPorMaterialImg) list.get(i);
			DzscEmsPorMaterialImg detailChanged = null; // 变更
			try {
				detailChanged = (DzscEmsPorMaterialImg) BeanUtils
						.cloneBean(detail);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			List lsImgBill = this.dzscDao.findDzscEmsImgBillBySeqNum(
					emsHeadChange.getId(), detailChanged.getDzscEmsImgBill()
							.getSeqNum());
			detailChanged.setDzscEmsImgBill((DzscEmsImgBill) lsImgBill.get(0));
			detailChanged.setId(null);
			if (isChange) {
				detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				detailChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			detailChanged.setDzscEmsPorHead(emsHeadChange);
			emsInnerChanges.add(detailChanged);
		}
		return emsInnerChanges;
	}

	/**
	 * 判断手册号是否重复
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkEmsPorWjHeadDuple(DzscEmsPorWjHead head) {
		List list = this.dzscDao.findDzscEmsPorWjHeadByEmsNo(head.getEmsNo());
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorWjHead hd = (DzscEmsPorWjHead) list.get(i);
			if (head.getEmsNo().equals(hd.getEmsNo())
					&& !head.getId().equals(hd.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断手册号是否重复
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkDzscEmsPorHeadDuple(DzscEmsPorHead head) {
		List list = this.dzscDao.findDzscEmsPorHeadByEmsNo(head.getEmsNo());
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorHead hd = (DzscEmsPorHead) list.get(i);
			if (head.getEmsNo().equals(hd.getEmsNo())
					&& !head.getId().equals(hd.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断通关备案内部编号号是否重复
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkDzscEmsPorCopEmsNoDuple(DzscEmsPorHead head) {
		List list = this.dzscDao
				.findDzscEmsPorHeadByCopEmsNo(head.getCopTrNo());
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorHead hd = (DzscEmsPorHead) list.get(i);
			if (head.getCopTrNo().equals(hd.getCopTrNo())
					&& !head.getId().equals(hd.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断通关备案企业内部物料编号是否存在
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否存在，true为存在
	 */
	public boolean checkDzscEmsPorCopEntNoIsExist(DzscEmsPorHead head) {
		String copEntNo = head.getCopEntNo();
		List lsMaterialHead = this.dzscDao.findDzscMaterialHead();
		if (lsMaterialHead.size() <= 0 || lsMaterialHead.get(0) == null) {
			// throw new RuntimeException("没有备案的物料表头");
			return false;
		}
		DzscMaterielHead materielHead = (DzscMaterielHead) lsMaterialHead
				.get(0);
		if (copEntNo.trim().equals(materielHead.getCopEntNo())) {
			return true;
		}
		return false;
	}

	/**
	 * 新增合同备案表头
	 * 
	 * @return DzscEmsPorWjHead 合同备案表头
	 */
	public DzscEmsPorWjHead newEmsPorWjHead() {
		CompanyOther comOther = dzscDao.getSysCompanyOther();
		String copEntNo = dzscMessageLogic.getNewCopEntNo("DzscEmsPorWjHead",
				"copTrNo", "D", DzscBusinessType.EMS_POR_WJ);
		DzscEmsPorWjHead head = new DzscEmsPorWjHead();
		Company com = this.dzscDao.findCompany();
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getBuCode() == null || "".equals(com.getBuCode())) {
			stringBuffer.append("公司的经营单位编码不能为空\r\n");
		}
		if (com.getBuName() == null || "".equals(com.getBuName())) {
			stringBuffer.append("公司的经营单位名称不能为空\r\n");
		}
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		head.setSeqNum(Integer.valueOf(dzscDao.getNum("DzscEmsPorWjHead",
				"seqNum")));
		head.setCopTrNo(copEntNo);
		head.setTradeCode(com.getBuCode());
		head.setTradeName(com.getBuName());
		head.setMachCode(com.getCode());
		head.setMachName(com.getName());
		head.setDeclareState(DzscState.ORIGINAL);
		head.setModifyMark(ModifyMarkState.ADDED);

		head.setOutTradeCo(com.getOutTradeCo());
		head.setEnterpriseAddress(com.getAddress());
		head.setLinkMan(com.getLinkman());
		head.setContactTel(com.getLinkman());
		head.setCustomNo(com.getMasterCustoms());
		head.setRedDep(com.getMasterFtc());

		List lsMaterialHead = this.dzscDao.findDzscMaterialHead();
		if (lsMaterialHead.size() <= 0) {
			// throw new RuntimeException("没有物料表头");
		} else {
			DzscMaterielHead materielHead = (DzscMaterielHead) lsMaterialHead
					.get(0);
			head.setManageObject(materielHead.getManageObject());
		}

		if (comOther != null) {
			head.setSancEmsNo(comOther.getSancEmsNo());
			head.setIePort1(comOther.getIePort1());
			head.setIePort2(comOther.getIePort2());
			head.setIePort3(comOther.getIePort3());
			head.setIePort4(comOther.getIePort4());
			head.setIePort5(comOther.getIePort5());
			// head.setBargainType(comOther.getBargainType());
			head.setTrade(comOther.getTrade());
			head.setLevyKind(comOther.getLevyKind());
			head.setTradeCountry(comOther.getTradeCountry());
			head.setNote(comOther.getNote());
			// head.setBargainKind(comOther.getBargainKind());

			head.setTransac(comOther.getTransac());
			head.setCurr(comOther.getCommonCurr());
		}
		head.setSaveDate(new Date());
		head.setAclUser(CommonUtils.getAclUser());
		head.setCompany(com);
		return this.dzscDao.saveEmsPorWjHead(head);
	}

	/**
	 * 保存合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            电子手册合同备案表头资料
	 * 
	 * @return DzscEmsPorWjHead 电子手册合同备案表头资料
	 */
	public DzscEmsPorWjHead saveEmsPorWj(DzscEmsPorWjHead obj) {
		if (DzscState.CHANGE.equals(obj.getDeclareState())) {
			obj.setModifyMark(ModifyMarkState.MODIFIED);
		}
		// String copTrNo = obj.getCopTrNo();
		//		
		// String copEntNo = dzscMessageLogic.getNewCopEntNo("DzscEmsPorWjHead",
		// "copTrNo", "D", DzscBusinessType.EMS_POR_WJ);
		//		
		// this.dzscMessageLogic.checkNewCopEntNo("DzscEmsPorWjHead",
		// "copTrNo", "D", DzscBusinessType.EMS_POR_WJ,copTrNo);
		// if(!tfCopTrNoOld.equals(copTrNo)){
		// this.dzscMessageLogic.checkCopEntNoOnly(copTrNo);
		// }
		this.dzscDao.saveEmsPorWj(obj);
		this.statDzscEmsPorWjExgCount(obj);
		this.statDzscEmsPorWjImgCount(obj);
		return obj;
	}

	/**
	 * 保存合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            电子手册合同备案表头资料
	 * 
	 * @return DzscEmsPorWjHead 电子手册合同备案表头资料
	 */
	public DzscEmsPorWjHead saveEmsPorWj(DzscEmsPorWjHead obj,
			String tfCopTrNoOld) {
		if (DzscState.CHANGE.equals(obj.getDeclareState())) {
			obj.setModifyMark(ModifyMarkState.MODIFIED);
		}
		String copTrNo = obj.getCopTrNo();

		String copEntNo = dzscMessageLogic.getNewCopEntNo("DzscEmsPorWjHead",
				"copTrNo", "D", DzscBusinessType.EMS_POR_WJ);

		this.dzscMessageLogic.checkNewCopEntNo("DzscEmsPorWjHead", "copTrNo",
				"D", DzscBusinessType.EMS_POR_WJ, copTrNo);
		if (!tfCopTrNoOld.equals(copTrNo)) {
			this.dzscMessageLogic.checkCopEntNoOnly(copTrNo);
		}
		this.dzscDao.saveEmsPorWj(obj);
		this.statDzscEmsPorWjExgCount(obj);
		this.statDzscEmsPorWjImgCount(obj);
		return obj;
	}

	/**
	 * 申报合同备案表头（申报中）
	 * 
	 * @param head
	 *            合同备案表头
	 * @param taskId
	 * 
	 * @return DzscEmsPorWjHead 合同备案表头
	 */
	public DeclareFileInfo applyEmsPorWjHead(DzscEmsPorWjHead head,
			String taskId) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscEmsPorWjHead> lsHead = new ArrayList<DzscEmsPorWjHead>();
		List lsExgWj = new ArrayList();
		List lsImgWj = new ArrayList();

		head = this.dzscDao.findDzscEmsPorWjHeadById(head.getId());

		if (DzscState.APPLY.equals(head.getDeclareState())) {
			throw new RuntimeException("此合同备案已经申报，不能再进行申报");
		}
		if (head.getManageObject() == null
				|| "".equals(head.getManageObject().trim())) {
			throw new RuntimeException("合同备案表头的管理对象不能为空");
		}
		DzscMaterielHead materielHead = null;
		if (head.getDzscManageType() == null
				|| DzscManageType.MATERIAL == head.getDzscManageType()) {
			List lsMaterialHead = this.dzscDao.findDzscMaterialHead();
			if (lsMaterialHead.size() <= 0) {
				throw new RuntimeException("没有物料表头");
			}
			materielHead = (DzscMaterielHead) lsMaterialHead.get(0);
			if (!head.getManageObject().equals(
					materielHead.getManageObject().trim())) {
				throw new RuntimeException("合同备案表头的管理对象和物料备案表头的管理对象不一致");
			}
		}
		/**
		 * 合同备案表头，若物料备案审批通过，则<Note_1>备用标志1字段填写“1”
		 */
		if (materielHead != null
				&& DzscState.EXECUTE.equals(materielHead.getMaterialState())) {
			head.setMaterialState("1");
		}

		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}

		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info, head
				.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(head.getCopTrNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.EMS_POR_WJ);
		if (DzscState.CHANGE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DzscDelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setInputER(signInfo.getIdCard().substring(
				signInfo.getIdCard().length() - 4));
		head.setDeclareDate(new Date());
		head.setDeclareState(DzscState.APPLY);
		this.dzscDao.saveEmsPorWjHead(head);
		lsHead.add(head);

		lsExgWj = this.dzscDao.findEmsPorExgWjStateChanged(head);
		lsImgWj = this.dzscDao.findEmsPorImgWjStateChanged(head);

		String formatFile = "DzscEmsPorWjFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("DzscEmsPorWjHeadData", lsHead);
		hmData.put("DzscEmsPorWjImgData", lsImgWj);
		hmData.put("DzscEmsPorWjExgData", lsExgWj);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 合同备案变更
	 * 
	 * @param emsHead
	 *            合同备案表头
	 * @param isChange
	 *            判断变更状态，true为变更状态
	 * @return DzscEmsPorWjHead 变更后合同备案表头
	 */
	public DzscEmsPorWjHead emsPorChangeWj(DzscEmsPorWjHead emsHead,
			boolean isChange) {
		DzscEmsPorWjHead emsHeadChange = getPorHeadChangeBeanWj(emsHead,
				isChange);
		dzscDao.saveEmsPorWj(emsHeadChange);
		List listDetailImgWj = getEmsPorImgWjChangeBean(emsHead, emsHeadChange,
				isChange);
		for (int i = 0; i < listDetailImgWj.size(); i++) {
			dzscDao.saveDzscEmsImgWj(((DzscEmsImgWj) listDetailImgWj.get(i)));
		}
		List listDetailExgWj = getEmsPorExgWjChangeBean(emsHead, emsHeadChange,
				isChange);
		for (int i = 0; i < listDetailExgWj.size(); i++) {
			dzscDao.saveDzscEmsExgWj(((DzscEmsExgWj) listDetailExgWj.get(i)));
		}
		return emsHeadChange;
	}

	/**
	 * 合同备案表头变更
	 * 
	 * @param emsHead
	 *            合同备案表头
	 * @param isChange
	 *            判断变更状态，true为变更状态
	 * @return DzscEmsPorWjHead 变更后合同备案表头
	 */
	private DzscEmsPorWjHead getPorHeadChangeBeanWj(DzscEmsPorWjHead emsHead,
			boolean isChange) { // 电子备案表头变更
		DzscEmsPorWjHead emsHeadChanged = null; // 变更
		try {
			emsHeadChanged = (DzscEmsPorWjHead) BeanUtils.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		emsHeadChanged.setSeqNum(Integer.valueOf(dzscDao.getNum(
				"DzscEmsPorWjHead", "seqNum")));
		emsHeadChanged.setId(null);
		if (isChange) { // 变更
			emsHeadChanged.setDeclareState(DzscState.CHANGE);
			emsHeadChanged.setModifyMark(ModifyMarkState.MODIFIED);
		} else { // 转抄
			String copEntNo = dzscMessageLogic.getNewCopEntNo(
					"DzscEmsPorWjHead", "copTrNo", "D",
					DzscBusinessType.EMS_POR_WJ);
			emsHeadChanged.setSeqNum(Integer.valueOf(dzscDao.getNum(
					"DzscEmsPorWjHead", "seqNum")));
			emsHeadChanged.setCopTrNo(copEntNo);
			emsHeadChanged.setDeclareState(DzscState.ORIGINAL);
			emsHeadChanged.setModifyMark(ModifyMarkState.ADDED);
			emsHeadChanged.setIeContractNo(null);
			emsHeadChanged.setImContractNo(null);
			emsHeadChanged.setEmsNo(null);
			emsHeadChanged.setBeginDate(null);
			emsHeadChanged.setEndDate(null);
			emsHeadChanged.setDestroyDate(null);
			emsHeadChanged.setDeferDate(null);
			emsHeadChanged.setSancEmsNo(null);
			emsHeadChanged.setAgreementNo(null);
			emsHeadChanged.setImgAmount(null);
			emsHeadChanged.setExgAmount(null);
			emsHeadChanged.setApproveDate(null);
			emsHeadChanged.setSaveDate(new Date());
			emsHeadChanged.setIsImportFromQP(false);
			emsHeadChanged.setAclUser(CommonUtils.getAclUser());
			emsHeadChanged.setCorrEmsNo(null);
		}

		return emsHeadChanged;
	}

	/**
	 * 合同备案料件变更
	 * 
	 * @param emsHead
	 *            合同备案表头
	 * @param emsHeadChange
	 *            变更的合同备案表头
	 * @param isChange
	 *            判断变更状态，true为变更状态
	 * @return List 是DzscEmsImgWj型，变更后的合同备案料件
	 */
	private List getEmsPorImgWjChangeBean(DzscEmsPorWjHead emsHead,
			DzscEmsPorWjHead emsHeadChange, boolean isChange) { // 料件清单变更
		List list = null;
		List emsInnerChanges = new Vector();
		list = dzscDao.findEmsPorImgWj(emsHead); // 获取未变更
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgWj detail = (DzscEmsImgWj) list.get(i);
			DzscEmsImgWj detailChanged = null; // 变更
			try {
				detailChanged = (DzscEmsImgWj) BeanUtils.cloneBean(detail);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			detailChanged.setId(null);
			detailChanged.setDzscEmsPorWjHead(emsHeadChange);
			if (isChange) {
				detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				detailChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			emsInnerChanges.add(detailChanged);
		}
		return emsInnerChanges;
	}

	/**
	 * 合同备案成品变更
	 * 
	 * @param emsHead
	 *            合同备案表头
	 * @param emsHeadChange
	 *            变更的合同备案表头
	 * @param isChange
	 *            判断变更状态，true为变更状态
	 * @return List 是DzscEmsExgWj型，变更后的合同备案成品
	 */
	private List getEmsPorExgWjChangeBean(DzscEmsPorWjHead emsHead,
			DzscEmsPorWjHead emsHeadChange, boolean isChange) { // 成品清单变更
		List list = null;
		List emsInnerChanges = new Vector();
		list = dzscDao.findEmsPorExgWj(emsHead); // 获取未变更
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgWj detail = (DzscEmsExgWj) list.get(i);
			DzscEmsExgWj detailChanged = null; // 变更
			try {
				detailChanged = (DzscEmsExgWj) BeanUtils.cloneBean(detail);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			detailChanged.setId(null);
			detailChanged.setDzscEmsPorWjHead(emsHeadChange);
			if (isChange) {
				detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			} else {
				detailChanged.setModifyMark(ModifyMarkState.ADDED);
			}
			// dzscDao.saveDzscEmsExgWj(detailChanged);
			emsInnerChanges.add(detailChanged);
		}
		return emsInnerChanges;
	}

	/**
	 * 
	 * @param wjHead
	 *            合同备案表头
	 * @param wjExingHead
	 *            变更的合同备案表头
	 * @param lsReturnFile
	 *            报文信息接收List
	 * @return String 是DzscEmsPorWjHead型 变更后合同备案表头
	 */
	public String processDzscEmsPorWjHead(final DzscEmsPorWjHead wjHead,
			final DzscEmsPorWjHead wjExingHead, List lsReturnFile) {
		return dzscMessageLogic.processMessage(DzscBusinessType.EMS_POR_WJ,
				wjHead.getCopTrNo(), new CspProcessMessage() {

					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						backBillDzscEmsPorWjHead(wjHead, wjExingHead);// 生效合同为执行状态
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						effectiveDzscEmsPorWjHead(wjHead, wjExingHead,
								receiptResult);
					}
				}, lsReturnFile);
	}

	/**
	 * 生效合同备案
	 * 
	 * @param wjHead
	 *            合同备案表头
	 * @param wjExingHead
	 *            变更后合同备案表头
	 * @param receiptResult
	 *            报文信息接收
	 */
	private void effectiveDzscEmsPorWjHead(DzscEmsPorWjHead wjHead,
			DzscEmsPorWjHead wjExingHead, CspReceiptResult receiptResult) {
		if (wjExingHead != null) {
			this.dzscDao.deleteAllEmsPorWj(wjExingHead);
			this.dzscDao.deleteDzscEmsPorWjHead(wjExingHead);
		}
		wjHead.setDeclareState(DzscState.EXECUTE);
		wjHead.setModifyMark(ModifyMarkState.UNCHANGE);
		wjHead.setCorrEmsNo(receiptResult.getCorrEmsNo());
		if (wjExingHead == null) {
			wjHead.setEmsNo(receiptResult.getEmsNo());
			// wjHead.setEmsNo(receiptResult.getCorrEmsNo());
		}
		this.dzscDao.saveEmsPorWj(wjHead);
		List lsExgWj = this.dzscDao.findEmsPorExgWjStateChanged(wjHead);
		for (int i = 0; i < lsExgWj.size(); i++) {
			DzscEmsExgWj exgWj = (DzscEmsExgWj) lsExgWj.get(i);
			if (ModifyMarkState.DELETED.equals(exgWj.getModifyMark())) {
				this.dzscDao.deleteDzscEmsExgWj(exgWj);
			} else {
				exgWj.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveDzscEmsExgWj(exgWj);
			}
		}
		List lsImgWj = this.dzscDao.findEmsPorImgWjStateChanged(wjHead);
		for (int i = 0; i < lsImgWj.size(); i++) {
			DzscEmsImgWj imgWj = (DzscEmsImgWj) lsImgWj.get(i);
			if (ModifyMarkState.DELETED.equals(imgWj.getModifyMark())) {
				this.dzscDao.deleteDzscEmsImgWj(imgWj);
			} else {
				imgWj.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveDzscEmsImgWj(imgWj);
			}
		}
		if (wjExingHead == null) {
			DzscEmsPorHead billHead = new DzscEmsPorHead();
			try {
				PropertyUtils.copyProperties(billHead, wjHead);
			} catch (Exception e) {
				e.printStackTrace();
			}
			billHead.setId(null);
			billHead.setSeqNum(Integer.valueOf(dzscDao.getNum("DzscEmsPorHead",
					"seqNum")));
			billHead.setDeclareState(DzscState.EXECUTE);
			billHead.setModifyMark(ModifyMarkState.UNCHANGE);
			// billHead.setContractEmsNo(wjHead.getEmsNo());
			// 带出手册类型
			billHead.setEmsType("");
			if ("X".equals(wjHead.getEmsType())) {
				billHead.setEmsType("B");
			} else if ("Y".equals(wjHead.getEmsType())) {
				billHead.setEmsType("C");
			}
			billHead.setEmsNo(receiptResult.getCorrEmsNo());
			billHead.setCopTrNo(receiptResult.getCorrEmsNo());
			// if(receiptResult.getTrEdiHeadEml()!=null){
			// billHead.setCorrEmsNo(receiptResult.getTrEdiHeadEml().getCorrEmsNo());
			// }
			// billHead.setLastEmsNo(receiptResult.getCorrEmsNo());
			billHead.setCorrEmsNo(receiptResult.getEmsNo());
			Integer dzscManageType = null;
			if (wjHead != null) {
				dzscManageType = wjHead.getDzscManageType();
			}
			// if (dzscManageType == null) {
			// DzscParameterSet parameter = this.dzscDao
			// .findDzscParameterSet();
			// if (parameter != null) {
			// dzscManageType = parameter.getDzscManageType();
			// }
			// }
			if (dzscManageType.equals(DzscManageType.MATERIAL)) {
				List lsMaterialHead = this.dzscDao.findDzscMaterialHead();
				if (lsMaterialHead.size() <= 0 || lsMaterialHead.get(0) == null) {
					throw new RuntimeException("没有物料表头");
				}
				DzscMaterielHead materielHead = (DzscMaterielHead) lsMaterialHead
						.get(0);
				if (materielHead.getCopEntNo() == null
						|| "".equals(materielHead.getCopEntNo().trim())) {
					throw new RuntimeException("物料表头的编号为空！");
				}
				billHead.setCopEntNo(materielHead.getCopEntNo());
			}
			this.dzscDao.saveEmsPorHead(billHead);
		}
	}

	/**
	 * 生效合同备案
	 * 
	 * @param wjHead
	 *            合同备案表头
	 * @param wjExingHead
	 *            变更后合同备案表头
	 */
	private void backBillDzscEmsPorWjHead(DzscEmsPorWjHead wjHead,
			DzscEmsPorWjHead wjExingHead) {
		if (wjExingHead == null) {
			wjHead.setDeclareState(DzscState.ORIGINAL);
		} else {
			wjHead.setDeclareState(DzscState.CHANGE);
		}
		// wjHead.setDeclareState(DzscState.BACK_BILL);
		this.dzscDao.saveEmsPorWj(wjHead);
	}

	/**
	 * 保存通关备案表头
	 * 
	 * @param obj
	 *            通关备案表头
	 */
	public void saveDzscEmsPorHead(DzscEmsPorHead obj) {
		this.dzscDao.saveDzscEmsPorHead(obj);
		this.statDzscEmsPorExgBillCount(obj);
		this.statDzscEmsPorExgBillMoney(obj);
		this.statDzscEmsPorImgBillCount(obj);
		this.statDzscEmsPorImgBillMoney(obj);
	}

	/**
	 * 处理通关备案回执
	 * 
	 * @param billHead
	 *            电子手册通关备案里的表头资料
	 * @param billExingHead
	 *            电子手册通关备案里的表头资料
	 * @param lsReturnFile
	 *            报文信息接收List
	 * @return String 报文回执处理
	 */
	public String processDzscEmsPorBillHead(final DzscEmsPorHead billHead,
			final DzscEmsPorHead billExingHead, List lsReturnFile) {
		return dzscMessageLogic.processMessage(DzscBusinessType.EMS_POR_BILL,
				billHead.getCopTrNo(), new CspProcessMessage() {

					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						backBillDzscEmsPorBillHead(billHead, billExingHead);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						effectiveDzscEmsPorBillHead(billHead, billExingHead,
								receiptResult);
					}
				}, lsReturnFile);
	}

	/**
	 * 生效合同备案
	 * 
	 * @param billHead
	 *            合同备案表头
	 * @param billExingHead
	 *            变更后合同备案表头
	 * @param receiptResult
	 *            报文信息接收
	 */
	private void effectiveDzscEmsPorBillHead(DzscEmsPorHead billHead,
			DzscEmsPorHead billExingHead, CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapImg = new HashMap<Integer, Complex>();
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();
		if (billExingHead != null) {
			List lsTemp = this.dzscDao.findDzscEmsImgBill(billExingHead);
			for (int i = 0; i < lsTemp.size(); i++) {
				DzscEmsImgBill img = (DzscEmsImgBill) lsTemp.get(i);
				mapImg.put(img.getSeqNum(), img.getComplex());
			}
			lsTemp = this.dzscDao.findDzscEmsExgBill(billExingHead);
			for (int i = 0; i < lsTemp.size(); i++) {
				DzscEmsExgBill exg = (DzscEmsExgBill) lsTemp.get(i);
				mapExg.put(exg.getSeqNum(), exg.getComplex());
			}
			this.deleteDzscEmsPorHead(billExingHead);
		} else {
			billHead.setEmsNo(receiptResult.getEmsNo());
		}
		billHead.setDeclareState(DzscState.EXECUTE);
		billHead.setModifyMark(ModifyMarkState.UNCHANGE);
		this.dzscDao.saveEmsPorHead(billHead);
		List lsMaterialImg = this.dzscDao
				.findDzscEmsPorMaterialImgStateChanged(billHead);
		for (int i = 0; i < lsMaterialImg.size(); i++) {
			DzscEmsPorMaterialImg materialImg = (DzscEmsPorMaterialImg) lsMaterialImg
					.get(i);
			if (ModifyMarkState.DELETED.equals(materialImg.getModifyMark())) {
				this.dzscDao.delete(materialImg);
			} else {
				materialImg.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveOrUpdate(materialImg);
			}
		}

		List lsMaterialExg = this.dzscDao
				.findDzscEmsPorMaterialExgStateChanged(billHead);
		for (int i = 0; i < lsMaterialExg.size(); i++) {
			DzscEmsPorMaterialExg materialExg = (DzscEmsPorMaterialExg) lsMaterialExg
					.get(i);
			if (ModifyMarkState.DELETED.equals(materialExg.getModifyMark())) {
				this.dzscDao.delete(materialExg);
			} else {
				materialExg.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveOrUpdate(materialExg);
			}
		}

		List lsExgBill = this.dzscDao.findDzscEmsExgBillStateChanged(billHead);
		for (int i = 0; i < lsExgBill.size(); i++) {
			DzscEmsExgBill exgBill = (DzscEmsExgBill) lsExgBill.get(i);
			if (ModifyMarkState.DELETED.equals(exgBill.getModifyMark())) {
				this.dzscDao.deleteDzscEmsExgBill(exgBill);
			} else {
				if (ModifyMarkState.MODIFIED.equals(exgBill.getModifyMark())) {
					Complex oldComplex = mapExg.get(exgBill.getSeqNum());
					// 变更编码
					if (!exgBill.getComplex().equals(oldComplex)) {
						changeDzscEmsExgBillComplex(exgBill);
					}
				}
				exgBill.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveDzscEmsExgBill(exgBill);
			}
		}

		List lsImgBill = this.dzscDao.findDzscEmsImgBillStateChanged(billHead);
		for (int i = 0; i < lsImgBill.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) lsImgBill.get(i);
			if (ModifyMarkState.DELETED.equals(imgBill.getModifyMark())) {
				this.dzscDao.deleteDzscEmsImgBill(imgBill);
			} else {
				if (ModifyMarkState.MODIFIED.equals(imgBill.getModifyMark())) {
					Complex oldComplex = mapImg.get(imgBill.getSeqNum());
					// 变更编码
					if (!imgBill.getComplex().equals(oldComplex)) {
						changeDzscEmsImgBillComplex(imgBill);
					}
				}
				imgBill.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveDzscEmsImgBill(imgBill);
			}
		}

		List lsBomBill = this.dzscDao.findDzscEmsBomBillStateChanged(billHead);
		for (int i = 0; i < lsBomBill.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) lsBomBill.get(i);
			if (ModifyMarkState.DELETED.equals(bomBill.getModifyMark())) {
				this.dzscDao.deleteDzscEmsBomBill(bomBill);
			} else {
				bomBill.setModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscDao.saveDzscEmsBomBill(bomBill);
			}
		}
	}

	/**
	 * 变更成品编码
	 * 
	 * @param exgBill
	 *            手册通关备案的成品资料
	 */
	public void changeDzscEmsExgBillComplex(DzscEmsExgBill exgBill) {
		List list = this.dzscDao.findDzscCustomsDeclarationCommInfo(false,
				exgBill.getDzscEmsPorHead(), exgBill.getSeqNum());
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) list
					.get(i);
			commInfo.setComplex(exgBill.getComplex());
			this.dzscDao.saveOrUpdate(commInfo);
		}
	}

	/**
	 * 变更料件的商品编码
	 * 
	 * @param imgBill
	 *            电子手册通关备案里的料件资料
	 * 
	 */
	private void changeDzscEmsImgBillComplex(DzscEmsImgBill imgBill) {
		if (imgBill.getId() != null && !"".equals(imgBill.getId())) {
			List list = this.dzscDao.findDzscCustomsDeclarationCommInfo(true,
					imgBill.getDzscEmsPorHead(), imgBill.getSeqNum());
			for (int i = 0; i < list.size(); i++) {
				DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) list
						.get(i);
				commInfo.setComplex(imgBill.getComplex());
				this.dzscDao.saveOrUpdate(commInfo);
			}
		}
	}

	/**
	 * 生效合同备案
	 * 
	 * @param billHead
	 *            合同备案表头
	 * @param billExingHead
	 *            变更后合同备案表头
	 */
	private void backBillDzscEmsPorBillHead(DzscEmsPorHead billHead,
			DzscEmsPorHead billExingHead) {
		if (billExingHead == null) {
			billHead.setDeclareState(DzscState.ORIGINAL);
		} else {
			billHead.setDeclareState(DzscState.CHANGE);
		}
		this.dzscDao.saveEmsPorHead(billHead);
	}

	//
	// /**
	// * 获取符合条件的前四位商品编码
	// *
	// * @param code
	// * @param type
	// * 物料类型
	// * @return List 是DzscInnerMergeData型，归并数据
	// */
	// public List findFourComplex(String code, String type) {
	// List list = this.dzscDao.findFourComplex(code, type);
	// Set set = new HashSet();
	// for (int i = 0; i < list.size(); i++) {
	// set.add(list.get(i));
	// }
	// return new ArrayList(set);
	// }

	/**
	 * 获取dzscDao
	 * 
	 * @return Returns the dzscDao.
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
	}

	/**
	 * 设置dzscDao
	 * 
	 * @param dzscDao
	 */
	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组 来自通关备案
	 * 
	 * @param contractId
	 *            通关备案通关备案表头
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @return List 是ContractUnitWaste型，合同单耗
	 */
	public List<ContractUnitWaste> findContractUnitWasteByCustomForEdit(
			String contractId, int index, int length) {
		List<ContractUnitWaste> contractUnitWasteList = new ArrayList<ContractUnitWaste>();
		List<DzscEmsExgBill> dzscEmsExgBillList = null;
		List<DzscEmsImgBill> dzscEmsImgBillList = null;
		Map<String, DzscEmsBomBill> dzscEmsBomBillMap = new HashMap<String, DzscEmsBomBill>();
		List<DzscEmsBomBill> dzscEmsBomBillList = null;
		final int columnCount = 6;

		dzscEmsExgBillList = this.dzscDao
				.findDzscEmsExgBillUNCHANGE(contractId);
		int dzscEmsPorExgCount = dzscEmsExgBillList.size();
		dzscEmsImgBillList = this.dzscDao.findDzscEmsImgBill(contractId);
		dzscEmsBomBillList = this.dzscDao.findDzscEmsBomBill(contractId);
		for (int i = 0; i < dzscEmsBomBillList.size(); i++) {
			DzscEmsBomBill dzscEmsBomBill = (DzscEmsBomBill) dzscEmsBomBillList
					.get(i);
			//
			// 合同成品id,料件序号 == key
			//
			String key = (dzscEmsBomBill.getDzscEmsExgBill().getId() + (dzscEmsBomBill
					.getImgSeqNum() == null ? "" : dzscEmsBomBill
					.getImgSeqNum()));

			dzscEmsBomBillMap.put(key, dzscEmsBomBill);
		}

		//
		// 获得交差数据表
		//
		int groupCount = dzscEmsPorExgCount / columnCount
				+ ((dzscEmsPorExgCount % columnCount) > 0 ? 1 : 0);
		//
		// 以成品行数为6条记录进行--手动分组
		//
		for (int g = 0; g < groupCount; g++) {
			//
			// 获取以列的个数分组的临时成品列表
			//

			List<DzscEmsExgBill> tempDzscEmsExgBillList = new ArrayList<DzscEmsExgBill>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < dzscEmsPorExgCount) {
					// if (!dzscEmsExgBillList.get(i).getModifyMark().equals(
					// ModifyMarkState.UNCHANGE)) {
					tempDzscEmsExgBillList.add(dzscEmsExgBillList.get(i));
					// }
				} else {
					break;
				}
			}

			for (int i = 0; i < dzscEmsImgBillList.size(); i++) {
				DzscEmsImgBill dzscEmsImgBill = dzscEmsImgBillList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setPtNo(String.valueOf(dzscEmsImgBill
						.getSeqNum()));
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtName(dzscEmsImgBill.getName());
				contractUnitWaste.setPtSpec(dzscEmsImgBill.getSpec());
				for (int j = 0; j < tempDzscEmsExgBillList.size(); j++) {
					DzscEmsExgBill dzscEmsExgBill = tempDzscEmsExgBillList
							.get(j);
					//
					// 合同成品id,料件序号 == key
					//
					String key = (dzscEmsExgBill.getId() + (dzscEmsImgBill
							.getSeqNum() == null ? "" : dzscEmsImgBill
							.getSeqNum()));
					DzscEmsBomBill dzscEmsBomBill = dzscEmsBomBillMap.get(key);
					if (dzscEmsBomBill == null) {
						continue;
					}
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate1(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate2(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate3(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate4(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate5(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate6(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					for (int k = 0; k < tempDzscEmsExgBillList.size(); k++) {
						DzscEmsExgBill contractExg = tempDzscEmsExgBillList
								.get(k);
						switch (k) {
						case 0:
							contractUnitWaste.setExg1(String
									.valueOf(contractExg.getSeqNum()));
							break;
						case 1:
							contractUnitWaste.setExg2(String
									.valueOf(contractExg.getSeqNum()));
							break;
						case 2:
							contractUnitWaste.setExg3(String
									.valueOf(contractExg.getSeqNum()));
							break;
						case 3:
							contractUnitWaste.setExg4(String
									.valueOf(contractExg.getSeqNum()));
							break;
						case 4:
							contractUnitWaste.setExg5(String
									.valueOf(contractExg.getSeqNum()));
							break;
						case 5:
							contractUnitWaste.setExg6(String
									.valueOf(contractExg.getSeqNum()));
							break;
						}
					}
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}
		return contractUnitWasteList;
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组 来自通关备案
	 * 
	 * @param contractId
	 *            通关备案通关备案表头
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @return List 是ContractUnitWaste型，合同单耗
	 */
	public List<ContractUnitWaste> findContractUnitWasteByCustom(
			String contractId, int index, int length) {
		List<ContractUnitWaste> contractUnitWasteList = new ArrayList<ContractUnitWaste>();
		List<DzscEmsExgBill> dzscEmsExgBillList = null;
		List<DzscEmsImgBill> dzscEmsImgBillList = null;
		Map<String, DzscEmsBomBill> dzscEmsBomBillMap = new HashMap<String, DzscEmsBomBill>();
		List<DzscEmsBomBill> dzscEmsBomBillList = null;
		final int columnCount = 6;

		dzscEmsExgBillList = this.dzscDao.findDzscEmsExgBill(contractId, index,
				length);
		int dzscEmsPorExgCount = dzscEmsExgBillList.size();
		dzscEmsImgBillList = this.dzscDao.findDzscEmsImgBill(contractId);
		dzscEmsBomBillList = this.dzscDao.findDzscEmsBomBill(contractId);
		for (int i = 0; i < dzscEmsBomBillList.size(); i++) {
			DzscEmsBomBill dzscEmsBomBill = (DzscEmsBomBill) dzscEmsBomBillList
					.get(i);
			//
			// 合同成品id,料件序号 == key
			//
			String key = (dzscEmsBomBill.getDzscEmsExgBill().getId() + (dzscEmsBomBill
					.getImgSeqNum() == null ? "" : dzscEmsBomBill
					.getImgSeqNum()));

			dzscEmsBomBillMap.put(key, dzscEmsBomBill);
		}

		//
		// 获得交差数据表
		//
		int groupCount = dzscEmsPorExgCount / columnCount
				+ ((dzscEmsPorExgCount % columnCount) > 0 ? 1 : 0);
		//
		// 以成品行数为6条记录进行--手动分组
		//
		for (int g = 0; g < groupCount; g++) {
			//
			// 获取以列的个数分组的临时成品列表
			//
			List<DzscEmsExgBill> tempDzscEmsExgBillList = new ArrayList<DzscEmsExgBill>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < dzscEmsPorExgCount) {
					tempDzscEmsExgBillList.add(dzscEmsExgBillList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < dzscEmsImgBillList.size(); i++) {
				DzscEmsImgBill dzscEmsImgBill = dzscEmsImgBillList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setPtNo(String.valueOf(dzscEmsImgBill
						.getSeqNum()));
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtName(dzscEmsImgBill.getName());
				contractUnitWaste.setPtSpec(dzscEmsImgBill.getSpec());
				for (int j = 0; j < tempDzscEmsExgBillList.size(); j++) {
					DzscEmsExgBill dzscEmsExgBill = tempDzscEmsExgBillList
							.get(j);
					//
					// 合同成品id,料件序号 == key
					//
					String key = (dzscEmsExgBill.getId() + (dzscEmsImgBill
							.getSeqNum() == null ? "" : dzscEmsImgBill
							.getSeqNum()));
					DzscEmsBomBill dzscEmsBomBill = dzscEmsBomBillMap.get(key);
					if (dzscEmsBomBill == null) {
						continue;
					}
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate1(dzscEmsBomBill
								.getWare());

						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate2(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate3(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate4(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate5(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(dzscEmsBomBill
								.getUnitWare());
						contractUnitWaste.setWasteRate6(dzscEmsBomBill
								.getWare());
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					for (int k = 0; k < tempDzscEmsExgBillList.size(); k++) {
						DzscEmsExgBill contractExg = tempDzscEmsExgBillList
								.get(k);
						switch (k) {
						case 0:
							contractUnitWaste.setExg1(String
									.valueOf(contractExg.getSeqNum()));
							contractUnitWaste.setAmount1(contractExg
									.getAmount());
							break;
						case 1:
							contractUnitWaste.setExg2(String
									.valueOf(contractExg.getSeqNum()));
							contractUnitWaste.setAmount2(contractExg
									.getAmount());
							break;
						case 2:
							contractUnitWaste.setExg3(String
									.valueOf(contractExg.getSeqNum()));
							contractUnitWaste.setAmount3(contractExg
									.getAmount());
							break;
						case 3:
							contractUnitWaste.setExg4(String
									.valueOf(contractExg.getSeqNum()));
							contractUnitWaste.setAmount4(contractExg
									.getAmount());
							break;
						case 4:
							contractUnitWaste.setExg5(String
									.valueOf(contractExg.getSeqNum()));
							contractUnitWaste.setAmount5(contractExg
									.getAmount());
							break;
						case 5:
							contractUnitWaste.setExg6(String
									.valueOf(contractExg.getSeqNum()));
							contractUnitWaste.setAmount6(contractExg
									.getAmount());
							break;
						}
					}
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}
		return contractUnitWasteList;
	}

	/**
	 * 转抄通关备案成品料件单耗
	 * 
	 * @param list
	 *            是DzscEmsExgBill型，通关备案成品
	 * @param head
	 *            通关备案表头
	 * @param isProductAmountToZero
	 *            成品出口数量是否要设为零，true为设为零
	 * @param isProductPriceToZero
	 *            成品单价是否要设为零，true为设为零
	 * @param isMaterielAmountToZero
	 *            料件进口数量是否要设为零，true为设为零
	 * @param isMaterielPriceToZero
	 *            料件单价是否要设为零，true为设为零
	 * @param isUnitWasteToZero
	 *            单耗是否要设为零，true为设为零
	 */
	public void copyProductMaterielUnitWaste(List<DzscEmsExgBill> list,
			DzscEmsPorHead head, boolean isProductAmountToZero,
			boolean isProductPriceToZero, boolean isMaterielAmountToZero,
			boolean isMaterielPriceToZero, boolean isUnitWasteToZero) {
		List<DzscEmsExgBill> dzscEmsExgBillList = list;
		List<DzscEmsImgBill> dzscEmsImgBillList = null;
		List<DzscEmsBomBill> dzscEmsBomBillList = null;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		//
		// 先保存料件,用map存取老的no,和新生成的no对应
		//		
		dzscEmsImgBillList = this.dzscDao
				.findDzscEmsImgBill(dzscEmsExgBillList);
		// Integer maxValue = this.dzscDao.getNumForImgExgBill("DzscEmsImgBill",
		// head);
		for (DzscEmsImgBill d : dzscEmsImgBillList) {
			d.setId(null);
			d.setDzscEmsPorHead(head);
			// map.put(d.getNo(), maxValue);
			// d.setNo(maxValue);
			if (isMaterielAmountToZero) {
				d.setAmount(null);
			}
			if (isMaterielPriceToZero) {
				d.setPrice(null);
			}
			d.setMoney(null);
			d.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsImgBill(d);
			// maxValue++;
		}
		//
		// 保存成品和单耗
		//
		// maxValue = this.dzscDao.getNumForImgExgBill("DzscEmsExgBill", head);
		for (DzscEmsExgBill d : list) {
			String id = d.getId();
			d.setId(null);
			d.setDzscEmsPorHead(head);
			// d.setNo(maxValue);
			if (isProductAmountToZero) {
				d.setAmount(null);
			}
			if (isProductPriceToZero) {
				d.setPrice(null);
			}
			d.setMoney(null);
			d.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsExgBill(d);
			// maxValue++;
			dzscEmsBomBillList = this.dzscDao.findDzscEmsBomBill(id);
			for (DzscEmsBomBill dzscEmsBomBill : dzscEmsBomBillList) {
				dzscEmsBomBill.setDzscEmsExgBill(d);
				dzscEmsBomBill.setId(null);
				dzscEmsBomBill.setImgSeqNum(map.get(dzscEmsBomBill
						.getImgSeqNum()));
				if (isUnitWasteToZero) {
					dzscEmsBomBill.setUnitWare(null);
				}
				dzscEmsBomBill.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsBomBill(dzscEmsBomBill);
			}
		}
	}

	/**
	 * 转抄通关备案成品
	 * 
	 * @param list
	 *            是DzscEmsExgBill型，通关备案成品
	 * @param head
	 *            通关备案表头
	 * @param isProductAmountToZero
	 *            成品出口数量是否要设为零，true为设为零
	 * @param isProductPriceToZero
	 *            成品单价是否要设为零，true为设为零
	 */
	public void copyProduct(List<DzscEmsExgBill> list, DzscEmsPorHead head,
			boolean isProductAmountToZero, boolean isProductPriceToZero) {
		DzscEmsExgBill temp = list.get(0);
		// Integer maxValue = this.dzscDao.getNumForImgExgBill("DzscEmsExgBill",
		// head);
		for (DzscEmsExgBill dzscEmsExgBill : list) {
			dzscEmsExgBill.setId(null);
			// dzscEmsExgBill.setNo(maxValue);
			dzscEmsExgBill.setDzscEmsPorHead(head);
			if (isProductAmountToZero) {
				dzscEmsExgBill.setAmount(null);
			}
			if (isProductPriceToZero) {
				dzscEmsExgBill.setPrice(null);
			}
			dzscEmsExgBill.setMoney(null);
			dzscEmsExgBill.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsExgBill(dzscEmsExgBill);
			// maxValue++;
		}
	}

	/**
	 * 转抄通关备案料件
	 * 
	 * @param list
	 *            是DzscEmsImgBill型，通关备案料件
	 * @param head
	 *            通关备案表头
	 * @param isMaterielAmountToZero
	 *            料件进口数量是否要设为零，true为设为零
	 * @param isMaterielPriceToZero
	 *            料件单价是否要设为零，true为设为零
	 */
	public void copyMateiel(List<DzscEmsImgBill> list, DzscEmsPorHead head,
			boolean isMaterielAmountToZero, boolean isMaterielPriceToZero) {
		for (DzscEmsImgBill dzscEmsImgBill : list) {
			dzscEmsImgBill.setId(null);
			dzscEmsImgBill.setDzscEmsPorHead(head);
			if (isMaterielAmountToZero) {
				dzscEmsImgBill.setAmount(null);
			}
			if (isMaterielPriceToZero) {
				dzscEmsImgBill.setPrice(null);
			}
			dzscEmsImgBill.setMoney(null);
			dzscEmsImgBill.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsImgBill(dzscEmsImgBill);
		}
	}

	/**
	 * 根据通关备案的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exgBill
	 *            手册通关备案的成品资料
	 * @return List 是 DzscEmsExgBill型，手册通关备案的成品资料
	 */
	public List findMaterialExgByEmsExg(DzscEmsExgBill exgBill) {
		List lsResult = new ArrayList();
		if (exgBill.getTenSeqNum() == null) {
			throw new RuntimeException("报关商品:" + exgBill.getSeqNum() + ";"
					+ exgBill.getComplex().getCode() + "的归并序号为空");
		}
		List lsMateriel = this.dzscDao.findInnerMergeDataByTen(exgBill
				.getTenSeqNum(), exgBill.getComplex().getCode());
		if (lsMateriel.size() <= 0) {
			throw new RuntimeException("报关商品" + exgBill.getSeqNum() + ";"
					+ exgBill.getComplex().getCode() + "没有做归并");
		}
		for (int i = 0; i < lsMateriel.size(); i++) {
			Materiel master = (Materiel) lsMateriel.get(i);
			List lsVersionApply = materialApplyDao
					.findMaterialBomVersionApply(master);
			if (lsVersionApply.size() <= 0) {
				continue;
			}
			for (int j = 0; j < lsVersionApply.size(); j++) {
				MaterialBomVersionApply versionApply = (MaterialBomVersionApply) lsVersionApply
						.get(j);
				List lsDetailApply = this.materialApplyDao
						.findMaterialBomDetailApply(versionApply);
				if (lsDetailApply.size() > 0) {
					TempDzscProductVersionInfo info = new TempDzscProductVersionInfo();
					info.setParentNo(master.getPtNo());
					info.setVersionNo(versionApply.getVersion().toString());
					info.setBeginDate(versionApply.getBeginDate());
					info.setEndDate(versionApply.getEndDate());
					if (j == lsVersionApply.size() - 1) {
						info.setAmount(exgBill.getAmount());
					}
					lsResult.add(info);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 删除通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案BOM资料
	 * @return Map 是DzscEmsBomBill型,通关备案BOM资料
	 */
	public Map<Integer, List<DzscEmsBomBill>> deleteDzscEmsBomBill(List list) {
		Map<Integer, List<DzscEmsBomBill>> map = new HashMap<Integer, List<DzscEmsBomBill>>();
		List<DzscEmsBomBill> lsDelete = new ArrayList<DzscEmsBomBill>();
		List<DzscEmsBomBill> lsUpdate = new ArrayList<DzscEmsBomBill>();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
			if (ModifyMarkState.DELETED.equals(bom.getModifyMark())) {
				continue;
			}
			if (!ModifyMarkState.ADDED.equals(bom.getModifyMark())) {
				bom.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveDzscEmsBomBill(bom);
				lsUpdate.add(bom);
			} else {
				this.dzscDao.deleteDzscEmsBomBill(bom);
				lsDelete.add(bom);
			}
			this.writeBackDzscEmsExgBill(bom);
			this.writeBackDzscEmsImgBill(bom);
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 自动计算通关备案单耗
	 * 
	 * @param list
	 *            通关备案成品List
	 * @param exgBill
	 *            通关备案成品资料
	 * @return List 是DzscEmsBomBill型，返回通关备案单耗
	 */
	public List autoCalDzscEmsBomUnitWaste(List list, DzscEmsExgBill exgBill) {
		List lsResult = new ArrayList();
		Map<String, Double> hmMaterialBomUnitWaste = new HashMap<String, Double>();// 归并前单耗总数量
		Map<String, Double> hmMaterialBomUnitUsed = new HashMap<String, Double>();// 归并前单项用量总数量
		Map<Integer, Double> hmEmsUnitWasteAmount = new HashMap<Integer, Double>();// 归并后单耗总数量
		Map<Integer, Double> hmEmsUnitUsedAmount = new HashMap<Integer, Double>();// 归并后单项用量总数量
		Map<Integer, DzscEmsBomBill> hmEmsUnitUsedInfo = new HashMap<Integer, DzscEmsBomBill>();// 归并后单耗
		for (int i = 0; i < list.size(); i++) {
			TempDzscProductVersionInfo info = (TempDzscProductVersionInfo) list
					.get(i);
			if (info.getAmount() == null || info.getAmount() <= 0) {
				continue;
			}
			Double exgAmount = CommonUtils
					.getDoubleExceptNull(info.getAmount());
			String ptNo = info.getParentNo();
			Integer versionNo = Integer.valueOf(info.getVersionNo());
			List lsDetail = this.materialApplyDao.findMaterialBomDetailApply(
					ptNo, versionNo);
			for (int j = 0; j < lsDetail.size(); j++) {
				MaterialBomDetailApply detailApply = (MaterialBomDetailApply) lsDetail
						.get(j);
				String subPtNo = detailApply.getMateriel().getPtNo();
				Double oldUnitWaste = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitWaste
								.get(subPtNo));
				Double oldUnitUsed = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitUsed.get(subPtNo));
				hmMaterialBomUnitWaste.put(subPtNo, oldUnitWaste
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(detailApply
								.getUnitWaste()));
				hmMaterialBomUnitUsed.put(subPtNo, oldUnitUsed
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(detailApply
								.getUnitUsed()));
			}
		}
		Set<String> hsPtNo = hmMaterialBomUnitWaste.keySet();
		Iterator itDetail = hsPtNo.iterator();
		while (itDetail.hasNext()) {
			String detailPtNo = itDetail.next().toString();
			DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
					.findInnerMergeDataByMaterialCode(detailPtNo);
			if (innerMergeData == null
					|| innerMergeData.getDzscTenInnerMerge() == null) {
				throw new RuntimeException("料件" + detailPtNo + "没有做归并关系");
			}
			if (!DzscState.EXECUTE.equals(innerMergeData.getStateMark())) {
				throw new RuntimeException("料件" + detailPtNo + "在归并关系中没有备案");
			}
			Integer tenSeqNum = innerMergeData.getDzscTenInnerMerge()
					.getTenSeqNum();
			if (hmEmsUnitUsedInfo.get(tenSeqNum) == null) {
				List lsEmsImgBill = this.dzscDao.findDzscEmsImgBillByTenSeqNum(
						exgBill.getDzscEmsPorHead().getId(), tenSeqNum);
				if (lsEmsImgBill.size() <= 0) {
					throw new RuntimeException("归并序号为"
							+ innerMergeData.getDzscTenInnerMerge()
									.getTenSeqNum() + "的料件在料件表中不存在");
				}
				DzscEmsImgBill imgBill = (DzscEmsImgBill) lsEmsImgBill.get(0);
				DzscEmsBomBill bomBill = new DzscEmsBomBill();
				bomBill.setImgSeqNum(imgBill.getSeqNum());
				bomBill.setComplex(imgBill.getComplex());
				bomBill.setName(imgBill.getName());
				bomBill.setSpec(imgBill.getSpec());
				bomBill.setUnit(imgBill.getUnit());
				bomBill.setCountry(imgBill.getCountry());
				hmEmsUnitUsedInfo.put(imgBill.getTenSeqNum(), bomBill);
			}
			Double oldEmsUnitWaste = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double oldEmsUnitUsed = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			hmEmsUnitWasteAmount.put(tenSeqNum, oldEmsUnitWaste
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitWaste
							.get(detailPtNo)));
			hmEmsUnitUsedAmount.put(tenSeqNum, oldEmsUnitUsed
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitUsed
							.get(detailPtNo)));
		}
		Iterator itBomImgSeqNum = hmEmsUnitUsedInfo.keySet().iterator();
		Double totalExgAmount = CommonUtils.getDoubleExceptNull(exgBill
				.getAmount());
		while (itBomImgSeqNum.hasNext()) {
			Integer tenSeqNum = Integer.valueOf(itBomImgSeqNum.next()
					.toString());
			DzscEmsBomBill bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
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
			Double waste = (unitWaste == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(
							((unitUsed - unitWaste) / unitWaste) * 100, 6));
			bomBill.setUnitWare(unitWaste);
			bomBill.setWare(waste);
			bomBill.setUnitDosage(unitUsed);
			bomBill.setAmount(CommonUtils.getDoubleByDigit(totalExgAmount
					* unitUsed, 6));
			bomBill.setMoney(CommonUtils
					.getDoubleExceptNull(bomBill.getPrice())
					* CommonUtils.getDoubleExceptNull(bomBill.getAmount()));
		}
		lsResult.addAll(hmEmsUnitUsedInfo.values());
		return lsResult;
	}

	/**
	 * 保存自动计算通关备案的单耗
	 * 
	 * @param list
	 *            通关备案成品List
	 * @param exgBill
	 *            通关备案成品资料
	 */
	public void saveAutoCalDzscEmsBomUnitWaste(List list, DzscEmsExgBill exgBill) {
		String declareState = exgBill.getDzscEmsPorHead().getDeclareState();
		if (DzscState.ORIGINAL.equals(declareState)) {
			this.dzscDao.deleteAll(this.dzscDao.findEmsPorBomBill(exgBill));
			for (int i = 0; i < list.size(); i++) {
				DzscEmsBomBill bomBill = (DzscEmsBomBill) list.get(i);
				bomBill.setDzscEmsExgBill(exgBill);
				bomBill.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsBomBill(bomBill);
				this.writeBackDzscEmsExgBill(bomBill);
				this.writeBackDzscEmsImgBill(bomBill);
			}
		} else if (DzscState.CHANGE.equals(declareState)) {
			Map<Integer, DzscEmsBomBill> hmOldBomBill = new HashMap<Integer, DzscEmsBomBill>();
			List lsOldBomBill = this.dzscDao.findEmsPorBomBill(exgBill);
			for (int i = 0; i < lsOldBomBill.size(); i++) {
				DzscEmsBomBill oldBomBill = (DzscEmsBomBill) lsOldBomBill
						.get(i);
				hmOldBomBill.put(oldBomBill.getImgSeqNum(), oldBomBill);
			}
			for (int i = 0; i < list.size(); i++) {
				DzscEmsBomBill bomBill = (DzscEmsBomBill) list.get(i);
				DzscEmsBomBill oldBomBill = hmOldBomBill.get(bomBill
						.getImgSeqNum());
				if (oldBomBill != null) {
					if ((oldBomBill.getUnitDosage() != bomBill.getUnitDosage())
							&& (oldBomBill.getUnitWare() != bomBill
									.getUnitWare())
							&& (oldBomBill.getWare() != bomBill.getWare())) {
						oldBomBill.setUnitWare(bomBill.getUnitWare());
						oldBomBill.setWare(bomBill.getWare());
						oldBomBill.setUnitDosage(bomBill.getUnitDosage());
						oldBomBill.setAmount(bomBill.getAmount());
						oldBomBill.setMoney(bomBill.getMoney());
						if (!ModifyMarkState.ADDED.equals(oldBomBill
								.getModifyMark())) {
							oldBomBill.setModifyMark(ModifyMarkState.MODIFIED);
						}
						this.dzscDao.saveDzscEmsBomBill(oldBomBill);
						this.writeBackDzscEmsExgBill(oldBomBill);
						this.writeBackDzscEmsImgBill(oldBomBill);
					}
				} else {
					bomBill.setDzscEmsExgBill(exgBill);
					bomBill.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveDzscEmsBomBill(bomBill);
					this.writeBackDzscEmsExgBill(bomBill);
					this.writeBackDzscEmsImgBill(bomBill);
				}
			}
		}
	}

	/**
	 * 转抄通关备案成品单耗
	 * 
	 * @param exgBillFrom
	 *            被拷贝的通关备案成品
	 * @param exgBillTo
	 *            通关备案成品
	 */
	public void copyDzscEmsPorBomBill(DzscEmsExgBill exgBillFrom,
			DzscEmsExgBill exgBillTo) {
		this.dzscDao.deleteAll(this.dzscDao.findEmsPorBomBill(exgBillTo));
		List lsFrom = this.dzscDao.findEmsPorBomBill(exgBillFrom);
		for (int i = 0; i < lsFrom.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) lsFrom.get(i);
			DzscEmsBomBill newBomBill = new DzscEmsBomBill();
			try {
				PropertyUtils.copyProperties(newBomBill, bomBill);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			newBomBill.setDzscEmsExgBill(exgBillTo);
			newBomBill.setId(null);
			newBomBill.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(exgBillTo.getAmount())
					* CommonUtils.getDoubleExceptNull(newBomBill
							.getUnitDosage()), 5));
			newBomBill.setMoney(CommonUtils
					.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(newBomBill.getAmount())
							* CommonUtils.getDoubleExceptNull(newBomBill
									.getPrice()), 5));
			newBomBill.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsBomBill(newBomBill);
			this.writeBackDzscEmsExgBill(newBomBill);
			this.writeBackDzscEmsImgBill(newBomBill);
		}
	}

	/**
	 * "0":不处理表头 ，"1":插入新表头
	 * 
	 * @param ediNo
	 *            手册编号
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DzscEmsPorHead makeDzscHead(String ediNo) {
		List list = dzscDao.findisExistHeadBySeqNum(ediNo);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorHead head = (DzscEmsPorHead) list.get(i);
			if (head.getIsFirstDown() != null && head.getIsFirstDown()) { // 存在已经下载
				// dzscDao.deleteAllEmsPor(head); // 删除表体
				return head;
			}
		}
		return null;
	}

	/**
	 * 获取流水号，当存在时就返回，不存在时就新建一个
	 * 
	 * @param ediNo
	 *            手册编号
	 * @return string DzscEmsPorHead的流水号
	 */
	public String getSeqNum(String ediNo) {
		List list = dzscDao.findisExistHeadBySeqNum(ediNo);
		if (list != null && list.size() > 0) {
			DzscEmsPorHead head = (DzscEmsPorHead) list.get(0);
			String seqNum = String.valueOf(head.getSeqNum());
			dzscDao.deleteDzscEmsPorHead(head);
			return seqNum;
		} else {
			return dzscDao.getNum("DzscEmsPorHead", "seqNum"); // 得到新的流水号
		}
	}

	/**
	 * 删除非正在执行的通关备案表头By EdiNo
	 * 
	 * @param ediNo
	 *            手册编号
	 */
	public void deleteByEdiNoNotExcu(String ediNo) {
		List list = dzscDao.findNotExcute(ediNo);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorHead head = (DzscEmsPorHead) list.get(i);
			// dzscDao.deleteAllEmsPor(head);
			dzscDao.deleteDzscEmsPorHead(head);
		}
	}

	/**
	 * 取得String数据
	 * 
	 * @param xx
	 *            数据
	 * @return String
	 */
	private String forStr(Object xx) {
		if (xx != null) {
			return String.valueOf(xx);
		}
		return null;
	}

	/**
	 * 取得Double数据
	 * 
	 * @param xx
	 *            数据
	 * @return Double
	 */
	private Double forDou(Object xx) {
		if (xx != null) {
			return Double.valueOf(xx.toString());
		}
		return null;
	}

	/**
	 * 返回归并关系四码distinct--外经新增上方2005-8-25
	 * 
	 * @param type
	 *            物料类型
	 * @return List 电子手册商品归并资料第三层资料的临时实体类
	 */
	public List findMerger4DistinctToTemp(String type) {
		List list = dzscDao.findMerger4Distinct(type);
		List tempList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			BillTemp temp = new BillTemp();
			temp.setBill1(forStr(obj[0]));// 四吗序号
			temp.setBill2(forStr(obj[1]));// 编码
			temp.setBill3(forStr(obj[2])); // 名称
			temp.setBill4(forStr(obj[3]));// 规格
			temp.setBill5(forStr(obj[4]));// 单位
			// temp.setBillSum1(forDou(obj[5]));// 单价
			tempList.add(temp);
		}
		return tempList;
	}

	/**
	 * 返回归并关系十码distinct--外经新增下方2005-8-25
	 * 
	 * @param type
	 *            物料类型
	 * @param fourSeqNum
	 *            四码序号
	 * @return List 电子手册商品归并资料第二层资料的临时实体类
	 */
	public List findMerger10DistinceToTemp(String type, String fourSeqNum) {
		List list = dzscDao.findMerger10Distince(type, fourSeqNum);
		List tempList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			BillTemp temp = new BillTemp();
			temp.setBill1(forStr(obj[0]));// 10吗序号
			temp.setBill2(forStr(obj[1]));// 编码
			temp.setBill3(forStr(obj[2])); // 名称
			temp.setBill4(forStr(obj[3]));// 规格
			temp.setBill5(forStr(obj[4]));// 单位
			// temp.setBillSum1(forDou(obj[5]));// 单价
			tempList.add(temp);
		}
		return tempList;
	}

	/**
	 * 保存合同备案料件
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	public List saveWjImg(List list, DzscEmsPorWjHead head) {
		List listImgExg = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgWj img = (DzscEmsImgWj) list.get(i);
			img.setSeqNum(this.dzscDao.getMaxDzscEmsImgWjNum(head));
			img.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsImgWj(img);
			listImgExg.add(img);
		}
		statDzscEmsPorWjImgCount(head);
		return listImgExg;
	}

	/**
	 * 保存合同备案料件
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	public List saveWjImgFromComplex(List list, DzscEmsPorWjHead head) {
		List listImgExg = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			DzscEmsImgWj img = new DzscEmsImgWj();
			img.setDzscEmsPorWjHead(head);
			img.setSeqNum(this.dzscDao.getMaxDzscEmsImgWjNum(head));
			img.setFourComplex(complex.getCode());
			img.setFourName(complex.getName());
			img.setFirstUnit(complex.getFirstUnit());
			img.setSecondUnit(complex.getSecondUnit());
			img.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsImgWj(img);
			listImgExg.add(img);
		}
		statDzscEmsPorWjImgCount(head);
		return listImgExg;
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	public List saveWjExgFromComplex(List list, DzscEmsPorWjHead head) {
		List listImgExg = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			DzscEmsExgWj exg = new DzscEmsExgWj();
			exg.setDzscEmsPorWjHead(head);
			exg.setSeqNum(this.dzscDao.getMaxDzscEmsExgWjNum(head));
			exg.setFourComplex(complex.getCode());
			exg.setFourName(complex.getName());
			exg.setFirstUnit(complex.getFirstUnit());
			exg.setSecondUnit(complex.getSecondUnit());
			exg.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsExgWj(exg);
			listImgExg.add(exg);
		}
		statDzscEmsPorWjExgCount(head);
		return listImgExg;
	}

	/**
	 * 统计合同备案的料件项数
	 * 
	 * @param head
	 *            电子手册合同备案表头资料
	 */
	private void statDzscEmsPorWjImgCount(DzscEmsPorWjHead head) {
		head = this.dzscDao.findDzscEmsPorWjHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setMaterielItemCount(this.dzscDao.findDzscEmsImgWjCount(head
				.getId()));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveEmsPorWjHead(head);
	}

	/**
	 * 统计合同备案的成品项数
	 * 
	 * @param head
	 *            电子手册合同备案表头资料
	 */
	private void statDzscEmsPorWjExgCount(DzscEmsPorWjHead head) {
		head = this.dzscDao.findDzscEmsPorWjHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setProductItemCount(this.dzscDao.findDzscEmsExgWjCount(head
				.getId()));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveEmsPorWjHead(head);
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param list
	 *            是DzscEmsExgWj型，合同备案成品
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsExgWj型，合同备案成品
	 */
	public List saveWjExg(List list, DzscEmsPorWjHead head) {
		List listImgExg = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgWj exg = (DzscEmsExgWj) list.get(i);
			exg.setSeqNum(this.dzscDao.getMaxDzscEmsExgWjNum(head));
			exg.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsExgWj(exg);
			listImgExg.add(exg);
		}
		statDzscEmsPorWjExgCount(head);
		return listImgExg;
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param obj
	 *            电子手册合同备案里的料件资料
	 */
	public void saveDzscEmsImgWj(DzscEmsImgWj obj) {
		if (ModifyMarkState.UNCHANGE.equals(obj.getModifyMark())) {
			obj.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveDzscEmsImgWj(obj);
	}

	/**
	 * 删除合同备案料件
	 * 
	 * @param obj
	 *            电子手册合同备案里的料件资料
	 */
	public void deleteDzscEmsImgWj(DzscEmsImgWj obj) {
		this.dzscDao.deleteDzscEmsImgWj(obj);
		statDzscEmsPorWjImgCount(obj.getDzscEmsPorWjHead());
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param obj
	 *            电子手册合同备案里的成品资料
	 */
	public void saveDzscEmsExgWj(DzscEmsExgWj obj) {
		if (ModifyMarkState.UNCHANGE.equals(obj.getModifyMark())) {
			obj.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveDzscEmsExgWj(obj);
	}

	/**
	 * 删除合同备案成品
	 * 
	 * @param obj
	 *            电子手册合同备案里的成品资料
	 */
	public void deleteDzscEmsExgWj(DzscEmsExgWj obj) {
		this.dzscDao.deleteDzscEmsExgWj(obj);
		statDzscEmsPorWjExgCount(obj.getDzscEmsPorWjHead());
	}

	/**
	 * 将合同备案成品表示为删除标志
	 * 
	 * @param imgWj
	 *            电子手册合同备案里的料件资料
	 */
	public void markDeleteDzscEmsImgWj(DzscEmsImgWj imgWj) {
		int count = this.dzscDao.findDzscEmsImgBillCount(imgWj
				.getDzscEmsPorWjHead().getEmsNo(), imgWj.getFourSeqNum());
		if (count > 0) {
			throw new RuntimeException("此四码对应的十码商品在通关备案中已经存在，不能删除");
		}
		imgWj.setModifyMark(ModifyMarkState.DELETED);
		this.dzscDao.saveDzscEmsImgWj(imgWj);
		statDzscEmsPorWjImgCount(imgWj.getDzscEmsPorWjHead());
	}

	/**
	 * 将合同备案料件表示为删除标志
	 * 
	 * @param exgWj
	 *            电子手册合同备案里的成品资料
	 */
	public void markDeleteDzscEmsExgWj(DzscEmsExgWj exgWj) {
		int count = this.dzscDao.findDzscEmsExgBillCount(exgWj
				.getDzscEmsPorWjHead().getEmsNo(), exgWj.getFourSeqNum());
		if (count > 0) {
			throw new RuntimeException("此四码对应的十码商品在通关备案中已经存在，不能删除");
		}
		exgWj.setModifyMark(ModifyMarkState.DELETED);
		this.dzscDao.saveDzscEmsExgWj(exgWj);
		statDzscEmsPorWjExgCount(exgWj.getDzscEmsPorWjHead());
	}

	/**
	 * 保存通关备案BOM
	 * 
	 * @param list
	 *            是DzscEmsBomBill型，保存通关备案BOM
	 */
	public void saveBomLists(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
			this.dzscDao.saveDzscEmsBomBill(bom);
		}
	}

	/**
	 * 保存通关备案成品
	 * 
	 * @param list
	 *            是DzscEmsExgBill型，保存通关备案成品
	 */
	public void saveExgLists(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgBill exg = (DzscEmsExgBill) list.get(i);
			this.dzscDao.saveDzscEmsExgBill(exg);
		}
	}

	/**
	 * 保存通关备案料件
	 * 
	 * @param list
	 *            是DzscEmsImgBill型，保存通关备案料件
	 */
	public void saveImgLists(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill exg = (DzscEmsImgBill) list.get(i);
			this.dzscDao.saveDzscEmsImgBill(exg);
		}
	}

	/**
	 * 通过内部件号找到清单数据
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return List 当十位编码序号存在时返回通关备案料件或成品，否则返回Null
	 */
	public List findBillByMaterielPtNo(String emsNo, String ptNo, String type) {
		Integer seqNum = this.dzscDao.findSeqNumByPtNo(ptNo, type);
		if (seqNum != null) {
			return this.dzscDao.findImgExgBillBySeqNum(seqNum, type, emsNo);
		} else {
			return new Vector();
		}
	}

	/**
	 * 删除通关备案
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	public void deleteDzscEmsPorHead(DzscEmsPorHead head) {
		// this.dzscDao.deleteAll(this.dzscDao.findDzscEmsPorMaterialExg(head));
		// this.dzscDao.deleteAll(this.dzscDao.findDzscEmsPorMaterialImg(head));
		// String dele =
		// " Delete from DzscEmsPorMaterialExg where dzscEmsPorHead='"
		// + head.getId() + "'";// 删除归并成品
		// dele = " Delete from DzscEmsPorMaterialImg where dzscEmsPorHead='"
		// + head.getId() + "'";// 删除归并料件
		// toolsLogic.executeSql(dele);
		// dele =
		// " Delete from DzscEmsBomBill where dzscEmsExgBill in (select id from DzscEmsExgBill  "
		// + "where dzscEmsPorHead='" + head.getId() + "')";
		// toolsLogic.executeSql(dele);
		// dele = " Delete from DzscEmsExgBill where dzscEmsPorHead='"
		// + head.getId() + "'";// 删除成品
		// toolsLogic.executeSql(dele);
		// dele = " Delete from DzscEmsImgBill where dzscEmsPorHead='"
		// + head.getId() + "'";// 删除料件
		// toolsLogic.executeSql(dele);
		// this.dzscDao.deleteAll(this.dzscDao.findDzscEmsBomBill(head.getId()));
		// this.dzscDao.deleteAll(this.dzscDao.findDzscEmsExgBill(head));
		// this.dzscDao.deleteAll(this.dzscDao.findDzscEmsImgBill(head.getId()));
		// List list=this.dzscDao.findDzscEmsExgBill(head);
		// for(int i=0;i<list.size();i++){
		// DzscEmsExgBill dzscEmsExgBill=(DzscEmsExgBill)list.get(i);
		// this.dzscDao.deleteDzscEmsBomBill(dzscEmsExgBill);
		// }
		this.dzscDao.deleteDzscEmsBomBill(head);
		this.dzscDao.deleteDzscEmsExgBill(head);
		this.dzscDao.deleteDzscEmsImgBill(head);
		this.dzscDao.deleteDzscEmsPorMaterialImg(head);
		this.dzscDao.deleteDzscEmsPorMaterialExg(head);

		this.dzscDao.deleteDzscEmsPorHead(head);
	}

	/**
	 * 变更手册分册
	 * 
	 * @param fas
	 *            手册分册
	 * @return DzscEmsPorHeadFas 变更后手册分册
	 */
	public DzscEmsPorHeadFas emsHeadFasChange(DzscEmsPorHeadFas fas) {
		DzscEmsPorHeadFas emsHeadChange = dzscEmsPorHeadFasChange(fas);
		this.dzscDao.saveDzscEmsPorHeadFas(emsHeadChange);
		return emsHeadChange;
	}

	/**
	 * 手册分册表头
	 * 
	 * @param emsHead
	 *            手册分册
	 * @return DzscEmsPorHeadFas 变更后手册分册
	 */
	private DzscEmsPorHeadFas dzscEmsPorHeadFasChange(DzscEmsPorHeadFas emsHead) { // 电子分册表头变更
		DzscEmsPorHeadFas dzscEmsPorHeadFasChanged = null; // 变更
		try {
			dzscEmsPorHeadFasChanged = (DzscEmsPorHeadFas) BeanUtils
					.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		int changeTimes = emsHead.getModifyTimes().intValue();
		dzscEmsPorHeadFasChanged.setDeclareType("2"); // 申报类型 1,申请备案 2,申请变更
		dzscEmsPorHeadFasChanged.setDeclareState(DzscState.CHANGE); // 审批状态
		// 1，申请变更
		dzscEmsPorHeadFasChanged.setModifyMark(ModifyMarkState.UNCHANGE);// (未修改)
		dzscEmsPorHeadFasChanged.setModifyTimes(Integer
				.valueOf(changeTimes + 1)); // 变更次数加
		// 1
		dzscEmsPorHeadFasChanged.setCheckMark("0");
		dzscEmsPorHeadFasChanged.setExeMark("2");
		dzscEmsPorHeadFasChanged.setCompany(CommonUtils.getCompany());
		dzscEmsPorHeadFasChanged.setId(null);
		return dzscEmsPorHeadFasChanged;
	}

	/**
	 * 查询当前手册料件或成品的备案或报关信息(数量和金额),以便于控制
	 * 
	 * @param dzscEmsPorHead
	 *            通关备案通关备案表头
	 * @param isMaterial
	 *            是否为料件，true为料件
	 * @param seqNum
	 *            凭证序号
	 * @return TempDzscCommMoneyAmountInfo 临时实体类，料件或成品的备案或报关信息(数量和金额）
	 */
	public TempDzscCommMoneyAmountInfo findDzscCurrentEmsCommInfo(
			DzscEmsPorHead dzscEmsPorHead, boolean isMaterial, Integer seqNum) {
		TempDzscCommMoneyAmountInfo commInfo = new TempDzscCommMoneyAmountInfo();
		DzscEmsPorWjHead wjHead = this.dzscDao
				.findExingDzscEmsPorWjHeadByEmsNo(dzscEmsPorHead.getCorrEmsNo());// getCorrEmsNo
		double imgTotalMoney = (wjHead == null ? 0 : CommonUtils
				.getDoubleExceptNull(wjHead.getImgAmount()));
		double exgTotalMoney = (wjHead == null ? 0 : CommonUtils
				.getDoubleExceptNull(wjHead.getExgAmount()));
		System.out.println("imgTotalMoney:" + imgTotalMoney + ";exgTotalMoney:"
				+ exgTotalMoney);
		if (isMaterial) {
			commInfo.setDzscEmsTotalMoney(imgTotalMoney);
			DzscEmsImgBill imgBill = null;
			List list = this.dzscDao.findDzscEmsImgBillBySeqNum(dzscEmsPorHead
					.getId(), seqNum);
			if (list.size() > 0) {
				imgBill = (DzscEmsImgBill) list.get(0);
			}
			double allImgMoney = this.dzscDao
					.findEmsPorImgBillMoney(dzscEmsPorHead);
			commInfo.setCanPutOnRecordsMoney(CommonUtils
					.getDoubleExceptNull(imgTotalMoney)
					- allImgMoney
					+ (imgBill == null ? 0.0 : CommonUtils
							.getDoubleExceptNull(imgBill.getMoney())));
			// 全部(已生效+未生效)料件进口数量
			double allDirectImport = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null, dzscEmsPorHead
									.getEmsNo(), null, null, -1);
			// 全部(已生效+未生效)转厂进口数量
			double allTransferFactoryImport = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							dzscEmsPorHead.getEmsNo(), null, null, -1);
			// 全部(已生效+未生效)料件退换出口数量
			double allExchangeExport = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" },
							dzscEmsPorHead.getEmsNo(), null, null, -1);
			commInfo.setCustomsDeclarationAmount(allDirectImport
					+ allTransferFactoryImport - allExchangeExport);
		} else {
			commInfo.setDzscEmsTotalMoney(exgTotalMoney);
			DzscEmsExgBill exgBill = null;
			List list = this.dzscDao.findDzscEmsExgBillBySeqNum(dzscEmsPorHead
					.getId(), seqNum);
			if (list.size() > 0) {
				exgBill = (DzscEmsExgBill) list.get(0);
			}
			double allExgMoney = this.dzscDao
					.findEmsPorExgBillMoney(dzscEmsPorHead);
			commInfo.setCanPutOnRecordsMoney(CommonUtils
					.getDoubleExceptNull(exgTotalMoney)
					- allExgMoney
					+ (exgBill == null ? 0.0 : CommonUtils
							.getDoubleExceptNull(exgBill.getMoney())));
			// 全部(已生效+未生效)成品出口数量
			double allDirectExport = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, dzscEmsPorHead
									.getEmsNo(), null, null, -1);
			// 全部(已生效+未生效)转厂出口数量
			double allTransferFactoryExport = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							dzscEmsPorHead.getEmsNo(), null, null, -1);
			// 全部(已生效+未生效)成品退厂返工数量
			double allBackFactoryRework = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null,
							dzscEmsPorHead.getEmsNo(), null, null, -1);
			// 全部(已生效+未生效)成品返工复出数量
			double allReworkExport = this.dzscContractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, dzscEmsPorHead
									.getEmsNo(), null, null, -1);
			commInfo.setCustomsDeclarationAmount(allDirectExport
					+ allTransferFactoryExport - allBackFactoryRework
					+ allReworkExport);
		}
		return commInfo;
	}

	/**
	 * 保存通关备案料件资料
	 * 
	 * @param obj
	 *            通关备案料件资料
	 */
	public void saveDzscEmsImgBill(DzscEmsImgBill obj) {
		this.dzscDao.saveDzscEmsImgBill(obj);
		if (obj.getId() != null && !"".equals(obj.getId())) {
			List list = this.dzscDao.findDzscEmsBomBillByImg(obj
					.getDzscEmsPorHead(), obj);
			for (int i = 0; i < list.size(); i++) {
				DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
				bom.setName(obj.getName());
				bom.setSpec(obj.getSpec());
				bom.setComplex(obj.getComplex());
				bom.setUnit(obj.getUnit());
				bom.setUnitWeight(obj.getUnitWeight());
				bom.setCountry(obj.getCountry());
				bom.setPrice(obj.getPrice());
				bom.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(bom.getAmount())
						* CommonUtils.getDoubleExceptNull(bom.getPrice()), 5));
				this.dzscDao.saveDzscEmsBomBill(bom);
				this.writeBackDzscEmsExgBill(bom);
			}
		}
		this.statDzscEmsPorImgBillCount(obj.getDzscEmsPorHead());
		this.statDzscEmsPorImgBillMoney(obj.getDzscEmsPorHead());
	}
	/**
	 * 保存通关备案料件资料
	 * 
	 * @param obj
	 *            通关备案料件资料
	 *            与SaveDzscEmsImgBill相同，省略重复代码提高效率
	 *            2010-06-28hcl
	 */
	public void onlySaveDzscEmsImgBill(DzscEmsImgBill obj) {
		this.dzscDao.saveDzscEmsImgBill(obj);
		this.statDzscEmsPorImgBillCount(obj.getDzscEmsPorHead());
		this.statDzscEmsPorImgBillMoney(obj.getDzscEmsPorHead());
	}

	/**
	 * 保存通关备案成品资料
	 * 
	 * @param obj
	 *            通关备案成品资料
	 * @return String 是 DzscEmsExgBill型 根据BOM的单项用量来反写料件的数量和金额
	 * 
	 */
	public String saveDzscEmsExgBill(DzscEmsExgBill obj) {
		String result = "";
		this.dzscDao.saveDzscEmsExgBill(obj);
		result = this.writeBackDzscEmsBomBill(obj);
		this.statDzscEmsPorExgBillCount(obj.getDzscEmsPorHead());
		this.statDzscEmsPorExgBillMoney(obj.getDzscEmsPorHead());
		return result;
	}

	/**
	 * 删除通关备案料件资料
	 * 
	 * @param lsDzscEmsImgBill
	 *            通关备案料件资料
	 * @return Map 是 DzscEmsImgBill型 通关备案料件资料
	 */
	public Map<Integer, List<DzscEmsImgBill>> deleteDzscEmsImgBill(
			List lsDzscEmsImgBill) {
		Map<Integer, List<DzscEmsImgBill>> map = new HashMap<Integer, List<DzscEmsImgBill>>();
		List<DzscEmsImgBill> lsDelete = new ArrayList<DzscEmsImgBill>();
		List<DzscEmsImgBill> lsUpdate = new ArrayList<DzscEmsImgBill>();
		DzscEmsPorHead head = null;
		if (lsDzscEmsImgBill.size() > 0) {
			head = ((DzscEmsImgBill) (lsDzscEmsImgBill.get(0)))
					.getDzscEmsPorHead();
		}
		for (int i = 0; i < lsDzscEmsImgBill.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) lsDzscEmsImgBill.get(i);
			if (ModifyMarkState.DELETED.equals(imgBill.getModifyMark())) {
				continue;
			}
			if (ModifyMarkState.ADDED.equals(imgBill.getModifyMark())) {
				this.deleteDzscEmsPorMaterialImg(this.dzscDao
						.findDzscEmsPorMaterialImg(imgBill));
				this.deleteDzscEmsBomBill(this.dzscDao.findDzscEmsBomBillByImg(
						head, imgBill));
				this.dzscDao.deleteDzscEmsImgBill(imgBill);
				lsDelete.add(imgBill);
			} else {
				int count = this.dzscDao
						.findDzscCustomsDeclarationCommInfoCount(imgBill
								.getDzscEmsPorHead().getEmsNo(), imgBill
								.getSeqNum(), true);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此料件"
							+ imgBill.getSeqNum() + ":"
							+ imgBill.getComplex().getCode() + ",所以不能删除");
				}
				this.deleteDzscEmsPorMaterialImg(this.dzscDao
						.findDzscEmsPorMaterialImg(imgBill));
				this.deleteDzscEmsBomBill(this.dzscDao.findDzscEmsBomBillByImg(
						head, imgBill));
				imgBill.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveDzscEmsImgBill(imgBill);
				lsUpdate.add(imgBill);
			}
		}
		if (head != null) {
			this.statDzscEmsPorImgBillCount(head);
			this.statDzscEmsPorImgBillMoney(head);
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	public List findDzscEmsBomBill(List lsDzscEmsImgBill){
		DzscEmsPorHead head = null;
		if (lsDzscEmsImgBill.size() > 0) {
			head = ((DzscEmsImgBill) (lsDzscEmsImgBill.get(0)))
					.getDzscEmsPorHead();
		}
		List<DzscEmsImgBill>  overlap = new ArrayList<DzscEmsImgBill>();
		List<DzscEmsBomBill> list=new ArrayList();
		for (int i = 0; i < lsDzscEmsImgBill.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) lsDzscEmsImgBill.get(i);
			list=this.dzscDao.findDzscEmsBomBillByImg(
					head, imgBill);
			if(list.size()>0){
				overlap.add(imgBill);
			}
			
		}
		return overlap;
	}
	/**
	 * 
	 * 删除通关备案成品资料
	 * 
	 * @param lsDzscEmsExgBill
	 *            通关备案成品资料
	 * @return Map 是 DzscEms成品成品ExgBill型 通关备案成品资料
	 */
	public Map<Integer, List<DzscEmsExgBill>> deleteDzscEmsExgBill(
			List lsDzscEmsExgBill) {
		Map<Integer, List<DzscEmsExgBill>> map = new HashMap<Integer, List<DzscEmsExgBill>>();
		List<DzscEmsExgBill> lsDelete = new ArrayList<DzscEmsExgBill>();
		List<DzscEmsExgBill> lsUpdate = new ArrayList<DzscEmsExgBill>();
		DzscEmsPorHead head = null;
		if (lsDzscEmsExgBill.size() > 0) {
			head = ((DzscEmsExgBill) (lsDzscEmsExgBill.get(0)))
					.getDzscEmsPorHead();
		}
		for (int i = 0; i < lsDzscEmsExgBill.size(); i++) {
			DzscEmsExgBill exgBill = (DzscEmsExgBill) lsDzscEmsExgBill.get(i);
			if (ModifyMarkState.DELETED.equals(exgBill.getModifyMark())) {
				continue;
			}
			if (ModifyMarkState.ADDED.equals(exgBill.getModifyMark())) {
				this.deleteDzscEmsPorMaterialExg(this.dzscDao
						.findDzscEmsPorMaterialExg(exgBill));
				this.deleteDzscEmsBomBill(this.dzscDao
						.findDzscEmsBomBill(exgBill));
				this.dzscDao.deleteDzscEmsExgBill(exgBill);
				lsDelete.add(exgBill);
			} else {
				int count = this.dzscDao
						.findDzscCustomsDeclarationCommInfoCount(exgBill
								.getDzscEmsPorHead().getEmsNo(), exgBill
								.getSeqNum(), false);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此成品"
							+ exgBill.getSeqNum() + ":"
							+ exgBill.getComplex().getCode() + ",所以不能删除");
				}
				this.deleteDzscEmsPorMaterialExg(this.dzscDao
						.findDzscEmsPorMaterialExg(exgBill));
				this.deleteDzscEmsBomBill(this.dzscDao
						.findDzscEmsBomBill(exgBill));
				exgBill.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveDzscEmsExgBill(exgBill);
				lsUpdate.add(exgBill);
			}
		}
		if (head != null) {
			this.statDzscEmsPorExgBillCount(head);
			this.statDzscEmsPorExgBillMoney(head);
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 查询不在通关备案归并的料件或成品
	 * 
	 * @param type
	 *            物料类型
	 * @param head
	 *            电子手册通关备案里的表头资料
	 * @return List 是DzscInnerMergeData型 通关备案归并的料件或成品
	 */
	public List findInnerMergeNotInDzscEmsPor(String type, DzscEmsPorHead head) {
		List list = this.dzscInnerMergeDao.findDzscInnerMergeData(type,
				DzscState.EXECUTE, false);
		List lsExist = new ArrayList();
		if (MaterielType.MATERIEL.equals(type)) {
			lsExist = this.dzscDao.findDzscEmsPorMaterialImgPtNo(head);
		} else if (MaterielType.FINISHED_PRODUCT.equals(type)) {
			lsExist = this.dzscDao.findDzscEmsPorMaterialExgPtNo(head);
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (lsExist.contains(data.getMateriel().getPtNo())) {
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * 新增通关备案归并料件
	 * 
	 * @param data
	 *            电子手册通关备案里的表头资料
	 * @param list
	 *            通关备案归并料件
	 * @return List 是DzscEmsPorMaterialImg型 通关备案归并料件
	 */
	public List addDzscEmsPorMaterialImg(DzscEmsPorHead head, List list) {
		List<DzscEmsPorMaterialImg> lsResult = new ArrayList<DzscEmsPorMaterialImg>();
		Map<Integer, DzscEmsImgBill> hmImgBill = new HashMap<Integer, DzscEmsImgBill>();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			DzscEmsPorMaterialImg materialImg = new DzscEmsPorMaterialImg();
			Integer tenSeqNum = data.getDzscTenInnerMerge().getTenSeqNum();
			DzscEmsImgBill imgBill = hmImgBill.get(tenSeqNum);
			if (imgBill == null) {
				List lsImgBill = this.dzscDao.findDzscEmsImgBillByTenSeqNum(
						head.getId(), tenSeqNum);
				if (lsImgBill.size() > 0) {
					imgBill = (DzscEmsImgBill) lsImgBill.get(0);
				} else {
					imgBill = new DzscEmsImgBill();
					imgBill.setDzscEmsPorHead(head);
					imgBill
							.setSeqNum(this.dzscDao
									.getMaxDzscEmsPorImgNum(head));
					imgBill.setTenSeqNum(tenSeqNum);
					imgBill.setComplex(data.getDzscTenInnerMerge()
							.getTenComplex());
					imgBill.setName(data.getDzscTenInnerMerge().getTenPtName());
					imgBill.setSpec(data.getDzscTenInnerMerge().getTenPtSpec());
					imgBill.setUnit(data.getDzscTenInnerMerge().getTenUnit());
					imgBill.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveDzscEmsImgBill(imgBill);
				}
				hmImgBill.put(tenSeqNum, imgBill);
			}
			materialImg.setDzscEmsPorHead(head);
			materialImg.setDzscEmsImgBill(imgBill);
			materialImg.setMateriel(data.getMateriel());
			materialImg.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveOrUpdate(materialImg);
			lsResult.add(materialImg);
		}
		this.statDzscEmsPorImgBillCount(head);
		this.statDzscEmsPorImgBillMoney(head);
		return lsResult;
	}

	/**
	 * 新增通关备案归并成品 电子手册通关备案里的表头资料
	 * 
	 * @param data
	 *            电子手册通关备案里的表头资料
	 * @param list
	 *            通关备案归并成品
	 * @return List 是DzscEmsPorMaterialExg型 通关备案归并成品
	 */
	public List addDzscEmsPorMaterialExg(DzscEmsPorHead head, List list) {
		List<DzscEmsPorMaterialExg> lsResult = new ArrayList<DzscEmsPorMaterialExg>();
		Map<Integer, DzscEmsExgBill> hmExgBill = new HashMap<Integer, DzscEmsExgBill>();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			DzscEmsPorMaterialExg materialExg = new DzscEmsPorMaterialExg();
			Integer tenSeqNum = data.getDzscTenInnerMerge().getTenSeqNum();
			DzscEmsExgBill exgBill = hmExgBill.get(tenSeqNum);
			if (exgBill == null) {
				List lsExgBill = this.dzscDao.findDzscEmsExgBillByTenSeqNum(
						head.getId(), tenSeqNum);
				if (lsExgBill.size() > 0) {
					exgBill = (DzscEmsExgBill) lsExgBill.get(0);
				} else {
					exgBill = new DzscEmsExgBill();
					exgBill.setDzscEmsPorHead(head);
					exgBill
							.setSeqNum(this.dzscDao
									.getMaxDzscEmsPorExgNum(head));
					exgBill.setTenSeqNum(tenSeqNum);
					exgBill.setComplex(data.getDzscTenInnerMerge()
							.getTenComplex());
					exgBill.setName(data.getDzscTenInnerMerge().getTenPtName());
					exgBill.setSpec(data.getDzscTenInnerMerge().getTenPtSpec());
					exgBill.setUnit(data.getDzscTenInnerMerge().getTenUnit());
					exgBill.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveDzscEmsExgBill(exgBill);
				}
				hmExgBill.put(tenSeqNum, exgBill);
			}
			materialExg.setDzscEmsPorHead(head);
			materialExg.setDzscEmsExgBill(exgBill);
			materialExg.setMateriel(data.getMateriel());
			materialExg.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveOrUpdate(materialExg);
			lsResult.add(materialExg);
		}
		this.statDzscEmsPorExgBillCount(head);
		this.statDzscEmsPorExgBillMoney(head);
		return lsResult;
	}

	/**
	 * 删除通关备案归并料件
	 * 
	 * @param data
	 *            电子手册通关备案里的表头资料
	 * 
	 * @param list
	 *            通关备案归并料件
	 * 
	 * @return List 是DzscEmsPorMaterialImg型 通关备案归并料件
	 */
	public Map<Integer, List<DzscEmsPorMaterialImg>> deleteDzscEmsPorMaterialImg(
			List list) {
		Map<Integer, List<DzscEmsPorMaterialImg>> map = new HashMap<Integer, List<DzscEmsPorMaterialImg>>();
		List<DzscEmsPorMaterialImg> lsDelete = new ArrayList<DzscEmsPorMaterialImg>();
		List<DzscEmsPorMaterialImg> lsUpdate = new ArrayList<DzscEmsPorMaterialImg>();
		if (list.size() <= 0) {
			return map;
		}
		DzscEmsPorHead head = ((DzscEmsPorMaterialImg) list.get(0))
				.getDzscEmsPorHead();
		List<DzscEmsImgBill> lsImgBill = new ArrayList<DzscEmsImgBill>();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialImg materialImg = (DzscEmsPorMaterialImg) list
					.get(i);
			DzscEmsImgBill imgBill = materialImg.getDzscEmsImgBill();
			if (!lsImgBill.contains(imgBill)) {
				lsImgBill.add(imgBill);
			}
			if (ModifyMarkState.ADDED.equals(materialImg.getModifyMark())) {
				this.dzscDao.delete(materialImg);
				lsDelete.add(materialImg);
			} else {
				materialImg.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveOrUpdate(materialImg);
				lsUpdate.add(materialImg);
			}
		}
		for (int i = 0; i < lsImgBill.size(); i++) {
			DzscEmsImgBill imgBill = lsImgBill.get(i);
			if (this.dzscDao.findDzscEmsPorMaterialImgCount(imgBill) <= 0) {
				if (ModifyMarkState.ADDED.equals(imgBill.getModifyMark())) {
					this.dzscDao.delete(imgBill);
				} else {
					int count = this.dzscDao
							.findDzscCustomsDeclarationCommInfoCount(imgBill
									.getDzscEmsPorHead().getEmsNo(), imgBill
									.getSeqNum(), true);
					if (count > 0) {
						throw new RuntimeException("已经有报关单用到料件"
								+ imgBill.getComplex().getCode() + "，所以不能删除");
					}
					imgBill.setModifyMark(ModifyMarkState.DELETED);
					this.dzscDao.saveOrUpdate(imgBill);
				}
			}
		}
		this.statDzscEmsPorImgBillCount(head);
		this.statDzscEmsPorImgBillMoney(head);
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 删除通关备案归并成品
	 * 
	 * @param data
	 *            电子手册通关备案里的表头资料
	 * @param list
	 *            通关备案归并成品
	 * @return Map 是DzscEmsPorMaterialExg型 通关备案归并成品
	 */
	public Map<Integer, List<DzscEmsPorMaterialExg>> deleteDzscEmsPorMaterialExg(
			List list) {
		Map<Integer, List<DzscEmsPorMaterialExg>> map = new HashMap<Integer, List<DzscEmsPorMaterialExg>>();
		List<DzscEmsPorMaterialExg> lsDelete = new ArrayList<DzscEmsPorMaterialExg>();
		List<DzscEmsPorMaterialExg> lsUpdate = new ArrayList<DzscEmsPorMaterialExg>();
		if (list.size() <= 0) {
			return map;
		}
		DzscEmsPorHead head = ((DzscEmsPorMaterialExg) list.get(0))
				.getDzscEmsPorHead();
		List<DzscEmsExgBill> lsExgBill = new ArrayList<DzscEmsExgBill>();
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialExg materialExg = (DzscEmsPorMaterialExg) list
					.get(i);
			DzscEmsExgBill exgBill = materialExg.getDzscEmsExgBill();
			if (!lsExgBill.contains(exgBill)) {
				lsExgBill.add(exgBill);
			}
			if (ModifyMarkState.ADDED.equals(materialExg.getModifyMark())) {
				this.dzscDao.delete(materialExg);
				lsDelete.add(materialExg);
			} else {
				materialExg.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveOrUpdate(materialExg);
				lsUpdate.add(materialExg);
			}
		}
		for (int i = 0; i < lsExgBill.size(); i++) {
			DzscEmsExgBill exgBill = lsExgBill.get(i);
			if (this.dzscDao.findDzscEmsPorMaterialExgCount(exgBill) <= 0) {
				if (ModifyMarkState.ADDED.equals(exgBill.getModifyMark())) {
					this.deleteDzscEmsBomBill(this.dzscDao
							.findDzscEmsBomBill(exgBill));
					this.dzscDao.delete(exgBill);
				} else {
					int count = this.dzscDao
							.findDzscCustomsDeclarationCommInfoCount(exgBill
									.getDzscEmsPorHead().getEmsNo(), exgBill
									.getSeqNum(), false);
					if (count > 0) {
						throw new RuntimeException("已经有报关单用到成品"
								+ exgBill.getComplex().getCode() + "，所以不能删除");
					}
					this.deleteDzscEmsBomBill(this.dzscDao
							.findDzscEmsBomBill(exgBill));
					exgBill.setModifyMark(ModifyMarkState.DELETED);
					this.dzscDao.saveOrUpdate(exgBill);
				}
			}
		}
		this.statDzscEmsPorExgBillCount(head);
		this.statDzscEmsPorExgBillMoney(head);
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 保存电子手册分册
	 * 
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 * @return dzscEmsPorHeadFas 电子手册分册
	 */
	public void saveDzscEmsPorHeadFas(DzscEmsPorHeadFas dzscEmsPorHeadFas) {
		if (dzscEmsPorHeadFas.getCopEmsNo() == null
				|| "".equals(dzscEmsPorHeadFas.getCopEmsNo().trim())) {
			String copEntNo = dzscMessageLogic.getNewCopEntNo(
					"DzscEmsPorHeadFas", "copEmsNo", "D",
					DzscBusinessType.FASCICULE);
			dzscEmsPorHeadFas.setCopEmsNo(copEntNo);
		}
		this.dzscDao.saveDzscEmsPorHeadFas(dzscEmsPorHeadFas);
	}

	/**
	 * 电子分册申报
	 * 
	 * @param data
	 *            电子手册分册表头资料
	 * @param taskId
	 * 
	 * @return 是DeclareFileInfo型 生成报文的文件名
	 */
	public DeclareFileInfo applyDzscEmsPorHeadFas(DzscEmsPorHeadFas data,
			String taskId) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscEmsPorHeadFas> dataList = new ArrayList<DzscEmsPorHeadFas>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		List lsDzscEmsPorHead = this.dzscDao.findDzscEmsPorHeadExcu(data
				.getEmsNo());
		DzscEmsPorHead dzscEmsPorHead = lsDzscEmsPorHead.size() > 0 ? (DzscEmsPorHead) lsDzscEmsPorHead
				.get(0)
				: null;
		if (dzscEmsPorHead == null) {
			throw new RuntimeException("系统找不到正在执行的手册" + data.getEmsNo());
		}
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscEmsPorHead.getManageObject());
		signInfo.setSysType(DzscBusinessType.FASCICULE);
		signInfo.setCopNo(data.getCopEmsNo());
		// signInfo.setTradeCode(data.getTradeCode());// 企业编码
		signInfo.setColDcrTime(0);// 核查次数
		signInfo.setSignDate(new Date());// 签名日期
		if (DzscState.CHANGE.equals(data.getDeclareState())) {
			signInfo.setDeclareType(DzscDelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		data.setInputEr(signInfo.getIdCard().substring(
				signInfo.getIdCard().length() - 4));
		data.setDeclareState(DzscState.APPLY);
		data.setDeclareDate(new Date());// 申报日期
		data.setDeclareTime(new Date()); // 申报时间
		data.setDeclareState(signInfo.getDeclareType());
		this.dzscDao.saveDzscEmsPorHeadFas(data);
		// data.setMaterielItemCount(156846);
		// data.setProductItemCount(556854);
		String formatFile = "DzscEmsPorFasFormat.xml";

		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("DzscFasHead", dataList);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 电子手册分册回执处理
	 * 
	 * @param data
	 *            电子手册分册表头资料
	 * @param lsReturnFile
	 *            回执内容
	 * @return String 是 DzscEmsPorHeadFas型 电子手册分册回执处理
	 */
	public String proccessDzscEmsPorHeadFas(final DzscEmsPorHeadFas data,
			List lsReturnFile) {
		return dzscMessageLogic.processMessage(DzscBusinessType.FASCICULE, data
				.getCopEmsNo(), new CspProcessMessage() {

			public void failureHandling(
					TempCspReceiptResultInfo tempReceiptResult) {
				voidbackBillDzscEmsPorHeadFas(data);
			}

			public void successHandling(
					TempCspReceiptResultInfo tempReceiptResult) {
				effectiveDzscEmsPorHeadFas(data);
			}
		}, lsReturnFile);

	}

	/**
	 * 报文申报成功回执处理
	 * 
	 * @param data
	 *            电子手册分册表头资料
	 */
	private void effectiveDzscEmsPorHeadFas(DzscEmsPorHeadFas data) {
		List<DzscEmsPorHeadFas> list = this.dzscDao
				.findDzscEmsProHeadFasByFasEmsNoandState(data.getEmsNo(), data
						.getFasEmsNo(), DzscState.EXECUTE);
		for (DzscEmsPorHeadFas de : list) {
			this.dzscDao.deleteDzscEmsPorHeadFas(de);
		}
		data.setDeclareState(DzscState.EXECUTE);
		this.dzscDao.saveDzscEmsPorHeadFas(data);

	}

	/**
	 * 根据BOM的单项用量来反写料件的数量和金额
	 * 
	 * @param dzscEmsBomBill
	 * 
	 *            手册通关备案的BOM资料
	 * @return String 是 DzscEmsImgBill 型 根据BOM的单项用量来反写料件的数量和金额
	 */
	private String writeBackDzscEmsImgBill(DzscEmsBomBill dzscEmsBomBill) {
		String resul = "";
		DzscParameterSet parameter = this.dzscDao.findDzscParameterSet();
		if (parameter.getUpdateEmsExgBomWriteBackImg() == null
				|| !parameter.getUpdateEmsExgBomWriteBackImg()) {
			return "";
		}
		int countBit = parameter.getCountBit() == null ? 5 : parameter
				.getCountBit();
		int moneyBit = parameter.getMoneyBit() == null ? 5 : parameter
				.getMoneyBit();
		if (dzscEmsBomBill == null || dzscEmsBomBill.getImgSeqNum() == null) {
			return "";
		}
		DzscEmsPorHead dzscEmsPorHead = dzscEmsBomBill.getDzscEmsExgBill()
				.getDzscEmsPorHead();
		List list = this.dzscDao.findDzscEmsImgBillBySeqNum(dzscEmsPorHead
				.getId(), dzscEmsBomBill.getImgSeqNum());
		if (list.size() > 0 && list.get(0) != null) {
			DzscEmsImgBill dzscEmsImgBill = (DzscEmsImgBill) list.get(0);
			if (dzscEmsImgBill == null || dzscEmsImgBill.getSeqNum() == null) {
				return "";
			}
			Double amount = this.dzscDao.findDzscEmsBomImgUsedAmount(
					dzscEmsPorHead, dzscEmsImgBill.getSeqNum());
			if (!amount.equals(dzscEmsImgBill.getAmount())) {
				if (ModifyMarkState.UNCHANGE.equals(dzscEmsImgBill
						.getModifyMark())) {
					dzscEmsImgBill.setModifyMark(ModifyMarkState.MODIFIED);
				}
				dzscEmsImgBill.setAmount(CommonUtils.getDoubleByDigit(amount,
						countBit));
				dzscEmsImgBill.setMoney(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(dzscEmsImgBill
								.getAmount())
								* CommonUtils
										.getDoubleExceptNull(dzscEmsImgBill
												.getPrice()), moneyBit));
				this.dzscDao.saveDzscEmsImgBill(dzscEmsImgBill);
				// 当修改数量小于已进口数量，提示
				Double[] imgEms = processImgRemainContractMountAndMoney(dzscEmsImgBill);
				if (imgEms[0] > CommonUtils.getDoubleExceptNull(dzscEmsImgBill
						.getAmount())) {
					resul = "料件备案－－备案序号："
							+ dzscEmsImgBill.getSeqNum()
							+ "  归并序号："
							+ dzscEmsImgBill.getTenSeqNum()
							+ "\n已进口数量："
							+ imgEms[0]
							+ "  料件当前数量："
							+ CommonUtils.getDoubleExceptNull(dzscEmsImgBill
									.getAmount()) + "\n------------------\n";
				}
			}
			this.statDzscEmsPorImgBillMoney(dzscEmsImgBill.getDzscEmsPorHead());
		}
		return resul;
	}

	/**
	 * 根据BOM的单项用量来反写料件的数量和金额
	 * 
	 * @param dzscEmsExgBill
	 * 
	 *            手册通关备案的成品资料
	 * @return String 是 DzscEmsBomBill 型 根据BOM的单项用量来反写料件的数量和金额
	 */
	private String writeBackDzscEmsBomBill(DzscEmsExgBill dzscEmsExgBill) {
		String result = "";
		String str = "";
		if (dzscEmsExgBill == null || dzscEmsExgBill.getSeqNum() == null) {
			return "";
		}
		DzscParameterSet parameter = this.dzscDao.findDzscParameterSet();
		if (parameter.getUpdateEmsExgBomWriteBackImg() == null
				|| !parameter.getUpdateEmsExgBomWriteBackImg()) {
			return "";
		}
		int countBit = parameter.getCountBit() == null ? 5 : parameter
				.getCountBit();
		int moneyBit = parameter.getMoneyBit() == null ? 5 : parameter
				.getMoneyBit();
		List list = this.dzscDao.findDzscEmsBomBill(dzscEmsExgBill);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsBomBill dzscEmsBomBill = (DzscEmsBomBill) list.get(i);
			Double amount = CommonUtils.getDoubleExceptNull(dzscEmsBomBill
					.getUnitDosage())
					* CommonUtils.getDoubleExceptNull(dzscEmsExgBill
							.getAmount());
			if (!amount.equals(dzscEmsBomBill.getAmount())) {
				dzscEmsBomBill.setAmount(CommonUtils.getDoubleByDigit(amount,
						countBit));
				dzscEmsBomBill.setMoney(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(dzscEmsBomBill
								.getAmount())
								* CommonUtils
										.getDoubleExceptNull(dzscEmsBomBill
												.getPrice()), moneyBit));
				// 单耗、损耗改变时 ，标志位才改变。否则数量、金额等栏位变化时不改变标志位。
				// if (ModifyMarkState.UNCHANGE.equals(dzscEmsBomBill
				// .getModifyMark())) {
				// dzscEmsBomBill.setModifyMark(ModifyMarkState.MODIFIED);
				// }
				this.dzscDao.saveDzscEmsBomBill(dzscEmsBomBill);
				str = this.writeBackDzscEmsImgBill(dzscEmsBomBill);
				if (str != null && !"".equals(str)) {
					result += str;
				}
			}
		}
		return result;
	}

	/**
	 * 根据BOM的单项用量来反写成品的数量和金额
	 * 
	 * @param dzscEmsBomBill
	 *            手册通关备案的BOM资料
	 * 
	 */
	private void writeBackDzscEmsExgBill(DzscEmsBomBill dzscEmsBomBill) {
		DzscParameterSet parameter = this.dzscDao.findDzscParameterSet();
		if (parameter.getUpdateEmsBomWriteBackExg() == null
				|| !parameter.getUpdateEmsBomWriteBackExg()) {
			return;
		}
		int priceBit = parameter.getPriceBit() == null ? 5 : parameter
				.getPriceBit();
		int moneyBit = parameter.getMoneyBit() == null ? 5 : parameter
				.getMoneyBit();
		DzscEmsExgBill dzscEmsExgBill = dzscEmsBomBill.getDzscEmsExgBill();
		Double materielFee = CommonUtils.getDoubleByDigit(this
				.getFinishProductMaterielFee(dzscEmsExgBill), 5);
		// Double unitNetWeight = CommonUtils.getDoubleByDigit(this
		// .getFinishProductUnitWeight(contractExg.getId()), 4);
		dzscEmsExgBill.setImgMoney(materielFee);
		// contractExg.setUnitNetWeight(unitNetWeight);
		/**
		 * 成品单价 = 原料费 + 加工费单价
		 */
		Double unitPrice = CommonUtils.getDoubleExceptNull(materielFee)
				+ CommonUtils.getDoubleExceptNull(dzscEmsExgBill
						.getMachinPrice());
		if (!unitPrice.equals(dzscEmsExgBill.getPrice())) {
			dzscEmsExgBill.setPrice(CommonUtils.getDoubleByDigit(unitPrice,
					priceBit));
			dzscEmsExgBill.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(dzscEmsExgBill.getPrice())
					* CommonUtils.getDoubleExceptNull(dzscEmsExgBill
							.getAmount()), moneyBit));
			if (ModifyMarkState.UNCHANGE.equals(dzscEmsExgBill.getModifyMark())) {
				dzscEmsExgBill.setModifyMark(ModifyMarkState.MODIFIED);
			}
			this.dzscDao.saveDzscEmsExgBill(dzscEmsExgBill);
		}
		this.statDzscEmsPorExgBillMoney(dzscEmsExgBill.getDzscEmsPorHead());
	}

	/**
	 * 获得合同成品原料费用
	 * 
	 * @param dzscEmsExgBill
	 *            手册通关备案的成品资料
	 * 
	 * @return Double 合同成品原料费用
	 */
	private Double getFinishProductMaterielFee(DzscEmsExgBill dzscEmsExgBill) {
		// double returnValue = this.dzscDao
		// .findDzscEmsBomTotalPrice(dzscEmsExgBill);
		// double finishProductQantity = CommonUtils
		// .getDoubleExceptNull(dzscEmsExgBill.getAmount());
		// if (finishProductQantity == 0) {
		// return 0.0;
		// }
		// return returnValue / finishProductQantity;
		List list = this.dzscDao.findDzscEmsBomBill(dzscEmsExgBill);
		double exgUnitPrice = 0.0;
		for (int i = 0; i < list.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) list.get(i);
			if (!ModifyMarkState.DELETED.equals(bomBill.getModifyMark())) {
				exgUnitPrice += CommonUtils.getDoubleExceptNull(bomBill
						.getPrice())
						* CommonUtils.getDoubleByDigit(CommonUtils
								.getDoubleByDigit(bomBill.getUnitWare(), 9)
								/ (1 - CommonUtils.getDoubleByDigit(bomBill
										.getWare(), 5) / 100.0), 5);
			}
		}
		return CommonUtils.getDoubleByDigit(exgUnitPrice, 5);
	}

	/**
	 * 返回电子手册分册表头资料
	 * 
	 * @param data
	 *            电子手册分册表头资料
	 */
	private void voidbackBillDzscEmsPorHeadFas(DzscEmsPorHeadFas data) {
		data.setDeclareState(DzscState.ORIGINAL);
		this.dzscDao.saveDzscEmsPorHeadFas(data);

	}

	/**
	 * 查询归并前归并后的BOM单耗差异
	 * 
	 * @param list
	 *            成品资料
	 * @param exgBill
	 *            手册通关备案的成品资料
	 * @return List 是TempDzscCustomsFactoryUnitWasteDiff型 归并前归并后的BOM单耗差异
	 */
	public List findDzscCustomsFactoryUnitWasteDiff(List list,
			DzscEmsExgBill exgBill) {
		List lsResult = new ArrayList();
		Map<String, Double> hmMaterialBomUnitWaste = new HashMap<String, Double>();// 归并前单耗总数量
		Map<String, Double> hmMaterialBomUnitUsed = new HashMap<String, Double>();// 归并前单项用量总数量
		Map<Integer, Double> hmEmsUnitWasteAmount = new HashMap<Integer, Double>();// 归并后单耗总数量
		Map<Integer, Double> hmEmsUnitUsedAmount = new HashMap<Integer, Double>();// 归并后单项用量总数量
		Map<Integer, DzscEmsBomBill> hmEmsUnitUsedInfo = new HashMap<Integer, DzscEmsBomBill>();
		Map<Integer, TempDzscCustomsFactoryUnitWasteDiff> hmTempDiff = new HashMap<Integer, TempDzscCustomsFactoryUnitWasteDiff>();// 归并后单耗
		List lsBomBill = this.dzscDao.findDzscEmsBomBill(exgBill);
		for (int i = 0; i < lsBomBill.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) lsBomBill.get(i);
			hmEmsUnitUsedInfo.put(bomBill.getImgSeqNum(), bomBill);
		}
		for (int i = 0; i < list.size(); i++) {
			TempDzscProductVersionInfo info = (TempDzscProductVersionInfo) list
					.get(i);
			if (info.getAmount() == null || info.getAmount() <= 0) {
				continue;
			}
			Double exgAmount = CommonUtils
					.getDoubleExceptNull(info.getAmount());
			String ptNo = info.getParentNo();
			Integer versionNo = Integer.valueOf(info.getVersionNo());
			List lsDetail = this.materialApplyDao.findMaterialBomDetailApply(
					ptNo, versionNo);
			for (int j = 0; j < lsDetail.size(); j++) {
				MaterialBomDetailApply detailApply = (MaterialBomDetailApply) lsDetail
						.get(j);
				String subPtNo = detailApply.getMateriel().getPtNo();
				Double oldUnitWaste = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitWaste
								.get(subPtNo));
				Double oldUnitUsed = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitUsed.get(subPtNo));
				hmMaterialBomUnitWaste.put(subPtNo, oldUnitWaste
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(detailApply
								.getUnitWaste()));
				hmMaterialBomUnitUsed.put(subPtNo, oldUnitUsed
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(detailApply
								.getUnitUsed()));
			}
		}
		Set<String> hsPtNo = hmMaterialBomUnitWaste.keySet();
		Iterator itDetail = hsPtNo.iterator();
		while (itDetail.hasNext()) {
			String detailPtNo = itDetail.next().toString();
			DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
					.findInnerMergeDataByMaterialCode(detailPtNo);
			if (innerMergeData == null
					|| innerMergeData.getDzscTenInnerMerge() == null) {
				throw new RuntimeException("料件" + detailPtNo + "没有做归并关系");
			}
			if (!DzscState.EXECUTE.equals(innerMergeData.getStateMark())) {
				throw new RuntimeException("料件" + detailPtNo + "在归并关系中没有备案");
			}
			Integer tenSeqNum = innerMergeData.getDzscTenInnerMerge()
					.getTenSeqNum();
			if (hmTempDiff.get(tenSeqNum) == null) {
				List lsEmsImgBill = this.dzscDao.findDzscEmsImgBillByTenSeqNum(
						exgBill.getDzscEmsPorHead().getId(), tenSeqNum);
				if (lsEmsImgBill.size() <= 0) {
					throw new RuntimeException("归并序号为"
							+ innerMergeData.getDzscTenInnerMerge()
									.getTenSeqNum() + "的料件在料件表中不存在");
				}
				DzscEmsImgBill imgBill = (DzscEmsImgBill) lsEmsImgBill.get(0);
				TempDzscCustomsFactoryUnitWasteDiff tempDiff = new TempDzscCustomsFactoryUnitWasteDiff();
				tempDiff.setImgSeqNum(imgBill.getSeqNum());
				tempDiff.setComplex(imgBill.getComplex());
				tempDiff.setName(imgBill.getName());
				tempDiff.setSpec(imgBill.getSpec());
				hmTempDiff.put(imgBill.getTenSeqNum(), tempDiff);
			}
			Double oldEmsUnitWaste = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double oldEmsUnitUsed = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			hmEmsUnitWasteAmount.put(tenSeqNum, oldEmsUnitWaste
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitWaste
							.get(detailPtNo)));
			hmEmsUnitUsedAmount.put(tenSeqNum, oldEmsUnitUsed
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitUsed
							.get(detailPtNo)));
		}
		Iterator itBomImgSeqNum = hmTempDiff.keySet().iterator();
		Double totalExgAmount = CommonUtils.getDoubleExceptNull(exgBill
				.getAmount());
		while (itBomImgSeqNum.hasNext()) {
			Integer tenSeqNum = Integer.valueOf(itBomImgSeqNum.next()
					.toString());
			TempDzscCustomsFactoryUnitWasteDiff tempDiff = hmTempDiff
					.get(tenSeqNum);
			DzscEmsBomBill bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
			if (tempDiff == null) {
				continue;
			}
			if (bomBill != null) {
				tempDiff.setCustomsUnitWare(bomBill.getUnitWare());
				tempDiff.setCustomsWare(bomBill.getWare());
				hmEmsUnitUsedInfo.remove(tenSeqNum);
			} else {
				tempDiff.setCustomsUnitWare(0.0);
				tempDiff.setCustomsWare(0.0);
			}
			Double unitWasteAmount = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double unitUsedAmount = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			Double unitWaste = (totalExgAmount == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(unitWasteAmount / totalExgAmount, 6));
			Double unitUsed = (totalExgAmount == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(unitUsedAmount / totalExgAmount, 6));
			Double waste = (unitWaste == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(
							((unitUsed - unitWaste) / unitWaste) * 100, 6));
			tempDiff.setFactoryUnitWare(unitWaste);
			tempDiff.setFactoryWare(waste);

			tempDiff.setUnitWareDiff(CommonUtils.getDoubleExceptNull(tempDiff
					.getCustomsUnitWare())
					- CommonUtils.getDoubleExceptNull(tempDiff
							.getFactoryUnitWare()));
			tempDiff.setWareDiff(CommonUtils.getDoubleExceptNull(tempDiff
					.getCustomsWare())
					- CommonUtils
							.getDoubleExceptNull(tempDiff.getFactoryWare()));
		}
		itBomImgSeqNum = hmEmsUnitUsedInfo.keySet().iterator();
		while (itBomImgSeqNum.hasNext()) {
			Integer tenSeqNum = Integer.valueOf(itBomImgSeqNum.next()
					.toString());
			DzscEmsBomBill bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
			TempDzscCustomsFactoryUnitWasteDiff tempDiff = new TempDzscCustomsFactoryUnitWasteDiff();
			tempDiff.setImgSeqNum(bomBill.getImgSeqNum());
			tempDiff.setComplex(bomBill.getComplex());
			tempDiff.setName(bomBill.getName());
			tempDiff.setSpec(bomBill.getSpec());

			tempDiff.setCustomsUnitWare(bomBill.getUnitWare());
			tempDiff.setCustomsWare(bomBill.getWare());
			tempDiff.setFactoryUnitWare(0.0);
			tempDiff.setFactoryWare(0.0);
			tempDiff.setUnitWareDiff(CommonUtils.getDoubleExceptNull(tempDiff
					.getCustomsUnitWare())
					- CommonUtils.getDoubleExceptNull(tempDiff
							.getFactoryUnitWare()));
			tempDiff.setWareDiff(CommonUtils.getDoubleExceptNull(tempDiff
					.getCustomsWare())
					- CommonUtils
							.getDoubleExceptNull(tempDiff.getFactoryWare()));
			hmTempDiff.put(bomBill.getImgSeqNum(), tempDiff);
		}
		lsResult.addAll(hmTempDiff.values());
		return lsResult;
	}

	/**
	 * 通关备案料件进出平衡检查
	 * 
	 * @param dzscEmsPorHead
	 *            电子手册通关备案里的表头资料
	 * @return List 是TempDzscImgAndExgUsedDiffAmount型 通关备案料件进出平衡检查
	 */
	public List findDzscEmsImgAndExgUsedDiffAmount(DzscEmsPorHead dzscEmsPorHead) {
		List lsResult = new ArrayList();
		List lsImg = this.dzscDao
				.findDzscEmsImgBillExceptDeleted(dzscEmsPorHead.getId());
		List lsUsed = this.dzscDao
				.findDzscEmsImgBillAmountByExgUsed(dzscEmsPorHead);
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		for (int i = 0; i < lsUsed.size(); i++) {
			Object[] objs = (Object[]) lsUsed.get(i);
			if (objs[0] != null) {
				map.put(Integer.valueOf(objs[0].toString().trim()),
						objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
								.toString().trim()));
			}
		}
		for (int i = 0; i < lsImg.size(); i++) {
			DzscEmsImgBill img = (DzscEmsImgBill) lsImg.get(i);
			TempDzscImgAndExgUsedDiffAmount diff = new TempDzscImgAndExgUsedDiffAmount();
			diff.setSeqNum(img.getSeqNum());
			diff.setCommNameSpec((img.getName() == null ? "" : img.getName())
					+ "/" + (img.getSpec() == null ? "" : img.getSpec()));
			diff.setImgAmount(CommonUtils.getDoubleByDigit(img.getAmount(), 5));
			diff.setExgUsedAmount(CommonUtils.getDoubleByDigit(map.get(img
					.getSeqNum()), 5));
			diff.setDiffAmount(diff.getImgAmount() - diff.getExgUsedAmount());
			diff.setDiffRate(diff.getImgAmount() == 0.0 ? 0.0 : CommonUtils
					.getDoubleByDigit((diff.getDiffAmount() / diff
							.getImgAmount()) * 100.0, 2));
			lsResult.add(diff);
		}
		return lsResult;
	}

	/**
	 * 查找海关商品编码资料
	 * 
	 * @param code
	 *            海关商品编码资料
	 * 
	 * @return Complex 自用商品编码资料
	 */
	public Complex findCustomsComplexByCode(String code) {

		List ls = this.dzscDao.findCustomsComplex(code);
		if (ls != null && ls.size() > 0) {
			CustomsComplex obj = (CustomsComplex) ls.get(0);
			Complex c = new Complex();
			c.setId(obj.getCode());
			c.setCode(obj.getCode());
			c.setName(obj.getName());
			c.setFirstUnit(obj.getFirstUnit());
			c.setSecondUnit(obj.getSecondUnit());
			c.setLowrate(obj.getLowrate());
			c.setHighrate(obj.getHighrate());
			c.setNote(obj.getNote());
			this.dzscDao.saveOrUpdate(c);
			return c;
		}
		return null;
	}

	/**
	 * 保存通关备案料件归并资料
	 * 
	 * @param ls
	 *            通关备案料件归并
	 * @param isOverwrite
	 *            是否覆盖导入
	 */
	public void saveDzscEmsImgFromImport(List ls, boolean isOverwrite) {
		DzscEmsPorHead dzscEmsPorHead = null;
		if (ls.size() > 0) {
			dzscEmsPorHead = ((TempDzscEmsImgBill) ls.get(0)).getImg()
					.getDzscEmsPorHead();
		} else {
			return;
		}
		boolean isMaterielManageType = isMaterielManageType(dzscEmsPorHead);
		Map<Integer, List<Materiel>> map = new HashMap<Integer, List<Materiel>>();
		List lsInnerMerge = this.dzscInnerMergeDao
				.findDzscInnerMergeDataTenSeqNumAndMateriel(
						MaterielType.MATERIEL, DzscState.EXECUTE);
		for (int i = 0; i < lsInnerMerge.size(); i++) {
			Object[] objs = (Object[]) lsInnerMerge.get(i);
			if (objs[0] == null || objs[1] == null) {
				continue;
			}
			Integer tenSeqNum = Integer.parseInt(objs[1].toString());
			List<Materiel> lsMateriel = map.get(tenSeqNum);
			if (lsMateriel == null) {
				lsMateriel = new ArrayList<Materiel>();
				map.put(tenSeqNum, lsMateriel);
			}
			lsMateriel.add((Materiel) objs[0]);
		}
		// List lsImgSeqNum = this.dzscDao
		// .findDzscEmsImgBillSeqNum(dzscEmsPorHead);
		List lsImgBill = this.dzscDao.findDzscEmsImgBill(dzscEmsPorHead);
		Map<Integer, DzscEmsImgBill> imgMap = new HashMap<Integer, DzscEmsImgBill>();
		for (int i = 0; i < lsImgBill.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) lsImgBill.get(i);
			imgMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ls.size(); i++) {
			DzscEmsImgBill imgBill = imgMap
					.get(((TempDzscEmsImgBill) ls.get(i)).getImg().getSeqNum());
			if (imgBill == null) {// 如果不存在，则直接保存
				imgBill = ((TempDzscEmsImgBill) ls.get(i)).getImg();
				imgBill.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsImgBill(imgBill);
				if (imgBill.getTenSeqNum() != null) {
					addDzscEmsPorMaterialImgWhenImport(dzscEmsPorHead,
							isMaterielManageType, map, imgBill);
				} else {
					String customsNo = imgBill.getCustomsNo();
					if (customsNo != null && !"".equals(customsNo.trim())) {
						DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
								.findInnerMergeDataByCustomsNo(customsNo);
						if (innerMergeData == null) {
							throw new RuntimeException("在归并关系中没有找到报关助记码"
									+ customsNo + "所对应的归并关系");
						}
						if (innerMergeData != null
								&& innerMergeData.getDzscTenInnerMerge() != null
								&& innerMergeData.getDzscTenInnerMerge()
										.getTenSeqNum() != null) {
							imgBill.setTenSeqNum(innerMergeData
									.getDzscTenInnerMerge().getTenSeqNum());
							addDzscEmsPorMaterialImgWhenImport(dzscEmsPorHead,
									isMaterielManageType, map, imgBill);
						} else {
							throw new RuntimeException("在归并关系中,物料"
									+ innerMergeData.getMateriel().getPtNo()
									+ "所对应的10码序号为空！ ");
						}
					}
					if (isMaterielManageType && imgBill.getTenSeqNum() == null) {
						throw new RuntimeException("手册"
								+ dzscEmsPorHead.getEmsNo() + "是以料号为管理单元的，"
								+ "\r\n所以要导入的备案序号为" + imgBill.getSeqNum()
								+ "的料件的归并序号不能为空！");
					}
				}
			} else {
				if (isOverwrite) {
					Integer oldTenSeqNum = imgBill.getTenSeqNum();
					DzscEmsImgBill img = ((TempDzscEmsImgBill) ls.get(i))
							.getImg();
					String customsNo = img.getCustomsNo();
					if (img.getTenSeqNum() == null && customsNo != null
							&& !"".equals(customsNo.trim())) {
						DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
								.findInnerMergeDataByCustomsNo(customsNo);
						if (innerMergeData == null) {
							throw new RuntimeException("在归并关系中没有找到报关助记码"
									+ customsNo + "所对应的归并关系");
						}
						if (innerMergeData != null
								&& innerMergeData.getDzscTenInnerMerge() != null
								&& innerMergeData.getDzscTenInnerMerge()
										.getTenSeqNum() != null) {
							img.setTenSeqNum(innerMergeData
									.getDzscTenInnerMerge().getTenSeqNum());
						} else {
							throw new RuntimeException("在归并关系中,物料"
									+ innerMergeData.getMateriel().getPtNo()
									+ "所对应的10码序号为空！ ");
						}
					}
					if (img.getTenSeqNum() == null && isMaterielManageType) {
						throw new RuntimeException("手册"
								+ dzscEmsPorHead.getEmsNo() + "是以料号为管理单元的，"
								+ "\r\n所以要导入的备案序号为" + imgBill.getSeqNum()
								+ "的料件的归并序号不能为空！");
					}
					imgBill.setComplex(img.getComplex());
					imgBill.setName(img.getName());
					imgBill.setSpec(img.getSpec());
					imgBill.setPrice(img.getPrice());
					imgBill.setAmount(img.getAmount());
					imgBill.setMoney(img.getMoney());
					imgBill.setUnit(img.getUnit());
					imgBill.setCountry(img.getCountry());
					imgBill.setLevyMode(img.getLevyMode());
					imgBill.setTenSeqNum(img.getTenSeqNum());
					imgBill.setCustomsNo(img.getCustomsNo());
					if (ModifyMarkState.UNCHANGE
							.equals(imgBill.getModifyMark())) {
						imgBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.dzscDao.saveDzscEmsImgBill(imgBill);
					if (oldTenSeqNum == null && imgBill.getTenSeqNum() != null) {
						addDzscEmsPorMaterialImgWhenImport(dzscEmsPorHead,
								isMaterielManageType, map, imgBill);
					} else if (oldTenSeqNum != null
							&& imgBill.getTenSeqNum() == null) {
						deleteDzscEmsPorMaterialImgWhenImport(this.dzscDao
								.findDzscEmsPorMaterialImg(imgBill));
					} else if (oldTenSeqNum != null
							&& imgBill.getTenSeqNum() != null
							&& !oldTenSeqNum.equals(imgBill.getTenSeqNum())) {
						deleteDzscEmsPorMaterialImgWhenImport(this.dzscDao
								.findDzscEmsPorMaterialImg(imgBill));
						addDzscEmsPorMaterialImgWhenImport(dzscEmsPorHead,
								isMaterielManageType, map, imgBill);
					}
				} else {
					continue;
				}
			}
		}
		this.statDzscEmsPorImgBillCount(dzscEmsPorHead);
		this.statDzscEmsPorImgBillMoney(dzscEmsPorHead);
	}

	/**
	 * 删除通关备案料件归并资料
	 * 
	 * @param list
	 *            通关备案料件归并
	 */
	private void deleteDzscEmsPorMaterialImgWhenImport(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialImg materialImg = (DzscEmsPorMaterialImg) list
					.get(i);
			if (ModifyMarkState.ADDED.equals(materialImg.getModifyMark())) {
				this.dzscDao.delete(materialImg);
			} else {
				materialImg.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveOrUpdateNoCache(materialImg);
			}
		}
	}

	/**
	 * 增加电子手册归并料件资料
	 * 
	 * @param dzscEmsPorHead
	 *            电子手册通关备案里的表头资料
	 * @param isMaterielManageType
	 *            是否存在企业内部物料编号，存在为True 否为False
	 * @param map
	 *            报关常用工厂物料资料
	 * @param imgBill
	 *            电子手册通关备案里的料件资料
	 */
	private void addDzscEmsPorMaterialImgWhenImport(
			DzscEmsPorHead dzscEmsPorHead, boolean isMaterielManageType,
			Map<Integer, List<Materiel>> map, DzscEmsImgBill imgBill) {
		List<Materiel> lsMateriel = map.get(imgBill.getTenSeqNum());
		if (lsMateriel != null && lsMateriel.size() > 0) {
			for (Materiel materiel : lsMateriel) {
				DzscEmsPorMaterialImg materialImg = new DzscEmsPorMaterialImg();
				materialImg.setDzscEmsImgBill(imgBill);
				materialImg.setDzscEmsPorHead(dzscEmsPorHead);
				materialImg.setMateriel(materiel);
				materialImg.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveOrUpdateNoCache(materialImg);
			}
		} else {
			if (isMaterielManageType) {
				throw new RuntimeException("手册" + dzscEmsPorHead.getEmsNo()
						+ "是以料号为管理单元的，" + "\r\n所以要导入的归并序号为"
						+ imgBill.getTenSeqNum() + "的料件在归并关系中找不到！");
			}
		}
	}

	/**
	 * 保存导入的电子手册成品资料
	 * 
	 * @param ls
	 *            电子手册成品资料
	 * @param isOverwrite
	 *            是否覆盖导入
	 */
	public void saveDzscEmsExgFromImport(List ls, boolean isOverwrite) {
		DzscEmsPorHead dzscEmsPorHead = null;
		if (ls.size() > 0) {
			dzscEmsPorHead = ((TempDzscEmsExgBill) ls.get(0)).getExg()
					.getDzscEmsPorHead();
		} else {
			return;
		}
		boolean isMaterielManageType = isMaterielManageType(dzscEmsPorHead);
		Map<Integer, List<Materiel>> map = new HashMap<Integer, List<Materiel>>();
		List lsInnerMerge = this.dzscInnerMergeDao
				.findDzscInnerMergeDataTenSeqNumAndMateriel(
						MaterielType.FINISHED_PRODUCT, DzscState.EXECUTE);
		for (int i = 0; i < lsInnerMerge.size(); i++) {
			Object[] objs = (Object[]) lsInnerMerge.get(i);
			if (objs[0] == null || objs[1] == null) {
				continue;
			}
			Integer tenSeqNum = Integer.parseInt(objs[1].toString());
			List<Materiel> lsMateriel = map.get(tenSeqNum);
			if (lsMateriel == null) {
				lsMateriel = new ArrayList<Materiel>();
				map.put(tenSeqNum, lsMateriel);
			}
			lsMateriel.add((Materiel) objs[0]);
		}
		// List lsExgSeqNum = this.dzscDao
		// .findDzscEmsExgBillSeqNum(dzscEmsPorHead);
		List lsExgBill = this.dzscDao.findDzscEmsExgBill(dzscEmsPorHead);
		Map<Integer, DzscEmsExgBill> exgMap = new HashMap<Integer, DzscEmsExgBill>();
		for (int i = 0; i < lsExgBill.size(); i++) {
			DzscEmsExgBill exgBill = (DzscEmsExgBill) lsExgBill.get(i);
			exgMap.put(exgBill.getSeqNum(), exgBill);
		}
		for (int i = 0; i < ls.size(); i++) {
			DzscEmsExgBill exgBill = exgMap
					.get(((TempDzscEmsExgBill) ls.get(i)).getExg().getSeqNum());
			if (exgBill == null) {// 如果不存在，则直接保存
				exgBill = ((TempDzscEmsExgBill) ls.get(i)).getExg();
				exgBill.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsExgBill(exgBill);
				if (exgBill.getTenSeqNum() != null) {
					addDzscEmsPorMaterialExgWhenImport(dzscEmsPorHead,
							isMaterielManageType, map, exgBill);
				} else {
					String customsNo = exgBill.getCustomsNo();
					if (customsNo != null && !"".equals(customsNo.trim())) {
						DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
								.findInnerMergeDataByCustomsNo(customsNo);
						if (innerMergeData == null) {
							throw new RuntimeException("在归并关系中没有找到报关助记码"
									+ customsNo + "所对应的归并关系");
						}
						if (innerMergeData != null
								&& innerMergeData.getDzscTenInnerMerge() != null
								&& innerMergeData.getDzscTenInnerMerge()
										.getTenSeqNum() != null) {
							exgBill.setTenSeqNum(innerMergeData
									.getDzscTenInnerMerge().getTenSeqNum());
							addDzscEmsPorMaterialExgWhenImport(dzscEmsPorHead,
									isMaterielManageType, map, exgBill);
						} else {
							throw new RuntimeException("在归并关系中,物料"
									+ innerMergeData.getMateriel().getPtNo()
									+ "所对应的10码序号为空！ ");
						}
					}
					if (isMaterielManageType && exgBill.getTenSeqNum() == null) {
						throw new RuntimeException("手册"
								+ dzscEmsPorHead.getEmsNo() + "是以料号为管理单元的，"
								+ "\r\n所以要导入的备案序号为" + exgBill.getSeqNum()
								+ "的成品的归并序号不能为空！");
					}
				}
			} else {
				if (isOverwrite) {
					Integer oldTenSeqNum = exgBill.getTenSeqNum();
					DzscEmsExgBill exg = ((TempDzscEmsExgBill) ls.get(i))
							.getExg();
					String customsNo = exg.getCustomsNo();
					if (exg.getTenSeqNum() == null && customsNo != null
							&& !"".equals(customsNo.trim())) {
						DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
								.findInnerMergeDataByCustomsNo(customsNo);
						if (innerMergeData == null) {
							throw new RuntimeException("在归并关系中没有找到报关助记码"
									+ customsNo + "所对应的归并关系");
						}
						if (innerMergeData != null
								&& innerMergeData.getDzscTenInnerMerge() != null
								&& innerMergeData.getDzscTenInnerMerge()
										.getTenSeqNum() != null) {
							exg.setTenSeqNum(innerMergeData
									.getDzscTenInnerMerge().getTenSeqNum());
						} else {
							throw new RuntimeException("在归并关系中,物料"
									+ innerMergeData.getMateriel().getPtNo()
									+ "所对应的10码序号为空！ ");
						}
					}
					if (exg.getTenSeqNum() == null && isMaterielManageType) {
						throw new RuntimeException("手册"
								+ dzscEmsPorHead.getEmsNo() + "是以料号为管理单元的，"
								+ "\r\n所以要导入的备案序号为" + exgBill.getSeqNum()
								+ "的成品的归并序号不能为空！");
					}
					exgBill.setComplex(exg.getComplex());
					exgBill.setName(exg.getName());
					exgBill.setSpec(exg.getSpec());
					exgBill.setPrice(exg.getPrice());
					exgBill.setMachinPrice(exg.getMachinPrice());
					exgBill.setAmount(exg.getAmount());
					exgBill.setMoney(exg.getMoney());
					exgBill.setImgMoney(exg.getImgMoney());
					exgBill.setMachinMoney(exg.getMachinMoney());
					exgBill.setUnit(exg.getUnit());
					exgBill.setCountry(exg.getCountry());
					exgBill.setLevyMode(exg.getLevyMode());
					exgBill.setTenSeqNum(exg.getTenSeqNum());
					exgBill.setCustomsNo(exg.getCustomsNo());
					if (ModifyMarkState.UNCHANGE
							.equals(exgBill.getModifyMark())) {
						exgBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.dzscDao.saveDzscEmsExgBill(exgBill);
					if (oldTenSeqNum == null && exgBill.getTenSeqNum() != null) {
						addDzscEmsPorMaterialExgWhenImport(dzscEmsPorHead,
								isMaterielManageType, map, exgBill);
					} else if (oldTenSeqNum != null
							&& exgBill.getTenSeqNum() == null) {
						deleteDzscEmsPorMaterialExgWhenImport(this.dzscDao
								.findDzscEmsPorMaterialExg(exgBill));
					} else if (oldTenSeqNum != null
							&& exgBill.getTenSeqNum() != null
							&& !oldTenSeqNum.equals(exgBill.getTenSeqNum())) {
						deleteDzscEmsPorMaterialExgWhenImport(this.dzscDao
								.findDzscEmsPorMaterialExg(exgBill));
						addDzscEmsPorMaterialExgWhenImport(dzscEmsPorHead,
								isMaterielManageType, map, exgBill);
					}
				} else {
					continue;
				}
			}
		}
		this.statDzscEmsPorExgBillCount(dzscEmsPorHead);
		this.statDzscEmsPorExgBillMoney(dzscEmsPorHead);
	}

	/**
	 * 删除导入的电子手册归并成品资料
	 * 
	 * @param list
	 *            正在执行的通关备案成品归并
	 */
	private void deleteDzscEmsPorMaterialExgWhenImport(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialExg materialExg = (DzscEmsPorMaterialExg) list
					.get(i);
			if (ModifyMarkState.ADDED.equals(materialExg.getModifyMark())) {
				this.dzscDao.delete(materialExg);
			} else {
				materialExg.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveOrUpdateNoCache(materialExg);
			}
		}
	}

	/**
	 * 保存导入的电子手册归并成品资料
	 * 
	 * @param dzscEmsPorHead
	 *            电子手册通关备案里的表头资料
	 * @param isMaterielManageType
	 *            是否存在企业物料编号，存在为True，否为False
	 * @param map
	 *            报关常用工厂物料资料
	 * @param exgBill
	 *            手册通关备案的成品资料
	 */
	private void addDzscEmsPorMaterialExgWhenImport(
			DzscEmsPorHead dzscEmsPorHead, boolean isMaterielManageType,
			Map<Integer, List<Materiel>> map, DzscEmsExgBill exgBill) {
		List<Materiel> lsMateriel = map.get(exgBill.getTenSeqNum());
		if (lsMateriel != null && lsMateriel.size() > 0) {
			for (Materiel materiel : lsMateriel) {
				DzscEmsPorMaterialExg materialExg = new DzscEmsPorMaterialExg();
				materialExg.setDzscEmsExgBill(exgBill);
				materialExg.setDzscEmsPorHead(dzscEmsPorHead);
				materialExg.setMateriel(materiel);
				materialExg.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveOrUpdateNoCache(materialExg);
			}
		} else {
			if (isMaterielManageType) {
				throw new RuntimeException("手册" + dzscEmsPorHead.getEmsNo()
						+ "是以料号为管理单元的，" + "\r\n所以要导入的归并序号为"
						+ exgBill.getTenSeqNum() + "的成品在归并关系中找不到！");
			}
		}
	}

	/**
	 * 保存电子手册通关备案保存单耗文本导入
	 * 
	 * @param ls
	 *            单耗
	 * @param isOverwrite
	 *            是否覆盖导入
	 * 
	 */
	public void saveDzscEmsBomFromImport(List ls, boolean isOverwrite) {
		DzscEmsPorHead dzscEmsPorHead = null;
		if (ls.size() > 0) {
			dzscEmsPorHead = ((TempDzscEmsBomBill) ls.get(0)).getBom()
					.getDzscEmsExgBill().getDzscEmsPorHead();
		} else {
			return;
		}
		List lsBomBill = this.dzscDao
				.findDzscEmsBomBill(dzscEmsPorHead.getId());
		Map<String, DzscEmsBomBill> bomMap = new HashMap<String, DzscEmsBomBill>();
		for (int i = 0; i < lsBomBill.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) lsBomBill.get(i);
			bomMap.put(((bomBill.getDzscEmsExgBill().getSeqNum() == null ? ""
					: bomBill.getDzscEmsExgBill().getSeqNum().toString())
					+ "-" + (bomBill.getImgSeqNum() == null ? "" : bomBill
					.getImgSeqNum().toString())), bomBill);
		}
		for (int i = 0; i < ls.size(); i++) {
			DzscEmsBomBill bom = ((TempDzscEmsBomBill) ls.get(i)).getBom();
			DzscEmsBomBill bomBill = bomMap.get((bom.getDzscEmsExgBill()
					.getSeqNum() == null ? "" : bom.getDzscEmsExgBill()
					.getSeqNum().toString())
					+ "-"
					+ (bom.getImgSeqNum() == null ? "" : bom.getImgSeqNum()
							.toString()));
			DzscEmsExgBill exgBill = bom.getDzscEmsExgBill();
			DzscEmsImgBill imgBill = this.dzscDao.findEmsPorImgBillToBill(
					dzscEmsPorHead.getId(), bom.getDzscEmsExgBill().getId(),
					bom.getImgSeqNum().toString());
			if (bomBill == null) {
				bomBill = bom;
				Double exgAmount = exgBill.getAmount();
				Double unitDosage = bomBill.getUnitWare()
						/ (1 - bomBill.getWare() / 100);
				bomBill.setUnitDosage(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(unitDosage), 5));
				if (imgBill != null) {
					bomBill.setPrice(CommonUtils.getDoubleExceptNull(imgBill
							.getPrice()));
				} else {
					bomBill.setPrice(0.0);
				}
				bomBill.setAmount(CommonUtils.getDoubleExceptNull(exgAmount
						* unitDosage));
				bomBill.setMoney(CommonUtils.getDoubleExceptNull(bomBill
						.getPrice()
						* bomBill.getAmount()));
				bomBill.setNonMnlRatio(bomBill.getNonMnlRatio());
				bomBill.setModifyMark(ModifyMarkState.ADDED);
			} else {
				if (isOverwrite) {
					bomBill.setComplex(bom.getComplex());
					bomBill.setName(bom.getName());
					bomBill.setSpec(bom.getSpec());
					bomBill.setUnit(bom.getUnit());
					bomBill.setCountry(bom.getCountry());
					bomBill.setImgSeqNum(bom.getImgSeqNum());
					bomBill.setUnitWare(bom.getUnitWare());
					bomBill.setWare(bom.getWare());
					bomBill.setNonMnlRatio(bom.getNonMnlRatio());
					// bomBill.setUnitDosage(bom.getUnitDosage());
					// bomBill.setPrice(imgBill.getPrice() == null ? 0.0 :
					// imgBill
					// .getPrice());
					Double exgAmount = exgBill.getAmount();
					Double unitDosage = bomBill.getUnitWare()
							/ (1 - bomBill.getWare() / 100);
					bomBill.setUnitDosage(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(unitDosage), 5));
					bomBill.setAmount(CommonUtils.getDoubleExceptNull(exgAmount
							* unitDosage));
					if (imgBill != null) {
						bomBill.setPrice(CommonUtils
								.getDoubleExceptNull(imgBill.getPrice()));
					} else {
						bomBill.setPrice(0.0);
					}
					bomBill.setMoney(CommonUtils.getDoubleExceptNull(bomBill
							.getAmount()
							* bomBill.getPrice()));
					if (ModifyMarkState.UNCHANGE
							.equals(bomBill.getModifyMark())) {
						bomBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
				} else {
					continue;
				}
			}
			this.dzscDao.saveDzscEmsBomBill(bomBill);
			saveDzscEmsBomBill(bomBill);
		}
	}

	/**
	 * 判断是否存在企业内部物料编号
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 * @return boolean 是否存在企业内部物料编号 存在返回TRUE 否则返回FALSE
	 */
	private boolean isMaterielManageType(DzscEmsPorHead head) {
		if (head.getCopEntNo() == null || "".equals(head.getCopEntNo().trim())) {
			return false;
		} else {
			return true;
		}
		// Integer dzscManageType = null;
		// DzscEmsPorWjHead wjHead = this.dzscDao
		// .findExingDzscEmsPorWjHeadByEmsNo(head.getCorrEmsNo());
		// if (wjHead != null) {
		// dzscManageType = wjHead.getDzscManageType();
		// }
		// if (dzscManageType == null) {
		// DzscParameterSet parameter = this.dzscDao.findDzscParameterSet();
		// if (parameter != null) {
		// dzscManageType = parameter.getDzscManageType();
		// }
		// }
		// if (dzscManageType == null || dzscManageType ==
		// DzscManageType.MATERIAL) {
		// return true;
		// } else {
		// return false;
		// }
	}

	/**
	 * 更新电子手册手册编号
	 * 
	 * @param oldEmsNo
	 *            久的emsno
	 * @param newEmsNo
	 *            新的emsno
	 * @param head
	 *            电子手册合同备案表头资料
	 * @return Boolean 判断是合同备案还是通关备案,当为"DzscEmsPorWjHead"是手册合同备案
	 *         ,当为"DzscEmsPorHead"是手册通关备案
	 */
	public Boolean editDzscEmsNo(String oldEmsNo, String newEmsNo, String head) {
		List list;

		boolean hadChange = false;

		if (head.equals("DzscEmsPorWjHead")) {
			/**
			 * 更新电子手册的手册合同备案
			 */
			list = this.dzscDao.findDzscEmsPorWjHeadByEmsNo(oldEmsNo);
			if (!list.isEmpty()) {
				DzscEmsPorWjHead dzscEmsPorWjHead;
				for (int i = 0; i < list.size(); i++) {
					dzscEmsPorWjHead = (DzscEmsPorWjHead) list.get(i);
					dzscEmsPorWjHead.setCorrEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscEmsPorWjHead);
				}
				hadChange = true;
			}
			System.out.println("更新电子手册的手册合同备案-完成");

			/**
			 * 更新电子手册的手册通关备案
			 */
			list = this.dzscDao.findDzscEmsPorHeadByCorrEmsNo(oldEmsNo);
			if (!list.isEmpty()) {
				DzscEmsPorHead dzscEmsPorHead;
				for (int i = 0; i < list.size(); i++) {
					dzscEmsPorHead = (DzscEmsPorHead) list.get(i);
					dzscEmsPorHead.setCopTrNo(newEmsNo);
					dzscEmsPorHead.setCorrEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscEmsPorHead);
				}
			}
			System.out.println("更新电子手册的手册通关备案-完成");

			return hadChange;

		} else if (head.equals("DzscEmsPorHead")) {

			/**
			 * 更新电子手册的手册通关备案
			 */
			list = this.dzscDao.findDzscEmsPorHeadByEmsNo(oldEmsNo);
			if (!list.isEmpty()) {
				DzscEmsPorHead dzscEmsPorHead;
				for (int i = 0; i < list.size(); i++) {
					dzscEmsPorHead = (DzscEmsPorHead) list.get(i);
					if (dzscEmsPorHead.getCopTrNo().equals(oldEmsNo))
						dzscEmsPorHead.setCopTrNo(newEmsNo);
					dzscEmsPorHead.setEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscEmsPorHead);
				}
				hadChange = true;
			}
			System.out.println("更新电子手册的手册通关备案-完成");

			/**
			 * 更新电子手册的手册分册通关备案
			 */
			list = this.dzscDao.findEntityObjectByEmsNo("DzscEmsPorHeadFas",
					oldEmsNo, null);
			if (!list.isEmpty()) {
				DzscEmsPorHeadFas dzscEmsPorHeadFas;
				for (int i = 0; i < list.size(); i++) {
					dzscEmsPorHeadFas = (DzscEmsPorHeadFas) list.get(i);
					dzscEmsPorHeadFas.setEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscEmsPorHeadFas);
				}
			}
			System.out.println("更新电子手册的手册分册通关备案-完成");

			/**
			 * 更新手册报关清单
			 */
			list = this.dzscDao.findEntityObjectByEmsHeadH2kNo(
					"DzscCustomsBillList", oldEmsNo, null);
			if (!list.isEmpty()) {
				DzscCustomsBillList dzscCustomsBillList;
				for (int i = 0; i < list.size(); i++) {
					dzscCustomsBillList = (DzscCustomsBillList) list.get(i);
					dzscCustomsBillList.setEmsHeadH2k(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscCustomsBillList);
				}
			}
			System.out.println("更新手册报关清单-完成");

			/**
			 * 更新电子手册报关单
			 */
			list = this.dzscDao.findEntityObjectByEmsHeadH2kNo(
					"DzscCustomsDeclaration", oldEmsNo, null);
			if (!list.isEmpty()) {
				DzscCustomsDeclaration dzscCustomsDeclaration;
				for (int i = 0; i < list.size(); i++) {
					dzscCustomsDeclaration = (DzscCustomsDeclaration) list
							.get(i);
					dzscCustomsDeclaration.setEmsHeadH2k(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscCustomsDeclaration);
				}
			}
			System.out.println("更新电子手册报关单-完成");

			/**
			 * 更新电子手册的数据报核
			 */
			list = this.dzscDao.findEntityObjectByEmsNo("DzscContractCav",
					oldEmsNo, null);
			if (!list.isEmpty()) {
				DzscContractCav dzscContractCav;
				for (int i = 0; i < list.size(); i++) {
					dzscContractCav = (DzscContractCav) list.get(i);
					dzscContractCav.setEmsNo(newEmsNo);
					dzscContractCav.setCopEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscContractCav);
				}
			}
			System.out.println("更新电子手册的数据报核-完成");

			/**
			 * 更新电子手册的中期核查
			 */
			list = this.dzscDao.findEntityObjectByEmsNo("DzscCheckHead",
					oldEmsNo, null);
			if (!list.isEmpty()) {
				DzscCheckHead dzscCheckHead;
				for (int i = 0; i < list.size(); i++) {
					dzscCheckHead = (DzscCheckHead) list.get(i);
					dzscCheckHead.setEmsNo(newEmsNo);
					dzscCheckHead.setCopEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(dzscCheckHead);
				}
			}
			System.out.println("更新电子手册的中期核查-完成");

			/**
			 * 更新单据中心的商品大类
			 */
			System.out.println("更新单据中心的商品大类-完成");

			// /**
			// * 更新关封表体
			// */
			// list = this.dzscDao.findEntityObjectByEmsNo(
			// "CustomsEnvelopCommodityInfo", oldEmsNo, null);
			// if (!list.isEmpty()) {
			// CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo;
			// for (int i = 0; i < list.size(); i++) {
			// customsEnvelopCommodityInfo = (CustomsEnvelopCommodityInfo) list
			// .get(i);
			// customsEnvelopCommodityInfo.setEmsNo(newEmsNo);
			// this.dzscDao.saveOrUpdate(customsEnvelopCommodityInfo);
			// }
			// }
			// System.out.println("更新关封表体-完成");

			// /**
			// * 更新关封表头
			// */
			// list = this.dzscDao.findCustomsEnvelopBillByEmsNo(oldEmsNo);
			// if (!list.isEmpty()) {
			// CustomsEnvelopBill customsEnvelopBill;
			// for (int i = 0; i < list.size(); i++) {
			// customsEnvelopBill = (CustomsEnvelopBill) list.get(i);
			// customsEnvelopBill.setEmsNo(customsEnvelopBill.getEmsNo()
			// .replaceAll(oldEmsNo, newEmsNo));
			// this.dzscDao.saveOrUpdate(customsEnvelopBill);
			// }
			// }
			// System.out.println("更新关封表头-完成");

			/**
			 * 更新转厂期初单表体
			 */
			list = this.dzscDao.findEntityObjectByEmsNo(
					"TransferFactoryInitBillCommodityInfo", oldEmsNo, null);
			if (!list.isEmpty()) {
				FptInitBillItem transferFactoryInitBillCommodityInfo;
				for (int i = 0; i < list.size(); i++) {
					transferFactoryInitBillCommodityInfo = (FptInitBillItem) list
							.get(i);
					transferFactoryInitBillCommodityInfo.setEmsNo(newEmsNo);
					this.dzscDao
							.saveOrUpdate(transferFactoryInitBillCommodityInfo);
				}
			}
			System.out.println("更新转厂期初单表体-完成");

			/**
			 * 更新转厂单据表体
			 */
			list = this.dzscDao.findEntityObjectByEmsNo(
					"TransferFactoryCommodityInfo", oldEmsNo, null);
			if (!list.isEmpty()) {
				FptBillItem transferFactoryCommodityInfo;
				for (int i = 0; i < list.size(); i++) {
					transferFactoryCommodityInfo = (FptBillItem) list.get(i);
					transferFactoryCommodityInfo.setEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(transferFactoryCommodityInfo);
				}
			}
			System.out.println("更新转厂单据表体-完成");

			/**
			 * 更新外汇核销管理－核销单使用
			 */
			list = this.dzscDao.findEntityObjectByEmsNo("FecavBill", oldEmsNo,
					null);
			if (!list.isEmpty()) {
				FecavBill fecavBill;
				for (int i = 0; i < list.size(); i++) {
					fecavBill = (FecavBill) list.get(i);
					fecavBill.setEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(fecavBill);
				}
			}
			System.out.println("更新外汇核销管理－核销单使用-完成");

			/**
			 * 更新外汇核销管理－进口白单管制
			 */
			list = this.dzscDao.findEntityObjectByEmsNo(
					"ImpCustomsDeclaration", oldEmsNo, null);
			if (!list.isEmpty()) {
				ImpCustomsDeclaration impCustomsDeclaration;
				for (int i = 0; i < list.size(); i++) {
					impCustomsDeclaration = (ImpCustomsDeclaration) list.get(i);
					impCustomsDeclaration.setEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(impCustomsDeclaration);
				}
			}
			System.out.println("更新外汇核销管理－进口白单管制-完成");

			/**
			 * 更新外汇核销管理－出口收汇管制
			 */
			list = this.dzscDao.findEntityObjectByEmsNo("FecavBillStrike",
					oldEmsNo, ProjectType.DZSC);
			if (!list.isEmpty()) {
				FecavBillStrike fecavBillStrike;
				for (int i = 0; i < list.size(); i++) {
					fecavBillStrike = (FecavBillStrike) list.get(i);
					fecavBillStrike.setEmsNo(newEmsNo);
					this.dzscDao.saveOrUpdate(fecavBillStrike);
				}
			}
			System.out.println("更新外汇核销管理－出口收汇管制-完成");

			return hadChange;
		}

		return hadChange;

	}

	/**
	 * 根据通关备案成品查找报关单耗成品
	 * 
	 * @param exgBill
	 *            手册通关备案的成品资料
	 * @return List是DzscCustomsBomExg型 报关单耗表头成品资料
	 */
	public List findDzscCustomsBomExg(DzscEmsExgBill exgBill) {
		List list = new ArrayList();
		if (exgBill.getTenSeqNum() != null) {
			list = this.dzscInnerMergeDao
					.findDzscCustomsBomExgByTenSeqNum(exgBill.getTenSeqNum());
		}
		// if (list.size() <= 0) {
		// list = this.dzscInnerMergeDao
		// .findDzscCustomsBomExgByComplex(exgBill.getComplex());
		// }
		return list;
	}

	/**
	 * 保存通关备案单耗来自报关单耗
	 * 
	 * @param exgBill
	 *            手册通关备案的成品资料
	 * @param list
	 *            手册通关备案的BOM资料
	 */
	public void saveDzscEmsBomBillByCustomsBom(DzscEmsExgBill exgBill, List list) {
		String declareState = exgBill.getDzscEmsPorHead().getDeclareState();
		if (DzscState.ORIGINAL.equals(declareState)) {
			this.dzscDao.deleteAll(this.dzscDao.findEmsPorBomBill(exgBill));
			for (int i = 0; i < list.size(); i++) {
				DzscCustomsBomDetail bomDetail = (DzscCustomsBomDetail) list
						.get(i);
				List lsImgBill = this.dzscDao.findDzscEmsImgBillByTenSeqNum(
						exgBill.getDzscEmsPorHead().getId(), bomDetail
								.getTenSeqNum());
				DzscEmsImgBill imgBill = null;
				if (lsImgBill.size() > 0) {
					imgBill = (DzscEmsImgBill) lsImgBill.get(0);
				}
				if (imgBill == null) {
					imgBill = new DzscEmsImgBill();
					imgBill.setDzscEmsPorHead(exgBill.getDzscEmsPorHead());
					imgBill
							.setSeqNum(this.dzscDao
									.getMaxDzscEmsPorImgNum(exgBill
											.getDzscEmsPorHead()));
					imgBill.setTenSeqNum(bomDetail.getTenSeqNum());
					imgBill.setComplex(bomDetail.getComplex());
					imgBill.setName(bomDetail.getName());
					imgBill.setSpec(bomDetail.getSpec());
					imgBill.setUnit(bomDetail.getUnit());
					imgBill.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveDzscEmsImgBill(imgBill);
				}
				DzscEmsBomBill bomBill = new DzscEmsBomBill();
				bomBill.setDzscEmsExgBill(exgBill);
				bomBill.setImgSeqNum(imgBill.getSeqNum());
				bomBill.setComplex(bomDetail.getComplex());
				bomBill.setName(bomDetail.getName());
				bomBill.setSpec(bomDetail.getSpec());
				bomBill.setUnit(bomDetail.getUnit());
				bomBill.setPrice(imgBill.getPrice());
				bomBill.setUnitWare(bomDetail.getUnitWare());
				bomBill.setWare(bomDetail.getWare());
				bomBill.setUnitDosage(bomDetail.getUnitDosage());
				bomBill.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
						.getDoubleExceptNull(exgBill.getAmount())
						* CommonUtils.getDoubleExceptNull(bomBill
								.getUnitDosage()), 6));
				bomBill.setMoney(CommonUtils.getDoubleExceptNull(bomBill
						.getPrice())
						* CommonUtils.getDoubleExceptNull(bomBill.getAmount()));
				bomBill.setModifyMark(ModifyMarkState.ADDED);
				this.dzscDao.saveDzscEmsBomBill(bomBill);
				this.writeBackDzscEmsExgBill(bomBill);
				this.writeBackDzscEmsImgBill(bomBill);
			}
		} else if (DzscState.CHANGE.equals(declareState)) {
			Map<Integer, DzscEmsBomBill> hmOldBomBill = new HashMap<Integer, DzscEmsBomBill>();
			List lsOldBomBill = this.dzscDao.findEmsPorBomBill(exgBill);
			for (int i = 0; i < lsOldBomBill.size(); i++) {
				DzscEmsBomBill oldBomBill = (DzscEmsBomBill) lsOldBomBill
						.get(i);
				hmOldBomBill.put(oldBomBill.getImgSeqNum(), oldBomBill);
			}
			for (int i = 0; i < list.size(); i++) {
				DzscCustomsBomDetail bomDetail = (DzscCustomsBomDetail) list
						.get(i);
				List lsImgBill = this.dzscDao.findDzscEmsImgBillByTenSeqNum(
						exgBill.getDzscEmsPorHead().getId(), bomDetail
								.getTenSeqNum());
				DzscEmsImgBill imgBill = null;
				if (lsImgBill.size() > 0) {
					imgBill = (DzscEmsImgBill) lsImgBill.get(0);
				}
				if (imgBill == null) {
					imgBill = new DzscEmsImgBill();
					imgBill.setDzscEmsPorHead(exgBill.getDzscEmsPorHead());
					imgBill
							.setSeqNum(this.dzscDao
									.getMaxDzscEmsPorImgNum(exgBill
											.getDzscEmsPorHead()));
					imgBill.setTenSeqNum(bomDetail.getTenSeqNum());
					imgBill.setComplex(bomDetail.getComplex());
					imgBill.setName(bomDetail.getName());
					imgBill.setSpec(bomDetail.getSpec());
					imgBill.setUnit(bomDetail.getUnit());
					imgBill.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveDzscEmsImgBill(imgBill);
				}
				DzscEmsBomBill oldBomBill = hmOldBomBill.get(imgBill
						.getSeqNum());
				if (oldBomBill != null) {
					if ((oldBomBill.getUnitDosage() != bomDetail
							.getUnitDosage())
							&& (oldBomBill.getUnitWare() != bomDetail
									.getUnitWare())
							&& (oldBomBill.getWare() != bomDetail.getWare())) {
						oldBomBill.setUnitWare(bomDetail.getUnitWare());
						oldBomBill.setWare(bomDetail.getWare());
						oldBomBill.setUnitDosage(bomDetail.getUnitDosage());
						oldBomBill.setAmount(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(exgBill
										.getAmount())
										* CommonUtils
												.getDoubleExceptNull(bomDetail
														.getUnitDosage()), 6));
						oldBomBill.setMoney(CommonUtils
								.getDoubleExceptNull(imgBill.getPrice())
								* CommonUtils.getDoubleExceptNull(oldBomBill
										.getAmount()));
						if (!ModifyMarkState.ADDED.equals(oldBomBill
								.getModifyMark())) {
							oldBomBill.setModifyMark(ModifyMarkState.MODIFIED);
						}
						this.dzscDao.saveDzscEmsBomBill(oldBomBill);
						this.writeBackDzscEmsExgBill(oldBomBill);
						this.writeBackDzscEmsImgBill(oldBomBill);
					}
				} else {
					DzscEmsBomBill bomBill = new DzscEmsBomBill();
					bomBill.setDzscEmsExgBill(exgBill);
					bomBill.setImgSeqNum(imgBill.getSeqNum());
					bomBill.setComplex(bomDetail.getComplex());
					bomBill.setName(bomDetail.getName());
					bomBill.setSpec(bomDetail.getSpec());
					bomBill.setUnit(bomDetail.getUnit());
					bomBill.setPrice(imgBill.getPrice());
					bomBill.setUnitWare(bomDetail.getUnitWare());
					bomBill.setWare(bomDetail.getWare());
					bomBill.setUnitDosage(bomDetail.getUnitDosage());
					bomBill.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(exgBill.getAmount())
							* CommonUtils.getDoubleExceptNull(bomBill
									.getUnitDosage()), 6));
					bomBill.setMoney(CommonUtils.getDoubleExceptNull(bomBill
							.getPrice())
							* CommonUtils.getDoubleExceptNull(bomBill
									.getAmount()));
					bomBill.setModifyMark(ModifyMarkState.ADDED);
					this.dzscDao.saveDzscEmsBomBill(bomBill);
					this.writeBackDzscEmsExgBill(bomBill);
					this.writeBackDzscEmsImgBill(bomBill);
				}
			}
		}
	}

	/**
	 * 统计出口总金额
	 * 
	 * @param contract
	 *            电子手册通关备案里的表头资料
	 */
	public void getTotalPriceBExport(DzscEmsPorHead dzscEmsPorHead) {

		Double exgAmount = this.dzscDao.getTotalPriceBExport(dzscEmsPorHead);
		dzscEmsPorHead.setExgAmount(exgAmount);
		this.dzscDao.saveOrUpdate(dzscEmsPorHead);

	}

	/**
	 * 统计进口总金额
	 * 
	 * @param contract
	 *            电子手册通关备案里的表头资料
	 */
	public void getTotalPriceBImport(DzscEmsPorHead dzscEmsPorHead) {
		Double imgAmount = this.dzscDao.getTotalPriceBImport(dzscEmsPorHead);
		dzscEmsPorHead.setImgAmount(imgAmount);
		this.dzscDao.saveOrUpdate(dzscEmsPorHead);
	}

	/**
	 * 通关备案归并料件变更
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param emsHeadChange
	 *            变更后的通关备案表头
	 * 
	 */
	private void changeEmsPorMaterialImg(DzscEmsPorHead emsHead,
			DzscEmsPorHead emsHeadChange) { // 料件清单变更
		List list = dzscDao.findImgMaterialAndSeqNum(emsHead);
		List emsInnerChanges = new Vector();
		List lsImgBill = this.dzscDao.findDzscEmsImgBillByHead(emsHeadChange);
		Map imgBillMap = new HashMap();
		for (int i = 0; i < lsImgBill.size(); i++) {
			DzscEmsImgBill dzscEmsImgBill = (DzscEmsImgBill) lsImgBill.get(i);
			if (imgBillMap.get(dzscEmsImgBill.getSeqNum()) == null)
				imgBillMap.put(dzscEmsImgBill.getSeqNum(), dzscEmsImgBill);
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null)
				continue;
			Integer seqNum = (Integer) ((Object[]) list.get(i))[0];
			Materiel materiel = (Materiel) ((Object[]) list.get(i))[1];
			DzscEmsPorMaterialImg detailChanged = new DzscEmsPorMaterialImg(); // 变更
			detailChanged.setCompany(emsHeadChange.getCompany());
			detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			detailChanged.setDzscEmsPorHead(emsHeadChange);
			detailChanged.setMateriel(materiel);

			DzscEmsImgBill imgBill = (DzscEmsImgBill) imgBillMap.get(seqNum);
			detailChanged.setDzscEmsImgBill(imgBill);
			emsInnerChanges.add(detailChanged);
		}
		dzscDao.batchSaveOrUpdate(emsInnerChanges);
	}

	/**
	 * 改变通关手册表头的申报状态
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 * @param declareState
	 *            申报状态
	 * @return DzscEmsPorHead 是DzscEmsPorHead型，电子手册通关备案里的表头资料
	 */
	public DzscEmsPorHead changeDzscEmsPorHeadDeclareState(DzscEmsPorHead head,
			String declareState) {
		head = (DzscEmsPorHead) this.dzscDao
				.load(head.getClass(), head.getId());
		if (head == null || !DzscState.APPLY.equals(head.getDeclareState())) {
			return head;
		}
		head.setDeclareState(declareState);
		this.dzscDao.saveOrUpdate(head);
		return head;
	}

	/**
	 * 通关备案归成品件变更或复制
	 * 
	 * @param emsHead
	 *            通关备案表头
	 * @param emsHeadChange
	 *            变更后的通关备案表头
	 */
	private void changeEmsPorMaterialExg(DzscEmsPorHead emsHead,
			DzscEmsPorHead emsHeadChange) { // 料件清单变更
		List list = dzscDao.findExgMaterialAndSeqNum(emsHead);
		List emsInnerChanges = new Vector();
		List lsExgBill = this.dzscDao.findDzscEmsExgBillByHead(emsHeadChange);
		Map exgBillMap = new HashMap();
		for (int i = 0; i < lsExgBill.size(); i++) {
			DzscEmsExgBill dzscEmsExgBill = (DzscEmsExgBill) lsExgBill.get(i);
			if (exgBillMap.get(dzscEmsExgBill.getSeqNum()) == null)
				exgBillMap.put(dzscEmsExgBill.getSeqNum(), dzscEmsExgBill);
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null)
				continue;
			Integer seqNum = (Integer) ((Object[]) list.get(i))[0];
			Materiel materiel = (Materiel) ((Object[]) list.get(i))[1];
			DzscEmsPorMaterialExg detailChanged = new DzscEmsPorMaterialExg(); // 变更
			detailChanged.setCompany(emsHeadChange.getCompany());
			detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			detailChanged.setDzscEmsPorHead(emsHeadChange);
			detailChanged.setMateriel(materiel);

			DzscEmsExgBill exgBill = (DzscEmsExgBill) exgBillMap.get(seqNum);
			detailChanged.setDzscEmsExgBill(exgBill);
			emsInnerChanges.add(detailChanged);
		}
		dzscDao.batchSaveOrUpdate(emsInnerChanges);

	}

	/**
	 * 改变备案资料库表头的申报状态
	 * 
	 * @param head
	 *            合同备案表头
	 * @param declareState
	 *            申报状态
	 */
	public DzscEmsPorWjHead changeDictPorHeadDeclareState(
			DzscEmsPorWjHead head, String declareState) {
		head = (DzscEmsPorWjHead) this.dzscDao.load(head.getClass(), head
				.getId());
		if (head == null || !DzscState.APPLY.equals(head.getDeclareState())) {
			return head;
		}
		head.setDeclareState(declareState);
		this.dzscDao.saveOrUpdate(head);
		return head;
	}

	/**
	 * 改变备案资料库料件成品的修改标志
	 * 
	 * @param head
	 *            料件清单备案
	 * @param modifyMark
	 *            修改标志
	 */
	public void changeDictPorImgExgModifyMark(List list, String modifyMark) {
		if (list.size() <= 0) {
			return;
		}
		Method methodSet = null;
		Method methodGet = null;
		try {
			methodSet = list.get(0).getClass().getMethod("setModifyMark",
					new Class[] { String.class });
			methodGet = list.get(0).getClass().getMethod("getModifyMark");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (methodSet == null || methodGet == null) {
			System.out.println(methodSet == null ? "methodSet is null"
					: "methodSet is not null ");
			System.out.println(methodGet == null ? "methodGet is null"
					: "methodGet is not null ");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			try {
				String oldModifyMark = (String) methodGet.invoke(obj);
				// if (ModifyMarkState.ADDED.equals(oldModifyMark)) {
				// continue;
				// }
				methodSet.invoke(obj, modifyMark);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			this.dzscDao.saveOrUpdate(obj);
		}
	}

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 *            料件清单备案
	 * @param modifyMark
	 *            修改标志
	 */
	public void changeContractImgModifyMark(List list, String modifyMark) {
		DzscEmsPorHead head = null;
		if (list.size() > 0) {
			head = ((DzscEmsImgBill) (list.get(0))).getDzscEmsPorHead();
		}
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsImgBill imgBill = (DzscEmsImgBill) list.get(i);
				if (imgBill.getModifyMark().equals(modifyMark)) {
					continue;
				}

				int count = this.dzscDao
						.findDzscCustomsDeclarationCommInfoCount(imgBill
								.getDzscEmsPorHead().getEmsNo(), imgBill
								.getSeqNum(), true);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此料件"
							+ imgBill.getSeqNum() + ":"
							+ imgBill.getComplex().getCode() + ",所以不能删除");
				}
				imgBill.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveDzscEmsImgBill(imgBill);
				this.deleteDzscEmsPorMaterialImg(this.dzscDao
						.findDzscEmsPorMaterialImg(imgBill));
				this.deleteDzscEmsBomBill(this.dzscDao.findDzscEmsBomBillByImg(
						head, imgBill));
			}
		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsImgBill imgBill = (DzscEmsImgBill) list.get(i);
				if (ModifyMarkState.DELETED.equals(imgBill.getModifyMark())) {
					imgBill.setModifyMark(modifyMark);
					this.dzscDao.saveDzscEmsImgBill(imgBill);
					/**
					 * 查找合同BOM 来自 合同料件序号
					 */
					// List contractBomList = this.contractDao
					// .findContractBomByImgSeqNum(contractImg);
					// this.rollbackContractExg(contractBomList, modifyMark);
				} else {
					imgBill.setModifyMark(modifyMark);
					this.dzscDao.saveDzscEmsImgBill(imgBill);
					List ls = this.dzscDao.findDzscEmsPorMaterialImg(imgBill);
					for (int j = 0; j < ls.size(); j++) {
						DzscEmsPorMaterialImg materialImg = (DzscEmsPorMaterialImg) ls
								.get(j);
						materialImg.setModifyMark(modifyMark);
						this.dzscDao.saveOrUpdate(materialImg);
					}
				}
			}
		}
		if (list.size() > 0) {
			this.statDzscEmsPorImgBillCount(head);
			this.statDzscEmsPorImgBillMoney(head);
		}
	}

	/**
	 * 修改通关手册成品的修改标志
	 * 
	 * @param list
	 *            成品清单备案资料
	 * @param modifyMark
	 *            修改标志
	 */
	public void changeContractExgModifyMark(List list, String modifyMark) {
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsExgBill exgBill = (DzscEmsExgBill) list.get(i);
				if (exgBill.getModifyMark().equals(modifyMark)) {
					continue;
				}
				int count = this.dzscDao
						.findDzscCustomsDeclarationCommInfoCount(exgBill
								.getDzscEmsPorHead().getEmsNo(), exgBill
								.getSeqNum(), false);
				if (count > 0) {
					throw new RuntimeException("已经有报关单用到此成品"
							+ exgBill.getSeqNum() + ":"
							+ exgBill.getComplex().getCode() + ",所以不能删除");
				}
				exgBill.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveDzscEmsExgBill(exgBill);
				this.deleteDzscEmsPorMaterialExg(this.dzscDao
						.findDzscEmsPorMaterialExg(exgBill));
				this.deleteDzscEmsBomBill(this.dzscDao
						.findDzscEmsBomBill(exgBill));

			}
		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsExgBill exgBill = (DzscEmsExgBill) list.get(i);
				if (ModifyMarkState.DELETED.equals(exgBill.getModifyMark())) {
					exgBill.setModifyMark(modifyMark);
					this.dzscDao.saveDzscEmsExgBill(exgBill);
					List contractBomList = this.dzscDao
							.findDzscEmsBomBill(exgBill.getId());
					// this.rollbackContractBom(contractBomList, modifyMark);
				} else {
					exgBill.setModifyMark(modifyMark);
					this.dzscDao.saveDzscEmsExgBill(exgBill);
					List ls = this.dzscDao.findDzscEmsPorMaterialExg(exgBill);
					for (int j = 0; j < ls.size(); j++) {
						DzscEmsPorMaterialExg materialExg = (DzscEmsPorMaterialExg) ls
								.get(j);
						materialExg.setModifyMark(modifyMark);
						this.dzscDao.saveOrUpdate(materialExg);
					}
				}
			}
		}
		if (list.size() > 0) {
			DzscEmsPorHead contract = ((DzscEmsExgBill) list.get(0))
					.getDzscEmsPorHead();
			this.statDzscEmsPorExgBillCount(contract);
			this.statDzscEmsPorExgBillMoney(contract);
		}
	}

	/**
	 * 修改通关手册单耗的修改标志
	 * 
	 * @param list
	 *            单耗清单备案资料
	 * @param modifyMark
	 *            修改标志
	 */
	public void changeContractBomModifyMark(List list, String modifyMark) {
		if (list.size() <= 0) {
			return;
		}
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			// DzscEmsBomBill temp = ((DzscEmsBomBill) list.get(0));
			// DzscEmsExgBill dzscEmsExgBill = temp.getDzscEmsExgBill();
			for (int i = 0; i < list.size(); i++) {
				DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
				if (bom.getModifyMark().equals(modifyMark)) {
					continue;
				}
				bom.setModifyMark(ModifyMarkState.DELETED);
				this.dzscDao.saveDzscEmsBomBill(bom);
				this.writeBackDzscEmsExgBill(bom);
				this.writeBackDzscEmsImgBill(bom);
			}
		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsBomBill BomBill = (DzscEmsBomBill) list.get(i);
				if (ModifyMarkState.DELETED.equals(BomBill.getModifyMark())) {
					BomBill.setModifyMark(modifyMark);
					this.dzscDao.saveDzscEmsBomBill(BomBill);
					this.writeBackDzscEmsImgBill(BomBill);
				} else {
					BomBill.setModifyMark(modifyMark);
					this.dzscDao.saveDzscEmsBomBill(BomBill);
				}
			}
		}
	}

	/**
	 * 保存通关备案料件资料
	 * 
	 * @param list
	 *            通关备案料件资料
	 */
	public void saveEmsPorImg(List<DzscEmsImgBill> list) {
		if (list.size() <= 0) {
			return;
		}
		Map<Integer, List<DzscEmsBomBill>> mapBom = this
				.findEmsPorBomGroupByImgSeqNum(list.get(0).getDzscEmsPorHead());
		for (DzscEmsImgBill img : list) {
			this.updateDzscEmsBomBillSeqNum(img, mapBom);
			this.dzscDao.saveDzscEmsImgBill(img);
			// this.contractDao.updateContractBomImgSeqNum(img);
		}
		// if (list.size() > 0) {
		// DzscEmsPorHead dzscEmsPorHead = ((DzscEmsImgBill)
		// list.get(0)).getDzscEmsPorHead();
		// this.statDzscEmsImgBillCount(dzscEmsPorHead);
		// this.statContractImgMoney(contract);
		// }
	}

	/**
	 * 统计合同备案的料件项数
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	private void statDzscEmsImgBillCount(DzscEmsPorHead head) {
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setMaterielItemCount(this.dzscDao.findDzscEmsImgBillCount(head
				.getId()));
		// System.out.println("---------"+head.getMaterielItemCount());
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveDzscEmsPorHead(head);
	}

	/**
	 * 查找通关备案BOM 来自 通关备案成品ID
	 * 
	 * @param dzscEmsPorHead
	 *            手册通关备案的BOM资料
	 * @return Map 是DzscEmsBomBill型 通关备案BOM
	 */
	private Map<Integer, List<DzscEmsBomBill>> findEmsPorBomGroupByImgSeqNum(
			DzscEmsPorHead dzscEmsPorHead) {
		Map<Integer, List<DzscEmsBomBill>> map = new HashMap<Integer, List<DzscEmsBomBill>>();
		List list = this.dzscDao.findDzscEmsBomBillByHeadId(dzscEmsPorHead
				.getId());
		for (int i = 0; i < list.size(); i++) {
			DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
			List<DzscEmsBomBill> lsBom = map.get(bom.getImgSeqNum());
			if (lsBom == null) {
				lsBom = new ArrayList<DzscEmsBomBill>();
				map.put(bom.getImgSeqNum(), lsBom);
			}
			lsBom.add(bom);
		}
		return map;
	}

	/**
	 * 根据料件的备案序号更新单耗中的料件序号
	 * 
	 * @param img
	 *            电子手册通关备案里的料件资料
	 * @param mapBom
	 *            手册通关备案的BOM资料
	 */
	private void updateDzscEmsBomBillSeqNum(DzscEmsImgBill img,
			Map<Integer, List<DzscEmsBomBill>> mapBom) {
		DzscEmsImgBill oldImg = (DzscEmsImgBill) this.dzscDao.load(
				DzscEmsImgBill.class, img.getId());
		if (oldImg == null) {
			throw new RuntimeException("备案序号是:" + img.getSeqNum() + "的料件不存在");
		}
		if (!oldImg.getSeqNum().equals(img.getSeqNum())) {
			List list = mapBom.get(oldImg.getSeqNum());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					DzscEmsBomBill dzscEmsBomBill = (DzscEmsBomBill) list
							.get(i);
					dzscEmsBomBill.setImgSeqNum(img.getSeqNum());
					this.dzscDao.saveDzscEmsBomBill(dzscEmsBomBill);
				}
			}
		}
	}

	/**
	 * 计算成品单价 公式 sum(料件单项用量*料件单价*(1+成品加工费比例)) 公式化简 sum(料件单项用量*料件单价)*(1+成品加工费比例)
	 * 
	 * @param exgBills
	 *            手册通关备案的成品资料
	 * @return List 是 DzscEmsExgBill型 计算成品单价
	 */
	public List processDzscEmsExgBillPrice(List<DzscEmsExgBill> exgBills) {
		StringBuffer bf = new StringBuffer("运行结果：\n");
		// 加工费比例
		Double machinScale = null;
		// 归并序号
		Integer tenSeqNum = null;
		// 加工费
		Double machinPrice = 0d;
		for (DzscEmsExgBill exg : exgBills) {
			bf.append("备案序号：" + exg.getSeqNum());
			if (!ModifyMarkState.ADDED.equals(exg.getModifyMark())) {
				bf.append("\t不是新增状态，不进行计算...\n");
				continue;
			}
			tenSeqNum = exg.getTenSeqNum();
			if (tenSeqNum == null || "".equals(tenSeqNum)) {// 不存在归并序号，不进行统计
				bf.append("\t没有进行归并，不进行计算...\n");
				continue;
			}
			machinScale = this.dzscDao.findMachinScaleByTemSeqNum(tenSeqNum);
			machinScale = CommonUtils.getDoubleExceptNull(machinScale);
			List<DzscEmsBomBill> bomBills = this.dzscDao.findEmsPorBomBill(exg);
			for (DzscEmsBomBill bomBill : bomBills) {
				machinPrice += CommonUtils.getDoubleExceptNull(bomBill
						.getUnitDosage())
						* CommonUtils.getDoubleExceptNull(bomBill.getPrice());
			}
			machinPrice = CommonUtils.getDoubleByDigit(machinPrice
					* (1 + machinScale / 100), 5);
			// 更新成品单价
			exg.setPrice(machinPrice);
			// 更新成品总价
			exg.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(exg.getPrice())
					* CommonUtils.getDoubleExceptNull(exg.getAmount()), 5));
			this.dzscDao.saveDzscEmsExgBill(exg);
			bf.append("\t计算成功....\n");
		}
		List result = new ArrayList();
		result.add(exgBills);
		result.add(bf.toString());
		return result;
	}

	/**
	 * 计算成品耗用料件情况
	 * 
	 * @param exg
	 *            手册通关备案的成品资料
	 * @return List 是 TempDzscEmsImgBill型 计算成品耗用料件情况
	 */
	public List processImgAmountFromExg(DzscEmsExgBill exg) {
		// TODO Auto-generated method stub
		List<TempDzscEmsImgBill> imgs = new ArrayList();
		// 成品所耗用料件
		List<DzscEmsBomBill> list = dzscDao.findEmsPorBomBill(exg);
		DzscEmsImgBill img = null;
		Double[] imgEms = null;
		for (DzscEmsBomBill bom : list) {
			img = dzscDao.getDzscEmsImgBillFromDzscEmsBomBill(exg
					.getDzscEmsPorHead().getId(), bom.getImgSeqNum());
			if (img == null) {
				continue;
			}
			imgEms = processImgRemainContractMountAndMoney(img);
			TempDzscEmsImgBill tempBill = new TempDzscEmsImgBill();
			tempBill.setImg(img);
			tempBill.setAmount(imgEms[0]);
			tempBill.setMoney(imgEms[1]);
			imgs.add(tempBill);
		}
		return imgs;
	}

	/**
	 * 统计料件进出口数量，金额
	 * 
	 * @param imgBill
	 *            电子手册通关备案里的料件资料
	 * @return Double[]是 DzscEmsImgBill型 统计料件进出口数量，金额
	 */
	public Double[] processImgRemainContractMountAndMoney(DzscEmsImgBill imgBill) {
		Double[] imgEms = new Double[] { 0d, 0d };
		System.out.println("帐册号：" + imgBill.getDzscEmsPorHead().getEmsNo());
		/**
		 * 已生效料件进口
		 */
		double[] effectiveDirectImport = dzscContractCavDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.DIRECT_IMPORT, null,
						CustomsDeclarationState.EFFECTIVED);
		/**
		 * 已生效转厂进口
		 */
		double[] effectiveTransferFactoryImport = dzscContractCavDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.TRANSFER_FACTORY_IMPORT, null,
						CustomsDeclarationState.EFFECTIVED);

		/**
		 * 已生效余料转入
		 */
		double[] effectiveRemainForwardImport = dzscContractCavDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.REMAIN_FORWARD_IMPORT, null,
						CustomsDeclarationState.EFFECTIVED);

		/**
		 * 已生效料件退换出
		 */
		double[] effectiveExchangeExport = dzscContractCavDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, CustomsDeclarationState.EFFECTIVED);
		// 进出口数量＝(直接进口+转厂进口数量+余料转入数量-料件退换)
		imgEms[0] = effectiveDirectImport[0]
				+ effectiveTransferFactoryImport[0]
				+ effectiveRemainForwardImport[0] - effectiveExchangeExport[0];
		imgEms[1] = effectiveDirectImport[1]
				+ effectiveTransferFactoryImport[1]
				+ effectiveRemainForwardImport[1] - effectiveExchangeExport[1];
		return imgEms;
	}

	/**
	 * 根据成品数量和单耗重新反算成品单价和料件数量
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 */
	public void reCalContractUnitWaste(DzscEmsPorHead head) {
		// TODO Auto-generated method stub
		// 获取手册BOM
		List<DzscEmsBomBill> list = this.dzscDao.findDzscEmsBomBillByHead(head);
		// 参数
		DzscParameterSet parameter = dzscDao.findDzscParameterSet();
		/**
		 * 单价小数位控制
		 */
		int priceBit = parameter.getPriceBit();
		/**
		 * 数量小数位控制
		 */
		int countBit = parameter.getCountBit();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = parameter.getMoneyBit();

		for (DzscEmsBomBill bom : list) {
			if (!ModifyMarkState.ADDED.equals(bom.getModifyMark())
					|| !ModifyMarkState.MODIFIED.equals(bom.getModifyMark())) {// 只有新增,已修改状态进行修改
				continue;
			}
			// 成品出品数量
			double exgAmount = CommonUtils.getDoubleExceptNull(bom
					.getDzscEmsExgBill().getAmount());
			// 单耗
			double unitWaste = CommonUtils.getDoubleExceptNull(bom
					.getUnitWare());
			// 损耗
			double waste = (bom.getWare() == null ? 0.0 : bom.getWare()) / 100.0;
			// 计算单项用量
			double unitDosage = CommonUtils.getDoubleByDigit((waste >= 1 ? 0
					: unitWaste / (1 - waste)), 9);
			bom.setUnitDosage(CommonUtils.getDoubleByDigit(unitDosage, 5));
			// 计算bom数量=成品出品数量*单项用量
			bom.setAmount(CommonUtils.getDoubleByDigit(
					(exgAmount * unitDosage), countBit));
			// bom单价
			bom.setPrice(CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(bom.getPrice()), priceBit));
			// bom总金额=bom数量＊bom单价
			double totalPrice = (bom.getAmount() == null ? 0 : bom.getAmount())
					* (bom.getPrice() == null ? 0 : bom.getPrice());
			bom.setMoney(CommonUtils.getDoubleByDigit(totalPrice, moneyBit));
			this.dzscDao.saveDzscEmsBomBill(bom);
		}
		// 修改BOM反算成品
		if (parameter.getUpdateEmsBomWriteBackExg() != null
				&& parameter.getUpdateEmsBomWriteBackExg()) {
			List exgs = this.dzscDao.findDzscEmsExgBill(head);
			for (int i = 0; i < exgs.size(); i++) {
				DzscEmsExgBill exg = (DzscEmsExgBill) exgs.get(i);
				if (!ModifyMarkState.ADDED.equals(exg.getModifyMark())
						|| !ModifyMarkState.MODIFIED
								.equals(exg.getModifyMark())) {// 只有新增状态进行修改
					continue;
				}
				writeBackContractExg(exg, parameter);
			}
		}
		// 修改成品反算料件
		if (parameter.getUpdateEmsExgBomWriteBackImg() != null
				&& parameter.getUpdateEmsExgBomWriteBackImg()) {
			List imgs = this.dzscDao.findDzscEmsImgBill(head);
			for (int i = 0; i < imgs.size(); i++) {
				DzscEmsImgBill img = (DzscEmsImgBill) imgs.get(i);
				if (!ModifyMarkState.ADDED.equals(img.getModifyMark())
						|| !ModifyMarkState.MODIFIED
								.equals(img.getModifyMark())) {// 只有新增状态进行修改
					continue;
				}
				writeBackContractImg(head.getId(), img, parameter);
			}
		}
		// 统计合同备案的进口总值
		this.statContractImgMoney(head, parameter);
		// 统计合同备案的出口总值
		this.statContractExgMoney(head, parameter);

	}

	/**
	 * 保存合同成品来自统计
	 * 
	 * @param exg
	 *            合同成品
	 * @param parameter
	 *            电子手册参数设置表
	 */
	private void writeBackContractExg(DzscEmsExgBill exg,
			DzscParameterSet parameter) {
		/**
		 * 单价小数位控制
		 */
		int priceBit = parameter.getPriceBit();
		/**
		 * 数量小数位控制
		 */
		int countBit = parameter.getCountBit();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = parameter.getMoneyBit();

		if (ModifyMarkState.DELETED.equals(exg.getModifyMark())) {
			return;
		}
		// ll
		Double materielFee = CommonUtils.getDoubleByDigit(this
				.getFinishProductMaterielFee(exg), moneyBit);
		// Double unitNetWeight = CommonUtils.getDoubleByDigit(this
		// .getFinishProductUnitWeight(contractExg.getId()), 4);

		// if (materielFee.equals(contractExg.getMaterialFee())) {
		exg.setImgMoney(materielFee);
		// contractExg.setUnitNetWeight(unitNetWeight);
		/**
		 * 合计时 毛重 = 净重
		 */
		// contractExg.setUnitGrossWeight(unitNetWeight);
		/**
		 * 成品单价 = 原料费 + 加工费单价
		 */
		Double unitPrice = CommonUtils.getDoubleExceptNull(materielFee)
				+ CommonUtils.getDoubleExceptNull(exg.getMachinPrice());
		exg.setPrice(CommonUtils.getDoubleByDigit(unitPrice, priceBit));
		Double exportAmount = CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(exg.getAmount()), countBit);
		exg.setAmount(exportAmount);
		exg.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(exg.getPrice() * exportAmount), moneyBit));

		if (ModifyMarkState.UNCHANGE.equals(exg.getModifyMark())) {
			exg.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveDzscEmsExgBill(exg);
		// this.statContractExgCount(contractExg.getContract());
		// ll
		// this.statContractExgMoney(exg.getDzscEmsPorHead(), parameter);
		// }

	}

	/**
	 * 保存料件总表记录
	 * 
	 * @param contractId
	 *            合同表头Id
	 * @param img
	 *            合同料件
	 * @param parameter
	 *            电子手册参数设置表
	 */
	private void writeBackContractImg(String contractId, DzscEmsImgBill img,
			DzscParameterSet parameter) {// ,
		/**
		 * 单价小数位控制
		 */
		int priceBit = parameter.getPriceBit();
		/**
		 * 数量小数位控制
		 */
		int countBit = parameter.getCountBit();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = parameter.getMoneyBit();

		if (img == null || img.getSeqNum() == null
				|| ModifyMarkState.DELETED.equals(img.getModifyMark())) {
			return;
		}
		Double amount = this.dzscDao.findDzscEmsBomBillUsedAmount(contractId,
				img.getSeqNum());
		// if (amount.equals(contractImg.getAmount())) {
		img.setAmount(CommonUtils.getDoubleByDigit(amount, countBit));
		/**
		 * 申报单价
		 */
		Double declarePrice = CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(img.getPrice()), priceBit);
		img.setPrice(declarePrice);
		/**
		 * 反算总金额
		 */

		img.setMoney(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(img.getAmount())
				* declarePrice, moneyBit));
		if (ModifyMarkState.UNCHANGE.equals(img.getModifyMark())) {
			img.setModifyMark(ModifyMarkState.MODIFIED);
		}
		/**
		 * 保存料件总表信息
		 */
		this.dzscDao.saveDzscEmsImgBill(img);
		// ll
		// this.statContractImgCount(contractImg.getContract());
		// this.statContractImgMoney(img.getDzscEmsPorHead(), parameter);
		// }
	}

	/**
	 * 统计合同备案的出口总值
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 * @param parameter
	 *            电子手册参数设置表
	 */
	private void statContractExgMoney(DzscEmsPorHead head,
			DzscParameterSet parameter) {
		/**
		 * 金额小数位控制
		 */
		int moneyBit = parameter.getMoneyBit();
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}

		head.setExgAmount(CommonUtils.getDoubleByDigit(this.dzscDao
				.findDzscEmsExgBillMoney(head.getId()), moneyBit));
		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveDzscEmsPorHead(head);
	}

	/**
	 * 统计合同备案的进口总值
	 * 
	 * @param head
	 *            电子手册通关备案里的表头资料
	 * @param parameter
	 *            电子手册参数设置表
	 */
	private void statContractImgMoney(DzscEmsPorHead head,
			DzscParameterSet parameter) {
		/**
		 * 单价小数位控制
		 */
		int priceBit = parameter.getPriceBit();
		/**
		 * 数量小数位控制
		 */
		int countBit = parameter.getCountBit();
		/**
		 * 金额小数位控制
		 */
		int moneyBit = parameter.getMoneyBit();
		head = this.dzscDao.findDzscEmsPorHeadById(head.getId());
		if (head == null) {
			return;
		}
		head.setImgAmount(CommonUtils.getDoubleByDigit(this.dzscDao
				.findDzscEmsImgBillMoney(head.getId()), moneyBit));

		if (ModifyMarkState.UNCHANGE.equals(head.getModifyMark())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscDao.saveDzscEmsPorHead(head);
	}

	public List findAllDzscEmsExgBill() {
		return dzscDao.findAllDzscEmsExgBill();
	}

	/**
	 * 转抄通关备案成品单耗(成品单耗来自其他合同)
	 * 
	 * @param exgBillFrom
	 *            被拷贝的通关备案成品
	 * @param exgBillTo
	 *            通关备案成品 2010-05-31 hcl
	 */
	public void copyOtherDzscEmsPorBomBill(DzscEmsExgBill from,
			DzscEmsExgBill to) {
		//删除实体对象 防止传递过来的对象和缓存中的对象的版本不一致
		this.dzscDao.deleteAll(this.dzscDao.findEmsPorBomBill(to));
		//查找Bom
		List lsFrom = this.dzscDao.findEmsPorBomBill(from);
		for (int i = 0; i < lsFrom.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) lsFrom.get(i);
			DzscEmsBomBill newBomBill = new DzscEmsBomBill();
			try {
				PropertyUtils.copyProperties(newBomBill, bomBill);

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			newBomBill.setDzscEmsExgBill(to);
			newBomBill.setId(null);
			newBomBill.setAmount(CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(to.getAmount())
					* CommonUtils.getDoubleExceptNull(newBomBill
							.getUnitDosage()), 5));
			newBomBill.setMoney(CommonUtils
					.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(newBomBill.getAmount())
							* CommonUtils.getDoubleExceptNull(newBomBill
									.getPrice()), 5));
			newBomBill.setModifyMark(ModifyMarkState.ADDED);
			this.dzscDao.saveDzscEmsBomBill(newBomBill);
			this.writeBackDzscEmsExgBill(newBomBill);

			this.saveNewEmsImgBill(newBomBill,from);

			this.writeBackDzscEmsImgBillFromOther(newBomBill);
		}

	}
	/**
	 * 转抄其他合同单耗，如本合同无该料件则新增至本合同
	 * @param newBomBill
	 * 2010-06-03 hcl
	 * @param from 
	 */

	private void saveNewEmsImgBill(DzscEmsBomBill newBomBill, DzscEmsExgBill from) {
		this.dzscDao.findEmsImgBill(newBomBill,from);

	}

	/**
	 * 根据BOM的单项用量来反写料件的数量和金额 (bom来自其他合同)
	 * 
	 * @param dzscEmsBomBill
	 * 
	 *            手册通关备案的BOM资料
	 * @return String 是 DzscEmsImgBill 型 根据BOM的单项用量来反写料件的数量和金额
	 */
	private String writeBackDzscEmsImgBillFromOther(
			DzscEmsBomBill dzscEmsBomBill) {
		String resul = "";
		DzscParameterSet parameter = this.dzscDao.findDzscParameterSet();
//		if (parameter.getUpdateEmsExgBomWriteBackImg() == null
//				|| !parameter.getUpdateEmsExgBomWriteBackImg()) {
//			return "";
//		}
		int countBit = parameter.getCountBit() == null ? 5 : parameter
				.getCountBit();
		int moneyBit = parameter.getMoneyBit() == null ? 5 : parameter
				.getMoneyBit();
		if (dzscEmsBomBill == null || dzscEmsBomBill.getImgSeqNum() == null) {
			return "";
		}
		DzscEmsPorHead dzscEmsPorHead = dzscEmsBomBill.getDzscEmsExgBill()
				.getDzscEmsPorHead();
		System.out.println("bomPrice="+dzscEmsBomBill.getPrice());
		List list = this.dzscDao.findDzscEmsImgBillBySeqNum(dzscEmsPorHead
				.getId(), dzscEmsBomBill.getImgSeqNum());
		if (list.size() > 0 && list.get(0) != null) {
			DzscEmsImgBill dzscEmsImgBill = (DzscEmsImgBill) list.get(0);
			if (dzscEmsImgBill == null || dzscEmsImgBill.getSeqNum() == null) {
				return "";
			}
			Double amount = this.dzscDao.findDzscEmsBomImgUsedAmount(
					dzscEmsPorHead, dzscEmsImgBill.getSeqNum());
			if (!amount.equals(dzscEmsImgBill.getAmount())) {
				if (ModifyMarkState.UNCHANGE.equals(dzscEmsImgBill
						.getModifyMark())) {
					dzscEmsImgBill.setModifyMark(ModifyMarkState.MODIFIED);
				}
				dzscEmsImgBill.setAmount(CommonUtils.getDoubleByDigit(amount,
						countBit));
				dzscEmsImgBill.setMoney(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(dzscEmsImgBill
								.getAmount())
								* CommonUtils
										.getDoubleExceptNull(dzscEmsImgBill
												.getPrice()), moneyBit));
				this.dzscDao.saveDzscEmsImgBill(dzscEmsImgBill);
				// 当修改数量小于已进口数量，提示
				Double[] imgEms = processImgRemainContractMountAndMoney(dzscEmsImgBill);
				if (imgEms[0] > CommonUtils.getDoubleExceptNull(dzscEmsImgBill
						.getAmount())) {
					resul = "料件备案－－备案序号："
							+ dzscEmsImgBill.getSeqNum()
							+ "  归并序号："
							+ dzscEmsImgBill.getTenSeqNum()
							+ "\n已进口数量："
							+ imgEms[0]
							+ "  料件当前数量："
							+ CommonUtils.getDoubleExceptNull(dzscEmsImgBill
									.getAmount()) + "\n------------------\n";
				}
			}
			this.statDzscEmsPorImgBillMoney(dzscEmsImgBill.getDzscEmsPorHead());
		}
		return resul;
	}
	public void changeEmsEdiForbid(String emsNo, String seqNum,
			boolean isMaterial, boolean isForbid) {
		List list = null;
		System.out.println("emsNo="+emsNo);
		System.out.println("seqNum="+seqNum);
		if (isMaterial) {// 料件
			DzscEmsImgBill img = dzscDao.findDzscEmsImgBillByEmsNoSeqNum(emsNo,Integer.parseInt(seqNum) );
				System.out.println("=L=isForbid="+isForbid);
				img.setIsForbid(Boolean.valueOf(isForbid));
				dzscDao.saveOrUpdate(img);
		} else {// 成品
			DzscEmsExgBill exg =dzscDao.findDzscEmsExgBillByEmsNoSeqNum(emsNo, Integer.parseInt(seqNum));
				System.out.println("=C=isForbid="+isForbid);
				exg.setIsForbid(Boolean.valueOf(isForbid));
				dzscDao.saveOrUpdate(exg);
		}
	}

	public void deleteCommdityForbid(DzscCommdityForbid obj) {
		dzscDao.delete(obj);
	}


	public List addCommdityForbid(List list, boolean isMaterial, String emsNo) {
		List result=new ArrayList();
		for(int i=0;i<list.size();i++){
			BillTemp temp =(BillTemp) list.get(i);
			DzscCommdityForbid dzscForbid=new DzscCommdityForbid();
			dzscForbid.setCompany(CommonUtils.getCompany());
			dzscForbid.setEmsNo(emsNo);
			dzscForbid.setSeqNum(temp.getBill1());
			dzscForbid.setComplex(temp.getBill2());
			dzscForbid.setName(temp.getBill3());
			dzscForbid.setSpec(temp.getBill4());
			dzscForbid.setUnit(temp.getBill5());
			dzscForbid.setVersion(temp.getBill6());
			dzscForbid.setForbiddate(dateToStandDate(new Date()));
			dzscForbid.setForbiduser(CommonUtils.getRequest().getUser().getUserName());
			if (isMaterial) {
				dzscForbid.setType(MaterielType.MATERIEL);
			} else {
				dzscForbid.setType(MaterielType.FINISHED_PRODUCT);
			}
			dzscDao.saveOrUpdate(dzscForbid);
			System.out.println("getbill1="+temp.getBill1());
			changeEmsEdiForbid(emsNo,temp.getBill1(), isMaterial, true);
			result.add(dzscForbid);
		}
		return result;
	}

	public List getTempContractForBid(boolean isMaterial, String emsNo) {
		List list = dzscDao.findEdiMaterielInfo(isMaterial,emsNo);
		System.out.println("list DzscEmsImgBil="+list.size());
		List tempListExg = new ArrayList();
		if (isMaterial) {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsImgBill img = (DzscEmsImgBill) list.get(i);
				BillTemp obj = new BillTemp();
				System.out.println("seqNum="+img.getSeqNum());
				obj.setBill1(String.valueOf(img.getSeqNum()));
				obj.setBill2(img.getComplex() == null ? null : img.getComplex()
						.getCode());
				obj.setBill3(img.getName());
				obj.setBill4(img.getSpec());
				obj.setBill5(img.getUnit() == null ? null : img.getUnit()
						.getName());
				obj.setBill7(String.valueOf(img.getAmount() == null ? 0.0 : img
						.getAmount()));
				tempListExg.add(obj);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				DzscEmsExgBill exg = (DzscEmsExgBill) list.get(i);
					BillTemp obj = new BillTemp();
						obj.setBill1(String.valueOf(exg.getSeqNum()));
						obj.setBill2(exg.getComplex() == null ? null : exg
								.getComplex().getCode());
						obj.setBill3(exg.getName());
						obj.setBill4(exg.getSpec());
						obj.setBill5(exg.getUnit() == null ? null : exg
								.getUnit().getName());
						obj.setBill7(String.valueOf(exg.getAmount() == null ? 0.0 : exg
								.getAmount()));
						tempListExg.add(obj);
				}
			}
		return tempListExg;
	}
	/**
	 * 日期转成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}
	/**
	 * 判断通关手册成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param DzscEmsPorHead
	 *            通关手册
	 */
	public String checkContractIsUnitModifyMarkExgBom(DzscEmsPorHead head) {
		StringBuffer message = new StringBuffer();
		List lsExgBill = new ArrayList();
		lsExgBill = this.dzscDao.findDzscEmsExgBill(head);
		for (int i = 0; i < lsExgBill.size(); i++) {
			DzscEmsExgBill exg = (DzscEmsExgBill) lsExgBill.get(i);
			List lsBomBill = this.dzscDao.findDzscEmsBomBillByExgSeqNum(head,
					exg.getSeqNum());
			for (int j = 0; j < lsBomBill.size(); j++) {
				if (!((DzscEmsBomBill) lsBomBill.get(j)).getModifyMark()
						.equals(exg.getModifyMark())) {
					message.append(exg.getSeqNum() + ",");
					break;
				}
			}
		}
		return message.toString();
	}
}
