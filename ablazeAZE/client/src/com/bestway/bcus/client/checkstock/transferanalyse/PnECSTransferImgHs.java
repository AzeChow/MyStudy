
package com.bestway.bcus.client.checkstock.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.client.verification.transferanalyse.PnVFTransferDiffHsExg;
import com.bestway.bcs.client.verification.transferanalyse.PnVFTransferDiffHsImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExg;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExg;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;
import com.bestway.util.FileUtils;
@SuppressWarnings("serial")
public class PnECSTransferImgHs extends JPanel{

	private JToolBar toolBar;
	private JLabel label;
	private JTextField tfSeqNum;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnHistroy;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnImport;
	private FmECSTransferImgBalance parent;
	private JTableListModel tableModel = null;
	private Request request;
	private JButton btnImpFormFpt;
	
	public PnECSTransferImgHs(FmECSTransferImgBalance parent) {
		this.parent = parent;
		initialize();
	}
	private void initialize() {
		request = new Request(CommonVars.getCurrUser());		
		setLayout(new BorderLayout(0, 0));
		add(getToolBar(), BorderLayout.NORTH);
		add(getScrollPane(), BorderLayout.CENTER);
		initTable(Collections.EMPTY_LIST);
	}
	
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
			toolBar.add(getLabel());
			toolBar.add(getTfSeqNum());
			toolBar.add(getBtnHistroy());
			toolBar.add(getBtnImport());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClean());
			toolBar.add(getBtnImpFormFpt());
			toolBar.add(getBtnClose());
		}
		return toolBar;
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
	

	/**
	 * 
	 * @return
	 */
	private JButton getBtnHistroy() {
		if (btnHistroy == null) {
			btnHistroy = new JButton("\u67E5\u770B\u5386\u53F2\u8BB0\u5F55");
			btnHistroy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getEcsSection() == null) {
						JOptionPane.showMessageDialog(parent, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					doFindStars();
				}
			});
		}
		return btnHistroy;
	}

	private void doFindStars(){
		new AsynSwingWorker<List<?>>() {
			protected List<?> submit() {
				try{
					initBtnStatus(0);
					CommonProgress.showProgressDialog(parent);
					CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
					String seqNum = tfSeqNum.getText().trim();
					return parent.ecsCheckStockAction.findECSTransferDiffImgs(request, parent.getEcsSection(),(seqNum.isEmpty() ?null:Integer.parseInt(seqNum)));
				}finally{
					CommonProgress.closeProgressDialog();
					initBtnStatus(2);
				}
			}
			protected void success(List<?> result) {
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
					if (tableModel.getRowCount() <= 0) {
						return;
					}
					if (parent.getEcsSection() == null) {
						JOptionPane.showMessageDialog(parent, "批次号不能为空！", "警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.YES_NO_OPTION == JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?", "提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)",
									"否(N)" }, "否(N)")) {
						try {
							// ....
							parent.ecsCheckStockAction.deleteECSTransferDiffImgs(request, parent.getEcsSection());
							initTable(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(parent, "未能成功删除！", "警告",
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
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton(" \u5BFC\u5165\u76D8\u70B9\u6570\u91CF ");
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (parent.getEcsSection() == null) {
						JOptionPane.showMessageDialog(parent, "批次号不能为空！", "警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					try {
						initBtnStatus(0);
						if(parent.ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getEcsSection(), ECSTransferDiffImg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							parent.ecsCheckStockAction.deleteECSTransferDiffImgs(request, parent.getEcsSection());							
							initTable(Collections.EMPTY_LIST);
						}
						DgECSImport dgECSImport = new DgECSImport(true,true);
						dgECSImport.setFmObj(parent);
						dgECSImport.setVisible(true);
					}finally{
						initBtnStatus(2);
					}
				}
			});
		}
		return btnImport;
	}

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

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}
	
	public void initTable(List<?> list) {
		list = (list == null ? Collections.EMPTY_LIST : list);
		initTable(table, list);
	}

	// 填充盘点--料件--下
	public JTableListModel initTable(JTable jTable, final List<?> list) {
		tableModel = new JTableListModel(jTable, list, new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();				
				list.add(addColumn("备案序号", "seqNum", 110));
				list.add(addColumn("报关商品名称", "hsName", 100));
				list.add(addColumn("报关商品规格", "hsSpec", 120));
				list.add(addColumn("计量单位", "hsUnit", 80));
				list.add(addColumn("已收货未转厂", "hsUnTransferNum", 100));
				list.add(addColumn("已转厂未收货", "hsUnSendferNum", 100));
				list.add(addColumn("收货数量", "hsBillNum", 100));
				list.add(addColumn("报关数量", "hsCustomsNum", 100));
				return list;
			}
		});
		return tableModel;
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
	 * @param i
	 */
	public void initBtnStatus(int i) {
		switch (i) {
		case 0:
			btnClean.setEnabled(false);
			btnExport.setEnabled(false);
			btnHistroy.setEnabled(false);
			btnImport.setEnabled(false);
			btnImpFormFpt.setEnabled(false);
			break;
		case 1:
			btnHistroy.setEnabled(true);
			btnImport.setEnabled(true);
			break;
		case 2:
			btnImpFormFpt.setEnabled(true);
			btnClean.setEnabled(true);
			btnExport.setEnabled(true);
			btnImport.setEnabled(true);
			btnHistroy.setEnabled(true);
			break;
		default:
			break;
		}
	}
	
	public String getSeqNum() {
		return tfSeqNum.getText().trim();
	}
	
	public void setSeqNum(String seqNum){
		tfSeqNum.setText(seqNum);
	}
	private JButton getBtnImpFormFpt() {
		if (btnImpFormFpt == null) {
			btnImpFormFpt = new JButton("从深加工结转获取");
			btnImpFormFpt.setToolTipText("从深加工结转获取");
			btnImpFormFpt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final ECSSection section = parent.getEcsSection();
					if(section == null){
						JOptionPane.showMessageDialog(parent,"请先选择批次后，再操作！","警告",JOptionPane.WARNING_MESSAGE);								
						return ;
					}
					final Request req = new Request(CommonVars.getCurrUser());					
					if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(PnECSTransferImgHs.this, "确定先删除本批次数据，并从深加工模块获取数据吗？",
							"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
						return ;
					}					
					new AsynSwingWorker<List<ECSTransferDiffImg> >() {
						public List<ECSTransferDiffImg> submit() {			
							req.setTaskId(CommonProgress.getExeTaskId());
							CommonStepProgress.showStepProgressDialog(req.getTaskId());
							CommonStepProgress.setStepMessage("系统正在发送获取请求，请稍后...");
							long beginTime = System.currentTimeMillis();									
							try{
								initBtnStatus(0);
								return parent.ecsCheckStockAction.generateTransferDiffImgHsFormFpt(req, section);												
							}finally{
								System.out.println(" 获取数量完成,共用时:" + (System.currentTimeMillis() - beginTime) + " 毫秒 ");
								initBtnStatus(2);
								CommonStepProgress.closeStepProgressDialog();
							}
						}
						protected void success(List<ECSTransferDiffImg> e) {						
							initTable(e);		
						}
					}.doWork();
				}
			});
		}
		return btnImpFormFpt;
	}
	
	/**
	 * 显示数据
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {
		this.tfSeqNum.setText(seqNum == null ? null : String.valueOf(seqNum));
		this.doFindStars();
	}
}
