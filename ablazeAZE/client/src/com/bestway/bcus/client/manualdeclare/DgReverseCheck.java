package com.bestway.bcus.client.manualdeclare;

import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.client.common.CheckBoxListItem;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.client.manualdeclare.DgEmsEdiMerger.addImgBefore;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * 反向查询
 * @author sxk
 *
 */
public class DgReverseCheck extends JDialogBase {

	private JPanel pnMainPanel = null;
	private JSplitPane spnMainSp = null;
	private JPanel pnTop = null;
	private JScrollPane spnTable = null;
	private JTable tbMain = null;
	private JLabel lbPtNo = null;
	private JButton btnCheck = null;
	private JPanel pnTopCheck = null;
	private JTextField tfPtNo = null;
	private JButton btnClose = null;
	private JButton jButton = null;
	private JTableListModel tableModel = null;
	private List list = null;
	private Integer seqNum = null; 
	private EmsEdiMergerHead emsEdiMergerHead = null; // 归并关系表头  //  @jve:decl-index=0:
	/**
	 * 声明电子账册方法接口
	 */
	private ManualDeclareAction manualdeclearAction = null;
	
	/**
	 * 普通基础代码操作接口
	 */
	private CommonBaseCodeAction commonBaseCodeAction = null;
	/**
	 * 窗口类构造函数
	 * 
	 */
	public DgReverseCheck() {
		super();
		/**
		 * 获取电子账册方法接口
		 */
		manualdeclearAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
		.getApplicationContext()
		.getBean("commonBaseCodeAction");
		initialize();
	}

	/**
	 * 初始化类
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(710, 420));
        this.setTitle("反向查询");
        this.setContentPane(getPnMainPanel());
		initTable(new Vector());
	}

	/**
	 * 主容器
	 */
	private JPanel getPnMainPanel() {
		if (pnMainPanel == null) {
			pnMainPanel = new JPanel();
			pnMainPanel.setLayout(new BorderLayout());
			pnMainPanel.add(getSpnMainSp(), BorderLayout.CENTER);
		}
		return pnMainPanel;
	}

	/**
	 * 上下分割容器	
	 */
	private JSplitPane getSpnMainSp() {
		if (spnMainSp == null) {
			spnMainSp = new JSplitPane();
			spnMainSp.setOrientation(JSplitPane.VERTICAL_SPLIT);
			spnMainSp.setDividerLocation(80);
			spnMainSp.setTopComponent(getPnTop());
			spnMainSp.setBottomComponent(getSpnTable());
			spnMainSp.setEnabled(false);
		}
		return spnMainSp;
	}

	/**
	 * This method initializes pnTop	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			lbPtNo = new JLabel();
			lbPtNo.setFont(new Font("Dialog", Font.PLAIN, 12));
			lbPtNo.setBounds(new Rectangle(143, 23, 57, 24));
			lbPtNo.setText("料件货号");
			pnTop = new JPanel();
			pnTop.setLayout(null);
			pnTop.add(getPnTopCheck(), null);
		}
		return pnTop;
	}

	/**
	 * This method initializes spnTable	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSpnTable() {
		if (spnTable == null) {
			spnTable = new JScrollPane();
			spnTable.setViewportView(getTbMain());
		}
		return spnTable;
	}

	/**
	 * This method initializes tbMain	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbMain() {
		if (tbMain == null) {
			tbMain = new JTable();
		}
		return tbMain;
	}

	/**
	 * 查询按钮事件
	 */
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setBounds(new Rectangle(381, 23, 91, 24));
			btnCheck.setText("查询");
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 queryData();
				}
			});
		}
		return btnCheck;
	}
	
	/**
	 * 查询
	 */
	public void queryData(){
		if(null==tfPtNo.getText() || "".equals(tfPtNo.getText())){
			JOptionPane.showMessageDialog(
					DgReverseCheck.this, " 请输入查询条件！",
					"提示", 2);
			return;
		}
		
		// 执行查询线程
		new Find().execute();

	}
	
	class Find extends SwingWorker {
		public Find() {
		}

		@Override
		protected Object doInBackground() throws Exception {// 后台计算
			// 查询返回结果
			List list = null;
			// 查询
			CommonProgress.showProgressDialog(DgReverseCheck.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			list  =manualdeclearAction.findEmsEdiMergerExgBefore(new Request(CommonVars
					.getCurrUser()), tfPtNo.getText().trim());
			if(null!=list && list.size() >0){
				
				List<EmsEdiMergerImgBefore> list2 = commonBaseCodeAction
				.findEmsEdiMergerImgBeforeByPtNo(new Request(
						CommonVars.getCurrUser(), true), emsEdiMergerHead,tfPtNo.getText().trim());
				if(null!=list2 && list2.size()>0){
					seqNum = list2.get(0).getSeqNum()==null?0:list2.get(0).getSeqNum();
				}
				initTable(list);
			}else{
				JOptionPane.showMessageDialog(
						DgReverseCheck.this, "没有成品使用该料件！",
						"提示", 2);
				initTable(new Vector());
			}
			
			return list;
		}

		@Override
		protected void done() {// 更新视图
			list = null;
			try {
				list = (List) this.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			initTable(list);
		}
	}
	/**
	 * 初始化TABLE
	 * 
	 * @param table
	 * @param list
	 * @return
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(tbMain, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("成品货号", "emsEdiMergerVersion.emsEdiMergerBefore.ptNo", 100));
				list.add(addColumn("版本号", "emsEdiMergerVersion.version", 80));
				list.add(addColumn("成品备案序号", "emsEdiMergerVersion.emsEdiMergerBefore.seqNum", 80));
				list.add(addColumn("成品名称", "emsEdiMergerVersion.emsEdiMergerBefore.name", 80));
				list.add(addColumn("成品规格", "emsEdiMergerVersion.emsEdiMergerBefore.spec", 80));
				list.add(addColumn("成品单位", "emsEdiMergerVersion.emsEdiMergerBefore.unit.name", 80));
				list.add(addColumn("料件货号", "ptNo", 80));
				list.add(addColumn("料件备案序号", "complex.code", 80));
				list.add(addColumn("料件名称", "name", 80));
				list.add(addColumn("料件规格", "spec", 80));
				list.add(addColumn("料件单位", "unit.name", 80));
				list.add(addColumn("单耗", "unitWaste", 80));
				list.add(addColumn("损耗", "waste", 80));
				list.add(addColumn("修改标记", "emsEdiMergerVersion.emsEdiMergerBefore.modifyMark", 80));
				
				return list;
			}
		});
		tbMain.getColumnModel().getColumn(14).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
						else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});
		tbMain.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						returnValue = seqNum.toString();
						return returnValue;
					}
				});
	}
	/**
	 * This method initializes pnTopCheck	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnTopCheck() {
		if (pnTopCheck == null) {
			pnTopCheck = new JPanel();
			pnTopCheck.setLayout(null);
			pnTopCheck.setBounds(new Rectangle(2, 4, 694, 73));
			pnTopCheck.setBorder(BorderFactory.createTitledBorder(null, "\u6210\u54c1\u67e5\u8be2\u6761\u4ef6", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), SystemColor.activeCaption));
			pnTopCheck.add(getBtnCheck(), new GridBagConstraints());
			pnTopCheck.add(lbPtNo, new GridBagConstraints());
			pnTopCheck.add(getTfPtNo(), null);
			pnTopCheck.add(getBtnClose(), null);
			pnTopCheck.add(getJButton(), null);
		}
		return pnTopCheck;
	}

	/**
	 * This method initializes tfPtNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfPtNo() {
		if (tfPtNo == null) {
			tfPtNo = new JTextField();
			tfPtNo.setBounds(new Rectangle(204, 25, 96, 22));
		}
		return tfPtNo;
	}

	/**
	 * This method initializes btnClose	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(475, 23, 91, 24));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgReverseCheck.this.dispose();

				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(301, 25, 26, 21));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmsEdiMergerImgBefore emsEdiMergerImgBefore = (EmsEdiMergerImgBefore) CommonQuery.getInstance()
							.getEmsEdiMergerImgBefore(true,
									emsEdiMergerHead);
					if (emsEdiMergerImgBefore != null ) {
						tfPtNo.setText(emsEdiMergerImgBefore.getPtNo() == null ? "" : emsEdiMergerImgBefore.getPtNo());
						seqNum = emsEdiMergerImgBefore.getSeqNum() ==null? 0 : emsEdiMergerImgBefore.getSeqNum();
					}
				}
			});
		}
		return jButton;
	}

	public EmsEdiMergerHead getEmsEdiMergerHead() {
		return emsEdiMergerHead;
	}

	public void setEmsEdiMergerHead(EmsEdiMergerHead emsEdiMergerHead) {
		this.emsEdiMergerHead = emsEdiMergerHead;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
