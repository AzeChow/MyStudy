/*
 * Created on 2004-6-9
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.custombase.entity.FactoryAndFactualCustomsRalationEntity;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;

/**
 * 海关编码DAO类 2010-07-07 check by hcl
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({ "unchecked"})
public class HsCodeDao extends BaseDao {

	/**
	 * 通过属性和值，查找Complex
	 * 
	 * @param sFields
	 *            属性
	 * @param sValue
	 *            值
	 * @return
	 */
	public List findComplex(String sFields, String sValue) {
		String hsql = "select c from Complex c left join fetch c.firstUnit b left join fetch c.secondUnit ";
		// + " left join fetch c.comUnit"
		// + " left join fetch c.curr"
		// + " left join fetch c.country";
		List list = null;
		if (sFields == null || sFields.equals("")) {
			list = this.getHibernateTemplate().find(
					hsql + " where (c.isOut <> '1' or c.isOut is null)");
		} else {
			hsql += " where (c.isOut <> '1' or c.isOut is null) and c."
					+ sFields + " like ? ";
			list = this.find(hsql, new Object[] { "%" + sValue + "%" });
		}
		return list;
	}

	/**
	 * 分页查询bcs报关商品资料
	 * 
	 * @param index
	 *            分页查询参数
	 * @param length分页查询参数
	 * @param property属性
	 * @param value值
	 * @param isLike是否模糊查询
	 * @param isImport是否主料
	 * @return
	 */

	public List findComplexBcsTenInnerMerge(int index, int length,
			String property, Object value, boolean isLike, boolean isImport) {
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select a from BcsTenInnerMerge a where a.scmCoi in (?,?) "
				+ " and a.seqNum is not null and a.scmCoi is not null ";
		if (isImport) {
			paramters.add(MaterielType.MATERIEL);
		} else {
			paramters.add(MaterielType.FINISHED_PRODUCT);
		}
		paramters.add(MaterielType.MACHINE);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		System.out.println("hsql=" + hql);
		return super.findPageList(hql, paramters.toArray(), index, length);
	}

	/**
	 * 查找商品编码
	 * 
	 * @param type
	 * @return
	 */
	public List findComplexByType(String type) {
		return this
				.find(
						"select a from Complex a "
								+ " left join fetch a.firstUnit "
								+ " left join fetch a.secondUnit"
								// + " left join fetch a.comUnit"
								// + " left join fetch a.curr"
								// + " left join fetch a.country "
								+ " where (a.isOut <> '1' or a.isOut is null) and a.scmCoi = ?",
						new Object[] { type });
	}

	/**
	 * 分页条件查询商品编码
	 * 
	 * @param index
	 *            分页查询参数
	 * @param length分页查询参数
	 * @param property属性
	 * @param value值
	 * @param isLike是否模糊查询
	 * @return
	 */
	public List findComplex(int index, int length, String property,
			Object value, boolean isLike, boolean getAll) {
		List res = null;
		
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Complex a "
				+ " left join fetch a.firstUnit "
				+ " left join fetch a.secondUnit "
				+ " where 1=1 ";
		if(!getAll) {
			hsql += " and (a.isOut <> '1' or a.isOut is null)";
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		
		res = super.findPageList(hsql, paramters.toArray(), index, length);
		
		return res;
	}

	/**
	 * 分页查询关系对应的设备
	 * 
	 * @param materielType
	 *            物料类型==设备
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 物料和物料与报关商品折算比的list
	 */
	public List findMachinebyFactoryAndFactualCustomsRalation(int index,
			int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec"
				+ ",a.statCusNameRelationHsn.cusUnit,a.statCusNameRelationHsn.complex"
				+ ",a.materiel.ptNo"
				+ " from FactoryAndFactualCustomsRalation as a  "
				+ " where a.statCusNameRelationHsn.materielType=? and a.company.id=? ";

		paramters.add(MaterielType.MACHINE);// 设备
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}
		long start = System.currentTimeMillis();
		List<Object[]> tmp = super.findPageList(hsql, paramters.toArray(),
				index, length);
		List result = new ArrayList();
		for (Object[] obj : tmp) {
			FactoryAndFactualCustomsRalationEntity fce = new FactoryAndFactualCustomsRalationEntity();
			fce.setHsCusName(obj[0] == null ? "" : (String) obj[0]);
			fce.setHsCusSpec(obj[1] == null ? "" : (String) obj[1]);
			fce.setHsCusUnit(obj[2] == null ? null : (Unit) obj[2]);
			fce.setComplex(obj[3] == null ? null : (Complex) obj[3]);
			fce.setMaterielNo(obj[4] == null ? "" : (String) obj[4]);
			result.add(fce);
		}
		System.out.println("查询时间：" + (System.currentTimeMillis() - start));
		return result;
	}

	/**
	 * 保存商品编码
	 * 
	 * @param complex商品编码
	 */
	public void saveComplex(Complex complex) {
		this.saveOrUpdate(complex);
	}
	
	/**
	 * 保存商检编码
	 * 
	 * @param complex商品编码
	 */
	public void saveCheckupComplex(CheckupComplex checkupComplex) {
		this.saveOrUpdate(checkupComplex);
	}
	/**
	 * 删除商品编码
	 * 
	 * @param complex
	 */
	public void deleteComplex(Complex complex) {
		this.delete(complex);
	}
	
//	public void deleteSpecialComplex(){
//		this.delete("delete a.code = '4911999090' from CustomsComplex a");
//	}

	/**
	 * 查询商品编码
	 * 
	 * @return
	 */
	public List findCustomsComplexNotInComplex() {
		// String hsql = "select c from CustomsComplex c left join fetch
		// c.firstUnit b "
		// + " left join fetch c.secondUnit"
		// + " where (c.isOut <> '1' or c.isOut is null) "
		// + " and c.code not in (select a.code from Complex as a "
		// + " where (a.isOut <> '1' or a.isOut is null))";
		// return this.find(hsql);
		List lsCustomsComplex = this.findCustomsComplex();
		List lsComplexCode = this.find("select a.code from Complex a");
		for (int i = lsCustomsComplex.size() - 1; i >= 0; i--) {
			if (lsComplexCode.contains(((CustomsComplex) lsCustomsComplex
					.get(i)).getCode())) {
				lsCustomsComplex.remove(i);
			}
		}
		return lsCustomsComplex;
	}

	/**
	 * 分页条件查询商品编码
	 * 
	 * @param index
	 *            分页查询参数
	 * @param length分页查询参数
	 * @param property属性
	 * @param value值
	 * @param isLike是否模糊查询
	 * @return
	 */
	public List findCustomsComplexNotInComplex(int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select c from CustomsComplex c left join fetch c.firstUnit b "
				+ " left join fetch c.secondUnit where 1=1 "
				+ " and c.code not in (select a.code from Complex as a)";

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  c." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  c." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找海关商品编码
	 * 
	 * @param index
	 *            分页查询参数
	 * @param length分页查询参数
	 * @param property属性
	 * @param value值
	 * @param isLike是否模糊查询
	 * @return
	 */
	public List findCustomsComplex(int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select c from CustomsComplex c left join fetch c.firstUnit  "
				+ " left join fetch c.secondUnit where 1=1 ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  c." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  c." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}
	/**
	 * 返回所有Complex
	 * 
	 * @return
	 */
	public List findCustomsComplex() {
		String hsql = "select c from CustomsComplex c left join fetch c.firstUnit b "
				+ " left join fetch c.secondUnit"
				+ " where (c.isOut <> '1' or c.isOut is null) ";
		return this.find(hsql);
	}
	/**
	 * 返回所有CheckupComplex
	 * 
	 * @return
	 */
	public List findCheckupComplex() {
//		String hsql = "select c from CheckupComplex c left join fetch c.complex.firstUnit b "
//				+ " left join fetch c.complex.secondUnit"
//				+ " where (c.complex.isOut <> '1' or c.complex.isOut is null) ";
		String hsql = "select a from CheckupComplex as a " +
				"left join fetch a.complex as c " +
				"left join fetch c.firstUnit as b " +
				"left join fetch c.secondUnit as c " +
				"where (c.isOut <> '1' or c.isOut is null) ";
		return this.find(hsql);
	}
	/**
	 * 通过CODE查询商检编码
	 * 
	 * @param code商品编码代码
	 * @return 商检编码（List）
	 */
	public List findCheckupComplex(String code) {
		return this
				.find(
						"select c from CheckupComplex c "
								+ " where (c.complex.isOut <> '1' or c.complex.isOut is null)"
								+ " and c.complex.code=?",
						new Object[] { code });
	}
	
	/**
	 * 通过CODE模糊查询海光商品编码
	 * 
	 * @param code商品编码代码
	 * @return
	 */
	public List findCustomsComplexLikeCode(String code) {
		return this
				.find(
						"select c from CustomsComplex c left join fetch c.firstUnit b "
								+ " left join fetch c.secondUnit"
								+ " where (c.isOut <> '1' or c.isOut is null) and  c.code like ?",
						new Object[] { code + "%" });
	}

	/**
	 * 通过CODE查询海光商品编码
	 * 
	 * @param code商品编码代码
	 * @return 商品编码（List）
	 */
	public List findCustomsComplex(String code) {
		return this
				.find(
						"select c from CustomsComplex c left join fetch c.firstUnit b "
								+ " left join fetch c.secondUnit"
								+ " where (c.isOut <> '1' or c.isOut is null) and  c.code = ?",
						new Object[] { code });
	}

	/**
	 * 通过code查询海光商品编码
	 * 
	 * @param code商品编码代码
	 * @return 商品编码
	 */
	public Complex findComplexByCode(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Complex a where a.code=? and (a.isOut <> '1' or a.isOut is null)";
			List list = find(hsql, new Object[] { sValue });
			if (list.size() > 0) {
				return (Complex) list.get(0);
			}
		}
		return null;
	}

	/**
	 * 查询商品编码为isOut不为“1”或者为空的
	 * 
	 * @return
	 */
	public List<Complex> findComplex() {
		String hsql = "select c from Complex c left join fetch c.firstUnit b "
				+ " left join fetch c.secondUnit"
				+ " where (c.isOut <> '1' or c.isOut is null) ";
		return this.find(hsql);
	}

	/**
	 * 查询商品编码
	 * 
	 * @return
	 */
	public List findComplexCode() {
		String hsql = "select c from Complex c left join fetch c.firstUnit b "
				+ " left join fetch c.secondUnit";
		return this.find(hsql);
	}

	/**
	 * 查询商品编码个数 是否有四码存在
	 * 
	 * @param fourCode
	 *            模糊商品编码代码
	 * @return
	 */
	public long findComplexLikeFourCodeCount(String fourCode) {
		if (fourCode != null) {
			String hsql = "select count(*) from Complex where code like ? and (isOut <> '1' or isOut is null)";
			List list = find(hsql, new Object[] { fourCode + "%" });
			if (list.size() > 0) {
				return (Long) list.get(0);
			}
		}
		return 0;
	}

	/**
	 * 
	 * 领证商品备注表
	 */
	public List findLicensenote(String sFields, String sValue) {
		return findCustoms("LicenseNote", sFields, sValue);
	}
	/**
	 * 获取表属性的最大值
	 * 
	 * @param className
	 *            所需查询的表
	 * @param seqNum
	 *            要查询的属性
	 * @return
	 */
	public String getNum(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a ");
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 通过code 证件代码资料
	 * 
	 * @param code证件代码资料的编码
	 * @return
	 */
	public List findCustomsDocuName(String code) {
		return this.find("select a.name from LicenseDocu a where a.code = ?",
				new Object[] { code });

	}

	/**
	 * 查询海关商品编码（不在商品编码里）
	 * 
	 * @return
	 */
	public List findCustomsComplexNotInComplex1() {
		List lsCustomsComplex = this.findCustomsComplex();
		// List lsComplexCode = this.find("select a.code from Complex a");
		List IsComplexCode = this.find("select a.code from Complex a");
		for (int i = lsCustomsComplex.size() - 1; i >= 0; i--) {
			if (IsComplexCode.contains(((CustomsComplex) lsCustomsComplex
					.get(i)).getCode())) {
				// lsCustomsComplex.remove(i);
				lsCustomsComplex.get(i);

			}
		}
		return lsCustomsComplex;
	}

	// .equals(((CustomsComplex)IsCustomsComplex).getCode())
	/**
	 * 
	 * 查询商品编码（不在海关商品编码里）
	 * 
	 */
	public List findComplexNotInCustomsComplex1() {
		List IsComplex = this.findComplex();
		// List IsCustomsComplex = this.findCustomsComplex();
		List IsCustomsComplexCode = this
				.find("select a.code from CustomsComplex a");
		for (int i = IsComplex.size() - 1; i >= 0; i--) {

			// if(((Complex)IsComplex.get(i)).getCode().contains(((CustomsComplex)IsCustomsComplexCode).getCode())){
			// IsComplex.remove(i);
			// }
			if (IsCustomsComplexCode.contains(((Complex) IsComplex.get(i))
					.getCode())) {
				IsComplex.remove(i);
			}
			// if(((Complex)IsComplex).getName().equals(((CustomsComplex)IsCustomsComplex).getName())){
			// IsComplex.remove(i);
			// }
		}
		return IsComplex;
	}

	/**
	 * 查询商品编码（不在海关商品编码里）分页查询方法
	 * 
	 * @param index
	 *            分页查询参数
	 * @param length分页查询参数
	 * @param property属性
	 * @param value值
	 * @param isLike是否模糊查询
	 * @return
	 */
	public List findComplexNotInCustomsComplex(int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select c from Complex c left join fetch c.firstUnit b "
				+ " left join fetch c.secondUnit where 1=1 "
				+ " and c.code not in (select a.code from CustomsComplex as a)";

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  c." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  c." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 通过商品编码code，查询海关商品编码
	 * 
	 * @param code商品编码
	 * @return
	 */
	public Boolean findCustomsComplexByCode(String code) {
		List list = this.find(
				"select a from CustomsComplex a where a.code = ?",
				new Object[] { code });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;

	}

	/**
	 * 根据商品编码更新许可证号
	 * 
	 * @param ccontrol
	 * @param code
	 */
	public void updateCustomsComplexByCode(String ccontrol, String code) {
		this.batchUpdateOrDelete(
				" update CustomsComplex a set a.ccontrol=? where a.code = ?",
				new Object[] { ccontrol, code });
	}

	/**
	 * 查询海关商品编码的编码
	 * 
	 * @return
	 */
	public List findCustomsComplexCode() {
		return this.find("select a.code from CustomsComplex a ");

	}

	/**
	 * 查询证件代码资料
	 * 
	 * @param code
	 *            证件代码资料的编码
	 * @return
	 */
	public Boolean findLicenseDocuByCode(String code) {
		List list = this.find("select a from LicenseDocu a where a.code = ?",
				new Object[] { code });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 通过商品名称，查询海关商品编码
	 * 
	 * @param name
	 * @return
	 */
	public Boolean findCustomsComplexByName(String name) {
		List list = this.find(
				"select a from CustomsComplex a where a.name = ?",
				new Object[] { name });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询商品编码（不在海关商品编码）
	 * 
	 * @return
	 */
	public List findComplexNotInCustomsComplex() {

		List IsComplex = this.findComplex();

		for (int i = IsComplex.size() - 1; i >= 0; i--) {

			String code = ((Complex) IsComplex.get(i)).getCode();
			String name = ((Complex) IsComplex.get(i)).getName();
			// System.out.println(" $$ code $$"+code);
			if (findCustomsComplexByCode(code)) {
				// System.out.println(" $$$$$$$IsComplex$$$$"+IsComplex);
				IsComplex.remove(i);
			}
			// || findCustomsComplexByName(name)
			// if(findCustomsComplexByName(name)){
			// IsComplex.remove(i);
			// }
		}
		return IsComplex;

	}

	/**
	 * 查询商品编码个数
	 * 
	 * @param code商品编码的编码
	 * @return
	 */
	public List checkComplexCode(String code) {
		return this.find("select count (a) from Complex a where a.code = ?",
				new Object[] { code });
	}

	// public Boolean findCustomsComplex(List customsComplex){
	// List list = this.find("select a from CustomsComplex a",new
	// Object[]{customsComplex});
	// if(list != null && list.size()>0){
	// return true;
	// }
	// return false ;
	// }
	// public List findComplexNotInCustomsComplex(){
	// List IsComplex = this.findComplex();
	//
	// for (int i= IsComplex.size()-1;i>=0;i--){
	// if(findCustomsComplex(IsComplex)){
	// IsComplex.remove(i);
	// }
	// }
	// return IsComplex;
	// }
	/**
	 * 查询所以的商品编码
	 */
	public List findComplexAll() {
		String hsql = "select a from Complex a";
		return this.find(hsql);
	}

	/**
	 * 查询海关商品编码
	 * 
	 * @param impexpfalg进出口标志
	 * @return
	 */
	public List findComplexForCheckupNotIn(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select a from  Complex   a    where 1=1 ";
		// if (impexpfalg == null) {
		//
		// } else if (impexpfalg == ImpExpFlag.IMPORT) {
		// hsql += " and a.ccontrol like ? ";
		// para.add("%A%");
		// } else if (impexpfalg == ImpExpFlag.EXPORT) {
		// hsql += " and a.ccontrol like ? ";
		// para.add("%B%");
		// }
		hsql += " and a.code not in  ";
		hsql += " ( select  a.complex.code  from CheckupComplex  a   where  a.impExpFlag =? ) ";
		para.add(impexpfalg);
		// hsql += " and a.code  in  ( select  a.code  from Complex  a  ) ";
		return this.find(hsql, para.toArray());
	}

	
	/**
	 * 通过进出口标志，查询检查成品
	 * 
	 * @param impexpfalg进出口标志
	 * @return
	 */
	public List findComplexByFlag(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select  a from CheckupComplex  a    where 1=1 ";
		if (impexpfalg != null) {
			hsql += " and a.impExpFlag = ? ";
			para.add(impexpfalg);
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查询所有的运势方式
	 * 
	 * @return
	 */
	public List findTransferMode() {
		return this.find("select a from Transf a");
	}
	/**
	 * 删除报关行
	 * 
	 * @return
	 */
	public void deleteCustomsBroker(CustomsBroker customsBroker) {
		this.delete(customsBroker);
	}
	/**
	 * 保存报关行
	 * 
	 * @param customsBroker报关行
	 */
	public void saveCustomsBroker(CustomsBroker customsBroker) {
		this.saveOrUpdate(customsBroker);
	}
}
