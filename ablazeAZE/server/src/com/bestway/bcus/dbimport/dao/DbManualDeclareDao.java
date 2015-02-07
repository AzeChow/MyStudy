/*
 * Created on 2004-12-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bestway.bcus.backup.SortingEntityClass;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.custombase.entity.CustomBaseVersion;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptInitBill;



/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class DbManualDeclareDao extends HibernateDaoSupport { //
	
	/**
	 * 
	 */
	public DbManualDeclareDao() {
		super();
	}
	
	protected  void initDao() throws Exception{
		super.initDao();
		this.getHibernateTemplate().setCacheQueries(true);
	}
	
	public void saveEmsEdiTrHead(EmsEdiTrHead head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveEmsMergerHead(EmsEdiMergerHead head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveEmsMergerImgAfter(EmsEdiMergerImgAfter imgAfter) throws DataAccessException{
		getHibernateTemplate().save(imgAfter);
	}
	public void saveEmsMergerExgAfter(EmsEdiMergerExgAfter exgAfter) throws DataAccessException{
		getHibernateTemplate().save(exgAfter);
	}
	public void saveEmsMergerExgBefore(EmsEdiMergerExgBefore exgBefore) throws DataAccessException{
		getHibernateTemplate().save(exgBefore);
	}
	public void saveEmsHead(EmsHeadH2k head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveEmsHeadExg(EmsHeadH2kExg exg) throws DataAccessException{
		getHibernateTemplate().save(exg);
	}
	public void saveEmsHeadH2kVersion(EmsHeadH2kVersion version) throws DataAccessException{
		getHibernateTemplate().save(version);
	}
	public void saveEmsHeadH2kFas(EmsHeadH2kFas head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveCheckHead(CheckHead head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveCustomsEnvelopRequestBill(FptAppHead head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
//	public void saveCustomsEnvelopBill(CustomsEnvelopBill head) throws DataAccessException{
//		getHibernateTemplate().save(head);
//	}
	public void saveTransferFactoryBill(FptBillHead head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveTransferFactoryInitBill(FptInitBill head) throws DataAccessException{
		getHibernateTemplate().save(head);
	}
	public void saveAclGroup(AclGroup head) throws DataAccessException{
		getHibernateTemplate().saveOrUpdate(head);
	}
	public void saveObject(Object obj){
		getHibernateTemplate().save(obj);
	}
	public List findObject(String className){
		return getHibernateTemplate().find("from "+className);
	}
	public List findVersionByClassName(String className){
		return getHibernateTemplate().find("select a from CustomBaseVersion a where a.tableName=?",
				new Object[]{className});
	}
	public void deleteObjectAll(String className){
		getHibernateTemplate().deleteAll(findObject(className));
	}
	/**
	 * 返回用户对象
	 * */
	public AclUser getAclUser(String loginName,Company company){
		if (loginName != null){
			List list = this.getHibernateTemplate().find("select a from AclUser a where a.loginName=? and a.company.id=?",
					new Object[]{loginName,company});
			if (list != null && list.size()>0){
				return (AclUser) list.get(0);
			}
		}
		return null;
	}
	
	
	
	public void deleteDbAll(){
		List list = SortingEntityClass.getInstance(				
				this.getSessionFactory()).getSortedClassNameByRefDepth();
		for (int i=list.size()-1; i>-1; i--){			
			String className = (String)list.get(i);
			if (className.equals("com.bestway.bcus.dbimport.entity.DBDataConnect") || className.equals("com.bestway.bcus.custombase.entity.CustomBaseVersion")){
			   continue;
			}			
			System.out.println("正在删除数据的类："+(String)list.get(i)+"...");
			deleteObjectAll(className);
		}
		deleteCustomBaseVersion("GbToBig");
		deleteCustomBaseVersion("DriverList");
		deleteCustomBaseVersion("ClassList");
		deleteCustomBaseVersion("FieldList");
		deleteCustomBaseVersion("BillType");
	}
	private void deleteCustomBaseVersion(String className){
		List list = findVersionByClassName(className.toLowerCase());
		if (list.size()>0){
			CustomBaseVersion obj = (CustomBaseVersion) list.get(0);
			this.getHibernateTemplate().delete(obj);
		}
	}
	public void editVersion(String className,String version){ //className,date
		List list = findVersionByClassName(className.toLowerCase());
		if (list.size()>0){
			CustomBaseVersion obj = (CustomBaseVersion) list.get(0);
			obj.setVersion(version);
			saveObject(obj);
		} else {
			CustomBaseVersion obj = new CustomBaseVersion();
			obj.setTableName(className.toLowerCase());
			obj.setVersion(version);
			saveObject(obj);
		}
	}
}
