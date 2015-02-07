package com.bestway.bls.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.StorageBillDao;
import com.bestway.bls.entity.BlsDeliveryResultType;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceID;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.bls.entity.TempBillReturn;
import com.bestway.bls.entity.TempCollateEntity;
import com.bestway.bls.entity.TempCollateItem;
import com.bestway.bls.entity.TempDeliveryEntity;
import com.bestway.bls.entity.TempQueryBillList;
import com.bestway.bls.entity.TempQueryProduct;
import com.bestway.bls.entity.TempStockReturn;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;

/**
 * 仓单管理Logic层checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class StorageBillLogic {
	/**
	 * 仓单管理DAO
	 */
	private StorageBillDao storageBillDao;
	/**
	 * 报文发送逻辑层
	 */
	private BlsMessageLogic blsMessageLogic;

	public StorageBillDao getStorageBillDao() {
		return storageBillDao;
	}

	public void setStorageBillDao(StorageBillDao storageBillDao) {
		this.storageBillDao = storageBillDao;
	}

	public BlsMessageLogic getBlsMessageLogic() {
		return blsMessageLogic;
	}

	public void setBlsMessageLogic(BlsMessageLogic blsMessageLogic) {
		this.blsMessageLogic = blsMessageLogic;
	}

	/**
	 * 保存多个对像
	 * 
	 * @param objs
	 * @return
	 */
	public List saveOrUpdateObjects(List objs) {
		if (objs == null) {
			System.out.println("Exception :  objs  is null !!!");
			return new ArrayList();
		}
		for (int i = 0; i < objs.size(); i++) {
			storageBillDao.saveOrUpdate(objs.get(i));
		}
		return objs;
	}

	/**
	 * 保存一个对像
	 * 
	 * @param obj
	 * @return
	 */
	public Object saveOrUpdateObject(Object obj) {
		storageBillDao.saveOrUpdate(obj);
		return obj;
	}

	/**
	 * 根据车次查找仓单
	 * 
	 * @param delivery
	 * @return
	 */
	public List findStorageBillForDelivery(Delivery delivery) {
		return this.storageBillDao.findStorageBillForDelivery(delivery);
	}

	/**
	 * 根据进出标志及仓单号查找仓单
	 * 
	 * @param billNo
	 * @param inOutFlag
	 * @return
	 */
	public List findStorageBillForDeliveryAdd(Delivery delivery,
			String inOutFlag) {
		if (delivery.getId() == null) {
			System.out.println("the  delivery   id  is null !");
			return new ArrayList();
		}
		List allList = storageBillDao.findStorageBill(null, inOutFlag);
		// List existlist = storageBillDao.findStorageBillForDelivery(delivery);
		// allList.removeAll(existlist);
		return allList;
	}

	/**
	 * 根据仓单查找仓单归并后
	 * 
	 * @param storageBill
	 * @return
	 */
	public List findStorageBillAfterForDelivery(StorageBill storageBill) {
		return this.storageBillDao.findStorageBillAfterForDelivery(storageBill);
	}

	/**
	 * 查找内部归并，根据车次号过滤
	 * 
	 * @param storageBill
	 * @return
	 */
	public List findStorageBillAfterForDeliveryAdd(StorageBill storageBill) {
		return this.storageBillDao.findBlsTenInnerMerge(storageBill);

	}

	/**
	 * 根据仓单归并后查找归并前
	 * 
	 * @param storageBillAfter
	 * @return
	 */
	public List findStorageBillBeforeForDelivery(
			StorageBillAfter storageBillAfter) {
		return this.storageBillDao
				.findStorageBillBeforeForDelivery(storageBillAfter);
	}

	/**
	 * 新增仓单管理－－归并前
	 * 
	 * @param sbill
	 * @return
	 */
	public List findStorageBillBeforeForDeliveryAdd(StorageBill sbill) {
		List reList = new ArrayList();
		Map rmap = new HashMap();
		List tlist = new ArrayList();
		List hlist = storageBillDao.findStorageBillAfterForDelivery(sbill);
		for (int i = 0; i < hlist.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) hlist.get(i);
			if (aft.getSeqNum() == null || aft.getCodeTS() == null) {
				continue;
			}
			List slist = storageBillDao.findStorageBillBeforeForDelivery(aft);
			for (int j = 0; j < slist.size(); j++) {
				StorageBillBefore bfe = (StorageBillBefore) slist.get(j);
				if (bfe.getPartNo() == null) {
					continue;
				}
				String key = aft.getSeqNum().toString() + "/"
						+ aft.getCodeTS().getCode() + "/"
						+ bfe.getPartNo().getPtNo();
				System.out.println("key1---------------" + key);
				tlist.add(key);
			}
		}
		List innerList = this.storageBillDao
				.findStorageBillBeforeForDeliveryAdd();
		for (int i = 0; i < innerList.size(); i++) {
			BlsInnerMerge bls = (BlsInnerMerge) innerList.get(i);
			if (bls.getBlsTenInnerMerge() == null
					|| bls.getBlsTenInnerMerge().getSeqNum() == null
					|| bls.getBlsTenInnerMerge().getComplex() == null
					|| bls.getMateriel() == null) {
				continue;
			}
			String key = bls.getBlsTenInnerMerge().getSeqNum().toString() + "/"
					+ bls.getBlsTenInnerMerge().getComplex().getCode() + "/"
					+ bls.getMateriel().getPtNo();
			// System.out.println("key2---------------" + key);
			// if (!tlist.contains(key)) {
			// reList.add(bls);
			// }
			reList.add(bls);
		}
		return reList;
	}

	/**
	 * 新增仓单管理－－归并后
	 * 
	 * @param sbill
	 * @return
	 */
	public List saveStorageBillAfterAndBefore(List list) {
		List hasList = (List) list.get(0);
		saveOrUpdateObjects(hasList);
		List headList = (List) list.get(1);
		saveOrUpdateObjects(headList);
		Map nomap = (Map) list.get(2);
		for (int i = 0; i < headList.size(); i++) {
			StorageBillAfter atfs = (StorageBillAfter) headList.get(i);
			if (atfs.getCodeTS() == null || atfs.getSeqNum() == null) {
				continue;
			}
			String keys = atfs.getCodeTS().getCode() + "/"
					+ atfs.getSeqNum().toString();
			List delist = (List) nomap.get(keys);
			if (delist == null) {
				continue;
			}
			for (int j = 0; j < delist.size(); j++) {
				StorageBillBefore bef = (StorageBillBefore) delist.get(j);
				if (bef == null) {
					continue;
				}
				bef.setStorageBillAfter(atfs);
				saveOrUpdateObject(bef);
			}
		}
		return headList;
	}

	/**
	 * 删除车次
	 * 
	 * @param delivery
	 */
	public void deleteDelivery(Delivery delivery) {
		List dlist = this.findStorageBillForDelivery(delivery);
		for (int i = 0; i < dlist.size(); i++) {
			StorageBill sbill = (StorageBill) dlist.get(i);
			sbill.setDelivery(null);
			this.saveOrUpdateObject(sbill);
		}
		this.storageBillDao.delete(delivery);
	}

	/**
	 * 删除仓单
	 * 
	 * @param storageBill
	 */
	public void deleteStorageBill(StorageBill storageBill) {
		List after = this.findStorageBillAfterForDelivery(storageBill);
		for (int i = 0; i < after.size(); i++) {
			List lists = this
					.findStorageBillBeforeForDelivery((StorageBillAfter) after
							.get(i));
			this.storageBillDao.deleteAll(lists);
		}
		this.storageBillDao.deleteAll(after);
		this.storageBillDao.delete(storageBill);
	}

	/**
	 * 删除仓单--归并后
	 * 
	 * @param storageBillafter
	 */
	public void deleteStorageBillAfter(StorageBillAfter storageBillafter) {
		List lists = this.findStorageBillBeforeForDelivery(storageBillafter);
		this.storageBillDao.deleteAll(lists);
		this.storageBillDao.delete(storageBillafter);
	}

	/**
	 * 删除仓单--出口--归并后，要反写数据
	 * 
	 * @param storageBillafter
	 */
	public void deleteStorageBillAfterByExport(StorageBillAfter storageBillafter) {
		String billNo = storageBillafter.getCorrBillNo();
		Integer seqNo = storageBillafter.getCorrBillGNo();
		// if ( seqNo == null) {
		List list = this.storageBillDao.findStorageBillAfterByBillNoAndSeqNo(
				billNo, seqNo);
		if (list != null && !list.isEmpty() && list.get(0) != null) {
			StorageBillAfter aft = (StorageBillAfter) list.get(0);
			aft.setIsOut(false);
			this.storageBillDao.saveOrUpdate(aft);
		}
		// }
		List lists = this.findStorageBillBeforeForDelivery(storageBillafter);
		this.storageBillDao.deleteAll(lists);
		this.storageBillDao.delete(storageBillafter);
	}

	/**
	 * 保存仓单归并后－－新增多个时用到
	 * 
	 * @param map
	 */
	public void saveBillAfterForExport(Map map) {
		List alist = new ArrayList(map.keySet());
		List nlist = new ArrayList(map.values());
		this.saveOrUpdateObjects(alist);
		this.saveOrUpdateObjects(nlist);
		for (int i = 0; i < alist.size(); i++) {
			StorageBillAfter ft = (StorageBillAfter) alist.get(i);
			List dlist = storageBillDao.findStorageBillBeforeForDelivery(ft);
			for (int j = 0; j < dlist.size(); j++) {
				StorageBillBefore bf = (StorageBillBefore) dlist.get(j);
				StorageBillBefore sbill = new StorageBillBefore();
				try {
					BeanUtilsBean.getInstance().copyProperties(sbill, bf);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				sbill.setId(null);
				sbill.setStorageBillAfter((StorageBillAfter) map.get(ft));
				sbill.setDetailQty(ft.getQty());
				
				this.storageBillDao.saveOrUpdate(sbill);
			}
		}
	}

	// public List findStorageBillBeforeForDelivery(Delivery delivery) {
	// return null;
	// }
	//
	// public List findStorageBillBeforeForDeliveryAdd(Delivery delivery) {
	// return null;
	// }
	/**
	 * 车次海关申报
	 */
	public Delivery applyDelivery(Delivery delivery) {
		// 系统类型，比如车次，车到，或者核销
		String serviceType = BlsServiceType.MANIFEST_DECLARE;
		// 关键值
		String keyCode = delivery.getDeliveryNo();
		delivery.setMessTimeStamp(new Date());
		this.storageBillDao.saveOrUpdate(delivery);
		// 生成报文查询值参数
		Map<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("id", delivery.getId());
		// 海关申报
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.declareMessage(serviceType, delivery.getId(), keyCode,
						queryValues, null, null, (Company) delivery
								.getCompany());
		String declareState = DeclareState.WAIT_EAA;
		if (BlsDeliveryResultType.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsDeliveryResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsDeliveryResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		delivery.setDeclareState(declareState);
		this.storageBillDao.saveOrUpdate(delivery);
		return delivery;
	}

	/**
	 * 车次申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	public Delivery processDelivery(Delivery delivery,
			BlsReceiptResult blsReceiptResult) {
		String declareState = DeclareState.WAIT_EAA;
		if (BlsDeliveryResultType.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsDeliveryResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsDeliveryResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		delivery.setDeclareState(declareState);
		this.storageBillDao.saveOrUpdate(delivery);
		this.blsMessageLogic.processMessage(blsReceiptResult);
		return delivery;
	}

	/**
	 * 检查仓单数据合法性
	 * 
	 * @param sbill
	 * @return
	 */
	public String checkStorageBill(StorageBill sbill) {
		String ret = "";
		String seqNo = "";
		List after = storageBillDao.findStorageBillAfterForDelivery(sbill);
		for (int i = 0; i < after.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) after.get(i);
			if (aft.getSeqNo() == null) {
				ret += "仓单表体序号有为空！";

				return ret;
			} else {
				seqNo = aft.getSeqNo().toString();
				if (aft.getCodeTS() == null) {
					ret += "\t仓单表体序号为" + seqNo + " 商品编码为空！\n";
				}
				if (aft.getName() == null || aft.getName().trim().equals("")) {
					ret += "\t仓单表体序号为" + seqNo + " 名称为空！\n";
				}
				if (aft.getCurr() == null) {
					ret += "\t仓单表体序号为" + seqNo + " 币制为空！\n";
				}
				if (aft.getUnit() == null) {
					ret += "\t仓单表体序号为" + seqNo + " 申报单位为空！\n";
				}
				if (aft.getQty() == null) {
					ret += "\t仓单表体序号为" + seqNo + " 申报数量为空！\n";
				}
				if (aft.getUnitPrice() == null) {
					ret += "\t仓单表体序号为" + seqNo + " 申报单价为空！\n";
				}
				if (aft.getTotalP() == null) {
					ret += "\t仓单表体序号为" + seqNo + " 申报总价为空！\n";
				}
			}
		}

		List before = storageBillDao.findStorageBillBefore(sbill);
		for (int i = 0; i < before.size(); i++) {
			StorageBillBefore bfe = (StorageBillBefore) before.get(i);
			String seq = bfe.getStorageBillAfter().getSeqNo() == null ? ""
					: bfe.getStorageBillAfter().getSeqNo().toString();
			if (bfe.getPartNo() == null) {
				ret += "\t仓单表体序号为" + seq + " 归并前内部商品货号为空！\n";
			}
			if (bfe.getPartNameC() == null) {
				ret += "\t仓单表体序号为" + seq + " 归并前中文名称为空！\n";
			}
		}
		return ret;
	}

	/**
	 * 查询库存信息 yp
	 * 
	 * @param serviceInfostring
	 * @param handlestring
	 * @return
	 */
	public List findStockFromHP(String emsNo, String warehouseCode,
			String customsCode, String contrItem, String codeTS, String gName,
			String gModel, String gUnit) {
		String str = "";
		List<TempStockReturn> listResult = new ArrayList<TempStockReturn>();
		// 生成动作类型(XML格式)
		String serviceInfostring = blsMessageLogic.getBlsServiceInfo(
				BlsServiceID.QUERY_STOCK, (Company) CommonUtils.getCompany());
		System.out.println("serviceInfostring=" + serviceInfostring);
		// 生成查询参数(XML格式)
		StringBuffer handlestring = new StringBuffer();
		handlestring.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		handlestring.append("<QueryStock xmlns=\"un:epz\">");
		handlestring.append("  <EmsNo>" + emsNo + "</EmsNo>");
		handlestring.append("   <WarehouseCode>" + warehouseCode
				+ "</WarehouseCode>");
		handlestring.append("    <CustomsCode>" + customsCode
				+ "</CustomsCode>");
		handlestring.append("    <ContrItem>" + contrItem + "</ContrItem>");
		handlestring.append("    <CodeTS>" + codeTS + "</CodeTS>");
		handlestring.append("    <GName>" + gName + "</GName>");
		handlestring.append("    <GModel>" + gModel + "</GModel>");
		handlestring.append("    <GUnit>" + gUnit + "</GUnit>");
		handlestring.append("</QueryStock>");

		System.out
				.println("handlestring.toString()=" + handlestring.toString());
		BlsParameterSet paraSet = storageBillDao.findBlsParameterSet();
		// 调用海关服务，获取库存查询结果（XML格式）
		String stockReturnInfo = BswHpServiceClient.getInstance(paraSet)
				.hpServiceExec(serviceInfostring, handlestring.toString());
		System.out.println("stockReturnInfo=" + stockReturnInfo);
		InputStream inputStream = new ByteArrayInputStream(stockReturnInfo
				.getBytes());
		Document doc;
		SAXBuilder sax = new SAXBuilder();
		try {
			TempStockReturn tempStockReturn = new TempStockReturn();
			List<TempQueryProduct> queryProducts = new ArrayList<TempQueryProduct>();
			doc = sax.build(inputStream);
			Element stockReturnElement = doc.getRootElement();
			// 命名空间
			Namespace ns = stockReturnElement.getNamespace();

			System.out.println("stockReturnElement.name="
					+ stockReturnElement.getName());
			Element serviceHandleElement = stockReturnElement.getChild(
					"ServiceHandle", ns);
			System.out.println("serviceHandleElement.name="
					+ serviceHandleElement.getName());
			if (serviceHandleElement != null) {
				tempStockReturn.setServiceHandle(serviceHandleElement
						.getTextTrim());
				str = serviceHandleElement.getTextTrim();
				System.out.println("serviceHandleElement.getTextTrim()="
						+ serviceHandleElement.getTextTrim());
			} else {
				System.out.println("null");
			}

			Element serviceStatusElement = stockReturnElement.getChild(
					"ServiceStatus", ns);
			if (serviceStatusElement != null) {
				tempStockReturn.setServiceStatus(serviceStatusElement
						.getTextTrim());
				System.out.println("serviceStatusElement.getTextTrim()="
						+ serviceStatusElement.getTextTrim());
			}

			Element descriptionElement = stockReturnElement.getChild(
					"Description", ns);
			if (descriptionElement != null) {
				tempStockReturn
						.setDescription(descriptionElement.getTextTrim());
				str = descriptionElement.getTextTrim();
				System.out.println("descriptionElement.getTextTrim()="
						+ descriptionElement.getTextTrim());
			}

			Element emsNoElement = stockReturnElement.getChild("EmsNo", ns);
			if (emsNoElement != null) {
				tempStockReturn.setEmsNo(emsNoElement.getTextTrim());
				System.out.println("emsNoElement.getTextTrim()="
						+ emsNoElement.getTextTrim());
			}

			Element warehouseCodeElement = stockReturnElement.getChild(
					"WarehouseCode", ns);
			if (warehouseCodeElement != null) {
				tempStockReturn.setWareHouseCode(warehouseCodeElement
						.getTextTrim());
				System.out.println("warehouseCodeElement.getTextTrim()="
						+ warehouseCodeElement.getTextTrim());
			}

			Element customsCodeElement = stockReturnElement.getChild(
					"CustomsCode", ns);
			if (customsCodeElement != null) {
				tempStockReturn
						.setCustomsCode(customsCodeElement.getTextTrim());
				System.out.println("customsCodeElement.getTextTrim()="
						+ customsCodeElement.getTextTrim());
			}

			Element queryProductsElement = stockReturnElement.getChild(
					"QueryProducts", ns);
			if (queryProductsElement != null) {
				List<Element> listQueryProducts = queryProductsElement
						.getChildren("QueryProduct", ns);
				if (listQueryProducts != null && !listQueryProducts.isEmpty()) {
					for (Element queryProductElement : listQueryProducts) {
						if (queryProductElement != null) {
							TempQueryProduct tempQueryProduct = new TempQueryProduct();

							Element contrItemElement = queryProductElement
									.getChild("ContrItem", ns);
							if (contrItemElement != null) {
								tempQueryProduct.setContrItem(contrItemElement
										.getTextTrim());
								System.out
										.println("contrItemElement.getTextTrim()="
												+ contrItemElement
														.getTextTrim());
							}

							Element codeTSElement = queryProductElement
									.getChild("CodeTS", ns);
							if (codeTSElement != null) {
								tempQueryProduct.setCodeTS(codeTSElement
										.getTextTrim());
								System.out
										.println("codeTSElement.getTextTrim()="
												+ codeTSElement.getTextTrim());
							}

							Element gNameElement = queryProductElement
									.getChild("GName", ns);
							if (gNameElement != null) {
								tempQueryProduct.setName((gNameElement
										.getTextTrim()));
								System.out
										.println("gNameElement.getTextTrim()="
												+ gNameElement.getTextTrim());
							}

							Element gModelElement = queryProductElement
									.getChild("GModel", ns);
							if (gModelElement != null) {
								tempQueryProduct.setModel((gModelElement
										.getTextTrim()));
								System.out
										.println("gModelElement.getTextTrim()="
												+ gModelElement.getTextTrim());
							}

							Element gUnitElement = queryProductElement
									.getChild("GUnit", ns);
							if (gUnitElement != null) {
								tempQueryProduct.setUnit((gUnitElement
										.getTextTrim()));
								System.out
										.println("gUnitElement.getTextTrim()="
												+ gUnitElement.getTextTrim());
							}

							Element leaveElement = queryProductElement
									.getChild("Leave", ns);
							if (leaveElement != null) {
								tempQueryProduct.setAmount(Double
										.valueOf((leaveElement.getTextTrim())));
								System.out
										.println("leaveElement.getTextTrim()="
												+ leaveElement.getTextTrim());

								System.out
										.println("Double.valueOf(leaveElement.getTextTrim())="
												+ Double.valueOf(leaveElement
														.getTextTrim()));
							}
							System.out.println("tempQueryProduct.getGModel()="
									+ tempQueryProduct.getModel());

							System.out.println("tempQueryProduct.getGName()="
									+ tempQueryProduct.getName());

							System.out.println("tempQueryProduct.getGUnit()="
									+ tempQueryProduct.getUnit());
							queryProducts.add(tempQueryProduct);
						}
					}
					tempStockReturn.setQueryProducts(queryProducts);
				}
			}
			listResult.add(tempStockReturn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// System.out.println("str="+str);
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listResult;
	}

	/**
	 * 查询仓单信息 hw
	 * 
	 * @param serviceInfostring
	 * @param handlestring
	 * @return
	 */
	public List findStorageBillStatusFromHP(String tradeCo, String billNo) {
		List<TempCollateItem> listResult = new ArrayList<TempCollateItem>();
		List<TempBillReturn> listBillReturn = new ArrayList<TempBillReturn>();
		// TempCollateEntity tempCollateEntity = new TempCollateEntity();
		String serviceInfostring = blsMessageLogic.getBlsServiceInfo(
				BlsServiceID.QUERY_BILL, (Company) CommonUtils.getCompany());
		// 生成查询参数(XML格式)
		StringBuffer handlestring = new StringBuffer();
		handlestring.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		handlestring.append("<QueryBill xmlns=\"un:epz\">");
		handlestring.append(" <TradeCo>" + tradeCo + "</TradeCo>");
		handlestring.append(" <BillNo>" + billNo + "</BillNo>");
		handlestring.append("</QueryBill>");

		System.out
				.println("handlestring.toString()=" + handlestring.toString());

		System.out.println("serviceInfostring=" + serviceInfostring);
		BlsParameterSet paraSet = storageBillDao.findBlsParameterSet();
		// 调用海关服务，获取库存查询结果（XML格式）
		String stockReturnInfo = BswHpServiceClient.getInstance(paraSet)
				.hpServiceExec(serviceInfostring, handlestring.toString());
		InputStream inputStream = new ByteArrayInputStream(stockReturnInfo
				.getBytes());
		Document doc;
		SAXBuilder sax = new SAXBuilder();
		try {

			List<TempCollateItem> listCollateItemResults1 = new ArrayList<TempCollateItem>();
			List<TempCollateItem> listCollateItemResults2 = new ArrayList<TempCollateItem>();
			List<TempCollateItem> listCollateItemResults3 = new ArrayList<TempCollateItem>();
			List<TempQueryBillList> listQueryBillLists = new ArrayList<TempQueryBillList>();
			TempBillReturn tempBillReturn = new TempBillReturn();
			doc = sax.build(inputStream);
			Element billReturnElement = doc.getRootElement();
			Namespace ns = billReturnElement.getNamespace();
			Element serviceHandleElement = billReturnElement.getChild(
					"ServiceHandle", ns);
			if (serviceHandleElement != null) {
				tempBillReturn.setServiceHandle(serviceHandleElement
						.getTextTrim());
			}

			Element serviceStatusElement = billReturnElement.getChild(
					"ServiceStatus", ns);
			if (serviceStatusElement != null) {
				tempBillReturn.setServiceStatus(serviceStatusElement
						.getTextTrim());
			}

			Element descriptionElement = billReturnElement.getChild(
					"Description", ns);
			if (descriptionElement != null) {
				tempBillReturn.setDescription(descriptionElement.getTextTrim());
			}

			Element tradeCoElement = billReturnElement.getChild("TradeCo", ns);
			if (descriptionElement != null) {
				tempBillReturn.setTradeCo(tradeCoElement.getTextTrim());
			}

			Element emsNoElement = billReturnElement.getChild("EmsNo", ns);
			if (emsNoElement != null) {
				tempBillReturn.setEmsNo(emsNoElement.getTextTrim());
			}

			Element wareHouseCodeElement = billReturnElement.getChild(
					"WareHouseCode", ns);
			if (wareHouseCodeElement != null) {
				tempBillReturn.setWareHouseCode(wareHouseCodeElement
						.getTextTrim());
			}

			Element customsCodeElement = billReturnElement.getChild(
					"CustomsCode", ns);
			if (customsCodeElement != null) {
				tempBillReturn.setCustomsCode(customsCodeElement.getTextTrim());
			}

			Element billNoElement = billReturnElement.getChild("BillNo", ns);
			if (billNoElement != null) {
				tempBillReturn.setBillNo(billNoElement.getTextTrim());
			}

			Element iOFlagElement = billReturnElement.getChild("IOFlag", ns);
			if (iOFlagElement != null) {
				tempBillReturn.setIOFlag(iOFlagElement.getTextTrim());
			}

			Element iEPortElement = billReturnElement.getChild("IEPort", ns);
			if (iEPortElement != null) {
				tempBillReturn.setIEPort(iEPortElement.getTextTrim());
			}

			Element deliveryNosElement = billReturnElement.getChild(
					"DeliveryNo", ns);
			if (deliveryNosElement != null) {
				tempBillReturn.setDeliveryNo(deliveryNosElement.getTextTrim());
			}

			Element billTypeElement = billReturnElement
					.getChild("BillType", ns);
			if (billTypeElement != null) {
				tempBillReturn.setBillType(billTypeElement.getTextTrim());
			}

			Element itemsCountElement = billReturnElement.getChild(
					"ItemsCount", ns);
			if (itemsCountElement != null) {
				tempBillReturn.setItemsCount(Integer.parseInt(itemsCountElement
						.getTextTrim()));
			}

			Element billOperTimeElement = billReturnElement.getChild(
					"BillOperTime", ns);
			if (billOperTimeElement != null) {
				tempBillReturn.setBillOperTime(billOperTimeElement
						.getTextTrim());
			}

			Element isDeliveryArrivedElement = billReturnElement.getChild(
					"IsDeliveryArrived", ns);
			if (isDeliveryArrivedElement != null) {
				tempBillReturn.setIsDeliveryArrived(Integer
						.parseInt(isDeliveryArrivedElement.getTextTrim()));
			}

			Element deliveryInfoElement = billReturnElement.getChild(
					"DeliveryInfo", ns);
			if (deliveryInfoElement != null) {
				TempDeliveryEntity tempDeliveryEntity = new TempDeliveryEntity();
				Element deliveryNoElement = deliveryInfoElement.getChild(
						"DeliveryNo", ns);
				if (deliveryNoElement != null) {
					tempDeliveryEntity.setDeliveryNo(deliveryNoElement
							.getTextTrim());
				}

				Element vehicleLicenseElement = deliveryInfoElement.getChild(
						"VehicleLicense", ns);
				if (vehicleLicenseElement != null) {
					tempDeliveryEntity.setVehicleLicense(vehicleLicenseElement
							.getTextTrim());
				}

				Element deliveryOperTimeElement = deliveryInfoElement.getChild(
						"DeliveryOperTime", ns);
				if (deliveryOperTimeElement != null) {
					tempDeliveryEntity
							.setDeliveryOperTime(deliveryOperTimeElement
									.getTextTrim());
				}
				tempBillReturn.setDeliveryInfo(tempDeliveryEntity);
			}

			Element isCollateMftInOutFinishendElement = billReturnElement
					.getChild("IsCollateMftInOutFinishend", ns);
			if (isCollateMftInOutFinishendElement != null) {
				tempBillReturn.setIsCollateMftInOutFinishend(Integer
						.parseInt(isCollateMftInOutFinishendElement
								.getTextTrim()));
			}

			Element isCollateMftEntFinishedElement = billReturnElement
					.getChild("IsCollateMftEntFinished", ns);
			if (isCollateMftEntFinishedElement != null) {
				tempBillReturn
						.setIsCollateMftEntFinished(Integer
								.parseInt(isCollateMftEntFinishedElement
										.getTextTrim()));
			}

			Element queryBillListsElement = billReturnElement.getChild(
					"QueryBillLists", ns);
			if (queryBillListsElement != null) {
				List<Element> listQueryBillList = queryBillListsElement
						.getChildren("QueryBillList", ns);
				if (listQueryBillList != null && !listQueryBillList.isEmpty()) {
					TempQueryBillList tempQueryBillList = new TempQueryBillList();
					for (Element queryBillListElement : listQueryBillList) {

						Element gNoElement = queryBillListElement.getChild(
								"GNo", ns);
						if (gNoElement != null) {
							tempQueryBillList.setNo(gNoElement.getTextTrim());
						}

						Element copGNoElement = queryBillListElement.getChild(
								"CopGNo", ns);
						if (copGNoElement != null) {
							tempQueryBillList.setCopGNo(gNoElement
									.getTextTrim());
						}

						Element contrItemElement = queryBillListElement
								.getChild("ContrItem", ns);
						if (contrItemElement != null) {
							tempQueryBillList.setContrItem(Integer
									.parseInt(contrItemElement.getTextTrim()));
						}

						Element codeTSElement = queryBillListElement.getChild(
								"CodeTS", ns);
						if (codeTSElement != null) {
							tempQueryBillList.setCodeTS(codeTSElement
									.getTextTrim());
						}

						Element gNameElement = queryBillListElement.getChild(
								"GName", ns);
						if (gNameElement != null) {
							tempQueryBillList.setName(gNameElement
									.getTextTrim());
						}

						Element gModelElement = queryBillListElement.getChild(
								"GModel", ns);
						if (gModelElement != null) {
							tempQueryBillList.setModel(gModelElement
									.getTextTrim());
						}

						Element qty1Element = queryBillListElement.getChild(
								"Qty1", ns);
						if (qty1Element != null) {
							tempQueryBillList.setQty1(Double
									.parseDouble((qty1Element.getTextTrim())));
						}

						Element qty2Element = queryBillListElement.getChild(
								"Qty2", ns);
						if (qty2Element != null) {
							tempQueryBillList.setQty2(Double
									.parseDouble((qty2Element.getTextTrim())));
						}

						Element gUnitElement = queryBillListElement.getChild(
								"GUnit", ns);
						if (gUnitElement != null) {
							tempQueryBillList.setUnit((gUnitElement
									.getTextTrim()));
						}

						Element qtyElement = queryBillListElement.getChild(
								"Qty", ns);
						if (qtyElement != null) {
							tempQueryBillList.setQty(Double
									.parseDouble((qtyElement.getTextTrim())));
						}

						Element unitPriceElement = queryBillListElement
								.getChild("UnitPrice", ns);
						if (unitPriceElement != null) {
							tempQueryBillList.setUnitPrice(Double
									.parseDouble((unitPriceElement
											.getTextTrim())));
						}

						Element totalPriceElement = queryBillListElement
								.getChild("TotalPrice", ns);
						if (totalPriceElement != null) {
							tempQueryBillList.setTotalPrice(Double
									.parseDouble((totalPriceElement
											.getTextTrim())));
						}

						Element currElement = queryBillListElement.getChild(
								"Curr", ns);
						if (currElement != null) {
							tempQueryBillList.setCurr((currElement
									.getTextTrim()));
						}

						Element originCountryElement = queryBillListElement
								.getChild("OriginCountry", ns);
						if (originCountryElement != null) {
							tempQueryBillList
									.setOriginCountry((originCountryElement
											.getTextTrim()));
						}

						Element tradeModeElement = queryBillListElement
								.getChild("TradeMode", ns);
						if (tradeModeElement != null) {
							tempQueryBillList.setTradeMode((tradeModeElement
									.getTextTrim()));
						}

						Element transModeElement = queryBillListElement
								.getChild("TransMode", ns);
						if (transModeElement != null) {
							tempQueryBillList.setTransMode((transModeElement
									.getTextTrim()));
						}

						Element collateMftInOutsElement = queryBillListElement
								.getChild("CollateMftInOut", ns);
						if (collateMftInOutsElement != null) {
							System.out.print(collateMftInOutsElement.getName());
							TempCollateEntity tempCollateEntity = new TempCollateEntity();

							Element collateTypeElement = collateMftInOutsElement
									.getChild("CollateType", ns);
							if (collateTypeElement != null) {
								tempCollateEntity
										.setCollateType((collateTypeElement
												.getTextTrim()));
								System.out
										.println("collateTypeElement.getTextTrim()="
												+ collateTypeElement
														.getTextTrim());
							}

							Element isCollateFinishedElement = collateMftInOutsElement
									.getChild("IsCollateFinished", ns);
							if (isCollateFinishedElement != null) {
								tempCollateEntity.setIsCollateFinished(Integer
										.parseInt((isCollateFinishedElement
												.getTextTrim())));
								System.out
										.println("isCollateFinishedElement.getTextTrim()="
												+ isCollateFinishedElement
														.getTextTrim());

							}

							Element collateTotalCountElement = collateMftInOutsElement
									.getChild("CollateTotalCount", ns);
							if (collateTotalCountElement != null) {
								tempCollateEntity.setCollateTotalCount(Double
										.parseDouble((collateTotalCountElement
												.getTextTrim())));
								System.out
										.println("collateTotalCountElement.getTextTrim()="
												+ collateTotalCountElement
														.getTextTrim());
							}

							Element collatedCountElement = collateMftInOutsElement
									.getChild("CollatedCount", ns);
							if (collatedCountElement != null) {
								tempCollateEntity.setCollatedCount(Double
										.parseDouble((collatedCountElement
												.getTextTrim())));
								System.out
										.println("collatedCountElement.getTextTrim()="
												+ collatedCountElement
														.getTextTrim());
							}

							Element collateItemsElement = collateMftInOutsElement
									.getChild("CollateItems", ns);
							if (collateItemsElement != null) {
								List<Element> listCollateEntity = collateItemsElement
										.getChildren("CollateItem", ns);
								if (listCollateEntity != null
										&& !listCollateEntity.isEmpty()) {
									for (Element collateItemElement : listCollateEntity) {
										if (collateItemElement != null) {
											TempCollateItem tempCollateItem = new TempCollateItem();

											Element collateItemTypeElement = collateItemElement
													.getChild(
															"CollateItemType",
															ns);
											if (collateItemTypeElement != null) {
												tempCollateItem
														.setCollateItemType(collateItemTypeElement
																.getTextTrim());
												System.out
														.println("collateItemTypeElement.getTextTrim()="
																+ collateItemTypeElement
																		.getTextTrim());
											}

											Element collateItemFormIDElement = collateItemElement
													.getChild(
															"CollateItemFormID",
															ns);
											if (collateItemFormIDElement != null) {
												tempCollateItem
														.setCollateItemFormID(collateItemFormIDElement
																.getTextTrim());
												System.out
														.println("collateItemFormIDElement.getTextTrim()="
																+ collateItemFormIDElement
																		.getTextTrim());
											}

											Element collateItemFormGNoElement = collateItemElement
													.getChild(
															"CollateItemFormGNo",
															ns);
											if (collateItemFormGNoElement != null) {
												tempCollateItem
														.setCollateItemFormGNo(Integer
																.parseInt(collateItemFormGNoElement
																		.getTextTrim()));
												System.out
														.println("collateItemFormGNoElement.getTextTrim()="
																+ collateItemFormGNoElement
																		.getTextTrim());
											}

											Element collateItemFormCountElement = collateItemElement
													.getChild(
															"CollateItemFormCount",
															ns);
											if (collateItemFormCountElement != null) {
												tempCollateItem
														.setCollateItemFormCount(Double
																.parseDouble(collateItemFormCountElement
																		.getTextTrim()));
												System.out
														.print("collateItemFormCountElement.getTextTrim()="
																+ collateItemFormCountElement
																		.getTextTrim());
											}

											Element collateItemOperTimeElement = collateItemElement
													.getChild(
															"CollateItemOperTime",
															ns);
											if (collateItemOperTimeElement != null) {
												tempCollateItem
														.setCollateItemOperTime(collateItemOperTimeElement
																.getTextTrim());
												System.out
														.println("collateItemOperTimeElement.getTextTrim()"
																+ collateItemOperTimeElement
																		.getTextTrim());
											}
											listCollateItemResults1
													.add(tempCollateItem);
											System.out
													.println("tempCollateItem="
															+ tempCollateItem);
										}
										tempCollateEntity
												.setCollateItems(listCollateItemResults1);
									}
								}
							}
							tempQueryBillList
									.setCollateMftInOut(tempCollateEntity);
						}
						// 仓单报关单明细
						Element collateMftEntsElement = queryBillListElement
								.getChild("CollateMftEnt", ns);
						TempCollateEntity tempCollateEntity = new TempCollateEntity();
						if (collateMftEntsElement != null) {
							Element collateTypeElement = collateMftEntsElement
									.getChild("CollateType", ns);
							if (collateTypeElement != null) {
								tempCollateEntity
										.setCollateType((collateTypeElement
												.getTextTrim()));
							}

							Element isCollateFinishedElement = collateMftEntsElement
									.getChild("IsCollateFinished", ns);
							if (isCollateFinishedElement != null) {
								tempCollateEntity.setIsCollateFinished(Integer
										.parseInt((isCollateFinishedElement
												.getTextTrim())));
							}

							Element collateTotalCountElement = collateMftEntsElement
									.getChild("CollateTotalCount", ns);
							if (collateTotalCountElement != null) {
								tempCollateEntity.setCollateTotalCount(Double
										.parseDouble((collateTotalCountElement
												.getTextTrim())));
							}

							Element collatedCountElement = collateMftEntsElement
									.getChild("CollatedCount", ns);
							if (collatedCountElement != null) {
								tempCollateEntity.setCollatedCount(Double
										.parseDouble((collatedCountElement
												.getTextTrim())));
							}

							Element collateItemsElement = collateMftEntsElement
									.getChild("CollateItems", ns);
							if (collateItemsElement != null) {
								List<Element> listCollateEntity = collateItemsElement
										.getChildren("CollateItem", ns);
								if (listCollateEntity != null
										&& !listCollateEntity.isEmpty()) {
									for (Element collateItemElements : listCollateEntity) {
										if (collateItemElements != null) {
											TempCollateItem tempCollateItem = new TempCollateItem();

											Element collateItemTypeElement = collateItemElements
													.getChild(
															"CollateItemType",
															ns);
											if (collateItemTypeElement != null) {
												tempCollateItem
														.setCollateItemType(collateItemTypeElement
																.getTextTrim());
											}

											Element collateItemFormIDElement = collateItemElements
													.getChild(
															"CollateItemFormID",
															ns);
											if (collateItemFormIDElement != null) {
												tempCollateItem
														.setCollateItemFormID(collateItemFormIDElement
																.getTextTrim());
											}

											Element collateItemFormGNoElement = collateItemElements
													.getChild(
															"CollateItemFormGNo",
															ns);
											if (collateItemFormGNoElement != null) {
												tempCollateItem
														.setCollateItemFormGNo(Integer
																.parseInt(collateItemFormGNoElement
																		.getTextTrim()));
											}

											Element collateItemFormCountElement = collateItemElements
													.getChild(
															"CollateItemFormCount",
															ns);
											if (collateItemFormCountElement != null) {
												tempCollateItem
														.setCollateItemFormCount(Double
																.parseDouble(collateItemFormCountElement
																		.getTextTrim()));
											}

											Element collateItemOperTimeElement = collateItemElements
													.getChild(
															"CollateItemOperTime",
															ns);
											if (collateItemOperTimeElement != null) {
												tempCollateItem
														.setCollateItemOperTime(collateItemOperTimeElement
																.getTextTrim());
											}
											listCollateItemResults2
													.add(tempCollateItem);
										}
										tempCollateEntity
												.setCollateItems(listCollateItemResults2);
									}
								}
							}
						}
						tempQueryBillList.setCollateMftEnt(tempCollateEntity);

						// 库存核扣明细
						Element collateMftStocksElement = queryBillListElement
								.getChild("CollateMftEnt", ns);
						TempCollateEntity collateMftStock = new TempCollateEntity();
						if (collateMftStocksElement != null) {
							Element collateTypeElement = collateMftStocksElement
									.getChild("CollateType", ns);
							if (collateTypeElement != null) {
								collateMftStock
										.setCollateType((collateTypeElement
												.getTextTrim()));
							}

							Element isCollateFinishedElement = collateMftStocksElement
									.getChild("IsCollateFinished", ns);
							if (isCollateFinishedElement != null) {
								collateMftStock.setIsCollateFinished(Integer
										.parseInt((isCollateFinishedElement
												.getTextTrim())));
							}

							Element collateTotalCountElement = collateMftStocksElement
									.getChild("CollateTotalCount", ns);
							if (collateTotalCountElement != null) {
								collateMftStock.setCollateTotalCount(Double
										.parseDouble((collateTotalCountElement
												.getTextTrim())));
							}

							Element collatedCountElement = collateMftStocksElement
									.getChild("CollatedCount", ns);
							if (collatedCountElement != null) {
								collateMftStock.setCollatedCount(Double
										.parseDouble((collatedCountElement
												.getTextTrim())));
							}

							Element collateItemsElement = collateMftStocksElement
									.getChild("CollateItems", ns);
							if (collateItemsElement != null) {
								List<Element> listCollateEntity = collateItemsElement
										.getChildren("CollateItem", ns);
								if (listCollateEntity != null
										&& !listCollateEntity.isEmpty()) {
									for (Element collateItemElements : listCollateEntity) {
										if (collateItemElements != null) {
											TempCollateItem tempCollateItem = new TempCollateItem();

											Element collateItemTypeElement = collateItemElements
													.getChild(
															"CollateItemType",
															ns);
											if (collateItemTypeElement != null) {
												tempCollateItem
														.setCollateItemType(collateItemTypeElement
																.getTextTrim());
											}

											Element collateItemFormIDElement = collateItemElements
													.getChild(
															"CollateItemFormID",
															ns);
											if (collateItemFormIDElement != null) {
												tempCollateItem
														.setCollateItemFormID(collateItemFormIDElement
																.getTextTrim());
											}

											Element collateItemFormGNoElement = collateItemElements
													.getChild(
															"CollateItemFormGNo",
															ns);
											if (collateItemFormGNoElement != null) {
												tempCollateItem
														.setCollateItemFormGNo(Integer
																.parseInt(collateItemFormGNoElement
																		.getTextTrim()));
											}

											Element collateItemFormCountElement = collateItemElements
													.getChild(
															"CollateItemFormCount",
															ns);
											if (collateItemFormCountElement != null) {
												tempCollateItem
														.setCollateItemFormCount(Double
																.parseDouble(collateItemFormCountElement
																		.getTextTrim()));
											}

											Element collateItemOperTimeElement = collateItemElements
													.getChild(
															"CollateItemOperTime",
															ns);
											if (collateItemOperTimeElement != null) {
												tempCollateItem
														.setCollateItemOperTime(collateItemOperTimeElement
																.getTextTrim());
											}
											listCollateItemResults3
													.add(tempCollateItem);
										}
										collateMftStock
												.setCollateItems(listCollateItemResults3);
									}
								}
							}
						}
						tempQueryBillList.setCollateMftStock(collateMftStock);

					}
					listQueryBillLists.add(tempQueryBillList);
				}
				tempBillReturn.setQueryBillLists(listQueryBillLists);
			}
			listBillReturn.add(tempBillReturn);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listBillReturn;
	}

	/**
	 * 改变车次管理表头的申报状态
	 * 
	 * @param delivery
	 * @param declareState
	 * @return
	 */
	public Delivery changeDeliveryDeclareState(Delivery delivery,
			String declareState) {
		delivery = (Delivery) this.storageBillDao.load(delivery.getClass(),
				delivery.getId());
		if (delivery == null
				|| !DeclareState.WAIT_EAA.equals(delivery.getDeclareState())) {
			return delivery;
		}
		delivery.setDeclareState(declareState);
		this.storageBillDao.saveOrUpdate(delivery);
		return delivery;
	}

	/**
	 * 根据仓单号，查询仓单的状态
	 * 
	 * @param billNo
	 * @param company
	 * @return
	 */
	public String getDeliveryServiceStatus(String billNo, Company company) {
		List list = this.storageBillDao.findDeliveryByBillNo(billNo, company);
		if (list.size() > 0 && list.get(0) != null) {
			Delivery delivery = (Delivery) list.get(0);
			BlsReceiptResult receiptResult = this.blsMessageLogic
					.findBlsReceiptResultFromHG(
							BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
							company);
			return receiptResult.getServiceStatus();
		} else {
			throw new RuntimeException("没有找到包含仓单号是:" + billNo + "的车次");
		}
	}
}
