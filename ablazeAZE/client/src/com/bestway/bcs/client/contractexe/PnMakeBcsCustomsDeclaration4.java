/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class PnMakeBcsCustomsDeclaration4 extends JPanelBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTableListModel tableModel = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private List commInfoList = null; // @jve:decl-index=0:
	private JButton btnSelectAll = null;
	private JButton btnNotSelectAll = null;
	private ContractAction contractAction = null;
	private ContractExeAction contractExeAction = null;
	private JPanel jPanel = null;
	private JLabel jLabel11 = null;
	private boolean isProduct = false;
	private JComboBox cbbEmsNo = null;
	private int materielProductType = -1;
	private Integer impExpType = null;
	private String tradeCode = null; // @jve:decl-index=0:
	private List selectCommodityList = new ArrayList(); // @jve:decl-index=0:
	private JButton jButton = null;
	CompanyOther other = CommonVars.getOther();

	/**
	 * This is the default constructor
	 */
	public PnMakeBcsCustomsDeclaration4() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		cbbEmsNo.removeAllItems();
		List contracts = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser(), true));
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbEmsNo.addItem((Contract) contracts.get(i));
			}
			this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance()
					.getRender("emsNo", "impContractNo", 100, 150)
					.setForegroundColor(java.awt.Color.red));
		}
		if (this.cbbEmsNo.getItemCount() > 0) {
			this.cbbEmsNo.setSelectedIndex(0);
		}
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			isProduct = this.materielProductType == Integer
					.parseInt(MaterielType.FINISHED_PRODUCT);

			initTable(new ArrayList());
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(630, 248);
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getJPanel(), BorderLayout.NORTH);
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
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.setVisible(false);
			btnSelectAll.setPreferredSize(new Dimension(60, 25));
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setText("不选");
			btnNotSelectAll.setVisible(false);
			btnNotSelectAll.setPreferredSize(new Dimension(60, 25));
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModel == null) {
			return;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list
						.get(i);
				temp.setIsSelected(isSelected);
			}
		}
		tableModel.updateRows(list);
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List selectCommodityList) {

		List list = this.contractExeAction
				.findTempBcsImpExpCommodityInfoByParent(
						new Request(CommonVars.getCurrUser()), getContract(),
						selectCommodityList, isProduct, tradeCode, impExpType);

		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {

			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 50));
				list.add(addColumn("是否超量", "isOutAmount", 60));
				if (isProduct) {
					list.add(addColumn("备案序号", "contractExg.seqNum", 60));
					list.add(addColumn("商品编码", "contractExg.complex.code", 100));
					list.add(addColumn("名称", "contractExg.name", 160));
					list.add(addColumn("规格型号", "contractExg.spec", 160));
					list.add(addColumn("单位", "contractExg.unit.name", 60));
				} else {
					list.add(addColumn("备案序号", "contractImg.seqNum", 60));
					list.add(addColumn("商品编码", "contractImg.complex.code", 100));
					list.add(addColumn("名称", "contractImg.name", 160));
					list.add(addColumn("规格型号", "contractImg.spec", 160));
					list.add(addColumn("单位", "contractImg.unit.name", 60));
				}

				list.add(addColumn("产销国", "impExpCommodityInfo.country.name",
						80));

				list.add(addColumn("折算报关数量", "impExpCommodityInfo.quantity", 80));
				list.add(addColumn("合同当前余量", "converCount", 100));

				list.add(addColumn("单价", "impExpCommodityInfo.unitPrice", 60));
				list.add(addColumn("总金额", "impExpCommodityInfo.amountPrice", 60));
				list.add(addColumn("毛重", "impExpCommodityInfo.grossWeight", 60));
				list.add(addColumn("净重", "impExpCommodityInfo.netWeight", 60));
				list.add(addColumn("件数", "impExpCommodityInfo.piece", 60));

				return list;
			}
		};

		jTableListModelAdapter.setEditableColumn(1);

		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);

		jTable.getColumnModel().getColumn(1)
				.setCellRenderer(new MultiRenderer());

		jTable.getColumnModel().getColumn(1)
				.setCellEditor(new CheckBoxEditor(new JCheckBox()));

		// 是否超量
		jTable.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String stu = "";
						if (value != null
								&& Boolean.parseBoolean(value.toString()) == true) {
							stu = "是";
							this.setForeground(Color.RED);
						} else {
							stu = "否";
							this.setForeground(Color.BLACK);
						}
						this.setValue(stu);
						return this;
					}
				});
		// 折算数量
		jTable.getColumnModel().getColumn(9)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if (other != null) {
								this.setValue(CommonUtils.getDoubleByDigit(
										Double.valueOf(value.toString()),
										other.getCustomAmountNum()));
							} else {
								this.setValue(CommonUtils.getDoubleByDigit(
										Double.valueOf(value.toString()), 5));
							}
						}
						return this;
					}
				});
		jTable.getColumnModel().getColumn(10)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							this.setForeground(Color.RED);
						} else {
							this.setForeground(Color.BLACK);
						}
						return this;
					}
				});
		// 单价
		jTable.getColumnModel().getColumn(11)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if (other != null) {
								this.setValue(CommonUtils.getDoubleByDigit(
										Double.valueOf(value.toString()),
										other.getCustomPriceNum()));
							} else {
								this.setValue(CommonUtils.getDoubleByDigit(
										Double.valueOf(value.toString()), 4));
							}
						}
						return this;
					}
				});
		// 总金额
		jTable.getColumnModel().getColumn(12)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							if (other != null) {
								this.setValue(CommonUtils.getDoubleByDigit(
										Double.valueOf(value.toString()),
										other.getCustomTotalNum()));
							} else {
								this.setValue(CommonUtils.getDoubleByDigit(
										Double.valueOf(value.toString()), 4));
							}
						}
						return this;
					}
				});
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				value = false;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
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
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;
		private JTable table = null;

		/**
		 * 构造CheckBoxEditor内部类
		 * 
		 * @param checkBox
		 */
		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		/**
		 * 编辑方法
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		/**
		 * 获取编辑值
		 */
		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		/**
		 * 触发方法
		 */
		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) obj;
				temp.setIsSelected(temp.getIsSelected() == null ? false : !temp
						.getIsSelected());
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	public List getComInfoList() {

		return selectCommodityList;
	}

	/**
	 * 获取报关商品
	 * 
	 * @return
	 */
	public List getCommodityList() {
		List newList = new ArrayList();
		if (this.tableModel == null) {
			return newList;
		}
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo temp = (TempBcsImpExpCommodityInfo) list
						.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					newList.add(temp);
				}
				// newList.add(temp);
			}
		}
		return newList;
	}

	/**
	 * 验证
	 * 
	 * @return
	 */
	public boolean vaildateData(List selectCommodityList) {
		if (selectCommodityList == null || selectCommodityList.size() <= 0) {
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(this,
					"选择转成报关单的商品信息在【物料与报关对应表】中没有找到“当前使用标志”的报关资料", "提示!",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		List checkList = new ArrayList();
		for (int i = 0; i < selectCommodityList.size(); i++) {
			TempBcsImpExpCommodityInfo t = (TempBcsImpExpCommodityInfo) selectCommodityList
					.get(i);
			if (isProduct == true && t.getContractExg() == null) {
				checkList.add(t);
			} else if (isProduct == false && t.getContractImg() == null) {
				checkList.add(t);
			}
		}
		if (checkList.size() > 0) {
			String tishi = "";
			for (int i = 0; i < checkList.size(); i++) {
				TempBcsImpExpCommodityInfo obj = (TempBcsImpExpCommodityInfo) checkList
						.get(i);
				String ptNo = obj.getImpExpCommodityInfo().getMateriel() == null ? ""
						: obj.getImpExpCommodityInfo().getMateriel().getPtNo();
				String ptName = obj.getImpExpCommodityInfo().getMateriel() == null ? ""
						: obj.getImpExpCommodityInfo().getMateriel()
								.getFactoryName();
				tishi = tishi + ptNo + "         " + ptName + "\n";
			}
			CommonProgress.closeProgressDialog();
			if (JOptionPane
					.showConfirmDialog(
							this,
							"共选中"
									+ selectCommodityList.size()
									+ "条，"
									+ "  未备案共"
									+ checkList.size()
									+ "条"
									+ "以下料号在【物料与报关对应中】没有找到“当前使用标志”的报关资料，或者报关资料在备案资料库没有对应的记录号\n"
									+ tishi + "是否继续？", "确认",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
				return false;
			}
			// JOptionPane.showMessageDialog(this, "共选中"
			// + selectCommodityList.size() + "条，" + "  未备案共"
			// + checkList.size() + "条" + "以下未对应十码或者合同中未找到备案资料库对应的记录号\n"
			// + tishi, "提示", 0);
			// return true;
		}
		return true;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("   请选择合同号");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout.setHgap(1);
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(jLabel11, null);
			jPanel.add(getCbbEmsNo(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnSelectAll(), null);

			jPanel.add(getBtnNotSelectAll(), null);

		}
		return jPanel;
	}

	public List getCommInfoList() {
		return commInfoList;
	}

	public void setCommInfoList(List commInfoList) {
		this.commInfoList = commInfoList;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * 设置进出口类型
	 * 
	 * @param impExpTyp
	 */
	public void setImpExpType(Integer impExpTyp) {
		this.impExpType = impExpTyp;

	}

	/**
	 * 设置贸易方式
	 * 
	 * @param tradeCode
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;

	}

	/**
	 * 得到合同号
	 * 
	 * @return
	 */
	public Contract getContract() {
		return (Contract) cbbEmsNo.getSelectedItem();
	}

	/**
	 * This method initializes cbbEmsNo
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setPreferredSize(new Dimension(200, 27));
			cbbEmsNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commInfoList == null || commInfoList.size() <= 0) {
						return;
					}
					new DoneThread().start();
				}
			});
		}
		return cbbEmsNo;
	}

	class DoneThread extends Thread {

		public void run() {

			CommonProgress.showProgressDialog();

			CommonProgress.setMessage("系统正在生成报关单商品资料，请稍候...");

			try {
				boolean isEms = getContract().getIsContractEms();// 是否是电子化手册

				if (isEms) {

					//检查临时的进出口申请单表体资料
					List list = contractExeAction
							.checkTempBcsImpExpCommodityInfo(new Request(
									CommonVars.getCurrUser()), getContract(),
									mergeCommInfoList());

					String ptNo = (String) list.get(0);

					if (ptNo != null && !"".equals(ptNo.trim())) {

						CommonProgress.closeProgressDialog();

						if (JOptionPane.OK_OPTION != JOptionPane
								.showOptionDialog(
										PnMakeBcsCustomsDeclaration4.this,
										"以下料号在【物料与报关对应中】没有找到,或者报关资料在备案资料库没有对应的记录号\n是否继续？\n"
												+ ptNo, "提示",
										JOptionPane.OK_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										new Object[] { "是", "否" }, "否")) {

							return;

						}

						CommonProgress.showProgressDialog();

					}

					List listTemp = (List) list.get(1);

					List oneToManys = new ArrayList();// 保存一对多对象

					List oneToOnes = new ArrayList();// 保存一对一对象

					List repeatOneToManys = new ArrayList();// 保存一对多中的重复对象

					checkOneToMany(listTemp, oneToManys, oneToOnes,
							repeatOneToManys);

					if (oneToManys.size() > 0) {

						CommonProgress.closeProgressDialog();

						DgBcsImpExpCommodityInfo dg = new DgBcsImpExpCommodityInfo();

						dg.setList(oneToManys);

						dg.setIsProduct(isProduct);

						dg.setVisible(true);

						List selectList = dg.getSelectList();

						oneToOnes.addAll(selectList);

						if (repeatOneToManys.size() > 0) {

							// 将重复的一对多（申请单表体的“国家地区对象”不同）放到一对一中
							addRepeatOneToManys(selectList, repeatOneToManys,
									oneToOnes);
						}

					}

					CommonProgress.showProgressDialog();

					CommonProgress.setMessage("系统正在生成报关单商品资料，请稍候...");

					initTable(oneToOnes);

				} else {

					selectCommodityList = contractExeAction
							.checkExistTempBcsImpExpCommodityInfoBcsTenInnerMergeByParent(
									new Request(CommonVars.getCurrUser()),
									getContract(), commInfoList);

					if (!vaildateData(selectCommodityList)) {

						return;

					}

					initTable(selectCommodityList);

				}

				CommonProgress.closeProgressDialog();

			} catch (Exception ex) {

				CommonProgress.closeProgressDialog();

				JOptionPane.showMessageDialog(null,
						"生成报关单商品资料失败,数据可能没有在合同中备案!", "提示!",
						JOptionPane.INFORMATION_MESSAGE);

				ex.printStackTrace();

				return;
			}
		}
	}

	/**
	 * 将重复的一对多（申请单表体的“国家地区对象”不同）放到一对一中
	 * 
	 * @param selectList
	 *            所勾选的list
	 * @param repeatOneToManys
	 *            重复的一对多
	 * @param oneToOnes
	 *            一对一list
	 */
	private void addRepeatOneToManys(List selectList, List repeatOneToManys,
			List oneToOnes) {
		Map<String, TempBcsImpExpCommodityInfo> seleleMap = new HashMap<String, TempBcsImpExpCommodityInfo>();
		initSelectMap(selectList, seleleMap);

		for (int i = 0; i < repeatOneToManys.size(); i++) {
			TempBcsImpExpCommodityInfo tempInfo = (TempBcsImpExpCommodityInfo) repeatOneToManys
					.get(i);
			Integer tempInfoSeqNo = null;
			String name = null;
			String spec = null;
			// 判断是成品还是料件
			if (isProduct) {
				tempInfoSeqNo = tempInfo.getContractExg().getCredenceNo();// 获取成品归并序号
				spec = tempInfo.getContractExg().getSpec();
				name = tempInfo.getContractExg().getName();
			} else {
				tempInfoSeqNo = tempInfo.getContractImg().getCredenceNo();// 获取料件归并序号
				spec = tempInfo.getContractImg().getSpec();
				name = tempInfo.getContractImg().getName();
			}
			String materielID = tempInfo.getImpExpCommodityInfo().getMateriel()
					.getId();

			if (seleleMap.get(tempInfoSeqNo + "/" + materielID + "/" + spec
					+ "/" + name) != null) {
				oneToOnes.add(tempInfo);
			}
		}
	}

	/**
	 * 合并工厂物料
	 * 
	 * @return
	 */
	public List mergeCommInfoList() {

		List list = new ArrayList();

		for (int i = 0; i < commInfoList.size(); i++) {

			// 旧的临时表体资料
			TempBcsImpExpCommodityInfo oldTemp = (TempBcsImpExpCommodityInfo) commInfoList
					.get(i);

			// 旧的表体信息
			ImpExpCommodityInfo oldInfo = oldTemp.getImpExpCommodityInfo();

			TempBcsImpExpCommodityInfo newTemp = new TempBcsImpExpCommodityInfo();

			ImpExpCommodityInfo newInfo = new ImpExpCommodityInfo();

			try {

				/*
				 * 复制 表体
				 */
				PropertyUtils.copyProperties(newTemp, oldTemp);

				PropertyUtils.copyProperties(newInfo, oldInfo);

				newTemp.setImpExpCommodityInfo(newInfo);

				// 加入到list
				list.add(newTemp);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		Map<String, TempBcsImpExpCommodityInfo> map = new HashMap<String, TempBcsImpExpCommodityInfo>();

		for (int j = 0; j < list.size(); j++) {

			TempBcsImpExpCommodityInfo newTemp = (TempBcsImpExpCommodityInfo) list
					.get(j);

			ImpExpCommodityInfo tempImpExp = newTemp.getImpExpCommodityInfo();

			String countryCode = tempImpExp.getCountry() == null ? ""
					: tempImpExp.getCountry().getCode();

			// 物料ID+国家代码 为 key 不为空就合并
			if (map.get(tempImpExp.getMateriel().getId() + "/" + countryCode) != null) {

				mergeOneToManys(
						map.get(tempImpExp.getMateriel().getId() + "/"
								+ countryCode), newTemp);

				// 为空 那么put
			} else {

				map.put(tempImpExp.getMateriel().getId() + "/" + countryCode,
						newTemp);
			}
		}
		return new ArrayList(map.values());
	}

	/**
	 * 将所勾选的报关资料放入map中
	 * 
	 * @param selectList
	 * @param seleleMap
	 */
	private void initSelectMap(List selectList,
			Map<String, TempBcsImpExpCommodityInfo> seleleMap) {
		for (int i = 0; i < selectList.size(); i++) {
			TempBcsImpExpCommodityInfo tempInfo = (TempBcsImpExpCommodityInfo) selectList
					.get(i);
			Integer tempInfoSeqNo = null;
			String name = null;
			String spec = null;
			// 判断是成品还是料件
			if (isProduct) {
				tempInfoSeqNo = tempInfo.getContractExg().getCredenceNo();// 获取成品归并序号
				spec = tempInfo.getContractExg().getSpec();
				name = tempInfo.getContractExg().getName();
			} else {
				tempInfoSeqNo = tempInfo.getContractImg().getCredenceNo();// 获取料件归并序号
				spec = tempInfo.getContractImg().getSpec();
				name = tempInfo.getContractImg().getName();
			}
			String materielID = tempInfo.getImpExpCommodityInfo().getMateriel()
					.getId();

			seleleMap.put(tempInfoSeqNo + "/" + materielID + "/" + spec + "/"
					+ name, tempInfo);
		}
	}

	/**
	 * 检查一对多
	 */
	private void checkOneToMany(List<TempBcsImpExpCommodityInfo> listSource,
			List oneToManys, List oneToOnes, List repeatOneToOnes) {

		// 先以物料id为key,将数据放到map中
		Map<String, List<TempBcsImpExpCommodityInfo>> map = new HashMap<String, List<TempBcsImpExpCommodityInfo>>();

		initMap(listSource, map);

		List list = new ArrayList(map.values());

		for (int i = 0; i < list.size(); i++) {

			List ls = (List) list.get(i);

			if (ls.size() == 1) {

				// 当size只有一条是
				oneToOnes.addAll(ls);

			} else {

				int count = 1;

				Integer oldSeqNo = ((TempBcsImpExpCommodityInfo) ls.get(0))
						.getSeqNo();

				for (int j = 1; j < ls.size(); j++) {

					TempBcsImpExpCommodityInfo newTemp = (TempBcsImpExpCommodityInfo) ls
							.get(j);

					Integer newSeqNo = newTemp.getSeqNo();

					if (oldSeqNo == newSeqNo) {

						count++;

					} else {
						break;
					}

				}

				if (count == ls.size()) {

					oneToOnes.addAll(ls);
					continue;
				}

				Map<String, TempBcsImpExpCommodityInfo> mapOld = new HashMap<String, TempBcsImpExpCommodityInfo>();

				for (int j = 0; j < ls.size(); j++) {

					TempBcsImpExpCommodityInfo newTemp = (TempBcsImpExpCommodityInfo) ls
							.get(j);

					Integer oldTempSeqNo = null;

					String name = null;

					String spec = null;

					// 判断是成品还是料件
					if (isProduct) {

						oldTempSeqNo = newTemp.getContractExg().getCredenceNo();// 获取成品归并序号

						spec = newTemp.getContractExg().getSpec();

						name = newTemp.getContractExg().getName();

					} else {

						oldTempSeqNo = newTemp.getContractImg().getCredenceNo();// 获取料件归并序号

						spec = newTemp.getContractImg().getSpec();

						name = newTemp.getContractImg().getName();

					}

					if (mapOld.get(oldTempSeqNo + "/" + spec + "/" + name) != null) {

						repeatOneToOnes.add(newTemp);

					} else {

						mapOld.put(oldTempSeqNo + "/" + spec + "/" + name,
								newTemp);

						if (j == 0) {

							newTemp.setIsSelected(true);
						}

						oneToManys.add(newTemp);
					}
				}

			}

		}
	}

	/**
	 * 保存相同序号，相同国家地区对象
	 * 
	 * @param tempInfo
	 * @param temp
	 * @param newImpExp
	 */
	private void mergeOneToManys(TempBcsImpExpCommodityInfo tempInfo,
			TempBcsImpExpCommodityInfo newTemp) {

		ImpExpCommodityInfo newImpExp = newTemp.getImpExpCommodityInfo();

		ImpExpCommodityInfo oldImpExp = tempInfo.getImpExpCommodityInfo();

		String boxNo = oldImpExp.getBoxNo();// 箱号

		String newBoxNo = "";

		if (boxNo != null && !"".equals(boxNo)) {

			newBoxNo = getNotExistBoxNo(boxNo, newImpExp.getBoxNo());

		} else {

			newBoxNo = newImpExp.getBoxNo();
		}

		Double grossWeight = CaleUtil.add(oldImpExp.getGrossWeight(),
				newImpExp.getGrossWeight());

		Double quantity = CaleUtil.add(oldImpExp.getQuantity(),
				newImpExp.getQuantity());

		Double netWeight = CaleUtil.add(oldImpExp.getNetWeight(),
				newImpExp.getNetWeight());

		Double cubage = CaleUtil
				.add(oldImpExp.getBulks(), newImpExp.getBulks());

		Double money = CaleUtil.add(oldImpExp.getAmountPrice(),
				newImpExp.getAmountPrice());

		Integer piece = CommonUtils.getIntegerExceptNull(oldImpExp.getPiece())
				+ CommonUtils.getIntegerExceptNull(newImpExp.getPiece());

		Double workUsd = CaleUtil.add(oldImpExp.getWorkUsd(),
				newImpExp.getWorkUsd());

		oldImpExp.setBoxNo(newBoxNo);// 箱号

		oldImpExp.setWorkUsd(workUsd);// 加工费总价

		oldImpExp.setPiece(piece);// 件数

		oldImpExp.setGrossWeight(grossWeight);// 毛重

		oldImpExp.setNetWeight(netWeight);// 净重

		oldImpExp.setBulks(cubage);// 体积

		oldImpExp.setAmountPrice(money);// 总金额

		oldImpExp.setQuantity(quantity);// 数量

		if (quantity != 0) {

			oldImpExp.setInvgrossWeight(grossWeight / quantity);// 毛重

			oldImpExp.setInvnetWeight(netWeight / quantity);// 单净重

			oldImpExp.setUnitPrice(money / quantity);// 单价

		}

		tempInfo.getCommodityInfos().add(newTemp);
	}

	/**
	 * 初始化map
	 * 
	 * @param list
	 * @param map
	 */
	private void initMap(List<TempBcsImpExpCommodityInfo> list,
			Map<String, List<TempBcsImpExpCommodityInfo>> map) {

		for (int i = 0; i < list.size(); i++) {

			TempBcsImpExpCommodityInfo temp = list.get(i);

			String materielId = temp.getImpExpCommodityInfo().getMateriel()
					.getId();

			if (map.get(materielId) == null) {

				List<TempBcsImpExpCommodityInfo> ls = new ArrayList<TempBcsImpExpCommodityInfo>();

				ls.add(temp);

				map.put(materielId, ls);

			} else {

				map.get(materielId).add(temp);
			}

		}
	}

	/**
	 * 判断箱号是否存在
	 * 
	 * @param allbillNo
	 *            所有的箱号
	 * @param newbillNo
	 *            新箱号
	 * @return 若存在为true 否则为false
	 */
	private String getNotExistBoxNo(String allbillNo, String newbillNo) {
		String newBoxNo = allbillNo;
		if (newbillNo == null || "".equals(newbillNo)) {
			return newBoxNo;
		}
		String[] yy = newbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (allbillNo.contains(yy[i])) {
				continue;
			}
			newBoxNo += "," + yy[i];
		}
		return newBoxNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setPreferredSize(new Dimension(120, 25));
			jButton.setText("合并报关商品");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new DoneThread().start();
				}
			});
		}
		return jButton;
	}
}
