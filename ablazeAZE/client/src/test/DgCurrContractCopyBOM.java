package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCurrContractCopyBOM extends JDialogBase {

	private JButton sure;

	private JButton cancel;

	private JPanel jContentPane;

	private JTextField number;

	private JLabel numberLabel;

	private String corrEmsNo;

	public String getCorrEmsNo() {
		return corrEmsNo;
	}

	public void setCorrEmsNo(String corrEmsNo) {
		this.corrEmsNo = corrEmsNo;
	}

	public DgCurrContractCopyBOM() {

		initialize();

	}

	private void initialize() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setTitle("本合同转抄");

		setSize(400, 260);

		setContentPane(getJContentPane());

	}

	private JPanel getJContentPane() {

		if (jContentPane == null) {

			jContentPane = new JPanel();

			jContentPane.setLayout(null);

			jContentPane.add(getNumberLabel());

			jContentPane.add(getNumber());

			jContentPane.add(getSure());

			jContentPane.add(getCancel());

		}

		return jContentPane;

	}

	private JTextField getNumber() {

		if (number == null) {

			number = new JTextField();

			number.setColumns(10);

			number.setBounds(205, 64, 75, 21);

		}

		return number;

	}

	private JButton getSure() {

		if (sure == null) {

			sure = new JButton("确定");

			sure.setBounds(77, 138, 75, 25);

			sure.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					String no = checkNumber(number.getText());

					if (no.equals("")) {

						return;

					}

					DgCurrContractCopyBOM.this.setCorrEmsNo(no);

					DgCurrContractCopyBOM.this.dispose();

				}
			});

		}

		return sure;

	}

	private JButton getCancel() {

		if (cancel == null) {

			cancel = new JButton("取消");

			cancel.setBounds(205, 138, 75, 25);

			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					DgCurrContractCopyBOM.this.dispose();

				}
			});

		}

		return cancel;

	}

	private JLabel getNumberLabel() {

		if (numberLabel == null) {

			numberLabel = new JLabel("从备案序号转入：");

			numberLabel.setBounds(77, 58, 118, 32);
		}

		return numberLabel;

	}

	private String checkNumber(String text) {

		if (StringUtils.isNotBlank(text) && StringUtils.isNumeric(text)) {

			return text;

		}

		return "";

	}

}
