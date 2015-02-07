package com.bestway.bcs.dictpor.dao;

import java.util.List;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;

/**
 * 公共基础查询类
 * 2010-07-07check by hcl
 * @author Administrator
 *
 */
public class BcsDictPorDao extends BaseDao {
	/**
	 * 获取当前公司
	 * 
	 * @return Company 当前公司，返回符合条件的第一条数据
	 * 
	 */
	public Company findCompany() {
		List list = this.find("select a from Company a where a.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		return (Company) list.get(0);
	}

	/**
	 * 获取className里最大的流水号
	 * 
	 * @param className
	 *            表名
	 * @param seqNum
	 *            流水号
	 * @return Sting className里最大的流水号
	 */
	public String getNum(String className, String seqNum) {
		String num = "1";
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a " + "where a.company= ?", new Object[] { CommonUtils
				.getCompany() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 获得最大的料件序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxDictPorImgSeqNum(BcsDictPorHead head) {
		List list = this
				.find("select max(a.seqNum) from BcsDictPorImg as a "
						+ " where a.company.id=? and a.dictPorHead.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得最大的成品序号来自当前备案资料库
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxDictPorExgSeqNum(BcsDictPorHead head) {
		List list = this
				.find("select max(a.seqNum) from BcsDictPorExg as a "
						+ " where a.company.id=? and a.dictPorHead.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	public List findBcsDictPorHead() {
		return this.find("select a from BcsDictPorHead a"
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 查询正在执行的备案资料库表头
	 * 
	 * @return
	 */
	public List findExingBcsDictPorHead() {
		return this.find("select a from BcsDictPorHead a"
				+ " where a.declareState=? and a.company.id=?", new Object[] {
				DeclareState.PROCESS_EXE, CommonUtils.getCompany().getId() });
	}
	/**
	 * 
	 * @param dictPorEmsNo  备案资料库编号
	 * @param state       审批状态
	 * @return
	 */
	public List findBcsDictPorHead(String dictPorEmsNo, String state) {
		return this.find("select a from BcsDictPorHead a"
				+ " where a.company.id=? "
				+ "and a.dictPorEmsNo=? and a.declareState=? ", new Object[] {
				CommonUtils.getCompany().getId(), dictPorEmsNo, state });
	}
	/**
	 * 返回备案资料库表头
	 * @param id  备案资料库表头id
	 * @return
	 */
	public BcsDictPorHead findBcsDictPorHeadById(String id) {
		return (BcsDictPorHead) this.load(BcsDictPorHead.class, id);
	}

	/**
	 * 根据备案资料库编号查询备案资料库
	 * 
	 * @param dictPorEmsNo  备案资料库编号
	 * @return LIST(备案资料库)
	 */
	public List findBcsDictPorHeadByEmsNo(String dictPorEmsNo) {
		return this
				.find("select a from BcsDictPorHead a"
						+ " where a.company.id=? " + "and a.dictPorEmsNo=?  ",
						new Object[] { CommonUtils.getCompany().getId(),
								dictPorEmsNo });
	}

	/**
	 * 根据备案资料库编号查询正在执行备案资料库
	 * 
	 * @param dictPorEmsNo  备案资料库编号
	 * @return 单个备案资料库
	 */
	public BcsDictPorHead findExingBcsDictPorHeadByEmsNo(String dictPorEmsNo) {
		List list = this.find("select a from BcsDictPorHead a"
				+ " where a.company.id=? "
				+ "and a.dictPorEmsNo=?  and a.declareState=? ", new Object[] {
				CommonUtils.getCompany().getId(), dictPorEmsNo,
				DeclareState.PROCESS_EXE });
		if (list != null && !list.isEmpty()) {
			return (BcsDictPorHead) list.get(0);
		}
		return null;
	}

	/**
	 * 根据内部编号查询备案资料库
	 * 
	 * @param dictPorEmsNo  备案资料库编号
	 * @return
	 */
	public List findBcsDictPorHeadByCopEmsNo(String copEmsNo) {
		return this.find("select a from BcsDictPorHead a"
				+ " where a.company.id=? " + "and a.copEmsNo=?  ",
				new Object[] { CommonUtils.getCompany().getId(), copEmsNo });
	}

	/**
	 * 查询备案资料库成品
	 * 
	 * @param head 备案资料库表头
	 * @return
	 */
	public List<BcsDictPorExg> findBcsDictPorExgByHead(BcsDictPorHead head) {
		return this.find("select a from BcsDictPorExg a where a.company.id=?"
				+ " and a.dictPorHead.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 查询备案资料库未修改成品
	 * 
	 * @param head 备案资料库表头
	 * @return
	 */
	public List findBcsDictPorExgStateChanged(BcsDictPorHead head) {
		return this.find("select a from BcsDictPorExg a where a.company.id=?"
				+ " and a.dictPorHead.id=? and a.modifyMark <> ? "
				+ " order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), head.getId(),
				ModifyMarkState.UNCHANGE });
	}

	/**
	 * 查询备案资料库料件
	 * 
	 * @param head 备案资料库表头
	 * @return
	 */
	public List<BcsDictPorImg> findBcsDictPorImgByHead(BcsDictPorHead head) {
		return this.find("select a from BcsDictPorImg a where a.company.id=?"
				+ " and a.dictPorHead.id=? order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 查询备案资料库除未修改过的料件
	 * 
	 * @param head备案资料库表头
	 * @return
	 */
	public List findBcsDictPorImgStateChanged(BcsDictPorHead head) {
		return this.find("select a from BcsDictPorImg a where a.company.id=?"
				+ " and a.dictPorHead.id=? and a.modifyMark <> ?"
				+ " order by a.seqNum", new Object[] {
				CommonUtils.getCompany().getId(), head.getId(),
				ModifyMarkState.UNCHANGE });
	}

	/**
	 * 查询备案资料库成品序号
	 * 
	 * @param head 备案资料库表头
	 * @return
	 */
	public List findBcsDictPorExgSeqNumByHead(BcsDictPorHead head) {
		return this.find("select a.innerMergeSeqNum from BcsDictPorExg a "
				+ " where a.company.id=? and a.dictPorHead.id=?", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 查询备案资料库料件序号
	 * 
	 * @param head 备案资料库表头
	 * @return
	 */
	public List findBcsDictPorImgSeqNumByHead(BcsDictPorHead head) {
		return this.find("select a.innerMergeSeqNum from BcsDictPorImg a "
				+ " where a.company.id=? and a.dictPorHead.id=?", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 查询备案资料库料件或者成品个数
	 * 
	 * @param head 备案资料库表头
	 * @param isImg 是否料件
	 * @return
	 */
	public int findBcsDictPorImgExgCountByHead(BcsDictPorHead head,
			boolean isImg) {
		String tableName = isImg ? "BcsDictPorImg" : "BcsDictPorExg";
		List list = this.find("select count(a.id) from " + tableName + " a "
				+ " where a.company.id=? and a.dictPorHead.id=?", new Object[] {
				CommonUtils.getCompany().getId(), head.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据备案资料库料件查询合同和这些备案资料库料件相对应的合同料件个数
	 * 
	 * @param img   备案资料库料件
	 * @return
	 */
	public List findContractByDictPorImg(BcsDictPorImg img) {
		return this
				.find("select distinct a.contract.copEmsNo from ContractImg a "
						+ " where a.contract.company.id=? "
						+ " and a.contract.corrEmsNo=? and a.credenceNo=? "
						+ " order by a.contract.copEmsNo ",
						new Object[] { CommonUtils.getCompany().getId(),
								img.getDictPorHead().getDictPorEmsNo(),
								img.getSeqNum() });
	}

	/**
	 * 根据备案资料库料件查询合同和这些备案资料库料件相对应的合同料件个数
	 * 
	 * @param exg 备案资料库成品
	 * @return
	 */
	public List findContractByDictPorExg(BcsDictPorExg exg) {
		return this
				.find(
						"select distinct a.contract.copEmsNo from ContractExg a where a.contract.company.id=? "
								+ " and a.contract.corrEmsNo=? and a.credenceNo=? "
								+ " order by a.contract.copEmsNo ",
						new Object[] { CommonUtils.getCompany().getId(),
								exg.getDictPorHead().getDictPorEmsNo(),
								exg.getSeqNum() });
	}

	/**
	 * 根据修改状态查询备案资料库料件或成品
	 * 
	 * @param dictPorHead 备案资料库
	 * @param modifyMark 修改标志
	 * @param isImg 是否料件
	 * @return
	 */
	public List findBcsDictPorImgExgByModifyMark(BcsDictPorHead dictPorHead,
			String modifyMark, boolean isImg ) {
		String tableName = isImg ? "BcsDictPorImg" : "BcsDictPorExg";
		return this.find("select a from " + tableName
				+ " a where a.dictPorHead.id = ? "
				+ " and a.modifyMark=? order by a.seqNum ", new Object[] {
				dictPorHead.getId(), modifyMark });
	}

	/**
	 * 查询备案资料库的最大序号，除去新增状态的料件
	 * 
	 * @param dictPorHead 备案资料库
	 * @param isImg 是否料件
	 * @return
	 */
	public int findMaxBcsDictPorImgExgSeqNumExceptAdd(
			BcsDictPorHead dictPorHead, boolean isImg) {
		String tableName = isImg ? "BcsDictPorImg" : "BcsDictPorExg";
		List list = this.find("select max(a.seqNum) from " + tableName
				+ " a where a.dictPorHead.id = ? " + " and a.modifyMark<>? ",
				new Object[] { dictPorHead.getId(), ModifyMarkState.ADDED });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据备案资料库的编号查询备案手册
	 * 
	 * @param dictPorEmsNo 备案资料库编号
	 * @return
	 */
	public List findContractByDictPorEmsNo(String dictPorEmsNo) {
		return this.find("select a from Contract a where a.company.id=? "
				+ " and a.corrEmsNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), dictPorEmsNo });
	}

	/**
	 * 通过归并序号和备案资料库表头，查询备案资料库料件
	 * 
	 *  @param dictPorEmsNo 备案资料库表头
	 * @param seqNum 归并序号
	 * @return
	 */
	public List findBcsDictPorImgBycorrEmsNoAndtenSeqNum(String dictPorEmsNo,
			Integer seqNum) {
		return this.find("select a from BcsDictPorImg a where a.company.id=?"
				+ " and a.dictPorHead.dictPorEmsNo=? and a.innerMergeSeqNum=?",
				new Object[] { CommonUtils.getCompany().getId(), dictPorEmsNo,
						seqNum });
	}

	/**
	 * 通过备案序号和备案资料库表头，查询备案资料库料件的备案序号
	 * 
	 * @param dictPorEmsNo备案资料库表头
	 * @param seqNum
	 * @return
	 */
	public Integer findBcsDictPorImgBycorrEmsNoAndBeiAnNum(
			String dictPorEmsNo, Integer seqNum) {
		List list = this
				.find(
						"select a.seqNum from BcsDictPorImg a where a.company.id=?"
								+ " and a.dictPorHead.dictPorEmsNo=? and a.innerMergeSeqNum=?",
						new Object[] { CommonUtils.getCompany().getId(),
								dictPorEmsNo, seqNum });
		if (list.size() > 0 && list.get(0) != null) {
			Integer i = (Integer) (list.get(0));
			return i;
		}
		return null;
	}
	
	
	public List<BcsTenInnerMerge> findBcsTenInnerMerges() {
		return this.find(
				"select a from BcsTenInnerMerge a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}
}
