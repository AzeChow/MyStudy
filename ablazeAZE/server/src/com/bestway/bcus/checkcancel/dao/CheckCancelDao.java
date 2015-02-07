package com.bestway.bcus.checkcancel.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.checkcancel.entity.CancelCusExgResult;
import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.checkcancel.entity.CancelCusImgResult;
import com.bestway.bcus.checkcancel.entity.CancelCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelExgBefore;
import com.bestway.bcus.checkcancel.entity.CancelExgResult;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CancelImgAvgPrice;
import com.bestway.bcus.checkcancel.entity.CancelImgBefore;
import com.bestway.bcus.checkcancel.entity.CancelImgResult;
import com.bestway.bcus.checkcancel.entity.CancelOwnerExgResult;
import com.bestway.bcus.checkcancel.entity.CancelOwnerImgResult;
import com.bestway.bcus.checkcancel.entity.CancelTemp;
import com.bestway.bcus.checkcancel.entity.CancelUnitWear;
import com.bestway.bcus.checkcancel.entity.CheckBgCy;
import com.bestway.bcus.checkcancel.entity.CheckExg;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccount;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccountComport;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.ColorSet;
import com.bestway.bcus.checkcancel.entity.FactoryCheckExg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckExgConverImg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImgForBg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImgResult;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SendState;

/**
 * 电子帐册中期核销与数据报核Dao check by lxr Date :2008-10-30
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class CheckCancelDao extends BaseDao {
	/**
	 * 
	 * 显示中期核查表头数据
	 * 
	 * @param head
	 *            中期核查表头
	 * @return 所有数据
	 */
	public List findCheckHead(CheckParameter head) {
		return this
				.find("select a from CheckHead a where a.company.id= ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 
	 * 显示自用中期核查表头数据
	 * 
	 * @param head
	 *            自用中期核查表头
	 * @return 所有数据
	 */
	public List findCheckOwnerHead() {
		return this.find(
				"select a from CheckOwnerHead a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 中期核查－－工厂盘点－－成品折为料件
	 * 
	 * @param head
	 * @return 返回版本号
	 */
	public List findFactoryExgVersion(CheckParameter head) {
		return this
				.find("select distinct a.versionNo from FactoryCheckExgConverImg a"
						+ " where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 
	 * 保存中期核查表头
	 * 
	 * @param checkHead
	 *            中期核查表头
	 * @throws DataAccessException
	 */
	public void saveCheckHead(CheckHead checkHead) throws DataAccessException {
		this.saveOrUpdate(checkHead);
	}

	/**
	 * 
	 * 删除中期核查表头
	 * 
	 * @param checkHead
	 */
	public void deleteCheckHead(CheckHead checkHead) {
		this.delete(checkHead);
	}

	/**
	 * 
	 * 显示中期核查料件
	 * 
	 * @param checkHead
	 *            中期核查表头
	 * @return 所有料件数据
	 */
	public List findCheckImg(CheckHead checkHead) {

		return this.find("select a from CheckImg a where a.company.id= ? "
				+ " and a.checkHead.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), checkHead.getId() });
	}

	/**
	 * 显示中期核查料件
	 * 
	 * @param head
	 *            参数设置
	 * @return 所有料件数据
	 */
	public List findCheckImgByHead(CheckParameter head) {
		return this.find("select a from CheckImg a where a.company.id= ? "
				+ "and a.checkHead.head.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 
	 * 保存中期核查料件
	 * 
	 * @param checkImg
	 * @throws DataAccessException
	 */
	public void saveCheckImg(CheckImg checkImg) throws DataAccessException {
		this.saveOrUpdate(checkImg);

	}

	/**
	 * 保存中期核查－－工厂盘点－－料件
	 * 
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveCheckImgForBg(FactoryCheckImgForBg obj)
			throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	/**
	 * 
	 * 删除中期核查料件
	 * 
	 * @param checkImg
	 */
	public void deleteCheckImg(CheckImg checkImg) {
		this.delete(checkImg);
	}

	/**
	 * 
	 * 显示中期核查成品
	 * 
	 * @param checkHead
	 *            中期核查成品
	 * @return 所有数据
	 */
	public List findCheckExg(CheckHead checkHead) {

		return this.find("select a from CheckExg a where a.company.id= ? "
				+ "and a.checkHead.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), checkHead.getId() });
	}

	/**
	 * 
	 * 保存中期核查成品
	 * 
	 * @param checkExg
	 * @throws DataAccessException
	 */
	public void saveCheckExg(CheckExg checkExg) throws DataAccessException {
		this.saveOrUpdate(checkExg);

	}

	/**
	 * 删除中期核查成品
	 * 
	 * @param checkExg
	 */
	public void deleteCheckExg(CheckExg checkExg) {
		this.delete(checkExg);
	}

	/**
	 * 删除中期核查表体(包含料件与成品)
	 * 
	 * @param checkHead
	 */
	public void deleteAllCheckImgExg(CheckHead checkHead) {
		this.deleteAll(findCheckImg(checkHead));
		this.deleteAll(findCheckExg(checkHead));
	}

	/**
	 * 获取电子帐册参数设置 （电子帐册是否分批发送）
	 * 
	 * @return
	 */
	private boolean getIsEmsH2kSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 中期核查--添加料件--来自内部归并关系
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param checkHead
	 *            中期核销表头
	 * @return 所有内部归并的数据
	 */
	public List findImgFromInnerMerge(EmsHeadH2k emsHeadH2k, CheckHead checkHead) {
		/*
		 * if (getIsEmsH2kSend()){ return this .find( "select a from
		 * InnerMergeData a where a.company= ? and a.imrType=?" + " and
		 * a.hsFourNo in (select b.seqNum from EmsHeadH2kImg b where
		 * b.emsHeadH2k=? and b.sendState = ?)" + " and a.hsFourNo not in
		 * (select c.seqNum from CheckImg c where c.checkHead=?)", new Object[]
		 * { CommonUtils.getCompany(), MaterielType.MATERIEL,
		 * emsHeadH2k,Integer.valueOf(SendState.SEND), checkHead }); } else {
		 */
		return this
				.find("select a from InnerMergeData a where a.company= ? and a.imrType=?"
						+ " and a.hsFourNo in (select b.seqNum from EmsHeadH2kImg b where b.emsHeadH2k=?)"
						+ " and a.hsFourNo not in (select c.seqNum from CheckImg c where c.checkHead=?)",
						new Object[] { CommonUtils.getCompany(),
								MaterielType.MATERIEL, emsHeadH2k, checkHead });
		// }
	}

	/**
	 * 
	 * 中期核查--添加成品--来自内部归并关系
	 * 
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @param checkHead
	 *            中期核销表头
	 * @return 所有内部归并的数据
	 */
	public List findExgFromInnerMerge(EmsHeadH2k emsHeadH2k, CheckHead checkHead) {
		/*
		 * if (getIsEmsH2kSend()){ return this .find( "select a from
		 * InnerMergeData a where a.company= ? and a.imrType=?" + " and
		 * a.hsFourNo in (select b.seqNum from EmsHeadH2kExg b where
		 * b.emsHeadH2k=? and b.sendState = ?)" + " and a.hsFourNo not in
		 * (select c.seqNum from CheckExg c where c.checkHead=?)", new Object[]
		 * { CommonUtils.getCompany(), MaterielType.FINISHED_PRODUCT,
		 * emsHeadH2k,Integer.valueOf(SendState.SEND), checkHead }); } else {
		 */
		return this
				.find("select a from InnerMergeData a where a.company= ? and a.imrType=?"
						+ " and a.hsFourNo in (select b.seqNum from EmsHeadH2kExg b where b.emsHeadH2k=?)"
						+ " and a.hsFourNo not in (select c.seqNum from CheckExg c where c.checkHead=?)",
						new Object[] { CommonUtils.getCompany(),
								MaterielType.FINISHED_PRODUCT, emsHeadH2k,
								checkHead });
		// }
	}

	/**
	 * 
	 * 显示核销表头
	 * 
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 所有List数据
	 */
	public List findCancelHead(boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerHead a where a.company.id= ? order by a.cancelTimes desc ";
		} else {
			hsql = "select a from CancelCusHead a where a.company.id= ?  order by a.cancelTimes desc ";
		}
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 
	 * 显示核销表头
	 * 
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 所有CancelHead数据
	 */
	public CancelHead findMaxCancelTimesCancelHead(boolean isOwner,
			String declareType) {
		String hsql = "";
		List<Object> paramters = new ArrayList<Object>();
		if (isOwner) {
			hsql = "select a from CancelOwnerHead a where a.company.id= ? ";
		} else {
			hsql = "select a from CancelCusHead a where a.company.id= ?  ";
		}
		paramters.add(CommonUtils.getCompany().getId());
		if (declareType != null) {
			hsql += "  and a.declareType=? ";
			paramters.add(declareType);
		}
		hsql += " order by a.cancelTimes Desc ";
		List ls = this.find(hsql, paramters.toArray());

		if (ls != null && ls.size() > 0) {
			CancelHead newCancelHead = new CancelHead();
			int max = (ls.get(0) == null
					|| ((CancelHead) ls.get(0)).getCancelTimes() == null || ""
					.equals(((CancelHead) ls.get(0)).getCancelTimes())) ? 0
					: Integer
							.valueOf(((CancelHead) ls.get(0)).getCancelTimes());
			// 不能直接返回因为次数为字符型当为10后取不到最大的2011.08.18
			for (int i = 0; i < ls.size(); i++) {
				CancelHead head = (CancelHead) ls.get(i);
				int oldMax = (head.getCancelTimes() == null
						|| "".equals(head.getCancelTimes()) ? 0 : Integer
						.valueOf(head.getCancelTimes()));
				if (oldMax >= max) {
					max = oldMax;
					try {
						PropertyUtils.copyProperties(newCancelHead, head);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
			return newCancelHead;
		}
		return null;
	}

	/**
	 * 
	 * 显示核销单耗
	 * 
	 * @param head
	 *            核销表头
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 所有List数据
	 */
	public List findCancelUnitWear(CancelHead head, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerUnitWear a where a.company.id= ? and a.cancelHead.id= ?";
		} else {
			hsql = "select a from CancelCusUnitWear a where a.company.id= ? and a.cancelHead.id= ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				head.getId() });
	}

	/**
	 * 
	 * 转化海关核销单耗到自用核销单耗表
	 * 
	 * @param ownerHeadId
	 *            自用核销表头id
	 * @param cusHeadId
	 *            海关核销表头id
	 * @return 影响行数
	 */
	@SuppressWarnings("deprecation")
	public int convertCancelUnitWear(String ownerHeadId, String cusHeadId) {
		Session s = getSession();
		int count = 0;
		String hsql = "insert into " + "CancelownerUnitWear " + "select "
				+ "id, '" + ownerHeadId + "', cpSeqNum, "
				+ "version, ljSeqNum, unitWear, "
				+ "wear, unitUse, cpUse, ljUse, " + "'"
				+ CommonUtils.getCompany().getId() + "' " + "from "
				+ "CancelCusUnitWear " + "where " + "CancelHead = '"
				+ cusHeadId + "'";
		Connection conn = s.connection();
		try {
			PreparedStatement ps = conn.prepareStatement(hsql);
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * 显示核销单耗
	 * 
	 * @param head
	 *            核销表头
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 所有List数据
	 */
	public List findCancelUnitWear(CancelHead head, boolean isOwner, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerUnitWear a where a.cancelHead.id= ? and a.company.id= ?  ";
		} else {
			hsql = "select a from CancelCusUnitWear a where a.cancelHead.id= ? and a.company.id= ?  ";
		}
		paramters.add(head.getId());
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 
	 * 获得海关核销表
	 * 
	 * @param company
	 *            公司id
	 * @param reportDelcareType
	 *            报核类型
	 * @return 所有CancelCusHead数据
	 */
	public List<CancelCusHead> findCancelCusHead(Company company,
			String reportDelcareType) {
		String hsql = "select a from CancelCusHead a where a.company.id= ? and a.declareType = ? ";
		return this.find(hsql, new Object[] { company.getId(),
				reportDelcareType });
	}

	/**
	 * 
	 * 显示核销表头BY次数
	 * 
	 * @param times
	 *            报核次数
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 所有List数据
	 */
	public List findCancelHeadByTimes(String times, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerHead a where a.company.id= ? and a.cancelTimes = ?";
		} else {
			hsql = "select a from CancelCusHead a where a.company.id= ? and a.cancelTimes = ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				times });
	}

	/**
	 * 
	 * 返回核销类型
	 * 
	 * @param declareType
	 *            报核类型
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelHeadByType(String declareType, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerHead a where a.company.id = ? and a.declareType = ?";
		} else {
			hsql = "select a from CancelCusHead a where a.company.id = ? and a.declareType = ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				declareType });
	}

	/**
	 * 
	 * 返回核销状态
	 * 
	 * @param declareState
	 *            报核类型
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelHeadByState(String declareState, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerHead a where a.company.id = ? and a.declareState = ?";
		} else {
			hsql = "select a from CancelCusHead a where a.company.id = ? and a.declareState = ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				declareState });
	}

	/**
	 * 
	 * 保存核销表头
	 * 
	 * @param cancelHead
	 * @throws DataAccessException
	 */
	public void saveCancelHead(CancelHead cancelHead)
			throws DataAccessException {
		this.saveOrUpdate(cancelHead);
	}

	/**
	 * 
	 * 保存Object
	 * 
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveObject(Object obj) throws DataAccessException {
		this.getHibernateTemplate().save(obj);
	}

	/**
	 * 
	 * 保存Object flush
	 * 
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveObjectFlush(Object obj) throws DataAccessException {
		this.getHibernateTemplate().save(obj);
		this.getHibernateTemplate().flush();
	}

	/**
	 * 
	 * 清除缓存
	 * 
	 */
	public void clear() {
		this.getHibernateTemplate().clear();
	}

	/**
	 * 获取核销头
	 * 
	 * @param cancelHead
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 返回CancelHead
	 */
	public CancelHead getHead(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerHead a where a.id = "
					+ cancelHead.getId();
		} else {
			hsql = "select a from CancelCusHead a where a.id = "
					+ cancelHead.getId();
		}
		List list = this.find(hsql);
		return (CancelHead) list.get(0);
	}

	/**
	 * 
	 * 删除核销表头
	 * 
	 * @param cancelHead
	 */
	public void deleteCancelHead(CancelHead cancelHead) {
		this.delete(cancelHead);
	}

	/**
	 * 显示核销--报关单
	 * 
	 * @param cancelHead
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelCustomsDeclara(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerCustomsDeclara a where a.cancelHead.id= ? and a.company.id= ? ";
		} else {
			hsql = "select a from CancelCusCustomsDeclara a where a.cancelHead.id= ? and a.company.id= ?";
		}
		hsql += " order by  a.inOutportFlat,a.declareDate, a.customNo";
		return this.find(hsql, new Object[] { cancelHead.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 获取核销报关单_I or E
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param flag
	 *            进口还是出口
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelCustomsDeclaraByFlag(CancelHead cancelHead,
			String flag, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ? and a.inOutportFlat = ? order by a.declareDate,a.customNo";
		} else {
			hsql = "select a from CancelCusCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ? and a.inOutportFlat = ? order by a.declareDate,a.customNo";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				cancelHead.getId(), flag });
	}

	/**
	 * 
	 * 获取核销报关单_I or E份数
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param flag
	 *            进口还是出口
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public Integer findCancelCustomsDeclaraByFlagCount(CancelHead cancelHead,
			String flag, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select count(*) from CancelOwnerCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ? and a.inOutportFlat = ?";
		} else {
			hsql = "select count(*) from CancelCusCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ? and a.inOutportFlat = ?";
		}
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), cancelHead.getId(), flag });
		if (list != null && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 
	 * 获取核销报关单单号
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelCustomsDeclaraCode(CancelHead cancelHead,
			boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a.customNo from CancelOwnerCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ?";
		} else {
			hsql = "select a.customNo from CancelCusCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				cancelHead.getId() });
	}

	/**
	 * 
	 * 保存核销--报关单
	 * 
	 * @param cancelCustomsDeclara
	 * @throws DataAccessException
	 */
	public void saveCancelCustomsDeclara(
			CancelCustomsDeclara cancelCustomsDeclara)
			throws DataAccessException {
		this.saveOrUpdate(cancelCustomsDeclara);
	}

	/**
	 * 查询电子帐册报关单 核销id
	 * 
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCustomsDeclaration(CancelHead cancelHead, boolean isOwner,Date beginDate,Date endDate,String customsDecCode) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "";
		if (isOwner) {
//			hsql = "select a from CustomsDeclaration a where a.company.id = ? and a.effective = ? and a.customsDeclarationCode "
//					+ " not in (select c.customNo from CancelOwnerCustomsDeclara c where c.company.id = ?)";
			hsql = "select a from CustomsDeclaration a where a.company.id = ? and a.effective = ? "
					+ " and not exists (select c.customNo from CancelOwnerCustomsDeclara c where c.company.id = ? and c.customNo=a.customsDeclarationCode)";
		} else {
//			hsql = "select a from CustomsDeclaration a where a.company.id = ? and a.effective = ? and a.customsDeclarationCode "
//					+ " not in (select c.customNo from CancelCusCustomsDeclara c where c.company.id = ?) ";
			hsql = "select a from CustomsDeclaration a where a.company.id = ? and a.effective = ? "
					+ " and not exists (select c.customNo from CancelCusCustomsDeclara c where c.company.id = ? and a.customsDeclarationCode = c.customNo) ";

		}
		para.add(CommonUtils.getCompany().getId());
		para.add(new Boolean(true));
		para.add(CommonUtils.getCompany().getId());
		
		if(beginDate!=null){
			hsql+=" and a.declarationDate >= ?";
			para.add(CommonUtils.getBeginDate(beginDate));
		}
		
		if(endDate!=null){
			hsql+=" and a.declarationDate <= ?";
			para.add(CommonUtils.getEndDate(endDate));
		}
		
		if(customsDecCode!=null&&!"".equals(customsDecCode.trim())){
			hsql+=" and a.customsDeclarationCode = ? ";
			para.add(customsDecCode);
		}
		
		return this.find(hsql,para.toArray());
	}

	/**
	 * 
	 * 核销--新增所有报关单
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCustomsDeclaration(Date beginDate, Date endDate,
			boolean isOwner) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		hql = "select a from CustomsDeclaration a where a.company.id = ? "
				+ "and a.effective = ? " + "and a.tradeMode.code != ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add("0110");
		if (beginDate != null) {
			hql += " and (a.declarationDate>=?) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.declarationDate<=?) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 
	 * 删除核销--报关单
	 * 
	 * @param cancelCustomsDeclara
	 */
	public void deleteCancelCustomsDeclara(
			CancelCustomsDeclara cancelCustomsDeclara) {
		this.delete(cancelCustomsDeclara);
	}

	/**
	 * 
	 * 显示核销--料件原始
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelImgBefore(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerImgBefore a where a.company.id= ? and a.cancelHead.id= ? order by a.emsSeqNum";
		} else {
			hsql = "select a from CancelCusImgBefore a where a.company.id= ? and a.cancelHead.id= ? order by a.emsSeqNum";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				cancelHead.getId() });
	}

	/**
	 * 显示核销--料件原始
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public void deleteCancelImgBefore(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = " delete from CancelOwnerImgBefore a where   a.cancelHead.id= ?  ";
		} else {
			hsql = " delete from CancelCusImgBefore a where  a.cancelHead.id= ?  ";
		}
		this.batchUpdateOrDelete(hsql, new Object[] { cancelHead.getId() });
	}

	/**
	 * 
	 * 显示核销--料件中间
	 * 
	 * @param cancelHead
	 *            核销id
	 * @return
	 */
	public List findCancelImgMiddle(CancelHead cancelHead) {

		return this
				.find("select a from CancelImgMiddle a where a.company= ? and a.cancelHead= ?",
						new Object[] { CommonUtils.getCompany(), cancelHead });
	}

	/**
	 * 
	 * 显示核销--料件结果
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelImgResult(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		List list = null;
		if (isOwner) {
			hsql = "select a from CancelOwnerImgResult a where a.company.id= ? and a.cancelHead.id= ? ";// order
			// by
			// a.emsSeqNum
			list = this.find(hsql, new Object[] {
					CommonUtils.getCompany().getId(), cancelHead.getId() });
			Collections.sort(list, new Comparator<CancelOwnerImgResult>() {

				public int compare(CancelOwnerImgResult o1,
						CancelOwnerImgResult o2) {
					// TODO Auto-generated method stub
					if (o1.getEmsSeqNum() > o2.getEmsSeqNum()) {
						return 1;
					}
					if (o1.getEmsSeqNum() < o2.getEmsSeqNum()) {
						return -1;
					}
					return 0;
				}

			});
		} else {
			hsql = "select a from CancelCusImgResult a where a.company.id= ? and a.cancelHead.id= ? ";// order
			// by
			// a.emsSeqNum
			list = this.find(hsql, new Object[] {
					CommonUtils.getCompany().getId(), cancelHead.getId() });
			Collections.sort(list, new Comparator<CancelCusImgResult>() {

				public int compare(CancelCusImgResult o1, CancelCusImgResult o2) {
					// TODO Auto-generated method stub
					if (o1.getEmsSeqNum() > o2.getEmsSeqNum()) {
						return 1;
					}
					if (o1.getEmsSeqNum() < o2.getEmsSeqNum()) {
						return -1;
					}
					return 0;
				}

			});
		}
		return list;
	}

	/**
	 * 
	 * 删除核销--料件结果
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 */
	public void deleteCancelImgResult(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = " delete from CancelOwnerImgResult a where   a.cancelHead.id= ?  ";
		} else {
			hsql = " delete from CancelCusImgResult a  where  a.cancelHead.id= ?  ";
		}
		this.batchUpdateOrDelete(hsql, new Object[] { cancelHead.getId() });
	}

	/**
	 * 
	 * 删除核销--每月平均单价表
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 */
	public void deleteCancelImgAvgPrice(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "delete from CancelOwnerImgAvgPrice a where a.cancelHead.id=?";
		} else {
			hsql = "delete from CancelCusImgAvgPrice a where a.cancelHead.id=?";
		}
		this.batchUpdateOrDelete(hsql, new Object[] { cancelHead.getId() });
	}

	/**
	 * 
	 * 显示核销--成品原始
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelExgBefore(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerExgBefore a where a.company.id= ? and a.cancelHead.id= ? order by a.emsSeqNum";
		} else {
			hsql = "select a from CancelCusExgBefore a where a.company.id= ? and a.cancelHead.id= ? order by a.emsSeqNum";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				cancelHead.getId() });
	}

	/**
	 * 
	 * 显示核销--成品中间
	 * 
	 * @param cancelHead
	 *            核销id
	 * @return
	 */
	public List findCancelExgMiddle(CancelHead cancelHead) {

		return this
				.find("select a from CancelExgMiddle a where a.company= ? and a.cancelHead= ?",
						new Object[] { CommonUtils.getCompany(), cancelHead });
	}

	/**
	 * 
	 * 显示核销--成品结果
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findCancelExgResult(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		List list = null;
		if (isOwner) {
			hsql = "select a from CancelOwnerExgResult a where a.company.id= ? and a.cancelHead.id= ? order by a.emsSeqNum";
			list = this.find(hsql, new Object[] {
					CommonUtils.getCompany().getId(), cancelHead.getId() });
			Collections.sort(list, new Comparator<CancelOwnerExgResult>() {

				public int compare(CancelOwnerExgResult o1,
						CancelOwnerExgResult o2) {
					// TODO Auto-generated method stub
					if (o1.getEmsSeqNum() > o2.getEmsSeqNum()) {
						return 1;
					}
					if (o1.getEmsSeqNum() < o2.getEmsSeqNum()) {
						return -1;
					}
					return 0;
				}

			});
		} else {
			hsql = "select a from CancelCusExgResult a where a.company.id= ? and a.cancelHead.id= ? order by a.emsSeqNum";
			list = this.find(hsql, new Object[] {
					CommonUtils.getCompany().getId(), cancelHead.getId() });
			Collections.sort(list, new Comparator<CancelCusExgResult>() {

				public int compare(CancelCusExgResult o1, CancelCusExgResult o2) {
					// TODO Auto-generated method stub
					if (o1.getEmsSeqNum() > o2.getEmsSeqNum()) {
						return 1;
					}
					if (o1.getEmsSeqNum() < o2.getEmsSeqNum()) {
						return -1;
					}
					return 0;
				}

			});
		}
		return list;
	}

	/**
	 * 显示核销--获取料件--来自报关单商品信息
	 */
	/*
	 * public List findImgFromCustomsDeclarationCommInfo() { return this.find(
	 * "select
	 * a.commSerialNo,a.commAmount,a.commTotalPrice,a.customsDeclaration.
	 * customsDeclarationCode," +
	 * "b.inOutType,a.customsDeclaration.tradeMode.name,a.customsDeclaration.transferMode.name,b.code,b.deducMark,"
	 * + "b.name,b.deducNote,a.commName,a.commSpec from
	 * CustomsDeclarationCommInfo a,Deduc b where
	 * b.tradeCode=a.customsDeclaration.tradeMode.code" + " and
	 * a.customsDeclaration.company= ? and a.materielProductFlag= ? and
	 * b.inOutType='I'", new Object[] {
	 * CommonUtils.getCompany(),Integer.valueOf(MaterielType.MATERIEL) }); }
	 */

	/*
	 * public Deduc findDeducByTradeCode(String code, String inoutType) { List
	 * list = this.find( "from Deduc a where a.tradeCode = ? and a.inOutType=?",
	 * new Object[] { code, inoutType }); if (list != null && list.size() > 0) {
	 * return (Deduc) list.get(0); } return null; }
	 */
	/**
	 * 显示核扣方式资料
	 */
	public List findAllDeduc(String inoutType) {
		return this.find("select a from Deduc a where a.inOutType=?",
				new Object[] { inoutType });
	}

	/**
	 * 显示核扣方式资料
	 */
	public List findDeducByType(String inoutType) {
		return this.find("select a from Deduc a where a.inOutType = ?",
				new Object[] { inoutType });
	}

	/**
	 * 
	 * 查询电子帐册报关单明细
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isProduct
	 *            是料件或者是成品
	 * @param iseffective
	 *            是否生效
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List findImpExpCommInfo(Date beginDate, Date endDate,
			boolean isProduct, Boolean iseffective, CancelHead cancelHead,
			boolean isOwner) {
		System.out.println("parameters:" + "beginDate=" + beginDate
				+ "endDate=" + endDate + "isProduct=" + isProduct
				+ "iseffective=" + iseffective + "cancelHead id="
				+ cancelHead.getId() + "isOwner=" + isOwner);
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		// hsql += " left outer join fetch a.complex "
		// + " left outer join fetch a.legalUnit "
		// + " left outer join fetch a.secondLegalUnit "
		// + " left outer join fetch a.country "
		// + " left outer join fetch a.uses "
		// + " left outer join fetch a.levyMode "
		// + " left outer join fetch a.unit ";
		if (isOwner) {
			hsql += ",CancelOwnerCustomsDeclara b";
		} else {
			hsql += ",CancelCusCustomsDeclara b";
		}
		hsql += " left outer join fetch a.baseCustomsDeclaration ";
		hsql += " where a.baseCustomsDeclaration.company.id=? "
				+ " and b.company.id= ? and b.cancelHead.id= ? "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode=b.customNo";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());
		if (iseffective) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(iseffective);
		}
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else { // 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		/*
		 * if (beginDate != null) { hsql += " and
		 * a.baseCustomsDeclaration.declarationDate>=? ";
		 * parameters.add(beginDate); } if (endDate != null) { hsql += " and
		 * a.baseCustomsDeclaration.declarationDate<=? ";
		 * parameters.add(endDate); }
		 */
		// hsql += " and a.baseCustomsDeclaration.customsDeclarationCode in ";
		// if (isOwner) {
		// hsql +=
		// " (select b.customNo from CancelOwnerCustomsDeclara b where
		// b.company.id= ? and b.cancelHead.id= ?) ";
		// } else {
		// hsql +=
		// " (select b.customNo from CancelCusCustomsDeclara b where
		// b.company.id= ? and b.cancelHead.id= ?) ";
		// }
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(cancelHead.getId());
		hsql += " order by a.commSerialNo";
		System.out.println("hsql=" + hsql);
		List list = this.find(hsql, parameters.toArray());
		System.out.println("----------------------list size:" + list.size());
		return list;
	}

	/**
	 * 获取自用或者海关料件结果数据
	 * 
	 * @param emsSeqNum
	 *            帐册序号
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @param beforeTimes
	 *            报核次数
	 * @return
	 */
	public Double[] findResultNum(String emsSeqNum, boolean isOwner,
			String beforeTimes) {
		Double[] x = new Double[2];
		String tables = "";
		if (isOwner) {
			tables = "CancelOwnerImgResult";
		} else {
			tables = "CancelCusImgResult";
		}
		List list = this
				.find("select a.resultNum,a.resultSumPrice from "
						+ tables
						+ " a where a.cancelHead.cancelTimes=? and a.emsSeqNum = ? and a.company.id = ?",
						new Object[] { beforeTimes, Integer.valueOf(emsSeqNum),
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			Object[] objs = (Object[]) list.get(0);
			x[0] = (Double) objs[0];
			x[1] = (Double) objs[1];
			return x;
		}
		x[0] = Double.valueOf(0);
		x[1] = Double.valueOf(0);
		return x;
	}

	/**
	 * 获取自用或者海关料件结果数据
	 * 
	 * @param emsSeqNum
	 *            帐册序号
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @param beforeTimes
	 *            报核次数
	 * @return
	 */
	public List findAllResultNum(boolean isOwner, String beforeTimes) {
		String tables = "";
		if (isOwner) {
			tables = "CancelOwnerImgResult";
		} else {
			tables = "CancelCusImgResult";
		}
		return this
				.find("select a.emsSeqNum ,a.resultNum,a.resultSumPrice from "
						+ tables
						+ " a where a.cancelHead.cancelTimes=? and a.company.id = ?",
						new Object[] { beforeTimes,
								CommonUtils.getCompany().getId() });
		// return this
		// .find(
		// "select a.emsSeqNum ,a.factLeaveNum,a.factLeaveSumPrice from "
		// + tables
		// + " a where a.cancelHead.cancelTimes=? and a.company.id = ?",
		// new Object[] { beforeTimes,
		// CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示电子帐册料件
	 * 
	 * @param emsHeadH2k
	 *            电子帐册id
	 * @return
	 */
	public List findEmsEdiImg(EmsHeadH2k emsHeadH2k) {
		if (getIsEmsH2kSend()) {
			return this
					.find("from EmsHeadH2kImg a where a.emsHeadH2k.id = ? and a.sendState = ? and a.modifyMark <> 2 order by seqNum",
							new Object[] { emsHeadH2k.getId(),
									Integer.valueOf(SendState.SEND) });
		} else {
			return this
					.find("from EmsHeadH2kImg a where a.emsHeadH2k.id = ? and a.modifyMark <> 2 order by seqNum",
							new Object[] { emsHeadH2k.getId() });
		}
	}

	/**
	 * 显示电子帐册成品
	 * 
	 * @param emsHeadH2k
	 *            电子帐册id
	 * @return
	 */
	public List findEmsEdiExg(EmsHeadH2k emsHeadH2k) {
		if (getIsEmsH2kSend()) {
			return this
					.find("from EmsHeadH2kExg a where a.emsHeadH2k.id = ? and a.sendState = ? order by seqNum",
							new Object[] { emsHeadH2k.getId(),
									Integer.valueOf(SendState.SEND) });
		} else {
			return this
					.find("from EmsHeadH2kExg a where a.emsHeadH2k.id = ? order by seqNum",
							new Object[] { emsHeadH2k.getId() });
		}
	}

	/**
	 * 显示自用或者海关料件中间结果
	 * 
	 * @param cancelHead
	 *            核销id
	 * @param seqNum
	 *            帐册序号
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return
	 */
	public List getCancelImgBefore(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerImgBefore a "
					+ " left outer join fetch a.cancelHead "
					+ "where a.cancelHead.id = ? and a.company.id = ?";
		} else {
			hsql = "select a from CancelCusImgBefore a "
					+ "  left outer join fetch a.cancelHead "
					+ "where a.cancelHead.id = ? and a.company.id = ?";
		}
		return this.find(hsql, new Object[] { cancelHead.getId(),
				CommonUtils.getCompany().getId() });
	}

	// /**
	// * 显示自用或者海关料件中间结果
	// *
	// * @param cancelHead
	// * 核销id
	// * @param seqNum
	// * 帐册序号
	// * @param isOwner
	// * 是否是自用核销或者是海关核销
	// * @return
	// */
	// public List getCancelImgBefore(CancelHead cancelHead, String seqNum,
	// boolean isOwner) {
	// String hsql = "";
	// if (isOwner) {
	// hsql = "select a from CancelOwnerImgBefore a "
	// + "where a.cancelHead.id = ? and a.emsSeqNum = ? and a.company.id = ?";
	// } else {
	// hsql = "select a from CancelCusImgBefore a "
	// + "where a.cancelHead.id = ? and a.emsSeqNum = ? and a.company.id = ?";
	// }
	// return this.find(hsql, new Object[] { cancelHead.getId(),
	// Integer.valueOf(seqNum), CommonUtils.getCompany().getId() });
	// }
	/**
	 * 计算自用核销或者海关核销表头本期进口总金额（核增金额）
	 */
	public Double getimgImpTotalMoneyhz(CancelHead cancelHead, boolean isOwner) {
		List<Object> paramereas = new ArrayList<Object>();
		String tables = "";
		if (isOwner) {
			tables = "CancelOwnerImgBefore";
		} else {
			tables = "CancelCusImgBefore";
		}
		;
		String hsql = "select sum(a.price) from "
				+ tables
				+ " a where a.cancelHead.id=? and a.company.id = ? and checkWay = '+'";
		paramereas.add(cancelHead.getId());
		paramereas.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, paramereas.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 计算自用核销或者海关核销表头本期进口总金额（核减金额）
	 */
	public Double getimgImpTotalMoneyhj(CancelHead cancelHead, boolean isOwner) {
		List<Object> paramereas = new ArrayList<Object>();
		String tables = "";
		if (isOwner) {
			tables = "CancelOwnerImgBefore";
		} else {
			tables = "CancelCusImgBefore";
		}
		;
		String hsql = "select sum(a.price) from "
				+ tables
				+ " a where a.cancelHead.id=? and a.company.id = ? and checkWay = '-'";
		paramereas.add(cancelHead.getId());
		paramereas.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, paramereas.toArray());
		if (list.size() > 0 && list != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 显示核销--获取料件--来自报关单商品信息--根据结果筛选
	 * 
	 * @param seqNum
	 *            帐册序列
	 * @return
	 */
	public List findImgFromCustomsDeclarationCommInfoByResult(String seqNum) {

		return this
				.find("select a from CustomsDeclarationCommInfo a where a.customsDeclaration.company= ? and a.materielProductFlag= ?"
						+ " and a.commSerialNo= ? ",
						new Object[] { CommonUtils.getCompany(),
								Integer.valueOf(MaterielType.MATERIEL),
								Integer.valueOf(seqNum) });
	}

	/**
	 * 保存核销--料件原始数据
	 */
	public void saveCancelImgBefore(CancelImgBefore cancelImgBefore) {
		// this.saveOrUpdate(cancelImgBefore);
		cancelImgBefore.setCompany(CommonUtils.getCompany());
		this.saveOrUpdateDirect(cancelImgBefore);
	}

	/**
	 * 保存核销--成品原始数据
	 */
	public void saveCancelExgBefore(CancelExgBefore cancelExgBefore) {
		// this.saveOrUpdate(cancelExgBefore);
		cancelExgBefore.setCompany(CommonUtils.getCompany());
		this.saveOrUpdateDirect(cancelExgBefore);
	}

	/**
	 * 保存核销--料件结果数据
	 */
	public void saveCancelImgResult(CancelImgResult cancelImgResult) {
		// this.saveOrUpdate(cancelImgResult);
		cancelImgResult.setCompany(CommonUtils.getCompany());
		this.saveOrUpdateDirect(cancelImgResult);
	}

	/**
	 * 保存核销--料件单价数据
	 */
	public void saveCancelImgPriceResult(CancelImgAvgPrice cancelImgAvgPrice) {
		this.saveOrUpdate(cancelImgAvgPrice);
	}

	/**
	 * 保存核销--成品结果数据
	 */
	public void saveCancelExgResult(CancelExgResult cancelExgResult) {
		this.saveOrUpdate(cancelExgResult);
	}

	/**
	 * 显示核销--获取成品--来自报关单商品信息
	 */
	/*
	 * public List findExgFromCustomsDeclarationCommInfo() { return this.find(
	 * "select
	 * a.commSerialNo,a.commAmount,a.commTotalPrice,a.customsDeclaration.
	 * customsDeclarationCode," +
	 * "b.inOutType,a.customsDeclaration.tradeMode.name,a.customsDeclaration.transferMode.name,b.code,b.deducMark,"
	 * + "b.name,b.deducNote,a.complex.name,a.commSpec,a.legalAmount from
	 * CustomsDeclarationCommInfo a,Deduc b " + "where
	 * b.tradeCode=a.customsDeclaration.tradeMode.code" + " and
	 * a.customsDeclaration.company= ? and a.materielProductFlag= ? and
	 * b.inOutType='E'", new Object[] {
	 * CommonUtils.getCompany(),Integer.valueOf(MaterielType.FINISHED_PRODUCT)
	 * }); }
	 */
	/**
	 * 显示报关单成品商品信息
	 * 
	 * @return
	 */
	public List findExgCustoms() {
		return this
				.find("select a from CustomsDeclarationCommInfo  a  where  a.customsDeclaration.company= ? and a.materielProductFlag= ?",
						new Object[] { CommonUtils.getCompany(),
								Integer.valueOf(MaterielType.FINISHED_PRODUCT) });
	}

	/**
	 * 
	 * 批量删除核销料件原始/结果数据
	 * 
	 * @param cancelHead
	 * @param isOwner
	 */
	public void deleteCancelImg(CancelHead cancelHead, boolean isOwner) {
		this.deleteCancelImgBefore(cancelHead, isOwner);
		this.deleteCancelImgResult(cancelHead, isOwner);
	}

	/**
	 * 删除料件或者成品
	 * 
	 * @param ls
	 *            参数为List
	 */
	public void deleteCancelImgExgList(List ls) {
		this.deleteAll(ls);
	}

	/**
	 * 删除核销单耗表
	 * 
	 * @param cancelHead
	 * @param isOwner
	 */
	public void deleteCancelUnitWear(CancelHead cancelHead, boolean isOwner) {
		// this.deleteAll(this.findCancelUnitWear(cancelHead, isOwner));
		String hsql = "";
		if (isOwner) {
			hsql = " delete from CancelOwnerUnitWear a where   a.cancelHead.id = ?";
		} else {
			hsql = " delete from CancelCusUnitWear a where  a.cancelHead.id = ?";
		}
		// this.batchUpdateOrDeleteByJDBC(hsql,
		// new Object[] { cancelHead.getId() });
		this.batchUpdateOrDelete(hsql, new Object[] { cancelHead.getId() });

	}

	/**
	 * 批量删除核销成品原始/结果数据
	 * 
	 * @param cancelHead
	 * @param isOwner
	 */
	public void deleteCancelExg(CancelHead cancelHead, boolean isOwner) {
		this.deleteAll(findCancelExgBefore(cancelHead, isOwner));
		this.deleteAll(findCancelExgResult(cancelHead, isOwner));
	}

	/**
	 * 
	 * 显示核销--报关单(求报关单份数)
	 * 
	 * @param cancelHead
	 *            核销头id
	 * @param inOutportFlat
	 *            进口或者出口
	 * @param isOwner
	 * @return
	 */
	public List findCancelCustomsDeclaraNum(CancelHead cancelHead,
			String inOutportFlat, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ? "
					+ " and a.inOutportFlat= ?";
		} else {
			hsql = "select a from CancelCusCustomsDeclara a where a.company.id= ? and a.cancelHead.id= ? "
					+ " and a.inOutportFlat= ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				cancelHead.getId(), inOutportFlat });
	}

	/**
	 * 获得电子帐册BOM
	 * 
	 * @param seqNum
	 *            帐册序号
	 * @param emsHeadH2k
	 *            帐册id
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsBomByExgBeforeAndVersion(String seqNum,
			EmsHeadH2k emsHeadH2k, String version) {
		return this
				.find("select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? and a.company= ?"
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k= ? and a.emsHeadH2kVersion.version= ?",
						new Object[] { Integer.valueOf(seqNum),
								CommonUtils.getCompany(), emsHeadH2k, version });
	}

	/**
	 * 保存临时表
	 * 
	 * @param cancelTemp
	 * @throws DataAccessException
	 */
	public void saveCancelTemp(CancelTemp cancelTemp)
			throws DataAccessException {
		this.saveOrUpdate(cancelTemp);
	}

	/**
	 * 查询临时表中的序号，名称，规格，数量，单价，单重
	 * 
	 * @return
	 */
	public List findGroupImg() {
		return this
				.find("select a.emsSeqNum,a.name,a.spec,sum(a.num),sum(a.price),sum(a.weight)"
						+ " from CancelTemp a group by a.emsSeqNum,a.name,a.spec");
	}

	/**
	 * 批量删除临时表所有数据
	 */
	public void deleteCancelTemp() {
		this.deleteAll(this.find("from CancelTemp"));
	}

	/**
	 * 批量删除核销各表
	 * 
	 * @param cancelHead
	 * @param isOwner
	 */
	public void deleteCancelAll(CancelHead cancelHead, boolean isOwner) {
		deleteCancelTemp();
		deleteCancelExg(cancelHead, isOwner);
		deleteCancelImg(cancelHead, isOwner);
		deleteCancelImgAvgPrice(cancelHead, isOwner);
		this.deleteCancelUnitWear(cancelHead, isOwner);
		this.deleteAll(findCancelCustomsDeclara(cancelHead, isOwner));
	}

	/**
	 * 根据序号查询Bom表
	 * 
	 * @param seqNum
	 *            备案序号
	 * @param emsHeadH2k
	 *            帐册id
	 * @return
	 */
	public List findBomBySeqNum(Integer seqNum, EmsHeadH2k emsHeadH2k) {
		return this
				.find("select a from EmsHeadH2kBom a "
						+ " left outer join fetch a.complex "
						+ " left outer join fetch a.unit "
						+ " where a.seqNum = ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? ",

				new Object[] { Integer.valueOf(seqNum), emsHeadH2k.getId() });
	}

	/**
	 * 获取报关单（成品出口，转厂出， 返工复出）总数量
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public Double findCommAmount(Integer seqNum, String version,
			Date beginDate, Date endDate, Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?,?,?) and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.commSerialNo = ?";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (version != null) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 获取报关单退厂返工总总数量
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public Double findCommAmount2(Integer seqNum, String version,
			Date beginDate, Date endDate, Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.commSerialNo = ?";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (version != null) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 获取报关单（成品出口，转厂出， 返工复出）总金额
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public Double findCommMoney(Integer seqNum, String version, Date beginDate,
			Date endDate, Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?,?,?) and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.commSerialNo = ?";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (version != null) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 获取报关单退厂返工总金额
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public Double findCommMoney2(Integer seqNum, String version,
			Date beginDate, Date endDate, Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.commSerialNo = ?";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (version != null) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 得到退料出口总数量
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public List findBackMaterielAmountByType(Date beginDate, Date endDate,
			Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo, sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(type);// 退料出口
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 得到退料出口总金额
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public List findBackMaterielMoneyByType(Date beginDate, Date endDate,
			Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo, sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ?  and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(type);// 退料出口
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 得到料件内销总数量
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public List findCommAmountByType(Date beginDate, Date endDate,
			Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo, sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b "
					+ "where b.company.id= ? and b.cancelHead.id= ?"
					+ " and b.customNo is not null ) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b "
					+ "where b.company.id= ? and b.cancelHead.id= ?"
					+ " and b.customNo is not null ) ";
		}
		parameters.add(type);// 料件内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 得到料件内销总重量
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public List findCommWeightByType(Date beginDate, Date endDate,
			Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo, sum(a.netWeight) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b "
					+ "where b.company.id= ? and b.cancelHead.id= ?"
					+ " and b.customNo is not null ) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b "
					+ "where b.company.id= ? and b.cancelHead.id= ?"
					+ " and b.customNo is not null ) ";
		}
		parameters.add(type);// 料件内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 得到料件内销总金额
	 * 
	 * @param seqNum
	 *            备案序列
	 * @param version
	 *            版本号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public List findCommMoneyByType(Date beginDate, Date endDate,
			Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo, sum(a.dollarTotalPrice) "
				+ " from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) "
				+ " and a.baseCustomsDeclaration.company.id = ?  "
				+ " and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b "
					+ " where b.company.id= ? and b.cancelHead.id= ?"
					+ " and b.customNo is not null ) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b "
					+ " where b.company.id= ? and b.cancelHead.id= ?"
					+ "  and b.customNo is not null ) ";
		}
		parameters.add(type);// 料件内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获取每月平均单价
	 * 
	 * @param cancelHead
	 *            报核id
	 * @param isOwner
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findCancelImgAvgPrice(CancelHead cancelHead, boolean isOwner,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (isOwner) {
			hsql = "select a from CancelOwnerImgAvgPrice a where a.cancelHead.id=? ";
		} else {
			hsql = "select a from CancelCusImgAvgPrice a where a.cancelHead.id=? ";
		}
		parameters.add(cancelHead.getId());
		if (beginDate != null) {
			hsql += " and a.beginDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.endDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.emsSeqNum,a.beginDate ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 删除每月平均单价
	 * 
	 * @param cancelHead
	 *            报核id
	 * @param isOwner
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public void deleteCancelImgAvgPrice(CancelHead cancelHead, boolean isOwner,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (isOwner) {
			hsql = "delete CancelOwnerImgAvgPrice where cancelHead.id=? ";
		} else {
			hsql = "delete CancelCusImgAvgPrice where cancelHead.id=? ";
		}
		parameters.add(cancelHead.getId());
		if (beginDate != null) {
			hsql += " and beginDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and endDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		this.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 计算料件总平均单价
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @param isProduct
	 *            料件或者成品
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public List findNumAndMoneyForAveragePrice(Date beginDate, Date endDate,
			Boolean isEffect, boolean isProduct, CancelHead cancelHead,
			boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.commSerialNo,"
				+ " sum(case when a.baseCustomsDeclaration.impExpType in ("
				+ ImpExpType.DIRECT_IMPORT
				+ ","
				+ ImpExpType.TRANSFER_FACTORY_IMPORT
				+ ","
				+ ImpExpType.REMAIN_FORWARD_IMPORT
				+ ","
				+ ImpExpType.DIRECT_EXPORT
				+ ","
				+ ImpExpType.TRANSFER_FACTORY_EXPORT
				+ ","
				+ ImpExpType.REWORK_EXPORT
				+ ") then a.dollarTotalPrice "
				+ " when a.baseCustomsDeclaration.impExpType in ("
				+ ImpExpType.BACK_MATERIEL_EXPORT
				+ ","
				+ ImpExpType.REMAIN_FORWARD_EXPORT
				+ ","
				+ ImpExpType.REWORK_EXPORT
				+ ") then -a.dollarTotalPrice else a.dollarTotalPrice end "
				+ "),sum(case when a.baseCustomsDeclaration.impExpType in ("
				+ ImpExpType.DIRECT_IMPORT
				+ ","
				+ ImpExpType.TRANSFER_FACTORY_IMPORT
				+ ","
				+ ImpExpType.REMAIN_FORWARD_IMPORT
				+ ","
				+ ImpExpType.DIRECT_EXPORT
				+ ","
				+ ImpExpType.TRANSFER_FACTORY_EXPORT
				+ ","
				+ ImpExpType.REWORK_EXPORT
				+ ") then a.commAmount "
				+ " when a.baseCustomsDeclaration.impExpType in ("
				+ ImpExpType.BACK_MATERIEL_EXPORT
				+ ","
				+ ImpExpType.REMAIN_FORWARD_EXPORT
				+ ","
				+ ImpExpType.REWORK_EXPORT
				+ ") then -a.commAmount else a.commAmount end "
				+ ") from CustomsDeclarationCommInfo a "
				+ " left outer join  a.complex "
				+ " left outer join  a.legalUnit "
				+ " left outer join  a.secondLegalUnit "
				+ " left outer join  a.country "
				+ " left outer join  a.uses "
				+ " left outer join  a.levyMode "
				+ " left outer join  a.unit "
				+ " where a.baseCustomsDeclaration.company.id = ?  and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());
		// String para = "(";
		// for (int i = 0; i < impExpTypes.length; i++) {
		// if (i == impExpTypes.length - 1) {
		// para += "?";
		// } else {
		// para += "?,";
		// }
		// parameters.add(impExpTypes[i]);
		// }
		// para += ")";
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in  (?,?,?,?)";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 计算料件总重量应该来源于电子帐册中的重量比例因子,因此不用
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param isEffect
	 * @param isProduct
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */

	public List findImgTotalWeight(Date beginDate, Date endDate,
			Boolean isEffect, boolean isProduct, CancelHead cancelHead,
			boolean isOwner) {
		// List<Object> parameters = new ArrayList<Object>();
		// String hsql = "select a.commSerialNo, sum(a.netWeight) from
		// CustomsDeclarationCommInfo a "
		// + " where a.baseCustomsDeclaration.company.id = ? and
		// a.baseCustomsDeclaration.customsDeclarationCode in ";
		// if (isOwner) {
		// hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where
		// b.company.id= ? and b.cancelHead.id= ?) ";
		// } else {
		// hsql += " (select b.customNo from CancelCusCustomsDeclara b where
		// b.company.id= ? and b.cancelHead.id= ?) ";
		// }
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(cancelHead.getId());
		// if (!isProduct) {// 料件
		// hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
		// parameters.add(ImpExpType.DIRECT_IMPORT);
		// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		// parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		// parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		// } else {// 成品
		// hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
		// parameters.add(ImpExpType.DIRECT_EXPORT);
		// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// parameters.add(ImpExpType.REWORK_EXPORT);
		// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		// }
		// if (isEffect != null && isEffect.equals(new Boolean(true))) {
		// hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
		// parameters.add(isEffect);
		// }
		// if (beginDate != null) {
		// hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
		// parameters.add(beginDate);
		// }
		// if (endDate != null) {
		// hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
		// parameters.add(endDate);
		// }
		// hsql += " group by a.commSerialNo";
		// return this.find(hsql, parameters.toArray());
		return null;
	}

	/**
	 * 获取报关单明细总金额
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @param isProduct
	 *            料件或者成品
	 * @return
	 */
	public Double getTotalMoney(Date beginDate, Date endDate, Boolean isEffect,
			boolean isProduct) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			// parameters.add(ImpExpType.REWORK_EXPORT);
			// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 计算报核每月平均单价的总进口数量
	 * 
	 * @param cancelHead
	 *            报核id
	 * @param isOwner
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param head
	 *            帐册id
	 * @return
	 */
	public List getbaseCustomsDeclarationByAmountMonth(CancelHead cancelHead,
			boolean isOwner, Date beginDate, Date endDate, EmsHeadH2k head) {
		List<Object> parameters = new ArrayList<Object>();
		String tablename = "";
		if (isOwner) {
			tablename = "CancelOwnerCustomsDeclara";
		} else {
			tablename = "CancelCusCustomsDeclara";
		}
		String hsql = "select distinct a.seqNum, "
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?,?,?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?  and b.commSerialNo =a.seqNum ) as aa, "
				// ---{1}
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?  and b.commSerialNo =a.seqNum ) as bb, "
				// -------{2}内销
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?  and b.commSerialNo =a.seqNum ) as cc,"
				// ------{3}结转出口
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and b.baseCustomsDeclaration.tradeMode.code in(?,?) and bbb.cancelHead.id = ?   and b.commSerialNo =a.seqNum ) as dd"
				+ " from EmsHeadH2kImg a where  a.emsHeadH2k.id = ?  ";
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(cancelHead.getId());
		// ---------
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// 内销
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(cancelHead.getId());
		// －－－－－
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// 结转出口
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(cancelHead.getId());
		// －－－－－
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退换料件
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add("0300");
		parameters.add("0700");
		parameters.add(cancelHead.getId());

		parameters.add(head.getId());
		if (getIsEmsH2kSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		hsql += " order by a.seqNum";
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 计算报核每月平均单价的总进口金额
	 * 
	 * @param cancelHead
	 *            报核id
	 * @param isOwner
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param head
	 *            帐册id
	 * @return
	 */
	public List getbaseCustomsDeclarationByMoneyMonth(CancelHead cancelHead,
			boolean isOwner, Date beginDate, Date endDate, EmsHeadH2k head) {
		// List<Object> parameters = new ArrayList<Object>();
		// String tablename = "";
		// if (isOwner) {
		// tablename = "CancelOwnerCustomsDeclara";
		// } else {
		// tablename = "CancelCusCustomsDeclara";
		// }
		// String hsql = "select distinct a.seqNum, "
		// +
		// " (select sum(b.commTotalPrice* case when
		// b.baseCustomsDeclaration.exchangeRate is null then 1.0 when
		// b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else
		// b.baseCustomsDeclaration.exchangeRate end) from
		// CustomsDeclarationCommInfo b,"
		// + tablename
		// + " bbb "
		// +
		// " where bbb.customNo =
		// b.baseCustomsDeclaration.customsDeclarationCode "
		// +
		// " and b.baseCustomsDeclaration.impExpType in (?,?,?) and
		// b.baseCustomsDeclaration.effective = ? "
		// +
		// " and b.baseCustomsDeclaration.declarationDate>=? and
		// b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?
		// and b.commSerialNo =a.seqNum ) as aa, "
		// // ---{1}
		// +
		// " (select sum(b.commTotalPrice* case when
		// b.baseCustomsDeclaration.exchangeRate is null then 1.0 when
		// b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else
		// b.baseCustomsDeclaration.exchangeRate end) from
		// CustomsDeclarationCommInfo b,"
		// + tablename
		// + " bbb "
		// +
		// " where bbb.customNo =
		// b.baseCustomsDeclaration.customsDeclarationCode "
		// +
		// " and b.baseCustomsDeclaration.impExpType in (?) and
		// b.baseCustomsDeclaration.effective = ? "
		// +
		// " and b.baseCustomsDeclaration.declarationDate>=? and
		// b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?
		// and b.commSerialNo =a.seqNum ) as bb, "
		// // -------{2}内销
		// +
		// " (select sum(b.commTotalPrice* case when
		// b.baseCustomsDeclaration.exchangeRate is null then 1.0 when
		// b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else
		// b.baseCustomsDeclaration.exchangeRate end) from
		// CustomsDeclarationCommInfo b,"
		// + tablename
		// + " bbb "
		// +
		// " where bbb.customNo =
		// b.baseCustomsDeclaration.customsDeclarationCode "
		// +
		// " and b.baseCustomsDeclaration.impExpType in (?) and
		// b.baseCustomsDeclaration.effective = ? "
		// +
		// " and b.baseCustomsDeclaration.declarationDate>=? and
		// b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?
		// and b.commSerialNo =a.seqNum ) as cc,"
		// // ------{3}结转出口
		// +
		// " (select sum(b.commTotalPrice* case when
		// b.baseCustomsDeclaration.exchangeRate is null then 1.0 when
		// b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else
		// b.baseCustomsDeclaration.exchangeRate end) from
		// CustomsDeclarationCommInfo b,"
		// + tablename
		// + " bbb "
		// +
		// " where bbb.customNo =
		// b.baseCustomsDeclaration.customsDeclarationCode "
		// +
		// " and b.baseCustomsDeclaration.impExpType in (?) and
		// b.baseCustomsDeclaration.effective = ? "
		// +
		// " and b.baseCustomsDeclaration.declarationDate>=? and
		// b.baseCustomsDeclaration.declarationDate<=? and
		// b.baseCustomsDeclaration.tradeMode.code in(?,?) and bbb.cancelHead.id
		// = ? and b.commSerialNo =a.seqNum ) as dd"
		// + " from EmsHeadH2kImg a where a.emsHeadH2k.id = ? ";
		// parameters.add(ImpExpType.DIRECT_IMPORT);
		// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		// parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		// parameters.add(new Boolean(true));
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// parameters.add(CommonUtils.getEndDate(endDate));
		// parameters.add(cancelHead.getId());
		// // ---------
		// parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// 内销
		// parameters.add(new Boolean(true));
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// parameters.add(CommonUtils.getEndDate(endDate));
		// parameters.add(cancelHead.getId());
		// // －－－－－
		// parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// 结转出口
		// parameters.add(new Boolean(true));
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// parameters.add(CommonUtils.getEndDate(endDate));
		// parameters.add(cancelHead.getId());
		// // －－－－－
		// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退换料件
		// parameters.add(new Boolean(true));
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// parameters.add(CommonUtils.getEndDate(endDate));
		// parameters.add("0300");
		// parameters.add("0700");
		// parameters.add(cancelHead.getId());
		//
		// parameters.add(head.getId());
		// if (getIsEmsH2kSend()) {
		// hsql += " and a.sendState = ? ";
		// parameters.add(Integer.valueOf(SendState.SEND));
		// }
		// hsql += " order by a.seqNum";
		// return this.find(hsql, parameters.toArray());
		List<Object> parameters = new ArrayList<Object>();
		String tablename = "";
		if (isOwner) {
			tablename = "CancelOwnerCustomsDeclara";
		} else {
			tablename = "CancelCusCustomsDeclara";
		}
		String hsql = "select distinct a.seqNum, "
				+ " (select sum(b.commTotalPrice* case when b.baseCustomsDeclaration.exchangeRate  is null then 1.0 when b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else b.baseCustomsDeclaration.exchangeRate end) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?,?,?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?  and b.commSerialNo =a.seqNum ) as aa, "
				// ---{1}
				+ " (select sum(b.commTotalPrice* case when b.baseCustomsDeclaration.exchangeRate  is null then 1.0 when b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else b.baseCustomsDeclaration.exchangeRate end) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?  and b.commSerialNo =a.seqNum ) as bb, "
				// -------{2}内销
				+ " (select sum(b.commTotalPrice* case when b.baseCustomsDeclaration.exchangeRate  is null then 1.0 when b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else b.baseCustomsDeclaration.exchangeRate end) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.cancelHead.id = ?  and b.commSerialNo =a.seqNum ) as cc,"
				// ------{3}结转出口
				+ " (select sum(b.commTotalPrice* case when b.baseCustomsDeclaration.exchangeRate  is null then 1.0 when b.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 else b.baseCustomsDeclaration.exchangeRate end) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and  b.baseCustomsDeclaration.impExpType in (?) and b.baseCustomsDeclaration.effective = ?  "
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and b.baseCustomsDeclaration.tradeMode.code in(?,?) and bbb.cancelHead.id = ?   and b.commSerialNo =a.seqNum ) as dd"
				+ " from EmsHeadH2kImg a where  a.emsHeadH2k.id = ?  ";
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(cancelHead.getId());
		// ---------
		parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// 内销
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(cancelHead.getId());
		// －－－－－
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// 结转出口
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(cancelHead.getId());
		// －－－－－
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退换料件
		parameters.add(new Boolean(true));
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add("0300");
		parameters.add("0700");
		parameters.add(cancelHead.getId());

		parameters.add(head.getId());
		if (getIsEmsH2kSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		hsql += " order by a.seqNum";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示报关单数据
	 * 
	 * @param isEffect
	 *            是否生效
	 * @param cancelHead
	 *            报核id
	 * @param isOwner
	 * @param impExpTypes
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式
	 * @return
	 */
	public List getbaseCustomsDeclaration(Boolean isEffect,
			CancelHead cancelHead, boolean isOwner, Integer[] impExpTypes,
			String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclaration a "
				+ " where company.id = ?  and a.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());
		if (impExpTypes != null && impExpTypes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == impExpTypes.length - 1) {
					hsql += " a.impExpType=? ";
				} else {
					hsql += " a.impExpType=? or ";
				}
				parameters.add(impExpTypes[i]);
			}
			hsql += " ) ";
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.tradeMode.code=? ";
				} else {
					hsql += " a.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.effective = ?) ";
			parameters.add(isEffect);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 核销表头计算(料件进口,料件转厂,余料结转进口或者成品出口,转厂出口,返工复出)总金额
	 * 
	 * @param isEffect
	 *            是否生效
	 * @param isProduct
	 *            料件或者成品
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 * @return
	 */
	public List getTotalMoneyToBefore(Boolean isEffect, boolean isProduct,
			CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.company.id = ?  and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);// 0.料件进口
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 1.料件转厂
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 21.余料结转进口
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);// 4.成品出口
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 5.转厂出口
			parameters.add(ImpExpType.REWORK_EXPORT);// 7.返工复出
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 核销表头计算(退料出口,余料结转出口,或者退厂返工)总金额
	 * 
	 * @param isEffect
	 *            是否生效
	 * @param isProduct
	 *            料件或者成品
	 * @param cancelHead
	 *            核销id
	 * @param isOwner
	 * @return
	 */
	public List getTotalMoneyToAfter(Boolean isEffect, boolean isProduct,
			CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.company.id = ? and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?) ";// ,?
			// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 6.退料出口
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// 14.余料结转出口
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?) ";
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 2.退厂返工
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获取所有报关单表体
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @return
	 */
	public List findAllCustomsList(Date beginDate, Date endDate,
			Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a ";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获取自用或者海关料件表中的应剩余--总价值
	 * 
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public Double getEndMoney(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select sum(a.leaveSumPrice) from CancelOwnerImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		} else {
			hsql = "select sum(a.leaveSumPrice) from CancelCusImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		}
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), cancelHead.getId() });
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 获取自用或者海关料件表中的结余--总价值
	 */
	public List getOnlyEndMoney(CancelHead cancelHead, boolean isOwner) {
		String hsql = " ";
		if (isOwner) {
			hsql = "select sum(a.leaveSumPrice),sum(a.resultSumPrice) from CancelOwnerImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		} else {
			hsql = "select sum(a.leaveSumPrice),sum(a.resultSumPrice)  from CancelCusImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		}
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				cancelHead.getId() });

	}

	/**
	 * 获取自用或者海关料件表中的期初--总价值
	 * 
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public Double getBeginMoney(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select sum(a.beginMoney) from CancelOwnerImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		} else {
			hsql = "select sum(a.beginMoney) from CancelCusImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		}
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), cancelHead.getId() });
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 获取自用或者海关料件表中的总价值
	 * 
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public Double getExportUseMoney(CancelHead cancelHead, boolean isOwner) {
		String hsql = "";
		if (isOwner) {
			hsql = "select sum(a.useSumPrice) from CancelOwnerImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		} else {
			hsql = "select sum(a.useSumPrice) from CancelCusImgResult a where a.company.id= ? and a.cancelHead.id= ?";
		}
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), cancelHead.getId() });
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 得到料件内销总金额
	 * 
	 * @param isEffect
	 *            是否生效
	 * @param type
	 *            内销
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public List findCommMoneyByType(Boolean isEffect, int type,
			CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(type);// 料件内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 得到料件内销总金额
	 * 
	 * @param isEffect
	 *            是否生效
	 * @param type
	 *            内销
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public List findCommMoneyByType(Boolean isEffect, int type,
			String[] tradeCodes, CancelHead cancelHead, boolean isOwner) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? and a.baseCustomsDeclaration.customsDeclarationCode in ";
		if (isOwner) {
			hsql += " (select b.customNo from CancelOwnerCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		} else {
			hsql += " (select b.customNo from CancelCusCustomsDeclara b where b.company.id= ? and b.cancelHead.id= ?) ";
		}
		parameters.add(type);// 料件内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cancelHead.getId());
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}

		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 取得自用核销最大次数
	 * 
	 * @return
	 */
	public Integer getCheckTimes() {
		Integer no = new Integer(1);
		List list = this
				.find("select max(a.checkTimes) from CheckOwnerHead a where a.company.id = ?",
						new Object[] { CommonUtils.getCompany().getId() });
		if (list != null && list.get(0) != null) {
			String temp = list.get(0).toString();
			int n = Integer.parseInt(temp) + 1;
			no = new Integer(n);
		} else {
			no = new Integer(1);
		}
		return no;
	}

	/**
	 * 
	 * 返回工厂盘点料件
	 * 
	 * @param head
	 * 
	 * @return
	 */
	public List findFactoryCheckImg(CheckParameter head) {
		return this
				.find("select a from FactoryCheckImg a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 保存工厂盘点料件表
	 * 
	 * @param obj
	 */
	public void saveFactoryCheckImg(FactoryCheckImg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存工厂盘点成品表
	 */
	public void saveFactoryCheckExg(FactoryCheckExg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存工厂盘点成品折料件
	 */
	public void saveFactoryCheckExgConverImg(FactoryCheckExgConverImg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 返回工厂盘点成品
	 */
	public List findFactoryCheckExg(CheckParameter head) {
		return this
				.find("select a from FactoryCheckExg a where a.company.id= ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回工厂盘点成品折算料件
	 */
	public List findFactoryCheckExgConverImg(CheckParameter head) {
		return this
				.find("select a from FactoryCheckExgConverImg a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回中期核查－－工厂盘点－－成品折为料件
	 * 
	 * @param head
	 * @return
	 */
	public List findFactoryCheckExgConverImgAll(CheckParameter head) {
		return this
				.find("select a from FactoryCheckExgConverImg a where a.company.id = ? and a.head.id = ? order by ptNo",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回中期核查－－工厂盘点－－料件
	 * 
	 * @param head
	 * @return
	 */
	public List findFactoryCheckImgForBgAll(CheckParameter head) {
		return this
				.find("select a from FactoryCheckImgForBg a where a.company.id = ? and a.head.id = ? order by seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 删除工厂盘点成品折算料件
	 * 
	 * @param ptNo
	 * @return
	 */
	public void deleteFactoryCheckExgConverImg(CheckParameter head) {
		List list = this.findFactoryCheckExgConverImgAll(head);
		this.deleteAll(list);
	}

	/**
	 * 删除中期核查－－工厂盘点－－料件
	 * 
	 * @param head
	 */
	public void deleteFactoryCheckImgForBg(CheckParameter head) {
		List list = this.findFactoryCheckImgForBgAll(head);
		this.deleteAll(list);
	}

	/*
	 * public MaterialBomMaster findMaterialBomMasterByPtno(String ptNo){ List
	 * list = this.find("select a from MaterialBomMaster a " + " left outer join
	 * fetch a.materiel where a.materiel.ptNo=? and a.company.id = ?", new
	 * Object[]{ptNo,CommonUtils.getCompany().getId()}); if (list != null &&
	 * list.size()>0){ return (MaterialBomMaster) list.get(0); } return null; }
	 */

	/*
	 * public List findVersionByBomMaster(MaterialBomMaster master){ return
	 * this.find("select a from MaterialBomVersion a " + " left outer join fetch
	 * a.master where a.master.id = ? and a.company.id = ? order by a.version",
	 * new Object[]{master.getId(),CommonUtils.getCompany().getId()}); }
	 * 
	 * public List findDetailByVersion(MaterialBomVersion version){ return
	 * this.find("select a from MaterialBomDetail a " + " left outer join fetch
	 * a.materiel " + " left outer join fetch a.version where a.version.id = ?
	 * and a.company.id = ?", new
	 * Object[]{version.getId(),CommonUtils.getCompany().getId()}); }
	 */
	/**
	 * 返回报关常用工厂BOM料件资料
	 */
	public List findDetailByVersionNoAndCpNo(String cpNo, String version) {
		return this
				.find("select a from MaterialBomDetail a "
						+ " left outer join fetch a.materiel "
						+ " left outer join fetch a.version where a.version.version = ? and a.company.id = ? and a.version.master.materiel.ptNo = ?",
						new Object[] { Integer.valueOf(version),
								CommonUtils.getCompany().getId(), cpNo });
	}

	/**
	 * 返回报关常用工厂BOM料件资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findDetailByVersionAndPtNo(String ptNo) {
		return this
				.find("select a from MaterialBomDetail a "
						+ " left outer join fetch a.materiel "
						+ " left outer join fetch a.version where a.company.id = ? and a.materiel.ptNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 返回中期核查计算表
	 * 
	 * @return
	 */
	public List findCheckOwnerAccount(CheckParameter head) {
		return this
				.find("select a from CheckOwnerAccount a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回中期核查工厂盘点料件汇总
	 * 
	 * @param head
	 * @return
	 */
	public List findFactoryCheckImgCollect(CheckParameter head) {
		return this
				.find("select a from FactoryCheckImgResult a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 删除中期核查－－自用中期核查计算表－－核查计算表资料
	 * 
	 * @param head
	 */
	public void deleteCheckOwnerAccount(CheckParameter head) {
		List list = this.findCheckOwnerAccount(head);
		this.deleteAll(list);
	}

	/**
	 * 保存中期核查－－自用中期核查计算表－－核查计算表资料
	 * 
	 * @param obj
	 */
	public void saveCheckOwnerAccount(CheckOwnerAccount obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @param leftOuter
	 *            左连接内容
	 * @return 查询结果
	 */
	public List commonSearch(String selects, String className, List conditions,
			String groupBy, String leftOuter) {
		if (leftOuter == null) {
			leftOuter = "";
		}
		String sql = "";
		sql = " from " + className + " a " + leftOuter + " where a.company =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null
			sql = " select a from " + className + " a " + leftOuter
					+ " where a.company =? ";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					sql += "? ";
					params.add(condition.getValue());
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		// System.out.println("xxxxxxxxxxxxxxxx:" + sql);
		List result = this.find(sql, params.toArray());

		return result;
	}

	/**
	 * 获得分页 List 来自带多个?来代替名字参数的 hsql 语句
	 * 
	 * @param hsql
	 *            hibernate查询语句
	 * @param objParams
	 *            实体类参数
	 * @return 获得分页 List 来自带多个?来代替名字参数的 hsql 语句
	 */
	public List commonSearch(String hsql, Object[] objParams) {
		return super.find(hsql, objParams);
	}

	/**
	 * 查询报关常用工厂BOM料件资料
	 * 
	 * @param pkNoList
	 * @return 返回成品料件，版本号，单耗，损耗
	 */
	public List findMaterielBomDetail(List<String> pkNoList) {

		String hsql = "select m.ptNo,a.version.id,a.unitWaste,a.unitUsed,a.waste "
				+ "from MaterialBomDetail a "
				// + " left join a.version v "
				+ " left join a.materiel m " + " where 1=1 ";

		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.version.id = ?  ";
			} else {
				hsql += " or  a.version.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 显示资料副表来自料件类型 工厂料件
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与资料主表对应的按工厂料号排序的工厂料件
	 */
	public List<StatCusNameRelationMt> findStatCusNameRelationMt(
			String materielType) {
		return this.find(
				"select a from StatCusNameRelationMt a left join fetch a.materiel m "
						+ " left join fetch a.statCusNameRelation "
						+ " where a.statCusNameRelation.company.id= ? "
						+ " and a.statCusNameRelation.materielType= ? "
						+ " order by a.materiel.ptNo", new Object[] {
						CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 返回清单归并前数量
	 * 
	 * @param beginDate
	 *            清单的申报开始日期
	 * @param endDate
	 *            清单的申报结束日期
	 * @param ptNo
	 *            料件
	 * @return
	 */
	public Double findAtcMergeBeforeComInfoByptNo(int impExpFlag,
			Date beginDate, Date endDate, String ptNo, Integer impExpType) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.declaredAmount) from AtcMergeBeforeComInfo as a left outer join  a.materiel where a.afterComInfo.billList.impExpFlag = ? "
				+ " and a.afterComInfo.billList.effectstate= ? and (a.afterComInfo.billList.listDeclareDate <= ? and a.afterComInfo.billList.listDeclareDate >=? )"
				+ " and a.materiel.ptNo = ?";

		parameters.add(impExpFlag);
		parameters.add(true);
		// parameters.add(ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION);
		parameters.add(endDate);
		parameters.add(beginDate);
		parameters.add(ptNo);

		if (impExpType != null) {
			hsql += " and a.afterComInfo.billList.impExpType =? ";
			parameters.add(impExpType);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 返回清单归并前成品
	 * 
	 * @param beginDate
	 *            清单的申报开始日期
	 * @param endDate
	 *            清单的申报结束日期
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findAtcMergeBeforeComInfoCpByptNo(int impExpFlag,
			Date beginDate, Date endDate) {
		return this
				.find("select sum(a.declaredAmount),a.materiel.ptNo,a.afterComInfo.version from AtcMergeBeforeComInfo as a "
						+ "left outer join  a.materiel "
						+ "left outer join  a.afterComInfo "
						+ "where a.afterComInfo.billList.impExpFlag = ? "
						+ " and a.afterComInfo.billList.State= ? and a.afterComInfo.billList.listDeclareDate <= ? and a.afterComInfo.billList.listDeclareDate >=? "
						+ " group by a.materiel.ptNo,a.afterComInfo.version order by a.afterComInfo.version",
						new Object[] { impExpFlag, true,
								// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION,
								endDate, beginDate });
	}

	/**
	 * 删除中期核查－－工厂盘点－－料件汇总
	 * 
	 * @param head
	 */
	public void deleteTotalImg(CheckParameter head) {
		List list = findFactoryCheckImgCollect(head);
		this.deleteAll(list);
	}

	/**
	 * 返回中期核查计算表对比
	 * 
	 * @return
	 */
	public List findCheckOwnerAccountComport(CheckParameter head) {
		return this
				.find("select a from CheckOwnerAccountComport a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 删除中期核查－－自用中期核查计算表－－核查对比表资料
	 * 
	 * @param head
	 */
	public void deleteCheckOwnerAccountComport(CheckParameter head) {
		List list = this.findCheckOwnerAccountComport(head);
		this.deleteAll(list);

	}

	/**
	 * 删除滚动核销－－帐帐分析－－差异分析
	 * 
	 * @param head
	 */
	public void deleteCheckImgBgComport(CheckParameter head) {
		List list = this.findCheckBgCy(head);
		this.deleteAll(list);
	}

	/**
	 * 保存中期核查－－工厂盘点－－料件汇总
	 * 
	 * @param obj
	 */
	public void saveFactoryCheckImgResult(FactoryCheckImgResult obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存中期核查－－自用中期核查计算表－－核查对比表资料
	 * 
	 * @param obj
	 */
	public void saveCheckOwnerAccountComport(CheckOwnerAccountComport obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 删除中期核查－－参数设定资料
	 * 
	 * @param obj
	 */
	public void deleteCheckParameter(CheckParameter obj) {
		this.delete(obj);
	}

	/**
	 * 删除List
	 * 
	 * @param list
	 */
	public void deleteList(List list) {
		this.deleteAll(list);
	}

	/**
	 * 内部归并
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findMaterielFromInnerMergeData(String ptNo) {
		return this
				.find(" select a.hsAfterTenMemoNo,a.hsAfterComplex,a.hsAfterMemoUnit from InnerMergeData as a "
						+ " left join a.materiel "
						+ " left join a.hsBeforeLegalUnit"
						+ " left join a.hsBeforeEnterpriseUnit"
						+ " left join a.hsAfterComplex"
						+ " left join a.hsAfterMemoUnit"
						+ " left join a.hsAfterLegalUnit"
						+ " left join a.hsAfterSecondLegalUnit"
						+ " where a.company.id= ? and a.materiel.ptNo = ? and a.imrType = ?",
						new Object[] { CommonUtils.getCompany().getId(), ptNo,
								MaterielType.MATERIEL });
	}

	/**
	 * 显示中期核查－－自用中期核查计算表－－自定义差异颜色资料
	 * 
	 * @return 返回list
	 */
	public List findAllColor() {
		return this.find("select a from ColorSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示中期核查－－自用中期核查计算表－－自定义差异颜色资料
	 * 
	 * @param color
	 *            颜色
	 * @return 返回ColorSet
	 */
	public ColorSet findColorSet(int color) {
		List list = this
				.find("select a from ColorSet a where a.company.id = ? and a.color = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								Integer.valueOf(color) });
		if (list != null && list.size() > 0) {
			return (ColorSet) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存ColorSet
	 * 
	 * @param obj
	 */
	public void saveColorSet(ColorSet obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 显示中期核查－－自用中期核查计算表－－自定义差异颜色资料
	 * 
	 * @param num
	 *            开始数字
	 * @return 返回ColorSet
	 */
	public ColorSet findColorByNum(Double num) {
		List list = this
				.find("select a from ColorSet a where a.company.id = ? and (a.beginNum <= ? and a.endNum >= ?)",
						new Object[] { CommonUtils.getCompany().getId(), num,
								num });
		if (list != null && list.size() > 0) {
			return (ColorSet) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查出本月耗用量
	 * 
	 * @param beginMonth
	 *            报核开始日期
	 * @param lastMonth
	 *            报核结束日期
	 * @param head
	 *            帐册id
	 * @param isOwner
	 * @param cancelHead
	 *            核销id
	 * @return
	 */
	public List finLjUseNumByMonth(Date beginDate, Date endDate,
			EmsHeadH2k head, boolean isOwner, CancelHead cancelHead) {
		String tablename = "";
		if (isOwner) {
			tablename = "CancelOwnerCustomsDeclara";
		} else {
			tablename = "CancelCusCustomsDeclara";
		}
		String sql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
				+ " a.unitWear as unitWear, a.wear as wear, "
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and b.baseCustomsDeclaration.effective = ? and  b.baseCustomsDeclaration.impExpType in (?,?,?)"
				+ " and b.baseCustomsDeclaration.declarationDate>=? and b.baseCustomsDeclaration.declarationDate<=? and bbb.inOutportFlat = ?  and bbb.cancelHead.id = ?  and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  b.version = a.emsHeadH2kVersion.version ) as aa, "
				// ---
				+ " (select sum(c.commAmount) from CustomsDeclarationCommInfo c,"
				+ tablename
				+ " ccc "
				+ " where ccc.customNo = c.baseCustomsDeclaration.customsDeclarationCode "
				+ " and c.baseCustomsDeclaration.effective = ? and  c.baseCustomsDeclaration.impExpType in (?) "
				+ "and c.baseCustomsDeclaration.declarationDate>=? and c.baseCustomsDeclaration.declarationDate<=? and ccc.inOutportFlat = ?  and ccc.cancelHead.id = ? and  c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  c.version = a.emsHeadH2kVersion.version ) as cc"
				+ " from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(new Boolean(true));
		params.add(4);// 成品出口
		params.add(5);// 转厂出口
		params.add(7);// 返工复出
		params.add(CommonUtils.getBeginDate(beginDate));
		params.add(CommonUtils.getEndDate(endDate));
		params.add("E");
		params.add(cancelHead.getId());
		params.add(new Boolean(true));
		params.add(2);// 退厂返工
		params.add(CommonUtils.getBeginDate(beginDate));
		params.add(CommonUtils.getEndDate(endDate));
		params.add("I");
		params.add(cancelHead.getId());
		params.add(head.getId());

		if (getIsEmsH2kSend()) {
			sql += " and a.sendState = ? ";
			params.add(Integer.valueOf(SendState.SEND));
		}
		sql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		List list = find(sql, params.toArray());
		return list;
	}

	/**
	 * 查出所有耗用量
	 * 
	 * @param head
	 *            帐册id
	 * @param isOwner
	 * @param cancelHead
	 *            核销id
	 * @return
	 */
	public List finLjUseNum(EmsHeadH2k head, boolean isOwner,
			CancelHead cancelHead) {
//		 System.out.println("EmsHeadH2k id = "+head.getId()+"isOwner="+isOwner+"cancelHead="+cancelHead.getId());
//		 String tablename = "";
//		 if (isOwner) {
//		 tablename = "CancelOwnerCustomsDeclara";
//		 } else {
//		 tablename = "CancelCusCustomsDeclara";
//		 }
//		 String sql =
//		 "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
//		 + " a.unitWear as unitWear, a.wear as wear, "
//		 + " (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
//		 + tablename
//		 + " bbb "
//		 +
//		 " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
//		 +
//		 " and b.baseCustomsDeclaration.effective = ? and  b.baseCustomsDeclaration.impExpType in (?,?,?) and bbb.inOutportFlat = ?  and bbb.cancelHead.id = ?  and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  b.version = a.emsHeadH2kVersion.version ) as aa, "
//		 // ---
//		 + " (select sum(c.commAmount) from CustomsDeclarationCommInfo c,"
//		 + tablename
//		 + " ccc "
//		 +
//		 " where ccc.customNo = c.baseCustomsDeclaration.customsDeclarationCode "
//		 +
//		 " and c.baseCustomsDeclaration.effective = ? and  c.baseCustomsDeclaration.impExpType in (?) and ccc.inOutportFlat = ?  and ccc.cancelHead.id = ? and  c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  c.version = a.emsHeadH2kVersion.version ) as cc"
//		 +
//		 " from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
//		 List<Object> params = new ArrayList<Object>();
//		 params.add(new Boolean(true));
//		 params.add(4);// 成品出口
//		 params.add(5);// 转厂出口
//		 params.add(7);// 返工复出
//		 params.add("E");
//		 params.add(cancelHead.getId());
//		 params.add(new Boolean(true));
//		 params.add(2);// 退厂返工
//		 params.add("I");
//		 params.add(cancelHead.getId());
//		 params.add(head.getId());
//		 if (getIsEmsH2kSend()) {
//		 sql += " and a.sendState = ? ";
//		 params.add(Integer.valueOf(SendState.SEND));
//		 }
//		 sql +=
//		 " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
//		 List list = find(sql, params.toArray());
//		 System.out.println("hql="+sql);
//		 return list;
		
		
		System.out.println("EmsHeadH2k id = " + head.getId() + "isOwner="
				+ isOwner + "cancelHead=" + cancelHead.getId());
		String tablename = "";
		if (isOwner) {
			tablename = "CancelOwnerCustomsDeclara";
		} else {
			tablename = "CancelCusCustomsDeclara";
		}
		String sql = "select a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
				+ " a.unitWear as unitWear, a.wear as wear,"
				+ " sum(CASE WHEN  c.inOutportFlat = 'E' THEN b.commAmount ELSE -b.commAmount END ) "
				+ " from EmsHeadH2kBom a, CustomsDeclarationCommInfo b,"
				+ tablename
				+ " c "
				+ " where b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  "
				+ " and b.version = a.emsHeadH2kVersion.version "
				+ " and c.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and b.baseCustomsDeclaration.effective = ? and  b.baseCustomsDeclaration.impExpType in (?,?,?,?) "
				+ " and c.cancelHead.id = ?  "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(new Boolean(true));
		params.add(4);// 成品出口
		params.add(5);// 转厂出口
		params.add(7);// 返工复出
		params.add(2);// 退厂返工
		params.add(cancelHead.getId());
		params.add(head.getId());
		if (getIsEmsH2kSend()) {
			sql += " and a.sendState = ? ";
			params.add(Integer.valueOf(SendState.SEND));
		}
		sql += " group by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum ,a.emsHeadH2kVersion.version,a.seqNum,a.unitWear, a.wear";
		List list = find(sql, params.toArray());
		System.out.println("hql=" + sql);
		return list;
	}

	/**
	 * 显示归并前成品的备案序号
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEmsEdiMergerExgBeforeByPtNo(String ptNo) {
		if (ptNo == null) {
			return new ArrayList();
		}
		String hsql = " select a.seqNum  from  EmsEdiMergerExgBefore a  "
				+ "where a.company.id =?  and a.ptNo=?  ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				ptNo });
	}

	/**
	 * 显示成品折算料件
	 * 
	 * @return
	 */
	public List findFactoryStoryProduct() {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a  from  FactoryStoryProduct  a  "
				+ "where a.company.id =?  ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });

	}

	/**
	 * 显示成品折算料件
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findFactoryStoryProductByPtNo(String ptNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a  from  FactoryStoryProduct  a  "
				+ "where a.company.id =?  and a.ptNo= ? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				ptNo });

	}

	/**
	 * 查询电子帐册成品
	 * 
	 * @param seqNum
	 *            备案序号
	 * @return
	 */
	public List findEmsHeadH2kExgBySeqNum(Integer seqNum) {
		if (seqNum == null || (!(seqNum instanceof Integer))) {
			return new ArrayList();
		}
		String hsql = "select a from EmsHeadH2kExg a "
				+ "where  a.seqNum=? and a.company.id=? "
				+ "and a.emsHeadH2k.declareState=?";
		return this.find(hsql, new Object[] { seqNum,
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE });
	}

	/**
	 * 
	 * 获取电子帐册版本号
	 * 
	 * @param emsHeadH2kExg
	 *            电子帐册成品id
	 * @return
	 */
	public List findEmsHeadH2kVersion(EmsHeadH2kExg emsHeadH2kExg) {
		if (emsHeadH2kExg == null
				|| (!(emsHeadH2kExg instanceof EmsHeadH2kExg))) {
			return new ArrayList();
		}
		return this.find(
				"select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id=?"
						+ " and a.company.id= ?",
				new Object[] { emsHeadH2kExg.getId(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 
	 * 获取电子帐册成品BOM
	 * 
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsHeadH2kBom(EmsHeadH2kVersion version) {
		if (version == null || (!(version instanceof EmsHeadH2kVersion))) {
			return new ArrayList();
		}
		return this.find(
				"select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id=?"
						+ " and a.company.id= ? order by a.seqNum",
				new Object[] { version.getId(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 获取报关单明细数量
	 * 
	 * @param head
	 *            帐册id
	 * @param isOwner
	 * @param cancelHead
	 *            核销id
	 * @return
	 */
	public List finCpUseNum(EmsHeadH2k head, boolean isOwner,
			CancelHead cancelHead) {
		String tablename = "";
		if (isOwner) {
			tablename = "CancelOwnerCustomsDeclara";
		} else {
			tablename = "CancelCusCustomsDeclara";
		}
		String sql = "select distinct a.seqNum as cpNum," +
		// --
				" (select sum(b.commAmount) from CustomsDeclarationCommInfo b,"
				+ tablename
				+ " bbb "
				+ " where bbb.customNo = b.baseCustomsDeclaration.customsDeclarationCode "
				+ " and b.baseCustomsDeclaration.effective = ? and  b.baseCustomsDeclaration.impExpType in (?,?,?) and bbb.inOutportFlat = ?  and  bbb.cancelHead.id = ? and  b.commSerialNo = a.seqNum) as aa, "
				// --
				+ " (select sum(c.commAmount) from CustomsDeclarationCommInfo c,"
				+ tablename
				+ " ccc "
				+ " where ccc.customNo = c.baseCustomsDeclaration.customsDeclarationCode "
				+ " and c.baseCustomsDeclaration.effective = ? and  c.baseCustomsDeclaration.impExpType in (?) and ccc.inOutportFlat = ?  and  ccc.cancelHead.id = ? and c.commSerialNo = a.seqNum) as cc,"
				+
				// --
				" (select sum(d.netWeight) from CustomsDeclarationCommInfo d,"
				+ tablename
				+ " ddd "
				+ " where ddd.customNo = d.baseCustomsDeclaration.customsDeclarationCode "
				+ " and d.baseCustomsDeclaration.effective = ? and  d.baseCustomsDeclaration.impExpType in (?,?,?,?) and (ddd.inOutportFlat = ? or ddd.inOutportFlat = ?)  and ddd.cancelHead.id = ? and  d.commSerialNo = a.seqNum) as dd,"
				+
				// --
				" (select sum(e.dollarTotalPrice) from CustomsDeclarationCommInfo e,"
				+ tablename
				+ " eee "
				+ " where eee.customNo = e.baseCustomsDeclaration.customsDeclarationCode "
				+ " and e.baseCustomsDeclaration.effective = ? and  e.baseCustomsDeclaration.impExpType in (?,?,?) and eee.inOutportFlat = ?  and eee.cancelHead.id = ? and  e.commSerialNo = a.seqNum) as ee, "
				// --
				+ " (select sum(f.dollarTotalPrice) from CustomsDeclarationCommInfo f,"
				+ tablename
				+ " fff "
				+ " where fff.customNo = f.baseCustomsDeclaration.customsDeclarationCode "
				+ " and f.baseCustomsDeclaration.effective = ? and  f.baseCustomsDeclaration.impExpType in (?) and fff.inOutportFlat = ?  and fff.cancelHead.id = ? and  f.commSerialNo = a.seqNum) as ff "
				+ " from EmsHeadH2kExg a where a.emsHeadH2k.id = ? ";

		List<Object> params = new ArrayList<Object>();
		params.add(new Boolean(true));
		params.add(ImpExpType.DIRECT_EXPORT); // 成品出口
		params.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
		params.add(ImpExpType.REWORK_EXPORT); // 返工复出
		params.add("E");
		params.add(cancelHead.getId());
		// ---
		params.add(new Boolean(true));
		params.add(ImpExpType.BACK_FACTORY_REWORK); // 退厂返工
		params.add("I");
		params.add(cancelHead.getId());
		// ---
		params.add(new Boolean(true));
		params.add(ImpExpType.DIRECT_EXPORT);
		params.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		params.add(ImpExpType.REWORK_EXPORT);
		params.add(ImpExpType.BACK_FACTORY_REWORK);
		params.add("E");
		params.add("I");
		params.add(cancelHead.getId());
		// ---
		params.add(new Boolean(true));
		params.add(ImpExpType.DIRECT_EXPORT);
		params.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		params.add(ImpExpType.REWORK_EXPORT);
		params.add("E");
		params.add(cancelHead.getId());
		params.add(new Boolean(true));
		params.add(ImpExpType.BACK_FACTORY_REWORK);
		params.add("I");
		params.add(cancelHead.getId());
		params.add(head.getId());
		if (getIsEmsH2kSend()) {
			sql += " and a.sendState = ? ";
			params.add(Integer.valueOf(SendState.SEND));
		}
		sql += " order by a.seqNum";
		System.out.println("====================================" + sql);
		List list = find(sql, params.toArray());
		return list;
	}

	/**
	 * 显示成品BOM表
	 * 
	 * @param ptNo
	 *            料号
	 * @param version
	 *            版本号
	 * @return
	 */
	public List findEmsEdiMergerBomByCpPtNo(String ptNo, String version) {
		if (getIsMergerSend()) {
			return this
					.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo = ? and a.emsEdiMergerVersion.version = ? and "
							+ " a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState=? and a.sendState = ?",
							new Object[] { ptNo, Integer.valueOf(version),
									new Boolean(false),
									Integer.valueOf(SendState.SEND) });
		} else {
			return this
					.find("select a from EmsEdiMergerExgBom a where a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo = ? and a.emsEdiMergerVersion.version = ? and "
							+ " a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState=?  and "
							+ " a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState=?",
							new Object[] { ptNo, Integer.valueOf(version),
									new Boolean(false),
									DeclareState.PROCESS_EXE });
		}
	}

	/**
	 * 显示电子帐册参数设置
	 * 
	 * @return 电子帐册归并关系是否分批发送
	 */
	private boolean getIsMergerSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsSEND,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取滚动核销－－帐帐分析－－差异分析
	 * 
	 * @param head
	 * @return 所有数据
	 */
	public List findCheckBgCy(CheckParameter head) {
		return this
				.find("select a from CheckBgCy a where a.head.id = ? and a.company.id = ?",
						new Object[] { head.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存滚动核销－－帐帐分析－－差异分析
	 * 
	 * @param obj
	 */
	public void saveCheckBgCy(CheckBgCy obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 获取电子帐册中的耗用
	 * 
	 * @param head
	 *            核销id
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffect
	 *            是否生效
	 * @param isDeclaration
	 * @return
	 */
	public List finLjUseNum(EmsHeadH2k head, Date beginDate, Date endDate,
			Boolean isEffect, boolean isDeclaration) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
				+ " a.unitWear as unitWear, a.wear as wear,"
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.impExpType in (?,?,?)  ";
		parameters.add(4);
		parameters.add(5);
		parameters.add(7);
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		hsql += " and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  b.version = a.emsHeadH2kVersion.version ) as aa,"
				+

				" (select sum(c.commAmount) from CustomsDeclarationCommInfo c "
				+ " where c.baseCustomsDeclaration.impExpType in (?)  ";
		parameters.add(2);
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (c.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null && isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " and  c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  c.version = a.emsHeadH2kVersion.version ) as cc "
				+ " from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
		parameters.add(head.getId());
		if (getIsEmsH2kSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		List list = find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 保存核销中的单耗表头
	 * 
	 * @param obj
	 */
	public void saveCancelUnitWear(CancelUnitWear obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 回写报关单上是否核销栏位
	 * 
	 * @param cancelHead
	 *            报核id
	 * @return
	 */
	public List findmakeCustomsDeclarationisEmsCav(CancelHead cancelHead) {
		String hsql = "select a from CustomsDeclaration a where a.company.id = ? and a.effective = ? and a.customsDeclarationCode "
				+ "  in (select c.customNo from CancelCusCustomsDeclara c where c.company.id = ? and c.cancelHead.id=?)";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				true, CommonUtils.getCompany().getId(), cancelHead.getId() });
	}

	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */
	public List findEmsHeadH2k() {

		return this
				.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=? order by a.modifyTimes DESC",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false) });
	}

}