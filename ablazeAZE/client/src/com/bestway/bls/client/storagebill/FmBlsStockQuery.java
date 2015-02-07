package com.bestway.bls.client.storagebill;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.entity.TempQueryProduct;
import com.bestway.bls.entity.TempStockReturn;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;

/**
 * 贺巍2009 6-5 添加注释
 * 库存查询主窗口 
 * @author hw
 *
 */
public class FmBlsStockQuery extends JInternalFrameBase {

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfWarehouseCode = null;

	private JLabel jLabel2 = null;

	private JTextField tfCustomsCode = null;

	private JLabel jLabel3 = null;

	private JTextField tfContrItem = null;

	private JTextField tfCodeTS = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JTextField tfGname = null;

	private JLabel jLabel6 = null;

	private JTextField tfGmodel = null;

	private JLabel jLabel7 = null;

	private JTextField tfGunit = null;

	private JButton btnQuery = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JTableListModel tableModelStockReturn;

	private JTableListModel tableModelQueryProduct;

	/**
	 * 车次管理 ACTION 接口
	 */
	private StorageBillAction storageBillAction;

	private JScrollPane jScrollPane = null;

	private JTable tbStockReturn = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbQueryProduct = null;

	private JLabel jLabel8 = null;

	private JComboBox cbbEmsNo = null;

	private BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
	.getApplicationContext().getBean("blsInOutStockBillAction");  //  @jve:decl-index=0:
	/**
	 * 构造函数
	 * This method initializes
	 * 
	 */
	public FmBlsStockQuery() {
		super();
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		initialize();
		List list = new ArrayList();
		initStockReturn(list);
		List list2 = new ArrayList();
		initQueryProduct(list2);
	}

	/**
	 * 初始化组建及标题
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(703, 435));
		this.setTitle("查询库存报文");
		this.setContentPane(getJSplitPane());

	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(110);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerSize(5);
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
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(268, 87, 111, 18));
			jLabel7.setBounds(new Rectangle(268, 87, 79, 18));
			jLabel7.setText("商品单位(代码)");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(39, 87, 67, 18));
			jLabel6.setBounds(new Rectangle(83, 87, 67, 18));
			jLabel6.setText("商品型号");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(268, 64, 92, 18));
			jLabel5.setBounds(new Rectangle(268, 64, 79, 18));
			jLabel5.setText("商品名称");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(268, 40, 92, 18));
			jLabel4.setBounds(new Rectangle(268, 40, 79, 18));
			jLabel4.setText("商品编码");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(268, 16, 92, 18));
			jLabel3.setBounds(new Rectangle(268, 16, 79, 18));
			jLabel3.setText("电子帐册序号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(39, 64, 67, 18));
			jLabel2.setBounds(new Rectangle(83, 64, 67, 18));
			jLabel2.setText("关区代码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(39, 40, 67, 18));
			jLabel1.setBounds(new Rectangle(83, 40, 67, 18));
			jLabel1.setText("仓库编码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(39, 16, 67, 18));
			jLabel.setBounds(new Rectangle(83, 16, 67, 18));
			jLabel.setText("电子帐册号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfWarehouseCode(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCustomsCode(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTfContrItem(), null);
			jPanel.add(getTfCodeTS(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfGname(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfGmodel(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfGunit(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getCbbEmsNo(), null);
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
			jPanel1.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfWarehouseCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWarehouseCode() {
		if (tfWarehouseCode == null) {
			tfWarehouseCode = new JTextField();
			tfWarehouseCode.setBounds(new Rectangle(105, 37, 109, 22));
			tfWarehouseCode.setBounds(new Rectangle(149, 37, 93, 22));
		}
		return tfWarehouseCode;
	}

	/**
	 * This method initializes tfCustomsCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsCode() {
		if (tfCustomsCode == null) {
			tfCustomsCode = new JTextField();
			tfCustomsCode.setBounds(new Rectangle(105, 61, 109, 22));
			tfCustomsCode.setBounds(new Rectangle(149, 61, 93, 22));
		}
		return tfCustomsCode;
	}

	/**
	 * This method initializes tfContrItem
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContrItem() {
		if (tfContrItem == null) {
			tfContrItem = new JTextField();
			tfContrItem.setBounds(new Rectangle(379, 13, 109, 22));
			tfContrItem.setBounds(new Rectangle(347, 13, 93, 22));
		}
		return tfContrItem;
	}

	/**
	 * This method initializes tfCodeTS
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCodeTS() {
		if (tfCodeTS == null) {
			tfCodeTS = new JTextField();
			tfCodeTS.setBounds(new Rectangle(379, 37, 109, 22));
			tfCodeTS.setBounds(new Rectangle(347, 37, 93, 22));
		}
		return tfCodeTS;
	}

	/**
	 * This method initializes tfGname
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGname() {
		if (tfGname == null) {
			tfGname = new JTextField();
			tfGname.setBounds(new Rectangle(379, 61, 109, 22));
			tfGname.setBounds(new Rectangle(347, 61, 93, 22));
		}
		return tfGname;
	}

	/**
	 * This method initializes tfGmodel
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGmodel() {
		if (tfGmodel == null) {
			tfGmodel = new JTextField();
			tfGmodel.setBounds(new Rectangle(105, 86, 109, 22));
			tfGmodel.setBounds(new Rectangle(149, 86, 93, 22));
		}
		return tfGmodel;
	}

	/**
	 * This method initializes tfGunit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfGunit() {
		if (tfGunit == null) {
			tfGunit = new JTextField();
			tfGunit.setBounds(new Rectangle(379, 86, 109, 22));
			tfGunit.setBounds(new Rectangle(347, 86, 93, 22));
		}
		return tfGunit;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(511, 82, 67, 26));
			btnQuery.setBounds(new Rectangle(520, 82, 67, 26));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String emsNo = (String)cbbEmsNo.getSelectedItem();
					String contrItem = tfContrItem.getText();
					String warehouseCode = tfWarehouseCode.getText();
					String codeTS = tfCodeTS.getText();
					String customsCode = tfCustomsCode.getText();
					String gName = tfGname.getText();
					String gModel = tfGmodel.getText();
					String gUnit = tfGunit.getText();
					
					List list = storageBillAction.findStockFromHP(new Request(
							CommonVars.getCurrUser()), emsNo, warehouseCode,
							customsCode, contrItem, codeTS, gName, gModel,
							gUnit);
					System.out.println("TempStockReturnList="
							+ ((TempStockReturn) list.get(0)));

					System.out.println("TempStockReturnList.getDescription()="
							+ ((TempStockReturn) list.get(0)).getDescription());
					initStockReturn(list);
					List<TempQueryProduct> queryProductList = new ArrayList<TempQueryProduct>();
					// for (int i = 0; i < list.size(); i++) {
					// TempStockReturn tempStockReturn = (TempStockReturn) list
					// .get(i);
					// List queryProducts = tempStockReturn.getQueryProducts();
					// for (int j = 0; j < queryProducts.size(); j++) {
					// TempQueryProduct tempQueryProduct = (TempQueryProduct)
					// queryProducts
					// .get(j);
					// queryProductList.add(tempQueryProduct);
					// initQueryProduct(queryProductList);
					// }

					// }
				}
			});
		}
		return btnQuery;
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
			jSplitPane1.setDividerLocation(120);
			jSplitPane1.setTopComponent(getJPanel3());
			jSplitPane1.setBottomComponent(getJPanel2());
			jSplitPane1.setDividerSize(5);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			// gridBagConstraints.fill = GridBagConstraints.BOTH;
			// gridBagConstraints.gridy = 0;
			// gridBagConstraints.weightx = 1.0;
			// gridBagConstraints.weighty = 1.0;
			// gridBagConstraints.gridx = 0;
			jLabel8 = new JLabel();
			jLabel8.setText("库存信息");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
			jPanel2.add(jLabel8, BorderLayout.NORTH);
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
			// gridBagConstraints1.fill = GridBagConstraints.BOTH;
			// gridBagConstraints1.gridy = 0;
			// gridBagConstraints1.weightx = 1.0;
			// gridBagConstraints1.weighty = 1.0;
			// gridBagConstraints1.gridx = 0;
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 初始化StockReturn表
	 * 
	 * @param list
	 */
	private void initStockReturn(List list) {
		tableModelStockReturn = new JTableListModel(tbStockReturn, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("服务Handle", "serviceHandle", 100));
						list.add(addColumn("服务状态", "serviceStatus", 100));
						list.add(addColumn("反馈描述", "description", 100));
						list.add(addColumn("电子帐册号", "emsNo", 100));
						list.add(addColumn("仓库编码", "wareHouseCode", 80));
						list.add(addColumn("关区代码", "customsCode", 100));
						// list.add(addColumn("商品库存信息", "queryProducts", 100));
						return list;
					}
				});
	}

	/**
	 * 初始化QueryProduct表
	 * 
	 * @param list
	 */
	private void initQueryProduct(List list) {
		tableModelQueryProduct = new JTableListModel(tbQueryProduct, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("电子帐册序号", "contrItem", 100));
						list.add(addColumn("商品编码", "codeTS", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("商品型号", "model", 100));
						list.add(addColumn("商品单位", "unit", 80));
						list.add(addColumn("库存数量", "amount", 100));
						return list;
					}
				});
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbStockReturn == null) {
			tbStockReturn = new JTable();
			// tbStockReturn.addMouseListener(new java.awt.event.MouseAdapter()
			// {
			// public void mouseClicked(java.awt.event.MouseEvent e) {
			// if (e.getClickCount() == 1) {
			// TempStockReturn temp = (TempStockReturn) tableModelStockReturn
			// .getCurrentRow();
			//						
			// List queryProducts = temp.getQueryProducts();
			//
			//						
			//						
			// if (temp== null) {
			// initQueryProduct(new ArrayList());
			// } else {
			// initQueryProduct(temp.getQueryProducts());
			// }
			// }
			// }
			// });
			tbStockReturn.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbStockReturn
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								TempStockReturn temp = (TempStockReturn) tableModelStockReturn
										.getCurrentRow();

								List queryProducts = temp.getQueryProducts();
								if (temp == null) {
									initQueryProduct(new ArrayList());
								} else {
									initQueryProduct(temp.getQueryProducts());
									System.out.println("dd");
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbStockReturn;
	}

	// System.out.println("temp.getServiceHandle()="+temp.getServiceHandle());
	//	 
	// System.out.println("temp.getServiceStatus()="+temp.getServiceStatus());
	//	
	// System.out.println("temp.getDescription()="+temp.getDescription());
	//	
	// System.out.println("temp.getEmsNo()="+temp.getEmsNo());
	//	
	// System.out.println("temp.getWareHouseCode()="+temp.getWareHouseCode());
	//	
	// System.out.println("temp.getCustomsCode()="+temp.getCustomsCode());
	//	
	// System.out.println("temp.getQueryProducts()=" + temp.getQueryProducts());

	// System.out.println(((TempQueryProduct)queryProducts.get(0)).getModel());
	//	
	// System.out.println(((TempQueryProduct)queryProducts.get(0)).getName());

	// System.out.println(((TempQueryProduct)queryProducts.get(0)).getUnit());
	//	
	// System.out.println(((TempQueryProduct)queryProducts.get(0)).getAmount());
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (tbQueryProduct == null) {
			tbQueryProduct = new JTable();
		}
		return tbQueryProduct;
	}

	private void intoComboBox2(JComboBox jComboBox) {
		List list=this.blsInOutStockBillAction.findAllEmsNo(new Request(
				CommonVars.getCurrUser()));
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
//				this.cbbEmsHead.addItem(((BaseEmsHead) contracts.get(i))
//						.getEmsNo());
				jComboBox.addItem(list.get(i));
			}

		}
//		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
//		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
//				"", "emsNo"));
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
//				"", "emsNo");
		jComboBox.setSelectedIndex(-1);
	}
	
	/**
	 * This method initializes cbbEmsNo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new Rectangle(150, 13, 89, 22));
//			List list=this.blsInOutStockBillAction.findAllEmsNo(new Request(
//					CommonVars.getCurrUser()));
			intoComboBox2(cbbEmsNo);
		}
		return cbbEmsNo;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
