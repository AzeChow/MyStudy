/**
 * 库存分析：结存与盘点差额表（通用）
 * @author wss
 */

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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.TempCurrentBillDetailPanDian;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.DgImpExpQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSourceNew;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.selfcheck.DgMaterialThatDayBalance.CheckableItem;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgMaterialDifferent extends JDialogBase {
	private MaterialManageAction materialManageAction = null;
	private static String SELECT_ALL = "全选";  //  @jve:decl-index=0:

	private static String SELECT_NOT_ALL = "不选";  //  @jve:decl-index=0:
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel top = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
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
	 * 物料类型
	 */
	private String materielType = null;  //  @jve:decl-index=0:
	
	/**
	 * 查询action
	 */
	private CasCheckAction casCheckAction = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField tfBeginPtPart = null;
	private JTextField tfEndPtPart = null;
	private JButton btnBeginPtNo = null;
	private JButton btnEndPtNo = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private JTextField tfBeginHsCode = null;
	private JTextField tfEndHsCode = null;
	private JButton btnBeginHsCode = null;
	private JButton btnEndHsCode = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JButton btnRelation = null;
	private JPopupMenu pmRelation = null;  //  @jve:decl-index=0:visual-constraint="807,216"
	private JMenuItem miImpExpDetail = null;  //  @jve:decl-index=0:visual-constraint="811,257"
	private JMenuItem miThatDayBalance = null;  //  @jve:decl-index=0:visual-constraint="812,289"
	private JMenuItem miCurrentConvert = null;  //  @jve:decl-index=0:visual-constraint="812,321"
	private JMenuItem miThatDayConvert = null;  //  @jve:decl-index=0:visual-constraint="812,352"
	private JMenuItem miZhiDanUse = null;  //  @jve:decl-index=0:visual-constraint="813,382"
	
	private JMenuItem miConvert = null;  //  @jve:decl-index=0:visual-constraint="812,321"


	//关联查询时传递 的参数
	private Date sDate;
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
	public DgMaterialDifferent() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		casCheckAction = (CasCheckAction) CommonVars.getApplicationContext().getBean(
		"casCheckAction");
		//this.materielType = type;
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
		this.setTitle("***结存与盘点差额表");
		this.setSize(812, 541);
        this.setContentPane(getJContentPane());
        List list=new ArrayList();
        
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(185);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerSize(8);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(399, 30, 32, 22));
			jLabel5.setText("至");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(399, 78, 32, 22));
			jLabel4.setText("至");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(450, 78, 65, 22));
			jLabel12.setText("报关编码");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(184, 78, 65, 22));
			jLabel11.setText("报关编码");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(450, 30, 65, 22));
			jLabel3.setText("工厂料号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(184, 30, 65, 22));
			jLabel2.setText("工厂料号");
			lbHsSpec = new JLabel();
			lbHsSpec.setText("报关规格");
			lbHsSpec.setBounds(new Rectangle(449, 102, 65, 22));
			lbHsName = new JLabel();
			lbHsName.setText("报关名称");
			lbHsName.setBounds(new Rectangle(184, 102, 65, 22));
			lbFactorySpec = new JLabel();
			lbFactorySpec.setText("工厂规格");
			lbFactorySpec.setBounds(new Rectangle(449, 54, 65, 22));
			lbDate = new JLabel();
			lbDate.setText("报表日期");
			lbDate.setBounds(new Rectangle(184, 126, 65, 22));
			lbFactoryName = new JLabel();
			lbFactoryName.setText("工厂名称");
			lbFactoryName.setBounds(new Rectangle(184, 54, 65, 22));
			lbFactory = new JLabel();
			lbFactory.setText("仓库");
			lbFactory.setBounds(new Rectangle(16, 16, 38, 18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "\u4ed3\u8d26\u9009\u9879", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
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
			jPanel1.add(jLabel2, null);
			jPanel1.add(jLabel3, null);
			jPanel1.add(getTfBeginPtPart(), null);
			jPanel1.add(getTfEndPtPart(), null);
			jPanel1.add(getBtnBeginPtNo(), null);
			jPanel1.add(getBtnEndPtNo(), null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(getTfBeginHsCode(), null);
			jPanel1.add(getTfEndHsCode(), null);
			jPanel1.add(getBtnBeginHsCode(), null);
			jPanel1.add(getBtnEndHsCode(), null);
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
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
			jScrollPane.setBounds(new Rectangle(15, 32, 157, 124));
			jScrollPane.setViewportView(getLsFactory());
		}
		return jScrollPane;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbDate == null) {
			cbDate = new JCalendarComboBox();
			cbDate.setBounds(new Rectangle(250, 126, 135, 22));
		}
		return cbDate;
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
			tfFactoryName.setBounds(new Rectangle(249, 54, 116, 22));
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
			btnFactoryName.setBounds(new Rectangle(365, 54, 20, 22));
			btnFactoryName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false,
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
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
			tfFactorySpec.setBounds(new Rectangle(514, 54, 112, 22));
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
			btnFactorySpec.setBounds(new Rectangle(627, 54, 20, 22));
			btnFactorySpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
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
			tfHsName.setBounds(new Rectangle(249, 102, 116, 22));
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
			btnHsName.setBounds(new Rectangle(365, 102, 20, 22));
			btnHsName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
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
			tfHsSpec.setBounds(new Rectangle(514, 102, 112, 22));
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
			btnHsSpec.setBounds(new Rectangle(627, 102, 20, 22));
			btnHsSpec.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
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
			cbSetName.setBounds(new Rectangle(180, 155, 123, 21));
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
			cbShowNegative.setBounds(new Rectangle(355, 155, 129, 21));
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
				if (cbSetName.isSelected() ? (temp.getHsAmount() != null && temp.getHsAmount() < 0 ):
					(temp.getPtAmount() != null && temp.getPtAmount() < 0)) {
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
			cbFactoryByGroup.setBounds(new Rectangle(513, 155, 107, 21));
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
			btnSelect.setBounds(new Rectangle(665, 30, 97, 25));
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
		//查询
		//报表日期不可不空
		if(cbDate.getDate() == null){
			JOptionPane.showMessageDialog(DgMaterialDifferent.this,
					"您必须选中一个时间！", "注意啦！！！", 0);
			return;
		}
		//组织查询条件
		List<Condition> conditionsBill = new ArrayList<Condition>();
		List<Condition> conditionsCheck = new ArrayList<Condition>();
		
		//外发加工反查  料件级查询条件
		List<Condition> conditionsMateriel = new ArrayList<Condition>();

		//生效的单据
		conditionsBill.add(new Condition("and", null,"billMaster.isValid", "=",new Boolean(true), null));
		//生效日期(从当年开始至现在)
		if (cbDate.getDate() != null) { // 开始日期
			Date d = CommonVars.getBeginDate(cbDate.getDate());
			Date endDate = CommonVars.getEndDate(cbDate.getDate());
			
			Date startDate = CommonVars.getBeginDate(new Date(endDate.getYear(),0,1));
			
			conditionsBill.add(new Condition("and", null,"billMaster.validDate", ">=",startDate, null));
			conditionsBill.add(new Condition("and", null,"billMaster.validDate", "<=",endDate, null));
			
			conditionsCheck.add(new Condition("and", null,"checkDate", ">=",d, null));
			conditionsCheck.add(new Condition("and", null,"checkDate", "<=",endDate, null));
		}
		// 开始工厂料号不等于空,结束工厂料号为空时
		if (!tfBeginPtPart.getText().trim().equals("")
				&& tfEndPtPart.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "ptPart",
					"=", tfBeginPtPart.getText(), null));
			}
			

			conditionsCheck.add(new Condition("and", null, "ptNo",
					"=", tfBeginPtPart.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "materiel.ptNo",
					"=", tfBeginPtPart.getText(), null));
			
		}
		// 开始工厂料号不等于空,结束工厂料号不为空时
		else if (!tfBeginPtPart.getText().trim().equals("")
				&& !tfEndPtPart.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", "(", "ptPart",
						">=", tfBeginPtPart.getText(), null));
				conditionsBill.add(new Condition("and", null, "ptPart",
						"<=", tfEndPtPart.getText(), ")"));
			}
			
			
			conditionsCheck.add(new Condition("and", "(", "ptNo",
					">=", tfBeginPtPart.getText(), null));
			conditionsCheck.add(new Condition("and", null, "ptNo",
					"<=", tfEndPtPart.getText(), ")"));
			
			conditionsMateriel.add(new Condition("and", "(", "materiel.ptNo",
					">=", tfBeginPtPart.getText(), null));
			conditionsMateriel.add(new Condition("and", null, "materiel.ptNo",
					"<=", tfEndPtPart.getText(), ")"));

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
		// 开始报关料号不等于空,结束料号为空时
		if (!tfBeginHsCode.getText().trim().equals("")
				&& tfEndHsCode.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", null, "complex.code",
					"=", tfBeginHsCode.getText(), null));
			}
			
			conditionsCheck.add(new Condition("and", null, "complex.code",
					"=", tfBeginHsCode.getText(), null));
			
			conditionsMateriel.add(new Condition("and", null, "statCusNameRelationHsn.complex.code",
					"=", tfBeginHsCode.getText(), null));
			

		} else // 报关料号不等于空,结束料号不为空时
		if (!tfBeginHsCode.getText().trim().equals("")
				&& !tfEndHsCode.getText().trim().equals("")) {
			
			//外发加工的是料件级反查，所以不能直接加此条件
			if(!"outSource".equals(materielType)){
				conditionsBill.add(new Condition("and", "(", "complex.code",
						">=", tfBeginHsCode.getText(), null));
				conditionsBill.add(new Condition("and", null, "complex.code",
						"<=", tfEndHsCode.getText(), ")"));
			}
			
			conditionsCheck.add(new Condition("and", "(", "complex.code",
					">=", tfBeginHsCode.getText(), null));
			conditionsCheck.add(new Condition("and", null, "complex.code",
					"<=", tfEndHsCode.getText(), ")"));

			conditionsMateriel.add(new Condition("and", "(", "statCusNameRelationHsn.complex.code",
					">=", tfBeginHsCode.getText(), null));
			conditionsMateriel.add(new Condition("and", null, "statCusNameRelationHsn.complex.code",
					"<=", tfEndHsCode.getText(), ")"));
			
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
//		List<String> checkableCodeList = new ArrayList<String>();
		for (int j = 0; j < lsFactory.getModel().getSize(); j++) {
			Object value = lsFactory.getModel().getElementAt(j);
			if (value instanceof CheckableItem) {
				CheckableItem item = (CheckableItem) value;
				if (item.isSelected) {
					checkableItemList.add(item);
//					checkableCodeList.add(item.getCode());
				}
			}
		}
		
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
		
		//casCondition.setIsGroup(cbFactoryByGroup.isSelected());
		//casCondition.setIshsTaotal(cbSetName.isSelected());
		//casCondition.setIsShowLess(cbShowNegative.isSelected());
		
		//String billDetail = BillUtil.getBillDetailTableNameByMaterielType(getMaterielType());
        //执行查询线程
		new Find(conditionsBill,
				conditionsCheck,
				"",
				getMaterielType(),
				cbSetName.isSelected(),
				cbShowNegative.isSelected(),
				cbFactoryByGroup.isSelected(),
				conditionsMateriel,
				cbDate.getDate())
									.execute();
		
		
	
	}
	
	class Find extends SwingWorker{
		//查询条件
		List<Condition> conditionsBill = null;
		List<Condition> conditionsCheck = null;
        //排序
		String orderBy = null;
        //查询目标表
		String billDetail = null;
		//报关名称汇总
		Boolean ishsTaotal = false;
		//显示负数结存
		Boolean isShowLess = false;
		
		Boolean isGroup = false;
		
		List<Condition> conditionsMateriel = null;
		
		Date date;
		CasCondition casCondition = null;
		
		public Find(List<Condition> conditionsBill,List<Condition> conditionsCheck, String orderBy,
				String billDetail,Boolean ishsTaotal,Boolean isShowLess,Boolean isGroup,
				List<Condition> conditionsMateriel,Date date) {
			this.conditionsBill = conditionsBill;
			this.conditionsCheck = conditionsCheck;
			this.orderBy = orderBy;
			//this.billDetail = billDetail;
			this.ishsTaotal = ishsTaotal;
			this.isShowLess = isShowLess;
			this.isGroup = isGroup;
			this.conditionsMateriel = conditionsMateriel;
			this.date = date;
		}

		@Override
		protected Object doInBackground() throws Exception {//后台计算
			//查询返回结果
			List list = null;
			//查询
			CommonProgress.showProgressDialog(DgMaterialDifferent.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			String orderBy = "";
			if( "outSource".equals(getMaterielType())){
				list = casCheckAction.getOutSourcePanDian(conditionsBill,
														conditionsCheck, 
														orderBy, 
														getMaterielType(),
														ishsTaotal, 
														isShowLess, 
														isGroup,
														conditionsMateriel, 
														cbDate.getDate());
			}else{
				list = casCheckAction.getCurrentBillDetailPanDian(new Request(CommonVars.getCurrUser()),
																conditionsBill,
																conditionsCheck,
																orderBy,
																getMaterielType(),
																ishsTaotal,
																false,//显示负数结存改在客户端判断
																isGroup,
																cbDate.getDate());
			}
			
			System.out.println("结果："+list.size());
			return list;
		}

		@Override
		protected void done() {//更新视图
//			List list = null;
			try {
				listResult = (List)this.get();
				System.out.println("wss测试更新视图中list大小为" + listResult.size());
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(DgMaterialDifferent.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			}
//			if(list == null){
//				list = new ArrayList();
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
			btnMimeograph.setBounds(new Rectangle(665, 65, 97, 25));
			btnMimeograph.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printData();
				}
			});
		
		}
		return btnMimeograph;
	}

	/**
	 * 打印报表
	 */
	
	public void printData(){
		try {
			List list = tableModel.getList();
			CustomReportDataSourceNew ds = new CustomReportDataSourceNew(
					list);
	

			//1.全不选
			String str = "report/dayBalancePD.jasper";
			//选了
			if(cbSetName.isSelected() || cbFactoryByGroup.isSelected()){
				if(!cbFactoryByGroup.isSelected()){//2.只选了报关名称汇总
					str = "report/dayBalancePDCus.jasper";
				}else if(!cbSetName.isSelected()){//3.只选了仓库分组
					str = "report/dayBalancePDGroup.jasper";
				}else{//4.两者都选了
					str = "report/dayBalancePDCusAndGroup.jasper";
				}
			}
			
			InputStream masterReportStream = DgMaterialDifferent.class
					.getResourceAsStream(str);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("title", getTitle());
//			parameters.put("type", getMaterielType());
			parameters.put("date", cbDate.getDate());
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
	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(665, 100, 97, 25));
			btnClose.setMnemonic(KeyEvent.VK_UNDEFINED);
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
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
//			jTable.getColumnModel().getColumn(13).setCellRenderer(
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
	 * @param intOutFlat
	 *            The intOutFlat to set.
	 */
	public void setMaterielType(String intOutFlat) {
		this.materielType = intOutFlat;
	}
	/**
	 * This method initializes tfBeginPtPart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBeginPtPart() {
		if (tfBeginPtPart == null) {
			tfBeginPtPart = new JTextField();
			tfBeginPtPart.setBounds(new Rectangle(249, 30, 116, 22));
		}
		return tfBeginPtPart;
	}
	/**
	 * This method initializes tfEndPtPart	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEndPtPart() {
		if (tfEndPtPart == null) {
			tfEndPtPart = new JTextField();
			tfEndPtPart.setBounds(new Rectangle(514, 30, 112, 22));
			//tfEndPtPart.setEnabled(false);
		}
		return tfEndPtPart;
	}
	/**
	 * This method initializes btnBeginPtNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBeginPtNo() {
		if (btnBeginPtNo == null) {
			btnBeginPtNo = new JButton();
			btnBeginPtNo.setBounds(new Rectangle(365, 30, 20, 22));
			btnBeginPtNo.setText("...");
			btnBeginPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
																new ArrayList(),"ptNo");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfBeginPtPart.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnBeginPtNo;
	}
	/**
	 * This method initializes btnEndPtNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEndPtNo() {
		if (btnEndPtNo == null) {
			btnEndPtNo = new JButton();
			btnEndPtNo.setBounds(new Rectangle(627, 30, 20, 22));
			btnEndPtNo.setText("...");
			//btnEndPtNo.setEnabled(false);
			btnEndPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
																	new ArrayList(),"ptNo");
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						tfEndPtPart.setText(m == null ? "" : m.getPtNo());
					}
				}
			});
		}
		return btnEndPtNo;
	}
	/**
	 * This method initializes tfBeginHsCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBeginHsCode() {
		if (tfBeginHsCode == null) {
			tfBeginHsCode = new JTextField();
			tfBeginHsCode.setBounds(new Rectangle(249, 78, 116, 22));
		}
		return tfBeginHsCode;
	}
	/**
	 * This method initializes tfEndHsCode	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfEndHsCode() {
		if (tfEndHsCode == null) {
			tfEndHsCode = new JTextField();
			tfEndHsCode.setBounds(new Rectangle(514, 78, 112, 22));
			//tfEndHsCode.setEnabled(false);
		}
		return tfEndHsCode;
	}
	/**
	 * This method initializes btnBeginHsCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBeginHsCode() {
		if (btnBeginHsCode == null) {
			btnBeginHsCode = new JButton();
			btnBeginHsCode.setBounds(new Rectangle(365, 78, 20, 22));
			btnBeginHsCode.setText("...");
			btnBeginHsCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
											new ArrayList(),"hsNo");
					
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						tfBeginHsCode.setText((String)sm.getObject3());
					}
				}
			});
		}
		return btnBeginHsCode;
	}
	/**
	 * This method initializes btnEndHsCode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEndHsCode() {
		if (btnEndHsCode == null) {
			btnEndHsCode = new JButton();
			btnEndHsCode.setBounds(new Rectangle(627, 78, 20, 22));
			btnEndHsCode.setText("...");
			//btnEndHsCode.setEnabled(false);
			btnEndHsCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<TempObject> list = CasQuery.getInstance()
					.getFactoryAndFactualCustomsRalationForBalance(false, 
							"currentTotal".equals(getMaterielType())? MaterielType.MATERIEL:getMaterielType(),
														new ArrayList(),"hsNo");

					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						tfEndHsCode.setText((String)sm.getObject3());
					}
				}
			});
		}
		return btnEndHsCode;
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
		this.tfBeginHsCode.setText(hsCode);
		this.tfEndHsCode.setText("");
		this.tfHsName.setText(hsName);
		this.tfHsSpec.setText(hsSpec);
		
		
		this.tfBeginPtPart.setText(ptNo);
		this.tfEndPtPart.setText("");
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
			btnRelation.setBounds(new Rectangle(665, 135, 97, 25));
			btnRelation.setText("关联报表");
			btnRelation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgMaterialDifferent.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					TempCurrentBillDetailPanDian t = (TempCurrentBillDetailPanDian)tableModel.getCurrentRow();
					sDate = cbDate.getDate();
					sHsCode = t.getComplex().getCode();
					sHsName = t.getHsName();
					sHsSpec = t.getHsSpec();
					
					sPtNo = cbSetName.isSelected()?"":t.getPtNo();
					sPtName = cbSetName.isSelected()?"":t.getPtName();
					sPtSpec = cbSetName.isSelected()?"":t.getPtSpec();
					
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
			
			//物料帐
			pmRelation.add(getMiImpExpDetail());
			//当日结存
			pmRelation.add(getMiThatDayBalance());
			
			
			if("currentTotal".equals(materielType)){
				pmRelation.add(getMiCurrentConvert());//在产品折料情况表 
				pmRelation.add(getMiZhiDanUse());//在产品制单耗用情况
			}
			
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
					dialog.setWaiFa(isWaiFa);
					dialog.setMaterialType(type);
					dialog.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					//dialog.setModel(false);
					dialog.setTitle(title);
					dialog.setVisible(true);
					dialog.queryData();
				}
			});
		}
		return miImpExpDetail;
	}
	/**
	 * This method initializes miThatDayBalance	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMiThatDayBalance() {
		if (miThatDayBalance == null) {
			miThatDayBalance = new JMenuItem();
			miThatDayBalance.setText("当日结存表");
			miThatDayBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
					
					String title = "";
					if(MaterielType.MATERIEL == materielType){
						title = "原材料当日结存表";
					}
					//在产品
					else if("currentTotal" == materielType){
						title = "在产品当日结存表";
					}
					//产成品
					else if(MaterielType.FINISHED_PRODUCT == materielType){
						title = "产成品当日结存表";
					}
					//外发加工
					else if("outSource" == materielType){
						title = "外发加工当日结存表";
					}
					//残次品
					else if(MaterielType.BAD_PRODUCT == materielType){
						title = "残次品当日结存表";
					}
					
					dg.setTitle(title);
					
					dg.setMaterielType(materielType);
					dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
					
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
			miCurrentConvert.setText("结存折料情况");
			miCurrentConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					
//					
//					dg.setQueryCondition(null, sDate, sHsCode, sHsName, sHsSpec, sPtNo,sPtName,sPtSpec);
//					dg.setTitle("在产品折料情况表");
//					
//					dg.queryData();
//					
//					dg.setVisible(true);
				}
			});
		}
		return miCurrentConvert;
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
					
				}
			});
		}
		return miZhiDanUse;
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

}  //  @jve:decl-index=0:visual-constraint="-12,32"
