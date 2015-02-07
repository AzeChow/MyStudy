/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.warning.entity;

import java.io.Serializable;

/**
 * 预警信息标示
 * 
 * @author ls
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WarningItemFlag implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 电子手册合同到期 <= X 天提醒 */
	@WarningItemAnnotation(caption = "电子手册合同到期天数小于或等于 X 天的时候提醒")
	public static final int DZSC_CONTRACT_AT_TERM = 0;

	/** 电子手册核销到期 <= X 天提醒 */
	@WarningItemAnnotation(caption = "电子手册核销到期天数小于或等于 X 天的时候提醒")
	public static final int DZSC_CHECKCANCEL_AT_TERM = 1;

	/** 电子手册合同料件 >= -X 时提醒 */
	@WarningItemAnnotation(caption = "电子手册合同备案料件数量小于成品耗用数量 X 的时候提醒")
	public static final int DZSC_CONTRACT_MATERIEL = 5;

	/** 电子手册已转未送 >= X 时提醒 */
	@WarningItemAnnotation(caption = "电子手册已转未送数量大于或等于 X 的时候提醒")
	public static final int DZSC_OUT_CUSTOM_NOT_FACTORY = 8;

	/** 电子帐册核销到期 <= X 天提醒 */
	@WarningItemAnnotation(caption = "电子帐册核销到期天数小于或等于 X 天的时候提醒")
	public static final int BCUS_CHECKCANCEL_AT_TERM = 2;

	/** 电子帐册已送未转 >= X 时提醒 */
	@WarningItemAnnotation(caption = "电子帐册已送未转数量大于或等于 X 的时候提醒")
	public static final int BCUS_OUT_FACTORY_NOT_CUSTOM = 3;

	/** 电子帐册已转未送 >= X 时提醒 */
	@WarningItemAnnotation(caption = "电子帐册已转未送数量大于或等于 X 的时候提醒")
	public static final int BCUS_OUT_CUSTOM_NOT_FACTORY = 4;

	/** 电子手册已送未转 >= X 时提醒 */
	@WarningItemAnnotation(caption = "电子手册已送未转数量大于或等于 X 的时候提醒")
	public static final int DZSC_OUT_FACTORY_NOT_CUSTOM = 7;

	/** 电子化手册合同料件 >= -X 时提醒 */
	@WarningItemAnnotation(caption = "电子化手册合同备案料件数量小于成品耗用数量 X 的时候提醒")
	public static final int BCS_CONTRACT_MATERIEL = 6;

	/** 电子化手册已送未转 >= X 时提醒 */
	@WarningItemAnnotation(caption = "电子化手册已送未转数量大于或等于 X 的时候提醒")
	public static final int BCS_OUT_FACTORY_NOT_CUSTOM = 9;

	/** 电子化手册已转未送 >= X 时提醒 */
	@WarningItemAnnotation(caption = "电子化手册已转未送数量大于或等于 X 的时候提醒")
	public static final int BCS_OUT_CUSTOM_NOT_FACTORY = 10;

	/** 电子化手册合同到期 <= X 天提醒 */
	@WarningItemAnnotation(caption = "电子化手册合同到期天数小于或等于 X 天的时候提醒")
	public static final int BCS_CONTRACT_AT_TERM = 13;

	/** 电子化手册核销到期 <= X 天提醒 */
	@WarningItemAnnotation(caption = "电子化手册核销到期天数小于或等于 X 天的时候提醒")
	public static final int BCS_CHECKCANCEL_AT_TERM = 14;

	@WarningItemAnnotation(caption = "出口报关单大于或等于 X 天还没有收到出口退税联时提醒")
	public static final int FECAVBILL_NO_EXPORT_DRAWBACK_MATERIEL = 12;

	/** 电子化手册已转未送 >= X 时提醒 */
	@WarningItemAnnotation(caption = "设备监管到期前天数小于或等于 X 天的时候提醒")
	public static final int FIXTURE_CUSTOM_OVERSEE = 11;
	/** 电子化手册已转未送 >= X 时提醒 */
	@WarningItemAnnotation(caption = "对帐册中成品版本超过540天未使用的检查,当超过(540-X) 天的时候提醒")
	public static final int BCUS_EmsHeadH2kVersion_MORE_540_DAYS = 15;
    
	/**电子化手册实际增值率低于设置合同增值率X时候提醒*/
	@WarningItemAnnotation(caption = "电子化手册实际增值率低于设置合同增值率X时候提醒")
	public static final int BCUS_Value_Added_Rate = 16;
	/**电子化手册进出口报关单返工复出的时间要在退厂返工后X天复出的时候提醒*/
	@WarningItemAnnotation(caption = "电子化手册进出口报关单返工复出的时间要在退厂返工后X天复出的时候提醒")
	public static final int BCS_IE_REWORK_BACK = 17;
	/**电子帐册进出口报关单返工复出的时间要在退厂返工后X天复出的时候提醒*/
	@WarningItemAnnotation(caption = "电子帐册进出口报关单返工复出的时间要在退厂返工后X天复出的时候提醒")
	public static final int BCUS_IE_REWORK_BACK = 18;
	/**电子手册进出口报关单返工复出的时间要在退厂返工后X天复出的时候提醒*/
	@WarningItemAnnotation(caption = "电子手册进出口报关单返工复出的时间要在退厂返工后X天复出的时候提醒")//进出口报关单退厂返工与返工复出条件提醒1:退厂返工的数量和金额与返工复出的一致2:返工复出的时间要在退厂返工后X天复出
	public static final int DZSC_IE_REWORK_BACK = 19;
}
