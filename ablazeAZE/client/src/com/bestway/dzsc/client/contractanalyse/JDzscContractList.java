/*
 * Created on 2005-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JDzscContractList extends JList {

	private javax.swing.JPanel jContentPane = null;

	private DzscAction contractAction = null;

	/**
	 * This is the default constructor
	 */
	public JDzscContractList() {
		super();
		initialize();
		contractAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		// 初始化合同
		// List list = contractAction.findContractByProcessExe(new Request(
		// CommonVars.getCurrUser(), true));
		// this.setListData(list.toArray());
		// this.setCellRenderer(new CheckListCellRenderer());
		this.showContractData(true, false);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index == -1) {
					return;
				}
				DzscEmsPorHead contract = (DzscEmsPorHead) getModel().getElementAt(index);
				contract.setSelected(!contract.isSelected());
				Rectangle rect = getCellBounds(index, index);
				repaint(rect);
			}
		});
	}

	/**
	 * 显示合同信息
	 * 
	 * @param isExingContract
	 *            正在执行的合同
	 * @param isCavContract
	 *            已经核销的合同
	 */
	public void showContractData(boolean isExingContract, boolean isCavContract) {
		List list = new ArrayList();
		if (isExingContract) {
			list.addAll(contractAction.findDzscEmsPorHeadExcu(new Request(
					CommonVars.getCurrUser(), true)));
		}
		if (isCavContract) {
			list.addAll(contractAction.findDzscEmsPorHead(new Request(CommonVars
					.getCurrUser(), true),DzscState.CHECK_CANCEL));
		}
		this.setListData(list.toArray());
		this.setCellRenderer(new CheckListCellRenderer());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
	}

	class CheckListCellRenderer extends JCheckBox implements ListCellRenderer {

		public CheckListCellRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			DzscEmsPorHead contract = (DzscEmsPorHead) value;
			setText(contract.getIeContractNo() + "/" + contract.getEmsNo());
			setSelected(contract.isSelected());
			return this;
		}
	}

	/**
	 * 取得被选中的合同
	 * 
	 * @return
	 */
	public List getSelectedContracts() {
		List<DzscEmsPorHead> lsResult = new ArrayList<DzscEmsPorHead>();
		for (int i = 0; i < this.getModel().getSize(); i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) this.getModel().getElementAt(i);
			if (contract.isSelected()) {
				lsResult.add((DzscEmsPorHead) this.getModel().getElementAt(i));
			}
		}
		return lsResult;
	}
}
