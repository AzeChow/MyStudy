package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableJDBCModel;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.constant.EncodeType;
import com.bestway.common.constant.FileType;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgDBToTxtRegionEdit extends JDialogBase {
	private static final long			serialVersionUID		= 1L;
	private javax.swing.JPanel			jContentPane			= null;
	private JSplitPane					jSplitPane				= null;
	private JPanel						jPanel					= null;
	private JPanel						jPanel1					= null;
	private JToolBar					jToolBar				= null;
	private JTable						jTable					= null;
	private JScrollPane					jScrollPane				= null;
	private JToolBar					jToolBar1				= null;
	private JButton						btnEdit					= null;
	private JButton						btnSave					= null;
	private JButton						btnClose				= null;
	private JPanel						jPanel2					= null;
	private JTextField					tfRegionName			= null;
	private JCustomFormattedTextField	tfExportRowNumber		= null;
	private JTextField					tfSrcJDBCDataSourceName	= null;
	private JTextField					tfSrcJDBCViewName		= null;
	private JComboBox					cbbGbkToBig5Flag		= null;
	private JTextField					tfNote					= null;
	private DataExportAction			dataExportAction		= null;
	private DBToTxtRegion				dbToTxtRegion			= null;
	private JTableListModel				tableModel				= null;
	private int							dataState				= DataState.BROWSE;
	private JLabel						lb2						= null;
	private JTextField					tfFilePath				= null;
	private JComboBox					cbbFileType				= null;
	private JLabel						jLabel6					= null;
	private JCheckBox					cbIsExistCaption		= null;
	private JLabel						jLabel7					= null;
	private JButton						btnSelectedFile			= null;
	private JButton						btnAdd					= null;
	private JButton						btnCancel				= null;
	private JDBCView					jdbcView				= null;
	private JButton						btnShowData				= null;
	private JComboBox					cbbEncoding				= null;
	private JLabel						jLabel8					= null;

	/**
	 * This is the default constructor
	 */
	public DgDBToTxtRegionEdit() {
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
				dbToTxtRegion = (DBToTxtRegion) tableModel.getCurrentRow(); // 格式
				jdbcView = dbToTxtRegion.getSrcJDBCView();
			}
			initTable(false);
			showData();
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
		this.setTitle("从DB导出到文本-----域编辑");
		this.setSize(697, 495);
		this.setContentPane(getJContentPane());
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
			jLabel7 = new JLabel();
			jLabel7.setText("导出文件格式  : ");
			jLabel7.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabel7.setForeground(new Color(255, 153, 51));
			jToolBar = new JToolBar();
			jToolBar.add(getBtnShowData());
			jToolBar.add(jLabel7);
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
			btnShowData.setText("显示视图数据");
			btnShowData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new showViewData().start();
				}
			});
		}
		return btnShowData;
	}

	class showViewData extends Thread {

		@Override
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();

			CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
					false, null, 0);
			CommonProgress.setMessage(flag, "正在执行 SQL 语句, 请稍后...");
			btnShowData.setEnabled(false);
			try {
				initTable(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex.getMessage());
			} finally {
				btnShowData.setEnabled(true);
				CommonProgress.closeProgressDialog(flag);
			}
		}
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
	 * This method initializes tfFilePath
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFilePath() {
		if (tfFilePath == null) {
			tfFilePath = new JTextField();
			tfFilePath.setBackground(Color.white);
			tfFilePath.setBounds(new Rectangle(174, 57, 284, 22));
		}
		return tfFilePath;
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

					DgDBToTxtRegionEdit.this.dispose();
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
			jLabel8.setBounds(new Rectangle(364, 6, 74, 22));
			jLabel8.setText("导出文件编码");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(95, 85, 76, 23));
			jLabel6.setText("导出文件类型");
			lb2 = new JLabel();
			lb2.setBounds(new Rectangle(95, 59, 75, 20));
			lb2.setText("导出文件路径");
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(95, 6, 45, 19);
			jLabel.setText("域名称");
			jLabel1.setBounds(486, 57, 74, 22);
			jLabel1.setText("从第几行导出");
			jLabel2.setBounds(95, 30, 72, 20);
			jLabel2.setText("数据源名称");
			jLabel3.setBounds(381, 30, 56, 21);
			jLabel3.setText("视图名称");
			jLabel4.setBounds(94, 117, 72, 22);
			jLabel4.setText("字符集转换");
			jLabel5.setBounds(380, 116, 47, 21);
			jLabel5.setText("域描述");
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfRegionName(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getTfExportRowNumber(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getTfSrcJDBCDataSourceName(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getTfSrcJDBCViewName(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getCbbGbkToBig5Flag(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getTfNote(), null);
			jPanel2.add(lb2, null);
			jPanel2.add(getTfFilePath(), null);
			jPanel2.add(getCbbFileType(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getCbIsExistCaption(), null);
			jPanel2.add(getBtnSelectedFile(), null);
			jPanel2.add(getCbbEncoding(), null);
			jPanel2.add(jLabel8, null);
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
			cbbEncoding.setBounds(new Rectangle(444, 6, 169, 22));
		}
		return cbbEncoding;
	}

	/**
	 * This method initializes cbbFileType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFileType() {
		if (cbbFileType == null) {
			cbbFileType = new JComboBox();
			cbbFileType.setBounds(new Rectangle(174, 85, 170, 22));
		}
		return cbbFileType;
	}

	/**
	 * This method initializes cbIsExportCaption
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsExistCaption() {
		if (cbIsExistCaption == null) {
			cbIsExistCaption = new JCheckBox();
			cbIsExistCaption.setSelected(true);
			cbIsExistCaption.setBounds(new Rectangle(375, 86, 238, 22));
			cbIsExistCaption.setText("是否有标题栏导出");
		}
		return cbIsExistCaption;
	}

	/**
	 * This method initializes btnSelectedFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectedFile() {
		if (btnSelectedFile == null) {
			btnSelectedFile = new JButton();
			btnSelectedFile.setBounds(new Rectangle(459, 57, 19, 21));
			btnSelectedFile.setText("...");
			btnSelectedFile
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
									DgDBToTxtRegionEdit.this, "选取导出文件");
							if (state == JFileChooser.APPROVE_OPTION) {
								File f = fileChooser.getSelectedFile();
								if (f != null) {
									if (f.isDirectory()) {
										tfFilePath.setText(f.getAbsolutePath()
												+ "/temp");
									} else {
										tfFilePath.setText(f.getAbsolutePath());
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
		return btnSelectedFile;
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
			tfRegionName.setBounds(174, 6, 170, 22);
		}
		return tfRegionName;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JCustomFormattedTextField getTfExportRowNumber() {
		if (tfExportRowNumber == null) {
			tfExportRowNumber = new JCustomFormattedTextField();
			tfExportRowNumber.setValue(new Double(1));
			tfExportRowNumber.setBackground(Color.white);
			tfExportRowNumber.setBounds(562, 57, 51, 22);
		}
		return tfExportRowNumber;
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
	private JTextField getTfSrcJDBCDataSourceName() {
		if (tfSrcJDBCDataSourceName == null) {
			tfSrcJDBCDataSourceName = new JTextField();
			tfSrcJDBCDataSourceName.setEditable(false);
			tfSrcJDBCDataSourceName.setBackground(Color.white);
			tfSrcJDBCDataSourceName.setBounds(174, 30, 170, 22);
		}
		return tfSrcJDBCDataSourceName;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfSrcJDBCViewName() {
		if (tfSrcJDBCViewName == null) {
			tfSrcJDBCViewName = new JTextField();
			tfSrcJDBCViewName.setEditable(false);
			tfSrcJDBCViewName.setBackground(Color.white);
			tfSrcJDBCViewName.setBounds(443, 30, 170, 22);
		}
		return tfSrcJDBCViewName;
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
			cbbGbkToBig5Flag.setBounds(173, 117, 170, 22);
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
			tfNote.setBounds(442, 116, 170, 22);
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

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public void setJdbcView(JDBCView jdbcView) {
		this.jdbcView = jdbcView;
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
		if (dataState == DataState.ADD) {
			tfSrcJDBCDataSourceName.setText(jdbcView.getJdbcDataSource()
					.getName());
			tfSrcJDBCViewName.setText(jdbcView.getName());
		} else {
			tfRegionName.setText(dbToTxtRegion.getRegionName());
			tfExportRowNumber.setValue(dbToTxtRegion.getExportRowNumber());
			tfNote.setText(dbToTxtRegion.getNote());
			tfFilePath.setText(dbToTxtRegion.getDestFilePath());
			cbIsExistCaption
					.setSelected(dbToTxtRegion.getIsExistCaption() == null ? false
							: dbToTxtRegion.getIsExistCaption());
			tfSrcJDBCDataSourceName.setText(dbToTxtRegion.getSrcJDBCView()
					.getJdbcDataSource().getName());
			tfSrcJDBCViewName.setText(dbToTxtRegion.getSrcJDBCView().getName());

			this.cbbEncoding.setSelectedItem(dbToTxtRegion.getEncoding());

			// 转换方式
			if (dbToTxtRegion.getGbkToBig5Flag() != null) {
				cbbGbkToBig5Flag.setSelectedIndex(ItemProperty.getIndexByCode(
						String.valueOf(this.dbToTxtRegion.getGbkToBig5Flag()),
						cbbGbkToBig5Flag));
			} else {
				cbbGbkToBig5Flag.setSelectedIndex(-1);
			}
			// 文件类型
			if (dbToTxtRegion.getFileType() != null) {
				cbbFileType.setSelectedIndex(ItemProperty
						.getIndexByCode(String.valueOf(this.dbToTxtRegion
								.getFileType()), cbbFileType));
			} else {
				cbbFileType.setSelectedIndex(-1);
			}
		}
	}

	private void emptyData() {
		tfRegionName.setText("");
		tfExportRowNumber.setValue(new Double(1));
		tfSrcJDBCDataSourceName.setText(jdbcView.getJdbcDataSource().getName());
		tfSrcJDBCViewName.setText(jdbcView.getName());
		tfNote.setText("");
		tfFilePath.setText("");
		cbIsExistCaption.setSelected(true);
		cbbGbkToBig5Flag.setSelectedIndex(0);
		cbbEncoding.setSelectedItem(null);
		cbbFileType.setSelectedIndex(0);
	}

	private void fillData() {
		dbToTxtRegion.setRegionName(tfRegionName.getText());
		dbToTxtRegion.setNote(tfNote.getText());
		dbToTxtRegion.setExportRowNumber(Double.valueOf(
				tfExportRowNumber.getValue().toString()).intValue());
		dbToTxtRegion.setSrcJDBCView(jdbcView);
		dbToTxtRegion.setDestFilePath(tfFilePath.getText());
		dbToTxtRegion.setIsExistCaption(cbIsExistCaption.isSelected());

		dbToTxtRegion.setEncoding((String) cbbEncoding.getSelectedItem());

		if (cbbGbkToBig5Flag.getSelectedItem() != null) {
			dbToTxtRegion.setGbkToBig5Flag(((ItemProperty) cbbGbkToBig5Flag
					.getSelectedItem()).getCode());
		}
		if (cbbFileType.getSelectedItem() != null) {
			dbToTxtRegion.setFileType(Integer
					.parseInt(((ItemProperty) cbbFileType.getSelectedItem())
							.getCode()));
		}
	}

	private boolean validateData() {
		if (tfRegionName.getText().equals("")) {
			JOptionPane.showMessageDialog(DgDBToTxtRegionEdit.this, "请填写域名称！",
					"确认", 2);
			return false;
		}
		if (tfFilePath.getText().equals("")) {
			JOptionPane.showMessageDialog(DgDBToTxtRegionEdit.this,
					"请填写导出的文件路径！", "确认", 2);
			return false;
		}
		if (tfFilePath.getText().equals("")) {
			JOptionPane.showMessageDialog(DgDBToTxtRegionEdit.this,
					"请填写导出的文件路径！", "确认", 2);
			return false;
		}
		File file = new File(tfFilePath.getText());
		if (file.isDirectory()) {
			JOptionPane.showMessageDialog(DgDBToTxtRegionEdit.this,
					"导出的文件类型不可为目录！", "确认", 2);
			return false;
		}
		return true;
	}

	private void saveData() {
		if (dataState == DataState.ADD) {
			dbToTxtRegion = new DBToTxtRegion();
			fillData();
			dbToTxtRegion = dataExportAction.saveDBToTxtRegion(new Request(
					CommonVars.getCurrUser()), dbToTxtRegion);
			tableModel.addRow(dbToTxtRegion);
		} else if (dataState == DataState.EDIT) {
			fillData();
			dbToTxtRegion = dataExportAction.saveDBToTxtRegion(new Request(
					CommonVars.getCurrUser()), dbToTxtRegion);
			tableModel.updateRow(dbToTxtRegion);
		}
	}

	private void initTable(boolean isShowData) {
		if (jdbcView == null) {
			return;
		}
		if (isShowData) {
			TempResultSet rs = this.dataExportAction.getTempResultSet(
					new Request(CommonVars.getCurrUser()), jdbcView
							.getJdbcDataSource(), jdbcView.getSqlScript());
			new JTableJDBCModel(jTable, rs.getColumnNames(), rs.getRows());
		} else {
			TempResultSet rs = this.dataExportAction.getNoDataTempResultSet(
					new Request(CommonVars.getCurrUser()), jdbcView
							.getJdbcDataSource(), jdbcView.getSqlScript());
			new JTableJDBCModel(jTable, rs.getColumnNames(), rs.getRows());
		}

	}

	private void setState() {
		btnEdit.setEnabled(dataState == DataState.BROWSE);
		btnSave.setEnabled(!(dataState == DataState.BROWSE));
		btnAdd.setEnabled(dataState == DataState.BROWSE);
		btnCancel.setEnabled(!(dataState == DataState.BROWSE)
				&& dbToTxtRegion != null);

		tfRegionName.setEditable(!(dataState == DataState.BROWSE));
		cbbGbkToBig5Flag.setEnabled(!(dataState == DataState.BROWSE));
		tfNote.setEditable(!(dataState == DataState.BROWSE));

		tfRegionName.setEditable(!(dataState == DataState.BROWSE));
		tfExportRowNumber.setEditable(!(dataState == DataState.BROWSE));
		tfNote.setEditable(!(dataState == DataState.BROWSE));
		tfFilePath.setEditable(!(dataState == DataState.BROWSE));
		cbIsExistCaption.setEnabled(!(dataState == DataState.BROWSE));
		cbbGbkToBig5Flag.setEnabled(!(dataState == DataState.BROWSE));
		cbbFileType.setEnabled(!(dataState == DataState.BROWSE));
		btnSelectedFile.setEnabled(!(dataState == DataState.BROWSE));
		cbbEncoding.setEnabled(!(dataState == DataState.BROWSE));
	}

	

}
