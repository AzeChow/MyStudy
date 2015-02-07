package com.bestway.common.fpt.qp.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.qp.action.FptQpAction;
import com.bestway.common.fpt.qp.action.FptQpServiceClient;
import com.bestway.common.fpt.qp.entity.FptAppHeadByQp;
import com.bestway.common.fpt.qp.entity.FptAppItemByQp;
import com.bestway.dzsc.qp.entity.TempQPEmsPorData;

public class FptQpTest extends TestCase {

	public void atestUrlReplace() {
		String serverUrl = "http://IP:6667/fpt/hessian/fptQpAction";
		String url = serverUrl.replace("IP", "192.168.1.28");
		System.out.println(url);
	}

	/** 转换类型 */
	public void atestCastListType2() {
		System.out
				.println(getListClass("ptsEmsListCm2", TempQPEmsPorData.class));
		System.out.println(this.getBestwayClass("ptsEmsHead",
				TempQPEmsPorData.class));
	}

	public void atestFindFptAppItemOutByQp() {
		String outTradeCode = "";
		String outCopAppNo = "";
		FptQpAction fptQpAction = FptQpServiceClient.getFptQpAction();

		List itemsByQp = fptQpAction.findFptAppItemOutByQp(outTradeCode,
				outCopAppNo);

		List<FptAppItemByQp> items = castListType(itemsByQp,
				FptAppItemByQp.class);

		super.assertNotNull(items);
		System.out.println(items.size());
		System.out.println(itemsByQp.toString());
		super.assertEquals(items.get(0).getCodeTs(), "1");
		System.out.println(items.get(0).getUnit());

	}

	// public void atestFindFptAppHeadByQpNoList() {
	// String outTradeCode = "";
	// String outCopAppNo = "";
	// FptQpAction fptQpAction = FptQpServiceClient.getFptQpAction();
	//
	// List itemsByQp = fptQpAction.findFptAppHeadByQpNoList(null);
	//
	// List<FptAppHeadByQp> heads = castListType2(itemsByQp,
	// FptAppHeadByQp.class);
	//
	// super.assertNotNull(heads);
	// System.out.println(heads.get(0).getInDate());
	// // System.out.println(items.toString());
	// // super.assertEquals(items.get(0).getCodeTs(), "1");
	// // System.out.println(items.get(0).getUnit());
	//
	// }

	public void testFindFptAppHeadByQpHasList() {
		String outTradeCode = "";
		String outCopAppNo = "";
		FptQpAction fptQpAction = FptQpServiceClient.getFptQpAction();

		List<Object> itemsByQp = fptQpAction.findFptAppHeadByQpHasList(null,
				null);
		System.out.println(itemsByQp.size());

		// System.out.println(((Map)itemsByQp.get(0)).get("items"));

		

//		System.out.println("heads.size() = " + itemsByQp.size());
//
//		for (Object p : itemsByQp) {
//			Map headMap = (Map)p;
////			System.out.println(headMap);
//			Iterator<Entry> i = headMap.entrySet().iterator();
//			for (;i.hasNext();) {
//			    Entry e = i.next();
//			    Object key = e.getKey();
//			    Object value = e.getValue();
//			    System.out.println(value);
//			}
//
//		}
		
		System.out.println(itemsByQp.size());

		// System.out.println(((Map)itemsByQp.get(0)).get("items"));

		List<FptAppHeadByQp> heads = castListType(itemsByQp,
				FptAppHeadByQp.class);

		System.out.println("heads.size() = " + heads.size());

		for (FptAppHeadByQp p : heads) {
			System.out.println(p.getOutTradeName());
			List<FptAppItemByQp> items = p.getItems();
			for (FptAppItemByQp item : items) {
				System.out.println("  items size = " + item.getCodeTs());
			}

		}

		// System.out.println(items.toString());
		// super.assertEquals(items.get(0).getCodeTs(), "1");
		// System.out.println(items.get(0).getUnit());

	}

	// /** 转换类型 */
	// private List castListType2(List itemsByQp, Class clazz) {
	//
	// List items = new ArrayList();
	//
	// for (Object obj : itemsByQp) {
	// Map map = (Map) obj;
	// Iterator iterator = map.entrySet().iterator();
	// // FptAppItemByQp item = new FptAppItemByQp();
	// Object item = new Object();
	// try {
	// item = clazz.newInstance();
	// } catch (Exception e2) {
	// e2.printStackTrace();
	// }
	//
	// for (; iterator.hasNext();) {
	// Map.Entry<String, Object> e = (Map.Entry) iterator.next();
	// String name = e.getKey();
	// Object value = e.getValue();
	//
	// if (value instanceof List) {
	// Class listClass = this.getListClass(name, clazz);
	// if (listClass != null) {
	// value = castListType2((List) value, listClass);
	// }
	// } else if (value instanceof Map) {
	// Class bestwayClass = this.getBestwayClass(name, clazz);
	// if (bestwayClass != null) {
	// value = castObjectType((Map) value, bestwayClass);
	// }
	// }
	//
	// try {
	// PropertyUtils.setProperty(item, name, value);
	// } catch (IllegalAccessException e1) {
	// // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// } catch (InvocationTargetException e1) {
	// // TODO Auto-generated catch block
	// // e1.print/StackTrace();
	// } catch (NoSuchMethodException e1) {
	// // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// }
	// }
	// items.add(item);
	// }
	// return items;
	// }

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

	
	/** 转换类型 */
	public static Object castObjectType1(Object obj) {
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
	public static Object castObjectType(Map obj, Class clazz) {
		Map map = (Map) obj;
		Iterator iterator = map.entrySet().iterator();
		Object item = new Object();
		try {
			item = clazz.newInstance();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		for (; iterator.hasNext();) {
			Map.Entry e = (Map.Entry) iterator.next();
			String name = e.getKey().toString();
			Object value = e.getValue();

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
//				 e2.printStackTrace();
			}
		}
		return item;
	}

}
