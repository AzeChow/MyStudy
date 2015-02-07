package com.bestway.common.warning.logic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.warning.entity.TempWarningItem;
import com.bestway.common.warning.entity.WarningItemAnnotation;
import com.bestway.common.warning.entity.WarningItemFlag;

public class WarningLogic {

	private Map<String, StringBuffer>	warningCache	= WarningCache
																.getCache();

	/** 获得预警信息 */
	public StringBuffer getWarningByKey(String key) {
		return warningCache.get(key);
	}

	/** 获得所有预警信息 */
	public Map<String, StringBuffer> getWarningCache() {
		Company company = (Company) CommonUtils.getCompany();
		AclUser aclUser = (AclUser) CommonUtils.getAclUser();
		Map<String, StringBuffer> returnMap = new HashMap<String, StringBuffer>();
		//
		// key = company + user name + warning No.
		//
		String keyPrefix = company.getId() + aclUser.getName().trim();
		String[] keys = warningCache.keySet().toArray(new String[] {});
		for (String key : keys) {
			if (key.startsWith(keyPrefix)) {
				returnMap.put(key, warningCache.get(key));
			}
		}
		return returnMap;
	}

	/** 删除所有预警信息 */
	public void removeWarningByKey(String key) {
		warningCache.remove(key);
		WarningCache.store();
	}

	/** 载入cache */
	public void init() {
		WarningCache.load();
	}

	private static List<TempWarningItem>	tempWarningItemList	= null;

	public static List<TempWarningItem> getTempWarningItem() {
		if (tempWarningItemList == null) {
			tempWarningItemList = new ArrayList<TempWarningItem>();
			WarningItemFlag temp = new WarningItemFlag();
			Field[] fields = WarningItemFlag.class.getFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				if (f.isAnnotationPresent(WarningItemAnnotation.class)) {
					WarningItemAnnotation fAnnotation = (WarningItemAnnotation) f
							.getAnnotation(WarningItemAnnotation.class);
					try {
						int value = f.getInt(temp);
						String caption = fAnnotation.caption();
						TempWarningItem t = new TempWarningItem();
						t.setItemName(caption);
						t.setWarningItemFlag(value);
						tempWarningItemList.add(t);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return tempWarningItemList;
	}

}
