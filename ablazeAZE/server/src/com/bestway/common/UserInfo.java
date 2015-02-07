package com.bestway.common;

public class UserInfo {
  public UserInfo() {
  }

  //用户的标识, 可能为用户的编号,也可能是用户的一个GUID(如果有的话).
  private String userId = null;
  //用户的姓名或名称或描述
  private String userName = null;

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public String getUserName() {
  	return userName;
    
  }



}
