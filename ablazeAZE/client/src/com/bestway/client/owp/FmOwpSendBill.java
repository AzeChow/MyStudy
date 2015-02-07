package com.bestway.client.owp;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.custom.common.DgBaseExportCustomsDeclaration;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.owp.action.OwpBillAction;
import com.bestway.owp.action.OwpMessageAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;


/**
 * 外发加工发货登记表
 * @author 石小凯 10.8.13
 *
 */
public class FmOwpSendBill extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnBrowse = null;
	private JButton btnDeclare = null;
	private JButton btnReturnReceipt = null;
	private JButton btnLoadBGD = null;
	private JButton btnRefurbish = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JToolBar jToolBar1 = null;
	private JComboBox jComboBox = null;
	private JButton jButton = null;
	private JPanel jPanel = null;
	private JComboBox cbbAppHeadNo = null;
	private OwpMessageAction owpMessageAction = null;
	private JLabel lbAppHeadNo = null;
	private OwpAppHead owpAppHead = null;
	private OwpBillAction owpBillAction = null;
	/**
	 * 外发加工发货登记表表头tableModel
	 */
	private JTableListModel tableModelHead = null;
	/**
	 * This method initializes 
	 * 
	 */
	public FmOwpSendBill() {
		super();
//		owpMessageAction = (OwpMessageAction) CommonVars
//		.getApplicationContext().getBean("owpMessageAction");
		owpBillAction = (OwpBillAction) CommonVars
		.getApplicationContext().getBean("owpBillAction");
		initialize();
		
	}
	public  void initUIComponents() {
//		List owpHead=this.owpMessageAction.findOwpAppHead(new Request(
//				CommonVars.getCurrUser()));
		List owpHead=this.owpBillAction.findOwpAppHead(new Request(
				CommonVars.getCurrUser()));
		System.out.println("owpHead="+owpHead.size());
		if (owpHead != null && owpHead.size() > 0) {
			for (int i = 0; i < owpHead.size(); i++) {
				this.cbbAppHeadNo.addItem((OwpAppHead)owpHead.get(i));
			}
			this.cbbAppHeadNo.setRenderer(CustomBaseRender.getInstance()
					.getRender("appNo", "seqNo", 100, 150)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbbAppHeadNo.getItemCount() > 0) {
			this.cbbAppHeadNo.setSelectedIndex(0);
		}
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(880, 397));
        this.setContentPane(getJContentPane());
        this.setTitle("外发加工出货登记表");
        this
		.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				initUIComponents();
				initTable(getDataSource());
			}
		});
//      //获取外发加工登记表表头
//		List list = owpMessageAction.findOwpSendBillHeadInfo(new Request(CommonVars
//				.getCurrUser()),owpAppHead.getAppNo());
//        this.initTable(list);
//       
	}
	
	protected List getDataSource() {
		owpAppHead = (OwpAppHead)this.cbbAppHeadNo.getSelectedItem();
		if(null==owpAppHead){
			System.out.println("aaaa");
			 return new Vector();
		}
		String appNo = owpAppHead.getAppNo();
		//获取外发加工登记表表头
//		List list = owpMessageAction.findOwpSendBillHeadInfo(new Request(CommonVars
//				.getCurrUser()),appNo);
		System.out.println("appNo="+appNo);
		List list = owpBillAction.findOwpSendBillHeadInfo(new Request(CommonVars
				.getCurrUser()), appNo);
		System.out.println("bbbb"+list.size());
		return  list;
	}
	/**
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("发货单编号", "billNo", 120));
						list.add(addColumn("电子口岸统一编号", "seqNo", 120));
						list.add(addColumn("申请表编号", "appNo", 100));
						list.add(addColumn("企业内部编号", "copBillNo", 120));
						list.add(addColumn("帐册号", "emsNo", 60));
						list.add(addColumn("委托方企业编码", "outTradeCode", 120));
						list.add(addColumn("委托方企业名称", "outTradeName", 120));
						list.add(addColumn("承揽方企业编码", "inTradeCode", 120));
						list.add(addColumn("承揽方企业名称", "inTradeName", 120));
						list.add(addColumn("发货申报人", "isDeclaEm", 60));
						list.add(addColumn("发货日期", "collectDate", 60));
						list.add(addColumn("备注", "note", 80));
						list.add(addColumn("录入人", "creater", 100));
						return list;
					}
				});
		return tableModel;
	}
	
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar1(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnReturnReceipt());
			jToolBar.add(getBtnLoadBGD());
			jToolBar.add(getBtnRefurbish());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpSendBill dg = new DgOwpSendBill();
					dg.setTableModelHead(tableModel);
					dg.setOwpAppHead(owpAppHead);
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);
					
				}
			});
		}
		return btnAdd;
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
						JOptionPane.showMessageDialog(FmOwpSendBill.this,
								"请选择你要删除的资料", "提示", 0);
						return;
					}
					DgOwpSendBill dg = new DgOwpSendBill();
					dg.setTableModelHead(tableModel);
					dg.setOwpAppHead(owpAppHead);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 deleteBillHead();
				}
			});
			
		}
		return btnDelete;
	}
	private void deleteBillHead(){
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmOwpSendBill.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(FmOwpSendBill.this,
				"确定要删除吗？", "确认", 0) != 0) {
			return;
		}
		OwpBillSendHead head = (OwpBillSendHead) tableModel
			.getCurrentRow();
//		owpMessageAction.deleteOwpSendBillHead(new Request(
//				CommonVars.getCurrUser()), head);
		tableModel.deleteRow(head);
		
	}
	/**
	 * This method initializes btnBrowse	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmOwpSendBill.this,
								"请选择你要删除的资料", "提示", 0);
						return;
					}
					DgOwpSendBill dg = new DgOwpSendBill();
					dg.setTableModelHead(tableModel);
					dg.setOwpAppHead(owpAppHead);
					dg.setDataState(DataState.BROWSE);
					dg.setVisible(true);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes btnDeclare	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
		}
		return btnDeclare;
	}

	/**
	 * This method initializes btnReturnReceipt	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReturnReceipt() {
		if (btnReturnReceipt == null) {
			btnReturnReceipt = new JButton();
			btnReturnReceipt.setText("回执查看");
		}
		return btnReturnReceipt;
	}

	/**
	 * This method initializes btnLoadBGD	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnLoadBGD() {
		if (btnLoadBGD == null) {
			btnLoadBGD = new JButton();
			btnLoadBGD.setText("导入");
		}
		return btnLoadBGD;
	}

	/**
	 * This method initializes btnRefurbish	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRefurbish() {
		if (btnRefurbish == null) {
			btnRefurbish = new JButton();
			btnRefurbish.setText("刷新");
		}
		return btnRefurbish;
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
		}
		return jTable;
	}

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.add(getJButton());
			jToolBar1.add(getJPanel());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("a");
			jButton.setVisible(false);
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbAppHeadNo = new JLabel();
			lbAppHeadNo.setText("申请表编号：");
			jPanel = new JPanel();
			jPanel = new JPanel();
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setHgap(1);
			f.setVgap(2);
			jPanel.setLayout(f);
			jPanel.add(lbAppHeadNo, null);
			jPanel.add(getCbbAppHeadNo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbAppHeadNo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbAppHeadNo() {
		if (cbbAppHeadNo == null) {
			cbbAppHeadNo = new JComboBox();
			cbbAppHeadNo.setPreferredSize(new Dimension(150, 24));
			cbbAppHeadNo.setUI(new CustomBaseComboBoxUI(300));
			cbbAppHeadNo.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initTable(getDataSource());
					}
				}
			});
		}
		return cbbAppHeadNo;
	}
	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}
	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
