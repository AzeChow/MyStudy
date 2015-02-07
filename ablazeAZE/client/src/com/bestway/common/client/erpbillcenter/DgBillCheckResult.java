package com.bestway.common.client.erpbillcenter;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.constant.SBillType;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class DgBillCheckResult extends JDialogBase {

	private JPanel jPanel = null;
	private JToolBar jJToolBarBar = null;
	private JButton btnEdit = null;
	private JButton btnClose = null;
	private JScrollPane jScrollPane = null;
	
	/**
	 * 表
	 */
	private JTable jTable = null;
	
	/**
	 * 表Model
	 */
	private JTableListModel tableModel = null;
	
	/**
	 * 数据源
	 */
	private List dataSource = null;
	private JLabel jLabel = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DgBillCheckResult() {
		super();
		initialize();
	}
	
	/**
	 * 显示
	 */
	@Override
	public void setVisible(boolean isFlag){
		if(isFlag){
			if (dataSource != null) {
				initTable(dataSource);
			}
		}
		super.setVisible(isFlag);
	}
	
	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(722, 427));
        this.setContentPane(getJPanel());
        this.setTitle("检查有问题的单据体");
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("以下单据的 料号、报关编码、报关名称、报关规格、报关单位、手册号等在对应关系中并无一一对应的资料！");
			jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel.setForeground(Color.RED);
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnClose());
			jJToolBarBar.add(jLabel);
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBillCheckResult.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}
					editBillDetail();
				}
			});
		}
		return btnEdit;
	}
	
	/**
	 * 编辑单据
	 */
	private void editBillDetail() {
		BillDetail billDetail = (BillDetail)tableModel.getCurrentRow();
		BillMaster billMaster = billDetail.getBillMaster();
		
		int billType = billMaster.getBillType().getBillType().intValue();
		
		DgBillDetail dgBillDetail = new DgBillDetail(billMaster,
				tableModel);
		dgBillDetail.setDataState(DataState.EDIT);
		
		Boolean isMateriel = true;
		if("成品".equals(billDetail.getNote())){
			isMateriel = false;
		}
		
		dgBillDetail.fillInvNetWeight(dgBillDetail.getMaterielType(billType,
				isMateriel), billDetail);
		dgBillDetail.setCurrBillDetail((BillDetail) tableModel
				.getCurrentRow());
		dgBillDetail
				.setPtAmount(((BillDetail) tableModel.getCurrentRow())
						.getPtAmount());
		dgBillDetail.setBillCategoryBillDetail(billType);
		
		dgBillDetail.setVisible(true);
	}

	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillCheckResult.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
							editBillDetail();
					}
				}
			});
		}
		return jTable;
	}
	
	/**
	 * 初始化表
	 */
	private JTableListModel initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> columns = new Vector<JTableListColumn>();
						
						columns.add(addColumn("类型", "billMaster.billType.billType", 120));
						columns.add(addColumn("单据类别", "billMaster.billType.name", 200));
						columns.add(addColumn("单据号", "billMaster.billNo", 175));
						columns.add(addColumn("料号", "ptPart", 175));
					
						return columns;
					}
				});
		
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText(castValue(value));
		                return this;
					}
					
					/**
					 * 单据分类
					 */
					private String castValue(Object value) {
						System.out.println("value.toString: " + value.toString());
						String returnValue = "";
						int val;
						try{
							val = Integer.parseInt(((String)value).trim());
						}catch (Exception e) {
							System.out.println("啊！中招了！");
							return returnValue;
						}
						
//						if (SBillType.MATERIEL_IN == val){
//							returnValue = "料件入仓";
//						} else if (SBillType.MATERIEL_OUT == val){
//							returnValue = "料件出仓";
//						} else if (SBillType.PRODUCE_IN == val){
//							returnValue = "成品入仓";
//						} else if (SBillType.PRODUCE_OUT == val){
//							returnValue = "成品出仓";
//						} else if (SBillType.LEFTOVER_MATERIEL_IN == val
//								|| SBillType.LEFTOVER_MATERIEL_OUT == val){
//							returnValue = "边角料出入仓";
//						} else if (SBillType.REMAIN_PRODUCE_IN == val
//								|| SBillType.REMAIN_PRODUCE_OUT == val){
//							returnValue = "残次品出入仓";
//						} else if (SBillType.FIXTURE_IN == val){
//							returnValue = "设备入仓";
//						} else if (SBillType.FIXTURE_OUT == val){
//							returnValue = "设备出仓";
//						}
//						
						System.out.println("val :" + val);
						
						switch(val){
							case SBillType.MATERIEL_IN:returnValue = "料件入仓";break;
							case SBillType.MATERIEL_OUT:returnValue = "料件出仓";break;
							case SBillType.PRODUCE_IN:returnValue = "成品入仓";break;
							case SBillType.PRODUCE_OUT:returnValue = "成品出仓";break;
							case SBillType.LEFTOVER_MATERIEL_IN:
							case SBillType.LEFTOVER_MATERIEL_OUT:returnValue = "边角料出入仓";break;
							case SBillType.REMAIN_PRODUCE_IN:
							case SBillType.REMAIN_PRODUCE_OUT:returnValue = "残次品出入仓";break;
							case SBillType.FIXTURE_IN:returnValue = "设备入仓";break;
							case SBillType.FIXTURE_OUT:returnValue = "设备出仓";break;
							default:
								returnValue = "";
								break;
							
						}

						return returnValue;
					}
		});
		
		return tableModel;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
