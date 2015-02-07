package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExg;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;
import javax.swing.SwingConstants;

public class PnECSStockOutSendExgPt extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTableListModel tableModel = null;
	private ECSCheckStockAction ecsCheckStockAction = null;
	private JToolBar toolBar;
	private JScrollPane scrollPaneP;
	private MultiSpanCellTable tablePrim;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnImport;
	private JButton btnSwitch;
	private JTextField tfNumb;
	private JTextField tfNump;
	private JTextField tfBegin;
	private JTextField tfEnd;
	private Request request;
	private JScrollPane scrollPaneH;
	private JTable tableHand;
	private JTabbedPane tabbedPane;
	private JPanel panelP;
	private JPanel panelH;
	private JTableListModelAdapter adapterConvert;
	private JTableListModel tableModelP;
	private JTableListModelAdapter adapterRawdata;
	private JTableListModel tableModelH;
	private JLabel label;
	private JTextField tfSeqNum;
	private JPanel panel;
	private List<ECSStockOutSendExg> listData;
	private FmECSStockOutSendExg parent;
	private JLabel label_1;


	/**
	 * This is the default constructor
	 */
	public PnECSStockOutSendExgPt(FmECSStockOutSendExg parent) {
		super();
		this.parent = parent;
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkStockOutSendExg(request);
		initialize();
		btnSwitch.setEnabled(false);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new java.awt.BorderLayout());
		this.add(getTabbedPane(), BorderLayout.CENTER);
		this.add(getPanel(), BorderLayout.NORTH);
		initTable();
		initBtnStatus(0);
		initBtnStatus(1);
	}

	public void initTable() {
		initJtableP(Collections.EMPTY_LIST);
		initJtableH(Collections.EMPTY_LIST);
	}
	
	void initJtableH(List list) {
		if (adapterRawdata == null) {
			adapterRawdata = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("成品料号", "baseExg.ptNo", 110));
					list.add(addColumn("成品序号", "baseExg.seqNum", 60));
					list.add(addColumn("成品版本", "baseExg.version", 50));
					list.add(addColumn("料件序号", "seqNum", 60));
					list.add(addColumn("料件名称", "hsName", 100));
					list.add(addColumn("型号规格", "hsSpec", 100));
					list.add(addColumn("计量单位", "hsUnit", 100));
					list.add(addColumn("单耗", "unitWaste", 50));
					list.add(addColumn("损耗", "waste", 100));
					list.add(addColumn("总耗用", "hsAmount", 100));					
					return list;
				}
			};
		}
		tableModelH = new JTableListModel(tableHand, list, adapterRawdata);
	}

	JTableListModel initJtableP(List list) {
		if (adapterConvert == null) {
			adapterConvert = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("料号", "ptNo", 110));
					list.add(addColumn("工厂名称", "ptName", 120));
					list.add(addColumn("工厂规格", "ptSpec", 120));
					list.add(addColumn("计量单位", "ptUnit", 100));
					//list.add(addColumn("工单号", "", 100));
					list.add(addColumn("版本号", "version", 50));
					list.add(addColumn("盘点库存", "ptAmount", 100));

					list.add(addColumn("成品备案序号", "seqNum", 100));
					list.add(addColumn("版本号", "version", 50));
					list.add(addColumn("报关商品名称", "hsName", 120));
					list.add(addColumn("报关商品规格", "hsSpec", 120));
					list.add(addColumn("计量单位", "hsUnit", 50));
					list.add(addColumn("折算报关数量", "hsAmount", 100));
					list.add(addColumn("折算系数", "unitConvert", 100));
					return list;
				}
			};
		}
		// tableModelP = new JTableListModel(tablePrim, list, adapterConvert);
		tableModelP = new AttributiveCellTableModel((MultiSpanCellTable) tablePrim, list, adapterConvert);
		TableColumnModel cm = tablePrim.getColumnModel();
		ColumnGroup exgGroup = new ColumnGroup("【工厂资料】");
		exgGroup.add(cm.getColumn(1));
		exgGroup.add(cm.getColumn(2));
		exgGroup.add(cm.getColumn(3));
		exgGroup.add(cm.getColumn(4));
		exgGroup.add(cm.getColumn(5));
		exgGroup.add(cm.getColumn(6));
		ColumnGroup hsImpGroup = new ColumnGroup("【报关资料】");
		hsImpGroup.add(cm.getColumn(7));
		hsImpGroup.add(cm.getColumn(8));
		hsImpGroup.add(cm.getColumn(9));
		hsImpGroup.add(cm.getColumn(10));
		hsImpGroup.add(cm.getColumn(11));
		hsImpGroup.add(cm.getColumn(12));
		hsImpGroup.add(cm.getColumn(13));

		GroupableTableHeader header = (GroupableTableHeader) tablePrim.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(hsImpGroup);
		return tableModelP;
	}

	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNump.setText(parent.getSection().getVerificationNo().toString());
		tfNumb.setText(parent.getSection().getCancelOwnerHead().getCancelTimes());
		tfBegin.setText(dFormat.format(parent.getSection().getBeginDate()));
		tfEnd.setText(dFormat.format(parent.getSection().getEndDate()));
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("导入原态", null, getPnRawdata(), null);
			tabbedPane.addTab("折算数据", null, getPnconvert(), null);
			tabbedPane.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					int index = tabbedPane.getSelectedIndex();
					if (index == 1) {
						//btnSwitch.setEnabled(true);
						btnImport.setEnabled(false);
						btnCalculate.setEnabled(false);
					} else if (index == 0) {
						//btnSwitch.setEnabled(false);
						btnImport.setEnabled(true);
						btnCalculate.setEnabled(true);
					}
				}
			});
		}
		return tabbedPane;
	}

	private JPanel getPnRawdata() {
		if (panelP == null) {
			panelP = new JPanel();
			panelP.setLayout(new BorderLayout(0, 0));
			panelP.add(getScrollPaneP(), BorderLayout.CENTER);
		}
		return panelP;
	}

	private JPanel getPnconvert() {
		if (panelH == null) {
			panelH = new JPanel();
			panelH.setLayout(new BorderLayout(0, 0));
			panelH.add(getScrollPaneH(), BorderLayout.CENTER);
		}
		return panelH;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 30));
			jLabel3.setText("\u76D8\u70B9\u6838\u67E5\u6279\u6B21\u9009\u62E9");
			jLabel3.setBounds(5, 5, 99, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(388, 5, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(527, 5, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);

			JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
			lblNewLabel.setBounds(149, 7, 78, 15);
			jPanel2.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
			lblNewLabel_1.setBounds(269, 7, 78, 15);
			jPanel2.add(lblNewLabel_1);
			jPanel2.add(getTfNumb());
			jPanel2.add(getTfNump());
			jPanel2.add(getTfBegin());
			jPanel2.add(getTfEnd());

			JButton btnNewButton_1 = new JButton("New button");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if (dgECSSection.isOk()) {
						parent.changePn(dgECSSection.getSection());
						fillValue();
						initBtnStatus(1);
					}
				}
			});
			btnNewButton_1.setBounds(105, 3, 34, 23);
			jPanel2.add(btnNewButton_1);
			jPanel2.add(getLabel_1());
		}
		return jPanel2;
	}

	
	
	/**
	 * 
	 * @param i
	 */
	private void initBtnStatus(int i) {
		switch (i) {
		case 0:
			btnCalculate.setEnabled(false);
			btnClean.setEnabled(false);
			btnExport.setEnabled(false);
			btnHistroy.setEnabled(false);
			btnImport.setEnabled(false);
			btnSwitch.setEnabled(false);
			break;
		case 1:
			btnHistroy.setEnabled(true);
			btnImport.setEnabled(true);
			break;
		case 2:
			btnCalculate.setEnabled(true);
			btnClean.setEnabled(true);
			btnExport.setEnabled(true);
			btnImport.setEnabled(true);
			btnHistroy.setEnabled(true);
			btnSwitch.setEnabled(true);
			break;
		default:
			break;
		}
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

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			toolBar.add(getLabel());
			toolBar.add(getTextField());
			toolBar.add(getBtnHistroy());
			toolBar.add(getBtnImport());
			toolBar.add(getBtnCalculate());
			toolBar.add(getBtnSwitch());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClean());
			toolBar.add(getBtnClose());
		}
		return toolBar;
	}

	private JButton getBtnHistroy() {
		if (btnHistroy == null) {
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u8BB0\u5F55");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSStockOutSendExgPt.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					PnECSStockOutSendExgPt.this.doFindStars();					
				}
			});
		}
		return btnHistroy;
	}

	private void doFindStars() {
		final String seqNum = tfSeqNum.getText().trim();		
		new AsynSwingWorker<List>() {
			int tableIndex = tabbedPane.getSelectedIndex();
			@Override
			protected List submit() {
				CommonProgress.showProgressDialog(parent);
				CommonProgress.setMessage("系统正在查询数据，请稍后...");
				Integer sn = (seqNum.isEmpty() ? null:Integer.parseInt(seqNum));
				try{
					initBtnStatus(0);
					if(tableIndex == 0)
						return ecsCheckStockAction.findECSStockOutSendExgs(request, parent.getSection(),sn);
					return  ecsCheckStockAction.findECSStockOutSendExgResolves(request, parent.getSection(),sn);
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}

			@Override
			protected void success(List data) {
					if(tableIndex == 0)
						initJtableP(data);
					else
						initJtableH(data);					
			};

		}.doWork();
	}

	/**
	 * 折算海关
	 * 
	 * @return
	 */
	private JButton getBtnCalculate() {
		if (btnCalculate == null) {
			btnCalculate = new JButton("2.\u6298\u7B97\u62A5\u5173\u6570\u91CF");
			btnCalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSStockOutSendExgPt.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					// ...
					tabbedPane.setSelectedIndex(0);
					
					PnECSStockOutSendExgPt.this.doConvertHs();
				}
			});
		}
		return btnCalculate;
	}

	private void doConvertHs() {
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.convertECSStockOutSendExgs(request, parent.getSection());
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();
				}
			}

			@Override
			protected void done() {
				try {
					initJtableP(this.get());
				} catch (Exception e2) {
					DgErrorMessage dgErrorMessage = new DgErrorMessage();
					String[] strs = e2.getMessage().split(",");
					List<String> list = new ArrayList<String>();
					for(String str:strs){
						list.add(str);
					}
					dgErrorMessage.initTable(list);
					CommonStepProgress.closeStepProgressDialog();
					dgErrorMessage.setVisible(true);
				}
			};

		}.execute();
	}

	/**
	 * 
	 * @return
	 */
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton("  \u5BFC\u51FAExcel  ");
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = tabbedPane.getSelectedIndex();
					if (index == 1) {
						tableModelH = (JTableListModel) tableHand.getModel();
						if (tableModelH != null && tableModelH.getRowCount() > 0) {
							tableModelH.getMiSaveTableListToExcel().doClick();
						}
					} else if (index == 0) {
						tableModelP = (JTableListModel) tablePrim.getModel();
						if (tableModelP != null && tableModelP.getRowCount() > 0) {
							tableModelP.getMiSaveTableListToExcel().doClick();
						}
					}

				}
			});
		}
		return btnExport;
	}

	private JButton getBtnClean() {
		if (btnClean == null) {
			btnClean = new JButton("  \u6E05\u7A7A\u5F53\u524D\u6570\u636E  ");
			btnClean.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSStockOutSendExgPt.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							// ....
							ecsCheckStockAction.deleteECSStockExgs(request, parent.getSection());
							initJtableP(Collections.EMPTY_LIST);
							initJtableH(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(PnECSStockOutSendExgPt.this, "删除失败！", "警告",
									JOptionPane.WARNING_MESSAGE);
						}

					} else {
						return;
					}
				}
			});
		}
		return btnClean;
	}

	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("  关闭  ");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parent.dispose();
				}
			});
		}
		return btnClose;
	}

	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton("1.\u5BFC\u5165\u76D8\u70B9\u6570\u91CF");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSStockOutSendExgPt.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					try {
						initBtnStatus(0);
						if(ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getSection(), ECSStockOutSendExg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							ecsCheckStockAction.deleteECSStockOutSendExgs(request, parent.getSection());
							initJtableH(Collections.EMPTY_LIST);
							initJtableP(Collections.EMPTY_LIST);
						}
						listData = new ArrayList<ECSStockOutSendExg>();
						tabbedPane.setSelectedIndex(0);
						
						DgECSImportPt<ECSStockOutSendExg> dgECSImport = new DgECSImportPt<ECSStockOutSendExg>(false);
						dgECSImport.setClazz("PnECSStockOutSendExgPt");
						dgECSImport.setListData(listData);
						dgECSImport.setSection(parent.getSection());
						dgECSImport.setVisible(true);
						initJtableP(listData);
					}finally{
						initBtnStatus(2);
					}
				}
			});
		}
		return btnImport;
	}

	/**
	 * 成品折算料件
	 * 
	 * @return
	 */
	private JButton getBtnSwitch() {
		if (btnSwitch == null) {
			btnSwitch = new JButton("3.\u6210\u54C1\u6298\u7B97\u6599\u4EF6\uFF08\u8D26\u518C\u5355\u8017\uFF09");
			btnSwitch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSStockOutSendExgPt.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					tabbedPane.setSelectedIndex(1);					
					PnECSStockOutSendExgPt.this.doConvertHsTo();
				}
			});
		}
		return btnSwitch;
	}

	private void doConvertHsTo() {
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.resolveECSStockOutSendExgResolves(request, parent.getSection());
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();
				}
			}

			@Override
			protected void done() {
				try {
					initJtableH(this.get());
				} catch (Exception e2) {
					DgErrorMessage dgErrorMessage = new DgErrorMessage();
					String[] strs = e2.getMessage().substring(e2.getMessage().indexOf(":") +1).split(",");
					List<String[]> list = new ArrayList<String[]>();
					
					for(String str:strs){
						String[] istrs = new String[]{str,"序号找不到BOM"};
						list.add(istrs);
					}
					dgErrorMessage.initTable(list);
					CommonStepProgress.closeStepProgressDialog();
					dgErrorMessage.setVisible(true);
				}
			};

		}.execute();
	}

	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setEditable(false);
			tfNumb.setBounds(222, 4, 44, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}

	private JTextField getTfNump() {
		if (tfNump == null) {
			tfNump = new JTextField();
			tfNump.setEditable(false);
			tfNump.setBounds(343, 4, 44, 21);
			tfNump.setColumns(10);
		}
		return tfNump;
	}

	private JTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JTextField();
			tfBegin.setEditable(false);
			tfBegin.setBounds(441, 4, 83, 21);
			tfBegin.setColumns(10);
		}
		return tfBegin;
	}

	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setBounds(577, 4, 83, 21);
			tfEnd.setColumns(10);
		}
		return tfEnd;
	}

	private JScrollPane getScrollPaneH() {
		if (scrollPaneH == null) {
			scrollPaneH = new JScrollPane();
			scrollPaneH.setViewportView(getTableHand());
		}
		return scrollPaneH;
	}

	private JTable getTableHand() {
		if (tableHand == null) {
			tableHand = new JTable();
		}
		return tableHand;
	}

	private JScrollPane getScrollPaneP() {
		if (scrollPaneP == null) {
			scrollPaneP = new JScrollPane();
			scrollPaneP.setViewportView(getTablePrim());
		}
		return scrollPaneP;
	}

	private JTable getTablePrim() {
		if (tablePrim == null) {
			tablePrim = new MultiSpanCellTable();
		}
		return tablePrim;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("备案序号：");
		}
		return label;
	}
	private JTextField getTextField() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setColumns(10);
			tfSeqNum.setPreferredSize(new Dimension(80,25));
			tfSeqNum.setDocument(new PlainDocument(){
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					char c = str.charAt(str.length()-1);
					if(c >='0' && c <='9'){
						super.insertString(offs, str, a);
					}
				}
			});
		}
		return tfSeqNum;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getToolBar(), BorderLayout.SOUTH);
			panel.add(getJPanel2());
		}
		return panel;
	}
	
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			fillValue();
			initTable();
		}
		
		super.setVisible(aFlag);
	}
	
	/**
	 * 根据数据显示信息
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {
		this.tfSeqNum.setText(seqNum == null ? null : String.valueOf(seqNum));
		this.tabbedPane.setSelectedIndex(1);
		this.doFindStars();
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("<html><font color='blue'>【料号级】</font></html>");
			label_1.setVerticalAlignment(SwingConstants.TOP);
			label_1.setBounds(663, 5, 60, 21);
		}
		return label_1;
	}
}
