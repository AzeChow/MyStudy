package com.bestway.customs.common.action;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseExportInvoiceItem;
import com.bestway.customs.common.entity.ImportBGDCondition;

/**
 * 报关单的基础类接口 (2008年10月28日 贺巍添加部分注释)
 * 
 * @author ？
 * 
 */
@SuppressWarnings("unchecked")
public interface BaseEncAction {
	/**
	 * 保存报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	BaseCustomsDeclaration saveCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 检查重复报关单商品序号是否连续
	 */
	boolean checkCustDeclCommInfoSerialNumber(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 检查商品信息中项号所对应的商品的名称、规格与通关手册备案的名称、规格是否一致
	 */
	boolean checkCustDeclCommInfoSeqNumIsCorrespondsNameAndSpec(
			Request request, boolean isMaterial,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 检查商品信息中项号所对应商品的法定单位（包括第二法定单位）法定单位数量是否为0
	 */
	String[] checkCustDeclCommInfoLegalAmountBySeqNumIsZero(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 取得出口报关单
	 * 
	 * @param id
	 *            报关单ID
	 * @return 返回报关单表头（用于出口收汇核销单打印）
	 */
	public BaseCustomsDeclaration findCustomsDeclarationById(Request request,
			String id);

	/**
	 * 取得出口报关单
	 * 
	 * @param customsDeclarationCode
	 *            报关单号 customsDeclarationCode
	 * @param impExpFlag
	 *            进出口标志
	 * @return 返回报关单表头
	 */
	public BaseCustomsDeclaration findCustomsDeclarationByCustomsDeclarationCode(
			Request request, String customsDeclarationCode, boolean impExpFlag);

	/**
	 * 取得报关单明细
	 * 
	 * @param master
	 * @return 返回报关单明细列表（用于出口收汇核销单打印）
	 */
	public List findCustomsDeclarationInfo(Request request,
			BaseCustomsDeclaration master);

	/**
	 * 取得报关单明细
	 * 
	 * @param baseID
	 *            表头ID
	 * @return 返回报关单明细列表
	 */
	public Map<String, List> findCustomsDeclarationInfos(Request request,
			List<String> baseID, Integer projectType);

	/**
	 * 删除报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	void deleteCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 删除报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public void deleteCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration,
			boolean isCustomDelete);

	/**
	 * 删除报关单
	 * 
	 * @param request
	 * @param baseCustomsDeclarations
	 */
	void deleteCustomsDeclaration(Request request, List baseCustomsDeclarations);

	/**
	 * 修改进出货转厂单据
	 */
	void updateTransferFactoryBill(Request request, List baseCustomsDeclarations);

	/**
	 * 查询结转报关单已经与海关单据对应
	 * 
	 * @param request
	 * @param baseCustomsDeclarations
	 * @return
	 */
	List findMakeBillCorrespondingInfoBase(Request request,
			List baseCustomsDeclarations);

	/**
	 * 取得进口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的进口报关单表头
	 */
	List findImportCustomsDeclaration(Request request, String emsNo,
			Date beginDate, Date endDate);

	/**
	 * 取得进口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的进口报关单表头
	 */
	List findImportCustomsDeclaration(Request request, List emsHeads,
			Date beginDate, Date endDate);

	/**
	 * 取得出口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的出口报关单表头
	 */
	List findExportCustomsDeclaration(Request request, String emsNo,
			Date beginDate, Date endDate);

	/**
	 * 取得出口报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的出口报关单表头
	 */
	List findExportCustomsDeclaration(Request request, List emsNo,
			Date beginDate, Date endDate);

	/**
	 * 取得特殊报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的特殊报关单表头
	 */
	List findSpecialCustomsDeclaration(Request request, String emsNo,
			Date beginDate, Date endDate);

	/**
	 * 取得特殊报关单表头 BY 申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return List 是CustomsDeclaration型，在有效期内与指定的手册号匹配的特殊报关单表头
	 */
	List findSpecialCustomsDeclaration(Request request, Date beginDate,
			Date endDate);

	/**
	 * 取得进口报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CustomsDeclaration型，进口报关单表头
	 */
	List findImportCustomsDeclaration(Request request);

	/**
	 * 取得出口报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CustomsDeclaration型，出口报关单表头
	 */
	List findExportCustomsDeclaration(Request request);

	/**
	 * 取得特殊报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CustomsDeclaration型，特殊报关单表头
	 */
	List findSpecialCustomsDeclaration(Request request);

	/**
	 * 保存报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCommInfo
	 *            报关单物料信息
	 */
	BaseCustomsDeclarationCommInfo saveCustomsDeclarationCommInfo(
			Request request,
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo);

	/**
	 * 查询是否选中规范申报规格
	 * 
	 * @param request
	 * @return
	 */
	List findIsSpecification(Request request);

	/**
	 * 取得报关单商品临时信息(如果在报关单中已存在的话，将其过滤)
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List
	 */
	List getTempCustomsDeclarationCommInfo(Request request, boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration, String sfield,
			Object svalues);

	/**
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @return
	 */
	List getTempCustomsDeclarationCommInfo(Request request, boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration);

	/**
	 * 保存报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            料件成品判断，true代表料件，false代表成品
	 * @param tempCommInfoList
	 *            是List型
	 * @param customsDeclaration
	 *            报关单表头
	 */
	void saveCustomsDeclarationCommInfo(Request request, boolean isMaterial,
			List tempCommInfoList, BaseCustomsDeclaration customsDeclaration);

	/**
	 * 删除报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCommInfo
	 *            报关单商品信息
	 */
	void deleteCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo);

	/**
	 * 删除报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param commInfoList
	 *            是List型，报关单商品信息
	 */
	void deleteCustomsDeclarationCommInfo(Request request, List commInfoList);

	/**
	 * 取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是CustomsDeclarationCommInfo型，报关单商品信息
	 */
	List findCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclaration customsDeclaration);

	/**
	 * 根据报关单查询报关单集装箱
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是CustomsDeclarationContainer型，报关单集装箱
	 */
	List findContainerByCustomsDeclaration(Request request,
			BaseCustomsDeclaration customsDeclaration);

	/**
	 * 查询报关行
	 */
	List findcustomsbrokerList(Request request);

	/**
	 * 查询海关删单的报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param typeModel
	 * 
	 */
	public List getImExportCustomsDeclarationDelete(Request request,
			int typeModel, String emsNo, Date beginDate, Date endDate);

	/**
	 * 保存集装箱信息
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclarationContainer
	 *            报关单集装箱信息
	 */
	BaseCustomsDeclarationContainer saveCustomsDeclarationContainer(
			Request request,
			BaseCustomsDeclarationContainer baseCustomsDeclarationContainer);

	/**
	 * 保存集装箱信息
	 * 
	 * @param request
	 *            请求控制
	 * @param b
	 *            报关单表头
	 * @param list
	 *            报关单集装箱信息
	 */
	void saveCustomsDeclarationContainer(Request request,
			BaseCustomsDeclaration b, List list);

	/**
	 * 删除集装箱信息
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclarationContainer
	 *            报关单集装箱信息
	 */
	void deleteCustomsDeclarationContainer(Request request,
			BaseCustomsDeclarationContainer baseCustomsDeclarationContainer);

	/**
	 * 取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return 符合指定的进出口标志的报关单商品信息
	 */
	List findCustomsDeclarationCommInfo(Request request, int impExpFlag);

	/**
	 * 查询报关单集装箱数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return 符合指定进出口标志的报关单集装箱数据
	 */
	List findCustomsDeclarationContainer(Request request, int impExpFlag);

	/**
	 * 取得报关单预计录入号
	 * 
	 * @param request
	 *            请求控制
	 * @param projectType
	 *            模块类型
	 * @return 最大的报关单预录入号
	 */
	String getMaxPreCustomsDeclarationCode(Request request);

	/**
	 * 取得报关单流水号
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return 与指定的进出口标志匹配的报关单流水号
	 */
	Integer getCustomsDeclarationSerialNumber(Request request, int impExpFlag,
			String emsHeadH2k);

	/**
	 * 获取出口发票项目来自出口报关单Id
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationId
	 *            出口报关单id
	 * @return 与指定报关单id匹配的报关单中的出口发票信息
	 */
	List findExportInvoiceItemByCustomsDeclarationId(Request request,
			String customsDeclarationId);

	/**
	 * 删除出口发票项目
	 * 
	 * @param request
	 *            请求控制
	 * @param exportInvoiceItem
	 *            出口发票项目
	 */
	void deleteExportInvoiceItem(Request request,
			BaseExportInvoiceItem exportInvoiceItem);

	/**
	 * 保存或修改出口发票项目
	 * 
	 * @param request
	 *            请求控制
	 * @param exportInvoiceItem
	 *            出口发票项目
	 */
	void saveExportInvoiceItem(Request request,
			BaseExportInvoiceItem exportInvoiceItem);

	/**
	 * 转抄报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param isCopyComm
	 *            是否要转抄商品，true为要
	 * @return BaseCustomsDeclaration 报关单表头Base
	 */
	BaseCustomsDeclaration copyCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, boolean isCopyComm);

	/**
	 * 取得报关单商品流水号
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单表头
	 * @return Integer 报关单商品流水号
	 */
	Integer getCustomsDeclarationCommInfoSerialNumber(Request request,
			BaseCustomsDeclaration customsDeclaration);

	/**
	 * 是否已经有报关单引用了外汇销销单号（jbcus,dzba,bcs)
	 * 
	 * @param request
	 *            请求控制
	 * @param authorizeFile
	 *            批准文号
	 * @param id
	 *            报关单表头Id
	 * @return List 是CustomsDeclaration型，报关单表头信息
	 */
	List findExistAuthorizeFile(Request request, String authorizeFile, String id);

	/**
	 * 取得报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            报关单id
	 * @return 与指定id匹配的报关单
	 */
	BaseCustomsDeclaration findCustomsDeclaration(Request request, String id);

	/**
	 * 排序报关单商品信息后保存
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单商品信息
	 */
	public void saveCustomsInfo(Request request, Vector list);

	/**
	 * 返回系统参数设置中的其他设置
	 * 
	 * @param request
	 *            请求控制
	 * @return 系统参数设置中的其他设置
	 */
	public CompanyOther findCompanyOther(Request request);

	/**
	 * 取得未生效报关单来自进出口类型
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配没有生效的报关单
	 */
	List<BaseCustomsDeclaration> findCustomsDeclarationByImpExpType(
			Request request, int impExpType);

	/**
	 * 生效报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	BaseCustomsDeclaration effectCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 回卷报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	BaseCustomsDeclaration unreelCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 计算报关单头件，毛，净重
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public BaseCustomsDeclaration getPiceAndWeight(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 更新报关单隋附单据
	 * 
	 * @return
	 */
	public BaseCustomsDeclaration getAttachedBill(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 只显示物料四码不为空
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            第一个索引
	 * @param ptNo
	 *            料号
	 * @param emsNo
	 *            手册号
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterielFromInner(Request request, String type,
			int firstIndex, String ptNo, String emsNo);

	/**
	 * 通过内部料号找到帐册信息
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return List 是EmsHeadH2kImg或EmsHeadH2kExg型，电子帐册料件或成品
	 */
	public List findBillByMaterielPtNo(Request request, String emsNo,
			String ptNo, String type, String materielVersion);

	/**
	 * 通过内部料号找到帐册信息
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return List 是EmsHeadH2kImg或EmsHeadH2kExg型，电子帐册料件或成品
	 */
	public List findBillByMaterielPtNo2(Request request, String emsNo,
			String ptNo, String type, String materielVersion);

	/**
	 * 取得物料内容来自报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param info
	 *            报关单商品信息
	 * @return 报关单商品信息中物料内容
	 */
	public List getMaterielByCustoms(Request request,
			BaseCustomsDeclarationCommInfo info);

	/**
	 * 是否已经有报关单引用了外汇销销单号（jbcus,dzsc,bcs)
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            报关单表头Id
	 * @return BaseCustomsDeclaration 报关单表头Base
	 */
	BaseCustomsDeclaration findAllCustomsDeclarationById(Request request,
			String id);

	/**
	 * 报关清单来自报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customs
	 *            报关商品信息
	 * @return 报关清单内容
	 */
	public List findAppFromMaterielByCustomsInfo(Request request,
			BaseCustomsDeclarationCommInfo customs);

	/**
	 * 申请单商品信息来自申请单转报关单中间表
	 * 
	 * @param request
	 *            请求控制
	 * @param customs
	 *            报关商品信息
	 * @return 报关清单内容
	 */
	public List findImpExpCommodityInfoByCustomsInfo(Request request,
			BaseCustomsDeclarationCommInfo customs);

	/**
	 * 取得进口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 进口报关单
	 */
	List findImportCustomsDeclaration(Request request, String condition,
			List<Object> parameters);

	/**
	 * 取得出口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 出口报关单
	 */
	List findExportCustomsDeclaration(Request request, String condition,
			List<Object> parameters);

	/**
	 * 得到申请单号通过清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            单据号
	 * @return 进出口申请单单据号
	 */
	public String getImpExpNoByBillNo(Request request, String billNo);

	/**
	 * 得到多个申请单号通过报关清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            报关清单号码
	 * @return 进出口申请单单据号
	 */
	public List getImpExpMultiNoByBillNo(Request request, String billNo);

	/**
	 * 计算申请单物料的仓库总毛重、仓库总净重
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ImpExpCommodityInfo型，申请单表头
	 * @return Materiel 报关常用物料
	 */
	public Materiel getNetGossWeight(Request request, List list);

	/**
	 * 计算申请单物料与报关单物料的总毛重、总净重的差异
	 * 
	 * @param request
	 *            请求控制
	 * @param info
	 *            报关单商品信息
	 * @return List 是型，
	 */
	public List findAppFromMaterielByCustomsInfoAndNetGossWeight(
			Request request, BaseCustomsDeclarationCommInfo info);

	/**
	 * 删单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param user
	 *            用户信息
	 */
	public void deleteCustomsDeclarationDelete(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, AclUser user);

	/**
	 * 删单
	 * 
	 * @param request
	 *            请求控制
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 * @param user
	 *            用户信息
	 */
	public void deleteCustomsDeclarationDelete(Request request,
			List baseCustomsDeclarations, AclUser user);

	/**
	 * 删除进出口报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param typeModel
	 *            模块类型
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 删除符合条件的进出口报关单
	 */
	/*
	 * public List findImExportCustomsDeclarationDelete(Request request, int
	 * typeModel, String emsNo, Date beginDate, Date endDate);
	 */
	/**
	 * 数量取整
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单商品信息
	 */
	public void customsInfoForMatInt(Request request, List list);

	/**
	 * 得到要申报的进口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getImportDataSource(Request request);

	/**
	 * 取得已生成taskId、未生效的报关单
	 * 
	 * @param request
	 * @return
	 */
	public List<BaseCustomsDeclaration> findDeclarationHasTaskIdUnEffect(
			Request request);

	/**
	 * 得到要申报的特殊报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getSpexportDataSource(Request request);

	/**
	 * 得到要申报的出口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getExportDataSource(Request request);

	/**
	 * 自动排序
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单
	 */
	public void commInfoAutoOrder(Request request,
			BaseCustomsDeclaration customsDeclaration);

	/**
	 * 用户自定义排序
	 * 
	 * @param request
	 *            请求控制
	 */
	public void userDefinedOrder(Request request);

	/**
	 * 根据当前商品编码查询对应的海关商品编码
	 */
	public CheckupComplex findCheckupComplexByCode(Request request,
			String code, int impExpFlag);

	/**
	 * 增加设备管理的特殊报关单
	 * 
	 * @param list
	 * @return
	 */
	List addFixAssetCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, List list);

	/**
	 * 增加设备管理的特殊报关单
	 * 
	 * @param list
	 * @return
	 */
	List addFixtureCustomsDeclarationCommInfo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration, List list);

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 */
	void saveAtcMergeBeforeComInfo(Request request, Vector list);

	/**
	 * 检查发票，核销单号，司机纸号码是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param projectType
	 *            单据类型
	 * @param serialNumber
	 *            流水号
	 * @param fields
	 * @param checkValue
	 * @return
	 */
	boolean isReCustoms(Request request, int projectType, Integer serialNumber,
			String fields, String checkValue);

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            报关单表头
	 * @return 报关单商品信息
	 */
	List findCustomsDeclarationCommInfoByConveyance(Request request,
			String conveyance, int impExpFlag);

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            报关单表头
	 * @return 报关单商品信息
	 */
	List findCustomsDeclarationCommInfoByConveyance(Request request,
			String conveyance, Integer impExpFlag, Integer projectType);

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            运输工具
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findMergerCustomsDeclarationCommInfoByConveyance(
			Request request, String conveyance, int impExpFlag,
			boolean isReworkTotalPrice);

	/**
	 * 取得进口报关单
	 * 
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 进口报关单
	 */
	public List findCustomsDeclarationByConveyance(Request request,
			String conveyance, int impExpFlag);

	/**
	 * 取得报关单金额
	 * 
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	Map<String, Double> findCustomsDeclarationMoney(Request request,
			Integer impExpFlag);

	/**
	 * 取得最后一次导入报关单的日期，并且将最后一次的日期-3
	 * 
	 * @param isImportBGD
	 * @return
	 */
	Date getLastImportBGDDate(Request request, boolean isImportBGD);

	/**
	 * 导入报关单资料
	 */
	Map loadBGDFromQPXml(Request request, ImportBGDCondition condition);

	/**
	 * 导入报关单资料
	 */
	Map loadBGDFromQPDirect(Request request, ImportBGDCondition condition);

	/**
	 * 设置报关单的汇率
	 * 
	 * @param request
	 *            请求控制
	 * @param curr
	 *            币制
	 * @param date
	 *            日期
	 * @param emsno
	 * @return
	 */
	public Double getCurrRateByCurr(Request request, Curr curr, Date date,
			String emsno);

	/**
	 * 从归并关系和电子帐册物料中找出物料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            初始索引
	 * @param obj
	 * 
	 * @param sFields
	 * @return
	 */
	public List findMaterielFromInnerAndInEmsMerger(Request request,
			String type, int firstIndex, Object obj, String sFields);

	/**
	 * 从归并关系和电子帐册物料中找出物料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            初始索引
	 * @param ptNo
	 * @return
	 */
	// public List findMaterielFromInnerAndInEmsMerger(Request request,
	// String type, int firstIndex,String ptNo);
	/**
	 * 统计设备的合同定量、报关已出数量、剩余可进数量
	 * 
	 * @param customsDeclarationCommInfo
	 * @return
	 */
	public List statisticsFixInCommInfo(Request request,
			BaseCustomsDeclarationCommInfo basecustomsDeclarationCommInfo);

	/**
	 * 获取报关单头的运输工具相同的所有集装箱号
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return 获取报关单头的运输工具相同的所有集装箱号
	 */
	public List findAllContainerByConveyance(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 导出报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param isFtoJ
	 *            是否繁转简
	 * @return
	 */
	public List customsDeclarationExport(Request request, int impExpFlag,
			Date beginDate, Date endDate, boolean isFtoJ);

	/**
	 * 通过进出口标志找出报关单
	 * 
	 * @param request
	 * @param flag
	 * @return
	 */
	public List findCustomsDeclarationByImpExpFlag(Request request, int flag);

	/**
	 * 通过单据类型找出电子帐册资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return
	 */
	public List findEmsByProjectType(Request request, int type);

	/**
	 * 通过单据类型找正在执行的电子帐册资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return
	 */
	public List findExcuteEmsByProjectType(Request request, int type);

	/**
	 * 找出成品或料件
	 * 
	 * @param request
	 *            请求控制
	 * @param headid
	 * @param type
	 * @param impExpFlag
	 * @param couId
	 * @param code
	 * @return
	 */
	public List findImgOrExg(Request request, String headid, int type,
			int impExpFlag, String couId, String code);

	/**
	 * 找到录入了QP系统导出的进口报关单
	 * 
	 * @param code
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	@SuppressWarnings("unchecked")
	public List findBGDFromQPXml(Request request, Integer impExpFlag,
			String condition, List<Object> parameters);

	/**
	 * 根据报关单号取得所有的从QP倒出的报关单
	 * 
	 * @param code
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List findAllLoadBGDFromQPXml(Request request,
			String customsDeclarationCode);

	/**
	 * 得到要申报的进口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getImportDataSource(Request request, String str1, String str2);

	/**
	 * 得到要申报的特殊报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getSpexportDataSource(Request request, String str1, String str2);

	/**
	 * 得到要申报的出口报关单表头，已检查但未生效的报关单表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempCustomsMessage型，存放还没被选择的报关单表头信息
	 */
	public List getExportDataSource(Request request, String str1, String str2);

	/**
	 * 当报关单明细超出20项的时候，将当前报关单拆分成多份报关单
	 * 
	 * @param baseCustomsDeclaration
	 * @return
	 */
	List splitCustomsDeclaration(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 进口报关单按流水号重新排序
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @return
	 */
	public void resortImportCustomsDeclarationSerialNumber(Request request,
			String emsNo);

	/**
	 * 出口报关单按流水号重新排序
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @return
	 */
	public void resortExportCustomsDeclarationSerialNumber(Request request,
			String emsNo);

	/**
	 * 特殊报关单按流水号重新排序
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @return
	 */
	public void resortSpecialCustomsDeclarationSerialNumber(Request request);

	/**
	 * 计量单折算比例位资料
	 * 
	 * @param request
	 * @return
	 */
	public Map findAllUnitRateMap(Request request);

	/**
	 * 根据运输工具查询报关单
	 * 
	 * @param request
	 * @param traffic
	 * @return
	 */
	public List<BaseCustomsDeclarationCommInfo> findCustomsDeclarationsForPrint(
			Request request, String traffic);

	/**
	 * 查询所有状态的合同
	 * 
	 * @param declareState
	 * @return
	 */
	public List findContractByDeclareState(Request request, String declareState);

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBatchUpdateComplex(Request request, String declareState,
			String contractNo, String emsType, Boolean isMaterial);

	/**
	 * 更新合同备案的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllContractComplex(Request request, String declareState,
			Boolean isMaterial, Complex complex, String oldComplexId,
			Integer seqNum, boolean isSendData, String contractId);

	/**
	 * 更新所有备案资料库的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllBcsDictPorComplex(Request request,
			String declareState, Boolean isMaterial, Complex complex,
			String oldComplexId, Integer seqNum, boolean isSendData);

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllBcsInnerMergeDataComplex(Request request,
			Boolean isMaterial, Complex complex, Integer seqNum);

	/**
	 * 取得备案资料库的归并关系
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findBcsDictPorImgOrExgInnerMergeSeqNum(Request request,
			String declareState, Boolean isMaterial, Integer seqNum);

	/**
	 * 查询未复进数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> queryReturnImportReport(Request request,
			Date begin, Date end, int queryType, int impExpFlag);

	/**
	 * 查询未复进数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> queryReturnImportReport(Request request,
			Date begin, Date end, int queryType, int impExpFlag, String emsNo,
			int projectType);

	/**
	 * 查询未复出数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> queryReturnExportReport(Request request,
			Date begin, Date end, int queryType, int impExpFlag);

	/**
	 * 查询未复出数据报表。
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> queryReturnExportReport(Request request,
			Date begin, Date end, int queryType, int impExpFlag,
			String emsHeadH2k, int projectType);

	/**
	 * 查询统计未复出数据或未复进数据。
	 * 
	 * @param request
	 * @param complexId
	 *            商品编号
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	public Double countReturnNumbers(Request request, String complexId,
			boolean impExpFlag, String version, int commSerialNo);

	/**
	 * 查询统计未复出数据或未复进数据。
	 * 
	 * @param request
	 * @param complexId
	 *            商品编号
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            手册号
	 * @return
	 */
	public Double countReturnNumbers(Request request, String emsHeadH2k,
			String complexId, boolean impExpFlag, String version,
			int commSerialNo);

	/**
	 * 查询统计可退换料件出口数或退厂返工可进口数
	 * 
	 * @param request
	 * @param complexId
	 *            商品编号
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            手册号
	 * @return
	 */
	public Double countRetreatNumbers(Request request, String emsHeadH2k,
			String complexId, boolean impExpFlag, String version,
			int commSerialNo);

	/**
	 * 补充申报基础接口
	 * 
	 * @author chl
	 *
	 */

	/**
	 * 根据 手册号,补充报关单类型,流水号, 开始日期（进出口日期在该日期之后）, 结束日期（进出口日期在该日期之前）, 查询报关单
	 * 
	 * @param request
	 * @param ems
	 * @param supplmentType
	 * @param serialNumber
	 * @param begin
	 * @param end
	 * @return
	 */
	public List getCustomsDeclaration(Request request, String ems,
			Integer supplmentType, Integer serialNumber, Date begin, Date end);

	/**
	 * 保存补充报关单信息
	 * 
	 * @param decSupplementList
	 */
	public String saveDecSupplementList(DecSupplementList decSupplementList);

	public List getDecSupplementList(String supType,
			String baseCustomsDeclarationCommInfo);

	/**
	 * 通过报关单id获得报关单底下的补充报关单信息列表
	 * 
	 * @param request
	 * @param decSupplementList
	 * @return
	 */
	public List getDecSupplementList(Request request,
			String baseCustomsDeclarationId);

	/**
	 * 根据时间 补充报关单
	 * 
	 * @param request
	 * @param begin
	 * @param end
	 * @return
	 */
	public List queryDecSupplementList(Request request, Date begin, Date end);

	/**
	 * 根据全部补充报关单
	 * 
	 * @param request
	 * @return
	 */
	public List queryDecSupplementListAll(Request request);

	/**
	 * 删除补充报关单
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	public void deleteDecSupplementList(Request request, String id);

	/**
	 * 根据ID查找补充报关单
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	public List getDecSupplementListById(Request request, String id);

	/**
	 * 根据ID查找内部商品新增补充报关单表体 发送状态为未发送
	 * 
	 * @param request
	 * @param date
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo getBaseCustomsDeclarationCommInfoById(
			String id);

	/**
	 * 查找所有未发送的补充申报单
	 * 
	 * @param request
	 * @param isSend
	 * @return
	 */
	public List findDecSupplementListByIsSend(Request request, String isSend);

	/**
	 * 获得多个申请单,报关单通过申请单转报关单中间表获得申请单
	 * 
	 * @param custom
	 *            报关单
	 * @return 申请单
	 */
	public List getMultiImpExpBillByCustomsDeclaration(Request request,
			BaseCustomsDeclaration custom);

	/**
	 * 将数据传到服务端
	 * 
	 * @param request
	 * @param json
	 * @param impExpFlag
	 * @param projectType
	 * @return
	 */
	Object transferImportDeclaration(Request request, String srcJSON,
			Integer impExpFlag, Integer projectType);

	/**
	 * 将数据传到服务端
	 * 
	 * @param request
	 * @param json
	 * @param impExpFlag
	 * @param projectType
	 * @return
	 */
	Object transferExportDeclaration(Request request, String srcJSON,
			Integer impExpFlag, Integer projectType);

	/**
	 * 查询所有当天的报关单号
	 * 
	 * @param request
	 * @param projectType
	 * @param date
	 * @param impExpFlag
	 * @return
	 */
	List findCustomsDeclaration(Request request, Integer projectType,
			Date date, Integer impExpFlag);

	/**
	 * 查询所有的报关单
	 * 
	 * @param request
	 * @param projectType
	 * @param date
	 * @param impExpFlag
	 * @return
	 */
	List<BaseCustomsDeclaration> findCustomsDeclaration(Request request,
			Integer impExpFlag, Date beginDate, Date endDate,
			String customsDecCode, Integer projectType, String emsNo,
			List impExpTypeList);

	/**
	 * 查询所有的报关单
	 * 
	 * @param request
	 * @param projectType
	 * @param date
	 * @param impExpFlag
	 * @return
	 */
	List<BaseCustomsDeclaration> findCustomsDeclaration(Request request,
			Integer impExpFlag, Date beginDate, Date endDate,
			String customsDecCode, Integer projectType, String emsNo);

	/**
	 * 查询所有报关单号
	 * 
	 * @param request
	 * @param projectType
	 * @param impExpFlag
	 * @return
	 */
	List findAllCustomsDeclaration(Request request, Integer projectType,
			Integer impExpFlag);

	/**
	 * 得到收获单位
	 * 
	 * @param request
	 * @return
	 */
	public String getReceiverUnit(Request request);

	/**
	 * 查询是否显示出口装箱单或者发票
	 * 
	 * @param parameter
	 */
	List findExportPackinglistOrInvoice(Request request);

	public Map<Integer, String> getCommSpec(Integer projectType,
			BaseCustomsDeclaration bgdhead);

}
