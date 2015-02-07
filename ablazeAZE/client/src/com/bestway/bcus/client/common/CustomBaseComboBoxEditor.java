/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalComboBoxEditor;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.ui.winuicontrol.KeyAdapterControl.KeyAdapterExtend;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomBaseComboBoxEditor {
	private static CustomBaseComboBoxEditor customBaseComboBoxEditor = null;

	private static final int DEFAULT_POPUP_WIDTH = -1;

	public static CustomBaseComboBoxEditor getInstance() {
		if (customBaseComboBoxEditor == null) {
			customBaseComboBoxEditor = new CustomBaseComboBoxEditor();
		}
		return customBaseComboBoxEditor;
	}

	public void setComboBoxEditor(final JComboBox comboBox) {
		comboBox.setEditor(new TextFieldComboBoxEditor(comboBox,
				CustomBaseComboBoxEditor.DEFAULT_POPUP_WIDTH));
		comboBox.addPropertyChangeListener("enabled",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						setEditorComponentState(comboBox, evt);
					}
				});
	}

	public void setComboBoxEditor(final JComboBox comboBox, int popupWidth) {
		comboBox.setEditor(new TextFieldComboBoxEditor(comboBox, popupWidth));
		comboBox.addPropertyChangeListener("enabled",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						setEditorComponentState(comboBox, evt);
					}
				});
	}

	public void setComboBoxEditor(final JComboBox comboBox, String codeField,
			String nameField) {
	
		comboBox.setEditor(new TextFieldComboBoxEditor(comboBox, codeField,
				nameField, CustomBaseComboBoxEditor.DEFAULT_POPUP_WIDTH));
		comboBox.addPropertyChangeListener("enabled",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						setEditorComponentState(comboBox, evt);
					}
				});
	}

	public void setComboBoxEditor(final JComboBox comboBox, String codeField,
			String nameField, int popupWidth) {
		comboBox.setEditor(new TextFieldComboBoxEditor(comboBox, codeField,
				nameField, popupWidth));
		comboBox.addPropertyChangeListener("enabled",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						setEditorComponentState(comboBox, evt);
					}
				});
	}
	
	private void setEditorComponentState(JComboBox comboBox,
			PropertyChangeEvent evt) {
		if (comboBox.getEditor().getEditorComponent() instanceof JTextField) {
			JTextField textField = (JTextField) comboBox.getEditor()
					.getEditorComponent();
			textField.setEnabled(true);
			if ("true".equals(evt.getNewValue().toString())
					&& "false".equals(evt.getOldValue().toString())) {
				textField.setEditable(true);
				textField.setBackground(UIManager
						 .getColor("controlLtHighlight"));
			} else if ("false".equals(evt.getNewValue().toString())
					&& "true".equals(evt.getOldValue().toString())) {
				textField.setEditable(false);
				textField.setBackground((Color) UIManager.getColor("Panel.background"));
			}
		}
	}
	
	public void setSelectedIndex(JComboBox comboBox, String code) {
		((TextFieldComboBoxEditor) comboBox.getEditor()).setSelectedIndex(code);
	}

	class TextFieldComboBoxEditor extends MetalComboBoxEditor {
		JComboBox comboBox = null;

		String codeField = "";

		String nameField = "";

		Object oldObject = null;

		int oldSelectedIndex = -1;

		ComboBoxModel model = null;
		ComboBoxModel tempModel = null;

		public void setItem(Object anObject) {
			oldObject = anObject;
			if (anObject == null) {
				editor.setText("");
				return;
			}
			if (anObject instanceof CustomBaseEntity) {
				if (nameField != null && !"".equals(nameField.trim())) {
					editor.setText(((CustomBaseEntity) anObject).getName());
				} else if (codeField != null && !"".equals(codeField.trim())) {
					editor.setText(((CustomBaseEntity) anObject).getCode());
				} else {
					editor.setText(((CustomBaseEntity) anObject).getName());
				}
			} else if (anObject instanceof ItemProperty) {
				if (nameField != null && !"".equals(nameField.trim())) {
					editor.setText(((ItemProperty) anObject).getName());
				} else if (codeField != null && !"".equals(codeField.trim())) {
					editor.setText(((ItemProperty) anObject).getCode());
				} else {
					editor.setText(((ItemProperty) anObject).getName());
				}
			} else {
				if ((nameField != null) && (!nameField.equals(""))
						|| (codeField != null) && (!codeField.equals(""))) {
					if ((nameField != null) && (!nameField.equals(""))) {
						try {
							editor.setText(BeanUtils.getProperty(anObject,
									nameField));
						} catch (Exception e) {
							editor.setText("");
							e.printStackTrace();
						}
					} else if ((codeField != null) && (!codeField.equals(""))) {
						try {
							editor.setText(BeanUtils.getProperty(anObject,
									codeField));
						} catch (Exception e) {
							editor.setText("");
							e.printStackTrace();
						}
					}
				} else {
					editor.setText(anObject.toString());
				}
			}
			editor.setCaretPosition(0);
		}

		public Object getItem() {
			return oldObject;
		}

		private int getComBoBoxSelectedIndex() {
			if (this.comboBox == null) {
				return -1;
			}
			return this.comboBox.getSelectedIndex();
		}

		private void fireSelectedIndexChange(int oldValue, int newValue) {
			// System.out.println("-----------------selectedIndex:oldValue"
			// + oldValue + ",newValue:" + newValue);
			ItemListener[] itemListeners = this.comboBox.getItemListeners();
			int eventType = ItemEvent.ITEM_STATE_CHANGED;
			Object item = null;
			if (oldValue >= 0) {
				item = this.comboBox.getItemAt(oldValue);
				if (itemListeners != null && itemListeners.length > 0) {
					for (ItemListener itemListener : itemListeners) {
						if (itemListener != null) {
							itemListener.itemStateChanged(new ItemEvent(
									this.comboBox, eventType, item,
									ItemEvent.DESELECTED));
						}
					}
				}
			}
			if (newValue >= 0) {
				item = this.comboBox.getItemAt(newValue);
				if (itemListeners != null && itemListeners.length > 0) {
					for (ItemListener itemListener : itemListeners) {
						if (itemListener != null) {
							itemListener.itemStateChanged(new ItemEvent(
									this.comboBox, eventType, item,
									ItemEvent.SELECTED));
						}
					}
				}
			}
		}

		public TextFieldComboBoxEditor(JComboBox comboBox, int popupWidth) {
			this(comboBox,null,null,popupWidth);
		}

		private void copyModel() {
			List list = new ArrayList();
			for (int i = 0; i < this.comboBox.getModel().getSize(); i++) {
				list.add(this.comboBox.getModel().getElementAt(i));
			}
			this.model = new DefaultComboBoxModel(list.toArray());
		}

		private void resetModel() {
			comboBox.setModel(model);
			comboBox.hidePopup();
		}

		public TextFieldComboBoxEditor(final JComboBox comboBox, String codeField,
				String nameField, int popupWidth) {
			this.comboBox = comboBox;	
			if(codeField != null){
				this.codeField = codeField;
			}
			if(nameField != null){
				this.nameField = nameField;
			}
			this.comboBox.setEditable(true);		
			if (popupWidth == CustomBaseComboBoxEditor.DEFAULT_POPUP_WIDTH) {
				this.comboBox.setUI(new CustomBaseComboBoxUI());
			} else {
				this.comboBox.setUI(new CustomBaseComboBoxUI(popupWidth));
			}
			this.comboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					oldSelectedIndex = getComBoBoxSelectedIndex();
				}
			});
			this.comboBox.addPropertyChangeListener("model",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getNewValue().equals(tempModel)){
							return;
						}
						copyModel();
					}
				});
			
			editor.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if(!editor.isEditable()){
						return;
					}
					copyModel();
					// editor.getDocument().addDocumentListener(documentListener);
					editor.selectAll();
					oldSelectedIndex = getComBoBoxSelectedIndex();
				}

				public void focusLost(FocusEvent e) {
					if(!editor.isEditable()){
						return;
					}
					// editor.getDocument().removeDocumentListener(
					// documentListener);
					String keyAndValue = getSelectKeyAndValue();
					// System.out.println("-------------"+keyAndValue+"
					// "+editor.getText().trim());
					// String vaule = editor.getText().trim();					
					resetModel();
					// setSelectedIndex(vaule);
					setSelectedIndexByKeyAndValue(keyAndValue);
					int index = getComBoBoxSelectedIndex();
					if (index != oldSelectedIndex) {
						fireSelectedIndexChange(oldSelectedIndex, index);
					}
				}
			});
			editor.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					// System.out.println("keyTyped()"+editor.getText()+e.getKeyChar());
					// //
					if(!editor.isEditable()){
						return;
					}
					SwingUtilities.invokeLater(notifyTableRunnable);
				}
			});			
			//
			// 加入回车事件
			//
			KeyListener[] keyListeners = editor.getKeyListeners();
			boolean isExist = false;
			for(KeyListener kl : keyListeners ){
				if(kl instanceof KeyAdapterExtend){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				editor.addKeyListener(
					new KeyAdapterExtend(editor));
			}			
		}

		protected Runnable notifyTableRunnable = new Runnable() {
			public void run() {
				String key = editor.getText().trim();
				filterKey(key);
			}
		};
		
		private void filterKey(String key) {
			if (key == null || "".equals(key.trim())) {
				model.setSelectedItem(null);
				comboBox.setModel(model);
			} else {
				List list = getFiltedList(key.trim());
				tempModel = new DefaultComboBoxModel(list
						.toArray());
				tempModel.setSelectedItem(null);
				comboBox.setModel(tempModel);
			}
			comboBox.showPopup();
			editor.setText(key);
		}

		private List getFiltedList(String key) {
			List lsResult = new ArrayList();
			String itemName = "";
			String itemCode = "";
			if (model.getSize() < 1) {
				return lsResult;
			}
			Object obj = model.getElementAt(0);
			if (obj instanceof CustomBaseEntity) {
				CustomBaseEntity entity = null;
				int size = model.getSize();
				for (int i = 0; i < size; i++) {
					entity = (CustomBaseEntity) model.getElementAt(i);
					if (entity.getCode() != null
							&& entity.getCode().indexOf(key) >= 0
							|| (entity.getName() != null && entity.getName()
									.indexOf(key) >= 0)) {
						lsResult.add(entity);
					}
				}
			} else if (obj instanceof ItemProperty) {
				ItemProperty item = null;
				int size = model.getSize();
				for (int i = 0; i < size; i++) {
					item = (ItemProperty) model.getElementAt(i);
					if (item.getCode() != null
							&& item.getCode().indexOf(key) >= 0
							|| (item.getName() != null && item.getName()
									.indexOf(key) >= 0)) {
						lsResult.add(item);
					}
				}
			} else {
				if (((codeField != null) && (!codeField.equals("")))
						|| ((nameField != null) && (!nameField.equals("")))) {
					try {
						int size = model.getSize();
						for (int i = 0; i < size; i++) {
							if ((codeField != null) && (!codeField.equals(""))) {
								itemCode = BeanUtils.getProperty(model
										.getElementAt(i), codeField);
								if (itemCode != null
										&& itemCode.indexOf(key) >= 0) {
									lsResult.add(model.getElementAt(i));
									continue;
								}
							}
							if ((nameField != null) && (!nameField.equals(""))) {
								itemName = BeanUtils.getProperty(model
										.getElementAt(i), nameField);
								if (itemName != null
										&& itemName.indexOf(key) >= 0) {
									lsResult.add(model.getElementAt(i));
									continue;
								}
							}
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				} else {
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						itemCode = model.getElementAt(i).toString();
						if (itemCode != null && itemCode.indexOf(key) >= 0) {
							lsResult.add(model.getElementAt(i));
						}
					}
				}
			}
			return lsResult;
		}

		public void setSelectedIndex(String code) {
			int index = -1;
			String itemName = "";
			String itemCode = "";
			if (code.equals("")) {
				this.comboBox.setSelectedIndex(index);
				return;
			}
			if (this.comboBox.getItemCount() < 1) {
				editor.setText("");
				return;
			}
			if (this.comboBox.getSelectedItem() != null) {
				Object obj = this.comboBox.getSelectedItem();
				if (obj instanceof CustomBaseEntity) {
					itemName = ((CustomBaseEntity) obj).getName();
					itemCode = ((CustomBaseEntity) obj).getCode();
				} else if (obj instanceof ItemProperty) {
					itemName = ((ItemProperty) obj).getName();
					itemCode = ((ItemProperty) obj).getCode();
				} else {
					if ((codeField != null) && (!codeField.equals(""))
							|| (nameField != null) && (!nameField.equals(""))) {
						if ((codeField != null) && (!codeField.equals(""))) {
							try {
								itemCode = BeanUtils
										.getProperty(obj, codeField);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
						if ((nameField != null) && (!nameField.equals(""))) {
							try {
								itemName = BeanUtils
										.getProperty(obj, nameField);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
					} else {
						itemName = obj.toString();
						itemCode = obj.toString();
					}
				}
				// if (code.equals(itemName)) {
				// // return;
				// } else if (code.equals(itemCode)) {
				// editor.setText(itemName);
				// }
				// if (code.equals(itemCode) || itemName != null
				// && itemName.indexOf(code) >= 0) {
				// editor.setText(itemName);
				// }
				if (nameField != null && !nameField.equals("")) {
					editor.setText(itemName);
				} else if (codeField != null && !codeField.equals("")) {
					editor.setText(itemCode);
				} else {
					editor.setText(itemName);
				}
			}
			if (this.comboBox != null) {
				if (this.comboBox.getItemCount() < 1) {
					return;
				}

				Object obj = this.comboBox.getItemAt(0);

				// int tempIndex =-1;// this.comboBox.getSelectedIndex();
				// boolean isFirst = false;

				if (obj instanceof CustomBaseEntity) {
					CustomBaseEntity entity = null;
					ComboBoxModel model = this.comboBox.getModel();
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						entity = (CustomBaseEntity) model.getElementAt(i);
						if (entity.getCode() != null
								&& entity.getCode().equals(code)
								|| (entity.getName() != null && entity
										.getName().equals(code))) {
							index = i;
							break;
						}
						// if (isFirst
						// && (entity.getName() != null && entity
						// .getName().indexOf(code) >= 0)) {
						// tempIndex = i;
						// isFirst = false;
						// }
					}
					// if (index == -1) {
					// index = tempIndex;
					// }

				} else if (obj instanceof ItemProperty) {
					ItemProperty item = null;
					ComboBoxModel model = this.comboBox.getModel();
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						item = (ItemProperty) model.getElementAt(i);
						if (item.getCode() != null
								&& item.getCode().equals(code)
								|| (item.getName() != null && item.getName()
										.equals(code))) {
							index = i;
							break;
						}
						// if (isFirst
						// && (item.getName() != null && item.getName()
						// .indexOf(code) >= 0)) {
						// tempIndex = i;
						// isFirst = false;
						// }
					}
					// if (index == -1) {
					// index = tempIndex;
					// }
				} else {
					if ((codeField != null) && (!codeField.equals(""))
							|| (nameField != null) && (!nameField.equals(""))) {
						try {
							ComboBoxModel model = this.comboBox.getModel();
							int size = model.getSize();
							for (int i = 0; i < size; i++) {
								if ((codeField != null)
										&& (!codeField.equals(""))) {
									itemCode = BeanUtils.getProperty(model
											.getElementAt(i), codeField);
									if (itemCode != null
											&& itemCode.equals(code)) {
										index = i;
										break;
									}
								}
								if ((nameField != null)
										&& (!nameField.equals(""))) {
									itemName = BeanUtils.getProperty(model
											.getElementAt(i), nameField);
									if (itemName != null
											&& itemName.equals(code)) {
										index = i;
										break;
									}
								}
								// if (isFirst
								// && (itemName != null && itemName
								// .indexOf(code) >= 0)) {
								// tempIndex = i;
								// isFirst = false;
								// }
							}
							// if (index == -1) {
							// index = tempIndex;
							// }
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					} else {
						ComboBoxModel model = this.comboBox.getModel();
						int size = model.getSize();
						for (int i = 0; i < size; i++) {
							itemCode = model.getElementAt(i).toString();
							if (itemCode.equals(code)
									|| itemCode.indexOf(code) >= 0) {
								index = i;
								break;
							}
						}
						// if (index == -1) {
						// index = tempIndex;
						// }
					}
				}
			}
			if (this.comboBox.getSelectedIndex() < 0 && index < 0) {
				editor.setText("");
			}
			this.comboBox.setSelectedIndex(index);
		}

		public void setSelectedIndexByKeyAndValue(String keyAndValue) {
			int index = -1;
			String codeName = "";
			String itemName = "";
			String itemCode = "";
			if (keyAndValue.equals("")) {
				this.comboBox.setSelectedIndex(index);
				return;
			}
			if (this.comboBox.getItemCount() < 1) {
				editor.setText("");
				return;
			}
			if (this.comboBox.getSelectedItem() != null) {
				Object obj = this.comboBox.getSelectedItem();
				if (obj instanceof CustomBaseEntity) {
					itemName = ((CustomBaseEntity) obj).getName();
					itemCode = ((CustomBaseEntity) obj).getCode();
				} else if (obj instanceof ItemProperty) {
					itemName = ((ItemProperty) obj).getName();
					itemCode = ((ItemProperty) obj).getCode();
				} else {
					if ((codeField != null) && (!codeField.equals(""))
							|| (nameField != null) && (!nameField.equals(""))) {
						if ((codeField != null) && (!codeField.equals(""))) {
							try {
								itemCode = BeanUtils
										.getProperty(obj, codeField);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
						if ((nameField != null) && (!nameField.equals(""))) {
							try {
								itemName = BeanUtils
										.getProperty(obj, nameField);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
					} else {
						itemName = obj.toString();
						itemCode = obj.toString();
					}
				}
				// if (code.equals(itemName)) {
				// // return;
				// } else if (code.equals(itemCode)) {
				// editor.setText(itemName);
				// }
				// if (code.equals(itemCode) || itemName != null
				// && itemName.indexOf(code) >= 0) {
				// editor.setText(itemName);
				// }
				if (nameField != null && !nameField.equals("")) {
					editor.setText(itemName);
				} else if (codeField != null && !codeField.equals("")) {
					editor.setText(itemCode);
				} else {
					editor.setText(itemName);
				}
			}
			if (this.comboBox != null) {
				if (this.comboBox.getItemCount() < 1) {
					return;
				}

				Object obj = this.comboBox.getItemAt(0);

				// int tempIndex =-1;// this.comboBox.getSelectedIndex();
				// boolean isFirst = false;

				if (obj instanceof CustomBaseEntity) {
					CustomBaseEntity entity = null;
					ComboBoxModel model = this.comboBox.getModel();
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						entity = (CustomBaseEntity) model.getElementAt(i);
						codeName = (entity.getCode() == null ? "" : entity
								.getCode())
								+ (entity.getName() == null ? "" : entity
										.getName());
						if (codeName.equals(keyAndValue)) {
							index = i;
							break;
						}
					}
				} else if (obj instanceof ItemProperty) {
					ItemProperty item = null;
					ComboBoxModel model = this.comboBox.getModel();
					int size = model.getSize();
					for (int i = 0; i < size; i++) {
						item = (ItemProperty) model.getElementAt(i);
						codeName = (item.getCode() == null ? "" : item
								.getCode())
								+ (item.getName() == null ? "" : item.getName());
						if (codeName.equals(keyAndValue)) {
							index = i;
							break;
						}
					}
				} else {
					if ((codeField != null) && (!codeField.equals(""))
							|| (nameField != null) && (!nameField.equals(""))) {
						try {
							ComboBoxModel model = this.comboBox.getModel();
							int size = model.getSize();
							for (int i = 0; i < size; i++) {
								if ((codeField != null)
										&& (!codeField.equals(""))) {
									itemCode = BeanUtils.getProperty(model
											.getElementAt(i), codeField);
									codeName = (itemCode == null ? ""
											: itemCode);
									if (itemCode != null
											&& itemCode.equals(keyAndValue)) {
										index = i;
										break;
									}
								}
								if ((nameField != null)
										&& (!nameField.equals(""))) {
									itemName = BeanUtils.getProperty(model
											.getElementAt(i), nameField);
									codeName += (itemName == null ? ""
											: itemName);
									// if (itemName != null
									// && itemName.equals(keyAndValue)) {
									// index = i;
									// break;
									// }
								}
								if (codeName.equals(keyAndValue)) {
									index = i;
									break;
								}
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					} else {
						ComboBoxModel model = this.comboBox.getModel();
						int size = model.getSize();
						for (int i = 0; i < size; i++) {
							itemCode = model.getElementAt(i).toString();
							codeName = (itemCode == null ? "" : itemCode);
							if (codeName.equals(keyAndValue)) {
								index = i;
								break;
							}
						}
					}
				}
			}
			if (this.comboBox.getSelectedIndex() < 0 && index < 0) {
				editor.setText("");
			}
			this.comboBox.setSelectedIndex(index);
		}

		private String getSelectKeyAndValue() {
			String str = "";
			// System.out.println("---comboBox.getItemCount():"+comboBox.getItemCount());
			if (comboBox.getSelectedItem() == null
					&& (comboBox.getItemCount() != 1 || "".equals(editor
							.getText().trim())
							&& model.getSize() <= 1)) {
				// System.out.println("---return 1");
				return str;
			}
			Object obj = null;
			if (comboBox.getSelectedItem() == null) {
				// System.out.println("---return 3");
				obj = comboBox.getModel().getElementAt(0);
			} else {
				// System.out.println("---return 4");
				obj = comboBox.getSelectedItem();
			}
			if (obj == null) {
				// System.out.println("---return 2");
				return str;
			}
			if (obj instanceof CustomBaseEntity) {
				CustomBaseEntity entity = (CustomBaseEntity) obj;
				str = (entity.getCode() == null ? "" : entity.getCode())
						+ (entity.getName() == null ? "" : entity.getName());
			} else if (obj instanceof ItemProperty) {
				ItemProperty item = (ItemProperty) obj;
				str = (item.getCode() == null ? "" : item.getCode())
						+ (item.getName() == null ? "" : item.getName());
			} else {
				if ((codeField != null) && (!codeField.equals(""))
						|| (nameField != null) && (!nameField.equals(""))) {
					try {
						if ((codeField != null) && (!codeField.equals(""))) {
							String itemCode = BeanUtils.getProperty(obj,
									codeField);
							str = (itemCode == null ? "" : itemCode);
						}
						if ((nameField != null) && (!nameField.equals(""))) {
							String itemName = BeanUtils.getProperty(obj,
									nameField);
							str += (itemName == null ? "" : itemName);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				} else {
					str = obj.toString();
				}
			}
			return str;
		}
	}

}