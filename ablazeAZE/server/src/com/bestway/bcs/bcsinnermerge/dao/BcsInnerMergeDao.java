/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomExg;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeDataOrder;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao checked by 陈井彬 2008.11.30
 * 
 * @author ls 内部归并DAO接口
 */
@SuppressWarnings("unchecked")
public class BcsInnerMergeDao extends BaseDao {
	/**
	 * 查找物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMerge() {
		return this.find("select a from BcsInnerMerge a "
				+ "  left join fetch a.materiel "
				+ "  left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? order by a.bcsTenInnerMerge.seqNum",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查找物料与报关对应表keys key=materiel.ptNo/bcsTenInnerMerge.seqNum
	 * 
	 * @param meterialType
	 * @return
	 */
	public List<String> findBcsInnerMergeKeys(String materialType) {
		return this
				.find("select case when b=null then CONCAT(a.materiel.ptNo,'/') else CONCAT(a.materiel.ptNo, CONCAT('/',cast(b.seqNum as string))) end"
						+ " from BcsInnerMerge a "
						+ " left join a.bcsTenInnerMerge b"
						+ " where a.company= ? and a.materielType = ? order by a.bcsTenInnerMerge.seqNum",
						new Object[] { CommonUtils.getCompany(), materialType });
	}

	/**
	 * 过滤已经在 报关商品资料 中存在的 自用商品编码
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
	 * @return List 是Complex型，自用商品编码
	 */
	public List<BcsInnerMerge> findComplexOutOfBcsTenInnerMerge(int index,
			int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Complex a "
				+ " left join fetch a.firstUnit "
				+ " left join fetch a.secondUnit "
				+ " where (a.isOut <> '1' or a.isOut is null) ";
		// + "and a.code not in (select b.complex.code from BcsTenInnerMerge
		// b))";

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMerge(String materielType) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.materielType=?  "
				+ "   order by a.bcsTenInnerMerge.seqNum asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				materielType });
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param meteriel
	 *            物料
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMerge(Materiel meteriel) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "and a.materiel.id=?  "
				+ "   order by a.bcsTenInnerMerge.seqNum asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),meteriel.getId()});
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(物料一对多的对应关系)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMergeOneToMany(String materielType) {
		String hsql = "select distinct b1 from BcsInnerMerge b1, BcsInnerMerge b2 "
				+ "where b1.materiel.id = b2.materiel.id and b1.bcsTenInnerMerge.id != b2.bcsTenInnerMerge.id "
				+ "and b1.materielType = ? and b1.company = ? "
				+ "order by b1 ";
		return this.find(hsql,
				new Object[] { materielType, CommonUtils.getCompany() });
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public BcsInnerMerge findBcsInnerMergeByPtNoAndSeqNum(String ptNo,
			Integer seqNum) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? "
				+ "	and a.materiel.ptNo=? and  a.bcsTenInnerMerge.seqNum =? ";
		List<BcsInnerMerge> list = this.find(hsql,
				new Object[] { CommonUtils.getCompany(), ptNo, seqNum });
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public BcsInnerMerge findBcsInnerMergeByPtNo(String ptNo) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.materiel.ptNo=?  ";
		List list = this.find(hsql, new Object[] { CommonUtils.getCompany(),
				ptNo });
		if (list.size() > 0) {
			return (BcsInnerMerge) list.get(0);
		}
		return null;
	}
	
	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMergeByPtNos(List<String> ptNos) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.isUsing = ? ";
		List<BcsInnerMerge> rs = new ArrayList<BcsInnerMerge>();
		String where = "";
		for(int i = 0 ;i < ptNos.size();i+=500){
			 where = " and a.materiel.ptNo in ('"+StringUtils.join(ptNos.subList(i, i+500 < ptNos.size() ? i+500 : ptNos.size()).toArray(),"','")+"') ";
			 rs.addAll(this.find(hsql+where, new Object[] { CommonUtils.getCompany(),Boolean.TRUE}));
		}
		return rs;
	}

	/**
	 * 根据指定料号列表 查询对应关系。
	 * 
	 * @param ptNoSet
	 * @return
	 */
	public List<BcsInnerMerge> findBcsInnerMergeByPtNoSet(Set<String> ptNoSet) {
		List<BcsInnerMerge> returnList = new ArrayList<BcsInnerMerge>();
		String hqlHead = "select a from BcsInnerMerge a "
				+ "		left join fetch a.materiel "
				+ "		left join fetch a.bcsTenInnerMerge "
				+ " where a.company.id = ? ";
		StringBuilder hql = new StringBuilder(hqlHead);

		if (ptNoSet != null && !ptNoSet.isEmpty()) {
			Iterator<String> it = ptNoSet.iterator();
			hql.append(" and a.materiel.ptNo in ('" + it.next());
			int i = 1;
			while (it.hasNext()) {
				hql.append("','" + it.next());
				if (i % 1000 == 0) {
					hql.append("') order by a.isUsing");

					returnList.addAll(find(hql.toString(),
							new Object[] { CommonUtils.getCompany().getId() }));

					hql.setLength(0);

					if (it.hasNext()) {
						hql.append(hqlHead + " and a.materiel.ptNo in ('"
								+ it.next());
					}
				}
			}

			if (hql.length() > 0) {
				hql.append("') order by a.isUsing");
				returnList.addAll(find(hql.toString(),
						new Object[] { CommonUtils.getCompany().getId() }));
			}
		}

		return returnList;
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List findBcsInnerMerge(String type, String tenCode, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.materielType=?  ";
		paramters.add(CommonUtils.getCompany());
		paramters.add(type);
		if (tenCode != null) {
			hsql += " and a.bcsTenInnerMerge.complex.code=? ";
			paramters.add(tenCode);
		}
		if (seqNum != null) {
			hsql += " and a.bcsTenInnerMerge.seqNum=? ";
			paramters.add(seqNum);
		}
		hsql += "   order by a.bcsTenInnerMerge.complex.code asc ";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMergeByInnerMerge(String type,
			String tenCode, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.bcsTenInnerMerge from BcsInnerMerge a "
				+ "  where a.company= ? " + "	and a.materielType=?  ";
		paramters.add(CommonUtils.getCompany());
		paramters.add(type);
		if (tenCode != null) {
			hsql += " and a.bcsTenInnerMerge.complex.code=? ";
			paramters.add(tenCode);
		}
		if (seqNum != null) {
			hsql += " and a.bcsTenInnerMerge.seqNum=? ";
			paramters.add(seqNum);
		}
		hsql += "   order by a.bcsTenInnerMerge.seqNum asc ";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List findBcsTenInnerMergeByInnerMerge(String materielType) {
		String hsql = "select distinct a.bcsTenInnerMerge from BcsInnerMerge a "
				+ "  where a.company= ? "
				+ "	and a.materielType=?  "
				+ "	  and a.bcsTenInnerMerge.seqNum is not null "
				+ "   order by a.bcsTenInnerMerge.seqNum asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				materielType });
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMergeByTenInnerMerge(
			BcsTenInnerMerge bcsTenInnerMerge) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "	  and a.bcsTenInnerMerge.id= ? "
				+ "   order by a.materiel.ptNo asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				bcsTenInnerMerge.getId() });
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List findBcsInnerMergeByMerge(String materielType) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.materielType=?  "
				+ "	  and a.bcsTenInnerMerge.seqNum is not null "
				+ "   order by a.bcsTenInnerMerge.seqNum asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				materielType });
	}

	/**
	 * 分页查询未归并
	 * 
	 * @param type
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcsInnerMergeDataByTypeNoTen(String type, int index,
			int length, String property, Object value, boolean isLike) {
		String hsql = "";
		List listMateriel = new ArrayList();
		List<Object> paramters = new ArrayList<Object>();
		hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company.id= ? " + "	and a.materielType=?  "
				+ "	  and ( a.bcsTenInnerMerge.seqNum is null ) ";
		paramters.clear();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(type);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%' ";
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		listMateriel = super.findPageList(hsql, paramters.toArray(), index,
				length);
		return listMateriel;
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@SuppressWarnings("unchecked")
	public List findBcsInnerMerge(String materielType, int index, int length,
			String property, Object value, boolean isLike, boolean isNoMerge) {
		if (isNoMerge) {
			// 如果是未归并
			List list = this.findBcsInnerMergeDataByTypeNoTen(materielType,
					index, length, property, value, isLike);
			return list;
		} else {
			List returnList = new ArrayList();
			List listTenCode = new ArrayList();
			List listMaterielCode = new ArrayList();
			int hasTenCodeCount = 0;
			/**
			 * 找十码不为空的数据
			 */
			List<Object> paramters = new ArrayList<Object>();

			String hsql = "select a from BcsInnerMerge a "
					+ " left join fetch a.materiel "
					+ " left join fetch a.bcsTenInnerMerge "
					+ "  where a.company.id= ? and a.materielType=?  "
					+ "	and a.bcsTenInnerMerge.seqNum is not null ";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add(materielType);
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a." + property + " like ?  ";
					paramters.add(value + "%");
				} else {
					hsql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
			listTenCode = super.findPageList(hsql, paramters.toArray(), index,
					length);
			/**
			 * 获取十码的个数
			 */
			hasTenCodeCount = this.findTenCodeCount(materielType, property,
					value, isLike);

			if (listTenCode.size() == 0) {
				/**
				 * 找十码为空的数据
				 */
				hsql = "select a from BcsInnerMerge a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.bcsTenInnerMerge "
						+ "  where a.company.id= ? "
						+ "	and a.materielType=?  "
						+ "	  and ( a.bcsTenInnerMerge.seqNum is null ) ";
				paramters.clear();
				paramters.add(CommonUtils.getCompany().getId());
				paramters.add(materielType);
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like ?  ";
						paramters.add(value + "%");
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				/**
				 * 当十码的大小等于0说明 如果 fourRemainCount > 0 的话肯定已经被用了一些数据了 index 应该等于
				 */
				int tenCodeIndex = index - hasTenCodeCount;
				listMaterielCode = super.findPageList(hsql,
						paramters.toArray(), tenCodeIndex, length);

			} else if (listTenCode.size() < length) {
				/**
				 * 找十码为空的数据
				 */
				hsql = "select a from BcsInnerMerge a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.bcsTenInnerMerge "
						+ "  where a.company.id= ? "
						+ "	and a.materielType=?  "
						+ "	  and ( a.bcsTenInnerMerge.seqNum is null ) ";
				paramters.clear();
				paramters.add(CommonUtils.getCompany().getId());
				paramters.add(materielType);
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like ?  ";
						paramters.add(value + "%");
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				listMaterielCode = super.findPageList(hsql,
						paramters.toArray(), 0, length - (listTenCode.size()));
			}
			returnList.addAll(listTenCode);
			returnList.addAll(listMaterielCode);
			return returnList;
		}

	}

	/**
	 * 在物料与报关对应表里获取十码的个数
	 * 
	 * @param type
	 *            物料类别
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return int 十码的个数
	 */
	private int findTenCodeCount(String type, String property, Object value,
			boolean isLike) {
		int hasTenCodeCount = 0;
		/**
		 * 找四码不为空的数据
		 */
		List<Object> paramters = new ArrayList<Object>();

		/**
		 * 找四码为空十码不为空的数据
		 */
		String hsql = "select count(*)from BcsInnerMerge a "
				+ "  where a.company.id= ? " + "	and a.materielType=?  "
				+ "	  and   a.bcsTenInnerMerge.seqNum is not null  ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(type);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}

		/**
		 * 获取十码的个数及余数
		 */
		List listTenCodeCount = super.find(hsql, paramters.toArray());

		if (listTenCodeCount.size() > 0 && listTenCodeCount.get(0) != null) {
			hasTenCodeCount = Integer.parseInt(listTenCodeCount.get(0)
					.toString());

		}
		return hasTenCodeCount;
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param BcsInnerMerge
	 *            物料与报关对应表
	 */
	public void saveBcsInnerMerge(BcsInnerMerge BcsInnerMerge) {
		this.saveOrUpdate(BcsInnerMerge);
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param BcsInnerMerge
	 *            物料与报关对应表
	 */
	public void deleteBcsInnerMerge(BcsInnerMerge BcsInnerMerge) {
		this.delete(BcsInnerMerge);
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param list
	 *            物料与报关对应表
	 */
	public void saveBcsInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param list
	 *            物料与报关对应表
	 */
	public void deleteBcsInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.delete(list.get(i));
		}
	}

	/**
	 * 获取最大报关商品资料的凭证序号，但是是物料与报关对应表里对应的报关商品资料
	 * 
	 * @param materielType
	 *            物料类型
	 * @return int 最大的凭证序号
	 */
	public int getMaxBcsInnerMergeNo(String materielType) {
		List list = this
				.find("select max(a.bcsTenInnerMerge.seqNum) from BcsInnerMerge as a "
						+ "	where a.company.id=? and a.materielType=?",
						new Object[] { CommonUtils.getCompany().getId(),
								materielType });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得物料与报关对应表里的个数
	 * 
	 * @param materielType
	 *            物料类型
	 * @return int 个数
	 */
	public int getBcsInnerMergeCount(String materielType) {
		List list = this.find("select count(a.id) from BcsInnerMerge as a "
				+ "	where a.company.id=? and a.materielType=?", new Object[] {
				CommonUtils.getCompany().getId(), materielType });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param b
	// * 报关商品资料
	// * @return List 是ContractImg型，合同备案料件
	// */
	// public List findBcsInnerMergeInContract(BcsTenInnerMerge b) {
	//
	// List<Object> paramters = new ArrayList<Object>();
	// String hsql = "";
	// String materielType = b.getScmCoi();
	// if (MaterielType.MATERIEL.equals(materielType)) {
	// hsql +=
	// "select a from ContractImg a where a.company= ? and a.contract.isCancel=?  and a.credenceNo = ? ";
	// } else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
	// hsql +=
	// "select a from ContractExg a where a.company= ? and a.contract.isCancel=? and a.credenceNo = ? ";
	// }
	// paramters.add(CommonUtils.getCompany());
	// paramters.add(false);
	// paramters.add(b.getSeqNum());
	// return this.find(hsql, paramters.toArray());
	// }

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param ls
	// * 是BcsInnerMerge型，物料与报关对应表
	// * @return List 是ContractImg型，合同备案料件
	// */
	// public List findBcsInnerMergeInContract(List<BcsInnerMerge> ls) {
	// if (ls == null || ls.size() <= 0) {
	// return new ArrayList();
	// }
	// List<Object> paramters = new ArrayList<Object>();
	// String hsql = "";
	// BcsInnerMerge b = ls.get(0);
	// String materielType = b.getMaterielType();
	// if (MaterielType.MATERIEL.equals(materielType)) {
	// hsql +=
	// "select a from ContractImg a where a.company= ? and a.contract.isCancel=? ";
	// } else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
	// hsql +=
	// "select a from ContractExg a where a.company= ? and a.contract.isCancel=? ";
	// }
	// paramters.add(CommonUtils.getCompany());
	// paramters.add(false);
	// Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	// boolean isFirst = true;
	// String condition = "";
	// for (int i = 0; i < ls.size(); i++) {
	// BcsInnerMerge tempB = (BcsInnerMerge) ls.get(i);
	// if (tempB.getBcsTenInnerMerge() == null) {
	// continue;
	// }
	// if (isFirst) {
	// condition += " and (a.seqNum = ? ";
	// paramters.add(tempB.getBcsTenInnerMerge().getSeqNum());
	// map.put(tempB.getBcsTenInnerMerge().getSeqNum(), tempB
	// .getBcsTenInnerMerge().getSeqNum());
	// isFirst = false;
	// } else {
	// if (map.get(tempB.getBcsTenInnerMerge().getSeqNum()) == null) {
	// condition += " or a.seqNum = ? ";
	// paramters.add(tempB.getBcsTenInnerMerge().getSeqNum());
	// map.put(tempB.getBcsTenInnerMerge().getSeqNum(), tempB
	// .getBcsTenInnerMerge().getSeqNum());
	// }
	// }
	// if (i == ls.size() - 1) {
	// condition += " ) ";
	// }
	// }
	// if ("".equals(condition)) {
	// return new ArrayList();
	// }
	// hsql += condition;
	//
	// return this.find(hsql, paramters.toArray());
	// }

	/**
	 * 根据料件类型查找不存在物料与报关对应表的物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterielForBcsInnerMerge(String materielType, int index,
			int length) {
		return this.findMaterielForBcsInnerMerge(materielType, index, length,
				null, null, null, Boolean.FALSE);
	}

	/**
	 * 查找内部归并中所有工厂物料
	 * 
	 * 
	 * @return List 是Object[]型
	 */
	public List finbcsInnerMerge() {
		return this.find("select  a   from BcsInnerMerge a");
	}

	/**
	 * 查找内部归并中所有工厂物料
	 * 
	 * 
	 * @return List 是Object[]型
	 */
	public List finbcsInnerMergeByType(String type) {
		List paraList = new ArrayList();
		paraList.add(type);
		paraList.add(CommonUtils.getCompany().getId());
		return this
				.find("select  a   from BcsInnerMerge a  where a.materielType=?  and a.company.id =? ",
						paraList.toArray());

	}

	/**
	 * 查找不存在物料与报关对应表的物料来自料件类型
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @param includeIsUsed
	 *            包含已使用的物料
	 * @return 是Materiel型，报关常用物料
	 */
	public List findMaterielForBcsInnerMerge(String materielType, int index,
			int length, String property, Object value, Boolean isLike,
			Boolean includeIsUsed) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from Materiel as a"
				+ " left join fetch a.complex " + " left join fetch a.scmCoc"
				+ " left join fetch a.calUnit"
				+ " where a.scmCoi.coiProperty=? and a.company.id=? ";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		if (!includeIsUsed) {
			hsql += " and (a.isUseInBcsInnerMerge is null or a.isUseInBcsInnerMerge = ? )";
			paramters.add(Boolean.FALSE);
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
		hsql += " order by a.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找报关商品资料来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();

		String hsql = "select a from BcsTenInnerMerge a "
				+ " left join fetch a.complex " + "  where a.company.id= ? "
				+ "	and a.scmCoi=?  ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(materielType);
		System.out.println("--:" + materielType);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
			System.out.println("--:" + value);
		}
		hsql += " order by a.seqNum ";
		System.out.println("--:" + hsql);
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找报关商品资料来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(String materielType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from BcsTenInnerMerge a "
				+ " left join fetch a.complex " + "  where a.company.id= ? "
				+ "	and a.scmCoi=?  ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(materielType);
		System.out.println("--:" + materielType);
		hsql += " order by a.seqNum ";
		System.out.println("--:" + hsql);
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找报关商品资料来自不同物料类型并且在物料与报关对应表中存在的
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return List 是bcsTenInnerMerge型，报关商品资料
	 */
	public List findBcsTenInnerMergeInMerge(String materielType, int index,
			int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct b from BcsInnerMerge a "
				+ "left join a.bcsTenInnerMerge b " + "where a.company.id = ? "
				+ "and a.materielType = ?  and b is not null ";

		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(materielType);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and b." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and b." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by b.seqNum";
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找报关商品资料来自不同物料类型并且在物料与报关对应表中存在的
	 * 
	 * @param materielId
	 *            报关常用物料Id
	 * @return List 是bcsTenInnerMerge型，报关商品资料
	 */
	public BcsTenInnerMerge findBcsTenInnerMergeInMerge(String materielId) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a.bcsTenInnerMerge from BcsInnerMerge a "
				+ "  where a.company.id= ? and a.isUsing = ? "
				+ "	and a.materiel.id=? and a.bcsTenInnerMerge is not null";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(Boolean.TRUE);
		paramters.add(materielId);
		List list = this.find(hsql, paramters.toArray());
		if (list.size() > 0) {
			return (BcsTenInnerMerge) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找报关商品资料来自不同物料类型并且在物料与报关对应表中存在的
	 * 
	 * @param materielId
	 *            报关常用物料Id
	 * @return List 是bcsTenInnerMerge型，报关商品资料
	 */
	public Map findBcsInnerMergeMU() {
		List<Object> paramters = new ArrayList<Object>();
		Map map = new HashMap();
		String hsql = "select a.materiel.ptNo,a.unitConvert from BcsInnerMerge a "
				+ "  where a.company.id= ? "
				+ " and a.bcsTenInnerMerge is not null";
		paramters.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, paramters.toArray());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] == null || objs[1] == null) {
				continue;
			}
			map.put(objs[0], objs[1]);
		}
		return map;
	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	public void saveBcsTenInnerMerge(BcsTenInnerMerge bcsTenInnerMerge) {
		this.getHibernateTemplate().save(bcsTenInnerMerge);

	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	public void saveBcsTenInnerMerge2(BcsTenInnerMerge bcsTenInnerMerge) {
		this.saveOrUpdate(bcsTenInnerMerge);
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	public void deleteBcsTenInnerMerge(BcsTenInnerMerge bcsTenInnerMerge) {
		this.delete(bcsTenInnerMerge);
	}

	/**
	 * 获得最大的报关商品资料序号
	 * 
	 * @param materielType
	 *            物料类型
	 * @return int 最大序号
	 */
	@SuppressWarnings("unchecked")
	public int getMaxTenBcsInnerMergeNo(String materielType) {
		List list = this
				.find("select max(a.seqNum) from BcsTenInnerMerge as a "
						+ "	where a.company.id=? and a.scmCoi=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								materielType });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * @return List 是credenceNo（Integer型），凭证序号
	 */
	public List getContractExgCredenceNo() {
		String hsql = "select a.credenceNo from ContractExg a"
				+ " where a.company.id=? and a.contract.isContractEms=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				false });

	}

	/**
	 * @return List 是credenceNo（Integer型），凭证序号
	 */
	public List getContractImgCredenceNo() {
		String hsql = "select a.credenceNo from ContractImg a "
				+ " where a.company.id=? and a.contract.isContractEms=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				false });

	}

	/**
	 * @return List 是credenceNo（Integer型），凭证序号
	 */
	public List getBcsDictPorExgInnerMergeSeqNum() {
		String hsql = "select a.innerMergeSeqNum from BcsDictPorExg a "
				+ " where a.dictPorHead.company.id=?";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });

	}

	/**
	 * @return List 是credenceNo（Integer型），凭证序号
	 */
	public List getBcsDictPorImgInnerMergeSeqNum() {
		String hsql = "select a.innerMergeSeqNum from BcsDictPorImg a "
				+ " where a.dictPorHead.company.id=? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });

	}

	/**
	 * @return List 是bcsTenInnerMerge.id（String型），报关商品资料Id
	 */
	public List getBcsInnerMergeForId() {
		String hsql = "select  a.bcsTenInnerMerge.id   from BcsInnerMerge  a  "
				+ " where a.company.id=?";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	// /**
	// * 根据物料号码,抓取未变更的归并数据
	// *
	// * @param materialCode
	// * 料号
	// * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	// */
	// public List findInnerMergeDataPtNo() {
	// return this.find(
	// "select distinct a.materiel.ptNo from BcsInnerMerge as a "
	// + " where a.company.id=? ", new Object[] { CommonUtils
	// .getCompany().getId() });
	// }

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List checkExistedMaterialPtNoInInnerMerge(String materielType,
			String ptNo) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel.ptNo from BcsInnerMerge a "
				+ " where a.materielType = ? and a.materiel.ptNo=?  and a.company.id=?";
		paramters.add(materielType);
		paramters.add(ptNo);
		paramters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List checkExistedMaterialPtNoInInnerMerge(String materielType,
			String ptNo, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel.ptNo from BcsInnerMerge a "
				+ " where a.materielType = ? and a.materiel.ptNo=?  and a.company.id=? and a.bcsTenInnerMerge.seqNum = ? ";
		paramters.add(materielType);
		paramters.add(ptNo);
		paramters.add(seqNum);
		paramters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, paramters.toArray());
	}

	// ×××××××××××××××××××××以下为海关帐所用到的方法
	/**
	 * 根据料件类型备案资料库料件表
	 * 
	 * @param materielType
	 *            是成品,还是料件
	 */
	public List findExeStateBcsDictPor(String materielType) {
		String hsql = "";
		if (MaterielType.MATERIEL.equals(materielType)
				|| MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			hsql += "select a from  BcsDictPorImg a  left join  a.dictPorHead b"
					+ "  where a.company= ? " + "	and b.declareState=?  ";
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)
				|| MaterielType.BAD_PRODUCT.equals(materielType)) {
			hsql += "select a from  BcsDictPorExg a  left join  a.dictPorHead b"
					+ "  where a.company= ? " + "	and b.declareState=?  ";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				DeclareState.PROCESS_EXE });

	}

	/**
	 * 根据类型查询合同备案料件信息
	 * 
	 * @param materielType
	 *            是边角料,还是殘次品
	 * @return
	 */
	public List findExeStateBcsContract(String materielType) {
		String hsql = "";
		if (MaterielType.MATERIEL.equals(materielType)
				|| MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			hsql += "select a from  ContractImg a  left join  a.contract b"
					+ "  where a.company= ? "
					+ "	and b.declareState in ( ?,? )";
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)
				|| MaterielType.BAD_PRODUCT.equals(materielType)) {
			hsql += "select a from  ContractExg a  left join  a.contract b"
					+ "  where a.company= ? "
					+ "	and b.declareState in ( ?,? ) ";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				DeclareState.CHANGING_CANCEL, DeclareState.PROCESS_EXE });
	}

	/**
	 * 返回参数设定
	 * 
	 * @param type
	 * @return
	 */
	public String getBpara(int type) {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	/**
	 * 成品类型电子帐册料件
	 * 
	 * @param materielType是边角料
	 *            ,还是殘次品
	 * @return
	 */
	public List findExeStateBcusEms2K(String materielType) {
		String hsql = "";
		if (MaterielType.MATERIEL.equals(materielType)
				|| MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			hsql += "select a from  EmsHeadH2kImg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? "
					+ "	and b.declareState in (?,?)  ";
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)
				|| MaterielType.BAD_PRODUCT.equals(materielType)) {
			hsql += "select a from  EmsHeadH2kExg a  left join  a.emsHeadH2k b"
					+ "  where a.company= ? " + "	and b.declareState in (?,?) ";
		}
		if ((getBpara(BcusParameter.EmsSEND).equals("1"))) {// 分批申报
			return this.find(hsql, new Object[] { CommonUtils.getCompany(),
					DeclareState.CHANGING_CANCEL, DeclareState.APPLY_POR });
		} else {// 不分批申报
			return this.find(hsql, new Object[] { CommonUtils.getCompany(),
					DeclareState.CHANGING_CANCEL, DeclareState.PROCESS_EXE });
		}
	}

	/**
	 * 根据类型电子手册通关备案里的料件资料
	 * 
	 * @param materielType是边角料
	 *            ,还是殘次品
	 * @return
	 */
	public List findExeStateDzscEmsPorHead(String materielType) {
		String hsql = "";
		if (MaterielType.MATERIEL.equals(materielType)
				|| MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			hsql += "select a from  DzscEmsImgBill a  left join  a.dzscEmsPorHead b"
					+ "  where a.company= ? "
					+ "	and b.declareState in ( ?,? )  ";
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)
				|| MaterielType.BAD_PRODUCT.equals(materielType)) {
			hsql += "select a from  DzscEmsExgBill a  left join  a.dzscEmsPorHead b"
					+ "  where a.company= ? "
					+ "	and b.declareState in ( ?,? ) ";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				DzscState.CHECK_CANCEL, DzscState.EXECUTE });
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet() {
		List list = this.find(
				"select a from BcsParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BcsParameterSet) list.get(0);
		} else {
			BcsParameterSet parameterSet = new BcsParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	// *********************************************以上为海关帐所用方法

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	public BcsInnerMergeDataOrder findBcsInnerMergeDataOrder() {
		List list = this.find(" select a from BcsInnerMergeDataOrder as a "
				+ "  where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BcsInnerMergeDataOrder) list.get(0);
		} else {
			BcsInnerMergeDataOrder order = new BcsInnerMergeDataOrder();
			this.saveOrUpdate(order);
			return order;
		}
	}

	/**
	 * 保存从文本导入的内部归并资料
	 * 
	 * @param bcsInnerMergeDataOrder
	 */
	public void saveBcsInnerMergeDataOrder(
			BcsInnerMergeDataOrder bcsInnerMergeDataOrder) {
		this.saveOrUpdate(bcsInnerMergeDataOrder);
	}

	/**
	 * 根据成品查找报关单耗资料
	 */
	public List findCustomsBomDetailByExg(ContractExg exg) {
		List<Object> paramters = new ArrayList<Object>();
		String exgName = exg.getName();
		String exgSpce = exg.getSpec();
		Unit exgUnit = exg.getUnit();
		String hsql = "select a from BcsCustomsBomDetail a where a.bcsCustomsBomExg.company.id= ?";
		paramters.add(CommonUtils.getCompany().getId());
		if (exgName != null) {
			hsql += " and a.bcsCustomsBomExg.name=? ";
			paramters.add(exgName);
		}
		if (exgSpce != null) {
			hsql += " and a.bcsCustomsBomExg.spec=? ";
			paramters.add(exgSpce);
		}
		if (exgUnit != null) {
			hsql += " and a.bcsCustomsBomExg.unit=? ";
			paramters.add(exgUnit);
		}
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findBcsCustomsBomExgTenSeqNum() {
		return this.find(" select a.tenSeqNum from BcsCustomsBomExg a "
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 抓取内部归并表
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findDzscTenInnerMerge(String imrType) {
		return this.find(" select a from BcsInnerMerge a "
				+ " where a.bcsTenInnerMerge is not null "
				+ " and a.company.id=? and a.materielType=? "
				+ " order by a.bcsTenInnerMerge.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), imrType });
	}

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findBcsCustomsBomDetail(BcsCustomsBomExg bcsCustomsBomExg) {
		// TODO Auto-generated method stub
		return this.find(" select a from BcsCustomsBomDetail a "
				+ " where a.company.id=? and a.bcsCustomsBomExg.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						bcsCustomsBomExg.getId() });
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findBcsCustomsBomExg() {
		return this.find(" select a from BcsCustomsBomExg a "
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 通过归并序号抓取内部归并表
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsInnerMergeDataByTenSeqNum(Integer tenSeqNum,
			String materielType) {
		return this.find(" select a from BcsInnerMerge a "
				+ " where a.company.id=? and a.bcsTenInnerMerge.seqNum=?  "
				+ " and a.materielType=? ", new Object[] {
				CommonUtils.getCompany().getId(), tenSeqNum, materielType });

	}

	/**
	 * 通过归并序号抓取报关常用资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsTenInnerMergeDataBySeqNum(Integer tenSeqNum,
			String materielType) {
		return this.find(" select a from BcsTenInnerMerge a "
				+ " where a.company.id=? and a.seqNum=?  "
				+ " and  a.scmCoi=?  ", new Object[] {
				CommonUtils.getCompany().getId(), tenSeqNum, materielType });

	}

	/**
	 * 通过料号抓取内部归并表
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsInnerMergeDataByTenPtNo(String ptNO, String materielType) {
		return this.find(" select a from BcsInnerMerge a "
				+ " where a.company.id=? and a.materiel.ptNo=?  "
				+ " and a.materielType=? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNO, materielType });
	}

	/**
	 * 通过料号抓取内部归并表
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public BcsInnerMerge findBcsInnerMergeByTenPtNo(String ptNO,
			String materielType) {
		List list = this.find(" select a from BcsInnerMerge a "
				+ " where a.company.id=? and a.materiel.ptNo=?  "
				+ " and a.materielType=? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNO, materielType });
		if (list.size() > 0) {
			return (BcsInnerMerge) list.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 通过工厂成品料号获取工厂bom
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findMaterialBomDetail(String ptNo, Integer version, Date endDate) {
		String hql = "select a from MaterialBomDetail a"
				+ " where a.company.id=? and a.version.master.materiel.ptNo=?";
		List paras = new ArrayList();
		paras.add(CommonUtils.getCompany().getId());
		paras.add(ptNo);
		if (version != null) {
			hql += " and a.version.version = ?";
			paras.add(version);
		}

		if (endDate != null) {
			hql += " and a.version.endDate = ?";
			paras.add(endDate);
		}

		List<MaterialBomDetail> list = this.find(hql, paras.toArray());

		if (endDate != null) {
			List<MaterialBomDetail> result = new ArrayList();
			version = ((MaterialBomDetail) list.get(0)).getVersion()
					.getVersion();
			for (MaterialBomDetail materialBomDetail : list) {
				if (version.equals(materialBomDetail.getVersion().getVersion())) {
					result.add(materialBomDetail);
				}
			}
			return result;
		}

		return list;
	}

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findBcsCustomsBomDetailTenSeqNum(
			BcsCustomsBomExg bcsCustomsBomExg) {
		return this.find(" select a.tenSeqNum from BcsCustomsBomDetail a "
				+ " where a.company.id=? and a.bcsCustomsBomExg.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						bcsCustomsBomExg.getId() });
	}

	/**
	 * 按报关单耗查找合同中料件的序号
	 * 
	 * @param bsb
	 * @param exg
	 * @return 2010-06月-08hcl
	 */
	public List getImgSeqNum(BcsCustomsBomDetail bsb, ContractExg exg) {
		String hsql = "select a.seqNum from ContractImg a where a.company.id= ? and a.contract.id=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(exg.getContract().getId());
		if (bsb.getComplex() != null) {
			hsql += " and a.complex=? ";
			paramters.add(bsb.getComplex());
		}
		if (bsb.getName() != null) {
			hsql += " and a.name=? ";
			paramters.add(bsb.getName());
		}
		if (bsb.getSpec() != null) {
			hsql += " and a.spec=? ";
			paramters.add(bsb.getSpec());
		}
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查询正在执行的通过手册料件或成品
	 * 
	 * @param materielType
	 */
	public List<?> findContractMateriel(String materielType,BcsDictPorHead bcsDictPorHead) {
		List<Object> paramters = new ArrayList<Object>();
		if (MaterielType.MATERIEL.equals(materielType)) {
			String hsql = "select a from BcsDictPorImg a join fetch a.dictPorHead b where b.company=? and b.declareState = ?  ";
			paramters.add(CommonUtils.getCompany());
			paramters.add(DeclareState.PROCESS_EXE);
			if(bcsDictPorHead!=null){
				hsql += "and b.id= ?";
				paramters.add(bcsDictPorHead.getId());
			}
			return find(hsql,paramters.toArray());
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			String hsql = "select a from BcsDictPorExg a join fetch a.dictPorHead b where b.company=? and b.declareState = ?  ";
			paramters.add(CommonUtils.getCompany());
			paramters.add(DeclareState.PROCESS_EXE);
			if(bcsDictPorHead!=null){
				hsql += "and b.id= ?";
				paramters.add(bcsDictPorHead.getId());
			}
			
			return find(hsql,paramters.toArray());
		}
		return null;
	}

	/**
	 * 查询新增的物料是否在物料与报关对应表中存在
	 * 
	 * @param materielType
	 * @param materiel
	 * @return
	 */

	public boolean findisGeting(String materielType, String ptNo) {
		List<Object> param = new ArrayList<Object>();
		String hsql = "select  a   from BcsInnerMerge a  where a.materielType=?  and a.company.id =?  and a.materiel.ptNo =? ";
		param.add(materielType);
		param.add(CommonUtils.getCompany().getId());
		param.add(ptNo);
		List list = this.find(hsql, param.toArray());
		System.out.println("hhsql" + hsql);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查找BCS内部归并对应表对象
	 * @param materialType
	 * @param strBcsInnerMergeKeys
	 * @return
	 */
	public List<Materiel> findBcsInnerMergeObject(String materialType,String strBcsInnerMergeKeys) {
		return this
				.find("select distinct a.materiel from BcsInnerMerge a left join a.bcsTenInnerMerge b"
						 +" where a.company= ? and a.materielType = ?" 
                         +" and (case when b = null then CONCAT(a.materiel.ptNo,'/') else CONCAT(a.materiel.ptNo, CONCAT('/',cast(b.seqNum as string))) end)"
						 +" in ("+strBcsInnerMergeKeys+") ",
						new Object[] { CommonUtils.getCompany(), materialType });
	}

	/**
	 * 保存根据料件新增或替换的数据
	 * 
	 * @param list
	 *          
	 */
	public void saveBcsInnerMergeAddOrUpdate(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.saveOrUpdate(list.get(i));
		}
	}
	
	/**
	 * 查找工厂物料主档对象
	 * @param materialType
	 * @param strBcsInnerMergeKeys
	 * @return
	 */
	public List<String> findBcsInnerMergeEnterpriseObject(String strBcsInnerMergeEnterpriseKeys) {
		return this
				.find("select a from EnterpriseMaterial a"
                         +" where a.company= ? and CONCAT(a.ptNo,'/') IN ( " + strBcsInnerMergeEnterpriseKeys +" )",
						new Object[] { CommonUtils.getCompany()});
	}
	
	/**
	 * 查询备案资料库数量，用于“报关商品资料中的导入”
	 * @param request
	 * @return
	 */
	public List<BcsDictPorHead> findCountBcsDictPorHead(){
		return this.find("select a from BcsDictPorHead a where a.company= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(),DeclareState.PROCESS_EXE});
	}
}
