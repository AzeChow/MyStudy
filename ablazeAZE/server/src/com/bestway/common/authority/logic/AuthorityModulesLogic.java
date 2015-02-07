package com.bestway.common.authority.logic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import org.springframework.aop.framework.AopProxyUtils;

import com.bestway.common.BaseAction;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.acl.CustomPermission;
import com.bestway.common.authority.entity.AuthorityModule;

public class AuthorityModulesLogic {
	private static AuthorityModulesLogic parseAuthorityModules = null;

	private AuthorityModule[] modules = null;

	private Hashtable permissions = new Hashtable();

	public static AuthorityModulesLogic getInstance() {
		if (parseAuthorityModules == null) {
			parseAuthorityModules = new AuthorityModulesLogic();
		}
		return parseAuthorityModules;
	}

	private AuthorityModulesLogic() {
		this.parseModule();
	}

	public AuthorityModule[] getModules() {
		return modules;
	}

	private void parseModule() {
		if (modules != null) {
			return;
		}
		try {
			List actionBeans = new ArrayList();
			String[] beanNames = CommonUtils.getContext().getBeanDefinitionNames();
			for (int i = 0; i < beanNames.length; i++) {
				Object obj = CommonUtils.getContext().getBean(beanNames[i]);
				if (obj instanceof BaseAction) {
					actionBeans.add(obj);
				}
			}
			int iCount = actionBeans.size();
			List lsModules = new ArrayList();
			for (int i = 0; i < iCount; i++) {
				if (!AopProxyUtils.getTargetClass(actionBeans.get(i))
						.isAnnotationPresent(AuthorityClassAnnotation.class)) {
					actionBeans.get(i).getClass().getAnnotation(
							AuthorityClassAnnotation.class);
					continue;
				}
				AuthorityClassAnnotation classAnnotation = (AuthorityClassAnnotation) AopProxyUtils
						.getTargetClass(actionBeans.get(i)).getAnnotation(
								AuthorityClassAnnotation.class);
				AuthorityModule module = new AuthorityModule();
				
//				AuthorityModule moduleRemove = new AuthorityModule();
//				List lsOld = new ArrayList();
//				if(lsModules.size()>0){
//					for(int l=0;l<lsModules.size();l++){
//						moduleRemove=(AuthorityModule) lsModules.get(l);
//						if(moduleRemove.getCaption().equalsIgnoreCase(classAnnotation.caption())){
//							lsOld=moduleRemove.getPermissions();
//							lsModules.remove(l);
//						}
//					}
//				}
				
				module.setName(AopProxyUtils.getTargetClass(actionBeans.get(i))
						.getName());
				module.setCaption(classAnnotation.caption());
				module.setIndex(classAnnotation.index());
				Method[] methods = AopProxyUtils.getTargetClass(actionBeans.get(i)).getMethods();
				HashSet hsModules = new HashSet();
				int mCount = methods.length;
				for (int j = 0; j < mCount; j++) {
					Method m = methods[j];
					if (m.isAnnotationPresent(AuthorityFunctionAnnotation.class)) {
						AuthorityFunctionAnnotation functionAnnotation = (AuthorityFunctionAnnotation) m
								.getAnnotation(AuthorityFunctionAnnotation.class);
						String methodName = functionAnnotation.caption();
						CustomPermission permission = new CustomPermission(
								methodName);
						permission.setIndex(functionAnnotation.index());
						permission.setName(AopProxyUtils.getTargetClass(actionBeans.get(i)).getName());
						hsModules.add(permission);
						permissions.put(methodName, permission);
					}
				}
				List ls = new ArrayList();
				ls.addAll(hsModules);
				
//				ls.addAll(lsOld);
				
				Collections.sort(ls);
				module.setPermissions(ls);
				
				lsModules.add(module);
			}
			modules = new AuthorityModule[lsModules.size()];
			Collections.sort(lsModules);
			for (int i = 0; i < lsModules.size(); i++) {
				modules[i] = (AuthorityModule) lsModules.get(i);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return Returns the permissions.
	 */
	public Hashtable getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions
	 *            The permissions to set.
	 */
	public void setPermissions(Hashtable permissions) {
		this.permissions = permissions;
	}

	public String getPermissionCaption(String methodName) {
		CustomPermission permission = (CustomPermission) permissions
				.get(methodName);
		if (permission != null) {
			return permission.toString();
		} else {
			return "";
		}
	}
}