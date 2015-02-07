package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.customs.common.entity.BaseEmsHead;

public class PnMakeCustomsOrderToFptAppHead1 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel9 = null;
	private JComboBox cbbProjectType1 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField1 = null;
	private JButton jButton1 = null;
	private JPanel jPanel = null;
	private FptManageAction fptManageAction = null;
	private ButtonGroup bgp = new ButtonGroup(); // @jve:decl-index=0:
	private int projectType = ProjectType.BCS;
	private String emsNo = ""; // @jve:decl-index=0:
	private List oderList = new ArrayList(); // @jve:decl-index=0:
	private String materielType = MaterielType.MATERIEL; // @jve:decl-index=0:
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public PnMakeCustomsOrderToFptAppHead1() {
		super();
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();
		initCompnent();
	}

	private void initCompnent() {
		// ------------------------------------------
		this.cbbProjectType1.removeAllItems();
		this.cbbProjectType1.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		this.cbbProjectType1.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "电子化手册"));
		this.cbbProjectType1.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbProjectType1);
		this.cbbProjectType1.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType1.setSelectedIndex(-1);
		// ---------------------------------------------
		bgp.add(jRadioButton);
		bgp.add(jRadioButton1);
		// ---------------------------------------------
//		try {
//			Image image = javax.imageio.ImageIO.read(getClass().getResource(
//					"/com/bestway/client/resource/images/makeFptApphead.jpg"));
//			Graphics g = this.getGraphics();
//			if (image != null) {
//				g.drawImage(image, 0, 0, this.getPreferredSize().width,
//						this.getPreferredSize().height, this);
//			}
//			// jLabel.setIcon(new ImageIcon(image));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel2 = new JLabel();
		jLabel2.setText("手   册   号");
		jLabel2.setBounds(new Rectangle(91, 65, 60, 23));
		jLabel9 = new JLabel();
		jLabel9.setText("管理类型");
		jLabel9.setBounds(new Rectangle(89, 37, 57, 23));
		jLabel6 = new JLabel();
		jLabel6.setText("订   单   号");
		jLabel6.setBounds(new Rectangle(91, 91, 60, 23));
		this.setSize(598, 360);
		this.setLayout(new BorderLayout());
		this.add(getJPanel(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes pnMaster
	 * 
	 * @return javax.swing.JPanel
	 */

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(new Rectangle(153, 92, 277, 22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("...");
			jButton.setBounds(new Rectangle(430, 93, 24, 20));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					oderList = getOrders();
					String str = "";
					if (oderList != null) {
						for (int i = 0; i < oderList.size(); i++) {
							CustomOrder cod = (CustomOrder) oderList.get(i);
							str += (cod.getBillCode() == null ? "" : (cod
									.getBillCode())
									+ ";");
						}
					}
					getJTextField().setText(str);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes cbbProjectType1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType1() {
		if (cbbProjectType1 == null) {
			cbbProjectType1 = new JComboBox();
			cbbProjectType1.setBounds(new Rectangle(151, 37, 192, 25));
		}
		return cbbProjectType1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("转入");
			jRadioButton.setSelected(true);
			jRadioButton.setBounds(new Rectangle(345, 39, 61, 22));
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					materielType = MaterielType.MATERIEL;
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
			jRadioButton1.setText("转出");
			jRadioButton1.setBounds(new Rectangle(407, 39, 59, 22));
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							materielType = MaterielType.FINISHED_PRODUCT;
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setEditable(false);
			jTextField1.setBounds(new Rectangle(153, 67, 277, 22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("...");
			jButton1.setBounds(new Rectangle(430, 67, 24, 20));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty item = (ItemProperty) getCbbProjectType1()
							.getSelectedItem();
					if (item == null) {
						JOptionPane
								.showMessageDialog(
										PnMakeCustomsOrderToFptAppHead1.this,
										"没有选择管理类型！", "提示！",
										JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (item.getCode().equals(String.valueOf(ProjectType.BCUS))) {
						projectType = ProjectType.BCUS;
					} else if (item.getCode().equals(
							String.valueOf(ProjectType.DZSC))) {
						projectType = ProjectType.DZSC;
					} else if (item.getCode().equals(
							String.valueOf(ProjectType.BCS))) {
						projectType = ProjectType.BCS;
					}
					BaseEmsHead beh = (BaseEmsHead) getAllEms(projectType);
					String str = "";
					if (beh != null) {
						getJTextField1().setText(beh.getEmsNo());
						emsNo = beh.getEmsNo();
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(19, 103, 268, 158));
//			jLabel.setDisabledIcon(new ImageIcon(getClass().getResource("/com/bestway/client/resource/images/makeFptApphead.jpg")));
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/bestway/client/resource/images/makeFptApphead.gif")));
			jLabel.setText(null);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createLineBorder(SystemColor.activeCaptionBorder, 1),
					"\u5fc5\u8981\u6761\u4ef6\u586b\u5199",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(102, 102, 102)));
			jPanel.add(jLabel9, null);
			jPanel.add(getCbbProjectType1(), null);
			jPanel.add(getJRadioButton(), null);
			jPanel.add(getJRadioButton1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	private List getOrders() {
		List dataSource = fptManageAction
				.findCustomOrderByIfok(new Request(CommonVars.getCurrUser(),
						true));
		System.out.println(dataSource.size());
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("订单号码", "billCode", 100));
		list.add(new JTableListColumn("订单日期", "orderDate", 80));
		list.add(new JTableListColumn("客户名称", "customer.name", 150));
		list.add(new JTableListColumn("转厂", "ifzc", 55));
		list.add(new JTableListColumn("生效", "ifok", 55));
		list.add(new JTableListColumn("订单版本号", "importcount", 80));
		list.add(new JTableListColumn("报关类型", "customType", 80));
		list.add(new JTableListColumn("定单总项数", "contractCount", 100));
		list.add(new JTableListColumn("已转合同项数", "isChangeToContract", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择订单！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;

	}

	private Object getAllEms(int projectType) {
		List dataSource = fptManageAction.findAllEmsExe(
				new Request(CommonVars.getCurrUser()), projectType);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("手册号", "emsNo", 200));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("请选择手册！");
		dgCommonQuery.setLike(false);
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;

	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public List getOderList() {
		return oderList;
	}

	public void setOderList(List oderList) {
		this.oderList = oderList;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
} // @jve:decl-index=0:visual-constraint="10,14"
