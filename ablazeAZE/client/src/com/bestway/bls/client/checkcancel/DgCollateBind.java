/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.CollateBindDetail;
import com.bestway.bls.entity.FormType;
import com.bestway.bls.entity.ImpExp;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.CheckRange;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.message.logic.DzscMessageLogic;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.ComponentOrientation;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgCollateBind extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private Integer imExp; // @jve:decl-index=0:

	public Integer getImExp() {
		return imExp;
	}

	public void setImExp(Integer imExp) {
		this.imExp = imExp;
	}

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JTextField tfFormID = null;

	private JTextField tfNote = null;

	private JTable jTableImg = null;

	private JScrollPane jScrollPane = null;

	private JButton btnClose = null;

	private JButton btnSave = null;

	private DzscContractCavAction dzscContractCavAction = null;

	private DzscAction dzscAction = null;

	private CollateBind collateBind = null; // 中期核查表头 // @jve:decl-index=0:

	private CollateBind collateBindNew = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelImg = null;

	private JLabel jLabel8 = null;

	private JLabel lbApplyType = null;

	private JComboBox cbbFormType = null;

	private DzscMessageLogic dzscExportMessageLogic = null;

	private MaterialApplyAction materialApplyAction = null;

	private JLabel jLabel7 = null;

	private JTextField tfDeclareState = null;

	private JButton btnUp = null;

	private JButton btnDown = null;

	private BlsCheckCancelAction blsCheckCancelAction = null;

	private JLabel lbApplyType1 = null;

	private JComboBox cbbCollateFormType = null;
	public MaterialManageAction materialManageAction = null;

	private JTextField tfBrief = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar1 = null;

	private Integer dataState2 = DataState.EDIT; // @jve:decl-index=0:

	private JButton btnEdit1 = null;

	public CollateBind getCollateBindNew() {
		return collateBindNew;
	}

	public void setCollateBindNew(CollateBind collateBindNew) {
		this.collateBindNew = collateBindNew;
	}

	/**
	 * This is the default constructor
	 */
	public DgCollateBind(Integer imp) {
		super();
		this.imExp = imp;
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
			if (dataState == DataState.EDIT) {
				if (tableModel.getCurrentRow() != null) {
					collateBind = (CollateBind) tableModel.getCurrentRow();
				}
				showData();
				// List dataSourceImg = this.blsCheckCancelAction
				// .findCollateBindDetailByHead(new Request(CommonVars
				// .getCurrUser()), collateBind.getId());
				// initTableImg(dataSourceImg);
				setState();
				serchImg();
			} else if (dataState == DataState.ADD) {
				showData();
				setState();
				serchImg();
			} else if (dataState == DataState.BROWSE) {
				if (tableModel.getCurrentRow() != null) {
					collateBind = (CollateBind) tableModel.getCurrentRow();
				}
				showData();
				setState();
				serchImg();
			}

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
		this.setSize(563, 507);
		this.setContentPane(getJContentPane());

	}

	/** init detail */
	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTableImg, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "gno", 100));
						list.add(addColumn("备注", "note", 120));

						// list.add(addColumn("单证编号", "collateBind.formID",
						// 100));
						// list.add(addColumn("客户供应商",
						// "collateBind.scmCoc.name",
						// 100));
						list.add(addColumn("企业编码", "collateBind.brief.code",
								100));
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
		// DefaultComboBoxModel scmCocs = new DefaultComboBoxModel();
		// List list = this.materialManageAction.findScmCocs(new Request(
		// CommonVars.getCurrUser(), true));
		// scmCocs = new DefaultComboBoxModel(list.toArray());
		// this.cbbScmCoc.setModel(scmCocs);
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
		// "code", "name", 280);
		// this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
		// "code", "name", 110, 170));
		// this.cbbScmCoc.setSelectedItem(null);

		cbbFormType.addItem(new ItemProperty(FormType.IM_MFT, "入仓单"));
		cbbFormType.addItem(new ItemProperty(FormType.EX_MFT, "出仓单"));
		// cbbFormType.addItem(new ItemProperty(FormType.IM_ENT, "入仓报关单"));
		// cbbFormType.addItem(new ItemProperty(FormType.EX_ENT, "出仓报关单"));
		cbbFormType.setSelectedItem(null);

		cbbCollateFormType.addItem(new ItemProperty(FormType.IM_MFT, "入仓单"));
		cbbCollateFormType.addItem(new ItemProperty(FormType.EX_MFT, "出仓单"));
		cbbCollateFormType.addItem(new ItemProperty(FormType.IM_ENT, "入仓报关单"));
		cbbCollateFormType.addItem(new ItemProperty(FormType.EX_ENT, "出仓报关单"));
		cbbCollateFormType.setSelectedItem(null);
	}

	// newObj = this.blsCheckCancelAction.newCollateBind(new Request(
	// CommonVars.getCurrUser()), newObj);
	/** show data */
	private void showData() {
		if (dataState == DataState.EDIT) {
			collateBind = (CollateBind) tableModel.getCurrentRow();
			this.tfDeclareState.setText(DeclareState
					.getDeclareStateSpec(collateBind.getDeclareState()));
			this.tfFormID.setText(collateBind.getFormID());
			this.tfNote.setText(collateBind.getNote());
			this.tfBrief.setText(collateBind.getBrief() == null ? ""
					: collateBind.getBrief().getCode());

			int anIndex = ItemProperty.getIndexByCode(collateBind
					.getCollateFormType(), cbbCollateFormType);
			this.cbbCollateFormType.setSelectedIndex(anIndex);

			int anIndex2 = ItemProperty.getIndexByCode(collateBind
					.getFormType(), cbbFormType);
			this.cbbFormType.setSelectedIndex(anIndex2);
		} else if (dataState == DataState.ADD) {
			this.tfDeclareState.setText(DeclareState
					.getDeclareStateSpec(collateBindNew.getDeclareState()));
			this.tfFormID.setText(collateBindNew.getFormID());
			this.tfNote.setText(collateBindNew.getNote());
			this.tfBrief.setText(collateBindNew.getBrief() == null ? ""
					: collateBindNew.getBrief().getCode());

			int anIndex = ItemProperty.getIndexByCode(collateBindNew
					.getCollateFormType(), cbbCollateFormType);
			this.cbbCollateFormType.setSelectedIndex(anIndex);

			int anIndex2 = ItemProperty.getIndexByCode(collateBindNew
					.getFormType(), cbbFormType);
			this.cbbFormType.setSelectedIndex(anIndex2);
		} else if (dataState == DataState.BROWSE) {
			collateBind = (CollateBind) tableModel.getCurrentRow();
			this.tfDeclareState.setText(DeclareState
					.getDeclareStateSpec(collateBind.getDeclareState()));
			this.tfFormID.setText(collateBind.getFormID());
			this.tfNote.setText(collateBind.getNote());
			this.tfBrief.setText(collateBind.getBrief() == null ? ""
					: collateBind.getBrief().getCode());

			int anIndex = ItemProperty.getIndexByCode(collateBind
					.getCollateFormType(), cbbCollateFormType);
			this.cbbCollateFormType.setSelectedIndex(anIndex);

			int anIndex2 = ItemProperty.getIndexByCode(collateBind
					.getFormType(), cbbFormType);
			this.cbbFormType.setSelectedIndex(anIndex2);
		}

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			// jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
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
					if (dataState2 == DataState.EDIT) {
						System.out.println("DataState.EDIT");
						CollateBindDetail newObj = blsCheckCancelAction
								.newCollateBindDetail(new Request(CommonVars
										.getCurrUser()), collateBind);
						tableModelImg.addRow(newObj);
					} else if (dataState2 == DataState.ADD) {
						System.out.println("DataState.ADD");
						CollateBindDetail newObj = blsCheckCancelAction
								.newCollateBindDetail(new Request(CommonVars
										.getCurrUser()), collateBindNew);
						tableModelImg.addRow(newObj);
					} else if (dataState2 == DataState.BROWSE) {
						System.out.println("DataState.ADD");
						CollateBindDetail newObj = blsCheckCancelAction
								.newCollateBindDetail(new Request(CommonVars
										.getCurrUser()), collateBindNew);
						tableModelImg.addRow(newObj);
					}

					System.out.println("dataState=" + dataState);
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
					// if (jTabbedPane.getSelectedIndex() == 0) {
					dataState = DataState.EDIT;
					setState();
					// } else if (jTabbedPane.getSelectedIndex() == 1) {
					// if (tableModelImg.getCurrentRow() == null) {
					// JOptionPane.showMessageDialog(DgCollateBind.this,
					// "请选择你将要修改的记录", "提示！", 0);
					// return;
					// }
					// DgCollateBindDetail dgCheckImg = new
					// DgCollateBindDetail();
					// dgCheckImg.setTableModel(tableModelImg);
					// dgCheckImg.setDataState(DataState.EDIT);
					// dgCheckImg.setVisible(true);
					// }
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

					// if (jTabbedPane.getSelectedIndex() == 1) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgCollateBind.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					List checkHeads = tableModelImg.getCurrentRows();

					if (JOptionPane.showConfirmDialog(DgCollateBind.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
						// dzscContractCavAction.deleteAllCheckImgExg(new
						// Request(
						// CommonVars.getCurrUser()),checkHead);
						blsCheckCancelAction.deleteCollateBindDetail(
								new Request(CommonVars.getCurrUser()),
								checkHeads);
						tableModelImg.deleteRows(checkHeads);
						setState();
					}
					// }
				}
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
			// jTabbedPane.addTab("核销捆绑关系基本信息", null, getJPanel(), null);
			// jTabbedPane.addTab("核销捆绑关系货物项信息", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (DgCollateBind.this.jTabbedPane
									.getSelectedIndex() == 1) {

							}
							setState();
						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * 
	 */
	private void serchImg() {
		List dataSourceImg = null;
		if (dataState == DataState.EDIT && collateBind != null) {
			dataSourceImg = blsCheckCancelAction.findCollateBindDetailByHead(
					new Request(CommonVars.getCurrUser()), collateBind.getId());
			initTableImg(dataSourceImg);
		} else if (dataState == DataState.ADD) {
			dataSourceImg = new ArrayList();
			initTableImg(dataSourceImg);
		} else if (dataState == DataState.BROWSE) {
			dataSourceImg = blsCheckCancelAction.findCollateBindDetailByHead(
					new Request(CommonVars.getCurrUser()), collateBind.getId());
			initTableImg(dataSourceImg);
		}
		// if (collateBind.getId() != null) {
		// dataSourceImg = blsCheckCancelAction.findCollateBindDetailByHead(
		// new Request(CommonVars.getCurrUser()), collateBind.getId());
		// initTableImg(dataSourceImg);
		// } else {
		// dataSourceImg = new ArrayList();
		// initTableImg(dataSourceImg);
		// }
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
			lbApplyType1 = new JLabel();
			lbApplyType1.setBounds(new Rectangle(16, 118, 83, 22));
			lbApplyType1.setForeground(Color.blue);
			lbApplyType1
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbApplyType1.setText("核销单证类型");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(297, 28, 63, 22));
			jLabel7.setForeground(Color.blue);
			jLabel7.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel7.setText("申报状态");
			lbApplyType = new JLabel();
			lbApplyType.setBounds(new Rectangle(298, 73, 62, 22));
			lbApplyType.setForeground(Color.blue);
			lbApplyType
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lbApplyType.setText("单证类型");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(23, 73, 76, 22));
			jLabel8.setForeground(Color.blue);
			jLabel8.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel8.setText("企业编码");
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(37, 28, 62, 22);
			jLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel.setForeground(Color.blue);
			if (this.imExp == ImpExp.IMP) {
				jLabel.setText("入仓单号");
			} else if (this.imExp == ImpExp.EXP) {
				jLabel.setText("出仓单号");
			} else if (this.imExp == ImpExp.IMP_EXP) {
				jLabel.setText("出仓单号");
			}
			jLabel3.setBounds(299, 118, 61, 22);
			jLabel3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel3.setForeground(new Color(51, 51, 51));
			jLabel3.setText("备注");
			jPanel.add(jLabel, null);
			jPanel.add(getTfFormID(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfNote(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(lbApplyType, null);
			jPanel.add(getCbbFormType(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfDeclareState(), null);
			jPanel.add(lbApplyType1, null);
			jPanel.add(getCbbCollateFormType(), null);
			jPanel.add(getTfBrief(), null);
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
	 * This method initializes tfFormID
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfFormID() {
		if (tfFormID == null) {
			tfFormID = new JTextField();
			tfFormID.setBounds(113, 28, 159, 22);
		}
		return tfFormID;
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
			tfNote.setBounds(366, 118, 159, 22);
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
						DgCollateBindDetail dgCheckImg = new DgCollateBindDetail();
						dgCheckImg.setTableModel(tableModelImg);
						dgCheckImg.setDataState(DataState.BROWSE);
						dgCheckImg.setImExp(imExp);
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

					DgCollateBind.this.dispose();

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
		// if (cbbScmCoc.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(null, "项目类型不可为空!!", "警告",
		// JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		if (this.cbbCollateFormType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "核销单证类型不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbFormType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "单证类型不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfFormID.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "单证编号不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/** fill data */
	private void fillData() {
		if (dataState == DataState.EDIT) {
			collateBind.setFormID(this.tfFormID.getText());
			collateBind.setNote(this.tfNote.getText());
			// collateBind.setBrief((ScmCoc) this.cbbScmCoc.getSelectedItem());

			ItemProperty a = (ItemProperty) this.cbbCollateFormType
					.getSelectedItem();
			if (a == null) {
				collateBind.setCollateFormType(null);
			} else {
				collateBind.setCollateFormType(a.getCode());
			}

			ItemProperty b = (ItemProperty) this.cbbFormType.getSelectedItem();
			if (b == null) {
				collateBind.setFormType(null);
			} else {
				collateBind.setFormType(b.getCode());
			}
		} else if (dataState == DataState.ADD) {
			collateBindNew.setFormID(this.tfFormID.getText());
			collateBindNew.setNote(this.tfNote.getText());
			// collateBind.setBrief((ScmCoc) this.cbbScmCoc.getSelectedItem());

			ItemProperty a = (ItemProperty) this.cbbCollateFormType
					.getSelectedItem();
			if (a == null) {
				collateBindNew.setCollateFormType(null);
			} else {
				collateBindNew.setCollateFormType(a.getCode());
			}

			ItemProperty b = (ItemProperty) this.cbbFormType.getSelectedItem();
			if (b == null) {
				collateBindNew.setFormType(null);
			} else {
				collateBindNew.setFormType(b.getCode());
			}
		}

	}

	/** save data */
	private void saveData() {
		fillData();
		if (dataState == DataState.EDIT) {
			dataState2 = 2;
			collateBind = blsCheckCancelAction.saveCollateBind(new Request(
					CommonVars.getCurrUser()), collateBind);
			tableModel.updateRow(collateBind);
			System.out.println("ddddddd");
		} else if (dataState == DataState.ADD) {
			dataState2 = 1;
			collateBindNew = this.blsCheckCancelAction.newCollateBind(
					new Request(CommonVars.getCurrUser()), collateBindNew);
			tableModel.addRow(collateBindNew);
			if (collateBindNew != null) {
				System.out.println("eeeeee");
			}
		} else if (dataState == DataState.BROWSE) {
			dataState2 = 0;
			collateBindNew = this.blsCheckCancelAction.newCollateBind(
					new Request(CommonVars.getCurrUser()), collateBindNew);
			tableModel.addRow(collateBindNew);
			if (collateBindNew != null) {
				System.out.println("eeeeee");
			}
		}
		// tableModel.updateRow(collateBind);
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
		if (collateBind != null) {
			state = collateBind.getDeclareState();
		}
		this.btnUp.setEnabled(tableModel.hasPreviousRow());
		this.btnDown.setEnabled(tableModel.hasNextRow());

		this.tfFormID.setEnabled(dataState != DataState.BROWSE);
		this.tfNote.setEnabled(dataState != DataState.BROWSE);
		// this.cbbScmCoc.setEnabled(dataState != DataState.BROWSE);
		this.cbbCollateFormType.setEnabled(dataState != DataState.BROWSE);
		this.cbbFormType.setEnabled(dataState != DataState.BROWSE);

		btnAdd.setEnabled(DeclareState.APPLY_POR.equals(state)); // 新增
		btnEdit.setEnabled((dataState == DataState.BROWSE)
				&& (DeclareState.APPLY_POR.equals(state)));
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(state)); // 删除
		btnSave.setEnabled(dataState != DataState.BROWSE
				&& (DeclareState.APPLY_POR.equals(state))); // 保存
		btnEdit1.setEnabled(DeclareState.APPLY_POR.equals(state));
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
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFormType() {
		if (cbbFormType == null) {
			cbbFormType = new JComboBox();
			cbbFormType.setBounds(new Rectangle(366, 73, 159, 22));
		}
		return cbbFormType;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareState() {
		if (tfDeclareState == null) {
			tfDeclareState = new JTextField();
			tfDeclareState.setEnabled(false);
			tfDeclareState.setBounds(new Rectangle(366, 28, 159, 22));
		}
		return tfDeclareState;
	}

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
					//dataState = DataState.EDIT;
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
					//dataState = DataState.EDIT;
					setState();
					serchImg();
				}
			});
		}
		return btnDown;
	}

	/**
	 * This method initializes cbbCollateFormType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCollateFormType() {
		if (cbbCollateFormType == null) {
			cbbCollateFormType = new JComboBox();
			cbbCollateFormType.setBounds(new Rectangle(113, 118, 159, 22));
		}
		return cbbCollateFormType;
	}

	/**
	 * This method initializes tfBrief
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBrief() {
		if (tfBrief == null) {
			tfBrief = new JTextField();
			tfBrief.setEnabled(false);
			tfBrief.setBounds(new Rectangle(113, 73, 159, 22));
		}
		return tfBrief;
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
			jPanel2.add(getJPanel3(), BorderLayout.NORTH);
			jPanel2.add(getJPanel1(), BorderLayout.CENTER);
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
			jPanel3.add(getJPanel(), BorderLayout.CENTER);
			jPanel3.setPreferredSize(new Dimension(1, 160));
		}
		return jPanel3;
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
			jToolBar1.add(getBtnDelete());
			jToolBar1.add(getBtnEdit1());
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
				public void actionPerformed(java.awt.event.ActionEvent e) {//					
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgCollateBind.this,
								"请选择你将要修改的记录", "提示！", 0);
						return;
					}
					DgCollateBindDetail dgCheckImg = new DgCollateBindDetail();
					dgCheckImg.setTableModel(tableModelImg);
					dgCheckImg.setDataState(DataState.EDIT);
					dgCheckImg.setImExp(imExp);
					dgCheckImg.setVisible(true);
				}
			});
		}
		return btnEdit1;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
