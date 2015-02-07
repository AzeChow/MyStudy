/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempComplex;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgImgExgRecord extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null;  //  @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JCheckBox jCheckBox = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel3 = null;

	private JButton jButton = null;

	private List lsResult = null;

	private boolean isImg = true;// 料件/成品

	private JButton jButton1 = null;

	private JLabel jLabel4 = null;

	private JTextField jTextField = null;

	private JLabel jLabel5 = null;

	private JComboBox jComboBox1 = null;

	private JLabel jLabel6 = null;

	private JTextField jTextField1 = null;

	private JButton jButton2 = null;

	private JLabel jLabel7 = null;

	private JComboBox jComboBox2 = null;

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	/**
	 * This is the default constructor
	 */
	public DgImgExgRecord() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");

		initialize();

	}

	private void initUIComponents() {
		// 客户
		this.jComboBox
				.setModel(new DefaultComboBoxModel(encAction
						.findCustomsDeclarationCustomer(
								new Request(CommonVars.getCurrUser()), isImg)
						.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name", 280);
		this.jComboBox.setSelectedIndex(-1);

		// 贸易方式
		List list = CustomBaseList.getInstance().getTrades();
		jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox1,
				"code", "name");
		jComboBox1.setSelectedIndex(-1);
		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.jComboBox2
				.setModel(new DefaultComboBoxModel(projectList.toArray()));
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2, "code", "name");
		jComboBox2.setSelectedIndex(-1);
	}

	// 填充动态ComboBox
	public void intoComboBox(List list, JComboBox jComboBox) {

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
		this.setSize(803, 482);
		this.jCalendarComboBox.setDate(CommonVars.getBeginDate());
		this.jCalendarComboBox1.setDate(new Date());
		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		//
		// public void windowOpened(java.awt.event.WindowEvent e) {
		//
		// if (isImg) {
		// setTitle("料件流水帐");
		// jLabel.setText("料件流水帐");
		// } else {
		// setTitle("成品流水帐");
		// jLabel.setText("成品流水帐");
		// }
		// initUIComponents();
		// list = new Vector();
		// initTable(list);
		// }
		// });
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			if (isImg) {
				setTitle("料件流水帐");
				jLabel.setText("料件流水帐");
			} else {
				setTitle("成品流水帐");
				jLabel.setText("成品流水帐");
			}
			initUIComponents();
			list = new Vector();
			initTable(list);
		}
		super.setVisible(f);
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
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关日期",
								"info.baseCustomsDeclaration.declarationDate",
								80));
						if (isImg) {
							list.add(addColumn("进口日期",
									"info.baseCustomsDeclaration.impExpDate",
									80));
						} else {
							list.add(addColumn("出口日期",
									"info.baseCustomsDeclaration.impExpDate",
									80));
						}
						list
								.add(addColumn(
										"报关单流水号",
										"info.baseCustomsDeclaration.serialNumber",
										80));
						list.add(addColumn("报关单份数", "customsPiece", 80,
								Integer.class));
						list
						.add(addColumn(
								"进/出口岸",
								"info.baseCustomsDeclaration.customs.name",
								80));
						list
								.add(addColumn(
										"报关单号",
										"info.baseCustomsDeclaration.customsDeclarationCode",
										120));
						list
								.add(addColumn(
										"报送海关",
										"info.baseCustomsDeclaration.declarationCustoms.name",
										120));
						list.add(addColumn("帐册号",
								"info.baseCustomsDeclaration.emsHeadH2k", 120));
						list.add(addColumn("总净重",
								"info.baseCustomsDeclaration.netWeight", 80));
						list.add(addColumn("总毛重",
								"info.baseCustomsDeclaration.grossWeight", 80));
						if (!isImg) {// 成品
							list
									.add(addColumn(
											"批准文号",
											"info.baseCustomsDeclaration.authorizeFile",
											80));
						}
						list.add(addColumn("备案序号", "info.commSerialNo", 60));
						if (!isImg) {// 成品
							list.add(addColumn("版本号", "info.version", 80));
						}
						list.add(addColumn("商品编码", "info.complex.code", 80));
						list.add(addColumn("品名", "info.commName", 150));
						list.add(addColumn("规格", "info.commSpec", 150));
						if (isImg) {
							list.add(addColumn("原产国", "info.country.name", 80));
							list.add(addColumn("法定单位", "info.legalUnit.name",
									50));
							list.add(addColumn("法定数量", "info.firstAmount", 50));
						} else {
							list.add(addColumn("产销国", "info.country.name", 80));
						}
						list.add(addColumn("申报单位", "info.unit.name", 50));
						list.add(addColumn("申报数量", "info.commAmount", 50));
						list.add(addColumn("申报单价", "info.commUnitPrice", 50));
						list
								.add(addColumn(
										"申报币制",
										"info.baseCustomsDeclaration.currency.name",
										50));
						list.add(addColumn("申报总价", "info.commTotalPrice", 50));
						list.add(addColumn("申报美元总价", "info.dollarTotalPrice",
								100));
						list.add(addColumn("贸易方式",
								"info.baseCustomsDeclaration.tradeMode.name",
								80));
						list.add(addColumn("运输工具",
								"info.baseCustomsDeclaration.conveyance", 80));
						list
								.add(addColumn(
										"提运单号",
										"info.baseCustomsDeclaration.billOfLading",
										80));
						if (!isImg) {
							list.add(addColumn("出口发票号",
									"info.baseCustomsDeclaration.invoiceCode",
									80));
						}
						list
								.add(addColumn(
										"集装箱号",
										"info.baseCustomsDeclaration.allContainerNumInEleven",
										80));
						list.add(addColumn("件数", "info.pieces", 50));
						list.add(addColumn("净重", "info.netWeight", 50));
						list.add(addColumn("毛重", "info.grossWeight", 50));
						list.add(addColumn("客户名称",
								"info.baseCustomsDeclaration.customer.name",
								150));
						list.add(addColumn("装箱单号",
								"info.baseCustomsDeclaration.wlserialNumber",
								80));
						list
								.add(addColumn("事业部", "info.projectDept.name",
										100));
						list.add(addColumn("录入员","info.baseCustomsDeclaration.creater.userName",80));
						list.add(addColumn("随附单据",
								"info.baseCustomsDeclaration.attachedBill", 80));
						list.add(addColumn("备注",
								"info.baseCustomsDeclaration.memos", 100));
						return list;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);

		List<JTableListColumn> cm = tableModel.getColumns();
//		cm.get(8).setFractionCount(decimalLength);
//		cm.get(9).setFractionCount(decimalLength);
//		if (!isImg) {
//			cm.get(18).setFractionCount(decimalLength);
//			cm.get(19).setFractionCount(decimalLength);
//			cm.get(21).setFractionCount(decimalLength);
//			cm.get(22).setFractionCount(decimalLength);
//			cm.get(29).setFractionCount(decimalLength);
//			cm.get(30).setFractionCount(decimalLength);
//			CommonVars.castMultiplicationValue(jTable, 21, 18, 19,decimalLength);
//		} else {
//			cm.get(16).setFractionCount(decimalLength);
//			cm.get(18).setFractionCount(decimalLength);
//			cm.get(19).setFractionCount(decimalLength);
//			cm.get(21).setFractionCount(decimalLength);
//			cm.get(22).setFractionCount(decimalLength);
//			cm.get(29).setFractionCount(decimalLength);
//			cm.get(30).setFractionCount(decimalLength);
//			CommonVars.castMultiplicationValue(jTable, 21, 18, 19,decimalLength);
//		}
		cm.get(9).setFractionCount(decimalLength);
		cm.get(10).setFractionCount(decimalLength);
		if (!isImg) {//成品
			cm.get(19).setFractionCount(decimalLength);
			cm.get(20).setFractionCount(decimalLength);
			cm.get(22).setFractionCount(decimalLength);
			cm.get(23).setFractionCount(decimalLength);
			cm.get(30).setFractionCount(decimalLength);
			cm.get(31).setFractionCount(decimalLength);
			CommonVars.castMultiplicationValue(jTable, 22, 19, 20,decimalLength);
		} else {
			cm.get(17).setFractionCount(decimalLength);
			cm.get(19).setFractionCount(decimalLength);
			cm.get(20).setFractionCount(decimalLength);
			cm.get(22).setFractionCount(decimalLength);
			cm.get(23).setFractionCount(decimalLength);
			cm.get(29).setFractionCount(decimalLength);
			cm.get(30).setFractionCount(decimalLength);
			CommonVars.castMultiplicationValue(jTable, 22, 19, 20,decimalLength);
		}
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	/**
	 * @param isImg
	 *            The isImg to set.
	 */
	public void setImg(boolean isImg) {
		this.isImg = isImg;
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
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(113, 42, 73, 18));
			jLabel7.setText("事业部");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(528, 36, 68, 24));
			jLabel6.setText("商品序号：");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(323, 64, 61, 26));
			jLabel5.setText("贸易方式：");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(112, 66, 76, 23));
			jLabel4.setText("报关单号：");
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 2, 165, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel1.setBounds(323, 10, 78, 18);
			jLabel1.setText("报关日期：");
			jLabel2.setBounds(497, 8, 18, 19);
			jLabel2.setText("至");
			jLabel3.setBounds(323, 38, 61, 20);
			jLabel3.setText("客户名称：");
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJComboBox2(), null);
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
			jTable = new JTable();
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(404, 8, 86, 20);
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(518, 8, 87, 21);
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(630, 7, 131, 23);
			jCheckBox.setText("计算未生效报关单");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(384, 37, 139, 23);
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
			jButton.setBounds(557, 65, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String seqNum = jTextField1.getText();
					try {
						if (seqNum != null && !seqNum.equals("")) {
							new Integer(seqNum.trim());
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgImgExgRecord.this,
								"商品序号输入有误，请输入正确数字!", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return ;
					}
					new find().start();
				}
			});
		}
		return jButton;
	}

	class find extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				String customerid = "";
				if (jComboBox.getSelectedItem() != null) {
					customerid = ((ScmCoc) jComboBox.getSelectedItem()).getId();
				}
				boolean iseffect = !(jCheckBox.isSelected());
				Date beginDate = jCalendarComboBox
						.getDate();
				Date endDate = jCalendarComboBox1
						.getDate();
				String customsNo = jTextField.getText(); // 报关单号
				Trade trade = (Trade) jComboBox1.getSelectedItem(); // 贸易方式
				String sName = jTextField1.getText(); // 商品名称
				Integer  seqNo=null;
				if (sName != null && !sName.equals("")) {
					seqNo =new Integer(sName.trim());
				}
				String projectDeptName = "";
				// 事业部
				if (jComboBox2.getSelectedItem() != null) {
					projectDeptName = ((ProjectDept) jComboBox2
							.getSelectedItem()).getName();
				}
				lsResult = encAction.findImpExpCommInfo(new Request(CommonVars
						.getCurrUser()), seqNo, customerid, null, beginDate,
						endDate, null, null, !isImg, Boolean.valueOf(iseffect),
						null, trade, customsNo, projectDeptName);
				lsResult = encAction.getTotal(new Request(CommonVars
						.getCurrUser()), lsResult);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImgExgRecord.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(660, 65, 86, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(192, 66, 115, 22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new java.awt.Rectangle(384, 66, 138, 23));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(594, 37, 121, 22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(716, 37, 31, 22));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TempComplex obj = (TempComplex) getNameFromCustomsInfo();
					if (obj != null) {
						jTextField1.setText(obj.getCode() == null ? "" : obj
								.getCode().trim());
					}
				}
			});
		}
		return jButton2;
	}

	public Object getNameFromCustomsInfo() {
		List list = new Vector();
		list.add(new JTableListColumn("序号", "code", 60));
		list.add(new JTableListColumn("名称", "name", 200));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List list1 = encAction.findCustomsDeclarationCommInfoNoSpec(new Request(
				CommonVars.getCurrUser()), isImg);
		dgCommonQuery.setDataSource(list1);
		dgCommonQuery.setTitle("选择商品名称");
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(new java.awt.Rectangle(192, 40, 114, 20));
		}
		return jComboBox2;
	}

}
