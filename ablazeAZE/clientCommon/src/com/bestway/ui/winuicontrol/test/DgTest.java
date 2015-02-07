package com.bestway.ui.winuicontrol.test;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.JTableFooter;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import com.bestway.ui.winuicontrol.JLineLabel;

public class DgTest extends JFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JFooterScrollPane jFooterScrollPane = null;

	private JFooterTable jFooterTable = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JScrollPane jScrollPane = null;

	private JLineLabel jLineLabel = null;

	private JTextField jTextField = null;

	public DgTest() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(494, 405));
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {

			jLineLabel = new JLineLabel();
			jLineLabel.setBounds(new Rectangle(226, 269, 112, 18));
			jLineLabel.setText("测试");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJFooterScrollPane(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLineLabel, null);
			jContentPane.add(getJTextField(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jFooterScrollPane
	 * 
	 * @return com.bestway.client.util.footer.JFooterScrollPane
	 */
	private JFooterScrollPane getJFooterScrollPane() {
		if (jFooterScrollPane == null) {
			jFooterScrollPane = new JFooterScrollPane();
			jFooterScrollPane.setBounds(new Rectangle(30, 37, 411, 228));
			jFooterScrollPane.setViewportView(getJFooterTable());
			jFooterScrollPane.setViewportView(getJFooterTable());
		}
		return jFooterScrollPane;
	}

	/**
	 * This method initializes jFooterTable
	 * 
	 * @return com.bestway.client.util.footer.JFooterTable
	 */
	private JFooterTable getJFooterTable() {
		if (jFooterTable == null) {
			jFooterTable = new JFooterTable();
			/* 表格列向量 */
			Vector cell;
			/* 表格行向量 */
			Vector row = new Vector();
			/* 声明表格模型 */
			DefaultTableModel tableModel = new DefaultTableModel();
			/* 声明表格头数组 */
			String[] tableHeads = { "ID", "姓名", "年龄", "城市" };
			/* 将表格头转换过向量类型，以备表格模型使用 */
			Vector tableHeadName = new Vector();
			for (int i = 0; i < tableHeads.length; i++) {
				tableHeadName.add(tableHeads[i]);
			}
			/* 初始化表格数据，这些数据实例运行来源于数据库中 */
			for (int i = 1; i <= 100; i++) {
				cell = new Vector();
				cell.add("" + i);
				cell.add(" 水如清" + i);
				cell.add(" 22");
				cell.add(" 深圳");
				row.add(cell);
			}
			/* 设置表格模型 */
			tableModel.setDataVector(row, tableHeadName);

			jFooterTable.setModel(tableModel);

			jFooterTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


		}
		return jFooterTable;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(328, 5, 63, 23));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableFooter tableFooter = jFooterTable.getTableFooter();
					if (tableFooter != null) {
						tableFooter.addFooterTypeInfo(new TableFooterType(2,
								TableFooterType.SUM, ""));
						tableFooter.addFooterTypeInfo(new TableFooterType(1,
								TableFooterType.MIN, ""));
						tableFooter.addFooterTypeInfo(new TableFooterType(0,
								TableFooterType.AVG, "平均"));
						tableFooter.addFooterTypeInfo(new TableFooterType(3,
								TableFooterType.COUNT, ""));
						tableFooter.statisticTableFooterInfo();
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(404, 6, 60, 24));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableFooter tableFooter = jFooterTable.getTableFooter();
					if (tableFooter != null) {
						tableFooter.removeFooterTypeInfo(0);
						tableFooter.removeFooterTypeInfo(1);
						tableFooter.removeFooterTypeInfo(2);
						tableFooter.removeFooterTypeInfo(3);
						tableFooter.statisticTableFooterInfo();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(163, 2, 104, 32));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					UIManager.getLookAndFeelDefaults().
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(100, 292, 253, 46));
			jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Test", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(335, 269, 71, 18));
		}
		return jTextField;
	}

	public static void main(String[] args) {
		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// }
		// catch(Exception e) {
		// e.printStackTrace();
		// }
		DgTest frame = new DgTest();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
