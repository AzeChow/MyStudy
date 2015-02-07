package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.ComponentOrientation;

/**
 * 出入仓单据明细编辑窗体
 * 
 * @author hw
 * 
 */
public class DgBlsInOutStockBillDetail extends JDialogBase {

	/**
	 * 进出仓标志
	 */
	private String inOutFlag = null;

	/**
	 * 数量
	 */
	private Double qTy = null;

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	/**
	 * 表格数据源
	 */
	private List dataSource = new ArrayList(); // @jve:decl-index=0:
	/**
	 * 物料实体类
	 */
	private Materiel materiels = null; // @jve:decl-index=0:

	public Materiel getMateriels() {
		return materiels;
	}

	public void setMateriels(Materiel materiels) {
		this.materiels = materiels;
	}

	public List getDataSource() {
		return dataSource;
	}

	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnEdit = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JComboBox cbbOriginCountry = null;

	private JLabel jLabel1 = null;

	private JButton btnSave = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbCurr = null;

	private JLabel jLabel5 = null;

	private JCustomFormattedTextField tfGrossWeight = null;

	private JLabel jLabel6 = null;

	private JCustomFormattedTextField tfNetWeight = null;

	private JLabel jLabel7 = null;

	private JCustomFormattedTextField tfUnitPrice = null;

	private JCustomFormattedTextField tfDetailQty = null;

	private JTextField tfPartNo = null;

	private int dataState = DataState.ADD;
	/**
	 * 出入仓单据表体实体类
	 */
	private BlsInOutStockBillDetail blsInOutStockBillDetail = null; // @jve:decl-index=0:

	/**
	 * 得到出入仓单据表体实体类
	 * 
	 * @return 出入仓单据表体
	 */
	public BlsInOutStockBillDetail getBlsInOutStockBillDetail() {
		return blsInOutStockBillDetail;
	}

	/**
	 * 设置出入仓单据表体实体类
	 * 
	 * @param blsInOutStockBillDetail
	 */
	public void setBlsInOutStockBillDetail(
			BlsInOutStockBillDetail blsInOutStockBillDetail) {
		this.blsInOutStockBillDetail = blsInOutStockBillDetail;
	}

	/**
	 * 出入仓单据表头实体类
	 */
	private BlsInOutStockBill blsInOutStockBill = null; // @jve:decl-index=0:

	Materiel materiel = null;

	private JTableListModel tableModel = null;

	private BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:

	DgBlsInOutStockBill dgg;

	public DgBlsInOutStockBill getDgg() {
		return dgg;
	}

	public void setDgg(DgBlsInOutStockBill dgg) {
		this.dgg = dgg;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JCustomFormattedTextField tfPackCount = null;

	private JLabel jLabel8 = null;

	private JTextField tfSeqNum = null;

	private JButton btnExit = null;

	private JLabel jLabel9 = null;

	private JTextField tfWarehouseName = null;

	private JLabel jLabel10 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel12 = null;

	private JCustomFormattedTextField tfTotalPrice = null;

	private JLabel lbUnit = null;
	private JLabel jLabel11 = null;
	private JTextField tfBlsInStockBillNumber = null;
	private JLabel jLabel13 = null;
	private JCustomFormattedTextField tfSerialNumber = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel16 = null;
	private JTextField tfEntryID = null;
	private JCustomFormattedTextField tfEntryNo = null;
	private JTextField tfMemo = null;
	private JLabel jLabel17 = null;
	private JTextField tfRemark2 = null;

	private JLabel jLabel18 = null;

	private JTextField tfEmsNo = null;

	public BlsInOutStockBill getBlsInOutStockBill() {
		return blsInOutStockBill;
	}

	public void setBlsInOutStockBill(BlsInOutStockBill blsInOutStockBill) {
		this.blsInOutStockBill = blsInOutStockBill;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * 出入仓单局编辑窗体构造方法 This method initializes
	 * 
	 */
	public DgBlsInOutStockBillDetail(
			BlsInOutStockBillDetail blsInOutStockBillDetail) {
		super();
		initialize();
		initUIComponents();
		// showData();
		this.btnEdit.setEnabled(false);
		if (blsInOutStockBillDetail == null) {
			blsInOutStockBillDetail = new BlsInOutStockBillDetail();
		}
		if (blsInOutStockBillDetail.getGrossWeight() != null) {
			this.tfGrossWeight.setValue(blsInOutStockBillDetail
					.getGrossWeight());
			this.tfGrossWeight.setEditable(false);
		} else {
			this.tfGrossWeight.setText("");
			this.tfGrossWeight.setEditable(true);
		}
		// this.tfGrossWeight
		// .setValue(blsInOutStockBillDetail.getGrossWeight() == null ? 0.0
		// : blsInOutStockBillDetail.getGrossWeight());
		// this.tfGrossWeight.setEditable(false);
		this.tfNetWeight
				.setValue(blsInOutStockBillDetail.getNetWeight() == null ? 0.0
						: blsInOutStockBillDetail.getNetWeight());
		this.tfNetWeight.setEditable(true);
		if (blsInOutStockBillDetail.getPartNo() != null) {
			this.tfPartNo
					.setText(blsInOutStockBillDetail.getPartNo().getPtNo());
			this.tfPartNo.setEditable(false);
		} else {
			this.tfPartNo.setText("");
			this.tfPartNo.setEditable(false);
		}
		if (blsInOutStockBillDetail.getSpec() != null) {
			this.tfSpec.setText(blsInOutStockBillDetail.getSpec());
			this.tfSpec.setEditable(false);
		} else {
			this.tfSpec.setText("");
			this.tfSpec.setEditable(false);
		}
		if (blsInOutStockBillDetail.getWarehouseName() != null) {
			this.tfWarehouseName.setText(blsInOutStockBillDetail
					.getWarehouseName());
			this.tfWarehouseName.setEditable(false);
		} else {
			this.tfWarehouseName.setText("");
			this.tfWarehouseName.setEditable(false);
		}
		if (blsInOutStockBillDetail.getUnit() != null) {
			this.lbUnit.setText(blsInOutStockBillDetail.getUnit().getName());
			// this.lbUnit.setEditable(false);
		} else {
			this.lbUnit.setText("");
			// this.lbUnit.setEditable(false);
		}
		// this.tfPartNo
		// .setText(blsInOutStockBillDetail.getPartNo().getPtNo() == null ? ""
		// : blsInOutStockBillDetail.getPartNo().getPtNo());
		// this.tfPartNo.setEditable(false);
		this.tfDetailQty.setText(String.valueOf(blsInOutStockBillDetail
				.getDetailQty() == null ? 0.0 : blsInOutStockBillDetail
				.getDetailQty()));
		this.tfDetailQty.setEditable(true);
		if (blsInOutStockBillDetail.getTotalPrice() != null) {
			this.tfTotalPrice
					.setValue(blsInOutStockBillDetail.getTotalPrice() == null ? 0.0
							: blsInOutStockBillDetail.getTotalPrice());
			this.tfTotalPrice.setEnabled(false);
		} else {
			this.tfTotalPrice.setText("");
			this.tfTotalPrice.setEnabled(false);
		}

		this.cbbOriginCountry.setSelectedIndex(-1);
		this.cbbCurr.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(481, 447));
		this.setContentPane(getJPanel());
		this.setTitle("出入仓单据商品明细编辑");
	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected void initUIComponents() {
		// 初始化币制
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCurr);
		// 初始化起运国
		this.cbbOriginCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbOriginCountry.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbOriginCountry);
		// 数量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfDetailQty, 1);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDetailQty,
				new AutoCalcListener() {
					public void run() {
						tfTotalPrice.setValue(getTotalPrice());
					}
				});

		// 单价
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice, 1);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfUnitPrice,
				new AutoCalcListener() {
					public void run() {
						tfTotalPrice.setValue(getTotalPrice());
					}
				});

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
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// else if(dataState == DataState.EDIT)
					// {
					// btnEdit.setEnabled(false);
					// btnSave.setEnabled(true);
					// }
					dataState = DataState.EDIT;
					setState();
					tfNetWeight.setEditable(true);
					tfGrossWeight.setEditable(true);
					tfDetailQty.setEditable(true);
					btnEdit.setEnabled(false);
					btnSave.setEnabled(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(32, 348, 63, 18));
			jLabel18.setText("账册序号");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new Rectangle(232, 312, 92, 18));
			jLabel17.setText("                   备注2");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(30, 312, 63, 21));
			jLabel16
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel16.setText("备注1");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(232, 237, 92, 22));
			jLabel15
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel15.setText("报关单商品序号");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(25, 238, 68, 21));
			jLabel14
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel14.setText("报关单编号");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(232, 274, 92, 22));
			jLabel13
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel13.setText(" 入仓单商品序号");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(33, 275, 60, 21));
			jLabel11
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel11.setText("入仓单号码");
			lbUnit = new JLabel();
			lbUnit.setBounds(new Rectangle(184, 90, 46, 21));
			lbUnit.setText(" 千克");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(232, 200, 92, 22));
			jLabel12
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel12.setText("总价");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(44, 201, 49, 21));
			jLabel10
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel10.setText("型号规格");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(232, 163, 92, 22));
			jLabel9.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel9.setText("商品名称");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(44, 164, 49, 21));
			jLabel8.setForeground(Color.blue);
			jLabel8.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel8.setText("归并序号");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(232, 126, 92, 22));
			jLabel7.setForeground(Color.blue);
			jLabel7.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel7.setText("件数");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(232, 89, 92, 22));
			jLabel6.setForeground(Color.blue);
			jLabel6.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel6.setText("净重");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(232, 52, 92, 22));
			jLabel5.setForeground(Color.blue);
			jLabel5.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel5.setText("毛重");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(232, 15, 92, 22));
			jLabel4.setForeground(Color.blue);
			jLabel4.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel4.setText("币值");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(44, 127, 49, 21));
			jLabel3.setForeground(Color.blue);
			jLabel3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel3.setText("申报单价");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(65, 90, 28, 21));
			jLabel2.setForeground(Color.blue);
			jLabel2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel2.setText("数量");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(17, 53, 76, 21));
			jLabel1.setForeground(Color.blue);
			jLabel1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel1.setText("企业内部货号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(33, 16, 60, 21));
			jLabel.setForeground(Color.blue);
			jLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jLabel.setText("原产国");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbOriginCountry(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getCbbCurr(), null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getTfGrossWeight(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getTfNetWeight(), null);
			jPanel1.add(jLabel7, null);
			jPanel1.add(getTfUnitPrice(), null);
			jPanel1.add(getTfDetailQty(), null);
			jPanel1.add(getTfPartNo(), null);
			jPanel1.add(getTfPackCount(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getTfSeqNum(), null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getTfWarehouseName(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getTfSpec(), null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(getTfTotalPrice(), null);
			jPanel1.add(lbUnit, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getTfBlsInStockBillNumber(), null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(getTfSerialNumber(), null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(getTfEntryID(), null);
			jPanel1.add(getTfEntryNo(), null);
			jPanel1.add(getTfMemo(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getTfRemark2(), null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(getTfEmsNo(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbOriginCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOriginCountry() {
		if (cbbOriginCountry == null) {
			cbbOriginCountry = new JComboBox();
			cbbOriginCountry.setBounds(new Rectangle(101, 16, 108, 21));
		}
		return cbbOriginCountry;
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
					if (!checkData()) {
						return;
					}
					fillData();
					// blsInOutStockBillDetail = (BlsInOutStockBillDetail)
					// tableModel
					// .getCurrentRow();
					// String id = blsInOutStockBillDetail.getId();
					// System.out.println("保存前测试："+blsInOutStockBillDetail.getPackCount());
					blsInOutStockBillDetail = blsInOutStockBillAction
							.saveBlsInOutStockBillDetail(new Request(CommonVars
									.getCurrUser()), blsInOutStockBillDetail);
					System.out.println("保存后数据测试："
							+ blsInOutStockBillDetail.getPackCount());
					String id = blsInOutStockBillDetail.getId();
					showData();
					if (id == null) {
						dgg.tableModel.addRow(blsInOutStockBillDetail);
					} else {
						dgg.tableModel.updateRow(blsInOutStockBillDetail);
					}
					dataState = DataState.BROWSE;
					setState();
					cbbOriginCountry.setEditable(true);
					cbbCurr.setEditable(true);
					btnEdit.setEnabled(true);
					btnSave.setEnabled(false);
				}
			});
		}
		return btnSave;
	}

	/**
	 * 填充数据
	 * 
	 * @param data
	 */
	private void fillData() {
		blsInOutStockBillDetail = (BlsInOutStockBillDetail) tableModel
				.getCurrentRow();

		if (CommonUtils.getDoubleExceptNull(qTy) == 0) {
			blsInOutStockBillDetail.setNowDetailQty(Double.valueOf(tfDetailQty
					.getValue().toString()));
		} else {
			if (Double.valueOf(tfDetailQty.getValue().toString()) < CommonUtils
					.getDoubleExceptNull(qTy)) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
						"数量小于已经对应数量", "出入仓单据商品明细", JOptionPane.OK_OPTION);
				return;
			}
		}
		blsInOutStockBillDetail.setOriginCountry((Country) cbbOriginCountry
				.getSelectedItem());
		blsInOutStockBillDetail.setCurr((Curr) cbbCurr.getSelectedItem());
		if (tfUnitPrice.getValue() != null) {
			blsInOutStockBillDetail.setUnitPrice(Double.valueOf(tfUnitPrice
					.getValue().toString()));
		} else {
			blsInOutStockBillDetail.setUnitPrice(null);
		}
		// blsInOutStockBillDetail.setPartNo(materiels);
		if (this.tfNetWeight.getValue() != null) {
			blsInOutStockBillDetail.setNetWeight(Double
					.valueOf(this.tfNetWeight.getValue().toString()));
		} else {
			blsInOutStockBillDetail.setNetWeight(null);
		}
		// blsInOutStockBillDetail.setGrossWeight(materiels.getPtOutWeight());
		// System.out.println("list.size()="+dataSource.size());
		// System.out.println("materiels="+materiels);
		// blsInOutStockBillDetail.setDetailQty(materiels.getAmount());
		if (tfDetailQty.getValue() != null) {
			blsInOutStockBillDetail.setDetailQty(Double.valueOf(tfDetailQty
					.getValue().toString()));
		} else {
			blsInOutStockBillDetail.setDetailQty(null);
		}
		System.out.println("tf=" + tfPackCount.getValue());
		System.out.println("tf2=" + tfPackCount.getText());
		if (tfPackCount.getText().trim() != null) {
			blsInOutStockBillDetail.setPackCount(Integer.valueOf(tfPackCount
					.getText().trim().toString()));
		} else {
			blsInOutStockBillDetail.setPackCount(null);
		}
		System.out.println("填充数据测试：" + blsInOutStockBillDetail.getPackCount());
		// blsInOutStockBillDetail.setNetWeight(Double
		// .parseDouble((tfNetWeight.getText() == null || tfNetWeight
		// .getText().trim().equals("")) ? "0.0" : tfNetWeight
		// .getText()));
		if (tfGrossWeight.getValue() != null) {
			blsInOutStockBillDetail.setGrossWeight(Double
					.parseDouble(tfGrossWeight.getValue().toString()));
		} else {
			blsInOutStockBillDetail.setGrossWeight(null);
		}

		// blsInOutStockBillDetail.setDetailQty(Double
		// .parseDouble((tfDetailQty.getText() == null || tfDetailQty
		// .getText().trim().equals("")) ? "0.0" : tfDetailQty
		// .getText()));
		if (tfEmsNo.getText() != null) {
			blsInOutStockBillDetail.setEmsNo(Integer
					.parseInt(tfEmsNo.getText() == null ? null : tfEmsNo
							.getText()));
		} else {
			blsInOutStockBillDetail.setEmsNo(null);
		}
		if (tfWarehouseName.getText() != null) {
			blsInOutStockBillDetail
					.setWarehouseName(tfWarehouseName.getText() == null ? ""
							: tfWarehouseName.getText());
		} else {
			blsInOutStockBillDetail.setWarehouseName("");
		}
		if (tfTotalPrice.getValue() != null) {
			System.out.println("tfTotalPrice.getValue()="
					+ tfTotalPrice.getValue());
			blsInOutStockBillDetail.setTotalPrice(Double
					.parseDouble(tfTotalPrice.getValue().toString()));
		} else {
			blsInOutStockBillDetail.setTotalPrice(null);
		}
		if (blsInOutStockBillDetail.getSeqNo() == null) {
			System.out.println("this id=" + blsInOutStockBillDetail.getId());
			blsInOutStockBillDetail.setSeqNo(blsInOutStockBillAction
					.findMaxBlsInOutStockBillSeqNos(new Request(CommonVars
							.getCurrUser()), blsInOutStockBill) + 1);
		}
		blsInOutStockBillDetail.setInBillNo(tfBlsInStockBillNumber.getText()
				.trim());
		blsInOutStockBillDetail.setInBillGoodNo("".equals(tfSerialNumber
				.getText().trim()) ? null : Integer.parseInt(tfSerialNumber
				.getText().trim()));

		blsInOutStockBillDetail.setEntryID(tfEntryID.getText());
		blsInOutStockBillDetail.setEntryGNo(tfEntryNo.getText());
		blsInOutStockBillDetail.setRemarks1(this.tfMemo.getText());
		blsInOutStockBillDetail.setRemarks2(this.tfRemark2.getText());
	}

	/**
	 * 设置组建状态
	 */
	private void setState() {
		// this.btnEdit.setEnabled(dataState != DataState.BROWSE);
		this.cbbOriginCountry.setEnabled(dataState != DataState.BROWSE);
		this.cbbCurr.setEnabled(dataState != DataState.BROWSE);
		this.tfGrossWeight.setEnabled(dataState != DataState.BROWSE);
		this.tfPartNo.setEnabled(dataState != DataState.BROWSE);
		this.tfDetailQty.setEnabled(dataState != DataState.BROWSE);
		this.tfNetWeight.setEnabled(dataState != DataState.BROWSE);
		// this.tfNetWeight.setEnabled(true);
		// a
		this.tfUnitPrice.setEnabled(dataState != DataState.BROWSE);
		this.tfPackCount.setEnabled(dataState != DataState.BROWSE);
		this.tfSeqNum.setEnabled(dataState != DataState.BROWSE);
		this.tfWarehouseName.setEnabled(dataState != DataState.BROWSE);
		// this.tfUnit.setEnabled(dataState != DataState.BROWSE);
		this.tfSpec.setEnabled(dataState != DataState.BROWSE);
		this.tfTotalPrice.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit.setEnabled(!blsInOutStockBill.getIsEffective());

		this.tfEntryID.setEditable(dataState != DataState.BROWSE);
		this.tfEntryNo.setEditable(dataState != DataState.BROWSE);

		this.tfBlsInStockBillNumber.setEnabled(dataState != DataState.BROWSE);
		this.tfSerialNumber.setEnabled(dataState != DataState.BROWSE);
		this.tfMemo.setEditable(dataState != DataState.BROWSE);
		this.tfRemark2.setEditable(dataState != DataState.BROWSE);
		this.tfEmsNo.setEditable(dataState != DataState.BROWSE);
	}

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (cbbOriginCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"原产国必须选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
//		String emsNo = this.tfEmsNo.getText();
//		if (emsNo == null || emsNo.equals("")) {
//			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
//					"账册序号必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
//			return false;
//		}
		// String partNo = this.tfPartNo.getText();
		// if (partNo == null || partNo.equals("")) {
		// JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
		// "企业内部货号必需填写！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		// if (blsInOutStockBill.getId() == null) {
		// List list = blsInOutStockBillAction.findBillNo(new Request(
		// CommonVars.getCurrUser()), billNo);
		// if (!list.isEmpty()) {
		// JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
		// "单据编号重复，请重新填写！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		// }
		if (cbbCurr.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"币制必须选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		// if (tfDetailQty.getText() != null
		// && !tfDetailQty.getText().trim().equals("")) {
		// try {
		// Integer.parseInt(tfDetailQty.getText().toString());
		// } catch (Exception e) {
		// JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
		// "数量必须填写有效整数 ！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }
		// } else {
		// JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
		// "数量必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
		// return false;
		// }

		if (tfPackCount.getText() != null
				&& !tfPackCount.getText().trim().equals("")) {
			try {
				Integer.parseInt(tfPackCount.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
						"件数必须填写有效整数 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"件数必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		Double nw = 0.0;
		Double gw = 0.0;
		if (tfNetWeight.getText() != null
				&& !tfNetWeight.getText().trim().equals("")) {
			try {
				nw = Double.parseDouble(tfNetWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
						"净重必须填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}

		} else {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"净重必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (tfGrossWeight.getText() != null
				&& !tfGrossWeight.getText().trim().equals("")) {
			try {
				gw = Double.parseDouble(tfGrossWeight.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
						"毛重必须填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"毛重必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (nw - gw > 0) {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"毛重必须大于等于净重 ！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (tfUnitPrice.getText() != null
				&& !tfUnitPrice.getText().trim().equals("")) {
			try {
				gw = Double.parseDouble(tfUnitPrice.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
						"申报单价必须填写有效数字 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"申报单价必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		 if (tfEmsNo.getText() != null && !tfEmsNo.getText().trim().equals("")) {
			try {
				Integer.parseInt(tfEmsNo.getText().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
						"账册序号必须填写有效整数 ！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(DgBlsInOutStockBillDetail.this,
					"账册序号必须填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!tfDetailQty.getText().trim().equals("")) {
			if (Double.valueOf(tfDetailQty.getText().trim()).doubleValue() <= 0) {
				JOptionPane.showMessageDialog(this, "数量不能小于或等于 0！", "提示！", 0);
				tfDetailQty.requestFocus();
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "数量不合法或为空！", "提示！", 0);
			tfDetailQty.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		this.qTy = blsInOutStockBillDetail.getDetailQty();
		// a
		this.tfPartNo
				.setText(blsInOutStockBillDetail.getPartNo().getPtNo() == null ? ""
						: blsInOutStockBillDetail.getPartNo().getPtNo());
		if (blsInOutStockBillDetail.getOriginCountry() != null) {
			cbbOriginCountry.setSelectedItem(blsInOutStockBillDetail
					.getOriginCountry());
		} else {
			cbbOriginCountry.setSelectedIndex(-1);
		}

		this.cbbCurr.setSelectedItem(blsInOutStockBillDetail.getCurr());
		// this.tfPartNo.setText((String)(blsInOutStockBillDetail.getPartNo()==null?"":blsInOutStockBillDetail.getPartNo()));
		if (blsInOutStockBillDetail.getGrossWeight() != null) {
			this.tfGrossWeight.setValue(this.blsInOutStockBillDetail
					.getGrossWeight());
		} else {
			this.tfGrossWeight.setText("");
		}
		if (blsInOutStockBillDetail.getNetWeight() != null) {
			this.tfNetWeight.setValue(this.blsInOutStockBillDetail
					.getNetWeight());
		} else {
			this.tfNetWeight.setText("");
		}
		if (blsInOutStockBillDetail.getUnitPrice() != null) {
			this.tfUnitPrice.setValue(this.blsInOutStockBillDetail
					.getUnitPrice());
		} else {
			this.tfUnitPrice.setText("");
		}
		if (blsInOutStockBillDetail.getDetailQty() != null) {
			this.tfDetailQty.setValue(this.blsInOutStockBillDetail
					.getDetailQty());
		} else {
			this.tfDetailQty.setText("");
		}
		this.tfPackCount
				.setText((blsInOutStockBillDetail.getPackCount() == null ? ""
						: blsInOutStockBillDetail.getPackCount()).toString());
		this.tfEmsNo
				.setText(blsInOutStockBillDetail.getEmsNo() == null ? null
						: blsInOutStockBillDetail.getEmsNo().toString());
		if (blsInOutStockBillDetail.getUnit() != null) {
			this.lbUnit.setText(blsInOutStockBillDetail.getUnit().getName());
		} else {
			this.lbUnit.setText("");
		}
		if (blsInOutStockBillDetail.getSpec() != null) {
			this.tfSpec.setText(blsInOutStockBillDetail.getSpec());
		} else {
			this.tfSpec.setText("");
		}
		if (blsInOutStockBillDetail.getInBillNo() != null) {
			this.tfBlsInStockBillNumber.setText(blsInOutStockBillDetail
					.getInBillNo());
		} else {
			this.tfBlsInStockBillNumber.setText("");
		}
		if (blsInOutStockBillDetail.getInBillGoodNo() != null) {
			this.tfSerialNumber.setText(blsInOutStockBillDetail
					.getInBillGoodNo().toString());
		} else {
			this.tfSerialNumber.setText("");
		}

		if (blsInOutStockBillDetail.getEntryID() != null) {
			this.tfEntryID.setText(blsInOutStockBillDetail.getEntryID());
		}

		if (blsInOutStockBillDetail.getEntryGNo() != null) {
			this.tfEntryNo.setText(blsInOutStockBillDetail.getEntryGNo());
		}

		if (blsInOutStockBillDetail.getRemarks1() != null) {
			this.tfMemo.setText(blsInOutStockBillDetail.getRemarks1());
		}
		if (blsInOutStockBillDetail.getRemarks2() != null) {
			this.tfRemark2.setText(blsInOutStockBillDetail.getRemarks2());
		}
		if (blsInOutStockBillDetail.getWarehouseName() != null) {
			this.tfWarehouseName.setText(blsInOutStockBillDetail
					.getWarehouseName());
		}
	}

	/**
	 * 设置显示和组建状态
	 */
	public void setVisibles(boolean f) {
		if (f) {
			if (dataState == DataState.BROWSE) {
				btnEdit.setEnabled(true);
				btnSave.setEnabled(false);
			}
			if (blsInOutStockBill.getIoFlag().equals(BlsIOStockBillIOF.IMPORT)) {
				this.jLabel11.setVisible(false);
				this.tfBlsInStockBillNumber.setVisible(false);
				this.jLabel13.setVisible(false);
				this.tfSerialNumber.setVisible(false);

				this.jLabel16.setBounds(jLabel11.getBounds());
				this.tfMemo.setLocation(this.tfBlsInStockBillNumber
						.getLocation());
			} else {
				this.jLabel11.setVisible(true);
				this.tfBlsInStockBillNumber.setVisible(true);
				this.jLabel13.setVisible(true);
				this.tfSerialNumber.setVisible(true);
			}
			showData();
			setState();
			super.setVisible(f);
		}
	}

	/**
	 * This method initializes cbbCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(334, 15, 95, 22));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			// DefaultFormatterFactory defaultFormatterFactory = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory.setEditFormatter(new NumberFormatter());
			// defaultFormatterFactory.setDisplayFormatter(new
			// NumberFormatter());
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(new Rectangle(334, 52, 95, 22));
			// tfGrossWeight.setFormatterFactory(defaultFormatterFactory);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfGrossWeight, 4);
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes tfNetWeight
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			// DefaultFormatterFactory defaultFormatterFactory1 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			// defaultFormatterFactory1.setDisplayFormatter(new
			// NumberFormatter());
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(new Rectangle(334, 89, 95, 22));
			// tfNetWeight.setFormatterFactory(defaultFormatterFactory1);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfNetWeight, 4);
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			// DefaultFormatterFactory defaultFormatterFactory21 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory21.setEditFormatter(new
			// NumberFormatter());
			// defaultFormatterFactory21
			// .setDisplayFormatter(new NumberFormatter());
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(new Rectangle(101, 127, 108, 21));
			// tfUnitPrice.setFormatterFactory(defaultFormatterFactory21);

		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes tfDetailQty
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfDetailQty() {
		if (tfDetailQty == null) {
			// DefaultFormatterFactory defaultFormatterFactory211 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory211.setEditFormatter(new
			// NumberFormatter());
			// defaultFormatterFactory211
			// .setDisplayFormatter(new NumberFormatter());
			tfDetailQty = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDetailQty.setBounds(new Rectangle(101, 90, 82, 21));
			// tfDetailQty.setFormatterFactory(defaultFormatterFactory211);
			CustomFormattedTextFieldUtils.setFormatterFactory(tfDetailQty, 4);
			tfDetailQty.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							tfTotalPrice.setValue(getTotalPrice());

						}

					});
		}
		return tfDetailQty;
	}

	/**
	 * This method initializes tfPartNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPartNo() {
		if (tfPartNo == null) {
			tfPartNo = new JTextField();
			tfPartNo.setBounds(new Rectangle(101, 53, 108, 21));
		}
		return tfPartNo;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					blsInOutStockBillDetail = (BlsInOutStockBillDetail) tableModel
							.getCurrentRow();

					showData();

				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					blsInOutStockBillDetail = (BlsInOutStockBillDetail) tableModel
							.getCurrentRow();
					showData();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes tfPackCount
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfPackCount() {
		if (tfPackCount == null) {
			// DefaultFormatterFactory defaultFormatterFactory11 = new
			// DefaultFormatterFactory();
			// defaultFormatterFactory11.setEditFormatter(new
			// NumberFormatter());
			// defaultFormatterFactory11
			// .setDisplayFormatter(new NumberFormatter());
			tfPackCount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPackCount.setBounds(new Rectangle(334, 126, 95, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfPackCount, 4);
		}
		return tfPackCount;
	}

	/**
	 * This method initializes tfSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(new Rectangle(101, 164, 108, 21));
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfWarehouseName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWarehouseName() {
		if (tfWarehouseName == null) {
			tfWarehouseName = new JTextField();
			tfWarehouseName.setBounds(new Rectangle(334, 163, 95, 22));
		}
		return tfWarehouseName;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new Rectangle(101, 201, 108, 21));
		}
		return tfSpec;
	}

	/**
	 * 得到总价
	 * 
	 * @return
	 */
	protected Double getTotalPrice() {
		double detailQty = 0;
		double unitPrice = 0;
		if (this.tfDetailQty.getText() != null
				&& !this.tfDetailQty.getText().equals("")) {
			detailQty = Double.parseDouble(this.tfDetailQty.getValue()
					.toString());
		}
		if (this.tfUnitPrice.getText() != null
				&& !this.tfUnitPrice.getText().equals("")) {
			unitPrice = Double.parseDouble(this.tfUnitPrice.getValue()
					.toString());
		}
		BigDecimal bd = new BigDecimal(detailQty * unitPrice);
		String totalPrice = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalPrice);
	}

	/**
	 * This method initializes tfTotalPrice
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			DefaultFormatterFactory defaultFormatterFactory2111 = new DefaultFormatterFactory();
			defaultFormatterFactory2111.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory2111
					.setDisplayFormatter(new NumberFormatter());
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new Rectangle(334, 200, 95, 22));
			tfTotalPrice.setFormatterFactory(defaultFormatterFactory2111);
		}
		return tfTotalPrice;
	}

	/**
	 * This method initializes tfBlsInStockBillNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBlsInStockBillNumber() {
		if (tfBlsInStockBillNumber == null) {
			tfBlsInStockBillNumber = new JTextField();
			tfBlsInStockBillNumber.setBounds(new Rectangle(101, 275, 108, 21));
		}
		return tfBlsInStockBillNumber;
	}

	/**
	 * This method initializes tfSerialNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfSerialNumber() {
		if (tfSerialNumber == null) {
			tfSerialNumber = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSerialNumber.setBounds(new Rectangle(334, 274, 95, 22));
			CustomFormattedTextFieldUtils
					.setFormatterFactory(tfSerialNumber, 4);
		}
		return tfSerialNumber;
	}

	/**
	 * This method initializes tfEntryID
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEntryID() {
		if (tfEntryID == null) {
			tfEntryID = new JTextField();
			tfEntryID.setBounds(new Rectangle(101, 238, 108, 21));
		}
		return tfEntryID;
	}

	/**
	 * This method initializes tfEntryNo
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfEntryNo() {
		if (tfEntryNo == null) {
			tfEntryNo = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfEntryNo.setBounds(new Rectangle(334, 237, 95, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(tfEntryNo, 4);
		}
		return tfEntryNo;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(new Rectangle(101, 312, 108, 21));
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfRemark2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemark2() {
		if (tfRemark2 == null) {
			tfRemark2 = new JTextField();
			tfRemark2.setBounds(new Rectangle(334, 312, 95, 22));
		}
		return tfRemark2;
	}

	/**
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(101, 348, 108, 21));
		}
		return tfEmsNo;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
