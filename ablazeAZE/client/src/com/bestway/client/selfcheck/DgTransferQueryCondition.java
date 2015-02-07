
package com.bestway.client.selfcheck;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.bestway.bcs.contractstat.entity.TempBcsCustomsDeclarCommInfo;
import com.bestway.bcus.cas.action.CasCheckAction;
import com.bestway.bcus.cas.invoice.entity.TempCustomsDeclarCommInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgTransferQueryCondition extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbCommInfo = null;

	private JComboBox cbbCustomer = null;

	private JComboBox cbbImpExpType = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private List contracts = null; // @jve:decl-index=0:
	
	//private ContractStatAction  contractStatAction = null;

	private CasCheckAction casCheckAction = null;

	private List lsResult = null;  //  @jve:decl-index=0:

	private int state = CustomsDeclarationState.EFFECTIVED;

	private boolean isImport;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbName = null;

	private JComboBox cbbCode = null;

	private JComboBox cbbSeqNo = null;
	
	private int impExpFlag = 0;
	
	private int currentType = 0;
	
	

	public int getCurrentType() {
		return currentType;
	}

	public void setCurrentType(int currentType) {
		this.currentType = currentType;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}
	public void setImpExpFlag(int impExpFlag){
		this.impExpFlag = impExpFlag;
	}
	public int getImpExpFlag(){
		return impExpFlag;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgTransferQueryCondition() {
		super();
		initialize();
		casCheckAction = (CasCheckAction) CommonVars
				.getApplicationContext().getBean("casCheckAction");
		
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			//setCbbState();暂时不用先
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		// 商品
		List<TempCustomsDeclarCommInfo> dlist = new ArrayList<TempCustomsDeclarCommInfo>();
		//查询转厂报关的商品
		dlist = casCheckAction
						.findCustomsDeclarationCommInfoDistance(new Request(CommonVars
								.getCurrUser()), isImport, contracts, state,currentType);

		Map map = new HashMap();
		Map mapName = new HashMap();
		Map mapCode = new HashMap();
		Map mapSeqNo = new HashMap();

		for (TempCustomsDeclarCommInfo data : dlist) {
			if (map.get(data.getCode() + data.getName()) == null) {
				map.put(data.getCode() + data.getName(), data);
			}
			if (mapName.get(data.getName()) == null) {
				mapName.put(data.getName(), data);
			}
			if (mapCode.get(data.getCode()) == null) {
				mapCode.put(data.getCode(), data);
			}
			if (mapSeqNo.get(data.getSeqNum()) == null) {
				mapSeqNo.put(data.getSeqNum(), data);
			}
		}

		List resultList = new ArrayList(map.values());
		List listName = new ArrayList(mapName.values());
		List listCode = new ArrayList(mapCode.values());
		List listSeqNo = new ArrayList(mapSeqNo.values());

		//商品下拉框
		cbbCommInfo.setModel(new DefaultComboBoxModel(resultList.toArray()));
		cbbCommInfo.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 160));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCommInfo,
				"code", "name", 260);
		cbbCommInfo.setSelectedIndex(-1);

		//名称下拉框
		cbbName.setModel(new DefaultComboBoxModel(listName.toArray()));
		cbbName.setRenderer(CustomBaseRender.getInstance().getRender("name",
				"name", 0, 160));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbName,
				"name", "name");
		cbbName.setSelectedIndex(-1);
		
		// 编码下拉框
		cbbCode.setModel(new DefaultComboBoxModel(listCode.toArray()));
		cbbCode.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"code", 100, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCode,
				"code", "code");
		cbbCode.setSelectedIndex(-1);
		
		// 序号下拉框
		cbbSeqNo.setModel(new DefaultComboBoxModel(listSeqNo.toArray()));
		cbbSeqNo.setRenderer(CustomBaseRender.getInstance().getRender("seqNum",
				"seqNum", 100, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbSeqNo,
				"seqNum", "seqNum");
		cbbSeqNo.setSelectedIndex(-1);
		
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
				this.cbbImpExpType.addItem(isImport == true ? 
						new ItemProperty(String.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"):
							new ItemProperty(String.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
				
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
					.getRender());	
		// 客户
		List list = null;
		//查询客户
		list = casCheckAction.findCustomsDeclarationCustomer(
								new Request(CommonVars.getCurrUser()), isImport,
													contracts, state,currentType);

		if(list.size()>0 && list!=null){
			this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
			this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
					"code", "name", 100, 100));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
					this.cbbCustomer, "code", "name");
			this.cbbCustomer.setSelectedIndex(-1);			
		}

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(398, 301);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(43, 99, 89, 22));
			jLabel7.setText("序号");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(43, 71, 89, 22));
			jLabel6.setText("编码");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(43, 43, 89, 22));
			jLabel5.setText("名称");
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(43, 13, 89, 22);
			jLabel.setText("检索商品");
			jLabel1.setBounds(43, 129, 89, 22);
			jLabel1.setText("检索客户");
			jLabel2.setBounds(43, 158, 89, 22);
			jLabel2.setText("进出口类型");
			jLabel3.setBounds(43, 187, 89, 22);
			jLabel3.setText("报关单日期范围");
			jLabel4.setBounds(231, 189, 13, 18);
			jLabel4.setText("至");
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbCommInfo(), null);
			jContentPane.add(getCbbCustomer(), null);
			jContentPane.add(getCbbImpExpType(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getCbbName(), null);
			jContentPane.add(getCbbCode(), null);
			jContentPane.add(getCbbSeqNo(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommInfo() {
		if (cbbCommInfo == null) {
			cbbCommInfo = new JComboBox();
			cbbCommInfo.setBounds(139, 13, 197, 22);
		}
		return cbbCommInfo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(139, 129, 197, 22);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(139, 158, 197, 22);
			cbbImpExpType.setEnabled(false);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(139, 188, 89, 20);
			cbbBeginDate.setDate(null);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(245, 188, 90, 19);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(74, 225, 90, 21);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return jButton;
	}

	private void queryData() {
		String commCode = "";
		String customer = "";
		String impExpType = "";
		Integer seqNum = null;
		String complexCode = "";
		String name = "";
		
		//电子化手册，电子手册需选定合同
		if ((currentType == 0 || currentType == 2)&& contracts.size() <= 0) {
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgTransferQueryCondition.this, "请选择合同!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
//		else if (contracts.size() == 1) {
//			if (cbbSeqNo.getSelectedItem() != null) {
//				seqNum = ((TempCustomsDeclarCommInfo) cbbSeqNo
//						.getSelectedItem()).getSeqNum();
//			}
//			if (cbbName.getSelectedItem() != null) {
//				name = ((TempCustomsDeclarCommInfo) cbbName
//						.getSelectedItem()).getName();
//			}
//			if (cbbCode.getSelectedItem() != null) {
//				commCode = ((TempCustomsDeclarCommInfo) cbbCode
//						.getSelectedItem()).getCode();
//			}
//		} else {
//			if (cbbCommInfo.getSelectedItem() != null) {
//				name = ((TempCustomsDeclarCommInfo) cbbCommInfo
//						.getSelectedItem()).getName();
//			}
//		}
//		
		
	
		if (cbbSeqNo.getSelectedItem() != null) {
			seqNum = ((TempCustomsDeclarCommInfo) cbbSeqNo
					.getSelectedItem()).getSeqNum();
		}
		if (cbbName.getSelectedItem() != null) {
			name = ((TempCustomsDeclarCommInfo) cbbName
					.getSelectedItem()).getName();
		}
		if (cbbCode.getSelectedItem() != null) {
			commCode = ((TempCustomsDeclarCommInfo) cbbCode
					.getSelectedItem()).getCode();
		}
		if (cbbCommInfo.getSelectedItem() != null) {
			name = ((TempCustomsDeclarCommInfo) cbbCommInfo
					.getSelectedItem()).getName();
		}
		if (cbbCustomer.getSelectedItem() != null) {
			customer = ((ScmCoc) cbbCustomer.getSelectedItem()).getId();
		}
		if (cbbImpExpType.getSelectedItem() != null) {
			impExpType = ((ItemProperty) cbbImpExpType.getSelectedItem())
					.getCode();
		}
		
		Date beginDate = cbbBeginDate.getDate();
		Date endDate = cbbEndDate.getDate();
		
		//查询送出货结转明细信息
		lsResult = casCheckAction.findTransferCustomsDeclarationCommInfo(
				new Request(CommonVars.getCurrUser()), isImport, seqNum,
				commCode, name, customer, impExpType, beginDate, endDate,
				contracts, state,impExpFlag,currentType);
	
		
		dispose();
	}

	private void setCbbState() {
		chooseConditions(contracts.size() <= 1);
	}

	private void chooseConditions(boolean isSingle) {
		cbbCommInfo.setEnabled(!isSingle);
		cbbName.setEnabled(isSingle);
		cbbCode.setEnabled(isSingle);
		cbbSeqNo.setEnabled(isSingle);
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(184, 225, 90, 21);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	public List getContracts() {
		return contracts;
	}

	public void setContracts(List contracts) {
		this.contracts = contracts;
	}

	/**
	 * This method initializes cbbName
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbName() {
		if (cbbName == null) {
			cbbName = new JComboBox();
			cbbName.setBounds(new Rectangle(139, 43, 197, 22));

		}
		return cbbName;
	}

	/**
	 * This method initializes cbbCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCode() {
		if (cbbCode == null) {
			cbbCode = new JComboBox();
			cbbCode.setBounds(new Rectangle(139, 72, 197, 22));
		}
		return cbbCode;
	}

	/**
	 * This method initializes cbbSeqNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSeqNo() {
		if (cbbSeqNo == null) {
			cbbSeqNo = new JComboBox();
			cbbSeqNo.setBounds(new Rectangle(139, 100, 197, 22));
		}
		return cbbSeqNo;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			queryData();
			CommonProgress.closeProgressDialog();
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
