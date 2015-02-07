/*
 * Created on 2004-12-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import java.util.List;
import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.WareSet;



/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class DbCustomDao extends HibernateDaoSupport {
	
	/**
	 * 
	 */
	public DbCustomDao() {
		super();
	}
	
	protected  void initDao() throws Exception{
		super.initDao();
		this.getHibernateTemplate().setCacheQueries(true);
	}
	
    /**
     * 地区代码表
     * @param code
     * @return
     */
	public District getDistrict(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from District a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (District) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 币制
	 * @param code
	 * @return
	 */
	public Curr getCurr(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from Curr a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (Curr) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 企业性质
	 */
	public CoType getCoType(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from CoType a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (CoType) list.get(0);
			}
		}
		return null;
	}
	public CoType getCoTypeByName(String name){
		if (name != null){
			  List list = this.getHibernateTemplate().find("select a from CoType a where a.name=? and (a.isOut <>'1' or a.isOut is null)",
				    	new Object[]{name});
			  if (list != null && list.size()>0){
				  return (CoType) list.get(0);
			  }
		}
		return null;
	}
	/**
	 * 主管海关
	 */
	public Customs getCustoms(String code){
        if (code != null){
			List list = this.getHibernateTemplate().find("select a from Customs a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (Customs) list.get(0);
			}
        }
		return null;
	}
	/**
	 * 主管海关
	 */
	public Customs getCustomsByName(String name){
		if (name != null){
			List list = this.getHibernateTemplate().find("select a from Customs a where a.name=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{name});
			if (list != null && list.size()>0){
				return (Customs) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 外经部门
	 */
	public RedDep getRedDep(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from RedDep a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (RedDep) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 征免方式
	 */
	public LevyMode getLevyMode(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from LevyMode a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (LevyMode) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 征免性质
	 */
	public LevyKind getLevyKind(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from LevyKind a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (LevyKind) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 保税方式
	 */
	public PayMode getPayMode(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from PayMode a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (PayMode) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 加工种类
	 */
	public MachiningType getMachiningType(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from MachiningType a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (MachiningType) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 申报标志
	 */
	public ApplicationMode getApplicationMode(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from ApplicationMode a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (ApplicationMode) list.get(0);
			}
		}
		return null;
	}
	public SrtJzx getSrtJzx(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from SrtJzx a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (SrtJzx) list.get(0);
			}
		}
		return null;
	}
	public SrtTj getSrtTj(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from SrtTj a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (SrtTj) list.get(0);
			}
		}
		return null;
	}
	 /*
	 * 根据编码查询计量方式
	 */
	public Unit Findbyidunit(String sValue){
		if (sValue != null){
			String hsql="select a from Unit a where a.code=?  and (a.isOut <>'1' or a.isOut is null)";
			List list=this.getHibernateTemplate().find(hsql,new Object[]{sValue});
			if (list != null && list.size()>0){
				return (Unit)list.get(0);
			}
		}
	    return null;
		
	}
	/**
	 * 仓库
	 */
	public WareSet getWareSet(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from WareSet a where a.code=?",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (WareSet)list.get(0);
			}
		}
		return null;
	}
	/**
	 * 国家
	 */
	public Country getCountry(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from Country a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code});
			if (list != null && list.size()>0){
				return (Country)list.get(0);
			}
		}
		return null;
	}
       
}
