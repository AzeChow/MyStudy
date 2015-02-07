/*
 * Created on 2005-3-23
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface AuthorityClassAnnotation {
	String caption(); 
	double index() default 1;
}
