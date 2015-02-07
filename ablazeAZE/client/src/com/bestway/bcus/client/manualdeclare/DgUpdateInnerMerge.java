package com.bestway.bcus.client.manualdeclare;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.TempOfInnerMergeData;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.JCheckBox;

public class DgUpdateInnerMerge extends JDialogBase {

	private JPanel pnMain = null;
	private JPanel pnTime = null;
	private JPanel pnNext = null;
	private JButton btnUp = null;
	private JButton btnDown = null;
	private JButton btnYes = null;
	private JButton btnExit = null;
	private JLabel lbStartTime = null;
	private JLabel lbEndTime = null;
	private JCalendarComboBox cbStartDate = null;
	private JCalendarComboBox cbEndDate = null;
	private JLabel lbInfo = null;
	private JPanel spnList = null;
	private JTable tbList = null;
	private int sept = 1;
	private Date startDate = null; // @jve:decl-index=0:
	private Date endDate = null; // @jve:decl-index=0:
	private List innerMergeList = null;
	private ManualDeclareAction manualDeclearAction = null;
	private JTableListModel tableModel = null;
	/**
	 * 电子账册归并关系
	 */
	private EmsEdiMergerHead emsEdiHead = null; // @jve:decl-index=0:
	/**
	 * 电子账册管理
	 */
	private EmsHeadH2k emsHead = null; // @jve:decl-index=0:
	private JScrollPane spnListTable = null;
	private JPanel pnInfo = null;
	private JLabel lbInfo1 = null;
	private JCheckBox cbUpdateAll = null;
	private JCheckBox cbAdd = null;
	private JCheckBox cbChange = null;
	private JLabel lbInfo2 = null;
	private boolean isEms = true;

	/**
	 * This method initializes
	 * 
	 */
	public DgUpdateInnerMerge() {
		super();
		initialize();
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(460, 280));
		this.setTitle("同步内部归并");
		this.setContentPane(getPnMain());
		setState();

	}

	private void setState() {
		if (sept == 1) {
			getPnMain().add(getPnTime(), BorderLayout.CENTER);
			getPnTime().setVisible(true);
			getSpnList().setVisible(false);
			btnUp.setEnabled(false);
			btnDown.setEnabled(true);
			btnYes.setEnabled(false);
			this.setSize(new Dimension(460, 280));
		} else if (sept == 2) {
			startDate = cbStartDate.getDate();
			endDate = cbEndDate.getDate();
			innerMergeList = manualDeclearAction.getInnerMergeListByDate(isEms,
					cbAdd.isSelected(), cbChange.isSelected(), emsEdiHead,
					emsHead, startDate, endDate);
			initTable(innerMergeList);
			getPnMain().add(getSpnList(), BorderLayout.CENTER);
			getPnTime().setVisible(false);
			getSpnList().setVisible(true);
			btnUp.setEnabled(true);
			btnDown.setEnabled(false);
			btnYes.setEnabled(true);
			this.setSize(new Dimension(700, 400));
		}

	}

	/**
	 * This method initializes pnMain
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = -1;
			gridBagConstraints.gridy = -1;
			pnMain = new JPanel();
			pnMain.setLayout(new BorderLayout());
			pnMain.add(getPnTime(), BorderLayout.CENTER);
			pnMain.add(getPnNext(), BorderLayout.SOUTH);

		}
		return pnMain;
	}

	/**
	 * This method initializes pnTime
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTime() {
		if (pnTime == null) {
			lbInfo2 = new JLabel();
			lbInfo2.setBounds(new Rectangle(12, 109, 163, 23));
			lbInfo2.setForeground(Color.red);
			lbInfo2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbInfo2.setText("选择同步内部归并的状态");
			lbInfo2.setFont(new Font("\u65b0\u5b8b\u4f53", Font.PLAIN, 12));
			lbInfo = new JLabel();
			lbInfo.setBounds(new Rectangle(12, 4, 293, 23));
			lbInfo.setFont(new Font("\u65b0\u5b8b\u4f53", Font.PLAIN, 12));
			lbInfo.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbInfo.setForeground(Color.red);
			lbInfo.setText("选择同步内部归并的时间（内部归并修改时间）");
			lbEndTime = new JLabel();
			lbEndTime.setBounds(new Rectangle(212, 62, 48, 18));
			lbEndTime.setText("结束日期");
			lbStartTime = new JLabel();
			lbStartTime.setBounds(new Rectangle(18, 62, 48, 18));
			lbStartTime.setText("开始日期");
			pnTime = new JPanel();
			pnTime.setLayout(null);
			pnTime.add(lbStartTime, null);
			pnTime.add(lbEndTime, null);
			pnTime.add(getCbStartDate(), null);
			pnTime.add(getCbEndDate(), null);
			pnTime.add(lbInfo, null);
			pnTime.add(getCbAdd(), null);
			pnTime.add(getCbChange(), null);
			pnTime.add(lbInfo2, null);
		}
		return pnTime;
	}

	/**
	 * This method initializes pnNext
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnNext() {
		if (pnNext == null) {
			pnNext = new JPanel();
			pnNext.setLayout(new GridBagLayout());
			pnNext.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createLineBorder(Color.gray, 1), "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnNext.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED), "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			pnNext.add(getBtnUp(), new GridBagConstraints());
			pnNext.add(getBtnDown(), new GridBagConstraints());
			pnNext.add(getBtnYes(), new GridBagConstraints());
			pnNext.add(getBtnExit(), new GridBagConstraints());
		}
		return pnNext;
	}

	/**
	 * This method initializes btnUp
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUp() {
		if (btnUp == null) {
			btnUp = new JButton();
			btnUp.setText("上一步");
			btnUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					sept--;
					setState();
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
			btnDown.setText("下一步");
			btnDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					sept++;
					setState();
				}
			});
		}
		return btnDown;
	}

	/**
	 * This method initializes btnYes
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnYes() {
		if (btnYes == null) {
			btnYes = new JButton();
			btnYes.setText("同步");
			btnYes.setActionCommand("");
			btnYes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							DgUpdateInnerMerge.this, "您确认要同步吗?",
							"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List tempList = new ArrayList();
					for (int i = 0; i < innerMergeList.size(); i++) {
						tempList.add(((TempOfInnerMergeData) innerMergeList
								.get(i)).getData());
					}
					// 是否更新归并关系、帐册管理
					if (cbUpdateAll.isSelected()) {
						if (emsEdiHead != null) {// 查找帐册管理中"申请变更"或"申请备案"的帐册
							List list = manualDeclearAction
									.findEmsHeadH2k(CommonUtils.getRequest());
							for (int i = 0; i < list.size(); i++) {
								EmsHeadH2k k = (EmsHeadH2k) list.get(0);
								if (k.getDeclareState().equals("1")
										|| k.getDeclareState().equals("4"))
									emsHead = k;
							}
						} else {// 查找归并关系中"申请变更"或"申请备案"的帐册
							List list = manualDeclearAction
									.findEmsEdiMergerHead(CommonUtils
											.getRequest());
							for (int i = 0; i < list.size(); i++) {
								EmsEdiMergerHead k = (EmsEdiMergerHead) list
										.get(0);
								if (k.getDeclareState().equals("1")
										|| k.getDeclareState().equals("4"))
									emsEdiHead = k;
							}
						}
					}
					List returnInfo = new ArrayList();
					if (emsEdiHead != null) {
						returnInfo = manualDeclearAction.updateEmsEdiMerger(
								emsEdiHead, tempList);
					}
					if (emsHead != null) {
						returnInfo.addAll(manualDeclearAction.updateEmsHeadH2k(
								emsHead, tempList));
					}
					btnUp.setEnabled(false);
					btnYes.setEnabled(false);
					String inFo = "";
					for (int i = 0; i < returnInfo.size(); i++) {
						inFo += returnInfo.get(i);
					}
					JOptionPane.showMessageDialog(DgUpdateInnerMerge.this,
							inFo, "返回信息", -1);
				}
			});
		}
		return btnYes;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("取消");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes cbStartDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbStartDate() {
		if (cbStartDate == null) {
			cbStartDate = new JCalendarComboBox();
			cbStartDate.setBounds(new Rectangle(74, 59, 101, 22));
		}
		return cbStartDate;
	}

	/**
	 * This method initializes cbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbEndDate() {
		if (cbEndDate == null) {
			cbEndDate = new JCalendarComboBox();
			cbEndDate.setBounds(new Rectangle(271, 59, 101, 22));
		}
		return cbEndDate;
	}

	/**
	 * This method initializes spnList
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JPanel getSpnList() {
		if (spnList == null) {
			spnList = new JPanel();
			spnList.setLayout(new BorderLayout());
			spnList.add(getSpnListTable(), BorderLayout.CENTER);
			spnList.add(getPnInfo(), BorderLayout.NORTH);
		}
		return spnList;
	}

	/**
	 * This method initializes tbList
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbList() {
		if (tbList == null) {
			tbList = new JTable();
		}
		return tbList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public EmsEdiMergerHead getHead() {
		return emsEdiHead;
	}

	public void setHead(EmsEdiMergerHead head) {
		this.emsEdiHead = head;
	}

	public EmsHeadH2k getEmsHead() {
		return emsHead;
	}

	public void setEmsHead(EmsHeadH2k emsHead) {
		this.emsHead = emsHead;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(tbList, list,
				new JTableListModelAdapter(4) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("物料类别", "data.imrType", 60));
						list.add(addColumn("归并后状态", "changeTypeAfter", 70));
						if(!isEms)
						list.add(addColumn("归并前状态", "changeTypeBefore", 70));
						list.add(addColumn("料号", "data.materiel.ptNo", 100));
						list.add(addColumn("10位商品编码",
								"data.hsAfterComplex.code", 100));
						list.add(addColumn("商品名称.规格,型号",
								"data.hsBeforeMaterielNameSpec",
								"data.hsAfterMaterielTenName", 150));
						list.add(addColumn("法定单位",
								"data.hsAfterComplex.firstUnit.name", 50));
						list.add(addColumn("企业单位",
								"data.materiel.calUnit.name", 50));
						list.add(addColumn("备案序号", "data.hsAfterTenMemoNo", 50,
								Integer.class));
						list.add(addColumn("10位商品编码",
								"data.hsAfterComplex.code", 100));
						list.add(addColumn("商品名称",
								"data.hsAfterMaterielTenName", 120));

						list.add(addColumn("商品规格,型号",
								"data.hsAfterMaterielTenSpec", 120));
						list.add(addColumn("法定单位一",
								"data.hsAfterComplex.firstUnit.name", 50));

						list.add(addColumn("法定单位二",
								"data.hsAfterComplex.secondUnit.name", 50));
						list.add(addColumn("备案单位", "data.hsAfterMemoUnit.name",
								50));
						list.add(addColumn("4位编码序号", "data.hsFourNo", 70,
								Integer.class));
						list.add(addColumn("4位商品名称", "data.hsFourMaterielName",
								120));
						list.add(addColumn("4位商品编码", "data.hsFourCode", 70));
						list.add(addColumn("是否已备案", "data.isMerger",
								"data.isExistMerger", DataType.BOOLEAN, 70));
						list.add(addColumn("导入时间", "data.importTimer", 70));
						list.add(addColumn("修改时间", "data.updateDate", 70));
						return list;
					}
				});
		EmsEdiMergerClientLogic.MaterielTypeState(tbList);
	}

	/**
	 * This method initializes spnListTable
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpnListTable() {
		if (spnListTable == null) {
			spnListTable = new JScrollPane();
			spnListTable.setViewportView(getTbList());
		}
		return spnListTable;
	}

	/**
	 * This method initializes pnInfo
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnInfo() {
		if (pnInfo == null) {
			lbInfo1 = new JLabel();
			lbInfo1.setText("要进行同步的内部归并信息");
			lbInfo1.setForeground(Color.red);
			lbInfo1.setFont(new Font("Dialog", Font.PLAIN, 12));
			pnInfo = new JPanel();
			pnInfo.setLayout(new GridBagLayout());
			pnInfo.add(lbInfo1, new GridBagConstraints());
			pnInfo.add(getCbUpdateAll(), new GridBagConstraints());
		}
		return pnInfo;
	}

	/**
	 * This method initializes cbUpdateAll
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUpdateAll() {
		if (cbUpdateAll == null) {
			cbUpdateAll = new JCheckBox();
			cbUpdateAll.setText("同时更新到（归并关系/帐册管理）");
		}
		return cbUpdateAll;
	}

	/**
	 * This method initializes cbAdd
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAdd() {
		if (cbAdd == null) {
			cbAdd = new JCheckBox();
			cbAdd.setBounds(new Rectangle(228, 110, 49, 26));
			cbAdd.setText("新增");
			cbAdd.setSelected(true);
		}
		return cbAdd;
	}

	/**
	 * This method initializes cbChange
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbChange() {
		if (cbChange == null) {
			cbChange = new JCheckBox();
			cbChange.setBounds(new Rectangle(306, 110, 49, 26));
			cbChange.setText("变更");
		}
		return cbChange;
	}

	public boolean isEms() {
		return isEms;
	}

	public void setEms(boolean isEms) {
		this.isEms = isEms;
	}

}
