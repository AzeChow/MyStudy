package com.bestway.bcus.client.checkstock.transferanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.apache.commons.lang.BooleanUtils;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Color;

/**
 * @author 
 */
@SuppressWarnings({ "serial", "rawtypes"})
public class FmECSTransferImgBalance extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	
	protected ECSCheckStockAction ecsCheckStockAction = null;
	private PnECSTransferImgPt ptPanel;
	private PnECSTransferImgHs hsPanel;
	
	private JTextField tfEnd;
	private JTextField tfBegin;
	private JTextField tfNump;
	private JTextField tfNumb;
	private JButton btnSelSection;	
	private ECSSection ecsSection;
	private JLabel lbLevel;
	

	public ECSSection getEcsSection() {
		return ecsSection;
	}

	public void setEcsSection(ECSSection ecsSection) {
		this.ecsSection = ecsSection;
	}

	/**
	 * This is the default constructor
	 */
	public FmECSTransferImgBalance() {
		super();		
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		authority.checkTransferImgBalance(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(834, 566);
		this.setContentPane(getJContentPane());
		this.setTitle("料件结转情况表/盘点数据分析");			
	}

	

	public void fillValue() {
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		tfNump.setText(ecsSection.getVerificationNo().toString());
		tfNumb.setText(ecsSection.getCancelOwnerHead().getCancelTimes());
		tfBegin.setText(dFormat.format(ecsSection.getBeginDate()));
		tfEnd.setText(dFormat.format(ecsSection.getEndDate()));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
			jContentPane.add(getHsPanel(),BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 30));
			jLabel3.setText("\u76D8\u70B9\u6838\u67E5\u6279\u6B21\u9009\u62E9");
			jLabel3.setBounds(5, 5, 99, 18);
			jLabel4.setText("起始日期");
			jLabel4.setBounds(385, 5, 52, 18);
			jLabel5.setText("截止日期");
			jLabel5.setBounds(525, 5, 52, 18);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);

			JLabel lblNewLabel = new JLabel("\u6838\u9500\u62A5\u6838\u6B21\u6570");
			lblNewLabel.setBounds(145, 7, 78, 15);
			jPanel2.add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("\u76D8\u70B9\u6838\u67E5\u6B21\u6570");
			lblNewLabel_1.setBounds(265, 7, 78, 15);
			jPanel2.add(lblNewLabel_1);
			jPanel2.add(getTfEnd());
			jPanel2.add(getTfBegin());
			jPanel2.add(getTfNump());
			jPanel2.add(getTfNumb());
			jPanel2.add(getBtnSelSection());
			jPanel2.add(getLbLevel());
		}
		return jPanel2;
	}


	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setBounds(575, 4, 78, 21);
			tfEnd.setColumns(10);
		}
		return tfEnd;
	}

	private JTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JTextField();
			tfBegin.setEditable(false);
			tfBegin.setBounds(440, 4, 78, 21);
			tfBegin.setColumns(10);
		}
		return tfBegin;
	}

	private JTextField getTfNump() {
		if (tfNump == null) {
			tfNump = new JTextField();
			tfNump.setEditable(false);
			tfNump.setBounds(340, 4, 44, 21);
			tfNump.setColumns(10);
		}
		return tfNump;
	}

	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setEditable(false);
			tfNumb.setBounds(220, 4, 44, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}

	private JButton getBtnSelSection() {
		if (btnSelSection == null) {
			btnSelSection = new JButton("...");
			btnSelSection.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if(dgECSSection.isOk()){
						ecsSection = dgECSSection.getSection();
						fillValue();						
						sectionChange();
					}
				}
			});
			btnSelSection.setBounds(104, 3, 37, 23);
		}
		return btnSelSection;
	}
	
	public PnECSTransferImgPt getPtPanel() {
		if(ptPanel == null){
			ptPanel = new PnECSTransferImgPt(this);			
		}
		return ptPanel;
	}
	public PnECSTransferImgHs getHsPanel() {
		if(hsPanel == null){
			hsPanel = new PnECSTransferImgHs(this);
		}			
		return hsPanel;
	}
	/**
	 * 批次改变处理
	 */
	private void sectionChange(){
		boolean isHsImport = BooleanUtils.toBooleanDefaultIfNull(ecsSection.getTransImportIsHs(),true);		
		if(isHsImport){
			lbLevel.setText("【编码级】");
			getPtPanel().setVisible(false);
			getJContentPane().remove(getPtPanel());
			getJContentPane().add(getHsPanel(),BorderLayout.CENTER);
			getHsPanel().setVisible(true);
		}else{
			lbLevel.setText("【料号级】");
			getHsPanel().setVisible(false);
			getJContentPane().remove(getHsPanel());
			getJContentPane().add(getPtPanel(),BorderLayout.CENTER);
			getPtPanel().setVisible(true);
		}
		initTable(Collections.EMPTY_LIST);
	}
	
	public void initTable(List list) {		
		boolean isHsImport = BooleanUtils.toBooleanDefaultIfNull(ecsSection.getTransImportIsHs(),true);
		if(isHsImport){
			getHsPanel().initTable(list);
		}else{
			getPtPanel().initTable(list);
		}
	}	
	
	private JLabel getLbLevel() {
		if (lbLevel == null) {
			lbLevel = new JLabel("【编码级】");
			lbLevel.setForeground(Color.BLUE);
			lbLevel.setBounds(659, 7, 78, 15);
		}
		return lbLevel;
	}
	
	/**
	 * 显示数据
	 * @param section
	 * @param seqNum
	 */
	public void showData(ECSSection section, Integer seqNum) {
		this.ecsSection = section;
		this.fillValue();
		this.sectionChange();		
		boolean isHsImport = BooleanUtils.toBooleanDefaultIfNull(ecsSection.getTransImportIsHs(),true);
		if(isHsImport){
			getHsPanel().showData(section,seqNum);
		}else{
			getPtPanel().showData(section,seqNum);	
		}
		
	}
}
