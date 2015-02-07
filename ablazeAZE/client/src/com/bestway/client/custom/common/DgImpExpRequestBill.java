package com.bestway.client.custom.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.TempMateriel;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.dzsc.client.dzscmanage.DzscClientHelper;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings({ "unchecked", "serial" })
public class DgImpExpRequestBill extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JFooterTable jTable = null;

	private JFooterScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAddTop = null;

	private JButton btnEditTop = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JButton btnAddBottom = null;

	private JButton btnEditBottom = null;

	private JButton btnDelete = null;

	private JButton btnAvailability = null;

	private JButton btnCancelAvailability = null;

	private JTextField tfCommodityItem = null;

	private JTextField tfMaterielProductFlag = null;

	private JTextField tfConveyance = null;

	private JTextField tfBillNo = null;

	private JCalendarComboBox cbbBeginAvailabilityDate = null;

	private EncAction encAction = null;

	private List list = null; // @jve:decl-index=0:

	private JTableListModel commodityTableModel = null;

	private JTableListModel impExpRequestModel = null;

	private ImpExpRequestBill impExpRequestBill = null;

	// private JTableListModel tableModel = null;

	private int impExpType = -1;

	private int materielProductType = -1;

	private int billType = -1;

	private int dataState = -1;

	protected CommonBaseCodeAction commonBaseCodeAction = null;

	private MaterialManageAction materialManageAction = null;

	private JComboBox cbbScmCoc = null;

	private JComboBox cbbWareSet = null;

	private int projectType = ProjectType.BCUS;

	private JTextField tfContainerCode = null;

	private JLabel jLabel72 = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbIeport = null;

	private JButton btnClose = null;

	private JButton btnPrint = null;

	private Boolean isOut = false;// 进出口 // @jve:decl-index=0:

	private JLabel jLabel8 = null;

	private JComboBox cbbTransferMode = null;

	private ImpExpRequestBill headImpExpRequestBill = null; // @jve:decl-index=0:

	private JLabel lbMemos = null;

	private JTextField tfMemos = null;

	public ImpExpRequestBill getHeadImpExpRequestBill() {
		return headImpExpRequestBill;
	}

	public void setHeadImpExpRequestBill(ImpExpRequestBill headImpExpRequestBill) {
		this.headImpExpRequestBill = headImpExpRequestBill;
	}

	public Boolean getIsOut() {
		return isOut;
	}

	public void setIsOut(Boolean isOut) {
		this.isOut = isOut;
	}

	public JTableListModel getCommodityTableModel() {
		return commodityTableModel;
	}

	public JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(60, 30));
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}

			});
		}
		return btnClose;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImpExpRequestBill() {
		super();
		initialize();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		this.commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出口申请单");
		this.setContentPane(getJContentPane());
		this.setSize(619, 430);
		this.setResizable(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			try {
				initUIComponents();
				if (dataState == DataState.ADD) {
					initAddData();

				} else {// if (dataState == DataState.EDIT)
					impExpRequestBill = headImpExpRequestBill;
					showData(impExpRequestBill);
				}
				setState();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, ex.getMessage(), "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		super.setVisible(b);
	}

	public void showWithParams(String billNo, int impExpType, boolean isOut,
			int projectType, int materielProductType) {
		this.setImpExpType(impExpType);
		this.setProjectType(projectType);
		this.setMaterielProductType(materielProductType);
		this.setDataState(DataState.BROWSE);
		this.setIsOut(isOut);
		this.setHeadImpExpRequestBill(encAction.getImpExpRequestBill(
				new Request(CommonVars.getCurrUser()), billNo));
		this.setVisible(true);
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPane 集装箱号. jLabel71.setText("JLabel");
	 *         setText("JLabel"); l
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbMemos = new JLabel();
			lbMemos.setBounds(new Rectangle(232, 111, 69, 21));
			lbMemos.setText("  备         注 ");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(17, 110, 51, 22));
			jLabel8.setText("运输方式");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(232, 64, 70, 22));
			jLabel7.setText("进  出  口  岸");
			jLabel72 = new JLabel();
			jLabel72.setBounds(new Rectangle(17, 64, 51, 22));
			jLabel72.setText("集装箱号");
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("单据号");
			jLabel.setBounds(17, 40, 51, 22);
			jLabel1.setText("客户/供应商");
			jLabel1.setBounds(232, 40, 70, 22);
			jLabel2.setText("生  效  日  期");
			jLabel2.setBounds(232, 88, 70, 22);
			jLabel3.setText("运输工具");
			jLabel3.setBounds(17, 87, 51, 22);
			jLabel4.setText("商品项数");
			jLabel4.setBounds(447, 40, 55, 22);
			jLabel5.setText("物件/成品");
			jLabel5.setBounds(447, 64, 55, 22);
			jLabel6.setText("仓库名称");
			jLabel6.setBounds(447, 88, 55, 22);
			jLabel6.setVisible(false);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJToolBar1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getTfCommodityItem(), null);
			jContentPane.add(getTfMaterielProductFlag(), null);
			jContentPane.add(getTfConveyance(), null);
			jContentPane.add(getTfBillNo(), null);
			jContentPane.add(getCbbBeginAvailabilityDate(), null);
			jContentPane.add(getCbbScmCoc(), null);
			jContentPane.add(getCbbWareSet(), null);
			jContentPane.add(getTfContainer(), null);
			jContentPane.add(jLabel72, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getCbbIeport(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getCbbTransferMode(), null);
			jContentPane.add(lbMemos, null);
			jContentPane.add(getTfMemos(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * jCont jContentPane.add(jLabel71, null); entPane.add(集装箱号, null);
	 * 
	 * @return javax.sw jContentPane.add(jLabel7, null); ing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setBounds(0, 0, 609, 34);
			jToolBar.setFloatable(false);
			// jToolBar.setPreferredSize(new Dimension(60, 34));
			jToolBar.add(getBtnAddTop());
			jToolBar.add(getBtnEditTop());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnAvailability());
			jToolBar.add(getBtnCancelAvailability());
			// jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnClose());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JFooterTable getJTable() {
		if (jTable == null) {
			jTable = new JFooterTable();
			jTable.setRowHeight(25);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					// System.out.print("==="+e.getClickCount());
					if (e.getClickCount() == 2) {
						editImpExpCommodityInfo(DataState.BROWSE);
					}
				}
			});

			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (commodityTableModel == null)
						return;
					if (commodityTableModel.getCurrentRow() == null)
						return;
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						ImpExpCommodityInfo obj = (ImpExpCommodityInfo) commodityTableModel
								.getCurrentRow();
						if (obj.getIsTransferCustomsBill()) {
							obj.setIsTransferCustomsBill(false);
						} else {
							obj.setIsTransferCustomsBill(true);
						}
						obj = encAction.saveImpExpComInfo(new Request(
								CommonVars.getCurrUser()), obj);
						commodityTableModel.updateRow(obj);
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes cbbScoCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(305, 40, 137, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes cbbWareSet
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWareSet() {
		if (cbbWareSet == null) {
			cbbWareSet = new JComboBox();
			cbbWareSet.setBounds(new Rectangle(504, 88, 97, 22));
			cbbWareSet.setVisible(false);
		}
		return cbbWareSet;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setBounds(0, 161, 609, 234);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setLayout(f);
			jToolBar1.setBounds(0, 132, 609, 30);
			jToolBar1.add(getBtnAddBottom());
			jToolBar1.add(getBtnEditBottom());
			jToolBar1.add(getBtnDelete());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAddTop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddTop() {
		if (btnAddTop == null) {
			btnAddTop = new JButton();
			btnAddTop.setText("新增");
			btnAddTop.setPreferredSize(new Dimension(60, 30));
			btnAddTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initAddData();
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnAddTop;
	}

	/**
	 * 
	 * This method initializes btnEditTop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditTop() {
		if (btnEditTop == null) {
			btnEditTop = new JButton();
			btnEditTop.setText("修改");
			btnEditTop.setPreferredSize(new Dimension(60, 30));
			btnEditTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpRequestBill != null) {
						showData(impExpRequestBill);
					}
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEditTop;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(60, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull() == true) {
						return;
					}
					// if (isReBillNO()) {
					// return;
					// }
					saveData();
				}
			});
		}
		return btnSave;
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
			btnCancel.setPreferredSize(new Dimension(60, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (impExpRequestBill != null) {
						showData(impExpRequestBill);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnAddBottom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddBottom() {
		if (btnAddBottom == null) {
			btnAddBottom = new JButton();
			btnAddBottom.setText("新增");
			btnAddBottom.setPreferredSize(new Dimension(60, 25));
			btnAddBottom.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (getImpExpRequestBill() == null
							|| getImpExpRequestBill().getBillNo() == null
							|| "".equals(getImpExpRequestBill().getBillNo())) {

						JOptionPane.showMessageDialog(DgImpExpRequestBill.this,
								"单据号不能为空", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;

					}

					addMaterielByImpExpRequestBillType();
				}
			});
		}
		return btnAddBottom;
	}

	/**
	 * This method initializes btnEditBottom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditBottom() {
		if (btnEditBottom == null) {
			btnEditBottom = new JButton();
			btnEditBottom.setText("修改");
			btnEditBottom.setPreferredSize(new Dimension(60, 25));
			btnEditBottom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							doEdit();
						}
					});
		}
		return btnEditBottom;
	}

	private void doEdit() {
		if (btnSave.isValid()) {
			saveData();
		}
		editImpExpCommodityInfo(DataState.EDIT);
	}

	/**
	 * 编缉数据
	 */
	private void editImpExpCommodityInfo(int dataState) {
		// if (!this.btnEditBottom.isEnabled()) {
		// return;
		// }
		if (this.getImpExpRequestBill() == null
				|| this.getImpExpRequestBill().getBillNo() == null
				|| "".equals(this.getImpExpRequestBill().getBillNo())) {
			JOptionPane.showMessageDialog(this, "单据号不能为空", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (commodityTableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		DgImpExpCommodityInfo dg = new DgImpExpCommodityInfo();
		dg.setTableModel(commodityTableModel);
		dg.setDataState(dataState);
		dg.setImpExpRequestBill(this.getImpExpRequestBill());
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 25));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (btnSave.isValid()) {
						saveData();
					}
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择你要删除的数据", "提示",
								0);
						return;
					}
					if (JOptionPane.showConfirmDialog(null, "是否确定删除数据!!!",
							"提示", 0) != 0) {
						return;
					}
					List list = commodityTableModel.getCurrentRows();
					encAction.deleteImpExpCommodityInfo(
							new Request(CommonVars.getCurrUser()), list);
					for (int i = 0; i < list.size(); i++) {
						commodityTableModel.deleteRow(list.get(i));
					}
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnAvailability
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAvailability() {
		if (btnAvailability == null) {
			btnAvailability = new JButton();
			btnAvailability.setText("生效");
			btnAvailability.setPreferredSize(new Dimension(60, 30));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// fillData(getImpExpRequestBill());
							// if (isRe(getImpExpRequestBill())) {
							// return;
							// }
							List list = encAction
									.findImpExpCommodityInfoByCheck(
											new Request(CommonVars
													.getCurrUser()),
											impExpRequestBill.getId());
							if (list.size() > 0) {
								JOptionPane.showMessageDialog(null,
										"商品信息中栏位有空，,\n进出口申请记录不能生效!!", "非法数据!!",
										1);
								return;
							}
							beginAvailability(true);
						}
					});
		}
		return btnAvailability;
	}

	/**
	 * This method initializes btnCancelAvailability
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancelAvailability() {
		if (btnCancelAvailability == null) {
			btnCancelAvailability = new JButton();
			btnCancelAvailability.setText("撤消生效");
			btnCancelAvailability.setPreferredSize(new Dimension(60, 30));
			btnCancelAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (projectType == ProjectType.BCS) {
								List tlist = new ArrayList();
								tlist.add(getImpExpRequestBill());
								Integer rt = encAction
										.findMakeBcsCustomsDeclarationCountByListId(
												new Request(CommonVars
														.getCurrUser()), tlist);
								if (rt != null && rt > 0) {
									JOptionPane.showMessageDialog(
											DgImpExpRequestBill.this,
											"在进出口申请单中有数据已转报关清单,不能撤消生效!", "警告!",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							} else {
								if (getImpExpRequestBill().getToCustomCount() != null
										&& getImpExpRequestBill()
												.getToCustomCount() > 0) {
									JOptionPane.showMessageDialog(
											DgImpExpRequestBill.this,
											"在进出口申请单中有数据已转报关清单,不能撤消生效!", "警告!",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							}
							beginAvailability(false);
						}
					});
		}
		return btnCancelAvailability;
	}

	/**
	 * This method initializes tfCommodityItem
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommodityItem() {
		if (tfCommodityItem == null) {
			tfCommodityItem = new JTextField();
			tfCommodityItem.setBounds(504, 40, 97, 22);
		}
		return tfCommodityItem;
	}

	/**
	 * This method initializes tfMaterielProductFlag
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielProductFlag() {
		if (tfMaterielProductFlag == null) {
			tfMaterielProductFlag = new JTextField();
			tfMaterielProductFlag.setBounds(504, 64, 97, 22);
		}
		return tfMaterielProductFlag;
	}

	/**
	 * This method initializes tfConveyance
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConveyance() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(69, 87, 160, 22);
		}
		return tfConveyance;
	}

	/**
	 * This method initializes tfBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setBounds(69, 40, 160, 22);
		}
		return tfBillNo;
	}

	/**
	 * @return Returns the encAction.
	 */
	public EncAction getEncAction() {
		return encAction;
	}

	/**
	 * @param encAction
	 *            The encAction to set.
	 */
	public void setEncAction(EncAction encAction) {
		this.encAction = encAction;
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return Returns the type.
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setImpExpType(int type) {
		this.impExpType = type;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * This method initializes cbbBeginAvailabilityDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginAvailabilityDate() {
		if (cbbBeginAvailabilityDate == null) {
			cbbBeginAvailabilityDate = new JCalendarComboBox();
			cbbBeginAvailabilityDate.setBounds(305, 88, 137, 22);
		}
		return cbbBeginAvailabilityDate;
	}

	/**
	 * @return Returns the impExpRequestModel.
	 */
	public JTableListModel getImpExpRequestModel() {
		return impExpRequestModel;
	}

	/**
	 * @param impExpRequestModel
	 *            The impExpRequestModel to set.
	 */
	public void setImpExpRequestModel(JTableListModel impExpRequestModel) {
		this.impExpRequestModel = impExpRequestModel;
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public ImpExpRequestBill getImpExpRequestBill() {
		return impExpRequestBill;
	}

	/**
	 * @param impExpRequestBill
	 *            The impExpRequestBill to set.
	 */
	public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.impExpRequestBill = impExpRequestBill;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * 设置商品项数
	 * 
	 */
	private void setItemCount() {
		if (this.getImpExpRequestBill() != null
				&& this.dataState != DataState.ADD) {

			impExpRequestBill = this.encAction.saveImpExpRequestBillItemCount(
					new Request(CommonVars.getCurrUser()), impExpRequestBill);
			int itemCount = impExpRequestBill.getItemCount();

			this.tfCommodityItem.setText(itemCount + "");
			if (impExpRequestModel != null) {
				impExpRequestModel.updateRow(impExpRequestBill);
			}
		} else {
			this.tfCommodityItem.setText("0");
		}
	}

	/**
	 * 
	 * 设置状态时判断是否可以生效
	 */
	public boolean isAvailability(boolean isAva) {
		boolean isAvailability = false;
		int itemCount = Integer.parseInt(this.tfCommodityItem.getText());
		if (itemCount > 0) {
			if (isAva) {
				isAvailability = !this.getImpExpRequestBill()
						.getIsAvailability().booleanValue();
			} else {
				isAvailability = this.getImpExpRequestBill()
						.getIsAvailability().booleanValue();
			}
		}

		return isAvailability;
	}

	/**
	 * 
	 * 设置状态时判断是否已经生效
	 */
	private boolean isAvailability() {
		boolean isAvailability = false;
		if (this.impExpRequestBill != null) {
			if (impExpRequestBill.getIsAvailability() != null) {
				isAvailability = impExpRequestBill.getIsAvailability()
						.booleanValue();
			}
		}
		return isAvailability;
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initUIComponents() {
		List list = new ArrayList();
		if (this.materielProductType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {
			list = this.getCustomer();
		} else if (materielProductType == Integer
				.parseInt(MaterielType.MATERIEL)) {
			list = this.getManufacturer();
		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		List wareSetList = this.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser()));
		this.cbbWareSet
				.setModel(new DefaultComboBoxModel(wareSetList.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbWareSet,
				"code", "name", 250);
		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		// 进出口岸
		this.cbbIeport.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIeport);
		// 初始化运输方式
		this.cbbTransferMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransferMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransferMode);

	}

	/**
	 * 获得是客户的对象列表
	 */
	protected List getCustomer() {
		List list = null;
		if (this.billType == ImpExpType.TRANSFER_FACTORY_EXPORT) {

			list = this.materialManageAction.findScmManuFactoryOut(new Request(
					CommonVars.getCurrUser()));
			// String string[] =(String[])list.get(0);
			// System.out.print(string[0]+"sdfgsdgfdsgdfsgdfsh");
		} else
			list = this.materialManageAction.findScmManucustomer(new Request(
					CommonVars.getCurrUser()));
		List customerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsCustomer() != null
					&& scmCoc.getIsCustomer().booleanValue()) {
				customerList.add(scmCoc);
			}
		}
		return customerList;
	}

	/**
	 * 获得是供应商的对象列表
	 */
	protected List getManufacturer() {
		List list = null;
		if (this.billType == ImpExpType.TRANSFER_FACTORY_IMPORT) {

			list = this.materialManageAction.findScmManuFactoryIn(new Request(
					CommonVars.getCurrUser()));

		} else

			list = this.materialManageAction.findScmManufacturer(new Request(
					CommonVars.getCurrUser()));

		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void initTable(List list) {
		commodityTableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "materiel.ptNo", 90));
						list.add(addColumn("名称", "materiel.factoryName", 120));
						list.add(addColumn("规格型号", "materiel.factorySpec", 120));
						list.add(addColumn("工厂单位", "materiel.calUnit.name", 60));
						list.add(addColumn("备案序号", "seqNum", 60));
						list.add(addColumn("备案名称", "afterName", 60));
						list.add(addColumn("备案规格", "afterSpec", 60));
						list.add(addColumn("备案单位", "afterUnit", 60));

						list.add(addColumn("数量", "quantity", 60));
						list.add(addColumn("单价", "unitPrice", 60));
						list.add(addColumn("总金额", "amountPrice", 100));
						list.add(addColumn("加工费总价", "workUsd", 80));
						list.add(addColumn("单毛重", "invgrossWeight", 80));
						list.add(addColumn("毛重", "grossWeight", 80));
						list.add(addColumn("单净重", "invnetWeight", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("件数", "piece", 80));
						list.add(addColumn("箱号", "boxNo", 80));
						list.add(addColumn("制单号", "makeBillNo", 60));
						list.add(addColumn("版本号", "version", 60));
						list.add(addColumn("国家", "country.name", 100));
						list.add(addColumn("币制", "currency.name", 100));
						list.add(addColumn("未在物料料号", "ptNo", 100));
						list.add(addColumn("是否已转清单", "isTransferCustomsBill",
								100));
						list.add(addColumn("扩展备注", "extendMemo", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(11)
				.setCellRenderer(new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null) {
							setText("0");
						} else {
							setText(new BigDecimal(value.toString()).setScale(
									2, BigDecimal.ROUND_HALF_UP).toString());
						}
						return this;
					}
				});
		jTable.getColumnModel().getColumn(24)
				.setCellRenderer(new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (new Boolean(true).equals(new Boolean(value
								.toString()))) {
							returnValue = "是";
						} else {
							returnValue = "否";
						}
						return returnValue;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// 页脚
		commodityTableModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(11,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(12,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(13,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(14,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(15,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(16,
				TableFooterType.SUM, ""));
		commodityTableModel.addFooterTypeInfo(new TableFooterType(17,
				TableFooterType.SUM, ""));
	}

	/**
	 * 
	 * 填充对象
	 */
	private void fillData(ImpExpRequestBill impExpRequestBill) {

		impExpRequestBill.setBillNo((this.tfBillNo.getText()));

		impExpRequestBill.setBillType(Integer.valueOf(getImpExpType()));

		impExpRequestBill.setContainerCode(tfContainerCode.getText());

		impExpRequestBill.setCompany(CommonVars.getCurrUser().getCompany());

		impExpRequestBill.setConveyance(this.tfConveyance.getText());

		impExpRequestBill.setScmCoc((ScmCoc) this.cbbScmCoc.getSelectedItem());

		impExpRequestBill.setTransfMode((Transf) this.cbbTransferMode
				.getSelectedItem());

		impExpRequestBill.setMaterielProductFlag(Integer.valueOf(this
				.getMaterielProductType()));

		impExpRequestBill.setWareSet((WareSet) this.cbbWareSet
				.getSelectedItem());

		impExpRequestBill.setItemCount(Integer.valueOf(this.tfCommodityItem
				.getText()));

		if (impExpRequestBill.getIsAvailability() == null) {

			impExpRequestBill.setIsAvailability(Boolean.valueOf(false));

		}

		if (impExpRequestBill.getIsCustomsBill() == null) {

			impExpRequestBill.setIsCustomsBill(Boolean.valueOf(false));

		}

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date now = new Date();

		String defaultDate = bartDateFormat.format(now);

		impExpRequestBill.setImputDate(java.sql.Date.valueOf(defaultDate));

		if (this.cbbBeginAvailabilityDate.getDate() != null) {

			impExpRequestBill.setBeginAvailability(java.sql.Date
					.valueOf(bartDateFormat
							.format(this.cbbBeginAvailabilityDate.getDate())));

		} else {

			impExpRequestBill.setBeginAvailability(null);

		}

		impExpRequestBill.setIePort(cbbIeport.getSelectedItem() == null ? null
				: (Customs) cbbIeport.getSelectedItem());

		impExpRequestBill.setMemos(tfMemos.getText());
	}

	/**
	 * 
	 * 显示数据并设置对象
	 */
	private void showData(ImpExpRequestBill impExpRequestBill) {
		if (impExpRequestBill.getContainerCode() != null) {
			this.tfContainerCode.setText(impExpRequestBill.getContainerCode());
		} else {
			this.tfContainerCode.setText("");
		}
		if (impExpRequestBill.getBillNo() != null) {
			this.tfBillNo.setText(impExpRequestBill.getBillNo().toString());
		} else {
			this.tfBillNo.setText("");
		}
		if (impExpRequestBill.getBeginAvailability() != null) {
			this.cbbBeginAvailabilityDate.setDate(impExpRequestBill
					.getBeginAvailability());
		} else {
			this.cbbBeginAvailabilityDate.setDate(null);
		}
		try {
			if (impExpRequestBill.getMaterielProductFlag() != null) {
				if (impExpRequestBill.getMaterielProductFlag().intValue() == Integer
						.parseInt(MaterielType.FINISHED_PRODUCT)) {
					this.tfMaterielProductFlag.setText("成品");
				} else if (impExpRequestBill.getMaterielProductFlag()
						.intValue() == Integer.parseInt(MaterielType.MATERIEL)) {
					this.tfMaterielProductFlag.setText("料件");
				}
			} else {
				this.tfMaterielProductFlag.setText("");
			}
		} catch (Exception ex) {

		}
		this.cbbScmCoc.setSelectedItem(impExpRequestBill.getScmCoc());
		this.cbbWareSet.setSelectedItem(impExpRequestBill.getWareSet());
		if (impExpRequestBill.getConveyance() != null) {
			this.tfConveyance.setText(impExpRequestBill.getConveyance());
		} else {
			this.tfConveyance.setText("");
		}
		if (impExpRequestBill.getIePort() != null) {
			this.cbbIeport.setSelectedItem(impExpRequestBill.getIePort());
		} else {
			this.cbbIeport.setSelectedItem(null);
		}
		// if(impExpRequestBill.getTransferMode()!=null)
		// System.out.println("impExpRequestBill.getTransferMode()"+impExpRequestBill.getTransferMode().getName());
		// else
		// System.out.println("null");
		if (impExpRequestBill.getTransfMode() != null) {
			this.cbbTransferMode.setSelectedItem(impExpRequestBill
					.getTransfMode());
		} else {
			this.cbbTransferMode.setSelectedItem(null);
		}
		List dataSource = this.encAction.findImpExpCommodityInfo(new Request(
				CommonVars.getCurrUser()), impExpRequestBill.getId());
		initTable(dataSource);
		this.tfCommodityItem.setText(String.valueOf(dataSource.size()));
		if (impExpRequestBill.getMemos() != null) {
			this.tfMemos.setText(impExpRequestBill.getMemos());
		} else {
			this.tfMemos.setText("");
		}
	}

	/**
	 * 清空数据
	 * 
	 */
	private void emptyData() {
		this.tfBillNo.setText("");
		this.cbbBeginAvailabilityDate.setDate(null);
		this.tfMaterielProductFlag.setText("");
		this.tfCommodityItem.setText("0");
		this.cbbScmCoc.setSelectedItem(null);
		this.cbbWareSet.setSelectedItem(null);
		this.cbbTransferMode.setSelectedItem(null);
		this.tfConveyance.setText("");
		this.tfMemos.setText("");
		initTable(new ArrayList());
	}

	/**
	 * 是否生成了报关清单用于说明
	 */
	private boolean isMakeApplyToCustomsBill() {
		if (impExpRequestBill != null) {
			if (impExpRequestBill.getIsCustomsBill() != null
					&& impExpRequestBill.getIsCustomsBill().booleanValue() == true) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 窗体状态控制
	 */
	private void setState() {
		this.setItemCount();
		this.btnAddTop.setEnabled(dataState == DataState.BROWSE
				&& !isMakeApplyToCustomsBill());
		this.btnEditTop.setEnabled(dataState == DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.btnCancel.setEnabled(dataState != DataState.BROWSE
		// && !isMakeApplyToCustomsBill()
				&& this.impExpRequestBill != null);
		this.btnAvailability
				.setEnabled(isAvailability(true) && !isMakeApplyToCustomsBill()
						&& dataState == DataState.BROWSE);
		this.btnCancelAvailability.setEnabled(isAvailability(false)
				&& !isMakeApplyToCustomsBill());
		this.tfBillNo.setEditable(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.tfCommodityItem.setEditable(false);
		this.cbbBeginAvailabilityDate.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.tfMaterielProductFlag.setEditable(false);
		this.tfConveyance.setEditable(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.tfContainerCode.setEditable(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.cbbWareSet.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.cbbScmCoc.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.cbbTransferMode.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		this.btnAddBottom.setEnabled(dataState != DataState.ADD
				&& this.impExpRequestBill != null && !isAvailability()
				&& !isMakeApplyToCustomsBill());
		this.btnEditBottom.setEnabled(dataState != DataState.ADD
				&& this.impExpRequestBill != null && !isAvailability()
				&& !isMakeApplyToCustomsBill());
		this.btnDelete.setEnabled(dataState != DataState.ADD
				&& this.impExpRequestBill != null && !isAvailability()
				&& !isMakeApplyToCustomsBill());
		cbbIeport.setEnabled(dataState != DataState.BROWSE && !isAvailability()
				&& !isMakeApplyToCustomsBill());
		this.tfMemos.setEditable(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeApplyToCustomsBill());
		/*
		 * this.jTable.setEnabled(dataState != DataState.ADD &&
		 * this.impExpRequestBill != null && !isAvailability() &&
		 * !isMakeApplyToCustomsBill());
		 */
	}

	/**
	 * 验证数据是否为空
	 * 
	 * @return
	 */
	private boolean vaildatorDataIsNull() {
		if (this.tfBillNo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "单据号不能为空", "警告", 1);
			return true;
		}
		// 控制太多输入不方便 edit by xxm
		/*
		 * if (this.tfCustomerName.getText().equals("")) {
		 * JOptionPane.showMessageDialog(null, "客户/供应商名称不可为空", "警告", 1); return
		 * true; } if (this.tfDepotName.getText().equals("")) {
		 * JOptionPane.showMessageDialog(null, "仓库名称不可为空", "警告", 1); return
		 * true; } if (this.tfConveyance.getText().equals("")) {
		 * JOptionPane.showMessageDialog(null, "运输工具不可为空", "警告", 1); return
		 * true; } if (this.tfBillNo.getText().equals("")) {
		 * JOptionPane.showMessageDialog(null, "类别名称不可为空", "警告", 1); return
		 * true; }
		 */
		return false;
	}

	/**
	 * 检查是否出现重复的单据号
	 * 
	 * @param data
	 * @return
	 */
	private boolean isRe(ImpExpRequestBill data) {

		if (encAction.isReMerger(new Request(CommonVars.getCurrUser()), data)) {

			JOptionPane.showMessageDialog(this, "单据号重复！", "提示", 2);

			return true;

		}

		return false;
	}

	/**
	 * 保存数据
	 */
	private void saveData() {

		if (dataState == DataState.ADD) {

			ImpExpRequestBill data = new ImpExpRequestBill();

			// 设置 没 转 报关单
			data.setIsCustomsBill(new Boolean(false));

			fillData(data);

			if (isRe(data)) {

				return;
			}

			// edit by xxm
			/*
			 * String maxBillNo = this.encAction.getMaxBillNoByType(new Request(
			 * CommonVars.getCurrUser()), getType()); if
			 * (!data.getBillNo().equals(maxBillNo)) {
			 * data.setBillNo((maxBillNo)); }
			 */
			// 保存申请单表头项数
			data = this.encAction.saveImpExpRequestBillItemCount(new Request(
					CommonVars.getCurrUser()), data);

			impExpRequestModel.addRow(data);

			this.setImpExpRequestBill(data);

		} else if (dataState == DataState.EDIT) {

			ImpExpRequestBill data = null;

			if (impExpRequestBill != null) {

				data = impExpRequestBill;

			}

			fillData(data);

			if (isRe(data)) {
				return;
			}

			data = this.encAction.saveImpExpRequestBillItemCount(new Request(
					CommonVars.getCurrUser()), data);

			impExpRequestModel.updateRow(data);

			this.setImpExpRequestBill(data);

		}

		dataState = DataState.BROWSE;

		setState();
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initAddData() {
		emptyData();
		// edit by xxm
		CompanyOther other = CommonVars.getOther();
		if (other != null && other.getIsAutoGetDjNo() != null
				&& other.getIsAutoGetDjNo().equals(Boolean.valueOf(true))) {
			tfBillNo.setText(String.valueOf(encAction.getMaxBillNoByType(
					new Request(CommonVars.getCurrUser()), getBillType())));
		}

		if (this.materielProductType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {
			tfMaterielProductFlag.setText("成品");
		} else if (materielProductType == Integer
				.parseInt(MaterielType.MATERIEL)) {
			tfMaterielProductFlag.setText("料件");
		}

	}

	/**
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * 
	 * 生效和失效记录并设置状态
	 */
	private void beginAvailability(boolean isAvailability) {
		try {
			this.getImpExpRequestBill().setIsAvailability(
					Boolean.valueOf(isAvailability));
			if (isAvailability == true) {
				fillData(this.getImpExpRequestBill());
			}
			impExpRequestBill = this.encAction.saveImpExpRequestBill(
					new Request(CommonVars.getCurrUser()),
					getImpExpRequestBill());
			this.impExpRequestModel.updateRow(impExpRequestBill);
			setState();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "数据可能有误,生效失败!", "提示", 0);
		}
	}

	/**
	 * 新增物料对象-->来自物料主档
	 */
	public void addMaterielByImpExpRequestBillType() {

		if (btnSave.isValid()) {

			saveData();

		}

		String dialogTitle = "";

		List selectedList = new ArrayList();

		List list = new ArrayList();

		if (this.materielProductType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {

			dialogTitle = "进出口物料类别---成品";

		} else if (this.materielProductType == Integer
				.parseInt(MaterielType.MATERIEL)) {

			dialogTitle = "进出口物料类别---料件";
		}

		switch (projectType) {

		case ProjectType.BCUS:

			selectedList = CommonQuery.getInstance().getMaterielByTypeBcus(
					dialogTitle, String.valueOf(getMaterielProductType()),
					impExpRequestBill.getId());

			break;

		case ProjectType.BCS:

			selectedList = CommonQueryPage.getInstance().getMaterielByTypeBcs(
					dialogTitle, String.valueOf(getMaterielProductType()),
					impExpRequestBill);

			break;

		case ProjectType.DZSC:

			selectedList = DzscClientHelper.getInstance()
					.getMaterielByTypeDzsc(dialogTitle,
							String.valueOf(getMaterielProductType()),
							this.impExpRequestBill);
			break;

		default:
			break;
		}

		// 如果什么都没选 就直接结束返回
		if (selectedList == null || selectedList.size() <= 0) {

			return;

		}

		// 填充申请单的表体信息
		for (int i = 0; i < selectedList.size(); i++) {

			ImpExpCommodityInfo data = new ImpExpCommodityInfo();

			TempMateriel tempMateriel = (TempMateriel) selectedList.get(i);

			data.setMateriel(tempMateriel.getMateriel());

			data.setImpExpRequestBill(impExpRequestBill);

			data.setIsPutOnRecord(tempMateriel.getIsMemo());

			data.setAfterName(tempMateriel.getAfterTenName());

			data.setAfterSpec(tempMateriel.getAfterSpec());

			data.setAfterUnit(tempMateriel.getAfterUnit());

			data.setCountry(tempMateriel.getMateriel().getSysArea() == null ? null
					: tempMateriel.getMateriel().getSysArea().getCountry());

			data.setSeqNum(tempMateriel.getSeqNum());

			data.setInvnetWeight(tempMateriel.getMateriel().getPtNetWeight() == null ? 0.0
					: tempMateriel.getMateriel().getPtNetWeight());

			data.setInvgrossWeight(tempMateriel.getMateriel().getPtOutWeight() == null ? 0.0
					: tempMateriel.getMateriel().getPtOutWeight());// 单毛重

			data.setUnitPrice(tempMateriel.getMateriel().getPtPrice() == null ? 0.0
					: tempMateriel.getMateriel().getPtPrice());

			list.add(data);

		}

		list = encAction.saveImpExpCommodityInfo(
				new Request(CommonVars.getCurrUser()), list);

		commodityTableModel.addRows(list);

		setState();

		getBtnAddBottom().requestFocus();

		DgImpExpCommodityInfo dg = new DgImpExpCommodityInfo();

		dg.setTableModel(commodityTableModel);

		dg.setDataState(DataState.EDIT);

		dg.setImpExpRequestBill(this.getImpExpRequestBill());

		ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) list
				.get(0);

		dg.setImpExpCommodityInfo(impExpCommodityInfo);

		dg.setVisible(true);

	}

	/**
	 * 新增物料对象-->来自物料主档
	 */
	// public void addMaterielByImpExpRequestBillTypeSMD() {
	// if (btnSave.isValid()) {
	// saveData();
	// }
	// String dialogTitle = "";
	// List selectedList = new ArrayList();
	// List list = new ArrayList();
	// if (this.materielProductType == Integer
	// .parseInt(MaterielType.FINISHED_PRODUCT)) {
	// dialogTitle = "进出口物料类别---成品";
	// } else if (this.materielProductType == Integer
	// .parseInt(MaterielType.MATERIEL)) {
	// dialogTitle = "进出口物料类别---料件";
	// }
	// switch (projectType) {
	// case ProjectType.BCUS:
	// selectedList = CommonQuery.getInstance().getMaterielByTypeBcusSMD(
	// dialogTitle, String.valueOf(getMaterielProductType()),
	// impExpRequestBill.getId());
	//
	// break;
	// case ProjectType.BCS:
	// selectedList = CommonQueryPage.getInstance().getMaterielByTypeBcs(
	// dialogTitle, String.valueOf(getMaterielProductType()),
	// impExpRequestBill);
	//
	// break;
	// case ProjectType.DZSC:
	// selectedList = DzscClientHelper.getInstance()
	// .getMaterielByTypeDzsc(dialogTitle,
	// String.valueOf(getMaterielProductType()),
	// this.impExpRequestBill);
	// break;
	// default:
	// break;
	// }
	// if (selectedList == null || selectedList.size() <= 0) {
	// return;
	// }
	// for (int i = 0; i < selectedList.size(); i++) {
	// ImpExpCommodityInfo data = new ImpExpCommodityInfo();
	// TempMateriel tempMateriel = (TempMateriel) selectedList.get(i);
	// data.setMateriel(tempMateriel.getMateriel());
	// data.setImpExpRequestBill(impExpRequestBill);
	// data.setIsPutOnRecord(tempMateriel.getIsMemo());
	// data.setAfterName(tempMateriel.getAfterTenName());
	// data.setAfterSpec(tempMateriel.getAfterSpec());
	// data.setAfterUnit(tempMateriel.getAfterUnit());
	// data
	// .setCountry(tempMateriel.getMateriel().getSysArea() == null ? null
	// : tempMateriel.getMateriel().getSysArea()
	// .getCountry());
	// data.setSeqNum(tempMateriel.getSeqNum());
	// data
	// .setInvnetWeight(tempMateriel.getMateriel()
	// .getPtNetWeight() == null ? 0.0 : tempMateriel
	// .getMateriel().getPtNetWeight());
	// data
	// .setInvgrossWeight(tempMateriel.getMateriel()
	// .getPtOutWeight() == null ? 0.0 : tempMateriel
	// .getMateriel().getPtOutWeight());// 单毛重
	// data
	// .setUnitPrice(tempMateriel.getMateriel().getPtPrice() == null ? 0.0
	// : tempMateriel.getMateriel().getPtPrice());
	// list.add(data);
	// }
	// list = encAction.saveImpExpCommodityInfo(new Request(CommonVars
	// .getCurrUser()), list);
	//
	// commodityTableModel.addRows(list);
	// setState();
	// getBtnAddBottom().requestFocus();
	// DgImpExpCommodityInfo dg = new DgImpExpCommodityInfo();
	// dg.setTableModel(commodityTableModel);
	// dg.setDataState(DataState.EDIT);
	// dg.setImpExpRequestBill(this.getImpExpRequestBill());
	// ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) list
	// .get(0);
	// dg.setImpExpCommodityInfo(impExpCommodityInfo);
	// dg.setVisible(true);
	// }

	/**
	 * This method initializes tfContainer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContainer() {
		if (tfContainerCode == null) {
			tfContainerCode = new JTextField();
			tfContainerCode.setBounds(new Rectangle(69, 64, 160, 22));
		}
		return tfContainerCode;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	/**
	 * This method initializes cbbIeport
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIeport() {
		if (cbbIeport == null) {
			cbbIeport = new JComboBox();
			cbbIeport.setBounds(new Rectangle(305, 64, 137, 22));
		}
		return cbbIeport;
	}

	/**
	 * This method initializes btnPrint 新谱插件
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = commodityTableModel.getList();
					@SuppressWarnings("unused")
					DgPrintInfo printInfo = new DgPrintInfo(impExpRequestBill,
							list, isOut);
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes cbbTransferMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransferMode() {
		if (cbbTransferMode == null) {
			cbbTransferMode = new JComboBox();
			cbbTransferMode.setBounds(new Rectangle(69, 110, 160, 22));
		}
		return cbbTransferMode;
	}

	/**
	 * This method initializes tfMemos
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemos() {
		if (tfMemos == null) {
			tfMemos = new JTextField();
			tfMemos.setBounds(new Rectangle(305, 110, 138, 22));
		}
		return tfMemos;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

class MultiRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null) {
			return this;
		}
		if (Boolean.valueOf(value.toString()) instanceof Boolean) {
			checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			// if (checkValue(table, row, column)) {
			// checkBox.setBackground(Color.BLUE);
			// checkBox.setForeground(Color.WHITE);
			// }
			return checkBox;
		}
		String str = (value == null) ? "" : value.toString();
		return super.getTableCellRendererComponent(table, str, isSelected,
				hasFocus, row, column);
	}

}