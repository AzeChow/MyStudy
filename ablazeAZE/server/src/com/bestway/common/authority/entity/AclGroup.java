package com.bestway.common.authority.entity;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 用户群组
 * @author Administrator
 *
 */
public class AclGroup extends BaseScmEntity  implements Group {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 组名称
	 */
	private String groupName; 
	/**
	 * 组描述
	 */
	private String groupDescription; 

	/**
	 * 此对象不在持久化范围之内，只提供acl内部计算时使用
	 */
	private Set aclUsers;

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public void setAclUsers(Set aclUsers) {
		this.aclUsers = aclUsers;
	}

	public String getGroupName() {
		return groupName;
	}

	public Set getAclUsers() {
		if (aclUsers == null) {
			aclUsers = new HashSet();
		}
		return aclUsers;
	}

	/** default constructor */
	public AclGroup() {
	}

	/** minimal constructor */
	public AclGroup(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	/**
	 * getName
	 * 
	 * @return String
	 */
	public String getName() {
		return getId().toString();
	}

	/**
	 * addMember
	 * 
	 * @param user
	 *            Principal
	 * @return boolean
	 */
	public boolean addMember(Principal user) {
		this.getAclUsers().add(user);
		return true;
	}

	/**
	 * isMember
	 * 
	 * @param member
	 *            Principal
	 * @return boolean
	 */
	public boolean isMember(Principal member) {
		return this.getAclUsers().contains(member);
	}

	/**
	 * removeMember
	 * 
	 * @param user
	 *            Principal
	 * @return boolean
	 */
	public boolean removeMember(Principal user) {
		this.getAclUsers().remove(user);
		return true;
	}

	/**
	 * members
	 * 
	 * @return Enumeration
	 */
	public Enumeration members() {
		return new Enumeration() {
			int count = 0;

			Iterator iterator = getAclUsers().iterator();

			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			public Object nextElement() {
				return iterator.next();
			}
		};

	}
}