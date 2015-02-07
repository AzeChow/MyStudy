package com.bestway.owp.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.owp.entity.OwpAppHead;



/**
 * 外发加工申请表dao
 * @author wss
 */
public class OwpAppDao extends BaseDao {

	/**
	 * 查找外发加工申请表表头
	 * @return
	 * @author wss
	 */
	public List findOwpAppHead() {
		return this.find("select a from OwpAppHead a"
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 获取className里最大的流水号
	 * @param className 表名
	 * @param seqNum 流水号
	 * @return Sting className里最大的流水号
	 * @author wss
	 */
	public String getNum(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a " + "where a.company= ?", new Object[] { CommonUtils
				.getCompany() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}
	
	/**
	 * 查询外发加工申请表出货
	 * @param head 外发加工申请表表头
	 * @return
	 * @author wss
	 */
	public List findOwpAppSendItemByHeadId(String headId) {
		return this.find("select a from OwpAppSendItem a where a.company.id=?"
				+ " and a.head.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), headId});
	}
	
	/**
	 * 查询外发加工申请表收货
	 * @param head 外发加工申请表表头
	 * @return
	 * @author wss
	 */
	public List findOwpAppRecvItemByHeadId(String headId) {
		return this.find("select a from OwpAppRecvItem a where a.company.id=?"
				+ " and a.head.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), headId});
	}
	
	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param selects 选择内容
	 * @param className 类名
	 * @param conditions 查询条件
	 * @param groupBy分组
	 * @param leftOuter 左连接内容
	 * @param orderBy 排序规则
	 * @return 查询结果list
	 * (wss抄至casDao)
	 */
	public List commonSearch(String selects, String className, List conditions,
			String groupBy, String leftOuter, String orderBy) {
		if (leftOuter == null) {
			leftOuter = "";
		}

		String sql = "";
		sql = " from " + className + " a " + leftOuter + " where a.company =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null
			sql = " select a from " + className + " a " + leftOuter
					+ " where a.company =? ";
			System.out.println("then 2 this is sql=" + sql);
		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += " " + condition.getOperator() + " ";
				}
				if (condition.getValue() != null) {
					sql += "? ";
					params.add(condition.getValue());
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		if (orderBy != null && !orderBy.equals("")) {
			sql = sql + " " + orderBy;
		}
		System.out.println("=====search hsql:" + sql);
		List result = this.find(sql, params.toArray());
		System.out.println("wss result.size = " + result.size());
		return result;
	}
	
	/**
	 * 获取当前公司
	 * @return Company 当前公司，返回符合条件的第一条数据
	 * @author wss
	 */
	public Company findCompany() {
		List list = this.find("select a from Company a where a.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		return (Company) list.get(0);
	}
	
	/**
	 * 查找外发加工申请表表头信息
	 * 
	 * @param noNullField 不为空的字段
	 * @return list
	 * @author wss
	 */
	public List findOwpAppHeadInfo(String noNullField) {
		return this.find(
				"select a from OwpAppHead a"
				+ " where a.company.id=? " 
				+ " and a." + noNullField + " is not null ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	
	/**
	 * 根据id查询出相应的外发申请表表头
	 * @param id
	 * @return
	 * @author wss
	 */
	public OwpAppHead findOwpAppHeadById(String id) {
		List list = this
				.find(
						"select a from OwpAppHead as a where a.company.id=? and a.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(), id });
		if (list != null && list.size() > 0) {
			return (OwpAppHead) list.get(0);
		}
		return null;
	}
	
	/**
	 * 获得刚申报后,未修改的  外发货物  数据
	 * 
	 * @param parentId
	 * @return
	 * @author wss2010.09.26
	 */
	public List findOwpAppSendItemsStateChanged(String parentId) {
		return this.find(
				"select a from OwpAppSendItem a "
						+ " where a.head.id = ? "
						+ " and a.modifyMark <> ? "
						+ " and a.company.id=? "
						+ " order by a.seqNum ", new Object[] {
								parentId, ModifyMarkState.UNCHANGE,
								CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 获得刚申报后,未修改的  收回货物  数据
	 * 
	 * @param parentId
	 * @return wss2010.09.26
	 */
	public List findOwpAppRecvItemsStateChanged(String parentId) {
		return this.find(
				"select a from OwpAppRecvItem a "
						+ " where a.head.id = ? "
						+ " and a.modifyMark <> ? "
						+ " and a.company.id=? "
						+ " order by a.seqNum ", new Object[] {
								parentId, ModifyMarkState.UNCHANGE,
								CommonUtils.getCompany().getId() });
	}

	
	/** 
	 * 获得相应状态的  外发加工申请单
	 * @param tradeCode 委托方企业代码
	 * @param declareState 申请类型
	 * @author wss
	 */
	public OwpAppHead findOwpAppHeadByInTradeCode(String inTradeCode,
			String declareState) {
		List list = this.find(
				"select a from OwpAppHead a "
						+ " where a.company= ? "
						+ " and a.inTradeCode = ? "
						+ " and a.declareState=? ",
				new Object[] { CommonUtils.getCompany(), inTradeCode,declareState });
		if (list != null && list.size() > 0) {
			return (OwpAppHead) list.get(0);
		}
		return null;
	}

	

	/**
	 * 查找 owpAppHead 来自 copAppNo
	 * @author wss
	 */
	public OwpAppHead findOwpAppHeadByCopAppNo(String copAppNo){
		List list = this.find(
				"select a from OwpAppHead as a "
					+ "	where a.copAppNo=? "
					+ " and a.company.id= ? "
					+ " and a.declareState = ? ", new Object[] {
				copAppNo, CommonUtils.getCompany().getId(),
				DeclareState.PROCESS_EXE });
		if (!list.isEmpty()) {
			return (OwpAppHead) list.get(0);
		}
		return null;
	}

}
