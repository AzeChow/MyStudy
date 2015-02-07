/*
 * Created on 2004-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bestway.bcs.contract.entity.BcsCommdityForbid;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
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
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.logic.DzscImportQPDataLogic;
import com.bestway.dzsc.dzscmanage.logic.DzscLogic;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * com.bestway.dzsc.dzscmanage.action.DzscActionImpl
 * 
 * @author bsway
 * 
 */
//商品归并-合同备案-手册通关管理
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DzscActionImpl extends BaseActionImpl implements DzscAction {

	private DzscDao dzscDao = null;

	private DzscLogic dzscLogic = null;

	private DzscImportQPDataLogic dzscImportQPDataLogic = null;

	public DzscImportQPDataLogic getDzscImportQPDataLogic() {
		return dzscImportQPDataLogic;
	}

	public void setDzscImportQPDataLogic(
			DzscImportQPDataLogic dzscImportQPDataLogic) {
		this.dzscImportQPDataLogic = dzscImportQPDataLogic;
	}

	public Object load(Class cls, String id) {
		return this.dzscDao.load(cls, id);
	}

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
	public List findMaterielByImpExpRequestBillType(Request request,
			String materialType, String impExpRequestBillId, int index,
			int length, String property, Object value, boolean isLike,
			boolean isFilter) {
		return this.dzscLogic.findMaterielByImpExpRequestBillType(materialType,
				impExpRequestBillId, index, length, property, value, isLike,
				isFilter);
	}

	/**
	 * 查找自用商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Complex，自用商品编码
	 */
	public List findComplexForInnerMerge(Request request) {
		return this.dzscDao.findComplexForInnerMerge();
	}

	// /**
	// * 抓取最大十码归并序号
	// */
	// public Integer findMaxInnerMergeTenSeqNum(Request request,String
	// materialType){
	// return this.dzscDao.findMaxInnerMergeTenSeqNum(materialType);
	// }
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
	public String getNum(Request request, String className, String seqNum) {
		return dzscDao.getNum(className, seqNum);
	}

	/**
	 * 根据手册号来抓取合同备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public DzscEmsPorWjHead findExingDzscEmsPorWjHeadByEmsNo(Request request,
			String emsNo) {
		return this.dzscDao.findExingDzscEmsPorWjHeadByEmsNo(emsNo);
	}

	/**
	 * 新增通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DzscEmsPorHead newDzscEmsPorHead(Request request) {
		return this.dzscLogic.newDzscEmsPorHead();
	}

	/**
	 * 申报通关备案表头（申报中）
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案-申报手册", index = 4)
	public DeclareFileInfo applyDzscEmsPorHead(Request request,
			DzscEmsPorHead head) {
		return this.dzscLogic.applyDzscEmsPorHead(head, request.getTaskId());
	}

	/**
	 * 保存通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案-保存手册", index = 4)
	public DzscEmsPorHead saveDzscEmsPorHead(Request request, DzscEmsPorHead obj) {
		this.dzscLogic.saveDzscEmsPorHead(obj);
		return obj;
	}

	// /**
	// * 返回合同表体备案(料件)
	// */
	// public List findEmsPorImg(Request request, DzscEmsPorHead head) {
	// return this.dzscDao.findEmsPorImg(head);
	// }

	// /**
	// * 返回合同表体备案(成品)
	// */
	// public List findEmsPorExg(Request request, DzscEmsPorHead head) {
	// return this.dzscDao.findEmsPorExg(head);
	// }

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
	public List findMerger4(Request request, String type,
			DzscEmsPorHead dzscEmsPorHead) {
		return this.dzscDao.findMerger4(type, dzscEmsPorHead);
	}

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
	// public List findMerger10ForEmsPor(Request request, DzscEmsPorHead head,
	// String type) {
	// return this.dzscLogic.findMerger10ForEmsPor(head, type);
	// }

	// /**
	// * 保存电子备案合同表体备案料件
	// */
	// public DzscEmsPorImg saveDzscEmsPorImg(Request request, DzscEmsPorImg
	// img)
	// throws DataAccessException {
	// this.dzscDao.saveDzscEmsPorImg(img);
	// return img;
	// }
	//
	// /**
	// * 保存电子备案合同表体备案成品
	// */
	// public DzscEmsPorExg saveDzscEmsPorExg(Request request, DzscEmsPorExg
	// exg)
	// throws DataAccessException {
	// this.dzscDao.saveDzscEmsPorExg(exg);
	// return exg;
	// }

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
	@AuthorityFunctionAnnotation(caption = "手册通关备案-保存手册", index = 4)
	public DzscEmsPorHead dzscEmsPorHeadChange(Request request,
			DzscEmsPorHead emsHead, boolean isChange) {
		return this.dzscLogic.dzscEmsPorHeadChange(emsHead, isChange);
	}

	/**
	 * 返回通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgBill(Request request, DzscEmsPorHead head) {
		return this.dzscDao.findEmsPorImgBill(head);
	}

	/**
	 * 保存通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案料件资料
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案-保存手册", index = 4)
	public DzscEmsImgBill saveDzscEmsImgBill(Request request, DzscEmsImgBill obj) {
		this.dzscLogic.saveDzscEmsImgBill(obj);
		return obj;
	}

	/**
	 * 返回通关备案成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品资料
	 */
	public List findEmsPorExgBill(Request request, DzscEmsPorHead head) {
		return this.dzscDao.findDzscEmsExgBill(head);
	}

	/**
	 * 保存通关备案成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案成品资料
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案-保存手册", index = 4)
	public List saveDzscEmsExgBill(Request request, DzscEmsExgBill obj) {
		List list = new ArrayList();
		String str = this.dzscLogic.saveDzscEmsExgBill(obj);
		list.add(obj);
		list.add(str);
		return list;
	}

	/**
	 * 返回通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param exg
	 *            通关备案成品资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findEmsPorBomBill(Request request, DzscEmsExgBill exg) {
		return this.dzscDao.findEmsPorBomBill(exg);
	}

	/**
	 * 进口金额合计
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return Double 手册进口金额合计
	 */
	public Double sumMoney(Request request, DzscEmsPorHead head) {
		return this.dzscDao.sumMoney(head);
	}

	/**
	 * 查找新增的通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param exg
	 *            通关备案成品资料
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgBillToBill(Request request, DzscEmsExgBill exg) {
		return this.dzscDao.findEmsPorImgBillToBill(exg);
	}

	/**
	 * 保存通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bom
	 *            通关备案BOM资料
	 */
	public DzscEmsBomBill saveDzscEmsBomBill(Request request, DzscEmsBomBill bom)
			throws DataAccessException {
		this.dzscLogic.saveDzscEmsBomBill(bom);
		return bom;
	}

	/**
	 * 获取正在执行的通关备案成品归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialExg(Request request,
			DzscEmsPorHead dzscEmsPorHead) {
		return this.dzscDao.findDzscEmsPorMaterialExg(dzscEmsPorHead);
	}

	/**
	 * 获取正在执行的通关备案料件归并
	 * 
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorMaterialImg(Request request,
			DzscEmsPorHead dzscEmsPorHead) {
		return this.dzscDao.findDzscEmsPorMaterialImg(dzscEmsPorHead);
	}

	//
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
	// public List findFourComplex(Request request, String code, String type) {
	// return this.dzscLogic.findFourComplex(code, type);
	// }

	/**
	 * 使用该料件的成品列表
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            通关备案料件资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findExgBillForImg(Request request, DzscEmsImgBill img) {
		return this.dzscDao.findExgBillForImg(img);
	}

	/**
	 * 判断手册号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkEmsPorWjHeadDuple(Request request, DzscEmsPorWjHead head) {
		return this.dzscLogic.checkEmsPorWjHeadDuple(head);
	}

	/**
	 * 判断手册号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkDzscEmsPorHeadDuple(Request request, DzscEmsPorHead head) {
		return this.dzscLogic.checkDzscEmsPorHeadDuple(head);
	}

	/**
	 * 判断通关备案内部编号号是否重复
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkDzscEmsPorCopEmsNoDuple(Request request,
			DzscEmsPorHead head) {
		return this.dzscLogic.checkDzscEmsPorCopEmsNoDuple(head);
	}

	/**
	 * 判断通关备案企业内部物料编号是否存在
	 * 
	 * @param head
	 *            通关备案表头
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkDzscEmsPorCopEntNoIsExist(Request request,
			DzscEmsPorHead head) {
		return this.dzscLogic.checkDzscEmsPorCopEntNoIsExist(head);
	}

	/**
	 * 删除通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案料件资料
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案-保存手册", index = 4)
	public Map<Integer, List<DzscEmsImgBill>> deleteDzscEmsImgBill(
			Request request, List lsDzscEmsImgBill) {
		return this.dzscLogic.deleteDzscEmsImgBill(lsDzscEmsImgBill);
	}

	
	public List findDzscEmsBomBill (Request request,
			List lsDzscEmsImgBill){
		return this.dzscLogic.findDzscEmsBomBill(lsDzscEmsImgBill);
	}
	/**
	 * 删除通关备案成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案成品资料
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案-保存手册", index = 4)
	public Map<Integer, List<DzscEmsExgBill>> deleteDzscEmsExgBill(
			Request request, List lsDzscEmsExgBill) {
		return this.dzscLogic.deleteDzscEmsExgBill(lsDzscEmsExgBill);
	}

	/**
	 * 判断在通关备案BOM资料中是否存在通关备案料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            通关备案料件资料
	 * @return List 是DzscEmsBomBill型，通关备案BOM资料
	 */
	public List findImgFromBom(Request request, DzscEmsImgBill img) {
		return this.dzscDao.findImgFromBom(img);
	}

	/**
	 * 删除通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            通关备案BOM资料
	 */
	public Map<Integer, List<DzscEmsBomBill>> deleteDzscEmsBomBill(
			Request request, List list) {
		return this.dzscLogic.deleteDzscEmsBomBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "手册合同备案--回执处理", index = 3)
	public List findIsRepeatChange(Request request, DzscEmsPorHead head,
			String state) {
		return this.dzscDao.findIsRepeatChange(head, state);
	}

	// /**
	// * 删除合同
	// */
	// public void deleteAllEmsPor(Request request, DzscEmsPorHead head) {
	// this.dzscDao.deleteAllEmsPor(head);
	// }

	/**
	 * 删除合同备案
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--删除手册", index = 3)
	public void deleteDzscEmsPorHead(Request request, DzscEmsPorHead head) {
		this.dzscLogic.deleteDzscEmsPorHead(head);
	}

	/**
	 * 返回电子手册合同里的料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public List findEmsPorImgWj(Request request, DzscEmsPorWjHead head) {
		return this.dzscDao.findEmsPorImgWj(head);
	}

	/**
	 * 返回电子手册合同里的料件项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public int findEmsPorImgWjCount(Request request, DzscEmsPorWjHead head) {
		return this.dzscDao.findEmsPorImgWjCount(head);
	}

	/**
	 * 返回合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgWj型，合同备案成品
	 */
	public List findEmsPorExgWj(Request request, DzscEmsPorWjHead head) {
		return this.dzscDao.findEmsPorExgWj(head);
	}

	/**
	 * 返回电子手册合同里的成品项数
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsImgWj型，手册合同里的料件
	 */
	public int findEmsPorExgWjCount(Request request, DzscEmsPorWjHead head) {
		return this.dzscDao.findEmsPorExgWjCount(head);
	}

	/**
	 * 保存合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案表头
	 * @return obj 是DzscEmsPorWjHead型，合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public DzscEmsPorWjHead saveEmsPorWj(Request request, DzscEmsPorWjHead obj) {
		return this.dzscLogic.saveEmsPorWj(obj);
	}
	/**
	 * 保存合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案表头
	 * @return obj 是DzscEmsPorWjHead型，合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public DzscEmsPorWjHead saveEmsPorWj(Request request, DzscEmsPorWjHead obj,String tfCopTrNoOld) {
		return this.dzscLogic.saveEmsPorWj(obj,tfCopTrNoOld);
	}

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
	public List findMerger4ToWj(Request request, String type,
			DzscEmsPorWjHead dzscEmsPorWjHead) {
		return this.dzscDao.findMerger4ToWj(type, dzscEmsPorWjHead);
	}

	// /**
	// * 料件，成品新增序号--外经
	// *
	// * @param className
	// * @param seqNum
	// * @return
	// */
	// public Integer getNumForImgExgWj(Request request, String className,
	// DzscEmsPorWjHead head) {
	// return this.dzscDao.getNumForImgExgWj(className, head);
	// }

	/**
	 * 保存合同备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param imgWj
	 *            合同备案料件
	 * @return obj 合同备案料件
	 */
	public DzscEmsImgWj saveDzscEmsImgWj(Request request, DzscEmsImgWj imgWj) {
		this.dzscLogic.saveDzscEmsImgWj(imgWj);
		return imgWj;
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param exgWj
	 *            合同备案成品
	 * @return obj 合同备案成品
	 */
	public DzscEmsExgWj saveDzscEmsExgWj(Request request, DzscEmsExgWj exgWj) {
		this.dzscLogic.saveDzscEmsExgWj(exgWj);
		return exgWj;
	}

	// // BOM保存 --外经
	// public DzscEmsBomWj saveDzscEmsBomWj(Request request, DzscEmsBomWj obj) {
	// this.dzscDao.saveDzscEmsBomWj(obj);
	// return obj;
	// }

	// /**
	// * 使用该料件的成品列表--外经
	// */
	// public List findExgWjForImg(Request request, DzscEmsImgWj img) {
	// return this.dzscDao.findExgWjForImg(img);
	// }

	// /**
	// * 判断在单耗中是否存在该料件--外经
	// */
	// public List findImgFromBomWj(Request request, DzscEmsImgWj img) {
	// return this.dzscDao.findImgFromBomWj(img);
	// }

	/**
	 * 删除合同备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案料件
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public void deleteDzscEmsImgWj(Request request, DzscEmsImgWj obj) {
		this.dzscLogic.deleteDzscEmsImgWj(obj);
	}

	/**
	 * 将合同备案成品表示为删除标志
	 * 
	 * @param obj
	 */
	public DzscEmsImgWj markDeleteDzscEmsImgWj(Request request, DzscEmsImgWj obj) {
		this.dzscLogic.markDeleteDzscEmsImgWj(obj);
		return obj;
	}

	/**
	 * 将合同备案料件表示为删除标志
	 * 
	 * @param obj
	 */
	public DzscEmsExgWj markDeleteDzscEmsExgWj(Request request, DzscEmsExgWj obj) {
		this.dzscLogic.markDeleteDzscEmsExgWj(obj);
		return obj;
	}

	/**
	 * 删除合同备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            合同备案成品
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public void deleteDzscEmsExgWj(Request request, DzscEmsExgWj obj) {
		this.dzscLogic.deleteDzscEmsExgWj(obj);
	}

	/**
	 * 新增合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return DzscEmsPorWjHead 合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public DzscEmsPorWjHead newEmsPorWjHead(Request request) {
		return this.dzscLogic.newEmsPorWjHead();
	}

	/**
	 * 申报合同备案表头（申报中）
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同备案表头
	 * @return DzscEmsPorWjHead 合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--申报合同", index = 2.4)
	public DeclareFileInfo applyEmsPorWjHead(Request request,
			DzscEmsPorWjHead head) {
		return this.dzscLogic.applyEmsPorWjHead(head, request.getTaskId());
	}

	/**
	 * 删除合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--删除合同", index = 3)
	public void deleteAllEmsPorWj(Request request, DzscEmsPorWjHead head) {
		this.dzscDao.deleteAllEmsPorWj(head);
	}

	/**
	 * 删除合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--删除合同", index = 3)
	public void deleteDzscEmsPorWjHead(Request request, DzscEmsPorWjHead head) {
		this.dzscDao.deleteDzscEmsPorWjHead(head);
	}

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
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public List findIsRepeatChangeWj(Request request, DzscEmsPorWjHead head,
			String state) {
		return this.dzscDao.findIsRepeatChangeWj(head, state);
	}

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
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public DzscEmsPorWjHead emsPorChangeWj(Request request,
			DzscEmsPorWjHead emsHead, boolean isChange) {
		return this.dzscLogic.emsPorChangeWj(emsHead, isChange);
	}

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
	@AuthorityFunctionAnnotation(caption = "手册合同备案-回执处理", index = 2.5)
	public String processDzscEmsPorWjHead(Request request,
			DzscEmsPorWjHead wjHead, DzscEmsPorWjHead wjExingHead,
			List lsReturnFile) {
		return this.dzscLogic.processDzscEmsPorWjHead(wjHead, wjExingHead,
				lsReturnFile);
	}

	/**
	 * 处理通关备案回执
	 * 
	 * @param billHead
	 * @param billExingHead
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案-回执处理", index = 2.5)
	public String processDzscEmsPorBillHead(Request request,
			DzscEmsPorHead billHead, DzscEmsPorHead billExingHead,
			List lsReturnFile) {
		return this.dzscLogic.processDzscEmsPorBillHead(billHead,
				billExingHead, lsReturnFile);
	}

	/**
	 * 查询合同备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--浏览", index = 3)
	public List findDzscEmsPorWjHead(Request request, boolean isCancel) {
		return this.dzscDao.findDzscEmsPorWjHead(isCancel);
	}

	/**
	 * 查询合同备案表头
	 * 
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--浏览", index = 3)
	public DzscEmsPorWjHead findDzscEmsPorWjHeadById(Request request, String id) {
		return this.dzscDao.findDzscEmsPorWjHeadById(id);
	}

	/**
	 * 获取dzscDao
	 * 
	 * @return dzbaDao.
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
	}

	/**
	 * 设置dzscDao
	 * 
	 * @param dzbaDao
	 */
	public void setDzscDao(DzscDao dzbaDao) {
		this.dzscDao = dzbaDao;
	}

	/**
	 * 获取dzscLogic
	 * 
	 * @return dzbaLogic
	 */
	public DzscLogic getDzscLogic() {
		return dzscLogic;
	}

	/**
	 * 设置dzscLogic
	 * 
	 * @param dzbaLogic
	 */
	public void setDzscLogic(DzscLogic dzbaLogic) {
		this.dzscLogic = dzbaLogic;
	}

	/**
	 * 查找通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--浏览", index = 4)
	public List findDzscEmsPorHead1(Request request, boolean isCancel) {
		return this.dzscDao.findDzscEmsPorHead(isCancel);
	}

	/**
	 * 查找通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--浏览", index = 4)
	public DzscEmsPorHead findDzscEmsPorHeadById(Request request, String id) {
		return this.dzscDao.findDzscEmsPorHeadById(id);
	}

	/**
	 * 获取正在执行的通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findExeEmsPorHead(Request request) {
		return this.dzscDao.findExeEmsPorHead();
	}

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
	public Double findEmsPorImgBillMoney(Request request, DzscEmsImgBill img,
			DzscEmsPorHead head) {
		return this.dzscDao.findEmsPorImgBillMoney(img, head);
	}

	/**
	 * 返回通关备案料件进口金额
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案表头
	 * @return Double 进口金额
	 */
	public Double findEmsPorImgBillMoney(Request request, DzscEmsPorHead head) {
		return this.dzscDao.findEmsPorImgBillMoney(head);
	}

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
	public List findCustomObject(Request request, String className, String code) {
		return this.dzscDao.findCustomObject(className, code);
	}

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
	public List findMerger10ForDzscEmsPor(Request request, DzscEmsPorHead head,
			boolean isMaterial) {
		return this.dzscLogic.findMerger10ForDzscEmsPor(head, isMaterial);
	}

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
	public List saveDzscEmsPorImgExgBill(Request request, DzscEmsPorHead head,
			boolean isMaterial, List list) {
		return this.dzscLogic.saveDzscEmsPorImgExgBill(head, isMaterial, list);
	}

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
	public List saveDzscEmsPorImgExgByComplex(Request request,
			DzscEmsPorHead head, boolean isMaterial, List list) {
		return this.dzscLogic.saveDzscEmsPorImgExgByComplex(head, isMaterial,
				list);
	}

	/**
	 * 电子手册基通关备案表头--变更状态
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	public List findDzscEmsPorHeadChange(Request request) {
		return this.dzscDao.findDzscEmsPorHeadChange();
	}

	// // 返回成品单耗为null的单耗序号
	// public String findDmIsNullSeqNum(Request request, DzscEmsPorHead head) {
	// return this.dzscLogic.findDmIsNullSeqNum(head);
	// }
	//
	// // 返回成品数量为null的序号
	// public String findExgIsNullSeqNum(Request request, DzscEmsPorHead head) {
	// return this.dzscLogic.findExgIsNullSeqNum(head);
	// }
	//
	// // 返回料件数量为null的序号
	// public String findImgIsNullSeqNum(Request request, DzscEmsPorHead head) {
	// return this.dzscLogic.findImgIsNullSeqNum(head);
	// }

	/**
	 * 获得最大的合同流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的合同流水号
	 */
	public String getMaxPreContractCodeSuffixByWJ(Request request) {
		return this.dzscDao.getMaxPreContractCodeSuffixByWJ();
	}

	/**
	 * 获得最大的手册流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的手册流水号
	 */
	public String getMaxPreContractCodeSuffixByCustom(Request request) {
		return this.dzscDao.getMaxPreContractCodeSuffixByCustom();
	}

	// /**
	// * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组 来自外经合同
	// *
	// * @param index
	// * @param length
	// * @return
	// */
	// public List<ContractUnitWaste> findContractUnitWasteByWJ(Request request,
	// String contractId, int index, int length) {
	// return this.dzscLogic.findContractUnitWasteByWJ(contractId, index,
	// length);
	// }

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
	public List<ContractUnitWaste> findContractUnitWasteByCustom(
			Request request, String contractId, int index, int length) {
		return this.dzscLogic.findContractUnitWasteByCustom(contractId, index,
				length);
	}

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
	public List<ContractUnitWaste> findContractUnitWasteByCustomForEdit(
			Request request, String contractId, int index, int length) {
		return this.dzscLogic.findContractUnitWasteByCustomForEdit(contractId,
				index, length);
	}

	/**
	 * 当前通关备案成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            通关备案通关备案表头Id
	 * @return int 通关备案成品记录总数
	 */
	public int findDzscEmsExgBillCount(Request request, String contractId) {
		return this.dzscDao.findDzscEmsExgBillCount(contractId);
	}

	/**
	 * 当前合同备案成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 合同备案成品记录总数
	 */
	public int findDzscEmsExgWjCount(Request request, String contractId) {
		return this.dzscDao.findDzscEmsExgWjCount(contractId);
	}

	/**
	 * 电子手册基通关备案表头--执行状态
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
//	@AuthorityFunctionAnnotation(caption = "统计报表", index = 10)
	public List findDzscEmsPorHeadExcu(Request request) {
		return this.dzscDao.findDzscEmsPorHeadExcu();
	}

	@AuthorityFunctionAnnotation(caption = "电子手册分册--浏览", index = 5)
	public List findDzscEmsPorHeadExcu1(Request request) {
		return this.dzscDao.findDzscEmsPorHeadExcu();
	}
	
	/**
	 * 电子手册基通关备案表头--执行状态
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
//	@AuthorityFunctionAnnotation(caption = "统计报表", index = 10)
	public List findDzscEmsPorHeadExcu(Request request, String emsNo) {
		return this.dzscDao.findDzscEmsPorHeadExcu(emsNo);
	}

	/**
	 * "0":不处理表头 ，"1":插入新表头
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            手册编号
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DzscEmsPorHead makeDzscHead(Request request, String seqNum) {
		return this.dzscLogic.makeDzscHead(seqNum);
	}

	/**
	 * 获取流水号，当存在时就返回，不存在时就新建一个
	 * 
	 * @param request
	 *            请求控制
	 * @param ediNo
	 *            手册编号
	 * @return string DzscEmsPorHead的流水号
	 */
	public String getSeqNum(Request request, String ediNo) {
		return this.dzscLogic.getSeqNum(ediNo);
	}

	/**
	 * 删除非正在执行的通关备案表头By EdiNo
	 * 
	 * @param request
	 *            请求控制
	 * @param ediNo
	 *            手册编号
	 */
	public void DeleteByEdiNoNotExcu(Request request, String ediNo) {
		this.dzscLogic.deleteByEdiNoNotExcu(ediNo);
	}

	/**
	 * 根据申报状态查找通关备案表头
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscState
	 *            申报状态
	 * @return List 是DzscEmsPorHead型，通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--数据报核-浏览", index = 9.1)
	public List findDzscEmsPorHead(Request request, String dzscState) {
		return this.dzscDao.findDzscEmsPorHead(dzscState);
	}

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
	public void copyProductMaterielUnitWaste(Request request,
			List<DzscEmsExgBill> list, DzscEmsPorHead head,
			boolean isProductAmountToZero, boolean isProductPriceToZero,
			boolean isMaterielAmountToZero, boolean isMaterielPriceToZero,
			boolean isUnitWasteToZero) {
		this.dzscLogic.copyProductMaterielUnitWaste(list, head,
				isProductAmountToZero, isProductPriceToZero,
				isMaterielAmountToZero, isMaterielPriceToZero,
				isUnitWasteToZero);
	}

	/**
	 * 转抄通关备案成品
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
	public void copyProduct(Request request, List<DzscEmsExgBill> list,
			DzscEmsPorHead head, boolean isProductAmountToZero,
			boolean isProductPriceToZero) {
		this.dzscLogic.copyProduct(list, head, isProductAmountToZero,
				isProductPriceToZero);

	}

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
	public void copyMateiel(Request request, List<DzscEmsImgBill> list,
			DzscEmsPorHead head, boolean isMaterielAmountToZero,
			boolean isMaterielPriceToZero) {
		this.dzscLogic.copyMateiel(list, head, isMaterielAmountToZero,
				isMaterielPriceToZero);
	}

	/**
	 * 返回归并关系四码distinct--外经新增上方2005-8-25
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 存放电子手册商品归并资料第三层资料的临时实体类
	 */
	public List findMerger4Distinct(Request request, String type) {
		return this.dzscLogic.findMerger4DistinctToTemp(type);
	}

	/**
	 * 返回归并关系十码distinct--外经新增下方2005-8-25
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 存放电子手册商品归并资料第二层资料的临时实体类
	 */
	public List findMerger10DistinceToTemp(Request request, String type,
			String fourSeqNum) {
		return this.dzscLogic.findMerger10DistinceToTemp(type, fourSeqNum);
	}

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
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public List saveWjImg(Request request, List list, DzscEmsPorWjHead head) {
		return this.dzscLogic.saveWjImg(list, head);
	}

	/**
	 * 保存合同备案料件
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public List saveWjImgFromComplex(Request request, List list,
			DzscEmsPorWjHead head) {
		return this.dzscLogic.saveWjImgFromComplex(list, head);
	}

	/**
	 * 保存合同备案成品
	 * 
	 * @param list
	 *            是DzscEmsImgWj型，合同备案料件
	 * @param head
	 *            合同备案表头
	 * @return List 是DzscEmsImgWj型，合同备案料件
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public List saveWjExgFromComplex(Request request, List list,
			DzscEmsPorWjHead head) {
		return this.dzscLogic.saveWjExgFromComplex(list, head);
	}

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
	@AuthorityFunctionAnnotation(caption = "手册合同备案--保存合同", index = 3)
	public List saveWjExg(Request request, List list, DzscEmsPorWjHead head) {
		return this.dzscLogic.saveWjExg(list, head);
	}

	/**
	 * 保存通关备案BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsBomBill型，保存通关备案BOM
	 */
	public void saveBomLists(Request request, List list) {
		this.dzscLogic.saveBomLists(list);
	}

	/**
	 * 保存通关备案成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsExgBill型，保存通关备案成品
	 */
	public void saveExgLists(Request request, List list) {
		this.dzscLogic.saveExgLists(list);
	}

	/**
	 * 保存通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscEmsImgBill型，保存通关备案料件
	 */
	public void saveImgLists(Request request, List list) {
		this.dzscLogic.saveImgLists(list);
	}

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
	public List findMaterielFromInner(Request request, String type,
			int firstIndex, String ptNo) {
		return this.dzscDao.findMaterielFromInner(type, firstIndex, ptNo);
	}

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
	public List findBillByMaterielPtNo(Request request, String emsNo,
			String ptNo, String type) {
		return this.dzscLogic.findBillByMaterielPtNo(emsNo, ptNo, type);
	}

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
	public Double findEmsPorImgTotalNum(Request request, DzscEmsPorHead head,
			Integer imgSeqNum) {
		return this.dzscDao.findEmsPorImgTotalNum(head, imgSeqNum);
	}

	/**
	 * 查询不在通关备案归并的料件或成品
	 * 
	 * @return
	 */
	public List findInnerMergeNotInDzscEmsPor(Request request, String type,
			DzscEmsPorHead head) {
		return this.dzscLogic.findInnerMergeNotInDzscEmsPor(type, head);
	}

	/**
	 * 新增通关备案归并料件
	 * 
	 * @param data
	 * @param list
	 */
	public List addDzscEmsPorMaterialImg(Request request, DzscEmsPorHead head,
			List list) {
		return this.dzscLogic.addDzscEmsPorMaterialImg(head, list);
	}

	/**
	 * 新增通关备案归并料件
	 * 
	 * @param data
	 * @param list
	 */
	public List addDzscEmsPorMaterialExg(Request request, DzscEmsPorHead head,
			List list) {
		return this.dzscLogic.addDzscEmsPorMaterialExg(head, list);
	}

	/**
	 * 删除通关备案归并料件
	 * 
	 * @param data
	 * @param list
	 */
	public Map<Integer, List<DzscEmsPorMaterialImg>> deleteDzscEmsPorMaterialImg(
			Request request, List list) {
		return this.dzscLogic.deleteDzscEmsPorMaterialImg(list);
	}

	/**
	 * 删除通关备案归并成品
	 * 
	 * @param data
	 * @param list
	 */
	public Map<Integer, List<DzscEmsPorMaterialExg>> deleteDzscEmsPorMaterialExg(
			Request request, List list) {
		return this.dzscLogic.deleteDzscEmsPorMaterialExg(list);
	}

	/**
	 * 根据电子手册编号查询手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorHeadFas型，手册分册
	 */
//	@AuthorityFunctionAnnotation(caption = "统计报表", index = 10)
	public List findDzscEmsPorHeadFas(Request request, String emsNo) {
		return this.dzscDao.findDzscEmsPorHeadFas(emsNo);
	}

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
	public List findDzscEmsPorHeadFasByCop(Request request, String emsNo,
			String copEmsNo) {
		return this.dzscDao.findDzscEmsPorHeadFasByCop(emsNo, copEmsNo);
	}

	/**
	 * 保存电子手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 * @return dzscEmsPorHeadFas 电子手册分册
	 */
	@AuthorityFunctionAnnotation(caption = "电子手册分册-保存分册", index = 5)
	public DzscEmsPorHeadFas saveDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas dzscEmsPorHeadFas) {
		this.dzscLogic.saveDzscEmsPorHeadFas(dzscEmsPorHeadFas);
		return dzscEmsPorHeadFas;
	}

	/**
	 * 删除电子手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscEmsPorHeadFas
	 *            电子手册分册
	 */
	@AuthorityFunctionAnnotation(caption = "电子手册分册-保存分册", index = 5)
	public void deleteDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas dzscEmsPorHeadFas) {
		this.dzscDao.deleteDzscEmsPorHeadFas(dzscEmsPorHeadFas);
	}

	/**
	 * 变更手册分册
	 * 
	 * @param request
	 *            请求控制
	 * @param fas
	 *            手册分册
	 * @return DzscEmsPorHeadFas 变更后手册分册
	 */
	@AuthorityFunctionAnnotation(caption = "电子手册分册-保存分册", index = 5)
	public DzscEmsPorHeadFas emsHeadFasChange(Request request,
			DzscEmsPorHeadFas fas) {
		return this.dzscLogic.emsHeadFasChange(fas);
	}

	/**
	 * 根据合同号码查询合同备案进口总金额
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号码
	 * @return Double 进口总金额
	 */
	public Double findWjHeadMoneyByContractNo(Request request, String contractNo) {
		return this.dzscDao.findWjHeadMoneyByContractNo(contractNo);
	}

	/**
	 * 根据通关备案的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exgBill
	 * @return
	 */
	public List findMaterialExgByEmsExg(Request request, DzscEmsExgBill exgBill) {
		return this.dzscLogic.findMaterialExgByEmsExg(exgBill);
	}

	/**
	 * 自动计算通关备案单耗
	 * 
	 * @param list
	 * @return
	 */
	public List autoCalDzscEmsBomUnitWaste(Request request, List list,
			DzscEmsExgBill exgBill) {
		return this.dzscLogic.autoCalDzscEmsBomUnitWaste(list, exgBill);
	}

	/**
	 * 保存自动计算通关备案的单耗
	 * 
	 * @param list
	 * @param exgBill
	 */
	public void saveAutoCalDzscEmsBomUnitWaste(Request request, List list,
			DzscEmsExgBill exgBill) {
		this.dzscLogic.saveAutoCalDzscEmsBomUnitWaste(list, exgBill);
	}

	// /**
	// * 从企业的BOM中导入通关备案单耗
	// *
	// * @param request
	// * 请求控制
	// * @param exgBill
	// * 通关备案成品
	// */
	// public void importDzscEmsPorBomBillFromBom(Request request,
	// DzscEmsExgBill exgBill) {
	// this.dzscLogic.importDzscEmsPorBomBillFromBom(exgBill);
	// }

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
	public void copyDzscEmsPorBomBill(Request request,
			DzscEmsExgBill exgBillFrom, DzscEmsExgBill exgBillTo) {
		this.dzscLogic.copyDzscEmsPorBomBill(exgBillFrom, exgBillTo);
	}

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
	public TempDzscCommMoneyAmountInfo findDzscCurrentEmsCommInfo(
			Request request, DzscEmsPorHead dzscEmsPorHead, boolean isMaterial,
			Integer seqNum) {
		return this.dzscLogic.findDzscCurrentEmsCommInfo(dzscEmsPorHead,
				isMaterial, seqNum);
	}

	/**
	 * 电子分册申报 *
	 * 
	 * @return 生成报文的文件名。
	 */
	public DeclareFileInfo applyDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas data) {
		return this.dzscLogic.applyDzscEmsPorHeadFas(data, request.getTaskId());
	}

	/**
	 * 电子分册回执处理 *
	 */
	public String proccessDzscEmsPorHeadFas(Request request,
			DzscEmsPorHeadFas data, List lsReturnFile) {
		return this.dzscLogic.proccessDzscEmsPorHeadFas(data, lsReturnFile);
	}

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
	public List findDzscEmsBomBillImg(Request request,
			DzscEmsPorHead dzscEmsPorHead, int index, int length,
			String property, Object value, Boolean isLike) {
		return this.dzscDao.findDzscEmsBomBillImg(dzscEmsPorHead, index,
				length, property, value, isLike);
	}

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findDzscEmsBomBillByImg(Request request,
			DzscEmsPorHead dzscEmsPorHead, DzscEmsImgBill img) {
		return this.dzscDao.findDzscEmsBomBillByImg(dzscEmsPorHead, img);
	}

	/**
	 * 查询归并前归并后的BOM单耗差异
	 * 
	 * @param list
	 * @param exgBill
	 * @return
	 */
	public List findDzscCustomsFactoryUnitWasteDiff(Request request, List list,
			DzscEmsExgBill exgBill) {
		return this.dzscLogic
				.findDzscCustomsFactoryUnitWasteDiff(list, exgBill);
	}

	public List findDzscEmsExgBillBySeqNum(Request request, String emsNo,
			String seqNum) {
		return this.dzscDao.findDzscEmsExgBillBySeqNum(emsNo, seqNum);
	}

	/**
	 * 根据通过备案头，归并序号查询通关备案成品
	 * 
	 * @param dzscEmsPorHead
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	public DzscEmsExgBill findDzscEmsExgBillByTenSeqNum(Request request,
			DzscEmsPorHead dzscEmsPorHead, Integer tenSeqNum) {
		List list = this.dzscDao.findDzscEmsExgBillByTenSeqNum(dzscEmsPorHead
				.getId(), tenSeqNum);
		if (list.size() > 0) {
			return (DzscEmsExgBill) list.get(0);
		}
		return null;
	}

	/**
	 * 根据通关备案头与料号查询通关备案归并成品
	 * 
	 * @param dzscEmsPorHead
	 * @param ptno
	 * @return
	 */
	public DzscEmsPorMaterialExg findDzscEmsPorMaterialExgByPtno(
			Request request, DzscEmsPorHead dzscEmsPorHead, String ptno) {
		return dzscDao.findDzscEmsPorMaterialExgByPtno(dzscEmsPorHead, ptno);
	}

	/**
	 * 保存实体类
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(Request request, Object obj) {
		dzscDao.saveOrUpdate(obj);
	}

	/**
	 * 根据料号查询电子手册归并
	 * 
	 * @param ptno
	 * @return
	 */
	public List findDzscInnerMergeDataByptno(Request request, String ptno) {
		return dzscDao.findDzscInnerMergeDataByptno(ptno);
	}

	/**
	 * 根据十位归并序号查询电子手册通关备案料件
	 * 
	 * @param dzscEmsPorHead
	 * @param Seqno
	 * @return
	 */
	public DzscEmsImgBill findDzscEmsImgBillByTenSeqNum(Request request,
			DzscEmsPorHead dzscEmsPorHead, int tenSeqNum) {
		List list = this.dzscDao.findDzscEmsImgBillByTenSeqNum(dzscEmsPorHead
				.getId(), tenSeqNum);
		if (list.size() > 0) {
			return (DzscEmsImgBill) list.get(0);
		}
		return null;
	}

	/**
	 * 根据通关备案头与料号查询通关备案归并料件
	 * 
	 * @param dzscEmsPorHead
	 * @param ptno
	 * @return
	 */
	public DzscEmsPorMaterialImg findDzscEmsPorMaterialImgByPtno(
			Request request, DzscEmsPorHead dzscEmsPorHead, String ptno) {
		return dzscDao.findDzscEmsPorMaterialImgByPtno(dzscEmsPorHead, ptno);
	}

	/**
	 * 根据通关备案成品以及料件序号查询通关备案BOM
	 * 
	 * @param dzscEmsExgBill
	 * @param imgSeqNum
	 * @return
	 */
	public DzscEmsBomBill findDzscEmsBomBill(Request request,
			DzscEmsExgBill dzscEmsExgBill, Integer imgSeqNum) {
		return dzscDao.findDzscEmsBomBill(dzscEmsExgBill, imgSeqNum);
	}

	public int findDzscEmsImgBillCount(Request request, String contractId) {
		return dzscDao.findDzscEmsExgBillCount(contractId);
	}

	public List findDzscContract(Request request, String declareState) {
		return dzscDao.findDzscContract(declareState);
	}

	/**
	 * 按物料类型查询所有有四位归并的资料
	 * 
	 * @param materieltype
	 * @return
	 */
	public List findDzscInnerMergeData(Request request, String materieltype) {
		return dzscDao.findDzscInnerMergeData(materieltype);
	}

	/**
	 * 通关备案料件进出平衡检查
	 * 
	 * @return
	 */
	public List findDzscEmsImgAndExgUsedDiffAmount(Request request,
			DzscEmsPorHead dzscEmsPorHead) {
		return this.dzscLogic
				.findDzscEmsImgAndExgUsedDiffAmount(dzscEmsPorHead);
	}

	public Complex findCustomsComplexByCode(Request request, String code) {
		return this.dzscLogic.findCustomsComplexByCode(code);
	}

	public List findDzscEmsExgBill(Request request, DzscEmsPorHead head) {
		return this.dzscDao.findDzscEmsExgBill(head);
	}

	public void saveDzscEmsImgFromImport(Request request, List ls,
			boolean isOverwrite) {
		this.dzscLogic.saveDzscEmsImgFromImport(ls, isOverwrite);
	}

	public void saveDzscEmsExgFromImport(Request request, List ls,
			boolean isOverwrite) {
		this.dzscLogic.saveDzscEmsExgFromImport(ls, isOverwrite);
	}

	public void saveDzscEmsBomFromImport(Request request, List ls,
			boolean isOverwrite) {
		this.dzscLogic.saveDzscEmsBomFromImport(ls, isOverwrite);
	}

	public List findTenInnerSeqNum(Request request) {
		return this.dzscDao.findTenInnerSeqNum();
	}

	/**
	 * 更新电子手册手册编号
	 * 
	 * @param request
	 * @param head
	 *            合同备案表头
	 * @param newEmsNo
	 *            新的手册号
	 */
	public Boolean editDzscEmsNo(Request request, String oldEmsNo,
			String newEmsNo, String head) {
		return this.dzscLogic.editDzscEmsNo(oldEmsNo, newEmsNo, head);
	}

	/**
	 * 更新合同备案手册编号--权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--修改手册号", index = 3)
	public void checkEditPorWjHeadEmsNo(Request request) {

	}

	/**
	 * 更新通关备案手册编号--权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--修改手册号", index = 4)
	public void checkEditPorHeadEmsNo(Request request) {

	}

	/**
	 * 根据通关备案成品查找报关单耗成品
	 * 
	 * @param exgBill
	 * @return
	 */
	public List findDzscCustomsBomExg(Request request, DzscEmsExgBill exgBill) {
		return this.dzscLogic.findDzscCustomsBomExg(exgBill);
	}

	/**
	 * 保存通关备案单耗来自报关单耗
	 * 
	 * @param exgBill
	 * @param list
	 */
	public void saveDzscEmsBomBillByCustomsBom(Request request,
			DzscEmsExgBill exgBill, List list) {
		this.dzscLogic.saveDzscEmsBomBillByCustomsBom(exgBill, list);
	}

	public List findDzscEmsBomBillBySeq(Request request, DzscEmsPorHead head,
			Integer be, Integer en) {
		return this.dzscDao.findDzscEmsBomBillBySeq(head, be, en);
	}

	/**
	 * 改变通关手册表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--改变申报状态", index = 3)
	public DzscEmsPorWjHead changeDictPorHeadDeclareState(Request request,
			DzscEmsPorWjHead head, String declareState) {
		return this.dzscLogic.changeDictPorHeadDeclareState(head, declareState);
	}

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "手册合同备案--改变修改标志", index = 3)
	public void changeDictPorImgExgModifyMark(Request request, List list,
			String modifyMark) {
		this.dzscLogic.changeDictPorImgExgModifyMark(list, modifyMark);
	}

	/**
	 * 改变通关手册表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--改变申报状态", index = 4)
	public DzscEmsPorHead changeDzscEmsPorHeadDeclareState(Request request,
			DzscEmsPorHead head, String declareState) {
		return this.dzscLogic.changeDzscEmsPorHeadDeclareState(head,
				declareState);
	}

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--改变修改标志", index = 4)
	public void changeContractImgModifyMark(Request request, List list,
			String modifyMark) {
		this.dzscLogic.changeContractImgModifyMark(list, modifyMark);
	}

	/**
	 * 修改通关手册成品的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--改变修改标志", index = 4)
	public void changeContractExgModifyMark(Request request, List list,
			String modifyMark) {
		this.dzscLogic.changeContractExgModifyMark(list, modifyMark);
	}

	/**
	 * 修改通关手册单耗的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--改变修改标志", index = 4)
	public void changeContractBomModifyMark(Request request, List list,
			String modifyMark) {
		this.dzscLogic.changeContractBomModifyMark(list, modifyMark);
	}

	/**
	 * 从QP中导入电子手册通关备案
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importDzscEmsPorBillFromQP(Request request, String emsPorWjContent) {
		this.dzscImportQPDataLogic
				.importDzscEmsPorBillFromQP(emsPorWjContent);
	}

	/**
	 * 从QP中导入电子手册合同备案
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importDzscEmsPorWjFromQP(Request request, String emsPorWjContent) {
		this.dzscImportQPDataLogic.importDzscEmsPorWjFromQP(emsPorWjContent);
	}
	
	/**
	 * 从QP中查询电子手册合同备案表头 
	 * @return
	 */
	public List findDzscQPEmsPorWjHead(Request request) {
		return this.dzscImportQPDataLogic.findDzscQPEmsPorWjHead();
	}
	/**
	 * 从QP中查询电子手册通关备案表头 
	 * @return
	 */
	public List findDzscQPEmsPorBillHead(Request request) {
		return this.dzscImportQPDataLogic.findDzscQPEmsPorBillHead();
	}
	
	public DzscEmsExgBill getContractExgByContract(Request request,String contractId,
			Integer exgId, String contractNo){
		return this.dzscDao.getContractExgByContract(contractId,exgId,contractNo);
	}
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgBillModifyMark(Request request, DzscEmsPorHead head,String modifyMark){
		return this.dzscDao.findEmsPorImgBillModifyMark(head,modifyMark);
	}
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorExgBillModifyMark(Request request, DzscEmsPorHead head,String modifyMark){
		return this.dzscDao.findEmsPorExgBillModifyMark(head,modifyMark);
	}
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public int findMaxEmsPorImgSeqNumExceptAdd(Request request,DzscEmsPorHead head) {
	   return dzscDao.findMaxEmsPorImgSeqNumExceptAdd(head);
	}
	
	/**
	 * 计算成品单价
	 * @return
	 */
	public List processDzscEmsExgBillPrice(Request request,List<DzscEmsExgBill> exgBills){
		return this.dzscLogic.processDzscEmsExgBillPrice(exgBills);
	}
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public int findMaxEmsPorExgSeqNumExceptAdd(Request request,DzscEmsPorHead head) {
	   return dzscDao.findMaxEmsPorExgSeqNumExceptAdd(head);
	}
	
	/**
	 * 返回通关备案料件
	 * 
	 * @param head
	 *            通关备案料件
	 * @return List 是DzscEmsImgBill型，通关备案料件资料
	 */
	public List findEmsPorImgByParentId(Request request,DzscEmsPorHead head) {
		return dzscDao.findEmsPorImgByParentId(head);
	}
	
	public void saveEmsPorImg(Request request, List list){
		dzscLogic.saveEmsPorImg(list);
	}
	
	public void saveDzscEmsExgBill(Request request, List list){
		dzscDao.saveDzscEmsExgBill(list);
	}
	/**
	 * 查找电子手册参数设置
	 */
	public DzscParameterSet findDzscParameterSet(Request request){
		return this.dzscDao.findDzscParameterSet();
	}
	/**
	 * 计算成品耗用料件情况
	 * @param request
	 * @param exg
	 * @return
	 */
	public List<TempDzscEmsImgBill> processImgAmountFromExg(Request request,
			DzscEmsExgBill exg){
		return this.dzscLogic.processImgAmountFromExg(exg);
	}
	
	/**
	 * 根据成品数量和单耗重新反算成品单价和料件数量
	 * @param request
	 * @param head
	 */
	public void reCalContractUnitWaste(Request request, DzscEmsPorHead head){
		this.dzscLogic.reCalContractUnitWaste(head);
	}

	public List findAllDzscEmsExgBill() {
		return dzscLogic.findAllDzscEmsExgBill();
	}
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
	public void copyOtherDzscEmsPorBomBill(Request request,
			DzscEmsExgBill from, DzscEmsExgBill to) {
		this.dzscLogic.copyOtherDzscEmsPorBomBill(from, to);
	}
	 /**
	  * 保存提高效率
	  * @param request
	  * @param list
	  * 2010-06-21 hcl
	  */
	public DzscEmsImgBill onlySaveDzscEmsImgBill(Request request,
			DzscEmsImgBill obj) {
		 this.dzscLogic.onlySaveDzscEmsImgBill(obj);
		 return obj;
	}
	/**
	 * 保存通关备案BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bom
	 *            通关备案BOM资料
	 */
	public DzscEmsBomBill saveImgToDzscEmsBomBill(Request request, DzscEmsBomBill bom) {
		this.dzscLogic.saveImgToDzscEmsBomBill(bom);
		return bom;
		
	}
	
	
	

	/**
	 *获取手册商品
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public List getTempContractForBid(Request request,boolean isMaterial,String emsNo){
		return this.dzscLogic.getTempContractForBid(isMaterial,emsNo);
	}
	/**
	 *新增禁用商品
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public List addCommdityForbid(Request request, List list,
			boolean isMaterial, String emsNo) {
		return this.dzscLogic.addCommdityForbid(list,isMaterial,emsNo);
	}
	
	/**
	 * 恢复手册商品
	 * @param request
	 * @param emsNo
	 * @param seqNum
	 * @param isMaterial
	 * @param b
	 * @param version
	 */
	public void changeEmsEdiForbid(Request request,String emsNo, String seqNum,boolean isMaterial
		) {
		 this.dzscLogic.changeEmsEdiForbid(emsNo,seqNum,isMaterial,false);
	}
	
	/**
	 *删除禁用商品
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public void deleteCommdityForbid(Request request, DzscCommdityForbid obj) {
		 this.dzscLogic.deleteCommdityForbid(obj);
	}
	/**
	 * 查找禁用商品
	 * @param request
	 * @param isMateriel
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	public List findCommdityForbid(Request request, String emsNo, boolean isMateriel,
			Date beginDate, Date endDate) {
		return dzscDao.findCommdityForbid(emsNo,isMateriel, beginDate, endDate);
	}
	@AuthorityFunctionAnnotation(caption = "手册报关申报--报关单删单查询", index = 8.4)
	public void brownDeleteCustoms(Request request) {
		// TODO Auto-generated method stub
		
	}
	@AuthorityFunctionAnnotation(caption = "手册报关申请单--浏览", index = 6)
	public void findImpExpRequestBill(Request request) {
		// TODO Auto-generated method stub
		
	}
	@AuthorityFunctionAnnotation(caption = "手册报关申请单--浏览", index = 6)
	public void checkDzscCommodityForbid(Request request) {
		// TODO Auto-generated method stub
		
	}
	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public void checkDzscCustomsAnalyse(Request request) {
		// TODO Auto-generated method stub
		
	}
	@AuthorityFunctionAnnotation(caption = "手册报关清单--浏览", index = 7)
	public void checkDzscCustomsBillListAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}
	@AuthorityFunctionAnnotation(caption = "统计报表", index = 11)
	public void checkContractReport(Request request) {
		// TODO Auto-generated method stub
	}
	/**
	 * 判断通关手册成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param DzscEmsPorHead
	 *            通关手册
	 */
	public String checkContractIsUnitModifyMarkExgBom(Request request,DzscEmsPorHead head){
		 return this.dzscLogic.checkContractIsUnitModifyMarkExgBom(head);
	}
}