package com.bsw.core.client.eport;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 可根据实际需求添加新的枚举类型 或者 添加新的逻辑方法
 * 
 * @author zcj 2015-3-15
 */
public enum QueryBaseList {

	UNITS(CustomBaseList.getInstance().getUnits()) {

		/**
		 * 查找匹配的单位
		 */
		@Override
		public Object findMatchName(String codition, Class<?> clz) {

			List<Unit> units = getList();

			for (Unit unit : units) {

				if (unit.getName().equals(codition)) {

					return unit;
				}
			}

			return null;
		}

	},

	// 随附单证List
	LICENSE_DOCU(CustomBaseList.getInstance().getLicensedocus()) {

		/**
		 * 查询匹配的字符串
		 * 
		 * @param 条件
		 * @param 类名
		 */
		@Override
		public String findMatchName(String codition, Class<?> clz) {

			List baseList = getList();

			for (int i = 0; i < baseList.size(); i++) {

				if (clz.equals(LicenseDocu.class)) {

					LicenseDocu licenseDocu = (LicenseDocu) baseList.get(i);

					// 迭代出来的名字
					String nameInList = licenseDocu.getName();

					if (StringUtils.isBlank(codition)) {

						return "";

					} else {

						if (nameInList.equals(codition)) {

							return licenseDocu.getCode();

						}

					}

				}

			}

			return null;
		}

	};

	private List baseList;

	private QueryBaseList(List queryList) {

		baseList = queryList;

	}

	public List getList() {

		return baseList;

	}

	// 根据实际业务 自行编写适当的 业务方法
	public abstract Object findMatchName(String codition, Class<?> clz);

}
