/**
 * @author luosheng 2006/9/1
 * 
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.EncodeType;
import com.bestway.common.constant.FileType;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.constant.ImportDataMode;
import com.bestway.common.constant.JDBCFlag;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.dataexport.entity.TxtToDBRegionSetup;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class DgTxtToDBRegionEdit extends JDialogBase {
	private static final long			serialVersionUID			= 1L;
	private javax.swing.JPanel			jContentPane				= null;
	private JSplitPane					jSplitPane					= null;
	private JPanel						jPanel						= null;
	private JPanel						jPanel1						= null;
	private JToolBar					jToolBar					= null;
	private JButton						btnEditRegionSetup			= null;
	private JTable						jTable						= null;
	private JScrollPane					jScrollPane					= null;
	private JToolBar					jToolBar1					= null;
	private JButton						btnEdit						= null;
	private JButton						btnSave						= null;
	private JButton						btnClose					= null;
	private JPanel						jPanel2						= null;
	private JTextField					tfRegionName				= null;
	private JTextField					tfDestTableName				= null;
	private JTextField					tfSrcFilePath				= null;
	private JComboBox					cbbGbkToBig5Flag			= null;
	private JTextField					tfNote						= null;
	private DataExportAction			dataExportAction			= null;
	private TxtToDBRegion				txtToDBRegion				= null;
	private JTableListModel				tableModel					= null;
	private JTableListModel				tableModel1					= null;
	private int							dataState					= DataState.BROWSE;
	private JButton						btnDelete					= null;
	private JLabel						lb2							= null;
	private JTextField					tfDestJDBCDataSourceName	= null;
	private JLabel						jLabel6						= null;
	private JLabel						jLabel7						= null;
	private JButton						btnSelectedDestTableName	= null;
	private JCustomFormattedTextField	tfImportRowNumber			= null;
	private JButton						btnSelectedSrcFilePath		= null;
	private JComboBox					cbbFileType					= null;
	private JComboBox					cbbDataImportMode			= null;
	private JButton						btnAdd						= null;
	private JButton						btnCancel					= null;
	private JDBCDataSource				destJDBCDataSource			= null;
	private JLabel						jLabel8						= null;
	private JComboBox					cbbEncoding					= null;
	private JButton						btnShowData					= null;

	/**
	 * This is the default constructor
	 */
	public DgTxtToDBRegionEdit() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		initUIComponents();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
				txtToDBRegion = (TxtToDBRegion) tableModel.getCurrentRow(); // 格式
				destJDBCDataSource = txtToDBRegion.getDestJDBCDataSource();
				showData();
			}else{
				initTable(new ArrayList());
			}
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("文本导出到 DB 域编辑");
		this.setSize(697, 495);
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
		this.setContentPane(getJContentPane());
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(185);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEditRegionSetup());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnShowData());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnShowData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowData() {
		if (btnShowData == null) {
			btnShowData = new JButton();
			btnShowData.setText("显示源文件数据");
			btnShowData.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (txtToDBRegion == null) {
						JOptionPane.showMessageDialog(DgTxtToDBRegionEdit.this,
								"域对象不能为空！", "提示", 2);
						return;
					}
					File file = new File(txtToDBRegion.getSrcFilePath());
					if (!file.exists()) {
						JOptionPane.showMessageDialog(DgTxtToDBRegionEdit.this,
								"导出的文件不在！", "确认", 2);
						return;
					}
					if (file.isDirectory()) {
						JOptionPane.showMessageDialog(DgTxtToDBRegionEdit.this,
								"导出的文件类型不可为目录！", "确认", 2);
						return;
					}
					DgTxtToDBShowData dg = new DgTxtToDBShowData();
					dg.setTxtToDBRegion(txtToDBRegion);
					dg.setVisible(true);
				}
			});
		}
		return btnShowData;
	}

	/**
	 * 
	 * This method initializes btnEditRegionSetup
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEditRegionSetup() {
		if (btnEditRegionSetup == null) {
			btnEditRegionSetup = new JButton();
			btnEditRegionSetup.setText("编辑对应关系");
			btnEditRegionSetup
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (txtToDBRegion == null) {
								JOptionPane.showMessageDialog(
										DgTxtToDBRegionEdit.this, "域对象不能为空！",
										"提示", 2);
								return;
							}
							DgTxtToDBRegionSetupEdit dg = new DgTxtToDBRegionSetupEdit();
							dg.setTableModel1(tableModel1);
							dg.setTxtToDBRegion(txtToDBRegion);
							dg.setVisible(true);
						}
					});

		}
		return btnEditRegionSetup;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jToolBar1
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAdd());
			jToolBar1.add(getBtnEdit());
			jToolBar1.add(getBtnSave());
			jToolBar1.add(getBtnCancel());
			jToolBar1.add(getBtnClose());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes tfDestJDBCDataSourceName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDestJDBCDataSourceName() {
		if (tfDestJDBCDataSourceName == null) {
			tfDestJDBCDataSourceName = new JTextField();
			tfDestJDBCDataSourceName.setBackground(Color.white);
			tfDestJDBCDataSourceName.setBounds(new Rectangle(459, 88, 160, 22));
			tfDestJDBCDataSourceName.setEditable(false);
		}
		return tfDestJDBCDataSourceName;
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					saveData();
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgTxtToDBRegionEdit.this.dispose();
				}
			});

		}
		return btnClose;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(385, 6, 85, 19));
			jLabel8.setText("导入文件编码");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(490, 30, 77, 22));
			jLabel7.setText("从第几行导入");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(95, 59, 75, 22));
			jLabel6.setText("导入文件类型");
			lb2 = new JLabel();
			lb2.setBounds(new Rectangle(386, 88, 70, 22));
			lb2.setText("目的数据源");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(95, 3, 45, 22);
			jLabel.setText("域名称");
			jLabel1.setBounds(95, 88, 56, 22);
			jLabel1.setText("目标表名");
			jLabel2.setBounds(95, 30, 72, 22);
			jLabel2.setText("源文件路径");
			jLabel3.setBounds(399, 59, 56, 22);
			jLabel3.setText("导入方式");
			jLabel4.setBounds(95, 113, 72, 22);
			jLabel4.setText("字符集转换");
			jLabel5.setBounds(405, 116, 47, 22);
			jLabel5.setText("域描述");
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfRegionName(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getTfDestTableName(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getTfSrcFilePath(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getCbbGbkToBig5Flag(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getTfNote(), null);
			jPanel2.add(lb2, null);
			jPanel2.add(getTfDestJDBCDataSourceName(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getBtnSelectedDestTableName(), null);
			jPanel2.add(getTfImportRowNumber(), null);
			jPanel2.add(getBtnSelectedSrcFilePath(), null);
			jPanel2.add(getCbbFileType(), null);
			jPanel2.add(getCbbDataImportMode(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(getCbbEncoding(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbEncoding
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEncoding() {
		if (cbbEncoding == null) {
			cbbEncoding = new JComboBox();
			cbbEncoding.setBounds(new Rectangle(476, 5, 141, 21));
		}
		return cbbEncoding;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfRegionName() {
		if (tfRegionName == null) {
			tfRegionName = new JTextField();
			tfRegionName.setBounds(175, 3, 170, 22);
		}
		return tfRegionName;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfDestTableName() {
		if (tfDestTableName == null) {
			tfDestTableName = new JTextField();
			// tfDestTableName.setEditable(false);
			tfDestTableName.setBackground(Color.white);
			tfDestTableName.setBounds(175, 88, 151, 22);
		}
		return tfDestTableName;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSrcFilePath() {
		if (tfSrcFilePath == null) {
			tfSrcFilePath = new JTextField();
			tfSrcFilePath.setBackground(Color.white);
			tfSrcFilePath.setBounds(175, 30, 283, 22);
		}
		return tfSrcFilePath;
	}

	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbGbkToBig5Flag() {
		if (cbbGbkToBig5Flag == null) {
			cbbGbkToBig5Flag = new JComboBox();
			cbbGbkToBig5Flag.setBounds(175, 113, 170, 22);
		}
		return cbbGbkToBig5Flag;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(459, 115, 219, 22);
		}
		return tfNote;
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
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return tableModel1;
	}

	/**
	 * @param tableModel1
	 *            The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		this.tableModel1 = tableModel1;
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除对应关系");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtToDBRegionEdit.this,
								"请选中字段对应！", "提示", 2);
						return;
					}
					TxtToDBRegionSetup obj = (TxtToDBRegionSetup) tableModel1
							.getCurrentRow();
					dataExportAction.deleteTxtToDBRegionSetup(new Request(
							CommonVars.getCurrUser()), obj);
					tableModel1.deleteRow(obj);

				}
			});

		}
		return btnDelete;
	}

	/**
	 * This method initializes btnSelectedDestTableName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectedDestTableName() {
		if (btnSelectedDestTableName == null) {
			btnSelectedDestTableName = new JButton();
			btnSelectedDestTableName.setBounds(new Rectangle(326, 88, 19, 21));
			btnSelectedDestTableName.setText("...");
			btnSelectedDestTableName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgTxtToDBDestTableName dg = new DgTxtToDBDestTableName();
							dg.setVisible(true);
							if (dg.isOk()) {
								tfDestTableName.setText(dg.getDestTableName());
								destJDBCDataSource = dg.getDestJDBCDataSource();
								tfDestJDBCDataSourceName
										.setText(destJDBCDataSource.getName());
							}
						}
					});
		}
		return btnSelectedDestTableName;
	}

	/**
	 * This method initializes tfImportRowNumber
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfImportRowNumber() {
		if (tfImportRowNumber == null) {
			tfImportRowNumber = new JCustomFormattedTextField();
			tfImportRowNumber.setValue(new Double(1));
			tfImportRowNumber.setBackground(Color.white);
			tfImportRowNumber.setBounds(new Rectangle(571, 30, 47, 22));
		}
		return tfImportRowNumber;
	}

	/**
	 * This method initializes btnSelectedSrcFilePath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectedSrcFilePath() {
		if (btnSelectedSrcFilePath == null) {
			btnSelectedSrcFilePath = new JButton();
			btnSelectedSrcFilePath.setBounds(new Rectangle(456, 30, 21, 21));
			btnSelectedSrcFilePath.setText("...");
			btnSelectedSrcFilePath
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							
							JFileChooser fileChooser = new JFileChooser("./");

							fileChooser.removeChoosableFileFilter(fileChooser
									.getFileFilter());
							fileChooser
									.addChoosableFileFilter(new ExampleFileFilter(
											"txt"));
							fileChooser
									.addChoosableFileFilter(new ExampleFileFilter(
											"xls"));
							fileChooser
									.setFileSelectionMode(JFileChooser.FILES_ONLY);
							int state = fileChooser.showDialog(
									DgTxtToDBRegionEdit.this, "选取导入文件");
							if (state == JFileChooser.APPROVE_OPTION) {
								File f = fileChooser.getSelectedFile();
								if (f != null) {
									String filePath = f.getAbsolutePath();
									tfSrcFilePath.setText(filePath);
									if (filePath.toLowerCase().trim().endsWith(
											"xls")) {
										cbbFileType
												.setSelectedIndex(ItemProperty
														.getIndexByCode(
																String
																		.valueOf(FileType.XLS),
																cbbFileType));
									} else if (filePath.toLowerCase().trim()
											.endsWith("txt")) {
										cbbFileType
												.setSelectedIndex(ItemProperty
														.getIndexByCode(
																String
																		.valueOf(FileType.TXT),
																cbbFileType));
									}
								}
							}
						}

						class ExampleFileFilter extends FileFilter {
							String	suffix	= "";

							ExampleFileFilter(String suffix) {
								this.suffix = suffix;
							}

							@Override
							public boolean accept(File f) {
								String suffix = getSuffix(f);
								if (f.isDirectory() == true) {
									return true;
								}
								if (suffix != null) {
									if (suffix.toLowerCase()
											.equals(this.suffix)) {
										return true;
									} else {
										return false;
									}
								} else {
									return false;
								}
							}

							@Override
							public String getDescription() {
								return "*." + this.suffix;
							}

							private String getSuffix(File f) {
								String s = f.getPath(), suffix = null;
								int i = s.lastIndexOf('.');
								if (i > 0 && i < s.length() - 1)
									suffix = s.substring(i + 1).toLowerCase();
								return suffix;
							}
						}
					});
		}
		return btnSelectedSrcFilePath;
	}

	/**
	 * This method initializes cbbFileType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFileType() {
		if (cbbFileType == null) {
			cbbFileType = new JComboBox();
			cbbFileType.setBounds(new Rectangle(175, 59, 170, 23));
		}
		return cbbFileType;
	}

	/**
	 * This method initializes cbbDataImportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDataImportMode() {
		if (cbbDataImportMode == null) {
			cbbDataImportMode = new JComboBox();
			cbbDataImportMode.setBounds(new Rectangle(459, 59, 160, 23));
		}
		return cbbDataImportMode;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.ADD;
					emptyData();
					setState();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					setState();
					showData();
				}
			});
		}
		return btnCancel;
	}

	private void initUIComponents() {
		// 转换
		this.cbbGbkToBig5Flag.removeAllItems();
		this.cbbGbkToBig5Flag
				.addItem(new ItemProperty(GbkToBig5Flag.NO, "不转换"));
		this.cbbGbkToBig5Flag.addItem(new ItemProperty(
				GbkToBig5Flag.BIG5_TO_GBK, "繁转简"));
		this.cbbGbkToBig5Flag.addItem(new ItemProperty(
				GbkToBig5Flag.GBK_TO_BIG5, "简转繁"));
		this.cbbGbkToBig5Flag.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbGbkToBig5Flag);

		// 文件类型
		this.cbbFileType.removeAllItems();
		this.cbbFileType.addItem(new ItemProperty(String.valueOf(FileType.XLS),
				"EXCEL格式(xls)"));
		this.cbbFileType.addItem(new ItemProperty(String.valueOf(FileType.TXT),
				"文本格式(txt)"));
		this.cbbFileType
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFileType);
		// 导入方式
		this.cbbDataImportMode.removeAllItems();
		this.cbbDataImportMode.addItem(new ItemProperty(String
				.valueOf(ImportDataMode.ADD_MODE), "新增导入"));
		this.cbbDataImportMode.addItem(new ItemProperty(String
				.valueOf(ImportDataMode.MODIFY_MODE), "更新导入"));
		this.cbbDataImportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDataImportMode);
		// 文件编码
		this.cbbEncoding.removeAllItems();
		this.cbbEncoding.addItem(EncodeType.ISO_8859_1);
		this.cbbEncoding.addItem(EncodeType.UTF_8);
		this.cbbEncoding.addItem(EncodeType.GBK);
		this.cbbEncoding.addItem(EncodeType.BIG5);
		this.cbbEncoding.addItem(EncodeType.ASCII);
		this.cbbEncoding.setSelectedItem(null);
		this.cbbEncoding.setEditable(true);
	}

	private void showData() {
		tfRegionName.setText(txtToDBRegion.getRegionName());
		tfDestTableName.setText(txtToDBRegion.getDestTableName());
		tfSrcFilePath.setText(txtToDBRegion.getSrcFilePath());
		tfImportRowNumber.setValue(txtToDBRegion.getImportRowNumber());
		tfNote.setText(txtToDBRegion.getNote());
		if (txtToDBRegion.getDestJDBCDataSource() != null) {
			tfDestJDBCDataSourceName.setText(txtToDBRegion
					.getDestJDBCDataSource().getName());
			destJDBCDataSource = txtToDBRegion.getDestJDBCDataSource();
		}
		// 转换方式
		if (txtToDBRegion.getGbkToBig5Flag() != null) {
			cbbGbkToBig5Flag.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(this.txtToDBRegion.getGbkToBig5Flag()),
					cbbGbkToBig5Flag));
		} else {
			cbbGbkToBig5Flag.setSelectedIndex(-1);
		}

		// 文件类型
		if (txtToDBRegion.getFileType() != null) {
			cbbFileType.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.txtToDBRegion.getFileType()), cbbFileType));
		} else {
			cbbFileType.setSelectedIndex(-1);
		}

		this.cbbEncoding.setSelectedItem(txtToDBRegion.getEncoding());

		this.cbbDataImportMode.setSelectedIndex(ItemProperty.getIndexByCode(
				String.valueOf(txtToDBRegion.getImportDataMode()),
				cbbDataImportMode));

		List<TxtToDBRegionSetup> list = dataExportAction
				.findTxtToDBRegionSetup(new Request(CommonVars.getCurrUser()),
						txtToDBRegion.getId());
		initTable(list);
	}

	private void emptyData() {
		tfRegionName.setText("");
		tfImportRowNumber.setValue(new Double(1));
		tfSrcFilePath.setText("");
		tfDestTableName.setText("");
		tfNote.setText("");
		tfDestJDBCDataSourceName.setText("");
		cbbDataImportMode.setSelectedIndex(0);
		cbbGbkToBig5Flag.setSelectedIndex(0);
		cbbFileType.setSelectedIndex(0);
		destJDBCDataSource = null;
		cbbEncoding.setSelectedItem(null);
		initTable(new ArrayList());
	}

	private void fillData() {
		txtToDBRegion.setRegionName(tfRegionName.getText());
		txtToDBRegion.setNote(tfNote.getText());
		txtToDBRegion.setSrcFilePath(tfSrcFilePath.getText());
		txtToDBRegion.setDestTableName(tfDestTableName.getText());
		txtToDBRegion.setDestJDBCDataSource(destJDBCDataSource);
		txtToDBRegion.setImportRowNumber(Double.valueOf(
				tfImportRowNumber.getValue().toString()).intValue());
		txtToDBRegion.setEncoding((String) cbbEncoding.getSelectedItem());

		if (cbbGbkToBig5Flag.getSelectedItem() != null) {
			txtToDBRegion.setGbkToBig5Flag(((ItemProperty) cbbGbkToBig5Flag
					.getSelectedItem()).getCode());
		}
		if (cbbFileType.getSelectedItem() != null) {
			txtToDBRegion.setFileType(Integer
					.parseInt(((ItemProperty) cbbFileType.getSelectedItem())
							.getCode()));
		}
		txtToDBRegion.setImportDataMode(Integer
				.parseInt(((ItemProperty) cbbDataImportMode.getSelectedItem())
						.getCode()));
	}

	private boolean validateData() {
		if (tfRegionName.getText().equals("")) {
			JOptionPane.showMessageDialog(DgTxtToDBRegionEdit.this, "请填写域名称！",
					"确认", 2);
			return false;
		}
		if (tfDestTableName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "请填写目标表名！", "确认", 2);
			return false;
		}
		if (destJDBCDataSource == null) {
			JOptionPane.showMessageDialog(this, "目的数据源不可为空！", "确认", 2);
			return false;
		}
		if (tfSrcFilePath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "请填写导出的文件路径！", "确认", 2);
			return false;
		}
		if (tfSrcFilePath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "请填写导出的文件路径！", "确认", 2);
			return false;
		}
		File file = new File(tfSrcFilePath.getText());
		if (!file.exists()) {
			JOptionPane.showMessageDialog(this, "导出的文件不在！", "确认", 2);
			return false;
		}
		if (file.isDirectory()) {
			JOptionPane.showMessageDialog(this, "导出的文件类型不可为目录！", "确认", 2);
			return false;
		}
		return true;
	}

	private void saveData() {

		if (dataState == DataState.ADD) {
			txtToDBRegion = new TxtToDBRegion();
			fillData();
			txtToDBRegion = dataExportAction.saveTxtToDBRegion(new Request(
					CommonVars.getCurrUser()), txtToDBRegion);
			tableModel.addRow(txtToDBRegion);
		} else if (dataState == DataState.EDIT) {
			fillData();
			txtToDBRegion = dataExportAction.saveTxtToDBRegion(new Request(
					CommonVars.getCurrUser()), txtToDBRegion);
			tableModel.updateRow(txtToDBRegion);
		}
	}

	private void initTable(List dataSource) {
		tableModel1 = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("目的表字段名", "destFieldName", 80));
						list
								.add(addColumn("目的表字段类型", "destFieldTypeDesc",
										100));
						list.add(addColumn("转换方式", "jdbcFlag", 60));
						list.add(addColumn("源文件字段名", "srcFieldName", 80));
						list.add(addColumn("目的表字段对应的SQL", "sqlStr", 150));
						list.add(addColumn("SQL是否使用缓存", "isCache", 100));
						list.add(addColumn("是否是判定唯一性的关键列", "isKey", 150));
						list.add(addColumn("是否是主键", "isPrimaryKey", 150));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";

						if (value.equals(JDBCFlag.NO)) {
							returnValue = "不转换";
						} else if (value.equals(JDBCFlag.CURRENT_COMPANY_ID)) {
							returnValue = "当前公司ID";
						} else if (value.equals(JDBCFlag.GUID)) {
							returnValue = "32位的GUID";
						} else if (value.equals(JDBCFlag.SQL)) {
							returnValue = "自定义SQL";
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new TableCheckBoxRender());
	}

	private void setState() {
		btnEdit.setEnabled(dataState == DataState.BROWSE);
		btnSave.setEnabled(!(dataState == DataState.BROWSE));
		btnAdd.setEnabled(dataState == DataState.BROWSE);
		btnCancel.setEnabled(!(dataState == DataState.BROWSE)
				&& txtToDBRegion != null);

		tfRegionName.setEditable(!(dataState == DataState.BROWSE));
		cbbGbkToBig5Flag.setEnabled(!(dataState == DataState.BROWSE));
		tfNote.setEditable(!(dataState == DataState.BROWSE));
		tfImportRowNumber.setEditable(!(dataState == DataState.BROWSE));
		tfSrcFilePath.setEditable(!(dataState == DataState.BROWSE));
		cbbFileType.setEnabled(!(dataState == DataState.BROWSE));
		tfDestTableName.setEnabled(!(dataState == DataState.BROWSE));
		cbbDataImportMode.setEnabled(!(dataState == DataState.BROWSE));
		btnSelectedDestTableName.setEnabled(!(dataState == DataState.BROWSE));
		btnSelectedSrcFilePath.setEnabled(!(dataState == DataState.BROWSE));
		cbbEncoding.setEnabled(!(dataState == DataState.BROWSE));

		btnEditRegionSetup.setEnabled(!(dataState == DataState.ADD));
		btnDelete.setEnabled(!(dataState == DataState.ADD));
		btnShowData.setEnabled(!(dataState == DataState.ADD));

	}

}
