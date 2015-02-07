/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TempEnvelopComplex;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgEnvelopListReport extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null; // @jve:decl-index=0:visual-constraint="763,10"

	private List list = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JButton btnQuery = null;

	private List lsResult = null;

	private JButton btnExit = null;

	private TransferFactoryManageAction transferFactoryManageAction = null;

	private JLabel jLabel1 = null;

	private JTextField tfEnvelopCode = null;

	private JLabel lbScmCoc = null;

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbComplex = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="763,65"

	private JRadioButton rbImport = null;

	private JRadioButton rbExport = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="763,109"

	private JButton btnPrint = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	/**
	 * This is the default constructor
	 */
	public DgEnvelopListReport() {
		super();
		this.transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		initialize();
		this.getButtonGroup();
		this.getButtonGroup1();
	}

	private void initUIComponents() {
		lbScmCoc.setText(rbImport.isSelected() ? "供应商" : "客户");
		// 商品
		this.cbbComplex.removeAllItems();
		this.cbbComplex.setModel(new DefaultComboBoxModel(
				transferFactoryManageAction.findCustomsEnvelopComplex(
						new Request(CommonVars.getCurrUser()),
						rbImport.isSelected(), true).toArray()));
		this.cbbComplex.setRenderer(CustomBaseRender.getInstance().getRender(
				"seqNum", "name", 60, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbComplex, "seqNum", "name");
		this.cbbComplex.setSelectedIndex(-1);
		// 客户
		this.cbbScmCoc.removeAllItems();
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(
				transferFactoryManageAction.findCustomsEnvelopScmCoc(
						new Request(CommonVars.getCurrUser(), true),
						rbImport.isSelected()).toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 100));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name");
		this.cbbScmCoc.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("关封明细报表");
		this.setSize(710, 490);
		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		//
		// public void windowOpened(java.awt.event.WindowEvent e) {
		//
		// }
		// });
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			list = new Vector();
			initTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		final String scmCoc = (rbImport.isSelected() ? "厂商" : "客户");
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn(scmCoc + "名称", "scmCoc.name", 150));
						list.add(addColumn("所属关区",
								"scmCoc.belongToCustoms.name", 100));
						list
								.add(addColumn("手册号", "oppositeEmsNo",
										100));
						list.add(addColumn("编码", "oppositeComplexCode", 100));
						list.add(addColumn("名称", "oppositeName", 150));
						list.add(addColumn("规格", "oppositeSpec", 150));
						list.add(addColumn("单位", "oppositeUnit.name", 50));
						list.add(addColumn("单价", "unitPrice", 50));
						list.add(addColumn("关封数量", "ownerQuantity", 50));
						list.add(addColumn("关封有效期", "endAvailability", 100));
						list.add(addColumn("购销合同号",
								"purchaseAndSaleContractNo", 100));
						list.add(addColumn("收到关封日期", "", 100));
						list.add(addColumn("确认关封日期", "beginAvailability", 100));
						list.add(addColumn("确认完成日期", "endCaseDate", 100));
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("商品名称", "ptName", 100));
						list.add(addColumn("商品规格", "ptSpec", 100));
						list.add(addColumn("转厂日期", "transferFactoryDate", 100));
						list.add(addColumn("实际转厂数量",
								"realTransferFactoryAmount", 100));
						return list;
					}
				});
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gOpposite = new ColumnGroup(scmCoc + "资料");
		gOpposite.add(cm.getColumn(1));
		gOpposite.add(cm.getColumn(2));
		gOpposite.add(cm.getColumn(3));
		gOpposite.add(cm.getColumn(4));
		gOpposite.add(cm.getColumn(5));
		gOpposite.add(cm.getColumn(6));
		gOpposite.add(cm.getColumn(7));
		gOpposite.add(cm.getColumn(8));
		ColumnGroup gSelf = new ColumnGroup("本公司资料");
		gSelf.add(cm.getColumn(9));
		gSelf.add(cm.getColumn(10));
		gSelf.add(cm.getColumn(11));
		gSelf.add(cm.getColumn(12));
		gSelf.add(cm.getColumn(13));
		gSelf.add(cm.getColumn(14));
		gSelf.add(cm.getColumn(15));
		gSelf.add(cm.getColumn(16));
		gSelf.add(cm.getColumn(17));
		gSelf.add(cm.getColumn(18));
		gSelf.add(cm.getColumn(19));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gOpposite);
		header.addColumnGroup(gSelf);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(476, 43, 12, 18));
			jLabel4.setText("从");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(476, 71, 12, 19));
			jLabel2.setText("到");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(229, 68, 58, 19));
			jLabel3.setText("转厂商品");
			lbScmCoc = new JLabel();
			lbScmCoc.setBounds(new java.awt.Rectangle(230, 42, 58, 20));
			lbScmCoc.setText("供应商");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(231, 18, 58, 20));
			jLabel1.setText("关封号");
			jLabel = new JLabel();
			jLabel.setText("关封有效日期 :");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(489, 17, 104, 19);

			// jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
			// 24));
			// jLabel.setForeground(java.awt.Color.blue);

			jPanel.add(jLabel, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnExit(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfEnvelopCode(), null);
			jPanel.add(lbScmCoc, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCbbComplex(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel4, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
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
			jTable = new GroupableHeaderTable();// JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(616, 19, 64, 23);
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new find().start();
				}
			});
		}
		return btnQuery;
	}

	class find extends Thread {

		@Override
		public void run() {
			try {
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				if (beginDate != null && endDate != null
						&& beginDate.compareTo(endDate) > 0) {
					JOptionPane.showMessageDialog(null, "起始日期大于终止日期！");
					return;
				}
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				String envelopCode = tfEnvelopCode.getText().trim();
				String complexCode = null;
				String scmCocCode = null;
				Integer seqNum = null;
				int state = -1;
				if (cbbComplex.getSelectedItem() != null) {
					complexCode = ((TempEnvelopComplex) cbbComplex
							.getSelectedItem()).getCode();
					seqNum = ((TempEnvelopComplex) cbbComplex.getSelectedItem())
							.getSeqNum();
				}
				if (cbbScmCoc.getSelectedItem() != null) {
					scmCocCode = ((ScmCoc) cbbScmCoc.getSelectedItem())
							.getCode();
				}
				if (rbYes.isSelected()) {
					state = 1;
				} else if (rbNo.isSelected()) {
					state = 0;
				}
				// lsResult =
				// transferFactoryManageAction.findCustomsEnvelopList(
				// new Request(CommonVars.getCurrUser()), envelopCode,
				// seqNum, complexCode, scmCocCode, rbImport.isSelected(),
				// state);
				lsResult = transferFactoryManageAction.findCustomsEnvelopList(
						new Request(CommonVars.getCurrUser()), envelopCode,
						seqNum, complexCode, scmCocCode, rbImport.isSelected(),
						state, beginDate, endDate);
				initTable(lsResult);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEnvelopListReport.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(616, 69, 64, 23);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEnvelopCode() {
		if (tfEnvelopCode == null) {
			tfEnvelopCode = new JTextField();
			tfEnvelopCode.setBounds(new java.awt.Rectangle(287, 18, 177, 22));
		}
		return tfEnvelopCode;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new java.awt.Rectangle(287, 43, 177, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbComplex() {
		if (cbbComplex == null) {
			cbbComplex = new JComboBox();
			cbbComplex.setBounds(new java.awt.Rectangle(287, 69, 177, 22));
		}
		return cbbComplex;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setText("已生效");
			rbYes.setBounds(new java.awt.Rectangle(17, 17, 62, 20));
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setSelected(false);
			rbNo.setBounds(new java.awt.Rectangle(81, 17, 64, 20));
			rbNo.setText("未生效");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setText("全部");
			rbAll.setSelected(true);
			rbAll.setBounds(new java.awt.Rectangle(141, 17, 55, 20));
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbYes);
			buttonGroup.add(this.rbNo);
			buttonGroup.add(this.rbAll);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImport() {
		if (rbImport == null) {
			rbImport = new JRadioButton();
			rbImport.setText("转入");
			rbImport.setSelected(true);
			rbImport.setBounds(new java.awt.Rectangle(18, 16, 62, 22));
			rbImport.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						initUIComponents();
					}
				}
			});
		}
		return rbImport;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExport() {
		if (rbExport == null) {
			rbExport = new JRadioButton();
			rbExport.setText("转出");
			rbExport.setBounds(new java.awt.Rectangle(88, 16, 62, 22));
			rbExport.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						initUIComponents();
					}
				}
			});
		}
		return rbExport;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new java.awt.Rectangle(11, 4, 211, 46));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "关封类型",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					new java.awt.Color(51, 51, 51)));
			jPanel2.add(getRbImport(), null);
			jPanel2.add(getRbExport(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new java.awt.Rectangle(11, 50, 211, 44));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "关封状态",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel3.add(getRbYes(), null);
			jPanel3.add(getRbNo(), null);
			jPanel3.add(getRbAll(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.rbExport);
			buttonGroup1.add(this.rbImport);
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new java.awt.Rectangle(616, 44, 64, 23));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(DgEnvelopListReport.this,
								"没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgEnvelopListReport.class
							.getResourceAsStream("report/CustomsEnvelopListReport.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("scmCoc", (rbImport.isSelected() ? "供应商"
							: "客户"));
					JasperPrint jasperPrint;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});

		}
		return btnPrint;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(489, 43, 104, 21));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(489, 70, 104, 20));
		}
		return cbbEndDate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
