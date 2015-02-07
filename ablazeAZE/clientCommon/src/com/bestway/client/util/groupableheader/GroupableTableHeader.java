/*
 * (swing1.1beta3)
 * 
 */

package com.bestway.client.util.groupableheader;

import java.awt.Graphics;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * GroupableTableHeader
 * 
 * @version 1.0 10/20/98
 * @author Nobuo Tamemasa
 */

public class GroupableTableHeader extends JTableHeader {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.JTableHeader#setUI(javax.swing.plaf.TableHeaderUI)
	 */
	public void setUI(TableHeaderUI ui) {
		if (ui instanceof GroupableTableHeaderUI) {
			super.setUI(ui);
		}
	}

	private static final String uiClassID = "GroupableTableHeaderUI";

	protected Vector columnGroups = null;

	public GroupableTableHeader(TableColumnModel model) {
		super(model);
		setUI(new GroupableTableHeaderUI());
		setReorderingAllowed(false);
	}

	public void setReorderingAllowed(boolean b) {
		reorderingAllowed = false;
	}

	public void addColumnGroup(ColumnGroup g) {
		if (columnGroups == null) {
			columnGroups = new Vector();
		}
		columnGroups.addElement(g);
	}

	public Enumeration getColumnGroups(TableColumn col) {
		if (columnGroups == null)
			return null;
		Enumeration enumGroup = columnGroups.elements();
		while (enumGroup.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) enumGroup.nextElement();
			Vector v_ret = (Vector) cGroup.getColumnGroups(col, new Vector());
			if (v_ret != null) {
				return v_ret.elements();
			}
		}
		return null;
	}

	public Vector getColumnGroups(){
		return columnGroups;
	}
	
	public void setColumnMargin() {
		if (columnGroups == null)
			return;
		int columnMargin = getColumnModel().getColumnMargin();
		Enumeration enumColumn = columnGroups.elements();
		while (enumColumn.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) enumColumn.nextElement();
			cGroup.setColumnMargin(columnMargin);
		}
	}
	
	public int getTableHeaderLayerCount(){
		int layer=1;
		if(columnGroups==null){
			return layer;
		}
		Enumeration enumGroup = columnGroups.elements();
		while (enumGroup.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) enumGroup.nextElement();
			int m=getGroupLayerCount(cGroup,1);
			if(m>layer){
				layer=m;
			}
		}
		return layer;
	}
	
	private int getGroupLayerCount(ColumnGroup group,int layer){
		Enumeration enumGroup =group.v.elements();
		if(enumGroup==null){
			return layer;
		}
		layer++;
		while (enumGroup.hasMoreElements()) {
			Object obj= enumGroup.nextElement();
			if(obj instanceof ColumnGroup){
				ColumnGroup cGroup = (ColumnGroup) obj;//enumGroup.nextElement();
				return getGroupLayerCount(cGroup ,layer);
			}else if (obj instanceof TableColumn){
				return layer;
			}
		}
		return layer;
	}

}

