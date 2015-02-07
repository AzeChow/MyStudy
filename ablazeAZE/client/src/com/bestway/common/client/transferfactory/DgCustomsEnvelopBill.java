package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TempCustomsEmsInfo;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;

public class DgCustomsEnvelopBill extends DgCommon {

	private JPanel pnMaster = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

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

	private JTextField tfPurchaseAndSaleContractNo = null;

	private JTextField tfCustomsEnvelopBillNo = null;

	private JComboBox cbbCustoms = null;

	private JTextField tfImportExport = null;

	private JTextField tfMemo = null;

	private JButton btnEndCase = null;

	private JButton btnCancelEndCase = null;

	private JTextField tfCarryForwardApplyToCustomsBillNo = null;

	private JTextField tfEndCaseDate = null;

	private JComboBox cbbExpComp = null;

	private JCheckBox cbEndCase = null;

	private JComboBox cbbImpComp = null;

	private JCalendarComboBox cbbBeginAvailability = null;

	private JCalendarComboBox cbbEndAvailability = null;

	private List list = null; // @jve:decl-index=0:

	private JTableListModel commodityTableModel = null;

	private JTableListModel customsEnvelopModel = null;

	private CustomsEnvelopBill customsEnvelopBill = null;

	private boolean isImportGoods = true;

	private int materielProductType = -1;

	private int dataState = -1;

	private int itemCount = 0;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel51 = null;

	private JLabel lbEmsNo = null;

	private JComboBox cbbProjectType = null;

	private JLabel jLabel52 = null;

	private JTextField jTextField = null;

	private JTextField tfEmsNo = null;

	private JButton jButton = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomsEnvelopBill() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("关封单据");
		this.setContentPane(getJContentPane());
		this.setSize(669, 532);
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	public void setVisible(boolean b) {
		if (b) {
			initComponents();
			jTextField.setVisible(CommonVars.isCompany("田氏化工"));
			jLabel52.setVisible(CommonVars.isCompany("田氏化工"));
			if (dataState == DataState.ADD) {
				emptyData();
			} else if (dataState == DataState.EDIT) {
				customsEnvelopBill = (CustomsEnvelopBill) customsEnvelopModel
						.getCurrentRow();
				showData(customsEnvelopBill);
			}
			setState();
		}
		super.setVisible(b);
	}

	private JPanel getPnMaster() {
		if (pnMaster == null) {
			jLabel52 = new JLabel();
			jLabel52.setBounds(new Rectangle(337, 146, 51, 19));
			jLabel52.setText("条码号");
			// lbEmsNo = new JLabel();
			// lbEmsNo.setBounds(new Rectangle(40, 170, 74, 24));
			// lbEmsNo.setText("手册编号");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(40, 143, 74, 23));
			jLabel51.setText("管理类型");
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			pnMaster = new JPanel();
			pnMaster.setLayout(null);
			jLabel.setText("关封号");
			jLabel.setBounds(40, 16, 74, 22);
			jLabel1.setText("购销合同(协议)号");
			jLabel1.setBounds(336, 16, 98, 22);
			jLabel2.setText("转入/转出");
			jLabel2.setBounds(40, 41, 74, 22);
			jLabel3.setText("转入企业名称");
			jLabel3.setBounds(40, 93, 74, 22);
			jLabel4.setText("审批海关");
			jLabel4.setBounds(336, 41, 98, 22);
			jLabel6.setText("有效截止日期");
			jLabel6.setBounds(336, 67, 98, 22);
			jLabel7.setBounds(40, 67, 74, 22);
			jLabel7.setText("生效起始日期");
			jLabel8.setBounds(40, 198, 74, 22);
			jLabel8.setText("备注");
			jLabel9.setBounds(336, 94, 98, 22);
			jLabel9.setText("转出企业名称");
			jLabel11.setBounds(124, 119, 51, 22);
			jLabel11.setText("结案日期");
			jLabel12.setBounds(336, 119, 98, 22);
			jLabel12.setText("结转报关单号");
			pnMaster.add(jLabel, null);
			pnMaster.add(jLabel1, null);
			pnMaster.add(jLabel2, null);
			pnMaster.add(jLabel3, null);
			pnMaster.add(jLabel4, null);
			pnMaster.add(jLabel6, null);
			pnMaster.add(getTfPurchaseAndSaleContractNo(), null);
			pnMaster.add(getTfCustomsEnvelopBillNo(), null);
			pnMaster.add(getCbbCustoms(), null);
			pnMaster.add(jLabel7, null);
			pnMaster.add(getTfImportExport(), null);
			pnMaster.add(getTfMemo(), null);
			pnMaster.add(jLabel8, null);
			pnMaster.add(jLabel9, null);
			pnMaster.add(jLabel11, null);
			pnMaster.add(jLabel12, null);
			pnMaster.add(getTfCarryForwardApplyToCustomsBillNo(), null);
			pnMaster.add(getTfEndCaseDate(), null);
			pnMaster.add(getCbbExpComp(), null);
			pnMaster.add(getCbEndCase(), null);
			pnMaster.add(getCbbImpComp(), null);
			pnMaster.add(getCbbBeginAvailability(), null);
			pnMaster.add(getCbbEndAvailability(), null);
			pnMaster.add(jLabel51, null);
			// pnMaster.add(lbEmsNo, null);
			pnMaster.add(getLbEmsNo(), null);
			pnMaster.add(getCbbProjectType(), null);
			pnMaster.add(jLabel52, null);
			pnMaster.add(getJTextField(), null);
			pnMaster.add(getTfEmsNo(), null);
			pnMaster.add(getJButton(), null);
		}
		return pnMaster;
	}

	private JLabel getLbEmsNo() {
		if (lbEmsNo == null) {
			lbEmsNo = new JLabel();
			lbEmsNo.setBounds(new Rectangle(40, 170, 74, 24));
			lbEmsNo.setText("手册编号");
		}
		return lbEmsNo;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return j pnMaster.add(jLabel5, null); avax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnAddTop());
			jToolBar.add(getBtnEditTop());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnAvailability());
			jToolBar.add(getBtnCancelAvailability());
			jToolBar.add(getBtnEndCase());
			jToolBar.add(getBtnCancelEndCase());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editImpExpCommodityInfo();
					}
				}
			});
		}
		return jTable;
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
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setPreferredSize(new Dimension(217, 35));
			jToolBar1.setLayout(f);
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
			btnAddTop.setPreferredSize(new Dimension(65, 30));
			btnAddTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					emptyData();
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
			btnEditTop.setPreferredSize(new Dimension(65, 30));
			btnEditTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (customsEnvelopBill != null) {
						showData(customsEnvelopBill);
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
			btnSave.setPreferredSize(new Dimension(65, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull() == true) {
						return;
					}
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
			btnCancel.setPreferredSize(new Dimension(65, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (customsEnvelopBill != null) {
						showData(customsEnvelopBill);
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
			btnAddBottom.setPreferredSize(new Dimension(65, 30));
			btnAddBottom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (customsEnvelopBill == null) {
						return;
					}
					if (cbbProjectType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								DgCustomsEnvelopBill.this, "请选择项目类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					ItemProperty item = (ItemProperty) cbbProjectType
							.getSelectedItem();
					int projectType = Integer.parseInt(item.getCode());
					if (tfEmsNo.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(
								DgCustomsEnvelopBill.this, "请选择"
										+ lbEmsNo.getText(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String emsNo = tfEmsNo.getText().trim();
					// DgCustomsEnvelopCommFrom dg = new
					// DgCustomsEnvelopCommFrom();
					// dg.setMaterial(isImportGoods);
					// dg.setEnvelopBill(customsEnvelopBill);
					// dg.setVisible(true);
					// List lsResult = dg.getLsResult();
					// commodityTableModel.addRows(lsResult);
					List list = TransferFactoryQuery.getInstance()
							.findTempCustomsEnvelopCommInfo(projectType, emsNo,
									customsEnvelopBill, isImportGoods);
					list = transferFactoryManageAction
							.saveCustomsEnvelopRequestCommInfo(new Request(
									CommonVars.getCurrUser(), true), list,
									customsEnvelopBill);
					if (dataState == DataState.EDIT
							&& commodityTableModel.getList().size() <= 0) {
						saveData();
						dataState = DataState.EDIT;
					}
					commodityTableModel.addRows(list);
					setState();
					// addData();
				}
			});
		}
		return btnAddBottom;
	}

	/**
	 * 新增关封商品信息
	 */
	private void addData() {
		List customsEnvelopCommodityInfos = getCustomsEnvelopCommodityInfos();
		if (customsEnvelopCommodityInfos == null) {
			return;
		}
		customsEnvelopCommodityInfos = transferFactoryManageAction
				.saveCustomsEnvelopCommodityInfo(new Request(CommonVars
						.getCurrUser()), customsEnvelopCommodityInfos);
		for (int i = 0; i < customsEnvelopCommodityInfos.size(); i++) {
			commodityTableModel.addRow(customsEnvelopCommodityInfos.get(i));
		}
		setState();
	}

	/**
	 * 获得关封商品信息
	 */
	private List getCustomsEnvelopCommodityInfos() {
		List list = manualDeclearAction.findEmsHeadH2kInExecuting(new Request(
				CommonVars.getCurrUser(), true));
		if (list == null || list.size() <= 0) {
			JOptionPane.showMessageDialog(null, "没有正在执行的电子帐册，不能新增!!", "信息",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) (list.get(0));
		List customsEnvelopCommodityInfos = new ArrayList();
		if (isImportGoods() == true) {
			List emsMateriels = (List) CommonQuery.getInstance()
					.getEmsMateriel(emsHeadH2k, customsEnvelopBill.getId());
			if (emsMateriels == null || emsMateriels.size() <= 0) {
				return null;
			}
			for (int i = 0; i < emsMateriels.size(); i++) {
				CustomsEnvelopCommodityInfo data = new CustomsEnvelopCommodityInfo();
				EmsHeadH2kImg temp = (EmsHeadH2kImg) emsMateriels.get(i);

				data.setSeqNum(temp.getSeqNum());
				data.setPtName(temp.getName());
				data.setPtSpec(temp.getSpec());
				data.setUnit(temp.getUnit());
				data.setComplex(temp.getComplex());
				data.setCurr(temp.getCurr());

				data.setCustomsEnvelopBill(customsEnvelopBill);
				customsEnvelopCommodityInfos.add(data);
			}
		} else {
			List emsFinishedProduct = (List) CommonQuery.getInstance()
					.getEmsFinishedProduct(emsHeadH2k,
							customsEnvelopBill.getId());
			if (emsFinishedProduct == null || emsFinishedProduct.size() <= 0) {
				return null;
			}
			for (int i = 0; i < emsFinishedProduct.size(); i++) {
				CustomsEnvelopCommodityInfo data = new CustomsEnvelopCommodityInfo();
				EmsHeadH2kExg temp = (EmsHeadH2kExg) emsFinishedProduct.get(i);

				data.setSeqNum(temp.getSeqNum());
				data.setPtName(temp.getName());
				data.setPtSpec(temp.getSpec());
				data.setUnit(temp.getUnit());
				data.setComplex(temp.getComplex());
				data.setCurr(temp.getCurr());

				data.setCustomsEnvelopBill(customsEnvelopBill);
				customsEnvelopCommodityInfos.add(data);
			}
		}
		return customsEnvelopCommodityInfos;
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
			btnEditBottom.setPreferredSize(new Dimension(65, 30));
			btnEditBottom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editImpExpCommodityInfo();
						}
					});
		}
		return btnEditBottom;
	}

	/**
	 * 编缉数据
	 */
	private void editImpExpCommodityInfo() {
		if (!this.jTable.isEnabled()) {
			return;
		}
		if (commodityTableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgCustomsEnvelopCommodityInfo dg = new DgCustomsEnvelopCommodityInfo();
		dg.setTableModel(commodityTableModel);
		dg.setDataState(DataState.EDIT);
		dg.setCustomsEnvelopBill(this.getCustomsEnvelopBill());
		dg.setImportGoods(isImportGoods());
		dg.setVisible(true);
		showData(customsEnvelopBill);
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
			btnDelete.setPreferredSize(new Dimension(65, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择你要删除的数据", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(null, "是否确定删除数据!!!",
							"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = commodityTableModel.getCurrentRows();
					transferFactoryManageAction
							.deleteCustomsEnvelopCommodityInfo(new Request(
									CommonVars.getCurrUser()), list);
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
			btnAvailability.setPreferredSize(new Dimension(65, 30));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = transferFactoryManageAction
									.findCustomsEnvelopCommodityInfoByCheck(
											new Request(CommonVars
													.getCurrUser()),
											customsEnvelopBill.getId());
							if (list.size() > 0) {
								JOptionPane.showMessageDialog(null,
										"商品信息中数量中有空的数据,\n关封单据记录不能生效!!",
										"非法数据!!",
										JOptionPane.INFORMATION_MESSAGE);
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
			btnCancelAvailability.setPreferredSize(new Dimension(65, 30));
			btnCancelAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (transferFactoryManageAction
									.hasDataTransFactoryBillByEnvelopId(
											new Request(CommonVars
													.getCurrUser()),
											customsEnvelopBill)) {
								JOptionPane.showMessageDialog(null,
										"此关封已经有转厂单据,不能撤消生效!!", "警告",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							beginAvailability(false);
						}
					});
		}
		return btnCancelAvailability;
	}

	/**
	 * This method initializes tfPurchaseAndSaleContractNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPurchaseAndSaleContractNo() {
		if (tfPurchaseAndSaleContractNo == null) {
			tfPurchaseAndSaleContractNo = new JTextField();
			tfPurchaseAndSaleContractNo.setBounds(445, 16, 165, 22);
		}
		return tfPurchaseAndSaleContractNo;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillNo() {
		if (tfCustomsEnvelopBillNo == null) {
			tfCustomsEnvelopBillNo = new JTextField();
			tfCustomsEnvelopBillNo.setBounds(124, 16, 173, 22);
		}
		return tfCustomsEnvelopBillNo;
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
	 * @return Returns the tableModel.
	 */
	public JTableListModel getCommodityTableModel() {
		return commodityTableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setCommodityTableModel(JTableListModel tableModel) {
		this.commodityTableModel = tableModel;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		if (this.isImportGoods == true) {
			return Integer.parseInt(MaterielType.MATERIEL);
		} else {
			return Integer.parseInt(MaterielType.FINISHED_PRODUCT);
		}
	}

	/**
	 * @return Returns the impExpRequestModel.
	 */
	public JTableListModel getCustomsEnvelopModel() {
		return customsEnvelopModel;
	}

	/**
	 * This method initializes cbbCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(445, 41, 165, 22);
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes tfImportExport
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImportExport() {
		if (tfImportExport == null) {
			tfImportExport = new JTextField();
			tfImportExport.setEditable(false);
			tfImportExport.setBounds(124, 41, 173, 22);
		}
		return tfImportExport;
	}

	/**
	 * This method initializes btnEndCase
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEndCase() {
		if (btnEndCase == null) {
			btnEndCase = new JButton();
			btnEndCase.setText("结案");
			btnEndCase.setPreferredSize(new Dimension(65, 30));
			btnEndCase.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					beginEndCase(true);
				}
			});
		}
		return btnEndCase;
	}

	/**
	 * This method initializes btnCancelEndCase
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancelEndCase() {
		if (btnCancelEndCase == null) {
			btnCancelEndCase = new JButton();
			btnCancelEndCase.setText("撤消结案");
			btnCancelEndCase.setPreferredSize(new Dimension(65, 30));
			btnCancelEndCase
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							beginEndCase(false);
						}
					});
		}
		return btnCancelEndCase;
	}

	/**
	 * This method initializes tfCarryForwardApplyToCustomsBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCarryForwardApplyToCustomsBillNo() {
		if (tfCarryForwardApplyToCustomsBillNo == null) {
			tfCarryForwardApplyToCustomsBillNo = new JTextField();
			tfCarryForwardApplyToCustomsBillNo.setBounds(445, 119, 165, 22);
		}
		return tfCarryForwardApplyToCustomsBillNo;
	}

	/**
	 * This method initializes tfEndCaseDate
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndCaseDate() {
		if (tfEndCaseDate == null) {
			tfEndCaseDate = new JTextField();
			tfEndCaseDate.setEditable(false);
			tfEndCaseDate.setBounds(175, 119, 122, 22);
		}
		return tfEndCaseDate;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExpComp() {
		if (cbbExpComp == null) {
			cbbExpComp = new JComboBox();
			cbbExpComp.setBounds(445, 94, 165, 22);
		}
		return cbbExpComp;
	}

	/**
	 * This method initializes cbEndCase
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbEndCase() {
		if (cbEndCase == null) {
			cbEndCase = new JCheckBox();
			cbEndCase.setEnabled(false);
			cbEndCase.setText("已结案");
			cbEndCase.setBounds(40, 121, 62, 19);
		}
		return cbEndCase;
	}

	/**
	 * This method initializes cbbCompany
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpComp() {
		if (cbbImpComp == null) {
			cbbImpComp = new JComboBox();
			cbbImpComp.setBounds(124, 93, 173, 22);
		}
		return cbbImpComp;
	}

	/**
	 * This method initializes cbbBeginAvailability
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginAvailability() {
		if (cbbBeginAvailability == null) {
			cbbBeginAvailability = new JCalendarComboBox();
			// cbbBeginAvailability.addChangeListener(new ChangeListener() {
			//
			// public void stateChanged(ChangeEvent arg0) {
			//					
			// }
			//
			// });
			cbbBeginAvailability.setBounds(124, 67, 173, 22);
			cbbBeginAvailability.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					if (cbbBeginAvailability.getDate() == null) {
						return;
					}
					Calendar beginDate = cbbBeginAvailability.getCalendar();
					Calendar endDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.add(Calendar.DAY_OF_YEAR, 90);
					}
					cbbEndAvailability.setCalendar(endDate);
				}

			});
		}
		return cbbBeginAvailability;
	}

	/**
	 * This method initializes cbbEndAvailability
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndAvailability() {
		if (cbbEndAvailability == null) {
			cbbEndAvailability = new JCalendarComboBox();
			cbbEndAvailability.setEnabled(true);
			cbbEndAvailability.setBounds(445, 67, 165, 22);
		}
		return cbbEndAvailability;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(124, 200, 487, 22);
		}
		return tfMemo;
	}

	/**
	 * @param impExpRequestModel
	 *            The impExpRequestModel to set.
	 */
	public void setCustomsEnvelopModel(JTableListModel impExpRequestModel) {
		this.customsEnvelopModel = impExpRequestModel;
	}

	/**
	 * @return Returns the CustomsEnvelopBill.
	 */
	public CustomsEnvelopBill getCustomsEnvelopBill() {
		return customsEnvelopBill;
	}

	/**
	 * @param CustomsEnvelopBill
	 *            The CustomsEnvelopBill to set.
	 */
	public void setCustomsEnvelopBill(CustomsEnvelopBill CustomsEnvelopBill) {
		this.customsEnvelopBill = CustomsEnvelopBill;
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
	 * 设置商品信息个数
	 * 
	 */
	private void setItemCount() {
		if (this.customsEnvelopBill == null || this.dataState == DataState.ADD) {
			itemCount = 0;
			return;
		}
		itemCount = this.transferFactoryManageAction
				.findCustomsEnvelopCommodityInfoCount(new Request(CommonVars
						.getCurrUser()), this.customsEnvelopBill.getId());
	}

	/**
	 * 
	 * 设置状态时判断是否可以生效
	 */
	public boolean isAvailability(boolean isAva) {
		boolean isAvailability = false;
		if (this.itemCount > 0) {
			if (isAva) {
				isAvailability = !this.getCustomsEnvelopBill()
						.getIsAvailability().booleanValue();
			} else {
				isAvailability = this.getCustomsEnvelopBill()
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
		if (this.customsEnvelopBill != null) {
			isAvailability = customsEnvelopBill.getIsAvailability()
					.booleanValue();
		}
		return isAvailability;
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void initTable(List list) {
		commodityTableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						String sysType = "手册";
						ItemProperty item = (ItemProperty) cbbProjectType
								.getSelectedItem();
						if (item != null) {
							int projectType = Integer.parseInt(item.getCode());
							switch (projectType) {
							case ProjectType.BCS:
								sysType = "手册";
								break;
							case ProjectType.BCUS:
								sysType = "账册";
								break;
							case ProjectType.DZSC:
								sysType = "手册";
								break;
							}
						}
						List list = new Vector();
						list.add(addColumn("关封序号", "ceseqNum", 120));
						list.add(addColumn(sysType + "号", "emsNo", 90));
						list.add(addColumn(sysType + "序号", "seqNum", 90));
						list.add(addColumn("海关商品编码", "complex.code", 120));
						list.add(addColumn("商品名称", "ptName", 120));
						list.add(addColumn("规格型号", "ptSpec", 120));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("报关单已转数量", "transferQuantity", 80));
						list.add(addColumn("数量", "ownerQuantity", 60));
						list.add(addColumn("单价", "unitPrice", 60));
						list.add(addColumn("总价", "totalPrice", 60));
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 
	 * 填充对象
	 */
	private void fillData(CustomsEnvelopBill customsEnvelopBill) {
		customsEnvelopBill.setCustomsEnvelopBillNo(this.tfCustomsEnvelopBillNo
				.getText());
		Calendar calendar = this.cbbBeginAvailability.getCalendar();
		// customsEnvelopBill.setBeginAvailability(new Date(calendar
		// .get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH),
		// calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0));
		customsEnvelopBill.setBeginAvailability(this.cbbBeginAvailability
				.getDate());
		Calendar calendarByEnd = this.cbbEndAvailability.getCalendar();
		// customsEnvelopBill.setEndAvailability(new Date(calendarByEnd
		// .get(Calendar.YEAR) - 1900, calendarByEnd.get(Calendar.MONTH),
		// calendarByEnd.get(Calendar.DAY_OF_MONTH), 23, 59, 59));
		customsEnvelopBill
				.setEndAvailability(this.cbbEndAvailability.getDate());
		customsEnvelopBill
				.setCarryForwardApplyToCustomsBillNo(this.tfCarryForwardApplyToCustomsBillNo
						.getText());
		customsEnvelopBill.setCustoms((Customs) this.cbbCustoms
				.getSelectedItem());

		// customsEnvelopBill.setEndCaseDate(new Date());
		// customsEnvelopBill
		// .setExportEnterpriseEmsNo(this.tfExportEnterpriseEmsNo
		// .getText());
		// customsEnvelopBill
		// .setImportEnterpriseEmsNo(this.tfImportEnterpriseEmsNo
		// .getText());
		customsEnvelopBill.setIsAvailability(new Boolean(false));
		customsEnvelopBill.setIsEndCase(new Boolean(false));
		customsEnvelopBill.setIsImpExpGoods(new Boolean(this.isImportGoods()));
		customsEnvelopBill.setMemo(this.tfMemo.getText());
		customsEnvelopBill
				.setPurchaseAndSaleContractNo(this.tfPurchaseAndSaleContractNo
						.getText());
		if (this.isImportGoods() == true) {
			customsEnvelopBill.setScmCoc((ScmCoc) this.cbbExpComp
					.getSelectedItem());
			customsEnvelopBill.setTransCompany((Company) this.cbbImpComp
					.getSelectedItem());
		} else {
			customsEnvelopBill.setScmCoc((ScmCoc) this.cbbImpComp
					.getSelectedItem());
			customsEnvelopBill.setTransCompany((Company) this.cbbExpComp
					.getSelectedItem());
		}
		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			int projectType = Integer.parseInt(item.getCode());
			customsEnvelopBill.setProjectType(projectType);
		} else {
			customsEnvelopBill.setProjectType(null);
		}
		customsEnvelopBill.setEmsNo(this.tfEmsNo.getText().trim());
		customsEnvelopBill.setCodeNo(jTextField.getText());
		//设置表头创建日期
		if (customsEnvelopBill.getCreateDate() == null
				|| "".equals(customsEnvelopBill.getCreateDate())) {
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			String defaultDate = bartDateFormat.format(now);
			customsEnvelopBill.setCreateDate(java.sql.Date.valueOf(defaultDate));
		}
	}

	/**
	 * 
	 * 显示数据并设置对象
	 */
	private void showData(CustomsEnvelopBill customsEnvelopBill) {
		if (customsEnvelopBill.getCustomsEnvelopBillNo() != null) {
			this.tfCustomsEnvelopBillNo.setText(customsEnvelopBill
					.getCustomsEnvelopBillNo());
		} else {
			this.tfCustomsEnvelopBillNo.setText("");
		}
		if (customsEnvelopBill.getBeginAvailability() != null) {
			this.cbbBeginAvailability.setDate(customsEnvelopBill
					.getBeginAvailability());
		} else {
			this.cbbBeginAvailability.setDate(null);
		}
		if (customsEnvelopBill.getEndAvailability() != null) {
			this.cbbEndAvailability.setDate(customsEnvelopBill
					.getEndAvailability());
		} else {
			this.cbbEndAvailability.setDate(null);
		}
		if (customsEnvelopBill.getCarryForwardApplyToCustomsBillNo() != null) {
			this.tfCarryForwardApplyToCustomsBillNo.setText(customsEnvelopBill
					.getCarryForwardApplyToCustomsBillNo());
		} else {
			this.tfCarryForwardApplyToCustomsBillNo.setText("");
		}

		this.cbbCustoms.setSelectedItem(customsEnvelopBill.getCustoms());

		// if (customsEnvelopBill.getExportEnterpriseEmsNo() != null) {
		// this.tfExportEnterpriseEmsNo.setText(customsEnvelopBill
		// .getExportEnterpriseEmsNo());
		// } else {
		// this.tfExportEnterpriseEmsNo.setText("");
		// }
		//
		// if (customsEnvelopBill.getImportEnterpriseEmsNo() != null) {
		// this.tfImportEnterpriseEmsNo.setText(customsEnvelopBill
		// .getImportEnterpriseEmsNo());
		// } else {
		// this.tfImportEnterpriseEmsNo.setText("");
		// }

		jTextField.setText(customsEnvelopBill.getCodeNo());
		if (customsEnvelopBill.getIsEndCase() != null) {
			this.cbEndCase.setSelected(customsEnvelopBill.getIsEndCase()
					.booleanValue());
		} else {
			this.cbEndCase.setSelected(false);
		}

		if (customsEnvelopBill.getPurchaseAndSaleContractNo() != null) {
			this.tfPurchaseAndSaleContractNo.setText(customsEnvelopBill
					.getPurchaseAndSaleContractNo());
		} else {
			this.tfPurchaseAndSaleContractNo.setText("");
		}

		if (customsEnvelopBill.getMemo() != null) {
			this.tfMemo.setText(customsEnvelopBill.getMemo());
		} else {
			this.tfMemo.setText("");
		}
		if (this.isImportGoods() == true) {
			this.cbbExpComp.setSelectedItem(customsEnvelopBill.getScmCoc());
			this.cbbImpComp.setSelectedItem(customsEnvelopBill
					.getTransCompany());
		} else {
			this.cbbImpComp.setSelectedItem(customsEnvelopBill.getScmCoc());
			this.cbbExpComp.setSelectedItem(customsEnvelopBill
					.getTransCompany());
		}
		if (customsEnvelopBill.getProjectType() != null) {
			this.cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(
					customsEnvelopBill.getProjectType().toString(),
					this.cbbProjectType));
		} else {
			this.cbbProjectType.setSelectedIndex(-1);
		}
		this.tfEmsNo.setText(customsEnvelopBill.getEmsNo());
		List dataSource = this.transferFactoryManageAction
				.findCustomsEnvelopCommodityInfo(new Request(CommonVars
						.getCurrUser()), this.customsEnvelopBill.getId());
		initTable(dataSource);
	}

	/**
	 * 清空数据
	 * 
	 */
	private void emptyData() {
		this.tfCustomsEnvelopBillNo.setText("");
		this.cbbBeginAvailability.setDate(null);
		this.cbbEndAvailability.setDate(null);
		jTextField.setText("");
		this.tfCarryForwardApplyToCustomsBillNo.setText("");
		this.cbbCustoms.setSelectedItem(null);
		// this.tfImportEnterpriseEmsNo.setText("");
		// this.tfExportEnterpriseEmsNo.setText("");
		this.cbEndCase.setSelected(false);
		this.tfPurchaseAndSaleContractNo.setText("");
		this.tfMemo.setText("");
		if (this.isImportGoods()) {
			this.cbbExpComp.setSelectedItem(null);
		} else {
			this.cbbImpComp.setSelectedItem(null);
		}
		if (customsEnvelopModel != null
				&& customsEnvelopModel.getList() != null
				&& customsEnvelopModel.getList().size() > 0) {
			CustomsEnvelopBill bill = (CustomsEnvelopBill) customsEnvelopModel
					.getList().get(0);
			if (bill.getProjectType() != null) {
				this.cbbProjectType.setSelectedIndex(ItemProperty
						.getIndexByCode(bill.getProjectType().toString(),
								this.cbbProjectType));
			}
			if (bill.getEmsNo() != null && !"".equals(bill.getEmsNo())) {
				this.tfEmsNo.setText(bill.getEmsNo());
			}
		} else {
			this.cbbProjectType.setSelectedIndex(-1);
			this.tfEmsNo.setText("");
		}
		initTable(new ArrayList());
	}

	/**
	 * 是否结案
	 */
	private boolean isEndCase() {
		if (customsEnvelopBill != null) {
			if (customsEnvelopBill.getIsEndCase().booleanValue() == true) {
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
		this.btnAddTop.setEnabled(dataState == DataState.BROWSE);
		this.btnEditTop.setEnabled(dataState == DataState.BROWSE
				&& !isAvailability() && !isEndCase());
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isEndCase());
		this.btnCancel.setEnabled(dataState != DataState.BROWSE
				&& this.customsEnvelopBill != null);
		this.btnAvailability.setEnabled(isAvailability(true) && !isEndCase());
		this.btnCancelAvailability.setEnabled(isAvailability(false)
				&& !isEndCase());
		this.btnEndCase.setEnabled(isAvailability() && !isEndCase());
		this.btnCancelEndCase.setEnabled(isEndCase());
		this.cbEndCase.setSelected(isEndCase());

		boolean isEnabled = dataState != DataState.BROWSE && !isAvailability()
				&& !isEndCase();
		this.tfCustomsEnvelopBillNo.setEditable(isEnabled);
		this.cbbBeginAvailability.setEnabled(isEnabled);
		this.cbbEndAvailability.setEnabled(isEnabled);
		jButton.setEnabled(isEnabled
				&& (commodityTableModel != null && commodityTableModel
						.getList().isEmpty()));
		this.tfCarryForwardApplyToCustomsBillNo.setEditable(isEnabled);
		this.cbbCustoms.setEnabled(isEnabled);
		// this.tfImportEnterpriseEmsNo.setEditable(isEnabled);
		// this.tfExportEnterpriseEmsNo.setEditable(isEnabled);
		this.tfPurchaseAndSaleContractNo.setEditable(isEnabled);
		this.tfMemo.setEditable(isEnabled);
		this.cbbExpComp.setEnabled(isEnabled);
		this.cbbImpComp.setEnabled(isEnabled);

		this.btnAddBottom.setEnabled(dataState != DataState.ADD
				&& this.customsEnvelopBill != null && !isAvailability()
				&& !isEndCase());
		this.btnEditBottom.setEnabled(dataState != DataState.ADD
				&& this.customsEnvelopBill != null && !isAvailability()
				&& !isEndCase());
		this.btnDelete.setEnabled(dataState != DataState.ADD
				&& this.customsEnvelopBill != null && !isAvailability()
				&& !isEndCase());
		this.jTable.setEnabled(dataState != DataState.ADD
				&& this.customsEnvelopBill != null && !isAvailability()
				&& !isEndCase());

		if (customsEnvelopBill != null) {
			if (customsEnvelopBill.getEndCaseDate() != null) {
				this.tfEndCaseDate.setText(customsEnvelopBill.getEndCaseDate()
						.toString());
			} else {
				this.tfEndCaseDate.setText("");
			}
		} else {
			this.tfEndCaseDate.setText("");
		}
		this.cbbProjectType.setEnabled(dataState != DataState.BROWSE
				&& commodityTableModel.getList().size() <= 0);
		this.jTextField.setEditable(isEnabled);

	}

	private boolean vaildatorDataIsNull() {
		if (this.tfCustomsEnvelopBillNo.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "关封号不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
//		if (this.tfCustomsEnvelopBillNo.getText().trim().length()<8||this.tfCustomsEnvelopBillNo.getText().trim().length()>8) {
//			JOptionPane.showMessageDialog(null, "关封号应该为8位!!", "警告",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
		if (this.cbbBeginAvailability.getDate() == null) {
			JOptionPane.showMessageDialog(null, "生效日期不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbImpComp.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null,
					isImportGoods() == true ? "转入企业名称不可为空" : "转出企业名称不可为空",
					"警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbExpComp.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null,
					isImportGoods() == true ? "转出企业名称不可为空" : "转入企业名称不可为空",
					"警告", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		// if (this.cbbImpComp.getSelectedItem() == null ||
		// this.cbbImpComp.getSelectedItem().equals("")) {
		// JOptionPane.showMessageDialog(null, "转入企业帐册号不可为空", "警告",
		// JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		// if (this.cbbExpComp.getSelectedItem() == null ||
		// this.cbbExpComp.getSelectedItem().equals("")) {
		// JOptionPane.showMessageDialog(null, "转出企业帐册号不可为空", "警告",
		// JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		return false;
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		if (dataState == DataState.ADD) {
			CustomsEnvelopBill data = new CustomsEnvelopBill();
			fillData(data);
			data = this.transferFactoryManageAction.saveCustomsEnvelopBill(
					new Request(CommonVars.getCurrUser()), data);
			customsEnvelopModel.addRow(data);
			this.setCustomsEnvelopBill(data);
		} else if (dataState == DataState.EDIT) {
			CustomsEnvelopBill data = null;
			if (customsEnvelopBill != null) {
				data = customsEnvelopBill;
			}
			fillData(data);
			data = this.transferFactoryManageAction.saveCustomsEnvelopBill(
					new Request(CommonVars.getCurrUser()), data);
			customsEnvelopModel.updateRow(data);
			this.setCustomsEnvelopBill(data);
		}
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initComponents() {
		List scmCoces = new ArrayList();
		List<Company> companies = new ArrayList<Company>(); // super.systemAction.findCompanies();
		companies.add((Company) CommonVars.getCurrUser().getCompany());
		if (this.isImportGoods() == true) {
			scmCoces = super.getManufacturer();
			tfImportExport.setText("转入");
			this.cbbImpComp.setModel(new DefaultComboBoxModel(companies
					.toArray()));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbImpComp, "code", "name", 280);
			this.cbbImpComp.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 110, 170));
			this.cbbExpComp.setModel(new DefaultComboBoxModel(scmCoces
					.toArray()));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbExpComp, "code", "name", 250);
			this.cbbExpComp.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 80, 170));
			if (this.cbbImpComp.getItemCount() > 0) {
				this.cbbImpComp.setSelectedIndex(0);
			}
		} else {
			scmCoces = super.getCustomer();
			tfImportExport.setText("转出");
			this.cbbExpComp.setModel(new DefaultComboBoxModel(companies
					.toArray()));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbExpComp, "code", "name", 280);
			this.cbbExpComp.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 110, 170));
			this.cbbImpComp.setModel(new DefaultComboBoxModel(scmCoces
					.toArray()));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					cbbImpComp, "code", "name", 250);
			this.cbbImpComp.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 80, 170));

			if (this.cbbExpComp.getItemCount() > 0) {
				this.cbbExpComp.setSelectedIndex(0);
			}
		}
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());

		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "电子化手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(-1);

		initTable(new ArrayList());
	}

	/**
	 * 
	 * 生效和失效记录并设置状态
	 */
	private void beginAvailability(boolean isAvailability) {
		try {
			this.getCustomsEnvelopBill().setIsAvailability(
					Boolean.valueOf(isAvailability));
			customsEnvelopBill = this.transferFactoryManageAction
					.saveCustomsEnvelopBill(new Request(CommonVars
							.getCurrUser()), getCustomsEnvelopBill());
			this.customsEnvelopModel.updateRow(customsEnvelopBill);
			setState();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "数据可能有误,生效失败!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 
	 * 结案和取消结案并设置状态
	 */
	private void beginEndCase(boolean isEndCase) {
		try {
			this.getCustomsEnvelopBill().setIsEndCase(
					Boolean.valueOf(isEndCase));
			if (isEndCase == true) {
				Date date = new Date();
				this.getCustomsEnvelopBill().setEndCaseDate(date);
			} else {
				this.getCustomsEnvelopBill().setEndCaseDate(null);
			}
			customsEnvelopBill = this.transferFactoryManageAction
					.saveCustomsEnvelopBill(new Request(CommonVars
							.getCurrUser()), getCustomsEnvelopBill());
			this.customsEnvelopModel.updateRow(customsEnvelopBill);
			setState();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "数据可能有误,操作失败!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(6);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(270);
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getPnMaster(), java.awt.BorderLayout.CENTER);
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
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new java.awt.Rectangle(124, 143, 173, 24));
			cbbProjectType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						tfEmsNo.setText("");
					}
					if (cbbProjectType.getSelectedIndex() == 0)
						lbEmsNo.setText("帐册编号");
					else
						lbEmsNo.setText("手册编号");

				}
			});
		}
		return cbbProjectType;
	}

	// private void showEmsNoData(int projectType) {
	// // this.cbbEmsNo.removeAllItems();
	// // List list = this.transferFactoryManageAction.findEmsHeadByProjectType(
	// // new Request(CommonVars.getCurrUser(), true), projectType);
	// // for (int i = 0; i < list.size(); i++) {
	// // this.cbbEmsNo.addItem((TempCustomsEmsInfo) list.get(i));
	// // }
	// // this.cbbEmsNo.setSelectedIndex(-1);
	// this.tfEmsNo.setText("");
	// switch (projectType) {
	// case ProjectType.BCS:
	// lbEmsNo.setText("纸质手册号");
	// break;
	// case ProjectType.BCUS:
	// lbEmsNo.setText("联网监管账册号");
	// break;
	// case ProjectType.DZSC:
	// lbEmsNo.setText("电子手册号");
	// break;
	// }
	// }

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(389, 144, 221, 22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(124, 172, 458, 23));
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(582, 172, 26, 23));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty item = (ItemProperty) cbbProjectType
							.getSelectedItem();

					int projectType = 0;

					if (item != null)
						projectType = Integer.parseInt(item.getCode());
					else {
						JOptionPane.showMessageDialog(
								DgCustomsEnvelopBill.this, "请选择项目类型！！", "警告",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (cbbImpComp.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null,
								isImportGoods() == false ? "转入企业名称不可为空"
										: "转出企业名称不可为空", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (cbbExpComp.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null,
								isImportGoods() == true ? "转出企业名称不可为空"
										: "转入企业名称不可为空", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					DgSelectEmsNo dg = new DgSelectEmsNo();
					dg.setProjectType(projectType);
					dg.setTitle(lbEmsNo.getText());
					dg.setOldEmsNo(tfEmsNo.getText().trim());
					System.out.println(commodityTableModel.getList().size());
					dg.setCanEdit(dataState != DataState.BROWSE
							&& commodityTableModel.getList().size() <= 0);
					// if(customsEnvelopBill == null){
					// customsEnvelopBill = (CustomsEnvelopBill)
					// customsEnvelopModel
					// .getList().get(0);
					// }
					if (customsEnvelopBill != null
							&& customsEnvelopBill.getIsImpExpGoods() != null) {
						dg
								.setImpExpGoods(customsEnvelopBill
										.getIsImpExpGoods());
					}
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = dg.getLsResult();
						StringBuffer sb = new StringBuffer("");
						for (int i = 0; i < list.size(); i++) {
							TempCustomsEmsInfo temp = (TempCustomsEmsInfo) list
									.get(i);
							sb.append(temp.getEmsNo() + ";");
						}
						tfEmsNo.setText(sb.toString());
					}
					// showEmsNoData(projectType);
				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
