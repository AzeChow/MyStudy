/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkstock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.BooleanUtils;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.BaseEntity;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.AsynSwingWorker;
import com.jnetdirect.jsql.ay;

/**
 * @author xc 
 * 盘点核查批次选择
 */
public class DgECSSectionSel extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel rootPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private ECSCheckStockAction ecsCheckStockAction = null;

	private ECSSection section;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private boolean ok = false;
	
	/**
	 * This is the default constructor
	 */
	public DgECSSectionSel() {
		super(true);
		ok = false;
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 400);
		this.setTitle("账册盘点核查批次选择窗口");
		this.setContentPane(getRootPanel());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				loadTable();
			}
		});
	}

	private JPanel getRootPanel() {
		if (rootPanel == null) {
			rootPanel = new JPanel();
			rootPanel.setLayout(new BorderLayout(0, 0));
			rootPanel.add(getScrollPane(), BorderLayout.CENTER);
			rootPanel.add(getPanel(), BorderLayout.SOUTH);
		}
		return rootPanel;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					section = (ECSSection)tableModel.getCurrentRow();
					if(section != null){
						ok = true;
						DgECSSectionSel.this.dispose();
					}else{
						JOptionPane.showMessageDialog(DgECSSectionSel.this, "在列表中选择盘点和产批次，再确定！","提示",JOptionPane.INFORMATION_MESSAGE);						
					}
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = false;
					DgECSSectionSel.this.dispose();
				}
			});

		}
		return jButton1;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getJButton());
			panel.add(getJButton1());
		}
		return panel;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
			
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			tableModel = new JTableListModel(table, Collections.EMPTY_LIST, new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> cols =new ArrayList<JTableListColumn>();
					cols.add(addColumn("选择", "isSelected", 40));
					cols.add(addColumn("报核次数", "cancelOwnerHead.cancelTimes", 60));
					cols.add(addColumn("批次号", "verificationNo", 60));
					cols.add(addColumn("盘点核查结束日期", "endDate", 90));
					cols.add(addColumn("报核开始日期", "cancelOwnerHead.beginDate", 90));
					cols.add(addColumn("报核结束日期", "cancelOwnerHead.endDate", 80));
					cols.add(addColumn("盘点导入方式", "stockImportIsHs", 90));
					cols.add(addColumn("结转导入方式", "transImportIsHs", 90));
					cols.add(addColumn("内购参与统计", "buyIsCount", 90));
					cols.add(addColumn("申报日期", "cancelOwnerHead.declareDate", 90));					
					cols.add(addColumn("创建日期", "createDate", 90));
					cols.add(addColumn("创建人", "createPeople", 50));
					cols.add(addColumn("备注", "memo", 100));
					return cols;
				}
			},ListSelectionModel.SINGLE_SELECTION);
			
			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent evt) {
					BaseEntity e = (BaseEntity)tableModel.getCurrentRow();
					if(e != null){
						BaseEntity o = null;
						for(int i = 0 ;i <tableModel.getSize();i++){
							o = (BaseEntity)tableModel.getList().get(i);
							o.setIsSelected(o == e);
						}
					}
				}
			});
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2){
						getJButton().doClick();
					}
				}
			});
			
			
		}
		return table;
	}

	/**
	 * 加载表格数据
	 */
	private void loadTable(){
		new AsynSwingWorker<List<ECSSection>>(){
			protected List<ECSSection> submit() {
				return ecsCheckStockAction.findEcsSection(new Request(CommonVars.getCurrUser()));
			}
			protected void success(java.util.List<ECSSection> e) {
				tableModel.setList(e);
				table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer(){
					private JRadioButton button = new JRadioButton();				
					public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column) {
						button.setHorizontalAlignment(JLabel.CENTER);
						button.setBackground(table.getBackground());
						isSelected = BooleanUtils.toBoolean(String.valueOf(value));
						if (isSelected) {
							button.setForeground(table.getSelectionForeground());
							button.setBackground(table.getSelectionBackground());							
						} else {
							button.setForeground(table.getForeground());
							button.setBackground(table.getBackground());
						}
						button.setSelected(isSelected);
						return button;
					}		
				});
				
				table.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
						String formatValue = "料号级";
						if(value !=null && "true".equals(value)){										
							formatValue = "编码级";					
						}
						return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
					}
				});
				
				table.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
						String formatValue = "编码级";
						if(value !=null && "false".equals(value)){										
							formatValue = "料号级";					
						}
						return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
					}
				});				
				
				table.getColumnModel().getColumn(9).setCellRenderer(new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
						System.out.println(value);
						String formatValue = "否";
						if("true".equals(value)){										
							formatValue = "是";					
						}
						return super.getTableCellRendererComponent(table, formatValue, isSelected, hasFocus,row, column);
					}
				});
			};
		}.doWork();		
	}
	
	public boolean isOk() {
		return ok;
	}
	public ECSSection getSection() {
		return section;
	}
}
