package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Permission;
import java.util.Enumeration;

public class CustomAcl extends AclImpl implements Serializable {
	
  public CustomAcl(java.security.Principal principal, String string) {
    super(principal, string);
  }
  
  public boolean checkPermission(Principal principal, Permission permission)
  {
  	Enumeration enumer = this.getPermissions(principal);
  	while(enumer.hasMoreElements())
  	{
  		CustomPermission patternPermission = (CustomPermission)enumer.nextElement();
  		String sPattern = patternPermission.toString();
  		String s=sPattern.replaceAll("\\*",".*?");
  		if(permission.toString().matches(s))
  			return true;
  	}
  	return false;
  }

}
