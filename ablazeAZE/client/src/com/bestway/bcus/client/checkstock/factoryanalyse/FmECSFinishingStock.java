package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSStockAnalyse;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
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
public class FmECSFinishingStock extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTableListModel tableModel = null;
	private ECSCheckStockAction ecsCheckStockAction = null;
	private JPanel panel;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnChoose;
	private JTextField tfEnd;
	private JTextField tfBegin;
	private JTextField tfNumb;
	private JTextField tfNump;
	private ECSSection ecsSection;
	private Request request;
	private JLabel label;
	private JTextField tfSeqNum;
	/*******连接列********/
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
	public FmECSFinishingStock() {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkFinishingStock(new Request(CommonVars.getCurrUser()));
		initialize();
		initBtnStatus(0);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(834, 566);
		this.setContentPane(getJContentPane());
		initTable(Collections.EMPTY_LIST);
	}

	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		this.setTitle("在制品库存汇总");
	}

	/**
	 * 
	 * @return
	 */
	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(table, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("备案序号", "seqNum", 50));
				list.add(addColumn("报关名称", "hsName", 150));
				list.add(addColumn("报关规格", "hsSpec", 150));
				list.add(addColumn("计量单位", "hsUnit", 50));
				list.add(addColumn("原材料(A)", "imgHsAmount", 80));
				list.add(addColumn("成品(B)", "exgHsAmount", 80));
				
				list.add(addColumn("库存汇总(A+B)", "hsAmount", 120));
				
				return list;
			}
		});
		return tableModel;
	}

	/**
	 * 
	 * @return
	 */
	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNumb.setText(ecsSection.getVerificationNo().toString());
		tfNump.setText(ecsSection.getCancelOwnerHead().getCancelTimes());
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
			jPanel2.setLayout(new FlowLayout(FlowLayout.LEADING, 2, 2));			
			jPanel2.setPreferredSize(new Dimension(1, 30));
			jLabel3.setText("\u76D8\u70B9\u6838\u67E5\u6279\u6B21\u9009\u62E9");
			jLabel3.setBounds(5, 5, 99, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(422, 4, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(587, 4, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getBtnChoose());
			
						JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
						lblNewLabel.setBounds(149, 7, 78, 15);
						jPanel2.add(lblNewLabel);
			jPanel2.add(getTfNump());
			
						JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
						lblNewLabel_1.setBounds(283, 7, 78, 15);
						jPanel2.add(lblNewLabel_1);
			jPanel2.add(getTfNumb());
			jPanel2.add(jLabel4, null);
			jPanel2.add(getTfBegin());
			jPanel2.add(jLabel5, null);
			jPanel2.add(getTfEnd());
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
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
			toolBar.add(getLabel());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnHistroy());
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
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u8BB0\u5F55");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (ecsSection == null) {
						JOptionPane.showMessageDialog(FmECSFinishingStock.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					FmECSFinishingStock.this.doFindStars();					
				}
			});
		}
		return btnHistroy;
	}

	private void doFindStars() {
		new AsynSwingWorker<List<?>>() {
		
			@Override
			protected List<?> submit() {
				try{
					CommonProgress.showProgressDialog(FmECSFinishingStock.this);
					CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
					initBtnStatus(0);
					String seqNum = tfSeqNum.getText().trim();
					return ecsCheckStockAction
							.findECSFinishingStockResolves(request,ecsSection,(seqNum.isEmpty() ? null : Integer.parseInt(seqNum)));
				}finally{
					CommonProgress.closeProgressDialog();
					initBtnStatus(2);
				}
			}

			@Override
			protected void success(List<?> result) {
					initTable(result);
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
					FmECSFinishingStock.this.doConvertStars();
				}
			});
		}
		return btnCalculate;
	}
   //计算汇总表
	private void doConvertStars() {
		new AsynSwingWorker<List>() {			
			protected List submit()  {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送库存汇总请求，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.convertECSFinishingStockResolves(request, ecsSection);
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();
				}
			}
			protected void success(List data) {
				initTable(data);				
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
					if (tableModel != null && tableModel.getRowCount() > 0) {
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
						JOptionPane.showMessageDialog(FmECSFinishingStock.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							// ....
							ecsCheckStockAction.deleteECSBadStockResolve(request, ecsSection);
							initTable(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(FmECSFinishingStock.this, "未能成功删除！", "警告",
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
			btnClose = new JButton("  \u5173\u95ED  ");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FmECSFinishingStock.this.dispose();
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
			table = new JTable();
//			table.addMouseListener(new MouseAdapter() {
//				/**
//				 * 双击穿透报表
//				 */
//				public void mouseClicked(MouseEvent e) {					
//					if(e.getClickCount() ==2){
//						int colIdx = table.columnAtPoint(e.getPoint());
//						if(colIdx < 0)	return;
//						int idx = CommonUtils.indexOf(linkedCols, table.getColumnModel().getColumn(colIdx));						 
//						if(idx < 0 )	return;
//						ECSStockAnalyse  aly = (ECSStockAnalyse)tableModel.getCurrentRow();
//						switch(idx){
//							case 0:	//料件
//								FmECSStockImg fm0 = new FmECSStockImg();
//								fm0.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm0);
//								break;
//							case 1://在成品
//								FmECSStockProcessImg fm1 = new FmECSStockProcessImg();
//								fm1.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm1);
//								break;
//							case 2:	//成品
//								FmECSStockExg fm2 = new FmECSStockExg();
//								fm2.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm2);
//								break;
//							case 3:	//外发
//								FmECSStockOutSendExg fm3 = new FmECSStockOutSendExg();
//								fm3.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm3);
//								break;
//							case 4:	//厂外
//								FmECSStockOutFactoryImg fm4 = new FmECSStockOutFactoryImg();
//								fm4.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm4);
//								break;
//							case 5:	//内购
//								FmECSStockBuyImg fm5 = new FmECSStockBuyImg();
//								fm5.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm5);
//								break;
//							case 6:	//在途料件
//								FmECSStockTravelingImg fm6 = new FmECSStockTravelingImg();
//								fm6.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm6);
//								break;
//							case 7:	//在途成品
//								FmECSStockTravelingExg fm7 = new FmECSStockTravelingExg();
//								fm7.showData(aly.getSection(),aly.getSeqNum());
//								ShowFormControl.refreshInteralForm(fm7);
//								break;
//						}
//					}
//				}
//			});
		}
		return table;
	}

	/**
	 * 
	 * @return
	 */
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton("...");
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if(dgECSSection.isOk()){
						ecsSection = dgECSSection.getSection();
						fillValue();
						initBtnStatus(1);
					}
				}
			});
			btnChoose.setPreferredSize(new Dimension(29, 23));
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
			tfEnd.setBounds(647, 4, 85, 21);
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
			tfBegin.setBounds(484, 4, 85, 21);
			tfBegin.setColumns(10);
		}
		return tfBegin;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setEditable(false);
			tfNumb.setBounds(360, 4, 45, 21);
			tfNumb.setColumns(5);
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
			tfNump.setBounds(228, 4, 45, 21);
			tfNump.setColumns(5);
		}
		return tfNump;
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
