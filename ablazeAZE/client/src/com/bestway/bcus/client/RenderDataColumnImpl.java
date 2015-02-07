package com.bestway.bcus.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.RenderDataColumn;
import com.bestway.client.util.RenderDataColumnInterface;
import com.bestway.client.util.RenderDataColumnItem;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;

public class RenderDataColumnImpl implements RenderDataColumnInterface {
	private SystemAction	systemAction	= null;

	public RenderDataColumnImpl() {
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
	}

	public List<RenderDataColumnItem> getRenderDataColumnItem(String className,
			String key) {
		String companyId = CommonVars.getCurrUser().getCompany().getId();
		List<RenderDataColumn> list = systemAction.findRenderDataColumn(
				new Request(CommonVars.getCurrUser()), className, companyId,
				key);
		if (list == null) {
			return null;
		} else {
			List<RenderDataColumnItem> returnList = new ArrayList<RenderDataColumnItem>();
			for (RenderDataColumn rdc : list) {
				RenderDataColumnItem item = new RenderDataColumnItem();
				item.setCode(rdc.getName());
				item.setName("");
				item.setSelected(rdc.getIsShow());
				returnList.add(item);
			}
			return returnList;
		}
	}

	public void saveRenderDataColumn(String className, String key,
			List<RenderDataColumnItem> list) {
		String companyId = CommonVars.getCurrUser().getCompany().getId();
		List<RenderDataColumn> returnList = systemAction.findRenderDataColumn(
				new Request(CommonVars.getCurrUser()), className, companyId,
				key);
		//
		// 新增
		//
		if (returnList == null) {
			returnList = new ArrayList<RenderDataColumn>();
			for (RenderDataColumnItem item : list) {
				RenderDataColumn rdc = new RenderDataColumn();
				rdc.setClassName(className);
				rdc.setCompany(CommonVars.getCurrUser().getCompany());
				rdc.setIsShow(item.isSelected());
				//
				// hashCode 
				//
				String hashCode = String.valueOf(CommonUtils.hash(key));
				rdc.setKey(hashCode);
				rdc.setName(item.getCode());
				returnList.add(rdc);
			}
			systemAction.saveRenderDataColumn(new Request(CommonVars
					.getCurrUser()), returnList);
		} else { // 修改
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			for (RenderDataColumnItem item : list) {
				map.put(item.getCode(), item.isSelected());
			}
			for (RenderDataColumn rdc : returnList) {
				rdc.setIsShow(map.get(rdc.getName()) == null ? false : map
						.get(rdc.getName()));
			}
			systemAction.saveRenderDataColumn(new Request(CommonVars
					.getCurrUser()), returnList);
		}

	}

}
