package com.bestway.client.windows.form;


public interface ThemeListener {
	/**
	 * com.bestway.client.windows.form.FormThemeControl
	 * public static final int	OCEAN_THEME		= 0;	// 海兰色
	 *  public static final int	STEEL_THEME		= 1;	// 铁色
	 *  public static final int	AQUA_THEME		= 2;	// 海蓝宝石
	 *  public static final int	SANDSTONE_THEME	= 3;	// 沙岩色
	 *  public static final int	EMERALD_THEME	= 4;	// 翠绿色
	 *  public static final int	CHARCOAL_THEME	= 5;	// 木炭色
     * Invoked when an action occurs.
     */
    public void actionPerformed(int style);
}
