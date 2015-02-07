package test;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTable;

public class FmTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JDesktopPane jDesktopPane = null;
	private JInternalFrame jInternalFrame = null;
	private JPanel jContentPane1 = null;
	private JInternalFrame jInternalFrame1 = null;
	private JPanel jContentPane2 = null;
	private JButton jButton1 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton5 = null;
	private JButton jButton6 = null;
	private JTabbedPane jTabbedPane = null;
	private JTabbedPane jTabbedPane1 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JScrollPane jScrollPane = null;
	private JSplitPane jSplitPane1 = null;
	private JInternalFrame jInternalFrame2 = null;
	private JPanel jContentPane3 = null;
	private JPanel jPanel = null;
	private JPanel jPanel5 = null;
	private JToolBar jToolBar = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JButton jButton7 = null;
	private JButton jButton8 = null;
	private JButton jButton9 = null;
	private JButton jButton10 = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(14, 20, 136, 29));
			jButton.setText("打开Dialog");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTest dgTest=new DgTest(FmTest.this);
					dgTest.setModal(false);
					dgTest.setVisible(true);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jDesktopPane	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getJDesktopPane() {
		if (jDesktopPane == null) {
			jDesktopPane = new JDesktopPane();
			jDesktopPane.setBounds(new Rectangle(173, 263, 880, 293));
			jDesktopPane.add(getJInternalFrame(), null);
			jDesktopPane.add(getJInternalFrame1(), null);
		}
		return jDesktopPane;
	}

	/**
	 * This method initializes jInternalFrame	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getJInternalFrame() {
		if (jInternalFrame == null) {
			jInternalFrame = new JInternalFrame();
			jInternalFrame.setBounds(new Rectangle(52, 45, 463, 177));
			jInternalFrame.setTitle("aaaaaaaaaaaaa");
			
			jInternalFrame.setContentPane(getJContentPane1());
		}
		return jInternalFrame;
	}

	/**
	 * This method initializes jContentPane1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane1() {
		if (jContentPane1 == null) {
			jContentPane1 = new JPanel();
			jContentPane1.setLayout(new BorderLayout());
		}
		return jContentPane1;
	}

	/**
	 * This method initializes jInternalFrame1	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getJInternalFrame1() {
		if (jInternalFrame1 == null) {
			jInternalFrame1 = new JInternalFrame();
			jInternalFrame1.setBounds(new Rectangle(577, 55, 257, 179));
			jInternalFrame1.setTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			jInternalFrame1.setContentPane(getJContentPane2());
		}
		return jInternalFrame1;
	}

	/**
	 * This method initializes jContentPane2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane2() {
		if (jContentPane2 == null) {
			jContentPane2 = new JPanel();
			jContentPane2.setLayout(new BorderLayout());
		}
		return jContentPane2;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(246, 16, 242, 44));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jInternalFrame.setVisible(true);
					jInternalFrame1.setVisible(true);
//					try {
						jInternalFrame.setClosable(true);
						jInternalFrame.setIconifiable(true);
						jInternalFrame.setResizable(true);
//						jInternalFrame.setMaximum(true);
//					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new Rectangle(846, 54, 270, 163));
			jSplitPane.setDividerLocation(100);
			jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setDividerSize(5);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJButton5(), gridBagConstraints5);
			jPanel1.add(getJButton9(), gridBagConstraints);
			jPanel1.add(getJButton10(), gridBagConstraints1);
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJButton6(), gridBagConstraints6);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
		}
		return jButton5;
	}

	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
		}
		return jButton6;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(53, 98, 10, 10));
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jTabbedPane1	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setBounds(new Rectangle(40, 107, 428, 166));
			jTabbedPane1.addTab("66666", null, getJPanel3(), null);
			jTabbedPane1.addTab(null, null, getJPanel4(), null);
			jTabbedPane1.addTab("22222222222", null, getJScrollPane(), null);
			jTabbedPane1.addTab("11111111111111", null, getJSplitPane1(), null);
			jTabbedPane1.addTab(null, null, getJInternalFrame2(), null);
			jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.println("stateChanged()"); // TODO Auto-generated Event stub stateChanged()
				}
			});
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
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
			jPanel4.setLayout(new GridBagLayout());
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setEnabled(false);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jInternalFrame2	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getJInternalFrame2() {
		if (jInternalFrame2 == null) {
			jInternalFrame2 = new JInternalFrame();
			jInternalFrame2.setContentPane(getJContentPane3());
		}
		return jInternalFrame2;
	}

	/**
	 * This method initializes jContentPane3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane3() {
		if (jContentPane3 == null) {
			jContentPane3 = new JPanel();
			jContentPane3.setLayout(new BorderLayout());
		}
		return jContentPane3;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBounds(new Rectangle(507, 82, 10, 10));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.setBounds(new Rectangle(523, 65, 343, 213));
			jPanel5.add(getJToolBar(), BorderLayout.NORTH);
			jPanel5.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton8());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton7());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("1");
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("2");
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton7	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton8	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton9	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
		}
		return jButton9;
	}

	/**
	 * This method initializes jButton10	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
		}
		return jButton10;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	public static void main(String[] args){
		FmTest fmTest=new FmTest();
		fmTest.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public FmTest() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1192, 598);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(-1);
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
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJDesktopPane(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJSplitPane(), null);
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.add(getJTabbedPane1(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel5(), null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
