package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JInternalFrameBase;


public class FmOuterObtainFecavBill extends JInternalFrameBase {

	private JPanel			jContentPane	= null;
	private JToolBar		jToolBar		= null;
	private JScrollPane		jScrollPane		= null;
	private JTable			jTable			= null;
	private JButton			btnAdd			= null;
	private JButton			btnDelete		= null;
	private JButton			btnRefresh		= null;
	private JButton			btnExit			= null;
	private JTableListModel	tableModel		= null;
	private FecavAction		fecavAction		= null;
	private JButton			btnSearch		= null;
	private JButton			btnPrint		= null;
	private JLabel lbCount = null;
	/**
	 * This method initializes
	 * 
	 */
	public FmOuterObtainFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(625, 365));
		this.setContentPane(getJContentPane());
		this.setTitle("外部领单");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						List list = fecavAction.findFecavBill(
								new Request(CommonVars.getCurrUser()));
//						List list = fecavAction.findFecavBillByState(new Request(
//								CommonVars.getCurrUser()), FecavState.OUTER_OBTAIN);
						initTable(list);
						showCount(list);
					
					}
				});
   
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbCount = new JLabel();
			lbCount.setText("JLabel");
			lbCount.setForeground(new java.awt.Color(0,51,255));
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(lbCount, java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	   private void showCount(List list){
//		   List list = fecavAction.findFecavBillNotOuterObtain(
//					new Request(CommonVars.getCurrUser()));
		   this.lbCount.setText("   总份数："+list.size()  );
	   }
	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());			
		}
		return jToolBar;
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

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBatchOuterObtainFecavBill dg = new DgBatchOuterObtainFecavBill();
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null && list.size() > 0) {
						tableModel.addRows(list);
					}
					showCount(tableModel.getList());
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							FmOuterObtainFecavBill.this, "你确定要删除这些数据吗？", "提示",
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmOuterObtainFecavBill.this, "请选择你要删除的数据",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = tableModel.getCurrentRows();
					for(int i=list.size()-1;i>=0;i--){
						FecavBill fecavBill=(FecavBill)list.get(i);
						if(FecavState.OUTER_OBTAIN!=fecavBill.getBillState()){
							list.remove(i);
						}
					}
					fecavAction.deleteFecavBill(new Request(CommonVars
							.getCurrUser()), list);
					tableModel.deleteRows(list);
					showCount(tableModel.getList());
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = fecavAction.findFecavBill(
							new Request(CommonVars.getCurrUser()));
//					List list = fecavAction.findFecavBillByState(new Request(
//							CommonVars.getCurrUser()), FecavState.OUTER_OBTAIN);
					initTable(list);
					showCount(list);
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmOuterObtainFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initTable(List list) {
		 JTableListModel.dataBind(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("核销单号 ", "code", 200));
						list.add(addColumn("领单人", "outerObtain", 100));
						list.add(addColumn("领单日期", "outerObtainDate", 100));
						list.add(addColumn("操作人", "outerOperator", 100));
						list.add(addColumn("操作日期", "outerOperatorDate", 100));
						list.add(addColumn("标志", "billState", 150));
						return list;
					}
				});
		TableColumn column = this.jTable.getColumnModel().getColumn(6);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				int state = -1;
				if (value != null) {
					state = Integer.parseInt(value.toString());
				}
				if(state == FecavState.OUTER_OBTAIN){
					this.setText("内部未领用");
				}else{
					this.setText("内部已领用");
				}				
				return this;
			}
		});
		tableModel =(JTableListModel)jTable.getModel();
//		CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
//				this.tfFieldValue, this.btnSearch, this.tableModel);		
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFecavBillQuery dg=new DgFecavBillQuery();
					dg.setFecavState(FecavState.OUTER_OBTAIN);
					dg.setVisible(true);
					if(dg.isOk()){
						List list=dg.getLsResult();
						initTable(list);
						showCount(list);
					}
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = FmOuterObtainFecavBill.class
							.getResourceAsStream("report/OuterObtainFecavBill.jasper");
					// SimpleDateFormat dateFormat = new SimpleDateFormat(
					// "yyyy-MM-dd");
					// Map<String, Object> parameters = new HashMap<String,
					// Object>();
					// parameters.put("isImport", new Boolean(false));
					// parameters.put("contractNo",
					// contract.getExpContractNo());
					// parameters.put("emsNo", contract.getEmsNo());
					// parameters.put("beginDate",
					// contract.getBeginDate() == null ? "" : dateFormat
					// .format(contract.getBeginDate()));
					// parameters.put("effectiveDate",
					// contract.getEndDate() == null ? "" : dateFormat
					// .format(contract.getEndDate()));
					// parameters.put("companyName", CommonVars.getCurrUser()
					// .getCompany().getName());
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, null, ds);
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

} // @jve:decl-index=0:visual-constraint="10,10"
