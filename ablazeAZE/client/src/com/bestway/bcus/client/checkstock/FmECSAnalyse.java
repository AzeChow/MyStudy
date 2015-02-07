package com.bestway.bcus.client.checkstock;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSAnalyse;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.checkstock.contractanalyse.FmECSContractAnalyse;
import com.bestway.bcus.client.checkstock.factoryanalyse.FmECSStockAnalyse;
import com.bestway.bcus.client.checkstock.transferanalyse.FmECSTransferCollectBalance;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

/**
 * @author xxm
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class FmECSAnalyse extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTableListModel tableModel = null;
	private JTableListModelAdapter adapterConvert;
	private MultiSpanCellTable tablePrim;
	private ECSCheckStockAction ecsCheckStockAction = null;
	private JPanel panel;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private MultiSpanCellTable table;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnNewButton;
	private JTextField tfEnd;
	private JTextField tfBegin;
	private JTextField tfNump;
	private JTextField tfNumb;
	private ECSSection ecsSection;
	private Request request;
	private JTextField tfSeqNum;
	private JLabel label;
	private JLabel lbComments;
	/**
	 * 关联列名称
	 */
	private List<TableColumn> linkedCols = new ArrayList<TableColumn>();
	 
	public ECSSection getEcsSection() {
		return ecsSection;
	}

	public void setEcsSection(ECSSection ecsSection) {
		this.ecsSection = ecsSection;
	}

	/**
	 * This is the default constructor
	 */
	public FmECSAnalyse() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkAnalyse(request);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1088, 566);
		this.setContentPane(getJContentPane());
		this.setTitle("短溢分析/盘点数据分析");
		initTable(Collections.EMPTY_LIST);
		initBtnStatus(0);
	}

	/**
	 * 
	 * @param list
	 */
	public void initTable(List list) {
		list = (list == null ? new ArrayList() : list);
		initTable(table, list);
	}

	/**
	 * 
	 * @param jTable
	 * @param list
	 * @return
	 */
	private JTableListModel initTable(MultiSpanCellTable jTable, final List list) {
//		final String[] linkedColNames = {"帐面结存(A)","实际库存(B)","结转差额(C)"};
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable)jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("备案序号", "seqNum", 50));
				list.add(addColumn("物料名称", "hsName", 100));
				list.add(addColumn("物料规格", "hsSpec", 120));
				list.add(addColumn("计量单位", "hsUnit", 60));

				list.add(addColumn("单价(P)", "price", 60));

//				list.add(addColumn(linkedColNames[0],"emsBalance", 100));
//				list.add(addColumn(linkedColNames[1], "stockBalance", 100));
//				list.add(addColumn(linkedColNames[2], "transferBalance", 100));

				list.add(addColumn("期初/进口数量", "importAmount", 100));
				list.add(addColumn("出口耗用量", "exgWasteAmount", 100));
				list.add(addColumn("账册结存(A)", "emsBalance", 100));
				list.add(addColumn("料件库存","stockAmountImg", 100));
				list.add(addColumn("在产品库存", "stockAmountProcessImg", 100));
				list.add(addColumn("成品库存", "stockAmountExg", 100));
				list.add(addColumn("厂外库存","stockAmountOutFactoryImg", 100));
				list.add(addColumn("内购库存", "stockAmountBuyImg", 100));
				list.add(addColumn("在途料件", "stockAmountTravelingImg", 100));
				list.add(addColumn("在途成品","stockAmountTravelingExg", 100));
				list.add(addColumn("外发库存汇总", "stockAmountOutSendExgPt", 100));
				list.add(addColumn("半成品库存(已入库)", "stockAmountSemiExgHStore", 100));
				list.add(addColumn("在制品库存汇总", "stockAmountFinishingExg", 100));
				list.add(addColumn("残次品库存汇总", "stockAmountBadImg", 100));
				list.add(addColumn("盘点总库存(B)", "stockBalance", 100));
				list.add(addColumn("已送货未转厂数", "unTransHadSendNum", 100));
				list.add(addColumn("已转厂未送货数", "unSendHadTransNum", 100));
				list.add(addColumn("已收货未转厂数", "unTransHadReceiveNum", 100));
				list.add(addColumn("已转厂未收货数", "unReceiveHadTransNum", 100));
				list.add(addColumn("结转差额(C)", "transferBalance", 100));
				
				list.add(addColumn("差异数(D)=B-A+C", "diffAmount", 120));
				list.add(addColumn("关税(G)=D*P*税率", "usd", 120));
				list.add(addColumn("增值税(Z)=(D*P+G)*17%", "usdAdd", 150));

				return list;
			}
		})
		{
			//设置第一列可编辑
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex == 5 ;
			}
		};
		
		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		ColumnGroup exgGroup = new ColumnGroup("【账册数据】");
		exgGroup.add(cm.getColumn(6));
		exgGroup.add(cm.getColumn(7));
		exgGroup.add(cm.getColumn(8));
		header.addColumnGroup(exgGroup);
		ColumnGroup factoryGroup = new ColumnGroup("【盘点数据】");
		factoryGroup.add(cm.getColumn(9));
		factoryGroup.add(cm.getColumn(10));
		factoryGroup.add(cm.getColumn(11));
		factoryGroup.add(cm.getColumn(12));
		factoryGroup.add(cm.getColumn(13));
		factoryGroup.add(cm.getColumn(14));
		factoryGroup.add(cm.getColumn(15));
		factoryGroup.add(cm.getColumn(16));
		factoryGroup.add(cm.getColumn(17));
		factoryGroup.add(cm.getColumn(18));
		factoryGroup.add(cm.getColumn(19));
		factoryGroup.add(cm.getColumn(20));
		header.addColumnGroup(factoryGroup);
		ColumnGroup transfer = new ColumnGroup("【结转数据】");
		transfer.add(cm.getColumn(21));
		transfer.add(cm.getColumn(22));
		transfer.add(cm.getColumn(23));
		transfer.add(cm.getColumn(24));
		header.addColumnGroup(transfer);
		
		
		tableModel.setAllowSetValue(true);	//设置可编辑
		jTable.getColumnModel().getColumn(5).setCellEditor(new NumberCellEditor()); 	//设置第一列编辑控件
		
		jTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘单价’列中的单元格可直接修改单价，回车后自动保存。";}
		});
		jTable.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘"+"账册结存(A)"+"’可关联【帐册分析】表";}
		});
		jTable.getColumnModel().getColumn(20).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘"+"盘点总库存(B)"+"’可关联【盘点数据分析】表";}
		});
		jTable.getColumnModel().getColumn(25).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {return "双击‘"+"结转差额(C)"+"’可关联【结转差额总表】";}
		});
		this.linkedCols.clear();
		this.linkedCols.add(jTable.getColumnModel().getColumn(8));
		this.linkedCols.add(jTable.getColumnModel().getColumn(20));
		this.linkedCols.add(jTable.getColumnModel().getColumn(25));
		return tableModel;
	}
	/**
	 * 更新价格
	 */
	private void updatePrice(){
		 ECSAnalyse a = (ECSAnalyse)tableModel.getCurrentRow();
		 a = ecsCheckStockAction.updateAnalysePrice(request,a);
		 tableModel.updateRow(a);
	}
	/**
	 * 列编辑器
	 * @author xc
	 *
	 */
	class NumberCellEditor extends DefaultCellEditor{
		public NumberCellEditor() {
			super(new JNumberTextField());
			final JNumberTextField textField = (JNumberTextField)editorComponent;
			this.clickCountToStart = 2;
	        delegate = new EditorDelegate() {
	            public void setValue(Object value) {
	            	textField.setValue(value);
	            }

			    public Object getCellEditorValue() {
			    	return textField.getValue();
			    }
	        };
	        ActionListener[] actions = textField.getActionListeners();
	        for(ActionListener action : actions)
	        	textField.removeActionListener(action);
	        textField.addActionListener(delegate);
	        textField.addKeyListener(new KeyAdapter() {	        	
	        	public void keyPressed(KeyEvent e) {	        		
	        		if(e.getKeyCode() == KeyEvent.VK_ENTER){
	        			stopCellEditing();	        			
	        		}
	        	}	        	
			});
	        addCellEditorListener(new CellEditorListener() {				
				public void editingStopped(ChangeEvent e) {										
					updatePrice();
				}				
				public void editingCanceled(ChangeEvent e) {}
			});
		}
	}
	
	class JNumberTextField extends JTextField{
		public JNumberTextField() {
			setDocument(new PlainDocument(){
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					String text = this.getText(0, getLength());
					char c = str.charAt(str.length()-1);				
					if(c >='0' && c <='9'){
						super.insertString(offs, str, a);
					}else if(c == '.' && text.length() > 0 && !text.contains(".")){						
						super.insertString(offs, str, a);					
					}
				}
			});
		}
		public Object getValue(){
			String value = getText().trim();
			return value.isEmpty() ? null : Double.valueOf(value);
		}
		public void setValue(Object value){
			setText(value == null ? "" : value.toString());
		}
	}
	
	
	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNumb.setText(ecsSection.getCancelOwnerHead().getCancelTimes());
		tfNump.setText(ecsSection.getVerificationNo().toString());
		tfBegin.setText(dFormat.format(ecsSection.getBeginDate()));
		tfEnd.setText(dFormat.format(ecsSection.getEndDate()));
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
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
			jContentPane.add(getPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			jLabel4.setBounds(385, 4, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(517, 4, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);

			JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
			lblNewLabel.setBounds(149, 7, 78, 15);
			jPanel2.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
			lblNewLabel_1.setBounds(265, 7, 78, 15);
			jPanel2.add(lblNewLabel_1);
			jPanel2.add(getBtnNewButton());
			jPanel2.add(getTfEnd());
			jPanel2.add(getTfBegin());
			jPanel2.add(getTfNump());
			jPanel2.add(getTfNumb());
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
			toolBar.add(getBtnCalculate());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClean());
			toolBar.add(getBtnClose());
			toolBar.add(getLbComments());
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
					if (ecsSection == null) {
						JOptionPane.showMessageDialog(FmECSAnalyse.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					// ecsCheckStockAction.findECSA
					FmECSAnalyse.this.doFindStars();
					initBtnStatus(2);
				}
			});
		}
		return btnHistroy;
	}

	private void doFindStars() {
		new AsynSwingWorker<List>() {
			protected List submit() {
				CommonProgress.showProgressDialog(FmECSAnalyse.this);
				CommonProgress.setMessage("系统正在查询数据，请稍后...");
				try{
					btnCalculate.setEnabled(false);				
					String seqNum = tfSeqNum.getText().trim();
					return ecsCheckStockAction.findECSAnalyses(request, ecsSection,seqNum.isEmpty() ? null : NumberUtils.toInt(seqNum));
				}finally{
					CommonProgress.closeProgressDialog();
					btnCalculate.setEnabled(true);	
				}
			}
			protected void success(List data) {
				initTable(data);				
			};

		}.doWork();
	}

	/**
	 * 计算
	 * 
	 * @return
	 */
	private JButton getBtnCalculate() {
		if (btnCalculate == null) {
			btnCalculate = new JButton("  \u8BA1\u7B97  ");
			btnCalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// ecsCheckStockAction
					FmECSAnalyse.this.doConvertStars();
				}
			});
		}
		return btnCalculate;
	}

	/**
	 * 
	 */
	private void doConvertStars() {
		new AsynSwingWorker<List>() {
			protected List submit() {
				CommonProgress.showProgressDialog(FmECSAnalyse.this);
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				try{
					btnCalculate.setEnabled(false);
					return ecsCheckStockAction.ecsAnalyse(request, ecsSection);
				}finally{
					CommonProgress.closeProgressDialog();
					btnCalculate.setEnabled(true);
				}
			}
			protected void success(List list) {
				initTable(list);
			};

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
					if (tableModel.getRowCount() > 0) {
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
					if (ecsSection == null) {
						JOptionPane.showMessageDialog(FmECSAnalyse.this, "批次号不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							ecsCheckStockAction.deleteECSAnalyses(request, ecsSection);
							initTable(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(FmECSAnalyse.this, "未能成功删除！", "警告",
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
			btnClose = new JButton("  \u5173\u95ED  ");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FmECSAnalyse.this.dispose();
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
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() ==2){
						int colIdx = table.columnAtPoint(e.getPoint());
						if(colIdx < 0)	return;
						int idx = CommonUtils.indexOf(linkedCols,table.getColumnModel().getColumn(colIdx));
						if(idx < 0 )	return;
						ECSAnalyse s = (ECSAnalyse)tableModel.getCurrentRow();
						ECSSection section = s.getSection();
						section.setEndDate(Date.valueOf(CommonUtils.getDate(section.getEndDate(),"yyyy-MM-dd")));
						if(idx == 0 ){
							FmECSContractAnalyse fm = new FmECSContractAnalyse();
							fm.showData(section,s.getSeqNum());
							ShowFormControl.refreshInteralForm(fm);
						}else if(idx == 1){
							FmECSStockAnalyse fm = new FmECSStockAnalyse();
							fm.showData(section,s.getSeqNum());
							ShowFormControl.refreshInteralForm(fm);
						}else if(idx == 2){
							FmECSTransferCollectBalance fm = new FmECSTransferCollectBalance();
							fm.showData(section,s.getSeqNum());
							ShowFormControl.refreshInteralForm(fm);
						}
					}
				}
			});
		}
		return table;
	}

	/**
	 * 
	 * @return
	 */
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("New button");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if (dgECSSection.isOk()) {
						ecsSection = dgECSSection.getSection();
						fillValue();
						initBtnStatus(1);
					}
				}
			});
			btnNewButton.setBounds(104, 3, 35, 23);
		}
		return btnNewButton;
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
			break;
		case 1:
			btnHistroy.setEnabled(true);
			btnCalculate.setEnabled(true);
			break;
		case 2:
			btnCalculate.setEnabled(true);
			btnClean.setEnabled(true);
			btnExport.setEnabled(true);
			btnHistroy.setEnabled(true);
			break;
		default:
			break;
		}
	}
	/**
	 * 
	 * @return
	 */
	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setBounds(570, 4, 78, 21);
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
			tfBegin.setBounds(435, 4, 78, 21);
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
			tfNump.setBounds(340, 4, 44, 21);
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
			tfNumb.setBounds(220, 4, 44, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}

	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setPreferredSize(new Dimension(80, 25));
			tfSeqNum.setColumns(10);
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
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("备案序号：");
		}
		return label;
	}
	private JLabel getLbComments() {
		if (lbComments == null) {
			lbComments = new JLabel("<html><body><font color='blue'>税率=该序号对应报关单商品编码的最优税率；<br/>单价单元格可直接双击修改；</font></body></html>");
		}
		return lbComments;
	}
	
	/**
	 * 按照条件查询显示数据
	 * @param section2
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {		
		this.ecsSection = section;
		fillValue();
		if(seqNum != null)
			getTfSeqNum().setText(String.valueOf(seqNum));
		doFindStars();
	}
	
}
