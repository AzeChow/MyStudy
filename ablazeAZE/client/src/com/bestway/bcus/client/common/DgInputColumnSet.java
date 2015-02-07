package com.bestway.bcus.client.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 栏位设定主窗体
 * 
 * @author sanvi 2010-07-07 check by hcl
 */
@SuppressWarnings({"unchecked", "serial"})
public class DgInputColumnSet extends JDialogBase {

	private JPanel jContentPane = null;// 主面板

	private JPanel jPanel = null;// 右侧面板

	private JButton btnOk = null;// 确定按钮

	private JButton btnCancel = null;// 取消按钮

	private JButton btnRemove = null;// 删除按钮

	private JButton btnDown = null;// 下移按钮

	private JButton btnUp = null;// 上移按钮

	private JPanel jPanel1 = null;// 右侧面板

	private JPanel jPanel2 = null;// 右侧需要导入面板

	private JPanel jPanel3 = null;// 右侧所有栏位面板

	private JLabel jLabel = null;// 表的所有栏位标题

	private JLabel jLabel1 = null;// 需要导入栏位标题

	private JScrollPane jScrollPane = null;// 表的所以栏位滚动面板

	private JScrollPane jScrollPane1 = null;// 需要导入栏位滚动面板

	private JList listSour = null;// 左侧下拉选框List

	private JList listDest = null;// 右侧下拉选框List

	private JLabel jLabel2 = null;// 赋值标签

	private SystemAction systemAction = null;// SystemAction接口

	private String tableFlag; // 表标签 // @jve:decl-index=0:

	private boolean ok = false;// 判断是否确定

	private List<InputTableColumnSet> columnList = new ArrayList<InputTableColumnSet>(); // 栏位List:  //  @jve:decl-index=0:

	/**
	 * 
	 * 获取ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * 
	 * 设置ok
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * 
	 * 获取tableFlag
	 */
	public String getTableFlag() {
		return tableFlag;
	}

	/**
	 * 
	 * 设置tableFlag
	 */
	public void setTableFlag(String tableFlag) {
		this.tableFlag = tableFlag;
	}

	 public List getColumnList() {
	 return columnList;
	 }
	
	 public void setColumnList(List columnList) {
	 this.columnList = columnList;
	 }

	/**
	 * 
	 * 构造函数 初始化数据面板等
	 */
	public DgInputColumnSet() {
		super();
		initialize();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		listSour.setCellRenderer(new CheckListCellRenderer());
		listDest.setCellRenderer(new DefaultListCellRenderer() {

			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null && value instanceof InputTableColumnSet) {
					String caption = ((InputTableColumnSet) value)
							.getColumnCaption();
					// String[] strs = caption.split(":");
					// this.setText(String.valueOf(index + 1) + ":"
					// + (strs.length > 1 ? strs[1] : strs[0]));
					this.setText(String.valueOf(index + 1) + ":" + caption);
				} else {
					this.setText("");
				}
				this.setPreferredSize(new Dimension(Double.valueOf(
						this.getPreferredSize().getWidth()).intValue(), 26));
				return this;
			}
		});
	}

	/**
	 * 
	 * 内部类CheckListCellRenderer
	 */
	class CheckListCellRenderer extends JCheckBox implements ListCellRenderer {
		/**
		 * 
		 * @param tableFlag
		 */
		public CheckListCellRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		/**
		 * 
		 * @param tableFlag
		 */
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			if (value != null && value instanceof InputTableColumnSet) {
				this.setText(String.valueOf(index + 1) + ":"
						+ ((InputTableColumnSet) value).getColumnCaption());
				this.setSelected(((InputTableColumnSet) value).isSelected());
			} else {
				this.setText("");
				this.setSelected(false);
			}
			return this;
		}
	}

	/**
	 * 初始化面板数据
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(569, 472));
		this.setTitle("自定义栏位设定");
		this.setContentPane(getJContentPane());

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
			
				// 可供导入的列
				List<InputTableColumnSet> columnListNew = getInputDefaultTableColumnList(tableFlag);
				
				// 已经选择的列
				List<InputTableColumnSet> lsDestColumnSet = systemAction.findInputTableColumnSet(
						new Request(CommonVars.getCurrUser()), tableFlag);
				
				Map<String, InputTableColumnSet> columnMap = new HashMap<String, InputTableColumnSet>(columnListNew.size());
				
				if(columnList.size()==0){
					columnList =columnListNew;
				}else {
					if(columnListNew!=null){ //技嘉
						for (int i = 0; i < columnListNew.size(); i++) {
							columnList.add(columnListNew.get(i));
						}
					}
				}
				
				DefaultListModel sourListModel = new DefaultListModel();
				InputTableColumnSet inputTableColumnSet = null;
				for (int i = 0; i < columnList.size(); i++) {
					inputTableColumnSet = columnList.get(i);
					
					// 以下几列默认选择 2013.8.24
					/*if(i == 5 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11|| i == 12 || i == 13 ) {
						columnMap.put(inputTableColumnSet.getColumnField(), inputTableColumnSet);
					}*/
					sourListModel.addElement(inputTableColumnSet);
				}
				
				
				// 添加默认选择的列
				for (InputTableColumnSet columnSet : columnMap.values()) {
					boolean isAdd = true;
					for (InputTableColumnSet sourColumnSet : lsDestColumnSet) {
						if (sourColumnSet.getColumnField().equals(
								columnSet.getColumnField())) {
							isAdd = false;
							break;
						}
					}
					
					if(isAdd) {
						lsDestColumnSet.add(columnSet);
					}
				}
				
				listSour.setModel(sourListModel);
				DefaultListModel destListModel = new DefaultListModel();
				
				for (int i = 0; i < lsDestColumnSet.size(); i++) {
					destListModel.addElement(lsDestColumnSet.get(i));
					selectedSourColumnSet((InputTableColumnSet) lsDestColumnSet
							.get(i));
				}
				listDest.setModel(destListModel);
			}
		});
	}

	/**
	 * 
	 * 选取源目标栏位设置
	 */
	private void selectedSourColumnSet(InputTableColumnSet columnSet) {
		DefaultListModel sourListModel = (DefaultListModel) listSour.getModel();
		for (int i = 0; i < sourListModel.size(); i++) {
			InputTableColumnSet sourColumnSet = (InputTableColumnSet) sourListModel
					.getElementAt(i);
			if (sourColumnSet.getColumnField().equals(
					columnSet.getColumnField())) {
				sourColumnSet.setSelected(true);
				break;
			}
		}
	}

	/**
	 * 
	 * 获取jContentPane面板
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.EAST);
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 获取面板jPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setText(" ");
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.add(jLabel2, null);
			jPanel.add(getBtnUp(), null);
			jPanel.add(getBtnDown(), null);
			jPanel.add(getBtnRemove(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * 
	 * 获取按钮：确定
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setName("jButton");
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultListModel listModel = (DefaultListModel) listDest
							.getModel();
					List list = new ArrayList();
					for (int i = 0; i < listModel.size(); i++) {
						InputTableColumnSet columnSet = (InputTableColumnSet) listModel
								.getElementAt(i);
						columnSet.setId(null);
						columnSet.setSortNo(i);
						columnSet.setTableFlag(tableFlag);
						list.add(columnSet);
					}
					systemAction.saveInputTableColumnSet(new Request(CommonVars
							.getCurrUser()), list, tableFlag);
					ok = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * 获取按钮：取消
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setName("jButton1");
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 获取按钮：移除
	 */
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton();
			btnRemove.setText("移除");
			btnRemove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultListModel listModel = (DefaultListModel) listDest
							.getModel();
					int index = listDest.getSelectedIndex();
					if (index < 0) {
						return;
					}
					InputTableColumnSet columnSet = (InputTableColumnSet) listModel
							.getElementAt(index);
					DefaultListModel sourListModel = (DefaultListModel) listSour
							.getModel();
					for (int i = 0; i < sourListModel.size(); i++) {
						InputTableColumnSet sourColumnSet = (InputTableColumnSet) sourListModel
								.getElementAt(i);
						if (sourColumnSet.getColumnField().equals(
								columnSet.getColumnField())) {
							sourColumnSet.setSelected(false);
							break;
						}
					}
					listModel.removeElementAt(index);
					if (index < listModel.getSize()) {
						listDest.setSelectedIndex(index);
					} else {
						if (listModel.getSize() > 0) {
							listDest.setSelectedIndex(listModel.getSize() - 1);
						}
					}
					listSour.repaint();
				}
			});
		}
		return btnRemove;
	}

	/**
	 * 
	 * 获取按钮：下移
	 */
	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton();
			btnDown.setText("下移");
			btnDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultListModel listModel = (DefaultListModel) listDest
							.getModel();
					int index = listDest.getSelectedIndex();
					if (index == listModel.getSize() - 1) {
						return;
					}
					InputTableColumnSet columnSet = (InputTableColumnSet) listModel
							.getElementAt(index);
					listModel.removeElementAt(index);
					listModel.insertElementAt(columnSet, index + 1);
					listDest.setSelectedIndex(index + 1);
				}
			});
		}
		return btnDown;
	}

	/**
	 * 
	 * 获取按钮：上移
	 */
	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton();
			btnUp.setText("上移");
			btnUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DefaultListModel listModel = (DefaultListModel) listDest
							.getModel();
					int index = listDest.getSelectedIndex();
					if (index == 0) {
						return;
					}
					InputTableColumnSet columnSet = (InputTableColumnSet) listModel
							.getElementAt(index);
					listModel.removeElementAt(index);
					listModel.insertElementAt(columnSet, index - 1);
					listDest.setSelectedIndex(index - 1);
				}
			});
		}
		return btnUp;
	}

	/**
	 * 
	 * 获取面板jPanel1
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(1);
			gridLayout1.setColumns(2);
			jPanel1 = new JPanel();
			jPanel1.setLayout(gridLayout1);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel2(), null);

		}
		return jPanel1;
	}

	/**
	 * 
	 *获取面板：jPanel2
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("需要导入的栏位 (栏位数为零，导入全部栏位)");
			jLabel1.setForeground(Color.blue);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(jLabel1, BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * 
	 * 获取面板jPanel3
	 * 
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel = new JLabel();
			jLabel.setText("表的所有栏位");
			jLabel.setForeground(Color.blue);
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(jLabel, BorderLayout.NORTH);
			jPanel3.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * 
	 * 获取面板：jScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * 
	 * 获取面板：jScrollPane1
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList1());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * 
	 * 获取JList：listSour
	 * 
	 */
	private JList getJList() {
		if (listSour == null) {
			listSour = new JList();
			listSour.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int index = listSour.locationToIndex(e.getPoint());
					InputTableColumnSet item = (InputTableColumnSet) listSour
							.getModel().getElementAt(index);
					item.setSelected(!item.isSelected());

					DefaultListModel listModel = (DefaultListModel) listDest
							.getModel();
					if (item.isSelected()) {
						listModel.addElement(item);
					} else {
						for (int i = listModel.getSize() - 1; i >= 0; i--) {
							InputTableColumnSet destItem = (InputTableColumnSet) listModel
									.get(i);
							if (item.getColumnField().equals(
									destItem.getColumnField())) {
								listModel.remove(i);
							}
						}
					}
					Rectangle rect = listSour.getCellBounds(index, index);
					listSour.repaint(rect);
				}
			});
		}
		return listSour;
	}

	/**
	 * 
	 * 
	 * 获取JList：listDest
	 * 
	 */
	private JList getJList1() {
		if (listDest == null) {
			listDest = new JList();
		}
		return listDest;
	}

	/**
	 * 
	 * 
	 * 获取和初始化List：lsResult
	 * 
	 */
	private List getInputDefaultTableColumnList(String tableFlag) {
		List list = InputTableColumnSetUtils.getColumn(tableFlag);// getDefaultImgTableColumnList();
		List<InputTableColumnSet> lsResult = new ArrayList<InputTableColumnSet>();
		for (int i = 0; i < list.size(); i++) {
			JTableListColumn column = (JTableListColumn) list.get(i);
			if (column.getWidth() == 0) {
            continue;
			}
			InputTableColumnSet columnSet = new InputTableColumnSet();
			columnSet.setColumnCaption(column.getCaption());
			columnSet.setColumnField(column.getProperty());
			lsResult.add(columnSet);
		}
		return lsResult;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
