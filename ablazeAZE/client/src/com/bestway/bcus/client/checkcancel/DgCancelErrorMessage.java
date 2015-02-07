/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DgErrorMessage.java
 *
 * Created on Mar 21, 2009, 11:33:32 AM
 */
package com.bestway.bcus.client.checkcancel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author lyh
 */
public class DgCancelErrorMessage extends JDialogBase {

    private JTableListModel jTableListModel = null;
    public List list = null;  //  @jve:decl-index=0:

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public JTableListModel getJTableListModel() {
		return jTableListModel;
	}

	public void setJTableListModel(JTableListModel tableListModel) {
		jTableListModel = tableListModel;
	}

	/** Creates new form DgErrorMessage */
    public DgCancelErrorMessage() {
        super();
        initComponents();
    }

    public void setVisible(boolean f) {
        if (f) {
            initOther();
            super.setVisible(f);
        }
    }

    private void initOther() {
        FlowLayout dl = new FlowLayout();
        dl.setAlignment(FlowLayout.CENTER);
        jToolBar1.setLayout(dl);
        initTable(list);
    }

    private JTableListModel initTable(List list) {
    	JTableListModelAdapter  jTableListModelAdapter = new JTableListModelAdapter(){
    		public List InitColumns(){
    			List<JTableListColumn> list = new Vector<JTableListColumn>();
    			list.add(addColumn("错误对象", "title", 150));
                list.add(addColumn("错误原因", "message", 500)); //
                list.add(addColumn("允许申请", "isAllow", 70)); //
                list.add(addColumn("检查时间", "checkDate", 100)); //
                return list;
    		}
    	};
        jTable1.getTableHeader().setReorderingAllowed(false);//设置用户是否可以拖动列头，以重新排序各列。
		JTableListModel.dataBind(jTable1, list, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		jTableListModel = (JTableListModel) jTable1.getModel();
		jTableListModel.setAllowSetValue(true);
		jTableListModelAdapter.setEditableColumn(3);
		jTable1.getColumnModel().getColumn(3).setHeaderRenderer(
				new CheckBoxHeader("isAllow"));
		jTable1.getColumnModel().getColumn(3).setCellRenderer(
				new TableCheckBoxRender());
        return jTableListModel;
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("数据检查");
        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setRollover(true);
        jButton1.setText("确定");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_END);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(25);
        jScrollPane1.setViewportView(jTable1);
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-441)/2, (screenSize.height-472)/2, 841, 500);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;

	//表头渲染器
	class CheckBoxHeader extends JCheckBox implements TableCellRenderer,
			MouseListener{
	    protected CheckBoxHeader rendererComponent;
	    protected int column;
	    protected JTable table;
	    protected boolean mousePressed = false;
	    protected boolean isShowChangeState = false;
	    protected String propertyName = "isSelected";
	    public CheckBoxHeader() {
	        this(false, "isSelected");
	    }
	    public CheckBoxHeader(boolean isShowChangeState) {
	        this(isShowChangeState, "isSelected");
	    }
	    public CheckBoxHeader(String propertyName) {
	        this(false, propertyName);
	    }
	    public CheckBoxHeader(boolean isShowChangeState, String propertyName) {
	        this.isShowChangeState = isShowChangeState;
	        this.propertyName = propertyName;
	        this.setBorderPainted(true);
	        rendererComponent = this;
	        rendererComponent.addItemListener(new ItemListener() {
	            @Override
	            public void itemStateChanged(ItemEvent e) {
	                int row=table.getSelectedRow();
	                JTableListModel tableModel = (JTableListModel) table.getModel();
	                if (CheckBoxHeader.this.isShowChangeState) {
	                    int rows = table.getRowCount();
	                    for (int i = 0; i < rows; i++) {
	                        tableModel.setValueAt(CheckBoxHeader.this.isSelected(), i, column);
	                    }
	                } else {
	                    List list = tableModel.getList();
	                    for (int i = 0; i < list.size(); i++) {
							Object obj = list.get(i);
							try {
								BeanUtils.setProperty(obj,
										CheckBoxHeader.this.propertyName,
										CheckBoxHeader.this.isSelected());
							} catch (IllegalAccessException ex) {
								Logger
										.getLogger(
												CheckBoxHeader.class.getName())
										.log(Level.SEVERE, null, ex);
							} catch (InvocationTargetException ex) {
								Logger
										.getLogger(
												CheckBoxHeader.class.getName())
										.log(Level.SEVERE, null, ex);
							}
						}
	                    tableModel.fireTableDataChanged();
	                }
	                if(row>=0){
	                    table.setRowSelectionInterval(row, row);
	                }
	            }
	        });
	    }

	    public Component getTableCellRendererComponent(
	            JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        this.table = table;
	        if (table != null) {
	            JTableHeader header = table.getTableHeader();
	            if (header != null) {
	                rendererComponent.setForeground(header.getForeground());
	                rendererComponent.setBackground(header.getBackground());
	                rendererComponent.setFont(header.getFont());
	                header.addMouseListener(rendererComponent);
	            }
	        }
	        setColumn(column);
	        rendererComponent.setText("是否允许");
	        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	        return rendererComponent;
	    }

	    protected void setColumn(int column) {
	        this.column = column;
	    }

	    public int getColumn() {
	        return column;
	    }

	    protected void handleClickEvent(MouseEvent e) {
	        if (mousePressed) {
	            mousePressed = false;
	            JTableHeader header = (JTableHeader) (e.getSource());
	            JTable tableView = header.getTable();
	            TableColumnModel columnModel = tableView.getColumnModel();
	            int viewColumn = columnModel.getColumnIndexAtX(e.getX());
	            int column = tableView.convertColumnIndexToModel(viewColumn);

	            if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {
	                doClick();
	            }
	        }
	    }

	    public void mouseClicked(MouseEvent e) {
	        handleClickEvent(e);
	        ((JTableHeader) e.getSource()).repaint();
	    }

	    public void mousePressed(MouseEvent e) {
	        mousePressed = true;
	    }

	    public void mouseReleased(MouseEvent e) {
	    }

	    public void mouseEntered(MouseEvent e) {
	    }

	    public void mouseExited(MouseEvent e) {
	    }

	}
}
