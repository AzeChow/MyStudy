/*
 * Created on 2004-11-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtLog;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.Gbflag;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDataToolsLogic {
	private static DataImportAction	dataImportAction	= (DataImportAction) CommonVars
																.getApplicationContext()
																.getBean(
																		"dataImportAction");

	public static void saveLog(String taskName, String result,
			String otherNote, String beginDate, String endDate,
			JTableListModel tableModel1) {
		TxtLog log = new TxtLog();
		log.setType("文本导入");
		log.setTaskName(taskName);
		log.setResult(result);
		log.setOtherNote(otherNote);
		log.setBeginDate(beginDate);
		log.setEndDate(endDate);
		log.setComputer(getComputerName());
		log.setExcuter(CommonVars.getCurrUser().getUserName());
		log.setCompany(CommonVars.getCurrUser().getCompany());
		log = dataImportAction.saveTxtLog(
				new Request(CommonVars.getCurrUser()), log);
		tableModel1.addRow(log);
	}

	/**
	 * 得到计算机名和IP地址
	 * 
	 * @return
	 */
	private static String getComputerName() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			return localhost.getHostName();
		} catch (UnknownHostException uhe) {
			return "";
		}
	}

	// 检查类型
	public static BillTemp checkType(TxtFormat txtFormat, BillTemp temp, int j,
			String value) {
		String fieldType = txtFormat.getFieldtype().trim();
		try {
			if (value == null || value.equals("")) {
				return null;
			}
			if (fieldType.equals("Double")) {
				Double.valueOf(value);
			} else if (fieldType.equals("Integer")) {
				Integer.valueOf(value);
			} else if (fieldType.equals("boolean")) {
				Boolean.parseBoolean(value);
			} else if (fieldType.equals("Date")) {
				java.sql.Date.valueOf(value);
			} else if (fieldType.equals("int")) {
				Integer.parseInt(value);
			} else if (fieldType.equals("double")) {
				Double.parseDouble(value);
			}
		} catch (Exception e) {
			temp.setBill1("文本数据中第" + String.valueOf(j + 1) + "行错误:["
					+ txtFormat.getName() + "]栏位字串类型不匹配(类型应为：" + fieldType
					+ ")！");
			return temp;
		}
		return null;
	}

	// 检查长度
	public static BillTemp checkLenth(TxtFormat txtFormat, BillTemp temp,
			int j, int valueLenth) {
		String fieldType = txtFormat.getFieldtype().trim();
		if (fieldType.equals("String")) {
			int fieldLenth = txtFormat.getFieldlen().intValue();
			if (valueLenth > fieldLenth) {
				temp.setBill1("文本数据中第" + String.valueOf(j + 1) + "行错误:["
						+ txtFormat.getName() + "]栏位字串过长(允许最大长度："
						+ String.valueOf(fieldLenth) + "；实际长度："
						+ String.valueOf(valueLenth) + ")！");
				return temp;
			}
		}
		return null;
	}

	// 检查是否为空
	public static BillTemp checkIsNull(TxtFormat txtFormat, BillTemp temp,
			int j, String value) {
		if (txtFormat.getIsNull().equals(new Boolean(false))) {
			if (value.equals("")) {
				temp.setBill1("文本数据中第" + String.valueOf(j + 1) + "行错误:["
						+ txtFormat.getName() + "]栏位不可为空");
				return temp;
			}
		}
		return null;
	}

	public static Hashtable getHash(List list) {
		Hashtable selectedValues = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			selectedValues.put(Integer.valueOf(i + 1),
					((TxtFormat) list.get(i)).getSortno());
		}
		return selectedValues;
	}

	public static String getFileColumnValue(String[] fileRow, int dataIndex,
			Hashtable selectedValues) {
		int columnIndex = Integer.parseInt(selectedValues.get(
				Integer.valueOf(dataIndex)).toString()) - 1;
		if (columnIndex < 0 || columnIndex > fileRow.length - 1) {
			return "";
		} else {
			return fileRow[columnIndex];
		}
	}

	// 得到对象值
	public static Object getObj(TxtFormat txtFormat, String value) {
		Object obj1 = null;
		if (txtFormat.getGbflag().equals(Gbflag.NEW_COID)) { // 新的公司id
			obj1 = CommonVars.getCurrUser().getCompany();
		} else if (txtFormat.getGbflag() == null
				|| txtFormat.getGbflag().equals("")
				|| txtFormat.getGbflag().equals(Gbflag.NO)) { // 无
			if (txtFormat.getFieldtype().equals("Double")) {
				obj1 = strToDou(value);
			} else if (txtFormat.getFieldtype().equals("Boolean")) {
				obj1 = strToBoo(value);
			} else if (txtFormat.getFieldtype().equals("Date")) {
				obj1 = getDateByString(value);
			} else {
				obj1 = value;
			}
		} else if (txtFormat.getGbflag().equals(Gbflag.OBJ)) { // 对象转换
			String obj = txtFormat.getObjName().getClassPath();
			String field = txtFormat.getGbStr().getFieldname();
			List list = dataImportAction.findObjList(new Request(CommonVars
					.getCurrUser()), obj, field, value, txtFormat.getObjName()
					.getIsCoid());// 查找对象
			if (list != null && list.size()>0) {
				obj1 = list.get(0);
			} else {
				obj1 = null;
			}
		} else if (txtFormat.getGbflag().equals(Gbflag.SEQNUM)) { // 序号/或流水号
			//
		}
		return obj1;
	}

	
	private static Date getDateByString(Object obj1) {
		if (obj1 == null || obj1.equals("")) {
			return null;
		}
		String obj = obj1.toString();
		String dates = obj + " 00:00:00";
		return strToStandDate(dates);
	}

	// 日期转换
	public static Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return (Date) dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	private static Object strToDou(String str) {
		if (str == null || str.equals("")) {
			return Double.valueOf("0");
		}
		return Double.valueOf(str);
	}

	private static Boolean strToBoo(String str) {
		if (str == null || str.equals("") || str.equals("0")
				|| str.equals("false")) {
			return Boolean.valueOf(false);
		}
		return Boolean.valueOf(true);
	}

	// 保存新数据
	public static Object save(String className, List txtFormatList,
			String[] strs, Hashtable selectedValues) {
		try {
			Class cls = Class.forName(className);
			Object obj = cls.newInstance();
			for (int j = 0; j < txtFormatList.size(); j++) {// 字段设置
				TxtFormat txtFormat = (TxtFormat) txtFormatList.get(j);
				Object obj1 = DgDataToolsLogic.getObj(txtFormat,
						getFileColumnValue(strs, j + 1, selectedValues));

				BeanUtils.setProperty(obj, txtFormat.getFieldname(), obj1);
			}
			return obj;
			// dataImportAction.saveTxtInput(new
			// Request(CommonVars.getCurrUser()),obj);
		} catch (Exception e) {
			return null;
		}
	}

	public static JTableListModel initTable1(List dataSource, JTable jTable1) {
		return new JTableListModel(jTable1, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("类型", "type", 100));
						list.add(addColumn("任务名称", "taskName", 250));
						list.add(addColumn("执行结果", "result", 100));
						list.add(addColumn("其他说明", "otherNote", 150));
						list.add(addColumn("开始运行时间", "beginDate", 120));
						list.add(addColumn("结束运行时间", "endDate", 120));
						list.add(addColumn("执行机器名", "computer", 100));
						list.add(addColumn("执行人", "excuter", 80));
						return list;
					}
				});
	}

	public static JTableListModel initTable2(List dataSource, JTable jTable2) {
		return new JTableListModel(jTable2, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("任务名称", "taskname", 250));
						list.add(addColumn("运行方式", "excutekind", 80));
						list.add(addColumn("运行状态", "executeState", 80));
						list.add(addColumn("运行日期", "excuteday", 100));
						list.add(addColumn("运行时间", "excutetime", 100));
						list.add(addColumn("任务创建者", "createuser", 80));
						list.add(addColumn("任务修改者", "edituser", 80));
						list.add(addColumn("建立日期", "createdate", 80));
						return list;
					}
				});
	}

	public static void getColumn(JTable jTable2) {
		jTable2.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "未执行";
						} else if (value.equals("1")) {
							returnValue = "正在执行";
						} else if (value.equals("2")) {
							returnValue = "执行结束";
						}
						return returnValue;
					}
				});
	}

	// 检查重复值
	public static String isRepeat(String className, List txtFormatList,
			String[] strs, String del, Hashtable selectedValues) {
		String ss = "0";
		for (int j = 0; j < txtFormatList.size(); j++) {// 字段设置
			TxtFormat txtFormat = (TxtFormat) txtFormatList.get(j);
			if (txtFormat.getIskey().equals(new Boolean(true))) { // 主键
				List list = dataImportAction.findRepeatList(new Request(
						CommonVars.getCurrUser()), className, txtFormat
						.getFieldname(), getFileColumnValue(strs, j + 1,
						selectedValues), txtFormat.getTxttask().getClassList()
						.getIsCoid());
				if (list.size() > 0) {
					ss = "1"; // 找到重复值
					if (del.equals("1")) {
						Object obj = list.get(0);
						dataImportAction.deleteTxtInput(new Request(CommonVars
								.getCurrUser()), obj);
					}
					if (del.equals("2")) {
						try {
							Object obj1 = list.get(0);
							for (int k = 0; k < txtFormatList.size(); k++) {// 字段设置
								TxtFormat txtFormat1 = (TxtFormat) txtFormatList
										.get(k);
								if (txtFormat1.getIskey().equals(
										new Boolean(true))
										|| txtFormat1.getGbflag().equals(
												Gbflag.NEW_COID)) {
									continue;
								}
								Object values = DgDataToolsLogic.getObj(
										txtFormat1, getFileColumnValue(strs,
												k + 1, selectedValues));
								BeanUtils.setProperty(obj1, txtFormat1
										.getFieldname(), values);
							}
							dataImportAction.saveTxt(new Request(CommonVars
									.getCurrUser()), obj1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				}
			}
		}
		return ss;
	}
	// 以上是文本处理
	// 以下是数据处理
}
