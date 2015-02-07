/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JToggleButton;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgAtcMergeCommInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfMaterielCode = null;

	private JTextField tfComplexName = null;

	private JTextField tfUnit = null;

	private JTextField tfSecondLegalUnit = null;

	private JComboBox cbbUses = null;

	private JTextField tfMemo = null;

	private JTextField tfEmsSerialNo = null;

	private JTextField tfComplexCode = null;

	private JTextField tfComplexSpec = null;

	private JTextField tfLegalUnit = null;

	private JComboBox cbbCurrency = null;

	private JComboBox cbbLevyModel = null;

	private JFormattedTextField tfNetWeight = null;

	private JFormattedTextField tfDeclaredAmount = null;

	private JFormattedTextField tfSecondLegalAmount = null;

	private JFormattedTextField tfGrossWeight = null;

	private JFormattedTextField tfDeclaredPrice = null;

	private JFormattedTextField tfLegalAmount = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private DefaultFormatterFactory fourPointdefaultFormatterFactory = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private NumberFormatter fourPointNumberFormatter = null; // @jve:decl-index=0:

	private JTableListModel tableModel;

	private AtcMergeBeforeComInfo beforeCommInfo = null; // @jve:decl-index=0:

	private EncAction encAction = null;

	private boolean isOk = false;

	private boolean isMakeCustomsDeclaration = false;

	private JLabel jLabel29 = null;

	private JTextField jTextField = null;

	private JLabel jLabel30 = null;

	private JTextField jTextField1 = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel32 = null;

	private JCustomFormattedTextField tfUnitWeight = null;

	private JComboBox jComboBox = null;

	private JFormattedTextField tfTotalPrice = null;

	private JLabel jLabel33 = null;

	private JComboBox jComboBox1 = null;

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JLabel jLabel34 = null;

	private JLabel jLabel35 = null;

	private JTextField jTextField2 = null;

	private JButton btnPrevious = null;// 查看上一笔记录

	private JButton btnNext = null;// 查看下一笔记录

	private JLabel jLabel36 = null;

	private JCustomFormattedTextField tfPeice = null;

	private JLabel jLabel37 = null;

	private JLabel jLabel371 = null;

	private JCustomFormattedTextField tfStoreCount = null;

	private JComboBox cbVersion = null;

	private List versions = null; // @jve:decl-index=0:

	private JLabel jLabel38 = null;

	private JTextField tfVersion = null;

	private JLabel jLabel381 = null;

	private JTextField tfBoxNo = null;

	private JLabel jLabel231 = null;

	private JCustomFormattedTextField tfWordUsd = null;

	public JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (beforeCommInfo == null) {
						return;
					}
					if (checkRerictCommodity()) {
						return;
					}
					if (!checkData()) {
						return;
					}
					// fillData(beforeCommInfo);

					saveData();

					tableModel.updateRow(beforeCommInfo);
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}

					beforeCommInfo = (AtcMergeBeforeComInfo) tableModel
							.getCurrentRow();

					showData(beforeCommInfo);

					setState();
				}
			});
		}
		return btnPrevious;
	}

	public JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下笔");
			if (tableModel != null
					&& tableModel.getCurrRowCount() >= tableModel.getRowCount() - 1) {
				btnNext.setEnabled(false);
				btnPrevious.setEnabled(true);
			}// TODO
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (beforeCommInfo == null) {
						return;
					}
					if (checkRerictCommodity()) {
						return;
					}
					if (!checkData()) {
						return;
					}

					// fillData(beforeCommInfo);

					saveData();

					tableModel.updateRow(beforeCommInfo);
					// if (tableModel.getRowCount() >
					// tableModel.getCurrRowCount()) {
					if (!tableModel.nextRow()
							|| tableModel.getCurrRowCount() >= (tableModel
									.getRowCount() - 1)) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}

					beforeCommInfo = (AtcMergeBeforeComInfo) tableModel
							.getCurrentRow();

					showData(beforeCommInfo);

					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * @return Returns the isMakeCustomsDeclaration.
	 */
	public boolean isMakeCustomsDeclaration() {
		return isMakeCustomsDeclaration;
	}

	/**
	 * @param isMakeCustomsDeclaration
	 *            The isMakeCustomsDeclaration to set.
	 */
	public void setMakeCustomsDeclaration(boolean isMakeCustomsDeclaration) {
		this.isMakeCustomsDeclaration = isMakeCustomsDeclaration;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
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

	private Map<Integer, String> h2kLegalUnitGeneMap = null; // @jve:decl-index=0:

	private JLabel jLabel39 = null;

	private JTextField tfCmpVersion = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgAtcMergeCommInfo(JTableListModel tableModel) {
		super();
		this.tableModel = tableModel;
		beforeCommInfo = (AtcMergeBeforeComInfo) tableModel.getCurrentRow();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		initUIComponents();
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgAtcMergeCommInfo() {
		super();
		initialize();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initUIComponents();

	}

	public Map getUnitRateMap() {
		Map unitRateMap = encAction.findAllUnitRateMap(new Request(CommonVars
				.getCurrUser()));
		return unitRateMap;
	}

	public Map getH2kLegalUnitGeneMap() {
		String materielType = beforeCommInfo.getMateriel().getScmCoi()
				.getCoiProperty();
		String emsNO = beforeCommInfo.getAfterComInfo().getBillList()
				.getEmsHeadH2k();
		h2kLegalUnitGeneMap = encAction.findImgExgBillBySeqNum(new Request(
				CommonVars.getCurrUser()), beforeCommInfo.getAfterComInfo().getEmsSerialNo(),
				materielType, emsNO);
		return h2kLegalUnitGeneMap;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关清单商品信息编辑");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(654, 512);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
					beforeCommInfo = (AtcMergeBeforeComInfo) tableModel
							.getCurrentRow();
					showData(beforeCommInfo);
					setState();
				}
			}
		});
	}

	private void setState() {
		this.tfDeclaredAmount.setEditable(!this.isMakeCustomsDeclaration);
		this.tfDeclaredPrice.setEditable(!this.isMakeCustomsDeclaration);
		jTextField1.setEditable(!this.isMakeCustomsDeclaration);
		this.tfLegalAmount.setEditable(!this.isMakeCustomsDeclaration);
		this.tfSecondLegalAmount.setEditable(!this.isMakeCustomsDeclaration
				&& !tfSecondLegalUnit.getText().equals(""));
		this.tfNetWeight.setEditable(!this.isMakeCustomsDeclaration);
		this.tfGrossWeight.setEditable(!this.isMakeCustomsDeclaration);
		this.tfMemo.setEditable(!this.isMakeCustomsDeclaration);
		this.cbbCurrency.setEnabled(!this.isMakeCustomsDeclaration);
		this.cbbLevyModel.setEnabled(!this.isMakeCustomsDeclaration);
		this.cbbUses.setEnabled(!this.isMakeCustomsDeclaration);
		jComboBox.setEnabled(!this.isMakeCustomsDeclaration);
		jComboBox1.setEnabled(!this.isMakeCustomsDeclaration);// 事业部
		this.btnSave.setEnabled(!this.isMakeCustomsDeclaration);
		jTextField2.setEditable(!this.isMakeCustomsDeclaration);// 扩展备注栏

		if (beforeCommInfo.getMateriel().getScmCoi().getCoiProperty().equals(
				MaterielType.FINISHED_PRODUCT)) {
			tfVersion.setEditable(!this.isMakeCustomsDeclaration);
			tfCmpVersion.setEditable(!this.isMakeCustomsDeclaration);
		} else {
			this.tfVersion.setEditable(false);
			this.jLabel38.setEnabled(false);
			this.tfCmpVersion.setEditable(false);
			this.jLabel39.setEnabled(false);
		}
		this.tfPeice.setEditable(!this.isMakeCustomsDeclaration);// 件数
		this.tfTotalPrice.setEditable(!this.isMakeCustomsDeclaration);// 总价
		// this.tfWordUsd.setEditable(!this.isMakeCustomsDeclaration);// 加工费总价
		if (beforeCommInfo.getMateriel().getScmCoi().getCoiProperty().equals(
				MaterielType.FINISHED_PRODUCT)) {
			tfWordUsd.setEditable(!this.isMakeCustomsDeclaration);
		} else {
			this.tfWordUsd.setEditable(false);
			this.jLabel231.setEnabled(false);
		}
		this.tfBoxNo.setEditable(!this.isMakeCustomsDeclaration);// 箱号

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			jContentPane.setLayout(new BorderLayout());
			jLabel.setText("JLabel");
			jLabel1.setText("JLabel");
			jLabel2.setText("JLabel");
			jLabel3.setText("JLabel");
			jLabel4.setText("JLabel");
			jLabel5.setText("JLabel");
			jLabel6.setText("JLabel");
			jLabel7.setText("JLabel");
			jLabel8.setText("JLabel");
			jLabel9.setText("JLabel");
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel39 = new JLabel();
			jLabel39.setBounds(new Rectangle(505, 313, 50, 18));
			jLabel39.setText("企业版本");
			jLabel231 = new JLabel();
			jLabel231.setBounds(new Rectangle(59, 229, 89, 22));
			jLabel231.setText("加工费总价");
			jLabel381 = new JLabel();
			jLabel381.setBounds(new Rectangle(59, 342, 89, 22));
			jLabel381.setText("箱号 ");
			jLabel38 = new JLabel();
			jLabel38.setBounds(new Rectangle(356, 311, 89, 22));
			jLabel38.setForeground(java.awt.Color.blue);
			jLabel38.setText("版本");
			jLabel371 = new JLabel();
			jLabel371.setBounds(new Rectangle(356, 418, 90, 19));
			jLabel371.setText("仓库数量");
			jLabel371.setVisible(false);
			jLabel37 = new JLabel();
			jLabel37.setBounds(new Rectangle(59, 416, 90, 22));
			jLabel37.setText("版本");
			jLabel37.setVisible(false);
			jLabel36 = new JLabel();
			jLabel36.setBounds(new Rectangle(59, 313, 90, 18));
			jLabel36.setForeground(java.awt.Color.blue);
			jLabel36.setText("件数");
			jLabel35 = new JLabel();
			jLabel35.setBounds(new Rectangle(59, 389, 89, 22));
			jLabel35.setText("扩展备注栏");
			jLabel34 = new JLabel();
			jLabel34.setForeground(java.awt.Color.red);
			jLabel34.setBounds(new Rectangle(459, 2, 156, 18));
			jLabel34.setText("注：蓝色字体为海关申报栏位");
			jLabel33 = new JLabel();
			jLabel33.setBounds(new Rectangle(355, 346, 90, 18));
			jLabel33.setText("事业部");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(236, 419, 32, 19));
			jLabel32.setForeground(java.awt.Color.blue);
			jLabel32.setText("单重");
			jLabel32.setVisible(false);
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(355, 203, 90, 18));
			jLabel31.setForeground(java.awt.Color.blue);
			jLabel31.setText("总价");
			jLabel30 = new JLabel();
			jLabel30.setBounds(new java.awt.Rectangle(355, 46, 98, 23));
			jLabel30.setForeground(java.awt.Color.blue);
			jLabel30.setText("对应报关单商品号");
			jLabel29 = new JLabel();
			jLabel29.setBounds(new java.awt.Rectangle(59, 21, 86, 24));
			jLabel29.setForeground(java.awt.Color.blue);
			jLabel29.setText("商品序号");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel19 = new JLabel();
			javax.swing.JLabel jLabel20 = new JLabel();
			javax.swing.JLabel jLabel21 = new JLabel();
			javax.swing.JLabel jLabel22 = new JLabel();
			javax.swing.JLabel jLabel23 = new JLabel();
			javax.swing.JLabel jLabel24 = new JLabel();
			javax.swing.JLabel jLabel25 = new JLabel();
			javax.swing.JLabel jLabel26 = new JLabel();
			javax.swing.JLabel jLabel27 = new JLabel();
			javax.swing.JLabel jLabel28 = new JLabel();
			jPanel.setLayout(null);
			jLabel10.setBounds(59, 46, 90, 18);
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel10.setText("商品货号");
			jLabel11.setBounds(355, 73, 90, 21);
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel11.setText("商品名称");
			jLabel12.setBounds(59, 126, 105, 21);
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel12.setText("计量单位(归并后)");
			jLabel13.setBounds(355, 126, 90, 21);
			jLabel13.setForeground(java.awt.Color.blue);
			jLabel13.setText("申报数量");
			jLabel14.setBounds(59, 176, 90, 22);
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel14.setText("第二法定单位");
			jLabel15.setBounds(355, 176, 90, 18);
			jLabel15.setForeground(java.awt.Color.blue);
			jLabel15.setText("第二法定数量");
			jLabel16.setBounds(355, 233, 90, 18);
			jLabel16.setForeground(java.awt.Color.blue);
			jLabel16.setText("产销国（地区）");
			jLabel17.setBounds(59, 259, 89, 22);
			jLabel17.setForeground(java.awt.Color.blue);
			jLabel17.setText("用途代码");
			jLabel18.setBounds(355, 289, 90, 18);
			jLabel18.setForeground(java.awt.Color.blue);
			jLabel18.setText("毛重(可选)");
			jLabel19.setBounds(59, 362, 89, 22);
			jLabel19.setText("备注");
			jLabel20.setBounds(355, 21, 89, 22);
			jLabel20.setText("帐册序号");
			jLabel20.setForeground(java.awt.Color.blue);
			jLabel21.setBounds(59, 73, 89, 22);
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel21.setText("商品编码");
			jLabel22.setBounds(355, 100, 89, 22);
			jLabel22.setForeground(java.awt.Color.blue);
			jLabel22.setText("商品规格");
			jLabel23.setBounds(59, 203, 89, 22);
			jLabel23.setForeground(java.awt.Color.blue);
			jLabel23.setText("企业申报单价");
			jLabel24.setBounds(59, 150, 89, 22);
			jLabel24.setForeground(java.awt.Color.blue);
			jLabel24.setText("法定计量单位");
			jLabel25.setBounds(355, 150, 89, 22);
			jLabel25.setForeground(java.awt.Color.blue);
			jLabel25.setText("法定数量");
			jLabel26.setBounds(59, 100, 89, 22);
			jLabel26.setForeground(java.awt.Color.blue);
			jLabel26.setText("币制");
			jLabel27.setBounds(355, 263, 90, 18);
			jLabel27.setForeground(java.awt.Color.blue);
			jLabel27.setText("征免方式");
			jLabel28.setBounds(59, 285, 89, 22);
			jLabel28.setForeground(java.awt.Color.blue);
			jLabel28.setText("净重(可选)");
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel16, null);
			jPanel.add(jLabel17, null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(jLabel20, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(jLabel23, null);
			jPanel.add(jLabel24, null);
			jPanel.add(jLabel25, null);
			jPanel.add(jLabel26, null);
			jPanel.add(jLabel27, null);
			jPanel.add(jLabel28, null);
			jPanel.add(getTfMaterielCode(), null);
			jPanel.add(getTfComplexName(), null);
			jPanel.add(getTfUnit(), null);
			jPanel.add(getTfSecondLegalUnit(), null);
			jPanel.add(getCbbUses(), null);
			jPanel.add(getTfMemo(), null);
			jPanel.add(getTfEmsSerialNo(), null);
			jPanel.add(getTfComplexCode(), null);
			jPanel.add(getTfComplexSpec(), null);
			jPanel.add(getTfLegalUnit(), null);
			jPanel.add(getCbbCurrency(), null);
			jPanel.add(getCbbLevyModel(), null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(getTfDeclaredAmount(), null);
			jPanel.add(getTfSecondLegalAmount(), null);
			jPanel.add(getTfGrossWeight(), null);
			jPanel.add(getTfDeclaredPrice(), null);
			jPanel.add(getTfLegalAmount(), null);
			jPanel.add(jLabel29, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel30, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(jLabel31, null);
			jPanel.add(jLabel32, null);
			jPanel.add(getTfUnitWeight(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getTfTotalPrice(), null);
			jPanel.add(jLabel33, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(jLabel35, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel36, null);
			jPanel.add(getTfPeice(), null);
			jPanel.add(jLabel37, null);
			jPanel.add(jLabel371, null);
			jPanel.add(getTfStoreCount(), null);
			jPanel.add(getCbVersion(), null);
			jPanel.add(jLabel38, null);
			jPanel.add(getTfVersion(), null);
			jPanel.add(jLabel381, null);
			jPanel.add(getTfBoxNo(), null);
			jPanel.add(jLabel231, null);
			jPanel.add(getTfWordUsd(), null);
			jPanel.add(jLabel34, null);
			jPanel.add(jLabel39, null);
			jPanel.add(getTfCmpVersion(), null);
		}
		return jPanel;
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
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnPrevious(), null);
			jToolBar.add(getBtnNext(), null);
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
					if (beforeCommInfo != null) {
						if (!checkData()) {
							return;
						}
						if (checkRerictCommodity()) {
							return;
						}
						saveData();
					}
					setOk(true);
					dispose();
				}
			});
		}
		return btnSave;
	}

	// 检查是不是限制类商品并且是否超出了限制类商品中定义的数量
	public boolean checkRerictCommodity() {
		// double aamount = 0;
		// double Amount =0;
		// Boolean isMaterial = EncCommon.isMaterial(beforeCommInfo
		// .getAfterComInfo().getBillList().getImpExpType());
		//		
		// Integer yy=EncCommon.getMaterielTypeByBillType(beforeCommInfo
		// .getAfterComInfo().getBillList().getImpExpType());
		//
		// RestirictCommodity commodity = materialManageAction
		// .findRerictCommodity(new Request(CommonVars.getCurrUser()),
		// isMaterial, tfEmsSerialNo.getText().toString());
		//
		// // 得到清单中的数量
		// Double DeclarationCommInfo = materialManageAction
		// .findCustomsBeforeComInfo(
		// new Request(CommonVars.getCurrUser()), isMaterial,
		// tfEmsSerialNo.getText().toString(),yy.toString());
		//		
		// Amount=DeclarationCommInfo+Double.valueOf(tfDeclaredAmount.getText().toString());
		// if (commodity != null) {
		// if (commodity.getAmount() != null
		// && !commodity.getAmount().equals("")) {
		// aamount = Double.parseDouble(commodity.getAmount().toString());
		// if (Amount > aamount) {
		// JOptionPane.showMessageDialog(this, "备案号："
		// + tfEmsSerialNo.getText() + "已进(出)数量[" + Amount
		// + "] 超出了限制类商品中备的数量[" + aamount + "]!\n", "提示", 0);
		// tfDeclaredAmount.requestFocus();
		// return true;
		// }
		// }
		// }

		return false;
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
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfMaterielCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielCode() {
		if (tfMaterielCode == null) {
			tfMaterielCode = new JTextField();
			tfMaterielCode.setBounds(167, 46, 167, 22);
			tfMaterielCode.setEditable(false);
		}
		return tfMaterielCode;
	}

	/**
	 * This method initializes tfComplexName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexName() {
		if (tfComplexName == null) {
			tfComplexName = new JTextField();
			tfComplexName.setBounds(447, 73, 167, 22);
			tfComplexName.setEditable(false);
		}
		return tfComplexName;
	}

	/**
	 * This method initializes tfUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(167, 126, 167, 22);
			tfUnit.setEditable(false);
		}
		return tfUnit;
	}

	/**
	 * This method initializes tfSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSecondLegalUnit() {
		if (tfSecondLegalUnit == null) {
			tfSecondLegalUnit = new JTextField();
			tfSecondLegalUnit.setBounds(167, 176, 167, 22);
			tfSecondLegalUnit.setEditable(false);
		}
		return tfSecondLegalUnit;
	}

	/**
	 * This method initializes cbbUses
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUses() {
		if (cbbUses == null) {
			cbbUses = new JComboBox();
			cbbUses.setBounds(167, 259, 167, 22);
		}
		return cbbUses;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(167, 366, 450, 22);
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfEmsSerialNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsSerialNo() {
		if (tfEmsSerialNo == null) {
			tfEmsSerialNo = new JTextField();
			tfEmsSerialNo.setBounds(447, 21, 167, 22);
			tfEmsSerialNo.setEditable(false);
		}
		return tfEmsSerialNo;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(167, 73, 167, 22);
			tfComplexCode.setEditable(false);
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes tfComplexSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexSpec() {
		if (tfComplexSpec == null) {
			tfComplexSpec = new JTextField();
			tfComplexSpec.setBounds(447, 100, 167, 22);
			tfComplexSpec.setEditable(false);
		}
		return tfComplexSpec;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(167, 150, 167, 22);
			tfLegalUnit.setEditable(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes cbbCurrency
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(167, 100, 167, 22);
		}
		return cbbCurrency;
	}

	/**
	 * This method initializes cbbLevyModel
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyModel() {
		if (cbbLevyModel == null) {
			cbbLevyModel = new JComboBox();
			cbbLevyModel.setBounds(447, 259, 167, 22);
		}
		return cbbLevyModel;
	}

	/**
	 * This method initializes tfNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(167, 285, 167, 22);
			tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes tfDeclaredAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDeclaredAmount() {
		if (tfDeclaredAmount == null) {
			tfDeclaredAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeclaredAmount.setBounds(447, 126, 167, 22);
			tfDeclaredAmount
					.setHorizontalAlignment(javax.swing.JTextField.LEFT);
			tfDeclaredAmount.setFormatterFactory(getDefaultFormatterFactory());
			tfDeclaredAmount.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							getFirstAmount();
						}

					});
		}
		return tfDeclaredAmount;
	}

	// 计算第一法定数量、第二法定数量:
	// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
	// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
	// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
	// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
	protected void getFirstAmount() {
		double amount = 0;
		double legalUnitGene = 0.0;
		double legalUnit2Gene = 0.0;

		if (getH2kLegalUnitGeneMap().get(beforeCommInfo.getSerialNo()) != null) {
			String legalUnitGeneMap = getH2kLegalUnitGeneMap().get(
					beforeCommInfo.getSerialNo()).toString();
			String[] str = legalUnitGeneMap.split("/");
			legalUnitGene = Double.valueOf(str[0]);
			legalUnit2Gene = Double.valueOf(str[1]);
		}

		if (this.tfDeclaredAmount.getValue() != null
				&& !this.tfDeclaredAmount.getValue().equals("")) {
			amount = Double.parseDouble(this.tfDeclaredAmount.getText()
					.toString());
		}
		String unit = tfUnit.getText();
		String legalUnit = tfLegalUnit.getText();
		String secondUnit = tfSecondLegalUnit.getText();
		if (unit.equals(legalUnit)) {
			tfLegalAmount.setValue(Double.valueOf(amount));
		} else if (getUnitRateMap().get(unit + "+" + legalUnit) != null) {// 条件3
			Double unitRate = Double.parseDouble(getUnitRateMap().get(
					unit + "+" + legalUnit).toString());
			tfLegalAmount
					.setValue(amount * (unitRate == null ? 0.0 : unitRate));
		} else {// 条件4
			tfLegalAmount.setValue(amount * legalUnitGene);
		}

		if (unit.equals(secondUnit)) {
			tfSecondLegalAmount.setValue(Double.valueOf(amount));
		} else if (getUnitRateMap().get(unit + "+" + secondUnit) != null) {// 条件3
			Double unitRate = Double.parseDouble(getUnitRateMap().get(
					unit + "+" + secondUnit).toString());
			tfSecondLegalAmount.setValue(amount
					* (unitRate == null ? 0.0 : unitRate));
		} else {// 条件4
			tfSecondLegalAmount.setValue(amount * legalUnit2Gene);
		}
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfSecondLegalAmount() {
		if (tfSecondLegalAmount == null) {
			tfSecondLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSecondLegalAmount.setBounds(447, 176, 167, 22);
			tfSecondLegalAmount
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfSecondLegalAmount;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(447, 285, 167, 22);
			tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes tfDeclaredPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDeclaredPrice() {
		if (tfDeclaredPrice == null) {
			tfDeclaredPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeclaredPrice.setBounds(167, 203, 167, 22);
			// tfDeclaredPrice
			// .setFormatterFactory(getFourPointdefaultFormatterFactory());
			String billPrice = manualDecleareAction.getBpara(new Request(
					CommonVars.getCurrUser()),
					BcusParameter.BILL_TO_CONTROL_PRICE);
			if (billPrice == null)
				billPrice = "3";
			int digitNum = Integer.valueOf(billPrice);
			System.out.println("price=" + digitNum);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfDeclaredPrice,
					digitNum);
			// tfDeclaredPrice
			// .addCaretListener(new javax.swing.event.CaretListener() {
			// public void caretUpdate(javax.swing.event.CaretEvent e) {
			// if (tfDeclaredAmount.getValue() != null
			// && tfDeclaredPrice.getValue() != null) {
			// jFormattedTextField.setValue(Double
			// .valueOf(tfDeclaredAmount.getValue()
			// .toString())
			// * Double.valueOf(tfDeclaredPrice
			// .getValue().toString()));
			// }
			// }
			// });
		}
		return tfDeclaredPrice;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setBounds(447, 150, 167, 22);
			tfLegalAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getFourPointdefaultFormatterFactory() {
		if (fourPointdefaultFormatterFactory == null) {
			fourPointdefaultFormatterFactory = new DefaultFormatterFactory();
			fourPointdefaultFormatterFactory
					.setDisplayFormatter(getFourPointNumberFormatter());
			fourPointdefaultFormatterFactory
					.setEditFormatter(getFourPointNumberFormatter());
			// fourPointdefaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			// fourPointdefaultFormatterFactory.setEditFormatter(getNumberFormatter());
			// fourPointdefaultFormatterFactory.setNullFormatter(getNumberFormatter());
			// fourPointdefaultFormatterFactory.setDisplayFormatter(getNumberFormatter());

		}
		return fourPointdefaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getFourPointNumberFormatter() {
		if (fourPointNumberFormatter == null) {
			DecimalFormat decimalFormat = new DecimalFormat("#.####");
			fourPointNumberFormatter = new NumberFormatter(decimalFormat);
		}
		return fourPointNumberFormatter;
	}

	/**
	 * 将窗口控件上的数据付值到data实体上，以用来保存
	 * 
	 * @param data
	 */
	private void fillData(AtcMergeBeforeComInfo data) {
		if (this.tfDeclaredAmount.getValue() != null) {
			data.setDeclaredAmount(Double.valueOf(this.tfDeclaredAmount
					.getValue().toString()));
		} else {
			data.setDeclaredAmount(null);
		}
		if (this.tfDeclaredPrice.getValue() != null) {
			data.setDeclaredPrice(Double
					.valueOf(this.tfDeclaredPrice.getText()));
		} else {
			data.setDeclaredPrice(null);
		}
		if (this.tfLegalAmount.getValue() != null) {
			data.setLegalAmount(Double.valueOf(this.tfLegalAmount.getValue()
					.toString()));
		} else {
			data.setLegalAmount(null);
		}
		if (this.tfSecondLegalAmount.getValue() != null) {
			data.setSecondLegalAmount(Double.valueOf(this.tfSecondLegalAmount
					.getValue().toString()));
		} else {
			data.setSecondLegalAmount(null);
		}
		if (this.tfGrossWeight.getValue() != null) {
			data.setGrossWeight(Double.valueOf(this.tfGrossWeight.getValue()
					.toString()));
		} else {
			data.setGrossWeight(null);
		}
		if (this.tfNetWeight.getValue() != null) {
			data.setNetWeight(Double.valueOf(this.tfNetWeight.getValue()
					.toString()));
		} else {
			data.setNetWeight(null);
		}

		// 件数
		if (!"".equals(tfPeice.getValue()) && this.tfPeice.getValue() != null) {
			BigDecimal b = new BigDecimal(this.tfPeice.getValue().toString());
			data.setPiece(Integer.valueOf(b.setScale(0,
					BigDecimal.ROUND_HALF_UP).intValue()));
		} else {
			data.setPiece(null);
		}

		data.setBoxNo(tfBoxNo.getText());// 箱号
		data.setExtendMemo(jTextField2.getText());// 扩展备注

		if (this.cbbCurrency.getSelectedItem() != null) {
			data.setCurrency((Curr) this.cbbCurrency.getSelectedItem());
		} else {
			data.setCurrency(null);
		}
		if (this.cbbUses.getSelectedItem() != null) {
			data.setUsesCode((Uses) this.cbbUses.getSelectedItem());
		} else {
			data.setUsesCode(null);
		}
		if (this.jComboBox.getSelectedItem() != null) {
			data.setSalesCountry((Country) this.jComboBox.getSelectedItem());
		} else {
			data.setSalesCountry(null);
		}
		if (this.cbbLevyModel.getSelectedItem() != null) {
			data.setLevyMode((LevyMode) this.cbbLevyModel.getSelectedItem());
		} else {
			data.setLevyMode(null);
		}
		// 事业部
		if (this.jComboBox1.getSelectedItem() != null) {
			data
					.setProjectDept((ProjectDept) this.jComboBox1
							.getSelectedItem());
		} else {
			data.setProjectDept(null);
		}
		data.setCustomsNo(this.jTextField1.getText());
		// 单重
		if (this.tfUnitWeight.getValue() != null
				&& !this.tfUnitWeight.getValue().equals("")) {
			data.setStoreAmount(Double.parseDouble(this.tfStoreCount.getValue()
					.toString()));
		}
		// 版本
		Object o = this.tfVersion.getText().trim();
		Integer version = null;
		if (o != null && !"".equals(o.toString())) {
			try {
				version = Integer.parseInt(o.toString());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "输入的版本号应为整数！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		data.setVersion(version);
		//企业版本
		data.setCmpVersion(tfCmpVersion.getText().trim());
		// 仓库数量
		if (this.tfStoreCount.getValue() != null
				&& !this.tfStoreCount.getValue().equals("")) {
			data.setStoreAmount(Double.parseDouble(this.tfStoreCount.getValue()
					.toString()));
		}

		/**
		 * 总价
		 */
		if (this.tfTotalPrice.getValue() != null) {
			data.setTotalPrice(Double.valueOf(this.tfTotalPrice.getText()
					.toString()));
		} else {
			data.setTotalPrice(0.0);
		}
		/**
		 * 加工费总价
		 */
		if (this.tfWordUsd.getValue() != null) {
			data
					.setWorkUsd(Double.valueOf(this.tfWordUsd.getText()
							.toString()));
		} else {
			data.setTotalPrice(0.0);
		}
		data.setMemos(tfMemo.getText());
	}

	/**
	 * 将实体内的数据显示到窗口上
	 * 
	 * @param data
	 */
	private void showData(AtcMergeBeforeComInfo data) {

		this.tfMaterielCode.setText(data.getMateriel().getPtNo());// 商品货号
		jTextField.setText(String.valueOf(data.getSerialNo()));// 商品序号
		if (data.getAfterComInfo() != null) {
			this.tfEmsSerialNo.setText(String.valueOf(data.getAfterComInfo()
					.getEmsSerialNo()));// 帐册序号
			this.tfComplexCode.setText(data.getAfterComInfo().getComplex()
					.getCode());// 商品编码
			this.tfComplexName.setText(data.getMateriel().getFactoryName());// 商品名称
			this.tfComplexSpec.setText(data.getMateriel().getFactorySpec());// 商品规格
			if (data.getAfterComInfo().getUnit() != null) {
				this.tfUnit.setText(data.getAfterComInfo().getUnit().getName());
			}
			if (data.getAfterComInfo().getLegalUnit() != null) {
				this.tfLegalUnit.setText((data.getAfterComInfo().getLegalUnit()
						.getName()));
			}
			this.tfSecondLegalUnit.setText(data.getAfterComInfo()
					.getSecondLegalUnit() == null ? "" : (data
					.getAfterComInfo().getSecondLegalUnit().getName()));
		}
		this.tfDeclaredAmount.setValue(data.getDeclaredAmount());
		this.tfDeclaredPrice.setValue(data.getDeclaredPrice());
		this.tfTotalPrice.setValue(data.getTotalPrice());
		this.tfWordUsd.setValue(data.getWorkUsd());
		this.tfSecondLegalAmount.setValue(data.getSecondLegalAmount());
		this.tfLegalAmount.setValue(data.getLegalAmount());
		if (data.getSalesCountry() != null) {
			this.jComboBox.setSelectedItem(data.getSalesCountry());
		}
		if (data.getCurrency() != null) {
			this.cbbCurrency.setSelectedItem(data.getCurrency());
		}
		if (data.getUsesCode() != null) {
			this.cbbUses.setSelectedItem(data.getUsesCode());
		}
		if (data.getLevyMode() != null) {
			this.cbbLevyModel.setSelectedItem(data.getLevyMode());
		}
		// 事业部
		if (data.getProjectDept() != null) {
			this.jComboBox1.setSelectedItem(data.getProjectDept());
		}
		tfUnitWeight.setValue(data.getUnitWeight());
		tfStoreCount.setValue(data.getStoreAmount());
		// 版本
		tfVersion.setText(data.getVersion() == null ? null : String
				.valueOf(data.getVersion()));
		tfCmpVersion.setText(data.getCmpVersion());
		Integer index = null;
		EmsEdiMergerVersion temp = null;
		for (int i = 0; i < versions.size(); i++) {
			if (data.getVersion() == null) {
				break;
			}
			temp = (EmsEdiMergerVersion) versions.get(i);
			if (data.getVersion().equals(temp.getVersion())) {
				index = i;
				break;
			}
		}
		if (index == null) {
			cbVersion.setSelectedIndex(-1);
		} else {
			cbVersion.setSelectedIndex(index);
		}

		this.tfGrossWeight.setValue(data.getGrossWeight());
		this.tfNetWeight.setValue(data.getNetWeight());
		this.tfMemo.setText(data.getMemos());
		this.jTextField1.setText(data.getCustomsNo());
		jTextField2.setText(data.getExtendMemo());
		this.tfPeice.setValue(data.getPiece()); // 件数
		this.tfBoxNo.setText(data.getBoxNo());// 箱号
	}

	private boolean checkData() {

		// boolean returndata =true;
		// System.out.print("========jj="+this.tfPeice.getValue().toString());
		// if (this.tfPeice.getValue().toString() != null){
		// try {
		// System.out.print("========jj="+this.tfPeice.getValue().toString());
		// Integer jj= Integer.parseInt(this.tfPeice.getValue().toString());
		//				
		// } catch (Exception e) {
		// returndata=false;
		// JOptionPane.showMessageDialog(this, "输入的件数应为整数！", "提示", 2);
		// }
		//		
		// }
		return true;
	}

	private void saveData() {
		fillData(beforeCommInfo);
		beforeCommInfo = encAction.saveAtcMergeBeforeComInfo(new Request(
				CommonVars.getCurrUser()), beforeCommInfo);
		tableModel.updateRow(beforeCommInfo);
	}

	private void initUIComponents() {
		// 初始化货币
		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCurrency);
		this.cbbCurrency.setSelectedIndex(-1);
		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUses);
		this.cbbUses.setSelectedIndex(-1);
		// 初始化征免方式
		this.cbbLevyModel.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyModel.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyModel);
		this.cbbLevyModel.setSelectedIndex(-1);
		// 产销国
		this.jComboBox
				.setModel(CustomBaseModel.getInstance().getCountryModel());
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.jComboBox);
		this.jComboBox.setSelectedIndex(-1);

		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.jComboBox1
				.setModel(new DefaultComboBoxModel(projectList.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "code", "name");
		jComboBox1.setSelectedIndex(-1);

		// 版本单重
		versions = materialManageAction.findVersion(new Request(CommonVars
				.getCurrUser(), true), beforeCommInfo);
		this.cbVersion.setModel(new DefaultComboBoxModel(versions.toArray()));
		this.cbVersion.setRenderer(CustomBaseRender.getInstance().getRender(
				"unitWeight", "version"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbVersion, "unitWeight", "version");
		this.cbVersion.setSelectedIndex(-1);
       //==============================
		String billAmount = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.BILL_TO_CONTROL_AMOUNT);
		if (billAmount == null) {
			billAmount = "3";
		}
		int digitNum = Integer.valueOf(billAmount);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfDeclaredAmount,
				digitNum);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfLegalAmount,
				digitNum);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfSecondLegalAmount,
				digitNum);
		
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDeclaredAmount,
				new AutoCalcListener() {
					public void run() {
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						double unitPrice = (tfDeclaredPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredPrice.getValue()
										.toString()));
						double totalPrice = CommonVars.getDoubleByDigit(
								unitPrice * amount, 5);
						tfTotalPrice.setValue(totalPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDeclaredPrice,
				new AutoCalcListener() {
					public void run() {
						double unitPrice = (tfDeclaredPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredPrice.getValue()
										.toString()));
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						double totalPrice = CommonVars.getDoubleByDigit(
								unitPrice * amount, 5);
						tfTotalPrice.setValue(totalPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfTotalPrice,
				new AutoCalcListener() {
					public void run() {
						double totalPrice = (tfTotalPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfTotalPrice.getValue()
										.toString()));
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						if (amount == 0) {
							amount = 1.0;
						}
						double unitPrice = CommonVars.getDoubleByDigit(
								totalPrice / amount, 5);
						tfDeclaredPrice.setValue(unitPrice);
					}
				});
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(new Rectangle(167, 21, 167, 22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(447, 46, 167, 22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnitWeight() {
		if (tfUnitWeight == null) {
			tfUnitWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitWeight.setBounds(new Rectangle(270, 418, 60, 23));
			tfUnitWeight.setEditable(false);
			tfUnitWeight.setVisible(false);
		}
		return tfUnitWeight;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(447, 229, 167, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalPrice.setBounds(new Rectangle(447, 203, 167, 22));
			tfTotalPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfTotalPrice.setEditable(true);
			String billTotalPrice = manualDecleareAction.getBpara(new Request(
					CommonVars.getCurrUser()),
					BcusParameter.BILL_TO_CONTROL_TOTALPRICE);
			if (billTotalPrice == null)
				billTotalPrice = "3";
			int digitNum = Integer.valueOf(billTotalPrice);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfTotalPrice,
					digitNum);
		}
		return tfTotalPrice;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new Rectangle(447, 342, 167, 22));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(167, 391, 450, 20));
		}
		return jTextField2;
	}

	/**
	 * This method initializes tfPeice
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfPeice() {
		if (tfPeice == null) {
			tfPeice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPeice.setBounds(new Rectangle(167, 311, 167, 22));
			tfPeice.setFormatterFactory(getDefaultFormatterFactory());
			tfPeice.setEditable(true);
		}
		return tfPeice;
	}

	/**
	 * This method initializes tfTotalPrice1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfStoreCount() {
		if (tfStoreCount == null) {
			tfStoreCount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfStoreCount.setFormatterFactory(getDefaultFormatterFactory());
			tfStoreCount.setBounds(new Rectangle(447, 418, 167, 22));
			tfStoreCount.setVisible(false);
			tfStoreCount.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (tfStoreCount.isVisible()) {
						process();
					}
				}
			});
			tfStoreCount.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							if (tfStoreCount.isVisible()) {
								process();
							}
						}

					});
		}
		return tfStoreCount;
	}

	private void process() {
		// EmsEdiMergerVersion version = (EmsEdiMergerVersion) cbVersion
		// .getSelectedItem();
		// if (version != null) {
		// Double mount = 0d;
		// try {
		// mount = Double.valueOf(tfStoreCount.getValue().toString());
		// System.out.println("ssssmount=" + mount);
		// } catch (Exception e1) {
		// mount = 0d;
		// }
		// Double superMount = version.getUnitWeight() * mount;
		// tfDeclaredAmount.setValue(superMount);
		// } else {
		// tfDeclaredAmount.setValue(0d);
		// }
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbVersion() {
		if (cbVersion == null) {
			cbVersion = new JComboBox();
			cbVersion.setBounds(new Rectangle(168, 418, 67, 22));
			cbVersion.setVisible(false);
			cbVersion.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// EmsEdiMergerVersion version = (EmsEdiMergerVersion)
					// cbVersion
					// .getSelectedItem();
					// if (version != null) {
					// tfUnitWeight.setValue(version.getUnitWeight());
					// tfVersion.setValue(version.getVersion());
					// } else {
					// tfUnitWeight.setValue(0d);
					// tfVersion.setValue(null);
					// }
					// if (tfStoreCount.isVisible()) {
					// process();
					// }
				}
			});
		}
		return cbVersion;
	}

	/**
	 * This method initializes tfPeice1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JTextField getTfVersion() {
		if (tfVersion == null) {
			tfVersion = new JTextField();
			tfVersion.setBounds(new Rectangle(447, 311, 57, 22));
		}
		return tfVersion;
	}

	/**
	 * This method initializes tfBoxNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBoxNo() {
		if (tfBoxNo == null) {
			tfBoxNo = new JTextField();
			tfBoxNo.setBounds(new Rectangle(167, 342, 167, 22));
		}
		return tfBoxNo;
	}

	/**
	 * This method initializes tfWordUsd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfWordUsd() {
		if (tfWordUsd == null) {
			tfWordUsd = new JCustomFormattedTextField();
			tfWordUsd.setBounds(new Rectangle(167, 229, 167, 22));
			String billTotalPrice = manualDecleareAction.getBpara(new Request(
					CommonVars.getCurrUser()),
					BcusParameter.BILL_TO_CONTROL_TOTALPRICE);
			if (billTotalPrice == null)
				billTotalPrice = "3";
			int digitNum = Integer.valueOf(billTotalPrice);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfWordUsd,
					digitNum);
		}
		return tfWordUsd;
	}

	/**
	 * This method initializes tfCmpVersion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfCmpVersion() {
		if (tfCmpVersion == null) {
			tfCmpVersion = new JTextField();
			tfCmpVersion.setBounds(new Rectangle(557, 311, 57, 22));
		}
		return tfCmpVersion;
	}
} // @jve:decl-index=0:visual-constraint="-29,5"
