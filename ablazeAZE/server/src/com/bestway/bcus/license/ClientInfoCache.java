/*
 * Created on 2004-12-3
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.license.entity.ClientInfo;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClientInfoCache {
	private static List<ClientInfo> cache=new ArrayList<ClientInfo>();
//	private static JTableListModel tableModel = null;

	/**
	 * @return Returns the tableModel.
	 */
//	public synchronized static JTableListModel getTableModel() {
//		return tableModel;
//	}

//	/**
//	 * @param tableModel
//	 *            The tableModel to set.
//	 */
//	public synchronized static void setTableModel(JTableListModel tableModel) {
//		ClientInfoCache.tableModel = tableModel;
//	}

	public synchronized static void addClientInfo(ClientInfo clientInfo) {
//		List cache = tableModel.getList();
		ClientInfo info = null;
		int size = cache.size();
		for (int i = 0; i < size; i++) {
			if (((ClientInfo) cache.get(i)).equals(clientInfo)) {
				info = (ClientInfo) cache.get(i);
				break;
			}
		}
		if (info == null) {
			cache.add(clientInfo);
		} else {
			info.setLastCallTime(clientInfo.getLastCallTime());
		}
	}

	public synchronized static void removeClientInfo(ClientInfo clientInfo) {
		cache.remove(clientInfo);
	}
	
	public synchronized static List getClientInfo() {
		return cache;
	}
}