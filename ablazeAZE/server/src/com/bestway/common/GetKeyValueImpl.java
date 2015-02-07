package com.bestway.common;

import java.util.Map;

import com.bestway.common.CommonUtils.GetKeyValue;

public abstract class GetKeyValueImpl<E> implements GetKeyValue<E>{
	public Object getValue(E e) {
		return e;
	}
	public void put(E e, Map map) {
		map.put(getKey(e), getValue(e));
	}
}