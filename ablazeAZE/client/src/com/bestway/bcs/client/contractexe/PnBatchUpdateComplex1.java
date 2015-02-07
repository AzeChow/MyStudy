/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.entity.CommInfoImpExp;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class PnBatchUpdateComplex1 extends JPanelBase {

	private JTableListModel tableModel = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton btnSave = null;

	private ContractExeAction contractExeAction = null;
	private CustomBaseAction customBaseAction = null;

	private JButton btnDelete = null;

	private JPanel jPanel = null;

	private JButton btnAdd = null;

	private JButton btnDone = null;

	private JLabel jLabel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel1 = null;

	private JTextField tfComplex = null;

	private JButton btnComplex = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbFirstUnit = null;

	private JComboBox cbbSecondUnit = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JCheckBox cbbSendData = null;

	// =================================
	public String emsType = "0"; // @jve:decl-index=0://是电子帐册，归并关系、内部归并
	public Boolean isMaterial = false; // @jve:decl-index=0://是料件还是成品
	private Boolean isExe = false; // @jve:decl-index=0:
	private Boolean isModify = false; // @jve:decl-index=0:

	private JCheckBox cbbAll = null;

	private JCheckBox cbbSingle = null;

	private JComboBox cbbEmsNo = null;

	private JToolBar jToolBar = null;

	private JLabel jLabel6 = null;

	public void setEmsType(String emsType) {
		this.emsType = emsType;
	}

	public void setIsMaterial(Boolean isMaterial) {
		this.isMaterial = isMaterial;
	}

	public void setIsExe(Boolean isExe) {
		this.isExe = isExe;
	}
	public Boolean getIsExe() {
		return this.isExe;
	}

	public void setIsModify(Boolean isModify) {
		this.isModify = isModify;
	}

	// ===========================
	/**
	 * This is the default constructor
	 */
	public PnBatchUpdateComplex1() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		initialize();

	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			btnDone.setEnabled(false);
			initUIComponents();
			initTable(new ArrayList());
		}
		super.setVisible(isFlag);
	}

	private void initUIComponents() {
		// 初始化第一单位
		this.cbbFirstUnit
				.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbFirstUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFirstUnit);
		cbbFirstUnit.setSelectedItem(null);
		// 初始化第二单位
		this.cbbSecondUnit.setModel(CustomBaseModel.getInstance()
				.getUnitModel());
		this.cbbSecondUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbSecondUnit);
		cbbSecondUnit.setSelectedItem(null);

		String decelareState = "";
		if (isExe) {
			decelareState = DeclareState.PROCESS_EXE;
		} else if (isModify) {
			decelareState = DeclareState.CHANGING_EXE;
		}
		// 电子化手册
		cbbEmsNo.removeAllItems();
		List contracts = contractExeAction.findContractByDeclareState(
				new Request(CommonVars.getCurrUser(), true), decelareState);
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmsNo.addItem((Contract) contracts.get(i));
			}
			this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
					"emsNo", "impContractNo", 100, 150).setForegroundColor(
					java.awt.Color.red));
		}
		if (this.cbbEmsNo.getItemCount() > 0) {
			this.cbbEmsNo.setSelectedIndex(0);
		}

		if ("0".equals(emsType)) {// 合同备案
			cbbSingle.setEnabled(false);

			cbbAll.setSelected(true);
			cbbSingle.setSelected(false);
		} else if ("1".equals(emsType)) {// 备案资料库
			cbbSingle.setEnabled(true);

			cbbEmsNo.removeAllItems();
			cbbSingle.setSelected(true);
			cbbAll.setSelected(false);
		} else if ("2".equals(emsType)) {// 对应关系
			cbbSingle.setEnabled(false);

			cbbEmsNo.removeAllItems();
			cbbSingle.setSelected(false);
			cbbAll.setSelected(false);
		}
		cbbSendData.setEnabled(isModify);
		cbbEmsNo.setEnabled("0".equals(emsType));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(733, 332);
		this.add(getJPanel(), BorderLayout.CENTER);
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
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(new Rectangle(61, 273, 90, 25));
			btnSave.setPreferredSize(new Dimension(90, 25));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(CommonUtils.isEmpty(tfComplex.getText())) {
						JOptionPane.showMessageDialog(
								PnBatchUpdateComplex1.this, "请选择商品编码", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						
						return ;
					}
					
					btnDone.setEnabled(true);
					List result = new ArrayList();
					for (int i = 0; i < tableModel.getCurrentRows().size(); i++) {
						TempCustomsDeclarationCommInfo commInfo = (TempCustomsDeclarationCommInfo) tableModel
								.getCurrentRows().get(i);
						commInfo.setDetailNote(commInfo.getComplex().getId()); // 旧编码id
						commInfo.setComplex(customBaseAction
								.findComplexByCode(tfComplex.getText().trim()));
						commInfo.setLegalUnit((Unit) cbbFirstUnit
								.getSelectedItem());
						commInfo.setLegalUnit2((Unit) cbbSecondUnit
								.getSelectedItem());
						
						result.add(commInfo);

					}
					tableModel.updateRows(result);
				}
			});
		}
		return btnSave;
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
	 * 初始化数据Table
	 */
	private JTableListModel initTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complex.code", 80));
						if(isMaterial){
							list.add(addColumn("料件序号", "emsSerialNo", 60));
						} else {
							list.add(addColumn("成品序号", "emsSerialNo", 60));
						}
						list.add(addColumn("第一法定单位", "complex.firstUnit.name",
								70));
						list.add(addColumn("第二法定单位", "complex.secondUnit.name",
								70));
						list.add(addColumn("名称", "name", 200));
						if (!"2".equals(emsType)) {// 十码对应关系
							list.add(new JTableListColumn("记录号", "credenceNo",
									70));
						}
						return list;
					}
				});
		return tableModel;
		// tableModel = new JTableListModel(jTable, list,
		// jTableListModelAdapter);
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("移出商品");
			btnDelete.setPreferredSize(new Dimension(85, 25));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List result = new ArrayList();
					for (int i = 0; i < tableModel.getCurrentRows().size(); i++) {
						TempCustomsDeclarationCommInfo objs = (TempCustomsDeclarationCommInfo) tableModel
								.getList().get(i);
						result.add(objs);
					}
					tableModel.deleteRows(result);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("第一步：用鼠标拖拉选择要修改商品的物料；当选择错后还可以移出商品");
			jLabel.setBounds(new Rectangle(41, 4, 675, 18));
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(jLabel, BorderLayout.NORTH);
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增商品");
			btnAdd.setPreferredSize(new Dimension(85, 25));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String decelareState = "";
					if (isExe) {
						decelareState = DeclareState.PROCESS_EXE;
					} else if (isModify) {
						decelareState = DeclareState.CHANGING_EXE;
					}
					Contract contract = (Contract) cbbEmsNo.getSelectedItem();
					List list = CommonQuery.getInstance()
							.getBcsBatchUpdateComplex(
									decelareState,
									contract == null ? null : contract
											.getEmsNo(), emsType, isMaterial);
					if (list != null && list.size() > 0) {
						initTable(list);
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnDone
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setPreferredSize(new Dimension(90, 25));
			btnDone.setBounds(new Rectangle(169, 272, 90, 25));
			btnDone.setText("同步");
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRows().size() <= 0) {
						JOptionPane.showMessageDialog(
								PnBatchUpdateComplex1.this, "请选择同步的数据", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					new UpdateComplex(emsType, cbbAll.isSelected(), cbbSingle
							.isSelected(), cbbSendData.isSelected()).start();

				}
			});
		}
		return btnDone;
	}

	/**
	 * 商品同步
	 * 
	 * @author Administrator
	 * 
	 */
	class UpdateComplex extends Thread {

		private String emsHType = "0";
		private boolean isSycAll = false;
		private boolean isSycSingle = false;
		private boolean isSendDate = false;

		public UpdateComplex(String emsType, boolean isSycAll,
				boolean isSycSingle, boolean isSendDate) {
			this.emsHType = emsType;
			this.isSycAll = isSycAll;
			this.isSycSingle = isSycSingle;
			this.isSendDate = isSendDate;
		}

		public void run() {
			try {
				btnDone.setEnabled(false);
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正同步数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				String decelareState = "";
				if (isExe) {
					decelareState = DeclareState.PROCESS_EXE;
				} else if (isModify) {
					decelareState = DeclareState.CHANGING_EXE;
				}
				if ("0".equals(emsHType)) {// 合同备案
					String contractId = ((Contract) cbbEmsNo.getSelectedItem()).getId();
					for (int i = 0; i < tableModel.getCurrentRows().size(); i++) {
						TempCustomsDeclarationCommInfo commInfo = (TempCustomsDeclarationCommInfo) tableModel
								.getCurrentRows().get(i);
						if (commInfo.getComplex() == null) {
							CommonStepProgress.closeStepProgressDialog();
							JOptionPane.showMessageDialog(
									PnBatchUpdateComplex1.this, "同步数据有错!",
									"提示", 2);
							return;
						}
						if (isSycAll) {// 合同备案选择后是否同步所有
							contractExeAction.updateAllContractComplex(
									new Request(CommonVars.getCurrUser()),
									decelareState, isMaterial, commInfo
											.getComplex(), commInfo.getDetailNote(),
									Integer.valueOf(commInfo.getEmsSerialNo()),
									isSendDate, contractId);
							if (commInfo.getCredenceNo() != null) {
								contractExeAction.updateAllBcsDictPorComplex(
										new Request(CommonVars.getCurrUser()),
										decelareState, isMaterial, commInfo
												.getComplex(), commInfo.getDetailNote(), (commInfo
												.getCredenceNo() == null ? null
												: Integer.valueOf(commInfo
														.getCredenceNo())),
										isSendDate);
								List list1 = contractExeAction
										.findBcsDictPorImgOrExgInnerMergeSeqNum(
												new Request(CommonVars
														.getCurrUser()),
												decelareState, isMaterial,
												commInfo.getCredenceNo());
								if (list1 != null && list1.size() > 0) {
									if (list1.get(0) != null) {
										contractExeAction
												.updateAllBcsInnerMergeDataComplex(
														new Request(CommonVars
																.getCurrUser()),
														isMaterial, commInfo
																.getComplex(),
														Integer.valueOf(list1
																.get(0)
																.toString()));
									}
								}
							}

						} else {
							contractExeAction.updateAllContractComplex(
									new Request(CommonVars.getCurrUser()),
									decelareState, isMaterial, commInfo
											.getComplex(), commInfo.getDetailNote(),
									Integer.valueOf(commInfo.getEmsSerialNo()),
									isSendDate, contractId);
						}
					}
				} else if ("1".equals(emsHType)) {// 备案资料库
					for (int i = 0; i < tableModel.getCurrentRows().size(); i++) {
						TempCustomsDeclarationCommInfo commInfo = (TempCustomsDeclarationCommInfo) tableModel
								.getCurrentRows().get(i);
						if (isSycSingle) {// 备案资料库选择后是否同步所有
							contractExeAction.updateAllBcsDictPorComplex(
									new Request(CommonVars.getCurrUser()),
									decelareState, isMaterial, commInfo
											.getComplex(), commInfo.getDetailNote(),
									Integer.valueOf(commInfo.getEmsSerialNo()),
									isSendDate);
							if (commInfo.getCredenceNo() != null) {
								contractExeAction
										.updateAllBcsInnerMergeDataComplex(
												new Request(CommonVars
														.getCurrUser()),
												isMaterial,
												commInfo.getComplex(),
												(commInfo.getCredenceNo() == null ? null
														: Integer
																.valueOf(commInfo
																		.getCredenceNo())));
							}
						} else {
							contractExeAction.updateAllBcsDictPorComplex(
									new Request(CommonVars.getCurrUser()),
									decelareState, isMaterial, commInfo
											.getComplex(), commInfo.getDetailNote(),
									Integer.valueOf(commInfo.getEmsSerialNo()),
									isSendDate);
						}
					}

				} else if ("2".equals(emsHType)) {// 对应关系
					for (int i = 0; i < tableModel.getCurrentRows().size(); i++) {
						TempCustomsDeclarationCommInfo commInfo = (TempCustomsDeclarationCommInfo) tableModel
								.getCurrentRows().get(i);
						contractExeAction.updateAllBcsInnerMergeDataComplex(
								new Request(CommonVars.getCurrUser()),
								isMaterial, commInfo.getComplex(), Integer
										.valueOf(commInfo.getEmsSerialNo()));
					}
				}
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(PnBatchUpdateComplex1.this,
						"数据同步完毕!", "提示", 2);
 			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(PnBatchUpdateComplex1.this,
						"同步数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
				btnDone.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(420);
			jSplitPane.setDividerSize(2);
			jSplitPane.setLeftComponent(getJPanel2());
			jSplitPane.setRightComponent(getJPanel1());
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
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(7, 30, 324, 18));
			jLabel5.setText("第三步：确认数据无错后用鼠标拖拉选择多条再点同步");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(6, 7, 279, 18));
			jLabel4.setText("第二步：输入要修改的商品编码，然后点保存；");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(20, 236, 106, 18));
			jLabel3.setText("第二法定单位：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(20, 200, 106, 18));
			jLabel2.setText("第一法定单位：");
			jLabel1 = new JLabel();
			jLabel1.setText("请输入商品编码：");
			jLabel1.setBounds(new Rectangle(20, 163, 106, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getBtnDone(), null);
			jPanel1.add(getBtnSave(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfComplex(), null);
			jPanel1.add(getBtnComplex(), null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getCbbFirstUnit(), null);
			jPanel1.add(getCbbSecondUnit(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getCbbSendData(), null);
			jPanel1.add(getCbbAll(), null);
			jPanel1.add(getCbbSingle(), null);
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
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel2.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(127, 158, 144, 25));
		}
		return tfComplex;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(new Rectangle(271, 157, 24, 25));
			btnComplex.setText(".....");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex s = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (s != null) {
						tfComplex.setText(s.getCode());
						cbbFirstUnit.setSelectedItem(s.getFirstUnit());
						cbbSecondUnit.setSelectedItem(s.getSecondUnit());
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes cbbFirstUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFirstUnit() {
		if (cbbFirstUnit == null) {
			cbbFirstUnit = new JComboBox();
			cbbFirstUnit.setEnabled(false);
			cbbFirstUnit.setBounds(new Rectangle(127, 193, 167, 25));
		}
		return cbbFirstUnit;
	}

	/**
	 * This method initializes cbbSecondUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSecondUnit() {
		if (cbbSecondUnit == null) {
			cbbSecondUnit = new JComboBox();
			cbbSecondUnit.setEnabled(false);
			cbbSecondUnit.setBounds(new Rectangle(127, 232, 166, 25));
		}
		return cbbSecondUnit;
	}

	/**
	 * This method initializes cbbSendData
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbSendData() {
		if (cbbSendData == null) {
			cbbSendData = new JCheckBox();
			cbbSendData.setBounds(new Rectangle(11, 117, 298, 21));
			cbbSendData.setForeground(Color.BLUE);
			cbbSendData.setText("需要向海关发送");
		}
		return cbbSendData;
	}

	/**
	 * This method initializes cbbAll
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbAll() {
		if (cbbAll == null) {
			cbbAll = new JCheckBox();
			cbbAll.setBounds(new Rectangle(11, 60, 298, 26));
			cbbAll.setText("修改合同资料同时修改备案资料库与对应关系");
			cbbAll.setSelected(true);
			cbbAll.setEnabled(false);
		}
		return cbbAll;
	}

	/**
	 * This method initializes cbbSingle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbSingle() {
		if (cbbSingle == null) {
			cbbSingle = new JCheckBox();
			cbbSingle.setBounds(new Rectangle(11, 88, 298, 26));
			cbbSingle.setText("修改备案资料库同时修改对应关系");
		}
		return cbbSingle;
	}

	/**
	 * This method initializes cbbEmsNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setPreferredSize(new Dimension(120, 25));
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("请选择手册号");
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(jLabel6);
			jToolBar.add(getCbbEmsNo());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
		}
		return jToolBar;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
