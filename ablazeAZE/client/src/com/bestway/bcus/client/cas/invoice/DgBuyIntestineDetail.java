package com.bestway.bcus.client.cas.invoice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.invoice.entity.TempEmsImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.DgCommonQueryPage;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBuyIntestineDetail extends JDialogBase {
	private MaterialManageAction materialManageAction = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JFormattedTextField jTextField2 = null;

	private JLabel jLabel6 = null;

	private JFormattedTextField jTextField4 = null;

	private JFormattedTextField jTextField5 = null;

	private JFormattedTextField jTextField6 = null;

	private JButton jButton = null;

	private JButton jbOk = null;

	private JButton jbCancel = null;

	private JLabel jLabel7 = null;

	private JTextField tfName = null;

	private JLabel jLabel8 = null;

	private BaseEmsHead bh = null; // @jve:decl-index=0:

	public CasInvoiceInfo info = null;

	public CasInvoice head = null;

	public int state = DataState.ADD;

	private Complex complex = null; // @jve:decl-index=0:

	private CasAction casAction = null;

	public boolean isOk = false;

	private JComboBox jComboBox = null;
	private TempEmsImg tempEmsImg = null;

	private JLabel jLabel81 = null;

	private JLabel jLabel9 = null;

	private JFormattedTextField jTextField51 = null;

	private JLabel jLabel10 = null;

	private JFormattedTextField jTextField511 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBuyIntestineDetail() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();

	}

	public void setVisible(boolean f) {
		if (f) {
			showDate(info);
			setState();
			super.setVisible(f);
		}
	}

	private void showDate(CasInvoiceInfo info) {
		if (info == null) {
			return;
		}
		jTextField.setText(info.getComplex() == null ? "" : info.getComplex()
				.getCode());
		this.complex = info.getComplex();
		tfName.setText(info.getCuName());
		jTextField1.setText(info.getCuSpec());
		jComboBox.setSelectedItem(info.getUnit());
		jTextField2.setValue(info.getAmount());
		jTextField4.setValue(info.getPrice());
		jTextField5.setValue(info.getTotalMoney());
		jTextField51.setValue(info.getImpost());
		jTextField511.setValue(info.getAllMoney());
		jTextField6.setValue(info.getTotalWeight());

	}

	private boolean fillData() {
		if (state == DataState.ADD) {
			if (jTextField.getText() == null) {
				JOptionPane.showMessageDialog(DgBuyIntestineDetail.this,
						"商品编码不能为空!", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (tfName.getText() == null) {
				JOptionPane.showMessageDialog(DgBuyIntestineDetail.this,
						"名称不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (jTextField2.getValue() == null) {
				JOptionPane.showMessageDialog(DgBuyIntestineDetail.this,
						"数量不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if (jTextField4.getValue() == null) {
				JOptionPane.showMessageDialog(DgBuyIntestineDetail.this,
						"单价不能为空！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			info = new CasInvoiceInfo();
		}

		info.setCuName(tfName.getText());
		info.setCuSpec(jTextField1.getText());
		info.setAmount(Double
				.parseDouble(jTextField2.getValue() == null ? "0.0"
						: jTextField2.getValue().toString()));
		info.setPrice(Double.parseDouble(jTextField4.getValue() == null ? "0.0"
				: jTextField4.getValue().toString()));
		info.setTotalMoney(Double
				.parseDouble(jTextField5.getValue() == null ? "0.0"
						: jTextField5.getValue().toString()));
		info.setTotalWeight(Double
				.parseDouble(jTextField6.getValue() == null ? "0.0"
						: jTextField6.getValue().toString()));
		info.setComplex(this.complex);
		info.setUnit((Unit) jComboBox.getSelectedItem());
		info.setCasInvoice(head);
		info.setSeqNum(tempEmsImg == null ? null : tempEmsImg.getSeqNum());
		info.setProjectType(tempEmsImg == null ? null : tempEmsImg
				.getProjectType());
		info.setImpost(Double
				.parseDouble(jTextField51.getValue() == null ? "0.0"
						: jTextField51.getValue().toString()));
		info.setAllMoney(Double
				.parseDouble(jTextField511.getValue() == null ? "0.0"
						: jTextField511.getValue().toString()));
		return true;
	}

	private void setState() {
		jTextField.setEditable(state != DataState.BROWSE);
		jTextField2.setEditable(state != DataState.BROWSE);
		jTextField5.setEditable(state != DataState.BROWSE);
		jTextField51.setEditable(state != DataState.BROWSE);
		jTextField511.setEditable(state != DataState.BROWSE);
		jTextField4.setEditable(state != DataState.BROWSE);
		jTextField6.setEditable(state != DataState.BROWSE);
		jButton.setEnabled(state != DataState.BROWSE);
		tfName.setEditable(state != DataState.BROWSE);
		jTextField1.setEditable(state != DataState.BROWSE);
		jComboBox.setEnabled(state != DataState.BROWSE);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("国内购买发票体");
		this.setSize(new Dimension(333, 377));
		this.setContentPane(getJPanel());
		// 工厂单位
		List listunit = materialManageAction.findUnit(new Request(CommonVars
				.getCurrUser()));
		this.jComboBox.setModel(new DefaultComboBoxModel(listunit.toArray()));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox, "code", "name");
		jComboBox.setSelectedIndex(-1);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(37, 278, 61, 23));
			jLabel10.setText("价税合计");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(38, 252, 59, 22));
			jLabel9.setText("税额(RMB)");
			jLabel81 = new JLabel();
			jLabel81.setBounds(new Rectangle(181, 6, 99, 20));
			jLabel81.setText("增值税率: 17%");
			jLabel81.setForeground(new Color(51, 51, 255));
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(5, 3, 87, 29));
			jLabel8.setForeground(new Color(51, 51, 255));
			jLabel8.setText("新增发票商品 :");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(37, 64, 60, 22));
			jLabel7.setText("发票名称");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(37, 120, 60, 22));
			jLabel6.setText("发票单位:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(37, 175, 60, 22));
			jLabel5.setText("商品重量:");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(37, 228, 60, 22));
			jLabel4.setText("金额(RMB)");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(37, 202, 60, 22));
			jLabel3.setText("单价(RMB)");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(37, 147, 60, 22));
			jLabel2.setText("发票数量:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(37, 90, 60, 22));
			jLabel1.setText("发票规格:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(37, 37, 60, 22));
			jLabel.setText("商品编码");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(getJTextField5(), null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJbOk(), null);
			jPanel.add(getJbCancel(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfName(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJComboBox(), null); // Generated
			jPanel.add(jLabel81, null);
			jPanel.add(jLabel9, null);
			jPanel.add(getJTextField51(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getJTextField511(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(100, 37, 166, 22));
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
			jTextField1.setBounds(new Rectangle(100, 90, 187, 22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JFormattedTextField();
			jTextField2.setBounds(new Rectangle(100, 147, 187, 22));
			jTextField2.addPropertyChangeListener("value",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							changeValue();
						}
					});
			CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField2,
					5);
		}
		return jTextField2;
	}

	private void changeValue() {
		Double dou1 = Double
				.valueOf(getJTextField2().getValue() == null ? "0.0 "
						: getJTextField2().getValue().toString());
		Double dou2 = Double
				.valueOf(getJTextField4().getValue() == null ? "0.0 "
						: getJTextField4().getValue().toString());
		getJTextField5().setValue(dou1 * dou2);
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JFormattedTextField();
			jTextField4.setBounds(new Rectangle(100, 202, 187, 22));
			jTextField4.addPropertyChangeListener("value",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							changeValue();
						}
					});
			CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField4,
					4);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JFormattedTextField();
			jTextField5.setBounds(new Rectangle(100, 228, 187, 22));
			jTextField5.addPropertyChangeListener("value",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							CountMoney();
						}
					});
			CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField5,
					5);
		}
		return jTextField5;
	}

	private void CountMoney() {
		double totalMoney = Double
				.parseDouble(jTextField5.getValue() == null ? "0.0"
						: jTextField5.getValue().toString());
		double yl = totalMoney * 0.17;
		jTextField51.setValue(yl);
		double allMoney = totalMoney * (1 + 0.17);
		jTextField511.setValue(allMoney);
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JFormattedTextField();
			jTextField6.setBounds(new Rectangle(100, 175, 187, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField2,
					6);
		}
		return jTextField6;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(263, 37, 22, 22));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TempEmsImg tem = (TempEmsImg) getTempEmsImg();
					if (tem == null) {
						return;
					} else {
						jTextField.setText(tem.getComplex() == null ? "" : tem
								.getComplex().getCode());
						tfName.setText(tem.getCusName());

						jTextField1.setText(tem.getCusSpec());
						jComboBox.setSelectedItem(tem.getUnit());
						complex = tem.getComplex();
						tempEmsImg = tem;
					}

				}
			});
		}
		return jButton;
	}

	public Object getTempEmsImg() {
		CasAction casAction = (CasAction) CommonVars.getApplicationContext()
				.getBean("casAction");
		List datalist = casAction.findImgFromBaseEmsHead(new Request(CommonVars
				.getCurrUser()), bh, head);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("商品编码", "complex.code", 120));
		list.add(new JTableListColumn("商品名称", "cusName", 120));
		list.add(new JTableListColumn("商品规格", "cusSpec", 120));
		list.add(new JTableListColumn("单位", "unit.name", 80));
		list.add(new JTableListColumn("合同号", "emsNo", 80));
		list.add(new JTableListColumn("备案序号", "seqNum", 80));
		list.add(new JTableListColumn("手册号", "emsNo", 120));
		list.add(new JTableListColumn("来源", "projectName", 80));
		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setTitle("选择报关资料");
		DgCommonQueryPage.setSingleResult(true);
		dgCommonQuery.setDataSource(datalist);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * This method initializes jbOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbOk() {
		if (jbOk == null) {
			jbOk = new JButton();
			jbOk.setBounds(new Rectangle(97, 307, 60, 25));
			jbOk.setText("确定");
			jbOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!fillData()) {
						return;
					}
					casAction.savaOrUpdateObject(new Request(CommonVars
							.getCurrUser()), info);
					isOk = true;
					DgBuyIntestineDetail.this.dispose();
				}

			});
		}
		return jbOk;
	}

	/**
	 * This method initializes jbCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbCancel() {
		if (jbCancel == null) {
			jbCancel = new JButton();
			jbCancel.setBounds(new Rectangle(161, 307, 60, 25));
			jbCancel.setText("取消");
			jbCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DgBuyIntestineDetail.this.dispose();
				}

			});
		}
		return jbCancel;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(100, 64, 187, 22));
		}
		return tfName;
	}

	public BaseEmsHead getBh() {
		return bh;
	}

	public void setBh(BaseEmsHead bh) {
		this.bh = bh;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(100, 120, 187, 22)); // Generated
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField51
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJTextField51() {
		if (jTextField51 == null) {
			jTextField51 = new JFormattedTextField();
			jTextField51.setBounds(new Rectangle(100, 253, 187, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.jTextField51, 5);
		}
		return jTextField51;
	}

	/**
	 * This method initializes jTextField511
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJTextField511() {
		if (jTextField511 == null) {
			jTextField511 = new JFormattedTextField();
			jTextField511.setBounds(new Rectangle(100, 277, 187, 22));
			CustomFormattedTextFieldUtils.setFormatterFactory(
					this.jTextField511, 5);
		}
		return jTextField511;
	}

} // @jve:decl-index=0:visual-constraint="303,30"
