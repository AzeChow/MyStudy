package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.common.Request;
import com.bestway.jptds.common.AppException;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgProgressCustomBaseData extends JDialogBase {

	private JProgressBar progressBar;

	private JPanel contentPanel;

	private JButton startBtn;

	private JTextArea taskOutput;

	private CustomBaseAction customBaseAction;

	// private DataImportAction dataImportAction;
	//
	// private TcsParametersAction tcsParametersAction;

	public DgProgressCustomBaseData(final List fileNames, final String folder) {

		super();

		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");

		customBaseAction.checkBaseCodeHsAuthority(new Request(CommonVars
				.getCurrUser()));

		// dataImportAction = (DataImportAction) CommonVars
		// .getApplicationContext().getBean("dataImportAction");
		//
		// tcsParametersAction = (TcsParametersAction) CommonVars
		// .getApplicationContext().getBean("tcsParametersAction");

		initialize();

		// 启动就更新文件
		SwingWorker<List, Void> worker = new SwingWorker<List, Void>() {

			private String[] infos;

			public String[] getInfos() {
				return infos;
			}

			public void setInfos(String[] infos) {

				firePropertyChange("infos", getInfos(), infos);

				this.infos = infos;
			}

			@Override
			protected List doInBackground() throws Exception {

				// 文件列表长度就是总进度
				int totalProgress = fileNames.size();

				List resultList = new ArrayList();

				for (int i = 0; i < fileNames.size(); i++) {

					// 起始时间
					long startTime = System.currentTimeMillis();

					String fileName = (String) fileNames.get(i);

					// 跳过 这2份压缩文件不处理
					if (fileName.equals("FieldList.zip")
							|| fileName.equals("ClassList.zip")) {

						continue;

					}

					// 上传本地文件到服务器
					localFileAndUploadToServer(fileName, folder);

					CommonProgress.showProgressDialog();

					CommonProgress.setMessage("正在更新 " + fileName + "文件");

					// 更新资料
					String[] returns = customBaseAction.upgradeCustomsBase(
							new Request(CommonVars.getCurrUser()), fileName);

					CommonProgress.closeProgressDialog();

					long endTime = System.currentTimeMillis();

					// 总的处理时间
					double totalTime = (endTime - startTime) / 1000.0;

					// 每次执行一次文件更新就进度增加一次
					setProgress((100 / totalProgress) * (i + 1));

					TimeUnit.SECONDS.sleep(1);

					setInfos(returns);

					// 将每次的处理结果 加入到 结果集合
					resultList.add(returns);

				}

				// 如果当执行完毕 结果进度少于100 时候就直接设置为100
				if (getProgress() < 100) {

					setProgress(100);

				}

				return resultList;

			}

			@Override
			protected void done() {

				try {

					List resultList = get();

					boolean isUpdateSuccessful = false;

					if (resultList.size() == 0 || resultList == null) {

						taskOutput.append("没有需要更新的文件 \n");

						isUpdateSuccessful = true;

					} else {

						taskOutput.append("已更新的文件数量是 : " + resultList.size()
								+ " 个. \n");

						isUpdateSuccessful = true;

					}

					if (isUpdateSuccessful) {

						startBtn.setEnabled(true);

						startBtn.setText("完成");

						progressBar.setValue(100);

						JOptionPane.showMessageDialog(
								DgProgressCustomBaseData.this, "更新完成", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					} else {

						JOptionPane.showMessageDialog(
								DgProgressCustomBaseData.this,
								"您的版本已经是最新的版本，无须更新！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (InterruptedException e) {

					e.printStackTrace();

				} catch (ExecutionException e) {

					e.printStackTrace();
				}

				super.done();

			}

		};

		worker.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {

				if ("progress" == event.getPropertyName()) {

					int progress = (Integer) event.getNewValue();

					progressBar.setIndeterminate(false);

					progressBar.setValue(progress);

				}

				if ("infos" == event.getPropertyName()) {

					String[] infos = (String[]) event.getNewValue();

					StringBuffer sbBuffer = new StringBuffer();

					for (int i = 0; i < infos.length; i++) {

						if (i == 3) {

							sbBuffer.append("耗时:  " + infos[i] + "  \n");

						} else {

							sbBuffer.append(infos[i] + " ,  \n");

						}

					}

					taskOutput.append(sbBuffer.toString());

					taskOutput.append("--------------------------- \n");

				}

			}
		});

		worker.execute();

	}

	/**
	 * 初始化 所有 组件
	 */
	private void initialize() {

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		setTitle("更新资料...");

		setContentPane(getContentPanel());

		setSize(550, 300);

		setResizable(false);

		getStartBtn();

		getTaskOutput();

		getProgressBar();

		JPanel panel = new JPanel();

		panel.add(startBtn);

		panel.add(progressBar);

		getContentPane().add(panel, BorderLayout.PAGE_START);

		getContentPane().add(new JScrollPane(taskOutput), BorderLayout.CENTER);

	}

	@Override
	public void setVisible(boolean b) {

		super.setVisible(b);

	}

	private JPanel getContentPanel() {

		if (contentPanel == null) {

			contentPanel = new JPanel();

			contentPanel.setSize(550, 390);

			contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,
					20));

		}

		return contentPanel;
	}

	private JButton getStartBtn() {

		if (startBtn == null) {

			startBtn = new JButton("正在更新...");

			// 开始 更新时不可用
			startBtn.setEnabled(false);

			startBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {

					// 关闭对话框
					DgProgressCustomBaseData.this.dispose();

				}
			});

		}

		return startBtn;
	}

	private JTextArea getTaskOutput() {

		if (taskOutput == null) {

			taskOutput = new JTextArea(8, 35);

			// taskOutput.setMargin(new Insets(5, 5, 5, 5));

			taskOutput.setEditable(false);

		}

		return taskOutput;
	}

	private JProgressBar getProgressBar() {

		if (progressBar == null) {

			progressBar = new JProgressBar(0, 100);

			progressBar.setValue(0);

			progressBar.setStringPainted(true);

		}

		return progressBar;

	}

	/**
	 * 本地文件上传到服务器端的目录下。
	 *
	 * @param zipfileName
	 *            需要上传的压缩文件
	 */
	private void localFileAndUploadToServer(String zipfileName, String folder) {

		// 本地存放压缩文件的物理位置
		File file = getFile(folder + File.separator, zipfileName);

		try {

			// 转换成 字节数组
			byte[] bytes = FileUtils.readFileToByteArray(file);

			// 上传到服务器
			customBaseAction.uploadCustomsBaseFileToServer(bytes, zipfileName);

			// 完成后删除
			FileUtils.forceDelete(file);

		} catch (IOException e) {

			e.printStackTrace();

			JOptionPane.showMessageDialog(DgProgressCustomBaseData.this,
					"文件上传失败");

			dispose();

			throw new AppException("文件不能正常上传...");

		}

	}

	public static File getFile(String... names) {
		if (names == null) {
			throw new NullPointerException("names must not be null");
		}
		File file = null;
		for (String name : names) {
			if (file == null) {
				file = new File(name);
			} else {
				file = new File(file, name);
			}
		}
		return file;
	}

}
