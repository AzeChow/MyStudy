package com.bestway.bcs.client.dictpor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
@SuppressWarnings("serial")
public class DgBcsDictPorImgExgSeqNumEdit extends JDialogBase {
	private JSplitPane splitPane;
	private JScrollPane scroPnImg;
	private JScrollPane scroPnExg;
	private JPanel panel;
	private JButton btnSave;
	private JButton btnClose;
	private JTable tbImg;
	private JTable tbExg;
	private JTableListModel tbmImg;
	private JTableListModel tbmExg;	
	private BcsDictPorAction bcsDictPorAction = null;
	private boolean isFinish = false;
	private JPanel panel2;
	private JLabel lbMsg;

	/**
	 * Create the dialog.
	 */
	public DgBcsDictPorImgExgSeqNumEdit() {
		initialize();
	}
	private void initialize() {
		bcsDictPorAction = (BcsDictPorAction) CommonVars.getApplicationContext().getBean("bcsDictPorAction");
		setTitle("归并序号修改窗口");
		setBounds(100, 100, 850, 487);		
		getContentPane().add(getPanel2(), BorderLayout.NORTH);
		getContentPane().add(getSplitPane(), BorderLayout.CENTER);
		getContentPane().add(getPanel(), BorderLayout.SOUTH);
	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setDividerSize(5);
			splitPane.setDividerLocation(200);
			splitPane.setLeftComponent(getScroPnImg());
			splitPane.setRightComponent(getScroPnExg());
		}
		return splitPane;
	}
	private JScrollPane getScroPnImg() {
		if (scroPnImg == null) {
			scroPnImg = new JScrollPane();
			scroPnImg.setBorder(new TitledBorder(null, "\u6599\u4EF6\u8868\u683C", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLUE));
			scroPnImg.setViewportView(getTbImg());
		}
		return scroPnImg;
	}
	private JScrollPane getScroPnExg() {
		if (scroPnExg == null) {
			scroPnExg = new JScrollPane();
			scroPnExg.setBorder(new TitledBorder(null, "\u6210\u54C1\u8868\u683C", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
			scroPnExg.setViewportView(getTbExg());
		}
		return scroPnExg;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setHgap(15);
			panel.setPreferredSize(new Dimension(1, 30));
			panel.add(getBtnSave());
			panel.add(getBtnClose());
		}
		return panel;
	}
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("保存");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(getContentPane(), "是否确认保存？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){						
						if( tbImg.getEditingColumn() >= 0 && tbImg.getEditingRow() >=0)
							tbImg.editingStopped(new ChangeEvent(e.getSource()));
						if( tbExg.getEditingColumn() >= 0 && tbExg.getEditingRow() >=0)
							tbExg.editingStopped(new ChangeEvent(e.getSource()));
						List list = new ArrayList(tbmImg.getList());
						list.addAll(tbmExg.getList());
						list = bcsDictPorAction.saveBcsDictPorImgExg(new Request(CommonVars.getCurrUser()), list);
						isFinish = true;
						for(Object o : list){
							if(o instanceof BcsDictPorExg && (((BcsDictPorExg)o).getInnerMergeSeqNum( )== null
						||((BcsDictPorExg)o).getInnerMergeSeqNum( )== 0)){
								isFinish = false;
								break;
							}
							if(o instanceof BcsDictPorImg && (((BcsDictPorImg)o).getInnerMergeSeqNum()== null 
									|| ((BcsDictPorImg)o).getInnerMergeSeqNum()== 0)){
								isFinish = false;
								break;
							}
						}						
						if(isFinish)
							dispose();					
						tbmExg.updateRows(list);
						tbmImg.updateRows(list);
					}
					
				}
			});
		}
		return btnSave;
	}
	
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!isFinish){
						if(JOptionPane.NO_OPTION == 
								JOptionPane.showConfirmDialog(getContentPane(), "您还未完成归并序号补充操作，确定要关闭？","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE))
							return;
					}
					dispose();
				}
			});
		}
		return btnClose;
	}
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
		}
		return tbExg;
	}
	
	public void setImgs(List<BcsDictPorImg> imgs) {
		initTbmImgs(imgs);
	}
	
	/**
	 * 初始化料件表格模版
	 */
	private void initTbmImgs(List<BcsDictPorImg> imgs){
		tbmImg = new JTableListModel(tbImg, imgs, new JTableListModelAdapter() {			
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("归并序号", "innerMergeSeqNum", 100,Integer.class));
				list.add(addColumn("备案序号", "seqNum", 100,Integer.class));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 100));
				list.add(addColumn("计量单位", "comUnit.name", 60));
				return list;
			}			
		}){
			//设置第一列可编辑
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex ==1;
			}
		};
		tbmImg.setAllowSetValue(true);	//设置可编辑
		tbImg.getColumnModel().getColumn(1).setCellEditor(new IntegerCellEditor()); 	//设置第一列编辑控件
	}
	
	public void setExgs(List<BcsDictPorExg> exgs) {
		initTbmExgs(exgs);
	}
	/**
	 * 初始化成品表格模版
	 */
	private void initTbmExgs(List<BcsDictPorExg> exgs){
		tbmExg = new JTableListModel(tbExg, exgs, new JTableListModelAdapter() {			
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("归并序号", "innerMergeSeqNum", 100,Integer.class));
				list.add(addColumn("备案序号", "seqNum", 100,Integer.class));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("型号规格", "commSpec", 100));
				list.add(addColumn("计量单位", "comUnit.name", 60));
				return list;
			}
		}){
			//设置第一列可编辑
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnIndex ==1;
			}
		};
		tbmExg.setAllowSetValue(true);		//设置可编辑
		tbExg.getColumnModel().getColumn(1).setCellEditor(new IntegerCellEditor()); //设置第一列编辑控件
	}
	
	class IntegerCellEditor extends DefaultCellEditor{
		public IntegerCellEditor() {
			super(new JIntegerTextField());
			final JIntegerTextField textField = (JIntegerTextField)editorComponent;
			this.clickCountToStart = 1;
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
		}
	}
	
	class JIntegerTextField extends JTextField{
		public JIntegerTextField() {
			setDocument(new PlainDocument(){
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					char c = str.charAt(str.length()-1);
					if(c >='0' && c <='9'){
						super.insertString(offs, str, a);
					}
				}
			});
		}
		public Object getValue(){
			String value = getText().trim();
			return value.isEmpty() ? null : Integer.valueOf(value);
		}
		public void setValue(Object value){
			setText(value == null ? "" : value.toString());
		}
	}
	private JPanel getPanel2() {
		if (panel2 == null) {
			panel2 = new JPanel();
			panel2.setPreferredSize(new Dimension(1,30));
			panel2.setLayout(null);
			panel2.add(getLbMsg());
		}
		return panel2;
	}
	private JLabel getLbMsg() {
		if (lbMsg == null) {
			lbMsg = new JLabel("注：下方两个表格中的归并序号列可点击修改。");
			lbMsg.setForeground(Color.BLUE);
			lbMsg.setBounds(23, 0, 787, 25);
		}
		return lbMsg;
	}
}
