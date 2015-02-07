
package com.bestway.bcus.client.checkstock.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSContractAnalyse;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.temp.ECSContractAnalyseTemp;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
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
public class FmECSContractAnalyse extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JButton btnCount = null;
	private JButton btnExport = null;
	private JPanel jPanel7 = null;
	private JLabel jLabel6 = null;
	private JFooterTable tbBgImg = null;
	private JFooterScrollPane jScrollPane = null;
	private JButton btnClose = null;
	private JTableListModel tableModel = null;
	
	private EmsAnalyHead head = null;	
	private ECSCheckStockAction ecsCheckStockAction =null;
	private JToolBar toolBar;
	private JButton btnHistroy;
	private ECSSection section =null;  //  @jve:decl-index=0:
	private JButton btnClear;
	private JPanel panel;
	private JLabel label;
	private JButton btnChoose;
	private JLabel label_1;
	private JTextField tfCanTime;
	private JLabel label_2;
	private JTextField tfSection;
	private JLabel label_3;
	private JTextField tfBeginDate;
	private JLabel label_4;
	private JTextField tfEndDate;
	private JLabel label_5;
	private JTextField tfSeqNum;
	private static List<TableColumn> linkedCols = new ArrayList<TableColumn>();
	/**
	 * This is the default constructor
	 */
	public FmECSContractAnalyse(){
		super();		
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkContractAnalyse(new Request(CommonVars.getCurrUser()));
		initialize();
	}


	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(902, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("账册分析");
		initTable(Collections.EMPTY_LIST);
	}

	
	private void initTable(final List<?> list) {
		tableModel = new JTableListModel(tbBgImg, list,
			new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> list = new ArrayList<JTableListColumn>();
					list.add(addColumn("账册编号", "emsNo", 100));
					list.add(addColumn("备案序号", "seqNum", 100));
					list.add(addColumn("料件名称", "commName", 150));
					list.add(addColumn("型号规格", "commSpec", 150));
					list.add(addColumn("计量单位", "unit", 80));
					list.add(addColumn("进口数量(A)", "importAmount", 100));
					list.add(addColumn("成品耗用(B)", "wasteAmount", 100));
					list.add(addColumn("结余(A-B)", "resultAmount", 100));
					return list;
				}
		});
		linkedCols.clear();
		linkedCols.add(tbBgImg.getColumnModel().getColumn(6));
		linkedCols.add(tbBgImg.getColumnModel().getColumn(7));
		tbBgImg.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {
				return "双击‘进口数量(A)’可关联【料件情况统计表】";
			}
		});
		tbBgImg.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer(){
			public String getToolTipText() {
				return "双击‘成品耗用(B)’可关联【成品情况统计表】";
			}
		});		
	}


	private void setState(boolean enabled) {
		this.btnHistroy.setEnabled(enabled);
		this.btnCount.setEnabled(enabled);
		this.btnExport.setEnabled(enabled);
		this.btnClear.setEnabled(enabled);
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
			jContentPane.add(getJPanel7(), BorderLayout.CENTER);
			jContentPane.add(getPanel(), BorderLayout.NORTH);
		}
		return jContentPane;
	}
	
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCount() {
		if (btnCount == null) {
			btnCount = new JButton();
			btnCount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (section ==null){
						JOptionPane.showMessageDialog(FmECSContractAnalyse.this,
								"请选择一个盘点核销批次！", "提示", 2);
						return;
					}
					boolean isTrue = ecsCheckStockAction.isExistECSContractAnalyseBySection(new Request(
							CommonVars.getCurrUser()), section);					
					
					if (isTrue
							&& JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(FmECSContractAnalyse.this, 
									"已经存在该批次的账册分析，确定要重新统计吗","确认",JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")) {
						return;
					}
					
					new Jisuan().doWork();
				}
			});
			btnCount.setText("统计");
 	}
		return btnCount;
	}
	
	class Jisuan extends AsynSwingWorker<List> {
		@Override
		protected List submit() {
			try{
				setState(false);
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());
				CommonStepProgress.setStepMessage("系统正在发送账册分析请求，请稍后...");
				ecsCheckStockAction.delectECSContractAnalyseBySection(request,section);
				ecsCheckStockAction.gainEmsHeadH2kAnalyse(request, section);
				return ecsCheckStockAction.findECSContractAnalyseBySection(request, section,null);
			}finally{
				setState(true);
				CommonStepProgress.closeStepProgressDialog();
			}			
		}
		protected void success(List result) {
			if (result==null||result.size() == 0) {
				JOptionPane.showMessageDialog(FmECSContractAnalyse.this,
						"统计数据为空！", "提示", 2);
			}
			initTable(result);
		}		
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton();
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tableModel.getMiSaveAllToExcel().doClick();
				}
			});
			btnExport.setText("导出EXCEL");
		}
		return btnExport;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getToolBar(), BorderLayout.NORTH);
			jPanel7.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes tbBgImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getTbBgImg() {
		if (tbBgImg == null) {
			tbBgImg = new JFooterTable();
			tbBgImg.addMouseListener(new MouseAdapter() {
				/**
				 * 双击穿透报表
				 */
				public void mouseClicked(MouseEvent e) {					
					if(e.getClickCount() ==2){
						int colIdx = tbBgImg.columnAtPoint(e.getPoint());
						if(colIdx < 0)	return;
						int idx = CommonUtils.indexOf(linkedCols, tbBgImg.getColumnModel().getColumn(colIdx));						 
						if(idx < 0 )	return;
						ECSContractAnalyseTemp  aly = (ECSContractAnalyseTemp)tableModel.getCurrentRow();
						if(idx == 0 ){		
							FmECSCustomsCountImg fm = new FmECSCustomsCountImg();
							fm.showData(section, aly.getSeqNum());
							ShowFormControl.refreshInteralForm(fm);
						}else if (idx == 1){							
							FmECSDeclarationCommInfoExg fm = new FmECSDeclarationCommInfoExg();
							fm.showData(section, aly.getSeqNum());
							ShowFormControl.refreshInteralForm(fm);
						}
						
					}
				}
			});
		}
		return tbBgImg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getTbBgImg());
		}
		return jScrollPane;
	}
	

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmECSContractAnalyse.this.dispose();
				}
			});
		}
		return btnClose;
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
			toolBar.add(getLabel_5());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnHistroy());
			toolBar.add(getBtnCount());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClear());
			toolBar.add(getBtnClose());
		}
		return toolBar;
	}
	private JButton getBtnHistroy() {
		if (btnHistroy == null) {
			btnHistroy = new JButton("查看历史记录");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (section ==null){
						JOptionPane.showMessageDialog(FmECSContractAnalyse.this,
								"请选择一个盘点核销批次！", "提示", 2);
						return;
					}
					new AsynSwingWorker<List<ECSContractAnalyse>>() {
						Request request = new Request(CommonVars.getCurrUser());
						protected List<ECSContractAnalyse> submit() {	
							CommonProgress.showProgressDialog(FmECSContractAnalyse.this);
							CommonProgress.setMessage("系统正在查询数据，请稍后...");
							setState(false);
							Integer seqNum = null;
							if(StringUtils.isNotBlank(tfSeqNum.getText().trim()))
								seqNum = NumberUtils.toInt(tfSeqNum.getText().trim());								
							return ecsCheckStockAction.findECSContractAnalyseBySection(request, section,seqNum);							
						}						
						protected void success(List<ECSContractAnalyse> list) {
							CommonProgress.closeProgressDialog();
							setState(true);
							tableModel.setList(list);
						};						
					}.doWork();
				}
			});
		}
		return btnHistroy;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("清空当前数据");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(section == null){
						JOptionPane.showMessageDialog(getContentPane(),"请选择盘点核查批次！","提示",JOptionPane.INFORMATION_MESSAGE);						
						return;
					}
					if(JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(FmECSContractAnalyse.this,
							"确定删除本批次数据","提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
						new AsynSwingWorker<Object>() {
							Request request = new Request(CommonVars.getCurrUser());
							protected Object submit() {	
								ecsCheckStockAction.delectECSContractAnalyseBySection(request, section);
								 return null;
							}						
							protected void success(Object o) {
								tableModel.setList(Collections.EMPTY_LIST);
							};						
						}.doWork();
					}
				
				}
			});
		}
		return btnClear;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(1,30));	
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
			panel.add(getLabel());
			panel.add(getBtnChoose());
			panel.add(getLabel_1());
			panel.add(getTfCanTime());
			panel.add(getLabel_2());
			panel.add(getTfSection());
			panel.add(getLabel_3());
			panel.add(getTfBeginDate());
			panel.add(getLabel_4());
			panel.add(getTfEndDate());
		}
		return panel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("盘点核查批次选择：");
		}
		return label;
	}
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton("...");
			btnChoose.setPreferredSize(new Dimension(29, 23));
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DgECSSectionSel dg = new DgECSSectionSel();
			 		dg.setVisible(true);
			 		if(dg.isOk()){   // 选中批次后确定
			 			section = dg.getSection();// 拿到批次
			 			fillValues(section);
			 		}
				}
			});
		}
		return btnChoose;
	}
	/**
	 * 填充批次数据
	 * @param section
	 */
	private void fillValues(ECSSection section){
		if(section!=null){			 				
			tfCanTime.setText(section.getCancelOwnerHead().getCancelTimes());
			tfSection.setText(section.getVerificationNo()+"");
			tfBeginDate.setText(CommonUtils.getDate(section.getBeginDate(),"yyyy-MM-dd"));
			tfEndDate.setText(CommonUtils.getDate(section.getEndDate(),"yyyy-MM-dd"));
		}
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("核销报核次数:");
		}
		return label_1;
	}
	private JTextField getTfCanTime() {
		if (tfCanTime == null) {
			tfCanTime = new JTextField();
			tfCanTime.setPreferredSize(new Dimension(44, 21));
			tfCanTime.setEditable(false);
		}
		return tfCanTime;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("盘点核查批次:");
		}
		return label_2;
	}
	private JTextField getTfSection() {
		if (tfSection == null) {
			tfSection = new JTextField();
			tfSection.setPreferredSize(new Dimension(44, 21));
			tfSection.setEditable(false);
		}
		return tfSection;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("盘点开始日期:");
		}
		return label_3;
	}
	private JTextField getTfBeginDate() {
		if (tfBeginDate == null) {
			tfBeginDate = new JTextField();
			tfBeginDate.setPreferredSize(new Dimension(78, 21));
			tfBeginDate.setEnabled(false);
		}
		return tfBeginDate;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel();
			label_4.setText("盘点结束日期:");
		}
		return label_4;
	}
	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
			tfEndDate.setPreferredSize(new Dimension(78, 21));
			tfEndDate.setEnabled(false);
		}
		return tfEndDate;
	}
	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("备案序号：");
		}
		return label_5;
	}
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setPreferredSize(new Dimension(80, 25));
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
		this.section = section;
		fillValues(section);
		if(seqNum != null)
			getTfSeqNum().setText(String.valueOf(seqNum));
		getBtnHistroy().doClick();
	}
	
}
