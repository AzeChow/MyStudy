/*
 * Created on 2005-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.selfcheck;

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

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JCommonContractList extends JList {

	private javax.swing.JPanel jContentPane = null;

	private ContractAction contractAction = null;

	/**
	 * This is the default constructor
	 */
	public JCommonContractList() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
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
				Contract contract = (Contract) getModel().getElementAt(index);
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
			list.addAll(contractAction.findContractByProcessExe(new Request(
					CommonVars.getCurrUser(), true)));
		}
		if (isCavContract) {
			list.addAll(contractAction.findContract(new Request(CommonVars
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
			Contract contract = (Contract) value;
			setText(contract.getImpContractNo() + "/" + contract.getEmsNo());
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
		List<Contract> lsResult = new ArrayList<Contract>();
		for (int i = 0; i < this.getModel().getSize(); i++) {
			Contract contract = (Contract) this.getModel().getElementAt(i);
			if (contract.isSelected()) {
				lsResult.add((Contract) this.getModel().getElementAt(i));
			}
		}
		return lsResult;
	}
}
