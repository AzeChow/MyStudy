package com.bestway.client.util.footer;

import java.awt.Container;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.bestway.client.util.JTableListModel;

public class JFooterTable extends JTable {
	/**
	 * If this <code>JTable</code> is the <code>viewportView</code> of an
	 * enclosing <code>JScrollPane</code> (the usual situation), configure
	 * this <code>ScrollPane</code> by, amongst other things, installing the
	 * table's <code>tableHeader</code> as the <code>columnHeaderView</code>
	 * of the scroll pane. When a <code>JTable</code> is added to a
	 * <code>JScrollPane</code> in the usual way, using
	 * <code>new JScrollPane(myTable)</code>, <code>addNotify</code> is
	 * called in the <code>JTable</code> (when the table is added to the
	 * viewport). <code>JTable</code>'s <code>addNotify</code> method in
	 * turn calls this method, which is protected so that this default
	 * installation procedure can be overridden by a subclass.
	 * 
	 * @see #addNotify
	 */
	protected void configureEnclosingScrollPane() {
		Container p = getParent();
		if (p instanceof JViewport) {
			Container gp = p.getParent();
			if (gp instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) gp;
				// Make certain we are the viewPort's view and not, for
				// example, the rowHeaderView of the scrollPane -
				// an implementor of fixed columns might do this.
				JViewport viewport = scrollPane.getViewport();
				if (viewport == null || viewport.getView() != this) {
					return;
				}
				scrollPane.setColumnHeaderView(getTableHeader());
				// scrollPane.getViewport().setBackingStoreEnabled(true);
				if (scrollPane instanceof JFooterScrollPane) {
					JTableFooter tableFooter = new JTableFooter(
							getTableHeader());					
					((JFooterScrollPane) scrollPane)
							.setColumnFooterView(tableFooter);
					if(this.getModel() instanceof JTableListModel){
						JTableListModel tableModel=(JTableListModel)this.getModel();
						Map map=tableModel.getFooterMap();
						if(!map.isEmpty()){
							Iterator iterator=map.values().iterator();
							while(iterator.hasNext()){
								tableFooter.addFooterTypeInfo((TableFooterType)iterator.next());
							}
							map.clear();
						}
					}
				}
				Border border = scrollPane.getBorder();
				if (border == null || border instanceof UIResource) {
					Border scrollPaneBorder = UIManager
							.getBorder("Table.scrollPaneBorder");
					if (scrollPaneBorder != null) {
						scrollPane.setBorder(scrollPaneBorder);
					}
				}
			}
		}
	}

	// private void refresh

	/**
	 * Reverses the effect of <code>configureEnclosingScrollPane</code> by
	 * replacing the <code>columnHeaderView</code> of the enclosing scroll
	 * pane with <code>null</code>. <code>JTable</code>'s
	 * <code>removeNotify</code> method calls this method, which is protected
	 * so that this default uninstallation procedure can be overridden by a
	 * subclass.
	 * 
	 * @see #removeNotify
	 * @see #configureEnclosingScrollPane
	 * @since 1.3
	 */
	protected void unconfigureEnclosingScrollPane() {
		Container p = getParent();
		if (p instanceof JViewport) {
			Container gp = p.getParent();
			if (gp instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) gp;
				// Make certain we are the viewPort's view and not, for
				// example, the rowHeaderView of the scrollPane -
				// an implementor of fixed columns might do this.
				JViewport viewport = scrollPane.getViewport();
				if (viewport == null || viewport.getView() != this) {
					return;
				}
				scrollPane.setColumnHeaderView(null);
				if (scrollPane instanceof JFooterScrollPane) {
					((JFooterScrollPane) scrollPane).setColumnFooterView(null);
				}
			}
		}
	}

	public JTableFooter getTableFooter() {
		Container p = getParent();
		if (p instanceof JViewport) {
			Container gp = p.getParent();
			if (gp instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) gp;
				if (scrollPane instanceof JFooterScrollPane) {
					if (((JFooterScrollPane) scrollPane).getColumnFooter() != null) {
						return (JTableFooter) ((JFooterScrollPane) scrollPane)
								.getColumnFooter().getView();
					}
				}
			}
		}
		return null;
	}
}
