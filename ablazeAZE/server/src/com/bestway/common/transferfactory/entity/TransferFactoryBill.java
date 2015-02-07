package com.bestway.common.transferfactory.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 转厂进出货单
 */
public class TransferFactoryBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 *  单据号
	 */
	private String transferFactoryBillNo = null; 
	/**
	 * 从哪个结转单据拆分出来的
	 */
	private String fromTransFactBillNo=null;
	/**
	 * 关封号
	 */
	private String envelopNo = null; 
//	/**
//	 * 关封ID
//	 */
//	private String envelopId = null; 
	/**
	 * 仓库对象类
	 */
	private WareSet wareSet = null; 
	/**
	 * 生效
	 */
	private Boolean isAvailability = false;
	/**
	 * 进出口类型
	 */
	private Boolean isImpExpGoods = false;
	/**
	 * 生效日期
	 */
	private Date beginAvailability = null;
	/**
	 * 客户供应商
	 */
	private ScmCoc scmCoc = null;
	/**
	 * 商品项目个数
	 */
	private Integer itemCount = null;
	/**
	 * 已转报关清单的商品项数
	 */
	private Integer  makeBillListItemCount;
	/**
	 * 是否转报关清单
	 */
	private Boolean isApplyToCustomsBill = false;
	/**
	 * 是否关封申请单
	 */
	private Boolean isCustomsEnvelopRequestBill = false;
	/**
	 * 是否转报关单
	 */
	private Boolean isCustomsDeclaration = false;
	/**
	 * 录入人员对象
	 */
	private AclUser aclUser = null;
	/**
	 * 生成的报关清单号码
	 */
	private String makeCustomsBillListCode;
	/**
	 * 生成的报关单号码
	 */
	private String makeCustomsDeclarationCode;
	/**
	 * 备注
	 */
	private String memo = null;
	/**
	 * 是否选中
	 */
	private Boolean isSelected =false;
	
	/**
	 * 临时使用字段
	 */
	 private String tempNo;

	/**
	 * @return Returns the aclUser.
	 */
	public AclUser getAclUser() {
		return aclUser;
	}

	/**
	 * @param aclUser
	 *            The aclUser to set.
	 */
	public void setAclUser(AclUser aclUser) {
		this.aclUser = aclUser;
	}

	/**
	 * @return Returns the isApplyToCustomsBill.
	 */
	public Boolean getIsApplyToCustomsBill() {
		return isApplyToCustomsBill;
	}

	/**
	 * @param isApplyToCustomsBill
	 *            The isApplyToCustomsBill to set.
	 */
	public void setIsApplyToCustomsBill(Boolean isApplyToCustomsBill) {
		this.isApplyToCustomsBill = isApplyToCustomsBill;
	}

	/**
	 * @return Returns the isCustomsEnvelopRequestBill.
	 */
	public Boolean getIsCustomsEnvelopRequestBill() {
		return isCustomsEnvelopRequestBill;
	}

	/**
	 * @param isCustomsEnvelopRequestBill
	 *            The isCustomsEnvelopRequestBill to set.
	 */
	public void setIsCustomsEnvelopRequestBill(
			Boolean isCustomsEnvelopRequestBill) {
		this.isCustomsEnvelopRequestBill = isCustomsEnvelopRequestBill;
	}

	/**
	 * @return Returns the itemCount.
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount
	 *            The itemCount to set.
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return Returns the wareSet.
	 */
	public WareSet getWareSet() {
		return wareSet;
	}

	/**
	 * @param wareSet
	 *            The wareSet to set.
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	/**
	 * @return Returns the beginAvailability.
	 */
	public Date getBeginAvailability() {
		if (beginAvailability == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(beginAvailability));
	}

	/**
	 * @param beginAvailability
	 *            The beginAvailability to set.
	 */
	public void setBeginAvailability(Date beginAvailability) {
		this.beginAvailability = beginAvailability;
	}

	/**
	 * @return Returns the isAvailability.
	 */
	public Boolean getIsAvailability() {
		return isAvailability;
	}

	/**
	 * @param isAvailability
	 *            The isAvailability to set.
	 */
	public void setIsAvailability(Boolean isAvailability) {
		this.isAvailability = isAvailability;
	}

	/**
	 * @return Returns the memo.
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            The memo to set.
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 客户供应商
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * 客户供应商
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * @return Returns the isImpExpGoods.
	 */
	public Boolean getIsImpExpGoods() {
		return isImpExpGoods;
	}

	/**
	 * @param isImpExpGoods
	 *            The isImpExpGoods to set.
	 */
	public void setIsImpExpGoods(Boolean isImpExpGoods) {
		this.isImpExpGoods = isImpExpGoods;
	}

	/**
	 * @return Returns the transferFactoryBillNo.
	 */
	public String getTransferFactoryBillNo() {
		return transferFactoryBillNo;
	}

	/**
	 * @param transferFactoryBillNo
	 *            The transferFactoryBillNo to set.
	 */
	public void setTransferFactoryBillNo(String transferFactoryBillNo) {
		this.transferFactoryBillNo = transferFactoryBillNo;
	}

	/**关封号
	 * @return Returns the envelopNo.
	 */
	public String getEnvelopNo() {
		return envelopNo;
	}

	/**关封号
	 * @param envelopNo
	 *            The envelopNo to set.
	 */
	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}
//
//	public String getEnvelopId() {
//		return envelopId;
//	}
//
//	public void setEnvelopId(String envelopId) {
//		this.envelopId = envelopId;
//	}

	public Boolean getIsCustomsDeclaration() {
		return isCustomsDeclaration;
	}

	public void setIsCustomsDeclaration(Boolean isCustomsDeclaration) {
		this.isCustomsDeclaration = isCustomsDeclaration;
	}

	public String getFromTransFactBillNo() {
		return fromTransFactBillNo;
	}

	public void setFromTransFactBillNo(String fromTransFactBillNo) {
		this.fromTransFactBillNo = fromTransFactBillNo;
	}

	public Integer getMakeBillListItemCount() {
		return makeBillListItemCount;
	}

	public void setMakeBillListItemCount(Integer makeBillListItemCount) {
		this.makeBillListItemCount = makeBillListItemCount;
	}

	public String getMakeCustomsBillListCode() {
		return makeCustomsBillListCode;
	}

	public void setMakeCustomsBillListCode(String makeCustomsBillListCode) {
		this.makeCustomsBillListCode = makeCustomsBillListCode;
	}

	public String getMakeCustomsDeclarationCode() {
		return makeCustomsDeclarationCode;
	}

	public void setMakeCustomsDeclarationCode(String makeCustomsDeclarationCode) {
		this.makeCustomsDeclarationCode = makeCustomsDeclarationCode;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelect) {
		this.isSelected = isSelect;
	}

	public String getTempNo() {
		return tempNo;
	}

	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
}