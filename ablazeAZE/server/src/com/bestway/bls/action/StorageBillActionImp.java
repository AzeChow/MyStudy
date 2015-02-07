package com.bestway.bls.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bls.dao.StorageBillDao;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.bls.logic.StorageBillLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 车次管理 ACTION 接口实现 checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
//保税物流-车次管理
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class StorageBillActionImp extends BaseActionImpl implements
		StorageBillAction {
	/**
	 * 仓单管理DAO
	 */
	private StorageBillDao storageBillDao;
	/**
	 * 仓单管理逻辑层
	 */
	private StorageBillLogic storageBillLogic;

	public StorageBillDao getStorageBillDao() {
		return storageBillDao;
	}

	public void setStorageBillDao(StorageBillDao storageBillDao) {
		this.storageBillDao = storageBillDao;
	}

	public StorageBillLogic getStorageBillLogic() {
		return storageBillLogic;
	}

	public void setStorageBillLogic(StorageBillLogic storageBillLogic) {
		this.storageBillLogic = storageBillLogic;
	}

	/**
	 * 保存一个对像
	 * 
	 * @param objs
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保存权限", index = 1)
	public Object saveOrUpdateObject(Request request, Object obj) {
		return storageBillLogic.saveOrUpdateObject(obj);
	}

	/**
	 * 保存多个对像
	 * 
	 * @param objs
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保存权限", index = 2)
	public List saveOrUpdateObjects(Request request, List objs) {
		return storageBillLogic.saveOrUpdateObjects(objs);
	}

	/**
	 * 删除一个对像
	 * 
	 * @param objs
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "删除权限", index = 3)
	public void deleteObject(Request request, Object obj) {
		storageBillDao.delete(obj);
	}

	/**
	 * 删除车次
	 * 
	 * @param delivery
	 */
	@AuthorityFunctionAnnotation(caption = "车次-删除权限", index = 4)
	public void deleteDelivery(Request request, Delivery delivery) {
		this.storageBillLogic.deleteDelivery(delivery);
	}

	/**
	 * 删除仓单
	 * 
	 * @param storageBill
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-删除权限", index = 5)
	public void deleteStorageBill(Request request, StorageBill storageBill) {
		this.storageBillLogic.deleteStorageBill(storageBill);
	}

	/**
	 * 删除仓单--归并后
	 * 
	 * @param storageBillafter
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-删除权限", index = 5)
	public void deleteStorageBillAfter(Request request,
			StorageBillAfter storageBillafter) {
		this.storageBillLogic.deleteStorageBillAfter(storageBillafter);
	}

	/**
	 * 删除仓单--出口--归并后，要反写数据
	 * 
	 * @param storageBillafter
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-删除权限", index = 5)
	public void deleteStorageBillAfterByExport(Request request,
			StorageBillAfter storageBillafter) {
		this.storageBillLogic.deleteStorageBillAfterByExport(storageBillafter);
	}

	/**
	 * 删除多个对像
	 * 
	 * @param objs
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-删除权限", index = 5)
	public void deleteObjects(Request request, List objs) {
		storageBillDao.deleteAll(objs);
	}

	/**
	 * 根据车次号 查找车次
	 * 
	 * @param deliveryNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 9)
	public List findDelivery(Request request, String deliveryNo) {
		return this.storageBillDao.findDelivery(deliveryNo);
	}

	/**
	 * 根据进出标志查找车次
	 * 
	 * @param inOut
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 9)
	public List findDeliveryByInOut(Request request, int inOut) {
		return this.storageBillDao.findDeliveryByInOut(inOut);
	}

	/**
	 * 根据车次查找仓单
	 * 
	 * @param delivery
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 9)
	public List findStorageBillForDelivery(Request request, Delivery delivery) {
		return this.storageBillLogic.findStorageBillForDelivery(delivery);
	}

	/**
	 * 根据仓单号查找仓单
	 * 
	 * @param billNo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBill(Request request, String billNo) {
		return this.storageBillDao.findStorageBill(billNo);
	}

	/**
	 * 根据进出标志查找仓单
	 * 
	 * @param inout
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBillByInOut(Request request, String inout) {
		return this.storageBillDao.findStorageBillByInOut(inout);
	}

	/**
	 * 根据进出标志及仓单号查找仓单
	 * 
	 * @param billNo
	 * @param inOutFlag
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBillForDeliveryAdd(Request request,
			Delivery delivery, String inOutFlag) {
		return this.storageBillLogic.findStorageBillForDeliveryAdd(delivery,
				inOutFlag);
	}

	/**
	 * 根据仓单查找仓单归并后
	 * 
	 * @param storageBill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBillAfterForDelivery(Request request,
			StorageBill storageBill) {
		return this.storageBillLogic
				.findStorageBillAfterForDelivery(storageBill);
	}

	/**
	 * 查找内部归并，根据车次号过滤
	 * 
	 * @param storageBill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 17)
	public List findStorageBillAfterForDeliveryAdd(Request request,
			StorageBill storageBill) {
		return this.storageBillLogic
				.findStorageBillAfterForDeliveryAdd(storageBill);
	}

	/**
	 * 查找仓单号归并后最大序号
	 * 
	 * @param storageBill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public Integer findMaxStorageBillAfterSeqNo(Request request,
			StorageBill storageBill) {
		return this.storageBillDao.findMaxStorageBillAfterSeqNo(storageBill);
	}

	/**
	 * 根据仓单归并后查找归并前
	 * 
	 * @param storageBillAfter
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBillBeforeForDelivery(Request request,
			StorageBillAfter storageBillAfter) {
		return this.storageBillLogic
				.findStorageBillBeforeForDelivery(storageBillAfter);
	}

	/**
	 * 新增仓单管理－－归并前
	 * 
	 * @param sbill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-新增权限", index = 20)
	public List findStorageBillBeforeForDeliveryAdd(Request request,
			StorageBill sbill) {
		return this.storageBillLogic.findStorageBillBeforeForDeliveryAdd(sbill);
	}

	/**
	 * 新增仓单管理－－归并后
	 * 
	 * @param sbill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-新增权限", index = 20)
	public List saveStorageBillAfterAndBefore(Request request, List list) {
		return this.storageBillLogic.saveStorageBillAfterAndBefore(list);
	}

	/**
	 * 查找进口仓单归并后
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findBillAfterNotOut(Request request) {
		return this.storageBillDao.findBillAfterNotOut();
	}

	/**
	 * 保存仓单归并后－－新增多个时用到
	 * 
	 * @param map
	 */
	@AuthorityFunctionAnnotation(caption = "仓单－保存权限", index = 23)
	public void saveBillAfterForExport(Request request, Map map) {
		this.storageBillLogic.saveBillAfterForExport(map);
	}

	/**
	 * 查找仓单表项目个数
	 * 
	 * @param sbill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public Integer findAmountpicesFromStorageBill(Request request,
			StorageBill sbill) {
		return this.storageBillDao.findAmountpicesFromStorageBill(sbill);
	}

	/**
	 * 查找车次总件数
	 * 
	 * @param sbill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 17)
	public Integer findAmountpicesFromDelivery(Request request, Delivery dev) {
		return this.storageBillDao.findAmountpicesFromDelivery(dev);
	}

	/**
	 * 查找车次表体项目个数
	 * 
	 * @param sbill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 17)
	public Integer findCountFromDelivery(Request request, Delivery dev) {
		return this.storageBillDao.findCountFromDelivery(dev);
	}

	// public List findStorageBillBeforeForDelivery(Request request,
	// Delivery delivery);
	//
	// public List findStorageBillBeforeForDeliveryAdd(Request request,
	// Delivery delivery);
	/**
	 * 车次海关申报
	 */
	@AuthorityFunctionAnnotation(caption = "车次-海关申报", index = 27)
	public Delivery applyDelivery(Request request, Delivery delivery) {
		return this.storageBillLogic.applyDelivery(delivery);
	}

	/**
	 * 车次申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-申报回执处理", index = 28)
	public Delivery processDelivery(Request request, Delivery delivery,
			BlsReceiptResult blsReceiptResult) {
		return this.storageBillLogic
				.processDelivery(delivery, blsReceiptResult);
	}

	/**
	 * 新增仓单位归并前－－用于新增多个时，过滤
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-新增权限", index = 20)
	public void addStorageBillBefore(Request request, List bList,
			StorageBill storageBill) {
		Map mapss = new HashMap();
		List alist = findStorageBillAfterForDelivery(request, storageBill);
		for (int i = 0; i < alist.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) alist.get(i);
			if (aft.getCodeTS() == null) {
				continue;
			}
			if (aft.getSeqNum() == null) {
				continue;
			}
			String str = aft.getCodeTS().getCode() + "/"
					+ aft.getSeqNum().toString();
			mapss.put(str, aft);
		}
		if (bList == null) {
			return;
		} else {// 13592772769
			Map<String, BlsTenInnerMerge> map = new HashMap();
			Integer max = findMaxStorageBillAfterSeqNo(request, storageBill);
			if (max + bList.size() > 20) {
				throw new RuntimeException("该仓单已经走过20项商品!");
				// JOptionPane.showMessageDialog(DgBaseStorage.this,
				// "该仓单已经走过20项商品，请重新选择！", "提示！",
				// JOptionPane.WARNING_MESSAGE);
				// return;
			}
			Map nomap = new HashMap();
			List hasList = new ArrayList();
			List headList = new ArrayList();
			for (int i = 0; i < bList.size(); i++) {
				BlsInnerMerge inner = (BlsInnerMerge) bList.get(i);
				Materiel m = inner.getMateriel();
				if (m == null) {
					continue;
				}
				BlsTenInnerMerge teninner = inner.getBlsTenInnerMerge();
				if (teninner == null || teninner.getComplex() == null) {
					continue;
				}
				if (teninner.getSeqNum() == null) {
					continue;
				}
				String keystr = teninner.getComplex().getCode() + "/"
						+ teninner.getSeqNum().toString();
				StorageBillAfter afts = (StorageBillAfter) mapss.get(keystr);
				if (afts != null) {
					System.out.println("old -----------------");
					// List arlist = storageBillAction
					// .findStorageBillBeforeForDelivery(new Request(
					// CommonVars.getCurrUser()), afts);
					StorageBillBefore be = new StorageBillBefore();
					be.setPartNo(m);
					be.setStorageBillAfter(afts);
					be.setPartNameC(m.getFactoryName());
					hasList.add(be);
				} else {
					List ttlist = (List) nomap.get(keystr);
					if (ttlist == null) {
						ttlist = new ArrayList();
						afts = new StorageBillAfter();
						afts
								.setCodeTS(inner.getBlsTenInnerMerge()
										.getComplex());
						afts.setStorageBill(storageBill);
						afts.setCodeTS(teninner.getComplex());
						afts.setContrItem(teninner.getSeqNum());
						afts.setName(teninner.getName());
						afts.setModel(teninner.getSpec());
						afts.setSeqNum(teninner.getSeqNum());
						++max;
						afts.setSeqNo(max);
						StorageBillBefore bef = new StorageBillBefore();
						bef.setPartNo(m);
						bef.setPartNameC(m.getFactoryName());
						ttlist.add(bef);
						headList.add(afts);
						nomap.put(keystr, ttlist);
					} else {
						StorageBillBefore bef = new StorageBillBefore();
						bef.setPartNo(m);
						bef.setPartNameC(m.getFactoryName());
						ttlist.add(bef);
					}
				}
			}
			List saveList = new ArrayList();
			saveList.add(hasList);
			saveList.add(headList);
			saveList.add(nomap);
			List clist = saveStorageBillAfterAndBefore(request, saveList);
			// refreshTable();
		}
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	@AuthorityFunctionAnnotation(caption = "核销捆绑关系基本信息（表头）所有的数据-查询", index = 30)
	public List findDelivery(Request request, int index, int length,
			String property, Object value, boolean isLike, String inout) {
		return this.storageBillDao.findDelivery(index, length, property, value,
				isLike, inout);
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	@AuthorityFunctionAnnotation(caption = "核销捆绑关系基本信息（表头）所有的数据-查询", index = 30)
	public List findDelivery(Request request, Date startDate, Date endDate,
			String inout) {
		return this.storageBillDao.findDelivery(startDate, endDate, inout);
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	@AuthorityFunctionAnnotation(caption = "核销捆绑关系基本信息（表头）所有的数据-查询", index = 30)
	public List findStorageBill(Request request, int index, int length,
			String property, Object value, boolean isLike, String inout) {
		return this.storageBillDao.findStorageBill(index, length, property,
				value, isLike, inout);
	}

	/**
	 * 根据有效期查询
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBill(Request request, Date startDate, Date endDate,
			String inout) {
		return this.storageBillDao.findStorageBill(startDate, endDate, inout);
	}

	/**
	 * 检查仓单数据合法性
	 * 
	 * @param sbill
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "检查仓单数据合法性", index = 34)
	public String checkStorageBill(Request request, StorageBill sbill) {
		return storageBillLogic.checkStorageBill(sbill);
	}

	/**
	 * 根据ID查找仓单
	 * 
	 * @param id
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public StorageBill findStorageBillByID(Request request, String id) {
		List lt = storageBillDao.findStorageBillByID(id);
		if (lt != null && lt.size() > 0) {
			return ((StorageBill) lt.get(0));
		}
		return null;
	}

	/**
	 * 根据ID查找车次信息
	 * 
	 * @param id
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "车次-查询权限", index = 17)
	public Delivery findDeliveryByID(Request request, String id) {
		List lt = this.storageBillDao.findDeliveryByID(id);
		if (lt != null && lt.size() > 0) {
			return ((Delivery) lt.get(0));
		}
		return null;
	}

	/**
	 * 查询库存信息
	 * 
	 * @param serviceInfostring
	 * @param handlestring
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "库存信息-", index = 37)
	public List findStockFromHP(Request request, String emsNo,
			String warehouseCode, String customsCode, String contrItem,
			String codeTS, String gName, String gModel, String gUnit) {
		return this.storageBillLogic.findStockFromHP(emsNo, warehouseCode,
				customsCode, contrItem, codeTS, gName, gModel, gUnit);
	}

	/**
	 * 查询仓单信息 hw
	 * 
	 * @param serviceInfostring
	 * @param handlestring
	 * @return List
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public List findStorageBillStatusFromHP(Request request, String tradeCo,
			String billNo) {
		return this.storageBillLogic.findStorageBillStatusFromHP(tradeCo,
				billNo);
	}

	/**
	 * 找到剩余数量
	 */
	@AuthorityFunctionAnnotation(caption = "仓单-查询权限", index = 13)
	public Double findRemainingQuantity(Request request, String corrBillNo,Integer seqNum) {
		return this.storageBillDao.findRemainingQuantity(corrBillNo,seqNum);
	}

	/**
	 * 改变车次管理表头的申报状态
	 */
	public Delivery changeDeliveryDeclareState(Request request,
			Delivery delivery, String declareState) {
		return this.storageBillLogic.changeDeliveryDeclareState(delivery,
				declareState);
	}
}
