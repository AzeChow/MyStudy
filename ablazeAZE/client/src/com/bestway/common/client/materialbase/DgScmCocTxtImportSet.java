package com.bestway.common.client.materialbase;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCocControl;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * 客户供应商导入栏位设置
 * @author sxk
 *
 */
public class DgScmCocTxtImportSet extends JDialogBase {

	private JPanel pnMain = null;
	private JLabel lbBriefName = null;
	private JComboBox cbbBriefName = null;
	private JLabel lbCountryName = null;
	private JComboBox cbbCountryName = null;
	private JLabel lbPortLinName = null;
	private JComboBox cbbPortLinName = null;
	private JLabel lbCountry2Name = null;
	private JComboBox cbbCountry2Name = null;
	private JLabel lbCustomsName = null;
	private JComboBox cbbCustomsName = null;
	private JLabel lbBelongToCustomsName = null;
	private JComboBox cbbBelongToCustomsName = null;
	private JLabel lbTransfName = null;
	private JComboBox cbbTransfName = null;
	private JLabel lbPredock = null;
	private JComboBox cbbPredock = null;
	private JLabel lbWrapType = null;
	private JComboBox cbbWrapType = null;
	private JLabel lbTradeMode = null;
	private JComboBox cbbTradeMode = null;
	private JButton btnEdit = null;
	private JButton btnSave = null;
	private JButton btnClose = null;
	private int state = DataState.BROWSE; //浏览状态
	private ScmCocControl data = null;
	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;
	public DgScmCocTxtImportSet() {
		super();
		
		//料件管理操作接口
		materialManageAction = (MaterialManageAction) CommonVars.getApplicationContext().getBean(
		"materialManageAction");
		
		initialize();
	}

	/**
	 * 初始化容器
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(506, 283));
        this.setTitle("客户供应商导入栏位设置");
        this.setContentPane(getJContentPane());
        this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {
				initDate();
				setState();
			}
		});
			
	}
	
	/**
	 * 初始化控件数据
	 */
	private void initDate() {
		List list = materialManageAction.findScmCocControl(new Request(
				CommonVars.getCurrUser()));
		if (list.size() == 0) {
			return;
		}
		data = (ScmCocControl) list.get(0);
		cbbBriefName.setSelectedItem(data.getBriefName());
		cbbCountryName.setSelectedItem(data.getCountryName());
		cbbPortLinName.setSelectedItem(data.getPortLinName());
		cbbCountry2Name.setSelectedItem(data.getCountry2Name());
		cbbCustomsName.setSelectedItem(data.getCustomsName());
		cbbBelongToCustomsName.setSelectedItem(data.getBelongToCustomsName());
		cbbTransfName.setSelectedItem(data.getTransfName());
		cbbPredock.setSelectedItem(data.getPredock());
		cbbWrapType.setSelectedItem(data.getWrapType());
		cbbTradeMode.setSelectedItem(data.getTradeMode());
	}
	/**
	 * 控件填充
	 */
	private JPanel getJContentPane() {
		if (pnMain == null) {
			lbTradeMode = new JLabel();
			lbTradeMode.setBounds(new Rectangle(256, 173, 87, 22));
			lbTradeMode.setText("贸  易  方  式");
			lbWrapType = new JLabel();
			lbWrapType.setBounds(new Rectangle(17, 172, 99, 22));
			lbWrapType.setText("包  装  种  类");
			lbPredock = new JLabel();
			lbPredock.setBounds(new Rectangle(256, 137, 86, 22));
			lbPredock.setText("码          头");
			lbTransfName = new JLabel();
			lbTransfName.setBounds(new Rectangle(17, 136, 98, 22));
			lbTransfName.setText("运  输  方  式");
			lbBelongToCustomsName = new JLabel();
			lbBelongToCustomsName.setBounds(new Rectangle(257, 98, 86, 22));
			lbBelongToCustomsName.setText("所  属  海  关");
			lbCustomsName = new JLabel();
			lbCustomsName.setBounds(new Rectangle(16, 97, 99, 22));
			lbCustomsName.setText("出  口  口  岸");
			lbCountry2Name = new JLabel();
			lbCountry2Name.setBounds(new Rectangle(257, 57, 82, 22));
			lbCountry2Name.setText("产    销    国");
			lbPortLinName = new JLabel();
			lbPortLinName.setBounds(new Rectangle(16, 57, 97, 22));
			lbPortLinName.setText("指    运    港");
			lbCountryName = new JLabel();
			lbCountryName.setBounds(new Rectangle(257, 19, 82, 22));
			lbCountryName.setText("运    抵    国");
			lbBriefName = new JLabel();
			lbBriefName.setBounds(new Rectangle(16, 19, 106, 22));
			lbBriefName.setText("关务海关注册公司");
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(lbBriefName, null);
			pnMain.add(getCbbBriefName(), null);
			pnMain.add(lbCountryName, null);
			pnMain.add(getCbbCountryName(), null);
			pnMain.add(lbPortLinName, null);
			pnMain.add(getCbbPortLinName(), null);
			pnMain.add(lbCountry2Name, null);
			pnMain.add(getCbbCountry2Name(), null);
			pnMain.add(lbCustomsName, null);
			pnMain.add(getCbbCustomsName(), null);
			pnMain.add(lbBelongToCustomsName, null);
			pnMain.add(getCbbBelongToCustomsName(), null);
			pnMain.add(lbTransfName, null);
			pnMain.add(getCbbTransfName(), null);
			pnMain.add(lbPredock, null);
			pnMain.add(getCbbPredock(), null);
			pnMain.add(lbWrapType, null);
			pnMain.add(getCbbWrapType(), null);
			pnMain.add(lbTradeMode, null);
			pnMain.add(getCbbTradeMode(), null);
			pnMain.add(getBtnEdit(), null);
			pnMain.add(getBtnSave(), null);
			pnMain.add(getBtnClose(), null);
		}
		return pnMain;
	}

	/**
	 * 栏位状态设置
	 */
	private void setState() {
		cbbBriefName.setEnabled(state == DataState.EDIT);
		cbbCountryName.setEnabled(state == DataState.EDIT);
		cbbPortLinName.setEnabled(state == DataState.EDIT);
		cbbCountry2Name.setEnabled(state == DataState.EDIT);
		cbbCustomsName.setEnabled(state == DataState.EDIT);
		cbbBelongToCustomsName.setEnabled(state == DataState.EDIT);
		cbbTransfName.setEnabled(state == DataState.EDIT);
		cbbPredock.setEnabled(state == DataState.EDIT);
		cbbWrapType.setEnabled(state == DataState.EDIT);
		cbbTradeMode.setEnabled(state == DataState.EDIT);
	}
	/**
	 * 关务海关注册公司	
	 */
	private JComboBox getCbbBriefName() {
		if (cbbBriefName == null) {
			cbbBriefName = new JComboBox();
			cbbBriefName.addItem("");
			cbbBriefName.addItem("代码");
			cbbBriefName.addItem("名称");
			cbbBriefName.setBounds(new Rectangle(120, 19, 100, 22));
		}
		return cbbBriefName;
	}

	/**
	 * 运抵国
	 */
	private JComboBox getCbbCountryName() {
		if (cbbCountryName == null) {
			cbbCountryName = new JComboBox();
			cbbCountryName.addItem("");
			cbbCountryName.addItem("代码");
			cbbCountryName.addItem("名称");
			cbbCountryName.setBounds(new Rectangle(350, 19, 110, 22));
		}
		return cbbCountryName;
	}

	/**
	 * 指运港
	 */
	private JComboBox getCbbPortLinName() {
		if (cbbPortLinName == null) {
			cbbPortLinName = new JComboBox();
			cbbPortLinName.addItem("");
			cbbPortLinName.addItem("代码");
			cbbPortLinName.addItem("名称");
			cbbPortLinName.setBounds(new Rectangle(120, 56, 100, 22));
		}
		return cbbPortLinName;
	}

	/**
	 * 产销国
	 */
	private JComboBox getCbbCountry2Name() {
		if (cbbCountry2Name == null) {
			cbbCountry2Name = new JComboBox();
			cbbCountry2Name.addItem("");
			cbbCountry2Name.addItem("代码");
			cbbCountry2Name.addItem("名称");
			cbbCountry2Name.setBounds(new Rectangle(350, 57, 110, 22));
		}
		return cbbCountry2Name;
	}

	/**
	 * 出口口岸
	 */
	private JComboBox getCbbCustomsName() {
		if (cbbCustomsName == null) {
			cbbCustomsName = new JComboBox();
			cbbCustomsName.addItem("");
			cbbCustomsName.addItem("代码");
			cbbCustomsName.addItem("名称");
			cbbCustomsName.setBounds(new Rectangle(120, 97, 100, 22));
		}
		return cbbCustomsName;
	}

	/**
	 * 所属海关	
	 */
	private JComboBox getCbbBelongToCustomsName() {
		if (cbbBelongToCustomsName == null) {
			cbbBelongToCustomsName = new JComboBox();
			cbbBelongToCustomsName.addItem("");
			cbbBelongToCustomsName.addItem("代码");
			cbbBelongToCustomsName.addItem("名称");
			cbbBelongToCustomsName.setBounds(new Rectangle(350, 98, 110, 22));
		}
		return cbbBelongToCustomsName;
	}

	/**
	 * 运输方式
	 */
	private JComboBox getCbbTransfName() {
		if (cbbTransfName == null) {
			cbbTransfName = new JComboBox();
			cbbTransfName.addItem("");
			cbbTransfName.addItem("代码");
			cbbTransfName.addItem("名称");
			cbbTransfName.setBounds(new Rectangle(120, 136, 100, 22));
		}
		return cbbTransfName;
	}

	/**
	 * 码头
	 */
	private JComboBox getCbbPredock() {
		if (cbbPredock == null) {
			cbbPredock = new JComboBox();
			cbbPredock.addItem("");
			cbbPredock.addItem("代码");
			cbbPredock.addItem("名称");
			cbbPredock.setBounds(new Rectangle(351, 138, 108, 22));
		}
		return cbbPredock;
	}

	/**
	 * 包装种类
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.addItem("");
			cbbWrapType.addItem("代码");
			cbbWrapType.addItem("名称");
			cbbWrapType.setBounds(new Rectangle(120, 172, 101, 22));
		}
		return cbbWrapType;
	}

	/**
	 * 贸易方式	
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.addItem("");
			cbbTradeMode.addItem("代码");
			cbbTradeMode.addItem("名称");
			cbbTradeMode.setBounds(new Rectangle(354, 174, 106, 22));
		}
		return cbbTradeMode;
	}

	/**
	 * 修改按钮
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(72, 209, 86, 25));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					state = DataState.EDIT;
					setState();

				}
			});
		}
		return btnEdit;
	}

	/**
	 * 保存按钮
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(202, 210, 93, 25));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveDate();
					state = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 保存数据
	 */
	private void saveDate(){
		if (data == null) {
			data = new ScmCocControl();
		}
		data
		.setBriefName(cbbBriefName.getSelectedItem() == null ? ""
				: cbbBriefName.getSelectedItem().toString());
		data
		.setCountryName(cbbCountryName.getSelectedItem() == null ? ""
				: cbbCountryName.getSelectedItem().toString());
		data
		.setPortLinName(cbbPortLinName.getSelectedItem() == null ? ""
				: cbbPortLinName.getSelectedItem().toString());
		data
		.setCountry2Name(cbbCountry2Name.getSelectedItem() == null ? ""
				: cbbCountry2Name.getSelectedItem().toString());
		data
		.setCustomsName(cbbCustomsName.getSelectedItem() == null ? ""
				: cbbCustomsName.getSelectedItem().toString());
		data
		.setBelongToCustomsName(cbbBelongToCustomsName.getSelectedItem() == null ? ""
				: cbbBelongToCustomsName.getSelectedItem().toString());
		data
		.setTransfName(cbbTransfName.getSelectedItem() == null ? ""
				: cbbTransfName.getSelectedItem().toString());
		data
		.setPredock(cbbPredock.getSelectedItem() == null ? ""
				: cbbPredock.getSelectedItem().toString());
		data
		.setWrapType(cbbWrapType.getSelectedItem() == null ? ""
				: cbbWrapType.getSelectedItem().toString());
		data
		.setTradeMode(cbbTradeMode.getSelectedItem() == null ? ""
				: cbbTradeMode.getSelectedItem().toString());
		try{
			materialManageAction.saveScmCocControl(new Request(
					CommonVars.getCurrUser()), data);
		}catch(Exception e){
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(
				DgScmCocTxtImportSet.this, "保存完毕！", "提示", 2);
	}
	/**
	 * 退出按钮
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(337, 210, 88, 25));
			btnClose.setText("退出");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgScmCocTxtImportSet.this.dispose();
				}
			});
		}
		return btnClose;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
