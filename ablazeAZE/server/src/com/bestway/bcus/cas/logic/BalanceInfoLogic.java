/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.dao.CasLeftoverMaterielDao;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.TempDeliveryToPlant;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 工厂单据表维护 checked 2008-09-14
 * 
 * @author Administrator 海关帐平衡表计算logic
 */
@SuppressWarnings("unchecked")
public class BalanceInfoLogic {
	private static final Log logger = LogFactory.getLog(BalanceInfoLogic.class);
	/** 海关帐Dao */
	private CasDao casDao = null;
	/** 边角料Dao */
	private CasLeftoverMaterielDao casLeftoverMaterielDao = null;
	/** 物料管理Dao */
	private MaterialManageDao materialManageDao = null;

	// /** 料件年审报表LOGIC --add by sls */
	// private ImgOrgUseInfoLogic imgOrgUseInfoLogic = null;

	/** 取得边角料Dao */
	public CasLeftoverMaterielDao getCasLeftoverMaterielDao() {
		return casLeftoverMaterielDao;
	}

	/** 取得海关帐Dao */
	public CasDao getCasDao() {
		return casDao;
	}

	/** 取得物料管理Dao */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/** 设计物料管理Dao */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 设计海关帐Dao
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/** 设计边角料Dao */
	public void setCasLeftoverMaterielDao(
			CasLeftoverMaterielDao casLeftoverMaterielDao) {
		this.casLeftoverMaterielDao = casLeftoverMaterielDao;
	}

	/** 为了每个公司只能有一个方法在同步运行,并且.. key = company id */
	private Map<String, Boolean> runMap = new Hashtable<String, Boolean>();

	/** 参数Map类 */
	static class ParameterMaps {
		// /////////////////////////////////////////////////////////
		// 所有成品单据 用于 生成对应的料件之用 key = ptNo 对应的折算数量
		// /////////////////////////////////////////////////////////
		/** 2001成品起初单 */
		Map<String, Double> billDetail2001Map = new HashMap<String, Double>();

		/** 2002车间入库 */
		Map<String, Double> billDetail2002Map = new HashMap<String, Double>();

		/** 2007受托加工车间入库 */
		Map<String, Double> billDetail2007Map = new HashMap<String, Double>();

		/** 2009结转成品退货单 */
		Map<String, Double> billDetail2009Map = new HashMap<String, Double>();

		/** 2010受托加工退回成品 */
		Map<String, Double> billDetail2010Map = new HashMap<String, Double>();

		/** 2101直接出口 */
		Map<String, Double> billDetail2101Map = new HashMap<String, Double>();

		/** 2102结转出口 */
		Map<String, Double> billDetail2102Map = new HashMap<String, Double>();

		/** 2103返回车间 */
		Map<String, Double> billDetail2103Map = new HashMap<String, Double>();

		/** 盘点表 */
		Hashtable<String, Double> checkHs = new Hashtable<String, Double>();
		/**
		 * 结转成品
		 */
		Map<String, Double> billDetailCpTranMap = new HashMap<String, Double>();

		/** 2106海关批准内销 */
		Map<String, Double> billDetail2106Map = new HashMap<String, Double>();

		/** 半成品单据明细 */
		Map<String, Double> billDetailHalfMap = new HashMap<String, Double>();

		/** 半成品外发统计结果 */
		Map<String, Double> billDetailHalfOutSourceMap = new HashMap<String, Double>();

		/** 2107其他内销 */
		Map<String, Double> billDetail2107Map = new HashMap<String, Double>();

		/** 2110受托加工返回 */
		Map<String, Double> billDetail2110Map = new HashMap<String, Double>();

		/** F26入库成品-出库成品 */
		Map<String, Double> billDetailF26Map = new HashMap<String, Double>();

		/**** key :报关名称+"/"+规格+"/"+单位名称*** value为：对应的对应关系list *****/
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListByMaterielMap = new HashMap<String, List<FactoryAndFactualCustomsRalation>>();

		/** key为：料号 value为：报关名称+报关规格+报关单位（最后一个对应关系的） */
		Map<String, String> ptNoKeyMap = new HashMap<String, String>();

		/** key为：对应关系中的 报关名称+报关规格+报关单位 value为：相同报关名称、报关规格、报关单位的BillTemp */
		Map<String, BillTemp> hsKeyMap = new HashMap<String, BillTemp>();

		/** key为：料号 value为：对应关系(第一个) */
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueByMaterielMap = new HashMap<String, FactoryAndFactualCustomsRalation>();

		/** 2015外发加工成品入库 */
		Map<String, Double> billDetail2015Map = new HashMap<String, Double>();

		/** 2017外发加工成品退回 */
		Map<String, Double> billDetail2017Map = new HashMap<String, Double>();

		/** 2113外发加工成品返修 */
		Map<String, Double> billDetail2113Map = new HashMap<String, Double>();

		/** 2114外发加工成品出库 */
		Map<String, Double> billDetail2114Map = new HashMap<String, Double>();

		/** 4104外设加工返修成品 */
		Map<String, Double> billDetail4104Map = new HashMap<String, Double>();

		/**
		 * 出口成品损耗量映射 key=名称+"/"+规格+"/"+单位 value = 直接出口+结转出口+返工复出-退厂返工料件总耗用量（单项用量
		 * * 数量）
		 */
		Map<String, Double> customsProductMapWaste = new HashMap<String, Double>();

		/**
		 * 合同进口料件映射 key=名称+"/"+规格+"/"+单位 value = （料件进口 + 料件转厂 - 退料出口）总量
		 */
		Map<String, Double> customsMaterialMapAmount = new HashMap<String, Double>();

		/** 合同进口 海关批准内销 */
		Map<String, Double> customsMaterialDOMESTICSALESMapAmount = new HashMap<String, Double>();

		/** key :真实的 报关名称+"/"+规格+"/"+单位名称 */
		Map<String, String> statCusNameRelationHsByProductMap = new HashMap<String, String>();

		/** key = 报关名称+"/"+规格+"/"+单位名称 转厂进口报关数 */
		Map<String, Double> transFactInMapAmount = new HashMap<String, Double>();

		/** key = 报关名称+"/"+规格+"/"+单位名称 转厂出口耗用料件(单项用量 * 数量)报关数 */
		Map<String, Double> transFactOutMapAmount = new HashMap<String, Double>();

		/** key = 报关名称+"/"+规格+"/"+单位名称 送货数量 */
		Map<String, Double> sendMapAmount = new HashMap<String, Double>();

		/** key = 报关名称+"/"+规格+"/"+单位名称 收货货数量 */
		Map<String, Double> recvMapAmount = new HashMap<String, Double>();

		/** 物料与大类的折算系数 */
		Map<String, Double> unitConvertMap = new HashMap<String, Double>();

		/** key = 报关名称+"/"+规格+"/"+单位名称 后续补税 */
		// Map<String, Double> custom9700 = new HashMap<String, Double>();
		//
		// /** key = 报关名称+"/"+规格+"/"+单位名称 进口料件内销 */
		// Map<String, Double> custom0644 = new HashMap<String, Double>();
		/** key = 报关名称+"/"+规格+"/"+单位名称 发票核销数量 */
		Map<String, Double> invoiceVerificationAmountMap = new HashMap<String, Double>();

		/** 以下为计算短溢表（测试版）所有重新建立的临时缓存Map,wss2010.11.16 ****************/

		/**
		 * key为：【料号】 value为：【报关名称+报关规格+报关单位（最后一个对应关系的）】
		 */
		Map<String, String> mapPtNoKey = new HashMap<String, String>();

		/**
		 * key为：【料号】 value为：【对应关系(第一个)】
		 */
		Map<String, FactoryAndFactualCustomsRalation> mapPtNoFFC = new HashMap<String, FactoryAndFactualCustomsRalation>();

		/****
		 * key :【报关名称+"/"+规格+"/"+单位名称】*** value为：【对应的对应关系list】
		 *****/
		Map<String, List<FactoryAndFactualCustomsRalation>> mapKeyFFCList = new HashMap<String, List<FactoryAndFactualCustomsRalation>>();

		/**
		 * key：【报关名称+报关规格+报关单位】 value：【相同报关名称、报关规格、报关单位的BillTemp】
		 */
		Map<String, BillTemp> mapKeyBillTemp = new HashMap<String, BillTemp>();

		/**
		 * 2001成品期初单
		 */
		Map<String, Double> mapBillDetailPtAmount2001 = new HashMap<String, Double>();

		/**
		 * 2002车间入库
		 */
		Map<String, Double> mapBillDetailPtAmount2002 = new HashMap<String, Double>();

		/**
		 * 2007受托加工车间入库
		 */
		Map<String, Double> mapBillDetailPtAmount2007 = new HashMap<String, Double>();

		/**
		 * 2009结转成品退货单
		 */
		Map<String, Double> mapBillDetailPtAmount2009 = new HashMap<String, Double>();

		/**
		 * 2010受托加工退回成品
		 */
		Map<String, Double> mapBillDetailPtAmount2010 = new HashMap<String, Double>();

		/**
		 * 2101直接出口
		 */
		Map<String, Double> mapBillDetailPtAmount2101 = new HashMap<String, Double>();

		/**
		 * 2102结转出口
		 */
		Map<String, Double> mapBillDetailPtAmount2102 = new HashMap<String, Double>();

		/**
		 * 2103返回车间
		 */
		Map<String, Double> mapBillDetailPtAmount2103 = new HashMap<String, Double>();

		/**
		 * 结转成品 2102 结转出口 - 2009 结转成品退货单 - 2011已交货未结转期初单 + 2012已结转未交货期初单
		 */
		Map<String, Double> mapBillDetailPtAmountCpTran = new HashMap<String, Double>(); // 料号，耗用量（成品数量）

		/**
		 * 2106海关批准内销
		 */
		Map<String, Double> mapBillDetailPtAmount2106 = new HashMap<String, Double>();

		/**
		 * 2107其他内销
		 */
		Map<String, Double> mapBillDetailPtAmount2107 = new HashMap<String, Double>();

		/**
		 * 2110受托加工返回
		 */
		Map<String, Double> mapBillDetailPtAmount2110 = new HashMap<String, Double>();

		/**
		 * F26入库成品-出库成品
		 */
		Map<String, Double> mapBillDetailPtAmountF26 = new HashMap<String, Double>();

		/**
		 * 2015外发加工成品入库
		 */
		Map<String, Double> mapBillDetailPtAmount2015 = new HashMap<String, Double>();

		/**
		 * 2017外发加工成品退回
		 */
		Map<String, Double> mapBillDetailPtAmount2017 = new HashMap<String, Double>();

		/**
		 * 2113外发加工成品返修
		 */
		Map<String, Double> mapBillDetailPtAmount2113 = new HashMap<String, Double>();

		/**
		 * 2114外发加工成品出库
		 */
		Map<String, Double> mapBillDetailPtAmount2114 = new HashMap<String, Double>();

		/**
		 * 半成品结余 + 4002半成品盘盈单 + 4004外发加工半成品退 + 4006外发加工半成品入库 - 4102半成品盘亏单 -
		 * 4103委外加工出库 - 4104外发加工半成品返修 - 4106委外加工出库 - 5001残次品入库（半成品部分
		 */
		Map<String, Double> mapBillDetailPtAmountHalf = new HashMap<String, Double>();

		/**
		 * 半成品外发 + 4103委外加工出库 + 4104外发加工半成品返修 + 4106委外加工出库 - 4003委外加工入库 -
		 * 4004外发加工半成品退 - 4006外发加工半成品入库
		 */
		Map<String, Double> mapBillDetailPtAmountHalfOutSource = new HashMap<String, Double>();

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> mapCustomsProductWaste = new HashMap<String, Double>();

		/**
		 * 成品转厂损耗量映射
		 */
		Map<String, Double> mapTransFactOutWaste = new HashMap<String, Double>();

		/**
		 * 合同进口映射
		 */
		Map<String, Double> mapCustomsMaterialAmount = new HashMap<String, Double>();

		// /**
		// * 海关批准内销
		// */
		// Map<String, Double> mapCustomsMaterialDOMESTICSALESAmount = new
		// HashMap<String, Double>();
		/**
		 * 转厂进口映射
		 */
		Map<String, Double> mapTransFactInAmount = new HashMap<String, Double>();

		/**
		 * 外发数量（成品折耗）
		 */
		Map<String, Double> mapSendAmount = new HashMap<String, Double>();

		/**
		 * 料件收货数量
		 */
		Map<String, Double> mapRecvAmount = new HashMap<String, Double>();
		Map<String, String> resultHsNameSpecUnitF6F9Map = new HashMap<String, String>();
		Map<String, Double> beforeResultF6Map = new HashMap<String, Double>();
		/**
		 * 外发数量（成品折耗）7栏
		 */
		Map<String, Double> result7Map = new HashMap<String, Double>();

		Map<String, Double> beforResult7Map = new HashMap<String, Double>();
		Map<String, String> resultHsNameSpecUnitF7F8Map = new HashMap<String, String>();
		/**
		 * 外发数量（成品折耗）8栏
		 */
		Map<String, Double> beforResult8Map = new HashMap<String, Double>();
		Map<String, Double> result8Map = new HashMap<String, Double>();

		/**
		 * 盘点导入（编码级）
		 */
		Map<String, Double> checkBalanceOfCustom = new HashMap<String, Double>();

	}

	/**
	 * 判断是否只选中一种报关模式来计算短溢表
	 * 
	 * @param projectTypeParam
	 * @return
	 */
	private boolean isSingleSelect(ProjectTypeParam projectTypeParam) {
		int i = 0;
		if (projectTypeParam.getIsBcs() != null && projectTypeParam.getIsBcs()) {
			i++;
		}
		if (projectTypeParam.getIsBcus() != null
				&& projectTypeParam.getIsBcus()) {
			i++;
		}
		if (projectTypeParam.getIsDzsc() != null
				&& projectTypeParam.getIsDzsc()) {
			i++;
		}
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            模块类型参数(是来自bcs还是bcus......)
	 * @param isFromCheckData
	 *            是否来源于检查盘点表
	 * @return 短溢表计算结果
	 */
	public List<BalanceInfo> findBalanceInfo(Date beginDate, Date endDate,
			ProjectTypeParam projectTypeParam, String taskId,
			boolean isFromCheckData) throws RuntimeException {

		boolean isSingleSelect = this.isSingleSelect(projectTypeParam);

		String companyId = CommonUtils.getCompany().getId();
		Boolean isRun = runMap.get(companyId);

		if (isRun != null && isRun.booleanValue() == true) {
			throw new RuntimeException("计算短溢表正在进行中请稍后再运行 ！！");
		}
		runMap.put(companyId, true);

		try {
			ParameterMaps parameterMaps = new ParameterMaps();

			// get thread internal message
			ProgressInfo progressInfo = ProcExeProgress.getInstance()
					.getProgressInfo(taskId);
			String clientTipMessage = "计算短溢表 开始查询初始化对应关系... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);
			// this.initStatCusNameRelationHsByMaterielMap(parameterMaps);
			// this.initStatCusNameRelationHsByProductMap(parameterMaps);
			clientTipMessage = "计算短溢表 开始查询实际报关资料对应... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 ***第一步：弄好后面会用到的基础对应**************
			 */
			this.initStatCusNameRelationByMaterielMap(parameterMaps);

			clientTipMessage = "计算短溢表 初始化 成品车间入存,车间返回,并计算其BOM耗用... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 ***第二步：初始化 成品单据 bom 折料*************
			 */
			this.initBillDetailProductMap(endDate, parameterMaps, progressInfo);

			/**
			 ***第三步：初始化 半成品单据 bom 折料***********
			 */
			this.initBillDetailHalfProductMap(endDate, parameterMaps,
					progressInfo);

			/**
			 ***第四步：电子帐册 *****************
			 */
			if (projectTypeParam.getIsBcus() == true) {
				clientTipMessage = "计算短溢表 初始化 BCUS(电子帐册) 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);
				// 1.结转出口成品(报关单) 损耗映射
				this.initCustomsProductMapByBcus(beginDate, endDate,
						parameterMaps, progressInfo);

				// 2.料件进口(报关单) 映射
				this.initCustomsMaterielMapByBcus(beginDate, endDate,
						parameterMaps, progressInfo);

				if (isSingleSelect && !isFromCheckData) {
					// 3.根据单据查询送货数量，将送货数量按照帐册单耗折算成料件。
					this.initSendAmountByBcus(endDate, parameterMaps);
				}

			}

			/**
			 ***第五步：电子化手册*****************
			 */
			if (projectTypeParam.getIsBcs() == true) {
				System.out.println("wss 正在执行的是电子化手册");
				clientTipMessage = "计算短溢表 初始化 BCS 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				// 1.结转出口成品(报关单) 损耗映射（为11栏用）
				this.initCustomsProductMapByBcs(beginDate, endDate,
						parameterMaps, progressInfo);
				// 结转出口情况
				this.initTransFactOutMapByBcs(beginDate, endDate,
						parameterMaps, progressInfo);

				// 2.料件进口(报关单) 映射（为12栏用）
				this.initCustomsMaterielMapByBcs(beginDate, endDate,
						parameterMaps, progressInfo);

				// 结转进口情况
				this.initTransFactInMapByBcs(beginDate, endDate, parameterMaps,
						progressInfo);
				// 3.根据单据查询送货数量，将送货数量按照电子化手册单耗折算成料件。
				if (isSingleSelect && !isFromCheckData) {
					this.initSendAmountByBcs(endDate, parameterMaps);
				}

				// // 4.特殊报关单 后续补税
				// this.initCustom9700MapBcs(parameterMaps);

				// // 5.海关批准内销
				// initCustom0644MapBcs(parameterMaps);

			}

			/**
			 ***第六步：电子手册**********************
			 */
			if (projectTypeParam.getIsDzsc() == true) {
				clientTipMessage = "计算短溢表 初始化 DZSC 报关成品(直接出口+结转出口+返工复出-退厂返工)的合计BOM耗用... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);
				// 1.结转出口成品(报关单) 损耗映射
				this.initCustomsProductMapByDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 结转送货情况
				this.initTransFactOutMapByDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 2.料件进口(报关单) 映射
				this.initCustomsMaterielMapByDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 结转收送情况
				this.initTransFactInMapByDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 3.根据单据查询送货数量，将送货数量按照电子手册单耗折算成料件。
				if (isSingleSelect && !isFromCheckData) {
					this.initSendAmountByDzsc(endDate, parameterMaps);
				}

			}
			clientTipMessage = "开始计算短溢表转厂栏位 ... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 *** 第七步：计算收货数量
			 */
			if (!isFromCheckData) {
				this.initRecvAmount(endDate, parameterMaps);// 
			}

			/**
			 ***第八步：如果选多种报关模式，则按照报关常用工厂BOM来折算送货的折料明细。
			 */
			if (!isSingleSelect) {
				this.initSendAmount(parameterMaps);
			}

			System.out.println("wss 开始计算发票***********************************");
			/**
			 * 第九步计算发票数量 =全部发票数量-已核销的发票数量
			 */
			if (!isFromCheckData) {
				this.initInvoiceVerificationMap(beginDate, endDate,
						parameterMaps);
			}

			clientTipMessage = "开始计算短溢表 ... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/**
			 ***第十步：总统计
			 */
			List<BalanceInfo> reslutList = this.getResult(beginDate, endDate,
					parameterMaps, isFromCheckData, projectTypeParam,
					isSingleSelect);
			//
			// 开始删除历史记录
			//
			clientTipMessage = "计算短溢表 开始批量删除历史记录... !!";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);
			// 删除平衡表信息BalanceInfo
			this.casLeftoverMaterielDao.deleteBalanceInfo();
			for (int i = 0, n = reslutList.size(); i < n; i++) {
				BalanceInfo item = (BalanceInfo) reslutList.get(i);
				item.setCompany(CommonUtils.getCompany());
				this.casDao.getHibernateTemplate().save(item);
			}
			return reslutList;
		} catch (Exception ex) {
			logger.error("错误", ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			runMap.put(companyId, false);
		}
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String nowToDate(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date);
		return str1;
	}

	/**
	 * 获取年数
	 * 
	 * @param date
	 * @return
	 */
	public String getYear(Date date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		if (date == null) {
			return "";
		} else {
			return defaultDate.substring(0, 4);
		}
	}

	/**
	 * 获得短溢表计算结果集
	 * 
	 * @return 短溢表计算结果
	 */
	private List<BalanceInfo> getResult(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, boolean isFromCheckData,
			ProjectTypeParam projectTypeParam, boolean isSingleSelect)
			throws Exception {// isFromCheckData

		String clientTipMessage = "wss 进入了总统计的方法*******************************************";
		System.out.println(clientTipMessage);
		logger.info(clientTipMessage);

		/**
		 * key为：报关名称+报关规格+报关单位 value为：相同报关名称、报关规格、报关单位的BillTemp
		 */
		Map<String, BillTemp> hsKeyMap = parameterMaps.hsKeyMap;

		/**
		 * 料件单据
		 */
		List<BalanceInfo> reslutList = new ArrayList<BalanceInfo>();
		HashMap<String, BalanceInfo> resultHash = new HashMap<String, BalanceInfo>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		// edit by xxm 2007-12-3
		conditions.add(new Condition("and", null,
				"billMaster.billType is not null ", null, null, null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
						.getYear()), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));

		// 表名BillDetailMateriel----料件单据明细

		String billDetailRemainMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		// wss2010.10.13还要按仓库属性分组
		String selects = "select sum(a.hsAmount),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.wareSet.wareProperty ";
		String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.wareSet.wareProperty ";
		String leftOuter = " left join a.billMaster left join a.billMaster.billType left join a.hsUnit left join a.wareSet  ";

		List listBillDetailMateriel = casDao.commonSearch(selects,
				billDetailRemainMateriel, conditions, groupBy, leftOuter);

		// wss:2010.07.13新增残次品（料件部分）

		conditions.add(new Condition("and", null, "billMaster.billType.code",
				"=", "5001", null));
		conditions.add(new Condition("and", null, "note", "=", "料件", null));

		// 残次品表
		billDetailRemainMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);

		// 残次品（料件部分）
		List listBillDetailMateriel5001 = casDao.commonSearch(selects,
				billDetailRemainMateriel, conditions, groupBy, leftOuter);
		System.out.println();

		clientTipMessage = "wss billDetailMateriel5001.size "
				+ listBillDetailMateriel5001.size();
		System.out.println(clientTipMessage);
		logger.info(clientTipMessage);

		listBillDetailMateriel.addAll(listBillDetailMateriel5001);

		clientTipMessage = "wss billDetailMateriel.size "
				+ listBillDetailMateriel.size();
		System.out.println(clientTipMessage);
		logger.info(clientTipMessage);

		int size = listBillDetailMateriel.size();

		int yyy = 0;

		/**
		 * 遍历料件单据
		 */
		for (int i = 0; i < size; i++) {
			Object[] objs = (Object[]) listBillDetailMateriel.get(i);
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			System.out.println("hsAmount=" + hsAmount);
			// 单据类型
			String typeCode = objs[1] == null ? "" : (String) objs[1];
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// 仓库属性wss2010.10.13新加,如果为null，则认为是"0"保税仓
			String wareProperty = objs[5] == null ? "0" : (String) objs[5];
			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			// logger.info("ls test key -------------"+key);
			BalanceInfo item = null;
			if (resultHash.get(key) != null) {
				item = (BalanceInfo) resultHash.get(key);
			} else {
				hsKeyMap.remove(key);
				item = new BalanceInfo();
				item.setName(hsName);
				item.setSpec(hsSpec);
				item.setUnitName(hsUnitName);
				resultHash.put(key, item);
				yyy++;
			}
			// item.setF15(parameterMaps.custom9700.get(key) == null ? 0.0
			// : parameterMaps.custom9700.get(key));

			// wss:2010.06.23屏蔽
			// item.setF16(parameterMaps.custom0644.get(key) == null ? 0.0
			// : parameterMaps.custom0644.get(key));

			// System.out.println("parameterMaps.custom0644.get(key)="
			// + parameterMaps.custom0644.get(key));
			// System.out.println("此处的parameterMaps.custom9700.values()="
			// + parameterMaps.custom9700.values());

			// **f0 国内购买的 1001料件期初单非保税部分 -所有出仓单中非保税部分(只抓非保税仓"1"的) + 国内购买数
			if ("1".equals(wareProperty)) {
				if (typeCode.equals("1001")) {
					item.setF0(item.getF0() + hsAmount);
				}
				if (typeCode.equals("1101") // 1101 车间领用
						|| typeCode.equals("1102") // 1102 料件退换
						|| typeCode.equals("1103") // 1103 料件复出
						|| typeCode.equals("1104") // 1104 料件盘亏单
						|| typeCode.equals("1105") // 1105 料件转仓出库
						|| typeCode.equals("1106") // 1106 结转料件退货单
						|| typeCode.equals("1107") // 1107 其他料件退货单
						|| typeCode.equals("1108") // 1108 其他领用
						|| typeCode.equals("1109") // 1109 受托加工领用
						|| typeCode.equals("1111") // 11110受托加工料件退回
						|| typeCode.equals("1112") // 1112外发加工返修出库
						|| typeCode.equals("1113") // 1113外发加工料件出库
						|| typeCode.equals("1114") // 1114受托加工料件出库
						|| typeCode.equals("1115")) { // 1115海关批准内销wss2010.06.30加)
					item.setF0(item.getF0() - hsAmount);
				}

			}

			// **f1 库存原材料. 把单据类型不相同的但是名称规格单位相同的数量累加(只抓保税仓"0"的)
			if ("0".equals(wareProperty)) {
				if (typeCode.equals("1001") // 1001 料件期初单
						|| typeCode.equals("1003") // 1003 直接进口
						|| typeCode.equals("1004") // 1004 结转进口
						// || typeCode.equals("1005") // 1005 国内购买
						|| typeCode.equals("1006") // 1006 车间返回
						|| typeCode.equals("1007") // 1007 料件盘盈单
						|| typeCode.equals("1009") // 1009 其他来源
						|| typeCode.equals("1010") // 1010 料件转仓入库
						|| typeCode.equals("1011") // 1011 海关征税进口
						|| typeCode.equals("1012") // 1012 外发加工退回料件
						|| typeCode.equals("1013") // 1013外发加工返回料件
						|| typeCode.equals("1017") // 1017 外发加工料件退回
						|| typeCode.equals("1018") // 1018 外发加工料件入库
				) {
					item.setF1(item.getF1() + hsAmount);
				} else if (typeCode.equals("1101") // 1101 车间领用
						|| typeCode.equals("1102") // 1102 料件退换
						|| typeCode.equals("1103") // 1103 料件复出
						|| typeCode.equals("1104") // 1104 料件盘亏单
						|| typeCode.equals("1105") // 1105 料件转仓出库
						|| typeCode.equals("1106") // 1106 结转料件退货单
						|| typeCode.equals("1107") // 1107 其他料件退货单
						|| typeCode.equals("1108") // 1108 其他领用
						|| typeCode.equals("1110") // 1110 外发加工出库
						|| typeCode.equals("1112") // 1112外发加工返修出库
						|| typeCode.equals("1113") // 1113外发加工料件出库
						|| typeCode.equals("1115") // 1115海关批准内销wss2010.06.30加
						|| typeCode.equals("5001") // 5001残次品（料件部分）

				) { // 出库
					item.setF1(item.getF1() - hsAmount);
				}
			}

			// B非保税库存原料还要加上 1005国内购买数量
			if (typeCode.equals("1005")) {
				item.setF0(item.getF0() + hsAmount);
			}

			// ******F2设置半成品折原料 1101 车间领用 + 1002 在产品 - 1006 车间返回
			if (typeCode.equals("1101") || typeCode.equals("1002")) {
				item.setF2(item.getF2() + hsAmount);
			} else if (typeCode.equals("1006")) {
				item.setF2(item.getF2() - hsAmount);
			}

			// ********F4外发加工/厂外存放数 1012外发加工退回料件 1110外发加工出库
			if (typeCode.equals("1110") || typeCode.equals("1014")
					|| typeCode.equals("1112") || typeCode.equals("1113")) {
				item.setF4(item.getF4() + hsAmount);
			} else if (typeCode.equals("1012") || typeCode.equals("1013")
					|| typeCode.equals("1017") || typeCode.equals("1018")) {
				item.setF4(item.getF4() - hsAmount);
			}

			// A.国内购买 = 1020国内购买期初单+1005国内购买 - 本年度已核销发票数（在后面加上）
			if (typeCode.equals("1020") || typeCode.equals("1005")) {
				item.setF18(CommonUtils.getDoubleExceptNull(item.getF18())
						+ hsAmount);
			}
		}

		// *******来自盘点parameterMaps.checkHs料件对应的折算报关数量

		clientTipMessage = "wss isFromCheckData = " + isFromCheckData;
		System.out.println(clientTipMessage);
		logger.info(clientTipMessage);

		if (isFromCheckData) {

			clientTipMessage = "wss开始抓盘点数据*****************************";
			System.out.println(clientTipMessage);
			logger.info(clientTipMessage);
			initLjCheckData(parameterMaps, endDate);

		}
		/**
		 *edit by sls 2008/04/02
		 */
		// com.bestway.bcus.cas.logic.ImgOrgUseInfoLogic.ParameterMaps tmpMaps
		// = new com.bestway.bcus.cas.logic.ImgOrgUseInfoLogic.ParameterMaps();
		// tmpMaps.billDetail2002MapByF25 = parameterMaps.billDetail2002Map;
		// tmpMaps.billDetail2007MapByF25 = parameterMaps.billDetail2007Map;
		// tmpMaps.billDetail2110MapByF25 = parameterMaps.billDetail2110Map;
		// tmpMaps.billDetail2103MapByF25 = parameterMaps.billDetail2103Map;
		// ******车间材料库存统计表****************************************
		// Map<String, BillTemp> f25Map = this.imgOrgUseInfoLogic
		// .getF25ByHsKeyForTemp(null, beginDate, endDate, tmpMaps);
		/**********/
		Iterator<BalanceInfo> iterator = resultHash.values().iterator();
		while (iterator.hasNext()) {
			BalanceInfo item = iterator.next();

			String hsName = item.getName() == null ? "" : item.getName().trim();
			String hsUnitName = item.getUnitName() == null ? "" : item
					.getUnitName().trim();
			String hsSpec = item.getSpec() == null ? "" : item.getSpec().trim();

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			System.out.println("this is key=" + key);
			logger.info(key);

			Map<String, Double> tempMap = this.getProductConvertIntoMateriel(
					key, parameterMaps);
			// 如果来自于盘点数据则国内购买数据为0;
			if (isFromCheckData) {
				item.setF0(0.0);
			}
			// 来自盘点 库存原材料
			if (isFromCheckData) {
				System.out.println("parameterMaps.checkHs.get(key+0)="
						+ parameterMaps.checkHs.get(key + "/0"));
				item.setF1(parameterMaps.checkHs.get(key + "/0") == null ? 0.0
						: parameterMaps.checkHs.get(key + "/0"));
			}

			// 来自盘点 非保税库存原材料
			if (isFromCheckData) {
				item.setF0(parameterMaps.checkHs.get(key + "/B") == null ? 0.0
						: parameterMaps.checkHs.get(key + "/B"));
			}

			// if (f25Map.get(key) != null) {// 成品折原料 --edit by sls 2008/04/03
			// BillTemp tmp = f25Map.get(key);
			// item.setF2(tmp.getBillSum2());
			// }

			// 来自盘点 在成品折原料
			if (isFromCheckData) {
				item.setF2(parameterMaps.checkHs.get(key + "/2") == null ? 0.0
						: parameterMaps.checkHs.get(key + "/2"));
			} else {
				Map<String, Double> tempHalfMap = this
						.getHalfProductConvertIntoMateriel(key, parameterMaps);
				item.setF2(CommonUtils.getDoubleExceptNull(item.getF2())
						+ CommonUtils.getDoubleExceptNull(tempHalfMap.get("F2")
								- CommonUtils.getDoubleExceptNull(tempMap
										.get("F2"))));
			}

			// 来自盘点 成品折原料
			if (isFromCheckData) {
				item.setF3(parameterMaps.checkHs.get(key + "/1") == null ? 0.0
						: parameterMaps.checkHs.get(key + "/1"));
			} else {
				item.setF3(tempMap.get("F3"));// 成品折原料
			}

			// 来自盘点 外发加工/厂外存放数
			if (isFromCheckData) {
				item.setF4(parameterMaps.checkHs.get(key + "/4") == null ? 0.0
						: parameterMaps.checkHs.get(key + "/4"));
			} else {
				Map<String, Double> tempHalfMap = this
						.getHalfProductConvertIntoMateriel(key, parameterMaps);
				item.setF4(item.getF4()
						+ CommonUtils
								.getDoubleExceptNull(tempHalfMap.get("F4"))
						- CommonUtils.getDoubleExceptNull(tempMap.get("F4")));// 外发加工/厂外存放数
			}

			// 实际库存总数 f1+f2+f3+f4
			item.setF5(

			// wss:2010.08.02屏蔽，不用加的(2010.08.06放开)
					CommonUtils.getDoubleExceptNull(item.getF0())
							+ CommonUtils.getDoubleExceptNull(item.getF1())
							+ CommonUtils.getDoubleExceptNull(item.getF2())
							+ CommonUtils.getDoubleExceptNull(item.getF3())
							+ CommonUtils.getDoubleExceptNull(item.getF4()));

			// 计算转厂 6/9
			// Double customAmount = parameterMaps.aInF6MapAmountForTra.get(key)
			// == null ? 0.0: parameterMaps.aInF6MapAmountForTra.get(key);
			Double transFactInAmount = parameterMaps.transFactInMapAmount
					.get(key) == null ? 0.0
					: parameterMaps.transFactInMapAmount.get(key);
			Double recvAmount = parameterMaps.recvMapAmount.get(key) == null ? 0.0
					: parameterMaps.recvMapAmount.get(key);
			// System.out.println("-----------key:"+key+" ---customs:"+customAmount+"    tran:"+tranHsAmount);
			if (recvAmount - transFactInAmount > 0) {
				item.setF9(recvAmount - transFactInAmount);
			} else {
				item.setF6(0 - (recvAmount - transFactInAmount));
			}
			//
			// 计算转厂 F6/F9
			// (海关帐单据)收货数量 [A]= 结转料件退货单(报关数量) - 已结转未收货期初单(报关数量) + 结转进口(报关数量) -
			// 结转料件退货单(报关数量)
			// (报关单数量)料件转厂 [B]= 料件转厂报关数数量
			// F6 (已转厂未收货数) = [A] - [B] > 0 时填充 用 ( [A] - [B] == F6 )
			// F9 (已收货未转厂数) = [A] - [B] <= 0 时填充 用 ( [B] - [A] == F9 )
			//
			//
			// 计算转厂F7/F8
			// (海关帐单据)结转成品数量 [A]= (结转出口 + 已交货未结转期初单 - 结转成品退货单 - 已结转未交货期初单) ->
			// 查找最近时间一本合同BOM来拆料
			// (报关单数量)结转成品报关单 [B]= 结转成品报关单拆料 -> 查找最近时间一本合同BOM来拆料
			// F7 (已送货未转厂数) = [A] - [B] > 0 时填充 用 ( [A] - [B] == F7 )
			// F8 (已转厂未送货数) = [A] - [B] <= 0 时填充 用 ( [B] - [A] == F8 )
			//
			// if (isSingleSelect) {
			Double transFactOutAmount = parameterMaps.transFactOutMapAmount
					.get(key) == null ? 0.0
					: parameterMaps.transFactOutMapAmount.get(key);// 报关数量(出货)
			Double sendAmount = parameterMaps.sendMapAmount.get(key) == null ? 0.0
					: parameterMaps.sendMapAmount.get(key);
			// if (projectTypeParam.getIsBcs() != null
			// && projectTypeParam.getIsBcs()) {
			// // customAmount = parameterMaps.f8MapNewByBcs.get(key) == null ?
			// 0.0
			// // : parameterMaps.f8MapNewByBcs.get(key);// 报关数量(出货)
			//
			// tranHsAmount = parameterMaps.f7MapNewByBcs.get(key) == null ? 0.0
			// : parameterMaps.f7MapNewByBcs.get(key);
			// }
			// if (projectTypeParam.getIsDzsc() != null
			// && projectTypeParam.getIsDzsc()) {
			// // customAmount = parameterMaps.f8MapNewByDzsc.get(key) == null ?
			// 0.0
			// // : parameterMaps.f8MapNewByDzsc.get(key);// 报关数量(出货)
			//
			// tranHsAmount = parameterMaps.f7MapNewByDzsc.get(key) == null ?
			// 0.0
			// : parameterMaps.f7MapNewByDzsc.get(key);
			// }
			// if (projectTypeParam.getIsBcus() != null
			// && projectTypeParam.getIsBcus()) {
			// // customAmount = parameterMaps.f8MapNewByBcus.get(key) == null ?
			// 0.0
			// // : parameterMaps.f8MapNewByBcus.get(key);// 报关数量(出货)
			//
			// tranHsAmount = parameterMaps.f7MapNewByBcus.get(key) == null ?
			// 0.0
			// : parameterMaps.f7MapNewByBcus.get(key);
			// }
			// } else {
			// // 按照工厂的来折料
			// Map<String, Double> aInF7MapAmount =
			// parameterMaps.transFactOutMapAmount;
			// customAmount = aInF7MapAmount.get(key) == null ? 0.0
			// : aInF7MapAmount.get(key);// 报关数量(出货)
			// System.out.println("此时2的：customAmount=" + customAmount);
			// tranHsAmount = parameterMaps.f7Map.get(key) == null ? 0.0
			// : parameterMaps.f7Map.get(key);
			// System.out.println("此时2的：tranHsAmount=" + tranHsAmount);
			// }
			if (sendAmount - transFactOutAmount > 0) {
				System.out.println("大于零时：tranHsAmount=" + sendAmount);
				System.out.println("大于零时：customAmount=" + transFactOutAmount);
				System.out.println("大于零时：tranHsAmount - customAmount="
						+ (sendAmount - transFactOutAmount));
				item.setF7(sendAmount - transFactOutAmount);
			} else {
				item.setF8(0 - (sendAmount - transFactOutAmount));
				System.out.println("小于零时：tranHsAmount=" + sendAmount);
				System.out.println("小于零时：customAmount=" + transFactOutAmount);
				System.out.println("小于零时：tranHsAmount - customAmount="
						+ (sendAmount - transFactOutAmount));
			}

			// key = hsName + "/" + hsSpec + "/" + hsUnitName;
			// String key2 = hsName + hsSpec + "/" + hsUnitName;
			// String key3 = hsName + hsSpec + hsUnitName;

			// F11设置合同进口数
			item
					.setF11(item.getF11()
							+ (parameterMaps.customsMaterialMapAmount.get(key) == null ? 0.0
									: parameterMaps.customsMaterialMapAmount
											.get(key)));

			// F12合同出口耗料 + 还要加上海关批准内销
			item
					.setF12(item.getF12()
							+ (parameterMaps.customsProductMapWaste.get(key) == null ? 0.0
									: parameterMaps.customsProductMapWaste
											.get(key))
							+ (parameterMaps.customsMaterialDOMESTICSALESMapAmount
									.get(key) == null ? 0.0
									: parameterMaps.customsMaterialDOMESTICSALESMapAmount
											.get(key)));

			// F18国内购买 = 1020国内购买期初单 + 1005国内购买 + 本年度已核销发票数
			if (isFromCheckData) {
				item.setF18(0.0);// 如果从盘点导入，则此栏位为0
			} else {
				item
						.setF18(CommonUtils.getDoubleExceptNull(item.getF18())
								- CommonUtils
										.getDoubleExceptNull(parameterMaps.invoiceVerificationAmountMap
												.get(key)));
			}

			reslutList.add(item);
		}
		return reslutList;
	}

	// /**
	// * 初始化后续补税数map
	// *
	// * @param parameterMaps
	// */
	// private void initCustom9700MapBcs(ParameterMaps parameterMaps) {
	// String selects =
	// "select a.commName,a.commSpec,a.unit.name, sum(a.commAmount) from BcsCustomsDeclarationCommInfo a "
	// + "where a.baseCustomsDeclaration.company.id=? "
	// + "and a.baseCustomsDeclaration.effective=? "
	// + "and a.baseCustomsDeclaration.impExpType=? "
	// + "and a.baseCustomsDeclaration.impExpFlag in(?,?) "
	// + "and a.baseCustomsDeclaration.tradeMode.code=? "
	// + "group by a.commName,a.commSpec,a.unit.name";
	// List<Object> params = new ArrayList<Object>();
	// params.add(CommonUtils.getCompany().getId());
	// params.add(true);
	// params.add(ImpExpType.DIRECT_IMPORT);
	// params.add(ImpExpFlag.IMPORT);
	// params.add(ImpExpFlag.SPECIAL);
	// params.add("9700");
	// List list = casDao.commonSearch(selects, params.toArray());
	// Map<String, Double> custom9700 = parameterMaps.custom9700;
	// for (int i = 0; i < list.size(); i++) {
	// Object[] objs = (Object[]) list.get(i);
	// String hsName = (String) objs[0] == null ? "" : (String) objs[0];
	// String hsSpec = (String) objs[1] == null ? "" : (String) objs[1];
	// String unitName = (String) objs[2] == null ? "" : (String) objs[2];
	// Double hsAmount = (Double) objs[3] == null ? 0.0 : (Double) objs[3];
	// String key = hsName + "/" + hsSpec + "/" + unitName;
	//
	// System.out.println("9700Key=" + key);
	// System.out.println("9700hsAmount=" + hsAmount);
	// if (custom9700.get(key) == null) {
	// custom9700.put(key, hsAmount);
	// } else {
	// custom9700.put(key, custom9700.get(key) + hsAmount);
	// }
	// }
	// System.out.println("custom9700.values()=" + custom9700.values());
	// }

	// /**
	// * 料件内销数map
	// *
	// * @param parameterMaps
	// */
	// private void initCustom0644MapBcs(ParameterMaps parameterMaps) {
	// String selects =
	// "select a.commName,a.commSpec,a.unit.name, sum(a.commAmount) from BcsCustomsDeclarationCommInfo a "
	// + "where a.baseCustomsDeclaration.company.id=? "
	// + "and a.baseCustomsDeclaration.effective=? "
	// + "and a.baseCustomsDeclaration.impExpType=? "
	// + "and a.baseCustomsDeclaration.impExpFlag in(?,?) "
	// + "and a.baseCustomsDeclaration.tradeMode.code=? "
	// + "group by a.commName,a.commSpec,a.unit.name";
	// List<Object> params = new ArrayList<Object>();
	// params.add(CommonUtils.getCompany().getId());
	// params.add(true);
	// params.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
	// params.add(ImpExpFlag.IMPORT);
	// params.add(ImpExpFlag.SPECIAL);
	// params.add("0644");
	// List list = casDao.commonSearch(selects, params.toArray());
	// Map<String, Double> custom0644 = parameterMaps.custom0644;
	//
	// System.out.println("wss bcs 特殊报关单进口料件内销：list.size" + list.size());
	//
	// for (int i = 0; i < list.size(); i++) {
	// Object[] objs = (Object[]) list.get(i);
	// String hsName = (String) objs[0] == null ? "" : (String) objs[0];
	// String hsSpec = (String) objs[1] == null ? "" : (String) objs[1];
	// String unitName = (String) objs[2] == null ? "" : (String) objs[2];
	// Double hsAmount = (Double) objs[3] == null ? 0.0 : (Double) objs[3];
	// String key = hsName + "/" + hsSpec + "/" + unitName;
	//
	// System.out.println("0644Key=" + key);
	// System.out.println("0644hsAmount=" + hsAmount);
	// if (custom0644.get(key) == null) {
	// custom0644.put(key, hsAmount);
	// } else {
	// custom0644.put(key, custom0644.get(key) + hsAmount);
	// }
	// }
	// System.out.println("custom0644.values()=" + custom0644.values());
	// }

	/**
	 * 初始化 半成品单据明细 hashMap
	 * 
	 */
	private void initBillDetailHalfProductMap(Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		// String selects =
		// " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailHalfProduct a "
		// + ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
		// +
		// " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
		// +
		// " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
		// + " and a.billMaster.company.id = ? "
		// + " group by a.billMaster.billType.code,v.id "
		// + " order by v.id ";// order by 确保 bom id 只查询一次

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailHalfProduct a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) from MaterialBomVersion c where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());

		// wss:2010.07.13加上残次品（半成品）
		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailRemainProduct a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) from MaterialBomVersion c where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.billType.code = ? "
				+ " and a.note = ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		params = new ArrayList<Object>();
		params.add(true);
		params.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		params.add(endDate);
		params.add("5001");
		params.add("半成品");
		params.add(CommonUtils.getCompany().getId());
		beginTime = System.currentTimeMillis();
		List list5001 = casDao.commonSearch(selects, params.toArray());

		list.addAll(list5001);

		logger.info("CAS 开始查询所有半成品的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		Map<String, Double> billDetailHalfMap = parameterMaps.billDetailHalfMap; // 半成品结余
		Map<String, Double> billDetailHalfOutSourceMap = parameterMaps.billDetailHalfOutSourceMap; // 半成品外发

		// 存取分页查询bom时所临时存放的成品列表
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 半成品 版本.id
			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);
			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				String clientTipMessage = "CAS 半成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);

				progressInfo.setHintMessage(clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());

				List materialBomDetailList = this.materialManageDao
						.findMaterielBomDetail(versionIdList);

				versionIdList.clear();
				versionIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (materialBomDetailList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}
				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
					String tempVersionId = (String) bomObjs[1];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					String typeCode = (String) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);

						// 料件料号为 key
						String ptNoKey = (String) bomObjs[0];

						// 成品单耗
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];

						// 成品单
//						Double unitUsed = bomObjs[3] == null ? 0.0
//								: (Double) bomObjs[3];

						// 损耗
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 料件的半成品单耗用量=半成品数量*(单耗/(1-损耗率)
						Double halfProductMaterialUnitWaste = ptAmount
								* (unitWaste / (1 - waste));
						if (typeCode.equals("4002")// 半成品盘盈单
								|| typeCode.equals("4003")// 委外加工入库
								|| typeCode.equals("4004")// 外发加工半成品退
								|| typeCode.equals("4006")// 外发加工半成品入库
						) { // 入库
							if (billDetailHalfMap.get(ptNoKey) == null) {
								billDetailHalfMap.put(ptNoKey,
										halfProductMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailHalfMap
										.get(ptNoKey);
								tempAmout += halfProductMaterialUnitWaste;
								billDetailHalfMap.put(ptNoKey, tempAmout);
							}
						}
						if (typeCode.equals("4102") // 半成品盘亏单
								|| typeCode.equals("4103")// 委外加工出库
								|| typeCode.equals("4104")// 外发加工半成品返修
								|| typeCode.equals("4106")// 委外加工出库
								|| typeCode.equals("5001")// 残次品（半成品部分
						) { // 出库
							if (billDetailHalfMap.get(ptNoKey) == null) {
								billDetailHalfMap.put(ptNoKey,
										-halfProductMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailHalfMap
										.get(ptNoKey);
								tempAmout -= halfProductMaterialUnitWaste;
								billDetailHalfMap.put(ptNoKey, tempAmout);
							}
						}

						// 下面统计半成品外发
						if (typeCode.equals("4103")// 
								|| typeCode.equals("4104")// 
								|| typeCode.equals("4106")//
						) { // 入库
							if (billDetailHalfOutSourceMap.get(ptNoKey) == null) {
								billDetailHalfOutSourceMap.put(ptNoKey,
										halfProductMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailHalfOutSourceMap
										.get(ptNoKey);
								tempAmout += halfProductMaterialUnitWaste;
								billDetailHalfOutSourceMap.put(ptNoKey,
										tempAmout);
							}
						}
						if (typeCode.equals("4003") // 
								|| typeCode.equals("4004")// 
								|| typeCode.equals("4006")// 
						) { // 出库
							if (billDetailHalfOutSourceMap.get(ptNoKey) == null) {
								billDetailHalfOutSourceMap.put(ptNoKey,
										-halfProductMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailHalfOutSourceMap
										.get(ptNoKey);
								tempAmout -= halfProductMaterialUnitWaste;
								billDetailHalfOutSourceMap.put(ptNoKey,
										tempAmout);
							}
						}

					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 初始化 成品单据明细 hashMap 把单据类型一样的和bom id一样的来进行分组汇总数量
	 * 
	 */
	private void initBillDetailProductMap(Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		// String selects =
		// " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailProduct a "
		// + ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
		// +
		// " and ( a.version = v.version or ( a.version is null and v.version = 0 )) "
		// +
		// " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
		// + " and a.billMaster.company.id = ? "
		// + " group by a.billMaster.billType.code,v.id "
		// + " order by v.id ";// order by 确保 bom id 只查询一次

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailProduct a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) from MaterialBomVersion c where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, params.toArray());
		logger.info("CAS 开始查询所有成品的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		// wss 新增残次品（成品部分）
		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id from BillDetailRemainProduct a "
				+ ",MaterialBomVersion v where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) from MaterialBomVersion c where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.billType.code = ? "
				+ " and a.note = ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次
		params = new ArrayList<Object>();
		params.add(true);
		params.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		params.add(endDate);
		params.add("5001");
		params.add("成品");
		params.add(CommonUtils.getCompany().getId());

		// 成品
		List list5001 = casDao.commonSearch(selects, params.toArray());

		list.addAll(list5001);

		/**
		 * 2001成品起初单
		 */
		Map<String, Double> billDetail2001Map = parameterMaps.billDetail2001Map; // 

		/**
		 * 2002车间入库
		 */
		Map<String, Double> billDetail2002Map = parameterMaps.billDetail2002Map; // 

		/**
		 * 2007受托加工车间入库
		 */
		Map<String, Double> billDetail2007Map = parameterMaps.billDetail2007Map; // 

		/**
		 * 2009结转成品退货单
		 */
		Map<String, Double> billDetail2009Map = parameterMaps.billDetail2009Map; // 

		/**
		 * 2010受托加工退回成品
		 */
		Map<String, Double> billDetail2010Map = parameterMaps.billDetail2010Map; // 

		/**
		 * 2101直接出口
		 */
		Map<String, Double> billDetail2101Map = parameterMaps.billDetail2101Map; // 

		/**
		 * 2102结转出口
		 */
		Map<String, Double> billDetail2102Map = parameterMaps.billDetail2102Map; // 

		/**
		 * 2103返回车间
		 */
		Map<String, Double> billDetail2103Map = parameterMaps.billDetail2103Map; // 

		/**
		 * 结转成品
		 */
		Map<String, Double> billDetailCpTranMap = parameterMaps.billDetailCpTranMap; // 料号，耗用量（成品数量）

		/**
		 * 2106海关批准内销
		 */
		Map<String, Double> billDetail2106Map = parameterMaps.billDetail2106Map; // 

		/**
		 * 2107其他内销
		 */
		Map<String, Double> billDetail2107Map = parameterMaps.billDetail2107Map; // 

		/**
		 * 2110受托加工返回
		 */
		Map<String, Double> billDetail2110Map = parameterMaps.billDetail2110Map; // 

		/**
		 * F26入库成品-出库成品
		 */
		Map<String, Double> billDetailF26Map = parameterMaps.billDetailF26Map; // 

		/**
		 * 2015外发加工成品入库
		 */
		Map<String, Double> billDetail2015Map = parameterMaps.billDetail2015Map; // 

		/**
		 * 2017外发加工成品退回
		 */
		Map<String, Double> billDetail2017Map = parameterMaps.billDetail2017Map; // 

		/**
		 * 2113外发加工成品返修
		 */
		Map<String, Double> billDetail2113Map = parameterMaps.billDetail2113Map; // 

		/**
		 * 2114外发加工成品出库
		 */
		Map<String, Double> billDetail2114Map = parameterMaps.billDetail2114Map; // 

		// 存取分页查询bom时所临时存放的成品列表
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 报关常用工厂BOM版本id
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			versionIdMap.put(versionId, versionId);

			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				String clientTipMessage = "CAS 成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);
				List<String> versionIdList = new ArrayList<String>();

				versionIdList.addAll(versionIdMap.values());

				// 报关常用工厂BOM料件资料版本号Id、单耗、单项用量、损耗和父件料号
				List materialBomDetailList = this.materialManageDao
						.findMaterielBomDetail(versionIdList);

				versionIdList.clear();
				versionIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (materialBomDetailList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();

				for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
					String tempVersionId = (String) bomObjs[1];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					String typeCode = (String) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);

						// 料件料号为 key
						String ptNoKey = (String) bomObjs[0];

						// 成品单耗
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];

						// 成品单
//						Double unitUsed = bomObjs[3] == null ? 0.0
//								: (Double) bomObjs[3];

						// 损耗
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 料件的成品单项用量 -----> 成品数量*(单耗/(1-损耗率))
						Double productMaterialUnitWaste = ptAmount
								* (unitWaste / (1 - waste));
						if (typeCode.equals("2001")) {// 2001 成品起初单
							if (billDetail2001Map.get(ptNoKey) == null) {
								billDetail2001Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2001Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2001Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2002")) {// 2002 车间入库
							if (billDetail2002Map.get(ptNoKey) == null) {
								billDetail2002Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2002Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2002Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2015")) {// 2015 外发加工成品入库
							// add by zy
							// 2007-5-8
							if (billDetail2015Map.get(ptNoKey) == null) {
								billDetail2015Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2015Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2015Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2017")) {// 2017 外发加工成品退回
							if (billDetail2017Map.get(ptNoKey) == null) {
								billDetail2017Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2017Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2017Map.put(ptNoKey, tempAmout);
							}
						}

						else if (typeCode.equals("2113")) {// 2113 外发加工成品返修
							if (billDetail2113Map.get(ptNoKey) == null) {
								billDetail2113Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2113Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2113Map.put(ptNoKey, tempAmout);
							}
						}

						else if (typeCode.equals("2114")) {// 2114外发加工成品出库
							if (billDetail2114Map.get(ptNoKey) == null) {
								billDetail2114Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2114Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2114Map.put(ptNoKey, tempAmout);
							}
						}

						else if (typeCode.equals("2007")) {// 2007 受托加工车间入库
							if (billDetail2007Map.get(ptNoKey) == null) {
								billDetail2007Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2007Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2007Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2009")) {// 2009 结转成品退货单
							System.out
									.println("--------------------------------结转成品退货单");
							if (billDetail2009Map.get(ptNoKey) == null) {
								billDetail2009Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2009Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2009Map.put(ptNoKey, tempAmout);
							}
							// 结转成品
							if (billDetailCpTranMap.get(ptNoKey) == null) {
								System.out.println("----------结转成品退货单");
								billDetailCpTranMap.put(ptNoKey,
										-productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailCpTranMap
										.get(ptNoKey);
								tempAmout -= productMaterialUnitWaste;
								billDetailCpTranMap.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2010")) {// 2010 受托加工退回成品
							if (billDetail2010Map.get(ptNoKey) == null) {
								billDetail2010Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2010Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2010Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2101")) {// 2101 直接出口
							if (billDetail2101Map.get(ptNoKey) == null) {
								billDetail2101Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2101Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2101Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2102")) {// 2102 结转出口
							if (billDetail2102Map.get(ptNoKey) == null) {
								billDetail2102Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2102Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2102Map.put(ptNoKey, tempAmout);
							}
							// 结转成品
							if (billDetailCpTranMap.get(ptNoKey) == null) {
								billDetailCpTranMap.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailCpTranMap
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetailCpTranMap.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2011")) {// 2011已交货未结转期初单
							System.out.println("----------2011");
							if (billDetailCpTranMap.get(ptNoKey) == null) {
								billDetailCpTranMap.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailCpTranMap
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetailCpTranMap.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2012")) {// 2012已结转未交货期初单
							System.out.println("----------2012");
							// 结转成品
							if (billDetailCpTranMap.get(ptNoKey) == null) {
								billDetailCpTranMap.put(ptNoKey,
										-productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailCpTranMap
										.get(ptNoKey);
								tempAmout -= productMaterialUnitWaste;
								billDetailCpTranMap.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2103")) {// 2103 返回车间
							if (billDetail2103Map.get(ptNoKey) == null) {
								billDetail2103Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2103Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2103Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2106")) {// 2106 海关批准内销
							if (billDetail2106Map.get(ptNoKey) == null) {
								billDetail2106Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2106Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2106Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2107")) {// 2107 其他内销
							if (billDetail2107Map.get(ptNoKey) == null) {
								billDetail2107Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2107Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2107Map.put(ptNoKey, tempAmout);
							}
						} else if (typeCode.equals("2110")) {// 2110 受托加工返回
							if (billDetail2110Map.get(ptNoKey) == null) {
								billDetail2110Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetail2110Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetail2110Map.put(ptNoKey, tempAmout);
							}
						}
						//
						// F26 入库成品-出库成品
						//
						if (typeCode.equals("2001") // 2001 成品期初单
								|| typeCode.equals("2002")// 2002 车间入库
								|| typeCode.equals("2003")// 2003 退厂返工
								|| typeCode.equals("2004")// 2004 成品盘盈单
								|| typeCode.equals("2005")// 2005 成品转仓入库
								|| typeCode.equals("2008")// 2008 其他成品退货单
								|| typeCode.equals("2009")// 2009 结转成品退货单
								|| typeCode.equals("2015")// 2015 外发加工成品入库
								|| typeCode.equals("2017")// 2017外发加工成品退回
						) { // 入库
							if (billDetailF26Map.get(ptNoKey) == null) {
								billDetailF26Map.put(ptNoKey,
										productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailF26Map
										.get(ptNoKey);
								tempAmout += productMaterialUnitWaste;
								billDetailF26Map.put(ptNoKey, tempAmout);
							}
						}
						if (typeCode.equals("2101") // 2101 直接出口
								|| typeCode.equals("2102")// 2102 结转出口
								|| typeCode.equals("2103")// 2103 返回车间
								|| typeCode.equals("2104")// 2104 返工复出
								|| typeCode.equals("2105")// 2105 成品盘亏单
								|| typeCode.equals("2106")// 2106 海关批准内销
								|| typeCode.equals("2107")// 2107 其他内销
								|| typeCode.equals("2108")// 2108 其他处理
								|| typeCode.equals("2109")// 2109 成品转仓出库
								|| typeCode.equals("2113")// 2113外发加工成品返修
								|| typeCode.equals("2114")// 2114外发加工成品出库
								|| typeCode.equals("5001")// 5001残次品（成品部分）
						) { // 出库
							if (billDetailF26Map.get(ptNoKey) == null) {
								billDetailF26Map.put(ptNoKey,
										-productMaterialUnitWaste);
							} else {
								Double tempAmout = billDetailF26Map
										.get(ptNoKey);
								tempAmout -= productMaterialUnitWaste;
								billDetailF26Map.put(ptNoKey, tempAmout);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 获得料件来自半成品折算
	 * 
	 * @param key
	 * @param parameterMaps
	 * @return
	 */
	private Map<String, Double> getHalfProductConvertIntoMateriel(String key,
			ParameterMaps parameterMaps) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// 根据报关的名称 获得对应的工厂料件列表
		//
		List<FactoryAndFactualCustomsRalation> mtList = parameterMaps.statCusNameRelationMtListByMaterielMap
				.get(key);
		if (mtList == null) {
			logger.info("Jbcus 海关帐大类对应没有这样的报关名称 == " + key);
			map.put("F2", 0.0);
			return map;
		}
		for (int i = 0, n = mtList.size(); i < n; i++) {
			FactoryAndFactualCustomsRalation mt = mtList.get(i);
			String ptNoKey = mt.getMateriel().getPtNo(); // 料件料号
			Double unitConvert = mt.getUnitConvert() == null ? 1.0 : mt
					.getUnitConvert(); // 折算系数
			if (!map.containsKey("F2")) {
				Double halfPtAmount = parameterMaps.billDetailHalfMap
						.get(ptNoKey);
				Double ptAmount = (halfPtAmount == null ? 0 : halfPtAmount);
				map.put("F2", (ptAmount * unitConvert));
			} else {
				Double halfPtAmount = parameterMaps.billDetailHalfMap
						.get(ptNoKey);
				Double ptAmount = (halfPtAmount == null ? 0 : halfPtAmount);
				map.put("F2", map.get("F2") + (ptAmount * unitConvert));
			}

			if (!map.containsKey("F4")) {
				Double halfPtAmount = parameterMaps.billDetailHalfOutSourceMap
						.get(ptNoKey);
				Double ptAmount = (halfPtAmount == null ? 0 : halfPtAmount);
				map.put("F4", (ptAmount * unitConvert));
			} else {
				Double halfPtAmount = parameterMaps.billDetailHalfOutSourceMap
						.get(ptNoKey);
				Double ptAmount = (halfPtAmount == null ? 0 : halfPtAmount);
				map.put("F4", map.get("F4") + (ptAmount * unitConvert));
			}
		}
		return map;
	}

	/**
	 * 初始化检测数据
	 * 
	 * @param parameterMaps
	 * @param endDate
	 */
	private void initLjCheckData(ParameterMaps parameterMaps, Date endDate) {
		parameterMaps.checkHs.clear();
		// 平衡表--物料类型(料件)-- 开始日期(当年年份--1月--1日)---结束日期

		// List ls = this.casDao.findCheckBalanceConvertMateriel(endDate,
		// endDate);

		// List list = this.findCheckBalanceConvertMaterielData(endDate);

		// 查询盘点中的数据
		List list = new ArrayList();

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.name,a.spec,a.hsUnit.name,a.kcType,"
				+ " a.wareSet.wareProperty,sum(a.hsAmount) "
				+ " from CheckBalanceConvertMateriel a "
				+ " left join a.hsUnit " + " left join a.fatherCheckBalance "
				+ " where a.company.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());

		if (endDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
			hsql += " and a.checkDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.name,a.spec,a.hsUnit.name,a.kcType,a.wareSet.wareProperty ";

		System.out.println("wss hsql =" + hsql);

		list = this.casDao.find(hsql, parameters.toArray());

		System.out.println("this is list.size=" + list.size());

		// 遍历每一条
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);

			String name = (String) objs[0] == null ? "" : (String) objs[0];
			String spec = (String) objs[1] == null ? "" : (String) objs[1];
			String unitName = (String) objs[2] == null ? "" : (String) objs[2];
			String type = (String) objs[3] == null ? "" : (String) objs[3];
			String wareProperty = (String) objs[4] == null ? "0"
					: (String) objs[4];// 如果为空，则认为是0
			Double hsAmount = (Double) objs[5] == null ? 0.0 : (Double) objs[5];

			if (name.equals("")) {
				continue;
			}

			String key = name + "/" + spec + "/" + unitName;

			if ("0".equals(type)) {
				// 如果是1，则认为是非保税
				if ("1".equals(wareProperty)) {
					key += "/" + "B";
				}
				// 其它认为是保税
				else {
					key += "/" + "0";
				}
			} else {
				key += "/" + type;
			}
			if (parameterMaps.checkHs.get(key) == null) {
				System.out.println("check.getHsAmount()=" + hsAmount);
				parameterMaps.checkHs.put(key, hsAmount);
			} else {
				Double oldAmount = parameterMaps.checkHs.get(key) == null ? 0.0
						: parameterMaps.checkHs.get(key);
				parameterMaps.checkHs.put(key, oldAmount + hsAmount);
			}

		}
	}

	/**
	 * 查找盘点平衡表中CheckBalance所有数据 的报关名称、报关规格、报关单位、库存类型、报关数量
	 * 
	 * @return list
	 * @author wss
	 */
	public List findCheckBalanceData(Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.name,a.spec,a.unitName,a.kcType,sum(a.hsAmount) "
				+ " from CheckBalance a " + " where a.company.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());

		if (endDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
			hsql += " and a.checkDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.name,a.spec,a.unitName,a.kcType ";

		System.out.println("wss hsql =" + hsql);
		return this.casDao.find(hsql, parameters.toArray());
	}

	/**
	 * 查找盘点平衡表中CheckBalanceConvertMateriel所有数据 的报关名称、报关规格、报关单位、库存类型、报关数量
	 * 
	 * @return list
	 * @author wss
	 */
	public List findCheckBalanceConvertMaterielData(Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.name,a.spec,a.hsUnit.name,a.kcType,sum(a.hsAmount) "
				+ " from CheckBalanceConvertMateriel a "
				+ " left join a.hsUnit left join a.fatherCheckBalance "
				+ " where a.company.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());

		if (endDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
			hsql += " and a.checkDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.name,a.spec,a.hsUnit.name,a.kcType ";

		System.out.println("wss hsql =" + hsql);
		return this.casDao.find(hsql, parameters.toArray());
	}

	/**
	 * 成品入库单折损耗(车间入库-车间返回) 把工厂的转成报关的
	 * 
	 * @param key
	 *            取得唯一键值
	 * @return 成品入库单折损耗
	 */
	private Map<String, Double> getProductConvertIntoMateriel(String key,
			ParameterMaps parameterMaps) throws Exception {
		logger.info("开始 Jbcus 成品入库单折损耗 == " + key);
		Map<String, Double> map = new HashMap<String, Double>();
		try {

			/**
			 * key :报关名称+"/"+规格+"/"+单位名称*** value为：对应的对应关系list
			 */
			List<FactoryAndFactualCustomsRalation> mtList = parameterMaps.statCusNameRelationMtListByMaterielMap
					.get(key);
			if (mtList == null) {
				logger.info("Jbcus 海关帐大类对应没有这样的报关名称 == " + key);
				map.put("F2", 0.0);
				map.put("F3", 0.0);
				map.put("F4", 0.0);
				return map;
			}

			Map<String, String> filterMap = new HashMap<String, String>();
			for (int i = mtList.size() - 1; i >= 0; i--) {
				FactoryAndFactualCustomsRalation item = mtList.get(i);
				String ptNo = item.getMateriel() == null ? "" : item
						.getMateriel().getPtNo();

				if (filterMap.containsKey(ptNo) || ptNo.equals("")) {
					mtList.remove(i);
				} else {
					filterMap.put(ptNo, ptNo);
				}
			}

			for (int i = 0, n = mtList.size(); i < n; i++) {
				FactoryAndFactualCustomsRalation mt = mtList.get(i);
				String ptNoKey = mt.getMateriel().getPtNo(); // 料件料号
				Double unitConvert = mt.getUnitConvert() == null ? 1.0 : mt
						.getUnitConvert(); // 折算系数
				// 半成品重新计算
				if (!map.containsKey("F2")) {
					Double ptAmount2002 = parameterMaps.billDetail2002Map
							.get(ptNoKey);
					Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002);

					Double ptAmount2103 = parameterMaps.billDetail2103Map
							.get(ptNoKey);
					Double ptAmount2 = (ptAmount2103 == null ? 0 : ptAmount2103);

					map.put("F2", ((ptAmount - ptAmount2) * unitConvert));

				} else {
					Double ptAmount2002 = parameterMaps.billDetail2002Map
							.get(ptNoKey);
					Double ptAmount = (ptAmount2002 == null ? 0 : ptAmount2002);

					Double ptAmount2103 = parameterMaps.billDetail2103Map
							.get(ptNoKey);
					Double ptAmount2 = (ptAmount2103 == null ? 0 : ptAmount2103);
					map.put("F2", map.get("F2")
							+ ((ptAmount - ptAmount2) * unitConvert));
				}

				//
				// 库存产成品耗用 相同版本的（入库成品单据数量－出库成品单据数量） ＊对应版本的单耗
				//

				if (!map.containsKey("F3")) {
					//
					// 获得该成品料号和版本对应的
					// 入库成品-出库成品
					//                
					Double f26PtAmount = parameterMaps.billDetailF26Map
							.get(ptNoKey);
					Double ptAmount = (f26PtAmount == null ? 0 : f26PtAmount);
					map.put("F3", (ptAmount * unitConvert));
				} else {
					Double f26PtAmount = parameterMaps.billDetailF26Map
							.get(ptNoKey);
					Double ptAmount = (f26PtAmount == null ? 0 : f26PtAmount);
					map.put("F3", map.get("F3") + (ptAmount * unitConvert));
				}
				//
				// 入库成品折料 ： （2006 外发加工入库） - （4104 外发加工返修半成品）数量＊对应版本单项用量＊料件折算
				// add by zy 2007-5-8
				if (!map.containsKey("F4")) {
					Double ptAmount2015 = parameterMaps.billDetail2015Map
							.get(ptNoKey);
					Double ptAmount2017 = parameterMaps.billDetail2017Map
							.get(ptNoKey);

					Double ptAmount2113 = parameterMaps.billDetail2113Map
							.get(ptNoKey);
					Double ptAmount2114 = parameterMaps.billDetail2114Map
							.get(ptNoKey);

					Double ptAmount = (ptAmount2017 == null ? 0 : ptAmount2017)
							+ (ptAmount2015 == null ? 0 : ptAmount2015)
							- (ptAmount2114 == null ? 0 : ptAmount2114)
							- (ptAmount2113 == null ? 0 : ptAmount2113);
					map.put("F4", (ptAmount * unitConvert));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return map;
	}

	/**
	 * 初始化 statCusNameRelationMtListMap 和 statCusNameRelationMtValueMap 对应表
	 */
	private void initStatCusNameRelationByMaterielMap(
			ParameterMaps parameterMaps) throws Exception {

		/**** key :[报关名称+"/"+规格+"/"+单位名称] value为：[对应的对应关系list] *****/
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListByMaterielMap = parameterMaps.statCusNameRelationMtListByMaterielMap;

		/** key为：[料号] value为：[对应关系(第一个)] */
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueByMaterielMap = parameterMaps.statCusNameRelationMtValueByMaterielMap;

		/** key为：[料号] value为：[报关名称+报关规格+报关单位（最后一个对应关系的）] */
		Map<String, String> ptNoKeyMap = parameterMaps.ptNoKeyMap;

		/** key为：[报关名称+报关规格+报关单位] value为：[相同报关名称、报关规格、报关单位的BillTemp] */
		Map<String, BillTemp> hsKeyMap = parameterMaps.hsKeyMap;

		// 所有 料件对应关系 按料号排
		List<FactoryAndFactualCustomsRalation> listExistStatCusNameRelationMt = this.casDao
				.findStatCusNameRelationMt(MaterielType.MATERIEL);

		for (int i = 0; i < listExistStatCusNameRelationMt.size(); i++) {

			FactoryAndFactualCustomsRalation mt = listExistStatCusNameRelationMt
					.get(i);

			Materiel materiel = mt.getMateriel();

			StatCusNameRelationHsn sr = mt.getStatCusNameRelationHsn();

			String hsName = sr.getCusName() == null ? "" : sr.getCusName();
			String hsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
			String unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			unitName = unitName == null ? "" : unitName;

			// keyList = 报关名称+"/"+报关规格+"/"+报关单位
			String tenKey = hsName + "/" + hsSpec + "/" + unitName;
			if (hsKeyMap.get(tenKey) == null) {
				BillTemp b = new BillTemp();
				b.setBill1(hsName);
				b.setBill2(hsSpec);
				b.setBill3(unitName);
				hsKeyMap.put(tenKey, b);
			}
			if (statCusNameRelationMtListByMaterielMap.get(tenKey) == null) {
				List<FactoryAndFactualCustomsRalation> tempList = new ArrayList<FactoryAndFactualCustomsRalation>();
				tempList.add(mt);
				statCusNameRelationMtListByMaterielMap.put(tenKey, tempList);
			} else {
				List<FactoryAndFactualCustomsRalation> tempList = statCusNameRelationMtListByMaterielMap
						.get(tenKey);
				tempList.add(mt);
			}

			// key = ptno 因为工厂料号不可能为空
			if (materiel == null) {
				continue;
			}
			String key = materiel.getPtNo();
			if (statCusNameRelationMtValueByMaterielMap.get(key) == null) {
				statCusNameRelationMtValueByMaterielMap.put(key, mt);
			}

			// key = ptno
			ptNoKeyMap.put(key, tenKey);
		}
	}

	/**
	 * 初始化大类和实际的对应的信息用于一对多的结转计算
	 * 
	 */
	// private void initStatCusNameRelationHsByMaterielMap(
	// ParameterMaps parameterMaps) {
	// Map<String, String> statCusNameRelationHsByMaterielMap =
	// parameterMaps.statCusNameRelationHsByMaterielMap;
	// Map<String, Double> unitConvertMap = parameterMaps.unitConvertMap;
	//
	// List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn =
	// this.casDao
	// .findStatCusNameRelationHsn(MaterielType.MATERIEL);
	// for (int i = 0, n = listExistStatCusNameRelationHsn.size(); i < n; i++) {
	// StatCusNameRelationHsn hsn = listExistStatCusNameRelationHsn.get(i);
	// StatCusNameRelation sr = hsn.getStatCusNameRelation();
	//			
	//
	// String hsName = hsn.getCusName() == null ? "" : hsn.getCusName();
	// String hsSpec = hsn.getCusSpec() == null ? "" : hsn.getCusSpec();
	// String unitName = hsn.getCusUnit() == null ? "" : hsn.getCusUnit()
	// .getName();
	//
	// Double unitConvert = (hsn ==null || hsn.getUnitConvert() ==
	// null||"".equals(hsn.getUnitConvert())) ? 1.0 : hsn
	// .getUnitConvert() ; // 折算系数
	//
	// unitName = unitName == null ? "" : unitName;
	// String key = hsName + "/" + hsSpec + "/" + unitName;
	// //
	// // value = 报关名称+"/"+规格+"/"+单位名称
	// //
	// String tenHsName = sr.getCusName() == null ? "" : sr.getCusName();
	// String tenHsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
	// String tenUnitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
	// .getName();
	// tenUnitName = tenUnitName == null ? "" : tenUnitName;
	// String value = tenHsName + "/" + tenHsSpec + "/" + tenUnitName;
	//
	// if (statCusNameRelationHsByMaterielMap.get(key) == null) {
	// statCusNameRelationHsByMaterielMap.put(key, value);
	// unitConvertMap.put(value,unitConvert);
	//				
	//				
	// }
	//
	// }
	// }
	// ////////////////////////////////////////////////////////////////////
	// 
	// 下面的代码计算转厂情况
	//
	// F6、已转厂未收货数 = a-b+c
	// a、 转厂报关数、其中：
	// 贸易方式为：进料深加工 或 来料深加工
	// 进出口类型为：料件转厂
	// b、 结转收货数：
	// (结转进口) - (结转料件退货单)
	// + (国内购买未核销数)
	// c、 跨年度结转期初单：
	// 已结转本年收货期初单
	// - 已收货本年结转期初单
	// F7、已送货未转厂数 = (c+b-a)的成品折料数
	// a、转厂报关数、其中：
	// 贸易方式为：进料深加工 或 来料深加工
	// 进出口类型为：转厂出口
	// b、结转送货数：
	// (结转出口) - (结转成品退货单)
	// c、跨年度结转期初单：
	// 已交货本年结转期初单 - 已结转本年交货期初单
	// F8、已转厂未送货数
	// 算法同7。
	// F9、已收货未转厂数
	// 算法同6。
	// //////////////////////////////////////////////////////////////////
	/**
	 * 4． 已结转未收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 <
	 * “结转报关进口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 5． 未结转已收货: 当相同客户名称的 （ “结转进口” 单据折算报关数量的累加 －“结转料件退货单” 单据折算报关数量的累加 >“结转报关进口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货 *
	 * 
	 */
	private void initRecvAmount(Date endDate, ParameterMaps parameterMaps) {

		// 以 报关名+称规格+单位+单据类型号码 为key的 map
		Map<String, Double> recvMapAmount = parameterMaps.recvMapAmount;

		// 以名称+规格+单位,单据类型号码来分组进行统计

		// *********有关结转的单据*********************
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1004", null)); // 1004 结转进口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1106", null)); // 1106 结转料件退货单

		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1015", null)); // 1015 已收货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1016", ")")); // 1016 已结转未收货期初单

		// conditions.add(new Condition("and", null, " billMaster.validDate ",
		// ">=", CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
		// .getYear()), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		// CommonUtils.getBeginDate(Integer
		// .valueOf(getYear(endDate)), 0, 1);
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", CommonUtils.getEndDate(endDate), null));
		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailMateriel", conditions, groupBy, leftOuter);

		// key = 报关名称+"/"+规格+"/"+单位名称 + "/" +客户代码
		Map<String, Double> tra1106Map = new HashMap<String, Double>();
		Map<String, Double> tra1015Map = new HashMap<String, Double>();
		Map<String, Double> tra1016Map = new HashMap<String, Double>();
		Map<String, Double> tra1004Map = new HashMap<String, Double>();
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;
			String typeCode = (String) objs[4] == null ? "" : (String) objs[4];
			if (typeCode.equals("1106")) {// 1106结转料件退货单
				tra1106Map.put(key, hsAmount);
			} else if (typeCode.equals("1015")) {// 1015已收货未结转期初单
				tra1015Map.put(key, hsAmount);
			} else if (typeCode.equals("1016")) {// 1016已结转未收货期初单
				tra1016Map.put(key, hsAmount);
			} else if (typeCode.equals("1004")) {// 1004结转进口
				tra1004Map.put(key, hsAmount);
			}
		}
		Hashtable<String, String> disHs = new Hashtable<String, String>();
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String noCustomerKey = hsName + "/" + hsSpec + "/" + unitName;
			if (disHs.get(noCustomerKey) != null) {
				continue;
			}
			disHs.put(noCustomerKey, noCustomerKey);
			//
			// 退货数量
			//
			Double backAmount = tra1106Map.get(noCustomerKey) == null ? 0.0
					: tra1106Map.get(noCustomerKey);// 结转料件退货单
			Double ysAmount = tra1015Map.get(noCustomerKey) == null ? 0.0
					: tra1015Map.get(noCustomerKey);// 已收货未结转期初单
			Double yjAmount = tra1016Map.get(noCustomerKey) == null ? 0.0
					: tra1016Map.get(noCustomerKey);// 已结转未收货期初单
			Double jikouAmount = tra1004Map.get(noCustomerKey) == null ? 0.0
					: tra1004Map.get(noCustomerKey);// 结转进口
			//
			// 收货数量 = 1015已收货未结转期初单(报关数量) - 1016已结转未收货期初单(报关数量) + 1004结转进口(报关数量)
			// -
			// 1106结转料件退货单(报关数量)
			//
			double tempAmount = ysAmount - yjAmount + jikouAmount - backAmount;
			System.out.println("----noCustomerKey:" + noCustomerKey
					+ "  ysAmount:" + ysAmount + "   yjAmount:" + yjAmount
					+ "  jikouAmount:" + jikouAmount);
			if (recvMapAmount.get(noCustomerKey) == null) {
				recvMapAmount.put(noCustomerKey, tempAmount);
			}
		}
	}

	/**
	 * dzsc 的 Bom 检查
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initSendAmountByDzsc(Date endDate, ParameterMaps parameterMaps) {
		List dzscEmsExgBills = this.casLeftoverMaterielDao.findDzscEmsExgBill();
		//
		// 获取成品版本号对应的料件明细
		//
		Map<String, Object[]> exgObjectMap = new HashMap<String, Object[]>();
		Map<String, Date> dateMap = new HashMap<String, Date>();
		for (int j = 0, n = dzscEmsExgBills.size(); j < n; j++) {
			Object[] bomObjs = (Object[]) dzscEmsExgBills.get(j);
			// 成品
			String hsName = bomObjs[0] == null ? "" : (String) bomObjs[0];
			String hsSpec = bomObjs[1] == null ? "" : (String) bomObjs[1];
			String hsUnitName = bomObjs[2] == null ? "" : (String) bomObjs[2];
			Date beginDate = (Date) bomObjs[3];
//			String contractId = (String) bomObjs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (exgObjectMap.get(key) == null) {
				exgObjectMap.put(key, bomObjs);
				dateMap.put(key, beginDate);
				// logger.info(" begin 日期最近的一个  diffDays  == "+ key +
				// " --- "+beginDate);
			} else {
				if (beginDate.after(dateMap.get(key))) {
					exgObjectMap.put(key, bomObjs);
					dateMap.put(key, beginDate);
					// logger.info("diffDays 短溢表计算 日期最近的一个   == "+ key +
					// " --- "+ beginDate);
				}
			}
		}
		/**
		 * 报关成品 名称+"/"+规格+"/"+单位 映射 bom
		 */
		Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
		Iterator<Map.Entry<String, Object[]>> iteratorByExg = exgObjectMap
				.entrySet().iterator();
		for (; iteratorByExg.hasNext();) {
			Map.Entry<String, Object[]> e = iteratorByExg.next();
			Object[] exgObjs = e.getValue();
			String key = e.getKey();
			// 成品
			String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
			String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
			String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
//			Date beginDate = (Date) exgObjs[3];
			String contractId = (String) exgObjs[4];
			List emsHeadH2kBomList = this.casLeftoverMaterielDao
					.findDzscEmsBomBill(hsName, hsSpec, hsUnitName, contractId);
			if (emsHeadH2kBomList.size() == 0) {
				System.out.println("成品" + key + "没有找到bom");
			}
			bomObjectMap.put(key, emsHeadH2kBomList);
		}

		// // 以 报关名+称规格+单位 为key的 map
		// Map<String, Double> f7MapNew = parameterMaps.f7MapNewByDzsc;
		// 以 报关名+称规格+单位 为key的 map
		Map<String, Double> sendMapAmount = parameterMaps.sendMapAmount;

		// Map<String, Double> f8MapNew = parameterMaps.f8MapNewByDzsc;

		// /**
		// * 转厂出口报关数
		// */
		// Map<String, Double> aInF7MapAmount =
		// parameterMaps.transFactOutMapAmount;

		// Iterator<Map.Entry<String, Double>> iterator = aInF7MapAmount
		// .entrySet().iterator();
		// for (; iterator.hasNext();) {
		// Map.Entry<String, Double> e = iterator.next();
		// Double hsAmount = e.getValue() == null ? 0.0 : e.getValue();
		// String key = e.getKey();
		// // 结转成品报关单
		// if (f8MapNew.get(key) == null) {
		// f8MapNew.put(key, hsAmount);
		// } else {
		// f8MapNew.put(key, f8MapNew.get(key) + hsAmount);
		// }
		// // List<Object[]> tempObjes = bomObjectMap.get(key);
		// // if (tempObjes == null) {
		// // logger.info("报关单------短溢表计算 Dzsc Bom 不存在  !! == " + key);
		// // continue;
		// // }
		// // for (Object[] bomObjs : tempObjes) {
		// // // 料件
		// // String hsName = bomObjs[0] == null ? "" : (String) bomObjs[0];
		// // String hsSpec = bomObjs[1] == null ? "" : (String) bomObjs[1];
		// // String hsUnitName = bomObjs[2] == null ? ""
		// // : (String) bomObjs[2];
		// //
		// // // key=名称+"/"+规格+"/"+单位
		// // String keyMaterial = hsName + "/" + hsSpec + "/" + hsUnitName;
		// // String keyInteger = "锌合金压铸件//千克";
		// //
		// // // 损耗
		// // Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
		// // waste = waste * 0.01;
		// //
		// // // 单耗
		// // Double unitWear = bomObjs[4] == null ? 0.0
		// // : (Double) bomObjs[4];
		// //
		// // // 单项用量 = 单耗 / (1-损耗);
		// // Double unitUsed = unitWear / (1 - waste);
		// //
		// // // 成品单耗损
		// // Double productMaterialWaste = hsAmount * unitUsed;
		// // // 结转成品报关单
		// // if (f8MapNew.get(keyMaterial) == null) {
		// // f8MapNew.put(keyMaterial, productMaterialWaste);
		// // } else {
		// // f8MapNew.put(keyMaterial, f8MapNew.get(keyMaterial)
		// // + productMaterialWaste);
		// // }
		// // if (keyInteger.equals(keyMaterial)) {
		// // logger.info("[报关单 --- ] " + keyInteger + "成品数量 = "
		// // + hsAmount + " 损耗 = " + waste + " 单项用量 ="
		// // + unitUsed + " 成品单耗损  = " + productMaterialWaste);
		// // logger.info("[报关单 --- 累加  ] " + keyInteger
		// // + "productMaterialWaste  = "
		// // + f8MapNew.get(keyMaterial) + productMaterialWaste);
		// // }
		// // }
		// }

		// 以名称+规格+单位,客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口 ++
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011 ++
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012 ++
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		// CommonUtils.getBeginDate(Integer.valueOf(getYear(endDate)),0, 1);
		// CommonUtils.getBeginDate(Integer.valueOf(CommonUtils.getYear()), 0,
		// 1);
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailProduct", conditions, groupBy, leftOuter);
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;
			String typeCode = (String) objs[4] == null ? "" : (String) objs[4];
			List<Object[]> tempObjes = bomObjectMap.get(key);
			if (tempObjes == null) {
				logger.info("单据------短溢表计算 Dzsc Bom 不存在  !! == " + key);
				continue;
			}
			for (Object[] bomObjs : tempObjes) {
				// 料件
				String hsName1 = bomObjs[0] == null ? "" : (String) bomObjs[0];
				String hsSpec1 = bomObjs[1] == null ? "" : (String) bomObjs[1];
				String hsUnitName1 = bomObjs[2] == null ? ""
						: (String) bomObjs[2];

				// key=名称+"/"+规格+"/"+单位
				String keyMaterial = hsName1 + "/" + hsSpec1 + "/"
						+ hsUnitName1;

				// 损耗
				Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
				waste = waste * 0.01;

				// 单耗
				Double unitWear = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];

				// 单项用量 = 单耗 / (1-损耗);
				Double unitUsed = unitWear / (1 - waste);

				// 成品单耗损
				Double productMaterialWaste = hsAmount * unitUsed;
				if (typeCode.equals("2009") || typeCode.equals("2012")) {
					// 2009结转成品退货单
					// 2012已交货未结转期初单
					productMaterialWaste = -productMaterialWaste;
				} else if (typeCode.equals("2102") || typeCode.equals("2011")) {
					// 2102结转出口
					// 2011已结转未交货期初单
					productMaterialWaste = +productMaterialWaste;
				}
				String keyInteger = "锌合金压铸件//千克";
				// 结转成品 = 结转出口 + 已交货未结转期初单 - 结转成品退货单 - 已结转未交货期初单
				if (sendMapAmount.get(keyMaterial) == null) {
					sendMapAmount.put(keyMaterial, productMaterialWaste);
				} else {
					sendMapAmount.put(keyMaterial, sendMapAmount
							.get(keyMaterial)
							+ productMaterialWaste);
				}
				if (keyInteger.equals(keyMaterial)) {
					logger.info("[单据 ---(" + typeCode + ") ] " + keyInteger
							+ "成品数量 = " + hsAmount + " 损耗 = " + waste
							+ " 单项用量 =" + unitUsed + " 成品单耗损  = "
							+ productMaterialWaste);
					logger.info("[单据 ---累加  ] " + keyInteger
							+ "productMaterialWaste  = "
							+ sendMapAmount.get(keyMaterial)
							+ productMaterialWaste);
				}
			}
		}
		if (sendMapAmount.keySet().size() == 0) {
			System.out.println("------------------f7MapNew.keySet().size()==0");
		}
		// if (f8MapNew.keySet().size() == 0) {
		// System.out.println("------------------f8MapNew.keySet().size()==0");
		// }
	}

	/**
	 * bcs 的 Bom 检查
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initSendAmountByBcs(Date endDate, ParameterMaps parameterMaps) {
		List bcsEmsExgBills = this.casLeftoverMaterielDao.findBcsEmsExgBill();

		// 获取成品版本号对应的料件明细
		Map<String, Object[]> exgObjectMap = new HashMap<String, Object[]>();
		Map<String, Date> dateMap = new HashMap<String, Date>();
		for (int j = 0, n = bcsEmsExgBills.size(); j < n; j++) {
			Object[] bomObjs = (Object[]) bcsEmsExgBills.get(j);
			// 成品
			String hsName = bomObjs[0] == null ? "" : (String) bomObjs[0];
			String hsSpec = bomObjs[1] == null ? "" : (String) bomObjs[1];
			String hsUnitName = bomObjs[2] == null ? "" : (String) bomObjs[2];
			Date beginDate = (Date) bomObjs[3];
//			String contractId = (String) bomObjs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (exgObjectMap.get(key) == null) {
				exgObjectMap.put(key, bomObjs);
				dateMap.put(key, beginDate);
				logger.info(" begin 日期最近的一个  diffDays  == " + key + " --- "
						+ beginDate);
			} else {
				if (beginDate.after(dateMap.get(key))) {
					exgObjectMap.put(key, bomObjs);
					dateMap.put(key, beginDate);
					logger.info("diffDays 短溢表计算 日期最近的一个   == " + key + " --- "
							+ beginDate);
				}
			}
		}

		Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
		Iterator<Map.Entry<String, Object[]>> iteratorByExg = exgObjectMap
				.entrySet().iterator();
		for (; iteratorByExg.hasNext();) {
			Map.Entry<String, Object[]> e = iteratorByExg.next();
			Object[] exgObjs = e.getValue();
			String key = e.getKey();
			// 成品
			String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
			String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
			String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
//			Date beginDate = (Date) exgObjs[3];
			String contractId = (String) exgObjs[4];
			List contractBomList = this.casLeftoverMaterielDao.findContractBom(
					hsName, hsSpec, hsUnitName, contractId);
			if (contractBomList.size() == 0) {
				System.out.println("成品" + key + "没有找到bom");
			}
			bomObjectMap.put(key, contractBomList);
		}

		// 以 报关名+称规格+单位 为key的 map
		// Map<String, Double> f7MapNew = parameterMaps.f7MapNewByBcs;
		Map<String, Double> sendMapAmount = parameterMaps.sendMapAmount;

		// Map<String, Double> f8MapNew = parameterMaps.f8MapNewByBcs;

		// Map<String, Double> aInF7MapAmount =
		// parameterMaps.transFactOutMapAmount;

		// Iterator<Map.Entry<String, Double>> iterator = aInF7MapAmount
		// .entrySet().iterator();
		// for (; iterator.hasNext();) {
		// Map.Entry<String, Double> e = iterator.next();
		// Double hsAmount = e.getValue() == null ? 0.0 : e.getValue();
		// String key = e.getKey();
		// // 结转成品报关单
		// if (f8MapNew.get(key) == null) {
		// f8MapNew.put(key, hsAmount);
		// } else {
		// f8MapNew.put(key, f8MapNew.get(key) + hsAmount);
		// }
		// // List<Object[]> tempObjes = bomObjectMap.get(key);
		// // if (tempObjes == null) {
		// // logger.info("报关单------短溢表计算 Bcs Bom 不存在  !! == " + key);
		// // continue;
		// // }
		// // for (Object[] bomObjs : tempObjes) {
		// // // 料件
		// // String hsName = bomObjs[0] == null ? "" : (String) bomObjs[0];
		// // String hsSpec = bomObjs[1] == null ? "" : (String) bomObjs[1];
		// // String hsUnitName = bomObjs[2] == null ? ""
		// // : (String) bomObjs[2];
		// //
		// // // key=名称+"/"+规格+"/"+单位
		// // String keyMaterial = hsName + "/" + hsSpec + "/" + hsUnitName;
		// // String keyInteger = "五金配件/电脑显示器底座用/电脑配件/千克";
		// //
		// // // 损耗
		// // Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
		// // waste = waste / 100.0;
		// //
		// // // 单耗
		// // Double unitWear = bomObjs[4] == null ? 0.0
		// // : (Double) bomObjs[4];
		// //
		// // // 单项用量 = 单耗 / (1-损耗);
		// // Double unitUsed = unitWear / (1 - waste);
		// //
		// // // 成品单耗损
		// // Double productMaterialWaste = hsAmount * unitUsed;
		// //
		// // // 结转成品报关单
		// // if (f8MapNew.get(keyMaterial) == null) {
		// // f8MapNew.put(keyMaterial, productMaterialWaste);
		// // } else {
		// // f8MapNew.put(keyMaterial, f8MapNew.get(keyMaterial)
		// // + productMaterialWaste);
		// // }
		// // if (keyInteger.equals(keyMaterial)) {
		// // logger.info("[报关单 --- ] " + keyInteger + "成品数量 = "
		// // + hsAmount + " 损耗 = " + waste + " 单项用量 ="
		// // + unitUsed + " 成品单耗损  = " + productMaterialWaste);
		// // logger.info("[报关单 --- 累加  ] " + keyInteger
		// // + "productMaterialWaste  = "
		// // + f8MapNew.get(keyMaterial) + productMaterialWaste);
		// // }
		// // }
		// }

		// 以名称+规格+单位,客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口 ++
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011 ++
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012 ++
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailProduct", conditions, groupBy, leftOuter);
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;
			String typeCode = (String) objs[4] == null ? "" : (String) objs[4];
			List<Object[]> tempObjes = bomObjectMap.get(key);
			if (tempObjes == null) {
				logger.info("单据------短溢表计算 Dzsc Bom 不存在  !! == " + key);
				continue;
			}
			for (Object[] bomObjs : tempObjes) {
				// 料件
				String hsName1 = bomObjs[0] == null ? "" : (String) bomObjs[0];
				String hsSpec1 = bomObjs[1] == null ? "" : (String) bomObjs[1];
				String hsUnitName1 = bomObjs[2] == null ? ""
						: (String) bomObjs[2];

				// key=名称+"/"+规格+"/"+单位
				String keyMaterial = hsName1 + "/" + hsSpec1 + "/"
						+ hsUnitName1;

				// 损耗
				Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
				waste = waste / 100.0;

				// 单耗
				Double unitWear = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];

				// 单项用量 = 单耗 / (1-损耗);
				Double unitUsed = unitWear / (1 - waste);

				// 成品单耗损
				Double productMaterialWaste = hsAmount * unitUsed;
				if (typeCode.equals("2009") || typeCode.equals("2012")) {
					// 2009结转成品退货单
					// 2012已结转未交货期初单
					productMaterialWaste = -productMaterialWaste;
				} else if (typeCode.equals("2102") || typeCode.equals("2011")) {
					// 2102结转出口
					// 2011已交货未结转期初单
					productMaterialWaste = +productMaterialWaste;
				}
				String keyInteger = "锌合金压铸件//千克";
				// 结转成品 = 结转出口 + 已交货未结转期初单 - 结转成品退货单 - 已结转未交货期初单
				if (sendMapAmount.get(keyMaterial) == null) {
					sendMapAmount.put(keyMaterial, productMaterialWaste);
				} else {
					sendMapAmount.put(keyMaterial, sendMapAmount
							.get(keyMaterial)
							+ productMaterialWaste);
				}
				if (keyInteger.equals(keyMaterial)) {
					logger.info("[单据 ---(" + typeCode + ") ] " + keyInteger
							+ "成品数量 = " + hsAmount + " 损耗 = " + waste
							+ " 单项用量 =" + unitUsed + " 成品单耗损  = "
							+ productMaterialWaste);
					logger.info("[单据 ---累加  ] " + keyInteger
							+ "productMaterialWaste  = "
							+ sendMapAmount.get(keyMaterial)
							+ productMaterialWaste);
				}
			}
		}
	}

	/**
	 * 电子帐册 的 Bom 检查
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initSendAmountByBcus(Date endDate, ParameterMaps parameterMaps) {

		// /**
		// * 电子帐册
		// */
		// List bcusEmsExgBills =
		// this.casLeftoverMaterielDao.findBcusEmsExgBill();
		//
		// /**
		// * 报关成品名称规格单位 对应 名称 + 规格 + 单位
		// */
		// Map<String, Object[]> exgObjectMap = new HashMap<String, Object[]>();
		// Map<String, Date> dateMap = new HashMap<String, Date>();
		// for (int j = 0, n = bcusEmsExgBills.size(); j < n; j++) {
		// Object[] exgObjs = (Object[]) bcusEmsExgBills.get(j);
		// // 成品
		// String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
		// String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
		// String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
		// Date beginDate = (Date) exgObjs[3];
		// String contractId = (String) exgObjs[4];
		//
		// // key=名称+"/"+规格+"/"+单位
		// String key = hsName + "/" + hsSpec + "/" + hsUnitName;
		// if (exgObjectMap.get(key) == null) {
		// exgObjectMap.put(key, exgObjs);
		// dateMap.put(key, beginDate);
		// logger.info(" begin 日期最近的一个  diffDays  == " + key + " --- "
		// + beginDate);
		// } else {
		// if (beginDate.after(dateMap.get(key))) {
		// exgObjectMap.put(key, exgObjs);
		// dateMap.put(key, beginDate);
		// logger.info("diffDays 短溢表计算 日期最近的一个   == " + key + " --- "
		// + beginDate);
		// }
		// }
		// }
		//
		// /**
		// * key=名称+"/"+规格+"/"+单位 对应 获取成品版本号对应的料件明细
		// */
		// Map<String, List<Object[]>> bomObjectMap = new HashMap<String,
		// List<Object[]>>();
		//
		// Iterator<Map.Entry<String, Object[]>> iteratorByExg = exgObjectMap
		// .entrySet().iterator();
		// for (; iteratorByExg.hasNext();) {
		// Map.Entry<String, Object[]> e = iteratorByExg.next();
		// Object[] exgObjs = e.getValue();
		// String key = e.getKey();
		// // 成品
		// String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
		// String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
		// String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
		// Date beginDate = (Date) exgObjs[3];
		// String contractId = (String) exgObjs[4];
		//
		// // 查找对应的BOM
		// List emsHeadH2kBomList = this.casLeftoverMaterielDao
		// .findBcusEmsBomBill(hsName, hsSpec, hsUnitName, contractId);
		//
		// if (emsHeadH2kBomList.size() == 0) {
		// System.out.println("成品" + key + "没有找到bom");
		// }
		// bomObjectMap.put(key, emsHeadH2kBomList);
		// }
		/**
		 * key=名称+"/"+规格+"/"+单位 对应 获取成品版本号对应的料件明细
		 */
		Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();

		List bcusEmsExgBills = this.casLeftoverMaterielDao
				.findExingBcusEmsExgBill();
		for (int i = 0; i < bcusEmsExgBills.size(); i++) {
			Object[] exgObjs = (Object[]) bcusEmsExgBills.get(i);
			String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
			String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
			String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			String exgId = (String) exgObjs[3];
			// 查找对应的BOM(最大版本号)
			List emsHeadH2kBomList = this.casLeftoverMaterielDao
					.findBcusEmsBomBill(exgId);
			if (emsHeadH2kBomList.size() == 0) {
				System.out.println("成品" + key + "没有找到bom");
			}
			bomObjectMap.put(key, emsHeadH2kBomList);
		}

		/**
		 * 以 报关名+称规格+单位 为key的 map
		 */
		// Map<String, Double> f7MapNew = parameterMaps.f7MapNewByBcus;
		Map<String, Double> sendMapAmount = parameterMaps.sendMapAmount;

		// /**
		// *
		// */
		// Map<String, Double> f8MapNew = parameterMaps.f8MapNewByBcus;

		// /**
		// *
		// */
		// Map<String, Double> aInF7MapAmount =
		// parameterMaps.transFactOutMapAmount;
		//
		// Iterator<Map.Entry<String, Double>> iterator = aInF7MapAmount
		// .entrySet().iterator();
		// for (; iterator.hasNext();) {
		// Map.Entry<String, Double> e = iterator.next();
		// Double hsAmount = e.getValue() == null ? 0.0 : e.getValue();
		// String key = e.getKey();
		// // 结转成品报关单
		// if (f8MapNew.get(key) == null) {
		// f8MapNew.put(key, hsAmount);
		// } else {
		// f8MapNew.put(key, f8MapNew.get(key) + hsAmount);
		// }
		// }

		// 以名称+规格+单位,单据类型号码来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口 ++
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011 ++
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012 ++
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailProduct", conditions, groupBy, leftOuter);
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;
			String typeCode = (String) objs[4] == null ? "" : (String) objs[4];
			List<Object[]> tempObjes = bomObjectMap.get(key);
			if (tempObjes == null) {
				logger.info("单据------短溢表计算 bcus Bom 不存在  !! == " + key);
				continue;
			}
			for (Object[] bomObjs : tempObjes) {
				// 料件
				String hsName1 = bomObjs[0] == null ? "" : (String) bomObjs[0];
				String hsSpec1 = bomObjs[1] == null ? "" : (String) bomObjs[1];
				String hsUnitName1 = bomObjs[2] == null ? ""
						: (String) bomObjs[2];

				// key=名称+"/"+规格+"/"+单位
				String keyMaterial = hsName1 + "/" + hsSpec1 + "/"
						+ hsUnitName1;

				// 损耗
				Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
				waste = waste / 100.0;

				// 单耗
				Double unitWear = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];

				// 单项用量 = 单耗 / (1-损耗);
				Double unitUsed = unitWear / (1 - waste);

				// 成品单耗损
				Double productMaterialWaste = hsAmount * unitUsed;
				if (typeCode.equals("2009") || typeCode.equals("2012")) {
					// 2009结转成品退货单
					// 2012已交货未结转期初单
					productMaterialWaste = -productMaterialWaste;
				} else if (typeCode.equals("2102") || typeCode.equals("2011")) {
					// 2102结转出口
					// 2011已结转未交货期初单
					productMaterialWaste = +productMaterialWaste;
				}
				// 结转成品 = 结转出口 + 已交货未结转期初单 - 结转成品退货单 - 已结转未交货期初单
				if (sendMapAmount.get(keyMaterial) == null) {
					sendMapAmount.put(keyMaterial, productMaterialWaste);
				} else {
					sendMapAmount.put(keyMaterial, sendMapAmount
							.get(keyMaterial)
							+ productMaterialWaste);
				}

			}
		}
	}

	// private void initStatCusNameRelationHsByProductMap(
	// ParameterMaps parameterMaps) {
	// Map<String, String> statCusNameRelationHsByProductMap =
	// parameterMaps.statCusNameRelationHsByProductMap;
	//				
	// List<FactoryAndFactualCustomsRalation> listExistStatCusNameRelationHsn =
	// this.casDao
	// .findStatCusNameRelationHsn(MaterielType.FINISHED_PRODUCT);
	// for (int i = 0, n = listExistStatCusNameRelationHsn.size(); i < n; i++) {
	// FactoryAndFactualCustomsRalation sr =
	// listExistStatCusNameRelationHsn.get(i);
	// StatCusNameRelationHsn hsn = sr.getStatCusNameRelationHsn();
	//
	// String hsName = hsn.getCusName() == null ? "" : hsn.getCusName();
	// String hsSpec = hsn.getCusSpec() == null ? "" : hsn.getCusSpec();
	// String unitName = hsn.getCusUnit() == null ? "" : hsn.getCusUnit()
	// .getName();
	//
	// unitName = unitName == null ? "" : unitName;
	// String key = hsName + "/" + hsSpec + "/" + unitName;
	//
	// //
	// // value =
	// //
	// String tenHsName = sr.getCusName() == null ? "" : sr.getCusName();
	// String tenHsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
	// String tenUnitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
	// .getName();
	// tenUnitName = tenUnitName == null ? "" : tenUnitName;
	// String value = tenHsName + "/" + tenHsSpec + "/" + tenUnitName;
	//
	// if (statCusNameRelationHsByProductMap.get(key) == null) {
	// statCusNameRelationHsByProductMap.put(key, value);
	//				
	// }
	// }
	// }
	/**
	 * 7． 已结转未收货: 当相同客户名称的 （ “结转出口” 单据折算报关数量的累加 －“结转成品退货单” 单据折算报关数量的累加 <
	 * “结转报关出口”数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF3Map 已结转未收货
	 * 
	 * 8． 未结转已收货: 当相同客户名称的 （ “结转出口” 单据折算报关数量的累加 －“结转成品退货单” 单据折算报关数量的累加 >“结转报关出口”
	 * 数量的累加 ）时, 所有客户两者差额值的和。 carryForwardF4Map 未结转已收货
	 */
	private void initSendAmount(ParameterMaps parameterMaps) {
		Map<String, Double> f7Map = parameterMaps.sendMapAmount;// parameterMaps.f7Map;
		// Map<String, Double> f8Map = parameterMaps.f8Map;
		Map<String, Double> billDetailCpTranMap = parameterMaps.billDetailCpTranMap;// 结转成品
		System.out.println("------------billDetailCpTranMap:"
				+ billDetailCpTranMap.size());
		Map<String, String> ptNoKeyMap = parameterMaps.ptNoKeyMap;
		Map<String, FactoryAndFactualCustomsRalation> statCusNameRelationMtValueByMaterielMap = parameterMaps.statCusNameRelationMtValueByMaterielMap;
		Map<String, Double> tempbillDetailCpTranMap = new HashMap<String, Double>();
		Iterator<Map.Entry<String, Double>> iterator = billDetailCpTranMap
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Double> e = iterator.next();
			String ptNoKey = e.getKey();
			FactoryAndFactualCustomsRalation mt = statCusNameRelationMtValueByMaterielMap
					.get(ptNoKey);
			Double unitConvert = (mt == null || mt.getUnitConvert() == null) ? 1.0
					: mt.getUnitConvert();// 折算系数
			String key = ptNoKeyMap.get(ptNoKey);
			if (key == null) {
				continue;
			}
			Double value = e.getValue() == null ? 0.0 : e.getValue();
			value = value * unitConvert;
			Double tempValue = tempbillDetailCpTranMap.get(key);
			if (tempValue == null) {
				tempbillDetailCpTranMap.put(key, value);
			} else {
				tempbillDetailCpTranMap.put(key, tempValue + value);
			}
		}
		Iterator<Map.Entry<String, Double>> iterator2 = tempbillDetailCpTranMap
				.entrySet().iterator();
		while (iterator2.hasNext()) {
			Map.Entry<String, Double> e = iterator2.next();
			String key = e.getKey();
			Double hsAmount = e.getValue() == null ? 0.0 : e.getValue();
			double tempAmount = hsAmount;
			if (tempAmount > 0) {
				if (f7Map.get(key) == null) {
					f7Map.put(key, tempAmount);
				}
			} else {
				if (f7Map.get(key) == null) {
					f7Map.put(key, tempAmount);
				}
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// 
	// 下面的代码计算合同情况
	//
	// Bcus,Dzsc,Bcs 合同执行情况
	// F11、合同执行情况|合同进口数
	// 报关单料件进口数量：
	// (料件进口 + 料件转厂 - 退料出口)
	// F12、合同执行情况|合同出口耗料
	// 报关单成品出口折料数量：
	// (成品出口 + 转厂出口 + 返工复出 - 退厂返工)
	// ///////////////////////////////////////////////////////////////////
	/**
	 * 初始化报关成品来自联网监管customsProductMap
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initCustomsProductMapByBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,v.id "
				+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",EmsHeadH2kVersion v left join v.emsHeadH2kExg exg where a.commSerialNo = exg.seqNum "
				+ " and a.version = v.version  "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.modifyMark=? "
				+ " and exg.emsHeadH2k.historyState=?  "
				+ " group by a.baseCustomsDeclaration.impExpType,v.id "
				+ " order by v.id "; // order by 确保 bom id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));
		// parameters.add(beginDate);

		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_EXPORT); // 直接出口
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT); // 转厂出口
		parameters.add(ImpExpType.REWORK_EXPORT); // 返工复出
		parameters.add(ImpExpType.BACK_FACTORY_REWORK); // 退厂返工
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT); // 余料结转(进口报关单)
		// 这个好像根本没用！！！

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("0");
		parameters.add(false);
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [联网监管] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste;

		/**
		 * 成品转厂损耗量映射
		 */
		Map<String, Double> transFactOutMapAmount = parameterMaps.transFactOutMapAmount;

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // version guid
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);
			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);
				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casLeftoverMaterielDao
						.findEmsHeadH2kBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (emsHeadH2kBomList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcus Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste / 100.0);
						System.out.println("bcus-----损耗:" + waste + "单耗:"
								+ unitWear + "单项用量:" + unitUsed);

						// 成品单耗
						Double productMaterialWaste = tempHsAmount * unitUsed;

						// 出口成品损耗量映射
						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT // 直接出口
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT // 返工复出
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT) {// 转厂出口
							// 返工复出
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										-productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												- productMaterialWaste);
							}
						}

						// 成品转厂报关数
						if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT) { // 转厂出口
							if (transFactOutMapAmount.get(key) == null) {
								transFactOutMapAmount.put(key,
										productMaterialWaste);
							} else {
								transFactOutMapAmount.put(key,
										transFactOutMapAmount.get(key)
												+ productMaterialWaste);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 初始化报关成品来自联网监管customsProductMap
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initCustomsMaterielMapByBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		// System.out.println("此地ByBcus的startdate="
		// + nowToDate(CommonUtils.getBeginDate(Integer
		// .valueOf(getYear(endDate)), 0, 1)));

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in ( ?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_IMPORT); // 直接进口
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT); // 退料出口

		// wss:加上海关批准内销
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES); // 海关批准内销

		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [联网监管] 报关Material 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 合同进口映射
		 */
		Map<String, Double> customsMaterialMapAmount = parameterMaps.customsMaterialMapAmount;

		/**
		 * 海关批准内销
		 */
		Map<String, Double> customsMaterialDOMESTICSALESMapAmount = parameterMaps.customsMaterialDOMESTICSALESMapAmount;

		/**
		 * 转厂进口映射
		 */
		Map<String, Double> transFactInMapAmount = parameterMaps.transFactInMapAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			if (typeCode.intValue() == ImpExpType.DIRECT_IMPORT) {// 料件进口
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}

			} else if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {// 料件转厂
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
				if (transFactInMapAmount.get(key) == null) {
					transFactInMapAmount.put(key, tempHsAmount);
				} else {
					transFactInMapAmount.put(key, transFactInMapAmount.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.BACK_MATERIEL_EXPORT) {// 退料出口
				tempHsAmount = -tempHsAmount;
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.MATERIAL_DOMESTIC_SALES) {// 海关批准内销
				// tempHsAmount = -tempHsAmount;
				if (customsMaterialDOMESTICSALESMapAmount.get(key) == null) {
					customsMaterialDOMESTICSALESMapAmount
							.put(key, tempHsAmount);
				} else {
					customsMaterialDOMESTICSALESMapAmount.put(key,
							customsMaterialDOMESTICSALESMapAmount.get(key)
									+ tempHsAmount);
				}
			}
		}
	}

	// /**
	// * 抓取结转单据数量
	// *
	// * @param beginDate
	// * @param endDate
	// * @param parameterMaps
	// * @param progressInfo
	// */
	// private void initCustomsTransFactoryMapByBcus(Date beginDate, Date
	// endDate,
	// ParameterMaps parameterMaps, ProgressInfo progressInfo) {
	// List<Object> parameters = new ArrayList<Object>();
	// String selects =
	// " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
	// +
	// " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration left join a.unit "
	// + " where a.baseCustomsDeclaration.effective = ? "
	// + " and a.baseCustomsDeclaration.declarationDate >= ? "
	// + " and a.baseCustomsDeclaration.declarationDate <= ? "
	// + " and a.baseCustomsDeclaration.impExpType in ( ?,? ) "
	// + " and a.baseCustomsDeclaration.company.id = ? "
	// +
	// " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
	// parameters.add(Boolean.valueOf(true));
	// parameters.add(CommonUtils.getBeginDate(Integer
	// .valueOf(getYear(endDate)), 0, 1));
	// parameters.add(CommonUtils.getEndDate(endDate));
	// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
	// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
	// // 2007-12-3
	// parameters.add(CommonUtils.getCompany().getId());
	// // 成品
	// long beginTime = System.currentTimeMillis();
	// List list = casDao.commonSearch(selects, parameters.toArray());
	// String clientTipMessage = "短溢表计算 开始查询所有 [BCS] 报关Material 的共用时间: "
	// + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
	// logger.info(clientTipMessage);
	// progressInfo.setHintMessage(clientTipMessage);
	//
	// /**
	// * 转厂进口报关数
	// */
	// Map<String, Double> aInF6MapAmount = parameterMaps.aInF6MapAmount;
	//
	// /**
	// * 转厂出口报关数
	// */
	// Map<String, Double> aInF7MapAmountNewByBcus =
	// parameterMaps.aInF7MapAmountNewByBcus;
	// for (int i = 0, size = list.size(); i < size; i++) {
	// Object[] objs = (Object[]) list.get(i);
	// Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; //
	// 报关Materiel数量
	// Integer typeCode = (Integer) objs[1]; // 类型代码
	// String hsName = objs[2] == null ? "" : (String) objs[2];
	// String hsSpec = objs[3] == null ? "" : (String) objs[3];
	// String hsUnitName = objs[4] == null ? "" : (String) objs[4];
	//
	// // key=名称+"/"+规格+"/"+单位
	// String key = hsName + "/" + hsSpec + "/" + hsUnitName;
	//
	// if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT) { // 转厂出口
	// if (aInF7MapAmountNewByBcus.get(key) == null) { //
	// aInF7MapAmountNewByBcus.put(key, tempHsAmount);
	// } else {
	// aInF7MapAmountNewByBcus.put(key, aInF7MapAmountNewByBcus
	// .get(key)
	// + tempHsAmount);
	// }
	// } else if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
	// // 料件转厂
	// if (aInF6MapAmount.get(key) == null) {
	// aInF6MapAmount.put(key, tempHsAmount);
	// } else {
	// aInF6MapAmount.put(key, aInF6MapAmount.get(key)
	// + tempHsAmount);
	// }
	// }
	// }
	// }

	/**
	 * 初始化报关成品来自纸质手册customsProductMap
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	private void initCustomsProductMapByBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		System.out.println("此地ByBcs的startdate="
				+ nowToDate(CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1)));
		System.out.println("此地ByBcs的date="
				+ nowToDate(CommonUtils.getBeginDate(Integer
						.valueOf(CommonUtils.getYear()), 0, 1)));
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
				+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				// + " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.contract.declareState=?" // or
				// exg.contract.isCancel=?
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		// parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
		// .getYear()), 0, 1));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		// parameters.add(true);

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [无纸通关] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste;// 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> exgIdMap = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String exgId = (String) objs[2]; // 成品 id
			if (exgId == null) {
				continue;
			}

			// 为了去掉重复的 exgId
			exgIdMap.put(exgId, exgId);
			tempList.add(objs);
			if ((i != 0 && (exgIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(exgIdMap.values());
				List contractBomList = this.casLeftoverMaterielDao
						.findContractBomDetail(versionIdList);
				versionIdList.clear();
				exgIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (contractBomList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = contractBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) contractBomList.get(j);
					String tempExgId = (String) bomObjs[5];
					if (bomObjectMap.get(tempExgId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempExgId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempExgId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempExgId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempExgId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcs Bom 明细为空  !! == " + tempExgId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste / 100.0);

						// 成品单耗损
						Double productMaterialWaste = tempHsAmount * unitUsed;
						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT // 直接出口
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT // 转厂出口
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) { // 返工复出

							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										-productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												- productMaterialWaste);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 初始化报关成品转厂出口耗料（加上已核销的部分） 电子化手册Bcs
	 * 
	 * @param beginDate
	 * @param endDate
	 *            wss2010.11.17
	 */
	private void initTransFactOutMapByBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),exg.id "
				+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		System.out.println("wss 已核销的成品转厂出口list.size = " + list.size());

		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 转厂出口  的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 转厂出口报关数
		 */
		Map<String, Double> transFactOutMapAmount = parameterMaps.transFactOutMapAmount;// 转厂出口耗用

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> exgIdMap = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String exgId = (String) objs[1]; // 成品 id
			if (exgId == null) {
				continue;
			}

			// 为了去掉重复的 exgId
			exgIdMap.put(exgId, exgId);
			tempList.add(objs);
			if ((i != 0 && (exgIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算电子化手册 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				List<String> lsVersionId = new ArrayList<String>();
				lsVersionId.addAll(exgIdMap.values());
				List lsBom = this.casLeftoverMaterielDao
						.findContractBomDetail(lsVersionId);
				lsVersionId.clear();
				exgIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsBom.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> mapBom = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = lsBom.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) lsBom.get(j);
					String tempExgId = (String) bomObjs[5];
					if (mapBom.get(tempExgId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						mapBom.put(tempExgId, temp);
					} else {
						List<Object[]> temp = mapBom.get(tempExgId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					String tempExgId = (String) objs[1]; // version guid
					List<Object[]> tempObjes = mapBom.get(tempExgId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcs Bom 明细为空  !! == " + tempExgId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste / 100.0);

						// 成品单耗损
						Double productMaterialWaste = tempHsAmount * unitUsed;

						if (transFactOutMapAmount.get(key) == null) {
							transFactOutMapAmount
									.put(key, productMaterialWaste);
						} else {
							transFactOutMapAmount.put(key,
									transFactOutMapAmount.get(key)
											+ productMaterialWaste);
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 初始化报关成品来自电子化手册customsProductMap
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initCustomsMaterielMapByBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		System.out.println("此地ByBcs的startdate="
				+ nowToDate(CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1)));
		System.out.println("此地ByBcs的date="
				+ nowToDate(CommonUtils.getBeginDate(Integer
						.valueOf(CommonUtils.getYear()), 0, 1)));

		System.out.println("wss beginDate = " + beginDate);
		System.out.println("wss endDate = " + endDate);

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from BcsCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				// + " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in ( ?,?,?,?,? ) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in (select b.emsNo from Contract b where b.company.id = ? and b.declareState = ?"
				+ " and b.emsNo is not null)"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
		parameters.add(Boolean.valueOf(true));
		// parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
		// .getYear()), 0, 1));
		// CommonUtils.getBeginDate(Integer.valueOf(getYear(endDate)), 0, 1)
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_IMPORT); // 直接进口
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT); // 退料出口
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 余料结转

		// wss:2010.07.04加
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES); // 海关批准内销

		// 2007-12-3
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [BCS] 报关Material 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 合同进口料件映射
		 */
		Map<String, Double> customsMaterialMapAmount = parameterMaps.customsMaterialMapAmount;

		/**
		 * 海关批准内销
		 */
		Map<String, Double> customsMaterialDOMESTICSALESMapAmount = parameterMaps.customsMaterialDOMESTICSALESMapAmount;

		// /**
		// * 转厂进口报关数
		// */
		// Map<String, Double> transFactInMapAmount =
		// parameterMaps.transFactInMapAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			if (typeCode.intValue() == ImpExpType.DIRECT_IMPORT) {// 料件进口
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.REMAIN_FORWARD_IMPORT) {// 余料结转转入
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {// 料件转厂
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
				// if (transFactInMapAmount.get(key) == null) {
				// transFactInMapAmount.put(key, tempHsAmount);
				// } else {
				// transFactInMapAmount.put(key, transFactInMapAmount.get(key)
				// + tempHsAmount);
				// }
			} else if (typeCode.intValue() == ImpExpType.BACK_MATERIEL_EXPORT) {// 退厂返工
				tempHsAmount = -tempHsAmount;// 这个是减的
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			}
			// wss:2010.07.04加
			else if (typeCode.intValue() == ImpExpType.MATERIAL_DOMESTIC_SALES) {// 海关批准内销
				if (customsMaterialDOMESTICSALESMapAmount.get(key) == null) {
					customsMaterialDOMESTICSALESMapAmount
							.put(key, tempHsAmount);
				} else {
					customsMaterialDOMESTICSALESMapAmount.put(key,
							customsMaterialDOMESTICSALESMapAmount.get(key)
									+ tempHsAmount);
				}
			}
		}
	}

	/**
	 * 初始化报关成品转厂进口 （加上已核销部分） 电子化手册Bcs
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate截止日期
	 *            wss2010.11.17
	 */
	private void initTransFactInMapByBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from BcsCustomsDeclarationCommInfo a "
				+ " left join a.baseCustomsDeclaration "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in "
				+ " (select b.emsNo "
				+ " from Contract b "
				+ " where b.company.id = ? "
				+ " and b.emsNo is not null)"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";

		parameters.add(Boolean.valueOf(true));// 已生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		System.out.println("wss 已核销的料件转厂进口list.size = " + list.size());
		String clientTipMessage = "短溢表计算 开始查询 [电子化手册] 料件转厂  报关Material 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 转厂进口报关数
		 */
		Map<String, Double> transFactInMapAmount = parameterMaps.transFactInMapAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
//			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			if (transFactInMapAmount.get(key) == null) {
				transFactInMapAmount.put(key, tempHsAmount);
			} else {
				transFactInMapAmount.put(key, transFactInMapAmount.get(key)
						+ tempHsAmount);
			}
		}
	}

	// /**
	// * 抓取结转单据数量
	// *
	// * @param beginDate
	// * @param endDate
	// * @param parameterMaps
	// * @param progressInfo
	// */
	// private void initCustomsTransFactoryMapByBcs(Date beginDate, Date
	// endDate,
	// ParameterMaps parameterMaps, ProgressInfo progressInfo) {
	// List<Object> parameters = new ArrayList<Object>();
	// String selects =
	// " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
	// +
	// " from BcsCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration left join a.unit "
	// + " where a.baseCustomsDeclaration.effective = ? "
	// // + " and a.baseCustomsDeclaration.declarationDate >= ? "
	// + " and a.baseCustomsDeclaration.declarationDate <= ? "
	// + " and a.baseCustomsDeclaration.impExpType in ( ?,? ) "
	// + " and a.baseCustomsDeclaration.company.id = ? "
	// +
	// " and a.baseCustomsDeclaration.emsHeadH2k in (select b.emsNo from Contract b where b.company.id = ? and b.declareState = ?"
	// + " and b.emsNo is not null)"
	// +
	// " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
	// parameters.add(Boolean.valueOf(true));
	// // parameters.add(CommonUtils.getBeginDate(Integer
	// // .valueOf(getYear(endDate)), 0, 1));
	//
	// parameters.add(CommonUtils.getEndDate(endDate));
	// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
	// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
	// // 2007-12-3
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(DeclareState.PROCESS_EXE);
	// // 成品
	// long beginTime = System.currentTimeMillis();
	// List list = casDao.commonSearch(selects, parameters.toArray());
	// String clientTipMessage = "短溢表计算 开始查询所有 [BCS] 报关Material 的共用时间: "
	// + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
	// logger.info(clientTipMessage);
	// progressInfo.setHintMessage(clientTipMessage);
	//
	// /**
	// * 转厂进口报关数
	// */
	// Map<String, Double> aInF6MapAmount = parameterMaps.aInF6MapAmount;
	//
	// /**
	// * 转厂出口报关数
	// */
	// Map<String, Double> aInF7MapAmountNewByBcs =
	// parameterMaps.aInF7MapAmountNewByBcs;
	// for (int i = 0, size = list.size(); i < size; i++) {
	// Object[] objs = (Object[]) list.get(i);
	// Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; //
	// 报关Materiel数量
	// Integer typeCode = (Integer) objs[1]; // 类型代码
	// String hsName = objs[2] == null ? "" : (String) objs[2];
	// String hsSpec = objs[3] == null ? "" : (String) objs[3];
	// String hsUnitName = objs[4] == null ? "" : (String) objs[4];
	//
	// // key=名称+"/"+规格+"/"+单位
	// String key = hsName + "/" + hsSpec + "/" + hsUnitName;
	//
	// if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT) { // 转厂出口
	// if (aInF7MapAmountNewByBcs.get(key) == null) { //
	// aInF7MapAmountNewByBcs.put(key, tempHsAmount);
	// } else {
	// aInF7MapAmountNewByBcs.put(key, aInF7MapAmountNewByBcs
	// .get(key)
	// + tempHsAmount);
	// }
	// } else if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
	// // 料件转厂
	// if (aInF6MapAmount.get(key) == null) {// add by zy 2007-5-9
	// aInF6MapAmount.put(key, tempHsAmount);
	// } else {
	// aInF6MapAmount.put(key, aInF6MapAmount.get(key)
	// + tempHsAmount);
	// }
	// }
	// }
	// }

	/**
	 * 初始化报关成品来DZSC customsProductMap
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	private void initCustomsProductMapByDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
				+ " from DzscCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",DzscEmsExgBill exg left join exg.dzscEmsPorHead "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				/* + " and a.baseCustomsDeclaration.declarationDate >= ? " */
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.dzscEmsPorHead.declareState=? "
				// +
				// " and (exg.dzscEmsPorHead.declareState=? or (exg.dzscEmsPorHead.declareState=?"
				// + " and exg.dzscEmsPorHead.endDate<=?))"
				// + " and exg.contract.isCancel=? "
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DzscState.EXECUTE);
		// parameters.add(DzscState.CHECK_CANCEL);
		// parameters.add(CommonUtils.getEndDate(endDate));
		// parameters.add(false);
		// parameters.add(true);
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [电子手册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;
//		int xx = 0;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // version guid
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);
			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casLeftoverMaterielDao
						.findDzscEmsBomBill(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (emsHeadH2kBomList.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste);

						// 成品单耗损
						Double productMaterialWaste = tempHsAmount * unitUsed;
						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) {// 直接出口

							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							if (customsProductMapWaste.get(key) == null) {
								customsProductMapWaste.put(key,
										-productMaterialWaste);
							} else {
								customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												- productMaterialWaste);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 初始化报关成品 加上已核销的部分 电子手册dzsc
	 * 
	 * @param beginDate
	 * @param endDate
	 *            wss2010.11.17整理
	 */
	private void initTransFactOutMapByDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
				+ " from DzscCustomsDeclarationCommInfo a  left join a.baseCustomsDeclaration, "
				+ " DzscEmsExgBill exg left join exg.dzscEmsPorHead "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		System.out.println("wss 已核销的成品转厂出口list.size = " + list.size());
		String clientTipMessage = "边角料计算 开始查询 [电子手册]成品转厂出口（已核销合同部分） 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 转厂出口报关数
		 */
		Map<String, Double> transFactOutMapAmount = parameterMaps.transFactOutMapAmount;

		List<Object[]> lsTemp = new ArrayList<Object[]>();
		Map<String, String> mapVersionId = new HashMap<String, String>();
		int page = 20;
//		int xx = 0;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2];
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			mapVersionId.put(versionId, versionId);
			lsTemp.add(objs);

			if ((i != 0 && (mapVersionId.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(mapVersionId.values());
				List lsBom = this.casLeftoverMaterielDao
						.findDzscEmsBomBill(versionIdList);
				versionIdList.clear();
				mapVersionId.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsBom.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					lsTemp.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> mapBomObject = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = lsBom.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) lsBom.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (mapBomObject.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						mapBomObject.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = mapBomObject.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = lsTemp.size(); j < n; j++) {
					objs = (Object[]) lsTemp.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
//					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = mapBomObject.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste);

						// 成品单耗损
						Double totalUnitUsed = tempHsAmount * unitUsed;

						// 转厂出口耗用
						if (transFactOutMapAmount.get(key) == null) {
							transFactOutMapAmount.put(key, totalUnitUsed);
						} else {
							transFactOutMapAmount.put(key,
									transFactOutMapAmount.get(key)
											+ totalUnitUsed);
						}
					}
				}
				lsTemp.clear();
			}
		}
	}

	/**
	 * 初始化报关成品来自纸质手册 Dzsc
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	private void initCustomsMaterielMapByDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from DzscCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				/* + " and a.baseCustomsDeclaration.declarationDate >= ? " */
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in ( ?,?,?,?,?) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in ("
				+ " select b.emsNo from DzscEmsPorHead b where b.company.id = ? "
				+ " and b.declareState = ? "// +
				// " and (b.declareState = ? or (b.declareState=? and b.endDate<=?))"
				+ " and b.emsNo is not null )"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
		parameters.add(Boolean.valueOf(true));
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂进口
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 余料结转进

		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES); // 海关批准内销
		// 2007-12-3
		parameters.add(CommonUtils.getCompany().getId());
		// edit by xxm 2007-12-3
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(DzscState.EXECUTE);
		// parameters.add(DzscState.CHECK_CANCEL);
		// parameters.add(CommonUtils.getEndDate(endDate));
		// parameters.add(new Boolean(false));
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [DZSC] 报关Material ["
				+ list.size() + "] 记录 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		/**
		 * 合同进口料件映射
		 */
		Map<String, Double> customsMaterialMapAmount = parameterMaps.customsMaterialMapAmount;

		// /**
		// * 转厂进口报关数
		// */
		// Map<String, Double> transFactInMapAmount =
		// parameterMaps.transFactInMapAmount;

		/**
		 * 海关批准内销
		 */
		Map<String, Double> customsMaterialDOMESTICSALESMapAmount = parameterMaps.customsMaterialDOMESTICSALESMapAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (typeCode.intValue() == ImpExpType.DIRECT_IMPORT) {// 料件进口
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.REMAIN_FORWARD_IMPORT) {// 余料结转转入
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {// 料件转厂
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
				// if (transFactInMapAmount.get(key) == null) {
				// transFactInMapAmount.put(key, tempHsAmount);
				// } else {
				// transFactInMapAmount.put(key, transFactInMapAmount.get(key)
				// + tempHsAmount);
				// }
			} else if (typeCode.intValue() == ImpExpType.BACK_MATERIEL_EXPORT) {// 退料出口
				tempHsAmount = -tempHsAmount;
				if (customsMaterialMapAmount.get(key) == null) {
					customsMaterialMapAmount.put(key, tempHsAmount);
				} else {
					customsMaterialMapAmount.put(key, customsMaterialMapAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.MATERIAL_DOMESTIC_SALES) {// 海关批准内销
				if (customsMaterialDOMESTICSALESMapAmount.get(key) == null) {
					customsMaterialDOMESTICSALESMapAmount
							.put(key, tempHsAmount);
				} else {
					customsMaterialDOMESTICSALESMapAmount.put(key,
							customsMaterialDOMESTICSALESMapAmount.get(key)
									+ tempHsAmount);
				}
			}
		}
		// this.initCustomsTransFactoryMapByDzsc(beginDate, endDate,
		// parameterMaps, progressInfo);
	}

	/**
	 * 初始化报关料件转厂 加上已核销部分 电子手册 Dzsc
	 * 
	 * @param beginDate开始日期
	 * @param endDate
	 *            截止日期 wss2010.11.17整理
	 */
	private void initTransFactInMapByDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from DzscCustomsDeclarationCommInfo a "
				+ " left join a.baseCustomsDeclaration "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in ("
				+ " select b.emsNo "
				+ " from DzscEmsPorHead b "
				+ " where b.company.id = ? "
				+ " and b.emsNo is not null )"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂进口
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		System.out.println("wss 已核销的料件转厂list.size = " + list.size());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子手册] 料件转厂（已核销部分） ["
				+ list.size() + "] 记录 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 转厂进口报关数
		 */
		Map<String, Double> transFactInMapAmount = parameterMaps.transFactInMapAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
//			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (transFactInMapAmount.get(key) == null) {
				transFactInMapAmount.put(key, tempHsAmount);
			} else {
				transFactInMapAmount.put(key, transFactInMapAmount.get(key)
						+ tempHsAmount);
			}
		}
	}

	// /**
	// * 抓取结转单据数量
	// *
	// * @param beginDate
	// * @param endDate
	// * @param parameterMaps
	// * @param progressInfo
	// */
	// private void initCustomsTransFactoryMapByDzsc(Date beginDate, Date
	// endDate,
	// ParameterMaps parameterMaps, ProgressInfo progressInfo) {
	// List<Object> parameters = new ArrayList<Object>();
	// String selects =
	// " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
	// +
	// " from DzscCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration left join a.unit "
	// + " where a.baseCustomsDeclaration.effective = ? "
	// // + " and a.baseCustomsDeclaration.declarationDate >= ? "
	// + " and a.baseCustomsDeclaration.declarationDate <= ? "
	// + " and a.baseCustomsDeclaration.impExpType in ( ?,? ) "
	// + " and a.baseCustomsDeclaration.company.id = ? "
	// +
	// " and a.baseCustomsDeclaration.emsHeadH2k in (select b.emsNo from DzscEmsPorHead b where b.company.id = ? "
	// + " and b.declareState = ?)"
	// +
	// " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
	// parameters.add(Boolean.valueOf(true));
	// // parameters.add(CommonUtils.getBeginDate(Integer
	// // .valueOf(getYear(endDate)), 0, 1));
	//
	// parameters.add(CommonUtils.getEndDate(endDate));
	// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
	// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
	// // 2007-12-3
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(DzscState.EXECUTE);
	// // 成品
	// long beginTime = System.currentTimeMillis();
	// List list = casDao.commonSearch(selects, parameters.toArray());
	// String clientTipMessage = "短溢表计算 开始查询所有 [Dzsc] 报关Material 的共用时间: "
	// + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
	// logger.info(clientTipMessage);
	// progressInfo.setHintMessage(clientTipMessage);
	//
	// /**
	// * 转厂进口报关数
	// */
	// Map<String, Double> aInF6MapAmount = parameterMaps.aInF6MapAmount;
	//
	// /**
	// * 转厂出口报关数
	// */
	// Map<String, Double> aInF7MapAmountNewByDzsc =
	// parameterMaps.aInF7MapAmountNewByDzsc;
	// for (int i = 0, size = list.size(); i < size; i++) {
	// Object[] objs = (Object[]) list.get(i);
	// Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; //
	// 报关Materiel数量
	// Integer typeCode = (Integer) objs[1]; // 类型代码
	// String hsName = objs[2] == null ? "" : (String) objs[2];
	// String hsSpec = objs[3] == null ? "" : (String) objs[3];
	// String hsUnitName = objs[4] == null ? "" : (String) objs[4];
	//
	// // key=名称+"/"+规格+"/"+单位
	// String key = hsName + "/" + hsSpec + "/" + hsUnitName;
	//
	// if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT) { // 转厂出口
	// if (aInF7MapAmountNewByDzsc.get(key) == null) { //
	// aInF7MapAmountNewByDzsc.put(key, tempHsAmount);
	// } else {
	// aInF7MapAmountNewByDzsc.put(key, aInF7MapAmountNewByDzsc
	// .get(key)
	// + tempHsAmount);
	// }
	// } else if (typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
	// // 料件转厂
	// if (aInF6MapAmount.get(key) == null) {
	// aInF6MapAmount.put(key, tempHsAmount);
	// } else {
	// aInF6MapAmount.put(key, aInF6MapAmount.get(key)
	// + tempHsAmount);
	// }
	// }
	// }
	// }

	/**
	 * 取2小数位 add by xxm 2007-12-4
	 */
	public Double formatBigD(Double amount) {
		if (amount == null) {
			return Double.valueOf(0);
		}
		BigDecimal bd = new BigDecimal(amount);
		String amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(amountStr);
	}

	// /**
	// * @return the imgOrgUseInfoLogic
	// */
	// public ImgOrgUseInfoLogic getImgOrgUseInfoLogic() {
	// return imgOrgUseInfoLogic;
	// }
	//
	// /**
	// * @param imgOrgUseInfoLogic
	// * the imgOrgUseInfoLogic to set
	// */
	// public void setImgOrgUseInfoLogic(ImgOrgUseInfoLogic imgOrgUseInfoLogic)
	// {
	// this.imgOrgUseInfoLogic = imgOrgUseInfoLogic;
	// }

	private void initInvoiceVerificationMap(Date beginDate, Date endDate,
			ParameterMaps parameterMaps) {
		Map<String, Double> invoiceVerificationAmountMap = parameterMaps.invoiceVerificationAmountMap;
		String hsql = "select a.cuName,a.cuSpec,a.unit.name,sum(a.canceledNum)"
				+ " from CasInvoiceInfo a where a.casInvoice.validDate>= ? and a.casInvoice.validDate<= ? "
				+ " and a.canceled=? "
				+ " and a.company.id=? group by a.cuName,a.cuSpec,a.unit.name";
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getBeginDate(beginDate));
		params.add(CommonUtils.getEndDate(endDate));
		params.add(true);
		params.add(CommonUtils.getCompany().getId());
		List list = this.casDao.commonSearch(hsql, params.toArray());
		System.out.println("wss 核销数list.size " + list.size());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String hsName = (objs[0] == null ? "" : objs[0].toString().trim());
			String hsSpec = (objs[1] == null ? "" : objs[1].toString().trim());
			String hsUnit = (objs[2] == null ? "" : objs[2].toString().trim());
			Double hsAmount = (objs[3] == null ? 0.0 : Double.valueOf(objs[3]
					.toString().trim()));
			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnit;
			invoiceVerificationAmountMap.put(key, hsAmount);

			System.out.println("wss 核销数key:" + key);
			System.out.println("wss hsAmount:" + hsAmount);
		}
	}

	/**
	 * 查找并计算短溢表(按报关名称分时间段进行查询)
	 * 
	 * @param request发送请求
	 * @param beginDate开始日期
	 * @param endDate
	 *            结束日期
	 * @param projectTypeParam
	 *            手册类型
	 * @param isFromCheckData
	 *            库存数据是否来源于盘点导入
	 * @param isAll
	 *            是否体现合同中所有料件的短溢情况
	 * @return 短溢表计算结果BalanceInfo
	 * @author wss2010.10.27 测试版本(Copy原来的方法进行修改) hcl 修改
	 */
	public List<BalanceInfo> findBalanceInfoTest(Date beginDate, Date endDate,
			ProjectTypeParam projectTypeParam, String taskId,
			boolean isFromCheckData, boolean isAll, boolean useNewContract) {

		// 判断是否单选
		boolean isSingleSelect = this.isSingleSelect(projectTypeParam);
		System.out.println("wss 手册类型是否单选？ = " + isSingleSelect);

		// 判断是否已经有客户端在运行
		String companyId = CommonUtils.getCompany().getId();
		Boolean isRun = runMap.get(companyId);
		if (isRun != null && isRun.booleanValue() == true) {
			throw new RuntimeException("计算短溢表正在进行中请稍后再运行 ！");
		}
		runMap.put(companyId, true);

		// 好戏开始了
		try {
			// 用于缓存的内部类，里边的map缓存着需要的数据
			ParameterMaps parameterMaps = new ParameterMaps();
			ProgressInfo progressInfo = ProcExeProgress.getInstance()
					.getProgressInfo(taskId);
			String clientTipMessage = "1.初始化基础对应...";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/** 第一步：弄好后面会用到的一些基础对应 **/
			this.initMapBasicInfo(parameterMaps);

			// 如果库存数据来源于工厂单据
			if (!isFromCheckData) {
				clientTipMessage = "2.初始化成品、半成品单据 BOM折料...";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				/** 第二步：初始化 成品单据 bom 折料 用Map缓存 **/
				this.initMapBillDetailProduct(endDate, parameterMaps,
						progressInfo);

				/** 第三步：初始化 半成品单据 bom 折料 用Map缓存 **/
				this.initMapBillDetailHalfProduct(endDate, parameterMaps,
						progressInfo);
			}

			/** 第四步：电子帐册 */
			if (projectTypeParam.getIsBcus() == true) {

				System.out.println("电子帐册");
				clientTipMessage = "3.计算  电子帐册  报关单信息...";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				// 1.出口成品(报关单) 损耗映射
				this.initMapCustomsProductWasterBcus(beginDate, endDate,
						parameterMaps, progressInfo);

				// 2.转厂折料
				this.initMapTransFactOutWasterBcus(beginDate, endDate,
						parameterMaps, progressInfo);

				// 3.料件进口(报关单) 映射
				this.initMapCustomsMaterielBcus(beginDate, endDate,
						parameterMaps, progressInfo);

				// 4.计算F6与F9转厂进口(报关单与单据 )
				this.initMapTransFactInBcus(beginDate, endDate, parameterMaps,
						progressInfo);

				if (isSingleSelect) {
					// 3.根据单据查询送货数量，将送货数量按照帐册单耗折算成料件。
					this.initMapSendAmountBcus(endDate, parameterMaps);
				}
			}

			/*** 第五步：电子化手册 **/
			if (projectTypeParam.getIsBcs() == true) {
				System.out.println("电子化手册");
				clientTipMessage = "3.计算 电子化手册 报关单信息... !!";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				// 1.出口成品(报关单)损耗映射 --只包含正在执行的
				this.initMapCustomsProductWasterBcs(beginDate, endDate,
						parameterMaps, progressInfo);

				// 2.计算转厂的单据与报关转厂出口之间的差存放在beforResult7Map与beforResult8Map中 损耗映射
				if (useNewContract) {
					this.initMapTransFactOutWasterBcsByNewContract(beginDate,
							endDate, parameterMaps, progressInfo);
				} else {
					this.initMapTransFactOutWasterBcs(beginDate, endDate,
							parameterMaps, progressInfo);
				}
				

				// 3.料件进口(报关单) 映射 --只包含正在执行的
				this.initMapCustomsMaterielBcs(beginDate, endDate,
						parameterMaps, progressInfo);

				// 4.计算F6与F9转厂进口(报关单与单据 )
				this.initMapTransFactInBcs(beginDate, endDate, parameterMaps,
						progressInfo);

			}

			/*** 第六步：电子手册 **/
			if (projectTypeParam.getIsDzsc() == true) {
				System.out.println("电子手册");

				clientTipMessage = "3.计算  电子手册 报关单信息.. ";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				// 1.出口成品(报关单) 损耗映射--只包含正在执行的
				this.initMapCustomsProductDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 2.转厂出口F7,F8 (报关单) 损耗映射 -- 还要包含已经核销的
				this.initMapTransFactOutWasterDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 3.料件进口(报关单) 映射--只包含正在执行的
				this.initMapCustomsMaterielDzsc(beginDate, endDate,
						parameterMaps, progressInfo);

				// 4.转厂进口(报关单)F6,F9 映射-- 还要包含已经核销的
				this.initMapTransFactInDzsc(beginDate, endDate, parameterMaps,
						progressInfo);

				// 5.根据单据查询送货数量，将送货数量按照电子手册单耗折算成料件。
				if (isSingleSelect) {
					this.initMapSendAmountDzsc(endDate, parameterMaps);
				}
			}
			clientTipMessage = "4.计算 结转收货情况 ...";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/*** 第七步：计算6,9栏结转进口单据 */
			initMapTransFactInAmont(endDate, parameterMaps);

			/*** 第八步：如果选多种报关模式，则按照报关常用工厂BOM来折算送货的折料明细。 */
			if (!isSingleSelect) {
				System.out.println("多种手册类型");
				this.initMapSendAmount(parameterMaps);
			}

			System.out.println("5.计算发票...");

			/** 第九步计算发票数量 = 全部发票数量-已核销的发票数量 */
			if (!isFromCheckData) {
				this.initInvoiceVerificationMap(beginDate, endDate,
						parameterMaps);
			}

			clientTipMessage = "6.短溢表总统计 ... ";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			/*** 第十步：总统计 */
			List<BalanceInfo> lsResult = this.getResultTest(beginDate, endDate,
					parameterMaps, isFromCheckData, projectTypeParam,
					isSingleSelect, isAll, useNewContract);

			// 开始删除历史记录
			clientTipMessage = "7.删除历史记录... ";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			// 删除平衡表信息BalanceInfo
			this.casLeftoverMaterielDao.deleteBalanceInfo();

			clientTipMessage = "8.保存新有的短溢表统计结果...";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			for (int i = 0, n = lsResult.size(); i < n; i++) {
				BalanceInfo item = (BalanceInfo) lsResult.get(i);
				item.setCompany(CommonUtils.getCompany());
				this.casDao.getHibernateTemplate().save(item);
			}
			return lsResult;

		} catch (Exception ex) {
			logger.error("错误", ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			runMap.put(companyId, false);
		}
	}

	/**
	 * 编码级盘点导入计算
	 * 
	 * @param endDate
	 * @param parameterMaps
	 * @param progressInfo
	 */
	private void initMapCheckBalanceOfCustom(Date endDate,
			ParameterMaps parameterMaps) {
		List<Object> parameters = new ArrayList<Object>();
		Map balanceMap = parameterMaps.checkBalanceOfCustom;
		String hsql = "select a.name,a.spec,a.unitName,a.kcType,sum(a.checkAmount)"
				+ " from CheckBalanceOfCustom a "
				+ "where a.checkDate>=? and a.checkDate<=? ";
		hsql += " group by a.name,a.spec,a.unitName,a.kcType";
		parameters.add(CommonUtils.getBeginDate(endDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		List list = new ArrayList();
		list = this.casDao.find(hsql, parameters.toArray());
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = (Object[]) list.get(i);
			String name = (String) objects[0];
			String spec = (String) objects[1];
			String unitName = (String) objects[2];
			String kcType = (String) objects[3];
			Double checkAmount = (Double) objects[4];
			String key = name + "/" + spec + "/" + unitName + "/" + kcType;
			balanceMap.put(key, checkAmount);

		}
	}

	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param parameterMaps
	 * @param progressInfo
	 *            hclBcus
	 */
	private void initMapTransFactOutWasterBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();
		Map<String, Double> beforResult7Map = parameterMaps.beforResult7Map;
		Map<String, Double> beforResult8Map = parameterMaps.beforResult8Map;
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF7F8Map;
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计
		// 以名称+规格+单位+编码+版本+客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " a.billMaster.billType.code ,a.version ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,"
				+ " a.billMaster.scmCoc.name ,a.version ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List list = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = objs[4] == null ? "" : (String) objs[4];
			String typeCode = objs[5] == null ? "" : (String) objs[5];
			String version = objs[6] == null ? "" : String.valueOf(objs[6]);

			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName
					+ "/" + version;

			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName + "/"
					+ version;
			hsNameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount = -hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount = +hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
		}
		// 2.获取成品报关单map
		List<Object> parameters = new ArrayList<Object>();
		selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,a.version"
				+ " from CustomsDeclarationCommInfo a, EmsHeadH2kExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.emsHeadH2k.emsNo "
				+ " and exg.emsHeadH2k.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,a.version ";
		parameters.add(DeclareState.PROCESS_EXE);// 正在运行合同
		parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = objs[4] == null ? "" : (String) objs[4];
			String version = objs[5] == null ? "" : String.valueOf(objs[5]);
			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName
					+ "/" + version;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName + "/"
					+ version;
			hsNameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(key);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(key);
				removeList.add(key);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult7Map.put(key, result);
			} else if (result < (-0.0001)) {

				beforResult8Map.put(key, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();

		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String key = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(key);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(key, result);
			} else if (result < (-0.0001)) {
				beforResult7Map.put(key, -result);
			}
		}

	}

	/**
	 * 获得短溢表计算结果集
	 * 
	 * @return 短溢表计算结果
	 * @author wss2010.10.27测试版本
	 */
	private List<BalanceInfo> getResultTest(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, boolean isFromCheckData,
			ProjectTypeParam projectTypeParam, boolean isSingleSelect,
			boolean isAll, boolean useNewContract) {

		String clientTipMessage = "wss 进入了总统计的方法*******************************************";
		System.out.println(clientTipMessage);
		logger.info(clientTipMessage);

		// 1.抓取合同中的物料资料Map<key,BalanceInfo>
		Map<String, BalanceInfo> mapResult = getMapContractMaterielInfo(projectTypeParam);

		List<BalanceInfo> lsResult = new ArrayList<BalanceInfo>();

		// 2.如果来自单据
		if (!isFromCheckData) {

			// 料件单据
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("and", null, "billMaster.isValid",
					"=", Boolean.valueOf(true), null));
			conditions.add(new Condition("and", null, "hsName is not null ",
					null, null, null));
			conditions.add(new Condition("and", null,
					"billMaster.billType is not null ", null, null, null));
			conditions.add(new Condition("and", null, " billMaster.validDate ",
					">=", CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
							.getYear()), 0, 1), null));
			conditions.add(new Condition("and", null, " billMaster.validDate ",
					"<=", endDate, null));

			// 表名BillDetailMateriel----料件单据明细
			String billDetailRemainMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

			String selects = "select sum(a.hsAmount),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.wareSet.wareProperty ";
			String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.wareSet.wareProperty ";
			String leftOuter = " left join a.billMaster left join a.billMaster.billType left join a.hsUnit left join a.wareSet  ";

			List listBillDetailMateriel = casDao.commonSearch(selects,
					billDetailRemainMateriel, conditions, groupBy, leftOuter);
			String[] codes = new String[] { "5001", "5101" };
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", " in (", codes, ")"));
			// conditions.add(new Condition("and", null,
			// "billMaster.billType.code",
			// "=", "5001", null));
			conditions.add(new Condition("and", null, "note", "=", "料件", null));

			// 残次品表
			billDetailRemainMateriel = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);

			// 残次品（料件部分）
			List listBillDetailMateriel5001 = casDao.commonSearch(selects,
					billDetailRemainMateriel, conditions, groupBy, leftOuter);

			clientTipMessage = "wss billDetailMateriel5001.size "
					+ listBillDetailMateriel5001.size();
			System.out.println(clientTipMessage);
			logger.info(clientTipMessage);

			listBillDetailMateriel.addAll(listBillDetailMateriel5001);

			clientTipMessage = "wss billDetailMateriel.size "
					+ listBillDetailMateriel.size();
			System.out.println(clientTipMessage);
			logger.info(clientTipMessage);

			int size = listBillDetailMateriel.size();

//			int yyy = 0;

			// 遍历料件单据
			for (int i = 0; i < size; i++) {
				Object[] objs = (Object[]) listBillDetailMateriel.get(i);
				Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
				// 单据类型
				String typeCode = objs[1] == null ? "" : (String) objs[1];
				String hsName = objs[2] == null ? "" : (String) objs[2];
				String hsSpec = objs[3] == null ? "" : (String) objs[3];
				String hsUnitName = objs[4] == null ? "" : (String) objs[4];

				// 仓库属性wss2010.10.13新加,如果为null，则认为是"0"保税仓
				String wareProperty = objs[5] == null ? "0" : (String) objs[5];

				if ("".equals(hsName)) {
					continue;
				}

				// key=名称+"/"+规格+"/"+单位
				String key = hsName + "/" + hsSpec + "/" + hsUnitName;

				BalanceInfo item = mapResult.get(key);

				if (item == null) {
					if (isAll) {
						item = new BalanceInfo();
						mapResult.put(key, item);
						item.setName(hsName);// 名称
						item.setSpec(hsSpec);// 规格mapTransFactInAmount
						item.setUnitName(hsUnitName);// 单位
					} else {
						continue;
					}
				}

				// **f0 国内购买的 1001料件期初单非保税部分 -所有出仓单中非保税部分(只抓非保税仓"1"的) + 国内购买数
				if ("1".equals(wareProperty)) {
					if (typeCode.equals("1101") // 1101 车间领用
							|| typeCode.equals("1102") // 1102 料件退换
							|| typeCode.equals("1103") // 1103 料件复出
							|| typeCode.equals("1104") // 1104 料件盘亏单
							|| typeCode.equals("1105") // 1105 料件转仓出库
							|| typeCode.equals("1106") // 1106 结转料件退货单
							|| typeCode.equals("1107") // 1107 其他料件退货单
							|| typeCode.equals("1108") // 1108 其他领用
							|| typeCode.equals("1109") // 1109 受托加工领用
							|| typeCode.equals("1111") // 11110受托加工料件退回
							|| typeCode.equals("1112") // 1112外发加工返修出库
							|| typeCode.equals("1113") // 1113外发加工料件出库
							|| typeCode.equals("1114") // 1114受托加工料件出库
							|| typeCode.equals("1115")) { // 1115海关批准内销
						item.setF0(item.getF0() - hsAmount);
					}
					if (typeCode.equals("1001") // 1001 料件期初单
							|| typeCode.equals("1003") // 1003 直接进口
							|| typeCode.equals("1004") // 1004 结转进口
							|| typeCode.equals("1006") // 1006 车间返回
							|| typeCode.equals("1007") // 1007 料件盘盈单
							|| typeCode.equals("1009") // 1009 其他来源
							|| typeCode.equals("1010") // 1010 料件转仓入库
							|| typeCode.equals("1017") // 1017 外发加工料件退回
							|| typeCode.equals("1018") // 1018 外发加工料件入库
							|| typeCode.equals("5001") // 5001残次品入库（料件部分）
							|| typeCode.equals("5002")) // 5002残次品出库（料件部分）) 
							{// 车间返回
						item.setF0(item.getF0() + hsAmount);
					}
				}

				// B非保税库存原料还要加上 1005国内购买数量
				if (typeCode.equals("1005")) {
					item.setF0(item.getF0() + hsAmount);
				}

				// **f1 库存原材料. 把单据类型不相同的但是名称规格单位相同的数量累加(只抓保税仓"0"的)
				if ("0".equals(wareProperty)) {
					if (typeCode.equals("1001") // 1001 料件期初单
							|| typeCode.equals("1003") // 1003 直接进口
							|| typeCode.equals("1004") // 1004 结转进口
							|| typeCode.equals("1006") // 1006 车间返回
							|| typeCode.equals("1007") // 1007 料件盘盈单
							|| typeCode.equals("1009") // 1009 其他来源
							|| typeCode.equals("1010") // 1010 料件转仓入库
							|| typeCode.equals("1017") // 1017 外发加工料件退回
							|| typeCode.equals("1018") // 1018 外发加工料件入库
							|| typeCode.equals("5001") // 5001残次品入库（料件部分）
							|| typeCode.equals("5002") // 5002残次品出库（料件部分）
					) {
						item.setF1(item.getF1() + hsAmount);
					} else if (typeCode.equals("1101") // 1101 车间领用
							|| typeCode.equals("1102") // 1102 料件退换
							|| typeCode.equals("1103") // 1103 料件复出
							|| typeCode.equals("1104") // 1104 料件盘亏单
							|| typeCode.equals("1105") // 1105 料件转仓出库
							|| typeCode.equals("1106") // 1106 结转料件退货单
							|| typeCode.equals("1107") // 1107 其他料件退货单
							|| typeCode.equals("1108") // 1108 其他领用
							|| typeCode.equals("1112") // 1112外发加工返修出库
							|| typeCode.equals("1113") // 1113外发加工料件出库
							|| typeCode.equals("1115") // 1115海关批准内销wss2010.06.30加
							|| typeCode.equals("5101") // 5101残次品出库（料件部分）

					) { // 出库

						item.setF1(item.getF1() - hsAmount);
					}
				}

				// ******F2设置半成品折原料 1101 车间领用 + 1002 在产品期初单 - 1006 车间返回
				if (typeCode.equals("1101") || typeCode.equals("1002")) {
					item.setF2(item.getF2() + hsAmount);
				} else if (typeCode.equals("1006")) {
					item.setF2(item.getF2() - hsAmount);
				}

				// ********F4外发加工/厂外存放数
				if (typeCode.equals("1014") // 1014委外期初单
						|| typeCode.equals("1112") // 1112外发加工返修出库
						|| typeCode.equals("1113")) {// 1113外发加工料件出库
					item.setF4(item.getF4() + hsAmount);
				} else if (typeCode.equals("1017") // 1017外发加工料件退回
						|| typeCode.equals("1018")) {// 1018外发加工料件入库
					item.setF4(item.getF4() - hsAmount);
				}

				// A.国内购买 = 1020国内购买期初单+1005国内购买 - 本年度已核销发票数（在后面加上）
				if (typeCode.equals("1020") || typeCode.equals("1005")) {
					item.setF18(CommonUtils.getDoubleExceptNull(item.getF18())
							+ hsAmount);
				}
			}
		}

		// 3.来自盘点
		else {

			// 查询盘点中的数据
			List list = new ArrayList();

			List<Object> parameters = new ArrayList<Object>();
			String hsql = " select a.name,a.spec,a.hsUnit.name,a.kcType,"
					+ " a.wareSet.wareProperty,sum(a.hsAmount) "
					+ " from CheckBalanceConvertMateriel a "
					+ " left join a.hsUnit "
					+ " left join a.fatherCheckBalance "
					+ " where a.company.id = ? ";

			parameters.add(CommonUtils.getCompany().getId());

			if (endDate != null) {
				hsql += " and a.checkDate >= ? ";
				parameters.add(CommonUtils.getBeginDate(endDate));
				hsql += " and a.checkDate <= ? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}
			hsql += " group by a.name,a.spec,a.hsUnit.name,a.kcType,a.wareSet.wareProperty ";

			list = this.casDao.find(hsql, parameters.toArray());

			// 遍历每一条
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);

				String name = (String) objs[0] == null ? "" : (String) objs[0];
				String spec = (String) objs[1] == null ? "" : (String) objs[1];
				String unitName = (String) objs[2] == null ? ""
						: (String) objs[2];
				String type = (String) objs[3] == null ? "" : (String) objs[3];

				// 仓库属性wss2010.10.13新加,如果为null，则认为是"0"保税仓
				String wareProperty = (String) objs[4] == null ? "0"
						: (String) objs[4];

				Double hsAmount = (Double) objs[5] == null ? 0.0
						: (Double) objs[5];

				// 过滤没用的
				if ("".equals(name)) {
					continue;
				}

				// key:名称 + 规格 + 单位
				String key = name + "/" + spec + "/" + unitName;

				BalanceInfo item = mapResult.get(key);

				// 如果合同中不存在这样的资料
				if (item == null) {
					if (isAll) {
						item = new BalanceInfo();
						item.setName(name);// 名称
						item.setSpec(spec);// 规格
						item.setUnitName(unitName);// 单位
						mapResult.put(key, item);
					} else {
						continue;
					}
				}

				// **f0 非保税库存原材料 (只抓非保税仓"1"的) 料件库存
				if ("1".equals(wareProperty) && "0".equals(type)) {
					item.setF0(item.getF0() + hsAmount);
				}

				// **f1 库存原材料. (只抓保税仓"0"的)
				if ("0".equals(wareProperty) && "0".equals(type)) {
					item.setF1(item.getF1() + hsAmount);
				}

				// f2在产品折料
				if ("2".equals(type)) {
					item.setF2(item.getF2() + hsAmount);
				}
				// f3成品折料
				if ("1".equals(type)) {
					item.setF3(item.getF3() + hsAmount);
				}

				// f4外发折料
				if ("4".equals(type)) {
					item.setF4(item.getF4() + hsAmount);
				}

			}
		}

		// 4.遍历短溢表，将相应数据填充
		Iterator<BalanceInfo> iterator = mapResult.values().iterator();
		while (iterator.hasNext()) {
			BalanceInfo item = iterator.next();

			String hsName = item.getName() == null ? "" : item.getName().trim();
			String hsUnitName = item.getUnitName() == null ? "" : item
					.getUnitName().trim();
			String hsSpec = item.getSpec() == null ? "" : item.getSpec().trim();

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			
			
			// 如果数据来源于工厂单据
			if (!isFromCheckData) {
				// 获取产成品耗料 折报关数量
				Map<String, Double> mapTempProduct = this
						.getMapHsAmountUsedByProduct(key, parameterMaps);

				// 获取半成品耗料 折报关数量
				Map<String, Double> mapTempHalf = this
						.getMapHsAmountUsedByHalf(key, parameterMaps);

				item.setF2(CommonUtils.getDoubleExceptNull(item.getF2())
						+ CommonUtils.getDoubleExceptNull(mapTempHalf.get("F2")
								+ CommonUtils
										.getDoubleExceptNull(mapTempProduct
												.get("F2"))));

				// f3成品折原料
				item.setF3(mapTempProduct.get("F3"));

				// f4 外发加工/厂外存放数
				item.setF4(item.getF4()
						+ CommonUtils
								.getDoubleExceptNull(mapTempHalf.get("F4"))
						+ CommonUtils.getDoubleExceptNull(mapTempProduct
								.get("F4")));
				// 加盘点库存（编码级）
			} else {
				/** 新加的编码级盘点导入，不需要折料 **/
				this.initMapCheckBalanceOfCustom(endDate, parameterMaps);
				Double checkBalanceOfCustomAmount0 = parameterMaps.checkBalanceOfCustom
						.get(key + "/0") == null ? 0.0
						: parameterMaps.checkBalanceOfCustom.get(key + "/0");
				Double checkBalanceOfCustomAmount1 = parameterMaps.checkBalanceOfCustom
						.get(key + "/1") == null ? 0.0
						: parameterMaps.checkBalanceOfCustom.get(key + "/1");
				Double checkBalanceOfCustomAmount2 = parameterMaps.checkBalanceOfCustom
						.get(key + "/2") == null ? 0.0
						: parameterMaps.checkBalanceOfCustom.get(key + "/2");
				Double checkBalanceOfCustomAmount4 = parameterMaps.checkBalanceOfCustom
						.get(key + "/4") == null ? 0.0
						: parameterMaps.checkBalanceOfCustom.get(key + "/4");
				Double checkBalanceOfCustomAmount5 = parameterMaps.checkBalanceOfCustom
						.get(key + "/5") == null ? 0.0
						: parameterMaps.checkBalanceOfCustom.get(key + "/5");
				Double checkBalanceOfCustomAmount6 = parameterMaps.checkBalanceOfCustom
						.get(key + "/6") == null ? 0.0
						: parameterMaps.checkBalanceOfCustom.get(key + "/6");
				item.setF1(item.getF1() + checkBalanceOfCustomAmount0);
				item.setF3(item.getF3() + checkBalanceOfCustomAmount1);
				item.setF2(item.getF2() + checkBalanceOfCustomAmount2);
				item.setF4(item.getF4() + checkBalanceOfCustomAmount4);
				item.setF1(item.getF1() + checkBalanceOfCustomAmount5);
				item.setF0(item.getF0() + checkBalanceOfCustomAmount6);
			}

			// 实际库存总数 f1+f2+f3+f4
			item.setF5(CommonUtils.getDoubleExceptNull(item.getF0())
					+ CommonUtils.getDoubleExceptNull(item.getF1())
					+ CommonUtils.getDoubleExceptNull(item.getF2())
					+ CommonUtils.getDoubleExceptNull(item.getF3())
					+ CommonUtils.getDoubleExceptNull(item.getF4()));

			// 计算转厂7/8
			if (projectTypeParam.getIsBcs()) {
				// 初始化电子化手册F7与F8折单耗
				if(useNewContract) {
					this.initMapContractBomF7AndF8BcsByNewContract(parameterMaps, key);
				} else {
					this.initMapContractBomF7AndF8Bcs(parameterMaps, key);
				}
				
			} else if (projectTypeParam.getIsDzsc()) {

				// 初始化电子手册F7与F8折单耗
				this.initMapContractBomF7AndF8Dzsc(parameterMaps, key);
			} else if (projectTypeParam.getIsBcus()) {
				// 初始化电子帐册F7与F8折单耗
				this.initMapContractBomF7AndF8Bcus(parameterMaps, key);
			}
			// 计算转厂 6/9
			item.setF9(CommonUtils
					.getDoubleExceptNull(parameterMaps.mapRecvAmount.get(key)));
			item.setF6(CommonUtils
					.getDoubleExceptNull(parameterMaps.mapTransFactInAmount
							.get(key)));
			// 转厂
			Double f7 = CommonUtils
					.getDoubleExceptNull(parameterMaps.result7Map.get(key));
			// 送货
			Double f8 = CommonUtils
					.getDoubleExceptNull(parameterMaps.result8Map.get(key));
			item.setF7(f7);
			item.setF8(f8);
			parameterMaps.mapTransFactOutWaste = null;
			parameterMaps.mapSendAmount = null;

			// F11设置合同进口数-海关批准内销
			item
					.setF11(item.getF11()
							+ CommonUtils
									.getDoubleExceptNull(parameterMaps.mapCustomsMaterialAmount
											.get(key)));

			// F12合同出口耗料
			item
					.setF12(item.getF12()
							+ CommonUtils
									.getDoubleExceptNull(parameterMaps.mapCustomsProductWaste
											.get(key)));
			// + CommonUtils
			// .getDoubleExceptNull(parameterMaps.mapCustomsMaterialDOMESTICSALESAmount
			// .get(key)));

			// F18国内购买 = 1020国内购买期初单 + 1005国内购买 - 本年度已核销发票数
			if (!isFromCheckData) {// 只适用来自于单据
				item
						.setF18(CommonUtils.getDoubleExceptNull(item.getF18())
								- CommonUtils
										.getDoubleExceptNull(parameterMaps.invoiceVerificationAmountMap
												.get(key)));
			}

			lsResult.add(item);
		}
		return lsResult;
	}

	/**
	 * 初始化电子化手册F7与F8折单耗
	 * 使用最新合同折料
	 */
	private void initMapContractBomF7AndF8BcsByNewContract(ParameterMaps parameterMaps,
			String hsKey) {

		Map<String, Double> result7Map = parameterMaps.result7Map;
		Map<String, Double> result8Map = parameterMaps.result8Map;
		List<Object> parameters = new ArrayList<Object>();
		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		String selects = "select a.contractExg.name,a.contractExg.spec,a.contractExg.unit.name"
				+ " ,a.contractExg.contract.impContractNo,a.name||'/'||a.spec||'/'||a.unit.name,a.unitDosage,a.contractExg.seqNum"
				+ " from ContractBom a "
				+ " where a.contractExg.contract.declareState= ? and (a.name||'/'||a.spec||'/'||a.unit.name=?)"
				+ "  order by a.contractExg.contract.impContractNo,a.contractExg.seqNum";
		parameters.clear();
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(hsKey);
		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMap = new HashMap<String, BillTemp>();

		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String impContractNo = objs[3] == null ? "" : (String) objs[3];
			String bomKey = objs[4] == null ? "" : (String) objs[4];
			Double unitDosage = objs[5] == null ? 0.0 : (Double) objs[5];
			String key = name + "/" + spec + "/" + unitName + "/"
					+ impContractNo;

			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitDosage);
			bomMap.put(key, b);

		}
		System.out.println("HWY" );
		for (Entry entry : parameterMaps.beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			// 2014-3-19合同最大的合同号,根据成品查找
			String maxImpContractNo = this.casDao.findMaxExgContractNoFromBcs(
					nameSepcUnitKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				// System.out.println("F7使用的最大合同号:" + maxImpContractNo +
				// " scKey="
				// + scKey + " 转厂数量=" + value + " 单耗=" + unitDan);
				bomValue = result7Map.get(hsKey);
				// System.out.println("F8bomValue:" + value * unitDan);
				if (bomValue == null) {
					bomValue = value * unitDan;
				} else {
					bomValue += value * unitDan;
				}
				result7Map.put(hsKey, bomValue);
			}

		}

		for (Entry entry : parameterMaps.beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromBcs(
					nameSepcUnitKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				// System.out.println("F8使用的最大合同号:" + maxImpContractNo +
				// " scKey="
				// + scKey + " 转厂数量=" + value + " 单耗=" + unitDan);
				bomValue = result8Map.get(hsKey);
				// System.out.println("F7bomValue:" + value * unitDan);
				if (bomValue == null) {
					bomValue = value * unitDan;
				} else {
					bomValue += value * unitDan;
				}
				result8Map.put(hsKey, bomValue);
			}

		}
	}
	
	/**
	 * 初始化电子化手册F7与F8折单耗
	 * 使用单据对应折料
	 */
	private void initMapContractBomF7AndF8Bcs(ParameterMaps parameterMaps,
			String hsKey) {

		Map<String, Double> result7Map = parameterMaps.result7Map;
		Map<String, Double> result8Map = parameterMaps.result8Map;
		List<Object> parameters = new ArrayList<Object>();
		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		String selects = "select a.contractExg.name,a.contractExg.spec,a.contractExg.unit.name"
			+ " ,a.contractExg.contract.impContractNo,a.name||'/'||a.spec||'/'||a.unit.name,"
			+ " a.unitDosage,a.contractExg.seqNum, a.contractExg.contract.emsNo"
			+ " from ContractBom a "
			+ " where a.contractExg.contract.declareState= ? and (a.name||'/'||a.spec||'/'||a.unit.name=?)"
			+ "  order by a.contractExg.contract.impContractNo,a.contractExg.seqNum";
		parameters.clear();
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(hsKey);
		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMapF8 = new HashMap<String, BillTemp>();
		Map<String, BillTemp> bomMapF7 = new HashMap<String, BillTemp>();
	
		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String impContractNo = objs[3] == null ? "" : (String) objs[3];
			String bomKey = objs[4] == null ? "" : (String) objs[4];
			Double unitDosage = objs[5] == null ? 0.0 : (Double) objs[5];
			String emsNo = objs[7] == null ? "" : (String) objs[7];
			String keyF8 = name + "/" + spec + "/" + unitName + "/" + emsNo;
			String keyF7 = name + "/" + spec + "/" + unitName + "/"
			+ impContractNo;
			
			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitDosage);
			bomMapF8.put(keyF8, b);
			bomMapF7.put(keyF7, b);
		}

		for (Entry entry : parameterMaps.beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			// 去掉斜杠
			nameSepcUnitKey = nameSepcUnitKey.substring(0, nameSepcUnitKey.length() - 1);
			
			// 合同最大的合同号
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromBcs(
					nameSepcUnitKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMapF7.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				// System.out.println("F7使用的最大合同号:" + maxImpContractNo +
				// " scKey="
				// + scKey + " 转厂数量=" + value + " 单耗=" + unitDan);
				bomValue = result7Map.get(hsKey);
				// System.out.println("F8bomValue:" + value * unitDan);
				if (bomValue == null) {
					bomValue = value * unitDan;
				} else {
					bomValue += value * unitDan;
				}
				result7Map.put(hsKey, bomValue);
			}

		}

		for (Entry entry : parameterMaps.beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			//String maxImpContractNo = this.casDao.findMaxImpContractNoFromBcs(
			//		nameSepcUnitKey, hsKey);
			//String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMapF8.get(nameSepcUnitKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				// System.out.println("F8使用的最大合同号:" + maxImpContractNo +
				// " scKey="
				// + scKey + " 转厂数量=" + value + " 单耗=" + unitDan);
				bomValue = result8Map.get(hsKey);
				// System.out.println("F7bomValue:" + value * unitDan);
				if (bomValue == null) {
					bomValue = value * unitDan;
				} else {
					bomValue += value * unitDan;
				}
				result8Map.put(hsKey, bomValue);
			}

		}
	}

	/**
	 * 初始化电子手册F7与F8折单耗
	 */
	private void initMapContractBomF7AndF8Dzsc(ParameterMaps parameterMaps,
			String hsKey) {

		Map<String, Double> result7Map = parameterMaps.result7Map;
		Map<String, Double> result8Map = parameterMaps.result8Map;
		List<Object> parameters = new ArrayList<Object>();
		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		String selects = "select a.dzscEmsExgBill.name,a.dzscEmsExgBill.spec,a.dzscEmsExgBill.unit.name"
				+ " ,a.dzscEmsExgBill.dzscEmsPorHead.ieContractNo,a.name||'/'||a.spec||'/'||a.unit.name,a.unitDosage,a.dzscEmsExgBill.seqNum"
				+ " from DzscEmsBomBill a "
				+ "where a.dzscEmsExgBill.dzscEmsPorHead.declareState= ? and (a.name||'/'||a.spec||'/'||a.unit.name=?)"
				+ "  order by a.dzscEmsExgBill.dzscEmsPorHead.ieContractNo,a.dzscEmsExgBill.seqNum";
		parameters.clear();
		parameters.add(DzscState.EXECUTE);
		parameters.add(hsKey);

		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMap = new HashMap<String, BillTemp>();

		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String impContractNo = objs[3] == null ? "" : (String) objs[3];
			String bomKey = objs[4] == null ? "" : (String) objs[4];
			Double unitDosage = objs[5] == null ? 0.0 : (Double) objs[5];
			String key = name + "/" + spec + "/" + unitName + "/"
					+ impContractNo;

			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitDosage);
			bomMap.put(key, b);

		}

		for (Entry entry : parameterMaps.beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			// 合同最大的合同号
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromDzsc(
					nameSepcUnitKey, hsKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				bomValue = result7Map.get(hsKey);
				if (bomValue == null) {
					bomValue = value * unitDan;
				} else {
					bomValue += value * unitDan;
				}
				result7Map.put(hsKey, bomValue);
			}

		}

		for (Entry entry : parameterMaps.beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromDzsc(
					nameSepcUnitKey, hsKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				bomValue = result8Map.get(hsKey);
				if (bomValue == null) {
					bomValue = value * unitDan;
				} else {
					bomValue += value * unitDan;
				}
				result8Map.put(hsKey, bomValue);
			}

		}
	}

	/**
	 * 初始化电子帐册F7与F8折单耗
	 */
	private void initMapContractBomF7AndF8Bcus(ParameterMaps parameterMaps,
			String hsKey) {

		Map<String, Double> result7Map = parameterMaps.result7Map;
		Map<String, Double> result8Map = parameterMaps.result8Map;
		List<Object> parameters = new ArrayList<Object>();
		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		String selects = "select a.emsHeadH2kVersion.emsHeadH2kExg.name,a.emsHeadH2kVersion.emsHeadH2kExg.spec,"
				+ " a.emsHeadH2kVersion.emsHeadH2kExg.unit.name"
				+ " ,a.emsHeadH2kVersion.version,a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.emsNo, "
				+ " a.name||'/'||a.spec||'/'||a.unit.name,a.unitWear,a.wear,a.emsHeadH2kVersion.emsHeadH2kExg.seqNum"
				+ " from EmsHeadH2kBom a "
				+ "  where (a.name||'/'||a.spec||'/'||a.unit.name=?)"
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState= ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=?"
				+ " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum ";
		parameters.clear();
		parameters.add(hsKey);
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(new Boolean(false));

		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMap = new HashMap<String, BillTemp>();

		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String version = objs[3] == null ? "" : (String) objs[3];
			String impContractNo = objs[4] == null ? "" : (String) objs[4];
			String bomKey = objs[5] == null ? "" : (String) objs[5];
			Double unitWear = objs[6] == null ? 0.0 : (Double) objs[6];// 单耗
			Double wear = objs[7] == null ? 0.0 : (Double) objs[7];// 损耗
			String key = name + "/" + spec + "/" + unitName + "/" + version
					+ "/" + impContractNo;

			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitWear);
			b.setBillSum2(wear);
			bomMap.put(key, b);

		}
		// 合同号最大的手册
		selects = "select a.emsNo from  EmsHeadH2k a  where  a.historyState=? order by a.emsNo desc";
		parameters.clear();
		parameters.add(new Boolean(false));
		List listEmsHeadH2k = casDao
				.commonSearch(selects, parameters.toArray());
		String maxImpContractNo = "";
		if (listEmsHeadH2k != null && listEmsHeadH2k.size() != 0)
			maxImpContractNo = (String) listEmsHeadH2k.get(0);

		for (Entry entry : parameterMaps.beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				Double wear = billTemp.getBillSum2();
				bomValue = result7Map.get(hsKey);
				if (bomValue == null) {
					bomValue = value * (unitDan / (1 - wear));
				} else {
					bomValue += value * (unitDan / (1 - wear));
				}
				result7Map.put(hsKey, bomValue);
			}

		}

		for (Entry entry : parameterMaps.beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = parameterMaps.resultHsNameSpecUnitF7F8Map
					.get(scKey);
			String andKey = nameSepcUnitKey + ("/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitDan = billTemp.getBillSum1();
				Double wear = billTemp.getBillSum2();
				bomValue = result8Map.get(hsKey);
				if (bomValue == null) {
					bomValue = value * (unitDan / (1 - wear));
				} else {
					bomValue += value * (unitDan / (1 - wear));
				}
				result8Map.put(hsKey, bomValue);
			}

		}
	}

	/**
	 * 合同中的料件资料全部要在短溢表中显示
	 * 
	 * @param projectTypeParam
	 * @return
	 * @author wss2010.11.15
	 */
	public Map<String, BalanceInfo> getMapContractMaterielInfo(
			ProjectTypeParam projectTypeParam) {
		Map<String, BalanceInfo> mapResult = new HashMap<String, BalanceInfo>();
		String name;
		String spec;
		String unitName;
		Object[] objs;
		BalanceInfo b;

		// 电子化手册
		if (projectTypeParam.getIsBcs()) {
			List<Object[]> list = casDao.find(
					" select a.name,a.spec,a.unit.name from ContractImg a "
							+ " where a.contract.declareState = ? "
							+ " and a.company.id = ? "
							+ " order by a.contract.emsNo,a.seqNum ",
					new Object[] { DeclareState.PROCESS_EXE,
							CommonUtils.getCompany().getId() });

			for (int i = 0; i < list.size(); i++) {
				objs = list.get(i);
				name = objs[0] == null ? "" : (String) objs[0];
				spec = objs[1] == null ? "" : (String) objs[1];
				unitName = objs[2] == null ? "" : (String) objs[2];
				b = new BalanceInfo();
				b.setName(name);
				b.setSpec(spec);
				b.setUnitName(unitName);

				mapResult.put(name + "/" + spec + "/" + unitName, b);
			}
		}

		// 电子帐册
		if (projectTypeParam.getIsBcus()) {

			List<Object[]> list = casDao.find(
					" select a.name,a.spec,a.unit.name from EmsHeadH2kImg a "
							+ " where a.company.id = ? "
							+ " order by a.emsHeadH2k.emsNo,a.seqNum ",
					new Object[] { CommonUtils.getCompany().getId() });

			for (int i = 0; i < list.size(); i++) {
				objs = list.get(i);
				name = objs[0] == null ? "" : (String) objs[0];
				spec = objs[1] == null ? "" : (String) objs[1];
				unitName = objs[2] == null ? "" : (String) objs[2];

				b = new BalanceInfo();
				b.setName(name);
				b.setSpec(spec);
				b.setUnitName(unitName);
				mapResult.put(name + "/" + spec + "/" + unitName, b);
			}
		}

		// 电子手册
		if (projectTypeParam.getIsDzsc()) {

			List<Object[]> list = casDao.find(
					" select a.name,a.spec,a.unit.name from DzscEmsImgBill a "
							+ " where a.dzscEmsPorHead.declareState = ? "
							+ " and a.company.id = ? "
							+ " order by a.dzscEmsPorHead.emsNo,a.seqNum ",
					new Object[] { DzscState.EXECUTE,
							CommonUtils.getCompany().getId() });

			for (int i = 0; i < list.size(); i++) {
				objs = list.get(i);
				name = objs[0] == null ? "" : (String) objs[0];
				spec = objs[1] == null ? "" : (String) objs[1];
				unitName = objs[2] == null ? "" : (String) objs[2];

				b = new BalanceInfo();
				b.setName(name);
				b.setSpec(spec);
				b.setUnitName(unitName);

				mapResult.put(name + "/" + spec + "/" + unitName, b);
			}
		}
		return mapResult;

	}

	/**
	 * 初始化一些基本信息，用map缓存 wss2010.11.18整理
	 */
	private void initMapBasicInfo(ParameterMaps parameterMaps) {

		/**
		 * key :【报关名称+"/"+规格+"/"+单位名称】 value：【对应的对应关系list】
		 */
		Map<String, List<FactoryAndFactualCustomsRalation>> mapKeyFFCList = parameterMaps.mapKeyFFCList;

		/**
		 * key：【料号】 value：【对应关系(第一个)】
		 */
		Map<String, FactoryAndFactualCustomsRalation> mapPtNoFFC = parameterMaps.mapPtNoFFC;

		/**
		 * key：【料号】 value：【报关名称+报关规格+报关单位(最后一个对应关系的)】
		 */
		Map<String, String> mapPtNoKey = parameterMaps.mapPtNoKey;

		/**
		 * key：【报关名称+报关规格+报关单位】 value：【相同报关名称、报关规格、报关单位的BillTemp】
		 */
		Map<String, BillTemp> mapKeyBillTemp = parameterMaps.mapKeyBillTemp;

		// 所有 对应关系 按料号排
		List<FactoryAndFactualCustomsRalation> lsExistFFC = this.casDao
				.findStatCusNameRelationMt(MaterielType.MATERIEL);

		for (int i = 0; i < lsExistFFC.size(); i++) {

			FactoryAndFactualCustomsRalation ffc = lsExistFFC.get(i);

			Materiel materiel = ffc.getMateriel();

			StatCusNameRelationHsn sr = ffc.getStatCusNameRelationHsn();

			String hsName = sr.getCusName() == null ? "" : sr.getCusName();// 报关名称
			String hsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();// 报关规格
			String unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			unitName = unitName == null ? "" : unitName;// 报关单位

			// keyList = 报关名称+"/"+报关规格+"/"+报关单位
			String tenKey = hsName + "/" + hsSpec + "/" + unitName;
			if (mapKeyBillTemp.get(tenKey) == null) {
				BillTemp b = new BillTemp();
				b.setBill1(hsName);
				b.setBill2(hsSpec);
				b.setBill3(unitName);
				mapKeyBillTemp.put(tenKey, b);
			}
			if (mapKeyFFCList.get(tenKey) == null) {
				List<FactoryAndFactualCustomsRalation> lsTemp = new ArrayList<FactoryAndFactualCustomsRalation>();
				lsTemp.add(ffc);
				mapKeyFFCList.put(tenKey, lsTemp);
			} else {
				List<FactoryAndFactualCustomsRalation> lsTemp = mapKeyFFCList
						.get(tenKey);	
				
				/**
				 * 如果改物料已经存在物料对应关系，取折算比最大的。
				 */
				for (FactoryAndFactualCustomsRalation factoryAndFactualCustomsRalation : lsTemp) {
					// 料号相同，已经存在对应关系
					if(materiel == factoryAndFactualCustomsRalation.getMateriel() 
							|| (materiel != null && factoryAndFactualCustomsRalation.getMateriel() != null
									&& materiel.getPtNo().equals(factoryAndFactualCustomsRalation.getMateriel().getPtNo()))) {
						// 判断折算比大小，当前的大，则删除老的。
						// 老的大，把当前对象设置成老对象。
						if(ffc.getUnitConvert() != null && 
								(factoryAndFactualCustomsRalation.getUnitConvert() == null
								|| ffc.getUnitConvert() > factoryAndFactualCustomsRalation.getUnitConvert())) {
							lsTemp.remove(factoryAndFactualCustomsRalation);
						} else {
							ffc = factoryAndFactualCustomsRalation;
						}
						break;
					}
				}
				// 不存在添加
				if(!lsTemp.contains(ffc)) {
					lsTemp.add(ffc);
				}
				
			}

			if (materiel == null) {
				continue;
			}
			String key = materiel.getPtNo();
			if (mapPtNoFFC.get(key) == null) {
				mapPtNoFFC.put(key, ffc);
			}

			mapPtNoKey.put(key, tenKey);
		}
	}

	/**
	 * 初始化 成品单据明细折BOM （用Map缓存） wss2010.11.17整理
	 */
	private void initMapBillDetailProduct(Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailProduct a ,MaterialBomVersion v "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) "
				+ " from MaterialBomVersion c "
				+ " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		parameters.add(endDate);
		parameters.add(CommonUtils.getCompany().getId());

		// 查询成品单据耗时
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		logger.info("CAS 查询成品单据的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		// 残次品（成品部分）
		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailRemainProduct a,MaterialBomVersion v "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) " + " from MaterialBomVersion c "
				+ " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? "
				+ " and a.billMaster.billType.code = ? " + " and a.note = ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		parameters.add(endDate);
		parameters.add("5101");
		parameters.add("成品");
		parameters.add(CommonUtils.getCompany().getId());

		// 残次品（成品部分）
		List list5001 = casDao.commonSearch(selects, parameters.toArray());

		list.addAll(list5001);

		/**
		 * 2001成品起初单
		 */
		Map<String, Double> mapBillDetailPtAmount2001 = parameterMaps.mapBillDetailPtAmount2001; // 

		/**
		 * 2002车间入库
		 */
		Map<String, Double> mapBillDetailPtAmount2002 = parameterMaps.mapBillDetailPtAmount2002; // 

		/**
		 * 2007受托加工车间入库
		 */
		Map<String, Double> mapBillDetailPtAmount2007 = parameterMaps.mapBillDetailPtAmount2007; // 

		/**
		 * 2009结转成品退货单
		 */
		Map<String, Double> mapBillDetailPtAmount2009 = parameterMaps.mapBillDetailPtAmount2009; // 

		/**
		 * 2010受托加工退回成品
		 */
		Map<String, Double> mapBillDetailPtAmount2010 = parameterMaps.mapBillDetailPtAmount2010; // 

		/**
		 * 2101直接出口
		 */
		Map<String, Double> mapBillDetailPtAmount2101 = parameterMaps.mapBillDetailPtAmount2101; // 

		/**
		 * 2102结转出口
		 */
		Map<String, Double> mapBillDetailPtAmount2102 = parameterMaps.mapBillDetailPtAmount2102; // 

		/**
		 * 2103返回车间
		 */
		Map<String, Double> mapBillDetailPtAmount2103 = parameterMaps.mapBillDetailPtAmount2103; // 

		/**
		 * 结转成品 2102 结转出口 - 2009 结转成品退货单 + 2011已交货未结转期初单 - 2012已结转未交货期初单
		 */
		Map<String, Double> mapBillDetailPtAmountCpTran = parameterMaps.mapBillDetailPtAmountCpTran; //

		/**
		 * 2106海关批准内销
		 */
		Map<String, Double> mapBillDetailPtAmount2106 = parameterMaps.mapBillDetailPtAmount2106; // 

		/**
		 * 2107其他内销
		 */
		Map<String, Double> mapBillDetailPtAmount2107 = parameterMaps.mapBillDetailPtAmount2107; // 

		/**
		 * 2110受托加工返回
		 */
		Map<String, Double> mapBillDetailPtAmount2110 = parameterMaps.mapBillDetailPtAmount2110; // 

		/**
		 * F26入库成品-出库成品
		 */
		Map<String, Double> mapBillDetailPtAmountF26 = parameterMaps.mapBillDetailPtAmountF26; // 

		/**
		 * 2015外发加工成品入库
		 */
		Map<String, Double> mapBillDetailPtAmount2015 = parameterMaps.mapBillDetailPtAmount2015; // 

		/**
		 * 2017外发加工成品退回
		 */
		Map<String, Double> mapBillDetailPtAmount2017 = parameterMaps.mapBillDetailPtAmount2017; // 

		/**
		 * 2113外发加工成品返修
		 */
		Map<String, Double> mapBillDetailPtAmount2113 = parameterMaps.mapBillDetailPtAmount2113; // 

		/**
		 * 2114外发加工成品出库
		 */
		Map<String, Double> mapBillDetailPtAmount2114 = parameterMaps.mapBillDetailPtAmount2114; // 

		// 存取分页查询bom时所临时存放的成品列表
		List<Object[]> lsTempProduct = new ArrayList<Object[]>();
		Map<String, String> mapVersionId = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 报关常用工厂BOM版本id
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			mapVersionId.put(versionId, versionId);

			lsTempProduct.add(objs);

			if ((i != 0 && (mapVersionId.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				String clientTipMessage = "CAS 成品单据总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				List<String> lsVersionId = new ArrayList<String>();

				lsVersionId.addAll(mapVersionId.values());

				// 根据版本id查询出相应报关常用工厂BOM资料
				List lsBom = this.materialManageDao
						.findMaterielBomDetail(lsVersionId);

				lsVersionId.clear();
				mapVersionId.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsBom.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					lsTempProduct.clear();
					continue;
				}

				// Map key:成品版本号versionId value:料件明细
				Map<String, List<Object[]>> mapVersionIdBom = new HashMap<String, List<Object[]>>();

				for (int j = 0, n = lsBom.size(); j < n; j++) {
					Object[] objsBom = (Object[]) lsBom.get(j);
					String tempVersionId = (String) objsBom[1];
					if (mapVersionIdBom.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(objsBom);
						mapVersionIdBom.put(tempVersionId, temp);
					} else {
						List<Object[]> objsTemp = mapVersionIdBom
								.get(tempVersionId);
						objsTemp.add(objsBom);
					}
				}
				for (int j = 0, n = lsTempProduct.size(); j < n; j++) {
					objs = (Object[]) lsTempProduct.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					String typeCode = (String) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // 版本id
					List<Object[]> tempObjs = mapVersionIdBom
							.get(tempVersionId);
					if (tempObjs == null) {
						logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjs.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjs.get(k);

						// 料号为 key
						String ptNoKey = (String) bomObjs[0];

						// 单耗
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];

						// 单项用量
//						Double unitUsed = bomObjs[3] == null ? 0.0
//								: (Double) bomObjs[3];

						// 损耗
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 料件耗用总用量----> 成品数量*(单耗/(1-损耗率))
						Double totalUnitUsed = ptAmount
								* (unitWaste / (1 - waste));

						if (typeCode.equals("2001")) {// 2001 成品起初单
							if (mapBillDetailPtAmount2001.get(ptNoKey) == null) {
								mapBillDetailPtAmount2001.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2001
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2001.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2002")) {// 2002 车间入库
							if (mapBillDetailPtAmount2002.get(ptNoKey) == null) {
								mapBillDetailPtAmount2002.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2002
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2002.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2015")) {// 2015 外发加工成品入库
							if (mapBillDetailPtAmount2015.get(ptNoKey) == null) {
								mapBillDetailPtAmount2015.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2015
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2015.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2017")) {// 2017 外发加工成品退回
							if (mapBillDetailPtAmount2017.get(ptNoKey) == null) {
								mapBillDetailPtAmount2017.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2017
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2017.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2113")) {// 2113 外发加工成品返修
							if (mapBillDetailPtAmount2113.get(ptNoKey) == null) {
								mapBillDetailPtAmount2113.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2113
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2113.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2114")) {// 2114外发加工成品出库
							if (mapBillDetailPtAmount2114.get(ptNoKey) == null) {
								mapBillDetailPtAmount2114.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2114
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2114.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2007")) {// 2007 受托加工车间入库
							if (mapBillDetailPtAmount2007.get(ptNoKey) == null) {
								mapBillDetailPtAmount2007.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2007
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2007.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2009")) {// 2009 结转成品退货单
							if (mapBillDetailPtAmount2009.get(ptNoKey) == null) {
								mapBillDetailPtAmount2009.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2009
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2009.put(ptNoKey,
										tempAmout);
							}
							// 结转成品
							if (mapBillDetailPtAmountCpTran.get(ptNoKey) == null) {
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										-totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountCpTran
										.get(ptNoKey);
								tempAmout -= totalUnitUsed;
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2010")) {// 2010 受托加工退回成品
							if (mapBillDetailPtAmount2010.get(ptNoKey) == null) {
								mapBillDetailPtAmount2010.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2010
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2010.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2101")) {// 2101 直接出口
							if (mapBillDetailPtAmount2101.get(ptNoKey) == null) {
								mapBillDetailPtAmount2101.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2101
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2101.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2102")) {// 2102 结转出口
							if (mapBillDetailPtAmount2102.get(ptNoKey) == null) {
								mapBillDetailPtAmount2102.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2102
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2102.put(ptNoKey,
										tempAmout);
							}
							// 结转成品
							if (mapBillDetailPtAmountCpTran.get(ptNoKey) == null) {
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountCpTran
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2011")) {// 2011已交货未结转期初单
							if (mapBillDetailPtAmountCpTran.get(ptNoKey) == null) {
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										-totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountCpTran
										.get(ptNoKey);
								tempAmout -= totalUnitUsed;
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2012")) {// 2012已结转未交货期初单
							// 结转成品
							if (mapBillDetailPtAmountCpTran.get(ptNoKey) == null) {
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountCpTran
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmountCpTran.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2103")) {// 2103 返回车间
							if (mapBillDetailPtAmount2103.get(ptNoKey) == null) {
								mapBillDetailPtAmount2103.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2103
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2103.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2106")) {// 2106 海关批准内销
							if (mapBillDetailPtAmount2106.get(ptNoKey) == null) {
								mapBillDetailPtAmount2106.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2106
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2106.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2107")) {// 2107 其他内销
							if (mapBillDetailPtAmount2107.get(ptNoKey) == null) {
								mapBillDetailPtAmount2107.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2107
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2107.put(ptNoKey,
										tempAmout);
							}
						} else if (typeCode.equals("2110")) {// 2110 受托加工返回
							if (mapBillDetailPtAmount2110.get(ptNoKey) == null) {
								mapBillDetailPtAmount2110.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmount2110
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmount2110.put(ptNoKey,
										tempAmout);
							}
						}
						// F26 入库成品-出库成品
						if (typeCode.equals("2001") // 2001 成品期初单
								|| typeCode.equals("2002")// 2002 车间入库
								|| typeCode.equals("2003")// 2003 退厂返工
								|| typeCode.equals("2004")// 2004 成品盘盈单
								|| typeCode.equals("2005")// 2005 成品转仓入库
								|| typeCode.equals("2008")// 2008 其他成品退货单
								|| typeCode.equals("2009")// 2009 结转成品退货单
								|| typeCode.equals("2015")// 2015 外发加工成品入库
								|| typeCode.equals("2017")// 2017外发加工成品退回
						) { // 入库
							if (mapBillDetailPtAmountF26.get(ptNoKey) == null) {
								mapBillDetailPtAmountF26.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountF26
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmountF26
										.put(ptNoKey, tempAmout);
							}
						}
						if (typeCode.equals("2101") // 2101 直接出口
								|| typeCode.equals("2102")// 2102 结转出口
								|| typeCode.equals("2103")// 2103 返回车间
								|| typeCode.equals("2104")// 2104 返工复出
								|| typeCode.equals("2105")// 2105 成品盘亏单
								|| typeCode.equals("2106")// 2106 海关批准内销
								|| typeCode.equals("2107")// 2107 其他内销
								|| typeCode.equals("2108")// 2108 其他处理
								|| typeCode.equals("2109")// 2109 成品转仓出库
								|| typeCode.equals("2113")// 2113外发加工成品返修
								|| typeCode.equals("5101")// 5101残次品 出库（成品部分）
						) { // 出库
							if (mapBillDetailPtAmountF26.get(ptNoKey) == null) {
								mapBillDetailPtAmountF26.put(ptNoKey,
										-totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountF26
										.get(ptNoKey);
								tempAmout -= totalUnitUsed;
								mapBillDetailPtAmountF26
										.put(ptNoKey, tempAmout);
							}
						}
					}
				}
				lsTempProduct.clear();
			}
		}
	}

	/**
	 * 初始化 半成品单据明细折BOM （用Map缓存） wss2010.11.17整理
	 */
	private void initMapBillDetailHalfProduct(Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailHalfProduct a,MaterialBomVersion v  "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) "
				+ " from MaterialBomVersion c "
				+ " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " and a.billMaster.billType.code not in(?,?) "
				+ " group by a.billMaster.billType.code,v.id"
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		parameters.add(endDate);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("4001");
		parameters.add("4101"); // 不为半成品入库与半成品出库
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());

		// 残次品（半成品部分）
		selects = " select sum(a.ptAmount),a.billMaster.billType.code,v.id "
				+ " from BillDetailRemainProduct a,MaterialBomVersion v  "
				+ " where a.ptPart = v.master.materiel.ptNo "
				+ " and ( a.version = v.version or ( a.version is null and v.version = "
				+ "(select min(c.version) " + " from MaterialBomVersion c "
				+ " where c.master=v.master))) "
				+ " and a.billMaster.isValid = ? "
				+ " and a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? " + " and ("
				+ " (a.billMaster.billType.code =?  and a.note = ?) "
				+ " or (a.billMaster.billType.code =? and a.note in(?,?)) "
				+ " or (a.billMaster.billType.code =?  and a.note = ?)) "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,v.id "
				+ " order by v.id ";// order by 确保 bom id 只查询一次

		parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(CommonUtils.getBeginDate(Integer.valueOf(CommonUtils
				.getYear()), 0, 1));
		parameters.add(endDate);
		parameters.add("5101");
		parameters.add("半成品");

		parameters.add("5001");
		parameters.add("成品");
		parameters.add("料件");

		parameters.add("5002");
		parameters.add("半成品");

		parameters.add(CommonUtils.getCompany().getId());
		beginTime = System.currentTimeMillis();
		List list5001 = casDao.commonSearch(selects, parameters.toArray());

		list.addAll(list5001);

		logger.info("CAS 开始查询所有半成品单据的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

		/**
		 * 半成品结余 + 4002半成品盘盈单 + 4004外发加工半成品退 + 4006外发加工半成品入库 - 4102半成品盘亏单 -
		 * 4103委外加工出库 - 4104外发加工半成品返修 - 4106委外加工出库 - 5001残次品入库（半成品部分
		 */
		Map<String, Double> mapBillDetailPtAmountHalf = parameterMaps.mapBillDetailPtAmountHalf; //

		/**
		 * 半成品外发 + 4103委外加工出库 + 4104外发加工半成品返修 + 4106委外加工出库 - 4003委外加工入库 -
		 * 4004外发加工半成品退 - 4006外发加工半成品入库
		 */
		Map<String, Double> mapBillDetailPtAmountHalfOutSource = parameterMaps.mapBillDetailPtAmountHalfOutSource; // 

		// 存取分页查询bom时所临时存放的成品列表
		List<Object[]> lsTemp = new ArrayList<Object[]>();
		Map<String, String> mapVersionId = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // 半成品 版本.id
			if (versionId == null) {
				continue;
			}
			// 为了去掉重复的 versionId
			mapVersionId.put(versionId, versionId);
			lsTemp.add(objs);

			if ((i != 0 && (mapVersionId.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				String clientTipMessage = "CAS 半成品单据总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);

				progressInfo.setHintMessage(clientTipMessage);

				List<String> lsVersionId = new ArrayList<String>();
				lsVersionId.addAll(mapVersionId.values());

				// 查找相应的报关常用工厂BOM资料
				List lsMaterialBomDetail = this.materialManageDao
						.findMaterielBomDetail(lsVersionId);

				lsVersionId.clear();
				mapVersionId.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsMaterialBomDetail.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					lsTemp.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = lsMaterialBomDetail.size(); j < n; j++) {
					Object[] objsBom = (Object[]) lsMaterialBomDetail.get(j);
					String tempVersionId = (String) objsBom[1];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(objsBom);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(objsBom);
					}
				}
				for (int j = 0, n = lsTemp.size(); j < n; j++) {
					objs = (Object[]) lsTemp.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					String typeCode = (String) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("Cas 计算时 Bom 明细为空  !! == " + tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);

						// 料号为 key
						String ptNoKey = (String) bomObjs[0];

						// 单耗
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];

						// 成品单项用量
//						Double unitUsed = bomObjs[3] == null ? 0.0
//								: (Double) bomObjs[3];

						// 损耗
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 料件的半成品单耗用总量=半成品数量*(单耗/(1-损耗率)
						Double totalUnitUsed = ptAmount
								* (unitWaste / (1 - waste));

						if (typeCode.equals("4002")// 4002半成品盘盈单
								|| typeCode.equals("4004")// 4004外发加工半成品退
								|| typeCode.equals("4006")// 4006外发加工半成品入库
								|| typeCode.equals("4009")// 4009半成品期实
								|| typeCode.equals("5002")// 5002外发加工半成品入库
						) { // 入库
							if (mapBillDetailPtAmountHalf.get(ptNoKey) == null) {
								mapBillDetailPtAmountHalf.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountHalf
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmountHalf.put(ptNoKey,
										tempAmout);
							}
						}
						if (typeCode.equals("4102") // 4102半成品盘亏单
								|| typeCode.equals("4103")// 4103委外加工出库
								|| typeCode.equals("4104")// 4104外发加工半成品返修
								|| typeCode.equals("4106")// 4106委外加工出库
								|| typeCode.equals("5101")// 5101残次品 出库（半成品部分
								|| typeCode.equals("5001")// 5001残次品 入库（成品、料件部分
						) { // 出库
							if (mapBillDetailPtAmountHalf.get(ptNoKey) == null) {
								mapBillDetailPtAmountHalf.put(ptNoKey,
										-totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountHalf
										.get(ptNoKey);
								tempAmout -= totalUnitUsed;
								mapBillDetailPtAmountHalf.put(ptNoKey,
										tempAmout);
							}
						}

						// 下面统计半成品外发
						if (// typeCode.equals("4103")||// 4103委外加工出库
						typeCode.equals("4104")// 4104外发加工半成品返修
								|| typeCode.equals("4106")// 4106委外加工出库
						) { // 入库
							if (mapBillDetailPtAmountHalfOutSource.get(ptNoKey) == null) {
								mapBillDetailPtAmountHalfOutSource.put(ptNoKey,
										totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountHalfOutSource
										.get(ptNoKey);
								tempAmout += totalUnitUsed;
								mapBillDetailPtAmountHalfOutSource.put(ptNoKey,
										tempAmout);
							}
						}
						if (// typeCode.equals("4003")|| // 4003委外加工入库
						typeCode.equals("4004")// 4004外发加工半成品退
								|| typeCode.equals("4006")// 4006外发加工半成品入库
						) { // 出库
							if (mapBillDetailPtAmountHalfOutSource.get(ptNoKey) == null) {
								mapBillDetailPtAmountHalfOutSource.put(ptNoKey,
										-totalUnitUsed);
							} else {
								Double tempAmout = mapBillDetailPtAmountHalfOutSource
										.get(ptNoKey);
								tempAmout -= totalUnitUsed;
								mapBillDetailPtAmountHalfOutSource.put(ptNoKey,
										tempAmout);
							}
						}
					}
				}
				lsTemp.clear();
			}
		}
	}

	/**
	 * 初始化报关成品 电子帐册Bcus
	 * 
	 * @param beginDate开始日期
	 * @param endDate
	 *            截止日期 wss2010.11.17整理
	 */
	private void initMapCustomsProductWasterBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,v.id "
				+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",EmsHeadH2kVersion v left join v.emsHeadH2kExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.version = v.version  "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.modifyMark=? "
				+ " and exg.emsHeadH2k.historyState=?  "
				+ " group by a.baseCustomsDeclaration.impExpType,v.id "
				+ " order by v.id "; // order by 确保 版本id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));

		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_EXPORT); // 直接出口
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT); // 转厂出口
		parameters.add(ImpExpType.REWORK_EXPORT); // 返工复出
		parameters.add(ImpExpType.BACK_FACTORY_REWORK); // 退厂返工

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("0");
		parameters.add(false);
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [联网监管] 报关成品(正在执行的合同) 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> mapCustomsProductWaste = parameterMaps.mapCustomsProductWaste;

		List<Object[]> lsTemp = new ArrayList<Object[]>();
		Map<String, String> mapVersionId = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2]; // version guid
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			mapVersionId.put(versionId, versionId);

			lsTemp.add(objs);
			if ((i != 0 && (mapVersionId.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算 报关成品（正在执行的合同）总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				List<String> lsVersionId = new ArrayList<String>();
				lsVersionId.addAll(mapVersionId.values());
				List lsBom = this.casLeftoverMaterielDao
						.findEmsHeadH2kBomDetail(lsVersionId);

				lsVersionId.clear();
				mapVersionId.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsBom.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					lsTemp.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> mapBomObject = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = lsBom.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) lsBom.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (mapBomObject.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						mapBomObject.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = mapBomObject.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = lsTemp.size(); j < n; j++) {
					objs = (Object[]) lsTemp.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = mapBomObject.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcus Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste / 100.0);

						// 成品单耗
						Double totalUnitUsed = tempHsAmount * unitUsed;

						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT // 直接出口
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT // 返工复出
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT) {// 转厂出口
							if (mapCustomsProductWaste.get(key) == null) {
								mapCustomsProductWaste.put(key, totalUnitUsed);
							} else {
								mapCustomsProductWaste.put(key,
										mapCustomsProductWaste.get(key)
												+ totalUnitUsed);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							totalUnitUsed = -totalUnitUsed;
							if (mapCustomsProductWaste.get(key) == null) {
								mapCustomsProductWaste.put(key, totalUnitUsed);
							} else {
								mapCustomsProductWaste.put(key,
										mapCustomsProductWaste.get(key)
												- totalUnitUsed);
							}
						}
					}
				}
				lsTemp.clear();
			}
		}
	}

	/**
	 * 初始化报关料件 Bcus
	 * 
	 * @param beginDate开始日期
	 * @param endDate截止日期
	 *            wss2010.11.17整理
	 */
	private void initMapCustomsMaterielBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from CustomsDeclarationCommInfo a "
				+ " left join a.baseCustomsDeclaration "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in ( ?,?,?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_IMPORT); // 直接进口
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT); // 余料结转出
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT); //余料结转入
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT); // 退料出口
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES); // 海关批准内销
		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子帐册] 报关正在执行的帐册的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 合同进口映射
		 */
		Map<String, Double> mapCustomsMaterialAmount = parameterMaps.mapCustomsMaterialAmount;

		// /**
		// * 转厂进口映射
		// */
		// Map<String, Double> mapTransFactInAmount =
		// parameterMaps.mapTransFactInAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			if (typeCode.intValue() == ImpExpType.DIRECT_IMPORT
					|| typeCode.intValue() == ImpExpType.REMAIN_FORWARD_IMPORT
					|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				// 料件进口||余料结转转入||料件转厂
				if (mapCustomsMaterialAmount.get(key) == null) {
					mapCustomsMaterialAmount.put(key, tempHsAmount);
				} else {
					mapCustomsMaterialAmount.put(key, mapCustomsMaterialAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.REMAIN_FORWARD_EXPORT
					|| typeCode.intValue() == ImpExpType.BACK_MATERIEL_EXPORT
					|| typeCode.intValue() == ImpExpType.MATERIAL_DOMESTIC_SALES) {
				// -余料结转转出 ||退料出口||海关批准内销
				if (mapCustomsMaterialAmount.get(key) == null) {
					mapCustomsMaterialAmount.put(key, -tempHsAmount);
				} else {
					mapCustomsMaterialAmount.put(key, mapCustomsMaterialAmount
							.get(key)
							- tempHsAmount);
				}
			}
		}
	}

	/**
	 * 初始化外发成品折BOM　，Bcus (前提为手册类型为只选了一种，其中BOM为合同中的单耗表)
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initMapSendAmountBcus(Date endDate, ParameterMaps parameterMaps) {
		/**
		 * key=名称+"/"+规格+"/"+单位 对应 获取成品版本号对应的料件明细
		 */
		Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();

		// 合同中的成品信息
		List lsExg = this.casLeftoverMaterielDao.findExingBcusEmsExgBill();
		for (int i = 0; i < lsExg.size(); i++) {
			Object[] exgObjs = (Object[]) lsExg.get(i);
			String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
			String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
			String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			String exgId = (String) exgObjs[3];

			// 查找对应的BOM(最大版本号)
			List lsBom = this.casLeftoverMaterielDao.findBcusEmsBomBill(exgId);
			if (lsBom.size() == 0) {
				System.out.println("成品" + key + "没有找到bom");
			}
			bomObjectMap.put(key, lsBom);
		}

		/**
		 * 外发数量 + 2102结转出口-2011 已结转未交货期初单 - 2009结转成品退货单 + 2012已交货未结转期初单
		 */
		Map<String, Double> mapSendAmount = parameterMaps.mapSendAmount;

		// 以名称+规格+单位,单据类型号码来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List carryForwardList = casDao.commonSearch(selects,
				"BillDetailProduct", conditions, groupBy, leftOuter);
		for (int i = 0; i < carryForwardList.size(); i++) {
			Object[] objs = (Object[]) carryForwardList.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;
			String typeCode = (String) objs[4] == null ? "" : (String) objs[4];
			List<Object[]> tempObjes = bomObjectMap.get(key);
			if (tempObjes == null) {
				logger.info("单据------短溢表计算 bcus Bom 不存在  !! == " + key);
				continue;
			}
			for (Object[] bomObjs : tempObjes) {
				// 料件
				String hsName1 = bomObjs[0] == null ? "" : (String) bomObjs[0];
				String hsSpec1 = bomObjs[1] == null ? "" : (String) bomObjs[1];
				String hsUnitName1 = bomObjs[2] == null ? ""
						: (String) bomObjs[2];

				// key=名称+"/"+规格+"/"+单位
				String keyMaterial = hsName1 + "/" + hsSpec1 + "/"
						+ hsUnitName1;

				// 损耗
				Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
				waste = waste / 100.0;

				// 单耗
				Double unitWear = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];

				// 单项用量 = 单耗 / (1-损耗);
				Double unitUsed = unitWear / (1 - waste);

				// 成品单耗损
				Double totalUnitUsed = hsAmount * unitUsed;
				if (typeCode.equals("2009") // 2009结转成品退货单
						|| typeCode.equals("2012")) {// 2012已交货未结转期初单

					totalUnitUsed = -totalUnitUsed;
				} else if (typeCode.equals("2102") // 2102结转出口
						|| typeCode.equals("2011")) {// 2011已结转未交货期初单

					totalUnitUsed = +totalUnitUsed;
				}

				if (mapSendAmount.get(keyMaterial) == null) {
					mapSendAmount.put(keyMaterial, totalUnitUsed);
				} else {
					mapSendAmount.put(keyMaterial, mapSendAmount
							.get(keyMaterial)
							+ totalUnitUsed);
				}
			}
		}
	}

	/**
	 * 初始化报关成品出口耗料 电子化手册Bcs
	 * 
	 * @param beginDate
	 * @param endDate
	 *            wss2010.11.17整理
	 */
	private void initMapCustomsProductWasterBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
				+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.contract.declareState in(?)" // 
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);// 正在执行
//		parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> mapCustomsProductWaste = parameterMaps.mapCustomsProductWaste;// 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> exgIdMap = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String exgId = (String) objs[2]; // 成品 id
			if (exgId == null) {
				continue;
			}

			// 为了去掉重复的 exgId
			exgIdMap.put(exgId, exgId);
			tempList.add(objs);
			if ((i != 0 && (exgIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算电子化手册 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				List<String> lsVersionId = new ArrayList<String>();
				lsVersionId.addAll(exgIdMap.values());
				List lsBom = this.casLeftoverMaterielDao
						.findContractBomDetail(lsVersionId);
				lsVersionId.clear();
				exgIdMap.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsBom.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> mapBom = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = lsBom.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) lsBom.get(j);
					String tempExgId = (String) bomObjs[5];
					if (mapBom.get(tempExgId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						mapBom.put(tempExgId, temp);
					} else {
						List<Object[]> temp = mapBom.get(tempExgId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempExgId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = mapBom.get(tempExgId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcs Bom 明细为空  !! == " + tempExgId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste / 100.0);

						// 成品单耗损
						Double productMaterialWaste = tempHsAmount * unitUsed;
						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT // 直接出口
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT // 转厂出口
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) { // 返工复出

							if (mapCustomsProductWaste.get(key) == null) {
								mapCustomsProductWaste.put(key,
										productMaterialWaste);
							} else {
								mapCustomsProductWaste.put(key,
										mapCustomsProductWaste.get(key)
												+ productMaterialWaste);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							if (mapCustomsProductWaste.get(key) == null) {
								mapCustomsProductWaste.put(key,
										-productMaterialWaste);
							} else {
								mapCustomsProductWaste.put(key,
										mapCustomsProductWaste.get(key)
												- productMaterialWaste);
							}
						}
					}
				}
				tempList.clear();
			}
		}
	}
	
	/**
	 * 初始化报关成品转厂出口耗料（加上已核销的部分） 电子化手册Bcs
	 * 使用单据对应折料
	 * @param beginDate
	 * @param endDate
	 *            hclBcs
	 */
	private void initMapTransFactOutWasterBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();

		Map<String, Double> beforResult7Map = parameterMaps.beforResult7Map;
		Map<String, Double> beforResult8Map = parameterMaps.beforResult8Map;
		
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF7F8Map;
		{
			String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,a.billMaster.billType.code  ";
			String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,a.billMaster.scmCoc.name  ";
			String leftOuter = " left join a.hsUnit ";
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("and", null, "billMaster.isValid", "=",
					Boolean.valueOf(true), null));
			conditions.add(new Condition("and", null, "hsName is not null ", null,
					null, null));
			conditions.add(new Condition("and", "(", "billMaster.billType.code",
					"=", "2009", null)); // 2009 结转成品退货单
			conditions.add(new Condition("or", null, "billMaster.billType.code",
					"=", "2102", null)); // 2102 结转出口
			conditions.add(new Condition("or", null, "billMaster.billType.code",
					"=", "2011", null)); // 2011已交货未结转期初单
			conditions.add(new Condition("or", null, "billMaster.billType.code",
					"=", "2012", ")")); // 2012已结转未交货期初单
			conditions.add(new Condition("and", null, " billMaster.validDate ",
					">=", CommonUtils.getBeginDate(Integer
							.valueOf(getYear(endDate)), 0, 1), null));
			conditions.add(new Condition("and", null, " billMaster.validDate ",
					"<=", endDate, null));
			List sendBillList = casDao.commonSearch(selects, "BillDetailProduct",
					conditions, groupBy, leftOuter);

			for (int i = 0; i < sendBillList.size(); i++) {
				Object[] objs = (Object[]) sendBillList.get(i);
				Double mapHsAmount = 0.0;
				Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
				String hsName = (String) objs[1] == null ? "" : (String) objs[1];
				String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
				String unitName = (String) objs[3] == null ? "" : (String) objs[3];
				// String complex = (String) objs[4] == null ? "" : (String)
				// objs[4];
				String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
				String typeCode = (String) objs[5] == null ? "" : (String) objs[5];
				
				String emsHeadH2k = "";
				// key=客户名称+报关名称+报关规格+单位
				String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
				// + "/" + complex;
				// 不包含客户名称
				String tempName = hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
				// + "/"+ complex;

				hsNameMap.put(key, tempName);
				if (sendBillMap.get(key) != null)
					mapHsAmount = sendBillMap.get(key);

				if (typeCode.equals("2009") // 2009结转成品退货单
						|| typeCode.equals("2012")) {// 2012已结转未交货期初单

					mapHsAmount -= hsAmount;
				} else if (typeCode.equals("2102") // 2102结转出口
						|| typeCode.equals("2011")) {// 2011已交货未结转期初单

					mapHsAmount += hsAmount;
				}
				sendBillMap.put(key, mapHsAmount);
			}

			// 2.获取成品报关单map
			List<Object> parameters = new ArrayList<Object>();
			selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
					+ " a.baseCustomsDeclaration.customer.name"
					+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
					+ " where a.commSerialNo = exg.seqNum "
					+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
					+ " and exg.contract.declareState in(?,?) "
					+ " and a.baseCustomsDeclaration.effective = ? "
					+ " and a.baseCustomsDeclaration.declarationDate >= ? "
					+ " and a.baseCustomsDeclaration.declarationDate <= ? "
					+ " and a.baseCustomsDeclaration.impExpType = ?  "
					+ " and a.baseCustomsDeclaration.company.id = ? "
					+ " group by exg.name,exg.spec,exg.unit.name,"
					+ " a.baseCustomsDeclaration.customer.name ";
			parameters.add(DeclareState.PROCESS_EXE);// 正在运行合同
			parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销合同
			parameters.add(Boolean.valueOf(true));// 生效
			parameters.add(CommonUtils.getBeginDate(Integer
					.valueOf(getYear(endDate)), 0, 1));// 开始日期
			parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

			parameters.add(CommonUtils.getCompany().getId());

			long beginTime = System.currentTimeMillis();
			List customsList = casDao.commonSearch(selects, parameters.toArray());
			String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 报关成品 的共用时间: "
					+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			for (int i = 0; i < customsList.size(); i++) {
				Object[] objs = (Object[]) customsList.get(i);
				Double mapHsAmount = 0.0;
				Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
				String hsName = (String) objs[1] == null ? "" : (String) objs[1];
				String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
				String unitName = (String) objs[3] == null ? "" : (String) objs[3];
				// String complex = (String) objs[4] == null ? "" : (String)
				// objs[4];
				String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
				
				String emsHeadH2k = "";
				// key=客户名称+报关名称+报关规格+单位
				String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
				// + "/" + complex;
				// 不包含客户名称
				String tempName = hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
				// + "/"+ complex;
				hsNameMap.put(key, tempName);
				if (customsBillMap.get(key) != null)
					mapHsAmount = customsBillMap.get(key);
				mapHsAmount = +hsAmount;
				customsBillMap.put(key, mapHsAmount);
			}

			// 3.单据数量 - 报关数量 放进MAP
			List<String> removeList = new ArrayList<String>();
			for (Entry<String, Double> entry : customsBillMap.entrySet()) {
				String key = entry.getKey();
				Double value = entry.getValue();
				Double sendValue = sendBillMap.get(key);
				Double result = 0.0;

				if (sendValue != null) {
					result = sendValue - value;
					sendBillMap.remove(key);
					removeList.add(key);
				} else {
					result = -value;
				}
				// 0.0001为误差允许范围
				if (result > 0.0001) {
					beforResult7Map.put(key, result);
				}
			}
			// 移除已经计算过的
			for (int i = 0; i < removeList.size(); i++) {
				customsBillMap.remove(removeList.get(i));
			}
			removeList.clear();

			for (Entry<String, Double> entry : sendBillMap.entrySet()) {
				String key = entry.getKey();
				Double sendValue = entry.getValue();
				Double value = customsBillMap.get(key);
				Double result = 0.0;
				if (value != null) {
					result = sendValue - value;
				} else {
					result = sendValue;
				}
				result *= (-1);
				// 0.0001为误差允许范围
				if (result < (-0.0001)) {
					beforResult7Map.put(key, -result);
				}
			}
			
		}
		
		
		
		// ----------------------------准备f8数据-------------------//
		
		List sendBillList = casDao.findBillDetailByNameSpecUnit(beginDate, endDate);
		
		
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计
		for (int i = 0; i < sendBillList.size(); i++) {
			Object[] objs = (Object[]) sendBillList.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String typeCode = (String) objs[5] == null ? "" : (String) objs[5];
			String emsHeadH2k = (String) objs[7] == null ? "" : (String) objs[7];
			// key=客户名称+报关名称+报关规格+单位
			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
			// + "/" + complex;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
			// + "/"+ complex;

			hsNameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount -= hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount += hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
		}

		// 2.获取成品报关单map
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,a.baseCustomsDeclaration.emsHeadH2k"
				+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and exg.contract.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,a.baseCustomsDeclaration.emsHeadH2k ";
		parameters.add(DeclareState.PROCESS_EXE);// 正在运行合同
		parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());
		
		long beginTime = System.currentTimeMillis();
		List customsList = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < customsList.size(); i++) {
			Object[] objs = (Object[]) customsList.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String emsHeadH2k = (String) objs[5] == null ? "" : (String) objs[5];
			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
			// + "/" + complex;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName + "/" + emsHeadH2k;
			// + "/"+ complex;
			hsNameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(key);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(key);
				removeList.add(key);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			if (result < (-0.0001)) {

				beforResult8Map.put(key, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();

		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String key = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(key);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(key, result);
			}
		}

	}

	/**
	 * 初始化报关成品转厂出口耗料（加上已核销的部分） 电子化手册Bcs
	 * 使用最新合同折料
	 * @param beginDate
	 * @param endDate
	 *            hclBcs
	 */
	private void initMapTransFactOutWasterBcsByNewContract(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();

		Map<String, Double> beforResult7Map = parameterMaps.beforResult7Map;
		Map<String, Double> beforResult8Map = parameterMaps.beforResult8Map;
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF7F8Map;
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,a.billMaster.scmCoc.name  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List sendBillList = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);

		for (int i = 0; i < sendBillList.size(); i++) {
			Object[] objs = (Object[]) sendBillList.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String typeCode = (String) objs[5] == null ? "" : (String) objs[5];
			// key=客户名称+报关名称+报关规格+单位
			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName;
			// + "/" + complex;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName;
			// + "/"+ complex;

			hsNameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount -= hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount += hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
		}

		// 2.获取成品报关单map
		List<Object> parameters = new ArrayList<Object>();
		selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name"
				+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and exg.contract.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name ";
		parameters.add(DeclareState.PROCESS_EXE);// 正在运行合同
		parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List customsList = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < customsList.size(); i++) {
			Object[] objs = (Object[]) customsList.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName;
			// + "/" + complex;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName;
			// + "/"+ complex;
			hsNameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(key);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(key);
				removeList.add(key);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult7Map.put(key, result);
			} else if (result < (-0.0001)) {

				beforResult8Map.put(key, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();

		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String key = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(key);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(key, result);
			} else if (result < (-0.0001)) {
				beforResult7Map.put(key, -result);
			}
		}

	}

	/**
	 * 初始化报关成品 电子化手册Bcs
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate截止日期
	 *            wss2010.11.17整理
	 */
	private void initMapCustomsMaterielBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from BcsCustomsDeclarationCommInfo a "
				+ " left join a.baseCustomsDeclaration "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in ( ?,?,?,?,?,? ) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in "
				+ " (select b.emsNo "
				+ " from Contract b "
				+ " where b.company.id = ? "
				+ " and b.declareState in(?)"
				+ " and b.emsNo is not null)"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";

		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_IMPORT); // 直接进口
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT); // 退料出口
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 余料结转入
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// 余料结转出
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES); // 海关批准内销

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
//		parameters.add(DeclareState.CHANGING_CANCEL);
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 报关正在执行的合同的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 合同进口料件映射
		 */
		Map<String, Double> mapCustomsMaterialAmount = parameterMaps.mapCustomsMaterialAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			if (typeCode.intValue() == ImpExpType.DIRECT_IMPORT
					|| typeCode.intValue() == ImpExpType.REMAIN_FORWARD_IMPORT
					|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				// 料件进口||余料结转转入||料件转厂
				if (mapCustomsMaterialAmount.get(key) == null) {
					mapCustomsMaterialAmount.put(key, tempHsAmount);
				} else {
					mapCustomsMaterialAmount.put(key, mapCustomsMaterialAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.REMAIN_FORWARD_EXPORT
					|| typeCode.intValue() == ImpExpType.BACK_MATERIEL_EXPORT
					|| typeCode.intValue() == ImpExpType.MATERIAL_DOMESTIC_SALES) {
				// -余料结转转出 ||退料出口||海关批准内销
				if (mapCustomsMaterialAmount.get(key) == null) {
					mapCustomsMaterialAmount.put(key, -tempHsAmount);
				} else {
					mapCustomsMaterialAmount.put(key, mapCustomsMaterialAmount
							.get(key)
							- tempHsAmount);
				}
			}
		}
	}

	/**
	 * 初始化结转进口与报关单转厂进口
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initMapTransFactInAmont(Date endDate,
			ParameterMaps parameterMaps) {
		// 查询单据中的转厂部份
		/**
		 * 转厂进口报关数
		 */
		Map<String, Double> mapTransFactInAmount = parameterMaps.mapTransFactInAmount;
		/**
		 * 料件收货数量
		 */
		Map<String, Double> mapRecvAmount = parameterMaps.mapRecvAmount;

		Map<String, Double> resultF9Map = new HashMap<String, Double>();
//		List<Object> parameters = new ArrayList<Object>();
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code,a.billMaster.scmCoc.name  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code ,a.billMaster.scmCoc.name  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1004", null)); // 1004 结转进口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1106", null)); // 1106 结转料件退货单

		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1015", null)); // 1015 已收货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1016", ")")); // 1016 已结转未收货期初单

		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", CommonUtils.getEndDate(endDate), null));

		List listBill = casDao.commonSearch(selects, "BillDetailMateriel",
				conditions, groupBy, leftOuter);

		for (int i = 0; i < listBill.size(); i++) {
			Object[] objs = (Object[]) listBill.get(i);
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String typeCode = objs[4] == null ? "" : (String) objs[4];
			String customter = objs[5] == null ? "" : (String) objs[5];
			Double tempAmount = 0.0;
			// key=客户+名称 +规格+单位
			String key = customter + "/" + hsName + "/" + hsSpec + "/"
					+ unitName;
			String tempKey = hsName + "/" + hsSpec + "/" + unitName;

			if (parameterMaps.resultHsNameSpecUnitF6F9Map.get(key) == null) {
				parameterMaps.resultHsNameSpecUnitF6F9Map.put(key, tempKey);
			}
			if (resultF9Map.get(key) != null)
				tempAmount = resultF9Map.get(key);

			// tempAmount=+1015-1016+1004-1106
			if (typeCode.equals("1106")) {// 1106结转料件退货单
				tempAmount -= hsAmount;
			} else if (typeCode.equals("1015")) {// 1015已收货未结转期初单
				tempAmount += hsAmount;
			} else if (typeCode.equals("1016")) {// 1016已结转未收货期初单
				tempAmount -= hsAmount;
			} else if (typeCode.equals("1004")) {// 1004结转进口
				tempAmount += hsAmount;
			}
			resultF9Map.put(key, tempAmount);
		}

		// // 计算转厂 6/9
		for (Entry<String, String> entry : parameterMaps.resultHsNameSpecUnitF6F9Map
				.entrySet()) {
			String key = entry.getKey();// 客户+名称 +规格+单位
			String value = entry.getValue();// 名称 +规格+单位
			Double transFactInAmount = CommonUtils
					.getDoubleExceptNull(parameterMaps.beforeResultF6Map
							.get(key));// A

			Double recvAmount = CommonUtils.getDoubleExceptNull(resultF9Map
					.get(key));// B

			if (recvAmount > transFactInAmount) {
				// 存放F9栏
				Double recvAmout = recvAmount - transFactInAmount;
				if (mapRecvAmount.get(value) != null) {
					recvAmout += mapRecvAmount.get(value);
				}
				mapRecvAmount.put(value, recvAmout);
			} else {
				// F6
				Double amount = transFactInAmount - recvAmount;
				if (mapTransFactInAmount.get(value) != null) {
					amount += mapTransFactInAmount.get(value);
				}
				mapTransFactInAmount.put(value, amount);
			}
		}

	}

	/**
	 * 初始化报关成品料件转厂（加上已核销部分） 电子化手册Bcs
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate截止日期
	 *            wss2010.11.17整理
	 */
	private void initMapTransFactInBcs(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF6F9Map;
		Map<String, Double> beforeResultF6Map = parameterMaps.beforeResultF6Map;

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ ",a.baseCustomsDeclaration.customer.name "
				+ " from BcsCustomsDeclarationCommInfo a "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in "
				+ " (select b.emsNo "
				+ " from Contract b "
				+ " where b.company.id = ? "
				+ " and b.declareState in(?,?)"
				+ " and b.emsNo is not null)"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name,a.baseCustomsDeclaration.customer.name  ";

		parameters.add(Boolean.valueOf(true));// 已生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(DeclareState.CHANGING_CANCEL);

		long beginTime = System.currentTimeMillis();
		List listCustoms = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 料件转厂报关单 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0, size = listCustoms.size(); i < size; i++) {
			Object[] objs = (Object[]) listCustoms.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
//			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];
			String hsComster = objs[5] == null ? "" : (String) objs[5];

			// key=客户+"/"+名称+"/"+规格+"/"+单位
			String key = hsComster + "/" + hsName + "/" + hsSpec + "/"
					+ hsUnitName;
			String tempKey = hsName + "/" + hsSpec + "/" + hsUnitName;

			hsNameMap.put(key, tempKey);
			beforeResultF6Map.put(key, tempHsAmount);
		}
	}

	/**
	 * 初始化外发成品折BOM　，Bcs (前提为手册类型为只选了一种，其中BOM为合同中的单耗表)
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initMapSendAmountBcs(Date endDate, ParameterMaps parameterMaps) {

	}

	/**
	 * 初始化报关成品 电子手册dzsc
	 * 
	 * @param beginDate
	 * @param endDate
	 *            wss2010.11.17整理
	 */
	private void initMapCustomsProductDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,exg.id "
				+ " from DzscCustomsDeclarationCommInfo a  left join a.baseCustomsDeclaration, "
				+ " DzscEmsExgBill exg left join exg.dzscEmsPorHead "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?,?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.dzscEmsPorHead.declareState in(?) "
				+ " group by a.baseCustomsDeclaration.impExpType,exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DzscState.EXECUTE);
//		parameters.add(DzscState.CHECK_CANCEL);
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [电子手册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		/**
		 * 出口成品损耗量映射
		 */
		Map<String, Double> mapCustomsProductWaste = parameterMaps.mapCustomsProductWaste; // 报关成品

		List<Object[]> lsTemp = new ArrayList<Object[]>();
		Map<String, String> mapVersionId = new HashMap<String, String>();
		int page = 20;
//		int xx = 0;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[2];
			if (versionId == null) {
				continue;
			}

			// 为了去掉重复的 versionId
			mapVersionId.put(versionId, versionId);
			lsTemp.add(objs);

			if ((i != 0 && (mapVersionId.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "短溢表计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				logger.info(clientTipMessage);
				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(mapVersionId.values());
				List lsBom = this.casLeftoverMaterielDao
						.findDzscEmsBomBill(versionIdList);
				versionIdList.clear();
				mapVersionId.clear();
				logger.info("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");
				if (lsBom.size() <= 0) {
					logger.info(" 对应的BOM明细为空!! ");
					lsTemp.clear();
					continue;
				}

				// 获取成品版本号对应的料件明细
				Map<String, List<Object[]>> mapBomObject = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = lsBom.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) lsBom.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (mapBomObject.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						mapBomObject.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = mapBomObject.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				// 循环刚才临时保存的成品数据 --> 并拆料件
				for (int j = 0, n = lsTemp.size(); j < n; j++) {
					objs = (Object[]) lsTemp.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[2]; // version guid
					List<Object[]> tempObjes = mapBomObject.get(tempVersionId);
					if (tempObjes == null) {
						logger.info("短溢表计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}
					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];

						// key=名称+"/"+规格+"/"+单位
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;

						// 损耗
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;

						// 单耗
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						// 单项用量 = 单耗 / (1-损耗);
						Double unitUsed = unitWear / (1 - waste);

						// 成品单耗损
						Double totalUnitUsed = tempHsAmount * unitUsed;

						if (typeCode.intValue() == ImpExpType.DIRECT_EXPORT// 直接出口
								|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_EXPORT// 转厂
								|| typeCode.intValue() == ImpExpType.REWORK_EXPORT) {// 返工复出

							if (mapCustomsProductWaste.get(key) == null) {
								mapCustomsProductWaste.put(key, totalUnitUsed);
							} else {
								mapCustomsProductWaste.put(key,
										mapCustomsProductWaste.get(key)
												+ totalUnitUsed);
							}
						} else if (typeCode.intValue() == ImpExpType.BACK_FACTORY_REWORK) {// 退厂返工
							if (mapCustomsProductWaste.get(key) == null) {
								mapCustomsProductWaste.put(key, -totalUnitUsed);
							} else {
								mapCustomsProductWaste.put(key,
										mapCustomsProductWaste.get(key)
												- totalUnitUsed);
							}
						}
					}
				}
				lsTemp.clear();
			}
		}
	}

	/**
	 * 初始化报关成品 转厂出口 加上已核销的部分 电子手册dzsc
	 * 
	 * @param beginDate
	 * @param endDate
	 *            hcl dzsc
	 */
	private void initMapTransFactOutWasterDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();
		Map<String, Double> beforResult7Map = parameterMaps.beforResult7Map;
		Map<String, Double> beforResult8Map = parameterMaps.beforResult8Map;
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF7F8Map;
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计

		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,a.billMaster.scmCoc.name  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List listBill = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);

		for (int i = 0; i < listBill.size(); i++) {
			Object[] objs = (Object[]) listBill.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			// String complex = (String) objs[4] == null ? "" : (String)
			// objs[4];
			String scmCoc = objs[4] == null ? "" : (String) objs[4];
			String typeCode = objs[5] == null ? "" : (String) objs[5];

			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName;
			hsNameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount -= hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount += hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
		}

		// 2.获取成品报关单map
		List<Object> parameters = new ArrayList<Object>();
		selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name"
				+ " from DzscCustomsDeclarationCommInfo a, DzscEmsExgBill exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
				+ " and exg.dzscEmsPorHead.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name ";
		parameters.add(DzscState.EXECUTE);// 正在运行合同
		parameters.add(DzscState.CHECK_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List listCustom = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子手册] 报关单转厂出口共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < listCustom.size(); i++) {
			Object[] objs = (Object[]) listCustom.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String key = scmCoc + "/" + hsName + "/" + hsSpec + "/" + unitName;
			// 不包含客户名称
			String tempName = hsName + "/" + hsSpec + "/" + unitName;
			hsNameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(key);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(key);
				removeList.add(key);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult7Map.put(key, result);
			} else if (result < (-0.0001)) {

				beforResult8Map.put(key, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();

		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String key = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(key);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(key, result);
			} else if (result < (-0.0001)) {
				beforResult7Map.put(key, -result);
			}
		}

	}

	/**
	 * 初始化报关成品 电子手册 Dzsc
	 * 
	 * @param beginDate开始日期
	 * @param endDate
	 *            截止日期 wss2010.11.17整理
	 */
	private void initMapCustomsMaterielDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {
		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ " from DzscCustomsDeclarationCommInfo a "
				+ " left join a.baseCustomsDeclaration "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in ( ?,?,?,?,?,?) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in ("
				+ " select b.emsNo from DzscEmsPorHead b where b.company.id = ? "
				+ " and b.declareState in(?) "
				+ " and b.emsNo is not null )"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name ";
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂进口
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// -退料出口
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 余料结转进
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// -余料结转出
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES); // -海关批准内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DzscState.EXECUTE);
//		parameters.add(DzscState.CHECK_CANCEL);

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子手册] 报关正在执行的合同["
				+ list.size() + "] 记录 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		/**
		 * 合同进口料件映射
		 */
		Map<String, Double> mapCustomsMaterialAmount = parameterMaps.mapCustomsMaterialAmount;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (typeCode.intValue() == ImpExpType.DIRECT_IMPORT
					|| typeCode.intValue() == ImpExpType.REMAIN_FORWARD_IMPORT
					|| typeCode.intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
				// 料件进口||余料结转转入||料件转厂
				if (mapCustomsMaterialAmount.get(key) == null) {
					mapCustomsMaterialAmount.put(key, tempHsAmount);
				} else {
					mapCustomsMaterialAmount.put(key, mapCustomsMaterialAmount
							.get(key)
							+ tempHsAmount);
				}
			} else if (typeCode.intValue() == ImpExpType.REMAIN_FORWARD_EXPORT
					|| typeCode.intValue() == ImpExpType.BACK_MATERIEL_EXPORT
					|| typeCode.intValue() == ImpExpType.MATERIAL_DOMESTIC_SALES) {
				// -余料结转转出 ||退料出口||海关批准内销
				if (mapCustomsMaterialAmount.get(key) == null) {
					mapCustomsMaterialAmount.put(key, -tempHsAmount);
				} else {
					mapCustomsMaterialAmount.put(key, mapCustomsMaterialAmount
							.get(key)
							- tempHsAmount);
				}
			}
		}
	}

	/**
	 * 初始化报关料件转厂 加上已核销的部分 电子手册 Dzsc
	 * 
	 * @param beginDate开始日期
	 * @param endDate
	 *            截止日期 wss2010.11.17整理
	 */
	private void initMapTransFactInDzsc(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF6F9Map;
		Map<String, Double> beforeResultF6Map = parameterMaps.beforeResultF6Map;

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ ",a.baseCustomsDeclaration.customer.name "
				+ " from DzscCustomsDeclarationCommInfo a "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k in ("
				+ " select b.emsNo from DzscEmsPorHead b where b.company.id = ? "
				+ " and b.declareState in( ? ,?)"
				+ " and b.emsNo is not null )"
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name,a.baseCustomsDeclaration.customer.name ";
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂进口
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DzscState.CHECK_CANCEL);
		parameters.add(DzscState.EXECUTE);

		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子手册] 报关Material ["
				+ list.size() + "] 记录 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
//			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];
			String hsComster = objs[5] == null ? "" : (String) objs[5];
			// key=客户+"/"+名称+"/"+规格+"/"+单位
			String key = hsComster + "/" + hsName + "/" + hsSpec + "/"
					+ hsUnitName;
			String tempKey = hsName + "/" + hsSpec + "/" + hsUnitName;

			hsNameMap.put(key, tempKey);
			beforeResultF6Map.put(key, tempHsAmount);
		}

	}

	/**
	 * 初始化外发成品折BOM　，Dzsc (前提为手册类型为只选了一种，其中BOM为合同中的单耗表)
	 * 
	 * @param endDate
	 * @param parameterMaps
	 */
	private void initMapSendAmountDzsc(Date endDate, ParameterMaps parameterMaps) {
		List lsExg = this.casLeftoverMaterielDao.findDzscEmsExgBill();

		// 获取成品版本号对应的料件明细
		Map<String, Object[]> exgObjectMap = new HashMap<String, Object[]>();
		Map<String, Date> mapDate = new HashMap<String, Date>();
		for (int j = 0, n = lsExg.size(); j < n; j++) {
			Object[] bomObjs = (Object[]) lsExg.get(j);
			// 成品
			String hsName = bomObjs[0] == null ? "" : (String) bomObjs[0];
			String hsSpec = bomObjs[1] == null ? "" : (String) bomObjs[1];
			String hsUnitName = bomObjs[2] == null ? "" : (String) bomObjs[2];
			Date beginDate = (Date) bomObjs[3];
//			String contractId = (String) bomObjs[4];

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (exgObjectMap.get(key) == null) {
				exgObjectMap.put(key, bomObjs);
				mapDate.put(key, beginDate);
			} else {
				if (beginDate.after(mapDate.get(key))) {
					exgObjectMap.put(key, bomObjs);
					mapDate.put(key, beginDate);
				}
			}
		}
		/**
		 * 报关成品 名称+"/"+规格+"/"+单位 映射 bom
		 */
		Map<String, List<Object[]>> mapBomObject = new HashMap<String, List<Object[]>>();
		Iterator<Map.Entry<String, Object[]>> iteratorByExg = exgObjectMap
				.entrySet().iterator();
		for (; iteratorByExg.hasNext();) {
			Map.Entry<String, Object[]> e = iteratorByExg.next();
			Object[] exgObjs = e.getValue();
			String key = e.getKey();

			String hsName = exgObjs[0] == null ? "" : (String) exgObjs[0];
			String hsSpec = exgObjs[1] == null ? "" : (String) exgObjs[1];
			String hsUnitName = exgObjs[2] == null ? "" : (String) exgObjs[2];
//			Date beginDate = (Date) exgObjs[3];
			String contractId = (String) exgObjs[4];
			List lsBom = this.casLeftoverMaterielDao.findDzscEmsBomBill(hsName,
					hsSpec, hsUnitName, contractId);
			if (lsBom.size() == 0) {
				System.out.println("成品" + key + "没有找到bom");
			}
			mapBomObject.put(key, lsBom);
		}

		Map<String, Double> sendMapAmount = parameterMaps.mapSendAmount;

		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011 已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012 已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List list = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String key = hsName + "/" + hsSpec + "/" + unitName;
			String typeCode = (String) objs[4] == null ? "" : (String) objs[4];
			List<Object[]> tempObjes = mapBomObject.get(key);
			if (tempObjes == null) {
				logger.info("单据------短溢表计算 Dzsc Bom 不存在  !! == " + key);
				continue;
			}
			for (Object[] bomObjs : tempObjes) {
				// 料件
				String hsName1 = bomObjs[0] == null ? "" : (String) bomObjs[0];
				String hsSpec1 = bomObjs[1] == null ? "" : (String) bomObjs[1];
				String hsUnitName = bomObjs[2] == null ? ""
						: (String) bomObjs[2];

				// key=名称+"/"+规格+"/"+单位
				String keyMaterial = hsName1 + "/" + hsSpec1 + "/" + hsUnitName;

				// 损耗
				Double waste = bomObjs[3] == null ? 0.0 : (Double) bomObjs[3];
				waste = waste * 0.01;

				// 单耗
				Double unitWear = bomObjs[4] == null ? 0.0
						: (Double) bomObjs[4];

				// 单项用量 = 单耗 / (1-损耗);
				Double unitUsed = unitWear / (1 - waste);

				// 成品单耗损
				Double productMaterialWaste = hsAmount * unitUsed;
				if (typeCode.equals("2009") // 2009结转成品退货单
						|| typeCode.equals("2012")) {// 2012已交货未结转期初单

					productMaterialWaste = -productMaterialWaste;
				} else if (typeCode.equals("2102") // 2102结转出口
						|| typeCode.equals("2011")) {// 2011已结转未交货期初单

					productMaterialWaste = +productMaterialWaste;
				}
				if (sendMapAmount.get(keyMaterial) == null) {
					sendMapAmount.put(keyMaterial, productMaterialWaste);
				} else {
					sendMapAmount.put(keyMaterial, sendMapAmount
							.get(keyMaterial)
							+ productMaterialWaste);
				}
			}
		}
	}

	/**
	 * 初始化料件收获 Map缓存
	 * 
	 * @param endDate
	 * @param parameterMaps
	 *            wss2010.11.17整理
	 */
	private void initMapTransFactInBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> hsNameMap = parameterMaps.resultHsNameSpecUnitF6F9Map;
		Map<String, Double> beforeResultF6Map = parameterMaps.beforeResultF6Map;

		List<Object> parameters = new ArrayList<Object>();
		String selects = " select sum(a.commAmount),a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name "
				+ ",a.baseCustomsDeclaration.customer.name "
				+ " from CustomsDeclarationCommInfo a "
				+ " left join a.unit "
				+ " where a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ? "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by a.baseCustomsDeclaration.impExpType,a.commName,a.commSpec,a.unit.name,a.baseCustomsDeclaration.customer.name  ";

		parameters.add(Boolean.valueOf(true));// 已生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT); // 料件转厂
		parameters.add(CommonUtils.getCompany().getId());
		long beginTime = System.currentTimeMillis();
		List listCustoms = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子帐册] 料件转厂报关单 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0, size = listCustoms.size(); i < size; i++) {
			Object[] objs = (Object[]) listCustoms.get(i);
			Double tempHsAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 报关Materiel数量
//			Integer typeCode = (Integer) objs[1]; // 类型代码
			String hsName = objs[2] == null ? "" : (String) objs[2];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[4] == null ? "" : (String) objs[4];
			String hsComster = objs[5] == null ? "" : (String) objs[5];

			// key=客户+"/"+名称+"/"+规格+"/"+单位
			String key = hsComster + "/" + hsName + "/" + hsSpec + "/"
					+ hsUnitName;
			String tempKey = hsName + "/" + hsSpec + "/" + hsUnitName;

			hsNameMap.put(key, tempKey);
			beforeResultF6Map.put(key, tempHsAmount);
		}
	}

	/**
	 * 如果选有多本手册类型，成品折BOM时应该按照报关常用工厂BOM计算
	 * 
	 * @param parameterMaps
	 *            wss2010.11.17整理
	 */
	private void initMapSendAmount(ParameterMaps parameterMaps) {

		/**
		 * 外发数量（成品折耗）
		 */
		Map<String, Double> mapSendAmount = parameterMaps.mapSendAmount;

		/**
		 * 结转成品 + 2102 结转出口 - 2011已交货未结转期初单 - 2009 结转成品退货单 + 2012已结转未交货期初单
		 */
		Map<String, Double> mapBillDetailPtAmountCpTran = parameterMaps.mapBillDetailPtAmountCpTran;

		/**
		 * key为：料号 value为：报关名称+报关规格+报关单位（最后一个对应关系的）
		 */
		Map<String, String> mapPtNoKey = parameterMaps.mapPtNoKey;

		Map<String, FactoryAndFactualCustomsRalation> mapPtNoFFC = parameterMaps.mapPtNoFFC;

		Map<String, Double> tempbillDetailCpTranMap = new HashMap<String, Double>();

		Iterator<Map.Entry<String, Double>> iterator = mapBillDetailPtAmountCpTran
				.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Double> e = iterator.next();
			String ptNoKey = e.getKey();

			String key = mapPtNoKey.get(ptNoKey);
			if (key == null) {
				continue;
			}
			FactoryAndFactualCustomsRalation ffc = mapPtNoFFC.get(ptNoKey);
			Double unitConvert = (ffc == null || ffc.getUnitConvert() == null) ? 1.0
					: ffc.getUnitConvert();// 折算系数

			Double value = e.getValue() == null ? 0.0 : e.getValue();
			value = value * unitConvert;
			Double tempValue = tempbillDetailCpTranMap.get(key);
			if (tempValue == null) {
				tempbillDetailCpTranMap.put(key, value);
			} else {
				tempbillDetailCpTranMap.put(key, tempValue + value);
			}
		}
		Iterator<Map.Entry<String, Double>> iterator2 = tempbillDetailCpTranMap
				.entrySet().iterator();
		while (iterator2.hasNext()) {
			Map.Entry<String, Double> e = iterator2.next();
			String key = e.getKey();
			Double hsAmount = e.getValue() == null ? 0.0 : e.getValue();
			double tempAmount = hsAmount;
			if (tempAmount > 0) {
				if (mapSendAmount.get(key) == null) {
					mapSendAmount.put(key, tempAmount);
				}
			} else {
				if (mapSendAmount.get(key) == null) {
					mapSendAmount.put(key, tempAmount);
				}
			}
		}
	}

	/**
	 * 成品单据折料数 转化 为报关数量
	 * 
	 * @param key取得唯一键值
	 * @return 成品、半成品单据 折损耗报关数量 map wss2010.11.18整理
	 */
	private Map<String, Double> getMapHsAmountUsedByProduct(String key,
			ParameterMaps parameterMaps) {

		Map<String, Double> mapResult = new HashMap<String, Double>();
		try {

			/**
			 * key :【报关名称+"/"+规格+"/"+单位名称】 value：【对应的对应关系list】
			 */
			List<FactoryAndFactualCustomsRalation> lsFFC = parameterMaps.mapKeyFFCList
					.get(key);
			if (lsFFC == null) {
				logger.info("对应关系中没有这样的报关名称 == " + key);
				mapResult.put("F2", 0.0);// 在产品折料数
				mapResult.put("F3", 0.0);// 产成品折料数
				mapResult.put("F4", 0.0);// 外发折料数
				return mapResult;
			}

			// 用于过滤已经算过了的，或者料号为空的
			Map<String, String> mapFilter = new HashMap<String, String>();

			for (int i = 0, n = lsFFC.size(); i < n; i++) {
				FactoryAndFactualCustomsRalation mt = lsFFC.get(i);
				String ptNoKey = mt.getMateriel().getPtNo(); // 料件料号
				Double unitConvert = mt.getUnitConvert() == null ? 1.0 : mt
						.getUnitConvert(); // 折算系数

				// 进行过滤
				if (ptNoKey == null || "".equals(ptNoKey) || mapFilter.containsKey(ptNoKey)) {
					continue;
				} else {
					mapFilter.put(ptNoKey, ptNoKey);
				}

				// 在产品折料 报关数量
				Double ptAmount2002 = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmount2002
								.get(ptNoKey));//车间入库
				
				Double ptAmount2103 = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmount2103
								.get(ptNoKey));//返回车间

				if (!mapResult.containsKey("F2")) {
					mapResult.put("F2",
							((ptAmount2103 - ptAmount2002) * unitConvert));
				} else {
					mapResult.put("F2", mapResult.get("F2")
							+ ((ptAmount2103 - ptAmount2002) * unitConvert));
				}

				// 产成品折料 报关数量
				Double f26PtAmount = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmountF26
								.get(ptNoKey));
				if (!mapResult.containsKey("F3")) {

					mapResult.put("F3", (f26PtAmount * unitConvert));
				} else {
					mapResult.put("F3", mapResult.get("F3")
							+ (f26PtAmount * unitConvert));
				}

				// 外发加工折料 报关数量
				Double ptAmount2015 = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmount2015
								.get(ptNoKey));
				Double ptAmount2017 = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmount2017
								.get(ptNoKey));
				Double ptAmount2113 = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmount2113
								.get(ptNoKey));
				Double ptAmount2114 = CommonUtils
						.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmount2114
								.get(ptNoKey));
				Double totalAmount = ptAmount2113 + ptAmount2114 - ptAmount2015
						- ptAmount2017;
				if (!mapResult.containsKey("F4")) {
					mapResult.put("F4", totalAmount * unitConvert);
				} else {
					mapResult.put("F4", mapResult.get("F4") + totalAmount
							* unitConvert);
				}
				
				
				parameterMaps.mapBillDetailPtAmount2002.remove(ptNoKey);
				parameterMaps.mapBillDetailPtAmount2103.remove(ptNoKey);
				parameterMaps.mapBillDetailPtAmountF26.remove(ptNoKey);
				parameterMaps.mapBillDetailPtAmount2017.remove(ptNoKey);
				parameterMaps.mapBillDetailPtAmount2113.remove(ptNoKey);
				parameterMaps.mapBillDetailPtAmount2114.remove(ptNoKey);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapResult;
	}

	/**
	 * 半成品单据折料数 转化 为报关数量
	 * 
	 * @param key取得唯一键值
	 * @return 成品、半成品单据 折损耗报关数量 map wss2010.11.18整理
	 */
	private Map<String, Double> getMapHsAmountUsedByHalf(String key,
			ParameterMaps parameterMaps) {

		Map<String, Double> mapResult = new HashMap<String, Double>();

		List<FactoryAndFactualCustomsRalation> mapFFC = parameterMaps.mapKeyFFCList
				.get(key);

		if (mapFFC == null) {
			logger.info("对应关系没有这样的报关名称 == " + key);
			mapResult.put("F2", 0.0);
			mapResult.put("F4", 0.0);
			return mapResult;
		}

		// 用于过滤已经算过了的，或者料号为空的
		Map<String, String> mapFilter = new HashMap<String, String>();

		for (int i = 0, n = mapFFC.size(); i < n; i++) {
			FactoryAndFactualCustomsRalation ffc = mapFFC.get(i);

			String ptNoKey = ffc.getMateriel().getPtNo(); // 料件料号
			Double unitConvert = ffc.getUnitConvert() == null ? 1.0 : ffc
					.getUnitConvert(); // 折算系数

			// 进行过滤
			if (mapFilter.containsKey(ptNoKey) || "".equals(ptNoKey)) {
				continue;
			} else {
				mapFilter.put(ptNoKey, ptNoKey);
			}

			// 在产品耗用 折算报关数量
			Double halfPtAmount = CommonUtils
					.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmountHalf
							.get(ptNoKey));
			if (!mapResult.containsKey("F2")) {

				mapResult.put("F2", (halfPtAmount * unitConvert));
			} else {
				mapResult.put("F2", mapResult.get("F2")
						+ (halfPtAmount * unitConvert));
			}

			Double halfOutSourcePtAmount = CommonUtils
					.getDoubleExceptNull(parameterMaps.mapBillDetailPtAmountHalfOutSource
							.get(ptNoKey));

			if (!mapResult.containsKey("F4")) {
				mapResult.put("F4", (halfOutSourcePtAmount * unitConvert));
			} else {
				mapResult.put("F4", mapResult.get("F4")
						+ (halfOutSourcePtAmount * unitConvert));
			}
		}
		return mapResult;
	}

	/**
	 * 查找送货转厂耗料情况
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantBcs(BalanceInfo balanceInfo, Date beginDate,
			Date endDate, String taskId) {
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);
//		ParameterMaps parameterMaps = new ParameterMaps();
		List<Object> parameters = new ArrayList<Object>();
		String myKey = balanceInfo.getKey();
		List resultList = new ArrayList();
		Hashtable<String, TempDeliveryToPlant> diffirentMap = new Hashtable<String, TempDeliveryToPlant>();
		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();
		Map<String, Double> beforResult7Map = new HashMap<String, Double>();
		Map<String, Double> beforResult8Map = new HashMap<String, Double>();
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> nameMap = new HashMap<String, String>();
		Map<String, Double> returnDeclareMap = new HashMap<String, Double>();
		Map<String, Double> returnBillMap = new HashMap<String, Double>();
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,a.billMaster.scmCoc.name  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List sendBillList = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);

		for (int i = 0; i < sendBillList.size(); i++) {
			Object[] objs = (Object[]) sendBillList.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String typeCode = (String) objs[5] == null ? "" : (String) objs[5];
			// key=客户名称+报关名称+报关规格+单位
			String key = scmCoc + "/@/" + hsName + "/@/" + hsSpec + "/@/"
					+ unitName;
			// 不包含客户名称
			String tempName = hsName + "/@/" + hsSpec + "/@/" + unitName;
			nameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount -= hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount += hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
			returnBillMap.put(key, mapHsAmount);
		}

		// 2.获取成品报关单map
		selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name"
				+ " from BcsCustomsDeclarationCommInfo a, ContractExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and exg.contract.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name ";
		parameters.add(DeclareState.PROCESS_EXE);// 正在运行合同
		parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		List customsList = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子化手册] 转厂报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < customsList.size(); i++) {
			Object[] objs = (Object[]) customsList.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String key = scmCoc + "/@/" + hsName + "/@/" + hsSpec + "/@/"
					+ unitName;
			// 不包含客户名称
			String tempName = hsName + "/@/" + hsSpec + "/@/" + unitName;
			// + "/"+ complex;
			nameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
			returnDeclareMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String scKey = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(scKey);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(scKey);
				removeList.add(scKey);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult7Map.put(scKey, result);
			} else if (result < (-0.0001)) {
				beforResult8Map.put(scKey, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();
		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String scKey = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(scKey);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(scKey, result);
			} else if (result < (-0.0001)) {
				beforResult7Map.put(scKey, -result);
			}
		}

		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		selects = "select a.contractExg.name,a.contractExg.spec,a.contractExg.unit.name"
				+ " ,a.contractExg.contract.impContractNo,a.name||'/'||a.spec||'/'||a.unit.name,a.unitWaste,"
				+ " a.waste,a.unitDosage,a.contractExg.seqNum"
				+ " from ContractBom a "
				+ " where a.contractExg.contract.declareState= ? and (a.name||'/'||a.spec||'/'||a.unit.name=?)"
				+ "  order by a.contractExg.contract.impContractNo,a.contractExg.seqNum";
		parameters.clear();
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(myKey);
		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMap = new HashMap<String, BillTemp>();

		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String impContractNo = objs[3] == null ? "" : (String) objs[3];
			String bomKey = objs[4] == null ? "" : (String) objs[4];
			Double unitWaste = objs[5] == null ? 0.0 : (Double) objs[5];
			Double waste = objs[6] == null ? 0.0 : (Double) objs[6];
			Double unitDosage = objs[7] == null ? 0.0 : (Double) objs[7];
			String key = name + "/@/" + spec + "/@/" + unitName + "/@/"
					+ impContractNo;

			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitWaste);
			b.setBillSum2(waste);
			b.setBillSum3(unitDosage);
			bomMap.put(key, b);

		}
		TempDeliveryToPlant temp = null;
		for (Entry entry : beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = nameMap.get(scKey);
			String nameSepcUnitKeyRep = nameSepcUnitKey.replaceAll("/@/", "/");
			// 合同最大的合同号
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromBcs(
					nameSepcUnitKeyRep);
			String andKey = nameSepcUnitKey + ("/@/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			Double bomValue = 0.0;
			if (billTemp != null) {
				Double unitWaste = billTemp.getBillSum1();
				Double waste = billTemp.getBillSum2();
				Double unitDosage = billTemp.getBillSum3();
				bomValue += value * unitDosage;
				if (diffirentMap.get(scKey) != null) {
					temp = diffirentMap.get(scKey);
				} else {
					temp = new TempDeliveryToPlant();
					if (scKey.split("/@/").length >= 1) {
						temp.setCustomer(scKey.split("/@/")[0]);
					}
					if (scKey.split("/@/").length >= 2) {
						temp.setPtName(scKey.split("/@/")[1]);
					}
					if (scKey.split("/@/").length >= 3) {
						temp.setPtSpec(scKey.split("/@/")[2]);
					}
					if (scKey.split("/@/").length >= 4) {
						temp.setPtUnitName(scKey.split("/@/")[3]);
					}
				}
				temp.setContractConsumption(unitWaste);
				temp.setContracLoss(waste);
				temp.setCarryOverF7Difference(bomValue);
				temp.setConrtact(maxImpContractNo);
				diffirentMap.put(scKey, temp);
			}

		}

		for (Entry entry : beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = nameMap.get(scKey);
			String nameSepcUnitKeyRep = nameSepcUnitKey.replaceAll("/@/", "/");
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromBcs(
					nameSepcUnitKeyRep);
			String andKey = nameSepcUnitKey + ("/@/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			Double bomValue = 0.0;
			if (billTemp != null) {
				Double unitWaste = billTemp.getBillSum1();
				Double waste = billTemp.getBillSum2();
				Double unitDosage = billTemp.getBillSum3();
				bomValue += value * unitDosage;
				if (diffirentMap.get(scKey) != null) {
					temp = diffirentMap.get(scKey);
				} else {
					temp = new TempDeliveryToPlant();
					if (scKey.split("/@/").length >= 1) {
						temp.setCustomer(scKey.split("/@/")[0]);
					}
					if (scKey.split("/@/").length >= 2) {
						temp.setPtName(scKey.split("/@/")[1]);
					}
					if (scKey.split("/@/").length >= 3) {
						temp.setPtSpec(scKey.split("/@/")[2]);
					}
					if (scKey.split("/@/").length >= 4) {
						temp.setPtUnitName(scKey.split("/@/")[3]);
					}
				}
				temp.setContractConsumption(unitWaste);
				temp.setContracLoss(waste);
				temp.setConrtact(maxImpContractNo);
				temp.setCarryOverF8Difference(bomValue);
				diffirentMap.put(scKey, temp);
			}

		}
		for (Entry entry : diffirentMap.entrySet()) {
			String scKey = (String) entry.getKey();
			temp = (TempDeliveryToPlant) entry.getValue();
			temp.setCarriedOverExportBillsAmount(returnBillMap.get(scKey));
			temp.setCarryOverExportDeclarationsAmount(returnDeclareMap
					.get(scKey));
			resultList.add(temp);
		}

		return resultList;
	}

	/**
	 * 查找送货转厂耗料情况
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantDzsc(BalanceInfo balanceInfo,
			Date beginDate, Date endDate, String taskId) {
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);
//		ParameterMaps parameterMaps = new ParameterMaps();
		List<Object> parameters = new ArrayList<Object>();
		Hashtable<String, TempDeliveryToPlant> diffirentMap = new Hashtable<String, TempDeliveryToPlant>();
		String myKey = balanceInfo.getKey();
		List resultList = new ArrayList();
		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();
		Map<String, Double> beforResult7Map = new HashMap<String, Double>();
		Map<String, Double> beforResult8Map = new HashMap<String, Double>();
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> nameMap = new HashMap<String, String>();
		Map<String, Double> returnDeclareMap = new HashMap<String, Double>();
		Map<String, Double> returnBillMap = new HashMap<String, Double>();
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计

		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,a.billMaster.billType.code  ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,a.billMaster.scmCoc.name  ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List listBill = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);
		System.out.println("====selects==" + selects);
		System.out.println("====listBill==" + listBill.size());
		for (int i = 0; i < listBill.size(); i++) {
			Object[] objs = (Object[]) listBill.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String scmCoc = objs[4] == null ? "" : (String) objs[4];
			String typeCode = objs[5] == null ? "" : (String) objs[5];
			String key = scmCoc + "/@/" + hsName + "/@/" + hsSpec + "/@/"
					+ unitName;
			// 不包含客户名称
			String tempName = hsName + "/@/" + hsSpec + "/@/" + unitName;
			nameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount -= hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount += hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
			returnBillMap.put(key, mapHsAmount);
		}

		// 2.获取成品报关单map
		selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name"
				+ " from DzscCustomsDeclarationCommInfo a, DzscEmsExgBill exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
				+ " and exg.dzscEmsPorHead.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name ";
		parameters.add(DzscState.EXECUTE);// 正在运行合同
		parameters.add(DzscState.CHECK_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());
		long beginTime = System.currentTimeMillis();
		List listCustom = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子手册] 转厂报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < listCustom.size(); i++) {
			Object[] objs = (Object[]) listCustom.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = (Double) objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = (String) objs[1] == null ? "" : (String) objs[1];
			String hsSpec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String scmCoc = (String) objs[4] == null ? "" : (String) objs[4];
			String key = scmCoc + "/@/" + hsName + "/@/" + hsSpec + "/@/"
					+ unitName;
			// 不包含客户名称
			String tempName = hsName + "/@/" + hsSpec + "/@/" + unitName;
			nameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
			returnDeclareMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(key);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(key);
				removeList.add(key);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			System.out.println("===customsresult==" + key + " ==" + result);
			if (result > 0.0001) {
				beforResult7Map.put(key, result);
			} else if (result < (-0.0001)) {
				beforResult8Map.put(key, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();

		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String key = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(key);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			System.out.println("===sendBillresult==" + key + " ==" + result);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(key, result);
			} else if (result < (-0.0001)) {
				beforResult7Map.put(key, -result);
			}
		}

		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		selects = "select a.dzscEmsExgBill.name,a.dzscEmsExgBill.spec,a.dzscEmsExgBill.unit.name"
				+ " ,a.dzscEmsExgBill.dzscEmsPorHead.ieContractNo,a.name||'/'||a.spec||'/'||a.unit.name,"
				+ " a.unitWare,a.ware,a.unitDosage,a.dzscEmsExgBill.seqNum"
				+ " from DzscEmsBomBill a "
				+ "where a.dzscEmsExgBill.dzscEmsPorHead.declareState= ? and (a.name||'/'||a.spec||'/'||a.unit.name=?)"
				+ "  order by a.dzscEmsExgBill.dzscEmsPorHead.ieContractNo,a.dzscEmsExgBill.seqNum";
		parameters.clear();
		parameters.add(DzscState.EXECUTE);
		parameters.add(myKey);

		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMap = new HashMap<String, BillTemp>();
		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String impContractNo = objs[3] == null ? "" : (String) objs[3];
			String bomKey = objs[4] == null ? "" : (String) objs[4];
			Double unitWare = objs[5] == null ? 0.0 : (Double) objs[5];
			Double ware = objs[6] == null ? 0.0 : (Double) objs[6];
			Double unitDosage = objs[7] == null ? 0.0 : (Double) objs[7];
			String key = name + "/@/" + spec + "/@/" + unitName + "/@/"
					+ impContractNo;
			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitWare);
			b.setBillSum2(ware);
			b.setBillSum3(unitDosage);
			bomMap.put(key, b);

		}
		TempDeliveryToPlant temp = null;
		for (Entry entry : beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = nameMap.get(scKey);
			String nameSepcUnitKeyRep = nameSepcUnitKey.replaceAll("/@/", "/");
			// 合同最大的合同号
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromDzsc(
					nameSepcUnitKeyRep, myKey);
			String andKey = nameSepcUnitKey + ("/@/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitWare = billTemp.getBillSum1();
				Double ware = billTemp.getBillSum2();
				Double unitDosage = billTemp.getBillSum3();
				bomValue += value * unitDosage;
				if (diffirentMap.get(scKey) != null) {
					temp = diffirentMap.get(scKey);
				} else {
					temp = new TempDeliveryToPlant();
					if (scKey.split("/@/").length >= 1) {
						temp.setCustomer(scKey.split("/@/")[0]);
					}
					if (scKey.split("/@/").length >= 2) {
						temp.setPtName(scKey.split("/@/")[1]);
					}
					if (scKey.split("/@/").length >= 3) {
						temp.setPtSpec(scKey.split("/@/")[2]);
					}
					if (scKey.split("/@/").length >= 4) {
						temp.setPtUnitName(scKey.split("/@/")[3]);
					}
				}
				temp.setContractConsumption(unitWare);
				temp.setContracLoss(ware);
				temp.setConrtact(maxImpContractNo);
				temp.setCarryOverF8Difference(bomValue);
				diffirentMap.put(scKey, temp);
			}

		}

		for (Entry entry : beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = nameMap.get(scKey);
			String nameSepcUnitKeyRep = nameSepcUnitKey.replaceAll("/@/", "/");
			String maxImpContractNo = this.casDao.findMaxImpContractNoFromDzsc(
					nameSepcUnitKeyRep, myKey);
			String andKey = nameSepcUnitKey + ("/@/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			if (billTemp != null) {
				Double bomValue = 0.0;
				Double unitWare = billTemp.getBillSum1();
				Double ware = billTemp.getBillSum2();
				Double unitDosage = billTemp.getBillSum3();
				bomValue += value * unitDosage;
				if (diffirentMap.get(scKey) != null) {
					temp = diffirentMap.get(scKey);
				} else {
					temp = new TempDeliveryToPlant();
					if (scKey.split("/@/").length >= 1) {
						temp.setCustomer(scKey.split("/@/")[0]);
					}
					if (scKey.split("/@/").length >= 2) {
						temp.setPtName(scKey.split("/@/")[1]);
					}
					if (scKey.split("/@/").length >= 3) {
						temp.setPtSpec(scKey.split("/@/")[2]);
					}
					if (scKey.split("/@/").length >= 4) {
						temp.setPtUnitName(scKey.split("/@/")[3]);
					}
				}
				temp.setContractConsumption(unitWare);
				temp.setContracLoss(ware);
				temp.setConrtact(maxImpContractNo);
				temp.setCarryOverF8Difference(bomValue);
				diffirentMap.put(scKey, temp);
			}

		}
		for (Entry entry : diffirentMap.entrySet()) {
			String scKey = (String) entry.getKey();
			temp = (TempDeliveryToPlant) entry.getValue();
			temp.setCarriedOverExportBillsAmount(returnBillMap.get(scKey));
			temp.setCarryOverExportDeclarationsAmount(returnDeclareMap
					.get(scKey));
			resultList.add(temp);
		}
		return resultList;
	}

	/**
	 * 查找BCUS送货转厂耗料情况
	 * 
	 * @param request
	 * @param balanceInfo
	 * @return
	 */
	public List findDeliveryToPlantBcus(BalanceInfo balanceInfo,
			Date beginDate, Date endDate, String taskId) {
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);
//		ParameterMaps parameterMaps = new ParameterMaps();
		List<Object> parameters = new ArrayList<Object>();
		String myKey = balanceInfo.getKey();
		List resultList = new ArrayList();
		Hashtable<String, TempDeliveryToPlant> diffirentMap = new Hashtable<String, TempDeliveryToPlant>();
		// 单据Map
		Map<String, Double> sendBillMap = new HashMap<String, Double>();
		// 报关单Map
		Map<String, Double> customsBillMap = new HashMap<String, Double>();
		Map<String, Double> beforResult7Map = new HashMap<String, Double>();
		Map<String, Double> beforResult8Map = new HashMap<String, Double>();
		// 用来存储成品 key（除去客户名称）map(key,key-客户名称)
		Map<String, String> nameMap = new HashMap<String, String>();
		Map<String, Double> returnDeclareMap = new HashMap<String, Double>();
		Map<String, Double> returnBillMap = new HashMap<String, Double>();
		// 1.获取成品单据map（key，Aamout）
		// 以名称+规格+单位+编码,客户来分组进行统计
		String selects = "select sum(a.hsAmount),a.hsName,a.hsSpec,a.hsUnit.name,a.billMaster.scmCoc.name,"
				+ " a.billMaster.billType.code ,a.version ";
		String groupBy = "group by a.hsName,a.hsSpec, a.hsUnit.name,a.billMaster.billType.code,"
				+ " a.billMaster.scmCoc.name ,a.version ";
		String leftOuter = " left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2009", null)); // 2009 结转成品退货单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2102", null)); // 2102 结转出口
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2011", null)); // 2011已交货未结转期初单
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2012", ")")); // 2012已结转未交货期初单
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", CommonUtils.getBeginDate(Integer
						.valueOf(getYear(endDate)), 0, 1), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));
		List list = casDao.commonSearch(selects, "BillDetailProduct",
				conditions, groupBy, leftOuter);

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String scmCoc = objs[4] == null ? "" : (String) objs[4];
			String typeCode = objs[5] == null ? "" : (String) objs[5];
			String version = objs[6] == null ? "" : String.valueOf(objs[6]);

			String key = scmCoc + "/@/" + hsName + "/@/" + hsSpec + "/@/"
					+ unitName + "/@/" + version;

			// 不包含客户名称
			String tempName = hsName + "/@/" + hsSpec + "/@/" + unitName
					+ "/@/" + version;
			nameMap.put(key, tempName);
			if (sendBillMap.get(key) != null)
				mapHsAmount = sendBillMap.get(key);

			if (typeCode.equals("2009") // 2009结转成品退货单
					|| typeCode.equals("2012")) {// 2012已结转未交货期初单

				mapHsAmount = -hsAmount;
			} else if (typeCode.equals("2102") // 2102结转出口
					|| typeCode.equals("2011")) {// 2011已交货未结转期初单

				mapHsAmount = +hsAmount;
			}
			sendBillMap.put(key, mapHsAmount);
			returnBillMap.put(key, mapHsAmount);
		}

		selects = " select sum(a.commAmount),exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,a.version"
				+ " from CustomsDeclarationCommInfo a, EmsHeadH2kExg exg "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.emsHeadH2k.emsNo "
				+ " and exg.emsHeadH2k.declareState in(?,?) "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType = ?  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " group by exg.name,exg.spec,exg.unit.name,"
				+ " a.baseCustomsDeclaration.customer.name,a.version ";
		parameters.add(DeclareState.PROCESS_EXE);// 正在运行合同
		parameters.add(DeclareState.CHANGING_CANCEL);// 已经核销合同
		parameters.add(Boolean.valueOf(true));// 生效
		parameters.add(CommonUtils.getBeginDate(Integer
				.valueOf(getYear(endDate)), 0, 1));// 开始日期
		parameters.add(CommonUtils.getEndDate(endDate));// 结束日期
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);

		parameters.add(CommonUtils.getCompany().getId());

		long beginTime = System.currentTimeMillis();
		list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "短溢表计算 开始查询所有 [电子帐册] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double mapHsAmount = 0.0;
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			String hsName = objs[1] == null ? "" : (String) objs[1];
			String hsSpec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String scmCoc = objs[4] == null ? "" : (String) objs[4];
			String version = objs[5] == null ? "" : String.valueOf(objs[5]);
			String key = scmCoc + "/@/" + hsName + "/@/" + hsSpec + "/@/"
					+ unitName + "/@/" + version;
			// 不包含客户名称
			String tempName = hsName + "/@/" + hsSpec + "/@/" + unitName
					+ "/@/" + version;
			nameMap.put(key, tempName);
			if (customsBillMap.get(key) != null)
				mapHsAmount = customsBillMap.get(key);
			mapHsAmount = +hsAmount;
			customsBillMap.put(key, mapHsAmount);
			returnDeclareMap.put(key, mapHsAmount);
		}

		// 3.单据数量 - 报关数量 放进MAP
		List<String> removeList = new ArrayList<String>();
		for (Entry<String, Double> entry : customsBillMap.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			Double sendValue = sendBillMap.get(key);
			Double result = 0.0;

			if (sendValue != null) {
				result = sendValue - value;
				sendBillMap.remove(key);
				removeList.add(key);
			} else {
				result = -value;
			}
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult7Map.put(key, result);
			} else if (result < (-0.0001)) {

				beforResult8Map.put(key, -result);
			}
		}
		// 移除已经计算过的
		for (int i = 0; i < removeList.size(); i++) {
			customsBillMap.remove(removeList.get(i));
		}
		removeList.clear();

		for (Entry<String, Double> entry : sendBillMap.entrySet()) {
			String key = entry.getKey();
			Double sendValue = entry.getValue();
			Double value = customsBillMap.get(key);
			Double result = 0.0;
			if (value != null) {
				result = sendValue - value;
			} else {
				result = sendValue;
			}
			result *= (-1);
			// 0.0001为误差允许范围
			if (result > 0.0001) {
				beforResult8Map.put(key, result);
			} else if (result < (-0.0001)) {
				beforResult7Map.put(key, -result);
			}
		}

		// 5.用Map缓存单耗
		// 由于按升序排序，所以存入map时如果有相同的最后大的会把前面的给覆盖，
		// 因此达到了当成品表中有相同的名称 +规格+单位+手册号时最料件序号最大的对应的单耗号
		selects = "select a.emsHeadH2kVersion.emsHeadH2kExg.name,a.emsHeadH2kVersion.emsHeadH2kExg.spec,"
				+ " a.emsHeadH2kVersion.emsHeadH2kExg.unit.name"
				+ " ,a.emsHeadH2kVersion.version,a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.emsNo, "
				+ " a.name||'/'||a.spec||'/'||a.unit.name,a.unitWear,a.wear,a.emsHeadH2kVersion.emsHeadH2kExg.seqNum"
				+ " from EmsHeadH2kBom a "
				+ "  where (a.name||'/'||a.spec||'/'||a.unit.name=?)"
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState= ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=?"
				+ " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum ";
		parameters.clear();
		parameters.add(myKey);
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(new Boolean(false));

		List bomExglist = casDao.commonSearch(selects, parameters.toArray());
		Map<String, BillTemp> bomMap = new HashMap<String, BillTemp>();

		for (int i = 0; i < bomExglist.size(); i++) {
			Object[] objs = (Object[]) bomExglist.get(i);
			String name = objs[0] == null ? "" : (String) objs[0];
			String spec = objs[1] == null ? "" : (String) objs[1];
			String unitName = objs[2] == null ? "" : (String) objs[2];
			String version = objs[3] == null ? "" : (String) objs[3];
			String impContractNo = objs[4] == null ? "" : (String) objs[4];
			String bomKey = objs[5] == null ? "" : (String) objs[5];
			Double unitWear = objs[6] == null ? 0.0 : (Double) objs[6];// 单耗
			Double wear = objs[7] == null ? 0.0 : (Double) objs[7];// 损耗
			String key = name + "/@/" + spec + "/@/" + unitName + "/@/"
					+ version + "/@/" + impContractNo;

			BillTemp b = new BillTemp();
			b.setBill1(bomKey);
			b.setBillSum1(unitWear);
			b.setBillSum2(wear);
			bomMap.put(key, b);

		}
		// 合同号最大的手册
		selects = "select a.emsNo from  EmsHeadH2k a  where  a.historyState=? order by a.emsNo desc";
		parameters.clear();
		parameters.add(new Boolean(false));
		List listEmsHeadH2k = casDao
				.commonSearch(selects, parameters.toArray());
		String maxImpContractNo = "";
		if (listEmsHeadH2k != null && listEmsHeadH2k.size() != 0)
			maxImpContractNo = (String) listEmsHeadH2k.get(0);

		TempDeliveryToPlant temp = null;
		for (Entry entry : beforResult7Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = nameMap.get(scKey);
//			String nameSepcUnitKeyRep = nameSepcUnitKey.replaceAll("/@/", "/");
			String andKey = nameSepcUnitKey + ("/@/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			Double bomValue = 0.0;
			if (billTemp != null) {
				Double unitWaste = billTemp.getBillSum1();
				Double waste = billTemp.getBillSum2();
				Double unitDosage = billTemp.getBillSum3();
				bomValue += value * unitDosage;
				if (diffirentMap.get(scKey) != null) {
					temp = diffirentMap.get(scKey);
				} else {
					temp = new TempDeliveryToPlant();
					if (scKey.split("/@/").length >= 1) {
						temp.setCustomer(scKey.split("/@/")[0]);
					}
					if (scKey.split("/@/").length >= 2) {
						temp.setPtName(scKey.split("/@/")[1]);
					}
					if (scKey.split("/@/").length >= 3) {
						temp.setPtSpec(scKey.split("/@/")[2]);
					}
					if (scKey.split("/@/").length >= 4) {
						temp.setPtUnitName(scKey.split("/@/")[3]);
					}
				}
				temp.setContractConsumption(unitWaste);
				temp.setContracLoss(waste);
				temp.setCarryOverF7Difference(bomValue);
				temp.setConrtact(maxImpContractNo);
				diffirentMap.put(scKey, temp);
			}

		}

		for (Entry entry : beforResult8Map.entrySet()) {
			String scKey = (String) entry.getKey();
			Double value = (Double) entry.getValue();
			String nameSepcUnitKey = nameMap.get(scKey);
//			String nameSepcUnitKeyRep = nameSepcUnitKey.replaceAll("/@/", "/");
			String andKey = nameSepcUnitKey + ("/@/" + maxImpContractNo);
			BillTemp billTemp = bomMap.get(andKey);
			Double bomValue = 0.0;
			if (billTemp != null) {
				Double unitWaste = billTemp.getBillSum1();
				Double waste = billTemp.getBillSum2();
				Double unitDosage = billTemp.getBillSum3();
				bomValue += value * unitDosage;
				if (diffirentMap.get(scKey) != null) {
					temp = diffirentMap.get(scKey);
				} else {
					temp = new TempDeliveryToPlant();
					if (scKey.split("/@/").length >= 1) {
						temp.setCustomer(scKey.split("/@/")[0]);
					}
					if (scKey.split("/@/").length >= 2) {
						temp.setPtName(scKey.split("/@/")[1]);
					}
					if (scKey.split("/@/").length >= 3) {
						temp.setPtSpec(scKey.split("/@/")[2]);
					}
					if (scKey.split("/@/").length >= 4) {
						temp.setPtUnitName(scKey.split("/@/")[3]);
					}
				}
				temp.setContractConsumption(unitWaste);
				temp.setContracLoss(waste);
				temp.setConrtact(maxImpContractNo);
				temp.setCarryOverF8Difference(bomValue);
				diffirentMap.put(scKey, temp);
			}

		}
		for (Entry entry : diffirentMap.entrySet()) {
			String scKey = (String) entry.getKey();
			temp = (TempDeliveryToPlant) entry.getValue();
			temp.setCarriedOverExportBillsAmount(returnBillMap.get(scKey));
			temp.setCarryOverExportDeclarationsAmount(returnDeclareMap
					.get(scKey));
			resultList.add(temp);
		}

		return resultList;
	}
}
