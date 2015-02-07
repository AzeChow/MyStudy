package com.bestway.common.warning.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.bestway.common.WebUtils;

/**
 * 存放预警管理的提示的信息
 * 
 * @author ls
 * 
 */
public class WarningCache {
	/** key = companyId + aclUser.getName() + serial */
	private static Map<String, StringBuffer>	map;
	private static String						WARNING_ITEM_FILE	= WebUtils
																			.getWebAppRoot()
																			+ "warningItem.xml";

	/** warning cache */
	public static Map<String, StringBuffer> getCache() {
		if (map == null) {
			map = new Hashtable<String, StringBuffer>();
		}
		return map;
	}

	
	
	/** 把 cache 存入文件 */
	public synchronized static void store() {
		Properties p = new Properties();
		Map<String, StringBuffer> map = getCache();

		Iterator<Map.Entry<String, StringBuffer>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, StringBuffer> e = iterator.next();
			p.put(e.getKey(), e.getValue().toString());
		}

		try {
			OutputStream out = new FileOutputStream(WARNING_ITEM_FILE);
			// utf8 encode
			p.storeToXML(out,
							"[ JBCUS WARNING FLAG VERSION BY LASTMODIFE IN BESTWAYSOFT ]");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 在init 的时候载入cache */
	public static void load() {
		Properties p = new Properties();
		Map<String, StringBuffer> map = getCache();

		try {
			File f = new File(WARNING_ITEM_FILE);
			if(!f.exists()){
				return;
			}
			InputStream in = new FileInputStream(WARNING_ITEM_FILE);
			p.loadFromXML(in);
			in.close();

			Iterator<Map.Entry<Object, Object>> iterator = p.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Object, Object> e = iterator.next();
				if (e.getKey() != null) {
					map.put((String)e.getKey(), new StringBuffer(e
									.getValue() == null ? "" : e.getValue()
									.toString()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
