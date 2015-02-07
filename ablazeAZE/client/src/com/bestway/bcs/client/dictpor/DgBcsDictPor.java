package com.bestway.bcs.client.dictpor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JPopupMenu.Separator;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.client.contract.BcsClientHelper;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgBcsDictPor extends JDialogBase {

	private JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	
	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar = null;

	private JButton btnHeadEdit = null;

	private JButton btnHeadSave = null;

	private JButton btnHeadUndo = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel = null;

	private JTextField tfCopEmsNo = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbDeclareCustoms = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel21 = null;

	private JTextField tfCode = null;

	private JTextField tfName = null;

	private JLabel jLabel22 = null;

	private JCalendarComboBox cbbDeclareDate = null;

	private JLabel jLabel221 = null;

	private JFormattedTextField tfProductRatio = null;

	private JLabel jLabel222 = null;

	private JTextField tfDictPorEmsNo = null;

	private JLabel jLabel11 = null;

	private JTextField tfMemo = null;

	private JToolBar jToolBar1 = null;

	private JButton btnImgAdd = null;

	private JButton btnImgDelete = null;

	private JButton btnImgEdit = null;

	private JScrollPane jScrollPane = null;

	private JTable tbImg = null;

	private JToolBar jToolBar2 = null;

	private JButton btnExgAdd = null;

	private JButton btnExgEdit = null;

	private JButton btnExgDelete = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbExg = null;

	private int headDataState = DataState.BROWSE;

	private BcsDictPorAction bcsDictPorAction = null;

	private BcsDictPorHead bcsDictPorHead = null;

	private JTableListModel tableModelHead = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbManageObject = null;

	private JScrollPane jScrollPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JTextArea taImgRangeSpec = null;

	private JTextArea taExgRangeSpec = null;

	private JSplitPane jSplitPane = null;

	private JButton btnPrint = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbLevyKind = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JComboBox cbbTrade = null;

	private JComboBox cbbReceiveArea = null;

	private JComboBox cbbCurr = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JComboBox cbbMachiningType = null;

	private JButton jButton = null;

	private JLabel jLabel9 = null;

	private JComboBox cbbLimitFlag = null;

	private JButton btnImgSort = null;

	private JButton btnExgSort = null;

	private JButton btnChangeDeclareState = null;

	private JPopupMenu pmChangeDeclareState = null; // @jve:decl-index=0:visual-constraint="748,102"

	private JMenuItem miUndoDeclare = null;

	private JButton btnChangeExgModifyMark = null;

	private JPopupMenu pmChangeModifyMark = null; // @jve:decl-index=0:visual-constraint="776,193"

	private JMenuItem miNotModified = null;

	private JMenuItem miModified = null;

	private JMenuItem miDelete = null;
	private JMenuItem miAdd = null;

	private JButton btnChangeImgModifyMark = null;

    private JPopupMenu pmEdiFunction = null;
	
	private JMenuItem btnPrint1 = null;
	
	private JMenuItem btnPrintChange = null;

	private JMenuItem jMenuItem = null;

	private JMenuItem btnPrintProcedureChange = null;

	private JMenuItem btnPrintMaterialChange = null;
	
	private BcsParameterSet parameterSet = null; // @jve:decl-index=0:
	
	private ContractAction contractAction = null;
	
	private String manageType = "0"; // @jve:decl-index=0:
	
	
	public int getHeadDataState() {
		return headDataState;
	}

	public void setHeadDataState(int headDataState) {
		this.headDataState = headDataState;
	}

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
	public DgBcsDictPor() {
		super();
		initialize();
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
		.getBean("contractAction");
		 parameterSet = contractAction
			.findBcsParameterSet(new Request(CommonVars.getCurrUser()));
			if (parameterSet != null && parameterSet.getManageType() != null) {
				this.manageType = parameterSet.getManageType();
			}
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(689, 563));
		this.setTitle("备案资料库备案");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			if (tableModelHead != null) {
				bcsDictPorHead = (BcsDictPorHead) tableModelHead
						.getCurrentRow();
			}
			showHeadData(bcsDictPorHead);
			setState();
		}
		super.setVisible(b);
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("基本信息", null, getJPanel(), null);
			jTabbedPane.addTab("料件表", null, getJPanel1(), null);
			jTabbedPane.addTab("成品表", null, getJPanel2(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							int index = jTabbedPane.getSelectedIndex();
							if (index == 0) {
								bcsDictPorHead = bcsDictPorAction
										.findBcsDictPorHeadById(new Request(
												CommonVars.getCurrUser()),
												bcsDictPorHead.getId());
								tableModelHead.updateRow(bcsDictPorHead);
								showHeadData(bcsDictPorHead);
							} else if (index == 1) {
								initImgTable();
								setState();
							} else if (index == 2) {
								initExgTable();
								setState();
							}
						}
					});
		}
		return jTabbedPane;
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel3(), BorderLayout.CENTER);
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
			jPanel1.add(getJToolBar1(), BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
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
			jPanel2.add(getJToolBar2(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnHeadEdit());
			jToolBar.add(getBtnHeadSave());
			jToolBar.add(getBtnHeadUndo());
			jToolBar.add(getBtnChangeDeclareState());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnPrint());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHeadEdit() {
		if (btnHeadEdit == null) {
			btnHeadEdit = new JButton();
			btnHeadEdit.setText("修改");
			btnHeadEdit.setPreferredSize(new Dimension(65, 30));
			btnHeadEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					headDataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnHeadEdit;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHeadSave() {
		if (btnHeadSave == null) {
			btnHeadSave = new JButton();
			btnHeadSave.setText("保存");
			btnHeadSave.setPreferredSize(new Dimension(65, 30));
			btnHeadSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull()) {
						return;
					}
					fillHeadData(bcsDictPorHead);
					bcsDictPorHead = bcsDictPorAction.saveBcsDictPorHead(
							new Request(CommonVars.getCurrUser()),
							bcsDictPorHead);
					tableModelHead.updateRow(bcsDictPorHead);
					headDataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnHeadSave;
	}
	/**
	 * 判断保存表单内容
	 * @return
	 */
	private boolean vaildatorDataIsNull() {
		if(tfCopEmsNo.getText().length()>18){
			JOptionPane.showMessageDialog(null, "企业内部编号不可大于18位！", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHeadUndo() {
		if (btnHeadUndo == null) {
			btnHeadUndo = new JButton();
			btnHeadUndo.setText("取消");
			btnHeadUndo.setPreferredSize(new Dimension(65, 30));
			btnHeadUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showHeadData(bcsDictPorHead);
					headDataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnHeadUndo;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(322, 191, 107, 24));
			jLabel9.setText("限制类标志(可不填)");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(24, 192, 86, 21));
			jLabel8.setText("加工种类");
			jLabel8.setForeground(Color.blue);
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(324, 165, 93, 22));
			jLabel7.setText("币制");
			jLabel7.setForeground(Color.blue);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(24, 165, 86, 22));
			jLabel6.setText("地区代码");
			jLabel6.setForeground(Color.blue);
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(322, 136, 94, 24));
			jLabel5.setText("贸易方式");
			jLabel5.setForeground(Color.blue);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(24, 141, 86, 18));
			jLabel4.setText("征免性质");
			jLabel4.setForeground(Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(322, 109, 92, 25));
			jLabel3.setText("管理对象");
			jLabel3.setForeground(Color.blue);
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(24, 217, 75, 23));
			jLabel11.setText("备注");
			jLabel222 = new JLabel();
			jLabel222.setBounds(new Rectangle(24, 110, 86, 25));
			jLabel222.setText("备案资料库编号");
			jLabel221 = new JLabel();
			jLabel221.setBounds(new Rectangle(322, 79, 93, 22));
			jLabel221.setText("生产能力(万美元)");
			jLabel221.setForeground(Color.blue);
			jLabel22 = new JLabel();
			jLabel22.setBounds(new Rectangle(24, 78, 86, 24));
			jLabel22.setText("申报日期");
			jLabel22.setForeground(Color.blue);
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(322, 50, 96, 23));
			jLabel21.setText("加工贸易企业名称");
			jLabel21.setForeground(Color.blue);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(24, 49, 96, 24));
			jLabel2.setText("加工贸易企业编号");
			jLabel2.setForeground(Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setText("主管海关");
			jLabel1.setForeground(Color.blue);
			jLabel1.setBounds(new Rectangle(322, 21, 93, 23));
			jLabel = new JLabel();
			jLabel.setText("企业内部编号");
			jLabel.setForeground(Color.blue);
			jLabel.setBounds(new Rectangle(24, 20, 86, 24));
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(jLabel, null);
			jPanel3.add(getTfCopEmsNo(), null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(getCbbDeclareCustoms(), null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(jLabel21, null);
			jPanel3.add(getTfCode(), null);
			jPanel3.add(getTfName(), null);
			jPanel3.add(jLabel22, null);
			jPanel3.add(getCbbDeclareDate(), null);
			jPanel3.add(jLabel221, null);
			jPanel3.add(getTfProductRatio(), null);
			jPanel3.add(jLabel222, null);
			jPanel3.add(getTfDictPorEmsNo(), null);
			jPanel3.add(jLabel11, null);
			jPanel3.add(getTfMemo(), null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(getCbbManageObject(), null);
			jPanel3.add(getJSplitPane(), null);
			jPanel3.add(jLabel4, null);
			jPanel3.add(getCbbLevyKind(), null);
			jPanel3.add(jLabel5, null);
			jPanel3.add(jLabel6, null);
			jPanel3.add(getCbbTrade(), null);
			jPanel3.add(getCbbReceiveArea(), null);
			jPanel3.add(getCbbCurr(), null);
			jPanel3.add(jLabel7, null);
			jPanel3.add(jLabel8, null);
			jPanel3.add(getCbbMachiningType(), null);
			jPanel3.add(jLabel9, null);
			jPanel3.add(getCbbLimitFlag(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(new Rectangle(120, 20, 193, 23));
		}
		return tfCopEmsNo;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustoms() {
		if (cbbDeclareCustoms == null) {
			cbbDeclareCustoms = new JComboBox();
			cbbDeclareCustoms.setBounds(new Rectangle(418, 20, 195, 23));
		}
		return cbbDeclareCustoms;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new Rectangle(120, 49, 193, 23));
			tfCode.setEditable(false);
		}
		return tfCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(418, 49, 195, 23));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeclareDate() {
		if (cbbDeclareDate == null) {
			cbbDeclareDate = new JCalendarComboBox();
			cbbDeclareDate.setBounds(new Rectangle(120, 78, 193, 24));
			cbbDeclareDate.setEnabled(false);
		}
		return cbbDeclareDate;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfProductRatio() {
		if (tfProductRatio == null) {
			tfProductRatio = new JFormattedTextField();
			tfProductRatio.setBounds(new Rectangle(418, 78, 195, 23));
		}
		return tfProductRatio;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDictPorEmsNo() {
		if (tfDictPorEmsNo == null) {
			tfDictPorEmsNo = new JTextField();
			tfDictPorEmsNo.setBounds(new Rectangle(120, 110, 193, 24));
		}
		return tfDictPorEmsNo;
	}

	/**
	 * This method initializes jTextField111
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (str == null) {
						return;
					}
					if (super.getLength() >= 50 || str.getBytes().length > 50
							|| (super.getLength() + str.getBytes().length) > 50) {
						return;
					}
					super.insertString(offs, str, a);
				}
			});
			tfMemo.setBounds(new Rectangle(120, 217, 497, 25));
		}
		return tfMemo;
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
			jToolBar1.setLayout(f);
			jToolBar1.add(getBtnImgAdd());
			jToolBar1.add(getBtnImgEdit());
			jToolBar1.add(getBtnImgDelete());
			jToolBar1.add(getBtnImgSort());
			jToolBar1.add(getBtnChangeImgModifyMark());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgAdd() {
		if (btnImgAdd == null) {
			btnImgAdd = new JButton();
			btnImgAdd.setText("新增");
			btnImgAdd.setPreferredSize(new Dimension(65, 30));
			btnImgAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					if (CommonVars.getContractFrom() == null) {
						return;
					}
					List lsImg = new ArrayList();
					if (CommonVars.getContractFrom().equals("2")) {
						list = BcsClientHelper.getInstance().getComplex();
						if (list == null || list.size() <= 0) {
							return;
						}
						lsImg = bcsDictPorAction.addBcsDictPorImgByComplex(
								new Request(CommonVars.getCurrUser()),
								bcsDictPorHead, list);
						tableModelImg.addRows(lsImg);
					} else if (CommonVars.getContractFrom().equals("1")) {
						list = BcsDictPorHelper.getInstance()
								.findInnerMergeNotInBcsDictPor(bcsDictPorHead,
										MaterielType.MATERIEL, true);
						if (list != null && list.size() > 0) {
							lsImg = bcsDictPorAction
									.addBcsDictPorImgByInnerMerge(new Request(
											CommonVars.getCurrUser()),
											bcsDictPorHead, list);
							tableModelImg.addRows(lsImg);
						}
					} else if (CommonVars.getContractFrom().equals("0")) {
						list = BcsDictPorHelper.getInstance()
								.findInnerMergeNotInBcsDictPor(bcsDictPorHead,
										MaterielType.MATERIEL, false);
						if (list != null && list.size() > 0) {
							lsImg = bcsDictPorAction
									.addBcsDictPorImgByInnerMerge(new Request(
											CommonVars.getCurrUser()),
											bcsDictPorHead, list);
							tableModelImg.addRows(lsImg);
						}
					}
					if (lsImg != null && lsImg.size() > 0) {
						DgBcsDictPorImg dg = new DgBcsDictPorImg();
						dg.setTableModel(tableModelImg);
						dg.setDataState(DataState.EDIT);
						dg.setBcsDictPorHead(bcsDictPorHead);
						dg.setVisible(true);
					}
				}
			});
		}
		return btnImgAdd;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgDelete() {
		if (btnImgDelete == null) {
			btnImgDelete = new JButton();
			btnImgDelete.setText("删除");
			btnImgDelete.setPreferredSize(new Dimension(65, 30));
			btnImgDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModelImg.getCurrentRows();
					if (list.size() > 0) {
						if (JOptionPane.showConfirmDialog(DgBcsDictPor.this,
								"确认是否删除这些记录?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					} else {
						return;
					}
					Map<Integer, List<BcsDictPorImg>> map = bcsDictPorAction
							.deleteBcsDictPorImg(new Request(CommonVars
									.getCurrUser()), list);
					List lsDelete = map.get(DeleteResultMark.DELETE);
					if (lsDelete != null && lsDelete.size() > 0) {
						tableModelImg.deleteRows(lsDelete);
					}
					List lsUpdate = map.get(DeleteResultMark.UPDATE);
					if (lsUpdate != null && lsUpdate.size() > 0) {
						tableModelImg.updateRows(lsUpdate);
					}
				}
			});
		}
		return btnImgDelete;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgEdit() {
		if (btnImgEdit == null) {
			btnImgEdit = new JButton();
			btnImgEdit.setText("修改");
			btnImgEdit.setPreferredSize(new Dimension(65, 30));
			btnImgEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBcsDictPor.this,
								"请选择要修改的料件", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgBcsDictPorImg dg = new DgBcsDictPorImg();
					dg.setTableModel(tableModelImg);
					dg.setDataState(DataState.EDIT);
					dg.setBcsDictPorHead(bcsDictPorHead);
					dg.setVisible(true);
				}
			});
		}
		return btnImgEdit;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbImg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
		}
		return tbImg;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar2 = new JToolBar();
			jToolBar2.setLayout(f);
			jToolBar2.add(getBtnExgAdd());
			jToolBar2.add(getBtnExgEdit());
			jToolBar2.add(getBtnExgDelete());
			jToolBar2.add(getBtnExgSort());
			jToolBar2.add(getBtnChangeExgModifyMark());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgAdd() {
		if (btnExgAdd == null) {
			btnExgAdd = new JButton();
			btnExgAdd.setText("新增");
			btnExgAdd.setPreferredSize(new Dimension(65, 30));
			btnExgAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();
					if (CommonVars.getContractFrom() == null) {
						return;
					}
					List lsExg = new ArrayList();
					if (CommonVars.getContractFrom().equals("2")) {
						list = BcsClientHelper.getInstance().getComplex();
						if (list == null || list.size() <= 0) {
							return;
						}
						lsExg = bcsDictPorAction.addBcsDictPorExgByComplex(
								new Request(CommonVars.getCurrUser()),
								bcsDictPorHead, list);
						tableModelExg.addRows(lsExg);
					} else if (CommonVars.getContractFrom().equals("1")) {
						list = BcsDictPorHelper.getInstance()
								.findInnerMergeNotInBcsDictPor(bcsDictPorHead,
										MaterielType.FINISHED_PRODUCT, true);
						if (list != null && list.size() > 0) {
							lsExg = bcsDictPorAction
									.addBcsDictPorExgByInnerMerge(new Request(
											CommonVars.getCurrUser()),
											bcsDictPorHead, list);
							tableModelExg.addRows(lsExg);
						}
					} else if (CommonVars.getContractFrom().equals("0")) {
						list = BcsDictPorHelper.getInstance()
								.findInnerMergeNotInBcsDictPor(bcsDictPorHead,
										MaterielType.FINISHED_PRODUCT, false);
						if (list != null && list.size() > 0) {
							lsExg = bcsDictPorAction
									.addBcsDictPorExgByInnerMerge(new Request(
											CommonVars.getCurrUser()),
											bcsDictPorHead, list);
							tableModelExg.addRows(lsExg);
						}
					}
					if (lsExg != null && lsExg.size() > 0) {
						DgBcsDictPorExg dg = new DgBcsDictPorExg();
						dg.setTableModel(tableModelExg);
						dg.setDataState(DataState.EDIT);
						dg.setBcsDictPorHead(bcsDictPorHead);
						dg.setVisible(true);
					}
				}
			});
		}
		return btnExgAdd;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgEdit() {
		if (btnExgEdit == null) {
			btnExgEdit = new JButton();
			btnExgEdit.setText("修改");
			btnExgEdit.setPreferredSize(new Dimension(65, 30));
			btnExgEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBcsDictPor.this,
								"请选择要修改的成品", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgBcsDictPorExg dg = new DgBcsDictPorExg();
					dg.setTableModel(tableModelExg);
					dg.setDataState(DataState.EDIT);
					dg.setBcsDictPorHead(bcsDictPorHead);
					dg.setVisible(true);
				}
			});
		}
		return btnExgEdit;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgDelete() {
		if (btnExgDelete == null) {
			btnExgDelete = new JButton();
			btnExgDelete.setText("删除");
			btnExgDelete.setPreferredSize(new Dimension(65, 30));
			btnExgDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModelExg.getCurrentRows();
					if (list.size() > 0) {
						if (JOptionPane.showConfirmDialog(DgBcsDictPor.this,
								"确认是否删除这些记录?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					} else {
						return;
					}
					Map<Integer, List<BcsDictPorExg>> map = bcsDictPorAction
							.deleteBcsDictPorExg(new Request(CommonVars
									.getCurrUser()), list);
					List lsDelete = map.get(DeleteResultMark.DELETE);
					if (lsDelete != null && lsDelete.size() > 0) {
						tableModelExg.deleteRows(lsDelete);
					}
					List lsUpdate = map.get(DeleteResultMark.UPDATE);
					if (lsUpdate != null && lsUpdate.size() > 0) {
						tableModelExg.updateRows(lsUpdate);
					}
				}
			});
		}
		return btnExgDelete;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
		}
		return tbExg;
	}

	private void showHeadData(BcsDictPorHead head) {
		if (head == null) {
			return;
		}
		this.tfCopEmsNo.setText(head.getCopEmsNo());
		this.tfDictPorEmsNo.setText(head.getDictPorEmsNo());
		this.tfCode.setText(head.getMachCode());
		this.tfName.setText(head.getMachName());
		this.cbbDeclareDate.setDate(head.getDeclareDate());
		this.tfProductRatio.setValue(head.getProductRatio());
		this.cbbDeclareCustoms.setSelectedItem(head.getDeclareCustoms());
		this.cbbCurr.setSelectedItem(head.getCurr());
		this.cbbLevyKind.setSelectedItem(head.getLevyKind());
		this.cbbMachiningType.setSelectedItem(head.getMachiningType());
		this.cbbReceiveArea.setSelectedItem(head.getReceiveArea());
		this.cbbTrade.setSelectedItem(head.getTrade());
		cbbManageObject.setSelectedIndex(ItemProperty.getIndexByCode(String
				.valueOf(head.getManageObject()), cbbManageObject));
		this.tfMemo.setText(head.getMemo());
		this.taImgRangeSpec.setText(head.getImgRangeSpec());
		this.taExgRangeSpec.setText(head.getExgRangeSpec());
		this.cbbLimitFlag.setSelectedIndex(ItemProperty.getIndexByCode(head
				.getLimitFlag(), cbbLimitFlag));
	}

	private void fillHeadData(BcsDictPorHead head) {
		if (head == null) {
			return;
		}
		// int index = cbbManageObject.getSelectedIndex();
		// if (bcsDictPorHead != null && index >= 0) {
        // Company company = (Company) bcsDictPorHead.getCompany();
		// if (index == 0) {
		// tfCode.setText(company.getBuCode());
		// tfName.setText(company.getBuName());
		// } else if (index == 1) {
		// tfCode.setText(company.getCode());
		// tfName.setText(company.getName());
		// }
		// }
		// head.setTradeCode(tfCode.getText().trim());
		// head.setTradeName(tfName.getText().trim());
		// head.setMachCode(tfCode.getText().trim());
		// head.setMachName(tfName.getText().trim());
		head.setCopEmsNo(this.tfCopEmsNo.getText().trim());
		head.setDeclareCustoms((Customs) this.cbbDeclareCustoms
				.getSelectedItem());
		head.setLevyKind((LevyKind) this.cbbLevyKind.getSelectedItem());
		head.setReceiveArea((District) this.cbbReceiveArea.getSelectedItem());
		head.setTrade((Trade) this.cbbTrade.getSelectedItem());
		head.setMachiningType((MachiningType) this.cbbMachiningType
				.getSelectedItem());
		head.setCurr((Curr) this.cbbCurr.getSelectedItem());
		head.setProductRatio(this.tfProductRatio.getValue() == null ? 0.0
				: Double.valueOf(this.tfProductRatio.getValue().toString()));
		head.setImgRangeSpec(this.taImgRangeSpec.getText().trim());
		head.setExgRangeSpec(this.taExgRangeSpec.getText().trim());
		if (this.cbbManageObject.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbManageObject
					.getSelectedItem();
			head.setManageObject(item.getCode());
		}
		if (this.cbbLimitFlag.getSelectedItem() != null) {
			head.setLimitFlag(((ItemProperty) cbbLimitFlag.getSelectedItem())
					.getCode());
		} else {
			head.setLimitFlag(null);
		}
		head.setMemo(this.tfMemo.getText().trim());
		head.setDictPorEmsNo(tfDictPorEmsNo.getText().trim());
	}

	/**
	 * 初始化数据Table
	 */
	private void initImgTable() {
		BcsDictPorHead head = (BcsDictPorHead) tableModelHead.getCurrentRow();
		if (head == null) {
			return;
		}
		List list = bcsDictPorAction.findBcsDictPorImgByHead(new Request(
				CommonVars.getCurrUser()), head);
		tableModelImg = new JTableListModel(tbImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 100));
						list
								.add(addColumn("备案序号", "seqNum", 100,
										Integer.class));
						list.add(addColumn("归并序号", "innerMergeSeqNum", 100,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 60));
						list.add(addColumn("计量单位", "comUnit.name", 100));
						list.add(addColumn("主料标志", "isMainImg", 100));
						list.add(addColumn("申报单价", "unitPrice", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});
		tbImg.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		tbImg.getColumnModel().getColumn(8).setCellRenderer(
				new TableCheckBoxRender());
	}

	/**
	 * 初始化数据Table
	 */
	private void initExgTable() {
		BcsDictPorHead head = (BcsDictPorHead) tableModelHead.getCurrentRow();
		if (head == null) {
			return;
		}
		List list = bcsDictPorAction.findBcsDictPorExgByHead(new Request(
				CommonVars.getCurrUser()), head);
		tableModelExg = new JTableListModel(tbExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("修改标志", "modifyMark", 100));
						list
								.add(addColumn("备案序号", "seqNum", 100,
										Integer.class));
						list.add(addColumn("归并序号", "innerMergeSeqNum", 100,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 60));
						list.add(addColumn("计量单位", "comUnit.name", 100));
						// list.add(addColumn("主料标志", "", 100));
						list.add(addColumn("申报单价", "unitPrice", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});
		tbExg.getColumnModel().getColumn(1).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
	}

	private void setState() {
		String declareState = bcsDictPorHead.getDeclareState();
		this.btnHeadEdit
				.setEnabled(headDataState == DataState.BROWSE
						&& (DeclareState.APPLY_POR.equals(declareState) || DeclareState.CHANGING_EXE
								.equals(declareState)));
		this.btnHeadSave.setEnabled(headDataState != DataState.BROWSE);
		this.btnHeadUndo.setEnabled(headDataState != DataState.BROWSE);
		this.btnImgAdd.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		jButton.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnImgEdit.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnImgDelete.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnExgAdd.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnExgEdit.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnExgDelete.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnImgSort.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnExgSort.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.tfProductRatio.setEditable(headDataState != DataState.BROWSE);
		this.cbbDeclareCustoms.setEnabled(headDataState != DataState.BROWSE);
		this.cbbCurr.setEnabled(headDataState != DataState.BROWSE);
		this.cbbLevyKind.setEnabled(headDataState != DataState.BROWSE);
		this.cbbMachiningType.setEnabled(headDataState != DataState.BROWSE);
		this.cbbTrade.setEnabled(headDataState != DataState.BROWSE);
		this.cbbReceiveArea.setEnabled(headDataState != DataState.BROWSE);
		this.cbbManageObject.setEnabled(headDataState != DataState.BROWSE);
		this.cbbLimitFlag.setEnabled(headDataState != DataState.BROWSE);
		this.tfMemo.setEditable(headDataState != DataState.BROWSE);
		this.btnPrint.setEnabled(headDataState == DataState.BROWSE
				|| DeclareState.CHANGING_EXE.equals(declareState));

		this.taImgRangeSpec.setEditable(headDataState != DataState.BROWSE);

		this.taExgRangeSpec.setEditable(headDataState != DataState.BROWSE);

		this.getMiUndoDeclare().setEnabled(
				DeclareState.WAIT_EAA.equals(declareState));
		this.btnChangeExgModifyMark.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnChangeImgModifyMark.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));

		this.tfCopEmsNo.setEditable(headDataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		
		//管理类别 0为既用于关务管理也向海关申报数据;1为仅用于关务管理不向海关申报数据
		if (parameterSet != null && parameterSet.getManageType() != null) {
			if (parameterSet.getManageType().equals("1")) {//
				tfDictPorEmsNo.setEditable(headDataState != DataState.BROWSE
						&& DeclareState.APPLY_POR.equals(declareState));
			}
			else{
				tfDictPorEmsNo.setEditable(false);
			}
		}
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		//this.cbbPrintParam.removeAllItems();
		//this.cbbPrintParam.addItem("  打印加工贸易备案资料申请表");
		//this.cbbPrintParam.addItem("  打印加工贸易备案资料变更申请表");
		//this.cbbPrintParam.setMaximumRowCount(8);
		//this.cbbPrintParam.setUI(new CustomBaseComboBoxUI(180));
		//cbbPrintParam.setSelectedIndex(-1);
		// 初始化主管海关
		this.cbbDeclareCustoms.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbDeclareCustoms.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbDeclareCustoms);
		this.cbbDeclareCustoms.setSelectedItem(null);
		// 初始化贸易方式
		this.cbbTrade.setModel(CustomBaseModel.getInstance().getTradeModel());
		this.cbbTrade.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTrade);
		this.cbbTrade.setSelectedItem(null);

		// 初始化征免方式
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.cbbLevyKind
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLevyKind);
		this.cbbLevyKind.setSelectedItem(null);

		// 初始化地区代码
		this.cbbReceiveArea.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		this.cbbReceiveArea.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbReceiveArea);
		this.cbbReceiveArea.setSelectedItem(null);

		// 初始化币别
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurr);
		this.cbbCurr.setSelectedItem(null);

		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfProductRatio,
				7);

		// 初始化加工种类
		this.cbbMachiningType.setModel(CustomBaseModel.getInstance()
				.getMachiningTypeModel());
		this.cbbMachiningType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbMachiningType);
		this.cbbMachiningType.setSelectedItem(null);

		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfProductRatio,
				7);

		// 初始化管理对象
		this.cbbManageObject.removeAllItems();
		this.cbbManageObject.addItem(new ItemProperty(ManageObject.MANAGE_COP,
				"经营单位"));
		this.cbbManageObject.addItem(new ItemProperty(
				ManageObject.MANUFACTURE_COP, "加工单位"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbManageObject);
		this.cbbManageObject.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbManageObject.setSelectedIndex(-1);
		// 限制类标志
		cbbLimitFlag.removeAllItems();
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_BEFORE_EMS,
				"调整前旧手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_AFTER_EMS,
				"调整后新手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ACOUNT_BOOK_USE,
				"台账专用手册"));
		cbbLimitFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLimitFlag);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageObject() {
		if (cbbManageObject == null) {
			cbbManageObject = new JComboBox();
			cbbManageObject.setBounds(new Rectangle(418, 110, 195, 23));
			cbbManageObject.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// int index = cbbManageObject.getSelectedIndex();
					// if (bcsDictPorHead != null && index >= 0) {
					// Company company = (Company) bcsDictPorHead.getCompany();
					// if (index == 0) {
					// tfCode.setText(company.getBuCode());
					// tfName.setText(company.getBuName());
					// } else if (index == 1) {
					// tfCode.setText(company.getCode());
					// tfName.setText(company.getName());
					// }
					// }
				}
			});
		}
		return cbbManageObject;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jScrollPane2.setViewportView(getTaImgRangeSpec());
			jScrollPane2
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u8fdb\u53e3\u6599\u4ef6\u8303\u56f4\uff08\u6587\u5b57\u63cf\u8ff0\uff09",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jScrollPane3.setViewportView(getTaExgRangeSpec());
			jScrollPane3
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u51fa\u53e3\u6210\u54c1\u8303\u56f4\uff08\u6587\u5b57\u63cf\u8ff0\uff09",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaImgRangeSpec() {
		if (taImgRangeSpec == null) {
			taImgRangeSpec = new JTextArea();
			taImgRangeSpec.setLineWrap(true);
		}
		return taImgRangeSpec;
	}

	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaExgRangeSpec() {
		if (taExgRangeSpec == null) {
			taExgRangeSpec = new JTextArea();
			taExgRangeSpec.setLineWrap(true);
		}
		return taExgRangeSpec;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new Rectangle(26, 245, 595, 206));
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJScrollPane2());
			jSplitPane.setBottomComponent(getJScrollPane3());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setPreferredSize(new Dimension(65, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//printReport();
					getPmEdiFunction().show(btnPrint, 0,btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	private JPopupMenu getPmEdiFunction()
	{
		if(pmEdiFunction == null) 
		{
			pmEdiFunction = new JPopupMenu();
			pmEdiFunction.setSize(new Dimension(110, 36));
			pmEdiFunction.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		    pmEdiFunction.add(getBtnPrint1());
			Separator separator = new Separator();
			separator.setForeground(Color.gray);
			/**
			 * 增加分割线
			 */
			pmEdiFunction.add(separator);
			pmEdiFunction.add(getBtnPrintChange());		
			pmEdiFunction.add(getBtnPrintProcedureChange());
			pmEdiFunction.add(getBtnPrintMaterialChange());
			
			
		}
		return pmEdiFunction;
	}
	
	private JMenuItem getBtnPrintChange() 
	{
		    if(btnPrintChange==null)
		    {
		    	btnPrintChange=new JMenuItem();
		    	btnPrintChange.setSize(new Dimension(100, 30));
		    	btnPrintChange.setText("打印加工贸易备案资料变更申请表");
		    	btnPrintChange.addActionListener(new java.awt.event.ActionListener() 
		    	{
					public void actionPerformed(java.awt.event.ActionEvent e) 
					{
						    ChangePrint();
					}
		    	});
		    }
		    return btnPrintChange;
	}
	
	private JMenuItem getBtnPrint1() 
	{
		    if(btnPrint1==null)
		    {
		    	btnPrint1=new JMenuItem();
		    	btnPrint1.setSize(new Dimension(100, 30));
		    	btnPrint1.setText("打印加工贸易备案资料申请表");
		    	btnPrint1.addActionListener(new java.awt.event.ActionListener() 
		    	{
					public void actionPerformed(java.awt.event.ActionEvent e) 
					{
						    printAllEms();
					}
		    	});
		    }
		    return btnPrint1;
	}
	
	/**
	 * 打印报表
	 * 
	 */
	/*private void printReport() 
	{
		if(this.cbbPrintParam.getSelectedItem() == null)
		{
			JOptionPane.showMessageDialog(null, "请选择要打印的项目!!", "提示",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List list = new ArrayList();
		switch (this.cbbPrintParam.getSelectedIndex()) {
		case 0:// 打印加贸易备案资料申请表
			printAllEms();
			break;
		case 1:// 打印加贸晚备案资料变更申请表
			ChangePrint();
			break;
		}
	}*/

	private void ChangePrint() { // 打印备案资料库资料
		try {
			if (bcsDictPorHead == null) {
				return;
			}
//			if (!bcsDictPorHead.getDeclareState().equals(
//					DeclareState.CHANGING_EXE)) {
//				JOptionPane.showMessageDialog(null, "你现在处理正在执行或者初始状态不能打变更资料!!",
//						"提示", JOptionPane.INFORMATION_MESSAGE);
//				return;
//
//			}
			List list = new ArrayList();
			// 查询语句
			list.add(bcsDictPorHead);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = DgBcsDictPor.class
					.getResourceAsStream("report/BcsDictPorChange.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private  void printAllEms() {
		try {
			if (bcsDictPorHead == null) {
				return;
			}
			List list = new ArrayList();
			list.add(bcsDictPorHead);
			String emsNo = "";
			if (bcsDictPorHead.getDictPorEmsNo() != null
					&& !"".equals(bcsDictPorHead.getDictPorEmsNo().trim())) {
				emsNo = bcsDictPorHead.getDictPorEmsNo();
			} else {
				emsNo = bcsDictPorAction.getDictPorHeadReturnTempEmsNo(
						new Request(CommonVars.getCurrUser()), bcsDictPorHead
								.getCopEmsNo());
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("imgCount", bcsDictPorAction
					.findBcsDictPorImgExgCountByHead(new Request(CommonVars
							.getCurrUser()), bcsDictPorHead, true));
			parameters.put("exgCount", bcsDictPorAction
					.findBcsDictPorImgExgCountByHead(new Request(CommonVars
							.getCurrUser()), bcsDictPorHead, false));
			parameters.put("emsNo", emsNo);
			InputStream masterReportStream = DgBcsDictPor.class
					.getResourceAsStream("report/BcsDictPorDeclareReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(new Rectangle(120, 138, 193, 24));
		}
		return cbbLevyKind;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTrade() {
		if (cbbTrade == null) {
			cbbTrade = new JComboBox();
			cbbTrade.setBounds(new Rectangle(418, 137, 195, 24));
		}
		return cbbTrade;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReceiveArea() {
		if (cbbReceiveArea == null) {
			cbbReceiveArea = new JComboBox();
			cbbReceiveArea.setBounds(new Rectangle(120, 165, 193, 24));
		}
		return cbbReceiveArea;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(418, 164, 195, 24));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMachiningType() {
		if (cbbMachiningType == null) {
			cbbMachiningType = new JComboBox();
			cbbMachiningType.setBounds(new Rectangle(120, 191, 193, 24));
		}
		return cbbMachiningType;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("导入");
			jButton.setPreferredSize(new Dimension(65, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBcsDictImport dg = new DgBcsDictImport();
					dg.setHead(bcsDictPorHead);
					dg.setVisible(true);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLimitFlag() {
		if (cbbLimitFlag == null) {
			cbbLimitFlag = new JComboBox();
			cbbLimitFlag.setBounds(new Rectangle(430, 191, 183, 24));
		}
		return cbbLimitFlag;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImgSort() {
		if (btnImgSort == null) {
			btnImgSort = new JButton();
			btnImgSort.setText("料件排序");
			btnImgSort.setPreferredSize(new Dimension(65, 30));
			btnImgSort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImg == null
							|| tableModelImg.getList().size() <= 0) {
						return;
					}
					List list = tableModelImg.getList();
					if (list.size() <= 0) {
						return;
					}
					Vector vet = new Vector();
					list = bcsDictPorAction.findBcsDictPorImgExgByModifyMark(
							new Request(CommonVars.getCurrUser()),
							bcsDictPorHead, ModifyMarkState.ADDED, true);
					int beginSeqNum = bcsDictPorAction
							.findMaxBcsDictPorImgExgSeqNumExceptAdd(
									new Request(CommonVars.getCurrUser()),
									bcsDictPorHead, true) + 1;
					int size = list.size();
					for (int i = 0; i < size; i++) {
						vet.add(list.get(i));
					}
					DgBcsDictPorImgExgSort dg = new DgBcsDictPorImgExgSort();
					dg.setList(vet);
					dg.setBeginSeqNum(beginSeqNum);
					dg.setImg(true);
					dg.setVisible(true);
					if (dg.isOk()) {
						if (bcsDictPorHead != null) {
							initImgTable();
						}
					}
				}
			});
		}
		return btnImgSort;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExgSort() {
		if (btnExgSort == null) {
			btnExgSort = new JButton();
			btnExgSort.setText("成品排序");
			btnExgSort.setPreferredSize(new Dimension(65, 30));
			btnExgSort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg == null
							|| tableModelExg.getList().size() <= 0) {
						return;
					}
					List list = tableModelExg.getList();
					if (list.size() <= 0) {
						return;
					}
					Vector vet = new Vector();
					list = bcsDictPorAction.findBcsDictPorImgExgByModifyMark(
							new Request(CommonVars.getCurrUser()),
							bcsDictPorHead, ModifyMarkState.ADDED, false);
					int beginSeqNum = bcsDictPorAction
							.findMaxBcsDictPorImgExgSeqNumExceptAdd(
									new Request(CommonVars.getCurrUser()),
									bcsDictPorHead, false) + 1;
					int size = list.size();
					for (int i = 0; i < size; i++) {
						vet.add(list.get(i));
					}
					DgBcsDictPorImgExgSort dg = new DgBcsDictPorImgExgSort();
					dg.setList(vet);
					dg.setBeginSeqNum(beginSeqNum);
					dg.setImg(false);
					dg.setVisible(true);
					if (dg.isOk()) {
						if (bcsDictPorHead != null) {
							initExgTable();
						}
					}
				}
			});
		}
		return btnExgSort;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeDeclareState().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeDeclareState;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeDeclareState() {
		if (pmChangeDeclareState == null) {
			pmChangeDeclareState = new JPopupMenu();
			pmChangeDeclareState.add(getMiUndoDeclare());
		}
		return pmChangeDeclareState;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoDeclare() {
		if (miUndoDeclare == null) {
			miUndoDeclare = new JMenuItem();
			miUndoDeclare.setText("取消申报");
			miUndoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String declareState = "";
							if (bcsDictPorHead.getDictPorEmsNo() != null
									&& !"".equals(bcsDictPorHead
											.getDictPorEmsNo().trim())) {
								declareState = DeclareState.CHANGING_EXE;
							} else {
								declareState = DeclareState.APPLY_POR;
							}
							bcsDictPorHead = bcsDictPorAction
									.changeDictPorHeadDeclareState(new Request(
											CommonVars.getCurrUser()),
											bcsDictPorHead, declareState);
							setState();
							if (tableModelHead != null) {
								tableModelHead.updateRow(bcsDictPorHead);
							}
						}
					});
		}
		return miUndoDeclare;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeExgModifyMark() {
		if (btnChangeExgModifyMark == null) {
			btnChangeExgModifyMark = new JButton();
			btnChangeExgModifyMark.setText("改变修改标志");
			btnChangeExgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeExgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeExgModifyMark;
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeModifyMark() {
		if (pmChangeModifyMark == null) {
			pmChangeModifyMark = new JPopupMenu();
			pmChangeModifyMark.add(getMiNotModified());
			pmChangeModifyMark.add(getMiModified());
			pmChangeModifyMark.add(getMiDelete());
			pmChangeModifyMark.add(getMiAdd());
		}
		return pmChangeModifyMark;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotModified() {
		if (miNotModified == null) {
			miNotModified = new JMenuItem();
			miNotModified.setText("未修改");
			miNotModified
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jTabbedPane.getSelectedIndex() == 1) {
								if (tableModelImg.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											DgBcsDictPor.this, "请选择料件", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								List list = tableModelImg.getCurrentRows();
								bcsDictPorAction.changeDictPorImgExgModifyMark(
										new Request(CommonVars.getCurrUser()),
										list, ModifyMarkState.UNCHANGE);
								initImgTable();
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								if (tableModelExg.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											DgBcsDictPor.this, "请选择成品", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								List list = tableModelExg.getCurrentRows();
								bcsDictPorAction.changeDictPorImgExgModifyMark(
										new Request(CommonVars.getCurrUser()),
										list, ModifyMarkState.UNCHANGE);
								initExgTable();
							}
						}
					});
		}
		return miNotModified;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified() {
		if (miModified == null) {
			miModified = new JMenuItem();
			miModified.setText("已修改");
			miModified.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBcsDictPor.this,
									"请选择料件", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelImg.getCurrentRows();
						bcsDictPorAction.changeDictPorImgExgModifyMark(
								new Request(CommonVars.getCurrUser()), list,
								ModifyMarkState.MODIFIED);
						initImgTable();
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBcsDictPor.this,
									"请选择成品", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelExg.getCurrentRows();
						bcsDictPorAction.changeDictPorImgExgModifyMark(
								new Request(CommonVars.getCurrUser()), list,
								ModifyMarkState.MODIFIED);
						initExgTable();
					}
				}
			});
		}
		return miModified;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDelete() {
		if (miDelete == null) {
			miDelete = new JMenuItem();
			miDelete.setText("已删除");
			miDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBcsDictPor.this,
									"请选择料件", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelImg.getCurrentRows();
						bcsDictPorAction.changeDictPorImgExgModifyMark(
								new Request(CommonVars.getCurrUser()), list,
								ModifyMarkState.DELETED);
						initImgTable();
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBcsDictPor.this,
									"请选择成品", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelExg.getCurrentRows();
						bcsDictPorAction.changeDictPorImgExgModifyMark(
								new Request(CommonVars.getCurrUser()), list,
								ModifyMarkState.DELETED);
						initExgTable();
					}
				}
			});
		}
		return miDelete;
	}

	private JMenuItem getMiAdd() {
		if (miAdd == null) {
			miAdd = new JMenuItem();
			miAdd.setText("新  增");
			miAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelImg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBcsDictPor.this,
									"请选择料件", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelImg.getCurrentRows();
						bcsDictPorAction.changeDictPorImgExgModifyMark(
								new Request(CommonVars.getCurrUser()), list,
								ModifyMarkState.ADDED);
						initImgTable();
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						if (tableModelExg.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(DgBcsDictPor.this,
									"请选择成品", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List list = tableModelExg.getCurrentRows();
						bcsDictPorAction.changeDictPorImgExgModifyMark(
								new Request(CommonVars.getCurrUser()), list,
								ModifyMarkState.ADDED);
						initExgTable();
					}
				}
			});
		}
		return miAdd;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeImgModifyMark() {
		if (btnChangeImgModifyMark == null) {
			btnChangeImgModifyMark = new JButton();
			btnChangeImgModifyMark.setText("改变修改标志");
			btnChangeImgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeImgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeImgModifyMark;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
		}
		return jMenuItem;
	}

	/**
	 * This method initializes btnPrintProcedureChange	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getBtnPrintProcedureChange() {
		if (btnPrintProcedureChange == null) {
			btnPrintProcedureChange = new JMenuItem();
			btnPrintProcedureChange.setText("打印成品变更表");
			btnPrintProcedureChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					procudureChangePrint();
				}
				
			});
		}
		return btnPrintProcedureChange;
	}

	/**
	 * This method initializes btnPrintMaterialChange	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getBtnPrintMaterialChange() {
		if (btnPrintMaterialChange == null) {
			btnPrintMaterialChange = new JMenuItem();
			btnPrintMaterialChange.setText("打印料件变更表");
			btnPrintMaterialChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					materialChangePrint();
				}				
			});
		}
		return btnPrintMaterialChange;
	}
	
	/**
	 * 打印成品变更表
	 */
	private void procudureChangePrint() {
		// TODO Auto-generated method stub
		try {
			if (bcsDictPorHead == null) {
				return;
			}
//			if (!bcsDictPorHead.getDeclareState().equals(
//					DeclareState.CHANGING_EXE)) {
//				JOptionPane.showMessageDialog(null, "你现在处理正在执行或者初始状态不能打变更资料!!",
//						"提示", JOptionPane.INFORMATION_MESSAGE);
//				return;
//
//			}
			List list = this.bcsDictPorAction.findBcsDictPorExgStateChanged(new Request(
					CommonVars.getCurrUser()), bcsDictPorHead);	
			if(list==null || list.size()==0){
				JOptionPane.showMessageDialog(DgBcsDictPor.this, "成品没有变更!");
				return;
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = DgBcsDictPor.class
					.getResourceAsStream("report/BcsProcedureChange.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("companyName", bcsDictPorHead.getMachName());
			if(bcsDictPorHead.getCurr()!=null){
			  parameters.put("currName", bcsDictPorHead.getCurr().getName());
			}else{
				parameters.put("currName", "");
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 打印料件变更表
	 */
	private void materialChangePrint() {
		// TODO Auto-generated method stub
		try {
			if (bcsDictPorHead == null) {
				return;
			}
//			if (!bcsDictPorHead.getDeclareState().equals(
//					DeclareState.CHANGING_EXE)) {
//				JOptionPane.showMessageDialog(null, "你现在处理正在执行或者初始状态不能打变更资料!!",
//						"提示", JOptionPane.INFORMATION_MESSAGE);
//				return;
//
//			}
			List list = this.bcsDictPorAction.findBcsDictPorImgStateChanged(new Request(
					CommonVars.getCurrUser()), bcsDictPorHead);	
			if(list==null || list.size()==0){
				JOptionPane.showMessageDialog(DgBcsDictPor.this, "料件没有变更!");
				return;
			}
			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = DgBcsDictPor.class
					.getResourceAsStream("report/BcsMaterialChange.jasper");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("companyName", bcsDictPorHead.getMachName());
			if(bcsDictPorHead.getCurr()!=null){
			  parameters.put("currName", bcsDictPorHead.getCurr().getName());
			}else{
				parameters.put("currName", "");
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
