package com.bestway.common.fpt.logic;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.fpt.dao.FptManageDao;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.qp.action.FptQpAction;
import com.bestway.common.fpt.qp.action.FptQpServiceClient;
import com.bestway.common.fpt.qp.entity.FptAppHeadByQp;
import com.bestway.common.fpt.qp.entity.FptAppItemByQp;
import com.bestway.common.fpt.qp.entity.FptBillItemByQp;

/**
 * 转厂QP下载逻辑
 * 
 * @author luosheng
 * 2009/09/07
 * 
 */
public class FptQpLogic {

	/** 转厂管理Dao */
	private FptManageDao fptManageDao = null;
	/** log4j */
	private static final Logger log = Logger.getLogger(FptQpLogic.class);

	/** 获得 QP 访问入口对象 */
	private FptQpAction getFptQpAction() {
		FptParameterSet parameterSet = this.fptManageDao.findFptParameterSet();
		FptQpAction fptQpAction = FptQpServiceClient
				.getFptQpAction(parameterSet);
		return fptQpAction;
	}

	/**
	 * 下载收货单出口
	 * 
	 * @param taskId
	 * @param fptAppHead
	 * @return
	 */
	public List downloadFptBillItemsOutByQp(String taskId,
			FptBillHead fptBillHead) {
		return null;
	}

	/**
	 * 获得转厂管理 DAO
	 */
	public FptManageDao getFptManageDao() {
		return fptManageDao;
	}

	/**
	 * 设置转厂管理 DAO
	 */
	public void setFptManageDao(FptManageDao fptManageDao) {
		this.fptManageDao = fptManageDao;
	}

	/**
	 * 下载出口明细
	 * 
	 * 在转厂申请表模块 [转入方] 加入单笔从QP系统导入转出方的资料, 1.用户选择转入申请表 2.点击 [下载转出方申请表数据] 导入按钮
	 * 3.以[申请表编号]为条件新增导入转出方申请表数据, 4.以[申请表编号]为条件覆盖导入转出方申请表数据 ( 先删除以前所有数据,导入最新数据 )
	 * 
	 * @param fptAppHead
	 * @return result = List<FptAppItem> 比QP的代码本人认为要更为高明 用进程任务相对于反回信息,要更高明一些
	 */
	public List<FptAppItem> downloadFptAppItemsOutByQp(String taskId,
			FptAppHead fptAppHead) {
		//
		// 服务端进程任务
		//
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);

		String outTradeCode = fptAppHead.getOutTradeCode();
		String outCopAppNo = fptAppHead.getOutCopAppNo();
		FptQpAction fptQpAction = this.getFptQpAction();
		List itemsByQp = fptQpAction.findFptAppItemOutByQp(outTradeCode,
				outCopAppNo);
		if (itemsByQp != null) {
			progressInfo.setHintMessage("从QP成功下载转厂出口明细数据有 " + itemsByQp.size()
					+ " 条记录");
		}
		//
		// cast map to type
		//
		List<FptAppItemByQp> items = this.castListType(itemsByQp,
				FptAppItemByQp.class);
		//
		// 先删除以前所有数据,导入最新数据
		//
		List list = this.fptManageDao.findFptAppItems(fptAppHead.getId());
		for (int i = 0; i < list.size(); i++) {
			FptAppItem data = (FptAppItem) list.get(i);
			this.fptManageDao.deleteFptAppItem(data);
			progressInfo.setHintMessage("成功删除转厂出口明细序号 [ " + data.getListNo()
					+ " ] 的记录");
		}
		//
		// 保存
		//
		List<FptAppItem> returnList = saveFptAppItems(fptAppHead, items);
		progressInfo.setHintMessage("成功保存所有出口申请单明细下载记录");
		return returnList;
	}

	/**
	 * 下载进口明细
	 * 
	 * .在转厂申请表模块 [转出方] 加入单笔从QP系统导入转入方的资料 1.用户选择已审核通过的转出申请表 2.点击 [下载转入方申请表数据]
	 * 导入按钮, 3.以[申请表编号]为条件新增导入转入方申请表数据, 4.以[申请表编号]为条件覆盖导入转入方申请表数据 (
	 * 先删除以前所有数据,导入最新数据 )
	 * 
	 * @param fptAppHead
	 * @return result = List<FptAppItem> 比QP的代码本人认为要更为高明 用进程任务相对于反回信息,要更高明一些
	 */
	public List<FptAppItem> downloadFptAppItemsInByQp(String taskId,
			FptAppHead fptAppHead) {
		//
		// 服务端进程任务
		//
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);

		String outTradeCode = fptAppHead.getOutTradeCode();
		String outCopAppNo = fptAppHead.getOutCopAppNo();
		FptQpAction fptQpAction = this.getFptQpAction();
		List itemsByQp = fptQpAction.findFptAppItemOutByQp(outTradeCode,
				outCopAppNo);
		if (itemsByQp != null) {
			progressInfo.setHintMessage("从QP成功下载转厂进口明细数据有 " + itemsByQp.size()
					+ " 条记录");
		}
		//
		// cast map to type
		//
		List<FptAppItemByQp> items = this.castListType(itemsByQp,
				FptAppItemByQp.class);
		//
		// 先删除以前所有数据,导入最新数据
		//
		List list = this.fptManageDao.findFptAppItems(fptAppHead.getId());
		for (int i = 0; i < list.size(); i++) {
			FptAppItem data = (FptAppItem) list.get(i);
			this.fptManageDao.deleteFptAppItem(data);
			progressInfo.setHintMessage("成功删除转厂进口明细序号 [ " + data.getListNo()
					+ " ] 的记录");
		}
		//
		// 保存
		//
		List<FptAppItem> returnList = saveFptAppItems(fptAppHead, items);
		progressInfo.setHintMessage("成功保存所有进口申请单明细下载记录");
		return returnList;
	}

	/**
	 * 转换对象并保存
	 * 
	 * @param fptAppHead
	 * @param itemsByQp
	 * @return
	 */
	private List<FptAppItem> saveFptAppItems(FptAppHead fptAppHead,
			List<FptAppItemByQp> itemsByQp) {
		//
		// 返回对象
		//
		List<FptAppItem> returnList = new ArrayList<FptAppItem>();
		for (FptAppItemByQp p : itemsByQp) {
			FptAppItem n = new FptAppItem();
			// n.setCodeTs(codeTS)
			n.setListNo(p.getListNo());
			n.setCompany(CommonUtils.getCompany());
			n.setFptAppHead(fptAppHead);
			n.setInEmsNo(p.getInEmsNo());
			n.setInOutFlag(p.getInOutFlag());
			n.setModifyMarkState(ModifyMarkState.ADDED);
			n.setName(p.getName());
			n.setSpec(p.getSpec());
			n.setNote(p.getNote());
			n.setQty(p.getQty());
			n.setQty1(p.getQty1());
			// n.setStandComplex(null);
			n.setTrNo(p.getTrNo());
			// n.setUnit(i.getUnit());
			// n.setUnit1(i.getUnit1());
			n.setInOutNo(p.getInOutNo());
			this.fptManageDao.saveFptAppItem(n);
			returnList.add(n);
		}
		return returnList;
	}

	/**
	 * 获得 正在处理 的转出申请表 来自海关编码没有明细 在转厂申请表模块 [转入方] 加入按
	 * [对方公司海关编码]从QP系统查询已审核通的的转厂申请表, 用户可以选取,批量从QP导入,不提供覆盖导入
	 * 
	 * @param outTradeCode
	 * @return
	 */
	public List<FptAppHead> findFptAppHeadByQp(String outTradeCode) {
		FptParameterSet parameterSet = this.fptManageDao.findFptParameterSet();
		StringBuffer stringBuffer = new StringBuffer();
		if (parameterSet.getProjectType() == null) {
			stringBuffer.append("请到转厂管理参数设置中,先设置项目类型\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}

		FptQpAction fptQpAction = this.getFptQpAction();
		List headsByQp = fptQpAction.findFptAppHeadByQpNoList(outTradeCode);
		// if (headsByQp != null) {
		// progressInfo.setHintMessage("从QP成功下载转出申请表头数据有 " + headsByQp.size()
		// + " 条记录");
		// }
		//
		// cast map to type
		//
		List<FptAppHeadByQp> heads = this.castListType(headsByQp,
				FptAppHeadByQp.class);
		//
		// 保存
		//
		List<FptAppHead> returnList = new ArrayList<FptAppHead>();
		for (FptAppHeadByQp f : heads) {
			FptAppHead h = new FptAppHead();
			h.setCompany(CommonUtils.getCompany());
			h.setAclUser(CommonUtils.getAclUser());
			h.setAppClass(f.getAppClass());
			h.setAppNo(f.getAppNo());
			h.setSeqNo(f.getSeqNo());
			h.setContrNo(f.getContrNo());
			h.setEmsNo(f.getEmsNo());
			h.setInOutFlag(f.getInOutFlag());
			//
			// 出口表头信息
			//
			h.setOutAgentCode(f.getOutAgentCode());
			h.setOutAgentName(f.getOutAgentName());
			h.setOutCopAppNo(f.getOutCopAppNo());
			h.setOutCorp(f.getOutCorp());
			// h.setOutCustoms(f.getOutCustoms());
			String outDate = f.getOutDate();
			if (outDate != null && !outDate.equals("")) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date date = java.sql.Date.valueOf(dateFormat
							.format(outDate));
					h.setOutDate(date);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			String inDate = f.getInDate();
			if (inDate != null && !inDate.equals("")) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					Date date = java.sql.Date.valueOf(dateFormat
							.format(outDate));
					h.setInDate(date);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			h.setOutDecl(f.getOutDecl());
			// h.setOutDistrict(f.getOutDistrict());
			h.setOutLiceNo(f.getOutLiceNo());
			h.setOutNote(f.getOutNote());
			h.setOutTradeCode(f.getOutTradeCode());
			h.setOutTradeCode2(f.getOutTradeCode2());
			h.setOutTradeName(f.getOutTradeName());
			h.setOutTradeName2(f.getOutTradeName2());
			// h.setScmCoc(scmCoc);
			// h.setConveyDay(f.getConveyDay().intValue());
			// h.setConveySpa(f.getConveySpa().intValue());
			h.setDelcareType(DelcareType.APPLICATION);
			h.setModifyMarkState(ModifyMarkState.ADDED);
			h.setDeclareState(DeclareState.APPLY_POR);
			h.setProjectType(parameterSet.getProjectType());
			returnList.add(h);
		}
		// progressInfo.setHintMessage("成功获得下载记录");
		return returnList;

	}

	/**
	 * 获得 正在处理 的转出申请表 来自海关编码没有明细 在转厂申请表模块 [转入方] 加入按
	 * [对方公司海关编码]从QP系统查询已审核通的的转厂申请表, 用户可以选取,批量从QP导入,不提供覆盖导入
	 * 
	 * save 转出申请表 来自海关编码 有明细
	 * 
	 * @param outTradeCode
	 * @param outCopAppNos
	 * @return
	 */
	public List<FptAppHead> downloadFptAppHeadsOutByQp(String taskId,
			String outTradeCode, List<FptAppHead> fptAppHeads) {

		//
		// 服务端进程任务
		//
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);

		List<String> outCopAppNos = new ArrayList<String>();
		Map<String, FptAppHead> map = new HashMap<String, FptAppHead>();
		for (FptAppHead f : fptAppHeads) {
			outCopAppNos.add(f.getOutCopAppNo());
			map.put(f.getOutCopAppNo(), f);
		}
		FptQpAction fptQpAction = this.getFptQpAction();
		List headsByQp = fptQpAction.findFptAppHeadByQpHasList(outTradeCode,
				outCopAppNos);
		if (headsByQp != null) {
			progressInfo.setHintMessage("从QP成功下载转出申请表数据有 " + headsByQp.size()
					+ " 条记录");
		}
		//
		// cast map to type
		//
		List<FptAppHeadByQp> heads = this.castListType(headsByQp,
				FptAppHeadByQp.class);

		for (FptAppHeadByQp p : heads) {
			String outCopAppNo = p.getOutCopAppNo();
			FptAppHead f = map.get(outCopAppNo);
			if (f == null) {
				continue;
			}
			this.fptManageDao.saveFptAppHead(f);
			//
			// 保存
			//
			//
			// cast map to type
			//
			List<FptAppItemByQp> items = this.castListType(p.getItems(),
					FptAppItemByQp.class);

			List<FptAppItem> returnList = saveFptAppItems(f, items);
			progressInfo.setHintMessage("成功保存转出申请表数据申请表编号 [" + p.getAppNo()
					+ " ] 的记录");
		}
		//
		// return
		//
		return fptAppHeads;
	}

	
	/** 转换类型 */
	private List castListType(List itemsByQp, Class clazz) {

		List items = new ArrayList();
		for (Object obj : itemsByQp) {
			Map map = (Map) obj;
			Iterator iterator = map.entrySet().iterator();
			// FptAppItemByQp item = new FptAppItemByQp();
			Object item = new Object();
			try {
				item = clazz.newInstance();
			} catch (Exception ex){
				ex.printStackTrace();
//				log.error(ex.getMessage(), ex);
			}
			for (; iterator.hasNext();) {
				Map.Entry<String, Object> e = (Map.Entry) iterator.next();
				String name = e.getKey();
				Object value = e.getValue();
				try {
					PropertyUtils.setProperty(item, name, value);
				} catch (Exception e1) {
					e1.printStackTrace();
//					log.error(e1.getMessage(), e1);
				} 
			}
			items.add(item);
		}
		return items;
	}
	
	
//	/** 转换类型 */
//	private List castListType2(List itemsByQp, Class clazz) {
//
//		
//		for(Field f:clazz.getFields()){
//			Class<?> cls = f.getDeclaringClass();
//			cls.getTypeParameters()
//		}
//		List items = new ArrayList();
//		for (Object obj : itemsByQp) {
//			Map map = (Map) obj;
//			Iterator iterator = map.entrySet().iterator();
//			// FptAppItemByQp item = new FptAppItemByQp();
//			Object item = new Object();
//			try {
//				item = clazz.newInstance();
//			} catch (Exception ex){
//				ex.printStackTrace();
////				log.error(ex.getMessage(), ex);
//			}
//			for (; iterator.hasNext();) {
//				Map.Entry<String, Object> e = (Map.Entry) iterator.next();
//				String name = e.getKey();
//				Object value = e.getValue();
//				try {
//					PropertyUtils.setProperty(item, name, value);
//				} catch (Exception e1) {
//					e1.printStackTrace();
////					log.error(e1.getMessage(), e1);
//				} 
//			}
//			items.add(item);
//		}
//		return items;
//	}
	
	

	/**/// //////////////////////以下是转厂收退货单//////////////////////////**/
	/**
	 * 下载收货单进口所转出资料
	 * 
	 * @param taskId
	 * @param fptAppHead
	 * @return
	 */
	public List downloadFptBillItemsInByQp(String taskId,
			FptBillHead fptBillHead) {
		//
		// 服务端进程任务
		//
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);
        String issueTradeCod=fptBillHead.getIssueTradeCod();// 发货企业编码
		String receiveTradeCod = fptBillHead.getReceiveTradeCod();//发货企业编码
		FptQpAction fptQpAction = this.getFptQpAction();
		List itemsByQp = fptQpAction.findFptBillItemOutByQp(issueTradeCod,receiveTradeCod);
		if (itemsByQp != null) {
			progressInfo.setHintMessage("从QP成功下载转厂出口明细数据有 " + itemsByQp.size()
					+ " 条记录");
		}
		//
		// cast map to type
		//
		List<FptBillItemByQp> items = this.castListType(itemsByQp,
				FptBillItemByQp.class);
		//
		// 先删除以前所有数据,导入最新数据
		//
		List list = this.fptManageDao.findFptBillItemByParentId(fptBillHead
				.getId());
		for (int i = 0; i < list.size(); i++) {
			FptBillItem data = (FptBillItem) list.get(i);
			this.fptManageDao.deleteFptBillItem(data);
			progressInfo.setHintMessage("成功删除转厂出口明细序号 [ " + data.getListNo()
					+ " ] 的记录");
		}
		//
		// 保存
		//
		List returnList = saveFptBillItems(fptBillHead, items);
		progressInfo.setHintMessage("成功保存所有出口申请单明细下载记录");
		return returnList;
	}

	/**
	 * 转换对象并保存
	 * 
	 * @param fptAppHead
	 * @param itemsByQp
	 * @return
	 */
	private List saveFptBillItems(FptBillHead fptBillHead,
			List<FptBillItemByQp> itemsByQp) {
		//
		// 返回对象
		//
		List returnList = new ArrayList();
		for (FptBillItemByQp p : itemsByQp) {
			FptBillItem n = new FptBillItem();
			// n.setCodeTs(codeTS)
			n.setListNo(p.getListNo());
			n.setCompany(CommonUtils.getCompany());
			n.setFptBillHead(fptBillHead);
			n.setInEmsNo(p.getInEmsNo());
			n.setBillSort(p.getBillSort());
			n.setCopGName(p.getCopGName());
			n.setCopGModel(p.getCopGModel());
			n.setQty(p.getQty());
			n.setTradeQty(p.getTradeQty());
			// n.setStandComplex(null);
			n.setTrGno(p.getTrGno());
			// n.setUnit(i.getUnit());
			// n.setUnit1(i.getUnit1());
			n.setOutNo(p.getOutNo());
			n.setNote(p.getNote());
			this.fptManageDao.saveFptBillItem(n);
			returnList.add(n);
		}
		return returnList;
	}
}
