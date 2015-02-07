package com.bestway.bcs.dictpor.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;

public interface BcsDictPorAction {
	/**
	 * 查询备案资料库表头
	 * 
	 * @param request
	 * @return
	 */
	List findBcsDictPorHead(Request request);

	/**
	 * 查询正在执行的备案资料库表头
	 * 
	 * @return
	 */
	List findExingBcsDictPorHead(Request request);

	/**
	 * 查询备案资料库表头
	 * 
	 * @param request
	 * @return
	 */
	List findBcsDictPorHead(Request request, String dictPorEmsNo, String state);

	/**
	 * 查询备案资料库表头
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	BcsDictPorHead findBcsDictPorHeadById(Request request, String id);

	/**
	 * 备案资料库表头查询成品资料
	 * 
	 * @param request
	 * @param head
	 * @return
	 */
	List findBcsDictPorExgByHead(Request request, BcsDictPorHead head);

	/**
	 * 查询备案资料库未修改成品
	 * 
	 * @param head
	 * @return
	 */
	List findBcsDictPorExgStateChanged(Request request, BcsDictPorHead head);

	/**
	 * 根据备案资料库表头查询备案资料库料件资料
	 * 
	 * @param request
	 * @param head
	 * @return
	 */
	List findBcsDictPorImgByHead(Request request, BcsDictPorHead head);

	/**
	 * 根据备案资料库表头查询备案资料库除未修改过的料件资料
	 * 
	 * @param request
	 * @param head
	 * @return
	 */
	List findBcsDictPorImgStateChanged(Request request, BcsDictPorHead head);

	/**
	 * 新增备案资料库表头
	 * 
	 * @return BcsDictPorHead 备案资料库表头
	 */
	BcsDictPorHead addBcsDictPorHead(Request request);

	/**
	 * 从自用商品中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	List<BcsDictPorImg> addBcsDictPorImgByComplex(Request request,
			BcsDictPorHead head, List list);

	/**
	 * 从自用商品中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	List<BcsDictPorExg> addBcsDictPorExgByComplex(Request request,
			BcsDictPorHead head, List list);

	/**
	 * 查询没有在备案资料库中存在的归并数据
	 * 
	 * @param head
	 * @param materielType
	 * @return
	 */
	List findInnerMergeNotInBcsDictPor(Request request, BcsDictPorHead head,
			String materielType, boolean isFromInnerMerge);

	/**
	 * 从内部归并中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	List<BcsDictPorImg> addBcsDictPorImgByInnerMerge(Request request,
			BcsDictPorHead head, List list);

	/**
	 * 从内部归并中增加备案资料库成品
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	List<BcsDictPorExg> addBcsDictPorExgByInnerMerge(Request request,
			BcsDictPorHead head, List list);

	/**
	 * 保存备案资料库表头
	 * 
	 * @param head
	 */
	BcsDictPorHead saveBcsDictPorHead(Request request, BcsDictPorHead head);

	/**
	 * 保存备案资料库料件
	 * 
	 * @param img
	 */
	BcsDictPorImg saveBcsDictPorImg(Request request, BcsDictPorImg img);

	/**
	 * 保存备案资料库成品
	 * 
	 * @param exg
	 */
	BcsDictPorExg saveBcsDictPorExg(Request request, BcsDictPorExg exg);

	/**
	 * 删处备案资料库表头
	 * 
	 * @param head
	 */
	void deleteBcsDictPorHead(Request request, BcsDictPorHead head);

	/**
	 * 删处备案资料库料件或成品
	 * 
	 * @param list
	 */
	Map<Integer, List<BcsDictPorImg>> deleteBcsDictPorImg(Request request,
			List list);

	/**
	 * 删处备案资料库料件或成品
	 * 
	 * @param list
	 */
	Map<Integer, List<BcsDictPorExg>> deleteBcsDictPorExg(Request request,
			List list);

	/**
	 * 保存备案资料库料件或成品
	 * 
	 * @param list
	 */
	List saveBcsDictPorImgExg(Request request, List list);

	/**
	 * 转抄备案资料库
	 * 
	 * @param head
	 */
	BcsDictPorHead copyBcsDictPor(Request request, BcsDictPorHead head);

	/**
	 * 变更备案资料库
	 * 
	 * @param head
	 */
	BcsDictPorHead changeBcsDictPor(Request request, BcsDictPorHead head);

	/**
	 * 备案资料库备案申请
	 * 
	 * @param taskId
	 * @param bcsDictPorHead
	 * @return
	 */
	DeclareFileInfo applyBcsDictPor(Request request,
			BcsDictPorHead bcsDictPorHead);

	/**
	 * 备案资料库备案回执处理
	 * 
	 * @param head
	 * @param exingHead
	 * @return
	 */
	String processBcsDictPor(Request request, final BcsDictPorHead head,
			final BcsDictPorHead exingHead, List lsReturnFile);

	/**
	 * 查询备案资料库料件或者成品个数
	 * 
	 * @param head
	 * @return
	 */
	int findBcsDictPorImgExgCountByHead(Request request, BcsDictPorHead head,
			boolean isImg);

	/**
	 * 获取备案资料库备案回执的手册号
	 * 
	 * @param copEmsNo
	 * @return
	 */
	String getDictPorHeadReturnTempEmsNo(Request request, String copEmsNo);

	/**
	 * 导入料件
	 */
	public void saveBcsDictImgFromImport(Request request, List ls,
			boolean isOverwrite);

	/**
	 * 导入成品
	 */
	public void saveBcsDictExgFromImport(Request request, List ls,
			boolean isOverwrite);

	/**
	 * 检查备案资料库
	 * 
	 * @param request
	 * @param list
	 *            备案资料库list
	 * @return
	 */
	public List bcsDictPorCheckup(Request request, List list);

	/**
	 * 根据修改状态查询备案资料库料件或成品
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	List findBcsDictPorImgExgByModifyMark(Request request,
			BcsDictPorHead dictPorHead, String modifyMark, boolean isImg);

	/**
	 * 查询备案资料库的最大序号，除去新增状态的料件
	 * 
	 * @param contract
	 * @return
	 */
	int findMaxBcsDictPorImgExgSeqNumExceptAdd(Request request,
			BcsDictPorHead dictPorHead, boolean isImg);

	/**
	 * 改变备案资料库表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	BcsDictPorHead changeDictPorHeadDeclareState(Request request,
			BcsDictPorHead head, String declareState);

	/**
	 * 改变备案资料库料件成品的修改标志
	 * 
	 * @param head
	 * @param declareState
	 */
	void changeDictPorImgExgModifyMark(Request request, List list,
			String modifyMark);

	/**
	 * 变更备案资料库编号
	 * 
	 * @param head
	 * @param declareState
	 */
	List changeDictPorEmsNo(Request request, String oldDictPorEmsNo,
			String dictPorEmsNo);

	/**
	 * 修改号码检查备案资料库号码是否存在
	 * 
	 * @param head
	 * @param declareState
	 */
	List checkDictPorEmsNo(Request request, String dictPorEmsNo);

	/**
	 * 从QP的导出文件中导入备案资料库资料
	 * 
	 * @param excelFileContent
	 */
	BcsDictPorHead importBcsDictPorHeadFromQPExportFile(Request request,
			byte[] excelFileContent, String declareState, String manageObject,
			LevyKind levyKind, Trade trade, District receiveArea, Curr curr,
			MachiningType machiningType, String taskId,boolean isCover);

	/**
	 * 通过备案序号查询备案资料库料件
	 * 
	 * @param head
	 * @return
	 */
	public List findBcsDictPorImgBycorrEmsNoAndtenSeqNum(Request request,
			String dictPorEmsNo, Integer seqNum);
	/**
	 * 电子化手册备备案资料库料件备案
	 * @param request
	 * @param head
	 * @return
	 */
	public BcsDictPorHead putOnRecordBcsDictPor(Request request,BcsDictPorHead head);
	
	/**
	 * 电子化手册备案资料库资料同步报关商品资料的归并序号。
	 * @param request
	 * @param head
	 * @return [String(转换信息),ArrayList(料件序号不存在的),ArrayList(成品序号不存在的)]
	 */
	public Object[] updateSeqNum(Request request,BcsDictPorHead head);
	/**
	 * 电子化手册备案资料库导入
	 * @param request
	 */
	public void importBcsDictPor(Request request);
}
