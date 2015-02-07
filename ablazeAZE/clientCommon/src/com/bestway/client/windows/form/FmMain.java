/*
 * Created on 2004-6-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.windows.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.windows.control.GlobleKeyEventPostProcessor;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.docs.DgExplain;
import com.bestway.ui.winuicontrol.JFrameBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.JMDIMenuBar;
import com.bestway.ui.winuicontrol.JNaviBar;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class FmMain extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	// private JComboBox cbHideForm = null;
	private javax.swing.JLabel jLabel = null;

	private JNaviBar naviBar = null;

	private JScrollPane jScrollPane = null;

	private JTree jTree = null;

	private JDesktopPane deskPanel = null;

	private JMDIMenuBar jJMenuBar = null;

	private JMenu mSys = null;

	private JMenuItem miLogout = null;

	private JMenuItem miExit = null;

	private JMenu mFacade = null;

	private JMenu mHelp = null;

	private JMenuItem miDailog = null;

	private JMenuItem miAbout = null;

	private JRadioButtonMenuItem miJava = null;

	private JRadioButtonMenuItem miWindows = null;

	private JRadioButtonMenuItem mishow = null;

	private ButtonGroup buttonGroup = null;

	// private JPanel navigationPanel = null;

	private JPanel jPanel1 = null;

	private static FmMain fmMain = null;

	// private JButton btnCloseWindows = null;

	private JMenu mThemes = null;

	private JMenu mFont = null;

	private JRadioButtonMenuItem rbmFontPlain = null;

	private JRadioButtonMenuItem rmbFontBold = null;

	private JRadioButtonMenuItem rbmOcean = null;

	private JRadioButtonMenuItem rbmSteel = null;

	private JRadioButtonMenuItem rbmAqua = null;

	private JRadioButtonMenuItem rbmContrast = null;

	private JRadioButtonMenuItem rbmEmerald = null;

	private javax.swing.JLabel lbOtherInfo1 = null;

	/**
	 * 用户自定义的注销事件处理程序
	 */
	private List<LogoutActionListener> logoutActionListenerList = new ArrayList<LogoutActionListener>(); // @jve:decl-index=0:
	/**
	 * 后台运行线程
	 */
	// private List<javax.swing.Timer> backgroundProcessList = new
	// ArrayList<javax.swing.Timer>();
	private List<Thread> backgroundProcessList = new ArrayList<Thread>();
	private JMenu mOther = null;

	private Map<String, Icon> iconMap = new HashMap<String, Icon>(); // @jve:decl-index=0:

	private boolean isOverFlow = false;

	private int totalWidth = 0;

	private int menuBarWidth = 0;

	private JPanel jPanel2 = null;

	private List hasModules = null;

	private JMenuItem miRegister = null;

	private JMenu jMenu = null;

	private boolean showNav = true;

	private boolean isMenuNavigation = false;

	private int dividerSize = 6;

	private JMenuItem miUpgrade = null;

	private Upgrade upgrade = null;

	private LoadPlugin loadPlugin = null; // @jve:decl-index=0:

	private JMenuItem miBug = null;

	private ThemeListener themeListener = null; // @jve:decl-index=0:

	private NavigationListener navigationListener = null; // @jve:decl-index=0:

	private ActionListener setupJAVAIMEListener = null;

	private JMenuItem jbcusHelp = null;

	private ActionListener jbcusHelpListener = null;

	private List<Window> listOpenedWindow = new ArrayList<Window>(); // @jve:decl-index=0:

	private int oldwidth = 0;

	private String clientModeleFileName = "ClientModuleTree.xml";

	private int oldheight = 0;

	public String getClientModeleFileName() {
		return clientModeleFileName;
	}

	public void setClientModeleFileName(String clientModeleFileName) {
		this.clientModeleFileName = clientModeleFileName;
	}

	/**
	 * This is the default constructor
	 */
	private FmMain() {

		super();

		initialize();

		this.setIconImage((new ImageIcon(getClass().getResource(
				"/com/bestway/client/resource/images/bestway.gif"))).getImage());

		UIManager.put("swing.boldMetal", Boolean.FALSE);

		setComponentStyle();

		setExtendedState(Frame.MAXIMIZED_BOTH);
		// MemoryTimer timer = new MemoryTimer(this.lbOtherInfo1);
		// timer.start();

		setApplicationContext();

		setGlobleKeyListener();// 设置全局键盘
	}

	public static FmMain getInstance() {
		if (fmMain == null) {
			fmMain = new FmMain();
		}
		return fmMain;
	}

	@SuppressWarnings("unchecked")
	private FmMain(List hasModules, int style, boolean isMenuNavigation) {

		super();

		initialize();

		setIconImage((new ImageIcon(getClass().getResource(
				"/com/bestway/client/resource/images/bestway.gif"))).getImage());

		this.hasModules = hasModules;

		this.isMenuNavigation = isMenuNavigation;

		UIManager.put("swing.boldMetal", Boolean.FALSE);

		FormThemeControl.setTheme(style);

		setComponentStyle();

		setExtendedState(Frame.MAXIMIZED_BOTH);

		// MemoryTimer timer = new MemoryTimer(this.lbOtherInfo1);

		// timer.start();

		setApplicationContext();

		setThemeState(style);

		setGlobleKeyListener();// 设置全局键盘
	}

	@SuppressWarnings("unchecked")
	public static FmMain getInstance(List hasModules, int style,
			boolean isMenuNavigation) {
		fmMain = new FmMain(hasModules, style, isMenuNavigation);
		return fmMain;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setJMenuBar(getJJMenuBar());

		setTitle(CommonVariables.mainTitle);

		setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);

		setContentPane(getJContentPane());

		setSize(490, 373);

		// addWindowListener(new java.awt.event.WindowAdapter() {
		//
		// public void windowClosing(java.awt.event.WindowEvent e) {
		// // int a = JOptionPane.showConfirmDialog(fmMain, "您确定要关闭整个程序吗？",
		// // "温馨提示",
		// // JOptionPane.YES_NO_OPTION);
		// // if (a == 0) {
		// // fmMain.dispose(); // 关闭
		// // }
		// }
		// });
	}

	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			initSysFunctionUIComponents();
			JFrameBaseHelper.maximize(this);
			refreshNaviBar();
		}
	}

	private void loadPlugin() {
		if (loadPlugin != null) {
			if (isMenuNavigation) {
				loadPlugin.load(jJMenuBar, mOther, isOverFlow, totalWidth,
						menuBarWidth, new MenuActionListener());
			} else {
				loadPlugin.load(jTree);
			}
		}
	}

	public void refreshNaviBar() {
		if (naviBar != null) {
			jPanel1.setVisible(naviBar.getActiveFormCount() > 0);
		}
		if (naviBar != null) {
			lbOther.setVisible(naviBar.getInactiveFormCount() > 0);
			lbOther.setText("▼(" + naviBar.getInactiveFormCount() + ")");
		}
	}

	/**
	 * 显示其它辅助信息
	 * 
	 * @param otherInfo
	 */
	public void setStateInfo1(String otherInfo) {
		this.lbOtherInfo1.setText(otherInfo);
	}

	/**
	 * 显示其它辅助信息
	 * 
	 * @param otherInfo
	 */
	public void setStateInfo2(String otherInfo) {
		this.lbOtherInfo2.setText(otherInfo);
	}

	/**
	 * 增加后台处理线程
	 * 
	 * @param backgroundProcess
	 */
	public void addBackgroundProcess(Thread backgroundProcess) {
		if (backgroundProcess != null) {
			this.backgroundProcessList.add(backgroundProcess);
		}
	}

	/**
	 * 启动后台处理线程
	 */
	public void startBackgroundProcess() {
		for (Thread backgroundProcess : this.backgroundProcessList) {
			if (!backgroundProcess.isAlive()) {
				backgroundProcess.start();
			}
		}
	}

	/**
	 * 停止后台处理线程
	 */
	public void stopBackgroundProcess() {
		for (Thread backgroundProcess : this.backgroundProcessList) {
			backgroundProcess.interrupt();
		}
	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJContentPane() {

		if (jContentPane == null) {

			jContentPane = new JPanel();

			jContentPane.setLayout(new BorderLayout());

			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);// getJToolBar()

			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);

			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);

		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {

		if (jSplitPane == null) {

			jSplitPane = new JSplitPane();

			// jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerSize(dividerSize);

			jSplitPane.setLeftComponent(getJScrollPane());

			jSplitPane.setRightComponent(getDeskPanel());

			jSplitPane.setDividerLocation(150);

			jSplitPane
					.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

						public void propertyChange(
								java.beans.PropertyChangeEvent e) {

							if ((e.getPropertyName()
									.equals("lastDividerLocation"))) {

								jLabel.setPreferredSize(new Dimension(
										jSplitPane.getDividerLocation(), 22));

								// System.out.println("propertyChange(lastDividerLocation)");
								// //
								// "lastDividerLocation"

								naviBar.adjustNaviBar();

								refreshNaviBar();

								jPanel1.revalidate();

							}
						}
					});
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), BorderLayout.WEST);
			jPanel.add(getPnWarning(), BorderLayout.EAST);
			jPanel.add(getJPanel4(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes cbHideForm
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	public JNaviBar getNaviBar() {
		if (naviBar == null) {
			naviBar = new JNaviBar();
		}
		return naviBar;
	}

	// /**
	// *
	// * This method initializes cbHideForm
	// *
	// *
	// *
	// * @return javax.swing.JComboBox
	// *
	// */
	// private JComboBox getCbHideForm() {
	// if (cbHideForm == null) {
	// cbHideForm = new JComboBox();
	// cbHideForm.setVisible(false);
	// cbHideForm.addItemListener(new java.awt.event.ItemListener() {
	//
	// public void itemStateChanged(java.awt.event.ItemEvent e) {
	//
	// if (e.getStateChange() == 1 && e.getItem() != null) {
	// showHideForm((Component) e.getItem());
	// }
	// }
	// });
	// cbHideForm.setRenderer(new DefaultListCellRenderer() {
	// public Component getListCellRendererComponent(JList list,
	// Object value, int index, boolean isSelected,
	// boolean cellHasFocus) {
	// Component retValue = super.getListCellRendererComponent(
	// list, value, index, isSelected, cellHasFocus);
	// if (value != null) {
	// if (value instanceof JInternalFrame) {
	// setText(((JInternalFrame) value).getTitle());
	// } else if (value instanceof JInternalFrame.JDesktopIcon) {
	// setText(((JInternalFrame.JDesktopIcon) value)
	// .getInternalFrame().getTitle());
	// }
	// }
	// return retValue;
	// }
	// });
	// }
	// return cbHideForm;
	// }

	// private void showHideForm(Component form) {
	// if (form instanceof JInternalFrame) {
	// JInternalFrame formb = (JInternalFrame) form;
	// reopenForm(formb);
	// } else if (form instanceof JInternalFrame.JDesktopIcon) {
	// JInternalFrame formc = (JInternalFrame) ((JInternalFrame.JDesktopIcon)
	// form)
	// .getInternalFrame();
	//
	// deskPanel.getDesktopManager().deiconifyFrame(formc);
	// reopenForm(formc);
	// }
	// }

	// public class NaviButtonActionReopenForm{
	// public void reopenForm(JInternalFrame jf){
	// if(jf!=null){
	// reopenForm(jf);
	// }
	// }
	// }

	/**
	 * @param formc
	 */
	public void reopenForm(JInternalFrame form) {
		ShowFormControl.reshowForm(form, deskPanel);
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}

	private void removeParentNodeHasNotChild(ModuleTreeNode node, List list) {
		if (node == null) {
			return;
		}
		int childCount = node.getChildCount();
		if (childCount <= 0) {
			if (!list.contains(getNameExceptPackage(node.getTargetForm()))) {
				ModuleTreeNode parentNode = (ModuleTreeNode) node.getParent();
				node.removeFromParent();
				removeParentNodeHasNotChild(parentNode, list);
			}
		} else {
			for (int i = node.getChildCount() - 1; i >= 0; i--) {
				if (i <= node.getChildCount() - 1) {
					ModuleTreeNode childNode = (ModuleTreeNode) node
							.getChildAt(i);
					if (childNode != null) {
						removeParentNodeHasNotChild(childNode, list);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List getNameExceptPackage(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				continue;
			}
			String moduleName = list.get(i).toString();
			String name = getNameExceptPackage(moduleName);
			lsResult.add(name);
			System.out.println("---" + name);
		}
		return lsResult;
	}

	private String getNameExceptPackage(String moduleName) {
		if (moduleName == null) {
			return "";
		}
		String name = "";
		int lastIndex = moduleName.lastIndexOf(".");
		if (lastIndex >= 0 && lastIndex < moduleName.length() - 1) {
			name = moduleName.substring(lastIndex + 1);
		}
		return name;
	}

	public Icon getIconByName(String iconName) {
		if (iconName == null || "".equals(iconName.trim())) {
			return null;
		}
		Icon icon = iconMap.get(iconName);
		if (icon == null) {
			icon = new ImageIcon(this.getClass().getResource(iconName));
			iconMap.put(iconName, icon);
		}
		return icon;
	}

	/**
	 * 初始化 JTree
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getJTree() {

		if (jTree == null) {

			jTree = new JTree();

			jTree.setCursor(new Cursor(Cursor.HAND_CURSOR));

			jTree.addMouseListener(new java.awt.event.MouseAdapter() {

				// 鼠标点击事件
				public void mouseClicked(java.awt.event.MouseEvent e) {

					JTree tree = (JTree) e.getSource();

					tree.setCursor(new Cursor(Cursor.WAIT_CURSOR));

					if (tree.getRowForLocation(e.getX(), e.getY()) == -1) {

						tree.setCursor(new Cursor(Cursor.HAND_CURSOR));

						return;
					}

					TreePath curPath = tree.getPathForLocation(e.getX(),
							e.getY());

					String targetForm = ((ModuleTreeNode) curPath
							.getLastPathComponent()).getTargetForm();

					String isshow = ((ModuleTreeNode) curPath
							.getLastPathComponent()).getIsshow();

					String caption = ((ModuleTreeNode) curPath
							.getLastPathComponent()).getCaption();

					String iconName = ((ModuleTreeNode) curPath
							.getLastPathComponent()).getIconName();

					Map<String, Object> parameters = ((ModuleTreeNode) curPath
							.getLastPathComponent()).getParameters();

					try {
						showTargetForm(isshow, targetForm, caption,
								getIconByName(iconName), parameters);

					} finally {
						tree.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
				}
			});

			ToolTipManager.sharedInstance().registerComponent(jTree);

			jTree.setCellRenderer(new DefaultTreeCellRenderer() {

				private static final long serialVersionUID = 1L;

				public Component getTreeCellRendererComponent(JTree tree,
						Object value, boolean sel, boolean expanded,
						boolean leaf, int row, boolean hasFocus) {

					String iconName = null;

					if (value instanceof ModuleTreeNode) {

						ModuleTreeNode treeNode = (ModuleTreeNode) value;

						iconName = treeNode.getIconName();

					}

					if (iconName != null && !"".equals(iconName.trim())) {

						Icon icon = getIconByName(iconName);

						setLeafIcon(icon);

						setClosedIcon(icon);

						setOpenIcon(icon);

					} else {
						setLeafIcon(UIManager.getIcon("Tree.leafIcon"));
						setClosedIcon(UIManager.getIcon("Tree.closedIcon"));
						setOpenIcon(UIManager.getIcon("Tree.openIcon"));
					}

					setToolTipText(value.toString());

					return super.getTreeCellRendererComponent(tree, value, sel,
							expanded, leaf, row, hasFocus);
				}
			});
		}
		return jTree;
	}

	private void initSysFunctionUIComponents() {
		if (isMenuNavigation) {
			this.createSysMenu();
		} else {
			this.createTreeModel();
		}
		loadPlugin();
	}

	private Element getRootElement() {
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			doc = sb.build(this.getClass().getClassLoader()
					.getResourceAsStream(clientModeleFileName));
			return doc.getRootElement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void createSysMenu() {
		jTree.setModel(null);
		this.jScrollPane.setVisible(false);
		mOther = new JMenu(">>>");
		isOverFlow = false;
		mSys.add(getMFacade());
		mSys.add(getMThemes());
		mSys.add(getJMenu());
		mSys.add(getMJAVAIME());
		mSys.add(getMNavigation());
		mSys.add(getMHelp());
		mSys.add(getMiLogout());
		mSys.add(getMiExit());

		totalWidth = mSys.getPreferredSize().width;
		menuBarWidth = Toolkit.getDefaultToolkit().getScreenSize().width
				- mOther.getPreferredSize().width;// -100
		// System.out.println("---------------menuBarWidth:" + menuBarWidth);

		Element root = this.getRootElement();
		if (root == null) {
			return;
		}
		List list = root.getChildren("link");
		if (list == null || list.size() == 0) {
			return;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Element element = (Element) list.get(i);
			String moduleId = "";
			if (element.getAttribute("id") != null) {
				moduleId = element.getAttribute("id").getValue();
			}
			String targetForm = "";
			if (element.getAttribute("targetForm") != null
					&& element.getAttribute("targetForm").getValue() != null) {
				targetForm = element.getAttribute("targetForm").getValue()
						.toString().trim();
			}
			String caption = "";
			if (element.getAttribute("caption") != null
					&& element.getAttribute("caption").getValue() != null) {
				caption = element.getAttribute("caption").getValue().toString()
						.trim();
			}
			String strShow = "";
			if (element.getAttribute("isshow") != null) {
				strShow = element.getAttribute("isshow").getValue();
			}
			Map<String, Object> mapParameters = this
					.getFormParameterFromXml(root);
			if (hasModules != null && hasModules.size() > 0) {
				if (!hasModules.contains(moduleId)) {
					continue;
				}
			}
			if (totalWidth >= menuBarWidth) {
				JModuleMenu item = new JModuleMenu(moduleId, caption, strShow,
						targetForm, mapParameters);
				if (element.getAttribute("iconName") != null) {
					String iconName = element.getAttribute("iconName")
							.getValue();
					item.setIconName(iconName);
					item.setIcon(this.getIconByName(iconName));
				}
				item.addActionListener(new MenuActionListener());
				if (!isOverFlow) {
					jJMenuBar.add(mOther);
					isOverFlow = true;
				}
				mOther.add(item);
				totalWidth += item.getPreferredSize().width;
				createMenu(element, item);
			} else {
				JModuleMenu item = new JModuleMenu(moduleId, caption, strShow,
						targetForm, mapParameters);
				item.addActionListener(new MenuActionListener());
				if (element.getAttribute("iconName") != null) {
					String iconName = element.getAttribute("iconName")
							.getValue();
					item.setIconName(iconName);
					item.setIcon(this.getIconByName(iconName));
				}
				jJMenuBar.add(item);
				totalWidth += item.getPreferredSize().width;
				createMenu(element, item);
			}
		}

		jJMenuBar.refreshMenuBar();
		jSplitPane.setDividerSize(0);
		jSplitPane.setDividerLocation(0);
	}

	public class MenuActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Object object = e.getSource();
			if (object instanceof JModuleMenu) {
				JModuleMenu menu = (JModuleMenu) object;
				String targetForm = menu.getTargetForm();
				String isshow = menu.getIsshow();
				String caption = menu.getText();
				Map<String, Object> parameters = menu.getParameters();
				FmMain.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				try {
					showTargetForm(isshow, targetForm, caption, menu.getIcon(),
							parameters);
				} finally {

					setCursor(new Cursor(Cursor.HAND_CURSOR));

					// FmMain.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			} else if (object instanceof JModuleMenuItem) {
				JModuleMenuItem menuItem = (JModuleMenuItem) object;
				String targetForm = menuItem.getTargetForm();
				String isshow = menuItem.getIsshow();
				String caption = menuItem.getText();
				Map<String, Object> parameters = menuItem.getParameters();
				FmMain.this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				try {
					showTargetForm(isshow, targetForm, caption,
							menuItem.getIcon(), parameters);
				} finally {
					FmMain.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}
		}

	}

	private void createMenu(Element xmlNode, JMenuItem menu) {
		List list = xmlNode.getChildren("link");
		if (list == null || list.size() == 0) {
			return;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Element element = (Element) list.get(i);
			String moduleId = "";
			if (element.getAttribute("id") != null) {
				moduleId = element.getAttribute("id").getValue();
			}
			String targetForm = "";
			if (element.getAttribute("targetForm") != null
					&& element.getAttribute("targetForm").getValue() != null) {
				targetForm = element.getAttribute("targetForm").getValue()
						.toString().trim();
			}
			String caption = "";
			if (element.getAttribute("caption") != null
					&& element.getAttribute("caption").getValue() != null) {
				caption = element.getAttribute("caption").getValue().toString()
						.trim();
			}

			String strShow = "";
			if (element.getAttribute("isshow") != null) {
				strShow = element.getAttribute("isshow").getValue();
			}
			Map<String, Object> mapParameters = this
					.getFormParameterFromXml(element);
			if (element.getChildren("link").size() > 0) {
				JModuleMenu item = new JModuleMenu(moduleId, caption, strShow,
						targetForm, mapParameters);
				if (element.getAttribute("iconName") != null) {
					String iconName = element.getAttribute("iconName")
							.getValue();
					item.setIconName(iconName);
					item.setIcon(this.getIconByName(iconName));
				}
				item.addActionListener(new MenuActionListener());
				menu.add(item);
				createMenu(element, item);
			} else {
				JModuleMenuItem item = new JModuleMenuItem(moduleId, caption,
						strShow, targetForm, mapParameters);
				if (element.getAttribute("iconName") != null) {
					String iconName = element.getAttribute("iconName")
							.getValue();
					item.setIconName(iconName);
					item.setIcon(this.getIconByName(iconName));
				}
				item.addActionListener(new MenuActionListener());
				menu.add(item);
			}
		}
	}

	private void createTreeModel() {
		jJMenuBar.removeAll();
		jJMenuBar.add(getMSys());
		jJMenuBar.add(getMFacade());
		jJMenuBar.add(getMThemes());
		jJMenuBar.add(getJMenu());
		jJMenuBar.add(getMJAVAIME());
		jJMenuBar.add(getMNavigation());
		jJMenuBar.add(getMHelp());

		ModuleTreeNode rootNode = null;
		Element root = this.getRootElement();
		String rootShow = "";
		if (root.getAttribute("isshow") != null) {
			rootShow = root.getAttribute("isshow").getValue();
		}
		String moduleId = "";
		if (root.getAttribute("id") != null) {
			moduleId = root.getAttribute("id").getValue();
		}
		String iconName = "";
		if (root.getAttribute("iconName") != null) {
			iconName = root.getAttribute("iconName").getValue();
		}
		Map<String, Object> mapParameters = this.getFormParameterFromXml(root);
		rootNode = new ModuleTreeNode(moduleId, root.getAttribute("caption")
				.getValue(), rootShow, root.getAttribute("targetForm")
				.getValue(), mapParameters);
		if (iconName != null && !"".equals(iconName.trim())) {
			rootNode.setIconName(iconName);
		}
		createTreeNode(root, rootNode);
		if (hasModules != null && hasModules.size() > 0) {
			String module = hasModules.get(0).toString();
			if (module.indexOf("com.bestway") >= 0) {// 旧License的模块表示
				List lsName = getNameExceptPackage(hasModules);
				removeParentNodeHasNotChild(rootNode, lsName);
			} else {
				int childCount = rootNode.getChildCount();
				for (int i = childCount - 1; i >= 0; i--) {
					ModuleTreeNode childNode = (ModuleTreeNode) rootNode
							.getChildAt(i);
					if (!hasModules.contains(childNode.getModuleId())) {
						childNode.removeFromParent();
					}
				}
			}
		}
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		jTree.setModel(model);
		jScrollPane.setVisible(true);
		jSplitPane.setDividerSize(dividerSize);
		jSplitPane.setDividerLocation(150);
	}

	public void replaceMenuInfo(String oldTargetForm, String caption,
			String isshow, String targetForm, Map<String, Object> parameters) {
		ModuleTreeNode mtn = this.getModuleTreeNode(oldTargetForm,
				(ModuleTreeNode) jTree.getModel().getRoot());
		if (caption != null) {
			mtn.setCaption(caption);
			mtn.setUserObject(caption);
		}
		if (targetForm != null)
			mtn.setTargetForm(targetForm);
		if (parameters != null)
			mtn.setParameters(parameters);
	}

	public ModuleTreeNode getModuleTreeNode(String targetForm,
			ModuleTreeNode root) {
		ModuleTreeNode mtn = null;
		ModuleTreeNode target = null;
		for (int i = 0; i < root.getChildCount(); i++) {
			mtn = (ModuleTreeNode) root.getChildAt(i);
			if (mtn.getTargetForm().equals(targetForm)) {
				target = mtn;
				break;
			}
			if (mtn.isLeaf()) {
				break;
			} else {
				this.getModuleTreeNode(targetForm, mtn);
			}
		}

		return target;
	}

	/**
	 * 获取xml中窗口的自定义参数
	 * 
	 * @param element
	 * @return
	 */
	private Map<String, Object> getFormParameterFromXml(Element element) {
		Element parameters = element.getChild("parameters");
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		if (parameters != null) {
			List listParameter = parameters.getChildren("parameter");
			if (listParameter != null && !listParameter.isEmpty()) {
				for (int j = 0; j < listParameter.size(); j++) {
					Element elementParameter = (Element) listParameter.get(j);
					if (elementParameter.getAttribute("key").getValue() != null
							&& !"".equals(elementParameter.getAttribute("key")
									.getValue().trim())) {
						mapParameters.put(elementParameter.getAttribute("key")
								.getValue().trim(), elementParameter
								.getAttribute("value").getValue().trim());
					}
				}
			}
		}
		return mapParameters;
	}

	private Map<String, Object> getFormParameterFromActionCommand(
			String actionCommand) {
		// String actionCommand=button.getActionCommand();
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		if (actionCommand != null && !"".equals(actionCommand.trim())) {
			int index = actionCommand.indexOf("?");
			if (index >= 0) {
				String parameterStr = actionCommand.substring(index + 1);
				String[] parameters = parameterStr.split(";");
				for (int i = 0; i < parameters.length; i++) {
					if (parameters[i] != null) {
						String[] kv = parameters[i].split("=");
						if (kv.length == 2 && kv[0] != null && kv[1] != null) {
							if ("true".equals(kv[1].trim().toLowerCase())
									|| "false".equals(kv[1].trim()
											.toLowerCase())) {
								mapParameters.put(kv[0].trim(),
										Boolean.valueOf(kv[1].trim()));
							} else {
								mapParameters.put(kv[0].trim(), kv[1].trim());
							}
						}
					}
				}
			}
		}
		return mapParameters;
	}

	private void createTreeNode(Element xmlNode, ModuleTreeNode treeNode) {
		List list = xmlNode.getChildren("link");
		if (list == null || list.size() == 0) {
			return;
		}
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Element element = (Element) list.get(i);
			String moduleId = "";
			if (element.getAttribute("id") != null) {
				moduleId = element.getAttribute("id").getValue();
			}
			String targetForm = "";
			if (element.getAttribute("targetForm") != null
					&& element.getAttribute("targetForm").getValue() != null) {
				targetForm = element.getAttribute("targetForm").getValue()
						.toString().trim();
			}
			String caption = "";
			if (element.getAttribute("caption") != null
					&& element.getAttribute("caption").getValue() != null) {
				caption = element.getAttribute("caption").getValue().toString()
						.trim();
			}
			// if (hasModules != null && !targetForm.equals("")
			// && hasModules.contains(targetForm)) {
			String strShow = "";
			if (element.getAttribute("isshow") != null) {
				strShow = element.getAttribute("isshow").getValue();
			}
			String iconName = "";
			if (element.getAttribute("iconName") != null) {
				iconName = element.getAttribute("iconName").getValue();
			}
			Map<String, Object> mapParameters = this
					.getFormParameterFromXml(element);
			ModuleTreeNode node = new ModuleTreeNode(moduleId, caption,
					strShow, targetForm, mapParameters);
			if (iconName != null && !"".equals(iconName.trim())) {
				node.setIconName(iconName);
			}
			treeNode.add(node);
			createTreeNode(element, node);
			// } else {
			// lsParentNodeCaption.add(xmlNode.getAttribute("caption")
			// .getValue());
			// }
		}
	}

	public void showTargetForm(String isshow, String targetForm,
			String caption, Icon icon, Map<String, Object> parameters) {
		if (targetForm.trim().equals("")) {
			return;
		}
		if (isshow.equals("1") && !FmMain.this.showNav) {
			return;
		}
		if (checkFormIsExist(targetForm)) {
			return;
		}

		Class cls = null;

		Object form = null;

		try {

			cls = Class.forName(targetForm);

			form = cls.newInstance();

			if (parameters != null && !parameters.isEmpty()) {

				Iterator iterator = parameters.keySet().iterator();

				while (iterator.hasNext()) {

					String key = iterator.next().toString().trim();

					Object value = parameters.get(key);

					System.out.println("---key:" + key + " value:" + value);

					PropertyUtils.setSimpleProperty(form, key, value);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		if (form instanceof JInternalFrame) {

			((JInternalFrameBase) form).setTitle(caption);

			if (icon != null) {
				((JInternalFrameBase) form).setFrameIcon(icon);
			}
			ShowFormControl.showForm((JInternalFrame) form, deskPanel,
					jJMenuBar, naviBar);
		} else if (form instanceof JPanel) {
			bindEnventAdapterToButton((JPanel) form);
			JInternalFrameBase flowFrame = new JInternalFrameBase();
			flowFrame.setContentPane((JPanel) form);
			flowFrame.setTitle(caption + "向导");
			flowFrame.setFlowPaneClassName(form.getClass().getName());
			if (icon != null) {
				flowFrame.setFrameIcon(icon);
			}
			ShowFormControl.showForm(flowFrame, deskPanel, jJMenuBar, naviBar);
			// if (navigationPanel != null) {
			// deskPanel.remove(navigationPanel);
			// navigationPanel = null;
			// }
			// navigationPanel = ShowPanelControl.showDesktopPanel((JPanel)
			// form,
			// deskPanel);
			// navigationPanel.setBorder(BorderFactory.createLoweredBevelBorder());
			// navigationPanel.setBackground(Color.white);
			// bindEnventAdapterToButton(navigationPanel);
			// hideInternalForm();
		} else if (form instanceof JDialog) {
			((JDialog) form).setVisible(true);
		}
	}

	/**
	 * ��container�ڵİ�ť����¼������¼�����ʱ��ִ��Ԥ�ȶ���õĳ��򣨴򿪶���Ĵ��ڣ�
	 * 
	 * @param container
	 */
	private void bindEnventAdapterToButton(JComponent container) {
		int count = container.getComponentCount();
		NavigationPanelButtonAdapter navigationPanelButtonAdapter = new NavigationPanelButtonAdapter();
		for (int i = 0; i < count; i++) {
			if (container.getComponent(i) instanceof JButton) {
				JButton button = ((JButton) container.getComponent(i));
				button.setToolTipText(button.getText());
				button.addActionListener(navigationPanelButtonAdapter);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else if (container.getComponent(i) instanceof JComponent) {
				bindEnventAdapterToButton((JComponent) container
						.getComponent(i));
			}
		}
	}

	private boolean checkFormIsExist(String targetForm) {
		int iCount = deskPanel.getComponentCount();
		for (int i = 0; i < iCount; i++) {
			if (deskPanel.getComponent(i) instanceof JInternalFrame) {
				if (targetForm.equals(deskPanel.getComponent(i).getClass()
						.getName())
						|| targetForm.equals(((JInternalFrameBase) deskPanel
								.getComponent(i)).getFlowPaneClassName())) {
					JInternalFrame form = (JInternalFrame) deskPanel
							.getComponent(i);
					reopenForm(form);
					return true;
				}
			} else if (deskPanel.getComponent(i) instanceof JInternalFrame.JDesktopIcon) {
				if (targetForm.equals(((JInternalFrame.JDesktopIcon) deskPanel
						.getComponent(i)).getInternalFrame().getClass()
						.getName())) {
					JInternalFrame form = (JInternalFrame) ((JInternalFrame.JDesktopIcon) deskPanel
							.getComponent(i)).getInternalFrame();
					deskPanel.getDesktopManager().deiconifyFrame(form);
					reopenForm(form);
					return true;
				}
			}
		}
		return false;
	}

	class NavigationPanelButtonAdapter implements java.awt.event.ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (((JButton) e.getSource()).getActionCommand().equals("close")) {
			} else {
				String caption = button.getText();
				String actionCommand = ((JButton) e.getSource())
						.getActionCommand();
				// System.out.println("-----------actionCommand:" +
				// actionCommand);
				button.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				Element root = getRootElement();
				Element element = getElementByTargetFormName(root,
						((JButton) e.getSource()).getActionCommand().trim());
				if (element != null) {
					mapParameters = getFormParameterFromXml(element);
				} else {
					mapParameters = getFormParameterFromActionCommand(button
							.getActionCommand());
				}

				String formClassName = "";
				if (actionCommand.indexOf("?") > 0) {
					formClassName = actionCommand.substring(0,
							actionCommand.indexOf("?"));
				} else {
					formClassName = actionCommand;
				}
				// System.out.println("--------------formClassName:"
				// + formClassName);
				showTargetForm("", formClassName, caption, null, mapParameters);
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	public Element getElementByTargetFormName(Element element,
			String targetFormName) {
		List listElement = element.getChildren("link");
		if (listElement == null || listElement.size() == 0) {
			return null;
		}
		for (int i = 0; i < listElement.size(); i++) {
			Element subElement = (Element) listElement.get(i);
			if (subElement != null) {
				String formName = (subElement.getAttributeValue("targetForm") == null ? ""
						: subElement.getAttributeValue("targetForm").trim());
				if (targetFormName.equals(formName)) {
					return subElement;
				} else {
					Element elementTemp = getElementByTargetFormName(
							subElement, targetFormName);
					if (elementTemp == null) {
						continue;
					} else {
						return elementTemp;
					}
				}
			} else {
				continue;
			}
		}
		return null;
	}

	/**
	 * 
	 * This method initializes deskPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JDesktopPane
	 * 
	 */
	public JDesktopPane getDeskPanel() {
		if (deskPanel == null) {
			deskPanel = new JDesktopPane();
			// deskPanel.setBorder(null);
			deskPanel.addContainerListener(new ContainerAdapter() {

				public void componentAdded(java.awt.event.ContainerEvent e) {

					super.componentAdded(e);

					if (e.getChild() instanceof JInternalFrame
							|| e.getChild() instanceof JInternalFrame.JDesktopIcon) {

						if (e.getChild() instanceof JInternalFrame.JDesktopIcon) {

							e.getChild().setVisible(false);

						} else if (e.getChild() instanceof JInternalFrame) {

							e.getChild().setVisible(true);

							if (naviBar != null) {

								naviBar.addInternalFrame((JInternalFrame) e
										.getChild());

							}
						}
					}
					refreshNaviBar();
				}

				public void componentRemoved(java.awt.event.ContainerEvent e) {
					if (e.getChild() instanceof JInternalFrame
							|| e.getChild() instanceof JInternalFrame.JDesktopIcon) {
						if (naviBar != null
								&& e.getChild() instanceof JInternalFrame) {
							naviBar.removeInternalFrame((JInternalFrame) e
									.getChild());
						}
					}
					super.componentRemoved(e);
					if (naviBar != null) {
						boolean isZero = naviBar.getActiveFormCount() <= 0;
						jPanel1.setVisible(isZero);
						if (!isMenuNavigation && isZero
								&& !getJScrollPane().isVisible()) {
							getJScrollPane().setVisible(true);
							jSplitPane.setDividerSize(dividerSize);
							jSplitPane.setDividerLocation(150);
						}

					}
					refreshNaviBar();
				}
			});
		}
		return deskPanel;
	}

	/**
	 * 
	 * This method initializes jJMenuBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuBar
	 * 
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMDIMenuBar();
			// 系统
			jJMenuBar.add(getMSys());
			// 风格
			// jJMenuBar.add(getMFacade());
			// 样式
			jJMenuBar.add(getMThemes());

			jJMenuBar.add(getJMenu());

			jJMenuBar.add(getMJAVAIME());

			jJMenuBar.add(getMNavigation());

			jJMenuBar.add(getMHelp());
		}
		return jJMenuBar;
	}

	/**
	 * 
	 * This method initializes mSys
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenu
	 * 
	 */
	private JMenu getMSys() {
		if (mSys == null) {
			mSys = new JMenu();
			mSys.setText("系统");
			mSys.add(getMiLogout());
			mSys.add(getMiExit());
		}
		return mSys;
	}

	/**
	 * 
	 * 初始化登出
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private JMenuItem getMiLogout() {
		if (miLogout == null) {
			miLogout = new JMenuItem();
			miLogout.setText("注销");
			miLogout.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					deskPanel.removeAll();
					fmMain.dispose();
					fmMain = null;
					if (!logoutActionListenerList.isEmpty()) {
						for (LogoutActionListener logoutActionListener : logoutActionListenerList) {
							logoutActionListener.actionPerformed(e);
						}
					}
					// ShowFormControl
					// .showForm("com.bestway.bcus.client.authority.FmLogin");
				}
			});

		}
		return miLogout;
	}

	/**
	 * 
	 * 初始化退出
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private JMenuItem getMiExit() {
		if (miExit == null) {
			miExit = new JMenuItem();
			miExit.setText("退出");
			miExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					System.exit(0);
				}
			});

		}
		return miExit;
	}

	/**
	 * 初始化风格
	 * 
	 * @return javax.swing.JMenu
	 * 
	 */
	private JMenu getMFacade() {
		if (mFacade == null) {
			mFacade = new JMenu();
			mFacade.setText("风格");
			mFacade.setVisible(false);
			mFacade.add(getMiJava());
			mFacade.add(getMiWindows());
			getButtonGroup();
		}
		return mFacade;
	}

	/**
	 * 帮助按钮初始化
	 * 
	 * @return javax.swing.JMenu
	 * 
	 */
	private JMenu getMHelp() {
		if (mHelp == null) {
			mHelp = new JMenu();
			mHelp.setText("帮助");
			mHelp.add(getMiRegister());
			mHelp.add(getMiDailog());
			mHelp.add(getMiAbout());
			mHelp.addSeparator();
			mHelp.add(getJMenuItem());
			// mHelp.add(getJMenuItem1());
			// mHelp.addSeparator();
			mHelp.add(getMiUpgrade());
			mHelp.add(getMiBug());
		}
		return mHelp;
	}

	/**
	 * 
	 * This method initializes miDefault
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */
	private void setComponentStyle() {

		FormThemeControl.setComponentStyle(this);
		// this.naviBar.reactiveSelectInternalFrame();

		JInternalFrame activeFrame = naviBar.getSelectedInternalFrame();

		if (activeFrame != null) {

			reopenForm(activeFrame);

		}
	}

	/**
	 * 
	 * This method initializes miAbout
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenuItem
	 * 
	 */

	private JMenuItem getMiDailog() {
		if (miDailog == null) {
			miDailog = new JMenuItem();
			miDailog.setText("程序日志");
			miDailog.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					getDgExplain().setVisible(true);
				}
			});

		}
		return miDailog;
	}

	private DgExplain dgExplain = null;

	private JMenuItem getMiAbout() {
		if (miAbout == null) {
			miAbout = new JMenuItem();
			miAbout.setText("关于");
			miAbout.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					(new DgAbout()).setVisible(true);

				}
			});

		}
		return miAbout;
	}

	/**
	 * 
	 * This method initializes miJava
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getMiJava() {
		if (miJava == null) {
			miJava = new JRadioButtonMenuItem();
			miJava.setText("默认风格");
			miJava.setActionCommand("java");
			miJava.setSelected(true);
			miJava.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					// FormThemeControl.setTheme(FormThemeControl.OCEAN_THEME);
					// // MetalLookAndFeel.setCurrentTheme(new OceanTheme());
					// FormThemeControl.setStyle(e);
					// setComponentStyle();
					// setThemesState(true);
				}
			});

		}
		return miJava;
	}

	/**
	 * 
	 * This method initializes miWindows
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getMiWindows() {
		if (miWindows == null) {
			miWindows = new JRadioButtonMenuItem();
			miWindows.setText("Windows风格");
			miWindows.setActionCommand("windows");
			miWindows.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					// FormThemeControl.setStyle(e);
					// setComponentStyle();
					// setThemesState(false);
				}
			});

		}
		return miWindows;
	}

	/**
	 * 
	 */
	/*
	 * private void setThemesState(boolean state) { mThemes.setEnabled(state); }
	 */

	/**
	 * 
	 * This method initializes buttonGroup
	 * 
	 * 
	 * 
	 * @return javax.swing.ButtonGroup
	 * 
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getMiJava());
			buttonGroup.add(this.getMiWindows());
		}
		return buttonGroup;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbOtherInfo1 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jLabel = new JLabel();
			jLabel.setText("系统已打开的窗口");// 系统已打开的窗口
			jLabel.setPreferredSize(new Dimension(120, 22));
			lbOtherInfo1.setText("JLabel");
			lbOtherInfo1.setForeground(Color.blue);
			jPanel1.add(jLabel, BorderLayout.WEST);
			jPanel1.add(getNaviBar(), BorderLayout.CENTER);
			jPanel1.add(getJPanel3(), BorderLayout.EAST);
		}
		return jPanel1;
	}

	// /**
	// *
	// * This method initializes btnCloseWindows
	// *
	// *
	// *
	// * @return javax.swing.JButton
	// *
	// */
	// private JButton getBtnCloseWindows() {
	// if (btnCloseWindows == null) {
	// btnCloseWindows = new JButton();
	// // btnCloseWindows.setText("关闭全部窗口");
	// // btnCloseWindows.setToolTipText("关闭全部窗口");
	// btnCloseWindows.setIcon(new ImageIcon(getClass().getResource(
	// "/com/bestway/client/resource/images/help_large.gif")));
	// btnCloseWindows.setPreferredSize(new Dimension(22, 18));
	// btnCloseWindows.setBorder(null);
	// btnCloseWindows.setContentAreaFilled(false);
	// btnCloseWindows.setHorizontalTextPosition(SwingConstants.CENTER);
	// btnCloseWindows
	// .addActionListener(new java.awt.event.ActionListener() {
	//
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (deskPanel.getComponentCount() > 0) {
	// // if
	// // (FmMain.this.getCbHideForm().getItemCount() >
	// // 0) {
	// if (!(JOptionPane.showConfirmDialog(
	// FmMain.this, "你确定要全部关闭？", "提示！", 0) == 0)) {
	// return;
	// }
	// } else {
	// return;
	// }
	// closeAllChildForm();
	// }
	// });
	//
	// }
	// return btnCloseWindows;
	// }

	/**
	 * 
	 * This method initializes mThemes
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenu
	 * 
	 */
	private JMenu getMThemes() {
		if (mThemes == null) {
			mThemes = new JMenu();
			mThemes.setText("样式");
			mThemes.add(getMFont());
			ButtonGroup bgThemes = new ButtonGroup();
			bgThemes.add(getRbmOcean());
			bgThemes.add(getRbmSteel());
			bgThemes.add(getRbmAqua());
			bgThemes.add(getRbmContrast());
			bgThemes.add(getRbmEmerald());

			mThemes.add(getRbmOcean());
			mThemes.add(getRbmSteel());
			mThemes.add(getRbmAqua());
			mThemes.add(getRbmContrast());
			mThemes.add(getRbmEmerald());
		}
		return mThemes;
	}

	/**
	 * 
	 * This method initializes mFont
	 * 
	 * 
	 * 
	 * @return javax.swing.JMenu
	 * 
	 */
	private JMenu getMFont() {
		if (mFont == null) {
			mFont = new JMenu();
			mFont.setText("字体");
			ButtonGroup bgFont = new ButtonGroup();
			bgFont.add(getRbmFontPlain());
			bgFont.add(getRmbFontBold());
			mFont.add(getRbmFontPlain());
			mFont.add(getRmbFontBold());
		}
		return mFont;
	}

	/**
	 * 
	 * This method initializes rbmFontPlain
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRbmFontPlain() {
		if (rbmFontPlain == null) {
			rbmFontPlain = new JRadioButtonMenuItem();
			rbmFontPlain.setText("细体");
			rbmFontPlain.setSelected(true);
			rbmFontPlain.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					UIManager.put("swing.boldMetal", Boolean.FALSE);
					setComponentStyle();
				}
			});

		}
		return rbmFontPlain;
	}

	/**
	 * 
	 * This method initializes rmbFontBold
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRmbFontBold() {
		if (rmbFontBold == null) {
			rmbFontBold = new JRadioButtonMenuItem();
			rmbFontBold.setText("粗体");
			rmbFontBold.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {
					UIManager.put("swing.boldMetal", Boolean.TRUE);
					setComponentStyle();
				}
			});

		}
		return rmbFontBold;
	}

	/**
	 * 
	 * This method initializes rbmOcean
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRbmOcean() {
		if (rbmOcean == null) {
			rbmOcean = new JRadioButtonMenuItem();
			rbmOcean.setText("海蓝色");
			rbmOcean.setSelected(true);
			rbmOcean.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {
					FormThemeControl.setTheme(FormThemeControl.OCEAN_THEME);
					setComponentStyle();
					if (themeListener != null) {
						themeListener
								.actionPerformed(FormThemeControl.OCEAN_THEME);
					}
				}
			});

		}
		return rbmOcean;
	}

	/**
	 * 
	 * This method initializes rbmSteel
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRbmSteel() {
		if (rbmSteel == null) {
			rbmSteel = new JRadioButtonMenuItem();
			rbmSteel.setText("铁色");
			rbmSteel.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					FormThemeControl.setTheme(FormThemeControl.STEEL_THEME);
					setComponentStyle();
					if (themeListener != null) {
						themeListener
								.actionPerformed(FormThemeControl.STEEL_THEME);
					}
				}
			});

		}
		return rbmSteel;
	}

	/**
	 * 
	 * This method initializes rbmAqua
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRbmAqua() {
		if (rbmAqua == null) {
			rbmAqua = new JRadioButtonMenuItem();
			rbmAqua.setText("海蓝宝石");
			rbmAqua.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					FormThemeControl.setTheme(FormThemeControl.AQUA_THEME);
					setComponentStyle();
					if (themeListener != null) {
						themeListener
								.actionPerformed(FormThemeControl.AQUA_THEME);
					}
				}
			});

		}
		return rbmAqua;
	}

	/**
	 * 
	 * This method initializes rbmContrast
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRbmContrast() {
		if (rbmContrast == null) {
			rbmContrast = new JRadioButtonMenuItem();
			rbmContrast.setText("沙岩色");
			rbmContrast.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					FormThemeControl.setTheme(FormThemeControl.SANDSTONE_THEME);
					setComponentStyle();
					if (themeListener != null) {
						themeListener
								.actionPerformed(FormThemeControl.SANDSTONE_THEME);
					}
				}
			});

		}
		return rbmContrast;
	}

	/**
	 * 
	 * This method initializes rbmEmerald
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 * 
	 */
	private JRadioButtonMenuItem getRbmEmerald() {
		if (rbmEmerald == null) {
			rbmEmerald = new JRadioButtonMenuItem();
			rbmEmerald.setText("祖母绿");
			rbmEmerald.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					FormThemeControl.setTheme(FormThemeControl.EMERALD_THEME);
					setComponentStyle();
					if (themeListener != null) {
						themeListener
								.actionPerformed(FormThemeControl.EMERALD_THEME);
					}
				}
			});

		}
		return rbmEmerald;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbOtherInfo2 = new JLabel();
			lbOtherInfo2.setText(" ");
			lbOtherInfo2.setHorizontalAlignment(SwingConstants.CENTER);
			lbOtherInfo2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbOtherInfo2.setForeground(Color.blue);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jPanel2.add(lbOtherInfo1, BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * ���ù�������ֵ
	 */
	private void setApplicationContext() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"application-resource.xml");
		CommonVariables.setApplicationContext(applicationContext);
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiRegister() {
		if (miRegister == null) {
			miRegister = new JMenuItem();
			miRegister.setText("注册");
		}
		return miRegister;
	}

	/**
	 * 移除之前的所有响应时间 根据自定义加入事件对象
	 * 
	 * @param actionListener
	 */
	public void addRegisterActionListener(ActionListener actionListener) {

		ActionListener[] actionListeners = getMiRegister().getActionListeners();

		for (int i = 0; i < actionListeners.length; i++) {

			getMiRegister().removeActionListener(actionListeners[i]);

		}

		getMiRegister().addActionListener(actionListener);

	}

	/**
	 * 增加自定义注销事件
	 * 
	 * @param actionListener
	 */
	public void addLogoutActionListener(ActionListener actionListener) {
		this.getMiLogout().addActionListener(actionListener);
	}

	/**
	 * 增加自定义系统退出事件
	 * 
	 * @param actionListener
	 */
	public void addExitActionListener(ActionListener actionListener) {
		this.getMiExit().addActionListener(actionListener);
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("视窗");
			jMenu.add(getMiShow());
		}
		return jMenu;
	}

	private JRadioButtonMenuItem getMiShow() {
		if (mishow == null) {
			mishow = new JRadioButtonMenuItem();
			mishow.setText("显示向导栏");
			mishow.setSelected(true);
			mishow.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {

					if (mishow.isSelected()) {
						FmMain.this.setShowNav(true);
					} else {
						FmMain.this.setShowNav(false);
					}

				}
			});

		}
		return mishow;
	}

	/**
	 * @return Returns the showNav.
	 */
	public boolean isShowNav() {
		return showNav;
	}

	/**
	 * @param showNav
	 *            The showNav to set.
	 */
	public void setShowNav(boolean showNav) {
		this.showNav = showNav;
	}

	/**
	 * This method initializes miUpgrade
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUpgrade() {
		if (miUpgrade == null) {
			miUpgrade = new JMenuItem();
			miUpgrade.setText("程序升级");
			miUpgrade.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (JOptionPane.showConfirmDialog(FmMain.this,
					// "你确认要更新程序吗？", "提示!", JOptionPane.YES_NO_OPTION,
					// JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					upgrade.run();
					// }
				}
			});
		}
		return miUpgrade;
	}

	private JMenuItem miRestart = null;

	private JPanel pnWarning = null;

	// private JMenu mIME = null;

	private JMenu mJAVAIME = null;

	private JMenuItem miSetupJAVAIME = null;

	private JMenu mNavigation = null;

	private JRadioButtonMenuItem rbmMenu = null;

	private JRadioButtonMenuItem rbmTree = null;

	private JPanel jPanel3 = null; // @jve:decl-index=0:visual-constraint="539,59"

	private JLabel lbOther = null;

	private JLabel lbOtherInfo2 = null;

	private JPanel jPanel4 = null;

	private JMenuItem getJMenuItem() {
		if (miRestart == null) {
			miRestart = new JMenuItem();
			miRestart.setText("重启服务器");
			miRestart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmMain.this,
							"你确认要重启服务器吗？", "提示!", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
						upgrade.restart();
					}
				}
			});
		}
		return miRestart;
	}

	public void setUpgrade(Upgrade upgrade) {
		this.upgrade = upgrade;
	}

	/**
	 * This method initializes miBug
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBug() {
		if (miBug == null) {
			miBug = new JMenuItem();
			miBug.setText("错误报告");
			miBug.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String operatingSystem = System.getProperty("os.name");

					try {
						if (operatingSystem.toLowerCase().indexOf("windows") > -1) {
							Runtime.getRuntime()
									.exec("rundll32 url.dll,FileProtocolHandler http://bugs.ibestsoft.com/");
						} else {
							// Try with htmlview...
							Runtime.getRuntime().exec(
									"htmlview http://bugs.ibestsoft.com/");
						}
					} catch (Exception ex) {
						javax.swing.JOptionPane.showMessageDialog(FmMain.this,
								"Unable to open http://bugs.ibestsoft.com/",
								"", javax.swing.JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return miBug;
	}

	public void setLoadPlugin(LoadPlugin loadPlugin) {
		this.loadPlugin = loadPlugin;
	}

	/**
	 * This method initializes pnWarning
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getPnWarning() {
		if (pnWarning == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(2);
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(1);
			pnWarning = new JPanel();
			pnWarning.setPreferredSize(new Dimension(200, 18));
			pnWarning.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			pnWarning.setLayout(flowLayout);
		}
		return pnWarning;
	}

	public ThemeListener getThemeListener() {
		return themeListener;
	}

	public void addThemeListener(ThemeListener styleListener) {
		this.themeListener = styleListener;
	}

	private void setThemeState(int theme) {
		switch (theme) {
		case FormThemeControl.OCEAN_THEME:
			getRbmOcean().setSelected(true);
			break;
		case FormThemeControl.STEEL_THEME:
			getRbmSteel().setSelected(true);
			break;
		case FormThemeControl.AQUA_THEME:
			getRbmAqua().setSelected(true);
			break;
		case FormThemeControl.SANDSTONE_THEME:
			getRbmContrast().setSelected(true);
			break;
		case FormThemeControl.EMERALD_THEME:
			getRbmEmerald().setSelected(true);
			break;
		default:
			getRbmOcean().setSelected(true);
		}
		if (this.isMenuNavigation) {
			getRbmMenu().setSelected(true);
		} else {
			getRbmTree().setSelected(true);
		}
	}

	public int getNaviBarAllowWidth() {
		int allowWidth = Toolkit.getDefaultToolkit().getScreenSize().width
				- this.jLabel.getPreferredSize().width
				// - this.btnCloseWindows.getPreferredSize().width
				- this.lbOther.getPreferredSize().width;// -100
		return allowWidth;
	}

	/**
	 * 关闭所有其他的窗口
	 */
	public void closeAllChildFormExceptThis(JInternalFrame jf) {
		int iCount = deskPanel.getComponentCount();
		for (int i = iCount - 1; i > -1; i--) {
			Component component = deskPanel.getComponent(i);
			if (component instanceof JInternalFrame) {
				String targetForm = component.getClass().getName();
				if (targetForm.equals(jf.getClass().getName())
						|| targetForm.equals(((JInternalFrameBase) jf)
								.getFlowPaneClassName())) {
					continue;
				}
				((JInternalFrame) component).doDefaultCloseAction();
				component = null;
			} else if (component instanceof JInternalFrame.JDesktopIcon) {
				((JInternalFrame.JDesktopIcon) component).getInternalFrame()
						.doDefaultCloseAction();
				component = null;
			} else if (component instanceof JPanel) {
				deskPanel.remove(component);
				component = null;
			}
		}
		deskPanel.repaint();
	}

	/**
	 * 关闭所有窗口
	 */
	public void closeAllChildForm() {
		int iCount = deskPanel.getComponentCount();
		for (int i = iCount - 1; i > -1; i--) {
			Component component = deskPanel.getComponent(i);
			if (component instanceof JInternalFrame) {
				((JInternalFrame) component).doDefaultCloseAction();
				component = null;
			} else if (component instanceof JInternalFrame.JDesktopIcon) {
				((JInternalFrame.JDesktopIcon) component).getInternalFrame()
						.doDefaultCloseAction();
				component = null;
			} else if (component instanceof JPanel) {
				deskPanel.remove(component);
				component = null;
			}
		}
		deskPanel.repaint();
	}

	/**
	 * This method initializes mJAVAIME
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMJAVAIME() {
		if (mJAVAIME == null) {
			mJAVAIME = new JMenu();
			mJAVAIME.setText("输入法");
			mJAVAIME.add(getMiSetupJAVAIME());
		}
		return mJAVAIME;
	}

	/**
	 * This method initializes miSetup
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiSetupJAVAIME() {
		if (miSetupJAVAIME == null) {
			miSetupJAVAIME = new JMenuItem();
			miSetupJAVAIME.setText("安装 JAVA 输入法");
			miSetupJAVAIME.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (setupJAVAIMEListener != null) {
						setupJAVAIMEListener.actionPerformed(e);
					}
				}
			});
		}
		return miSetupJAVAIME;
	}

	public ActionListener getSetupJAVAIMEListener() {
		return setupJAVAIMEListener;
	}

	public void addSetupJAVAIMEListener(ActionListener setupJAVAIMEListener) {
		this.setupJAVAIMEListener = setupJAVAIMEListener;
	}

	public JMenuItem getJbcusHelp() {
		if (jbcusHelp == null) {
			jbcusHelp = new JMenuItem();
			jbcusHelp.setText("在线帮助");
			jbcusHelp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jbcusHelpListener != null) {
						jbcusHelpListener.actionPerformed(e);
					}

				}
			});

		}
		return jbcusHelp;
	}

	public ActionListener getJbcusHelpListener() {

		return jbcusHelpListener;
	}

	public void addJbcusHelpListener(ActionListener jbcusHelpListener) {
		this.jbcusHelpListener = jbcusHelpListener;

	}

	/**
	 * This method initializes jMenu1
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getMNavigation() {
		if (mNavigation == null) {
			mNavigation = new JMenu();
			mNavigation.setText("导航类型");
			mNavigation.add(getRbmMenu());
			mNavigation.add(getRbmTree());

			ButtonGroup bgNavigation = new ButtonGroup();
			bgNavigation.add(getRbmMenu());
			bgNavigation.add(getRbmTree());
		}
		return mNavigation;
	}

	/**
	 * This method initializes jRadioButtonMenuItem
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 */
	private JRadioButtonMenuItem getRbmMenu() {
		if (rbmMenu == null) {
			rbmMenu = new JRadioButtonMenuItem();
			rbmMenu.setText("菜单导航");
			rbmMenu.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (!isMenuNavigation) {
						isMenuNavigation = true;
						if (navigationListener != null) {
							navigationListener
									.actionPerformed(isMenuNavigation);
						}
						initSysFunctionUIComponents();
					}
				}

			});
			// rbmMenu.addItemListener(new java.awt.event.ItemListener() {
			// public void itemStateChanged(java.awt.event.ItemEvent e) {
			// isMenuNavigation = true;
			// if (navigationListener != null) {
			// navigationListener.actionPerformed(isMenuNavigation);
			// }
			// initSysFunctionUIComponents();
			// }
			// });
		}
		return rbmMenu;
	}

	/**
	 * This method initializes jRadioButtonMenuItem1
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 */
	private JRadioButtonMenuItem getRbmTree() {
		if (rbmTree == null) {
			rbmTree = new JRadioButtonMenuItem();
			rbmTree.setText("树形导航");
			rbmTree.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (isMenuNavigation) {
						isMenuNavigation = false;
						if (navigationListener != null) {
							navigationListener
									.actionPerformed(isMenuNavigation);
						}
						initSysFunctionUIComponents();
					}
				}

			});
			// rbmTree.addItemListener(new java.awt.event.ItemListener() {
			// public void itemStateChanged(java.awt.event.ItemEvent e) {
			// isMenuNavigation = false;
			// if (navigationListener != null) {
			// navigationListener.actionPerformed(isMenuNavigation);
			// }
			// initSysFunctionUIComponents();
			// }
			// });
		}
		return rbmTree;
	}

	public NavigationListener getNavigationListener() {
		return navigationListener;
	}

	public void setNavigationListener(NavigationListener navigationListener) {
		this.navigationListener = navigationListener;
	}

	public void reshowTreeNavi() {
		if (!this.isMenuNavigation) {
			if (getJScrollPane().isVisible()) {
				getJScrollPane().setVisible(false);
				jSplitPane.setDividerSize(0);
				jSplitPane.setDividerLocation(0);
			} else {
				getJScrollPane().setVisible(true);
				jSplitPane.setDividerSize(dividerSize);
				jSplitPane.setDividerLocation(150);
			}
		}
	}

	public void addOpenedWindow(Window window) {
		listOpenedWindow.add(window);
	}

	public void removeOpenedWindow(Window window) {
		listOpenedWindow.remove(window);
		// System.out.println("--------remove:"+window.getClass().getName()+"
		// size"+listOpenedWindow.size());
	}

	public Window getLastOpenedWindow() {
		if (listOpenedWindow != null && listOpenedWindow.size() > 0) {
			// System.out.println("--------"+listOpenedWindow.get(listOpenedWindow.size()
			// - 1).getClass().getName()+" size:"+listOpenedWindow.size());
			return listOpenedWindow.get(listOpenedWindow.size() - 1);
		}
		return null;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			lbOther = new JLabel();
			lbOther.setText("▼(0)");
			lbOther.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (naviBar != null) {
						naviBar.getJPopupMenu().show(
								lbOther,
								lbOther.getX(),
								lbOther.getY()
										+ lbOther.getPreferredSize().height);
					}
				}
			});
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setSize(new Dimension(175, 45));
			// jPanel3.add(getBtnCloseWindows(), BorderLayout.EAST);
			jPanel3.add(lbOther, BorderLayout.WEST);
		}
		return jPanel3;
	}

	private void setGlobleKeyListener() {
		KeyboardFocusManager mg = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		mg.addKeyEventPostProcessor(new GlobleKeyEventPostProcessor());
	}

	/**
	 * 增加用户自定义的注销事件处理程序
	 * 
	 * @param logoutActionListener
	 */
	public void addLogoutActionListener(
			LogoutActionListener logoutActionListener) {
		if (this.logoutActionListenerList != null
				&& logoutActionListener != null) {
			this.logoutActionListenerList.add(logoutActionListener);
		}
	}

	public int getOldwidth() {
		return oldwidth;
	}

	public void setOldwidth(int oldwidth) {
		this.oldwidth = oldwidth;
	}

	public int getOldheight() {
		return oldheight;
	}

	public void setOldheight(int oldheight) {
		this.oldheight = oldheight;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.LOWERED));
			jPanel4.add(lbOtherInfo2, BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * 根据模块Id判断系统是否包含此模块
	 * 
	 * @param moduleId
	 * @return
	 */
	public boolean checkHasModule(String moduleId) {
		if (moduleId == null || "".equals(moduleId.trim())) {
			return false;
		}
		if (this.hasModules == null || this.hasModules.size() <= 0) {
			return true;
		}
		return this.hasModules.contains(moduleId.trim());
	}

	public DgExplain getDgExplain() {
		if (dgExplain == null) {
			dgExplain = new DgExplain();
		}
		return dgExplain;
	}
}
