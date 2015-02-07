package com.bestway.common.qp.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.dec.qp.action.DecQpAction;
import com.bestway.dec.qp.action.DecQpServiceClient;

public class CommonQpTest {

	public static void main(String[] args) {
		CommonQpTest test = new CommonQpTest();
		test.download_I_Dec();
	}

	public void atestUrlReplace() {
	}

	// public void testFindFptAppItemOutByQp() {
	// String outTradeCode = "";
	// String outCopAppNo = "";
	// FptQpAction fptQpAction = FptQpServiceClient.getFptQpAction();
	//		
	// List itemsByQp = fptQpAction.findFptAppItemOutByQp(
	// outTradeCode, outCopAppNo);
	//		
	// List<FptAppItemByQp> items =
	// castListType(itemsByQp,FptAppItemByQp.class);
	//		
	// super.assertNotNull(items);
	// System.out.println(items.size());
	// System.out.println(items.toString());
	// super.assertEquals(items.get(0).getCodeTs(), "1");
	// System.out.println(items.get(0).getUnit());
	//
	// }

	public void download_I_Dec() {
		String outTradeCode = "";
		String outCopAppNo = "";
		DecQpAction fptQpAction = DecQpServiceClient.getCommonQpAction();
		List list = fptQpAction.download_E_Dec("88888888", "4419940044",
				"2008-11-15", "2008-11-15");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		// List itemsByQp = fptQpAction.findFptAppHeadByQpNoList(
		// null);
		//		
		// List<FptAppHeadByQp> heads =
		// castListType(itemsByQp,FptAppHeadByQp.class);
		//		
		// super.assertNotNull(heads);
		// System.out.println(heads.get(0).getInDate());
		// System.out.println(items.toString());
		// super.assertEquals(items.get(0).getCodeTs(), "1");
		// System.out.println(items.get(0).getUnit());

	}

	/** 转换类型 */
	private List castListType(List itemsByQp, Class clazz) {

		List items = new ArrayList();
		for (Object obj : itemsByQp) {
			Map map = (Map) obj;
			Iterator iterator = map.entrySet().iterator();
			// FptAppItemByQp item = new FptAppItemByQp();
			Object item = new Object();
			try {
				item = clazz.newInstance();
			} catch (InstantiationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for (; iterator.hasNext();) {
				Map.Entry<String, Object> e = (Map.Entry) iterator.next();
				String name = e.getKey();
				Object value = e.getValue();
				// if(value instanceof Date){
				// SimpleDateFormat bartDateFormat = new
				// SimpleDateFormat("yyyy-MM-dd");
				// // String defaultDate = bartDateFormat.format(value);
				// // return java.sql.Date.valueOf(defaultDate);
				// Calendar calendar = java.util.Calendar.getInstance();
				// calendar.setTimeInMillis(((Date)value).getTime());
				// String defaultDate =
				// bartDateFormat.format(calendar.getTime()).toString();
				// System.out.println(value);
				// }
				try {
					PropertyUtils.setProperty(item, name, value);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			items.add(item);
		}
		return items;
	}

}
