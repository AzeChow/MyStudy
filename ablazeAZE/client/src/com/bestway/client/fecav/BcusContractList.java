
/*
 * Created on 2005-6-9
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav;

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

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BcusContractList extends JList {

	private javax.swing.JPanel jContentPane = null;

	private FecavAction fecavAction = null;

	/**
	 * This is the default constructor
	 */
	public BcusContractList() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
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
				EmsHeadH2k contract = (EmsHeadH2k) getModel().getElementAt(
						index);
				contract.setIsSelected(!contract.getIsSelected());
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
			list.addAll(fecavAction.findEmsHeadH2kByProcessExe(new Request(
					CommonVars.getCurrUser(), true)));
		}
		// if (isCavContract) {
		// list.addAll(fecavAction.findContract(new Request(CommonVars
		// .getCurrUser(), true), true));
		// }
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
			EmsHeadH2k contract = (EmsHeadH2k) value;
			setText(contract.getEmsNo());
			setSelected(contract.getIsSelected());
			return this;
		}
	}

	/**
	 * 取得被选中的合同
	 * 
	 * @return
	 */
	public List getSelectedContracts() {
		List<EmsHeadH2k> lsResult = new ArrayList<EmsHeadH2k>();
		for (int i = 0; i < this.getModel().getSize(); i++) {
			EmsHeadH2k contract = (EmsHeadH2k) this.getModel().getElementAt(i);
			if (contract.getIsSelected()) {
				lsResult.add((EmsHeadH2k) this.getModel().getElementAt(i));
			}
		}
		return lsResult;
	}
}
