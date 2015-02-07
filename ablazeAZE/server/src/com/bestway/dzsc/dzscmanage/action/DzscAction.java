/*
 * Created on 2004-10-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.action;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.BcsCommdityForbid;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscCommdityForbid;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHeadFas;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialExg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialImg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.dzscmanage.entity.TempDzscCommMoneyAmountInfo;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsImgBill;


/**
 * com.bestway.dzsc.dzscmanage.action.DzscAction
 * 
 * @author yp
 * 
 */
public interface DzscAction {
	Object load(Class cls, String id);

	/**
	 * 查找电子手册的物料基本资料，不在进出口申请单里，并把得到的结果加到临时物料基本资料里
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类别属性
	 * @param impExpRequestBillId
	 *            进出口申请单头Id
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是tempMateriel型，电子手册的临时物料基本资料
	 */
	List findMaterielByImpExpRequestBillType(Request request,
			String materialType, String impExpRequestBillId, int index,
			int length, String property, Object value, boolean isLike,
			boolean isFilter);

	/**
	 * 查找自用商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Complex，自用商品编码
	 */
	List findComplexForInnerMerge(Request request);

	/**
	 * 根据手册号来抓取合同备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	DzscEmsPorWjHead findExingDzscEmsPorWjHeadByEmsNo(Request request,
			String emsNo);

	/**
	 * 获取className里最大的流水号
	 * 
	 * @param request
	 *            请求控制
	 * @param className
	 *            表名
	 * @param seqNum
	 *            流水号
	 * @return Sting className里最大的流水号
	 */
	String getNum(Request request, String className, String seqNum);

	/**
	 * 将合同备案成品表示为删除标志
	 * 
	 * @param obj
	 */
	DzscEmsImgWj markDeleteDzscEmsImgWj(Request request, DzscEmsImgWj obj);

	/**
	 * 将合同备案料件表示为删除标志
	 * 
	 * @param obj
	 */
	DzscEmsExgWj markDeleteDzscEmsExgWj(Request request, DzscEmsExgWj obj);

	/**
	 * 改变备案资料库表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	DzscEmsPorWjHead changeDictPorHeadDeclareState(Request request,
			DzscEmsPorWjHead head, String declareState);

	/**
	 * 改变备案资料库料件成品的修改标志
	 * 
	 * @param head
	 * @param declareState
	 */
	void changeDictPorImgExgModifyMark(Request request, List list,
			String modifyMark);

	/**
	 * 修改通关手册单耗的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	void changeContractBomModifyMark(Request request, List list,
			String modifyMark);

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	void changeContractImgModifyMark(Request request, List list,
			String modifyMark);

	/**
	 * 修改通关手册成品的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	void changeContractExgModifyMark(Request request, List list,
			String modifyMark);

	/**
	 * 判断手册号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	boolean checkEmsPorWjHeadDuple(Request request, DzscEmsPorWjHead head);

	/**
	 * 判断手册号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	boolean checkDzscEmsPorHeadDuple(Request request, DzscEmsPorHead head);

	/**
	 * 判断通关备案内部编号号是否重复
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	boolean checkDzscEmsPorCopEmsNoDuple(Request request, DzscEmsPorHead head);

	/**
	 * 判断通关备案企业内部物料编号是否存在
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	boolean checkDzscEmsPorCopEntNoIsExist(Request request, DzscEmsPorHead head);

	/**
	 * 查找通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorHead1(Request request, boolean isCancel);

	/**
	 * 新增通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return DzscEmsPorHead 通关备案表头
	 */
	DzscEmsPorHead newDzscEmsPorHead(Request request);

	/**
	 * 申报通关备案表头（申报中）
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	DeclareFileInfo applyDzscEmsPorHead(Request request, DzscEmsPorHead head);

	// String findDmIsNullSeqNum(Request request, DzscEmsPorHead head);
	//
	// String findExgIsNullSeqNum(Request request, DzscEmsPorHead head);
	//
	// String findImgIsNullSeqNum(Request request, DzscEmsPorHead head);

	/**
	 * 保存通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案表头
	 */
	DzscEmsPorHead saveDzscEmsPorHead(Request request, DzscEmsPorHead obj);

	// List findEmsPorExg(Request request, DzscEmsPorHead head);
	//
	// List findEmsPorImg(Request request, DzscEmsPorHead head);

	/**
	 * 返回电子手册归并四码
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param dzscEmsPorHead
	 *            通关备案表头
	 * @return List 存放通关备案料件的属性
	 */
	List findMerger4(Request request, String type, DzscEmsPorHead dzscEmsPorHead);

	// DzscEmsPorImg saveDzscEmsPorImg(Request request, DzscEmsPorImg
	// img)
	// throws DataAccessException;
	//
	// DzscEmsPorExg saveDzscEmsPorExg(Request request, DzscEmsPorExg
	// exg)
	// throws DataAccessException;

	/**
	 * 变更通关备案
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHead
	 *            通关备案表头
	 * @param isChange
	 *            是否为变跟状态，true为变更状态
	 * @return DzscEmsPorHead 通关备案表头
	 */
	DzscEmsPorHead dzscEmsPorHeadChange(Request request,
			DzscEmsPorHead emsHead, boolean isChange);

	/**
	 * 返回通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	List findEmsPorImgBill(Request request, DzscEmsPorHead head);
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	List findEmsPorImgBillModifyMark(Request request, DzscEmsPorHead head,String modifyMark);
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	List findEmsPorExgBillModifyMark(Request request, DzscEmsPorHead head,String modifyMark);


	/**
	 * 保存通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案料件资料
	 */
	DzscEmsImgBill saveDzscEmsImgBill(Request request, DzscEmsImgBill obj);

	/**
	 * 返回通关备案成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品资料
	 */
	List findEmsPorExgBill(Request request, DzscEmsPorHead head);

	/**
	 * 保存通关备案成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案成品资料
	 */
	List saveDzscEmsExgBill(Request request, DzscEmsExgBill obj);

	/**
	 * 返回通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param exg
	 *            通关备案成品资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	List findEmsPorBomBill(Request request, DzscEmsExgBill exg);

	/**
	 * 进口金额合计
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return Double 手册进口金额合计
	 */
	Double sumMoney(Request request, DzscEmsPorHead head);

	/**
	 * 查找新增的通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param exg
	 *            通关备案成品资料
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	List findEmsPorImgBillToBill(Request request, DzscEmsExgBill exg);

	/**
	 * 保存通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bom
	 *            通关备案BOM资料
	 */
	DzscEmsBomBill saveDzscEmsBomBill(Request request, DzscEmsBomBill bom);

	// Integer getNumForImgExgBill(Request request, String className,
	// DzscEmsPorHead head);

	// Integer getNumForBomBill(Request request, String className,
	// DzscEmsExgBill exg);

	// /**
	// * 获取符合条件的前四位商品编码
	// *
	// * @param request
	// * 请求控制
	// * @param code
	// * @param type
	// * 物料类型
	// * @return List 是DzscInnerMergeData型，归并数据
	// */
	// List findFourComplex(Request request, String code, String type);

	/**
	 * 使用该料件的成品列表
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            通关备案料件资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	List findExgBillForImg(Request request, DzscEmsImgBill img);

	/**
	 * 删除通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案料件资料
	 */
	Map<Integer, List<DzscEmsImgBill>> deleteDzscEmsImgBill(Request request,
			List lsDzscEmsImgBill);

	/**
	 * 查找BOM中是否存在该料件
	 * @param request
	 * @param lsDzscEmsImgBill
	 * @return
	 */
	List findDzscEmsBomBill (Request request,
			List lsDzscEmsImgBill);
	/**
	 * 删除通关备案成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案成品资料
	 */
	Map<Integer, List<DzscEmsExgBill>> deleteDzscEmsExgBill(Request request,
			List lsDzscEmsExgBill);

	/**
	 * 判断在通关备案BOM资料中是否存在通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            通关备案料件资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	List findImgFromBom(Request request, DzscEmsImgBill img);

	/**
	 * 删除通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案BOM资料
	 */
	Map<Integer, List<DzscEmsBomBill>> deleteDzscEmsBomBill(Request request,
			List list);

	/**
	 * 检查是否重复变更
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @param state
	 *            申报状态
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findIsRepeatChange(Request request, DzscEmsPorHead head, String state);

	/**
	 * 删除合同备案
	 * 
	 * @param request
	 *            请求控制
	 */
	void deleteDzscEmsPorHead(Request request, DzscEmsPorHead head);

	/**
	 * 返回电子手册合同里的料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	List findEmsPorImgWj(Request request, DzscEmsPorWjHead head);

	/**
	 * 返回合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgWj型，合同备案成品
	 */
	List findEmsPorExgWj(Request request, DzscEmsPorWjHead head);

	/**
	 * 返回电子手册合同里的料件项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	int findEmsPorImgWjCount(Request request, DzscEmsPorWjHead head);

	/**
	 * 返回电子手册合同里的成品项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	int findEmsPorExgWjCount(Request request, DzscEmsPorWjHead head);

	/**
	 * 保存合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案表头
	 * @return obj 是DzscEmsPorWjHead型，合同备案表头
	 */
	DzscEmsPorWjHead saveEmsPorWj(Request request, DzscEmsPorWjHead obj);

	/**
	 * 保存合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案表头
	 * @return obj 是DzscEmsPorWjHead型，合同备案表头
	 */
	DzscEmsPorWjHead saveEmsPorWj(Request request, DzscEmsPorWjHead obj,String tfCopTrNoOld);
	/**
	 * 返回电子手册归并4码
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param dzscEmsPorWjHead
	 *            通关备案表头
	 * @return List 存放电子手册商品归并资料的第三层资料（四位编码序号、四位编码名称、四位商品规格、四位商品单位、四位商品编码）
	 * 
	 */
	List findMerger4ToWj(Request request, String type,
			DzscEmsPorWjHead dzscEmsPorWjHead);

	// Integer getNumForImgExgWj(Request request, String className,
	// DzscEmsPorWjHead head);

	/**
	 * 保存合同备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案料件
	 * @return obj 合同备案料件
	 */
	DzscEmsImgWj saveDzscEmsImgWj(Request request, DzscEmsImgWj obj);

	/**
	 * 保存合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案成品
	 * @return obj 合同备案成品
	 */
	DzscEmsExgWj saveDzscEmsExgWj(Request request, DzscEmsExgWj obj);

	// DzscEmsBomWj saveDzscEmsBomWj(Request request, DzscEmsBomWj obj);

	// List findExgWjForImg(Request request, DzscEmsImgWj img);

	// List findImgFromBomWj(Request request, DzscEmsImgWj img);

	/**
	 * 删除合同备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案料件
	 */
	void deleteDzscEmsImgWj(Request request, DzscEmsImgWj obj);

	/**
	 * 删除合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案成品
	 */
	void deleteDzscEmsExgWj(Request request, DzscEmsExgWj obj);

	/**
	 * 新增合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return DzscEmsPorWjHead 合同备案表头
	 */
	DzscEmsPorWjHead newEmsPorWjHead(Request request);

	/**
	 * 申报合同备案表头（申报中）
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同备案表头
	 * @return DzscEmsPorWjHead 合同备案表头
	 */
	DeclareFileInfo applyEmsPorWjHead(Request request, DzscEmsPorWjHead head);

	/**
	 * 删除合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同备案表头
	 */
	void deleteAllEmsPorWj(Request request, DzscEmsPorWjHead head);

	/**
	 * 删除合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同备案表头
	 */
	void deleteDzscEmsPorWjHead(Request request, DzscEmsPorWjHead head);

	/**
	 * 检查是否重复变更 -- 合同备案
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @param state
	 *            申报状态
	 * @return List 是DzscEmsPorWjHead型，通关备案表头
	 */
	List findIsRepeatChangeWj(Request request, DzscEmsPorWjHead head,
			String state);

	/**
	 * 合同备案变更
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHead
	 *            合同备案表头
	 * @param isChange
	 *            判断变更状态，true为变更状态
	 * @return DzscEmsPorWjHead 变更后合同备案表头
	 */
	DzscEmsPorWjHead emsPorChangeWj(Request request, DzscEmsPorWjHead emsHead,
			boolean isChange);

	/**
	 * 从内部归并中抓取通关备案需要的数据,经过合同备案4码的过滤
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            是否为料件，true时为料件
	 * @return List 是TempDzscCommDataForEmsPor型，类型存放十码和四码的资料
	 */
	List findMerger10ForDzscEmsPor(Request request, DzscEmsPorHead head,
			boolean isMaterial);

	/**
	 * 根据查询得到的通关备案需要的数据，生成通关备案商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            是否为料件，true时为料件
	 * @param list
	 *            是TempDzscCommDataForEmsPor型，类型存放十码和四码的资料
	 * @return List 是DzscEmsImgBill或DzscEmsExgBill型，通关备案料件、成品
	 */
	List saveDzscEmsPorImgExgBill(Request request, DzscEmsPorHead head,
			boolean isMaterial, List list);

	/**
	 * 根据查询得到的通关备案需要的数据，生成通关备案商品信息
	 * 
	 * @param head
	 *            通关备案表头
	 * @param isMaterial
	 *            是否为料件，true时为料件
	 * @param list
	 *            是TempDzscCommDataForEmsPor型，类型存放十码和四码的资料
	 * @return List 是DzscEmsImgBill或DzscEmsExgBill型，通关备案料件、成品
	 */
	List saveDzscEmsPorImgExgByComplex(Request request, DzscEmsPorHead head,
			boolean isMaterial, List list);

	/**
	 * 生效合同备案
	 * 
	 * @param request
	 *            请求控制
	 * @param wjHead
	 *            合同备案表头
	 * @param wjExingHead
	 *            变更后合同备案表头
	 */
	String processDzscEmsPorWjHead(Request request, DzscEmsPorWjHead wjHead,
			DzscEmsPorWjHead wjExingHead, List lsReturnFile);

	/**
	 * 处理通关备案回执
	 * 
	 * @param billHead
	 * @param billExingHead
	 * @return
	 */
	String processDzscEmsPorBillHead(Request request, DzscEmsPorHead billHead,
			DzscEmsPorHead billExingHead, List lsReturnFile);

	/**
	 * 查询合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	List findDzscEmsPorWjHead(Request request, boolean isCancel);

	/**
	 * 查询合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	DzscEmsPorWjHead findDzscEmsPorWjHeadById(Request request, String id);

	/**
	 * 获取正在执行的通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findExeEmsPorHead(Request request);

	/**
	 * 返回通关备案料件进口金额
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            通关备案料件
	 * @param head
	 *            通关备案表头
	 * @return Double 进口金额
	 */
	Double findEmsPorImgBillMoney(Request request, DzscEmsImgBill img,
			DzscEmsPorHead head);

	/**
	 * 返回通关备案料件进口金额
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 */
	Double findEmsPorImgBillMoney(Request request, DzscEmsPorHead head);

	/**
	 * 查找对应表的资料，根据code
	 * 
	 * @param request
	 *            请求控制
	 * @param className
	 *            对应表
	 * @param code
	 * @return List 是对应表型
	 */
	List findCustomObject(Request request, String className, String code);

	// List findMergerTenByFourSeqNum(Request request, String type,
	// String fourSeqNum);

	// /**
	// * 修改通关备案表体标记
	// *
	// * @param request
	// * 请求控制
	// * @param obj
	// * 通关备案表头
	// */
	// void editModify(Request request, DzscEmsPorHead obj);

	/**
	 * 获得最大的合同流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的合同流水号
	 */
	String getMaxPreContractCodeSuffixByWJ(Request request);

	/**
	 * 获得最大的手册流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的手册流水号
	 */
	String getMaxPreContractCodeSuffixByCustom(Request request);

	/**
	 * 电子手册基通关备案表头--变更状态
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorHeadChange(Request request);

	// /**
	// * 在通关备案里新增10位编码
	// *
	// * @param request
	// * 请求控制
	// * @param head
	// * 通关备案表头
	// * @param type
	// * 物料类型
	// * @return DzscEmsImgBill或DzscEmsExgBill型，通关备案料件、成品
	// */
	// List findMerger10ForEmsPor(Request request, DzscEmsPorHead head, String
	// type);

	// /**
	// * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组 来自外经合同
	// *
	// * @param index
	// * @param length
	// * @return
	// */
	// List<ContractUnitWaste> findContractUnitWasteByWJ(Request request,
	// String contractId, int index, int length);

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组 来自通关备案
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            通关备案通关备案表头
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @return List 是ContractUnitWaste型，合同单耗
	 */
	List<ContractUnitWaste> findContractUnitWasteByCustom(Request request,
			String contractId, int index, int length);

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组 来自通关备案
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            通关备案通关备案表头
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @return List 是ContractUnitWaste型，合同单耗
	 */
	List<ContractUnitWaste> findContractUnitWasteByCustomForEdit(
			Request request, String contractId, int index, int length);

	/**
	 * 当前通关备案成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	int findDzscEmsExgBillCount(Request request, String contractId);

	/**
	 * 当前合同备案成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同备案成品记录总数
	 */
	int findDzscEmsExgWjCount(Request request, String contractId);

	/**
	 * 电子手册基通关备案表头--执行状态
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorHeadExcu(Request request);

	/**
	 * 电子手册基通关备案表头--执行状态
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorHeadExcu(Request request, String emsNo);

	/**
	 * "0":不处理表头 ，"1":插入新表头
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            手册编号
	 * @return DzscEmsPorHead 通关备案表头
	 */
	DzscEmsPorHead makeDzscHead(Request request, String seqNum);

	/**
	 * 获取流水号，当存在时就返回，不存在时就新建一个
	 * 
	 * @param request
	 *            请求控制
	 * @param ediNo
	 *            手册编号
	 * @return string DzscEmsPorHead的流水号
	 */
	String getSeqNum(Request request, String ediNo);

	/**
	 * 删除非正在执行的通关备案表头By EdiNo
	 * 
	 * @param request
	 *            请求控制
	 * @param ediNo
	 *            手册编号
	 */
	void DeleteByEdiNoNotExcu(Request request, String ediNo);

	/**
	 * 根据申报状态查找通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscState
	 *            申报状态
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorHead(Request request, String dzscState);

	/**
	 * 查找通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	DzscEmsPorHead findDzscEmsPorHeadById(Request request, String id);

	/**
	 * 转抄通关备案成品料件单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsExgBill型，通关备案成品
	 * @param head
	 *            通关备案表头
	 * @param isProductAmountToZero
	 *            成品出口数量是否要设为零，true为设为零
	 * @param isProductPriceToZero
	 *            成品单价是否要设为零，true为设为零
	 * @param isMaterielAmountToZero
	 *            料件进口数量是否要设为零，true为设为零
	 * @param isMaterielPriceToZero
	 *            料件单价是否要设为零，true为设为零
	 * @param isUnitWasteToZero
	 *            单耗是否要设为零，true为设为零
	 */
	void copyProductMaterielUnitWaste(Request request,
			List<DzscEmsExgBill> list, DzscEmsPorHead head,
			boolean isProductAmountToZero, boolean isProductPriceToZero,
			boolean isMaterielAmountToZero, boolean isMaterielPriceToZero,
			boolean isUnitWasteToZero);

	/**
	 * 转抄通关备案成品料件单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsExgBill型，通关备案成品
	 * @param head
	 *            通关备案表头
	 * @param isProductAmountToZero
	 *            成品出口数量是否要设为零，true为设为零
	 * @param isProductPriceToZero
	 *            成品单价是否要设为零，true为设为零
	 */
	void copyProduct(Request request, List<DzscEmsExgBill> list,
			DzscEmsPorHead head, boolean isProductAmountToZero,
			boolean isProductPriceToZero);

	/**
	 * 转抄通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsImgBill型，通关备案料件
	 * @param head
	 *            通关备案表头
	 * @param isMaterielAmountToZero
	 *            料件进口数量是否要设为零，true为设为零
	 * @param isMaterielPriceToZero
	 *            料件单价是否要设为零，true为设为零
	 */
	void copyMateiel(Request request, List<DzscEmsImgBill> list,
			DzscEmsPorHead head, boolean isMaterielAmountToZero,
			boolean isMaterielPriceToZero);

	/**
	 * 返回归并关系四码distinct--外经新增上方2005-8-25
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 存放电子手册商品归并资料第三层资料的临时实体类
	 */
	List findMerger4Distinct(Request request, String type);

	/**
	 * 返回归并关系十码distinct--外经新增下方2005-8-25
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 存放电子手册商品归并资料第二层资料的临时实体类
	 */
	List findMerger10DistinceToTemp(Request request, String type,
			String fourSeqNum);

	/**
	 * 保存合同备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	List saveWjImg(Request request, List list, DzscEmsPorWjHead head);

	/**
	 * 保存合同备案料件
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	List saveWjImgFromComplex(Request request, List list, DzscEmsPorWjHead head);

	/**
	 * 保存合同备案成品
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	List saveWjExgFromComplex(Request request, List list, DzscEmsPorWjHead head);

	/**
	 * 保存合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsExgWj型，合同备案成品
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsExgWj型，合同备案成品
	 */
	List saveWjExg(Request request, List list, DzscEmsPorWjHead head);

	/**
	 * 保存通关备案BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsBomBill型，保存通关备案BOM
	 */
	void saveBomLists(Request request, List list);

	/**
	 * 保存通关备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsExgBill型，保存通关备案成品
	 */
	void saveExgLists(Request request, List list);

	/**
	 * 保存通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsImgBill型，保存通关备案料件
	 */
	void saveImgLists(Request request, List list);

	/**
	 * 只显示四码不为空的商品归并信息
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            数据的下标号
	 * @param ptNo
	 *            料号
	 * @return List 是DzscInnerMergeData型，商品归并资料
	 */
	List findMaterielFromInner(Request request, String type, int firstIndex,
			String ptNo);

	/**
	 * 通过内部件号找到清单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return List 当十位编码序号存在时返回通关备案料件或成品，否则返回Null
	 */
	List findBillByMaterielPtNo(Request request, String emsNo, String ptNo,
			String type);

	/**
	 * 返回通关备案BOM总耗用量
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return Double 总耗用量
	 */
	Double findEmsPorImgTotalNum(Request request, DzscEmsPorHead head,
			Integer imgSeqNum);

	/**
	 * 查询不在通关备案归并的料件或成品
	 * 
	 * @return
	 */
	List findInnerMergeNotInDzscEmsPor(Request request, String type,
			DzscEmsPorHead head);

	/**
	 * 新增通关备案归并料件
	 * 
	 * @param data
	 * @param list
	 */
	List addDzscEmsPorMaterialImg(Request request, DzscEmsPorHead head,
			List list);

	/**
	 * 新增通关备案归并料件
	 * 
	 * @param data
	 * @param list
	 */
	List addDzscEmsPorMaterialExg(Request request, DzscEmsPorHead head,
			List list);

	/**
	 * 删除通关备案归并料件
	 * 
	 * @param data
	 * @param list
	 */
	Map<Integer, List<DzscEmsPorMaterialImg>> deleteDzscEmsPorMaterialImg(
			Request request, List list);

	/**
	 * 删除通关备案归并成品
	 * 
	 * @param data
	 * @param list
	 */
	Map<Integer, List<DzscEmsPorMaterialExg>> deleteDzscEmsPorMaterialExg(
			Request request, List list);

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorMaterialExg(Request request,
			DzscEmsPorHead dzscEmsPorHead);

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	List findDzscEmsPorMaterialImg(Request request,
			DzscEmsPorHead dzscEmsPorHead);

	/**
	 * 根据电子手册编号查询手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHeadFas型，手册分册
	 */
	List findDzscEmsPorHeadFas(Request request, String emsNo);

	/**
	 * 根据分册内部编号、手册编号查询手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @param copEmsNo
	 *            企业内部编号
	 * @return List 是DzscEmsPorHeadFas型，手册分册
	 */
	List findDzscEmsPorHeadFasByCop(Request request, String emsNo,
			String copEmsNo);

	/**
	 * 保存电子手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 * @return dzscEmsPorHeadFas 电子手册分册
	 */
	DzscEmsPorHeadFas saveDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas dzscEmsPorHeadFas);

	/**
	 * 删除电子手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 */
	void deleteDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas dzscEmsPorHeadFas);

	/**
	 * 变更手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param fas
	 *            手册分册
	 * @return DzscEmsPorHeadFas 变更后手册分册
	 */
	DzscEmsPorHeadFas emsHeadFasChange(Request request, DzscEmsPorHeadFas fas);

	// void loadDzscInnerMerge();
	/**
	 * 根据合同号码查询合同备案进口总金额
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号码
	 * @return Double 进口总金额
	 */
	Double findWjHeadMoneyByContractNo(Request request, String contractNo);

	/**
	 * 根据通关备案的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exgBill
	 * @return
	 */
	List findMaterialExgByEmsExg(Request request, DzscEmsExgBill exgBill);

	/**
	 * 自动计算通关备案单耗
	 * 
	 * @param list
	 * @return
	 */
	List autoCalDzscEmsBomUnitWaste(Request request, List list,
			DzscEmsExgBill exgBill);

	/**
	 * 保存自动计算通关备案的单耗
	 * 
	 * @param list
	 * @param exgBill
	 */
	void saveAutoCalDzscEmsBomUnitWaste(Request request, List list,
			DzscEmsExgBill exgBill);

	// /**
	// * 从企业的BOM中导入通关备案单耗
	// *
	// * @param request
	// * 请求控制
	// * @param exgBill
	// * 通关备案成品
	// */
	// void importDzscEmsPorBomBillFromBom(Request request, DzscEmsExgBill
	// exgBill);

	/**
	 * 转抄通关备案成品单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param exgBillFrom
	 *            被拷贝的通关备案成品
	 * @param exgBillTo
	 *            通关备案成品
	 */
	void copyDzscEmsPorBomBill(Request request, DzscEmsExgBill exgBillFrom,
			DzscEmsExgBill exgBillTo);

	/**
	 * 查询当前手册料件或成品的备案或报关信息(数量和金额),以便于控制
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscEmsPorHead
	 *            通关备案通关备案表头
	 * @param isMaterial
	 *            是否为料件，true为料件
	 * @param seqNum
	 *            凭证序号
	 * @return TempDzscCommMoneyAmountInfo 临时实体类，存放料件或成品的备案或报关信息(数量和金额）
	 */
	TempDzscCommMoneyAmountInfo findDzscCurrentEmsCommInfo(Request request,
			DzscEmsPorHead dzscEmsPorHead, boolean isMaterial, Integer seqNum);
	/**
	 * 查询电子手册参数设置
	 */
	DzscParameterSet findDzscParameterSet(Request request);
	/**
	 * 电子分册申报 *
	 * 
	 * @return 生成报文的文件名。
	 */
	DeclareFileInfo applyDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas data);

	/**
	 * 电子分册回执处理 *
	 */
	String proccessDzscEmsPorHeadFas(Request request, DzscEmsPorHeadFas data,
			List lsReturnFile);

	/**
	 * 查询BOM中的料件
	 * 
	 * @param materielType
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	List findDzscEmsBomBillImg(Request request, DzscEmsPorHead dzscEmsPorHead,
			int index, int length, String property, Object value, Boolean isLike);

	DzscEmsPorHead changeDzscEmsPorHeadDeclareState(Request request,
			DzscEmsPorHead head, String declareState);

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	List findDzscEmsBomBillByImg(Request request,
			DzscEmsPorHead dzscEmsPorHead, DzscEmsImgBill img);

	/**
	 * 查询归并前归并后的BOM单耗差异
	 * 
	 * @param list
	 * @param exgBill
	 * @return
	 */
	List findDzscCustomsFactoryUnitWasteDiff(Request request, List list,
			DzscEmsExgBill exgBill);

	public List findDzscEmsExgBillBySeqNum(Request request, String emsNo,
			String seqNum);

	/**
	 * 根据通过备案头，归并序号查询通关备案成品
	 * 
	 * @param dzscEmsPorHead
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	DzscEmsExgBill findDzscEmsExgBillByTenSeqNum(Request request,
			DzscEmsPorHead dzscEmsPorHead, Integer tenSeqNum);

	/**
	 * 根据通关备案头与料号查询通关备案归并成品
	 * 
	 * @param dzscEmsPorHead
	 * @param ptno
	 * @return
	 */
	DzscEmsPorMaterialExg findDzscEmsPorMaterialExgByPtno(Request request,
			DzscEmsPorHead dzscEmsPorHead, String ptno);

	/**
	 * 保存实体类
	 * 
	 * @param obj
	 */
	void saveOrUpdate(Request request, Object obj);

	/**
	 * 根据料号查询电子手册归并
	 * 
	 * @param ptno
	 * @return
	 */
	List findDzscInnerMergeDataByptno(Request request, String ptno);

	/**
	 * 根据十位归并序号查询电子手册通关备案料件
	 * 
	 * @param dzscEmsPorHead
	 * @param Seqno
	 * @return
	 */
	DzscEmsImgBill findDzscEmsImgBillByTenSeqNum(Request request,
			DzscEmsPorHead dzscEmsPorHead, int Seqno);

	/**
	 * 根据通关备案头与料号查询通关备案归并料件
	 * 
	 * @param dzscEmsPorHead
	 * @param ptno
	 * @return
	 */
	DzscEmsPorMaterialImg findDzscEmsPorMaterialImgByPtno(Request request,
			DzscEmsPorHead dzscEmsPorHead, String ptno);

	/**
	 * 根据通关备案成品以及料件序号查询通关备案BOM
	 * 
	 * @param dzscEmsExgBill
	 * @param imgSeqNum
	 * @return
	 */
	DzscEmsBomBill findDzscEmsBomBill(Request request,
			DzscEmsExgBill dzscEmsExgBill, Integer imgSeqNum);

	/**
	 * 根据合同号查询通关备案料件总数
	 * 
	 * @param contractId
	 * @return
	 */
	int findDzscEmsImgBillCount(Request request, String contractId);

	List findDzscContract(Request request, String declareState);

	/**
	 * 按物料类型查询所有有四位归并的资料
	 * 
	 * @param materieltype
	 * @return
	 */
	List findDzscInnerMergeData(Request request, String materieltype);

	/**
	 * 通关备案料件进出平衡检查
	 * 
	 * @return
	 */
	List findDzscEmsImgAndExgUsedDiffAmount(Request request,
			DzscEmsPorHead dzscEmsPorHead);

	Complex findCustomsComplexByCode(Request request, String code);

	List findDzscEmsExgBill(Request request, DzscEmsPorHead head);

	void saveDzscEmsImgFromImport(Request request, List ls, boolean isOverwrite);

	void saveDzscEmsExgFromImport(Request request, List ls, boolean isOverwrite);

	void saveDzscEmsBomFromImport(Request request, List ls, boolean isOverwrite);

	List findTenInnerSeqNum(Request request);

	/**
	 * 更新电子手册手册编号
	 * 
	 * @param request
	 * @param head
	 *            合同备案表头
	 * @param newEmsNo
	 *            新的手册号
	 */
	Boolean editDzscEmsNo(Request request, String oldEmsNo, String newEmsNo,
			String head);

	/**
	 * 更新合同备案手册编号--权限控制
	 */
	void checkEditPorWjHeadEmsNo(Request request);

	/**
	 * 更新通关备案手册编号--权限控制
	 */
	void checkEditPorHeadEmsNo(Request request);

	/**
	 * 根据通关备案成品查找报关单耗成品
	 * 
	 * @param exgBill
	 * @return
	 */
	List findDzscCustomsBomExg(Request request, DzscEmsExgBill exgBill);

	/**
	 * 保存通关备案单耗来自报关单耗
	 * 
	 * @param exgBill
	 * @param list
	 */
	void saveDzscEmsBomBillByCustomsBom(Request request,
			DzscEmsExgBill exgBill, List list);

	public List findDzscEmsBomBillBySeq(Request request, DzscEmsPorHead head,
			Integer be, Integer en);

	/**
	 * 从QP中查询电子手册合同备案表头
	 * 
	 * @return
	 */
	List findDzscQPEmsPorWjHead(Request request);

	/**
	 * 从QP中查询电子手册通关备案表头
	 * 
	 * @return
	 */
	List findDzscQPEmsPorBillHead(Request request);

	/**
	 * 从QP中导入电子手册通关备案
	 * 
	 * @param tempQPEmsPorData
	 */
	void importDzscEmsPorBillFromQP(Request request, String emsPorWjContent);

	/**
	 * 从QP中导入电子手册合同备案
	 * 
	 * @param tempQPEmsPorData
	 */
	void importDzscEmsPorWjFromQP(Request request, String emsPorWjContent);
	
	DzscEmsExgBill getContractExgByContract(Request request,String contractId,
			Integer exgId, String contractNo);
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public int findMaxEmsPorImgSeqNumExceptAdd(Request request,DzscEmsPorHead head) ;
	
	/**
	 * 计算成品单价
	 * @return
	 */
	public List processDzscEmsExgBillPrice(Request request,List<DzscEmsExgBill> exgBills);
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public int findMaxEmsPorExgSeqNumExceptAdd(Request request,DzscEmsPorHead head) ;
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgByParentId(Request request,DzscEmsPorHead head);
	
	public void saveEmsPorImg(Request request, List list);
	
	public void saveDzscEmsExgBill(Request request, List list);

	/**
	 * 计算成品耗用料件情况
	 * @param request
	 * @param exg
	 * @return
	 */
	public List<TempDzscEmsImgBill> processImgAmountFromExg(Request request,
			DzscEmsExgBill exg);

	/**
	 * 根据成品数量和单耗重新反算成品单价和料件数量
	 * @param request
	 * @param head
	 */
	public void reCalContractUnitWaste(Request request, DzscEmsPorHead head);

	public List findAllDzscEmsExgBill();
	/**
	 * 转抄通关备案成品单耗(成品单耗来自其他合同)
	 * 
	 * @param request
	 *            请求控制
	 * @param exgBillFrom
	 *            被拷贝的通关备案成品
	 * @param exgBillTo
	 *            通关备案成品
	 *  2010-05-31 hcl        
	 */
	public void copyOtherDzscEmsPorBomBill(Request request, DzscEmsExgBill from,
			DzscEmsExgBill to);
 /**
  * 保存List（bom）提高效率
  * @param request
  * @param list
  * 2010-06-21 hcl
  */
	DzscEmsImgBill onlySaveDzscEmsImgBill(Request request, DzscEmsImgBill obj);
	/**
	 * 
	 * 保存通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bom
	 *            通关备案BOM资料
	 */
	DzscEmsBomBill saveImgToDzscEmsBomBill(Request request, DzscEmsBomBill bom);

	/**
	 * 增加禁用商品
	 * @param request
	 * @param list
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	List addCommdityForbid(Request request, List list, boolean isMaterial,
			String emsNo);
	/**
	 * 修改合同商品
	 */
	void changeEmsEdiForbid(Request request, String emsNo, String seqNum,
			boolean isMaterial);
	/**
	 * 删除禁用商品
	 */
	void deleteCommdityForbid(Request request, DzscCommdityForbid obj);
	/**
	 * 查找禁用商品
	 */
	List findCommdityForbid(Request request, String emsNo, boolean isMateriel,
			Date begindate, Date enddate);
	/**
	 *获取手册商品
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public List getTempContractForBid(Request request,boolean isMaterial,String emsNo);

	void brownDeleteCustoms(Request request);

	void findImpExpRequestBill(Request request);

	void checkDzscCommodityForbid(Request request);

	void checkDzscCustomsAnalyse(Request request);

	void checkDzscCustomsBillListAuthority(Request request);

	List findDzscEmsPorHeadExcu1(Request request);

	void checkContractReport(Request request);
	/**
	 * 判断通关手册成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param DzscEmsPorHead
	 *            通关手册
	 */
	String checkContractIsUnitModifyMarkExgBom(Request request,DzscEmsPorHead head);
}