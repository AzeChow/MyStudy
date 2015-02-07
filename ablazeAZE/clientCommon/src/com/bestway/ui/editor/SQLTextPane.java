package com.bestway.ui.editor;

public class SQLTextPane extends BaseTextPane {

	private SQLEditorDocument	document	= null;

	public SQLTextPane() {
		super();
		document = new SQLEditorDocument();
		document.setKeyMap(Utils.SQL_KEYS);
		this.setDocument(document);
	}

	public String getText() {
//		System.out.println(document.getSbText().toString().trim());
		return document.getSbText().toString().trim();
	}

}
