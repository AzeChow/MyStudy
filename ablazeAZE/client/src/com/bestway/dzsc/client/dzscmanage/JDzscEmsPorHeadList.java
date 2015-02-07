/*
 * Created on 2005-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
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
public class JDzscEmsPorHeadList extends JList {

	private javax.swing.JPanel jContentPane = null;

	private DzscAction dzscAction = null;
	/**
	 * This is the default constructor
	 */
	public JDzscEmsPorHeadList() {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
		"dzscAction");
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
				DzscEmsPorHead head = (DzscEmsPorHead) getModel().getElementAt(index);
				head.setSelected(!head.isSelected());
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
			list.addAll(dzscAction.findDzscEmsPorHead(new Request(
					CommonVars.getCurrUser(), true),DzscState.EXECUTE));
		}
		if (isCavContract) {
			list.addAll(dzscAction.findDzscEmsPorHead1(new Request(CommonVars
					.getCurrUser(), true), true));
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
			DzscEmsPorHead head = (DzscEmsPorHead) value;
			setText(head.getIeContractNo() + "/" + head.getEmsNo());
			setSelected(head.isSelected());
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
			DzscEmsPorHead head = (DzscEmsPorHead) this.getModel().getElementAt(i);
			if (head.isSelected()) {
				lsResult.add((DzscEmsPorHead) this.getModel().getElementAt(i));
			}
		}
		return lsResult;
	}
}
