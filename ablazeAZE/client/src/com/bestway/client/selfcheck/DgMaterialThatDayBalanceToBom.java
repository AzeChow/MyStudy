package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.CheckBalanceConvertMateriel;
import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.SemiProductInfo;
import com.bestway.bcus.cas.invoice.entity.ToBomTempThatDayBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.selfcheck.DgCurrentThatDayBalanceToBom.CheckableItem;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JRadioButton;

/**
 * 当日结存表（通用）
 * 
 * @author chenSir wss修改
 */
public class DgMaterialThatDayBalanceToBom extends JDialogBase {
	private MaterialManageAction materialManageAction = null;
	private static String SELECT_ALL = "全选"; // @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选"; // @jve:decl-index=0:
	private JPanel jContentPane = null;
	private JPanel top = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JLabel lbFactory = null;
	private JList lsFactory = null;
	private JLabel lbFactoryName = null;
	private JLabel lbDate = null;
	private JTextField tfFactoryName = null;
	private JCalendarComboBox cbDate = null;
	private JButton btnFactoryName = null;
	private JLabel lbFactorySpec = null;
	private JTextField tfFactorySpec = null;
	private JButton btnFactorySpec = null;
	private JLabel lbHsName = null;
	private JTextField tfHsName = null;
	private JButton btnHsName = null;
	private JLabel lbHsSpec = null;
	private JTextField tfHsSpec = null;
	private JButton btnHsSpec = null;
	private JCheckBox cbSetName = null;
	private JCheckBox cbShowNegative = null;
	private JCheckBox cbFactoryByGroup = null;
	private JButton btnSelect = null;
	private JButton btnMimeograph = null;
	private JButton btnClose = null;
	private JTableListModel tableModel = null;
	/**
	 * 在厂料号
	 */
	private List<String> ptParts = new ArrayList(); // @jve:decl-index=0:

	/**
	 * 报关料号
	 */
	private List<String> cusParts = new ArrayList();

	/**
	 * 物料类型
	 */
	private String materielType = null; // @jve:decl-index=0:

	/**
	 * 查询action
	 */
	private CasCheckAction casCheckAction = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel21 = null;
	private JTextField tfPtPart = null;
	private JTextField tfCusPart = null;
	private JTextField tfPtPart1 = null;
	private JTextField tfCusPart1 = null;
	private JButton btnFactoryName1 = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JPanel jPanelMaterial = null;
	private JPanel jPanelProduct = null;
	private JLabel jLabel5 = null;
	private JTextField tfFinishedPtPart = null;
	private JButton btnFinishedPtPart = null;
	private JLabel jLabel51 = null;
	private JTextField tfFinishedPtPart1 = null;
	private JButton btnFinishedPtPart1 = null;
	private JLabel jLabel52 = null;
	private JLabel jLabel53 = null;
	private JLabel jLabel54 = null;
	private JLabel jLabel55 = null;
	private JLabel jLabel56 = null;
	private JLabel jLabel57 = null;
	private JTextField tfFinishedPtName = null;
	private JTextField tfFinishedPtSpec = null;
	private JTextField tfFinishedHsComplex = null;
	private JTextField tfFinishedHsComplex1 = null;
	private JTextField tfFinishedHsName = null;
	private JTextField tfFinishedHsSpec = null;
	private JButton btnFinishedPtName = null;
	private JButton btnFinishedHsComplex = null;
	private JButton btnFinishedPtSpec = null;
	private JButton btnFinishedHsComplex1 = null;
	private JButton btnFinishedHsName = null;
	private JButton btnFinishedHsSpec = null;
	private JButton btnRelation = null;

	private JPopupMenu pmRelation = null; // @jve:decl-index=0:visual-constraint="807,216"
	private JMenuItem miImpExpDetail = null; // @jve:decl-index=0:visual-constraint="811,257"
	private JMenuItem miThatDayBalance = null;
	private JMenuItem miPandianDifferent = null; // @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miThatDayConvert = null; // @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miZhiDanUse = null; // @jve:decl-index=0:visual-constraint="813,382"

	// 关联查询时传递 的参数
	private Date sDate;
	// 成品级
	private String sFHsNo;
	private String sFHsName;
	private String sFHsSpec;
	private String sFPtNo;
	private String sFPtName;
	private String sFPtSpec;
	private JLabel lbDataSoure = null;
	private JRadioButton rbBillDetail = null;
	private JRadioButton rbCheckBlance = null;
	private ButtonGroup group = null;
	private List resultList = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgMaterialThatDayBalanceToBom() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext()
				.getBean("casCheckAction");
		initialize();
		initUI();
	}

	private void initUI() {
		// 初始化仓库
		Vector<Object> vector = new Vector<Object>();
		List listWareSet = materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < listWareSet.size(); i++) {
			if (i == 0) {
				vector.add(new CheckableItemExtra(SELECT_ALL));
			}
			WareSet obj = (WareSet) listWareSet.get(i);
			CheckableItem item = new CheckableItem(obj.getCode(), obj.getName());
			vector.add(item);
		}
		this.lsFactory.setListData(vector);
		tfFinishedPtPart1.setEditable(false);
		tfCusPart1.setEditable(false);
		tfPtPart1.setEditable(false);
		btnFinishedPtPart1.setEnabled(false);
		jButton1.setEnabled(false);
		jButton2.setEnabled(false);
		tfFinishedHsComplex1.setEditable(false);
		btnFinishedHsComplex1.setEnabled(false);
		group = new ButtonGroup();
		group.add(rbBillDetail);
		group.add(rbCheckBlance);
		rbBillDetail.setSelected(true);

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("成品结存折料情况");
		this.setSize(791, 600);
		this.setContentPane(getJContentPane());
		Vector list = new Vector();
		initTableBill(list);

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
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbDataSoure = new JLabel();
			lbDataSoure.setBounds(new Rectangle(181, 12, 91, 24));
			lbDataSoure.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 14));
			lbDataSoure.setForeground(Color.red);
			lbDataSoure.setText("\u6570\u636e\u6765\u6e90\u4e8e\uff1a");
			lbDataSoure.setBackground(Color.red);
			jLabel4 = new JLabel();
			jLabel4.setText("至");
			jLabel4.setBounds(new Rectangle(217, 72, 32, 22));
			jLabel3 = new JLabel();
			jLabel3.setText("至");
			jLabel3.setBounds(new Rectangle(219, 18, 32, 22));
			jLabel21 = new JLabel();
			jLabel21.setText("报关编码");
			jLabel21.setBounds(new Rectangle(250, 72, 65, 22));
			jLabel2 = new JLabel();
			jLabel2.setText("报关编码");
			jLabel2.setBounds(new Rectangle(6, 72, 65, 22));
			jLabel1 = new JLabel();
			jLabel1.setText("工厂料号");
			jLabel1.setBounds(new Rectangle(250, 18, 65, 22));
			jLabel = new JLabel();
			jLabel.setText("工厂料号");
			jLabel.setBounds(new Rectangle(6, 18, 65, 22));
			lbHsSpec = new JLabel();
			lbHsSpec.setText("报关规格");
			lbHsSpec.setBounds(new Rectangle(250, 98, 65, 22));
			lbHsName = new JLabel();
			lbHsName.setText("报关名称");
			lbHsName.setBounds(new Rectangle(6, 98, 65, 22));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setBounds(new Rectangle(250, 45, 65, 22));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setLocation(new Point(16, 255));
			lbDate.setSize(new Dimension(65, 22));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setBounds(new Rectangle(6, 45, 65, 22));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(16, 16, 38, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u4ed3\u8d26\u9009\u9879",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setPreferredSize(new Dimension(1, 310));
			jPanel1.add(lbFactory, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(lbDate, null);
			jPanel1.add(getCbSetName(), null);
			jPanel1.add(getCbShowNegative(), null);
			jPanel1.add(getCbFactoryByGroup(), null);
			jPanel1.add(getBtnSelect(), null);
			jPanel1.add(getBtnMimeograph(), null);
			jPanel1.add(getBtnClose(), null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(getJPanelMaterial(), null);
			jPanel1.add(getJPanelProduct(), null);
			jPanel1.add(getBtnRelation(), null);
			jPanel1.add(lbDataSoure, null);
			jPanel1.add(getRbBillDetail(), null);
			jPanel1.add(getRbCheckBlance(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(15, 32, 157, 210));
			jScrollPane.setViewportView(getLsFactory());
		}
		return jScrollPane;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbDate == null) {
			cbDate = new JCalendarComboBox();
			cbDate.setSize(new Dimension(90, 22));
			cbDate.setLocation(new Point(82, 255));
		}
		return cbDate;
	}

	class CheckableItem {
		private String code;

		private String name;

		private boolean isSelected;

		public CheckableItem(String str, String name) {
			this.code = str;
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name + " (" + code + ")";
		}
	}

	/**
	 * This method initializes lsFactory
	 * 
	 * @return javax.swing.JList
	 */
	class CheckableItemExtra {

		private boolean isSelected;

		private String name;

		public CheckableItemExtra(String name) {
			this.name = name;
			isSelected = false;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	private JList getLsFactory() {
		if (lsFactory == null) {
			lsFactory = new JList();
			lsFactory.setCellRenderer(new CheckListRenderer());
			lsFactory.setBounds(new Rectangle(0, 0, 154, 181));
			lsFactory.setFixedCellHeight(18);
			lsFactory.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = lsFactory.locationToIndex(e.getPoint());
					Object obj = lsFactory.getModel().getElementAt(index);
					if (obj instanceof CheckableItemExtra) {
						CheckableItemExtra item = (CheckableItemExtra) obj;
						item.setSelected(!item.isSelected());
						if (item.isSelected()) {
							item.setName(SELECT_NOT_ALL);
						} else {
							item.setName(SELECT_ALL);
						}
						ListModel model = lsFactory.getModel();
						for (int i = 0; i < model.getSize(); i++) {
							Object o = model.getElementAt(i);
							if (o instanceof CheckableItem) {
								CheckableItem c = (CheckableItem) o;
								c.setSelected(item.isSelected());
							}
						}
						lsFactory.repaint();
					} else if (obj instanceof CheckableItem) {
						CheckableItem item = (CheckableItem) obj;
						item.setSelected(!item.isSelected());
						Rectangle rect = lsFactory.getCellBounds(index, index);
						lsFactory.repaint(rect);
					}
				}
			});
		}
		return lsFactory;
	}

	class CheckListRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			setEnabled(list.isEnabled());
			if (value instanceof CheckableItemExtra) {
				CheckableItemExtra item = (CheckableItemExtra) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(), Font.BOLD, getFont()
						.getSize()));
				setText("  " + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(), Font.PLAIN, getFont()
						.getSize()));
				setText("  " + item.getCode() + " (" + item.getName() + ")");
			}
			return this;
		}
	}

	/**
	 * This method initializes tfFactoryName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactoryName() {
		if (tfFactoryName == null) {
			tfFactoryName = new JTextField();
			tfFactoryName.setBounds(new Rectangle(73, 45, 116, 22));
		}
		return tfFactoryName;
	}

	/**
	 * This method initializes btnFactoryName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFactoryName() {
		if (btnFactoryName == null) {
			btnFactoryName = new JButton();
			btnFactoryName.setText("...");
			btnFactoryName.setBounds(new Rectangle(190, 45, 20, 22));
			btnFactoryName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, MaterielType.MATERIEL,
											new ArrayList(), "ptName");
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFactoryName.setText(m == null ? "" : m
										.getFactoryName());
							}
						}
					});
		}
		return btnFactoryName;
	}

	/**
	 * This method initializes tfFactorySpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFactorySpec() {
		if (tfFactorySpec == null) {
			tfFactorySpec = new JTextField();
			tfFactorySpec.setBounds(new Rectangle(317, 45, 112, 22));
		}
		return tfFactorySpec;
	}

	/**
	 * This method initializes btnFactorySpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFactorySpec() {
		if (btnFactorySpec == null) {
			btnFactorySpec = new JButton();
			btnFactorySpec.setText("...");
			btnFactorySpec.setBounds(new Rectangle(430, 45, 20, 22));
			btnFactorySpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object object = CasQuery.getInstance()
									.findPtSpecFromStatCusNameRelationMt(
											MaterielType.MATERIEL,
											tfFactoryName.getText());
							if (object != null) {
								tfFactorySpec
										.setText((String) ((TempObject) object)
												.getObject());
							}
						}
					});
		}
		return btnFactorySpec;
	}

	/**
	 * This method initializes tfHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsName() {
		if (tfHsName == null) {
			tfHsName = new JTextField();
			tfHsName.setBounds(new Rectangle(73, 98, 116, 22));
		}
		return tfHsName;
	}

	/**
	 * This method initializes btnHsName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsName() {
		if (btnHsName == null) {
			btnHsName = new JButton();
			btnHsName.setText("...");
			btnHsName.setBounds(new Rectangle(190, 98, 20, 22));
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findWuLiaoHsNameFromStatCusNameRelationHsn(
									MaterielType.MATERIEL);
					if (object != null) {
						tfHsName.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnHsName;
	}

	/**
	 * This method initializes tfHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsSpec() {
		if (tfHsSpec == null) {
			tfHsSpec = new JTextField();
			tfHsSpec.setBounds(new Rectangle(317, 98, 112, 22));
		}
		return tfHsSpec;
	}

	/**
	 * This method initializes btnHsSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHsSpec() {
		if (btnHsSpec == null) {
			btnHsSpec = new JButton();
			btnHsSpec.setText("...");
			btnHsSpec.setBounds(new Rectangle(430, 98, 20, 22));
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance()
							.findWuLiaoHsSpecFromStatCusNameRelationHsn(
									MaterielType.MATERIEL, tfHsName.getText());
					if (object != null) {
						tfHsSpec.setText((String) ((TempObject) object)
								.getObject());
					}
				}
			});
		}
		return btnHsSpec;
	}

	/**
	 * This method initializes cbSetName
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSetName() {
		if (cbSetName == null) {
			cbSetName = new JCheckBox();
			cbSetName.setText("报关名称汇总");
			cbSetName.setLocation(new Point(668, 230));
			cbSetName.setSize(new Dimension(107, 21));
		}
		return cbSetName;
	}

	/**
	 * This method initializes cbShowNegative
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbShowNegative() {
		if (cbShowNegative == null) {
			cbShowNegative = new JCheckBox();
			cbShowNegative.setText("显示负数结存");
			cbShowNegative.setLocation(new Point(668, 260));
			cbShowNegative.setSize(new Dimension(102, 21));
			cbShowNegative
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							showLessthanZero(cbShowNegative.isSelected());

						}
					});
		}
		return cbShowNegative;
	}

	protected void showLessthanZero(boolean isShow) {
		if (resultList == null || resultList.size() == 0) {
			initTable(new ArrayList());
			return;
		}
		if (isShow) {
			List tempList = new ArrayList();
			if (rbBillDetail.isSelected()) { // 单据中心
				for (int i = 0; i < resultList.size(); i++) {
					ToBomTempThatDayBalance infoTemp = (ToBomTempThatDayBalance) resultList
							.get(i);
					if ((infoTemp.getProduct().getPtAmount() == null ? 0.0
							: infoTemp.getProduct().getPtAmount()) < 0
							|| (infoTemp.getProduct().getPtAmount() == null ? 0.0
									: infoTemp.getProduct().getPtAmount()) < 0) {
						tempList.add(infoTemp);
					}
				}
				initTableBill(tempList);
				if (cbSetName.isSelected()){
					if(cbFactoryByGroup.isSelected())
						combineTable(18);
					else
						combineTable(17);
				}
				else
					combineTableBill();
			}
		} else {
			if (!rbCheckBlance.isSelected()) {
				initTableBill(resultList);
				if (cbSetName.isSelected())
					combineTable(18);
				else
					combineTableBill();
			} else {
				initTable(resultList);
				if (cbSetName.isSelected())
					combineTable(14);

			}
		}

	}

	/**
	 * This method initializes cbFactoryByGroup
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbFactoryByGroup() {
		if (cbFactoryByGroup == null) {
			cbFactoryByGroup = new JCheckBox();
			cbFactoryByGroup.setText("按仓库分组");
			cbFactoryByGroup.setLocation(new Point(668, 200));
			cbFactoryByGroup.setSize(new Dimension(101, 21));
		}
		return cbFactoryByGroup;
	}

	/**
	 * This method initializes btnSelect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JButton();
			btnSelect.setText("查询");
			btnSelect.setSize(new Dimension(87, 22));
			btnSelect.setLocation(new Point(674, 30));
			btnSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					queryData();
				}
			});
		}
		return btnSelect;
	}

	/**
	 * 查询
	 */
	public void queryData() {
		// 查询
		// 组织查询条件
		List<Condition> finishedConditions = getFinishedCondition();
		List<Condition> materialConditions = getMaterialCondition();
		List<Condition> finishedConditionsCheckBalance = getCheckBalanceCondition();
		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(getMaterielType());
		// 执行查询线程
		new Find(finishedConditions, materialConditions,
				finishedConditionsCheckBalance).execute();

	}

	private List<Condition> getCheckBalanceCondition() {
		List<Condition> finishedConditions = new ArrayList<Condition>();
		// 库存类别
		finishedConditions.add(new Condition("and", null, "kcType", "=", "1",
				null));
		// 生效日期
		if (cbDate.getDate() != null) { // 开始日期
			Date endDate = CommonVars.getEndDate(cbDate.getDate());
			Date beginDate = CommonVars.getBeginDate(cbDate.getDate());
			System.out.println("date=" + endDate);
			finishedConditions.add(new Condition("and", null, "checkDate",
					">=", beginDate, null));
			finishedConditions.add(new Condition("and", null, "checkDate",
					"<=", endDate, null));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfFinishedPtPart.getText().trim().equals("")
				&& tfFinishedPtPart1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "ptNo", "=",
					tfFinishedPtPart.getText(), null));
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfFinishedPtPart.getText().trim().equals("")
				&& !tfFinishedPtPart1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", "(", "ptNo", ">=",
					tfFinishedPtPart.getText(), null));
			finishedConditions.add(new Condition("and", null, "ptNo", "<=",
					tfFinishedPtPart1.getText(), ")"));
		}
		// 工厂名称
		if (!tfFinishedPtName.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "ptName", "=",
					tfFinishedPtName.getText(), null));
		}
		// 工厂规格
		if (!tfFinishedPtSpec.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "ptSpec", "=",
					tfFinishedPtSpec.getText(), null));
		}
		// 仓库
		List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
		List<String> checkableCodeList = new ArrayList<String>();
		for (int j = 0; j < lsFactory.getModel().getSize(); j++) {
			Object value = lsFactory.getModel().getElementAt(j);
			if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				if (item.isSelected) {
					checkableItemList.add(item);
					checkableCodeList.add(item.getCode());
				}
			}
		}
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckableItem item = checkableItemList.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(", "wareSet.code", "=", item
						.getCode().trim(), null);
			} else {
				condition = new Condition("or", null, "wareSet.code", "=", item
						.getCode().trim(), null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			finishedConditions.add(condition);
		}
		// 报关编码
		if (!tfFinishedHsComplex.getText().trim().equals("")
				&& tfFinishedHsComplex1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "complex.code",
					"=", tfFinishedHsComplex.getText(), null));
		} else if (!tfFinishedHsComplex.getText().trim().equals("")
				&& !tfFinishedHsComplex1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", "(", "complex.code",
					">=", tfFinishedHsComplex.getText(), null));
			finishedConditions.add(new Condition("and", null, "complex.code",
					"<=", tfFinishedHsComplex1.getText(), ")"));
		}
		// 报关名称
		if (!tfFinishedHsName.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "Name", "=",
					tfFinishedHsName.getText(), null));
		}
		// 报关规格
		if (!tfFinishedHsSpec.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "Spec", "=",
					tfFinishedHsSpec.getText(), null));
		}

		return finishedConditions;
	}

	private List<Condition> getMaterialCondition() {
		List MaterialConditions = new ArrayList<Condition>();
		if (rbBillDetail.isSelected()) {
			// 工厂料号不等于空,结束料号为空时
			if (!tfPtPart.getText().trim().equals("")
					&& tfPtPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"materiel.ptNo", "=", tfPtPart.getText(), null));
				// casCondition.setStartPtPart( tfPtPart.getText().trim());
			} else // 工厂料号不等于空,结束料号不为空时
			if (!tfPtPart.getText().trim().equals("")
					&& !tfPtPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", "(",
						"materiel.ptNo", ">=", tfPtPart.getText(), null));
				MaterialConditions.add(new Condition("and", null,
						"materiel.ptNo", "<=", tfPtPart1.getText(), ")"));
				// casCondition.setStartPtPart( tfPtPart.getText().trim());
				// casCondition.setEndPtPart( tfPtPart1.getText().trim());
			}
			// 工厂名称
			if (!tfFactoryName.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"materiel.factoryName", "=", tfFactoryName.getText(),
						null));
				// casCondition.setPtName( tfFactoryName.getText().trim());
			}
			// 工厂规格
			if (!tfFactorySpec.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"materiel.factorySpec", "=", tfFactorySpec.getText(),
						null));
				// casCondition.setPtSpec( tfFactorySpec.getText().trim());
			}
			// 报关料号不等于空,结束料号为空时
			if (!tfCusPart.getText().trim().equals("")
					&& tfCusPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"statCusNameRelationHsn.complex.code", "=", tfCusPart
								.getText(), null));
				// casCondition.setStartHsPart( tfCusPart.getText().trim());
			}
			// 报关料号不等于空,结束料号不为空时
			else if (!tfCusPart.getText().trim().equals("")
					&& !tfCusPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", "(",
						"statCusNameRelationHsn.complex.code", ">=", tfCusPart
								.getText(), null));
				MaterialConditions.add(new Condition("and", null,
						"statCusNameRelationHsn.complex.code", "<=", tfCusPart1
								.getText(), ")"));
				// casCondition.setStartHsPart( tfCusPart.getText().trim());
				// casCondition.setEndHsPart( tfCusPart1.getText().trim());
			}
			// 报关名称
			if (!tfHsName.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"statCusNameRelationHsn.cusName", "=", tfHsName
								.getText(), null));
				// casCondition.setHsName( tfHsName.getText().trim());
			}
			// 报关规格
			if (!tfHsSpec.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"statCusNameRelationHsn.cusSpec", "=", tfHsSpec
								.getText(), null));
				// casCondition.setHsSpec( tfHsSpec.getText().trim());
			}
		} else {// 盘点导入

			// 工厂料号不等于空,结束料号为空时
			if (!tfPtPart.getText().trim().equals("")
					&& tfPtPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null, "ptNo", "=",
						tfPtPart.getText(), null));
				// casCondition.setStartPtPart( tfPtPart.getText().trim());
			} else // 工厂料号不等于空,结束料号不为空时
			if (!tfPtPart.getText().trim().equals("")
					&& !tfPtPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", "(", "ptNo", ">=",
						tfPtPart.getText(), null));
				MaterialConditions.add(new Condition("and", null, "ptNo", "<=",
						tfPtPart1.getText(), ")"));
				// casCondition.setStartPtPart( tfPtPart.getText().trim());
				// casCondition.setEndPtPart( tfPtPart1.getText().trim());
			}
			// 工厂名称
			if (!tfFactoryName.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null, "ptName",
						"=", tfFactoryName.getText(), null));
				// casCondition.setPtName( tfFactoryName.getText().trim());
			}
			// 工厂规格
			if (!tfFactorySpec.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null, "ptSpec",
						"=", tfFactorySpec.getText(), null));
				// casCondition.setPtSpec( tfFactorySpec.getText().trim());
			}
			// 报关料号不等于空,结束料号为空时
			if (!tfCusPart.getText().trim().equals("")
					&& tfCusPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null,
						"complex.code", "=", tfCusPart.getText(), null));
				// casCondition.setStartHsPart( tfCusPart.getText().trim());
			}
			// 报关料号不等于空,结束料号不为空时
			else if (!tfCusPart.getText().trim().equals("")
					&& !tfCusPart1.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", "(",
						"complex.code", ">=", tfCusPart.getText(), null));
				MaterialConditions.add(new Condition("and", null,
						"complex.code", "<=", tfCusPart1.getText(), ")"));
				// casCondition.setStartHsPart( tfCusPart.getText().trim());
				// casCondition.setEndHsPart( tfCusPart1.getText().trim());
			}
			// 报关名称
			if (!tfHsName.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null, "name", "=",
						tfHsName.getText(), null));
				// casCondition.setHsName( tfHsName.getText().trim());
			}
			// 报关规格
			if (!tfHsSpec.getText().trim().equals("")) {
				MaterialConditions.add(new Condition("and", null, "spec", "=",
						tfHsSpec.getText(), null));
			}
		}
		return MaterialConditions;
	}

	private List<Condition> getFinishedCondition() {
		List finishedConditions = new ArrayList<Condition>();
		finishedConditions.add(new Condition("and", null, "billMaster.isValid",
				"=", new Boolean(true), null));
		// 生效日期(从当年开始至现在)
		if (cbDate.getDate() != null) { // 开始日期
			Date endDate = CommonVars.getEndDate(cbDate.getDate());

			Date startDate = CommonVars.getBeginDate(new Date(
					endDate.getYear(), 0, 1));
			finishedConditions.add(new Condition("and", null,
					"billMaster.validDate", ">=", startDate, null));
			finishedConditions.add(new Condition("and", null,
					"billMaster.validDate", "<=", endDate, null));
			// .setDate(cbDate.getDate());
			// DateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			// System.out.println(fm.format(startDate));
			// System.out.println(fm.format(date));
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfFinishedPtPart.getText().trim().equals("")
				&& tfFinishedPtPart1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "ptPart", "=",
					tfFinishedPtPart.getText(), null));
			// casCondition.setStartPtPart( tfPtPart.getText().trim());
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfFinishedPtPart.getText().trim().equals("")
				&& !tfFinishedPtPart1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", "(", "ptPart", ">=",
					tfFinishedPtPart.getText(), null));
			finishedConditions.add(new Condition("and", null, "ptPart", "<=",
					tfFinishedPtPart1.getText(), ")"));
			// casCondition.setStartPtPart( tfPtPart.getText().trim());
			// casCondition.setEndPtPart( tfPtPart1.getText().trim());
		}
		// 工厂名称
		if (!tfFinishedPtName.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "ptName", "=",
					tfFinishedPtName.getText(), null));
			// casCondition.setPtName( tfFactoryName.getText().trim());
		}
		// 工厂规格
		if (!tfFinishedPtSpec.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "ptSpec", "=",
					tfFinishedPtSpec.getText(), null));
			// casCondition.setPtSpec( tfFactorySpec.getText().trim());
		}
		// 报关料号不等于空,结束料号为空时
		if (!tfFinishedHsComplex.getText().trim().equals("")
				&& tfFinishedHsComplex1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "complex.code",
					"=", tfFinishedHsComplex.getText(), null));
			// casCondition.setStartHsPart( tfCusPart.getText().trim());
		}
		// 报关料号不等于空,结束料号不为空时
		else if (!tfFinishedHsComplex.getText().trim().equals("")
				&& !tfFinishedHsComplex1.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", "(", "complex.code",
					">=", tfFinishedHsComplex.getText(), null));
			finishedConditions.add(new Condition("and", null, "complex.code",
					"<=", tfFinishedHsComplex1.getText(), ")"));
			// casCondition.setStartHsPart( tfCusPart.getText().trim());
			// casCondition.setEndHsPart( tfCusPart1.getText().trim());
		}
		// 报关名称
		if (!tfFinishedHsName.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "hsName", "=",
					tfFinishedHsName.getText(), null));
			// casCondition.setHsName( tfHsName.getText().trim());
		}
		// 报关规格
		if (!tfFinishedHsSpec.getText().trim().equals("")) {
			finishedConditions.add(new Condition("and", null, "hsSpec", "=",
					tfFinishedHsSpec.getText(), null));
			// casCondition.setHsSpec( tfHsSpec.getText().trim());
		}
		// 仓库
		List<CheckableItem> checkableItemList = new ArrayList<CheckableItem>();
		List<String> checkableCodeList = new ArrayList<String>();
		for (int j = 0; j < lsFactory.getModel().getSize(); j++) {
			Object value = lsFactory.getModel().getElementAt(j);
			if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				if (item.isSelected) {
					checkableItemList.add(item);
					checkableCodeList.add(item.getCode());
				}
			}
		}
		// casCondition.setWareSetCodes(checkableCodeList);
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckableItem item = checkableItemList.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(", "wareSet.code", "=", item
						.getCode().trim(), null);
			} else {
				condition = new Condition("or", null, "wareSet.code", "=", item
						.getCode().trim(), null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			finishedConditions.add(condition);
		}

		return finishedConditions;
	}

	class Find extends SwingWorker {
		// 成品查询条件
		List<Condition> finishedConditions = null;
		// 原料查询条件
		List<Condition> materialConditions = null;
		// 盘点导入条件
		List<Condition> finishedConditionsBalance = null;
		// 排序
		String orderBy = null;
		// 查询目标表
		String billDetail = null;
		// 查询条件
		CasCondition casCondition = null;

		public Find(List<Condition> finishedConditions,
				List<Condition> materialConditions,
				List<Condition> finishedConditionsBalance) {
			this.finishedConditions = finishedConditions;
			this.finishedConditionsBalance = finishedConditionsBalance;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
			this.materialConditions = materialConditions;
			// this.casCondition = casCondition;
		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算
			// 查询返回结果
			List list = null;
			// 查询
			CommonProgress
					.showProgressDialog(DgMaterialThatDayBalanceToBom.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			// 原来的成品当日结存数据去折料
			list = casCheckAction.getCurrentBillDtailToBom(new Request(
					CommonVars.getCurrUser()), finishedConditions,
					materialConditions, finishedConditionsBalance, rbBillDetail
							.isSelected(), "", MaterielType.FINISHED_PRODUCT,
					cbSetName.isSelected(), cbShowNegative.isSelected(),
					cbFactoryByGroup.isSelected());
			return list;
		}

		@Override
		protected void done() {// 更新视图

			try {
				resultList = (List) this.get();
			} catch (Exception e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(
						DgMaterialThatDayBalanceToBom.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			}
			if (resultList == null) {
				resultList = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			if (!rbCheckBlance.isSelected()) {
				initTableBill(resultList);
				if (cbSetName.isSelected()){
					if(cbFactoryByGroup.isSelected())
						combineTable(18);
					else
						combineTable(17);
				}
				else
					combineTableBill();
			} else {
				initTable(resultList);
				if (cbSetName.isSelected()){
					if(cbFactoryByGroup.isSelected())
						combineTable(14);
					else
						combineTable(13);
				}
				else
					combineTableCheckBalance();

			}
		}
	}

	/**
	 * This method initializes btnMimeograph
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMimeograph() {
		if (btnMimeograph == null) {
			btnMimeograph = new JButton();
			btnMimeograph.setText("打印");
			btnMimeograph.setLocation(new Point(674, 70));
			btnMimeograph.setSize(new Dimension(87, 22));
			btnMimeograph
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								List list = tableModel.getCurrentRowsOrder();
								CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
										list);
								Map<String, Object> parameters = new HashMap<String, Object>();
								String str = "";
								if (rbBillDetail.isSelected()) {
									if (cbSetName.isSelected()) {// 2.只选了报关名称汇总
										System.out.println("hcl nameset");
										str = "report/ThatDayConvertGroupByComplex.jasper";
									} else {
										str = "report/ThatDayConvert.jasper";
									}
									parameters.put("type", "数据来自：单据中心");
								} else {
									if (cbSetName.isSelected()) {// 2.只选了报关名称汇总
										System.out.println("hcl nameset");
										str = "report/CheckBalanceThatDayConvertGroupByComplex.jasper";
									} else {
										str = "report/CheckBalanceThatDayConvert.jasper";
									}
									parameters.put("type", "数据来自：盘点导入");
									parameters.put("bomType", "成品");
								}
								InputStream masterReportStream = DgMaterialThatDayBalanceToBom.class
										.getResourceAsStream(str);

								String companyCode = ((Company) CommonVars
										.getCurrUser().getCompany()).getCode();
								String companyName = ((Company) CommonVars
										.getCurrUser().getCompany()).getName();
								parameters.put("companyName", companyName);
								parameters.put("companyCode", companyCode);
								parameters.put("title", getTitle());
								parameters
										.put("size", new Integer(list.size()));
								parameters.put("date", cbDate.getDate());
								System.out.println("ds=" + ds);
								System.out.println("parameters=" + parameters);
								System.out.println("masterReportStream="
										+ masterReportStream);

								JasperPrint jasperPrint = JasperFillManager
										.fillReport(masterReportStream,
												parameters, ds);
								DgReportViewer viewer = new DgReportViewer(
										jasperPrint);
								viewer.setVisible(true);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
		}
		return btnMimeograph;
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
			btnClose.setLocation(new Point(674, 150));
			btnClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnClose.setSize(new Dimension(87, 22));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMaterialThatDayBalanceToBom.this.dispose();
				}
			});

		}
		return btnClose;
	}

	private void initTableBill(final List list) {
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				list, new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("工厂料号", "product.ptPart", 100));
						list.add(addColumn("工厂名称", "product.ptName", 100));
						list.add(addColumn("工厂规格", "product.ptSpec", 100));
						list.add(addColumn("工厂单位", "product.ptUnit.name", 60));
						list.add(addColumn("工厂数量", "product.ptAmount", 60));
						list.add(addColumn("报关名称", "product.hsName", 100));
						list.add(addColumn("报关规格", "product.hsSpec", 100));
						list.add(addColumn("报关单位", "product.hsUnit.name", 60));
						list.add(addColumn("折算系数", "product.unitConvert", 60));
						list.add(addColumn("报关数量", "product.hsAmount", 60));
						if(cbFactoryByGroup.isSelected())
						list.add(addColumn("仓库名称", "product.wareSet.name", 60));
						// bom
						list.add(addColumn("工厂料号", "ptPart", 100));
						list.add(addColumn("工厂单位", "ptUnit.name", 60));
						list.add(addColumn("单耗", "unitWaste", 60));
						list.add(addColumn("损耗", "waste", 60));
						list.add(addColumn("单项用量", "unitUsed", 60));
						list.add(addColumn("库存数量", "wareAmount", 60));
						list.add(addColumn("报关名称", "hsName", 100));
						list.add(addColumn("报关规格", "hsSpec", 100));
						list.add(addColumn("报关单位", "hsUnit.name", 60));
						list.add(addColumn("折算系数", "unitConvert", 60));
						list.add(addColumn("报关数量", "hsAmount", 60));
						if (cbSetName.isSelected())
							list.add(addColumn("报关数量", "hsAllAmount", 80));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_product = new ColumnGroup("成品");
		g_product.add(cm.getColumn(0));
		g_product.add(cm.getColumn(1));
		g_product.add(cm.getColumn(2));
		g_product.add(cm.getColumn(3));
		g_product.add(cm.getColumn(4));
		g_product.add(cm.getColumn(5));
		g_product.add(cm.getColumn(6));
		g_product.add(cm.getColumn(7));
		g_product.add(cm.getColumn(8));
		g_product.add(cm.getColumn(9));
		g_product.add(cm.getColumn(10));
		int i=11;
		if(cbFactoryByGroup.isSelected()){
		g_product.add(cm.getColumn(i++));
		}
		ColumnGroup g_bom = new ColumnGroup("BOM");
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));

		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_product);
		header.addColumnGroup(g_bom);
		jTable.repaint();
	}

	private void initTable(final List list) {
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				list, new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						// 折前信息
						// list.add(addColumn("物料类型", "materielType", 100));
						list.add(addColumn("工厂料号", "fatherCheckBalance.ptNo",
								100));
						list.add(addColumn("工厂名称", "fatherCheckBalance.ptName",
								100));
						list.add(addColumn("工厂规格", "fatherCheckBalance.ptSpec",
								100));
						list.add(addColumn("工厂单位",
								"fatherCheckBalance.ptUnit.name", 60));
						list.add(addColumn("工厂数量",
								"fatherCheckBalance.checkAmount", 60));
						list.add(addColumn("折算系数",
								"fatherCheckBalance.unitConvert", 60));
						if(cbFactoryByGroup.isSelected())
						list.add(addColumn("仓库名称",
								"fatherCheckBalance.wareSet.name", 60));
						// bom
						list.add(addColumn("工厂料号", "ptNo", 100));
						list.add(addColumn("工厂单位", "ptUnitName", 60));
						list.add(addColumn("单耗", "unitWaste", 60));
						list.add(addColumn("损耗", "waste", 60));
						list.add(addColumn("单项用量", "unitUsed", 60));
						list.add(addColumn("库存数量", "checkAmount", 60));
						list.add(addColumn("报关名称", "name", 100));
						list.add(addColumn("报关规格", "spec", 100));
						list.add(addColumn("报关单位", "hsUnit.name", 60));
						list.add(addColumn("折算系数", "unitConvert", 60));
						list.add(addColumn("报关数量", "hsAmount", 60));
						if (cbSetName.isSelected())
							list.add(addColumn("报关数量", "tempDouble", 80));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup g_product = new ColumnGroup("成品");
		g_product.add(cm.getColumn(0));
		g_product.add(cm.getColumn(1));
		g_product.add(cm.getColumn(2));
		g_product.add(cm.getColumn(3));
		g_product.add(cm.getColumn(4));
		g_product.add(cm.getColumn(5));
		g_product.add(cm.getColumn(6));
		int i=7;
		if(cbFactoryByGroup.isSelected()){
		g_product.add(cm.getColumn(i++));
		}
		ColumnGroup g_bom = new ColumnGroup("BOM");
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		g_bom.add(cm.getColumn(i++));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.setColumnModel(jTable.getColumnModel());
		header.addColumnGroup(g_product);
		header.addColumnGroup(g_bom);
		jTable.repaint();
	}

	/**
	 * 合并料件单元格
	 */
	/**
	 * 合并料件单元格
	 */
	public void combineTable(int i) {
		((MultiSpanCellTable) jTable).splitRows2(new int[] { i, i + 1, i + 2,
				i + 5 });
		((MultiSpanCellTable) jTable).combineRowsByIndeies(new int[] { i,
				i + 1, i + 2, i + 5 }, new int[] { i, i + 1, i + 2, i + 5 });
	}

	/**
	 * 合并成品单元格-单据中心
	 */
	public void combineTableBill() {
		((MultiSpanCellTable) jTable).splitRows2(new int[] { 1, 2, 3, 4, 5, 6,
				7, 8, 9, 10 });
		((MultiSpanCellTable) jTable).combineRowsByIndeies(new int[] { 1, 2, 3,
				4, 5, 6, 7, 8, 9, 10 }, new int[] { 1, 2, 3, 4, 5, 6, 7, 8,
				9, 10 });
	}

	/**
	 * 合并成品单元格-盘点导入
	 */
	public void combineTableCheckBalance() {
		((MultiSpanCellTable) jTable).splitRows2(new int[] { 1, 2, 3, 4, 5, 6 });
		((MultiSpanCellTable) jTable).combineRowsByIndeies(new int[] { 1, 2, 3,
				4, 5, 6 }, new int[] { 1, 2, 3, 4, 5, 6 });
	}

	/**
	 * @return Returns the intOutFlat.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * 获取选择条件类型
	 * 
	 * @return
	 */
	public String getImgExgType() {
		if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			return MaterielType.FINISHED_PRODUCT;
		}
		return MaterielType.MATERIEL;
	}

	/**
	 * @param intOutFlat
	 *            The intOutFlat to set.
	 */
	public void setMaterielType(String intOutFlat) {
		this.materielType = intOutFlat;
	}

	/**
	 * This method initializes tfPtPart
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtPart() {
		if (tfPtPart == null) {
			tfPtPart = new JTextField();
			tfPtPart.setBounds(new Rectangle(73, 18, 116, 22));
			tfPtPart.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfPtPart.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfPtPart1.setEditable(true);
						jButton1.setEnabled(true);
					} else {
						tfPtPart1.setEditable(false);
						jButton1.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtPart;
	}

	/**
	 * This method initializes tfCusPart
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCusPart() {
		if (tfCusPart == null) {
			tfCusPart = new JTextField();
			tfCusPart.setBounds(new Rectangle(73, 72, 116, 22));
		}
		return tfCusPart;
	}

	/**
	 * This method initializes tfPtPart1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPtPart1() {
		if (tfPtPart1 == null) {
			tfPtPart1 = new JTextField();
			tfPtPart1.setBounds(new Rectangle(317, 18, 112, 22));
			tfCusPart.getDocument().addDocumentListener(new DocumentListener() {
				private void setState() {
					String editAfterQueryValue = tfCusPart.getText().trim();
					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
						tfCusPart1.setEditable(true);
						jButton2.setEnabled(true);
					} else {
						tfCusPart1.setText(null);
						tfCusPart1.setEditable(false);
						jButton2.setEnabled(false);
					}
				}

				public void insertUpdate(DocumentEvent e) {
					setState();
				}

				public void removeUpdate(DocumentEvent e) {
					setState();
				}

				public void changedUpdate(DocumentEvent e) {
					setState();
				}

			});
		}
		return tfPtPart1;
	}

	/**
	 * This method initializes tfCusPart1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCusPart1() {
		if (tfCusPart1 == null) {
			tfCusPart1 = new JTextField();
			tfCusPart1.setBounds(new Rectangle(317, 72, 112, 22));
		}
		return tfCusPart1;
	}

	/**
	 * This method initializes btnFactoryName1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFactoryName1() {
		if (btnFactoryName1 == null) {
			btnFactoryName1 = new JButton();
			btnFactoryName1.setText("...");
			btnFactoryName1.setBounds(new Rectangle(190, 18, 20, 22));
			btnFactoryName1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							List<TempObject> list = CasQuery.getInstance()
									.getWuLiaoProductptNo(false,
											MaterielType.MATERIEL,
											new ArrayList());
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfPtPart.setText(m == null ? "" : m.getPtNo());
							}
						}
					});
		}
		return btnFactoryName1;
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
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new MultiSpanCellTable() {
				protected JTableHeader createDefaultTableHeader() {
					return new GroupableTableHeader(columnModel);
				}
			};
		}
		return jTable;
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
			jButton.setBounds(new Rectangle(190, 72, 20, 22));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance().findMertailHs(
							MaterielType.MATERIEL);
					if (object != null) {
						tfCusPart.setText((String) ((TempObject) object)
								.getObject3());

					}
				}
			});
		}
		return jButton;
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
			// jButton1.setEnabled(false);
			jButton1.setBounds(new Rectangle(430, 18, 20, 22));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List<TempObject> list = CasQuery.getInstance()
							.getWuLiaoProductptNo(false, MaterielType.MATERIEL,
									new ArrayList());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel) sm.getObject();
						tfPtPart1.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("...");
			// jButton2.setEnabled(false);
			jButton2.setBounds(new Rectangle(430, 72, 20, 22));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object object = CasQuery.getInstance().findMertailHs(
							MaterielType.MATERIEL);
					if (object != null) {
						tfCusPart1.setText((String) ((TempObject) object)
								.getObject3());

					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jPanelMaterial
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelMaterial() {
		if (jPanelMaterial == null) {
			jPanelMaterial = new JPanel();
			jPanelMaterial.setLayout(null);
			jPanelMaterial.setBounds(new Rectangle(181, 179, 486, 127));
			jPanelMaterial.setBorder(BorderFactory.createTitledBorder(null,
					"\u539f\u6750\u6599\u67e5\u8be2",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font(
							"\u65b0\u5b8b\u4f53", Font.BOLD, 14), Color.blue));
			jPanelMaterial.setFont(new Font("Dialog", Font.PLAIN, 12));
			jPanelMaterial.add(lbHsName, null);
			jPanelMaterial.add(getTfHsName(), null);
			jPanelMaterial.add(getBtnHsName(), null);
			jPanelMaterial.add(lbHsSpec, null);
			jPanelMaterial.add(getTfHsSpec(), null);
			jPanelMaterial.add(getBtnHsSpec(), null);
			jPanelMaterial.add(jLabel2, null);
			jPanelMaterial.add(getTfCusPart(), null);
			jPanelMaterial.add(getJButton(), null);
			jPanelMaterial.add(jLabel4, null);
			jPanelMaterial.add(jLabel21, null);
			jPanelMaterial.add(getTfCusPart1(), null);
			jPanelMaterial.add(getJButton2(), null);
			jPanelMaterial.add(lbFactoryName, null);
			jPanelMaterial.add(getTfFactoryName(), null);
			jPanelMaterial.add(getBtnFactoryName(), null);
			jPanelMaterial.add(lbFactorySpec, null);
			jPanelMaterial.add(getTfFactorySpec(), null);
			jPanelMaterial.add(getBtnFactorySpec(), null);
			jPanelMaterial.add(jLabel, null);
			jPanelMaterial.add(getTfPtPart(), null);
			jPanelMaterial.add(getBtnFactoryName1(), null);
			jPanelMaterial.add(jLabel3, null);
			jPanelMaterial.add(jLabel1, null);
			jPanelMaterial.add(getTfPtPart1(), null);
			jPanelMaterial.add(getJButton1(), null);
		}
		return jPanelMaterial;
	}

	/**
	 * This method initializes jPanelProduct
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelProduct() {
		if (jPanelProduct == null) {
			jLabel57 = new JLabel();
			jLabel57.setBounds(new Rectangle(250, 103, 65, 22));
			jLabel57.setText("报关规格");
			jLabel56 = new JLabel();
			jLabel56.setBounds(new Rectangle(6, 103, 65, 22));
			jLabel56.setText("报关名称");
			jLabel55 = new JLabel();
			jLabel55.setBounds(new Rectangle(250, 78, 65, 22));
			jLabel55.setText("报关编码");
			jLabel54 = new JLabel();
			jLabel54.setBounds(new Rectangle(6, 78, 65, 22));
			jLabel54.setText("报关编码");
			jLabel53 = new JLabel();
			jLabel53.setBounds(new Rectangle(250, 50, 65, 22));
			jLabel53.setText("工厂规格");
			jLabel52 = new JLabel();
			jLabel52.setBounds(new Rectangle(6, 50, 65, 22));
			jLabel52.setText("工厂名称");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(250, 24, 65, 22));
			jLabel51.setText("工厂料号");
			jLabel5 = new JLabel();
			jLabel5.setText("工厂料号");
			jLabel5.setBounds(new Rectangle(6, 24, 65, 22));
			jPanelProduct = new JPanel();
			jPanelProduct.setLayout(null);
			jPanelProduct.setBounds(new Rectangle(180, 42, 486, 130));
			jPanelProduct.setBorder(BorderFactory.createTitledBorder(null,
					"\u6210\u54c1\u67e5\u8be2\u9009\u9879",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font(
							"\u65b0\u5b8b\u4f53", Font.BOLD, 14), Color.blue));
			jPanelProduct.add(jLabel5, null);
			jPanelProduct.add(getTfFinishedPtPart(), null);
			jPanelProduct.add(getBtnFinishedPtPart(), null);
			jPanelProduct.add(jLabel51, null);
			jPanelProduct.add(getTfFinishedPtPart1(), null);
			jPanelProduct.add(getBtnFinishedPtPart1(), null);
			jPanelProduct.add(jLabel52, null);
			jPanelProduct.add(jLabel53, null);
			jPanelProduct.add(jLabel54, null);
			jPanelProduct.add(jLabel55, null);
			jPanelProduct.add(jLabel56, null);
			jPanelProduct.add(jLabel57, null);
			jPanelProduct.add(getTfFinishedPtName(), null);
			jPanelProduct.add(getTfFinishedPtSpec(), null);
			jPanelProduct.add(getTfFinishedHsComplex(), null);
			jPanelProduct.add(getTfFinishedHsComplex1(), null);
			jPanelProduct.add(getTfFinishedHsName(), null);
			jPanelProduct.add(getTfFinishedHsSpec(), null);
			jPanelProduct.add(getBtnFinishedPtName(), null);
			jPanelProduct.add(getBtnFinishedHsComplex(), null);
			jPanelProduct.add(getBtnFinishedPtSpec(), null);
			jPanelProduct.add(getBtnFinishedHsComplex1(), null);
			jPanelProduct.add(getBtnFinishedHsName(), null);
			jPanelProduct.add(getBtnFinishedHsSpec(), null);
		}
		return jPanelProduct;
	}

	/**
	 * This method initializes tfFinishedPtPart
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedPtPart() {
		if (tfFinishedPtPart == null) {
			tfFinishedPtPart = new JTextField();
			tfFinishedPtPart.setBounds(new Rectangle(73, 24, 116, 22));
			tfFinishedPtPart.getDocument().addDocumentListener(
					new DocumentListener() {
						private void setState() {
							String editAfterQueryValue = tfFinishedPtPart
									.getText().trim();
							if (!"".equalsIgnoreCase(editAfterQueryValue)) {
								tfFinishedPtPart1.setEditable(true);
								btnFinishedPtPart1.setEnabled(true);
							} else {
								tfFinishedPtPart1.setText(null);
								tfFinishedPtPart1.setEditable(false);
								btnFinishedPtPart1.setEnabled(false);
							}
						}

						public void insertUpdate(DocumentEvent e) {
							setState();
						}

						public void removeUpdate(DocumentEvent e) {
							setState();
						}

						public void changedUpdate(DocumentEvent e) {
							setState();
						}
					});
		}
		return tfFinishedPtPart;
	}

	/**
	 * This method initializes btnFinishedPtPart
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedPtPart() {
		if (btnFinishedPtPart == null) {
			btnFinishedPtPart = new JButton();
			btnFinishedPtPart.setBounds(new Rectangle(190, 24, 20, 22));
			btnFinishedPtPart.setText("...");
			btnFinishedPtPart
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "ptNo");
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFinishedPtPart.setText(m == null ? "" : m
										.getPtNo());
							}
						}
					});
		}
		return btnFinishedPtPart;
	}

	/**
	 * This method initializes tfFinishedPtPart1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedPtPart1() {
		if (tfFinishedPtPart1 == null) {
			tfFinishedPtPart1 = new JTextField();
			tfFinishedPtPart1.setBounds(new Rectangle(317, 24, 112, 22));
		}
		return tfFinishedPtPart1;
	}

	/**
	 * This method initializes btnFinishedPtPart1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedPtPart1() {
		if (btnFinishedPtPart1 == null) {
			btnFinishedPtPart1 = new JButton();
			btnFinishedPtPart1.setBounds(new Rectangle(430, 24, 20, 22));
			btnFinishedPtPart1.setText("...");
			btnFinishedPtPart1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "ptNo");
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFinishedPtPart1.setText(m == null ? "" : m
										.getPtNo());
							}
						}
					});
		}
		return btnFinishedPtPart1;
	}

	/**
	 * This method initializes tfFinishedPtName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedPtName() {
		if (tfFinishedPtName == null) {
			tfFinishedPtName = new JTextField();
			tfFinishedPtName.setBounds(new Rectangle(73, 50, 116, 22));
		}
		return tfFinishedPtName;
	}

	/**
	 * This method initializes tfFinishedPtSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedPtSpec() {
		if (tfFinishedPtSpec == null) {
			tfFinishedPtSpec = new JTextField();
			tfFinishedPtSpec.setBounds(new Rectangle(317, 50, 112, 22));
		}
		return tfFinishedPtSpec;
	}

	/**
	 * This method initializes tfFinishedHsComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedHsComplex() {
		if (tfFinishedHsComplex == null) {
			tfFinishedHsComplex = new JTextField();
			tfFinishedHsComplex.setBounds(new Rectangle(73, 78, 116, 22));
			tfFinishedHsComplex.getDocument().addDocumentListener(
					new DocumentListener() {
						private void setState() {
							String editAfterQueryValue = tfFinishedHsComplex
									.getText().trim();
							if (!"".equalsIgnoreCase(editAfterQueryValue)) {
								tfFinishedHsComplex1.setEditable(true);
								btnFinishedHsComplex1.setEnabled(true);
							} else {
								tfFinishedHsComplex1.setText(null);
								tfFinishedHsComplex1.setEditable(false);
								btnFinishedHsComplex1.setEnabled(false);
							}
						}

						public void insertUpdate(DocumentEvent e) {
							setState();
						}

						public void removeUpdate(DocumentEvent e) {
							setState();
						}

						public void changedUpdate(DocumentEvent e) {
							setState();
						}
					});
		}
		return tfFinishedHsComplex;
	}

	/**
	 * This method initializes tfFinishedHsComplex1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedHsComplex1() {
		if (tfFinishedHsComplex1 == null) {
			tfFinishedHsComplex1 = new JTextField();
			tfFinishedHsComplex1.setBounds(new Rectangle(317, 78, 112, 22));
		}
		return tfFinishedHsComplex1;
	}

	/**
	 * This method initializes tfFinishedHsName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedHsName() {
		if (tfFinishedHsName == null) {
			tfFinishedHsName = new JTextField();
			tfFinishedHsName.setBounds(new Rectangle(73, 103, 116, 22));
		}
		return tfFinishedHsName;
	}

	/**
	 * This method initializes tfFinishedHsSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinishedHsSpec() {
		if (tfFinishedHsSpec == null) {
			tfFinishedHsSpec = new JTextField();
			tfFinishedHsSpec.setBounds(new Rectangle(317, 103, 112, 22));
		}
		return tfFinishedHsSpec;
	}

	/**
	 * This method initializes btnFinishedPtName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedPtName() {
		if (btnFinishedPtName == null) {
			btnFinishedPtName = new JButton();
			btnFinishedPtName.setBounds(new Rectangle(190, 50, 20, 22));
			btnFinishedPtName.setText("...");
			btnFinishedPtName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "ptName");
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFinishedPtName.setText(m == null ? "" : m
										.getFactoryName());
							}
						}
					});
		}
		return btnFinishedPtName;
	}

	/**
	 * This method initializes btnFinishedHsComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedHsComplex() {
		if (btnFinishedHsComplex == null) {
			btnFinishedHsComplex = new JButton();
			btnFinishedHsComplex.setBounds(new Rectangle(190, 78, 20, 22));
			btnFinishedHsComplex.setText("...");
			btnFinishedHsComplex
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "hsNo");

							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								tfFinishedHsComplex.setText((String) sm
										.getObject3());
							}
						}
					});
		}
		return btnFinishedHsComplex;
	}

	/**
	 * This method initializes btnFinishedPtSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedPtSpec() {
		if (btnFinishedPtSpec == null) {
			btnFinishedPtSpec = new JButton();
			btnFinishedPtSpec.setBounds(new Rectangle(430, 50, 20, 22));
			btnFinishedPtSpec.setText("...");
			btnFinishedPtSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "ptSpec");
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								Materiel m = (Materiel) sm.getObject();
								tfFinishedPtSpec.setText(m == null ? "" : m
										.getFactorySpec());
							}
						}
					});
		}
		return btnFinishedPtSpec;
	}

	/**
	 * This method initializes btnFinishedHsComplex1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedHsComplex1() {
		if (btnFinishedHsComplex1 == null) {
			btnFinishedHsComplex1 = new JButton();
			btnFinishedHsComplex1.setBounds(new Rectangle(430, 78, 20, 22));
			btnFinishedHsComplex1.setText("...");
			btnFinishedHsComplex1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "hsNo");

							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								tfFinishedHsComplex1.setText((String) sm
										.getObject3());
							}
						}
					});
		}
		return btnFinishedHsComplex1;
	}

	/**
	 * This method initializes btnFinishedHsName
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedHsName() {
		if (btnFinishedHsName == null) {
			btnFinishedHsName = new JButton();
			btnFinishedHsName.setBounds(new Rectangle(190, 103, 20, 22));
			btnFinishedHsName.setText("...");
			btnFinishedHsName
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false, getMaterielType(),
											new ArrayList(), "hsName");

							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								tfFinishedHsName.setText((String) sm
										.getObject());
							}
						}
					});
		}
		return btnFinishedHsName;
	}

	/**
	 * This method initializes btnFinishedHsSpec
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishedHsSpec() {
		if (btnFinishedHsSpec == null) {
			btnFinishedHsSpec = new JButton();
			btnFinishedHsSpec.setBounds(new Rectangle(430, 103, 20, 22));
			btnFinishedHsSpec.setText("...");
			btnFinishedHsSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// String materialType = getType();
							List<TempObject> list = CasQuery
									.getInstance()
									.getFactoryAndFactualCustomsRalationForBalance(
											false,
											MaterielType.FINISHED_PRODUCT,
											new ArrayList(), "hsSpec");
							if (list != null && list.size() > 0) {
								TempObject sm = list.get(0);
								tfFinishedHsSpec.setText((String) sm
										.getObject1());
							}
						}
					});
		}
		return btnFinishedHsSpec;
	}

	/**
	 * 关联查询时设置查询状态
	 */
	public void setQueryState(Boolean isFromCheck) {
		if (isFromCheck) {
			rbCheckBlance.setSelected(true);
			rbBillDetail.setSelected(false);
			btnRelation.setEnabled(false);
			// setstat();
		}
	}

	/**
	 * 关联查询时的参数注入(成品条件)
	 * 
	 * @param endDate
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryConditionF(Date endDate, String hsNo, String hsName,
			String hsSpec, String ptNo, String ptName, String ptSpec) {
		// 如果结束日期不为空
		if (endDate != null) {
			this.cbDate.setDate(endDate);
		}

		this.tfFinishedHsComplex.setText(hsNo);
		this.tfFinishedHsComplex1.setText("");
		this.tfFinishedHsName.setText(hsName);
		this.tfFinishedHsSpec.setText(hsSpec);

		this.tfFinishedPtPart.setText(ptNo);
		this.tfFinishedPtPart1.setText("");
		this.tfFinishedPtName.setText(ptName);
		this.tfFinishedPtSpec.setText(ptSpec);
	}

	/**
	 * 关联查询时的参数注入（料件条件）
	 * 
	 * @param endDate
	 * @param hsCode
	 * @param hsName
	 * @param hsSpec
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryConditionM(Date endDate, String hsCode, String hsName,
			String hsSpec, String ptNo, String ptName, String ptSpec) {
		// 如果结束日期不为空
		if (endDate != null) {
			this.cbDate.setDate(endDate);
		}
		this.tfCusPart.setText(hsCode);
		this.tfCusPart1.setText("");
		this.tfHsName.setText(hsName);
		this.tfHsSpec.setText(hsSpec);

		this.tfPtPart.setText(ptNo);
		this.tfPtPart1.setText("");
		this.tfFactoryName.setText(ptName);
		this.tfFactorySpec.setText(ptSpec);
	}

	/**
	 * This method initializes btnRelation
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(674, 110, 87, 22));
			btnRelation.setText("报表关联");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgMaterialThatDayBalanceToBom.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}

					ToBomTempThatDayBalance b = (ToBomTempThatDayBalance) tableModel
							.getCurrentRow();
					sDate = cbDate.getDate();

					// 成品
					if (b.getProduct().getComplex() != null) {
						sFHsNo = b.getProduct().getComplex().getCode();
					}

					sFHsName = b.getProduct().getHsName();
					sFHsSpec = b.getProduct().getHsSpec();

					sFPtNo = cbSetName.isSelected() ? "" : b.getProduct()
							.getPtPart();
					sFPtName = cbSetName.isSelected() ? "" : b.getProduct()
							.getPtName();
					sFPtSpec = cbSetName.isSelected() ? "" : b.getProduct()
							.getPtSpec();

					// //料件
					// sMHsCode = b.getBill().getComplex().getCode();
					// sMHsName = b.getBill().getHsName();
					// sMHsSpec = b.getBill().getHsSpec();
					// sMPtNo = b.getBill().getPtPart();
					// sMPtName = b.getBill().getPtName();
					// sMPtSpec = b.getBill().getPtSpec();

					Component comp = (Component) e.getSource();
					getPmRelation().show(comp, 0, comp.getHeight());

				}
			});
		}
		return btnRelation;
	}

	/**
	 * This method initializes pmRelation
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmRelation() {
		if (pmRelation == null) {
			pmRelation = new JPopupMenu();

			pmRelation.add(getMiImpExpDetail());// 产成品进出仓帐

			pmRelation.add(getMiThatDayBalance());// 产成品当日结存

			pmRelation.add(getMiPandianDifferent());// 结存与盘点差额ok2010.06.28

			// 在产品制单耗用
//			pmRelation.add(getMiZhiDanUse());

		}
		return pmRelation;
	}

	/**
	 * This method initializes miImpExpDetail
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImpExpDetail() {
		if (miImpExpDetail == null) {
			miImpExpDetail = new JMenuItem();
			miImpExpDetail.setText("产成品进出仓帐");
			miImpExpDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgCheckSelfImpExpQuery dialog = new DgCheckSelfImpExpQuery();
							dialog
									.setMaterialType(MaterielType.FINISHED_PRODUCT);
							dialog.setWaiFa(false);

							dialog.setTitle("产成品进出仓帐");

							dialog.setQueryCondition(null, sDate, sFHsNo,
									sFHsName, sFHsSpec, sFPtNo, sFPtName,
									sFPtSpec);

							dialog.queryData();

							dialog.setVisible(true);
						}
					});
		}
		return miImpExpDetail;
	}

	/**
	 * This method initializes miImpExpDetail
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiThatDayBalance() {
		if (miThatDayBalance == null) {
			miThatDayBalance = new JMenuItem();
			miThatDayBalance.setText("产成品当日结存");
			miThatDayBalance
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgMaterialThatDayBalance dg = new DgMaterialThatDayBalance();
							dg.setTitle("产成品当日结存表");
							dg.setMaterielType(MaterielType.FINISHED_PRODUCT);

							dg.setQueryCondition(null, sDate, sFHsNo, sFHsName,
									sFHsSpec, sFPtNo, sFPtName, sFPtSpec);

							dg.queryData();
							dg.setVisible(true);
						}
					});
		}
		return miThatDayBalance;
	}

	/**
	 * This method initializes miPandianDifferent
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPandianDifferent() {
		if (miPandianDifferent == null) {
			miPandianDifferent = new JMenuItem();
			miPandianDifferent.setText("结存与盘点差额");
			miPandianDifferent
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMaterialDifferent dg = new DgMaterialDifferent();
							dg.setTitle("产成品结存与盘点差额表");
							dg.setMaterielType(MaterielType.FINISHED_PRODUCT);

							dg.setQueryCondition(null, sDate, sFHsNo, sFHsName,
									sFHsSpec, sFPtNo, sFPtName, sFPtSpec);

							dg.queryData();

							dg.setVisible(true);

						}
					});
		}
		return miPandianDifferent;
	}

	/**
	 * This method initializes miZhiDanUse
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiZhiDanUse() {
		if (miZhiDanUse == null) {
			miZhiDanUse = new JMenuItem();
			miZhiDanUse.setText("制单耗用情况");
			miZhiDanUse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgWorkOrderBOM dg = new DgWorkOrderBOM();

					dg.setTitle("制单耗用情况表");

					dg.setQueryCondition(null, sDate, sFHsNo, sFHsName,
							sFHsSpec, sFPtNo, sFPtName, sFPtSpec);

					dg.queryData();

					dg.setVisible(true);

				}
			});
		}
		return miZhiDanUse;
	}

	/**
	 * This method initializes rbBillDetail
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBillDetail() {
		if (rbBillDetail == null) {
			rbBillDetail = new JRadioButton();
			rbBillDetail.setBounds(new Rectangle(281, 12, 80, 24));
			rbBillDetail.setText("\u5355\u636e\u4e2d\u5fc3");
			rbBillDetail.setSelected(true);
			rbBillDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnRelation.setEnabled(true);
					cbShowNegative.setVisible(true);
					resultList = new ArrayList();

				}
			});
		}
		return rbBillDetail;
	}

	/**
	 * This method initializes rbCheckBlance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCheckBlance() {
		if (rbCheckBlance == null) {
			rbCheckBlance = new JRadioButton();
			rbCheckBlance.setBounds(new Rectangle(363, 12, 82, 24));
			rbCheckBlance.setText("\u76d8\u70b9\u5bfc\u5165");
			rbCheckBlance
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							btnRelation.setEnabled(false);
							cbShowNegative.setVisible(false);
							resultList = new ArrayList();

						}
					});
		}
		return rbCheckBlance;
	}

} // @jve:decl-index=0:visual-constraint="-9,38"
