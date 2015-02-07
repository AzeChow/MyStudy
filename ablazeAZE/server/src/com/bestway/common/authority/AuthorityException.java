package com.bestway.common.authority;

public class AuthorityException extends RuntimeException{
  public AuthorityException() {
    super("|未授权的异常");
  }
  public AuthorityException(String message) {
    super("|"+message);
  }

}
