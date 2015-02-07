package com.bestway.bcus.client.checkstock.jtreeTable;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.*;
import javax.swing.table.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.EventObject;

/**
 * This example shows how to create a simple JTreeTable component, by using a
 * JTree as a renderer (and editor) for the cells in a particular column in the
 * JTable.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class JTreeTable extends JTable {
	/** A subclass of JTree. */
	protected TreeTableCellRenderer tree;
	private String textProperty;

	public String getTextProperty() {
		return textProperty;
	}

	public void setTextProperty(String textProperty) {
		this.textProperty = textProperty;
	}

	public JTreeTable(TreeTableModel treeTableModel) {
		super();
		init(treeTableModel);
	}
	
	public void setModel(TreeTableModel treeTableModel){
		init(treeTableModel);
	}
	
	private void init(TreeTableModel treeTableModel){
		// Creates the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(treeTableModel);
		tree.setRootVisible(false);
		// Installs a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Forces the JTable and JTree to share their row selection models.
		ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
		tree.setSelectionModel(selectionWrapper);
		setSelectionModel(selectionWrapper.getListSelectionModel());

		// Installs the tree editor renderer and editor.
		setDefaultRenderer(TreeTableModel.class, tree);
		setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
		TableCellEditor cellEditor = getDefaultEditor(String.class);		
		if(cellEditor instanceof DefaultCellEditor){
			((DefaultCellEditor)cellEditor).setClickCountToStart(1);
		}
		// 显示格子线
		setShowGrid(true);
		//不显示垂直线条
		setShowVerticalLines(false);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		// No intercell spacing
		setIntercellSpacing(new Dimension(0,2));

		// And update the height of the trees row to match that of
		// the table.
//				if (tree.getRowHeight() < 1) {
//					// Metal looks better like this.
//				}
		setRowHeight(35);
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root));
	}
	/**
	 * 展开所有节点
	 * @param tree
	 * @param parent
	 */
	private void expandAll(TreeTableCellRenderer tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path);
				tree.expandPath(parent); 
			}
		}
	}

	
	/**
	 * Overridden to message super and forward the method to the tree. Since the
	 * tree is not actually in the component hierarchy it will never receive
	 * this unless we forward it in this manner.
	 */
	public void updateUI() {
		super.updateUI();
		if (tree != null) {
			tree.updateUI();
			// Do this so that the editor is referencing the current renderer
			// from the tree. The renderer can potentially change each time
			// laf changes.
			setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor());
		}
		// Use the tree's default foreground and background colors in the
		// table.
		LookAndFeel.installColorsAndFont(this, "Tree.background",
				"Tree.foreground", "Tree.font");
	}

	/**
	 * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
	 * resize the editor. The UI currently uses different techniques to paint
	 * the renderers and editors; overriding setBounds() below is not the right
	 * thing to do for an editor. Returning -1 for the editing row in this case,
	 * ensures the editor is never painted.
	 */
	public int getEditingRow() {
		return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1
				: editingRow;
	}

	/**
	 * Returns the actual row that is editing as <code>getEditingRow</code> will
	 * always return -1.
	 */
	private int realEditingRow() {
		return editingRow;
	}

	/**
	 * This is overridden to invoke super's implementation, and then, if the
	 * receiver is editing a Tree column, the editor's bounds is reset. The
	 * reason we have to do this is because JTable doesn't think the table is
	 * being edited, as <code>getEditingRow</code> returns -1, and therefore
	 * doesn't automatically resize the editor for us.
	 */
	public void sizeColumnsToFit(int resizingColumn) {
		super.sizeColumnsToFit(resizingColumn);
		if (getEditingColumn() != -1
				&& getColumnClass(editingColumn) == TreeTableModel.class) {
			Rectangle cellRect = getCellRect(realEditingRow(),getEditingColumn(), false);
			Component component = getEditorComponent();
			component.setBounds(cellRect);
			component.validate();
		}
	}

	/**
	 * Overridden to pass the new rowHeight to the tree.
	 */
	public void setRowHeight(int rowHeight) {
		super.setRowHeight(rowHeight);
		if (tree != null && tree.getRowHeight() != rowHeight) {
			tree.setRowHeight(getRowHeight());
		}
	}

//	@Override
//	public void changeSelection(int rowIndex, int columnIndex, boolean toggle,
//			boolean extend) {
//		boolean editable = ((TreeTableModelAdapter)getModel()).getModel().isCellEditable();
//		if(editable){
//			super.changeSelection(rowIndex, columnIndex, toggle, extend);
//			super.editCellAt(rowIndex, columnIndex, null);
//		}
//	}
	/**
	 * Returns the tree that is being shared between the model.
	 */
	public JTree getTree() {
		return tree;
	}

	/**
	 * Overridden to invoke repaint for the particular location if the column
	 * contains the tree. This is done as the tree editor does not fill the
	 * bounds of the cell, we need the renderer to paint the tree in the
	 * background, and then draw the editor over it.
	 */
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean retValue = super.editCellAt(row, column, e);
		if (retValue && getColumnClass(column) == TreeTableModel.class) {
			repaint(getCellRect(row, column, false));
		}
		return retValue;
	}

	/**
	 * 一个TreeCellRender JTree的子类 用语显示一棵树
	 */
	public class TreeTableCellRenderer extends JTree implements
			TableCellRenderer {
		/** Last table/tree row asked to renderer. */
		protected int visibleRow;
		/**
		 * Border to draw around the tree, if this is non-null, it will be
		 * painted.
		 */
		protected Border highlightBorder;

		public TreeTableCellRenderer(TreeModel model) {
			super(model);
		}
		
		/**
		 * updateUI is overridden to set the colors of the Tree's renderer to
		 * match that of the table.
		 */
		public void updateUI() {
			super.updateUI();
			// Make the tree's cell renderer use the table's cell selection
			// colors.
			TreeCellRenderer tcr = getCellRenderer();
			if (tcr instanceof DefaultTreeCellRenderer) {
				DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
				// For 1.1 uncomment this, 1.2 has a bug that will cause an
				// exception to be thrown if the border selection color is
				// null.
				// dtcr.setBorderSelectionColor(null);
				dtcr.setTextSelectionColor(UIManager
						.getColor("Table.selectionForeground"));
				dtcr.setBackgroundSelectionColor(UIManager
						.getColor("Table.selectionBackground"));
			}
		}

		/**
		 * Sets the row height of the tree, and forwards the row height to the
		 * table.
		 */
		public void setRowHeight(int rowHeight) {
			if (rowHeight > 0) {
				super.setRowHeight(rowHeight);
				if (JTreeTable.this != null
						&& JTreeTable.this.getRowHeight() != rowHeight) {
					JTreeTable.this.setRowHeight(getRowHeight());
				}
			}
		}

		/**
		 * This is overridden to set the height to match that of the JTable.
		 */
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, 0, w, JTreeTable.this.getHeight());
		}

		/**
		 * Sublcassed to translate the graphics such that the last visible row
		 * will be drawn at 0,0.
		 */
		public void paint(Graphics g) {
			g.translate(0, -visibleRow * getRowHeight());
			super.paint(g);
			// Draw the Table border if we have focus.
			if (highlightBorder != null) {
				highlightBorder.paintBorder(this, g, 0, visibleRow
						* getRowHeight(), getWidth(), getRowHeight());
			}
		}
		@Override
		public String convertValueToText(Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
//			if(leaf){
//				 if(value instanceof DefaultMutableTreeNode){
//					value = ((DefaultMutableTreeNode)value).getUserObject();
//					try {
//						if(value instanceof ECSAttachmentManagement){
//							ECSAttachmentManagement attachment = (ECSAttachmentManagement)value;
//							return attachment.getProjectContent();
//						}
//						return value.toString();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
			
			if(value instanceof DefaultMutableTreeNode){
				value = ((DefaultMutableTreeNode)value).getUserObject();
				try {
					if(value instanceof String){
						return (String)value;
//						ECSAttachmentManagement attachment = (ECSAttachmentManagement)value;
//						Pro
//						return attachment.getProjectContent();
					}else{
						return BeanUtils.getSimpleProperty(value, textProperty);
					}
//					return value.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
		}
		/**
		 * TreeCellRenderer method. Overridden to update the visible row.
		 */
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Color background;
			Color foreground;
			
			if (isSelected) {
				background = table.getSelectionBackground();
				foreground = table.getSelectionForeground();
			} else {
				background = table.getBackground();
				foreground = table.getForeground();
			}
			highlightBorder = null;
			if (realEditingRow() == row && getEditingColumn() == column) {
				background = UIManager.getColor("Table.focusCellBackground");
				foreground = UIManager.getColor("Table.focusCellForeground");
			} else if (hasFocus) {
				highlightBorder = UIManager
						.getBorder("Table.focusCellHighlightBorder");
				if (isCellEditable(row, column)) {
					background = UIManager
							.getColor("Table.focusCellBackground");
					foreground = UIManager
							.getColor("Table.focusCellForeground");
				}
			}

			visibleRow = row;
			setBackground(background);			
			TreeCellRenderer tcr = getCellRenderer();
			
			if (tcr instanceof DefaultTreeCellRenderer) {
				DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
//				ImageIcon image = new ImageIcon("/1.png");
				ImageIcon fileOpen = new ImageIcon(getClass().getResource("FileOpen.png"));
				ImageIcon fileClose = new ImageIcon(getClass().getResource("FileClose.png"));
				ImageIcon fileNewDatabase = new ImageIcon(getClass().getResource("FileNewDatabase.png"));
				ImageIcon excel = new ImageIcon(getClass().getResource("excel.png"));
				ImageIcon ppt = new ImageIcon(getClass().getResource("ppt.png"));
				ImageIcon print = new ImageIcon(getClass().getResource("print.png"));
				ImageIcon word = new ImageIcon(getClass().getResource("word.png"));
				dtcr.setOpenIcon(fileOpen);
				dtcr.setClosedIcon(fileClose);
				
				TreePath treePath = tree.getPathForRow(row);
				Object obj = ((DefaultMutableTreeNode)treePath.getLastPathComponent()).getUserObject();
				if(obj instanceof ECSAttachmentManagement){
					ECSAttachmentManagement  attachment = (ECSAttachmentManagement)obj;
					if(ECSAttachmentManagement.UPLOAD_AND_DOWNLOAD.equals(attachment.getControlsState())){
						String extensions = getFileExtension(attachment.getAttachmentName());
						if(extensions==null){
							dtcr.setLeafIcon(fileNewDatabase);
						}else if(extensions.toLowerCase().equals("xls")||extensions.toLowerCase().equals("xlsx")){
							dtcr.setLeafIcon(excel);
						}else if(extensions.toLowerCase().equals("doc")||extensions.toLowerCase().equals("docx")){
							dtcr.setLeafIcon(word);
						}else if(extensions.toLowerCase().equals("ppt")||extensions.toLowerCase().equals("pptx")){
							dtcr.setLeafIcon(ppt);
						}else{
							dtcr.setLeafIcon(fileNewDatabase);
						}
					}else if(ECSAttachmentManagement.EXPORP_EXCEL.equals(attachment.getControlsState())){
						dtcr.setLeafIcon(excel);
					}else if(ECSAttachmentManagement.PRINT.equals(attachment.getControlsState())){
						dtcr.setLeafIcon(print);
					}
				}
				if (isSelected) {
					dtcr.setTextSelectionColor(foreground);
					dtcr.setBackgroundSelectionColor(background);
				} else {
					dtcr.setTextNonSelectionColor(foreground);
					dtcr.setBackgroundNonSelectionColor(background);
				}
			}			
			return this;
		}
	}
	
	private String getFileExtension(String fileName) {
		if(fileName == null)
			return null;
		int index = fileName.lastIndexOf(".");
		if (index >= 0 && fileName.length() > index + 2) {
			return fileName.substring(index + 1);
		}
		return null;
	}

	public class TreeTableCellEditor extends DefaultCellEditor {
		public TreeTableCellEditor() {
			super(new TreeTableTextField());
		}

		/**
		 * Overridden to determine an offset that tree would place the editor
		 * at. The offset is determined from the <code>getRowBounds</code> JTree
		 * method, and additionally from the icon DefaultTreeCellRenderer will
		 * use.
		 * <p>
		 * The offset is then set on the TreeTableTextField component created in
		 * the constructor, and returned.
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int r, int c) {
			Component component = super.getTableCellEditorComponent(table,
					value, isSelected, r, c);
//			JTree t = getTree();
//			boolean rv = t.isRootVisible();
//			int offsetRow = rv ? r : r - 1;
//			Rectangle bounds = t.getRowBounds(offsetRow);
//			if(bounds == null)
//				return component;
//			int offset = bounds.x;
//			TreeCellRenderer tcr = t.getCellRenderer();
//			if (tcr instanceof DefaultTreeCellRenderer) {
//				Object node = t.getPathForRow(offsetRow).getLastPathComponent();
//				Icon icon;
//				if (t.getModel().isLeaf(node))
//					icon = ((DefaultTreeCellRenderer) tcr).getLeafIcon();
//				else if (tree.isExpanded(offsetRow))
//					icon = ((DefaultTreeCellRenderer) tcr).getOpenIcon();
//				else
//					icon = ((DefaultTreeCellRenderer) tcr).getClosedIcon();
//				if (icon != null) {
//					offset += ((DefaultTreeCellRenderer) tcr).getIconTextGap()
//							+ icon.getIconWidth();
//				}
//			}
//			((TreeTableTextField) getComponent()).offset = offset;
//			System.out.println(value);
			return component;
		}

		/**
		 * This is overridden to forward the event to the tree. This will return
		 * true if the click count >= 3, or the event is null.
		 */
		public boolean isCellEditable(EventObject e) {
			if (e instanceof MouseEvent) {
				MouseEvent me = (MouseEvent) e;
				// If the modifiers are not 0 (or the left mouse button),
				// tree may try and toggle the selection, and table
				// will then try and toggle, resulting in the
				// selection remaining the same. To avoid this, we
				// only dispatch when the modifiers are 0 (or the left mouse
				// button).
				if (me.getModifiers() == 0
						|| me.getModifiers() == InputEvent.BUTTON1_MASK) {
					for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
						if (getColumnClass(counter) == TreeTableModel.class) {
							MouseEvent newME = new MouseEvent(
									JTreeTable.this.tree, me.getID(),
									me.getWhen(), me.getModifiers(), me.getX()- getCellRect(0, counter, true).x,
									me.getY(), me.getClickCount(),
									me.isPopupTrigger());
							JTreeTable.this.tree.dispatchEvent(newME);
							break;
						}
					}
				}
				boolean editable = ((TreeTableModelAdapter)getModel()).getModel().isCellEditable();
				if (editable && me.getClickCount() >= 1) {
					return true;
				}
				return false;
			}
			if (e == null) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Component used by TreeTableCellEditor. The only thing this does is to
	 * override the <code>reshape</code> method, and to ALWAYS make the x
	 * location be <code>offset</code>.
	 */
	static class TreeTableTextField extends JTextField {
		public int offset;

		public void reshape(int x, int y, int w, int h) {
			int newX = Math.max(x, offset);
			super.reshape(newX, y, w - (newX - x), h);
		}
	}

	/**
	 * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to
	 * listen for changes in the ListSelectionModel it maintains. Once a change
	 * in the ListSelectionModel happens, the paths are updated in the
	 * DefaultTreeSelectionModel.
	 */
	class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {
		/** Set to true when we are updating the ListSelectionModel. */
		protected boolean updatingListSelectionModel;

		public ListToTreeSelectionModelWrapper() {
			super();
			getListSelectionModel().addListSelectionListener(
					createListSelectionListener());
		}

		/**
		 * Returns the list selection model. ListToTreeSelectionModelWrapper
		 * listens for changes to this model and updates the selected paths
		 * accordingly.
		 */
		ListSelectionModel getListSelectionModel() {
			return listSelectionModel;
		}

		/**
		 * This is overridden to set <code>updatingListSelectionModel</code> and
		 * message super. This is the only place DefaultTreeSelectionModel
		 * alters the ListSelectionModel.
		 */
		public void resetRowSelection() {
			if (!updatingListSelectionModel) {
				updatingListSelectionModel = true;
				try {
					super.resetRowSelection();
				} finally {
					updatingListSelectionModel = false;
				}
			}
			// Notice how we don't message super if
			// updatingListSelectionModel is true. If
			// updatingListSelectionModel is true, it implies the
			// ListSelectionModel has already been updated and the
			// paths are the only thing that needs to be updated.
		}

		/**
		 * Creates and returns an instance of ListSelectionHandler.
		 */
		protected ListSelectionListener createListSelectionListener() {
			return new ListSelectionHandler();
		}

		/**
		 * If <code>updatingListSelectionModel</code> is false, this will reset
		 * the selected paths from the selected rows in the list selection
		 * model.
		 */
		protected void updateSelectedPathsFromSelectedRows() {
			if (!updatingListSelectionModel) {
				updatingListSelectionModel = true;
				try {
					// This is way expensive, ListSelectionModel needs an
					// enumerator for iterating.
					int min = listSelectionModel.getMinSelectionIndex();
					int max = listSelectionModel.getMaxSelectionIndex();

					clearSelection();
					if (min != -1 && max != -1) {
						for (int counter = min; counter <= max; counter++) {
							if (listSelectionModel.isSelectedIndex(counter)) {
								TreePath selPath = tree.getPathForRow(counter);

								if (selPath != null) {
									addSelectionPath(selPath);
								}
							}
						}
					}
				} finally {
					updatingListSelectionModel = false;
				}
			}
		}

		/**
		 * Class responsible for calling updateSelectedPathsFromSelectedRows
		 * when the selection of the list changse.
		 */
		class ListSelectionHandler implements ListSelectionListener {
			public void valueChanged(ListSelectionEvent e) {
				updateSelectedPathsFromSelectedRows();
			}
		}
	}
}
