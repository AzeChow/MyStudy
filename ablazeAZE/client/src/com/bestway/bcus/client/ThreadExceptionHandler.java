package com.bestway.bcus.client;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler extends ExceptionHandler implements UncaughtExceptionHandler{

	public void uncaughtException(Thread t, Throwable e) {
		super.handle(e);
	}

}
