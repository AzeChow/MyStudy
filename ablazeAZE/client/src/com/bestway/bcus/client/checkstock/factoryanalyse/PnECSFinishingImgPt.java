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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSBadImg;
import com.bestway.bcus.checkstock.entity.ECSFinishingImg;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSStockImg;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;


public class PnECSFinishingImgPt extends JPanel {
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
	private JPanel panel;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	// private JTable table;
	private MultiSpanCellTable table;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnImport;
	private JTextField tfEnd;
	private JTextField tfBegin;
	private JTextField tfNump;
	private JTextField tfNumb;
	private JButton btnChoose;
	private Request request;
	private JTableListModelAdapter adapterRawdata;
	private JLabel label;
	private JTextField tfSeqNum;
	private List<ECSFinishingImg> listData;
	private FmECSFinishingImg parent;
	private JLabel label_1;
	
	/**
	 * This is the default constructor
	 */
	public PnECSFinishingImgPt(FmECSFinishingImg parent) {
		super();
		this.parent = parent;
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkFinishingImg(new Request(CommonVars.getCurrUser()));
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		this.add(getJPanel2(), BorderLayout.NORTH);
		this.add(getPanel(), BorderLayout.CENTER);
		initTable(Collections.EMPTY_LIST);
		initBtnStatus(0);
		initBtnStatus(1);
	}
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private JTableListModel initTable(List list) {
		adapterRawdata = new JTableListModelAdapter() {
			@SuppressWarnings("unchecked")
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("料号", "ptNo", 110));
				list.add(addColumn("工厂名称", "ptName", 100));
				list.add(addColumn("工厂规格", "ptSpec", 120));
				list.add(addColumn("计量单位", "ptUnit", 50));
				list.add(addColumn("库存数量", "ptAmount", 100));

				list.add(addColumn("料件备案序号", "seqNum", 80));
				list.add(addColumn("报关商品名称", "hsName", 100));
				list.add(addColumn("报关商品规格", "hsSpec", 120));
				list.add(addColumn("计量单位", "hsUnit", 50));
				list.add(addColumn("折算报关数量", "hsAmount", 100));
				list.add(addColumn("折算系数", "unitConvert", 60));
				list.add(addColumn("仓库","warehouse",100));
				list.add(addColumn("备注","memo",200));

				return list;
			}
		};

		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) table, list, adapterRawdata);

		TableColumnModel cm = getTable().getColumnModel();
		ColumnGroup exgGroup = new ColumnGroup("【工厂资料】");
		exgGroup.add(cm.getColumn(1));
		exgGroup.add(cm.getColumn(2));
		exgGroup.add(cm.getColumn(3));
		exgGroup.add(cm.getColumn(4));
		exgGroup.add(cm.getColumn(5));
		ColumnGroup hsImpGroup = new ColumnGroup("【报关资料】");
		hsImpGroup.add(cm.getColumn(6));
		hsImpGroup.add(cm.getColumn(7));
		hsImpGroup.add(cm.getColumn(8));
		hsImpGroup.add(cm.getColumn(9));
		hsImpGroup.add(cm.getColumn(10));
		hsImpGroup.add(cm.getColumn(11));
		hsImpGroup.add(cm.getColumn(12));
		hsImpGroup.add(cm.getColumn(13));

		GroupableTableHeader header = (GroupableTableHeader) getTable().getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(hsImpGroup);
		return tableModel;
	}

	/**
	 * 
	 * @return
	 */
	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNump.setText(parent.getSection().getVerificationNo().toString());
		tfNumb.setText(parent.getSection().getCancelOwnerHead().getCancelTimes());
		tfBegin.setText(dFormat.format(parent.getSection().getBeginDate()));
		tfEnd.setText(dFormat.format(parent.getSection().getEndDate()));

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
			jLabel3.setText("盘点核查批次选择");
			jLabel3.setBounds(5, 5, 99, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(394, 5, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(525, 5, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);

			JLabel lblNewLabel = new JLabel("核销报核次数");
			lblNewLabel.setBounds(149, 7, 78, 15);
			jPanel2.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("盘点核查次数");
			lblNewLabel_1.setBounds(267, 7, 78, 15);
			jPanel2.add(lblNewLabel_1);
			jPanel2.add(getTfEnd());
			jPanel2.add(getTfBegin());
			jPanel2.add(getTfNump());
			jPanel2.add(getTfNumb());
			jPanel2.add(getBtnChoose());
			jPanel2.add(getLabel_1());
		}
		return jPanel2;
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
	 * 
	 * @return
	 */
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			
			panel.add(getToolBar(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}

	/**
	 * 
	 * @return
	 */
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			toolBar.add(getLabel());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnHistroy());
			toolBar.add(getBtnImport());

			toolBar.add(getBtnCalculate());

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
			btnHistroy = new JButton("查看历史记录");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSFinishingImgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					PnECSFinishingImgPt.this.doFindStars();					
				}
			});
		}
		return btnHistroy;
	}

	private void doFindStars() {
		new AsynSwingWorker<List>() {
			protected List submit() {
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				String seqNum = tfSeqNum.getText().trim();
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.findECSFinishingImgs(request, 
							parent.getSection(),(seqNum.isEmpty() ? null:Integer.parseInt(seqNum)));
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();
				}
			}
			protected void success(List result) {
				initTable(result);
			}
		}.doWork();
	}
	/**
	 * 
	 * @return
	 */
	private JButton getBtnCalculate() {
		if (btnCalculate == null) {
			btnCalculate = new JButton(" \u6298\u7B97\u62A5\u5173\u6570\u91CF ");
			btnCalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane
								.showMessageDialog(PnECSFinishingImgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					PnECSFinishingImgPt.this.doConvertStars();
				}
			});
		}
		return btnCalculate;
	}

	
	private void doConvertStars(){
		new AsynSwingWorker<List>() {
			protected List submit(){
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.convertECSFinishingImg(request, parent.getSection());
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();
				}
			}
			
			protected void success(List result) {
				initTable(result);
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
					tableModel = (JTableListModel) table.getModel();
					if ( tableModel != null && tableModel.getRowCount() > 0 ) {
						tableModel.getMiSaveTableListToExcel().doClick();
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
								.showMessageDialog(PnECSFinishingImgPt.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							// ....
							ecsCheckStockAction.deleteECSFinishingImg(request, parent.getSection());
							initTable(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(PnECSFinishingImgPt.this, "未能成功删除！", "警告",
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
			btnClose = new JButton(" 关闭  ");
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
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	/**
	 * 
	 * @return
	 */
	private JTable getTable() {
		if (table == null) {
			table = new MultiSpanCellTable();
		}
		return table;
	}

	/**
	 * 
	 * @return
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton("导入盘点数量");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSFinishingImgPt.this,
								"批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					try {
						initBtnStatus(0);
						if(ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getSection(), ECSFinishingImg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							ecsCheckStockAction.deleteECSFinishingImg(request, parent.getSection());
							initTable(Collections.EMPTY_LIST);
						}
						
						listData = new ArrayList<ECSFinishingImg>();
						DgECSImportPt<ECSFinishingImg> dgECSImport = new DgECSImportPt<ECSFinishingImg>(true);
						dgECSImport.setClazz("PnECSFinishingImgPt");
						dgECSImport.setSection(parent.getSection());
						dgECSImport.setListData(listData);
						dgECSImport.setVisible(true);
						initTable(listData);
					}finally{
						initBtnStatus(2);
					}
				}
			});
		}
		return btnImport;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setBounds(580, 4, 78, 21);
			tfEnd.setColumns(10);
		}
		return tfEnd;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JTextField();
			tfBegin.setEditable(false);
			tfBegin.setBounds(445, 4, 78, 21);
			tfBegin.setColumns(10);
		}
		return tfBegin;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfNump() {
		if (tfNump == null) {
			tfNump = new JTextField();
			tfNump.setEditable(false);
			tfNump.setBounds(340, 4, 52, 21);
			tfNump.setColumns(10);
		}
		return tfNump;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setEditable(false);
			tfNumb.setBounds(224, 4, 42, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}

	/**
	 * 
	 * @return
	 */
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton("New button");
			btnChoose.setBounds(110, 3, 29, 23);
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if(dgECSSection.isOk()){
						parent.changePn(dgECSSection.getSection());;
						fillValue();
						initBtnStatus(1);
					}
				}
			});

		}
		return btnChoose;
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
			break;
		default:
			break;
		}
	}
	
	
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("备案序号：");
		}
		return label;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setColumns(10);
			tfSeqNum.setPreferredSize(new Dimension(80,25));
			tfSeqNum.setDocument(new PlainDocument(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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
		this.doFindStars();
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("<html><font color='blue'>【料号级】</font></html>");
			label_1.setVerticalAlignment(SwingConstants.TOP);
			label_1.setBounds(656, 5, 60, 21);
		}
		return label_1;
	}
}
