package com.bestway.bcus.cas.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.dao.CasLeftoverMaterielDao;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BalanceInfo2;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;

public class BalanceInfoLogic2 {
	private static final Log logger = LogFactory
	.getLog(ImgOrgUseInfoLogic.class);
	
	private BalanceInfoLogic balanceInfoLogic = null;

	private CasLeftoverMaterielDao casLeftoverMaterielDao = null;

	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @return 短溢表计算结果
	 */
	public List<BalanceInfo2> findBalanceInfo2(Date beginDate, Date endDate,
			ProjectTypeParam projectTypeParam, String taskId,
			boolean isUpdateData, boolean isFromCheckData) {
		ProgressInfo progressInfo = ProcExeProgress.getInstance().getProgressInfo(taskId);
		
		List<BalanceInfo2> resultList = new ArrayList<BalanceInfo2>();
		
		String clientTipMessage = "开始计算来源于单据数据的短溢表... !!";
		
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		
		List<BalanceInfo> fromBillList = this.balanceInfoLogic.
										findBalanceInfo(null, endDate, projectTypeParam, taskId, false);

//		this.casLeftoverMaterielDao.deleteBalanceInfo();
//		for (int i = 0, n = fromBillList.size(); i < n; i++) {
//			BalanceInfo item = (BalanceInfo) fromBillList.get(i);				
//			item.setCompany(CommonUtils.getCompany());
//			this.casLeftoverMaterielDao.getHibernateTemplate().save(item);
////			this.casDao.getHibernateTemplate().save(item);
//		}
		clientTipMessage = "单据计算的短溢表进行最后计算... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		for(BalanceInfo item : fromBillList){
			BalanceInfo2 tmp = new BalanceInfo2();
			tmp.setName(item.getName());
			tmp.setSpec(item.getSpec());
			tmp.setUnitName(item.getUnitName());
			tmp.setF1(item.getF5());
			resultList.add(tmp);
		}
		
		clientTipMessage = "开始计算来源于盘点数据的短溢表... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		
		List<BalanceInfo> fromCheckList = this.balanceInfoLogic.
					findBalanceInfo(beginDate, endDate, projectTypeParam, taskId, true);
		
		clientTipMessage = "来源于盘点数据的短溢表进行最后计算... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		for(int i = 0;i<fromCheckList.size();i++){
			BalanceInfo tmp = fromCheckList.get(i);
			BalanceInfo2 returnTmp = resultList.get(i);
			returnTmp.setF2(tmp.getF5());
			returnTmp.setF3(CommonUtils.getDoubleExceptNull(returnTmp.getF1())
					-CommonUtils.getDoubleExceptNull(returnTmp.getF2()));
		}
//		//
//		// reslut list is resultList & existReslutList 并集
//		//
//		List<BalanceInfo2> reslutList = new ArrayList<BalanceInfo2>();
//		List<BalanceInfo2> existReslutList = this.casLeftoverMaterielDao
//				.findBalanceInfo2();
//
//		List<BalanceInfo> list = this.balanceInfoLogic.findBalanceInfo(
//				beginDate, endDate, projectTypeParam, taskId, isFromCheckData);
//		Map<String, BalanceInfo> existMap = new HashMap<String, BalanceInfo>();
//		for (BalanceInfo balanceInfo : list) {
//			//
//			// key = name + spec + unitName
//			//			
//			existMap.put(balanceInfo.getKey(), balanceInfo);
//		}
//
//		//
//		// 更新已存在的数
//		//
//		boolean flag = isUpdateData;
//		for (BalanceInfo2 balanceInfo2 : existReslutList) {
//			String key = balanceInfo2.getKey();
//			BalanceInfo balanceInfo = existMap.get(key);
//			if (balanceInfo != null) {
//				//
//				// 是否更新盘点监管数据
//				//
//				if (flag == true) {
//					balanceInfo2.setF1A(balanceInfo.getF1());
//					balanceInfo2.setF2A(balanceInfo.getF2());
//					balanceInfo2.setF3A(balanceInfo.getF3());
//				}
//				// //////////////////////////////////////
//				// 转厂信息
//				//
//				balanceInfo2.setF5(balanceInfo.getF9());
//				//
//				// (内购)实际进料
//				//
//				balanceInfo2.setF5A(new Double(0.0));
//				//
//				// (内购)已结案合同发票核销数
//				//
//				balanceInfo2.setF5B(new Double(0.0));
//
//				balanceInfo2.setF6(balanceInfo.getF8());
//				balanceInfo2.setF7(balanceInfo.getF7());
//				balanceInfo2.setF8(balanceInfo.getF6());
//				//
//				// /////////////////////////////////////////
//
//				balanceInfo2.setF10(balanceInfo.getF10());
//				balanceInfo2.setF11(balanceInfo.getF11());
//				balanceInfo2.setF12(balanceInfo.getF12());
//				//
//				// 进行除掉
//				//
//				existMap.remove(key);
//			}
//			reslutList.add(balanceInfo2);
//		}
//		//
//		// 加入新增的记录数据
//		//
//		Iterator<Entry<String, BalanceInfo>> iterator = existMap.entrySet()
//				.iterator();
//		while (iterator.hasNext()) {
//			Entry<String, BalanceInfo> entry = iterator.next();
//			BalanceInfo balanceInfo = entry.getValue();
//			BalanceInfo2 balanceInfo2 = new BalanceInfo2();
//			if (flag == true) {
//				balanceInfo2.setF1A(balanceInfo.getF1());
//				balanceInfo2.setF2A(balanceInfo.getF2());
//				balanceInfo2.setF3A(balanceInfo.getF3());
//			}
//			balanceInfo2.setName(balanceInfo.getName());
//			balanceInfo2.setSpec(balanceInfo.getSpec());
//			balanceInfo2.setUnitName(balanceInfo.getUnitName());
//
//			// //////////////////////////////////////
//			// 转厂信息
//			//
//			balanceInfo2.setF5(balanceInfo.getF9());
//			//
//			// (内购)实际进料
//			//
//			balanceInfo2.setF5A(new Double(0.0));
//			//
//			// (内购)已结案合同发票核销数
//			//
//			balanceInfo2.setF5B(new Double(0.0));
//
//			balanceInfo2.setF6(balanceInfo.getF8());
//			balanceInfo2.setF7(balanceInfo.getF7());
//			balanceInfo2.setF8(balanceInfo.getF6());
//			//
//			// /////////////////////////////////////////
//
//			balanceInfo2.setF10(balanceInfo.getF10());
//			balanceInfo2.setF11(balanceInfo.getF11());
//			balanceInfo2.setF12(balanceInfo.getF12());
//			reslutList.add(balanceInfo2);
//		}
//
		return resultList;
	}

	public BalanceInfoLogic getBalanceInfoLogic() {
		return balanceInfoLogic;
	}

	public void setBalanceInfoLogic(BalanceInfoLogic balanceInfoLogic) {
		this.balanceInfoLogic = balanceInfoLogic;
	}

	public CasLeftoverMaterielDao getCasLeftoverMaterielDao() {
		return casLeftoverMaterielDao;
	}

	public void setCasLeftoverMaterielDao(
			CasLeftoverMaterielDao casLeftoverMaterielDao) {
		this.casLeftoverMaterielDao = casLeftoverMaterielDao;
	}

}
