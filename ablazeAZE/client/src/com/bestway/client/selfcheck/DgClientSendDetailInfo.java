
package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JTextField;
import java.awt.Dimension;

/**
 * @author yp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgClientSendDetailInfo extends JDialogBase {

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JButton btnReloadSearch = null;

	private JButton btnClose = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;


	private JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;


	/**
	 * This method initializes
	 * 
	 */
	public DgClientSendDetailInfo() {
		super(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(727, 558);
		this.setContentPane(getJContentPane());
		this.setTitle("送货明细表");
		//this.getButtonGroup();
		initTable(new Vector());
	}



	/**
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(327, 73, 52, 15));
			jLabel6.setText("合同规格");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(327, 53, 52, 15));
			jLabel5.setText("合同名称");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(64, 73, 52, 15));
			jLabel4.setText("内部规格");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(64, 53, 52, 15));
			jLabel3.setText("内部名称");
			jLabel2 = new JLabel();
			jLabel2.setText("客户名称");
			jLabel2.setBounds(new Rectangle(63, 11, 52, 15));
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new Rectangle(328, 32, 52, 15));
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel
					.setBorder(
							BorderFactory.createTitledBorder(null, 
									"过滤条件", 
									TitledBorder.DEFAULT_JUSTIFICATION, 
									TitledBorder.DEFAULT_POSITION, 
									new Font("Dialog", Font.BOLD, 12), 
									Color.blue));
			jLabel.setText("开始日期");
			jLabel.setBounds(new Rectangle(64, 32, 52, 15));
			jPanel.add(jLabel2, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfInnerName(), null);
			jPanel.add(getTfInnerSpec(), null);
			jPanel.add(getTfContractName(), null);
			jPanel.add(getTfContractSpec(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes endDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(85, 22));
			cbbBeginDate.setBounds(new Rectangle(123, 32, 150, 20));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnReloadSearch == null) {
			btnReloadSearch = new JButton();
			btnReloadSearch.setText("查询");
			btnReloadSearch.setBounds(new Rectangle(577, 11, 60, 26));
			btnReloadSearch.setPreferredSize(new java.awt.Dimension(60, 26));
		}
		return btnReloadSearch;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(577, 67, 60, 26));
			btnClose.setPreferredSize(new java.awt.Dimension(60, 26));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgClientSendDetailInfo.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new GroupableHeaderTable();
		}
		return jTable;
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
	 * This method initializes pnContent
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(85, 22));
			cbbEndDate.setBounds(new Rectangle(384, 32, 150, 20));
		}
		return cbbEndDate;
	}

	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("日期", "validDate", 100));
				list.add(addColumn("单据号", "billNo", 180));
				list.add(addColumn("工厂料号", "ptPart", 100));
				list.add(addColumn("工厂品名", "ptName", 100));
				list.add(addColumn("工厂规格", "ptSpec", 120));
				list.add(addColumn("工厂单位", "ptUnit.name", 100));
				list.add(addColumn("采购业务订单号", "poSoBillNo", 180));
				list.add(addColumn("工厂数量", "ptRecvAmount", 180));
				list.add(addColumn("报关数量", "hsRecvAmount", 100));
				list.add(addColumn("工厂数量", "ptBackAmount", 100));
				list.add(addColumn("报关数量", "hsBackAmount", 100));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("报关名称", "hsName", 100));
				list.add(addColumn("报关单位", "hsUnit.name", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gOriginal = new ColumnGroup("摘要");
		gOriginal.add(cm.getColumn(1));
		gOriginal.add(cm.getColumn(2));
		gOriginal.add(cm.getColumn(3));
		gOriginal.add(cm.getColumn(4));
		gOriginal.add(cm.getColumn(5));
		gOriginal.add(cm.getColumn(6));
		gOriginal.add(cm.getColumn(7));
		//ColumnGroup gSend = new ColumnGroup(jRadioButton.isSelected() ? "收货"
		//		: "送货");
		//gSend.add(cm.getColumn(8));
		//gSend.add(cm.getColumn(9));
		ColumnGroup gBack = new ColumnGroup("退货");
		gBack.add(cm.getColumn(10));
		gBack.add(cm.getColumn(11));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gOriginal);
		//header.addColumnGroup(gSend);
		header.addColumnGroup(gBack);
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(8).setFractionCount(2);
		xs.get(10).setFractionCount(2);
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new tableCellRender());
	}
	
	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value == null) {
				this.setText("0.00");
			}else{
				if(value.equals(""))
					this.setText("0.00");
			}
			return this;
		}
	}

	private JLabel jLabel2 = null;

	private JComboBox jComboBox = null;

	//private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="792,76"

	private JButton jButton = null;

	private JSplitPane jSplitPane = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JTextField tfInnerName = null;

	private JTextField tfInnerSpec = null;

	private JTextField tfContractName = null;

	private JTextField tfContractSpec = null;



	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(115, 22));
			jComboBox.setBounds(new Rectangle(123, 11, 225, 20));
		}
		return jComboBox;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	/*
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			//buttonGroup.add(this.getJRadioButton());
			//buttonGroup.add(this.getJRadioButton1());
		}
		return buttonGroup;
	}
	*/

	/**
	 * This method initializes btnCustomSort	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");
			jButton.setBounds(new Rectangle(577, 39, 60, 26));
			jButton.setPreferredSize(new java.awt.Dimension(60, 26));
		}
		return jButton;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(98);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setEnabled(false);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes tfInnerName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInnerName() {
		if (tfInnerName == null) {
			tfInnerName = new JTextField();
			tfInnerName.setBounds(new Rectangle(123, 53, 150, 20));
		}
		return tfInnerName;
	}

	/**
	 * This method initializes tfInnerSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfInnerSpec() {
		if (tfInnerSpec == null) {
			tfInnerSpec = new JTextField();
			tfInnerSpec.setBounds(new Rectangle(123, 73, 150, 20));
		}
		return tfInnerSpec;
	}

	/**
	 * This method initializes tfContractName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfContractName() {
		if (tfContractName == null) {
			tfContractName = new JTextField();
			tfContractName.setBounds(new Rectangle(384, 53, 150, 20));
		}
		return tfContractName;
	}

	/**
	 * This method initializes tfContractSpec	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfContractSpec() {
		if (tfContractSpec == null) {
			tfContractSpec = new JTextField();
			tfContractSpec.setBounds(new Rectangle(384, 73, 150, 20));
		}
		return tfContractSpec;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
