package com.bsw.core.client.eport;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;

import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.google.gson.Gson;

public class DgQPImport extends JDialogBase {
	private JLabel lblNewLabel;
	private JComboBox cbbDecType;
	private JLabel label;
	// private JComboBox cbbUserType;
	private JLabel label_1;
	private JCalendarComboBox ccbBeginDate;
	private JLabel label_2;
	private JCalendarComboBox ccbEndDate;
	private JButton btnOk;
	private JButton btnClose;

	private JScrollPane scrollPane;

	protected BaseEncAction baseEncAction = null;
	/**
	 * 进出标记
	 */
	private Integer impExpFlag;
	private Integer projectType;
	private JList cbbDecTypeList;
	private JLabel lbCustomsDeclarationCode;

	// 报关单号 输入框
	private JTextField tfCustomsDeclarationCode;

	// 根据报关单号导入
	private JCheckBox cbByCustomsDeclarationCodeImport;

	public DgQPImport(Integer impExpFlag, Integer projectType) {
		setBounds(new Rectangle(0, 0, 510, 230));
		baseEncAction = (ContractExeAction) CommonVars.getApplicationContext()
				.getBean("contractExeAction");
		this.impExpFlag = impExpFlag;
		this.projectType = projectType;
		initComponents();
		init();
	}

	private void initComponents() {
		getContentPane().setLayout(null);
		getContentPane().add(getLblNewLabel());

		// 报关单类型选择
		// getContentPane().add(getCbbDecType());

		// getContentPane().add(getLabel());

		// getContentPane().add(getCbbUserType());
		getContentPane().add(getLabel_1());
		getContentPane().add(getCcbBeginDate());
		getContentPane().add(getLabel_2());
		getContentPane().add(getCcbEndDate());
		getContentPane().add(getBtnOk());
		getContentPane().add(getBtnClose());

		getContentPane().add(getScrollPane());
		getContentPane().add(getLbCustomsDeclarationCode());
		getContentPane().add(getTfCustomsDeclarationCode());

		getContentPane().add(getCbByCustomsDeclarationCodeImport());

	}

	private void init() {

		// DefaultComboBoxModel cbmDecType = new DefaultComboBoxModel();
		// DefaultComboBoxModel cbmUserType = new DefaultComboBoxModel();
		//
		// DefaultListModel cbmDecTypeList = new DefaultListModel();

		// cbbDecType.setModel(cbmDecType);
		// cbbUserType.setModel(cbmUserType);

		Vector<CheckableItem> items = new Vector<DgQPImport.CheckableItem>();

		if (ImpExpFlag.IMPORT == impExpFlag) {

			CheckableItem item1 = new CheckableItem("进口报关单");

			CheckableItem item2 = new CheckableItem("进口转关运输提前报关单");

			items.addElement(item1);

			items.addElement(item2);

			// cbmDecType.addElement("进口报关单");
			// cbmDecType.addElement("进口转关运输提前报关单");

			// cbmUserType.addElement("录入或申报单位");
			// cbmUserType.addElement("经营单位");
			// cbmUserType.addElement("收货单位");

		} else {

			// cbmDecType.addElement("出口报关单");
			// cbmDecType.addElement("出口转关运输提前报关单");

			CheckableItem item1 = new CheckableItem("出口报关单");

			CheckableItem item2 = new CheckableItem("出口转关运输提前报关单");

			items.addElement(item1);

			items.addElement(item2);

			// cbmUserType.addElement("录入或申报单位");
			// cbmUserType.addElement("经营单位");
			// cbmUserType.addElement("发货单位");
		}

		cbbDecTypeList.setListData(items);

		// cbbDecType.setSelectedIndex(0);
		// cbbUserType.setSelectedIndex(2);
	}

	private JScrollPane getScrollPane() {

		if (scrollPane == null) {

			scrollPane = new JScrollPane();

			scrollPane.setBounds(91, 54, 230, 65);

			scrollPane.setViewportView(getCbbDecTypeList());

		}

		return scrollPane;

	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("报关单类型");
			lblNewLabel.setBounds(10, 54, 85, 15);
		}
		return lblNewLabel;
	}

	private JComboBox getCbbDecType() {
		if (cbbDecType == null) {
			cbbDecType = new JComboBox();
			cbbDecType.setBounds(91, 23, 149, 21);
		}
		return cbbDecType;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("用户类型");
			label.setBounds(265, 95, 73, 15);
		}
		return label;
	}

	// private JComboBox getCbbUserType() {
	// if (cbbUserType == null) {
	// cbbUserType = new JComboBox();
	// cbbUserType.setBounds(337, 23, 142, 21);
	// }
	// return cbbUserType;
	// }

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("开始日期");
			label_1.setBounds(10, 16, 85, 15);
		}
		return label_1;
	}

	private JCalendarComboBox getCcbBeginDate() {
		if (ccbBeginDate == null) {
			ccbBeginDate = new JCalendarComboBox();
			ccbBeginDate.setBounds(91, 13, 149, 24);
		}
		return ccbBeginDate;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("结束日期");
			label_2.setBounds(265, 17, 73, 15);
		}
		return label_2;
	}

	private JCalendarComboBox getCcbEndDate() {
		if (ccbEndDate == null) {
			ccbEndDate = new JCalendarComboBox();
			ccbEndDate.setBounds(337, 15, 142, 24);
		}
		return ccbEndDate;
	}

	private JButton getBtnOk() {

		if (btnOk == null) {

			btnOk = new JButton("确定");

			btnOk.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					// 检查提交的数据
					if (!checkData()) {

						return;

					}

					try {

						new Thread(new ExecuteImport()).start();

					} catch (Exception ex) {

						ex.printStackTrace();

					}

					dispose();

				}

			});

			btnOk.setBounds(117, 159, 93, 23);

		}

		return btnOk;
	}

	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("取消");
			btnClose.setBounds(245, 159, 93, 23);
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	private List getParams() {

		String[] userTypes = { "录入或申报单位", "经营单位", "收货单位" };

		List params = new ArrayList();

		// 报关单类型
		List decTypes = new ArrayList();

		// 所有报关单类型的 Size
		int typeSize = cbbDecTypeList.getModel().getSize();

		for (int i = 0; i < typeSize; i++) {

			Object obj = cbbDecTypeList.getModel().getElementAt(i);

			if (obj instanceof CheckableItem) {

				CheckableItem item = (CheckableItem) obj;

				// 被选择的 加进去 报关单类型里面
				if (item.isSelected) {
					decTypes.add(item.toString());
				}

			}

		}

		if (ImpExpFlag.EXPORT == impExpFlag) {
			userTypes = new String[] { "录入或申报单位", "经营单位", "发货单位" };
		}

		for (int j = 0; j < decTypes.size(); j++) {

			for (int i = 0; i < userTypes.length; i++) {

				Map<String, Object> param = new HashMap<String, Object>();

				// 如果选择了<报关单号> 导入，那么就不需要加入 <日期> 参数
				if (cbByCustomsDeclarationCodeImport.isSelected()) {

					// 报关单号
					param.put("entryId", tfCustomsDeclarationCode.getText());

				} else {

					param.put("bdate", ccbBeginDate.getDate());

					param.put("edate", ccbEndDate.getDate());

				}

				param.put("impExpFlag", impExpFlag);

				param.put("decType", decTypes.get(j));

				param.put("userType", userTypes[i]);

				// 最后将每一段参数放进去 List里面
				params.add(param);

			}

		}

		return params;

	}

	/**
	 * 获取当前所选取的参数
	 */
	private Map<String, Object> getParam() {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("impExpFlag", impExpFlag);

		/*
		 * isSelectedItem 是被选择的 报关单类型
		 */
		List decTypes = new ArrayList();

		int typeSize = cbbDecTypeList.getModel().getSize();

		for (int i = 0; i < typeSize; i++) {

			Object obj = cbbDecTypeList.getModel().getElementAt(i);

			if (obj instanceof CheckableItem) {

				CheckableItem item = (CheckableItem) obj;

				if (item.isSelected) {

					decTypes.add(item.getName());

				}

			}

		}

		String[] userTypes = { "录入或申报单位", "经营单位", "收货单位" };

		if (ImpExpFlag.IMPORT == impExpFlag) {

			params.put("userType", Arrays.toString(userTypes));

		} else {

			userTypes[2] = "发货单位";

			params.put("userType", Arrays.toString(userTypes));

		}

		params.put("bdate", ccbBeginDate.getDate());

		params.put("edate", ccbEndDate.getDate());

		return params;
	}

	private boolean checkData() {

		List p = getParams();

		System.out.println(p);

		// 检查报关单号是否符合18位
		if (StringUtils.isNotBlank(tfCustomsDeclarationCode.getText())
				&& tfCustomsDeclarationCode.getText().length() != 18) {

			JOptionPane.showMessageDialog(DgQPImport.this, "请输入18位报关单号");

			return false;

		}

		ArrayList decTypes = new ArrayList();

		// 所有报关单类型的 Size
		int typeSize = cbbDecTypeList.getModel().getSize();

		for (int i = 0; i < typeSize; i++) {

			Object obj = cbbDecTypeList.getModel().getElementAt(i);

			if (obj instanceof CheckableItem) {

				CheckableItem item = (CheckableItem) obj;

				// 被选择的 加进去 报关单类型里面
				if (item.isSelected) {

					decTypes.add(item.toString());

				}

			}

		}

		if (decTypes.size() == 0) {

			JOptionPane.showMessageDialog(DgQPImport.this, "请选择报关单类型");

			return false;
		}

		if (ccbBeginDate.getDate() == null) {

			JOptionPane.showMessageDialog(this, "请选择开始日期");

			return false;

		}

		if (ccbEndDate.getDate() == null) {

			JOptionPane.showMessageDialog(this, "请选择结束日期");

			return false;

		}

		return true;

	}

	/**
	 * 执行 导入
	 * 
	 * @author Administrator
	 *
	 */
	public class ExecuteImport implements Runnable {

		@Override
		public void run() {

			CommonProgress.showProgressDialog(DgQPImport.this);

			CommonProgress.setMessage("系统正在导入数据，请稍后...");

			try {

				List errors = new ArrayList();

				// 查询
				List declaration = baseEncAction.findAllCustomsDeclaration(
						CommonVars.getRequst(), projectType, null);

				Set<String> setDeclaration = new HashSet<String>();

				setDeclaration.addAll(declaration);

				// 从QP下载数据
				DownloadDataFromEport dataExchange = DataExchangeFactory
						.getInstance().getDownloadDataFormEport(
								Consts.DEC_DOWNLOAD_MODULE_ID);

				// 测试 连接 QP
				if (!dataExchange.testConnect()) {

					CommonProgress.closeProgressDialog();

					JOptionPane.showMessageDialog(DgQPImport.this,
							"请先安装并打开QP系统!");

					return;
				}

				List params = getParams();

				Map<String, Integer> checkSameCode = new HashMap<String, Integer>();

				for (int j = 0; j < params.size(); j++) {

					// JSON格式数据
					String data = dataExchange
							.download((Map<String, Object>) params.get(j));

					System.out.println("全部数据：" + data);

					if (data == null || "".equals(data.trim())) {
						continue;
					}

					Gson gson = new Gson();

					List list = gson.fromJson(data, List.class);

					for (int i = 0; i < list.size(); i++) {

						Map map = (Map) list.get(i);

						String stringGson = gson.toJson(map);

						System.out.println("单份报关单数据：" + stringGson);

						Map<String, String> bgdhead = (Map<String, String>) map
								.get("报关单表头");

						System.out.println(bgdhead);

						String customsDeclarationCode = bgdhead.get("海关编号");

						// 先判断是否为空
						if (checkSameCode.get(customsDeclarationCode) == null) {

							checkSameCode.put(customsDeclarationCode,
									Integer.valueOf(0));

						} else {

							Integer count = checkSameCode
									.get(customsDeclarationCode);

							count++;

							System.out.println("重复的次数是    :  " + count);

							if (count != Integer.valueOf(0)) {
								continue;
							}

						}

						if (customsDeclarationCode == null) {

							System.out.println("报关单号为空");

						} else if (!setDeclaration
								.contains(customsDeclarationCode)) {

							CommonProgress.setMessage("系统正在导入报关单号为："
									+ customsDeclarationCode + "的报关单,请稍后...");

							try {

								baseEncAction.transferImportDeclaration(
										CommonVars.getRequst(), stringGson,
										impExpFlag, projectType);

								System.out.println("导入成功报关单号："
										+ customsDeclarationCode);

							} catch (Exception e) {
								e.printStackTrace();

								errors.add(new Object[] {
										customsDeclarationCode, e.getMessage() });

							}

						} else {

							if (StringUtils.isNotBlank(customsDeclarationCode)) {

								errors.add(new Object[] {
										customsDeclarationCode, "报关单号已经存在" });

							}
						}
					}

				}

				if (errors.size() > 0) {

					CommonProgress.closeProgressDialog();

					showError(errors);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	public void showError(List list) {
		DgErrorMessage dg = new DgErrorMessage() {
			@Override
			public List<JTableListColumn> getTableColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(new JTableListColumn("报关单号", "", 130));
				list.add(new JTableListColumn("导入失败原因", "", 300));
				return list;
			}
		};
		dg.setTitle("报关单导入错误信息");
		dg.initTable(list);
		dg.setVisible(true);
	}

	private JList getCbbDecTypeList() {

		if (cbbDecTypeList == null) {

			cbbDecTypeList = new JList();

			cbbDecTypeList.setCellRenderer(new InputCheckListRenderer());

			cbbDecTypeList.setFixedCellHeight(18);

			cbbDecTypeList.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					int index = cbbDecTypeList.locationToIndex(e.getPoint());

					Object obj = cbbDecTypeList.getModel().getElementAt(index);

					if (obj instanceof CheckableItem) {

						CheckableItem item = (CheckableItem) obj;

						item.setSelected(!item.isSelected());

						Rectangle rect = cbbDecTypeList.getCellBounds(index,
								index);

						cbbDecTypeList.repaint(rect);

					}

				}

			});

		}

		return cbbDecTypeList;

	}

	/**
	 * 列表框呈现类（此处用于手册/账册号）
	 * 
	 * @author Administrator
	 */
	class InputCheckListRenderer extends JCheckBox implements ListCellRenderer {

		public InputCheckListRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {

			setEnabled(list.isEnabled());

			if (value instanceof CheckableItem) {

				CheckableItem item = (CheckableItem) value;

				setSelected(item.isSelected());

				setSize(350, 17);

				setFont(new Font(getFont().getName(), Font.BOLD, getFont()
						.getSize()));

				setText("  " + item.getName());

			}
			return this;
		}
	}

	/**
	 * 用于列表框中项中的选择
	 */
	class CheckableItem {

		private boolean isSelected;

		private String name;

		public CheckableItem(String name) {
			this.name = name;
			isSelected = true;
		}

		public void setSelected(boolean b) {
			isSelected = b;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	private JLabel getLbCustomsDeclarationCode() {
		if (lbCustomsDeclarationCode == null) {
			lbCustomsDeclarationCode = new JLabel("报关单号");
			lbCustomsDeclarationCode.setBounds(10, 128, 54, 21);
		}
		return lbCustomsDeclarationCode;
	}

	private JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(91, 128, 162, 21);

			tfCustomsDeclarationCode.setEnabled(false);

		}
		return tfCustomsDeclarationCode;
	}

	private JCheckBox getCbByCustomsDeclarationCodeImport() {

		if (cbByCustomsDeclarationCodeImport == null) {

			cbByCustomsDeclarationCodeImport = new JCheckBox();

			cbByCustomsDeclarationCodeImport.setBounds(253, 125, 54, 23);

			cbByCustomsDeclarationCodeImport
					.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							boolean isSelected = cbByCustomsDeclarationCodeImport
									.isSelected();

							tfCustomsDeclarationCode.setEnabled(isSelected);

							ccbBeginDate.setEnabled(!isSelected);

							ccbEndDate.setEnabled(!isSelected);

						}
					});

		}

		return cbByCustomsDeclarationCodeImport;

	}
}
