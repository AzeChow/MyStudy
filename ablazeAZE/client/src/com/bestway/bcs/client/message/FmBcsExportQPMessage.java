package com.bestway.bcs.client.message;

import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JToolBar;
import javax.swing.ButtonGroup;

public class FmBcsExportQPMessage extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBcsExportQPMessage() {
		super();
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(677, 463));
		this.setContentPane(getJContentPane());
		this.setTitle("导出文件(QP导入用)");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						addTabbedPane();
					}
				});

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.LEFT);
			jTabbedPane.addTab("物料备案", null, getJPanel(), null);
			jTabbedPane.addTab("BOM备案", null, getJPanel1(), null);
			jTabbedPane.addTab("中期核查", null, getJPanel2(), null);
			jTabbedPane.addTab("数据报核", null, getJPanel3(), null);
			jTabbedPane.addTab("报关清单", null, getJPanel4(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							addTabbedPane();
						}
					});
		}
		return jTabbedPane;
	}

	private void addTabbedPane() {
//		if (jTabbedPane.getSelectedIndex() == 0) {
//			if (jPanel.getComponentCount() <= 0) {
//				jPanel.add(new PnBcsExportMaterial(), BorderLayout.CENTER);
//			}
//		} else if (jTabbedPane.getSelectedIndex() == 1) {
//			if (jPanel1.getComponentCount() <= 0) {
//				jPanel1.add(new PnBcsExportBom(), BorderLayout.CENTER);
//			}
//		} else if (jTabbedPane.getSelectedIndex() == 2) {
//			if (jPanel2.getComponentCount() <= 0) {
//				jPanel2.add(new PnBcsCheckImg(), BorderLayout.CENTER);
//			}
//		} else if (jTabbedPane.getSelectedIndex() == 3) {
//			if (jPanel3.getComponentCount() <= 0) {
//				jPanel3.add(new PnBcsExportCav(), BorderLayout.CENTER);
//			}
//		} else if (jTabbedPane.getSelectedIndex() == 4) {
//			if (jPanel4.getComponentCount() <= 0) {
//				jPanel4.add(new PnBcsExportBillList(), BorderLayout.CENTER);
//			}
//		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
		}
		return jPanel4;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
