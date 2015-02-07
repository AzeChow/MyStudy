package com.bestway.common.fileimport.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.logic.ContractLogic;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.logic.BcsDictPorLogic;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.common.BaseDao;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;

public class ImportDataFromExcel {

	private BaseDao baseDao = null;

	private Map cacheMap = new HashMap();

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 从Excel文档中导入数据
	 * 
	 * @param xmlFileName
	 * @param excelFileContent
	 * @param taskId
	 * @return
	 */
	public synchronized Map importData(String xmlFileName,
			byte[] excelFileContent, String taskId, boolean isCover,
			Object logic, String declareState) {
		Map contentMap = new HashMap();
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		Workbook workbook = null;
		ByteArrayInputStream byteArrayInputStream = null;
		InputStream inputStream = null;
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		List listClassName = new ArrayList();
		try {
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setEncoding("GBK");
			byteArrayInputStream = new ByteArrayInputStream(excelFileContent);
			workbook = Workbook.getWorkbook(byteArrayInputStream, wbs);
			inputStream = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							"com/bestway/common/fileimport/logic/exceldataformat/"
									+ xmlFileName);
			doc = sb.build(inputStream);
			if (doc == null) {
				return contentMap;
			}
			Element root = doc.getRootElement();
			List list = root.getChildren("sheetformat");
			checkExcelFormat(workbook, list);
			int totalCount = getTotalCount(workbook, list);
			if (info != null) {
				info.setBeginTime(System.currentTimeMillis());
				info.setTotalNum(totalCount);
				info.setCurrentNum(0);
				info.setMethodName("系统正在导入文件资料");
			}
			for (int i = 0; i < list.size(); i++) {
				Element sheetformat = (Element) list.get(i);
				String className = sheetformat.getAttribute("class").getValue();
				if (className == null || "".equals(className.trim())) {
					continue;
				}
				Class entityClass = null;
				try {
					entityClass = Class.forName(className);
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
					continue;
				}
				listClassName.add(className);
				String mutiRow = sheetformat.getAttribute("multi-row")
						.getValue();
				int sheetIndex = converStringToInteger(sheetformat
						.getAttributeValue("sheetindex")) - 1;
				Sheet sheet = workbook.getSheet(sheetIndex);
				if ("false".equals(mutiRow.trim())) {
					Map varMap = new HashMap();
					Object entity = null;
					try {
						entity = entityClass.newInstance();
					} catch (InstantiationException e) {

						e.printStackTrace();
					} catch (IllegalAccessException e) {

						e.printStackTrace();
					}
					if (entity != null) {
						List lsField = sheetformat.getChildren("field");
						StringBuffer keyField = new StringBuffer();
						StringBuffer keyValue = new StringBuffer();
						StringBuffer keyDesc = new StringBuffer();
						for (int j = 0; j < lsField.size(); j++) {
							Element fieldElement = (Element) lsField.get(j);
							// String fieldType = fieldElement
							// .getAttributeValue("class");
							String row = fieldElement.getAttributeValue("row");
							String column = fieldElement
									.getAttributeValue("column");
							String fieldName = fieldElement.getTextTrim();
							// System.out.println("sheet=" + sheet.getName());
							// System.out.println("row=" + row);
							// System.out.println("column=" + column);
							// System.out.println("fieldName=" + fieldName);
							if (fieldName == null
									|| "".equals(fieldName.trim())) {
								continue;
							}
							if (column == null || "".equals(column.trim())) {
								setVariableValue(fieldElement, entityClass,
										entity, varMap);
							} else {
								Object excelCellValue = getExcelCellValue(
										sheet, row, column);
								if (excelCellValue == null) {
									continue;
								}
								setEntityPropertyValue(entityClass, entity,
										fieldElement, excelCellValue, varMap,
										i, j);
								getKeyFieldAndKeyValue(fieldElement,
										excelCellValue, keyField, keyValue,
										keyDesc);
							}
						}

						checkDuplicate(sheetformat, entityClass,
								keyField.toString(), keyValue.toString(),
								keyDesc.toString(), isCover, logic,
								declareState);

						this.baseDao.saveOrUpdate(entity);

						if (!"".equals(keyValue.toString().trim())) {

							cacheContentData(className, entity,
									keyValue.toString());

						} else if (entity instanceof BaseEntity) {

							cacheContentData(className, entity,
									((BaseEntity) entity).getId());

						}
					}
					if (info != null) {
						info.setCurrentNum(info.getCurrentNum() + 1);
					}
				} else if ("true".equals(mutiRow.trim())) {
					String beginRowStr = sheetformat
							.getAttributeValue("beginRow");
					if (beginRowStr == null || "".equals(beginRowStr.trim())) {
						throw new RuntimeException("文件" + xmlFileName + "第"
								+ (sheetIndex + 1) + "Sheet的beginRow参数为空！");
					}
					int beginRow = converStringToInteger(beginRowStr) - 1;
					int rows = sheet.getRows();
					for (int j = beginRow; j < rows; j++) {
						Map varMap = new HashMap();
						Object entity = null;
						try {
							entity = entityClass.newInstance();
						} catch (InstantiationException e) {

							e.printStackTrace();
						} catch (IllegalAccessException e) {

							e.printStackTrace();
						}
						StringBuffer keyField = new StringBuffer();
						StringBuffer keyValue = new StringBuffer();
						StringBuffer keyDesc = new StringBuffer();
						if (entity != null) {
							List lsField = sheetformat.getChildren("field");
							for (int u = 0; u < lsField.size(); u++) {
								Element fieldElement = (Element) lsField.get(u);
								// String fieldType = fieldElement
								// .getAttributeValue("class");
								String row = String.valueOf(j + 1);// fieldElement.getAttributeValue("row");
								String column = fieldElement
										.getAttributeValue("column");
								String fieldName = fieldElement.getTextTrim();

								// System.out.println("row=" + row);
								// System.out.println("column=" + column);
								// System.out.println("fieldName=" + fieldName);
								if (fieldName == null
										|| "".equals(fieldName.trim())) {
									continue;
								}
								if (column == null || "".equals(column.trim())) {
									setVariableValue(fieldElement, entityClass,
											entity, varMap);
								} else {
									Object excelCellValue = getExcelCellValue(
											sheet, row, column);
									if (excelCellValue == null) {
										continue;
									}
									setEntityPropertyValue(entityClass, entity,
											fieldElement, excelCellValue,
											varMap, i, j);
									getKeyFieldAndKeyValue(fieldElement,
											excelCellValue, keyField, keyValue,
											keyDesc);
								}
							}
							this.baseDao.saveOrUpdate(entity);
							if (!"".equals(keyValue.toString().trim())) {
								cacheContentData(className, entity,
										keyValue.toString());
							} else if (entity instanceof BaseEntity) {
								cacheContentData(className, entity,
										((BaseEntity) entity).getId());
							}
						}
						if (info != null) {
							info.setCurrentNum(info.getCurrentNum() + 1);
						}
					}
				}
				contentMap.put(className, cacheMap.get(className));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			if (workbook != null) {
				workbook.close();
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < listClassName.size(); i++) {
			String className = listClassName.get(i).toString();
			cacheMap.remove(className);
		}
		return contentMap;
	}

	/**
	 * 获取实体总条目数
	 * 
	 * @param workbook
	 * @param list
	 * @return
	 */
	private int getTotalCount(Workbook workbook, List list) {
		int totalCount = 0;
		for (int i = 0; i < list.size(); i++) {
			Element sheetformat = (Element) list.get(i);
			String mutiRow = sheetformat.getAttribute("multi-row").getValue();
			int sheetIndex = converStringToInteger(sheetformat
					.getAttributeValue("sheetindex")) - 1;
			Sheet sheet = workbook.getSheet(sheetIndex);
			if ("false".equals(mutiRow.trim())) {
				totalCount++;
			} else {
				String beginRowStr = sheetformat.getAttributeValue("beginRow");
				if (beginRowStr == null || "".equals(beginRowStr.trim())) {
					throw new RuntimeException("xml格式文件" + "第"
							+ (sheetIndex + 1) + "Sheet的beginRow参数为空！");
				}
				int beginRow = converStringToInteger(beginRowStr) - 1;
				int rows = sheet.getRows();
				totalCount += (rows - beginRow);
			}
		}
		return totalCount;
	}

	/**
	 * 检查Excel文档格式是否符合默认格式
	 * 
	 * @param workbook
	 * @param list
	 */
	private void checkExcelFormat(Workbook workbook, List list) {
		int sheetsNum = workbook.getSheets().length;
		if (sheetsNum != list.size()) {
			throw new RuntimeException("Excel文件格式不符合要求");
		}
		for (int i = 0; i < list.size(); i++) {
			Element sheetformat = (Element) list.get(i);
			String mutiRow = sheetformat.getAttribute("multi-row").getValue();
			int sheetIndex = converStringToInteger(sheetformat
					.getAttributeValue("sheetindex")) - 1;
			Sheet sheet = workbook.getSheet(sheetIndex);
			int columnNum = sheet.getColumns();
			int rowNum = sheet.getRows();
			if ("false".equals(mutiRow.trim())) {
				int maxRowNum = this.getMaxRowNum(sheetformat);
				int maxColumnNum = this.getMaxColumnNum(sheetformat);
				if (maxRowNum > rowNum || maxColumnNum > columnNum) {
					throw new RuntimeException("Excel文件格式不符合要求");
				}
			} else {
				int maxColumnNum = this.getMaxColumnNum(sheetformat);
				if (maxColumnNum > columnNum) {
					throw new RuntimeException("Excel文件格式不符合要求");
				}
			}
		}
	}

	/**
	 * 获取格式文档中的关于某Sheet的最大行号
	 * 
	 * @param sheetformat
	 * @return
	 */
	private int getMaxRowNum(Element sheetformat) {
		int maxRowNum = 0;
		List lsField = sheetformat.getChildren("field");
		for (int i = 0; i < lsField.size(); i++) {
			Element fieldElement = (Element) lsField.get(i);
			String row = fieldElement.getAttributeValue("row");
			if (row == null || "".equals(row.trim())) {
				continue;
			}
			String[] rows = row.split("\\+");
			for (int j = 0; j < rows.length; j++) {
				if (rows[j] != null && !"".equals(rows[j].trim())) {
					int rowNum = this.converStringToInteger(rows[j].trim());
					if (maxRowNum < rowNum) {
						maxRowNum = rowNum;
					}
				}
			}
		}
		return maxRowNum;
	}

	/**
	 * 获取格式文档中的关于某Sheet的最大列号
	 * 
	 * @param sheetformat
	 * @return
	 */
	private int getMaxColumnNum(Element sheetformat) {
		int maxColumnNum = 0;
		List lsField = sheetformat.getChildren("field");
		for (int i = 0; i < lsField.size(); i++) {
			Element fieldElement = (Element) lsField.get(i);
			String column = fieldElement.getAttributeValue("column");
			if (column == null || "".equals(column.trim())) {
				continue;
			}
			String[] columns = column.split("\\+");
			for (int j = 0; j < columns.length; j++) {
				if (columns[j] != null && !"".equals(columns[j].trim())) {
					int columnNum = this.converStringToInteger(columns[j]
							.trim());
					if (maxColumnNum < columnNum) {
						maxColumnNum = columnNum;
					}
				}
			}
		}
		return maxColumnNum;
	}

	/**
	 * 根据key值来检测实体是否重复，一般用来检查表头
	 * 
	 * @param sheetformat
	 * @param entityClass
	 * @param keyField
	 * @param keyValue
	 * @param keyDesc
	 */
	private void checkDuplicate(Element sheetformat, Class entityClass,
			String keyField, String keyValue, String keyDesc, boolean isCover,
			Object logic, String declareState) {

		String className = entityClass.getName();

		Object tempObject = null;

		try {

			tempObject = entityClass.newInstance();

		} catch (InstantiationException e) {

			e.printStackTrace();

		} catch (IllegalAccessException e) {

			e.printStackTrace();

		}

		String hsql = "";

		List list = new ArrayList();

		List parameters = new ArrayList();

		if (tempObject instanceof CustomBaseEntity) {

			hsql = "select a from " + className + " as a where 2>1 ";

		} else if (tempObject instanceof BaseScmEntity) {

			hsql = "select a from " + className + " as a"
					+ " where a.company.id=? ";

			parameters.add(CommonUtils.getCompany().getId());

		} else {

			hsql = "select a from " + className + " as a where 2>1 ";

		}

		String[] keyFields = keyField.split(",");

		String[] keyValues = keyValue.split(",");

		for (int i = 0; i < keyFields.length; i++) {

			if (keyFields[i] != null && !"".equals(keyFields[i].trim())
					&& keyValues[i] != null && !"".equals(keyValues[i].trim())) {

				hsql += " and a." + keyFields[i].trim() + "=? ";

				parameters.add(keyValues[i].trim());

			}
		}

		// 补充备案状态 ------- 这里不需要再判断 直接删除所有手册号相同的旧手册
		// hsql += " and a.declareState=? ";
		//
		// parameters.add(declareState);

		list = this.baseDao.find(hsql, parameters.toArray());

		System.out.println("jCheckBox.isSelected()=" + isCover);

		if (list.size() > 0) {

			// String description = sheetformat.getAttributeValue("desc");
			System.out.println("jCheckBox.isSelected()=" + isCover);

			if (isCover) {

				System.out.println("className=" + className);

				if (list.get(0) instanceof Contract) {

					((ContractLogic) logic).deleteContract(list);

					System.out.println("删除以前手册成功");

					return;

				} else {

					if (list.get(0) instanceof BcsDictPorHead) {

						for (int i = 0; i < list.size(); i++) {

							BcsDictPorHead head = (BcsDictPorHead) list.get(i);

							((BcsDictPorLogic) logic)
									.deleteBcsDictPorHead(head);

							System.out.println("删除以前备案资料库"
									+ head.getDictPorEmsNo() + "成功");
						}

						return;

					}
				}
			}
			throw new RuntimeException("关键KEY值是:" + keyDesc + "已存在.");
		}

	}

	private void getKeyFieldAndKeyValue(Element fieldElement,
			Object excelCellValue, StringBuffer keyField,
			StringBuffer keyValue, StringBuffer keyDesc) {
		String isKeyStr = fieldElement.getAttributeValue("key");
		String fieldName = fieldElement.getTextTrim();
		String desc = fieldElement.getAttributeValue("desc");
		boolean isKey = (isKeyStr == null || "".equals(isKeyStr.trim())) ? false
				: Boolean.parseBoolean(isKeyStr.trim());
		if (isKey) {
			if (keyValue.toString().trim().equals("")) {
				keyValue.append(excelCellValue.toString().trim());
				keyField.append(fieldName);
				if (desc != null && !"".equals(desc.trim())) {
					keyDesc.append(desc + ":"
							+ excelCellValue.toString().trim());
				}
			} else {
				keyValue.append("," + excelCellValue.toString().trim());
				keyField.append("," + fieldName);
				if (desc != null && !"".equals(desc.trim())) {
					keyDesc.append("/" + desc + ":"
							+ excelCellValue.toString().trim());
				}
			}
		}
	}

	/**
	 * 如果实体对象的属性值来源于中间变量的属性值，就用此方法处理
	 * 
	 * @param fieldElement
	 * @param entityClass
	 * @param entity
	 * @param varMap
	 */
	private void setVariableValue(Element fieldElement, Class entityClass,
			Object entity, Map varMap) {
		String varValue = fieldElement.getAttributeValue("value");
		String fieldName = fieldElement.getTextTrim();
		Field field = getDeclaredField(entityClass, fieldName);
		if (field != null) {
			if (varValue != null && !"".equals(varValue.trim())) {
				String[] varValues = varValue.split("\\.");
				Object var = varMap.get(varValues[0].trim());
				if (var != null) {
					Field getField = getDeclaredField(var.getClass(),
							varValues[1].trim());
					if (getField != null) {
						try {
							PropertyUtils.setProperty(
									entity,
									field.getName(),
									PropertyUtils.getProperty(var,
											getField.getName()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				String fieldClass = field.getType().getName();
				Map dataMap = (Map) cacheMap.get(fieldClass);
				if (dataMap != null && dataMap.values() != null
						&& !dataMap.values().isEmpty()) {
					try {
						PropertyUtils.setProperty(entity, field.getName(),
								dataMap.values().iterator().next());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 绑定实体对象的属性值 参数 i 为 第几个表，j为第几行
	 * 
	 * @param entityClass
	 * @param entity
	 * @param fieldElement
	 * @param excelCellValue
	 * @param varMap
	 * 
	 */
	private void setEntityPropertyValue(Class entityClass, Object entity,
			Element fieldElement, Object excelCellValue, Map varMap, int i,
			int j) {
		String var = fieldElement.getAttributeValue("var");
		System.out.println("var----------------------------------------------"
				+ var);
		if (var != null && !"".equals(var.trim())) {
			String[] keyVar = var.split(":");
			Map dataMap = (Map) cacheMap.get(keyVar[1].trim());
			if (dataMap != null) {
				varMap.put(keyVar[0].trim(),
						dataMap.get(excelCellValue.toString().trim()));
			}
		}
		setSeperateValue(entityClass, entity, fieldElement, excelCellValue, i,
				j);
	}

	/**
	 * 缓存已经保存的实体对象
	 * 
	 * @param className
	 * @param entity
	 * @param key
	 */
	private void cacheContentData(String className, Object entity, String key) {
		Map dataMap = (Map) cacheMap.get(className);
		if (dataMap == null) {
			dataMap = new HashMap();
			cacheMap.put(className, dataMap);
		}
		dataMap.put(key.trim(), entity);
	}

	/**
	 * 根据行号和列号取得Excel单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private Object getExcelCellValue(Sheet sheet, String row, String column) {
		Object excelCellValue = null;
		if ((row != null && !"".equals(row.trim()))
				&& (column != null && !"".equals(column.trim()))) {
			int rowIndex = converStringToInteger(row.trim()) - 1;
			if (column.indexOf("+") >= 0) {
				String[] columns = column.split("\\+");
				for (int u = 0; u < columns.length; u++) {
					int columnIndex = converStringToInteger(columns[u].trim()) - 1;
					excelCellValue = plusExcelCellValue(excelCellValue,
							getExcelCellValue(sheet.getCell(columnIndex,
									rowIndex)));
				}
			} else {
				int columnIndex = converStringToInteger(column) - 1;
				excelCellValue = getExcelCellValue(sheet.getCell(columnIndex,
						rowIndex));
			}
		}
		return excelCellValue;
	}

	/**
	 * 处理字段的分隔值 比如一个Excel单元格中可能有5个进出口岸的值：iePort1/iePort2/iePort3/iePort4/iePort5
	 * 这时候就要分开处理
	 * 
	 * @param entityClass
	 * @param entity
	 * @param fieldElement
	 * @param excelCellValue
	 */
	private void setSeperateValue(Class entityClass, Object entity,
			Element fieldElement, Object excelCellValue, int i, int j) {
		String fieldName = fieldElement.getTextTrim();
		String valueSeperate = fieldElement.getAttributeValue("value-seperate");
		if (valueSeperate != null && !"".equals(valueSeperate)) {
			String[] fieldNames = fieldName.split(valueSeperate);
			String[] values = excelCellValue.toString().split(valueSeperate);
			for (int n = 0; n < values.length; n++) {
				Field field = getDeclaredField(entityClass, fieldNames[n]);
				if (field != null) {
					setMapValue(fieldElement, entity, field, values[n], i, j);
				}
			}
		} else {
			Field field = getDeclaredField(entityClass, fieldName);
			if (field != null) {
				setMapValue(fieldElement, entity, field, excelCellValue, i, j);
			}
		}
	}

	/**
	 * 处理字段的映射值，比如：进料加工=R/来料加工=U，将 “进料加工” 转换成 “R”， “来料加工” 转换成 “U”
	 * 
	 * @param fieldElement
	 * @param entity
	 * @param field
	 * @param excelCellValue
	 */
	private void setMapValue(Element fieldElement, Object entity, Field field,
			Object excelCellValue, int i, int j) {
		String valueMapStr = fieldElement.getAttributeValue("valueMap");
		Map valueMap = new HashMap();
		System.out.println("valueMapStr=" + valueMapStr);

		if (valueMapStr != null && !"".equals(valueMapStr.trim())) {
			String[] valueMaps = valueMapStr.split("/");
			for (int m = 0; m < valueMaps.length; m++) {
				String[] mapStr = valueMaps[m].split("=");
				System.out.println("[0]=" + mapStr[0]);
				System.out.println("[0]=" + mapStr[1]);
				valueMap.put(mapStr[0], mapStr[1]);
			}
		}
		System.out.println("excelcellValue=" + excelCellValue);
		if (!valueMap.isEmpty()) {
			excelCellValue = valueMap.get(excelCellValue.toString());
		}
		String fieldType = field.getType().getName();
		System.out.println("fieldType------------------------------"
				+ fieldType);
		setValue(fieldElement, entity, fieldType, field, excelCellValue, i, j);
	}

	/**
	 * 将值{excelCellValue}绑定到实体对象(entity)上
	 * 
	 * @param fieldElement
	 * @param entity
	 * @param fieldType
	 * @param field
	 * @param excelCellValue
	 */
	private void setValue(Element fieldElement, Object entity,
			String fieldType, Field field, Object excelCellValue, int i, int j) {
		if (field == null) {
			return;
		}
		Object value = null;
		if (fieldType != null && !"".equals(fieldType.trim())) {

			if ("java.lang.Integer".equals(fieldType)) {
				if (excelCellValue != null
						&& !"".equals(excelCellValue.toString().trim())) {
					value = Integer.parseInt(excelCellValue.toString());
				}
			} else if ("java.lang.String".equals(fieldType)) {
				if (excelCellValue == null)
					throw new RuntimeException("第" + i + "个表   第" + (j + 1)
							+ "行  第" + fieldElement.getAttributeValue("column")
							+ "列,数据不能为空,请检查！");
				value = excelCellValue.toString();

			} else if ("java.lang.Boolean".equals(fieldType)) {
				if (excelCellValue != null
						&& !"".equals(excelCellValue.toString().trim())) {
					value = Boolean.parseBoolean(excelCellValue.toString());
				}
			} else if ("java.lang.Double".equals(fieldType)) {
				if (excelCellValue != null
						&& !"".equals(excelCellValue.toString().trim())) {
					value = Double.parseDouble(excelCellValue.toString());
				}
			} else if ("java.util.Date".equals(fieldType)) {
				if (!"".equals(excelCellValue.toString().trim())) {
					String[] dates = excelCellValue.toString().split("-");
					if (dates != null) {
						if (dates.length < 3) {
							throw new RuntimeException(excelCellValue
									+ "不符合日期格式'yyyy-MM-dd'!");
						}
						GregorianCalendar date = new GregorianCalendar(
								converStringToInteger(dates[0]),
								converStringToInteger(dates[1]) - 1,
								converStringToInteger(dates[2]));
						value = date.getTime();
					}
				}
			} else if (fieldType.indexOf("com.bestway") >= 0) {
				String refProperty = fieldElement
						.getAttributeValue("refProperty");
				if (refProperty != null && !"".equals(refProperty.trim())) {
					value = getEntityData(fieldType, refProperty,
							excelCellValue.toString().trim());
				} else {
					Map dataMap = (Map) cacheMap.get(fieldType);
					if (dataMap != null) {
						value = dataMap.get(excelCellValue.toString().trim());
					}
				}
			}
		}
		try {
			PropertyUtils.setProperty(entity, field.getName(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将字符串转换成整型值
	 * 
	 * @param str
	 * @return
	 */
	private int converStringToInteger(String str) {
		if (str == null || "".equals(str.trim())) {
			return -1;
		}
		try {
			return Integer.parseInt(str.trim());
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 将两个Excel单元格中的值进行相加
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	private Object plusExcelCellValue(Object value1, Object value2) {
		if (value1 == null && value2 == null) {
			return null;
		}
		if (value1 != null && value2 == null) {
			return value1;
		}
		if (value1 == null && value2 != null) {
			return value2;
		}
		String className = value2.getClass().getName();
		if ("java.lang.Integer".equals(className)) {
			return Integer.parseInt(value1.toString().trim())
					+ Integer.parseInt(value2.toString().trim());
		} else if ("java.lang.Double".equals(className)) {
			return Double.parseDouble(value1.toString().trim())
					+ Double.parseDouble(value2.toString().trim());
		} else if ("java.lang.String".equals(className)) {
			return value1.toString().trim() + value2.toString().trim();
		} else {
			return value1.toString().trim() + value2.toString().trim();
		}
	}

	/**
	 * 取得Excel单元格中的值
	 * 
	 * @param cell
	 * @return
	 */
	private Object getExcelCellValue(Cell cell) {
		Object returnValue = null;
		if (cell.getType() == CellType.LABEL) {
			LabelCell labelCell = (LabelCell) cell;
			returnValue = labelCell.getString();
		} else if (cell.getType() == CellType.NUMBER) {
			NumberCell numberCell = (NumberCell) cell;
			numberCell.getNumberFormat().setMinimumFractionDigits(9);
			Double value = numberCell.getValue();
			if (value != null) {
				returnValue = value.toString().trim();
				String tempValue = returnValue.toString().trim();
				if (tempValue.substring(tempValue.length() - 2,
						tempValue.length()).equals(".0")) {
					returnValue = String.valueOf(Double.valueOf(tempValue)
							.intValue());
				}
			}
		} else if (cell.getType() == CellType.DATE) {
			DateCell dateCell = (DateCell) cell;
			Date date = dateCell.getDate();
			if (date != null) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				returnValue = bartDateFormat.format((Date) date);
			}
		} else {
			returnValue = cell.getContents();
		}
		return returnValue;
	}

	/**
	 * 取得类型为{className}的实体的在缓存中的值
	 * 
	 * @param className
	 * @param refProperty
	 * @param key
	 * @return
	 */
	private Object getEntityData(String className, String refProperty,
			String key) {
		Map dataMap = (Map) cacheMap.get(className);
		if (dataMap == null || dataMap.isEmpty()) {
			dataMap = new HashMap();
			cacheMap.put(className, dataMap);
			try {
				Class entityClass = Class.forName(className);
				if (entityClass != null) {
					Object tempObject = entityClass.newInstance();
					Field field = getDeclaredField(entityClass, refProperty);
					if (field == null) {
						return null;
					}
					String hsql = "";
					List list = new ArrayList();
					if (tempObject instanceof CustomBaseEntity) {
						hsql = "select a from " + className + " as a";
						list = this.baseDao.find(hsql);
					} else if (tempObject instanceof BaseScmEntity) {
						hsql = "select a from " + className + " as a"
								+ " where a.company.id=? ";
						list = this.baseDao
								.find(hsql, new Object[] { CommonUtils
										.getCompany().getId() });
					} else {
						hsql = "select a from " + className + " as a";
						list = this.baseDao.find(hsql);
					}
					for (int i = 0; i < list.size(); i++) {
						Object object = list.get(i);
						Object keyValue = PropertyUtils.getProperty(object,
								field.getName());
						if (keyValue == null) {
							continue;
						}
						dataMap.put(keyValue.toString().trim(), object);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (dataMap != null && !dataMap.isEmpty()) {
			Object obj = dataMap.get(key.trim());
			if (obj == null) {
				try {
					Class entityClass = Class.forName(className);
					if (entityClass != null) {
						Object tempObject = entityClass.newInstance();
						Field field = getDeclaredField(entityClass, refProperty);
						if (field == null) {
							return null;
						}
						String hsql = "";
						List list = new ArrayList();
						if (tempObject instanceof Complex) {
							hsql = "select a from CustomsComplex as a where a."
									+ field.getName() + "=?";
							list = this.baseDao.find(hsql,
									new Object[] { key.trim() });
						}
						for (int i = 0; i < list.size(); i++) {
							CustomsComplex customsComplex = (CustomsComplex) list
									.get(i);
							Complex complex = new Complex();
							PropertyUtils.copyProperties(complex,
									customsComplex);
							complex.setId(complex.getCode());
							this.baseDao.saveOrUpdate(complex);
							Object keyValue = PropertyUtils.getProperty(
									complex, field.getName());
							if (keyValue == null) {
								continue;
							}
							dataMap.put(keyValue.toString().trim(), complex);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			obj = dataMap.get(key.trim());
			return obj;
		} else {
			return null;
		}
	}

	/**
	 * 根据字段名称{fieldName}取得字段对象
	 * 
	 * @param cls
	 * @param fieldName
	 * @return
	 */
	private Field getDeclaredField(Class cls, String fieldName) {
		Field field = null;
		try {
			field = cls.getDeclaredField(fieldName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		if (field == null && !cls.getName().equals(Object.class.getName())) {
			return getDeclaredField(cls.getSuperclass(), fieldName);
		}
		return field;
	}

}
