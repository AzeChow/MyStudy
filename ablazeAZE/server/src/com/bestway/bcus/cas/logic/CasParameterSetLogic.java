/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import org.hibernate.SessionFactory;

import com.bestway.bcus.LocalSessionFactoryBeanSubClass;
import com.bestway.bcus.cas.dao.CasParameterSetDao;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingOption;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CasParameterSetLogic {

	/**
	 * 海关帐参数设置Dao
	 */
	public CasParameterSetDao casParameterSetDao = null;

	/**
	 * 取得海关帐参数设置Dao
	 * 
	 * @return 海关帐参数设置Dao
	 */
	public CasParameterSetDao getCasParameterSetDao() {
		return casParameterSetDao;
	}

	/**
	 * 设计海关帐参数设置Dao
	 * 
	 * @param casParameterSetDao
	 *            海关帐参数设置Dao
	 */
	public void setCasParameterSetDao(CasParameterSetDao casParameterSetDao) {
		this.casParameterSetDao = casParameterSetDao;
	}

	/**
	 * 查找记帐年度对象
	 * 
	 * @return 记帐年度
	 */
	public WriteAccountYear findWriteAccountYear() {
		WriteAccountYear writeAccountYear = this.casParameterSetDao
				.findWriteAccountYear();
		if (writeAccountYear != null) {
			CommonUtils.setYear(writeAccountYear.getYear().toString());
		}
		return writeAccountYear;
	}

	/**
	 * 保存记帐年度对象
	 * 
	 * @param writeAccountYear
	 *            记帐年度
	 */
	public void saveWriteAccountYear(WriteAccountYear writeAccountYear) {
		this.casParameterSetDao.saveWriteAccountYear(writeAccountYear);
		if (writeAccountYear != null) {
			CommonUtils.setYear(writeAccountYear.getYear().toString());
		}
	}

	/**
	 * 重新加载Hibernate SessionFactory
	 */
	public void reloadSessionFactory() {
		try {
			CommonUtils.setLoadingSessionFactory(true);
			LocalSessionFactoryBeanSubClass localSessionFactory = (LocalSessionFactoryBeanSubClass) CommonUtils
					.getContext().getBean("&sessionFactory");
			localSessionFactory.destroy();
			localSessionFactory.afterPropertiesSet();
			SessionFactory sessionFactory = (SessionFactory) localSessionFactory
					.getObject();
			String[] beanNames = CommonUtils.getContext()
					.getBeanDefinitionNames();
			for (int i = 0; i < beanNames.length; i++) {
				Object bean = CommonUtils.getContext().getBean(beanNames[i]);
				if (bean instanceof BaseDao) {
					((BaseDao) bean).setSessionFactory(sessionFactory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("重新加载SessionFactory出错", e);
		} finally {
			CommonUtils.setLoadingSessionFactory(false);
			System.gc();
		}
	}

	/**
	 * 查找单据对应选项
	 * 
	 * @return 单据对应选项
	 */
	public BillCorrespondingOption findBillCorrespondingOption() {
		BillCorrespondingOption billCorrespondingOption = this.casParameterSetDao
				.findBillCorrespondingOption();
		if (billCorrespondingOption != null) {
			CommonUtils.setUpdateUnitPrice(billCorrespondingOption
					.getIsCalculatePrice());
		}
		return billCorrespondingOption;
	}

	/**
	 * 保存单据对应选项
	 * 
	 * @param temp
	 *            单据对应选项
	 */
	public void saveBillCorrespondingOption(BillCorrespondingOption temp) {
		this.casParameterSetDao.saveBillCorrespondingOption(temp);
		if (temp != null) {
			CommonUtils.setUpdateUnitPrice(temp.getIsCalculatePrice());
		}
	}

	/**
	 * 查找单据对应控制对象
	 * 
	 * @return 单据对应控制对象
	 */
	public BillCorrespondingControl findBillCorrespondingControl() {
		BillCorrespondingControl b = this.casParameterSetDao
				.findBillCorrespondingControl();
		if (b != null) {
			CommonUtils.setBillCorrespondingControl(b);
		}
		return b;
	}

	/**
	 * 保存单据对应控制对象
	 * 
	 * @param temp
	 *            单据对应控制对象
	 */
	public void saveBillCorrespondingControl(BillCorrespondingControl temp) {
		this.casParameterSetDao.saveBillCorrespondingControl(temp);
		if (temp != null) {
			CommonUtils.setBillCorrespondingControl(temp);
		}
	}
}