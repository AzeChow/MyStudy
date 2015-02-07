package com.bestway.waijing.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.waijing.entity.WjszParaSet;

public class WjszDao extends BaseDao {

	/**
	 * 保存实体对象
	 * @param obj
	 * @return
	 */
	public Object saveObj(Object obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	
	/**
	 * 根据类名和编码查询海关基础资料实体
	 * @param clsName
	 * @param codeField
	 * @param codeValue
	 * @return
	 */
	public Object findCustomsBaseEntity(Class<?> clsName,String codeField,String codeValue){
		String hsql="select a from "+clsName.getName()+" as a where a."+codeField+"=? ";
		List<Object> parameters=new ArrayList<Object>();
		parameters.add(codeValue);
		List<?> list=this.find(hsql,parameters.toArray());
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List saveListObj(List ls){
		for (int i=0;i<ls.size();i++){
			this.saveOrUpdate(ls.get(i));
		}
		return ls;
	}
	
	/**
	 * 保存参数设置
	 * @param paraset
	 */
	public void saveWjszParaSet(WjszParaSet  paraset){
		this.saveOrUpdate(paraset);
	}
	/**
	 * 查询参数设置
	 * @return
	 */
	public WjszParaSet findWjszParaSet(){
		String hsql="select a from WjszParaSet a where a.company.id=?";
		List<Object> parameters=new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		List list= this.find(hsql, parameters.toArray());
		if(list.size()>0){
			return (WjszParaSet)list.get(0);
		}else{
			WjszParaSet wjszParaSet=new WjszParaSet();
			this.saveOrUpdate(wjszParaSet);
			return wjszParaSet;
		}
	}
	

	/**
	 * 抓取电子化手册备案资料库备案中料件的外经编号
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findBcsImgWjCode(String dictEmsNo,Integer seqNum){
		List list= this.find(" select distinct a.wjCode from BcsDictPorImg as a "
				+ "  where a.dictPorHead.dictPorEmsNo=? and a.seqNum=? ",//and a.dictPorHead.declareState=? 
				new Object[] { dictEmsNo,  seqNum});//DeclareState.PROCESS_EXE,
//		if(list.size()>0&&list.get(0)!=null){
//			return list.get(0).toString().trim();
//		}
			for(int i=0;i<list.size();i++){
				if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
					return list.get(i).toString().trim();
				}
			}
		return null;
	}
	
	/**
	 * 抓取电子化手册备案资料库备案中成品的外经编号
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findBcsExgWjCode(String dictEmsNo,Integer seqNum){
		List list= this.find(" select distinct a.wjCode from BcsDictPorExg as a "
				+ "  where a.dictPorHead.dictPorEmsNo=?  and a.seqNum=? ",//and a.dictPorHead.declareState=?
				new Object[] { dictEmsNo,  seqNum});//DeclareState.PROCESS_EXE,
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return list.get(i).toString().trim();
			}
		}
		return null;
	}
	/**
	 * 抓取电子手册合同备案中料件的外经编号
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findDzscImgWjCode(String wjHeadEmsNo,Integer seqNum){
		List list= this.find(" select distinct a.wjCode from DzscEmsImgWj as a "
				+ "  where a.dzscEmsPorWjHead.emsNo=? and a.seqNum=? ",//and a.dzscEmsPorWjHead.declareState=? 
				new Object[] { wjHeadEmsNo, seqNum});//DzscState.EXECUTE, 
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return list.get(i).toString().trim();
			}
		}
		return null;
	}
	
	/**
	 * 抓取电子手册合同备案中成品的外经编号
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public String findDzscExgWjCode(String wjHeadEmsNo,Integer seqNum){
		List list= this.find(" select distinct a.wjCode from DzscEmsExgWj as a "
				+ "  where a.dzscEmsPorWjHead.emsNo=? and a.seqNum=? ",// and a.dzscEmsPorWjHead.declareState=?
				new Object[] { wjHeadEmsNo, seqNum});// DzscState.EXECUTE,
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return list.get(i).toString().trim();
			}
		}
		return null;
	}
	
	/**
	 * 电子手册通关手册成品表OK!
	 */
	public List findDzscExg(DzscEmsPorHead porHead){
		return this.find("select a from DzscEmsExgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?",
						new Object[]{CommonUtils.getCompany().getId(),porHead.getId()});
	}
	
	/**
	 * 电子手册通关手册料件总表OK!
	 */
	public List findDzscImg(DzscEmsPorHead porHead){
		return this.find("select a from DzscEmsImgBill a where a.company.id = ? and a.dzscEmsPorHead.id = ?",
				new Object[]{CommonUtils.getCompany().getId(),porHead.getId()});
	}
	public List findEmsPorBomBillByHead(DzscEmsPorHead head) {
		return this.find("select a from DzscEmsBomBill a where a.dzscEmsExgBill.dzscEmsPorHead.id = ?",
				new Object[] { head.getId() });
	}
	
	//OK!
	public List findContractImgByContract(Contract head) {
		return this.find("select a from ContractImg a where a.contract.id = ? "
				+ " order by a.seqNum ", new Object[] { head.getId()});
	}
	//OK!
	public List findContractExgByContract(Contract head) {
		return this.find("select a from ContractExg a where a.contract.id = ?  and a.company.id=? "
						+ " order by a.seqNum ", new Object[] { head.getId(),
						CommonUtils.getCompany().getId() });
	}
	
	//OK!
	public List findContractBomByContract(Contract head) {
		return this.find("select a from ContractBom a left join a.contractExg"
				+ " where a.contractExg.contract.id = ?  and a.company.id=? "
				+ "  order by a.createDate ", new Object[] { head.getId(),
				CommonUtils.getCompany().getId() });
	}
	
	//保存ok!
	public void saveModuleSelect(int i){
		WjszParaSet obj = null;
	   obj = findWjszParaSet();
	   if (obj == null){
		   obj = new WjszParaSet();
	   }
	   obj.setModule(Integer.valueOf(i));
	   obj.setCompany(CommonUtils.getCompany());
	   this.saveOrUpdate(obj);
	}
	/**
	 * ok!
	 * @param i
	 */
	public void saveConModuleSelect(int i){
		WjszParaSet obj = null;
		   obj = findWjszParaSet();
		   if (obj == null){
			   obj = new WjszParaSet();
		   }
		   obj.setConModule(Integer.valueOf(i));
		   obj.setCompany(CommonUtils.getCompany());
		   this.saveOrUpdate(obj);
		}
	
	
	//查找模式ok!
	public Integer getModule(){
		List ls = this.find("select a from WjszParaSet a where a.company.id = ?",
				new Object[]{CommonUtils.getCompany().getId()});
		if (ls != null && ls.size() >0){
			WjszParaSet obj = (WjszParaSet) ls.get(0);
			return obj.getModule() == null ?0:obj.getModule();
		}
		return Integer.valueOf(0);
	}
	
	
    //查找模式ok!
	public Integer getConModule(){
		List ls = this.find("select a from WjszParaSet a where a.company.id = ?",
				new Object[]{CommonUtils.getCompany().getId()});
		if (ls != null && ls.size() >0){
			WjszParaSet obj = (WjszParaSet) ls.get(0);
			return obj.getConModule() == null ?0:obj.getConModule();
		}
		return Integer.valueOf(0);
	}
	/**
	 * 电子手册合同备案表头
	 * @return
	 */
	public List findBcusWjHead(){
		/*return this.find("select a from DzscEmsPorWjHead a where a.company.id = ?  and " +
				" (a.declareState = ? or a.declareState = ?) order by a.seqNum",
				new Object[]{CommonUtils.getCompany().getId(),DzscState.ORIGINAL,DzscState.CHANGE});*/
		
		return this.find("select a from EmsEdiTrHead a where a.company.id = ?",
				new Object[]{CommonUtils.getCompany().getId()});
	}
	
	/**
	 * 电子手册合同备案表头
	 * @return
	 */
	public List findDzscWjHead(){
		/*return this.find("select a from DzscEmsPorWjHead a where a.company.id = ?  and " +
				" (a.declareState = ? or a.declareState = ?) order by a.seqNum",
				new Object[]{CommonUtils.getCompany().getId(),DzscState.ORIGINAL,DzscState.CHANGE});*/
		
		return this.find("select a from DzscEmsPorWjHead a where a.company.id = ?  order by a.seqNum",
				new Object[]{CommonUtils.getCompany().getId()});
	}
	
	public List findBcsDictPorHead() {
		/*List ls = this.find("select a from BcsDictPorHead a where a.company.id=?"
				+ " and (a.declareState = ? or a.declareState = ?) order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(),DeclareState.APPLY_POR, DeclareState.CHANGING_EXE });
		return ls;*/
		
		List ls = this.find("select a from BcsDictPorHead a where a.company.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId() });
		return ls;
	}
	
	/**
	 * 电子手册表头
	 * @return
	 */
	public List findDzscEmsPorHead(){
		/*return this.find("select a from DzscEmsPorHead a where a.company.id = ?  and " +
				" (a.declareState = ? or a.declareState = ?) order by a.seqNum",
				new Object[]{CommonUtils.getCompany().getId(),DzscState.ORIGINAL, DzscState.CHANGE});*/
		return this.find("select a from DzscEmsPorHead a where a.company.id = ?  order by a.seqNum",
				new Object[]{CommonUtils.getCompany().getId()});
	}

	/**
	 * 电子化手册表头
	 */
	public List findContractHead(){
		/*return this.find("select a from Contract a where a.company= ? and ( a.isCancel=? ) and (a.declareState=? or a.declareState=?)",
							new Object[] { CommonUtils.getCompany(),new Boolean(false), DeclareState.APPLY_POR,DeclareState.CHANGING_EXE });*/
		return this.find("select a from Contract a where a.company= ? and ( a.isCancel=? )",
				new Object[] { CommonUtils.getCompany(),new Boolean(false) });
	}
	
	public DzscEmsPorHead saveDzscEmsPorHead(DzscEmsPorHead obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public Contract saveContract(Contract obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public DzscEmsImgWj saveDzscEmsImgWj(DzscEmsImgWj obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public DzscEmsExgWj saveDzscEmsExgWj(DzscEmsExgWj obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public DzscEmsImgBill saveDzscEmsImgBill(DzscEmsImgBill obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public DzscEmsExgBill saveDzscEmsExgBill(DzscEmsExgBill obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public BcsDictPorImg saveBcsDictPorImg(BcsDictPorImg obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public BcsDictPorExg saveBcsDictPorExg(BcsDictPorExg obj){
		this.saveOrUpdate(obj);
		return obj;
	}
	public List findDzscEmsImgWj(DzscEmsPorWjHead chead){
		return this.find("select a from DzscEmsImgWj a where a.company.id = ? and a.dzscEmsPorWjHead.id = ?",
				new Object[]{CommonUtils.getCompany().getId(),chead.getId()});
	}
	public List findDzscEmsExgWj(DzscEmsPorWjHead chead){
		return this.find("select a from DzscEmsExgWj a where a.company.id = ? and a.dzscEmsPorWjHead.id = ?",
				new Object[]{CommonUtils.getCompany().getId(),chead.getId()});
	}
	public List findEmsEdiTrImg(EmsEdiTrHead head){
//		return this.find("select a from EmsEdiTrImg a where a.company.id = ? and a.emsEdiTrHead.historyState = ? " +
//				" and (a.emsEdiTrHead.declareState = ? or a.emsEdiTrHead.declareState = ?)",
//				new Object[]{CommonUtils.getCompany().getId(),new Boolean(false),DeclareState.APPLY_POR,DeclareState.CHANGING_EXE});
		return this.find("select a from EmsEdiTrImg a where a.company.id = ? and a.emsEdiTrHead.id = ? ",
				new Object[]{CommonUtils.getCompany().getId(),head.getId()});
	}
	public List findEmsEdiTrExg(EmsEdiTrHead head){
//		return this.find("select a from EmsEdiTrExg a where a.company.id = ? and a.emsEdiTrHead.historyState = ? " +
//				" and (a.emsEdiTrHead.declareState = ? or a.emsEdiTrHead.declareState = ?)",
//				new Object[]{CommonUtils.getCompany().getId(),new Boolean(false),DeclareState.APPLY_POR,DeclareState.CHANGING_EXE});
		return this.find("select a from EmsEdiTrExg a where a.company.id = ? and a.emsEdiTrHead.id = ? ",
				new Object[]{CommonUtils.getCompany().getId(),head.getId()});
	}

	public List findBcsDictPorImg(BcsDictPorHead porHead) {
		return this.find("select a from BcsDictPorImg a where a.company.id=?"
				+ " and a.dictPorHead.id = ? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(),porHead.getId() });
	}
	public List findBcsDictPorExg(BcsDictPorHead porHead) {
		return this.find("select a from BcsDictPorExg a where a.company.id=?"
				+ " and a.dictPorHead.id = ? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(),porHead.getId() });
	}
	
	public List findContractBomByExgId(ContractExg exg) {
		return this.find("select a from ContractBom a left join a.contractExg"
				+ " where a.contractExg.id = ?  and a.company.id=? "
				+ "  order by a.createDate ", new Object[] { exg.getId(),
				CommonUtils.getCompany().getId() });
	}
	/**
	 * 电子手册通关手册单耗表
	 */
	public List findEmsPorBomBill(DzscEmsExgBill exg) {
		return this.find("select a from DzscEmsBomBill a where a.dzscEmsExgBill.id = ?",
				new Object[] { exg.getId() });
	}
	
	/**
	 * 根据凭证序号抓取电子手册合同备案中料件的备案序号
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findDzscImgWjSeqNumByWjCode(String wjHeadEmsNo,String wjCode){
		List list= this.find(" select distinct a.seqNum from DzscEmsImgWj as a "
				+ "  where a.dzscEmsPorWjHead.emsNo=? and a.wjCode=? ",//and a.dzscEmsPorWjHead.declareState=? 
				new Object[] { wjHeadEmsNo, wjCode});//DzscState.EXECUTE, 
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return Integer.valueOf(list.get(i).toString().trim());
			}
		}
		return null;
	}
	
	/**
	 * 根据凭证序号抓取电子手册合同备案中成品的备案序号
	 * @param wjHeadEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findDzscExgWjSeqNumByWjCode(String wjHeadEmsNo,String wjCode){
		List list= this.find(" select distinct a.seqNum from DzscEmsExgWj as a "
				+ "  where a.dzscEmsPorWjHead.emsNo=? and a.wjCode=? ",//and a.dzscEmsPorWjHead.declareState=? 
				new Object[] { wjHeadEmsNo, wjCode});//DzscState.EXECUTE, 
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return Integer.valueOf(list.get(i).toString().trim());
			}
		}
		return null;
	}
	
	/**
	 * 根据凭证序号抓取电子化手册中备案资料库料件的备案序号
	 * @param dictEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findBcsDictImgWjSeqNumByWjCode(String dictEmsNo,String wjCode){
		List list= this.find(" select distinct a.seqNum from BcsDictPorImg as a "
				+ "  where a.dictPorHead.dictPorEmsNo=? and a.wjCode=? ",//and a.dzscEmsPorWjHead.declareState=? 
				new Object[] { dictEmsNo, wjCode});//DzscState.EXECUTE, 
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return Integer.valueOf(list.get(i).toString().trim());
			}
		}
		return null;
	}
	/**
	 * 根据凭证序号抓取电子化手册中备案资料库料件的备案序号
	 * @param dictEmsNo
	 * @param seqNum
	 * @return
	 */
	public Integer findBcsDictExgWjSeqNumByWjCode(String dictEmsNo,String wjCode){
		List list= this.find(" select distinct a.seqNum from BcsDictPorExg as a "
				+ "  where a.dictPorHead.dictPorEmsNo=? and a.wjCode=? ",//and a.dzscEmsPorWjHead.declareState=? 
				new Object[] { dictEmsNo, wjCode});//DzscState.EXECUTE, 
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null&&!"".equals(list.get(i).toString().trim())){
				return Integer.valueOf(list.get(i).toString().trim());
			}
		}
		return null;
	}
	
	/**
	 * 获得最大的料件序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxDictPorImgSeqNum(BcsDictPorHead head) {
		List list = this
				.find("select max(a.seqNum) from BcsDictPorImg as a "
						+ " where a.company.id=? and a.dictPorHead.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}
	
	/**
	 * 获得最大的成品序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxDictPorExgSeqNum(BcsDictPorHead head) {
		List list = this
				.find("select max(a.seqNum) from BcsDictPorExg as a "
						+ " where a.company.id=? and a.dictPorHead.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
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
			return Integer.parseInt(num);
		}
		return 0;
	}
	
	/**
	 * 获取DzscEmsExgWj里最大的流水号
	 * 
	 * @return Sting className里最大的流水号
	 */
	public Integer getMaxDzscEmsExgWjNum(DzscEmsPorWjHead head) {
		List list = this.find("select max(a.seqNum) from DzscEmsExgWj as a "
				+ " where a.dzscEmsPorWjHead.id=? "
				+ " and a.dzscEmsPorWjHead.company= ?", new Object[] {
				head.getId(), CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			String num = list.get(0).toString();
			return Integer.parseInt(num);
		}
		return 0;
	}
}
