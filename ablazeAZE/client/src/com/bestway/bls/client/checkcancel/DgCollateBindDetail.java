/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.checkcancel;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.entity.CollateBindDetail;
import com.bestway.bls.entity.CollateBindDetailList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.message.logic.DzscMessageLogic;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgCollateBindDetail extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JCustomFormattedTextField tfGno = null;

	private JTextField tfNote = null;

	private JTable jTableImg = null;

	private JScrollPane jScrollPane = null;

	private JButton btnClose = null;

	private JButton btnSave = null;

	private DzscContractCavAction dzscContractCavAction = null;

	private DzscAction dzscAction = null;

	private CollateBindDetail collateBindDetail = null;  //  @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelImg = null;

	private DzscMessageLogic dzscExportMessageLogic = null;

	private MaterialApplyAction materialApplyAction = null;

	private JButton btnUp = null;

	private JButton btnDown = null;

	private BlsCheckCancelAction blsCheckCancelAction = null;

	public MaterialManageAction materialManageAction = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar1 = null;

	private JButton btnEdit1 = null;

	private Integer imExp; // @jve:decl-index=0:

	public Integer getImExp() {
		return imExp;
	}

	public void setImExp(Integer imExp) {
		this.imExp = imExp;
	}
	/**
	 * This is the default constructor
	 */
	public DgCollateBindDetail() {
		super();
		blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
				.getApplicationContext().getBean("blsCheckCancelAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dzscContractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initUIComponents();
			if (tableModel.getCurrentRow() != null) {
				collateBindDetail = (CollateBindDetail) tableModel.getCurrentRow();
			}
			showData();
			// List dataSourceImg = this.blsCheckCancelAction
			// .findCollateBindDetailByHead(new Request(CommonVars
			// .getCurrUser()), CollateBindDetail.getId());
			// initTableImg(dataSourceImg);
			setState();
			this.serchImg();
		}
		super.setVisible(b);
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("核销捆绑关系货物项信息维护");
		this.setSize(654, 474);
		this.setContentPane(getJContentPane());

	}

	/** init detail */
	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTableImg, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单证编号", "formId", 100));
						list.add(addColumn("货物序号", "gno",
								100));
						list.add(addColumn("货物数量", "gcount",
								100));
						list.add(addColumn("企业编码","tradeCode", 100));
						list.add(addColumn("核扣单位","unit.name", 100));
						list.add(addColumn("备注","note", 100));
						return list;
					}
				});
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initUIComponents() {
		// List<Company> companies = this.systemAction.findCompanies();
		DefaultComboBoxModel scmCocs = new DefaultComboBoxModel();
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		scmCocs = new DefaultComboBoxModel(list.toArray());
//		this.cbbScmCoc.setModel(scmCocs);
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
//				"code", "name", 280);
//		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
//				"code", "name", 110, 170));
//		this.cbbScmCoc.setSelectedItem(null);

//		cbbFormType.addItem(new ItemProperty(FormType.IM_MFT, "入仓单"));
//		cbbFormType.addItem(new ItemProperty(FormType.EX_MFT, "出仓单"));
//		cbbFormType.addItem(new ItemProperty(FormType.IM_ENT, "入仓报关单"));
//		cbbFormType.addItem(new ItemProperty(FormType.EX_ENT, "出仓报关单"));
//		cbbFormType.setSelectedItem(null);

//		cbbCollateFormType.addItem(new ItemProperty(FormType.IM_MFT, "入仓单"));
//		cbbCollateFormType.addItem(new ItemProperty(FormType.EX_MFT, "出仓单"));
//		cbbCollateFormType.addItem(new ItemProperty(FormType.IM_ENT, "入仓报关单"));
//		cbbCollateFormType.addItem(new ItemProperty(FormType.EX_ENT, "出仓报关单"));
//		cbbCollateFormType.setSelectedItem(null);
	}

	/** show data */
	private void showData() {
//		this.tfDeclareState.setText(DeclareState
//				.getDeclareStateSpec(CollateBindDetail.getDeclareState()));
		collateBindDetail = (CollateBindDetail) tableModel.getCurrentRow();
		Integer gno = collateBindDetail.getGno()==null?0:collateBindDetail.getGno();
		this.tfGno.setValue(gno);
		this.tfNote.setText(collateBindDetail.getNote());
//		this.cbbScmCoc.setSelectedItem(CollateBindDetail.getScmCoc());

//		int anIndex = ItemProperty.getIndexByCode(CollateBindDetail
//				.getCollateFormType(), cbbCollateFormType);
//		this.cbbCollateFormType.setSelectedIndex(anIndex);
//
//		int anIndex2 = ItemProperty.getIndexByCode(CollateBindDetail.getFormType(),
//				cbbFormType);
//		this.cbbFormType.setSelectedIndex(anIndex2);

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			//jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jContentPane;
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
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnUp());
			jToolBar.add(getBtnDown());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

//					if (jTabbedPane.getSelectedIndex() == 1) {
						CollateBindDetailList newObj = blsCheckCancelAction
								.newCollateBindDetailList(new Request(CommonVars
										.getCurrUser()), collateBindDetail);
						tableModelImg.addRow(newObj);
//					}
					setState();
				}
			});

		}
		return btnAdd;
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
//					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						setState();
//					} else if (jTabbedPane.getSelectedIndex() == 1) {
//						if (tableModelImg.getCurrentRow() == null) {
//							JOptionPane.showMessageDialog(DgCollateBindDetail.this,
//									"请选择你将要修改的记录", "提示！", 0);
//							return;
//						}
//						DgCollateBindDetailList dgCheckImg = new DgCollateBindDetailList();
//						dgCheckImg.setTableModel(tableModelImg);	
//						dgCheckImg.setDataState(DataState.EDIT);
//						dgCheckImg.setVisible(true);
//					}
				}
			});

		}
		return btnEdit;
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
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

//					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgCollateBindDetail.this,
									"请选择你要删除的资料", "确认", 1);
							return;
						}

						List checkHeads = tableModelImg.getCurrentRows();

						if (JOptionPane.showConfirmDialog(DgCollateBindDetail.this,
								"确定要删除此记录吗?\n", "确认", 0) == 0) {
							// dzscContractCavAction.deleteAllCheckImgExg(new
							// Request(
							// CommonVars.getCurrUser()),checkHead);
							blsCheckCancelAction.deleteCollateBindDetailList(
									new Request(CommonVars.getCurrUser()),
									checkHeads);
							tableModelImg.deleteRows(checkHeads);
							setState();
						}
					}
//				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(1);
//			jTabbedPane.addTab("核销捆绑关系--货物项信息", null, getJPanel(), null);
//			jTabbedPane.addTab("核销捆绑关系--核销项信息", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (DgCollateBindDetail.this.jTabbedPane
									.getSelectedIndex() == 1) {
								List dataSourceImg = blsCheckCancelAction
										.findCollateBindDetailListByDetail(
												new Request(CommonVars
														.getCurrUser()),
												collateBindDetail.getId());
								initTableImg(dataSourceImg);
							}
							setState();
						}
					});

		}
		return jTabbedPane;
	}
	
	private void serchImg() {
		List dataSourceImg = blsCheckCancelAction
				.findCollateBindDetailListByDetail(new Request(CommonVars
						.getCurrUser()), collateBindDetail.getId());
		initTableImg(dataSourceImg);
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
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setPreferredSize(new Dimension(1,120					));
			jPanel.setLayout(null);
			jLabel.setBounds(36, 31, 62, 22);
			jLabel.setForeground(Color.blue);
			jLabel.setText("序号");
			jLabel3.setBounds(35, 72, 61, 22);
			jLabel3.setText("备注");
			jPanel.add(jLabel, null);
			jPanel.add(getTfGno(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfNote(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJToolBar1(), BorderLayout.NORTH);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes tfGno
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JCustomFormattedTextField getTfGno() {
		if (tfGno == null) {
			tfGno = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGno.setBounds(104, 31, 386, 22);
		}
		return tfGno;
	}

	/**
	 * 
	 * This method initializes tfNote
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(103, 72, 385, 22);
		}
		return tfNote;
	}

	/**
	 * 
	 * This method initializes jTableImg
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTableImg() {
		if (jTableImg == null) {
			jTableImg = new JTable();
			jTableImg.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						DgCollateBindDetailList dgCheckImg = new DgCollateBindDetailList(imExp);
						dgCheckImg.setTableModel(tableModelImg);	
						dgCheckImg.setDataState(DataState.BROWSE);
						dgCheckImg.setVisible(true);
					}
				}
			});
		}
		return jTableImg;
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
			jScrollPane.setViewportView(getJTableImg());
		}
		return jScrollPane;
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

					DgCollateBindDetail.this.dispose();

				}
			});

		}
		return btnClose;
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
					if (!vaildatorDataIsNull()) {
						saveData();
					}
				}
			});

		}
		return btnSave;
	}

	private boolean vaildatorDataIsNull() {
//		if (cbbScmCoc.getSelectedItem() == null) {
//			JOptionPane.showMessageDialog(null, "项目类型不可为空!!", "警告",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
//		if (this.cbbCollateFormType.getSelectedItem() == null) {
//			JOptionPane.showMessageDialog(null, "核销单证类型不可为空!!", "警告",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
//		if (this.cbbFormType.getSelectedItem() == null) {
//			JOptionPane.showMessageDialog(null, "单证类型不可为空!!", "警告",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
		if (this.tfGno.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "序号不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/** fill data */
	private void fillData() {

		collateBindDetail.setGno(Integer.valueOf(this.tfGno.getValue().toString()));
		collateBindDetail.setNote(this.tfNote.getText());
//		CollateBindDetail.setScmCoc((ScmCoc) this.cbbScmCoc.getSelectedItem());
//
//		ItemProperty a = (ItemProperty) this.cbbCollateFormType
//				.getSelectedItem();
//		if (a == null) {
//			CollateBindDetail.setCollateFormType(null);
//		} else {
//			CollateBindDetail.setCollateFormType(a.getCode());
//		}
//
//		ItemProperty b = (ItemProperty) this.cbbFormType.getSelectedItem();
//		if (b == null) {
//			CollateBindDetail.setFormType(null);
//		} else {
//			CollateBindDetail.setFormType(b.getCode());
//		}
	}

	/** save data */
	private void saveData() {
		fillData();
		collateBindDetail = blsCheckCancelAction.saveCollateBindDetail(new Request(
				CommonVars.getCurrUser()), collateBindDetail);
		tableModel.updateRow(collateBindDetail);
		dataState = DataState.BROWSE;
		setState();
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
	 * @return Returns the tableModelImg.
	 */
	public JTableListModel getTableModelImg() {
		return tableModelImg;
	}

	/**
	 * @param tableModelImg
	 *            The tableModelImg to set.
	 */
	public void setTableModelImg(JTableListModel tableModelImg) {
		this.tableModelImg = tableModelImg;
	}

	private void setState() {
		String state = DeclareState.APPLY_POR;
		if (collateBindDetail != null) {
			state = collateBindDetail.getCollateBind().getDeclareState();
		}
		this.btnUp.setEnabled(tableModel.hasPreviousRow());
		this.btnDown.setEnabled(tableModel.hasNextRow());

		this.tfGno.setEnabled(dataState != DataState.BROWSE);
		this.tfNote.setEnabled(dataState != DataState.BROWSE);
//		this.cbbScmCoc.setEnabled(dataState != DataState.BROWSE);
//		this.cbbCollateFormType.setEnabled(dataState != DataState.BROWSE);
//		this.cbbFormType.setEnabled(dataState != DataState.BROWSE);

		btnAdd.setEnabled(DeclareState.APPLY_POR.equals(state)); // 新增
		btnEdit
				.setEnabled(dataState == DataState.BROWSE 
						&& (DeclareState.APPLY_POR.equals(state)));
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(state)); // 删除
		btnSave.setEnabled(dataState != DataState.BROWSE
				&& (DeclareState.APPLY_POR.equals(state))); // 保存
		btnEdit1
		.setEnabled(DeclareState.APPLY_POR.equals(state));
	}

	// private boolean isImgExgPageAndExistData() {
	// if ((jTabbedPane.getSelectedIndex() == 1)
	// && (tableModelImg.getRowCount() > 0)) {
	// return true;
	// }
	// if ((jTabbedPane.getSelectedIndex() == 2)
	// && (tableModelExg.getRowCount() > 0)) {
	// return true;
	// }
	// return false;
	// }

	/**
	 * This method initializes btnUp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton();
			btnUp.setText("上笔");
			btnUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnUp.setEnabled(false);
						btnDown.setEnabled(true);
					} else {
						btnUp.setEnabled(true);
						btnDown.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
					serchImg();
				}
			});
		}
		return btnUp;
	}

	/**
	 * This method initializes btnDown
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDown() {
		if (btnDown == null) {
			btnDown = new JButton();
			btnDown.setText("下笔");
			btnDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnUp.setEnabled(true);
						btnDown.setEnabled(false);
					} else {
						btnUp.setEnabled(true);
						btnDown.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
					serchImg();
				}
			});
		}
		return btnDown;
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
			jPanel2.add(getJPanel(),BorderLayout.NORTH);
			jPanel2.add(getJPanel1(),BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAdd());
			jToolBar1.add(getBtnEdit1());
			jToolBar1.add(getBtnDelete());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnEdit1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit1() {
		if (btnEdit1 == null) {
			btnEdit1 = new JButton();
			btnEdit1.setText("修改");
			btnEdit1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {					
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgCollateBindDetail.this,
									"请选择你将要修改的记录", "提示！", 0);
							return;
						}
						DgCollateBindDetailList dgCheckImg = new DgCollateBindDetailList(imExp);
						dgCheckImg.setTableModel(tableModelImg);	
						dgCheckImg.setDataState(DataState.EDIT);
						dgCheckImg.setVisible(true);
				}
			});
		}
		return btnEdit1;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
