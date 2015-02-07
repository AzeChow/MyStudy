package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CarryBalance;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
@SuppressWarnings("unchecked")
public class DgSupplierBalanceAllInfoNew extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764955124734067792L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel5 = null;
	private JComboBox cbbClientName = null;
	private JTextField tfWareName = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JTextField tfWareSpec = null;
	private JButton btnQuery = null;
	private JButton btnPrint = null;
	private JButton btnClose = null;
	private JSplitPane jSplitPane = null;
	private JPanel pnTop = null;
	/**
	 * 料件／成品
	 */
	private Boolean isM = true;  //  @jve:decl-index=0:
	/**
	 * 查询action
	 */
	private CasCheckAction casCheckAction = null;
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPopupMenu pmRelation = null; // @jve:decl-index=0:visual-constraint="837,215"
	private JMenuItem miEnvelopDetailInfo = null; // @jve:decl-index=0:visual-constraint="811,257"
	private JMenuItem miRecvSendDetailInfo = null; // @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miTransferDetailInfo = null; // @jve:decl-index=0:visual-constraint="812,321"
	private JMenuItem miBalanceDetailInfo = null; // @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miExeContractDetail = null; // @jve:decl-index=0:visual-constraint="813,382"

	// 关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private ScmCoc sScmCoc;

	public Boolean getIsM() {
		return isM;
	}

	public void setIsM(Boolean isM) {
		this.isM = isM;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgSupplierBalanceAllInfoNew() {
		super();
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(817, 567));
		this.setContentPane(getJContentPane());
		this.setTitle("***转厂差额总表");
		initUi();
		initTable(new ArrayList()); // 初始化表格
	}

	/**
	 * This method
	 */
	public void setVisible(boolean b) {
		jLabel.setText(isM == true ? "供应商" : "客户名称");
		super.setVisible(b);

	}

	/**
	 * 界面初始化
	 */
	private void initUi() {
		List list1 = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbClientName.setModel(new DefaultComboBoxModel(list1.toArray()));
		this.cbbClientName.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbClientName, "code", "name");
		this.cbbClientName.setUI(new CustomBaseComboBoxUI(250));
		this.cbbClientName.setSelectedItem(null);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			jTable = new MultiSpanCellTable(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			
			};
			
		}
		return jTable;
	}

	/**
	 * 初始化表格
	 */
	
	private JTableListModel initTable(List list) {
		
		// tableModel = new JTableListModel(jTable, list,
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				list, new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn(" 客户名称", "customerName", 140));// 1
						list.add(addColumn("商品名称", "name", 100));// 2
						list.add(addColumn("商品规格", "spec", 100));// 3
						list.add(addColumn("单位", "unitName", 60));// 4
						list.add(addColumn("期初数", "amountFirst", 60));// 5
						list.add(addColumn("业务类型", "type", 60));// 6
						list.add(addColumn("1月", "amountMonth1", 60));// 7
						list.add(addColumn("2月", "amountMonth2", 60));// 8
						list.add(addColumn("3月", "amountMonth3", 60));// 9
						list.add(addColumn("4月", "amountMonth4", 60));// 10
						list.add(addColumn("5月", "amountMonth5", 60));// 11
						list.add(addColumn("6月", "amountMonth6", 60));// 12
						list.add(addColumn("7月", "amountMonth7", 60));// 13
						list.add(addColumn("8月", "amountMonth8", 60));// 14
						list.add(addColumn("9月", "amountMonth9", 60));// 15
						list.add(addColumn("10月", "amountMonth10", 60));// 16
						list.add(addColumn("11月", "amountMonth11", 60));// 17
						list.add(addColumn("12月", "amountMonth12", 60));// 18
						list.add(addColumn("差额总值", "total", 60));// 19
						list.add(addColumn("年度合计", "yearTotal", 60));// 20
						//list.add(addColumn("转厂期限", "date", 100));// 21
						list.add(addColumn("关封余量", "remain", 60));// 21
						return list;
					}
				});
		tableModel.setSort(false);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(
					JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if(value != null && "0.0".equals(value.toString())) {
					value = null;
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				return this;
			}
		};
		jTable.getColumnModel().getColumn(7).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(10).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(11).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(12).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(13).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(14).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(15).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(16).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(17).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(18).setCellRenderer(cellRenderer);
		jTable.getColumnModel().getColumn(19).setCellRenderer(cellRenderer);
		
		return tableModel;
	}

	/**
	 * 
	 */
	public void combineTable() {
		((MultiSpanCellTable) jTable).splitRows2(new int[] { 1, 2, 3, 4, 5, 19,
				21 });
		((MultiSpanCellTable) jTable).combineRowsByIndeies(new int[] { 1, 2, 3,
				4, 5, 19, 21}, new int[] { 1, 2, 3, 4, 5, 19, 21});
	}

	/**
	 * This method initializes cbbClientName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbClientName() {
		if (cbbClientName == null) {
			cbbClientName = new JComboBox();
			cbbClientName.setBounds(new Rectangle(137, 12, 158, 24));
		}
		return cbbClientName;
	}

	/**
	 * This method initializes tfWareName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWareName() {
		if (tfWareName == null) {
			tfWareName = new JTextField();
			tfWareName.setBounds(new Rectangle(137, 41, 136, 24));
		}
		return tfWareName;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			// cbbBeginDate.setDate(findBeginData2());
			cbbBeginDate.setBounds(new Rectangle(420, 12, 158, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes tfWareSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWareSpec() {
		if (tfWareSpec == null) {
			tfWareSpec = new JTextField();
			tfWareSpec.setBounds(new Rectangle(420, 41, 136, 24));
		}
		return tfWareSpec;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(600, 12, 95, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btnQuery;
	}

	/**
	 * 查询
	 */
	public void queryData() {

		ConditionBalance conditionBalance = new ConditionBalance();
		conditionBalance.setM(isM);// 1.料件还是成品(进还是出)?
		ScmCoc sc = (ScmCoc) cbbClientName.getSelectedItem();// 2.客户供应商
		if (sc != null) {
			System.out.println(sc.getCode());
			conditionBalance.setCustomerCode(sc.getCode());
			conditionBalance.setCustomerName(sc.getName());
		} else {
			System.out.println("--------------");
			conditionBalance.setCustomerCode("");
			conditionBalance.setCustomerName("");
		}
		conditionBalance.setDate(cbbBeginDate.getDate() == null ? new Date()
				: cbbBeginDate.getDate());// 3.查询日期
		conditionBalance.setName(tfWareName.getText().trim());// 4.报关名称
		conditionBalance.setSpec(tfWareSpec.getText().trim());// 5.报关规格
		new Find(conditionBalance).execute();

	}

	class Find extends SwingWorker {

		private ConditionBalance conditionBalance = null;

		public Find(ConditionBalance conditionBalance) {
			this.conditionBalance = conditionBalance;
		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算

			// 查询
			CommonProgress.showProgressDialog(DgSupplierBalanceAllInfoNew.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			// 查询返回结果
			return casCheckAction.getCurrentBalanceInfoNew(new Request(CommonVars
					.getCurrUser()), conditionBalance);
		}

		@Override
		protected void done() {// 更新视图
			List list = null;
			try {
				list = (List) this.get();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(DgSupplierBalanceAllInfoNew.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
			combineTable();
		}
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
			btnPrint.setBounds(new Rectangle(704, 12, 95, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						List<CarryBalance> list = tableModel.getList();
						CarryBalance c = null;
						int n = 3;
						for (int i = 0; i < list.size(); i++) {
							c = list.get(i);
							c.setAmountFirst(CommonUtils.getDoubleByDigit(c.getAmountFirst(), n));
							c.setAmountMonth1(CommonUtils.getDoubleByDigit(c.getAmountMonth1(), n));
							c.setAmountMonth2(CommonUtils.getDoubleByDigit(c.getAmountMonth2(), n));
							c.setAmountMonth3(CommonUtils.getDoubleByDigit(c.getAmountMonth3(), n));
							c.setAmountMonth4(CommonUtils.getDoubleByDigit(c.getAmountMonth4(), n));
							c.setAmountMonth5(CommonUtils.getDoubleByDigit(c.getAmountMonth5(), n));
							c.setAmountMonth6(CommonUtils.getDoubleByDigit(c.getAmountMonth6(), n));
							c.setAmountMonth7(CommonUtils.getDoubleByDigit(c.getAmountMonth7(), n));
							c.setAmountMonth8(CommonUtils.getDoubleByDigit(c.getAmountMonth8(), n));
							c.setAmountMonth9(CommonUtils.getDoubleByDigit(c.getAmountMonth9(), n));
							c.setAmountMonth10(CommonUtils.getDoubleByDigit(c.getAmountMonth10(), n));
							c.setAmountMonth11(CommonUtils.getDoubleByDigit(c.getAmountMonth11(), n));
							c.setAmountMonth12(CommonUtils.getDoubleByDigit(c.getAmountMonth12(), n));
							c.setTotal(CommonUtils.getDoubleByDigit(c.getTotal(), n));
							c.setYearTotal(CommonUtils.getDoubleByDigit(c.getYearTotal(), n));
						}
						
						
						CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
								list);
						
						InputStream masterReportStream = DgSupplierBalanceAllInfoNew.class
								.getResourceAsStream("report/supplierBalanceAllInfo.jasper");
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("title", getTitle());
						parameters.put("impExpType", isM ? "转入" : "转出");
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								masterReportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
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
			btnClose.setBounds(new Rectangle(704, 43, 95, 24));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(2);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setDividerLocation(86);

		}
		return jSplitPane;
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.setBorder(BorderFactory.createTitledBorder(null, "选项",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jLabel5 = new JLabel();
			jLabel5.setText("商品规格");
			jLabel5.setBounds(new Rectangle(355, 41, 62, 24));
			jLabel3 = new JLabel();
			jLabel3.setText("报表日期");
			jLabel3.setBounds(new Rectangle(355, 12, 62, 24));
			jLabel2 = new JLabel();
			jLabel2.setText("商品名称");
			jLabel2.setBounds(new Rectangle(74, 41, 62, 24));
			jLabel = new JLabel();
			jLabel.setText("客户名称");
			jLabel.setBounds(new Rectangle(74, 12, 62, 24));
			pnTop.add(jLabel, null);
			pnTop.add(getCbbClientName(), null);
			pnTop.add(jLabel2, null);
			pnTop.add(getTfWareName(), null);
			pnTop.add(jLabel3, null);
			pnTop.add(getCbbBeginDate(), null);
			pnTop.add(jLabel5, null);
			pnTop.add(getTfWareSpec(), null);
			pnTop.add(getBtnClose(), null);
			pnTop.add(getBtnPrint(), null);
			pnTop.add(getBtnQuery(), null);
			pnTop.add(getJButton(), null);
			pnTop.add(getJButton1(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(274, 41, 21, 24));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc)(cbbClientName.getSelectedItem());
					
					Object obj = CasQuery.getInstance()
					.findHsNameFromCustomsEnvelopCommodityInfo(isM, true, scmCoc!=null?scmCoc.getName():"");
					if (obj != null) {
						tfWareName.setText((String) ((TempObject) obj)
								.getObject());
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(558, 41, 21, 24));
			jButton1.setText("...");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc)(cbbClientName.getSelectedItem());
					
					Object obj = CasQuery.getInstance()
							.findHsNameFromCustomsEnvelopCommodityInfo(isM ,
									false, scmCoc!=null?scmCoc.getName():"");
					if (obj != null) {
						tfWareSpec.setText((String) ((TempObject) obj)
								.getObject());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes pmRelation
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmRelation() {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();

			// 关封明细
			pmRelation.add(getMiEnvelopDetailInfo());

			// 收送货明细
			pmRelation.add(getMiRecvSendDetailInfo());

			// 结转明细
			pmRelation.add(getMiTransferDetailInfo());

			// 结转差额明细表
			pmRelation.add(getMiBalanceDetailInfo());

			// 合同执行情况
			pmRelation.add(getMiExeContractDetail());
		}
		return pmRelation;
	}

	/**
	 * This method initializes miEnvelopDetailInfo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiEnvelopDetailInfo() {
		if (miEnvelopDetailInfo == null) {
			miEnvelopDetailInfo = new JMenuItem();
			miEnvelopDetailInfo.setText("关封明细");
			miEnvelopDetailInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (isM) {
								DgSupplierEnvelopDetailInfo dg = new DgSupplierEnvelopDetailInfo();
								dg.setImport(true);
								dg.setQueryCondition(null, sDate, sHsCode,
										sHsName, sHsSpec, sScmCoc);

								dg.queryData();
								dg.setVisible(true);

							} else {
								DgClientEnvelopDetailInfo dg = new DgClientEnvelopDetailInfo();
								dg.setImport(false);
								dg.setQueryCondition(null, sDate, sHsCode,
										sHsName, sHsSpec, sScmCoc);

								dg.queryData();
								dg.setVisible(true);
							}
						}
					});
		}
		return miEnvelopDetailInfo;
	}

	/**
	 * This method initializes miRecvSendDetailInfo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiRecvSendDetailInfo() {
		if (miRecvSendDetailInfo == null) {
			miRecvSendDetailInfo = new JMenuItem();
			miRecvSendDetailInfo.setText("收/送货明细");
			miRecvSendDetailInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String type = MaterielType.MATERIEL;
							String title = "收货明细表";
							if (!isM) {
								type = MaterielType.FINISHED_PRODUCT;
								title = "送货明细表";
							}
							DgRecvSendDetailInfo dg = new DgRecvSendDetailInfo();
							dg.setMaterielType(type);
							dg.setTitle(title);
							dg.setQueryCondition(null, sDate, sHsCode, sHsName,
									sHsSpec, sScmCoc);

							dg.queryData();

							dg.setVisible(true);
						}
					});
		}
		return miRecvSendDetailInfo;
	}

	/**
	 * This method initializes miTransferDetailInfo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiTransferDetailInfo() {
		if (miTransferDetailInfo == null) {
			miTransferDetailInfo = new JMenuItem();
			miTransferDetailInfo.setText("转厂明细");
			miTransferDetailInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							boolean isImp = true;
							if (!isM) {
								isImp = false;
							}

							DgTransferDetailInfo dg = new DgTransferDetailInfo();
							dg.setIsImport(isImp);
							dg.setTitle("结转明细表");

							dg.setVisible(true);

						}
					});
		}
		return miTransferDetailInfo;
	}

	/**
	 * This method initializes miBalanceDetailInfo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBalanceDetailInfo() {
		if (miBalanceDetailInfo == null) {
			miBalanceDetailInfo = new JMenuItem();
			miBalanceDetailInfo.setText("结转差额明细表");
			miBalanceDetailInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgSupplierBalanceDetailInfo dg = new DgSupplierBalanceDetailInfo();
							dg.setTitle("结转差额分表");
							dg.setIsM(isM);

							dg.setQueryCondition(sDate, sHsName, sHsSpec);
							dg.queryData();

							dg.setVisible(true);

						}
					});
		}
		return miBalanceDetailInfo;
	}

	/**
	 * This method initializes miExeContractDetail
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiExeContractDetail() {
		if (miExeContractDetail == null) {
			miExeContractDetail = new JMenuItem();
			miExeContractDetail.setText("合同执行情况");
			miExeContractDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							FmContractExecStat dg = new FmContractExecStat();
							// ShowFormControl.showForm(dg);
							dg.setVisible(true);
						}
					});
		}
		return miExeContractDetail;
	}

	/**
	 * 关联查询时的参数注入
	 * 
	 * @param date
	 * @param hsName
	 * @param hsSpec
	 * @param customerName
	 * @author wss
	 */
	public void setQueryCondition(Date date, String hsName, String hsSpec,
			ScmCoc scmCoc) {
		if (date != null) {
			this.cbbBeginDate.setDate(date);
		}
		this.tfWareName.setText(hsName);
		this.tfWareSpec.setText(hsSpec);
		this.cbbClientName.setSelectedItem(scmCoc);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
