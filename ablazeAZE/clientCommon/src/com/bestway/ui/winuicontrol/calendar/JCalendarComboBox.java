package com.bestway.ui.winuicontrol.calendar;

/*
 * Copyright (c) 2003, Bodo Tasche (http://www.wannawork.de) All rights
 * reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. * Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 * This Class creates a ComboBox for selecting the Date. If pressed, it shows a
 * Popup that contains a JCalendarPanel.
 * 
 * You can add a ChangeListener to this ComboBox to receive change events.
 * 
 * It is possible to change the Text on the ComboBox using the
 * DateFormat-Parameter.
 * 
 * @author Bodo Tasche, David Freels
 */
public class JCalendarComboBox extends JPanel implements ActionListener,
		AncestorListener, ChangeListener, SwingConstants {
	private Date oldDate = null;

	private List<DateValueListener> dateValueListeners = new ArrayList<DateValueListener>();

	public void removeDateValueListener(DateValueListener dateValueListener) {
		if (dateValueListeners != null && dateValueListeners.size() > 0) {
			dateValueListeners.remove(dateValueListener);
		}
	}

	public void addDateValueListener(DateValueListener dateValueListener) {
		if (dateValueListeners == null) {
			dateValueListeners = new ArrayList<DateValueListener>();
		}
		dateValueListeners.add(dateValueListener);
	}

	/**
	 * Creates a Calendar using the current Date and current Local settings.
	 */
	public JCalendarComboBox() {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel();
		createGUI();
	}

	protected void printComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void printComponents(Graphics g) {
		super.paintComponents(g);
	}

	/**
	 * Creates a Calendar using the cal-Date and current Locale Settings. It
	 * doesn't use the Locale in the Calendar-Object !
	 * 
	 * @param cal
	 *            Calendar to use
	 */
	public JCalendarComboBox(Calendar cal) {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel(cal);
		createGUI();
	}

	/**
	 * Creates a Calendar using the current Date and the given Locale Settings.
	 * 
	 * @param locale
	 *            Locale to use
	 */
	public JCalendarComboBox(Locale locale) {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel(locale);
		createGUI();
	}

	/**
	 * Creates a Calender using the given Date and Locale
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 */
	public JCalendarComboBox(Calendar cal, Locale locale) {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel(cal, locale);
		createGUI();
	}

	/**
	 * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 * @param dateFormat
	 *            DateFormat for the ComboBox
	 */
	public JCalendarComboBox(Calendar cal, Locale locale, DateFormat dateFormat) {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel(cal, locale, dateFormat);
		createGUI();
	}

	/**
	 * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 * @param dateFormat
	 *            DateFormat for the ComboBox
	 * @param location
	 *            Location of the Popup (LEFT, CENTER or RIGHT)
	 */
	public JCalendarComboBox(Calendar cal, Locale locale,
			DateFormat dateFormat, int location) {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel(cal, locale, dateFormat);
		_popUpLocation = location;
		createGUI();
	}

	/**
	 * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal
	 *            Calendar to use
	 * @param locale
	 *            Locale to use
	 * @param dateFormat
	 *            DateFormat for the ComboBox
	 * @param location
	 *            Location of the Popup (LEFT, CENTER or RIGHT)
	 * @param flat
	 *            Flat Buttons for next/last Month/Year
	 */
	public JCalendarComboBox(Calendar cal, Locale locale,
			DateFormat dateFormat, int location, boolean flat) {
		_textField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		_calendarPanel = new JCalendarPanel(cal, locale, dateFormat, flat);
		_popUpLocation = location;
		createGUI();
	}

	/**
	 * Creates the GUI for the JCalendarComboBox
	 */
	private void createGUI() {
		_calendarPanel.setListenerModus(JCalendarPanel.FIRE_DAYCHANGES);
		_selected = (Calendar) _calendarPanel.getCalendar().clone();

		_calendarPanel.addChangeListener(this);
		_calendarPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new BorderLayout());
		// setLayout(new GridBagLayout());
		_textField.setFormatterFactory(getDefaultFormatterFactory());

		// _textField
		// .setEditor(new JSpinner.DateEditor(_textField,
		// ((SimpleDateFormat) _calendarPanel.getDateFormat())
		// .toPattern()));
		setValue(_selected.getTime());

		this._textField.setBorder(new EditorBorder());

		// _textField.setBorder(null);
		//     
		_textField.addFocusListener(new FocusAdapter() {
			public void focusGained(final FocusEvent e) {
				// System.out.println("Focus gained!!");
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						oldDate = (Date) _textField.getValue();
						((JTextField) e.getSource()).selectAll();
					}
				});
			}

			public void focusLost(FocusEvent e) {
				if (_textField.getText() == null
						|| _textField.getText().trim().equals("")) {
					// Date date = new Date(0);
					// _selected.setTime(date);
					// setValue(date);
					// _calendarPanel.setCalendar(_selected);
					_selected.setTime(new Date());
					setValue(null);
					_calendarPanel.setCalendar(_selected);
					fireChangeEvent();
					fireDateValueChangeEnvent(oldDate, null);
				} else {
					Date date = getDate();// null;
					String strDate = _textField.getText();
					if (checkStringIsNumber(strDate) && strDate.length() == 8) {
						String year = strDate.substring(0, 4);
						String month = strDate.substring(4, 6);
						String day = strDate.substring(6, 8);
						try {
							Calendar calendar = Calendar.getInstance();
							calendar.set(Calendar.YEAR, Integer.valueOf(year));
							calendar.set(Calendar.MONTH,
									Integer.valueOf(month) - 1);
							calendar.set(Calendar.DATE, Integer.valueOf(day));
							calendar.set(Calendar.HOUR_OF_DAY, 0);
							calendar.set(Calendar.MINUTE, 0);
							calendar.set(Calendar.SECOND, 0);
							calendar.set(Calendar.MILLISECOND, 0);
							date = calendar.getTime();
						} catch (Exception ex) {
							// date = new Date();
						}
					} else {
						try {
							String s = _textField.getText();
							if (s == null)
								throw new java.lang.IllegalArgumentException();
							int splitCode = '-';
							if (s.indexOf('/') > 0) {
								splitCode = '/';
							}
							int year;
							int month;
							int day;
							int firstDash;
							int secondDash;
							firstDash = s.indexOf(splitCode);
							secondDash = s.indexOf(splitCode, firstDash + 1);
							if ((firstDash > 0) & (secondDash > 0)
									& (secondDash < s.length() - 1)) {
								year = Integer.parseInt(s.substring(0,
										firstDash));// - 1900;
								month = Integer.parseInt(s.substring(
										firstDash + 1, secondDash)) - 1;
								day = Integer.parseInt(s
										.substring(secondDash + 1));
							} else {
								throw new java.lang.IllegalArgumentException();
							}
							Calendar calendar = Calendar.getInstance();
							calendar.set(Calendar.YEAR, year);
							calendar.set(Calendar.MONTH, month);
							calendar.set(Calendar.DATE, day);
							calendar.set(Calendar.HOUR_OF_DAY, 0);
							calendar.set(Calendar.MINUTE, 0);
							calendar.set(Calendar.SECOND, 0);
							calendar.set(Calendar.MILLISECOND, 0);
							date = calendar.getTime();
						} catch (Exception ex) {
							// date = new Date();
						}
					}
					if (date == null) {
						date = new Date();
					}
					_selected.setTime(date);
					setValue(date);
					_calendarPanel.setCalendar(_selected);
					fireChangeEvent();
					fireDateValueChangeEnvent(oldDate, date);
				}
			}

		});

		 _button = new ArrowButton(ArrowButton.SOUTH, UIManager
		 .getColor("controlShadow"), Color.BLACK, UIManager
		 .getColor("controlLtHighlight"));
		//        
		// _button = new ArrowButton(ArrowButton.SOUTH);

		Insets insets = new Insets(_button.getMargin().top, 0, _button
				.getMargin().bottom, 0);
		_button.setMargin(insets);
		_button.addActionListener(this);
		_button.setEnabled(true);

		_button.addFocusListener(new FocusAdapter() {

			/**
			 * Invoked when a component gains the keyboard focus.
			 */
			public void focusGained(FocusEvent e) {
				if (e.getOppositeComponent() != null) {
					if (e.getOppositeComponent() instanceof JComponent) {
						JComponent opposite = (JComponent) e
								.getOppositeComponent();
						if ((opposite.getTopLevelAncestor() != _calendarWindow)
								&& (!_calendarWindowFocusLost))
							_calendarWindowFocusLost = false;
					}
				}
			}
		});

		add(_textField, BorderLayout.CENTER);
		add(_button, BorderLayout.EAST);

		// GridBagConstraints c = new GridBagConstraints();
		// c.insets = new Insets(1, 1, 1, 1);
		// add(_textField,c);
		// c.insets = new Insets(1, 1, 1, 1);
		// add(_button,c);
	}

	private boolean checkStringIsNumber(String s) {
		try {
			Double.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Creates the CalendarWindow-Popup
	 */
	private void createCalendarWindow() {
		Window ancestor = (Window) this.getTopLevelAncestor();

		_calendarWindow = new JWindow(ancestor);

		JPanel contentPanel = (JPanel) _calendarWindow.getContentPane();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(_calendarPanel, BorderLayout.CENTER);

		((JComponent) ((RootPaneContainer) ancestor).getContentPane())
				.addAncestorListener(this);

		((JComponent) ((RootPaneContainer) ancestor).getContentPane())
				.addMouseListener(new MouseAdapter() {

					public void mouseClicked(MouseEvent e) {
						hideCalendar();
					}
				});

		_calendarWindow.addWindowListener(new WindowAdapter() {

			public void windowDeactivated(WindowEvent e) {
				hideCalendar();
			}
		});

		_calendarWindow.addWindowFocusListener(new WindowAdapter() {

			public void windowLostFocus(WindowEvent e) {
				if (_button.isSelected()) {
					_calendarWindowFocusLost = true;
				}

				hideCalendar();
			}
		});

		ancestor.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				hideCalendar();
			}

			public void componentMoved(ComponentEvent e) {
				hideCalendar();
			}

			public void componentShown(ComponentEvent e) {
				hideCalendar();
			}

			public void componentHidden(ComponentEvent e) {
				hideCalendar();
			}
		});
		ComponentListener list;

		_calendarWindow.pack();
	}

	/**
	 * Returns the current seleted Date as Calendar
	 * 
	 * @return current selected Date
	 */
	public Calendar getCalendar() {
		return _calendarPanel.getCalendar();
	}

	/**
	 * Sets the current selected Date
	 * 
	 * @param cal
	 *            Date to select
	 */
	public void setCalendar(Calendar cal) {
		_calendarPanel.setCalendar(cal);
		setValue(_calendarPanel.getCalendar().getTime());
	}

	/**
	 * Sets the current selected Date
	 * 
	 * @param cal
	 *            Date to select
	 */
	public void setDate(Date date) {
		if (date != null) {
			_calendarPanel.getCalendar().setTime(date);
			setValue(date);
		} else {
			date = new Date(0);
			_calendarPanel.getCalendar().setTime(date);
			setValue(date);
		}
	}

	public void setValue(Date date) {
		if (date == null || date.equals(new Date(0))) {
			_textField.setValue(null);
		} else {
			_textField.setValue(date);
		}
	}

	/**
	 * Sets the current selected Date
	 * 
	 * @param cal
	 *            Date to select
	 */
	public Date getDate() {
		Date date = (Date) _textField.getValue();
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public JTextField getJTextField() {
		return _textField;
	}

	/**
	 * Returns the JCalendarPanel that is shown in the PopUp
	 * 
	 * @return JCalendarPanel in the PopUp
	 */
	public JCalendarPanel getCalendarPanel() {
		return _calendarPanel;
	}

	/**
	 * Sets the Popup Location (Left/Right/Center)
	 * 
	 * @param location
	 */
	public void setPopUpLocation(int location) {
		_popUpLocation = location;
	}

	/**
	 * Returns the Popup Location
	 * 
	 * @return Location of the Popup
	 */
	public int getPopUpLocation() {
		return _popUpLocation;
	}

	/**
	 * In earlier Versions it sets the vertical Position of the Text in the
	 * Button, but it is not longer used!
	 * 
	 * @deprecated does nothing!!
	 * @param value
	 *            nothing
	 */
	public void setVerticalAlignment(int value) {
	}

	/**
	 * Handles the ToggleButton events for hiding / showing the PopUp
	 * 
	 * @param e
	 *            the ActionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if ((_calendarWindow != null) && (_calendarWindow.isVisible())) {
			hideCalendar();
		} else {
			showCalender();
		}
	}

	/**
	 * Hides the Calendar PopUp and fires a ChangeEvent if a change was made
	 */
	public void hideCalendar() {
		if (_calendarWindow.isVisible()) {
			oldDate = (Date) _textField.getValue();
			_calendarWindow.setVisible(false);
			if (!_calendarPanel.getCalendar().getTime().equals(
					_textField.getValue())) {
				_changed = true;
			}

			if (_changed) {

				setValue(_calendarPanel.getCalendar().getTime());
				_selected = (Calendar) _calendarPanel.getCalendar().clone();
				_changed = false;
				fireChangeEvent();
				fireDateValueChangeEnvent(oldDate, _calendarPanel.getCalendar()
						.getTime());
			}
		}
	}

	/**
	 * Shows the Calendar PopUp
	 */
	public void showCalender() {
		Window ancestor = (Window) this.getTopLevelAncestor();

		if ((_calendarWindow == null)
				|| (ancestor != _calendarWindow.getOwner())) {
			createCalendarWindow();
		}

		// Update the datefrom the spinner model
		// _textField.nextFocus();
		Date date = (Date) _textField.getValue();
		if (date == null) {
			date = new Date();
			// System.out.println("date is null");
		}
		// else{
		// System.out.println("date bbbbbbbb is "+date);
		// }
		// System.out.println("date now is "+date);
		_selected.setTime(date);
		_calendarPanel.setCalendar(_selected);

		Point location = getLocationOnScreen();

		int x;

		if (_popUpLocation == RIGHT) {
			x = (int) location.getX() + _button.getSize().width
					- _calendarWindow.getSize().width;
		} else if (_popUpLocation == CENTER) {
			x = (int) location.getX()
					+ ((_button.getSize().width - _calendarWindow.getSize().width) / 2);
		} else {
			x = (int) location.getX();
		}

		int y = (int) location.getY() + _button.getHeight();

		Rectangle screenSize = getDesktopBounds();

		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		if (x + _calendarWindow.getWidth() > screenSize.width) {
			x = screenSize.width - _calendarWindow.getWidth();
		}

		if (y + 30 + _calendarWindow.getHeight() > screenSize.height) {
			y = (int) location.getY() - _calendarWindow.getHeight();
		}

		_calendarWindow.setBounds(x, y, _calendarWindow.getWidth(),
				_calendarWindow.getHeight());
		// _calendarWindow.setBounds(x, y,
		// _calendarWindow.getWidth()>this.getWidth()?
		// _calendarWindow.getWidth():this.getWidth() ,
		// _calendarWindow.getHeight());
		_calendarWindow.setVisible(true);
	}

	/**
	 * Gets the screensize. Takes into account multi-screen displays.
	 * 
	 * @return a union of the bounds of the various screen devices present
	 */
	private Rectangle getDesktopBounds() {
		final GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		final GraphicsDevice[] gd = ge.getScreenDevices();
		final Rectangle[] screenDeviceBounds = new Rectangle[gd.length];
		Rectangle desktopBounds = new Rectangle();
		for (int i = 0; i < gd.length; i++) {
			final GraphicsConfiguration gc = gd[i].getDefaultConfiguration();
			screenDeviceBounds[i] = gc.getBounds();
			desktopBounds = desktopBounds.union(screenDeviceBounds[i]);
		}

		return desktopBounds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.AncestorListener#ancestorAdded(javax.swing.event.AncestorEvent)
	 */
	public void ancestorAdded(AncestorEvent event) {
		hideCalendar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.AncestorListener#ancestorMoved(javax.swing.event.AncestorEvent)
	 */
	public void ancestorMoved(AncestorEvent event) {
		hideCalendar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.AncestorListener#ancestorRemoved(javax.swing.event.AncestorEvent)
	 */
	public void ancestorRemoved(AncestorEvent event) {
		hideCalendar();
	}

	/**
	 * Listens to ChangeEvents of the JCalendarPanel and rembers if something
	 * was changed.
	 * 
	 * If the Day was changed, the PopUp is closed.
	 * 
	 * @param e
	 *            ChangeEvent
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		_changed = true;
		hideCalendar();
	}

	/**
	 * Adds a Changelistener to this JCalendarComboBox.
	 * 
	 * It will be called everytime the ComboBox is closed and the Date was
	 * changed
	 * 
	 * @param listener
	 *            ChangeListener
	 */
	public void addChangeListener(ChangeListener listener) {
		_changeListener.add(listener);
	}

	/**
	 * Removes a ChangeListener from this JCalendarComboBox
	 * 
	 * @param listener
	 *            listener to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		_changeListener.remove(listener);
	}

	/**
	 * Gets all ChangeListeners
	 * 
	 * @return all ChangeListeners
	 */
	public ChangeListener[] getChangeListener() {
		return (ChangeListener[]) _changeListener.toArray();
	}

	/**
	 * Fires the ChangeEvent
	 */
	protected void fireChangeEvent() {
		if (!_fireingChangeEvent) {
			_fireingChangeEvent = true;
			ChangeEvent event = new ChangeEvent(this);

			for (int i = 0; i < _changeListener.size(); i++) {
				((ChangeListener) _changeListener.get(i)).stateChanged(event);
			}

			_fireingChangeEvent = false;
		}

	}

	/**
	 * Enables/Disables the ComboBox
	 * 
	 * @param enabled
	 *            Enabled ?
	 */
	public void setEnabled(boolean enabled) {
		_textField.setEditable(enabled);
		_button.setEnabled(enabled);		
	}

	/**
	 * Is the ComboBox enabled ?
	 * 
	 * @return enabled ?
	 */
	public boolean isEnabled() {
		return _button.isEnabled();
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getDateFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes dateFormatter
	 * 
	 * @return javax.swing.text.DateFormatter
	 */
	private DateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DateFormatter();
			dateFormatter.setValueClass(Date.class);
		}
		return dateFormatter;
	}

	private void fireDateValueChangeEnvent(Date oldDate, Date newDate) {
		// System.out.println(oldDate + "--------" + newDate);
		if (oldDate == null && newDate == null) {
			return;
		} else if (oldDate != null && newDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (dateFormat.format(oldDate).equals(dateFormat.format(newDate))) {
				return;
			}
		}
		if (dateValueListeners != null && dateValueListeners.size() > 0) {
			for (int i = 0; i < dateValueListeners.size(); i++) {
				DateValueListener dateValueListener = dateValueListeners.get(i);
				if (dateValueListener != null) {
					dateValueListener.valueChanged(newDate);
				}
			}
		}
	}

	/**
	 * Where should be the Popup?
	 */
	private int _popUpLocation = LEFT;

	/**
	 * If the Window looses Focus and the ToggleButton get's it, don't popup the
	 * Window again...
	 */
	private boolean _calendarWindowFocusLost = false;

	/**
	 * Current selected Day
	 */
	private Calendar _selected;

	/**
	 * The ToggleButton for hiding/showing the JCalendarPanel
	 */
	private ArrowButton _button;

	/**
	 * The text field that holds the date
	 */
	private JFormattedTextField _textField = new JFormattedTextField();

	/**
	 * The JWindow for the PopUp
	 */
	private JWindow _calendarWindow;

	/**
	 * The JCalendarPanel inside the PopUp
	 */
	private JCalendarPanel _calendarPanel;

	/**
	 * The list of ChangeListeners
	 */
	private ArrayList _changeListener = new ArrayList();

	/**
	 * Currently firing an ChangeEvent?
	 */
	private boolean _fireingChangeEvent = false;

	/**
	 * Something changed in the JCalendarPanel ?
	 */
	private boolean _changed = false;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private DateFormatter dateFormatter = null; // @jve:decl-index=0:parse

	protected static Insets editorBorderInsets = new Insets(2, 2, 2, 0);

	class EditorBorder extends AbstractBorder {
		public void paintBorder(Component c, Graphics g, int x, int y, int w,
				int h) {
			g.translate(x, y);

			if (MetalLookAndFeel.getCurrentTheme() instanceof OceanTheme) {
				g.setColor(MetalLookAndFeel.getControlDarkShadow());
				g.drawRect(0, 0, w, h - 1);
				g.setColor(MetalLookAndFeel.getControlShadow());
				g.drawRect(1, 1, w - 2, h - 3);
			} else {
				g.setColor(MetalLookAndFeel.getControlDarkShadow());
				g.drawLine(0, 0, w - 1, 0);
				g.drawLine(0, 0, 0, h - 2);
				g.drawLine(0, h - 2, w - 1, h - 2);
				g.setColor(MetalLookAndFeel.getControlHighlight());
				g.drawLine(1, 1, w - 1, 1);
				g.drawLine(1, 1, 1, h - 1);
				g.drawLine(1, h - 1, w - 1, h - 1);
				g.setColor(MetalLookAndFeel.getControl());
				g.drawLine(1, h - 2, 1, h - 2);
			}

			g.translate(-x, -y);
		}

		public Insets getBorderInsets(Component c) {
			return editorBorderInsets;
		}
	}

	public Color getBackground() {
		if(_textField != null){
			return _textField.getBackground();
		}
		return super.getBackground();
	}

	public void setBackground(Color bg) {
		if(_textField != null){
			_textField.setBackground(bg);			
		}
		super.setBackground(bg);
	}

	public void setToolTipText(String text) {
		if(_textField != null){
			_textField.setToolTipText(text);			
		}
		super.setToolTipText(text);
	}

	public String getToolTipText() {
		if(_textField != null)
			return _textField.getToolTipText();
		return super.getToolTipText();
	}
	
}