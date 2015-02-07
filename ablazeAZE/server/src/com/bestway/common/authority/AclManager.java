package com.bestway.common.authority;

import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.AclEntry;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.bestway.common.authority.entity.AclUser;

public class AclManager {
	private static Hashtable<String, Acl> hashTableAcl = null;

	private static Hashtable<String, Acl> iphashTableAcl = null;

	private static AclManager aclManager = null;

	private static Hashtable<String, List> hashIPpermission = null;

	public Hashtable getHashTableAcl() {
		return hashTableAcl;
	}

	private AclManager() {
	}

	public static AclManager getInstance() {
		if (aclManager == null) {
			aclManager = new AclManager();
			hashTableAcl = new Hashtable<String, Acl>();
			iphashTableAcl = new Hashtable<String, Acl>();
		}
		return aclManager;
	}

	public void addAcl(String moduleName, Acl acl) {
		hashTableAcl.put(moduleName, acl);
	}

	public Acl getAcl(String moduleName) {
		if (hashTableAcl.get(moduleName) == null) {
			return null;
		}
		return (Acl) hashTableAcl.get(moduleName);
	}

	public void addipAcl(String moduleName, Acl acl) {
		iphashTableAcl.put(moduleName, acl);
	}

	public Acl getipAcl(String moduleName) {
		if (iphashTableAcl.get(moduleName) == null) {
			return null;
		}
		return (Acl) iphashTableAcl.get(moduleName);
	}

	/**
	 * 清除全部权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache() {
		hashTableAcl.clear();
	}

	public void clearipAclCache() {
		iphashTableAcl.clear();
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache(String moduleName, Principal user) {
		Acl acl = (Acl) hashTableAcl.get(moduleName);
		if (acl != null) {
			hashTableAcl.remove(moduleName);
			// AclUser owner = new AclUser();
			// owner.setId("1"); // OwnerName是随意写的
			// for (Enumeration e = acl.entries(); e.hasMoreElements();) {
			// AclEntry entry = (AclEntry) e.nextElement();
			// if (entry.getPrincipal().equals(user)) {
			// try {
			// if(acl.removeEntry(owner, entry)){
			// System.out.println("-----------------------yyyyyyyyyyyyyyy");
			// }else{
			// System.out.println("-----------------------nnnnnnnnnnnnnnns");
			// }
			// } catch (NotOwnerException e1) {
			// e1.printStackTrace();
			// }
			// System.out.println("-----------------------entry
			// e:"+entry.getPrincipal().getName());
			// }
			// System.out.println("-----------------------entry:"+entry.getPrincipal().getName());
			// }
		}
	}

	public void clearipAclCache(String moduleName, Principal user) {
		Acl acl = (Acl) iphashTableAcl.get(moduleName);
		if (acl != null) {
			iphashTableAcl.remove(moduleName);
		}
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache(List lsUser) {
		AclUser owner = new AclUser();
		owner.setId("1"); // OwnerName是随意写的
		for (Enumeration e = hashTableAcl.keys(); e.hasMoreElements();) {
			String moduleName = e.nextElement().toString();
			Acl acl = (Acl) hashTableAcl.get(moduleName);
			boolean isContain = false;
			for (Enumeration f = acl.entries(); f.hasMoreElements();) {
				AclEntry entry = (AclEntry) f.nextElement();
				if (lsUser.contains(entry.getPrincipal())) {
					isContain = true;
					break;
					// try {
					// acl.removeEntry(owner, entry);
					// } catch (NotOwnerException e1) {
					// e1.printStackTrace();
					// }
				}
			}
			if (isContain) {
				hashTableAcl.remove(moduleName);
				continue;
			}
		}
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearipAclCache(List lsUser) {
		AclUser owner = new AclUser();
		owner.setId("1"); // OwnerName是随意写的
		for (Enumeration e = iphashTableAcl.keys(); e.hasMoreElements();) {
			String moduleName = e.nextElement().toString();
			Acl acl = (Acl) iphashTableAcl.get(moduleName);
			boolean isContain = false;
			for (Enumeration f = acl.entries(); f.hasMoreElements();) {
				AclEntry entry = (AclEntry) f.nextElement();
				if (lsUser.contains(entry.getPrincipal())) {
					isContain = true;
					break;
					// try {
					// acl.removeEntry(owner, entry);
					// } catch (NotOwnerException e1) {
					// e1.printStackTrace();
					// }
				}
			}
			if (isContain) {
				iphashTableAcl.remove(moduleName);
				continue;
			}
		}
	}

	public static Hashtable<String, List> getHashIPpermission() {
		if (hashIPpermission == null) {
			hashIPpermission = new Hashtable();
		}
		return hashIPpermission;
	}

	public static void setHashIPpermission(
			Hashtable<String, List> hashIPpermission) {

		AclManager.hashIPpermission = hashIPpermission;
	}
}
