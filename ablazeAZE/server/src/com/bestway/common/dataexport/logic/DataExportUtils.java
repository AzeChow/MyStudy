package com.bestway.common.dataexport.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.bestway.common.dataexport.entity.JDBCDataSource;

/**
 * @author luosheng 2006/9/1
 *         <ul>
 *         <li> 1.格式化 当导入的字段值是通过 SQL 来转换时的SQL
 *         </ul>
 * 
 */
public class DataExportUtils {

	/**
	 * @param test
	 *            args
	 */
	public static void main(String[] args) {

		String sql = " select a from 	" +
				" table a where "
				+ " a.id = @id"
				+ "\n"
				+ "and"
				+ "\t"
				+ "a.name > @name "
				+ " or a.spec<@spec "
				+ " and a.1>=@1 "
				+ " and a.2<=@2 "
				+ " and (a.3!=@3) and "
				+ "a.x >= x and a.bb is not null a.b not in(a,b,c,d,e,f) and a.id lik @like";

		List<Item> items = getItems(sql);
		for (Item item : items) {
			System.out.println("key -------------" + item.getKey());
			System.out.println("value------------" + item.getValue());
		}

		System.out.println(sql);
		System.out.println(getTestSql(sql, "1!=1"));
		System.out.println(getCacheSql(sql, "1=1"));
		System.out.println(getParaSql(sql));
		
		System.out.println("tableName == "+getTableNameBySql(sql));

	}

	/**
	 *  例子:
	 *  if( sql = select a.name from table a where a.id = @id && repateExpress = "1=1"){ 
	 *      	return cacheSql = select a.id ,a.name from table a where 1=1 
	 *  } 	��û���SQL
	 */
	public static String getCacheSql(String sql, String repateExpress) {
		String testSql = getTestSql(sql, repateExpress);
		List<Item> items = getItems(sql);
		//
		// ���cache���,substring select
		//
		String cacheSql = testSql.trim().substring(6);
		String tempStr = "";
		for (Item item : items) {
			tempStr += item.getKey() + ",";
		}
		if(cacheSql.toLowerCase().trim().startsWith("distinct")){
			cacheSql = cacheSql.trim().substring(8);
			cacheSql = "select distinct " + tempStr + cacheSql;
		}else{
			cacheSql = "select " + tempStr + cacheSql;
		}
		return cacheSql;
	}

	/**
	 *  例子:
	 *  if( sql = select a.name from table a where a.id = @id && repateExpress = "1!=1"){ 
	 *      	return testSql = select a.name from table a where 1!=1 
	 *  } 	��û���SQL
	 */
	public static String getTestSql(String sql, String repateExpress) {
		char[] chars = sql.toCharArray();

		List<String> paras = new ArrayList<String>();
		Stack<String> fullStack = new Stack<String>();

		String s = "";
		for (char c : chars) {
			if (c == '=' || c == ' ' || c == '>' || c == '<' || c == '!'
					|| c == '\n' || c == '\t' || c == '(' || c == ')') {
				fullStack.push(s);
				fullStack.push(String.valueOf(c));
				if (s.trim().equals("")) {
					continue;
				}
				if (s.startsWith("@") && !s.endsWith("@")) {
					paras.add(s);
				}
				s = "";
			} else {
				s += c;
			}
		}
		//
		// 加入最后一个字符序列
		//
		if (!s.equals("")) {
			if (s.startsWith("@") && !s.endsWith("@")) {
				paras.add(s);
			}
			fullStack.push(s);
		}

		Stack<String> newFullStack = new Stack<String>();
		while (fullStack.size() > 0) {
			String value = fullStack.pop();
			if (paras.contains(value)) {
				String key = fullStack.pop();
				while (key.trim().equals("") || key.trim().equals("=")
						|| key.trim().equals(">") || key.trim().equals("<")
						|| key.trim().equals("!") || key.trim().equals("\n")
						|| key.trim().equals("\t")) {
					key = fullStack.pop();
				}
				newFullStack.push(repateExpress);
			} else {
				newFullStack.push(value);
			}
		}
		String testSql = "";
		while (newFullStack.size() > 0) {
			testSql += newFullStack.pop();
		}
		return testSql;
	}

	
	
	/**
	 *  例子:
	 *  if( sql = select a.name from table a where a.id = @id ){ 
	 *      	return tableName = table 
	 *  } 	��û���SQL
	 */
	public static String getTableNameBySql(String sql) {
		char[] chars = sql.toCharArray();

		String s = "";
		String beforeWord = "";
		String tableName = "";
		for (char c : chars) {
			if (c == ' ' || c == '\n' || c == '\t') {
				if (s.trim().equals("")) {
					continue;
				}
				if (beforeWord.equalsIgnoreCase("from")) {
					tableName = s;
				}
				beforeWord = s;
				s = "";				
			} else {
				s += c;
			}
		}
		return tableName;
	}

	
	
	/**
	 *  返回例子:
	 *  select * from table a where a.id = @id
	 *  return ---> 
	 * 	item.key = a.id
	 * 	item.value = id
	 * */
	public static List<Item> getItemsValueNoAt(String sql) {
		List<Item> items = getItems(sql);
		for(Item item : items){
			item.setValue(item.getValue()!= null?item.getValue().substring(1):item.getValue() );
		}		
		return items;
	}
	
	/**
	 *  返回例子:
	 *  select * from table a where a.id = @id
	 *  return ---> 
	 * 	item.key = a.id
	 * 	item.value = @id
	 * */
	public static List<Item> getItems(String sql) {
		char[] chars = sql.toCharArray();

		Stack<String> stack = new Stack<String>();
		List<String> paras = new ArrayList<String>();
		Stack<Item> items = new Stack<Item>();

		String s = "";
		for (char c : chars) {
			if (c == '=' || c == ' ' || c == '>' || c == '<' || c == '!'
					|| c == '\n' || c == '\t' || c == '(' || c == ')') {
				if (s.trim().equals("")) {
					continue;
				}
				if (s.startsWith("@") && !s.endsWith("@")) {
					paras.add(s);
				}
				stack.push(s);
				s = "";
			} else {
				s += c;
			}
		}
		//
		// 加入最后一个字符序列
		//
		if (!s.equals("")) {
			if (s.startsWith("@") && !s.endsWith("@")) {
				paras.add(s);
			}
			stack.push(s);
		}

		while (stack.size() > 0) {
			String value = stack.pop();
			if (paras.contains(value)) {
				String key = stack.pop();
				Item item = new Item(key, value);
				items.push(item);
			}
		}

		List<Item> itemsList = new ArrayList<Item>();
		while (items.size() > 0) {
			Item item = items.pop();
			itemsList.add(item);
		}
		return itemsList;

	}

	/** 用参数值替代参数 把 @parameter 转成 ?  */
	public static String getParaSql(String sql) {
		if (sql == null || sql.trim().equals("")) {
			return "";
		}
		char[] chars = sql.toCharArray();

		List<String> paras = new ArrayList<String>();
		Stack<String> fullStack = new Stack<String>();

		String s = "";
		for (char c : chars) {
			if (c == '=' || c == ' ' || c == '>' || c == '<' || c == '!'
					|| c == '\n' || c == '\t' || c == '(' || c == ')') {
				fullStack.push(s);
				fullStack.push(String.valueOf(c));
				if (s.trim().equals("")) {
					continue;
				}
				if (s.startsWith("@") && !s.endsWith("@")) {
					paras.add(s);
				}
				s = "";
			} else {
				s += c;
			}
		}
		//
		// 加入最后一个字符序列
		//
		if (!s.equals("")) {
			if (s.startsWith("@") && !s.endsWith("@")) {
				paras.add(s);
			}
			fullStack.push(s);
		}

		Stack<String> paraFullStack = new Stack<String>();
		while (fullStack.size() > 0) {
			String value = fullStack.pop();
			if (paras.contains(value)) {
				paraFullStack.push("?");
			} else {
				paraFullStack.push(value);
			}
		}
		String paraSql = "";
		while (paraFullStack.size() > 0) {
			paraSql += paraFullStack.pop();
		}
		return paraSql;
	}

	public static class Item {
		private String	key		= null;
		private String	value	= null;

		public Item(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	
	
	
	
	public static boolean isProgressDataSource(JDBCDataSource dataSource) {
		String url = dataSource.getUrl().trim();
		if (url.indexOf("jdbc:JdbcProgress") > -1) {
			return true;
		}
		return false;
	}

}
