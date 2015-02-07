package com.bestway.client.util;

import java.util.List;

public interface RenderDataColumnInterface {
	/**
	 * 获得
	 * @param className
	 * @param companyId
	 * @param key
	 * @return
	 */
	public List<RenderDataColumnItem> getRenderDataColumnItem(String className,
			 String key);

	/**
	 * 保存
	 * @param list
	 */
	public void saveRenderDataColumn(String className,
			 String key,List<RenderDataColumnItem> list);
}
