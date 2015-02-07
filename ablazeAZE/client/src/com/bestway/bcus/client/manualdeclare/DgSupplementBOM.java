package com.bestway.bcus.client.manualdeclare;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgSupplementBOM extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JLabel lbSerialNo = null;
	private JTextField tfBeginSerialNo = null;
	private JLabel lbSerialTo = null;
	private JTextField tfEndSerialNo = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private ManualDeclareAction manualdeclearAction = null;
	private EmsHeadH2k emsHeadH2k = null;
	private String exgLastDate = null; // @jve:decl-index=0:
	private JLabel lbTime = null;
	private JLabel lbState = null;
	protected JCalendarComboBox cbbEndDate;
	protected JCalendarComboBox cbbBeginDate;
	private JLabel lbTo = null;
	private JCheckBox cbAdd = null;
	private JCheckBox cbUnChange = null;

	private Integer seqNumMin;// 开始序号
	private Integer seqNumMax;// 结束序号
	private Date beginDate;// 开始时间
	private Date endDate;// 结束时间
	private String states;// 状态

	/**
	 * This method initializes
	 * 
	 */
	public DgSupplementBOM() {
		super();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	public DgSupplementBOM(EmsHeadH2k emsHeadH2k2, String exgLastDate2) {
		super();
		this.emsHeadH2k = emsHeadH2k2;
		this.exgLastDate = exgLastDate2;
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(302, 236));
		this.setContentPane(getJPanel());
		this.setTitle("补充BOM");
		this.setVisible(true);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbTo = new JLabel();
			lbTo.setBounds(new Rectangle(170, 19, 12, 18));
			lbTo.setText("\u5230");
			lbState = new JLabel();
			lbState.setBounds(new Rectangle(17, 95, 70, 25));
			lbState.setText(" 状态");
			lbTime = new JLabel();
			lbTime.setBounds(new Rectangle(17, 15, 70, 25));
			lbTime.setText("变更时间");
			lbSerialTo = new JLabel();
			lbSerialTo.setText("到");
			lbSerialTo.setSize(new Dimension(14, 25));
			lbSerialTo.setLocation(new Point(170, 55));
			lbSerialNo = new JLabel();
			lbSerialNo.setText("归并序号从");
			lbSerialNo.setSize(new Dimension(70, 25));
			lbSerialNo.setLocation(new Point(17, 55));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(lbSerialNo, null);
			jPanel.add(getTfBeginSerialNo(), null);
			jPanel.add(lbSerialTo, null);
			jPanel.add(getTfEndSerialNo(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(lbTime, null);
			jPanel.add(lbState, null);
			jPanel.add(new JCalendarComboBox(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(lbTo, null);
			jPanel.add(getCbAdd(), null);
			jPanel.add(getCbUnChange(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfBeginSerialNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBeginSerialNo() {
		if (tfBeginSerialNo == null) {
			tfBeginSerialNo = new JTextField();
			tfBeginSerialNo.setLocation(new Point(86, 55));
			tfBeginSerialNo.setSize(new Dimension(77, 25));
		}
		return tfBeginSerialNo;
	}

	/**
	 * This method initializes tfEndSerialNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEndSerialNo() {
		if (tfEndSerialNo == null) {
			tfEndSerialNo = new JTextField();
			tfEndSerialNo.setLocation(new Point(195, 55));
			tfEndSerialNo.setSize(new Dimension(77, 25));
		}
		return tfEndSerialNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setSize(new Dimension(70, 28));
			jButton.setLocation(new Point(65, 150));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						seqNumMin = CommonUtils.isEmpty(getTfBeginSerialNo()
								.getText()) ? null : Integer
								.parseInt(getTfBeginSerialNo().getText());
						seqNumMax = CommonUtils.isEmpty(getTfEndSerialNo()
								.getText()) ? null : Integer
								.parseInt(getTfEndSerialNo().getText());
						beginDate = cbbBeginDate.getDate();
						endDate = CommonUtils.getEndDate(cbbEndDate.getDate());
						states = (cbAdd.isSelected() ? "3" : "") + ","
								+ (cbUnChange.isSelected() ? "0" : "");
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(DgSupplementBOM.this,
								"归并序号只能是数字！", "提示", 2);
						return;
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					new SupplementBOM().start();
					DgSupplementBOM.this.dispose();

				}
			});
		}
		return jButton;
	}

	class SupplementBOM extends Thread {
		public void run() {
			try {
				CommonStepProgress.showStepProgressDialog();
				CommonStepProgress.setStepMessage("系统正在补充BOM数据，请稍后...");
				if (checkIsSendState()) {
					return;
				}
				manualdeclearAction.importEmsHead2kFromMerger(new Request(CommonVars
						.getCurrUser()), getEmsHeadH2k(), false, seqNumMin,
						seqNumMax, beginDate, endDate, states, CommonVars
								.getEmsFrom());

				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgSupplementBOM.this,
						"补充BOM数据成功！", "提示", 2);
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(DgSupplementBOM.this, "补充BOM失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 判断此账册是否已经发送
	 * 
	 * @return
	 */
	private boolean checkIsSendState() {
		emsHeadH2k = manualdeclearAction.getH2k(new Request(CommonVars
				.getCurrUser()), emsHeadH2k.getId());
		if (DeclareState.WAIT_EAA.equals(emsHeadH2k.getDeclareState())) {
			JOptionPane.showMessageDialog(DgSupplementBOM.this,
					"此账册已经发送，正等待审批，不能执行此动作！");
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.setSize(new Dimension(70, 28));
			jButton1.setLocation(new Point(152, 150));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplementBOM.this.dispose();
				}
			});
		}
		return jButton1;
	}

	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public String getExgLastDate() {
		return exgLastDate;
	}

	public void setExgLastDate(String exgLastDate) {
		this.exgLastDate = exgLastDate;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(195, 15, 77, 25));
		}
		return cbbEndDate;
	}

	@SuppressWarnings("deprecation")
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(86, 15, 77, 25));
			Date date = cbbBeginDate.getDate();
			date.setDate(1);
			date.setHours(0);
			cbbBeginDate.setDate(date);

		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbAdd
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAdd() {
		if (cbAdd == null) {
			cbAdd = new JCheckBox();
			cbAdd.setBounds(new Rectangle(100, 96, 57, 21));
			cbAdd.setText("新增");
		}
		return cbAdd;
	}

	/**
	 * This method initializes cbUnChange
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUnChange() {
		if (cbUnChange == null) {
			cbUnChange = new JCheckBox();
			cbUnChange.setBounds(new Rectangle(194, 96, 63, 21));
			cbUnChange.setText("未修改");
		}
		return cbUnChange;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
