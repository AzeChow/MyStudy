package com.bestway.fixasset.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.AgreementGroupState;
import com.bestway.common.constant.AgreementState;
import com.bestway.common.constant.FixType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.fixasset.entity.AgreementGroupDetail;
import com.bestway.fixasset.entity.AgreementGroupHead;
import com.bestway.fixasset.entity.AgreementInvoiceDetail;
import com.bestway.fixasset.entity.AgreementInvoiceHead;
import com.bestway.fixasset.entity.AgreementWarDetail;
import com.bestway.fixasset.entity.AgreementWarHead;
import com.bestway.fixasset.entity.DeleteAgreementCommInfo;
import com.bestway.fixasset.entity.DutyCertDetail;
import com.bestway.fixasset.entity.DutyCertHead;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixasset.entity.ImpAgreementCommInfo;

public class FixAssetDao extends BaseDao {
	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	public List findAgreement() {
		return this.find("select a from Agreement a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	public Agreement findAgreement(Agreement agreement) {
		return (Agreement) this.get(Agreement.class, agreement.getId());
	}

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	public List findAgreementExcuting() {
		return this.find("select a from Agreement a where a.company.id= ? "
				+ " and a.declareState=? ", new Object[] {
				CommonUtils.getCompany().getId(), AgreementState.EXECUTING });
	}

	/**
	 * 保存批文协议
	 * 
	 * @param agreement
	 */
	public void saveAgreement(Agreement agreement) {
		this.saveOrUpdate(agreement);
	}

	/**
	 * 删除批文协议
	 * 
	 * @param agreement
	 */
	public void deleteAgreement(Agreement agreement) {
		this.delete(agreement);
	}
	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementCommInfo() {
		return this.find(
				"select a from AgreementCommInfo a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}
	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementCommInfo(Agreement agreement) {
		return this.find(
				"select a from AgreementCommInfo a where a.agreement.id= ? ",
				new Object[] { agreement.getId() });
	}

	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementCommInfo(String agreementNo) {
		return this.find(
				"select a from AgreementCommInfo a where a.company.id= ? "
						+ " and a.agreement.sancEmsNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), agreementNo });
	}

	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public AgreementCommInfo findAgreementCommInfoByNo(Agreement agreement,
			Integer mainNo) {
		List list = this.find(
				"select a from AgreementCommInfo a where a.agreement.id= ? "
						+ " and a.mainNo=? ", new Object[] { agreement.getId(),
						mainNo });
		if (list.size() > 0) {
			return (AgreementCommInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 抓取已经存在的设备明细信息
	 * 
	 * @return
	 */
	public List findExistFixassetCommInfo(Agreement agreement) {
		return this.find("select distinct a.mainNo"
				+ " from AgreementCommInfo a where a.company.id= ? "
				+ " and a.agreement.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), agreement.getId() });
	}
	
	/**
	 * 抓取已经存在的设备明细信息
	 * 
	 * @return
	 */
	public List findExistFixassetImpCommInfo(Agreement agreement) {
		return this.find("select distinct a.mainNo"
				+ " from ImpAgreementCommInfo a where a.company.id= ? "
				+ " and a.agreement.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), agreement.getId() });
	}

	/**
	 * 查询批文协议设备明细最大序号
	 * 
	 * @param agreement
	 * @return
	 */
	public Integer findMaxAgreementCommInfoNo(Agreement agreement) {
		List list = this.find("select max(a.mainNo) from AgreementCommInfo a "
				+ " where a.agreement.id= ? ",
				new Object[] { agreement.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 查询批文协议设备明细最大序号
	 * 
	 * @param agreement
	 * @return
	 */
	public Integer findMaxImpAgreementCommInfoNo(Agreement agreement) {
		List list = this.find(
				"select max(a.mainNo) from ImpAgreementCommInfo a "
						+ " where a.agreement.id= ? ", new Object[] { agreement
						.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 保存批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void saveAgreementCommInfo(AgreementCommInfo commInfo) {
		this.saveOrUpdate(commInfo);
	}

	/**
	 * 删除批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementCommInfo(AgreementCommInfo commInfo) {
		this.delete(commInfo);
	}

	/**
	 * 查询新增的批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findImpAgreementCommInfo(Agreement agreement) {
		return this
				.find(
						"select a from ImpAgreementCommInfo a where a.agreement.id= ? ",
						new Object[] { agreement.getId() });
	}

	/**
	 * 保存新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void saveImpAgreementCommInfo(ImpAgreementCommInfo commInfo) {
		this.saveOrUpdate(commInfo);
	}

	/**
	 * 删除新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void deleteImpAgreementCommInfo(ImpAgreementCommInfo commInfo) {
		this.delete(commInfo);
	}

	/**
	 * 查询取消的批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDeleteAgreementCommInfo(Agreement agreement) {
		return this
				.find(
						"select a from DeleteAgreementCommInfo a where a.agreement.id= ? ",
						new Object[] { agreement.getId() });
	}

	/**
	 * 保存取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void saveDeleteAgreementCommInfo(DeleteAgreementCommInfo commInfo) {
		this.saveOrUpdate(commInfo);
	}

	/**
	 * 删除取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void deleteDeleteAgreementCommInfo(DeleteAgreementCommInfo commInfo) {
		this.delete(commInfo);
	}

	/**
	 * 查询取消备案的设备的金额
	 * 
	 * @param agreement
	 * @return
	 */
	public Double findDeleteAgreementCommInfoMoney(Agreement agreement) {
		List list = this
				.find(
						"select sum(a.totalPrice) from DeleteAgreementCommInfo a where a.agreement.id= ? ",
						new Object[] { agreement.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查询新增备案的设备的金额
	 * 
	 * @param agreement
	 * @return
	 */
	public Double findAddAgreementCommInfoMoney(Agreement agreement) {
		List list = this
				.find(
						"select sum(a.totalPrice) from ImpAgreementCommInfo a "
								+ " where a.agreement.id= ? and a.mainNo not in "
								+ " (select b.mainNo from AgreementCommInfo b where b.agreement.id= ?)",
						new Object[] { agreement.getId(), agreement.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupHead(Agreement agreement) {
		return this.find(
				"select a from AgreementGroupHead a where a.agreement.id= ? ",
				new Object[] { agreement.getId() });
	}

	/**
	 * 查询中间数据
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDeleteAgreementCommInfoHistory(Agreement agreement,
			Integer changedTimes) {
		return this.find(
				"select a from DeleteAgreementCommInfoHistory a where a.agreement.id= ? "
						+ " and a.changedTimes=? ", new Object[] {
						agreement.getId(), changedTimes });
	}

	/**
	 * 查询中间数据
	 * 
	 * @param agreement
	 * @return
	 */
	public List findImpAgreementCommInfoHistory(Agreement agreement,
			Integer changedTimes) {
		return this.find(
				"select a from ImpAgreementCommInfoHistory a where a.agreement.id= ? "
						+ " and a.changedTimes=? ", new Object[] {
						agreement.getId(), changedTimes });
	}

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupHead(Agreement agreement, Integer changedTimes) {
		return this.find(
				"select a from AgreementGroupHead a where a.agreement.id= ? "
						+ " and a.changedTimes=? ", new Object[] {
						agreement.getId(), changedTimes });
	}

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupDetail(String agreementNo, Date beginDate,
			Date endDate) {
		String hql = "select a from AgreementGroupDetail a where a.groupHead.company.id=?"
				+ " and a.groupHead.agreement.sancEmsNo= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(agreementNo);
		if (beginDate != null) {
			hql += " and a.groupHead.createDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.groupHead.createDate<=? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupHeadByState(Agreement agreement, Integer state) {
		return this.find(
				"select a from AgreementGroupHead a where a.agreement.id= ? ",
				new Object[] { agreement.getId() });
	}

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupHeadByState(Agreement agreement, int state) {
		return this.find(
				"select a from AgreementGroupHead a where a.agreement.id= ? "
						+ " and a.stateMark=? ", new Object[] {
						agreement.getId(), state });
	}

	/**
	 * 保存批文协议设备分组表头
	 * 
	 * @param commInfo
	 */
	public void saveAgreementGroupHead(AgreementGroupHead groupHead) {
		this.saveOrUpdate(groupHead);
	}

	/**
	 * 删除批文协议设备分组表头
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementGroupHead(AgreementGroupHead groupHead) {
		this.delete(groupHead);
	}

	/**
	 * 查询批文协议设备明细分组
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupDetail(AgreementGroupHead groupHead) {
		return this
				.find(
						"select a from AgreementGroupDetail a where a.groupHead.id= ? ",
						new Object[] { groupHead.getId() });
	}

	/**
	 * 查询批文协议设备明细分组
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupDetailNotHandIn(Agreement agreement) {
		return this.find(
				"select a from AgreementGroupDetail a where a.groupHead.agreement.id= ? "
						+ " and a.groupHead.stateMark=? ", new Object[] {
						agreement.getId(), AgreementGroupState.RECEIVE });
	}

	/**
	 * 保存批文协议设备明细分组
	 * 
	 * @param commInfo
	 */
	public void saveAgreementGroupDetail(AgreementGroupDetail groupDetail) {
		this.saveOrUpdate(groupDetail);
	}

	/**
	 * 删除批文协议设备明细分组
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementGroupDetail(AgreementGroupDetail groupDetail) {
		this.delete(groupDetail);
	}

	/**
	 * 根据主序号查询处于"收件"状态的集结物品项数
	 * 
	 * @param agreement
	 * @param mainNo
	 * @return
	 */
	public int findAgreementGroupCountNoHandIn(Agreement agreement,
			Integer mainNo) {
		List list = this
				.find(
						"select count(a.id) from AgreementGroupDetail a where a.groupHead.agreement.id= ? "
								+ " and a.mainNo=? and a.groupHead.stateMark=? ",
						new Object[] { agreement.getId(), mainNo,
								AgreementGroupState.RECEIVE });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询批文协议设备发票表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementInvoiceHead(String sancEmsNo) {
		return this.find(
				"select a from AgreementInvoiceHead a where a.company.id= ? "
						+ " and a.sancEmsNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), sancEmsNo });
	}

	/**
	 * 保存批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	public void saveAgreementInvoiceHead(AgreementInvoiceHead invoiceHead) {
		this.saveOrUpdate(invoiceHead);
	}

	/**
	 * 删除批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementInvoiceHead(AgreementInvoiceHead invoiceHead) {
		this.delete(invoiceHead);
	}

	/**
	 * 查询批文协议设备发票明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementInvoiceDetail(AgreementInvoiceHead invoiceHead) {
		return this
				.find(
						"select a from AgreementInvoiceDetail a where a.invoiceHead.id= ? ",
						new Object[] { invoiceHead.getId() });
	}

	/**
	 * 保存批文协议设备发票明细
	 * 
	 * @param commInfo
	 */
	public void saveAgreementInvoiceDetail(AgreementInvoiceDetail invoiceDetail) {
		this.saveOrUpdate(invoiceDetail);
	}

	/**
	 * 删除批文协议设备发票明细
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementInvoiceDetail(
			AgreementInvoiceDetail invoiceDetail) {
		this.delete(invoiceDetail);
	}

	/**
	 * 查询批文协议设备保证书表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementWarHead(String sancEmsNo) {
		return this.find(
				"select a from AgreementWarHead a where a.company.id= ? "
						+ " and a.sancEmsNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), sancEmsNo });
	}

	/**
	 * 保存批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	public void saveAgreementWarHead(AgreementWarHead warHead) {
		this.saveOrUpdate(warHead);
	}

	/**
	 * 删除批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementWarHead(AgreementWarHead warHead) {
		this.delete(warHead);
	}

	/**
	 * 查询批文协议设备保证书明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementWarDetail(AgreementWarHead warHead) {
		return this.find(
				"select a from AgreementWarDetail a where a.warHead.id= ? ",
				new Object[] { warHead.getId() });
	}

	/**
	 * 保存批文协议设备保证书明细
	 * 
	 * @param commInfo
	 */
	public void saveAgreementWarDetail(AgreementWarDetail warDetail) {
		this.saveOrUpdate(warDetail);
	}

	/**
	 * 删除批文协议设备保证书明细
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementWarDetail(AgreementWarDetail warDetail) {
		this.delete(warDetail);
	}

	/**
	 * 查询批文协议设备保证书表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertHead(String sancEmsNo) {
		return this.find("select a from DutyCertHead a where a.company.id= ? "
				+ " and a.sancEmsNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), sancEmsNo });
	}

	/**
	 * 保存批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	public void saveDutyCertHead(DutyCertHead certHead) {
		this.saveOrUpdate(certHead);
	}

	/**
	 * 删除批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	public void deleteDutyCertHead(DutyCertHead certHead) {
		this.delete(certHead);
	}

	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertDetail(DutyCertHead certHead) {
		return this.find(
				"select a from DutyCertDetail a where a.certHead.id= ? ",
				new Object[] { certHead.getId() });
	}

	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertDetailByCertNo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String customsDeclaration = "CustomsDeclarationCommInfo";
		if (baseCustomsDeclaration instanceof CustomsDeclaration) {
			customsDeclaration = "CustomsDeclarationCommInfo";
		} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
			customsDeclaration = "BcsCustomsDeclarationCommInfo";
		} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
			customsDeclaration = "DzscCustomsDeclarationCommInfo";
		}
		return this.find("select a from DutyCertDetail a where "
				+ " a.company.id=? and a.certHead.certNo= ? "
				+ " and a.mainNo not in (select b.commSerialNo from "
				+ customsDeclaration
				+ " b where b.baseCustomsDeclaration.id=? " 
				+ " and b.baseCustomsDeclaration.fixType=? " 
				+" )", new Object[] {
				CommonUtils.getCompany().getId(),
				baseCustomsDeclaration.getEmsHeadH2k(),
				baseCustomsDeclaration.getId() ,FixType.SHANZHI});
	}

	/**
	 * 保存批文协议设备征免税证明细
	 * 
	 * @param commInfo
	 */
	public void saveDutyCertDetail(DutyCertDetail certDetail) {
		this.saveOrUpdate(certDetail);
	}

	/**
	 * 删除批文协议设备征免税证明细
	 * 
	 * @param commInfo
	 */
	public void deleteDutyCertDetail(DutyCertDetail certDetail) {
		this.delete(certDetail);
	}

	/**
	 * 抓取不在设备批文中的商品
	 * 
	 * @param agreement
	 * @return
	 */
	public List findComplexNotInAgreement(Agreement agreement) {
		return this.find("select a from Complex a where a.code not in "
				+ " (select b.complex.code from AgreementCommInfo b"
				+ " where b.agreement.id=? ) ", new Object[] { agreement
				.getId() });
	}

	/**
	 * 查询未集结的商品
	 * 
	 * @param agreement
	 * @return
	 */
	public List findImpCommInfoNotGroup(Agreement agreement) {
		return this.find("select a from ImpAgreementCommInfo a "
				+ "where a.agreement.id=? and a.allotAmout<a.amount ",
				new Object[] { agreement.getId() });
	}

	public AgreementGroupHead findAgreementGroupHeadByNo(Agreement agreement,
			String groupNo) {
		List list = this
				.find(
						"select a from AgreementGroupHead a "
								+ "where a.company.id=? and a.agreement.id=? and a.groupNo=?  ",
						new Object[] { CommonUtils.getCompany().getId(),
								agreement.getId(), groupNo });
		if (list.size() > 0) {
			return (AgreementGroupHead) list.get(0);
		}
		return null;
	}

	public AgreementGroupHead findAgreementGroupHeadByInvoiceNo(
			Agreement agreement, String invoiceNo) {
		List list = this
				.find(
						"select a from AgreementGroupHead a "
								+ "where a.company.id=? and a.agreement.id=? and a.invoiceNo=?  ",
						new Object[] { CommonUtils.getCompany().getId(),
								agreement.getId(), invoiceNo });
		if (list.size() > 0) {
			return (AgreementGroupHead) list.get(0);
		}
		return null;
	}

	public AgreementGroupHead findAgreementGroupHeadByWarNo(
			Agreement agreement, String warNo) {
		List list = this.find("select a from AgreementGroupHead a "
				+ "where a.company.id=? and a.agreement.id=? and a.warNo=?  ",
				new Object[] { CommonUtils.getCompany().getId(),
						agreement.getId(), warNo });
		if (list.size() > 0) {
			return (AgreementGroupHead) list.get(0);
		}
		return null;
	}

	public AgreementGroupHead findAgreementGroupHeadByCertNo(
			Agreement agreement, String certNo) {
		List list = this.find("select a from AgreementGroupHead a "
				+ "where a.company.id=? and a.agreement.id=? and a.certNo=?  ",
				new Object[] { CommonUtils.getCompany().getId(),
						agreement.getId(), certNo });
		if (list.size() > 0) {
			return (AgreementGroupHead) list.get(0);
		}
		return null;
	}

	public ImpAgreementCommInfo findImpAgreementCommInfoByNo(
			Agreement agreement, Integer mainNo) {
		List list = this.find("select a from ImpAgreementCommInfo a "
				+ "where a.company.id=? and a.agreement.id=? and a.mainNo=?  ",
				new Object[] { CommonUtils.getCompany().getId(),
						agreement.getId(), mainNo });
		if (list.size() > 0) {
			return (ImpAgreementCommInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 查询最大组编号
	 * 
	 * @return
	 */
	public String findAgreementGroupMaxGroupNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String preFix = format.format(new Date());
		List list = this
				.find("select max(a.invoiceNo) from AgreementGroupHead a "
						+ "where a.company.id=? and a.groupNo like ?  ",
						new Object[] { CommonUtils.getCompany().getId(),
								preFix + "%" });
		if (list.size() > 0 && list.get(0) != null) {
			String no = String.valueOf(Integer.parseInt(list.get(0).toString()
					.substring(8, 11)) + 1);
			int len = no.length();
			for (int i = 0; i < (3 - len); i++) {
				no = "0" + no;
			}
			return preFix + no;
		}
		return preFix + "001";
	}

	/**
	 * 查询最大发票编号
	 * 
	 * @return
	 */
	public String findAgreementGroupMaxInvoiceNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String preFix = format.format(new Date());
		List list = this
				.find("select max(a.invoiceNo) from AgreementGroupHead a "
						+ "where a.company.id=? and a.invoiceNo like ?  ",
						new Object[] { CommonUtils.getCompany().getId(),
								preFix + "%" });
		if (list.size() > 0 && list.get(0) != null) {
			String no = String.valueOf(Integer.parseInt(list.get(0).toString()
					.substring(8, 11)) + 1);
			int len = no.length();
			for (int i = 0; i < (3 - len); i++) {
				no = "0" + no;
			}
			return preFix + no;
		}
		return preFix + "001";
	}

	/**
	 * 查询最大变更次数
	 * 
	 * @return
	 */
	public Integer findAgreementGroupMaxChangedTimes(Agreement agreement) {
		List list = this.find(
				"select max(a.changedTimes) from AgreementGroupHead a "
						+ "where a.company.id=? and a.agreement.id=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						agreement.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString()) + 1;
		}
		return 1;
	}

	/**
	 * 根据发票号码查询发票
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public AgreementInvoiceHead findAgreementInvoiceHeadByNo(String scancEmsNo,
			String invoiceNo) {
		List list = this
				.find(
						"select a from AgreementInvoiceHead a "
								+ "where a.company.id=? and a.invoiceCode=?  and a.sancEmsNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								invoiceNo, scancEmsNo });
		if (list.size() > 0) {
			return (AgreementInvoiceHead) list.get(0);
		}
		return null;
	}

	/**
	 * 根据保证书码查询保证书
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public AgreementWarHead findAgreementWarHeadByNo(String scancEmsNo,
			String warNo) {
		List list = this.find("select a from AgreementWarHead a "
				+ "where a.company.id=? and a.warNo=? and a.sancEmsNo=? ",
				new Object[] { CommonUtils.getCompany().getId(), warNo,
						scancEmsNo });
		if (list.size() > 0) {
			return (AgreementWarHead) list.get(0);
		}
		return null;
	}

	/**
	 * 根据征免税号码查询征免税
	 * 
	 * @param invoiceNo
	 * @return
	 */
	public DutyCertHead findDutyCertHeadByNo(String scancEmsNo, String certNo) {
		List list = this.find("select a from DutyCertHead a "
				+ "where a.company.id=? and a.certNo=?  and a.sancEmsNo=? ",
				new Object[] { CommonUtils.getCompany().getId(), certNo,
						scancEmsNo });
		if (list.size() > 0) {
			return (DutyCertHead) list.get(0);
		}
		return null;
	}

	/**
	 * 抓取正在执行的征免税
	 * 
	 * @return
	 */
	public List findDutyCertHeadExecuting() {
		return this.find("select a from DutyCertHead a,Agreement b "
				+ "where a.sancEmsNo=b.sancEmsNo and a.company.id=? "
				+ " and b.company.id=? and b.declareState=?  ", new Object[] {
				CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId(), AgreementState.EXECUTING });
	}

	/**
	 * 查询设备报关单
	 * 
	 * @param customsDeclarationCommInfo
	 * @param impExpTypes
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclaration(String customsDeclarationCommInfo,
			Integer[] impExpTypes, String agreementNo, Date beginDate,
			Date endDate) {
		String hql = "select a from "
				+ customsDeclarationCommInfo
				+ " a,DutyCertHead b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.certNo "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.company.id=? and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.fixType=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(FixType.SHANZHI);
		if (agreementNo != null && !"".equals(agreementNo.trim())) {
			hql += " and b.sancEmsNo= ? ";
			parameters.add(agreementNo);
		}
		if (impExpTypes.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hql += " or a.baseCustomsDeclaration.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hql += " ) ";
		}
		if (beginDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询设备报关单
	 * 
	 * @param customsDeclarationCommInfo
	 * @param impExpTypes
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclaration(String customsDeclarationCommInfo,
			Integer[] impExpTypes) {
		String hql = "select a,b from " + customsDeclarationCommInfo
				+ " a,DutyCertHead b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.certNo "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.fixType=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(FixType.SHANZHI);
		if (impExpTypes.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hql += " or a.baseCustomsDeclaration.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hql += " ) ";
		}
		return this.find(hql, parameters.toArray());
	}
	/**
	 * 查询设备报关单
	 * 
	 * @param customsDeclarationCommInfo
	 * @param impExpTypes
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclaration(String customsDeclarationCommInfo,Integer[] impExpTypes,
			String certNo,Integer mainNo) {
		String hql = "select a from " + customsDeclarationCommInfo
				+ " a,DutyCertHead b "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=b.certNo "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " and b.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? " +
						" and b.certNo =? " +
						" and a.commSerialNo =? "
						+" and a.baseCustomsDeclaration.fixType =? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(certNo);
		parameters.add(mainNo);
		parameters.add(FixType.SHANZHI);
		if (impExpTypes.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == 0) {
					hql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hql += " or a.baseCustomsDeclaration.impExpType=? ";
				}
				parameters.add(impExpTypes[i]);
			}
			hql += " ) ";
		}
		return this.find(hql, parameters.toArray());
	}
	/**
	 * 存放位置表查询存放位置表
	 * 
	 * @return
	 */
	public List findFixassetLocation() {
		return this.find(
				"select a from FixassetLocation a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存存放位置表
	 * 
	 * @param location
	 */
	public void saveFixassetLocation(FixassetLocation location) {
		this.saveOrUpdate(location);
	}

	/**
	 * 删除存放位置表
	 * 
	 * @param location
	 */
	public void deleteFixassetLocation(FixassetLocation location) {
		this.delete(location);
	}

	/**
	 * 抓取已经存在的设备相关的报关单信息
	 * 
	 * @return
	 */
	public List findExistFixassetCustomsInfo() {
		List lsResult = new ArrayList();
		List list = this
				.find(
						"select distinct a.customsDeclarationCode,a.customsDeclaItemNo"
								+ " from FixassetLocationResultInfo a where a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			lsResult.add((objs[0] == null ? "" : objs[0].toString().trim())
					+ "-" + (objs[1] == null ? "" : objs[1].toString().trim()));
		}
		return lsResult;
	}

	/**
	 * 查询设备异动单据
	 * 
	 * @return
	 */
	public List findFixassetLocationChangeBillInfo() {
		return this
				.find(
						"select a from FixassetLocationChangeBillInfo a where a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixassetLocationResultInfo() {
		return this
				.find(
						"select a from FixassetLocationResultInfo a where a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixassetLocationResultInfo(String customsDeclarationCode,
			String itemNo) {
		return this
				.find(
						"select a from FixassetLocationResultInfo a where a.company.id= ? "
								+ " and a.customsDeclarationCode=? and a.customsDeclaItemNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								customsDeclarationCode, itemNo });
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixassetLocationResultInfoNotInTheLocation(
			FixassetLocation location) {
		return this.find(
				"select a from FixassetLocationResultInfo a where a.company.id= ? "
						+ " and a.location.id!=? ", new Object[] {
						CommonUtils.getCompany().getId(), location.getId() });
	}

	public List findComplex() {
		String hsql = "select c from Complex c left join fetch c.firstUnit b "
				+ " left join fetch c.secondUnit"
				+ " where (c.isOut <> '1' or c.isOut is null) ";
		return this.find(hsql);
	}

	public List findUnit() {
		String hsql = "select c from Unit c ";
		return this.find(hsql);
	}

	public List findCountry() {
		String hsql = "select c from Country c ";
		return this.find(hsql);
	}

	/**
	 * 查询设备金额
	 * 
	 * @param agreement
	 * @return
	 */
	public Double findAgreementFixassetMoney(Agreement agreement) {
		List list = this.find(
				"select sum(a.totalPrice) from AgreementCommInfo a where a.company.id= ? "
						+ " and a.agreement.id=? ", new Object[] {
						CommonUtils.getCompany().getId(), agreement.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.parseDouble(list.get(0).toString());
		}
		return 0.0;
	}
	
	/**
	 * 查找设备批文表体 来自 设备批文表头Id
	 * 
	 * @param parentId
	 *            设备批文表头Id
	 * @return List 是AgreementCommInfo型，设备批文表体
	 */
	public List findAgreementCommInfoByParentId(String parentId) {
		return this
				.find(
						"select a from AgreementCommInfo a where a.agreement.id = ? and a.company.id = ? "
								+ " order by a.mainNo ", new Object[] {
								parentId, CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param contractId
	 *            合同备案表头Id
	 * @param seqNum
	 *            料件序号
	 * @return List 是ContractImg型，合同料件
	 */
	public AgreementCommInfo findAgreementCommInfo(String contractId,
			String seqNum) {
		List list = this.find(
				"select a from AgreementCommInfo a where a.agreement.id = ? "
						+ " and a.mainNo=?", new Object[] { contractId,
						Integer.valueOf(seqNum) });
		if (list.size() <= 0) {
			return null;
		} else {
			return (AgreementCommInfo) list.get(0);
		}
	}
	
	/**
	 * 查询合同料件根据开始序号和结束序号
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @param beginSeqNum
	 *            开始序号
	 * @param endSeqNum
	 *            结束序号
	 * @return List 是ContractImg型，合同料件
	 */
	public List findAgreementCommInfoBeginAndEndSeqNum(String parentId,
			int beginSeqNum, int endSeqNum) {
		return this
				.find(
						"select c from AgreementCommInfo c where c.agreement.id=? "
								+ " and c.mainNo>=? and c.mainNo<=? order by c.mainNo ",
						new Object[] { parentId, beginSeqNum, endSeqNum });
	}


}
