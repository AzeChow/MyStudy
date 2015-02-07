/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.authority.action;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityException;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.AuthorityLog;
import com.bestway.common.authority.entity.OperationLogs;
import com.bestway.common.authority.logic.AuthorityLogic;

/**
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AuthorityAdvice implements MethodBeforeAdvice {
	/**
	 * Log4J Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AuthorityAdvice.class);

	private AuthorityLogic authorityLogic = null;

	/**
	 * @return Returns the authorityLogic.
	 */
	public AuthorityLogic getAuthorityLogic() {
		return authorityLogic;
	}

	/**
	 * @param authorityLogic
	 *            The authorityLogic to set.
	 */
	public void setAuthorityLogic(AuthorityLogic authorityLogic) {
		this.authorityLogic = authorityLogic;
	}
	
	public AuthorityAdvice() {
	}

	public void before(Method method, Object[] objectArray, Object object)
			throws Throwable {
		if(CommonUtils.isLoadingSessionFactory()){
			throw new RuntimeException("系统正在重新加载服务器，请稍候再继续操作！");
		}
		//
		// 没有一个参数不进行权限验证
		//
		if (objectArray == null || objectArray.length <= 0) {
			return;
		}
		Request request = null;
		for (int i = 0; i < objectArray.length; i++) {
			if (objectArray[i] instanceof Request) {
				request = (Request) objectArray[i];
				break;
			}
		}
		//
		// methodName == checkPermission or request == null 不进行权限验证
		//
		String methodName = method.getName();
		if (methodName.equals("checkPermission") || request == null) {
			return;
		}
		//
		// 模块名 == 类名
		//
		String moduleName = object.getClass().getName();
		if (moduleName == null) {
			logger.warn("ModuleName is null. ObejctClass:"
					+ object.getClass().getName() + "; MethodName:"
					+ methodName);
		}
		if (object.getClass().isAnnotationPresent(
				AuthorityClassAnnotation.class)) {
			AuthorityClassAnnotation classAnnotation = (AuthorityClassAnnotation) object
					.getClass().getAnnotation(AuthorityClassAnnotation.class);
			request.setModuleCaption(classAnnotation.caption());
		} else {
			request.setModuleCaption("");
		}
		String permission = "";
		//
		// 获得真正 method 名和参数个数相同的方法 (与当前方法)
		//
		for (Method m : Class.forName(object.getClass().getName()).getMethods()) {
			if (method.getName().equals(m.getName())
					&& objectArray.length == m.getParameterTypes().length) {
				method = m;
				break;
			}
		}
		if (method.isAnnotationPresent(AuthorityFunctionAnnotation.class)) {
			permission = ((AuthorityFunctionAnnotation) method
					.getAnnotation(AuthorityFunctionAnnotation.class))
					.caption();
			request.setModuleName(moduleName);
			request.setPermission(permission);
		} else {
			CommonUtils.setRequest(request);
			return;
		}
		if (request.getModuleName() == null) {
			request.setModuleName("");
		}
		if (request.getPermission() == null) {
			request.setPermission("");
		}
		if (request.getUser() != null) {
			request.setIpAddresss(CommonUtils
					.makeIPTo12Num(CommonUtils.getIp()));
		}

		// request.setIpAddresss("127.000.000.001");
		CommonUtils.setRequest(request);

		OperationLogs log = new OperationLogs();
		log.setUser(request.getUser());
		log.setModuleCaption(request.getModuleCaption());
		log.setPermission(permission);
		log.setOperateDate(new Date());
		log.setOperateIP(CommonUtils.getIp());
		authorityLogic.saveOperationLogs(log);
//		StringBuffer log=new StringBuffer();
//		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS");
//		log.append("<UserId>"+request.getUser().getLoginName()+"</UserId>\n");
//		log.append("<UserName>"+request.getUser().getUserName()+"</UserName>\n");
//		log.append("<ModuleCaption>"+request.getModuleCaption()+"</ModuleCaption>\n");
//		log.append("<Permission>"+permission+"</Permission>\n");
//		log.append("<OperateDate>"+dateFormat.format(new Date())+"</OperateDate>\n");
//		log.append("<OperateIP>"+CommonUtils.getIp()+"</OperateIP>");
//		new WriteAuthorityLog(log.toString()).start();
		//
		// 进行权限校验
		//
		boolean user = authorityLogic.checkPermission(request);
		boolean perip = authorityLogic.checkipPermission(request);
		if (user || perip) {
			//
			// 获得参数类型
			//
			String parmstring = "";
			for (int i = 0; i < objectArray.length; i++) {
				if (objectArray[i] != null) {
					parmstring += objectArray[i].getClass().getName();
				} else {
					parmstring += "null";
				}
			}
//			AuthorityLog.outinfo("\t" + request.getUser().getUserName() + "\t"
//					+ moduleName + "\t" + methodName + "\t" + parmstring,
//					logger);
			return;
		} else {
			throw new AuthorityException("用户'"
					+ request.getUser().getUserName() + "'<IP:"
					+ CommonUtils.getIp() + ">没有<"
					+ request.getModuleCaption() + "> 模块的 <"
					+ request.getPermission() + "> 方法的权限，请联系管理员");

			// throw new AuthorityException("非法IP'" + request.getIpAddresss()
			// + "'正试图对未授权的方法<" + request.getModuleCaption()
			// + "> 模块的 <" + request.getPermission() + "> 方法进行访问");

		}

	}
	
	class WriteAuthorityLog extends Thread{
		private String loginfo=null;
		public WriteAuthorityLog(String loginfo){
			this.loginfo=loginfo;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
//			super.run();
			AuthorityLog.outinfo(loginfo);
		}
		
	}

}
