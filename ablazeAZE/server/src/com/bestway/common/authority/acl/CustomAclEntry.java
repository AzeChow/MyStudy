package com.bestway.common.authority.acl;

import java.io.Serializable;
import java.security.Principal;

public class CustomAclEntry
    extends AclEntryImpl
    implements Serializable {
  public CustomAclEntry() {
  }

  public CustomAclEntry(Principal principal) {
    super(principal);
  }

}
