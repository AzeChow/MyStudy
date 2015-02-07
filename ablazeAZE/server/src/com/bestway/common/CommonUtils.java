/*
 * Created on 2004-7-2
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.cfg.Configuration;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.context.ApplicationContext;

import com.bestway.bcus.CustomNamingStrategy;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.BaseCompany;

/**
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonUtils {
	private static ApplicationContext context = null;

	private static ThreadLocal thread = new ThreadLocal();

	private static ThreadLocal threadIp = new ThreadLocal();

	private static String sendDir = null;

	private static String recvDir = null;

	private static String recvBillDir = null;

	private static String finallyDir = null;

	private static String logDir = null;

	private static String sendBgd = null;

	private static String recvBgd = null;

	private static String finallyBgd = null;

	private static String logBgd = null;

	private static String year = null;

	private static String isBigToGBK = null;

	private static String txtInput = null;

	private static String txtInputFinally = null;

	private static String txtInputFail = null;

	private static String txtInputLog = null;

	private static Boolean istitleRow = null;

	public static int inputRow = 0;

	public static int pageLength = 100;

	public static CompanyOther other = null; // 公司其他参数设置

	private static ThreadLocal threadServerIp = new ThreadLocal();

	private static ThreadLocal threadPort = new ThreadLocal();
	/**
	 * 数据导入锁
	 */
	private static boolean importDataLock = false;

	/**
	 * 产生报关单流水号的同步锁对象
	 */
	public static Object localForPreCustomsDeclarationCode = new Object();

	/**
	 * 是否强制用[报关单单价]修改[单据中的报关单价]
	 */
	public static boolean isUpdateUnitPrice = true;

	/**
	 * 海关帐单据对应控制状态对象
	 */
	public static BillCorrespondingControl billCorrespondingControl = null;

	public static Configuration configuration = null;

	private static final long serialVersionUID = 3689913980965500976L;

	private static SessionFactoryImpl sessionFactoryImpl = null;
	/**
	 * 是否正在加载SessionFactory
	 */
	private static boolean isLoadingSessionFactory = false;

	public synchronized static boolean isImportDataLock() {
		return importDataLock;
	}

	public synchronized static void setImportDataLock(boolean importDataLock) {
		CommonUtils.importDataLock = importDataLock;
	}

	public static SessionFactoryImpl getSessionFactoryImpl() {
		return sessionFactoryImpl;
	}

	public static void setSessionFactoryImpl(
			SessionFactoryImpl sessionFactoryImpl) {
		CommonUtils.sessionFactoryImpl = sessionFactoryImpl;
	}

	public static boolean isLoadingSessionFactory() {
		return isLoadingSessionFactory;
	}

	public static void setLoadingSessionFactory(boolean isLoadingSessionFactory) {
		CommonUtils.isLoadingSessionFactory = isLoadingSessionFactory;
	}

	public static Request getRequest() {

		if (thread.get() == null) {
			return null;
		}
		return (Request) thread.get();
	}

	public static void setIp(String ip) {
		threadIp.set(ip);
	}

	public static String getIp() {

		if (threadIp.get() == null) {
			return null;
		}
		return (String) threadIp.get();
	}

	public static void setRequest(Request request) {
		thread.set(request);
	}

	public static BaseCompany getCompany() {
		if (getRequest() == null) {
			return null;
		} else {
			/*
			 * if(!getRequest().getUser().getCompany().isValid()) throw new
			 * RuntimeException
			 * ("数据错误：公司资料不全，公司名称为"+getRequest().getUser().getCompany
			 * ().getName());
			 */
			return getRequest().getUser().getCompany();
		}
	}

	public static AclUser getAclUser() {
		if (getRequest() == null) {
			return null;
		} else {
			/*
			 * if(!getRequest().getUser().getCompany().isValid()) throw new
			 * RuntimeException
			 * ("数据错误：公司资料不全，公司名称为"+getRequest().getUser().getCompany
			 * ().getName());
			 */
			return getRequest().getUser();
		}
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext ctxt) {
		context = ctxt;
	}

	/**
	 * @return Returns the finallyDir.
	 */
	public static String getFinallyDir() {
		return finallyDir;
	}

	/**
	 * @param finallyDir
	 *            The finallyDir to set.
	 */
	public static void setFinallyDir(String finallyDir) {
		CommonUtils.finallyDir = finallyDir;
	}

	/**
	 * @return Returns the logDir.
	 */
	public static String getLogDir() {
		return logDir;
	}

	/**
	 * @param logDir
	 *            The logDir to set.
	 */
	public static void setLogDir(String logDir) {
		CommonUtils.logDir = logDir;
	}

	/**
	 * @return Returns the recvDir.
	 */
	public static String getRecvDir() {
		return recvDir;
	}

	/**
	 * @param recvDir
	 *            The recvDir to set.
	 */
	public static void setRecvDir(String recvDir) {
		CommonUtils.recvDir = recvDir;
	}

	public static String getRecvBillDir() {
		return recvBillDir;
	}

	public static void setRecvBillDir(String recvBillDir) {
		CommonUtils.recvBillDir = recvBillDir;
	}

	/**
	 * @return Returns the sendDir.
	 */
	public static String getSendDir() {
		return sendDir;
	}

	/**
	 * @param sendDir
	 *            The sendDir to set.
	 */
	public static void setSendDir(String sendDir) {
		CommonUtils.sendDir = sendDir;
	}

	/**
	 * @return Returns the finallyBgd.
	 */
	public static String getFinallyBgd() {
		return CommonUtils.getFinallyDir() + "/finallyBgd";
	}

	/**
	 * @param finallyBgd
	 *            The finallyBgd to set.
	 */
	public static void setFinallyBgd(String finallyBgd) {
		CommonUtils.finallyBgd = finallyBgd;
	}

	/**
	 * @return Returns the logBgd.
	 */
	public static String getLogBgd() {
		return CommonUtils.getLogDir() + "/logBgd";
	}

	/**
	 * @param logBgd
	 *            The logBgd to set.
	 */
	public static void setLogBgd(String logBgd) {
		CommonUtils.logBgd = logBgd;
	}

	/**
	 * @return Returns the recvBgd.
	 */
	public static String getRecvBgd() {
		return CommonUtils.getRecvDir() + "/recvBgd";
	}

	/**
	 * @param recvBgd
	 *            The recvBgd to set.
	 */
	public static void setRecvBgd(String recvBgd) {
		CommonUtils.recvBgd = recvBgd;
	}

	/**
	 * @return Returns the sendBgd.
	 */
	public static String getSendBgd() {
		return CommonUtils.getSendDir() + "/sendBgd";
	}

	/**
	 * @param sendBgd
	 *            The sendBgd to set.
	 */
	public static void setSendBgd(String sendBgd) {
		CommonUtils.sendBgd = sendBgd;
	}

	/**
	 * @return Returns the year.
	 */
	public static String getYear() {
		if (year == null || year.equals("")) {
			return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		}
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public static void setYear(String year) {
		CommonUtils.year = year;
	}

	/**
	 * @return Returns the txtInput.
	 */
	public static String getTxtInput() {
		return txtInput;
	}

	/**
	 * @param txtInput
	 *            The txtInput to set.
	 */
	public static void setTxtInput(String txtInput) {
		CommonUtils.txtInput = txtInput;
	}

	/**
	 * @return Returns the txtInputFail.
	 */
	public static String getTxtInputFail() {
		return txtInputFail;
	}

	/**
	 * @param txtInputFail
	 *            The txtInputFail to set.
	 */
	public static void setTxtInputFail(String txtInputFail) {
		CommonUtils.txtInputFail = txtInputFail;
	}

	/**
	 * @return Returns the txtInputFinally.
	 */
	public static String getTxtInputFinally() {
		return txtInputFinally;
	}

	/**
	 * @param txtInputFinally
	 *            The txtInputFinally to set.
	 */
	public static void setTxtInputFinally(String txtInputFinally) {
		CommonUtils.txtInputFinally = txtInputFinally;
	}

	/**
	 * @return Returns the txtInputLog.
	 */
	public static String getTxtInputLog() {
		return txtInputLog;
	}

	/**
	 * @param txtInputLog
	 *            The txtInputLog to set.
	 */
	public static void setTxtInputLog(String txtInputLog) {
		CommonUtils.txtInputLog = txtInputLog;
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
		CommonUtils.istitleRow = istitleRow;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return Returns the inputRow.
	 */
	public static int getInputRow() {
		return inputRow;
	}

	/**
	 * @param inputRow
	 *            The inputRow to set.
	 */
	public static void setInputRow(int inputRow) {
		CommonUtils.inputRow = inputRow;
	}

	/**
	 * 取得系统中table的所有者
	 * 
	 * @return
	 */
	public static String getTableOwner() {
		CustomNamingStrategy customNamingStrategy = (CustomNamingStrategy) context
				.getBean("customNamingStrategy");
		return customNamingStrategy.getTableOwner();
	}

	/**
	 * 根据Bean的名称，从Spring的上下文中取出已注入的Bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBeanInApplicationContext(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 将一数字转换成一定长度的字符串，长度不够时，在前面加“0”
	 * 
	 * @param num
	 * @param length
	 * @return
	 */
	public static String convertIntToStringByLength(Integer num, Integer length) {
		if (num == null) {
			return "";
		}
		String s = String.valueOf(num);
		if (length == null) {
			return s;
		}
		int iCount = length - s.length();
		for (int i = 0; i < iCount; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 获得这一天的开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeginDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		// calendar.setTime(getEndDate(calendar.getTime()));
		// calendar.add(Calendar.SECOND, 1);
		// return calendar.getTime();
		// getEndDate(calendar.getTime());
		return getEndDate(calendar.getTime());
	}

	/**
	 * 获得这一天的开始日期
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date getBeginDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -1);
		// calendar.set(Calendar.MILLISECOND, 0);
		// return calendar.getTime();
		return getEndDate(calendar.getTime());
	}

	/**
	 * 获得这一天的结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获得这一天的结束日期
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date getEndDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
    /**
     * 获得当月的最后一天日期
     * @param date
     * @return
     */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	/**
     * 获得当月的最后一天日期
     * @param date
     * @return
     */
	public static Date lastDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	/**
     * 获得当月的第一天日期
     * @param date
     * @return
     */
	public static Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
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
		CommonUtils.other = other;
	}

	/**
	 * @return 是否强制用[报关单单价]修改[单据中的报关单价]
	 */
	public static boolean isUpdateUnitPrice() {
		return isUpdateUnitPrice;
	}

	/**
	 * @param 是否强制用
	 *            [报关单单价]修改[单据中的报关单价]
	 */
	public static void setUpdateUnitPrice(boolean isUpdateUnitPrice) {
		CommonUtils.isUpdateUnitPrice = isUpdateUnitPrice;
	}

	/**
	 * @return 海关帐单据对应控制状态对象
	 */
	public static BillCorrespondingControl getBillCorrespondingControl() {
		return billCorrespondingControl;
	}

	/**
	 * 海关帐单据对应控制状态对象
	 */
	public static void setBillCorrespondingControl(
			BillCorrespondingControl billCorrespondingControl) {
		CommonUtils.billCorrespondingControl = billCorrespondingControl;
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
		String values=String.valueOf(sourceDouble);//lxr 2012-03-12 因为ROUND_HALF_UP只有字符串才能四舍五入例如：11381.835 与"11381.835"就会不一样
		BigDecimal b = new BigDecimal(values);
		return b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 格式化double的小数位数
	 * 
	 * @param sourceDouble
	 * @param n
	 * @return
	 */
	public static String formatDoubleByDigit(Double sourceDouble, int n) {
		if (sourceDouble == null) {
			return "0.0";
		}

		DecimalFormat format = new DecimalFormat("###0.000000000");
		format.setMaximumFractionDigits(n);
		format.setMinimumFractionDigits(0);

		return format.format(sourceDouble);
	}
	
	/**
	 * 格式化double的小数位数
	 * 
	 * @param sourceDouble
	 * @param n
	 * @return
	 */
	public static String formatDoubleByDigitNull(Double sourceDouble, int n) {
		if (sourceDouble == null) {
			return "";
		}

		DecimalFormat format = new DecimalFormat("###0.000000000");
		format.setMaximumFractionDigits(n);
		format.setMinimumFractionDigits(0);

		return format.format(sourceDouble);
	}

	/**
	 * 格式式类中的小数
	 * 
	 * @param obj
	 * @param n
	 */
	public static void formatDouble(Object obj, int n) {
		if (obj == null) {
			return;
		}
		if (n < 0) {
			return;
		}
		Field[] fs = obj.getClass().getDeclaredFields();
		try {
			for (Field f : fs) {
				f.setAccessible(true);
				Object o = f.get(obj);
				if (o == null) {
					continue;
				}
				if (o instanceof Double) {
					f.set(obj, getDoubleByDigit((Double) o, n));
				}
			}
		} catch (Exception e) {
			return;
		}
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

	/**
	 * 将为空的Integer转换成0
	 */
	public static Integer getIntegerExceptNull(Integer d) {
		return (d == null ? 0 : d);
	}

	/**
	 * 将Integer安全转换成字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String getStringFromInteger(Integer d) {
		return (d == null ? "" : d.toString());
	}
	
	public static String getStringExceptNull(Object o) {
		return (o == null ? "" : o.toString());
	}

	/**
	 * 获得/操作系统为文件分的路径
	 * 
	 * @param oldFilePath
	 * @return
	 */
	public static String getFilePath(String path) {
		String separator = File.separator;

		String[] createDirs = new String[0];
		String tempPath = null;
		if (path.indexOf("\\" + "\\") != -1) {
			createDirs = split(path, "\\" + "\\");
		}
		for (int i = 0; i < createDirs.length; i++) {
			if (i == 0) {
				tempPath = createDirs[i];
			} else {
				tempPath += "/" + createDirs[i];
			}
		}
		if (tempPath == null) {
			tempPath = path;
		}

		if (path.indexOf("\\") != -1) {
			createDirs = split(tempPath, "\\");
		}
		for (int i = 0; i < createDirs.length; i++) {
			if (i == 0) {
				tempPath = createDirs[i];
			} else {
				tempPath += "/" + createDirs[i];
			}
		}

		return tempPath;
	}

	/**
	 * 字符串分离
	 * 
	 * @param sourceStr
	 * @param matchStr
	 * @return
	 */
	public static String[] split(String sourceStr, String matchStr) {
		if (sourceStr == null) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		while (sourceStr.indexOf(matchStr) != -1) {
			String tempS = sourceStr.substring(0, sourceStr.indexOf(matchStr));
			sourceStr = sourceStr.substring(sourceStr.indexOf(matchStr)
					+ matchStr.length(), sourceStr.length());
			if ("".equals(tempS)) {
				continue;
			}
			list.add(tempS);
		}
		list.add(sourceStr);
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			str[i] = list.get(i);
		}
		return str;
	}

	public static void setIsBigToGBK(String isBigToGBK) {
		CommonUtils.isBigToGBK = isBigToGBK;
	}

	public static String getIsBigToGBK() {
		return isBigToGBK;
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
			System.out.println(endDate.after(nowDate));
			System.out.println(beginDate.equals(nowDate));
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

	/**
	 * 按海关年度进行切换的表
	 */
	public static String[] yearLimitDBTables = new String[] {
			"BillDetailFixture", "BillDetailHalfProduct", "BDLMateriel",
			"BillDetailMateriel", "BillDetailProduct",
			"BillDetailRemainProduct", "BillMasterFixture",
			"BillMasterHalfProduct", "BMLMateriel", "BillMasterMateriel",
			"BillMasterProduct", "BillMasterRemainProduct", "MonthStorage",
			"MonthStorage2" };

	/**
	 * 主要用于接口的本地Sql的时候用
	 * 
	 * @param sql
	 * @return
	 */
	public static String replaceSqlByTableName(String sql) {
		String casYear = CommonUtils.getYear();
		//
		// 如果海关帐记帐年份不是2005那么下面这些 表名=表名+年份(意思就是每年单据都会有不同的表来存数据)
		// 这里有考虑用 String.repace函数,但是这是不正确的. (大小的问题) 如果统一把sql转成小写,sql可能就会出错
		// 所以这里用 substring方法来解决, 先把sql 转成小写记录要替换的 index_position 再用实际的 sql
		// 出截取替换,这样的话就非常OK
		//
		if (!casYear.equals("2005")) {
			String[] tables = yearLimitDBTables;

			String lowerCaseSql = sql.toLowerCase();
			String tempSql = "";
			for (int i = 0, n = tables.length; i < n; i++) {

				String table = tables[i];
				String lowerCaseTable = table.toLowerCase();

				int tableLength = table.length();
				int indexPos = -1;
				boolean isExistMatch = false;

				while ((indexPos = lowerCaseSql.indexOf(lowerCaseTable)) != -1) {
					tempSql += sql.substring(0, indexPos) + table + casYear;
					sql = sql.substring(indexPos + tableLength);
					lowerCaseSql = lowerCaseSql.substring(indexPos
							+ tableLength);
					isExistMatch = true;
				}

				if (isExistMatch) {
					tempSql += sql;
					sql = tempSql;
					lowerCaseSql = sql.toLowerCase();
					tempSql = "";
				}
			}
		}
		return sql;
	}

	public static String tableNamingStrategyByCasYear(String tableName) {
		String casYear = CommonUtils.getYear();
		//
		// 如果海关帐记帐年份不是2005那么下面这些 表名=表名+年份(意思就是每年单据都会有不同的表来存数据)
		//
		if (!casYear.equals("2005")) {
			String[] tables = CommonUtils.yearLimitDBTables;
			for (int i = 0, n = tables.length; i < n; i++) {
				String table = tables[i];
				if (tableName.equalsIgnoreCase(table) == true) {
					tableName = tableName + casYear;
					System.out.println("海关帐年度表名 = " + tableName);
					break;
				}
			}
		}
		return tableName;
	}
	
	/**
	 * null 算空 "" 也算空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if(s == null || "".equals(s.trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 字符串不是null和""
	 * @param s
	 * @return
	 */
	public static boolean notEmpty(String s) {
		return !isEmpty(s);
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		CommonUtils.configuration = configuration;
	}

	/**
	 * 获得比较唯一的hashcode值
	 * 
	 * @param x
	 * @return
	 */
	public static int hash(Object x) {
		int h = x.hashCode();
		h += ~(h << 9);
		h ^= (h >>> 14);
		h += (h << 4);
		h ^= (h >>> 10);
		return h;
	}

	/**
	 * 自定义hashCode
	 * 
	 * @param s
	 * @return
	 */
	public static int hashCode(String s) {
		int h = 0;
		if (h == 0) {
			int off = 0;
			char val[] = s.toCharArray();
			int len = val.length;

			for (int i = 0; i < len; i++) {
				h = 31 * h + val[off++];
			}
		}
		return h;
	}

	private static final int BUF_SIZE = 32 * 1024;

	/**
	 * 获得文件的 bytes
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] getBytesByFile(String filePath) {
		File file = new File(filePath);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] fileContent = new byte[(int) file.length()];

			byte[] buf = new byte[BUF_SIZE];
			int len = 0;
			int count = 0;
			while ((len = in.read(buf)) != -1) {
				System.arraycopy(buf, 0, fileContent, count, len);
				count += len;
			}
			return fileContent;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}
		}
	}

	/**
	 * 保存文件来自 bytes
	 * 
	 * @param filePath
	 * @param bytes
	 */
	public static void saveFileByBytes(String filePath, byte[] bytes) {
		BufferedOutputStream out = null;
		try {
			File file = new File(filePath);

			out = new BufferedOutputStream(new FileOutputStream(file));

			byte[] buf = new byte[BUF_SIZE];
			int count = 0;

			for (int i = 0; i < bytes.length; i++) {
				if (i != 0 && i % BUF_SIZE == 0) {
					out.write(buf, 0, count);
					count = 0;
				}
				buf[count] = bytes[i];
				count++;
			}
			if (count > 0) {
				out.write(buf, 0, count);
			}
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFileName
	 * @param dstFileName
	 */
	public static void copyFile(String srcFileName, String dstFileName)
			throws Exception {
		FileChannel srcChannel = new FileInputStream(srcFileName).getChannel();
		FileChannel dstChannel = new FileOutputStream(dstFileName).getChannel();
		srcChannel.transferTo(0, srcChannel.size(), dstChannel);
		srcChannel.close();
		dstChannel.close();
	}

	public static boolean isCompany(String companyName) {
		String name = CommonUtils.getCompany().getName();
		if (name.indexOf(companyName) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 将全角字符转换为半角
	 * 
	 * @param str
	 * @return
	 */
	public static String dbc2sbc(String str) {
		for (int i = 0; i < str.length(); i++) {
			char code = str.charAt(i);
			// “65281”是“！”，“65373”是“｝”
			if (code >= 65281 && code < 65373) {
				// “65248”是转换码距
				str = str.replace(code, (char) (code - 65248));
				System.out.println("cc-----------B:" + String.valueOf(code));
				System.out.println("cc-----------A:"
						+ String.valueOf((char) (code - 65248)));
			}
		}
		return str;
	}

	/**
	 * 比较两个对象是否相等
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean compareObject(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if ((obj1 == null && obj2 != null) || (obj1 != null && obj2 == null)) {
			return false;
		}
		return obj1.equals(obj2);
	}

	/**
	 * 将{num}转换成长度为{len}字符串，如果长度不够前面补"0"
	 * 
	 * @param num
	 * @param len
	 * @return
	 */
	public static String convertIntToStr(int num, int len) {
		String str = String.valueOf(num);
		int diff = len - str.length();
		for (int i = 0; i < diff; i++) {
			str = "0" + str;
		}
		return str;
	}

	public static String makeIPTo12Num(String ip) {
		String resultstr = "";
		String[] strs = split(ip.trim(), ".");
		if (strs.length != 4) {
			return null;
		}
		for (int p = 0; p < strs.length; p++) {
			if (strs[p].contains(".")) {
				strs[p] = strs[p].substring(0, strs.length - 1);
			}
		}
		int[] numbs = new int[strs.length];
		try {
			for (int t = 0; t < strs.length; t++) {
				numbs[t] = Integer.parseInt(strs[t]);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		for (int k = 0; k < numbs.length; k++) {
			if (numbs[k] < 0 || numbs[k] > 255) {
				return null;
			} else {
				if (new Integer(strs[k]).toString().length() == 1) {
					resultstr += ("00" + new Integer(strs[k]).toString() + ".");
				} else if (new Integer(strs[k]).toString().length() == 2) {
					resultstr += ("0" + new Integer(strs[k]).toString() + ".");
				} else {
					resultstr += (new Integer(strs[k]).toString() + ".");
				}

			}
		}

		return resultstr.substring(0, resultstr.length() - 1);
	}

	public static void setServerIp(String ip) {

		threadServerIp.set(ip);
	}

	public static String getServerIp() {
		if (threadServerIp.get() == null) {
			return null;
		}
		return (String) threadServerIp.get();
	}

	public static void setPort(String port) {
		threadPort.set(port);
	}

	public static String getPort() {
		if (threadPort.get() == null) {
			return null;
		}
		return (String) threadPort.get();
	}

	/**
	 * 按照字节数截取字符串(含有中文),如果字符串的长度不超出字节数返回原字符串,
	 * 按照GBK的字符占用方式换算:每个汉字占2个字节,每个ASCII占1个字节.
	 * 
	 * @param str
	 *            要截取的字符串
	 * @param bytesLength
	 *            字节数
	 * @return 截取后的字符串
	 */
	public static String controlLengthByGBK(String str, int bytesLength) {
		String s = "";
		if (str == null || str.equals("")) {
			return str;
		}
		if (str.length() <= (bytesLength / 2)) {
			return str;
		}
		char[] chars = str.toCharArray();
		int k = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] > 255) {
				k += 2;// 每个汉字当GBK的占2个字节算
				if (k == (bytesLength + 1))
					break;
				s += chars[i];
			} else {
				++k;// 每个ASCII占1个字节算
				s += chars[i];
			}

			if (k >= bytesLength)
				break;
		}
		return s;
	}

	public static void main(String[] args) {
		Double d = new Double("20000000.5");
		System.out.println(d);
		System.out.println(formatDoubleByDigit(d, 10));
		System.out.println(getDoubleByDigit(d, 10));
		System.out.println(convertStrToDate("2012-07-30 15:21:11.557"));
		System.out.println(getDate(convertStrToDate("2012-07-30 15:21:11.557"), "yyyy-MM-dd"));
		Double num = Double.valueOf(12.1510);
		if(isDoubleToInteger(num)){
			System.out.println(formatDoubleByDigit(num, 0));
		}else{
			System.out.println(num);
		}
		String ronud = "10.00";
		System.out.println(getString(ronud));
	}

	public static Date getDateAfterNDay(Date date, int n) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		c.setTime(date);
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		String s = df.format(d2);
		return new Date(s);
	}

	/**
	 * 截取字符串中后面的0
	 * 
	 */
	public static String getString(String str) {
		String s = ".";
		String xx = "";
		if (str.indexOf(s) > 0) {
			Integer h = str.indexOf(s);
			String yy = str.substring(h + 1, str.length());
			Integer ss = getInteger(yy);
			xx = str.substring(0, h) + "." + yy.substring(0, ss + 1);
		} else {
			xx = "0.0";
		}
		return xx;
	}

	public static Integer getInteger(String str) {
		Integer hh = 0;
		char[] rr = str.toCharArray();
		for (int i = 1; i < rr.length + 1; i++) {
			if (rr[rr.length - i] != '0') {
				hh = rr.length - i;
				return hh;
			}
		}
		return hh;
	}
	/**
	 *  判断Double数值小数点是否后面全为0
	 * @param str
	 * @return
	 */
	public static boolean isDoubleToInteger(Double doubleValues) {
		Boolean isChanege = true;
		String str = doubleValues.toString();
		String s =".";
		if(str.indexOf(s)>0){
			int len = str.indexOf(s);
			String ss = str.substring(len+1, str.length());
			char[] charArray = ss.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if('0'!=charArray[i]){
					isChanege = false;
					break;
				}
			}
		}
		return isChanege;
	}

	/**
	 * 将yyyyMMddHHmmSS格式的字符串转换成日期
	 * 
	 * @param dDate
	 * @return
	 */
	public static Date yyyyMMddHHmmSSToDate(String dDate) {
		int year = Integer.MIN_VALUE;
		int mon = 0;
		int mday = 0;
		int hour = 0;
		int min = 0;
		int sec = 0;
		int millis = 0;
		if (dDate.length() >= 4)
			year = Integer.parseInt(dDate.substring(0, 4));
		if (dDate.length() >= 6)
			mon = Integer.parseInt(dDate.substring(4, 6));
		if (dDate.length() >= 8)
			mday = Integer.parseInt(dDate.substring(6, 8));
		if (dDate.length() >= 10)
			hour = Integer.parseInt(dDate.substring(8, 10));
		if (dDate.length() >= 12)
			min = Integer.parseInt(dDate.substring(10, 12));
		if (dDate.length() >= 14)
			sec = Integer.parseInt(dDate.substring(12, 14));
		if (dDate.length() > 14)
			millis = Integer.parseInt(dDate.substring(14));
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, year);
		gc.set(GregorianCalendar.MONTH, mon - 1);
		gc.set(GregorianCalendar.DAY_OF_MONTH, mday);
		gc.set(GregorianCalendar.HOUR_OF_DAY, hour);
		gc.set(GregorianCalendar.MINUTE, min);
		gc.set(GregorianCalendar.SECOND, sec);
		gc.set(GregorianCalendar.MILLISECOND, millis);
		return gc.getTime();
	}

	/**
	 * 将字符串 yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd 转换成日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date convertStrToDate(String dateStr) {
		if (dateStr == null || "".equals(dateStr.trim())) {
			return null;
		} else {
			String format = (dateStr.length() > 10 ? "yyyy-MM-dd HH:mm:ss"
					: "yyyy-MM-dd");
			DateFormat dateFormat = new SimpleDateFormat(format);
			try {
				return dateFormat.parse(dateStr.trim());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String getDate(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/** 转换类型 */
	private static Class getListClass(String fieldName, Class clazz) {
		for (Field f : clazz.getDeclaredFields()) {

			if (!(f.getGenericType() instanceof ParameterizedType)) {
				continue;
			}
			Type[] paraTypes = ((ParameterizedType) f.getGenericType())
					.getActualTypeArguments();
			if (paraTypes.length <= 0) {
				continue;
			}
			Class firstType = (Class) paraTypes[0];
			if ((f.getType().equals(List.class) || f.getType().getInterfaces()
					.equals(List.class))
					&& firstType.getName().indexOf("com.bestway") >= 0) {
				return firstType;
			}
		}
		return null;
	}

	/** 转换类型 */
	private static Class getBestwayClass(String fieldName, Class clazz) {
		for (Field f : clazz.getDeclaredFields()) {
			String typeStr = f.getType().getName();
			if (f.getName().equals(fieldName)
					&& typeStr.indexOf("com.bestway") >= 0) {
				return f.getType();
			}
		}
		return null;
	}

	/** 转换类型 */
	private static Map<String, String> getFieldMap(Class clazz) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		for (Field f : clazz.getDeclaredFields()) {
			fieldMap.put(f.getName(), f.getName());
		}
		return fieldMap;
	}

	/** 转换QP List<Map> to List<Object> 类型 */
	public static List castListType(List itemsByQp, Class clazz) {
		List items = new ArrayList();

		for (Object obj : itemsByQp) {
			if (!(obj instanceof Map)) {
				continue;
			}
			Map map = (Map) obj;
			Object item = castObjectType(map, clazz);
			items.add(item);
		}
		return items;
	}

	/** 转换QP List to java List 类型 */
	public static Object castObjectType(Object obj) {
		Object newObj = obj;
		Class newClass = obj.getClass();
		for (Field f : newClass.getDeclaredFields()) {

			if (!(f.getGenericType() instanceof ParameterizedType)) {
				continue;
			}
			Type[] paraTypes = ((ParameterizedType) f.getGenericType())
					.getActualTypeArguments();
			if (paraTypes.length <= 0) {
				continue;
			}
			Class firstType = (Class) paraTypes[0];
			if ((f.getType().equals(List.class) || f.getType().getInterfaces()
					.equals(List.class))
					&& firstType.getName().indexOf("com.bestway") >= 0) {
				f.setAccessible(true);
				Object old = null;
				try {
					old = f.get(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (old == null || !(old instanceof List)) {
					continue;
				}
				List newList = castListType((List) old, firstType);
				try {
					f.set(obj, newList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return newObj;

	}

	/** 转换类型 */
	private static Object castObjectType(Map obj, Class clazz) {
		Map map = (Map) obj;
		Iterator iterator = map.entrySet().iterator();
		//
		// 字段
		//
		Map<String, String> fieldMap = getFieldMap(clazz);

		Object item = new Object();
		try {
			item = clazz.newInstance();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		for (; iterator.hasNext();) {
			Map.Entry<String, Object> e = (Map.Entry) iterator.next();
			String name = e.getKey();
			Object value = e.getValue();

			if (!fieldMap.containsKey(name)) {
				System.out.println(clazz.getName() + "= " + name + "字段不存在");
				continue;
			}
			if (value != null && value instanceof List) {
				Class listClass = getListClass(name, clazz);
				if (listClass != null) {
					value = castListType((List) value, listClass);
				}
			} else if (value != null && value instanceof Map) {
				Class bestwayClass = getBestwayClass(name, clazz);
				if (bestwayClass != null) {
					value = castObjectType((Map) value, bestwayClass);
				}
			}
			try {
				PropertyUtils.setProperty(item, name, value);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return item;
	}

	private static SecureRandom ng = null;

	public static String getUUID() {
		byte[] randomBytes = new byte[16];
		if (ng == null) {
			ng = new SecureRandom(randomBytes);
		}
		ng.nextBytes(randomBytes);
		return UUID.nameUUIDFromBytes(randomBytes).toString();
	}
	/**
	 * 通过把LabelValue对象转变为XML文件----1
	 * @param object
	 * @param tab
	 * @return
	 */
	public static String coverter(LabelValue object, String tab) {
		tab = tab == null ? "" : tab + "  ";
		StringBuilder sb = new StringBuilder();
		if (object == null) {
	
		} else if (isList(object.value)) {
			List<LabelValue> list = (List<LabelValue>) (object.value);
			if (list.size() > 0) {
				sb.append(tab + "<" + object.label + ">\r\n");
				for (LabelValue labelValue : list) {
					sb.append(coverter(labelValue, tab));
				}
				if (object.label.contains(" ")) {
					sb
							.append(tab + "</" + object.label.split(" ")[0]
									+ ">\r\n");
				} else {
					sb.append(tab + "</" + object.label + ">\r\n");
				}
			}

		} else if (object.value == null || "".equals(object.value)) {
			sb.append(tab + "<" + object.label + "/>\r\n");
		} else {
			sb.append(tab + "<" + object.label + ">");
			sb.append(toXMLString(object.value.toString()));
			sb.append("</" + object.label + ">\r\n");
		}

		return sb.toString();
	}
	/**
	 * LabelValue存储key和value----2
	 * @author Administrator
	 *
	 */
	public static class LabelValue {
		String label;
		Object value;
		public LabelValue(String label, Object value) {
			super();
			this.label = label;
			this.value = value;
		}

		public LabelValue() {
			super();
		}
	}
	/**
	 * Checks if is object.---3
	 * 
	 * @param obj
	 *            the obj
	 * 
	 * @return true, if is object
	 */
	private static boolean isList(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof List)
			return true;
		return false;
	}
	/**
	 * 转变问XML文件时对特殊符号的替换--4
	 * @param str
	 * @return
	 */
	private static String toXMLString(String str) {
		// &lt; < 小于号
		// &gt; > 大于号
		// &amp; & 和
		// &apos; ' 单引号
		// &quot; " 双引号
		return str.replace("&", "&amp;").replace("<", "&lt;").replace(">",
				"&gt;").replace("'", "&apos;").replace("\"", "&quot;");
	}
	
	/**
	 * 获得对象的某个属性的值
	 * 
	 * @param target
	 * @param fieldName
	 * @return
	 */
	public static Object getProperty(Object target, String fieldName) {
		if(target == null) {
			return null;
		}
		return getProperty(target, target.getClass(), fieldName);
	}
	/**
	 * 获得对象的指定类型，某个属性的值
	 * 
	 * @param target
	 * @param fieldName
	 * @return
	 */
	public static Object getProperty(Object target,Class c, String fieldName) {
		if(target == null) {
			return null;
		}
		try {
			Field f = c.getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(target);
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 把list转化为Map
	 * @param list
	 * @param key
	 * @return
	 */
	public static Map listToMap(List list, GetKeyValue keyValue) {
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			map.put(keyValue.getKey(list.get(i)), keyValue.getValue(list.get(i)));
		}
		
		return map;
	}
	
	
	/**
	 * 把list转化为Map
	 * @param list
	 * @param key
	 * @return
	 */
	public static Map listToMap(List list, Map map, GetKeyValue keyValue) {
		for (int i = 0; i < list.size(); i++) {
			keyValue.put(list.get(i), map);
		}
		
		return map;
	}
	/**
	 * 查找下表
	 * @param array
	 * @param o
	 * @return 不存在返回-1
	 */
	public static <T> int indexOf(T[] array,T o){
		return indexOf(array, o,0);
	} 
	
	/**
	 * 查找下表
	 * @param array
	 * @param o
	 * @return 不存在返回-1
	 */
	public static <T> int indexOf(T[] array,T o,int startIndex){
		if (array == null) {
			return -1;
		}		
		if(startIndex < 0)
			startIndex = 0;
		if (o == null) {
			for (int i = startIndex; i < array.length; ++i) {
				if (array[i] == null)
					return i;
			}
		} else {
			for (int i = startIndex; i < array.length; ++i) {
				if (o.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	} 
	
	/**
	 * 查找下标
	 * @param array
	 * @param o
	 * @return 不存在返回-1
	 */
	public static <E> int indexOf(List<E> list,E o){
		return indexOf(list, o, 0);
	} 
	
	/**
	 * 查找下标
	 * @param array
	 * @param o
	 * @return 不存在返回-1
	 */
	public static <E> int indexOf(List<E> list,E o,int startIndex){
		if (list == null) {
			return -1;
		}
		if(startIndex <0)
			startIndex = 0;		
		if (o == null) {
			for (int i = startIndex; i < list.size(); ++i) {
				if (list.get(i) == null)
					return i;
			}
		} else {
			for (int i = startIndex; i < list.size(); ++i) {
				if (o.equals(list.get(i))) {
					return i;
				}
			}
		}
		return -1;
	} 
	
	public interface GetKeyValue<E> {
		public String getKey(E e);
		public Object getValue(E e);
		public void put(E e, Map map);
	}
	
	
	/** 
	* 隐藏表格中的某一列 
	* @param table  表格 
	* @param index  要隐藏的列 的索引 
	*/ 
	public static void hideColumn(JTable table,int index){ 
	TableColumn tc= table.getColumnModel().getColumn(index); 
	tc.setMaxWidth(0); 
	tc.setPreferredWidth(0); 
	tc.setMinWidth(0); 
	tc.setWidth(0); 
	table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0); 
	table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0); 
	}
	/**
	 * 当JTextField获得光标时，选中文本框中全部内容
	 * @param tf
	 */
	public static void jtextFieldSelectAll(JTextField tf){
		tf.addFocusListener(new FocusListener() {
			public void focusGained(final FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						((JTextField) e.getSource()).selectAll();
					}
				});
			}
			@Override
			public void focusLost(FocusEvent e) {
			}
		});
	}
	
	
	public static void setBeanNumberPropertyAsZero(Object bean){
		if(bean != null) {
			Field[] fields = bean.getClass().getDeclaredFields();
			Field f = null;
			Class type = null;
			try {
				for (int i = 0; i < fields.length; i++) {
					f = fields[i];
					type = f.getType();
					if(type.equals(Integer.class)
							|| type.equals(Long.class)
							|| type.equals(Double.class)
							|| type.equals(Float.class)) {
						org.apache.commons.beanutils.BeanUtils.setProperty(bean, f.getName(), 0);
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	  /** 
     * 获取某年第一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getCurrYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getCurrYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    }
    
}
