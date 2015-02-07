package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;

// Referenced classes of package sun.security.acl:
//            GroupImpl

public class WorldGroupImpl extends GroupImpl 
implements Serializable
{

    public WorldGroupImpl(String s)
    {
        super(s);
    }

    public boolean isMember(Principal principal)
    {
        return true;
    }
}
