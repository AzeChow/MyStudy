package com.bestway.client.selfcheck;

import com.bestway.bcs.client.dictpor.DgBcsDictPor;
import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.SemiProductInfo;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.DgFactoryQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.CheckableItem;
import com.bestway.common.client.erpbillcenter.FmFactoryAndFactualCustomsRalation;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Font;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.io.InputStream;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

/**
 * 当日结存表（通用）
 * @author chenSir
 * wss修改
 */
public class DgMaterialThatDayBalance extends JDialogBase {
	private MaterialManageAction materialManageAction = null;
	private static String SELECT_ALL = "全选";  //  @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选";  //  @jve:decl-index=0:
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
	private List<String> ptParts = new ArrayList();  //  @jve:decl-index=0:
	
	/**
	 * 报关料号
	 */
	private List<String> cusParts = new ArrayList();
	
	/**
	 * 物料类型
	 */
	private String materielType = null;  //  @jve:decl-index=0:
	
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
	private JButton btnRelation = null;
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="807,216"
	private JMenuItem miImpExpDetail = null;  //  @jve:decl-index=0:visual-constraint="811,257"
	private JMenuItem miPandianDifferent = null;  //  @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miConvert = null;  //  @jve:decl-index=0:visual-constraint="812,321"
	private JMenuItem miThatDayConvert = null;  //  @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miZhiDanUse = null;  //  @jve:decl-index=0:visual-constraint="813,382"
	
	//关联查询时传递 的参数
	private Date sDate;
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sPtNo;
	private String sPtName;
	private String sPtSpec;
	
	
	private List<TempThatDayBalance> listResult = null;
	private JRadioButton rbFromBill = null;
	private JRadioButton rbFromCheck = null;
	private ButtonGroup group = null;  //  @jve:decl-index=0:

	
	/**
	 * This method initializes 
	 * 
	 */
	public DgMaterialThatDayBalance() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
				"casCheckAction");
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

	
	}
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("***当日结存表");
		this.setSize(812, 546);
        this.setContentPane(getJContentPane());
        group = new ButtonGroup();
        group.add(rbFromBill);
        group.add(rbFromCheck);
        Vector list=new Vector();
		jTable.setModel(initTable(list));
			
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
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(399, 78, 32, 22));
			jLabel4.setText("至");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(399, 30, 32, 22));
			jLabel3.setText("至");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(450, 78, 65, 22));
			jLabel21.setText("报关编码");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(184, 78, 65, 22));
			jLabel2.setText("报关编码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(450, 30, 65, 22));
			jLabel1.setText("工厂料号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(184, 30, 65, 22));
			jLabel.setText("工厂料号");
			lbHsSpec = new JLabel();
			lbHsSpec.setText("报关规格");
			lbHsSpec.setLocation(new Point(449, 102));
			lbHsSpec.setSize(new Dimension(65, 22));
			lbHsName = new JLabel();
			lbHsName.setText("报关名称");
			lbHsName.setBounds(new Rectangle(184, 102, 65, 22));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setLocation(new Point(449, 54));
			lbFactorySpec.setSize(new Dimension(65, 22));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setLocation(new Point(184, 126));
			lbDate.setSize(new Dimension(65, 22));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setBounds(new Rectangle(184, 54, 65, 22));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(16, 16, 38, 20));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u4ed3\u8d26\u9009\u9879", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setPreferredSize(new Dimension(1, 180));
			jPanel1.setBounds(new Rectangle(0, 0, 804, 180));
			jPanel1.add(lbFactory, null);
			jPanel1.add(lbFactoryName, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(lbDate, null);
			jPanel1.add(getTfFactoryName(), null);
			jPanel1.add(getBtnFactoryName(), null);
			jPanel1.add(lbFactorySpec, null);
			jPanel1.add(getTfFactorySpec(), null);
			jPanel1.add(getBtnFactorySpec(), null);
			jPanel1.add(lbHsName, null);
			jPanel1.add(getTfHsName(), null);
			jPanel1.add(getBtnHsName(), null);
			jPanel1.add(lbHsSpec, null);
			jPanel1.add(getTfHsSpec(), null);
			jPanel1.add(getBtnHsSpec(), null);
			jPanel1.add(getCbSetName(), null);
			jPanel1.add(getCbShowNegative(), null);
			jPanel1.add(getCbFactoryByGroup(), null);
			jPanel1.add(getBtnSelect(), null);
			jPanel1.add(getBtnMimeograph(), null);
			jPanel1.add(getBtnClose(), null);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(getTfPtPart(), null);
			jPanel1.add(getTfCusPart(), null);
			jPanel1.add(getTfPtPart1(), null);
			jPanel1.add(getTfCusPart1(), null);
			jPanel1.add(getBtnFactoryName1(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(getBtnRelation(), null);
			jPanel1.add(getRbFromBill(), null);
			jPanel1.add(getRbFromCheck(), null);
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
			jScrollPane.setBounds(new Rectangle(15, 32, 157, 136));
			jScrollPane.setViewportView(getLsFactory());
		}
		return jScrollPane;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbDate == null) {
			cbDate = new JCalendarComboBox();
			cbDate.setSize(new Dimension(136, 22));
			cbDate.setLocation(new Point(249, 126));
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
				setFont(new Font(getFont().getName(),Font.BOLD, getFont().getSize()));
				setText("  " + item.getName());
			} else if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				setSelected(item.isSelected());
				setSize(350, 17);
				setFont(new Font(getFont().getName(),Font.PLAIN, getFont().getSize()));
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
			tfFactoryName.setSize(new Dimension(116, 22));
			tfFactoryName.setLocation(new Point(249, 54));
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
			btnFactoryName.setLocation(new Point(365, 54));
			btnFactoryName.setSize(new Dimension(20, 22));
			btnFactoryName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
						.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
															new ArrayList(),"ptName");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfFactoryName.setText(m == null ? "" : m.getFactoryName());
//						tfFactorySpec.setText(m == null ? "" : m.getFactorySpec());
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
			tfFactorySpec.setSize(new Dimension(112, 22));
			tfFactorySpec.setLocation(new Point(514, 54));
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
			btnFactorySpec.setLocation(new Point(628, 54));
			btnFactorySpec.setSize(new Dimension(20, 22));
			btnFactorySpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
														new ArrayList(),"ptSpec");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfFactorySpec.setText(m == null ? "" : m.getFactorySpec());
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
			tfHsName.setSize(new Dimension(116, 22));
			tfHsName.setLocation(new Point(249, 102));
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
			btnHsName.setLocation(new Point(365, 102));
			btnHsName.setSize(new Dimension(20, 22));
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
														new ArrayList(),"hsName");
					
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						tfHsName.setText((String)sm.getObject());
						tfHsSpec.setText((String)sm.getObject1());
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
			tfHsSpec.setSize(new Dimension(112, 22));
			tfHsSpec.setLocation(new Point(514, 102));
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
			btnHsSpec.setLocation(new Point(628, 102));
			btnHsSpec.setSize(new Dimension(20, 22));
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
														new ArrayList(),"hsSpec");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						tfHsSpec.setText((String)sm.getObject());
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
			cbSetName.setLocation(new Point(193, 154));
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
			cbShowNegative.setLocation(new Point(355, 154));
			cbShowNegative.setSize(new Dimension(129, 21));
			cbShowNegative.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showLessthanZero(cbShowNegative.isSelected());
				}
			});
			
		}
		return cbShowNegative;
	}


	/**
	 * 显示负数结存
	 * @param isShow
	 */
	private void showLessthanZero(boolean isShow) {
		if (listResult == null || listResult.size() == 0) {
			initTable(new ArrayList());
			return;
		}
		if (isShow) {
			List tempList = new ArrayList();
			for (int i = 0; i < listResult.size(); i++) {
				TempThatDayBalance temp = (TempThatDayBalance) listResult.get(i);
				if (cbSetName.isSelected() ? (temp.getHsAmount() != null && temp.getHsAmount() < 0)
						: (temp.getPtAmount() != null && temp.getPtAmount() < 0)) {
					tempList.add(temp);
				}
			}
			initTable(tempList);
		} else {
			initTable(listResult);
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
			cbFactoryByGroup.setLocation(new Point(506, 154));
			cbFactoryByGroup.setSize(new Dimension(123, 21));
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
			btnSelect.setSize(new Dimension(92, 25));
			btnSelect.setLocation(new Point(665, 25));
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
	public void queryData(){
		//单据查询条件
		List<Condition> conditionsBill = new ArrayList<Condition>();
		
		//盘点查询条件
		List<Condition> conditionsCheck = new ArrayList<Condition>();
		
		//外发加工反查  料件级查询条件
		List<Condition> conditionsMateriel = new ArrayList<Condition>();


		//生效的单据
		conditionsBill.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));
		
		//生效日期(从当年开始至现在)
		if (cbDate.getDate() != null) { // 开始日期
			Date endDate = CommonVars.getEndDate(cbDate.getDate());
			
			Date startDateCheck = CommonVars.getBeginDate(cbDate.getDate());
			
			Date startDate = CommonVars.getBeginDate(new Date(endDate.getYear(),0,1));
			
			conditionsBill.add(new Condition("and", null,"billMaster.validDate", ">=",startDate, null));
			conditionsBill.add(new Condition("and", null,"billMaster.validDate", "<=",endDate, null));
			
			conditionsCheck.add(new Condition("and", null,"checkDate", ">=",startDateCheck, null));
			conditionsCheck.add(new Condition("and", null,"checkDate", "<=",endDate, null));
			
		}
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtPart.getText().trim().equals("")
				&& tfPtPart1.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "ptPart",
					"=", tfPtPart.getText(), null));
			}
			
			
			conditionsCheck.add(new Condition("and", null, "ptNo",
					"=", tfPtPart.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "materiel.ptNo",
					"=", tfPtPart.getText(), null));
			
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtPart.getText().trim().equals("")
				&& !tfPtPart1.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", "(", "ptPart",
						">=", tfPtPart.getText(), null));
				conditionsBill.add(new Condition("and", null, "ptPart",
						"<=", tfPtPart1.getText(), ")"));
			}
			
			
			conditionsCheck.add(new Condition("and", "(", "ptNo",
					">=", tfPtPart.getText(), null));
			conditionsCheck.add(new Condition("and", null, "ptNo",
					"<=", tfPtPart1.getText(), ")"));
			
			conditionsMateriel.add(new Condition("and", "(", "materiel.ptNo",
					">=", tfPtPart.getText(), null));
			conditionsMateriel.add(new Condition("and", null, "materiel.ptNo",
					"<=", tfPtPart1.getText(), ")"));
		}
		//工厂名称
		if (!tfFactoryName.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "ptName",
						"=", tfFactoryName.getText(), null));
			}
			
			conditionsCheck.add(new Condition("and", null, "ptName",
						"=", tfFactoryName.getText(), null));
			conditionsMateriel.add(new Condition("and", null, "materiel.factoryName",
					"=", tfFactoryName.getText(), null));
		}
		//工厂规格
		if (!tfFactorySpec.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "ptSpec",
					"=", tfFactorySpec.getText(), null));
			}
			
			conditionsCheck.add(new Condition("and", null, "ptSpec",
					"=", tfFactorySpec.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "materiel.factorySpec",
					"=", tfFactorySpec.getText(), null));
		}
		// 报关料号不等于空,结束料号为空时
		if (!tfCusPart.getText().trim().equals("")
				&& tfCusPart1.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "complex.code",
					"=", tfCusPart.getText(), null));
			}
			
			
			conditionsCheck.add(new Condition("and", null, "complex.code",
					"=", tfCusPart.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "statCusNameRelationHsn.complex.code",
					"=", tfCusPart.getText(), null));
		} 
		// 报关料号不等于空,结束料号不为空时
		else if (!tfCusPart.getText().trim().equals("")
				&& !tfCusPart1.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", "(", "complex.code",
						">=", tfCusPart.getText(), null));
				conditionsBill.add(new Condition("and", null, "complex.code",
						"<=", tfCusPart1.getText(), ")"));
			}
			
			
			conditionsCheck.add(new Condition("and", "(", "complex.code",
					">=", tfCusPart.getText(), null));
			conditionsCheck.add(new Condition("and", null, "complex.code",
					"<=", tfCusPart1.getText(), ")"));
			
			conditionsMateriel.add(new Condition("and", "(", "statCusNameRelationHsn.complex.code",
					">=", tfCusPart.getText(), null));
			conditionsMateriel.add(new Condition("and", null, "statCusNameRelationHsn.complex.code",
					"<=", tfCusPart1.getText(), ")"));
		}
		//报关名称
		if (!tfHsName.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "hsName",
					"=", tfHsName.getText(), null));
			}
			
			
			conditionsCheck.add(new Condition("and", null, "name",
					"=", tfHsName.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "statCusNameRelationHsn.cusName",
					"=", tfHsName.getText(), null));
		}
		 //报关规格
		if (!tfHsSpec.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "hsSpec",
					"=", tfHsSpec.getText(), null));
			}
			
			
			conditionsCheck.add(new Condition("and", null, "spec",
					"=", tfHsSpec.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "statCusNameRelationHsn.cusSpec",
					"=", tfHsSpec.getText(), null));
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
		//casCondition.setWareSetCodes(checkableCodeList);
		for (int j = 0; j < checkableItemList.size(); j++) {
			CheckableItem item = checkableItemList.get(j);
			Condition condition = null;
			if (j == 0) {
				condition = new Condition("and", "(",
						"wareSet.code", "=", item.getCode().trim(),
						null);
			} else {
				condition = new Condition("or", null,
						"wareSet.code", "=", item.getCode().trim(),
						null);
			}
			if (j == checkableItemList.size() - 1) {
				condition.setEndBracket(")");
			}
			conditionsBill.add(condition);
			conditionsCheck.add(condition);
		}
		
		String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
		
		new Find(conditionsBill,"",getMaterielType(),cbSetName.isSelected(),cbShowNegative.isSelected(),
				rbFromCheck.isSelected(),conditionsCheck,conditionsMateriel).execute();
	
	}
	
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditionsBill = null;
        //排序
		String orderBy = null;
        //查询目标表
		String billDetail = null;
		//报关名称汇总
		Boolean ishsTaotal = false;
		//显示负数结存
		Boolean isShowLess = false;
		//查询条件
		CasCondition casCondition=null;
		
		Boolean isFromCheck = false;
		
		List<Condition> conditionsCheck = null;
		
		List<Condition> conditionsMateriel = null;
		
		public Find(List<Condition> conditionsBill, String orderBy,
				String billDetail,Boolean ishsTaotal,Boolean isShowLess,
				Boolean isFromCheck,List<Condition> conditionsCheck,
				List<Condition> conditionsMateriel) {
			this.conditionsBill = conditionsBill;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
			this.ishsTaotal = ishsTaotal;
			this.isShowLess = isShowLess;
			this.isFromCheck = isFromCheck;
			this.conditionsCheck = conditionsCheck;
			this.conditionsMateriel = conditionsMateriel;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgMaterialThatDayBalance.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			
			
			//wss2010.07.22显示负数结存改在客户端判断(直接false)
			if( "outSource".equals(getMaterielType())){
//				list = casCheckAction.getCurrentBillDetail(new Request(CommonVars.getCurrUser()),casCondition,billDetail);
				list = casCheckAction.getCurrentBillDetailOutSource(new Request(CommonVars.getCurrUser()), 
																	conditionsBill,
																	orderBy,
																	getMaterielType(),
																	cbSetName.isSelected(),
																	false,
																	cbFactoryByGroup.isSelected(),
																	conditionsMateriel,
																	isFromCheck,
																	conditionsCheck);
			}else{
				list = casCheckAction.getCurrentBillDetail(new Request(CommonVars.getCurrUser()), conditionsBill,
						orderBy,getMaterielType(),cbSetName.isSelected(),false,cbFactoryByGroup.isSelected(),
						isFromCheck,conditionsCheck);
			}
			return list;
		}

		@Override
		protected void done() {//更新视图
//			List list=null;
			try {
				listResult = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(DgMaterialThatDayBalance.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
//			if(list==null){
//				list=new ArrayList();
//			}
			CommonProgress.closeProgressDialog();
			showLessthanZero(cbShowNegative.isSelected());

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
			btnMimeograph.setLocation(new Point(665, 65));
			btnMimeograph.setSize(new Dimension(92, 25));
			btnMimeograph.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								List list = tableModel.getList();
								CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
										list);
						
								//1.全不选
								String str = "report/dayBalance.jasper";
								//选了
								if(cbSetName.isSelected() || cbFactoryByGroup.isSelected()){
									if(!cbFactoryByGroup.isSelected()){//2.只选了报关名称汇总
										str = "report/dayBalanceCus.jasper";
									}else if(!cbSetName.isSelected()){//3.只选了仓库分组
										str = "report/dayBalanceGroup.jasper";
									}else{//4.两者都选了
										str = "report/dayBalanceCusAndGroup.jasper";
									}
								}
								InputStream masterReportStream = DgMaterialThatDayBalance.class
										.getResourceAsStream(str);
								
								
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("title", getTitle());
//								parameters.put("type", getMaterielType());
								parameters.put("date",cbDate.getDate());
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
			btnClose.setLocation(new Point(665, 100));
			btnClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnClose.setSize(new Dimension(92, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMaterialThatDayBalance.this.dispose();
				}
			});
			
		}
		return btnClose;
	}
	
	private JTableListModel initTable(List list) {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> columns = new Vector<JTableListColumn>();
							if(!cbSetName.isSelected()){
								columns.add(addColumn("工厂料号", "ptPart", 100));
								columns.add(addColumn("工厂名称", "ptName", 100));
								columns.add(addColumn("工厂规格", "ptSpec", 100));
								columns.add(addColumn("工厂单位", "ptUnit.name", 100));
								columns.add(addColumn("工厂数量", "ptAmount", 100));
							}
							columns.add(addColumn("报关编码", "complex.code", 100));
							columns.add(addColumn("报关名称", "hsName", 100));
							columns.add(addColumn("报关规格", "hsSpec", 100));
							columns.add(addColumn("报关单位", "hsUnit.name", 100));
							columns.add(addColumn("报关数量", "hsAmount", 100));
							//columns.add(addColumn("物料类别", "materialType", 100));
							if(cbFactoryByGroup.isSelected()){
								columns.add(addColumn("物料仓库", "wareSet.name", 100));
							}
							return columns;
						}
					});
//			jTable.getColumnModel().getColumn(11).setCellRenderer(
//					new DefaultTableCellRenderer() {
//						public Component getTableCellRendererComponent(
//								JTable table, Object value, boolean isSelected,
//								boolean hasFocus, int row, int column) {
//							super.getTableCellRendererComponent(table, value,
//									isSelected, hasFocus, row, column);
//							super.setText(CheckMyselfUntil.getType((String)value,materielType));
//			                return this;
//						}
//					});
		return tableModel;
	}
	
	/**
	 * @return Returns the intOutFlat.
	 */
	public String getMaterielType() {
		return materielType;
	}
	
	/**
	 * 获取选择条件类型
	 * @return
	 */
	public String getImgExgType(){
		if(MaterielType.FINISHED_PRODUCT.equals(materielType)){
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
			tfPtPart.setBounds(new Rectangle(249, 30, 116, 22));
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
			tfCusPart.setBounds(new Rectangle(249, 78, 116, 22));
			//wss:此功能暂时屏蔽
//			tfCusPart.getDocument().addDocumentListener(new DocumentListener() {
//				private void setState() {
//					String editAfterQueryValue = tfCusPart.getText().trim();
//					if (!"".equalsIgnoreCase(editAfterQueryValue)) {
//						tfCusPart1.setEditable(true);
//						jButton2.setEnabled(true);
//					} else {
//						tfCusPart1.setEditable(false);
//						jButton2.setEnabled(false);
//					}
//				}
//
//				public void insertUpdate(DocumentEvent e) {
//					setState();
//				}
//
//				public void removeUpdate(DocumentEvent e) {
//					setState();
//				}
//
//				public void changedUpdate(DocumentEvent e) {
//					setState();
//				}
//
//			});
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
			tfPtPart1.setBounds(new Rectangle(514, 30, 112, 22));
			//tfPtPart1.setEditable(false);
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
			tfCusPart1.setBounds(new Rectangle(514, 78, 112, 22));
			//tfCusPart1.setEditable(false);
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
			btnFactoryName1.setBounds(new Rectangle(365, 30, 20, 22));
			btnFactoryName1.setText("...");
			btnFactoryName1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
																new ArrayList(),"ptNo");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
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
			jTable = new JTable();
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
			jButton.setBounds(new Rectangle(365, 78, 20, 22));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
								.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
														new ArrayList(),"hsNo");
					
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						tfCusPart.setText((String)sm.getObject3());
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
			jButton1.setBounds(new Rectangle(628, 30, 20, 22));
			jButton1.setText("...");
			//jButton1.setEnabled(false);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
																	new ArrayList(),"ptNo");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
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
			jButton2.setBounds(new Rectangle(628, 78, 20, 22));
			jButton2.setText("...");
			//jButton2.setEnabled(false);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, getMaterielType(),
														new ArrayList(),"hsNo");

					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						tfCusPart1.setText((String)sm.getObject3());
					}
				}
			});
		}
		return jButton2;
	}
	/**
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(665, 135, 92, 25));
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgMaterialThatDayBalance.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					TempThatDayBalance b = (TempThatDayBalance)tableModel.getCurrentRow();
					sDate = cbDate.getDate();
					sHsCode = b.getComplex().getCode();
					sHsName = b.getHsName();
					sHsSpec = b.getHsSpec();
					
					//如果报关名称汇总
					sPtNo = cbSetName.isSelected()? "":b.getPtPart();
					sPtName = cbSetName.isSelected()? "":b.getPtName();
					sPtSpec = cbSetName.isSelected()? "":b.getPtSpec();
					
					
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
			
			pmRelation.add(getMiImpExpDetail());//进出仓帐ok2010.06.28
			
			pmRelation.add(getMiPandianDifferent());//结存与盘点差额ok2010.06.28
			
			//产成品 、残次品折料情况
			if(MaterielType.FINISHED_PRODUCT.equals(materielType) 
					|| MaterielType.BAD_PRODUCT.equals(materielType) ){
				pmRelation.add(getMiConvert());
			}
			
			//产成品、残次品结存折料情况
			if(MaterielType.FINISHED_PRODUCT.equals(materielType)
					|| MaterielType.BAD_PRODUCT.equals(materielType)){
				pmRelation.add(getMiThatDayConvert());
			}
			
						
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
			miImpExpDetail.setText("物料帐");
			miImpExpDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String type = MaterielType.MATERIEL;
					boolean isWaiFa = false;
					String title = "";
					//料件
					if(MaterielType.MATERIEL == materielType){
						type = MaterielType.MATERIEL;
						title = "原材料物料帐";
					}
					//在产品
					else if("currentTotal" == materielType){
						type = MaterielType.SEMI_FINISHED_PRODUCT;
						title = "在产品物料帐";
					}
					//产成品
					else if(MaterielType.FINISHED_PRODUCT == materielType){
						type = MaterielType.FINISHED_PRODUCT;
						title = "产成品物料帐";
					}
					//外发加工
					else if("outSource" == materielType){
						type = MaterielType.SEMI_FINISHED_PRODUCT;
						isWaiFa = true;
						title = "外发加工物料帐";
					}
					//残次品
					else if(MaterielType.BAD_PRODUCT == materielType){
						type = MaterielType.BAD_PRODUCT;
						isWaiFa = true;
						title = "残次品物料帐";
					}
					
					
					DgCheckSelfImpExpQuery dialog =  new DgCheckSelfImpExpQuery();

					dialog.setMaterialType(type);
					
					dialog.setWaiFa(isWaiFa);
					
					dialog.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					
					dialog.queryData();
					
					dialog.setTitle(title);
					
					dialog.setVisible(true);
					
					dialog.setLocation(200, 200);
					
				}
			});
		}
		return miImpExpDetail;
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
			miPandianDifferent.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMaterialDifferent dg=new DgMaterialDifferent();
					 String title = "";
					if(MaterielType.MATERIEL == materielType){
						title = "原材料结存与盘点差额";
					}
					//在产品
					else if("currentTotal" == materielType){
						title = "在产品结存与盘点差额";
					}
					//产成品
					else if(MaterielType.FINISHED_PRODUCT == materielType){
						title = "产成品结存与盘点差额";
					}
					//外发加工
					else if("outSource" == materielType){
						title = "外发加工结存与盘点差额";
					}
					//残次品
					else if(MaterielType.BAD_PRODUCT == materielType){
						title = "残次品结存与盘点差额";
					}
					
					
					dg.setTitle(title);
					dg.setMaterielType(materielType);
					dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					dg.queryData();
					dg.setVisible(true);
					dg.setLocation(200, 200);
					
				}
			});
		}
		return miPandianDifferent;
	}
	/**
	 * This method initializes miConvert	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiConvert() {
		if (miConvert == null) {
			miConvert = new JMenuItem();
			miConvert.setText("折料情况");
			miConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//成品
					if(MaterielType.FINISHED_PRODUCT.equals(materielType)){
						DgProductConvert dg = new DgProductConvert();
						dg.setMaterielType(MaterielType.FINISHED_PRODUCT);
						
						dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
						dg.setTitle("产成品折料情况表");
						
						dg.queryData();
						
						dg.setVisible(true);
						dg.setLocation(200, 200);

					}
					//残次品
					else{
						DgBadProductBOM dg = new DgBadProductBOM();
						dg.setMaterielType(MaterielType.BAD_PRODUCT);
						
						dg.setTitle("残次品折料情况表");
						
						dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
						
						dg.queryData();
						
						dg.setVisible(true);
						dg.setLocation(200, 200);
					}
						
						
						
				}
			});
		}
		return miConvert;
	}
	/**
	 * This method initializes miThatDayConvert	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiThatDayConvert() {
		if (miThatDayConvert == null) {
			miThatDayConvert = new JMenuItem();
			miThatDayConvert.setText("结存折料情况");
			miThatDayConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//成品
					if(MaterielType.FINISHED_PRODUCT.equals(materielType)){
						DgMaterialThatDayBalanceToBom dg=new DgMaterialThatDayBalanceToBom();
						
						dg.setMaterielType(MaterielType.FINISHED_PRODUCT);
						
						dg.setTitle("产成品结存折料");
						
						dg.setQueryConditionF(sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
						
						dg.queryData();
						dg.setVisible(true);
						dg.setLocation(200, 200);
					}
					//残次品
					else{
						DgBadProductThatDayBalanceToBom dg=new DgBadProductThatDayBalanceToBom();
						dg.setTitle("残次品结存折料");
						
						dg.setQueryConditionF(sDate,sPtNo,sPtName,sPtSpec);
						
						dg.queryData();
						
						dg.setVisible(true);
						
						dg.setLocation(200, 200);
					}
					

				}
			});
		}
		return miThatDayConvert;
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
					
					dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					dg.queryData();
					dg.setVisible(true);
					dg.setLocation(200, 200);

					
					
				}
			});
		}
		return miZhiDanUse;
	}
	
	
	/**
	 * 关联查询时设置查询状态
	 */
	public void setQueryState(Boolean isFromCheck){
		if(isFromCheck){
			rbFromBill.setSelected(false);
			rbFromCheck.setSelected(true);
			btnRelation.setEnabled(false);
		}
	}
	/**
	 * 关联查询时的参数注入
	 * @param startDate
	 * @param endDate
	 * @param hsCode
	 * @param hsName
	 * @param hsSpec
	 * @param ptNo
	 * @param ptName
	 * @param ptSpec
	 * @author wss
	 */
	public void setQueryCondition(Date startDate,Date endDate,String hsCode,
			String hsName,String hsSpec,String ptNo,String ptName,String ptSpec){
		//如果结束日期不为空
		if(endDate != null){
			this.cbDate.setDate(endDate);
//			if(startDate != null){
//				this.startDate.setDate(startDate);
//			}else{
//				this.startDate.setDate(CommonUtils.getBeginDate(endDate.getYear(), 0, 1));
//			}
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
	 * This method initializes rbFromBill	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbFromBill() {
		if (rbFromBill == null) {
			rbFromBill = new JRadioButton();
			rbFromBill.setBounds(new Rectangle(449, 126, 99, 22));
			rbFromBill.setText("数据来自单据");
			rbFromBill.setSelected(true);
			rbFromBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnRelation.setEnabled(true);
				}
			});
		}
		return rbFromBill;
	}
	/**
	 * This method initializes rbFromCheck	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbFromCheck() {
		if (rbFromCheck == null) {
			rbFromCheck = new JRadioButton();
			rbFromCheck.setBounds(new Rectangle(555, 126, 101, 22));
			rbFromCheck.setText("数据来自盘点");
			rbFromCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnRelation.setEnabled(false);
				}
			});
		}
		return rbFromCheck;
	}

}  //  @jve:decl-index=0:visual-constraint="-12,32"
