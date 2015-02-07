package com.bestway.common.materialbase.logic;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.hibernate.EntityMode;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.cas.entity.TempBomsManager;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.EmptyProductMaterial;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.EnterpriseBomMaster;
import com.bestway.common.materialbase.entity.EnterpriseBomVersion;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCocForInput;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.TempBomManage;
import com.bestway.common.materialbase.entity.TempBomParam;
import com.bestway.common.materialbase.entity.TempEntBomMaster;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.common.materialbase.entity.TempMotor;
import com.bestway.common.materialbase.entity.TempOfCheck;
import com.bestway.common.materialbase.entity.TempRepeatMateriel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.common.materialbase.logic.MaterialManageLogic
 * 
 * @author administrator checked by lm date 2010.04.09 物流基础资料LOGIC
 */
@SuppressWarnings("unchecked")
public class MaterialManageLogic {

	// Connection conn = null;

	// private DataSource dataSource = null;
	/**
	 * 工厂通用代码（方法）
	 */
	private MaterialManageDao materialManageDao = null;

	/**
	 * 获取materialManageDao
	 * 
	 * @return materialManageDao
	 */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/**
	 * 设置materialManageDao
	 * 
	 * @param materialManageDao
	 */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 取得汇率
	 * 
	 * @param sourCurr
	 *            本地汇率
	 * @param destCurr
	 *            外地汇率
	 * @param createDate
	 *            创建日期
	 * @return 为double型，汇率
	 */
	public double findExchangeRate(Curr sourCurr, Curr destCurr, Date createDate) {
		if (sourCurr.equals(destCurr)) {
			return 1.0;
		}
		if (sourCurr == null) {
			throw new RuntimeException("本币币别为空,查不到其汇率");
		}
		if (destCurr == null) {
			throw new RuntimeException("外币币别为空,查不到其汇率");
		}
		// List list = this.fecavDao.findExchangeRate(sourCurr, destCurr,
		// createDate);
		List list = this.materialManageDao.findExchangeRate(destCurr, sourCurr,
				createDate);
		if (list.size() < 1 || list.get(0) == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = "";
			if (createDate != null) {
				date = format.format(createDate);
			}
			throw new RuntimeException("没有 " + destCurr.getName() + " 和 "
					+ sourCurr.getName() + "在日期" + date + "之前 的汇率设定");
		}
		CurrRate currRate = (CurrRate) list.get(0);
		return currRate.getRate();
	}

	/**
	 * 构造工厂BOM管理父件资料
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是TempEntBomMaster型，临时的工厂BOM管理父件资料
	 */
	public List findEnterpriseBomMaster(int index, int length, String property,
			Object value, Boolean isLike) {
		Set<Object> hsTemp = new HashSet<Object>();
		Map<String, EnterpriseMaterial> ptNomap = materialManageDao
				.findEnterpriseMaterialPtNoMap();
		List list = materialManageDao.findEnterpriseBomExg(index, length,
				property, value, isLike);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomMaster temp = new TempEntBomMaster();
			// Object[] obj = (Object[]) list.get(i);
			String ptNo = list.get(i).toString();// obj[0];
			// EnterpriseMaterial materiel = this.materialManageDao
			// .findEnterpriseMaterialByPtNo(ptNo);
			EnterpriseMaterial materiel = ptNomap.get(ptNo);
			temp.setPtNo(ptNo);
			if (materiel != null) {
				temp.setScmCoiName(materiel.getScmCoi() == null ? "" : materiel
						.getScmCoi().getName());
				temp.setPtName(materiel.getFactoryName());
				temp.setPtSpec(materiel.getFactorySpec());
				if (materiel.getCalUnit() != null) {
					temp.setCalUnitName(materiel.getCalUnit().getName());
				}
			}
			hsTemp.add(temp);
		}
		list = materialManageDao.findEnterpriseBomMaster(index, length,
				property, value, isLike);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				TempEntBomMaster temp = new TempEntBomMaster();
				EnterpriseBomMaster obj = (EnterpriseBomMaster) list.get(i);
				String ptNo = obj.getPtNo();
				// EnterpriseMaterial materiel = this.materialManageDao
				// .findEnterpriseMaterialByPtNo(ptNo);
				EnterpriseMaterial materiel = ptNomap.get(ptNo);
				if (materiel != null) {
					temp.setScmCoiName(materiel.getScmCoi() == null ? ""
							: materiel.getScmCoi().getName());
				}
				temp.setPtNo(obj.getPtNo());
				temp.setPtName(obj.getPtName());
				temp.setPtSpec(obj.getPtSpec());
				temp.setCalUnitName(obj.getCalUnitName());
				hsTemp.add(temp);
			}
			// return new ArrayList(hsTemp);
		}
		return new ArrayList(hsTemp);
		// return new ArrayList();
		// }
	}

	/**
	 * 构造工厂BOM管理父件资料
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是TempEntBomMaster型，临时的工厂BOM管理父件资料
	 */
	public List findEnterpriseBomMasterZX(int index, int length, String property,
			Object value, Boolean isLike) {
		Map<String, TempEntBomMaster> hsTemp = new HashMap<String, TempEntBomMaster>();
		Map<String, EnterpriseMaterial> ptNomap = materialManageDao
				.findEnterpriseMaterialPtNoMap();
		List list = materialManageDao.findEnterpriseBomExg(index, length,
				property, value, isLike);
		List<String> materielIdList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			TempEntBomMaster temp = new TempEntBomMaster();
			// Object[] obj = (Object[]) list.get(i);
			String ptNo = list.get(i).toString();// obj[0];
			// EnterpriseMaterial materiel = this.materialManageDao
			// .findEnterpriseMaterialByPtNo(ptNo);
			EnterpriseMaterial materiel = ptNomap.get(ptNo);
			temp.setPtNo(ptNo);
			if (materiel != null) {
				temp.setScmCoiName(materiel.getScmCoi() == null ? "" : materiel
						.getScmCoi().getName());
				temp.setPtName(materiel.getFactoryName());
				temp.setPtSpec(materiel.getFactorySpec());
				if (materiel.getCalUnit() != null) {
					temp.setCalUnitName(materiel.getCalUnit().getName());
				}
				materielIdList.add(materiel.getId());
			}
			hsTemp.put(ptNo, temp);
		}
		list = materialManageDao.findEnterpriseBomMaster(index, length,
				property, value, isLike);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				TempEntBomMaster temp = new TempEntBomMaster();
				EnterpriseBomMaster obj = (EnterpriseBomMaster) list.get(i);
				String ptNo = obj.getPtNo();
				// EnterpriseMaterial materiel = this.materialManageDao
				// .findEnterpriseMaterialByPtNo(ptNo);
				EnterpriseMaterial materiel = ptNomap.get(ptNo);
				if (materiel != null) {
					temp.setScmCoiName(materiel.getScmCoi() == null ? ""
							: materiel.getScmCoi().getName());
					materielIdList.add(materiel.getId());
				}
				temp.setPtNo(obj.getPtNo());
				temp.setPtName(obj.getPtName());
				temp.setPtSpec(obj.getPtSpec());
				temp.setCalUnitName(obj.getCalUnitName());
				hsTemp.put(ptNo, temp);
			}
		}
		
		List<Object[]> ptNoSeqNumList = materialManageDao.findPtNoAndSeqNumByMaterialId(materielIdList);
		TempEntBomMaster master = null;
		for (Object[] objs : ptNoSeqNumList) {
			master = hsTemp.get(objs[0]);
			if(master != null) {
				master.setSeqNum((Integer) objs[1]);
			}
		}
		
		
		return new ArrayList(hsTemp.values());
	}
	/**
	 * 构造工厂BOM管理版本
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	public List findEnterpriseBomVersion(String parentNo, int bomStructureType) {
		Set<Object> hsTemp = new HashSet<Object>();
		List list = materialManageDao.findEnterpriseBomVers(parentNo);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomVersion temp = new TempEntBomVersion();
			Object[] obj = (Object[]) list.get(i);

			if (bomStructureType == BomStructureType.NO_VERSION_NO_DATE) {
				temp.setVersion(0);
				temp.setBeginDate(null);
				temp.setEndDate(null);
				temp.setNetWeight((Double) obj[4]);
			} else {
				temp.setVersion(obj[0] == null ? null : Integer.parseInt(obj[0]
						.toString().trim()));
				temp.setBeginDate((Date) obj[1]);
				temp.setEndDate((Date) obj[2]);
				temp.setNotes((String) obj[3]);
				temp.setNetWeight((Double) obj[4]);
			}
			String memo = materialManageDao.findEnterpriseBomVersMemo(parentNo,
					temp.getVersion());
			temp.setNotes(memo);
			hsTemp.add(temp);
		}
		list = materialManageDao.findEnterpriseBomVersion(parentNo);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomVersion temp = new TempEntBomVersion();
			EnterpriseBomVersion obj = (EnterpriseBomVersion) list.get(i);
			if (bomStructureType == BomStructureType.NO_VERSION_NO_DATE) {
				temp.setVersion(0);
				temp.setBeginDate(null);
				temp.setEndDate(null);
			} else {
				temp.setVersion(obj.getVersion());
				temp.setBeginDate(obj.getBeginDate());
				temp.setEndDate(obj.getEndDate());
				temp.setNotes(obj.getNotes());
			}
			hsTemp.add(temp);
		}
		return new ArrayList(hsTemp);
	}
	
	
	/**
	 * 构造工厂BOM管理版本
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	public List findMaterialBomVersion(String parentNo, int bomStructureType) {
		Set<Object> hsTemp = new HashSet<Object>();
		List list = materialManageDao.findMaterielBomVersionByPtNo(parentNo);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomVersion temp = new TempEntBomVersion();
			Integer obj = (Integer) list.get(i);
			if (bomStructureType == BomStructureType.NO_VERSION_NO_DATE) {
				temp.setVersion(0);
				temp.setBeginDate(null);
				temp.setEndDate(null);
			} else {
				temp.setVersion(obj);
				temp.setBeginDate(null);
				temp.setEndDate(null);
			}
			hsTemp.add(temp);
		}
		return new ArrayList(hsTemp);
	}
	
	/**
	 * 构造工厂BOM管理版本时间
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	public List findMaterialBomVersionDate(String parentNo) {
		Set<Object> hsTemp = new HashSet<Object>();
		List<MaterialBomVersion> list = materialManageDao.findMaterielBomVersionObjectByPtNo(parentNo);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomVersion temp = new TempEntBomVersion();
			temp.setVersion(0);
			temp.setBeginDate(list.get(i).getBeginDate());
			temp.setEndDate(list.get(i).getEndDate());
			hsTemp.add(temp);
		}
		return new ArrayList(hsTemp);
	}
	
	
	/**
	 * 构造工厂BOM管理版本(震兴)
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	public List findEnterpriseBomVersionZX(String parentNo, int bomStructureType) {
		List<TempEntBomVersion> hsTemp = new ArrayList<TempEntBomVersion>();
		List list = materialManageDao.findEnterpriseBomVersion(parentNo);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomVersion temp = new TempEntBomVersion();
			EnterpriseBomVersion obj = (EnterpriseBomVersion) list.get(i);
			if (bomStructureType == BomStructureType.NO_VERSION_NO_DATE) {
				temp.setVersion(0);
				temp.setBeginDate(null);
				temp.setEndDate(null);
				temp.setNetWeight(obj.getNetWeight());
			} else {
				temp.setVersion(obj.getVersion());
				temp.setBeginDate(obj.getBeginDate());
				temp.setEndDate(obj.getEndDate());
				temp.setNotes(obj.getNotes());
				temp.setNetWeight(obj.getNetWeight());
			}
			if(!hsTemp.contains(temp)) {
				hsTemp.add(temp);
			}
		}
		
		if(hsTemp.size() == 0) {
			list = materialManageDao.findEnterpriseBomVers(parentNo);
			for (int i = 0; i < list.size(); i++) {
				TempEntBomVersion temp = new TempEntBomVersion();
				Object[] obj = (Object[]) list.get(i);

				if (bomStructureType == BomStructureType.NO_VERSION_NO_DATE) {
					temp.setVersion(0);
					temp.setBeginDate(null);
					temp.setEndDate(null);
				} else {
					temp.setVersion(obj[0] == null ? null : Integer.parseInt(obj[0]
							.toString().trim()));
					temp.setBeginDate((Date) obj[1]);
					temp.setEndDate((Date) obj[2]);
					temp.setNotes((String) obj[3]);
				}
				String memo = materialManageDao.findEnterpriseBomVersMemo(parentNo,
						temp.getVersion());
				temp.setNotes(memo);
				if(!hsTemp.contains(temp)) {
					hsTemp.add(temp);
				}
			}
		}
		
		return hsTemp;
	}

	/**
	 * 构造工厂BOM管理子件
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return HashMap<String, List<Object>> key为版本号＋有效日期＋失效日期，value为工厂BOM管理子件资料
	 */
	public HashMap<String, List<Object>> findEnterpriseBomDetail(
			String parentNo, int bomStructureType) {
		HashMap<String, List<Object>> hmResult = new HashMap<String, List<Object>>();
		List list = materialManageDao.findEnterpriseBomDetail(parentNo);
		for (int i = 0; i < list.size(); i++) {
			EnterpriseBomManage bomManage = (EnterpriseBomManage) list.get(i);// objs[0];
			EnterpriseMaterial enterpriseMaterial = this.materialManageDao
					.findEnterpriseMaterial(bomManage.getCompentNo());
			if (enterpriseMaterial != null) {
				bomManage.setCompentName(enterpriseMaterial.getFactoryName());
				bomManage.setCompentSpec(enterpriseMaterial.getFactorySpec());
			}
			String v = "0";
			String b = "";
			String e = "";
			if (bomStructureType != BomStructureType.NO_VERSION_NO_DATE) {
				v = (bomManage.getVersionNo() == null ? "" : bomManage
						.getVersionNo().trim());
				b = (bomManage.getEffectDate() == null ? "" : DateFormatUtils
						.format(bomManage.getEffectDate(), "yyyy-MM-dd"));
				e = (bomManage.getEndDate() == null ? "" : DateFormatUtils
						.format(bomManage.getEndDate(), "yyyy-MM-dd"));
			}
			String key = v + b + e;
			List<Object> lsDetail = hmResult.get(key);
			if (lsDetail == null) {
				lsDetail = new ArrayList<Object>();
				hmResult.put(key, lsDetail);
			}
			lsDetail.add(bomManage);
		}
		return hmResult;
	}

	/**
	 * 构造工厂BOM管理子件
	 * 
	 * @param parentNo
	 *            父件料号
	 * @return List 是EnterpriseBomManage型，存放工厂BOM管理子件资料
	 */
	public List findEnterpriseBomDetail(String parentNo) {
		List list = materialManageDao.findEnterpriseBomDetail(parentNo);
		for (int i = 0; i < list.size(); i++) {
			EnterpriseBomManage bomManage = (EnterpriseBomManage) list.get(i);// objs[0];
			EnterpriseMaterial enterpriseMaterial = this.materialManageDao
					.findEnterpriseMaterial(bomManage.getCompentNo());
			if (enterpriseMaterial != null) {
				bomManage.setCompentName(enterpriseMaterial.getFactoryName());
				bomManage.setCompentSpec(enterpriseMaterial.getFactorySpec());
			}
		}
		return list;
	}
	
	/**
	 * 构造工厂所有BOM管理子件
	 * 
	 * @param parentNo
	 *            父件料号
	 * @return List 是EnterpriseBomManage型，存放工厂BOM管理子件资料
	 */
	public List findAllEnterpriseBomDetailLs(int index,
			int length, String property, Object value, Boolean isLike) {
		List list = materialManageDao.findAllEnterpriseBomDetail(index, length, property, value, isLike);
		
		List<EnterpriseMaterial> enterpriseMaterialLs = this.materialManageDao.findEnterpriseMaterialList();
		
		for (int i = 0; i < list.size(); i++) {
			EnterpriseBomManage bomManage = (EnterpriseBomManage) list.get(i);// objs[0];
			
			for(EnterpriseMaterial enterpriseMaterial : enterpriseMaterialLs){			
				if (enterpriseMaterial.getPtNo().equals(bomManage.getCompentNo())) {
					bomManage.setCompentName(enterpriseMaterial.getFactoryName());
					bomManage.setCompentSpec(enterpriseMaterial.getFactorySpec());
					break;
				}				
			}
			
		}
		return list;
	}

	/**
	 * 从工厂物料主档资料里增加工厂BOM管理父料
	 * 
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @return List 是TempEntBomMaster型，临时的工厂BOM管理父件资料
	 */
	public List addEnterpriseBomMaster(List<EnterpriseMaterial> list) {
		List<TempEntBomMaster> lsResult = new ArrayList<TempEntBomMaster>();
		for (EnterpriseMaterial material : list) {
			EnterpriseBomMaster master = new EnterpriseBomMaster();
			master.setPtNo(material.getPtNo());
			master.setPtName(material.getFactoryName());
			master.setPtSpec(material.getFactorySpec());
			master.setCalUnitName(material.getCalUnit() == null ? "" : material
					.getCalUnit().getName());
			this.materialManageDao.saveEnterpriseBomMaster(master);
			TempEntBomMaster tempMaster = new TempEntBomMaster();
			tempMaster.setScmCoiName(material.getScmCoi().getName());
			tempMaster.setPtNo(material.getPtNo());
			tempMaster.setPtName(material.getFactoryName());
			tempMaster.setPtSpec(material.getFactorySpec());
			tempMaster.setCalUnitName(material.getCalUnit() == null ? ""
					: material.getCalUnit().getName());
			lsResult.add(tempMaster);
		}
		return lsResult;
	}

	/**
	 * 增加工厂BOM管理版本
	 * 
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 * 
	 */
	public void addEnterpriseBomVersion(TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion) {
		EnterpriseBomVersion version = new EnterpriseBomVersion();
		version.setParentNo(tempMaster.getPtNo());
		version.setVersion(tempVersion.getVersion());
		version.setBeginDate(tempVersion.getBeginDate());
		version.setEndDate(tempVersion.getEndDate());
		version.setNotes(tempVersion.getNotes());
		this.materialManageDao.saveEnterpriseBomVersion(version);
	}

	/**
	 * 增加工厂BOM管理版本
	 * 
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	public void saveEnterpriseBomVersion(TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion) {
		EnterpriseBomVersion version = this.materialManageDao
				.findEnterpriseBomVersion(tempMaster.getPtNo(), tempVersion
						.getVersion());
		if (version != null) {
			version.setBeginDate(tempVersion.getBeginDate());
			version.setEndDate(tempVersion.getEndDate());
			version.setVersion(tempVersion.getVersion());
			version.setNotes(tempVersion.getNotes());
			version.setNetWeight(tempVersion.getNetWeight());
			this.materialManageDao.saveEnterpriseBomVersion(version);
		} else {
			List list = this.materialManageDao
					.findEnterpriseBomManageByVersion(tempMaster.getPtNo(),
							tempVersion.getVersion());
			for (int i = 0; i < list.size(); i++) {
				EnterpriseBomManage manage = (EnterpriseBomManage) list.get(i);
				manage.setEffectDate(tempVersion.getBeginDate());
				manage.setEndDate(tempVersion.getEndDate());
				manage.setMemo(tempVersion.getNotes());
				this.materialManageDao.saveEnterpriseBomManage(manage);
			}
			// this.materialManageDao.updateEnterpriseBomManage(tempMaster
			// .getPtNo(), tempVersion);
		}
	}
	
	
	/**
	 * 增加工厂BOM管理版本（震兴）
	 * 计算净重
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	public void saveEnterpriseBomVersionZX(TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion) {
		EnterpriseBomVersion version = this.materialManageDao
				.findEnterpriseBomVersion(tempMaster.getPtNo(), tempVersion
						.getVersion());
		
//		EnterpriseMaterial em = materialManageDao.findEnterpriseMaterial(tempMaster.getPtNo());
//		Materiel m = materialManageDao.findMateriel(tempMaster.getPtNo());
//		// 修改物流主档的净重
//		em.setPtNetWeight(tempVersion.getNetWeight());
//		// 修改报关常用物料
//		m.setPtNetWeight(tempVersion.getNetWeight());
		if(version == null) {
			version = new EnterpriseBomVersion();
		}
		version.setParentNo(tempMaster.getPtNo());
		version.setBeginDate(tempVersion.getBeginDate());
		version.setEndDate(tempVersion.getEndDate());
		version.setVersion(tempVersion.getVersion());
		version.setNotes(tempVersion.getNotes());
		version.setNetWeight(tempVersion.getNetWeight());
		
		this.materialManageDao.saveEnterpriseBomVersion(version);
//		this.materialManageDao.saveMateriel(m);
//		this.materialManageDao.saveEnterpriseMaterial(em);
	}
	
	/**
	 * 增加工厂BOM管理版本（震兴）
	 * 修改净重
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	public void saveEnterpriseBomManageZX(EnterpriseBomManage detail) {
		
		// 料号
		String ptNo = detail.getCompentNo();
		// 净重
		Double netWeight = detail.getNetWeight();
		
		
		// 修改物流主档的净重
		EnterpriseMaterial em = materialManageDao.findEnterpriseMaterial(ptNo);
		em.setPtNetWeight(netWeight);
		
		// 修改报关常用物料
		Materiel m = materialManageDao.findMateriel(ptNo);
		m.setPtNetWeight(netWeight);
		
		// 修改工厂BOM子件净重
		List<EnterpriseBomManage> ebmList = materialManageDao.findEnterpriseBomManageList(ptNo);
		for (EnterpriseBomManage enterpriseBomManage : ebmList) {
			enterpriseBomManage.setNetWeight(netWeight);
			this.materialManageDao.saveEnterpriseBomManage(enterpriseBomManage);
		}
		
		this.materialManageDao.saveMateriel(m);
		this.materialManageDao.saveEnterpriseMaterial(em);
		this.materialManageDao.saveEnterpriseBomManage(detail);
	}
	
	/**
	 * 查找 工厂物料主档 资料，但是不在 工厂BOM管理 里由给定的父级料号和父件版本号所确定的子件的子级料号里（不是指定半成品的子件），
	 * 但要去掉循环调用的。
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialNotInDetail(String parentNo,
			Integer version) {
		List list = this.materialManageDao.findEnterpriseMaterialNotInDetail(
				parentNo, version);
		Set set = new HashSet();
		set.add(parentNo);
		getParentEnterpriseMaterial(set, parentNo);
		for (int i = list.size() - 1; i >= 0; i--) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
					.get(i);
			if (set.contains(enterpriseMaterial.getPtNo())) {
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * 返回工厂BOM管理里的最定级，也就是成品
	 * 
	 * @param set
	 *            是Set型，用来存放各级的父件料号
	 * @param compentNo
	 *            子件料号
	 */
	private void getParentEnterpriseMaterial(Set set, String compentNo) {
		List lsParent = this.materialManageDao
				.findEnterpriseBomManageList(compentNo);
		for (int i = 0; i < lsParent.size(); i++) {
			EnterpriseBomManage manage = (EnterpriseBomManage) lsParent.get(i);
			set.add(manage.getParentNo());
			getParentEnterpriseMaterial(set, manage.getParentNo());
		}
	}

	/**
	 * 增加工厂BOM管理子件
	 * 
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @return List 是EnterpriseBomManage型，工厂BOM管理子件资料
	 */
	public List addEnterpriseBomDetail(TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion, List<EnterpriseMaterial> list) {
		List<EnterpriseBomManage> lsResult = new ArrayList<EnterpriseBomManage>();
		for (EnterpriseMaterial material : list) {
			EnterpriseBomManage bomManage = new EnterpriseBomManage();
			bomManage.setParentNo(tempMaster.getPtNo());
			bomManage.setCompentNo(material.getPtNo());
			bomManage.setCompentName(material.getFactoryName());
			bomManage.setCompentSpec(material.getFactorySpec());
			bomManage.setNetWeight(material.getPtNetWeight());
			bomManage.setVersionNo(tempVersion.getVersion() == null ? "0"
					: tempVersion.getVersion().toString());
			bomManage.setEffectDate(tempVersion.getBeginDate());
			bomManage.setEndDate(tempVersion.getEndDate());
			this.materialManageDao.saveEnterpriseBomManage(bomManage);
			EnterpriseBomVersion version = this.materialManageDao
					.findEnterpriseBomVersion(tempMaster.getPtNo(), tempVersion
							.getVersion());
			if (version != null) {
				this.materialManageDao.deleteEnterpriseBomVersion(version);
			}
			EnterpriseBomMaster master = this.materialManageDao
					.findEnterpriseBomMaster(tempMaster.getPtNo());
			if (master != null) {
				this.materialManageDao.deleteEnterpriseBomMaster(master);
			}
			lsResult.add(bomManage);
		}
		return lsResult;
	}

	/**
	 * 删除工厂BOM管理父料
	 * 
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 */
	public void deleteEnterpriseBomMaster(TempEntBomMaster tempMaster) {
		EnterpriseBomMaster master = this.materialManageDao
				.findEnterpriseBomMaster(tempMaster.getPtNo());
		if (master != null) {
			this.materialManageDao.deleteEnterpriseBomMaster(master);
		}
		this.materialManageDao.deleteEnterpriseBomVersionByParent(tempMaster
				.getPtNo());
		this.materialManageDao.deleteEnterpriseBomManageByParent(tempMaster
				.getPtNo());
	}

	/**
	 * 单位用量计算
	 */
	public void accountUnitDosage() {
		materialManageDao.updateEnterpriseBomManageByptNo();
	}

	/**
	 * 删除工厂BOM管理版本
	 * 
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	public void deleteEnterpriseBomVersion(TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion) {
		EnterpriseBomVersion version = this.materialManageDao
				.findEnterpriseBomVersion(tempMaster.getPtNo(), tempVersion
						.getVersion());
		if (version != null) {
			this.materialManageDao.deleteEnterpriseBomVersion(version);
		}
		this.materialManageDao.deleteEnterpriseBomManageByVersion(tempMaster
				.getPtNo(), tempVersion.getVersion());
	}

	/**
	 * 增加工厂BOM管理子件
	 * 
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 * @param list
	 *            是EnterpriseBomManage型，工厂BOM管理子件资料
	 */
	public void deleteEnterpriseBomDetail(TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion, List<EnterpriseBomManage> list) {
		this.materialManageDao.deleteAll(list);
		int iCount = this.materialManageDao.findEnterpriseBomDetailCount(
				tempMaster.getPtNo(), tempVersion.getVersion());
		if (iCount <= 0) {
			EnterpriseBomMaster master = new EnterpriseBomMaster();
			master.setPtNo(tempMaster.getPtNo());
			master.setPtName(tempMaster.getPtName());
			master.setPtSpec(tempMaster.getPtSpec());
			master.setCalUnitName(tempMaster.getCalUnitName());
			this.materialManageDao.saveEnterpriseBomMaster(master);
			EnterpriseBomVersion version = new EnterpriseBomVersion();
			version.setParentNo(tempMaster.getPtNo());
			version.setVersion(tempVersion.getVersion());
			version.setBeginDate(tempVersion.getBeginDate());
			version.setEndDate(tempVersion.getEndDate());
			this.materialManageDao.saveEnterpriseBomVersion(version);
		}
	}

	/**
	 * 在工厂物料主档里查找成品或是委外的半成品，但是不在报关常用工厂BOM成品里存在
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findTopProductBomManage() {
		List lsAll = this.materialManageDao.findTopProductBomManage();
		List lsInBomMaster = this.materialManageDao
				.findEnterpriseMaterialInBomMaster();
		for (int i = 0; i < lsInBomMaster.size(); i++) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) lsInBomMaster
					.get(i);
			int index = lsAll.indexOf(enterpriseMaterial);
			if (index >= 0) {
				lsAll.remove(index);
			}
		}
		return lsAll;
	}

	/**
	 * 根据版本号在工厂物料主档里查找成品或是委外的半成品，但是不在报关常用工厂BOM成品里存在
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findTopProductBomManage(Integer version) {
		List lsAll = this.materialManageDao.findTopProductBomManage();
		List lsTemp = this.materialManageDao
				.findEnterpriseMaterialInBomMaster(version);
		// List lsAllParentNo = this.materialManageDao
		// .findAllProductBomManageParentNo();
		List<String> lsInBomMaster = new ArrayList<String>();
		// List<String> lsConvertedAll = new ArrayList<String>();
		for (int i = 0; i < lsTemp.size(); i++) {
			lsInBomMaster.add(CommonUtils.dbc2sbc(lsTemp.get(i).toString()));
		}
		// for (int i = 0; i < lsAllParentNo.size(); i++) {
		// lsConvertedAll.add(CommonUtils.dbc2sbc(lsAllParentNo.get(i)
		// .toString()));
		// }
		for (int i = lsAll.size() - 1; i >= 0; i--) {
			// if (lsInBomMaster.size() <= 0) {
			// return lsAlEnterpriseMateriall;
			// }
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) lsAll
					.get(i);
			String ptNo = enterpriseMaterial.getPtNo().trim();
			ptNo = CommonUtils.dbc2sbc(ptNo);
			// if (!lsConvertedAll.contains(ptNo)) {
			// lsAll.remove(i);
			// continue;
			// }
			int index = lsInBomMaster.indexOf(ptNo);
			if (index >= 0) {
				lsAll.remove(i);
				lsInBomMaster.remove(index);
				continue;
			}
		}
		Collections.sort(lsAll, new MyComparator());
		return lsAll;
	}

	/**
	 * 内部类（对象比较）
	 * 
	 * @author Administrator
	 * 
	 */
	class MyComparator implements Comparator {
		public MyComparator() {
			super();
		}

		public int compare(Object o1, Object o2) {
			EnterpriseMaterial A = (EnterpriseMaterial) o1;
			EnterpriseMaterial B = (EnterpriseMaterial) o2;
			String strA = A.getPtNo();
			String strB = B.getPtNo();
			return (strA.compareTo(strB)) * (-1);
		}
	}

	/**
	 * 在工厂物料主档里查找成品或是委外的半成品，但是要在工厂BOM管理父件、报关常用工厂BOM成品里不同时存在的
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档里成品或是委外的半成品
	 */
	public List findTopProductBomManageForVersion() {
		List lsAll = this.materialManageDao.findTopProductBomManage();
		System.out.println("成品和委外半成品的个数是:" + lsAll.size());
		Map<String, List<Integer>> hmEnterpriseVersion = this.materialManageDao
				.findAllEnterpriseBomVersionCount();
		Map<String, List<Integer>> hmMaterialVersion = this.materialManageDao
				.findAllMaterielBomVersionCount();
		// List lsAllParentNo = this.materialManageDao
		// .findAllProductBomManageParentNo();
		// List<String> lsConvertedAll = new ArrayList<String>();
		// for (int i = 0; i < lsAllParentNo.size(); i++) {
		// lsConvertedAll.add(CommonUtils.dbc2sbc(lsAllParentNo.get(i)
		// .toString()));
		// }
		for (int i = lsAll.size() - 1; i >= 0; i--) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) lsAll
					.get(i);
			if (enterpriseMaterial.getPtNo() == null) {
				lsAll.remove(i);
				continue;
			}
			String ptNo = enterpriseMaterial.getPtNo().trim();
			ptNo = CommonUtils.dbc2sbc(ptNo);
			// if (!lsConvertedAll.contains(ptNo)) {
			// lsAll.remove(i);
			// continue;
			// }
			List<Integer> lsEnterpriseVersion = hmEnterpriseVersion.get(ptNo);
			List<Integer> lsMaterialVersion = hmMaterialVersion.get(ptNo);
			if (lsEnterpriseVersion == null || lsEnterpriseVersion.size() <= 0) {
				lsAll.remove(i);
				continue;
			}
			if (lsMaterialVersion == null || lsMaterialVersion.size() <= 0) {
				continue;
			}
			for (int j = lsEnterpriseVersion.size() - 1; j >= 0; j--) {
				if (lsMaterialVersion.contains(lsEnterpriseVersion.get(j))) {
					lsEnterpriseVersion.remove(j);
				}
			}
			if (lsEnterpriseVersion.size() <= 0) {
				lsAll.remove(i);
			}
		}
		System.out.println("未展BOM的成品和委外半成品的个数是:" + lsAll.size());
		return lsAll;
	}

	/**
	 * 查找工厂物料主档里成品或是委外的半成品，但是不在报关常用工厂物料里
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是EnterpriseMaterial型，工厂物料主档里成品或是委外的半成品
	 */
	public List findEnterpriseMaterialForMaterial(String materielType) {
		List lsEnterprise = this.materialManageDao
				.findEnterpriseMaterialByType(materielType);
		List lsCustoms = this.materialManageDao
				.findCustomsMaterialByType(materielType);
		for (int i = lsEnterprise.size() - 1; i >= 0; i--) {
			if (lsCustoms.size() <= 0) {
				return lsEnterprise;
			}
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) lsEnterprise
					.get(i);
			String ptNo = enterpriseMaterial.getPtNo();
			int index = lsCustoms.indexOf(ptNo);
			if (index >= 0) {
				lsCustoms.remove(index);
				lsEnterprise.remove(i);
			}
		}
		return lsEnterprise;
	}

	/**
	 * 取得工厂BOM管理子件最低一阶的料件信息
	 * 
	 * @param bomPath
	 *            各级BOM的调用路径
	 * @param parentNo
	 *            父件料号
	 * @param paramParent
	 *            临时的BOM参数
	 * @param hmBom
	 *            是HashMap<String, Object>型，key为料号，value为临时工厂BOM管理子件
	 * @param layers
	 *            BOM的层数
	 * @param isMerge
	 */
	private int getLastLayerMaterialNoVersionNoDate(String bomPath,
			String parentNo, TempBomParam paramParent,
			HashMap<String, Object> hmBom, int layers, boolean isMerge) {
		int subLayers = layers;
		if (subLayers >= 50) {
			System.out.println("BOM:" + bomPath + ":的阶数已经超出" + subLayers
					+ ",有死循环的可能");
			return -1;
		}
		// 报关常用工厂BOM主档
		MaterialBomMaster bomMaster = this.materialManageDao
				.findMaterielBomMasterByPtNo(parentNo);
		List<MaterialBomDetail> bomDetailList = null;
		// 工厂BOM管理子件信息
		List<EnterpriseBomManage> lsParentChild = new ArrayList();
		if (bomMaster != null) {
			System.out.println("+++++++++++已经存在此料号" + parentNo);
			bomDetailList = this.materialManageDao
					.findMaterielBomDetail(bomMaster);
		}
		if (bomDetailList != null && bomDetailList.size() != 0) {
			lsParentChild.clear();
			for (MaterialBomDetail item : bomDetailList) {
				EnterpriseBomManage tmp = new EnterpriseBomManage();
				tmp.setUnitWare(item.getUnitWaste());
				tmp.setUnitDosage(item.getUnitUsed());
				tmp.setCompentNo(item.getMateriel().getPtNo());
				tmp.setParentNo(bomMaster.getMateriel().getPtNo());
				lsParentChild.add(tmp);
			}
		} else {
			lsParentChild = this.materialManageDao
					.findChildBomByParent(parentNo);// Y040000001C0
		}
		if(!isMerge) {
			List<String> lsTemp = new ArrayList();
			for (int i = 0; i < lsParentChild.size(); i++) {
				EnterpriseBomManage item = lsParentChild.get(i);
				if (i == 0) {
					lsTemp.add(item.getCompentNo());
				} else {
					if (lsTemp.contains(item.getCompentNo())) {
						lsParentChild.remove(i);
						i--;
					} else {
						lsTemp.add(item.getCompentNo());
					}
				}
			}
			lsTemp.clear();
		}
		for (int i = 0; i < lsParentChild.size(); i++) {
			EnterpriseBomManage bomManage = (EnterpriseBomManage) lsParentChild
					.get(i);
			System.out.println("********************循环的子料件为"
					+ bomManage.getCompentNo());
			TempBomParam param = new TempBomParam();
			param.setUnitWare(CommonUtils.getDoubleExceptNull(paramParent
					.getUnitWare())
					* CommonUtils.getDoubleExceptNull(bomManage.getUnitWare()));
			param.setUnitDosage(CommonUtils.getDoubleExceptNull(paramParent
					.getUnitDosage())
					* CommonUtils
							.getDoubleExceptNull(bomManage.getUnitDosage()));
			param.setWare(CommonUtils.getDoubleExceptNull(paramParent
					.getWare())
					* CommonUtils
							.getDoubleExceptNull(bomManage.getWare()));
			
			int childCount = this.materialManageDao.findChildBomCount(bomManage
					.getCompentNo());
			if (childCount <= 0 || parentNo.equals(bomManage.getCompentNo())) {
				String key = bomManage.getCompentNo()
				+ (bomManage.getIsMainImg() == true ? "_1" : "");
				TempBomManage tempExist = (TempBomManage) hmBom.get(key);// + effectEndDate
				
				if (tempExist == null) {
					tempExist = new TempBomManage();
					tempExist.setPtNo(bomManage.getCompentNo());
					tempExist.setUnitWare(CommonUtils.getDoubleExceptNull(param
							.getUnitWare()));
					tempExist.setUnitDosage(CommonUtils
							.getDoubleExceptNull(param.getUnitDosage()));
					tempExist.setWare(CommonUtils
							.getDoubleExceptNull(param.getWare()));
					tempExist.setEffectDate(param.getEffectDate());
					tempExist.setEndDate(param.getEndDate());
					tempExist.setNotes(bomManage.getMemo());
					tempExist.setIsMainImg(bomManage.getIsMainImg());
				} else {
					int n = 6;
					tempExist.setUnitWare(CommonUtils.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(tempExist.getUnitWare())
							+ CommonUtils.getDoubleExceptNull(param
									.getUnitWare()), n));
					tempExist.setUnitDosage(CommonUtils.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(tempExist.getUnitDosage())
							+ CommonUtils.getDoubleExceptNull(param
									.getUnitDosage()), n));
					tempExist.setWare(CommonUtils.getDoubleByDigit(
							(tempExist.getUnitDosage() == 0 ? 0
									: ((tempExist.getUnitDosage() - tempExist
											.getUnitWare()) / tempExist
											.getUnitDosage())), n));
					tempExist.setEffectDate(param.getEffectDate());
					tempExist.setEndDate(param.getEndDate());
				}
				hmBom.put(key, tempExist);// +
				// effectEndDate
			} else {
				subLayers = layers + 1;
				System.out.println("=======准备循环第" + subLayers + "阶,被循环的料号为:"
						+ bomManage.getCompentNo());
				if (!bomPath.contains(bomManage.getCompentNo())) {
					String subBomPath = bomPath
							+ (";" + bomManage.getCompentNo());
					getLastLayerMaterialNoVersionNoDate(subBomPath, bomManage
							.getCompentNo(), param, hmBom, subLayers, isMerge);
				}
			}
		}
		return subLayers;
	}

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 无版本，无时间
	 * @param enterpriseMaterial
	 *            工厂物料主档资料
	 * @param version
	 *            父件版本号
	 */
	private void transferBomFromEnterpriseNoVersionNoDate(
			EnterpriseMaterial enterpriseMaterial, Integer version, boolean isMerge) {
		System.out.println("========运行到STEP 1");
		HashMap<String, Object> hmMaterial = new HashMap<String, Object>();
		HashMap<String, Object> hmBom = new HashMap<String, Object>();
		String parentNo = enterpriseMaterial.getPtNo();// list.get(i).toString();//
		MaterialBomMaster bomMaster = this.materialManageDao
				.findMaterielBomMasterByPtNo(parentNo);// = new
		// MaterialBomMaster();
		if (bomMaster == null) {
			bomMaster = new MaterialBomMaster();
			Materiel materiel = this.getMaterielForBom(parentNo, hmMaterial);
			if (materiel == null) {
				return;
			}
			bomMaster.setMateriel(materiel);
			bomMaster.setCompany(CommonUtils.getCompany());
			this.materialManageDao.saveMaterielBomMaster(bomMaster);
		}
		System.out.println("========运行到保存了表头");
		TempBomParam param = new TempBomParam();
		param.setUnitWare(1.0);
		param.setUnitDosage(1.0);
		param.setWare(1.0);
		param.setEffectDate(null);
		param.setEndDate(null);
		System.out.println("========进入获取子阶层");
		int layers = 0;
		layers = this.getLastLayerMaterialNoVersionNoDate(parentNo, parentNo,
				param, hmBom, layers, isMerge);
		System.out.println("===layersNoVersionNoDate==" + layers);
		if (layers == -1) {
			return;
		}

		HashMap<String, Object> hmVersionGroup = this.getBomVersionGroup(hmBom);
		Iterator key = hmVersionGroup.keySet().iterator();
		while (key.hasNext()) {
			String versionKey = key.next().toString();
			List lsVersionGroup = (List) hmVersionGroup.get(versionKey);
			if (lsVersionGroup.size() <= 0) {
				continue;
			}
			TempBomManage temp = (TempBomManage) lsVersionGroup.get(0);
			MaterialBomVersion bomVersion = new MaterialBomVersion();
			bomVersion.setBeginDate(CommonUtils.getBeginDate(temp
					.getEffectDate()));
			bomVersion.setEndDate(CommonUtils.getEndDate(temp.getEndDate()));
			bomVersion.setVersion(version);
			bomVersion.setMaster(bomMaster);
			bomVersion.setNotes(temp.getNotes());
			this.materialManageDao.saveMaterielBomVersion(bomVersion);
			// version++;
			for (int j = 0; j < lsVersionGroup.size(); j++) {
				temp = (TempBomManage) lsVersionGroup.get(j);
				Materiel detail = this.getMaterielForBom(temp.getPtNo().trim(),
						hmMaterial);
				if (detail == null) {
					continue;
				}
				MaterialBomDetail bomDetail = new MaterialBomDetail();
				bomDetail.setVersion(bomVersion);
				bomDetail.setMateriel(detail);
				double unitware = 0.0;
				double unitDosage = 0.0;
				double ware = 0.0;
				if (layers > 0) {
					CompanyOther other = CommonUtils.getOther();
					// 保留小数位
					Integer d = 6;
					if (other != null) {
						d = (other.getBomChangeBit() == null) ? Integer
								.valueOf(0) : other.getBomChangeBit();
					}
					unitware = CommonUtils.getDoubleByDigit(temp.getUnitWare(),
							d);
					unitDosage = CommonUtils.getDoubleByDigit(temp
							.getUnitDosage(), d);
					ware = CommonUtils.getDoubleByDigit((unitDosage == 0 ? 0
							: ((unitDosage - unitware) / unitDosage)), d);
				} else if (layers == 0) {
					unitware = temp.getUnitWare();
					unitDosage = temp.getUnitDosage();
					ware = temp.getWare();
				}

				// 设置小数位
				bomDetail.setUnitWaste(unitware);
				bomDetail.setUnitUsed(unitDosage);
				bomDetail.setWaste(ware);
				this.materialManageDao.saveMaterielBomDetail(bomDetail);
			}
		}
	}

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @param version
	 *            父件版本号
	 * @param isMerge 
	 * 
	 */
	public void transferBomFromEnterpriseNoVersionNoDate(List list,
			Integer version, boolean isMerge) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
					.get(i);
			System.out.println("=======第" + i + "个" + "：料号为:"
					+ enterpriseMaterial.getPtNo());
			;
			this.transferBomFromEnterpriseNoVersionNoDate(enterpriseMaterial,
					version, isMerge);
		}
	}

	/**
	 * 取得工厂BOM管理里最低一阶的料件信息
	 * 
	 * @param bomPath
	 *            各级BOM的调用路径
	 * @param parentNo
	 *            父件料号
	 * @param paramParent
	 *            临时的BOM参数
	 * @param hmBom
	 *            是HashMap<String, Object>型，key为料号，value为临时工厂BOM管理子件
	 * @param layers
	 *            BOM的层数
	 */
	private int getLastLayerMaterialByDate(String bomPath, String parentNo,
			TempBomParam paramParent, HashMap<String, Object> hmBom, int layers) {
		int subLayers = layers;
		if (subLayers >= 50) {
			System.out.println("BOM:" + bomPath + ":的阶数已经超出" + subLayers
					+ ",有死循环的可能");
			return -1;
		}
		List lsParentChild = this.materialManageDao.findChildBomByParent(parentNo);
		for (int i = 0; i < lsParentChild.size(); i++) {
			EnterpriseBomManage bomManage = (EnterpriseBomManage) lsParentChild
					.get(i);
			TempBomParam param = new TempBomParam();
			param.setUnitWare(CommonUtils.getDoubleExceptNull(paramParent
					.getUnitWare())
					* CommonUtils.getDoubleExceptNull(bomManage.getUnitWare()));
			param.setUnitDosage(CommonUtils.getDoubleExceptNull(paramParent
					.getUnitDosage())
					* CommonUtils
							.getDoubleExceptNull(bomManage.getUnitDosage()));
			param.setWare(CommonUtils.getDoubleExceptNull(paramParent
					.getWare())
					* CommonUtils
							.getDoubleExceptNull(bomManage.getWare()));
			
			if (paramParent.getEffectDate() == null
					&& bomManage.getEffectDate() != null) {
				param.setEffectDate(bomManage.getEffectDate());
			} else if (paramParent.getEffectDate() != null
					&& bomManage.getEffectDate() == null) {
				param.setEffectDate(paramParent.getEffectDate());
			} else if (paramParent.getEffectDate() != null
					&& bomManage.getEffectDate() != null) {
				if (DateFormatUtils.format(paramParent.getEffectDate(),
						"yyyy-MM-dd").compareTo(
						DateFormatUtils.format(bomManage.getEffectDate(),
								"yyyy-MM-dd")) < 0) {
					param.setEffectDate(bomManage.getEffectDate());
				} else {
					param.setEffectDate(paramParent.getEffectDate());
				}
			}
			if (paramParent.getEndDate() == null
					&& bomManage.getEndDate() != null) {
				param.setEndDate(bomManage.getEndDate());
			} else if (paramParent.getEndDate() != null
					&& bomManage.getEndDate() == null) {
				param.setEndDate(paramParent.getEndDate());
			} else if (paramParent.getEndDate() != null
					&& bomManage.getEndDate() != null) {
				if (DateFormatUtils.format(paramParent.getEndDate(),
						"yyyy-MM-dd").compareTo(
						DateFormatUtils.format(bomManage.getEndDate(),
								"yyyy-MM-dd")) > 0) {
					param.setEndDate(bomManage.getEndDate());
				} else {
					param.setEndDate(paramParent.getEndDate());
				}
			}
			if (param.getEndDate() != null
					&& param.getEffectDate() != null
					&& DateFormatUtils.format(param.getEndDate(), "yyyy-MM-dd")
							.compareTo(
									DateFormatUtils.format(param
											.getEffectDate(), "yyyy-MM-dd")) < 0) {
				continue;
			}
			int childCount = this.materialManageDao.findChildBomCount(bomManage
					.getCompentNo());
			if (childCount <= 0) {
				String effectEndDate = (param.getEffectDate() == null ? "[]"
						: DateFormatUtils.format(param.getEffectDate(),
								"yyyy-MM-dd"))
						+ (param.getEndDate() == null ? "[]" : DateFormatUtils
								.format(param.getEndDate(), "yyyy-MM-dd"));
				TempBomManage tempExist = (TempBomManage) hmBom.get(bomManage
						.getCompentNo()
						+ effectEndDate);
				if (tempExist == null) {
					tempExist = new TempBomManage();
					tempExist.setPtNo(bomManage.getCompentNo());
					tempExist.setUnitWare(CommonUtils.getDoubleExceptNull(param
							.getUnitWare()));
					tempExist.setUnitDosage(CommonUtils
							.getDoubleExceptNull(param.getUnitDosage()));
					tempExist.setWare(CommonUtils
							.getDoubleExceptNull(param.getWare()));
					tempExist.setEffectDate(param.getEffectDate());
					tempExist.setEndDate(param.getEndDate());
					tempExist.setNotes(bomManage.getMemo());
				} else {
					int n = 6;
					tempExist.setUnitWare(CommonUtils.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(tempExist.getUnitWare())
							+ CommonUtils.getDoubleExceptNull(param
									.getUnitWare()), n));
					tempExist.setUnitDosage(CommonUtils.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(tempExist.getUnitDosage())
							+ CommonUtils.getDoubleExceptNull(param
									.getUnitDosage()), n));
					tempExist.setWare(CommonUtils.getDoubleByDigit(
							(tempExist.getUnitDosage() == 0 ? 0
									: ((tempExist.getUnitDosage() - tempExist
											.getUnitWare()) / tempExist
											.getUnitDosage())), n));
					tempExist.setEffectDate(param.getEffectDate());
					tempExist.setEndDate(param.getEndDate());
				}
				System.out.println(bomManage.getCompentNo() + "    "
						+ effectEndDate);
				hmBom.put(bomManage.getCompentNo() + effectEndDate, tempExist);
			} else {
				subLayers= layers + 1;
				String subBomPath = bomPath + (";" + bomManage.getCompentNo());
				getLastLayerMaterialByDate(subBomPath,
						bomManage.getCompentNo(), param, hmBom, subLayers);
			}
		}
		return subLayers;
	}

	/**
	 * 根据日期区分版本
	 * 
	 * @param hm
	 *            是HashMap<String, Object>型，key为料号，value为临时工厂BOM管理子件
	 * @return HashMap<String, Object> key为料号，value为临时工厂BOM管理子件
	 */
	private HashMap<String, Object> seperateVersionByDate(
			HashMap<String, Object> hm) {
		HashMap<String, Object> hmBom = new HashMap<String, Object>();
		Iterator key = hm.keySet().iterator();
		while (key.hasNext()) {
			String vKey = key.next().toString();
			List lsGroup = (List) hm.get(vKey);
			if (lsGroup.size() <= 0) {
				continue;
			}
			TempBomManage temp = (TempBomManage) lsGroup.get(0);
			Iterator subKey = hm.keySet().iterator();
			while (subKey.hasNext()) {
				String subVKey = key.next().toString();
				List lsSubGroup = (List) hm.get(subVKey);
				if (lsSubGroup.size() <= 0) {
					continue;
				}
				TempBomManage subTemp = (TempBomManage) lsSubGroup.get(0);
			}
		}
		return hmBom;
	}

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param enterpriseMaterial
	 *            工厂物料主档资料
	 */
	private void transferBomFromEnterpriseByDate(
			EnterpriseMaterial enterpriseMaterial) {
		HashMap<String, Object> hmMaterial = new HashMap<String, Object>();
		HashMap<String, Object> hmBom = new HashMap<String, Object>();
		String parentNo = enterpriseMaterial.getPtNo();// list.get(i).toString();//
		MaterialBomMaster bomMaster = this.materialManageDao
				.findMaterielBomMasterByPtNo(parentNo);// new
		if (bomMaster == null) {
			bomMaster = new MaterialBomMaster();
			Materiel materiel = this.getMaterielForBom(parentNo, hmMaterial);
			if (materiel == null) {
				return;
			}
			bomMaster.setMateriel(materiel);
			bomMaster.setCompany(CommonUtils.getCompany());
			this.materialManageDao.saveMaterielBomMaster(bomMaster);
		}
		TempBomParam param = new TempBomParam();
		param.setUnitWare(1.0);
		param.setWare(1.0);
		param.setUnitDosage(1.0);
		param.setEffectDate(null);
		param.setEndDate(null);
		int layers = 0;
		layers = this.getLastLayerMaterialByDate(parentNo, parentNo, param,
				hmBom, layers);
		System.out.println("===layersByDate==" + layers);
		if (layers == -1) {
			return;
		}

		HashMap<String, Object> hmVersionGroup = this.getBomVersionGroup(hmBom);
		Iterator key = hmVersionGroup.keySet().iterator();
		Integer version = this.materialManageDao
				.findMaxMaterielBomVersion(parentNo);
		while (key.hasNext()) {
			String versionKey = key.next().toString();
			List lsVersionGroup = (List) hmVersionGroup.get(versionKey);
			if (lsVersionGroup.size() <= 0) {
				continue;
			}
			TempBomManage temp = (TempBomManage) lsVersionGroup.get(0);
			MaterialBomVersion bomVersion = new MaterialBomVersion();
			bomVersion.setBeginDate(CommonUtils.getBeginDate(temp
					.getEffectDate()));
			bomVersion.setEndDate(CommonUtils.getEndDate(temp.getEndDate()));
			bomVersion.setVersion(version);
			bomVersion.setMaster(bomMaster);
			bomVersion.setNotes(temp.getNotes());
			this.materialManageDao.saveMaterielBomVersion(bomVersion);
			version++;
			for (int j = 0; j < lsVersionGroup.size(); j++) {
				temp = (TempBomManage) lsVersionGroup.get(j);
				Materiel detail = this.getMaterielForBom(temp.getPtNo().trim(),
						hmMaterial);
				if (detail == null) {
					continue;
				}
				MaterialBomDetail bomDetail = new MaterialBomDetail();
				bomDetail.setVersion(bomVersion);
				bomDetail.setMateriel(detail);
				double unitware = 0.0;
				double unitDosage = 0.0;
				double ware = 0.0;
				if (layers > 0) {
					CompanyOther other = CommonUtils.getOther();
					// 保留小数位
					Integer d = 6;
					if (other != null) {
						d = (other.getBomChangeBit() == null) ? Integer
								.valueOf(0) : other.getBomChangeBit();
					}
					unitware = CommonUtils.getDoubleByDigit(temp.getUnitWare(),
							d);
					unitDosage = CommonUtils.getDoubleByDigit(temp
							.getUnitDosage(), d);
					ware = CommonUtils.getDoubleByDigit((unitDosage == 0 ? 0
							: ((unitDosage - unitware) / unitDosage)), d);
				} else if (layers == 0) {
					unitware = temp.getUnitWare();
					unitDosage = temp.getUnitDosage();
					ware = temp.getWare();
				}

				// 设置小数位
				bomDetail.setUnitWaste(unitware);
				bomDetail.setUnitUsed(unitDosage);
				bomDetail.setWaste(ware);
				this.materialManageDao.saveMaterielBomDetail(bomDetail);
			}
		}
	}

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 */
	public void transferBomFromEnterpriseByDate(List list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
					.get(i);
			this.transferBomFromEnterpriseByDate(enterpriseMaterial);
		}
	}

	/**
	 * 取得工厂BOM管理最低一阶的料件信息
	 * 
	 * @param bomPath
	 *            各级BOM的调用路径
	 * @param parentNo
	 *            父件料号
	 * @param versionNo
	 *            版本号
	 * @param paramParent
	 *            临时的BOM参数
	 * @param hmBom
	 *            是HashMap<String, Object>型，key为料号，value为临时工厂BOM管理子件
	 * @param layers
	 *            BOM的层数
	 */
	private int getLastLayerMaterialByVersion(String bomPath, String parentNo,
			String versionNo, TempBomParam paramParent,
			HashMap<String, Object> hmBom, int layers) {
		int subLayers = layers;
		if (subLayers >= 50) {
			System.out.println("BOM:" + bomPath + ":的阶数已经超出" + subLayers
					+ ",有死循环的可能");
			return -1;
		}
		List lsParentChild = this.materialManageDao.findChildBomByParent(
				parentNo, versionNo);
		for (int i = 0; i < lsParentChild.size(); i++) {
			EnterpriseBomManage bomManage = (EnterpriseBomManage) lsParentChild
					.get(i);
			TempBomParam param = new TempBomParam();
			param.setUnitWare(CommonUtils.getDoubleExceptNull(paramParent
					.getUnitWare())
					* CommonUtils.getDoubleExceptNull(bomManage.getUnitWare()));
			param.setUnitDosage(CommonUtils.getDoubleExceptNull(paramParent
					.getUnitDosage())
					* CommonUtils
							.getDoubleExceptNull(bomManage.getUnitDosage()));
			param.setWare(CommonUtils
					.getDoubleExceptNull(paramParent.getWare())
					* CommonUtils.getDoubleExceptNull(bomManage.getWare()));
			int childCount = this.materialManageDao.findChildBomCount(bomManage
					.getCompentNo());
			if (childCount <= 0) {
				TempBomManage tempExist = (TempBomManage) hmBom.get(bomManage
						.getCompentNo());
				if (tempExist == null) {
					tempExist = new TempBomManage();
					tempExist.setPtNo(bomManage.getCompentNo());
					tempExist.setUnitWare(CommonUtils.getDoubleExceptNull(param
							.getUnitWare()));
					tempExist.setUnitDosage(CommonUtils
							.getDoubleExceptNull(param.getUnitDosage()));

					tempExist.setWare(CommonUtils.getDoubleExceptNull(param
							.getWare()));

					tempExist.setEffectDate(param.getEffectDate());
					tempExist.setEndDate(param.getEndDate());
					tempExist.setNotes(bomManage.getMemo());
				} else {
					tempExist.setUnitWare(CommonUtils
							.getDoubleExceptNull(tempExist.getUnitWare())
							+ CommonUtils.getDoubleExceptNull(param
									.getUnitWare()));
					tempExist.setUnitDosage(CommonUtils
							.getDoubleExceptNull(tempExist.getUnitDosage())
							+ CommonUtils.getDoubleExceptNull(param
									.getUnitDosage()));
					double tmpUnitWare = CommonUtils.getDoubleExceptNull(tempExist.getUnitWare());
					double tmpUnitDosage = CommonUtils.getDoubleExceptNull(tempExist.getUnitDosage());

					// 按照损耗计算公式 损耗率合并公式：1-（单耗1+单耗2+单耗n+...）/（单项用量1+单项用量2+单项用量n+…）
					double tmpWare = 1.0 - tmpUnitWare / tmpUnitDosage;
					
					tempExist.setWare(tmpWare);
					tempExist.setEffectDate(param.getEffectDate());
					tempExist.setEndDate(param.getEndDate());
				}
				hmBom.put(bomManage.getCompentNo(), tempExist);// +
			} else {
				subLayers = layers + 1;
				String subBomPath = bomPath + (";" + bomManage.getCompentNo());
				getLastLayerMaterialByVersion(subBomPath, bomManage
						.getCompentNo(), bomManage.getChildVersionNo(), param,
						hmBom, subLayers);
			}
		}
		return subLayers;
	}

	/**
	 * 根据父级料号返回工厂BOM管理里的父级版本号，但是在报关常用工厂BOM版本里不存在
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 工厂BOM管理里的版本号
	 */
	private List getNotTransferBomVersion(String ptNo) {
		List lsEnterpriseVersion = this.materialManageDao
				.findEnterpriseBomManageVersion(ptNo);
		List lsMaterialVersion = this.materialManageDao
				.findMaterielBomVersionByPtNo(ptNo);// 报关常用工厂BOM的版本
		for (int i = lsEnterpriseVersion.size() - 1; i >= 0; i--) {
			if (lsMaterialVersion.size() <= 0) {
				return lsEnterpriseVersion;
			}
			Integer version = Integer.valueOf(lsEnterpriseVersion.get(i)
					.toString());
			int index = lsMaterialVersion.indexOf(version);
			if (index >= 0) {
				lsEnterpriseVersion.remove(i);
				lsMaterialVersion.remove(index);
			}
		}
		return lsEnterpriseVersion;
	}

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param enterpriseMaterial
	 *            工厂物料主档资料
	 */
	private void transferBomFromEnterpriseByVersion(
			EnterpriseMaterial enterpriseMaterial) {
		HashMap<String, Object> hmMaterial = new HashMap<String, Object>();
		HashMap<String, Object> hmBom = new HashMap<String, Object>();
		String parentNo = enterpriseMaterial.getPtNo();// list.get(i).toString();//
		MaterialBomMaster bomMaster = this.materialManageDao
				.findMaterielBomMasterByPtNo(parentNo);
		// 增加报关常用工厂BOM的表头资料
		if (bomMaster == null) {
			bomMaster = new MaterialBomMaster();
			Materiel materiel = this.getMaterielForBom(parentNo, hmMaterial);
			if (materiel == null) {
				return;
			}
			bomMaster.setMateriel(materiel);
			bomMaster.setCompany(CommonUtils.getCompany());
			this.materialManageDao.saveMaterielBomMaster(bomMaster);
		}
		TempBomParam param = new TempBomParam();
		param.setWare(1.0);
		param.setUnitWare(1.0);
		param.setUnitDosage(1.0);
		param.setEffectDate(null);
		param.setEndDate(null);
		// ===开始增加报关常用工厂BOM的表体也就是版本与单损耗
		List lsEnterpriseBomManage = this.materialManageDao
				.findEnterpriseBomDetail(parentNo);
		Map<String, Date> hmBeginDate = new HashMap<String, Date>();
		Map<String, Date> hmEndDate = new HashMap<String, Date>();
		Map<String, String> hmNotes = new HashMap<String, String>();
		for (int i = 0; i < lsEnterpriseBomManage.size(); i++) {
			EnterpriseBomManage enterpriseBomManage = (EnterpriseBomManage) lsEnterpriseBomManage
					.get(i);
			hmBeginDate.put(enterpriseBomManage.getVersionNo(),
					enterpriseBomManage.getEffectDate());
			hmEndDate.put(enterpriseBomManage.getVersionNo(),
					enterpriseBomManage.getEndDate());
			hmNotes.put(enterpriseBomManage.getVersionNo(), enterpriseBomManage
					.getMemo());
		}
		List lsVersion = this.getNotTransferBomVersion(parentNo);
		for (int i = 0; i < lsVersion.size(); i++) {
			if (lsVersion.get(i) == null) {
				continue;
			}
			Integer version = Integer.valueOf(lsVersion.get(i).toString());
			hmBom.clear();
			int layers = 0;
			layers = this.getLastLayerMaterialByVersion(parentNo, parentNo,
					version.toString(), param, hmBom, layers);
			System.out.println("===layersByVersion==" + layers);
			if (layers == -1) {
				continue;
			}
			// ========保存报关常用工厂Bom的版本号
			MaterialBomVersion bomVersion = new MaterialBomVersion();
			bomVersion.setBeginDate(hmBeginDate.get(version.toString()));
			bomVersion.setEndDate(hmEndDate.get(version.toString()));
			bomVersion.setVersion(version);
			bomVersion.setMaster(bomMaster);
			bomVersion.setNotes(hmNotes.get(version.toString()));
			this.materialManageDao.saveMaterielBomVersion(bomVersion);

			Iterator key = hmBom.keySet().iterator();
			while (key.hasNext()) {
				String ptNoKey = key.next().toString();
				TempBomManage temp = (TempBomManage) hmBom.get(ptNoKey);
				Materiel detail = this.getMaterielForBom(temp.getPtNo().trim(),
						hmMaterial);
				if (detail == null) {
					continue;
				}
				MaterialBomDetail bomDetail = new MaterialBomDetail();
				bomDetail.setVersion(bomVersion);
				bomDetail.setMateriel(detail);
				double unitware = 0.0;
				double unitDosage = 0.0;
				double ware = 0.0;
				if (layers > 0) {
					CompanyOther other = CommonUtils.getOther();
					// 保留小数位
					Integer d = 6;
					if (other != null) {
						d = (other.getBomChangeBit() == null) ? Integer
								.valueOf(0) : other.getBomChangeBit();
					}
					unitware = CommonUtils.getDoubleByDigit(temp.getUnitWare(),
							d);
					unitDosage = CommonUtils.getDoubleByDigit(temp
							.getUnitDosage(), d);
					ware = CommonUtils.getDoubleByDigit((unitDosage == 0 ? 0
							: ((unitDosage - unitware) / unitDosage)), d);
				} else if (layers == 0) {
					unitware = temp.getUnitWare();
					unitDosage = temp.getUnitDosage();
					ware = temp.getWare();
				}
				// 设置小数位
				bomDetail.setUnitWaste(unitware);
				bomDetail.setUnitUsed(unitDosage);
				bomDetail.setWaste(ware);
				this.materialManageDao.saveMaterielBomDetail(bomDetail);
			}
		}
	}

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 */
	public void transferBomFromEnterpriseByVersion(List list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) list
					.get(i);
			this.transferBomFromEnterpriseByVersion(enterpriseMaterial);
		}
	}

	/**
	 * 将计算得到的最底层料件,按照生效日期和结束日期分组
	 * 
	 * @param hmBom
	 *            是HashMap<String, Object>型，key为料号，value为临时工厂BOM管理子件
	 * @return HashMap<String, Object> key为料号，value为临时工厂BOM管理子件
	 */
	private HashMap<String, Object> getBomVersionGroup(
			HashMap<String, Object> hmBom) {
		HashMap<String, Object> hmVersionGroup = new HashMap<String, Object>();
		Iterator key = hmBom.keySet().iterator();
		while (key.hasNext()) {
			TempBomManage temp = (TempBomManage) hmBom.get(key.next()
					.toString());
			System.out.println("ssssssss  " + temp.getEffectEndDate());
			List list = (List) hmVersionGroup.get(temp.getEffectEndDate());
			if (list == null) {
				list = new ArrayList();
				hmVersionGroup.put(temp.getEffectEndDate(), list);
			}
			list.add(temp);
		}
		return hmVersionGroup;
	}

	/**
	 * 根据料号确定报关常用工厂物料资料
	 * 
	 * @param ptNo
	 *            料号
	 * @param hmMaterial
	 *            是HashMap<String, Object>型，key为料号，value为报关常用工厂物料
	 * @return Materiel 报关常用工厂物料
	 */
	private Materiel getMaterielForBom(String ptNo,
			HashMap<String, Object> hmMaterial) {
		Materiel materiel = (Materiel) hmMaterial.get(ptNo);
		if (materiel == null) {
			materiel = this.materialManageDao.findMaterialByPtNo(ptNo);
		}
		if (materiel == null) {
			materiel = new Materiel();
			EnterpriseMaterial enterpriseMaterial = this.materialManageDao
					.findEnterpriseMaterialByPtNo(ptNo);
			if (enterpriseMaterial == null) {
				return null;
			}
			try {
				PropertyUtils.copyProperties(materiel, enterpriseMaterial);
			} catch (Exception e) {
				e.printStackTrace();
			}
			materiel.setId(null);
			this.materialManageDao.saveMateriel(materiel);
		}
		hmMaterial.put(ptNo, materiel);
		return materiel;
	}

	/**
	 * 根据给定的工厂物料主档资料，在报关常用工厂物料里新增资料
	 * 
	 * @param lsMateriel
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	public List addCustomMaterial(List lsMateriel) {
		List lsResult = new ArrayList();
		for (int i = 0; i < lsMateriel.size(); i++) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) lsMateriel
					.get(i);
			Materiel materiel = new Materiel();
			try {
				PropertyUtils.copyProperties(materiel, enterpriseMaterial);
			} catch (Exception e) {
				e.printStackTrace();
			}
			materiel.setId(null);
			materiel.setIsNewMateriel(new Boolean(true));
			// customMaterial.setStateMark(DzscState.Original);
			this.materialManageDao.saveMateriel(materiel);
			lsResult.add(materiel);
		}
		return lsResult;
	}

	/**
	 * 根据报关常用工厂物料保存报关常用工厂BOM成品资料
	 * 
	 * @param lsCustomMaterial
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List saveMaterialBomMaster(List lsCustomMaterial) {
		List lsResult = new ArrayList();
		if (lsCustomMaterial == null) {
			return lsResult;
		}
		for (int i = 0; i < lsCustomMaterial.size(); i++) {
			MaterialBomMaster master = new MaterialBomMaster();
			Materiel material = (Materiel) lsCustomMaterial.get(i);
			master.setMateriel(material);
			master.setCompany(CommonUtils.getCompany());
			master.setStateMark(DzscState.ORIGINAL);
			this.materialManageDao.saveMaterielBomMaster(master);
			lsResult.add(master);
		}
		return lsResult;
	}

	/**
	 * 根据报关常用工厂BOM版本、报关常用工厂物料保存报关常用工厂BOM成品资料
	 * 
	 * @param version
	 *            父件版本号
	 * @param lsCustomDetail
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件资料
	 */
	public List saveMaterialBomDetail(MaterialBomVersion version,
			List lsCustomDetail) {
		List lsResult = new ArrayList();
		for (int i = 0; i < lsCustomDetail.size(); i++) {
			MaterialBomDetail detail = new MaterialBomDetail();
			Materiel material = (Materiel) lsCustomDetail.get(i);
			detail.setVersion(version);
			detail.setMateriel(material);
			detail.setCompany(CommonUtils.getCompany());
			this.materialManageDao.saveMaterielBomDetail(detail);
			lsResult.add(detail);
		}
		return lsResult;
	}

	/**
	 * 保存报关常用工厂BOM成品资料
	 * 
	 * @param materielBomMaster
	 *            报关常用工厂BOM成品资料
	 */
	public void deleteMaterielBomMaster(MaterialBomMaster materielBomMaster) {
		List list = this.materialManageDao
				.findMaterielBomDetail(materielBomMaster);
		this.materialManageDao.deleteAll(list);
		list = this.materialManageDao.findMaterielBomVersion(materielBomMaster);
		this.materialManageDao.deleteAll(list);
		this.materialManageDao.deleteMaterielBomMaster(materielBomMaster);
	}

	/**
	 * 根据报关常用工厂BOM版本删除报关常用工厂BOM成品资料
	 * 
	 * @param version
	 *            父件版本号
	 */
	public void deleteMaterielBomVersion(MaterialBomVersion version) {
		List list = this.materialManageDao.findMaterielBomDetail(version);
		this.materialManageDao.deleteAll(list);
		this.materialManageDao.deleteMaterielBomVersion(version);
	}

	/**
	 * 批量更新报关常用工厂BOM料件的损耗率
	 * 
	 * @param taskId
	 *            任务Id
	 * @param versionNo
	 *            版本号
	 * @param pFourCode
	 *            成品四码
	 * @param mFourCode
	 *            料件四码
	 * @param waste
	 *            损耗率
	 * @param isByUnitUsed
	 *            true为更新单耗，false为更新单项用量
	 */
	public void updateMaterialBomWaste(String taskId, Integer versionNo,
			String pFourCode, String mFourCode, Double waste,
			boolean isByUnitUsed) {
		List lsPMateriel = this.materialManageDao
				.findInnerMergeByFourNo(pFourCode);
		List lsMMateriel = this.materialManageDao
				.findInnerMergeByFourNo(mFourCode);
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(lsPMateriel.size());
			info.setMethodName("正在更新单耗");
		}
		for (int i = 0; i < lsPMateriel.size(); i++) {
			Materiel pm = (Materiel) lsPMateriel.get(i);
			if (info != null) {
				info.setMethodName("正在更新成品" + pm.getPtNo() + "的单耗");
			}
			List lsBomDetail = this.materialManageDao.findMaterielBomDetail(pm,
					versionNo);
			for (int j = 0; j < lsBomDetail.size(); j++) {
				MaterialBomDetail detail = (MaterialBomDetail) lsBomDetail
						.get(j);
				if (lsMMateriel.contains(detail.getMateriel())) {
					detail.setWaste(waste);
					if (isByUnitUsed) {
						detail.setUnitWaste(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(detail
										.getUnitUsed())
										* (1 - detail.getWaste()), 6));
					} else {
						detail.setUnitUsed(CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(detail
										.getUnitWaste())
										/ (1 - detail.getWaste()), 6));
					}
					this.materialManageDao.saveMaterielBomDetail(detail);
				}
			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
	}

	/**
	 * 根据物料类型自动初始化报关常用工厂物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param number
	 *            要初始化的个数
	 */
	public void initMateriel(String materielType, int number) {
		ScmCoi scmCoi = this.materialManageDao
				.findScmCoiByProperty(materielType);
		if (scmCoi == null) {
			return;
		}
		for (int i = 0; i < number; i++) {
			Materiel materiel = new Materiel();
			materiel.setScmCoi(scmCoi);
			materiel.setPtNo(scmCoi.getName()
					+ CommonUtils.convertIntToStringByLength(i, 7));
			materiel.setFactoryName(materiel.getPtNo());
			this.materialManageDao.saveMateriel(materiel);
		}
	}

	/**
	 * 自动初始化报关常用工厂BOM料件资料
	 */
	public void initBom() {
		List lsMaster = this.materialManageDao
				.findMaterialByCoiProperty(MaterielType.FINISHED_PRODUCT);
		List lsDetail = this.materialManageDao
				.findMaterialByCoiProperty(MaterielType.MATERIEL);
		int m = 0;
		for (int i = 0; i < lsMaster.size(); i++) {
			Materiel materiel = (Materiel) lsMaster.get(i);
			MaterialBomMaster master = new MaterialBomMaster();
			master.setMateriel(materiel);
			this.materialManageDao.saveMaterielBomMaster(master);
			MaterialBomVersion version = new MaterialBomVersion();
			version.setMaster(master);
			version.setVersion(0);
			this.materialManageDao.saveMaterielBomVersion(version);
			for (int j = m; j < m + 100; j++) {
				if (j == lsDetail.size() - 1) {
					j = j - m;
					m = 0;
				}
				Materiel mDetail = (Materiel) lsDetail.get(j);
				MaterialBomDetail detail = new MaterialBomDetail();
				detail.setVersion(version);
				detail.setMateriel(mDetail);
				detail.setUnitUsed(2.0);
				detail.setWaste(0.1);
				detail.setUnitWaste(1.8);
				this.materialManageDao.saveMaterielBomDetail(detail);
			}
			m += 100;
		}
	}

	/**
	 * getMaterielRefEntityClassUpdateString
	 * 
	 * @return List 是String型，更新的SQL语句
	 */
	private List getMaterielRefEntityClassUpdateString() {
		List lsResult = new ArrayList<Class>();
		List lsAllClass = new ArrayList();
		lsAllClass.addAll(this.materialManageDao.getSessionFactory()
				.getAllClassMetadata().values());
		for (int i = 0; i < lsAllClass.size(); i++) {
			ClassMetadata classMetadata = (ClassMetadata) lsAllClass.get(i);
			Type[] propertyTypes = classMetadata.getPropertyTypes();
			String[] propertyNames = classMetadata.getPropertyNames();
			int propertyNum = propertyTypes.length;
			for (int j = 0; j < propertyNum; j++) {
				Type propertyType = propertyTypes[j];
				if (propertyType.getReturnedClass().equals(Materiel.class)) {
					String updateStr = " update "
							+ classMetadata.getMappedClass(EntityMode.POJO)
									.getName() + " set " + propertyNames[j]
							+ "=? where " + propertyNames[j]
							+ "=? and company=? ";
					System.out.println(updateStr);
					lsResult.add(updateStr);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 用给定的报关常用工厂物料更新报关常用工厂物料
	 * 
	 * @param list
	 *            放了多组SQL语句
	 * @param sour
	 *            要更新的报关常用工厂物料
	 * @param dest
	 *            报关常用工厂物料
	 */
	private void updateMaterielByOtherMateriel(List list, Materiel sour,
			Materiel dest) {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(sour);
		parameters.add(dest);
		parameters.add(CommonUtils.getCompany());
		for (int i = 0; i < list.size(); i++) {
			String hsql = list.get(i).toString();
			System.out.println(hsql);
			this.materialManageDao.batchUpdateOrDelete(hsql, parameters
					.toArray());
		}
	}

	/**
	 * 根据料号查找重复的报关常用工厂物料资料
	 * 
	 * @return List 是TempRepeatMateriel型，临时重复的报关常用工厂物料资料
	 */
	public List findRepeatMaterial() { // 物料主档查找是否重复
		List lsResult = new ArrayList();
		List list = this.materialManageDao.findRepeatMaterial();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null && !"".equals(objs[0].toString().trim())) {
				TempRepeatMateriel temp = new TempRepeatMateriel();
				temp.setPtNo(objs[0].toString());
				lsResult.add(temp);
			} else {
				throw new RuntimeException("在报关常用物料中存在料号为空的记录！");
			}
		}
		return lsResult;
	}

	/**
	 * 删除重复的报关常用工厂物料资料
	 * 
	 * @param list
	 *            是TempRepeatMateriel型，临时重复的报关常用工厂物料资料
	 */
	public void deleteRepeatMateriel(List list) {
		List lsUpdateString = this.getMaterielRefEntityClassUpdateString();
		for (int i = 0; i < list.size(); i++) {
			TempRepeatMateriel temp = (TempRepeatMateriel) list.get(i);
			List lsMateriel = this.materialManageDao
					.findRepeatMaterialByPtNo(temp.getPtNo());
			if (lsMateriel.size() < 2) {
				continue;
			}
			Materiel sour = (Materiel) lsMateriel.get(0);
			for (int j = 1; j < lsMateriel.size(); j++) {
				Materiel dest = (Materiel) lsMateriel.get(j);
				this.updateMaterielByOtherMateriel(lsUpdateString, sour, dest);
				this.materialManageDao.delete(dest);
			}
		}
	}

	/**
	 * 保存工厂物料主档资料
	 * 
	 * @param materiel
	 *            工厂物料主档资料
	 */
	public void saveEnterpriseMaterial(EnterpriseMaterial materiel) {
		if (materiel.getId() != null && !"".equals(materiel.getId())) {
			EnterpriseMaterial oldMateriel = (EnterpriseMaterial) this.materialManageDao
					.load(EnterpriseMaterial.class, materiel.getId());
			if (!materiel.getPtNo().equals(oldMateriel.getPtNo())) {
				this.materialManageDao.updateEnterpriseBomManageParentNo(
						materiel.getPtNo(), oldMateriel.getPtNo());
				this.materialManageDao.updateEnterpriseBomManageCompentNo(
						materiel.getPtNo(), oldMateriel.getPtNo());
			}
			// 更新物料主档
			List list = materialManageDao
					.findMaterialByptNo(materiel.getPtNo());
			if (list.size() > 0) {
				Materiel m = (Materiel) list.get(0);
				String id = m.getId();
				try {
					PropertyUtils.copyProperties(m, materiel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				m.setId(id);
				this.saveMateriel(m);
			}
		}
		this.materialManageDao.saveEnterpriseMaterial(materiel); // 物料主档保存
		
		
		// 修改工厂BOM子件净重
		List<EnterpriseBomManage> ebmList = materialManageDao.findEnterpriseBomManageList(materiel.getPtNo());
		for (EnterpriseBomManage enterpriseBomManage : ebmList) {
			enterpriseBomManage.setNetWeight(materiel.getPtNetWeight());
			this.materialManageDao.saveEnterpriseBomManage(enterpriseBomManage);
		}
	}

	/**
	 * 更新工厂BOM管理子件的料号
	 * 
	 * @param newCompentNo
	 *            新的子件料号
	 * @param oldCompentNo
	 *            旧的子件料号
	 */
	public void updateEnterpriseBomManageCompentNo(String newCompentNo,
			String oldCompentNo) {
		this.materialManageDao.updateEnterpriseBomManageCompentNo(newCompentNo,
				oldCompentNo);
	}

	/**
	 * 更新工厂BOM管理父件的料号
	 * 
	 * @param newParentNo
	 *            新的料号
	 * @param oldParentNo
	 *            旧的料号
	 */
	public void updateEnterpriseBomManageParentNo(String newParentNo,
			String oldParentNo) {
		this.materialManageDao.updateEnterpriseBomManageParentNo(newParentNo,
				oldParentNo);
	}

	/**
	 * 更新海关汇率
	 */
	public void updateCustomsCurrRate() {
		Map map = new HashMap();
		List list = this.materialManageDao.findCurrRateByM();
		Hashtable<String, CurrRate> hs = new Hashtable<String, CurrRate>();
		for (int i = 0; i < list.size(); i++) {
			CurrRate obj = (CurrRate) list.get(i);
			String key = (obj.getCurr() == null ? "" : obj.getCurr().getCode())
					+ "/"
					+ (obj.getCurr1() == null ? "" : obj.getCurr1().getCode());
			hs.put(key, obj);
			System.out.println(key);
		}
		System.out.println("-----------------------");
		List dList = this.materialManageDao.findAllEmsHeadH2k();
		for (int j = 0; j < dList.size(); j++) {
			Object obj = dList.get(j);
			if (obj instanceof Contract) {
				Contract bx = (Contract) obj;
				String str = bx.getEmsNo();
				map
						.put(str, bx.getCurr() == null ? "" : bx.getCurr()
								.getCode());
			} else if (obj instanceof DzscEmsPorHead) {
				DzscEmsPorHead bx = (DzscEmsPorHead) obj;
				String str = bx.getEmsNo();
				map
						.put(str, bx.getCurr() == null ? "" : bx.getCurr()
								.getCode());
			} else if (obj instanceof EmsHeadH2k) {
				EmsHeadH2k bx = (EmsHeadH2k) obj;
				String str = bx.getEmsNo();
				map.put(str, "502");
			}
		}
		List cList = this.materialManageDao.findAllCustoms(null, null, null);
		for (int j = 0; j < cList.size(); j++) {
			BaseCustomsDeclaration c = (BaseCustomsDeclaration) cList.get(j);
			String curr = c.getCurrency() == null ? "" : c.getCurrency()
					.getCode();
			String ems = c.getEmsHeadH2k();
			if (ems == null || map.get(ems) == null) {
				continue;
			} else {
				String cd = map.get(ems).toString() + "/" + curr;
				System.out.println(cd);
				if (hs.get(cd) == null) {
					continue;
				} else {
					CurrRate cr = hs.get(cd);
					c.setExchangeRate(cr.getRate());
					System.out.println(cr.getRate());
					this.materialManageDao.saveOrUpdate(c);
				}
			}
		}
	}

	/**
	 * 更新海关汇率
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param exchangeRateIsNull
	 *            汇率是否为空
	 */
	public void updateCustomsCurrRates(Date beginDate, Date endDate,
			Boolean exchangeRateIsNull) {
		List cList = this.materialManageDao.findAllCustoms(beginDate, endDate,
				exchangeRateIsNull);
		List tempList = new ArrayList();
		for (int j = 0; j < cList.size(); j++) {
			BaseCustomsDeclaration c = (BaseCustomsDeclaration) cList.get(j);
			Curr curr = c.getCurrency();
			String ems = c.getEmsHeadH2k();
			Date date = c.getDeclarationDate();
			if (curr == null || ems == null || ems.equals("")) {
				continue;
			} else {
				Double dou = getCurrRateByCurr(curr, date, ems);
				c.setExchangeRate(dou);
//				this.materialManageDao.saveOrUpdate(c);
				tempList.add(c);
			}
		}
		this.materialManageDao.batchSaveOrUpdate(tempList);
	}

	/**
	 * 获取汇率
	 * 
	 * @param currs
	 *            币制
	 * @param date
	 *            创建日期
	 * @param emsno
	 *            帐册编号
	 * @return 为Double型，汇率
	 */
	public Double getCurrRateByCurr(Curr currs, Date date, String emsno) {
		String currCode = null;
		List dList = this.materialManageDao.findAllEmsHeadH2k(emsno);
		for (int j = 0; j < dList.size(); j++) {
			if (j == 1) {
				break;
			}
			Object obj = dList.get(j);
			if (obj instanceof Contract) {
				Contract bx = (Contract) obj;
				currCode = bx.getCurr() == null ? null : bx.getCurr().getCode();
			} else if (obj instanceof DzscEmsPorHead) {
				DzscEmsPorHead bx = (DzscEmsPorHead) obj;
				currCode = bx.getCurr() == null ? null : bx.getCurr().getCode();
			} else if (obj instanceof EmsHeadH2k) {
				currCode = "502";
			}
		}// 以报关单帐册号为KEY，所报关单币制代码存入
		Double dou = 0.0;
		if (currCode != null
				&& currCode.equals(currs.getCode() == null ? "" : currs
						.getCode())) {
			dou = 1.0;
		} else {
			dou = this.materialManageDao.findCurrRateByM(currCode, currs
					.getCode(), date);
		}
		return dou;
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            源字符串
	 * @param bytesCount
	 *            位置
	 * @return 是String型，截取字符串
	 */
	private String cut(String str, int bytesCount) {
		String xx = "";
		if (str == null || str.equals("")) {
			return str;
		}
		byte[] bytes = str.getBytes();
		char[] chars = new String(bytes, 0, bytesCount).toCharArray();
		char[] charsPlus = new String(bytes, 0, bytesCount + 1).toCharArray();
		if (chars.length == charsPlus.length) {
			xx = new String(bytes, 0, bytesCount - 1);
		} else {
			xx = new String(bytes, 0, bytesCount);
		}
		String mStr = "";
		for (int i = 0; i < 255; i++) {
			mStr = mStr + " ";
		}
		return new String((xx + mStr).getBytes(), 0, bytesCount);
	}

	/**
	 * 截取字符串
	 * 
	 * @param Value
	 *            源数据
	 * @param bytesCount
	 *            位置
	 * @return 是String型，截取字符串
	 */
	private String cutStr(String Value, int bytesCount) {
		if (CommonUtils.isCompany("明门幼童") || CommonUtils.isCompany("宝钜儿童用品")) {
			return Value;
		}
		String mStr = "";
		for (int i = 0; i < 255; i++) {
			mStr = mStr + " ";
		}
		String result = cut(Value + mStr, bytesCount);
		return result;
	}

	/**
	 * 获得报关单明细资料商品的名称及规格
	 * 
	 * @param commInfo
	 *            报关单明细
	 * @return 是String[]型，报关单明细资料商品的名称及规格
	 */
	public String[] getCommNameSpec(BaseCustomsDeclarationCommInfo commInfo) {
		String[] nameSpec = new String[2];
		nameSpec[0] = "";
		nameSpec[1] = "";
		if (commInfo != null) {
			// nameSpec[0] = cutStr(commInfo.getCommName() == null ? "" :
			// commInfo
			// .getCommName(), 30);
			// nameSpec[1] = cutStr(commInfo.getCommSpec() == null ? "" :
			// commInfo
			// .getCommSpec(), 30);
			nameSpec[0] = commInfo.getCommName() == null ? "" : commInfo
					.getCommName();
			nameSpec[1] = commInfo.getCommSpec() == null ? "" : commInfo
					.getCommSpec();
			return nameSpec;
		}
		return nameSpec;
	}

	/**
	 * 修改报关常用物料同时会修改电子账册未备案的内部归并及工厂物料主档
	 * 
	 * @param materiel
	 *            报关常用工厂物料资料
	 * @return 是 Materiel型，报关常用工厂物料资料
	 */
	public Materiel saveMateriel(Materiel materiel) {
		// 此代码不可以加上，应该另种方法来处理 edit by xxm 2007-02-08
		/*
		 * List list = this.materialManageDao.findInnerMergeDataByptNo(materiel
		 * .getPtNo()); if (list.size() > 0) { InnerMergeData data =
		 * (InnerMergeData) list.get(0);
		 * data.setHsAfterComplex(materiel.getComplex());
		 * materialManageDao.saveOrUpdate(data); }
		 */
		// 为什么写到一起考虑到一家企业只可能有一种报关模式，这样速度应该还好吧。
		if (materiel != null && materiel.getUnitConvert() != null) {// &&
			// materiel.getUnitConvert().doubleValue()
			// !=
			// 0.0
			// 更新电子化手册的工厂与物料对应表
			this.materialManageDao.updateBcsInnerMergeUnitConvert(materiel);

			// 更新电子手册 的工厂与物料对应表

			// 更新电子帐册内部归并

			// 更新单据中的对应关系 的拆算系数不能更新，因为单据中心里面一个物料对应不同的手册的报关名称 ，但是折算系数不相等，
			// 因为不知道更新到哪个物料，

			// this.materialManageDao.updateFactoryAndFactualCustomsRalationUnitConvert(materiel);
		}

		List mlist = materialManageDao.findEnterpriseMaterialByptNo(materiel
				.getPtNo());
		if (mlist.size() > 0) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) mlist
					.get(0);
			String id = enterpriseMaterial.getId();
			try {
				PropertyUtils.copyProperties(enterpriseMaterial, materiel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			enterpriseMaterial.setId(id);
			materialManageDao.saveOrUpdate(enterpriseMaterial);
		}
		materialManageDao.saveMateriel(materiel);
		return materiel;
	}

	/**
	 * 保存Bom外部导入信息
	 * 
	 * @param headList
	 *            导入的临时数据
	 */
	public void executeBomImport(List headList) {
		for (int i = 0; i < headList.size(); i++) {
			TempBomsManager tbm = (TempBomsManager) headList.get(i);
			EnterpriseBomManage enterpriseBomManage = new EnterpriseBomManage();
			enterpriseBomManage.setParentNo(tbm.getEbm().getParentNo());
			enterpriseBomManage.setCompentNo(tbm.getEbm().getCompentNo());
			enterpriseBomManage.setVersionNo(String.valueOf(Double.valueOf(
					tbm.getEbm().getVersionNo().trim()).intValue()));
			enterpriseBomManage.setMemo(tbm.getEbm().getMemo());
			enterpriseBomManage.setIsMainImg(tbm.getEbm().getIsMainImg());
			enterpriseBomManage.setNetWeight(tbm.getEbm().getNetWeight());
			// 保留小数位
			CompanyOther other = CommonUtils.getOther();
			Integer d = 6;
			if (other != null) {
				d = (other.getBomChangeBit() == null) ? Integer.valueOf(0)
						: other.getBomChangeBit();
			}
			double unitware = CommonUtils.getDoubleByDigit(tbm.getEbm()
					.getUnitWare(), d);
			double ware = CommonUtils.getDoubleByDigit(tbm.getEbm().getWare(),
					d);

			enterpriseBomManage.setUnitWare(unitware);
			enterpriseBomManage.setWare(ware);
			double unitDosage = 0.0;
			if (tbm.getEbm().getUnitDosage() == null
					|| "".equals(String.valueOf(tbm.getEbm().getUnitDosage())
							.trim())) {
				if (enterpriseBomManage.getWare() < 1) {
					unitDosage = CommonUtils.getDoubleByDigit(
							enterpriseBomManage.getUnitWare()
									/ (1 - enterpriseBomManage.getWare()), d);
				}
			} else {
				unitDosage = CommonUtils.getDoubleByDigit(tbm.getEbm()
						.getUnitDosage(), d);
			}
			enterpriseBomManage.setUnitDosage(unitDosage);
			this.materialManageDao.saveOrUpdateNoCache(enterpriseBomManage);
		}
	}
	
	
	/**
	 * 保存Bom外部导入信息（震兴）
	 * 
	 * @param headList
	 *            导入的临时数据
	 */
	public void executeBomImportZX(List headList) {
		Set<EnterpriseMaterial>	map = new HashSet<EnterpriseMaterial>();	
		for (int i = 0; i < headList.size(); i++) {
			TempBomsManager tbm = (TempBomsManager) headList.get(i);
			EnterpriseBomManage enterpriseBomManage = new EnterpriseBomManage();
			enterpriseBomManage.setParentNo(tbm.getEbm().getParentNo());
			enterpriseBomManage.setCompentNo(tbm.getEbm().getCompentNo());
			enterpriseBomManage.setVersionNo(String.valueOf(Double.valueOf(
					tbm.getEbm().getVersionNo().trim()).intValue()));
			enterpriseBomManage.setMemo(tbm.getEbm().getMemo());
			enterpriseBomManage.setIsMainImg(tbm.getEbm().getIsMainImg());
			enterpriseBomManage.setNetWeight(tbm.getEbm().getNetWeight());
			// 保留小数位
			CompanyOther other = CommonUtils.getOther();
			Integer d = 6;
			if (other != null) {
				d = (other.getBomChangeBit() == null) ? Integer.valueOf(0)
						: other.getBomChangeBit();
			}
			double unitware = CommonUtils.getDoubleByDigit(tbm.getEbm()
					.getUnitWare(), d);
			double ware = CommonUtils.getDoubleByDigit(tbm.getEbm().getWare(),
					d);

			enterpriseBomManage.setUnitWare(unitware);
			enterpriseBomManage.setWare(ware);
			double unitDosage = 0.0;
			if (tbm.getEbm().getUnitDosage() == null
					|| "".equals(String.valueOf(tbm.getEbm().getUnitDosage())
							.trim())) {
				if (enterpriseBomManage.getWare() < 1) {
					unitDosage = CommonUtils.getDoubleByDigit(
							enterpriseBomManage.getUnitWare()
									/ (1 - enterpriseBomManage.getWare()), d);
				}
			} else {
				unitDosage = CommonUtils.getDoubleByDigit(tbm.getEbm()
						.getUnitDosage(), d);
			}
			enterpriseBomManage.setUnitDosage(unitDosage);
			
			//2013-03-11 by lxr modify李小姐要求，以物料主档的净重为准，无须返回到物料主档 
			
//			/*
//			 * 当子件的净重不为空会者不为0时，获得物料主档信息，改变净重。
//			 */
//			if (enterpriseBomManage.getNetWeight() != null
//					&& enterpriseBomManage.getNetWeight() != 0) {
//				EnterpriseMaterial enterpriseMaterial = materialManageDao
//						.findEnterpriseMaterial(enterpriseBomManage
//								.getCompentNo());
//				enterpriseMaterial.setPtNetWeight(enterpriseBomManage.getNetWeight());
//				map.add(enterpriseMaterial);
//			}
			this.materialManageDao.saveOrUpdateNoCache(enterpriseBomManage);
		}
//		System.out.println(map.size());
		
//		for (EnterpriseMaterial enterpriseMaterial : map) {
//			materialManageDao.saveEnterpriseMaterial(enterpriseMaterial);
//		}
		
	}

	/**
	 * 获取工厂BOM管理子件资料的料号及版本号
	 * 
	 * @return Set 是String型，工厂BOM管理子件资料
	 */
	private Set getBomManageptNoAndversionSet() {
		Set set = new HashSet();
		List list = materialManageDao.findEnterpriseBomManageptNoAndversion();
		for (int i = 0; i < list.size(); i++) {
			Object[] obs = (Object[]) list.get(i);
			String parentNo = obs[0] == null ? "" : obs[0].toString();
			String compentName = obs[1] == null ? "" : obs[1].toString();
			String versionNo = obs[2] == null ? "" : obs[2].toString();
			String key = parentNo + "/" + compentName + "/" + versionNo;
			set.add(key);
		}
		return set;
	}

	/**
	 * 检查要导入文件是否存在
	 * 
	 * @param list
	 *            导入的数据
	 * @param isrePlace
	 *            是否替换
	 * @return List是TempBomsManager型，工厂BOM管理子件的临时类
	 */
	public List checkBomImportFileData(List list, boolean isrePlace) {
		ArrayList listNotFound = new ArrayList();
		Set allSet = null;

		// 如果不替换
		if (!isrePlace) {
			// 查询工厂BOM管理的父级号料，子级号料与版本号
			allSet = getBomManageptNoAndversionSet();
		}
		// BillTemp obj = new BillTemp();
		TempBomsManager fileData = null;// new BillTemp();

		// 工厂物料料号list
		List lsAllPtNo = materialManageDao.findMaterielNo();

		for (int i = 0; i < list.size(); i++) {
			fileData = (TempBomsManager) list.get(i);
			String key = "";
			if (!lsAllPtNo
					.contains(fileData.getEbm().getParentNo() == null ? ""
							: fileData.getEbm().getParentNo().trim())) {
				fileData.setErrinfo(((fileData.getErrinfo() == null ? ""
						: fileData.getEbm().getParentNo()) + ";父级料号不存在"));
			}
			if (!lsAllPtNo
					.contains(fileData.getEbm().getCompentNo() == null ? ""
							: fileData.getEbm().getCompentNo().trim())) {
				fileData.setErrinfo((fileData.getErrinfo() == null ? ""
						: fileData.getErrinfo())
						+ ";子级料号不存在");
			}
			if (fileData.getEbm().getVersionNo() == null
					|| "".equals(fileData.getEbm().getVersionNo().trim())) {
				fileData.setErrinfo((fileData.getErrinfo() == null ? ""
						: fileData.getErrinfo())
						+ ";版本号不能为空");
			} else {
				if (!NumberUtils.isNumber(fileData.getEbm().getVersionNo()
						.trim())) {
					fileData.setErrinfo((fileData.getErrinfo() == null ? ""
							: fileData.getErrinfo())
							+ ";版本号不是数值型");
				}
			}
			// 如果不替换
			if (!isrePlace) {
				// key为 父级料号 + 子级料号 + 版本号
				key += (fileData.getEbm().getParentNo() == null ? "" : fileData
						.getEbm().getParentNo().trim())
						+ "/"
						+ (fileData.getEbm().getCompentNo() == null ? ""
								: fileData.getEbm().getCompentNo().trim())
						+ "/"
						+ (fileData.getEbm().getVersionNo() == null ? ""
								: fileData.getEbm().getVersionNo().trim());
				if (allSet.contains(key)) {
					fileData.setErrinfo((fileData.getErrinfo() == null ? ""
							: fileData.getErrinfo())
							+ ";BOM已经存在");
				}
			}
			if (fileData.getEbm().getUnitWare() != null
					&& !"".equals(String.valueOf(
							fileData.getEbm().getUnitWare()).trim())
					&& !NumberUtils.isNumber(String.valueOf(
							fileData.getEbm().getUnitWare()).trim())) {
				fileData.setErrinfo((fileData.getErrinfo() == null ? ""
						: fileData.getErrinfo())
						+ ";单耗不是数值型");
			}
			if (fileData.getEbm().getWare() != null
					&& !"".equals(String.valueOf(fileData.getEbm().getWare())
							.trim())) {
				if (!NumberUtils.isNumber(String.valueOf(
						fileData.getEbm().getWare()).trim())) {
					fileData.setErrinfo((fileData.getErrinfo() == null ? ""
							: fileData.getErrinfo())
							+ ";损耗不是数值型");
				}
				if (NumberUtils.createNumber(
						String.valueOf(fileData.getEbm().getWare()).trim())
						.doubleValue() >= 1) {
					fileData.setErrinfo((fileData.getErrinfo() == null ? ""
							: fileData.getErrinfo())
							+ ";损耗不能大于或等与1");
				}
			}
			if (fileData.getEbm().getUnitDosage() != null
					&& !"".equals(String.valueOf(
							fileData.getEbm().getUnitDosage()).trim())
					&& !NumberUtils.isNumber(String.valueOf(
							fileData.getEbm().getUnitDosage()).trim())) {
				fileData.setErrinfo((fileData.getErrinfo() == null ? ""
						: fileData.getErrinfo())
						+ ";单项用量不是数值型");
			}
			if (fileData.getErrinfo() != null
					&& !"".equals(fileData.getErrinfo().trim())) {
				listNotFound.add(fileData);

			}
		}
		return listNotFound;
	}

	/**
	 * 根据父级料号、子件料号、版本号判断导入的数据中是否已经存在工厂BOM管理里
	 * 
	 * @param list
	 *            导入的数据
	 * @param isrePlace
	 *            是否替换
	 * @return List 为TempBomsManager型，导入的临时资料
	 */
	public List findNotExistedBomManageFileData(List list, boolean isrePlace) {
		// List arrayList = new ArrayList();
		HashMap map = new HashMap();

		// 已经存在的所有 工厂BOM
		List listExist = materialManageDao.findAllEnterpriseBomManage();
		System.out.println("原工厂BOM存在" + listExist.size());
		EnterpriseBomManage bomManage = null;
		for (int i = 0; i < listExist.size(); i++) {
			bomManage = (EnterpriseBomManage) listExist.get(i);

			// key为 父级料号 + 版本号
			String s = (bomManage.getParentNo().trim() + "/" + (bomManage
					.getVersionNo() == null ? "" : String.valueOf(Double
					.valueOf(bomManage.getVersionNo().trim()).intValue())));
			map.put(s, s);
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			TempBomsManager fileData = (TempBomsManager) list.get(i);
			String parentNo = fileData.getEbm().getParentNo();
			int versionNo = Integer.parseInt(fileData.getEbm().getVersionNo()
					.trim());

			String s = (fileData.getEbm().getParentNo().trim() + "/" + (fileData
					.getEbm().getVersionNo() == null ? ""
					: String
							.valueOf(Double.valueOf(
									fileData.getEbm().getVersionNo().trim())
									.intValue())));

			// 如果 资料存在的覆盖导入
			if (isrePlace) {
				if (map.get(s) != null) {
					// 删除这些需要覆盖的BOM
					materialManageDao.deleteEnterpriseBomManageByVersion(
							parentNo, versionNo);
					map.remove(s);
				}
			} else {
				if (map.get(s) != null) {
					list.remove(i);// 不覆盖则删除
				}
			}
		}
		return list;
	}

	/**
	 * 保存客户供应商资料
	 * 
	 * @param ls
	 *            司机资料
	 * @param isOverwrite
	 *            是否覆盖
	 */
	public void saveInputScmcoc(List ls, boolean isOverwrite) {
		// 实现缓存用来判断是否有重复的信息
		Map<String,ScmCoc> hs = new HashMap<String, ScmCoc>();
		List list = materialManageDao.findScmCocs();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc obj = (ScmCoc) list.get(i);
			String code = obj.getCode();
			if (hs.get(code) == null) {
				hs.put(code, obj);
			}
		}
		for (int j = 0; j < ls.size(); j++) {
			ScmCoc sobj = null;
			ScmCocForInput scmCocForInput = (ScmCocForInput) ls.get(j);
			String key = scmCocForInput.getScmCoc().getCode();

			// BillTemp temp = (BillTemp) ls.get(j);
			// String key = temp.getBill1();
			// 当没有重复信息的时候，执行保存动作
			if (null == hs.get(key)) {
				sobj = new ScmCoc();
				// sobj = scmCocForInput.getScmCoc();
				try {
					sobj.setCode(key);
					sobj.setName(scmCocForInput.getScmCoc().getName());
					sobj.setFname(scmCocForInput.getScmCoc().getFname());
					sobj.setIsCustomer(scmCocForInput.getScmCoc()
							.getIsCustomer());
					sobj.setIsManufacturer(scmCocForInput.getScmCoc()
							.getIsManufacturer());
					sobj.setIsTransferFactoryOut(scmCocForInput.getScmCoc()
							.getIsTransferFactoryOut());
					sobj.setIsTransferFactoryIn(scmCocForInput.getScmCoc()
							.getIsTransferFactoryIn());
					sobj.setIsStraightOut(scmCocForInput.getScmCoc()
							.getIsStraightOut());
					sobj.setIsStraightIn(scmCocForInput.getScmCoc()
							.getIsStraightIn());
					sobj
							.setIsHomeBuy(scmCocForInput.getScmCoc()
									.getIsHomeBuy());
					sobj
							.setIsLeiXiao(scmCocForInput.getScmCoc()
									.getIsLeiXiao());
					sobj.setIsWork(scmCocForInput.getScmCoc().getIsWork());
					sobj.setIsCollaborater(scmCocForInput.getScmCoc()
							.getIsCollaborater());
					sobj.setBrief(scmCocForInput.getScmCoc().getBrief());
					sobj.setCountry(scmCocForInput.getScmCoc().getCountry());
					sobj.setCountry2(scmCocForInput.getScmCoc().getCountry2());
					sobj.setPortLin(scmCocForInput.getScmCoc().getPortLin());
					sobj.setCustoms(scmCocForInput.getScmCoc().getCustoms());
					sobj.setBelongToCustoms(scmCocForInput.getScmCoc()
							.getBelongToCustoms());
					sobj.setTransf(scmCocForInput.getScmCoc().getTransf());
					sobj.setPredock(scmCocForInput.getScmCoc().getPredock());
					sobj.setWarp(scmCocForInput.getScmCoc().getWarp());
					sobj.setTrade(scmCocForInput.getScmCoc().getTrade());

					sobj.setDeliveryDistance(scmCocForInput.getScmCoc()
							.getDeliveryDistance());
					sobj.setFlatCode(scmCocForInput.getScmCoc().getFlatCode());
					sobj.setEngName(scmCocForInput.getScmCoc().getEngName());
					sobj.setLinkMan(scmCocForInput.getScmCoc().getLinkMan());
					sobj.setLinkTel(scmCocForInput.getScmCoc().getLinkTel());
					sobj.setFax(scmCocForInput.getScmCoc().getFax());
					sobj.setAddre(scmCocForInput.getScmCoc().getAddre());
					sobj.setBank(scmCocForInput.getScmCoc().getBank());
					sobj.setTransportationTime(scmCocForInput.getScmCoc()
							.getTransportationTime());
					this.materialManageDao.saveOrUpdate(sobj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 当有重复信息时，实行覆盖动作
			} else {
				if (isOverwrite) {
					try {
						sobj = (ScmCoc) hs.get(key);
						sobj.setCode(key);
						sobj.setName(scmCocForInput.getScmCoc().getName());
						sobj.setFname(scmCocForInput.getScmCoc().getFname());
						sobj.setIsCustomer(scmCocForInput.getScmCoc()
								.getIsCustomer());
						sobj.setIsManufacturer(scmCocForInput.getScmCoc()
								.getIsManufacturer());
						sobj.setIsTransferFactoryOut(scmCocForInput.getScmCoc()
								.getIsTransferFactoryOut());
						sobj.setIsTransferFactoryIn(scmCocForInput.getScmCoc()
								.getIsTransferFactoryIn());
						sobj.setIsStraightOut(scmCocForInput.getScmCoc()
								.getIsStraightOut());
						sobj.setIsStraightIn(scmCocForInput.getScmCoc()
								.getIsStraightIn());
						sobj.setIsHomeBuy(scmCocForInput.getScmCoc()
								.getIsHomeBuy());
						sobj.setIsLeiXiao(scmCocForInput.getScmCoc()
								.getIsLeiXiao());
						sobj.setIsWork(scmCocForInput.getScmCoc().getIsWork());
						sobj.setIsCollaborater(scmCocForInput.getScmCoc()
								.getIsCollaborater());
						sobj.setBrief(scmCocForInput.getScmCoc().getBrief());
						sobj
								.setCountry(scmCocForInput.getScmCoc()
										.getCountry());
						sobj.setCountry2(scmCocForInput.getScmCoc()
								.getCountry2());
						sobj
								.setPortLin(scmCocForInput.getScmCoc()
										.getPortLin());
						sobj
								.setCustoms(scmCocForInput.getScmCoc()
										.getCustoms());
						sobj.setBelongToCustoms(scmCocForInput.getScmCoc()
								.getBelongToCustoms());
						sobj.setTransf(scmCocForInput.getScmCoc().getTransf());
						sobj
								.setPredock(scmCocForInput.getScmCoc()
										.getPredock());
						sobj.setWarp(scmCocForInput.getScmCoc().getWarp());
						sobj.setTrade(scmCocForInput.getScmCoc().getTrade());
						sobj.setFlatCode(scmCocForInput.getScmCoc()
								.getFlatCode());
						sobj
								.setEngName(scmCocForInput.getScmCoc()
										.getEngName());
						sobj
								.setLinkMan(scmCocForInput.getScmCoc()
										.getLinkMan());
						sobj
								.setLinkTel(scmCocForInput.getScmCoc()
										.getLinkTel());
						sobj.setFax(scmCocForInput.getScmCoc().getFax());
						sobj.setAddre(scmCocForInput.getScmCoc().getAddre());
						sobj.setBank(scmCocForInput.getScmCoc().getBank());
						sobj.setTransportationTime(scmCocForInput.getScmCoc()
								.getTransportationTime());
						sobj.setDeliveryDistance(scmCocForInput.getScmCoc()
								.getDeliveryDistance());
						this.materialManageDao.saveOrUpdate(sobj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 保存倒入的工厂司机信息
	 * 
	 * @param ls
	 *            司机资料
	 * @param isOverwrite
	 *            是否覆盖
	 */
	public void saveImportDriverInfo(List ls, boolean isOverwrite) {
		/*
		 * FptBillItem billItem = billItemMap
		 * .get(((TempFptBillHeadImportFromExcel)
		 * ls.get(i)).getFptBillItem().getListNo());
		 */
		Map<String, MotorCode> hs = new HashMap<String, MotorCode>();
		List list = materialManageDao.findMotorCode();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			MotorCode obj = (MotorCode) list.get(i);
//			String code = obj.getCode();
//			if (code == null || code.equals(""))
//				continue;
//			hs.put(code, obj);
			hs.put(obj.getHomeCard()+"/"+obj.getComplex(), obj);
		}
		for (int j = 0; j < ls.size(); j++) {
			TempMotor tm = (TempMotor) ls.get(j);
			MotorCode driver = hs.get(tm.getMc().getHomeCard()+"/"+tm.getMc().getComplex());
			// String key = driver.getCode();
			// 当没有重复信息的时候，执行保存动作
			if (driver == null) {
				driver = ((TempMotor) ls.get(j)).getMc();
				this.materialManageDao.saveOrUpdate(driver);
			} else {
				if (isOverwrite) {
					MotorCode drivers = ((TempMotor) ls.get(j)).getMc();
					driver.setCode(drivers.getCode());
					driver.setName(drivers.getName());
					driver.setHomeCard(drivers.getHomeCard());
					driver.setHongkongCard(drivers.getHongkongCard());
					driver.setComplex(drivers.getComplex());
					driver.setIcCard(drivers.getIcCard());
					driver.setCustomsPort(drivers.getCustomsPort());
					driver.setTrafficUnit(drivers.getTrafficUnit());
					driver.setTrafficUnitAdd(drivers.getTrafficUnitAdd());
					driver.setTrafficUnitTel(drivers.getTrafficUnitTel());
					this.materialManageDao.saveOrUpdate(driver);
				} else {
					continue;
				}
			}
			hs.put(driver.getHomeCard()+"/"+driver.getComplex(), driver);
		}
	}

	/*
	 * list.add(new JTableListColumn("代码", "code", 80)); list.add(new
	 * JTableListColumn("司机名称", "name", 80)); list.add(new
	 * JTableListColumn("国内车牌", "homeCard", 80)); list.add(new
	 * JTableListColumn("香港车牌", "hongkongCard", 80)); list.add(new
	 * JTableListColumn("司机本海关编码", "complex", 80)); list.add(new
	 * JTableListColumn("IC卡", "icCard", 80)); list.add(new
	 * JTableListColumn("结关口岸", "customsPort", 80)); list.add(new
	 * JTableListColumn("运输单位", "trafficUnit", 80)); list.add(new
	 * JTableListColumn("运输单位地址", "trafficUnitAdd", 80)); list.add(new
	 * JTableListColumn("运输单位电话", "trafficUnitTel", 80));
	 */
	/**
	 * 检查是否为空，如果是则返回0
	 */
	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 查找报关单明细资料，统计商品数量
	 * 
	 * @param isProduct
	 *            是否为成品
	 * @param seqNum
	 *            商品序号
	 * @param CustomsDeclarationCommInfo
	 *            报关单表体
	 * @return Double为Double型，统计报关单商品明细资料的商品数量
	 */
	public Double findCustomsDeclarationCommInfo(Boolean isProduct,
			String seqNum,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo) {
		Double CountAmount = 0.0;
		List list = this.materialManageDao.findCustomsDeclarationCommInfo(
				isProduct, seqNum, CustomsDeclarationCommInfo);
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			if (isProduct) {
				if (info.getBaseCustomsDeclaration().getImpExpType().equals(
						ImpExpType.DIRECT_IMPORT)) {
					// 直接进口-
					CountAmount = (fd(CountAmount) + fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) {
					// 料件转厂
					CountAmount = (fd(CountAmount) + fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.BACK_MATERIEL_EXPORT)) {// 退料出口
					CountAmount = (fd(CountAmount) - fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.REMAIN_FORWARD_IMPORT)) {
					CountAmount = (fd(CountAmount) + fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.MATERIAL_DOMESTIC_SALES)) {
					CountAmount = (fd(CountAmount) - fd(info.getCommAmount()));
				}
			} else {
				if (info.getBaseCustomsDeclaration().getImpExpType().equals(
						ImpExpType.DIRECT_EXPORT)) {
					CountAmount = (fd(CountAmount) + fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)) {
					CountAmount = (fd(CountAmount) + fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.BACK_FACTORY_REWORK)) {
					CountAmount = (fd(CountAmount) - fd(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.REWORK_EXPORT)) {
					CountAmount = (fd(CountAmount) + fd(info.getCommAmount()));
				}
			}
		}
		return CountAmount;
	}

	/**
	 * 查找报关单明细资料，统计商品数量
	 * 
	 * @param isProduct
	 *            是否为成品
	 * @param seqNum
	 *            商品序号
	 * @param CustomsDeclarationCommInfo
	 *            报关单表体
	 * @return Double为Double型，统计报关单商品明细资料的商品数量
	 */
	public List findCustomsDeclarationCommInfos(Boolean isMaterial,
			List<Integer> seqNums,BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo) {
		List list = this.materialManageDao.findCustomsDeclarationCommInfos(
				isMaterial, seqNums, CustomsDeclarationCommInfo);
		return list;
	}
	
	/**
	 * 增加报关常用工厂BOM版本资料或者插入报关常用工厂BOM料件资料
	 * 
	 * @param mersion
	 *            报关常用工厂BOM版本资料
	 * @param version
	 *            版本号
	 * @return Map是MaterialBomVersion型，报关常用工厂BOM版本资料
	 */
	public Map addMaterialBomVersionOrInsertMaterialBomDetail(
			MaterialBomVersion mersion, Integer version) {
		Map returnmap = new HashMap();
		MaterialBomMaster master = mersion.getMaster();
		List versionList = this.materialManageDao
				.findMaterielBomVersionByVersion(master, version);
		// 根据master 及版本号来查找唯一确定的BOM
		MaterialBomVersion materialBomVersion = null;
		if (versionList.size() == 0) {
			// 如果size为0,则说明没有这个版本
			materialBomVersion = new MaterialBomVersion();
			try {
				PropertyUtils.copyProperties(materialBomVersion, mersion);
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			}
			materialBomVersion.setId(null);
			materialBomVersion.setVersion(version);
			this.materialManageDao.saveMaterielBomVersion(materialBomVersion);
		} else {// 如果不为0
			returnmap.put("isExist", true);
			return returnmap;
		}
		List rlist = new ArrayList();
		List list = this.materialManageDao.findMaterielBomDetail(mersion);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MaterialBomDetail olddata = (MaterialBomDetail) iter.next();
			MaterialBomDetail newdata = new MaterialBomDetail();
			try {
				PropertyUtils.copyProperties(newdata, olddata);
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			}
			newdata.setId(null);
			newdata.setVersion(materialBomVersion);
			this.materialManageDao.saveMaterielBomDetail(newdata);
			rlist.add(newdata);
			returnmap.put("newVersion", materialBomVersion);
			returnmap.put("detail", rlist);
		}
		return returnmap;
	}

	/**
	 * 复制bom，工厂BOM管理
	 * 
	 * @param details
	 *            工厂BOM管理子件资料
	 *@param version
	 *            版本号
	 * @return Map 是EnterpriseBomManage型，工厂BOM管理版本资料
	 */
	public Map addMaterialBomVersionOrInsertMaterialBomDetail(
			List<EnterpriseBomManage> details, Integer version) {
		Map returnmap = new HashMap();
		List rlist = new ArrayList();
		// 判断新增bom版本号是否已经存在
		String parentNo = details.get(0).getParentNo();
		Integer versionNo = CommonUtils.getInteger(details.get(0)
				.getVersionNo());
		List<EnterpriseBomVersion> list = this.materialManageDao
				.findChildBomByParent(parentNo, version.toString());
		if (list.size() > 0) {// 已经存在
			returnmap.put("isExist", true);
			return returnmap;
		}
		// 不存在,新增版本bom
		// no1 新增新版本
		// List<EnterpriseBomVersion> boms =
		// this.materialManageDao.findEnterpriseBomVersionByVersion(parentNo,
		// versionNo);
		// EnterpriseBomVersion bomVersion = boms.get(0);
		// EnterpriseBomVersion newBomVersion = new EnterpriseBomVersion();
		// try {
		// PropertyUtils.copyProperties(newBomVersion, bomVersion);
		// } catch (IllegalAccessException ex) {
		// ex.printStackTrace();
		// } catch (InvocationTargetException ex) {
		// ex.printStackTrace();
		// } catch (NoSuchMethodException ex) {
		// ex.printStackTrace();
		// }
		// newBomVersion.setId(null);
		// newBomVersion.setVersion(version);
		// this.materialManageDao.saveEnterpriseBomVersion(newBomVersion);
		// no2新增新版本bom
		for (EnterpriseBomManage bom : details) {
			EnterpriseBomManage newBom = new EnterpriseBomManage();
			try {
				PropertyUtils.copyProperties(newBom, bom);
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			}
			newBom.setId(null);
			newBom.setVersionNo(version.toString());
			this.materialManageDao.saveEnterpriseBomManage(newBom);
			rlist.add(newBom);
		}
		// returnmap.put("newVersion", newBomVersion);
		returnmap.put("detail", rlist);
		return returnmap;
	}

	/**
	 * 统计工厂料件被使用的成品
	 * 
	 * @param materiel
	 *            工厂物料主档资料
	 * @return List 是EmptyProductMaterial型，料件被使用的成品对应表
	 */
	public List findEnterpriseBomManageByDetail(EnterpriseMaterial materiel) {
		List<EmptyProductMaterial> rs = new ArrayList();
		// 查询料件成耗用的成品，半成品
		List<EnterpriseBomManage> ms = materialManageDao
				.findEnterpriseBomManageByMateriel(materiel);
		for (EnterpriseBomManage e : ms) {
			EmptyProductMaterial ep = new EmptyProductMaterial();
			EnterpriseMaterial parent = materialManageDao
					.findEnterpriseMaterial(e.getParentNo());
			if (parent != null) {
				ep.setCompentNo(e.getCompentNo());
				ep.setCompentName(materiel.getFactoryName());
				ep.setCompentSpec(materiel.getFactorySpec());
				ep.setUnitDosage(e.getUnitDosage());
				ep.setUnitWare(e.getUnitWare());
				ep.setWare(e.getWare());
				ep.setVersionNo(e.getVersionNo());
				ep.setScmCoiName(parent.getScmCoi() == null ? "" : parent
						.getScmCoi().getName());
				ep.setPtNo(parent.getPtNo());
				ep.setPtName(parent.getFactoryName());
				ep.setPtSpec(parent.getFactorySpec());
				ep.setCalUnitName(parent.getCalUnit() == null ? "" : parent
						.getCalUnit().getName());
				// wss2010.04.13所加
				ep.setBomManagerId(e.getId());
				rs.add(ep);
			}
		}
		return rs;
	}

	/**
	 * 通过id 查找工厂BOM子件
	 * 
	 * @param id
	 * @return
	 * @author wss
	 */
	public List findEnterpriseBomManagerById(String id) {
		return materialManageDao
				.find(
						"select a from EnterpriseBomManage a where a.id=? and a.company.id=? ",
						new Object[] { id, CommonUtils.getCompany().getId() });
	}

	public List checkEnterpriseBomManageIsRight() {
		ArrayList result = new ArrayList();
		List list = materialManageDao.checkEnterpriseBomManageIsRight(true);
		List list2 = materialManageDao.checkEnterpriseBomManageIsRight(false);
		for (int i = 0; i < list.size(); i++) {
			EnterpriseBomManage bom = (EnterpriseBomManage) list.get(i);
			TempOfCheck c = new TempOfCheck();
			c.setParentNo(bom.getParentNo());
			c.setChildVersionNo(bom.getChildVersionNo());
			c.setVersionNo(bom.getVersionNo());
			c.setCompentNo(bom.getCompentNo());
			c.setErro("父级料号在物料主档中不存在");
			result.add(c);
		}
		for (int i = 0; i < list2.size(); i++) {
			EnterpriseBomManage bom = (EnterpriseBomManage) list2.get(i);
			TempOfCheck c = new TempOfCheck();
			c.setParentNo(bom.getParentNo());
			c.setChildVersionNo(bom.getChildVersionNo());
			c.setVersionNo(bom.getVersionNo());
			c.setCompentNo(bom.getCompentNo());
			c.setErro("子级料号在物料主档中不存在");
			result.add(c);
		}

		return result;
	}
	
	
	/**
	 * 根据料号查找报关常用工厂BOM成品资料列表
	 * 
	 * @param ptNos
	 *            料号
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List<MaterialBomMaster> findMaterielBomMasterByPtNos(String... ptNos){
		return materialManageDao.findMaterielBomMasterByPtNos(ptNos);
	}

	// public DataSource getDataSource() {
	// return dataSource;
	// }
	//
	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }
	// private String getInsertSqlCasHead() {
	// String sql = "insert into BomManage
	// (id,parentNo,compentNo,versionNo,unitWare,ware,unitDosage,coid) values "
	// + " (substring(CAST(newid() as varchar(255)),1,8)"
	// + " + substring(CAST(newid() as varchar(255)),10,4)"
	// + " + substring(CAST(newid() as varchar(255)),15,4)"
	// + " + substring(CAST(newid() as varchar(255)),20,4)"
	// + " + substring(CAST(newid() as varchar(255)),25,12)"
	// + ",?,?,?,?,?,?,?)";
	// return sql;
	// }
	// private void setParameters(PreparedStatement stmt, BillTemp param)
	// throws SQLException {
	// String stype = param.getBill1();
	// stmt.setObject(1, param.getBill1());
	// stmt.setObject(2, param.getBill2());
	// stmt.setObject(3, param.getBill3());
	// stmt.setObject(4, param.getBill4());
	// stmt.setObject(5, param.getBill5());
	// stmt.setObject(6, param.getBill6());
	// stmt.setObject(7, CommonUtils.getCompany().getId());
	// }

	// String insertSql = getInsertSqlCasHead();
	// try {
	// Properties prop = new Properties();
	// System.out.println("prop------------------------" + prop);
	// String filePath = new File("").getAbsolutePath();
	// System.out.println("filePath------------------------" + filePath);
	// // String s = "jbcus3";
	// String s = "bin";
	// if (filePath.contains(s)) {
	// s = filePath.substring(0, filePath.indexOf(s));
	// // s += "jbcus3" + File.separator + "src" + File.separator
	// // + "jdbc.properties";
	// s += "webapps" + File.separator + "ROOT" + File.separator
	// + "WEB-INF" + File.separator + "jdbc.properties";
	//
	// }
	// System.out.println("s------------------------" + s);
	// File file = new File(s);
	// if (file.exists()) {
	// FileInputStream in = new FileInputStream(s);
	// prop.load(in);
	// in.close();
	// }
	// String url = prop.getProperty("jdbc.url");
	// String driverClassName = prop.getProperty("jdbc.driverClassName");
	// String username = prop.getProperty("jdbc.username");
	// String password = prop.getProperty("jdbc.password");
	// String tableOwner = prop.getProperty("jdbc.tableOwner");
	// Class.forName(driverClassName);
	// conn = DriverManager.getConnection(url, username, password);
	// // conn = dataSource.getConnection();
	// if (tableOwner != null && !tableOwner.equals("")) {
	// conn.setCatalog(tableOwner);
	// }
	// PreparedStatement stmtInsert = conn.prepareStatement(insertSql);
	// System.out.println("[JBCUS] begin insert table casHead time:"
	// + new Date());
	// for (int i = 0; i < headList.size(); i++) {
	// BillTemp param = (BillTemp) headList.get(i);
	// setParameters(stmtInsert, param);
	// stmtInsert.addBatch();
	// if ((i != 0 && (i % 1000) == 0) || i == headList.size() - 1) {
	// stmtInsert.executeBatch();
	// stmtInsert.clearBatch();
	// }
	// }
	// } catch (SQLException e) {
	// // e.printStackTrace();
	// throw new RuntimeException(e);
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// conn.close();
	//
	// } catch (Exception ex) {
	//
	// }
	// }
	// System.out.println("[JBCUS] end insert table casHead time:"
	// + new Date());
	// List list = new ArrayList();
	
	/**
	 * 检查料件使用情况
	 * @param materiel
	 * @return
	 */
	public String checkMaterielUsingCondition(Materiel materiel) {
		// 工厂和实际报关对应表
		boolean isFactoryAndFactualCustomsRalation = materialManageDao
				.findIsUsingMateriel("FactoryAndFactualCustomsRalation",
						materiel);
		// BCS内部归并对应表
		boolean isBcsInnerMerge = materialManageDao.findIsUsingMateriel(
				"BcsInnerMerge", materiel);
		// 存放企业内部归并－－内部归并资料
		boolean isInnerMergeData = materialManageDao.findIsUsingMateriel(
				"InnerMergeData", materiel);
		// 存放报关常用工厂BOM成品资料
		boolean isMaterialBomMaster = materialManageDao.findIsUsingMateriel(
				"MaterialBomMaster", materiel);
		
		StringBuffer usingTable = new StringBuffer();
		if(isFactoryAndFactualCustomsRalation){
			usingTable.append("对应关系(ffRalation)");
		}
		if(isBcsInnerMerge){
			usingTable.append("," + "电子化手册-物料与报关对应表(BcsInnerMerge) ");
		}
		if(isInnerMergeData){
			usingTable.append("," + "电子账册-内部归并(InnerMergeData) ");
		}
		if(isMaterialBomMaster){
			usingTable.append("," + "存放报关常用工厂BOM成品资料(MaterialBomMaster) ");
		}
		
		return usingTable.toString();
	}
	
	public void saveScmCoc(ScmCoc coc){
//		List list = materialManageDao.checkCoc(coc);
//		String customsDeclarationCode = "";
//		for (int i = 0; i < list.size(); i++) {
//			customsDeclarationCode +=list.get(i)+", ";
//		}
//		if(!customsDeclarationCode.trim().isEmpty()){
//			throw new RuntimeException("资料已经被报关单号为:"+customsDeclarationCode+"的报关单使用到,不允许删除！");
//		}else{
			materialManageDao.saveScmCoc(coc);
//		}
	}
	
	public void deleteScmCoc(ScmCoc coc){
		List list = materialManageDao.checkCoc(coc);
		String customsDeclarationCode = "";
		for (int i = 0; i < list.size(); i++) {
			customsDeclarationCode +=list.get(i)+", ";
		}
		if(!customsDeclarationCode.trim().isEmpty()){
			throw new RuntimeException("资料已经被报关单号为:"+customsDeclarationCode+"的报关单使用到,不允许删除！");
		}else{
			materialManageDao.deleteScmCoc(coc);
		}
	}
}
