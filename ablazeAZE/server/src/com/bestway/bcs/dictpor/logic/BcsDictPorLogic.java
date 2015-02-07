package com.bestway.bcs.dictpor.logic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.dao.BcsDictPorDao;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.dictpor.entity.TempBcsDictPorCheckup;
import com.bestway.bcs.dictpor.entity.TempBcsDictPorExg;
import com.bestway.bcs.dictpor.entity.TempBcsDictPorImg;
import com.bestway.bcs.message.logic.BcsMessageLogic;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.CspProcessResult;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.fileimport.logic.ImportDataFromExcel;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;

/**
 * checked by kcb 2008-10-10
 * 
 * @author Administrator
 */
public class BcsDictPorLogic {
	/**
	 * 电子化手册－备案资料库DAO
	 */
	private BcsDictPorDao bcsDictPorDao = null;
	/**
	 * 电子化手册－物料与报关对应DAO
	 */
	private BcsInnerMergeDao bcsInnerMergeDao = null;
	/**
	 * 电子化手册－报文发送及查询DAO
	 */
	private BcsMessageLogic bcsMessageLogic = null;
	/**
	 * Excel导入通用类
	 */
	private ImportDataFromExcel importDataFromExcel = null;

	/**
	 * 获取 电子化手册－备案资料库DAO
	 * 
	 */
	public BcsDictPorDao getBcsDictPorDao() {
		return bcsDictPorDao;
	}

	/**
	 * 设置 电子化手册－备案资料库DAO
	 */
	public void setBcsDictPorDao(BcsDictPorDao dictPorDao) {
		this.bcsDictPorDao = dictPorDao;
	}

	/**
	 * 获取 电子化手册－物料与报关对应DAO
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 设置 电子化手册－物料与报关对应DAO
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	/**
	 * 获取 电子化手册－报文发送及查询DAO
	 */
	public BcsMessageLogic getBcsMessageLogic() {
		return bcsMessageLogic;
	}

	/**
	 * 设置 电子化手册－报文发送及查询DAO
	 */
	public void setBcsMessageLogic(BcsMessageLogic bcsExportMessageLogic) {
		this.bcsMessageLogic = bcsExportMessageLogic;
	}

	/**
	 * 获取 Excel导入通用类
	 */
	public ImportDataFromExcel getImportDataFromExcel() {
		return importDataFromExcel;
	}

	/**
	 * 设置 Excel导入通用类
	 */
	public void setImportDataFromExcel(ImportDataFromExcel importDataFromExcel) {
		this.importDataFromExcel = importDataFromExcel;
	}

	/**
	 * 新增备案资料库表头
	 * 
	 * @return BcsDictPorHead 备案资料库表头
	 */
	public BcsDictPorHead addBcsDictPorHead() {
		BcsDictPorHead head = new BcsDictPorHead();
		head.setSeqNum(Integer.valueOf(bcsDictPorDao.getNum("BcsDictPorHead",
				"seqNum")));
		String copEntNo = bcsMessageLogic.getNewCopEntNo("BcsDictPorHead",
				"copEmsNo", "C", BcsBusinessType.EMS_POR_WJ);
		Company com = this.bcsDictPorDao.findCompany();
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getBuCode() == null || "".equals(com.getBuCode())) {
			stringBuffer.append("公司的经营单位编码不能为空\r\n");
		}
		if (com.getBuName() == null || "".equals(com.getBuName())) {
			stringBuffer.append("公司的经营单位名称不能为空\r\n");
		}
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		head.setCopEmsNo(copEntNo);
		head.setTradeCode(com.getBuCode());
		head.setTradeName(com.getBuName());
		head.setMachCode(com.getCode());
		head.setMachName(com.getName());
		head.setDeclareCustoms(com.getMasterCustoms());
		head.setRedDep(com.getMasterFtc());

		head.setDeclareState(DeclareState.APPLY_POR);
		head.setModifyMark(ModifyMarkState.ADDED);
		// head.setAclUser(CommonUtils.getAclUser());
		head.setCompany(CommonUtils.getCompany());
		this.bcsDictPorDao.saveOrUpdate(head);
		return head;
	}

	/**
	 * 从自用商品中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorImg> addBcsDictPorImgByComplex(BcsDictPorHead head,
			List list) {
		List<BcsDictPorImg> lsResult = new ArrayList<BcsDictPorImg>();
		int maxSeqNum = this.bcsDictPorDao.getMaxDictPorImgSeqNum(head);
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			BcsDictPorImg img = new BcsDictPorImg();
			img.setSeqNum(maxSeqNum + i + 1);
			img.setDictPorHead(head);
			img.setComplex(complex);
			img.setCommName(complex.getName());
			img.setModifyMark(ModifyMarkState.ADDED);
			this.bcsDictPorDao.saveOrUpdate(img);
			lsResult.add(img);
		}
		return lsResult;
	}

	/**
	 * 从自用商品中增加备案资料库成品
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorExg> addBcsDictPorExgByComplex(BcsDictPorHead head,
			List list) {
		List<BcsDictPorExg> lsResult = new ArrayList<BcsDictPorExg>();
		int maxSeqNum = this.bcsDictPorDao.getMaxDictPorExgSeqNum(head);
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			BcsDictPorExg exg = new BcsDictPorExg();
			exg.setSeqNum(maxSeqNum + i + 1);
			exg.setDictPorHead(head);
			exg.setComplex(complex);
			exg.setCommName(complex.getName());
			exg.setModifyMark(ModifyMarkState.ADDED);
			this.bcsDictPorDao.saveOrUpdate(exg);
			lsResult.add(exg);
		}
		return lsResult;
	}

	/**
	 * 查询没有在备案资料库中存在的归并数据
	 * 
	 * @param head
	 * @param materielType
	 * @return
	 */
	public List findInnerMergeNotInBcsDictPor(BcsDictPorHead head,
			String materielType, boolean isFromInnerMerge) {
		List lsInnerMerge = new ArrayList();
		if (isFromInnerMerge) {
			lsInnerMerge = this.bcsInnerMergeDao
					.findBcsTenInnerMergeByInnerMerge(materielType);
		} else {
			lsInnerMerge = this.bcsInnerMergeDao
					.findBcsTenInnerMerge(materielType);
		}
		List lsInnerMergeSeqNum = new ArrayList();
		if (MaterielType.MATERIEL.equals(materielType)) {
			lsInnerMergeSeqNum = this.bcsDictPorDao
					.findBcsDictPorImgSeqNumByHead(head);
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			lsInnerMergeSeqNum = this.bcsDictPorDao
					.findBcsDictPorExgSeqNumByHead(head);
		}
		for (int i = lsInnerMerge.size() - 1; i >= 0; i--) {
			BcsTenInnerMerge innerMerge = (BcsTenInnerMerge) lsInnerMerge
					.get(i);
			if (lsInnerMergeSeqNum.contains(innerMerge.getSeqNum())) {
				lsInnerMerge.remove(i);
			}
		}
		return lsInnerMerge;
	}

	/**
	 * 从内部归并中增加备案资料库料件
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorImg> addBcsDictPorImgByInnerMerge(
			BcsDictPorHead head, List list) {
		List<BcsDictPorImg> lsResult = new ArrayList<BcsDictPorImg>();
		int maxSeqNum = this.bcsDictPorDao.getMaxDictPorImgSeqNum(head);
		for (int i = 0; i < list.size(); i++) {
			BcsTenInnerMerge tenInnerMerge = (BcsTenInnerMerge) list.get(i);
			BcsDictPorImg img = new BcsDictPorImg();
			img.setDictPorHead(head);
			img.setSeqNum(maxSeqNum + i + 1);
			img.setInnerMergeSeqNum(tenInnerMerge.getSeqNum());
			img.setComplex(tenInnerMerge.getComplex());
			img.setCommName(tenInnerMerge.getName());
			img.setCommSpec(tenInnerMerge.getSpec());
			img.setComUnit(tenInnerMerge.getComUnit());
			img.setUnitPrice(tenInnerMerge.getPrice());
			img.setCurr(tenInnerMerge.getCurr());
			img.setIsMainImg(tenInnerMerge.getIsMainImg());
			img.setModifyMark(ModifyMarkState.ADDED);
			this.bcsDictPorDao.saveOrUpdate(img);
			lsResult.add(img);
		}
		return lsResult;
	}

	/**
	 * 从内部归并中增加备案资料库成品
	 * 
	 * @param head
	 * @param list
	 * @return
	 */
	public List<BcsDictPorExg> addBcsDictPorExgByInnerMerge(
			BcsDictPorHead head, List list) {
		int maxSeqNum = this.bcsDictPorDao.getMaxDictPorExgSeqNum(head);
		List<BcsDictPorExg> lsResult = new ArrayList<BcsDictPorExg>();
		for (int i = 0; i < list.size(); i++) {
			BcsTenInnerMerge tenInnerMerge = (BcsTenInnerMerge) list.get(i);
			BcsDictPorExg exg = new BcsDictPorExg();
			exg.setDictPorHead(head);
			exg.setSeqNum(maxSeqNum + i + 1);
			exg.setInnerMergeSeqNum(tenInnerMerge.getSeqNum());
			exg.setComplex(tenInnerMerge.getComplex());
			exg.setCommName(tenInnerMerge.getName());
			exg.setCommSpec(tenInnerMerge.getSpec());
			exg.setComUnit(tenInnerMerge.getComUnit());
			exg.setUnitPrice(tenInnerMerge.getPrice());
			exg.setCurr(tenInnerMerge.getCurr());
			exg.setModifyMark(ModifyMarkState.ADDED);
			this.bcsDictPorDao.saveOrUpdate(exg);
			lsResult.add(exg);
		}
		return lsResult;
	}

	/**
	 * 保存备案资料库表头
	 * 
	 * @param head
	 */
	public void saveBcsDictPorHead(BcsDictPorHead head) {
		List list = this.bcsDictPorDao.findBcsDictPorHeadByCopEmsNo(head
				.getCopEmsNo());
		if (ModifyMarkState.ADDED.equals(head.getModifyMark())) {
			for (int i = 0; i < list.size(); i++) {
				BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) list.get(i);
				if (!bcsDictPorHead.getId().equals(head.getId())) {
					throw new RuntimeException("备案资料库内部编号重复！");
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) list.get(i);
				if (!bcsDictPorHead.getId().equals(head.getId())
						&& !bcsDictPorHead.getDictPorEmsNo().equals(
								head.getDictPorEmsNo())) {
					throw new RuntimeException("备案资料库内部编号重复！");
				}
			}
		}
		if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
			head.setModifyMark(ModifyMarkState.MODIFIED);
		}
		this.bcsDictPorDao.saveOrUpdate(head);
	}

	/**
	 * 保存备案资料库料件
	 * 
	 * @param img
	 */
	public void saveBcsDictPorImg(BcsDictPorImg img) {
		this.bcsDictPorDao.saveOrUpdate(img);
	}

	/**
	 * 保存备案资料库成品
	 * 
	 * @param exg
	 */
	public void saveBcsDictPorExg(BcsDictPorExg exg) {
		this.bcsDictPorDao.saveOrUpdate(exg);
	}

	/**
	 * 删处备案资料库表头
	 * 
	 * @param head
	 */
	public void deleteBcsDictPorHead(BcsDictPorHead head) {
		this.bcsDictPorDao.deleteAll(this.bcsDictPorDao
				.findBcsDictPorExgByHead(head));
		this.bcsDictPorDao.deleteAll(this.bcsDictPorDao
				.findBcsDictPorImgByHead(head));
		this.bcsDictPorDao.delete(head);
	}

	/**
	 * 删处备案资料库料件或成品
	 * 
	 * @param list
	 */
	public Map<Integer, List<BcsDictPorImg>> deleteBcsDictPorImg(List list) {
		Map<Integer, List<BcsDictPorImg>> map = new HashMap<Integer, List<BcsDictPorImg>>();
		List<BcsDictPorImg> lsDelete = new ArrayList<BcsDictPorImg>();
		List<BcsDictPorImg> lsUpdate = new ArrayList<BcsDictPorImg>();
		for (int i = 0; i < list.size(); i++) {
			BcsDictPorImg img = (BcsDictPorImg) list.get(i);
			if (ModifyMarkState.ADDED.equals(img.getModifyMark())) {
				this.bcsDictPorDao.delete(img);
				lsDelete.add(img);
			} else if (ModifyMarkState.UNCHANGE.equals(img.getModifyMark())
					|| ModifyMarkState.MODIFIED.equals(img.getModifyMark())) {
				List lsContract = this.bcsDictPorDao
						.findContractByDictPorImg(img);
				if (lsContract.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < lsContract.size(); j++) {
						sb.append(lsContract.get(j).toString() + ";\n");
					}
					throw new RuntimeException("序号为" + img.getSeqNum()
							+ "的料件已被合同\n" + sb.toString() + "引用，所以不能删除");
				}
				img.setModifyMark(ModifyMarkState.DELETED);
				this.bcsDictPorDao.saveOrUpdate(img);
				lsUpdate.add(img);
			}
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 删处备案资料库料件或成品
	 * 
	 * @param list
	 */
	public Map<Integer, List<BcsDictPorExg>> deleteBcsDictPorExg(List list) {
		Map<Integer, List<BcsDictPorExg>> map = new HashMap<Integer, List<BcsDictPorExg>>();
		List<BcsDictPorExg> lsDelete = new ArrayList<BcsDictPorExg>();
		List<BcsDictPorExg> lsUpdate = new ArrayList<BcsDictPorExg>();
		for (int i = 0; i < list.size(); i++) {
			BcsDictPorExg exg = (BcsDictPorExg) list.get(i);
			if (ModifyMarkState.ADDED.equals(exg.getModifyMark())) {
				this.bcsDictPorDao.delete(exg);
				lsDelete.add(exg);
			} else if (ModifyMarkState.UNCHANGE.equals(exg.getModifyMark())
					|| ModifyMarkState.MODIFIED.equals(exg.getModifyMark())) {
				List lsContract = this.bcsDictPorDao
						.findContractByDictPorExg(exg);
				if (lsContract.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < lsContract.size(); j++) {
						sb.append(lsContract.get(j).toString() + ";\n");
					}
					throw new RuntimeException("序号为" + exg.getSeqNum()
							+ "的成品已被合同\n" + sb.toString() + "引用，所以不能删除");
				}
				exg.setModifyMark(ModifyMarkState.DELETED);
				this.bcsDictPorDao.saveOrUpdate(exg);
				lsUpdate.add(exg);
			}
		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 保存备案资料库料件或成品
	 * 
	 * @param list
	 */
	public void saveBcsDictPorImgExg(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.bcsDictPorDao.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 转抄备案资料库
	 * 
	 * @param head
	 */
	public BcsDictPorHead copyBcsDictPor(BcsDictPorHead head) {
		BcsDictPorHead newHead = null;
		try {
			newHead = (BcsDictPorHead) BeanUtils.cloneBean(head);
			newHead.setId(null);
			newHead.setSeqNum(Integer.valueOf(bcsDictPorDao.getNum(
					"BcsDictPorHead", "seqNum")));
			String copEntNo = bcsMessageLogic.getNewCopEntNo("BcsDictPorHead",
					"copEmsNo", "C", BcsBusinessType.EMS_POR_WJ);
			newHead.setCopEmsNo(copEntNo);
			newHead.setDictPorEmsNo("");
			newHead.setDeclareState(DeclareState.APPLY_POR);
			newHead.setModifyMark(ModifyMarkState.ADDED);
			this.bcsDictPorDao.saveOrUpdate(newHead);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List lsExg = this.bcsDictPorDao.findBcsDictPorExgByHead(head);
		for (int i = 0; i < lsExg.size(); i++) {
			BcsDictPorExg exg = (BcsDictPorExg) lsExg.get(i);
			BcsDictPorExg newExg = null;
			try {
				newExg = (BcsDictPorExg) BeanUtils.cloneBean(exg);
				newExg.setId(null);
				newExg.setDictPorHead(newHead);
				newExg.setModifyMark(ModifyMarkState.ADDED);
				this.bcsDictPorDao.saveOrUpdate(newExg);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		List lsImg = this.bcsDictPorDao.findBcsDictPorImgByHead(head);
		for (int i = 0; i < lsImg.size(); i++) {
			BcsDictPorImg img = (BcsDictPorImg) lsImg.get(i);
			BcsDictPorImg newImg = null;
			try {
				newImg = (BcsDictPorImg) BeanUtils.cloneBean(img);
				newImg.setId(null);
				newImg.setDictPorHead(newHead);
				newImg.setModifyMark(ModifyMarkState.ADDED);
				this.bcsDictPorDao.saveOrUpdate(newImg);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return newHead;
	}

	/**
	 * 变更备案资料库
	 * 
	 * @param head
	 */
	public BcsDictPorHead changeBcsDictPor(BcsDictPorHead head) {
		BcsDictPorHead newHead = null;
		try {
			newHead = (BcsDictPorHead) BeanUtils.cloneBean(head);
			newHead.setId(null);
			newHead.setSeqNum(Integer.valueOf(bcsDictPorDao.getNum(
					"BcsDictPorHead", "seqNum")));
			newHead.setDeclareState(DeclareState.CHANGING_EXE);
			newHead.setModifyMark(ModifyMarkState.MODIFIED);
			this.bcsDictPorDao.saveOrUpdate(newHead);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List lsExg = this.bcsDictPorDao.findBcsDictPorExgByHead(head);
		for (int i = 0; i < lsExg.size(); i++) {
			BcsDictPorExg exg = (BcsDictPorExg) lsExg.get(i);
			BcsDictPorExg newExg = null;
			try {
				newExg = (BcsDictPorExg) BeanUtils.cloneBean(exg);
				newExg.setId(null);
				newExg.setDictPorHead(newHead);
				this.bcsDictPorDao.saveOrUpdate(newExg);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		List lsImg = this.bcsDictPorDao.findBcsDictPorImgByHead(head);
		for (int i = 0; i < lsImg.size(); i++) {
			BcsDictPorImg img = (BcsDictPorImg) lsImg.get(i);
			BcsDictPorImg newImg = null;
			try {
				newImg = (BcsDictPorImg) BeanUtils.cloneBean(img);
				newImg.setId(null);
				newImg.setDictPorHead(newHead);
				this.bcsDictPorDao.saveOrUpdate(newImg);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return newHead;
	}

	/**
	 * 备案资料库备案申请
	 * 
	 * @param taskId
	 * @param bcsDictPorHead
	 * @return
	 */
	public DeclareFileInfo applyBcsDictPor(String taskId,
			BcsDictPorHead bcsDictPorHead) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsBcsPtsSignInfo = new ArrayList<CspSignInfo>();
		List<BcsDictPorHead> lsBcsDictPorHead = new ArrayList<BcsDictPorHead>();
		List<BcsDictPorExg> lsBcsDictPorExg = new ArrayList<BcsDictPorExg>();
		List<BcsDictPorImg> lsBcsDictPorImg = new ArrayList<BcsDictPorImg>();
		System.out.println("Begin get data:" + System.currentTimeMillis());
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		String formatFile = "BcsDictPorFormat.xml";
		bcsDictPorHead = this.bcsDictPorDao
				.findBcsDictPorHeadById(bcsDictPorHead.getId());
		if (bcsDictPorHead == null) {
			throw new RuntimeException("没有物料表头");
		}
		CspSignInfo signInfo = bcsMessageLogic.getCspPtsSignInfo(info,
				bcsDictPorHead.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(bcsDictPorHead.getCopEmsNo());
		signInfo.setColDcrTime(0);
		if (ManageObject.MANUFACTURE_COP.equals(bcsDictPorHead
				.getManageObject())) {
			signInfo.setTradeCode(bcsDictPorHead.getMachCode());
		} else if (ManageObject.MANAGE_COP.equals(bcsDictPorHead
				.getManageObject())) {
			signInfo.setTradeCode(bcsDictPorHead.getTradeCode());
		} else {
			throw new RuntimeException("获取签名信息出错：未知的管理对象类型："
					+ bcsDictPorHead.getManageObject());
		}
		signInfo.setSysType(BcsBusinessType.EMS_POR_WJ);
		if (DeclareState.CHANGING_EXE.equals(bcsDictPorHead.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);
		} else if (DeclareState.APPLY_POR.equals(bcsDictPorHead
				.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.APPLICATION);
		} else {
			throw new RuntimeException("备案资料库的申报状态不是<初始状态>或<变更状态>，所以不能申报!");
		}
		lsBcsPtsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		bcsDictPorHead.setInputER(signInfo.getIdCard().substring(
				signInfo.getIdCard().length() - 4));
		bcsDictPorHead.setDeclareDate(new Date());
		bcsDictPorHead.setDeclareState(DeclareState.WAIT_EAA);
		lsBcsDictPorHead.add(bcsDictPorHead);
		// 料件
		lsBcsDictPorImg = this.bcsDictPorDao
				.findBcsDictPorImgStateChanged(bcsDictPorHead);
		// 成品
		lsBcsDictPorExg = this.bcsDictPorDao
				.findBcsDictPorExgStateChanged(bcsDictPorHead);
		hmData.put("BcsPtsSignInfo", lsBcsPtsSignInfo);
		hmData.put("BcsDictPorHeadData", lsBcsDictPorHead);
		hmData.put("BcsDictPorExgData", lsBcsDictPorExg);
		hmData.put("BcsDictPorImgData", lsBcsDictPorImg);
		DeclareFileInfo fileInfo = bcsMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.bcsDictPorDao.saveOrUpdate(bcsDictPorHead);
		return fileInfo;
	}

	/**
	 * 备案资料库备案回执处理
	 * 
	 * @param head
	 * @param exingHead
	 * @return
	 */
	public String processBcsDictPor(final BcsDictPorHead head,final BcsDictPorHead exingHead, List lsReturnFile) {
		return bcsMessageLogic.processMessage(BcsBusinessType.EMS_POR_WJ, head
				.getCopEmsNo(), new CspProcessMessage() {

			public void failureHandling(
					TempCspReceiptResultInfo tempReceiptResult) {
				backBillBcsDictPorHead(head, exingHead);
			}

			public void successHandling(
					TempCspReceiptResultInfo tempReceiptResult) {
				CspReceiptResult receiptResult = tempReceiptResult
						.getReceiptResult();
				effectiveBcsDictPorHead(head, exingHead, receiptResult);
			}
		}, lsReturnFile);
	}

	/**
	 * 生效备案资料库备案
	 * 
	 * @param head
	 *            合同备案表头
	 * @param exingHead
	 *            变更后合同备案表头
	 */
	private void effectiveBcsDictPorHead(BcsDictPorHead head,
			BcsDictPorHead exingHead, CspReceiptResult receiptResult) {
		if (exingHead != null) {
			this.deleteBcsDictPorHead(exingHead);
		}
		head.setDeclareState(DeclareState.PROCESS_EXE);
		head.setModifyMark(ModifyMarkState.UNCHANGE);
		if (exingHead == null && receiptResult != null) {
			head.setDictPorEmsNo(receiptResult.getEmsNo());
		}
		this.bcsDictPorDao.saveOrUpdate(head);
		List lsExg = this.bcsDictPorDao.findBcsDictPorExgStateChanged(head);
		for (int i = 0; i < lsExg.size(); i++) {
			BcsDictPorExg exg = (BcsDictPorExg) lsExg.get(i);
			if (ModifyMarkState.DELETED.equals(exg.getModifyMark())) {
				this.bcsDictPorDao.delete(exg);
			} else {
				exg.setModifyMark(ModifyMarkState.UNCHANGE);
				this.bcsDictPorDao.saveOrUpdate(exg);
			}
		}
		List lsImg = this.bcsDictPorDao.findBcsDictPorImgStateChanged(head);
		for (int i = 0; i < lsImg.size(); i++) {
			BcsDictPorImg img = (BcsDictPorImg) lsImg.get(i);
			if (ModifyMarkState.DELETED.equals(img.getModifyMark())) {
				this.bcsDictPorDao.delete(img);
			} else {
				img.setModifyMark(ModifyMarkState.UNCHANGE);
				this.bcsDictPorDao.saveOrUpdate(img);
			}
		}
	}

	/**
	 * 获取备案资料库备案回执的手册号
	 * 
	 * @param copEmsNo
	 * @return
	 */
	public String getDictPorHeadReturnTempEmsNo(String copEmsNo) {
		List list = this.bcsMessageLogic.findNotProcessReturnFile(
				BcsBusinessType.EMS_POR_WJ, copEmsNo);
		for (int i = 0; i < list.size(); i++) {
			CspReceiptResult result = (CspReceiptResult) list.get(i);
			if (CspProcessResult.IN_STOCK_SUCCEED.equals(result.getChkMark())) {
				return result.getEmsNo();
			}
		}
		return "";
	}

	/**
	 * 备案资料库备案退单
	 * 
	 * @param head
	 *            合同备案表头
	 * @param exingHead
	 *            变更后合同备案表头
	 */
	private void backBillBcsDictPorHead(BcsDictPorHead head,
			BcsDictPorHead exingHead) {
		if (exingHead == null) {
			head.setDeclareState(DeclareState.APPLY_POR);
		} else {
			head.setDeclareState(DeclareState.CHANGING_EXE);
		}
		// wjHead.setDeclareState(DzscState.BACK_BILL);
		this.bcsDictPorDao.saveOrUpdate(head);
	}

	/**
	 * 保存备案资料库料件
	 * 
	 * @param ls
	 *            存放数据的List
	 * @param isOverwrite
	 *            是否覆盖原有数据
	 */
	public void saveBcsDictImgFromImport(List ls, boolean isOverwrite) {
		BcsDictPorHead bcsEmsPorHead = null;
		if (ls.size() > 0) {
			bcsEmsPorHead = ((TempBcsDictPorImg) ls.get(0)).getImg()
					.getDictPorHead();
		} else {
			return;
		}
		// 先获取已有的数据
		List lsImgBill = this.bcsDictPorDao
				.findBcsDictPorImgByHead(bcsEmsPorHead);
		Map<Integer, BcsDictPorImg> imgMap = new HashMap<Integer, BcsDictPorImg>();
		for (int i = 0; i < lsImgBill.size(); i++) {
			BcsDictPorImg imgBill = (BcsDictPorImg) lsImgBill.get(i);
			imgMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ls.size(); i++) {
			BcsDictPorImg imgBill = imgMap.get(((TempBcsDictPorImg) ls.get(i))
					.getImg().getSeqNum());
			if (imgBill == null) {// 如果不存在，则直接保存
				imgBill = ((TempBcsDictPorImg) ls.get(i)).getImg();
				imgBill.setModifyMark(ModifyMarkState.ADDED);
				this.bcsDictPorDao.saveOrUpdate(imgBill);
			} else {
				if (isOverwrite) {
					BcsDictPorImg img = ((TempBcsDictPorImg) ls.get(i))
							.getImg();
					imgBill.setComplex(img.getComplex());
					imgBill.setCommName(img.getCommName());
					imgBill.setCommSpec(img.getCommSpec());
					imgBill.setUnitPrice(img.getUnitPrice());
					imgBill.setComUnit(img.getComUnit());
					imgBill.setCurr(img.getCurr());
					imgBill.setInnerMergeSeqNum(img.getInnerMergeSeqNum());
					imgBill.setIsMainImg(img.getIsMainImg());
					if (ModifyMarkState.UNCHANGE
							.equals(imgBill.getModifyMark())) {
						imgBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.bcsDictPorDao.saveOrUpdate(imgBill);
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * 保存备案资料库成品
	 * 
	 * @param ls
	 *            存放数据的List
	 * @param isOverwrite
	 *            是否覆盖原有数据
	 */
	public void saveBcsDictExgFromImport(List ls, boolean isOverwrite) {
		BcsDictPorHead bcsEmsPorHead = null;
		if (ls.size() > 0) {
			bcsEmsPorHead = ((TempBcsDictPorExg) ls.get(0)).getExg()
					.getDictPorHead();
		} else {
			return;
		}
		// 先获取已有的数据
		List lsImgBill = this.bcsDictPorDao
				.findBcsDictPorExgByHead(bcsEmsPorHead);
		Map<Integer, BcsDictPorExg> imgMap = new HashMap<Integer, BcsDictPorExg>();
		for (int i = 0; i < lsImgBill.size(); i++) {
			BcsDictPorExg imgBill = (BcsDictPorExg) lsImgBill.get(i);
			imgMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ls.size(); i++) {
			BcsDictPorExg imgBill = imgMap.get(((TempBcsDictPorExg) ls.get(i))
					.getExg().getSeqNum());
			if (imgBill == null) {// 如果不存在，则直接保存
				imgBill = ((TempBcsDictPorExg) ls.get(i)).getExg();
				imgBill.setModifyMark(ModifyMarkState.ADDED);
				this.bcsDictPorDao.saveOrUpdate(imgBill);
			} else {
				if (isOverwrite) {
					BcsDictPorExg img = ((TempBcsDictPorExg) ls.get(i))
							.getExg();
					imgBill.setComplex(img.getComplex());
					imgBill.setCommName(img.getCommName());
					imgBill.setCommSpec(img.getCommSpec());
					imgBill.setUnitPrice(img.getUnitPrice());
					imgBill.setComUnit(img.getComUnit());
					imgBill.setCurr(img.getCurr());
					imgBill.setInnerMergeSeqNum(img.getInnerMergeSeqNum());
					if (ModifyMarkState.UNCHANGE
							.equals(imgBill.getModifyMark())) {
						imgBill.setModifyMark(ModifyMarkState.MODIFIED);
					}
					this.bcsDictPorDao.saveOrUpdate(imgBill);
				} else {
					continue;
				}
			}
		}
	}

	/**
	 * 检查备案资料库
	 * 
	 * @param list
	 *            备案资料库list
	 * @return
	 */
	public List bcsDictPorCheckup(List list) {
		List arrayList = new ArrayList();
		for (int j = 0; j < list.size(); j++) {
			BcsDictPorHead head = (BcsDictPorHead) list.get(j);
			if (head.getDeclareState().equals(DeclareState.PROCESS_EXE)
					|| head.getDeclareState().equals(
							DeclareState.CHANGING_CANCEL))
				continue;
			StringBuffer headErr = new StringBuffer("");

			// 表头检查
			if (head.getCopEmsNo() == null || head.getCopEmsNo().equals(""))// 企业内部编号
				headErr.append("企业内部编号不可为空;");
			if (head.getDeclareCustoms() == null)// 主管海关
				headErr.append("主管海关不可为空;");
			if (head.getMachCode() == null || head.getMachCode().equals(""))// 加工单位编号
				headErr.append("加工单位编号不可为空;");
			if (head.getMachName() == null || head.getMachName().equals(""))// 加工单位名称
				headErr.append("加工单位名称不可为空;");
			if (head.getProductRatio() == null || head.getProductRatio() == 0)// 生产能力
				headErr.append("生产能力不可为空;");
			// if (head.getDeclareDate() == null)// 申报日期
			// headErr.append("申报日期不可为空;");
			if (head.getLevyKind() == null)// 征免性质
				headErr.append("征免性质不可为空;");
			if (head.getManageObject() == null
					|| head.getManageObject().equals(""))// 管理对象
				headErr.append("管理对象不可为空;");
			if (head.getTrade() == null)// 贸易方式
				headErr.append("贸易方式不可为空;");
			if (head.getReceiveArea() == null)// 地区代码
				headErr.append("地区代码不可为空;");
			if (head.getCurr() == null)// 币制
				headErr.append("币制不可为空;");
			if (head.getMachiningType() == null)// 加工种类
				headErr.append("加工种类不可为空;");
			// if (head.getLimitFlag() == null ||
			// head.getLimitFlag().equals(""))// 限制类标志
			// headErr.append("限制类标志不可为空;");

			List imgList = bcsDictPorDao.findBcsDictPorImgByHead(head);// 料件
			List exgList = bcsDictPorDao.findBcsDictPorExgByHead(head);// 成品
			int img = 0, exg = 0;
			int k = 0;
			while (imgList.size() > 0 && exgList.size() > 0
					&& img < imgList.size() && exg < exgList.size()) {
				// 料件
				String imgError = "";
				BcsDictPorImg porImg = (BcsDictPorImg) imgList.get(img);
				img++;
				if (porImg.getSeqNum() == null)// 序号
					imgError += "序号不可为空;";
				if (porImg.getComplex() == null) {// 商品编码
					imgError += "商品编码不可为空;";
					imgError += "第一法定单位不可为空;";
				}
				if (porImg.getCommName() == null
						|| porImg.getCommName().equals(""))// 商品名称
					imgError += "商品名称不可为空;";
				if (porImg.getComUnit() == null)// 计量单位
					imgError += "计量单位不可为空;";
				if (porImg.getComplex() != null
						&& porImg.getComplex().getFirstUnit() == null)// 第一法定单位
					imgError += "第一法定单位不可为空;";
				if (porImg.getModifyMark() == null
						|| porImg.getModifyMark().equals(""))// 修改标志
					imgError += "修改标志不可为空;";

				// 成品
				String exgError = "";
				BcsDictPorExg porExg = (BcsDictPorExg) exgList.get(exg);
				exg++;
				if (porExg.getSeqNum() == null)// 序号
					exgError += "序号不可为空;";
				if (porExg.getComplex() == null) {// 商品编码
					exgError += "商品编码不可为空;";
					exgError += "第一法定单位不可为空;";
				}
				if (porExg.getCommName() == null
						|| porExg.getModifyMark().equals(""))// 商品名称
					exgError += "商品名称不可为空;";
				if (porExg.getComUnit() == null)// 计量单位
					exgError += "计量单位不可为空;";
				if (porExg.getComplex() != null
						&& porExg.getComplex().getFirstUnit() == null)// 第一法定单位
					exgError += "第一法定单位不可为空;";
				if (porExg.getModifyMark() == null
						|| porExg.getModifyMark().equals(""))// 修改标志
					exgError += "修改标志不可为空;";

				if (imgError.equals("") && exgError.equals(""))
					continue;

				if (!imgError.equals("")) {
					k++;
					imgError = "序号" + String.valueOf(porImg.getSeqNum()) + " :"
							+ imgError + "\n";
				}
				if (!exgError.equals("")) {
					k++;
					exgError = "序号" + String.valueOf(porExg.getSeqNum()) + " :"
							+ exgError + "\n";
				}

				TempBcsDictPorCheckup tempBcsDictPorCheckup = new TempBcsDictPorCheckup();
				tempBcsDictPorCheckup.setCopEmsNo(head.getCopEmsNo());
				tempBcsDictPorCheckup.setHeadErr(headErr.toString());
				tempBcsDictPorCheckup.setImgErr(imgError.toString());
				tempBcsDictPorCheckup.setExgErr(exgError.toString());
				arrayList.add(tempBcsDictPorCheckup);
			}

			if (imgList.size() < exgList.size()) {
				while (exg < exgList.size()) {
					// 成品
					String exgError = "";
					BcsDictPorExg porExg = (BcsDictPorExg) exgList.get(exg);
					exg++;
					if (porExg.getSeqNum() == null)// 序号
						exgError += "序号不可为空;";
					if (porExg.getComplex() == null) {// 商品编码
						exgError += "商品编码不可为空;";
						exgError += "第一法定单位不可为空;";
					}
					if (porExg.getCommName() == null
							|| porExg.getModifyMark().equals(""))// 商品名称
						exgError += "商品名称不可为空;";
					if (porExg.getComUnit() == null)// 计量单位
						exgError += "计量单位不可为空;";
					if (porExg.getComplex() != null
							&& porExg.getComplex().getFirstUnit() == null)// 第一法定单位
						exgError += "第一法定单位不可为空;";
					if (porExg.getModifyMark() == null
							|| porExg.getModifyMark().equals(""))// 修改标志
						exgError += "修改标志不可为空;";

					if (exgError.equals(""))
						continue;
					if (!exgError.equals("")) {
						k++;
						exgError = "序号" + String.valueOf(porExg.getSeqNum())
								+ " :" + exgError + "\n";
					}

					String imgError = "";
					if (imgList.isEmpty())
						imgError = "没有料件资料;";
					TempBcsDictPorCheckup tempBcsDictPorCheckup = new TempBcsDictPorCheckup();
					tempBcsDictPorCheckup.setCopEmsNo(head.getCopEmsNo());
					tempBcsDictPorCheckup.setHeadErr(headErr.toString());
					tempBcsDictPorCheckup.setImgErr(imgError.toString());
					tempBcsDictPorCheckup.setExgErr(exgError.toString());
					arrayList.add(tempBcsDictPorCheckup);
				}
			} else if (imgList.size() > exgList.size()) {
				while (img < imgList.size()) {
					// 料件
					String imgError = "";
					BcsDictPorImg porImg = (BcsDictPorImg) imgList.get(img);
					img++;
					if (porImg.getSeqNum() == null)// 序号
						imgError += "序号不可为空;";
					if (porImg.getComplex() == null) {// 商品编码
						imgError += "商品编码不可为空;";
						imgError += "第一法定单位不可为空;";
					}
					if (porImg.getCommName() == null
							|| porImg.getCommName().equals(""))// 商品名称
						imgError += "商品名称不可为空;";
					if (porImg.getComUnit() == null)// 计量单位
						imgError += "计量单位不可为空;";
					if (porImg.getComplex() != null
							&& porImg.getComplex().getFirstUnit() == null)// 第一法定单位
						imgError += "第一法定单位不可为空;";
					if (porImg.getModifyMark() == null
							|| porImg.getModifyMark().equals(""))// 修改标志
						imgError += "修改标志不可为空;";

					if (imgError.equals(""))
						continue;

					if (!imgError.equals("")) {
						k++;
						imgError = "序号" + String.valueOf(porImg.getSeqNum())
								+ " :" + imgError + "\n";
					}
					String exgError = "";
					if (exgList.isEmpty())
						exgError = "没有成品资料;";
					TempBcsDictPorCheckup tempBcsDictPorCheckup = new TempBcsDictPorCheckup();
					tempBcsDictPorCheckup.setCopEmsNo(head.getCopEmsNo());
					tempBcsDictPorCheckup.setHeadErr(headErr.toString());
					tempBcsDictPorCheckup.setImgErr(imgError.toString());
					tempBcsDictPorCheckup.setExgErr(exgError.toString());
					arrayList.add(tempBcsDictPorCheckup);
				}
			} else if (imgList.isEmpty() && exgList.isEmpty()) {
				if (!headErr.toString().equals("")) {
					TempBcsDictPorCheckup tempBcsDictPorCheckup = new TempBcsDictPorCheckup();
					tempBcsDictPorCheckup.setCopEmsNo(head.getCopEmsNo());
					tempBcsDictPorCheckup.setHeadErr(headErr.toString());
					tempBcsDictPorCheckup.setImgErr("没有料件资料;");
					tempBcsDictPorCheckup.setExgErr("没有成品资料;");
					arrayList.add(tempBcsDictPorCheckup);
				}
			}

			if (k == 0 && !headErr.toString().equals("")
					&& (!imgList.isEmpty() || !exgList.isEmpty())) {
				String imgError = "";// 料件
				String exgError = "";// 成品
				if (exgList.isEmpty()) {
					exgError = "没有成品资料";
				} else if (imgList.isEmpty()) {
					imgError = "没有料件资料;";
				}
				TempBcsDictPorCheckup tempBcsDictPorCheckup = new TempBcsDictPorCheckup();
				tempBcsDictPorCheckup.setCopEmsNo(head.getCopEmsNo());
				tempBcsDictPorCheckup.setHeadErr(headErr.toString());
				tempBcsDictPorCheckup.setImgErr(imgError);
				tempBcsDictPorCheckup.setExgErr(exgError);
				arrayList.add(tempBcsDictPorCheckup);
			}
		}
		return arrayList;
	}

	/**
	 * 改变备案资料库表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	public BcsDictPorHead changeDictPorHeadDeclareState(BcsDictPorHead head,
			String declareState) {
		head = (BcsDictPorHead) this.bcsDictPorDao.load(head.getClass(), head
				.getId());
		if (head == null
				|| !DeclareState.WAIT_EAA.equals(head.getDeclareState())) {
			return head;
		}
		head.setDeclareState(declareState);
		this.bcsDictPorDao.saveOrUpdate(head);
		return head;
	}

	/**
	 * 改变备案资料库料件成品的修改标志
	 * 
	 * @param head
	 * @param declareState
	 */
	public void changeDictPorImgExgModifyMark(List list, String modifyMark) {
		if (list.size() <= 0) {
			return;
		}
		Method methodSet = null;
		Method methodGet = null;
		try {
			methodSet = list.get(0).getClass().getMethod("setModifyMark",
					new Class[] { String.class });
			methodGet = list.get(0).getClass().getMethod("getModifyMark");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (methodSet == null || methodGet == null) {
			System.out.println(methodSet == null ? "methodSet is null"
					: "methodSet is not null ");
			System.out.println(methodGet == null ? "methodGet is null"
					: "methodGet is not null ");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			try {
				String oldModifyMark = (String) methodGet.invoke(obj, null);
				// if (ModifyMarkState.ADDED.equals(oldModifyMark)) {
				// continue;
				// }
				methodSet.invoke(obj, modifyMark);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			this.bcsDictPorDao.saveOrUpdate(obj);
		}
	}

	/**
	 * 变更备案资料库编号
	 * 
	 * @param head
	 * @param declareState
	 */
	public List changeDictPorEmsNo(String oldDictPorEmsNo, String dictPorEmsNo) {
		// head=(BcsDictPorHead)this.bcsDictPorDao.load(head.getClass(),
		// head.getId());
		// if(head==null||!DeclareState.WAIT_EAA.equals(head.getDeclareState())){
		// return head;
		// }
		// String oldDictPorEmsNo=head.getDictPorEmsNo();
		if (oldDictPorEmsNo == null || "".equals(oldDictPorEmsNo.trim())) {
			return new ArrayList();
		}
		List<Contract> list = this.bcsDictPorDao
				.findContractByDictPorEmsNo(oldDictPorEmsNo);
		for (Contract contract : list) {
			contract.setCorrEmsNo(dictPorEmsNo);
			this.bcsDictPorDao.saveOrUpdate(contract);
		}
		List<BcsDictPorHead> lsHead = this.bcsDictPorDao
				.findBcsDictPorHeadByEmsNo(oldDictPorEmsNo);
		for (BcsDictPorHead head : lsHead) {
			head.setDictPorEmsNo(dictPorEmsNo);
			this.bcsDictPorDao.saveOrUpdate(head);
		}
		return lsHead;
	}

	/**
	 * 从QP的导出文件中导入备案资料库资料
	 * 
	 * @param excelFileContent
	 */
	public BcsDictPorHead importBcsDictPorHeadFromQPExportFile(
			byte[] excelFileContent, String declareState, String manageObject,
			LevyKind levyKind, Trade trade, District receiveArea, Curr curr,
			MachiningType machiningType, String taskId,boolean isCover) {
		String xmlFileName = "BcsDictPorImportFormat.xml";
		Map map = this.importDataFromExcel.importData(xmlFileName,
				excelFileContent, taskId,isCover,this,declareState);
		Map dataMap = (Map) map.get(BcsDictPorHead.class.getName());
		if (dataMap != null && dataMap.values().iterator().hasNext()) {
			BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) dataMap.values()
					.iterator().next();
			Company company = (Company) bcsDictPorHead.getCompany();
			bcsDictPorHead.setSeqNum(Integer.valueOf(bcsDictPorDao.getNum(
					"BcsDictPorHead", "seqNum")));
			bcsDictPorHead.setDeclareState(declareState);
			if (company != null) {
				bcsDictPorHead.setTradeCode(company.getBuCode());
				bcsDictPorHead.setTradeName(company.getBuName());
			}
			bcsDictPorHead.setManageObject(manageObject);
			bcsDictPorHead.setLevyKind(levyKind);
			bcsDictPorHead.setTrade(trade);
			bcsDictPorHead.setReceiveArea(receiveArea);
			bcsDictPorHead.setCurr(curr);
			bcsDictPorHead.setMachiningType(machiningType);
			bcsDictPorHead.setRedDep(company.getMasterFtc());
			this.bcsDictPorDao.saveOrUpdate(bcsDictPorHead);
			return bcsDictPorHead;
		}
		return null;
	}
	/**
	 * 电子化手册备备案资料库料件备案,(申请备案)(正在变更)
	 * @param request
	 * @param head
	 * @return
	 */
	public BcsDictPorHead putOnRecordBcsDictPor(BcsDictPorHead head){
		//执行状态为1(申请备案)时，直接修改执行状态为3(正在执行),修改标志为【未修改】,并更新料件表dictporImg，成品表dictporExg中的修改标志为【未修改】modifyMark
		//执行状态为4(正在变更)时，修改当前备案库ID的执行状态为3,修改标志为【未修改】,并更新料件表dictporImg，成品表dictporExg中的修改标志为【未修改】
		//,把旧的执行状态为3的备案库删除。（key=备案资料库编号+备案状态）
		String declareState = head.getDeclareState();
		if(DeclareState.APPLY_POR.equals(declareState)){
			head.setDeclareState(DeclareState.PROCESS_EXE);
			head.setModifyMark(ModifyMarkState.UNCHANGE);
			this.saveBcsDictPorHead(head);
			List<BcsDictPorImg> imgList = this.bcsDictPorDao.findBcsDictPorImgByHead(head);
			for (BcsDictPorImg img : imgList) {
				img.setModifyMark(ModifyMarkState.UNCHANGE);//修改标志=0,未修改
			}
			this.bcsDictPorDao.batchSaveOrUpdate(imgList);
			List<BcsDictPorExg> emgList = this.bcsDictPorDao.findBcsDictPorExgByHead(head); 
			for (BcsDictPorExg exg : emgList) {
				exg.setModifyMark(ModifyMarkState.UNCHANGE);//修改标志=0,未修改
			}
			this.bcsDictPorDao.batchSaveOrUpdate(emgList);
		}else if(DeclareState.CHANGING_EXE.equals(declareState)) {
			//删除掉旧的
			BcsDictPorHead oldHead = (BcsDictPorHead) this.bcsDictPorDao
					.findBcsDictPorHead(head.getDictPorEmsNo(),
							DeclareState.PROCESS_EXE).get(0);
			this.deleteBcsDictPorHead(oldHead);
			head.setDeclareState(DeclareState.PROCESS_EXE);
			head.setModifyMark(ModifyMarkState.UNCHANGE);
			this.saveBcsDictPorHead(head);
			List<BcsDictPorImg> imgList = this.bcsDictPorDao.findBcsDictPorImgByHead(head);
			for (BcsDictPorImg img : imgList) {
				img.setModifyMark(ModifyMarkState.UNCHANGE);//修改标志=0,未修改
			}
			this.bcsDictPorDao.batchSaveOrUpdate(imgList);
			List<BcsDictPorExg> emgList = this.bcsDictPorDao.findBcsDictPorExgByHead(head); 
			for (BcsDictPorExg exg : emgList) {
				exg.setModifyMark(ModifyMarkState.UNCHANGE);//修改标志=0,未修改
			}
			this.bcsDictPorDao.batchSaveOrUpdate(emgList);
		}
		
		return head;
	}
	
	
	
	/**
	 * 电子化手册备案资料库资料同步报关商品资料的归并序号。
	 * @param head
	 * @return [String(转换信息),ArrayList(料件序号不存在的),ArrayList(成品序号不存在的)]
	 */
	public Object[] updateSeqNum(BcsDictPorHead head) {
		
		int exgSize = 0, imgSize = 0;
		// 查询备案成品
		List<BcsDictPorExg> exgs = bcsDictPorDao.findBcsDictPorExgByHead(head);
		// 查询备案料件
		List<BcsDictPorImg> imgs = bcsDictPorDao.findBcsDictPorImgByHead(head);
		// 查询报关商品
		List<BcsTenInnerMerge> tenInnerMerges = bcsDictPorDao.findBcsTenInnerMerges();
		
		@SuppressWarnings("unchecked")
		Map<String, BcsTenInnerMerge> tenInnerMergeMap = CommonUtils.listToMap(
				tenInnerMerges, new GetKeyValueImpl<BcsTenInnerMerge>() {
					public String getKey(final BcsTenInnerMerge e) {
						return (e.getComplex() == null ? "" : e.getComplex().getCode()) + "," + e.getName()
								+ "," + e.getSpec() + ","
								+ (e.getComUnit() == null ? "" : e.getComUnit().getCode());
					}
				});
		
		BcsDictPorExg exg = null;
		String key = null;
		BcsTenInnerMerge tenInnerMerge = null;
		for (int i = 0; i < exgs.size(); i++) {
			exg = exgs.get(i);
			key = (exg.getComplex() == null ? "" : exg.getComplex().getCode()) + "," + exg.getCommName() + ","
					+ exg.getCommSpec() + "," + (exg.getComUnit() == null ? "" : exg.getComUnit().getCode());
			tenInnerMerge = tenInnerMergeMap.get(key);

			if (tenInnerMerge != null) {
				exg.setInnerMergeSeqNum(tenInnerMerge.getSeqNum());
				exgSize++;
			}
		}
		
		BcsDictPorImg img = null;
		for (int i = 0; i < imgs.size(); i++) {
			img = imgs.get(i);
			key = (img.getComplex() == null ? "" : img.getComplex().getCode()) + "," + img.getCommName() + ","
					+ img.getCommSpec() + "," + (img.getComUnit() == null ? "" : img.getComUnit().getCode());
			tenInnerMerge = tenInnerMergeMap.get(key);

			if (tenInnerMerge != null) {
				img.setInnerMergeSeqNum(tenInnerMerge.getSeqNum());
				imgSize++;
			}
		}
		List<BcsDictPorImg> result1Ls = new ArrayList<BcsDictPorImg>();
		for(int i = 0 ; i < imgs.size() ; i++){
			if(imgs.get(i).getInnerMergeSeqNum() == null || imgs.get(i).getInnerMergeSeqNum() == 0)
				result1Ls.add(imgs.get(i));
		}
		List<BcsDictPorExg> result2Ls = new ArrayList<BcsDictPorExg>();
		for(int i = 0 ; i < exgs.size() ; i++){
			if(exgs.get(i).getInnerMergeSeqNum() == null  || exgs.get(i).getInnerMergeSeqNum() == 0)
				result2Ls.add(exgs.get(i));
		}
		return new Object[]{"一共更新成品序号" + exgSize + "个，料件序号" + imgSize + "个。",result1Ls,result2Ls};
	}
	
	
}
