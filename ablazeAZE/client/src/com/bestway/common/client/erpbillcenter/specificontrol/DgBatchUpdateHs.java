package com.bestway.common.client.erpbillcenter.specificontrol;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
@SuppressWarnings("unchecked")
public class DgBatchUpdateHs extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JComboBox cbbType = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JComboBox cbbScmCoc = null;
	private MaterialManageAction materialManageAction = null;
	private CasAction casAction = null;
	private JTextField jtPtNo = null;
	private JButton jbPtNo = null;
	private JButton jbQuery = null;
	private JButton jbClose = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton jbEdit = null;
	private JTableListModel tableModel = null;
	private Integer 		   maximumFractionDigits 	= CasCommonVars.getOtherOption()
	.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
	.getOtherOption().getInOutMaximumFractionDigits();
	private JCheckBox jCheckBox = null;
	private JLabel lbNameStyle = null;
	private JComboBox cbbName = null;
	
	private String materielType = MaterielType.FINISHED_PRODUCT;  //  @jve:decl-index=0:
	/**
	 * This method initializes 
	 * 
	 */
	public DgBatchUpdateHs() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("批量修改\"工厂商品资料\"对应报关商品资料");
        this.setSize(new Dimension(676, 555));
        this.setContentPane(getJSplitPane());
			
	}
	
	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 65, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name", 235);
		this.cbbScmCoc.setSelectedItem(null);
		//
		// 初始化日历控件
		//
//		this.cbbBeginDate.setDate(null);
//		this.cbbEndDate.setDate(null);
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
		.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);
		
		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);
		initTable(new ArrayList());
		
		//
		// 初始化表1
		//
//		initTb1(new ArrayList<BillDetail>());
//		initTb2(new ArrayList());
//		initTb3(new ArrayList());
		
		
		
		//
		// 初始化商品大类名称
		//
		List<Object[]> tmpList = casAction.findStatCusNameRelationName(
				new Request(CommonVars.getCurrUser(), true), materielType);
		this.cbbName.removeAllItems();
		for (Object[] item : tmpList) {
			String name = item[0] == null ? "" : (String) item[0];
			String spec = item[1] == null ? "" : (String) item[1];
			NameSpecProperty nameSpec = new NameSpecProperty(name, spec);
			this.cbbName.addItem(nameSpec);
		}

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbName,
				"nameSpec", "nameSpec", 200);
		this.cbbName.setRenderer(CustomBaseRender.getInstance().getRender(
				"nameSpec", "nameSpec", 200, 0));
	}	
	
	public class NameSpecProperty {

		private String spec;

		private String name;

		public NameSpecProperty(String name, String spec) {
			this.spec = spec;
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String code) {
			this.spec = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the nameSpec
		 */
		public String getNameSpec() {
			String nameSpec = "";
			if (name != null)
				nameSpec = name;
			if (spec != null)
				nameSpec = nameSpec + "/" + spec;
			return nameSpec;
		}

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
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(100);
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
			lbNameStyle = new JLabel();
			lbNameStyle.setBounds(new Rectangle(327, 73, 64, 15));
			lbNameStyle.setText("名称/规格");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(7, 39, 61, 23));
			jLabel4.setText("物料类型:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(314, 6, 64, 23));
			jLabel3.setText("工厂料号:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(237, 39, 85, 23));
			jLabel2.setText("客户名称:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(199, 5, 15, 23));
			jLabel1.setText("到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(7, 5, 97, 23));
			jLabel.setText("单据生效日期从");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbType(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(getJtPtNo(), null);
			jPanel.add(getJbPtNo(), null);
			jPanel.add(getJbQuery(), null);
			jPanel.add(getJbClose(), null);
			jPanel.add(getJbEdit(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(lbNameStyle, null);
			jPanel.add(getCbbName(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes cbbType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbType() {
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.setBounds(new Rectangle(72, 39, 148, 23));
            cbbType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT,
            "成品"));
		    cbbType.addItem(new ItemProperty(
		            MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
		    cbbType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
		    cbbType.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
		    cbbType.addItem(new ItemProperty(MaterielType.REMAIN_MATERIEL,
		            "边角料"));
		    cbbType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT, "残次品"));	
		    cbbType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<Object[]> tmpList = casAction
							.findStatCusNameRelationName(new Request(CommonVars
									.getCurrUser(), true),
									((ItemProperty) cbbType.getSelectedItem())
											.getCode());
					cbbName.removeAllItems();
					for (Object[] item : tmpList) {
						String name = item[0] == null ? "" : (String) item[0];
						String spec = item[1] == null ? "" : (String) item[1];
						NameSpecProperty nameSpec = new NameSpecProperty(name, spec);
						cbbName.addItem(nameSpec);
					}
				}
			});
		}
		return cbbType;
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(106, 5, 90, 22));
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
			cbbEndDate.setBounds(new Rectangle(217, 5, 90, 22));
		}
		return cbbEndDate;
	}
	
	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(327, 39, 239, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jtPtNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJtPtNo() {
		if (jtPtNo == null) {
			jtPtNo = new JTextField();
			jtPtNo.setBounds(new Rectangle(382, 6, 157, 23));
		}
		return jtPtNo;
	}

	/**
	 * This method initializes jbPtNo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbPtNo() {
		if (jbPtNo == null) {
			jbPtNo = new JButton();
			jbPtNo.setBounds(new Rectangle(541, 6, 25, 23));
			jbPtNo.setText("...");
			jbPtNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty  item = (ItemProperty)cbbType.getSelectedItem();
					List<TempObject> list = CasQuery.getInstance()
					.getOneToMoreMaterielInfo(false, item.getCode(),jCheckBox.isSelected());
					if (list != null && list.size() > 0) {
						TempObject sm = list.get(0);
						Materiel m = (Materiel)sm.getObject();
						jtPtNo.setText(m == null ? "" : m.getPtNo());
					}
				}
			});			
		}
		return jbPtNo;
	}

	/**
	 * This method initializes jbQuery	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbQuery() {
		if (jbQuery == null) {
			jbQuery = new JButton();
			jbQuery.setBounds(new Rectangle(578, 7, 73, 23));
			jbQuery.setText("查询");
			jbQuery.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(getCbbType().getSelectedItem()==null){
						JOptionPane.showMessageDialog(DgBatchUpdateHs.this, 
								"请选择物料类型","提示!",JOptionPane.WARNING_MESSAGE);
						return;
					}
					new SearchThread().start();
				}
				
			});
		}
		return jbQuery;
	}

	/**
	 * This method initializes jbClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbClose() {
		if (jbClose == null) {
			jbClose = new JButton();
			jbClose.setBounds(new Rectangle(578, 60, 73, 23));
			jbClose.setText("关闭");
			jbClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});			
		}
		return jbClose;
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
	 * 取消单据对应线程
	 * 
	 * @author ls
	 * 
	 */
	class SearchThread extends Thread {

		public void run() {
//			 jbQuery.setEnabled(false);
//			 SwingWorker worker = new SwingWorker() {
//			      
//			      @Override
//			      protected Object doInBackground() throws Exception {
//			      List list=null;
//			      try {
//			        CommonProgress.showProgressDialog();  //  @jve:decl-index=0:
//			        CommonProgress.setMessage("系统正在加载默认系数，请稍后...");
//					ItemProperty  item = (ItemProperty)cbbType.getSelectedItem();
//					List tempList = casAction.findBillInfo(
//							new Request(CommonVars.getCurrUser()),item.getCode(),
//							getCbbBeginDate().getDate(),getCbbEndDate().getDate(),jtPtNo.getText(),
//							(ScmCoc)getCbbScmCoc().getSelectedItem());
//			      } catch (Exception e) {
//					JOptionPane.showMessageDialog(DgBatchUpdateHs.this,
//								"系统正在获取数据失败！！！" + e.getMessage(), "提示", 2);
//			      } finally {
//			        CommonProgress.closeProgressDialog();
//			      }
//			       return list;
//			      }
//			      
//			      @Override
//			      protected void done(){        
////			        System.out.println("done");
//			        //显示Table数据在这里调用
//			    	try {
//						initTable((List)this.get());
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					} catch (ExecutionException e) {
//						e.printStackTrace();
//					}
//			      }
//			};
//			worker.execute();
//			jbQuery.setEnabled(true);
			
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				jbQuery.setEnabled(false);

				String info = "";
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {
						// String tipMessage = casAction
						// .getClientTipMessageByBalanceInfo(new Request(
						// CommonVars.getCurrUser(), true));
						// CommonProgress.setMessage(flag, tipMessage);
					}
				};
				CommonProgress.showProgressDialog(flag,
						DgBatchUpdateHs.this, false, progressTask,
						5000);
				CommonProgress.setMessage(flag, "系统正在获取数据，请稍后...");
				NameSpecProperty ns = ((NameSpecProperty) cbbName.getSelectedItem());
				String nameStyle = null;
				if(ns != null) {
					nameStyle = ns.name + "/" + ns.spec;
				}
				
				ItemProperty  item = (ItemProperty)cbbType.getSelectedItem();
				List<BillDetail> tempList = casAction.findBillInfo(
						new Request(CommonVars.getCurrUser()),item.getCode(),
						getCbbBeginDate().getDate(),getCbbEndDate().getDate(),jtPtNo.getText(),
						(ScmCoc)getCbbScmCoc().getSelectedItem(), nameStyle);
				//检查是否过滤
				if(jCheckBox.isSelected()){
					CommonProgress.setMessage(flag, "数据筛选中，请稍后...");
					List matchList = casAction.findOneToMorePtNo(
							new Request(CommonVars.getCurrUser()),item.getCode());
					for(int i = 0; i < tempList.size(); i++){
						BillDetail detail = tempList.get(i);
						if(!matchList.contains(detail.getPtPart())){
							tempList.remove(i);
							i--;
						}
					}
				}
				initTable(tempList);

				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(DgBatchUpdateHs.this,
						info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgBatchUpdateHs.this,
						"系统正在获取数据失败！！！" + e.getMessage(), "提示", 2);
			}
			jbQuery.setEnabled(true);
		}
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
	 * This method initializes jbEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJbEdit() {
		if (jbEdit == null) {
			jbEdit = new JButton();
			jbEdit.setBounds(new Rectangle(578, 33, 73, 23));
			jbEdit.setText("修改");
			jbEdit.addActionListener(new java.awt.event.ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(tableModel.getCurrentRow()==null){
						JOptionPane.showMessageDialog(DgBatchUpdateHs.this,
								"您还没有选择需要更改的记录!","提示",JOptionPane.WARNING_MESSAGE);
						return;
					}
					List<BillDetail> list = tableModel.getCurrentRows();
					BillDetail bd = (BillDetail)list.get(0);
					String hsName = bd.getHsName() != null ? bd.getHsName()
							.trim() : "";
					for (BillDetail item : list){
						if (!hsName.equals(item.getHsName() != null ? item
								.getHsName().trim() : "")) {
							JOptionPane.showMessageDialog(DgBatchUpdateHs.this,
									"您选择的记录存在不同报关商品名称，请重新选择!","提示",JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					ItemProperty item = (ItemProperty)cbbType.getSelectedItem();
					DgBatchUpdateHsDetailAll detail = new DgBatchUpdateHsDetailAll();
					detail.setTableModel(tableModel);
					detail.setMaterielType(item.getCode());
					detail.setVisible(true);
				}
				
			});
		}
		return jbEdit;
	}
	

	/**
	 * 初始化表
	 * 
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据号", "billMaster.billNo", 100));
						list.add(addColumn("单据类型", "billMaster.billType.name", 100));
						list.add(addColumn("客户名称", "billMaster.scmCoc.name", 100));
						list.add(addColumn("生效时间", "billMaster.validDate", 100));
						list.add(addColumn("工厂料号", "ptPart", 100));
						list.add(addColumn("工厂名称", "ptName", 100));
						list.add(addColumn("工厂规格", "ptSpec", 100));
						list.add(addColumn("工厂单位", "ptUnit.name", 100));
						list.add(addColumn("工厂数量", "ptAmount", 100));
						list.add(addColumn("工厂单价", "ptPrice", 100));
						list.add(addColumn("报关商品编码", "complex.code", 100));
						list.add(addColumn("报关商品名称", "hsName", 100));
						list.add(addColumn("报关型号规格", "hsSpec", 100));
						list.add(addColumn("报关商品单位", "hsUnit.name", 100));
						list.add(addColumn("折算报关数量", "hsAmount", 100));
						list.add(addColumn("报关商品单价", "hsPrice", 100));
						list.add(addColumn("报关商品金额", "money", 100));
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		 List<JTableListColumn> cm = tableModel.getColumns();
		 cm.get(9).setFractionCount(3);
		 cm.get(10).setFractionCount(3);
		 cm.get(15).setFractionCount(3);
		 cm.get(16).setFractionCount(3);
		 cm.get(17).setFractionCount(3);
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(7, 73, 292, 17));
			jCheckBox.setText("是否对料号进行过滤，只显示一对多的料号");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes cbbName	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbName() {
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setBounds(new Rectangle(399, 70, 167, 22));
			cbbName.setUI(new CustomBaseComboBoxUI(270));
			cbbName.setEditable(true);
			//cbbName.setBorder(BorderFactory.createLineBorder(new Color(106, 135, 171), 1));
		}
		return cbbName;
	}	

}  //  @jve:decl-index=0:visual-constraint="10,10"
