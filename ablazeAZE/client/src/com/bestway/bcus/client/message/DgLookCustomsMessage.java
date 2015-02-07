/*
 * Created on 2005-6-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.message.entity.Message;
import com.bestway.client.custom.common.ParseEdiCustomsDeclaration;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * @author xxm // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgLookCustomsMessage extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JScrollPane jScrollPane3 = null;
	private JScrollPane jScrollPane4 = null;
	public List list = null; // @jve:decl-index=0:
	private JEditorPane jEditorPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	// 报文头
	private JTable jMessageHeadTable = null;
	// 报关单单头
	private JTable jCustomsDeclarationHeadTable = null;
	// 报关单单体
	private JTable jCustomsDeclarationBodyTable = null;
	// 自由文本
	private JTable freeTextTable = null;
	private String content = null;
	private JTableListModel tableHeadModel = null;
	private JTableListModel tableCustomsHeadModel = null;
	private BodyTableModel tableBodyModel = null;
	private JTableListModel tableFreeTextModel = null;
	private List<Message> messageHead;
	private List<Message> customsDeclarationHead;
	private List<List> customsDeclarationBodys;
	private List<Message> freeText; // @jve:decl-index=0:
	private JPanel pnContainer = null;
	private JLabel lbContainer = null;
	private JTextField tfContainer = null;
	private JTextArea taContainer = null;
	private JLabel lbTxt = null;
	/**
	 * 集装箱号信息2010-09-14 hcl add
	 */
	private String containerHead=null;
	private String containerText=null;
	
	/**
	 * This is the default constructor
	 */
	public DgLookCustomsMessage() {
		super();
		initialize();

	}

	private void parseMessage() {
		if (content != null && !content.equals("")) {
			ParseEdiCustomsDeclaration parse = ParseEdiCustomsDeclaration
					.getInstance(content);
			try {
				parse.parseMessage();
				messageHead = parse.getMessageHead();
				customsDeclarationHead = parse.getCustomsDeclarationHead();
				customsDeclarationBodys = parse.getCustomsDeclarationBodys();
				freeText = parse.getFreeText();
				containerHead=parse.getContainerHead();
				containerText=parse.getContainerText();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("报关单原文", null, getJScrollPane(), null);
			jTabbedPane.addTab("报文头", null, getJPanel1(), null);
			jTabbedPane.addTab("报关单单头", null, getJPanel2(), null);
			jTabbedPane.addTab("报关单单体", null, getJPanel3(), null);
			jTabbedPane.addTab("自由文本", null, getJPanel4(), null);
			jTabbedPane.addTab("集装箱", null, getPnContainer(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 1) {
								initMessageHeadTable(messageHead);
								System.out
										.println("messageHead:" + messageHead);
							} else if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 2) {
								initJCustomsDeclarationHeadTable(customsDeclarationHead);
								System.out.println("customsDeclarationHead:"
										+ customsDeclarationHead);
							} else if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 3) {
								initJCustomsDeclarationBodyTable(customsDeclarationBodys);
							} else if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 4) {
								initFreeText(freeText);
							} else if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 5) {
								initContainerText(containerHead,containerText);
							}
						}

						private void initContainerText(String containerHead,
								String containerText) {
							tfContainer.setText(containerHead);
							taContainer.setText(containerText);
							
						}
					});
		}
		return jTabbedPane;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane4(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJMessageHeadTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJCustomsDeclarationHeadTable());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJCustomsDeclarationBodyTable());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getFreeTextTable());
		}
		return jScrollPane4;
	}

	private JTable getJMessageHeadTable() {
		if (jMessageHeadTable == null) {
			jMessageHeadTable = new JTable();
		}
		return jMessageHeadTable;
	}

	private JTable getJCustomsDeclarationHeadTable() {
		if (jCustomsDeclarationHeadTable == null) {
			jCustomsDeclarationHeadTable = new JTable();
		}
		return jCustomsDeclarationHeadTable;
	}

	private JTable getJCustomsDeclarationBodyTable() {
		if (jCustomsDeclarationBodyTable == null) {
			jCustomsDeclarationBodyTable = new JTable();
			jCustomsDeclarationBodyTable
					.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}
		return jCustomsDeclarationBodyTable;
	}

	private JTable getFreeTextTable() {
		if (freeTextTable == null) {
			freeTextTable = new JTable();
		}
		return freeTextTable;
	}

	/**
	 * 初始化数据Table
	 */
	private void initMessageHeadTable(List messageHead) {
		if (tableHeadModel != null) {
			tableHeadModel.setList(messageHead);
		} else {
			tableHeadModel = new JTableListModel(jMessageHeadTable,
					messageHead, new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("名称", "title", 120));
							list.add(addColumn("栏位长度", "length", 50));
							list.add(addColumn("英文栏位", "name", 120));
							list.add(addColumn("值", "value", 200));
							return list;
						}
					});
		}
	}

	/**
	 * 初始化数据Table
	 */
	private void initJCustomsDeclarationBodyTable(List list) {
		if (tableBodyModel != null) {
			tableBodyModel.setList(list);
		} else {
			tableBodyModel = new BodyTableModel(list);
			jCustomsDeclarationBodyTable.setModel(tableBodyModel);
		}
	}

	/**
	 * 报关单表头
	 * 初始化数据Table
	 */
	private void initJCustomsDeclarationHeadTable(List CustomsHead) {
		if (tableCustomsHeadModel != null) {
			tableCustomsHeadModel.setList(CustomsHead);
		} else {
			tableCustomsHeadModel = new JTableListModel(
					jCustomsDeclarationHeadTable, CustomsHead,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("名称", "title", 120));
							list.add(addColumn("英文栏位", "name", 120));
							list.add(addColumn("栏位长度", "length", 50));
							list.add(addColumn("值", "value", 320));
							return list;
						}
					});
		}
	}

	/**
	 * 自由文本
	 * 初始化数据Table
	 */
	private void initFreeText(List freeText) {
		if (tableFreeTextModel != null) {
			tableFreeTextModel.setList(freeText);
		} else {
			tableFreeTextModel = new JTableListModel(freeTextTable, freeText,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("名称", "title", 200));
							list.add(addColumn("栏位长度", "length", 50));
							list.add(addColumn("值", "value", 320));
							return list;
						}
					});
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(790, 523);
		this.setContentPane(getJContentPane());
		this.setTitle("报文解析");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				try {
					String s = "";
					for (int i = 0; i < list.size(); i++) {
						s = s + list.get(i) + "\n";
					}
					jEditorPane.setText(s);
					content = s;
					parseMessage();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(DgLookCustomsMessage.this,
							"报文读取解析发生错误：\n" + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setEditable(false);
		}
		return jEditorPane;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	class BodyTableModel extends AbstractTableModel {
		private List<List<Message>> list;

		public BodyTableModel(List<List<Message>> list) {
			this.list = list;
		}

		public List getList() {
			return list;
		}

		public void setList(List list) {
			this.list = list;
		}

		public int getRowCount() {
			if (list == null) {
				return 0;
			}
			return list.size();
		}

		@Override
		public Class getColumnClass(int columnIndex) {
			return String.class;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public void setValueAt(Object aValue, int row, int column) {
		}

		@Override
		public String getColumnName(int column) {
			if (list == null || list.size() == 0)
				return "";
			return list.get(0).get(column).getTitle();
		}

		public int getColumnCount() {
			if (list == null || list.size() == 0)
				return 0;
			return list.get(0).size();
		}

		public Object getValueAt(int row, int column) {
			if (list == null || list.size() == 0)
				return "";
			return list.get(row).get(column).getValue();
		}
	}

	/**
	 * This method initializes pnContainer	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnContainer() {
		if (pnContainer == null) {
			lbTxt = new JLabel();
			lbTxt.setBounds(new Rectangle(87, 89, 271, 23));
			lbTxt.setText("集装箱号(11位)+style_code(1位)");
			lbContainer = new JLabel();
			lbContainer.setBounds(new Rectangle(82, 39, 274, 41));
			lbContainer.setFont(new Font("\u5b8b\u4f53", Font.BOLD, 18));
			lbContainer.setText("集装箱报文标示符：CONTAINER:");
			pnContainer = new JPanel();
			pnContainer.setLayout(null);
			pnContainer.add(lbContainer, null);
			pnContainer.add(getTfContainer(), null);
			pnContainer.add(getTaContainer(), null);
			pnContainer.add(lbTxt, null);
		}
		return pnContainer;
	}

	/**
	 * This method initializes tfContainer	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfContainer() {
		if (tfContainer == null) {
			tfContainer = new JTextField();
			tfContainer.setBounds(new Rectangle(358, 41, 120, 32));
		}
		return tfContainer;
	}

	/**
	 * This method initializes taContainer	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTaContainer() {
		if (taContainer == null) {
			taContainer = new JTextArea();
			taContainer.setBounds(new Rectangle(87, 117, 247, 233));
		}
		return taContainer;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
