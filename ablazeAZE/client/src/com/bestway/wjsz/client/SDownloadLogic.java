package com.bestway.wjsz.client;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.jptds.contract.entity.WJContract;
import com.bestway.jptds.contract.entity.WJContractBom;
import com.bestway.jptds.contract.entity.WJContractExg;
import com.bestway.jptds.contract.entity.WJContractImg;
import com.bestway.jptds.credence.entity.ExgCredence;
import com.bestway.jptds.credence.entity.ImgCredence;
import com.bestway.waijing.action.WjszAction;

public class SDownloadLogic {

	private static WjszAction wjszAction = (WjszAction) com.bestway.bcus.client.common.CommonVars
			.getApplicationContext().getBean("wjszAction");
	private static com.bestway.jptds.contract.action.ContractAction wjContractAction = (com.bestway.jptds.contract.action.ContractAction) com.bestway.jptds.client.common.CommonVars
			.getApplicationContext().getBean("contractAction");

	/**
	 * 根据类名和编码查询海关基础资料实体
	 * 
	 * @param clsName
	 * @param codeField
	 * @param codeValue
	 * @return
	 */
	public static Object findCustomsBaseEntityFromJBCUS(Class<?> clsName,
			String codeField, String codeValue) {
		return wjszAction.findCustomsBaseEntity(new com.bestway.common.Request(
				com.bestway.bcus.client.common.CommonVars.getCurrUser()),
				clsName, codeField, codeValue);
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
	 * 凭证下载到备案资料库
	 * 
	 */
	public static String downloadBcsDictData(boolean isConver,
			BcsDictPorHead bcsDictPorHead, List listImgCredence,
			List listExgCredence) {
		Hashtable<Integer, BcsDictPorImg> imgHs = new Hashtable<Integer, BcsDictPorImg>();
		Hashtable<Integer, BcsDictPorExg> exgHs = new Hashtable<Integer, BcsDictPorExg>();
		int insertImgNum = 0;
		int updateImgNum = 0;
		int noUpdateImgNum = 0;
		int insertExgNum = 0;
		int updateExgNum = 0;
		int noUpdateExgNum = 0;
		String infoStr = "";
		if (DeclareState.WAIT_EAA.equals(bcsDictPorHead.getDeclareState())) {
			infoStr = "该备案资料库的正在等待审批，所以不能下载资料";
			return infoStr;
		}
		if (DeclareState.PROCESS_EXE.equals(bcsDictPorHead.getDeclareState())) {
			infoStr = "该备案资料库的正在执行，所以不能下载资料";
			return infoStr;
		}
		List listDictImg = (List) wjszAction
				.findBcsDictPorImg(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), bcsDictPorHead);
		for (int i = 0; i < listDictImg.size(); i++) {
			BcsDictPorImg bcsDictPorImg = (BcsDictPorImg) listDictImg.get(i);
			if (bcsDictPorImg.getWjCode() != null
					&& !"".equals(bcsDictPorImg.getWjCode().trim())) {
				imgHs.put(Integer.valueOf(bcsDictPorImg.getWjCode().trim()),
						bcsDictPorImg);
			}
		}
		// List listImgCredence = wjCredenceAction.findImgCredence(
		// com.bestway.jptds.client.common.CommonVars.getRequest(), "",
		// "", "");
		int maxImgSeqNum = (Integer) wjszAction
				.getMaxDictPorImgSeqNum(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), bcsDictPorHead);
		for (int i = 0; i < listImgCredence.size(); i++) {
			ImgCredence imgCredence = (ImgCredence) listImgCredence.get(i);
			BcsDictPorImg bcsDictPorImg = imgHs
					.get(imgCredence.getCredenceNo());
			com.bestway.bcus.custombase.entity.hscode.Complex complex = imgCredence
					.getComplexCode() == null ? null
					: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.hscode.Complex.class,
							"code", imgCredence.getComplexCode().trim());
			com.bestway.bcus.custombase.entity.parametercode.Unit unit = imgCredence
					.getUnit() == null ? null
					: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Unit.class,
							"code", imgCredence.getUnit().getCode());
			com.bestway.bcus.custombase.entity.parametercode.Curr curr = imgCredence
					.getCurr() == null ? null
					: (com.bestway.bcus.custombase.entity.parametercode.Curr) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Curr.class,
							"code", imgCredence.getCurr().getCode());
			if (bcsDictPorImg == null) {
				bcsDictPorImg = new BcsDictPorImg();
				bcsDictPorImg.setDictPorHead(bcsDictPorHead);
				maxImgSeqNum++;
				bcsDictPorImg.setSeqNum(maxImgSeqNum);
				bcsDictPorImg
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				insertImgNum++;
			} else {
				if (!isConver) {
					noUpdateImgNum++;
					continue;
				}
				if (checkObjectIsEquals(bcsDictPorImg.getComplex(), complex)
						&& checkObjectIsEquals(bcsDictPorImg.getCommName(),
								imgCredence.getName())
						&& checkObjectIsEquals(bcsDictPorImg.getCommSpec(),
								imgCredence.getSpec())
						&& checkObjectIsEquals(bcsDictPorImg.getUnitPrice(),
								imgCredence.getUnitPrice())
						&& checkObjectIsEquals(bcsDictPorImg.getComUnit(), unit)
						&& checkObjectIsEquals(bcsDictPorImg.getCurr(), curr)) {
					noUpdateImgNum++;
					continue;
				}
				if (!com.bestway.jptds.common.ModifyMarkState.ADDED
						.equals(bcsDictPorImg.getModifyMark())) {
					bcsDictPorImg
							.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
				}
				updateImgNum++;
			}
			bcsDictPorImg
					.setWjCode(String.valueOf(imgCredence.getCredenceNo()));
			bcsDictPorImg.setComplex(complex);
			bcsDictPorImg.setCommName(imgCredence.getName());
			bcsDictPorImg.setCommSpec(imgCredence.getSpec());
			bcsDictPorImg.setCurr(curr);
			bcsDictPorImg.setComUnit(unit);
			bcsDictPorImg.setUnitPrice(imgCredence.getUnitPrice());
			wjszAction.saveObj(new com.bestway.common.Request(
					com.bestway.bcus.client.common.CommonVars.getCurrUser()),
					bcsDictPorImg);
		}
		List listDictExg = (List) wjszAction
				.findBcsDictPorExg(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), bcsDictPorHead);
		for (int i = 0; i < listDictExg.size(); i++) {
			BcsDictPorExg bcsDictPorExg = (BcsDictPorExg) listDictExg.get(i);
			if (bcsDictPorExg.getWjCode() != null
					&& !"".equals(bcsDictPorExg.getWjCode().trim())) {
				exgHs.put(Integer.valueOf(bcsDictPorExg.getWjCode().trim()),
						bcsDictPorExg);
			}
		}
		// List listExgCredence = wjCredenceAction.findExgCredence(
		// com.bestway.jptds.client.common.CommonVars.getRequest(), "",
		// "", "");
		int maxExgSeqNum = (Integer) wjszAction
				.getMaxDictPorExgSeqNum(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), bcsDictPorHead);
		for (int i = 0; i < listExgCredence.size(); i++) {
			ExgCredence exgCredence = (ExgCredence) listExgCredence.get(i);
			BcsDictPorExg bcsDictPorExg = exgHs
					.get(exgCredence.getCredenceNo());
			com.bestway.bcus.custombase.entity.hscode.Complex complex = exgCredence
					.getComplexCode() == null ? null
					: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.hscode.Complex.class,
							"code", exgCredence.getComplexCode().trim());
			com.bestway.bcus.custombase.entity.parametercode.Unit unit = exgCredence
					.getUnit() == null ? null
					: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Unit.class,
							"code", exgCredence.getUnit().getCode());
			com.bestway.bcus.custombase.entity.parametercode.Curr curr = exgCredence
					.getCurr() == null ? null
					: (com.bestway.bcus.custombase.entity.parametercode.Curr) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Curr.class,
							"code", exgCredence.getCurr().getCode());

			if (bcsDictPorExg == null) {
				bcsDictPorExg = new BcsDictPorExg();
				bcsDictPorExg.setDictPorHead(bcsDictPorHead);
				maxExgSeqNum++;
				bcsDictPorExg.setSeqNum(maxExgSeqNum);
				bcsDictPorExg
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				insertExgNum++;
			} else {
				if (!isConver) {
					noUpdateExgNum++;
					continue;
				}
				if (checkObjectIsEquals(bcsDictPorExg.getComplex(), complex)
						&& checkObjectIsEquals(bcsDictPorExg.getCommName(),
								exgCredence.getName())
						&& checkObjectIsEquals(bcsDictPorExg.getCommSpec(),
								exgCredence.getSpec())
						&& checkObjectIsEquals(bcsDictPorExg.getUnitPrice(),
								exgCredence.getUnitPrice())
						&& checkObjectIsEquals(bcsDictPorExg.getComUnit(), unit)
						&& checkObjectIsEquals(bcsDictPorExg.getCurr(), curr)) {
					noUpdateExgNum++;
					continue;
				}
				if (!com.bestway.jptds.common.ModifyMarkState.ADDED
						.equals(bcsDictPorExg.getModifyMark())) {
					bcsDictPorExg
							.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
				}
				updateExgNum++;
			}
			bcsDictPorExg
					.setWjCode(String.valueOf(exgCredence.getCredenceNo()));
			bcsDictPorExg.setComplex(complex);
			bcsDictPorExg.setCommName(exgCredence.getName());
			bcsDictPorExg.setCommSpec(exgCredence.getSpec());
			bcsDictPorExg.setCurr(curr);
			bcsDictPorExg.setComUnit(unit);
			bcsDictPorExg.setUnitPrice(exgCredence.getUnitPrice());
			wjszAction.saveObj(new com.bestway.common.Request(
					com.bestway.bcus.client.common.CommonVars.getCurrUser()),
					bcsDictPorExg);
		}
		infoStr = "下载成功！\n(不存在)插入料件数据：" + String.valueOf(insertImgNum)
				+ " 笔；插入成品数据：" + String.valueOf(insertExgNum) + " 笔；\n";
		infoStr += "(存在且未发送)更新料件数据：" + String.valueOf(updateImgNum)
				+ " 笔；更新成品数据：" + String.valueOf(updateExgNum) + " 笔；\n";
		infoStr += "(存在且发送)未更新料件数据：" + String.valueOf(noUpdateImgNum)
				+ " 笔；未更新成品数据：" + String.valueOf(noUpdateExgNum) + " 笔";
		return "数据下载信息！\n\n" + infoStr;
	}

	/**
	 * 凭证下载到备案资料库
	 * 
	 */
	public static String downloadDzscEmsPorWjData(boolean isConver,
			DzscEmsPorWjHead dzscEmsPorWjHead, List listImgCredence,
			List listExgCredence) {
		Hashtable<Integer, DzscEmsImgWj> imgHs = new Hashtable<Integer, DzscEmsImgWj>();
		Hashtable<Integer, DzscEmsExgWj> exgHs = new Hashtable<Integer, DzscEmsExgWj>();
		int insertImgNum = 0;
		int updateImgNum = 0;
		int noUpdateImgNum = 0;
		int insertExgNum = 0;
		int updateExgNum = 0;
		int noUpdateExgNum = 0;
		String infoStr = "";
		if (DzscState.APPLY.equals(dzscEmsPorWjHead.getDeclareState())) {
			infoStr = "该合同备案的正在等待审批，所以不能下载资料";
			return infoStr;
		}
		if (DzscState.EXECUTE.equals(dzscEmsPorWjHead.getDeclareState())) {
			infoStr = "该合同备案的正在执行，所以不能下载资料";
			return infoStr;
		}
		List listEmsPorImgWj = (List) wjszAction
				.findDzscEmsImgWj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), dzscEmsPorWjHead);
		for (int i = 0; i < listEmsPorImgWj.size(); i++) {
			DzscEmsImgWj dzscEmsImgWj = (DzscEmsImgWj) listEmsPorImgWj.get(i);
			if (dzscEmsImgWj.getWjCode() != null
					&& !"".equals(dzscEmsImgWj.getWjCode().trim())) {
				imgHs.put(Integer.valueOf(dzscEmsImgWj.getWjCode().trim()),
						dzscEmsImgWj);
			}
		}
		// List listImgCredence = wjCredenceAction.findImgCredence(
		// com.bestway.jptds.client.common.CommonVars.getRequest(), "",
		// "", "");
		int maxImgSeqNum = (Integer) wjszAction
				.getMaxDzscEmsImgWjNum(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), dzscEmsPorWjHead);
		for (int i = 0; i < listImgCredence.size(); i++) {
			ImgCredence imgCredence = (ImgCredence) listImgCredence.get(i);
			DzscEmsImgWj dzscEmsImgWj = imgHs.get(imgCredence.getCredenceNo());

			com.bestway.bcus.custombase.entity.hscode.Complex complex = imgCredence
					.getComplexCode() == null ? null
					: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.hscode.Complex.class,
							"code", imgCredence.getComplexCode().trim());
			com.bestway.bcus.custombase.entity.parametercode.Unit unit = imgCredence
					.getUnit() == null ? null
					: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Unit.class,
							"code", imgCredence.getUnit().getCode());
			// com.bestway.bcus.custombase.entity.parametercode.Curr curr =
			// imgCredence
			// .getCurr() == null ? null
			// : (com.bestway.bcus.custombase.entity.parametercode.Curr)
			// findCustomsBaseEntityFromJBCUS(
			// com.bestway.bcus.custombase.entity.parametercode.Curr.class,
			// "code", imgCredence.getCurr().getCode());

			if (dzscEmsImgWj == null) {
				dzscEmsImgWj = new DzscEmsImgWj();
				dzscEmsImgWj.setDzscEmsPorWjHead(dzscEmsPorWjHead);
				maxImgSeqNum++;
				dzscEmsImgWj.setSeqNum(maxImgSeqNum);
				dzscEmsImgWj
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				insertImgNum++;
			} else {
				if (!isConver) {
					noUpdateImgNum++;
					continue;
				}
				if (checkObjectIsEquals(dzscEmsImgWj.getFourComplex(),
						imgCredence.getComplexCode())
						&& checkObjectIsEquals(dzscEmsImgWj.getFourName(),
								imgCredence.getName())
						&& checkObjectIsEquals(dzscEmsImgWj.getFourSpec(),
								imgCredence.getSpec())
						&& checkObjectIsEquals(dzscEmsImgWj.getFourUnit(), unit)) {
					noUpdateImgNum++;
					continue;
				}
				if (!com.bestway.jptds.common.ModifyMarkState.ADDED
						.equals(dzscEmsImgWj.getModifyMark())) {
					dzscEmsImgWj
							.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
				}
				updateImgNum++;
			}
			dzscEmsImgWj.setWjCode(String.valueOf(imgCredence.getCredenceNo()));
			dzscEmsImgWj.setFourComplex(imgCredence.getComplexCode());
			dzscEmsImgWj.setFourName(imgCredence.getName());
			dzscEmsImgWj.setFourSpec(imgCredence.getSpec());
			// dzscEmsImgWj.setCurr(curr);
			dzscEmsImgWj.setFourUnit(unit);
			dzscEmsImgWj.setFirstUnit(complex.getFirstUnit());
			dzscEmsImgWj.setSecondUnit(complex.getSecondUnit());
			// dzscEmsImgWj.setUnitPrice(imgCredence.getUnitPrice());
			wjszAction.saveObj(new com.bestway.common.Request(
					com.bestway.bcus.client.common.CommonVars.getCurrUser()),
					dzscEmsImgWj);
		}
		List listEmsPorExgWj = (List) wjszAction
				.findDzscEmsExgWj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), dzscEmsPorWjHead);
		for (int i = 0; i < listEmsPorExgWj.size(); i++) {
			DzscEmsExgWj dzscEmsExgWj = (DzscEmsExgWj) listEmsPorExgWj.get(i);
			if (dzscEmsExgWj.getWjCode() != null
					&& !"".equals(dzscEmsExgWj.getWjCode().trim())) {
				exgHs.put(Integer.valueOf(dzscEmsExgWj.getWjCode().trim()),
						dzscEmsExgWj);
			}
		}
		// List listExgCredence = wjCredenceAction.findExgCredence(
		// com.bestway.jptds.client.common.CommonVars.getRequest(), "",
		// "", "");
		int maxExgSeqNum = (Integer) wjszAction
				.getMaxDzscEmsExgWjNum(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), dzscEmsPorWjHead);
		for (int i = 0; i < listExgCredence.size(); i++) {
			ExgCredence exgCredence = (ExgCredence) listExgCredence.get(i);
			DzscEmsExgWj dzscEmsExgWj = exgHs.get(exgCredence.getCredenceNo());

			com.bestway.bcus.custombase.entity.hscode.Complex complex = exgCredence
					.getComplexCode() == null ? null
					: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.hscode.Complex.class,
							"code", exgCredence.getComplexCode().trim());
			com.bestway.bcus.custombase.entity.parametercode.Unit unit = exgCredence
					.getUnit() == null ? null
					: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Unit.class,
							"code", exgCredence.getUnit().getCode());
			// com.bestway.bcus.custombase.entity.parametercode.Curr curr =
			// exgCredence
			// .getCurr() == null ? null
			// : (com.bestway.bcus.custombase.entity.parametercode.Curr)
			// findCustomsBaseEntityFromJBCUS(
			// com.bestway.bcus.custombase.entity.parametercode.Curr.class,
			// "code", exgCredence.getCurr().getCode());

			if (dzscEmsExgWj == null) {
				dzscEmsExgWj = new DzscEmsExgWj();
				dzscEmsExgWj.setDzscEmsPorWjHead(dzscEmsPorWjHead);
				maxExgSeqNum++;
				dzscEmsExgWj.setSeqNum(maxExgSeqNum);
				dzscEmsExgWj
						.setModifyMark(com.bestway.jptds.common.ModifyMarkState.ADDED);
				insertExgNum++;
			} else {
				if (!isConver) {
					noUpdateExgNum++;
					continue;
				}
				if (checkObjectIsEquals(dzscEmsExgWj.getFourComplex(),
						exgCredence.getComplexCode())
						&& checkObjectIsEquals(dzscEmsExgWj.getFourName(),
								exgCredence.getName())
						&& checkObjectIsEquals(dzscEmsExgWj.getFourSpec(),
								exgCredence.getSpec())
						// && checkObjectIsEquals(dzscEmsExgWj.getUnitPrice(),
						// exgCredence.getUnitPrice())
						&& checkObjectIsEquals(dzscEmsExgWj.getFourUnit(), unit)
				// && checkObjectIsEquals(dzscEmsExgWj.getCurr(),
				// exgCredence.getCurr())
				) {
					noUpdateExgNum++;
					continue;
				}
				if (!com.bestway.jptds.common.ModifyMarkState.ADDED
						.equals(dzscEmsExgWj.getModifyMark())) {
					dzscEmsExgWj
							.setModifyMark(com.bestway.jptds.common.ModifyMarkState.MODIFIED);
				}
				updateExgNum++;
			}
			dzscEmsExgWj.setWjCode(String.valueOf(exgCredence.getCredenceNo()));
			dzscEmsExgWj.setFourComplex(exgCredence.getComplexCode());
			dzscEmsExgWj.setFourName(exgCredence.getName());
			dzscEmsExgWj.setFourSpec(exgCredence.getSpec());
			// dzscEmsExgWj.setCurr(curr);
			dzscEmsExgWj.setFourUnit(unit);
			dzscEmsExgWj.setFirstUnit(complex.getFirstUnit());
			dzscEmsExgWj.setSecondUnit(complex.getSecondUnit());
			// dzscEmsExgWj.setUnitPrice(exgCredence.getUnitPrice());
			wjszAction.saveObj(new com.bestway.common.Request(
					com.bestway.bcus.client.common.CommonVars.getCurrUser()),
					dzscEmsExgWj);
		}
		infoStr = "下载成功！\n(不存在)插入料件数据：" + String.valueOf(insertImgNum)
				+ " 笔；插入成品数据：" + String.valueOf(insertExgNum) + " 笔；\n";
		infoStr += "(存在且未发送)更新料件数据：" + String.valueOf(updateImgNum)
				+ " 笔；更新成品数据：" + String.valueOf(updateExgNum) + " 笔；\n";
		infoStr += "(存在且发送)未更新料件数据：" + String.valueOf(noUpdateImgNum)
				+ " 笔；未更新成品数据：" + String.valueOf(noUpdateExgNum) + " 笔";
		return "数据下载信息！\n\n" + infoStr;
	}

	/**
	 * 电子手册下载资料OK!
	 * 
	 */
	public static String downloadDataDzsc(boolean isConver, DzscEmsPorHead head) {
		int insertNumLj = 0;
		int updateNumLj = 0;
		int insertNumCp = 0;
		int updateNumCp = 0;
		int insertNumBom = 0;
		int updateNumBom = 0;
		Map<Integer, DzscEmsImgBill> mapImg = new HashMap<Integer, DzscEmsImgBill>();
		Map<Integer, DzscEmsExgBill> mapExg = new HashMap<Integer, DzscEmsExgBill>();
		Map<String, DzscEmsBomBill> mapBom = new HashMap<String, DzscEmsBomBill>();
		String tishi = "";
		if (DzscState.APPLY.equals(head.getDeclareState())) {
			tishi = "该手册的正在等待审批，所以不能下载资料";
			return tishi;
		}
		if (DzscState.EXECUTE.equals(head.getDeclareState())) {
			tishi = "该手册的正在执行，所以不能下载资料";
			return tishi;
		}
		if (head.getCorrEmsNo() == null
				|| "".equals(head.getCorrEmsNo().trim())) {
			tishi = "该手册" + head.getEmsNo() + "的合同备案编号为空，所以不能下载资料";
			return tishi;
		}
		WJContract wjc = null;
		if (head.getWjComputerNo() == null
				|| "".equals(head.getWjComputerNo().trim())) {// 全套新增
			tishi = "该手册的外经申请文号为空";
			return tishi;
		} else {
			List listHead = wjContractAction.findWJContractByApplyNo(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					head.getWjComputerNo().trim());
			if (listHead.size() > 0 && listHead.get(0) != null) {
				wjc = (WJContract) listHead.get(0);
			} else {
				tishi = "在外经系统中没有找到申请文号为" + head.getWjComputerNo().trim()
						+ "的合同";
				return tishi;
			}
		}
		if (isConver) {
			String portCode = wjc.getInPortCode();
			if (portCode != null && !"".equals(portCode.trim())) {
				String[] codes = portCode.split("/");
				head
						.setIePort1(codes.length < 1 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[0]));
				head
						.setIePort2(codes.length < 2 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[1]));
				head
						.setIePort3(codes.length < 3 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[2]));
				head
						.setIePort4(codes.length < 4 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[3]));
				head
						.setIePort5(codes.length < 5 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[4]));
				if (wjc.getTrade() != null) {
					if (wjc.getTrade().getName().indexOf("来料") >= 0) {
						head.setImContractNo(wjc.getWsContractNo());
						head.setIeContractNo(wjc.getWsContractNo());
					} else if (wjc.getTrade().getName().indexOf("进料") >= 0) {
						head.setImContractNo(wjc.getImportContractNo());
						head.setIeContractNo(wjc.getExportContractNo());
					}
				}
				head.setBeginDate(wjc.getApplyDate());
				head.setEndDate(wjc.getEffectiveDate());
				head.setImgAmount(wjc.getImporTotalMoney());
				head.setExgAmount(wjc.getExporTotalMoney());
			}
			wjszAction.saveObj(new com.bestway.common.Request(
					com.bestway.bcus.client.common.CommonVars.getCurrUser()),
					head);
		}
		List imgList = (List) wjszAction
				.findDzscImg(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), head);
		for (int i = 0; i < imgList.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) imgList.get(i);
			mapImg.put(imgBill.getSeqNum(), imgBill);
		}
		List wjImgList = wjContractAction.findWJContractImg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);

		for (int i = 0; i < wjImgList.size(); i++) {
			boolean isNewContractImg = false;
			WJContractImg wjContractImg = (WJContractImg) wjImgList.get(i);
			DzscEmsImgBill imgBill = mapImg.get(wjContractImg.getSeqNum());
			if (imgBill == null) {
				imgBill = new DzscEmsImgBill();
				imgBill.setSeqNum(wjContractImg.getSeqNum());
				imgBill.setDzscEmsPorHead(head);
				imgBill
						.setModifyMark(com.bestway.common.constant.ModifyMarkState.ADDED);
				isNewContractImg = true;
			}
			/**
			 * 当是新增的料件或覆盖旧的料件时保存
			 */
			if (isNewContractImg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String wjEmsNo = head.getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer credenceNo = wjContractImg.getVoucherCode();
				// 抓取合同备案中的合同序号。
				Integer wjSeqNum = (Integer) wjszAction
						.findDzscImgWjSeqNumByWjCode(
								new com.bestway.common.Request(
										com.bestway.bcus.client.common.CommonVars
												.getCurrUser()), wjEmsNo,
								String.valueOf(credenceNo));
				com.bestway.bcus.custombase.entity.hscode.Complex complex = wjContractImg
						.getComplexCode() == null ? null
						: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.hscode.Complex.class,
								"code", wjContractImg.getComplexCode());
				com.bestway.bcus.custombase.entity.parametercode.Unit unit = wjContractImg
						.getUnit() == null ? null
						: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.parametercode.Unit.class,
								"code", wjContractImg.getUnit().getCode());
				com.bestway.bcus.custombase.entity.countrycode.Country country = wjContractImg
						.getCountry() == null ? null
						: (com.bestway.bcus.custombase.entity.countrycode.Country) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.countrycode.Country.class,
								"code", wjContractImg.getCountry().getCode());
				if (!isNewContractImg) {
					if (!checkObjectIsEquals(imgBill.getWjSeqNum(), wjSeqNum)
							|| !checkObjectIsEquals(imgBill.getComplex(),
									complex)
							|| !checkObjectIsEquals(imgBill.getName(),
									wjContractImg.getName())
							|| !checkObjectIsEquals(imgBill.getSpec(),
									wjContractImg.getSepce())
							|| !checkObjectIsEquals(imgBill.getAmount(),
									wjContractImg.getAmount())
							|| !checkObjectIsEquals(imgBill.getMoney(),
									wjContractImg.getTotalMoney())
							|| !checkObjectIsEquals(imgBill.getPrice(),
									wjContractImg.getUnitPrice())
							|| !checkObjectIsEquals(imgBill.getUnit(), unit)
							|| !checkObjectIsEquals(imgBill.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(imgBill.getModifyMark())) {
							imgBill
									.setModifyMark(com.bestway.common.constant.ModifyMarkState.MODIFIED);
						}
					}
				}
				imgBill.setWjSeqNum(wjSeqNum);
				imgBill.setComplex(complex);
				imgBill.setName(wjContractImg.getName());
				imgBill.setSpec(wjContractImg.getSepce());
				imgBill.setAmount(wjContractImg.getAmount());
				imgBill.setUnit(unit);
				imgBill.setCountry(country);
				imgBill.setPrice(wjContractImg.getUnitPrice());
				imgBill.setMoney(wjContractImg.getTotalMoney());
				imgBill = (DzscEmsImgBill) wjszAction.saveObj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), imgBill);
				mapImg.put(imgBill.getSeqNum(), imgBill);
				if (isNewContractImg) {
					insertNumLj++;
				} else {
					updateNumLj++;
				}
			}
		}
		List exgList = (List) wjszAction
				.findDzscExg(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), head);
		for (int i = 0; i < exgList.size(); i++) {
			DzscEmsExgBill exgBill = (DzscEmsExgBill) exgList.get(i);
			mapExg.put(exgBill.getSeqNum(), exgBill);
		}
		List wjExgList = wjContractAction.findWJContractExg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjExgList.size(); i++) {
			boolean isNewContractExg = false;
			WJContractExg wjContractExg = (WJContractExg) wjExgList.get(i);
			DzscEmsExgBill exgBill = mapExg.get(wjContractExg.getSeqNum());
			if (exgBill == null) {
				exgBill = new DzscEmsExgBill();
				exgBill.setSeqNum(wjContractExg.getSeqNum());
				exgBill.setDzscEmsPorHead(head);
				exgBill
						.setModifyMark(com.bestway.common.constant.ModifyMarkState.ADDED);
				isNewContractExg = true;
			}
			/**
			 * 当是新增的成品或覆盖旧的成品时保存
			 */
			if (isNewContractExg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String wjEmsNo = head.getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer credenceNo = wjContractExg.getVoucherCode();
				// 抓取合同备案中的外经编号==外经系统中的凭证序号。
				Integer wjSeqNum = (Integer) wjszAction
						.findDzscImgWjSeqNumByWjCode(
								new com.bestway.common.Request(
										com.bestway.bcus.client.common.CommonVars
												.getCurrUser()), wjEmsNo,
								String.valueOf(credenceNo));
				com.bestway.bcus.custombase.entity.hscode.Complex complex = wjContractExg
						.getComplexCode() == null ? null
						: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.hscode.Complex.class,
								"code", wjContractExg.getComplexCode());
				com.bestway.bcus.custombase.entity.parametercode.Unit unit = wjContractExg
						.getUnit() == null ? null
						: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.parametercode.Unit.class,
								"code", wjContractExg.getUnit().getCode());
				com.bestway.bcus.custombase.entity.countrycode.Country country = wjContractExg
						.getCountry() == null ? null
						: (com.bestway.bcus.custombase.entity.countrycode.Country) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.countrycode.Country.class,
								"code", wjContractExg.getCountry().getCode());
				if (!isNewContractExg) {
					if (!checkObjectIsEquals(exgBill.getWjSeqNum(), wjSeqNum)
							|| !checkObjectIsEquals(exgBill.getComplex(),
									complex)
							|| !checkObjectIsEquals(exgBill.getName(),
									wjContractExg.getName())
							|| !checkObjectIsEquals(exgBill.getSpec(),
									wjContractExg.getSepce())
							|| !checkObjectIsEquals(exgBill.getAmount(),
									wjContractExg.getAmount())
							|| !checkObjectIsEquals(exgBill.getMoney(),
									wjContractExg.getTotalMoney())
							|| !checkObjectIsEquals(exgBill.getPrice(),
									wjContractExg.getUnitPrice())
							|| !checkObjectIsEquals(exgBill.getUnit(), unit)
							|| !checkObjectIsEquals(exgBill.getCountry(),
									country)
							|| !checkObjectIsEquals(exgBill.getMachinPrice(),
									wjContractExg.getProcessingPrice())
							|| !checkObjectIsEquals(exgBill.getMachinMoney(),
									wjContractExg.getProcessingMoney())) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(exgBill.getModifyMark())) {
							exgBill
									.setModifyMark(com.bestway.common.constant.ModifyMarkState.MODIFIED);
						}
					}
				}
				exgBill.setWjSeqNum(wjSeqNum);
				exgBill.setComplex(complex);
				exgBill.setName(wjContractExg.getName());
				exgBill.setSpec(wjContractExg.getSepce());
				exgBill.setAmount(wjContractExg.getAmount());
				exgBill.setUnit(unit);
				exgBill.setCountry(country);
				exgBill.setPrice(wjContractExg.getUnitPrice());
				exgBill.setMoney(wjContractExg.getTotalMoney());
				exgBill.setMachinPrice(wjContractExg.getProcessingPrice());
				exgBill.setMachinMoney(wjContractExg.getProcessingMoney());
				exgBill = (DzscEmsExgBill) wjszAction.saveObj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), exgBill);
				mapExg.put(wjContractExg.getSeqNum(), exgBill);
				if (isNewContractExg) {
					insertNumCp++;
				} else {
					updateNumCp++;
				}
			}
		}
		List bomList = (List) wjszAction
				.findEmsPorBomBillByHead(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), head);
		for (int i = 0; i < bomList.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) bomList.get(i);
			String key = bomBill.getDzscEmsExgBill().getSeqNum() + "/"
					+ bomBill.getImgSeqNum();
			mapBom.put(key, bomBill);
		}
		List wjBomList = wjContractAction.findWJContractBom(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjBomList.size(); i++) {
			boolean isNewContractBom = false;
			WJContractBom wjContractBom = (WJContractBom) wjBomList.get(i);
			String key = wjContractBom.getWjContractExg().getSeqNum() + "/"
					+ wjContractBom.getSeqNum();
			DzscEmsBomBill bomBill = mapBom.get(key);
			if (bomBill == null) {
				bomBill = new DzscEmsBomBill();
				bomBill.setDzscEmsExgBill(mapExg.get(wjContractBom
						.getWjContractExg().getSeqNum()));
				bomBill
						.setModifyMark(com.bestway.common.constant.ModifyMarkState.ADDED);
				isNewContractBom = true;
			}
			/**
			 * 当是新增的单耗或覆盖旧的单耗时保存
			 */
			if (isNewContractBom || isConver) {
				com.bestway.bcus.custombase.entity.hscode.Complex complex = wjContractBom
						.getComplexCode() == null ? null
						: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.hscode.Complex.class,
								"code", wjContractBom.getComplexCode());
				com.bestway.bcus.custombase.entity.parametercode.Unit unit = wjContractBom
						.getUnit() == null ? null
						: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.parametercode.Unit.class,
								"code", wjContractBom.getUnit().getCode());
				com.bestway.bcus.custombase.entity.countrycode.Country country = wjContractBom
						.getCountry() == null ? null
						: (com.bestway.bcus.custombase.entity.countrycode.Country) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.countrycode.Country.class,
								"code", wjContractBom.getCountry().getCode());
				if (!isNewContractBom) {
					if (!checkObjectIsEquals(bomBill.getComplex(), complex)
							|| !checkObjectIsEquals(bomBill.getName(),
									wjContractBom.getName())
							|| !checkObjectIsEquals(bomBill.getSpec(),
									wjContractBom.getSepce())
							|| !checkObjectIsEquals(bomBill.getUnitWare(),
									wjContractBom.getUnitWaste())
							|| !checkObjectIsEquals(bomBill.getWare(),
									wjContractBom.getWaste())
							|| !checkObjectIsEquals(bomBill.getPrice(),
									wjContractBom.getUnitPrice())
							|| !checkObjectIsEquals(bomBill.getUnit(), unit)
							|| !checkObjectIsEquals(bomBill.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(bomBill.getModifyMark())) {
							bomBill
									.setModifyMark(com.bestway.common.constant.ModifyMarkState.MODIFIED);
						}
					}
				}
				bomBill.setImgSeqNum(wjContractBom.getSeqNum());
				bomBill.setComplex(complex);
				bomBill.setName(wjContractBom.getName());
				bomBill.setSpec(wjContractBom.getSepce());
				bomBill.setUnitWare(wjContractBom.getUnitWaste());
				bomBill.setWare(wjContractBom.getWaste());
				bomBill.setUnitDosage(wjContractBom.getUnitUsed());
				bomBill.setUnit(unit);
				bomBill.setCountry(country);
				bomBill.setPrice(wjContractBom.getUnitPrice());
				wjszAction.saveObj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), bomBill);
				if (isNewContractBom) {
					insertNumBom++;
				} else {
					updateNumBom++;
				}
			}
		}

		tishi += "料件 - (不存在)插入数据：" + String.valueOf(insertNumLj) + " 笔；\n";
		tishi += "料件 - (存在且未审核)更新数据：" + String.valueOf(updateNumLj) + " 笔；\n\n";

		tishi += "成品 - (不存在)插入数据：" + String.valueOf(insertNumCp) + " 笔；\n";
		tishi += "成品 - (存在且未审核)更新数据：" + String.valueOf(updateNumCp) + " 笔；\n\n";

		tishi += "单耗 - (不存在)插入数据：" + String.valueOf(insertNumBom) + " 笔；\n";
		tishi += "单耗 - (存在且未审核)更新数据：" + String.valueOf(updateNumBom) + " 笔；";

		return "数据下载成功！\n\n" + tishi;
	}

	/**
	 * 电子化手册下载资料
	 * 
	 */
	public static String downloadDataContract(boolean isConver, Contract head) {
		int insertNumLj = 0;
		int updateNumLj = 0;
		int insertNumCp = 0;
		int updateNumCp = 0;
		int insertNumBom = 0;
		int updateNumBom = 0;
		Map<Integer, ContractImg> mapImg = new HashMap<Integer, ContractImg>();
		Map<Integer, ContractExg> mapExg = new HashMap<Integer, ContractExg>();
		Map<String, ContractBom> mapBom = new HashMap<String, ContractBom>();
		String tishi = "";
		if (DeclareState.WAIT_EAA.equals(head.getDeclareState())) {
			tishi = "该手册的正在等待审批，所以不能下载资料";
			return tishi;
		}
		if (DeclareState.PROCESS_EXE.equals(head.getDeclareState())) {
			tishi = "该手册的正在执行，所以不能下载资料";
			return tishi;
		}
		if (head.getCorrEmsNo() == null
				|| "".equals(head.getCorrEmsNo().trim())) {
			tishi = "该手册" + head.getCopEmsNo() + "的备案资料库编号为空，所以不能下载资料";
			return tishi;
		}
		WJContract wjc = null;
		if (head.getWjComputerNo() == null
				|| "".equals(head.getWjComputerNo().trim())) {// 全套新增
			tishi = "该手册的外经申请文号为空";
			return tishi;
		} else {
			List listHead = wjContractAction.findWJContractByApplyNo(
					com.bestway.jptds.client.common.CommonVars.getRequest(),
					head.getWjComputerNo().trim());
			if (listHead.size() > 0 && listHead.get(0) != null) {
				wjc = (WJContract) listHead.get(0);
			} else {
				tishi = "在外经系统中没有找到申请文号为" + head.getWjComputerNo().trim()
						+ "的合同";
				return tishi;
			}
		}
		if (isConver) {
			String portCode = wjc.getInPortCode();
			if (portCode != null && !"".equals(portCode.trim())) {
				String[] codes = portCode.split("/");
				head
						.setIePort1(codes.length < 1 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[0]));
				head
						.setIePort2(codes.length < 2 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[1]));
				head
						.setIePort3(codes.length < 3 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[2]));
				head
						.setIePort4(codes.length < 4 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[3]));
				head
						.setIePort5(codes.length < 5 ? null
								: (com.bestway.bcus.custombase.entity.countrycode.Customs) findCustomsBaseEntityFromJBCUS(
										com.bestway.bcus.custombase.entity.countrycode.Customs.class,
										"code", codes[4]));
				if (wjc.getTrade() != null) {
					if (wjc.getTrade().getName().indexOf("来料") >= 0) {
						head.setImpContractNo(wjc.getWsContractNo());
						head.setExpContractNo(wjc.getWsContractNo());
					} else if (wjc.getTrade().getName().indexOf("进料") >= 0) {
						head.setImpContractNo(wjc.getImportContractNo());
						head.setExpContractNo(wjc.getExportContractNo());
					}
				}
				head.setBeginDate(wjc.getApplyDate());
				head.setEndDate(wjc.getEffectiveDate());
				head.setImgAmount(wjc.getImporTotalMoney());
				head.setExgAmount(wjc.getExporTotalMoney());
				if (wjc.getCurr() != null) {
					com.bestway.bcus.custombase.entity.parametercode.Curr curr = (com.bestway.bcus.custombase.entity.parametercode.Curr) findCustomsBaseEntityFromJBCUS(
							com.bestway.bcus.custombase.entity.parametercode.Curr.class,
							"code", wjc.getCurr().getCode());
					head.setCurr(curr);
				} else {
					head.setCurr(null);
				}
			}
			wjszAction.saveObj(new com.bestway.common.Request(
					com.bestway.bcus.client.common.CommonVars.getCurrUser()),
					head);
		}
		List imgList = (List) wjszAction
				.findContractImgByContract(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), head);
		for (int i = 0; i < imgList.size(); i++) {
			ContractImg contractImg = (ContractImg) imgList.get(i);
			mapImg.put(contractImg.getSeqNum(), contractImg);
		}
		List wjImgList = wjContractAction.findWJContractImg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);

		for (int i = 0; i < wjImgList.size(); i++) {
			boolean isNewContractImg = false;
			WJContractImg wjContractImg = (WJContractImg) wjImgList.get(i);
			ContractImg contractImg = mapImg.get(wjContractImg.getSeqNum());
			if (contractImg == null) {
				contractImg = new ContractImg();
				contractImg.setSeqNum(wjContractImg.getSeqNum());
				contractImg.setContract(head);
				contractImg
						.setModifyMark(com.bestway.common.constant.ModifyMarkState.ADDED);
				isNewContractImg = true;
			}
			/**
			 * 当是新增的料件或覆盖旧的料件时保存
			 */
			if (isNewContractImg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String dictEmsNo = head.getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer credenceNo = wjContractImg.getVoucherCode();
				// 抓取合同备案中的合同序号。
				Integer dictSeqNum = (Integer) wjszAction
						.findBcsDictImgWjSeqNumByWjCode(
								new com.bestway.common.Request(
										com.bestway.bcus.client.common.CommonVars
												.getCurrUser()), dictEmsNo,
								String.valueOf(credenceNo));
				com.bestway.bcus.custombase.entity.hscode.Complex complex = wjContractImg
						.getComplexCode() == null ? null
						: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.hscode.Complex.class,
								"code", wjContractImg.getComplexCode());
				com.bestway.bcus.custombase.entity.parametercode.Unit unit = wjContractImg
						.getUnit() == null ? null
						: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.parametercode.Unit.class,
								"code", wjContractImg.getUnit().getCode());
				com.bestway.bcus.custombase.entity.countrycode.Country country = wjContractImg
						.getCountry() == null ? null
						: (com.bestway.bcus.custombase.entity.countrycode.Country) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.countrycode.Country.class,
								"code", wjContractImg.getCountry().getCode());
				if (!isNewContractImg) {
					if (!checkObjectIsEquals(contractImg.getComplex(), complex)
							|| !checkObjectIsEquals(contractImg.getName(),
									wjContractImg.getName())
							|| !checkObjectIsEquals(contractImg.getSpec(),
									wjContractImg.getSepce())
							|| !checkObjectIsEquals(contractImg.getAmount(),
									wjContractImg.getAmount())
							|| !checkObjectIsEquals(
									contractImg.getTotalPrice(), wjContractImg
											.getTotalMoney())
							|| !checkObjectIsEquals(contractImg
									.getDeclarePrice(), wjContractImg
									.getUnitPrice())
							|| !checkObjectIsEquals(contractImg.getUnit(), unit)
							|| !checkObjectIsEquals(contractImg.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(contractImg.getModifyMark())) {
							contractImg
									.setModifyMark(com.bestway.common.constant.ModifyMarkState.MODIFIED);
						}
					}
				}
				contractImg.setCredenceNo(dictSeqNum);
				contractImg.setComplex(complex);
				contractImg.setName(wjContractImg.getName());
				contractImg.setSpec(wjContractImg.getSepce());
				contractImg.setAmount(wjContractImg.getAmount());
				contractImg.setUnit(unit);
				contractImg.setCountry(country);
				contractImg.setDeclarePrice(wjContractImg.getUnitPrice());
				contractImg.setTotalPrice(wjContractImg.getTotalMoney());
				contractImg = (ContractImg) wjszAction.saveObj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), contractImg);
				mapImg.put(contractImg.getSeqNum(), contractImg);
				if (isNewContractImg) {
					insertNumLj++;
				} else {
					updateNumLj++;
				}
			}
		}
		List exgList = (List) wjszAction
				.findContractExgByContract(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), head);
		for (int i = 0; i < exgList.size(); i++) {
			ContractExg contractExg = (ContractExg) exgList.get(i);
			mapExg.put(contractExg.getSeqNum(), contractExg);
		}
		List wjExgList = wjContractAction.findWJContractExg(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjExgList.size(); i++) {
			boolean isNewContractExg = false;
			WJContractExg wjContractExg = (WJContractExg) wjExgList.get(i);
			ContractExg contractExg = mapExg.get(wjContractExg.getSeqNum());
			if (contractExg == null) {
				contractExg = new ContractExg();
				contractExg.setSeqNum(wjContractExg.getSeqNum());
				contractExg.setContract(head);
				contractExg
						.setModifyMark(com.bestway.common.constant.ModifyMarkState.ADDED);
				isNewContractExg = true;
			}
			/**
			 * 当是新增的成品或覆盖旧的成品时保存
			 */
			if (isNewContractExg || isConver) {
				// 通关手册备案中的合同备案手册号码
				String dictEmsNo = head.getCorrEmsNo();
				// 通关手册备案料件中的合同备案料件序号
				Integer credenceNo = wjContractExg.getVoucherCode();
				// 抓取合同备案中的外经编号==外经系统中的凭证序号。
				Integer dictSeqNum = (Integer) wjszAction
						.findBcsDictExgWjSeqNumByWjCode(
								new com.bestway.common.Request(
										com.bestway.bcus.client.common.CommonVars
												.getCurrUser()), dictEmsNo,
								String.valueOf(credenceNo));
				com.bestway.bcus.custombase.entity.hscode.Complex complex = wjContractExg
						.getComplexCode() == null ? null
						: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.hscode.Complex.class,
								"code", wjContractExg.getComplexCode());
				com.bestway.bcus.custombase.entity.parametercode.Unit unit = wjContractExg
						.getUnit() == null ? null
						: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.parametercode.Unit.class,
								"code", wjContractExg.getUnit().getCode());
				com.bestway.bcus.custombase.entity.countrycode.Country country = wjContractExg
						.getCountry() == null ? null
						: (com.bestway.bcus.custombase.entity.countrycode.Country) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.countrycode.Country.class,
								"code", wjContractExg.getCountry().getCode());
				if (!isNewContractExg) {
					if (!checkObjectIsEquals(contractExg.getComplex(), complex)
							|| !checkObjectIsEquals(contractExg.getName(),
									wjContractExg.getName())
							|| !checkObjectIsEquals(contractExg.getSpec(),
									wjContractExg.getSepce())
							|| !checkObjectIsEquals(contractExg
									.getExportAmount(), wjContractExg
									.getAmount())
							|| !checkObjectIsEquals(
									contractExg.getTotalPrice(), wjContractExg
											.getTotalMoney())
							|| !checkObjectIsEquals(contractExg.getUnitPrice(),
									wjContractExg.getUnitPrice())
							|| !checkObjectIsEquals(contractExg.getUnit(), unit)
							|| !checkObjectIsEquals(contractExg.getCountry(),
									country)
							|| !checkObjectIsEquals(contractExg
									.getProcessUnitPrice(), wjContractExg
									.getProcessingPrice())
							|| !checkObjectIsEquals(contractExg
									.getProcessTotalPrice(), wjContractExg
									.getProcessingMoney())) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(contractExg.getModifyMark())) {
							contractExg
									.setModifyMark(com.bestway.common.constant.ModifyMarkState.MODIFIED);
						}
					}
				}
				contractExg.setCredenceNo(dictSeqNum);
				contractExg.setComplex(complex);
				contractExg.setName(wjContractExg.getName());
				contractExg.setSpec(wjContractExg.getSepce());
				contractExg.setExportAmount(wjContractExg.getAmount());
				contractExg.setUnit(unit);
				contractExg.setCountry(country);
				contractExg.setUnitPrice(wjContractExg.getUnitPrice());
				contractExg.setTotalPrice(wjContractExg.getTotalMoney());
				contractExg.setProcessUnitPrice(wjContractExg
						.getProcessingPrice());
				contractExg.setProcessTotalPrice(wjContractExg
						.getProcessingMoney());
				contractExg = (ContractExg) wjszAction.saveObj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), contractExg);
				mapExg.put(wjContractExg.getSeqNum(), contractExg);
				if (isNewContractExg) {
					insertNumCp++;
				} else {
					updateNumCp++;
				}
			}
		}
		List bomList = (List) wjszAction
				.findContractBomByContract(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), head);
		for (int i = 0; i < bomList.size(); i++) {
			ContractBom contractBom = (ContractBom) bomList.get(i);
			String key = contractBom.getContractExg().getSeqNum() + "/"
					+ contractBom.getContractImgSeqNum();
			mapBom.put(key, contractBom);
		}
		List wjBomList = wjContractAction.findWJContractBom(
				com.bestway.jptds.client.common.CommonVars.getRequest(), wjc);
		for (int i = 0; i < wjBomList.size(); i++) {
			boolean isNewContractBom = false;
			WJContractBom wjContractBom = (WJContractBom) wjBomList.get(i);
			String key = wjContractBom.getWjContractExg().getSeqNum() + "/"
					+ wjContractBom.getSeqNum();
			ContractBom contractBom = mapBom.get(key);
			if (contractBom == null) {
				contractBom = new ContractBom();
				contractBom.setContractExg(mapExg.get(wjContractBom
						.getWjContractExg().getSeqNum()));
				contractBom
						.setModifyMark(com.bestway.common.constant.ModifyMarkState.ADDED);
				isNewContractBom = true;
			}
			/**
			 * 当是新增的单耗或覆盖旧的单耗时保存
			 */
			if (isNewContractBom || isConver) {
				com.bestway.bcus.custombase.entity.hscode.Complex complex = wjContractBom
						.getComplexCode() == null ? null
						: (com.bestway.bcus.custombase.entity.hscode.Complex) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.hscode.Complex.class,
								"code", wjContractBom.getComplexCode());
				com.bestway.bcus.custombase.entity.parametercode.Unit unit = wjContractBom
						.getUnit() == null ? null
						: (com.bestway.bcus.custombase.entity.parametercode.Unit) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.parametercode.Unit.class,
								"code", wjContractBom.getUnit().getCode());
				com.bestway.bcus.custombase.entity.countrycode.Country country = wjContractBom
						.getCountry() == null ? null
						: (com.bestway.bcus.custombase.entity.countrycode.Country) findCustomsBaseEntityFromJBCUS(
								com.bestway.bcus.custombase.entity.countrycode.Country.class,
								"code", wjContractBom.getCountry().getCode());
				if (!isNewContractBom) {
					if (!checkObjectIsEquals(contractBom.getComplex(), complex)
							|| !checkObjectIsEquals(contractBom.getName(),
									wjContractBom.getName())
							|| !checkObjectIsEquals(contractBom.getSpec(),
									wjContractBom.getSepce())
							|| !checkObjectIsEquals(contractBom.getUnitWaste(),
									wjContractBom.getUnitWaste())
							|| !checkObjectIsEquals(contractBom.getWaste(),
									wjContractBom.getWaste())
							|| !checkObjectIsEquals(contractBom
									.getDeclarePrice(), wjContractBom
									.getUnitPrice())
							|| !checkObjectIsEquals(contractBom.getUnit(), unit)
							|| !checkObjectIsEquals(contractBom.getCountry(),
									country)) {
						if (!com.bestway.jptds.common.ModifyMarkState.ADDED
								.equals(contractBom.getModifyMark())) {
							contractBom
									.setModifyMark(com.bestway.common.constant.ModifyMarkState.MODIFIED);
						}
					}
				}
				contractBom.setContractImgSeqNum(wjContractBom.getSeqNum());
				ContractImg contractImg = mapImg.get(wjContractBom.getSeqNum());
				if (contractImg != null) {
					contractBom.setImgCredenceNo(contractImg.getCredenceNo());
				}
				contractBom.setComplex(complex);
				contractBom.setName(wjContractBom.getName());
				contractBom.setSpec(wjContractBom.getSepce());
				contractBom.setUnitWaste(wjContractBom.getUnitWaste());
				contractBom.setWaste(wjContractBom.getWaste());
				contractBom.setUnitDosage(wjContractBom.getUnitUsed());
				contractBom.setUnit(unit);
				contractBom.setCountry(country);
				contractBom.setDeclarePrice(wjContractBom.getUnitPrice());
				wjszAction.saveObj(
						new com.bestway.common.Request(
								com.bestway.bcus.client.common.CommonVars
										.getCurrUser()), contractBom);
				if (isNewContractBom) {
					insertNumBom++;
				} else {
					updateNumBom++;
				}
			}
		}
		tishi += "料件 - (不存在)插入数据：" + String.valueOf(insertNumLj) + " 笔；\n";
		tishi += "料件 - (存在且未审核)更新数据：" + String.valueOf(updateNumLj) + " 笔；\n\n";

		tishi += "成品 - (不存在)插入数据：" + String.valueOf(insertNumCp) + " 笔；\n";
		tishi += "成品 - (存在且未审核)更新数据：" + String.valueOf(updateNumCp) + " 笔；\n\n";

		tishi += "单耗 - (不存在)插入数据：" + String.valueOf(insertNumBom) + " 笔；\n";
		tishi += "单耗 - (存在且未审核)更新数据：" + String.valueOf(updateNumBom) + " 笔；";

		return "数据下载成功！\n\n" + tishi;
	}
}
