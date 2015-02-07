package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.acl.Permission;

public class PermissionImpl
    implements Permission,Serializable
{

    private String permission;

    public int hashCode()
    {
        return toString().hashCode();
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Permission)
        {
            Permission permission1 = (Permission)obj;
            return permission.equals(permission1.toString());
        } else
        {
            return false;
        }
    }

    public String toString()
    {
        return permission;
    }

    public PermissionImpl(String s)
    {
        permission = s;
    }

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
