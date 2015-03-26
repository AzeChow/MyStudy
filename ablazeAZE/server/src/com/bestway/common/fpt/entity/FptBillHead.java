package com.bestway.common.fpt.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 转厂进出货单
 */
public class FptBillHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 序号
	 */
	private Integer serialNumber;

	/** ------------------------------新增字段----------------------------------* */
	/**
	 * 项目类型 BCUS = 0; 电子帐册 BCS = 1; 电子化手册 DZSC = 3; 电子手册
	 */
	private Integer projectType = null;

	/**
	 * 单据类型标志 收发货单 2 退货单 3
	 */
	private String sysType = null;;

	/**
	 * 进出口标志中的进口标志 0 出口1 进口
	 * 
	 * 备注 :发货与收退货是0 ;收货与发退货是1
	 */
	private String billSort = null;

	/**
	 * 收发货单编号
	 */
	private String billNo = null;

	/**
	 * 电子口岸统一编号
	 */
	private String seqNo = null;

	/**
	 * 申请表编号
	 */
	private String appNo = null;

	/**
	 * 转出企业手册/账册号
	 */
	private String outEmsNo = null;

	/**
	 * 发货企业内部编号
	 */
	private String issueCopBillNo = null;

	/**
	 * 收货企业内部编号
	 */
	private String receiveCopBillNo = null;

	/**
	 * 发货企业编码
	 */
	private String issueTradeCod = null;

	/**
	 * 收货企业编码
	 */
	private String receiveTradeCod = null;;

	/**
	 * 发货企业名称
	 */
	private String issueTradeName = null;

	/**
	 * 收货企业名称
	 */
	private String receiveTradeName = null;

	/**
	 * 发货日期
	 */
	private Date issueDate;

	/**
	 * 收货日期
	 */
	private Date receiveDate;

	/**
	 * 发货申报人
	 */
	private String issueIsDeclaEm = null;

	/**
	 * 收货申报人
	 */
	private String receiveIsDeclaEm = null;

	/**
	 * 发货申报时间
	 */
	private Date issueIsDeclaDate = null;

	/**
	 * 收货申报时间
	 */
	private Date receiveIsDeclaDate = null;

	/**
	 * 发货申报企业9位组织机构代码
	 */
	private String issueAgentCode = null;

	/**
	 * 收货申报企业9位组织机构代码
	 */
	private String receiveAgentCode = null;

	/**
	 * 发货申报企业组织机构名称
	 */
	private String issueAgentName = null;

	/**
	 * 发货申报企业组织机构名称
	 */
	private String receiveAgentName = null;

	/**
	 * 发货备注
	 */
	private String issueNote = null;

	/**
	 * 收货备注
	 */
	private String receiveNote = null;

	/**
	 * 转入手册号
	 */
	private String contractNoIn = null;
	/**
	 * 申报状态 注：加入已撤销状态
	 */
	private String appState;

	/**
	 * 客户
	 */
	private ScmCoc customer;
	/**
	 * 生成的报关单号码
	 */
	private String makeCustomsDeclarationCode;

	/**
	 * 是否转报关单
	 */
	private Boolean isCustomsDeclaration = false;

	/**
	 * 旧运输工具类型（无用字段）
	 */
	private String transportToolType;
	/**
	 * 新运输工具类型（现用字段）
	 */
	private Transf transportToolTypenew;
	/**
	 * 运输工具编号
	 */
	private String transportToolNumber;

	/**
	 * 录入人员对象
	 */
	private AclUser aclUser = null;

	// /**
	// * 是否撤消 发货单，退货单有用到
	// */
	// private Boolean isCancel = false;

	private Boolean isSelected = true;

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

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

	public Boolean getIsCustomsDeclaration() {
		return isCustomsDeclaration;
	}

	public void setIsCustomsDeclaration(Boolean isCustomsDeclaration) {
		this.isCustomsDeclaration = isCustomsDeclaration;
	}

	public String getMakeCustomsDeclarationCode() {
		return makeCustomsDeclarationCode;
	}

	public void setMakeCustomsDeclarationCode(String makeCustomsDeclarationCode) {
		this.makeCustomsDeclarationCode = makeCustomsDeclarationCode;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getBillSort() {
		return billSort;
	}

	public void setBillSort(String billSort) {
		this.billSort = billSort;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOutEmsNo() {
		return outEmsNo;
	}

	public void setOutEmsNo(String inEmsNo) {
		this.outEmsNo = inEmsNo;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssueCopBillNo() {
		return issueCopBillNo;
	}

	public void setIssueCopBillNo(String issueCopBillNo) {
		this.issueCopBillNo = issueCopBillNo;
	}

	public String getReceiveCopBillNo() {
		return receiveCopBillNo;
	}

	public void setReceiveCopBillNo(String receiveCopBillNo) {
		this.receiveCopBillNo = receiveCopBillNo;
	}

	public String getIssueTradeCod() {
		return issueTradeCod;
	}

	public void setIssueTradeCod(String issueTradeCod) {
		this.issueTradeCod = issueTradeCod;
	}

	public String getReceiveTradeCod() {
		return receiveTradeCod;
	}

	public void setReceiveTradeCod(String receiveTradeCod) {
		this.receiveTradeCod = receiveTradeCod;
	}

	public String getIssueTradeName() {
		return issueTradeName;
	}

	public void setIssueTradeName(String issueTradeName) {
		this.issueTradeName = issueTradeName;
	}

	public String getReceiveTradeName() {
		return receiveTradeName;
	}

	public void setReceiveTradeName(String receiveTradeName) {
		this.receiveTradeName = receiveTradeName;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getIssueIsDeclaEm() {
		return issueIsDeclaEm;
	}

	public void setIssueIsDeclaEm(String issueIsDeclaEm) {
		this.issueIsDeclaEm = issueIsDeclaEm;
	}

	public String getReceiveIsDeclaEm() {
		return receiveIsDeclaEm;
	}

	public void setReceiveIsDeclaEm(String receiveIsDeclaEm) {
		this.receiveIsDeclaEm = receiveIsDeclaEm;
	}

	public Date getIssueIsDeclaDate() {
		return issueIsDeclaDate;
	}

	public void setIssueIsDeclaDate(Date issueIsDeclaDate) {
		this.issueIsDeclaDate = issueIsDeclaDate;
	}

	public Date getReceiveIsDeclaDate() {
		return receiveIsDeclaDate;
	}

	public void setReceiveIsDeclaDate(Date receiveIsDeclaDate) {
		this.receiveIsDeclaDate = receiveIsDeclaDate;
	}

	public String getIssueAgentCode() {
		return issueAgentCode;
	}

	public void setIssueAgentCode(String issueAgentCode) {
		this.issueAgentCode = issueAgentCode;
	}

	public String getReceiveAgentCode() {
		return receiveAgentCode;
	}

	public void setReceiveAgentCode(String receiveAgentCode) {
		this.receiveAgentCode = receiveAgentCode;
	}

	public String getIssueAgentName() {
		return issueAgentName;
	}

	public void setIssueAgentName(String issueAgentName) {
		this.issueAgentName = issueAgentName;
	}

	public String getReceiveAgentName() {
		return receiveAgentName;
	}

	public void setReceiveAgentName(String receiveAgentName) {
		this.receiveAgentName = receiveAgentName;
	}

	public String getIssueNote() {
		return issueNote;
	}

	public void setIssueNote(String issueNote) {
		this.issueNote = issueNote;
	}

	public String getReceiveNote() {
		return receiveNote;
	}

	public void setReceiveNote(String receiveNote) {
		this.receiveNote = receiveNote;
	}

	public String getAppState() {
		return appState;
	}

	public void setAppState(String appState) {
		this.appState = appState;
	}

	/**
	 * 获取内部编号,生成报文所用
	 * 
	 * @return
	 */
	public String getCopNo() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueCopBillNo;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveCopBillNo;
		}
		return "";
	}

	/**
	 * 获取发（收）货企业编码,生成报文所用
	 */
	public String getTradeCod() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueTradeCod;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveTradeCod;
		}
		return "";
	}

	/**
	 * 获取发（收）货企业名称,生成报文所用
	 */
	public String getTradeName() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueTradeName;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveTradeName;
		}
		return "";
	}

	/**
	 * 获取发（收）货日期,生成报文所用
	 */
	public Date getIossueDate() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueDate;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveDate;
		}
		return null;
	}

	/**
	 * 获取发（收）货申报人,生成报文所用
	 */
	public String getIsDeclaEm() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueIsDeclaEm;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveIsDeclaEm;
		}
		return "";
	}

	/**
	 * 获取发（收）货申报日期,生成报文所用
	 */
	public Date getIsDeclaDate() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueIsDeclaDate;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveIsDeclaDate;
		}
		return null;
	}

	/**
	 * 获取发（收）货申报企业9位组织机构代码,生成报文所用
	 */
	public String getAgentCode() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueAgentCode;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveAgentCode;
		}
		return "";
	}

	/**
	 * 获取发（收）货申报企业9位组织机构名称,生成报文所用
	 */
	public String getAgentName() {
		if (FptInOutFlag.OUT.equals(this.billSort)) {
			return this.issueAgentName;
		} else if (FptInOutFlag.IN.equals(this.billSort)) {
			return this.receiveAgentName;
		}
		return "";
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	// public Boolean getIsCancel() {
	// return isCancel;
	// }
	//
	// public void setIsCancel(Boolean isCancel) {
	// this.isCancel = isCancel;
	// }

	public ScmCoc getCustomer() {
		return customer;
	}

	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	public String getContractNoIn() {
		return contractNoIn;
	}

	public void setContractNoIn(String contractNoIn) {
		this.contractNoIn = contractNoIn;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 旧运输工具类型（无用字段）
	 */
	public String getTransportToolType() {
		return transportToolType;
	}
	/**
	 * 旧运输工具类型（无用字段）
	 */
	public void setTransportToolType(String transportToolType) {
		this.transportToolType = transportToolType;
	}

	public String getTransportToolNumber() {
		return transportToolNumber;
	}

	public void setTransportToolNumber(String transportToolNumber) {
		this.transportToolNumber = transportToolNumber;
	}
	/**
	 * 新运输工具类型（现用字段）
	 */
	public Transf getTransportToolTypenew() {
		return transportToolTypenew;
	}
	/**
	 * 新运输工具类型（现用字段）
	 */
	public void setTransportToolTypenew(Transf transportToolTypenew) {
		this.transportToolTypenew = transportToolTypenew;
	}

}