package com.bestway.bcus.client.checkstock.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CaleUtil;
import com.bestway.common.Request;
import com.bestway.util.AsynSwingWorker;
import com.bestway.util.FileUtils;
@SuppressWarnings("serial")
public class PnECSTransferImgPt extends JPanel {
	private JToolBar toolBar;
	private JLabel label;
	private JTextField tfSeqNum;
	private JScrollPane scrollPane;
	private MultiSpanCellTable table;
	private JButton btnHistroy;
	private JButton btnCalculate;
	private JButton btnExport;
	private JButton btnClean;
	private JButton btnClose;
	private JButton btnImport;
	private FmECSTransferImgBalance parent;
	private JTableListModel tableModel = null;
	private Request request;
	private JTableListModelAdapter adapterRawdata;
	
	public PnECSTransferImgPt(FmECSTransferImgBalance parent) {
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
			toolBar.add(getBtnCalculate());
			toolBar.add(getBtnExport());
			toolBar.add(getBtnClean());
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
			protected List<?> submit(){
				try{
					initBtnStatus(0);
					CommonProgress.showProgressDialog(parent);
					CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
					String seqNum = tfSeqNum.getText().trim();
					return parent.ecsCheckStockAction.findECSTransferDiffImgs(request, parent.getEcsSection(),(seqNum.isEmpty() ?null:Integer.parseInt(seqNum)));
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(java.util.List<?> result) {
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
			btnCalculate = new JButton(" 折算报关数量 ");
			btnCalculate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doConvertStars();
				}
			});
		}
		return btnCalculate;
	}

	
	private void doConvertStars(){
		new AsynSwingWorker<List<?>>() {
			protected List<?> submit() {
				CommonProgress.showProgressDialog(parent);
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				try{
					initBtnStatus(0);
					return parent.ecsCheckStockAction.convertECSTransferDiffImgs(request, parent.getEcsSection());
				}finally{
					initBtnStatus(2);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(java.util.List<?> result) {
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
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
						try {
							initBtnStatus(0);
							parent.ecsCheckStockAction.deleteECSTransferDiffImgs(request, parent.getEcsSection());
							initTable(Collections.EMPTY_LIST);
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(parent, "未能成功删除！", "警告",JOptionPane.WARNING_MESSAGE);
						}finally{
							initBtnStatus(2);
						}
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
						JOptionPane.showMessageDialog(parent, "批次号不能为空！", "警告",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					try {
						if(parent.ecsCheckStockAction.isExistsBySection(new Request(CommonVars.getCurrUser()), parent.getEcsSection(), ECSTransferDiffImg.class)){
							if (JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?", "提示",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是(Y)","否(N)" }, "否(N)")) {
								return;
							}
							parent.ecsCheckStockAction.deleteECSTransferDiffImgs(request, parent.getEcsSection());
							initTable(Collections.EMPTY_LIST);
						}
						DgECSImport dgECSImport = new DgECSImport(true,false);
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
			table = new MultiSpanCellTable();
		}
		return table;
	}
	
	public void initTable(List<?> list) {
		list = (list == null ? Collections.EMPTY_LIST : list);
		initTable(table, list);
	}

	// 填充盘点--料件--下
	public JTableListModel initTable(JTable jTable, final List<?> list) {
		adapterRawdata = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("料号", "ptNo", 110));
				list.add(addColumn("工厂名称", "ptName", 100));
				list.add(addColumn("工厂规格", "ptSpec", 120));
				list.add(addColumn("计量单位", "ptUnit", 50));
				list.add(addColumn("已收货未转厂", "ptUnTransferNum", 100));
				list.add(addColumn("已转厂未收货", "ptUnSendferNum", 100));

				list.add(addColumn("备案序号", "seqNum", 110));
				list.add(addColumn("报关商品名称", "hsName", 100));
				list.add(addColumn("报关商品规格", "hsSpec", 120));
				list.add(addColumn("计量单位", "hsUnit", 50));
				list.add(addColumn("折算已收货未转厂", "hsUnTransferNum", 100));
				list.add(addColumn("折算已转厂未收货", "hsUnSendferNum", 100));
				list.add(addColumn("折算系数", "unitConvert", 100));

				return list;
			}
		};

		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable, list, adapterRawdata);
		TableColumnModel cm = jTable.getColumnModel();
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

		GroupableTableHeader header = (GroupableTableHeader) jTable.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(hsImpGroup);
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
