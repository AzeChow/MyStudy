package com.bestway.bcs.dictpor.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.dao.BcsDictPorDao;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.dictpor.logic.BcsDictPorLogic;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
//电子化手册-备案资料库备案
@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class BcsDictPorActionImpl extends BaseActionImpl implements
		BcsDictPorAction {
	private BcsDictPorDao bcsDictPorDao = null;

	private BcsDictPorLogic bcsDictPorLogic = null;

	public BcsDictPorDao getBcsDictPorDao() {
		return bcsDictPorDao;
	}

	public void setBcsDictPorDao(BcsDictPorDao dictPorDao) {
		this.bcsDictPorDao = dictPorDao;
	}

	public BcsDictPorLogic getBcsDictPorLogic() {
		return bcsDictPorLogic;
	}

	public void setBcsDictPorLogic(BcsDictPorLogic dictPorLogic) {
		this.bcsDictPorLogic = dictPorLogic;
	}

	/**
	 * 查询备案资料库表头
	 * 
	 * @param request
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-浏览", index = 6)
	public List findBcsDictPorHead(Request request) {
		return this.bcsDictPorDao.findBcsDictPorHead();
	}

	/**
	 * 查询正在执行的备案资料库表头
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-浏览", index = 6)
	public List findExingBcsDictPorHead(Request request) {
		return this.bcsDictPorDao.findExingBcsDictPorHead();
	}

	/**
	 * 查询备案资料库表头
	 * 
	 * @param request
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-浏览", index = 6)
	public List findBcsDictPorHead(Request request, String dictPorEmsNo,
			String state) {
		return this.bcsDictPorDao.findBcsDictPorHead(dictPorEmsNo, state);
	}

	/**
	 * 查询备案资料库表头
	 * 
	 * @param request
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-浏览", index = 6)
	public BcsDictPorHead findBcsDictPorHeadById(Request request, String id) {
		return this.bcsDictPorDao.findBcsDictPorHeadById(id);
	}

	/**
	 * 备案资料库表头查询成品资料
	 * 
	 * @param request
	 * @param head
	 * @return
	 */
	public List findBcsDictPorExgByHead(Request request, BcsDictPorHead head) {
		return this.bcsDictPorDao.findBcsDictPorExgByHead(head);
	}

	/**
	 * 查询备案资料库未修改成品
	 * 
	 * @param head
	 * @return
	 */
	public List findBcsDictPorExgStateChanged(Request request,
			BcsDictPorHead head) {
		return this.bcsDictPorDao.findBcsDictPorExgStateChanged(head);
	}

	/**
	 * 备案资料库表头查询料件资料
	 * 
	 * @param request
	 * @param head
	 * @return
	 */
	public List findBcsDictPorImgByHead(Request request, BcsDictPorHead head) {
		return this.bcsDictPorDao.findBcsDictPorImgByHead(head);
	}

	/**
	 * 根据备案资料库表头查询备案资料库除未修改过的料件资料
	 * 
	 * @param request
	 * @param head
	 * @return
	 */
	public List findBcsDictPorImgStateChanged(Request request,
			BcsDictPorHead head) {
		return this.bcsDictPorDao.findBcsDictPorImgStateChanged(head);
	}

	/**
	 * 新增备案资料库表头
	 * 
	 * @return BcsDictPorHead 备案资料库表头
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-新增", index = 6.1)
	public BcsDictPorHead addBcsDictPorHead(Request request) {
		return this.bcsDictPorLogic.addBcsDictPorHead();
	}

	/**
	 * 从自用商品中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorImg> addBcsDictPorImgByComplex(Request request,
			BcsDictPorHead head, List list) {
		return this.bcsDictPorLogic.addBcsDictPorImgByComplex(head, list);
	}

	/**
	 * 从自用商品中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorExg> addBcsDictPorExgByComplex(Request request,
			BcsDictPorHead head, List list) {
		return this.bcsDictPorLogic.addBcsDictPorExgByComplex(head, list);
	}

	/**
	 * 查询没有在备案资料库中存在的归并数据
	 * 
	 * @param head
	 * @param materielType
	 * @return
	 */
	public List findInnerMergeNotInBcsDictPor(Request request,
			BcsDictPorHead head, String materielType, boolean isFromInnerMerge) {
		return this.bcsDictPorLogic.findInnerMergeNotInBcsDictPor(head,
				materielType, isFromInnerMerge);
	}

	/**
	 * 从内部归并中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorImg> addBcsDictPorImgByInnerMerge(Request request,
			BcsDictPorHead head, List list) {
		return this.bcsDictPorLogic.addBcsDictPorImgByInnerMerge(head, list);
	}

	/**
	 * 从内部归并中增加备案资料库成品
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorExg> addBcsDictPorExgByInnerMerge(Request request,
			BcsDictPorHead head, List list) {
		return this.bcsDictPorLogic.addBcsDictPorExgByInnerMerge(head, list);
	}

	/**
	 * 保存备案资料库表头
	 * 
	 * @param head
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-修改", index = 6.2)
	public BcsDictPorHead saveBcsDictPorHead(Request request,
			BcsDictPorHead head) {
		this.bcsDictPorLogic.saveBcsDictPorHead(head);
		return head;
	}

	/**
	 * 保存备案资料库料件
	 * 
	 * @param img
	 */
	public BcsDictPorImg saveBcsDictPorImg(Request request, BcsDictPorImg img) {
		this.bcsDictPorLogic.saveBcsDictPorImg(img);
		return img;
	}

	/**
	 * 保存备案资料库成品
	 * 
	 * @param exg
	 */
	public BcsDictPorExg saveBcsDictPorExg(Request request, BcsDictPorExg exg) {
		this.bcsDictPorLogic.saveBcsDictPorExg(exg);
		return exg;
	}

	/**
	 * 删处备案资料库表头
	 * 
	 * @param head
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-删除", index = 6.3)
	public void deleteBcsDictPorHead(Request request, BcsDictPorHead head) {
		this.bcsDictPorLogic.deleteBcsDictPorHead(head);
	}

	/**
	 * 删处备案资料库料件或成品
	 * 
	 * @param list
	 */
	public Map<Integer, List<BcsDictPorImg>> deleteBcsDictPorImg(
			Request request, List list) {
		return this.bcsDictPorLogic.deleteBcsDictPorImg(list);
	}

	/**
	 * 删处备案资料库料件或成品
	 * 
	 * @param list
	 */
	public Map<Integer, List<BcsDictPorExg>> deleteBcsDictPorExg(
			Request request, List list) {
		return this.bcsDictPorLogic.deleteBcsDictPorExg(list);
	}

	/**
	 * 保存备案资料库料件或成品
	 * 
	 * @param list
	 */
	public List saveBcsDictPorImgExg(Request request, List list) {
		this.bcsDictPorLogic.saveBcsDictPorImgExg(list);
		return list;
	}

	/**
	 * 转抄备案资料库
	 * 
	 * @param head
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-转抄", index = 6.4)
	public BcsDictPorHead copyBcsDictPor(Request request, BcsDictPorHead head) {
		return this.bcsDictPorLogic.copyBcsDictPor(head);
	}

	/**
	 * 变更备案资料库
	 * 
	 * @param head
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-变更", index = 6.5)
	public BcsDictPorHead changeBcsDictPor(Request request, BcsDictPorHead head) {
		return this.bcsDictPorLogic.changeBcsDictPor(head);
	}

	/**
	 * 备案资料库备案申请
	 * 
	 * @param taskId
	 * @param bcsDictPorHead
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-报关申报", index = 6.6)
	public DeclareFileInfo applyBcsDictPor(Request request,
			BcsDictPorHead bcsDictPorHead) {
		return this.bcsDictPorLogic.applyBcsDictPor(request.getTaskId(),
				bcsDictPorHead);
	}

	/**
	 * 备案资料库备案回执处理
	 * 
	 * @param head
	 * @param exingHead
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-处理回执", index = 6.7)
	public String processBcsDictPor(Request request, final BcsDictPorHead head,
			final BcsDictPorHead exingHead, List lsReturnFile) {
		return this.bcsDictPorLogic.processBcsDictPor(head, exingHead,
				lsReturnFile);
	}

	/**
	 * 查询备案资料库料件或者成品个数
	 * 
	 * @param head
	 * @return
	 */
	public int findBcsDictPorImgExgCountByHead(Request request,
			BcsDictPorHead head, boolean isImg) {
		return this.bcsDictPorDao.findBcsDictPorImgExgCountByHead(head, isImg);
	}

	/**
	 * 获取备案资料库备案回执的手册号
	 * 
	 * @param copEmsNo
	 * @return
	 */
	public String getDictPorHeadReturnTempEmsNo(Request request, String copEmsNo) {
		return this.bcsDictPorLogic.getDictPorHeadReturnTempEmsNo(copEmsNo);
	}

	public void saveBcsDictImgFromImport(Request request, List ls,
			boolean isOverwrite) {
		bcsDictPorLogic.saveBcsDictImgFromImport(ls, isOverwrite);
	}

	public void saveBcsDictExgFromImport(Request request, List ls,
			boolean isOverwrite) {
		bcsDictPorLogic.saveBcsDictExgFromImport(ls, isOverwrite);
	}

	/**
	 * 检查备案资料库
	 * 
	 * @param request
	 * @param list
	 *            备案资料库list
	 * @return
	 */
	public List bcsDictPorCheckup(Request request, List list) {
		return bcsDictPorLogic.bcsDictPorCheckup(list);
	}

	/**
	 * 根据修改状态查询备案资料库料件或成品
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	public List findBcsDictPorImgExgByModifyMark(Request request,
			BcsDictPorHead dictPorHead, String modifyMark, boolean isImg) {
		return this.bcsDictPorDao.findBcsDictPorImgExgByModifyMark(dictPorHead,
				modifyMark, isImg);
	}

	/**
	 * 查询备案资料库的最大序号，除去新增状态的料件
	 * 
	 * @param contract
	 * @return
	 */
	public int findMaxBcsDictPorImgExgSeqNumExceptAdd(Request request,
			BcsDictPorHead dictPorHead, boolean isImg) {
		return this.bcsDictPorDao.findMaxBcsDictPorImgExgSeqNumExceptAdd(
				dictPorHead, isImg);
	}

	/**
	 * 改变备案资料库表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-改变申报状态", index = 6.8)
	public BcsDictPorHead changeDictPorHeadDeclareState(Request request,
			BcsDictPorHead head, String declareState) {
		return this.bcsDictPorLogic.changeDictPorHeadDeclareState(head,
				declareState);
	}

	/**
	 * 改变备案资料库料件成品的修改标志
	 * 
	 * @param head
	 * @param declareState
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-改变修改状态", index = 6.9)
	public void changeDictPorImgExgModifyMark(Request request, List list,
			String modifyMark) {
		this.bcsDictPorLogic.changeDictPorImgExgModifyMark(list, modifyMark);
	}

	/**
	 * 变更备案资料库编号
	 * 
	 * @param head
	 * @param declareState
	 */
	public List changeDictPorEmsNo(Request request, String oldDictPorEmsNo,
			String dictPorEmsNo) {
		return this.bcsDictPorLogic.changeDictPorEmsNo(oldDictPorEmsNo,
				dictPorEmsNo);
	}

	/**
	 * 修改号码检查备案资料库号码是否存在
	 * 
	 * @param head
	 * @param declareState
	 */
	public List checkDictPorEmsNo(Request request, String dictPorEmsNo) {
		return this.bcsDictPorDao.findBcsDictPorHeadByEmsNo(dictPorEmsNo);
	}

	/**
	 * 从QP的导出文件中导入备案资料库资料
	 * 
	 * @param excelFileContent
	 */
	public BcsDictPorHead importBcsDictPorHeadFromQPExportFile(Request request,
			byte[] excelFileContent, String declareState, String manageObject,
			LevyKind levyKind, Trade trade, District receiveArea, Curr curr,
			MachiningType machiningType, String taskId,boolean isCover) {
		return this.bcsDictPorLogic.importBcsDictPorHeadFromQPExportFile(
				excelFileContent, declareState, manageObject, levyKind, trade,
				receiveArea, curr, machiningType, taskId, isCover);
	}

	/**
	 * 通过备案序号查询备案资料库料件
	 * 
	 * @param head
	 * @return
	 */
	public List findBcsDictPorImgBycorrEmsNoAndtenSeqNum(Request request,
			String dictPorEmsNo, Integer seqNum) {
		return this.bcsDictPorDao.findBcsDictPorImgBycorrEmsNoAndtenSeqNum(
				dictPorEmsNo, seqNum);
	}
	/**
	 * 电子化手册备案资料库料件备案
	 * @param request
	 * @param head
	 * @return
	 */
	@Override
	public BcsDictPorHead putOnRecordBcsDictPor(Request request,
			BcsDictPorHead head) {
		// TODO Auto-generated method stub
		return this.bcsDictPorLogic.putOnRecordBcsDictPor(head);
	}
	/**
	 * 电子化手册备案资料库资料同步报关商品资料的归并序号。
	 * @param request
	 * @param head
	 * @return [String(转换信息),ArrayList(料件序号不存在的),ArrayList(成品序号不存在的)]
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-同步归并序号", index = 6.10)
	public Object[] updateSeqNum(Request request,BcsDictPorHead head) {
		return bcsDictPorLogic.updateSeqNum(head);
	}
	/**
	 * 电子化手册备案资料库导入
	 */
	@AuthorityFunctionAnnotation(caption = "备案资料库备案-导入", index = 6.11) 
	public void importBcsDictPor(Request request){
		return;
	}
	
}
