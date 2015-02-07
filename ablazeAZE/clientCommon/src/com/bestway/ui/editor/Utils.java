package com.bestway.ui.editor;

import java.awt.Color;

public class Utils {

	public static String[]	SQL_KEYS					= { "select", "top",
			"from", "where", "left", "outer", "join", "right", "group", "by",
			"order", "having", "limit", "rownum", "case", "when", "then",
			"else", "end", "and", "or", "update", "insert", "into", "set",
			"delete", "create", "drop", "index", "table","sum","count","max","min","desc","asc","like" };

	public static String[]	HSQL_KEYS					= { "select", "top",
			"from", "where", "left", "outer", "join", "right", "group", "by",
			"order", "having", "limit", "rownum", "case", "when", "then",
			"else", "end", "and", "or", "update", "insert", "into", "set",
			"delete", "create", "drop", "index", "table", "sum","count","max","min","desc","asc","like" };

	public static String[]	JAVA_KEYS					= { "public"

														};
	public static String[]	SPECIAL_SYMBOL				= { "{", "}", "\"",
			";", ":", "<", ">", ",", ".", "?", "/", "*", "(", ")", "-", "+",
			"=", "|", "&", "^", "%", "@", "!", "~", "`", "\t","'","[","]" };

	public static Color		SQL_ANNOTATE_COLOR			= new Color(0, 152, 153);
	public static Color		KEY_COLOR					= Color.BLUE;
	public static Color		STRING_COLOR			    = Color.RED;

	public static String	SQL_ROW_ANNOTATE_PREFIX		= "--";
	public static String	JAVA_ROW_ANNOTATE_PREFIX	= "//";
	public static String	TEXT_ROW_ANNOTATE_PREFIX	= "#";

}
