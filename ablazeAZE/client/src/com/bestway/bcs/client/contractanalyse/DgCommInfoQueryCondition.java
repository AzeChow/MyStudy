/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcs.contractstat.entity.TempBcsCustomsDeclarCommInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 报关单商品查询窗体
 * 
 * @author Administrator
 * 
 *         lm checked by 2009-06-30
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgCommInfoQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JComboBox cbbCommInfo = null;

	private JComboBox cbbCustomer = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnOk = null;

	private JButton btnExit = null;

	private ContractAnalyseAction contractAnalyseAction = null;

	private ContractStatAction contractStatAction = null;

	private ContractAction contractAction = null;
	/** 已报关的商品 */
	private List lsResult = null; // @jve:decl-index=0:
	/** 是否为料件 */
	private boolean isImport;

	private JScrollPane jScrollPane = null; // @jve:decl-index=0:visual-constraint="458,195"

	private JTable tbContract = null;

	private JTableListModel contractTableModel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbYes = null;

	private JRadioButton rbNo = null;

	private JRadioButton rbAll = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="619,68"

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null; // @jve:decl-index=0:visual-constraint="687,117"

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:visual-constraint="597,142"

	private JLabel jLabel4 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbName = null;

	private JComboBox cbbCode = null;

	private JComboBox cbbSeqNo = null;

	private JPanel jPanel4 = null;

	private JCheckBox cbContractExe = null;

	private JCheckBox cbContractCancel = null;

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgCommInfoQueryCondition() {
		super();
		initialize();
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractStatAction = (ContractStatAction) CommonVars
				.getApplicationContext().getBean("contractStatAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
	}

	/**
	 * this is init
	 */

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 初始化合同
		// this.cbbContract.removeAllItems();
		List list = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()));
		// if (list != null && list.size() > 0) {
		// for (int i = 0; i < list.size(); i++) {
		// this.cbbContract.addItem((Contract) list.get(i));
		// }
		// this.cbbContract.setRenderer(CustomBaseRender.getInstance()
		// .getRender("expContractNo", "emsNo", 100, 100));
		// }
		// this.cbbContract.setSelectedIndex(-1);
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("是否选中", "isSelected", 50));
				list.add(addColumn("合同号", "impContractNo", 100));
				list.add(addColumn("帐册号 ", "emsNo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel.dataBind(tbContract, list, jTableListModelAdapter);
		contractTableModel = (JTableListModel) tbContract.getModel();

		TableColumn column = tbContract.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));

		// contractTableModel = new JTableListModel(tbContract, list,
		// new JTableListModelAdapter() {
		// public List InitColumns() {
		// List<JTableListColumn> list = new Vector<JTableListColumn>();
		// list.add(addColumn("是否选中", "isSelected", 100));
		// list.add(addColumn("合同号", "impContractNo", 100));
		// list.add(addColumn("帐册号 ", "emsNo", 100));
		// return list;
		// }
		// });

		// // 商品
		// this.cbbCommInfo.setModel(new DefaultComboBoxModel(contractStatAction
		// .findCustomsDeclarationCommInfo(
		// new Request(CommonVars.getCurrUser()), isImport, new ArrayList(),
		// CustomsDeclarationState.EFFECTIVED).toArray()));
		// this.cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
		// "seqNum", "name", 100, 100));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbCommInfo, "seqNum", "name");
		// this.cbbCommInfo.setSelectedIndex(-1);
		// // 客户
		// this.cbbCustomer.setModel(new DefaultComboBoxModel(contractStatAction
		// .findCustomsDeclarationCustomer(
		// new Request(CommonVars.getCurrUser()), isImport, new ArrayList(),
		// CustomsDeclarationState.EFFECTIVED).toArray()));
		// this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
		// "code", "name", 100, 100));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbCustomer, "code", "name");
		// this.cbbCustomer.setSelectedIndex(-1);
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (isImport) {
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT), "余料结转(出口)"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMAIN_FORWARD_IMPORT), "余料结转(进口)"));
		} else {
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
		}
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpType.setSelectedIndex(-1);

		this.cbbBeginDate.setDate(null);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSeqNo,
				"seqNum", "seqNum");
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCode,
				"code", "code");
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbName,
				"name", "name");
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCommInfo,
				"code", "name", 400);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustomer,
				"code", "name", 260);
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
			cb = new JCheckBox();
			cb.addActionListener(this);
		}

		/**
		 * 编辑列
		 */

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				// cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			// cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			// cb.removeActionListener(this);
			return cb.isSelected();
		}

		/**
		 * 参数设置
		 */

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof Contract) {
				Contract temp = (Contract) obj;
				temp.setSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
				// showCount(tableModel.getList());
				setCbbState();
				showCommCustomerInfo();
			}

			// System.out.println("aaaaaaaaaaaaaaaaaa");
			fireEditingStopped();
		}
	}

	/**
	 * 设置控件状态
	 */

	private void setCbbState() {
		int k = 0;
		List<Contract> conlist = contractTableModel.getList();
		for (Contract data : conlist) {
			if (data.getIsSelected()) {
				k++;
			}
		}
		chooseConditions(k <= 1);
	}

	/**
	 * 填充数据
	 */

	private void showCommCustomerInfo() {
		List<Contract> lsContract = getSelectContract();
		if (lsContract.size() <= 0) {
			cbbCommInfo.removeAllItems();
			cbbCustomer.removeAllItems();
			return;
		}
		// 商品
		// cbbCommInfo.setModel(new DefaultComboBoxModel(contractStatAction
		// .findCustomsDeclarationCommInfo(
		// new Request(CommonVars.getCurrUser()), isImport,
		// lsContract, CustomsDeclarationState.ALL).toArray()));
		List<TempBcsCustomsDeclarCommInfo> infos = contractStatAction
				.findCustomsDeclarationCommInfo(
						new Request(CommonVars.getCurrUser()), isImport,
						lsContract, CustomsDeclarationState.ALL);
		Map map = new HashMap();
		Map mapName = new HashMap();
		Map mapCode = new HashMap();
		Map mapSeqNo = new HashMap();

		for (TempBcsCustomsDeclarCommInfo data : infos) {
			if (map.get(data.getCode() + data.getName()) == null) {
				map.put((data.getCode() + data.getName() + data.getSpec() + data
						.getUnitName()), data);
			}
			if (mapName.get(data.getName()) == null) {
				mapName.put(data.getName(), data);
			}
			if (mapCode.get(data.getCode()) == null) {
				mapCode.put(data.getCode(), data);
			}
			if (mapSeqNo.get(data.getSeqNum()) == null) {
				mapSeqNo.put(data.getSeqNum(), data);
			}
		}
		List reaultLst = new ArrayList(map.values());
		List listName = new ArrayList(mapName.values());
		List listCode = new ArrayList(mapCode.values());
		List listSeqNo = new ArrayList(mapSeqNo.values());
		cbbCommInfo.setModel(new DefaultComboBoxModel(reaultLst.toArray()));
		cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name+spec+unitName", 70, 460));

		cbbCommInfo.setSelectedIndex(-1);
		// ***********************************************
		cbbName.setModel(new DefaultComboBoxModel(listName.toArray()));
		cbbName.setRenderer(CustomBaseRender.getInstance().getRender("name",
				"name", 0, 160));

		cbbName.setSelectedIndex(-1);
		// ***********************************************
		cbbCode.setModel(new DefaultComboBoxModel(listCode.toArray()));
		cbbCode.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"code", 100, 0));

		cbbCode.setSelectedIndex(-1);

		// ***********************************************
		Collections.sort(listSeqNo);
		cbbSeqNo.setModel(new DefaultComboBoxModel(listSeqNo.toArray()));
		cbbSeqNo.setRenderer(CustomBaseRender.getInstance().getRender("seqNum",
				"seqNum", 100, 0));

		cbbSeqNo.setSelectedIndex(-1);
		// ***********************************************
		// 客户
		cbbCustomer.setModel(new DefaultComboBoxModel(contractStatAction
				.findCustomsDeclarationCustomer(
						new Request(CommonVars.getCurrUser()), isImport,
						lsContract, CustomsDeclarationState.ALL).toArray()));
		cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 160));

		cbbCustomer.setSelectedIndex(-1);
		// ***********************************************

		// if (contract != null) {
		// cbbBeginDate.setDate(contract.getBeginDate());
		// } else {
		// cbbBeginDate.setDate(null);
		// }
	}

	/**
	 * 获取选择行的值
	 * 
	 * @return
	 */

	private List<Contract> getSelectContract() {
		List list = contractTableModel.getList();
		List<Contract> lsContract = new ArrayList<Contract>();
		for (int i = 0; i < list.size(); i++) {
			Contract contract = (Contract) list.get(i);
			if (contract.isSelected()) {
				lsContract.add(contract);
			}
		}
		return lsContract;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(576, 387);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		getButtonGroup1();
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommInfo() {
		if (cbbCommInfo == null) {
			cbbCommInfo = new JComboBox();
			cbbCommInfo.setBounds(new Rectangle(91, 23, 170, 22));
		}
		return cbbCommInfo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(91, 134, 170, 22));
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(new Rectangle(91, 164, 170, 22));
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(91, 192, 170, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(91, 218, 170, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.setBounds(new Rectangle(73, 301, 67, 24));
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnOk;
	}

	/**
	 * 查询已报关的商品
	 */
	private void find() {
		String commCode = "";
		String customer = "";
		String impExpType = "";
		Integer seqNum = null;
		String spec = "";
		String unitName = "";
		String complexCode = "";
		String name = "";
		List lsContract = getSelectContract();
		if (lsContract.size() <= 0) {
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgCommInfoQueryCondition.this,
					"请选择合同", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		} else if (lsContract.size() == 1) {
			if (cbbSeqNo.getSelectedItem() != null) {
				seqNum = ((TempBcsCustomsDeclarCommInfo) cbbSeqNo
						.getSelectedItem()).getSeqNum();
			}
			if (cbbName.getSelectedItem() != null) {
				name = ((TempBcsCustomsDeclarCommInfo) cbbName
						.getSelectedItem()).getName();
			}
			if (cbbCode.getSelectedItem() != null) {
				commCode = ((TempBcsCustomsDeclarCommInfo) cbbCode
						.getSelectedItem()).getCode();
			}
		} else {
			if (cbbCommInfo.getSelectedItem() != null) {
				commCode = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
						.getSelectedItem()).getCode();
				// seqNum = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
				// .getSelectedItem()).getSeqNum();
				name = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
						.getSelectedItem()).getName();
				spec = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
						.getSelectedItem()).getSpec();
				unitName = ((TempBcsCustomsDeclarCommInfo) cbbCommInfo
						.getSelectedItem()).getUnitName();
			}
		}

		if (cbbCustomer.getSelectedItem() != null) {
			customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
		}
		if (cbbImpExpType.getSelectedItem() != null) {
			impExpType = ((ItemProperty) cbbImpExpType.getSelectedItem())
					.getCode();
		}
		Date beginDate = cbbBeginDate.getDate();
		Date endDate = cbbEndDate.getDate();
		int state = -1;
		if (rbYes.isSelected()) {
			state = CustomsDeclarationState.EFFECTIVED;
		} else if (rbNo.isSelected()) {
			state = CustomsDeclarationState.NOT_EFFECTIVED;
		} else if (rbAll.isSelected()) {
			state = state = CustomsDeclarationState.ALL;
		}
		lsResult = contractAnalyseAction.findCommInfoImpExpAnalyse(new Request(
				CommonVars.getCurrUser()), isImport, commCode, name, spec,
				unitName, seqNum, customer, impExpType, beginDate, endDate,
				lsContract, state);
		dispose();
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
			btnExit.setBounds(new Rectangle(160, 301, 67, 24));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setSize(new Dimension(278, 284));
			jScrollPane.setViewportView(getTbContract());
			jScrollPane.setLocation(new Point(0, 70));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbContract() {
		if (tbContract == null) {
			tbContract = new JTable();
		}
		return tbContract;
	}

	/**
	 * 全选
	 * 
	 * @param isSelected
	 */

	private void selectAll(boolean isSelected) {
		if (contractTableModel == null) {
			return;
		}
		List list = contractTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Contract) {
				Contract temp = (Contract) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		contractTableModel.updateRows(list);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(17, 106, 73, 22));
			jLabel7.setText("合同序号");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(17, 79, 73, 22));
			jLabel6.setText("商品编码");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(17, 51, 73, 22));
			jLabel4.setText("商品名称");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(17, 23, 73, 22));
			jLabel3.setText("查询商品");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(17, 134, 73, 22));
			jLabel2.setText("查询客户");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(75, 218, 15, 22));
			jLabel5.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 192, 73, 22));
			jLabel1.setText("报关单日期从");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(17, 164, 73, 22));
			jLabel.setText("进出口类型");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getBtnExit(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbImpExpType(), null);
			jPanel.add(getCbbCustomer(), null);
			jPanel.add(getCbbCommInfo(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getCbbName(), null);
			jPanel.add(getCbbCode(), null);
			jPanel.add(getCbbSeqNo(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(280);
			jSplitPane.setDividerSize(6);
			jSplitPane.setLeftComponent(getJPanel2());
			jSplitPane.setRightComponent(getJPanel());
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
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(13, 254, 261, 34));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel1.add(getRbYes(), null);
			jPanel1.add(getRbNo(), null);
			jPanel1.add(getRbAll(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbYes() {
		if (rbYes == null) {
			rbYes = new JRadioButton();
			rbYes.setBounds(new java.awt.Rectangle(9, 13, 71, 20));
			rbYes.setText("已生效");
			rbYes.setSelected(true);
		}
		return rbYes;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNo() {
		if (rbNo == null) {
			rbNo = new JRadioButton();
			rbNo.setBounds(new java.awt.Rectangle(95, 13, 86, 20));
			rbNo.setText("未生效");
		}
		return rbNo;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAll() {
		if (rbAll == null) {
			rbAll = new JRadioButton();
			rbAll.setBounds(new java.awt.Rectangle(188, 13, 52, 20));

			rbAll.setText("全部");
		}
		return rbAll;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbYes);
			buttonGroup.add(this.rbNo);
			buttonGroup.add(this.rbAll);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJPanel3());
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel2.add(getJPanel4(), null);
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
			jPanel3.setLayout(null);
			jPanel3.setSize(new java.awt.Dimension(148, 27));
			jPanel3.setLocation(new java.awt.Point(67, 1));
			jPanel3.add(getJRadioButton(), null);
			jPanel3.add(getJRadioButton1(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(2, 3, 70, 23));
			jRadioButton.setText("全选");
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					selectAll(true);
					showCommCustomerInfo();
					setCbbState();

				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new java.awt.Rectangle(76, 1, 71, 25));
			jRadioButton1.setText("全否");
			jRadioButton1.setSelected(true);
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					selectAll(false);
					showCommCustomerInfo();
					setCbbState();
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton());
			buttonGroup1.add(getJRadioButton1());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes cbbName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbName() {
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setBounds(new Rectangle(91, 51, 170, 22));
		}
		return cbbName;
	}

	/**
	 * This method initializes cbbCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCode() {
		if (cbbCode == null) {
			cbbCode = new JComboBox();
			cbbCode.setBounds(new Rectangle(91, 79, 170, 22));
		}
		return cbbCode;
	}

	/**
	 * This method initializes cbbSeqNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSeqNo() {
		if (cbbSeqNo == null) {
			cbbSeqNo = new JComboBox();
			cbbSeqNo.setBounds(new Rectangle(91, 106, 170, 22));
		}
		return cbbSeqNo;
	}

	/**
	 * 设置状态
	 * 
	 * @param isSingle
	 */

	private void chooseConditions(boolean isSingle) {
		cbbCommInfo.setEnabled(!isSingle);
		cbbName.setEnabled(isSingle);
		cbbCode.setEnabled(isSingle);
		cbbSeqNo.setEnabled(isSingle);
	}

	/**
	 * 多线程类
	 * 
	 * @author Administrator
	 * 
	 */

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			find();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			jPanel4 = new JPanel();
			jPanel4.setLayout(gridLayout);
			jPanel4.setBounds(new Rectangle(69, 29, 187, 39));
			jPanel4.add(getCbContractExe(), null);
			jPanel4.add(getCbContractCancel(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes cbContractExe
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setSelected(true);
			cbContractExe.setText("\u6b63\u5728\u6267\u884c\u7684\u5408\u540c");
			cbContractExe
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								selectContract(true, true);
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								selectContract(true, false);
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								selectContract(false, true);
							} else {
								selectContract(false, false);
							}
						}
					});
		}
		return cbContractExe;
	}

	/**
	 * 选择正在执行或核销的合同
	 * 
	 * @param exe
	 * @param cancel
	 */
	protected void selectContract(boolean exe, boolean cancel) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("是否选中", "isSelected", 50));
				list.add(addColumn("合同号", "impContractNo", 100));
				list.add(addColumn("帐册号 ", "emsNo", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		List list = new ArrayList();
		if (exe) {
			list.addAll(contractAction.findContractByProcessExe(new Request(
					CommonVars.getCurrUser(), true)));
		}
		if (cancel) {

			list.addAll(contractAction.findContract(
					new Request(CommonVars.getCurrUser(), true), true));
		}
		tbContract.removeAll();
		JTableListModel.dataBind(tbContract, list, jTableListModelAdapter);
		contractTableModel = (JTableListModel) tbContract.getModel();

		TableColumn column = tbContract.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));

	}

	/**
	 * This method initializes cbContractCancel
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("\u6838\u9500\u7684\u5408\u540c");
			cbContractCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								selectContract(true, true);
							} else if (cbContractExe.isSelected()
									&& !cbContractCancel.isSelected()) {
								selectContract(true, false);
							} else if (!cbContractExe.isSelected()
									&& cbContractCancel.isSelected()) {
								selectContract(false, true);
							} else {
								selectContract(false, false);
							}
						}
					});
		}
		return cbContractCancel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
