package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.entity.TempBillReturn;
import com.bestway.bls.entity.TempCollateEntity;
import com.bestway.bls.entity.TempCollateItem;
import com.bestway.bls.entity.TempDeliveryEntity;
import com.bestway.bls.entity.TempQueryBillList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTabbedPane;
import java.awt.Rectangle;
import javax.swing.JLabel;

/**
 * 仓单状态查询
 * 
 * @author hw
 * 
 */
public class DgQueryResult extends JDialogBase {

	/**
	 * 数据源
	 */
	public List dataSource = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbBillReturn = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbDeliveryEntity = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbQueryBillList = null;

	private JTableListModel tableModelBillReturn;

	private JTableListModel tableModelDeliveryEntity;

	private JTableListModel tableModelQueryBillList;

	private JTableListModel tableModelCollateMftInOut;
	
	private JTableListModel tableModelCollateMftStock;

	private JTableListModel tableModelCollateMftEnt;

	private JTableListModel tableModelCollateItem;

	private JTableListModel tableModelCollateItem2;

	private JPanel jPanel5 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel6 = null;

	private JPanel jPanel7 = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbCollateMftInOut = null;
	
	private JTable tbCollateMftStock = null;

	private JScrollPane jScrollPane4 = null;

	private JTable tbCollateMftEnt = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JScrollPane jScrollPane5 = null;

	private JTable tbCollateItem1 = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel8 = null;

	private JPanel jPanel9 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JScrollPane jScrollPane6 = null;

	private JTable tbCollateItem2 = null;

	private JPanel jPanel10 = null;

	private JScrollPane jScrollPane7 = null;

	private JTable jTable = null;

	public List getDataSource() {
		return dataSource;
	}

	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgQueryResult() {
		super();
		initialize();
		initBillReturn(new ArrayList());
		initDeliveryEntity(new ArrayList());
		initQueryBillList(new ArrayList());
		initCollateMftInOut(new ArrayList());
		initCollateMftEnt(new ArrayList());
		initCollateItem2(new ArrayList());
		
		//showQueryResults();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(807, 403));
		this.setTitle("仓单状态查询");
		this.setContentPane(getJPanel());

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
			jPanel.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("BillReturn表", null, getJPanel1(), null);
			jTabbedPane.addTab("DeliveryEntity表", null, getJPanel2(), null);
			jTabbedPane.addTab("QueryBillList表", null, getJPanel3(), null);
			jTabbedPane.addTab("出入仓核销明细", null, getJPanel4(), null);
			jTabbedPane.addTab("CollateItem表", null, getJPanel5(), null);
			jTabbedPane.addTab("库存核扣明细", null, getJPanel10(), null);
		}
		return jTabbedPane;
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
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
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
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
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
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbBillReturn());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbBillReturn
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBillReturn() {
		if (tbBillReturn == null) {
			tbBillReturn = new JTable();
		}
		return tbBillReturn;
	}

	/**
	 * 初始化BillReturn表
	 * 
	 * @param list
	 */
	private void initBillReturn(List list) {
		tableModelBillReturn = new JTableListModel(tbBillReturn, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("服务Handle", "serviceHandle", 100));
						list.add(addColumn("服务状态", "serviceStatus", 100));
						list.add(addColumn("反馈描述", "description", 100));
						list.add(addColumn("企业编码", "TradeCo", 100));
						list.add(addColumn("电子帐册号", "emsNo", 100));
						list.add(addColumn("仓库编码", "wareHouseCode", 80));
						list.add(addColumn("关区代码", "customsCode", 100));
						list.add(addColumn("仓单号码", "billNo", 100));
						list.add(addColumn("进出口标志", "iOFlag", 100));
						list.add(addColumn("进出口口岸", "iEPort", 100));
						list.add(addColumn("车次号码", "deliveryNo", 100));
						list.add(addColumn("仓单类型", "billType", 100));
						list.add(addColumn("仓单表体数量", "itemsCount", 100));
						list.add(addColumn("仓单通过审核时间", "billOperTime", 100));
						list
								.add(addColumn("是否收到货到通知", "isDeliveryArrived",
										200));
						list.add(addColumn("出入仓核销是否完成",
								"isCollateMftInOutFinishend", 200));
						list.add(addColumn("仓单报关单核销是否完成",
								"isCollateMftEntFinished", 200));
						return list;
					}
				});
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbDeliveryEntity());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbDeliveryEntity
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDeliveryEntity() {
		if (tbDeliveryEntity == null) {
			tbDeliveryEntity = new JTable();
		}
		return tbDeliveryEntity;
	}

	/**
	 * 初始化DeliveryEntity表
	 * 
	 * @param list
	 */
	private void initDeliveryEntity(List list) {
		tableModelDeliveryEntity = new JTableListModel(tbDeliveryEntity, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("车次号", "deliveryNo", 100));
						list.add(addColumn("车牌号码", "vehicleLicense", 100));
						list.add(addColumn("申报货到报文时间", "deliveryOperTime",200));
						return list;
					}
				});
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbQueryBillList());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbQueryBillList
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbQueryBillList() {
		if (tbQueryBillList == null) {
			tbQueryBillList = new JTable();
		}
		return tbQueryBillList;
	}

	/**
	 * 初始化QueryBillList表
	 * 
	 * @param list
	 */
	private void initQueryBillList(List list) {
		tableModelQueryBillList = new JTableListModel(tbQueryBillList, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("表体序号", "no", 100));
						list.add(addColumn("基本号", "copGNo", 100));
						list.add(addColumn("在电子帐册中的商品序号", "contrItem", 200));
						list.add(addColumn("商品编码", "codeTS", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("商品规格", "model", 100));
						list.add(addColumn("法定第一数量", "qty1", 100));
						list.add(addColumn("法定第二数量", "qty2", 100));
						list.add(addColumn("申报单位编码", "unit", 100));
						list.add(addColumn("申报数量", "qty", 100));
						list.add(addColumn("申报单价", "unitPrice", 100));
						list.add(addColumn("申报总价", "totalPrice", 100));
						list.add(addColumn("币值", "curr", 100));
						list.add(addColumn("原产国", "originCountry", 100));
						list.add(addColumn("贸易方式", "tradeMode", 100));
						list.add(addColumn("成交方式", "transMode", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJSplitPane1(), BorderLayout.CENTER);
		}
		return jPanel5;
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
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel6());
			jSplitPane.setBottomComponent(getJPanel7());
			jSplitPane.setDividerLocation(180);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel = new JLabel();
			jLabel.setText("出入仓核销明细");
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(jLabel, BorderLayout.NORTH);
			jPanel6.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("仓单报关单明细");
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(jLabel1, BorderLayout.NORTH);
			jPanel7.add(getJScrollPane4(), BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbCollateMftInOut());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes tbCollateMftInOut
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCollateMftInOut() {
		if (tbCollateMftInOut == null) {
			tbCollateMftInOut = new JTable();
		}
		return tbCollateMftInOut;
	}
	
	/**
	 * This method initializes tbCollateMftInOut
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCollateMftStock() {
		if (tbCollateMftStock == null) {
			tbCollateMftStock = new JTable();
		}
		return tbCollateMftStock;
	}

	/**
	 * 初始化CollateMftInOut表
	 * 
	 * @param list
	 */
	private void initCollateMftInOut(List list) {
		tableModelCollateMftInOut = new JTableListModel(tbCollateMftInOut,
				list, new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("核销类型", "collateType", 100));
						list.add(addColumn("该商品序号得商品是否核销完成",
								"isCollateFinished", 250));
						list.add(addColumn("需要核销数量", "collateTotalCount", 150));
						list.add(addColumn("已核销数量", "collatedCount", 100));
						list.add(addColumn("需要核销数量", "collateTotalCount", 100));
						return list;
					}
				});
	}
	
	/**
	 * 初始化CollateMftInOut表
	 * 
	 * @param list
	 */
	private void initCollateMftStock(List list) {
		tableModelCollateMftStock = new JTableListModel(tbCollateMftStock,
				list, new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("核销类型", "collateType", 100));
						list.add(addColumn("该商品序号得商品是否核销完成",
								"isCollateFinished", 250));
						list.add(addColumn("需要核销数量", "collateTotalCount", 150));
						list.add(addColumn("已核销数量", "collatedCount", 100));
						list.add(addColumn("需要核销数量", "collateTotalCount", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getTbCollateMftEnt());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes tbCollateMftEnt
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCollateMftEnt() {
		if (tbCollateMftEnt == null) {
			tbCollateMftEnt = new JTable();
		}
		return tbCollateMftEnt;
	}

	/**
	 * 初始化CollateMftInOut表
	 * 
	 * @param list
	 */
	private void initCollateMftEnt(List list) {
		tableModelCollateMftEnt = new JTableListModel(tbCollateMftEnt, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("核销类型", "collateType", 100));
						list.add(addColumn("该商品序号得商品是否核销完成",
								"isCollateFinished", 200));
						list.add(addColumn("需要核销数量", "collateTotalCount", 100));
						list.add(addColumn("已核销数量", "collatedCount", 100));
						list.add(addColumn("需要核销数量", "collateTotalCount", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getTbCollateItem1());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes tbCollateItem1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCollateItem1() {
		if (tbCollateItem1 == null) {
			tbCollateItem1 = new JTable();
		}
		return tbCollateItem1;
	}

	/**
	 * 初始化CollateItem1表
	 * 
	 * @param list
	 */
	private void initCollateItem(List list) {
		tableModelCollateItem = new JTableListModel(tbCollateItem1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("核销类型", "collateItemType", 100));
						list.add(addColumn("核销单证号码","collateItemFormID", 250));
						list.add(addColumn("核销单证中得序号", "collateItemFormGNo", 150));
						list.add(addColumn("核销数量", "collateItemFormCount", 100));
						list.add(addColumn("核销申报时间", "collateItemOperTime", 100));
						
						return list;
					}
				});
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
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setTopComponent(getJPanel8());
			jSplitPane1.setBottomComponent(getJPanel9());
			jSplitPane1.setDividerLocation(180);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("CollateItem(出入仓核销明细)");
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(jLabel2, BorderLayout.NORTH);
			jPanel8.add(getJScrollPane5(), BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("CollateItem(仓单报关单明细)");
			jLabel3 = new JLabel();
			jLabel3.setText("JLabel");
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(jLabel4, BorderLayout.NORTH);
			jPanel9.add(getJScrollPane6(), BorderLayout.CENTER);
		}
		return jPanel9;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getTbCollateItem2());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes tbCollateItem2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCollateItem2() {
		if (tbCollateItem2 == null) {
			tbCollateItem2 = new JTable();
		}
		return tbCollateItem2;
	}

	/**
	 * 初始化CollateItem2表
	 * 
	 * @param list
	 */
	private void initCollateItem2(List list) {
		tableModelCollateItem2 = new JTableListModel(tbCollateItem2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("核销类型", "collateItemType", 100));
						list.add(addColumn("核销单证号码","collateItemFormID", 250));
						list.add(addColumn("核销单证中得序号", "collateItemFormGNo", 150));
						list.add(addColumn("核销数量", "collateItemFormCount", 100));
						list.add(addColumn("核销申报时间", "collateItemOperTime", 100));
						return list;
					}
				});
	}

	/**
	 * 显示查询结果
	 */
	public void showQueryResults() {
		//System.out.println("dataSource.size()="+dataSource.size());
		List<TempDeliveryEntity> listDeliveryInfo=new ArrayList<TempDeliveryEntity>();
		List<TempQueryBillList> listTempQueryBillList=new ArrayList<TempQueryBillList>();
		List<TempCollateEntity> listTempCollateEntity=new ArrayList<TempCollateEntity>();
		List<TempCollateEntity> listTempCollateEntity2=new ArrayList<TempCollateEntity>();
		List<TempCollateEntity> listTempCollateEntity3=new ArrayList<TempCollateEntity>();
		List<TempCollateItem> listTempCollateItem=new ArrayList<TempCollateItem>();
		List<TempCollateItem> listTempCollateItem2=new ArrayList<TempCollateItem>();
		if (dataSource != null) {
			initBillReturn(dataSource);
			for(int i=0;i<dataSource.size();i++)
			{
				//初始化TempBillReturn表
				TempBillReturn tempBillReturn=(TempBillReturn)dataSource.get(i);
				TempDeliveryEntity tempDeliveryEntity=tempBillReturn.getDeliveryInfo();
				System.out.println("tempDeliveryEntity="+tempDeliveryEntity);
				
//				System.out.println("DeliveryNo="+(tempBillReturn.getDeliveryInfo()).getDeliveryNo());
//				
//				System.out.println("DeliveryOperTime="+(tempBillReturn.getDeliveryInfo()).getDeliveryOperTime());
//				
//				System.out.println("VehicleLicense="+(tempBillReturn.getDeliveryInfo()).getVehicleLicense());
				
				listDeliveryInfo.add(tempDeliveryEntity);
				initDeliveryEntity(listDeliveryInfo);
				//仓单表体项
				List queryBillLists=(List)tempBillReturn.getQueryBillLists();
				for(int j=0;j<queryBillLists.size();j++)
				{
					//初始化TempQueryBillList表
					TempQueryBillList tempQueryBillList=(TempQueryBillList)queryBillLists.get(j);
					System.out.println("tempQueryBillList.getGNo()="+tempQueryBillList.getNo());
					System.out.println("tempQueryBillList.getCopGNo()="+tempQueryBillList.getCopGNo());
					System.out.println("tempQueryBillList.getContrItem()="+tempQueryBillList.getContrItem());
					System.out.println("tempQueryBillList.getCodeTS()="+tempQueryBillList.getCodeTS());
					
					//System.out.println("tempQueryBillList.getCodeTS()="+tempQueryBillList.());
					listTempQueryBillList.add(tempQueryBillList);
					initQueryBillList(listTempQueryBillList);
					
					System.out.println("tempQueryBillList.getCollateMftEnt()="+tempQueryBillList.getCollateMftEnt());
					
					System.out.println("tempQueryBillList.getCollateMftEnt().getCollateType()="+(tempQueryBillList.getCollateMftEnt()).getCollateType());
					
					System.out.println("tempQueryBillList.getCollateMftInOut()="+(tempQueryBillList.getCollateMftInOut()));
					
					System.out.println("tempQueryBillList.getCollateMftInOut().getCollateType()="+(tempQueryBillList.getCollateMftInOut()).getCollateType());
					
					System.out.println("CollateFinished="+(tempQueryBillList.getCollateMftInOut()).getIsCollateFinished());
					//初始化TempCollateEntity表
					if(tempQueryBillList.getCollateMftEnt()!=null)
					{
						TempCollateEntity tempCollateEntity=tempQueryBillList.getCollateMftEnt();
						System.out.println("tempCollateEntity="+tempCollateEntity);
						listTempCollateEntity.add(tempCollateEntity);
						initCollateMftEnt(listTempCollateEntity);
						//初始化TempCollateItem1表
						if(tempCollateEntity.getCollateItems()!=null)
						{
							List tempCollateItems=tempCollateEntity.getCollateItems();
							initCollateItem(tempCollateItems);
							System.out.println("tempCollateItems.size()="+tempCollateItems.size());
//							for(int t=0;t<tempCollateItems.size();t++)
//							{
//								TempCollateItem tempCollateItem=(TempCollateItem)tempCollateItems.get(t);
//								System.out.println("tempCollateItem.getCollateItemType()="+tempCollateItem.getCollateItemType());
//								System.out.println("tempCollateItem.getCollateItemFormGNo()="+tempCollateItem.getCollateItemFormGNo());
//								listTempCollateItem.add(tempCollateItem);
//								initCollateItem(listTempCollateItem);
//							}
							
							//初始化TempCollateEntity2表
							TempCollateEntity tempCollateEntity2=tempQueryBillList.getCollateMftInOut();
							System.out.println("tempCollateEntity2.getCollateType()="+tempCollateEntity2.getCollateType());
							listTempCollateEntity2.add(tempCollateEntity2);
							initCollateMftInOut(listTempCollateEntity2);
							
							//初始化TempCollateEntity3表
							TempCollateEntity tempCollateEntity3=tempQueryBillList.getCollateMftStock();
							System.out.println("tempCollateEntity3.getCollateType()="+tempCollateEntity3.getCollateType());
							listTempCollateEntity3.add(tempCollateEntity3);
							initCollateMftStock(listTempCollateEntity3);
							
							//初始化TempCollateItem2表
							List tempCollateItems2=tempCollateEntity2.getCollateItems();
//							System.out.println("tempCollateItems2.get(0)="+(tempCollateItems2.get(0)));
//							
//							System.out.println(((TempCollateItem)(tempCollateItems2.get(0))).getCollateItemFormGNo());
							initCollateItem2(tempCollateItems2);
//							for(int e=0;e<tempCollateItems.size();e++)
//							{
//								TempCollateItem tempCollateItem=(TempCollateItem)tempCollateItems.get(e);
//								listTempCollateItem2.add(tempCollateItem);
//								initCollateItem2(listTempCollateItem);
//							}
						}
					}
				}
			}
		}

	}

	/**
	 * 设置显示和组建状态
	 */
	public void setVisibles(boolean f) {
		if (f) {
			showQueryResults();
			super.setVisible(f);
		}
	}

	/**
	 * This method initializes jPanel10	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			jPanel10.setLayout(new BorderLayout());
			jPanel10.add(getJScrollPane7(), BorderLayout.CENTER);
		}
		return jPanel10;
	}

	/**
	 * This method initializes jScrollPane7	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane7() {
		if (jScrollPane7 == null) {
			jScrollPane7 = new JScrollPane();
			jScrollPane7.setViewportView(getTbCollateMftStock());
		}
		return jScrollPane7;
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
	
} // @jve:decl-index=0:visual-constraint="10,10"
