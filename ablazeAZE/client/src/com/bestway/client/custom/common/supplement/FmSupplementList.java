/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common.supplement;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.message.DgProcessCustomsMessageTCS;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.RepDeclarationType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author chl
 */
@SuppressWarnings( { "unchecked", "serial" })
public class FmSupplementList extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected BaseCustomsDeclaration customsDeclaration = null; // @jve:decl-index=0:

	protected JPanel jContentPane = null;

	protected JToolBar jToolBar = null;

	protected JTable jTable = null;

	protected JScrollPane jScrollPane = null;

	protected JButton btnAdd = null;

	protected JButton btnAddComminfo = null;

	protected JButton btnEdit = null;

	protected JButton btnClose = null;

	protected JTableListModel tableModel = null;

	private BaseEncAction baseEncAction;
	
	protected JButton btnDelete = null;

	private JButton btnBrowse = null;

	private JButton btnTcs = null;

	private JPanel jPanel2 = null;

	protected static int projectType = ProjectType.BCUS;
	

	private JButton btnBrowseAll = null;

	private JButton btnFlow = null;

	private JPanel pnQuery = null;

	private JLabel lbDeclarationDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel lbTo = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnQuery = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmSupplementList() {
		init();
	}
	
	public FmSupplementList(int projectType) {
		FmSupplementList.projectType = projectType;
		init();
	}
	
	public void init() {
		switch (projectType) {
		case ProjectType.BCUS:
			baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
					.getBean("encAction");
			break;
		case ProjectType.BCS:
			baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
					.getBean("contractExeAction");
			break;
		case ProjectType.DZSC:
			baseEncAction = (BaseEncAction) CommonVars.getApplicationContext()
					.getBean("dzscContractExeAction");
			break;
		}
		initialize();
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setTitle("补充申报单");
		this.setContentPane(getJContentPane());
		this.setSize(880, 397);
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						initUIComponents();
						initTable(getDataSource());
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(45, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmSupplementList.this, "请选择要删除的数据行!",
								"提示", 0);
						return;
					}
					deleteData();
				}

				
			});
		}
		return btnDelete;
	}
		/**
		 * 删除
		 */
		private void deleteData() {
			List idList = new ArrayList();
			if (tableModel.getCurrentRows() != null) {
				if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
					return;
				}
				List<Object[]> list = tableModel.getCurrentRows();
				for (Object[] data : list) {	
					String id =data[data.length - 2].toString();
					Integer num = Integer.valueOf(data[data.length - 4].toString());
					if(num.equals(SupplmentType.SEND_N)){
						idList.add(data);
					this.baseEncAction.deleteDecSupplementList(new Request(CommonVars
							.getCurrUser()),id);
					}else{
						JOptionPane.showMessageDialog(this, "申报已发送，不可删除!", "提示", 0);
					}

				}
				tableModel.deleteRows(idList);
			} else {
				JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示", 0);
			}
		}
	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	protected JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(512, 33));
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnAddComminfo());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnBrowseAll());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnTcs());
			jToolBar.add(getBtnFlow());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	protected JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						showSupplement(DataState.BROWSE);
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 查看补充单资料
	 * 
	 * @param dataState
	 */
	protected void showSupplement(int dataState) {
		List idList = new ArrayList();
		if (tableModel.getCurrentRows() != null) {
			List<Object[]> list = tableModel.getCurrentRows();
			Object[] data = list.get(0);
			String id =data[data.length - 2].toString();
			String baseCustomsDeclarationCommInfoid=data[data.length - 3].toString();
			String commSerialNo = data[5].toString();
			// 补充申报类型
			int customsDeclarationsupplmentType = Integer.parseInt(data[2].toString());
			
			BaseCustomsDeclaration baseCustomsDeclaration = (BaseCustomsDeclaration)data[data.length - 1];
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo = new BaseCustomsDeclarationCommInfo();
			baseCustomsDeclarationCommInfo.setId(baseCustomsDeclarationCommInfoid);
			baseCustomsDeclarationCommInfo.setCommSerialNo(Integer.valueOf(commSerialNo));
			String supplmentType = data[3].toString();
			List<DecSupplementList> decSupplementList = this.baseEncAction.getDecSupplementListById(new Request(CommonVars
					.getCurrUser()),id);
			DecSupplementList supplement = decSupplementList.get(0);
		    DgReplenishDeclareCommonInfo dgReplenishDeclareCommonInfo = new DgReplenishDeclareCommonInfo(supplmentType, baseCustomsDeclaration,baseCustomsDeclarationCommInfo,dataState);
		    dgReplenishDeclareCommonInfo.setCustomsDeclarationsupplmentType(customsDeclarationsupplmentType);
		    dgReplenishDeclareCommonInfo.entityToui(supplement);
		    dgReplenishDeclareCommonInfo.setVisible(true);
			tableModel.updateRows(idList);
		} else {
			JOptionPane.showMessageDialog(null, "请选择要查看的资料!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	protected JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(45, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] data =null;
					addCustomsDeclarationData(1, data);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 新增补充报关单单资料
	 * 
	 * @param dataState
	 */
	protected void addCustomsDeclarationData(int step, Object[] prama) {
		Integer supplmentType = null;
		BaseCustomsDeclaration baseCustomsDeclaration =null;
		DgSelectCustomsDeclarationType dgSelectCustomsDeclarationType = null;
	    if(prama!=null){
	    	supplmentType = Integer.valueOf(prama[2].toString());
	    	baseCustomsDeclaration = (BaseCustomsDeclaration)prama[prama.length-1];
	    }
		String supType = null;
		DgSelectCustomsDeclaration dgSelectCustomsDeclaration = null;
		DgSelectCustomsDeclarationCommInfo dgSelectCustomsDeclarationCommInfo = null;
		BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo = null;
		DgSelectDeclareCommonInfoType dgSelectDeclareCommonInfoType = null;
		DgReplenishDeclareCommonInfo dgReplenishDeclareCommonInfo = null;
		while (true) {
			if (step == 1) {
				if (dgSelectCustomsDeclarationType == null) {
					dgSelectCustomsDeclarationType = new DgSelectCustomsDeclarationType();
				}
				dgSelectCustomsDeclarationType.setVisible(true);
				supplmentType = dgSelectCustomsDeclarationType.getSupplment();
				if (dgSelectCustomsDeclarationType.result == 0) {
					break;
				}

				
				if (dgSelectCustomsDeclaration != null) {
					dgSelectCustomsDeclaration.dispose();
				}
				step += dgSelectCustomsDeclarationType.result;
			} else if (step == 2) {
				dgSelectCustomsDeclaration = new DgSelectCustomsDeclaration(
						projectType, supplmentType == 2 ? null : supplmentType);
				dgSelectCustomsDeclaration.setVisible(true);
				if (dgSelectCustomsDeclaration.result == 0) {
					break;
				}
				if (dgSelectCustomsDeclarationCommInfo != null) {
					dgSelectCustomsDeclarationCommInfo.dispose();
				}
				baseCustomsDeclaration = dgSelectCustomsDeclaration
						.getBaseCustomsDeclaration();
				step += dgSelectCustomsDeclaration.result;
			} else if (step == 3) {
				dgSelectCustomsDeclarationCommInfo = new DgSelectCustomsDeclarationCommInfo(
						projectType, supplmentType, baseCustomsDeclaration);
				dgSelectCustomsDeclarationCommInfo.setVisible(true);

				if (dgSelectCustomsDeclarationCommInfo.result == 0) {
					break;
				}
				
				baseCustomsDeclarationCommInfo = dgSelectCustomsDeclarationCommInfo
						.getBaseCustomsDeclarationCommInfo();
				step += dgSelectCustomsDeclarationCommInfo.result;
			} else if (step == 4) {
				dgSelectDeclareCommonInfoType = new DgSelectDeclareCommonInfoType(
						supplmentType, baseCustomsDeclaration, baseCustomsDeclarationCommInfo);
				dgSelectDeclareCommonInfoType.setVisible(true);
				if (dgSelectDeclareCommonInfoType.result == 0) {
					break;
				}
				supType = dgSelectDeclareCommonInfoType.getSupType();
				step += dgSelectDeclareCommonInfoType.result;
			} else if (step == 5) {
				dgReplenishDeclareCommonInfo = new DgReplenishDeclareCommonInfo(
						supType,baseCustomsDeclaration, baseCustomsDeclarationCommInfo,supplmentType);
				
				/*
				 * 公共信息模块，当第一次增加报关单的一个商品序号后，
				 * 公共信息面版栏位值已填写，要求在增加同一份报关单中的其它商品信息时。
				 * 公共信息面版栏位值自动带第一个商品信息的公共信息值。
				 */
				List<DecSupplementList> list = baseEncAction
						.getDecSupplementList(new Request(CommonVars
								.getCurrUser()), baseCustomsDeclaration.getId());
				if (list != null && list.size() > 0) {
					DecSupplementList supplement = list.get(0);
					dgReplenishDeclareCommonInfo.entityToui(supplement);
				}

				dgReplenishDeclareCommonInfo.setVisible(true);
				if (dgReplenishDeclareCommonInfo.result == 0) {
					break;
				}
			} else {
				break;
			}
		}
		initTable(getDataSource());
	}

	/**
	 * This method initializes btnAddComminfo
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnAddComminfo() {
		if (btnAddComminfo == null) {
			btnAddComminfo = new JButton();
			btnAddComminfo.setText("新增商品");
			btnAddComminfo.setToolTipText("在现有补充申报单上，新增补充申报商品。");
			btnAddComminfo.setPreferredSize(new Dimension(70, 30));
			btnAddComminfo.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要新增的申报单!", "提示",
								1);
						return;
					}
					addComminfo();
				}	
			});
		}
		return btnAddComminfo;
	}


	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(45, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要修改的资料!", "提示",
								1);
						return;
					}
					showSupplement(DataState.EDIT);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(45, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmSupplementList.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	protected List getDataSource() {
 	    return baseEncAction.queryDecSupplementList(new Request(
			CommonVars.getCurrUser()), cbbBeginDate.getDate(), cbbEndDate.getDate());
		
	}

	protected void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						String emsSpec = "";
						switch (projectType) {
						case ProjectType.BCS:
							emsSpec = "手册编号";
							break;
						case ProjectType.BCUS:
							emsSpec = "账册编号";
							break;
						case ProjectType.DZSC:
							emsSpec = "手册编号";
							break;
						}
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "serialNumber", 40,
								Integer.class));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("补充申报单类型", "supplmentType", 120));
						list
								.add(addColumn("商品申报补充类型", "supType",
										120));
						list.add(addColumn("进出口类型", "impExpType", 80));
						list.add(addColumn("商品序号", "commSerialNo", 60));
						list.add(addColumn("商品编码", "complex", 100));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 100));
						list.add(addColumn("申报日期", "declarationDate", 80));
						list.add(addColumn(emsSpec, "emsHeadH2k", 100));// "帐册编号"
						list.add(addColumn("申报标志", "isSend", 80));// "帐册编号"
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = SupplmentType.getSupplmentTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = RepDeclarationType.getSupType(value.toString());
						}
						this.setText(str);
						return this;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});
		
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = CommonUtils.getDate((Date) value, "yyyy-MM-dd");
						}
						this.setText(str);
						return this;
					}
				});
		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new DefaultTableCellRenderer() {
					private static final long serialVersionUID = 1L;
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = SupplmentType.getSupplmentSend(Integer.valueOf(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public class checkBoxRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
			} else if (value.equals("")) {
				checkBox.setSelected(false);
			} else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
			}
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			checkBox.setBackground(table.getBackground());
			if (isSelected) {
				checkBox.setForeground(table.getSelectionForeground());
				checkBox.setBackground(table.getSelectionBackground());
			} else {
				checkBox.setForeground(table.getForeground());
				checkBox.setBackground(table.getBackground());
			}
			return checkBox;
		}
	}

	protected boolean getBoolean(Boolean b) {
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected void initUIComponents() {
		
		/*
		 * 初始化查询条件，开始时间和结束时间
		 * 
		 * 开始时间 默认 为当月的开始时间（就是当月1号凌晨0时）  
		 * 结束时间 默认 为当月的结束时间（就是当月最后一天23点59分59秒999毫秒）
		 */
		Calendar calendar = Calendar.getInstance();    
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
	    Date begin = calendar.getTime();
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    Date end = calendar.getTime();
	    
	    cbbBeginDate.setDate(begin);
	    cbbEndDate.setDate(end);
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		FmSupplementList.projectType = projectType;
	}

	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}

	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("查看");
			btnBrowse.setPreferredSize(new Dimension(45, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择要查看的资料!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					showSupplement(DataState.BROWSE);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes btnTcs
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTcs() {
		if (btnTcs == null) {
			btnTcs = new JButton();
			btnTcs.setText("集成通申报");
			btnTcs.setPreferredSize(new Dimension(75, 30));
			btnTcs.setForeground(java.awt.Color.blue);
			btnTcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						DgProcessCustomsMessageTCS dgEdiMessage = new DgProcessCustomsMessageTCS();
						dgEdiMessage.setBaseEncAction(baseEncAction);
						dgEdiMessage.setTableModelCustoms(tableModel);
						dgEdiMessage.setProjectType(projectType);
						dgEdiMessage.setVisible(true);
					} finally {
						initTable(getDataSource());
					}

				}
			});
		}
		return btnTcs;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel2.add(getPnQuery(), BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnBrowseAll	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBrowseAll() {
		if (btnBrowseAll == null) {
			btnBrowseAll = new JButton();
			btnBrowseAll.setText("查看全部");
			btnBrowseAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List DecSupplementList = baseEncAction.queryDecSupplementListAll(new Request(
							CommonVars.getCurrUser()));
					initTable(DecSupplementList);
				}
			});
		}
		return btnBrowseAll;
	}
	/**
	 * 新增商品
	 */
	private void addComminfo() {
		if (tableModel.getCurrentRow() != null) {
			Object[]  data= (Object[]) tableModel.getCurrentRow();
//			supplmentType =Integer.valueOf(data[2].toString());
//			String serialNumber = data[0].toString();
//			baseCustomsDeclaration = new BaseCustomsDeclaration();
//			baseCustomsDeclaration.setSerialNumber(Integer.valueOf(serialNumber));
			addCustomsDeclarationData(3,data);
		} else {
			JOptionPane.showMessageDialog(null, "请选择要查看的资料!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
	}

	/**
	 * This method initializes btnFlow	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnFlow() {
		if (btnFlow == null) {
			btnFlow = new JButton();
			btnFlow.setText("流程指引");
			btnFlow.setToolTipText("<html><body>1.选择补充申报单类型。<br/>2.选择需要补充申报的报关单。<br/>" +
							"3.选择补充申报商品。<br/>4.选择商品补充申报类型。<br/>5.编辑商品补充申报信息</html></body>");
			btnFlow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(null, "1.选择补充申报单类型。\n2.选择需要补充申报的报关单。\n" +
							"3.选择补充申报商品。\n4.选择商品补充申报类型。\n5.编辑商品补充申报信息", "指引",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnFlow;
	}

	/**
	 * This method initializes pnQuery	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnQuery() {
		if (pnQuery == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			lbTo = new JLabel();
			lbTo.setText("到");
			lbDeclarationDate = new JLabel();
			lbDeclarationDate.setText("申报日期 从");
			pnQuery = new JPanel();
			pnQuery.setLayout(flowLayout);
			pnQuery.setPreferredSize(new Dimension(512, 33));
			pnQuery.add(lbDeclarationDate, null);
			pnQuery.add(getCbbBeginDate(), null);
			pnQuery.add(lbTo, null);
			pnQuery.add(getCbbEndDate(), null);
			pnQuery.add(getBtnQuery(), null);
		}
		return pnQuery;
	}

	/**
	 * This method initializes cbbBeginDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new Dimension(120, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(120, 24));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setPreferredSize(new Dimension(75, 24));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					query();
				}
			});
		}
		return btnQuery;
	}
	
	private void query() {
		initTable(getDataSource());
	}
} // @jve:decl-index=0:visual-constraint="10,10"
