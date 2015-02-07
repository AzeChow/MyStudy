package com.bestway.common.client.transferfactory;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopSpareAnalyes;
import com.bestway.common.transferfactory.entity.TempEnvelopComplex;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Rectangle;
import java.awt.Insets;

public class FmCustomsEnvelopSpareAnalyse extends FmCommon {

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JComboBox cbbImpExpGoods = null;

	private JTextField tfCustomsEnvelopBillNo = null;

	private JTextField tfComplexCode = null;

	private JTextField tfSeqNum = null;

	private JTextField tfCommodityName = null;

	private JTextField tfOppositeEnterpriseName = null;

	private JButton btnComplexCode = null;

	private JButton btnOppositeEnterpriseName = null;

	private JButton btnCustomsEnvelopBillNo = null;

	private String oppositeEnterpriseId = null;

	private String oppositeEnterpriseName = null;

	private JTableListModel tableModel = null;

	private List list = new ArrayList();

	private JPanel pn1 = null;

	private JLabel jLabel6 = null;

	private JCalendarComboBox cbbListBeginDate = null;

	private JLabel jLabel11 = null;

	private JCalendarComboBox cbbListEndDate = null;
	private JCheckBox cbisShowEnd;

	/**
	 * This method initializes
	 * 
	 */
	public FmCustomsEnvelopSpareAnalyse() {
		super();
		super.transferFactoryManageAction
				.checkCustomsEnvelopSpareAnalyse(new Request(CommonVars
						.getCurrUser()));
		initialize();
		initTable(new ArrayList());
		initUIComponents();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("关封余量分析");
		this.setContentPane(getJContentPane());
		this.setSize(876, 430);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridwidth = 1;
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.gridheight = 2;
			gridBagConstraints5.ipadx = 200;
			gridBagConstraints5.ipady = 70;
			gridBagConstraints5.insets = new Insets(0, 0, 1, 5);
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridwidth = 6;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.weighty = 1.0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints6.ipadx = 230;
			gridBagConstraints6.ipady = -104;
			gridBagConstraints6.insets = new Insets(0, 0, 5, 1);
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.gridheight = 2;
			gridBagConstraints7.ipadx = 364;
			gridBagConstraints7.ipady = 70;
			gridBagConstraints7.insets = new Insets(2, 3, 4, 5);
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.fill = GridBagConstraints.BOTH;
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.gridheight = 2;
			gridBagConstraints8.ipadx = 194;
			gridBagConstraints8.ipady = 70;
			gridBagConstraints8.insets = new Insets(2, 0, 4, 5);
			jContentPane.add(getJScrollPane(), gridBagConstraints6);
			jContentPane.add(getJPanel1(), gridBagConstraints7);
			jContentPane.add(getJPanel2(), gridBagConstraints8);
			GridBagConstraints gbc_checkBox = new GridBagConstraints();
			gbc_checkBox.insets = new Insets(0, 0, 5, 5);
			gbc_checkBox.gridx = 3;
			gbc_checkBox.gridy = 1;
			jContentPane.add(getisShowEnd(), gbc_checkBox);
			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 4;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.ipadx = 13;
			gridBagConstraints10.ipady = -5;
			gridBagConstraints10.insets = new Insets(11, 2, 5, 6);
			jContentPane.add(getBtnSearch(), gridBagConstraints10);
			jContentPane.add(getPn1(), gridBagConstraints5);
			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 4;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = 13;
			gridBagConstraints9.ipady = -5;
			gridBagConstraints9.insets = new java.awt.Insets(3, 2, 15, 6);
			jContentPane.add(getBtnPrint(), gridBagConstraints9);
		}
		return jContentPane;
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
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"商品条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									new java.awt.Color(51, 51, 51)));
			jLabel2.setBounds(188, 17, 55, 21);
			jLabel2.setText("商品名称");
			jLabel3.setBounds(167, 42, 78, 21);
			jLabel3.setText("对方企业名称");
			jLabel4.setBounds(5, 19, 56, 21);
			jLabel4.setText("商品编码");
			jLabel5.setBounds(5, 43, 57, 21);
			jLabel5.setText("帐册序号");
			jPanel1.add(getTfComplexCode(), null);
			jPanel1.add(getTfSeqNum(), null);
			jPanel1.add(getTfCommodityName(), null);
			jPanel1.add(getTfOppositeEnterpriseName(), null);
			jPanel1.add(getBtnComplexCode(), null);
			jPanel1.add(getBtnOppositeEnterpriseName(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			jPanel2.setLayout(null);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"关封条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jLabel.setBounds(7, 17, 53, 21);
			jLabel.setText("转入转出");
			jLabel1.setBounds(6, 43, 53, 21);
			jLabel1.setText("关封号");
			jPanel2.add(getCbbImpExpGoods(), null);
			jPanel2.add(getTfCustomsEnvelopBillNo(), null);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getBtnCustomsEnvelopBillNo(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printReport();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes cbbImpExpGoods
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpGoods() {
		if (cbbImpExpGoods == null) {
			cbbImpExpGoods = new JComboBox();
			cbbImpExpGoods.setBounds(60, 17, 125, 21);
		}
		return cbbImpExpGoods;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillNo() {
		if (tfCustomsEnvelopBillNo == null) {
			tfCustomsEnvelopBillNo = new JTextField();
			tfCustomsEnvelopBillNo.setBounds(60, 43, 105, 21);
		}
		return tfCustomsEnvelopBillNo;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(62, 19, 81, 21);
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes tfSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(63, 43, 100, 21);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes tfCommodityName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommodityName() {
		if (tfCommodityName == null) {
			tfCommodityName = new JTextField();
			tfCommodityName.setBounds(245, 17, 105, 21);
		}
		return tfCommodityName;
	}

	/**
	 * This method initializes tfOppositeEnterpriseName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOppositeEnterpriseName() {
		if (tfOppositeEnterpriseName == null) {
			tfOppositeEnterpriseName = new JTextField();
			tfOppositeEnterpriseName.setBounds(246, 43, 87, 21);
		}
		return tfOppositeEnterpriseName;
	}

	/**
	 * This method initializes btnComplexCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplexCode() {
		if (btnComplexCode == null) {
			btnComplexCode = new JButton();
			btnComplexCode.setBounds(142, 19, 19, 20);
			btnComplexCode.setText("...");
			btnComplexCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Object obj =
							// CommonQuery.getInstance().getComplex();
							// if (obj != null) {
							// tfComplexCode
							// .setText(((Complex) obj).getCode());
							// }
							boolean isImport = (cbbImpExpGoods
									.getSelectedIndex() == 0 ? true : false);
							TempEnvelopComplex temp = (TempEnvelopComplex) TransferFactoryQuery
									.getInstance().findCustomsEnvelopComplex(
											isImport, true);
							if (temp != null) {
								tfSeqNum.setText(temp.getSeqNum() == null ? ""
										: temp.getSeqNum().toString());
								tfComplexCode.setText(temp.getCode());
								tfCommodityName.setText(temp.getName());
							}
						}
					});

		}
		return btnComplexCode;
	}

	/**
	 * This method initializes btnOppositeEnterpriseName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOppositeEnterpriseName() {
		if (btnOppositeEnterpriseName == null) {
			btnOppositeEnterpriseName = new JButton();
			btnOppositeEnterpriseName.setBounds(331, 43, 19, 20);
			btnOppositeEnterpriseName.setText("...");
			btnOppositeEnterpriseName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object obj = CommonQuery.getInstance().getScmCoc();
							if (obj != null) {
								ScmCoc scmCoc = (ScmCoc) obj;
								tfOppositeEnterpriseName.setText(scmCoc
										.getName());
								oppositeEnterpriseName = scmCoc.getName();
								oppositeEnterpriseId = scmCoc.getId();
							}
						}
					});
		}
		return btnOppositeEnterpriseName;
	}

	/**
	 * This method initializes btnCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCustomsEnvelopBillNo() {
		if (btnCustomsEnvelopBillNo == null) {
			btnCustomsEnvelopBillNo = new JButton();
			btnCustomsEnvelopBillNo.setBounds(165, 43, 21, 19);
			btnCustomsEnvelopBillNo.setText("...");
			btnCustomsEnvelopBillNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							boolean isImport = (cbbImpExpGoods
									.getSelectedIndex() == 0 ? true : false);
							Object obj = TransferFactoryQuery.getInstance()
									.getCustomsEnvelopBill(isImport);
							if (obj != null) {
								CustomsEnvelopBill c = (CustomsEnvelopBill) obj;
								tfCustomsEnvelopBillNo.setText(c
										.getCustomsEnvelopBillNo());
							}
						}
					});
		}
		return btnCustomsEnvelopBillNo;
	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		// 初始化进出口类型
		this.cbbImpExpGoods.removeAllItems();
		this.cbbImpExpGoods.addItem(new ItemProperty(new Boolean(true)
				.toString(), "转入"));
		this.cbbImpExpGoods.addItem(new ItemProperty(new Boolean(false)
				.toString(), "转出"));
		this.cbbImpExpGoods.setSelectedIndex(0);
		cbbListBeginDate.setDate(null);
		cbbListEndDate.setDate(null);
	}

	/**
	 * 报表打印
	 */
	private void printReport() {
		try {
			// 是否只打印正则执行的关封
			Boolean isEff = false;

			switch (JOptionPane.showConfirmDialog(
					FmCustomsEnvelopSpareAnalyse.this, "是否只打印正则执行的关封!")) {
			case JOptionPane.YES_OPTION:
				isEff = true;
				break;
			case JOptionPane.NO_OPTION:
				isEff = false;
				break;
			case JOptionPane.CANCEL_OPTION:
				isEff = null;
				break;
			default:
				isEff = null;
			}
			// if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
			// FmCustomsEnvelopSpareAnalyse.this, "是否只打印正则执行的关封!")) {
			// isEff = true;
			// }

			List<CustomsEnvelopSpareAnalyes> printList = null;
			if (isEff == null) {
				return;
			}
			if (isEff) {
				printList = new ArrayList<CustomsEnvelopSpareAnalyes>();
				CustomsEnvelopSpareAnalyes analyes = null;
				for (int i = 0; i < list.size(); i++) {
					analyes = (CustomsEnvelopSpareAnalyes) list.get(i);
					if (analyes.isEff()) {
						printList.add(analyes);
					}
				}
			} else if (!isEff) {
				printList = list;
			}

			if (printList == null || printList.size() <= 0) {
				JOptionPane.showMessageDialog(null, "请选择打印数据!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			CustomReportDataSource ds = new CustomReportDataSource(printList);
			InputStream masterReportStream = FmCustomsEnvelopSpareAnalyse.class
					.getResourceAsStream("report/CustomsEnvelopSpareAnalyes.jasper");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("impExpType", ((CustomsEnvelopSpareAnalyes) list.get(0))
					.getIsImpExpGoods() ? "进货" : "出货");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, params, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 查询
	 */
	private void search() {
		if (this.cbbImpExpGoods.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(FmCustomsEnvelopSpareAnalyse.this,
					"请选择转入转出类型！");
			return;
		}
		Date beginDate = cbbListBeginDate.getDate();
		Date endDate = cbbListEndDate.getDate();
		boolean isImportGoods = Boolean
				.valueOf(((ItemProperty) this.cbbImpExpGoods.getSelectedItem())
						.getCode());
		if (tfOppositeEnterpriseName.getText() == null
				|| tfOppositeEnterpriseName.getText().equals("")) {
			oppositeEnterpriseId = null;
		} else if (oppositeEnterpriseName == null) {
			oppositeEnterpriseId = tfOppositeEnterpriseName.getText();
		} else if (!tfOppositeEnterpriseName.getText().equals(
				oppositeEnterpriseName)) {
			oppositeEnterpriseId = tfOppositeEnterpriseName.getText();
		}
		list = super.transferFactoryManageAction
				.findCustomsEnvelopSpareAnalyes(new Request(CommonVars
						.getCurrUser()), beginDate, endDate, this.tfComplexCode
						.getText(), this.tfCommodityName.getText(),
						this.tfSeqNum.getText(), oppositeEnterpriseId,
						isImportGoods, this.tfCustomsEnvelopBillNo.getText(),this.cbisShowEnd.isSelected());
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(this, "查到0笔数据", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
		initTable(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("是进货还是出货", "isImpExpGoods", 100));
						list.add(addColumn("手册号/帐册号", "emsNo", 100));
						list.add(addColumn("手册号/帐册有效期", "beginEmsAvailability",
								100));
						list.add(addColumn("关封号", "customsEnvelopBillNo", 80));
						list.add(addColumn("关封生效日期", "beginAvailability", 80));
						list.add(addColumn("是否结案", "isEndCase", 60));
						list.add(addColumn("帐册序号", "seqNum", 80));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("关封序号", "ceseqNum", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("1.关封数量", "ownerQuantity", 80));
						list.add(addColumn("2.送货数量",
								"transferFatoryCommodityInfoNum", 80));
						list.add(addColumn("3.关封余量(1-2)", "customsRemainNum",
								100));
						list.add(addColumn("4.已转数量", "transferQuantity", 80));
						list.add(addColumn("5.可分配数量(1-4)",
								"dispensabilityQuantity", 100));
						list.add(addColumn("厂商手册号", "factoryNo", 80));
						list.add(addColumn("客户/供应商名称", "scmCoc.name", 150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new MultiRenderer());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						// TODO Auto-generated method stub
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						JCheckBox box = new JCheckBox();
						if (value == null || "".equals(value)) {
							box.setSelected(false);
						} else {
							try {
								Boolean isEnd = Boolean.valueOf(value
										.toString());
								if (isEnd) {
									box.setSelected(true);
								} else {
									box.setSelected(false);
								}
							} catch (Exception e) {
								box.setSelected(false);
							}
						}
						return box;
					}

				});
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		// JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// if (value == null) {
			// return this;
			// }
			// if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			// checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			// checkBox.setHorizontalAlignment(JLabel.CENTER);
			// checkBox.setBackground(table.getBackground());
			// if (isSelected) {
			// checkBox.setForeground(table.getSelectionForeground());
			// checkBox.setBackground(table.getSelectionBackground());
			// } else {
			// checkBox.setForeground(table.getForeground());
			// checkBox.setBackground(table.getBackground());
			// }
			// return checkBox;
			// }
			// String str = (value == null) ? "" : value.toString();
			// return super.getTableCellRendererComponent(table, str,
			// isSelected,
			// hasFocus, row, column);
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				if (Boolean.valueOf(value.toString()))
					this.setText("进货");
				else
					this.setText("出货");
			}
			return this;
		}
	}

	/**
	 * This method initializes pn1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(11, 42, 68, 21));
			jLabel11.setText("\u622a\u6b62\u65e5\u671f");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(11, 21, 60, 17));
			jLabel6.setText("\u8d77\u59cb\u65e5\u671f");
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.setBorder(BorderFactory.createTitledBorder(null,
					"\u65e5\u671f\u6761\u4ef6",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			pn1.add(jLabel6, null);
			pn1.add(getCbbListBeginDate(), null);
			pn1.add(jLabel11, null);
			pn1.add(getCbbListEndDate(), null);
		}
		return pn1;
	}

	/**
	 * This method initializes cbbListBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListBeginDate() {
		if (cbbListBeginDate == null) {
			cbbListBeginDate = new JCalendarComboBox();
			cbbListBeginDate.setBounds(new Rectangle(74, 18, 120, 21));
		}
		return cbbListBeginDate;
	}

	/**
	 * This method initializes cbbListEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbListEndDate() {
		if (cbbListEndDate == null) {
			cbbListEndDate = new JCalendarComboBox();
			cbbListEndDate.setBounds(new Rectangle(74, 42, 120, 21));
		}
		return cbbListEndDate;
	}

	private JCheckBox getisShowEnd() {
		if (cbisShowEnd == null) {
			cbisShowEnd = new JCheckBox("显示未结案");
		}
		return cbisShowEnd;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
