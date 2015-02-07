package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmBcusParameterSet;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.client.custom.common.DgImportedBGDInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Rectangle;
import java.awt.CardLayout;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.Font;

public class DgImportedApplyToCustomsBillXML extends JDialogBase {

	private JPanel jContentPane = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel;

	private List lsSuccess; // @jve:decl-index=0:

	private List lsError; // @jve:decl-index=0:

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar = null;

	private JLabel jLabel = null;

	private JToolBar jToolBar1 = null;

	private JLabel jLabel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JPanel jPanel2 = null;

	private JButton btnImport = null;

	private JLabel lbTxt = null;

	private JTextField tfPath = null;

	private JButton btnPath = null;

	private JCheckBox cbTran = null;
	
	private boolean isIn=true;
	
/**
 * 保存路径用接口
 */
	private ManualDeclareAction manualDecleareAction = null;  //  @jve:decl-index=0:

public List getLsError() {
		return lsError;
	}

	public void setLsError(List lsError) {
		this.lsError = lsError;
		initErrorTable(lsError);
	}

	public List getLsSuccess() {
		return lsSuccess;
	}

	public boolean isIn() {
		return isIn;
	}

	public void setIn(boolean isIn) {
		this.isIn = isIn;
	}

	public void setLsSuccess(List dataSource) {
		this.lsSuccess = dataSource;
		initTable(lsSuccess);
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportedApplyToCustomsBillXML() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("导入报关清单");
		this.setBounds(new Rectangle(0, 0, 720, 424));
		this.setContentPane(getJContentPane());
		manualDecleareAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
		String bgdLoadDir = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.LOAD_APPLY_QP_BGD_DIR);
		tfPath.setText(bgdLoadDir);


	}

	public void setVisible(boolean b) {
		if (b) {
			if (lsSuccess == null) {
				lsSuccess = new ArrayList();
			}
			if (lsError == null) {
				lsError = new ArrayList();
			}
			initTable(lsSuccess);
			initErrorTable(lsError);
		}
		super.setVisible(b);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
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
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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

	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {

						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("文件名称", "fileName", 200));
						list.add(addColumn("进出口标志", "apply.impExpFlagToBW", 80));
						list.add(addColumn("清单号", "apply.listNo",
								150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Integer value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (value) {
							case ImpExpFlag.IMPORT:
								str = "进口报关清单(I)";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口报关清单(E)";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
	}

	private void initErrorTable(List list) {
		tableModel = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {

						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("文件名称", "fileName", 300));
						list.add(addColumn("进出口标志", "apply.impExpFlagToBW", 80));
						list.add(addColumn("错误信息", "errorInfo", 500));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Integer value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							switch (value) {
							case ImpExpFlag.IMPORT:
								str = "进口(I)";
								break;
							case ImpExpFlag.EXPORT:
								str = "出口(E)";
								break;
							}
						}
						this.setText(str);
						return this;
					}
				});
		
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerSize(2);
		}
		return jSplitPane;
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel1.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel
					.setText("导入成功的报关清单信息                                                                                                                 ");
			jLabel.setForeground(Color.blue);
			jToolBar = new JToolBar();
			jToolBar.setOrientation(JToolBar.HORIZONTAL);
			jToolBar.add(jLabel);
			jToolBar.setPreferredSize(new Dimension(Double.valueOf(
					jToolBar.getPreferredSize().getWidth()).intValue(),
					Double.valueOf(jLabel.getPreferredSize().getHeight())
							.intValue() + 5));
			// jButton.setPreferredSize(new Dimension(jButton.getWidth(), 5));
		}
		return jToolBar;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("导入失败的报关清单信息");
			jLabel1.setForeground(Color.blue);
			jToolBar1 = new JToolBar();
			jToolBar1.add(jLabel1);
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbTxt = new JLabel();
			lbTxt.setText("文件存放路径：");
			lbTxt.setName("lbTxt");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BoxLayout(getJPanel2(), BoxLayout.X_AXIS));
			jPanel2.add(getBtnImport(), null);
			jPanel2.add(lbTxt, null);
			jPanel2.add(getTfPath(), null);
			jPanel2.add(getBtnPath(), null);
			jPanel2.add(getCbTran(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("开始导入");
			btnImport.setName("btnImport");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					Object object =manualDecleareAction.loadBGDFromQPXml(new Request(CommonVars
							.getCurrUser()),isIn,cbTran.isSelected());
			Map mapBGD = (Map) object;
					List lsSuccess = (List) mapBGD.get(0);
					List lsError = (List) mapBGD.get(-1);
					if ((lsSuccess == null || lsSuccess.size() <= 0)
							&& (lsError == null || lsError.size() <= 0)) {
						JOptionPane.showMessageDialog(DgImportedApplyToCustomsBillXML.this, "没有导入任何清单！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						DgImportedApplyToCustomsBillXML.this.setLsSuccess(lsSuccess);
						DgImportedApplyToCustomsBillXML.this.setLsError(lsError);
						DgImportedApplyToCustomsBillXML.this.setVisible(true);
					}
					
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes tfPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPath() {
		if (tfPath == null) {
			tfPath = new JTextField();
			tfPath.setEditable(false);
			tfPath.setForeground(Color.red);
			tfPath.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return tfPath;
	}

	/**
	 * This method initializes btnPath	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPath() {
		if (btnPath == null) {
			btnPath = new JButton();
			btnPath.setText("修改路径");
			btnPath.setName("btnPath");
			btnPath.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String dir = "";
					if (tfPath.getText() != null
							&& !tfPath.getText().equals("")) {
						dir = tfPath.getText();
					} else {
						dir = "./";
					}
					JFileChooser fileChooser = new JFileChooser(dir);
					fileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int state = fileChooser.showDialog(DgImportedApplyToCustomsBillXML.this,
							"选择路径");
					if (state == JFileChooser.APPROVE_OPTION) {
						fileChooser.getSelectedFile();
						File f = fileChooser.getSelectedFile();
						tfPath.setText(f.getPath());
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.LOAD_APPLY_QP_BGD_DIR,
								tfPath.getText().trim());
					}
				}
			});
		}
		return btnPath;
	}

	/**
	 * This method initializes cbTran	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbTran() {
		if (cbTran == null) {
			cbTran = new JCheckBox();
			cbTran.setText("繁转简");
		}
		return cbTran;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
