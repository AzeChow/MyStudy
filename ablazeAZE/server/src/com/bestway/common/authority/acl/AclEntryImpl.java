package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.AclEntry;
import java.security.acl.Group;
import java.security.acl.Permission;
import java.util.Enumeration;
import java.util.Vector;

public class AclEntryImpl implements AclEntry, Serializable {

	private Principal user; // 用户

	private Vector permissionSet;// 保存权限的一个LIST

	private boolean negative;

	public AclEntryImpl() {
		user = null;
		permissionSet = new Vector(10, 10);
		negative = false;
	}

	public void setNegativePermissions() {
		negative = true;
	}

	public boolean isNegative() {
		return negative;
	}

	public synchronized Object clone() {
		AclEntryImpl aclentryimpl = new AclEntryImpl(user);
		aclentryimpl.permissionSet = (Vector) permissionSet.clone();
		aclentryimpl.negative = negative;
		return aclentryimpl;
	}

	public String toString() {
		StringBuffer stringbuffer = new StringBuffer();
		if (negative) {
			stringbuffer.append("-");
		} else {
			stringbuffer.append("+");
		}
		if (user instanceof Group) {
			stringbuffer.append("Group.");
		} else {
			stringbuffer.append("User.");
		}
		stringbuffer.append(user + "=");
		Enumeration enumeration = permissions();
		do {
			if (!enumeration.hasMoreElements()) {
				break;
			}
			Permission permission = (Permission) enumeration.nextElement();
			stringbuffer.append(permission);
			if (enumeration.hasMoreElements()) {
				stringbuffer.append(",");
			}
		} while (true);
		return new String(stringbuffer);
	}

	public Principal getPrincipal() {
		return user;
	}

	public AclEntryImpl(Principal principal) {
		permissionSet = new Vector(10, 10);
		negative = false;
		user = principal;
	}

	public boolean setPrincipal(Principal principal) {
		if (user != null) {
			return false;
		} else {
			user = principal;
			return true;
		}
	}

	public boolean addPermission(Permission permission) {
		if (permissionSet.contains(permission)) {
			return false;
		} else {
			permissionSet.addElement(permission);
			return true;
		}
	}

	public boolean checkPermission(Permission permission) {
		return permissionSet.contains(permission);
	}

	public boolean removePermission(Permission permission) {
		return permissionSet.removeElement(permission);
	}

	public Enumeration permissions() {
		return permissionSet.elements();
	}
}
