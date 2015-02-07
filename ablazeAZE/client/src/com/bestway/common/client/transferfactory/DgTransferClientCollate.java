/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TempCustomsEmsInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgTransferClientCollate extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private List list = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private TransferFactoryManageAction transferFactoryManageAction = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel4 = null;

	private JComboBox jComboBox1 = null;

	private JButton jButton2 = null;

	private JLabel jLabel51 = null;

	private JComboBox cbbProjectType = null;

	private JLabel lbEmsNo = null;

	private JComboBox cbbEmsNo = null;

	private JLabel jLabel = null;

	private JLabel lbSpec = null;

	private JTextField tfSpec = null;

	private JLabel lbName = null;

	private JTextField tfName = null;

	private JButton btnName = null;

	private JButton btnSpec = null;

	/**
	 * This is the default constructor
	 */
	public DgTransferClientCollate() {
		super();
		this.transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();

	}

	private void initUIComponents() {
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name", 270);
		jComboBox.setSelectedIndex(-1);

		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "纸质手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(1);
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
		this.setSize(800, 500);
		this.setTitle("转厂客户对帐表");
		/*
		 * transferFactoryManageAction.findTransferPlan(new Request(CommonVars
		 * .getCurrUser()));
		 */
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
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("日期", "bill1", 70));
						list.add(addColumn("单据号码", "bill2", 100));
						list.add(addColumn("客订单号", "bill3", 100));
						list.add(addColumn("帐册序号", "bill4", 60));
						list.add(addColumn("商品名称", "bill5", 120));
						list.add(addColumn("商品规格", "bill7", 120));
						list.add(addColumn("关封号", "bill6", 100));
						list.add(addColumn("期初", "billSum3", 80));
						list.add(addColumn("送货", "billSum1", 80));
						list.add(addColumn("转厂", "billSum2", 80));
						list.add(addColumn("结存", "billSum4", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null && Double.valueOf(value.toString()).doubleValue() < 0) {
							this.setForeground(Color.RED);
						} else {
							this.setForeground(Color.BLACK);
						}
						return this;
					}
				});
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
			lbName = new JLabel();
			lbName.setBounds(new Rectangle(440, 8, 66, 23));
			lbName.setText("商品名称");
			lbSpec = new JLabel();
			lbSpec.setBounds(new Rectangle(440, 38, 66, 23));
			lbSpec.setText("商品规格");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(464, 10, 0, 0));
			jLabel.setText("JLabel");
			lbEmsNo = new JLabel();
			lbEmsNo.setBounds(new Rectangle(223, 8, 66, 23));
			lbEmsNo.setText("\u624b\u518c\u7f16\u53f7");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(25, 8, 56, 23));
			jLabel51.setText("\u9879\u76ee\u7c7b\u578b");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(25, 38, 56, 23));
			jLabel4.setText("单据方式");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(223, 68, 66, 23));
			jLabel3.setText("截止日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(25, 68, 56, 23));
			jLabel2.setText("开始日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(223, 38, 66, 23));
			jLabel1.setText("客户供应商");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(jLabel51, null);
			jPanel.add(getCbbProjectType(), null);
			jPanel.add(lbEmsNo, null);
			jPanel.add(getCbbEmsNo(), null);
			jPanel.add(jLabel, null);
			jPanel.add(lbSpec, null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(lbName, null);
			jPanel.add(getTfName(), null);
			jPanel.add(getBtnName(), null);
			jPanel.add(getBtnSpec(), null);
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(662, 10, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new Find().start();
				}
			});
		}
		return jButton;
	}

	class Find extends Thread {

		@Override
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date beginDate = jCalendarComboBox.getDate();// 开始日期
				Date endDate = jCalendarComboBox1.getDate(); // 结束日期
				String sccode = null;
				String spec = tfSpec.getText();
				String name = tfName.getText();
				ScmCoc sc = (ScmCoc) jComboBox.getSelectedItem();
				if (sc != null) {
					sccode = sc.getCode();
				}
				Integer impExpType = null;
				if (jComboBox1.getSelectedItem() != null
						&& jComboBox1.getSelectedItem().equals("进货单据")) {
					impExpType = new Integer(ImpExpType.TRANSFER_FACTORY_IMPORT);
				} else if (jComboBox1.getSelectedItem() != null
						&& jComboBox1.getSelectedItem().equals("出货单据")) {
					impExpType = new Integer(ImpExpType.TRANSFER_FACTORY_EXPORT);
				}
				String emsNo = null;
				if (cbbEmsNo.getSelectedItem() != null) {
					emsNo = cbbEmsNo.getSelectedItem().toString();
				}
				ItemProperty item = (ItemProperty) cbbProjectType
						.getSelectedItem();
				Integer projectType = Integer.parseInt(item.getCode());
				lsResult = transferFactoryManageAction
						.findTransferClienCollate(new Request(CommonVars
								.getCurrUser()), projectType, emsNo, sccode,
								beginDate, endDate, spec, name, impExpType);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgTransferClientCollate.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
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
			jButton1.setBounds(662, 69, 94, 23);
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(292, 38, 137, 23));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(83, 68, 128, 23));
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
			jCalendarComboBox1.setBounds(new Rectangle(292, 68, 137, 23));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.addItem("进货单据");
			jComboBox1.addItem("出货单据");
			jComboBox1.setBounds(new Rectangle(83, 38, 128, 23));
			jComboBox1.setSelectedIndex(0);
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(662, 40, 94, 23));
			jButton2.setText("打印");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() < 1) {
						return;
					}
					CommonDataSource imgExgDS = new CommonDataSource(tableModel
							.getList());

					List company = new Vector(); // 只有一条记录
					company.add(CommonVars.getCurrUser().getCompany());
					CommonDataSource companyDS = new CommonDataSource(company);

					InputStream masterReportStream = DgTransferClientCollate.class
							.getResourceAsStream("report/TransferClientCollate.jasper");
					InputStream detailReportStream = DgTransferClientCollate.class
							.getResourceAsStream("report/TransferClientCollateSub.jasper");
					try {
						JasperReport detailReport = (JasperReport) JRLoader
								.loadObject(detailReportStream);
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("printDate", CommonVars.nowToDate());
						parameters.put("companyName", CommonVars.getCurrUser()
								.getCompany().getName());
						parameters.put("imgExgDS", imgExgDS);// 子数据源
						parameters.put("detailReport", detailReport);// 子报表
						JasperPrint jasperPrint;
						jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, companyDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new Rectangle(83, 8, 128, 23));
			cbbProjectType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cbbEmsNo.removeAllItems();
					if (cbbProjectType.getSelectedIndex() == 0) {
						lbEmsNo.setText("帐册编号");
					} else if (cbbProjectType.getSelectedIndex() == 1) {
						lbEmsNo.setText("手册编号");
					} else if (cbbProjectType.getSelectedIndex() == 2) {
						lbEmsNo.setText("手册编号");
					} else {
						return;
					}
					ItemProperty item = (ItemProperty) cbbProjectType
							.getSelectedItem();
					Integer projectType = Integer.parseInt(item.getCode());
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						List dataSource = transferFactoryManageAction
								.findEmsHeadByProjectType(new Request(
										CommonVars.getCurrUser(), true),
										projectType);
						cbbEmsNo.addItem("");
						for (int i = 0; i < dataSource.size(); i++) {
							TempCustomsEmsInfo temp = (TempCustomsEmsInfo) dataSource
									.get(i);
							cbbEmsNo.addItem(temp.getEmsNo());
						}
						cbbEmsNo.setSelectedIndex(0);
					}
				}
			});
		}
		return cbbProjectType;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(292, 8, 137, 23));
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(512, 38, 100, 23));
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(512, 8, 100, 23));
		}
		return tfName;
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new Rectangle(617, 8, 20, 22));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc) (jComboBox.getSelectedItem());

					boolean isImp = true;
					if ("出货单据".equals(jComboBox1.getSelectedItem())) {
						isImp = false;
					}

					Object obj = CasQuery.getInstance()
							.findHsNameFromCustomsEnvelopCommodityInfo(isImp,
									true,
									scmCoc != null ? scmCoc.getName() : "");
					if (obj != null) {
						tfName.setText((String) ((TempObject) obj).getObject());
					}
				}
			});
		}
		return btnName;
	}

	/**
	 * This method initializes btnSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSpec() {
		if (btnSpec == null) {
			btnSpec = new JButton();
			btnSpec.setBounds(new Rectangle(617, 38, 20, 22));
			btnSpec.setText("...");
			btnSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc) (jComboBox.getSelectedItem());
					boolean isImp = true;
					if ("出货单据".equals(jComboBox1.getSelectedItem())) {
						isImp = false;
					}

					Object obj = CasQuery.getInstance()
							.findHsNameFromCustomsEnvelopCommodityInfo(isImp,
									false,
									scmCoc != null ? scmCoc.getName() : "");
					if (obj != null) {
						tfSpec.setText((String) ((TempObject) obj).getObject());
					}
				}
			});
		}
		return btnSpec;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
