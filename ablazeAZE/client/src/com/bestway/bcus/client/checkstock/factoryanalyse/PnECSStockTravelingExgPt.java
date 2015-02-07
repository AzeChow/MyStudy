package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
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
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExg;
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
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;
import javax.swing.SwingConstants;

public class PnECSStockTravelingExgPt extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
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
	public JTabbedPane tabbedPane;
	private JPanel panelP;
	private JPanel panelH;
	private JTableListModelAdapter adapterConvert;
	private JTableListModel tableModelP;
	private JTableListModelAdapter adapterRawdata;
	private JTableListModel tableModelH;
	private JTextField tfSeqNum;
	private JPanel panel;
	private List<ECSStockTravelingExg> listData;
	private FmECSStockTravelingExg parent = null;
	private JLabel label_1;

	/**
	 * This is the default constructor
	 */
	public PnECSStockTravelingExgPt(final FmECSStockTravelingExg parent) {
		super();
		this.parent = parent;
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkStockExg(request);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		this.add(getPanel(), BorderLayout.NORTH);
		this.add(getTabbedPane(), BorderLayout.CENTER);
		initTable(Collections.EMPTY_LIST);
		initBtnStatus(0);
		initBtnStatus(1);
	}

	/**
	 * 
	 * @return
	 */
	public void initTable(List list) {
		initJtableP(new ArrayList<Object>());
		initJtableH(new ArrayList<Object>());
	}

	/**
	 * 
	 * @return
	 */
	void initJtableH(List list) {
		if (adapterRawdata == null) {
			adapterRawdata = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("成品料号", "baseExg.ptNo", 110));
					list.add(addColumn("成品序号", "baseExg.seqNum", 50));
					list.add(addColumn("成品版本", "baseExg.version", 50));
					list.add(addColumn("成品数量", "baseExg.ptAmount", 50));
					list.add(addColumn("料件序号", "seqNum", 50));
					list.add(addColumn("料件名称", "hsName", 100));
					list.add(addColumn("型号规格", "hsSpec", 160));
					list.add(addColumn("计量单位", "hsUnit", 60));
					list.add(addColumn("单耗", "unitWaste", 60));
					list.add(addColumn("损耗", "waste", 60));
					list.add(addColumn("单项用量", "unitUsed", 60));
					list.add(addColumn("总耗用", "hsAmount", 100));
					return list;
				}
			};
		}
		tableModelH = new JTableListModel(tableHand, list, adapterRawdata);
	}

	/**
	 * 
	 * @return
	 */
	JTableListModel initJtableP(List list) {
		if (adapterConvert == null) {
			adapterConvert = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("料号", "ptNo", 110));
					list.add(addColumn("工厂名称", "ptName", 120));
					list.add(addColumn("工厂规格", "ptSpec", 120));
					list.add(addColumn("计量单位", "ptUnit", 100));
					//list.add(addColumn("工单号", "ptName", 50));
					list.add(addColumn("版本号", "version", 50));
					list.add(addColumn("盘点库存", "ptAmount", 100));

					list.add(addColumn("成品备案序号", "seqNum", 100));
					list.add(addColumn("版本号", "version", 50));
					list.add(addColumn("报关商品名称", "hsName", 120));
					list.add(addColumn("报关商品规格", "hsSpec", 120));
					list.add(addColumn("计量单位", "hsUnit", 50));
					list.add(addColumn("折算报关数量", "hsAmount", 100));
					list.add(addColumn("折算系数", "unitConvert", 100));
					list.add(addColumn("仓库","warehouse",100));
					list.add(addColumn("备注","memo",200));
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
		hsImpGroup.add(cm.getColumn(14));
		hsImpGroup.add(cm.getColumn(15));

		GroupableTableHeader header = (GroupableTableHeader) tablePrim.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(hsImpGroup);
		return tableModelP;
	}

	/**
	 * 
	 * @return
	 */
	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNump.setText(parent.getSection().getVerificationNo().toString());
		System.out.println(tfNump.getText()+"------------------------");
		tfNumb.setText(parent.getSection().getCancelOwnerHead().getCancelTimes());
		tfBegin.setText(dFormat.format(parent.getSection().getBeginDate()));
		tfEnd.setText(dFormat.format(parent.getSection().getEndDate()));
	}


	/**
	 * 
	 * @return
	 */
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
						btnCalculate.setEnabled(false);
						btnImport.setEnabled(false);
						// btnSwitch.setEnabled(true);
					} else if (index == 0) {
						btnCalculate.setEnabled(true);
						btnImport.setEnabled(true);
						// btnSwitch.setEnabled(false);
					}
				}
			});
		}
		return tabbedPane;
	}

	/**
	 * 
	 * @return
	 */
	private JPanel getPnRawdata() {
		if (panelP == null) {
			panelP = new JPanel();
			panelP.setLayout(new BorderLayout(0, 0));
			panelP.add(getScrollPaneP(), BorderLayout.CENTER);
		}
		return panelP;
	}

	/**
	 * 
	 * @return
	 */
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
			jLabel4.setBounds(422, 5, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(563, 5, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);

			JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
			lblNewLabel.setBounds(149, 7, 78, 15);
			jPanel2.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
			lblNewLabel_1.setBounds(283, 7, 78, 15);
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
			btnHistroy.setEnabled(true);
			btnImport.setEnabled(true);
			btnCalculate.setEnabled(true);
			btnClean.setEnabled(true);
			btnExport.setEnabled(true);
			btnSwitch.setEnabled(true);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @return
	 */
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			
			JLabel label = new JLabel("备案序号：");
			toolBar.add(label);			
			tfSeqNum = new JTextField();
			tfSeqNum.setPreferredSize(new Dimension(80,25));
			tfSeqNum.setDocument(new PlainDocument(){
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					char c = str.charAt(str.length()-1);
					if(c >='0' && c <='9'){
						super.insertString(offs, str, a);
					}
				}
			});
			toolBar.add(tfSeqNum);
			tfSeqNum.setColumns(10);
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

	/**
	 * 
	 * @return
	 */
	private JButton getBtnHistroy() {
		if (btnHistroy == null) {
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u8BB0\u5F55");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane
								.showMessageDialog(PnECSStockTravelingExgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					PnECSStockTravelingExgPt.this.doFindStars();
					
					initBtnStatus(2);
				}
			});
		}
		return btnHistroy;
	}

	private void doFindStars() {
		final String seqNum = tfSeqNum.getText().trim();		
		new AsynSwingWorker<List<?>>() {
			int tableIndex = tabbedPane.getSelectedIndex();
			protected List<?> submit() {
				CommonProgress.showProgressDialog();	
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				try{
					initBtnStatus(0);
					if(tableIndex == 0)
						return ecsCheckStockAction.findECSStockTravelingExgs(request, parent.getSection(),(seqNum.isEmpty() ? null :Integer.parseInt(seqNum)));
					return  ecsCheckStockAction.findECSStockTravelingExgResolves(request, parent.getSection(),(seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(List<?> data) {			
				if(tableIndex == 0)
					initJtableP(data);
				else
					initJtableH(data);			
			}

		}.doWork();
		
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

	/**
	 * 
	 * @return
	 */
	private JButton getBtnClean() {
		if (btnClean == null) {
			btnClean = new JButton("  \u6E05\u7A7A\u5F53\u524D\u6570\u636E  ");
			btnClean.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane
								.showMessageDialog(PnECSStockTravelingExgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							// ....
							ecsCheckStockAction.deleteECSStockTravelingExgs(request, parent.getSection());
							initJtableP(Collections.EMPTY_LIST);
							initJtableH(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(PnECSStockTravelingExgPt.this, "未能成功删除！", "警告",
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

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton("1.\u5BFC\u5165\u76D8\u70B9\u6570\u91CF");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane
								.showMessageDialog(PnECSStockTravelingExgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					try {
						initBtnStatus(0);
						if(ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getSection(), ECSStockTravelingExg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							ecsCheckStockAction.deleteECSStockTravelingExgs(request, parent.getSection());
							initJtableH(Collections.EMPTY_LIST);
							initJtableP(Collections.EMPTY_LIST);
						}
						tabbedPane.setSelectedIndex(0);
						
						listData = new ArrayList<ECSStockTravelingExg>();
						DgECSImportPt<ECSStockTravelingExg> dgECSImport = new DgECSImportPt<ECSStockTravelingExg>(false);
						dgECSImport.setSection(parent.getSection());
						dgECSImport.setClazz("PnECSStockTravelingExgPt");
						dgECSImport.setListData(listData);
						dgECSImport.setVisible(true);
						initJtableP(listData); 
					} finally {
						initBtnStatus(2);
					}
				}
			});
		}
		return btnImport;
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
						JOptionPane
								.showMessageDialog(PnECSStockTravelingExgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					tabbedPane.setSelectedIndex(0);
					PnECSStockTravelingExgPt.this.doConvertHsExg();					
				}
			});
		}
		return btnCalculate;
	}

	private void doConvertHsExg() {
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.convertECSStockTravelingExgs(request, parent.getSection());
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
						JOptionPane
								.showMessageDialog(PnECSStockTravelingExgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}					
					tabbedPane.setSelectedIndex(1);
					PnECSStockTravelingExgPt.this.doConvertHsExgTo();
				}
			});
		}
		return btnSwitch;
	}

	private void doConvertHsExgTo() {
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.resolveECSStockTravelingExgResolves(request, parent.getSection());
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
//					String[] strs = e2.getMessage().split(",");
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

	/**
	 * 
	 * @return
	 */
	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setForeground(Color.BLACK);
			tfNumb.setFont(new Font("SimSun", Font.PLAIN, 12));
			tfNumb.setEditable(false);
			tfNumb.setBounds(229, 4, 44, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfNump() {
		if (tfNump == null) {
			tfNump = new JTextField();
			tfNump.setEditable(false);
			tfNump.setBounds(368, 4, 44, 21);
			tfNump.setColumns(10);
		}
		return tfNump;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JTextField();
			tfBegin.setEditable(false);
			tfBegin.setBounds(475, 4, 78, 21);
			tfBegin.setColumns(10);
		}
		return tfBegin;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setBounds(621, 4, 78, 21);
			tfEnd.setColumns(10);
		}
		return tfEnd;
	}

	/**
	 * 
	 * @return
	 */
	private JScrollPane getScrollPaneH() {
		if (scrollPaneH == null) {
			scrollPaneH = new JScrollPane();
			scrollPaneH.setViewportView(getTableHand());
		}
		return scrollPaneH;
	}

	/**
	 * 
	 * @return
	 */
	private JTable getTableHand() {
		if (tableHand == null) {
			tableHand = new JTable();
		}
		return tableHand;
	}

	/**
	 * 
	 * @return
	 */
	private JScrollPane getScrollPaneP() {
		if (scrollPaneP == null) {
			scrollPaneP = new JScrollPane();
			scrollPaneP.setViewportView(getTablePrim());
		}
		return scrollPaneP;
	}

	/**
	 * 
	 * @return
	 */
	private JTable getTablePrim() {
		if (tablePrim == null) {
			tablePrim = new MultiSpanCellTable();
		}
		return tablePrim;
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
			initTable(Collections.EMPTY_LIST);
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
			label_1.setBounds(699, 5, 60, 21);
		}
		return label_1;
	}
}
