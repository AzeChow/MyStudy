package com.bestway.bcs.verification.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Session;

import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
/**
 * 工厂库存数据分析 DAO
 * @author chl
 */
public class VFFactoryAnalyseDao extends BaseDao {

	/**
	 * 查询 vfsection【批次】下工厂库存报关数量，并按照合并序号分组汇总报关数量
	 * @param clazz VFBaseStockImg子类 工厂料件
	 * @param vfsection 批次
	 * @return List<[mergerNo,sum(a.hsAmount)]>
	 */
	public List<Object[]> findHadConvertGroupByMergeNo(Class<? extends VFBaseStockImg> clazz,VFSection vfsection) {
		String hql = "select a.mergerNo,sum(a.hsAmount) as hsAmount from ".concat(clazz.getName()).concat(" a where a.section = ? and a.mergerNo is not null group by a.mergerNo");		
		return (List<Object[]>)super.find(hql, vfsection);
	}

	// -------------------------------查询方法 开始-----------------------------//
	
	// -------------------------------查询方法 结束-----------------------------//

	// -------------------------------保存方法 开始-----------------------------//
	public <T extends VFBaseStockImg> void batchSaveVFEnity(List<T> list, String clazz) {
		Session s = getSession();
		s.setCacheMode(CacheMode.IGNORE);
		StringBuilder update = new StringBuilder("update " + clazz + " c set");
		List<Object> ps = new ArrayList<Object>();
		
		int size = list.size();
		T o = null;
		for (int i = 0; i < size; i++) {
			o = list.get(i);
//			convert.setSection(vfSection);
			update.append(" c.section = ");
			ps.add(o.getSection());
//			convert.setStockExg(exg);
//			convert.setUnitWaste(bom.getUnitWaste());
			
//			convert.setUnitUsed(bom.getUnitUsed());
//			convert.setWaste(bom.getWaste());
//			convert.setPtNo(bom.getPtNo());
//			convert.setPtName(bom.getPtName());
//			convert.setPtSpec(bom.getPtSpec());
//			convert.setPtUnit(bom.getPtUnit());
//			convert.setStoreAmount(CommonUtils.getDoubleExceptNull(exg.getStoreAmount()) * 
//					CommonUtils.getDoubleExceptNull(bom.getUnitUsed()));
		}
		System.gc();

		// batchSaveOrUpdate(list);
	}
	
	// -------------------------------保存方法 结束-----------------------------//

	// -------------------------------删除方法 开始-----------------------------//
	
	// -------------------------------删除方法 结束-----------------------------//
}
