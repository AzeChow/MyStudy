package com.bestway.wjsz.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.jptds.contract.entity.WJContract;
import com.bestway.jptds.contract.entity.WJContractBom;
import com.bestway.jptds.contract.entity.WJContractExg;
import com.bestway.jptds.contract.entity.WJContractImg;
import com.bestway.jptds.credence.action.CredenceAction;
import com.bestway.jptds.credence.entity.ExgCredence;
import com.bestway.jptds.credence.entity.ImgCredence;
import com.bestway.waijing.action.WjszAction;
import com.bestway.waijing.entity.TempPingZheng;

public class SUploadLogic {

	private static WjszAction wjszAction = (WjszAction) com.bestway.bcus.client.common.CommonVars
			.getApplicationContext().getBean("wjszAction");
	private static CredenceAction wjCredenceAction = (CredenceAction) com.bestway.jptds.client.common.CommonVars
			.getApplicationContext().getBean("credenceAction");
	private static com.bestway.jptds.custombase.action.CustomBaseAction wjCustomBaseAction = (com.bestway.jptds.custombase.action.CustomBaseAction) com.bestway.jptds.client.common.CommonVars
			.getApplicationContext().getBean("customBaseAction");
	private static com.bestway.jptds.contract.action.ContractAction wjContractAction = (com.bestway.jptds.contract.action.ContractAction) com.bestway.jptds.client.common.CommonVars
			.getApplicationContext().getBean("contractAction");
	private static String retishi = "";

	/**
	 * 根据类名和编码查询海关基础资料实体
	 * 
	 * @param clsName
	 * @param codeField
	 * @param codeValue
	 * @return
	 */
	public static Object findCustomsBaseEntityFromJPtds(Class<?> clsName,
			String codeField, String codeValue) {
		return wjCustomBaseAction.findCustomsBaseEntity(
				com.bestway.jptds.client.common.CommonVars.getRequest(),
				clsName, codeField, codeValue);
	}

	/**
	 * 获取凭证料件临时资料
	 * 
	 * @param values
	 * @param selectLsImg
	 * @return
	 */
	public static List getTempImgCredenceData(String values, List selectLsImg) {
		int selectInt = Integer.parseInt(values);
		String infoStr = "";
		List resultList = new ArrayList();
		List imgList = new ArrayList();
		if (selectInt == 0) {// 经营范围
			for (int i = 0; i < selectLsImg.size(); i++) {
				EmsEdiTrImg img = (EmsEdiTrImg) selectLsImg.get(i);
				TempPingZheng obj = new TempPingZheng();
				obj.setSeqNum(img.getPtNo());
				obj.setName(img.getName());
				obj.setCode(img.getComplex());
				obj.setType(1);// 料件
				resultList.add(obj);
				if (imgList.contains(obj.getSeqNum())) {
					infoStr += "经营范围料件序号[" + obj.getSeqNum() + "]有重复，不可以上传！\n";
				} else {
					imgList.add(obj.getSeqNum());
				}
			}
		} else if (selectInt == 1) {// 电子手册-合同备案
			for (int i = 0; i < selectLsImg.size(); i++) {
				DzscEmsImgWj img = (DzscEmsImgWj) selectLsImg.get(i);
				TempPingZheng obj = new TempPingZheng();
				if (img.getWjCode() == null
						|| "".equals(img.getWjCode().trim())) {
					infoStr += "合同备案料件[" + obj.getSeqNum() + "]外经序号为空，不可以上传！\n";
				} else {
					obj.setSeqNum(Integer.valueOf(img.getWjCode().trim()));// 外经编码
				}
				obj.setName(img.getFourName());
				obj.setSpec(img.getFourSpec());
				obj.setCode(img.getFourComplex());
				obj.setUnit(img.getFourUnit() == null ? null : img
						.getFourUnit().getCode());
				obj.setType(1);// 料件
				resultList.add(obj);
				if (imgList.contains(obj.getSeqNum())) {
					infoStr += "合同备案料件序号[" + obj.getSeqNum() + "]有重复，不可以上传！\n";
				} else {
					imgList.add(obj.getSeqNum());
				}
			}
		} else if (selectInt == 2) {// 电子化手册-备案资料库备案OK!
			for (int i = 0; i < selectLsImg.size(); i++) {
				BcsDictPorImg img = (BcsDictPorImg) selectLsImg.get(i);
				TempPingZheng obj = new TempPingZheng();
				if (img.getWjCode() == null
						|| "".equals(img.getWjCode().trim())) {
					infoStr += "备案资料库料件[" + obj.getSeqNum()
							+ "]外经序号为空，不可以上传！\n";
				} else {
					obj.setSeqNum(Integer.valueOf(img.getWjCode()));// 外经编码
				}
				obj.setName(img.getCommName());
				obj.setSpec(img.getCommSpec());
				obj.setCode(img.getComplex() == null ? null : img.getComplex()
						.getCode());
				obj.setUnit(img.getComUnit() == null ? null : img.getComUnit()
						.getCode());
				obj.setCurr(img.getCurr() == null ? null : img.getCurr()
						.getCode());
				obj.setUnitPrice(img.getUnitPrice());
				obj.setType(1);// 料件
				resultList.add(obj);
				if (imgList.contains(obj.getSeqNum())) {
					infoStr += "备案资料库料件序号[" + obj.getSeqNum() + "]有重复，不可以上传！\n";
				} else {
					imgList.add(obj.getSeqNum());
				}
			}
		}
		if (!"".equals(infoStr)) {
			throw new RuntimeException(infoStr);
		}
		return resultList;
	}

	/**
	 * 获取凭证成品临时资料
	 * 
	 * @param values
	 * @param selectLsExg
	 * @return
	 */
	public static List getTempExgCredenceData(String values, List selectLsExg) {
		int selectInt = Integer.parseInt(values);
		String infoStr = "";
		List<TempPingZheng> resultList = new ArrayList<TempPingZheng>();
		List exgList = new ArrayList();
		if (selectInt == 0) {// 经营范围
			for (int i = 0; i < selectLsExg.size(); i++) {
				EmsEdiTrExg exg = (EmsEdiTrExg) selectLsExg.get(i);
				TempPingZheng obj = new TempPingZheng();
				obj.setSeqNum(exg.getPtNo());
				obj.setName(exg.getName());
				obj.setCode(exg.getComplex());
				obj.setType(0);// 成品
				resultList.add(obj);
				if (exgList.contains(obj.getSeqNum())) {
					infoStr += "经营范围成品序号[" + obj.getSeqNum() + "]有重复，不可以上传！\n";
				} else {
					exgList.add(obj.getSeqNum());
				}
			}

		} else if (selectInt == 1) {// 电子手册-合同备案
			for (int i = 0; i < selectLsExg.size(); i++) {
				DzscEmsExgWj exg = (DzscEmsExgWj) selectLsExg.get(i);
				TempPingZheng obj = new TempPingZheng();
				if (exg.getWjCode() == null
						|| "".equals(exg.getWjCode().trim())) {
					infoStr += "合同备案成品[" + obj.getSeqNum() + "]外经编码为空，不可以上传！\n";
				} else {
					obj.setSeqNum(Integer.valueOf(exg.getWjCode()));// 外经编码
				}
				obj.setName(exg.getFourName());
				obj.setSpec(exg.getFourSpec());
				obj.setCode(exg.getFourComplex());
				obj.setUnit(exg.getFourUnit() == null ? null : exg
						.getFourUnit().getCode());
				obj.setType(0);// 成品
				resultList.add(obj);
				if (exgList.contains(obj.getSeqNum())) {
					infoStr += "合同备案成品序号[" + obj.getSeqNum() + "]有重复，不可以上传！\n";
				} else {
					exgList.add(obj.getSeqNum());
				}
			}
		} else if (selectInt == 2) {// 电子化手册-备案资料库备案OK!
			for (int i = 0; i < selectLsExg.size(); i++) {
				BcsDictPorExg exg = (BcsDictPorExg) selectLsExg.get(i);
				TempPingZheng obj = new TempPingZheng();
				if (exg.getWjCode() == null
						|| "".equals(exg.getWjCode().trim())) {
					infoStr += "合同备案成品[" + obj.getSeqNum() + "]外经编码为空，不可以上传！\n";
				} else {
					obj.setSeqNum(Integer.valueOf(exg.getWjCode()));// 外经编码
				}
				obj.setName(exg.getCommName());
				obj.setSpec(exg.getCommSpec());
				obj.setCode(exg.getComplex() == null ? null : exg.getComplex()
						.getCode());
				obj.setUnit(exg.getComUnit() == null ? null : exg.getComUnit()
						.getCode());
				obj.setCurr(exg.getCurr() == null ? null : exg.getCurr()
						.getCode());
				obj.setUnitPrice(exg.getUnitPrice());
				obj.setType(0);// 成品
				resultList.add(obj);
				if (exgList.contains(obj.getSeqNum())) {
					infoStr += "备案资料库成品序号[" + obj.getSeqNum() + "]有重复，不可以上传！\n";
				} else {
					exgList.add(obj.getSeqNum());
				}
			}
		}
		if (!"".equals(infoStr)) {
			throw new RuntimeException(infoStr);
		}
		return resultList;
	}

	/**
	 * 两个对象比较是否相等
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	private static boolean checkObjectIsEquals(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		} else if (o1 != null && o2 == null || o1 == null && o2 != null) {
			return false;
		} else {
			return o1.equals(o2);
		}
	}

	/**
	 * 凭证上传资料OK!
	 * 
	 */
	public static String uploadCredenceData(boolean isConver, Integer selectIn,
			List selectLsImg, List selectLsExg) {
		Hashtable<Integer, ImgCredence> imgHs = new Hashtable<Integer, ImgCredence>();
		Hashtable<Integer, ExgCredence> exgHs = new Hashtable<Integer, ExgCredence>();
		int insertImgNum = 0;
		int updateImgNum = 0;
		int noUpdateImgNum = 0;
		int insertExgNum = 0;
		int updateExgNum = 0;
		int noUpdateExgNum = 0;
		String infoStr = "";
		List listTempImg = getTempImgCredenceData(String.valueOf(selectIn),
				selectLsImg);
		List listImgCredence = wjCredenceAction.findImgCredence(
				com.bestway.jptds.client.common.CommonVars.getRequest(), "",
				"", "");
		for (int i = 0; i < listImgCredence.size(); i++) {
			ImgCredence imgCredence = (ImgCredence) listImgCredence.get(i);
			imgHs.put(imgCredence.getCredenceNo(), imgCredence);
		}
		List listExgCredence = wjCredenceAction.findExgCredence(
				com.bestway.jptds.client.common.CommonVars.getRequest(), "",
				"", "");
		for (int i = 0; i < listExgCredence.size(); i++) {
			ExgCredence exgCredence = (ExgCredence) listExgCredence.get(i);
			exgHs.put(exgCredence.getCredenceNo(), exgCredence);
		}
		for (int i = 0; i < listTempImg.size(); i++) {
			TempPingZheng temp = (TempPingZheng) listTempImg.get(i);
			ImgCredence imgCredence = imgHs.get(temp.getSeqNum());
			com.bestway.jptds.custombase.entity.Curr curr = (temp.getCurr() == null ? null
					: (com.bestway.jptds.custombase.entity.Curr) findCustomsBaseEntityFromJPtds(
							com.bestway.jptds.custombase.entity.Curr.class,
							"code", temp.getCurr().trim()));
			com.bestway.jptds.custombase.entity.Unit unit = (temp.getUnit() == null ? null
					: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
							com.bestway.jptds.custombase.entity.Unit.class,
							"code", temp.getUnit().trim()));
			if (imgCredence == null) {
				imgCredence = new ImgCredence();
				imgCredence.setCredenceNo(temp.getSeqNum());
				imgCredence
						.setAppState(com.bestway.jptds.common.DeclareState.DECLARE_NOT);
				imgCredence
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				insertImgNum++;
			} else {
				if (com.bestway.jptds.common.DeclareState.DECLARE_WAIT
						.equals(imgCredence.getAppState())
						|| com.bestway.jptds.common.DeclareState.DECLARE_PASS
								.equals(imgCredence.getAppState())) {
					noUpdateExgNum++;
					continue;
				}
				if (!isConver) {
					noUpdateImgNum++;
					continue;
				}
				if (checkObjectIsEquals(imgCredence.getComplexCode(), temp
						.getCode())
						&& checkObjectIsEquals(imgCredence.getName(), temp
								.getName())
						&& checkObjectIsEquals(imgCredence.getSpec(), temp
								.getSpec())
						&& checkObjectIsEquals(imgCredence.getCurr(), curr)
						&& checkObjectIsEquals(imgCredence.getUnit(), unit)
						&& checkObjectIsEquals(imgCredence.getUnitPrice(), temp
								.getUnitPrice())) {
					noUpdateImgNum++;
					continue;
				}
				imgCredence
						.setAppState(com.bestway.jptds.common.DeclareState.DECLARE_NOT);
				if (!com.bestway.jptds.common.ModifyMarkState.ADDED
						.equals(imgCredence.getModifyMark())) {
					imgCredence
							.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
				}
				updateImgNum++;
			}
			imgCredence.setComplexCode(temp.getCode());
			imgCredence.setName(temp.getName());
			imgCredence.setSpec(temp.getSpec());
			imgCredence.setCurr(curr);
			imgCredence.setUnit(unit);
			imgCredence.setUnitPrice(temp.getUnitPrice());
			imgCredence.setDataOriginType("2");//数据来源方式1:QP导入2:手工录入
			wjCredenceAction.saveImgCredence(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					imgCredence);
		}
		List listTempExg = getTempExgCredenceData(String.valueOf(selectIn),
				selectLsExg);
		for (int i = 0; i < listTempExg.size(); i++) {
			TempPingZheng temp = (TempPingZheng) listTempExg.get(i);
			ExgCredence exgCredence = exgHs.get(temp.getSeqNum());
			com.bestway.jptds.custombase.entity.Curr curr = (temp.getCurr() == null ? null
					: (com.bestway.jptds.custombase.entity.Curr) findCustomsBaseEntityFromJPtds(
							com.bestway.jptds.custombase.entity.Curr.class,
							"code", temp.getCurr().trim()));
			com.bestway.jptds.custombase.entity.Unit unit = (temp.getUnit() == null ? null
					: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
							com.bestway.jptds.custombase.entity.Unit.class,
							"code", temp.getUnit().trim()));
			if (exgCredence == null) {
				exgCredence = new ExgCredence();
				exgCredence.setCredenceNo(temp.getSeqNum());
				exgCredence
						.setAppState(com.bestway.jptds.common.DeclareState.DECLARE_NOT);
				exgCredence
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				insertExgNum++;
			} else {
				if (com.bestway.jptds.common.DeclareState.DECLARE_WAIT
						.equals(exgCredence.getAppState())
						|| com.bestway.jptds.common.DeclareState.DECLARE_PASS
								.equals(exgCredence.getAppState())) {
					noUpdateExgNum++;
					continue;
				}
				if (!isConver) {
					noUpdateExgNum++;
					continue;
				}
				if (checkObjectIsEquals(exgCredence.getComplexCode(), temp
						.getCode())
						&& checkObjectIsEquals(exgCredence.getName(), temp
								.getName())
						&& checkObjectIsEquals(exgCredence.getSpec(), temp
								.getSpec())
						&& checkObjectIsEquals(exgCredence.getCurr(), curr)
						&& checkObjectIsEquals(exgCredence.getUnit(), unit)
						&& checkObjectIsEquals(exgCredence.getUnitPrice(), temp
								.getUnitPrice())) {
					noUpdateExgNum++;
					continue;
				}
				updateExgNum++;
				exgCredence
						.setAppState(com.bestway.jptds.common.DeclareState.DECLARE_NOT);
				if (!com.bestway.jptds.common.ModifyMarkState.ADDED
						.equals(exgCredence.getModifyMark())) {
					exgCredence
							.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
				}
			}
			exgCredence.setComplexCode(temp.getCode());
			exgCredence.setName(temp.getName());
			exgCredence.setSpec(temp.getSpec());
			if (temp.getCurr() != null && !"".equals(temp.getCurr().trim())) {
				exgCredence
						.setCurr((com.bestway.jptds.custombase.entity.Curr) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Curr.class,
								"code", temp.getCurr().trim()));
			}
			if (temp.getUnit() != null && !"".equals(temp.getUnit().trim())) {
				exgCredence
						.setUnit((com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", temp.getUnit().trim()));
			}
			exgCredence.setUnitPrice(temp.getUnitPrice());
			exgCredence.setDataOriginType("2");//数据来源方式1:QP导入2:手工录入
			wjCredenceAction.saveExgCredence(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					exgCredence);
		}
		infoStr = "上传成功！\n(不存在)插入料件数据：" + String.valueOf(insertImgNum)
				+ " 笔；插入成品数据：" + String.valueOf(insertExgNum) + " 笔；\n";
		infoStr += "(存在且未发送)更新料件数据：" + String.valueOf(updateImgNum)
				+ " 笔；更新成品数据：" + String.valueOf(updateExgNum) + " 笔；\n";
		infoStr += "(存在且发送)未更新料件数据：" + String.valueOf(noUpdateImgNum)
				+ " 笔；未更新成品数据：" + String.valueOf(noUpdateExgNum) + " 笔";
		return "数据上传信息！\n\n" + infoStr;
	}

	private static Object createWJContract() {
		String str = "";
		WJContract wjc = new WJContract();
		com.bestway.jptds.system.entity.Company cop = com.bestway.jptds.client.common.CommonVars
				.getCompany();
		if (cop.getCode() == null || cop.getCode().trim().equals("")) {
			str += "公司资料加工单位编码为空！\n";
		}
		wjc.setOwnerCode(cop.getCode());
		if (cop.getOwnerCustomsCode() == null
				|| cop.getOwnerCustomsCode().getCode().trim().equals("")) {
			str += "公司资料加工单位主管海关为空！\n";
		}
		wjc.setOwnerCustoms(cop.getOwnerCustomsCode());
		if (cop.getOwnerAdress() == null
				|| cop.getOwnerAdress().trim().equals("")) {
			str += "公司资料加工单位地址为空！\n";
		}
		wjc.setOwnerDetal(cop.getOwnerAdress());
		if (cop.getName() == null || cop.getName().trim().equals("")) {
			str += "公司资料加工单位名称为空！\n";
		}
		wjc.setOwnerName(cop.getName());
		if (cop.getCompanyCoType() == null) {
			str += "公司资料加工单位性质为空！\n";
		}
		wjc.setOwnerTypeCode(cop.getCompanyCoType() == null ? null : cop
				.getCompanyCoType().getCode());
		wjc.setOwnerTypeName(cop.getCompanyCoType() == null ? null : cop
				.getCompanyCoType().getName());
		wjc.setOwnerTel((cop.getCustomserManager() == null ? "" : cop
				.getCustomserManager())
				+ "/"
				+ (cop.getCusManagerWorkPhone() == null ? "" : cop
						.getCusManagerWorkPhone()));
		// -----------------------------------------------------------------------------------------
		if (cop.getBuCode() == null || cop.getBuCode().trim().equals("")) {
			str += "公司资料经营单位编码为空！\n";
		}
		wjc.setBuCode(cop.getBuCode());
		if (cop.getBuName() == null || cop.getBuName().trim().equals("")) {
			str += "公司资料经营单位名称为空！\n";
		}
		wjc.setBuName(cop.getBuName());
		if (cop.getBuAdress() == null || cop.getBuAdress().trim().equals("")) {
			str += "公司资料经营单位地址为空！\n";
		}
		wjc.setBuDetal(cop.getBuAdress());
		// wjc.setIsCurrContract(false);
		wjc.setBuTypeCode(cop.getBuCoType() == null ? null : cop.getBuCoType()
				.getCode());
		wjc.setBuTypeName(cop.getBuCoType() == null ? null : cop.getBuCoType()
				.getName());
		wjc.setBuTel((cop.getCustomserManager() == null ? "" : cop
				.getCustomserManager())
				+ "/"
				+ (cop.getCusManagerWorkPhone() == null ? "" : cop
						.getCusManagerWorkPhone()));
		wjc.setBuBankNumber(cop.getBuBankNumber());
		wjc.setCurr(cop.getCurr());
		wjc.setTrade(cop.getTrade());
		wjc.setInPortCode(cop.getInPortCode());
		wjc.setInPortName(cop.getInPortName());
		wjc.setOutPortCode(cop.getOutPortCode());
		wjc.setOutPortName(cop.getOutPortName());
		// ----------------------------------------------------------------
		wjc.setModifyMarkState(com.bestway.jptds.common.ModifyMarkState.ADDED);
		wjc.setApproveState(com.bestway.jptds.common.DeclareState.DECLARE_NOT);
		wjc.setOption1(true);
		wjc = wjContractAction.createApplyNo(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		if (str.trim().equals("")) {
			return wjc;
		} else {
			return str;
		}

	}

	/**
	 * 电子手册上传资料OK!
	 * 
	 */
	public static String uploadDataDzsc(boolean isConver, DzscEmsPorHead head) {
		List rlist = wjContractAction
				.checkCanAddContract(com.bestway.jptds.client.common.CommonVars
						.getRequest());
		Boolean bl = (Boolean) rlist.get(0);
		Integer canAdd = (Integer) rlist.get(1);
		Integer added = (Integer) rlist.get(2);
		String infoStr = "";
		if (!bl) {
			infoStr = "已存在新合同数(" + added + ")不能大于可签合同份数(" + canAdd + ")";
			return infoStr;
		}
		List saveLs = new ArrayList();
		int insertNumLj = 0;
		int updateNumLj = 0;
		int insertNumCp = 0;
		int updateNumCp = 0;
		int insertNumBom = 0;
		int updateNumBom = 0;
		Map<Integer, WJContractImg> mapWJContractImg = new HashMap<Integer, WJContractImg>();
		Map<Integer, WJContractExg> mapWJContractExg = new HashMap<Integer, WJContractExg>();
		Map<String, WJContractBom> mapWJContractBom = new HashMap<String, WJContractBom>();

		boolean isNewContract = false;
		com.bestway.jptds.system.entity.Company wjCompany = com.bestway.jptds.client.common.CommonVars
				.getCompany();
		WJContract wjc = null;
		if (head.getWjComputerNo() == null
				|| "".equals(head.getWjComputerNo().trim())) {// 全套新增
			Object obj = createWJContract();
			if (obj instanceof WJContract) {
				wjc = (WJContract) obj;
			} else {
				throw new RuntimeException(obj.toString());
			}

			wjc = (WJContract) wjContractAction.saveOrUpdateObject(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					wjc);
			isNewContract = true;
		} else {
			List listHead = wjContractAction.findWJContractByApplyNo(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					head.getWjComputerNo().trim());
			if (listHead.size() > 0 && listHead.get(0) != null) {
				wjc = (WJContract) listHead.get(0);
				if (com.bestway.jptds.common.DeclareState.DECLARE_WAIT
						.equals(wjc.getApproveState())) {
					infoStr = "外经申请文号" + head.getWjComputerNo().trim()
							+ "的合同 等待审批状态，所以不能上传资料";
					return infoStr;
				} else if (com.bestway.jptds.common.DeclareState.DECLARE_PASS
						.equals(wjc.getApproveState())) {
					infoStr = "外经申请文号" + head.getWjComputerNo().trim()
							+ "的合同 正在执行状态，所以不能上传资料";
					return infoStr;
				}
			} else {
				Object obj = createWJContract();
				if (obj instanceof WJContract) {
					wjc = (WJContract) obj;
				} else {
					throw new RuntimeException(obj.toString());
				}
				wjc = (WJContract) wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjc);
				isNewContract = true;
			}
		}
		if (isNewContract || isConver) {
			String portCode = "";
			String portName = "";
			if (head.getIePort1() != null) {
				portCode = head.getIePort1().getCode();
				portName = head.getIePort1().getName();
			}
			if (head.getIePort2() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort2().getCode();
					portName += "/" + head.getIePort2().getName();
				} else {
					portCode = head.getIePort2().getCode();
					portName = head.getIePort2().getName();
				}
			}

			if (head.getIePort3() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort3().getCode();
					portName += "/" + head.getIePort3().getName();
				} else {
					portCode = head.getIePort3().getCode();
					portName = head.getIePort3().getName();
				}
			}
			if (head.getIePort4() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort4().getCode();
					portName += "/" + head.getIePort4().getName();
				} else {
					portCode = head.getIePort4().getCode();
					portName = head.getIePort4().getName();
				}
			}
			if (head.getIePort5() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort5().getCode();
					portName += "/" + head.getIePort5().getName();
				} else {
					portCode = head.getIePort5().getCode();
					portName = head.getIePort5().getName();
				}
			}
			wjc.setInPortCode(portCode);
			wjc.setInPortName(portName);
			wjc.setOutPortCode(portCode);
			wjc.setOutPortName(portName);

			wjc.setApplyDate(head.getBeginDate());
			wjc.setEffectiveDate(head.getEndDate());
			wjc.setImporTotalMoney(head.getImgAmount());
			wjc.setExporTotalMoney(head.getExgAmount());

			if (head.getTrade() != null) {
				if (head.getTrade().getName().indexOf("来料") >= 0) {
					wjc
							.setTrade((com.bestway.jptds.custombase.entity.Trade) findCustomsBaseEntityFromJPtds(
									com.bestway.jptds.custombase.entity.Trade.class,
									"code", "L"));
					wjc.setWsContractNo(head.getImContractNo());
				} else if (head.getTrade().getName().indexOf("进料") >= 0) {
					wjc
							.setTrade((com.bestway.jptds.custombase.entity.Trade) findCustomsBaseEntityFromJPtds(
									com.bestway.jptds.custombase.entity.Trade.class,
									"code", "J"));
					wjc.setImportContractNo(head.getImContractNo());
					wjc.setExportContractNo(head.getIeContractNo());
				}
			}
			com.bestway.jptds.custombase.entity.Curr curr = head.getCurr() == null ? null
					: (com.bestway.jptds.custombase.entity.Curr) findCustomsBaseEntityFromJPtds(
							com.bestway.jptds.custombase.entity.Curr.class,
							"code", head.getCurr().getCode());
			if (curr != null) {
				wjc.setCurr(curr);
				Double rate = wjContractAction
						.findCurrRateOfDollar(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), curr);
				if (rate == null) {
					rate = 0.0;
				}
				wjc.setDollarRate(rate);
				double importTotal = wjc.getImporTotalMoney() == null ? 0.0
						: wjc.getImporTotalMoney();
				double exportTotal = wjc.getExporTotalMoney() == null ? 0.0
						: wjc.getExporTotalMoney();
				wjc.setImporTotalMoneyDollar(importTotal * rate);
				wjc.setExporTotalMoneyDollar(exportTotal * rate);
			}
			if (!isNewContract
					&& !com.bestway.jptds.common.ModifyMarkState.ADDED
							.equals(wjc.getModifyMarkState())) {
				wjc
						.setModifyMarkState(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
			}
			wjc = (WJContract) wjContractAction.saveOrUpdateObject(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					wjc);
		} else {
			return "";
		}
		head.setWjComputerNo(wjc.getApplyNo());
		saveLs.add(head);
		wjszAction.saveListObj(new Request(CommonVars.getCurrUser()), saveLs);
		List wjImgList = wjContractAction.findWJContractImg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjImgList.size(); i++) {
			WJContractImg wjContractImg = (WJContractImg) wjImgList.get(i);
			mapWJContractImg.put(wjContractImg.getSeqNum(), wjContractImg);
		}
		List imgList = (List) wjszAction.findDzscImg(new Request(CommonVars
				.getCurrUser()), head);
		for (int i = 0; i < imgList.size(); i++) {
			boolean isNewContractImg = false;
			DzscEmsImgBill imgBill = (DzscEmsImgBill) imgList.get(i);
			WJContractImg wjContractImg = mapWJContractImg.get(imgBill
					.getSeqNum());
			if (wjContractImg == null) {
				wjContractImg = new WJContractImg();
				wjContractImg.setSeqNum(imgBill.getSeqNum());
				wjContractImg.setWjContract(wjc);
				wjContractImg
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				isNewContractImg = true;
			}
			/**
			 * 当是新增的料件或覆盖旧的料件时保存
			 */
			if (isNewContractImg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String wjEmsNo = imgBill.getDzscEmsPorHead().getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer wjSeqNum = imgBill.getWjSeqNum();
				// 抓取合同备案中的外经编号==外经系统中的凭证序号。
				String wjCode = (String) wjszAction.findDzscImgWjCode(
						new Request(CommonVars.getCurrUser()), wjEmsNo,
						wjSeqNum);
				Integer credenceNo = (wjCode == null || ""
						.equals(wjCode.trim())) ? null : Integer.valueOf(wjCode
						.trim());
				com.bestway.jptds.custombase.entity.Unit unit = imgBill
						.getUnit() == null ? null
						: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", imgBill.getUnit().getCode());
				com.bestway.jptds.custombase.entity.Country country = imgBill
						.getCountry() == null ? null
						: (com.bestway.jptds.custombase.entity.Country) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Country.class,
								"code", imgBill.getCountry().getCode());
				if (!isNewContractImg) {
					if (!checkObjectIsEquals(wjContractImg.getVoucherCode(),
							credenceNo)
							|| !checkObjectIsEquals(wjContractImg
									.getComplexCode(), imgBill.getComplex()
									.getCode())
							|| !checkObjectIsEquals(wjContractImg.getName(),
									imgBill.getName())
							|| !checkObjectIsEquals(wjContractImg.getSepce(),
									imgBill.getSpec())
							|| !checkObjectIsEquals(wjContractImg.getAmount(),
									imgBill.getAmount())
							|| !checkObjectIsEquals(wjContractImg
									.getTotalMoney(), imgBill.getMoney())
							|| !checkObjectIsEquals(wjContractImg
									.getUnitPrice(), imgBill.getPrice())
							|| !checkObjectIsEquals(wjContractImg.getUnit(),
									unit)
							|| !checkObjectIsEquals(wjContractImg.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(wjContractImg.getModifyMark())) {
							wjContractImg
									.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
						}
					}
				}
				if (credenceNo == null) {
					infoStr += "料件：" + imgBill.getSeqNum() + " 没有找到对应的凭证序号。\n";
					continue;
				}
				wjContractImg.setVoucherCode(credenceNo);
				wjContractImg.setComplexCode(imgBill.getComplex().getCode());
				wjContractImg.setName(imgBill.getName());
				wjContractImg.setSepce(imgBill.getSpec());
				wjContractImg.setAmount(imgBill.getAmount());
				wjContractImg.setUnit(unit);
				wjContractImg.setCountry(country);
				wjContractImg.setUnitPrice(imgBill.getPrice());
				wjContractImg.setTotalMoney(imgBill.getMoney());
				wjContractImg = (WJContractImg) wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjContractImg);
				mapWJContractImg.put(imgBill.getSeqNum(), wjContractImg);
				if (isNewContractImg) {
					insertNumLj++;
				} else {
					updateNumLj++;
				}
			}
		}
		List wjExgList = wjContractAction.findWJContractExg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjExgList.size(); i++) {
			WJContractExg wjContractExg = (WJContractExg) wjExgList.get(i);
			mapWJContractExg.put(wjContractExg.getSeqNum(), wjContractExg);
		}
		List exgList = (List) wjszAction.findDzscExg(new Request(CommonVars
				.getCurrUser()), head);
		for (int i = 0; i < exgList.size(); i++) {
			boolean isNewContractExg = false;
			DzscEmsExgBill exgBill = (DzscEmsExgBill) exgList.get(i);
			WJContractExg wjContractExg = mapWJContractExg.get(exgBill
					.getSeqNum());
			if (wjContractExg == null) {
				wjContractExg = new WJContractExg();
				wjContractExg.setSeqNum(exgBill.getSeqNum());
				wjContractExg.setWjContract(wjc);
				wjContractExg
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				isNewContractExg = true;
			}
			/**
			 * 当是新增的成品或覆盖旧的成品时保存
			 */
			if (isNewContractExg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String wjEmsNo = exgBill.getDzscEmsPorHead().getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer wjSeqNum = exgBill.getWjSeqNum();
				// 抓取合同备案中的外经编号==外经系统中的凭证序号。
				String wjCode = (String) wjszAction.findDzscExgWjCode(
						new Request(CommonVars.getCurrUser()), wjEmsNo,
						wjSeqNum);
				Integer credenceNo = (wjCode == null || ""
						.equals(wjCode.trim())) ? null : Integer.valueOf(wjCode
						.trim());
				com.bestway.jptds.custombase.entity.Unit unit = exgBill
						.getUnit() == null ? null
						: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", exgBill.getUnit().getCode());
				com.bestway.jptds.custombase.entity.Country country = exgBill
						.getCountry() == null ? null
						: (com.bestway.jptds.custombase.entity.Country) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Country.class,
								"code", exgBill.getCountry().getCode());
				if (!isNewContractExg) {
					if (!checkObjectIsEquals(wjContractExg.getVoucherCode(),
							credenceNo)
							|| !checkObjectIsEquals(wjContractExg
									.getComplexCode(), exgBill.getComplex()
									.getCode())
							|| !checkObjectIsEquals(wjContractExg.getName(),
									exgBill.getName())
							|| !checkObjectIsEquals(wjContractExg.getSepce(),
									exgBill.getSpec())
							|| !checkObjectIsEquals(wjContractExg.getAmount(),
									exgBill.getAmount())
							|| !checkObjectIsEquals(wjContractExg
									.getTotalMoney(), exgBill.getMoney())
							|| !checkObjectIsEquals(wjContractExg
									.getUnitPrice(), exgBill.getPrice())
							|| !checkObjectIsEquals(wjContractExg.getUnit(),
									unit)
							|| !checkObjectIsEquals(wjContractExg.getCountry(),
									country)
							|| !checkObjectIsEquals(wjContractExg
									.getProcessingPrice(), exgBill
									.getMachinPrice())
							|| !checkObjectIsEquals(wjContractExg
									.getProcessingMoney(), exgBill
									.getMachinMoney())) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(wjContractExg.getModifyMark())) {
							wjContractExg
									.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
						}
					}
				}
				if (credenceNo == null) {
					infoStr += "成品：" + exgBill.getSeqNum() + " 没有找到对应的凭证序号。\n";
					continue;
				}
				wjContractExg.setVoucherCode(credenceNo);
				wjContractExg.setComplexCode(exgBill.getComplex().getCode());
				wjContractExg.setName(exgBill.getName());
				wjContractExg.setSepce(exgBill.getSpec());
				wjContractExg.setAmount(exgBill.getAmount());
				wjContractExg.setUnit(unit);
				wjContractExg.setCountry(country);
				wjContractExg.setUnitPrice(exgBill.getPrice());
				wjContractExg.setTotalMoney(exgBill.getMoney());
				wjContractExg.setProcessingPrice(exgBill.getMachinPrice());
				wjContractExg.setProcessingMoney(exgBill.getMachinMoney());
				wjContractExg = (WJContractExg) wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjContractExg);
				mapWJContractExg.put(exgBill.getSeqNum(), wjContractExg);
				if (isNewContractExg) {
					insertNumCp++;
				} else {
					updateNumCp++;
				}
			}
		}
		List wjBomList = wjContractAction.findWJContractBom(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjBomList.size(); i++) {
			WJContractBom wjContractBom = (WJContractBom) wjBomList.get(i);
			String key = wjContractBom.getWjContractExg().getSeqNum() + "/"
					+ wjContractBom.getSeqNum();
			mapWJContractBom.put(key, wjContractBom);
		}
		List bomList = (List) wjszAction.findEmsPorBomBillByHead(new Request(
				CommonVars.getCurrUser()), head);
		for (int i = 0; i < bomList.size(); i++) {
			boolean isNewContractBom = false;
			DzscEmsBomBill bomBill = (DzscEmsBomBill) bomList.get(i);
			String key = bomBill.getDzscEmsExgBill().getSeqNum() + "/"
					+ bomBill.getImgSeqNum();
			WJContractBom wjContractBom = mapWJContractBom.get(key);
			if (wjContractBom == null) {
				wjContractBom = new WJContractBom();
				WJContractExg wjContractExg = mapWJContractExg.get(bomBill
						.getDzscEmsExgBill().getSeqNum());
				if (wjContractExg == null) {
					continue;
				}
				wjContractBom.setWjContractExg(wjContractExg);
				wjContractBom.setSeqNum(bomBill.getImgSeqNum());
				wjContractBom
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				isNewContractBom = true;
			}
			/**
			 * 当是新增的单耗或覆盖旧的单耗时保存
			 */
			if (isNewContractBom || isConver) {
				com.bestway.jptds.custombase.entity.Unit unit = bomBill
						.getUnit() == null ? null
						: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", bomBill.getUnit().getCode());
				com.bestway.jptds.custombase.entity.Country country = bomBill
						.getCountry() == null ? null
						: (com.bestway.jptds.custombase.entity.Country) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Country.class,
								"code", bomBill.getCountry().getCode());
				if (!isNewContractBom) {
					if (!checkObjectIsEquals(wjContractBom.getComplexCode(),
							bomBill.getComplex().getCode())
							|| !checkObjectIsEquals(wjContractBom.getName(),
									bomBill.getName())
							|| !checkObjectIsEquals(wjContractBom.getSepce(),
									bomBill.getSpec())
							|| !checkObjectIsEquals(wjContractBom
									.getUnitWaste(), bomBill.getUnitWare())
							|| !checkObjectIsEquals(wjContractBom.getWaste(),
									bomBill.getWare())
							|| !checkObjectIsEquals(wjContractBom
									.getUnitPrice(), bomBill.getPrice())
							|| !checkObjectIsEquals(wjContractBom.getUnit(),
									unit)
							|| !checkObjectIsEquals(wjContractBom.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(wjContractBom.getModifyMark())) {
							wjContractBom
									.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
						}
					}
				}
				wjContractBom.setComplexCode(bomBill.getComplex().getCode());
				wjContractBom.setName(bomBill.getName());
				wjContractBom.setSepce(bomBill.getSpec());
				wjContractBom.setUnitWaste(bomBill.getUnitWare());
				wjContractBom.setWaste(bomBill.getWare());
				wjContractBom.setUnitUsed(bomBill.getUnitDosage());
				wjContractBom.setUnit(unit);
				wjContractBom.setCountry(country);
				wjContractBom.setUnitPrice(bomBill.getPrice());
				WJContractImg wjContractImg = mapWJContractImg.get(bomBill
						.getImgSeqNum());
				if (wjContractImg != null) {
					wjContractBom
							.setVoucherCode(wjContractImg.getVoucherCode());
				}
				wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjContractBom);
				if (isNewContractBom) {
					insertNumBom++;
				} else {
					updateNumBom++;
				}
			}
		}

		infoStr += "料件 - (不存在)插入数据：" + String.valueOf(insertNumLj) + " 笔；\n";
		infoStr += "料件 - (存在且未审核)更新数据：" + String.valueOf(updateNumLj)
				+ " 笔；\n\n";

		infoStr += "成品 - (不存在)插入数据：" + String.valueOf(insertNumCp) + " 笔；\n";
		infoStr += "成品 - (存在且未审核)更新数据：" + String.valueOf(updateNumCp)
				+ " 笔；\n\n";

		infoStr += "单耗 - (不存在)插入数据：" + String.valueOf(insertNumBom) + " 笔；\n";
		infoStr += "单耗 - (存在且未审核)更新数据：" + String.valueOf(updateNumBom) + " 笔；";

		return "数据上传成功！\n\n" + infoStr;
	}

	/**
	 * 电子化手册上传资料
	 * 
	 */
	public static String uploadDataContract(boolean isConver, Contract head) {
		List rlist = wjContractAction
				.checkCanAddContract(com.bestway.jptds.client.common.CommonVars
						.getRequest());
		Boolean bl = (Boolean) rlist.get(0);
		Integer canAdd = (Integer) rlist.get(1);
		Integer added = (Integer) rlist.get(2);
		String infoStr = "";
		if (!bl) {
			infoStr = "已存在新合同数(" + added + ")不能大于可签合同份数(" + canAdd + ")";
			return infoStr;
		}
		List saveLs = new ArrayList();
		int insertNumLj = 0;
		int updateNumLj = 0;
		int insertNumCp = 0;
		int updateNumCp = 0;
		int insertNumBom = 0;
		int updateNumBom = 0;
		Map<Integer, WJContractImg> mapWJContractImg = new HashMap<Integer, WJContractImg>();
		Map<Integer, WJContractExg> mapWJContractExg = new HashMap<Integer, WJContractExg>();
		Map<String, WJContractBom> mapWJContractBom = new HashMap<String, WJContractBom>();
		boolean isNewContract = false;
		com.bestway.jptds.system.entity.Company wjCompany = com.bestway.jptds.client.common.CommonVars
				.getCompany();
		WJContract wjc = null;
		if (head.getWjComputerNo() == null
				|| "".equals(head.getWjComputerNo().trim())) {// 全套新增
			Object obj = createWJContract();
			if (obj instanceof WJContract) {
				wjc = (WJContract) obj;
			} else {
				throw new RuntimeException(obj.toString());
			}

			wjc = (WJContract) wjContractAction.saveOrUpdateObject(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					wjc);
			isNewContract = true;
		} else {
			List listHead = wjContractAction.findWJContractByApplyNo(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					head.getWjComputerNo().trim());
			if (listHead.size() > 0 && listHead.get(0) != null) {
				wjc = (WJContract) listHead.get(0);
				if (com.bestway.jptds.common.DeclareState.DECLARE_WAIT
						.equals(wjc.getApproveState())) {
					infoStr = "外经申请文号" + head.getWjComputerNo().trim()
							+ "的合同 等待审批状态，所以不能上传资料";
					return infoStr;
				} else if (com.bestway.jptds.common.DeclareState.DECLARE_PASS
						.equals(wjc.getApproveState())) {
					infoStr = "外经申请文号" + head.getWjComputerNo().trim()
							+ "的合同 正在执行状态，所以不能上传资料";
					return infoStr;
				}
			} else {
				Object obj = createWJContract();
				if (obj instanceof WJContract) {
					wjc = (WJContract) obj;
				} else {
					throw new RuntimeException(obj.toString());
				}
				wjc = (WJContract) wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjc);
				isNewContract = true;
			}
		}
		if (isNewContract || isConver) {
			String portCode = "";
			String portName = "";
			if (head.getIePort1() != null) {
				portCode = head.getIePort1().getCode();
				portName = head.getIePort1().getName();
			}
			if (head.getIePort2() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort2().getCode();
					portName += "/" + head.getIePort2().getName();
				} else {
					portCode = head.getIePort2().getCode();
					portName = head.getIePort2().getName();
				}
			}

			if (head.getIePort3() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort3().getCode();
					portName += "/" + head.getIePort3().getName();
				} else {
					portCode = head.getIePort3().getCode();
					portName = head.getIePort3().getName();
				}
			}
			if (head.getIePort4() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort4().getCode();
					portName += "/" + head.getIePort4().getName();
				} else {
					portCode = head.getIePort4().getCode();
					portName = head.getIePort4().getName();
				}
			}
			if (head.getIePort5() != null) {
				if (!portCode.equals("")) {
					portCode += "/" + head.getIePort5().getCode();
					portName += "/" + head.getIePort5().getName();
				} else {
					portCode = head.getIePort5().getCode();
					portName = head.getIePort5().getName();
				}
			}
			wjc.setInPortCode(portCode);
			wjc.setInPortName(portName);
			wjc.setOutPortCode(portCode);
			wjc.setOutPortName(portName);

			wjc.setApplyDate(head.getBeginDate());
			wjc.setEffectiveDate(head.getEndDate());
			wjc.setImporTotalMoney(head.getImgAmount());
			wjc.setExporTotalMoney(head.getExgAmount());
			if (head.getTrade() != null) {
				if (head.getTrade().getName().indexOf("来料") >= 0) {
					wjc
							.setTrade((com.bestway.jptds.custombase.entity.Trade) findCustomsBaseEntityFromJPtds(
									com.bestway.jptds.custombase.entity.Trade.class,
									"code", "L"));
					wjc.setWsContractNo(head.getImpContractNo());
				} else if (head.getTrade().getName().indexOf("进料") >= 0) {
					wjc
							.setTrade((com.bestway.jptds.custombase.entity.Trade) findCustomsBaseEntityFromJPtds(
									com.bestway.jptds.custombase.entity.Trade.class,
									"code", "J"));
					wjc.setImportContractNo(head.getImpContractNo());
					wjc.setExportContractNo(head.getExpContractNo());
				}
			}
			com.bestway.jptds.custombase.entity.Curr curr = head.getCurr() == null ? null
					: (com.bestway.jptds.custombase.entity.Curr) findCustomsBaseEntityFromJPtds(
							com.bestway.jptds.custombase.entity.Curr.class,
							"code", head.getCurr().getCode());
			if (curr != null) {
				wjc.setCurr(curr);
				Double rate = wjContractAction
						.findCurrRateOfDollar(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), curr);
				if (rate == null) {
					rate = 0.0;
				}
				wjc.setDollarRate(rate);
				double importTotal = wjc.getImporTotalMoney() == null ? 0.0
						: wjc.getImporTotalMoney();
				double exportTotal = wjc.getExporTotalMoney() == null ? 0.0
						: wjc.getExporTotalMoney();
				wjc.setImporTotalMoneyDollar(importTotal * rate);
				wjc.setExporTotalMoneyDollar(exportTotal * rate);
			}
			if (!isNewContract
					&& !com.bestway.jptds.common.ModifyMarkState.ADDED
							.equals(wjc.getModifyMarkState())) {
				wjc
						.setModifyMarkState(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
			}
			wjc = (WJContract) wjContractAction.saveOrUpdateObject(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					wjc);
		} else {
			return "";
		}
		head.setWjComputerNo(wjc.getApplyNo());
		saveLs.add(head);
		wjszAction.saveListObj(new Request(CommonVars.getCurrUser()), saveLs);
		List wjImgList = wjContractAction.findWJContractImg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjImgList.size(); i++) {
			WJContractImg wjContractImg = (WJContractImg) wjImgList.get(i);
			mapWJContractImg.put(wjContractImg.getSeqNum(), wjContractImg);
		}
		List imgList = (List) wjszAction.findContractImgByContract(new Request(
				CommonVars.getCurrUser()), head);
		for (int i = 0; i < imgList.size(); i++) {
			boolean isNewContractImg = false;
			ContractImg contractImg = (ContractImg) imgList.get(i);
			WJContractImg wjContractImg = mapWJContractImg.get(contractImg
					.getSeqNum());
			if (wjContractImg == null) {
				wjContractImg = new WJContractImg();
				wjContractImg.setSeqNum(contractImg.getSeqNum());
				wjContractImg.setWjContract(wjc);
				wjContractImg
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				isNewContractImg = true;
			}
			/**
			 * 当是新增的料件或覆盖旧的料件时保存
			 */
			if (isNewContractImg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String wjEmsNo = contractImg.getContract().getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer wjSeqNum = contractImg.getCredenceNo();
				// 抓取合同备案中的外经编号==外经系统中的凭证序号。
				String wjCode = (String) wjszAction.findBcsImgWjCode(
						new Request(CommonVars.getCurrUser()), wjEmsNo,
						wjSeqNum);
				// System.out.println("------------wjcode:"+wjCode+"  "+wjEmsNo+"  "+wjSeqNum);
				Integer credenceNo = (wjCode == null || ""
						.equals(wjCode.trim())) ? null : Integer.valueOf(wjCode
						.trim());
				com.bestway.jptds.custombase.entity.Unit unit = contractImg
						.getUnit() == null ? null
						: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", contractImg.getUnit().getCode());
				com.bestway.jptds.custombase.entity.Country country = contractImg
						.getCountry() == null ? null
						: (com.bestway.jptds.custombase.entity.Country) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Country.class,
								"code", contractImg.getCountry().getCode());
				if (!isNewContractImg) {
					if (!checkObjectIsEquals(wjContractImg.getVoucherCode(),
							credenceNo)
							|| !checkObjectIsEquals(wjContractImg
									.getComplexCode(), contractImg.getComplex()
									.getCode())
							|| !checkObjectIsEquals(wjContractImg.getName(),
									contractImg.getName())
							|| !checkObjectIsEquals(wjContractImg.getSepce(),
									contractImg.getSpec())
							|| !checkObjectIsEquals(wjContractImg.getAmount(),
									contractImg.getAmount())
							|| !checkObjectIsEquals(wjContractImg
									.getTotalMoney(), contractImg
									.getTotalPrice())
							|| !checkObjectIsEquals(wjContractImg
									.getUnitPrice(), contractImg
									.getDeclarePrice())
							|| !checkObjectIsEquals(wjContractImg.getUnit(),
									unit)
							|| !checkObjectIsEquals(wjContractImg.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(wjContractImg.getModifyMark())) {
							wjContractImg
									.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
						}
					}
				}
				if (credenceNo == null) {
					infoStr += "料件：" + contractImg.getSeqNum()
							+ " 没有找到对应的凭证序号。\n";
					continue;
				}
				wjContractImg.setVoucherCode(credenceNo);
				wjContractImg
						.setComplexCode(contractImg.getComplex().getCode());
				wjContractImg.setName(contractImg.getName());
				wjContractImg.setSepce(contractImg.getSpec());
				wjContractImg.setAmount(contractImg.getAmount());
				wjContractImg.setUnit(unit);
				wjContractImg.setCountry(country);
				wjContractImg.setUnitPrice(contractImg.getDeclarePrice());
				wjContractImg.setTotalMoney(contractImg.getTotalPrice());
				wjContractImg = (WJContractImg) wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjContractImg);
				mapWJContractImg.put(contractImg.getSeqNum(), wjContractImg);
				if (isNewContractImg) {
					insertNumLj++;
				} else {
					updateNumLj++;
				}
			}
		}
		List wjExgList = wjContractAction.findWJContractExg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjExgList.size(); i++) {
			WJContractExg wjContractExg = (WJContractExg) wjExgList.get(i);
			mapWJContractExg.put(wjContractExg.getSeqNum(), wjContractExg);
		}
		List exgList = (List) wjszAction.findContractExgByContract(new Request(
				CommonVars.getCurrUser()), head);
		for (int i = 0; i < exgList.size(); i++) {
			boolean isNewContractExg = false;
			ContractExg contractExg = (ContractExg) exgList.get(i);
			WJContractExg wjContractExg = mapWJContractExg.get(contractExg
					.getSeqNum());
			if (wjContractExg == null) {
				wjContractExg = new WJContractExg();
				wjContractExg.setSeqNum(contractExg.getSeqNum());
				wjContractExg.setWjContract(wjc);
				wjContractExg
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);

				isNewContractExg = true;
			}
			/**
			 * 当是新增的成品或覆盖旧的成品时保存
			 */
			if (isNewContractExg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String wjEmsNo = contractExg.getContract().getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer wjSeqNum = contractExg.getCredenceNo();
				// 抓取合同备案中的外经编号==外经系统中的凭证序号。
				String wjCode = (String) wjszAction.findBcsExgWjCode(
						new Request(CommonVars.getCurrUser()), wjEmsNo,
						wjSeqNum);
				Integer credenceNo = (wjCode == null || ""
						.equals(wjCode)) ? null : Integer.valueOf(wjCode
						.trim());
				com.bestway.jptds.custombase.entity.Unit unit = contractExg
						.getUnit() == null ? null
						: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", contractExg.getUnit().getCode());
				com.bestway.jptds.custombase.entity.Country country = contractExg
						.getCountry() == null ? null
						: (com.bestway.jptds.custombase.entity.Country) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Country.class,
								"code", contractExg.getCountry().getCode());
				if (!isNewContractExg) {
					if (!checkObjectIsEquals(wjContractExg.getVoucherCode(),
							credenceNo)
							|| !checkObjectIsEquals(wjContractExg
									.getComplexCode(), contractExg.getComplex()
									.getCode())
							|| !checkObjectIsEquals(wjContractExg.getName(),
									contractExg.getName())
							|| !checkObjectIsEquals(wjContractExg.getSepce(),
									contractExg.getSpec())
							|| !checkObjectIsEquals(wjContractExg.getAmount(),
									contractExg.getExportAmount())
							|| !checkObjectIsEquals(wjContractExg
									.getTotalMoney(), contractExg
									.getTotalPrice())
							|| !checkObjectIsEquals(wjContractExg
									.getUnitPrice(), contractExg.getUnitPrice())
							|| !checkObjectIsEquals(wjContractExg.getUnit(),
									unit)
							|| !checkObjectIsEquals(wjContractExg.getCountry(),
									country)
							|| !checkObjectIsEquals(wjContractExg
									.getProcessingPrice(), contractExg
									.getProcessUnitPrice())
							|| !checkObjectIsEquals(wjContractExg
									.getProcessingMoney(), contractExg
									.getProcessTotalPrice())) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(wjContractExg.getModifyMark())) {
							wjContractExg
									.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
						}
					}
				}
				if (credenceNo == null) {
					infoStr += "成品：" + contractExg.getSeqNum()
							+ " 没有找到对应的凭证序号。\n";
					continue;
				}
				wjContractExg.setVoucherCode(credenceNo);
				wjContractExg
						.setComplexCode(contractExg.getComplex().getCode());
				wjContractExg.setName(contractExg.getName());
				wjContractExg.setSepce(contractExg.getSpec());
				wjContractExg.setAmount(contractExg.getExportAmount());
				wjContractExg.setUnit(unit);
				wjContractExg.setCountry(country);
				wjContractExg.setUnitPrice(contractExg.getUnitPrice());
				wjContractExg.setTotalMoney(contractExg.getTotalPrice());
				wjContractExg.setProcessingPrice(contractExg
						.getProcessUnitPrice());
				wjContractExg.setProcessingMoney(contractExg
						.getProcessTotalPrice());
				wjContractExg = (WJContractExg) wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjContractExg);
				mapWJContractExg.put(contractExg.getSeqNum(), wjContractExg);
				if (isNewContractExg) {
					insertNumCp++;
				} else {
					updateNumCp++;
				}
			}
		}
		List wjBomList = wjContractAction.findWJContractBom(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjBomList.size(); i++) {
			WJContractBom wjContractBom = (WJContractBom) wjBomList.get(i);
			String key = wjContractBom.getWjContractExg().getSeqNum() + "/"
					+ wjContractBom.getSeqNum();
			mapWJContractBom.put(key, wjContractBom);
		}
		List bomList = (List) wjszAction.findContractBomByContract(new Request(
				CommonVars.getCurrUser()), head);
		for (int i = 0; i < bomList.size(); i++) {
			boolean isNewContractBom = false;
			ContractBom contractBom = (ContractBom) bomList.get(i);
			String key = contractBom.getContractExg().getSeqNum() + "/"
					+ contractBom.getContractImgSeqNum();
			WJContractBom wjContractBom = mapWJContractBom.get(key);
			if (wjContractBom == null) {
				wjContractBom = new WJContractBom();
				wjContractBom.setSeqNum(contractBom.getContractImgSeqNum());
				WJContractExg wjContractExg = mapWJContractExg.get(contractBom
						.getContractExg().getSeqNum());
				if (wjContractExg == null) {
					continue;
				}
				wjContractBom.setWjContractExg(wjContractExg);
				wjContractBom
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				isNewContractBom = true;
			}
			/**
			 * 当是新增的单耗或覆盖旧的单耗时保存
			 */
			if (isNewContractBom || isConver) {
				com.bestway.jptds.custombase.entity.Unit unit = contractBom
						.getUnit() == null ? null
						: (com.bestway.jptds.custombase.entity.Unit) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Unit.class,
								"code", contractBom.getUnit().getCode());
				com.bestway.jptds.custombase.entity.Country country = contractBom
						.getCountry() == null ? null
						: (com.bestway.jptds.custombase.entity.Country) findCustomsBaseEntityFromJPtds(
								com.bestway.jptds.custombase.entity.Country.class,
								"code", contractBom.getCountry().getCode());
				if (!isNewContractBom) {
					if (!checkObjectIsEquals(wjContractBom.getComplexCode(),
							contractBom.getComplex().getCode())
							|| !checkObjectIsEquals(wjContractBom.getName(),
									contractBom.getName())
							|| !checkObjectIsEquals(wjContractBom.getSepce(),
									contractBom.getSpec())
							|| !checkObjectIsEquals(wjContractBom
									.getUnitWaste(), contractBom.getUnitWaste())
							|| !checkObjectIsEquals(wjContractBom.getWaste(),
									contractBom.getWaste())
							|| !checkObjectIsEquals(wjContractBom
									.getUnitPrice(), contractBom
									.getDeclarePrice())
							|| !checkObjectIsEquals(wjContractBom.getUnit(),
									unit)
							|| !checkObjectIsEquals(wjContractBom.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(wjContractBom.getModifyMark())) {
							wjContractBom
									.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
						}
					}
				}
				wjContractBom
						.setComplexCode(contractBom.getComplex().getCode());
				wjContractBom.setName(contractBom.getName());
				wjContractBom.setSepce(contractBom.getSpec());
				wjContractBom.setUnitWaste(contractBom.getUnitWaste());
				wjContractBom.setWaste(contractBom.getWaste());
				wjContractBom.setUnitUsed(contractBom.getUnitDosage());
				wjContractBom.setUnit(unit);
				wjContractBom.setCountry(country);
				wjContractBom.setUnitPrice(contractBom.getDeclarePrice());
				WJContractImg wjContractImg = mapWJContractImg.get(contractBom
						.getContractImgSeqNum());
				if (wjContractImg != null) {
					wjContractBom
							.setVoucherCode(wjContractImg.getVoucherCode());
				}
				wjContractAction
						.saveOrUpdateObject(
								com.bestway.jptds.client.common.CommonVars
										.getRequest(), wjContractBom);
				if (isNewContractBom) {
					insertNumBom++;
				} else {
					updateNumBom++;
				}
			}
		}

		infoStr += "料件 - (不存在)插入数据：" + String.valueOf(insertNumLj) + " 笔；\n";
		infoStr += "料件 - (存在且未审核)更新数据：" + String.valueOf(updateNumLj)
				+ " 笔；\n\n";

		infoStr += "成品 - (不存在)插入数据：" + String.valueOf(insertNumCp) + " 笔；\n";
		infoStr += "成品 - (存在且未审核)更新数据：" + String.valueOf(updateNumCp)
				+ " 笔；\n\n";

		infoStr += "单耗 - (不存在)插入数据：" + String.valueOf(insertNumBom) + " 笔；\n";
		infoStr += "单耗 - (存在且未审核)更新数据：" + String.valueOf(updateNumBom) + " 笔；";

		return "数据上传成功！\n\n" + infoStr;
	}
}
