package com.bestway.bcus.client.checkstock.jtreeTable;

import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.time.DateFormatUtils;

import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.common.BaseEntity;

public class AttachmentTableModel extends AbstractTreeTableModel implements TreeTableModel {
	private boolean isCellEditable = false;
	
	//列名
	public List<String> colNames;
	//数据
	public List<ECSAttachmentManagement> data;
	
	
	public AttachmentTableModel(Object root) {
		super(root);
	}
	
	//列的类型
	static protected Class[] colTypes = {
		TreeTableModel.class,String.class,String.class,
		String.class,String.class,String.class,
		String.class,String.class,String.class,
		String.class,String.class,String.class,
		String.class,String.class};
	
	//
	public AttachmentTableModel(List<String> columnNames,
			List<ECSAttachmentManagement> dataVector) {
		this.colNames = columnNames;
		this.data = dataVector;
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("ROOT");
		createNodes(top);
		this.setRoot(top);
	}
	
	public void setCellEditable(boolean isCellEditable) {
		this.isCellEditable = isCellEditable;
	}
	public boolean isCellEditable(){
		return isCellEditable;
	}

	//设置数据
	public List<ECSAttachmentManagement> getData(){
		return this.data;
	}
		
	public void setData(Vector<ECSAttachmentManagement> entityVector) {
		data = entityVector;
	}
	//创建节点 
	private void createNodes(DefaultMutableTreeNode top) {
		for (int i = 0; i < data.size(); i++) {
			ECSAttachmentManagement parentAttachment = data.get(i);
			DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parentAttachment);
			if(parentAttachment.getChildren()!=null){
				//当是否隐藏为空或者为false时候才添加节点
				if(parentAttachment.getIsHidden()==null||parentAttachment.getIsHidden()==false){
					for (int j = 0; j < parentAttachment.getChildren().size(); j++) {
						ECSAttachmentManagement childAttachment = parentAttachment.getChildren().get(j);
						DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childAttachment);
						parentNode.add(childNode);
					}
				}
				
			}
			top.add(parentNode);
		}
	}
	
	/**
	 * @return 获得节点对象
	 * @param rowIndex
	 * 
	 * */
	public Object getObjectByNode(Object node) {
		Object obj = null;
		if (node instanceof DefaultMutableTreeNode) {
			obj = ((DefaultMutableTreeNode) node).getUserObject();
		} else {
			obj = node;
		}

		return obj;
	}
	public Class<?> getColumnClass(int column) {
		return colTypes[column];
	}

	@Override
	public int getColumnCount() {
		return colNames.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return colNames.get(column);
	}
	/**
	 * 获取行数据
	 * @param index
	 * @return
	 */
	public <T> T getRowData(int index){
		return (T)data.get(index);
	}
	
	@Override
	public boolean isCellEditable(Object node, int column) {
		if(column==8){
			return true;
		}
		if(column != 0 && isCellEditable == false)
			return false;
		ECSAttachmentManagement attachment = (ECSAttachmentManagement)getObjectByNode(node);
		if(attachment.getParentId() == null){
			return column == 0;
		}else {
			return column < 5||column==6;
		}	
	}
	
	@Override
	public Object getValueAt(Object node, int column) {  //渲染表格 显示数据		
		ECSAttachmentManagement attachment = null;
		Object useObj = getObjectByNode(node);
		if (useObj == null) {
			return null;
		}
		if(useObj instanceof BaseEntity){
			attachment=(ECSAttachmentManagement) useObj;
		}else{
			return column == 0 ? useObj.toString() : "";
		}
		
		switch (column) { //现在我把名称渲染到第二列
		case 0:
			return attachment.getProjectContent();
		case 1:
			return attachment.getDataContent();
		case 2:
			return attachment.getProvideUnit();
		case 3:
			return attachment.getDataType();
		case 4:
			return attachment.getRequirements();
		case 5:
			return attachment.getUsingSeal();
		case 6:
			return attachment.getDataSignature();
		case 7:
			return attachment.getAttachmentName();
		case 8:
			return attachment.getControlsState();
		case 9:
			if(attachment.getCreateDate()==null){
				return null;
			}else{
				return DateFormatUtils.format(attachment.getCreateDate(),"yyyy-MM-dd");
			}
		case 10:
			return attachment.getCreatePeople();
		case 11:
			if(attachment.getUpdateDate()==null){
				return null;
			}else{
				return DateFormatUtils.format(attachment.getUpdateDate(),"yyyy-MM-dd");
			}
		case 12:
			return attachment.getUpdatePeople();
		case 13:
			if(attachment.getIsHidden()==null||attachment.getIsHidden()==false){
				return "";
			}else{
				return "是";	
			}
		}
		
		return null;
	}
	
	@Override
	public void setValueAt(Object aValue, Object node, int column) {
		ECSAttachmentManagement attachment = ((ECSAttachmentManagement)getObjectByNode(node));
		String value = (String)aValue;
		switch (column) { //现在我把名称渲染到第二列
		case 0:
			attachment.setProjectContent(value);
			break;
		case 1:
			attachment.setDataContent(value);
			break;
		case 2:
			attachment.setProvideUnit(value);
			break;
		case 3:
			attachment.setDataType(value);
			break;
		case 4:
			attachment.setRequirements(value);
			break;
		case 5:
			attachment.setUsingSeal(value);
			break;
		case 6:
			attachment.setDataSignature(value);
			break;
		}
		
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof DefaultMutableTreeNode) {
			return ((DefaultMutableTreeNode) parent).getChildAt(index);
		} else {
			return null;
		}
	}

	@Override
	public int getChildCount(Object parent) {
		Object o = getObjectByNode(parent);
		if(o instanceof ECSAttachmentManagement){
			ECSAttachmentManagement att =((ECSAttachmentManagement)o);
			List<ECSAttachmentManagement> ch = att.getChildren();
			return ch == null || Boolean.TRUE.equals(att.getIsHidden()) ? 0 : ch.size();
		}else{
			return ((DefaultMutableTreeNode)parent).getChildCount();
		}
	}

	 public boolean isLeaf(Object node) {
		Object o = getObjectByNode(node);
		if(o instanceof ECSAttachmentManagement){
			String pid = ((ECSAttachmentManagement)getObjectByNode(node)).getParentId();
		    return pid != null;
		}
		return ((DefaultMutableTreeNode)node).getChildCount() == 0;
	 }
}
