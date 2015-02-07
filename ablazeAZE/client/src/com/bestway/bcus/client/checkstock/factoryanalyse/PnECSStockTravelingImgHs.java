package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.SwingWorker;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingImg;
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
public class PnECSStockTravelingImgHs extends JPanel {

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
	private MultiSpanCellTable table;
	private JButton btnHistroy;
	//private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnImport;
	private JTextField tfEnd;
	private JTextField tfBegin;
	private JTextField tfNump;
	private JTextField tfNumb;
	private JButton btnChoose;
	private File file;
	private Request request;
	private JTableListModelAdapter adapterRawdata;
	private JLabel label;
	private JTextField tfSeqNum;
	private FmECSStockTravelingImg parent;
	private List<ECSStockTravelingImg> listData;
	private JLabel lblNewLabel_2;
  
	/**
	 * This is the default constructor
	 */
	public PnECSStockTravelingImgHs(FmECSStockTravelingImg parent) {
		super();
		this.parent = parent;
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkStockTravelingImg(request);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JTableListModel initTable( final List list) {
		adapterRawdata = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();

				list.add(addColumn("料件备案序号", "seqNum", 110));
				list.add(addColumn("报关商品名称", "hsName", 100));
				list.add(addColumn("报关商品规格", "hsSpec", 100));
				list.add(addColumn("计量单位", "hsUnit", 50));
				list.add(addColumn("报关数量", "hsAmount", 100));
				list.add(addColumn("仓库","warehouse",100));
				list.add(addColumn("备注","memo",200));
				//list.add(addColumn("折算系数", "unitConvert", 100));

				return list;
			}
		};
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) this.getTable(), list, adapterRawdata);
		TableColumnModel cm = this.getTable().getColumnModel();
		ColumnGroup group = new ColumnGroup("【报关资料】");
		group.add(cm.getColumn(1));
		group.add(cm.getColumn(2));
		group.add(cm.getColumn(3));
		group.add(cm.getColumn(4));
		group.add(cm.getColumn(5));
		group.add(cm.getColumn(6));
		group.add(cm.getColumn(7));

		GroupableTableHeader header = (GroupableTableHeader) this.getTable().getTableHeader();
		header.addColumnGroup(group);
		return tableModel;
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
			jLabel4.setBounds(392, 5, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(521, 5, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);

			JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
			lblNewLabel.setBounds(149, 7, 78, 15);
			jPanel2.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
			lblNewLabel_1.setBounds(269, 7, 78, 15);
			jPanel2.add(lblNewLabel_1);
			jPanel2.add(getTfEnd());
			jPanel2.add(getTfBegin());
			jPanel2.add(getTfNump());
			jPanel2.add(getTfNumb());
			jPanel2.add(getBtnChoose());
			jPanel2.add(getLblNewLabel_2());
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

//			toolBar.add(getBtnCalculate());

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
						JOptionPane.showMessageDialog(PnECSStockTravelingImgHs.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					PnECSStockTravelingImgHs.this.doFindStars();
					initBtnStatus(2);
				}
			});
		}
		return btnHistroy;
	}

	
	private void doFindStars() {
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());	
				CommonStepProgress.setStepMessage("系统正在获取文件数据，请稍后...");
				btnHistroy.setEnabled(false);
				String seqNum = tfSeqNum.getText().trim();
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.findECSStockTravelingImgs(request, parent.getSection(),(seqNum.isEmpty()?null:Integer.parseInt(seqNum)));
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();
				}
			}

			@Override
			protected void done() {
				try {
					initTable(this.get());
				} catch (Exception e2) {
					DgErrorMessage dgErrorMessage = new DgErrorMessage();
					String[] strs = e2.getMessage().split(",");
					List<String> list = new ArrayList<String>();
						for(String str:strs){
							list.add(str);
						}
					dgErrorMessage.initTable(list);
					dgErrorMessage.setVisible(true);
				}
			};

		}.execute();
	}
	/**
	 * 
	 * @return
	 */
//	private JButton getBtnCalculate() {
//		if (btnCalculate == null) {
//			btnCalculate = new JButton("\u6298\u7B97\u62A5\u5173\u6570\u91CF");
//			btnCalculate.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					PnECSStockTravelingImgHs.this.doConvertStars();
//				}
//			});
//		}
//		return btnCalculate;
//	}

	private void doConvertStars(){
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());	
				CommonStepProgress.setStepMessage("系统正在获取文件数据，请稍后...");
				try{
					initBtnStatus(0);
					return ecsCheckStockAction.convertECSStockTravelingImgs(request, parent.getSection());
				}finally{
					initBtnStatus(2);
					CommonStepProgress.closeStepProgressDialog();;
				}
			}
			
			@Override
			protected void done() {
				try {
					initTable(this.get());
				} catch (Exception e2) {
					DgErrorMessage dgErrorMessage = new DgErrorMessage();
					String[] strs = e2.getMessage().split(",");
					List<String> list = new ArrayList<String>();
						for(String str:strs){
							list.add(str);
						}
					dgErrorMessage.initTable(list);
					CommonStepProgress.closeStepProgressDialog();;
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
					tableModel = (JTableListModel) table.getModel();
					if ( tableModel != null && tableModel.getRowCount() > 0) {
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
						JOptionPane.showMessageDialog(PnECSStockTravelingImgHs.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							// ....
							ecsCheckStockAction.deleteECSStockTravelingImgs(request, parent.getSection());
							initTable(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(PnECSStockTravelingImgHs.this, "未能成功删除！", "警告",
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
			btnImport = new JButton("\u5BFC\u5165\u76D8\u70B9\u6570\u91CF");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getSection() == null) {
						JOptionPane.showMessageDialog(PnECSStockTravelingImgHs.this, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					try {
						initBtnStatus(0);
						if(ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getSection(), ECSStockTravelingImg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							ecsCheckStockAction.deleteECSStockTravelingImgs(request, parent.getSection());
							initTable(Collections.EMPTY_LIST);
						}
						listData = new ArrayList<ECSStockTravelingImg>();
						DgECSImportHs<ECSStockTravelingImg> dgECSImport = new DgECSImportHs<ECSStockTravelingImg>(true);
						dgECSImport.setSection(parent.getSection());
						dgECSImport.setClazz("PnECSStockTravelingImgHs");
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
			tfEnd.setBounds(572, 4, 78, 21);
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
			tfBegin.setBounds(441, 4, 78, 21);
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
			tfNump.setBounds(345, 4, 44, 21);
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
			tfNumb.setBounds(225, 4, 44, 21);
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
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if(dgECSSection.isOk()){
						parent.changePn(dgECSSection.getSection());
						//fillValue();
						initBtnStatus(1);
					}
				}
			});
			btnChoose.setBounds(105, 3, 38, 23);
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
//			btnCalculate.setEnabled(false);
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
//			btnCalculate.setEnabled(true);
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
	
	
	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNump.setText(parent.getSection().getVerificationNo().toString());
		tfNumb.setText(parent.getSection().getCancelOwnerHead().getCancelTimes());
		tfBegin.setText(dFormat.format(parent.getSection().getBeginDate()));
		tfEnd.setText(dFormat.format(parent.getSection().getEndDate()));
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
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("<html><font color='blue'>【编码级】</font></html>");
			lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel_2.setBounds(652, 5, 99, 21);
		}
		return lblNewLabel_2;
	}
}
