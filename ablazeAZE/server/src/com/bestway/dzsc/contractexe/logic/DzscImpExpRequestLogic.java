/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pf.text.StringUtil;

import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.bcus.innermerge.entity.TempMateriel;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.logic.BaseEncLogic;
import com.bestway.dzsc.contractexe.dao.DzscContractExeDao;
import com.bestway.dzsc.contractexe.dao.DzscImpExpRequestDao;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationContainer;
import com.bestway.dzsc.contractexe.entity.MakeDzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.TempDzscImpExpCommodityInfo;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.jptds.client.common.CommonVars;

/**
 * com.bestway.dzsc.contractexe.logic.DzscImpExpRequestLogic
 * 
 * @author Administrator 进出口申请单转报关单(逻辑)
 */
public class DzscImpExpRequestLogic {
	private DzscDao dzscDao = null;

	private DzscImpExpRequestDao dzscImpExpRequestDao = null;

	private DzscContractExeDao dzscEncDao = null;

	private DzscContractExeLogic dzscEncLogic = null;

	// private CompanyDao companyDao = null;

	/**
	 * 获取dzscDao
	 * 
	 * @return dzscDao
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
	}

	/**
	 * 获取dzscEncLogic
	 * 
	 * @return dzscEncLogic
	 */
	public DzscContractExeLogic getDzscEncLogic() {
		return dzscEncLogic;
	}

	/**
	 * 设置dzscEncLogic
	 * 
	 * @param dzscEncLogic
	 */
	public void setDzscEncLogic(DzscContractExeLogic dzscEncLogic) {
		this.dzscEncLogic = dzscEncLogic;
	}

	/**
	 * 获取dzscEncDao
	 * 
	 * @return dzscEncDao
	 */
	public DzscContractExeDao getDzscEncDao() {
		return dzscEncDao;
	}

	/**
	 * 设置dzscEncDao
	 * 
	 * @param dzscEncDao
	 */
	public void setDzscEncDao(DzscContractExeDao dzscEncDao) {
		this.dzscEncDao = dzscEncDao;
	}

	/**
	 * 获取dzscImpExpRequestDao
	 * 
	 * @return dzscImpExpRequestDao
	 */
	public DzscImpExpRequestDao getDzscImpExpRequestDao() {
		return dzscImpExpRequestDao;
	}

	/**
	 * 设置dzscImpExpRequestDao
	 * 
	 * @param dzscImpExpRequestDao
	 */
	public void setDzscImpExpRequestDao(
			DzscImpExpRequestDao dzscImpExpRequestDao) {
		this.dzscImpExpRequestDao = dzscImpExpRequestDao;
	}

	/**
	 * 设置dzscDao
	 * 
	 * @param dzscDao
	 */
	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	/**
	 * 分页查找物料来自过滤Dzsc申请单明细
	 * 
	 * @param materielType
	 *            物料类别
	 * @param impExpRequestBill
	 *            申请单表头
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            用“like”还是用“＝”，当为true是用“like”
	 * @return List 是TempMateriel型，临时物料
	 */
	public List findMaterielByDzscRequestBill(String materielType,
			ImpExpRequestBill impExpRequestBill, int index, int length,
			String property, Object value, Boolean isLike) {
		List newList = new ArrayList();
		List emsList = dzscDao.findDzscEmsPorHead(DzscState.EXECUTE);
		/**
		 * 过滤的 hSql
		 */
		String conditionSql = " and m.id not in (select  c.materiel.id "
				+ "from ImpExpCommodityInfo c "
				+ "where c.impExpRequestBill.id = ? ) ";
		List<Object> paramters = new ArrayList<Object>();
		/**
		 * 过滤的 参数
		 */
		paramters.add(impExpRequestBill.getId());

		if (emsList == null || emsList.size() <= 0) { // 没有正在执行的海关合同
			/**
			 * 将返回所有的末备案的物料主档商品信息
			 */
			List list = this.dzscImpExpRequestDao.findMaterielByType(
					materielType, conditionSql, paramters.toArray(), index,
					length, property, value, isLike);
			for (int i = 0; i < list.size(); i++) {
				TempMateriel tempMateriel = new TempMateriel();
				tempMateriel.setIsMemo(new Boolean(false));
				tempMateriel.setMateriel((Materiel) list.get(i));
				newList.add(tempMateriel);
			}
			return newList;
		}
		/**
		 * 备案的记录
		 */
		List list = this.dzscImpExpRequestDao
				.findMaterielPutOnRecordsByDzscRequestBill(materielType,
						conditionSql, paramters.toArray(), index, length,
						property, value, isLike);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(true));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		/**
		 * 未备案的数据
		 */
		if (list.size() == 0) {
			list = this.dzscImpExpRequestDao
					.findMaterielNotPutOnRecordsByDzscRequestBill(materielType,
							conditionSql, paramters.toArray(), index, length,
							property, value, isLike);
		} else if (list.size() < length) {
			list = this.dzscImpExpRequestDao
					.findMaterielNotPutOnRecordsByDzscRequestBill(materielType,
							conditionSql, paramters.toArray(), index
									+ (list.size()), length - (list.size()),
							property, value, isLike);
		}
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;
	}

	/**
	 * 申请单转报关单
	 * 
	 * @param dzscCustomsDeclaration
	 * @param temps
	 * @param isProduct
	 */
	public String makeDzscCustomsDeclarationFromTemp(
			DzscCustomsDeclaration dzscCustomsDeclaration,
			List<TempDzscImpExpCommodityInfo> temps, boolean isProduct,
			boolean isNewRecord) {
		Map<String, TempDzscImpExpCommodityInfo> tempMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		// // 把所有 (相同手册，归并序号)进行合并
		// TempDzscImpExpCommodityInfo oldTemp;
		// for(TempDzscImpExpCommodityInfo temp : temps){
		// oldTemp = tempMap.get(getKey(temp, isProduct));
		// if(oldTemp!=null){//数量，金额累加
		// oldTemp.getImpExpCommodityInfo().setQuantity(CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getQuantity())
		// +CommonUtils.getDoubleExceptNull(oldTemp.getImpExpCommodityInfo().getQuantity()));
		// oldTemp.getImpExpCommodityInfo().setAmountPrice(CommonUtils.getDoubleExceptNull(temp.getImpExpCommodityInfo().getQuantity())
		// +CommonUtils.getDoubleExceptNull(oldTemp.getImpExpCommodityInfo().getQuantity()));
		// }
		// tempMap.put(getKey(temp, isProduct), temp);
		// }
		// List<TempDzscImpExpCommodityInfo> newTemps = new
		// ArrayList(tempMap.values());
		// 更新前检查数量，金额
		Double[] remain;
		Double[] change = new Double[2];
		for (TempDzscImpExpCommodityInfo newTemp : temps) {
			// 合同剩余数量，金额
			if (isProduct) {
				remain = processExgRemainContractMountAndMoney(newTemp
						.getDzscEmsExgBill());
			} else {
				remain = processImgRemainContractMountAndMoney(newTemp
						.getDzscEmsImgBill());
			}
			change[0] = newTemp.getImpExpCommodityInfo().getQuantity();
			change[1] = newTemp.getImpExpCommodityInfo().getAmountPrice();
			if (equalsDouble(change, remain)) {// 转换数据大于
				return "系统数据已被更改，请重新分配......";
			}
		}
		// key:帐册编号 value:报关单
		Map<String, DzscCustomsDeclaration> emsMap = new HashMap();
		// 报关单表头
		DzscCustomsDeclaration customsDeclaration;
		// 生成报关单
		if (isProduct) {// 成品
			for (TempDzscImpExpCommodityInfo newTemp : temps) {
				if ((customsDeclaration = emsMap.get(newTemp
						.getDzscEmsExgBill().getDzscEmsPorHead().getEmsNo())) == null) {
					if (!isNewRecord) {
						customsDeclaration = dzscCustomsDeclaration;
					} else {
						customsDeclaration = makeDzscCustomsDeclarationFromTemp(
								newTemp, dzscCustomsDeclaration, isProduct);
						sb.append("生成出品报关单,报关单流水号："
								+ customsDeclaration.getSerialNumber() + "\n");
					}
					emsMap.put(newTemp.getDzscEmsExgBill().getDzscEmsPorHead()
							.getEmsNo(), customsDeclaration);
				}// 生成表头
				// makeDzscCustomsDeclarationCommInfoByProduct(customsDeclaration,commInfo,newTemp,1d);
				makeDzscCustomsDeclarationCommInfoProduct(customsDeclaration,
						newTemp);// 生成报关单商品
				newTemp.getImpExpCommodityInfo().setIsTransferCustomsBill(true);// 设置已转报关单

				dzscEncDao.saveOrUpdate(newTemp.getImpExpCommodityInfo());
			}
		} else {
			for (TempDzscImpExpCommodityInfo newTemp : temps) {
				if ((customsDeclaration = emsMap.get(newTemp
						.getDzscEmsImgBill().getDzscEmsPorHead().getEmsNo())) == null) {
					if (!isNewRecord) {
						customsDeclaration = dzscCustomsDeclaration;
					} else {
						customsDeclaration = makeDzscCustomsDeclarationFromTemp(
								newTemp, dzscCustomsDeclaration, isProduct);
						sb.append("生成进口报关单,报关单流水号："
								+ customsDeclaration.getSerialNumber() + "\n");
					}
					emsMap.put(newTemp.getDzscEmsImgBill().getDzscEmsPorHead()
							.getEmsNo(), customsDeclaration);
				}// 生成表头
				// makeDzscCustomsDeclarationCommInfoByMateriel(customsDeclaration,commInfo,newTemp);//生成报关单商品
				makeDzscCustomsDeclarationCommInfoMe(customsDeclaration,
						newTemp);// 生成报关单商品
				newTemp.getImpExpCommodityInfo().setIsTransferCustomsBill(true);// 设置已转报关单
				dzscEncDao.saveOrUpdate(newTemp.getImpExpCommodityInfo());
			}
		}
		/**
		 * 是否自动计算报关单件数，毛重及净重
		 * 
		 */
		CompanyOther other = dzscDao.getSysCompanyOther();
		if (other != null) {
			if (other.getIsAutoWeight() != null && other.getIsAutoWeight()
					&& dzscCustomsDeclaration != null) {
				getPiceAndWeight(dzscCustomsDeclaration);
			}
		}
		sb.append("生成报关单成功!\n");
		return sb.toString();
	}

	/**
	 * 计算报关单头件，毛，净重
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public void getPiceAndWeight(BaseCustomsDeclaration baseCustomsDeclaration) {
		CompanyOther other = dzscDao.getSysCompanyOther();
		int fraction = 4;
		if (other != null && other.getIsAutoWeightRound() != null
				&& other.getIsAutoWeightRound()) {
			fraction = 0;
		}

		List list = dzscEncDao.getInfoSum(baseCustomsDeclaration);
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			/**
			 * 件数
			 */
			if (obj[0] != null
					&& !"".equals(obj[0])
					&& Double.valueOf(obj[0].toString()).doubleValue() != Double
							.valueOf(0)) {
				baseCustomsDeclaration.setCommodityNum(Integer
						.valueOf(formatBig(obj[0], 0)));
			}
			/**
			 * 净重
			 */
			if (obj[2] != null
					&& !"".equals(obj[2])
					&& Double.valueOf(obj[2].toString()).doubleValue() != Double
							.valueOf(0)) {
				baseCustomsDeclaration.setNetWeight(Double.valueOf(formatBig(
						obj[2], fraction)));
			}
			/**
			 * 毛重
			 */
			if (obj[1] != null
					&& !"".equals(obj[1])
					&& Double.valueOf(obj[1].toString()).doubleValue() != Double
							.valueOf(0)) {
				baseCustomsDeclaration.setGrossWeight(Double.valueOf(formatBig(
						obj[1], fraction)));
			}
			dzscEncDao.saveOrUpdate(baseCustomsDeclaration);
		}
	}

	/**
	 * 生成报关单表头
	 */
	private DzscCustomsDeclaration makeDzscCustomsDeclarationFromTemp(
			TempDzscImpExpCommodityInfo newTemp,
			DzscCustomsDeclaration dzscCustomsDeclaration, boolean isProduct) {
		DzscCustomsDeclaration customsDeclaration = new DzscCustomsDeclaration();
		if (isProduct) {
			customsDeclaration.setImpExpFlag(ImpExpFlag.EXPORT);// 出品
			customsDeclaration.setContract(newTemp.getDzscEmsExgBill()
					.getDzscEmsPorHead().getIeContractNo());// 设置合同号
			customsDeclaration.setSerialNumber(this.dzscEncDao
					.getCustomsDeclarationSerialNumber(ImpExpFlag.IMPORT,
							newTemp.getDzscEmsExgBill().getDzscEmsPorHead()
									.getEmsNo()));// 流水号
			customsDeclaration.setEmsHeadH2k(newTemp.getDzscEmsExgBill()
					.getDzscEmsPorHead().getEmsNo());// 手册编号
			customsDeclaration.setTradeCode(newTemp.getDzscEmsExgBill()
					.getDzscEmsPorHead().getTradeCode());// 经营单位代码
			customsDeclaration.setTradeName(newTemp.getDzscEmsExgBill()
					.getDzscEmsPorHead().getTradeName());// 经营单位名称
			customsDeclaration.setMachCode(newTemp.getDzscEmsExgBill()
					.getDzscEmsPorHead().getMachCode());// 收货单位代码
			customsDeclaration.setMachName(newTemp.getDzscEmsExgBill()
					.getDzscEmsPorHead().getMachName());// 收货单位名称
		} else {
			customsDeclaration.setImpExpFlag(ImpExpFlag.IMPORT);// 出品
			customsDeclaration.setContract(newTemp.getDzscEmsImgBill()
					.getDzscEmsPorHead().getIeContractNo());// 设置合同号
			customsDeclaration.setSerialNumber(this.dzscEncDao
					.getCustomsDeclarationSerialNumber(ImpExpFlag.IMPORT,
							newTemp.getDzscEmsImgBill().getDzscEmsPorHead()
									.getEmsNo()));// 流水号
			customsDeclaration.setEmsHeadH2k(newTemp.getDzscEmsImgBill()
					.getDzscEmsPorHead().getEmsNo());// 手册编号
			customsDeclaration.setTradeCode(newTemp.getDzscEmsImgBill()
					.getDzscEmsPorHead().getTradeCode());// 经营单位代码
			customsDeclaration.setTradeName(newTemp.getDzscEmsImgBill()
					.getDzscEmsPorHead().getTradeName());// 经营单位名称
			customsDeclaration.setMachCode(newTemp.getDzscEmsImgBill()
					.getDzscEmsPorHead().getMachCode());// 收货单位代码
			customsDeclaration.setMachName(newTemp.getDzscEmsImgBill()
					.getDzscEmsPorHead().getMachName());// 收货单位名称
		}
		// 进出口公共部分

		customsDeclaration
				.setImpExpType(dzscCustomsDeclaration.getImpExpType());// 进出口类型
		customsDeclaration.setCreater(CommonUtils.getAclUser());// 设置人员
		customsDeclaration.setCompany(CommonUtils.getCompany());// 设置公司
		customsDeclaration.setCustomser(((Company) CommonUtils.getCompany())
				.getAppCusMan());// 报关员
		customsDeclaration.setTelephone(((Company) CommonUtils.getCompany())
				.getAppCusManTel());// 报关员联系电话
		customsDeclaration.setCustoms(dzscCustomsDeclaration.getCustoms());// 进出口岸
		customsDeclaration.setDeclarationCustoms(dzscCustomsDeclaration
				.getDeclarationCustoms());// 申报的海关
		customsDeclaration.setImpExpDate(new Date());// 进出口日期
		customsDeclaration.setTransferMode(dzscCustomsDeclaration
				.getTransferMode());// 运输工具
		customsDeclaration.setTradeMode(dzscCustomsDeclaration.getTradeMode());// 贸易方式
		customsDeclaration.setMemos(dzscCustomsDeclaration.getMemos());// 备注

		CompanyOther other = CommonUtils.getOther();
		if (other != null) {
			if (customsDeclaration.getCurrency() == null) {// 设置币别
				customsDeclaration.setCurrency(other.getCommonCurr());
			}
			customsDeclaration.setDeclarationCompany(other
					.getDeclarationCompany());// 申报单位

		}
		// 自动带出参数
		CustomsDeclarationSet other1 = dzscEncDao
				.findCustomsSet(customsDeclaration.getImpExpType());
		if (other1 != null) {
			customsDeclaration.setDeclarationCustoms(other1
					.getDeclarationCustoms());
			customsDeclaration.setLevyKind(other1.getCustomlevyKind());
			customsDeclaration.setBillOfLading(other1.getBillOfLading());
			customsDeclaration.setBalanceMode(other1.getBalanceMode());
			customsDeclaration.setTradeMode(other1.getTradeMode());
			customsDeclaration.setConveyance(other1.getConveyance());
			customsDeclaration.setTransac(other1.getTransac());
			customsDeclaration.setUses(other1.getUses());
			customsDeclaration.setCountryOfLoadingOrUnloading(other1.getCoun());
			customsDeclaration.setPredock(other1.getPredock());
			customsDeclaration.setPortOfLoadingOrUnloading(other1.getPort());
			customsDeclaration.setWrapType(other1.getWrapType());
			customsDeclaration.setCustoms(other1.getCustoms());
			customsDeclaration.setTransferMode(other1.getTransferMode());
			customsDeclaration.setDomesticDestinationOrSource(other1
					.getDistrict());
			customsDeclaration.setCurrency(other1.getCurr());
		}
		this.dzscEncDao.saveCustomsDeclaration(customsDeclaration);
		return customsDeclaration;
	}

	/**
	 * 获取key的方法 手册相同，归并序号相同
	 * 
	 * @param temp
	 * @param isProduct
	 * @return
	 */
	private String getKey(TempDzscImpExpCommodityInfo temp, boolean isProduct) {
		if (isProduct) {
			return temp.getDzscEmsExgBill().getDzscEmsPorHead().getEmsNo()
					+ temp.getDzscEmsExgBill().getTenSeqNum();
		}
		return temp.getDzscEmsImgBill().getDzscEmsPorHead().getEmsNo()
				+ temp.getDzscEmsImgBill().getTenSeqNum();
	}

	/**
	 * 
	 * @param obj1
	 *            change
	 * @param obj2
	 *            remain
	 * @return
	 */
	private boolean equalsDouble(Double[] obj1, Double[] obj2) {
		if (obj1[0] > obj2[0] || obj1[1] > obj2[1]) {
			return true;
		}
		return false;
	}

	/**
	 * 进出口申请单--->电子备案报关单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isProduct
	 * 是成品还是料件
	 * 
	 * @param dzscCustomsDeclaration
	 *            报关单表头
	 * @param parentList
	 *            临时申请单表头
	 * @param dataSource
	 *            临时的报关单商品信息
	 * @param isProduct
	 *            判断是否成品，true时为成品
	 * @param netWeightParameter
	 *            净重参数
	 * @return list.get(0)==DzscCustomsDeclaration
	 *         list.get(1)==进出口申请单生成报关单时,修改进出申请单的生成报关单字段为true的list
	 */
	public List makeDzscCustomsDeclaration(
			DzscCustomsDeclaration dzscCustomsDeclaration,
			List<TempImpExpRequestBill> parentList,
			List<TempDzscImpExpCommodityInfo> dataSource, boolean isProduct,
			Double netWeightParameter) {
		List<DzscCustomsDeclarationCommInfo> dzscCustomsDeclarationCommInfoList = new ArrayList<DzscCustomsDeclarationCommInfo>();
		List<ImpExpCommodityInfo> oldImpExpCommodityInfoList = new ArrayList<ImpExpCommodityInfo>();
		List<Object> returnList = new ArrayList<Object>();
		Map map = new HashMap();
		/**
		 * 把所有相同的备案商品编码 数量等其它项整合 成一条
		 */
		List<TempDzscImpExpCommodityInfo> impExpCommodityInfoList = this
				.mergerImpExpCommodityInfo(dataSource, isProduct);
		/**
		 * 保存报关单表头用来获得其ID
		 */
		if (dzscCustomsDeclaration.getId() == null) { // 代表新增
			dzscEncDao.saveCustomsDeclaration(dzscCustomsDeclaration);
		}
		if ((parentList != null && parentList.size() > 0)
				&& (isProduct == true)) {// 注：为成品才带出客户资料相关设置
			TempImpExpRequestBill impexp = (TempImpExpRequestBill) parentList
					.get(0);
			ImpExpRequestBill d = impexp.getImpExpRequestBill();
			if (d != null) {
				ScmCoc k = d.getScmCoc();
				dzscCustomsDeclaration.setCountryOfLoadingOrUnloading(k
						.getCountry());
				dzscCustomsDeclaration.setPortOfLoadingOrUnloading(k
						.getPortLin());
				dzscCustomsDeclaration.setCustoms(k.getCustoms());
				dzscCustomsDeclaration.setTransferMode(k.getTransf());
				dzscCustomsDeclaration.setCustomer(k);
				dzscEncDao.saveCustomsDeclaration(dzscCustomsDeclaration);
			}
		}
		returnList.add(dzscCustomsDeclaration);
		for (int i = 0; i < impExpCommodityInfoList.size(); i++) {
			TempDzscImpExpCommodityInfo t = impExpCommodityInfoList.get(i);
			DzscCustomsDeclarationCommInfo d = null;
			if (isProduct == true) {// 是成品(PK单)
				/**
				 * 生成报关商品
				 */
				d = makeDzscCustomsDeclarationCommInfoByProduct(
						dzscCustomsDeclaration, d, t, netWeightParameter);

			} else {// 是料件(集装箱)
				/**
				 * 生成报关商品
				 */
				d = makeDzscCustomsDeclarationCommInfoByMateriel(
						dzscCustomsDeclaration, d, t);
			}
			/**
			 * 加入列表中
			 */
			dzscCustomsDeclarationCommInfoList.add(d);
		}
		/**
		 * 最后保存报关单明细数据
		 */
		for (int i = 0; i < dzscCustomsDeclarationCommInfoList.size(); i++) {
			this.dzscEncDao
					.saveCustomsDeclarationCommInfo(dzscCustomsDeclarationCommInfoList
							.get(i));
		}
		/**
		 * 回写进出口申请单据商品信息(设置转报关清单为true)
		 */
		for (int i = 0; i < dataSource.size(); i++) {
			TempDzscImpExpCommodityInfo t = (TempDzscImpExpCommodityInfo) dataSource
					.get(i);
			ImpExpCommodityInfo imp = t.getImpExpCommodityInfo();
			imp.setIsTransferCustomsBill(new Boolean(true)); // 已转报关清单
			oldImpExpCommodityInfoList.add(imp);
		}
		this.dzscEncDao.saveImpExpCommodityInfo(oldImpExpCommodityInfoList);
		/**
		 * 生成中间表信息
		 */
		makeMakeDzscCustomsDeclaration(isProduct, dataSource,
				dzscCustomsDeclarationCommInfoList);
		/**
		 * 进出口申请单生成报关清单时,修改进出申请单的生成报关清单字段为true
		 */
		returnList.add(getImpExpRequestBillInfo(dataSource));

		/**
		 * 回写报关单集装箱号
		 */
		if (dzscCustomsDeclaration.getImpExpFlag() != null
				&& dzscCustomsDeclaration.getImpExpFlag() == ImpExpFlag.IMPORT) {// 如果是进口
			for (int i = 0; i < parentList.size(); i++) {
				TempImpExpRequestBill temp = parentList.get(i);
				ImpExpRequestBill bill = temp.getImpExpRequestBill();
				if (bill.getContainerCode() == null
						|| "".equals(bill.getContainerCode())) {
					continue;
				}
				DzscCustomsDeclarationContainer container = new DzscCustomsDeclarationContainer();
				container.setBaseCustomsDeclaration(dzscCustomsDeclaration);
				container.setContainerCode(bill.getContainerCode());
				this.dzscEncDao.saveCustomsDeclarationContainer(container);
			}
		}

		return returnList;
	}

	/**
	 * 生成报关申请单转报关单中间表信息
	 * 
	 * @param isProduct
	 *            判断是否成品，true时为成品
	 * @param dataSource
	 *            临时的报关单商品信息
	 * @param dzscCustomsDeclarationCommInfoList
	 *            报关单物料
	 */
	private void makeMakeDzscCustomsDeclaration(
			boolean isProduct,
			List<TempDzscImpExpCommodityInfo> dataSource,
			List<DzscCustomsDeclarationCommInfo> dzscCustomsDeclarationCommInfoList) {
		Map<String, DzscCustomsDeclarationCommInfo> map = new HashMap<String, DzscCustomsDeclarationCommInfo>();
		for (DzscCustomsDeclarationCommInfo d : dzscCustomsDeclarationCommInfoList) {
			map.put(d.getComplex().getCode(), d);
		}
		for (TempDzscImpExpCommodityInfo temp : dataSource) {
			MakeDzscCustomsDeclaration m = new MakeDzscCustomsDeclaration();
			m.setCompany(CommonUtils.getCompany());
			m.setImpExpCommodityInfo(temp.getImpExpCommodityInfo());
			if (isProduct == true) {
				m.setDzscCustomsDeclarationCommInfo(map.get(temp
						.getDzscEmsExgBill().getComplex()));
			} else {
				m.setDzscCustomsDeclarationCommInfo(map.get(temp
						.getDzscEmsImgBill().getComplex()));
			}
			this.dzscImpExpRequestDao.saveMakeDzscCustomsDeclaration(m);
		}
	}

	/**
	 * 进出口申请单据生成报关单时,修改进出口单据的生成报关单字段为true
	 * 
	 * @param dataSource
	 *            临时的报关单商品信息
	 * @return List 是型，申请单表头
	 */
	private List getImpExpRequestBillInfo(
			List<TempDzscImpExpCommodityInfo> dataSource) {
		List<ImpExpRequestBill> impExpRequestBillList = new ArrayList<ImpExpRequestBill>();
		Set<ImpExpRequestBill> set = new HashSet<ImpExpRequestBill>();
		for (int i = 0; i < dataSource.size(); i++) {
			TempDzscImpExpCommodityInfo t = dataSource.get(i);
			if (t.getImpExpCommodityInfo() != null
					&& t.getImpExpCommodityInfo().getImpExpRequestBill() != null)
				set.add(t.getImpExpCommodityInfo().getImpExpRequestBill());
		}
		/**
		 * 修改结转单据的已转关封申请单字段
		 */
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			ImpExpRequestBill t = (ImpExpRequestBill) iterator.next();
			Integer tocustomCount = this.dzscEncDao.getInfoForToCustom(t);
			t.setToCustomCount(tocustomCount);
			if (t.getItemCount() != null
					&& t.getItemCount().equals(tocustomCount)) { // 判断是否全部转关
				t.setIsCustomsBill(new Boolean(true));
			}
			this.dzscEncDao.saveImpExpRequestBill(t);
			impExpRequestBillList.add(t);
		}
		/**
		 * 返回已生成关封申请单据的结转单据列表
		 */
		return impExpRequestBillList;
	}

	/**
	 * 把所有相同的备案商品编码 数量等其它项整合 成一条
	 * 
	 * @param dataSource
	 *            临时的报关单商品信息
	 * @param isProduct
	 *            判断是否成品，true时为成品
	 * @return List 是TempDzscImpExpCommodityInfo型，临时的报关单商品信息
	 */
	private List<TempDzscImpExpCommodityInfo> mergerImpExpCommodityInfo(
			List<TempDzscImpExpCommodityInfo> dataSource, boolean isProduct) {
		Map<String, TempDzscImpExpCommodityInfo> map = new HashMap<String, TempDzscImpExpCommodityInfo>();
		for (int i = 0; i < dataSource.size(); i++) {
			TempDzscImpExpCommodityInfo t = dataSource.get(i);
			if (i == 0) {
				if (isProduct) { // 是成品
					map.put(t.getDzscEmsExgBill().getComplex().getCode(), t);
				} else { // 是料件
					map.put(t.getDzscEmsImgBill().getComplex().getCode(), t);
				}
				continue;
			} else {
				TempDzscImpExpCommodityInfo temp = null;
				if (isProduct) { // 是成品
					temp = map.get(t.getDzscEmsExgBill().getComplex());
				} else { // 是料件
					temp = map.get(t.getDzscEmsImgBill().getComplex());
				}
				if (temp != null) {
					ImpExpCommodityInfo data = temp.getImpExpCommodityInfo();
					Double quantity = Double
							.valueOf((data.getQuantity() == null ? 0 : data
									.getQuantity().doubleValue())
									+ (t.getImpExpCommodityInfo() == null ? 0
											: t.getImpExpCommodityInfo()
													.getQuantity()
													.doubleValue()));
					Double grossWeight = Double
							.valueOf((data.getGrossWeight() == null ? 0 : data
									.getGrossWeight().doubleValue())
									+ (t.getImpExpCommodityInfo() == null ? 0
											: t.getImpExpCommodityInfo()
													.getGrossWeight()
													.doubleValue()));
					Double netWeight = Double
							.valueOf((data.getNetWeight() == null ? 0 : data
									.getNetWeight().doubleValue())
									+ (t.getImpExpCommodityInfo() == null ? 0
											: t.getImpExpCommodityInfo()
													.getNetWeight()
													.doubleValue()));
					Double cubage = Double.valueOf((data.getBulks() == null ? 0
							: data.getBulks().doubleValue())
							+ (t.getImpExpCommodityInfo() == null ? 0 : t
									.getImpExpCommodityInfo().getBulks()
									.doubleValue()));
					// edit by xxm strix
					data.setQuantity(quantity);
					data.setGrossWeight(grossWeight);
					data.setNetWeight(netWeight);
					data.setBulks(cubage);
				} else {
					if (isProduct) { // 是成品
						map
								.put(t.getDzscEmsExgBill().getComplex()
										.getCode(), t);
					} else { // 是料件
						map
								.put(t.getDzscEmsImgBill().getComplex()
										.getCode(), t);
					}
				}
			}
		}
		List<TempDzscImpExpCommodityInfo> impExpCommodityList = new ArrayList<TempDzscImpExpCommodityInfo>();
		impExpCommodityList.addAll(map.values());
		return impExpCommodityList;
	}

	// 生成报关单商品－－成品
	private DzscCustomsDeclarationCommInfo makeDzscCustomsDeclarationCommInfoProduct(
			DzscCustomsDeclaration dzscCustomsDeclaration,
			TempDzscImpExpCommodityInfo t) {
		DzscCustomsDeclarationCommInfo commInfo = new DzscCustomsDeclarationCommInfo();
		commInfo.setBaseCustomsDeclaration(dzscCustomsDeclaration);// 关联表头
		// 手册料件或成品
		DzscEmsExgBill dzscEmsExgBill = t.getDzscEmsExgBill();
		// 商品序号
		commInfo.setCommSerialNo(dzscEmsExgBill.getSeqNum());
		// 商品流水号
		commInfo.setComplex(this.dzscImpExpRequestDao
				.findComplexByCode(dzscEmsExgBill.getComplex().getCode()));
		// 商品名称
		commInfo.setCommName(dzscEmsExgBill.getName());
		// 规格
		commInfo.setCommSpec(dzscEmsExgBill.getSpec());
		// 单位
		commInfo.setUnit(dzscEmsExgBill.getUnit());
		// 公司
		commInfo.setCompany(CommonUtils.getCompany());
		// 征免方式
		commInfo.setLevyMode(dzscEmsExgBill.getLevyMode());
		// 版本
		commInfo.setVersion(t.getImpExpCommodityInfo().getMateriel()
				.getPtVersion());
		// 原产国 or 最终目的产国
		commInfo.setCountry(dzscEmsExgBill.getCountry());
		// 单价=手册成品单价
		commInfo.setCommUnitPrice(dzscEmsExgBill.getPrice());
		// 数量
		double amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
				: t.getImpExpCommodityInfo().getQuantity();
		// 毛重
		double grossWeight = CommonUtils.getDoubleExceptNull(t
				.getImpExpCommodityInfo().getGrossWeight());
		// 净重
		double netWeight = CommonUtils.getDoubleExceptNull(t
				.getImpExpCommodityInfo().getNetWeight());
		// 数量取整
		commInfo.setCommAmount(amount);
		// 总金额 = 数量＊单价
		commInfo.setCommTotalPrice(amount
				* (dzscEmsExgBill.getPrice() == null ? 0.0 : dzscEmsExgBill
						.getPrice()));
		// 净重取整
		commInfo.setNetWeight(netWeight);
		// 毛重取整
		commInfo.setGrossWeight(grossWeight);
		// 件数
		commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? 0
				: t.getImpExpCommodityInfo().getPiece().intValue());
		/**
		 * 流水号在保存时新增
		 */
		dzscEncLogic.saveCustomsDeclarationCommInfo(commInfo);

		// //更新报关单表头件数，毛重，净重，金额
		// //件数
		// dzscCustomsDeclaration.setCommodityNum(CommonUtils.getIntegerExceptNull(dzscCustomsDeclaration.getCommodityNum())
		// +CommonUtils.getIntegerExceptNull(commInfo.getPieces()));
		// //毛重
		// dzscCustomsDeclaration.setGrossWeight(CommonUtils.getDoubleExceptNull(dzscCustomsDeclaration.getGrossWeight())
		// +CommonUtils.getDoubleExceptNull(commInfo.getGrossWeight()));
		// //净重
		// dzscCustomsDeclaration.setNetWeight(CommonUtils.getDoubleExceptNull(dzscCustomsDeclaration.getNetWeight())
		// +CommonUtils.getDoubleExceptNull(commInfo.getNetWeight()));
		// this.dzscEncDao.saveCustomsDeclaration(dzscCustomsDeclaration);
		// 金额
		return commInfo;
	}

	// 生成报关单商品－－料件
	private DzscCustomsDeclarationCommInfo makeDzscCustomsDeclarationCommInfoMe(
			DzscCustomsDeclaration dzscCustomsDeclaration,
			TempDzscImpExpCommodityInfo t) {
		DzscCustomsDeclarationCommInfo commInfo = new DzscCustomsDeclarationCommInfo();
		commInfo.setBaseCustomsDeclaration(dzscCustomsDeclaration);// 关联表头
		// 手册料件或成品
		DzscEmsImgBill dzscEmsExgBill = t.getDzscEmsImgBill();
		// 商品序号
		commInfo.setCommSerialNo(dzscEmsExgBill.getSeqNum());
		// 商品流水号
		commInfo.setComplex(this.dzscImpExpRequestDao
				.findComplexByCode(dzscEmsExgBill.getComplex().getCode()));
		// 商品名称
		commInfo.setCommName(dzscEmsExgBill.getName());
		// 规格
		commInfo.setCommSpec(dzscEmsExgBill.getSpec());
		// 单位
		commInfo.setUnit(dzscEmsExgBill.getUnit());
		// 公司
		commInfo.setCompany(CommonUtils.getCompany());
		// 征免方式
		commInfo.setLevyMode(dzscEmsExgBill.getLevyMode());
		// 版本
		commInfo.setVersion(t.getImpExpCommodityInfo().getMateriel()
				.getPtVersion());
		// 原产国 or 最终目的产国
		commInfo.setCountry(dzscEmsExgBill.getCountry());
		// 单价=手册成品单价
		commInfo.setCommUnitPrice(dzscEmsExgBill.getPrice());
		// 数量
		double amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
				: t.getImpExpCommodityInfo().getQuantity();
		// 毛重
		double grossWeight = CommonUtils.getDoubleExceptNull(t
				.getImpExpCommodityInfo().getGrossWeight());
		// 净重
		double netWeight = CommonUtils.getDoubleExceptNull(t
				.getImpExpCommodityInfo().getNetWeight());
		// 数量取整
		commInfo.setCommAmount(amount);
		// 总金额 = 数量＊单价
		commInfo.setCommTotalPrice(amount
				* (dzscEmsExgBill.getPrice() == null ? 0.0 : dzscEmsExgBill
						.getPrice()));
		// 净重取整
		commInfo.setNetWeight(netWeight);
		// 毛重取整
		commInfo.setGrossWeight(grossWeight);
		// 件数
		commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? 0
				: t.getImpExpCommodityInfo().getPiece().intValue());
		/**
		 * 流水号在保存时新增
		 */
		dzscEncLogic.saveCustomsDeclarationCommInfo(commInfo);
		// //更新报关单表头件数，毛重，净重，金额
		// //件数
		// dzscCustomsDeclaration.setCommodityNum(CommonUtils.getIntegerExceptNull(dzscCustomsDeclaration.getCommodityNum())
		// +CommonUtils.getIntegerExceptNull(commInfo.getPieces()));
		// //毛重
		// dzscCustomsDeclaration.setGrossWeight(CommonUtils.getDoubleExceptNull(dzscCustomsDeclaration.getGrossWeight())
		// +CommonUtils.getDoubleExceptNull(commInfo.getGrossWeight()));
		// //净重
		// dzscCustomsDeclaration.setNetWeight(CommonUtils.getDoubleExceptNull(dzscCustomsDeclaration.getNetWeight())
		// +CommonUtils.getDoubleExceptNull(commInfo.getNetWeight()));
		// this.dzscEncDao.saveCustomsDeclaration(dzscCustomsDeclaration);
		// 金额
		return commInfo;
	}

	/**
	 * 生成报关单明细来自合同成品清单
	 * 
	 * @param dzscCustomsDeclaration
	 *            报关单表头
	 * @param commInfo
	 *            报关单商品
	 * @param t
	 *            临时的报关单商品信息
	 * @param netWeightParameter
	 *            净重参数
	 * @return DzscCustomsDeclarationCommInfo 报关单商品
	 */
	private DzscCustomsDeclarationCommInfo makeDzscCustomsDeclarationCommInfoByProduct(
			DzscCustomsDeclaration dzscCustomsDeclaration,
			DzscCustomsDeclarationCommInfo commInfo,
			TempDzscImpExpCommodityInfo t, Double netWeightParameter) {
		commInfo = new DzscCustomsDeclarationCommInfo();
		commInfo.setBaseCustomsDeclaration(dzscCustomsDeclaration);
		DzscEmsExgBill dzscEmsExgBill = t.getDzscEmsExgBill();
		/**
		 * 这里是清单流水号
		 */
		commInfo.setCommSerialNo(dzscEmsExgBill.getSeqNum());
		commInfo.setComplex(this.dzscImpExpRequestDao
				.findComplexByCode(dzscEmsExgBill.getComplex().getCode()));
		commInfo.setCommName(dzscEmsExgBill.getName());
		commInfo.setCommSpec(dzscEmsExgBill.getSpec());
		commInfo.setUnit(dzscEmsExgBill.getUnit());
		commInfo.setCompany(CommonUtils.getCompany());
		commInfo.setLevyMode(dzscEmsExgBill.getLevyMode());
		commInfo.setVersion(t.getImpExpCommodityInfo().getMateriel()
				.getPtVersion());
		commInfo.setCountry(dzscEmsExgBill.getCountry());
		/**
		 * 单价
		 */
		commInfo.setCommUnitPrice(dzscEmsExgBill.getPrice());
		/**
		 * 报关数量这里来自报关申请单数量
		 */
		double amount = 0.0;
		double grossWeight = 0.0;
		double netWeight = 0.0;
		if (dzscEmsExgBill.getUnit() != null
				&& (dzscEmsExgBill.getUnit().getName().trim().equals("公斤") || dzscEmsExgBill
						.getUnit().getName().trim().equals("千克"))) {
			amount = (t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
					: t.getImpExpCommodityInfo().getQuantity())
					* (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0
							: t.getImpExpCommodityInfo().getNetWeight())
					* netWeightParameter;
			/**
			 * 合同比例(合同单位毛重/合同单位净重)
			 */
			double contractRate = 0.0;
			double _grossWeight = dzscEmsExgBill.getUnitGrossWeight() == null ? 0.0
					: dzscEmsExgBill.getUnitGrossWeight();
			double _netWeight = dzscEmsExgBill.getUnitNetWeight() == null ? 0.0
					: dzscEmsExgBill.getUnitNetWeight();
			if (_grossWeight != 0.0 || _netWeight != 0.0) {
				contractRate = _grossWeight / _netWeight;
			}

			netWeight = (t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
					: t.getImpExpCommodityInfo().getQuantity())
					* (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0
							: t.getImpExpCommodityInfo().getNetWeight())
					* netWeightParameter;
			/**
			 * 申报单位毛重 = 申报单位净重 * 合同比例(合同单位毛重/合同单位净重)
			 */
			grossWeight = netWeight * contractRate;

		} else {
			amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0 : t
					.getImpExpCommodityInfo().getQuantity();
			grossWeight = amount
					* (dzscEmsExgBill.getUnitGrossWeight() == null ? 0.0
							: dzscEmsExgBill.getUnitGrossWeight());
			netWeight = amount
					* (dzscEmsExgBill.getUnitNetWeight() == null ? 0.0
							: dzscEmsExgBill.getUnitNetWeight());

		}
		commInfo.setCommAmount(forInterNum(amount));

		/*
		 * commInfo .setCommTotalPrice((t.getImpExpCommodityInfo().getQuantity()
		 * == null ? 0.0 : t.getImpExpCommodityInfo().getQuantity())
		 * (dzscEmsExgBill.getPrice() == null ? 0.0 :
		 * dzscEmsExgBill.getPrice()));
		 */
		/**
		 * 总额 = 单价 * 数量
		 */
		commInfo.setCommTotalPrice(forInterNum(amount)
				* (dzscEmsExgBill.getPrice() == null ? 0.0 : dzscEmsExgBill
						.getPrice()));
		commInfo.setNetWeight(forInterNum(netWeight)); // 净毛重取整
		commInfo.setGrossWeight(forInterNum(grossWeight));
		//
		// prece
		//
		commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? 0
				: t.getImpExpCommodityInfo().getPiece().intValue());
		/**
		 * 流水号在保存时新增
		 */
		dzscEncLogic.saveCustomsDeclarationCommInfo(commInfo);
		return commInfo;
	}

	private Double forInterNum(double shuliang) {
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}

	/**
	 * 生成报关单明细来自合同料件清单 集装箱
	 * 
	 * @param dzscCustomsDeclaration
	 *            报关单表头
	 * @param commInfo
	 *            报关单商品
	 * @param t
	 *            临时的报关单商品信息
	 * @return DzscCustomsDeclarationCommInfo 报关单商品
	 */
	private DzscCustomsDeclarationCommInfo makeDzscCustomsDeclarationCommInfoByMateriel(
			DzscCustomsDeclaration dzscCustomsDeclaration,
			DzscCustomsDeclarationCommInfo commInfo,
			TempDzscImpExpCommodityInfo t) {
		CustomsDeclarationSet other = this.dzscEncLogic
				.findCustomsSet(dzscCustomsDeclaration.getImpExpType());
		commInfo = new DzscCustomsDeclarationCommInfo();
		commInfo.setBaseCustomsDeclaration(dzscCustomsDeclaration);
		DzscEmsImgBill dzscEmsImgBill = t.getDzscEmsImgBill();
		/**
		 * 这里是清单流水号
		 */
		commInfo.setCommSerialNo(dzscEmsImgBill.getSeqNum());
		commInfo.setComplex(this.dzscImpExpRequestDao
				.findComplexByCode(dzscEmsImgBill.getComplex().getCode()));
		commInfo.setCommName(dzscEmsImgBill.getName());
		commInfo.setCommSpec(dzscEmsImgBill.getSpec());
		commInfo.setUnit(dzscEmsImgBill.getUnit());
		commInfo.setCompany(CommonUtils.getCompany());
		commInfo.setVersion(t.getImpExpCommodityInfo().getMateriel()
				.getPtVersion());
		commInfo.setCountry(dzscEmsImgBill.getCountry());
		if (other != null) {
			commInfo.setUses(other.getUses());
			commInfo.setLevyMode(other.getLevyMode());
		}

		/**
		 * 单价
		 */
		commInfo.setCommUnitPrice(dzscEmsImgBill.getPrice());
		double amount = 0.0;

		//
		// 报关数量这里来自报关申请单数量
		//
		/*
		 * if (dzscEmsImgBill.getUnit() != null &&
		 * (dzscEmsImgBill.getUnit().getName().trim().equals("公斤") ||
		 * dzscEmsImgBill .getUnit().getName().trim().equals("千克"))) { amount =
		 * (t.getImpExpCommodityInfo().getQuantity() == null ? 0.0 :
		 * t.getImpExpCommodityInfo().getQuantity())
		 * (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0 :
		 * t.getImpExpCommodityInfo().getNetWeight()); } else {//单位：个，台 amount =
		 * t.getImpExpCommodityInfo().getQuantity(); }
		 */
		amount = (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0 : t
				.getImpExpCommodityInfo().getNetWeight());
		/**
		 * 数量
		 */
		commInfo.setCommAmount(forInterNum(amount));
		if (commInfo.getComplex().getFirstUnit() != null
				&& commInfo.getComplex().getFirstUnit().equals("米")) {
			commInfo.setFirstAmount(forInterNum(t.getImpExpCommodityInfo()
					.getQuantity() * 0.9144));
		} else if (commInfo.getComplex().getFirstUnit() != null
				&& (commInfo.getComplex().getFirstUnit().equals("千克") || commInfo
						.getComplex().getFirstUnit().equals("公斤"))) {
			commInfo.setFirstAmount(forInterNum(amount));
		}
		if (commInfo.getComplex().getSecondUnit() != null
				&& commInfo.getComplex().getSecondUnit().equals("米")) {
			commInfo.setSecondAmount(forInterNum(t.getImpExpCommodityInfo()
					.getQuantity() * 0.9144));
		} else if (commInfo.getComplex().getSecondUnit() != null
				&& (commInfo.getComplex().getSecondUnit().equals("千克") || commInfo
						.getComplex().getSecondUnit().equals("公斤"))) {
			commInfo.setSecondAmount(forInterNum(amount));
		}
		/**
		 * 件数
		 */
		commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? 0
				: t.getImpExpCommodityInfo().getPiece().intValue());
		/**
		 * 总额 = 单价 * 数量
		 */
		commInfo.setCommTotalPrice(amount
				* (dzscEmsImgBill.getPrice() == null ? 0.0 : dzscEmsImgBill
						.getPrice()));

		/**
		 * 申报单位净重 = pk净重
		 */
		commInfo.setNetWeight(forInterNum((t.getImpExpCommodityInfo()
				.getNetWeight() == null ? 0.0 : t.getImpExpCommodityInfo()
				.getNetWeight())));
		/**
		 * 申报单位毛重 = pk毛重
		 */
		commInfo.setGrossWeight(forInterNum((t.getImpExpCommodityInfo()
				.getGrossWeight() == null ? 0.0 : t.getImpExpCommodityInfo()
				.getGrossWeight())));
		/**
		 * 流水号在保存时新增
		 */
		dzscEncLogic.saveCustomsDeclarationCommInfo(commInfo);
		return commInfo;
	}

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param parentList
	 *            临时申请单表头
	 * @return List 是TempDzscImpExpCommodityInfo型，临时的报关单商品信息
	 */
	public List findTempDzscImpExpCommodityInfoByParent(List parentList) {
		List list = this.dzscEncDao.findImpExpCommodityInfoByParent(parentList);
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempDzscImpExpCommodityInfo temp = new TempDzscImpExpCommodityInfo();
			temp.setImpExpCommodityInfo((ImpExpCommodityInfo) list.get(i));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 统计手册合同料件数量，金额余额
	 * 
	 * @param imgBill
	 * @return
	 */
	public Double[] processImgRemainContractMountAndMoney(DzscEmsImgBill imgBill) {
		// TODO Auto-generated method stub
		Double[] imgEms = { imgBill.getAmount(), imgBill.getMoney() };

		/**
		 * 已生效料件进口
		 */
		double[] effectiveDirectImport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.DIRECT_IMPORT, null,
						CustomsDeclarationState.EFFECTIVED);
		/**
		 * 已生效转厂进口
		 */
		double[] effectiveTransferFactoryImport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.TRANSFER_FACTORY_IMPORT, null,
						CustomsDeclarationState.EFFECTIVED);

		/**
		 * 已生效余料转入
		 */
		double[] effectiveRemainForwardImport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.REMAIN_FORWARD_IMPORT, null,
						CustomsDeclarationState.EFFECTIVED);

		/**
		 * 已生效料件退换出
		 */
		double[] effectiveExchangeExport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.IMPORT, imgBill
						.getSeqNum(), imgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, CustomsDeclarationState.EFFECTIVED);

		// 合同余量＝合同定量-(直接进口+转厂进口数量+余料转入数量-料件退换)
		imgEms[0] = imgEms[0] - effectiveDirectImport[0]
				- effectiveTransferFactoryImport[0]
				- effectiveRemainForwardImport[0] + effectiveExchangeExport[0];
		imgEms[1] = imgEms[1] - effectiveDirectImport[1]
				- effectiveTransferFactoryImport[1]
				- effectiveRemainForwardImport[1] + effectiveExchangeExport[1];
		return imgEms;
	}

	/**
	 * 统计手册合同成品数量，金额余额
	 * 
	 * @param request
	 * @param imgBill
	 * @return
	 */
	public Double[] processExgRemainContractMountAndMoney(DzscEmsExgBill exgBill) {
		// TODO Auto-generated method stub
		Double[] exgEms = { exgBill.getAmount(), exgBill.getMoney() };
		/**
		 * 已生效料件进口
		 */
		double[] effectiveDirectExport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.EXPORT, exgBill
						.getSeqNum(), exgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.DIRECT_EXPORT, null,
						CustomsDeclarationState.EFFECTIVED);
		/**
		 * 已生效转厂进口
		 */
		double[] effectiveTransferFactoryExport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.EXPORT, exgBill
						.getSeqNum(), exgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.TRANSFER_FACTORY_EXPORT, null,
						CustomsDeclarationState.EFFECTIVED);

		/**
		 * 已生效余料转入
		 */
		double[] effectiveBackFactoryRework = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.EXPORT, exgBill
						.getSeqNum(), exgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.BACK_FACTORY_REWORK, null,
						CustomsDeclarationState.EFFECTIVED);

		/**
		 * 已生效料件退换出
		 */
		double[] effectiveReworkExport = dzscEncDao
				.findCommInfoTotalAmountAndMoney(ImpExpFlag.EXPORT, exgBill
						.getSeqNum(), exgBill.getDzscEmsPorHead().getEmsNo(),
						ImpExpType.REWORK_EXPORT, null,
						CustomsDeclarationState.EFFECTIVED);
		// 合同余量＝合同定量-(直接进口+转厂进口数量+已生效成品退厂返工-已生效成品返工复出)
		exgEms[0] = exgEms[0] - effectiveDirectExport[0]
				- effectiveTransferFactoryExport[0]
				- effectiveBackFactoryRework[0] + effectiveReworkExport[0];
		exgEms[1] = exgEms[1] - effectiveDirectExport[1]
				- effectiveTransferFactoryExport[1]
				- effectiveBackFactoryRework[1] + effectiveReworkExport[1];
		return exgEms;
	}

	/**
	 * 把相应的数据变为BigDecimal，并返回指定的位数
	 * 
	 * @param amount
	 *            要改变的数据
	 * @param bigD
	 *            要返回的位数
	 * @return String 变更后的数据
	 */
	public static String formatBig(Object amount, int bigD) {
		if (amount == null || "".equals(amount)) {
			amount = "0";
		}
		if (bigD==0 && Double.valueOf(amount.toString()).doubleValue() < 0.5) {
			return "1";
		}
		BigDecimal bd = new BigDecimal(amount.toString());
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		return amountStr;
	}
}
