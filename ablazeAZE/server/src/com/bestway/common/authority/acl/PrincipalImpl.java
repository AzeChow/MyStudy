package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;

public class PrincipalImpl
    implements Principal,Serializable
{

    private String user;

    public int hashCode()
    {
        return user.hashCode();
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof PrincipalImpl)
        {
            PrincipalImpl principalimpl = (PrincipalImpl)obj;
            return user.equals(principalimpl.toString());
        } else
        {
            return false;
        }
    }

    public String getName()
    {
        return user;
    }

    public String toString()
    {
        return user;
    }

    public PrincipalImpl(String s)
    {
        user = s;
    }
}
