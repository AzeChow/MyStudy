package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Group;
import java.security.acl.LastOwnerException;
import java.security.acl.NotOwnerException;
import java.security.acl.Owner;
import java.util.Enumeration;

// Referenced classes of package sun.security.acl:
//            GroupImpl

public class OwnerImpl
    implements Owner,Serializable
{

    private Group ownerGroup;

    public OwnerImpl(Principal principal)
    {
        ownerGroup = new GroupImpl("AclOwners");
        ownerGroup.addMember(principal);
    }

    public synchronized boolean isOwner(Principal principal)
    {
        return ownerGroup.isMember(principal);
    }

    public synchronized boolean addOwner(Principal principal, Principal principal1)
        throws NotOwnerException
    {
        if(!isOwner(principal))
        {
            throw new NotOwnerException();
        } else
        {
            ownerGroup.addMember(principal1);
            return false;
        }
    }

    public synchronized boolean deleteOwner(Principal principal, Principal principal1)
        throws NotOwnerException, LastOwnerException
    {
        if(!isOwner(principal))
        {
            throw new NotOwnerException();
        }
        Enumeration enumeration = ownerGroup.members();
        Object obj = enumeration.nextElement();
        if(enumeration.hasMoreElements())
        {
            return ownerGroup.removeMember(principal1);
        } else
        {
            throw new LastOwnerException();
        }
    }
}
