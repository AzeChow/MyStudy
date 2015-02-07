package com.bestway.bcs.verification.entity;

import java.util.Date;
import java.util.List;

import com.bestway.common.BaseScmEntity;

public class VFAttachmentManagement extends BaseScmEntity {
	
	public static final String UPLOAD_AND_DOWNLOAD = "0";//上传和下载
	public static final String EXPORP_EXCEL = "1";//导出Excel
	public static final String PRINT = "2";//打印预报核
	
	public static final String DECLARATION_COMMINFO_EXGX_IMP = "0";//2、稽查期内进口报关单
	public static final String DECLARETION_COMMINFO_EXGX_EXP = "1";//3、稽查期内出口报关单
	public static final String STOCK_EXG = "2";//1、成品库存汇总表
	public static final String STOCK_IMG = "3";//2、原材料库存汇总表
	public static final String FINISHING_STOCK = "4";//3、在制品库存汇总表
	public static final String STOCK_TRAVELING_IMG = "5";//4、在途原材料库存汇总表
	public static final String STOCK_TRAVELING_EXG = "6";//5、在途成品库存汇总表
	public static final String STOCK_OUT_SEND_ANALYSE = "7";//6、外发加工库存
	public static final String STOCK_BUY_IMG = "8";//7、内购库存
	public static final String STOCK_OUT_FACTORY_IMG = "9";//8、厂外库存
	public static final String STOCK_PROCESS_IMG = "10";//9、在产品库存
	public static final String BAD_STOCK = "11";//10、残次品库存
	public static final String STOCK_SEMI_EXG_PT_HAD_STORE = "12";//11、半成品入库
	
	public static final String STOCK_EXG_CONVERT = "13";//1、成品库存折算表
	public static final String FINISHING_STOCK_CONVERT = "14";//2、在制品库存折算表
	public static final String STOCK_TRAVELING_EXG_CONVERT = "15";//3、在途成品库存折算表
	public static final String STOCK_OUT_SEND_ANALYSE_CONVERT = "16";//4、外发库存折算表
	public static final String STOCK_OUT_SEND_SEMI_EXG = "25";//5、外发库存折算表(半成品)
	public static final String SEMI_FINISHED_EXG = "17";//6、残次品库存折算表(半成品)
	public static final String FINISHED_EXG = "18";//7、残次品库存折算表(成品)
	public static final String STOCK_SEMI_EXG_PT_HAD_STORE_CONVERT = "19";//8、半成品库存折算表
	public static final String ANALYSES = "24";//1、短溢表
	public static final String CONTRACT_ANALYSE = "27";//合同分析底帐
	
	//批次
	public VFSection section;

	//项目内容
	private String projectContent;
	
	//数据内容
	private String dataContent;
	
	//提供单位
	private String provideUnit;
	
	//数据类型
	private String dataType;
	
	//资料提供要求
	private String requirements;
	
	//是否用印
	private String usingSeal;
	
	//资料签名
	private String dataSignature;
	
	//附件名
	private String attachmentName;
	
	//创建时间
	private Date createDate;
	
	//修改人
	private String updatePeople;
	
	//修改时间
	private Date updateDate;
	
	//是否隐藏
	public Boolean isHidden;
	
	//文件父级节点
	private String parentId;
	
	//控件状态
	private String controlsState;
	
	//操作状态
	private String operateState;
	
	//是不是 模板
	private Boolean isTemplate;
	
	//是否已修改
	private Boolean isModify =false;
	
	/**
	 * 子节点
	 */
	private List<VFAttachmentManagement> children = null;
	
	public List<VFAttachmentManagement> getChildren() {
		return children;
	}

	public void setChildren(List<VFAttachmentManagement> children) {
		this.children = children;
	}

	/**
	 * 项目内容
	 * @return
	 */
	public String getProjectContent() {
		return projectContent;
	}

	/**
	 * 项目内容
	 * @param projectContent
	 */
	public void setProjectContent(String projectContent) {
		if(getProjectContent()==null||!getProjectContent().equals(projectContent)){
			setIsModify(true);
		}
		this.projectContent = projectContent;
	}

	/**
	 * 数据内容
	 * @return
	 */
	public String getDataContent() {
		return dataContent;
	}

	/**
	 * 数据内容
	 * @param dateContent
	 */
	public void setDataContent(String dataContent) {
		if(getDataContent()==null||!getDataContent().equals(dataContent)){
			setIsModify(true);
		}
		this.dataContent = dataContent;
	}

	/**
	 * 提供单位
	 * @return
	 */
	public String getProvideUnit() {
		return provideUnit;
	}

	/**
	 * 提供单位
	 * @param provideUnit
	 */
	public void setProvideUnit(String provideUnit) {
		if(getProvideUnit()==null||!getProvideUnit().equals(provideUnit)){
			setIsModify(true);
		}
		this.provideUnit = provideUnit;
	}

	/**
	 * 数据类型
	 * @return
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 数据类型
	 * @param dataType
	 */
	public void setDataType(String dataType) {
		if(getDataType()==null||!getDataType().equals(dataType)){
			setIsModify(true);
		}
		this.dataType = dataType;
	}

	/**
	 * 资料提供要求
	 * @return
	 */
	public String getRequirements() {
		return requirements;
	}

	/**
	 * 资料提供要求
	 * @param requirements
	 */
	public void setRequirements(String requirements) {
		if(getRequirements()==null||!getRequirements().equals(requirements)){
			setIsModify(true);
		}
		this.requirements = requirements;
	}

	/**
	 * 是否用印
	 * @return
	 */
	public String getUsingSeal() {
		return usingSeal;
	}

	/**
	 * 是否用印
	 * @param isUsingSeal
	 */
	public void setUsingSeal(String usingSeal) {
		this.usingSeal = usingSeal;
	}

	/**
	 * 资料签名
	 * @return
	 */
	public String getDataSignature() {
		return dataSignature;
	}

	/**
	 * 资料签名
	 * @param dataSignature
	 */
	public void setDataSignature(String dataSignature) {
		if(getDataSignature()==null||!getDataSignature().equals(dataSignature)){
			setIsModify(true);
		}
		this.dataSignature = dataSignature;
	}

	/**
	 * 附件名
	 * @return
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * 附件名
	 * @param attachmentName
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 修改人
	 * @return
	 */
	public String getUpdatePeople() {
		return updatePeople;
	}

	/**
	 * 修改人
	 * @param updatePeople
	 */
	public void setUpdatePeople(String updatePeople) {
		this.updatePeople = updatePeople;
	}

	/**
	 * 修改时间
	 * @return
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 修改时间
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 是否隐藏
	 * @return
	 */
	public Boolean getIsHidden() {
		return isHidden;
	}

	/**
	 * 是否隐藏
	 * @param isHidden
	 */
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * 文件父级节点
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 文件父级节点
	 * @param parentNode
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 控件状态
	 * @return
	 */
	public String getControlsState() {
		return controlsState;
	}

	/**
	 * 控件状态
	 * @param operatePermissions
	 */
	public void setControlsState(String controlsState) {
		this.controlsState = controlsState;
	}

	/**
	 * 操作状态
	 * @return
	 */
	public String getOperateState() {
		return operateState;
	}

	/**
	 * 操作状态
	 * @param exoprt
	 */
	public void setOperateState(String operateState) {
		this.operateState = operateState;
	}

	/**
	 * 是不是 模板
	 * @return
	 */
	public Boolean getIsTemplate() {
		return isTemplate;
	}

	/**
	 * 是不是 模板
	 * @param isTemplate
	 */
	public void setIsTemplate(Boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	/**
	 * 是否已修改
	 * @return
	 */
	public Boolean getIsModify() {
		return isModify;
	}

	/**
	 * 是否已修改
	 * @param isModify
	 */
	public void setIsModify(Boolean isModify) {
		this.isModify = isModify;
	}
	/**
	 * 批次
	 * @return
	 */
	public VFSection getSection() {
		return section;
	}
	/**
	 * 批次
	 * @param section
	 */
	public void setSection(VFSection section) {
		this.section = section;
	}
}
