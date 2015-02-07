package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class DgEmsHeadH2kBomExport extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JTextField tfBeginSeqNo = null;
	private JLabel jLabel1 = null;
	private JTextField tfEndSeqNo = null;
	private JButton btnSearch = null;
	private JButton btnClose = null;
	private EmsHeadH2k head = null;  //  @jve:decl-index=0:
	private JTableListModel tableModel = null;
	private ManualDeclareAction manualDecleareAction = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JComboBox cbbExgModifyMark = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel21 = null;
	private JComboBox cbbBomModifyMark = null;
	private JRadioButton jRadioButtonBigBOM = null;
	private boolean jRadioButtonBigBOMState=false;
	private JCheckBox jCheckBoxBigBOM = null;
	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kBomExport() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(898, 410);
		this.setContentPane(getJContentPane());
		this.setTitle("电子帐册管理BOM");
		initTable(new Vector());
		ininCompent();
	}

	private void ininCompent() {
		this.cbbExgModifyMark.addItem(new ItemProperty("3", "新增"));
		this.cbbExgModifyMark.addItem(new ItemProperty("0", "未修改"));
		this.cbbExgModifyMark.addItem(new ItemProperty("1", "已修改"));
		this.cbbExgModifyMark.addItem(new ItemProperty("2", "已删除"));
		cbbExgModifyMark.setSelectedIndex(-1);

		this.cbbBomModifyMark.addItem(new ItemProperty("3", "新增"));
		this.cbbBomModifyMark.addItem(new ItemProperty("0", "未修改"));
		this.cbbBomModifyMark.addItem(new ItemProperty("1", "已修改"));
		this.cbbBomModifyMark.addItem(new ItemProperty("2", "已删除"));
		cbbBomModifyMark.setSelectedIndex(-1);

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单耗修改标志",
								"emsHeadH2kVersion.emsHeadH2kExg.modifyMark",
								60));
						list.add(addColumn("成品归并序号",
								"emsHeadH2kVersion.emsHeadH2kExg.seqNum", 90,
								Integer.class));
						list.add(addColumn("成品商品编码",
								"emsHeadH2kVersion.emsHeadH2kExg.complex.code",
								100));
						list.add(addColumn("版本号", "emsHeadH2kVersion.version",
								60));
						list.add(addColumn("成品名称",
								"emsHeadH2kVersion.emsHeadH2kExg.name", 100));
						list.add(addColumn("成品规格",
								"emsHeadH2kVersion.emsHeadH2kExg.spec", 100));
						list
								.add(addColumn(
										"成品单位",
										"emsHeadH2kVersion.emsHeadH2kExg.unit.name",
										60));
						list.add(addColumn("单耗修改标志", "modifyMark", 70));
						list.add(addColumn("料件归并序号", "seqNum", 100));
						list.add(addColumn("料件商品编码", "complex.code", 100));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("料件规格", "spec", 100));
						list.add(addColumn("料件单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWear", 80));
						list.add(addColumn("损耗", "wear", 80));
						return list;
					}
				});
		EmsEdiMergerClientLogic.transModifyMark(jTable);
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "未修改";
						} else if (value.equals("1")) {
							returnValue = "已修改";
						} else if (value.equals("2")) {
							returnValue = "已删除";
						} else if (value.equals("3")) {
							returnValue = "新增";
						}
						return returnValue;
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(19, 35));
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(434, 6, 72, 18));
			jLabel21.setText("单耗修改标志");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(254, 4, 85, 18));
			jLabel2.setText("成品修改标志");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(161, 2, 23, 20));
			jLabel1.setText("至");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(11, 4, 76, 21));
			jLabel.setText("成品归并序号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfBeginSeqNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfEndSeqNo(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getCbbExgModifyMark(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel21, null);
			jPanel.add(getCbbBomModifyMark(), null);
			jPanel.add(getJCheckBoxBigBOM(), null);
			jPanel.add(getJCheckBoxBigBOM(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfBeginSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBeginSeqNo() {
		if (tfBeginSeqNo == null) {
			tfBeginSeqNo = new JTextField();
			tfBeginSeqNo.setBounds(new Rectangle(89, 3, 70, 20));
		}
		return tfBeginSeqNo;
	}

	/**
	 * This method initializes tfEndSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndSeqNo() {
		if (tfEndSeqNo == null) {
			tfEndSeqNo = new JTextField();
			tfEndSeqNo.setBounds(new Rectangle(181, 3, 71, 21));
		}
		return tfEndSeqNo;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(new Rectangle(735, 5, 69, 21));
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (tfBeginSeqNo.getText().equals("")
					// || tfEndSeqNo.getText().equals("")) {
					// return;
					// }
					if (tfBeginSeqNo.getText() != null
							&& !"".equals(tfBeginSeqNo.getText())) {
						try {
							Integer.valueOf(tfBeginSeqNo.getText());
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(
									DgEmsHeadH2kBomExport.this, "成品归并序号不合法",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					if (tfEndSeqNo.getText() != null
							&& !"".equals(tfEndSeqNo.getText())) {
						try {
							Integer.valueOf(tfEndSeqNo.getText());
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(
									DgEmsHeadH2kBomExport.this, "成品归并序号不合法",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					String beginSeqNo = tfBeginSeqNo.getText();
					String endSeqNo = tfEndSeqNo.getText();
					String exgModifyMark = cbbExgModifyMark.getSelectedItem() == null ? ""
							: ((ItemProperty) cbbExgModifyMark
									.getSelectedItem()).getCode();
					String bomModifyMark = cbbBomModifyMark.getSelectedItem() == null ? ""
							: ((ItemProperty) cbbBomModifyMark
									.getSelectedItem()).getCode();
					new bomexport(exgModifyMark, bomModifyMark, beginSeqNo,
							endSeqNo,jRadioButtonBigBOMState).start();
				}
			});
		}
		return btnSearch;
	}

	class bomexport extends Thread {

		private String exgModifyMark;
		private String bomModifyMark;
		private String beginSeqNo;
		private String endSeqNo;
		private boolean jRadioButtonBigBOMState1;
		public bomexport(String exgModifyMark, String bomModifyMark,
				String beginSeqNo, String endSeqNo,boolean jRadioButtonBigBOMState) {
			this.exgModifyMark = exgModifyMark;
			this.bomModifyMark = bomModifyMark;
			this.beginSeqNo = beginSeqNo;
			this.endSeqNo = endSeqNo;
			this.jRadioButtonBigBOMState1=jRadioButtonBigBOMState;
		}

		public void run() {
			List list = new Vector();
			try {
				CommonProgress.showProgressDialog(DgEmsHeadH2kBomExport.this);
				CommonProgress.setMessage("系统正在查询资料，请稍后...");
				list = manualDecleareAction.findEmsHeadH2kBomExport(
						new Request(CommonVars.getCurrUser()), head,
						beginSeqNo, endSeqNo, exgModifyMark, bomModifyMark,jRadioButtonBigBOMState1);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsHeadH2kBomExport.this,
						"查询失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTable(list);
			}
		}
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(807, 5, 71, 21));
			btnClose.setText("退出");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsHeadH2kBomExport.this.dispose();
				}
			});
		}
		return btnClose;
	}

	public void setHead(EmsHeadH2k head) {
		this.head = head;
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

	/**
	 * This method initializes cbbExgModifyMark
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExgModifyMark() {
		if (cbbExgModifyMark == null) {
			cbbExgModifyMark = new JComboBox();
			cbbExgModifyMark.setBounds(new Rectangle(332, 2, 97, 23));
		}
		return cbbExgModifyMark;
	}

	/**
	 * This method initializes cbbBomModifyMark
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBomModifyMark() {
		if (cbbBomModifyMark == null) {
			cbbBomModifyMark = new JComboBox();
			cbbBomModifyMark.setBounds(new Rectangle(510, 5, 95, 20));
		}
		return cbbBomModifyMark;
	}


	/**
	 * This method initializes jCheckBoxBigBOM	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxBigBOM() {
		if (jCheckBoxBigBOM == null) {
			jCheckBoxBigBOM = new JCheckBox();
			jCheckBoxBigBOM.setText("是否显示最大版本");
			jCheckBoxBigBOM.setBounds(new Rectangle(609, 5, 126, 21));
			jCheckBoxBigBOM.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						jRadioButtonBigBOMState=true;
					}else{
						jRadioButtonBigBOMState=false;
					}
				}
			});
		}
		return jCheckBoxBigBOM;
	}

} // @jve:decl-index=0:visual-constraint="9,16"
