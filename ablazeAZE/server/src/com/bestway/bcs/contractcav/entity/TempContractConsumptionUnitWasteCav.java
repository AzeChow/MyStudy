package com.bestway.bcs.contractcav.entity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Vector;

import com.bestway.common.CommonUtils;

/**
 * 存放合同核销数据(临时表)
 * 
 * @author ower
 * 
 */
public class TempContractConsumptionUnitWasteCav implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 核销表头
	 */
	private ContractCav contractCav = null;
	/**
	 * 分组
	 */
	private Integer groupId = new Integer(0);

	/**
	 * 料件总表序号
	 */
	private String seqMaterialNum = "";

	/**
	 * 料件名称
	 */
	private String ptName = "";
	/**
	 * 料件规格
	 */
	private String ptSpec = "";
	/**
	 * 备案数量
	 */
	private String fileAmount = "0.0";
	/**
	 * 总进数量
	 */
	private String totalAmount = "0.0";
	/**
	 * 单位
	 */
	private String unit = "";
	/**
	 * 成品数量1
	 */
	private String productAmount1 = "0.0";
	/**
	 * 成品数量2
	 */
	private String productAmount2 = "0.0";
	/**
	 * 成品数量3
	 */
	private String productAmount3 = "0.0";
	/**
	 * 成品数量4
	 */
	private String productAmount4 = "0.0";
	/**
	 * 成品数量5
	 */
	private String productAmount5 = "0.0";
	/**
	 * 成品数量6
	 */
	private String productAmount6 = "0.0";
	/**
	 * 成品1
	 */
	private String exg1 = "";

	/**
	 * 成品2
	 */
	private String exg2 = "";

	/**
	 * 成品3
	 */
	private String exg3 = "";

	/**
	 * 成品4
	 */
	private String exg4 = "";
	/**
	 * 成品5
	 */
	private String exg5 = "";

	/**
	 * 成品6
	 */
	private String exg6 = "";
	/**
	 * 单位1
	 */
	private String unit1 = "";
	/**
	 * 单位2
	 */
	private String unit2 = "";
	/**
	 * 单位3
	 */
	private String unit3 = "";
	/**
	 * 单位4
	 */
	private String unit4 = "";
	/**
	 * 单位5
	 */
	private String unit5 = "";
	/**
	 * 单位6
	 */
	private String unit6 = "";
	/**
	 * 单耗1
	 */
	private String unitWaste1 = "0.0";

	/**
	 * 总耗1
	 */
	private String totalWaste1 = "0.0";

	/**
	 * 单耗2
	 */
	private String unitWaste2 = "0.0";

	/**
	 * 总耗2
	 */
	private String totalWaste2 = "0.0";

	/**
	 * 单耗3
	 */
	private String unitWaste3 = "0.0";

	/**
	 * 总耗3
	 */
	private String totalWaste3 = "0.0";

	/**
	 * 单耗4
	 */
	private String unitWaste4 = "0.0";

	/**
	 * 总耗4
	 */
	private String totalWaste4 = "0.0";

	/**
	 * 单耗5
	 */
	private String unitWaste5 = "0.0";

	/**
	 * 总耗5
	 */
	private String totalWaste5 = "0.0";

	/**
	 * 单耗6
	 */
	private String unitWaste6 = "0.0";

	/**
	 * 总耗6
	 */
	private String totalWaste6 = "0.0";
	/**
	 * 单位净重
	 */
	private String unitNetWeight = "0.0";
	/**
	 * 总耗合计
	 */
	private String totalConsumption = "0.0";
	/**
	 * 剩余料件数量合计
	 */
	private String totalRemnantImg = "0.0";

	/**
	 * 复出和余料结转出口合计
	 */
	private String totalComebackAndExport = "0.0";

	/**
	 * 剩余残次品及边角料数量
	 */
	private String totalRemnantImperfectionsAndScrap = "0.0";

	/**
	 * 内销数量合计
	 */
	private String totalInternalAmount = "0.0";

	/**
	 * 获取内销数量合计
	 * 
	 * @return
	 */
	public String getTotalInternalAmount() {
		return totalInternalAmount;
	}

	/**
	 * 设置内销数量合计
	 * 
	 * @param totalInternalAmount
	 */

	public void setTotalInternalAmount(String totalInternalAmount) {
		this.totalInternalAmount = totalInternalAmount;
	}

	/**
	 * 获得单位净重
	 * 
	 * @return
	 */
	public String getUnitNetWeight() {
		return unitNetWeight;
	}

	/**
	 * 设置单位净重
	 * 
	 * @param unitNetWeight
	 */
	public void setUnitNetWeight(String unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}

	/**
	 * 获得复出和余料结转出口合计
	 * 
	 * @return
	 */

	public String getTotalComebackAndExport() {
		return totalComebackAndExport;
	}

	/**
	 * 设置复出和余料结转出口合计
	 * 
	 * @param totalComebackAndExport
	 */

	public void setTotalComebackAndExport(String totalComebackAndExport) {
		this.totalComebackAndExport = totalComebackAndExport;
	}

	/**
	 * 获取剩余残次品及边角料数量
	 * 
	 * @return
	 */

	public String getTotalRemnantImperfectionsAndScrap() {
		return totalRemnantImperfectionsAndScrap;
	}

	/**
	 * 设置剩余残次品及边角料数量
	 * 
	 * @param totalRemnantImperfectionsAndScrap
	 */

	public void setTotalRemnantImperfectionsAndScrap(
			String totalRemnantImperfectionsAndScrap) {
		this.totalRemnantImperfectionsAndScrap = totalRemnantImperfectionsAndScrap;
	}

	/**
	 * 剩余料件数量合计
	 * 
	 * @return
	 */
	public String getTotalRemnantImg() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(5);
		nf.setMaximumIntegerDigits(10);
		nf.setGroupingUsed(false);
		Double[] result = new Double[2];
		result[0] = 0d;
		result[1] = 0d;
		add(totalAmount, result);
		del(getTotalConsumption(), result);
		if (unit == null || "千克".equals(unit)) {
			return nf.format(result[0]);
		}
		return nf.format(result[0]) + "/"
				+ nf.format(result[1]);
	}

	/**
	 * 剩余料件数量合计
	 * 
	 * @param totalRemnantImg
	 */
	public void setTotalRemnantImg(String totalRemnantImg) {
		this.totalRemnantImg = totalRemnantImg;
	}

	/**
	 * 成品序号
	 */
	private Integer exgSeqNum;

	/**
	 * 获取成品序号
	 * 
	 * @return exgSeqNum 成品序号
	 */

	public Integer getExgSeqNum() {
		return exgSeqNum;
	}

	/**
	 * 设置成品序号
	 * 
	 * @param exgSeqNum
	 *            成品序号
	 * 
	 */

	public void setExgSeqNum(Integer exgSeqNum) {
		this.exgSeqNum = exgSeqNum;
	}

	/**
	 * 获取分组
	 * 
	 * @return groupId 分组
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * 设置分组
	 * 
	 * @param groupId
	 *            分组
	 * 
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * 获取料件总表序号
	 * 
	 * @return seqMaterialNum 料件总表序号
	 */
	public String getSeqMaterialNum() {
		return seqMaterialNum;
	}

	/**
	 * 设置料件总表序号
	 * 
	 * @param seqMaterialNum
	 *            料件总表序号
	 * 
	 */
	public void setSeqMaterialNum(String ptNo) {
		this.seqMaterialNum = ptNo;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return ptName 料件名称
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param ptName
	 *            料件名称
	 * 
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 获取成品1
	 * 
	 * @return exg1 成品1
	 */
	public String getExg1() {
		return exg1;
	}

	/**
	 * 设置成品1
	 * 
	 * @param exg1
	 *            成品1
	 * 
	 */
	public void setExg1(String exg1) {
		this.exg1 = exg1;
	}

	/**
	 * 获取成品2
	 * 
	 * @return exg2 成品2
	 */
	public String getExg2() {
		return exg2;
	}

	/**
	 * 设置成品2
	 * 
	 * @param exg2
	 *            成品2
	 * 
	 */
	public void setExg2(String exg2) {
		this.exg2 = exg2;
	}

	/**
	 * 获取成品3
	 * 
	 * @return exg3 成品2
	 */
	public String getExg3() {
		return exg3;
	}

	/**
	 * 设置成品3
	 * 
	 * @param exg3
	 *            成品3
	 * 
	 */
	public void setExg3(String exg3) {
		this.exg3 = exg3;
	}

	/**
	 * 获取成品4
	 * 
	 * @return exg4 成品2
	 */
	public String getExg4() {
		return exg4;
	}

	/**
	 * 设置成品4
	 * 
	 * @param exg4
	 *            成品4
	 * 
	 */
	public void setExg4(String exg4) {
		this.exg4 = exg4;
	}

	/**
	 * 获取成品5
	 * 
	 * @return exg5 成品5
	 */
	public String getExg5() {
		return exg5;
	}

	/**
	 * 设置成品5
	 * 
	 * @param exg5
	 *            成品5
	 * 
	 */
	public void setExg5(String exg5) {
		this.exg5 = exg5;
	}

	/**
	 * 获取成品6
	 * 
	 * @return exg6 成品6
	 */
	public String getExg6() {
		return exg6;
	}

	/**
	 * 设置成品6
	 * 
	 * @param exg6
	 *            成品6
	 * 
	 */
	public void setExg6(String exg6) {
		this.exg6 = exg6;
	}

	/**
	 * 获取单耗1
	 * 
	 * @return unitWaste1 单耗1
	 */
	public String getUnitWaste1() {
		return unitWaste1;
	}

	/**
	 * 设置单耗1
	 * 
	 * @param unitWaste1
	 *            单耗1
	 * 
	 */
	public void setUnitWaste1(String unitWaste1) {
		this.unitWaste1 = unitWaste1;
	}

	/**
	 * 获取总耗1
	 * 
	 * @return totalWaste1 总耗1
	 */
	public String getTotalWaste1() {
		return totalWaste1;
	}

	/**
	 * 设置总耗1
	 * 
	 * @param totalWaste1
	 *            总耗1
	 * 
	 */
	public void setTotalWaste1(String totalWaste1) {
		this.totalWaste1 = totalWaste1;
	}

	/**
	 * 获取单耗2
	 * 
	 * @return unitWaste2 单耗2
	 */
	public String getUnitWaste2() {
		return unitWaste2;
	}

	/**
	 * 设置单耗2
	 * 
	 * @param unitWaste2
	 *            单耗2
	 * 
	 */
	public void setUnitWaste2(String unitWaste2) {
		this.unitWaste2 = unitWaste2;
	}

	/**
	 * 获取总耗2
	 * 
	 * @return totalWaste2 总耗2
	 */
	public String getTotalWaste2() {
		return totalWaste2;
	}

	/**
	 * 设置总耗2
	 * 
	 * @param totalWaste2
	 *            总耗2
	 * 
	 */
	public void setTotalWaste2(String totalWaste2) {
		this.totalWaste2 = totalWaste2;
	}

	/**
	 * 获取单耗3
	 * 
	 * @return unitWaste3 单耗3
	 */
	public String getUnitWaste3() {
		return unitWaste3;
	}

	/**
	 * 设置单耗3
	 * 
	 * @param unitWaste3
	 *            单耗3
	 * 
	 */
	public void setUnitWaste3(String unitWaste3) {
		this.unitWaste3 = unitWaste3;
	}

	/**
	 * 获取总耗3
	 * 
	 * @return totalWaste3 总耗3
	 */
	public String getTotalWaste3() {
		return totalWaste3;
	}

	/**
	 * 设置总耗3
	 * 
	 * @param totalWaste3
	 *            总耗3
	 * 
	 */
	public void setTotalWaste3(String totalWaste3) {
		this.totalWaste3 = totalWaste3;
	}

	/**
	 * 获取单耗4
	 * 
	 * @return unitWaste4 单耗4
	 */
	public String getUnitWaste4() {
		return unitWaste4;
	}

	/**
	 * 设置单耗4
	 * 
	 * @param unitWaste4
	 *            单耗4
	 * 
	 */
	public void setUnitWaste4(String unitWaste4) {
		this.unitWaste4 = unitWaste4;
	}

	/**
	 * 获取总耗4
	 * 
	 * @return totalWaste4 总耗4
	 */
	public String getTotalWaste4() {
		return totalWaste4;
	}

	/**
	 * 设置总耗4
	 * 
	 * @param totalWaste4
	 *            总耗4
	 * 
	 */
	public void setTotalWaste4(String totalWaste4) {
		this.totalWaste4 = totalWaste4;
	}

	/**
	 * 获取单耗5
	 * 
	 * @return unitWaste5 单耗5
	 */
	public String getUnitWaste5() {
		return unitWaste5;
	}

	/**
	 * 设置单耗5
	 * 
	 * @param unitWaste5
	 *            单耗5
	 * 
	 */
	public void setUnitWaste5(String unitWaste5) {
		this.unitWaste5 = unitWaste5;
	}

	/**
	 * 获取总耗5
	 * 
	 * @return totalWaste5 总耗5
	 */
	public String getTotalWaste5() {
		return totalWaste5;
	}

	/**
	 * 设置总耗5
	 * 
	 * @param totalWaste5
	 *            总耗5
	 * 
	 */
	public void setTotalWaste5(String totalWaste5) {
		this.totalWaste5 = totalWaste5;
	}

	/**
	 * 获取单耗6
	 * 
	 * @return unitWaste6 单耗6
	 */
	public String getUnitWaste6() {
		return unitWaste6;
	}

	/**
	 * 设置单耗6
	 * 
	 * @param unitWaste6
	 *            单耗6
	 * 
	 */
	public void setUnitWaste6(String unitWaste6) {
		this.unitWaste6 = unitWaste6;
	}

	/**
	 * 获取总耗6
	 * 
	 * @return totalWaste6 总耗
	 */
	public String getTotalWaste6() {
		return totalWaste6;
	}

	/**
	 * 设置总耗6
	 * 
	 * @param totalWaste6
	 *            总耗6
	 * 
	 */
	public void setTotalWaste6(String totalWaste6) {
		this.totalWaste6 = totalWaste6;
	}

	/**
	 * 获取料件规格
	 * 
	 * @return ptSpec 料件规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 设置料件规格
	 * 
	 * @param ptSpec
	 *            料件规格
	 * 
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 * 
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取总进数量
	 * 
	 * @return totalAmount 总进数量
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * 设置总进数量
	 * 
	 * @param totalAmount
	 *            总进数量
	 * 
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 获取备案数量
	 * 
	 * @return fileAmount 备案数量
	 */
	public String getFileAmount() {
		return fileAmount;
	}

	/**
	 * 设置备案数量
	 * 
	 * @param fileAmount
	 *            备案数量
	 * 
	 */
	public void setFileAmount(String fileAmount) {
		this.fileAmount = fileAmount;
	}

	/**
	 * 获取成品数量1
	 * 
	 * @return productAmount1 成品数量1
	 */
	public String getProductAmount1() {
		return productAmount1;
	}

	/**
	 * 获取成品数量2
	 * 
	 * @return productAmount2 成品数量2
	 */
	public String getProductAmount2() {
		return productAmount2;
	}

	/**
	 * 获取成品数量3
	 * 
	 * @return productAmount3 成品数量3
	 */
	public String getProductAmount3() {
		return productAmount3;
	}

	/**
	 * 获取成品数量4
	 * 
	 * @return productAmount4 成品数量4
	 */
	public String getProductAmount4() {
		return productAmount4;
	}

	/**
	 * 获取成品数量5
	 * 
	 * @return productAmount5 成品数量5
	 */
	public String getProductAmount5() {
		return productAmount5;
	}

	/**
	 * 获取成品数量6
	 * 
	 * @return productAmount6 成品数量6
	 */
	public String getProductAmount6() {
		return productAmount6;
	}

	/**
	 * 设置成品数量1
	 * 
	 * @param productAmount1
	 *            成品数量1
	 */
	public void setProductAmount1(String productAmount1) {
		this.productAmount1 = productAmount1;
	}

	/**
	 * 设置成品数量2
	 * 
	 * @param productAmount2
	 *            成品数量2
	 */
	public void setProductAmount2(String productAmount2) {
		this.productAmount2 = productAmount2;
	}

	/**
	 * 设置成品数量3
	 * 
	 * @param productAmount3
	 *            成品数量3
	 */
	public void setProductAmount3(String productAmount3) {
		this.productAmount3 = productAmount3;
	}

	/**
	 * 设置成品数量4
	 * 
	 * @param productAmount4
	 *            成品数量4
	 */
	public void setProductAmount4(String productAmount4) {
		this.productAmount4 = productAmount4;
	}

	/**
	 * 设置成品数量5
	 * 
	 * @param productAmount5
	 *            成品数量5
	 */
	public void setProductAmount5(String productAmount5) {
		this.productAmount5 = productAmount5;
	}

	/**
	 * 设置成品数量6
	 * 
	 * @param productAmount6
	 *            成品数量6
	 */
	public void setProductAmount6(String productAmount6) {
		this.productAmount6 = productAmount6;
	}

	/**
	 * 获取总耗合计
	 * 
	 * @return totalConsumption 总耗合计
	 */
	public String getTotalConsumption() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(5);
		nf.setMaximumIntegerDigits(10);
		nf.setGroupingUsed(false);
		Double[] result = new Double[2];
		result[0] = 0d;
		result[1] = 0d;
		add(totalWaste1, result);
		add(totalWaste2, result);
		add(totalWaste3, result);
		add(totalWaste4, result);
		add(totalWaste5, result);
		add(totalWaste6, result);
		if (unit == null || "千克".equals(unit)) {
			return nf.format(result[0]);
		}
		return nf.format(result[0]) + "/"
				+ nf.format(result[1]);
	}

	/**
	 * 字符串相加
	 * 
	 * @param str
	 * @param result
	 */
	public void add(String str, Double[] result) {
		String[] strs = str.split("/");
		for (int i = 0, size = strs.length; i < size; i++) {
			if (i == 0) {
				result[0] += getDoubleFromStr(strs[0]);
			}
			if (i == 1) {
				result[1] += getDoubleFromStr(strs[1]);
			}
		}
	}

	/**
	 * 字符串相减
	 * 
	 * @param str
	 * @param result
	 */

	public void del(String str, Double[] result) {
		String[] strs = str.split("/");
		for (int i = 0, size = strs.length; i < size; i++) {
			if (i == 0) {
				result[0] -= getDoubleFromStr(strs[0]);
			}
			if (i == 1) {
				result[1] -= getDoubleFromStr(strs[1]);
			}
		}
	}

	/**
	 * Str转为Double
	 * 
	 * @param str
	 * @return
	 */

	public Double getDoubleFromStr(String str) {
		Double result = null;
		try {
			result = Double.parseDouble(str);
		} catch (Exception e) {
			result = 0d;
		}
		return result;
	}

	/**
	 * 设置总耗合计
	 * 
	 * @param totalConsumption
	 *            总耗合计
	 */
	public void setTotalConsumption(String totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	/**
	 * 获取单位1
	 * 
	 * @return the unit1
	 */
	public String getUnit1() {
		return unit1;
	}

	/**
	 * 获取单位2
	 * 
	 * @return the unit2
	 */
	public String getUnit2() {
		return unit2;
	}

	/**
	 * 获取单位3
	 * 
	 * @return the unit3
	 */
	public String getUnit3() {
		return unit3;
	}

	/**
	 * 获取单位4
	 * 
	 * @return the unit4
	 */
	public String getUnit4() {
		return unit4;
	}

	/**
	 * 获取单位5
	 * 
	 * @return the unit5
	 */
	public String getUnit5() {
		return unit5;
	}

	/**
	 * 获取单位
	 * 
	 * @return the unit6
	 */
	public String getUnit6() {
		return unit6;
	}

	/**
	 * 设置单位1
	 * 
	 * @param unit1
	 *            单位1
	 * 
	 */
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	/**
	 * 设置单位2
	 * 
	 * @param unit2
	 *            单位2
	 * 
	 */
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit3
	 *            单位3
	 * 
	 */
	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}

	/**
	 * 设置单位4
	 * 
	 * @param unit4
	 *            单位4
	 * 
	 */
	public void setUnit4(String unit4) {
		this.unit4 = unit4;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit5
	 *            单位5
	 * 
	 */
	public void setUnit5(String unit5) {
		this.unit5 = unit5;
	}

	/**
	 * 设置单位6
	 * 
	 * @param unit6
	 *            单位6
	 * 
	 */
	public void setUnit6(String unit6) {
		this.unit6 = unit6;
	}

	/**
	 * 获取核销表头
	 * 
	 * @return contractCav 核销表头
	 */
	public ContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * 设置核销表头
	 * 
	 * @param contractCav
	 *            核销表头
	 */
	public void setContractCav(ContractCav contractCav) {
		this.contractCav = contractCav;
	}
}