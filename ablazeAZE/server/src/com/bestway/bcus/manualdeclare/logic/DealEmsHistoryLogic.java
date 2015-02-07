package com.bestway.bcus.manualdeclare.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.entity.Materiel;

/**
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DealEmsHistoryLogic {
	private EmsEdiTrDao emsEdiTrDao;
	private DataSource dataSource = null;
	private static Log logger = LogFactory.getLog(DealEmsHistoryLogic.class);

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	private void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void execuSql(String sql, Connection conn) throws SQLException {
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception ex) {
			throw new SQLException();
		} finally {
			close(conn);
		}
	}

	public void changeCustomsFromMateriel() {
		List list = emsEdiTrDao.findCustomsFromMateriel();
		for (int i = 0; i < list.size(); i++) {
			CustomsFromMateriel obj = (CustomsFromMateriel) list.get(i);
			if (obj.getBcusexgbill() != null) {
				obj.setBcusexgNo(obj.getBcusexgbill().getSeqNum());
				obj.setBcusexgbill(null);
			}
			if (obj.getBcusimgbill() != null) {
				obj.setBcusimgNo(obj.getBcusimgbill().getSeqNum());
				obj.setBcusimgbill(null);
			}
			emsEdiTrDao.saveCustomsFromMateriel(obj);
		}
	}

	public void dealEmsHistory2() throws SQLException {
		String sql = "";
		logger.info(" -- 开始更新内部商品中间表");
		changeCustomsFromMateriel();

		logger.info(" -- 开始执行事件:删除帐册BOM");
		sql = "delete emsheadh2kbom where emsheadh2kversion in (select id from emsheadh2khistoryversion)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除帐册版本");
		sql = "delete emsheadh2kversion where emsheadh2kexg in (select id from emsheadh2khistoryexg)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除帐册成品");
		sql = "delete emsheadh2kexg where emsheadh2k in (select id from emsheadh2khistory)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除帐册料件");
		sql = "delete emsheadh2kimg where emsheadh2k in (select id from emsheadh2khistory)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除帐册表头");
		sql = "delete from emsheadh2k where historystate = 1";
		execuSql(sql, dataSource.getConnection());
	}

	public void dealEmsHistory() throws SQLException {
		logger.info(" -- 开始执行事件:删除历史表BOM");
		String sql = "delete from emsheadh2khistorybom";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除历史表版本");
		sql = "delete from emsheadh2khistoryversion";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除历史表成品");
		sql = "delete from emsheadh2khistoryexg";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除历史表料件");
		sql = "delete from emsheadh2khistoryimg";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:删除历史表头");
		sql = "delete from emsheadh2khistory";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:迁移帐册表头");
		sql = "insert into emsheadh2khistory (id,emsType,preEmsNo,declareState,declareType,copEmsNo,emsNo,sancEmsNo,tradeCode,tradeName,machCode,machName,declareErType,machAbility,maxTurnMoney,modifyMark,modifyTimes,tel,address,beginDate,endDate,iePort,outTradeCo,levyKind,payMode,machiningType,inputEr,inputDate,emsApprNo,declareDate,declareTime,newApprDate,changeApprDate,note,historyState,ownerType,area,investAmount,checkMark,exeMark,imgItems,exgItems,imgAmount,exgAmount,imgWeight,exgWeight,coid) "
				+ " select id,emsType,preEmsNo,declareState,declareType,copEmsNo,emsNo,sancEmsNo,tradeCode,tradeName,machCode,machName,declareErType,machAbility,maxTurnMoney,modifyMark,modifyTimes,tel,address,beginDate,endDate,iePort,outTradeCo,levyKind,payMode,machiningType,inputEr,inputDate,emsApprNo,declareDate,declareTime,newApprDate,changeApprDate,note,historyState,ownerType,area,investAmount,checkMark,exeMark,imgItems,exgItems,imgAmount,exgAmount,imgWeight,exgWeight,coid from emsheadh2k where historystate = 1";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:迁移帐册料件");
		sql = "insert into emsheadh2khistoryimg (id,seqNum,complex,name,spec,unit,legalUnitGene,legalUnit2Gene,weigthUnitGene,curr,levyMode,note,declarePrice,declarePriceMo,modifyMark,modifyTimes,maxApprSpace,unitGrossWeight,unitNetWeight,isForbid,factoryPrice,beginDate,endDate,detailNote,emsHeadH2k,coid) "
				+ " select id,seqNum,complex,name,spec,unit,legalUnitGene,legalUnit2Gene,weigthUnitGene,curr,levyMode,note,declarePrice,declarePriceMo,modifyMark,modifyTimes,maxApprSpace,unitGrossWeight,unitNetWeight,isForbid,factoryPrice,beginDate,endDate,detailNote,emsHeadH2k,coid from emsheadh2kimg where emsheadh2k in (select id from emsheadh2khistory)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:迁移帐册成品");
		sql = "insert into emsheadh2khistoryexg (id,seqNum,complex,name,spec,unit,legalUnitGene,legalUnit2Gene,weigthUnitGene,curr,levyMode,note,declarePrice,declarePriceMo,modifyMark,modifyTimes,maxApprSpace,unitGrossWeight,unitNetWeight,factoryPrice,beginDate,endDate,emsHeadH2k,coid) "
				+ " select id,seqNum,complex,name,spec,unit,legalUnitGene,legalUnit2Gene,weigthUnitGene,curr,levyMode,note,declarePrice,declarePriceMo,modifyMark,modifyTimes,maxApprSpace,unitGrossWeight,unitNetWeight,factoryPrice,beginDate,endDate,emsHeadH2k,coid from emsheadh2kexg where emsheadh2k in (select id from emsheadh2khistory)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:迁移帐册版本");
		sql = "insert into emsheadh2khistoryversion (id,emsHeadH2kExg,version,name,isForbid,unitGrossWeight,unitNetWeight,factoryPrice,beginDate,endDate,coid)  "
				+ " select id,emsHeadH2kExg,version,name,isForbid,unitGrossWeight,unitNetWeight,factoryPrice,beginDate,endDate,coid from emsheadh2kversion where emsheadh2kexg in (select id from emsheadh2khistoryexg)";
		execuSql(sql, dataSource.getConnection());

		logger.info(" -- 开始执行事件:迁移帐册BOM");
		sql = "insert into emsheadh2khistorybom (id,seqNum,complex,name,spec,unit,note,unitWear,wear,price,modifyMark,modifyTimes,emsHeadH2kVersion,coid) "
				+ " select id,seqNum,complex,name,spec,unit,note,unitWear,wear,price,modifyMark,modifyTimes,emsHeadH2kVersion,coid from emsheadh2kbom where emsheadh2kversion in (select id from emsheadh2khistoryversion)";
		execuSql(sql, dataSource.getConnection());

	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	public void modityBomFromPrice(String seqNum, EmsHeadH2kImg emsAfter,
			EmsHeadH2k emsHeadh2k) {
		List list = this.emsEdiTrDao.findBomBySeqNum(seqNum, emsHeadh2k);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) list.get(i);
			bom.setPrice(emsAfter.getDeclarePrice());
			bom.setName(emsAfter.getName());
			bom.setSpec(emsAfter.getSpec());
			bom.setComplex(emsAfter.getComplex());
			bom.setUnit(emsAfter.getUnit());
			this.emsEdiTrDao.saveEmsHeadH2kBom(bom);
		}
	}

	public void changeChangeNum(EmsHeadH2k head) {
		List listimg = this.emsEdiTrDao.findEmsHeadH2kImg(head);
		for (int i = 0; i < listimg.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) listimg.get(i);
			Integer seqNum = img.getSeqNum();
			Integer num = this.emsEdiTrDao.findEmsImgExgCount(head
					.getModifyTimes(), seqNum, "EmsHeadH2kImg");
			if (num != null) {
				img.setModifyTimes(num - 1);
			}
			this.emsEdiTrDao.saveEmsHeadH2kImg(img);
		}

		List listexg = this.emsEdiTrDao.findEmsHeadH2kExg(head);
		for (int j = 0; j < listexg.size(); j++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) listexg.get(j);
			Integer seqNum = exg.getSeqNum();
			Integer num = this.emsEdiTrDao.findEmsImgExgCount(head
					.getModifyTimes(), seqNum, "EmsHeadH2kExg");
			if (num != null) {
				exg.setModifyTimes(num - 1);
			}
			this.emsEdiTrDao.saveEmsHeadH2kExg(exg);
			List listversion = this.emsEdiTrDao.findEmsHeadH2kVersion(exg);
			for (int k = 0; k < listversion.size(); k++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) listversion
						.get(k);
				List listbom = this.emsEdiTrDao.findEmsHeadH2kBom(version);
				for (int y = 0; y < listbom.size(); y++) {
					EmsHeadH2kBom bom = (EmsHeadH2kBom) listbom.get(y);
					Integer bomnum = this.emsEdiTrDao.findEmsBomCount(head
							.getModifyTimes(), bom.getSeqNum(), version
							.getVersion(), seqNum);
					if (bomnum != null) {
						bom.setModifyTimes(bomnum - 1);
					}
					this.emsEdiTrDao.saveEmsHeadH2kBom(bom);
				}
			}
		}
	}

	public void changetemp(EmsEdiMergerHead head) {
		List list = null;
		Hashtable hs = new Hashtable();
		list = this.emsEdiTrDao.findEmsEdiMergerImgBefore(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
			String ptNo = obj.getPtNo();
			if (hs.get(ptNo) == null) {
				hs.put(ptNo, obj);
			} else {
				emsEdiTrDao.deleteEmsEdiMergerImgBefore(obj);
			}
		}
		hs.clear();
		list = this.emsEdiTrDao.findEmsEdiMergerExgBefore(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list.get(i);
			String ptNo = obj.getPtNo();
			if (hs.get(ptNo) == null) {
				hs.put(ptNo, obj);
			} else {
				emsEdiTrDao.deleteEmsEdiMergerExgBefore(obj);
			}
		}
	}

	public void changeMateriel(String ptNo, String name, String spec) {
		Materiel mt = this.emsEdiTrDao.findMtBy(ptNo);
		if (mt != null) {
			mt.setFactoryName(name);
			mt.setFactorySpec(spec);
			this.emsEdiTrDao.saveMateriel(mt);
		}
	}
	
	// 更新四位商品资料（编码，名称）
	public void changeCustomsFourNo(String complex, String name,
			String materielType, Integer fourNo) {
		List list = this.emsEdiTrDao.findInnerMergeByFourNo(materielType,
				fourNo);
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsFourCode(complex);
			data.setHsFourMaterielName(name);
			this.emsEdiTrDao.saveInnerMergeData(data);
		}
		// ------------ 修改反向归并的4码（编码，名称）
		List fourDataList = this.emsEdiTrDao.findReverseMergeFourDataByHsFourNo(fourNo, materielType);
		for (int i = 0; i < fourDataList.size(); i++) {
			ReverseMergeFourData data = (ReverseMergeFourData) fourDataList.get(i);
			data.setHsFourCode(complex);
			data.setHsFourMaterielName(name);
			this.emsEdiTrDao.saveReverseMergeFourData(data);
		}
	}
	public void changeInner(String type, Integer seqNum, Complex complex,
			String name, String spec, Unit unit) {
//		List ls = this.emsEdiTrDao.findInnerMergeData(type, seqNum);
//		for (int i = 0; i < ls.size(); i++) {
//			InnerMergeData obj = (InnerMergeData) ls.get(i);
//			obj.setHsAfterComplex(complex);
//			obj.setHsAfterMaterielTenName(name);
//			obj.setHsAfterMaterielTenSpec(spec);
//			obj.setHsAfterMemoUnit(unit);
//			this.emsEdiTrDao.saveInnerMergeData(obj);
//		}
		this.emsEdiTrDao.updateInnerMergeData(type, seqNum, complex, name, spec, unit);
		
		
		// ------------ 修改反向归并的10码（编码，名称）
//		List tenDataList = this.emsEdiTrDao
//		for (int i = 0; i < tenDataList.size(); i++) {
//			ReverseMergeTenData obj = (ReverseMergeTenData) tenDataList.get(i);
//			obj.setHsAfterComplex(complex);
//			obj.setHsAfterMaterielTenName(name);
//			obj.setHsAfterMaterielTenSpec(spec);
//			obj.setHsAfterMemoUnit(unit);
//			this.emsEdiTrDao.saveReverseMergeTenData(obj);
//		}
		this.emsEdiTrDao.updateReverseMergeTenDataByHsAfterTenMemoNo(complex,name,spec,unit,seqNum, type);
	}

	// 同步电子帐册料件
	public List synchroEmsEdiImg(List ls) {
		List arrayList = new ArrayList();
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);
		for (int i = 0; i < ls.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) ls.get(i);
			List list = this.emsEdiTrDao.findInnerMergeData(
					MaterielType.MATERIEL, img.getSeqNum());
			InnerMergeData inner = (InnerMergeData) list.get(0);
			if (img.getModifyMark() != null
					&& img.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				img.setModifyMark(ModifyMarkState.MODIFIED);
				img.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			img.setName(inner.getHsAfterMaterielTenName());
			img.setSpec(inner.getHsAfterMaterielTenSpec());
			img.setComplex(inner.getHsAfterComplex());
			img.setUnit(inner.getHsAfterMemoUnit());
			this.emsEdiTrDao.saveEmsHeadH2kImg(img);

			modityBomFromPrice(String.valueOf(img.getSeqNum()), img, img
					.getEmsHeadH2k());
			arrayList.add(img);
		}
		return arrayList;
	}

	// 同步电子帐册成品
	public List synchroEmsEdiExg(List ls) {
		List arrayList = new ArrayList();
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);
		for (int i = 0; i < ls.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) ls.get(i);
			List list = this.emsEdiTrDao.findInnerMergeData(
					MaterielType.FINISHED_PRODUCT, exg.getSeqNum());
			InnerMergeData inner = (InnerMergeData) list.get(0);
			if (exg.getModifyMark() != null
					&& exg.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				exg.setModifyMark(ModifyMarkState.MODIFIED);
				exg.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			exg.setName(inner.getHsAfterMaterielTenName());
			exg.setSpec(inner.getHsAfterMaterielTenSpec());
			exg.setComplex(inner.getHsAfterComplex());
			exg.setUnit(inner.getHsAfterMemoUnit());
			this.emsEdiTrDao.saveEmsHeadH2kExg(exg);
			arrayList.add(exg);
		}
		return arrayList;
	}

	// 同步归并关系料件
	public List synchroEmsMergerImg(List ls) {
		List arrayList = new ArrayList();
		String isSendSign = emsEdiTrDao.getBpara(BcusParameter.EmsSEND_Sign);
		for (int i = 0; i < ls.size(); i++) {
			EmsEdiMergerImgAfter after = (EmsEdiMergerImgAfter) ls.get(i);
			List list = this.emsEdiTrDao.findInnerMergeData(
					MaterielType.MATERIEL, after.getSeqNum());
			if(list == null || list.size()==0){
				return null;
			}
			InnerMergeData inner = (InnerMergeData) list.get(0);
			if (after.getModifyMark() != null
					&& after.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				after.setModifyMark(ModifyMarkState.MODIFIED);
				after.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			after.setName(inner.getHsAfterMaterielTenName());
			after.setSpec(inner.getHsAfterMaterielTenSpec());
			after.setComplex(inner.getHsAfterComplex());
			after.setUnit(inner.getHsAfterMemoUnit());
			this.emsEdiTrDao.saveEmsEdiMergerImgAfter(after);
			List beforels = emsEdiTrDao.findEmsEdiMergerImgBefore(after);
			for (int j = 0; j < beforels.size(); j++) {
				EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore) beforels
						.get(j);
				img.setComplex(after.getComplex());
				img.setUnit(after.getUnit());
				if (img.getModifyMark() != null
						&& img.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
					img.setModifyMark(ModifyMarkState.MODIFIED);
					img.setSendState(Integer.parseInt(SendState.WAIT_SEND));
				}
				emsEdiTrDao.saveEmsEdiMergerImgBefore(img);
				List bomls = this.emsEdiTrDao.findEmsEdiMergerBomByPtNo(after
						.getEmsEdiMergerHead(), img.getPtNo());
				for (int k = 0; k < bomls.size(); k++) {
					EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) bomls.get(k);
					bom.setComplex(img.getComplex());
					bom.setUnit(img.getUnit());
					this.emsEdiTrDao.saveEmsEdiMergerExgBom(bom);
				}
			}
			arrayList.add(after);
		}
		return arrayList;
	}

	// 同步归并关系成品
	public List synchroEmsMergerExg(List ls) {
		List arrayList = new ArrayList();
		String isSendSign = emsEdiTrDao.getBpara(BcusParameter.EmsSEND_Sign);
		for (int i = 0; i < ls.size(); i++) {
			EmsEdiMergerExgAfter after = (EmsEdiMergerExgAfter) ls.get(i);
			List list = this.emsEdiTrDao.findInnerMergeData(
					MaterielType.FINISHED_PRODUCT, after.getSeqNum());
			if(list == null || list.size()==0){
				return null;
			}
			InnerMergeData inner = (InnerMergeData) list.get(0);
			if (after.getModifyMark() != null
					&& after.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				after.setModifyMark(ModifyMarkState.MODIFIED);
				after.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			after.setName(inner.getHsAfterMaterielTenName());
			after.setSpec(inner.getHsAfterMaterielTenSpec());
			after.setComplex(inner.getHsAfterComplex());
			after.setUnit(inner.getHsAfterMemoUnit());
			this.emsEdiTrDao.saveEmsEdiMergerExgAfter(after);
			List beforels = emsEdiTrDao.findEmsEdiMergerExgBefore(after);
			for (int j = 0; j < beforels.size(); j++) {
				EmsEdiMergerExgBefore exg = (EmsEdiMergerExgBefore) beforels
						.get(j);
				exg.setComplex(after.getComplex());
				exg.setUnit(after.getUnit());
				if (exg.getModifyMark() != null
						&& exg.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
					exg.setModifyMark(ModifyMarkState.MODIFIED);
					exg.setSendState(Integer.parseInt(SendState.WAIT_SEND));
				}
				emsEdiTrDao.saveEmsEdiMergerExgBefore(exg);
			}
			arrayList.add(after);
		}
		return arrayList;
	}

	// 归并关系料件重新归并
	public void changeMergerSeqNumImg(List ls, EmsEdiMergerHead emsMerger,
			Integer seqNum) {
		EmsEdiMergerImgAfter after = emsEdiTrDao
				.findEmsEdiMergerImgAfterBySeqNum(emsMerger, seqNum);
		if (after == null) {
			return;
		}
		for (int i = 0; i < ls.size(); i++) {
			EmsEdiMergerImgBefore beforeimg = (EmsEdiMergerImgBefore) ls.get(i);
			beforeimg.setComplex(after.getComplex());
			beforeimg.setUnit(after.getUnit());
			beforeimg.setSeqNum(after.getSeqNum());
			beforeimg.setEmsEdiMergerImgAfter(after);
			if (beforeimg.getModifyMark() != null
					&& beforeimg.getModifyMark().equals(
							ModifyMarkState.UNCHANGE)) {
				beforeimg.setModifyMark(ModifyMarkState.MODIFIED); // 已修改标记
			}
			beforeimg.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			this.emsEdiTrDao.saveEmsEdiMergerImgBefore(beforeimg);

			InnerMergeData data = this.emsEdiTrDao
					.findInnerMergeDataByMaterial(beforeimg.getPtNo());// 老归并
			List list = this.emsEdiTrDao.findInnerMergeData(
					MaterielType.MATERIEL, seqNum); // 新归并
			InnerMergeData obj = (InnerMergeData) list.get(0);
			data.setHsAfterTenMemoNo(obj.getHsAfterTenMemoNo());
			data.setHsAfterComplex(obj.getHsAfterComplex());
			data.setHsAfterMaterielTenName(obj.getHsAfterMaterielTenName());
			data.setHsAfterMaterielTenSpec(obj.getHsAfterMaterielTenSpec());
			data.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());
			data.setHsAfterLegalUnit(obj.getHsAfterLegalUnit());
			data.setHsAfterSecondLegalUnit(obj.getHsAfterSecondLegalUnit());
			data.setHsFourNo(obj.getHsFourNo());
			data.setHsFourMaterielName(obj.getHsFourMaterielName());
			data.setHsFourCode(obj.getHsFourCode());
			data.setUpdateDate(new Date());
			this.emsEdiTrDao.saveInnerMergeData(data);

			List bomls = this.emsEdiTrDao.findEmsEdiMergerBomByPtNo(emsMerger,
					beforeimg.getPtNo());
			for (int k = 0; k < bomls.size(); k++) {
				EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) bomls.get(k);
				bom.setComplex(obj.getHsAfterComplex());
				bom.setUnit(obj.getHsAfterMemoUnit());
				this.emsEdiTrDao.saveEmsEdiMergerExgBom(bom);
			}
		}
	}

	// 归并关系成品重新归并
	public void changeMergerSeqNumExg(List ls, EmsEdiMergerHead emsMerger,
			Integer seqNum) {
		EmsEdiMergerExgAfter after = emsEdiTrDao
				.findEmsEdiMergerExgAfterBySeqNum(emsMerger, seqNum);
		if (after == null) {
			return;
		}
		for (int i = 0; i < ls.size(); i++) {
			EmsEdiMergerExgBefore beforeexg = (EmsEdiMergerExgBefore) ls.get(i);
			beforeexg.setEmsEdiMergerExgAfter(after);
			beforeexg.setComplex(after.getComplex());
			beforeexg.setUnit(after.getUnit());
			beforeexg.setSeqNum(after.getSeqNum());
			beforeexg.setModifyMark(ModifyMarkState.ADDED); // 新增标记
			beforeexg.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			this.emsEdiTrDao.saveEmsEdiMergerExgBefore(beforeexg);
			InnerMergeData data = this.emsEdiTrDao
					.findInnerMergeDataByMaterial(beforeexg.getPtNo());
			List list = this.emsEdiTrDao.findInnerMergeData(
					MaterielType.FINISHED_PRODUCT, seqNum); // 新归并
			InnerMergeData obj = (InnerMergeData) list.get(0);
			data.setHsAfterTenMemoNo(obj.getHsAfterTenMemoNo());
			data.setHsAfterComplex(obj.getHsAfterComplex());
			data.setHsAfterMaterielTenName(obj.getHsAfterMaterielTenName());
			data.setHsAfterMaterielTenSpec(obj.getHsAfterMaterielTenSpec());
			data.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());
			data.setHsAfterLegalUnit(obj.getHsAfterLegalUnit());
			data.setHsAfterSecondLegalUnit(obj.getHsAfterSecondLegalUnit());
			data.setHsFourNo(obj.getHsFourNo());
			data.setHsFourMaterielName(obj.getHsFourMaterielName());
			data.setHsFourCode(obj.getHsFourCode());
			data.setUpdateDate(new Date());
			this.emsEdiTrDao.saveInnerMergeData(data);
		}
	}

	// 电子帐册单耗全选
	public List emsSelectAllSendState(List ls) {
		List arrayList = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) ls.get(i);
			if (bom.getSendState() == null
					|| bom.getSendState().equals(
							Integer.valueOf(SendState.NOT_SEND))) {
				bom.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				this.emsEdiTrDao.saveEmsHeadH2kBom(bom);
			}
			arrayList.add(bom);
		}
		return arrayList;
	}

	// 归并关系BOM全选
	public List emsMergerSelectAllSendState(List ls) {
		List arrayList = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) ls.get(i);
			if (bom.getSendState() == null
					|| bom.getSendState().equals(
							Integer.valueOf(SendState.NOT_SEND))) {
				bom.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				this.emsEdiTrDao.saveEmsEdiMergerExgBom(bom);
			}
			arrayList.add(bom);
		}
		return arrayList;
	}

	public List makeEmsEdiMergerExgBomWasteToInt(List list) {
		for (int k = 0; k < list.size(); k++) {
			EmsEdiMergerExgBom ebom = (EmsEdiMergerExgBom) list.get(k);
			ebom.setWaste(Double.valueOf(String.valueOf(Math.round(ebom
					.getWaste() == null ? 0.0 : ebom.getWaste()))));
			this.emsEdiTrDao.saveOrUpdate(ebom);
		}
		return new ArrayList();
	}
}