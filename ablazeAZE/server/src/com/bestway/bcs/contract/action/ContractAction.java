/*
 * Created on 2005-3-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.BcsCommdityForbid;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcs.contract.entity.ImgExgObject;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;

/**
 * com.bestway.bcs.contract.action.ContractAction
 * 
 * @author yp
 * 
 */
public interface ContractAction {
	/**
	 * 新增合同备案表头
	 * 
	 * @return DzscEmsPorHead 通关备案表头
	 */
	Contract newContract(Request request);

	/**
	 * 通关手册导入
	 * 
	 * @param request
	 */
	public void importContract(Request request);

	Object load(Class cls, String id);

	/**
	 * 返回合同的总耗用量
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同表头
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return double 合同的总耗用量
	 */
	public Double findEmsPorImgTotalNum(Request request, Contract head,
			Integer imgSeqNum);

	/**
	 * 批量修改商品编码与法定单位权限控制
	 */
	public void brownUpdateComplex(Request request);

	/**
	 * 保存BCS参数
	 * 
	 * @param parameter
	 */
	void saveBcsParameterSet(Request request, BcsParameterSet parameter);

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	BcsParameterSet findBcsParameterSet(Request request);

	/**
	 * 查询是否已备案
	 * 
	 * @param request
	 * @return
	 */
	List findEquipModeState(Request request, String emsNo);

	// ///////////
	// 合同
	// ///////////
	/**
	 * 查找所有的合同
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	List findContract(Request request);

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	Contract findContractById(Request request, String id);

	/**
	 * 根据EMS_NO查找正在执行的合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	Contract findExingContractByEmsNo(Request request, String emsNo);

	/**
	 * 转厂申请表根据emsNO查找合同中的企业合同号
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	Contract findContractByEmsNO(Request request, String emsNo);

	/**
	 * 查找所有的合同是否已审核
	 * 
	 * @param request
	 *            请求控制
	 * @param isCancel
	 *            审核判断，true表示已审核
	 * @return List 是Contract型，合同表头
	 */
	List findContract(Request request, boolean isCancel);

	/**
	 * 查找所有的合同是否已审核
	 * 
	 * @param request
	 *            请求控制
	 * @param isCancel
	 *            审核判断，true表示已审核
	 * @return List 是Contract型，合同表头
	 */
	List findContractInCav(Request request, boolean isCancel);

	/**
	 * 保存合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	Contract saveContract(Request request, Contract contract);

	/**
	 * 删除合同表头
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	void deleteContract(Request request, Contract contract);

	/**
	 * 批量保存合同
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同表头
	 */
	List saveContract(Request request, List list);

	/**
	 * 删除合同表头
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	void deleteContract(Request request, List<Contract> list);

	void deleteCavContract(Request request, List<Contract> liste);

	/**
	 * 转抄合同数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	List<Contract> copyContract(Request request, List<Contract> list);

	/**
	 * 判断合同是否可以备案
	 * 
	 * @param contract
	 *            合同表头
	 */
	String checkContractForPutOnRecord(Request request, Contract contract);

	/**
	 * 判断合同料件表、成品表、及单耗表的数量是否必需取整
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	String checkContractIsAmountToInteger(Request request, Contract contract);

	/**
	 * 备案合同数据
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	Contract putOnRecordContract(Request request, Contract contract);

	/**
	 * 变更合同 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @return Contract 合同表头
	 */
	Contract changingContract(Request request, Contract contract);

	// ///////////
	// 合同BOM
	// ///////////

	/**
	 * 查找所有的合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractBom型，合同BOM
	 */
	List findContractBom(Request request);

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	List findContractBomByParentId(Request request, String parentId);

	/**
	 * 查找合同BOM 来自 合同ID
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同id
	 * @return List 是ContractBom型，合同BOM
	 */
	List findContractBomByContractId(Request request, String parentId);

	/**
	 * 保存合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBom
	 *            合同BOM
	 */
	ContractBom saveContractBom(Request request, ContractBom contractBom);

	/**
	 * 批量保存合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同BOM
	 */
	List saveContractBom(Request request, List list);

	/**
	 * 删除合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractBom型，合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	Map<Integer, List<ContractBom>> deleteContractBom(Request request, List list);

	/**
	 * 删除单耗为空或者为零的记录
	 * 
	 * @param contract
	 */
	void deleteContractBomUnitWasteIsNull(Request request, Contract contract);

	/**
	 * 查找所有的合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractImg型，合同料件
	 */
	List findContractImg(Request request);

	/**
	 * 查找合同料件 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	List findContractImgByParentId(Request request, String parentId);

	/**
	 * 通过合同号查找合同料件
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	List findContractImgByContractExpContractNo(Request request,
			String expContractNo);

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractImgByContractIdByModifyMark(Request request,
			String parentId);

	/**
	 * 查找 合同成品 根据 备案编号 / 备案序号 (记录号)
	 * 
	 * @param request
	 * @param corrEmsNo
	 * @param credenceNo
	 * @return
	 */
	ContractExg findCurrContractExgByCredenceNo(Request request,
			String contractId, String corrEmsNo, String credenceNo);

	List findContractImgBy(Request request, String parentId,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA);

	/**
	 * 根据修改状态查询合同料件
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	List findContractImgByModifyMark(Request request, Contract contract,
			String modifyMark);

	/**
	 * 查询合同的最大序号，除去新增状态的料件
	 * 
	 * @param contract
	 * @return
	 */
	int findMaxContractImgSeqNumExceptAdd(Request request, Contract contract);

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同料件
	 */
	ContractImg saveContractImg(Request request, ContractImg contractImg);

	// /**
	// * 变更料件的商品编码
	// *
	// * @param contractImg
	// * @param complex
	// */
	// ContractImg changeContractImgComplex(Request request,
	// ContractImg contractImg, Complex complex);

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同料件
	 */
	ContractImg saveContractImg(Request request, ContractImg contractImg,
			List<ContractBom> list);

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	List saveContractImg(Request request, List list);

	/**
	 * 删除合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	Map<Integer, List<ContractImg>> deleteContractImg(Request request, List list);

	/**
	 * 查找BOM中是否存在该料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	List findContractBomImg(Request request, List list);

	// ///////////
	// 合同成品
	// ///////////
	/**
	 * 查找所有的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractExg型，合同成品
	 */
	List findContractExg(Request request);

	/**
	 * 根据修改状态查询合同成品
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	List findContractExgByModifyMark(Request request, Contract contract,
			String modifyMark);

	/**
	 * 查询合同的最大序号，除去新增状态的成品
	 * 
	 * @param contract
	 * @return
	 */
	int findMaxContractExgSeqNumExceptAdd(Request request, Contract contract);

	/**
	 * 批量保存合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 */
	ContractExg saveContractExg(Request request, ContractExg contractExg);

	// /**
	// * 变更成品编码
	// *
	// * @param contractExg
	// * 合同成品
	// */
	// ContractExg changeContractExgComplex(Request request,
	// ContractExg contractExg, Complex complex);

	/**
	 * 批量保存合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同成品
	 */
	List saveContractExg(Request request, List list);

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	List findContractExgByParentId(Request request, String parentId);

	/**
	 * 通过出口合同号查找合同成品
	 * 
	 * @param expContractNo
	 *            出口合同号
	 * @return List 是ContractExg型，合同成品
	 * @author sxk
	 */
	List findContractExgByExpContractNo(Request request, String expContractNo);

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	List findContractExgByParentIdByModifyMark(Request request, String parentId);

	List findContractExgBy(Request request, String parentId,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA);

	/**
	 * 删除合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractExg型，合同成品
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	Map<Integer, List<ContractExg>> deleteContractExg(Request request,
			List<ContractExg> list);

	/**
	 * 保存合同成品来自凭证成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param credenceList
	 *            报关商品资料
	 * @return List 是ContractExg型，合同成品
	 */
	List saveContractExg(Request request, Contract contract, List credenceList);

	/**
	 * 保存合同成品来自凭证成品
	 * 
	 * @param contract
	 *            合同表头
	 * @param dictPorExgList
	 *            报关商品资料
	 * @return List 是ContractExg型，合同成品
	 */
	List saveContractExgFromDictPorExg(Request request, Contract contract,
			List dictPorExgList);

	/**
	 * 保存合同Bom来自凭证料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 * @param credenceList
	 *            报关商品资料
	 * @return List 是ContractBom型，合同BOM
	 */
	List saveContractBom(Request request, ContractExg contractExg,
			List credenceList);

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	List findContractByProcessExe(Request request);

	/**
	 * 查找所有的合同来自正在执行的且来源于相同备案库
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	List findContractByProcessExecorrEmsNo(Request request, String corrEmsNo);

	List findContractByProcessExeEndDate(Request request);

	/**
	 * 查找合同BOM 来自 料件凭证号
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同料件
	 * @return List 是ContractBom型，合同BOM
	 */
	List findContractBomByCredenceNo(Request request, ContractImg contractImg);

	/**
	 * 查找合同BOM 来自 料件，成品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param exgSeqNum
	 *            成品序号
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	ContractBom findContractBomByImgExgSeqNum(Request request, String emsNo,
			String exgSeqNum, String imgSeqNum);

	/**
	 * 获得合同成品原料费用
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgId
	 *            合同成品Id
	 * @return Double 合同成品原料费用
	 */
	Double getFinishProductMaterielFee(Request request, String contractExgId);

	/**
	 * 获得合同成品单位净重
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgId
	 *            合同成品Id
	 * @return Double 合同成品单位净重
	 */
	Double getFinishProductUnitWeight(Request request, String contractExgId);

	/**
	 * 保存合同Bom来自报关商品
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param innerMergeList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	List saveContractBomFromInnerMerge(Request request,
			ContractExg contractExg, List innerMergeList);

	/**
	 * 保存合同Bom来自报关商品
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param dictPorImgList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	List saveContractBomFromDictPorImg(Request request,
			ContractExg contractExg, List dictPorImgList);

	/**
	 * 获得最大的成品序号来自当前合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 最大的成品序号
	 */
	int getMaxContractExgSeqNum(Request request, String contractId);

	/**
	 * 获得最大的Bom序号来自当前成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgId
	 *            合同成品Id
	 * @return int 最大的Bom序号
	 */
	int getMaxContractBomSeqNum(Request request, String contractExgId);

	/**
	 * 获得最大的料件总表序号来自当前合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 最大的料件总表序号
	 */
	int getMaxContractImgSeqNum(Request request, String contractId);

	/**
	 * 获得最大的合同流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的合同流水号
	 */
	String getMaxPreContractCodeSuffix(Request request);

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	List<ContractUnitWaste> findContractUnitWaste(Request request,
			String parentId, int index, int length);

	/**
	 * 获得合同单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * 
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	List<ContractUnitWaste> findContractUnitWasteAll(Request request,
			String parentId);

	/**
	 * 获得合同单耗---(不分组)
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * 
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	List<ImgExgObject> findContractUnitWasteAllNoGroup(Request request,
			String parentId);

	/**
	 * 获得合同未修改单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteByModifyMark(
			Request request, String parentId, int index, int length);

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	List<ContractUnitWaste> findContractUnitWasteChange(Request request,
			String parentId, int index, int length);

	/**
	 * 当前合同成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 合同成品记录总数
	 */
	int findContractExgCount(Request request, String contractId);

	/**
	 * 当前合同未修改成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 合同成品记录总数
	 */
	public int findContractExgCountByModifyMark(Request request,
			String contractId);

	/**
	 * 当前合同料件记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 合同料件记录总数
	 */
	int findContractImgCount(Request request, String contractId);

	int findBcsDictPorImgCount(Request request, String BcsDictPorId);

	int findBcsDictPorExgCount(Request request, String BcsDictPorId);

	/**
	 * 该帐册编号的合同是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @return boolean 帐册编号的合同是否存在，存在为true，否则为false
	 */
	boolean isExistContractByEmsNo(Request request, Contract contract);

	/**
	 * 获得合同的进口总金额
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return Double 合同的进口总金额
	 */
	Double getImgAmountSum(Request request, String contractId);

	/**
	 * 获得合同出口总金额
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return Double 合同出口总金额
	 */
	Double getExpAmountSum(Request request, String contractId);

	/**
	 * 保存合同Bom来自凭料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 * @param contractImgList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	List saveContractBomFromContractImg(Request request,
			ContractExg contractExg, List contractImgList);

	/**
	 * 查找所有的合同料件来自不在Bom中存在的
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 * @return List 是ContractImg型，合同料件
	 */
	List findContractImgNoInContractBom(Request request, ContractExg contractExg);

	/**
	 * 合同料件数据取整
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	List saveContractImgAmountInteger(Request request, List<ContractImg> list);

	/**
	 * 查找不存在报关与物料对照表的物料来自料件类型
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param materielType
	 *            物料类别
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
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	List findBcsTenInnerMergeNotContract(Request request, Contract contract,
			String materielType, int index, int length, String property,
			Object value, Boolean isLike, boolean isInnerMerge);

	/**
	 * 查找报关与物料对照表
	 * 
	 */
	List<BcsTenInnerMerge> findBcsTenInnerMerge(Request request,
			boolean isInnerMerge);

	/**
	 * 保存合同料件来自凭证料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param bcsInnerMergeList
	 *            报关商品资料
	 * @return List 是ContractImg型，合同料件
	 */
	List saveContractImg(Request request, Contract contract,
			List bcsInnerMergeList);

	/**
	 * 保存合同料件来自凭证料件
	 * 
	 * @param contract
	 *            合同表头
	 * @param bcsInnerMergeList
	 *            报关商品资料
	 * @return List 是ContractImg型，合同料件
	 */
	List saveContractImgFromDictPorImg(Request request, Contract contract,
			List dictPorImgList);

	/**
	 * 只显示物料四码不为空的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类别
	 * @param firstIndex
	 *            数据开始下标
	 * @param ptNo
	 *            料号
	 * @return List 是materiel型，报关常用物料
	 */
	public List findMaterielFromInner(Request request, String type,
			int firstIndex, String ptNo);

	/**
	 * 通过报关商品资料的凭证序号查找合同料件或合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类别
	 * @return List 是ContractImg或ContractExg型，合同料件或合同成品
	 */
	public List findBillByMaterielPtNo(Request request, String emsNo,
			String ptNo, String type);

	/**
	 * 保存合同料件来自商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveComtractImgByComplex(Request request, Contract contract,
			List list);

	/**
	 * 保存合同成品来自商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveComtractExgByComplex(Request request, Contract contract,
			List list);

	/**
	 * 保存合同BOM来自商品编码
	 * 
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	List saveComtractBomByComplex(Request request, ContractExg contractExg,
			List list);

	/**
	 * 处理合同回执
	 * 
	 * @param request
	 *            请求控制
	 * @param fileName
	 *            回执名称
	 */
	void processContractData(Request request, String fileName);

	/**
	 * 读取回执里的内容
	 * 
	 * @param request
	 *            请求控制
	 * @param fileName
	 *            回执名称
	 * @return List 是String型，存放了回执里的内容
	 */
	List<String> getContractFileList(Request request, String fileName);

	/**
	 * 从设置ftp地址、用户、密码然后下载
	 * 
	 * @param request
	 *            请求控制
	 * @return List 下载的文件
	 */
	List ftpDownload(Request request);

	/**
	 * 从设置ftp地址、用户、密码然后下载(合同)
	 * 
	 * @return List 下载的文件
	 */
	List ftpContractDownload(Request request);

	/**
	 * 查找未处理的回执
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是String型，回执名称
	 * 
	 */
	List findNotProcessedFile(Request request);

	/**
	 * 查找已处理的回执
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是String型，回执名称
	 */
	List findProcessedFile(Request request);

	/**
	 * 返回合同单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param exgList
	 *            是ContractExg型，合同成品
	 * @return List 是ContractBom型，合同BOM
	 */
	List findUnitWaste(Request request, List exgList);

	/**
	 * 变更合同料件序号
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	void changeContractImgSeqNum(Request request, Contract contract,
			ContractImg img, Integer newSeqNum, String modifyMark);

	/**
	 * 变更合同成品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param exg
	 *            合同成品
	 * @param newSeqNum
	 *            新序号
	 */
	void changeContractExgSeqNum(Request request, Contract contract,
			ContractExg exg, Integer newSeqNum, String modifyMark);

	/**
	 * 转抄合同成品单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param exgFrom
	 *            要被拷贝的合同成品
	 * @param exgTo
	 *            要拷贝的合同成品
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	void copyContractUnitWaste(Request request, ContractExg exgFrom,
			ContractExg exgTo);

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	List findContractExgBySeqNum(Request request, String emsNo, String seqNum);

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	List findDzscEmsExgBillBySeqNum(Request request, String emsNo, String seqNum);

	/**
	 * 根据合同单耗自动反算成品，料件的数量，金额等
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	void reCalContractUnitWaste(Request request, Contract contract);

	/**
	 * 更改合同商品的名称规格
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param isMaterial
	 *            料件判断，true为料件
	 * @param seqNum
	 *            料件序号
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格
	 * @return List 是ContractExg或ContractImg型，合同成品或合同料件
	 */
	Object changeContractCommNameSpec(Request request, Contract contract,
			boolean isMaterial, Integer seqNum, String name, String spec);

	/**
	 * 查找合同成品 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	List findTempContractExgByParentId(Request request, String parentId);

	/**
	 * 查找合同料件 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	List findTempContractImgByParentId(Request request, String parentId);

	/**
	 * 转抄合同成品(包括BOM)数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	Contract copyContractProduct(Request request, Contract contract,
			List<ContractExg> contractExgList);

	/**
	 * 转抄合同成品料件(不包括BOM)数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	Contract copyContractProductMateriel(Request request, Contract contract,
			List<ContractExg> contractExgList, List<ContractImg> contractImgList);

	/**
	 * 合同备案海关申报
	 * 
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	DeclareFileInfo applyContract(Request request, Contract head);

	/**
	 * 合同备案回执处理
	 * 
	 * @param contract
	 * @param exingContract
	 * @return
	 */
	void applyContractcheck(Request request);

	String processContract(Request request, final Contract contract,
			final Contract exingContract, List lsReturnFile);

	/**
	 * 查询不在合同里面的备案资料库的商品资料
	 * 
	 * @param materielType
	 * @param contractId
	 * @param dictPorEmsNo
	 * @return
	 */
	List findDictPorImgExgNotInContract(Request request, String materielType,
			String contractId, String dictPorEmsNo, boolean isFilt);

	/**
	 * 查询不在合同BOM里面的备案资料库的商品资料
	 * 
	 * @param materielType
	 * @param contractId
	 * @param dictPorEmsNo
	 * @return
	 */
	List findDictPorImgNotInContractBom(Request request, String exgId,
			String dictPorEmsNo);

	/**
	 * 根据商品编码，名称，规格查询合同成品
	 * 
	 * @param request
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	List findContractExgByname(Request request, Contract head, String code,
			String name, String spec);

	/**
	 * 根据料号查询BOM
	 * 
	 * @param request
	 * @param ptno
	 * @return
	 */
	List findBcsInnerMergeByptno(Request request, String ptno);

	/**
	 * 根据商品编码，名称，规格查询合同料件
	 * 
	 * @param request
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	List findContractImgByname(Request request, Contract head, String code,
			String name, String spec);

	/**
	 * 通过成品以及料件总表序号查询BOM
	 * 
	 * @param contractExg
	 * @param contractImgSeqNum
	 * @return
	 */
	ContractBom findContractBom(Request request, ContractExg contractExg,
			Integer contractImgSeqNum);

	/**
	 * 返回不同状态的合同头
	 * 
	 * @param request
	 *            请求控制
	 * 
	 * @param declareState
	 * 
	 *            合同状态
	 * @return List 合同
	 * 
	 */
	public List findcontract(Request request, String declareState);

	/**
	 * 取得物料信息
	 * 
	 * @param materieltype
	 * @return
	 */
	List findBcsInnerMerge(Request request, String materieltype);

	/**
	 * 合同料件进出平衡检查
	 * 
	 * @return
	 */
	List findContractImgAndExgUsedDiffAmount(Request request, Contract contract);

	/**
	 * 查询变更的进口料件
	 * 
	 * @param contract
	 * @return
	 */
	List[] findChangedContractImg(Request request, Contract contract);

	/**
	 * 查询变更的出口成品
	 * 
	 * @param contract
	 * @return
	 */
	List[] findChangedContractExg(Request request, Contract contract);

	/**
	 * 导入料件
	 */
	public void saveBcsImgFromImport(Request request, List ls,
			boolean isOverwrite);

	/**
	 * 导入成品
	 */
	public void saveBcsExgFromImport(Request request, List ls,
			boolean isOverwrite);

	/**
	 * 导入单耗
	 */
	public void saveContractEmsBomFromImport(Request request, List ls,
			boolean isOverwrite);

	/**
	 * 统计出口总金额
	 * 
	 * @param request
	 * @param contract
	 */
	public void getTotalPriceBExport(Request request, Contract contract);

	/**
	 * 统计进口总金额
	 * 
	 * @param request
	 * @param contract
	 */
	public void getTotalPriceBImport(Request request, Contract contract);

	/**
	 * 查询通关合同备案中的BOM料件
	 * 
	 * @param request
	 * @param list
	 * @return
	 */
	public List findBomDetailMaterialInContract(Request request, List list);

	/**
	 * 查询通关合同备案中的料件
	 * 
	 * @param request
	 * @param list
	 * @return
	 */
	public List findMaterialInContract(Request request, List list);

	public List findContractBomBySeq(Request request, Contract contract,
			Integer be, Integer en, Boolean declareStateCHANGING_EXE,
			Boolean declareStateWAIT_EAA);

	public List findBcsCustomsDeclarationCommInfo(Request request, String id,
			Integer commSerialNo, String complexCode, Integer impExpFlag,
			Integer impExpType);

	/**
	 * 改变通关手册表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	Contract changeContractDeclareState(Request request, Contract head,
			String declareState);

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
	 * 修改通关手册单耗的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	void changeContractBomModifyMark(Request request, List list,
			String modifyMark);

	/**
	 * 从QP的导出文件中导入通关手册资料
	 * 
	 * @param excelFileContent
	 */
	Contract importContractFromQPExportFile(Request request,
			byte[] excelFileContent, String declareState, String dictPorEmsNo,
			String limitFlag, String taskId, boolean isCover);

	/**
	 * 查找国内购买料件
	 * 
	 * @param parentId
	 * @param declareStateCHANGING_EXE
	 * @return
	 */
	public List findContractDomesticPurchaseBill(Request request,
			String parentId);

	/**
	 * 设置国内购买料件
	 * 
	 * @param parentId
	 * @param declareStateCHANGING_EXE
	 * @return
	 */
	public List setContractDomesticPurchaseBill(Request request, String parentId);

	/**
	 * 从QP中查询电子化手册备案资料库表头
	 * 
	 * @return
	 */
	public List findBcsQPDictPorHead(Request request);

	/**
	 * 从QP中查询电子化手册通关备案表头
	 * 
	 * @return
	 */
	public List findBcsQPContract(Request request);

	/**
	 * 从QP中导入电子化手册备案资料库
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importBcsDictPorHeadFromQP(Request request,
			List listPtsEmsWjHead, boolean isOverWrite);

	/**
	 * 从QP中导入电子化手册通关备案
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importContractFromQP(Request request, List listPtsEmsHead,
			boolean isOverWrite);

	/**
	 * 查找合同变更成品
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findConractExgChangedByContract(Request request,
			Contract contract);

	/**
	 * 查找合同变更后成品
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractExgChangeAfterByContract(Request request,
			Contract contract);

	/**
	 * 查找合同变更前成品
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractExgChangeBeforeByContract(Request request,
			Contract contract);

	/**
	 * 查找合同变更后料件
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractImgChangeAfterByContract(Request request,
			Contract contract);

	/**
	 * 查找合同变更前料件
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findContractImgChangeBeforeByContract(Request request,
			Contract contract);

	/**
	 * 查找合同变更料件
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public List findConractImgChangedByContract(Request request,
			Contract contract);

	/**
	 * 查询合同料件根据序号
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @param seqNum
	 *            开始序号
	 * 
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgBySeqNum(Request request, String parentId);

	/**
	 * 反写入料件总表同时反写bom
	 */
	public List writeToImgAndBomTable(Request request, String parentId,
			ContractExg exg, List bomList);

	/**
	 * 查询合同Bom中的归并序号
	 */
	public List findContractImgSeqNumByExgAndContract(Request request,
			ContractExg exg);

	/**
	 * 判断货币
	 */
	public List findCurrRateByCurr(Request request, String curr1);

	/**
	 * 判断货币
	 */
	public List findCurrRate(Request request, String curr, String curr1);

	/**
	 * 判断货币
	 */
	public List findCurrRateByDeclarationDate(Request request, String curr,
			String curr1, Date declarationDate);

	/**
	 * 获取合同商品
	 * 
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public List getTempContractForBid(Request request, boolean isMaterial,
			String emsNo);

	/**
	 * 新增禁用商品
	 * 
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	List addCommdityForbid(Request request, List list, boolean isMaterial,
			String emsNo);

	/**
	 * 删除禁用商品
	 * 
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	void deleteCommdityForbid(Request request, BcsCommdityForbid obj);

	/**
	 * 恢复手册商品
	 * 
	 * @param request
	 * @param emsNo
	 * @param seqNum
	 * @param isMaterial
	 * @param b
	 * @param version
	 */
	void changeEmsEdiForbid(Request request, String emsNo, String seqNum,
			boolean isMaterial);

	/**
	 * 查找禁用商品
	 * 
	 * @param request
	 * @param emsNo
	 * @param isMateriel
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	List findCommdityForbid(Request request, String emsNo, boolean isMateriel,
			Date begindate, Date enddate);

	void brownDeleteCustoms(Request request);

	void findImpExpRequestBill(Request request);

	/**
	 * 判断合同成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	String checkContractIsUnitModifyMarkExgBom(Request request,
			Contract contract);

	// BcsParameterSet DictPorfindBcsParameterSet(Request request);
	/**
	 * 判断进口合同号是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号
	 */
	public boolean checkImpContractNoIsExist(Request request, String contractNo);

	/**
	 * 判断出口合同号是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号
	 */
	public boolean checkExpContractNoIsExist(Request request, String contractNo);

	/**
	 * 查询是否显示出口装箱单或者发票
	 * 
	 * @param parameter
	 */
	List findExportPackinglistOrInvoice(Request request);

	/**
	 * 查询所有料件 或者 成品 的总金额和
	 * 
	 * @param request
	 * @param contractId
	 * @param clzName
	 * @return
	 */
	public Double findSumContractImgOrExgTotalPrices(Request request,
			String contractId, String clzName);

}
