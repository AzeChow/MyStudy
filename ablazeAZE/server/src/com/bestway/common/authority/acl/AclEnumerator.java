package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.acl.Acl;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

final class AclEnumerator
    implements Enumeration,Serializable
{

    Acl acl;
    Enumeration u1;
    Enumeration u2;
    Enumeration g1;
    Enumeration g2;

    public boolean hasMoreElements()
    {
        return u1.hasMoreElements() || u2.hasMoreElements() || g1.hasMoreElements() || g2.hasMoreElements();
    }

    public Object nextElement()
    {    	
        Acl acl1 = acl;

        if(u1.hasMoreElements())
        {
            return u1.nextElement();
        }
        if(u2.hasMoreElements()){
        	return u2.nextElement();
        }
       	if(g1.hasMoreElements()){
       		return g1.nextElement();
       	}     	
      	if(!g2.hasMoreElements()) {
        	return g2.nextElement();        
        }
 		throw new NoSuchElementException("Acl Enumerator");
    }

    AclEnumerator(Acl acl1, Hashtable hashtable, Hashtable hashtable1, Hashtable hashtable2, Hashtable hashtable3)
    {
        acl = acl1;
        u1 = hashtable.elements();
        u2 = hashtable2.elements();
        g1 = hashtable1.elements();
        g2 = hashtable3.elements();
    }
}
