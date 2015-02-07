/*
 * Created on 2004-9-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Condition;
import com.bestway.common.ConsignQueryCondition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JCheckBox;

/**
 * @author
 * 海关帐--委托外发加工帐
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgConsignQueryExtend extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JCalendarComboBox startDate = null;
	private JCalendarComboBox endDate = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
	/**
	 * 客户名称 
	 */
	private JComboBox cmbCustomer = null;
	/**
	 * 仓库名称
	 */
	private JComboBox cmbWareSet = null;
	/**
	 * 工厂商品名称
	 */
	private JTextField tfName = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 查询
	 */
	private JButton btnQuery = null;
	/**
	 * 打印
	 */
	private JButton btnPrint = null;
	/**
	 * 关闭
	 */
	private JButton btnExit = null;
	/**
	 * 海关帐接口
	 */
	private CasAction casAction = null;
	/**
	 * 类型
	 */
	private String materialType = MaterielType.MATERIEL;  //  @jve:decl-index=0:
	/**
	 * 表格模型
	 */
	private JTableListModel tableModel = null;
	private List impExpInfos = null;
	/**
	 * 料件操作管理接口
	 */
	private MaterialManageAction materialManageAction = null;
	/**
	 * 工厂名称选择
	 */
	private JButton btnName = null;
	private JLabel jLabel11 = null;
	private JTextField tfPtNo = null;
	/**
	 * 工厂料号选择
	 */
	private JButton btnPtNo = null;
	private JLabel jLabel12 = null;
	/**
	 * 结束工厂料号
	 */
	private JTextField tfEndPtNo = null;
	/**
	 * 工厂料号结束选择
	 */
	private JButton btnEndPtNo = null;
	/**
	 * 小数位  默认6位
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();  //  @jve:decl-index=0:
	private JPanel jPanel71 = null;
	private JComboBox cmbCustomer1 = null;
	private JComboBox cmbWareSet1 = null;
	private JTextField tfName1 = null;
	private JLabel jLabel71 = null;
	private JLabel jLabel81 = null;
	private JLabel jLabel91 = null;
	private JCalendarComboBox startDate1 = null;
	private JCalendarComboBox endDate1 = null;
	private JButton btnQuery1 = null;
	private JButton btnPrint1 = null;
	private JButton btnExit1 = null;
	private JLabel jLabel101 = null;
	private JLabel jLabel51 = null;
	private JButton btnName1 = null;
	private JLabel jLabel111 = null;
	private JTextField tfPtNo1 = null;
	private JButton btnPtNo1 = null;
	private JLabel jLabel121 = null;
	private JTextField tfEndPtNo1 = null;
	private JButton btnEndPtNo1 = null;
	private JPanel jPanel72 = null;
	private JComboBox cmbCustomer2 = null;
	private JComboBox cmbWareSet2 = null;
	private JTextField tfName2 = null;
	private JLabel jLabel72 = null;
	private JLabel jLabel82 = null;
	private JLabel jLabel92 = null;
	private JCalendarComboBox startDate2 = null;
	private JCalendarComboBox endDate2 = null;
	private JButton btnQuery2 = null;
	private JButton btnPrint2 = null;
	private JButton btnExit2 = null;
	private JLabel jLabel102 = null;
	private JLabel jLabel52 = null;
	private JButton btnName2 = null;
	private JLabel jLabel112 = null;
	private JTextField tfPtNo2 = null;
	private JButton btnPtNo2 = null;
	private JLabel jLabel122 = null;
	private JTextField tfEndPtNo2 = null;
	private JButton btnEndPtNo2 = null;
	private JCheckBox cbIsTotalFrontAmount = null;
	private JCheckBox cbOnlyMinus = null;
	private JCheckBox cbIsComputeBan = null;

	/**
	 * This is the default constructor
	 */
	public DgConsignQueryExtend() {
		super(false);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}

	private void initUIComponents() {
		fillCustomer();
		fillWareSet();

		initTable(new ArrayList());

		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.startDate.setDate(beginClarendar.getTime());
		this.startDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.endDate.setDate(endClarendar.getTime());
		this.endDate.setCalendar(endClarendar);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("委外进出仓帐查询");
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jLabel.setText("0");
			jLabel.setBounds(3, 34, 37, 32);
			jLabel1.setText("JLabel");
			jLabel1.setBounds(3, 65, 37, 32);
			jLabel2.setText("JLabel");
			jLabel2.setBounds(3, 96, 37, 32);
			jLabel3.setText("JLabel");
			jLabel3.setBounds(3, 1, 37, 32);
			jLabel4.setText("日期 从");
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (startDate == null) {
			startDate = new JCalendarComboBox();
			startDate.setBounds(302, 14, 141, 21);
		}
		return startDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (endDate == null) {
			endDate = new JCalendarComboBox();
			endDate.setLocation(468, 14);
			endDate.setSize(147, 21);
		}
		return endDate;
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
			jSplitPane.setDividerSize(1);
			jSplitPane.setDividerLocation(98);
			jSplitPane.setTopComponent(getJPanel7());
			jSplitPane.setBottomComponent(getJPanel6());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(449, 39, 15, 19));
			jLabel12.setText("止");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(238, 39, 64, 20));
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setText("工厂料号从");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jLabel7.setText("客户名称");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(24, 17, 64, 20);
			jLabel8.setText("仓库");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(24, 38, 64, 20);
			jLabel9.setText("工厂商品名称");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(14, 60, 74, 20);
			jLabel10.setText("日期 从");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(253, 16, 49, 18);
			jPanel7
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"过滤条件",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									new java.awt.Color(51, 51, 51)));
			jLabel5.setBounds(449, 14, 16, 20);
			jLabel5.setText("止");
			jPanel7.add(getJComboBox1(), null);
			jPanel7.add(getJComboBox2(), null);
			jPanel7.add(getJComboBox3(), null);
			jPanel7.add(jLabel7, null);
			jPanel7.add(jLabel8, null);
			jPanel7.add(jLabel9, null);
			jPanel7.add(getJCalendarComboBox(), null);
			jPanel7.add(getJCalendarComboBox1(), null);
			jPanel7.add(getJButton(), null);
			jPanel7.add(getBtnPrint(), null);
			jPanel7.add(getJButton2(), null);
			jPanel7.add(jLabel10, null);
			jPanel7.add(jLabel5, null);
			jPanel7.add(getBtnName(), null);
			jPanel7.add(jLabel11, null);
			jPanel7.add(getTfPtNo(), null);
			jPanel7.add(getBtnPtNo(), null);
			jPanel7.add(jLabel12, null);
			jPanel7.add(getTfEndPtNo(), null);
			jPanel7.add(getJButton3(), null);
			jPanel7.add(getCbIsTotalFrontAmount(), null);
			jPanel7.add(getCbOnlyMinus(), null);
			jPanel7.add(getCbIsComputeBan(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (cmbCustomer == null) {
			cmbCustomer = new JComboBox();
			cmbCustomer.setBounds(93, 17, 147, 20);
		}
		return cmbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cmbWareSet == null) {
			cmbWareSet = new JComboBox();
			cmbWareSet.setBounds(93, 38, 147, 20);
		}
		return cmbWareSet;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getJComboBox3() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(93, 60, 128, 20);
		}
		return tfName;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(632, 14, 65, 23);
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ConsignQueryCondition condition = getCondition();
					new SearchThread(condition).start();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 查询线程
	 * @author Administrator
	 *
	 */
	class SearchThread extends Thread {
		ConsignQueryCondition condition = null;

		public SearchThread(ConsignQueryCondition condition) {
			this.condition = condition;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
				CommonProgress.showProgressDialog(flag,
						DgConsignQueryExtend.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				impExpInfos = casAction.findConsignQueryInfo(new Request(CommonVars
						.getCurrUser(), true), condition);
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgConsignQueryExtend.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (impExpInfos != null && !impExpInfos.isEmpty()) {
					tableModel.setList(impExpInfos);
				} else {
					initTable(new ArrayList());
					JOptionPane.showMessageDialog(DgConsignQueryExtend.this,
							"没有查到符合条件的记录！", "提示！", 2);
				}
			}
			btnQuery.setEnabled(true);
		}
	}

	/**
	 * 获取查询条件
	 * @return
	 */
	private ConsignQueryCondition getCondition() {
		ConsignQueryCondition condition = new ConsignQueryCondition();
		condition.setScmCoc((ScmCoc)(getJComboBox1().getSelectedItem()));
		condition.setStartDate(getJCalendarComboBox().getDate());
		condition.setEndDate(getJCalendarComboBox1().getDate());
		condition.setWareSet((WareSet)(getJComboBox2().getSelectedItem()));
		condition.setStartPartNo(getTfPtNo().getText());
		condition.setEndPartNo(getTfEndPtNo().getText());
		condition.setName(this.getJComboBox3().getText());
		condition.setIsCompute(this.getCbIsTotalFrontAmount().isSelected());
		condition.setIsComputeBan(getCbIsComputeBan().isSelected());
		return condition;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(632, 39, 65, 23);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpInfos == null || impExpInfos.isEmpty()) {
						JOptionPane.showMessageDialog(
								DgConsignQueryExtend.this, "没有列印的记录！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					btnPrint.setEnabled(false);
					try {
						CommonDataSource imgExgDS = new CommonDataSource(
								impExpInfos, maximumFractionDigits);
						List<BaseCompany> company = new ArrayList<BaseCompany>();
						company.add(CommonVars.getCurrUser().getCompany());
						InputStream masterReportStream = DgConsignQueryExtend.class
								.getResourceAsStream("report/CustomInoutWareReportCope.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						// if (materialType == MaterielType.MATERIEL) {
						parameters.put("title", "委外进出仓帐");
						// } else if (materialType ==
						// MaterielType.FINISHED_PRODUCT) {
						// parameters.put("title", "成品进出仓帐");
						// } else if (materialType == MaterielType.MACHINE) {
						// parameters.put("title", "设备进出仓帐");
						// } else if (materialType ==
						// MaterielType.REMAIN_MATERIEL) {
						// parameters.put("title", "边角料进出仓帐");
						// } else if (materialType == MaterielType.BAD_PRODUCT)
						// {
						// parameters.put("title", "残次品进出仓帐");
						// }
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, imgExgDS);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
					btnPrint.setEnabled(true);
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(632, 64, 65, 23);
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	private void fillCustomer() {
		cmbCustomer.removeAllItems();
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cmbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cmbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cmbCustomer, "code", "name", 270);
		this.cmbCustomer.setSelectedItem(null);
	}

	private void fillWareSet() {
		cmbWareSet.removeAllItems();
		List wareSets = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		this.cmbWareSet.setModel(new DefaultComboBoxModel(wareSets.toArray()));
		this.cmbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cmbWareSet, "code", "name", 270);
		this.cmbWareSet.setSelectedItem(null);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("BOM编号(料号)", "ptPart", 100));
						list.add(addColumn("工厂商品名称", "ptName", 150));
						list.add(addColumn("工厂规格型号", "ptSpec", 120));
						list.add(addColumn("进出仓日期", "inOutDate", 100));
						list.add(addColumn("单据类型", "billType", 100));//5

						list.add(addColumn("凭证号", "pingZhenNoOfIn", 120));
						list.add(addColumn("数量", "numOfIn", 60));//7

						list.add(addColumn("凭证号", "pingZhenNoOfOut", 60));
						list.add(addColumn("出仓料号", "ptPartOfOut", 80));
						list.add(addColumn("数量", "numOfOut", 60));
						list.add(addColumn("折算数量", "numOfZheSuan", 60));
						list.add(addColumn("结存", "jieCun", 150));

						list.add(addColumn("制单号", "zhiDanHao", 100));
						list.add(addColumn("出送货商家名称 ", "customerName", 150));
						list.add(addColumn("商品序号", "ptNo", 100));
						list.add(addColumn("工厂单位", "unitName", 80));
						list.add(addColumn("仓库", "storeName", 80));
						list.add(addColumn("备注", "note", 100));

						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_in = new ColumnGroup("进仓");
		g_in.add(cm.getColumn(6));
		g_in.add(cm.getColumn(7));

		ColumnGroup g_out = new ColumnGroup("出仓");
		g_out.add(cm.getColumn(8));
		g_out.add(cm.getColumn(9));
		g_out.add(cm.getColumn(10));
		g_out.add(cm.getColumn(11));

		ColumnGroup g_rs = new ColumnGroup("结存");
		g_rs.add(cm.getColumn(12));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_in);
		header.addColumnGroup(g_out);
		header.addColumnGroup(g_rs);
		jTable.repaint();
	}

	/**
	 * This method initializes btnName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnName() {
		if (btnName == null) {
			btnName = new JButton();
			btnName.setBounds(new java.awt.Rectangle(221, 60, 19, 20));
			btnName.setText("...");
			btnName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<EnterpriseMaterial> list = CasQuery.getInstance()
							.findEnterpriseMaterial();
					if (list != null && list.size() > 0) {
						EnterpriseMaterial em = list.get(0);
						tfName.setText(em.getFactoryName());
					}
				}
			});
		}
		return btnName;
	}

	/**
	 * This method initializes tfPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new java.awt.Rectangle(302, 39, 124, 20));
			tfPtNo.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtNo.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfEndPtNo.setEditable(true);
						btnEndPtNo.setEnabled(true);
					} else {
						tfEndPtNo.setEditable(false);
						btnEndPtNo.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPtNo() {
		if (btnPtNo == null) {
			btnPtNo = new JButton();
			btnPtNo.setBounds(new java.awt.Rectangle(424, 39, 19, 20));
			btnPtNo.setText("...");
			btnPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<EnterpriseMaterial> list = CasQuery.getInstance()
							.findEnterpriseMaterial();
					if (list != null && list.size() > 0) {
						EnterpriseMaterial em = list.get(0);
						tfPtNo.setText(em.getPtNo());
					}else{
						System.out.println("垃圾.....");
					}
				}
			});
		}
		return btnPtNo;
	}

	/**
	 * This method initializes tfEndPtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndPtNo() {
		if (tfEndPtNo == null) {
			tfEndPtNo = new JTextField();
			tfEndPtNo.setBounds(new java.awt.Rectangle(468, 39, 130, 20));
			tfEndPtNo.setEditable(false);
			tfEndPtNo.setEnabled(true);
		}
		return tfEndPtNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setBounds(new java.awt.Rectangle(598, 39, 19, 19));
			btnEndPtNo.setEnabled(false);
			btnEndPtNo.setText("...");
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<EnterpriseMaterial> list = CasQuery.getInstance()
							.findEnterpriseMaterial();
					if (list != null && list.size() > 0) {
						EnterpriseMaterial em = list.get(0);
						tfEndPtNo.setText(em.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
	}

	/**
	 * This method initializes jPanel71	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel71() {
		if (jPanel71 == null) {
			jLabel121 = new JLabel();
			jLabel121.setBounds(new Rectangle(449, 39, 15, 19));
			jLabel121.setText("\u6b62");
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(238, 39, 64, 20));
			jLabel111.setText("\u5de5\u5382\u6599\u53f7\u4ece");
			jLabel111.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel51 = new JLabel();
			jLabel51.setBounds(new java.awt.Rectangle(449,14,16,20));
			jLabel51.setText("\u6b62");
			jLabel101 = new JLabel();
			jLabel101.setBounds(new java.awt.Rectangle(253,16,49,18));
			jLabel101.setText("\u65e5\u671f \u4ece");
			jLabel101.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel91 = new JLabel();
			jLabel91.setBounds(new java.awt.Rectangle(14,60,74,20));
			jLabel91.setText("\u5de5\u5382\u5546\u54c1\u540d\u79f0");
			jLabel91.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel81 = new JLabel();
			jLabel81.setBounds(new java.awt.Rectangle(24,38,64,20));
			jLabel81.setText("\u4ed3\u5e93");
			jLabel81.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel71 = new JLabel();
			jLabel71.setBounds(new java.awt.Rectangle(24,17,64,20));
			jLabel71.setText("\u5ba2\u6237\u540d\u79f0");
			jLabel71.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel71 = new JPanel();
			jPanel71.setLayout(null);
			jPanel71.setBounds(new Rectangle(380, 64, 233, 28));
			jPanel71.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "\u8fc7\u6ee4\u6761\u4ef6", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel71.add(getCmbCustomer1(), null);
			jPanel71.add(getCmbWareSet1(), null);
			jPanel71.add(getTfName1(), null);
			jPanel71.add(jLabel71, null);
			jPanel71.add(jLabel81, null);
			jPanel71.add(jLabel91, null);
			jPanel71.add(getStartDate1(), null);
			jPanel71.add(getEndDate1(), null);
			jPanel71.add(getBtnQuery1(), null);
			jPanel71.add(getBtnPrint1(), null);
			jPanel71.add(getBtnExit1(), null);
			jPanel71.add(jLabel101, null);
			jPanel71.add(jLabel51, null);
			jPanel71.add(getBtnName1(), null);
			jPanel71.add(jLabel111, null);
			jPanel71.add(getTfPtNo1(), null);
			jPanel71.add(getBtnPtNo1(), null);
			jPanel71.add(jLabel121, null);
			jPanel71.add(getTfEndPtNo1(), null);
			jPanel71.add(getBtnEndPtNo1(), null);
		}
		return jPanel71;
	}

	/**
	 * This method initializes cmbCustomer1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbCustomer1() {
		if (cmbCustomer1 == null) {
			cmbCustomer1 = new JComboBox();
			cmbCustomer1.setBounds(new java.awt.Rectangle(93,17,147,20));
		}
		return cmbCustomer1;
	}

	/**
	 * This method initializes cmbWareSet1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbWareSet1() {
		if (cmbWareSet1 == null) {
			cmbWareSet1 = new JComboBox();
			cmbWareSet1.setBounds(new java.awt.Rectangle(93,38,147,20));
		}
		return cmbWareSet1;
	}

	/**
	 * This method initializes tfName1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName1() {
		if (tfName1 == null) {
			tfName1 = new JTextField();
			tfName1.setBounds(new java.awt.Rectangle(93,60,128,20));
		}
		return tfName1;
	}

	/**
	 * This method initializes startDate1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getStartDate1() {
		if (startDate1 == null) {
			startDate1 = new JCalendarComboBox();
			startDate1.setBounds(new java.awt.Rectangle(302,14,141,21));
		}
		return startDate1;
	}

	/**
	 * This method initializes endDate1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getEndDate1() {
		if (endDate1 == null) {
			endDate1 = new JCalendarComboBox();
			endDate1.setLocation(new java.awt.Point(468,14));
			endDate1.setSize(new java.awt.Dimension(147,21));
		}
		return endDate1;
	}

	/**
	 * This method initializes btnQuery1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery1() {
		if (btnQuery1 == null) {
			btnQuery1 = new JButton();
			btnQuery1.setBounds(new java.awt.Rectangle(632,14,65,23));
			btnQuery1.setText("\u67e5\u8be2");
		}
		return btnQuery1;
	}

	/**
	 * This method initializes btnPrint1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrint1() {
		if (btnPrint1 == null) {
			btnPrint1 = new JButton();
			btnPrint1.setBounds(new java.awt.Rectangle(632,39,65,23));
			btnPrint1.setText("\u6253\u5370");
		}
		return btnPrint1;
	}

	/**
	 * This method initializes btnExit1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit1() {
		if (btnExit1 == null) {
			btnExit1 = new JButton();
			btnExit1.setBounds(new java.awt.Rectangle(632,64,65,23));
			btnExit1.setText("\u5173\u95ed");
		}
		return btnExit1;
	}

	/**
	 * This method initializes btnName1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnName1() {
		if (btnName1 == null) {
			btnName1 = new JButton();
			btnName1.setBounds(new Rectangle(221, 60, 19, 20));
			btnName1.setText("...");
		}
		return btnName1;
	}

	/**
	 * This method initializes tfPtNo1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtNo1() {
		if (tfPtNo1 == null) {
			tfPtNo1 = new JTextField();
			tfPtNo1.setBounds(new Rectangle(302, 39, 124, 20));
		}
		return tfPtNo1;
	}

	/**
	 * This method initializes btnPtNo1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPtNo1() {
		if (btnPtNo1 == null) {
			btnPtNo1 = new JButton();
			btnPtNo1.setBounds(new Rectangle(424, 39, 19, 20));
			btnPtNo1.setText("...");
		}
		return btnPtNo1;
	}

	/**
	 * This method initializes tfEndPtNo1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEndPtNo1() {
		if (tfEndPtNo1 == null) {
			tfEndPtNo1 = new JTextField();
			tfEndPtNo1.setBounds(new Rectangle(468, 39, 130, 20));
			tfEndPtNo1.setEditable(false);
			tfEndPtNo1.setEnabled(true);
		}
		return tfEndPtNo1;
	}

	/**
	 * This method initializes btnEndPtNo1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEndPtNo1() {
		if (btnEndPtNo1 == null) {
			btnEndPtNo1 = new JButton();
			btnEndPtNo1.setBounds(new Rectangle(598, 39, 19, 19));
			btnEndPtNo1.setText("...");
			btnEndPtNo1.setEnabled(false);
		}
		return btnEndPtNo1;
	}

	/**
	 * This method initializes jPanel72	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel72() {
		if (jPanel72 == null) {
			jLabel122 = new JLabel();
			jLabel122.setBounds(new Rectangle(449, 39, 15, 19));
			jLabel122.setText("\u6b62");
			jLabel112 = new JLabel();
			jLabel112.setBounds(new Rectangle(238, 39, 64, 20));
			jLabel112.setText("\u5de5\u5382\u6599\u53f7\u4ece");
			jLabel112.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel52 = new JLabel();
			jLabel52.setBounds(new java.awt.Rectangle(449,14,16,20));
			jLabel52.setText("\u6b62");
			jLabel102 = new JLabel();
			jLabel102.setBounds(new java.awt.Rectangle(253,16,49,18));
			jLabel102.setText("\u65e5\u671f \u4ece");
			jLabel102.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel92 = new JLabel();
			jLabel92.setBounds(new java.awt.Rectangle(14,60,74,20));
			jLabel92.setText("\u5de5\u5382\u5546\u54c1\u540d\u79f0");
			jLabel92.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel82 = new JLabel();
			jLabel82.setBounds(new java.awt.Rectangle(24,38,64,20));
			jLabel82.setText("\u4ed3\u5e93");
			jLabel82.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel72 = new JLabel();
			jLabel72.setBounds(new java.awt.Rectangle(24,17,64,20));
			jLabel72.setText("\u5ba2\u6237\u540d\u79f0");
			jLabel72.setHorizontalAlignment(SwingConstants.RIGHT);
			jPanel72 = new JPanel();
			jPanel72.setLayout(null);
			jPanel72.setBounds(new Rectangle(308, 65, 244, 27));
			jPanel72.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "\u8fc7\u6ee4\u6761\u4ef6", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel72.add(getCmbCustomer2(), null);
			jPanel72.add(getCmbWareSet2(), null);
			jPanel72.add(getTfName2(), null);
			jPanel72.add(jLabel72, null);
			jPanel72.add(jLabel82, null);
			jPanel72.add(jLabel92, null);
			jPanel72.add(getStartDate2(), null);
			jPanel72.add(getEndDate2(), null);
			jPanel72.add(getBtnQuery2(), null);
			jPanel72.add(getBtnPrint2(), null);
			jPanel72.add(getBtnExit2(), null);
			jPanel72.add(jLabel102, null);
			jPanel72.add(jLabel52, null);
			jPanel72.add(getBtnName2(), null);
			jPanel72.add(jLabel112, null);
			jPanel72.add(getTfPtNo2(), null);
			jPanel72.add(getBtnPtNo2(), null);
			jPanel72.add(jLabel122, null);
			jPanel72.add(getTfEndPtNo2(), null);
			jPanel72.add(getBtnEndPtNo2(), null);
		}
		return jPanel72;
	}

	/**
	 * This method initializes cmbCustomer2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbCustomer2() {
		if (cmbCustomer2 == null) {
			cmbCustomer2 = new JComboBox();
			cmbCustomer2.setBounds(new java.awt.Rectangle(93,17,147,20));
		}
		return cmbCustomer2;
	}

	/**
	 * This method initializes cmbWareSet2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbWareSet2() {
		if (cmbWareSet2 == null) {
			cmbWareSet2 = new JComboBox();
			cmbWareSet2.setBounds(new java.awt.Rectangle(93,38,147,20));
		}
		return cmbWareSet2;
	}

	/**
	 * This method initializes tfName2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName2() {
		if (tfName2 == null) {
			tfName2 = new JTextField();
			tfName2.setBounds(new java.awt.Rectangle(93,60,128,20));
		}
		return tfName2;
	}

	/**
	 * This method initializes startDate2	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getStartDate2() {
		if (startDate2 == null) {
			startDate2 = new JCalendarComboBox();
			startDate2.setBounds(new java.awt.Rectangle(302,14,141,21));
		}
		return startDate2;
	}

	/**
	 * This method initializes endDate2	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getEndDate2() {
		if (endDate2 == null) {
			endDate2 = new JCalendarComboBox();
			endDate2.setLocation(new java.awt.Point(468,14));
			endDate2.setSize(new java.awt.Dimension(147,21));
		}
		return endDate2;
	}

	/**
	 * This method initializes btnQuery2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery2() {
		if (btnQuery2 == null) {
			btnQuery2 = new JButton();
			btnQuery2.setBounds(new java.awt.Rectangle(632,14,65,23));
			btnQuery2.setText("\u67e5\u8be2");
		}
		return btnQuery2;
	}

	/**
	 * This method initializes btnPrint2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrint2() {
		if (btnPrint2 == null) {
			btnPrint2 = new JButton();
			btnPrint2.setBounds(new java.awt.Rectangle(632,39,65,23));
			btnPrint2.setText("\u6253\u5370");
		}
		return btnPrint2;
	}

	/**
	 * This method initializes btnExit2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit2() {
		if (btnExit2 == null) {
			btnExit2 = new JButton();
			btnExit2.setBounds(new java.awt.Rectangle(632,64,65,23));
			btnExit2.setText("\u5173\u95ed");
		}
		return btnExit2;
	}

	/**
	 * This method initializes btnName2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnName2() {
		if (btnName2 == null) {
			btnName2 = new JButton();
			btnName2.setBounds(new Rectangle(221, 60, 19, 20));
			btnName2.setText("...");
		}
		return btnName2;
	}

	/**
	 * This method initializes tfPtNo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtNo2() {
		if (tfPtNo2 == null) {
			tfPtNo2 = new JTextField();
			tfPtNo2.setBounds(new Rectangle(302, 39, 124, 20));
		}
		return tfPtNo2;
	}

	/**
	 * This method initializes btnPtNo2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPtNo2() {
		if (btnPtNo2 == null) {
			btnPtNo2 = new JButton();
			btnPtNo2.setBounds(new Rectangle(424, 39, 19, 20));
			btnPtNo2.setText("...");
		}
		return btnPtNo2;
	}

	/**
	 * This method initializes tfEndPtNo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEndPtNo2() {
		if (tfEndPtNo2 == null) {
			tfEndPtNo2 = new JTextField();
			tfEndPtNo2.setBounds(new Rectangle(468, 39, 130, 20));
			tfEndPtNo2.setEditable(false);
			tfEndPtNo2.setEnabled(true);
		}
		return tfEndPtNo2;
	}

	/**
	 * This method initializes btnEndPtNo2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEndPtNo2() {
		if (btnEndPtNo2 == null) {
			btnEndPtNo2 = new JButton();
			btnEndPtNo2.setBounds(new Rectangle(598, 39, 19, 19));
			btnEndPtNo2.setText("...");
			btnEndPtNo2.setEnabled(false);
		}
		return btnEndPtNo2;
	}

	/**
	 * This method initializes cbIsTotalFrontAmount	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsTotalFrontAmount() {
		if (cbIsTotalFrontAmount == null) {
			cbIsTotalFrontAmount = new JCheckBox();
			cbIsTotalFrontAmount.setBounds(new Rectangle(266, 68, 103, 20));
			cbIsTotalFrontAmount.setText("\u7edf\u8ba1\u4e0a\u671f\u7ed3\u5b58");
		}
		return cbIsTotalFrontAmount;
	}

	/**
	 * This method initializes cbOnlyMinus	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbOnlyMinus() {
		if (cbOnlyMinus == null) {
			cbOnlyMinus = new JCheckBox();
			cbOnlyMinus.setBounds(new Rectangle(383, 66, 108, 26));
			cbOnlyMinus.setText("\u663e\u793a\u8d1f\u6570\u7ed3\u5b58");
		}
		return cbOnlyMinus;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsComputeBan() {
		if (cbIsComputeBan == null) {
			cbIsComputeBan = new JCheckBox();
			cbIsComputeBan.setBounds(new Rectangle(490, 71, 119, 22));
			cbIsComputeBan.setText("是否统计半成品");
			cbIsComputeBan.setSelected(true);
		}
		return cbIsComputeBan;
	}

}
