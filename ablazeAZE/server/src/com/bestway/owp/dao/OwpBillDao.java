package com.bestway.owp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBillAndBillDetail;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillRecvItem;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.OwpBillSendItem;

public class OwpBillDao extends BaseDao {

	/**
	 * 根据id查询出相应的外发登记表表头
	 * @param id
	 * @return
	 * @author sxk
	 */
	public OwpBillSendHead findOwpBillSendHeadById(String id) {
		List list = this
				.find(
						"select a from OwpBillSendHead as a where a.company.id=? and a.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(), id });
		if (list != null && list.size() > 0) {
			return (OwpBillSendHead) list.get(0);
		}
		return null;
	}
	/**
	 * 根据id查询出相应的收货登记表表头
	 * @param id
	 * @return
	 * @author sxk
	 */
	public OwpBillRecvHead findOwpBillRecvHeadById(String id) {
		List list = this
				.find(
						"select a from OwpBillRecvHead as a where a.company.id=? and a.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(), id });
		if (list != null && list.size() > 0) {
			return (OwpBillRecvHead) list.get(0);
		}
		return null;
	}
	/**
	 * 按发货日期循序asc查找出该申请表下的所有出货登记表
	 * @param request
	 * @param id
	 * @return
	 * @author sxk
	 */
	public List findOwpBillSendHead(String appNo) {
		List list = this
				.find(
						"select a from OwpBillSendHead as a where a.company.id=? and a.appNo= ?  order by collectDate asc ",
						new Object[] { CommonUtils.getCompany().getId(), appNo });

		return list;
	}
	/**
	 * 按发货日期循序asc查找出该申请表下的所有收货登记表
	 * @param request
	 * @param id
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvHead(String appNo) {
		List list = this
				.find(
						"select a from OwpBillRecvHead as a where a.company.id=? and a.appNo= ? order by collectDate asc",
						new Object[] { CommonUtils.getCompany().getId(), appNo });

		return list;
	}
	/**
	 * 查找外发加工出货登记表头
	 * @param request
	 * @param 申请表编号
	 * @return 
	 * @author sxk
	 */
	public List findOwpSendBillHeadInfo(String appNo) {
		return this.find("select a from OwpBillSendHead a"
				+ " where a.company.id=? and a.appNo = ? " 
				+ " order by seqNum ", new Object[] { CommonUtils
				.getCompany().getId(),appNo });
	}
	/**
	 * 查找外发加工进货登记表头
	 * @param request
	 * @param 申请表编号
	 * @return 
	 * @author sxk
	 */
	public List findOwpRecvBillHeadInfo(String appNo) {
		return this.find("select a from OwpBillRecvHead a"
				+ " where a.company.id=? and a.appNo = ? " 
				+ " order by seqNum ", new Object[] { CommonUtils
				.getCompany().getId(),appNo });
	}
	/**
	 * 查询外发加工出货登记表表体
	 * @param head 外发加工登记表表头
	 * @return
	 * @author sxk
	 */
	public List findOwpSendBillItemByHead(OwpBillSendHead head) {
		return this.find("select a from OwpBillSendItem a where a.company.id=?"
				+ " and a.head.id=? order by a.listNo", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}
	/**
	 * 查询外发加工进货登记表表体
	 * @param head 外发加工登记表表头
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvItemByHead(OwpBillRecvHead head) {
		return this.find("select a from OwpBillRecvItem a where a.company.id=?"
				+ " and a.head.id=? order by a.listNo", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}
	/**
	 * 查询外发加工申请表出货
	 * @param head 外发加工申请表表头
	 * @return
	 * @author sxk
	 */
	public List findOwpSendItemByHead(String head) {
		System.out.println("head="+head);
		return this.find("select a from OwpAppSendItem a where a.company.id=? " 
				+ " and a.head.appNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), head });
	}
	/**
	 * 查询外发加工申请表进货
	 * @param head 外发加工申请表表头
	 * @return
	 * @author sxk
	 */
	public List findOwpRecvItemByHead(String head) {
		System.out.println("head="+head);
		return this.find("select a from OwpAppRecvItem a where a.company.id=? " 
				+ " and a.head.appNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), head });
	}
	/**
	 * 查找外发加工申请表表头
	 * @return
	 * @author sxk
	 */
	public List findOwpAppHead() {
		return this.find("select a from OwpAppHead a"
				+ " where a.company.id=? and a.declareState = ? ", new Object[] { CommonUtils
				.getCompany().getId(),DeclareState.PROCESS_EXE  });
	}

	/**
	 * 查找出货登记表最大商品序号
	 * @param billNo 
	 * @return
	 * @author sxk
	 */
	public Integer findBillSendMaxNo(String billNo) {
		List list = this
				.find(
						"select max(a.listNo) from OwpBillSendItem as a "
								+ "	where a.head.billNo = ? and a.company.id=?",
						new Object[] { billNo,
								CommonUtils.getCompany().getId() });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}
	/**
	 * 查找出货登记表头最大流水号
	 * @param billNo 
	 * @return
	 * @author sxk
	 */
	public Integer findBillSendHeadMaxNo(String appNo) {
		List list = this
				.find(
						"select max(a.seqNum) from OwpBillSendHead as a "
								+ "	where a.appNo = ? and a.company.id=?",
						new Object[] { appNo,
								CommonUtils.getCompany().getId() });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}
	/**
	 * 查找进货登记表最大商品序号
	 * @param billNo 
	 * @return
	 * @author sxk
	 */
	public Integer findBillRecvMaxNo(String billNo) {
		List list = this
				.find(
						"select max(a.listNo) from OwpBillRecvItem as a "
								+ "	where a.head.billNo = ? and a.company.id=?",
						new Object[] { billNo,
								CommonUtils.getCompany().getId() });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}
	/**
	 * 查找出货登记表头最大流水号
	 * @param billNo 
	 * @return
	 * @author sxk
	 */
	public Integer findBillRecvHeadMaxNo(String appNo) {
		List list = this
				.find(
						"select max(a.seqNum) from OwpBillRecvHead as a "
								+ "	where a.appNo = ? and a.company.id=?",
						new Object[] { appNo,
								CommonUtils.getCompany().getId() });

		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}
	/**
	 * 保存申请表来源的出货登记表体
	 */
	public void saveOwpBillSendItem(OwpBillSendItem item) {
		this.saveOrUpdate(item);
	}
	/**
	 * 保存申请表来源的进货登记表体
	 */
	public void saveOwpBillRecvItem(OwpBillRecvItem item) {
		this.saveOrUpdate(item);
	}
	/**
	 * 保存所新增的登记表和单据关联关系
	 * @param owpBillAndBillDetail
	 */
	public void savaOwpBillAndBillDetail(OwpBillAndBillDetail owpBillAndBillDetail){
		this.saveOrUpdate(owpBillAndBillDetail);
	}
	/**
	 * 查询申请表外发料件申报数量
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpAppSendItemQty(String appNo,Integer seqNum){
		return this.find("select a from OwpAppSendItem a where a.company.id=? " 
				+ " and a.head.appNo=? and a.seqNum=? ", new Object[] {
				CommonUtils.getCompany().getId(), appNo, seqNum });
	}
	/**
	 * 查询申请表进货料件申报数量
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpAppRecvItemQty(String appNo,Integer seqNum){
		return this.find("select a from OwpAppRecvItem a where a.company.id=? " 
				+ " and a.head.appNo=? and a.seqNum=? ", new Object[] {
				CommonUtils.getCompany().getId(), appNo, seqNum });
	}
	/**
	 * 查询登记表外发料件
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpBillSendItemBySeqNum(String appNo,Integer appGNo){
		return this.find("select a from OwpBillSendItem a where a.company.id=? " 
				+ " and a.head.appNo=? and a.appGNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), appNo, appGNo });
	}
	/**
	 * 查询登记表收回料件
	 * @param request
	 * @param appNo 外发加工申请表编号
	 * @param seqNum 外发加工申请表商品序号
	 * @return
	 * @author sxk
	 */
	public List findOwpBillRecvItemBySeqNum(String appNo,Integer appGNo){
		return this.find("select a from OwpBillRecvItem a where a.company.id=? " 
				+ " and a.head.appNo=? and a.appGNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), appNo, appGNo });
	}
	/**
	 * 组织HQL报表条件公共查询,(这个好的东西也要学习.要引用.罗盛)
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @param leftOuter
	 *            左连接内容
	 * @return 查询结果
	 */
	public List commonSearch(String selects, String className, List conditions,
			String groupBy, String leftOuter) {
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
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if(condition.getOperator().indexOf("in")>0){
						String[] strs = (String[])condition.getValue();
						for(int j=0;j<strs.length;j++){
							if(j==0){
								sql += "?";
							}else{
								sql +=",?";
							}
						}
						for(String str : strs){
							params.add(str);
						}
					}else{
						sql += "? ";
						params.add(condition.getValue());
						System.out.println("value1="+condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		System.out.println("---------------sql:" + sql);
		List result = this.find(sql, params.toArray());
		return result;
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
	 * (sxk抄至casDao)
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
		return result;
	}
	/**
	 * @author 石小凯
	 * @return 返回料件报关对应信息：名称、规格、单项用量
	 */
	public Map<String,FactoryAndFactualCustomsRalation> getUnitConvert(){
		Map<String,FactoryAndFactualCustomsRalation> map=new HashMap<String,FactoryAndFactualCustomsRalation>();
		List<FactoryAndFactualCustomsRalation> list = this.find(
				" from FactoryAndFactualCustomsRalation as a"+
				" where a.company.id=?", new Object[] { CommonUtils
						.getCompany().getId() });
		System.out.println("list.size()="+list.size());
		for(int i=0;i<list.size();i++){
			FactoryAndFactualCustomsRalation ralation=(FactoryAndFactualCustomsRalation)list.get(i);
			String key = ralation.getMateriel().getPtNo() == null ? "" : ralation.getMateriel().getPtNo();
			System.out.println("对应关系KEY"+key);
			map.put(key, ralation);
		}
		return map;
	}
	
	public List findOwpBillAndBillDetail(String billId){
		return this.find("select a from OwpBillAndBillDetail a where a.company.id=? " 
				+ " and a.bill=? ", new Object[] {
				CommonUtils.getCompany().getId(),billId});
	}
	public List findOwpBillAndBillDetailByOwpBillSendItem(String owpBillId){
		return this.find("select a from OwpBillAndBillDetail a where a.company.id=? " 
				+ " and a.owpBillSendItem.id=? ", new Object[] {
				CommonUtils.getCompany().getId(),owpBillId});
	}
	public List findOwpRecvBillAndBillDetailByOwpBillSendItem(String owpBillId){
		return this.find("select a from OwpBillAndBillDetail a where a.company.id=? " 
				+ " and a.owpBillRecvItem.id=? ", new Object[] {
				CommonUtils.getCompany().getId(),owpBillId});
	}
	/**
	 * 查找 owpBillSendHead 来自 copAppNo
	 * @author sxk
	 */
	public OwpBillSendHead findOwpBillSendHeadByCopBillNo(String copBillNo){
		List list = this.find("select a from OwpBillSendHead as a "
				+ "	where a.copBillNo=? and a.company.id= ? "
				+ " and a.declareState = ? ", new Object[] {
						copBillNo, CommonUtils.getCompany().getId(),
				DeclareState.PROCESS_EXE });
		if (!list.isEmpty()) {
			return (OwpBillSendHead) list.get(0);
		}
		return null;
	}
	/**
	 * 查找 owpBillRecvHead 来自 copAppNo
	 * @author sxk
	 */
	public OwpBillRecvHead findOwpBillRecvHeadByCopBillNo(String copBillNo){
		List list = this.find("select a from OwpBillRecvHead as a "
				+ "	where a.copBillNo=? and a.company.id= ? "
				+ " and a.declareState = ? ", new Object[] {
						copBillNo, CommonUtils.getCompany().getId(),
				DeclareState.PROCESS_EXE });
		if (!list.isEmpty()) {
			return (OwpBillRecvHead) list.get(0);
		}
		return null;
	}
}
