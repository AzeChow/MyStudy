/*
 * Created on 2005-5-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import javax.swing.JComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgImgWearDetail extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTableListModel tableModel = null;

	private EncAction encAction = null;

	private List list = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private List lsResult = null;

	private JButton jButton1 = null;

	private ManualDeclareAction manualDecleareAction = null;
	
	private MaterialManageAction materialManageAction = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox jCalendarComboBox = null;

	private JLabel jLabel3 = null;

	private JCalendarComboBox jCalendarComboBox1 = null;

	private JButton jButton2 = null;

	private JLabel jLabel4 = null;

	private List billlist = new Vector(); // @jve:decl-index=0:

	private EmsEdiMergerHead emsHead = null;

	private JButton jButton3 = null;

	private Integer[] wh = new Integer[2];

	private JRadioButton rbEms = null;

	private JRadioButton rbBillList = null;

	private ButtonGroup gp = new ButtonGroup();

	private JLabel jLabel1 = null;

	private JComboBox cbbPorjectDept = null;

	/**
	 * This is the default constructor
	 */
	public DgImgWearDetail() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		initialize();

	}

	private void initUIComponents() {
		// 事业部
		List projectList = materialManageAction.findProjectDept(new Request(
				CommonVars.getCurrUser(), true));
		this.cbbPorjectDept
				.setModel(new DefaultComboBoxModel(projectList.toArray()));
		this.cbbPorjectDept.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPorjectDept, "code", "name");
		cbbPorjectDept.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(744, 454);
		this.setTitle("料件耗用明细表");
		wh[0] = this.getWidth();
		wh[1] = this.getHeight();
		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		//
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// emsHead = manualDecleareAction
		// .findEmsEdiMergerHeadByDeclareState(new Request(
		// CommonVars.getCurrUser(), true),
		// DeclareState.PROCESS_EXE);
		
		// list = new Vector();
		// initTable(list);
		//
		// }
		// });
		gp.add(rbEms);
		gp.add(rbBillList);
		 initUIComponents();
		
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			emsHead = manualDecleareAction.findEmsEdiMergerHeadByDeclareState(
					new Request(CommonVars.getCurrUser(), true),
					DeclareState.PROCESS_EXE);
			initUIComponents();
			list = new Vector();
			initTable(list);
			initTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("料件序号", "bill10", 100,
										Integer.class));
						list.add(addColumn("料件货号", "bill1", 100));
						list.add(addColumn("料件名称", "bill2", 140));
						list.add(addColumn("料件规格", "bill3", 140));
						list
								.add(addColumn("成品序号", "bill11", 100,
										Integer.class));
						list.add(addColumn("成品货号", "bill4", 100));
						list.add(addColumn("成品名称", "bill5", 100));
						list.add(addColumn("成品规格", "bill6", 100));
						list.add(addColumn("版本号", "bill7", 50));
						list.add(addColumn("单耗", "bill12", 80));
						list.add(addColumn("损耗", "bill13", 80));
						list.add(addColumn("总出口量", "billSum4", 100));
						list.add(addColumn("总耗用量", "billSum3", 100));
						return list;
					}
				});
		// 截取小数位
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());

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
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(397, 40, 48, 21));
			jLabel1.setText("事业部");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(9, 44, 139, 31));
			jLabel4
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel4.setForeground(new java.awt.Color(255, 102, 51));
			jLabel4.setText("料号级");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(398, 10, 18, 22));
			jLabel3.setText("至");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(197, 9, 97, 21));
			jLabel2.setText("报关单申报日期");
			jLabel = new JLabel();
			jLabel.setText("料件耗用明细表");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(8, 8, 182, 34);

			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.blue);

			jPanel.add(jLabel, null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getRbEms(), null);
			jPanel.add(getRbBillList(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbPorjectDept(), null);
		}
		return jPanel;
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(507, 66, 94, 23);
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (billlist == null || billlist.size() < 1) {
						JOptionPane.showMessageDialog(DgImgWearDetail.this,
								"请先选择一个物料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (emsHead == null) {
						return;
					}
					String deptId = null;
					if(cbbPorjectDept.getSelectedItem()!=null){
						deptId = ((ProjectDept)cbbPorjectDept.getSelectedItem()).getId();
					}					
					new find(deptId).start();
				}
			});
		}
		return jButton;
	}

	class find extends Thread {

		String deptId;
		
		public find(String deptId){
		      this.deptId=deptId;
		}
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date beginDate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date endDate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				String version = null;
				boolean isEmsOrBillList = rbEms.isSelected() ? true
						: rbBillList.isSelected() ? false : true;	
				System.out.println("事业部id:"+deptId);
				lsResult = encAction.getLjUseDetail(new Request(CommonVars
						.getCurrUser()), billlist, beginDate, endDate, emsHead,
						version,isEmsOrBillList,deptId);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImgWearDetail.this, "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(614, 66, 86, 23);
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setBounds(new Rectangle(296, 9, 95, 21));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1
					.setBounds(new Rectangle(416, 10, 113, 22));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(397, 66, 95, 23));
			jButton2.setText("选择料件");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = (List) CommonQueryPage.getInstance()
							.getEmsEdiImgbeforeAll();
					if (list == null) {
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list
								.get(i);
						BillTemp bill = new BillTemp();
						bill.setBill1(obj.getPtNo());
						bill.setBill2(obj.getName());
						bill.setBill3(obj.getSpec());
						bill.setBill11(String.valueOf(obj
								.getEmsEdiMergerVersion()
								.getEmsEdiMergerBefore().getSeqNum()));
						bill.setBill4(obj.getEmsEdiMergerVersion()
								.getEmsEdiMergerBefore().getPtNo());
						bill.setBill5(obj.getEmsEdiMergerVersion()
								.getEmsEdiMergerBefore().getName());
						bill.setBill6(obj.getEmsEdiMergerVersion()
								.getEmsEdiMergerBefore().getSpec());
						bill
								.setBill7(String
										.valueOf(obj.getEmsEdiMergerVersion()
												.getVersion() == null ? 0 : obj
												.getEmsEdiMergerVersion()
												.getVersion()));
//						BigDecimal bg = new BigDecimal(
//								obj.getUnitWaste() == null ? 0 : obj
//										.getUnitWaste());
//						String UnitWaste = bg.setScale(9,
//								BigDecimal.ROUND_HALF_UP).toString();
//						bill.setBill12(UnitWaste);

//						BigDecimal bg1 = new BigDecimal(
//								obj.getWaste() == null ? 0 : obj.getWaste());
//						String Waste = bg1
//								.setScale(9, BigDecimal.ROUND_HALF_UP)
//								.toString();
//						bill.setBill13(Waste);
						
						//判断单耗是否等于0,当等于0时无法toPlainString()去掉多余的0
						if(obj.getUnitWaste()==0){
							bill.setBill12(CommonUtils.getString(obj.getUnitWaste()==null?
									"0.0":obj.getUnitWaste().toString()));
						}else{
							String bd = new BigDecimal(obj.getUnitWaste()).setScale(9,BigDecimal.ROUND_HALF_UP)
									.stripTrailingZeros().toPlainString();
							bill.setBill12(bd);
						}
						
						//判断损耗是否等于0,当等于0时无法toPlainString()去掉多余的0
						if(obj.getWaste()==0){
							bill.setBill13(CommonUtils.getString(obj.getWaste()==null?
									"0.0":obj.getWaste().toString()));
						}else{
							String bd = new BigDecimal(obj.getWaste()).setScale(9,BigDecimal.ROUND_HALF_UP)
									.stripTrailingZeros().toPlainString();
							bill.setBill13(bd);
						}
						
						billlist.add(bill);
					}
					initTable(billlist);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(613, 29, 83, 24));
			jButton3.setText("最大化");
			jButton3.setVisible(false);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					java.awt.Dimension dPoint = java.awt.Toolkit
							.getDefaultToolkit().getScreenSize();
					if (DgImgWearDetail.this.getWidth() == dPoint.width) {
						jButton3.setText("最大化");
						DgImgWearDetail.this.setPreferredSize(new Dimension(
								wh[0], wh[1]));
					} else {
						jButton3.setText("最小化");
						DgImgWearDetail.this.setPreferredSize(new Dimension(
								dPoint.width, dPoint.height));
					}

					DgImgWearDetail.this.pack();
					DgImgWearDetail.this.validate();
					Dimension screen = Toolkit.getDefaultToolkit()
							.getScreenSize();
					int x = (screen.width - DgImgWearDetail.this.getWidth()) / 2;
					int y = (screen.height - DgImgWearDetail.this.getHeight()) / 2;
					DgImgWearDetail.this.setLocation(x, y);

				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes rbEms
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEms() {
		if (rbEms == null) {
			rbEms = new JRadioButton();
			rbEms.setBounds(new Rectangle(196, 40, 171, 21));
			rbEms.setText("出口成品来源于报关单");
			rbEms.setSelected(true);
			rbEms.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jLabel2.setText("报关单申报日期");
				}
			});
		}
		return rbEms;
	}

	/**
	 * This method initializes rbBillList
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBillList() {
		if (rbBillList == null) {
			rbBillList = new JRadioButton();
			rbBillList.setBounds(new Rectangle(196, 66, 171, 21));
			rbBillList.setText("出口成品来源于报关清单");
			rbBillList.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jLabel2.setText("报关清单录入日期");
				}
			});
		}
		return rbBillList;
	}

	/**
	 * This method initializes cbbPorjectDept	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbPorjectDept() {
		if (cbbPorjectDept == null) {
			cbbPorjectDept = new JComboBox();
			cbbPorjectDept.setBounds(new Rectangle(443, 40, 123, 21));
		}
		return cbbPorjectDept;
	}
}
