/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsInnerMergeDataOrder;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;

/**
 * com.bestway.bls.blsinnermerge.dao.BlsInnerMergeDao
 * 
 * @author ls 内部归并DAO接口
 */
public class BlsInnerMergeDao extends BaseDao {
	/**
	 * 查找物料与报关对应表
	 */
	public List findBlsInnerMerge() {
		return this.find("select a from BlsInnerMerge a "
				+ "  left join fetch a.materiel "
				+ "  left join fetch a.blsTenInnerMerge "
				+ "  where a.company= ? order by a.blsTenInnerMerge.seqNum",
				new Object[] { CommonUtils.getCompany() });
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
	public List findComplexOutOfBlsTenInnerMerge(int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Complex a "
				+ " left join fetch a.firstUnit "
				+ " left join fetch a.secondUnit "
				+ " where (a.isOut <> '1' or a.isOut is null) ";
		// + "and a.code not in (select b.complex.code from BlsTenInnerMerge
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

	// /**
	// * 查找物料与报关对应表来自不同物料类型
	// *
	// * @param materielType
	// * 物料类型
	// * @return List 是BlsInnerMerge型，物料与报关对应表
	// */
	// public List findBlsInnerMerge() {//String materielType
	// String hsql = "select a from BlsInnerMerge a "
	// + " left join fetch a.materiel "
	// + " left join fetch a.blsTenInnerMerge "
	// + "  where a.company= ? " //+ "	and a.materielType=?  "
	// + "   order by a.blsTenInnerMerge.seqNum asc ";
	// return this.find(hsql, new Object[] { CommonUtils.getCompany()});
	// }

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public BlsInnerMerge findBlsInnerMergeByPtNo(String ptNo) {
		String hsql = "select a from BlsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.blsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.materiel.ptNo=?  ";
		List list = this.find(hsql, new Object[] { CommonUtils.getCompany(),
				ptNo });
		if (list.size() > 0) {
			return (BlsInnerMerge) list.get(0);
		}
		return null;
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsInnerMerge(String tenCode, Integer seqNum) {// String
		// type,
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from BlsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.blsTenInnerMerge "
				+ "  where a.company= ? ";// + "	and a.materielType=?  "
		paramters.add(CommonUtils.getCompany());
		// paramters.add(type);
		if (tenCode != null) {
			hsql += " and a.blsTenInnerMerge.complex.code=? ";
			paramters.add(tenCode);
		}
		if (seqNum != null) {
			hsql += " and a.blsTenInnerMerge.seqNum=? ";
			paramters.add(seqNum);
		}
		hsql += "   order by a.blsTenInnerMerge.complex.code asc ";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsTenInnerMergeByInnerMerge(String tenCode,// String type,
			Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.blsTenInnerMerge from BlsInnerMerge a "
				+ "  where a.company= ? ";// + "	and a.materielType=?  "
		paramters.add(CommonUtils.getCompany());
		// paramters.add(type);
		if (tenCode != null) {
			hsql += " and a.blsTenInnerMerge.complex.code=? ";
			paramters.add(tenCode);
		}
		if (seqNum != null) {
			hsql += " and a.blsTenInnerMerge.seqNum=? ";
			paramters.add(seqNum);
		}
		hsql += "   order by a.blsTenInnerMerge.seqNum asc ";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsTenInnerMergeByInnerMerge() {
		String hsql = "select distinct a.blsTenInnerMerge from BlsInnerMerge a "
				+ "  where a.company= ? "
				// + "	and a.materielType=?  "
				+ "	  and a.blsTenInnerMerge.seqNum is not null "
				+ "   order by a.blsTenInnerMerge.seqNum asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsInnerMergeByTenInnerMerge(
			BlsTenInnerMerge blsTenInnerMerge) {
		String hsql = "select a from BlsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.blsTenInnerMerge "
				+ "  where a.company= ? " + "	  and a.blsTenInnerMerge.id= ? "
				+ "   order by a.materiel.ptNo asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				blsTenInnerMerge.getId() });
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsInnerMergeByMerge(String materielType) {
		String hsql = "select a from BlsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.blsTenInnerMerge "
				+ "  where a.company= ? " + "	and a.materielType=?  "
				+ "	  and a.blsTenInnerMerge.seqNum is not null "
				+ "   order by a.blsTenInnerMerge.seqNum asc ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				materielType });
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
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsInnerMerge(int index, int length,// String materielType,
			String property, Object value, boolean isLike) {
		List returnList = new ArrayList();
		List listTenCode = new ArrayList();
		List listMaterielCode = new ArrayList();
		int hasTenCodeCount = 0;
		/**
		 * 找十码不为空的数据
		 */
		List<Object> paramters = new ArrayList<Object>();

		String hsql = "select a from BlsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.blsTenInnerMerge "
				+ "  where a.company.id= ? " // + "	and a.materielType=?  "
				+ "	  and   a.blsTenInnerMerge.seqNum is not null ";
		// and a.blsTenInnerMerge.seqNum != ?
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add(materielType);
		// paramters.add("");
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
		hasTenCodeCount = this.findTenCodeCount(property, value, isLike);// materielType,

		if (listTenCode.size() == 0) {
			/**
			 * 找十码为空的数据
			 */
			hsql = "select a from BlsInnerMerge a "
					+ " left join fetch a.materiel "
					+ " left join fetch a.blsTenInnerMerge "
					+ "  where a.company.id= ? " // + "	and a.materielType=?  "
					+ "	  and ( a.blsTenInnerMerge.seqNum is null ) ";
			paramters.clear();
			paramters.add(CommonUtils.getCompany().getId());
			// paramters.add(materielType);
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
			listMaterielCode = super.findPageList(hsql, paramters.toArray(),
					tenCodeIndex, length);

		} else if (listTenCode.size() < length) {
			/**
			 * 找十码为空的数据
			 */
			hsql = "select a from BlsInnerMerge a "
					+ " left join fetch a.materiel "
					+ " left join fetch a.blsTenInnerMerge "
					+ "  where a.company.id= ? " // + "	and a.materielType=?  "
					+ "	  and ( a.blsTenInnerMerge.seqNum is null ) ";
			paramters.clear();
			paramters.add(CommonUtils.getCompany().getId());
			// paramters.add(materielType);
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a." + property + " like ?  ";
					paramters.add(value + "%");
				} else {
					hsql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
			listMaterielCode = super.findPageList(hsql, paramters.toArray(), 0,
					length - (listTenCode.size()));
		}

		returnList.addAll(listTenCode);
		returnList.addAll(listMaterielCode);
		return returnList;
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
	private int findTenCodeCount(String property, Object value, boolean isLike) {
		int hasTenCodeCount = 0;
		/**
		 * 找四码不为空的数据
		 */
		List<Object> paramters = new ArrayList<Object>();

		/**
		 * 找四码为空十码不为空的数据
		 */
		String hsql = "select count(*)from BlsInnerMerge a "
				+ "  where a.company.id= ? "// + "	and a.materielType=?  "
				+ "	  and   a.blsTenInnerMerge.seqNum is not null  ";
		// and a.blsTenInnerMerge.seqNum != ?
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add(type);

		// paramters.add("");
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
	 * @param BlsInnerMerge
	 *            物料与报关对应表
	 */
	public void saveBlsInnerMerge(BlsInnerMerge BlsInnerMerge) {
		this.saveOrUpdate(BlsInnerMerge);
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param BlsInnerMerge
	 *            物料与报关对应表
	 */
	public void deleteBlsInnerMerge(BlsInnerMerge BlsInnerMerge) {
		this.delete(BlsInnerMerge);
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param list
	 *            物料与报关对应表
	 */
	public void saveBlsInnerMerge(List list) {
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
	public void deleteBlsInnerMerge(List list) {
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
	public int getMaxBlsInnerMergeNo() {// String materielType
		List list = this.find(
				"select max(a.blsTenInnerMerge.seqNum) from BlsInnerMerge as a "
						+ "	where a.company.id=?",// and a.materielType=?
				new Object[] { CommonUtils.getCompany().getId(), });
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
	public int getBlsInnerMergeCount(String materielType) {
		List list = this.find("select count(a.id) from BlsInnerMerge as a "
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
	// public List findBlsInnerMergeInContract(BlsTenInnerMerge b) {
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
	// * 是BlsInnerMerge型，物料与报关对应表
	// * @return List 是ContractImg型，合同备案料件
	// */
	// public List findBlsInnerMergeInContract(List<BlsInnerMerge> ls) {
	// if (ls == null || ls.size() <= 0) {
	// return new ArrayList();
	// }
	// List<Object> paramters = new ArrayList<Object>();
	// String hsql = "";
	// BlsInnerMerge b = ls.get(0);
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
	// BlsInnerMerge tempB = (BlsInnerMerge) ls.get(i);
	// if (tempB.getBlsTenInnerMerge() == null) {
	// continue;
	// }
	// if (isFirst) {
	// condition += " and (a.seqNum = ? ";
	// paramters.add(tempB.getBlsTenInnerMerge().getSeqNum());
	// map.put(tempB.getBlsTenInnerMerge().getSeqNum(), tempB
	// .getBlsTenInnerMerge().getSeqNum());
	// isFirst = false;
	// } else {
	// if (map.get(tempB.getBlsTenInnerMerge().getSeqNum()) == null) {
	// condition += " or a.seqNum = ? ";
	// paramters.add(tempB.getBlsTenInnerMerge().getSeqNum());
	// map.put(tempB.getBlsTenInnerMerge().getSeqNum(), tempB
	// .getBlsTenInnerMerge().getSeqNum());
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
	public List findMaterielForBlsInnerMerge(int index,// String materielType,
			int length) {
		return this.findMaterielForBlsInnerMerge(index, length,// materielType,
				null, null, null);
	}

	/**
	 * 查找内部归并中所有工厂物料
	 * 
	 * 
	 * @return List 是Object[]型
	 */
	public List finblsInnerMerge() {
		return this.find("select  a   from BlsInnerMerge a");
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
	 * @return 是Materiel型，报关常用物料
	 */
	public List findMaterielForBlsInnerMerge(int index,// String materielType,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from Materiel as a"
				+ " left join fetch a.complex " + " left join fetch a.scmCoc"
				+ " left join fetch a.calUnit" + " where a.company.id=? "; // a.scmCoi.coiProperty=?
		// and
		// +
		// " and (a.isUseInBlsInnerMerge is null or a.isUseInBlsInnerMerge = ? )";
		// hsql += " and a.id not in" ISUSEINBCSINNERMERGE
		// + "( select b.materiel.id from BlsInnerMerge b where b.materielType =
		// ? and b.company.id=?) ";
		// paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add(false);
		// paramters.add(CommonUtils.getCompany().getId());

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
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	public List findBlsTenInnerMerge(int index,// String materielType,
			int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();

		String hsql = "select a from BlsTenInnerMerge a "
				+ " left join fetch a.complex " + "  where a.company.id= ? ";
		// + "	and a.scmCoi=?  ";
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add(materielType);
		// System.out.println("--:" + materielType);
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
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	public List findBlsTenInnerMerge(String materielType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from BlsTenInnerMerge a "
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
	 * @return List 是blsTenInnerMerge型，报关商品资料
	 */
	public List findBlsTenInnerMergeInMerge(String materielType, int index,
			int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();

		// String hsql = "from BlsTenInnerMerge a "
		// + " left join fetch a.complex " + " where a.company= ? "
		// + " and a.scmCoi=? a.id in select (select b.blsTenInnerMerge.id from
		// BlsInnerMerge b where "
		// + " and b.materielType=? and b.blsTenInnerMerge is not null and
		// b.company = ? )";

		String hsql = "select distinct a.blsTenInnerMerge from BlsInnerMerge a "
				+ "left join fetch a.blsTenInnerMerge b"
				+ "  where a.company= ? "
				+ "	and a.materielType=?  and a.blsTenInnerMerge is not null";

		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(materielType);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a.blsTenInnerMerge" + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a.blsTenInnerMerge" + property + " = ?  ";
				paramters.add(value);
			}
		}
		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找报关商品资料来自不同物料类型并且在物料与报关对应表中存在的
	 * 
	 * @param materielId
	 *            报关常用物料Id
	 * @return List 是blsTenInnerMerge型，报关商品资料
	 */
	public BlsTenInnerMerge findBlsTenInnerMergeInMerge(String materielId) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a.blsTenInnerMerge from BlsInnerMerge a "
				+ "  where a.company.id= ? "
				+ "	and a.materiel.id=? and a.blsTenInnerMerge is not null";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(materielId);
		List list = this.find(hsql, paramters.toArray());
		if (list.size() > 0) {
			return (BlsTenInnerMerge) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查找报关商品资料来自不同物料类型并且在物料与报关对应表中存在的
	 * 
	 * @param materielId
	 *            报关常用物料Id
	 * @return List 是blsTenInnerMerge型，报关商品资料
	 */
	public Map findBlsInnerMergeMU() {
		List<Object> paramters = new ArrayList<Object>();
		Map map = new HashMap();
		String hsql = "select a.materiel.ptNo,a.unitConvert from BlsInnerMerge a "
				+ "  where a.company.id= ? "
				+ " and a.blsTenInnerMerge is not null";
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
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	public void saveBlsTenInnerMerge(BlsTenInnerMerge blsTenInnerMerge) {
		this.getHibernateTemplate().save(blsTenInnerMerge);

	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	public void saveBlsTenInnerMerge2(BlsTenInnerMerge blsTenInnerMerge) {
		this.saveOrUpdate(blsTenInnerMerge);
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	public void deleteBlsTenInnerMerge(BlsTenInnerMerge blsTenInnerMerge) {
		this.delete(blsTenInnerMerge);
	}

	/**
	 * 获得最大的报关商品资料序号
	 * 
	 * @param materielType
	 *            物料类型
	 * @return int 最大序号
	 */
	public int getMaxTenBlsInnerMergeNo() {// String materielType
		List list = this.find(
				"select max(a.seqNum) from BlsTenInnerMerge as a "
						+ "	where a.company.id=?",// and a.scmCoi=?
				new Object[] { CommonUtils.getCompany().getId() });
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
	public List getBlsDictPorExgInnerMergeSeqNum() {
		String hsql = "select a.innerMergeSeqNum from BlsDictPorExg a "
				+ " where a.dictPorHead.company.id=?";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });

	}

	/**
	 * @return List 是credenceNo（Integer型），凭证序号
	 */
	public List getBlsDictPorImgInnerMergeSeqNum() {
		String hsql = "select a.innerMergeSeqNum from BlsDictPorImg a "
				+ " where a.dictPorHead.company.id=? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });

	}

	/**
	 * @return List 是blsTenInnerMerge.id（String型），报关商品资料Id
	 */
	public List getBlsInnerMergeForId() {
		String hsql = "select  a.blsTenInnerMerge.id   from BlsInnerMerge  a  "
				+ " where a.company.id=?";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据物料号码,抓取未变更的归并数据
	 * 
	 * @param materialCode
	 *            料号
	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
	 */
	public List findInnerMergeDataPtNo() {
		return this
				.find(
						"select distinct (trim(a.materiel.ptNo) "
								+ "||trim(case when a.blsTenInnerMerge.seqNum is null then '' else str(a.blsTenInnerMerge.seqNum) end)"
								+ "||trim(case when a.blsTenInnerMerge.complex is null then '' else a.blsTenInnerMerge.complex.code end) "
								+ "||trim(case when a.blsTenInnerMerge.name is null then '' else a.blsTenInnerMerge.name end)"
								+ "||trim(case when a.blsTenInnerMerge.spec is null then '' else a.blsTenInnerMerge.spec end)"
								+ "||trim(case when a.blsTenInnerMerge.comUnit is null then '' else a.blsTenInnerMerge.comUnit.name end))"
								+ " from BlsInnerMerge as a "
								+ " left join a.blsTenInnerMerge"
								+ " left join a.blsTenInnerMerge.complex"
								+ " left join a.blsTenInnerMerge.comUnit"
								+ " where a.company.id=? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List findExistedMaterialPtNoInInnerMerge() {// String materielType
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel.ptNo from BlsInnerMerge a "
				+ " where a.company.id=?";// a.materielType = ? and
		// paramters.add(materielType);
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
	public List findExeStateBlsDictPor(String materielType) {
		String hsql = "";
		if (MaterielType.MATERIEL.equals(materielType)
				|| MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			hsql += "select a from  BlsDictPorImg a  left join  a.dictPorHead b"
					+ "  where a.company= ? " + "	and b.declareState=?  ";
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)
				|| MaterielType.BAD_PRODUCT.equals(materielType)) {
			hsql += "select a from  BlsDictPorExg a  left join  a.dictPorHead b"
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
	public List findExeStateBlsContract(String materielType) {
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
				.find(
						"select a from BcusParameter a where a.type = ? and a.company.id = ?",
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
	public BlsParameterSet findBlsParameterSet() {
		List list = this.find(
				"select a from BlsParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BlsParameterSet) list.get(0);
		} else {
			BlsParameterSet parameterSet = new BlsParameterSet();
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
	public BlsInnerMergeDataOrder findBlsInnerMergeDataOrder() {
		List list = this.find(" select a from BlsInnerMergeDataOrder as a "
				+ "  where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BlsInnerMergeDataOrder) list.get(0);
		} else {
			BlsInnerMergeDataOrder order = new BlsInnerMergeDataOrder();
			this.saveOrUpdate(order);
			return order;
		}
	}

	/**
	 * 保存从文本导入的内部归并资料
	 * 
	 * @param blsInnerMergeDataOrder
	 */
	public void saveBlsInnerMergeDataOrder(
			BlsInnerMergeDataOrder blsInnerMergeDataOrder) {
		this.saveOrUpdate(blsInnerMergeDataOrder);
	}
}
