package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.checkcancel.DgCancelCus;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BillMatchInfo;
import com.bestway.bls.entity.BillMatchSet;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;

import com.bestway.ui.editor.MessageInfo;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 单据对应
 * @author Administrator
 *
 */
public class FmBillCorresponding extends JInternalFrameBase {

	private JPanel jPanel = null;
	
	private JToolBar jToolBar = null;
	
	private JPanel jPanel1 = null;
	/**
	 * 单据查询按钮
	 */
	private JButton btnBillSerch = null;
	/**
	 * 单据对应
	 */
	private JButton btnCorresponding = null;
	/**
	 * 取消对应
	 */
	private JButton btnCancelCorresponding = null;
	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;
	
	private JPanel top = null;
	
	private JPanel center = null;
	
	private JLabel jLabel = null;
	/**
	 * 出仓单据客户名称
	 */
	private JComboBox cbCustomers = null;
	private JLabel jLabel1 = null;
	/**
	 * 待对应的出仓单据中的商品
	 */
	private JComboBox cbbName = null;
	
	private JLabel jLabel2 = null;
	
	private JLabel jLabel3 = null;
	/**
	 * 开始日期
	 */
	private JCalendarComboBox cbbBeginDate = null;

	/**
	 * 结束日期
	 */
	private JCalendarComboBox cbbEndDate = null;
	
	private JTabbedPane jTabbedPane = null;
	
	private JPanel jPanel2 = null;
	
	private JPanel jPanel3 = null;
	
	private JPanel jPanel4 = null;
	
	private JSplitPane jSplitPane = null;
	
	private JPanel jPanel5 = null;
	
	private JPanel jPanel6 = null;
	
	private JScrollPane jScrollPane = null;
	/**
	 * 出仓单据要做对应的商品信息
	 */
	private JTable tbOutBill = null;
	private JScrollPane jScrollPane1 = null;
	
	/**
	 * 单据对应参数
	 */
	private BillMatchSet billMatchSet = null;

	/**
	 * 料件管理操作接口 
	 */
	private MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
			.getApplicationContext().getBean("materialManageAction"); // @jve:decl-index=0:
	
	/**
	 * 进出仓单据操作接口
	 */
	private BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction)CommonVars
	.getApplicationContext().getBean("blsInOutStockBillAction");  //  @jve:decl-index=0:
	
	
	/**
	 * 期初单据
	 */
	private JTable tbFirstBill = null;
	
	/**
	 * 出仓单据商品信息
	 */
	private JTableListModel tableModelOutBillCommInfo = null;

	/**
	 * 入仓交单据信息
	 */
	private JTableListModel tableModelInBillInfo = null;	
	/**
	 * 入仓交单据信息
	 */
	private JTableListModel tableModelMatchInfo = null;
	
	private JScrollPane jScrollPane2 = null;
	/**
	 * 单据对应停息
	 */
	private JTable tbMatchInfo = null;
	private JPanel jPanel7 = null;
	private JPanel jPanel8 = null;
	private JCheckBox cbIsCheck = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBillCorresponding() {
		super();
		initialize();
		initUIComponents();
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setSize(new Dimension(767, 507));
		this.setTitle("进入仓单据对应");

		Thread thread = new getNameAndSpec();
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 初始化窗体中控件的初始值
	 * 
	 */
	protected void initUIComponents() {
		// 初始化供货方企业下拉框
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser()));
		this.cbCustomers.removeAllItems();
		this.cbCustomers.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbCustomers,
				"code", "name", 250);
		this.cbCustomers.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		Long start = System.currentTimeMillis();
		
		
		
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbName,
				"nameSpec", "nameSpec", 400);
		this.cbbName.setRenderer(CustomBaseRender.getInstance().getRender(
				"nameSpec", "nameSpec", 400, 0));
		
		
		
		//初始化参数
		billMatchSet = this.blsInOutStockBillAction.findBillMatchSet(new Request(CommonVars
				.getCurrUser()));
		System.out.println(billMatchSet==null?"参数为空":"参数不为空!");
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnBillSerch());
			jToolBar.add(getBtnCorresponding());
			jToolBar.add(getBtnCancelCorresponding());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
			jPanel1.add(getTop(), BorderLayout.NORTH);
			jPanel1.add(getCenter(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnBillSerch
	 * 根据名称规格查询出仓单据待对应的商品，及入仓单期初单商品
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBillSerch() {
		if (btnBillSerch == null) {
			btnBillSerch = new JButton();
			btnBillSerch.setText("单据查询");
			btnBillSerch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					billSerch();
				}
			});
		}
		return btnBillSerch;
	}
	
	/**
	 * 单据查询
	 */
	private void billSerch(){
		//必须选中一项商品
		NameSpecProperty nameAndSpec = (NameSpecProperty)cbbName.getSelectedItem();			
		//日期检查
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		if (beginDate != null && endDate != null) {
			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "开始日期大于结束日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		ScmCoc scmCoc = (ScmCoc)this.cbCustomers.getSelectedItem();
	   if (scmCoc == null) {
			if (JOptionPane.showConfirmDialog(this,
					"如果不选择客户供应商,可能会出现对应不合规范!!\n确定要继续吗??", "警告!!!",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
				return;
			}
		}
		
		//查询线程
		serch(nameAndSpec,scmCoc,beginDate, endDate);
	}
	
	private void serch(NameSpecProperty nameAndSpec,ScmCoc scmCoc,Date beginDate, Date endDate){
		//查询线程
		new SearchThread(nameAndSpec,scmCoc,beginDate, endDate).start();
		//查询线程
		new SearchThread1(nameAndSpec,scmCoc,beginDate, endDate).start();
	}
	
	/**
	 *单据查询线程
	 * 
	 * @author 
	 * 
	 */
	class SearchThread extends Thread{

		/**
		 * 开始日期
		 */
		private Date beginDate;

		/**
		 * 结束日期
		 */
		private Date endDate;
		
		/**
		 * 名称/规格
		 */
		private NameSpecProperty nameAndSpec;
		
		/**
		 * 客户
		 */
		private ScmCoc scmCoc;
		
		/**
		 * 
		 * @param beginDate
		 * @param endDate
		 */
		public SearchThread(NameSpecProperty nameAndSpec,ScmCoc scmCoc,Date beginDate, Date endDate) {
			this.beginDate = beginDate;
			this.endDate = endDate;
			this.nameAndSpec = nameAndSpec;
			this.scmCoc = scmCoc;
		}
		
		public void run() {
						//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			List<BlsInOutStockBillDetail> inBillInfo = null;
			List<BlsInOutStockBillDetail> outBillInfo = null;
			List<BillMatchInfo> matchBillInfo = null;
			try {
				JDialog log = new JDialog();
				log.setVisible(false);				
				String name = "";
				String spec = "";
				String nameSpecStr = "";
				if(nameAndSpec != null){
					name = nameAndSpec.getName();
					spec = nameAndSpec.getSpec();
					nameSpecStr = nameAndSpec.getNameSpec();
				}
				String customer = scmCoc==null?"":scmCoc.getCode();
				
				if (jTabbedPane.getSelectedIndex() == 0) {
					inBillInfo = blsInOutStockBillAction
							.findBlsInStockBillDetail(new Request(CommonVars
									.getCurrUser(), true), name, spec, scmCoc,
									beginDate, endDate);
					initInTable(inBillInfo);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					matchBillInfo = blsInOutStockBillAction.findBillMatchInfo(new Request(CommonVars
							.getCurrUser(), true),
							name, 
							spec, 
							scmCoc==null?"":scmCoc.getName(),
							beginDate,
							endDate);
					initMatchTable(matchBillInfo);
				}				
//				JOptionPane.showMessageDialog(FmBillCorresponding.this, "查询完成",
//						"提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBillCorresponding.this,
						"系统正在获取数据失败！！！" + e.getMessage(), "提示", 2);
			}
			
		}

		
	}
	
	/**
	 *单据查询线程
	 * 
	 * @author 
	 * 
	 */
	class SearchThread1 extends Thread{

		/**
		 * 开始日期
		 */
		private Date beginDate;

		/**
		 * 结束日期
		 */
		private Date endDate;
		
		/**
		 * 名称/规格
		 */
		private NameSpecProperty nameAndSpec;
		
		/**
		 * 客户
		 */
		private ScmCoc scmCoc;
		
		/**
		 * 
		 * @param beginDate
		 * @param endDate
		 */
		public SearchThread1(NameSpecProperty nameAndSpec,ScmCoc scmCoc,Date beginDate, Date endDate) {
			this.beginDate = beginDate;
			this.endDate = endDate;
			this.nameAndSpec = nameAndSpec;
			this.scmCoc = scmCoc;
		}
		
		public void run() {
						//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			List<BlsInOutStockBillDetail> inBillInfo = null;
			List<BlsInOutStockBillDetail> outBillInfo = null;
			List<BillMatchInfo> matchBillInfo = null;
			try {
				JDialog log = new JDialog();
				log.setVisible(false);				
				String name = "";
				String spec = "";
				String nameSpecStr = "";
				if(nameAndSpec != null){
					name = nameAndSpec.getName();
					spec = nameAndSpec.getSpec();
					nameSpecStr = nameAndSpec.getNameSpec();
				}
				String customer = scmCoc==null?"":scmCoc.getCode();
				
				if (jTabbedPane.getSelectedIndex() == 0) {
				
					outBillInfo = blsInOutStockBillAction
							.findBlsOutStockBillDetail(new Request(CommonVars
									.getCurrUser(), true), name, spec, scmCoc,
									beginDate, endDate);
					initOutTable(outBillInfo);					
				}	
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmBillCorresponding.this,
						"系统正在获取数据失败！！！" + e.getMessage(), "提示", 2);
			}
			
		}

		
	}
	
	
	/**
	 * 初始化出仓单据商品
	 * @param list
	 */
	private void initOutTable(List list){
		tableModelOutBillCommInfo =  new JTableListModel(tbOutBill, list,
				new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("单据编号", "bsb.billNo",100));
				list.add(addColumn("商品序号", "seqNo",100));
				list.add(addColumn("料号", "partNo.ptNo",100));
				list.add(addColumn("商品名称", "warehouseName",100));
				list.add(addColumn("商品规格", "spec",100));
				list.add(addColumn("商品单位", "unit.name",50));
				list.add(addColumn("数量", "detailQty",50));
				list.add(addColumn("生效日期", "bsb.validDate",100));
				list.add(addColumn("客户名称", "bsb.corrOwner.name",150));
				list.add(addColumn("入仓单单据号", "inBillNo",100));
				list.add(addColumn("入仓单商品序号", "inBillGoodNo",100));
				list.add(addColumn("备注1", "remarks1",100));
				list.add(addColumn("备注2", "remarks2",100));
				return list;
			}
		});
	}
	
	/**
	 * 初始化入仓单据商品
	 * @param list
	 */
   private void initInTable(List list){
	   tableModelInBillInfo =  new JTableListModel(tbFirstBill, list,
				new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("单据编号", "bsb.billNo",100));
				list.add(addColumn("商品序号", "seqNo",100));
				list.add(addColumn("料号", "partNo.ptNo",100));
				list.add(addColumn("商品名称", "warehouseName",100));
				list.add(addColumn("商品规格", "spec",100));
				list.add(addColumn("商品单位", "unit.name",50));
				list.add(addColumn("期初数量", "detailQty",50));
				list.add(addColumn("剩余数量", "nowDetailQty",50));
				list.add(addColumn("生效日期", "bsb.validDate",100));				
				list.add(addColumn("客户名称", "bsb.corrOwner.name",150));
				return list;
			}
		});
	}
   
   /**
    * 计算单据key HashMap
    * @param firstBills
    * @return
    */
   private Map<String,BlsInOutStockBillDetail> getFirstBillMap(List<BlsInOutStockBillDetail> bills){
		Map firstBillMap = null;		
	   if(bills!=null && bills.size()>0){
		   firstBillMap = new HashMap<String, String>();
		   for(BlsInOutStockBillDetail bill : bills){
				String key = getKey(bill);				
				firstBillMap.put(key, bill);
			}
	   }
	   return firstBillMap;
   }
   
   /**
    * 计算初期单据的KEY=(期初单据key=名称+规格+单位(编码)+客户(编码)+(料号))
    * @param bill
    * @return
    */
   private String getKey(BlsInOutStockBillDetail bill){
	   String key = 
//		        (bill.getWarehouseName()==null?"":bill.getWarehouseName())+"/"
//				+(bill.getSpec()==null?"":bill.getSpec())+"/"
//				+(bill.getUnit()==null?"":bill.getUnit().getCode())+"/"
//				+
				(bill.getBsb().getCorrOwner()==null?"":bill.getBsb().getCorrOwner().getCode()+"/"
				+(bill.getPartNo()==null?"":bill.getPartNo().getPtNo()));	   
	   return key;
   }
   
	/**
	 * This method initializes btnCorresponding
	 * 单据做对应
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCorresponding() {
		if (btnCorresponding == null) {
			btnCorresponding = new JButton();
			btnCorresponding.setText("单据对应");
			btnCorresponding.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {	
					
					new BillMatch().execute();
				
				}
			});
		}
		return btnCorresponding;
	}
	
	class BillMatch extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			if(billMatchSet ==null || billMatchSet.getIsCheack()){
				if(JOptionPane.OK_OPTION!=JOptionPane.showConfirmDialog(FmBillCorresponding.this, "确认做单据对应！","单据对应",JOptionPane.OK_CANCEL_OPTION)){
					return null;
				}
			}
			
			if(tableModelOutBillCommInfo==null || tableModelInBillInfo==null){
				JOptionPane.showMessageDialog(FmBillCorresponding.this, "请选择需要对应的单据", 
						"单据对应提示", 
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
			//出仓单据
			List<BlsInOutStockBillDetail> outBills = tableModelOutBillCommInfo.getCurrentRows();
			if(outBills==null || outBills.size()<=0){
				JOptionPane.showMessageDialog(FmBillCorresponding.this, "请选择出仓单据", "单据对应提示", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			//选中的期初单据
			List<BlsInOutStockBillDetail> firstBills = tableModelInBillInfo.getCurrentRows();
			//如果选中，则对应所有期初单据,自动对应
			if(firstBills==null || firstBills.size()==0){
				firstBills = tableModelInBillInfo.getList();
			}
			//如果没有任何期初单据，则退出
			if(firstBills==null || firstBills.size()==0){
				JOptionPane.showMessageDialog(FmBillCorresponding.this, 
						"没有任何期初单据，请添加期初单据!", 
						"单据对应提示", 
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
//			//消息框
//			MessageInfo ms = new MessageInfo(FmBillCorresponding.this,true,"单据对应");
//			//信息提示
//			StringBuffer info = new StringBuffer();
		
//			long start = System.currentTimeMillis();
			CommonProgress.showProgressDialog(FmBillCorresponding.this);
			CommonProgress.setMessage("系统正在处理数据，请稍后...");		
			long start = System.currentTimeMillis();
			blsInOutStockBillAction.matchInAndOutBill(new Request(
			      CommonVars.getCurrUser(), true),outBills,firstBills);
			System.out.println("对应："+(System.currentTimeMillis()-start));
			//期初单据keys
//			Map<String,BlsInOutStockBillDetail> firstBillMap = getFirstBillMap(firstBills);
////			System.out.println("初始化期初单据："+(System.currentTimeMillis()-start));
//			//单据对应信息
//			List<BillMatchInfo> matchBillInfoS = new ArrayList<BillMatchInfo>();
////			 info.delete(0, info.length());
////			 info.append("\n**********\n");
////			 info.append("信息：\n");
////			 info.append("单据对应开始...");	
////			 ms.addMessage(info.toString());
////			long start1 = System.currentTimeMillis();
//			
//			//开始做对应
//			for(BlsInOutStockBillDetail outBill : outBills){
////				 start = System.currentTimeMillis();
//				if(firstBillMap.containsKey(getKey(outBill))){//出仓单据有对应的初期单据，则对应
//					//获得对应的初期单据
//					BlsInOutStockBillDetail firstBill = firstBillMap.get(getKey(outBill));
////					//计算初期单据剩余数量
////					Double historyQty = blsInOutStockBillAction.findFirstBillMatchQty(new Request(
////						      CommonVars.getCurrUser(), true),firstBill);
////					Double nowQty = (CommonUtils.getDoubleExceptNull(firstBill.getDetailQty())
////							-CommonUtils.getDoubleExceptNull(historyQty));
//					
//					//检查待对应出仓单据对应的入仓数量是否足够					
//					if(CommonUtils.getDoubleExceptNull(outBill.getDetailQty())
//							> CommonUtils.getDoubleExceptNull(firstBill.getNowDetailQty()) ){
////						 info.delete(0, info.length());
////						 info.append("\n**********\n");
////						 info.append("警告：\n");
////						 info.append("出仓单据："+outBill.getBsb().getBillNo()
////								 +"\t出仓单据商品序号："+outBill.getSeqNo()+"\n");
////						 info.append("期初单据："+firstBill.getBsb().getBillNo()
////								 +"\t期初单据商品序号："+firstBill.getSeqNo()+"\n");
////						 info.append("!对应失败--期初库存数量不及出仓数量!");
////						 ms.addMessage(info.toString());
//						 
//						 continue;
//					}
//					//新建对应记录
//					BillMatchInfo billMatchInfo = new BillMatchInfo();
//					billMatchInfo.setInBillNo(firstBill.getBsb().getBillNo());
//					billMatchInfo.setOutBillNo(outBill.getBsb().getBillNo());
//					billMatchInfo.setMatchNo(firstBill.getSeqNo());
//					billMatchInfo.setOutMatchNo(outBill.getSeqNo());
//					billMatchInfo.setMatchName(firstBill.getWarehouseName());
//					billMatchInfo.setMatchSpec(firstBill.getSpec());
//					billMatchInfo.setUnitName(firstBill.getUnit().getName());
//					billMatchInfo.setCustomerName(firstBill.getBsb().getCorrOwner().getName());
//					billMatchInfo.setOldInBillNo(outBill.getInBillNo());
//					billMatchInfo.setOldInBillGoodNo(outBill.getSeqNo());
//					billMatchInfo.setDetailQty(outBill.getDetailQty());//对应数量,待检查
//					billMatchInfo.setMatchDate(new Date());
//					billMatchInfo.setCompany(firstBill.getCompany());	
//					billMatchInfo.setPtNo(firstBill.getPartNo().getPtNo());
//					matchBillInfoS.add(billMatchInfo);
//					//保存对应
//					blsInOutStockBillAction.saveBillMatchInfo(new Request(
//				      CommonVars.getCurrUser(), true), billMatchInfo);
//					//对应了之后，出仓单据对应初期单据
//					outBill.setInBillNo(firstBill.getBsb().getBillNo());
//					outBill.setInBillGoodNo(firstBill.getSeqNo());
//					
//					//期初单据数量
//					firstBill.setNowDetailQty(CommonUtils.getDoubleExceptNull(firstBill.getNowDetailQty())
//							- CommonUtils.getDoubleExceptNull(outBill.getDetailQty()));
////					info.delete(0, info.length());
////					 info.append("\n**********\n");
////					 info.append("信息：\n");
////					 info.append("出仓单据："+outBill.getBsb().getBillNo()
////							 +"\t出仓单据商品序号："+outBill.getSeqNo()+"\n");
////					 info.append("期初单据："+firstBill.getBsb().getBillNo()
////							 +"\t期初单据商品序号："+firstBill.getSeqNo()+"\n");
////					 info.append("!对应成功");
////					 ms.addMessage(info.toString());
//					 
//				}	
////				System.out.println("一个花费："+(System.currentTimeMillis()-start));
//			}
////			System.out.println("对应："+(System.currentTimeMillis()-start1));
////			 info.append("\n**********\n");
////			 info.append("信息：\n");
////			 info.append("单据对应结束");	
////			 ms.addMessage(info.toString());
////			 info.delete(0, info.length());
////			start = System.currentTimeMillis();
//			//出仓单据更新
//			blsInOutStockBillAction.saveBlsInOutStockBillDetail(new Request(
//				      CommonVars.getCurrUser(), true), outBills);
////			System.out.println("出更新："+(System.currentTimeMillis()-start));
////			start = System.currentTimeMillis();
//			//期初单据更新
//			blsInOutStockBillAction.saveBlsInOutStockBillDetail(new Request(
//				      CommonVars.getCurrUser(), true), firstBills);
////			System.out.println("入更新："+(System.currentTimeMillis()-start));
////			System.out.println("对应完成");
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(FmBillCorresponding.this, 
					"单据对应完成！", 
					"单据对应", 
					JOptionPane.OK_OPTION);
			//单据重新重询
			billSerch();
			return null;
		}
		
	}
	
	/**
	 * 初始化单据对应表格信息
	 * @param list
	 */
   private void initMatchTable(List list){//TODO
	   tableModelMatchInfo =  new JTableListModel(tbMatchInfo, list,
				new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("出仓单据编号", "outBillNo",100));
				list.add(addColumn("出仓单据商品序号", "outMatchNo",100));
				list.add(addColumn("期初单据编号", "inBillNo",100));				
				list.add(addColumn("期初单据商品序号", "matchNo",100));
				list.add(addColumn("料号", "ptNo",100));
				list.add(addColumn("商品名称", "matchName",100));
				list.add(addColumn("商品规格", "matchSpec",100));
				list.add(addColumn("商品单位", "unitName",50));
				list.add(addColumn("客户名称", "customerName",100));
				list.add(addColumn("对应数量", "detailQty",100));
				list.add(addColumn("对应日期", "matchDate",100));
				return list;
			}
		});
	}

	/**
	 * This method initializes btnCancelCorresponding
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancelCorresponding() {
		if (btnCancelCorresponding == null) {
			btnCancelCorresponding = new JButton();
			btnCancelCorresponding.setText("取消对应");
			btnCancelCorresponding.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new BillMatchDelete().execute();
				}

			});
		}
		return btnCancelCorresponding;
	}
	
	class BillMatchDelete extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			if(tableModelMatchInfo==null){
				JOptionPane.showMessageDialog(FmBillCorresponding.this, 
						"请选择要取消对应的信息!", 
						"对应取消提示",
						JOptionPane.ERROR);
				return null;
			}
			//取消对应
			List<BillMatchInfo> billMatchInfoS = tableModelMatchInfo.getCurrentRows();
			if(billMatchInfoS==null || billMatchInfoS.size()<=0){
				JOptionPane.showMessageDialog(FmBillCorresponding.this, 
						"请选择要取消对应的信息!", 
						"对应取消提示",
						JOptionPane.OK_OPTION);
				return null;
			}
//			//消息框
//			MessageInfo ms = new MessageInfo(FmBillCorresponding.this,true,"单据对应取消");
//			
//			//信息提示
//			StringBuffer info = new StringBuffer();
//			
//			info.append("\n**********\n");
//			info.append("对应取消开始...\n");
			CommonProgress.showProgressDialog(FmBillCorresponding.this);
			CommonProgress.setMessage("系统正在处理数据，请稍后..."); 
			long start = System.currentTimeMillis();
			blsInOutStockBillAction.deleteMatchInfo(new Request(CommonVars
					.getCurrUser(), true), billMatchInfoS);
			System.out.println("共："+(System.currentTimeMillis()-start));
//			for (BillMatchInfo billMatchInfo : billMatchInfoS) {
//						BlsInOutStockBillDetail outBill = (BlsInOutStockBillDetail) blsInOutStockBillAction
//								.findBlsOutStockBillDetailByMatchInfo(
//										new Request(CommonVars
//												.getCurrUser(), true),
//										billMatchInfo);
//						
//						BlsInOutStockBillDetail firstBill = (BlsInOutStockBillDetail) blsInOutStockBillAction
//						.findBlsInStockBillDetailByMatchInfo(
//								new Request(CommonVars
//										.getCurrUser(), true),
//								billMatchInfo);
//						
//						if(outBill==null){									
//							// 删除对应
//							blsInOutStockBillAction
//									.deletesaveBillMatchInfo(billMatchInfo);
////							info.delete(0, info.length());
////							info.append("\n**********\n");
////							info.append("警告：\n");
////							info.append("出仓单据编号："+billMatchInfo.getOutBillNo()+"\n");
////							info.append("出仓单据商品序号："+billMatchInfo.getOldInBillGoodNo()+"\n");
////							info.append("所对应的出仓单据不存在了,此对应被强行删除!");
////							ms.addMessage(info.toString());
//							continue;
//						}
//						//还原入仓单据
//						if(firstBill!=null){
//							
//							firstBill.setNowDetailQty(CommonUtils.getDoubleExceptNull(firstBill.getNowDetailQty())
//									+CommonUtils.getDoubleExceptNull(billMatchInfo.getDetailQty()));
//							long start = System.currentTimeMillis();
//							blsInOutStockBillAction.saveBlsInOutStockBillDetail(new Request(CommonVars
//										.getCurrUser(), true), firstBill);
//							System.out.println("保存："+(System.currentTimeMillis()-start));
//						}
//
//						// 还原出仓单据对应
//						outBill.setInBillNo(billMatchInfo
//								.getOldInBillNo());
//						outBill.setInBillGoodNo(billMatchInfo
//								.getOldInBillGoodNo());
//						blsInOutStockBillAction
//								.saveBlsInOutStockBillDetail(
//										new Request(CommonVars
//												.getCurrUser(), true),
//										outBill);
//						// 删除对应
//						blsInOutStockBillAction
//								.deletesaveBillMatchInfo(billMatchInfo);								
//					
////						info.delete(0, info.length());
////						info.append("\n**********\n");
////						info.append("信息：\n");								
////						info.append("出仓单据编号："+billMatchInfo.getOutBillNo()+"\n");
////						info.append("出仓单据商品序号："+billMatchInfo.getOutMatchNo()+"\n");
////						info.append("入仓单据编号："+billMatchInfo.getInBillNo()+"\n");
////						info.append("入仓单据商品序号："+billMatchInfo.getMatchNo()+"\n");
////						info.append("对应关系成功取消.................");
////						ms.addMessage(info.toString());
//						
//					}
			tableModelMatchInfo.deleteRows(billMatchInfoS);
			CommonProgress.closeProgressDialog();
//			info.delete(0, info.length());
//			info.append("\n**********\n");
//			info.append("对应取消结束...\n");
//			ms.addMessage(info.toString());
//			DgMessageInfo.showMessageDialog(FmBillCorresponding.this, info.toString(), "取消对应关系", JOptionPane.OK_OPTION);
			JOptionPane.showMessageDialog(FmBillCorresponding.this, 
					"单据对应取消完成！", 
					"单据对应取消", 
					JOptionPane.OK_OPTION);
			return null;
		}
		
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes top
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTop() {
		if (top == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(365, 61, 81, 27));
			jLabel3.setText("结束日期：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(17, 61, 73, 27));
			jLabel2.setText("起始日期：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(364, 15, 82, 27));
			jLabel1.setText("名称/规格：");
			jLabel = new JLabel();
			jLabel.setText("客户名称：");
			jLabel.setBounds(new Rectangle(18, 15, 72, 27));
			top = new JPanel();
			top.setLayout(null);
			top.setPreferredSize(new Dimension(1, 100));
			top.add(jLabel, null);
			top.add(getCbCustomers(), null);
			top.add(jLabel1, null);
			top.add(getJComboBox(), null);
			top.add(jLabel2, null);
			top.add(jLabel3, null);
			top.add(getCbbEndDate(), null);
			top.add(getCbbBeginDate(), null);
		}
		return top;
	}

	/**
	 * This method initializes center
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCenter() {
		if (center == null) {
			center = new JPanel();
			center.setLayout(new BorderLayout());
			center.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return center;
	}

	/**
	 * This method initializes cbCustomers
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbCustomers() {
		if (cbCustomers == null) {
			cbCustomers = new JComboBox();
			cbCustomers.setBounds(new Rectangle(104, 15, 178, 27));
		}
		return cbCustomers;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setBounds(new Rectangle(465, 15, 260, 27));
		}
		return cbbName;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(104, 61, 151, 27));
			cbbBeginDate.setDate(null);
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
			cbbEndDate.setBounds(new Rectangle(465, 61, 151, 27));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("单据对应", null, getJPanel2(), null);
			jTabbedPane.addTab("对应关系", null, getJPanel3(), null);
			jTabbedPane.addTab("选项", null, getJPanel4(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if(jTabbedPane.getSelectedIndex()==2){
						if(billMatchSet == null){
							billMatchSet = new BillMatchSet();
						}
						cbIsCheck.setSelected(billMatchSet.getIsCheack());
						btnSave.setEnabled(false);
						cbIsCheck.setEnabled(false);
						btnEdit.setEnabled(true);
					}
					setState();
				}
			});
		}
		return jTabbedPane;
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
			jPanel2.add(getJSplitPane(), BorderLayout.CENTER);
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
			jPanel4.add(getJPanel7(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJPanel5());
			jSplitPane.setRightComponent(getJPanel6());
			jSplitPane.setDividerLocation(450);
		}
		return jSplitPane;
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
			jPanel5.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbOutBill());
			jScrollPane.setBorder(new TitledBorder("出仓单据待对应商品"));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbOutBill
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbOutBill() {
		if (tbOutBill == null) {
			tbOutBill = new JTable();
		}
		return tbOutBill;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbFirstBill());
			jScrollPane1.setBorder(new TitledBorder("期初单据商品"));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbFirstBill
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFirstBill() {
		if (tbFirstBill == null) {
			tbFirstBill = new JTable();
		}
		return tbFirstBill;
	}
	
	/**
	 * 商品名称/规格
	 * @author Administrator
	 *
	 */
	public class NameSpecProperty {
		private String nameSpec;
		
		private String spec;

		private String name;

		public NameSpecProperty(String name,String spec) {
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
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbMatchInfo());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbMatchInfo	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbMatchInfo() {
		if (tbMatchInfo == null) {
			tbMatchInfo = new JTable();
		}
		return tbMatchInfo;
	}
	
	/**
	 * 状态设置
	 */
	private void setState(){
		if(jTabbedPane.getSelectedIndex() == 0 ){
			btnBillSerch.setEnabled(true);
			btnCorresponding.setEnabled(true);
			btnCancelCorresponding.setEnabled(false);
		}else if (jTabbedPane.getSelectedIndex() == 1) {
			btnBillSerch.setEnabled(true);
			btnCorresponding.setEnabled(false);
			btnCancelCorresponding.setEnabled(true);
		}else{
			btnBillSerch.setEnabled(false);
			btnCorresponding.setEnabled(false);
			btnCancelCorresponding.setEnabled(false);
		}
	}

	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jPanel7.add(getJPanel8(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(null);
			jPanel8.setBounds(new Rectangle(33, 24, 686, 258));
			jPanel8.setBorder(new TitledBorder("选项"));
			jPanel8.add(getCbIsCheck(), null);
			jPanel8.add(getBtnEdit(), null);
			jPanel8.add(getBtnSave(), null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes cbIsCheck	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbIsCheck() {
		if (cbIsCheck == null) {
			cbIsCheck = new JCheckBox();
			cbIsCheck.setBounds(new Rectangle(54, 51, 176, 30));
			cbIsCheck.setText("单据对应须确认");
		}
		return cbIsCheck;
	}

	/**
	 * This method initializes btnEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(100, 193, 86, 28));
			btnEdit.setText("修改设置");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					btnEdit.setEnabled(false);
					btnSave.setEnabled(true);
					cbIsCheck.setEnabled(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(266, 193, 86, 28));
			btnSave.setText("保存设置");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(billMatchSet == null ){ 
						billMatchSet = new BillMatchSet();
					}
					billMatchSet.setIsCheack(cbIsCheck.isSelected());
					billMatchSet = blsInOutStockBillAction.saveBillMatchSet(new Request(
						      CommonVars.getCurrUser(), true), billMatchSet);
					btnSave.setEnabled(false);
					btnEdit.setEnabled(true);
					cbIsCheck.setEnabled(false);
				}
			});
		}
		return btnSave;
	}
	
	class getNameAndSpec extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			/**
			 * 初始化商品大类名称
			 * 出仓单据里的没有对应转入单据商品，或不存在的对应转入单据商品信息
			 */
			List<Object[]> listName = blsInOutStockBillAction.findBlsOutStockBillDetailNoInputBill(new Request(
					CommonVars.getCurrUser(), true));
//			System.out.println("初始化商品发了："+(System.currentTimeMillis()-start)+"毫秒");
			cbbName.removeAllItems();
			DefaultComboBoxModel model  = new DefaultComboBoxModel();
			for (Object[] item : listName) {
				String name = item[0] ==null?"":(String) item[0];
				String spec = item[1] ==null?"":(String) item[1];	
				NameSpecProperty nameSpec = new NameSpecProperty(name,spec);
				model.addElement(nameSpec);
			}
			cbbName.setModel(model);
			cbbName.setSelectedItem(null);
		}
		
	}


} // @jve:decl-index=0:visual-constraint="10,10"

