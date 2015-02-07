package com.bestway.bcus.client.common;

import java.awt.Component;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.resource.IconSource;
import com.bestway.client.resource.ImageSource;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityException;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;

//一般常用变量
public class CommonVars {
	private static CommonVars commonVars = null;

	private static AclUser currUser;

	private static Integer projectType; // 存放三种模式的任一种

	private static ApplicationContext applicationContext = null;

	private static ImageSource imageSource = null;

	private static ImageSource rcpImageSource = null;

	private static IconSource iconSource = null;

	private static IconSource rcpIconSource = null;

	private static String emsFrom = null;

	private static Boolean istitleRow = null;

	private static ResourceBundleMessageSource messageSource = null;

	private static AuthorityAction authorityAction = null;

	private static Hashtable hashTableAcl = null;

	private static DBDataRoot dbRoot = null;

	private static CompanyOther other = null;// 参数设置

	private static Double sumMoney = Double.valueOf(0);// 报关单总金额

	private static String currName = null; // 币制名称

	private static Double beforeSumMoney = Double.valueOf(0);

	// BCS参数控制
	private static String contractFrom = null;

	private static String SpecialCustoms = null; // 特殊报关单来源

	private static Boolean unitWasteWriteBackExg = false; // 修改单耗时自动计算成品总额

	private static Boolean unitWasteWriteBackImg = false; // 修改单耗时自动计算料件总额

	private static Boolean isVailCustomsOther = false; // 报关单生效是否检查转关附加信息

	private static Boolean isCanModifyUnitPrice = false; // 进出口报关单的单价是否可以修改

	private static Boolean isPrintAll = false; // 进出口报关单打印随附单据是否打印全称

	public static Boolean getIsPrintAll() {
		return isPrintAll;
	}

	public static void setIsPrintAll(Boolean isPrintAll) {
		CommonVars.isPrintAll = isPrintAll;
	}

	private static int bomStructureType = -1;

	private static int isBigToGBK = 0;// 是否繁转简

	private static String serverName = "";

	private static String port = "";

	private static String ipAddress = null;

	// 报关单要允许输入超量的数据
	private static Boolean isCustomAmountOut = false;

	public static Boolean h2kMergerModifyNo = false; // 修改经营范围与归并关系的备案序号
	/**
	 * 允许多本备案资料库
	 */
	private static Boolean putOnRecords = false;

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		CommonVars.port = port;
	}

	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		CommonVars.serverName = serverName;
	}

	public static void setCurrUser(AclUser user) {
		currUser = user;
	}

	public static void setHashTableAcl(Hashtable acls) {
		hashTableAcl = acls;
	}

	public static AclUser getCurrUser() {
		if (currUser != null) {
			currUser.setCurrIP(getIpAddress());
		}
		return currUser;
	}

	public static Request getRequst() {
		return new Request(getCurrUser());
	}

	public static Hashtable getHashTableAcl() {
		return hashTableAcl;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext context) {

		Assert.notNull(context);

		applicationContext = context;

		setImageSource((ImageSource) context.getBean("imageSource"));

		setIconSource((IconSource) context.getBean("iconSource"));

		setRcpImageSource((ImageSource) context.getBean("rcpImageSource"));

		setRcpIconSource((IconSource) context.getBean("rcpIconSource"));

		setMessageSource((ResourceBundleMessageSource) context
				.getBean("messageSource"));
	}

	public static synchronized CommonVars getInstance() {
		if (commonVars == null) {
			commonVars = new CommonVars();
		}
		return commonVars;
	}

	/**
	 * 格式化Doublei (String ->Double)
	 */
	public static Double formatStrDouble(String dou) {
		if (dou != null && !dou.equals("")) {
			return new Double(dou);
		}
		return new Double(0);
	}

	public static void doLogoutClear() {
		currUser = null;
		hashTableAcl = null;
	}

	/**
	 * @return Returns the iconSource.
	 */
	public static IconSource getIconSource() {
		return iconSource;
	}

	/**
	 * @param iconSource
	 *            The iconSource to set.
	 */
	public static void setIconSource(IconSource iconSource1) {
		iconSource = iconSource1;
	}

	/**
	 * @return Returns the imageSource.
	 */
	public static ImageSource getImageSource() {
		return imageSource;
	}

	/**
	 * @param imageSource
	 *            The imageSource to set.
	 */
	public static void setImageSource(ImageSource imageSource1) {
		imageSource = imageSource1;
	}

	/**
	 * @return Returns the messageSource.
	 */
	public static ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource
	 *            The messageSource to set.
	 */
	public static void setMessageSource(
			ResourceBundleMessageSource messageSource1) {
		messageSource = messageSource1;
	}

	public static void checkPermission(String moduleName, String methodName) {
		if (authorityAction == null) {
			authorityAction = (AuthorityAction) applicationContext
					.getBean("authorityAction");
		}
		if (!authorityAction.checkPermission(new Request(currUser, moduleName,
				methodName)))
			throw new AuthorityException("没有新增权限");
	}

	/**
	 * @return Returns the rcpIconSource.
	 */
	public static IconSource getRcpIconSource() {
		return rcpIconSource;
	}

	/**
	 * @param rcpIconSource
	 *            The rcpIconSource to set.
	 */
	public static void setRcpIconSource(IconSource rcpIconSource1) {
		rcpIconSource = rcpIconSource1;
	}

	/**
	 * @return Returns the rcpImageSource.
	 */
	public static ImageSource getRcpImageSource() {
		return rcpImageSource;
	}

	/**
	 * @param rcpImageSource
	 *            The rcpImageSource to set.
	 */
	public static void setRcpImageSource(ImageSource rcpImageSource1) {
		rcpImageSource = rcpImageSource1;
	}

	/**
	 * date->日期型"HH:mm:ss"
	 * 
	 * @return
	 */
	public static Date dateToTime(Date date) {
		SimpleDateFormat bartTimeFormat = new SimpleDateFormat("HH:mm:ss");
		String defaultTime = bartTimeFormat.format(date);
		return java.sql.Time.valueOf(defaultTime);
	}

	/**
	 * date->字符型"HH:mm:ss"
	 * 
	 * @return
	 */
	public static String dateToTimeString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartTimeFormat = new SimpleDateFormat("HH:mm:ss");
		return bartTimeFormat.format(date);
	}

	/**
	 * date->日期型"yyyy-MM-dd"
	 */
	public static Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}

	/**
	 * now->日期型"yyyy-MM-dd"
	 */
	public static Date nowToStandDate() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(new Date());
		return java.sql.Date.valueOf(defaultDate);
	}

	/**
	 * 将now转化成字符型->"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String nowToDate() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		Date date1 = new Date();
		String str1 = formater.format(date1);
		return str1;
	}

	/**
	 * 将Date转化成字符型->"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String dateToDate(Date date1) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date1);
		return str1;
	}

	/**
	 * date->字符型"yyyy-MM-dd"
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(date);
	}

	/**
	 * String -->date
	 * 
	 * @param input
	 * @return
	 */
	public static Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Date getBeginDate() {
		String strNow = nowToDate();
		strNow = substring(strNow, 0, 8) + "01 00:00:00";
		return strToStandDate(strNow);
	}

	public static Date getNowDate() {
		String strNow = nowToDate();
		strNow = substring(strNow, 0, 10) + " 00:00:00";
		return strToStandDate(strNow);
	}

	/**
	 * 格式化Double（null->Double(0)）
	 * 
	 * @param dou
	 */
	public static Double formatDouble(Double dou) {
		if (dou != null) {
			return dou;
		} else {
			return new Double(0);
		}
	}

	/**
	 * if yy=null ->"":
	 * 
	 * @param yy
	 * @return
	 */
	public static String formatStr(String yy) {
		String xx = "";
		if (yy != null) {
			xx = yy;
		}
		return xx;
	}

	/**
	 * @return Returns the emsFrom.
	 */
	public static String getEmsFrom() {
		return emsFrom;
	}

	/**
	 * @param emsFrom
	 *            The emsFrom to set.
	 */
	public static void setEmsFrom(String emsFrom) {
		CommonVars.emsFrom = emsFrom;
	}

	/**
	 * @return Returns the istitleRow.
	 */
	public static Boolean getIstitleRow() {
		return istitleRow;
	}

	/**
	 * @param istitleRow
	 *            The istitleRow to set.
	 */
	public static void setIstitleRow(Boolean istitleRow) {
		CommonVars.istitleRow = istitleRow;
	}

	public static String getMainDay() {
		String noon = "";
		Date time = new Date();
		int hour = time.getHours();
		String week[] = new String[7];
		Date today = new Date();
		week[0] = "天";
		week[1] = "一";
		week[2] = "二";
		week[3] = "三";
		week[4] = "四";
		week[5] = "五";
		week[6] = "六";
		if (hour < 5)
			noon = "凌晨";
		if (hour > 4 & hour < 8)
			noon = "早上";
		if (hour > 7 & hour < 12)
			noon = "上午";
		if (hour == 12)
			noon = "中午";
		if (hour > 12 & hour < 19)
			noon = "下午";
		if (hour > 18 & hour < 23)
			noon = "晚上";
		if (hour > 22)
			noon = "深夜";
		return CommonVars.getCurrUser().getUserName() + "(先生/小姐): " + noon
				+ "好 " + dateToString(new Date()) + " 星期"
				+ week[today.getDay()];
	}

	public static String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start) {
			return new String(str.getBytes(), start, str.getBytes().length
					- start);
		}
		return "";
	}

	public static Date getDateByHgDate(String xx) {
		if (xx == null || xx.equals("") || xx.equals("000000")) {
			return null;
		}
		String nian = "20" + substring(xx, 0, 2) + "-" + substring(xx, 2, 4)
				+ "-" + substring(xx, 4, 6) + " 00:00:00";
		return strToStandDate(nian);
	}

	/**
	 * @return Returns the dbRoot.
	 */
	public static DBDataRoot getDbRoot() {
		return dbRoot;
	}

	/**
	 * @param dbRoot
	 *            The dbRoot to set.
	 */
	public static void setDbRoot(DBDataRoot dbRoot) {
		CommonVars.dbRoot = dbRoot;
	}

	public static String getImpExpTypeName(String impExpType) {
		String str = "";
		if (impExpType != null && !"".equals(impExpType)) {
			switch (Integer.parseInt(impExpType.toString())) {
			case ImpExpType.DIRECT_IMPORT: // 0
				str = "料件进口";
				break;
			case ImpExpType.TRANSFER_FACTORY_IMPORT: // 1
				str = "料件转厂";
				break;
			case ImpExpType.BACK_FACTORY_REWORK: // 2
				str = "退厂返工";
				break;
			case ImpExpType.GENERAL_TRADE_IMPORT: // 3
				str = "一般贸易进口";
				break;
			case ImpExpType.DIRECT_EXPORT: // 4
				str = "成品出口";
				break;
			case ImpExpType.TRANSFER_FACTORY_EXPORT: // 5
				str = "转厂出口";
				break;
			case ImpExpType.BACK_MATERIEL_EXPORT: // 6
				str = "退料出口";
				break;
			case ImpExpType.REWORK_EXPORT: // 7
				str = "返工复出";
				break;
			case ImpExpType.REMIAN_MATERIAL_BACK_PORT: // 8
				str = "边角料退港";
				break;
			case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES: // 9
				str = "边角料内销";
				break;
			case ImpExpType.GENERAL_TRADE_EXPORT: // 10
				str = "一般贸易出口";
				break;
			case ImpExpType.EQUIPMENT_IMPORT: // 11
				str = "设备进口";
				break;
			case ImpExpType.BACK_PORT_REPAIR: // 12
				str = "退港维修";
				break;
			case ImpExpType.EQUIPMENT_BACK_PORT: // 13
				str = "设备退港";
				break;
			case ImpExpType.REMAIN_FORWARD_EXPORT: // 14
				str = "余料结转出口";
				break;
			case ImpExpType.EXPORT_STORAGE: // 15
				str = "出口仓储";
				break;
			case ImpExpType.IMPORT_STORAGE: // 16
				str = "进口仓储";
				break;
			case ImpExpType.MATERIAL_DOMESTIC_SALES: // 17
				str = "海关批准内销";
				break;
			case ImpExpType.MATERIAL_EXCHANGE: // 18
				str = "料件退换";
				break;
			case ImpExpType.MATERIAL_REOUT: // 19
				str = "料件复出";
				break;
			case ImpExpType.REMAIN_FORWARD_IMPORT: // 21
				str = "余料转入";
				break;
			case ImpExpType.IMPORT_EXG_BACK:// 25
				str = "进料成品退换";
				break;
			case ImpExpType.IMPORT_REPAIR_MATERIAL:// 26
				str = "修理物品";
				break;
			case ImpExpType.EXPORT_MATERIAL_REBACK:// 27
				str = "修理物品复出";
				break;
			}
		}
		return str;
	}

	/**
	 * 取得外汇核销单的状态
	 * 
	 * @param state
	 * @return
	 */
	public static String getFecavState(int state) {
		String str = "";
		switch (state) {
		case FecavState.OUTER_OBTAIN:
			str = "外部领用";
			break;
		case FecavState.INNER_OBTAIN:
			str = "内部领用";
			break;
		case FecavState.USED:
			str = "核销单使用";
			break;
		case FecavState.DEBENTURE_SIGN_IN:
			str = "退税联签收";
			break;
		case FecavState.HAND_IN_BILL:
			str = "核销员交单";
			break;
		case FecavState.CONTROL:
			str = "核销单管制";
			break;
		case FecavState.STRIKE_BALANCE:
			str = "核销单冲销";
			break;
		case FecavState.CANCEL:
			str = "核销单核销";
			break;
		case FecavState.FINANCE_SIGN_IN:
			str = "核销单财务签收";
			break;
		case FecavState.CLOSE_ACCOUNT:
			str = "核销单关帐";
			break;
		}
		return str;
	}

	/**
	 * 格式化double的小数位数
	 * 
	 * @param sourceDouble
	 * @param n
	 * @return
	 */
	public static Double getDoubleByDigit(Double sourceDouble, int n) {
		if (sourceDouble == null) {
			return 0.0;
		}
		BigDecimal b = new BigDecimal(sourceDouble);
		return b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将为空的Double转换成0.0
	 * 
	 * @param d
	 * @return static Double
	 */
	public static Double getDoubleExceptNull(Double d) {
		return (d == null ? 0.0 : d);
	}

	public static String formatDoubleToString(Double d) {
		if (d == null) {
			return "";
		}
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(999);
		format.setMaximumFractionDigits(999);
		return format.format(d).toString();
	}

	public static String formatDoubleToString(Double d, int groupSize,
			int digitsSize) {
		if (d == null) {
			return "";
		}
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(groupSize);
		format.setMaximumFractionDigits(digitsSize);
		return format.format(d).toString();
	}

	/**
	 * @return Returns the other.
	 */
	public static CompanyOther getOther() {
		return other;
	}

	/**
	 * @param other
	 *            The other to set.
	 */
	public static void setOther(CompanyOther other) {
		CommonVars.other = other;
	}

	/**
	 * @return Returns the contractFrom.
	 */
	public static String getContractFrom() {
		return contractFrom;
	}

	/**
	 * @param contractFrom
	 *            The contractFrom to set.
	 */
	public static void setContractFrom(String contractFrom) {
		CommonVars.contractFrom = contractFrom;
	}

	public static Double getSumMoney() {
		return sumMoney;
	}

	public static void setSumMoney(Double sumMoney) {
		CommonVars.sumMoney = sumMoney;
	}

	public static Boolean getUnitWasteWriteBackExg() {
		return unitWasteWriteBackExg;
	}

	public static void setUnitWasteWriteBackExg(Boolean unitWasteWriteBackExg) {
		CommonVars.unitWasteWriteBackExg = unitWasteWriteBackExg;
	}

	public static Boolean getUnitWasteWriteBackImg() {
		return unitWasteWriteBackImg;
	}

	public static void setUnitWasteWriteBackImg(Boolean unitWasteWriteBackImg) {
		CommonVars.unitWasteWriteBackImg = unitWasteWriteBackImg;
	}

	/**
	 * @return Returns the isVailCustomsOther.
	 */
	public static Boolean getIsVailCustomsOther() {
		return isVailCustomsOther;
	}

	/**
	 * @param isVailCustomsOther
	 *            The isVailCustomsOther to set.
	 */
	public static void setIsVailCustomsOther(Boolean isVailCustomsOther) {
		CommonVars.isVailCustomsOther = isVailCustomsOther;
	}

	/**
	 * @return Returns the isCanModifyUnitPrice.
	 */
	public static Boolean getIsCanModifyUnitPrice() {
		return isCanModifyUnitPrice;
	}

	/**
	 * @param isCanModifyUnitPrice
	 *            The isCanModifyUnitPrice to set.
	 */
	public static void setIsCanModifyUnitPrice(Boolean isCanModifyUnitPrice) {
		CommonVars.isCanModifyUnitPrice = isCanModifyUnitPrice;
	}

	/**
	 * @return Returns the beforeSumMoney.
	 */
	public static Double getBeforeSumMoney() {
		return beforeSumMoney;
	}

	/**
	 * @param beforeSumMoney
	 *            The beforeSumMoney to set.
	 */
	public static void setBeforeSumMoney(Double beforeSumMoney) {
		CommonVars.beforeSumMoney = beforeSumMoney;
	}

	public static int getBomStructureType() {
		return bomStructureType;
	}

	public static void setBomStructureType(int bomStructureType) {
		CommonVars.bomStructureType = bomStructureType;
	}

	/**
	 * @return Returns the currName.
	 */
	public static String getCurrName() {
		return currName;
	}

	/**
	 * @param currName
	 *            The currName to set.
	 */
	public static void setCurrName(String currName) {
		CommonVars.currName = currName;
	}

	public static void setIsBigToGBK(int isBigToGBK) {
		CommonVars.isBigToGBK = isBigToGBK;
	}

	public static int getIsBigToGBK() {
		return isBigToGBK;
	}

	public static Date getBeginDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		// calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, -1);
		// return calendar.getTime();
		return getEndDate(calendar.getTime());
	}

	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Double getEmsImgPrice(EmsHeadH2kImg img) {
		Double factoryPrice = img.getFactoryPrice();
		Double declarePrice = img.getDeclarePrice();
		Date beginDate = img.getBeginDate();
		Date endDate = img.getEndDate();
		Date nowDate = new Date();
		if (factoryPrice == null || Double.valueOf(0).equals(factoryPrice)
				|| beginDate == null || endDate == null) {
			return declarePrice;
		} else if ((nowDate.after(beginDate) && endDate.after(nowDate))
				|| (beginDate.equals(nowDate) || endDate.equals(nowDate))) {
			return factoryPrice;
		}
		return declarePrice;
	}

	public static Double getEmsExgPrice(EmsHeadH2kVersion versionObj) {
		Double factoryPrice = versionObj.getFactoryPrice();
		Double declarePrice = versionObj.getEmsHeadH2kExg().getDeclarePrice();
		Date beginDate = versionObj.getBeginDate();
		Date endDate = versionObj.getEndDate();
		Date nowDate = new Date();
		if (factoryPrice == null || Double.valueOf(0).equals(factoryPrice)
				|| beginDate == null || endDate == null) {
			return declarePrice;
		} else if ((nowDate.after(beginDate) && endDate.after(nowDate))
				|| (beginDate.equals(nowDate) || endDate.equals(nowDate))) {
			return factoryPrice;
		}
		return declarePrice;
	}

	public static Boolean getIsCustomAmountOut() {
		return isCustomAmountOut;
	}

	public static void setIsCustomAmountOut(Boolean isCustomAmountOut) {
		CommonVars.isCustomAmountOut = isCustomAmountOut;
	}

	/**
	 * 获得三种模式之一 当为空时 为全部
	 * 
	 * @return
	 */
	public static Integer getProjectType() {
		return projectType;
	}

	/**
	 * 设置三种模式
	 * 
	 * @param projectType
	 */
	public static void setProjectType(Integer projectType) {
		CommonVars.projectType = projectType;
	}

	public static int getCustomsType(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		int projectType = ProjectType.BCUS;
		if (baseCustomsDeclaration instanceof CustomsDeclaration) {
			projectType = ProjectType.BCUS;
		} else if (baseCustomsDeclaration instanceof BcsCustomsDeclaration) {
			projectType = ProjectType.BCS;
		} else if (baseCustomsDeclaration instanceof DzscCustomsDeclaration) {
			projectType = ProjectType.DZSC;
		}
		return projectType;
	}

	public static boolean isCompany(String companyName) {
		String name = CommonVars.getCurrUser().getCompany().getName();
		if (name.indexOf(companyName) >= 0) {
			return true;
		}
		return false;
	}

	public static String getIpAddress() {
		return ipAddress;
	}

	public static void setIpAddress(String ipAddress) {
		CommonVars.ipAddress = ipAddress;
	}

	public static boolean isManager() {
		AclUser user = CommonVars.getCurrUser();
		String isManager = user.getUserFlag() == null ? "" : user.getUserFlag();
		if (isManager.equals("S")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将以{split}分割的字符串拆分成数组
	 * 
	 * @param text
	 * @param split
	 * @return
	 */
	public static Integer[] getIntegerArrayBySplit(String text, String split) {
		if (text == null) {
			return null;
		}
		String[] strArray = text.trim().split(split);
		List<Integer> lsTemp = new ArrayList<Integer>();
		for (int i = 0; i < strArray.length; i++) {
			if (strArray[i] != null && !"".equals(strArray[i].trim())) {
				if (NumberUtils.isNumber(strArray[i])) {
					lsTemp.add(Integer.parseInt(strArray[i]));
				}
			}
		}
		Integer[] intArray = new Integer[lsTemp.size()];
		for (int i = 0; i < lsTemp.size(); i++) {
			intArray[i] = lsTemp.get(i);
		}
		return intArray;
	}

	/**
	 * 实现加密,将字符串{myinfo}按照加密类型{digestType}进行加密
	 * 
	 * @param digestType
	 *            加密类型
	 * @param myinfo
	 *            被加密信息
	 * @return 加密后信息
	 */
	public static String messageDigest(String digestType, String myinfo) {
		if (digestType == null) {
			return myinfo;
		}
		String secType = null;
		if (digestType.equals("SHA-512")) {
			secType = "SHA-512";
		} else if (digestType.equals("MD5")) {
			secType = "MD5";
		} else if (digestType.equals("SHA-1")) {
			secType = "SHA-1";
		} else if (digestType.equals("SHA-256")) {
			secType = "SHA-256";
		} else {
			return myinfo;
		}
		String retStr = myinfo;
		try {
			java.security.MessageDigest alga = java.security.MessageDigest
					.getInstance(secType);
			alga.update(myinfo.getBytes());
			byte[] digesta = alga.digest();
			// System.out.println("本信息摘要是:{" + secType + "}" +
			// byte2hex(digesta));
			retStr = "{" + secType + "}" + byte2hex(digesta);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return retStr;

		// String secType = "SHA-512"; // "MD5" "SHA-1" "SHA-256" "SHA-512"
	}

	private static String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static boolean commonCheckNull(Component parentComponent, Object obj) {
		System.out.println("-- obj.getClass:" + obj.getClass().getName());
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		String info = systemAction.commonCheckNull(
				new Request(CommonVars.getCurrUser()), obj);
		if (info != null) {
			JOptionPane.showMessageDialog(parentComponent, info, "提示", 2);
			return true;
		}
		return false;
	}

	public static Boolean getPutOnRecords() {
		return putOnRecords;
	}

	public static void setPutOnRecords(Boolean putOnRecords) {
		CommonVars.putOnRecords = putOnRecords;
	}

	/**
	 * 根据小数位的长度,重新计算截断后的值
	 * 
	 * @param value
	 *            乘积
	 * @param fValue
	 *            乘数1
	 * @param sValue
	 *            乘数2
	 * @param decimalLength
	 *            小数位的长度
	 * @return
	 */
	private static String multiplicationValue(Double value, Object fValue,
			Object sValue, int decimalLength) {
		if (fValue == null || sValue == null)
			return "0";
		String s = "";
		for (int i = 0; i < decimalLength; i++)
			s = s + "#";
		DecimalFormat df = new DecimalFormat("#." + s);
		try {
			if (value != null) {
				Double fv = Double.valueOf(df.format(Double
						.valueOf((String) fValue)));
				Double sv = Double.valueOf(df.format(Double
						.valueOf((String) sValue)));
				value = fv * sv;
				return df.format(value.doubleValue());
			} else {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 用来重新计算两个截取小数点后的相乘值
	 * 
	 * @param jTable
	 * @param multiplicationColNum
	 *            乘积的列数
	 * @param fColNum
	 *            乘数1的列数
	 * @param sColNum
	 *            乘数2的列数
	 * @param decimalLength
	 *            小数位的长度
	 */
	public static void castMultiplicationValue(final JTable jTable,
			int multiplicationColNum, final int fColNum, final int sColNum,
			final int decimalLength) {
		jTable.getColumnModel().getColumn(multiplicationColNum)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (other != null) {
							if (other.getIsAutoshowThousandthsign() == null ? false
									: other.getIsAutoshowThousandthsign()) {
								double value0 = 0.0;
								Object value1 = null;
								Object value2 = null;
								try {
									value0 = new DecimalFormat().parse(
											(String) (value == null ? "0"
													: value)).doubleValue();
									value1 = new DecimalFormat()
											.parse((String) (jTable.getModel()
													.getValueAt(row, fColNum) == null ? "0"
													: jTable.getModel()
															.getValueAt(row,
																	fColNum)))
											.doubleValue();
									value2 = new DecimalFormat()
											.parse((String) (jTable.getModel()
													.getValueAt(row, sColNum) == null ? "0"
													: jTable.getModel()
															.getValueAt(row,
																	sColNum)))
											.doubleValue();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								DecimalFormat nf = new DecimalFormat();
								nf.setMaximumFractionDigits(decimalLength);
								nf.setDecimalSeparatorAlwaysShown(true);
								super.setText(nf.format(Double
										.parseDouble(multiplicationValue(
												value0, String.valueOf(value1),
												String.valueOf(value2),
												decimalLength))));
							}
						} else {
							super.setText((value == null) ? "0"
									: multiplicationValue(
											Double.parseDouble((String) value),
											jTable.getModel().getValueAt(row,
													fColNum), jTable.getModel()
													.getValueAt(row, sColNum),
											decimalLength));
						}
						return this;
					}
				});
	}

	public static String getSpecialCustoms() {
		return SpecialCustoms;
	}

	public static void setSpecialCustoms(String specialCustoms) {
		CommonVars.SpecialCustoms = specialCustoms;
	}

	public static Boolean getH2kMergerModifyNo() {
		return h2kMergerModifyNo;
	}

	public static void setH2kMergerModifyNo(Boolean mergerModifyNo) {
		CommonVars.h2kMergerModifyNo = mergerModifyNo;
	}

}
