package com.bestway.common.authority.acl;

import java.io.Serializable;

public class CustomPermission extends PermissionImpl implements Serializable,Comparable{
	private double index;
    private String name;
//  private String caption;
//  private String permissionType;

//  public String getPermissionType() {
//    return permissionType;
//  }

    public String getName() {
    	return name;
    }

//  public void setCaption(String caption) {
//    this.caption = caption;
//  }

//  public void setPermissionType(String permissionType) {
//    this.permissionType = permissionType;
//  }

    public void setName(String name) {
      this.name = name;
    }

//  public String getCaption() {
//    return caption;
//  }

  
  public CustomPermission(String string) {
    super(string);
  }

	/**
	 * @return Returns the index.
	 */
	public double getIndex() {
		return index;
	}
	/**
	 * @param index The index to set.
	 */
	public void setIndex(double index) {
		this.index = index;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		CustomPermission customPermission=(CustomPermission)arg0;	
		if(this.index-customPermission.getIndex()>0){
			return 1;
		}else if(this.index-customPermission.getIndex()<0){
			return -1;
		}else{ 
			return 0;
		}
	}
}
