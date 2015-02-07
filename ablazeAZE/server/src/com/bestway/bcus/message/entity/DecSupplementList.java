package com.bestway.bcus.message.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class DecSupplementList extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 补充报关单报关单商品新增报关单表体
	 */
	private String baseCustomsDeclarationCommInfo;
	/**
	 * 补充申报单商品序号
	 */
	private String gno;
	/**
	 * 补充申报单类型
	 */
	private String supType;
	/**
	 * 品牌中文
	 */
	private String brandCN;
	/**
	 * 品牌英文
	 */
	private String brandEN;
	/**
	 * 买方名称
	 */
	private String buyer;
	/**
	 * 买方联系人
	 */
	private String buyerContact;
	/**
	 * 买方地址
	 */
	private String buyerAddr;
	/**
	 * 买方电话
	 */
	private String buyerTel;
	/**
	 * 卖方名称
	 */
	private String seller;
	/**
	 * 卖方联系人
	 */
	private String sellerContact;
	/**
	 * 卖方地址
	 */
	private String sellerAddr;
	/**
	 * 卖方电话
	 */
	private String sellerTel;
	/**
	 * 生产厂商名称
	 */
	private String factory;
	/**
	 * 生产厂商联系人
	 */
	private String factoryContact;
	/**
	 * 生产厂商地址
	 */
	private String factoryAddr;
	/**
	 * 生产厂商电话
	 */
	private String factoryTel;
	/**
	 * 合同协议号
	 */
	private String contrNo;
	/**
	 * 签约日期
	 */
	private Date contrDate;
	/**
	 * 发票编号
	 */
	private String invoiceNo;
	/**
	 * 发票日期
	 */
	private Date invoiceDate;
	/**
	 * 买卖双方之间存在的关系
	 */
	private String i_BabRel;
	/**
	 * 买卖双方关系是否影响进口货物成交价格：
	 */
	private String i_PriceEffect;
	/**
	 * 进口货物成交价格相近情况
	 */
	private String i_PriceClose;
	/**
	 * 买方处置或使用货物时是否受到除行政法规规定的限制以及对货物销售地域限制以外的限制
	 */
	private String i_OtherLimited;
	/**
	 * 货物的价格是否受到使货物的成交价格无法确定的条件或因素的影响
	 */
	private String i_OtherEffect;
	/**
	 * 备注1
	 */
	private String i_Note1;
	/**
	 * 买方是否应直接或间接支付与进口货物有关并作为货物销售条件的特许权使用费
	 */
	private String i_IsUsefee;
	/**
	 * 卖方是否直接或间接从买方对该货物进口后销售、处置或者使用所得中获得收益
	 */
	private String i_IsProfit;
    /**
	 * 币制
	 */
	private Curr curr;
	/**
	 * 备注2
	 */
	private String i_Note2;
	/**
	 * 发票价格单位金额
	 */
	private String invoicePrice;
	/**
	 * 发票价格总金额
	 */
	private String invoiceAmount;
	/**
	 * 发票价格备注
	 */
	private String invoiceNote;
	/**
	 * 间接支付/收取的货款单位金额，进口是间接支付，出口是间接收取
	 */
	private String goodsPrice;
	/**
	 * 间接支付/收取的货款总金额，进口是间接支付，出口是间接收取
	 */
	private String goodsAmount;
	/**
	 * 间接支付/收取的货款备注，进口是间接支付，出口是间接收取
	 */
	private String goodsNote;
	/**
	 * 进口货物除购货佣金以外的佣金和经纪费单位金额
	 */
	private String i_CommissionPrice;
	/**
	 * 进口货物除购货佣金以外的佣金和经纪费总金额
	 */
	private String i_CommissionAmount;
	/**
	 * 进口货物除购货佣金以外的佣金和经纪费备注
	 */
	private String i_CommissionNote;
	/**
	 * 与该进口货物视为一体的容器费用单位金额
	 */
	private String i_ContainerPrice;
	/**
	 * 与该进口货物视为一体的容器费用总金额
	 */
	private String i_ContainerAmount;
	/**
	 * 与该进口货物视为一体的容器费用备注
	 */
	private String i_ContainerNote;
	/**
	 * 与该进口货物视为一体的容器费用单位金额
	 */
	private String i_PackPrice;
	/**
	 * 与该进口货物视为一体的容器费用总金额
	 */
	private String i_PackAmount;
	/**
	 * 与该进口货物视为一体的容器费用备注
	 */
	private String i_PackNote;
	/**
	 * 进口货物包装材料和包装劳务费用单位金额
	 */
	private String i_PartPrice;
	/**
	 * 进口货物包装材料和包装劳务费用总金额
	 */
	private String i_PartAmount;
	/**
	 * 进口货物包装材料和包装劳务费用备注
	 */
	private String i_PartNote;
	/**
	 * 进口货物包含的材料、部件、零件和类似货物单位金额
	 */
	private String i_ToolPrice;
	/**
	 * 进口货物包含的材料、部件、零件和类似货物总金额
	 */
	private String i_ToolAmount;
	/**
	 * 进口货物包含的材料、部件、零件和类似货物备注
	 */
	private String i_ToolNote;
	/**
	 * 在生产进口货物过程中消耗的材料单位金额
	 */
	private String i_LossPrice;
	/**
	 * 在生产进口货物过程中消耗的材料总金额
	 */
	private String i_LossAmount;
	/**
	 *在生产进口货物过程中消耗的材料备注
	 */
	private String i_LossNote;
	/**
	 *进口货物在境外进行的为生产进口货物所需的工程设计、技术研发、工艺及制图等相关服务单位金额
	 */
	private String i_DesignPrice;
	/**
	 *进口货物在境外进行的为生产进口货物所需的工程设计、技术研发、工艺及制图等相关服务总金额
	 */
	private String i_DesignAmount;
	/**
	 *进口货物在境外进行的为生产进口货物所需的工程设计、技术研发、工艺及制图等相关服务备注
	 */
	private String i_DesignNote;
	/**
	 *特许权使用费单位金额
	 */
	private String i_UsefeePrice;
	/**
	 *特许权使用费总金额
	 */
	private String i_UsefeeAmount;
	/**
	 *特许权使用费备注
	 */
	private String i_UsefeeNote;
	/**
	 *卖方直接或间接从买方对货物进口后转售、处置或使用所得中获得的收益单位金额
	 */
	private String i_ProfitPrice;
	/**
	 *卖方直接或间接从买方对货物进口后转售、处置或使用所得中获得的收益总金额
	 */
	private String i_ProfitAmount;
	/**
	 *价格申报项，卖方直接或间接从买方对货物进口后转售、处置或使用所得中获得的收益备注
	 */
	private String i_ProfitNote;
	/**
	 *进口货物运输费用单位金额
	 */
	private String i_FeePrice;
	/**
	 *进口货物运输费用总金额
	 */
	private String i_FeeAmount;
	/**
	 *进口货物运输费用备注
	 */
	private String i_FeeNote;
	/**
	 *进口货物运输相关费用单位金额
	 */
	private String i_OtherPrice;
	/**
	 *进口货物运输相关费用总金额
	 */
	private String i_OtherAmount;
	/**
	 *进口货物运输相关费用备注
	 */
	private String i_OtherNote;
	/**
	 *进口货物保险费单位金额
	 */
	private String i_InsurPrice;
	/**
	 *进口货物保险费总金额
	 */
	private String i_InsurAmount;
	/**
	 *进口货物保险费备注
	 */
	private String i_InsurNote;
	/**
	 *出口关税是否已经从申报价格中扣除
	 */
	private String e_IsDutyDel;
	/**
	 *商品其他名称
	 */
	private String gnameOther;
	/**
	 *进/出口国地区海关商品编码
	 */
	private String codeTsOther;
	/**
	 *该商品是否取得过海关预归类决定书
	 */
	private String isClassDecision;
	/**
	 *预归类决定书编号
	 */
	private String decisionNo;
	/**
	 *预归类决定书商品编码
	 */
	private String codeTsDecision;
	/**
	 *作出预归类决定的直属海关
	 */
	private Customs decisionCus;
	/**
	 *该商品是否曾被海关取样化验
	 */
	private String isSampleTest;
	/**
	 *商品信息选项
	 */
	private String goptions;
	/**
	 *运输方式
	 */
	private Transf trafMode;
	/**
	 *是否直接运输
	 */
	private String isDirectTraf;
	/**
	 *中转国地区
	 */
	private Country transitCountry;
	/**
	 *到货口岸
	 */
	private PreDock destPort;
	/**
	 *申报口岸
	 */
	private PreDock declPort;
	/**
	 *提单编号
	 */
	private String billNo;
	/**
	 *原产国（地区）
	 */
	private Country originCountry;
	/**
	 *原产国（地区）标记的位置
	 */
	private String originMark;
	/**
	 *原产地证书签发机构及所在国家（地区）
	 */
	private Country certCountry;
	/**
	 *原产地证书编号
	 */
	private String certNo;
	/**
	 *适用的原产地标准
	 */
	private String certStandard;
	/**
	 *其他需要说明的情况
	 */
	private String otherNote;
	/**
	 *对以上申报内容是否需要海关予以保密
	 */
	private String isSecret;
	/**
	 *申报单位类型
	 */
	private String agentType;
	/**
	 *申报人单位地址
	 */
	private String declAddr;
	/**
	 *申报人邮编
	 */
	private String declPost;
	/**
	 *申报人电话
	 */
	private String declTel;
	/**
	 *操作类型
	 */
	private String operType;
	/**
	 *操作员IC卡号
	 */
	private String icCardId;
	/**
	 *报关单数据中心统一编号
	 */
	private String clientSeqNo;
	/**
	 *签名日期
	 */
	private Date signDate;
	/**
	 *数字签名信息
	 */
	private String signInfo;
	/**
	 *是否发送过
	 */
	private String isSend;
	/**
	 *是否检查
	 */
	private String isCheck;
	/**
	 *录入日期
	 */
	private Date inputDate;
	/**
	 *发送日期
	 */
	private Date sendDate;
	
	public String getGno() {
		return gno;
	}
	public void setGno(String gno) {
		this.gno = gno;
	}
	public String getSupType() {
		return supType;
	}
	public void setSupType(String supType) {
		this.supType = supType;
	}
	public String getBrandCN() {
		return brandCN;
	}
	public void setBrandCN(String brandCN) {
		this.brandCN = brandCN;
	}
	public String getBrandEN() {
		return brandEN;
	}
	public void setBrandEN(String brandEN) {
		this.brandEN = brandEN;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getBuyerAddr() {
		return buyerAddr;
	}
	public void setBuyerAddr(String buyerAddr) {
		this.buyerAddr = buyerAddr;
	}
	public String getBuyerTel() {
		return buyerTel;
	}
	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getSellerContact() {
		return sellerContact;
	}
	public void setSellerContact(String sellerContact) {
		this.sellerContact = sellerContact;
	}
	public String getSellerAddr() {
		return sellerAddr;
	}
	public void setSellerAddr(String sellerAddr) {
		this.sellerAddr = sellerAddr;
	}
	public String getSellerTel() {
		return sellerTel;
	}
	public void setSellerTel(String sellerTel) {
		this.sellerTel = sellerTel;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getFactoryContact() {
		return factoryContact;
	}
	public void setFactoryContact(String factoryContact) {
		this.factoryContact = factoryContact;
	}
	public String getFactoryAddr() {
		return factoryAddr;
	}
	public void setFactoryAddr(String factoryAddr) {
		this.factoryAddr = factoryAddr;
	}
	public String getFactoryTel() {
		return factoryTel;
	}
	public void setFactoryTel(String factoryTel) {
		this.factoryTel = factoryTel;
	}
	public String getContrNo() {
		return contrNo;
	}
	public void setContrNo(String contrNo) {
		this.contrNo = contrNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getI_BabRel() {
		return i_BabRel;
	}
	public void setI_BabRel(String babRel) {
		i_BabRel = babRel;
	}
	public String getI_PriceEffect() {
		return i_PriceEffect;
	}
	public void setI_PriceEffect(String priceEffect) {
		i_PriceEffect = priceEffect;
	}
	public String getI_PriceClose() {
		return i_PriceClose;
	}
	public void setI_PriceClose(String priceClose) {
		i_PriceClose = priceClose;
	}
	public String getI_OtherLimited() {
		return i_OtherLimited;
	}
	public void setI_OtherLimited(String otherLimited) {
		i_OtherLimited = otherLimited;
	}
	public String getI_OtherEffect() {
		return i_OtherEffect;
	}
	public void setI_OtherEffect(String otherEffect) {
		i_OtherEffect = otherEffect;
	}
	public String getI_Note1() {
		return i_Note1;
	}
	public void setI_Note1(String note1) {
		i_Note1 = note1;
	}
	public String getI_IsUsefee() {
		return i_IsUsefee;
	}
	public void setI_IsUsefee(String isUsefee) {
		i_IsUsefee = isUsefee;
	}
	public String getI_IsProfit() {
		return i_IsProfit;
	}
	public void setI_IsProfit(String isProfit) {
		i_IsProfit = isProfit;
	}

	public Curr getCurr() {
		return curr;
	}
	public void setCurr(Curr curr) {
		this.curr = curr;
	}
	public String getI_Note2() {
		return i_Note2;
	}
	public void setI_Note2(String note2) {
		i_Note2 = note2;
	}
	public String getInvoicePrice() {
		return invoicePrice;
	}
	public void setInvoicePrice(String invoicePrice) {
		this.invoicePrice = invoicePrice;
	}
	public String getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getInvoiceNote() {
		return invoiceNote;
	}
	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public String getGoodsNote() {
		return goodsNote;
	}
	public void setGoodsNote(String goodsNote) {
		this.goodsNote = goodsNote;
	}
	public String getI_CommissionPrice() {
		return i_CommissionPrice;
	}
	public void setI_CommissionPrice(String commissionPrice) {
		i_CommissionPrice = commissionPrice;
	}
	public String getI_CommissionAmount() {
		return i_CommissionAmount;
	}
	public void setI_CommissionAmount(String commissionAmount) {
		i_CommissionAmount = commissionAmount;
	}
	public String getI_CommissionNote() {
		return i_CommissionNote;
	}
	public void setI_CommissionNote(String commissionNote) {
		i_CommissionNote = commissionNote;
	}
	public String getI_ContainerPrice() {
		return i_ContainerPrice;
	}
	public void setI_ContainerPrice(String containerPrice) {
		i_ContainerPrice = containerPrice;
	}
	/**
	 * 与该进口货物视为一体的容器费用单位金额
	 * @return
	 */
	public String getI_ContainerAmount() {
		return i_ContainerAmount;
	}
	public void setI_ContainerAmount(String containerAmount) {
		i_ContainerAmount = containerAmount;
	}
	public String getI_ContainerNote() {
		return i_ContainerNote;
	}
	public void setI_ContainerNote(String containerNote) {
		i_ContainerNote = containerNote;
	}
	/**
	 * 进口货物包装材料和包装劳务费用单位金额
	 * @return
	 */
	public String getI_PackPrice() {
		return i_PackPrice;
	}
	public void setI_PackPrice(String packPrice) {
		i_PackPrice = packPrice;
	}
	public String getI_PackAmount() {
		return i_PackAmount;
	}
	public void setI_PackAmount(String packAmount) {
		i_PackAmount = packAmount;
	}
	public String getI_PackNote() {
		return i_PackNote;
	}
	public void setI_PackNote(String packNote) {
		i_PackNote = packNote;
	}
	public String getI_PartPrice() {
		return i_PartPrice;
	}
	public void setI_PartPrice(String partPrice) {
		i_PartPrice = partPrice;
	}
	public String getI_PartAmount() {
		return i_PartAmount;
	}
	public void setI_PartAmount(String partAmount) {
		i_PartAmount = partAmount;
	}
	public String getI_PartNote() {
		return i_PartNote;
	}
	public void setI_PartNote(String partNote) {
		i_PartNote = partNote;
	}
	public String getI_ToolPrice() {
		return i_ToolPrice;
	}
	public void setI_ToolPrice(String toolPrice) {
		i_ToolPrice = toolPrice;
	}
	public String getI_ToolAmount() {
		return i_ToolAmount;
	}
	public void setI_ToolAmount(String toolAmount) {
		i_ToolAmount = toolAmount;
	}
	public String getI_ToolNote() {
		return i_ToolNote;
	}
	public void setI_ToolNote(String toolNote) {
		i_ToolNote = toolNote;
	}
	public String getI_LossPrice() {
		return i_LossPrice;
	}
	public void setI_LossPrice(String lossPrice) {
		i_LossPrice = lossPrice;
	}
	public String getI_LossAmount() {
		return i_LossAmount;
	}
	public void setI_LossAmount(String lossAmount) {
		i_LossAmount = lossAmount;
	}
	public String getI_LossNote() {
		return i_LossNote;
	}
	public void setI_LossNote(String lossNote) {
		i_LossNote = lossNote;
	}
	public String getI_DesignPrice() {
		return i_DesignPrice;
	}
	public void setI_DesignPrice(String designPrice) {
		i_DesignPrice = designPrice;
	}
	public String getI_DesignAmount() {
		return i_DesignAmount;
	}
	public void setI_DesignAmount(String designAmount) {
		i_DesignAmount = designAmount;
	}
	public String getI_DesignNote() {
		return i_DesignNote;
	}
	public void setI_DesignNote(String designNote) {
		i_DesignNote = designNote;
	}
	public String getI_UsefeePrice() {
		return i_UsefeePrice;
	}
	public void setI_UsefeePrice(String usefeePrice) {
		i_UsefeePrice = usefeePrice;
	}
	public String getI_UsefeeAmount() {
		return i_UsefeeAmount;
	}
	public void setI_UsefeeAmount(String usefeeAmount) {
		i_UsefeeAmount = usefeeAmount;
	}
	public String getI_UsefeeNote() {
		return i_UsefeeNote;
	}
	public void setI_UsefeeNote(String usefeeNote) {
		i_UsefeeNote = usefeeNote;
	}
	public String getI_ProfitPrice() {
		return i_ProfitPrice;
	}
	public void setI_ProfitPrice(String profitPrice) {
		i_ProfitPrice = profitPrice;
	}
	public String getI_ProfitAmount() {
		return i_ProfitAmount;
	}
	public void setI_ProfitAmount(String profitAmount) {
		i_ProfitAmount = profitAmount;
	}
	public String getI_ProfitNote() {
		return i_ProfitNote;
	}
	public void setI_ProfitNote(String profitNote) {
		i_ProfitNote = profitNote;
	}
	public String getI_FeePrice() {
		return i_FeePrice;
	}
	public void setI_FeePrice(String feePrice) {
		i_FeePrice = feePrice;
	}
	public String getI_FeeAmount() {
		return i_FeeAmount;
	}
	public void setI_FeeAmount(String feeAmount) {
		i_FeeAmount = feeAmount;
	}
	public String getI_FeeNote() {
		return i_FeeNote;
	}
	public void setI_FeeNote(String feeNote) {
		i_FeeNote = feeNote;
	}
	public String getI_OtherPrice() {
		return i_OtherPrice;
	}
	public void setI_OtherPrice(String otherPrice) {
		i_OtherPrice = otherPrice;
	}
	public String getI_OtherAmount() {
		return i_OtherAmount;
	}
	public void setI_OtherAmount(String otherAmount) {
		i_OtherAmount = otherAmount;
	}
	public String getI_OtherNote() {
		return i_OtherNote;
	}
	public void setI_OtherNote(String otherNote) {
		i_OtherNote = otherNote;
	}
	public String getI_InsurPrice() {
		return i_InsurPrice;
	}
	public void setI_InsurPrice(String insurPrice) {
		i_InsurPrice = insurPrice;
	}
	public String getI_InsurAmount() {
		return i_InsurAmount;
	}
	public void setI_InsurAmount(String insurAmount) {
		i_InsurAmount = insurAmount;
	}
	public String getI_InsurNote() {
		return i_InsurNote;
	}
	public void setI_InsurNote(String insurNote) {
		i_InsurNote = insurNote;
	}
	public String getE_IsDutyDel() {
		return e_IsDutyDel;
	}
	public void setE_IsDutyDel(String isDutyDel) {
		e_IsDutyDel = isDutyDel;
	}
	public String getGnameOther() {
		return gnameOther;
	}
	public void setGnameOther(String gnameOther) {
		this.gnameOther = gnameOther;
	}
	public String getCodeTsOther() {
		return codeTsOther;
	}
	public void setCodeTsOther(String codeTsOther) {
		this.codeTsOther = codeTsOther;
	}
	public String getIsClassDecision() {
		return isClassDecision;
	}
	public void setIsClassDecision(String isClassDecision) {
		this.isClassDecision = isClassDecision;
	}
	public String getDecisionNo() {
		return decisionNo;
	}
	public void setDecisionNo(String decisionNo) {
		this.decisionNo = decisionNo;
	}
	public String getCodeTsDecision() {
		return codeTsDecision;
	}
	public void setCodeTsDecision(String codeTsDecision) {
		this.codeTsDecision = codeTsDecision;
	}

	public Customs getDecisionCus() {
		return decisionCus;
	}
	public void setDecisionCus(Customs decisionCus) {
		this.decisionCus = decisionCus;
	}
	public String getIsSampleTest() {
		return isSampleTest;
	}
	public void setIsSampleTest(String isSampleTest) {
		this.isSampleTest = isSampleTest;
	}
	public String getGoptions() {
		return goptions;
	}
	public void setGoptions(String goptions) {
		this.goptions = goptions;
	}
	
	public Transf getTrafMode() {
		return trafMode;
	}
	public void setTrafMode(Transf trafMode) {
		this.trafMode = trafMode;
	}
	public String getIsDirectTraf() {
		return isDirectTraf;
	}
	public void setIsDirectTraf(String isDirectTraf) {
		this.isDirectTraf = isDirectTraf;
	}
	
	public Country getTransitCountry() {
		return transitCountry;
	}
	public void setTransitCountry(Country transitCountry) {
		this.transitCountry = transitCountry;
	}
	public PreDock getDestPort() {
		return destPort;
	}
	public void setDestPort(PreDock destPort) {
		this.destPort = destPort;
	}
	public PreDock getDeclPort() {
		return declPort;
	}
	public void setDeclPort(PreDock declPort) {
		this.declPort = declPort;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public Country getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}
	public String getOriginMark() {
		return originMark;
	}
	public void setOriginMark(String originMark) {
		this.originMark = originMark;
	}
	public Country getCertCountry() {
		return certCountry;
	}
	public void setCertCountry(Country certCountry) {
		this.certCountry = certCountry;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCertStandard() {
		return certStandard;
	}
	public void setCertStandard(String certStandard) {
		this.certStandard = certStandard;
	}
	
	public String getOtherNote() {
		return otherNote;
	}
	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}
	public String getIsSecret() {
		return isSecret;
	}
	public void setIsSecret(String isSecret) {
		this.isSecret = isSecret;
	}
	public String getAgentType() {
		return agentType;
	}
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	public String getDeclAddr() {
		return declAddr;
	}
	public void setDeclAddr(String declAddr) {
		this.declAddr = declAddr;
	}
	public String getDeclPost() {
		return declPost;
	}
	public void setDeclPost(String declPost) {
		this.declPost = declPost;
	}
	public String getDeclTel() {
		return declTel;
	}
	public void setDeclTel(String declTel) {
		this.declTel = declTel;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getIcCardId() {
		return icCardId;
	}
	public void setIcCardId(String icCardId) {
		this.icCardId = icCardId;
	}
	public String getClientSeqNo() {
		return clientSeqNo;
	}
	public void setClientSeqNo(String clientSeqNo) {
		this.clientSeqNo = clientSeqNo;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public void setContrDate(Date contrDate) {
		this.contrDate = contrDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getContrDate() {
		return contrDate;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public Date getSendDate() {
		return sendDate;
	}

	public String getSignInfo() {
		return signInfo;
	}
	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}
	public String getIsSend() {
		return isSend;
	}
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getBaseCustomsDeclarationCommInfo() {
		return baseCustomsDeclarationCommInfo;
	}
	public void setBaseCustomsDeclarationCommInfo(
			String baseCustomsDeclarationCommInfo) {
		this.baseCustomsDeclarationCommInfo = baseCustomsDeclarationCommInfo;
	}

	
	
}
