package com.bestway.util;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.bestway.jptds.client.ExceptionHandler;

/**
 * swing异步请求工具 使用SwingWorker的线程池执行 异步请求，服务器返回数据后，使用swing安全线程执行success(T e);方法
 *
 * @author xc
 * @param <T>
 */
public abstract class AsynSwingWorker<T> {

	private int progress;

	/**
	 * 发送请求方法
	 *
	 * @return
	 */
	protected abstract T submit();

	/**
	 * 发起一步异步之前
	 */
	protected void beforeSubmit() {
	}

	/**
	 * 发起异步请求之后
	 */
	protected void afterSubmit() {
	}

	/**
	 * 请求成功处理方法。(swing线程安全)
	 *
	 * @param result
	 */
	protected void success(T result) {
	}

	/**
	 * 执行异常处理
	 *
	 * @param e
	 */
	protected void errorHandler(Throwable e) {
		new ExceptionHandler().handle(e);
	}

	/**
	 * 开启请求操作
	 */
	public void doWork() {
		new Worker().execute();
	}

	/**
	 * 异步执行工具
	 *
	 * @author xc
	 */
	class Worker extends SwingWorker<T, Object> {

		long t = 0;

		/**
		 * 异步开启请求
		 */
		@Override
		protected T doInBackground() throws Exception {

			t = System.currentTimeMillis();
			beforeSubmit();
			try {
				return submit();
			} finally {
				System.out.println("【服务器处理耗时】:"
						+ (System.currentTimeMillis() - t) / 1000d + "秒");
				afterSubmit();
			}
		}

		/**
		 * 完成请求
		 */
		@Override
		protected void done() {
			try {
				T t = this.get();
				success(t);
			} catch (InterruptedException e) {
				errorHandler(e);
			} catch (ExecutionException e) {
				errorHandler(e.getCause());
			} finally {
				System.out.println("【操作耗时】:" + (System.currentTimeMillis() - t)
						/ 1000d + "秒");
			}
		}
	}

	public static void main(String[] args) {
		AsynSwingWorker<String> a = new AsynSwingWorker<String>() {
			protected String submit() {
				System.out.println(Thread.currentThread().getName() + "submit");
				return "test submit";
			}

			protected void success(String e) {
				System.out.println(Thread.currentThread().getName()
						+ "success:" + e);
			}
		};
		System.out.println(Thread.currentThread().getName() + ":main method");
		for (int i = 0; i < 20; i++) {
			a.doWork();
		}
	}
}
