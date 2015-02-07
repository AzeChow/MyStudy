package com.bestway.bcus.dataimport.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.MaterielAndHalfProduct;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

public class DataImportMaps {
	private Map<String, Map<String, Object>> calUnitMap = new HashMap<String, Map<String, Object>>();

	private Map<String, Map<String, Object>> scmCocMap = new HashMap<String, Map<String, Object>>();

	private Map<String, Map<String, Object>> wareSetMap = new HashMap<String, Map<String, Object>>();
	
	private Map<String, Map<String, Object>> innerMap = new HashMap<String, Map<String, Object>>();

	private Map<String, Map<String, Object>> materielAndHalfProductMap = new HashMap<String, Map<String, Object>>();

	private Map<String, Map<String, Object>> materiel = new HashMap<String, Map<String, Object>>();

	
	private Map<String, Object> currMap = null;

	private Map<String, Object> unitMap = null;

	private CommonBaseCodeDao commonBaseCodeDao = null;
	
	private MaterialManageDao materialManageDao =null;

	private static Log logger = LogFactory.getLog(DataImportMaps.class);

	/**
	 * 就是说要求重新生成这些Map
	 * 
	 */
	public DataImportMaps() {
		commonBaseCodeDao = (CommonBaseCodeDao) CommonUtils.getContext()
				.getBean("commonCodeDao");

		materialManageDao = (MaterialManageDao) CommonUtils.getContext()
				.getBean("materialManageDao");
	}

	/**
	 * 获得该公司存在的即是料件又是半成的品的料件
	 * 
	 * @param company
	 * @return
	 */
	public Map<String, Object> getMaterielAndHalfProductMap(Company company) {
		if (materielAndHalfProductMap.get(company.getId()) == null) {
			logger
					.info("[JBCUS DATA IMPORT]  begin init materielAndHalfProductMap ");
			Map<String, Object> map = new HashMap<String, Object>();
			List list = commonBaseCodeDao
					.findMaterielAndHalfProductMap(company);
			for (int i = 0; i < list.size(); i++) {
				MaterielAndHalfProduct materielAndHalf = (MaterielAndHalfProduct) list
						.get(i);
				map.put(materielAndHalf.getCode(), materielAndHalf);
			}
			materielAndHalfProductMap.put(company.getId(), map);
			logger
					.info("[JBCUS DATA IMPORT]  end  init materielAndHalfProductMap ");
		}
		return materielAndHalfProductMap.get(company.getId());
	}

	/**
	 * 获得计量单位 以名称为key
	 * 
	 * @param company
	 * @return
	 */

	public Map<String, Object> getCalUnit(Company company) {
		if (calUnitMap.get(company.getId()) == null) {
			logger.info("[JBCUS DATA IMPORT]  begin init CalUnit ");
			Map<String, Object> map = new HashMap<String, Object>();
			List list = materialManageDao.findCalUnit(company);
			for (int i = 0; i < list.size(); i++) {
				CalUnit calUnit = (CalUnit) list.get(i);
				map.put(calUnit.getCode(), calUnit);
			}
			calUnitMap.put(company.getId(), map);
			logger.info("[JBCUS DATA IMPORT]  end  init CalUnit ");
		}
		return calUnitMap.get(company.getId());
	}
	
	
	/**
	 * 获得物料主档 以名称为key
	 * 
	 * @param company
	 * @return
	 */
//    public Map getMateriel(Company company) {
//        if (wareSetMap.get(company.getId()) == null) {
//            System.out.println("begin init materiel");
//            Map map = new HashMap();
//            List list = commonBaseCodeDao.findMateriel();
//            for (int i = 0; i < list.size(); i++) {
//                Materiel materiel = (Materiel) list.get(i);
//                map.put(materiel.getPtNo(), materiel);
//            }
//            materiel.put(company.getId(), map);
//            System.out.println("end   init materiel");
//        }
//        return materiel.get(company.getId());
//    }
    

	/**
	 * 获得币值 以名称为key
	 * 
	 * @param company
	 * @return
	 */
	public Map<String, Object> getUnit() {
		if (unitMap == null) {
			logger.info("[JBCUS DATA IMPORT]  begin init unitMap");
			unitMap = new HashMap<String, Object>();
			List list = materialManageDao.findUnit();
			for (int i = 0, n = list.size(); i < n; i++) {
				Unit unit = (Unit) list.get(i);
				unitMap.put(unit.getCode(), unit);
			}
			logger.info("[JBCUS DATA IMPORT]  end init unitMap");
		}
		return unitMap;
	}

	/**
	 * 获得计量单位 以名称为key
	 * 
	 * @param company
	 * @return
	 */
	public Map<String, Object> getCurrCode() {
		if (currMap == null) {
			logger.info("[JBCUS DATA IMPORT]  begin init Currcode");
			currMap = new HashMap<String, Object>();
			List list = materialManageDao.findCurrCode();
			for (int i = 0; i < list.size(); i++) {
				CurrCode curr = (CurrCode) list.get(i);
				currMap.put(curr.getCode(), curr);
			}
			logger.info("[JBCUS DATA IMPORT]  end init Currcode");
		}
		return currMap;
	}

	/**
	 * 获得客户供应商
	 * 
	 * @param company
	 * @return
	 */
	public Map<String, Object> getScmCoc(Company company) {
		if (scmCocMap.get(company.getId()) == null) {
			logger.info("[JBCUS DATA IMPORT]  begin init ScmCoc");
			Map<String, Object> map = new HashMap<String, Object>();
			List list = materialManageDao.findScmCocs(company);
			for (int i = 0; i < list.size(); i++) {
				ScmCoc scmCoc = (ScmCoc) list.get(i);
				map.put(scmCoc.getCode(), scmCoc);
			}
			scmCocMap.put(company.getId(), map);
			logger.info("[JBCUS DATA IMPORT]  end  init ScmCoc");
		}
		return scmCocMap.get(company.getId());
	}

	/**
	 * 获得仓库
	 * 
	 * @param company
	 * @return
	 */
	public Map<String, Object> getWareSet(Company company) {
		if (wareSetMap.get(company.getId()) == null) {
			logger.info("[JBCUS DATA IMPORT]  begin init Wareset");
			Map<String, Object> map = new HashMap<String, Object>();
			List list = materialManageDao.findWareSet(company);
			for (int i = 0; i < list.size(); i++) {
				WareSet wareSet = (WareSet) list.get(i);
				map.put(wareSet.getCode(), wareSet);
			}
			wareSetMap.put(company.getId(), map);
			logger.info("[JBCUS DATA IMPORT]  end   init Wareset");
		}
		return wareSetMap.get(company.getId());
	}

	
	
	public Map<String, Object> getInnerMergedata() {
        logger.info("[JBCUS DATA IMPORT]  begin init innerMap");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List list = materialManageDao.findInnerMergedata();
		System.out.println("已经存在的内部归并数:"+list.size());
		for (int i = 0; i < list.size(); i++) {			
			Materiel obj = (Materiel) list.get(i);
			if (obj.getPtNo() != null){
				map.put(obj.getPtNo().trim(), obj.getPtNo().trim());
			}
			
		}
		
		logger.info("[JBCUS DATA IMPORT]  end   init innerMap");
		return map;
	}
	
	
	
	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}
}
