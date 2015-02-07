/*
 * Created on 2004-7-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.entity.BillTemp1;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.BcusContractExeInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.enc.entity.ExpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.bcus.enc.entity.InputInExRequestBillOrder;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.XinpuReport;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.BcusParameterSet;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;

/**
 * 通关--申请单与清单 接口
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 *         chcked by lm at 2010/10/12
 */
@SuppressWarnings("unchecked")
public interface EncAction extends BaseEncAction {
	/**
	 * 查询BCUS参数
	 * 
	 * @param request
	 *            请求控制
	 */
	BcusParameterSet findBcusParameterSet(Request request);

	/**
	 * 保存BCUS参数
	 * 
	 * @param request
	 *            请求控制
	 * @param bcusParameterSet
	 *            BCS参数设置
	 */
	BcusParameterSet saveBcusParameterSet(Request request,
			BcusParameterSet bcusParameterSet);

	/**
	 * 查找所有进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @return 所有进出口申请单
	 */
	List findImpExpRequestBill(Request request);
	List findImpExpRequestBill1(Request request);
	
	/**
	 * 查找进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @return 进出口申请单
	 */
	ImpExpRequestBill getImpExpRequestBill(Request request, String billNo);
	/**
	 * 查找所有进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return 进出口申请单
	 */
	List findAllImpExpRequestBillByType(Request request, int type);

	/**
	 * 查找所有进出口申请单来自进出口申请单类型
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @param isCustomsBill
	 *            已全转报关清单或全转已转报关单（当全部转完为True）
	 * @param billNo
	 *            单据号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param customer
	 *            客户/供应商
	 * @return 符合上面指定的条件的进出口申请单
	 */
	public List findImpExpRequestBillByType(Request request, int type,
			Boolean isCustomsBill, String billNo, Date beginDate, Date endDate,
			String customer);

	/**
	 * 删除进出口申请单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	void deleteImpExpRequestBill(Request request,
			ImpExpRequestBill impExpRequestBill);

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBill
	 *            进出口申请单据
	 * @return 进出口申请单
	 */
	ImpExpRequestBill saveImpExpRequestBill(Request request,
			ImpExpRequestBill impExpRequestBill);
	 /**
     * 保存申请单表头项数
     * @param request
     * @param impExpRequestBill
     * @return
     */
	public ImpExpRequestBill saveImpExpRequestBillItemCount(Request request,
			ImpExpRequestBill impExpRequestBill);
	
	
	
	/**
	 * 【查询】进出口申请单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBill
	 *            进出口申请单据
	 * @return 进出口申请单
	 */
	ImpExpRequestBill findImpExpRequestBillById(Request request,
			String id);
	
	
	/**
	 * 获得商品信息加载子表的记录
	 * 
	 * @param request
	 *            请求控制
	 * @return 商品信息加载子表的记录
	 */
	List findImpExpCommodityInfo(Request request);

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return 最大的编号或者是0001
	 */
	String getMaxBillNoByType(Request request, int type);

	/**
	 * 查找所有进出报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @return 进出口报关清单
	 */
	List findApplyToCustomsBillList(Request request);

	/**
	 * 根据清单类型查找报关清单.
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 报关清单
	 */
	List findApplyToCustomsBillListByType(Request request, int impExpFlag,
			Date beginDate, Date endDate);

	/**
	 * 根据清单类型查找报关清单.
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	public List findApplyToCustomsBillListByTypeBoToCustoms(Request request,
			int impExpFlag);

	/**
	 * 删除报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	void deleteApplyToCustomsBillList(Request request,
			ApplyToCustomsBillList applyToCustomsBillList);

	/**
	 * 保存报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 报关清单
	 */
	ApplyToCustomsBillList saveApplyToCustomsBillList(Request request,
			ApplyToCustomsBillList applyToCustomsBillList);

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 与指定的归并后商品信息id匹配的归并前商品信息
	 */
	List findAtcMergeBeforeComInfoByAfterID(Request request,
			AtcMergeAfterComInfo atcMergeAfterComInfo);

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 与指定的清单编号匹配的归并后商品信息
	 */
	List findAtcMergeAfterComInfoByListID(Request request,
			ApplyToCustomsBillList applyToCustomsBillList);
	/**
	 * 查询申请单
	 * 
	 * @param billNo
	 *            清单状态
	 * @param flat
	 *            进出口标志
	 * @return
	 */
	public ApplyToCustomsBillList getApplyBillList(Request request, String billNo, int flag);
	/**
	 * 根据清单类型查找未转报关单的报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return 未转报关单的报关清单
	 */
	List findBillListNoMakeCustomsDeclaration(Request request, int impExpFlag);

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            归并前商品信息
	 * @return 删除后报关清单的信息
	 */
	ApplyToCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			AtcMergeBeforeComInfo atcMergeBeforeComInfo);

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关清单归并前商品信息
	 * @return 删除后报关清单的信息
	 */
	ApplyToCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			List list);

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            归并前商品信息
	 * @return 删除后报关清单的信息
	 */
	AtcMergeBeforeComInfo saveAtcMergeBeforeComInfo(Request request,
			AtcMergeBeforeComInfo atcMergeBeforeComInfo);

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param declaredDataList
	 *            临时单据商品信息
	 * @param billList
	 *            归并前商品信息
	 * @return 报关清单归并前商品信息
	 */
	ApplyToCustomsBillList saveAtcMergeBeforeComInfo(Request request,
			List declaredDataList, ApplyToCustomsBillList billList);

	/**
	 * 获取报关清单的最大清单编号
	 * 
	 * @param request
	 *            请求控制
	 * @return 报关单的最大编号
	 */
	String getApplyToCustomsBillListMaxNo(Request request);

	/**
	 * 取得临时申报商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @param materielProductFlag
	 *            物料成品标志
	 * @return 临时申报商品信息
	 */
	List getTempDeclaredCommInfo(Request request,
			ApplyToCustomsBillList billList, Integer materielProductFlag);

	/**
	 * 删除进出口商品信息数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            进出口商品信息
	 */
	void deleteImpExpCommodityInfo(Request request, List list);

	/**
	 * 保存进出口商品信息数据
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param list
	 *            进出口商品信息
	 * @return 进出口商品信息数据
	 */
	List saveImpExpCommodityInfo(Request reqeust, List list);

	/**
	 * 获得进出口单商品信息记录来自数据是否正确的检验
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            父对象的id
	 * @return 检验后的商品信息记录
	 */
	List findImpExpCommodityInfoByCheck(Request request, String parentId);

	/**
	 * 查找所有可以生成报关清单的数据来自进出口申请单(ATC)
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return 有效且已全转报关清单或全转已转报关单
	 */
	List findImpExpRequestBillToATCByType(Request request, int type);

	/**
	 * 进出口申请单--->报关清单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isImportGoods
	 * 是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @param dataSource
	 *            数据源
	 * @param emsEdiMergerHead
	 *            电子帐册归并表头
	 * @param isImportGoods
	 *            是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */
	/*
	 * List makeApplyToCustomsRequestBillList(Request request,
	 * ApplyToCustomsBillList applyToCustomsBillList, List dataSource,
	 * EmsEdiMergerHead emsEdiMergerHead, boolean isImportGoods, boolean
	 * isNewRecord);
	 */

	/**
	 * 在根据报关清单自动生成报关单的时候，检查报关清单的数据是否符合条件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关清单内容
	 * @return 如果成功返回0；
	 */
	int checkBillListForMakeCustomsDeclaration(Request request, List list);

	/**
	 * 清单转报关单 从报关清单自动转报关单及其商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billLists
	 *            报关清单
	 * @param param
	 *            报关单参数
	 * @param customs
	 *            报关单信息
	 * @return 与指定的报关清单内容匹配的报关单信息
	 */
	List makeCusomsDeclarationFromBillList(Request request, List impexpbills,
			List afterinfoLists, MakeCusomsDeclarationParam param,
			CustomsDeclaration customs);

	/**
	 * 作废报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单信息
	 * @return 作废的报关单
	 */
	CustomsDeclaration cancelCustomsDeclaration(Request request,
			CustomsDeclaration customsDeclaration);

	/**
	 * 查找已转报关单的报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @return 已转报关单的报关清单
	 */
	List findBillListMakedCustomsDeclaration(Request request);

	/**
	 * 根据报关清单取得报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @return 与指定的报关清单匹配的报关单信息
	 */
	List findCustomsDeclarationByBillList(Request request,
			ApplyToCustomsBillList billList);

	/**
	 * 根据报关清单取得清单和报关单商品信息的差异
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @return 报关清单和报关单内容
	 */
	List findDiffrenceAnalyseCommInfo(Request request,
			ApplyToCustomsBillList billList);

	/**
	 * 根据报关单ID取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationID
	 *            报关单id
	 * @return 与指定报关单id匹配的报关单商品信息
	 */
	List findCommInfoByCustomsDeclarationID(Request request,
			String customsDeclarationID);

	/**
	 * 查询报关单信息来自预录入号
	 * 
	 * @param request
	 *            请求控制
	 * @param preCustomsDeclarationCode
	 *            预录入号
	 * @param projectType
	 *            模块信息
	 * @return 与指定的预录入号匹配的报关单信息
	 */
	List findCustomsDeclarationbyYlrh(Request request,
			String preCustomsDeclarationCode, int projectType);

	/**
	 * 根据清单类型和清单状态 查找报关清单.
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @param billState
	 *            单据状态
	 * @return 与指定的单据类型和状态匹配的报关清单
	 */
	List findApplyToCustomsBillListByTypeAndState(Request request,
			int impExpType, int billState);

	/**
	 * 查找进口类型退港申请书来自进出口单据Id
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBillId
	 *            进出口申请单id
	 * @return 与指定的进出口申请单据匹配的进口类型的退港申请书
	 */
	ImpBackPortRequestBook findImpBackPortRequestBookById(Request request,
			String impExpRequestBillId);

	/**
	 * 查找出口类型退港申请书来自进出口单据Id
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBillId
	 *            进出口申请单id
	 * @return 与指定的进出口申请单id匹配的出口类型的退港申请书
	 */
	ExpBackPortRequestBook findExpBackPortRequestBookById(Request request,
			String impExpRequestBillId);

	/**
	 * 保存进口类型的退港申请书
	 * 
	 * @param request
	 *            请求控制
	 * @param impBackPortRequestBook
	 *            进口类型的退港申请书
	 * @return 进口类型的退港申请书
	 */
	ImpBackPortRequestBook saveImpBackPortRequestBook(Request request,
			ImpBackPortRequestBook impBackPortRequestBook);

	/**
	 * 保存出口类型的退港申请书
	 * 
	 * @param request
	 *            请求控制
	 * @param expBackPortRequestBook
	 *            出口类型的退港申请书
	 * @return 出口类型的退港申请书
	 */
	ExpBackPortRequestBook saveExpBackPortRequestBook(Request request,
			ExpBackPortRequestBook expBackPortRequestBook);

	/**
	 * 查找进出口商品信息来自是否备案并以父类ID过滤
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            进出口申请单据id
	 * @return 与进出口申请单匹配且备案的进出口商品信息
	 */
	List findImpExpCommodityInfoByPutOnRecord(Request request, String parentId);

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	List findTempImpExpRequestBillByImpExpTypeToATC(Request request,
			int impExpType);

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	public List findTempImpExpRequestBillByImpExpTypeToATC(Request request,
			Date beginAvailabilityDate, Date endAvailabilityDate,
			String billNo, int impExpType);

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param request
	 *            请求控制
	 * @param parentList
	 *            父对象
	 * @return 进出口商品信息
	 */
	List findTempImpExpCommodityInfoByParent(Request request, List parentList);

	/**
	 * 获得当前进出口的商品信息的个数
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            进出口申请单id
	 * @return 进出口商品信息的个数
	 * 
	 */
	int findImpExpCommodityInfoCount(Request request, String parentId);

	/**
	 * 获得当前进出口的商品信息的个数
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            进出口申请单id
	 * @return 进出口商品信息的个数
	 * 
	 */
	List findImpExpCommodityInfo(Request request, String parentId);

	/**
	 * 获得当前所有申请单的单据号
	 * 
	 * @param request
	 *            请求控制
	 */
	List findBillNoOfImpExpRequestBill(Request request);

	/**
	 * 有数据转报关清单在进出口申请单中
	 * 
	 * @param request
	 *            请求控制
	 * @param c
	 *            进出口申请单据
	 * @return 有数据转报关清单的个数
	 */
	boolean hasDataTransferApplyToCustomsBillByImpExpRequestBill(
			Request request, ImpExpRequestBill c);

	/**
	 * 生成进出口申请单据
	 * 
	 * @param request
	 *            请求控制
	 * @param tempBillMasterList
	 *            临时单据表头
	 * @param tempBillDetailList
	 *            临时单据明细
	 */
	void makeImpExpRequestBill(Request request, List tempBillMasterList,
			List tempBillDetailList);

	/**
	 * 进出口申请单转报关清单的检查--返回没有在电子帐册中备案的数据(成品)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时进出口商品信息
	 * @param emsEdiMergerHead
	 *            经营范围归并表头
	 * @param type
	 *            物料类型
	 * @return 没有在电子帐册中备案的成品数据
	 */
	List checkTempImpExpCommodityInfoByFinishProduct(Request request,
			List list, EmsEdiMergerHead emsEdiMergerHead, String type);

	/**
	 * 进出口申请单转报关清单的检查--返回没有在电子帐册中备案的数据(料件)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时进出口商品信息
	 * @param emsH2k
	 *            电子帐册表头
	 * @param emsEdiMergerHead
	 *            经营范围归并关系表头
	 * @return 没有在电子帐册中备案的料件数据
	 */
	List checkTempImpExpCommodityInfoByMateriel(Request request, List list,
			EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead);

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param company
	 *            公司名称
	 * @return 最大的单据号或0001
	 */
	String getMaxBillNoByCompany(Company company);

	/**
	 * 返回载货清单成品列表
	 * 
	 * @param request
	 *            请求控制
	 * @return 载货清单成品列表
	 */
	public List findMakeListExgList(Request request);

	/**
	 * 判断单据号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param data
	 *            进出口申请单
	 * @return 单据号如果重复返回true 否则返回false
	 */
	public boolean isReMerger(Request request, ImpExpRequestBill data);

	/**
	 * 得到当前物料不在电子帐册中备案的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时进出口商品信息
	 * @param emsH2k
	 *            电子帐册表头
	 * @param emsEdiMergerHead
	 *            经营范围归并信息表头
	 * @param type
	 *            物料类型
	 * @param isbeianboolean
	 *            是否备案
	 * @param emsFrom
	 *            是否在电子帐册归并关系中备案
	 * 
	 * @return 没有在电子帐册中备案的物料记录
	 */
	public List checkTempImpExpCommodityInfoByFinishProduct2(Request request,
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead,
			String type, boolean isbeianboolean, String emsFrom);

	/**
	 * 申请单转清单时归并关系的来源选择
	 * 
	 * @param emsH2k
	 * @param emsEdiMergerHead
	 * @param type
	 * @param isbeian
	 * @param emsFrom
	 * @return
	 */
	public List findInnerMergeDataByPtNo(Request request, EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead, String type, boolean isbeian,
			String emsFrom);

	/**
	 * 进出口报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @param seqNum
	 *            序号
	 * @param customer
	 *            客户供应商
	 * @param ImpExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version 版本
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部
	 * @param deptName
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内符合条件的进出口报关商品信息
	 */
	public List findImpExpCommInfoList(Request request, boolean isImport,
			Integer seqNum, String customer, String ImpExpType, Date beginDate,
			Date endDate,String version, boolean isDeclaration, boolean isdept, List deptName,
			int isEffect);

	/**
	 * 特殊报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @param seqNum
	 *            序号
	 * @param customer
	 *            客户供应商
	 * @param ImpExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部
	 * @param deptName
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内符合条件的进出口报关商品信息
	 */
	public List findSpecialImpExpCommInfoList(Request request,
			boolean isImport, Integer seqNum, String customer,
			String ImpExpType, Date beginDate, Date endDate,
			boolean isDeclaration, boolean isdept, List deptId, int isEffect);

	/**
	 * 查询已报关的商品名称
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfo(Request request, boolean isImport);

	/**
	 * 查询已报关的商品名称
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfoNoSpec(Request request,
			boolean isImport);

	/**
	 * 根据类型得到明细
	 * 
	 * @param iEType
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param iseffective
	 *            是否生效
	 * @return 在有效期内与指定的类型匹配生效的进出口商品明细
	 */
	public List findImpExpCommInfoPriceByType(Request request,
			boolean isProduct, Date beginDate, Date endDate, Boolean iseffective);
//	/**
//	 * 查询报关单商品信息
//	 * @param tradeMode 贸易方式
//	 * @param  commName 商品名称
//	 * @param  type 打印类型
//	 * @param  beginDate 开始日期
//	 * @param  endDate 结束日期
//	 */
//	public List findImpExpComminfoDj(Request request,
//			Integer type ,Trade tradeMode,Integer commName,Date beginDate, Date endDate,boolean iseffective
//			, Map paraMap);
		
	
	/**
	 * 查询已报关的客户
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 以报关的客户
	 */
	public List findCustomsDeclarationCustomer(Request request, boolean isImport);

	/**
	 * 查询所有申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param state
	 *            清单状态
	 * @param type
	 *            进出口类型
	 * @param flat
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 与条件匹配的申请单
	 */
	public List findApplyBillList(Request request, Integer state, Integer type,
			Integer flat, Date beginDate, Date endDate);

	/**
	 * 进口料件使用情况
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部
	 * @param deptName
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内符合条件的进口料件情况
	 */
	public List findImpExpCommInfoUseForDept(Request request, boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, List deptCode, int isEffect);

	/**
	 * 不分事业部 进口料件情况统计表
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部
	 * @param deptName
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * @param isCountStoreNum
	 *            是否统计仓库里的数量
	 * @return 有效期内符合条件的进口料件情况
	 */
	public List findImpExpCommInfoUseNoDept(Request request, boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, int isEffect,
			boolean isCountStoreNum);

	/**
	 * 出口成品使用情况表
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            成品序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部
	 * @param deptName
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内符合条件的成品使用情况
	 */
	public List findImpExpCommInfoUseForExgForDept(Request request,
			boolean isImport, Integer seqNum, String customer, String IEType,
			Date beginDate, Date endDate, boolean isDeclaration,
			boolean isdept, List deptCode, int isEffect);

	/**
	 * 不分事业部出口成品情况统计表
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            成品序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isDeclaration
	 *            是否为申报日期
	 * @param isdept
	 *            是否区分事业部
	 * @param deptName
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内符合条件的成品使用情况
	 */
	public List findImpExpCommInfoUseForExgNoDept(Request request,
			boolean isImport, Integer seqNum, String customer, String IEType,
			Date beginDate, Date endDate, boolean isDeclaration, int isEffect);

	/**
	 * 查询物料总量
	 * 
	 * @param request
	 *            请求控制
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 与指定的十位备案序号匹配的物料总量
	 */
	public List findMaterielAmountBySeqNum(Request request, Integer tenSeqNum);

	/**
	 * 内部商品新增报关单表体
	 * 
	 * @param request
	 *            请求控制
	 * @param commInfo
	 *            报关单商品信息
	 * @param customsDeclaration
	 *            报关单内容
	 * @param exgbill
	 *            电子帐册成品表
	 * @param imgbill
	 *            电子帐册料件表
	 * @return 报关单表体内容
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill(
			Request request, BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill);

	/**
	 * 保存报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            报关商品信息
	 */
	public void SaveCustomsFromMateriel(Request request, CustomsFromMateriel obj);

	/**
	 * 取得成品版本号来自成品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            成品序号
	 * @return 与序号匹配的成品的版本号
	 */
	public List getExgVersion(Request request, String seqNum);

	/**
	 * 保存报关清单归并后商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            归并后商品信息
	 */
	public AtcMergeAfterComInfo saveAtcMergeAfterComInfo(Request request,
			AtcMergeAfterComInfo obj);

	/**
	 * 查询进出口报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @param commName
	 *            商品名称
	 * @param tradeMode
	 *            贸易方式
	 * @param customsNo
	 *            报关单号
	 * @param projectDeptName
	 *            事业部
	 * @return 符合指定条件的进出口报关单商品信息
	 */
	public List findImpExpCommInfo(Request request, Integer seqNum,
			String customer, String iEType, Date beginDate, Date endDate,
			Date impexpBeginDate, Date impexpEndDate, boolean isProduct,
			Boolean iseffective, String commName, Trade tradeMode,
			String customsNo, String projectDeptName);

	/**
	 * 帐册总体进出金额状况统计表
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否有效
	 * @return 有效期内帐册总体进出金额
	 */
	public List jisuanEmsImpExpMoneyTotal(Request request, Date beginDate,
			Date endDate, int isEffect);

	/**
	 * 帐册进出口量平横状况
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @param isDeclaration
	 *            是否为申报日期
	 * @return 有效期内帐册进出口情况
	 */
	public List jisuanEmsImpExpBalance(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration);

	/**
	 * 帐册出口成品状况表--------------------申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 指定的日期内帐册出口成品状况
	 */
	public List jisuanEmsExpExg(Request request, Date beginDate, Date endDate,
			Boolean isEffect);

	/**
	 * 料件进出平衡状况汇总表--------------------进出口日期
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @param jisuanImgBalanceTotal
	 *            是否计算有效期内料件进出状况汇总
	 * @return 有效期内料件进出状况汇总
	 */
	public List jisuanImgBalanceTotal(Request request, Date beginDate,
			Date endDate, int isEffect, boolean jisuanImgBalanceTotal,boolean isJisuan);

	/**
	 * 更新清单
	 * 
	 * @param request
	 *            请求控制
	 * @param flat
	 *            进出口标志
	 */
	public void changeApplyBillState(Request request, Integer flat);

	/**
	 * 出口报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内出口报关清单的情况
	 */
	public List jisuanExpCustomsBill(Request request, Date beginDate,
			Date endDate);

	/**
	 * 进口报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内进口报关清单的情况
	 */
	public List jisuanImpCustomsBill(Request request, Date beginDate,
			Date endDate);

	/**
	 * 应退换料件
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内与指定的进出口标志匹配的应退换料件的情况
	 */
	public List jisuanExchangeImp(Request request, Integer impExpFlag,
			Date beginDate, Date endDate);

	/**
	 * 应退换成品
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内应退换成品的情况
	 */
	public List jisuanExchangeExp(Request request, Integer impExpFlag,
			Date beginDate, Date endDate);

	/**
	 * 查询物料内容来自集装箱
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 生效的进出口申请单
	 */
	public List findMaterielFromContainer(Request request, int index,
			int length, String property, Object value, Boolean isLike);

	/**
	 * 进口料件单重差异稽核表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            进出口申请单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内进口料件单重差异
	 */
	public List jisuanImpUnitWeightCheck(Request request, List list,
			Date beginDate, Date endDate);

	/**
	 * 取得成品单耗来自序号汇总
	 * 
	 * @param request
	 *            请求控制
	 * @param allSeqNum
	 *            序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isTotal
	 *            是否统计
	 * @param deptId
	 *            部门ID号
	 * @return 与指定序号匹配的成品序号
	 */

	public List findExgUnitWearBySeqNumIsTotal(Request request, BillTemp1 bill ,
			Date beginDate, Date endDate,String deptId);
	/**
	 * 取得成品单耗来自序号单个
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            序号
	 * @return 与指定序号匹配的成品序号
	 */
	public List findExgUnitWearBySeqNumNoTotal(Request request, BillTemp1 bill ,
			Date beginDate, Date endDate,  String deptId, Integer impExpType);

	/**
	 * 查询所有料件的耗用
	 * @param request
	 * @param imgSeqNum
	 * @return
	 */
	public List findBomBySeqNum(Request request,Integer[] imgSeqNum);
	/**
	 * 取得报关单的总金额 净重 毛重
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单
	 * @return 报关单的总金额 净重 毛重
	 */
	public List getTotal(Request request, List list);

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBills
	 *            进出口申请单据
	 */
	public void saveImpExpRequestBills(Request request, List impExpRequestBills);

	/**
	 * 查找进出口申请单来自进出口申请单类型
	 * 
	 * @param billNo
	 *            单据号
	 * @param billType
	 *            单据类型
	 */
	public List findImpExpRequestBillByBillNo(Request request, String billNo,
			Integer billType);

	/**
	 * 进出口申请单--->报关清单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isImportGoods
	 * 是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @param dataSource
	 *            数据源
	 * @param emsEdiMergerHead
	 *            电子帐册归并表头
	 * @param isImportGoods
	 *            是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */
	public List makeApplyToCustomsRequestBillList(Request request,
			List dataSource, boolean isMaterial,
			MakeCusomsDeclarationParam param);

	/**
	 * 表头是否完全转报关单及所转清单
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            所有的单据号
	 * @param ListNo
	 *            新单据号
	 */
	public void changeImpexpbillByBillNo(Request request, List list,
			String ListNo);

	/**
	 * 保存报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param commInfo
	 *            内部商品新增报关单表体
	 * @param customsDeclaration
	 *            报关单表头
	 * @param exgbill
	 *            电子帐册成品
	 * @param imgbill
	 *            电子帐册料件
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill2(
			Request request, BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill);

	/**
	 * 保存报关单集装箱号
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @param billhs
	 *            单据LIST
	 * @return
	 */
	public List findCustomsDeclarationContainer(Request request,
			Date beginDate, Date endDate, int index, int length,
			String property, Object value, Boolean isLike, Hashtable billhs);

	/**
	 * 进口料件单重差异稽核表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            集装箱LIST
	 * @return
	 */
	public List accountImpUnitWeightCheck(Request request, List list);

	/**
	 * 内部商品转报关单删除
	 * 
	 * @param request
	 *            请求控制
	 * @param fromMateriel
	 *            报关单物料信息
	 * @param customsDeclaration
	 *            报关单表头
	 * @param exgbill
	 *            帐册成品
	 * @param imgbill
	 *            帐册料件
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill3(
			Request request, BaseCustomsFromMateriel fromMateriel,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill);

	/**
	 * 删除清单归并后
	 * 
	 * @param request
	 *            请求控制
	 * @param afterInfo
	 *            报关清单归并后商品信息
	 */
	public void deleteAtcMergeAfterComInfo(Request request,
			AtcMergeAfterComInfo afterInfo);

	/**
	 * 查找没有报关的归并后商品信息来自申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @param isTransferCustomsBill
	 *            是否已转报关单
	 * @return
	 */
	public List findAtcMergeAfterComInfoNotToCustoms(Request request,
			ApplyToCustomsBillList billList, Boolean isTransferCustomsBill);

	/**
	 * 申请单转报关单查看--删除
	 * 
	 * @param request
	 *            请求控制
	 * @param info
	 *            内部商品新增报关单表体
	 * @param t
	 *            进出口申请单表体资料
	 * @param isDelete
	 *            是否删除
	 */
	public void deleteImpExpCommodityInfo(Request request,
			BaseCustomsDeclarationCommInfo info, ImpExpCommodityInfo t,
			boolean isDelete);

	/**
	 * 保存进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param info
	 *            进出口申请单表体资料
	 * @return
	 */
	public ImpExpCommodityInfo saveImpExpComInfo(Request request,
			ImpExpCommodityInfo info);

	/**
	 * 根据成品序号及版本号，查找版本
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            帐册序号
	 * @param version
	 *            版本号
	 * @return
	 */
	public EmsHeadH2kVersion findEmsHeadH2kVersion(Request request,
			Integer seqNum, Integer version);

	/**
	 * 报关单删单查询
	 * 
	 * @param request
	 *            请求控制
	 */
	public void brownDeleteCustoms(Request request);

	/**
	 * 根据序号查找帐册料件
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            帐册序号
	 * @return
	 */
	public EmsHeadH2kImg getEmsHeadImg(Request request, Integer seqNum);

	/**
	 * 根据序号查找帐册成品
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            帐册序号
	 * @return
	 */
	public EmsHeadH2kExg getEmsHeadExg(Request request, Integer seqNum);

	/**
	 * 根据报送单信息查询归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            归并后商品信息
	 * @return
	 */
	public List findAllAtcMergerBeforeComInfo(Request request,
			ApplyToCustomsBillList billList);

	/**
	 * 计量单折算比例位资料
	 * 
	 * @param request
	 * @return
	 */
	public Map findAllUnitRateMap(Request request);

	/**
	 * 通过帐册序号返回帐册信息
	 * 
	 * @param seqNum
	 *            序号
	 * @param type
	 *            物料类型
	 * @param emsNo
	 *            手册号
	 * @return 与指定的序号手册号匹配的帐册信息
	 */
	public Map<Integer, String> findImgExgBillBySeqNum(Request request,
			Integer seqNum, String materielType, String emsNo);

	/**
	 * 新增清单归并前
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            帐件
	 * @param exg
	 *            帐册成品
	 * @param mt
	 *            报关常用工厂物料资料
	 * @param head
	 *            报关清单
	 * @return
	 */
	public List newBillBefore(Request request, EmsHeadH2kImg img,
			EmsHeadH2kExg exg, Materiel mt, ApplyToCustomsBillList head);

	/**
	 * 料号级 料件耗用明细表
	 * 
	 * @param request
	 *            请求控制
	 * @param billlist
	 *            单据LIST
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param head
	 *            帐册归并关系表头
	 * @param version
	 *            版本号
	 * @param isEmsOrBillList
	 *            出口数量是否来源于电子帐册
	 * @param deptId
	 *            事业部ID
	 * @return
	 */
	public List getLjUseDetail(Request request, List billlist, Date beginDate,
			Date endDate, EmsEdiMergerHead head, String version,
			boolean isEmsOrBillList, String deptId);

	// /**
	// * 转清单
	// *
	// * @param request
	// * 请求控制
	// * @param billList
	// * 报关清单
	// * @param impexpbills
	// * 单据LIST
	// * @param afterinfoLists
	// * 归并后商品信息LIST
	// * @param billListcount
	// * 清单条数
	// */
	// public void makeBillList(Request request, ApplyToCustomsBillList
	// billList,
	// List impexpbills, List afterinfoLists, int billListcount);

	/**
	 * 查找 报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param billState
	 *            清单状态
	 * @return
	 */
	public List findApplyToCustomsBillListByFlatAndState(Request request,
			int impExpFlag, int billState);

	/**
	 * 查找 报关清单表体－归并后
	 * 
	 * @param request
	 *            请求控制
	 * @param listNo
	 *            内部清单号码
	 * @return
	 */
	public List findAtcMergeAfterComInfoByBillNo(Request request, String listNo);

	/**
	 * 查找 报关清单表体－归并前
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            内部清单号码
	 * @return
	 */
	public List findAllAtcMergerBeforeComInfo(Request request, String billNo);

	/**
	 * 重新组合
	 * 
	 * @param request
	 *            请求控制
	 * @param after
	 *            报关清单归并后商品信息
	 * @param billNo
	 *            内部清单号码
	 */
	public void addBillToBill(Request request, AtcMergeAfterComInfo after,
			String billNo);

	/**
	 * 重新排序
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            内部清单号码
	 */
	public void billSort(Request request, String billNo);

	/**
	 * 查找进出口申请单导入的顺序
	 * 
	 * @param request
	 *            请求控制
	 * @return
	 */
	List findInputInExRequestBillOrder(Request request);

	/**
	 * 保存进出口申请单导入的顺序
	 * 
	 * @param request
	 *            请求控制
	 * @param order
	 *            进出口申请单顺序
	 */
	void saveInputInExRequestBillOrder(Request request,
			InputInExRequestBillOrder order);

	/**
	 * 根据料号查找进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param billType
	 *            清单类型
	 * @param billNo
	 *            内部清单号码
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findImpExpCommodityInfoByMaterielptNo(Request request,
			int billType, String billNo, String ptNo);

	/**
	 * 检查是否为空
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            报关清单
	 * @return
	 */
	public List checkIsNull(Request request, ApplyToCustomsBillList head);

	/**
	 * 分事业部统计－总
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @param isDeclaration
	 *            是否分分事业部统计
	 * @param deptCode
	 *            事业部ID
	 * @return
	 */
	public List jisuanImgBalanceTotalForDept(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration,
			List<String> deptCode);

	/**
	 * 分事业部统计
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @param isDeclaration
	 *            是否分分事业部统计
	 * @param deptCode
	 *            事业部ID
	 * @return
	 */
	public List jisuanEmsImpExpBalanceForDept(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration, String deptCode);

	/**
	 * 分事业部处理
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @param projectDept
	 *            事业部
	 * @return
	 */
	public List jisuanEmsExpExgForDept(Request request, Date beginDate,
			Date endDate, Boolean isEffect, String projectDept);

	/**
	 * 新增清单归并前来自内部商品
	 * 
	 * @param request
	 *            请求控制
	 * @param before
	 *            报关清单归并前商品信息
	 * @param img
	 *            帐册料件
	 * @param exg
	 *            帐册成品
	 * @param head
	 *            报关清单
	 * @return
	 */
	public List newBillBeforeFromMateriel(Request request,
			AtcMergeBeforeComInfo before, EmsHeadH2kImg img, EmsHeadH2kExg exg,
			ApplyToCustomsBillList head);

	/**
	 * 保存申请单表头及表体,文本导入时使用.
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            进出口申请单list
	 * @param isMergeCommInfo
	 *            是否将相同料号相同产销国的商品数量累计
	 * @return
	 */
	public List saveImpExpRequestBillAndImpExpCommodityInfo(Request request,
			List list, Boolean isMergeCommInfo,Boolean isSumMoney);

	/**
	 * 出口成品耗用料件统计表
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeadH2k
	 *            电子帐册
	 * @param exgSeqNums
	 *            帐册成品序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param impExpType
	 *            进出口类型
	 * @param isEffective 生效
	 * @return
	 */

	List findExgUsedImgAmountInfo(Request request, EmsHeadH2k emsHeadH2k,
			Integer[] exgSeqNums, Date beginDate, Date endDate,
			Integer impExpType,boolean isPrintZeroTotalUsed,Boolean isEffective);

	/**
	 * 找出帐册最早的月结期间
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeadH2k
	 *            电子帐册
	 */
	String findInitCustomsMonthTerm(Request request, EmsHeadH2k emsHeadH2k);

	/**
	 * 进口料件月结
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeadH2k
	 *            电子帐册
	 * @param preTerm
	 *            月结期间
	 * @param beginTerm
	 *            开始日期
	 * @param endTerm
	 *            结束日期
	 */
	void statCustomsInfoByMonth(Request request, EmsHeadH2k emsHeadH2k,
			String preTerm, String beginTerm, String endTerm);

	/**
	 * 查询帐册月结信息
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册号
	 * @param term
	 *            月结期间
	 * @return
	 */
	List findCustomsMonthStatInfoByTerm(Request request, String emsNo,
			String term);

	/**
	 * 查询帐册月结信息
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册号
	 * @return
	 */
	List findCustomsMonthStatTerm(Request request, String emsNo);

	/**
	 * 出口成品耗用料件统计月报表
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param exgSeqNums
	 *            帐册成品序号
	 * @param term
	 *            月结期间
	 * @param impExpType
	 *            进出口类型
	 * @return
	 */
	List findExgUsedImgMonthAmountInfo(Request request, EmsHeadH2k emsHeadH2k,
			Integer[] exgSeqNums, String term, Integer impExpType);

	/**
	 * 保存拆分后的清单
	 * 
	 * @param request
	 *            请求控制
	 *@param oldBill
	 *            申请单物料
	 * @param set
	 *            单据号set
	 * @param list
	 *            拆分后的清单
	 */
	boolean saveSplitedImpExpRequestBill(Request request,
			ImpExpRequestBill oldBill, Set set, List list);

	/**
	 * 更新电子帐册中的申请单物料的版本号，为最大的版本号
	 * 
	 * @param request
	 */
	void updateMaxVersion(Request request);

	/**
	 * 获得报关清单导入的属性设置
	 * 
	 * @param request
	 *            请求控制
	 */
	List findImportApplyToCustomsBillProperty(Request request);

	/**
	 * 保存报关清单导入的属性设置
	 * 
	 * @param request
	 *            请求控制
	 * @param importApplyProperty
	 *            导入报关清单的一些属性
	 */
	ImportApplyCustomsProperty saveImportApplyToCustomsBillProperty(
			Request request, ImportApplyCustomsProperty importApplyProperty);

	/**
	 * 检查导入到清单的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            在文件获取数据后的List
	 * @param importApplyProperty
	 *            参数和列顺序
	 * @return
	 */
	List checkImportFileData(Request request, List list,
			ImportApplyCustomsProperty importApplyProperty);

	/**
	 * 导入文件来自文件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            在文件获取数据后的List
	 * @param importApplyProperty
	 *            参数和列顺序
	 * @param cbIsOverwrite
	 *            是否覆盖导入
	 */
	public void importDataFromFile(Request request, List list,
			ImportApplyCustomsProperty importApplyProperty,
			boolean cbIsOverwrite);

	/**
	 * 导入前先检查数据是否合法
	 * 
	 * @param request
	 *            请求控制
	 * @param importApplyProperty
	 *            参数和列顺序
	 * @param list
	 *            在文件获取数据后的List
	 * @return
	 */
	public List tempDataCheck(Request request,
			ImportApplyCustomsProperty importApplyProperty, List list);

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的料件
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kByPtNoFromMerger(Request request, String ptNo);

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kExgByPtNoFromMerger(Request request, String ptNo);

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kBomByPtNoFromMerger(Request request, String ptNo,
			Integer version);

	/**
	 * 断判导入资料中清单编号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param value
	 *            清单编号
	 * 
	 */
	public boolean checkBillListNoOverlap(Request request, String value);

	/**
	 * 根据进出类型、单据类型,查找已转清单的申请单头资料
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findImpExpRequestBillByPara(Request request,
			String impExpFlagCode, String billTypeCode);

	/**
	 * 根据进出类型、单据类型,查找客户/供应商/合作伙伴资料
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findScmCocsByPara(Request request, String impExpFlagCode,
			String billTypeCode);

	/**
	 * 根据进出类型、单据类型,查找申请单表体
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            申请单单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            开始生效日期
	 * @param endDate
	 *            结束生效日期
	 * @return
	 */
	public List findImpExpCommodityInfoBySomePara(Request request,
			String impExpFlagCode, String billTypeCode,
			ImpExpRequestBill impExpRequestBill, String bomNo, ScmCoc scmCoc,
			Date beginDate, Date endDate);

	/**
	 * 根据进出类型、单据类型,查找表体名称、表体料号的申请单表体
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            申请单单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            开始生效日期
	 * @param endDate
	 *            结束生效日期
	 * @return
	 */
	public List getDistinctImpExpCommodityInfoByName(Request request,
			String impExpFlagCode, String billTypeCode,
			ImpExpRequestBill impExpRequestBill, String bomNo, ScmCoc scmCoc,
			Date beginDate, Date endDate);

	/**
	 * 根据条件获取大小清单资料
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlagCode
	 *            进出类型
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            生效开始日期
	 * @param endDate
	 *            生效结束日期
	 * @param IsListUnitNo
	 *            是否按统一编号查询，因为有些企业走QP，通过QP下载报关单
	 * @param customDeclareCode 报关单号
	 * @param commSerial 商品序号
	 * @return
	 */
	public List getRequestTOApplyTOCustomsReport(Request request,
			String impExpFlagCode, String billTypeCode,
			ImpExpRequestBill impExpRequestBill, String bomNo, ScmCoc scmCoc,
			Date beginDate, Date endDate, boolean IsListUnitNo, String customDeclareCode, String commSerial);

	/**
	 * 查找清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlagCode
	 *            进出类型
	 * @param billTypeCode
	 *            单据类型
	 * @param beginDate
	 *            生效开始日期
	 * @param endDate
	 *            生效结束日期
	 * @param IsListUnitNo
	 *            是否按统一编号查询，因为有些企业走QP，通过QP下载报关单
	 * @return
	 */
	public List getApplyReportTOCustomsReport(Request request,
			String impExpFlagCode, String billTypeCode, Date beginDate,
			Date endDate, boolean IsListUnitNo);

	/**
	 * 申请单转报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @param para
	 *            根据报关清单自动生成报关单时的参数
	 * @param customs
	 *            报关单
	 * @param billDetail
	 *            归并后商品信息
	 * @param arrayType
	 *            是否按类型排序
	 * @return
	 */
	public CustomsDeclaration makeBilllistsToCustomDeclaretions(
			Request request, ApplyToCustomsBillList billList,
			MakeCusomsDeclarationParam para, CustomsDeclaration customs,
			List billDetail, boolean arrayType);

	/**
	 * 根据清单表头查找表体
	 * 
	 * @param request
	 *            请求控制
	 * @param listNo
	 *            报关清单List
	 * @return
	 */
	public List findAtcMergeAfterComInfoByParents(Request request, List listNo);

	/**
	 * 检查清单
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            清单表头list
	 * @return
	 */
	public List applyToCustomsCheckup(Request request, List list);

	/**
	 * 根据单据类型、录入日期查找申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @param beginImputDate
	 *            开始录入日期
	 * @param endImputDate
	 *            结束录入日期
	 * @return 与条件匹配的所有进出口申请单
	 */
	public List findImpExpRequestBillByTypeAndImputDate(Request request,
			int type, Date beginImputDate, Date endImputDate);

	/**
	 * 转抄申请单资料
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBill
	 *            要转抄的申请单表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            单据号
	 * @return 新的申请单表头
	 */
	public ImpExpRequestBill copyImpExpRequestBillAndCommodityInfo(
			Request request, ImpExpRequestBill impExpRequestBill,
			Boolean copyInfo, String billNo);

	/**
	 * 转抄清单资料
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            要转抄的清单表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @return 新的清单表头
	 */
	public ApplyToCustomsBillList copyApplyToCustomsBillListAndCommInfo(
			Request request, ApplyToCustomsBillList applyToCustomsBillList,
			Boolean copyInfo);

	/**
	 * 获得各个模块的归并资料
	 * 
	 * @param request
	 *            请求控制
	 * @param projectType
	 *            模块信息
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * 
	 * @return 查询归并中的设备资料
	 */
	public List getInnerDateForSpeFix(Request request, int projectType,
			int index, int length, String property, Object value,
			boolean isLike, boolean isImport);

	/**
	 * 进出口包装统计
	 * 
	 * @param request
	 *            请求控制
	 * @param wrap
	 *            包装种类
	 * @param impExpFlag
	 *            进出口标志
	 * @param effective
	 *            是否生效
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findWrapStat(Request request, Wrap wrap, Integer impExpFlag,
			Boolean effective, Date beginDate, Date endDate);

	/**
	 * 根据报关单表体查找清单归并后表休
	 * 
	 * @param request
	 *            请求控制
	 * @param customsInfo
	 *            报关单物料信息
	 * @return
	 */
	public AtcMergeAfterComInfo findMakeListToCustomsBycustomsInfo(
			Request request, CustomsDeclarationCommInfo customsInfo);

	/**
	 * 查找清单商品总项数
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            申请单物料
	 * @return
	 */
	public Integer findMakeBcsCustomsDeclarationCountByListId(Request request,
			List list);

	/**
	 * 查找归并关系所有版本号
	 * 
	 * @param request
	 *            请求控制
	 * @return
	 */
	List findEmsEdiExgBomVersion(Request request);

	/**
	 * 获取XinpuReport数据
	 * 
	 * @return
	 */
	public XinpuReport getXinpuReport();

	/**
	 *保存XinpuReport数据
	 * 
	 * @param xinpuReport
	 */
	public void saveXinpuReport(XinpuReport xinpuReport);

	/**
	 * 抓取报关单某项商品的合同定量,合同余量,当前余量
	 * 
	 * @param request
	 *            请求控制
	 * @param isMaterial
	 *            true代表料件，false代表成品
	 * @param impExpType
	 *            单据类型
	 * @param tradeCode
	 *            贸易方式编码
	 * @param emsHead
	 *            合同备案表头
	 * @param seqNum
	 *            物料序号
	 * @return BcsContractExeInfo 存放合同成品的合同定量、合同余量、当前余量资料
	 */
	BcusContractExeInfo findBcusEmsHeadH2kExeInfo(Request request,
			boolean isMaterial, int impExpType, String tradeCode,
			EmsHeadH2k emsHead, String seqNum);

	/**
	 * 统计电子帐册财务报表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param mark
	 * @return
	 */
	public List calcCustomsDeclarationCommInfoAsFinancial(Request request,
			Date beginDate, Date endDate, String mark);

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBatchUpdateComplex(Request request, String emsType,
			Boolean isMaterial);

	/**
	 * 更新电子帐册的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllEmsComplex(Request request, Boolean isMaterial,
			Complex complex, Integer seqNum, boolean isSendData);

	/**
	 * 更新所有归并关系的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllEdiMergerAfterComplex(Request request,
			Boolean isMaterial, Complex complex, Complex old, Integer seqNum,
			boolean isSendData);

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllInnerMergeDataComplex(Request request,
			Boolean isMaterial,Complex complex, Integer seqNum);

	/**
	 * 海关申报
	 * 
	 * @param request
	 *            请求控制
	 */
	void customDeclare(Request request);
	
	/**
	 * 根据申请单表头ImpExpRequestBill查找清单中商品信息
	 * @return
	 */
	public List findTempImpExpCommodityInfoByBillListId(Request request,ImpExpRequestBill impExpRequestBill,
			Integer billType);
	
	/**
	 * 根据已转清单号码查找进出口申请单
	 */
	public List findImpExpRequestBillByAllBillNo(Request request, String allBillNo,
			Integer billType);
	/**
	 * 根据报关单物料信息获取进出口申请单表体资料
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType 单据类型
	 * @return
	 */
	public List findTempImpExpCommodityInfoBycustomsInfo(Request request, CustomsDeclarationCommInfo customsDeclarationCommInfo,Integer type);
	
	/**
	 * 根据报关单物料信息获取进出口申请单表体资料
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType 单据类型
	 * @return
	 */
	public List findTempImpExpCommodityInfoBycustomsInfo(Request request, BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo,Integer type);
	
	/**
	 * 根据报关单物料信息获得归并前商品信息
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType 单据类型
	 * @return
	 */
	public List findAtcMergeBeforeComInfoByCustomsDeclarationCommInfo(Request request,
			CustomsDeclarationCommInfo customsDeclarationCommInfo,
			Integer billType);
	
	/**
	 * 根据条件（进出口类型，账册号，开始时间，结束时间，自选条件）取得报关单BY 申报日期
	 * @param request
	 * 
	 * @param impExpFlag
	 *            进出口标志，进口为true，出口为false。
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param property
	 *            自选条件名
	 * @param value
	 *            自选条件值
	 * @return 在有效期内与指定的手册号匹配的进口报关单
	 */
	public List findCustomsDeclaration(Request request, boolean impExpFlag, String emsNo, Date beginDate,
			Date endDate, String property, Object value);
	/**
	 * 更新电子帐册中的报关单商品的版本号，为最大的版本号,如果没有最大版本号，默认为0
	 *  @param baseCustomsDeclaration 报关单表头
	 */
	public void updateMaxVersionCustomsDeclarationCommInfo(Request request,BaseCustomsDeclaration customsDeclaration);
	
	/**
	 * 生成报关清单(广达订制) 
	 * @param request
	 * @param dataSource
	 * @param isMaterial
	 * @param param
	 * @return
	 */
	public List makeApplyToCustomsRequestBillListForGUANGDA(Request request,
			List dataSource, boolean isMaterial,
			MakeCusomsDeclarationParam param);
	
	/**
	 * 申清单---清单--生成报关单 生成清单(广达订制) 
	 * 
	 * @param request
	 * @param impexpbills
	 * @param afterinfoLists
	 * @param param
	 * @param customs
	 *            报关单表头
	 * @return
	 */
	public List makeCusomsDeclarationFromBillListGUANGDA(Request request,List impexpbills,
			List afterinfoLists, MakeCusomsDeclarationParam param,
			CustomsDeclaration customs);
	/**
	 * 根据提运单号获取司机表中的信息
	 * @param billOfLading
	 * @return
	 */
	public List findMotorCode(String billOfLading);
	
	/**
	 * 查询料件耗用明细
	 * @param request
	 * @param beginDate 报关单开始日期
	 * @param endDate 报关单结束日期
	 * @param projectDept 事业部
	 * @param imgSeqNums 料件序号
	 * @param isTotal 是否统计
	 * @return
	 */
	public List findImgConsumption(Request request,Date beginDate,Date endDate,ProjectDept projectDept,Integer[] imgSeqNums,Integer impExpType,Boolean isTotal);
}