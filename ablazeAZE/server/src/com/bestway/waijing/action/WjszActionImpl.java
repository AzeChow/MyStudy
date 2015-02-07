package com.bestway.waijing.action;

import java.util.ArrayList;
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
import com.bestway.waijing.dao.WjszDao;
import com.bestway.waijing.entity.WjszParaSet;

public class WjszActionImpl implements WjszAction {

	private WjszDao wjszDao = null;

	public WjszDao getWjszDao() {
		return wjszDao;
	}

	public void setWjszDao(WjszDao wjszDao) {
		this.wjszDao = wjszDao;
	}

	/**
	 * 保存实体对象
	 * 
	 * @param obj
	 * @return
	 */
	public Object saveObj(Request request, Object obj) {
		return this.wjszDao.saveObj(obj);
	}

	/**
	 * 根据类名和编码查询海关基础资料实体
	 * 
	 * @param clsName
	 * @param codeField
	 * @param codeValue
	 * @return
	 */
	public Object findCustomsBaseEntity(Request request, Class<?> clsName,
			String codeField, String codeValue) {
		return this.wjszDao
				.findCustomsBaseEntity(clsName, codeField, codeValue);
	}

	public List saveListObj(Request request, List ls) {
		return this.wjszDao.saveListObj(ls);
	}

	/**
	 * 保存参数设置
	 * 
	 * @param paraset
	 */
	public void saveWjszParaSet(Request request, WjszParaSet paraset) {
		this.wjszDao.saveWjszParaSet(paraset);
	}

	/**
	 * 查询参数设置
	 * 
	 * @return
	 */
	public WjszParaSet findWjszParaSet(Request request) {
		return this.wjszDao.findWjszParaSet();
	}

	/**
	 * 抓取电子化手册备案资料库备案中料件的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findBcsImgWjCode(Request request, String dictEmsNo,
			Integer seqNum) {
		return this.wjszDao.findBcsImgWjCode(dictEmsNo, seqNum);
	}

	/**
	 * 抓取电子化手册备案资料库备案中成品的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findBcsExgWjCode(Request request, String dictEmsNo,
			Integer seqNum) {
		return this.wjszDao.findBcsExgWjCode(dictEmsNo, seqNum);
	}

	/**
	 * 抓取电子手册合同备案中料件的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findDzscImgWjCode(Request request, String wjHeadEmsNo,
			Integer seqNum) {
		return this.wjszDao.findDzscImgWjCode(wjHeadEmsNo, seqNum);
	}

	/**
	 * 抓取电子手册合同备案中成品的外经编号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findDzscExgWjCode(Request request, String wjHeadEmsNo,
			Integer seqNum) {
		return this.wjszDao.findDzscExgWjCode(wjHeadEmsNo, seqNum);
	}

	/**
	 * 电子手册通关手册成品表OK!
	 */
	public List findDzscExg(Request request, DzscEmsPorHead porHead) {
		return this.wjszDao.findDzscExg(porHead);
	}

	/**
	 * 电子手册通关手册料件总表OK!
	 */
	public List findDzscImg(Request request, DzscEmsPorHead porHead) {
		return this.wjszDao.findDzscImg(porHead);
	}

	public List findEmsPorBomBillByHead(Request request, DzscEmsPorHead head) {
		return this.wjszDao.findEmsPorBomBillByHead(head);
	}

	// OK!
	public List findContractImgByContract(Request request, Contract head) {
		return this.wjszDao.findContractImgByContract(head);
	}

	// OK!
	public List findContractExgByContract(Request request, Contract head) {
		return this.wjszDao.findContractExgByContract(head);
	}

	// OK!
	public List findContractBomByContract(Request request, Contract head) {
		return this.wjszDao.findContractBomByContract(head);
	}

	// 保存ok!
	public void saveModuleSelect(Request request, int i) {
		this.wjszDao.saveModuleSelect(i);
	}

	/**
	 * ok!
	 * 
	 * @param i
	 */
	public void saveConModuleSelect(Request request, int i) {
		this.wjszDao.saveConModuleSelect(i);
	}

	// 查找模式ok!
	public Integer getModule(Request request) {
		return this.wjszDao.getModule();
	}

	// 查找模式ok!
	public Integer getConModule(Request request) {
		return this.wjszDao.getConModule();
	}

	/**
	 * 电子手册合同备案表头
	 * 
	 * @return
	 */
	public List findBcusWjHead(Request request) {
		return this.wjszDao.findBcusWjHead();
	}

	/**
	 * 电子手册合同备案表头
	 * 
	 * @return
	 */
	public List findDzscWjHead(Request request) {
		return this.wjszDao.findDzscWjHead();
	}

	public List findBcsDictPorHead(Request request) {
		return this.wjszDao.findBcsDictPorHead();
	}

	/**
	 * 电子手册表头
	 * 
	 * @return
	 */
	public List findDzscEmsPorHead(Request request) {
		return this.wjszDao.findDzscEmsPorHead();
	}

	/**
	 * 电子化手册表头
	 */
	public List findContractHead(Request request) {
		return this.wjszDao.findContractHead();
	}

	public DzscEmsPorHead saveDzscEmsPorHead(Request request, DzscEmsPorHead obj) {
		return this.wjszDao.saveDzscEmsPorHead(obj);
	}

	public Contract saveContract(Request request, Contract obj) {
		return this.wjszDao.saveContract(obj);
	}

	public DzscEmsImgWj saveDzscEmsImgWj(Request request, DzscEmsImgWj obj) {
		return this.wjszDao.saveDzscEmsImgWj(obj);
	}

	public DzscEmsExgWj saveDzscEmsExgWj(Request request, DzscEmsExgWj obj) {
		return this.wjszDao.saveDzscEmsExgWj(obj);
	}

	public DzscEmsImgBill saveDzscEmsImgBill(Request request, DzscEmsImgBill obj) {
		return this.wjszDao.saveDzscEmsImgBill(obj);
	}

	public DzscEmsExgBill saveDzscEmsExgBill(Request request, DzscEmsExgBill obj) {
		return this.wjszDao.saveDzscEmsExgBill(obj);
	}

	public BcsDictPorImg saveBcsDictPorImg(Request request, BcsDictPorImg obj) {
		return this.wjszDao.saveBcsDictPorImg(obj);
	}

	public BcsDictPorExg saveBcsDictPorExg(Request request, BcsDictPorExg obj) {
		return this.wjszDao.saveBcsDictPorExg(obj);
	}

	public List findDzscEmsImgWj(Request request, DzscEmsPorWjHead chead) {
		return this.wjszDao.findDzscEmsImgWj(chead);
	}

	public List findDzscEmsExgWj(Request request, DzscEmsPorWjHead chead) {
		return this.wjszDao.findDzscEmsExgWj(chead);
	}

	public List findEmsEdiTrImg(Request request, EmsEdiTrHead head) {
		return this.wjszDao.findEmsEdiTrImg(head);
	}

	public List findEmsEdiTrExg(Request request, EmsEdiTrHead head) {
		return this.wjszDao.findEmsEdiTrExg(head);
	}

	public List findBcsDictPorImg(Request request, BcsDictPorHead porHead) {
		return this.wjszDao.findBcsDictPorImg(porHead);
	}

	public List findBcsDictPorExg(Request request, BcsDictPorHead porHead) {
		return this.wjszDao.findBcsDictPorExg(porHead);
	}

	public List findContractBomByExgId(Request request, ContractExg exg) {
		return this.wjszDao.findContractBomByExgId(exg);
	}

	/**
	 * 电子手册通关手册单耗表
	 */
	public List findEmsPorBomBill(Request request, DzscEmsExgBill exg) {
		return this.wjszDao.findEmsPorBomBill(exg);
	}

	/**
	 * 根据凭证序号抓取电子手册合同备案中料件的备案序号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findDzscImgWjSeqNumByWjCode(Request request,
			String wjHeadEmsNo, String wjCode) {
		return this.wjszDao.findDzscImgWjSeqNumByWjCode(wjHeadEmsNo, wjCode);
	}

	/**
	 * 根据凭证序号抓取电子手册合同备案中成品的备案序号
	 * 
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findDzscExgWjSeqNumByWjCode(Request request,
			String wjHeadEmsNo, String wjCode) {
		return this.wjszDao.findDzscExgWjSeqNumByWjCode(wjHeadEmsNo, wjCode);
	}

	/**
	 * 根据凭证序号抓取电子化手册中备案资料库料件的备案序号
	 * 
	 * @param dictEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findBcsDictImgWjSeqNumByWjCode(Request request,
			String dictEmsNo, String wjCode) {
		return this.wjszDao.findBcsDictImgWjSeqNumByWjCode(dictEmsNo, wjCode);
	}

	/**
	 * 根据凭证序号抓取电子化手册中备案资料库料件的备案序号
	 * 
	 * @param dictEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findBcsDictExgWjSeqNumByWjCode(Request request,
			String dictEmsNo, String wjCode) {
		return this.wjszDao.findBcsDictExgWjSeqNumByWjCode(dictEmsNo, wjCode);
	}

	/**
	 * 获得最大的料件序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxDictPorImgSeqNum(Request request, BcsDictPorHead head) {
		return this.wjszDao.getMaxDictPorImgSeqNum(head);
	}

	/**
	 * 获得最大的成品序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxDictPorExgSeqNum(Request request, BcsDictPorHead head) {
		return this.wjszDao.getMaxDictPorExgSeqNum(head);
	}

	/**
	 * 获取DzscEmsImgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsImgWjNum(Request request, DzscEmsPorWjHead head) {
		return this.wjszDao.getMaxDzscEmsImgWjNum(head);
	}

	/**
	 * 获取DzscEmsExgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsExgWjNum(Request request, DzscEmsPorWjHead head) {
		return this.wjszDao.getMaxDzscEmsExgWjNum(head);
	}
}
