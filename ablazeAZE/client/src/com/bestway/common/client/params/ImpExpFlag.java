package com.bestway.common.client.params;

/**
 * 进出口标记
 */
public enum ImpExpFlag {
	// 进口标志
	IMPORT(0),
	// 出口标志
	EXPORT(1),
	// 特殊报关标志
	SPECIAL(2);

	private int value;

	private ImpExpFlag(int _value) {

		value = _value;

	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
