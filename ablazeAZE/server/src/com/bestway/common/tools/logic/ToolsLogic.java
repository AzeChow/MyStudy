package com.bestway.common.tools.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Mappings;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.engine.Mapping;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.PersisterFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;
import org.hibernate.util.ArrayHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.common.CommonUtils;
import com.bestway.common.tools.dao.ToolsDao;
import com.bestway.common.tools.entity.DBIndex;
import com.bestway.common.tools.entity.EntityMutableTreeNode;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.common.tools.entity.TempResultSet;

public class ToolsLogic {
	private ToolsDao toolsDao = null;

	private DataSource dataSource = null;

	private List<TempNodeItem> classNames = null;

	private Map<TempNodeItem, List<TempNodeItem>> mapTempNodeItem = null;

	private Map<String, String> jbcusTables = new HashMap<String, String>();

	private String dialectClass = "";

	private Map<String, String> contentMap = new HashMap<String, String>();

	private static Log log = LogFactory.getLog(ToolsLogic.class);

	private Set set = null;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ToolsDao getToolsDao() {
		return toolsDao;
	}

	public void setToolsDao(ToolsDao toolsDao) {
		this.toolsDao = toolsDao;
	}

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 * @return
	 */
	public TempResultSet getTempResultSet(String sql) {
		//
		// 用方言断言
		// 第一种更健壮,第二种性能更好,暂选第二种(因为方言已知).
		//
		// dialectClass =
		// dialectClass==null?"":dialectClass.toLowerCase().trim();
		// boolean isOracle = dialectClass.indexOf("oracle") != -1;

		boolean isOracle = "org.hibernate.dialect.Oracle9Dialect"
				.equals(dialectClass == null ? "" : dialectClass.trim());

		TempResultSet tempRs = new TempResultSet();
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement stmt = connection.createStatement();
			resultSet = stmt.executeQuery(sql);

			ResultSetMetaData metaData = resultSet.getMetaData();

			int numberOfColumns = metaData.getColumnCount();
			String[] columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}
			//
			// Get all rows.
			//
			List<List> rows = new ArrayList<List>();
			while (resultSet.next()) {
				List<Object> newRow = new ArrayList<Object>();
				for (int i = 1; i <= columnNames.length; i++) {
					Object obj = resultSet.getObject(i);
					if (isOracle) {
						if (obj instanceof oracle.sql.TIMESTAMP) {// oracle 10
							// 的兼容性
							oracle.sql.TIMESTAMP oTimestamp = (oracle.sql.TIMESTAMP) obj;
							Timestamp t = oTimestamp.timestampValue();
							obj = t;
						}
						if (obj instanceof oracle.sql.NUMBER) {// 
							oracle.sql.NUMBER number = (oracle.sql.NUMBER) obj;
							obj = number.dateValue();
						}
					}
					newRow.add(obj);
				}
				rows.add(newRow);
			}
			resultSet.close();
			tempRs.setColumnNames(columnNames);
			tempRs.setRows(rows);
			stmt.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				//
				// 非常重要
				//
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tempRs;
	}

	/**
	 * connection 由连接池管理
	 * 
	 * @param sql
	 * @return
	 */
	public int executeUpdateSql(final String sql) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement stmt = connection.createStatement();
			int size = stmt.executeUpdate(sql);
			stmt.close();
			return size;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				//
				// 非常重要
				//
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * create drop .. DDL connection 由连接池管理
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSql(final String sql) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement stmt = connection.createStatement();
			boolean b = stmt.execute(sql);
			stmt.close();
			return b;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				//
				// 非常重要
				//
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// -------------------------------
	// 通过hibernate configuration 对象
	// 获取mapping table to class
	// 显示提示信息 ,并获取 table 结构
	// -------------------------------
	/**
	 * 获得DB表结构
	 * 
	 * @return
	 */
	public void initMapping() {
		//
		// initContentMap
		//
		initContentMap();
		//
		// 用于类名显示的时候
		//		
		classNames = new ArrayList<TempNodeItem>();
		//
		// 以表名显示的时候
		//
		mapTempNodeItem = new HashMap<TempNodeItem, List<TempNodeItem>>();
		//
		// 获得Dialect
		//
		Dialect dialect = getDialect();

		try {
			Configuration configuration = CommonUtils.getConfiguration();

			Mappings mapping = configuration.createMappings();

			// configuration.get

			Iterator<PersistentClass> iterator = configuration
					.getClassMappings();

			while (iterator.hasNext()) {
				//
				// persistentClass 对象
				//					  
				PersistentClass persistentClass = iterator.next();
				String entityName = persistentClass.getEntityName();
				Table table = persistentClass.getTable();
				// table.setName(name)

				Mapp mapp = new Mapp(persistentClass);
				//
				// 中文注释
				//
				String classContent = contentMap.get(entityName) == null ? ""
						: contentMap.get(entityName);
				//
				// init 以表名显示的树
				//
				TempNodeItem tableTempNodeItem = new TempNodeItem();
				tableTempNodeItem.setClassName(entityName);
				tableTempNodeItem.setName(table.getName());
				jbcusTables.put(table.getName(), table.getName());
				tableTempNodeItem.setCnName("类名:" + entityName + "\n表名:"
						+ table.getName() + "\n中文注释:" + classContent);
				tableTempNodeItem.setRemark(classContent);
				List<TempNodeItem> columnsTempNodeItem = new ArrayList<TempNodeItem>();
				mapTempNodeItem.put(tableTempNodeItem, columnsTempNodeItem);
				//
				// init 以类名显示的树
				//

				TempNodeItem classTempNodeItem = new TempNodeItem();
				classTempNodeItem.setClassName(entityName);
				int lastIndex = entityName.lastIndexOf(".");
				if (lastIndex > -1) {
					classTempNodeItem.setName(entityName
							.substring(lastIndex + 1));
				}
				classTempNodeItem.setCnName("表名:" + table.getName() + "\n类名:"
						+ entityName + "\n中文注释:" + classContent);
				classTempNodeItem.setRemark(classContent);
				classNames.add(classTempNodeItem);

				List<Property> entityProperties = new ArrayList<Property>();
				//
				// add id property into entityPorperties
				//
				Property propertyId = persistentClass.getIdentifierProperty();
				entityProperties.add(propertyId);
				//
				// add all not id property into entityPorperties
				//				
				Iterator<Property> propertyNotId = persistentClass
						.getPropertyClosureIterator();
				while (propertyNotId.hasNext()) {
					Property p = propertyNotId.next();
					entityProperties.add(p);
				}
				//
				// for each entityProrperties
				//				
				for (Property property : entityProperties) {
					//
					// 类字段名
					//
					String propertyName = property.getName();
					//
					// 对应的列
					//
					Iterator<Column> columns = property.getValue()
							.getColumnIterator();
					// int index = value.getColumnSpan();
					//
					// 一般只会有一个数据在其中
					//					
					while (columns.hasNext()) {
						Column column = columns.next();
						//
						// 中文注释
						//
						String key = entityName + "." + propertyName;
						String fieldContent = contentMap.get(key) == null ? ""
								: contentMap.get(key);
						String tableColumnName = column.getName();
						// System.out.println(entityName + "------- "
						// + propertyName + " ==== " + tableColumnName);
						TempNodeItem columnTempNodeItem = new TempNodeItem();
						String sqlType = column.getSqlType(dialect, mapp);
						columnTempNodeItem.setClassName(sqlType);
						columnTempNodeItem.setName(tableColumnName);
						columnTempNodeItem.setRemark(fieldContent);
						columnTempNodeItem.setCnName("类字段:" + propertyName
								+ "\n表字段:" + tableColumnName + "\n数据类型:"
								+ sqlType + "\n中文注释:" + fieldContent);
						columnsTempNodeItem.add(columnTempNodeItem);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	private Dialect getDialect() {
		try {
			Dialect dialect = (Dialect) Class.forName(dialectClass)
					.newInstance();
			return dialect;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	class Mapp implements Mapping {
		PersistentClass pc = null;

		public Mapp(PersistentClass persistentClass) {
			this.pc = persistentClass;
		}

		public Type getIdentifierType(String arg0) throws MappingException {
			return pc.getIdentifier().getType();
		}

		public String getIdentifierPropertyName(String arg0)
				throws MappingException {
			return pc.getIdentifierProperty().getName();
		}

		public Type getReferencedPropertyType(String arg0, String propertyName)
				throws MappingException {
			Property prop = pc.getReferencedProperty(propertyName);
			return prop.getType();
		}

	}

	/**
	 * 获得DB表结构
	 * 
	 * @return
	 */
	public Map<TempNodeItem, List<TempNodeItem>> getTableColumnMap() {
		return mapTempNodeItem;
	}

	/**
	 * 获得class结点集合
	 * 
	 * @return
	 */
	public List<TempNodeItem> getTempNodeItems() {
		Collections.sort(classNames);
		return classNames;
	}

	/**
	 * 获得指定class结点
	 * 
	 */
	public List<TempNodeItem> getTempNodeItems(String className) {
		for (int i = 0; i < classNames.size(); i++) {
			if (className.equalsIgnoreCase(classNames.get(i).getClassName())) {
				Collections.sort(classNames);
			}
		}
		return classNames;
	}

	/**
	 * 加入注释
	 * 
	 * @param list
	 * @param parentEntityName
	 * @return
	 */
	public List<TempNodeItem> getAnnotate(List<TempNodeItem> list,
			String parentEntityName) {

		Map<String, TempNodeItem> tempMap = new HashMap<String, TempNodeItem>();
		List<String> existPropertyColumns = new ArrayList<String>();
		//
		// 初始化 tempMap
		//	
		for (TempNodeItem temp : list) {
			String key = parentEntityName + "." + temp.getName();
			String fieldContent = contentMap.get(key) == null ? "" : contentMap
					.get(key);
			temp.setCnName("表字段: 空 " + "\n类字段:" + temp.getName() + "\n数据类型:"
					+ temp.getClassName() + "\n中文注释:" + fieldContent);
			temp.setRemark(fieldContent);
			tempMap.put(temp.getName(), temp);
		}
		try {
			Configuration configuration = CommonUtils.getConfiguration();
			Mappings mapping = configuration.createMappings();

			Iterator<PersistentClass> iterator = configuration
					.getClassMappings();

			while (iterator.hasNext()) {
				//
				// persistentClass 对象
				//
				PersistentClass persistentClass = iterator.next();
				String entityName = persistentClass.getEntityName();
				if (!parentEntityName.equals(entityName)) {
					continue;
				}

				List<Property> entityProperties = new ArrayList<Property>();
				//
				// add id property into entityPorperties
				//
				Property propertyId = persistentClass.getIdentifierProperty();
				entityProperties.add(propertyId);
				//
				// add all not id property into entityPorperties
				//				
				Iterator<Property> propertyNotId = persistentClass
						.getPropertyClosureIterator();
				while (propertyNotId.hasNext()) {
					Property p = propertyNotId.next();
					entityProperties.add(p);
				}
				//
				// for each entityProrperties
				//				
				for (Property property : entityProperties) {
					//
					// 类字段名
					//
					String propertyName = property.getName();
					//
					// 有数据库列的对应值
					//
					existPropertyColumns.add(propertyName);
					//
					// 对应的列
					//
					Iterator<Column> columns = property.getValue()
							.getColumnIterator();
					// int index = value.getColumnSpan();
					//
					// 一般只会有一个数据在其中
					//					
					while (columns.hasNext()) {
						TempNodeItem t = tempMap.get(propertyName);
						if (t == null) {
							continue;
						}
						if (property.getType() instanceof EntityType) {
							t.setClassName(property.getType().getName());
						}
						//
						// 中文注释
						//
						String key = entityName + "." + propertyName;
						String fieldContent = contentMap.get(key) == null ? ""
								: contentMap.get(key);
						Column column = columns.next();
						String tableColumnName = column.getName();
						t.setCnName("表字段:" + tableColumnName + "\n类字段:"
								+ t.getName() + "\n数据类型:" + t.getClassName()
								+ "\n中文注释:" + fieldContent);
						t.setEnName(tableColumnName);

						t.setRemark(fieldContent);
					}

				}
				break;
			}
			//
			// 返回
			//
			List<TempNodeItem> returnList = new ArrayList<TempNodeItem>();
			for (int i = 0; i < existPropertyColumns.size(); i++) {
				returnList.add(tempMap.get(existPropertyColumns.get(i)));
			}
			return returnList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	private void initContentMap() {
		contentMap = new HashMap<String, String>();
		try {
			ClassLoader ccl = Thread.currentThread().getContextClassLoader();
			InputStream in = ccl.getResourceAsStream("javadoc.xml");
			Stack<Item> stack = readContents(in);
			for (int i = 0; i < stack.size(); i++) {
				Item item = stack.get(i);
				contentMap
						.put(item.getKey(), item.getStringBuffer().toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/***************************************************
	 * <?xml version="1.0" encoding="UTF-8"?> <root caption="JBCUS帮助文档"
	 * createDate=""> <className name="" classCommentText=""> <filedName name=""
	 * fieldCommentText=""/> </className> .... </root>
	 *****************************************************/

	/**
	 * 读入注释到文件
	 * 
	 * @param classes
	 * @param filePath
	 */
	private Stack<Item> readContents(InputStream inputStream) {
		BufferedReader in = null;
		Stack<Item> stack = new Stack<Item>();
		try {
			in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			Document doc = null;
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(in);

			Element rootElement = doc.getRootElement();
			List<Element> classNameElements = rootElement
					.getChildren("className");
			for (Element classNameElement : classNameElements) {
				String className = classNameElement.getAttributeValue("name");
				String classCommentText = classNameElement
						.getAttributeValue("classCommentText");
				Item item = new Item(className, new StringBuffer(
						classCommentText));
				stack.add(item);

				List<Element> filedNameElements = classNameElement
						.getChildren("filedName");
				for (Element filedNameElement : filedNameElements) {
					String fieldName = filedNameElement
							.getAttributeValue("name");
					String fieldCommentText = filedNameElement
							.getAttributeValue("fieldCommentText");

					// System.out.println(fieldName + "  "+fieldCommentText);

					item = new Item(fieldName, new StringBuffer(
							fieldCommentText));
					stack.add(item);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
			}
		}
		return stack;
	}

	/**
	 * 
	 * @author ls
	 * 
	 */
	public static class Item {
		String key = null;

		StringBuffer stringBuffer = null;

		public Item(String key, StringBuffer stringBuffer) {
			this.key = key;
			this.stringBuffer = stringBuffer;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public StringBuffer getStringBuffer() {
			return stringBuffer;
		}

		public void setStringBuffer(StringBuffer stringBuffer) {
			this.stringBuffer = stringBuffer;
		}

	}

	public String getDialectClass() {
		return dialectClass;
	}

	public void setDialectClass(String dialectClass) {
		this.dialectClass = dialectClass;
	}

	/**
	 * Generate DDL for altering tables
	 * 
	 * @see org.hibernate.tool.hbm2ddl.SchemaUpdate
	 */
	private String[] generateSchemaUpdateScript(Dialect dialect,
			DatabaseMetadata databaseMetadata) throws HibernateException {

		Configuration conf = CommonUtils.getConfiguration();

		conf.buildMappings();

		String defaultCatalog = conf.getProperties().getProperty(
				Environment.DEFAULT_CATALOG);
		String defaultSchema = conf.getProperties().getProperty(
				Environment.DEFAULT_SCHEMA);

		ArrayList script = new ArrayList(50);

		Iterator<PersistentClass> iterator = conf.getClassMappings();

		while (iterator.hasNext()) {
			//
			// persistentClass 对象
			//					  
			PersistentClass persistentClass = iterator.next();
			Table table = persistentClass.getTable();
			Mapping mapping = new Mapp(persistentClass);
			if (jbcusTables.containsKey(table.getName())) {
				continue;
			}

			SessionFactoryImpl sessionFactoryImpl = CommonUtils
					.getSessionFactoryImpl();
			EntityPersister cp = PersisterFactory.createClassPersister(
					persistentClass, null, sessionFactoryImpl, mapping);
			Field filed;
			try {
				filed = sessionFactoryImpl.getClass().getDeclaredField(
						"entityPersisters");
				//
				// 允许访问私有定义字段
				//
				filed.setAccessible(true);
				Map map = (Map) filed.get(sessionFactoryImpl);
				String entityName = persistentClass.getEntityName();
				map.put(entityName, cp);
				int lastIndex = entityName.lastIndexOf(".") + 1;
				if (lastIndex > 0) {
					map.put(entityName.substring(lastIndex), cp);
				}
				filed.set(sessionFactoryImpl, map);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (table.isPhysicalTable()) {

				TableMetadata tableInfo = databaseMetadata.getTableMetadata(
						table.getName(), table.getSchema(), table.getCatalog(),
						false);
				if (tableInfo == null) {
					script.add(table.sqlCreateString(dialect, mapping,
							defaultCatalog, defaultSchema));
				} else {
					Iterator subiter = table.sqlAlterStrings(dialect, mapping,
							tableInfo, defaultCatalog, defaultSchema);
					while (subiter.hasNext())
						script.add(subiter.next());
				}

				Iterator comments = table.sqlCommentStrings(dialect,
						defaultCatalog, defaultSchema);
				while (comments.hasNext()) {
					script.add(comments.next());
				}

			}
		}

		iterator = conf.getClassMappings();

		while (iterator.hasNext()) {
			//
			// persistentClass 对象
			//					  
			PersistentClass persistentClass = iterator.next();
			Table table = persistentClass.getTable();
			Mapping mapping = new Mapp(persistentClass);
			if (jbcusTables.containsKey(table.getName())) {
				continue;
			}
			if (table.isPhysicalTable()) {

				TableMetadata tableInfo = databaseMetadata.getTableMetadata(
						table.getName(), table.getSchema(), table.getCatalog(),
						false);

				if (dialect.hasAlterTable()) {
					Iterator subIter = table.getForeignKeyIterator();
					while (subIter.hasNext()) {
						ForeignKey fk = (ForeignKey) subIter.next();
						if (fk.isPhysicalConstraint()) {
							boolean create = tableInfo == null
									|| (tableInfo.getForeignKeyMetadata(fk
											.getName()) == null && (
									// Icky workaround for MySQL bug:
									!(dialect instanceof MySQLDialect) || tableInfo
											.getIndexMetadata(fk.getName()) == null));
							if (create) {
								script.add(fk.sqlCreateString(dialect, mapping,
										defaultCatalog, defaultSchema));
							}
						}
					}
				}
			}
		}
		return ArrayHelper.toStringArray(script);
	}

	public boolean isJarExistHbmXml(File jar) throws MappingException {
		boolean isExist = false;
		JarFile jarFile = null;
		try {
			try {
				jarFile = new JarFile(jar);
			} catch (IOException ioe) {
				throw new MappingException(
						"Could not read mapping documents from jar: "
								+ jar.getName(), ioe);
			}
			Enumeration jarEntries = jarFile.entries();
			while (jarEntries.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) jarEntries.nextElement();
				if (ze.getName().endsWith(".hbm.xml")) {
					isExist = true;
					break;
				}
			}
		} finally {
			try {
				if (jarFile != null)
					jarFile.close();
			} catch (IOException ioe) {
				log.error("could not close jar", ioe);
			}
		}
		return isExist;
	}

	/**
	 * Execute the schema updates
	 * 
	 * @param script
	 *            print all DDL to the console 查找所有 .hbm.xml 结尾的文件 *
	 */
	public void executeSchemaUpdate(File jar, boolean script, boolean doUpdate) {

		if (!isJarExistHbmXml(jar)) {
			return;
		}
		try {
			ClassLoader thisLoader = Thread.currentThread()
					.getContextClassLoader();
			URLClassLoader cl = new URLClassLoader(new URL[] { jar.toURL() },
					thisLoader);
			Thread.currentThread().setContextClassLoader(cl);
		} catch (Exception ex) {
			ex.printStackTrace();
			new RuntimeException(ex.getMessage());
		}

		Configuration conf = CommonUtils.getConfiguration();
		conf.addJar(jar);

		log
				.info("[JBCUS] plugin ----------------- Running hbm2ddl schema update");
		Connection connection = null;
		Statement stmt = null;
		boolean autoCommitWasEnabled = true;
		Dialect dialect = getDialect();
		try {
			DatabaseMetadata meta;
			try {
				log
						.info("[JBCUS] plugin ----------------- fetching database metadata");
				connection = dataSource.getConnection();
				if (!connection.getAutoCommit()) {
					connection.commit();
					connection.setAutoCommit(true);
					autoCommitWasEnabled = false;
				}
				meta = new DatabaseMetadata(connection, dialect);
				stmt = connection.createStatement();
			} catch (SQLException sqle) {
				log
						.error(
								"[JBCUS] plugin ----------------- could not get database metadata",
								sqle);
				throw sqle;
			}

			log.info("[JBCUS] plugin ----------------- updating schema");

			String[] createSQL = this.generateSchemaUpdateScript(dialect, meta);
			for (int j = 0; j < createSQL.length; j++) {

				final String sql = createSQL[j];
				try {
					if (script)
						System.out.println(sql);
					if (doUpdate) {
						log.debug(sql);
						stmt.executeUpdate(sql);
					}
				} catch (SQLException e) {
					log.error("[JBCUS] plugin ----------------- Unsuccessful: "
							+ sql);
					log.error(e.getMessage());
				}
			}

			log.info("[JBCUS] plugin ----------------- schema update complete");

		} catch (Exception e) {
			log
					.error(
							"[JBCUS] plugin ----------------- could not complete schema update",
							e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (!autoCommitWasEnabled)
					connection.setAutoCommit(false);
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				log
						.error(
								"[JBCUS] plugin ----------------- Error closing connection",
								e);
			}
		}
	}

	public EntityMutableTreeNode getEntityMutableTreeNod(Class cls) {
		ClassMetadata mdata = toolsDao.getClassMetadata(cls);
		EntityMutableTreeNode root = new EntityMutableTreeNode();
		root.setEnytityName(cls.getSimpleName());
		root.setClassName(cls.getName());
		root.setPropertyName(cls.getSimpleName());
		String classContent = contentMap.get(cls.getName()) == null ? "无"
				: contentMap.get(cls.getName());
		root.setCnName(classContent);
		initEntityTreeNode(root, mdata);
		return root;
	}

	private void initEntityTreeNode(EntityMutableTreeNode node,
			ClassMetadata mdata) {
		String[] props = mdata.getPropertyNames();
		for (int t = 0; t < props.length; t++) {
			String prop = props[t];
			Type ty = mdata.getPropertyType(prop);
			Class c = ty.getReturnedClass();
			// --------------------------------------------------------------
			ClassMetadata tempmdata = toolsDao.getClassMetadata(c);
			Class ls = isSingleBasicType(c);
			if (ls != null) {
				EntityMutableTreeNode baseNode = new EntityMutableTreeNode();
				baseNode.setEnytityName(c.getSimpleName());
				baseNode.setClassName(c.getName());
				baseNode.setPropertyName(prop);
				String fullname = mdata.getEntityName() + "." + prop;
				String classContent = contentMap.get(fullname) == null ? "无"
						: contentMap.get(fullname);
				baseNode.setCnName(classContent);
				baseNode.setCls(ls);
				node.add(baseNode);
			} else if (tempmdata != null) {
				EntityMutableTreeNode tempNode = new EntityMutableTreeNode();
				tempNode.setEnytityName(c.getSimpleName());
				tempNode.setClassName(c.getName());
				String classContent = contentMap.get(tempmdata.getEntityName()) == null ? ""
						: contentMap.get(tempmdata.getEntityName());
				tempNode.setCnName(classContent);
				tempNode.setPropertyName(c.getSimpleName());
				node.add(tempNode);
				initEntityTreeNode(tempNode, tempmdata);
			}
		}
	}

	/**
	 * 是否是基本类型
	 * 
	 * @param cls
	 * @return
	 */
	private Class isSingleBasicType(Class cls) {
		if (cls.equals(int.class)) {
			return int.class;
		} else if (cls.equals(long.class)) {
			return long.class;
		} else if (cls.equals(short.class)) {
			return short.class;
		} else if (cls.equals(double.class)) {
			return double.class;
		} else if (cls.equals(float.class)) {
			return float.class;
		} else if (cls.equals(boolean.class)) {
			return boolean.class;
		} else if (cls.equals(Integer.class)) {
			return Integer.class;
		} else if (cls.equals(Long.class)) {
			return Long.class;
		} else if (cls.equals(Short.class)) {
			return Short.class;
		} else if (cls.equals(Double.class)) {
			return Double.class;
		} else if (cls.equals(Float.class)) {
			return Float.class;
		} else if (cls.equals(String.class)) {
			return String.class;
		} else if (cls.equals(Boolean.class)) {
			return Boolean.class;
		} else if (cls.equals(Date.class)) {
			return Date.class;
		} else if (cls.equals(Calendar.class)) {
			return Calendar.class;
		} else
			return null;
	}

	private Set getSet() {
		if (set == null) {
			set = mapTempNodeItem.keySet();
		}
		return set;
	}

	/**
	 * 从xml中读取索引数据
	 * 
	 * @return
	 */
	public List<DBIndex> getIndexInfo(boolean isYearLimitDBTable) {
		Map<String, String> mapYearLimitDBTable = new HashMap<String, String>();
		String[] yearLimitDBTables = CommonUtils.yearLimitDBTables;
		for (int i = 0, n = yearLimitDBTables.length; i < n; i++) {
			String table = yearLimitDBTables[i];
			mapYearLimitDBTable.put(table.toLowerCase(), table);
		}
		String casYear = CommonUtils.getYear();
		List<DBIndex> list = new ArrayList<DBIndex>();
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			doc = sb.build(this.getClass().getClassLoader()
					.getResourceAsStream(
							"com/bestway/common/tools/logic/DBIndex.xml"));
			Element root = doc.getRootElement();
			List listIndex = root.getChildren("index");
			for (int i = 0; i < listIndex.size(); i++) {
				DBIndex dbIndex = new DBIndex();
				Element indexElement = (Element) listIndex.get(i);
				String indexSql = indexElement.getTextTrim();
				if (indexSql == null || "".equals(indexSql.trim())) {
					continue;
				}
				String indexName = indexSql.substring(
						indexSql.indexOf(" index ") + 7,
						indexSql.indexOf(" on ")).trim();
				String tableName = indexSql.substring(
						indexSql.indexOf(" on ") + 4, indexSql.indexOf("("))
						.trim();
				String fieldNames = indexSql.substring(
						indexSql.indexOf("(") + 1, indexSql.indexOf(")"))
						.trim();
				if (mapYearLimitDBTable.get(tableName.toLowerCase().trim()) != null
						&& !casYear.equals("2005")) {
					String tableNameYear = tableName + casYear;
					String indexNameYear = indexName + casYear;
					indexSql="create index "+indexNameYear+" on "+tableNameYear+"("+fieldNames+")";
					dbIndex.setIndexName(indexNameYear);
					dbIndex.setTableName(tableNameYear);
				}else{
					dbIndex.setIndexName(indexName);
					dbIndex.setTableName(tableName);					
				}				
				dbIndex.setFieldNames(fieldNames);	
				dbIndex.setIndexSql(indexSql);
				if (isYearLimitDBTable) {
					if (mapYearLimitDBTable.get(tableName.toLowerCase().trim()) != null) {
						list.add(dbIndex);
					}
				} else {
					list.add(dbIndex);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
