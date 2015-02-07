package com.bestway.common;
import java.lang.annotation.*;

/**
 * 注释的保持特性，
 *     此处定义注释类型 EntityDetail 将保留至JVM装载，供JVM读取
 */
@Retention(RetentionPolicy.RUNTIME)

/*
 * 定义元注释，即允许何种程序元素具有定义的注释类型，
 *     避免在其他地方误用此注释
 */
@Target(ElementType.FIELD)

public @interface EntityItemDetail {
   String caption();
   String longcaption();
   String comment();
}
