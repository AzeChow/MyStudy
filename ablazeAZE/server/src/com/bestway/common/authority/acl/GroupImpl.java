package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.Vector;

public class GroupImpl
    implements Group, Serializable
{

    private Vector groupMembers;
    private String group;

    public int hashCode()
    {
        return group.hashCode();
    }

    public String getName()
    {
        return group;
    }

    public String toString()
    {
        return group;
    }

    public GroupImpl(String s)
    {
        groupMembers = new Vector(50, 100);
        group = s;
    }

    public boolean addMember(Principal principal)
    {
        if(groupMembers.contains(principal))
        {
            return false;
        }
        if(group.equals(principal.toString()))
        {
            throw new IllegalArgumentException();
        } else
        {
            groupMembers.addElement(principal);
            return true;
        }
    }

    public boolean isMember(Principal principal)
    {
        if(groupMembers.contains(principal))
        {
            return true;
        } else
        {
            Vector vector = new Vector(10);
            return isMemberRecurse(principal, vector);
        }
    }

    public boolean removeMember(Principal principal)
    {
        return groupMembers.removeElement(principal);
    }

    public boolean equals(Group group1)
    {
        return group.equals(group1.toString());
    }

    public Enumeration members()
    {
        return groupMembers.elements();
    }

    boolean isMemberRecurse(Principal principal, Vector vector)
    {
        for(Enumeration enumeration = members(); enumeration.hasMoreElements();)
        {
            boolean flag = false;
            Principal principal1 = (Principal)enumeration.nextElement();
            if(principal1.equals(principal))
            {
                return true;
            }
            if(principal1 instanceof GroupImpl)
            {
                GroupImpl groupimpl = (GroupImpl)principal1;
                vector.addElement(this);
                if(!vector.contains(groupimpl))
                {
                    flag = groupimpl.isMemberRecurse(principal, vector);
                }
            } else
            if(principal1 instanceof Group)
            {
                Group group1 = (Group)principal1;
                if(!vector.contains(group1))
                {
                    flag = group1.isMember(principal);
                }
            }
            if(flag)
            {
                return flag;
            }
        }

        return false;
    }
}
