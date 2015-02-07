package com.bestway.client.selfcheck;

import com.bestway.bcs.client.dictpor.DgBcsDictPor;
import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.TempCurrentBillDetailPanDian;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.DgFactoryQuery;
import com.bestway.bcus.client.cas.otherreport.FmCheckBalance;
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
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.CheckableItem;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
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

/**
 * 当日结存表（通用）
 * @author chenSir
 * wss修改
 */
public class DgCurrentDifferent extends JDialogBase {
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
	
	
	
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="842,217"
	private JMenuItem miImpExpDetail = null;  //  @jve:decl-index=0:visual-constraint="811,257"
	private JMenuItem miThatDayBalance = null;
	private JMenuItem miCurrentConvert = null;  //  @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miThatDayConvert = null;  //  @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miZhiDanUse = null;  //  @jve:decl-index=0:visual-constraint="813,382"
	
	//关联查询时传递 的参数
	private Date sDate;
	//成品级
//	private String sFPtNo;
//	private String sFPtName;
//	private String sFPtSpec;
	
	//料件级
	private String sHsCode;
	private String sHsName;
	private String sHsSpec;
	private String sPtNo;
	private String sPtName;
	private String sPtSpec;
	
	
	private List<TempCurrentBillDetailPanDian> listResult = null;

	
	
	/**
	 * This method initializes 
	 * 
	 */
	public DgCurrentDifferent() {
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
		this.setTitle("在产品结存与盘点差额表");
		this.setSize(new Dimension(855, 630));
        this.setContentPane(getJContentPane());
        Vector list=new Vector();
		initTable(list);
			
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
			jLabel4.setText("至");
			jLabel4.setBounds(new Rectangle(441, 66, 32, 22));
			jLabel3 = new JLabel();
			jLabel3.setText("至");
			jLabel3.setBounds(new Rectangle(441, 20, 32, 22));
			jLabel21 = new JLabel();
			jLabel21.setText("报关编码");
			jLabel21.setBounds(new Rectangle(474, 66, 65, 22));
			jLabel2 = new JLabel();
			jLabel2.setText("报关编码");
			jLabel2.setBounds(new Rectangle(230, 66, 65, 22));
			jLabel1 = new JLabel();
			jLabel1.setText("工厂料号");
			jLabel1.setBounds(new Rectangle(474, 20, 65, 22));
			jLabel = new JLabel();
			jLabel.setText("工厂料号");
			jLabel.setBounds(new Rectangle(230, 20, 65, 22));
			lbHsSpec = new JLabel();
			lbHsSpec.setText("报关规格");
			lbHsSpec.setBounds(new Rectangle(474, 89, 65, 22));
			lbHsName = new JLabel();
			lbHsName.setText("报关名称");
			lbHsName.setBounds(new Rectangle(230, 89, 65, 22));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setBounds(new Rectangle(474, 43, 65, 22));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setLocation(new Point(229, 112));
			lbDate.setSize(new Dimension(65, 22));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setBounds(new Rectangle(230, 43, 65, 22));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(25, 16, 29, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u4ed3\u8d26\u9009\u9879", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setPreferredSize(new Dimension(1, 190));
			jPanel1.setPreferredSize(new Dimension(1, 170));
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
			jPanel1.add(getBtnHsSpec(), null);
			jPanel1.add(getTfHsSpec(), null);
			jPanel1.add(lbHsSpec, null);
			jPanel1.add(getBtnHsName(), null);
			jPanel1.add(getTfHsName(), null);
			jPanel1.add(lbHsName, null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getTfCusPart(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel21, null);
			jPanel1.add(getTfCusPart1(), null);
			jPanel1.add(getTfFactorySpec(), null);
			jPanel1.add(getBtnFactorySpec(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getTfPtPart1(), null);
			jPanel1.add(lbFactorySpec, null);
			jPanel1.add(getBtnFactoryName(), null);
			jPanel1.add(getTfFactoryName(), null);
			jPanel1.add(lbFactoryName, null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getTfPtPart(), null);
			jPanel1.add(getBtnFactoryName1(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getBtnRelation(), null);
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
			jScrollPane.setBounds(new Rectangle(56, 20, 157, 131));
			jScrollPane.setViewportView(getLsFactory());
		}
		return jScrollPane;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbDate == null) {
			cbDate = new JCalendarComboBox();
			cbDate.setSize(new Dimension(137, 22));
			cbDate.setLocation(new Point(297, 112));
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
			tfFactoryName.setBounds(new Rectangle(297, 43, 116, 22));
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
			btnFactoryName.setBounds(new Rectangle(414, 43, 20, 22));
			btnFactoryName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
						.getFactoryAndFactualCustomsRalationForBalance(false, MaterielType.MATERIEL,
															new ArrayList(),"ptName");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfFactoryName.setText(m == null ? "" : m.getFactoryName());
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
			tfFactorySpec.setBounds(new Rectangle(541, 43, 112, 22));
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
			btnFactorySpec.setBounds(new Rectangle(654, 43, 20, 22));
			btnFactorySpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, MaterielType.MATERIEL,
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
			tfHsName.setBounds(new Rectangle(297, 89, 116, 22));
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
			btnHsName.setBounds(new Rectangle(414, 89, 20, 22));
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false,
							MaterielType.MATERIEL,
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
			tfHsSpec.setBounds(new Rectangle(541, 89, 112, 22));
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
			btnHsSpec.setBounds(new Rectangle(654, 89, 20, 22));
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
							.getFactoryAndFactualCustomsRalationForBalance(false,
									MaterielType.MATERIEL,
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
			cbSetName.setLocation(new Point(251, 138));
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
			cbShowNegative.setLocation(new Point(409, 138));
			cbShowNegative.setSize(new Dimension(102, 21));
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
				TempCurrentBillDetailPanDian temp = (TempCurrentBillDetailPanDian) listResult.get(i);
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
			cbFactoryByGroup.setLocation(new Point(533, 138));
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
			btnSelect.setSize(new Dimension(86, 25));
			btnSelect.setLocation(new Point(717, 17));
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
		//日期是一定要选择的
		if(cbDate.getDate() == null ){
			JOptionPane.showMessageDialog(DgCurrentDifferent.this,
					"您必须选择时间！", "注意啦！！！", 0);
			return;
		}
		
		//查询
		//组织查询条件
		List<Condition> finishedConditions = getFinishedCondition();
		List<Condition> materialConditions = getMaterialCondition();
		List<Condition> finishedConditionsCheck = getFinishedConditionCheck();
		List<Condition> materialConditionsCheck = getMaterialConditionCheck();
		
		String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
		

		//	执行查询线程
		new Find(finishedConditions,materialConditionsCheck,materialConditions,"",getMaterielType(),cbSetName.isSelected(),cbShowNegative.isSelected()).execute();
	
	}
	
	
	/**
	 * 料件级查询条件
	 * @return
	 */
	private List<Condition> getMaterialCondition() {
		List MaterialConditions=new ArrayList<Condition>();
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtPart.getText().trim().equals("")
				&& tfPtPart1.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", null, "materiel.ptNo",
					"=", tfPtPart.getText(), null));
			
			
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtPart.getText().trim().equals("")
				&& !tfPtPart1.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", "(", "materiel.ptNo",
					">=", tfPtPart.getText(), null));
			MaterialConditions.add(new Condition("and", null, "materiel.ptNo",
					"<=", tfPtPart1.getText(), ")"));
		}
		//工厂名称
		if (!tfFactoryName.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", null, "materiel.factoryName",
					"=", tfFactoryName.getText(), null));
		}
		//工厂规格
		if (!tfFactorySpec.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", null, "materiel.factorySpec",
					"=", tfFactorySpec.getText(), null));
		}
		// 报关料号不等于空,结束料号为空时
		if (!tfCusPart.getText().trim().equals("")
				&& tfCusPart1.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", null, "statCusNameRelationHsn.complex.code",
					"=", tfCusPart.getText(), null));
		} 
		// 报关料号不等于空,结束料号不为空时
		else if (!tfCusPart.getText().trim().equals("")
				&& !tfCusPart1.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", "(", "statCusNameRelationHsn.complex.code",
					">=", tfCusPart.getText(), null));
			MaterialConditions.add(new Condition("and", null, "statCusNameRelationHsn.complex.code",
					"<=", tfCusPart1.getText(), ")"));
		}
		//报关名称
		if (!tfHsName.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", null, "statCusNameRelationHsn.cusName",
					"=", tfHsName.getText(), null));
		}
		 //报关规格
		if (!tfHsSpec.getText().trim().equals("")) {
			MaterialConditions.add(new Condition("and", null, "statCusNameRelationHsn.cusSpec",
					"=", tfHsSpec.getText(), null));
		}
		return MaterialConditions;
	}
	
	/**
	 * 进出仓帐成品级查询条件
	 * @return
	 */
	private List<Condition> getFinishedCondition() {
		List finishedConditions=new ArrayList<Condition>();
		//生效日期(从当年开始至现在)
		
		finishedConditions.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));

		if (cbDate.getDate() != null) { // 开始日期
			Date endDate = CommonVars.getEndDate(cbDate.getDate());
			
			Date startDate = CommonVars.getBeginDate(new Date(endDate.getYear(),0,1));
			finishedConditions.add(new Condition("and", null,"billMaster.validDate", ">=",startDate, null));
			finishedConditions.add(new Condition("and", null,"billMaster.validDate", "<=",endDate, null));
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
			finishedConditions.add(condition);
		}
		return finishedConditions;
	}
	
	
	/**
	 * 盘点成品查询条件
	 * @return
	 */
	private List<Condition> getFinishedConditionCheck() {
		List finishedConditions=new ArrayList<Condition>();
		//生效日期(从当年开始至现在)
		if (cbDate.getDate() != null) { // 开始日期
			Date date =cbDate.getDate();
			Date beginDate = CommonUtils.getBeginDate(date);
			Date endDate = CommonUtils.getEndDate(date);
			finishedConditions.add(new Condition("and", null,"checkDate", ">=",beginDate, null));
			finishedConditions.add(new Condition("and", null,"checkDate", "<=",endDate, null));
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
			finishedConditions.add(condition);
		}
		return finishedConditions;
	}
	
	
	/**
	 * 盘点  成品折料  查询条件
	 * @return
	 */
	private List<Condition> getMaterialConditionCheck() {
		List conditions=new ArrayList<Condition>();
		//生效日期(从当年开始至现在)
		if (cbDate.getDate() != null) { // 开始日期
			Date date =cbDate.getDate();
			Date beginDate = CommonUtils.getBeginDate(date);
			Date endDate = CommonUtils.getEndDate(date);
			conditions.add(new Condition("and", null,"checkDate", ">=",beginDate, null));
			conditions.add(new Condition("and", null,"checkDate", "<=",endDate, null));
		}
		
		//在产品库存
		conditions.add(new Condition("and", null,"kcType", "=","3", null));
		
		// 工厂料号不等于空,结束料号为空时
		if (!tfPtPart.getText().trim().equals("")
				&& tfPtPart1.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptNo",
					"=", tfPtPart.getText(), null));
			
		} else // 工厂料号不等于空,结束料号不为空时
		if (!tfPtPart.getText().trim().equals("")
				&& !tfPtPart1.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "ptNo",
					">=", tfPtPart.getText(), null));
			conditions.add(new Condition("and", null, "ptNo",
					"<=", tfPtPart1.getText(), ")"));
		}
		//工厂名称
		if (!tfFactoryName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptName",
					"=", tfFactoryName.getText(), null));
		}
		//工厂规格
		if (!tfFactorySpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "ptSpec",
					"=", tfFactorySpec.getText(), null));
		}
		// 报关料号不等于空,结束料号为空时
		if (!tfCusPart.getText().trim().equals("")
				&& tfCusPart1.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "complex.code",
					"=", tfCusPart.getText(), null));
		} 
		// 报关料号不等于空,结束料号不为空时
		else if (!tfCusPart.getText().trim().equals("")
				&& !tfCusPart1.getText().trim().equals("")) {
			conditions.add(new Condition("and", "(", "complex.code",
					">=", tfCusPart.getText(), null));
			conditions.add(new Condition("and", null, "complex.code",
					"<=", tfCusPart1.getText(), ")"));
		}
		//报关名称
		if (!tfHsName.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "name",
					"=", tfHsName.getText(), null));
		}
		 //报关规格
		if (!tfHsSpec.getText().trim().equals("")) {
			conditions.add(new Condition("and", null, "spec",
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
			conditions.add(condition);
		}
		return conditions;
	}
	class Find extends SwingWorker{
		//单据成品查询条件
		List<Condition> finishedConditions = null;
		
		//盘点成品查询条件
		List<Condition> materialConditionsCheck = null;
		
		//原料查询条件
		List<Condition> materialConditions = null;
		
        //排序
		String orderBy = null;
		
        //查询目标表
		String billDetail = null;
		
		//报关名称汇总
		Boolean ishsTaotal = false;
		
		//显示负数结存
		Boolean isShowLess = false;
		
		public Find(List<Condition> finishedConditions,List<Condition> materialConditionsCheck, List<Condition> materialConditions,String orderBy,
				String billDetail,Boolean ishsTaotal,Boolean isShowLess) {
			this.finishedConditions = finishedConditions;
			this.materialConditionsCheck = materialConditionsCheck;
			this.orderBy = orderBy;
			this.billDetail = billDetail;
			this.ishsTaotal = ishsTaotal;
			this.isShowLess = isShowLess;
			this.materialConditions=materialConditions;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgCurrentDifferent.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			//原来的成品当日结存数据去折料
			list = casCheckAction.getCurrentDifferent(new Request(CommonVars.getCurrUser()),
														finishedConditions,
														materialConditionsCheck,
														materialConditions,
														orderBy,getMaterielType(),
														cbSetName.isSelected(),
														cbShowNegative.isSelected(),
														cbFactoryByGroup.isSelected());
			return list;
		}

		@Override
		protected void done() {//更新视图
//			List list= new ArrayList();
			try {
				listResult = (List)this.get();
			} catch (Exception e) {
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(DgCurrentDifferent.this,
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
			btnMimeograph.setLocation(new Point(717, 53));
			btnMimeograph.setSize(new Dimension(86, 25));
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
								InputStream masterReportStream = DgCurrentDifferent.class
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
			btnClose.setLocation(new Point(717, 89));
			btnClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnClose.setSize(new Dimension(86, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentDifferent.this.dispose();
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
							columns.add(addColumn("工厂料号","ptNo", 100));
							columns.add(addColumn("工厂名称规格","ptNameSpec", 100));
							columns.add(addColumn("工厂单位", "ptUnitName", 100));
							columns.add(addColumn("工厂帐存数量", "ptAmount", 100));
							columns.add(addColumn("工厂盘点数量", "ptCheckAmount", 100));
							columns.add(addColumn("工厂数量差异", "ptBalanceAmount", 100));
						}
						columns.add(addColumn("报关编码", "complex.code", 100));
						columns.add(addColumn("报关名称规格", "hsNameSpec", 100));
						columns.add(addColumn("报关单位", "hsUnitName", 100));
						columns.add(addColumn("报关帐存数量", "hsAmount", 100));
						columns.add(addColumn("报关盘点数量", "hsCheckAmount", 100));
						columns.add(addColumn("报关数量差异", "hsBalanceAmount", 100));
						//columns.add(addColumn("物料类别", "materialType", 100));
						if(cbFactoryByGroup.isSelected()){
							columns.add(addColumn("物料仓位", "wareSet.name", 100));
						}
						return columns;
					}
				});
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
			tfPtPart.setBounds(new Rectangle(297, 20, 116, 22));
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
			tfCusPart.setBounds(new Rectangle(297, 66, 116, 22));
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
			tfPtPart1.setBounds(new Rectangle(541, 20, 112, 22));
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
			tfCusPart1.setBounds(new Rectangle(541, 66, 112, 22));
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
			btnFactoryName1.setBounds(new Rectangle(414, 20, 20, 22));
			btnFactoryName1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, MaterielType.MATERIEL,
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
			jButton.setText("...");
			jButton.setBounds(new Rectangle(414, 66, 20, 22));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
								.getFactoryAndFactualCustomsRalationForBalance(false,
										MaterielType.MATERIEL,
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
			jButton1.setText("...");
			//jButton1.setEnabled(false);
			jButton1.setBounds(new Rectangle(654, 20, 20, 22));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, MaterielType.MATERIEL,
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
			jButton2.setText("...");
			//jButton2.setEnabled(false);
			jButton2.setBounds(new Rectangle(654, 66, 20, 22));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//String materialType = getType();
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							MaterielType.MATERIEL,
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
	 * This method initializes btnRelation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRelation() {
		if (btnRelation == null) {
			btnRelation = new JButton();
			btnRelation.setBounds(new Rectangle(717, 129, 86, 25));
			btnRelation.setText("报表关联");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {if (tableModel.getCurrentRow() == null) {
					JOptionPane.showMessageDialog(
							DgCurrentDifferent.this,
							"请选择你要查看的资料", "确认", 1);
					return;
				}
				TempCurrentBillDetailPanDian t = (TempCurrentBillDetailPanDian)tableModel.getCurrentRow();
				sDate = cbDate.getDate();
				sHsCode = t.getComplex().getCode();
				sHsName = t.getHsName();
				sHsSpec = t.getHsSpec();
				
				sPtNo = cbSetName.isSelected()? "":t.getPtNo();
				sPtName = cbSetName.isSelected()? "":t.getPtName();
				sPtSpec = cbSetName.isSelected()? "":t.getPtSpec();
				
				Component comp = (Component) e.getSource();
				getPmRelation().show(comp, 0, comp.getHeight());}
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
			
			pmRelation.add(getMiImpExpDetail());//进出仓帐
			
//			pmRelation.add(getMiThatDayBalance());//当日结存
			
//			pmRelation.add(getMiPandianDifferent());//结存与盘点差额ok2010.06.28
			
			pmRelation.add(getMiCurrentConvert());//在产品结存折料
			
			
			//在产品制单耗用
			pmRelation.add(getMiZhiDanUse());
						
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
					DgCheckSelfImpExpQuery dialog =  
						new DgCheckSelfImpExpQuery();
					dialog.setMaterialType(MaterielType.SEMI_FINISHED_PRODUCT);
					dialog.setWaiFa(false);		
					dialog.setTitle("在产品物料帐");
					dialog.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);					
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
			miThatDayBalance.setText("当日结存");
			miThatDayBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentThatDayBalance dg = new DgCurrentThatDayBalance();
					dg.setVisible(true);
					
					dg.setQueryCondition(null, sDate, "", "", "", sPtNo,sPtName,sPtSpec);
					
					dg.queryData();
					dg.setVisible(true);
				}
			});
		}
		return miThatDayBalance;
	}
	
	
	/**
	 * This method initializes miCurrentConvert	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiCurrentConvert() {
		if (miCurrentConvert == null) {
			miCurrentConvert = new JMenuItem();
			miCurrentConvert.setText("在产品结存折料");
			miCurrentConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentThatDayBalanceToBom dg = new DgCurrentThatDayBalanceToBom();
					
					dg.setTitle("在产品结存折料");
					dg.setQueryConditionM(sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					
					dg.queryData();
					
					dg.setVisible(true);
					
				}
			});
		}
		return miCurrentConvert;
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
					
					
				}
			});
		}
		return miZhiDanUse;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="-12,32"
