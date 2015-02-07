package com.bestway.client.common;

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.bestway.client.resource.IconSource;
import com.bestway.client.resource.ImageSource;
import com.bestway.client.util.JarDataStoreInterface;
import com.bestway.client.util.RenderDataColumnInterface;
import com.bestway.ui.winuicontrol.JTableBase;
import com.bestway.ui.winuicontrol.WindowOpenedEvent;

/**
 * 系统变量
 * 
 * @author Administrator
 * 
 */
public class CommonVariables {

	private static ApplicationContext applicationContext = null;

	private static ImageSource imageSource = null;

	private static IconSource iconSource = null;

	private static StringBuffer sbAbout = null;

	private static StringBuffer sbVesion = null;

	private static StringBuffer versionInfo = null;

	private static List every = null;

	private static int num = 0;

	private static RenderDataColumnInterface renderDataColumnInterface = null;

	private static JarDataStoreInterface jarDataStoreInterface = null;

	private static WindowOpenedEvent windowOpenedEvent = null;

	public static String mainTitle = null;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext context) {
		Assert.notNull(context);
		applicationContext = context;
		try {
			setImageSource((ImageSource) context.getBean("imageSource"));
			setIconSource((IconSource) context.getBean("iconSource"));
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static StringBuffer readFile(String fileName) {

		BufferedReader br = null;

		StringBuffer sb = new StringBuffer();

		try {

			ClassLoader ccl = Thread.currentThread().getContextClassLoader();

			br = new BufferedReader(new InputStreamReader(
					ccl.getResourceAsStream(fileName), "UTF-8"));

			String line;

			while ((line = br.readLine()) != null) {

				sb.append(line + System.getProperty("line.separator"));

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null) {

					br.close();

				}

			} catch (IOException e1) {

				e1.printStackTrace();

			}
		}
		return sb;
	}

	private static StringBuffer sbBalanceTipInfo = null;

	public static StringBuffer readFileByCasBalanceTipInfo() {
		if (sbBalanceTipInfo == null) {
			sbBalanceTipInfo = readFile("com/bestway/client/resource/casBalanceTipInfo.properties");
		}
		return sbBalanceTipInfo;
	}

	private static StringBuffer sbClinetPluginTipInfo = null;

	public static StringBuffer readFileByClientPluginTipInfo() {
		if (sbClinetPluginTipInfo == null) {
			sbClinetPluginTipInfo = readFile("com/bestway/client/resource/ClientPluginTipInfo.properties");
		}
		return sbClinetPluginTipInfo;
	}

	private static StringBuffer sbServerPluginTipInfo = null;

	public static StringBuffer readFileByServerPluginTipInfo() {
		if (sbServerPluginTipInfo == null) {
			sbServerPluginTipInfo = readFile("com/bestway/client/resource/ServerPluginTipInfo.properties");
		}
		return sbServerPluginTipInfo;
	}

	public static StringBuffer readFileByAbout() {
		if (sbAbout == null) {
			sbAbout = readFile("com/bestway/client/resource/about.properties");
			/**
			 * 通过资料包(用包名来定位)读取java properties 文件 ResourceBundle resoruceBundle =
			 * ResourceBundle.getBundle( "com.bestway.client.resource.about",
			 * Locale.getDefault(), Thread.currentThread()
			 * .getContextClassLoader()); resoruceBundle.getKeys();
			 * 
			 * ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			 * InputStream is = ccl .getResourceAsStream(
			 * "com/bestway/client/resource/about.properties");
			 */
		}
		return sbAbout;
	}

	public static StringBuffer readFileByVesion() {
		if (sbVesion == null) {
			sbVesion = readFile("com/bestway/client/resource/version.properties");
			sbVesion.append(readFile("com/bestway/client/resource/versionInfo.properties"));
		}
		return sbVesion;
	}

	public static List getEveryList() {
		if (every == null) {
			every = processMessage("com/bestway/client/resource/every.properties");
		}
		return every;
	}

	public static List processMessage(String fileName) {
		BufferedReader br = null;
		List<String> messageLines = new Vector<String>();
		try {
			ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			br = new BufferedReader(new InputStreamReader(
					ccl.getResourceAsStream(fileName), "GBK"));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().equals("")) {
					continue;
				}
				messageLines.add(line.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return messageLines;
	}

	public static StringBuffer getFileString(String fileName) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			br = new BufferedReader(new InputStreamReader(
					ccl.getResourceAsStream(fileName), "GBK"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return sb;
	}

	// get first note
	public static String getFirst() {
		List list = getEveryList();
		num = (int) (list.size() * Math.random());
		if (num == list.size()) {
			return (String) list.get(list.size() - 1);
		}
		return (String) list.get(num);
	}

	// get next note
	public static String getNext() {
		List list = getEveryList();
		num = num + 1;
		if (num != list.size()) {
			return (String) list.get(num);
		}
		num = 0;
		return (String) list.get(num);

	}

	// write files
	public static void writeFlat() {
		BufferedWriter bw = null;
		File write = new File("tag.properties");
		if (!write.exists()) {
			try {
				write.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// judge file is exist;
	public static boolean isFileExist() {
		File write = new File("tag.properties");
		if (write.exists()) {
			return true;
		}
		return false;
	}

	// delete files
	public static void deleteExist() {
		File write = new File("tag.properties");
		if (write.exists()) {
			write.delete();
		}
	}

	/**
	 * 获取属性类型
	 * 
	 * @param cls
	 * @param fieldDescription
	 * @return
	 */
	public static Class getTypeByField(Class cls, String fieldDescription) {
		String[] strs = split(fieldDescription, ".");
		Class tempClass = cls;
		for (int i = 0; i < strs.length; i++) {
			tempClass = getTypeByFieldDescription(tempClass, strs[i]);
		}
		return tempClass;
	}

	/**
	 * 获取本类里面的属性类型
	 * 
	 * @param cls
	 * @param field
	 * @return
	 */
	private static Class getTypeByFieldDescription(Class cls, String field) {
		if (field == null) {
			return Object.class;
		}
		PropertyDescriptor[] pros = PropertyUtils.getPropertyDescriptors(cls);
		for (int i = 0; i < pros.length; i++) {
			String tempField = pros[i].getName();
			if (tempField.equals(field)) {
				return pros[i].getPropertyType();
			}
		}
		return Object.class;
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

	/**
	 * 罗盛
	 * 
	 * @param list
	 * @param sourceStr
	 * @param startMatch
	 * @param endMatch
	 */
	public static void splitStrToList(List<String> list, String sourceStr,
			String startMatch, String endMatch) {
		int startIndex = sourceStr.indexOf(startMatch);
		int endIndex = sourceStr.indexOf(endMatch);
		if (sourceStr.equals("") || startIndex == -1) {
			return;
		}
		if (startIndex > -1 && endIndex == -1) {
			list.add(sourceStr.substring(startIndex, sourceStr.length()));
			return;
		}
		String tempStr = sourceStr.substring(startIndex,
				endIndex + endMatch.length());
		list.add(tempStr);
		sourceStr = sourceStr.substring(tempStr.length());
		splitStrToList(list, sourceStr, startMatch, endMatch);
	}

	/**
	 * 是否是基本类型
	 * 
	 * @param cls
	 * @return
	 */
	public static boolean isSingleBasicType(String cls) {
		boolean isTrue = false;
		if (cls.equals(int.class.getName()) || cls.equals(long.class.getName())
				|| cls.equals(short.class.getName())
				|| cls.equals(double.class.getName())
				|| cls.equals(float.class.getName())
				|| cls.equals(boolean.class.getName())
				|| cls.equals(Integer.class.getName())
				|| cls.equals(Long.class.getName())
				|| cls.equals(Short.class.getName())
				|| cls.equals(Double.class.getName())
				|| cls.equals(Float.class.getName())
				|| cls.equals(String.class.getName())
				|| cls.equals(Boolean.class.getName())
				|| cls.equals(Date.class.getName())
				|| cls.equals(Calendar.class.getName())) {
			isTrue = true;
		}
		return isTrue;
	}

	/**
	 * 获得根窗口
	 * 
	 * @param component
	 * @return
	 */
	public static Component getFormWhereComponentIsIn(Component component) {
		Component container = component.getParent();
		if (container == null || container instanceof JFrame
				|| container instanceof JInternalFrame
				|| container instanceof JDialog) {
			return container;
		} else {
			return getFormWhereComponentIsIn(container);
		}
	}

	/**
	 * 在容器中获得 List<JTableBase>
	 * 
	 * @param container
	 * @param jTableBaseList
	 */
	public static void getJTableBaseByComponent(Container container,
			List<JTableBase> jTableBaseList) {
		Component[] components = container.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof JTableBase) {
				jTableBaseList.add((JTableBase) components[i]);
			} else {
				if (components[i] instanceof Container) {
					getJTableBaseByComponent((Container) components[i],
							jTableBaseList);
				}
			}
		}
	}

	private static Icon excelIcon = null, copyIcon = null, searchIcon = null;

	public static Icon getCopyIcon() {
		if (copyIcon == null) {
			try {
				copyIcon = getIconSource().getIcon("copy.image");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return copyIcon;
	}

	public static Icon getExcelIcon() {
		if (excelIcon == null) {
			try {
				excelIcon = getIconSource().getIcon("excel.image");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return excelIcon;
	}

	public static Icon getSearchIcon() {
		if (searchIcon == null) {
			try {
				searchIcon = getIconSource().getIcon("search.image");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return searchIcon;
	}

	public static RenderDataColumnInterface getRenderDataColumnInterface() {
		return renderDataColumnInterface;
	}

	public static void setRenderDataColumnInterface(
			RenderDataColumnInterface renderDataColumnInterface) {
		CommonVariables.renderDataColumnInterface = renderDataColumnInterface;
	}

	public static JarDataStoreInterface getJarDataStoreInterface() {
		return jarDataStoreInterface;
	}

	public static void setJarDataStoreInterface(
			JarDataStoreInterface jarDataStoreInterface) {
		CommonVariables.jarDataStoreInterface = jarDataStoreInterface;
		System.out.println("this is a test !");
	}

	public static WindowOpenedEvent getWindowOpenedEvent() {
		return windowOpenedEvent;
	}

	public static void setWindowOpenedEvent(WindowOpenedEvent wiondowClosedEvent) {
		CommonVariables.windowOpenedEvent = wiondowClosedEvent;
	}

	/** 根据名字来获得类对象的字段当前值 */
	public static Object getObjectByFieldName(Object classObject,
			String fieldName) {
		Object obj = null;
		Field filed;
		try {
			filed = classObject.getClass().getDeclaredField(fieldName);
			//
			// 允许访问私有定义字段
			//
			filed.setAccessible(true);
			obj = filed.get(classObject);
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		if (obj == null && classObject.getClass().getSuperclass() != null) {
			return findDeclaredObjectByFieldName(classObject.getClass(),
					classObject, fieldName);
		}
		return obj;
	}

	public static Object invokePrivateMethod(Object o, String methodName,
			Object... obj) {
		final Method method = findDeclaredMethodWithEqualsParameters(
				o.getClass(), methodName, obj);
		method.setAccessible(true);
		try {
			return method.invoke(o, obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method findDeclaredMethodWithEqualsParameters(Class clazz,
			String methodName, Object... obj) {
		Method[] methods = clazz.getDeclaredMethods();
		Method targetMethod = null;
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				if (methods[i].getParameterTypes().length == obj.length) {
					targetMethod = methods[i];
					return targetMethod;
				}
			}
		}
		if (clazz.getSuperclass() != null) {
			return findDeclaredMethodWithEqualsParameters(
					clazz.getSuperclass(), methodName, obj);
		}
		return null;
	}

	public static Object findDeclaredObjectByFieldName(Class clazz,
			Object classObject, String fieldName) {
		System.out.println(clazz.toString());
		Field filed;
		try {
			filed = clazz.getDeclaredField(fieldName);
			//
			// 允许访问私有定义字段
			//
			filed.setAccessible(true);
			return filed.get(classObject);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (clazz.getSuperclass() != null) {
			return findDeclaredObjectByFieldName(clazz.getSuperclass(),
					classObject, fieldName);
		}
		return null;
	}

	/**
	 * Converts a plain text string to an html string if needed. Also replaces
	 * all '\n' with '<br>
	 * '.
	 * <p>
	 * 
	 * @param message
	 *            The message.
	 *            <p>
	 * @return The html string.
	 */
	public static String convertMessageToHtml(String message) {
		String htmlMessage = (message != null) ? message : "";

		htmlMessage = htmlMessage.trim();

		if (!htmlMessage.startsWith("<html>")) {
			htmlMessage = "<html>" + htmlMessage;
		}

		if (!htmlMessage.endsWith("</html>")) {
			htmlMessage = htmlMessage + "</html>";
		}

		htmlMessage = htmlMessage.replaceAll("\n", "<br>");

		return (htmlMessage);
	}

	/**
	 * 获得分行字符串
	 * 
	 * @param message
	 *            源字符串
	 * @param rowCount
	 *            每行的个数
	 * @return
	 */
	public static String convertMessageToHtml(String message, int rowCount) {
		StringBuffer strBuf = new StringBuffer();
		if (message == null || message.equals("")) {
			return "";
		}
		for (int i = 0; i < message.length(); i++) {
			if (i == rowCount) {
				strBuf.append(message.substring(0, rowCount) + "<br>");
				message = message.substring(rowCount, message.length());
				i = 0;
			}
		}
		strBuf.append(message);
		String htmlMessage = strBuf.toString();

		htmlMessage = htmlMessage.trim();

		if (!htmlMessage.startsWith("<html>")) {
			htmlMessage = "<html>" + htmlMessage;
		}

		if (!htmlMessage.endsWith("</html>")) {
			htmlMessage = htmlMessage + "</html>";
		}
		return htmlMessage;
	}

	public static int getPreferredRowHeight(JTable table, int rowIndex,
			int margin) {
		int height = table.getRowHeight();
		// System.out.println("default row height:"+table.getRowHeight());
		for (int c = 0; c < table.getColumnCount(); c++) {
			TableCellRenderer renderer = table.getCellRenderer(rowIndex, c);
			Component comp = table.prepareRenderer(renderer, rowIndex, c);
			int h = comp.getPreferredSize().height + 2 * margin;
			height = Math.max(height, h);
		}
		return height;
	}

	public static void packRows(JTable table) {
		packRows(table, -2);
	}

	public static void packRows(JTable table, int margin) {
		packRows(table, 0, table.getRowCount(), margin);
	}

	public static void packRows(JTable table, int start, int end, int margin) {
		for (int r = 0; r < table.getRowCount(); r++) {
			int h = getPreferredRowHeight(table, r, margin);

			if (table.getRowHeight(r) != h) {
				// System.out.println("R:"+table.getRowHeight(r) +" H:"+h);
				table.setRowHeight(r, h);
			}
		}
	}

}