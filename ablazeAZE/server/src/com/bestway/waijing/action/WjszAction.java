package com.bestway.waijing.action;

import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.waijing.entity.WjszParaSet;

public interface WjszAction {

	/**
	 * 保存实体对象
	 * 
	 * @param obj
	 * @return
	 */
	Object saveObj(Request request, Object obj);

	/**
	 * 根据类名和编码查询海关基础资料实体
	 * 
	 * @param clsName
	 * @param codeField
	 * @param codeValue
	 * @return
	 */
	Object findCustomsBaseEntity(Request request, Class<?> clsName,
			String codeField, String codeValue);

	List saveListObj(Request request, List ls);

	/**
	 * 保存参数设置
	 * 
	 * @param paraset
	 */
	void saveWjszParaSet(Request request, WjszParaSet paraset);

	/**
	 * 查询参数设置
	 * 
	 * @return
	 */
	WjszParaSet findWjszParaSet(Request request);

	/**
	 * 抓取电子化手册备案资料库备案中料件的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	String findBcsImgWjCode(Request request, String dictEmsNo, Integer seqNum);

	/**
	 * 抓取电子化手册备案资料库备案中成品的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	String findBcsExgWjCode(Request request, String dictEmsNo, Integer seqNum);

	/**
	 * 抓取电子手册合同备案中料件的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	String findDzscImgWjCode(Request request, String wjHeadEmsNo, Integer seqNum);

	/**
	 * 抓取电子手册合同备案中成品的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	String findDzscExgWjCode(Request request, String wjHeadEmsNo, Integer seqNum);

	/**
	 * 电子手册通关手册成品表OK!
	 */
	List findDzscExg(Request request, DzscEmsPorHead porHead);

	/**
	 * 电子手册通关手册料件总表OK!
	 */
	List findDzscImg(Request request, DzscEmsPorHead porHead);

	List findEmsPorBomBillByHead(Request request, DzscEmsPorHead head);

	// OK!
	List findContractImgByContract(Request request, Contract head);

	// OK!
	List findContractExgByContract(Request request, Contract head);

	// OK!
	List findContractBomByContract(Request request, Contract head);

	// 保存ok!
	void saveModuleSelect(Request request, int i);

	/**
	 * ok!
	 * 
	 * @param i
	 */
	void saveConModuleSelect(Request request, int i);

	// 查找模式ok!
	Integer getModule(Request request);

	// 查找模式ok!
	Integer getConModule(Request request);

	/**
	 * 电子手册合同备案表头
	 * 
	 * @return
	 */
	List findBcusWjHead(Request request);

	/**
	 * 电子手册合同备案表头
	 * 
	 * @return
	 */
	List findDzscWjHead(Request request);

	List findBcsDictPorHead(Request request);

	/**
	 * 电子手册表头
	 * 
	 * @return
	 */
	List findDzscEmsPorHead(Request request);

	/**
	 * 电子化手册表头
	 */
	List findContractHead(Request request);

	DzscEmsPorHead saveDzscEmsPorHead(Request request, DzscEmsPorHead obj);

	Contract saveContract(Request request, Contract obj);

	DzscEmsImgWj saveDzscEmsImgWj(Request request, DzscEmsImgWj obj);

	DzscEmsExgWj saveDzscEmsExgWj(Request request, DzscEmsExgWj obj);

	DzscEmsImgBill saveDzscEmsImgBill(Request request, DzscEmsImgBill obj);

	DzscEmsExgBill saveDzscEmsExgBill(Request request, DzscEmsExgBill obj);

	BcsDictPorImg saveBcsDictPorImg(Request request, BcsDictPorImg obj);

	BcsDictPorExg saveBcsDictPorExg(Request request, BcsDictPorExg obj);

	List findDzscEmsImgWj(Request request, DzscEmsPorWjHead chead);

	List findDzscEmsExgWj(Request request, DzscEmsPorWjHead chead);

	List findEmsEdiTrImg(Request request, EmsEdiTrHead head);

	List findEmsEdiTrExg(Request request, EmsEdiTrHead head);

	List findBcsDictPorImg(Request request, BcsDictPorHead porHead);

	List findBcsDictPorExg(Request request, BcsDictPorHead porHead);

	List findContractBomByExgId(Request request, ContractExg exg);

	/**
	 * 电子手册通关手册单耗表
	 */
	List findEmsPorBomBill(Request request, DzscEmsExgBill exg);

	/**
	 * 根据凭证序号抓取电子手册合同备案中料件的备案序号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	Integer findDzscImgWjSeqNumByWjCode(Request request, String wjHeadEmsNo,
			String wjCode);

	/**
	 * 根据凭证序号抓取电子手册合同备案中成品的备案序号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	Integer findDzscExgWjSeqNumByWjCode(Request request, String wjHeadEmsNo,
			String wjCode);

	/**
	 * 根据凭证序号抓取电子化手册中备案资料库料件的备案序号
	 * 
	 * @param dictEmsNo
	 * @param seqNum
	 * @return
	 */
	Integer findBcsDictImgWjSeqNumByWjCode(Request request, String dictEmsNo,
			String wjCode);

	/**
	 * 根据凭证序号抓取电子化手册中备案资料库料件的备案序号
	 * 
	 * @param dictEmsNo
	 * @param seqNum
	 * @return
	 */
	Integer findBcsDictExgWjSeqNumByWjCode(Request request, String dictEmsNo,
			String wjCode);

	/**
	 * 获得最大的料件序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	int getMaxDictPorImgSeqNum(Request request, BcsDictPorHead head);

	/**
	 * 获得最大的成品序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	int getMaxDictPorExgSeqNum(Request request, BcsDictPorHead head);

	/**
	 * 获取DzscEmsImgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	Integer getMaxDzscEmsImgWjNum(Request request, DzscEmsPorWjHead head);

	/**
	 * 获取DzscEmsExgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	Integer getMaxDzscEmsExgWjNum(Request request, DzscEmsPorWjHead head);
}
