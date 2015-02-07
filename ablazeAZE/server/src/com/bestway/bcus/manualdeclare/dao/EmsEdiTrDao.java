/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.CommdityForbidHis;
import com.bestway.bcus.manualdeclare.entity.EmsEdiHeadH2kBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImgExgMergerType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 经营范围DAO类
 * 
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 * 
 *         lm checked by 2010-11-05
 */
@SuppressWarnings("unchecked")
public class EmsEdiTrDao extends BaseDao {
	/**
	 * 查询报关常用工厂BOM所有成品的最大版本号
	 * 
	 * @author 石小凯
	 * @return 返回料号为KEY,最大版本号为VALUE的MAP
	 */
	public Map<String, Integer> findMaxMaterielBomVersion() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List list = this
				.find("select a.emsEdiMergerBefore.ptNo,max(a.version),"
						+ "  a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState"
						+ "  from EmsEdiMergerVersion a"
						+ "  where a.company.id=?"
						+ "  group by a.emsEdiMergerBefore.ptNo,"
						+ "  a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState",
						new Object[] { CommonUtils.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null) {
				map.put(objs[0].toString().trim() + "/"
						+ objs[2].toString().trim(), objs[1] == null ? 0
						: Integer.parseInt(objs[1].toString()));
			}
		}

		return map;
	}

	/**
	 * 显示所有经营范围表头数据
	 */
	public List findEmsEdiTrHead() {

		return this
				.find("select a from EmsEdiTrHead a where a.company.id= ? and a.historyState=? order by a.modifyTimes DESC",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false) });
	}

	/**
	 * 根据料件货号查找归并前成品
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 * @author sxk
	 */
	public List findEmsEdiMergerExgBefore(String ptNo) {

		return this.find(" select a from EmsEdiMergerExgBom a "
				+ " where a.company.id= ? and a.ptNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNo });

	}

	/**
	 * 查找经营范围表头资料
	 * 
	 * @return
	 */
	public List findEmsEdiTrHeadAll() {
		return this
				.find("select a from EmsEdiTrHead a where a.company.id= ? order by a.modifyTimes",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找正在执行的经营范围
	 * 
	 * @return
	 */
	public EmsEdiTrHead findEmsEdiTrHeadIng() {
		List list = this
				.find("select a from EmsEdiTrHead a where a.company.id = ? and a.historyState = ? and a.declareState = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), DeclareState.PROCESS_EXE });
		if (list != null && list.size() > 0) {
			return (EmsEdiTrHead) list.get(0);
		}
		return null;
	}

	/**
	 * 显示所有经营范围料件数据
	 * 
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @return
	 */
	public List findEmsEdiTrImg(EmsEdiTrHead emsEdiTrHead) {

		return this
				.find("select a from EmsEdiTrImg a where a.emsEdiTrHead.id= ? and a.company.id= ? order by a.ptNo",
						new Object[] { emsEdiTrHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示修改过的经营范围料件数据
	 * 
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @return
	 */
	public List findEditedEmsEdiTrImg(EmsEdiTrHead emsEdiTrHead) {

		return this
				.find("select a from EmsEdiTrImg a where a.emsEdiTrHead.id= ? and a.modifyMark!=? and a.company.id= ? order by a.ptNo",
						new Object[] { emsEdiTrHead.getId(),
								ModifyMarkState.UNCHANGE,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有经营范围料件数据(报文使用)
	 * 
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @return
	 */

	public List findEmsEdiTrImgToBaowen(EmsEdiTrHead emsEdiTrHead) {

		return this
				.find("select a from EmsEdiTrImg a where  a.modifyMark<>? and a.emsEdiTrHead.id= ? and a.company.id= ? order by a.ptNo",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsEdiTrHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有经营范围成品数据
	 * 
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @return
	 */

	public List findEmsEdiTrExg(EmsEdiTrHead emsEdiTrHead) {

		return this
				.find("select a from EmsEdiTrExg a where emsEdiTrHead.id= ? and a.company.id= ?  order by a.ptNo",
						new Object[] { emsEdiTrHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示已修改的经营范围成品数据
	 * 
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @return
	 */

	public List findEditedEmsEdiTrExg(EmsEdiTrHead emsEdiTrHead) {

		return this
				.find("select a from EmsEdiTrExg a where emsEdiTrHead.id= ? and a.modifyMark!=? and a.company.id= ?  order by a.ptNo",
						new Object[] { emsEdiTrHead.getId(),
								ModifyMarkState.UNCHANGE,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有经营范围成品数据(报文使用)
	 * 
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @return
	 */

	public List findEmsEdiTrExgToBaowen(EmsEdiTrHead emsEdiTrHead) {

		return this
				.find("select a from EmsEdiTrExg a where a.modifyMark<>? and a.emsEdiTrHead.id= ? and a.company.id= ?  order by a.ptNo",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsEdiTrHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存经营范围表头
	 * 
	 * @param emshead
	 *            经营范围表头
	 * @throws DataAccessException
	 */
	public void saveEmsEdiTrHead(EmsEdiTrHead emshead)
			throws DataAccessException {
		this.saveOrUpdate(emshead);
	}

	/**
	 * 保存经营范围料件
	 * 
	 * @param emsImg
	 *            经营范围料件
	 * @throws DataAccessException
	 */
	public void saveEmsEdiTrImg(EmsEdiTrImg emsImg) throws DataAccessException {
		this.saveOrUpdate(emsImg);
	}

	/**
	 * 保存经营范围料件
	 * 
	 * @param emsImg
	 *            经营范围成品
	 * @throws DataAccessException
	 */
	public void saveEmsEdiTrExg(EmsEdiTrExg emsExg) throws DataAccessException {
		this.saveOrUpdate(emsExg);
	}

	/**
	 * 删除经营范围表头
	 * 
	 * @param emshead
	 */
	public void deleteEmsEdiTrHead(EmsEdiTrHead emshead) {
		this.delete(emshead);
	}

	/**
	 * 批量删除经营范围表体
	 * 
	 * @param emshead
	 *            经营范围表头
	 */
	public void deleteEmsEdiTrImgExg(EmsEdiTrHead emshead) {
		this.deleteAll(findEmsEdiTrImg(emshead));
		this.deleteAll(findEmsEdiTrExg(emshead));
	}

	/**
	 * 删除经营范围料件
	 * 
	 * @param emsImg
	 *            经营范围料件
	 */
	public void deleteEmsEdiTrImg(EmsEdiTrImg emsImg) {
		this.delete(emsImg);
	}

	/**
	 * 删除经营范围料件
	 * 
	 * @param emsImg
	 *            经营范围成品
	 */
	public void deleteEmsEdiTrExg(EmsEdiTrExg emsExg) {
		this.delete(emsExg);
	}

	/**
	 * 显示归并关系表头
	 * 
	 * @return
	 */
	public List findEmsEdiMergerHead() {
		return this
				.find("select a from EmsEdiMergerHead a where a.company.id= ? and a.historyState=? order by a.modifyTimes DESC",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false) });
	}

	/**
	 * 查找电子帐册归并关系表头资料
	 * 
	 * @return
	 */
	public List findEmsEdiMergerHeadAll() {
		return this
				.find("select a from EmsEdiMergerHead a where a.company.id= ? order by a.modifyTimes",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找BCUS参数设置是否设置了电子帐册归并关系是否分批发送
	 * 
	 * @return
	 */
	private boolean getIsMergerSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsSEND,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得归并关系的表头来自申报状态(非历史记录)
	 * 
	 * @param declareState
	 *            申报状态
	 * @return
	 */
	public EmsEdiMergerHead findEmsEdiMergerHeadByDeclareState(
			String declareState) {
		if (getIsMergerSend()) {
			List list = this.find(
					"select a from EmsEdiMergerHead a where a.company.id= ? "
							+ "and a.historyState=?  ", new Object[] {
							CommonUtils.getCompany().getId(),
							new Boolean(false) });
			if (list == null || list.size() <= 0) {
				return null;
			}
			return (EmsEdiMergerHead) list.get(0);
		} else {
			List list = this
					.find("select a from EmsEdiMergerHead a where a.company.id= ? "
							+ "and a.historyState=? " + "and a.declareState=? ",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false), declareState });
			if (list == null || list.size() <= 0) {
				return null;
			}
			return (EmsEdiMergerHead) list.get(0);
		}
	}

	/**
	 * 获得归并关系中归并前的商品信息---成品
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeByEdiMerger(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select e.ptNo from EmsEdiMergerExgBefore e where e.emsEdiMergerExgAfter.id in "
						+ " (select a.id from EmsEdiMergerExgAfter a where"
						+ " a.emsEdiMergerHead.id = ? ) and e.company.id = ? ",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得归并关系中归并前的商品信息---成品
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeBySeqNum(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select e.ptNo,e.seqNum from EmsEdiMergerExgBefore e where e.emsEdiMergerExgAfter.id in "
						+ " (select a.id from EmsEdiMergerExgAfter a where"
						+ " a.emsEdiMergerHead.id = ? ) and e.company.id = ? ",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得归并关系中归并前的商品信息---料件
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeBySeqNum(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select e.ptNo,e.seqNum from EmsEdiMergerImgBefore e where e.emsEdiMergerImgAfter.id in "
						+ " (select a.id from EmsEdiMergerImgAfter a where"
						+ " a.emsEdiMergerHead.id = ? ) and e.company.id = ?",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得归并关系中归并前的商品信息---料件
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeByEdiMerger(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select e.ptNo from EmsEdiMergerImgBefore e where e.emsEdiMergerImgAfter.id in "
						+ " (select a.id from EmsEdiMergerImgAfter a where"
						+ " a.emsEdiMergerHead.id = ? ) and e.company.id = ? ",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并后料件
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgAfter(EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsMerger.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并后料件
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgAfterForProcessMess(
			EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ? and a.sendState = ? order by a.seqNum",
						new Object[] { emsMerger.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 根据料件序号查找归并关系归并后料件
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @param seqNum
	 *            料件序号
	 * @return
	 */
	public EmsEdiMergerImgAfter findEmsEdiMergerImgAfterBySeqNum(
			EmsEdiMergerHead emsMerger, Integer seqNum) {
		List ls = this
				.find("select a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ? and a.seqNum = ? order by a.seqNum",
						new Object[] { emsMerger.getId(),
								CommonUtils.getCompany().getId(), seqNum });
		if (ls != null && ls.size() > 0) {
			return (EmsEdiMergerImgAfter) ls.get(0);
		}
		return null;
	}

	/**
	 * 根据料件序号查找归并关系归并后成品
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @param seqNum
	 *            成品序号
	 * @return
	 */
	public EmsEdiMergerExgAfter findEmsEdiMergerExgAfterBySeqNum(
			EmsEdiMergerHead emsMerger, Integer seqNum) {
		List ls = this
				.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ? and a.seqNum = ? order by a.seqNum",
						new Object[] { emsMerger.getId(),
								CommonUtils.getCompany().getId(), seqNum });
		if (ls != null && ls.size() > 0) {
			return (EmsEdiMergerExgAfter) ls.get(0);
		}
		return null;
	}

	/**
	 * 显示归并关系归并后料件--报文使用
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgAfterToBaowen(EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerImgAfter a where a.modifyMark<>? and a.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsMerger.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并后料件--报文使用
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgAfterToBaowenBySendState(
			EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerImgAfter a where a.sendState = ? and a.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsMerger.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并前料件（由归并后料件一条条过滤）
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后料件
	 * @return
	 */
	public List findEmsEdiMergerImgBefore(EmsEdiMergerImgAfter emsAfter) {
		return this
				.find("select a from EmsEdiMergerImgBefore a where a.emsEdiMergerImgAfter.id=? and a.company.id= ?",
						new Object[] { emsAfter.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除归并前所有料件
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并前料件
	 */
	public void deleteEmsEdiMergerImgBeforeAll(EmsEdiMergerImgAfter emsAfter) {
		this.deleteAll(findEmsEdiMergerImgBefore(emsAfter));
	}

	/**
	 * 删除归并前所有成品
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并前成品
	 */
	public void deleteEmsEdiMergerExgBeforeAll(EmsEdiMergerExgAfter emsAfter) {
		this.deleteAll(findEmsEdiMergerExgBefore(emsAfter));
	}

	/**
	 * 显示归并关系归并前料件（所有归并前料件）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBefore(EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerImgBefore a where a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并前料件（所有归并前料件--报文使用）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeForProceMess(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerImgBefore a where a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.company.id= ? and a.sendState = ?  order by a.seqNum",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示归并关系归并前料件（所有归并前料件--报文使用）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeToBaowen(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerImgBefore a where a.modifyMark<>? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并前料件（所有归并前料件--报文使用）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeToBaowenBySendState(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerImgBefore a where a.sendState = ? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并后成品
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgAfter(EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsMerger.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并后成品
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgAfterForProceMess(EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ? and a.sendState = ? order by a.seqNum",
						new Object[] { emsMerger.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示归并关系归并后成品--报文使用
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgAfterToBaowen(EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerExgAfter a where a.modifyMark<>? and a.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsMerger.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并后成品--报文使用
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgAfterToBaowenBySendState(
			EmsEdiMergerHead emsMerger) {
		return this
				.find("select a from EmsEdiMergerExgAfter a where a.sendState = ? and a.emsEdiMergerHead.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsMerger.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 归并关系新增BOM--来自归并前料件资料
	 * 
	 * @param emsEdiMergerVersion
	 *            归并关系版本
	 * @param emsHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerAfterImgToBom(
			EmsEdiMergerVersion emsEdiMergerVersion, EmsEdiMergerHead emsHead) {
		// return this
		// .find("select a from EmsEdiMergerImgBefore a where a.company.id= ? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id= ?"
		// +
		// " and a.ptNo not in (select c.ptNo from EmsEdiMergerExgBom c where c.emsEdiMergerVersion.id=?)",
		// new Object[] { CommonUtils.getCompany().getId(),
		// emsHead.getId(), emsEdiMergerVersion.getId(), });
		// 2014-8-13已在归并关系禁用的商品不允许在BOM表中新增
		return this
				.find("select a from EmsEdiMergerImgBefore a "
						+ " left outer join fetch a.complex "
						+ " left outer join fetch a.emsEdiMergerImgAfter b "
						+ " left outer join fetch b.emsEdiMergerHead "
						+ " ,InnerMergeData e "
						+ " where a.ptNo=e.materiel.ptNo and (e.isForbid is null or e.isForbid='' or e.isForbid =0) "
						+ " and a.company.id= ? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id= ? "
						+ " and not exists "
						+ "(select c.ptNo from EmsEdiMergerExgBom c where c.emsEdiMergerVersion.id=? and c.ptNo=a.ptNo)",
						new Object[] { CommonUtils.getCompany().getId(),
								emsHead.getId(), emsEdiMergerVersion.getId(), });
	}

	/**
	 * 显示归并关系归并前成品（由归并后成品一条条过滤）
	 * 
	 * @param emsExgAfter
	 *            电子帐册归并关系归并后成品
	 * @return
	 */
	public List findEmsEdiMergerExgBefore(EmsEdiMergerExgAfter emsExgAfter) {
		return this
				.find("select a from EmsEdiMergerExgBefore a where a.emsEdiMergerExgAfter.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsExgAfter.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并前成品（所有归并前成品）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBefore(EmsEdiMergerHead emsEdiMergerHead) {
		return this.find("select a from EmsEdiMergerExgBefore a"
				+ " left join fetch a.emsEdiMergerExgAfter b"
				+ " left join fetch b.emsEdiMergerHead c"
				+ " where c.id=? and a.company.id= ? order by a.seqNum",
				new Object[] { emsEdiMergerHead.getId(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并前成品（所有归并前成品）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeForProceMess(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBefore a where a.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ? and a.sendState = ?  order by a.seqNum",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示归并关系归并前成品（所有归并前成品--报文使用）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeToBaowen(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBefore a where a.modifyMark<>? and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ?",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系归并前成品
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeToBaowenBySendState(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBefore a where a.sendState = ? and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ?",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系Bom(由归并前成品一条条过滤)
	 * 
	 * @param emsExgBefore
	 *            归并前成品
	 * @return
	 */
	public List findEmsEdiMergerBom(EmsEdiMergerExgBefore emsExgBefore) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.id=? and a.company.id= ? order by a.ptNo",
						new Object[] { emsExgBefore.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系Bom(由归并前成品一条条过滤)
	 * 
	 * @param versionObj
	 *            归并关系版本
	 * @return
	 */
	public List findEmsEdiMergerBomByVersion(EmsEdiMergerVersion versionObj) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.id=? and a.company.id= ? order by a.ptNo",
						new Object[] { versionObj.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除所有BOM
	 * 
	 * @param emsExgBefore
	 *            归并前成品
	 */
	public void deleteEmsEdiMergerMergerBomAll(
			EmsEdiMergerExgBefore emsExgBefore) {
		this.deleteAll(findEmsEdiMergerBom(emsExgBefore));
		this.deleteAll(findEmsEdiMergerVersion(emsExgBefore));
	}

	/**
	 * 显示归并关系Bom(由归并后成品得出)
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后成品
	 * @return
	 */
	public List findEmsEdiMergerBomByAfter(EmsEdiMergerExgAfter emsAfter) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.id=? and a.company.id= ?",
						new Object[] { emsAfter.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系Bom(由归并后成品得出)
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后成品
	 * @return
	 */
	public List findEmsEdiMergerVersionByAfter(EmsEdiMergerExgAfter emsAfter) {
		return this
				.find("select a from EmsEdiMergerVersion a where a.emsEdiMergerBefore.emsEdiMergerExgAfter.id=? and a.company.id= ?",
						new Object[] { emsAfter.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找内部归并数据块来自 四码归并序号
	 * 
	 * @param materielType
	 *            物料类别
	 * @param fourNo
	 *            四码归并序号
	 * @return
	 */
	public List findInnerMergeByFourNo(String materielType, int fourNo) {
		return this.find(" select a from InnerMergeData as a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit"
				+ " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit"
				+ " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType=? and a.company.id=? and a.hsFourNo = ? ",
				new Object[] { materielType, CommonUtils.getCompany().getId(),
						new Integer(fourNo) });
	}

	/**
	 * 根据内部归并的4码备案号查询
	 * 
	 * @param hsFourNO
	 *            四码序号
	 * @param materielType
	 *            物料类别
	 * @return
	 */
	public List findReverseMergeFourDataByHsFourNo(Integer hsFourNO,
			String imrType) {
		return this
				.find(" select a from ReverseMergeFourData as a where a.hsFourNo=? and a.company.id=? and a.imrType=? order by a.hsFourNo ",
						new Object[] { hsFourNO,
								CommonUtils.getCompany().getId(), imrType });
	}

	/**
	 * 根据种类，10位商品序号来查询内部归并数据
	 * 
	 * @param materielType
	 *            物料类别
	 * @param tenMemoNo
	 *            10位商品序号
	 * @return
	 */
	public List findInnerMergeDataByTypeAndTenMemoNo(String materielType,
			Integer tenMemoNo) {
		return this
				.find(" select a from InnerMergeData as a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.imrType=? and a.hsAfterTenMemoNo=? and a.company.id=? ",
						new Object[] { materielType, tenMemoNo,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系成品（由归并后成品得出）分页显示
	 * 
	 * @param seqNum
	 *            成品序号
	 * @param firstIndex
	 *            分页
	 * @param declareState
	 *            申报状态
	 * @param version
	 *            版本
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEmsEdiMergerBomByAfterPage(Integer seqNum, int firstIndex,
			String declareState, Integer version, String ptNo) {
		List<Object> p = new ArrayList<Object>();
		String hsql = "";
		if (getIsMergerSend()) {
			hsql = "select a from EmsEdiMergerExgBom a "
					+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState = ? ";
			p.add(seqNum);
			p.add(CommonUtils.getCompany().getId());
			p.add(new Boolean(false));
			if (ptNo != null && !"".equals(ptNo)) {
				hsql += "  and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=? ";
				p.add(ptNo);
			}
			if (version != null) {
				hsql += " and a.emsEdiMergerVersion.version = ? ";
				p.add(version);
			} else {
				hsql += " and a.emsEdiMergerVersion.version not in(select distinct a.version from EmsHeadH2kVersion a "
						+ " where a.emsHeadH2kExg.seqNum=?) ";
				p.add(seqNum);
			}
			hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo";
			// System.out.println("===hsql=="+hsql);
			return this.findPageList(hsql, p.toArray(), firstIndex, 100);
		} else {
			hsql = "select a from EmsEdiMergerExgBom a "
					+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState=? and "
					+ "  a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState = ? ";
			p.add(seqNum);
			p.add(CommonUtils.getCompany().getId());
			p.add(declareState);
			p.add(new Boolean(false));
			if (ptNo != null && !"".equals(ptNo)) {
				hsql += "  and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=? ";
				p.add(ptNo);
			}
			if (version != null) {
				hsql += " and a.emsEdiMergerVersion.version = ? ";
				p.add(version);
			} else {
				hsql += " and a.emsEdiMergerVersion.version not in(select distinct a.version from EmsHeadH2kVersion a "
						+ " where a.emsHeadH2kExg.seqNum=?) ";
				p.add(seqNum);
			}
			hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo";
			return this.findPageList(hsql, p.toArray(), firstIndex, 100);
		}
	}

	/**
	 * 显示归并关系成品（由归并后成品得出）
	 * 
	 * @param seqNum
	 *            成品序号
	 * @param declareState
	 *            申报状态
	 * @param version
	 *            版本
	 * @return
	 */
	public List findEmsEdiMergerBomPtNoByAfter(Integer seqNum,
			String declareState, Integer version) {
		List<Object> paramer = new ArrayList<Object>();
		if (getIsMergerSend()) {
			String hsql = "select distinct a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"
					+ "  and  a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState = ? ";
			paramer.add(seqNum);
			paramer.add(CommonUtils.getCompany().getId());
			paramer.add(new Boolean(false));
			hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo";
			return this.find(hsql, paramer.toArray());

		} else {
			String hsql = "select distinct a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo from EmsEdiMergerExgBom a "
					+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState=? and "
					+ "  a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState = ? ";
			paramer.add(seqNum);
			paramer.add(CommonUtils.getCompany().getId());
			paramer.add(declareState);
			paramer.add(new Boolean(false));
			hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo";
			return this.find(hsql, paramer.toArray());
		}
	}

	/**
	 * 显示归并关系成品（由归并后成品得出）
	 * 
	 * @param seqNum
	 *            成品序号
	 * @param declareState
	 *            申报状态
	 * @param version
	 *            版本号
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEmsEdiMergerBomByAfter(Integer seqNum, String declareState,
			Integer version, String ptNo) {
		List<Object> paramer = new ArrayList<Object>();
		if (getIsMergerSend()) {
			String hsql = "select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"

					+ " and  "
					+ "  a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState = ? "
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=?";
			paramer.add(seqNum);
			paramer.add(CommonUtils.getCompany().getId());
			paramer.add(new Boolean(false));
			paramer.add(ptNo);
			if (version != null) {
				hsql += " and a.emsEdiMergerVersion.version = ? ";
				paramer.add(version);
			} else {
				hsql += " and a.emsEdiMergerVersion.version not in(select distinct a.version from EmsHeadH2kVersion a "
						+ " where a.emsHeadH2kExg.seqNum=?) ";
				paramer.add(seqNum);
			}
			hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo";
			return this.find(hsql, paramer.toArray());
		} else {
			String hsql = "select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState=? and "
					+ "  a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState = ? "
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=?";
			paramer.add(seqNum);
			paramer.add(CommonUtils.getCompany().getId());
			paramer.add(new Boolean(false));
			paramer.add(ptNo);
			if (version != null) {
				hsql += " and a.emsEdiMergerVersion.version = ? ";
				paramer.add(version);
			} else {
				hsql += " and a.emsEdiMergerVersion.version not in(select distinct a.version from EmsHeadH2kVersion a "
						+ " where a.emsHeadH2kExg.seqNum=?) ";
				paramer.add(seqNum);
			}
			hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo";
			return this.find(hsql, paramer.toArray());
		}
	}

	/**
	 * 删除归并关系BOM（由归并后成品）
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后成品
	 */
	public void deleteBomByAfter(EmsEdiMergerExgAfter emsAfter) {
		this.deleteAll(findEmsEdiMergerBomByAfter(emsAfter));
		this.deleteAll(findEmsEdiMergerVersionByAfter(emsAfter));
	}

	/**
	 * 显示归并关系Bom（所有的Bom）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerBom(EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ?",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系Bom（所有的Bom）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerBomForProceMess(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=?  and a.company.id= ? and a.sendState = ?",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示归并关系Bom（所有的Bom--报文使用）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerBomToBaowen(EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.modifyMark<>? and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ?",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示归并关系Bom（所有的Bom--报文使用）
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerBomToBaowenBySendState(
			EmsEdiMergerHead emsEdiMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.sendState = ? and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ?",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存归并关系Bom
	 * 
	 * @param emsExgBom
	 *            成品BOM表
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerExgBom(EmsEdiMergerExgBom emsExgBom)
			throws DataAccessException {
		this.saveOrUpdate(emsExgBom);

	}

	/**
	 * 保存归并关系Bom
	 * 
	 * @param emsExgBom
	 *            成品BOM表
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerExgBoms(List<EmsEdiMergerExgBom> listEmsExgBom)
			throws DataAccessException {
		this.batchSaveOrUpdate(listEmsExgBom);
	}

	/**
	 * 保存归并关系归并后成品
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后成品
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerExgAfter(EmsEdiMergerExgAfter emsAfter)
			throws DataAccessException {
		this.saveOrUpdate(emsAfter);
	}

	/**
	 * 保存归并关系归并前成品
	 * 
	 * @param emsBefore
	 *            归并前成品
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerExgBefore(EmsEdiMergerExgBefore emsBefore)
			throws DataAccessException {
		this.saveOrUpdate(emsBefore);
	}

	/**
	 * 保存归并关系归并前料件
	 * 
	 * @param emsBefore
	 *            归并关系归并前料件表
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerImgBefore(EmsEdiMergerImgBefore emsBefore)
			throws DataAccessException {
		this.saveOrUpdate(emsBefore);
	}

	/**
	 * 保存归并关系表头
	 * 
	 * @param emshead
	 *            电子帐册归并关系表头
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerHead(EmsEdiMergerHead emshead)
			throws DataAccessException {
		this.saveOrUpdate(emshead);
	}

	/**
	 * 保存归并关系归并后料件
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后料件
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerImgAfter(EmsEdiMergerImgAfter emsAfter)
			throws DataAccessException {
		this.saveOrUpdate(emsAfter);

	}

	/**
	 * 保存归并关系归并后料件
	 * 
	 * @param state
	 *            修改标志
	 * @param id
	 *            归并后料件ID
	 * @param isImg
	 *            为料件还是成品
	 */
	public void saveEmsEdiMergerImgAfterToBefore(String state, String id,
			boolean isImg) {
		System.out.println("state=" + state);
		System.out.println("seqNum" + id);
		String hsql = null;
		if (isImg) {
			hsql = "update EmsEdiMergerImgBefore a set a.modifyMark=? where a.emsEdiMergerImgAfter.id=?";
		} else {
			hsql = "update EmsEdiMergerExgBefore a set a.modifyMark=? where a.emsEdiMergerExgAfter.id=?";
		}
		this.batchUpdateOrDelete(hsql, new Object[] { state, id });
	}

	/**
	 * 保存归并关系归并后成品列表
	 * 
	 * @param exgAfters
	 *            电子帐册归并关系归并后成品List
	 * @return
	 */
	public List saveEmsEdiMergerExgAfters(List exgAfters) {
		EmsEdiMergerHead header = null;
		if (exgAfters.size() > 0) {
			header = ((EmsEdiMergerExgAfter) exgAfters.get(0))
					.getEmsEdiMergerHead();
		}
		for (int i = 0; i < exgAfters.size(); i++) {
			this.saveOrUpdate(exgAfters.get(i));
		}
		if (header == null) {
			return new Vector();
		} else {
			return this.findEmsEdiMergerExgAfter(header);
		}
	}

	/**
	 * 保存归并关系归并后料件列表
	 * 
	 * @param imgAfters
	 *            电子帐册归并关系归并后料件List
	 * @return
	 */
	public List saveEmsEdiMergerImgAfters(List imgAfters) {
		EmsEdiMergerHead header = null;
		if (imgAfters.size() > 0) {
			header = ((EmsEdiMergerImgAfter) imgAfters.get(0))
					.getEmsEdiMergerHead();
		}
		for (int i = 0; i < imgAfters.size(); i++) {
			this.saveOrUpdate(imgAfters.get(i));
		}
		if (header == null) {
			return new Vector();
		} else {
			return this.findEmsEdiMergerImgAfter(header);
		}
	}

	/**
	 * 删除归并关系Bom
	 * 
	 * @param emsExgBom
	 *            成品BOM表
	 */
	public void deleteEmsEdiMergerExgBom(EmsEdiMergerExgBom emsExgBom) {
		this.delete(emsExgBom);
	}

	/**
	 * 删除归并关系归并后成品
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后成品
	 */
	public void deleteEmsEdiMergerExgAfter(EmsEdiMergerExgAfter emsAfter) {
		this.delete(emsAfter);
	}

	/**
	 * 删除归并关系表头
	 * 
	 * @param emshead
	 *            电子帐册归并关系表头
	 */
	public void deleteEmsEdiMergerHead(EmsEdiMergerHead emshead) {
		this.delete(emshead);
	}

	/**
	 * 批量删除归并关系表体
	 * 
	 * @param emshead
	 *            电子帐册归并关系表头
	 */
	public void deleteEmsEdiMergerImgExg(EmsEdiMergerHead emshead) {
		this.deleteAll(findEmsEdiMergerBom(emshead)); // 删除BOM
		this.deleteAll(findEmsEdiMergerVersionAll(emshead));// 删除版本
		this.deleteAll(findEmsEdiMergerExgBefore(emshead));// 删除归并前成品
		this.deleteAll(findEmsEdiMergerExgAfter(emshead)); // 删除归并后成品
		this.deleteAll(findEmsEdiMergerImgBefore(emshead));// 删除归并前料件
		this.deleteAll(findEmsEdiMergerImgAfter(emshead)); // 删除归并后料件

	}

	/**
	 * 删除归并关系归并前料件
	 * 
	 * @param emsBefore
	 *            归并关系归并前料件表
	 */
	public void deleteEmsEdiMergerImgBefore(EmsEdiMergerImgBefore emsBefore) {
		this.delete(emsBefore);
	}

	/**
	 * 删除归并关系归并前成品
	 * 
	 * @param emsBefore
	 *            归并前成品
	 */
	public void deleteEmsEdiMergerExgBefore(EmsEdiMergerExgBefore emsBefore) {
		this.delete(emsBefore);
	}

	/**
	 * 删除归并关系归并后料件
	 * 
	 * @param emsAfter
	 *            电子帐册归并关系归并后料件
	 */
	public void deleteEmsEdiMergerImgAfter(EmsEdiMergerImgAfter emsAfter) {
		this.delete(emsAfter);
	}

	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */
	public List findEmsHeadH2k() {

		return this
				.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=? order by a.modifyTimes DESC",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false) });
	}

	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */
	public EmsHeadH2k findEmsHeadH2kById(String emsHeadH2kId) {
		List list = this
				.find("select a from EmsHeadH2k a where a.company.id= ? and a.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsHeadH2kId });
		if (list.size() > 0) {
			return (EmsHeadH2k) list.get(0);
		}
		return null;
	}

	/**
	 * 显示归并关系表头
	 * 
	 * @return
	 */
	public EmsEdiMergerHead findNewEmsEdiMergerHeadById(
			String emsEdiMergerHeadId) {
		List list = this
				.find("select a from EmsEdiMergerHead a where a.company.id= ? and a.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsEdiMergerHeadId });
		if (list.size() > 0) {
			return (EmsEdiMergerHead) list.get(0);
		}
		return null;
	}

	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */
	public List findEmsHeadH2kAll() {

		return this
				.find("select a from EmsHeadH2k a where a.company.id= ? order by a.modifyTimes",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询BCUS参数设置是否设置了电子帐册是否分批发送
	 * 
	 * @return
	 */
	public boolean getIsEmsH2kSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据公司查询BCUS参数设置是否设置了电子帐册是否分批发送
	 * 
	 * @param conpany
	 *            公司
	 * @return
	 */
	private boolean getIsEmsH2kSend(Company conpany) {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								conpany.getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 显示当前正在执行的电子帐册表头
	 * 
	 * @param company
	 *            公司
	 * @return
	 */
	public List findEmsHeadH2kInExecut(Company company) {
		if (getIsEmsH2kSend(company)) {
			return this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=?",
							new Object[] { company.getId(), new Boolean(false) });
		} else {
			return this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.declareState=? and a.historyState=?",
							new Object[] { company.getId(),
									DeclareState.PROCESS_EXE,
									new Boolean(false) });
		}
	}

	/**
	 * 显示当前正在执行的电子帐册表头
	 * 
	 * @return
	 */
	public List<EmsHeadH2k> findEmsHeadH2kInExecuting() {
		if (getIsEmsH2kSend()) {
			return this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=?",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false) });
		} else {
			return this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.declareState=? and a.historyState=?",
							new Object[] { CommonUtils.getCompany().getId(),
									DeclareState.PROCESS_EXE,
									new Boolean(false) });
		}
	}

	/**
	 * 显示当前正在执行的电子帐册表头
	 * 
	 * @param company
	 *            公司
	 * @return
	 */
	public List findEmsHeadH2kInExecuting(Company company) {
		return this
				.find("select a from EmsHeadH2k a where a.company.id= ? and a.declareState=? and a.historyState=?",
						new Object[] { company.getId(),
								DeclareState.PROCESS_EXE, new Boolean(false) });
	}

	/**
	 * 保存电子帐册表头
	 * 
	 * @param emshead
	 *            电子帐册表头
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2k(EmsHeadH2k emshead) throws DataAccessException {
		this.saveOrUpdate(emshead);
	}

	/**
	 * 查询电子帐册表头
	 * 
	 * @param id
	 *            表头ID
	 * @return
	 */
	public EmsHeadH2k getH2k(String id) {
		List list = this.find("select a from EmsHeadH2k a where a.id=?",
				new Object[] { id });
		if (list.size() > 0) {
			return (EmsHeadH2k) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 删除电子帐册表头
	 * 
	 * @param emshead
	 *            电子帐册表头
	 */
	public void deleteEmsHeadH2k(EmsHeadH2k emshead) {
		this.delete(emshead);
	}

	/**
	 * 批量删除电子帐册表体
	 * 
	 * @param emshead
	 *            电子帐册表头
	 */
	public void deleteEmsHeadH2kImgExg(EmsHeadH2k emshead) {
		this.deleteAll(findEmsHeadH2kBom(emshead));// 删除Bom
		this.deleteAll(findEmsHeadH2kVersionAll(emshead));// 删除版本号
		this.deleteAll(findEmsHeadH2kExg(emshead));// 删除成品
		this.deleteAll(findEmsHeadH2kImg(emshead));// 删除料件
	}

	/**
	 * 批量删除电子帐册分册
	 * 
	 * @param fas
	 *            电子帐册分册
	 */
	public void deleteEmsHeadH2kFasImgExg(EmsHeadH2kFas fas) {
		this.deleteAll(findEmsHeadH2kFasImg(fas));
		this.deleteAll(findEmsHeadH2kFasExg(fas));
	}

	/**
	 * 显示所有电子帐册料件
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kImg(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有电子帐册料件
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List<EmsHeadH2kImg> findEmsHeadH2kImg() {
		return this
				.find("select a from EmsHeadH2kImg a where a.company.id= ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有电子帐册料件
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List<EmsHeadH2kImg> findEmsHeadH2kImgForProceMess(
			EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kImg a "
						+ " left outer join fetch a.emsHeadH2k "
						+ " where a.emsHeadH2k.id=? and a.company.id= ? and a.sendState = ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示所有电子帐册料件,成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param isImg
	 *            料件还是成品
	 * @param fields
	 *            字段名
	 * @param sValue
	 *            字段的值
	 * @return
	 */
	public List findEmsHeadH2kImgExg(EmsHeadH2k emsHeadH2k, boolean isImg,
			String fields, Object sValue) {
		List<Object> parameters = new ArrayList<Object>();
		String stable = "EmsHeadH2kImg";
		if (isImg) {
			stable = "EmsHeadH2kImg";
		} else {
			stable = "EmsHeadH2kExg";
		}
		String hsql = "select a from " + stable
				+ " a where a.emsHeadH2k.id=? and a.company.id= ? ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (fields != null && sValue != null && fields.equals("changeDate")) {
			hsql += " and (a.changeDate >= ? and a.changeDate <= ?) ";
			parameters.add((getDateByValues(sValue.toString()))[0]);
			parameters.add((getDateByValues(sValue.toString()))[1]);
		} else if (fields != null && sValue != null) {
			hsql += " and a." + fields + " = ? ";
			parameters.add(sValue);
		}
		hsql += " order by a.seqNum";
		List list = this.find(hsql, parameters.toArray());
		System.out.println("hsql====" + hsql);
		return list;
	}

	/**
	 * 1、查询电子账册和归并关系中同时存在的归并序号 2、查询这些归并序号地下的bom同时排除电子账册中已有的bom版本 3、并且符合以下条件：
	 * a、序号在指定的序号范围内（seqNumMin，seqNumMax） b、bom便跟时间在指定范围内（beginDate，endDate）
	 * c、bom状态在states中 d、指定账册emsHeadH2k
	 * 
	 * @param emsHeadH2k
	 * @param seqNumMin
	 * @param seqNumMax
	 * @param beginDate
	 * @param endDate
	 * @param states
	 * @return
	 */
	public List<EmsEdiMergerExgBom> findEmsHeadH2kImgExgBySeqNum(
			EmsHeadH2k emsHeadH2k, Integer seqNumMin, Integer seqNumMax,
			Date beginDate, Date endDate, String states) {
		/*
		 * 查询归并bom版本存在、但是电子账册bom版本中不存在的记录（关联的成品序号和版本号相等算相同） select v1.id from
		 * EmsEdiMergerVersion v1 where v1.id not in ( select v1.id from
		 * EmsEdiMergerVersion v1 ,EmsHeadH2kVersion v2 where
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum =
		 * v2.emsHeadH2kExg.seqNum and v2.version = v1.version and
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum < 1000) and
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum < 1000
		 */
		StringBuilder hql = new StringBuilder(
				"select v1.id from EmsEdiMergerVersion v1");
		hql.append(" where v1.id not in (")
				.append(" select v1.id")
				.append(" from EmsEdiMergerVersion v1 ,EmsHeadH2kVersion v2")
				.append(" where v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = v2.emsHeadH2kExg.seqNum")
				.append(" and v2.version = v1.version");
		if (seqNumMin != null) {
			hql.append(" and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum >= "
					+ seqNumMin);
		}
		if (seqNumMax != null) {
			hql.append(" and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum <= "
					+ seqNumMax);
		}
		hql.append(" )");
		if (seqNumMin != null) {
			hql.append(" and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum >= "
					+ seqNumMin);
		}
		if (seqNumMax != null) {
			hql.append(" and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum <= "
					+ seqNumMax);
		}

		List<String> versionIds = find(hql.toString());

		if (versionIds.isEmpty()) {
			return new ArrayList<EmsEdiMergerExgBom>();
		}

		hql.setLength(0);

		List<Object> parameters = new ArrayList<Object>();
		/*
		 * 查询归并bom版本和电子账册bom版本中同时存在的记录（关联的成品序号和版本号相等） select a from
		 * EmsEdiMergerExgBom a ,EmsHeadH2kExg e left join a.emsEdiMergerVersion
		 * v1 where v1.id in ( '9BE26587D1134FCA8A8C1027CB5B38CC',
		 * '5CC1F8F210B741E6B5744056E6B72593',
		 * '402882d8404d4aa701404d5dba2e00a7',
		 * '02227D05E1B54432897F860D28190514',
		 * '7E33A7FFF10E4F6DB9550F2D7D635BD1',
		 * 'DFD7427B835940C2B5382AACC13F7B3C',
		 * '70FF341AA14242AFB31F7BB6BFE52A60',
		 * '2FEEB73CCAAD49A6AC4DA034A463C03C') and
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = e.seqNum
		 */
		hql.append("select a from EmsEdiMergerExgBom a ,EmsHeadH2kExg e")
				.append(" left join a.emsEdiMergerVersion v1")
				.append(" where v1.id in ('" + versionIds.get(0));

		for (int i = 1; i < versionIds.size(); i++) {
			hql.append("','" + versionIds.get(i));
		}
		hql.append("')");

		if (seqNumMin != null) {
			hql.append(" and e.seqNum >= ?");
			parameters.add(seqNumMin);
		}
		if (seqNumMax != null) {
			hql.append(" and e.seqNum <= ?");
			parameters.add(seqNumMax);
		}
		if (beginDate != null) {
			hql.append(" and a.changeDate >= ?");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql.append(" and a.changeDate <= ?");
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (states != null && !"".equals(states) && !",".equals(states)) {
			String[] stas = states.split(",");
			if (stas.length == 2) {
				hql.append(" and (a.modifyMark = ? or a.modifyMark = ?)");
				parameters.add(stas[0]);
				parameters.add(stas[1]);
			} else {
				hql.append(" and a.modifyMark = ?");
				parameters.add(stas[0]);
			}
		}
		hql.append(
				" and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = e.seqNum")
				.append(" and a.company.id= ? and e.emsHeadH2k.id = ?");
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHeadH2k.getId());

		return find(hql.toString(), parameters.toArray());
	}

	/**
	 * 1、查询电子账册和归并关系中同时存在的归并序号 2、查询这些归并序号地下的bom同时排除电子账册中已有的bom版本 3、并且符合以下条件：
	 * a、序号在指定的序号范围内（seqNumMin，seqNumMax） b、bom便跟时间在指定范围内（beginDate，endDate）
	 * c、bom状态在states中 d、指定账册emsHeadH2k
	 * 
	 * @param emsHeadH2k
	 * @param seqNumMin
	 * @param seqNumMax
	 * @param beginDate
	 * @param endDate
	 * @param states
	 * @return
	 */
	public List<EmsEdiMergerExgBom> findEmsHeadH2kImgExgBySeqNumNew(
			EmsHeadH2k emsHeadH2k, Integer seqNumMin, Integer seqNumMax,
			Date beginDate, Date endDate, String states) {

		List<Object> parameters = new ArrayList<Object>();
		/*
		 * 查询归并前bom，关联的成品序号存在于电子账册成品表里，并且不存在于电子账册版本中的bom记录 select a from
		 * EmsEdiMergerExgBom a ,EmsHeadH2kExg e left join a.emsEdiMergerVersion
		 * v1 where v1.id in ( select v1.id from EmsEdiMergerVersion v1 where
		 * v1.id not in ( select v1.id from EmsEdiMergerVersion v1
		 * ,EmsHeadH2kVersion v2 where
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum =
		 * v2.emsHeadH2kExg.seqNum and v2.version = v1.version and
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum < 1850) and
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum < 1850) and
		 * v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = e.seqNum
		 */
		StringBuilder hql = new StringBuilder(
				" select a from EmsEdiMergerExgBom a ,EmsHeadH2kExg e")
				.append(" left join a.emsEdiMergerVersion v1")
				.append(" where v1.id in (")
				.append(" 	select v1.id")
				.append(" 	from EmsEdiMergerVersion v1")
				.append(" 	where v1.id not in (")
				.append(" 		select v1.id")
				.append(" 		from EmsEdiMergerVersion v1 ,EmsHeadH2kVersion v2")
				.append(" 		where v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = v2.emsHeadH2kExg.seqNum ")
				.append(" 		and v2.version = v1.version");
		if (seqNumMin != null) {
			hql.append(" 		and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum >= "
					+ seqNumMin);
		}
		if (seqNumMax != null) {
			hql.append(" 		and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum <= "
					+ seqNumMax);
		}
		hql.append(")");
		if (seqNumMin != null) {
			hql.append(" 	and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum >= "
					+ seqNumMin);
		}
		if (seqNumMax != null) {
			hql.append(" 	and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum <= "
					+ seqNumMax);
		}
		hql.append(")");

		if (beginDate != null) {
			hql.append(" and a.changeDate >= ?");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql.append(" and a.changeDate <= ?");
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (states != null && !"".equals(states) && !",".equals(states)) {
			String[] stas = states.split(",");
			if (stas.length == 2) {
				hql.append(" and (a.modifyMark = ? or a.modifyMark = ?)");
				parameters.add(stas[0]);
				parameters.add(stas[1]);
			} else {
				hql.append(" and a.modifyMark = ?");
				parameters.add(stas[0]);
			}
		}
		hql.append(
				" and v1.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = e.seqNum")
				.append(" and a.company.id= ? and e.emsHeadH2k.id = ?");
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsHeadH2k.getId());

		return find(hql.toString(), parameters.toArray());
	}

	private List<EmsHeadH2kVersion> findEmsHeadH2kVersion(
			List<Integer> seqNumList, EmsHeadH2k emsHeadH2k) {
		if (seqNumList == null || seqNumList.size() == 0) {
			return null;
		}

		StringBuilder hql = new StringBuilder(1000);
		hql.append(
				"select a from EmsHeadH2kVersion a where a.company.id='"
						+ CommonUtils.getCompany().getId() + "'")
				.append(" and a.emsHeadH2kExg.emsHeadH2k.id = '"
						+ emsHeadH2k.getId() + "'")
				.append(" and a.emsHeadH2kExg.seqNum in (" + seqNumList.get(0));
		for (int i = 1; i < seqNumList.size(); i++) {
			hql.append("," + seqNumList.get(i));
		}

		hql.append(")");

		return find(hql.toString());
	}

	public List findEmsHeadH2kImgExgBySeqNum(EmsHeadH2k emsHeadH2k,
			boolean isImg, Integer seqNumMin, Integer seqNumMax,
			Date beginDate, Date endDate, String states) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct b"
				+ " from EmsEdiMergerExgBom a ,EmsHeadH2kExg b"
				+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum = b.seqNum and a.company.id= ?";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNumMin != null) {
			hsql += " and a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum >= ?";
			parameters.add(seqNumMin);
		}
		if (seqNumMax != null) {
			hsql += " and a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum <= ?";
			parameters.add(seqNumMax);
		}
		if (beginDate != null) {
			hsql += " and a.changeDate >= ?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.changeDate <= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (states != null && !"".equals(states) && !",".equals(states)) {
			String[] stas = states.split(",");
			System.out.println(states);
			System.out.println(stas.length);
			if (stas.length == 2) {
				hsql += " and (a.modifyMark = ? or a.modifyMark = ?)";
				parameters.add(stas[0]);
				parameters.add(stas[1]);
			} else {
				hsql += " and a.modifyMark = ?";
				parameters.add(stas[0]);
			}

		}

		// hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum";
		List list = this.find(hsql, parameters.toArray());
		System.out.println("hsql====" + hsql);
		return list;
	}

	/**
	 * 显示所有电子帐册料件,成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param isImg
	 *            料件还是成品
	 * @param fields
	 *            字段名
	 * @return
	 */
	public List findEmsHeadH2kImgExgCheck(EmsHeadH2k emsHeadH2k, boolean isImg,
			String fields) {
		List<Object> parameters = new ArrayList<Object>();
		String stable = "EmsHeadH2kImg";
		if (isImg) {
			stable = "EmsHeadH2kImg";
		} else {
			stable = "EmsHeadH2kExg";
		}
		String hsql = "select a from " + stable
				+ " a where a.emsHeadH2k.id=? and a.company.id= ? ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(seqNum);
		// if (fields != null && sValue != null && fields.equals("changeDate"))
		// {
		// hsql += " and (a.changeDate >= ? and a.changeDate <= ?) ";
		// parameters.add((getDateByValues(sValue.toString()))[0]);
		// parameters.add((getDateByValues(sValue.toString()))[1]);
		// } else if (fields != null && sValue != null) {
		// hsql += " and a." + fields + " = ? ";
		// parameters.add(sValue);
		// }
		hsql += " order by a.seqNum";
		List list = this.find(hsql, parameters.toArray());
		System.out.println("hsql====" + hsql);
		return list;
	}

	/**
	 * 显示所有电子帐册料件,成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param isImg
	 *            料件还是成品
	 * @return
	 */
	public String findLastUpdateDateByImgExg(EmsHeadH2k emsHeadH2k,
			boolean isImg) {
		String stable = "EmsHeadH2kImg";
		if (isImg) {
			stable = "EmsHeadH2kImg";
		} else {
			stable = "EmsHeadH2kExg";
		}
		List list = this.find("select max(a.changeDate) from " + stable + " a "
				+ " where a.emsHeadH2k.id=? and a.company.id=?", new Object[] {
				emsHeadH2k.getId(), CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			return null;
		} else {
			Date updateDate = (Date) list.get(0);
			if (updateDate == null) {
				return null;
			}
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return bartDateFormat.format(updateDate);
		}
	}

	/**
	 * 获取时间格式
	 * 
	 * @param values
	 *            传递过来的值
	 * @return
	 */
	private Date[] getDateByValues(String values) {
		Date[] d = new Date[2];
		String bvalues = values + " 00:00:00";
		d[0] = strToStandDate(bvalues);
		String evalues = values + " 24:00:00";
		d[1] = strToStandDate(evalues);
		return d;
	}

	/**
	 * 转换时间格式
	 * 
	 * @param input
	 *            传递过来的值
	 * @return
	 */
	public Date strToStandDate(String input) {
		if (input == null) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 显示所有电子帐册料件--报文使用
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kImgToBaowen(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kImg a "
						+ " left outer join fetch a.emsHeadH2k "
						+ " where a.modifyMark<>? and  a.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有电子帐册料件--报文使用
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kImgToBaowenBySendSate(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kImg a "
						+ " left outer join fetch a.emsHeadH2k "
						+ " where a.sendState = ? and  a.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kExg(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List<EmsHeadH2kExg> findEmsHeadH2kExgForProceMess(
			EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kExg a "
						+ " left outer join fetch a.emsHeadH2k "
						+ " where a.emsHeadH2k.id=? and a.company.id= ? and a.sendState = ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示电子帐册所有成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param beginSeqNum
	 *            电子帐册成品序号(开始)
	 * @param endSeqNum
	 *            电子帐册成品序号(结束)
	 * @return
	 */
	public List findEmsHeadH2kExgInBegSeqNumEndSeqNum(EmsHeadH2k emsHeadH2k,
			Integer beginSeqNum, Integer endSeqNum) {
		return this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.id=? and a.company.id= ? "
						+ " and (a.seqNum >= ? and a.seqNum <= ?) order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(), beginSeqNum,
								endSeqNum });
	}

	/**
	 * 显示电子帐册所有成品--报文使用
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kExgToBaowen(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kExg a "
						+ " left outer join fetch a.emsHeadH2k "
						+ "where a.modifyMark<>? and  a.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有成品--报文使用
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kExgToBaowenBySendState(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kExg a "
						+ " left outer join fetch a.emsHeadH2k "
						+ " where a.sendState = ? and  a.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有版本（成品筛选--电子帐册）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 * @return
	 */
	public List findEmsHeadH2kVersion(EmsHeadH2kExg emsHeadH2kExg) {
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id=? and a.company.id= ?",
						new Object[] { emsHeadH2kExg.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有版本（成品筛选--电子帐册）
	 * 
	 * @param seqNum
	 *            电子帐册成品序号
	 * @param version
	 *            版本号
	 * @return
	 */
	public void updateEmsHeadH2kVersionByExgSeqNum(Integer seqNum,
			Integer version, boolean isForbid) {
		if (getIsEmsH2kSend()) {
			List list = this
					.find(" select a from EmsHeadH2kExg a "
							+ " where a.seqNum = ? and a.company.id=? and a.emsHeadH2k.historyState=? ",
							new Object[] { seqNum,
									CommonUtils.getCompany().getId(),
									new Boolean(false) });
			if (list != null && list.size() > 0) {
				String emsHeadH2kExgId = ((EmsHeadH2kExg) list.get(0)).getId();
				this.batchUpdateOrDelete(
						" update  EmsHeadH2kVersion a set a.isForbid=?"
								+ " where a.emsHeadH2kExg.id = ? and a.version = ? and a.company.id = ?",
						new Object[] { isForbid, emsHeadH2kExgId, version,
								CommonUtils.getCompany().getId() });
			}
		} else {
			List list = this
					.find(" select a from EmsHeadH2kExg a "
							+ " where a.seqNum = ? and a.company.id=? and a.emsHeadH2k.declareState=? and a.emsHeadH2k.historyState=? ",
							new Object[] { seqNum,
									CommonUtils.getCompany().getId(),
									DeclareState.PROCESS_EXE,
									new Boolean(false) });
			if (list != null && list.size() > 0) {
				String emsHeadH2kExgId = ((EmsHeadH2kExg) list.get(0)).getId();
				this.batchUpdateOrDelete(
						"update EmsHeadH2kVersion a  set  a.isForbid=? "
								+ " where  a.emsHeadH2kExg.id=? and  a.version = ? and a.company.id = ? ",
						new Object[] { isForbid, emsHeadH2kExgId, version,
								CommonUtils.getCompany().getId() });
			}
		}
	}

	/**
	 * 显示所有版本（成品筛选--归并关系）
	 * 
	 * @param emsEdiMergerExgBefore
	 *            归并前成品
	 * @return
	 */
	public List findEmsEdiMergerVersion(
			EmsEdiMergerExgBefore emsEdiMergerExgBefore) {
		return this
				.find("select a from EmsEdiMergerVersion a where a.emsEdiMergerBefore.id=? and a.company.id= ?",
						new Object[] { emsEdiMergerExgBefore.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有版本（成品筛选--归并关系）
	 * 
	 * @param emsHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerVersionAll(EmsEdiMergerHead emsHead) {
		return this
				.find("select a from EmsEdiMergerVersion a where a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ?",
						new Object[] { emsHead.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有版本号
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kVersionAll(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ?",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有版本号
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kVersionAllFromBom(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select distinct a.emsHeadH2kVersion from EmsHeadH2kBom a "
						+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=?"
						+ " and a.company.id= ?",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询已知成品序号下的电子帐册所有版本号
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kVersionByProduceSeqNum(EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList) {
		List<EmsHeadH2kVersion> list = null;

		StringBuilder hql = new StringBuilder(
				"select a from EmsHeadH2kVersion a "
						+ " left outer join fetch a.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kExg.emsHeadH2k "
						+ " where a.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ?");
		if (seqNumList != null && seqNumList.size() > 0) {
			hql.append(" and a.emsHeadH2kExg.seqNum in (" + seqNumList.get(0));
			for (int i = 1; i < seqNumList.size(); i++) {
				hql.append("," + seqNumList.get(i));
			}
			hql.append(")");
		}

		list = find(hql.toString(), new Object[] { emsHeadH2k.getId(),
				CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 查询已知成品序号下的电子帐册所有版本号
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public EmsHeadH2kVersion findEmsHeadH2kVersionBySeqNumAndVersion(
			EmsHeadH2k emsHeadH2k, Integer seqNum, Integer version) {
		List<EmsHeadH2kVersion> list = null;

		StringBuilder hql = new StringBuilder(
				"select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ?");
		hql.append(" and a.emsHeadH2kExg.seqNum = ? and a.version = ?");
		list = find(hql.toString(), new Object[] { emsHeadH2k.getId(),
				CommonUtils.getCompany().getId(), seqNum, version });
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 显示电子帐册BOM （由一条版本号筛选）
	 * 
	 * @param version
	 *            电子帐册历版本
	 * @return
	 */
	public List findEmsHeadH2kBom(EmsHeadH2kVersion version) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册BOM （由一条版本号筛选）
	 * 
	 * @param version
	 *            版本号
	 * @param ljSeqNum
	 *            料件序号
	 * @return
	 */
	public List findEmsHeadH2kBomBySeqNum(EmsHeadH2kVersion version,
			Integer ljSeqNum) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id=? and a.company.id= ? and a.seqNum = ?",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId(), ljSeqNum });
	}

	/**
	 * 显示归并关系BOM （由一条版本号筛选）
	 * 
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsEdiMergerBom(EmsEdiMergerVersion version) {
		return this
				.find("select a from EmsEdiMergerExgBom a "
				// + " left join fetch a.emsEdiMergerVersion b "
				// + " left join fetch b.emsEdiMergerBefore c "
				// + " left join fetch c.emsEdiMergerExgAfter d "
				// + " left join fetch d.emsEdiMergerHead "
				// + " left join fetch a.emsEdiMergerVersion  "
				// +
				// " left join fetch a.emsEdiMergerVersion.emsEdiMergerBefore  "
				// +
				// " left join fetch a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter  "
				// +
				// " left join fetch a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead "
						+ " where a.emsEdiMergerVersion.id=? and a.company.id= ? order by ptNo ",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId() });
	}

	public List findmanualdescbom(String spNo, int version) {
		return this
				.find("select a from EmsEdiMergerExgBom a "
						+ " where a.emsEdiMergerVersion.version=? and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=? "
						+ "and a.modifyMark<>3 and a.company.id= ? ",
						new Object[] { version, spNo,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 成品BOM表
	 * 
	 * @param version
	 *            版本号
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEmsEdiMergerBom(EmsEdiMergerVersion version, String ptNo) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.id=? and a.company.id= ? and a.ptNo = ?",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 成品BOM表
	 * 
	 * @param version
	 *            版本号
	 * @param seqNum
	 *            料件序号
	 * @return
	 */
	public List findEmsHeadH2kBom(EmsHeadH2kVersion version, Integer seqNum) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id = ? and a.company.id = ? and a.seqNum = ?",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId(), seqNum });
	}

	/**
	 * 显示电子帐册所有Bom
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kBom(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ?",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有成品Bom
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kBomSeq(EmsHeadH2k emsHeadH2k) {
		return this.find(
				" select distinct a.seqNum,b.version,c.seqNum ,a.id from EmsHeadH2kBom a "
						+ " left join a.emsHeadH2kVersion b"
						+ "	left join b.emsHeadH2kExg c "
						+ "  where c.emsHeadH2k.id=? and a.company.id= ? ",
				new Object[] { emsHeadH2k.getId(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有成品Bom
	 * 
	 * @param id
	 *            电子帐册所有成品Bom ID
	 * @return
	 */
	public EmsHeadH2kBom findEmsHeadH2kBomById(String id) {
		List list = this.find(" select a from EmsHeadH2kBom a "
				+ "  where  a.id= ? ", new Object[] { id });
		if (list.size() > 0) {
			return (EmsHeadH2kBom) list.get(0);
		} else
			return null;
	}

	/**
	 * 显示电子帐册成品底下所有版本的所有Bom
	 * 
	 * @param id
	 *            电子帐册所有成品Bom ID
	 * @return
	 */
	public List<EmsHeadH2kBom> findEmsHeadH2kBomByExg(EmsHeadH2kExg exg) {
		List list = this
				.find(" select a from EmsHeadH2kBom a "
						+ "  where a.emsHeadH2kVersion.emsHeadH2kExg.id= ? and a.company.id = ?",
						new Object[] { exg.getId(),
								CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 显示电子帐册所有成品Bom
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List<EmsHeadH2kBom> findEmsHeadH2kBomForProceMess(
			EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a "
						+ " left outer join fetch a.emsHeadH2kVersion "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k "
						+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? and a.sendState = ? order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(),
								Integer.valueOf(SendState.WAIT_SEND) });
	}

	/**
	 * 显示电子帐册所有Bom
	 * 
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsHeadH2kBomByVersion(EmsHeadH2kVersion version) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id=? and a.company.id= ?",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有Bom
	 * 
	 * @param seqNum
	 *            成品序号
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsHeadH2kBomByCpSeqNum(Integer seqNum,
			EmsHeadH2k emsHeadH2k, Integer version) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? and "
						+ " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = ? and a.emsHeadH2kVersion.version = ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(), seqNum,
								version });
	}

	/**
	 * 根据成品序号查询单耗表
	 * 
	 * @param beginSeq
	 * @param endSeq
	 * @return
	 */
	public List finEmsHeadH2KBomFromBeginSeqToEndSeq(String beginSeq,
			String endSeq, int pageNum, EmsHeadH2k head) {

		int bSeq = Integer.valueOf(beginSeq) - 1;

		int eSeq = Integer.valueOf(endSeq) + 1;

		String hql = "select a from EmsHeadH2kBom a "
				+ " left join fetch a.emsHeadH2kVersion b "
				+ " left join fetch a.emsHeadH2kVersion.emsHeadH2kExg c "
				+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? "
				+ " and c.seqNum > ? and c.seqNum < ? "
				+ " order by c.seqNum,b.version,a.seqNum";

		Object[] params = new Object[] { head.getId(),
				CommonUtils.getCompany().getId(), bSeq, eSeq };

		int maxLength = 10000;

		pageNum = (pageNum - 1) * maxLength + 1;

		return findPageList(hql, params, pageNum, maxLength);

	}

	/**
	 * 显示电子帐册所有Bom
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kBomOrder(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a "
						+ " left join fetch a.emsHeadH2kVersion b "
						+ " left join fetch a.emsHeadH2kVersion.emsHeadH2kExg c "
						+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ?"
						+ " order by c.seqNum,b.version,a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有Bom--报文使用
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kBomToBaowen(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a "
						+ " left outer join fetch a.emsHeadH2kVersion "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k "
						+ " where a.modifyMark<>? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE,
								emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册所有Bom--报文使用
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kBomToBaowenBySendState(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a "
						+ " left outer join fetch a.emsHeadH2kVersion "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k "
						+ " where a.sendState = ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { Integer.valueOf(SendState.WAIT_SEND),
								emsHeadH2k.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 电子帐册料件新增--来自归并关系归并后料件
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @param emsH2k
	 *            电子帐册表头
	 * @param isNow
	 *            判断是否正在执行
	 * @return
	 */
	public List findEmsEdiMergerImgAfterToH2k(EmsEdiMergerHead emsMerger,
			EmsHeadH2k emsH2k, boolean isNow) {
		if (getIsMergerSend()) {
			if (isNow) {
				System.out.println("isNow=" + isNow);
				return this
						.find("select a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead.declareState='3' and a.emsEdiMergerHead.historyState =? and a.company.id= ?"
								+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kImg c where c.emsHeadH2k.id=?)",
								new Object[] { new Boolean(false),
										CommonUtils.getCompany().getId(),
										emsH2k.getId() });
			} else {
				System.out.println("isNow=" + isNow);
				return this
						.find("select a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead.declareState='1' and a.emsEdiMergerHead.historyState =? and a.company.id= ?"
								+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kImg c where c.emsHeadH2k.id=?)",
								new Object[] { new Boolean(false),
										CommonUtils.getCompany().getId(),
										emsH2k.getId() });

			}

		} else {
			if (emsMerger == null) {
				return new Vector();
			}
			return this
					.find("select a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead=? and a.company.id= ?"
							+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kImg c where c.emsHeadH2k.id=?)",
							new Object[] { emsMerger,
									CommonUtils.getCompany().getId(),
									emsH2k.getId(), });
		}
	}

	/**
	 * 电子分册料件新增--来自电子帐册料件
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param emsH2kFas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kImgToFas(EmsHeadH2k emsHeadH2k,
			EmsHeadH2kFas emsH2kFas) {
		return this
				.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id=? and a.company.id= ?"
						+ "  and a.seqNum not in (select c.seqNum from EmsHeadH2kFasImg c where c.emsHeadH2kFas.id=?)",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(),
								emsH2kFas.getId(), });
	}

	/**
	 * 电子分册成品新增--来自电子帐册成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param emsH2kFas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kExgToFas(EmsHeadH2k emsHeadH2k,
			EmsHeadH2kFas emsH2kFas) {
		return this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.id=? and a.company.id= ?"
						+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kFasExg c where c.emsHeadH2kFas.id=?)",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(),
								emsH2kFas.getId(), });
	}

	/**
	 * 查找电子分册表头数据
	 * 
	 * @param emsH2kFas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kFas(EmsHeadH2kFas emsH2kFas) {
		return this.find(
				"select a from EmsHeadH2kFas a where a.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 按当前分册查找分册料件数据
	 * 
	 * @param emsH2kFas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kFasImgd(EmsHeadH2kFas emsH2kFas) {
		return this
				.find("select a from EmsHeadH2kFasImg a where a.company.id = ? and a.emsHeadH2kFas.id =?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsH2kFas.getId() });
	}

	/**
	 * 按当前分册查找分册成品数据
	 * 
	 * @param emsH2kFas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kFasExgd(EmsHeadH2kFas emsH2kFas) {
		return this
				.find("select a from EmsHeadH2kFasExg a where a.company.id = ? and a.emsHeadH2kFas.id =?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsH2kFas.getId() });
	}

	/**
	 * 查找电子分册料件表数据
	 */
	public List findEmsHeadH2kFasImg() {
		return this
				.find("select a from EmsHeadH2kFasImg a left join a.EmsHeadH2kFas where a.company.id = ?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存电子帐册料件
	 * 
	 * @param emsH2kImg
	 *            电子帐册料件
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kImg(EmsHeadH2kImg emsH2kImg)
			throws DataAccessException {
		this.saveOrUpdate(emsH2kImg);
	}

	/**
	 * 电子帐册成品新增--来自归并关系归并后成品
	 * 
	 * @param emsMerger
	 *            电子帐册归并关系表头
	 * @param emsH2k
	 *            电子帐册表头
	 * @param isNow
	 *            判断是否正在执行
	 * @return
	 */
	public List findEmsEdiMergerExgAfterToH2k(EmsEdiMergerHead emsMerger,
			EmsHeadH2k emsH2k, boolean isNow) {// isNow判断是否正在执行
		if (getIsMergerSend()) {
			System.out.println("第一条语句emsMerger=" + emsMerger + ",emsH2k="
					+ emsH2k);
			if (isNow) {
				System.out.println("true");
				return this
						.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.declareState='3' and a.emsEdiMergerHead.historyState=? and a.company.id= ?"
								+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kExg c where c.emsHeadH2k.id=? )",
								new Object[] { new Boolean(false),
										CommonUtils.getCompany().getId(),
										emsH2k.getId() });
			} else {
				System.out.println("false");
				return this
						.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.declareState='1' and a.emsEdiMergerHead.historyState=? and a.company.id= ?"
								+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kExg c where c.emsHeadH2k.id=? )",
								new Object[] { new Boolean(false),
										CommonUtils.getCompany().getId(),
										emsH2k.getId() });
			}
		} else {
			System.out.println("第二条语句");
			if (emsMerger == null) {
				return new Vector();
			}
			return this
					.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.id=? and a.company.id= ?"
							+ " and a.seqNum not in (select c.seqNum from EmsHeadH2kExg c where c.emsHeadH2k.id=? )",
							new Object[] { emsMerger.getId(),
									CommonUtils.getCompany().getId(),
									emsH2k.getId(), });
		}
	}

	/**
	 * 保存电子帐册成品
	 * 
	 * @param emsH2kExg
	 *            电子帐册成品
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kExg(EmsHeadH2kExg emsH2kExg)
			throws DataAccessException {
		this.saveOrUpdate(emsH2kExg);
	}

	/**
	 * 保存电子帐册版本号
	 * 
	 * @param emsH2k
	 *            电子帐册历版本
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kVersion(EmsHeadH2kVersion emsH2k)
			throws DataAccessException {
		this.saveOrUpdate(emsH2k);
	}

	/**
	 * 保存归并关系版本号
	 * 
	 * @param emsH2k
	 *            归并关系版本
	 * @throws DataAccessException
	 */
	public void saveEmsEdiMergerVersion(EmsEdiMergerVersion emsH2k)
			throws DataAccessException {
		this.saveOrUpdate(emsH2k);
	}

	/**
	 * 电子帐册计算单耗--显示归并前成品--归并后成品得出
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 * @param emsMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeByAfter(EmsHeadH2kExg emsHeadH2kExg,
			EmsEdiMergerHead emsMergerHead) {
		return this
				.find("select a from EmsEdiMergerExgBefore a where a.emsEdiMergerExgAfter.seqNum=? and a.company.id= ?"
						+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=?",
						new Object[] { emsHeadH2kExg.getSeqNum(),
								CommonUtils.getCompany().getId(),
								emsMergerHead.getId() });
	}

	/**
	 * 显示归并关系Bom由归并前成品得出（在电子帐册中）
	 * 
	 * @param emsExgBefore
	 *            归并前成品
	 * @return
	 */
	public List findEmsEdiBomByExgBefore(EmsEdiMergerExgBefore emsExgBefore) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerExgBefore.id=? and a.company.id= ?",
						new Object[] { emsExgBefore.getId(),
								CommonUtils.getCompany().getId(), });
	}

	/**
	 * 电子帐册计算单耗--根据成品BOM查找归并关系归并前料件
	 * 
	 * @param emsBom
	 *            成品BOM表
	 * @param emsHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeByGNo(EmsEdiMergerExgBom emsBom,
			EmsEdiMergerHead emsHead) {
		return this.find(
				"select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
						+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=?",
				new Object[] { emsBom.getPtNo(),
						CommonUtils.getCompany().getId(), emsHead.getId() });
	}

	/**
	 * 根据料件查询归并关系前的归并序号
	 * 
	 * @param isMateriel
	 * @param ptNo
	 * @param emsHead
	 * @return
	 */
	public Integer findSeqNumEmsEdiMergerAfterByGNo(boolean isMateriel,
			String ptNo, EmsEdiMergerHead emsHead) {
		if (isMateriel) {
			List lsit = this
					.find("select a.emsEdiMergerImgAfter.seqNum from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? ",
							new Object[] { ptNo,
									CommonUtils.getCompany().getId(),
									emsHead.getId() });
			if (lsit != null && lsit.size() > 0) {
				return lsit.get(0) == null ? null : (Integer) lsit.get(0);
			}
		} else {
			List lsit = this
					.find("select a.emsEdiMergerExgAfter.seqNum from EmsEdiMergerExgBefore a where a.ptNo=? and a.company.id= ?"
							+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=?",
							new Object[] { ptNo,
									CommonUtils.getCompany().getId(),
									emsHead.getId() });
			if (lsit != null && lsit.size() > 0) {
				return lsit.get(0) == null ? null : (Integer) lsit.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据料件查询 归并关系归并前料件表或成品表
	 * 
	 * @param isMateriel
	 * @param ptNo
	 * @param emsHead
	 * @return
	 */
	public Object findEmsEdiMergerAfterByGNo(boolean isMateriel, String ptNo,
			EmsEdiMergerHead emsHead) {
		if (isMateriel) {
			List lsit = this
					.find("select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? ",
							new Object[] { ptNo,
									CommonUtils.getCompany().getId(),
									emsHead.getId() });
			if (lsit != null && lsit.size() > 0) {
				return lsit.get(0) == null ? null
						: (EmsEdiMergerImgBefore) lsit.get(0);

			}
		} else {
			List lsit = this
					.find("select a from EmsEdiMergerExgBefore a where a.ptNo=? and a.company.id= ?"
							+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=?",
							new Object[] { ptNo,
									CommonUtils.getCompany().getId(),
									emsHead.getId() });
			if (lsit != null && lsit.size() > 0) {
				return lsit.get(0) == null ? null
						: (EmsEdiMergerExgBefore) lsit.get(0);
			}
		}
		return null;
	}

	/**
	 * 电子帐册计算单耗--根据成品BOM查找归并关系归并前料件
	 * 
	 * @param isMateriel
	 *            料件还是成品
	 * @param ptNo
	 *            料号
	 * @param emsHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeByGNo(boolean isMateriel, String ptNo,
			EmsEdiMergerHead emsHead) {
		if (isMateriel) {
			return this
					.find("select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? ",
							new Object[] { ptNo,
									CommonUtils.getCompany().getId(),
									emsHead.getId() });
		} else {
			return this
					.find("select a from EmsEdiMergerExgBefore a where a.ptNo=? and a.company.id= ?"
							+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=?",
							new Object[] { ptNo,
									CommonUtils.getCompany().getId(),
									emsHead.getId() });
		}
	}

	/**
	 * 电子帐册计算单耗--查找电子帐册是否存在归并后料件
	 * 
	 * @param emsBom
	 *            料件序号
	 * @param emsVersion
	 *            电子帐册历版本
	 * @param exg
	 *            电子帐册成品
	 * @return
	 */
	public List findEmsHeadH2kBomBySeqNum(Integer emsBom,
			EmsHeadH2kVersion emsVersion, EmsHeadH2kExg exg) {
		return this
				.find("select a from EmsHeadH2kBom a where a.seqNum=? and a.company.id= ? and a.emsHeadH2kVersion.id=? and a.emsHeadH2kVersion.emsHeadH2kExg.id = ?",
						new Object[] { emsBom,
								CommonUtils.getCompany().getId(),
								emsVersion.getId(), exg.getId() });
	}

	/**
	 * 保存电子帐册BOM
	 * 
	 * @param emsH2kBom
	 *            电子帐册成品BOM
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kBom(EmsHeadH2kBom emsH2kBom)
			throws DataAccessException {
		this.saveOrUpdate(emsH2kBom);
	}

	/**
	 * 批量删除电子帐册BOM表
	 * 
	 * @param version
	 *            版本号
	 */
	public void deleteEmsHeadh2kBomByVersion(EmsHeadH2kVersion version) {
		this.deleteAll(findEmsHeadH2kBom(version));
	}

	/**
	 * 批量删除归并关系BOM表
	 * 
	 * @param version
	 *            版本号
	 */
	public void deleteEmsMergerBomByVersion(EmsEdiMergerVersion version) {
		this.deleteAll(findEmsEdiMergerBom(version));
	}

	/**
	 * 删除电子账册中一个BOM料件
	 * 
	 * @param emsHeadH2kBom
	 *            电子帐册成品BOM表
	 */
	public void deleteEmsHeadH2kBom(EmsHeadH2kBom emsHeadH2kBom) {
		this.delete(emsHeadH2kBom);
	}

	/**
	 * 删除电子账册中一个BOM版本
	 * 
	 * @param emsHeadH2kVersion
	 *            电子帐册历版本
	 */
	public void deleteEmsHeadH2kVersion(EmsHeadH2kVersion emsHeadH2kVersion) {
		this.delete(emsHeadH2kVersion);
	}

	/**
	 * 删除归并关系中一个BOM版本
	 * 
	 * @param emsEdiMergerVersion
	 *            归并关系版本
	 */
	public void deleteEmsEdiMergerVersion(
			EmsEdiMergerVersion emsEdiMergerVersion) {
		this.delete(emsEdiMergerVersion);
	}

	/**
	 * 删除电子账册中一个料件
	 * 
	 * @param emsHeadH2kImg
	 *            电子帐册料件
	 */
	public void deleteEmsHeadH2kImg(EmsHeadH2kImg emsHeadH2kImg) {
		this.delete(emsHeadH2kImg);
	}

	/**
	 * 删除电子账册中一个成品
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 */
	public void deleteEmsHeadH2kExg(EmsHeadH2kExg emsHeadH2kExg) {
		this.delete(emsHeadH2kExg);
	}

	/**
	 * 显示所有BOM（成品筛选）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 * @return
	 */
	public List findBomByExg(EmsHeadH2kExg emsHeadH2kExg) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.id=? and a.company.id= ?",
						new Object[] { emsHeadH2kExg.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除所有表体（成品筛选）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 */
	public void deleteVersionAndBom(EmsHeadH2kExg emsHeadH2kExg) {
		this.deleteAll(findBomByExg(emsHeadH2kExg)); // 删除所有BOM
		this.deleteAll(findEmsHeadH2kVersion(emsHeadH2kExg)); // 删除所有版本
	}

	/**
	 * 查找是否存在版本号（检查版本号是否重复）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findExistVersion(EmsHeadH2kExg emsHeadH2kExg, String version) {
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id=? and a.version=? and a.company.id= ?",
						new Object[] { emsHeadH2kExg.getId(),
								Integer.valueOf(version),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找是否存在版本号（检查版本号是否重复）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 * @param version
	 *            版本号
	 * @return
	 * @author 石小凯
	 */
	public List findExistVersionNew(String ptNo, String version) {
		List<Object> parameters = new ArrayList<Object>();
		List list = null;
		if (getIsEmsH2kSend()) {
			String hsql = "select a from EmsEdiMergerExgBom a "
					+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=? and a.company.id= ? "
					+ "  and a.sendState=? ";
			parameters.add(ptNo);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(Integer.valueOf(SendState.SEND));
			if (version != null && !"".equals(version)) {
				hsql += " and a.emsEdiMergerVersion.version=?  ";
				parameters.add(Integer.valueOf(version));
			}
			list = this.find(hsql, parameters.toArray());
		} else {
			String hsql = "select a from EmsEdiMergerExgBom a "
					+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo=? and a.company.id= ?  "
					+ "  and a.modifyMark=? ";
			parameters.add(ptNo);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(ModifyMarkState.UNCHANGE);
			if (version != null && !"".equals(version)) {
				hsql += " and a.emsEdiMergerVersion.version=?  ";
				parameters.add(Integer.valueOf(version));
			}
			list = this.find(hsql, parameters.toArray());
		}
		return list;
	}

	/**
	 * 根据企业版本查询出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersionByCmpVersion(String ptNo, String cmpVersion) {
		List list = this
				.find("select a.version from EmsEdiMergerVersion a where a.emsEdiMergerBefore.ptNo=? "
						+ "  and a.cmpVersion=? and a.company.id= ?",
						new Object[] { ptNo, cmpVersion,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0 && list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return -1;
	}

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersion(String ptNo, String cmpVersion) {
		List list = this.find(
				"select a.version from EmsEdiMergerVersion a where a.emsEdiMergerBefore.ptNo=?"
						+ "  and a.cmpVersion=? and a.company.id= ?",
				new Object[] { ptNo, cmpVersion,
						CommonUtils.getCompany().getId() });
		// =======当企业版本存在则返回海关版本
		if (list != null && list.size() > 0 && list.get(0) != null) {
			return (Integer) list.get(0);
		} else {
			return -1;
		}
		// else {
		// // ======当企业版本不存在则海关版本加1;
		// List listMaxVer = this
		// .find(
		// "select max(a.version) from EmsEdiMergerVersion a where a.emsEdiMergerBefore.ptNo=? and a.company.id= ?",
		// new Object[] { ptNo,
		// CommonUtils.getCompany().getId() });
		// if (listMaxVer != null && listMaxVer.size() > 0
		// && listMaxVer.get(0) != null) {
		// return (Integer) listMaxVer.get(0) + 1;
		// } else {
		// // ======当海关版本最大为空则企业版本为0
		// return 0;
		// }
		//
		// }
	}

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getMaxVersion(String ptNo) {
		List listMaxVer = this
				.find("select max(a.version) from EmsEdiMergerVersion a where a.emsEdiMergerBefore.ptNo=? and a.company.id= ?",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (listMaxVer != null && listMaxVer.size() > 0
				&& listMaxVer.get(0) != null) {
			return (Integer) listMaxVer.get(0) + 1;
		} else {
			return 0;
		}
	}

	/**
	 * 查找是否存在版本号（检查版本号是否重复--归并关系）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findExistVersion(EmsEdiMergerExgBefore emsExgBefore,
			String version) {
		return this
				.find("select a from EmsEdiMergerVersion a where a.emsEdiMergerBefore.id=? and version=? and a.company.id= ?",
						new Object[] { emsExgBefore.getId(),
								Integer.valueOf(version),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 增加成品Bom来自料件
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kImgToBom(EmsHeadH2k emsHeadH2k,
			EmsHeadH2kVersion version) {
		if (getIsEmsH2kSend()) {
			return this
					.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id=? "
							+ " and a.seqNum not in (select b.seqNum from CommdityForbid b where b.seqNum not in "
							+ " (select c.seqNum from EmsHeadH2kBom c where c.emsHeadH2kVersion.id=? ) and b.type='2') "
							+ "  and (a.sendState = ? or a.sendState = ?)",
							new Object[] { emsHeadH2k.getId(), version.getId(),
									Integer.valueOf(SendState.SEND),
									Integer.valueOf(SendState.WAIT_SEND) });
		} else {
			return this
					.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id=? "
							+ " and a.seqNum not in (select b.seqNum from CommdityForbid b where b.seqNum not in "
							+ " (select c.seqNum from EmsHeadH2kBom c where c.emsHeadH2kVersion.id=? ) and b.type='2') ",
							new Object[] { emsHeadH2k.getId(), version.getId() });
		}
	}

	/**
	 * 返回电子帐册分册
	 * 
	 * @param emsHeadH2kNo
	 *            电子帐册编号
	 * @return
	 */
	public List findEmsHeadH2kFas(String emsHeadH2kNo) {
		return this
				.find("select a from EmsHeadH2kFas a where a.company.id= ? and a.historyState=? and a.emsHeadH2kNo=?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), emsHeadH2kNo });
	}

	/**
	 * 返回电子帐册分册
	 * 
	 * @param emsHeadH2kNo
	 *            电子帐册编号
	 * @return
	 */
	public List findEmsHeadH2kFasAll(String emsHeadH2kNo) {
		return this
				.find("select a from EmsHeadH2kFas a where a.company.id= ? and a.emsHeadH2kNo=? order by a.modifyTimes",
						new Object[] { CommonUtils.getCompany().getId(),
								emsHeadH2kNo });
	}

	/**
	 * 返回电子帐册分册
	 * 
	 * @param emsHeadH2kNo
	 *            电子帐册编号
	 * @param copEmsNo
	 *            企业内部编码
	 * @return
	 */
	public List findEmsHeadH2kFasByCop(String emsHeadH2kNo, String copEmsNo) {
		return this
				.find("select a from EmsHeadH2kFas a where a.company.id= ? and a.historyState=? and a.emsHeadH2kNo=? and a.copEmsNo=?",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), emsHeadH2kNo, copEmsNo });
	}

	/**
	 * 保存分册
	 * 
	 * @param fas
	 *            电子帐册分册
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kFas(EmsHeadH2kFas fas) throws DataAccessException {
		this.saveOrUpdate(fas);
	}

	/**
	 * 保存分册料件
	 * 
	 * @param fasImg
	 *            电子帐册分册料件
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kFasImg(EmsHeadH2kFasImg fasImg)
			throws DataAccessException {
		this.saveOrUpdate(fasImg);
	}

	/**
	 * 保存分册成品
	 * 
	 * @param fasExg
	 *            电子帐册分册成品
	 * @throws DataAccessException
	 */
	public void saveEmsHeadH2kFasExg(EmsHeadH2kFasExg fasExg)
			throws DataAccessException {
		this.saveOrUpdate(fasExg);
	}

	/**
	 * 查找电子帐册分册料件
	 * 
	 * @param fas
	 *            分册料件
	 * @return
	 */
	public List findEmsHeadH2kFasImg(EmsHeadH2kFas fas) {
		return this
				.find("select a from EmsHeadH2kFasImg a where a.emsHeadH2kFas.id=? and a.company.id= ?",
						new Object[] { fas.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 电子帐册分册料件(报文使用)
	 * 
	 * @param fas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kFasImgToBaowen(EmsHeadH2kFas fas) {
		return this
				.find("select a from EmsHeadH2kFasImg a where a.modifyMark<>? and a.emsHeadH2kFas.id=? and a.company.id= ?",
						new Object[] { ModifyMarkState.UNCHANGE, fas.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 电子帐册分册成品(报文使用)
	 * 
	 * @param fas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kFasExg(EmsHeadH2kFas fas) {
		return this
				.find("select a from EmsHeadH2kFasExg a where a.emsHeadH2kFas.id=? and a.company.id= ?",
						new Object[] { fas.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 电子帐册分册成品(报文使用)
	 * 
	 * @param fas
	 *            电子帐册分册
	 * @return
	 */
	public List findEmsHeadH2kFasExgToBaowen(EmsHeadH2kFas fas) {
		return this
				.find("select a from EmsHeadH2kFasExg a where a.modifyMark<>? and a.emsHeadH2kFas.id=? and a.company.id= ?",
						new Object[] { ModifyMarkState.UNCHANGE, fas.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除电子帐册分册料件资料
	 * 
	 * @param img
	 *            分册料件
	 */
	public void deleteEmsHeadH2kFasImg(EmsHeadH2kFasImg img) {
		this.delete(img);
	}

	/**
	 * 删除电子帐册分册成品资料
	 * 
	 * @param exg
	 *            分册成品
	 */
	public void deleteEmsHeadH2kFasExg(EmsHeadH2kFasExg exg) {
		this.delete(exg);
	}

	/**
	 * 删除电子帐册分册资料
	 * 
	 * @param fas
	 *            电子帐册分册
	 */
	public void deleteEmsHeadH2kFas(EmsHeadH2kFas fas) {
		this.delete(fas);
	}

	/**
	 * 根据物料主档料号查找归并关系归并前是否存在
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public boolean findFactoryNameFromImg(String ptNo) {
		List list = this
				.find("select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.emsEdiMergerImgAfter.emsEdiMergerHead.historyState=?"
						+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.company.id=?",
						new Object[] { ptNo, new Boolean(false),
								CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据物料主档料号查找归并关系归并前是否存在
	 * 
	 * @param ptNo
	 *            料号
	 * @param declareState
	 *            申报状态
	 * @return
	 */
	public EmsEdiMergerImgBefore findEmsEdiMergerImgBeforeByImg(String ptNo,
			String declareState) {
		if (getIsMergerSend()) {
			List list = this
					.find("select a from EmsEdiMergerImgBefore a where a.ptNo=? "
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.historyState=?"
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.company.id=?",
							new Object[] { ptNo, new Boolean(false),
									CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsEdiMergerImgBefore) list.get(0);
			}
			return null;
		} else {
			List list = this
					.find("select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.emsEdiMergerImgAfter.emsEdiMergerHead.declareState=? "
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.historyState=?"
							+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.company.id=?",
							new Object[] { ptNo, declareState,
									new Boolean(false),
									CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsEdiMergerImgBefore) list.get(0);
			}
			return null;
		}
	}

	/**
	 * 根据申报状态查找电子帐册归并关系表头资料
	 * 
	 * @param declareState
	 *            申报状态
	 * @return
	 */
	public EmsEdiMergerHead findEmsEdiMergerHead(String declareState) {
		if (getIsMergerSend()) {
			List ls = this.find(
					"select a from EmsEdiMergerHead a where  a.historyState=?"
							+ " and a.company.id = ?", new Object[] {
							new Boolean(false),
							CommonUtils.getCompany().getId() });
			if (ls != null && ls.size() > 0) {
				return (EmsEdiMergerHead) ls.get(0);
			}
			return null;
		} else {
			List ls = this
					.find("select a from EmsEdiMergerHead a where a.declareState = ? and a.historyState=?"
							+ " and a.company.id = ?", new Object[] {
							declareState, new Boolean(false),
							CommonUtils.getCompany().getId() });
			if (ls != null && ls.size() > 0) {
				return (EmsEdiMergerHead) ls.get(0);
			}
			return null;
		}
	}

	/**
	 * 根据料号查找归并前成品
	 * 
	 * @param seqNum
	 *            成品序号
	 * @return
	 */
	public boolean findFactoryNameFromExg(String ptNo) {
		List list = this
				.find("select a from EmsEdiMergerExgBefore a where a.ptNo=? and a.emsEdiMergerExgAfter.emsEdiMergerHead.historyState=?"
						+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.company.id=?",
						new Object[] { ptNo, new Boolean(false),
								CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 电子帐册使用--得到归并后料件--来自内部归并--通过经营范围四码筛选
	 * 
	 * @param type
	 *            物料类别
	 * @param emsEdiTrHead
	 *            经营范围表头
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findInnerMergeDataByType10ToEms(String type,
			EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsHeadH2k) { // 显示十码
		String emsTrClass = "";
		String emsEdiClass = "";
		if (type.equals(MaterielType.MATERIEL)) {
			emsTrClass = "EmsEdiTrImg";
			emsEdiClass = "EmsHeadH2kImg";
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
			emsTrClass = "EmsEdiTrExg";
			emsEdiClass = "EmsHeadH2kExg";
		}
		return this

				.find("select distinct a.hsAfterTenMemoNo,a.hsAfterComplex,a.hsAfterMaterielTenName,"
						+ "a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit,a.hsAfterLegalUnit,a.hsAfterSecondLegalUnit"
						+ " from InnerMergeData a left outer join  a.hsAfterSecondLegalUnit"
						+ " where (a.imrType = ? or a.imrType = ?) and a.company.id=?"
						+ " and a.hsFourNo in (select b.ptNo from "
						+ emsTrClass
						+ " b where b.emsEdiTrHead.id=?)"
						+ " and a.hsAfterTenMemoNo not in (select c.seqNum from "
						+ emsEdiClass + " c where c.emsHeadH2k.id=?)",
						new Object[] { type,
								MaterielType.SEMI_FINISHED_PRODUCT,
								CommonUtils.getCompany().getId(),
								emsEdiTrHead.getId(), emsHeadH2k.getId() });
	}

	/**
	 * 显示电子帐册料件变更记录来自变更次数
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param modifyTimes
	 *            修改次数
	 * @return
	 */
	public List findEmsHeadH2kImgChange(EmsHeadH2k emsHeadH2k,
			Integer modifyTimes) {
		return this.find("select a from EmsHeadH2kImg a where  "
				+ "  a.emsHeadH2k.id=? and a.company.id= ? "
				+ "   and  a.modifyTimes= ? order by a.seqNum", new Object[] {
				emsHeadH2k.getId(), CommonUtils.getCompany().getId(),
				modifyTimes });
	}

	/**
	 * 显示归并关系料件变更记录来自变更次数
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @param modifyTimes
	 *            修改次数
	 * @return
	 */
	public List findemsEdiMergerHeadImgChange(
			EmsEdiMergerHead emsEdiMergerHead, Integer modifyTimes) {
		return this
				.find("select a from EmsEdiMergerImgBefore a left outer join  a.emsEdiMergerImgAfter where  "
						+ "  a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.company.id= ? "
						+ "   and  a.modifyTimes= ? order by a.seqNum",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId(), modifyTimes });
	}

	/**
	 * 显示归并关系成品变更记录来自变更次数
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @param modifyTimes
	 *            修改次数
	 * @return
	 */
	public List findemsEdiMergerHeadExgChange(
			EmsEdiMergerHead emsEdiMergerHead, Integer modifyTimes) {
		return this
				.find("select a from EmsEdiMergerExgBefore a left outer join  a.emsEdiMergerExgAfter where  "
						+ "  a.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ? "
						+ "   and  a.modifyTimes= ? order by a.seqNum",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId(), modifyTimes });
	}

	/**
	 * 显示归并关系Bom变更记录来自变更次数
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @param modifyTimes
	 *            修改次数
	 * @return
	 */
	public List findemsEdiMergerHeadBomChange(
			EmsEdiMergerHead emsEdiMergerHead, Integer modifyTimes) {
		return this
				.find("select a from EmsEdiMergerExgBom a left outer join  a.emsEdiMergerVersion "
						+ "  where a.company.id= ? and a.modifyTimes= ? "
						+ " order by a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum,a.emsEdiMergerVersion.version",
						new Object[] { CommonUtils.getCompany().getId(),
								modifyTimes });
	}

	/**
	 * 显示电子帐册料件变更记录来备案序号段
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param beginNo
	 *            开始料件序号
	 * @param endNo
	 *            结束料件序号
	 * @param seqNumArray
	 *            料件序号数组集合
	 * @return
	 */
	public List findEmsHeadH2kImgChange(EmsHeadH2k emsHeadH2k, Integer beginNo,
			Integer endNo, Integer[] seqNumArray) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kImg a where "
				+ "  a.emsHeadH2k.id=? and a.company.id= ? ";
		// parameters.add(ModifyMarkState.UNCHANGE);
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (beginNo != null) {
			hsql += " and a.seqNum>=? ";
			parameters.add(beginNo);
		}
		if (endNo != null) {
			hsql += " and a.seqNum<=? ";
			parameters.add(endNo);
		}
		if (seqNumArray != null && seqNumArray.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < seqNumArray.length; i++) {
				if (i == seqNumArray.length - 1) {
					hsql += " a.seqNum=? ";
				} else {
					hsql += " a.seqNum=? or ";
				}
				parameters.add(seqNumArray[i]);
			}
			hsql += " ) ";
		}
		hsql += " order by a.seqNum ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示电子帐册成品变更记录来自变更次数
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param modifyTimes
	 *            修改次数
	 * @return
	 */
	public List findEmsHeadH2kExgChange(EmsHeadH2k emsHeadH2k,
			Integer modifyTimes) {
		return this.find("select a from EmsHeadH2kExg a where "
				+ "  a.emsHeadH2k.id=? and a.company.id= ?"
				+ " and a.modifyTimes = ? order by a.seqNum", new Object[] {
				emsHeadH2k.getId(), CommonUtils.getCompany().getId(),
				modifyTimes });
	}

	/**
	 * 显示电子帐册成品变更记录来自备案序号段
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param beginNo
	 *            开始成品序号
	 * @param endNo
	 *            结束成品序号
	 * @param seqNumArray
	 *            成品序号数组集合
	 * @return
	 */
	public List findEmsHeadH2kExgChange(EmsHeadH2k emsHeadH2k, Integer beginNo,
			Integer endNo, Integer[] seqNumArray) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kExg a where "
				+ "  a.emsHeadH2k.id=? and a.company.id= ?";
		// parameters.add(ModifyMarkState.UNCHANGE);
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (beginNo != null) {
			hsql += " and a.seqNum>=? ";
			parameters.add(beginNo);
		}
		if (endNo != null) {
			hsql += " and a.seqNum<=? ";
			parameters.add(endNo);
		}
		if (seqNumArray != null && seqNumArray.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < seqNumArray.length; i++) {
				if (i == seqNumArray.length - 1) {
					hsql += " a.seqNum=? ";
				} else {
					hsql += " a.seqNum=? or ";
				}
				parameters.add(seqNumArray[i]);
			}
			hsql += " ) ";
		}
		hsql += " order by a.seqNum ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示电子帐册Bom变更记录来自变更次数
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param modifyTimes
	 *            修改次数
	 * @return
	 */
	public List findEmsHeadH2kBomChange(EmsHeadH2k emsHeadH2k,
			Integer modifyTimes) {
		return this
				.find("select a from EmsHeadH2kBom a where "
						+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? "
						+ " and a.modifyTimes = ? "
						+ " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(), modifyTimes });
	}

	/**
	 * 显示电子帐册Bom变更记录来自备案序号段
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param beginNo
	 *            电子帐册成品序号(开始)
	 * @param endNo
	 *            电子帐册成品序号(结束)
	 * @param seqNumArray
	 *            成品序号数组集合
	 * @return
	 */
	public List findEmsHeadH2kBomChange(EmsHeadH2k emsHeadH2k, Integer beginNo,
			Integer endNo, Integer[] seqNumArray) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kBom a where "
				+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? ";
		// parameters.add(ModifyMarkState.UNCHANGE);
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (beginNo != null) {
			hsql += " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum>=? ";
			parameters.add(beginNo);
		}
		if (endNo != null) {
			hsql += " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum<=? ";
			parameters.add(endNo);
		}
		if (seqNumArray != null && seqNumArray.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < seqNumArray.length; i++) {
				if (i == seqNumArray.length - 1) {
					hsql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? ";
				} else {
					hsql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? or ";
				}
				parameters.add(seqNumArray[i]);
			}
			hsql += " ) ";
		}
		hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示电子帐册Bom变更记录来自备案序号段
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param beginNo
	 *            电子帐册成品序号(开始)
	 * @param endNo
	 *            电子帐册成品序号(结束)
	 * @param seqNumArray
	 *            成品序号数组集合
	 * @return
	 */
	public List findEmsHeadH2kBomSingleVersionChange(EmsHeadH2k emsHeadH2k,
			Map<Integer, List<Integer>> mapAllSeqNum) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kBom a where "
				+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());

		if (mapAllSeqNum != null && mapAllSeqNum.size() > 0) {
			hsql += " and ( ";
			Iterator iter = mapAllSeqNum.entrySet().iterator();
			int i = 0;
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Integer key = (Integer) entry.getKey();
				List listVer = (List) entry.getValue();
				if (i == mapAllSeqNum.size() - 1) {
					hsql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? ";
					parameters.add(key);
					if (listVer != null && listVer.size() > 0) {
						hsql += " and ( ";
						for (int j = 0; j < listVer.size(); j++) {
							if (j == listVer.size() - 1) {
								hsql += " a.emsHeadH2kVersion.version=? ";
							} else {
								hsql += " a.emsHeadH2kVersion.version=? or ";
							}
							parameters.add(listVer.get(j));
						}
						hsql += " ) ";
					}
				} else {
					hsql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=?  ";
					parameters.add(key);
					if (listVer != null && listVer.size() > 0) {
						hsql += " and ( ";
						for (int j = 0; j < listVer.size(); j++) {
							if (j == listVer.size() - 1) {
								hsql += " a.emsHeadH2kVersion.version=? ";
							} else {
								hsql += " a.emsHeadH2kVersion.version=? or ";
							}
							parameters.add(listVer.get(j));
						}
						hsql += " ) or ";
					} else {
						hsql += "  or ";
					}
				}
				i++;
			}
			hsql += " ) ";
		}
		hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示电子帐册Bom变更记录来自备案序号段
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isDeclared
	 *            是否申报
	 * @return
	 */
	public List findEmsHeadH2kBomByDate(EmsHeadH2k emsHeadH2k, Date beginDate,
			Date endDate, boolean isDeclared) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kBom a where "
				+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? and a.company.id= ? ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.changeDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.changeDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (isDeclared) {
			hsql += " and a.modifyMark=? ";
			parameters.add(ModifyMarkState.UNCHANGE);
		} else {
			hsql += " and a.modifyMark!=? ";
			parameters.add(ModifyMarkState.UNCHANGE);
		}
		hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询存放企业内部归并－－内部归并资料
	 * 
	 * @param isImg
	 *            料件还是成品
	 * @param seqNum
	 *            十位备案序号
	 * @return
	 */
	public List findInnerBySeqNum(boolean isImg, Integer seqNum) {
		String type = "";
		if (isImg) {
			type = MaterielType.MATERIEL;
		} else {
			type = MaterielType.FINISHED_PRODUCT;
		}
		return this
				.find("select a from InnerMergeData a where a.imrType = ? and a.hsAfterTenMemoNo = ?",
						new Object[] { type, seqNum });
	}

	/**
	 * 保存存放企业内部归并－－内部归并资料
	 * 
	 * @param obj
	 */
	public void saveInnerMergeData(InnerMergeData obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存内部归并－－反向归并第二层数据资料
	 * 
	 * @param obj
	 */
	public void saveReverseMergeTenData(ReverseMergeTenData obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存内部归并－－反向归并第三层数据资料
	 * 
	 * @param obj
	 */
	public void saveReverseMergeFourData(ReverseMergeFourData obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 查找电子帐册成品BOM
	 * 
	 * @param seqNum
	 *            成品序号
	 * @param emsheadh2k
	 *            电子帐册表头
	 * @return
	 */
	public List findBomBySeqNum(String seqNum, EmsHeadH2k emsheadh2k) {
		return this
				.find("select a from EmsHeadH2kBom a where a.seqNum = ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=?",
						new Object[] { Integer.valueOf(seqNum),
								emsheadh2k.getId() });
	}

	/**
	 * 电子帐册历版本
	 * 
	 * @param id
	 *            电子帐册成品ID
	 * @return
	 */
	public List findVersionNo(String id) {
		return this.find(
				"select a.version from EmsHeadH2kVersion a where a.emsHeadH2kExg.id=? "
						+ " and (a.isForbid is null or a.isForbid = ?)",
				new Object[] { id, new Boolean(false) });
	}

	/**
	 * 查找电子帐册历版本
	 * 
	 * @param obj
	 *            电子帐册成品
	 * @param bv
	 *            开始版本
	 * @param ev
	 *            结束版本
	 * @return
	 */
	public List findEmsVersion(EmsHeadH2kExg obj, Integer bv, Integer ev) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id =? and a.company.id= ? ";
		parameters.add(obj.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (bv != null) {
			hsql += " and a.version >= ? ";
			parameters.add(bv);
		}
		if (ev != null) {
			hsql += " and a.version <= ? ";
			parameters.add(ev);
		}
		hsql += " order by a.version";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找电子帐册历版本
	 * 
	 * @param obj
	 *            电子帐册成品
	 * @return
	 */
	public List findEmsVersion(EmsHeadH2kExg obj) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id =? and a.company.id= ? ";
		parameters.add(obj.getId());
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " order by a.version";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询电子帐册备案料件信息(注意：商品禁用使用到)
	 * 
	 * @param isMaterial
	 *            物料类别
	 * @return
	 */
	public List findEdiMaterielInfo(boolean isMaterial) {
		String classname = "";
		List list = null;
		if (getIsEmsH2kSend()) {
			if (isMaterial) {
				classname = "EmsHeadH2kImg";
				list = this
						.find(" select a from "
								+ classname
								+ " as a "
								+ " where a.emsHeadH2k.historyState=? "
								+ " and a.emsHeadH2k.company.id=? "
								+ " and a.sendState = ? and( a.isForbid=? or a.isForbid is null) "
								+ "  order by a.seqNum",
								new Object[] { new Boolean(false),
										CommonUtils.getCompany().getId(),
										Integer.valueOf(SendState.SEND),
										new Boolean(false) });
			} else {
				classname = "EmsHeadH2kVersion";// EmsHeadH2kExg
				list = this
						.find(" select a from "
								+ classname
								+ " as a "
								+ " where a.emsHeadH2kExg.emsHeadH2k.historyState=? "
								+ " and a.emsHeadH2kExg.emsHeadH2k.company.id=? "
								+ " and a.emsHeadH2kExg.sendState = ?  and( a.isForbid=? or a.isForbid is null)"
								+ "  order by a.emsHeadH2kExg.seqNum",
								new Object[] { new Boolean(false),
										CommonUtils.getCompany().getId(),
										Integer.valueOf(SendState.SEND),
										new Boolean(false) });
			}
		} else {
			if (isMaterial) {
				classname = "EmsHeadH2kImg";
				list = this
						.find(" select a from "
								+ classname
								+ " as a "
								+ " where a.emsHeadH2k.declareState=? "
								+ " and a.emsHeadH2k.historyState=? "
								+ " and a.emsHeadH2k.company.id=? and( a.isForbid=? or a.isForbid is null) "
								+ "  order by a.seqNum", new Object[] {
								DeclareState.PROCESS_EXE, new Boolean(false),
								CommonUtils.getCompany().getId(),
								new Boolean(false) });
			} else {
				classname = "EmsHeadH2kVersion ";// EmsHeadH2kExg
				list = this
						.find(" select a from "
								+ classname
								+ " as a "
								+ " where a.emsHeadH2kExg.emsHeadH2k.declareState=? "
								+ " and a.emsHeadH2kExg.emsHeadH2k.historyState=? "
								+ " and a.emsHeadH2kExg.emsHeadH2k.company.id=? and( a.isForbid=? or a.isForbid is null) "
								+ "  order by a.emsHeadH2kExg.seqNum",
								new Object[] { DeclareState.PROCESS_EXE,
										new Boolean(false),
										CommonUtils.getCompany().getId(),
										new Boolean(false) });
			}
		}
		return list;
	}

	/**
	 * 保存商品禁用信息
	 * 
	 * @param obj
	 *            商品禁用信息
	 */
	public void saveCommdityForbid(CommdityForbid obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存商品禁用信息历史
	 * 
	 * @param obj
	 *            商品禁用信息历史
	 */
	public void saveCommdityForbidHis(CommdityForbidHis obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存限制类商品
	 * 
	 * @param obj
	 *            限制类商品
	 */
	public void saveRestirictCommodity(RestirictCommodity obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 电子帐册料件
	 * 
	 * @param seqNum
	 *            料件序号
	 * @return
	 */
	public void updateEmsH2kImgIsForbidBySeqNum(String seqNum, boolean isForbid) {
		if (getIsEmsH2kSend()) {
			this.batchUpdateOrDelete(
					"  update EmsHeadH2kImg a set a.isForbid =?  where a.company.id=? and a.seqNum = ?",
					new Object[] { isForbid, CommonUtils.getCompany().getId(),
							Integer.valueOf(seqNum) });
		} else {
			List list = this
					.find(" select a from EmsHeadH2k a "
							+ " where a.company.id=? and a.declareState=? and a.historyState=? ",
							new Object[] { CommonUtils.getCompany().getId(),
									DeclareState.PROCESS_EXE,
									new Boolean(false) });
			if (list != null && list.size() > 0) {
				String emsHead2kId = ((EmsHeadH2k) list.get(0)).getId();
				this.batchUpdateOrDelete(
						"update EmsHeadH2kImg a set a.isForbid =?  where a.emsHeadH2k.id=? "
								+ " and a.company.id=? and a.seqNum = ?",
						new Object[] { isForbid, emsHead2kId,
								CommonUtils.getCompany().getId(),
								Integer.valueOf(seqNum) });
			}
		}
	}

	/**
	 * 删除商品禁用信息
	 * 
	 * @param obj
	 *            商品禁用信息
	 */
	public void deleteCommdityForbid(CommdityForbid obj) {
		this.delete(obj);
	}

	/**
	 * 删除限制类商品
	 * 
	 * @param obj
	 *            限制类商品
	 */
	public void deleteRestirictCommodity(RestirictCommodity obj) {
		this.delete(obj);
	}

	/**
	 * 查找商品禁用信息
	 * 
	 * @param isMateriel
	 *            物料类别
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findCommdityForbid(boolean isMateriel, Date beginDate,
			String serialNo, Date endDate) {
		List list = new Vector();
		List<Object> parameters = new ArrayList<Object>();
		String sql = "";
		if (isMateriel) {
			sql = "select a from CommdityForbid a where a.type = ? and a.company.id = ?";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		} else {
			sql = "select a from CommdityForbid a where a.type = ? and a.company.id = ?";
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		}
		if (serialNo != null && !"".equals(serialNo)) {
			sql += " and a.seqNum = ?";
			parameters.add(serialNo);
		}

		// sql += " order by Convert(Integer,a.seqNum)";
		sql += " order by   a.seqNum ";
		list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 查找商品禁用信息
	 * 
	 * @param isMateriel
	 *            物料类别
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findCommdityForbid(boolean isMateriel, Date beginDate,
			String serialNo, Date endDate, Boolean isForbid) {

		List list = null;
		HashMap<String, CommdityForbid> forbidMap = new HashMap<String, CommdityForbid>();
		List<CommdityForbid> forbidList = new ArrayList<CommdityForbid>();
		if (isForbid == null || isForbid == false) {
			if (isMateriel) {
				list = this
						.find(" select a from "
								+ "EmsHeadH2kImg"
								+ " as a "
								+ " where a.emsHeadH2k.historyState=? "
								+ " and a.emsHeadH2k.company.id=? "
								+ " and a.sendState = ?"
								+ (CommonUtils.isEmpty(serialNo) ? ""
										: (" and a.seqNum = " + serialNo))
								+ (isForbid == null ? ""
										: (" and (a.isForbid = false or a.isForbid is null)"))
								+ " order by a.seqNum",
								new Object[] { Boolean.FALSE,
										CommonUtils.getCompany().getId(),
										Integer.valueOf(SendState.SEND) });
				CommonUtils.listToMap(list, forbidMap,
						new GetKeyValueImpl<EmsHeadH2kImg>() {
							public String getKey(EmsHeadH2kImg e) {
								return e.getSeqNum().toString();
							}

							public void put(EmsHeadH2kImg e, Map map) {
								CommdityForbid obj = new CommdityForbid();

								obj.setSeqNum(e.getSeqNum().toString());
								obj.setComplex(e.getComplex().getCode());
								obj.setName(e.getName());
								obj.setSpec(e.getSpec());
								obj.setUnit(e.getUnit().getName());
								obj.setType(MaterielType.MATERIEL);

								obj.setIsForbid(e.getIsForbid());

								map.put(getKey(e), obj);
							}
						});

				forbidList = findCommdityForbid(MaterielType.MATERIEL);

				if (forbidList != null && !forbidList.isEmpty()) {
					for (CommdityForbid o : forbidList) {
						if (forbidMap.get(o.getSeqNum()) != null) {
							o.setIsForbid(Boolean.TRUE);
							forbidMap.put(o.getSeqNum(), o);
						}
					}
				}

				forbidList = new ArrayList<CommdityForbid>(forbidMap.values());
			} else {
				list = this
						.find(" select a from "
								+ "EmsHeadH2kVersion"
								+ " as a join fetch a.emsHeadH2kExg"
								+ " where a.emsHeadH2kExg.emsHeadH2k.historyState=? "
								+ " and a.emsHeadH2kExg.emsHeadH2k.company.id=? "
								+ " and a.emsHeadH2kExg.sendState = ?"
								+ (CommonUtils.isEmpty(serialNo) ? ""
										: (" and a.emsHeadH2kExg.seqNum = " + serialNo))
								+ (isForbid == null ? ""
										: (" and (a.isForbid = false or a.isForbid is null)"))
								+ " order by a.emsHeadH2kExg.seqNum",
								new Object[] { Boolean.FALSE,
										CommonUtils.getCompany().getId(),
										Integer.valueOf(SendState.SEND) });

				CommonUtils.listToMap(list, forbidMap,
						new GetKeyValueImpl<EmsHeadH2kVersion>() {
							public String getKey(EmsHeadH2kVersion e) {
								return e.getEmsHeadH2kExg().getSeqNum()
										.toString()
										+ "/" + e.getVersion();
							}

							public void put(EmsHeadH2kVersion e, Map map) {
								CommdityForbid obj = new CommdityForbid();

								obj.setSeqNum(e.getEmsHeadH2kExg().getSeqNum()
										.toString());
								obj.setComplex(e.getEmsHeadH2kExg()
										.getComplex().getCode());
								obj.setName(e.getEmsHeadH2kExg().getName());
								obj.setSpec(e.getEmsHeadH2kExg().getSpec());
								obj.setVersion(e.getVersion() + "");
								obj.setUnit(e.getEmsHeadH2kExg().getUnit()
										.getName());
								obj.setType(MaterielType.FINISHED_PRODUCT);

								obj.setIsForbid(e.getIsForbid());

								map.put(getKey(e), obj);
							}
						});

				forbidList = findCommdityForbid(MaterielType.FINISHED_PRODUCT);

				if (forbidList != null && !forbidList.isEmpty()) {
					for (CommdityForbid o : forbidList) {
						if (forbidMap.get(o.getSeqNum() + "/" + o.getVersion()) != null) {
							o.setIsForbid(Boolean.TRUE);
							forbidMap.put(o.getSeqNum() + "/" + o.getVersion(),
									o);
						}
					}
				}
			}

			forbidList = new ArrayList<CommdityForbid>(forbidMap.values());
		} else if (isForbid) {
			list = this.findCommdityForbid(isMateriel, beginDate, serialNo,
					endDate);
			CommdityForbid e = null;
			for (int i = 0; i < list.size(); i++) {
				e = (CommdityForbid) list.get(i);
				e.setIsForbid(Boolean.TRUE);

				forbidList.add(e);
			}
		}

		return forbidList;
	}

	public List findRelativeInfoFromMaterial(EmsHeadH2k emsHeadH2k,
			Integer seqNum, Integer version, Boolean isMaterial) {
		List list = null;
		List<CommdityForbid> forbidList = null;
		HashMap<String, CommdityForbid> forbidMap = new HashMap<String, CommdityForbid>();
		if (isMaterial) {
			StringBuilder hql = new StringBuilder(
					" select a from EmsHeadH2kBom a "
							+ " join fetch a.emsHeadH2kVersion b"
							+ " join fetch b.emsHeadH2kExg c"
							+ " join fetch c.emsHeadH2k d"
							+ " where a.company.id = ?" + " and d.id = ?");
			hql.append(" and a.seqNum = ? ");

			list = find(hql.toString(), new Object[] {
					CommonUtils.getCompany().getId(), emsHeadH2k.getId(),
					seqNum });

			CommonUtils.listToMap(list, forbidMap,
					new GetKeyValueImpl<EmsHeadH2kBom>() {
						public String getKey(EmsHeadH2kBom e) {
							return e.getEmsHeadH2kExg().getSeqNum().toString()
									+ "/"
									+ e.getEmsHeadH2kVersion().getVersion();
						}

						public void put(EmsHeadH2kBom e, Map map) {
							CommdityForbid obj = new CommdityForbid();

							obj.setSeqNum(e.getEmsHeadH2kExg().getSeqNum()
									.toString());
							obj.setComplex(e.getEmsHeadH2kExg().getComplex()
									.getCode());
							obj.setName(e.getEmsHeadH2kExg().getName());
							obj.setSpec(e.getEmsHeadH2kExg().getSpec());
							obj.setUnit(e.getEmsHeadH2kExg().getUnit()
									.getName());
							obj.setVersion(e.getEmsHeadH2kVersion()
									.getVersion() + "");
							obj.setType(MaterielType.FINISHED_PRODUCT);

							map.put(getKey(e), obj);
						}
					});
			forbidList = findCommdityForbid(MaterielType.FINISHED_PRODUCT);

			if (forbidList != null && !forbidList.isEmpty()) {
				for (CommdityForbid o : forbidList) {
					if (forbidMap.get(o.getSeqNum() + "/" + o.getVersion()) != null) {
						o.setIsForbid(Boolean.TRUE);
						forbidMap.put(o.getSeqNum() + "/" + o.getVersion(), o);
					}
				}
			}

		} else {
			list = this
					.findEmsHeadH2kBomByCpSeqNum(seqNum, emsHeadH2k, version);

			CommonUtils.listToMap(list, forbidMap,
					new GetKeyValueImpl<EmsHeadH2kBom>() {
						public String getKey(EmsHeadH2kBom e) {
							return e.getSeqNum().toString();
						}

						public void put(EmsHeadH2kBom e, Map map) {
							CommdityForbid obj = new CommdityForbid();

							obj.setSeqNum(e.getSeqNum().toString());
							obj.setComplex(e.getComplex().getCode());
							obj.setName(e.getName());
							obj.setSpec(e.getSpec());
							obj.setUnit(e.getUnit().getName());
							obj.setType(MaterielType.MATERIEL);

							map.put(getKey(e), obj);
						}
					});

			forbidList = findCommdityForbid(MaterielType.MATERIEL);

			if (forbidList != null && !forbidList.isEmpty()) {
				for (CommdityForbid o : forbidList) {
					if (forbidMap.get(o.getSeqNum()) != null) {
						o.setIsForbid(Boolean.TRUE);
						forbidMap.put(o.getSeqNum(), o);
					}
				}
			}

		}

		return new ArrayList(forbidMap.values());
	}

	/**
	 * 查找商品禁用信息历史
	 * 
	 * @param isMateriel
	 *            物料类别
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findCommdityForbidHis(boolean isMateriel, Date beginDate,
			String serialNo, Date endDate) {
		List list = new Vector();
		List<Object> parameters = new ArrayList<Object>();
		String sql = "";
		if (isMateriel) {
			sql = "select a from CommdityForbidHis a where a.type = ? and a.company.id = ?";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		} else {
			sql = "select a from CommdityForbidHis a where a.type = ? and a.company.id = ?";
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				sql += " and (a.forbiddate>=?  or a.forbiddate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.forbiddate<=?  or a.forbiddate is null) ";
				parameters.add(endDate);
			}
		}
		if (serialNo != null && !"".equals(serialNo)) {
			sql += " and a.seqNum = ?";
			parameters.add(serialNo);
		}

		// sql += " order by Convert(Integer,a.seqNum)";
		sql += " order by   a.seqNum ";
		list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 限制类商品
	 * 
	 * @param isMateriel
	 *            物料类别
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findRestirictCommodity(boolean isMateriel, Date beginDate,
			Date endDate) {
		List list = new Vector();
		List<Object> parameters = new ArrayList<Object>();
		String sql = "";
		if (isMateriel) {
			sql = "select a from RestirictCommodity a where a.types = ? and a.company.id = ?";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				sql += " and (a.vindicadate>=?  or a.vindicadate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.vindicadate<=?  or a.vindicadate is null) ";
				parameters.add(endDate);
			}
		} else {
			sql = "select a from RestirictCommodity a where a.types = ? and a.company.id = ?";
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				sql += " and (a.vindicadate>=?  or a.vindicadate is null) ";
				parameters.add(beginDate);
			}
			if (endDate != null) {
				sql += " and (a.vindicadate<=?  or a.vindicadate is null) ";
				parameters.add(endDate);
			}
		}
		// sql += " order by Convert(Integer,a.seqNum) ";
		sql += " order by   a.seqNum ";
		list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 显示归并关系归并前成品（由归并后成品一条条过滤）
	 * 
	 * @param emsExgAfter
	 *            电子帐册归并关系归并后成品
	 * @param firstIndex
	 *            分页
	 * @return
	 */
	public List findEmsEdiMergerExgBefore(EmsEdiMergerExgAfter emsExgAfter,
			int firstIndex) {
		return this
				.findPageList(
						"select a from EmsEdiMergerExgBefore a where a.emsEdiMergerExgAfter.id=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsExgAfter.getId(),
								CommonUtils.getCompany().getId() }, firstIndex,
						100);
	}

	/**
	 * 查找报关常用工厂BOM成品资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public MaterialBomMaster findMaterialBomMasterByPtno(String ptNo) {
		List list = this
				.find("select a from MaterialBomMaster a "
						+ " left outer join fetch a.materiel where a.materiel.ptNo=? and a.company.id = ?",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (MaterialBomMaster) list.get(0);
		}
		return null;
	}

	/**
	 * 报关常用工厂BOM版本资料
	 * 
	 * @param master
	 *            报关常用工厂BOM成品资料
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findVersionByBomMaster(MaterialBomMaster master, Integer version) {
		return this
				.find("select a from MaterialBomVersion a "
						+ " left outer join fetch a.master where a.master.id = ? and a.version=? and a.company.id = ? order by a.version",
						new Object[] { master.getId(), version,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询报关常用工厂BOM料件资料
	 * 
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findDetailByVersion(MaterialBomVersion version) {
		return this
				.find("select a from MaterialBomDetail a "
						+ " left outer join fetch a.materiel "
						+ " left outer join fetch a.version where a.version.id = ? and a.company.id = ?",
						new Object[] { version.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找归并关系归并前料件表
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeByPtNo(
			EmsEdiMergerHead emsEdiMergerHead) {
		List list = this
				.find("select a from EmsEdiMergerImgBefore a "
						+ " left outer join fetch a.complex "
						+ " left outer join fetch a.unit "
						+ " left outer join fetch a.complex.firstUnit "
						+ " left outer join fetch a.complex.secondUnit "
						+ " left outer join fetch a.curr where a.company.id = ? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsEdiMergerHead.getId() });
		return list;
	}

	/**
	 * 查找指定料号的归并关系归并前料件表
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeByPtNo(
			EmsEdiMergerHead emsEdiMergerHead, Set<String> ptNoSet) {
		String hql = new String(
				"select a from EmsEdiMergerImgBefore a "
						+ " left outer join fetch a.complex "
						+ " left outer join fetch a.unit "
						+ " left outer join fetch a.complex.firstUnit "
						+ " left outer join fetch a.complex.secondUnit "
						+ " left outer join fetch a.curr where a.company.id = ? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id = ?");
		List rs = new ArrayList();
		List<String> tmpLs = new ArrayList<String>(ptNoSet);
		String ql;
		for (int i = 0; i < tmpLs.size(); i += 1000) {
			int max = i + 1000 > tmpLs.size() ? tmpLs.size() : i + 1000;
			ql = hql + " and a.ptNo in ('"
					+ StringUtils.join(tmpLs.subList(i, max).toArray(), "','")
					+ "') ";
			rs.addAll(this.find(ql,
					new Object[] { CommonUtils.getCompany().getId(),
							emsEdiMergerHead.getId() }));
		}
		return rs;
		// if (ptNoSet != null && ptNoSet.size() > 0) {
		// hql.append(" and a.ptNo in (");
		// for (String ptNo : ptNoSet) {
		// hql.append("'" + ptNo + "',");
		// }
		// hql.append("'')");
		// }
		//
		// List list = this.find(hql.toString(), new Object[] {
		// CommonUtils.getCompany().getId(), emsEdiMergerHead.getId() });
		// return list;
	}

	/**
	 * 查找指定料号的归并关系归并前料件表
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeByPtNo(Set<String> ptNoSet) {
		StringBuilder hql = new StringBuilder(
				"select a from EmsEdiMergerImgBefore a "
						+ " left outer join fetch a.complex "
						+ " left outer join fetch a.unit "
						+ " left outer join fetch a.complex.firstUnit "
						+ " left outer join fetch a.complex.secondUnit "
						+ " left outer join fetch a.curr where a.company.id = ?");

		if (ptNoSet != null && ptNoSet.size() > 0) {
			hql.append(" and a.ptNo in (");
			for (String ptNo : ptNoSet) {
				hql.append("'" + ptNo + "',");
			}
			hql.append("'')");
		}

		List list = this.find(hql.toString(), new Object[] { CommonUtils
				.getCompany().getId() });
		return list;
	}

	/**
	 * 查找归并关系归并前成品表
	 * 
	 * @param emsEdiMergerHed
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeByPtNo(
			EmsEdiMergerHead emsEdiMergerHead) {
		List list = this
				.find("select a from EmsEdiMergerExgBefore a "
						+ " left outer join fetch a.complex "
						+ " left outer join fetch a.unit "
						+ " left outer join fetch a.complex.firstUnit "
						+ " left outer join fetch a.complex.secondUnit "
						+ " left outer join fetch a.curr where a.company.id = ? and a.emsEdiMergerExgAfter.emsEdiMergerHead.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsEdiMergerHead.getId() });
		/*
		 * if (list != null && list.size()>0){ return (EmsEdiMergerExgBefore)
		 * list.get(0); } return null;
		 */
		return list;
	}

	/**
	 * 查找BCUS参数设置
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public List getBparaList(int type) {
		return this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回参数设定
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public String getBpara(int type) {
		List list = find(
				"select a from BcusParameter a where a.type = ? and a.company.id = ?",
				new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	/**
	 * 保存参数设定
	 * 
	 * @param obj
	 *            参数设置
	 */
	public void saveBcusParameter(BcusParameter obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 返回归并前成品
	 * 
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeAll() {
		return this.find(
				"select a from EmsEdiMergerExgBefore a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回成品BOM表
	 * 
	 * @param head
	 *            电子帐册归并关系表头
	 * @param exgSeqNum
	 *            归并后成品序号
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsEdiMergerBomBySeqNum(EmsEdiMergerHead head,
			Integer exgSeqNum, Integer version) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.company.id = ? "
						+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id = ?"
						+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum = ? "
						+ " and a.emsEdiMergerVersion.version = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), exgSeqNum, version });
	}

	/**
	 * 返回归并关系归并前料件表
	 * 
	 * @return
	 */
	public List findEmsEdiMergerImgBeforeAll() {
		return this.find(
				"select a from EmsEdiMergerImgBefore a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 得到物料由十码
	 * 
	 * @param type
	 *            物料类别
	 * @param tenNo
	 *            十码序号
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findInnerMergeDataByTenNoPtNo(String type, Integer tenNo,
			String ptNo) {
		return this
				.find(" select a from InnerMergeData as a "
						+ " left join fetch a.materiel"
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.imrType=? and a.company.id=? and a.hsAfterTenMemoNo = ? and a.materiel.ptNo= ?",
						new Object[] { type, CommonUtils.getCompany().getId(),
								tenNo, ptNo });
	}

	/**
	 * 存放企业内部归并－－内部归并资料
	 * 
	 * @param type
	 *            物料类别
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public InnerMergeData findInnerMergeSeqNumByTenNoPtNo(String type,
			String ptNo) {
		List list = this
				.find(" select a from InnerMergeData as a "
						+ " left join fetch a.materiel"
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.imrType=? and a.company.id=?  and a.materiel.ptNo= ?",
						new Object[] { type, CommonUtils.getCompany().getId(),
								ptNo });
		if (list != null && list.size() > 0) {
			return (InnerMergeData) list.get(0);
		}
		return null;
	}

	/**
	 * 查找来自物料主档的报关单
	 * 
	 * @return
	 */
	public List findCustomsFromMateriel() {
		return this.find("select a from CustomsFromMateriel a");
	}

	/**
	 * 保存来自物料主档的报关单
	 * 
	 * @param obj
	 *            来自物料主档的报关单
	 */
	public void saveCustomsFromMateriel(CustomsFromMateriel obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 统计电子帐册料件成品序号
	 * 
	 * @param headModityTimes
	 *            表头修改次数
	 * @param seqNum
	 *            料件(成品)序号
	 * @param tableName
	 *            表名
	 * @return
	 */
	public Integer findEmsImgExgCount(Integer headModityTimes, Integer seqNum,
			String tableName) {
		List list = this
				.find("select count(a.seqNum) from "
						+ tableName
						+ " a where a.company.id = ? and a.seqNum = ? and a.emsHeadH2k.modifyTimes<=?",
						new Object[] { CommonUtils.getCompany().getId(),
								seqNum, headModityTimes });
		if (list != null && list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return null;
	}

	/**
	 * 统计电子帐册成品BOM的料件序号
	 * 
	 * @param headModityTimes
	 *            表头修改次数
	 * @param seqNum
	 *            料件序号
	 * @param version
	 *            版本号
	 * @param cpSeqNum
	 *            成品序号
	 * @return
	 */
	public Integer findEmsBomCount(Integer headModityTimes, Integer seqNum,
			Integer version, Integer cpSeqNum) {
		List list = this
				.find("select count(a.seqNum) from EmsHeadH2kBom a where a.company.id = ? and a.seqNum = ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.modifyTimes<=? "
						+ " and a.emsHeadH2kVersion.version = ? and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								seqNum, headModityTimes, version, cpSeqNum });
		if (list != null && list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return null;
	}

	/**
	 * 返回电子帐册成品BOM
	 * 
	 * @param exg
	 *            电子帐册成品
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsHeadH2kBomByCpVersion(EmsHeadH2kExg exg, Integer version) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.id=? and a.company.id= ? and "
						+ "  a.emsHeadH2kVersion.version = ? order by a.seqNum",
						new Object[] { exg.getId(),
								CommonUtils.getCompany().getId(), version });
	}

	/**
	 * 返回电子帐册成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param seqNum
	 *            成品序号
	 * @return
	 */
	public List findEmsHeadH2kExgBySeqNum(EmsHeadH2k emsHeadH2k, Integer seqNum) {
		return this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.id=? and a.company.id= ? and a.seqNum = ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(), seqNum });
	}

	/**
	 * 查询电子帐册成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param seqNumList
	 *            成品序号 列表
	 * @return
	 */
	public List findEmsHeadH2kExgBySeqNum(EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList) {

		List<EmsHeadH2kBom> list = null;
		StringBuilder hql = new StringBuilder(" select a from EmsHeadH2kExg a "
				+ " left outer join fetch a.emsHeadH2k "
				+ "where a.emsHeadH2k.id=? and a.company.id= ?");
		if (seqNumList != null && seqNumList.size() > 0) {
			hql.append(" and a.seqNum in (" + seqNumList.get(0));
			for (int i = 1; i < seqNumList.size(); i++) {
				hql.append("," + seqNumList.get(i));
			}
			hql.append(")");
		}

		list = find(hql.toString(), new Object[] { emsHeadH2k.getId(),
				CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 返回电子帐册料件
	 * 
	 * @param emsHeadH2K
	 *            电子帐册表头
	 * @param seqNum
	 *            料件序号
	 * @return
	 */
	public List findEmsHeadH2kImgBySeqNum(EmsHeadH2k emsHeadH2k, Integer seqNum) {
		return this
				.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id=? and a.company.id= ? and a.seqNum = ? order by a.seqNum",
						new Object[] { emsHeadH2k.getId(),
								CommonUtils.getCompany().getId(), seqNum });
	}

	/**
	 * 查询电子帐册料件
	 * 
	 * @param emsHeadH2K
	 *            电子帐册表头
	 * @param seqNum
	 *            料件序号 列表
	 * @return
	 */
	public List findEmsHeadH2kImgBySeqNum(EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList) {
		List<EmsHeadH2kImg> list = null;
		StringBuilder hql = new StringBuilder("select a from EmsHeadH2kImg a "
				+ " left outer join fetch a.emsHeadH2k "
				+ "where a.emsHeadH2k.id=? and a.company.id= ?");
		if (seqNumList != null && seqNumList.size() > 0) {
			hql.append(" and a.seqNum in (" + seqNumList.get(0));
			for (int i = 1; i < seqNumList.size(); i++) {
				hql.append("," + seqNumList.get(i));
			}
			hql.append(")");
		}

		list = find(hql.toString(), new Object[] { emsHeadH2k.getId(),
				CommonUtils.getCompany().getId() });

		return list;

	}

	/**
	 * 返回报关常用工厂物料资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public Materiel findMtBy(String ptNo) {
		List ls = this
				.find("select a from Materiel as a "
						+ " left join fetch a.complex"
						+ " left join fetch a.scmCoc"
						+ " left join fetch a.calUnit where a.company.id = ? and a.ptNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), ptNo });
		if (ls != null && ls.size() > 0) {
			return (Materiel) ls.get(0);
		}
		return null;
	}

	/**
	 * 保存报关常用工厂物料资料
	 * 
	 * @param obj
	 *            报关常用工厂物料资料
	 */
	public void saveMateriel(Materiel obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 查找企业内部归并－－内部归并资料
	 * 
	 * @param type
	 *            物料类别
	 * @param seqNum
	 *            十位备案序号
	 * @return
	 */
	public List findInnerMergeData(String type, Integer seqNum) {
		return this
				.find("select a  from InnerMergeData as a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit where a.company.id = ? and a.imrType = ? and a.hsAfterTenMemoNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), type,
								seqNum });
	}

	/**
	 * 更新企业内部归并－－内部归并资料
	 * 
	 * @param type
	 *            物料类别
	 * @param seqNum
	 *            十位备案序号
	 * @param complex
	 *            商品编码
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格
	 * @param unit
	 *            归并后备案单位
	 */
	public void updateInnerMergeData(String type, Integer seqNum,
			Complex complex, String name, String spec, Unit unit) {
		String hsql = "update InnerMergeData set hsAfterComplex=?,hsAfterMaterielTenName=?,"
				+ " hsAfterMaterielTenSpec=?,hsAfterMemoUnit=? "
				+ " where company.id = ? and imrType = ? and hsAfterTenMemoNo = ?";
		this.batchUpdateOrDelete(hsql, new Object[] { complex, name, spec,
				unit, CommonUtils.getCompany().getId(), type, seqNum });
	}

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param complex
	 *            商品编码
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @param unit
	 *            归并后备案单位
	 * @param hsAfterTenMemoNo
	 *            十位备案序号
	 * @param materielType
	 *            物料类别
	 */
	public void updateReverseMergeTenDataByHsAfterTenMemoNo(Complex complex,
			String name, String spec, Unit unit, Integer hsAfterTenMemoNo,
			String materielType) {
		String hsql = "update ReverseMergeTenData a set a.hsAfterComplex=?,a.hsAfterMaterielTenName=?, "
				+ " a.hsAfterMaterielTenSpec=?,a.hsAfterMemoUnit=? "
				+ " where a.company.id=? and a.reverseMergeFourData.id in (select b.id from ReverseMergeFourData b where b.imrType=?) and a.hsAfterTenMemoNo=? ";
		this.batchUpdateOrDelete(hsql, new Object[] { complex, name, spec,
				unit, CommonUtils.getCompany().getId(), materielType,
				hsAfterTenMemoNo });

	}

	/**
	 * 返回成品BOM表
	 * 
	 * @param emsEdiMergerHead
	 *            电子帐册归并关系表头
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEmsEdiMergerBomByPtNo(EmsEdiMergerHead emsEdiMergerHead,
			String ptNo) {
		return this
				.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.company.id= ? "
						+ " and a.ptNo = ? ",
						new Object[] { emsEdiMergerHead.getId(),
								CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 电子帐册归并关系归并后料件
	 * 
	 * @param newPtNo
	 *            料件序号
	 * @return
	 */
	public EmsEdiMergerImgAfter modityInnergerSeqNumImgId(Integer newPtNo) {
		List list = null;
		list = this.find(
				"select a from EmsEdiMergerImgAfter a where  a.company.id= ? "
						+ " and a.seqNum = ? ", new Object[] {
						CommonUtils.getCompany().getId(), newPtNo });
		if (list.size() > 0)
			return (EmsEdiMergerImgAfter) list.get(0);
		else
			return null;
	}

	/**
	 * 查找电子帐册归并关系归并后成品
	 * 
	 * @param newPtNo
	 *            料件序号
	 * @return
	 */
	public EmsEdiMergerExgAfter modityInnergerSeqNumExgId(Integer newPtNo) {
		List list = null;
		list = this.find(
				"select a from EmsEdiMergerExgAfter a where  a.company.id= ? "
						+ " and a.seqNum = ? ", new Object[] {
						CommonUtils.getCompany().getId(), newPtNo });
		if (list.size() > 0)
			return (EmsEdiMergerExgAfter) list.get(0);
		else
			return null;
	}

	/**
	 * 根据类型与料号查询内部归并的物料
	 * 
	 * @param request
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Materiel findInnerMergeDataByPtNo(String type, String ptNo) {

		List result = this
				.find(" select a.materiel from InnerMergeData as a "
						+ " where a.materiel.ptNo = ? and a.imrType=? and a.company.id=?",
						new Object[] { ptNo, type,
								CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (Materiel) result.get(0);
		else
			return null;

	}

	/**
	 * 查找存放企业内部归并－－内部归并资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public InnerMergeData findInnerMergeDataByMaterial(String ptNo) {
		List result = this.find(
				" select a from InnerMergeData as a left join fetch a.materiel "
						+ " where a.materiel.ptNo = ? and a.company.id=?",
				new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (InnerMergeData) result.get(0);
		else
			return null;
	}

	/**
	 * 查找成品BOM表
	 * 
	 * @param head
	 *            电子帐册归并关系表头
	 * @param beginSeqNum
	 *            开始序号
	 * @param endSeqNum
	 *            结束序号
	 * @param Bomversion
	 *            版本号
	 * @return
	 */
	public List findBomExport(EmsEdiMergerHead head, String beginSeqNum,
			String endSeqNum, String Bomversion) {
		List list = new Vector();
		List<Object> parameters = new ArrayList<Object>();
		String sql = "";
		sql = "select distinct a from EmsEdiMergerExgBom a where a.company.id=? and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id = ? "
				+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum >=? and  a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.seqNum<=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(head.getId());
		parameters.add(Integer.valueOf(beginSeqNum));
		parameters.add(Integer.valueOf(endSeqNum));
		if (Bomversion != null) {
			sql += "  and a.emsEdiMergerVersion.version=?  ";
			parameters.add(Integer.valueOf(Bomversion));
		}
		list = this.find(sql, parameters.toArray());
		return list;
	}

	/**
	 * 查找归并关系归并前料件表 料件序号
	 * 
	 * @param head
	 *            电子帐册归并关系表头
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public Integer findBomExportSeqnum(EmsEdiMergerHead head, String ptNo) {
		List list = this
				.find("select a.seqNum from EmsEdiMergerImgBefore a where a.company.id=? and a.emsEdiMergerImgAfter.emsEdiMergerHead.id = ? and a.ptNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), ptNo });
		if (list != null && list.size() > 0) {
			return (Integer) list.get(0);
		} else
			return null;
	}

	/**
	 * 查找最大版本
	 * 
	 * @author zyy date : Mar 29, 2012 3:50:55 PM
	 * @param head
	 * @param exgModifyMark
	 * @return
	 */
	public List findMaxVersionFromEmsHeadH2kVersion(EmsHeadH2k head,
			String exgModifyMark) {
		List<Object> parameters = new ArrayList<Object>();

		String hql = " select distinct a.seqNum,max(b.version) as version from "
				+ " EmsHeadH2kVersion b left join b.emsHeadH2kExg a "
				+ " where b.company.id=? and a.emsHeadH2k.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(head.getId());
		if (exgModifyMark != null && !"".equals(exgModifyMark)) {
			hql += " and a.modifyMark=?";
			parameters.add(exgModifyMark);
		}
		hql += " group by a.seqNum order by a.seqNum";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 电子帐册成品BOM
	 * 
	 * @param head
	 *            电子帐册表头
	 * @param beginSeqNum
	 *            开始序号
	 * @param endSeqNum
	 *            结束序号
	 * @return
	 */
	public List findEmsHeadH2kBomExport(EmsHeadH2k head, String beginSeqNum,
			String endSeqNum, String exgModifyMark, String bomModifyMark) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from EmsHeadH2kBom a where a.company.id=?"
				+ "  and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(head.getId());
		if (beginSeqNum != null && !"".equals(beginSeqNum)) {
			hql += " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum >=? ";
			parameters.add(Integer.valueOf(beginSeqNum));
		}
		if (endSeqNum != null && !"".equals(endSeqNum)) {
			hql += " and  a.emsHeadH2kVersion.emsHeadH2kExg.seqNum<=? ";
			parameters.add(Integer.valueOf(endSeqNum));
		}
		if (exgModifyMark != null && !"".equals(exgModifyMark)) {
			hql += " and a.emsHeadH2kVersion.emsHeadH2kExg.modifyMark=?";
			parameters.add(exgModifyMark);
		}
		if (bomModifyMark != null && !"".equals(bomModifyMark)) {
			hql += " and a.modifyMark=?";
			parameters.add(bomModifyMark);
		}
		// if (jRadioButtonBigBOMState) {
		// hql += " and a.emsHeadH2kVersion.version in (select
		// max(b.version),b.emsHeadH2kExg.seqNum" +
		// " from EmsHeadH2kVersion b where b.company.id=? and
		// b.emsHeadH2kExg.emsHeadH2k.id = ? order by b.emsHeadH2kExg.seqNum)";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(head.getId());
		// }
		hql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找商品禁用信息
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public List findCommdityForbid(String type) {
		List list = this
				.find("select distinct a from CommdityForbid a   where a.company.id= ?  and a.type=? ",
						new Object[] { CommonUtils.getCompany().getId(), type });
		return list;

	}

	/**
	 * 检查商品是否在商检中
	 * 
	 * @param code
	 * @return
	 */
	public CheckupComplex findCheckupComplex(String code, int impExpFlag) {
		List list = this
				.find("select  a from CheckupComplex a   "
						+ " where a.company.id= ?  and a.complex.code=? and a.impExpFlag=? ",
						new Object[] { CommonUtils.getCompany().getId(), code,
								impExpFlag });
		if (list != null && list.size() > 0) {
			return (CheckupComplex) list.get(0);
		}
		return null;
	}

	/**
	 * 查找商品禁用信息
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public List findCommdityForbidByExg(String type, Integer seqNum) {
		List list = this
				.find("select distinct a from CommdityForbid a   where a.company.id= ?  and a.type=? and a.seqNum=?",
						new Object[] { CommonUtils.getCompany().getId(), type,
								String.valueOf(seqNum) });
		return list;

	}

	/**
	 * 查找商品禁用信息
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public List findCommdityForbidBySeqNum(String type, Integer seqNum,
			Integer version) {
		List list = this
				.find("select distinct a from CommdityForbid a   where a.company.id= ?  and a.type=? and a.seqNum=? and a.version =?",
						new Object[] { CommonUtils.getCompany().getId(), type,
								String.valueOf(seqNum), String.valueOf(version) });

		return list;
	}

	/**
	 * 查找所有被报关单调用的报关常用物料
	 * 
	 * @param isMaterial
	 *            判断是否料件类型，true为料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billListState
	 *            清单状态
	 * @return List 是materiel型，报关常用物料
	 */
	public List findAllMaterial(boolean isMaterial, Date beginDate,
			Date endDate, String billListState) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select distinct a.materiel from AtcMergeBeforeComInfo a "
				+ " where a.afterComInfo.billList.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		hql += " and a.afterComInfo.billList.materielProductFlag=?";
		if (isMaterial) {
			parameters.add(Integer.valueOf(MaterielType.MATERIEL));
		} else {
			parameters.add(Integer.valueOf(MaterielType.FINISHED_PRODUCT));
		}
		if (beginDate != null) {
			hql += " and a.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (billListState != null) {

			hql += " and a.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找报关清单商品的申报数量
	 * 
	 * @param impExpFlag
	 *            物料标识
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            监管方式编码
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billListState
	 *            清单状态
	 * @param ptNo
	 *            料号
	 * @return List 存放了materiel的料号和对应的申报数量
	 */
	public Double findMaterialTotalAmount(Integer impExpFlag,
			Integer impExpType, String[] tradeCodes, Date beginDate,
			Date endDate, String billListState, String ptNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct sum(a.declaredAmount) "
				+ " from  AtcMergeBeforeComInfo as a "
				+ " where a.afterComInfo.billList.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpFlag != null) {
			hsql += " and a.afterComInfo.billList.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.afterComInfo.billList.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (billListState != null) {

			hsql += " and a.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		if (ptNo != null) {
			hsql += " and a.materiel.ptNo=? ";
			parameters.add(ptNo);
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.afterComInfo.billList.tradeMode.code=? ";
				} else {
					hsql += " a.afterComInfo.billList.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		List list = find(hsql, parameters.toArray());
		if (list != null && list.size() > 0)
			return (Double) list.get(0);
		else
			return 0.0;
	}

	/**
	 * 查找归并前商品信息
	 * 
	 * @param materiel
	 *            物料类别
	 * @param billListState
	 *            清单状态
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public Integer findEmsComInfo(String materiel, String billListState,
			Date beginDate, Date endDate) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.afterComInfo.emsSerialNo as emsSerialNo from  AtcMergeBeforeComInfo as a "
				+ " where a.afterComInfo.billList.company.id=? "
				+ "and a.materiel.ptNo = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(materiel);
		if (beginDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (billListState != null) {

			hsql += " and a.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Integer) list.get(0);
		} else {

			return null;
		}

	}

	/**
	 * 查找归并前商品信息
	 * 
	 * @param materiel
	 *            物料类别
	 * @param billListState
	 *            清单状态
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public String findScmCoc(String materiel, String billListState,
			Date beginDate, Date endDate) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.afterComInfo.billList.scmCoc.name from AtcMergeBeforeComInfo as a "
				+ " where a.afterComInfo.billList.company.id=? "
				+ "and a.materiel.ptNo = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(materiel);
		if (beginDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (billListState != null) {

			hsql += " and a.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (String) list.get(0);
		} else {

			return null;
		}

	}

	/**
	 * 查找成品BOM表
	 * 
	 * @param ptNo
	 *            料号
	 * @param billListState
	 *            清单状态
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findEmsEdiMergerExgBom(String ptNo, String billListState,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo as ptNo,a.emsEdiMergerVersion.version as version ,"
				+ " a.unitWaste as unitWaste , a.waste as waste,"
				+ " (select sum(b.declaredAmount) from AtcMergeBeforeComInfo b "
				+ " where b.afterComInfo.billList.impExpType in (?,?,?,?,?)   ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
		parameters.add(ImpExpType.EXPORT_MATERIAL_REBACK);
		if (beginDate != null) {
			hsql += " and b.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (billListState != null) {

			hsql += " and b.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		hsql += " and   b.materiel.ptNo= a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo  and  b.version = a.emsEdiMergerVersion.version  ) as aa,"
				+ " (select sum(c.declaredAmount) from AtcMergeBeforeComInfo c "
				+ " where c.afterComInfo.billList.impExpType in (?,?,?)  ";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(ImpExpType.IMPORT_EXG_BACK);
		parameters.add(ImpExpType.IMPORT_REPAIR_MATERIAL);
		if (beginDate != null) {
			hsql += " and c.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and c.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (billListState != null) {

			hsql += " and c.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		hsql += " and  c.materiel.ptNo = a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo and  c.version = a.emsEdiMergerVersion.version ) as cc "
				+ " from EmsEdiMergerExgBom a where a.company.id=? and a.ptNo=?  and a.modifyMark <>2 ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ptNo);
		// parameters.add(Integer.valueOf(SendState.SEND));
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查找报关清单归并前商品,料号及申报数量
	 * 
	 * @param version
	 *            版本
	 * @param ptNo
	 *            料号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billListState
	 *            清单状态
	 * @return
	 */
	public List statExpAmount(Integer version, String ptNo, Date beginDate,
			Date endDate, String billListState) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.materiel.ptNo,sum(a.declaredAmount) "
				+ " from  AtcMergeBeforeComInfo as a "
				+ " where a.company.id=? "
				+ "and a.materiel.ptNo = ? and a.version = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ptNo);
		parameters.add(version);
		if (beginDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate<=?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (billListState != null) {

			hsql += " and a.afterComInfo.billList.listState = ?";
			parameters.add(Integer.valueOf(billListState));
		}
		hsql += " group by a.materiel.ptNo ";
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查找归并前成品
	 * 
	 * @param type
	 *            物料类型
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public Unit getUnit(String type, String ptNo) {
		List<Object> parameters = new ArrayList<Object>();
		Unit unit = null;
		// 0: 成品 ２：料件
		if ("0".equals(type)) {
			String hsql = " select a " + " from  EmsEdiMergerExgBefore as a "
					+ " where a.company.id=? " + "and a.ptNo = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(ptNo);
			List list = this.find(hsql, parameters.toArray());
			if (list.size() > 0 && list.get(0) != null) {
				unit = ((EmsEdiMergerExgBefore) list.get(0)).getUnit();
			}
		}
		if ("2".equals(type)) {
			String hsql = " select a " + " from  EmsEdiMergerImgBefore as a "
					+ " where a.company.id=? " + "and a.ptNo = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(ptNo);
			List list = this.find(hsql, parameters.toArray());
			if (list.size() > 0 && list.get(0) != null) {
				unit = ((EmsEdiMergerImgBefore) list.get(0)).getUnit();
			}
		}
		return unit;
	}

	/**
	 * 查找报关单明细资料
	 * 
	 * @param before
	 *            禁用天数
	 * @param company
	 *            公司
	 * @return
	 */
	public List findCustomsDeclarationCommInfo(int before, Company company) {
		int dist = -540 + before;
		Date distDate = CommonUtils.getDateAfterNDay(new Date(), dist);
		String hsql = " select distinct  a.commSerialNo ,a.version from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType  in (?,?) "
				+ "  and  a.baseCustomsDeclaration.effective= ? "
				+ "  and  a.baseCustomsDeclaration.declarationDate is not null  "
				+ "  and  a.baseCustomsDeclaration.declarationDate>=? "
				+ "  and  a.company.id= ? ";
		return this.find(hsql,
				new Object[] { ImpExpType.DIRECT_EXPORT,
						ImpExpType.REWORK_EXPORT, new Boolean(true), distDate,
						company.getId() });
	}

	// public List findEmsHeadH2kVersion(EmsHeadH2k head) {
	// if (head == null) {
	// return new ArrayList();
	// }
	// String hsql = " select distinct b.seqNum,a.version , b from
	// EmsHeadH2kVersion a "
	// + " left join a.emsHeadH2kExg b "
	// + "where a.company.id=? and b.emsHeadH2k.id=? ";
	// return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
	// head.getId() });
	// }

	/**
	 * 查找商品禁用信息
	 * 
	 * @param company
	 *            公司
	 * @return
	 */
	public List findEmsEdiForbid(Company company) {
		String hsql = " select distinct a.seqNum,a.version  from  CommdityForbid  a "
				+ "where a.company.id=? and a.type =?  ";
		return this.find(hsql, new Object[] { company.getId(),
				MaterielType.FINISHED_PRODUCT });
	}

	/**
	 * 电子帐册成品BOM
	 * 
	 * @param head
	 *            帐册表头
	 * @param before
	 *            禁用天数
	 * @param company
	 *            公司
	 * @return
	 */
	public List findEmsHeadH2kBom(EmsHeadH2k head, int before, Company company) {
		if (head == null) {
			return new ArrayList();
		}
		int dist = -540 + before;
		Date distDate = CommonUtils.getDateAfterNDay(new Date(), dist);
		String hsql = " select distinct c.seqNum, b.version ,b from EmsHeadH2kBom a "
				+ "  left join  a.emsHeadH2kVersion b"
				+ "  left  join  b.emsHeadH2kExg c "
				+ " where  a.company.id=? and c.emsHeadH2k.id=? "
				+ " and a.changeDate<?  and  a.changeDate  is not null ";
		return this.find(hsql, new Object[] { company.getId(), head.getId(),
				distDate });
	}

	/**
	 * 查找报关单物料信息
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public List findCustomsDeclarationCountByImpexpType(int type) {
		String hsql = "  select  a.commSerialNo,SUM(a.commAmount),a.version from CustomsDeclarationCommInfo a  "
				+ " where a.company.id =?  and  a.baseCustomsDeclaration.impExpType=?  group by  a.commSerialNo,a.version ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(type);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找报关单物料信息
	 * 
	 * @param id
	 *            报关单物料信息ID
	 * @param type
	 *            物料类别
	 * @return
	 */
	public List findCustomsDeclarationCountByImpexpType(String id, int type) {
		String hsql = "  select  a.commSerialNo ,SUM(a.commAmount),a.version from CustomsDeclarationCommInfo a  "
				+ " where a.company.id =?   and  a.baseCustomsDeclaration.impExpType=?  and a.id not in ( ? ) "
				+ " group by  a.commSerialNo,a.version ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(type);
		para.add(id);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找报关单物料信息
	 * 
	 * @param id
	 *            报关单物料信息ID
	 * @return
	 */
	public List findCustomsDeclarationCountById(String id) {
		String hsql = "  select  a.commSerialNo ,SUM(a.commAmount),a.version from CustomsDeclarationCommInfo a  "
				+ " where a.company.id =?   and  a.baseCustomsDeclaration.id=?  group by  a.commSerialNo,a.version";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(id);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 返回限制类商品
	 * 
	 * @param ImpExpType
	 *            进出口类型
	 * @param seqNum
	 *            帐册序号
	 * @return
	 */
	public RestirictCommodity findRerictCommodity(Boolean ImpExpType,
			String seqNum) {
		List list = null;
		if (ImpExpType) {
			list = this
					.find("select a from RestirictCommodity a where a.company.id=? and a.seqNum=? and a.types=?",
							new Object[] { CommonUtils.getCompany().getId(),
									seqNum, MaterielType.MATERIEL });
		} else {
			list = find(
					"select a from RestirictCommodity a where a.company.id=? and a.seqNum=? and a.types=?  ",
					new Object[] { CommonUtils.getCompany().getId(), seqNum,
							MaterielType.FINISHED_PRODUCT });

		}
		if (!list.isEmpty())
			return (RestirictCommodity) list.get(0);
		else
			return null;
	}

	/**
	 * 返回限制类商品
	 * 
	 * @param ImpExpType
	 *            进出口类型
	 * @param seqNum
	 *            帐册序号
	 * @return
	 */
	public List<RestirictCommodity> findRerictCommoditys(Boolean ImpExpType,
			List<Integer> seqNums) {
		String hql = "select a from RestirictCommodity a where a.company.id=? and a.types=? ";

		List<RestirictCommodity> rs = new ArrayList<RestirictCommodity>();
		List<Integer> tmpLs = seqNums;
		String ql;
		for (int i = 0; i < tmpLs.size(); i += 1000) {
			int max = i + 1000 > tmpLs.size() ? tmpLs.size() : i + 1000;
			ql = hql + " and a.seqNum in ('"
					+ StringUtils.join(tmpLs.subList(i, max).toArray(), "','")
					+ "') ";

			if (ImpExpType) {
				rs.addAll(this.find(ql.toString(),
						new Object[] { CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL }));
			} else {
				rs.addAll(this.find(ql.toString(), new Object[] {
						CommonUtils.getCompany().getId(),
						MaterielType.FINISHED_PRODUCT }));
			}
		}
		return rs;
	}

	/**
	 * 查找归并关系归并前料件表
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEdiBomptNoToBomseqNum(String[] ptNo, String versionId) {
		String hsql = "  select  distinct a.ptNo,a.name,a.spec,b.unit.name,b.unitWaste,b.waste,"
				+ " a.emsEdiMergerImgAfter.seqNum,a.emsEdiMergerImgAfter.name,a.emsEdiMergerImgAfter.spec,"
				+ " a.seqNum"
				+ " from EmsEdiMergerImgBefore a ,EmsEdiMergerExgBom b  "
				+ " where  a.ptNo=b.ptNo and a.company.id =? and b.emsEdiMergerVersion.id=? order by a.emsEdiMergerImgAfter.seqNum";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		para.add(versionId);
		// if (ptNo != null && ptNo.length > 0) {
		// hsql += " and ( ";
		// for (int i = 0; i < ptNo.length; i++) {
		// if (i == ptNo.length - 1) {
		// hsql += " a.ptNo=? ";
		// } else {
		// hsql += " a.ptNo=? or ";
		// }
		// para.add(ptNo[i]);
		// }
		// hsql += " ) ";
		// }
		// hsql += " order by a.seqNum";
		// System.out.print("=========hsql===="+versionId+"==="+hsql);
		List list = find(hsql, para.toArray());
		return list;
	}

	/**
	 * 返回归并关系版本
	 * 
	 * @return
	 */
	public List findAllVersion() {
		String hsql = "  select  distinct a.version from EmsEdiMergerVersion a  "
				+ " where a.company.id =?  ";
		List para = new ArrayList();
		para.add(CommonUtils.getCompany().getId());
		List list = find(hsql, para.toArray());
		return list;
	}

	/**
	 * 电子帐册单耗检查----根据成品序号及版本号分组统计单耗
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public List findEmsHeadH2kBomUnitWearTotal(EmsHeadH2k emsHeadH2k) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.emsHeadH2kVersion.emsHeadH2kExg.seqNum ,a.emsHeadH2kVersion.version ,sum(a.unitWear) from EmsHeadH2kBom a "
				+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? "
				+ " and a.company.id=? "
				+ " and a.unit.code='035' "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.unit.code='035' "
				+ " group by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version "
				+ " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据查询类型查询电子帐册成品是否禁用
	 * 
	 * @param type
	 * @param seqNum
	 * @return
	 */
	public List checkExgCommdityForbidBySeqNum(String type, Integer seqNum,
			Integer version) {
		List<Object> parameters = new ArrayList<Object>();
		List<String> keyList = new ArrayList<String>();
		String hsql = " select a.id from CommdityForbid  a "
				+ " where a.type=?  and a.seqNum=? and a.version=? and a.company.id=?  ";
		parameters.add(type);
		parameters.add(String.valueOf(seqNum));
		parameters.add(String.valueOf(version));
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据查询类型查询电子帐册料件是否禁用
	 * 
	 * @param type
	 * @param seqNum
	 * @return
	 */
	public List checkImgCommdityForbidBySeqNum(String type, Integer seqNum) {
		List<Object> parameters = new ArrayList<Object>();
		List<String> keyList = new ArrayList<String>();
		String hsql = " select a.id  from CommdityForbid  a "
				+ " where a.type=?  and a.seqNum=? and a.company.id=?  ";
		parameters.add(type);
		parameters.add(String.valueOf(seqNum));
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示最大版本（成品筛选--电子帐册）
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册历版本
	 * @return
	 */
	public Map findMaxEmsHeadH2kVersion() {
		List list = this
				.find("select a.emsHeadH2kExg.seqNum,max(a.version) from EmsHeadH2kVersion a where  a.company.id= ? group by a.emsHeadH2kExg.seqNum",
						new Object[] { CommonUtils.getCompany().getId() });
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < list.size(); i++) {

			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null) {
				map.put(Integer.parseInt(objs[0].toString()),
						objs[1] == null ? 0 : Integer.parseInt(objs[1]
								.toString()));
			}
		}
		return map;
	}

	/**
	 * 查找电子帐册成品BOM
	 * 
	 * @return
	 */
	public List findAllEmsHeadH2kSqeNumAndVersion() {
		List list = this
				.find("select a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum from EmsHeadH2kBom a where  a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
		List newList = new ArrayList<String>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {

				Object[] objs = (Object[]) list.get(i);
				// System.out.println("objs[0]="+objs[0]);
				// System.out.println("objs[1]="+objs[1]);
				if (objs[0] != null && objs[1] != null) {
					newList.add(objs[0].toString() + "/" + objs[1].toString()
							+ "/" + objs[2].toString());
				}
			}
		}
		// System.out.println("newList.size()="+newList.size());
		return newList;
	}

	// public void
	// updateEmsEdiMergerImgBeforeComplexEqualsImgAfter(EmsEdiMergerImgAfter
	// imgafter){
	// String hsql="update EmsEdiMergerImgBefore a set a.complex="+
	// "(select b.complex from EmsEdiMergerImgAfter b where
	// b.id=a.emsEdiMergerImgAfter.id),"+
	// " a.unit=(select b.unit from EmsEdiMergerImgAfter b where
	// b.id=a.emsEdiMergerImgAfter.id),"+
	// " a.modifyMark=(case when a.modifyMark='0' then '1' else a.modifyMark
	// end),"+
	// " a.sendState=(case when a.modifyMark='0' then '1' else a.sendState
	// end)"+
	// " where a.emsEdiMergerImgAfter.id=?";
	// this.batchUpdateOrDelete(hsql,new Object[]{imgafter.getId()});
	// }
	/**
	 * 更新归并关系归并前料件表
	 * 
	 * @param imgafter
	 *            电子帐册归并关系归并后料件
	 */
	public void updateEmsEdiMergerImgBeforeComplexEqualsImgAfter(
			EmsEdiMergerImgAfter imgafter) {
		String hsql = "update EmsEdiMergerImgBefore a  set a.complex=?,a.unit=?,"
				+ " a.modifyMark=(case when a.modifyMark='0' then '1' else a.modifyMark end),"
				+ " a.sendState=(case when a.modifyMark='0' then 1 else a.sendState end)"
				+ " where a.emsEdiMergerImgAfter.id=?";
		this.batchUpdateOrDelete(hsql, new Object[] { imgafter.getComplex(),
				imgafter.getUnit(), imgafter.getId() });
	}

	/**
	 * 更新归并关系归并前成品表
	 * 
	 * @param imgafter
	 *            电子帐册归并关系归并后料件
	 */
	public void updateEmsEdiMergerExgBeforeComplexEqualsExgAfter(
			EmsEdiMergerExgAfter exgafter) {
		String hsql = "update EmsEdiMergerExgBefore a  set a.complex=?,a.unit=?,"
				+ " a.modifyMark=(case when a.modifyMark='0' then '1' else a.modifyMark end),"
				+ " a.sendState=(case when a.modifyMark='0' then 1 else a.sendState end)"
				+ " where a.emsEdiMergerExgAfter.id=?";
		this.batchUpdateOrDelete(hsql, new Object[] { exgafter.getComplex(),
				exgafter.getUnit(), exgafter.getId() });
	}

	// public void updateEmsEdiMergerBomByPtNo(EmsEdiMergerHead
	// emsEdiMergerHead,
	// String ptNo) {
	// String
	// return this
	// .find(
	// "select a from EmsEdiMergerExgBom a where
	// a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=?
	// and a.company.id= ? "
	// + " and a.ptNo = ? ", new Object[] {
	// emsEdiMergerHead.getId(),
	// CommonUtils.getCompany().getId(), ptNo });
	// }

	// public void
	// updateEmsEdiMergerExgBomComplexEqualsImgAfter(EmsEdiMergerImgAfter
	// imgafter){
	// String hsql="update EmsEdiMergerExgBom a set a.complex="+
	// " (select max(b.emsEdiMergerImgAfter.complex) from EmsEdiMergerImgBefore
	// b where"+
	// "
	// b.emsEdiMergerImgAfter.emsEdiMergerHead=a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead
	// and a.ptNo=b.ptNo),"+
	// " a.unit=(select max(b.emsEdiMergerImgAfter.unit) from
	// EmsEdiMergerImgBefore b where"+
	// "
	// b.emsEdiMergerImgAfter.emsEdiMergerHead=a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead
	// and a.ptNo=b.ptNo)"+
	// " where a.ptNo in (select distinct b.ptNo from EmsEdiMergerImgBefore b
	// where b.emsEdiMergerImgAfter.id=?)"
	// +
	// " and a.id in (select distinct b.id from EmsEdiMergerExgBom b,
	// EmsEdiMergerImgAfter c where
	// b.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead=c.emsEdiMergerHead
	// and c.id=?)";
	// this.batchUpdateOrDelete(hsql,new
	// Object[]{imgafter.getId(),imgafter.getId()});
	// }
	/**
	 * 更新成品BOM表
	 * 
	 * @param imgafter
	 *            电子帐册归并关系归并后料件
	 */
	public void updateEmsEdiMergerExgBomComplexEqualsImgAfter(
			EmsEdiMergerImgAfter imgafter) {
		String hsql = "update EmsEdiMergerExgBom a  set a.complex=?,a.unit=?"
				+ " where a.ptNo in (select distinct b.ptNo from EmsEdiMergerImgBefore b where b.emsEdiMergerImgAfter.id=?)"
				+ " and a.id in (select distinct b.id from EmsEdiMergerExgBom b, EmsEdiMergerImgAfter c  where "
				+ " b.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead=c.emsEdiMergerHead "
				+ " and c.id=?)";
		this.batchUpdateOrDelete(hsql, new Object[] { imgafter.getComplex(),
				imgafter.getUnit(), imgafter.getId(), imgafter.getId() });
	}

	/**
	 * 按条件查找内部归并
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List getInnerMergeListByDate(Date startDate, Date endDate) {
		String hsql = "";
		List parameters = new ArrayList<Object>();
		if (startDate != null && endDate != null) {
			hsql = "select a from InnerMergeData  a "
					+ "left outer join fetch a.materiel "
					+ "left outer join fetch a.hsBeforeLegalUnit "
					+ "left outer join fetch a.hsAfterComplex  "
					+ "left outer join fetch a.hsBeforeEnterpriseUnit "
					+ "where a.imrType in(?,?) and "
					+ "(a.updateDate>=? and a.updateDate<=? or(a.updateDate=null and (a.importTimer>= ? and a.importTimer<=?))) and a.hsFourNo!=null "
					+ " order by a.imrType";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(startDate);
			parameters.add(endDate);
			parameters.add(startDate);
			parameters.add(endDate);
		} else if (startDate == null && endDate != null) {
			hsql = "select a from InnerMergeData  a "
					+ "left outer join fetch a.materiel "
					+ "left outer join fetch a.hsBeforeLegalUnit "
					+ "left outer join fetch a.hsAfterComplex  "
					+ "left outer join fetch a.hsBeforeEnterpriseUnit "
					+ "where a.imrType in(?,?) and "
					+ "(a.updateDate<=? or(a.updateDate=null and (a.importTimer<=?))) and a.hsFourNo!=null "
					+ " order by a.imrType";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(endDate);
			parameters.add(endDate);
		} else if (startDate != null && endDate == null) {
			hsql = "select a from InnerMergeData  a "
					+ "left outer join fetch a.materiel "
					+ "left outer join fetch a.hsBeforeLegalUnit "
					+ "left outer join fetch a.hsAfterComplex  "
					+ "left outer join fetch a.hsBeforeEnterpriseUnit "
					+ "where a.imrType in(?,?) and "
					+ "(a.updateDate>=? or(a.updateDate=null and a.importTimer>= ? ))and a.hsFourNo!=null "
					+ " order by a.imrType";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(MaterielType.FINISHED_PRODUCT);
			parameters.add(startDate);
			parameters.add(startDate);
		} else if (startDate == null && endDate == null) {
			hsql = "select a from InnerMergeData  a "
					+ "left outer join fetch a.materiel "
					+ "left outer join fetch a.hsBeforeLegalUnit "
					+ "left outer join fetch a.hsAfterComplex  "
					+ "left outer join fetch a.hsBeforeEnterpriseUnit "
					+ "where a.imrType in(?,?) and "
					+ " (a.updateDate!=null or a.importTimer!= null)  and a.hsFourNo!=null  "
					+ " order by a.imrType";
			parameters.add(MaterielType.MATERIEL);
			parameters.add(MaterielType.FINISHED_PRODUCT);
		}
		List list = find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 获取归并关系归并后料件
	 * 
	 * @param head
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List getImgAfterSeqNum(EmsEdiMergerHead head) {
		String hsql = "select  a from EmsEdiMergerImgAfter a where a.emsEdiMergerHead=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(head);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获取归并关系归并前料件
	 * 
	 * @return
	 */
	public List getImgBeforePtNo() {
		String hsql = "select  a from EmsEdiMergerImgBefore a  ";
		return this.find(hsql);
	}

	/**
	 * 获取归并关系归并后成品
	 * 
	 * @param head
	 *            电子帐册归并关系表头
	 * @return
	 */
	public List getExgAfterSeqNum(EmsEdiMergerHead head) {
		String hsql = "select  a from EmsEdiMergerExgAfter a where  a.emsEdiMergerHead=?";
		List<Object> params = new ArrayList<Object>();
		params.add(head);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 获取归并关系归并前成品
	 * 
	 * @return
	 */
	public List getExgBeforePtNo() {
		String hsql = "select  a  from EmsEdiMergerExgBefore a ";
		return this.find(hsql);
	}

	/**
	 * 获取电子账册管理料件
	 * 
	 * @param head
	 *            电子帐册表头
	 * @return
	 */
	public List getEmsImg(EmsHeadH2k emsHead) {
		String hsql = "select  a from EmsHeadH2kImg a where  a.emsHeadH2k=?";
		List<Object> params = new ArrayList<Object>();
		params.add(emsHead);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 获取电子账册管理成品
	 * 
	 * @param head
	 *            电子帐册表头
	 * @return
	 */
	public List getEmsExg(EmsHeadH2k emsHead) {
		String hsql = "select  a from EmsHeadH2kExg a where  a.emsHeadH2k=?";
		List<Object> params = new ArrayList<Object>();
		params.add(emsHead);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 检查序号是否被报关单用到
	 * 
	 * @param seqNum
	 *            报关单商品流水号
	 * @param Type
	 *            进出口类型
	 * @return
	 */
	public List checkDeleteEmsHeadH2kImgExg(Integer seqNum, String Type) {
		String hsql = "select a.id from CustomsDeclarationCommInfo a where a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.impExpType ";
		List<Object> parmaters = new ArrayList();
		parmaters.add(seqNum);
		if (Type.equals(MaterielType.MATERIEL)) {
			hsql += "in(?,?,?,?,?,?,?,?,?,?,?)";
			parmaters.add(ImpExpType.DIRECT_IMPORT);
			parmaters.add(1);
			parmaters.add(3);
			parmaters.add(6);
			parmaters.add(8);
			parmaters.add(9);
			parmaters.add(14);
			parmaters.add(17);
			parmaters.add(18);
			parmaters.add(19);
			parmaters.add(21);
		}
		if (Type.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql += "in(?,?,?,?,?,?,?,?,?)";
			parmaters.add(2);
			parmaters.add(4);
			parmaters.add(5);
			parmaters.add(7);
			parmaters.add(10);
			parmaters.add(25);
			parmaters.add(26);
			parmaters.add(27);
			parmaters.add(28);
		}
		List list = this.find(hsql, parmaters.toArray());
		if (list == null)
			list = new ArrayList();
		return list;
	}

	/**
	 * 检查序号是否被报关单用到
	 * 
	 * @param seqNum
	 *            报关单商品流水号
	 * @param Type
	 *            进出口类型
	 * @return
	 */
	public List checkDeleteEmsHeadH2kImgExg(Integer seqNum, String Type,
			Integer version) {
		String hsql = "select a.id from CustomsDeclarationCommInfo a where a.commSerialNo=? "
				+ " and a.version=? and a.baseCustomsDeclaration.impExpType  ";
		List<Object> parmaters = new ArrayList();
		parmaters.add(seqNum);
		parmaters.add(version.toString());
		if (Type.equals(MaterielType.MATERIEL)) {
			hsql += "in(?,?,?,?,?,?,?,?,?,?,?)";
			parmaters.add(ImpExpType.DIRECT_IMPORT);
			parmaters.add(1);
			parmaters.add(3);
			parmaters.add(6);
			parmaters.add(8);
			parmaters.add(9);
			parmaters.add(14);
			parmaters.add(17);
			parmaters.add(18);
			parmaters.add(19);
			parmaters.add(21);
		}
		if (Type.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql += "in(?,?,?,?,?,?,?,?,?)";
			parmaters.add(2);
			parmaters.add(4);
			parmaters.add(5);
			parmaters.add(7);
			parmaters.add(10);
			parmaters.add(25);
			parmaters.add(26);
			parmaters.add(27);
			parmaters.add(28);
		}
		List list = this.find(hsql, parmaters.toArray());
		if (list == null)
			list = new ArrayList();
		return list;
	}

	/**
	 * 查找电子帐册料件
	 * 
	 * @param emsImgAfter
	 *            电子帐册归并关系归并后料件
	 * @return
	 */
	public List checkToEmsH2kImg(EmsEdiMergerImgAfter emsImgAfter) {
		String hsql = "select a.id from  EmsHeadH2kImg a where a.seqNum=? and a.modifyMark in(?,?) ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(emsImgAfter.getSeqNum());
		paramters.add("0");
		paramters.add("1");
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找电子帐册成品
	 * 
	 * @param emsExgAfter
	 *            电子帐册成品
	 * @return
	 */
	public List checkToEmsH2kExg(EmsEdiMergerExgAfter emsExgAfter) {
		String hsql = "select a.id from  EmsHeadH2kExg a where a.seqNum=? and a.modifyMark in(?,?) ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(emsExgAfter.getSeqNum());
		paramters.add("0");
		paramters.add("1");
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找电子帐册成品BOM资料
	 * 
	 * @param img
	 *            电子帐册料件
	 * @return
	 */
	public List checkToEmsH2kImgBom(EmsHeadH2kImg img) {
		String hsql = "select a from  EmsHeadH2kBom a where a.seqNum=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(img.getSeqNum());
		return this.find(hsql, paramters.toArray());
	}

	// /**
	// * //查找关封下的商品、用于根据备案序号自动查找出编码、名称、规格、单价
	// *
	// * @param seqNum
	// * @return
	// * @author sxk
	// */
	// public List findCustomsEnvelopCommodityInfo(String customsEnvelopBillNo)
	// {
	// return this
	// .find(
	// "select a from CustomsEnvelopCommodityInfo a where
	// a.customsEnvelopBill.customsEnvelopBillNo=? and a.company.id= ? ",
	// new Object[] { customsEnvelopBillNo,
	// CommonUtils.getCompany().getId() });
	//
	// }

	/**
	 * 根据tableName、属性、是否有Company来查找数据,
	 * 
	 * @param tableName
	 *            实体类
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * @param hasCompany
	 *            是否有company栏位
	 * @return
	 */
	public List findObjectByTableNames(String tableName, String[] property,
			Object[] value, boolean hasCompany) {
		List parameters = new ArrayList();
		String hsql = " select a from " + tableName + " a ";
		if (property != null) {
			hsql += " where ";
			for (int i = 0; i < property.length; i++) {
				if (i == 0)
					hsql += " a." + property[i] + "=? ";
				else {
					hsql += " and a." + property[i] + "=? ";
				}
				parameters.add(value[i]);
			}
		}
		if (hasCompany) {
			if (hsql.contains("where"))
				hsql += " and a.company.id=? ";
			else
				hsql += " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}
		if (parameters.isEmpty())
			return this.find(hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 按报关清单编号查询报关清单
	 * 
	 * @param billNo
	 * @return
	 */
	public List findApplyToCustomsBillList(String billNo) {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.company.id=? and a.listNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), billNo });
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的料件
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 */
	public List findEmsHeadH2kByPtNoFromMerger(String ptNo) {
		List list = this
				.find(" select a from EmsHeadH2kImg a"
						+ " where a.seqNum = (select b.seqNum from EmsEdiMergerImgAfter b "
						+ " where b.seqNum = (select c.seqNum from EmsEdiMergerImgBefore c"
						+ " where c.ptNo = ? " + " and c.modifyMark = '0')) "
						+ " and a.modifyMark = '0' " + " and a.company.id=? ",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 */
	public List findEmsHeadH2kExgByPtNoFromMerger(String ptNo) {
		List list = this
				.find(" select a from EmsHeadH2kExg a"
						+ " where a.seqNum = (select b.seqNum from EmsEdiMergerExgAfter b "
						+ " where b.seqNum = (select c.seqNum from EmsEdiMergerExgBefore c"
						+ " where c.ptNo = ? " + " and c.modifyMark = '0')) "
						+ " and a.modifyMark = '0' " + " and a.company.id=? ",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 */
	public List findEmsHeadH2kBomByPtNoFromMerger(String ptNo, Integer version) {
		List list = this
				.find(" select a from EmsHeadH2kBom a"
						+ " where a.company.id=? and a.emsHeadH2kVersion.version=? and (a.modifyMark=1 or a.modifyMark=3) "
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = "
						+ " (select b.seqNum from EmsEdiMergerExgAfter b "
						+ " where b.seqNum = (select seqNum from EmsEdiMergerExgBefore c "
						+ " where c.ptNo = ?) " + " and b.modifyMark = '0') ",
						new Object[] { CommonUtils.getCompany().getId(),
								version, ptNo });
		return list;
	}

	/**
	 * 查询成品序号得下的所有版本bom；
	 * 
	 * @param seqNumList
	 * @return
	 */
	public List<EmsHeadH2kBom> findEmsHeadH2kBomByProduceSeqNum(
			Integer exgSeqNum, EmsHeadH2k emsHeadH2k) {
		List<EmsHeadH2kBom> list = null;
		StringBuilder hql = new StringBuilder(
				" select a from EmsHeadH2kBom a "
						+ " left outer join fetch a.emsHeadH2kVersion "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k "
						+ " where a.company.id = ?"
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ?");
		hql.append(" and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum =? ");

		list = find(
				hql.toString(),
				new Object[] { CommonUtils.getCompany().getId(),
						emsHeadH2k.getId(), exgSeqNum });

		System.out.println("bom数量" + list.size());
		return list;
	}

	/**
	 * 查询成品序号得下的所有版本bom；
	 * 
	 * @param seqNumList
	 * @return
	 */
	public List<EmsHeadH2kBom> findEmsHeadH2kBomByProduceSeqNum(
			List<Integer> seqNumList, EmsHeadH2k emsHeadH2k) {
		List<EmsHeadH2kBom> list = null;
		StringBuilder hql = new StringBuilder(
				" select a from EmsHeadH2kBom a  "
						+ " left outer join fetch a.emsHeadH2kVersion "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k "
						+ " where a.company.id = ?"
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ?");
		if (seqNumList != null && seqNumList.size() > 0) {
			// hql.append(" and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum in ("
			// + seqNumList.get(0));
			// for (int i = 1; i < seqNumList.size(); i++) {
			// hql.append("," + seqNumList.get(i));
			// }
			// hql.append(")");

			List rs = new ArrayList();
			List<Integer> tmpLs = seqNumList;
			String ql;
			for (int i = 0; i < tmpLs.size(); i += 1000) {
				int max = i + 1000 > tmpLs.size() ? tmpLs.size() : i + 1000;
				ql = hql.toString()
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum in ("
						+ StringUtils
								.join(tmpLs.subList(i, max).toArray(), ",")
						+ ") ";
				rs.addAll(this.find(ql, new Object[] {
						CommonUtils.getCompany().getId(), emsHeadH2k.getId() }));
			}
			return rs;
		}

		list = find(hql.toString(), new Object[] {
				CommonUtils.getCompany().getId(), emsHeadH2k.getId() });

		System.out.println("list size = " + list.size());
		return list;
	}

	/**
	 * 查询成品序号指定版本下的所有版本bom；
	 * 
	 * @param versionSet
	 * @param prodeceSeqNum
	 * @param emsHeadH2k
	 * @return
	 */
	public List<EmsHeadH2kBom> findEmsHeadH2kBomByProduceSeqNumAndVersionList(
			Set<Integer> versionSet, Integer prodeceSeqNum,
			EmsHeadH2k emsHeadH2k) {
		List<EmsHeadH2kBom> list = null;
		StringBuilder hql = new StringBuilder(
				" select a from EmsHeadH2kBom a where a.company.id = ?"
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ?"
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = ?");
		if (versionSet != null && versionSet.size() > 0) {
			Iterator<Integer> it = versionSet.iterator();
			hql.append(" and a.emsHeadH2kVersion.version in (" + it.next());
			while (it.hasNext()) {
				hql.append("," + it.next());
			}
			hql.append(")");
		}

		list = find(hql.toString(), new Object[] {
				CommonUtils.getCompany().getId(), emsHeadH2k.getId(),
				prodeceSeqNum });

		return list;
	}

	/**
	 * 根据现有bom list 查询数据库中 有的 版本bom；
	 * 
	 * @param list
	 * @return
	 */
	public List<EmsHeadH2kBom> findEmsHeadH2kBomByBom(
			List<EmsEdiHeadH2kBomFrom> list, EmsHeadH2k emsHeadH2k) {

		List<EmsHeadH2kBom> res = null;
		StringBuilder hql = new StringBuilder(
				" select a from EmsHeadH2kBom a where a.company.id = ?"
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ?");

		EmsEdiHeadH2kBomFrom bomFrom = null;
		if (list != null && list.size() > 0) {
			bomFrom = list.get(0);
			hql.append(" and ((a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = "
					+ bomFrom.getSeqNum()
					+ " and a.emsHeadH2kVersion.version = "
					+ bomFrom.getVersion() + " and a.seqNum = "
					+ bomFrom.getBom().getSeqNum() + ")");
			for (int i = 1; i < list.size(); i++) {
				bomFrom = list.get(i);
				hql.append(" or (a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = "
						+ bomFrom.getSeqNum()
						+ " and a.emsHeadH2kVersion.version = "
						+ bomFrom.getVersion() + " and a.seqNum = "
						+ bomFrom.getBom().getSeqNum() + ")");
			}
			hql.append(")");
		}

		System.out.println(hql.length());

		res = find(hql.toString(), new Object[] {
				CommonUtils.getCompany().getId(), emsHeadH2k.getId() });

		return res;
	}

	/**
	 * 检查导入的货号资料是否在归并关系管理归并前、内部归并、报关常用工厂物料里都存在。
	 * 
	 * @param ptNo
	 *            要检查的货号
	 * @param tableName
	 *            归并关系管理归并前table
	 * @return
	 */
	public List findBeforeMaterielPtNo(String ptNo, String tableName,
			Integer seqNum, String property) {
		List parameters = new ArrayList();
		String hsql = "";
		if (getIsEmsH2kSend()) {
			hsql = "select distinct a from InnerMergeData a," + tableName
					+ " b " + " where a.materiel.ptNo=? and a.company.id=? "
					+ " and a.isExistMerger=? and b." + property
					+ ".seqNum=a.hsAfterTenMemoNo and b." + property
					+ ".emsEdiMergerHead.historyState=? and b.sendState=? "
					+ " and b.company.id=a.company.id ";
			parameters.add(ptNo);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(true);
			parameters.add(false);
			parameters.add(Integer.valueOf(SendState.SEND));
		} else {
			hsql = "select distinct a from InnerMergeData a,"
					+ tableName
					+ " b "
					+ " where a.materiel.ptNo=? and a.company.id=? "
					+ " and a.isExistMerger=? and b."
					+ property
					+ ".seqNum=a.hsAfterTenMemoNo and b."
					+ property
					+ ".emsEdiMergerHead.historyState=? and b."
					+ property
					+ ".emsEdiMergerHead.declareState=? and b.company.id=a.company.id ";
			parameters.add(ptNo);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(true);
			parameters.add(false);
			parameters.add(DeclareState.PROCESS_EXE);
		}
		if (seqNum != null) {
			hsql += " and a.hsAfterTenMemoNo=? ";
			parameters.add(seqNum);
		}
		System.out.println("hsql=========" + hsql);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 返回归并前的最大商品序号
	 * 
	 * @param applyToCustomsBillList
	 *            表头
	 * @return
	 */
	public Integer getMaxBeforeSerialNo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		List parameters = new ArrayList();
		String hsql = " select a from AtcMergeBeforeComInfo a where a.afterComInfo.billList.id=? "
				+ " and a.company.id=? ";
		parameters.add(atcMergeAfterComInfo.getBillList().getId());
		parameters.add(CommonUtils.getCompany().getId());
		List list = find(hsql, parameters.toArray());
		return list.size();

	}

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findnameValues(int type) {
		return this
				.find("select a from ParameterSet a left join fetch a.company where a.company= ? and a.type=?",
						new Object[] { CommonUtils.getCompany(), type });
	}

	public List findEmsEdiMergerVersionCount(Integer seqNum, String states) {
		String hsql = "select b from EmsEdiMergerExgBom a left join a.emsEdiMergerVersion b"
				+ " where a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum=? and a.company.id= ?";
		List parameters = new ArrayList();
		parameters.add(seqNum);
		parameters.add(CommonUtils.getCompany().getId());

		if (states != null && !"".equals(states) && !",".equals(states)) {
			String[] stas = states.split(",");
			System.out.println(states);
			System.out.println(stas.length);
			if (stas.length == 2) {
				hsql += " and (a.modifyMark = ? or a.modifyMark = ?)";
				parameters.add(stas[0]);
				parameters.add(stas[1]);
			} else {
				hsql += " and a.modifyMark = ?";
				parameters.add(stas[0]);
			}

		}

		return this.find(hsql, parameters.toArray());
	}

	public List findEmsHeadH2KVersionCount(Integer seqNum) {
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.seqNum=? and a.company.id= ?",
						new Object[] { seqNum, CommonUtils.getCompany().getId() });
	}

	public List checkEmsExgBomModifyMarkIsUnit(EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a "
						+ " left outer join fetch a.emsHeadH2kVersion "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
						+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k "
						+ " where a.company.id= ? and "
						+ " a.modifyMark != a.emsHeadH2kVersion.emsHeadH2kExg.modifyMark and "
						+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=?",
						new Object[] { CommonUtils.getCompany().getId(),
								emsHeadH2k.getId() });
	}

	/**
	 * 修改料件,成品归并前后,成品归并BOM的发送状态,修改标志
	 * 
	 * @param head
	 * @param state
	 * @param stateType
	 */
	public void updateEmsEdiMergerImgExgBomSendStateModifyMark(
			EmsEdiMergerHead head, String state, String stateType) {
		String hsql = "";
		if (stateType.equals(ImgExgMergerType.MERGERIMG_BEFORE)) {
			hsql = "update EmsEdiMergerImgBefore a set a.sendState=?,a.modifyMark =(case when a.modifyMark !='0' then '0' else a.modifyMark end) "
					+ " where a.id in(select a.id  from EmsEdiMergerImgBefore a  where a.company.id= ? "
					+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.sendState=?)";
		} else if (stateType.equals(ImgExgMergerType.MERGEREXG_BEFORE)) {
			hsql = "update EmsEdiMergerExgBefore a set a.sendState=?,a.modifyMark =(case when a.modifyMark !='0' then '0' else a.modifyMark end) "
					+ " where a.id in (select a.id  from EmsEdiMergerExgBefore a  where a.company.id= ? "
					+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.sendState=? )";
		} else if (stateType.equals(ImgExgMergerType.MERGERIMG_AFTER)) {
			hsql = "update EmsEdiMergerImgAfter a set a.sendState=?,a.modifyMark =(case when a.modifyMark !='0' then '0' else a.modifyMark end) "
					+ " where a.company.id= ? and a.emsEdiMergerHead.id=? and a.sendState=?  ";
		} else if (stateType.equals(ImgExgMergerType.MERGEREXG_AFTER)) {
			hsql = "update EmsEdiMergerExgAfter a set a.sendState=?,a.modifyMark =(case when a.modifyMark !='0' then '0' else a.modifyMark end) "
					+ " where a.company.id= ? and a.emsEdiMergerHead.id=? and a.sendState=?  ";
		} else if (stateType.equals(ImgExgMergerType.MERGEREXG_BOM)) {
			hsql = "update EmsEdiMergerExgBom a set a.sendState=?,a.modifyMark =(case when a.modifyMark !='0' then '0' else a.modifyMark end) "
					+ " where a.id in ( select distinct a.id from EmsEdiMergerExgBom a where a.company.id= ? "
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id=?"
					+ " and a.sendState=?)";
		}
		this.batchUpdateOrDelete(
				hsql,
				new Object[] { Integer.valueOf(SendState.SEND),
						CommonUtils.getCompany().getId(), head.getId(),
						Integer.valueOf(state) });
	}
}
