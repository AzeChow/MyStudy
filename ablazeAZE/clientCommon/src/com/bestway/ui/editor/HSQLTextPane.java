package com.bestway.ui.editor;

public class HSQLTextPane extends BaseTextPane {

	private HSQLEditorDocument	document	= null;

	public HSQLTextPane() {
		super();
		document = new HSQLEditorDocument();
		document.setKeyMap(Utils.HSQL_KEYS);
		this.setDocument(document);	
	}

	public String getText() {
		//System.out.println(document.getSbText().toString().trim());
		return document.getSbText().toString().trim();
	}

}
