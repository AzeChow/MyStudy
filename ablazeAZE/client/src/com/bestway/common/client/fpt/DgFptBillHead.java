package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings({ "unchecked", "serial" })
public class DgFptBillHead extends DgCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List list = null; // @jve:decl-index=0:

	private FptBillHead head = null;// // @jve:decl-index=0:

	private String fptInOutFlag = FptInOutFlag.OUT; // 转出转入标志 //
													// @jve:decl-index=0:

	private String fptBusinessType = FptBusinessType.FPT_BILL; // @jve:decl-index=0:

	private int dataState = -1;

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;

	private JButton btnPrint = null;

	private JButton btnUndo = null;

	private JButton btnCheck = null;

	private JButton btnChangeDeclareState = null;

	private JButton btnCopyBill = null;

	private JButton btninput = null;

	private JButton btnClose = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnBase = null;

	private JLabel jLabel2 = null;

	private JTextField tfUser = null;

	private JTextField tfExpIsDeclaEm = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel6 = null;

	private JTextField tfbillNo = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel16 = null;

	private JTextField tfseqNo = null;

	private JLabel jLabel18 = null;

	private JTextField tfappNo = null;

	private JLabel jLabel19 = null;

	private JTextField tfOutEmsNo = null;

	private JLabel jLabel21 = null;

	private JTextField tfExCopName = null;

	private JLabel jLabel22 = null;

	private JLabel jLabel23 = null;

	private JLabel jLabel25 = null;

	private JTextField tfImCopName = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel33 = null;

	private JLabel jLabel37 = null;

	private JCalendarComboBox tfExpIsDeclaDate = null;

	private JLabel jLabel41 = null;

	private JTextField tfExpNote = null;

	private JTextField tfContractNo = null;

	private JTextField tfExpCopBillNo = null;

	private JLabel jLabel101 = null;

	private JTextField tfTradeName = null;

	private JPanel pnExgBom = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel7 = null;

	private JToolBar jToolBar7 = null;

	private JButton btnAddExgBill = null;

	private JButton btnEditExgBill = null;

	private JButton btnDeleteExgBill = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbExpDetail = null;

	private JPanel jPanel8 = null;

	private JToolBar jToolBar8 = null;

	private JButton btnAddImpBill = null;

	private JButton btnEditImpBill = null;

	private JButton btnDeleteImpBill = null;

	private JButton btnChangeBomModifyMark = null;

	private JScrollPane jScrollPane4 = null;

	private JTable tbImpDetail = null;

	private JPanel pnMaterialImg = null;

	private JPanel jPanel5 = null;

	private JCalendarComboBox tfExpIssueDate = null;

	private JPanel jPanel51 = null;

	private JLabel jLabel261 = null;

	private JTextField tfImpCopBillNo = null;

	private JLabel jLabel371 = null;

	private JCalendarComboBox tfImpIsDeclaDate = null;

	private JLabel jLabel331 = null;

	private JCalendarComboBox tfImpIssueDate = null;

	private JTextField tfImpIsDeclaEm = null;

	private JLabel jLabel411 = null;

	private JTextField tfImpNote = null;

	private JLabel jLabel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JSplitPane jSplitPane2 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExpMergerDetail = null;

	private JSplitPane jSplitPane3 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbImpMergerDetail = null;

	private JTextPane jTextPane = null;

	private JTextPane jTextPane1 = null;

	private JSplitPane jSplitPane4 = null;

	private JTextPane jTextPane2 = null;

	private JSplitPane jSplitPane5 = null;

	private JTextPane jTextPane3 = null;

	private JLabel jLabel1 = null;

	private JTextField tfModifyMark = null;

	private JTableListModel tableModelHead = null; // 表头

	private JTableListModel tableModelReciveBill = null; // 收货备案

	private JTableListModel tableModelIssueBill = null; // 发货备案

	protected JTableListModel tableModelReciveMerger = null; // 收货归并

	protected JTableListModel tableModelIssueMerger = null; // 发货归并

	private Brief brief = null; // 海关编码

	private JLabel jLabel231 = null;

	private JLabel jLabel2311 = null;

	private JTextField tfExAgentName = null;

	private JLabel jLabel2312 = null;

	private JLabel jLabel23111 = null;

	private JTextField tfImAgentName = null;

	private JTextField cbbExpAgentCode = null;

	private JButton btnExpCopBillNO = null;

	private JTextField cbbExpTradeCod = null;

	private JTextField cbbImpTradeCod = null;

	private JTextField cbbImpAgentCode = null;

	private JButton jButton21 = null;

	private FptManageAction fptManageAction = null;

	private JCalendarComboBox tfInputDate = null;

	private JButton btnExpCopy = null;

	private JButton btnImpCopy = null;

	private JLabel jLabel51 = null;

	private JComboBox cbbProjectType = null;

	private JButton btnAppNo = null;

	private JMenuItem jMenuItem1 = null;

	private JPopupMenu jPopupMenu = null;

	private JPopupMenu jPopupMenu1 = null;

	private JMenuItem jMenuIn = null;

	private JButton btnResetExpListNo = null;

	private JButton btnResetImpListNo = null;

	private JButton btnImport = null;

	private JButton btExput = null;

	private JLabel lbCustomer = null;

	private JComboBox cbbCustomer = null;

	private JButton btnimport = null;

	private JPopupMenu pmImport = null;

	private JMenuItem miExcelImport = null;

	private JMenuItem miBillCenterImport = null;

	private FptParameterSet parameterSet = null;

	private Integer projectType = ProjectType.BCUS; // @jve:decl-index=0:

	private JLabel jLabel3 = null;

	// 运输工具类型 Label
	private JLabel transportToolTypeLabel = null;

	// 运输工具编码 Label
	private JLabel transportToolNumberLabel = null;

	// 运输工具编码
	private JTextField transportToolNumberInput = null;

	private JTextField contractNoIn = null;
	private JComboBox cbbTransferMode;

	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgFptBillHead() {

		super();

		initialize();

		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");

		parameterSet = fptManageAction.findTransParameterSet(new Request(
				CommonVars.getCurrUser(), true));

		projectType = parameterSet.getProjectType();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setTitle("单据明细");

		setContentPane(getJContentPane());

		setSize(694, 621);
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public String isFptInOutFlag() {
		return fptInOutFlag;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setFptInOutFlag(String fptInOutFlag) {
		this.fptInOutFlag = fptInOutFlag;
	}

	public void setVisible(boolean b) {
		if (b) {
			initComponents();
			iniWindows();
			head = (FptBillHead) tableModelHead.getCurrentRow();
			fillData();
			showData(head);
			List list = new Vector();
			initExpDetailTable(list);
			initImpDetailTable(list);
			// 归并后信息暂时隐藏
			// initExpMergerDetailTable(list);
			// initImpMergerDetailTable(list);
			setState();
			String title = "";
			if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
				if (FptBusinessType.FPT_BILL.equals(this.fptBusinessType)) {
					title = "转出-发货-单据明细";
				} else {
					title = "转出-退货-单据明细";
				}
			} else {
				if (FptBusinessType.FPT_BILL.equals(this.fptBusinessType)) {
					title = "转入-收货-单据明细";
				} else {
					title = "转入-退货-单据明细";
				}
			}

			this.setTitle(title);
		}
		super.setVisible(b);
	}

	private void iniWindows() {
		if (FptInOutFlag.IN.equals(this.fptInOutFlag)) {
			// 归并后暂时隐藏
			// this.jScrollPane1.remove(jPanel8);
			// this.jScrollPane1.remove(jPanel7);
			// jSplitPane1.setBottomComponent(getJPanel7());
			// jSplitPane1.setTopComponent(getJPanel8());
		}
	}

	public FptBillHead getFptBillHead() {
		return head;
	}

	public void setFptBillHead(FptBillHead head) {
		this.head = head;
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
	 * 
	 * 填充对象
	 */
	private void fillData(FptBillHead head) {
		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			int projectType = Integer.parseInt(item.getCode());
			head.setProjectType(projectType);
		} else {
			head.setProjectType(null);
		}
		head.setAppNo(this.tfappNo.getText().trim());
		head.setOutEmsNo(this.tfOutEmsNo.getText().trim());
		head.setBillNo(this.tfbillNo.getText().trim());
		head.setSeqNo(this.tfseqNo.getText().trim());
		this.head.setCustomer((ScmCoc) this.cbbCustomer.getSelectedItem());
		// 转出
		head.setIssueTradeCod(this.cbbExpTradeCod.getText().trim());
		head.setIssueTradeName(this.tfExCopName.getText().trim());
		head.setIssueAgentCode(this.cbbExpAgentCode.getText().trim());
		head.setIssueAgentName(this.tfExAgentName.getText().trim());
		head.setIssueDate(this.tfExpIssueDate.getDate());
		head.setIssueIsDeclaEm(this.tfExpIsDeclaEm.getText().trim());
		head.setIssueNote(this.tfExpNote.getText().trim());
		// 转入
		head.setReceiveTradeCod(this.cbbImpTradeCod.getText().trim());
		head.setReceiveTradeName(this.tfImCopName.getText().trim());
		head.setReceiveAgentCode(this.cbbImpAgentCode.getText().trim());
		head.setReceiveAgentName(this.tfImAgentName.getText().trim());
		head.setReceiveDate(this.tfImpIssueDate.getDate());
		head.setReceiveIsDeclaEm(this.tfImpIsDeclaEm.getText().trim());
		head.setReceiveNote(this.tfImpNote.getText().trim());
		// head.setContractNoIn(this.contractNoIn.getText().trim());

		head.setTransportToolNumber(transportToolNumberInput.getText().trim());
		Transf transf = (Transf) this.cbbTransferMode.getSelectedItem();
		if (transf != null) {
			head.setTransportToolType(transf.getName());
		}
	}

	/**
	 * 
	 * 显示数据并设置对象
	 */
	private void showData(FptBillHead head) {

		if (head.getProjectType() != null) {

			cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(head
					.getProjectType().toString(), cbbProjectType));

		} else {

			cbbProjectType.setSelectedIndex(-1);

		}

		if (head.getSeqNo() != null) {

			tfseqNo.setText(head.getSeqNo());

		} else {
			tfseqNo.setText("");
		}
		if (head.getAppNo() != null) {
			tfappNo.setText(head.getAppNo());
		} else {
			tfappNo.setText("");
		}
		if (head.getBillNo() != null) {
			tfbillNo.setText(head.getBillNo());
		} else {
			tfbillNo.setText("");
		}
		if (head.getOutEmsNo() != null) {
			tfOutEmsNo.setText(head.getOutEmsNo());
		} else {
			tfOutEmsNo.setText("");
		}
		if (head.getIssueTradeCod() != null) {
			cbbExpTradeCod.setText(head.getIssueTradeCod());
		} else {
			cbbExpTradeCod.setText("");
		}
		if (head.getIssueTradeName() != null) {
			tfExCopName.setText(head.getIssueTradeName());
		} else {
			tfExCopName.setText("");
		}
		if (head.getReceiveTradeCod() != null) {
			cbbImpTradeCod.setText(head.getReceiveTradeCod());
		} else {
			cbbImpTradeCod.setText("");
		}
		if (head.getReceiveTradeName() != null) {
			tfImCopName.setText(head.getReceiveTradeName());
		} else {
			tfImCopName.setText("");
		}
		if (head.getIssueAgentCode() != null) {
			cbbExpAgentCode.setText(head.getIssueAgentCode());
		} else {
			cbbExpAgentCode.setText("");
		}
		if (head.getReceiveAgentCode() != null) {
			cbbImpAgentCode.setText(head.getReceiveAgentCode());
		} else {
			cbbImpAgentCode.setText("");
		}
		if (head.getIssueAgentName() != null) {
			tfExAgentName.setText(head.getIssueAgentName());
		} else {
			tfExAgentName.setText("");
		}
		if (head.getReceiveAgentName() != null) {
			tfImAgentName.setText(head.getReceiveAgentName());
		} else {
			tfImAgentName.setText("");
		}
		if (head.getCreateDate() != null) {
			tfInputDate.setDate(head.getCreateDate());
		} else {
			tfInputDate.setDate(new Date());
		}
		if (this.head.getCustomer() != null) {
			cbbCustomer.setSelectedItem(this.head.getCustomer());
		} else {
			this.cbbCustomer.setSelectedItem(null);
		}
		// 转出---------------------------------
		if (head.getIssueIsDeclaDate() != null) {
			this.tfExpIsDeclaDate.setDate(head.getIssueIsDeclaDate());
		} else {
			this.tfExpIsDeclaDate.setDate(null);
		}
		this.tfExpCopBillNo.setText(head.getIssueCopBillNo());
		if (head.getIssueIsDeclaEm() != null) {
			this.tfExpIsDeclaEm.setText(head.getIssueIsDeclaEm());
		} else {
			if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
				this.tfExpIsDeclaEm.setText(CommonVars.getCurrUser()
						.getUserName());
			}
		}
		if (head.getIssueDate() != null) {
			this.tfExpIssueDate.setDate(head.getIssueDate());
		} else {
			this.tfExpIssueDate.setDate(null);
		}
		if (head.getIssueNote() != null) {
			this.tfExpNote.setText(head.getIssueNote());
		} else {
			this.tfExpNote.setText("");
		}

		if (head.getTransportToolType() != null) {

			String transportToolType = head.getTransportToolType();

			for (int i = 0; i < cbbTransferMode.getItemCount(); i++) {

				Transf transf = (Transf) cbbTransferMode.getItemAt(i);

				if (transportToolType.equals(transf.getName())) {

					this.cbbTransferMode.setSelectedIndex(i);

					break;

				}

			}

		} else {

			this.cbbTransferMode.setSelectedItem(null);
		}
		// 初始化 运输工具类型
		// List<Transf> list = CustomBaseList.getInstance().getTransfs();
		// System.out.println("list.size()======"+list.size());
		// for (int i = 0; i < list.size(); i++) {
		// Transf transf = list.get(i);
		// if(transf.getCode().equals(head.getTransportToolType())){
		// cbbTransferMode.setSelectedItem(transf);
		// break;
		// }
		// }
		// 初始化 运输工具编号
		if (head.getTransportToolNumber() != null) {
			transportToolNumberInput.setText(head.getTransportToolNumber());
		} else {
			transportToolNumberInput.setText("");
		}

		// 转入-----------------------------
		if (head.getReceiveIsDeclaDate() != null) {
			this.tfImpIsDeclaDate.setDate(head.getReceiveIsDeclaDate());
		} else {
			this.tfImpIsDeclaDate.setDate(null);
		}
		this.tfImpCopBillNo.setText(head.getReceiveCopBillNo());
		if (head.getReceiveIsDeclaEm() != null) {
			this.tfImpIsDeclaEm.setText(head.getReceiveIsDeclaEm());
		} else {
			// this.tfImpIsDeclaEm.setText("");
			if (FptInOutFlag.IN.equals(fptInOutFlag)) {
				this.tfImpIsDeclaEm.setText(CommonVars.getCurrUser()
						.getUserName());
			}
		}
		// if (head.getContractNoIn() != null) {
		// this.contractNoIn.setText(head.getContractNoIn());
		// } else {
		// this.contractNoIn.setText("");
		// }
		if (head.getReceiveDate() != null) {
			this.tfImpIssueDate.setDate(head.getReceiveDate());
		} else {
			this.tfImpIssueDate.setDate(null);
		}
		if (head.getReceiveNote() != null) {
			this.tfImpNote.setText(head.getReceiveNote());
		} else {
			this.tfImpNote.setText("");
		}
	}

	/**
	 * 清空数据
	 * 
	 */
	protected void emptyData() {
		this.tfappNo.setText("");
		this.tfbillNo.setText("");
		this.tfseqNo.setText("");
		this.tfOutEmsNo.setText("");
		this.cbbExpTradeCod.setText("");
		this.cbbExpAgentCode.setText("");
		this.tfExpNote.setText("");
		this.tfExpIssueDate.setDate(null);
		this.tfExpIsDeclaDate.setDate(null);
		this.cbbImpTradeCod.setText("");
		this.cbbImpAgentCode.setText("");
		this.tfInputDate.setDate(null);
		this.tfContractNo.setText("");
		this.tfImpNote.setText("");
		this.tfImpIssueDate.setDate(null);
		this.tfImpIsDeclaDate.setDate(null);
	}

	/**
	 * 
	 * 窗体状态控制
	 */
	private void setState() {
		//
		// 面板
		//
		if (head == null) {
			return;
		}

		String declareState = head.getAppState();
		boolean isChangingExe = DeclareState.CHANGING_EXE.equals(declareState);
		boolean isApplyPor = DeclareState.APPLY_POR.equals(declareState);
		//
		// 能编辑
		//
		boolean isCanEdit = isApplyPor || isChangingExe;

		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && isCanEdit); // 保存
		btnEdit.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState == DataState.BROWSE && isCanEdit); // 修改
		btnUndo.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && isCanEdit); // 撤消

		btninput.setEnabled(isCanEdit);
		// tfTradeName.setEditable(dataState != DataState.BROWSE);
		// tfappNo.setEditable(dataState != DataState.BROWSE);
		btnAppNo.setEnabled(dataState != DataState.BROWSE);
		// tfUser.setEditable(dataState != DataState.BROWSE);
		tfModifyMark.setEditable(dataState == DataState.READONLY);
		tfbillNo.setEditable(dataState != DataState.BROWSE);
		tfseqNo.setEditable(dataState == DataState.READONLY);
		btnExpCopBillNO.setEnabled(dataState == DataState.READONLY);
		jButton21.setEnabled(dataState != DataState.BROWSE);
		cbbProjectType.setEnabled(false);
		cbbCustomer.setEnabled(false);

		if (FptInOutFlag.OUT.equals(fptInOutFlag)) {

			//
			// 明细
			//
			FptBillItem f = (FptBillItem) tableModelIssueBill.getCurrentRow();
			boolean isAdd = false;
			cbbExpAgentCode.setEditable(false);
			tfExAgentName.setEditable(false);
			tfExpIsDeclaEm.setEditable(dataState != DataState.BROWSE);
			tfExpIssueDate.setEnabled(dataState != DataState.BROWSE);
			tfExpNote.setEditable(dataState != DataState.BROWSE);
			tfOutEmsNo.setEditable(false);
			transportToolNumberInput.setEnabled(dataState != DataState.BROWSE);
			//
			// 收货单备案
			//
			btnAddExgBill
					.setEnabled(dataState == DataState.BROWSE && isCanEdit);
			// btnEditExgBill.setEnabled(dataState == DataState.BROWSE
			// && isCanEdit && isAdd);
			btnEditExgBill.setEnabled(dataState == DataState.BROWSE
					&& isCanEdit);
			btnDeleteExgBill.setEnabled(dataState == DataState.BROWSE
					&& isCanEdit);
			btnExpCopy.setEnabled(dataState == DataState.BROWSE && isCanEdit);
			btnResetExpListNo.setEnabled(dataState == DataState.BROWSE
					&& isCanEdit);

			transportToolNumberInput.setEditable(dataState != DataState.BROWSE
					&& isCanEdit);

			cbbTransferMode.setEnabled(dataState != DataState.BROWSE
					&& isCanEdit);

			tfImpIsDeclaDate.setEnabled(false);
			tfImpIsDeclaEm.setEditable(false);
			tfImpIsDeclaEm.setEditable(false);
			tfImpNote.setEditable(false);
			tfImpIssueDate.setEnabled(false);
			cbbImpAgentCode.setEditable(false);
			tfImAgentName.setEditable(false);
			// 发货单备案
			//
			btnAddImpBill.setEnabled(false);
			btnEditImpBill.setEnabled(false);
			btnDeleteImpBill.setEnabled(false);
			btnResetImpListNo.setEnabled(false);
			btnImpCopy.setEnabled(false);
			tfbillNo.setEditable(false);

		} else {

			FptBillItem f = (FptBillItem) tableModelReciveBill.getCurrentRow();
			boolean isAdd = false;
			tfOutEmsNo.setEditable(false);
			cbbImpAgentCode.setEditable(false);
			tfImpIsDeclaEm.setEditable(dataState != DataState.BROWSE);
			// contractNoIn.setEditable(dataState != DataState.BROWSE);
			tfImpNote.setEditable(dataState != DataState.BROWSE);
			tfImpIssueDate.setEnabled(dataState != DataState.BROWSE);
			// 发货单备案
			//
			btnAddImpBill
					.setEnabled(dataState == DataState.BROWSE && isCanEdit);
			btnEditImpBill.setEnabled(dataState == DataState.BROWSE
					&& isCanEdit);
			btnDeleteImpBill.setEnabled(dataState == DataState.BROWSE
					&& isCanEdit);
			btnImpCopy.setEnabled(dataState == DataState.BROWSE && isCanEdit);
			btnResetImpListNo.setEnabled(dataState == DataState.BROWSE
					&& isCanEdit);

			transportToolNumberInput.setEditable(dataState != DataState.BROWSE
					&& isCanEdit);

			cbbTransferMode.setEnabled(dataState != DataState.BROWSE
					&& isCanEdit);

			tfImAgentName.setEditable(false);
			cbbExpAgentCode.setEditable(false);
			tfExAgentName.setEditable(false);
			tfExpIsDeclaDate.setEnabled(false);
			tfExpIsDeclaEm.setEditable(false);
			tfExpIssueDate.setEnabled(false);
			tfExpNote.setEditable(false);
			btnResetExpListNo.setEnabled(false);
			// cbbCustomer.setEnabled(dataState != DataState.BROWSE&&
			// isCanEdit);
			//
			// 收货单备案
			//
			btnAddExgBill.setEnabled(false);
			btnEditExgBill.setEnabled(false);
			btnDeleteExgBill.setEnabled(false);
			btnExpCopy.setEnabled(false);
			tfbillNo.setEditable(false);
		}
		if (FptBusinessType.FPT_BILL.equals(fptBusinessType)) {
			if (FptInOutFlag.IN.equals(fptInOutFlag)) {
				tfbillNo.setEditable(dataState != DataState.BROWSE && true);
			}
		} else {
			if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
				tfbillNo.setEditable(dataState != DataState.BROWSE && true);
			}
		}
		this.btnimport.setEnabled(FptBusinessType.FPT_BILL.equals(head
				.getSysType())
				&& FptInOutFlag.OUT.equals(fptInOutFlag)
				|| FptBusinessType.FPT_BILL_BACK.equals(head.getSysType())
				&& FptInOutFlag.IN.equals(fptInOutFlag));
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initComponents() {
		// 初始化出口口岸
		// this.cbbDeclareCustoms.setModel(CustomBaseModel.getInstance()
		// .getCustomModel());
		// this.cbbDeclareCustoms.setRenderer(CustomBaseRender.getInstance()
		// .getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbDeclareCustoms);
		if (this.fptInOutFlag.equals(FptInOutFlag.OUT)
				&& this.fptBusinessType.equals(FptBusinessType.FPT_BILL)) {
			this.lbCustomer.setText("客户");
		} else if (this.fptInOutFlag.equals(FptInOutFlag.OUT)
				&& this.fptBusinessType.equals(FptBusinessType.FPT_BILL_BACK)) {
			this.lbCustomer.setText("供应商");
		} else if (this.fptInOutFlag.equals(FptInOutFlag.IN)
				&& this.fptBusinessType.equals(FptBusinessType.FPT_BILL)) {
			this.lbCustomer.setText("供应商");
		} else if (this.fptInOutFlag.equals(FptInOutFlag.IN)
				&& this.fptBusinessType.equals(FptBusinessType.FPT_BILL_BACK)) {
			this.lbCustomer.setText("客户");
		} else {
			this.lbCustomer.setText("客户/供应商");
		}
		this.cbbProjectType.removeAllItems();
		if (ProjectType.BCUS == parameterSet.getProjectType()) {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCUS), "电子帐册"));
		} else if (ProjectType.BCS == parameterSet.getProjectType()) {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCS), "电子化手册"));
		} else if (ProjectType.DZSC == parameterSet.getProjectType()) {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.DZSC), "电子手册"));
		} else {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCUS), "电子帐册"));
		}

		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(0);

		if (cbbProjectType.getSelectedIndex() == 0)
			jLabel19.setText("转出帐册编号");
		else
			jLabel19.setText("转出手册编号");
		// 初始化运输方式
		this.cbbTransferMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransferMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransferMode);
		List list = null;
		// if (this.fptInOutFlag.equals(FptInOutFlag.OUT)) {
		// list = materialManageAction.findScmManuFactoryIn(new Request(
		// CommonVars.getCurrUser(), true));
		//
		// } else {
		// list = materialManageAction.findScmManuFactoryOut(new Request(
		// CommonVars.getCurrUser(), true));
		// }
		// this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 270);
		if (FptBusinessType.FPT_BILL.equals(this.fptBusinessType)) { // 转出
			jLabel6.setText("发货单编号");
			jLabel26.setText("转出企业内部编号");
			jLabel261.setText("转入企业内部编号");
			jLabel33.setText("发货日期");
			jLabel331.setText("收货日期");
			if (this.fptInOutFlag.equals(FptInOutFlag.OUT)) {
				jPanel5.setBorder(BorderFactory.createTitledBorder(null,
						"转出企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.blue));
				jPanel51.setBorder(BorderFactory.createTitledBorder(null,
						"转入企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.BLACK));
				jLabel26.setForeground(Color.blue);
				jLabel10.setForeground(Color.blue);
				jLabel33.setForeground(Color.blue);
				jLabel261.setForeground(Color.BLACK);
				jLabel.setForeground(Color.BLACK);
				jLabel331.setForeground(Color.BLACK);
			} else {
				jPanel5.setBorder(BorderFactory.createTitledBorder(null,
						"转出企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.BLACK));
				jPanel51.setBorder(BorderFactory.createTitledBorder(null,
						"转入企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.blue));
				jLabel26.setForeground(Color.BLACK);
				jLabel10.setForeground(Color.BLACK);
				jLabel33.setForeground(Color.BLACK);
				jLabel261.setForeground(Color.blue);
				jLabel.setForeground(Color.blue);
				jLabel331.setForeground(Color.blue);
			}
		} else {
			jLabel6.setText("退货单编号");
			jLabel26.setText("收退货企业内部编号");
			jLabel261.setText("发退货企业内部编号");
			jLabel33.setText("收退货日期");
			jLabel331.setText("发退货日期");
			jPanel5.setBorder(BorderFactory.createTitledBorder(null,
					"收退货单企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			jPanel51.setBorder(BorderFactory.createTitledBorder(null,
					"发退货企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.BLACK));
			if (this.fptInOutFlag.equals(FptInOutFlag.OUT)) {
				jPanel5.setBorder(BorderFactory.createTitledBorder(null,
						"收退货单企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.blue));
				jPanel51.setBorder(BorderFactory.createTitledBorder(null,
						"发退货企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.BLACK));
				jLabel26.setForeground(Color.blue);
				jLabel10.setForeground(Color.blue);
				jLabel33.setForeground(Color.blue);
				jLabel261.setForeground(Color.BLACK);
				jLabel.setForeground(Color.BLACK);
				jLabel331.setForeground(Color.BLACK);
			} else {
				jPanel5.setBorder(BorderFactory.createTitledBorder(null,
						"收退货单企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.BLACK));
				jPanel51.setBorder(BorderFactory.createTitledBorder(null,
						"发退货企业填写", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), Color.blue));
				jLabel26.setForeground(Color.BLACK);
				jLabel10.setForeground(Color.BLACK);
				jLabel33.setForeground(Color.BLACK);
				jLabel261.setForeground(Color.blue);
				jLabel.setForeground(Color.blue);
				jLabel331.setForeground(Color.blue);
			}
		}
	}

	/**
	 * 新增显示初始化数据
	 */
	private void fillData() {

		if (CommonVars.getCurrUser() != null) {

			AclUser aclUser = (AclUser) CommonVars.getCurrUser();

			tfUser.setText(aclUser.getUserName());

			if (aclUser.getCompany() != null) {

				tfTradeName.setText(aclUser.getCompany().getName());
			}
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnimport());
			jToolBar.add(getBtnCheck());
			jToolBar.add(getBtnChangeDeclareState());
			jToolBar.add(getBtnCopyBill());
			jToolBar.add(getBtninput());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtExput());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
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
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!validateData()) {
						return;
					}
					JOptionPane
							.showMessageDialog(DgFptBillHead.this,
									"转厂单据表头保存成功", "提示",
									JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnSave;
	}

	/**
	 * 验证数据
	 */
	private boolean validateData() {
		String str = "";
		fillData(head);
		if (cbbProjectType.getSelectedItem() == null) {
			str += "项目类型不能为空" + ";\n";
		}
		if (tfappNo.getText() == null || "".equals(tfappNo.getText())) {
			str += "申请表编号不能为空" + ";\n";
		}
		// 查找申请表编号是否存在申请单中
		if (!"".equals(tfappNo.getText().trim())
				&& !(head.getAppState().equals(DeclareState.CHANGING_EXE))) {
			if (!checkAppNoInFptAppHead(fptInOutFlag, tfappNo.getText())) {
				str += "申请表编号在申请单中不存在" + ";\n";
			}
		}
		if (!"".equals(tfExpCopBillNo.getText().trim())
				&& !(head.getAppState().equals(DeclareState.CHANGING_EXE))) {
			if (checkFptBillEmsBillNoDuple(head, fptInOutFlag, fptBusinessType)) {
				str += "企业内部编号不能重复" + ";\n";
			}
		}

		if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
			if (tfOutEmsNo.getText() == null || "".equals(tfOutEmsNo.getText())) {
				str += "手册/帐册号不可为空" + ";\n";
			}
			if (this.cbbExpAgentCode != null
					&& this.cbbExpAgentCode.getText().trim().length() != 9) {
				str += "转出企业组织机构代码必须为9位" + ";\n";
			}
			if (this.tfExpIssueDate.getDate() == null) {

				str += "发货日期不可为空" + ";\n";
			}
			if (FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType)) {
				if (this.tfbillNo.getText() == null
						|| "".equals(tfbillNo.getText())) {
					str += "退货编号不可为空" + ";\n";
				}
			}
			if (this.tfExpIsDeclaEm.getText() == null
					|| "".equals(tfExpIsDeclaEm.getText())) {
				str += "申报人不可为空" + ";\n";
			}
		} else {
			if (this.cbbImpAgentCode != null
					&& this.cbbImpAgentCode.getText().trim().length() != 9) {
				str += "转入企业组织机构代码必须为9位" + ";\n";
			}
			if (this.tfImpIssueDate.getDate() == null) {
				str += "收货日期不可为空" + ";\n";
			}
			if (FptBusinessType.FPT_BILL.equals(this.fptBusinessType)) {
				if (this.tfbillNo.getText() == null
						|| "".equals(tfbillNo.getText())) {
					str += "发货编号不可为空" + ";\n";
				}
			}
			if (this.tfImpIsDeclaEm.getText() == null
					|| "".equals(tfImpIsDeclaEm.getText())) {
				str += "申报人不可为空" + ";\n";
			}
			// if (this.contractNoIn.getText() == null
			// || "".equals(contractNoIn.getText())) {
			// str += "转入手册号不可为空" + ";\n";
			// }
		}
		if (!"".equals(str)) {
			JOptionPane.showMessageDialog(null, str, "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		head = fptManageAction.saveFptBillHead(
				new Request(CommonVars.getCurrUser()), head);
		tableModelHead.updateRow(head);
		dataState = DataState.BROWSE;
		setState();
		return true;
	}

	/**
	 * 判断申请单编号是否存在
	 */
	private boolean checkAppNoInFptAppHead(String fptInOutFlag, String appNo) {
		return this.fptManageAction.checkAppNoInFptAppHead(new Request(
				CommonVars.getCurrUser(), true), fptInOutFlag, appNo);

	}

	/**
	 * 判断企业内部编号是否重复
	 * 
	 * @param head
	 * @return
	 */
	private boolean checkFptBillEmsBillNoDuple(FptBillHead head,
			String fptInOutFlag, String fptBusinessType) {
		return this.fptManageAction.checkFptBillEmsBillNoDuple(new Request(
				CommonVars.getCurrUser(), true), fptInOutFlag, fptBusinessType,
				head);
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
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
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnPrint.setText("打印表格");
			btnPrint.setVisible(false);
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnUndo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData(head);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes btnCheck
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("检查");
			btnCheck.setVisible(false);
		}
		return btnCheck;
	}

	/**
	 * This method initializes btnChangeDeclareState
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState.setVisible(false);
		}
		return btnChangeDeclareState;
	}

	/**
	 * This method initializes btnCopyBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopyBill() {
		if (btnCopyBill == null) {
			btnCopyBill = new JButton();
			btnCopyBill
					.setText("\u8f6c\u6284\u901a\u5173\u5907\u6848\u8d44\u6599");
			btnCopyBill.setVisible(false);
		}
		return btnCopyBill;
	}

	/**
	 * This method initializes btninput
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtninput() {
		if (btninput == null) {
			btninput = new JButton();
			btninput.setText("文本导入");
			btninput.setVisible(false);
		}
		return btninput;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
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
			jPanel.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("基本信息", null, getPnBase(), null);
			jTabbedPane.addTab("商品明细", null, getPnExgBom(), null);
			jTabbedPane.addTab("归并后信息", null, getPnMaterialImg(), null); // 暂时隐藏
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane tab = (JTabbedPane) e.getSource();
							if (tab.getSelectedIndex() == 0) {
								if (head != null) {
									showData(head);
									setState();
								}
							} else if (tab.getSelectedIndex() == 1) {
								if (FptBusinessType.FPT_BILL
										.equals(fptBusinessType)) {
									jTextPane2.setText("           发货明细");
									jTextPane3.setText("           收货明细");
								} else {
									jTextPane2.setText("           收退货明细");
									jTextPane3.setText("           发退货明细");
								}
								if (dataState != DataState.BROWSE) {
									if (!validateData()) {
										tab.setSelectedIndex(0);
									}
								}
								if (head != null) {
									String parentId = head.getId();
									List outlist = fptManageAction
											.findFptBillItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.OUT);
									List inlist = fptManageAction
											.findFptBillItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.IN);
									initExpDetailTable(outlist);
									initImpDetailTable(inlist);
								}
							} else if (tab.getSelectedIndex() == 2) {
								if (FptBusinessType.FPT_BILL
										.equals(fptBusinessType)) {
									jTextPane1.setText("           发货汇总");
									jTextPane.setText("           收货汇总");
								} else {
									jTextPane1.setText("           收退货汇总");
									jTextPane.setText("           发退货汇总");
								}
								if (dataState != DataState.BROWSE) {
									if (!validateData()) {
										tab.setSelectedIndex(0);
									}
								}
								if (head != null) {
									String parentId = head.getId();
									String sysType = "";
									List outlist = fptManageAction
											.findFptBillDictItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.OUT,
													sysType);

									List inlist = fptManageAction
											.findFptBillDictItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.IN,
													sysType);
									// Logger.log(">>>>>>>>> "+"OutLiset Size "+outlist.size()+"in List Size "+inlist.size());
									initExpMergerDetailTable(outlist);
									initImpMergerDetailTable(inlist);
								}

							}
							setState();

						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * 初始发货明细
	 * 
	 */
	private void initExpDetailTable(List list) {
		JTableListModel.dataBind(tbExpDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "listNo", 40));
						// list.add(addColumn("修改标记", "modifyMake", 60));
						list.add(addColumn("申请表序号", "appGNo", 60));
						list.add(addColumn("项号", "trGno", 40));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 140));
						list.add(addColumn("规格型号", "commSpec", 140));
						list.add(addColumn("交易单位", "tradeUnit.name", 60));
						list.add(addColumn("交易数量", "tradeQty", 80));
						list.add(addColumn("申报单位", "unit.name", 60));
						list.add(addColumn("申报数量", "qty", 80));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("料号", "copGNo", 80));
						list.add(addColumn("归并前商品名称", "copGName", 100));
						list.add(addColumn("归并前商品规格", "copGModel", 100));
						return list;
					}
				});
		this.tableModelIssueBill = (JTableListModel) tbExpDetail.getModel();
		// tbExpDetail.getColumnModel().getColumn(2).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// super.setText((value == null) ? "" : castValue(value));
		// return this;
		// }
		//
		// private String castValue(Object value) {
		// return ModifyMarkState.getModifyMarkSpec(value
		// .toString());
		// }
		// });
		tbExpDetail
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始收货明细
	 * 
	 */
	private void initImpDetailTable(List list) {
		JTableListModel.dataBind(tbImpDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "listNo", 40));
						// list.add(addColumn("修改标记", "modifyMake", 60));
						list.add(addColumn("申请表序号", "appGNo", 60));
						list.add(addColumn("项号", "trGno", 40));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 140));
						list.add(addColumn("规格型号", "commSpec", 140));
						list.add(addColumn("交易单位", "tradeUnit.name", 60));
						list.add(addColumn("交易数量", "tradeQty", 80));
						list.add(addColumn("申报单位", "unit.name", 60));
						list.add(addColumn("申报数量", "qty", 80));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("料号", "copGNo", 80));
						list.add(addColumn("手册/账册号", "inEmsNo", 100));
						list.add(addColumn("归并前商品名称", "copGName", 100));
						list.add(addColumn("归并前商品规格", "copGModel", 100));
						return list;
					}
				});
		this.tableModelReciveBill = (JTableListModel) tbImpDetail.getModel();
		// tbImpDetail.getColumnModel().getColumn(2).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// super.setText((value == null) ? "" : castValue(value));
		// return this;
		// }
		//
		// private String castValue(Object value) {
		// return ModifyMarkState.getModifyMarkSpec(value
		// .toString());
		// }
		// });
		tbImpDetail
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始发货汇总
	 * 
	 */
	private void initExpMergerDetailTable(List list) {
		JTableListModel.dataBind(tbExpMergerDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "listNo", 40));
						list.add(addColumn("申请表序号", "appGNo", 80));
						list.add(addColumn("项号", "trGno", 40));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("规格型号", "commSpec", 100));
						list.add(addColumn("交易单位", "tradeUnit.name", 60));
						list.add(addColumn("交易数量", "tradeQty", 80));
						list.add(addColumn("申报单位", "unit.name", 60));
						list.add(addColumn("申报数量", "qty", 80));
						return list;
					}
				});
		this.tableModelIssueMerger = (JTableListModel) tbExpMergerDetail
				.getModel();
	}

	/**
	 * 初始收货汇总
	 * 
	 */
	private void initImpMergerDetailTable(List list) {
		JTableListModel.dataBind(tbImpMergerDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "listNo", 40));
						list.add(addColumn("手册/账册号", "inEmsNo", 100));
						list.add(addColumn("申请表序号", "appGNo", 80));
						list.add(addColumn("项号", "trGno", 40));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("规格型号", "commSpec", 100));
						list.add(addColumn("交易单位", "tradeUnit.name", 60));
						list.add(addColumn("交易数量", "tradeQty", 80));
						list.add(addColumn("申报单位", "unit.name", 60));
						list.add(addColumn("申报数量", "qty", 80));
						return list;
					}
				});
		this.tableModelReciveMerger = (JTableListModel) tbImpMergerDetail
				.getModel();
	}

	/**
	 * This method initializes pnBase
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBase() {
		if (pnBase == null) {
			lbCustomer = new JLabel();
			lbCustomer.setBounds(new Rectangle(21, 111, 115, 18));
			lbCustomer.setText("客户");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(21, 85, 115, 18));
			jLabel51.setText("手册类型");
			jLabel23111 = new JLabel();
			jLabel23111.setText("转入企业组织机构名称");
			jLabel23111.setBounds(new Rectangle(315, 53, 124, 18));
			jLabel2312 = new JLabel();
			jLabel2312.setText("转入企业组织机构代码");
			jLabel2312.setBounds(new Rectangle(10, 53, 125, 17));
			jLabel2311 = new JLabel();
			jLabel2311.setText("转出企业组织机构名称");
			jLabel2311.setBounds(new Rectangle(314, 52, 124, 18));
			jLabel231 = new JLabel();
			jLabel231.setText("转出企业组织机构代码");
			jLabel231.setBounds(new Rectangle(9, 52, 125, 17));
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(321, 33, 125, 19));
			jLabel1.setText("修改标志");
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(21, 5, 115, 25));
			jLabel101.setText("录入单位名称");
			jLabel101.setForeground(Color.black);
			jLabel41 = new JLabel();
			jLabel41.setText("\u5907\u6ce8");
			jLabel41.setBounds(new Rectangle(8, 130, 115, 22));
			jLabel37 = new JLabel();
			jLabel37.setText("申报日期");
			jLabel37.setBounds(new Rectangle(314, 76, 124, 22));
			jLabel33 = new JLabel();
			jLabel33.setText("发货日期");
			jLabel33.setBounds(new Rectangle(314, 104, 124, 22));
			jLabel33.setForeground(Color.blue);
			jLabel26 = new JLabel();
			jLabel26.setText("转出企业部内编号");
			jLabel26.setBounds(new Rectangle(9, 78, 115, 22));
			jLabel26.setForeground(Color.blue);
			jLabel25 = new JLabel();
			jLabel25.setText("转入企业名称");
			jLabel25.setBounds(new Rectangle(315, 25, 124, 22));
			jLabel23 = new JLabel();
			jLabel23.setText("转入企业编码");
			jLabel23.setBounds(new Rectangle(10, 24, 115, 21));
			jLabel22 = new JLabel();
			jLabel22.setText("转出企业编码");
			jLabel22.setBounds(new Rectangle(9, 23, 115, 21));
			jLabel21 = new JLabel();
			jLabel21.setText("转出企业名称");
			jLabel21.setBounds(new Rectangle(314, 22, 124, 22));
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(321, 84, 125, 23));
			jLabel19.setText("转出企业手册/帐册号");
			jLabel19.setForeground(Color.blue);
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(21, 32, 115, 21));
			jLabel18.setText("申请表编号");
			jLabel18.setForeground(Color.blue);
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(321, 57, 125, 23));
			jLabel16.setText("中国电子口岸统一号");
			jLabel16.setForeground(Color.blue);
			jLabel10 = new JLabel();
			jLabel10.setText("申报人");
			jLabel10.setBounds(new Rectangle(9, 104, 115, 22));
			jLabel10.setForeground(Color.blue);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(21, 56, 115, 21));
			jLabel6.setText("收发货编号");
			jLabel4 = new JLabel();
			jLabel4.setText("录入日期");
			jLabel4.setBounds(new Rectangle(320, 6, 48, 22));
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(501, 7, 46, 21));
			jLabel2.setText("操作员");

			// 运输工具类型
			transportToolTypeLabel = new JLabel();

			transportToolTypeLabel.setText("运输工具类型");

			transportToolTypeLabel.setBounds(new Rectangle(21, 138, 87, 21));

			// 运输工具编号
			transportToolNumberLabel = new JLabel("运输工具编号");

			transportToolNumberLabel.setBounds(new Rectangle(321, 136, 73, 24));

			pnBase = new JPanel();
			pnBase.setLayout(null);
			pnBase.add(jLabel2, null);
			pnBase.add(getTfUser(), null);
			pnBase.add(jLabel6, null);
			pnBase.add(getTfbillNo(), null);
			pnBase.add(jLabel16, null);
			pnBase.add(getTfseqNo(), null);
			pnBase.add(jLabel18, null);
			pnBase.add(getTfappNo(), null);
			pnBase.add(jLabel19, null);
			pnBase.add(getTfOutEmsNo(), null);
			pnBase.add(jLabel101, null);
			pnBase.add(getTfTradeName(), null);
			pnBase.add(getJPanel5(), null);
			pnBase.add(getJPanel51(), null);
			pnBase.add(jLabel1, null);
			pnBase.add(getTfModifyMark(), null);
			pnBase.add(getTfInputDate(), null);
			pnBase.add(jLabel4, null);
			pnBase.add(jLabel51, null);
			pnBase.add(getCbbProjectType(), null);
			pnBase.add(getBtnAppNo(), null);
			pnBase.add(lbCustomer, null);
			pnBase.add(getCbbCustomer(), null);
			pnBase.add(transportToolTypeLabel, null);
			pnBase.add(transportToolNumberLabel, null);
			pnBase.add(getTransportToolNumberInput(), null);
			pnBase.add(getCbbTransferMode());

		}
		return pnBase;
	}

	/**
	 * 运输工具编号 文本输入框
	 * 
	 * @return
	 */
	private JTextField getTransportToolNumberInput() {

		if (transportToolNumberInput == null) {

			transportToolNumberInput = new JTextField();

			transportToolNumberInput
					.setBounds(new Rectangle(457, 138, 184, 21));

			transportToolNumberInput.setColumns(10);

			// 初始化导入 设置不可用状态
			transportToolNumberInput.setEditable(true);

		}

		return transportToolNumberInput;
	}

	/**
	 * This method initializes tfUser
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUser() {
		if (tfUser == null) {
			tfUser = new JTextField();
			tfUser.setBounds(new Rectangle(555, 6, 87, 22));
			tfUser.setEditable(false);
		}
		return tfUser;
	}

	/**
	 * This method initializes tfExpIsDeclaEm
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpIsDeclaEm() {
		if (tfExpIsDeclaEm == null) {
			tfExpIsDeclaEm = new JTextField();
			tfExpIsDeclaEm.setBounds(new Rectangle(140, 103, 164, 22));
		}
		return tfExpIsDeclaEm;
	}

	/**
	 * This method initializes tfbillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfbillNo() {
		if (tfbillNo == null) {
			tfbillNo = new JTextField();
			tfbillNo.setBounds(new Rectangle(147, 56, 159, 21));
			tfbillNo.setEditable(false);
			tfbillNo.setDocument(new CustomDocument(17));
		}
		return tfbillNo;
	}

	/**
	 * This method initializes tfseqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfseqNo() {
		if (tfseqNo == null) {
			tfseqNo = new JTextField();
			tfseqNo.setBounds(new Rectangle(457, 60, 184, 21));
			tfseqNo.setEditable(false);
		}
		return tfseqNo;
	}

	/**
	 * This method initializes tfappNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfappNo() {
		if (tfappNo == null) {
			tfappNo = new JTextField();
			tfappNo.setBounds(new Rectangle(147, 33, 136, 21));
			tfappNo.setEditable(false);
		}
		return tfappNo;
	}

	/**
	 * This method initializes tfOutEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutEmsNo() {
		if (tfOutEmsNo == null) {
			tfOutEmsNo = new JTextField();
			tfOutEmsNo.setBounds(new Rectangle(457, 84, 185, 21));
			tfOutEmsNo.setEditable(false);

		}
		return tfOutEmsNo;
	}

	/**
	 * This method initializes tfExCopName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExCopName() {
		if (tfExCopName == null) {
			tfExCopName = new JTextField();
			tfExCopName.setEditable(false);
			tfExCopName.setBounds(new Rectangle(446, 24, 183, 22));
		}
		return tfExCopName;
	}

	/**
	 * This method initializes tfImCopName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImCopName() {
		if (tfImCopName == null) {
			tfImCopName = new JTextField();
			tfImCopName.setEditable(false);
			tfImCopName.setBounds(new Rectangle(445, 22, 182, 22));
		}
		return tfImCopName;
	}

	/**
	 * This method initializes tfExpIsDeclaDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfExpIsDeclaDate() {
		if (tfExpIsDeclaDate == null) {
			tfExpIsDeclaDate = new JCalendarComboBox();
			tfExpIsDeclaDate.setBounds(new Rectangle(446, 77, 183, 22));
			tfExpIsDeclaDate.setDate(null);
			tfExpIsDeclaDate.setEnabled(false);
		}
		return tfExpIsDeclaDate;
	}

	/**
	 * This method initializes tfExpNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpNote() {
		if (tfExpNote == null) {
			tfExpNote = new JTextField();
			tfExpNote.setBounds(new Rectangle(139, 131, 370, 22));
		}
		return tfExpNote;
	}

	/**
	 * This method initializes tfContractNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContractNo() {
		if (tfContractNo == null) {
			tfContractNo = new JTextField();
			tfContractNo.setBounds(new Rectangle(261, 83, 28, 22));
			tfContractNo.setVisible(false);
		}
		return tfContractNo;
	}

	/**
	 * This method initializes tfExpCopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpCopBillNo() {
		if (tfExpCopBillNo == null) {
			tfExpCopBillNo = new JTextField();
			tfExpCopBillNo.setBounds(new Rectangle(140, 77, 164, 22));
			tfExpCopBillNo.setEditable(false);
		}
		return tfExpCopBillNo;
	}

	/**
	 * This method initializes tfTradeName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(147, 6, 159, 21));
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes pnExgBom
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnExgBom() {
		if (pnExgBom == null) {
			pnExgBom = new JPanel();
			pnExgBom.setLayout(new BorderLayout());
			pnExgBom.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnExgBom;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(220);
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setBottomComponent(getJPanel8());
			jSplitPane1.setTopComponent(getJPanel7());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJToolBar7(), BorderLayout.NORTH);
			jPanel7.add(getJSplitPane4(), BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jToolBar7
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar7() {
		if (jToolBar7 == null) {
			jToolBar7 = new JToolBar();
			jToolBar7.setFloatable(false);
			jToolBar7.add(getBtnAddExgBill());
			jToolBar7.add(getBtnEditExgBill());
			jToolBar7.add(getBtnDeleteExgBill());
			jToolBar7.add(getBtnExpCopy());
			jToolBar7.add(getBtnResetExpListNo());
		}
		return jToolBar7;
	}

	/**
	 * 
	 * @param model
	 * @param isAppOrMerger
	 * @param action
	 *            "new,update,delete"
	 */
	public void getDgFptBillItemCommodityInfo(JTableListModel model,
			Boolean isAppOrMerger, String action) {

		DgFptBillItem dg = new DgFptBillItem();

		dg.setFptInOutFlag(fptInOutFlag);

		dg.setFptBusinessType(fptBusinessType);

		dg.setFptBillHead(head);

		dg.setIsAppOrMerger(isAppOrMerger);

		dg.setTableModel(model);

		if (head.getAppState().equals(DeclareState.PROCESS_EXE)
				|| head.getAppState().equals(DeclareState.WAIT_EAA)) {

			dg.setDataState(DataState.BROWSE);

		} else {

			dg.setDataState(DataState.EDIT);

		}

		dg.setAction(action);

		dg.setIsdataState(true);

		dg.setVisible(true);
	}

	/**
	 * This method initializes btnAddExgBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddExgBill() {
		if (btnAddExgBill == null) {
			btnAddExgBill = new JButton();
			btnAddExgBill.setText("增加");
			btnAddExgBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// getJPopupMenu().show(getBtnAddExgBill(), 0,
							// getBtnAddExgBill().getHeight());
							if (head == null) {
								JOptionPane.showMessageDialog(null,
										"请先保存转厂单记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List lsResult = FptQuery.getInstance()
									.findFptAppItemtype(fptInOutFlag,
											head.getAppNo());
							if (lsResult != null && lsResult.size() > 0) {
								lsResult = fptManageAction
										.saveFptAppItemCommInfoToFptBillItemCommInfo(
												new Request(CommonVars
														.getCurrUser(), true),
												lsResult, head);
								tableModelIssueBill.addRows(lsResult);
								getDgFptBillItemCommodityInfo(
										tableModelIssueBill, true, "new");
							}

						}
					});
		}
		return btnAddExgBill;
	}

	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setSize(new Dimension(70, 28));
			jPopupMenu.add(getJMenuItem1());
		}
		return jPopupMenu;
	}

	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setSize(new Dimension(47, 24));
			jMenuItem1.setText("转厂申请表转入");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (head == null) {
						JOptionPane.showMessageDialog(null, "请先保存转厂单记录!!!",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List lsResult = FptQuery.getInstance().findFptAppItemtype(
							fptInOutFlag, head.getAppNo());
					if (lsResult != null && lsResult.size() > 0) {
						lsResult = fptManageAction
								.saveFptAppItemCommInfoToFptBillItemCommInfo(
										new Request(CommonVars.getCurrUser(),
												true), lsResult, head);
						tableModelIssueBill.addRows(lsResult);
						getDgFptBillItemCommodityInfo(tableModelIssueBill,
								true, null);
					}
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes btnEditExgBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditExgBill() {
		if (btnEditExgBill == null) {
			btnEditExgBill = new JButton();
			btnEditExgBill.setText("修改");
			btnEditExgBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editExpData();
						}

					});
		}
		return btnEditExgBill;
	}

	/**
	 * This method initializes btnDeleteExgBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteExgBill() {
		if (btnDeleteExgBill == null) {
			btnDeleteExgBill = new JButton();
			btnDeleteExgBill.setText("删除");
			btnDeleteExgBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelIssueBill == null
									|| tableModelIssueBill.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(null,
										"请选择要删除的发货明细记录!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(null,
									"是否确定删除转厂发货明细记录?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List deleteList = tableModelIssueBill
									.getCurrentRows();
							Map<Integer, List<FptBillItem>> map = fptManageAction.deleteFptBillItem(
									new Request(CommonVars.getCurrUser()),
									deleteList);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelIssueBill.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelIssueBill.updateRows(lsUpdate);
							}

						}
					});
		}
		return btnDeleteExgBill;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbExpDetail());
		}
		return jScrollPane3;
	}

	/**
	 * 修改转出数据
	 */
	public void editExpData() {
		if (tableModelIssueBill == null
				|| tableModelIssueBill.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要修改的发货明细记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FptBillItem item = (FptBillItem) tableModelIssueBill.getCurrentRow();
		if (item.getCopGNo() != null && !"".equals(item.getCopGNo())) {
			getDgFptBillItemCommodityInfo(tableModelIssueBill, false, "update");
		} else {
			getDgFptBillItemCommodityInfo(tableModelIssueBill, true, "update");
		}

	}

	/**
	 * 修改转入数据
	 */
	public void editImpData() {
		if (tableModelReciveBill == null
				|| tableModelReciveBill.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要修改的收货明细记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FptBillItem item = (FptBillItem) tableModelReciveBill.getCurrentRow();
		if (item.getCopGNo() != null && !"".equals(item.getCopGNo())) {
			getDgFptBillItemCommodityInfo(tableModelReciveBill, false, "update");
		} else {
			getDgFptBillItemCommodityInfo(tableModelReciveBill, true, "update");
		}

	}

	/**
	 * This method initializes tbExpDetail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExpDetail() {
		if (tbExpDetail == null) {
			tbExpDetail = new JTable();
			tbExpDetail.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browExpData();
					}
				}
			});
			tbExpDetail.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							// if (e.getValueIsAdjusting()) {
							// return;
							// }
							JTableListModel tableModel = (JTableListModel) tbExpDetail
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {
								cx.printStackTrace();
							}
						}
					});
		}
		return tbExpDetail;
	}

	private void browExpData() {
		if (tableModelIssueBill == null
				|| tableModelIssueBill.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要修改的发货明细记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgFptBillItem dg = new DgFptBillItem();
		dg.setFptInOutFlag(fptInOutFlag);
		dg.setFptBusinessType(fptBusinessType);
		dg.setFptBillHead(head);
		dg.setTableModel(tableModelIssueBill);
		dg.setDataState(DataState.BROWSE);
		dg.setIsdataState(false);
		dg.setVisible(true);

	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJToolBar8(), java.awt.BorderLayout.NORTH);
			jPanel8.add(getJSplitPane5(), BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jToolBar8
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar8() {
		if (jToolBar8 == null) {
			jToolBar8 = new JToolBar();
			jToolBar8.setFloatable(false);
			jToolBar8.add(getBtnAddImpBill());
			jToolBar8.add(getBtnEditImpBill());
			jToolBar8.add(getBtnDeleteImpBill());
			jToolBar8.add(getBtnChangeBomModifyMark());
			jToolBar8.add(getBtnImpCopy());
			jToolBar8.add(getBtnResetImpListNo());
		}
		return jToolBar8;
	}

	/**
	 * This method initializes btnAddImpBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddImpBill() {
		if (btnAddImpBill == null) {
			btnAddImpBill = new JButton();
			btnAddImpBill.setText("增加");
			btnAddImpBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// getJPopupMenu1().show(getBtnAddImpBill(), 0,
							// getBtnAddImpBill().getHeight());

							if (head == null) {
								JOptionPane.showMessageDialog(null,
										"请先保存转厂单记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List lsResult = FptQuery.getInstance()
									.findFptAppItemtype(fptInOutFlag,
											head.getAppNo());
							if (lsResult != null && lsResult.size() > 0) {
								lsResult = fptManageAction
										.saveFptAppItemCommInfoToFptBillItemCommInfo(
												new Request(CommonVars
														.getCurrUser(), true),
												lsResult, head);
								tableModelReciveBill.addRows(lsResult);
								getDgFptBillItemCommodityInfo(
										tableModelReciveBill, true, "new");
							}
						}
					});
		}
		return btnAddImpBill;
	}

	private JPopupMenu getJPopupMenu1() {
		if (jPopupMenu1 == null) {
			jPopupMenu1 = new JPopupMenu();
			jPopupMenu1.setSize(new Dimension(70, 28));
			jPopupMenu1.add(getJMenuItem3());
		}
		return jPopupMenu1;
	}

	/**
	 * 申请表转入(转入)
	 * 
	 * @return
	 */
	private JMenuItem getJMenuItem3() {
		if (jMenuIn == null) {
			jMenuIn = new JMenuItem();
			jMenuIn.setSize(new Dimension(47, 24));
			jMenuIn.setText("转厂申请表转入");
			jMenuIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (head == null) {
						JOptionPane.showMessageDialog(null, "请先保存转厂单记录!!!",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List lsResult = FptQuery.getInstance().findFptAppItemtype(
							fptInOutFlag, head.getAppNo());
					if (lsResult != null && lsResult.size() > 0) {
						lsResult = fptManageAction
								.saveFptAppItemCommInfoToFptBillItemCommInfo(
										new Request(CommonVars.getCurrUser(),
												true), lsResult, head);
						tableModelReciveBill.addRows(lsResult);
						getDgFptBillItemCommodityInfo(tableModelReciveBill,
								true, null);
					}
				}
			});
		}
		return jMenuIn;
	}

	/**
	 * This method initializes btnEditImpBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditImpBill() {
		if (btnEditImpBill == null) {
			btnEditImpBill = new JButton();
			btnEditImpBill.setText("修改");
			btnEditImpBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editImpData();
						}
					});
		}
		return btnEditImpBill;
	}

	/**
	 * This method initializes btnDeleteImpBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteImpBill() {
		if (btnDeleteImpBill == null) {
			btnDeleteImpBill = new JButton();
			btnDeleteImpBill.setText("删除");
			btnDeleteImpBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelReciveBill == null
									|| tableModelReciveBill.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(null,
										"请选择要删除的收货明细记录!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(null,
									"是否确定删除转厂收货明细记录?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List deleteList = tableModelReciveBill
									.getCurrentRows();
							Map<Integer, List<FptBillItem>> map = fptManageAction.deleteFptBillItem(
									new Request(CommonVars.getCurrUser()),
									deleteList);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelReciveBill.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelReciveBill.updateRows(lsUpdate);
							}
						}
					});
		}
		return btnDeleteImpBill;
	}

	/**
	 * This method initializes btnChangeBomModifyMark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeBomModifyMark() {
		if (btnChangeBomModifyMark == null) {
			btnChangeBomModifyMark = new JButton();
			btnChangeBomModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeBomModifyMark
					.setText("\u6539\u53d8\u4fee\u6539\u6807\u5fd7");
			btnChangeBomModifyMark.setVisible(false);
		}
		return btnChangeBomModifyMark;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getTbImpDetail());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes tbImpDetail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImpDetail() {
		if (tbImpDetail == null) {
			tbImpDetail = new JTable();
			tbImpDetail.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browImpData();
					}
				}
			});
			tbImpDetail.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							// if (e.getValueIsAdjusting()) {
							// return;
							// }
							JTableListModel tableModel = (JTableListModel) tbImpDetail
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbImpDetail;
	}

	private void browImpData() {

		if (tableModelReciveBill == null
				|| tableModelReciveBill.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要修改的收货明细记录!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgFptBillItem dg = new DgFptBillItem();
		dg.setFptInOutFlag(fptInOutFlag);
		dg.setFptBusinessType(fptBusinessType);
		dg.setFptBillHead(head);
		dg.setTableModel(tableModelReciveBill);
		dg.setDataState(DataState.BROWSE);
		dg.setIsdataState(false);
		dg.setVisible(true);
	}

	/**
	 * This method initializes pnMaterialImg
	 * 
	 * @return javax.swing.JPanel
	 */

	private JPanel getPnMaterialImg() {
		if (pnMaterialImg == null) {
			pnMaterialImg = new JPanel();
			pnMaterialImg.setLayout(new BorderLayout());
			pnMaterialImg.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return pnMaterialImg;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(new Rectangle(11, 176, 662, 162));
			jPanel5.setBorder(BorderFactory.createTitledBorder(null, "转出企业填写",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 15), Color.blue));
			jPanel5.add(jLabel26, null);
			jPanel5.add(jLabel10, null);
			jPanel5.add(getTfExpCopBillNo(), null);
			jPanel5.add(getTfContractNo(), null);
			jPanel5.add(getTfExpIsDeclaEm(), null);
			jPanel5.add(jLabel37, null);
			jPanel5.add(getTfExpIsDeclaDate(), null);
			jPanel5.add(jLabel33, null);
			jPanel5.add(getTfExpIssueDate(), null);
			jPanel5.add(jLabel41, null);
			jPanel5.add(getTfExpNote(), null);
			jPanel5.add(jLabel231, null);
			jPanel5.add(getCbbExpAgentCode(), null);
			jPanel5.add(jLabel2311, null);
			jPanel5.add(getTfExAgentName(), null);
			jPanel5.add(jLabel22, null);
			jPanel5.add(getCbbExpTradeCod(), null);
			jPanel5.add(getBtnExpCopBillNO(), null);
			jPanel5.add(jLabel21, null);
			jPanel5.add(getTfExCopName(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes tfExpIssueDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfExpIssueDate() {
		if (tfExpIssueDate == null) {
			tfExpIssueDate = new JCalendarComboBox();
			tfExpIssueDate.setBounds(new Rectangle(446, 104, 183, 22));
		}
		return tfExpIssueDate;
	}

	/**
	 * This method initializes jPanel51
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel51() {
		if (jPanel51 == null) {
			// jLabel3 = new JLabel();
			// jLabel3.setBounds(new Rectangle(11, 134, 112, 23));
			// jLabel3.setText("转入手册号");
			// jLabel3.setForeground(Color.blue);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(11, 105, 112, 22));
			jLabel.setForeground(Color.blue);
			jLabel.setText("申报人");
			jLabel411 = new JLabel();
			jLabel411.setBounds(new Rectangle(10, 136, 115, 22));
			jLabel411.setText("备注");
			jLabel331 = new JLabel();
			jLabel331.setBounds(new Rectangle(315, 104, 124, 22));
			jLabel331.setForeground(Color.blue);
			jLabel331.setText("收货日期");
			jLabel371 = new JLabel();
			jLabel371.setBounds(new Rectangle(316, 75, 124, 22));
			jLabel371.setText("申报日期");
			jLabel261 = new JLabel();
			jLabel261.setBounds(new Rectangle(10, 76, 115, 22));
			jLabel261.setText("转入企业内部编号");
			jLabel261.setForeground(Color.blue);
			jPanel51 = new JPanel();
			jPanel51.setLayout(null);
			jPanel51.setBounds(new Rectangle(11, 348, 662, 181));
			jPanel51.setBorder(BorderFactory.createTitledBorder(null, "转入企业填写",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 15), Color.blue));
			jPanel51.add(jLabel261, null);
			jPanel51.add(getTfImpCopBillNo(), null);
			jPanel51.add(jLabel371, null);
			jPanel51.add(getTfImpIsDeclaDate(), null);
			jPanel51.add(jLabel331, null);
			jPanel51.add(getTfImpIssueDate(), null);
			jPanel51.add(getTfImpIsDeclaEm(), null);
			jPanel51.add(jLabel411, null);
			jPanel51.add(getTfImpNote(), null);
			jPanel51.add(jLabel, null);
			jPanel51.add(jLabel2312, null);
			jPanel51.add(getCbbImpAgentCode(), null);
			jPanel51.add(jLabel23111, null);
			jPanel51.add(getTfImAgentName(), null);
			jPanel51.add(jLabel23, null);
			jPanel51.add(getCbbImpTradeCod(), null);
			jPanel51.add(getJButton21(), null);
			jPanel51.add(jLabel25, null);
			jPanel51.add(getTfImCopName(), null);
			// jPanel51.add(jLabel3, null);
			// jPanel51.add(getcontractNoIn(), null);
		}
		return jPanel51;
	}

	/**
	 * This method initializes tfImpCopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpCopBillNo() {
		if (tfImpCopBillNo == null) {
			tfImpCopBillNo = new JTextField();
			tfImpCopBillNo.setBounds(new Rectangle(139, 76, 160, 22));
			tfImpCopBillNo.setEditable(false);
		}
		return tfImpCopBillNo;
	}

	/**
	 * This method initializes tfImpIsDeclaDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfImpIsDeclaDate() {
		if (tfImpIsDeclaDate == null) {
			tfImpIsDeclaDate = new JCalendarComboBox();
			tfImpIsDeclaDate.setBounds(new Rectangle(445, 74, 182, 22));
			tfImpIsDeclaDate.setDate(null);
			tfImpIsDeclaDate.setEnabled(false);
		}
		return tfImpIsDeclaDate;
	}

	/**
	 * This method initializes tfImpIssueDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfImpIssueDate() {
		if (tfImpIssueDate == null) {
			tfImpIssueDate = new JCalendarComboBox();
			tfImpIssueDate.setBounds(new Rectangle(445, 101, 182, 22));
		}
		return tfImpIssueDate;
	}

	/**
	 * This method initializes tfImpIsDeclaEm
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpIsDeclaEm() {
		if (tfImpIsDeclaEm == null) {
			tfImpIsDeclaEm = new JTextField();
			tfImpIsDeclaEm.setBounds(new Rectangle(139, 104, 160, 22));
		}
		return tfImpIsDeclaEm;
	}

	/**
	 * This method initializes tfImpNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpNote() {
		if (tfImpNote == null) {
			tfImpNote = new JTextField();
			tfImpNote.setBounds(new Rectangle(139, 136, 370, 22));
		}
		return tfImpNote;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	/**
	 **/
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(220);
			jSplitPane.setPreferredSize(new Dimension(455, 917));
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	/**
	 **/
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJSplitPane2(), gridBagConstraints);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	/**
	 **/
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.weightx = 1.0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJSplitPane3(), gridBagConstraints1);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	/**
	 **/
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setDividerSize(0);
			jSplitPane2.setPreferredSize(new Dimension(518, 423));
			jSplitPane2.setDividerLocation(20);
			jSplitPane2.setToolTipText("");
			jSplitPane2.setRightComponent(getJScrollPane());
			jSplitPane2.setLeftComponent(getJTextPane1());
			jSplitPane2.setComponentOrientation(ComponentOrientation.UNKNOWN);
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	/**
	 **/
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setComponentOrientation(ComponentOrientation.UNKNOWN);
			jScrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane.setViewportView(getTbExpMergerDetail());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbExpMergerDetail
	 * 
	 * @return javax.swing.JTable
	 */
	/**
	 **/
	private JTable getTbExpMergerDetail() {
		if (tbExpMergerDetail == null) {
			tbExpMergerDetail = new JTable();
		}
		return tbExpMergerDetail;
	}

	/**
	 * This method initializes jSplitPane3
	 * 
	 * @return javax.swing.JSplitPane
	 */
	/**
	 **/
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setDividerSize(0);
			jSplitPane3.setRightComponent(getJScrollPane1());
			jSplitPane3.setLeftComponent(getJTextPane());
			jSplitPane3.setDividerLocation(20);
		}
		return jSplitPane3;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	/**
	 **/
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbImpMergerDetail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbImpMergerDetail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImpMergerDetail() {
		if (tbImpMergerDetail == null) {
			tbImpMergerDetail = new JTable();
		}
		return tbImpMergerDetail;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	/**
	 **/
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setText("                 收货汇总");
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}

	/**
	 * This method initializes jTextPane1
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane1() {
		if (jTextPane1 == null) {
			jTextPane1 = new JTextPane();
			jTextPane1.setText("                发货汇总");
			jTextPane1.setEditable(false);
		}
		return jTextPane1;
	}

	/**
	 * This method initializes jSplitPane4
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane4() {
		if (jSplitPane4 == null) {
			jSplitPane4 = new JSplitPane();
			jSplitPane4.setDividerLocation(20);
			jSplitPane4.setDividerSize(0);
			jSplitPane4.setContinuousLayout(false);
			jSplitPane4.setLeftComponent(getJTextPane2());
			jSplitPane4.setRightComponent(getJScrollPane3());
		}
		return jSplitPane4;
	}

	/**
	 * This method initializes jTextPane2
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane2() {
		if (jTextPane2 == null) {
			jTextPane2 = new JTextPane();
			jTextPane2.setText("           发货明细");
			jTextPane2.setEditable(false);
		}
		return jTextPane2;
	}

	/**
	 * This method initializes jSplitPane5
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane5() {
		if (jSplitPane5 == null) {
			jSplitPane5 = new JSplitPane();
			jSplitPane5.setDividerLocation(20);
			jSplitPane5.setDividerSize(0);
			jSplitPane5.setLeftComponent(getJTextPane3());
			jSplitPane5.setRightComponent(getJScrollPane4());
		}
		return jSplitPane5;
	}

	/**
	 * This method initializes jTextPane3
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getJTextPane3() {
		if (jTextPane3 == null) {
			jTextPane3 = new JTextPane();
			jTextPane3.setText("         收货明细");
			jTextPane3.setEditable(false);
		}
		return jTextPane3;
	}

	/**
	 * This method initializes tfModifyMark
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfModifyMark() {
		if (tfModifyMark == null) {
			tfModifyMark = new JTextField();
			tfModifyMark.setBounds(new Rectangle(456, 32, 184, 21));
			tfModifyMark.setEditable(false);
		}
		return tfModifyMark;
	}

	/**
	 * This method initializes tfExAgentName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExAgentName() {
		if (tfExAgentName == null) {
			tfExAgentName = new JTextField();
			tfExAgentName.setBounds(new Rectangle(446, 51, 183, 22));
		}
		return tfExAgentName;
	}

	/**
	 * This method initializes tfImAgentName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImAgentName() {
		if (tfImAgentName == null) {
			tfImAgentName = new JTextField();
			tfImAgentName.setBounds(new Rectangle(445, 49, 182, 22));
		}
		return tfImAgentName;
	}

	/**
	 * This method initializes cbbExpAgentCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCbbExpAgentCode() {
		if (cbbExpAgentCode == null) {
			cbbExpAgentCode = new JTextField();
			cbbExpAgentCode.setDocument(new CustomDocument(9));
			cbbExpAgentCode.setBounds(new Rectangle(140, 51, 164, 22));
		}
		return cbbExpAgentCode;
	}

	/**
	 * This method initializes btnExpCopBillNO
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExpCopBillNO() {
		if (btnExpCopBillNO == null) {
			btnExpCopBillNO = new JButton();
			btnExpCopBillNO.setText("...");
			btnExpCopBillNO.setBounds(new Rectangle(278, 24, 25, 22));
			btnExpCopBillNO
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Brief brief = (Brief) CommonQuery.getInstance()
									.getCustomBrief(DgFptBillHead.this.brief);
							if (brief != null) {
								DgFptBillHead.this.brief = brief;
								getCbbExpTradeCod().setText(brief.getCode());
								getTfExCopName().setText(brief.getName());
							}
						}
					});
		}
		return btnExpCopBillNO;
	}

	/**
	 * This method initializes cbbExpTradeCod
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCbbExpTradeCod() {
		if (cbbExpTradeCod == null) {
			cbbExpTradeCod = new JTextField();
			cbbExpTradeCod.setEditable(false);
			cbbExpTradeCod.setBounds(new Rectangle(141, 24, 135, 22));
		}
		return cbbExpTradeCod;
	}

	/**
	 * This method initializes cbbImpTradeCod
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCbbImpTradeCod() {
		if (cbbImpTradeCod == null) {
			cbbImpTradeCod = new JTextField();
			cbbImpTradeCod.setEditable(false);
			cbbImpTradeCod.setBounds(new Rectangle(139, 25, 134, 22));
		}
		return cbbImpTradeCod;
	}

	/**
	 * This method initializes cbbImpAgentCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCbbImpAgentCode() {
		if (cbbImpAgentCode == null) {
			cbbImpAgentCode = new JTextField();
			cbbImpAgentCode.setDocument(new CustomDocument(9));
			cbbImpAgentCode.setBounds(new Rectangle(139, 52, 160, 22));
		}
		return cbbImpAgentCode;
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new JButton();
			jButton21.setText("...");
			jButton21.setBounds(new Rectangle(274, 25, 25, 22));
			jButton21.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Brief brief = (Brief) CommonQuery.getInstance()
							.getCustomBrief(DgFptBillHead.this.brief);
					if (brief != null) {
						DgFptBillHead.this.brief = brief;
						getCbbImpTradeCod().setText(brief.getCode());
						getTfImCopName().setText(brief.getName());
					}

				}
			});
		}
		return jButton21;
	}

	/**
	 * This method initializes tfInputDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfInputDate() {
		if (tfInputDate == null) {
			tfInputDate = new JCalendarComboBox();
			tfInputDate.setBounds(new Rectangle(373, 6, 124, 22));
			tfInputDate.setEnabled(false);
		}
		return tfInputDate;
	}

	public String getFptBusinessType() {
		return fptBusinessType;
	}

	public void setFptBusinessType(String fptBusinessType) {
		this.fptBusinessType = fptBusinessType;
	}

	/**
	 * This method initializes btnExpCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExpCopy() {
		if (btnExpCopy == null) {
			btnExpCopy = new JButton();
			btnExpCopy.setText("转抄");
			btnExpCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelIssueBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFptBillHead.this,
								"请选中要转抄转出明细资料", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgFptBillHead.this,
							"确定要转抄转出明细吗?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					if (tableModelIssueBill.getCurrentRow() != null) {
						List<FptBillItem> list = fptManageAction
								.copyFptBillExpImpDetail(
										new Request(CommonVars.getCurrUser()),
										tableModelIssueBill.getCurrentRows(),
										head);
						tableModelIssueBill.addRows(list);
					}
				}
			});
		}
		return btnExpCopy;
	}

	/**
	 * This method initializes btnImpCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImpCopy() {
		if (btnImpCopy == null) {
			btnImpCopy = new JButton();
			btnImpCopy.setText("转抄");
			btnImpCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModelReciveBill.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFptBillHead.this,
								"请选中要转抄转入明细资料", "提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgFptBillHead.this,
							"确定要转抄转入明细吗?", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					if (tableModelReciveBill.getCurrentRow() != null) {
						List<FptBillItem> list = fptManageAction
								.copyFptBillExpImpDetail(
										new Request(CommonVars.getCurrUser()),
										tableModelReciveBill.getCurrentRows(),
										head);
						tableModelReciveBill.addRows(list);
					}

				}
			});
		}
		return btnImpCopy;
	}

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new Rectangle(147, 81, 159, 22));
			cbbProjectType.setEnabled(false);
			cbbProjectType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// if (e.getStateChange() ==
					// java.awt.event.ItemEvent.SELECTED) {
					// }
					// if (cbbProjectType.getSelectedIndex() == 0)
					// jLabel19.setText("转出帐册编号");
					// else
					// jLabel19.setText("转出手册编号");

				}
			});
		}
		return cbbProjectType;
	}

	/**
	 * This method initializes btnAppNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAppNo() {
		if (btnAppNo == null) {
			btnAppNo = new JButton();
			btnAppNo.setBounds(new Rectangle(283, 33, 25, 21));
			btnAppNo.setText("...");
			btnAppNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 来源于转厂申请单
					@SuppressWarnings("rawtypes")
					List ls = (List) FptQuery.getInstance().findFptAppHeadType(
							fptInOutFlag);
					if (ls != null) {
						for (int i = 0; i < ls.size(); i++) {
							FptAppHead head = (FptAppHead) ls.get(i);
							getTfappNo().setText(head.getAppNo());
							getTfOutEmsNo().setText(head.getEmsNo());
							getCbbProjectType().setSelectedIndex(
									ItemProperty.getIndexByCode(head
											.getProjectType().toString(),
											getCbbProjectType()));
							cbbCustomer.setSelectedItem((ScmCoc) head
									.getScmCoc());

							if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
								cbbExpTradeCod
										.setText(head.getOutTradeCode() == null ? ""
												: head.getOutTradeCode());
								tfExCopName
										.setText(head.getOutTradeName() == null ? ""
												: head.getOutTradeName());
								cbbExpAgentCode
										.setText(head.getOutAgentCode() == null ? ""
												: head.getOutAgentCode());
								tfExAgentName
										.setText(head.getOutAgentName() == null ? ""
												: head.getOutAgentName());
								cbbImpTradeCod
										.setText(head.getInTradeCode() == null ? ""
												: head.getInTradeCode());
								tfImCopName
										.setText(head.getInTradeName() == null ? ""
												: head.getInTradeName());
							} else {
								cbbImpTradeCod
										.setText(head.getInTradeCode() == null ? ""
												: head.getInTradeCode());
								tfImCopName
										.setText(head.getInTradeName() == null ? ""
												: head.getInTradeName());
								cbbImpAgentCode
										.setText(head.getInAgentCode() == null ? ""
												: head.getInAgentCode());
								tfImAgentName
										.setText(head.getInAgentName() == null ? ""
												: head.getInAgentName());
								cbbExpTradeCod
										.setText(head.getOutTradeCode() == null ? ""
												: head.getOutTradeCode());
								tfExCopName
										.setText(head.getOutTradeName() == null ? ""
												: head.getOutTradeName());
							}
						}
					}
				}
			});
		}
		return btnAppNo;
	}

	/**
	 * This method initializes btnResetExpListNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnResetExpListNo() {
		if (btnResetExpListNo == null) {
			btnResetExpListNo = new JButton();
			btnResetExpListNo.setText("重排序号");
			btnResetExpListNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelIssueBill == null
									|| tableModelIssueBill.getList().size() <= 0) {
								return;
							}
							List list = tableModelIssueBill.getList();
							if (list.size() <= 0) {
								return;
							}
							Vector vet = new Vector();
							list = fptManageAction.findFptBillItems(
									new Request(CommonVars.getCurrUser()),
									head.getId(), head.getBillSort());
							// int beginSeqNum = fptManageAction
							// .findMaxValueByFptBillItemExceptAdd(
							// new Request(CommonVars
							// .getCurrUser()), head
							// .getId(), head
							// .getBillSort()) + 1;
							int beginSeqNum = 1;
							int size = list.size();
							for (int i = 0; i < size; i++) {
								vet.add(list.get(i));
							}
							DgFptBillItemSort dg = new DgFptBillItemSort();
							dg.setList(vet);
							dg.setFptManageAction(fptManageAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								if (head != null) {
									List outList = fptManageAction
											.findFptBillItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													head.getId(),
													FptInOutFlag.OUT);
									initExpDetailTable(outList);
								}
							}

						}
					});
		}
		return btnResetExpListNo;
	}

	/**
	 * This method initializes btnResetImpListNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnResetImpListNo() {
		if (btnResetImpListNo == null) {
			btnResetImpListNo = new JButton();
			btnResetImpListNo.setText("重排序号");
			btnResetImpListNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelReciveBill == null
									|| tableModelReciveBill.getList().size() <= 0) {
								return;
							}
							List list = tableModelReciveBill.getList();
							if (list.size() <= 0) {
								return;
							}
							Vector vet = new Vector();
							list = fptManageAction.findFptBillItems(
									new Request(CommonVars.getCurrUser()),
									head.getId(), head.getBillSort());
							// int beginSeqNum = fptManageAction
							// .findMaxValueByFptBillItemExceptAdd(
							// new Request(CommonVars
							// .getCurrUser()), head
							// .getId(), head
							// .getBillSort()) + 1;
							int beginSeqNum = 1;
							int size = list.size();
							for (int i = 0; i < size; i++) {
								vet.add(list.get(i));
							}
							DgFptBillItemSort dg = new DgFptBillItemSort();
							dg.setList(vet);
							dg.setFptManageAction(fptManageAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								if (head != null) {
									List inList = fptManageAction
											.findFptBillItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													head.getId(),
													FptInOutFlag.IN);
									initImpDetailTable(inList);
								}
							}

						}
					});
		}
		return btnResetImpListNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.setVisible(false);
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFptBillImport dg = new DgFptBillImport();
					dg.setFptBillHead(head);
					dg.setVisible(true);
					head = fptManageAction.findFptBillHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					tableModelHead.updateRow(head);
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (head != null) {
							String parentId = head.getId();
							List outlist = fptManageAction
									.findFptBillItemCommodityInfo(new Request(
											CommonVars.getCurrUser()),
											parentId, FptInOutFlag.OUT);
							List inlist = fptManageAction
									.findFptBillItemCommodityInfo(new Request(
											CommonVars.getCurrUser()),
											parentId, FptInOutFlag.IN);
							initExpDetailTable(outlist);
							initImpDetailTable(inlist);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (head != null) {
							String sysType = "";
							String parentId = head.getId();
							List outlist = fptManageAction
									.findFptBillDictItemCommodityInfo(
											new Request(CommonVars
													.getCurrUser()), parentId,
											FptInOutFlag.OUT, sysType);
							List inlist = fptManageAction
									.findFptBillDictItemCommodityInfo(
											new Request(CommonVars
													.getCurrUser()), parentId,
											FptInOutFlag.IN, sysType);
							initExpMergerDetailTable(outlist);
							initImpMergerDetailTable(inlist);
						}
					}
					setState();
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes btExput
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtExput() {
		if (btExput == null) {
			btExput = new JButton();
			btExput.setText("导出");
			btExput.setVisible(false);
			btExput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser("./");
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new ExampleFileFilter("xls"));
					String fileName = "";
					int state = fileChooser.showSaveDialog(DgFptBillHead.this);
					if (state == JFileChooser.APPROVE_OPTION) {
						File f = fileChooser.getSelectedFile();
						String description = fileChooser.getFileFilter()
								.getDescription();
						String suffix = description.substring(description
								.indexOf("."));
						if (f.getPath().indexOf(".") > 0) {
							fileName = f.getPath();
						} else {
							fileName = f.getPath() + suffix;
						}
					}
					File file = new File(fileName);
					TempResultSet ls = null;
					if (head != null) {
						String parentId = head.getId();
						ls = fptManageAction.exportFptBillItemToExcel(
								new Request(CommonVars.getCurrUser()),
								parentId, FptInOutFlag.IN);
					}

					exportXlsFile(ls, true, file, null);
				}
			});
		}
		return btExput;
	}

	private void exportXlsFile(TempResultSet tempResultSet,
			boolean isExistCaption, File file, String encoding) {
		WorkbookSettings wbs = new WorkbookSettings();
		List<List> rows = tempResultSet.getRows();
		String[] columnNames = tempResultSet.getColumnNames();
		WritableWorkbook workbook = null;
		if (encoding == null || encoding.trim().equals("")) {
			wbs.setEncoding("ISO-8859-1");
		} else {
			wbs.setEncoding(encoding);
		}

		try {
			workbook = Workbook.createWorkbook(file, wbs);
			String sheetCaption = "";
			WritableSheet ws = workbook.createSheet(
					sheetCaption.equals("") ? "导出的数据" : sheetCaption, 0);

			WritableFont wfHead = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.WHITE);
			WritableCellFormat wcfFHead = new WritableCellFormat(wfHead);
			try {
				wcfFHead.setAlignment(Alignment.CENTRE);
				wcfFHead.setVerticalAlignment(VerticalAlignment.CENTRE);
				wcfFHead.setBackground(Colour.GRAY_25);
				wcfFHead.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e2) {
				e2.printStackTrace();
			}

			WritableCellFormat wcfFDetail = new WritableCellFormat();
			try {
				wcfFDetail.setBorder(Border.ALL, BorderLineStyle.THIN);
			} catch (WriteException e2) {
				e2.printStackTrace();
			}
			if (isExistCaption) {
				for (int i = 0; i < columnNames.length; i++) {
					String columnName = columnNames[i];
					Label lb = new Label(i, 0, columnName, wcfFHead);
					try {
						ws.addCell(lb);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			int tempValue = isExistCaption ? 1 : 0;
			//
			// 保存记录
			//
			System.out.println("rows=" + rows.size());
			for (int i = 0; i < rows.size(); i++) {
				List row = rows.get(i);
				for (int j = 0; j < row.size(); j++) {
					Object value = row.get(j);

					if (value instanceof Integer || value instanceof Double) {
						if (value instanceof Integer) {
							Integer intValue = (value == null) ? 0
									: (Integer) value;
							jxl.write.Number labelN = new jxl.write.Number(j, i
									+ tempValue, intValue, wcfFDetail);
							try {
								ws.addCell(labelN);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Double intValue = (value == null) ? 0
									: (Double) value;
							jxl.write.Number labelN = new jxl.write.Number(j, i
									+ tempValue, intValue, wcfFDetail);
							try {
								ws.addCell(labelN);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} else if (value instanceof Boolean) {
						Boolean intValue = (value == null) ? false
								: (Boolean) value;
						jxl.write.Boolean labelN = new jxl.write.Boolean(j, i
								+ tempValue, intValue, wcfFDetail);
						try {
							ws.addCell(labelN);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						String str = "";
						if (value instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							str = bartDateFormat.format((Date) value);
						} else {
							if (value != null) {
								str = value.toString();
							}
						}
						System.out.println("str=" + str);
						jxl.write.Label labelN = new jxl.write.Label(j, i
								+ tempValue, str, wcfFDetail);
						try {
							ws.addCell(labelN);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.write();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				workbook.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	class ExampleFileFilter extends FileFilter {
		private List list = new ArrayList();

		ExampleFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}

	}

	/**
	 * This method initializes cbbCustomer
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(147, 111, 370, 21));
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes btnimport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnimport() {
		if (btnimport == null) {
			btnimport = new JButton();
			btnimport.setText("导入");
			btnimport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmImport().show(getBtnimport(), 0,
							getBtnimport().getHeight());
				}
			});
		}
		return btnimport;
	}

	/**
	 * This method initializes pmImport
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImport() {
		if (pmImport == null) {
			pmImport = new JPopupMenu();
			pmImport.add(getJMenuItem32());
			pmImport.addSeparator();
			pmImport.add(getJMenuItem33());
		}
		return pmImport;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem32() {
		if (miExcelImport == null) {
			miExcelImport = new JMenuItem();
			miExcelImport.setText("从Excel导入");
			miExcelImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgFptBillHeadImportFromExcel dg = new DgFptBillHeadImportFromExcel();
							dg.setFptInOutFlag(fptInOutFlag);
							dg.setHead(head);
							dg.setVisible(true);

							List outlist = fptManageAction
									.findFptBillItemCommodityInfo(new Request(
											CommonVars.getCurrUser()), head
											.getId(), FptInOutFlag.OUT);
							List inlist = fptManageAction
									.findFptBillItemCommodityInfo(new Request(
											CommonVars.getCurrUser()), head
											.getId(), FptInOutFlag.IN);
							initExpDetailTable(outlist);
							initImpDetailTable(inlist);
						}
					});
		}
		return miExcelImport;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem33() {
		if (miBillCenterImport == null) {
			miBillCenterImport = new JMenuItem();
			miBillCenterImport.setText("从单据中心导入");
			miBillCenterImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (!LicenseClient.getInstance(
									CommonVars.getCurrUser().getCompany()
											.getName())
									.checkFptManagePermisson()) {
								JOptionPane.showMessageDialog(
										DgFptBillHead.this,
										"没有使用此功能的权限，如果需要请联系百思维！");
								return;
							}
							DgMakeFptBill dialog = new DgMakeFptBill();
							dialog.setNewFptBillHead(false);
							dialog.setOnlyImportDetail(true);
							dialog.setOldFptBillHead(head);
							dialog.setVisible(true);
							if (jTabbedPane.getSelectedIndex() == 1) {
								if (FptBusinessType.FPT_BILL
										.equals(fptBusinessType)) {
									jTextPane2.setText("           发货明细");
									jTextPane3.setText("           收货明细");
								} else {
									jTextPane2.setText("           收退货明细");
									jTextPane3.setText("           发退货明细");
								}
								if (dataState != DataState.BROWSE) {
									if (!validateData()) {
										jTabbedPane.setSelectedIndex(0);
									}
								}
								if (head != null) {
									String parentId = head.getId();
									List outlist = fptManageAction
											.findFptBillItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.OUT);
									List inlist = fptManageAction
											.findFptBillItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.IN);
									initExpDetailTable(outlist);
									initImpDetailTable(inlist);
								}
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								if (FptBusinessType.FPT_BILL
										.equals(fptBusinessType)) {
									jTextPane1.setText("           发货汇总");
									jTextPane.setText("           收货汇总");
								} else {
									jTextPane1.setText("           收退货汇总");
									jTextPane.setText("           发退货汇总");
								}
								if (dataState != DataState.BROWSE) {
									if (!validateData()) {
										jTabbedPane.setSelectedIndex(0);
									}
								}
								if (head != null) {
									String parentId = head.getId();
									String sysType = "";
									List outlist = fptManageAction
											.findFptBillDictItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.OUT,
													sysType);
									List inlist = fptManageAction
											.findFptBillDictItemCommodityInfo(
													new Request(CommonVars
															.getCurrUser()),
													parentId, FptInOutFlag.IN,
													sysType);
									initExpMergerDetailTable(outlist);
									initImpMergerDetailTable(inlist);
								}

							}
							setState();
						}

					});
		}
		return miBillCenterImport;
	}

	// 判断字符限数
	class CustomDocument extends PlainDocument {

		int len;

		public CustomDocument(int len) {
			this.len = len;
		}

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= len || str.length() > len
					|| super.getLength() + str.length() > len) {
				return;
			}

			super.insertString(offs, str, a);
		}
	}

	private JComboBox getCbbTransferMode() {
		if (cbbTransferMode == null) {
			cbbTransferMode = new JComboBox();
			cbbTransferMode.setBounds(147, 138, 159, 21);
		}
		return cbbTransferMode;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
