/*
 * Created on 2004-7-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.customslist.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.TempBillListCommInfo;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.system.dao.CreateDirDao;
import com.bestway.bcus.system.dao.SysCodeDao;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.fpt.logic.FptManageLogic;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.contractexe.dao.DzscContractExeDao;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.dao.DzscListDao;
import com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.customslist.entity.DzscMakeApplyToCustoms;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialExg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialImg;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.message.logic.DzscMessageLogic;

/**
 * 单据操作logic
 * com.bestway.dzsc.customslist.logic.DzscListLogic
 * edit by cjb 2010-01-05
 * @author Administrator
 * 
 */
public class DzscListLogic {
	/**
	 * 手册操作dao
	 */
	private DzscDao dzscDao = null;

	/**
	 * 统参数设置Dao
	 */
	private CreateDirDao createDirDao = null;

	/**
	 * 手册通关/合同操作dao
	 */
	private DzscContractExeDao dzscContractExeDao = null;

	/**
	 * 单据操作dao
	 */
	private DzscListDao dzscListDao = null;

	/**
	 * 申请单操作dao
	 */
	private EncDao encDao = null;

	/**
	 * 报文操作dao
	 */
	private DzscMessageLogic dzscMessageLogic = null;

	/**
	 * 没有使用
	 */
	private SysCodeDao sysCodeDao = null;

	/**
	 * 转厂管理的Logic
	 */
	private FptManageLogic fptManageLogic = null;

	/**
	 * 报关申请单转报关清单
	 * 
	 * @param list
	 *            是TempImpExpCommodityInfo型，存放临时的申请单物料资料
	 * @param dzscEmsPorHead
	 *            通关备案表头
	 * @return List 是TempImpExpCommodityInfo型，在商品归并里没变更或在通关备案里存在的物料资料
	 */
	public List checkRequestBillForBillList(List list,
			DzscEmsPorHead dzscEmsPorHead) {
		List<Object> lsResult = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) list.get(i);
			Materiel materiel = t.getImpExpCommodityInfo().getMateriel();
			if (MaterielType.MATERIEL.equals(materiel.getScmCoi()
					.getCoiProperty())) {
				if (this.dzscDao.findDzscEmsPorMaterialImg(
						dzscEmsPorHead.getEmsNo(),
						t.getImpExpCommodityInfo().getMateriel()).size() <= 0) {
					lsResult.add(t);
				}
			} else if (MaterielType.FINISHED_PRODUCT.equals(materiel
					.getScmCoi().getCoiProperty())
					|| MaterielType.SEMI_FINISHED_PRODUCT.equals(materiel
							.getScmCoi().getCoiProperty())) {
				if (this.dzscDao.findDzscEmsPorMaterialExg(
						dzscEmsPorHead.getEmsNo(),
						t.getImpExpCommodityInfo().getMateriel()).size() <= 0) {
					lsResult.add(t);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 查询出实体{entityName}最大的内部编号
	 * @return
	 */
	public String getApplyToCustomsBillListMaxNo() {
		return dzscMessageLogic.getNewCopEntNo("DzscCustomsBillList",
				"copEmsNo", "D", DzscBusinessType.CUSTOMS_BILL_LIST);
	}

	/**
	 * 进出口申请单--->报关清单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isImportGoods
	 * 是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @param dataSource
	 *            临时的申请单物料资料
	 * @param isImg
	 *            是进货还是出货(出口还是进口)，true表示进口
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单，true表示新增
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */
	public List makeApplyToCustomsRequestBillList(
			DzscCustomsBillList applyToCustomsBillList, List dataSource,
			boolean isImg, boolean isNewRecord, boolean isMergeData) {
		List atcMergeAfterComInfoList = new ArrayList();
		List atcMergeBeforeComInfoList = new ArrayList();
		List oldImpExpCommodityInfoList = new ArrayList();
		List<DzscCustomsBillList> returnList = new ArrayList<DzscCustomsBillList>();
		Map map = new HashMap();
		/**
		 * 把所有相同的ptNo数量等其它项整合成一条 dataSource:所有选种的申请单体)
		 */
		List impExpCommodityInfoList = this.mergeImpExpCommodityInfo(
				dataSource, isMergeData);
		/**
		 * impExpCommodityInfoList:整合后的申请单体 保存报关清单表头用来获得其ID
		 */
		if (applyToCustomsBillList.getListNo() == null
				|| "".equals(applyToCustomsBillList.getListNo().trim())) {
			String copEntNo = dzscMessageLogic.getNewCopEntNo(
					"DzscCustomsBillList", "copEmsNo", "D",
					DzscBusinessType.CUSTOMS_BILL_LIST);
			applyToCustomsBillList.setListNo(copEntNo.substring(3));
			applyToCustomsBillList.setCopEmsNo(copEntNo);
		} else if (applyToCustomsBillList.getCopEmsNo() == null
				|| "".equals(applyToCustomsBillList.getCopEmsNo().trim())) {
			applyToCustomsBillList.setCopEmsNo("ENT"
					+ applyToCustomsBillList.getListNo());
		}
		this.dzscListDao.saveApplyToCustomsBillList(applyToCustomsBillList);
		returnList.add(applyToCustomsBillList);// 清单头

		for (int i = 0; i < impExpCommodityInfoList.size(); i++) {
			int icount = this.dzscListDao
					.findAtcMergeAfterComInfoCountByListID(applyToCustomsBillList);
			if (icount >= 20) {
				applyToCustomsBillList.setMaterialNum(Integer.valueOf(icount));
				this.dzscListDao
						.saveApplyToCustomsBillList(applyToCustomsBillList);
				applyToCustomsBillList = makeDzscCustomsBillList(applyToCustomsBillList);
				returnList.add(applyToCustomsBillList);
			}
			ImpExpCommodityInfo t = (ImpExpCommodityInfo) impExpCommodityInfoList
					.get(i);
			/**
			 * 通过结转商品信息的料号（ptNo) 获得归并系关中关并后的商品信息
			 */
			String ptNo = t.getMateriel().getPtNo();
			DzscInnerMergeData data = this.dzscDao
					.findInnerMergeDataByPtNo(ptNo);
			if (data == null) {
				throw new RuntimeException("料件" + ptNo + "在归并关系中不存在");
			}
			Double unitScale = data.getUnitConvert();
			DzscBillListAfterCommInfo atcMergeAfterComInfo = null;
			Double legalUnitGene = 0.0;
			Double legalUnit2Gene = 0.0;
			if (isImg == true) {// 料件
				List lsTemp = this.dzscDao
						.findDzscEmsPorMaterialImg(applyToCustomsBillList
								.getEmsHeadH2k(), t.getMateriel());
				if (lsTemp.size() <= 0) {
					throw new RuntimeException("料件" + t.getMateriel().getPtNo()
							+ "在手册" + applyToCustomsBillList.getEmsHeadH2k()
							+ "的<归并料件>中不存在");
				} else {
					DzscEmsPorMaterialImg materialImg = (DzscEmsPorMaterialImg) lsTemp
							.get(0);
					legalUnitGene = materialImg.getDzscEmsImgBill()
							.getLegalUnitGene();
					legalUnit2Gene = materialImg.getDzscEmsImgBill()
							.getLegalUnit2Gene();

					if (isNewRecord == true) { // 新增的一条记录(正在使用中)
						atcMergeAfterComInfo = (DzscBillListAfterCommInfo) map
								.get(materialImg.getDzscEmsImgBill()
										.getSeqNum());
					} else {
						atcMergeAfterComInfo = this.dzscListDao
								.findAtcMergeAfterComInfoByListID(
										applyToCustomsBillList, materialImg
												.getDzscEmsImgBill()
												.getSeqNum());
					}
					if (atcMergeAfterComInfo == null) {
						atcMergeAfterComInfo = makeBillListCommInfoByMateriel(
								applyToCustomsBillList, materialImg
										.getDzscEmsImgBill(), t);
						atcMergeAfterComInfoList.add(atcMergeAfterComInfo);
						map.put(atcMergeAfterComInfo.getEmsSerialNo(),
								atcMergeAfterComInfo);
					}
				}
			} else {
				List lsTemp = this.dzscDao
						.findDzscEmsPorMaterialExg(applyToCustomsBillList
								.getEmsHeadH2k(), t.getMateriel());
				if (lsTemp.size() <= 0) {
					throw new RuntimeException("成品" + t.getMateriel().getPtNo()
							+ "在手册" + applyToCustomsBillList.getEmsHeadH2k()
							+ "的<归并成品>中不存在");
				} else {
					DzscEmsPorMaterialExg materialExg = (DzscEmsPorMaterialExg) lsTemp
							.get(0);
					legalUnitGene = materialExg.getDzscEmsExgBill()
							.getLegalUnitGene();
					legalUnit2Gene = materialExg.getDzscEmsExgBill()
							.getLegalUnit2Gene();

					if (isNewRecord == true) { // 新增的一条记录(正在使用中)
						atcMergeAfterComInfo = (DzscBillListAfterCommInfo) map
								.get(materialExg.getDzscEmsExgBill()
										.getSeqNum());
					} else {
						atcMergeAfterComInfo = this.dzscListDao
								.findAtcMergeAfterComInfoByListID(
										applyToCustomsBillList, materialExg
												.getDzscEmsExgBill()
												.getSeqNum());
					}
					if (atcMergeAfterComInfo == null) {
						atcMergeAfterComInfo = makeBillListCommInfoByMateriel(
								applyToCustomsBillList, materialExg
										.getDzscEmsExgBill(), t);
						atcMergeAfterComInfoList.add(atcMergeAfterComInfo);
						map.put(atcMergeAfterComInfo.getEmsSerialNo(),
								atcMergeAfterComInfo);
					}
				}
			}
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo = this
					.makeBillListBeforeComInfo(atcMergeAfterComInfo, t,
							unitScale, legalUnitGene, legalUnit2Gene);
			Double grossWeight = Double.valueOf(CommonUtils
					.getDoubleExceptNull(atcMergeAfterComInfo.getGrossWeight())
					+ CommonUtils.getDoubleExceptNull(atcMergeBeforeComInfo
							.getGrossWeight()));
			Double netWeight = Double.valueOf(CommonUtils
					.getDoubleExceptNull(atcMergeAfterComInfo.getNetWeight())
					+ CommonUtils.getDoubleExceptNull(atcMergeBeforeComInfo
							.getNetWeight()));
			Double declaredAmount = Double.valueOf(CommonUtils
					.getDoubleExceptNull(atcMergeAfterComInfo
							.getDeclaredAmount())
					+ CommonUtils.getDoubleExceptNull(atcMergeBeforeComInfo
							.getDeclaredAmount()));
			atcMergeAfterComInfo.setGrossWeight(grossWeight);
			atcMergeAfterComInfo.setNetWeight(netWeight);
			atcMergeAfterComInfo.setDeclaredAmount(declaredAmount);

			// Double weigthUnitGene = CommonUtils.getDoubleExceptNull(data
			// .getDzscTenInnerMerge().getWeigthUnitGene());
			atcMergeAfterComInfo.setLegalAmount(CommonUtils
					.getDoubleExceptNull(atcMergeAfterComInfo
							.getDeclaredAmount())
					* CommonUtils.getDoubleExceptNull(legalUnitGene));
			atcMergeAfterComInfo.setSecondLegalAmount(CommonUtils
					.getDoubleExceptNull(atcMergeAfterComInfo
							.getDeclaredAmount())
					* CommonUtils.getDoubleExceptNull(legalUnit2Gene));
			/**
			 * 修改来返回其把合计数量写入其中
			 */
			this.dzscListDao.saveAtcMergeAfterComInfo(atcMergeAfterComInfo);
			atcMergeBeforeComInfoList.add(atcMergeBeforeComInfo);
		}
		Integer flag = applyToCustomsBillList.getImpExpFlag();
		atcMergeBeforeComInfoList = sortListByCheckupComplexForDzscBillListBeforeCommInfo(
				atcMergeBeforeComInfoList, flag);
		int maxNo = this.dzscListDao
				.findAtcMergeBeforeComInfoMaxNo(applyToCustomsBillList);
		for (int i = 0; i < atcMergeBeforeComInfoList.size(); i++) {
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo = (DzscBillListBeforeCommInfo) atcMergeBeforeComInfoList
					.get(i);
			atcMergeBeforeComInfo.setNo(maxNo + i);
			this.dzscListDao.saveAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		}
		/**
		 * 修改报关清单的商品项数
		 */
		int icount = this.dzscListDao
				.findAtcMergeAfterComInfoCountByListID(applyToCustomsBillList);
		applyToCustomsBillList.setMaterialNum(Integer.valueOf(icount));
		this.dzscListDao.saveApplyToCustomsBillList(applyToCustomsBillList);
		/**
		 * 回写进出口申请单据商品信息(设置转报关清单为true)
		 */
		for (int i = 0; i < dataSource.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
					.get(i);
			ImpExpCommodityInfo imp = t.getImpExpCommodityInfo();
			imp.setIsTransferCustomsBill(new Boolean(true)); // 已转报关清单
			oldImpExpCommodityInfoList.add(imp);
		}
		this.encDao.saveImpExpCommodityInfo(oldImpExpCommodityInfoList);

		/**
		 * 生成中间表信息
		 */
		makeMakeApplyToCustomsInfos(dataSource, atcMergeBeforeComInfoList);
		/**
		 * 进出口申请单据生成报关清单时,修改进出口单据的生成的清单号码，已转项数
		 */
		writeBackImpExpRequestBillInfo(dataSource);
		return returnList;
	}

	/**
	 * 把所有相同的ptNo数量等其它项整合成一条
	 * 
	 * @param dataSource
	 *            是TempImpExpCommodityInfo型，存放临时的申请单物料资料
	 * @return List 存放申请单物料资料
	 */
	private List mergeImpExpCommodityInfo(List dataSource, boolean isMergeData) {
		List impExpCommodityList = new ArrayList();
		Map map = new HashMap();
		if (isMergeData) {
			for (int i = 0; i < dataSource.size(); i++) {
				TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
						.get(i);
				if (t.getImpExpCommodityInfo().getMateriel() == null
						|| t.getImpExpCommodityInfo() == null) {
					continue;
				}
				String key = (t.getImpExpCommodityInfo().getMateriel()
						.getPtNo() + (t.getImpExpCommodityInfo().getCountry() == null ? ""
						: t.getImpExpCommodityInfo().getCountry().getCode()));
				Object obj = map.get(key);
				if (obj != null) {
					ImpExpCommodityInfo data = (ImpExpCommodityInfo) obj;
					Double quantity = CommonUtils.getDoubleExceptNull(data
							.getQuantity())
							+ CommonUtils.getDoubleExceptNull(t
									.getImpExpCommodityInfo().getQuantity());
					Double totalPrice = CommonUtils.getDoubleExceptNull(data
							.getAmountPrice())
							+ CommonUtils.getDoubleExceptNull(t
									.getImpExpCommodityInfo().getAmountPrice());
					Double grossWeight = CommonUtils.getDoubleExceptNull(data
							.getGrossWeight())
							+ CommonUtils.getDoubleExceptNull(t
									.getImpExpCommodityInfo().getGrossWeight());
					Double netWeight = CommonUtils.getDoubleExceptNull(data
							.getNetWeight())
							+ CommonUtils.getDoubleExceptNull(t
									.getImpExpCommodityInfo().getNetWeight());
					Double cubage = CommonUtils.getDoubleExceptNull(data
							.getBulks())
							+ CommonUtils.getDoubleExceptNull(t
									.getImpExpCommodityInfo().getBulks());
					Integer piece = (data.getPiece() == null ? 0
							: data.getPiece()
									+ (t.getImpExpCommodityInfo().getPiece() == null ? 0
											: t.getImpExpCommodityInfo()
													.getPiece()));
					data.setPiece(piece);
					data.setQuantity(quantity);
					data.setAmountPrice(totalPrice);
					data.setGrossWeight(grossWeight);
					data.setNetWeight(netWeight);
					data.setBulks(cubage);
				} else {
					map.put(t.getImpExpCommodityInfo().getMateriel().getPtNo(),
							t.getImpExpCommodityInfo());
				}
			}
			impExpCommodityList.addAll(map.values());
		} else {
			for (int i = 0; i < dataSource.size(); i++) {
				TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
						.get(i);
				impExpCommodityList.add(t.getImpExpCommodityInfo());
			}
		}
		return impExpCommodityList;
	}

	/**
	 * 根据结转单据商品信息(料件) 生成报关清单关归后商品信息--来自企业归并关系 通过单位折算比例因子计算转换报关后数量
	 * 
	 * @param data
	 *            报关清单表头
	 * @param exgBill
	 *            商品归并资料
	 * @param t
	 *            申请单物料资料
	 * @return DzscBillListAfterCommInfo 报关清单归并后商品信息
	 */
	private DzscBillListAfterCommInfo makeBillListCommInfoByMateriel(
			DzscCustomsBillList data, DzscEmsExgBill exgBill,
			ImpExpCommodityInfo t) {
		DzscBillListAfterCommInfo afterCommInfo = new DzscBillListAfterCommInfo();// 清单
		afterCommInfo.setBillList(data);
		afterCommInfo.setCompany(CommonUtils.getCompany());
		afterCommInfo.setComplex(exgBill.getComplex());
		afterCommInfo.setComplexName(exgBill.getName());
		afterCommInfo.setComplexSpec(exgBill.getSpec());
		afterCommInfo.setEmsSerialNo(exgBill.getSeqNum());
		afterCommInfo.setUnit(exgBill.getUnit());
		this.dzscListDao.saveAtcMergeAfterComInfo(afterCommInfo);
		return afterCommInfo;
	}

	/**
	 * 根据结转单据商品信息(料件) 生成报关清单关归后商品信息--来自企业归并关系 通过单位折算比例因子计算转换报关后数量
	 * 
	 * @param data
	 *            报关清单表头
	 * @param imgBill
	 *            商品归并资料
	 * @param t
	 *            申请单物料资料
	 * @return DzscBillListAfterCommInfo 报关清单归并后商品信息
	 */
	private DzscCustomsBillList makeDzscCustomsBillList(
			DzscCustomsBillList preBillList) {
		DzscCustomsBillList billList = null;
		try {
			billList = (DzscCustomsBillList) BeanUtils.cloneBean(preBillList);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (billList == null) {
			throw new RuntimeException("复制清单表头出错！");
		}
		String copEntNo = dzscMessageLogic.getNewCopEntNo(
				"DzscCustomsBillList", "copEmsNo", "D",
				DzscBusinessType.CUSTOMS_BILL_LIST);
		billList.setId(null);
		billList.setListNo(copEntNo.substring(3));
		billList.setCopEmsNo(copEntNo);
		this.dzscListDao.saveApplyToCustomsBillList(billList);
		return billList;
	}

	/**
	 * 根据结转单据商品信息(料件) 生成报关清单关归后商品信息--来自企业归并关系 通过单位折算比例因子计算转换报关后数量
	 * 
	 * @param data
	 *            报关清单表头
	 * @param imgBill
	 *            商品归并资料
	 * @param t
	 *            申请单物料资料
	 * @return DzscBillListAfterCommInfo 报关清单归并后商品信息
	 */
	private DzscBillListAfterCommInfo makeBillListCommInfoByMateriel(
			DzscCustomsBillList data, DzscEmsImgBill imgBill,
			ImpExpCommodityInfo t) {
		DzscBillListAfterCommInfo afterCommInfo = new DzscBillListAfterCommInfo();// 清单
		afterCommInfo.setBillList(data);
		afterCommInfo.setCompany(CommonUtils.getCompany());
		afterCommInfo.setComplex(imgBill.getComplex());
		afterCommInfo.setComplexName(imgBill.getName());
		afterCommInfo.setComplexSpec(imgBill.getSpec());
		afterCommInfo.setEmsSerialNo(imgBill.getSeqNum());
		afterCommInfo.setUnit(imgBill.getUnit());
		this.dzscListDao.saveAtcMergeAfterComInfo(afterCommInfo);
		return afterCommInfo;
	}

	/**
	 * 把Double值转换为Integer
	 * 
	 * @param d
	 *            要转的Double值
	 * @return Integer 转换后的Integer
	 */
	private Integer douToInteger(Double d) {
		if (d == null || d == Double.valueOf(0)) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(d));
	}

	/**
	 * 根据内部归并后信息与进出口商品信息生成报关清单归并前的商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            报关清单归并后商品信息
	 * @param impExpCommodityInfo
	 *            进出口申请单物料信息
	 * @return DzscBillListBeforeCommInfo 报关清单归并前商品信息
	 */
	private DzscBillListBeforeCommInfo makeBillListBeforeComInfo(
			DzscBillListAfterCommInfo atcMergeAfterComInfo,
			ImpExpCommodityInfo impExpCommodityInfo, Double unitScale,
			Double legalUnitGene, Double legalUnit2Gene) {
		DzscBillListBeforeCommInfo beforeCommInfo = new DzscBillListBeforeCommInfo();
		// int maxNo = this.dzscListDao
		// .findAtcMergeBeforeComInfoMaxNo(atcMergeAfterComInfo
		// .getBillList());
		// beforeCommInfo.setNo(maxNo + 1);
		beforeCommInfo.setAfterComInfo(atcMergeAfterComInfo);
		beforeCommInfo.setCompany(CommonUtils.getCompany());
		beforeCommInfo.setCurrency(impExpCommodityInfo.getCurrency());
		beforeCommInfo.setMateriel(impExpCommodityInfo.getMateriel());
		beforeCommInfo.setSalesCountry(impExpCommodityInfo.getCountry());
		if (unitScale == null || unitScale.equals(Double.valueOf(0))) {
			unitScale = new Double(1.0);
		}
		beforeCommInfo.setDeclaredAmount(CommonUtils
				.getDoubleExceptNull(impExpCommodityInfo.getQuantity())
				* unitScale);
		beforeCommInfo.setTotalPrice(CommonUtils
				.getDoubleExceptNull(impExpCommodityInfo.getAmountPrice()));
		beforeCommInfo
				.setDeclaredPrice(beforeCommInfo.getDeclaredAmount() == 0.0 ? 0.0
						: CommonUtils.getDoubleByDigit(beforeCommInfo
								.getTotalPrice()
								/ beforeCommInfo.getDeclaredAmount(), 5));
		// beforeCommInfo.setDeclaredPrice(CommonUtils
		// .getDoubleExceptNull(impExpCommodityInfo.getUnitPrice())
		// / unitScale);
		beforeCommInfo.setGrossWeight(CommonUtils
				.getDoubleExceptNull(impExpCommodityInfo.getGrossWeight())
				/ unitScale);
		beforeCommInfo.setNetWeight(CommonUtils
				.getDoubleExceptNull(impExpCommodityInfo.getNetWeight())
				/ unitScale);
		if (legalUnitGene == null || legalUnitGene.equals(Double.valueOf(0))) {
			legalUnitGene = new Double(1.0);
		}
		if (legalUnit2Gene == null || legalUnit2Gene.equals(Double.valueOf(0))) {
			legalUnit2Gene = new Double(1.0);
		}
		if (beforeCommInfo.getAfterComInfo() != null
				&& beforeCommInfo.getAfterComInfo().getComplex() != null
				&& beforeCommInfo.getAfterComInfo().getComplex().getFirstUnit() != null) {
			beforeCommInfo.setLegalAmount(CommonUtils
					.getDoubleExceptNull(beforeCommInfo.getDeclaredAmount())
					* legalUnitGene);
		}
		if (beforeCommInfo.getAfterComInfo() != null
				&& beforeCommInfo.getAfterComInfo().getComplex() != null
				&& beforeCommInfo.getAfterComInfo().getComplex()
						.getSecondUnit() != null) {
			beforeCommInfo.setSecondLegalAmount(CommonUtils
					.getDoubleExceptNull(beforeCommInfo.getDeclaredAmount())
					* legalUnit2Gene);
		}
		beforeCommInfo.setBomVersion(0);
		this.dzscListDao.saveAtcMergeBeforeComInfo(beforeCommInfo);
		return beforeCommInfo;
	}

	/**
	 * 生成中间表信息
	 * 
	 * @param tempImpExpCommodityInfoList
	 *            存放临时的申请单物料资料
	 * @param atcMergeBeforeComInfoList
	 *            报关清单归并前商品信息
	 */
	private void makeMakeApplyToCustomsInfos(List tempImpExpCommodityInfoList,
			List atcMergeBeforeComInfoList) {
		List makeApplyToCustomsList = new ArrayList();
		for (int i = 0; i < tempImpExpCommodityInfoList.size(); i++) {
			TempImpExpCommodityInfo temp = (TempImpExpCommodityInfo) tempImpExpCommodityInfoList
					.get(i);
			DzscBillListBeforeCommInfo a = getAtcMergeBeforeComInfoByPtNo(temp
					.getImpExpCommodityInfo().getMateriel().getPtNo(),
					atcMergeBeforeComInfoList);
			DzscMakeApplyToCustoms m = new DzscMakeApplyToCustoms();
			m.setAtcMergeBeforeComInfo(a);
			m.setImpExpCommodityInfo(temp.getImpExpCommodityInfo());
			m.setCompany(CommonUtils.getCompany());
			makeApplyToCustomsList.add(m);
		}
		this.dzscListDao.saveDzscMakeApplyToCustoms(makeApplyToCustomsList);
	}

	/**
	 * 进出口申请单据生成报关清单时,修改进出口单据的生成的清单号码，已转项数
	 * 
	 * @param dataSource
	 *            存放临时的申请单物料资料
	 * @param newListNo
	 *            新的清单号码
	 * @return List 是ImpExpRequestBill型，进出口申请单表头
	 */
	private void writeBackImpExpRequestBillInfo(List dataSource) {
		List impExpRequestBillList = new ArrayList();
		Set set = new HashSet();
		for (int i = 0; i < dataSource.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
					.get(i);
			set.add(t.getImpExpCommodityInfo().getImpExpRequestBill());
		}
		/**
		 * 修改结转单据的已转关封申请单字段
		 */
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			ImpExpRequestBill t = (ImpExpRequestBill) iterator.next();
			Integer tocustomCount = this.encDao.getInfoForToCustom(t);
			t.setToCustomCount(tocustomCount);
			if (t.getItemCount() != null
					&& t.getItemCount().equals(tocustomCount)) { // 判断是否全部转关
				t.setIsCustomsBill(new Boolean(true));
			}
			List list = this.dzscListDao
					.findDzscMakeBillListCodeByRequestBill(t);
			String alllistNo = "";
			for (int i = 0; i < list.size(); i++) {
				alllistNo += ((list.get(i) == null ? "" : list.get(i)
						.toString()) + ",");
			}
			t.setAllBillNo(alllistNo);
			this.encDao.saveImpExpRequestBill(t);
			impExpRequestBillList.add(t);
		}
	}

	/**
	 * 判断newbillNo是否在allbillNo里
	 * 
	 * @param allbillNo
	 *            已转清单号码
	 * @param newbillNo
	 *            新的清单号码
	 * @return boolean newbillNo在allbillNo里时返回true，否则返回false
	 */
	public boolean isExistBillNo(String allbillNo, String newbillNo) {
		if (allbillNo == null) {
			return false;
		}
		String[] yy = allbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (yy[i].toString().equals(newbillNo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查找所有进出报关清单
	 * 
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findApplyToCustomsBillList() {
		return this.getDzscListDao().findApplyToCustomsBillList();
	}

	/**
	 * 根据清单类型查找报关清单
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHead
	 *            手册表头
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findApplyToCustomsBillListByType(int impExpFlag,
			BaseEmsHead emsHead, Date beginDate, Date endDate) {
		return this.getDzscListDao().findApplyToCustomsBillListByType(
				impExpFlag, emsHead, beginDate, endDate);
	}

	/**
	 * 删除报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	public void deleteApplyToCustomsBillList(
			DzscCustomsBillList applyToCustomsBillList) {
		List list = null;
		DzscBillListAfterCommInfo atcMergeAfterComInfo = null;
		DzscBillListBeforeCommInfo atcMergeBeforeComInfo = null;
		list = this.getDzscListDao().findAtcMergeBeforeComInfoByListID(
				applyToCustomsBillList);
		for (int i = 0; i < list.size(); i++) {
			atcMergeBeforeComInfo = (DzscBillListBeforeCommInfo) list.get(i);
			//
			// 回写进出口申请单
			//
			this
					.writeBackImpExpRequestBillByApplyToCustomsBillId(atcMergeBeforeComInfo
							.getId());
			this.getDzscListDao().deleteAtcMergeBeforeComInfo(
					atcMergeBeforeComInfo);
		}
		list = this.getDzscListDao().findAtcMergeAfterComInfoByListID(
				applyToCustomsBillList);
		for (int i = 0; i < list.size(); i++) {
			atcMergeAfterComInfo = (DzscBillListAfterCommInfo) list.get(i);
//			transferFactoryManageLogic
//					.writeBackTransFactBillWhenDelAfterCommInfo(atcMergeAfterComInfo
//							.getId());
			this.getDzscListDao().deleteAtcMergeAfterComInfo(
					atcMergeAfterComInfo);
		}
		String applyToCustomsBillListId = applyToCustomsBillList.getId();
		this.getDzscListDao().deleteApplyToCustomsBillList(
				applyToCustomsBillList);
	}

	/**
	 * 保存报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	public void saveApplyToCustomsBillList(
			DzscCustomsBillList applyToCustomsBillList) {
		if (applyToCustomsBillList.getListNo() == null
				|| "".equals(applyToCustomsBillList.getListNo().trim())) {
			String copEntNo = dzscMessageLogic.getNewCopEntNo(
					"DzscCustomsBillList", "copEmsNo", "D",
					DzscBusinessType.CUSTOMS_BILL_LIST);
			applyToCustomsBillList.setListNo(copEntNo.substring(3));
			applyToCustomsBillList.setCopEmsNo(copEntNo);
		}
		this.getDzscListDao()
				.saveApplyToCustomsBillList(applyToCustomsBillList);
	}

	/**
	 * 申报保管清单
	 * 
	 * @param dzscCustomsBillList
	 *            报关清单表头
	 */
	public DeclareFileInfo applyDzscCustomsBillList(
			DzscCustomsBillList dzscCustomsBillList, String taskId) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscCustomsBillList> lsHead = new ArrayList<DzscCustomsBillList>();
		List<DzscBillListBeforeCommInfo> lsDetail = new ArrayList<DzscBillListBeforeCommInfo>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}

		dzscCustomsBillList.setListState(Integer.parseInt(DzscState.APPLY));
		dzscCustomsBillList.setListDeclareDate(new Date());
		this.getDzscListDao().saveApplyToCustomsBillList(dzscCustomsBillList);
		lsHead.add(dzscCustomsBillList);
		lsDetail = this.dzscListDao
				.findAtcMergeBeforeComInfoByListID(dzscCustomsBillList);
		Integer flag = dzscCustomsBillList.getImpExpFlag();
		lsDetail = sortListByCheckupComplexForDzscBillListBeforeCommInfo(
				lsDetail, flag);
		List lsDzscEmsPorHead = this.dzscDao
				.findDzscEmsPorHeadExcu(dzscCustomsBillList.getEmsHeadH2k());
		DzscEmsPorHead dzscEmsPorHead = lsDzscEmsPorHead.size() > 0 ? (DzscEmsPorHead) lsDzscEmsPorHead
				.get(0)
				: null;
		if (dzscEmsPorHead == null) {
			throw new RuntimeException("系统找不到正在执行的手册"
					+ dzscCustomsBillList.getEmsHeadH2k());
		}
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscEmsPorHead.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(dzscCustomsBillList.getCopEmsNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.CUSTOMS_BILL_LIST);
		lsSignInfo.add(signInfo);

		String formatFile = "DzscCustomsBillListFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("DzscListHeadData", lsHead);
		hmData.put("DzscListDetailData", lsDetail);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 根据手册号排序
	 * @param datalist
	 * @param flag
	 * @return
	 */
	public List sortListByCheckupComplexForDzscBillListBeforeCommInfo(
			List datalist, Integer flag) {
		if (datalist == null) {
			return new ArrayList();
		}
		List<String> codelist = this.dzscListDao.findComplexByFlag(flag);
		List yesList = new ArrayList();
		List noList = new ArrayList();
		List relist = new ArrayList();
		for (int i = 0; i < datalist.size(); i++) {
			DzscBillListBeforeCommInfo tempCustomsList = (DzscBillListBeforeCommInfo) datalist
					.get(i);
			if (tempCustomsList.getAfterComInfo() == null
					|| tempCustomsList.getAfterComInfo().getComplex() == null
					|| tempCustomsList.getAfterComInfo().getComplex().getCode() == null) {
				noList.add(tempCustomsList);
				continue;
			}
			if (codelist.contains(tempCustomsList.getAfterComInfo()
					.getComplex().getCode())) {
				yesList.add(tempCustomsList);
			} else {
				noList.add(tempCustomsList);
			}
		}
		Collections.sort(yesList, new Comparator<DzscBillListBeforeCommInfo>() {
			public int compare(DzscBillListBeforeCommInfo o1,
					DzscBillListBeforeCommInfo o2) {
				if (o1 == null || o1.getAfterComInfo() == null
						|| o1.getAfterComInfo().getEmsSerialNo() == null) {
					return -1;
				}
				if (o2 == null || o2.getAfterComInfo() == null
						|| o2.getAfterComInfo().getEmsSerialNo() == null) {
					return 1;
				}
				if (o1.getAfterComInfo().getEmsSerialNo() > o2
						.getAfterComInfo().getEmsSerialNo()) {
					return 1;
				} else if (o1.getAfterComInfo().getEmsSerialNo() == o2
						.getAfterComInfo().getEmsSerialNo()) {
					return 0;
				} else {
					return -1;
				}

			}
		});
		Collections.sort(noList, new Comparator<DzscBillListBeforeCommInfo>() {
			public int compare(DzscBillListBeforeCommInfo o1,
					DzscBillListBeforeCommInfo o2) {
				if (o1 == null || o1.getAfterComInfo() == null
						|| o1.getAfterComInfo().getEmsSerialNo() == null) {
					return -1;
				}
				if (o2 == null || o2.getAfterComInfo() == null
						|| o2.getAfterComInfo().getEmsSerialNo() == null) {
					return 1;
				}
				if (o1.getAfterComInfo().getEmsSerialNo() > o2
						.getAfterComInfo().getEmsSerialNo()) {
					return 1;
				} else if (o1.getAfterComInfo().getEmsSerialNo() == o2
						.getAfterComInfo().getEmsSerialNo()) {
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
	 * 报关清单回执处理
	 * 
	 * @param dzscCustomsBillList
	 *            报关清单表头
	 */
	public String processDzscCustomsBillList(
			final DzscCustomsBillList dzscCustomsBillList, List lsReturnFile) {
		return dzscMessageLogic.processMessage(
				DzscBusinessType.CUSTOMS_BILL_LIST, dzscCustomsBillList
						.getCopEmsNo(), new CspProcessMessage() {

					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						dzscCustomsBillList.setListState(Integer
								.parseInt(DzscState.BACK_BILL));
						getDzscListDao().saveApplyToCustomsBillList(
								dzscCustomsBillList);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						CspReceiptResult receiptResult = tempReceiptResult
								.getReceiptResult();
						dzscCustomsBillList.setListState(Integer
								.parseInt(DzscState.EXECUTE));
						dzscCustomsBillList.setCustomsSeqNo(receiptResult
								.getSeqNo());
						getDzscListDao().saveApplyToCustomsBillList(
								dzscCustomsBillList);
					}
				},lsReturnFile);
	}

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	public List findAtcMergeAfterComInfoByListID(
			DzscCustomsBillList applyToCustomsBillList) {
		return this.getDzscListDao().findAtcMergeAfterComInfoByListID(
				applyToCustomsBillList);
	}

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品信息
	 * @return List 是DzscBillListBeforeCommInfo型，清单归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByAfterID(
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		return this.getDzscListDao().findAtcMergeBeforeComInfoByAfterID(
				atcMergeAfterComInfo);
	}

	/**
	 * 查找所有归并前信息
	 */
	public List findAllAtcMergerBeforeComInfo(
			DzscCustomsBillList dzscCustomsBillList) {
		return this.getDzscListDao().findAllAtcMergerBeforeComInfo(
				dzscCustomsBillList);
	}

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 *            清单归并前商品信息
	 * @return DzscCustomsBillList 报关清单表头
	 */
	public DzscCustomsBillList deleteAtcMergeBeforeComInfo(
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo) {
		DzscBillListAfterCommInfo atcMergeAfterComInfo = atcMergeBeforeComInfo
				.getAfterComInfo();
		DzscCustomsBillList billList = atcMergeAfterComInfo.getBillList();
		this.getDzscListDao()
				.deleteAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		//
		// 回写转厂关封
		//
		// if (atcMergeBeforeComInfo.getIsAutoCreate() != null) {
		// if (atcMergeBeforeComInfo.getIsAutoCreate().booleanValue() == true) {
		// transferFactoryManageLogic
		// .writeBackTransferFactoryBillToApplyToCustomsBillId(atcMergeBeforeComInfo
		// .getId());
		// }
		// }
		/**
		 * 回写进出口申请单
		 */
		this
				.writeBackImpExpRequestBillByApplyToCustomsBillId(atcMergeBeforeComInfo
						.getId());

		List list = this.getDzscListDao().findAtcMergeBeforeComInfoByAfterID(
				atcMergeAfterComInfo);
		if (list.size() < 1) {
//			transferFactoryManageLogic
//					.writeBackTransFactBillWhenDelAfterCommInfo(atcMergeAfterComInfo
//							.getId());
			this.getDzscListDao().deleteAtcMergeAfterComInfo(
					atcMergeAfterComInfo);
			billList.setMaterialNum(Integer.valueOf(this.getDzscListDao()
					.getAtcMergeAfterCommonNum(billList)));
			this.getDzscListDao().saveApplyToCustomsBillList(billList);
		} else {
			statMergeAfterCommInfo(atcMergeAfterComInfo);
		}
		return billList;
	}

	/**
	 * 删除报关清单归并前商品信息(多笔)
	 * 
	 * @param list
	 *            是DzscBillListBeforeCommInfo型，报关清单归并前商品信息
	 * @return DzscCustomsBillList 报关清单表头
	 */
	public DzscCustomsBillList deleteAtcMergeBeforeComInfo(List list) {
		if (list.size() < 1) {
			return null;
		}
		// List tempList = new ArrayList();
		DzscBillListBeforeCommInfo atcMergeBeforeComInfo = null;
		DzscBillListAfterCommInfo atcMergeAfterComInfo = null;
		atcMergeAfterComInfo = ((DzscBillListBeforeCommInfo) list.get(0))
				.getAfterComInfo();
		DzscCustomsBillList billList = atcMergeAfterComInfo.getBillList();
		for (int i = 0; i < list.size(); i++) {
			atcMergeBeforeComInfo = (DzscBillListBeforeCommInfo) list.get(i);
			// //
			// // 回写转厂关封
			// //
			// if (atcMergeBeforeComInfo.getIsAutoCreate() != null) {
			// if (atcMergeBeforeComInfo.getIsAutoCreate().booleanValue() ==
			// true) {
			// transferFactoryManageLogic
			// .writeBackTransferFactoryBillToApplyToCustomsBillId(atcMergeBeforeComInfo
			// .getId());
			// }
			// }
			/**
			 * 回写进出口申请单
			 */
			this
					.writeBackImpExpRequestBillByApplyToCustomsBillId(atcMergeBeforeComInfo
							.getId());
			this.getDzscListDao().deleteAtcMergeBeforeComInfo(
					atcMergeBeforeComInfo);
		}
		list = this.getDzscListDao().findAtcMergeBeforeComInfoByAfterID(
				atcMergeAfterComInfo);
		if (list.size() < 1) {
//			transferFactoryManageLogic
//					.writeBackTransFactBillWhenDelAfterCommInfo(atcMergeAfterComInfo
//							.getId());
			this.getDzscListDao().deleteAtcMergeAfterComInfo(
					atcMergeAfterComInfo);
			billList.setMaterialNum(Integer.valueOf(this.getDzscListDao()
					.getAtcMergeAfterCommonNum(billList)));
			this.getDzscListDao().saveApplyToCustomsBillList(billList);
		} else {
			statMergeAfterCommInfo(atcMergeAfterComInfo);
		}
		return billList;
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param beforeComInfo
	 *            报关清单归并前商品信息
	 */
	public void saveAtcMergeBeforeComInfo(
			DzscBillListBeforeCommInfo beforeComInfo) {
		this.getDzscListDao().saveAtcMergeBeforeComInfo(beforeComInfo);
		DzscBillListAfterCommInfo atcMergeAfterComInfo = beforeComInfo
				.getAfterComInfo();
		statMergeAfterCommInfo(atcMergeAfterComInfo);
	}

	/**
	 * 统计报关清单归并后商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            报关清单归并后商品信息
	 */
	private void statMergeAfterCommInfo(
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		Hashtable ht = getMergeBeforeAmountByMergeAfter(atcMergeAfterComInfo);
		atcMergeAfterComInfo.setDeclaredAmount(Double.valueOf(ht.get(
				"declaredAmount").toString()));
		atcMergeAfterComInfo.setLegalAmount(Double.valueOf(ht
				.get("legalAmount").toString()));
		atcMergeAfterComInfo.setSecondLegalAmount(Double.valueOf(ht.get(
				"secondLegalAmount").toString()));
		atcMergeAfterComInfo.setGrossWeight(Double.valueOf(ht
				.get("grossWeight").toString()));
		atcMergeAfterComInfo.setNetWeight(Double.valueOf(ht.get("netWeight")
				.toString()));
		this.getDzscListDao().saveAtcMergeAfterComInfo(atcMergeAfterComInfo);
	}

	/**
	 * 将报关清单归并前商品信息的申报数量,法定数量...等进行统计
	 * 
	 * @param atcMergeAfterComInfo
	 *            报关清单归并前商品信息
	 * @return Hashtable 存储了报关清单归并前商品信息的申报数量,法定数量...等进行统计
	 */
	private Hashtable getMergeBeforeAmountByMergeAfter(
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		Hashtable ht = new Hashtable();
		DzscBillListBeforeCommInfo atcMergeBeforeComInfo = null;
		List list = this.getDzscListDao().findAtcMergeBeforeComInfoByAfterID(
				atcMergeAfterComInfo);
		double declaredAmount = 0;// 申报数量
		double legalAmount = 0;// 法定数量
		double secondLegalAmount = 0;// 第二法定数量
		double grossWeight = 0;// 毛重
		double netWeight = 0;// 净重
		for (int i = 0; i < list.size(); i++) {
			atcMergeBeforeComInfo = (DzscBillListBeforeCommInfo) list.get(i);
			if (atcMergeBeforeComInfo.getDeclaredAmount() != null) {
				declaredAmount += atcMergeBeforeComInfo.getDeclaredAmount()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getLegalAmount() != null) {
				legalAmount += atcMergeBeforeComInfo.getLegalAmount()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getSecondLegalAmount() != null) {
				secondLegalAmount += atcMergeBeforeComInfo
						.getSecondLegalAmount().doubleValue();
			}
			if (atcMergeBeforeComInfo.getGrossWeight() != null) {
				grossWeight += atcMergeBeforeComInfo.getGrossWeight()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getNetWeight() != null) {
				netWeight += atcMergeBeforeComInfo.getNetWeight().doubleValue();
			}
		}
		ht.put("declaredAmount", Double.valueOf(declaredAmount));
		ht.put("legalAmount", Double.valueOf(legalAmount));
		ht.put("secondLegalAmount", Double.valueOf(secondLegalAmount));
		ht.put("grossWeight", Double.valueOf(grossWeight));
		ht.put("netWeight", Double.valueOf(netWeight));
		return ht;
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param declaredDataList
	 *            是TempBillListCommInfo型，存放临时的报关清单商品信息
	 * @param billList
	 *            报关清单表头
	 */
	public void saveAtcMergeBeforeComInfo(List declaredDataList,
			DzscCustomsBillList billList) {
		DzscBillListAfterCommInfo atcMergeAfterComInfo = null;
		DzscBillListBeforeCommInfo atcMergeBeforeComInfo = null;
		TempBillListCommInfo commInfoData = null;
		HashMap<String, DzscBillListAfterCommInfo> hmAfter = new HashMap<String, DzscBillListAfterCommInfo>();
		List lsAfter = this.getDzscListDao().findAtcMergeAfterComInfoByListID(
				billList);
		for (int m = 0; m < lsAfter.size(); m++) {
			DzscBillListAfterCommInfo afterCommInfo = (DzscBillListAfterCommInfo) lsAfter
					.get(m);
			hmAfter.put(afterCommInfo.getEmsSerialNo()
					+ afterCommInfo.getComplex().getCode(), afterCommInfo);
		}
		for (int i = 0; i < declaredDataList.size(); i++) {
			commInfoData = (TempBillListCommInfo) declaredDataList.get(i);
			atcMergeAfterComInfo = (DzscBillListAfterCommInfo) hmAfter
					.get(commInfoData.getEmsSerialNo()
							+ commInfoData.getComplex().getCode());
			if (atcMergeAfterComInfo != null) {
				billList = atcMergeAfterComInfo.getBillList();
			} else {
				atcMergeAfterComInfo = new DzscBillListAfterCommInfo();
				atcMergeAfterComInfo.setEmsSerialNo(Integer
						.parseInt(commInfoData.getEmsSerialNo().trim()));
				atcMergeAfterComInfo.setComplex(commInfoData.getComplex());
				atcMergeAfterComInfo.setComplexName(commInfoData.getName());
				atcMergeAfterComInfo.setComplexSpec(commInfoData.getSpec());
				atcMergeAfterComInfo.setBillList(billList);
				if (commInfoData.getUnit() != null) {
					atcMergeAfterComInfo.setUnit(commInfoData.getUnit());
				}
				// if (commInfoData.getLegalUnit() != null) {
				// atcMergeAfterComInfo.setLegalUnit(commInfoData
				// .getLegalUnit());
				// }
				// if (commInfoData.getLegalUnit2() != null) {
				// atcMergeAfterComInfo.setLegalUnit(commInfoData
				// .getLegalUnit2());
				// }
				this.getDzscListDao().saveAtcMergeAfterComInfo(
						atcMergeAfterComInfo);
				hmAfter.put(atcMergeAfterComInfo.getEmsSerialNo()
						+ atcMergeAfterComInfo.getComplex().getCode(),
						atcMergeAfterComInfo);
				if (hmAfter.keySet().size() > 20) {
					throw new RuntimeException("报关清单归并后的数据不允许超出20笔");
				}
			}

			atcMergeBeforeComInfo = new DzscBillListBeforeCommInfo();
			int maxNo = this.dzscListDao
					.findAtcMergeBeforeComInfoMaxNo(atcMergeAfterComInfo
							.getBillList());
			atcMergeBeforeComInfo.setNo(maxNo + 1);
			atcMergeBeforeComInfo.setMateriel(commInfoData.getMateriel());
			atcMergeBeforeComInfo.setAfterComInfo(atcMergeAfterComInfo);
			atcMergeBeforeComInfo.setBomVersion(0);
			this.getDzscListDao().saveAtcMergeBeforeComInfo(
					atcMergeBeforeComInfo);
			ApplyCustomBillParameter a = this.getDzscListDao()
					.findApplyCustomBillParameter();
			if (a != null) {
				if (atcMergeBeforeComInfo.getCurrency() == null) {
					atcMergeBeforeComInfo.setCurrency(a.getCurr());
				}
				if (atcMergeBeforeComInfo.getSalesCountry() == null) {
					atcMergeBeforeComInfo.setSalesCountry(a.getCountry());
				}
				if (atcMergeBeforeComInfo.getUsesCode() == null) {
					atcMergeBeforeComInfo.setUsesCode(a.getUses());
				}
				if (atcMergeBeforeComInfo.getLevyMode() == null) {
					atcMergeBeforeComInfo.setLevyMode(a.getLevyMode());
				}
			}
		}
		billList.setMaterialNum(Integer.valueOf(this.getDzscListDao()
				.getAtcMergeAfterCommonNum(billList)));
		this.getDzscListDao().saveApplyToCustomsBillList(billList);
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @param billList
	 *            报关清单表头
	 * @param materielProductFlag
	 *            物料标识
	 * @return List 存储了TempBillListCommInfo数据
	 */
	public List getTempDeclaredCommInfo(DzscCustomsBillList billList,
			Integer materielProductFlag) {
		List result = new ArrayList();
		if (materielProductFlag == null ? true : materielProductFlag.toString()
				.equals(MaterielType.MATERIEL)) {
			List list = new ArrayList();
			list = this.getDzscListDao().findDeclaredMaterielInfo(billList);
			// list=this.getDzscListDao().findTempCommInfoFromInnerMergeForAtc(
			// false, billList);
			convertArrayToList(list, result, MaterielType.MATERIEL);
			// // 如果此料件在通关备案的归并料件中不存的话，就去掉
			// for (int i = result.size() - 1; i >= 0; i--) {
			// TempBillListCommInfo tempCommInfo = (TempBillListCommInfo) result
			// .get(i);
			// if (this.dzscDao.findDzscEmsPorMaterialImg(
			// billList.getEmsHeadH2k(), tempCommInfo.getMateriel())
			// .size() <= 0) {
			// result.remove(i);
			// }
			// }
		} else {
			List list = new ArrayList();
			list = this.getDzscListDao().findDeclaredProductInfo(billList);
			// list=this.getDzscListDao().findTempCommInfoFromInnerMergeForAtc(
			// true, billList);
			convertArrayToList(list, result, MaterielType.FINISHED_PRODUCT);
			// // 如果此料件在通关备案的归并成品中不存的话，就去掉
			// for (int i = result.size() - 1; i >= 0; i--) {
			// TempBillListCommInfo tempCommInfo = (TempBillListCommInfo) result
			// .get(i);
			// if (this.dzscDao.findDzscEmsPorMaterialExg(
			// billList.getEmsHeadH2k(), tempCommInfo.getMateriel())
			// .size() <= 0) {
			// result.remove(i);
			// }
			// }
		}
		return result;
	}

	/**
	 * 将数组转换成List
	 * 
	 * @param source
	 *            要被转换为数组的List
	 * @param dest
	 *            要来存储TempBillListCommInfo数据的List
	 * @param materielType
	 *            物料类别
	 */
	private void convertArrayToList(List source, List dest, String materielType) {
		TempBillListCommInfo commInfo = null;
		for (int i = 0; i < source.size(); i++) {
			commInfo = new TempBillListCommInfo();
			Object[] objs = (Object[]) source.get(i);
			// commInfo.setIsPutOnRecord(new Boolean(true));
			if (objs[0] != null) {
				commInfo.setEmsSerialNo(objs[0].toString());
			}
			if (objs[1] != null) {
				commInfo.setMateriel((Materiel) objs[1]);
			}
			if (objs[2] != null) {
				commInfo.setComplex(this.getDzscListDao().findComplex(
						objs[2].toString()));
			}
			if (objs[3] != null) {
				commInfo.setUnit((Unit) objs[3]);
			}
			if (objs[4] != null) {
				commInfo.setLegalUnit((Unit) objs[4]);
			}
			if (objs[5] != null) {
				commInfo.setLegalUnit2((Unit) objs[5]);
			}
			if (objs[6] != null) {
				commInfo.setName(objs[6].toString());
			}
			if (objs[7] != null) {
				commInfo.setSpec(objs[7].toString());
			}
			commInfo.setMaterielType(materielType);
			dest.add(commInfo);
		}
	}

	/**
	 * 报关清单归并前商品信息对象来自ptNo
	 * 
	 * @param ptNo
	 *            料号
	 * @param atcMergeBeforeComInfoList
	 *            报关清单归并前商品信息
	 * @return DzscBillListBeforeCommInfo 当两个参数的ptNo相等是返回报关清单归并前商品信息，否则返回Null
	 */
	private DzscBillListBeforeCommInfo getAtcMergeBeforeComInfoByPtNo(
			String ptNo, List atcMergeBeforeComInfoList) {
		for (int i = 0; i < atcMergeBeforeComInfoList.size(); i++) {
			DzscBillListBeforeCommInfo a = (DzscBillListBeforeCommInfo) atcMergeBeforeComInfoList
					.get(i);
			if (a.getMateriel().getPtNo().trim().toLowerCase().equals(
					ptNo.trim().toLowerCase())) {
				return a;
			}
		}
		return null;
	}

	// /**
	// * 进出口申请单据生成报关清单时,修改进出口单据的生成关封申请单字段为true
	// */
	// private List getImpExpRequestBillInfo(List dataSource) {
	// List impExpRequestBillList = new ArrayList();
	// Set set = new HashSet();
	// for (int i = 0; i < dataSource.size(); i++) {
	// TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
	// .get(i);
	// set.add(t.getImpExpCommodityInfo().getImpExpRequestBill());
	// }
	// //
	// // 修改结转单据的已转关封申请单字段
	// //
	// Iterator iterator = set.iterator();
	// while (iterator.hasNext()) {
	// ImpExpRequestBill t = (ImpExpRequestBill) iterator.next();
	// t.setIsCustomsBill(new Boolean(true));
	// this.getDzscListDao().saveImpExpRequestBill(t);
	// impExpRequestBillList.add(t);
	// }
	// //
	// // 返回已生成关封申请单据的结转单据列表
	// //
	// return impExpRequestBillList;
	// }

	/**
	 * 回写进出口申请单--->并修改是否生成报关清单单为false
	 * 
	 * @param applyToCustomsBillId
	 *            清单归并前商品信息Id
	 * 
	 */
	public void writeBackImpExpRequestBillByApplyToCustomsBillId(
			String applyToCustomsBillId) {
		List dataSource = this
				.getDzscListDao()
				.findDzscMakeApplyToCustomsByBeforeComInfo(applyToCustomsBillId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			DzscMakeApplyToCustoms m = (DzscMakeApplyToCustoms) dataSource
					.get(i);
			/**
			 * 修改进出口商品信息是否转报关请单,为false
			 */
			ImpExpCommodityInfo t = m.getImpExpCommodityInfo();
			t.setIsTransferCustomsBill(new Boolean(false));
			List list = new ArrayList();
			list.add(t);
			this.getEncDao().saveImpExpCommodityInfo(list);
			/**
			 * 修改结转单据是否转清单为真
			 */
			list = this.getEncDao().findImpExpCommodityInfo(
					t.getImpExpRequestBill().getId());
			boolean isModify = this
					.validateImpExpCommodityInfoAllDataIsFalse(list);
			if (list.size() > 0) {
				ImpExpRequestBill impExpRequestBill = t.getImpExpRequestBill();
				impExpRequestBill.setIsCustomsBill(new Boolean(false));
				/**
				 * 如果清单全部删除的话，将清单号清空
				 */
				if (isModify) {
					impExpRequestBill.setAllBillNo("");
				} else {
					String listNo = m.getAtcMergeBeforeComInfo()
							.getAfterComInfo().getBillList().getListNo();
					if (listNo != null && !"".equals(listNo)) {
						String allBillNo = impExpRequestBill.getAllBillNo();
						if (allBillNo != null && !"".equals(allBillNo)) {
							allBillNo.replaceAll(listNo, "");
							impExpRequestBill.setAllBillNo(allBillNo);
						}
					}
				}
				Integer toCustomBillCount = impExpRequestBill
						.getToCustomCount();
				if (toCustomBillCount != null && toCustomBillCount > 0) {
					impExpRequestBill.setToCustomCount(toCustomBillCount - 1);
				}
				this.getEncDao().saveImpExpRequestBill(impExpRequestBill);
			}

			/**
			 * 删除中间表信息
			 */
			this.getDzscListDao().deleteDzscMakeApplyToCustoms(m);
		}
	}

	/**
	 * 验证所有进出口商品信息是否都转报关清单
	 * 
	 * @param list
	 *            是ImpExpCommodityInfo型，进出口申请单物料信息
	 * @return boolean 当全部转换时为false，否则为true
	 */
	private boolean validateImpExpCommodityInfoAllDataIsFalse(List list) {
		boolean isFlag = true;
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo t = (ImpExpCommodityInfo) list.get(i);
			if (t.getIsTransferCustomsBill() != null) {
				if (t.getIsTransferCustomsBill() != null
						&& t.getIsTransferCustomsBill()) {
					isFlag = false;
					break;
				}
			}
		}
		return isFlag;
	}

	/**
	 * 报关清单生成报关单的基本信息 (表头信息)
	 * 
	 * @param billList
	 *            报关清单表头
	 * @param param
	 *            报关清单自动生成报关单时的参数
	 * @param listSerialNumber
	 *            生成的报关单流水号
	 * @return DzscCustomsDeclaration 报关单表头
	 */
	private DzscCustomsDeclaration makeCustomsDeclaration(
			DzscCustomsBillList billList, MakeCusomsDeclarationParam param,
			List listSerialNumber) {
		// EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsEdiTrDao
		// .findEmsHeadH2kInExecuting().get(0);
		List list = dzscDao.findDzscEmsPorHeadExcu();
		DzscEmsPorHead dzscEmsPorHead = null;
		for (int i = 0; i < list.size(); i++) {
			if (billList.getEmsHeadH2k().equals(
					((DzscEmsPorHead) list.get(i)).getEmsNo()))
				dzscEmsPorHead = (DzscEmsPorHead) list.get(i);
		}
		DzscCustomsDeclaration customsDeclaration = new DzscCustomsDeclaration();
		customsDeclaration.setEmsHeadH2k(dzscEmsPorHead.getEmsNo());
		customsDeclaration.setTradeCode(dzscEmsPorHead.getTradeCode());
		customsDeclaration.setTradeName(dzscEmsPorHead.getTradeName());

		customsDeclaration.setMachCode(dzscEmsPorHead.getTradeCode());
		customsDeclaration.setMachName(dzscEmsPorHead.getMachName());
		customsDeclaration.setContract(dzscEmsPorHead.getIeContractNo());

		// customsDeclaration.setBillListId(billList.getId());
		customsDeclaration.setBillListId(billList.getListNo());
		Integer serialNumber = null;
		// if (!param.isSpecialCustomsDeclaration()) {
		serialNumber = this.getDzscContractExeDao()
				.getCustomsDeclarationSerialNumber(
						billList.getImpExpFlag().intValue(),
						dzscEmsPorHead.getEmsNo());
		if (billList.getImpExpFlag().intValue() == ImpExpFlag.IMPORT) {
			listSerialNumber.add("进口报关单流水号：" + serialNumber);
		} else {
			listSerialNumber.add("出口报关单流水号：" + serialNumber);
		}
		// } else {
		// serialNumber = this.getDzscContractExeDao()
		// .getCustomsDeclarationSerialNumber(ImpExpFlag.SPECIAL,
		// dzscEmsPorHead.getEmsNo());
		// listSerialNumber.add("特殊报关单流水号：" + serialNumber);
		// }
		customsDeclaration.setSerialNumber(serialNumber);
		customsDeclaration.setCompany(CommonUtils.getCompany());
		// customsDeclaration.setManageCompany(getBrief(((Company) CommonUtils
		// .getCompany()).getBuCode()));
		// customsDeclaration
		// .setAcceptGoodsCompany(getBrief(((Company) CommonUtils
		// .getCompany()).getBuCode()));
		// customsDeclaration
		// .setDeclarationCompany(getBrief(((Company) CommonUtils
		// .getCompany()).getCode()));
		// customsDeclaration.setManufacturer(getBrief(((Company) CommonUtils
		// .getCompany()).getCode()));
		customsDeclaration.setCreater(CommonUtils.getRequest().getUser());
		customsDeclaration.setImpExpType(billList.getImpExpType());
		// if (!param.isSpecialCustomsDeclaration()) {
		customsDeclaration.setImpExpFlag(billList.getImpExpFlag());
		// } else {
		// customsDeclaration.setImpExpFlag(Integer
		// .valueOf(ImpExpFlag.SPECIAL));
		// }
		customsDeclaration.setImpExpDate(param.getImpExpDate());
		customsDeclaration.setDeclarationDate(param.getDelcarationDate());
		customsDeclaration.setBillOfLading(param.getBillOfLading());
		customsDeclaration.setLevyKind(param.getLevyKind());
		customsDeclaration.setPerTax(param.getPerTax());
		customsDeclaration.setLicense(param.getLicense());
		customsDeclaration.setCountryOfLoadingOrUnloading(param
				.getCountryOfLoadingOrUnloading());
		customsDeclaration.setDomesticDestinationOrSource(param
				.getDomesticDestinationOrSource());
		customsDeclaration.setPortOfLoadingOrUnloading(param
				.getPortOfLoadingOrUnloading());
		customsDeclaration.setTransac(param.getTransac());
		customsDeclaration.setWrapType(param.getWrapType());
		customsDeclaration.setCustomer(param.getCustomer());
		this.getDzscContractExeDao().saveCustomsDeclaration(customsDeclaration);
		return customsDeclaration;
	}

	/**
	 * 在根据报关清单自动生成报关单的时候，检查报关清单的数据是否符合条件
	 * 
	 * @param list
	 * @return int 如果成功返回0
	 */
	public int checkBillListForMakeCustomsDeclaration(List list) {
		int result = 0;
		return result;
	}

	/**
	 * 报关清单自动转报关单及其商品信息
	 * 
	 * @param billLists
	 *            是DzscCustomsBillList型，报关清单表头
	 * @param param
	 *            报关清单自动生成报关单时的参数
	 * @return List 存放了报关单的流水号
	 */
	public List makeCusomsDeclarationFromBillList(List billLists,
			MakeCusomsDeclarationParam param) {
		DzscCustomsBillList billList = null;
		DzscCustomsDeclaration customsDeclaration = null;
		DzscCustomsDeclarationCommInfo commInfo = null;
		DzscBillListAfterCommInfo mergeAfterComInfo = null;

		List listSerialNumber = new ArrayList();
		for (int i = 0; i < billLists.size(); i++) {
			billList = (DzscCustomsBillList) billLists.get(i); // 报关清单
			billList.setIsGenerateDeclaration(true);// 清单状态：已转报关单
			this.getDzscListDao().saveApplyToCustomsBillList(billList);
			customsDeclaration = makeCustomsDeclaration(billList, param, // 添加到报关单头
					listSerialNumber);
			List list = findAtcMergeAfterComInfoByListID(billList);
			Integer flag = billList.getImpExpFlag();
			list = sortListByCheckupComplexForDzscBillListAfterCommInfo(list,
					flag);
			for (int j = 0; j < list.size(); j++) {
				if ((j != 0) && ((j + 1) % 20 == 0)) {
					customsDeclaration = makeCustomsDeclaration(billList,
							param, listSerialNumber);
				}
				mergeAfterComInfo = (DzscBillListAfterCommInfo) list.get(j);
				commInfo = new DzscCustomsDeclarationCommInfo();
				commInfo.setBaseCustomsDeclaration(customsDeclaration);
				commInfo
						.setSerialNumber(this
								.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
				commInfo.setCommSerialNo(mergeAfterComInfo.getEmsSerialNo());
				commInfo.setComplex(mergeAfterComInfo.getComplex());
				commInfo.setCommName(mergeAfterComInfo.getComplexName());
				commInfo.setCommSpec(mergeAfterComInfo.getComplexSpec());
				commInfo.setUnit(mergeAfterComInfo.getUnit());
				commInfo.setCommAmount(mergeAfterComInfo.getDeclaredAmount());
				commInfo.setLegalUnit(mergeAfterComInfo.getComplex()
						.getFirstUnit());
				commInfo.setFirstAmount(mergeAfterComInfo.getLegalAmount());
				commInfo.setSecondLegalUnit(mergeAfterComInfo.getComplex()
						.getSecondUnit());
				commInfo.setSecondAmount(mergeAfterComInfo
						.getSecondLegalAmount());
				commInfo.setGrossWeight(mergeAfterComInfo.getGrossWeight());
				commInfo.setNetWeight(mergeAfterComInfo.getNetWeight());
				this.getDzscContractExeDao().saveCustomsDeclarationCommInfo(
						commInfo);
			}
		}
		return listSerialNumber;
	}

	/**
	 * 报关清单归并后信息按手册序号排序
	 * @param datalist
	 * @param flag
	 * @return
	 */
	public List sortListByCheckupComplexForDzscBillListAfterCommInfo(
			List datalist, Integer flag) {
		if (datalist == null) {
			return new ArrayList();
		}
		List<String> codelist = this.getDzscContractExeDao().findComplexByFlag(
				flag);
		List yesList = new ArrayList();
		List noList = new ArrayList();
		List relist = new ArrayList();
		for (int i = 0; i < datalist.size(); i++) {
			DzscBillListAfterCommInfo tempCustomsList = (DzscBillListAfterCommInfo) datalist
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
		Collections.sort(yesList, new Comparator<DzscBillListAfterCommInfo>() {
			public int compare(DzscBillListAfterCommInfo o1,
					DzscBillListAfterCommInfo o2) {
				if (o1 == null || o1.getEmsSerialNo() == null) {
					return -1;
				}
				if (o2 == null || o2.getEmsSerialNo() == null) {
					return 1;
				}
				if (o1.getEmsSerialNo() > o2.getEmsSerialNo()) {
					return 1;
				} else if (o1.getEmsSerialNo() == o2.getEmsSerialNo()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		Collections.sort(noList, new Comparator<DzscBillListAfterCommInfo>() {
			public int compare(DzscBillListAfterCommInfo o1,
					DzscBillListAfterCommInfo o2) {
				if (o1 == null || o1.getEmsSerialNo() == null) {
					return -1;
				}
				if (o2 == null || o2.getEmsSerialNo() == null) {
					return 1;
				}
				if (o1.getEmsSerialNo() > o2.getEmsSerialNo()) {
					return 1;
				} else if (o1.getEmsSerialNo() == o2.getEmsSerialNo()) {
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
	 * 取得报关单商品流水号
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 * @return Integer 报关单商品流水号
	 */
	public Integer getCustomsDeclarationCommInfoSerialNumber(
			BaseCustomsDeclaration customsDeclaration) {
		return this.dzscContractExeDao
				.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration
						.getId());

	}

	// /**
	// * 根据报关清单取得清单和报关单商品信息的差异
	// *
	// * @param billList
	// * 报关清单
	// * @return
	// */
	// public List findDiffrenceAnalyseCommInfo(ApplyToCustomsBillList billList)
	// {
	// List result = new ArrayList();
	// DiffrenceAnalyseCommInfo diffrenceCommInfo = null;
	// AtcMergeAfterComInfo mergeAfterCommInfo = null;
	// CustomsDeclarationCommInfo declarationCommInfo = null;
	// Object[] objs = null;
	// List list = this.getDzscListDao().findDiffrenceAnalyseCommInfo(billList);
	// for (int i = 0; i < list.size(); i++) {
	// objs = (Object[]) list.get(i);
	// mergeAfterCommInfo = (AtcMergeAfterComInfo) objs[0];
	// declarationCommInfo = (CustomsDeclarationCommInfo) objs[1];
	// diffrenceCommInfo = new DiffrenceAnalyseCommInfo();
	// if (mergeAfterCommInfo != null) {
	// diffrenceCommInfo.setCommSerialNo(Integer
	// .valueOf(mergeAfterCommInfo.getEmsSerialNo()));
	// diffrenceCommInfo.setComplex(mergeAfterCommInfo.getComplex());
	// diffrenceCommInfo.setCommName(mergeAfterCommInfo
	// .getComplexName());
	// diffrenceCommInfo.setCommSpec(mergeAfterCommInfo
	// .getComplexSpec());
	// diffrenceCommInfo.setUnit(mergeAfterCommInfo.getUnit());
	// diffrenceCommInfo.setAfterMergeAmount(mergeAfterCommInfo
	// .getDeclaredAmount());
	// }
	// if (declarationCommInfo != null) {
	// diffrenceCommInfo
	// .setCustomsDelarationAmount(declarationCommInfo
	// .getCommAmount());
	// }
	// double afterMergeAmount = 0;
	// double customsDelarationAmount = 0;
	// if (diffrenceCommInfo.getAfterMergeAmount() != null) {
	// afterMergeAmount = diffrenceCommInfo.getAfterMergeAmount()
	// .doubleValue();
	// }
	// if (diffrenceCommInfo.getCustomsDelarationAmount() != null) {
	// customsDelarationAmount = diffrenceCommInfo
	// .getCustomsDelarationAmount().doubleValue();
	// }
	// diffrenceCommInfo.setDiffrenceAmount(new Double(afterMergeAmount
	// - customsDelarationAmount));
	// result.add(diffrenceCommInfo);
	// }
	// return result;
	// }

	// /**
	// * 获得成品料件标识（是成品还是料件）
	// *
	// * @param billType
	// * 海关单据类型
	// * @param billCode
	// * 海关单据类型编号
	// * @return Integer 成品料件标识（是成品还是料件）
	// */
	// private Integer getMaterielProductFlag(int billType, String billCode) {
	// int flag = -1;
	// if (billType == SBillType.MATERIEL_IN) {// 1、 料件入仓
	// if (billCode.equalsIgnoreCase("1003")) {// 直接进口：1003-----料件进口
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// } else if (billCode.equalsIgnoreCase("1011")) {// 海关征税进口：1011-----一般性贸易进口
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// } else if (billCode.equalsIgnoreCase("1004")) {// 1104----料件转厂
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// }
	// } else if (billType == SBillType.MATERIEL_OUT) {// 2、 料件出仓
	// if (billCode.equalsIgnoreCase("1102")) {// 料件退回：1102---料件出口
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// } else if (billCode.equalsIgnoreCase("1103")) {// 料件复出：1105----料件转厂
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// }
	// } else if (billType == SBillType.PRODUCE_IN) {// 成品入库
	// if (billCode.equalsIgnoreCase("2003")) {// 退厂返工：2003-----退厂返工
	// flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
	// }
	// } else if (billType == SBillType.PRODUCE_OUT) {// 成品出库
	// if (billCode.equalsIgnoreCase("2101")) {// 直接出口：2101-----成品出口
	// flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
	// } else if (billCode.equalsIgnoreCase("2102")) {// 结转出口：2102-----转厂出口
	// flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
	// } else if (billCode.equalsIgnoreCase("2104")) {// 返工复出：2104---返工复出
	// flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
	// }
	// } else if (billType == SBillType.LEFTOVER_MATERIEL_OUT) {// 边角料出仓
	//
	// if (billCode.equalsIgnoreCase("6102")) {// 边角料退运出口：6102--边角料退港
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// } else if (billCode.equalsIgnoreCase("6104")) {// 边角料征税内销：6104---边角料内销
	// flag = Integer.parseInt(MaterielType.MATERIEL);
	// }
	// }
	// return new Integer(flag);
	// }

	// /**
	// * 获得进出口申请单据类型
	// *
	// * @param billType
	// * 海关单据类型
	// * @param billCode
	// * 海关单据类型编号
	// * @return Integer 进出口申请单据类型
	// */
	// private Integer getImpExpRequestBillType(int billType, String billCode) {
	// int flag = -1;
	// if (billType == SBillType.MATERIEL_IN) {// 1、 料件入仓
	// if (billCode.equalsIgnoreCase("1003")) {// 直接进口：1003-----料件进口
	// flag = ImpExpType.DIRECT_IMPORT;
	// } else if (billCode.equalsIgnoreCase("1011")) {// 海关征税进口：1011-----一般性贸易进口
	// flag = ImpExpType.GENERAL_TRADE_IMPORT;
	// }
	// } else if (billType == SBillType.MATERIEL_OUT) {// 2、 料件出仓
	// if (billCode.equalsIgnoreCase("1102")) {// 料件退回：1102---料件出口
	// flag = ImpExpType.BACK_MATERIEL_EXPORT;
	// } else if (billCode.equalsIgnoreCase("1105")) {// 料件转仓出库：1105----料件转厂
	// flag = ImpExpType.TRANSFER_FACTORY_IMPORT;
	// }
	// } else if (billType == SBillType.PRODUCE_IN) {// //成品入库
	// if (billCode.equalsIgnoreCase("2003")) {// 退厂返工：2003-----退厂返工
	// flag = ImpExpType.BACK_FACTORY_REWORK;
	// }
	// } else if (billType == SBillType.PRODUCE_OUT) {// //成品出库
	// if (billCode.equalsIgnoreCase("2101")) {// 直接出口：2101-----成品出口
	// flag = ImpExpType.DIRECT_EXPORT;
	// } else if (billCode.equalsIgnoreCase("2102")) {// 结转出口：2102-----转厂出口
	// flag = ImpExpType.TRANSFER_FACTORY_EXPORT;
	// } else if (billCode.equalsIgnoreCase("2104")) {// 返工复出：2104---返工复出
	// flag = ImpExpType.REWORK_EXPORT;
	// }
	// } else if (billType == SBillType.LEFTOVER_MATERIEL_OUT) {// //9、
	// // 边角料出仓
	// if (billCode.equalsIgnoreCase("6102")) {// 边角料退运出口：6102--边角料退港
	// flag = ImpExpType.REMIAN_MATERIAL_BACK_PORT;
	// } else if (billCode.equalsIgnoreCase("6104")) {// 边角料征税内销：6104---边角料内销
	// flag = ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES;
	// }
	// }
	// return new Integer(flag);
	// }

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPBillListMessage(String taskId, String messageFileName,
			DzscCustomsBillList dzscCustomsBillList) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要要导出的资料");
		}
		List list = this.dzscListDao
				.findAtcMergeBeforeComInfoByListID(dzscCustomsBillList);
		System.out.println("Begin get data:" + System.currentTimeMillis());
		String formatFile = "BillListFormat.xml";
		Map<String, List> hmData = new HashMap<String, List>();
		hmData.put("BillListId", list);
		// dzscMessageLogic.exportQPMessage(messageFileName,
		// BusinessType.CUSTOMS_BILL_LIST, formatFile, hmData, info);
	}

	/**
	 * 获取dzscListDao
	 * 
	 * @return dzscListDao
	 */
	public DzscListDao getDzscListDao() {
		return dzscListDao;
	}

	/**
	 * 设置dzscListDao
	 * 
	 * @param dzscListDao
	 */
	public void setDzscListDao(DzscListDao dzscListDao) {
		this.dzscListDao = dzscListDao;
	}

	/**
	 * 获取contractExeDao
	 * 
	 * @return dzscContractExeDao
	 */
	public DzscContractExeDao getDzscContractExeDao() {
		return dzscContractExeDao;
	}

	/**
	 * 设置contractExeDao
	 * 
	 * @param contractExeDao
	 */
	public void setDzscContractExeDao(DzscContractExeDao contractExeDao) {
		this.dzscContractExeDao = contractExeDao;
	}

	/**
	 * 获取createDirDao
	 * 
	 * @return createDirDao
	 */
	public CreateDirDao getCreateDirDao() {
		return createDirDao;
	}

	/**
	 * 设置createDirDao
	 * 
	 * @param createDirDao
	 */
	public void setCreateDirDao(CreateDirDao createDirDao) {
		this.createDirDao = createDirDao;
	}

	/**
	 * 获取encDao
	 * 
	 * @return encDao
	 */
	public EncDao getEncDao() {
		return encDao;
	}

	/**
	 * 设置encDao
	 * 
	 * @param encDao
	 */
	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}

	/**
	 * 获取dzscDao
	 * 
	 * @return dzscDao
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
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
	 * 获取转厂管理的Logic
	 */
	public FptManageLogic getFptManageLogic() {
		return fptManageLogic;
	}

	/**
	 * 设置转厂管理的Logic
	 * @param transferFactoryManageLogic
	 */
	public void setFptManageLogic(
			FptManageLogic transferFactoryManageLogic) {
		this.fptManageLogic = transferFactoryManageLogic;
	}
	
	/**
	 * 获取报文操作dao
	 * @return
	 */
	public DzscMessageLogic getDzscMessageLogic() {
		return dzscMessageLogic;
	}

	/**
	 * 设置报文操作dao
	 * @param dzscExportMessageLogic
	 */
	public void setDzscMessageLogic(DzscMessageLogic dzscExportMessageLogic) {
		this.dzscMessageLogic = dzscExportMessageLogic;
	}
}