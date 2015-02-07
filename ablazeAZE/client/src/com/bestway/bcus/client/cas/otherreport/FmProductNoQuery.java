package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JSplitPane;

public class FmProductNoQuery extends JFrameBase {

	private JButton btnCancel = null;

	private JCalendarComboBox cbbBegin = null;

	private JCalendarComboBox cbEnd = null;

	private JComboBox cbbMaterialType = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel = null;

	private CasAction casAction = null;

	private boolean isMateriel = true;

	private JLabel jLabel3 = null;

	private JTextField tfProNo = null;

	private JRadioButton rbShowAll = null;

	private List list = new ArrayList(); // @jve:decl-index=0:

	private List showList = new ArrayList(); // @jve:decl-index=0:

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JSplitPane jSplitPane = null;

	private JLabel jLabel4 = null;

	private JComboBox jComboBox = null;
	
	private MaterialManageAction materialManageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmProductNoQuery() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}
	
	private void initUIComponents(){
		List lsScmCoc = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		this.jComboBox.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name", 250);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(755, 515));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJSplitPane());
		this.setTitle("制单号查询报表");
		initTable(new ArrayList());
		rbShowAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (rbShowAll.isSelected()) {
					initTable(list);
				} else {
					initTable(showList);
				}
			}
		});

	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.setBounds(new Rectangle(667, 16, 66, 24));
			btnCancel.setPreferredSize(new Dimension(50, 24));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmProductNoQuery.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbbBegin
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBegin() {
		if (cbbBegin == null) {
			cbbBegin = new JCalendarComboBox();
			cbbBegin.setDate(null);
			cbbBegin.setBounds(new Rectangle(245, 4, 85, 24));
			cbbBegin.setPreferredSize(new Dimension(85, 24));
		}
		return cbbBegin;
	}

	/**
	 * This method initializes cbEnd
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbEnd() {
		if (cbEnd == null) {
			cbEnd = new JCalendarComboBox();
			cbEnd.setPreferredSize(new Dimension(85, 24));
			cbEnd.setBounds(new Rectangle(346, 4, 85, 24));
		}
		return cbEnd;
	}

	/**
	 * This method initializes cbbMaterialType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialType() {
		if (cbbMaterialType == null) {
			cbbMaterialType = new JComboBox();
			cbbMaterialType.addItem("  料  件  ");
			cbbMaterialType.addItem("  成  品  ");
			cbbMaterialType.setFocusable(false);
			cbbMaterialType.setPreferredSize(new Dimension(85, 24));
			cbbMaterialType.setBounds(new Rectangle(73, 4, 85, 24));
		}
		return cbbMaterialType;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(535, 16, 66, 24));
			btnQuery.setPreferredSize(new Dimension(50, 24));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FindThrea().start();
				}
			});
		}
		return btnQuery;
	}

	class FindThrea extends Thread {
		public void run() {
			setAlwaysOnTop(true);
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在查询数据,请稍候...");
			String materialType = null;
			if (cbbMaterialType.getSelectedIndex() == 1) {
				materialType = MaterielType.FINISHED_PRODUCT;
				FmProductNoQuery.this.isMateriel = false;
			} else {
				materialType = MaterielType.MATERIEL;
				FmProductNoQuery.this.isMateriel = true;
			}
			Date begin = cbbBegin.getDate();
			Date end = cbEnd.getDate();
			String proNo = tfProNo.getText();
			showList.clear();
			list = casAction.findBillDetailByMaterielType(new Request(
					CommonVars.getCurrUser()), proNo, materialType, begin, end, (ScmCoc)jComboBox.getSelectedItem());
			for (int k = 0; k < list.size(); k++) {
				TempBillDetail temp = (TempBillDetail) list.get(k);
				if (temp.getResidual() != 0) {
					showList.add(temp);
				}
			}
			if (rbShowAll.isSelected()) {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						FmProductNoQuery.this.initTable(list);
					}
				});
//				FmProductNoQuery.this.initTable(list);
			} else {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						FmProductNoQuery.this.initTable(showList);
					}
				});
//				FmProductNoQuery.this.initTable(showList);
			}
			CommonProgress.closeProgressDialog();
			setAlwaysOnTop(false);
		}
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(601, 16, 66, 24));
			btnPrint.setPreferredSize(new Dimension(50, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tableModel.getList();
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(FmProductNoQuery.this,
								"没有数据可打印!", "提示!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(list);
					InputStream reportStream = FmProductNoQuery.class
							.getResourceAsStream("report/ProductNoBillDetail.jasper");
					JasperPrint jasperPrint = null;
					Properties parameters = new Properties();
					parameters.put("isMateriel", isMateriel);
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("入库时间");
			jLabel1.setBounds(new Rectangle(190, 4, 55, 24));
			jLabel1.setPreferredSize(new Dimension(55, 24));
		}
		return jLabel1;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText(" 到");
			jLabel2.setBounds(new Rectangle(330, 7, 16, 18));
		}
		return jLabel2;
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
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("制单号", "billDetail.productNo", 80));
				list.add(addColumn("客  户", "billDetail.billMaster.scmCoc.name",
						110));
				list.add(addColumn("料号", "billDetail.ptPart", 90));
				list.add(addColumn("商品编码", "billDetail.complex.code", 70));
				list.add(addColumn("商品名称", "billDetail.ptName", 100));
				list.add(addColumn("型号规格", "billDetail.ptSpec", 100));
				list.add(addColumn("入库数量", "inCount", 70));
				list.add(addColumn("出库数量", "outCount", 70));
				list.add(addColumn("结余数量", "residual", 70));

				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter);

	}

	/**
	 * This method initializes tfProNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProNo() {
		if (tfProNo == null) {
			tfProNo = new JTextField();
			tfProNo.setPreferredSize(new Dimension(55, 24));
			tfProNo.setBounds(new Rectangle(73, 33, 85, 24));
		}
		return tfProNo;
	}

	/**
	 * This method initializes rbShowAll
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbShowAll() {
		if (rbShowAll == null) {
			rbShowAll = new JRadioButton();
			rbShowAll.setText("显示全部");
			rbShowAll.setBounds(new Rectangle(442, 18, 85, 24));
			rbShowAll.setPreferredSize(new Dimension(85, 24));
		}
		return rbShowAll;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(175, 33, 70, 24));
			jLabel4.setText("客户/供应商");
			jLabel3 = new JLabel();
			jLabel3.setText("制单号:");
			jLabel3.setBounds(new Rectangle(24, 33, 42, 24));
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(11, 4, 55, 24));
			jLabel.setText("物料类别:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getBtnCancel(), null);
			jPanel1.add(getBtnQuery(), null);
			jPanel1.add(getBtnPrint(), null);
			jPanel1.add(getRbShowAll(), null);
			jPanel1.add(getCbEnd(), null);
			jPanel1.add(getJLabel2(), null);
			jPanel1.add(getCbbBegin(), null);
			jPanel1.add(getJLabel1(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTfProNo(), null);
			jPanel1.add(getCbbMaterialType(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getJComboBox(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new Rectangle(274, 136, 132, 30));
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(60);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJScrollPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(245, 33, 186, 23));
			jComboBox.setPreferredSize(new Dimension(115, 22));
		}
		return jComboBox;
	}
} // @jve:decl-index=0:visual-constraint="10,15"
