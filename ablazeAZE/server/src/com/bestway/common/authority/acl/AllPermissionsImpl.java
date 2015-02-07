package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.acl.Permission;

// Referenced classes of package sun.security.acl:
//            PermissionImpl

public class AllPermissionsImpl extends PermissionImpl
implements Serializable
{

    public AllPermissionsImpl(String s)
    {
        super(s);
    }

    public boolean equals(Permission permission)
    {
        return true;
    }
}
