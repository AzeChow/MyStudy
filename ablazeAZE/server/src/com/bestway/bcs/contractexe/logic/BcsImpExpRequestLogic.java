package com.bestway.bcs.contractexe.logic;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcs.contractexe.dao.BcsImpExpRequestDao;
import com.bestway.bcs.contractexe.dao.ContractExeDao;
import com.bestway.bcs.contractexe.entity.BcsContractExeInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationContainer;
import com.bestway.bcs.contractexe.entity.ImpExpGoodsBill;
import com.bestway.bcs.contractexe.entity.MakeBcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcs.contractexe.entity.TempImpExpGoodsBill;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.logic.ContractStatLogic;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.bcus.innermerge.entity.TempMateriel;
import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * 进出口申请单转报关单(逻辑) checked by kcb 2008/10/25
 * 
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class BcsImpExpRequestLogic {
	/**
	 * 合同DAO
	 */
	private ContractDao bcsContractDao = null;
	/**
	 * 合同核销DAO
	 */
	private ContractCavDao contractCavDao;
	/**
	 * 进出口申请单DAO
	 */
	private BcsImpExpRequestDao bcsImpExpRequestDao = null;
	/**
	 * 合同执行DAO
	 */
	private ContractExeDao bcsEncDao = null;
	/**
	 * 合同执行逻辑层
	 */
	private ContractExeLogic bcsEncLogic = null;
	/**
	 * 内部归并DAO
	 */
	private BcsInnerMergeDao bcsInnerMergeDao = null;
	/**
	 * 统计报表DAO
	 */
	private ContractStatLogic contractStatLogic = null;
	/**
	 * 公司设置DAO
	 */
	private CompanyDao companyDao;

	/**
	 * 获取合同执行逻辑层 bcsEncLogic
	 * 
	 * @return Returns the bcsEncLogic
	 */
	public ContractExeLogic getDzbaEncLogic() {
		return bcsEncLogic;
	}

	/**
	 * 获取bcsContractDao
	 * 
	 * @return Returns the bcsContractDao.
	 */
	public ContractDao getBcsContractDao() {
		return bcsContractDao;
	}

	/**
	 * 设置bcsContractDao
	 * 
	 * @param bcsContractDao
	 *            The bcsContractDao to set.
	 */
	public void setBcsContractDao(ContractDao bcsContractDao) {
		this.bcsContractDao = bcsContractDao;
	}

	/**
	 * 设置bcsEncLogic
	 * 
	 * @param dzbaEncLogic
	 */
	public void setDzbaEncLogic(ContractExeLogic dzbaEncLogic) {
		this.bcsEncLogic = dzbaEncLogic;
	}

	/**
	 * 获取bcsEncDao
	 * 
	 * @return bcsEncDao
	 */
	public ContractExeDao getDzbaEncDao() {
		return bcsEncDao;
	}

	/**
	 * 设置bcsEncDao
	 * 
	 * @param dzbaEncDao
	 */
	public void setDzbaEncDao(ContractExeDao dzbaEncDao) {
		this.bcsEncDao = dzbaEncDao;
	}

	/**
	 * 获取bcsInnerMergeDao
	 * 
	 * @return bcsInnerMergeDao
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 设置bcsInnerMergeDao
	 * 
	 * @param bcsInnerMergeDao
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	/**
	 * 分页查找物料来自过滤纸质手册申请单明细
	 * 
	 * @param materielType
	 *            物料类别
	 * @param impExpRequestBill
	 *            申请单物料
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是型，
	 */
	public List findMaterielByBcsRequestBill(String materielType,
			ImpExpRequestBill impExpRequestBill, int index, int length,
			String property, Object value, Boolean isLike, boolean isFilter) {
		List newList = new ArrayList();
		// List emsList = bcsContractDao.findContractByProcessExe();
		/**
		 * 过滤的 hSql
		 */
		String conditionSql = null;
		List<Object> paramters = new ArrayList<Object>();
		if (isFilter) {
			conditionSql = " and b.id not in (select  c.materiel.id "
					+ "from ImpExpCommodityInfo c "
					+ "where c.impExpRequestBill.id = ? ) ";
			paramters.add(impExpRequestBill.getId());
		}
		// if (emsList == null || emsList.size() <= 0) { // 没有正在执行的海关合同
		/**
		 * 将返回所有的末备案的物料主档商品信息
		 */
		List list = this.bcsImpExpRequestDao.findMaterielByType(materielType,
				conditionSql, paramters.toArray(), index, length, property,
				value, isLike);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			// tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;
		// }
		// //
		// // 备案的记录
		// //
		// List list = this.bcsImpExpRequestDao
		// .findMaterielPutOnRecordsByBcsRequestBill(materielType,
		// conditionSql, paramters.toArray(), index, length,
		// property, value, isLike);
		// for (int i = 0; i < list.size(); i++) {
		// TempMateriel tempMateriel = new TempMateriel();
		// tempMateriel.setIsMemo(new Boolean(true));
		// tempMateriel.setMateriel((Materiel) list.get(i));
		// newList.add(tempMateriel);
		// }
		// //
		// // 未备案的数据
		// //
		// if (list.size() == 0) {
		// list = this.bcsImpExpRequestDao
		// .findMaterielNotPutOnRecordsByBcsRequestBill(materielType,
		// conditionSql, paramters.toArray(), index, length,
		// property, value, isLike);
		// } else if (list.size() < length) {
		// list = this.bcsImpExpRequestDao
		// .findMaterielNotPutOnRecordsByBcsRequestBill(materielType,
		// conditionSql, paramters.toArray(), index
		// + (list.size()), length - (list.size()),
		// property, value, isLike);
		// }
		// for (int i = 0; i < list.size(); i++) {
		// TempMateriel tempMateriel = new TempMateriel();
		// tempMateriel.setIsMemo(new Boolean(false));
		// tempMateriel.setMateriel((Materiel) list.get(i));
		// newList.add(tempMateriel);
		// }
		// return newList;
	}

	/**
	 * 分页查找物料来自过滤纸质手册申请单明细(联胜)
	 * 
	 * @param materielType
	 *            物料类别
	 * @param impExpRequestBill
	 *            申请单物料
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是型，
	 */
	public List findMaterielByBcsRequestBillLS(String materielType,
			ImpExpRequestBill impExpRequestBill, int index, int length,
			String property, Object value, Boolean isLike, boolean isFilter) {
		List newList = new ArrayList();
		// List emsList = bcsContractDao.findContractByProcessExe();
		/**
		 * 过滤的 hSql
		 */
		String conditionSql = null;
		List<Object> paramters = new ArrayList<Object>();
		if (isFilter) {
			if (impExpRequestBill != null) {
				conditionSql = " and b.materiel.id not in (select  c.materiel.id "
						+ "from ImpExpCommodityInfo c "
						+ "where c.impExpRequestBill.id = ? ) ";
				paramters.add(impExpRequestBill.getId());
			}
		}
		// if (emsList == null || emsList.size() <= 0) { // 没有正在执行的海关合同
		/**
		 * 将返回所有的物料与报关对应表中的商品信息
		 */
		List list = this.bcsImpExpRequestDao.findMaterielByTypeLS(materielType,
				conditionSql, paramters.toArray(), index, length, property,
				value, isLike);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			// tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;

	}

	/**
	 * 进出口申请单--->纸质手册报关单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isProduct
	 * 是成品还是料件
	 * 
	 * @param bcsCustomsDeclaration
	 *            报关单表头
	 * @param parentList
	 *            临时的进出口申请单表头
	 * @param dataSource
	 *            临时的进出口申请单表体资料
	 * @param isProduct
	 *            成品判断，true位成品
	 * @param netWeightParameter
	 *            净重参数
	 * @return List list.get(0)==BcsCustomsDeclaration
	 *         list.get(1)==进出口申请单生成报关单时,修改进出申请单的生成报关单字段为true的list
	 */
	public List makeBcsCustomsDeclaration(
			BcsCustomsDeclaration bcsCustomsDeclaration,
			List<TempImpExpRequestBill> parentList,
			List<TempBcsImpExpCommodityInfo> impExpCommodityInfoList,
			boolean isProduct, Double netWeightParameter,
			boolean isPriceFromContract) {

		List<BcsCustomsDeclarationCommInfo> bcsCustomsDeclarationCommInfoList = new ArrayList<BcsCustomsDeclarationCommInfo>();

		List<ImpExpCommodityInfo> oldImpExpCommodityInfoList = new ArrayList<ImpExpCommodityInfo>();

		List returnBilllist = new ArrayList();// 存放生成的报关单
		/**
		 * 把所有相同的备案商品编码 数量等其它项整合 成一条
		 */
		// List<TempBcsImpExpCommodityInfo> impExpCommodityInfoList = this
		// .mergerImpExpCommodityInfo(dataSource, isProduct);
		// /**
		// * 保存报关单表头用来获得其ID
		// */
		// if (bcsCustomsDeclaration.getId() == null) { // 代表新增
		// bcsEncDao.saveCustomsDeclaration(bcsCustomsDeclaration);
		// }

		if (parentList != null && parentList.size() > 0) {

			TempImpExpRequestBill impexp = (TempImpExpRequestBill) parentList
					.get(0);

			ImpExpRequestBill d = impexp.getImpExpRequestBill();

			if (d != null) {

				ScmCoc k = d.getScmCoc();

				if (bcsCustomsDeclaration.getSerialNumber() == null) {
					//
					// 找出最大的序号
					//
					bcsCustomsDeclaration.setSerialNumber(this.bcsEncDao
							.getCustomsDeclarationSerialNumber(
									bcsCustomsDeclaration.getImpExpFlag(),
									bcsCustomsDeclaration.getEmsHeadH2k()));

				}

				if (k != null) {

					if (k.getCountry() != null && !"".equals(k.getCountry())) {

						bcsCustomsDeclaration.setCountryOfLoadingOrUnloading(k
								.getCountry());

					}

					if (k.getPortLin() != null && !"".equals(k.getPortLin())) {

						bcsCustomsDeclaration.setPortOfLoadingOrUnloading(k
								.getPortLin());
					}

					bcsCustomsDeclaration.setCustomer(k);// 客户
				}

				bcsCustomsDeclaration.setCompany(CommonUtils.getCompany());

				bcsCustomsDeclaration
						.setManufacturer(getBrief(((Company) CommonUtils
								.getCompany()).getCode()));

				bcsCustomsDeclaration.setCreater(CommonUtils.getRequest()
						.getUser());

				bcsEncDao.saveCustomsDeclaration(bcsCustomsDeclaration);
			}

			returnBilllist.add(bcsCustomsDeclaration);

		}
		// returnList.add(bcsCustomsDeclaration);
		//
		// 取得报关单明细
		//
		List list = bcsEncDao
				.getBcsCustomsDeclarationCommInfo(bcsCustomsDeclaration);

		for (int i = 0; i < impExpCommodityInfoList.size(); i++) {

			TempBcsImpExpCommodityInfo t = impExpCommodityInfoList.get(i);

			BcsCustomsDeclarationCommInfo d = null;

			if (isProduct == true) {// 是成品(PK)
				/**
				 * 生成报关商品
				 */
				d = makeBcsCustomsDeclarationCommInfoByProduct(
						bcsCustomsDeclaration, d, t, netWeightParameter, list,
						isPriceFromContract);

			} else {// 是料件(集装箱单)
				/**
				 * 生成报关商品料件
				 */
				d = makeBcsCustomsDeclarationCommInfoByMateriel(
						bcsCustomsDeclaration, d, t, list, isPriceFromContract);
			}

			/**
			 * 加入列表中
			 */
			bcsCustomsDeclarationCommInfoList.add(d);

		}

		Integer falg = bcsCustomsDeclaration.getImpExpFlag();
		//
		// 商检排序
		//
		bcsCustomsDeclarationCommInfoList = sortSangJianImpExpCommodityInfo(
				bcsCustomsDeclarationCommInfoList, falg);
		/**
		 * 最后保存报关单明细数据
		 */
		int serNo = 1;
		if (bcsCustomsDeclaration.getId() != null) {
			serNo = this.bcsEncDao
					.getCustomsDeclarationCommInfoSerialNumber(bcsCustomsDeclaration
							.getId());
		}
		for (int i = 0; i < bcsCustomsDeclarationCommInfoList.size(); i++) {

			// 如果超过20项，拆分
			BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) bcsCustomsDeclarationCommInfoList
					.get(i);

			if (i != 0 && i % 20 == 0) {

				try {

					bcsCustomsDeclaration = (BcsCustomsDeclaration) BeanUtils
							.cloneBean(bcsCustomsDeclaration);

					bcsCustomsDeclaration.setId(null);

					//
					// 找出最大的序号
					//
					bcsCustomsDeclaration.setSerialNumber(this.bcsEncDao
							.getCustomsDeclarationSerialNumber(
									bcsCustomsDeclaration.getImpExpFlag(),
									bcsCustomsDeclaration.getEmsHeadH2k()));

					bcsEncDao.saveCustomsDeclaration(bcsCustomsDeclaration);

					serNo = 1;

					returnBilllist.add(bcsCustomsDeclaration);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			commInfo.setBaseCustomsDeclaration(bcsCustomsDeclaration);

			if (commInfo.getSerialNumber() == null) {
				commInfo.setSerialNumber(serNo);
			}
			serNo++;

			this.bcsEncDao.saveCustomsDeclarationCommInfo(commInfo);

		}

		List<TempBcsImpExpCommodityInfo> mergeBeforeInfos = getMergeBeforeInfo(impExpCommodityInfoList);

		/**
		 * 回写进出口申请单据商品信息(设置转报关清单为true)
		 */
		for (int i = 0; i < mergeBeforeInfos.size(); i++) {

			TempBcsImpExpCommodityInfo t = (TempBcsImpExpCommodityInfo) mergeBeforeInfos
					.get(i);

			ImpExpCommodityInfo imp = t.getImpExpCommodityInfo();

			imp.setIsTransferCustomsBill(new Boolean(true)); // 已转报关清单

			if (isProduct == true) {// 是成品(P

				ContractExg exg = t.getContractExg();

				imp.setAfterName(exg.getName());

				imp.setAfterSpec(exg.getSpec());

				imp.setAfterUnit(exg.getUnit() == null ? "" : exg.getUnit()
						.getName());

				imp.setSeqNum(exg.getSeqNum());

			} else {

				ContractImg img = t.getContractImg();

				imp.setAfterName(img.getName());

				imp.setAfterSpec(img.getSpec());

				imp.setAfterUnit(img.getUnit() == null ? "" : img.getUnit()
						.getName());

				imp.setSeqNum(img.getSeqNum());

			}

			oldImpExpCommodityInfoList.add(imp);

			System.out.println("回写进出口申请单据商品信息(设置转报关清单为true)==="
					+ imp.getQuantity());

		}

		this.bcsEncDao.saveImpExpCommodityInfo(oldImpExpCommodityInfoList);

		//
		// 回写报关单表头
		//
		List returnList = new ArrayList();

		List returnList1 = new ArrayList();// 存放报关单资料

		List<ImpExpRequestBill> returnList2 = new ArrayList<ImpExpRequestBill>();// 存放申请单资料

		Set<ImpExpRequestBill> setImpExpRequestBill = new HashSet<ImpExpRequestBill>();

		for (int i = 0; i < mergeBeforeInfos.size(); i++) {

			TempBcsImpExpCommodityInfo t = mergeBeforeInfos.get(i);

			setImpExpRequestBill.add(t.getImpExpCommodityInfo()
					.getImpExpRequestBill());

		}

		for (int j = 0; j < returnBilllist.size(); j++) {

			BcsCustomsDeclaration customsDeclaration = (BcsCustomsDeclaration) returnBilllist
					.get(j);

			CompanyOther other = bcsEncDao.findCompanyOther();

			if (other != null) {
				/**
				 * 是否自动计算报关单件数，毛重及净重
				 * 
				 */
				if (other.getIsAutoWeight() != null && other.getIsAutoWeight()) {

					bcsEncDao.getPiceAndWeight(customsDeclaration);

				}
				// 保存报关单头的隋附单据
				if (other.getIsCustomAutoAttachedBill() != null
						&& other.getIsCustomAutoAttachedBill()) {
					getAttachedBill(customsDeclaration);
				}
			}
			/**
			 * 回写报关单(集装箱)
			 */
			if (customsDeclaration.getImpExpFlag() != null
					&& customsDeclaration.getImpExpFlag() == ImpExpFlag.IMPORT) {// 如果是进口
				for (int i = 0; i < parentList.size(); i++) {
					TempImpExpRequestBill temp = parentList.get(i);
					ImpExpRequestBill bill = temp.getImpExpRequestBill();
					if (bill.getContainerCode() == null
							|| "".equals(bill.getContainerCode())) {
						continue;
					}
					BcsCustomsDeclarationContainer container = new BcsCustomsDeclarationContainer();
					container.setBaseCustomsDeclaration(bcsCustomsDeclaration);
					container.setContainerCode(bill.getContainerCode());
					this.bcsEncDao.saveCustomsDeclarationContainer(container);
				}
			}

			returnList1.add(customsDeclaration);
			/**
			 * 进出口申请单生成报关清单时,修改进出申请单的生成报关清单字段为true 同时回写报关单流水号与回写合同号
			 */
			getImpExpRequestBillInfo(setImpExpRequestBill, customsDeclaration,
					returnList2);
		}
		returnList.add(returnList1);
		returnList.add(returnList2);
		/**
		 * 生成中间表信息
		 */
		makeMakeBcsCustomsDeclaration(isProduct, mergeBeforeInfos,
				bcsCustomsDeclarationCommInfoList);
		return returnList;
	}

	// /**
	// * 获取归并前料件
	// * @param list 归并后料件
	// * @return
	// */
	// private List<TempBcsImpExpCommodityInfo>
	// getMergeBeforeInfo(List<TempBcsImpExpCommodityInfo> list){
	// List<TempBcsImpExpCommodityInfo> mergeBeforeInfo = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	//
	// List<TempBcsImpExpCommodityInfo> ls = list.get(i).getCommodityInfos();
	// //前面的数据是合并前的申请单信息，没有成品或料件，最后一个是合并后的对象，有成品或料件
	// TempBcsImpExpCommodityInfo oldTemp = ls.get(ls.size()-1);
	// mergeBeforeInfo.add(oldTemp);
	// for (int j = 0; j < ls.size()-1; j++) {
	// TempBcsImpExpCommodityInfo newTemp = ls.get(j);
	// newTemp.setContractExg(oldTemp.getContractExg());
	// newTemp.setContractImg(oldTemp.getContractImg());
	// ImpExpRequestBill bill =
	// oldTemp.getImpExpCommodityInfo().getImpExpRequestBill();
	// newTemp.getImpExpCommodityInfo().setImpExpRequestBill(bill);
	// // if(newTemp.getCommodityInfos()!=null){
	// //
	// mergeBeforeInfo.addAll(getMergeBeforeInfos(newTemp.getCommodityInfos(),oldTemp));
	// // }
	// mergeBeforeInfo.add(newTemp);
	// }
	//
	// }
	// return mergeBeforeInfo;
	// }
	/**
	 * 获取归并前料件
	 * 
	 * @param list
	 *            归并后料件
	 * @return
	 */
	private List<TempBcsImpExpCommodityInfo> getMergeBeforeInfo(
			List<TempBcsImpExpCommodityInfo> list) {

		List<TempBcsImpExpCommodityInfo> mergeBeforeInfo = new ArrayList();

		for (int i = 0; i < list.size(); i++) {

			TempBcsImpExpCommodityInfo oldTemp = list.get(i);

			/**
			 * 这里重复放进去合并前信息 --注释掉
			 */
			// ImpExpCommodityInfo imp = (ImpExpCommodityInfo) bcsEncDao.get(
			// ImpExpCommodityInfo.class, oldTemp.getImpExpCommodityInfo()
			// .getId());
			//
			// /**
			// * 这里出现 bug 的地方
			// */
			// oldTemp.setImpExpCommodityInfo(imp);
			//
			// mergeBeforeInfo.add(oldTemp);

			List<TempBcsImpExpCommodityInfo> ls = oldTemp.getCommodityInfos();

			for (int j = 0; j < ls.size(); j++) {

				TempBcsImpExpCommodityInfo newTemp = ls.get(j);

				ImpExpCommodityInfo impExpInfo = (ImpExpCommodityInfo) bcsEncDao
						.get(ImpExpCommodityInfo.class, newTemp
								.getImpExpCommodityInfo().getId());

				newTemp.setImpExpCommodityInfo(impExpInfo);

				newTemp.setContractExg(oldTemp.getContractExg());

				newTemp.setContractImg(oldTemp.getContractImg());

				mergeBeforeInfo.add(newTemp);

			}

		}

		return mergeBeforeInfo;
	}

	/**
	 * 保存报关单头的随附单据
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 */
	public void getAttachedBill(BaseCustomsDeclaration baseCustomsDeclaration) {
		System.out.println("进出口类型:" + baseCustomsDeclaration.getImpExpFlag());
		List list = bcsEncDao.getAttachedBill(baseCustomsDeclaration);
		String AttachedBill = "";
		String temp = "";
		// SXK修改..判断是否进出..某商品许可证代码为AB，
		// 在进口报关单中，带出随附单据为A，出口带出随附单据为B。
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null && !list.get(i).equals("")) {
				System.out.println("list.get(i)=" + list.get(i));
				if (baseCustomsDeclaration.getImpExpFlag() == 0
						&& list.get(i).toString().contains("B")) {
					temp = list.get(i).toString().replaceAll("B", " ");
					AttachedBill += removeSpaces(temp);
				} else if (baseCustomsDeclaration.getImpExpFlag() == 1
						&& list.get(i).toString().contains("A")) {
					temp = list.get(i).toString().replaceAll("A", " ");
					AttachedBill += removeSpaces(temp);
				} else {
					AttachedBill += list.get(i);
				}
			}
		}
		baseCustomsDeclaration.setAttachedBill(toDeleteRepeat(AttachedBill));
		bcsEncDao.saveCustomsDeclaration(baseCustomsDeclaration);
	}

	/**
	 * 去除字符串中的空格
	 * 
	 * @param s
	 * @return
	 * @author sxk
	 */
	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	/**
	 * 去除字符串中重复
	 * 
	 * @param str
	 * @return
	 * @author sxk
	 */
	public static String toDeleteRepeat(String str) {
		String[] tokens = str.split("");
		int k = tokens.length;
		for (int i = 0; i < tokens.length; i++) {
			for (int j = 0; j < tokens.length; j++) {
				if (tokens[i] != null && tokens[i].equals(tokens[j]) && i != j) {
					tokens[j] = null;
					k--;
				}
			}
		}
		StringBuffer resstr = new StringBuffer(50);
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] != null) {
				resstr.append(tokens[i]).append("");
			}
		}
		return resstr.toString();
	}

	/**
	 * 判断申清单中的已转报关单与回合同号
	 * 
	 * @param allbillNo
	 * @param contractNo
	 * @param bcsSerailNum
	 * @param bcsContractNo
	 * @return
	 */
	public String[] getExistBillAndContractNo(String allbillNo,
			String contractNo, String bcsSerailNum, String bcsContractNo) {
		Map mapTegther = new HashMap();
		String[] returnString = new String[2];

		String newStr = bcsSerailNum + "@" + bcsContractNo;

		if (allbillNo == null || "".equals(allbillNo) && contractNo == null
				|| "".equals(contractNo)) {
			returnString[0] = bcsSerailNum;
			returnString[1] = bcsContractNo;
			return returnString;
		}

		String[] stringBillNo = allbillNo.split(",");
		String[] stringContractNo = contractNo.split(",");

		if (stringBillNo.length == 0 && stringContractNo.length == 0) {
			mapTegther.put(stringBillNo[0] + "@" + stringContractNo[0],
					stringBillNo[0] + "@" + stringContractNo[0]);
		}
		if (stringBillNo.length == stringContractNo.length) {
			for (int i = 0; i < stringBillNo.length; i++) {
				mapTegther.put(stringBillNo[i] + "@" + stringContractNo[i],
						stringBillNo[i] + "@" + stringContractNo[i]);
			}
		}

		String str1 = allbillNo;
		String str2 = contractNo;
		int count = 0;
		Iterator it = mapTegther.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (!key.equals(newStr)) {
				count++;
			}
		}
		// System.out.print("==========count==" + count + "===mapTegther==" +
		// mapTegther.size()
		// + "\n");
		if (count == mapTegther.size()) {
			str1 = allbillNo + "," + bcsSerailNum;
			str2 = contractNo + "," + bcsContractNo;
			returnString[0] = str1;
			returnString[1] = str2;
			return returnString;
		}

		returnString[0] = str1;
		returnString[1] = str2;

		return returnString;
	}

	/**
	 * 判断箱号是否存在
	 * 
	 * @param allbillNo
	 *            所有的箱号
	 * @param newbillNo
	 *            新箱号
	 * @return 若存在为true 否则为false
	 */
	public String getNotExistBoxNo(String allbillNo, String newbillNo) {
		String newBoxNo = allbillNo;
		if (newbillNo == null || "".equals(newbillNo)) {
			return newBoxNo;
		}
		String[] yy = newbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (allbillNo.contains(yy[i])) {
				continue;
			}
			newBoxNo += "," + yy[i];
		}
		return newBoxNo;
	}

	/**
	 * 根据报关单商品的序号排序
	 * 
	 * @param datalist
	 *            报关单商品LIST
	 * @param flag
	 *            进出口标志
	 * @return 返回结果集
	 */
	public List sortSangJianImpExpCommodityInfo(List datalist, Integer flag) {
		if (datalist == null) {
			return new ArrayList();
		}
		List<String> codelist = this.bcsEncDao.findComplexByFlag(flag);
		List yesList = new ArrayList();
		List noList = new ArrayList();
		List relist = new ArrayList();
		for (int i = 0; i < datalist.size(); i++) {
			BcsCustomsDeclarationCommInfo tempCustomsList = (BcsCustomsDeclarationCommInfo) datalist
					.get(i);
			if (tempCustomsList.getComplex() == null
					|| tempCustomsList.getComplex().getCode() == null) {
				noList.add(tempCustomsList);
				continue;
			}
			if (codelist.contains(tempCustomsList.getComplex().getCode())) {
				yesList.add(tempCustomsList);
			} else {
				noList.add(tempCustomsList);
			}
		}
		Collections.sort(yesList,
				new Comparator<BcsCustomsDeclarationCommInfo>() {
					public int compare(BcsCustomsDeclarationCommInfo o1,
							BcsCustomsDeclarationCommInfo o2) {
						if (o1 == null || o1.getCommSerialNo() == null) {
							return -1;
						}
						if (o2 == null || o2.getCommSerialNo() == null) {
							return 1;
						}
						if (o1.getCommSerialNo() > o2.getCommSerialNo()) {
							return 1;
						} else if (o1.getCommSerialNo() == o2.getCommSerialNo()) {
							return 0;
						} else {
							return -1;
						}
					}
				});
		Collections.sort(noList,
				new Comparator<BcsCustomsDeclarationCommInfo>() {
					public int compare(BcsCustomsDeclarationCommInfo o1,
							BcsCustomsDeclarationCommInfo o2) {
						if (o1 == null || o1.getCommSerialNo() == null) {
							return -1;
						}
						if (o2 == null || o2.getCommSerialNo() == null) {
							return 1;
						}
						if (o1.getCommSerialNo() > o2.getCommSerialNo()) {
							return 1;
						} else if (o1.getCommSerialNo() == o2.getCommSerialNo()) {
							return 0;
						} else {
							return -1;
						}
					}
				});
		for (int i = 0; i < yesList.size(); i++) {
			relist.add(yesList.get(i));
		}
		for (int i = 0; i < noList.size(); i++) {
			relist.add(noList.get(i));
		}
		return relist;
	}

	/**
	 * 取得所有海关注册公司信息来自海关注册公司代码
	 * 
	 * @param breifCode
	 *            海关注册公司代码
	 * @return 海关注册公司信息
	 */
	public Brief getBrief(String breifCode) {
		List list = bcsEncDao.getBrief("code", breifCode);
		if (list.size() < 1) {
			return null;
		} else {
			return (Brief) list.get(0);
		}
	}

	/**
	 * 生成报关申请单转报关单中间表信息
	 * 
	 * @param isProduct
	 *            成品判断，true位成品
	 * @param dataSource
	 *            临时的进出口申请单表体资料
	 * @param bcsCustomsDeclarationCommInfo
	 *            报关单物料
	 */
	public void makeMakeBcsCustomsDeclaration(boolean isProduct,
			List<TempBcsImpExpCommodityInfo> dataSource,
			List<BcsCustomsDeclarationCommInfo> bcsCustomsDeclarationCommInfo) {

		System.out.println("bcsCustomsDeclarationCommInfo"
				+ bcsCustomsDeclarationCommInfo.size());

		System.out.println("dataSource" + dataSource.size());

		Map<String, BcsCustomsDeclarationCommInfo> map = new HashMap<String, BcsCustomsDeclarationCommInfo>();

		for (BcsCustomsDeclarationCommInfo d : bcsCustomsDeclarationCommInfo) {

			map.put(d.getCommSerialNo() + "/@/" + d.getComplex().getCode()
					+ "/@/"
					+ (d.getCountry() == null ? "" : d.getCountry().getCode()),
					d);
		}

		for (TempBcsImpExpCommodityInfo temp : dataSource) {

			MakeBcsCustomsDeclaration m = new MakeBcsCustomsDeclaration();

			m.setCompany(CommonUtils.getCompany());

			m.setImpExpCommodityInfo(temp.getImpExpCommodityInfo());

			System.out.println("makeMakeBcsCustomsDeclaration"
					+ temp.getImpExpCommodityInfo().getQuantity());

			String countryCode = temp.getImpExpCommodityInfo().getCountry() == null ? ""
					: temp.getImpExpCommodityInfo().getCountry().getCode();

			if (isProduct) {

				m.setBcsCustomsDeclarationCommInfo(map.get(temp
						.getContractExg().getSeqNum()
						+ "/@/"
						+ temp.getContractExg().getComplex().getCode()
						+ "/@/"
						+ countryCode));

			} else {

				m.setBcsCustomsDeclarationCommInfo(map.get(temp
						.getContractImg().getSeqNum()
						+ "/@/"
						+ temp.getContractImg().getComplex().getCode()
						+ "/@/"
						+ countryCode));
			}

			this.bcsImpExpRequestDao.saveMakeBcsCustomsDeclaration(m);
		}
	}

	/**
	 * 进出口申请单据生成报关单时,修改进出口单据的生成报关单字段为true
	 * 
	 * @param dataSource
	 *            临时的进出口申请单表体资料
	 * @return List 是ImpExpRequestBill型，申请单表头
	 */
	public List getImpExpRequestBillInfo(Set<ImpExpRequestBill> set,
			BcsCustomsDeclaration bcsCustomsDeclaration,
			List<ImpExpRequestBill> impExpRequestBillList) {

		// List<ImpExpRequestBill> impExpRequestBillList = new
		// ArrayList<ImpExpRequestBill>();
		// Set<ImpExpRequestBill> set = new HashSet<ImpExpRequestBill>();
		// for (int i = 0; i < dataSource.size(); i++) {
		// TempBcsImpExpCommodityInfo t = dataSource.get(i);
		// set.add(t.getImpExpCommodityInfo().getImpExpRequestBill());
		// }
		/**
		 * 修改结转单据的已转关封申请单字段
		 */
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			ImpExpRequestBill t = (ImpExpRequestBill) iterator.next();

			t = bcsEncDao.findImpExpRequestBillById(t.getId());
			/**
			 * 写报关单流水号
			 **/
			String SerialNumber = String.valueOf(bcsCustomsDeclaration
					.getSerialNumber());
			String AlllistNo = t.getAllBillNo();

			String contractNo = bcsCustomsDeclaration.getContract();
			String billContractNo = t.getContractNo();
			String[] value = getExistBillAndContractNo(AlllistNo,
					billContractNo, SerialNumber, contractNo);

			t.setAllBillNo(value[0]);

			/**
			 * 回写合同号
			 */
			t.setContractNo(value[1]);

			Integer tocustomCount = this.bcsEncDao.getInfoForToCustom(t);
			t.setToCustomCount(tocustomCount);
			if (t.getItemCount() != null
					&& t.getItemCount().equals(tocustomCount)) { // 判断是否全部转关
				t.setIsCustomsBill(new Boolean(true));
			}
			this.bcsEncDao.saveImpExpRequestBill(t);
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
	 *            临时的进出口申请单表体资料
	 * @param isProduct
	 *            成品判断，true位成品
	 * @return List 是TempBcsImpExpCommodityInfo型，临时的进出口申请单表体资料
	 * 
	 *         2010-06-18 hcl修改 原产国不同的表体要分开
	 */
	private List<TempBcsImpExpCommodityInfo> mergerImpExpCommodityInfo(
			List<TempBcsImpExpCommodityInfo> dataSource, boolean isProduct) {
		Map<String, TempBcsImpExpCommodityInfo> map = new HashMap<String, TempBcsImpExpCommodityInfo>();
		try {
			for (int i = 0; i < dataSource.size(); i++) {
				TempBcsImpExpCommodityInfo t = dataSource.get(i);
				if (i == 0) {
					if (isProduct) { // 是成品
						TempBcsImpExpCommodityInfo newTemp = new TempBcsImpExpCommodityInfo();
						PropertyUtils.copyProperties(newTemp, t);
						ImpExpCommodityInfo newImp = new ImpExpCommodityInfo();
						PropertyUtils.copyProperties(newImp,
								t.getImpExpCommodityInfo());
						newTemp.setImpExpCommodityInfo(newImp);

						Double unitConvert = this.bcsEncDao.findUnitConvert(
								isProduct, newTemp);// 查找折算比例
						String comUint = this.bcsEncDao.findComUnit(isProduct,
								newTemp);
						// newTemp.getImpExpCommodityInfo().setQuantity(
						// newTemp.getImpExpCommodityInfo().getQuantity()
						// * unitConvert);
						if (comUint.equals("千克")) {
							newTemp.getImpExpCommodityInfo().setQuantity(
									newTemp.getImpExpCommodityInfo()
											.getNetWeight());
						} else {
							newTemp.getImpExpCommodityInfo().setQuantity(
									newTemp.getImpExpCommodityInfo()
											.getQuantity() * unitConvert);
						}

						map.put(newTemp.getContractExg().getComplex() == null ? ""
								: newTemp.getContractExg().getComplex()
										.getCode()
										+ "/"
										+ newTemp.getContractExg().getName()
										+ "/"
										+ newTemp.getContractExg().getSpec()
										+ "/"
										+ newTemp.getContractExg().getUnit()
												.getName()
										+ "/"
										+ newTemp.getImpExpCommodityInfo()
												.getCountry().getName(),
								newTemp);
						// 单价
						newTemp.getImpExpCommodityInfo().setUnitPrice(
								newTemp.getImpExpCommodityInfo()
										.getAmountPrice()
										/ newTemp.getImpExpCommodityInfo()
												.getQuantity());

					} else { // 是料件
						TempBcsImpExpCommodityInfo newTemp = new TempBcsImpExpCommodityInfo();
						PropertyUtils.copyProperties(newTemp, t);
						ImpExpCommodityInfo newImp = new ImpExpCommodityInfo();
						PropertyUtils.copyProperties(newImp,
								t.getImpExpCommodityInfo());
						newTemp.setImpExpCommodityInfo(newImp);
						Double unitConvert = this.bcsEncDao.findUnitConvert(
								isProduct, newTemp);// 查找折算比例
						String comUint = this.bcsEncDao.findComUnit(isProduct,
								newTemp);
						// newTemp.getImpExpCommodityInfo().setQuantity(
						// newTemp.getImpExpCommodityInfo().getQuantity()
						// * unitConvert);
						if (comUint.equals("千克")) {
							newTemp.getImpExpCommodityInfo().setQuantity(
									newTemp.getImpExpCommodityInfo()
											.getNetWeight());
						} else {
							newTemp.getImpExpCommodityInfo().setQuantity(
									newTemp.getImpExpCommodityInfo()
											.getQuantity() * unitConvert);
						}
						map.put(newTemp.getContractImg().getComplex() == null ? ""
								: newTemp.getContractImg().getComplex()
										.getCode()
										+ "/"
										+ newTemp.getContractImg().getName()
										+ "/"
										+ newTemp.getContractImg().getSpec()
										+ "/"
										+ newTemp.getContractImg().getUnit()
												.getName()
										+ "/"
										+ newTemp.getImpExpCommodityInfo()
												.getCountry().getName(),
								newTemp);
						// 单价
						newTemp.getImpExpCommodityInfo().setUnitPrice(
								newTemp.getImpExpCommodityInfo()
										.getAmountPrice()
										/ newTemp.getImpExpCommodityInfo()
												.getQuantity());

					}

					continue;
				} else {
					TempBcsImpExpCommodityInfo temp = null;
					if (isProduct) { // 是成品
						temp = map
								.get(t.getContractExg().getComplex() == null ? ""
										: t.getContractExg().getComplex()
												.getCode()
												+ "/"
												+ t.getContractExg().getName()
												+ "/"
												+ t.getContractExg().getSpec()
												+ "/"
												+ t.getContractExg().getUnit()
														.getName()
												+ "/"
												+ t.getImpExpCommodityInfo()
														.getCountry().getName());
					} else { // 是料件
						temp = map
								.get(t.getContractImg().getComplex() == null ? ""
										: t.getContractImg().getComplex()
												.getCode()
												+ "/"
												+ t.getContractImg().getName()
												+ "/"
												+ t.getContractImg().getSpec()
												+ "/"
												+ t.getContractImg().getUnit()
														.getName()
												+ "/"
												+ t.getImpExpCommodityInfo()
														.getCountry().getName());
					}
					if (temp != null) {
						ImpExpCommodityInfo data = temp
								.getImpExpCommodityInfo();
						Double unitConvert = this.bcsEncDao.findUnitConvert(
								isProduct, t);// 查找折算比例
						Double quantity = Double
								.valueOf((data.getQuantity() == null ? 0 : data
										.getQuantity().doubleValue())
										+ (t.getImpExpCommodityInfo() == null ? 0
												: t.getImpExpCommodityInfo()
														.getQuantity()
														.doubleValue()
														* unitConvert));

						Double grossWeight = Double
								.valueOf((data.getGrossWeight() == null ? 0
										: data.getGrossWeight().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getGrossWeight() == null ? 0
												: t.getImpExpCommodityInfo()
														.getGrossWeight()
														.doubleValue()));
						Double netWeight = Double
								.valueOf((data.getNetWeight() == null ? 0
										: data.getNetWeight().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getNetWeight() == null ? 0 : t
												.getImpExpCommodityInfo()
												.getNetWeight().doubleValue()));
						Double cubage = Double
								.valueOf((data.getBulks() == null ? 0 : data
										.getBulks().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getBulks() == null ? 0 : t
												.getImpExpCommodityInfo()
												.getBulks().doubleValue()));
						Double money = Double
								.valueOf((data.getAmountPrice() == null ? 0
										: data.getAmountPrice().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getAmountPrice() == null ? 0
												: t.getImpExpCommodityInfo()
														.getAmountPrice()
														.doubleValue()));

						Integer piece = Integer
								.valueOf((data.getPiece() == null ? 0 : data
										.getPiece())
										+ (t.getImpExpCommodityInfo()
												.getPiece() == null ? 0 : t
												.getImpExpCommodityInfo()
												.getPiece().intValue()));
						Double workUsd = Double
								.valueOf((data.getWorkUsd() == null ? 0 : data
										.getWorkUsd())
										+ (t.getImpExpCommodityInfo()
												.getWorkUsd() == null ? 0
												: CommonUtils
														.getDoubleExceptNull(t
																.getImpExpCommodityInfo()
																.getWorkUsd()
																.doubleValue())));

						String boxNo = data.getBoxNo();// 箱号
						String newBoxNo = "";
						if (boxNo != null && !"".equals(boxNo)) {
							newBoxNo = getNotExistBoxNo(boxNo, t
									.getImpExpCommodityInfo().getBoxNo());
						} else {
							newBoxNo = t.getImpExpCommodityInfo().getBoxNo();
						}
						data.setBoxNo(newBoxNo);// 箱号
						data.setWorkUsd(workUsd);// 加工费总价
						data.setQuantity(quantity);// 数量
						data.setPiece(piece);// 件数
						data.setGrossWeight(grossWeight);// 毛重
						data.setNetWeight(netWeight);// 净重
						data.setBulks(cubage);// 体积
						data.setAmountPrice(money);// 总金额
						if (quantity != 0) {
							data.setInvgrossWeight(grossWeight / quantity);// 毛重
							data.setInvnetWeight(netWeight / quantity);// 单净重
							data.setUnitPrice(money / quantity);// 单价
						}
						// 申请单明细中出现包装方式不一致的合并 true:不一样的包装方式的归并】
						// false：包装方式一致的归并 ,用于申请单明细有包装方式的插件。
						if (data.getWrapType() != null
								&& t.getImpExpCommodityInfo().getWrapType() != null
								&& !data.getWrapType().equals(
										t.getImpExpCommodityInfo()
												.getWrapType())) {
							temp.setIsDifferWrapToMerger(true);
						} else {
							temp.setIsDifferWrapToMerger(false);
						}
					} else {
						if (isProduct) { // 是成品
							TempBcsImpExpCommodityInfo newTemp = new TempBcsImpExpCommodityInfo();
							PropertyUtils.copyProperties(newTemp, t);
							ImpExpCommodityInfo newImp = new ImpExpCommodityInfo();
							PropertyUtils.copyProperties(newImp,
									t.getImpExpCommodityInfo());
							newTemp.setImpExpCommodityInfo(newImp);
							Double unitConvert = this.bcsEncDao
									.findUnitConvert(isProduct, newTemp);// 查找折算比例
							String comUint = this.bcsEncDao.findComUnit(
									isProduct, newTemp);
							// newTemp.getImpExpCommodityInfo().setQuantity(
							// newTemp.getImpExpCommodityInfo()
							// .getQuantity()
							// * unitConvert);
							if (comUint.equals("千克")) {
								newTemp.getImpExpCommodityInfo().setQuantity(
										newTemp.getImpExpCommodityInfo()
												.getNetWeight());
							} else {
								newTemp.getImpExpCommodityInfo().setQuantity(
										newTemp.getImpExpCommodityInfo()
												.getQuantity() * unitConvert);
							}
							// 单价
							newTemp.getImpExpCommodityInfo().setUnitPrice(
									newTemp.getImpExpCommodityInfo()
											.getAmountPrice()
											/ newTemp.getImpExpCommodityInfo()
													.getQuantity());
							map.put(t.getContractExg().getComplex() == null ? ""
									: t.getContractExg().getComplex().getCode()
											+ "/"
											+ t.getContractExg().getName()
											+ "/"
											+ t.getContractExg().getSpec()
											+ "/"
											+ t.getContractExg().getUnit()
													.getName()
											+ "/"
											+ t.getImpExpCommodityInfo()
													.getCountry().getName(),
									newTemp);

						} else { // 是料件
							TempBcsImpExpCommodityInfo newTemp = new TempBcsImpExpCommodityInfo();
							PropertyUtils.copyProperties(newTemp, t);
							ImpExpCommodityInfo newImp = new ImpExpCommodityInfo();
							PropertyUtils.copyProperties(newImp,
									t.getImpExpCommodityInfo());
							newTemp.setImpExpCommodityInfo(newImp);
							Double unitConvert = this.bcsEncDao
									.findUnitConvert(isProduct, newTemp);// 查找折算比例
							String comUint = this.bcsEncDao.findComUnit(
									isProduct, newTemp);
							// newTemp.getImpExpCommodityInfo().setQuantity(
							// newTemp.getImpExpCommodityInfo()
							// .getQuantity()
							// * unitConvert);
							if (comUint.equals("千克")) {
								newTemp.getImpExpCommodityInfo().setQuantity(
										newTemp.getImpExpCommodityInfo()
												.getNetWeight());
							} else {
								newTemp.getImpExpCommodityInfo().setQuantity(
										newTemp.getImpExpCommodityInfo()
												.getQuantity() * unitConvert);
							}
							// 单价
							newTemp.getImpExpCommodityInfo().setUnitPrice(
									newTemp.getImpExpCommodityInfo()
											.getAmountPrice()
											/ newTemp.getImpExpCommodityInfo()
													.getQuantity());
							map.put(newTemp.getContractImg().getComplex() == null ? ""
									: newTemp.getContractImg().getComplex()
											.getCode()
											+ "/"
											+ newTemp.getContractImg()
													.getName()
											+ "/"
											+ newTemp.getContractImg()
													.getSpec()
											+ "/"
											+ newTemp.getContractImg()
													.getUnit().getName()
											+ "/"
											+ newTemp.getImpExpCommodityInfo()
													.getCountry().getName(),
									newTemp);
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TempBcsImpExpCommodityInfo> impExpCommodityList = new ArrayList<TempBcsImpExpCommodityInfo>();
		impExpCommodityList.addAll(map.values());
		return impExpCommodityList;
	}

	/**
	 * 把所有相同的备案商品编码 数量等其它项整合 成一条
	 * 
	 * @param dataSource
	 *            临时的进出口申请单表体资料
	 * @param isProduct
	 *            成品判断，true位成品
	 * @return List 是TempBcsImpExpCommodityInfo型，临时的进出口申请单表体资料
	 * 
	 *         2010-06-18 hcl修改 原产国不同的表体要分开
	 */
	private List<TempBcsImpExpCommodityInfo> mergerImpExpCommodityInfos(
			List<TempBcsImpExpCommodityInfo> dataSource, boolean isProduct) {

		List<TempBcsImpExpCommodityInfo> returnList = new ArrayList<TempBcsImpExpCommodityInfo>();

		Map<String, TempBcsImpExpCommodityInfo> map = new HashMap<String, TempBcsImpExpCommodityInfo>();

		for (int i = 0; i < dataSource.size(); i++) {

			TempBcsImpExpCommodityInfo temp = dataSource.get(i);

			// ImpExpCommodityInfo newImpExp = temp.getImpExpCommodityInfo();

			Integer credenceNo = null;

			if (isProduct) {

				credenceNo = temp.getContractExg().getCredenceNo();

			} else {

				credenceNo = temp.getContractImg().getCredenceNo();

			}

			Double unitConvert = this.bcsEncDao.findUnitConvert(credenceNo,
					temp, isProduct);// 查找折算比例

			System.out.println("料号"
					+ temp.getImpExpCommodityInfo().getMateriel().getPtNo()
					+ "折算比例===============" + unitConvert);

			// key = 料件商品编码+料件名称+料件规格+料件单位名称+料件原产地名称
			String key = getKey(temp, isProduct);

			TempBcsImpExpCommodityInfo oldTemp = map.get(key);

			if (oldTemp != null) {
				// 合并map中已经存在的对象
				mergerExist(oldTemp, temp, unitConvert, isProduct);

				oldTemp.getCommodityInfos().addAll(temp.getCommodityInfos());

				oldTemp.getCommodityInfos().add(temp);

			} else {

				oldTemp = new TempBcsImpExpCommodityInfo();

				// 合并map中不存在的对象
				mergerInexistence(oldTemp, temp, unitConvert, isProduct);

				map.put(key, oldTemp);

				returnList.add(oldTemp);
			}

		}

		return returnList;
	}

	/**
	 * 合并存在的对象
	 * 
	 * @param oldTemp
	 *            map中已保存的对象
	 * @param newImpExp
	 *            还未处理的对象
	 */
	private void mergerExist(TempBcsImpExpCommodityInfo oldTemp,
			TempBcsImpExpCommodityInfo newTemp, Double unitConvert,
			Boolean isProduct) {
		// /
		ImpExpCommodityInfo newImpExp = newTemp.getImpExpCommodityInfo();
		ImpExpCommodityInfo oldImpExp = oldTemp.getImpExpCommodityInfo();
		Double grossWeight = CaleUtil.add(oldImpExp.getGrossWeight(),
				newImpExp.getGrossWeight());
		Double netWeight = CaleUtil.add(oldImpExp.getNetWeight(),
				newImpExp.getNetWeight());
		Double cubage = CaleUtil
				.add(oldImpExp.getBulks(), newImpExp.getBulks());

		Integer piece = CommonUtils.getIntegerExceptNull(oldImpExp.getPiece())
				+ CommonUtils.getIntegerExceptNull(newImpExp.getPiece());
		Double workUsd = CaleUtil.add(oldImpExp.getWorkUsd(),
				newImpExp.getWorkUsd());
		Double money = CaleUtil.add(oldImpExp.getAmountPrice(),
				newImpExp.getAmountPrice());

		Unit unit = null;
		if (isProduct) {
			unit = newTemp.getContractExg().getUnit();
		} else {
			unit = newTemp.getContractImg().getUnit();
		}
		Double quantity = 0.0;
		if (unit != null && unit.getName().equals("千克")) {
			quantity = netWeight;// 等于千克是去净重
		} else {
			quantity = CaleUtil.add(oldImpExp.getQuantity(),
					CommonUtils.getDoubleExceptNull(newImpExp.getQuantity())
							* unitConvert);
		}

		String boxNo = oldImpExp.getBoxNo();// 箱号
		String newBoxNo = "";
		if (boxNo != null && !"".equals(boxNo)) {
			newBoxNo = getNotExistBoxNo(boxNo, newImpExp.getBoxNo());
		} else {
			newBoxNo = newImpExp.getBoxNo();
		}
		oldImpExp.setBoxNo(newBoxNo);// 箱号
		oldImpExp.setWorkUsd(workUsd);// 加工费总价
		oldImpExp.setPiece(piece);// 件数
		oldImpExp.setGrossWeight(grossWeight);// 毛重
		oldImpExp.setNetWeight(netWeight);// 净重
		oldImpExp.setBulks(cubage);// 体积
		oldImpExp.setAmountPrice(money);// 总金额
		oldImpExp.setQuantity(quantity);// 数量

		if (quantity != 0) {
			oldImpExp.setInvgrossWeight(grossWeight / quantity);// 毛重
			oldImpExp.setInvnetWeight(netWeight / quantity);// 单净重
			oldImpExp.setUnitPrice(money / quantity);// 单价
		}
		// 申请单明细中出现包装方式不一致的合并 true:不一样的包装方式的归并】
		// false：包装方式一致的归并 ,用于申请单明细有包装方式的插件。
		if (oldImpExp.getWrapType() != null && newImpExp.getWrapType() != null
				&& !oldImpExp.getWrapType().equals(newImpExp.getWrapType())) {
			oldTemp.setIsDifferWrapToMerger(true);
		} else {
			oldTemp.setIsDifferWrapToMerger(false);
		}
	}

	/**
	 * 合并不在的对象
	 * 
	 * @param oldTemp
	 *            空对象
	 * @param newImpExp
	 *            还未处理的对象
	 */
	private void mergerInexistence(TempBcsImpExpCommodityInfo oldTemp,
			TempBcsImpExpCommodityInfo temp, Double unitConvert,
			Boolean isProduct) {

		ImpExpCommodityInfo newImpExp = temp.getImpExpCommodityInfo();

		try {

			// 为保全归并前 数据不被修改
			PropertyUtils.copyProperties(oldTemp, temp);

			ImpExpCommodityInfo impExpCommodityInfo = new ImpExpCommodityInfo();

			PropertyUtils.copyProperties(impExpCommodityInfo, newImpExp);

			oldTemp.setImpExpCommodityInfo(impExpCommodityInfo);

		} catch (Exception e) {

			e.printStackTrace();

		}

		if (newImpExp.getQuantity() == null) {

			System.out.println("数量为空！");
			throw new RuntimeException("数量为空！");
		}

		Unit unit = null;

		if (isProduct) {

			unit = temp.getContractExg().getUnit();

		} else {

			unit = temp.getContractImg().getUnit();

		}

		Double quantity = 0.0;

		if (unit != null && unit.getName().equals("千克")) {

			quantity = newImpExp.getNetWeight();

		} else {
			quantity = newImpExp.getQuantity() * unitConvert;
		}

		// 设置数量
		oldTemp.getImpExpCommodityInfo().setQuantity(quantity);

		// 单价
		if (newImpExp.getAmountPrice() != null
				&& newImpExp.getQuantity() != null && quantity != null
				&& quantity != 0) {
			oldTemp.getImpExpCommodityInfo().setUnitPrice(
					newImpExp.getAmountPrice() / quantity);
		}
	}

	/**
	 * 获取key 料件商品编码+料件名称+料件规格+料件单位名称+料件原产地名称
	 * 
	 * @param temp
	 * @param isProduct
	 * @return
	 */
	private String getKey(TempBcsImpExpCommodityInfo temp, Boolean isProduct) {
		String complexCode = null;// 商品编码
		String name = null;// 名称
		String spec = null;// 规格
		String unitName = null;// 单位
		String countryName = null;// 原产地

		countryName = getString(temp.getImpExpCommodityInfo().getCountry() == null ? ""
				: temp.getImpExpCommodityInfo().getCountry().getName());

		if (isProduct) {
			ContractExg exg = temp.getContractExg();
			complexCode = getString(exg.getName());
			name = getString(exg.getComplex() == null ? "" : exg.getComplex()
					.getCode());
			spec = getString(exg.getSpec());
			unitName = getString(exg.getUnit() == null ? "" : exg.getUnit()
					.getName());
		} else {
			ContractImg img = temp.getContractImg();
			complexCode = getString(img.getName());
			name = getString(img.getComplex() == null ? "" : img.getComplex()
					.getCode());
			spec = getString(img.getSpec());
			unitName = getString(img.getUnit() == null ? "" : img.getUnit()
					.getName());
		}

		// key = 料件商品编码+料件名称+料件规格+料件单位名称+料件原产地名称
		String key = complexCode + "/" + name + "/" + spec + "/" + unitName
				+ "/" + countryName;

		return key;
	}

	private String getString(String stu) {
		if (stu == null) {
			return "";
		}
		return stu;
	}

	/**
	 * 把所有相同的备案商品编码 数量等其它项整合 成一条(联胜)
	 * 
	 * @param dataSource
	 *            临时的进出口申请单表体资料
	 * @param isProduct
	 *            成品判断，true位成品
	 * @return List 是TempBcsImpExpCommodityInfo型，临时的进出口申请单表体资料
	 */
	private List<TempBcsImpExpCommodityInfo> mergerImpExpCommodityInfoLS(
			List<TempBcsImpExpCommodityInfo> dataSource, boolean isProduct) {
		// 定义一个Map用来过滤合并key相同表体
		Map<String, TempBcsImpExpCommodityInfo> map = new HashMap<String, TempBcsImpExpCommodityInfo>();
		Map<String, BcsInnerMerge> innerMap = CommonUtils.listToMap(
				this.bcsInnerMergeDao.findBcsInnerMerge(),
				new GetKeyValueImpl<BcsInnerMerge>() {
					public String getKey(BcsInnerMerge t) {
						return t.getMateriel().getPtNo();
					}
				});
		try {
			for (int i = 0; i < dataSource.size(); i++) {
				TempBcsImpExpCommodityInfo t = dataSource.get(i);

				// 内部对应关系
				BcsInnerMerge innerMerge = innerMap.get(t
						.getImpExpCommodityInfo().getMateriel().getPtNo());
				if (innerMerge == null) {
					throw new RuntimeException(t.getImpExpCommodityInfo()
							.getMateriel().getPtNo()
							+ "没有内部对应关系");
				}

				// KEY：（编码、名称、规格、单位）、单价及原产国（产销国）
				String key = "";

				// 十位商品编码
				BcsTenInnerMerge b = innerMerge.getBcsTenInnerMerge();

				key += b.getComplex() == null ? "" : b.getComplex().getCode();
				key += b.getName() == null ? "" : ("/" + b.getName());
				key += b.getSpec() == null ? "" : ("/" + b.getSpec());
				key += b.getComUnit().getCode() == null ? "" : ("/" + b
						.getComUnit().getCode());
				key += t.getImpExpCommodityInfo().getUnitPrice() == null ? ""
						: ("/" + t.getImpExpCommodityInfo().getUnitPrice()); // 单价
				key += t.getImpExpCommodityInfo().getCountry() == null ? ""
						: ("/" + t.getImpExpCommodityInfo().getCountry()
								.getCode());// 原产国
				key += t.getImpExpCommodityInfo().getVersion() == null ? ""
						: ("/" + t.getImpExpCommodityInfo().getVersion());// 版本

				if (i == 0) {
					this.creatTempBcsImpExpCommodityInfo(isProduct, t, key, map);
				} else {
					TempBcsImpExpCommodityInfo info = null;
					info = map.get(key);
					if (info != null) {
						ImpExpCommodityInfo data = info
								.getImpExpCommodityInfo();
						// 合并料号
						Materiel m = (Materiel) info.getImpExpCommodityInfo()
								.getMateriel().clone();
						m.setPtNo(info.getImpExpCommodityInfo().getMateriel()
								.getPtNo()
								+ ","
								+ t.getImpExpCommodityInfo().getMateriel()
										.getPtNo());
						info.getImpExpCommodityInfo().setMateriel(m);
						Double unitConvert = this.bcsEncDao.findUnitConvert(
								isProduct, t);// 查找折算比例
						Double quantity = Double
								.valueOf((data.getQuantity() == null ? 0 : data
										.getQuantity().doubleValue())
										+ (t.getImpExpCommodityInfo() == null ? 0
												: t.getImpExpCommodityInfo()
														.getQuantity()
														.doubleValue()
														* unitConvert));

						Double grossWeight = Double
								.valueOf((data.getGrossWeight() == null ? 0
										: data.getGrossWeight().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getGrossWeight() == null ? 0
												: t.getImpExpCommodityInfo()
														.getGrossWeight()
														.doubleValue()));
						Double netWeight = Double
								.valueOf((data.getNetWeight() == null ? 0
										: data.getNetWeight().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getNetWeight() == null ? 0 : t
												.getImpExpCommodityInfo()
												.getNetWeight().doubleValue()));
						Double cubage = Double
								.valueOf((data.getBulks() == null ? 0 : data
										.getBulks().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getBulks() == null ? 0 : t
												.getImpExpCommodityInfo()
												.getBulks().doubleValue()));
						Double money = Double
								.valueOf((data.getAmountPrice() == null ? 0
										: data.getAmountPrice().doubleValue())
										+ (t.getImpExpCommodityInfo()
												.getAmountPrice() == null ? 0
												: t.getImpExpCommodityInfo()
														.getAmountPrice()
														.doubleValue()));

						Integer piece = Integer
								.valueOf((data.getPiece() == null ? 0 : data
										.getPiece())
										+ (t.getImpExpCommodityInfo() == null ? 0
												: t.getImpExpCommodityInfo()
														.getPiece()));
						Double workUsd = Double
								.valueOf((data.getWorkUsd() == null ? 0 : data
										.getWorkUsd())
										+ (t.getImpExpCommodityInfo() == null ? 0
												: CommonUtils
														.getDoubleExceptNull(t
																.getImpExpCommodityInfo()
																.getWorkUsd())));

						String boxNo = data.getBoxNo();// 箱号
						String newBoxNo = "";
						if (boxNo != null && !"".equals(boxNo)) {
							newBoxNo = getNotExistBoxNo(boxNo, t
									.getImpExpCommodityInfo().getBoxNo());
						} else {
							newBoxNo = t.getImpExpCommodityInfo().getBoxNo();
						}
						data.setBoxNo(newBoxNo);// 箱号
						data.setWorkUsd(workUsd);// 加工费总价
						data.setQuantity(quantity);// 数量
						data.setPiece(piece);// 件数
						data.setGrossWeight(grossWeight);// 毛重
						data.setNetWeight(netWeight);// 净重
						data.setBulks(cubage);// 体积
						data.setAmountPrice(money);// 总金额
						if (quantity != 0) {
							data.setInvgrossWeight(grossWeight / quantity);// 毛重
							data.setInvnetWeight(netWeight / quantity);// 单净重
							data.setUnitPrice(money / quantity);// 单价
						}

					} else {
						this.creatTempBcsImpExpCommodityInfo(isProduct, t, key,
								map);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		List<TempBcsImpExpCommodityInfo> impExpCommodityList = new ArrayList<TempBcsImpExpCommodityInfo>();
		impExpCommodityList.addAll(map.values());
		return impExpCommodityList;
	}

	/**
	 * 创建一个TempBcsImpExpCommodityInfo对象，把这个对象放入Map中 创建规则：
	 * 
	 * @param t
	 * @param key
	 * @param map
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void creatTempBcsImpExpCommodityInfo(boolean isProduct,
			TempBcsImpExpCommodityInfo t, String key,
			Map<String, TempBcsImpExpCommodityInfo> map)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		TempBcsImpExpCommodityInfo tempBcsIEInfo = new TempBcsImpExpCommodityInfo();
		PropertyUtils.copyProperties(tempBcsIEInfo, t);
		ImpExpCommodityInfo ieInfo = new ImpExpCommodityInfo();
		PropertyUtils.copyProperties(ieInfo, t.getImpExpCommodityInfo());
		tempBcsIEInfo.setImpExpCommodityInfo(ieInfo);
		Double unitConvert = this.bcsEncDao.findUnitConvert(isProduct,
				tempBcsIEInfo);// 查找折算比例
		tempBcsIEInfo.getImpExpCommodityInfo().setQuantity(
				tempBcsIEInfo.getImpExpCommodityInfo().getQuantity()
						* unitConvert);
		map.put(key, tempBcsIEInfo);
		// 单价
		tempBcsIEInfo.getImpExpCommodityInfo().setUnitPrice(
				tempBcsIEInfo.getImpExpCommodityInfo().getAmountPrice()
						/ tempBcsIEInfo.getImpExpCommodityInfo().getQuantity());

	}

	/**
	 * 数据转换
	 * 
	 * @param shuliang
	 *            要转换的数据
	 * @return Double 转换后的数据
	 */
	private Double forInterNum(double shuliang) {
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}

	/**
	 * 生成报关单明细来自合同成品清单
	 * 
	 * @param bcsCustomsDeclaration
	 *            报关单表头
	 * @param commInfo
	 *            报关单物料
	 * @param t
	 *            临时的进出口申请单表体资料
	 * @param netWeightParameter
	 *            净重参数
	 * @return BcsCustomsDeclarationCommInfo 报关单物料资料
	 */
	public BcsCustomsDeclarationCommInfo makeBcsCustomsDeclarationCommInfoByProduct(
			BcsCustomsDeclaration bcsCustomsDeclaration,
			BcsCustomsDeclarationCommInfo commInfo,
			TempBcsImpExpCommodityInfo t, Double netWeightParameter, List list,
			boolean isPriceFromContract) {

		// 报关单参数
		CompanyOther co = (CompanyOther) companyDao.findCompanyOther().get(0);

		// 设置净重毛重的小数位
		int netWeightFraction = 4;

		ContractExg contractExg = t.getContractExg();

		for (int i = 0; i < list.size(); i++) {

			BcsCustomsDeclarationCommInfo info = (BcsCustomsDeclarationCommInfo) list
					.get(i);

			if (isCommInfoEqualExg(info, t)) {

				commInfo = info;

				break;

			}
		}

		if (commInfo == null) {

			commInfo = new BcsCustomsDeclarationCommInfo();

			commInfo.setBaseCustomsDeclaration(bcsCustomsDeclaration);

			contractExg = t.getContractExg();
			/**
			 * 这里是清单流水号
			 */
			commInfo.setCommSerialNo(contractExg.getSeqNum());
			commInfo.setComplex(contractExg.getComplex());
			commInfo.setCommName(contractExg.getName());
			// if(co.getIsSpecification().equals(true)){
			// commInfo.setDeclareSpec(commInfo.getCommSpec());
			// }
			commInfo.setCommSpec(contractExg.getSpec());
			commInfo.setUnit(contractExg.getUnit());
			commInfo.setCompany(CommonUtils.getCompany());
			commInfo.setPtNo(t.getImpExpCommodityInfo().getMateriel().getPtNo());
			if (t.getIsDifferWrapToMerger() != null
					&& t.getIsDifferWrapToMerger()) {
				Wrap wrapType = new Wrap();
				wrapType.setCode("7");
				wrapType.setName("其他");
				commInfo.setWrapType(wrapType);
			} else {
				commInfo.setWrapType(t.getImpExpCommodityInfo().getWrapType());
			}
			CustomsDeclarationSet other = this.bcsEncLogic
					.findCustomsSet(commInfo.getBaseCustomsDeclaration()
							.getImpExpType());
			if (other != null) {
				commInfo.setUses(other.getUses());
				commInfo.setLevyMode(other.getLevyMode());
			}
			commInfo.setVersion(t.getImpExpCommodityInfo().getVersion());
			commInfo.setCountry(t.getImpExpCommodityInfo().getCountry());
			/**
			 * 报关数量这里来自报关申请单数量
			 */
			double amount = 0.0;
			// double grossWeight = 0.0;
			// double netWeight = 0.0;
			amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0 : t
					.getImpExpCommodityInfo().getQuantity();
			/**
			 * 数量
			 */
			commInfo.setCommAmount(CommonUtils.getDoubleByDigit(amount,
					co.getCustomAmountNum()));

			if (isPriceFromContract) {
				List priceList = this.bcsEncDao.findUnitPrice(
						contractExg.getSeqNum(), bcsCustomsDeclaration, true);
				// 单价
				Double unitPrice = (Double) priceList.get(0);
				commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
						unitPrice, co.getCustomPriceNum()));
				// 总金额
				commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
						commInfo.getCommAmount() * commInfo.getCommUnitPrice(),
						co.getCustomTotalNum()));
			} else {
				// 总金额
				commInfo.setCommTotalPrice(CommonUtils
						.getDoubleByDigit(t.getImpExpCommodityInfo()
								.getAmountPrice() == null ? 0.0 : t
								.getImpExpCommodityInfo().getAmountPrice(), co
								.getCustomTotalNum()));
				// 单价
				commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(t
						.getImpExpCommodityInfo().getUnitPrice(), co
						.getCustomPriceNum()));
			}
			// 加工费总价
			commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(t
					.getImpExpCommodityInfo().getWorkUsd() == null ? 0.0 : t
					.getImpExpCommodityInfo().getWorkUsd(), co
					.getCustomTotalNum()));

			commInfo.setNetWeight(CommonUtils
					.getDoubleByDigit((t.getImpExpCommodityInfo()
							.getNetWeight() * netWeightParameter),
							netWeightFraction));
			commInfo.setGrossWeight(CommonUtils
					.getDoubleByDigit((t.getImpExpCommodityInfo()
							.getGrossWeight() * netWeightParameter),
							netWeightFraction));

			// if (contractExg.getUnit() != null
			// && contractExg.getUnit().getName().trim().equals("个")) {
			//
			// amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
			// : t.getImpExpCommodityInfo().getQuantity();
			// grossWeight = amount
			// * (contractExg.getUnitGrossWeight() == null ? 0.0
			// : contractExg.getUnitGrossWeight());
			// netWeight = amount
			// * (contractExg.getUnitNetWeight() == null ? 0.0
			// : contractExg.getUnitNetWeight());
			//
			// } else if (contractExg.getUnit() != null
			// && (contractExg.getUnit().getName().trim().equals("公斤") ||
			// contractExg
			// .getUnit().getName().trim().equals("千克"))) {
			//
			// amount = (t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
			// : t.getImpExpCommodityInfo().getQuantity())
			// * (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0
			// : t.getImpExpCommodityInfo().getNetWeight())
			// * netWeightParameter;
			// /**
			// * 合同比例(合同单位毛重/合同单位净重)
			// */
			// double contractRate = 0.0;
			// double _grossWeight = contractExg.getUnitGrossWeight() == null ?
			// 0.0
			// : contractExg.getUnitGrossWeight();
			// double _netWeight = contractExg.getUnitNetWeight() == null ? 0.0
			// : contractExg.getUnitNetWeight();
			// if (_grossWeight != 0.0 || _netWeight != 0.0) {
			// contractRate = _grossWeight / _netWeight;
			// }
			// netWeight = (t.getImpExpCommodityInfo().getQuantity() == null ?
			// 0.0
			// : t.getImpExpCommodityInfo().getQuantity())
			// * (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0
			// : t.getImpExpCommodityInfo().getNetWeight())
			// * netWeightParameter;
			//
			// /**
			// * 申报单位毛重 = 申报单位净重 * 合同比例(合同单位毛重/合同单位净重)
			// */
			// grossWeight = netWeight * contractRate;
			//
			// }
			//
			// 计算第一法定数量、第二法定数量:
			// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
			// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
			// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
			// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
			if (contractExg.getComplex().getFirstUnit() != null) {
				String unitName = contractExg.getUnit() == null ? ""
						: contractExg.getUnit().getName();
				String firstUnitName = contractExg.getComplex().getFirstUnit()
						.getName();

				if (unitName.equals(firstUnitName)) {// 条件1
					commInfo.setFirstAmount(commInfo.getCommAmount());
				} else if ("千克".equals(firstUnitName)) {// 条件2
					commInfo.setFirstAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap().get(unitName + "+" + firstUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + firstUnitName).toString());
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(amount
							* CommonUtils.getDoubleExceptNull(unitRate),
							co.getCustomAmountNum()));
				} else {
					double unitGene = contractExg.getLegalUnitGene() == null ? 0.0
							: contractExg.getLegalUnitGene();
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(amount
							* unitGene, co.getCustomAmountNum()));
				}
			}

			if (contractExg.getComplex().getSecondUnit() != null) {
				// 当“计量单位”=“第二法定数量” 第二法定数量=数量
				String unitName = contractExg.getUnit() == null ? ""
						: contractExg.getUnit().getName();
				String secondUnitName = contractExg.getComplex()
						.getSecondUnit().getName();
				if (unitName.equals(secondUnitName)) {// 条件1
					commInfo.setSecondAmount(commInfo.getCommAmount());
				} else if ("千克".equals(secondUnitName)) {// 条件2
					commInfo.setSecondAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap()
						.get(unitName + "+" + secondUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + secondUnitName).toString());

					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							amount * CommonUtils.getDoubleExceptNull(unitRate),
							co.getCustomAmountNum()));
				} else {
					double unit2Gene = contractExg.getLegalUnit2Gene() == null ? 0.0
							: contractExg.getLegalUnit2Gene();
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							amount * unit2Gene, co.getCustomAmountNum()));
				}
			}

			//
			// prece
			//
			commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? 0
					: t.getImpExpCommodityInfo().getPiece().intValue());

			commInfo.setBoxNo(t.getImpExpCommodityInfo().getBoxNo());// 箱号

		} else {// 追加到原有的报关单

			/**
			 * 报关数量这里来自报关申请单数量
			 */
			double amount = 0.0;
			// double grossWeight = 0.0;
			// double netWeight = 0.0;
			amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0 : t
					.getImpExpCommodityInfo().getQuantity();// 保留三位小数);
			/**
			 * 数量
			 */
			commInfo.setCommAmount(CommonUtils.getDoubleByDigit(
					commInfo.getCommAmount() + amount, co.getCustomAmountNum()));// 保留三位小数
			/**
			 * 总额
			 */
			commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
					t.getImpExpCommodityInfo().getAmountPrice() == null ? commInfo
							.getCommTotalPrice() : commInfo.getCommTotalPrice()
							+ t.getImpExpCommodityInfo().getAmountPrice(), co
							.getCustomTotalNum()));
			/**
			 * 加工费总价
			 */
			commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					t.getImpExpCommodityInfo().getWorkUsd() == null ? commInfo
							.getWorkUsd() : commInfo.getWorkUsd()
							+ t.getImpExpCommodityInfo().getWorkUsd(), co
							.getCustomTotalNum()));
			/**
			 * 单价
			 */
			if (commInfo.getCommAmount() != 0.0) {
				commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
						commInfo.getCommTotalPrice() / commInfo.getCommAmount(),
						co.getCustomPriceNum()));
			}

			// if (contractExg.getUnit() != null
			// && contractExg.getUnit().getName().trim().equals("个")) {
			// amount = commInfo.getCommAmount();
			// grossWeight = amount
			// * (contractExg.getUnitGrossWeight() == null ? 0.0
			// : contractExg.getUnitGrossWeight());
			// netWeight = amount
			// * (contractExg.getUnitNetWeight() == null ? 0.0
			// : contractExg.getUnitNetWeight());
			//
			// } else if (contractExg.getUnit() != null
			// && (contractExg.getUnit().getName().trim().equals("公斤") ||
			// contractExg
			// .getUnit().getName().trim().equals("千克"))) {
			// amount = commInfo.getCommAmount()
			// * (t.getImpExpCommodityInfo().getNetWeight() == null ? 0.0
			// : t.getImpExpCommodityInfo().getNetWeight())
			// * netWeightParameter;
			// /**
			// * 合同比例(合同单位毛重/合同单位净重)
			// */
			// double contractRate = 0.0;
			// double _grossWeight = contractExg.getUnitGrossWeight() == null ?
			// 0.0
			// : contractExg.getUnitGrossWeight();
			// double _netWeight = contractExg.getUnitNetWeight() == null ? 0.0
			// : contractExg.getUnitNetWeight();
			// if (_grossWeight != 0.0 || _netWeight != 0.0) {
			// contractRate = _grossWeight / _netWeight;
			// }
			// netWeight = commInfo.getNetWeight()
			// + (t.getImpExpCommodityInfo().getQuantity()
			// * t.getImpExpCommodityInfo().getInvnetWeight() *
			// netWeightParameter);
			// /**
			// * 申报单位毛重 = 申报单位净重 * 合同比例(合同单位毛重/合同单位净重)
			// */
			// grossWeight = netWeight * contractRate;
			// }

			commInfo.setNetWeight(CommonUtils.getDoubleByDigit(
					t.getImpExpCommodityInfo().getNetWeight() == null ? commInfo
							.getNetWeight()
							: commInfo.getNetWeight()
									+ (t.getImpExpCommodityInfo()
											.getNetWeight() * netWeightParameter),
					netWeightFraction));

			commInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
					t.getImpExpCommodityInfo().getGrossWeight() == null ? commInfo
							.getGrossWeight()
							: commInfo.getGrossWeight()
									+ (t.getImpExpCommodityInfo()
											.getGrossWeight() * netWeightParameter),
					netWeightFraction));

			if (contractExg.getComplex().getFirstUnit() != null) {

				// 当“计量单位”=“第一法定数量” 第一法定数量=数量
				String unitName = contractExg.getUnit() == null ? ""
						: contractExg.getUnit().getName();

				String firstUnitName = contractExg.getComplex().getFirstUnit()
						.getName();

				if (unitName.equals(firstUnitName)) {// 条件1

					commInfo.setFirstAmount(commInfo.getCommAmount());

				} else if ("千克".equals(firstUnitName)) {// 条件2

					commInfo.setFirstAmount(commInfo.getNetWeight());

				} else if (getUnitRateMap().get(unitName + "+" + firstUnitName) != null) {// 条件3

					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + firstUnitName).toString());
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(
							(amount * CommonUtils.getDoubleExceptNull(unitRate))
									+ commInfo.getFirstAmount(),
							co.getCustomAmountNum()));
				} else {
					double unitGene = contractExg.getLegalUnitGene() == null ? 0.0
							: contractExg.getLegalUnitGene();
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(
							(amount * unitGene) + commInfo.getFirstAmount(),
							co.getCustomAmountNum()));
				}
			}

			if (contractExg.getComplex().getSecondUnit() != null) {
				// 当“计量单位”=“第二法定数量” 第二法定数量=数量
				String unitName = contractExg.getUnit() == null ? ""
						: contractExg.getUnit().getName();
				String secondUnitName = contractExg.getComplex()
						.getSecondUnit().getName();
				if (unitName.equals(secondUnitName)) {
					commInfo.setSecondAmount(commInfo.getCommAmount());
				} else if ("千克".equals(secondUnitName)) {
					commInfo.setSecondAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap()
						.get(unitName + "+" + secondUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + secondUnitName).toString());
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							(amount * CommonUtils.getDoubleExceptNull(unitRate))
									+ commInfo.getSecondAmount(),
							co.getCustomAmountNum()));
				} else {
					double unit2Gene = contractExg.getLegalUnit2Gene() == null ? 0.0
							: contractExg.getLegalUnit2Gene();
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							(amount * unit2Gene) + commInfo.getSecondAmount(),
							co.getCustomAmountNum()));
				}
			}
			//
			// prece
			//
			commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? commInfo
					.getPieces() : commInfo.getPieces()
					+ t.getImpExpCommodityInfo().getPiece().intValue());
			String boxNo = commInfo.getBoxNo();// 箱号
			String newBoxNo = "";
			if (boxNo != null && !"".equals(boxNo)) {
				newBoxNo = getNotExistBoxNo(boxNo, t.getImpExpCommodityInfo()
						.getBoxNo());
			} else {
				newBoxNo = t.getImpExpCommodityInfo().getBoxNo();
			}
			commInfo.setBoxNo(newBoxNo);
			// 设置创建日期
			commInfo.setCreateDate(new Date());
		}

		/**
		 * 流水号在保存时新增
		 */
		// bcsEncLogic.saveCustomsDeclarationCommInfo(commInfo);
		return commInfo;
	}

	public Map getUnitRateMap() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("克+千克", 0.001);
		map.put("个+千个", 0.001);
		map.put("支+千支", 0.001);
		map.put("块+千块", 0.001);
		map.put("米+千米", 0.001);
		map.put("千克+克", 1000.0);
		map.put("千个+个", 1000.0);
		map.put("千支+支", 1000.0);
		map.put("千块+块", 1000.0);
		map.put("千米+米", 1000.0);
		return map;
	}

	/**
	 * 生成报关单明细来自合同料件清单
	 * 
	 * @param bcsCustomsDeclaration
	 *            报关单表头
	 * @param commInfo
	 *            报关单物料
	 * @param t
	 *            临时的进出口申请单表体资料
	 * @return BcsCustomsDeclarationCommInfo 报关单物料资料
	 */
	public BcsCustomsDeclarationCommInfo makeBcsCustomsDeclarationCommInfoByMateriel(
			BcsCustomsDeclaration bcsCustomsDeclaration,
			BcsCustomsDeclarationCommInfo commInfo,
			TempBcsImpExpCommodityInfo t, List list, boolean isPriceFromContract) {
		// 报关单参数
		CompanyOther co = (CompanyOther) companyDao.findCompanyOther().get(0);

		ContractImg contractImg = t.getContractImg();
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsDeclarationCommInfo info = (BcsCustomsDeclarationCommInfo) list
					.get(i);
			if (isCommInfoEqual(info, t)) {
				commInfo = info;
				break;
			}

		}
		if (commInfo == null) {
			commInfo = new BcsCustomsDeclarationCommInfo();
			commInfo.setBaseCustomsDeclaration(bcsCustomsDeclaration);

			/**
			 * 这里是清单流水号
			 */
			// System.out.println("商品序号=" + contractImg.getSeqNum());
			commInfo.setCommSerialNo(contractImg.getSeqNum());
			commInfo.setComplex(contractImg.getComplex());
			commInfo.setCommName(contractImg.getName());
			// 详细型号规格
			commInfo.setDetailNote(contractImg.getDetailNote());
			commInfo.setCommSpec(contractImg.getSpec());
			commInfo.setUnit(contractImg.getUnit());
			commInfo.setCompany(CommonUtils.getCompany());
			commInfo.setLevyMode(contractImg.getLevyMode());
			commInfo.setVersion(t.getImpExpCommodityInfo().getMateriel()
					.getPtVersion());
			commInfo.setCountry(t.getImpExpCommodityInfo().getCountry());
			commInfo.setPtNo(t.getImpExpCommodityInfo().getMateriel().getPtNo());
			if (t.getIsDifferWrapToMerger() != null
					&& t.getIsDifferWrapToMerger()) {
				Wrap wrapType = new Wrap();
				wrapType.setCode("7");
				wrapType.setName("其他");
				commInfo.setWrapType(wrapType);
			} else {
				commInfo.setWrapType(t.getImpExpCommodityInfo().getWrapType());
			}
			CustomsDeclarationSet other = this.bcsEncLogic
					.findCustomsSet(commInfo.getBaseCustomsDeclaration()
							.getImpExpType());
			if (other != null) {
				commInfo.setUses(other.getUses());
				commInfo.setLevyMode(other.getLevyMode());
			}
			/**
			 * 数量
			 */
			double amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0
					: t.getImpExpCommodityInfo().getQuantity();// 保留三位小数);

			commInfo.setCommAmount(CommonUtils.getDoubleByDigit(amount,
					co.getCustomAmountNum()));// 保留三位小数

			if (isPriceFromContract) {
				List priceList = this.bcsEncDao.findUnitPrice(
						contractImg.getSeqNum(), bcsCustomsDeclaration, false);
				Double unitPrice = (Double) priceList.get(0);
				// 单价
				commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
						unitPrice, co.getCustomPriceNum()));
				// 总金额
				commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
						commInfo.getCommAmount() * commInfo.getCommUnitPrice(),
						co.getCustomTotalNum()));
			} else {
				// System.out.println("单价来源于申请单="
				// + t.getImpExpCommodityInfo().getUnitPrice());
				// 单价
				double unitPrive = t.getImpExpCommodityInfo().getUnitPrice();
				commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
						unitPrive, co.getCustomPriceNum()));
				// 总金额
				commInfo.setCommTotalPrice(CommonUtils
						.getDoubleByDigit(t.getImpExpCommodityInfo()
								.getAmountPrice() == null ? 0.0 : t
								.getImpExpCommodityInfo().getAmountPrice(), co
								.getCustomTotalNum()));
			}

			// 加工费总价
			commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(t
					.getImpExpCommodityInfo().getWorkUsd() == null ? 0.0 : t
					.getImpExpCommodityInfo().getWorkUsd(), co
					.getCustomTotalNum()));

			/**
			 * 申报单位净重 = pk净重
			 */
			commInfo.setNetWeight(CommonUtils.getDoubleByDigit((t
					.getImpExpCommodityInfo().getNetWeight() == null ? 0.0 : t
					.getImpExpCommodityInfo().getNetWeight()), 4));
			/**
			 * 申报单位毛重 = pk毛重
			 */
			commInfo.setGrossWeight(CommonUtils.getDoubleByDigit((t
					.getImpExpCommodityInfo().getGrossWeight() == null ? 0.0
					: t.getImpExpCommodityInfo().getGrossWeight()), 4));

			if (contractImg.getComplex().getFirstUnit() != null) {
				// 当“计量单位”=“第一法定数量” 第一法定数量=数量
				String unitName = contractImg.getUnit() == null ? ""
						: contractImg.getUnit().getName();
				String firstUnitName = contractImg.getComplex().getFirstUnit()
						.getName();
				if (unitName.equals(firstUnitName)) {// 条件1
					commInfo.setFirstAmount(commInfo.getCommAmount());
				} else if ("千克".equals(firstUnitName)) {// 条件2
					commInfo.setFirstAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap().get(unitName + "+" + firstUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + firstUnitName).toString());
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(amount
							* CommonUtils.getDoubleExceptNull(unitRate),
							co.getCustomAmountNum()));
				} else {
					double unitGene = contractImg.getLegalUnitGene() == null ? 0.0
							: contractImg.getLegalUnitGene();
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(amount
							* unitGene, co.getCustomAmountNum()));
				}
			}

			if (contractImg.getComplex().getSecondUnit() != null) {
				// 当“计量单位”=“第二法定数量” 第二法定数量=数量
				String unitName = contractImg.getUnit() == null ? ""
						: contractImg.getUnit().getName();
				String secondUnitName = contractImg.getComplex()
						.getSecondUnit().getName();
				if (unitName.equals(secondUnitName)) {
					commInfo.setSecondAmount(commInfo.getCommAmount());
				} else if ("千克".equals(secondUnitName)) {
					commInfo.setSecondAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap()
						.get(unitName + "+" + secondUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + secondUnitName).toString());
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							amount * CommonUtils.getDoubleExceptNull(unitRate),
							co.getCustomAmountNum()));
				} else {
					double unit2Gene = contractImg.getLegalUnit2Gene() == null ? 0.0
							: contractImg.getLegalUnit2Gene();
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							amount * unit2Gene, co.getCustomAmountNum()));
				}
			}

			/**
			 * 件数
			 */
			commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? 0
					: t.getImpExpCommodityInfo().getPiece().intValue());
			commInfo.setBoxNo(t.getImpExpCommodityInfo().getBoxNo());// 箱号

		} else {// 加到原有报关单

			double amount = 0.0;
			double amountPrice = 0.0;
			amount = t.getImpExpCommodityInfo().getQuantity() == null ? 0.0 : t
					.getImpExpCommodityInfo().getQuantity();// 保留三位小数);
			/**
			 * 数量
			 */
			commInfo.setCommAmount(CommonUtils.getDoubleByDigit(
					commInfo.getCommAmount() + amount, co.getCustomAmountNum()));// 保留三位小数
			/**
			 * 总额
			 */

			amountPrice = (t.getImpExpCommodityInfo().getAmountPrice() == null ? 0.0
					: t.getImpExpCommodityInfo().getAmountPrice());
			commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(amountPrice
					+ commInfo.getCommTotalPrice(), co.getCustomTotalNum()));

			/**
			 * 加工费总价
			 */
			commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					t.getImpExpCommodityInfo().getWorkUsd() == null ? commInfo
							.getWorkUsd() : commInfo.getWorkUsd()
							+ t.getImpExpCommodityInfo().getWorkUsd(), co
							.getCustomTotalNum()));
			/**
			 * 单价
			 */
			if (!commInfo.getCommAmount().equals(0.0)) {
				commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
						commInfo.getCommTotalPrice() / commInfo.getCommAmount(),
						co.getCustomPriceNum()));
			}

			/**
			 * 申报单位净重 = pk净重
			 */
			commInfo.setNetWeight(CommonUtils.getDoubleByDigit(
					(t.getImpExpCommodityInfo().getNetWeight() == null ? commInfo
							.getNetWeight() : commInfo.getNetWeight()
							+ t.getImpExpCommodityInfo().getNetWeight()), 4));
			/**
			 * 申报单位毛重 = pk毛重
			 */
			commInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
					(t.getImpExpCommodityInfo().getGrossWeight() == null ? commInfo
							.getGrossWeight() : commInfo.getGrossWeight()
							+ t.getImpExpCommodityInfo().getGrossWeight()), 4));

			if (contractImg.getComplex().getFirstUnit() != null) {
				// 当“计量单位”=“第一法定数量” 第一法定数量=数量
				String unitName = contractImg.getUnit() == null ? ""
						: contractImg.getUnit().getName();
				String firstUnitName = contractImg.getComplex().getFirstUnit()
						.getName();
				if (unitName.equals(firstUnitName)) {// 条件1
					commInfo.setFirstAmount(commInfo.getCommAmount());
				} else if ("千克".equals(firstUnitName)) {// 条件2
					commInfo.setFirstAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap().get(unitName + "+" + firstUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + firstUnitName).toString());
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(
							(amount * CommonUtils.getDoubleExceptNull(unitRate))
									+ commInfo.getFirstAmount(),
							co.getCustomAmountNum()));
				} else {
					double unitGene = contractImg.getLegalUnitGene() == null ? 0.0
							: contractImg.getLegalUnitGene();
					commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(
							(amount * unitGene) + commInfo.getFirstAmount(),
							co.getCustomAmountNum()));
				}
			}

			if (contractImg.getComplex().getSecondUnit() != null) {
				// 当“计量单位”=“第二法定数量” 第二法定数量=数量
				String unitName = contractImg.getUnit() == null ? ""
						: contractImg.getUnit().getName();
				String secondUnitName = contractImg.getComplex()
						.getSecondUnit().getName();
				if (unitName.equals(secondUnitName)) {
					commInfo.setSecondAmount(commInfo.getCommAmount());
				} else if ("千克".equals(secondUnitName)) {
					commInfo.setSecondAmount(commInfo.getNetWeight());
				} else if (getUnitRateMap()
						.get(unitName + "+" + secondUnitName) != null) {// 条件3
					Double unitRate = Double.parseDouble(getUnitRateMap().get(
							unitName + "+" + secondUnitName).toString());
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							(amount * CommonUtils.getDoubleExceptNull(unitRate))
									+ commInfo.getSecondAmount(),
							co.getCustomAmountNum()));
				} else {
					double unit2Gene = contractImg.getLegalUnit2Gene() == null ? 0.0
							: contractImg.getLegalUnit2Gene();
					commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							(amount * unit2Gene) + commInfo.getSecondAmount(),
							co.getCustomAmountNum()));
				}
			}
			/**
			 * 件数
			 */
			commInfo.setPieces(t.getImpExpCommodityInfo().getPiece() == null ? commInfo
					.getPieces() : commInfo.getPieces()
					+ t.getImpExpCommodityInfo().getPiece().intValue());
			String boxNo = commInfo.getBoxNo();// 箱号
			String newBoxNo = "";
			if (boxNo != null && !"".equals(boxNo)) {
				newBoxNo = getNotExistBoxNo(boxNo, t.getImpExpCommodityInfo()
						.getBoxNo());
			} else {
				newBoxNo = t.getImpExpCommodityInfo().getBoxNo();
			}
			commInfo.setBoxNo(newBoxNo);
		}
		/**
		 * 流水号在保存时新增
		 * 
		 */
		// bcsEncLogic.saveCustomsDeclarationCommInfo(commInfo);
		return commInfo;
	}

	private boolean isCommInfoEqual(BcsCustomsDeclarationCommInfo info,
			TempBcsImpExpCommodityInfo t) {
		ImpExpCommodityInfo imp = t.getImpExpCommodityInfo();
		ContractImg contractImg = t.getContractImg();
		String key = (info.getComplex() == null ? "" : info.getComplex()
				.getCode())
				+ "/"
				+ info.getCommName()
				+ "/"
				+ info.getCommSpec()
				+ "/"
				+ (info.getUnit() == null ? "" : info.getUnit().getName())
				+ "/"
				+ (info.getCountry() == null ? "" : info.getCountry().getName());
		if (key.equals((contractImg.getComplex() == null ? "" : contractImg
				.getComplex().getCode())
				+ "/"
				+ contractImg.getName()
				+ "/"
				+ contractImg.getSpec()
				+ "/"
				+ (contractImg.getUnit() == null ? "" : contractImg.getUnit()
						.getName())
				+ "/"
				+ (imp.getCountry() == null ? "" : imp.getCountry().getName())))
			return true;
		else
			return false;
	}

	private boolean isCommInfoEqualExg(BcsCustomsDeclarationCommInfo info,
			TempBcsImpExpCommodityInfo t) {
		ImpExpCommodityInfo imp = t.getImpExpCommodityInfo();
		ContractExg contractExg = t.getContractExg();
		String key = (info.getComplex() == null ? "" : info.getComplex()
				.getCode())
				+ "/"
				+ info.getCommName()
				+ "/"
				+ info.getCommSpec()
				+ "/"
				+ (info.getUnit() == null ? "" : info.getUnit().getName())
				+ "/"
				+ (info.getCountry() == null ? "" : info.getCountry().getName());
		if (key.equals((contractExg.getComplex() == null ? "" : contractExg
				.getComplex().getCode())
				+ "/"
				+ contractExg.getName()
				+ "/"
				+ contractExg.getSpec()
				+ "/"
				+ (contractExg.getUnit() == null ? "" : contractExg.getUnit()
						.getName())
				+ "/"
				+ (imp.getCountry() == null ? "" : imp.getCountry().getName())))
			return true;
		else
			return false;
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	public List findTempImpExpRequestBillByImpExpTypeToATC(List parentList) {
		List list = this.bcsEncDao.findImpExpCommodityInfoByParent(parentList);
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			// ImpExpRequestBill obj = (ImpExpRequestBill) list.get(i);
			TempBcsImpExpCommodityInfo temp = new TempBcsImpExpCommodityInfo();
			temp.setImpExpCommodityInfo((ImpExpCommodityInfo) list.get(i));
			// temp.setImpExpRequestBill(obj);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 检查是否有归并
	 * 
	 * @param contract
	 * @param commInfoList
	 * @param isProduct
	 * @param tradeCode
	 * @param impExpType
	 * @return
	 */
	public List checkExistTempBcsImpExpCommodityInfoBcsTenInnerMergeByParent(
			Contract contract, List commInfoList) {
		String emsNo = contract.getEmsNo();// 手册号
		boolean isEms = contract.getIsContractEms();// 是否是电子化手册
		String corrNo = contract.getCorrEmsNo();// 备案资料库编号
		// System.out.println(" emsNo==" + emsNo + "==isEms=" + isEms
		// + "==corrNo==" + corrNo);
		List newList = new ArrayList();
		List list = bcsEncDao.findImpExpCommodityInfoByCommInfo(commInfoList);

		// 缓存备案资料库成品表 key=归并序号 value=备案序号
		Map<Integer, Integer> mapExg = new HashMap<Integer, Integer>();
		initMapExg(mapExg, corrNo);

		// 缓存备案资料库料件表 key=归并序号 value=备案序号
		Map<Integer, Integer> mapImg = new HashMap<Integer, Integer>();
		initMapImg(mapImg, corrNo);

		// 存放所有的单位折算
		for (int i = 0; i < list.size(); i++) {
			TempBcsImpExpCommodityInfo temp = new TempBcsImpExpCommodityInfo();

			temp.setImpExpCommodityInfo((ImpExpCommodityInfo) list.get(i));

			temp.setIsSelected(new Boolean(false));
			BcsTenInnerMerge bcsTenInnerMerge = this.bcsInnerMergeDao
					.findBcsTenInnerMergeInMerge(temp.getImpExpCommodityInfo()
							.getMateriel().getId());
			temp.setSeqNo(bcsTenInnerMerge == null ? null : bcsTenInnerMerge
					.getSeqNum());
			if (bcsTenInnerMerge != null) {
				// temp.getImpExpCommodityInfo().setAfterName(bcsTenInnerMerge)
				String coiProperty = temp.getImpExpCommodityInfo()
						.getMateriel().getScmCoi().getCoiProperty();
				if (MaterielType.FINISHED_PRODUCT.equals(coiProperty)
						|| MaterielType.SEMI_FINISHED_PRODUCT
								.equals(coiProperty)) {
					if (isEms) {// 是纸质手册电子化

						Integer creno = (Integer) mapExg.get(bcsTenInnerMerge
								.getSeqNum());
						// 手册抓成品
						if (creno != null) {
							List lsContractExg = this.bcsContractDao
									.findContractExgByCredenceNo(emsNo,
											creno.toString());
							if (lsContractExg.size() > 0) {
								temp.setContractExg((ContractExg) lsContractExg
										.get(0));
							}
						}

					} else {
						List lsContractExg = this.bcsContractDao
								.findContractExgByCredenceNo(emsNo,
										bcsTenInnerMerge.getSeqNum().toString());
						if (lsContractExg.size() > 0) {
							temp.setContractExg((ContractExg) lsContractExg
									.get(0));

						}
					}

				} else if (MaterielType.MATERIEL.equals(coiProperty)) {
					if (isEms) {

						Integer creno = (Integer) mapImg.get(bcsTenInnerMerge
								.getSeqNum());
						// 手册抓料件
						if (creno != null) {
							List lsContractImg = this.bcsContractDao
									.findContractImgByCredenceNo(emsNo,
											creno.toString());
							if (lsContractImg.size() > 0) {
								temp.setContractImg((ContractImg) lsContractImg
										.get(0));
							}
						}

					} else {
						List lsContractImg = this.bcsContractDao
								.findContractImgByCredenceNo(emsNo,
										bcsTenInnerMerge.getSeqNum().toString());
						if (lsContractImg.size() > 0) {
							temp.setContractImg((ContractImg) lsContractImg
									.get(0));
						}
					}

				}
			}
			newList.add(temp);
		}

		return newList;
	}

	/**
	 * 初始化备案资料库成品表
	 * 
	 * @param mapExg
	 * @param corrNo
	 */
	private void initMapExg(Map mapExg, String corrNo) {
		// 备案资料库抓成品
		List rlist = this.bcsContractDao.findBcsDictPorExg(corrNo);
		for (int j = 0; j < rlist.size(); j++) {
			BcsDictPorExg exg = (BcsDictPorExg) rlist.get(j);
			if (exg.getSeqNum() == null || exg.getInnerMergeSeqNum() == null) {
				continue;
			}
			mapExg.put(exg.getInnerMergeSeqNum(), exg.getSeqNum());
		}
	}

	/**
	 * 初始化备案资料库料件表
	 * 
	 * @param mapImg
	 * @param corrNo
	 */
	public void initMapImg(Map mapImg, String corrNo) {
		// 备案资料库抓料件
		List rlist = this.bcsContractDao.findBcsDictPorImg(corrNo);
		for (int j = 0; j < rlist.size(); j++) {
			BcsDictPorImg img = (BcsDictPorImg) rlist.get(j);
			if (img.getSeqNum() == null || img.getInnerMergeSeqNum() == null) {
				continue;
			}
			mapImg.put(img.getInnerMergeSeqNum(), img.getSeqNum());
		}
	}

	/**
	 * 检查临时的进出口申请单表体资料
	 * 
	 * @param request
	 * @param contract
	 * @param commInfoList
	 * @return
	 */
	public List checkTempBcsImpExpCommodityInfo(Contract contract,
			List commInfoList) {

		List returnList = new ArrayList();

		List<TempBcsImpExpCommodityInfo> listTemp = new ArrayList<TempBcsImpExpCommodityInfo>();

		boolean isProduct = false;// true为成品

		if (commInfoList != null && commInfoList.size() > 0) {

			// 判断是不是成品
			isProduct = isProduct((TempBcsImpExpCommodityInfo) commInfoList
					.get(0));

		}

		// 保存报关数据
		Map<String, List> map = new HashMap<String, List>();

		initMap(contract, commInfoList, map, isProduct);

		String materielPtNo = "";

		for (int i = 0; i < commInfoList.size(); i++) {

			TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) commInfoList
					.get(i);

			String materielId = temp.getImpExpCommodityInfo().getMateriel()
					.getId();

			if (map.get(materielId) == null) {

				materielPtNo += temp.getImpExpCommodityInfo().getMateriel()
						.getPtNo()
						+ "\n";

				continue;

			} else {

				List list = map.get(materielId);

				for (int j = 0; j < list.size(); j++) {

					TempBcsImpExpCommodityInfo newTemp = new TempBcsImpExpCommodityInfo();

					try {

						// 为保全归并前 数据不被修改
						PropertyUtils.copyProperties(newTemp, temp);

						ImpExpCommodityInfo impExpCommodityInfo = new ImpExpCommodityInfo();

						PropertyUtils.copyProperties(impExpCommodityInfo,
								temp.getImpExpCommodityInfo());

						newTemp.setImpExpCommodityInfo(impExpCommodityInfo);

					} catch (Exception e) {
						e.printStackTrace();
					}

					Object[] obj = (Object[]) list.get(j);

					Integer seqNum = Integer.parseInt(obj[0].toString());// 归并序号

					newTemp.setSeqNo(seqNum);

					if (isProduct) {

						newTemp.setContractExg((ContractExg) obj[2]);// 合同成品

					} else {

						newTemp.setContractImg((ContractImg) obj[2]);// 合同料件

					}

					newTemp.setIsSelected(false);

					listTemp.add(newTemp);
				}
			}
		}

		returnList.add(materielPtNo);

		returnList.add(listTemp);

		return returnList;
	}

	/**
	 * 将内部归并对应表，合同备案成品资料或存放合同备案料件资料放到map中
	 * 
	 * @param list
	 * @param map
	 */
	public void initMap(Contract contract, List commInfoList,
			Map<String, List> map, boolean isProduct) {

		List materielIds = getMaterielIds(commInfoList);// 获取物料id

		String emsNo = contract.getEmsNo();// 帐册编号

		// 查询BCS内部归并对应表，存放合同备案成品资料或存放合同备案料件资料 传物料id,帐册编号,是否是成品
		List list = this.bcsContractDao.findBcsInnerMergeAndContractExgOrImg(
				materielIds, emsNo, isProduct);

		for (int i = 0; i < list.size(); i++) {

			Object[] obj = (Object[]) list.get(i);

			if (obj.length > 2) {

				// 物料id
				String materielId = obj[1] == null ? "" : obj[1].toString();

				if (map.get(materielId) == null) {

					List ls = new ArrayList();

					ls.add(obj);

					map.put(materielId, ls);

				} else {

					map.get(materielId).add(obj);
				}
			}

		}
	}

	/**
	 * 获取物料id
	 * 
	 * @param list
	 * @return
	 */
	public List<String> getMaterielIds(List<TempBcsImpExpCommodityInfo> list) {
		List<String> returnList = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TempBcsImpExpCommodityInfo temp = list.get(i);
			Materiel materiel = temp.getImpExpCommodityInfo().getMateriel();
			if (map.get(materiel.getId()) == null) {// 去掉重复的物料id
				map.put(materiel.getId(), materiel.getId());
				returnList.add(materiel.getId());
			}
		}
		return returnList;
	}

	/**
	 * 判断是不是成品
	 * 
	 * @param temp
	 * @return
	 */
	public boolean isProduct(TempBcsImpExpCommodityInfo temp) {
		String coiProperty = temp.getImpExpCommodityInfo().getMateriel()
				.getScmCoi().getCoiProperty();

		if (MaterielType.FINISHED_PRODUCT.equals(coiProperty)
				|| MaterielType.SEMI_FINISHED_PRODUCT.equals(coiProperty)) {
			return true;
		}
		return false;
	}

	/**
	 * 获得进出口申请单商品信息来自父对象
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param parentList
	 *            临时的申请单表头
	 * @return List 是TempBcsImpExpCommodityInfo型，临时的进出口申请单表体资料
	 */
	public List findTempBcsImpExpCommodityInfoByParent(Contract contract,
			List commInfoList, boolean isProduct, String tradeCode,
			Integer impExpType) {
		/**
		 * 把所有相同的备案商品编码 数量等其它项整合 成一条
		 */
		// List<TempBcsImpExpCommodityInfo> tempBcsImpExgList = this
		// .mergerImpExpCommodityInfo(commInfoList, isProduct);

		List<TempBcsImpExpCommodityInfo> tempBcsImpExgList = this
				.mergerImpExpCommodityInfos(commInfoList, isProduct);

		/**
		 * 计算合同当前余量
		 */
		List returnList = new ArrayList();

		for (int i = 0; i < tempBcsImpExgList.size(); i++) {

			TempBcsImpExpCommodityInfo t = (TempBcsImpExpCommodityInfo) tempBcsImpExgList
					.get(i);

			BcsContractExeInfo bcsContractExeInfo = null;
			// System.out.println("==isProduect=="+isProduct+"=="+impExpType+" ="+t
			// .getContractImg().getSeqNum()+" contract="+contract.getEmsNo());

			if (isProduct) {

				bcsContractExeInfo = bcsEncLogic.findBcsContractExeInfo(false,
						impExpType, tradeCode, contract, t.getContractExg()
								.getSeqNum(), null);

			} else {

				bcsContractExeInfo = bcsEncLogic.findBcsContractExeInfo(true,
						impExpType, tradeCode, contract, t.getContractImg()
								.getSeqNum(), null);

			}

			t.setConverCount(bcsContractExeInfo.getCurrentRemain());

			if (t.getImpExpCommodityInfo().getQuantity().doubleValue() > t
					.getConverCount().doubleValue()) {
				t.setIsOutAmount(true);
				t.setIsSelected(false);
			} else {
				t.setIsOutAmount(false);
				t.setIsSelected(true);
			}
			returnList.add(t);
		}

		return returnList;
	}

	/**
	 * 获取bcsEncDao
	 * 
	 * @return bcsEncDao
	 */
	public ContractExeDao getBcsEncDao() {
		return bcsEncDao;
	}

	/**
	 * 设置bcsEncDao
	 * 
	 * @param bcsEncDao
	 */
	public void setBcsEncDao(ContractExeDao bcsEncDao) {
		this.bcsEncDao = bcsEncDao;
	}

	/**
	 * 获取bcsEncLogic
	 * 
	 * @return bcsEncLogic
	 */
	public ContractExeLogic getBcsEncLogic() {
		return bcsEncLogic;
	}

	/**
	 * 设置bcsEncLogic
	 * 
	 * @param bcsEncLogic
	 */
	public void setBcsEncLogic(ContractExeLogic bcsEncLogic) {
		this.bcsEncLogic = bcsEncLogic;
	}

	/**
	 * 获取bcsImpExpRequestDao
	 * 
	 * @return List 是型，
	 */
	public BcsImpExpRequestDao getBcsImpExpRequestDao() {
		return bcsImpExpRequestDao;
	}

	/**
	 * 设置bcsImpExpRequestDao
	 * 
	 * @param bcsImpExpRequestDao
	 */
	public void setBcsImpExpRequestDao(BcsImpExpRequestDao bcsImpExpRequestDao) {
		this.bcsImpExpRequestDao = bcsImpExpRequestDao;
	}

	/**
	 * 根据进出口标志，查找合同备案料件资料
	 * 
	 * @param isLj
	 *            进出口标志
	 * @return
	 */
	public List findTempImpExpGoodsBill(Boolean isLj) {
		List newList = new ArrayList();
		List ls = bcsImpExpRequestDao.findImpExpGoodsBill(isLj);
		if (isLj) {// 进口
			for (int i = 0; i < ls.size(); i++) {
				Object[] objs = (Object[]) ls.get(i);
				String customsNo = (String) objs[0];// 报关单号
				String contractNo = (String) objs[1];// 合同号
				String krNo = (String) objs[2]; // kr#
				String catNo = (String) objs[3];// 车牌号
				TempImpExpGoodsBill temp = new TempImpExpGoodsBill();
				temp.setCustomsNo(customsNo);
				temp.setContractNo(contractNo);
				temp.setKr(krNo);
				temp.setCatNo(catNo);
				temp.setIsSelected(new Boolean(false));
				newList.add(temp);
			}
		} else {// 出口
			for (int i = 0; i < ls.size(); i++) {
				Object[] objs = (Object[]) ls.get(i);

				String catNo = (String) objs[0]; // 提运单号/大陆车牌
				String contractNo = (String) objs[1]; // 备案号/手册号
				String wrapType = (String) objs[2];// wrapType
				String countryOfLoadingOrUnloading = (String) objs[3];// countryOfLoadingOrUnloading
				String tradeMode = (String) objs[4];// tradeMode
				String memo1 = (String) objs[5];// memo1
				String memo2 = (String) objs[6];// memo2
				String customs = (String) objs[7];// customs
				String portLoad = (String) objs[8];// portLoad
				String authorizeFile = (String) objs[9];// authorizeFile
				String containerNum = (String) objs[10];// containerNum
				String conveyance = (String) objs[11];// conveyance
				String transferMode = (String) objs[12];// transferMode
				String domeConveyance = (String) objs[13];// domeConveyance
				String declarationCustoms = (String) objs[14];// declarationCustoms

				TempImpExpGoodsBill temp = new TempImpExpGoodsBill();
				temp.setCatNo(catNo);
				temp.setContractNo(contractNo);
				temp.setWrapType(wrapType);
				temp.setCountryOfLoadingOrUnloading(countryOfLoadingOrUnloading);
				temp.setTradeMode(tradeMode);
				temp.setMemo1(memo1);
				temp.setMemo2(memo2);
				temp.setCustoms(customs);
				temp.setPortLoad(portLoad);
				temp.setAuthorizeFile(authorizeFile);
				temp.setContainerNum(containerNum);
				temp.setConveyance(conveyance);
				temp.setTransferMode(transferMode);
				temp.setDomeConveyance(domeConveyance);
				temp.setDeclarationCustoms(declarationCustoms);
				temp.setIsSelected(new Boolean(false));
				newList.add(temp);
			}
		}
		return newList;
	}

	/**
	 * 统计同备案料件资料，料件或者成品
	 * 
	 * @param objhs
	 *            同备案料件资料
	 * @param contractNo
	 *            合同号
	 * @param seqNum
	 *            商品序号
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCode
	 *            贸易方式
	 * @return
	 */
	private ImpExpGoodsBill getContractImgNum(ImpExpGoodsBill objhs,
			String contractNo, Integer seqNum, Integer impExpType,
			String tradeCode) {
		ContractImg img = bcsContractDao.findContractImgByEmsNoSeqNum(
				contractNo, seqNum);
		/**
		 * 全部(已生效+未生效)料件进口数量
		 */
		double allDirectImport = this.contractCavDao.findCommInfoTotalAmount(
				seqNum, ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT, null,
				contractNo, null, null, -1);

		/**
		 * 全部(已生效+未生效)转厂进口数量
		 */
		double allTransferFactoryImport = this.contractCavDao
				.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, contractNo,
						null, null, -1);

		/**
		 * 全部(已生效+未生效)余料结转进口数
		 */
		double allRemainForwardImport = this.contractCavDao
				.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, contractNo,
						null, null, -1);

		/**
		 * 全部(已生效+未生效)退料退还出口数量(退料退还);
		 */
		double allExchangeExport = this.contractCavDao.findCommInfoTotalAmount(
				seqNum, ImpExpFlag.EXPORT, ImpExpType.BACK_MATERIEL_EXPORT,
				new String[] { "0300", "0700" }, contractNo, null, null, -1);
		Double heyNum = Double.valueOf(0);
		/**
		 * 全部(已生效+未生效)退料退还出口数量(退料复出);
		 */
		double allBackExportNotImport = this.contractCavDao
				.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0664",
								"0265" }, contractNo, null, null, -1);
		/**
		 * 全部(已生效+未生效)余料结转出口数
		 */
		double allRemainForwardExport = this.contractCavDao
				.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, contractNo,
						null, null, -1);
		/**
		 * 全部(已生效+未生效)退料退还进口数量(退料退还);
		 */
		double allExchangeImport = this.contractCavDao.findCommInfoTotalAmount(
				seqNum, ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
				new String[] { "0300", "0700" }, contractNo, null, null, -1);
		/**
		 * 全部(已生效+未生效)料件内销数量
		 */
		double alllefovMateriaImport = contractCavDao.findCommInfoTotalAmount(
				seqNum, ImpExpFlag.IMPORT, ImpExpType.MATERIAL_DOMESTIC_SALES,
				new String[] { "0644", "0245" }, contractNo, null, null, -1);
		if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// 可退料出口的数量
			heyNum = (allDirectImport + allTransferFactoryImport
					+ allRemainForwardImport - allExchangeExport
					- allBackExportNotImport - allRemainForwardExport);
		} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {// 可余料转出的数量
			heyNum = (allDirectImport + allTransferFactoryImport
					+ allRemainForwardImport - allExchangeExport
					- allBackExportNotImport - allRemainForwardExport - this
					.getProductUsedAmount(img));
		} else if (impExpType == ImpExpType.DIRECT_IMPORT
				&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {// 可退料退还进口数量
			heyNum = (allExchangeExport - allExchangeImport);
		} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES
				&& (tradeCode.equals("0644") || tradeCode.equals("0245"))) {// 海关批准内销料件内销
			heyNum = (allDirectImport + allTransferFactoryImport
					+ allRemainForwardImport - allExchangeExport
					- allBackExportNotImport - alllefovMateriaImport - this
					.getProductUsedAmount(img));

		} else {// 可直接进口/可转厂进口数量/可余料结转转入数量
			heyNum = (img.getAmount() == null ? 0.0 : img.getAmount())
					- (allDirectImport + allTransferFactoryImport
							+ allRemainForwardImport - allExchangeExport);
		}

		objhs.setImg(img);
		objhs.setContractAmount(img.getAmount() == null ? 0.0 : img.getAmount());// 合同定量;

		objhs.setContractRemain(heyNum);

		return objhs;
	}

	/**
	 * 获取成品耗用量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param seqNum
	 *            商品序号
	 */
	private double getProductUsedAmount(ContractImg img) {
		double totalUsedAmount = 0.0;
		List lsBom = this.bcsContractDao.findContractBomByImgSeqNum(img);
		for (int i = 0; i < lsBom.size(); i++) {
			ContractBom bom = (ContractBom) lsBom.get(i);
			ContractExg exg = bom.getContractExg();
			Integer seqNum = exg.getSeqNum();
			Contract contract = exg.getContract();
			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null,
							contract.getEmsNo(), null, null, -1);

			double totalExgAmount = allDirectExport + allTransferFactoryExport
					- allBackFactoryRework + allReworkExport;
			// totalUsedAmount += (totalExgAmount * CommonUtils
			// .getDoubleExceptNull(bom.getUnitDosage()));
			totalUsedAmount += (totalExgAmount * CommonUtils
					.getDoubleExceptNull(CommonUtils.getDoubleExceptNull(bom
							.getUnitWaste())
							/ (1 - CommonUtils.getDoubleExceptNull(bom
									.getWaste()) / 100.0)));
		}
		return totalUsedAmount;
	}

	/**
	 * 统计同备案料件资料，料件或者成品
	 * 
	 * @param objhs
	 *            同备案料件资料
	 * @param contractNo
	 *            合同号
	 * @param seqNum
	 *            商品序号
	 * @param impExpType
	 *            进出口类型
	 * @return
	 */
	private ImpExpGoodsBill getContractExgNum(ImpExpGoodsBill objhs,
			String contractNo, Integer seqNum, Integer impExpType) {
		List alllist = new ArrayList();
		ContractExg exg = bcsContractDao.findContractExgByEmsNoSeqNum(
				contractNo, seqNum);

		/**
		 * 全部(已生效+未生效)成品出口数量
		 */
		double allDirectExport = this.contractCavDao.findCommInfoTotalAmount(
				seqNum, ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT, null,
				contractNo, null, null, -1);
		/**
		 * 全部(已生效+未生效)转厂出口数量
		 */
		double allTransferFactoryExport = this.contractCavDao
				.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, contractNo,
						null, null, -1);
		/**
		 * 全部(已生效+未生效)成品退厂返工数量
		 */
		double allBackFactoryRework = this.contractCavDao
				.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, contractNo, null,
						null, -1);
		/**
		 * 全部(已生效+未生效)成品返工复出数量
		 */
		double allReworkExport = this.contractCavDao.findCommInfoTotalAmount(
				seqNum, ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT, null,
				contractNo, null, null, -1);
		Double heyNum = Double.valueOf(0);

		objhs.setExg(exg);
		objhs.setContractAmount(exg.getExportAmount() == null ? 0.0 : exg
				.getExportAmount());// 合同定量;

		/**
		 * 可退厂返工量
		 */
		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			heyNum = (allDirectExport + allTransferFactoryExport
					- allBackFactoryRework + allReworkExport);
		} else if (impExpType == ImpExpType.REWORK_EXPORT) {// 可返工复出量
			heyNum = (allBackFactoryRework - allReworkExport);
		} else {
			heyNum = (exg.getExportAmount() == null ? 0.0 : exg
					.getExportAmount()) // 当前余量
					- (allDirectExport + allTransferFactoryExport
							- allBackFactoryRework + allReworkExport);
		}
		objhs.setContractRemain(heyNum);
		return objhs;
	}

	/**
	 * 风岗嘉辉嘉安进出货单据转报关单
	 * 
	 * @param ls
	 *            商品LIST
	 * @param isLj
	 *            料件成品标志
	 * @return
	 */
	public String impExpGoodsTransCustoms(List ls, boolean isLj) {
		if (isLj) {
			return impExpGoodsTransCustomsForLj(ls);
		} else {
			return impExpGoodsTransCustomsForCp(ls);
		}
	}

	/**
	 * 风岗嘉辉嘉安进出货单据转报关单
	 * 
	 * @param ls
	 *            要转换的商品
	 * @return
	 */
	public String impExpGoodsTransCustomsForLj(List ls) {
		System.out.println(" -- 开始执行转换");
		String infostr = "";
		for (int i = 0; i < ls.size(); i++) {
			TempImpExpGoodsBill temp = (TempImpExpGoodsBill) ls.get(i);
			String customsNo = temp.getCustomsNo();// 报关单号
			String contractNo = temp.getContractNo();// 手册号
			String kr = temp.getKr(); // KR#
			String catNo = temp.getCatNo(); // 车牌号码
			System.out.println(" -- 开始检查合同是否存在");
			Contract conObj = bcsImpExpRequestDao
					.findContractByEmsNo(contractNo);
			if (conObj == null) {
				infostr = infostr + "转入失败：手册号：" + contractNo + " 不存在!" + "\n";
				continue;
			}
			System.out.println(" -- 结束检查合同是否存在");
			String sinfo = "";
			List list = this.bcsImpExpRequestDao
					.findImpExpGoodsBillByCustomsNoForImg(customsNo,
							contractNo, kr, catNo);
			System.out.println(" -- 汇总数量");
			Hashtable hs = new Hashtable();
			for (int j = 0; j < list.size(); j++) {
				ImpExpGoodsBill obj = (ImpExpGoodsBill) list.get(j);
				Integer key = obj.getSeqNum();
				Integer seqNum = Integer.valueOf(key.toString());// 合同序号
				ImpExpGoodsBill objhs = null;
				if (hs.get(key) == null) {
					objhs = new ImpExpGoodsBill();
					objhs.setSeqNum(obj.getSeqNum());
					objhs.setCountry(obj.getCountry());
					objhs.setNum(obj.getNum());// 当前要转的数量
					objhs = getContractImgNum(objhs, contractNo, seqNum,
							ImpExpType.DIRECT_IMPORT, "");
					hs.put(key, objhs);
				} else {
					objhs = (ImpExpGoodsBill) hs.get(key);
					objhs.setNum(objhs.getNum() + obj.getNum());
				}
			}
			System.out.println(" -- 检查合同明细是否超量");
			Enumeration e = hs.keys();
			System.out.println("11111111");
			while (e.hasMoreElements()) {
				System.out.println("2222");
				Object key = e.nextElement();
				ImpExpGoodsBill objhs = (ImpExpGoodsBill) hs.get(key);
				System.out.println("3333");
				double remainNum = objhs.getNum() - objhs.getContractRemain();
				System.out.println("4444");
				if (remainNum > 0) {
					System.out.println("5555");
					sinfo = sinfo + "转入失败：合同超量 报关单号：" + customsNo + " 序号："
							+ objhs.getSeqNum() + " 合同定量："
							+ objhs.getContractAmount() + " 合同余量："
							+ objhs.getContractRemain() + " 待转量:"
							+ objhs.getNum() + "\n";
				}
			}
			System.out.println("666:" + sinfo + "yyyy:" + infostr);
			if (!sinfo.equals("")) {
				System.out.println(" -- 合同超量");
				infostr = infostr + sinfo;
			}
			System.out.println("66777：" + sinfo);

			if (sinfo.equals("")) {
				System.out.println(" -- 开始生成报关单表头");

				BcsCustomsDeclaration bcsCh = new BcsCustomsDeclaration();
				bcsCh.setCompany(CommonUtils.getCompany());
				String strtype = null;

				System.out.println("888");
				bcsCh.setImpExpFlag(ImpExpFlag.IMPORT);
				bcsCh.setImpExpType(ImpExpType.DIRECT_IMPORT); // 直接进口
				strtype = "直接进口";
				System.out.println("9999");
				bcsCh.setEmsHeadH2k(contractNo);
				bcsCh.setKr(kr);
				bcsCh.setCustomsDeclarationCode(customsNo);
				System.out.println("000");
				bcsCh.setManufacturer(getBrief(((Company) CommonUtils
						.getCompany()).getCode()));
				bcsCh.setCreater(CommonUtils.getRequest().getUser());
				bcsCh.setImpExpDate(new Date());
				bcsCh.setDeclarationDate(new Date());
				bcsCh.setSerialNumber(this.bcsEncDao
						.getCustomsDeclarationSerialNumber(
								bcsCh.getImpExpFlag(), bcsCh.getEmsHeadH2k()));
				System.out.println("5555");
				bcsEncDao.saveCustomsDeclaration(bcsCh);
				System.out.println(" -- 开始生成报关单表体");
				Enumeration e1 = hs.keys();
				while (e1.hasMoreElements()) {
					Object key = e1.nextElement();
					ImpExpGoodsBill objhs = (ImpExpGoodsBill) hs.get(key);
					BcsCustomsDeclarationCommInfo commInfo = new BcsCustomsDeclarationCommInfo();
					commInfo.setBaseCustomsDeclaration(bcsCh);
					commInfo.setSerialNumber(this.bcsEncLogic
							.getCustomsDeclarationCommInfoSerialNumber(bcsCh));

					ContractImg img = objhs.getImg();
					commInfo.setCommSerialNo(img.getSeqNum());
					commInfo.setComplex(img.getComplex());
					commInfo.setCommName(img.getName());
					commInfo.setDetailNote(img.getDetailNote()); // 详细型号规格
					commInfo.setCommSpec(img.getSpec());
					commInfo.setUnit(img.getUnit());
					commInfo.setLegalUnit(img.getComplex().getFirstUnit());
					commInfo.setSecondLegalUnit(img.getComplex()
							.getSecondUnit());
					commInfo.setCommUnitPrice(img.getDeclarePrice());

					Country country = this.bcsImpExpRequestDao
							.findCountryByName(objhs.getCountry());
					commInfo.setCountry(country);
					commInfo.setCommAmount(objhs.getNum());
					commInfo.setCommTotalPrice((commInfo.getCommAmount() == null ? 0.0
							: commInfo.getCommAmount())
							* (commInfo.getCommUnitPrice() == null ? 0.0
									: commInfo.getCommUnitPrice()));
					commInfo = getFistSecondUnitAmount(commInfo);// 通用方法得到第一，二法定数量
					commInfo.setCompany(CommonUtils.getCompany());
					bcsEncDao.saveCustomsDeclarationCommInfo(commInfo);
				}
				System.out.println(" -- 开始更新已转报关单标记");
				for (int j = 0; j < list.size(); j++) {
					ImpExpGoodsBill obj = (ImpExpGoodsBill) list.get(j);
					obj.setIsTcustoms(new Boolean(true));
					obj.setSerialNumber(bcsCh.getSerialNumber());
					this.bcsImpExpRequestDao.saveImpExpGoodsBillList(obj);
				}
				System.out.println(" -- 更新标记完毕！");
				infostr = infostr + "转入成功：" + strtype + " 报关单号：" + customsNo
						+ " 手册号：" + contractNo + " 报关单流水号为："
						+ bcsCh.getSerialNumber() + "\n";
			}
		}
		return infostr;
	}

	/**
	 * 风岗嘉辉嘉安进出货单据转报关单
	 * 
	 * @param ls
	 *            要转换的商品
	 * @return
	 */
	public String impExpGoodsTransCustomsForCp(List ls) {
		System.out.println(" -- 开始执行转换");
		String infostr = "";
		for (int i = 0; i < ls.size(); i++) {
			TempImpExpGoodsBill temp = (TempImpExpGoodsBill) ls.get(i);
			String contractNo = temp.getContractNo();// 合同号
			String catNo = temp.getCatNo();
			String wrapType = temp.getWrapType();
			String tradeMode = temp.getTradeMode();// 贸易方式名称
			String countryOfLoadingOrUnloading = temp
					.getCountryOfLoadingOrUnloading();
			String memo1 = temp.getMemo1();
			String memo2 = temp.getMemo2();
			String customs = temp.getCustoms();
			String portLoad = temp.getPortLoad();
			String authorizeFile = temp.getAuthorizeFile();
			String containerNum = temp.getContainerNum();
			String conveyance = temp.getConveyance();
			String transferMode = temp.getTransferMode();
			String domeConveyance = temp.getDomeConveyance();
			String declarationCustoms = temp.getDeclarationCustoms();
			String transf = temp.getTransferMode();// 运输方式

			System.out.println(" -- 开始检查合同是否存在");
			Contract conObj = bcsImpExpRequestDao
					.findContractByimpContractNo(contractNo);// by 合同号
			if (conObj == null) {
				infostr = infostr + "转入失败：合同号：" + contractNo + " 不存在!" + "\n";
				continue;
			}
			String emsNo = conObj.getEmsNo();// 手册号
			System.out.println(" -- 结束检查合同是否存在");
			String sinfo = "";
			List list = this.bcsImpExpRequestDao
					.findImpExpGoodsBillByCustomsNoForExg(catNo, contractNo,
							countryOfLoadingOrUnloading, wrapType, tradeMode,
							memo1, memo2, customs, portLoad, authorizeFile,
							containerNum, conveyance, transferMode,
							domeConveyance, declarationCustoms);
			System.out.println(" -- 汇总数量");
			Hashtable hs = new Hashtable();
			for (int j = 0; j < list.size(); j++) {
				ImpExpGoodsBill obj = (ImpExpGoodsBill) list.get(j);
				Integer key = obj.getSeqNum();
				Integer seqNum = Integer.valueOf(key.toString());// 合同序号
				ImpExpGoodsBill objhs = null;
				if (hs.get(key) == null) {
					objhs = new ImpExpGoodsBill();
					objhs.setSeqNum(obj.getSeqNum());
					objhs.setCountry(obj.getCountry());
					objhs.setNum(obj.getNum());// 当前要转的数量

					objhs.setNetWeight(obj.getNetWeight());
					objhs.setGrossWeight(obj.getGrossWeight());
					objhs.setCommodityNum(obj.getCommodityNum());
					objhs.setTradeMode(obj.getTradeMode());

					Integer impExpType = ImpExpType.DIRECT_EXPORT;
					String tradeModeCode = gettradeModeCode(tradeMode);
					if (tradeModeCode != null && tradeModeCode.equals("0214")) {
						impExpType = ImpExpType.DIRECT_EXPORT; // 直接出口
					} else if (tradeModeCode != null
							&& tradeModeCode.equals("4400")) {
						impExpType = ImpExpType.BACK_FACTORY_REWORK; // 退厂返工
					} else if (tradeModeCode != null
							&& (tradeModeCode.equals("0265") || tradeModeCode
									.equals("0300"))) {
						impExpType = ImpExpType.BACK_MATERIEL_EXPORT; // 退料出口
					} else if (tradeModeCode != null
							&& tradeModeCode.equals("0255")) {
						impExpType = ImpExpType.TRANSFER_FACTORY_EXPORT; // 转厂出口
					} else if (tradeModeCode != null
							&& tradeModeCode.equals("0258")) {
						impExpType = ImpExpType.REMAIN_FORWARD_EXPORT; // 余料结转出口
					}
					if (tradeModeCode != null
							&& (tradeModeCode.equals("0265")
									|| tradeModeCode.equals("0300") || tradeModeCode
										.equals("0258"))) {// 料件
						objhs = getContractImgNum(objhs, emsNo, seqNum,
								impExpType, tradeModeCode);
						System.out.println("1111111111:" + key
								+ "    objhs.getimg:" + objhs.getImg());
					} else {
						System.out.println("2222222222:" + key
								+ "    objhs.getexg:" + objhs.getExg());
						objhs = getContractExgNum(objhs, emsNo, seqNum,
								impExpType);
					}
					hs.put(key, objhs);
				} else {
					objhs = (ImpExpGoodsBill) hs.get(key);
					objhs.setNum(forD(objhs.getNum()) + forD(obj.getNum()));
					objhs.setGrossWeight(forD(objhs.getGrossWeight())
							+ forD(obj.getGrossWeight()));
					objhs.setNetWeight(forD(objhs.getNetWeight())
							+ forD(obj.getNetWeight()));
					objhs.setCommodityNum((objhs.getCommodityNum() == null ? 0
							: objhs.getCommodityNum())
							+ (obj.getCommodityNum() == null ? 0 : obj
									.getCommodityNum()));
				}
			}
			System.out.println(" -- 检查合同明细是否超量");
			Enumeration e = hs.keys();
			System.out.println("11111111");
			Double totalgrossWeight = Double.valueOf(0);
			Double totalnetWeight = Double.valueOf(0);
			Integer totalcommodityNum = 0;

			while (e.hasMoreElements()) {
				System.out.println("2222");
				Object key = e.nextElement();
				ImpExpGoodsBill objhs = (ImpExpGoodsBill) hs.get(key);

				System.out.println("3333");
				double remainNum = objhs.getNum() - objhs.getContractRemain();
				totalgrossWeight += objhs.getGrossWeight();
				totalnetWeight += objhs.getNetWeight();
				totalcommodityNum += objhs.getCommodityNum();

				System.out.println("4444");
				if (remainNum > 0) {
					System.out.println("5555");
					sinfo = sinfo + "转入失败：合同超量 提运单号/大陆车牌：" + catNo + " 序号："
							+ objhs.getSeqNum() + " 合同定量："
							+ objhs.getContractAmount() + " 合同余量："
							+ objhs.getContractRemain() + " 待转量:"
							+ objhs.getNum() + "\n";
				}
			}
			System.out.println("666:" + sinfo + "yyyy:" + infostr);
			if (!sinfo.equals("")) {
				System.out.println(" -- 合同超量");
				infostr = infostr + sinfo;
			}
			System.out.println("66777：" + sinfo);

			if (sinfo.equals("")) {
				System.out.println(" -- 开始生成报关单表头");

				BcsCustomsDeclaration bcsCh = new BcsCustomsDeclaration();
				bcsCh.setCompany(CommonUtils.getCompany());
				String strtype = null;

				System.out.println("888");
				bcsCh.setImpExpFlag(ImpExpFlag.EXPORT);
				String tradeModeCode = gettradeModeCode(tradeMode);
				if (tradeModeCode != null && tradeModeCode.equals("0214")) {
					bcsCh.setImpExpType(ImpExpType.DIRECT_EXPORT); // 直接出口
					strtype = "直接出口";
				} else if (tradeModeCode != null
						&& tradeModeCode.equals("4400")) {
					bcsCh.setImpExpType(ImpExpType.BACK_FACTORY_REWORK); // 退厂返工
					strtype = "退厂返工";
				} else if (tradeModeCode != null
						&& (tradeModeCode.equals("0265") || tradeModeCode
								.equals("0300"))) {
					bcsCh.setImpExpType(ImpExpType.BACK_MATERIEL_EXPORT); // 退料出口
					strtype = "退料出口";
				} else if (tradeModeCode != null
						&& tradeModeCode.equals("0255")) {
					bcsCh.setImpExpType(ImpExpType.TRANSFER_FACTORY_EXPORT); // 转厂出口
					strtype = "转厂出口";
				} else if (tradeModeCode != null
						&& tradeModeCode.equals("0258")) {
					bcsCh.setImpExpType(ImpExpType.REMAIN_FORWARD_EXPORT); // 余料结转出口
					strtype = "余料结转出口";
				}

				System.out.println("9999");
				bcsCh.setEmsHeadH2k(emsNo);// 手册号

				Customs customs11 = this.bcsImpExpRequestDao
						.findCustomsByName(customs);
				bcsCh.setCustoms(customs11);// 出口口岸

				Transf transferMode11 = this.bcsImpExpRequestDao
						.findTransfByName(transferMode);
				bcsCh.setTransferMode(transferMode11);// 运输方式

				bcsCh.setConveyance(conveyance);// 运输工具名称
				bcsCh.setBillOfLading(catNo);// 提运单号

				Trade tradeMode11 = this.bcsImpExpRequestDao
						.findTradeByName(tradeMode);
				bcsCh.setTradeMode(tradeMode11);// 贸易方式

				Country country11 = this.bcsImpExpRequestDao
						.findCountryByName11(countryOfLoadingOrUnloading);
				bcsCh.setCountryOfLoadingOrUnloading(country11);// 运抵国

				PortLin portLoad11 = this.bcsImpExpRequestDao
						.findPortLinyName(portLoad);
				bcsCh.setPortOfLoadingOrUnloading(portLoad11);// 装货港口

				Customs declarationCustomsobj = this.bcsImpExpRequestDao
						.findCustomsByName(declarationCustoms);
				bcsCh.setDeclarationCustoms(declarationCustomsobj);// 报送海关

				ScmCoc scmcoc = this.bcsImpExpRequestDao
						.findScmCocByCode("00005");
				bcsCh.setCustomer(scmcoc);

				bcsCh.setAuthorizeFile(authorizeFile);// 批准证号

				Wrap wrapType11 = this.bcsImpExpRequestDao
						.findWrapByName(wrapType);
				bcsCh.setWrapType(wrapType11);// 包装种类

				bcsCh.setTradeCode(conObj.getTradeCode());
				bcsCh.setTradeName(conObj.getTradeName());
				bcsCh.setMachCode(conObj.getMachCode());
				bcsCh.setMachName(conObj.getMachName());
				bcsCh.setLevyKind(bcsImpExpRequestDao.findLevyKindByCode("502"));
				bcsCh.setCurrency(bcsImpExpRequestDao.findCurrByCode("110"));

				bcsCh.setCommodityNum(totalcommodityNum);// 件数
				bcsCh.setGrossWeight(totalgrossWeight);// 毛重
				bcsCh.setNetWeight(totalnetWeight);// 净重
				bcsCh.setContainerNum(containerNum);// 集装箱号码
				bcsCh.setMemos((memo1 == null ? "" : memo1) + "  "
						+ (memo2 == null ? "" : memo2));// 备注
				bcsCh.setDomesticConveyanceCode(domeConveyance);// 境内运输工具
				bcsCh.setOverseasConveyanceCode(domeConveyance);// 境外运输工具

				Transf transf11 = this.bcsImpExpRequestDao.findTransf(transf);
				bcsCh.setDomesticTransferMode(transf11);

				bcsCh.setCreateDate(new Date());

				System.out.println("000");
				bcsCh.setManufacturer(getBrief(((Company) CommonUtils
						.getCompany()).getCode()));
				bcsCh.setCreater(CommonUtils.getRequest().getUser());
				bcsCh.setImpExpDate(new Date());
				bcsCh.setDeclarationDate(new Date());
				bcsCh.setContract(contractNo);
				bcsCh.setSerialNumber(this.bcsEncDao
						.getCustomsDeclarationSerialNumber(
								bcsCh.getImpExpFlag(), bcsCh.getEmsHeadH2k()));
				System.out.println("5555");
				bcsEncDao.saveCustomsDeclaration(bcsCh);
				System.out.println(" -- 开始生成报关单表体");
				Enumeration e1 = hs.keys();
				while (e1.hasMoreElements()) {
					Object key = e1.nextElement();
					ImpExpGoodsBill objhs = (ImpExpGoodsBill) hs.get(key);
					BcsCustomsDeclarationCommInfo commInfo = new BcsCustomsDeclarationCommInfo();
					commInfo.setBaseCustomsDeclaration(bcsCh);
					commInfo.setSerialNumber(this.bcsEncLogic
							.getCustomsDeclarationCommInfoSerialNumber(bcsCh));
					tradeModeCode = gettradeModeCode(objhs.getTradeMode());
					System.out.println("wwwwwwwwwww:" + tradeModeCode
							+ "   key:" + key + "      img:" + objhs.getImg()
							+ "   exg:" + objhs.getExg());
					if (tradeModeCode != null
							&& (tradeModeCode.equals("0265")
									|| tradeModeCode.equals("0300") || tradeModeCode
										.equals("0258"))) {// 料件
						ContractImg img = objhs.getImg();
						commInfo.setCommSerialNo(img.getSeqNum());
						commInfo.setComplex(img.getComplex());
						commInfo.setCommName(img.getName());
						commInfo.setDetailNote(img.getDetailNote()); // 详细型号规格
						commInfo.setCommSpec(img.getSpec());
						commInfo.setUnit(img.getUnit());
						commInfo.setLegalUnit(img.getComplex().getFirstUnit());
						commInfo.setSecondLegalUnit(img.getComplex()
								.getSecondUnit());
						commInfo.setCommUnitPrice(img.getDeclarePrice());

						commInfo.setGrossWeight(objhs.getGrossWeight());
						commInfo.setNetWeight(objhs.getNetWeight());
						commInfo.setFirstAmount(objhs.getNetWeight());
						commInfo.setLevyMode(bcsImpExpRequestDao
								.findLevyModeByCode("3"));

					} else {
						ContractExg exg = objhs.getExg();
						commInfo.setCommSerialNo(exg.getSeqNum());
						commInfo.setComplex(exg.getComplex());
						commInfo.setCommName(exg.getName());
						commInfo.setCommSpec(exg.getSpec());
						commInfo.setUnit(exg.getUnit());
						commInfo.setLegalUnit(exg.getComplex().getFirstUnit());
						commInfo.setSecondLegalUnit(exg.getComplex()
								.getSecondUnit());
						commInfo.setCommUnitPrice(exg.getUnitPrice());

						commInfo.setGrossWeight(objhs.getGrossWeight());
						commInfo.setNetWeight(objhs.getNetWeight());
						commInfo.setFirstAmount(objhs.getNetWeight());
						commInfo.setLevyMode(bcsImpExpRequestDao
								.findLevyModeByCode("3"));
						commInfo.setPieces(objhs.getCommodityNum());// 件数
					}

					Country country22 = this.bcsImpExpRequestDao
							.findCountryByName11(objhs.getCountry());
					commInfo.setCountry(country22);// 目的国

					commInfo.setCommAmount(objhs.getNum());

					commInfo.setCommTotalPrice((commInfo.getCommAmount() == null ? 0.0
							: commInfo.getCommAmount())
							* (commInfo.getCommUnitPrice() == null ? 0.0
									: commInfo.getCommUnitPrice()));
					commInfo = getFistSecondUnitAmount(commInfo);// 通用方法得到第一，二法定数量
					commInfo.setCompany(CommonUtils.getCompany());
					bcsEncDao.saveCustomsDeclarationCommInfo(commInfo);
				}
				System.out.println(" -- 开始更新已转报关单标记");
				for (int j = 0; j < list.size(); j++) {
					ImpExpGoodsBill obj = (ImpExpGoodsBill) list.get(j);
					obj.setIsTcustoms(new Boolean(true));
					obj.setSerialNumber(bcsCh.getSerialNumber());
					this.bcsImpExpRequestDao.saveImpExpGoodsBillList(obj);
				}
				System.out.println(" -- 更新标记完毕！");
				infostr = infostr + "转入成功：" + strtype + " 提运单号/大陆车牌：" + catNo
						+ " 合同号：" + contractNo + " 报关单流水号为："
						+ bcsCh.getSerialNumber() + "\n";
			}
		}
		return infostr;
	}

	/**
	 * 根据贸易方式名称转换代码
	 * 
	 * @param name
	 *            贸易方式名称
	 * @return
	 */
	private String gettradeModeCode(String name) {
		if (name != null && name.equals("来料加工")) {
			return "0214";
		} else if (name != null && name.equals("来料成品退换")) {
			return "4400";
		} else if (name != null && name.equals("来料料件复出")) {
			return "0265";
		} else if (name != null && name.equals("来料料件退换")) {
			return "0300";
		} else if (name != null && name.equals("来料深加工")) {
			return "0255";
		} else if (name != null && name.equals("来料余料结转")) {
			return "0258";
		}
		return "";
	}

	/**
	 * 判断DOUBLE型是否为空，如果为空，则反回0.
	 * 
	 * @param d
	 *            要判断DOUBLE型 数据
	 */
	private Double forD(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 根据报关单商品查找第一，第二法定单位
	 * 
	 * @param commInfo
	 *            报关单商品
	 * @return
	 */
	private BcsCustomsDeclarationCommInfo getFistSecondUnitAmount(
			BcsCustomsDeclarationCommInfo commInfo) {

		// 申报数量
		Double declaraamount = commInfo.getCommAmount();
		// 申报单位
		String unit = commInfo.getUnit() == null ? "" : commInfo.getUnit()
				.getName();
		// 第一法定单位
		String firstunit = commInfo.getComplex().getFirstUnit() == null ? ""
				: commInfo.getComplex().getFirstUnit().getName();
		// 第二法定单位
		String secondunit = commInfo.getComplex().getSecondUnit() == null ? ""
				: commInfo.getComplex().getSecondUnit().getName();
		if (unit.equals(firstunit)) {
			commInfo.setFirstAmount(declaraamount);
		}
		if (unit.equals(secondunit)) {
			commInfo.setSecondAmount(declaraamount);
		}
		Double unitRate = bcsImpExpRequestDao.findUnitRate(unit, firstunit);
		if (unitRate != null) {
			commInfo.setFirstAmount(declaraamount * unitRate);
		}
		Double unitRate1 = bcsImpExpRequestDao.findUnitRate(unit, secondunit);
		if (unitRate1 != null) {
			commInfo.setSecondAmount(declaraamount * unitRate1);
		}
		return commInfo;
	}

	/**
	 * 获取合同核销DAO
	 * 
	 * @return
	 */
	public ContractCavDao getContractCavDao() {
		return contractCavDao;
	}

	/**
	 * 设置合同核销DAO
	 * 
	 * @return
	 */
	public void setContractCavDao(ContractCavDao contractCavDao) {
		this.contractCavDao = contractCavDao;
	}

	/**
	 * 申请单转报关单自动选合同，根据商品计算余量
	 * 
	 * @param list
	 *            要转的商品
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	public List getRContract(List<TempBcsImpExpCommodityInfo> list,
			int impExpFlag) {
		List retList = new ArrayList();
		Contract contract = null;
		StringBuffer restr = new StringBuffer("以下是系统自动选合同的经过:\n");
		try {
			List tlist = this.bcsImpExpRequestDao.findExeContrac();// 查找正在执行的合同，按核销日期排序
			if (tlist.size() == 0) {
				restr.append("没有正在执行的合同，程序将无法继续下去！\n");
				retList.add(contract);
				retList.add(restr);
				return retList;
			}
			for (int j = 0; j < tlist.size(); j++) {
				Contract ct = (Contract) tlist.get(j);
				boolean isOk = false;
				Map seqSer = new HashMap();
				if (ct.getIsContractEms() != null && ct.getIsContractEms()) {// 如果是电子化手册
					String corr = ct.getCorrEmsNo();
					List blist = bcsImpExpRequestDao.findBcsDictPorImgExgByNo(
							corr, impExpFlag);// 根据合同，查找备案粢资料库备案内容
					Map map = new HashMap();
					for (int i = 0; i < blist.size(); i++) {
						Object[] objs = (Object[]) blist.get(i);
						if (objs[0] == null || objs[1] == null) {
							continue;
						}
						map.put(objs[0], objs[1]);
					}
					List slist = findContractImgExg(ct.getId(), impExpFlag);// 根据合同，查找合同内容
					Map smap = new HashMap();
					for (int i = 0; i < slist.size(); i++) {
						Object[] objs = (Object[]) slist.get(i);
						if (objs[0] == null || objs[1] == null) {
							continue;
						}
						smap.put(objs[0], objs[1]);
					}
					// ---------------------------------------------------------
					for (int i = 0; i < list.size(); i++) {
						TempBcsImpExpCommodityInfo info = list.get(i);
						if (smap.get(map.get(info.getSeqNo())) != null) {
							seqSer.put(info.getSeqNo(),
									smap.get(map.get(info.getSeqNo())));// key:归并序号
																		// value:
																		// 合同序号
							isOk = true;
						} else {
							isOk = false;
							break;
						}
					}
					// ---------------------------------------------------------
				} else {// 普通纸质手册
					List slist = findContractImgExg(ct.getId(), impExpFlag);// 根据合同，查找合同内容
					Map smap = new HashMap();
					for (int i = 0; i < slist.size(); i++) {
						Object[] objs = (Object[]) slist.get(i);
						if (objs[0] == null || objs[1] == null) {
							continue;
						}
						smap.put(objs[0], objs[1]);
					}
					// ---------------------------------------------------------
					for (int i = 0; i < list.size(); i++) {
						TempBcsImpExpCommodityInfo info = list.get(i);
						if (smap.get(info.getSeqNo()) != null) {
							seqSer.put(info.getSeqNo(),
									smap.get(info.getSeqNo()));// key:归并序号
							isOk = true;
						} else {
							isOk = false;
							break;
						}
					}
					// ---------------------------------------------------------
				}

				// ---------------------------------------------------------
				if (isOk) {
					restr.append("手册号为："
							+ (ct.getEmsNo() == null ? "" : ct.getEmsNo())
							+ " 的合同已经备案所所要转的商品。\n");
					boolean isAll = false;
					Map statmap = getContractStata(ct, impExpFlag);// key:合同序号
					// value:对应的可进口量
					for (int i = 0; i < list.size(); i++) {
						TempBcsImpExpCommodityInfo info = list.get(i);
						double remAmount = statmap.get(seqSer.get(info
								.getSeqNo())) == null ? 0.0 : (Double) statmap
								.get(seqSer.get(info.getSeqNo()));
						double reqAmonut = info.getImpExpCommodityInfo()
								.getQuantity() == null ? 0.0 : info
								.getImpExpCommodityInfo().getQuantity();
						if (remAmount <= 0) {
							isAll = false;
							break;
						} else {
							isAll = true;
						}
						if (remAmount < reqAmonut) {
							isAll = false;
							break;
						} else {
							isAll = true;
						}
					}
					if (isAll) {
						if (contract == null) {
							contract = ct;
						}
						restr.append("同时该合同的可进口量也大于申请单折算后的数量,系统可能选择该合同！\n");

					} else {
						restr.append("但该合同的可进口量小于申请单折算后的数量，系统将排除此合同！\n");
					}

				} else {
					restr.append("手册号为："
							+ (ct.getEmsNo() == null ? "" : ct.getEmsNo())
							+ " 的合同没有备案所要转的商品。\n");
				}
				restr.append("-------------------------------------------------------------\n");
			}
			if (contract != null) {
				restr.append("最后结果，系统所选合同手册号为："
						+ (contract.getEmsNo() == null ? "" : contract
								.getEmsNo()) + "\n");
			} else {
				restr.append("最后结果，没有合同满足条件！\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		retList.add(contract);
		retList.add(restr);
		return retList;
	}

	/**
	 * 查找该合同商品的余量
	 * 
	 * @param ct
	 *            合同
	 * @param flag
	 *            进出口标志
	 * @return
	 */
	public Map getContractStata(Contract ct, Integer flag) {
		Map rmap = new HashMap();
		if (ct == null || flag == null) {
			return rmap;
		}
		List contractlsit = new ArrayList();
		contractlsit.add(ct);
		if (ImpExpFlag.IMPORT == flag) {
			List<ImpMaterialStat> statlist = this.contractStatLogic
					.findImpMaterialStatByContracts(contractlsit, null, null,
							CustomsDeclarationState.ALL, true, false);
			for (int j = 0; j < statlist.size(); j++) {
				ImpMaterialStat stat = (ImpMaterialStat) statlist.get(j);
				try {
					Integer.parseInt(stat.getSerialNo());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				rmap.put(Integer.parseInt(stat.getSerialNo()),
						stat.getCanImportAmount());
			}

		} else {
			List<ExpProductStat> statlist = this.contractStatLogic
					.findExpProductStatByContracts(contractlsit, null, null,
							CustomsDeclarationState.ALL, false);
			for (int j = 0; j < statlist.size(); j++) {
				ExpProductStat stat = (ExpProductStat) statlist.get(j);
				try {
					Integer.parseInt(stat.getSerialNo());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				rmap.put(Integer.parseInt(stat.getSerialNo()),
						stat.getCanExportAmount());
			}
		}
		return rmap;
	}

	/**
	 * 根据合同ID，查找所有商品
	 * 
	 * @param cid
	 *            合同ID
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	public List findContractImgExg(String cid, int impExpFlag) {
		List rlist = this.bcsImpExpRequestDao.findContractImgExg(cid,
				impExpFlag);
		return rlist;
	}

	/**
	 * 获取统计报表逻辑层
	 * 
	 * @return
	 */

	public ContractStatLogic getContractStatLogic() {
		return contractStatLogic;
	}

	/**
	 * 设置统计报表逻辑层
	 * 
	 * @return
	 */

	public void setContractStatLogic(ContractStatLogic contractStatLogic) {
		this.contractStatLogic = contractStatLogic;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	/**
	 * 取得物料与报关对应表中料件
	 * 
	 * @param isMaterial
	 *            是否料件
	 * @return
	 */
	public List getTempMaterielByTypeBcs(boolean isMaterial) {
		return this.bcsImpExpRequestDao.getTempMaterielByTypeBcs(isMaterial);
	}
}
