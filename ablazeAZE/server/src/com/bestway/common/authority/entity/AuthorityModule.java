package com.bestway.common.authority.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.bestway.common.CommonUtils;
import com.bestway.common.authority.acl.CustomPermission;
/**
 * 权限模块
 * @author Administrator
 *
 */
public class AuthorityModule implements Serializable, Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 
	 */
	private double index;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String caption;

	private List permissions = new Vector();

	/**
	 * @param permissions
	 *            The permissions to set.
	 */
	public void setPermissions(List permissions) {
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPermission(CustomPermission permission) {
		this.permissions.add(permission);
	}

	public String getCaption() {
		return caption;
	}

	public List getPermissions() {
		return permissions;
	}

	public AuthorityModule() {
	}

	public CustomPermission getPermission(int i) {
		return (CustomPermission) this.permissions.get(i);
	}

	public int getPermissionSize() {
		return this.permissions.size();
	}

	public AuthorityModule(String name, String caption) {
		this.name = name;
		this.caption = caption;
	}

	/**
	 * @return Returns the index.
	 */
	public double getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            The index to set.
	 */
	public void setIndex(double index) {
		this.index = index;
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other.getClass().equals(this.getClass())))
			return false;
		AuthorityModule castOther = (AuthorityModule) other;
		if (this.getName() == null && castOther.getName() == null) {
			return super.equals(other);
		} else {
			return new EqualsBuilder().append(this.getName(),
					castOther.getName()).isEquals();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		AuthorityModule authorityModule = (AuthorityModule) arg0;
		if (this.index - authorityModule.getIndex() > 0) {
			return 1;
		} else if (this.index - authorityModule.getIndex() < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
