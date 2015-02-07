package com.bestway.bcs.bcsinnermerge.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;

import com.bestway.common.BaseDao;
import com.bestway.common.MaterielType;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;

public class test extends BaseDao {
	public void init() {  
		HashMap<Integer,Materiel> map_product = new HashMap<Integer,Materiel>();
        HashMap<Integer,Materiel> map_materil= new HashMap<Integer,Materiel>();
		
		Materiel materil = findMateriel();
		if(materil == null){
			System.out.println("Materiel is null ");
			return ;
		}
		ScmCoi scmCoi = new ScmCoi();
		scmCoi.setCode("1");        
		scmCoi.setCoiProperty(MaterielType.FINISHED_PRODUCT);
		scmCoi.setName("成品");
		scmCoi.setId(null);
		scmCoi.setCompany(materil.getCompany());
		
		this.getHibernateTemplate().save(scmCoi);
		
		for(int i=0;i<1000;i++){
			Materiel m = new Materiel();
			m.setId(null);
			m.setScmCoi(scmCoi);
            m.setFactoryName("手机 "+i);
            m.setFactorySpec("小号"+i);
			this.getHibernateTemplate().save(m);
			map_product.put(i,m);
		}
		
		scmCoi = new ScmCoi();
		scmCoi.setCode("2");
		scmCoi.setCoiProperty(MaterielType.MATERIEL);
		scmCoi.setName("料件");
		scmCoi.setId(null);
		scmCoi.setCompany(materil.getCompany());
		this.getHibernateTemplate().save(scmCoi);
		
		for(int i=0;i<60000;i++){
			Materiel m = new Materiel();
			m.setId(null);
			m.setScmCoi(scmCoi);
            m.setFactoryName("机心"+i);
            m.setFactorySpec("小号"+i);
			this.getHibernateTemplate().save(m);
            map_materil.put(i,m);
		}
		
		
        
        for(int i=0;i<1000;i++){			
			MaterialBomMaster materialBomMaster = new MaterialBomMaster();
			materialBomMaster.setCompany(materil.getCompany() );
			materialBomMaster.setMateriel(map_product.get(Double.valueOf(Math.random()*1000).intValue())); //成品
			this.getHibernateTemplate().save(materialBomMaster);
			for(int v =0;v<10;v++){
				MaterialBomVersion version = new MaterialBomVersion();
				version.setVersion(v);
				version.setBeginDate(new Date());
				version.setEndDate(new Date());
				version.setMaster(materialBomMaster);
				version.setCompany(materil.getCompany() );
				this.getHibernateTemplate().save(version);
				for(int j=0;j<90;j++){
					MaterialBomDetail materialBomDetail = new MaterialBomDetail();
					materialBomDetail.setCompany(materil.getCompany());
					materialBomDetail.setMateriel(map_materil.get(Double.valueOf(Math.random()*60000).intValue()));//料件
					materialBomDetail.setUnitUsed(18.8);
					materialBomDetail.setUnitWaste(0.11);
					materialBomDetail.setWaste(0.005);
					materialBomDetail.setVersion(version);	
					this.getHibernateTemplate().save(materialBomDetail);
				}
			}
		}
       
    }
	
	
	
	public Materiel findMateriel() throws HibernateException {
	       List list =  this.find("select a from Materiel a where a.id = '4028811b077429670107743122fd0004' ");
	       if(!list.isEmpty() ){
	    	   return (Materiel)list.get(0);
	       }
	       return null;
	  }
	
	
	
	
}
