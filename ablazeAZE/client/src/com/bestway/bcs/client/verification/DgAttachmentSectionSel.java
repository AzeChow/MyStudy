package com.bestway.bcs.client.verification;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.BooleanUtils;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.BaseEntity;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.AsynSwingWorker;

/**
 * @author kpc
 * 核查分析批次选择
 */
public class DgAttachmentSectionSel extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel rootPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private VFVerificationAction vfverificationAction;

	private VFSection section;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private boolean ok = false;
	
	/**
	 * This is the default constructor
	 */
	public DgAttachmentSectionSel() {
		super(true);
		ok = false;
		vfverificationAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean("vfVerificationAction");
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
					section = (VFSection)tableModel.getCurrentRow();
					if(section != null){
						ok = true;
						section.setIsExist(true);
						vfverificationAction.updateVFSection(new Request(CommonVars.getCurrUser()), section);
						vfverificationAction.saveVFAttachmentTemplate(new Request(CommonVars.getCurrUser()), section);
						DgAttachmentSectionSel.this.dispose();
					}else{
						JOptionPane.showMessageDialog(DgAttachmentSectionSel.this, "在列表中选择盘点和产批次，再确定！","提示",JOptionPane.INFORMATION_MESSAGE);						
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
					DgAttachmentSectionSel.this.dispose();
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
					cols.add(addColumn("核查批次", "verificationNo", 60));
					cols.add(addColumn("截止时间", "endDate", 100));
					cols.add(addColumn("结转差额分析文本导入方式", "isImportHs", 200));
					cols.add(addColumn("创建时间", "createDate", 100));
					cols.add(addColumn("创建人", "createPeople", 100));
					cols.add(addColumn("备注", "memo", 300));
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
		new AsynSwingWorker<List<VFSection>>(){
			protected List<VFSection> submit() {
				return vfverificationAction.findAttachmentSection(new Request(CommonVars.getCurrUser()),false);
			}
			protected void success(java.util.List<VFSection> e) {
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
				
				table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus,int row, int column) {
						String formatValue = "料号级";
						System.out.println(value);
						if(value !=null && Boolean.TRUE.equals(value)){
							formatValue = "编码级";					
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
	public VFSection getSection() {
		return section;
	}
}