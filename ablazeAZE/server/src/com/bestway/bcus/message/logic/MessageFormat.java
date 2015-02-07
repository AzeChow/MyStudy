/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcus.message.entity.EMSDataType;

/**
 * @author 陈海鹏
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageFormat {
	//	    cdsFields: TClientDataSet; //保存上一次GetFields方法后的字段
	private List uniqueField;//保存上一次GetFields方法后的SectionFlag表中的唯一索引

	private Section section;
	
	private List<String> classNames;

	private String sSectionFlag;

	private Element message;

	public MessageFormat(InputStream formatStream) {
		section = null;
		uniqueField = new Vector();		
		classNames = new Vector<String>();

		SAXBuilder sax = new SAXBuilder();
		Document doc;
		try {
			doc = sax.build(formatStream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		message = doc.getRootElement();
	}

	private boolean checkSectionFlag(String sSectionFlag) {
		if (sSectionFlag.equals(this.sSectionFlag)) {
			return true;
		}
		uniqueField.clear();
		section=null;
		//tableNames.clear();不需要清除
		this.sSectionFlag = sSectionFlag;
		return false;
	}

	//List中为Field对象的集合
	public List<Field> getUniqueField(String sSectionFlag) {
		checkSectionFlag(sSectionFlag);
		if (section == null) {
			section = getSection(sSectionFlag);
		}
		return section.getFields();
	}

	public Section getSection(String sSectionFlag) {
		@SuppressWarnings("unchecked")
		List<Element> messageChildren = message.getChildren();
		for (int i = 0; i < messageChildren.size(); i++) {
			Element tempElement = messageChildren.get(i);
			if (!tempElement.getName().equals("section")) {
				continue;
			}
			Element sectionFlag = tempElement.getChild("section-flag");
			if (sectionFlag != null) {
				if (sectionFlag.getText().equals(sSectionFlag)) {
					return new Section(tempElement);
				} else
					continue;
			}
		}

		return null;
	}

	public List<String> getClassNames() {
		if (message == null) {
			throw new RuntimeException("报文格式文件节点时出错, 可能是报文格式文件不正确");
		}
		if (classNames.size() > 0) {
			return classNames;
		}
		for (int i = 0; i < message.getChildren().size(); i++) {
			Element tempElement = (Element) message.getChildren().get(i);
			if (!tempElement.getName().equals("section")) {
				continue;
			}
			
			classNames.add(tempElement.getChildText("class"));
			
		}
		return classNames;
	}
	
	
	public static void main(String[] args) {
		InputStream is = MessageFormat.class.getClassLoader().getResourceAsStream(
				"com/bestway/bcus/message/messageformat/Msg_EMS223.xml");
		MessageFormat mf = new MessageFormat(is);
		Object o = mf.getUniqueField("<" + EMSDataType.EMS_EDI_EXG + ">");
		System.out.println(o.toString());
	}
}

class Section{
	private String description;
	private String sectionFlag;
	private String className;
	private List<Field> fields = new Vector<Field>();
	private List<Field> uniqueFields = new Vector<Field>();
	
	public Section(Element section){
		description = section.getChildText("description");
		sectionFlag = section.getChildText("section-flag");
		className = section.getChildText("class");
		for(int i=0;i<section.getChildren().size();i++){
			Element child = ((Element)section.getChildren().get(i));
			if(!child.getName().equals("field")){
				continue;
			}
			fields.add(new Field(child));
		}
		
		for (int i = 0; i < fields.size(); i++) {
			if ((fields.get(i)).getUniqueIndexFlag().trim().equals("1")){					
				this.uniqueFields.add(( fields.get(i)));
			}
		}
	}
	
	//List中为Field对象的集合
	public List<Field> getUniqueField(){
		return uniqueFields;
	}
	
	/**
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return Returns the fields.
	 */
	public List<Field> getFields() {
		return fields;
	}
	/**
	 * @return Returns the sectionFlag.
	 */
	public String getSectionFlag() {
		return sectionFlag;
	}
}

class Field{
	private String description;
	private String fieldName;
	private String type;
	private String format;
	private String customWriteFlag;
	private String uniqueIndexFlag;
    

    public Field(Element field){
    	description = field.getChildText("description");
    	fieldName = field.getChildText("field-name");
    	type = field.getChildText("type");
    	format = field.getChildText("format");
    	uniqueIndexFlag = field.getChildText("UniqueIndexFlag");
    	customWriteFlag = field.getChildText("CustomWriteFlag");
    }

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return Returns the fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return Returns the uniqueIndexFlag.
	 */
	public String getUniqueIndexFlag() {
		return uniqueIndexFlag;
	}
	/**
	 * @return Returns the customWriteFlag.
	 */
	public String getCustomWriteFlag() {
		return customWriteFlag;
	}
}

