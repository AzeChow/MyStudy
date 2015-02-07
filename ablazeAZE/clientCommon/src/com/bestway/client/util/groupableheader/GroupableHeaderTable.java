/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util.groupableheader;

import javax.swing.table.JTableHeader;

import com.bestway.client.util.footer.JFooterTable;

/**
 * @author bsway
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupableHeaderTable extends JFooterTable{
		
	protected JTableHeader createDefaultTableHeader() {
		return new GroupableTableHeader(columnModel);
	}
	
}
