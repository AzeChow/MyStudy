/*
 * Created on 2005-1-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

import com.bestway.common.BaseEntity;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SortingEntityClass {

	private SessionFactory sessionFactory = null;

	private Map hmClassDepth = new HashMap();

	private Map hmClassMetadata = new HashMap();

	public static SortingEntityClass sortingEntityClass = null;

	public static SortingEntityClass getInstance(SessionFactory sf) {
		if (sortingEntityClass == null) {
			sortingEntityClass = new SortingEntityClass();
			sortingEntityClass.sessionFactory = sf;
		}
		return sortingEntityClass;
	}

	/**
	 * 根据entity class的引用深度来排序entity class,将引用最深的排在最前,没有任何引用的排在最后,并且返回排序结果
	 * 取得全部class的排序
	 * 
	 * @return
	 */
	public List getSortedClassNameByRefDepth() {
		List list = initClassMetadata();
		for (int i = 0; i < list.size(); i++) {
			ClassMetadata classMetadata = (ClassMetadata) list.get(i);
			retriveClassDepth(classMetadata, 0);
		}
		List lsResult = sortResult();
		return lsResult;
	}

	/**
	 * 根据entity class的引用深度来排序entity class,将引用最深的排在最前,没有任何引用的排在最后,并且返回排序结果
	 * 取得部分class的排序
	 * 
	 * @return
	 */
	public List getSortedClassNameByRefDepth(List lsSelectedClasses) {
		List list = initClassMetadata();
		for (int i = 0; i < list.size(); i++) {
			ClassMetadata classMetadata = (ClassMetadata) list.get(i);
			if (lsSelectedClasses.contains(classMetadata.getMappedClass(
					EntityMode.POJO).getName())) {
				retriveClassDepth(classMetadata, 0);
			}
		}
		List lsResult = sortResult();
		return lsResult;
	}

	public Map getEntityClassNameMap() {
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		List list = initClassMetadata();
		for (int i = 0; i < list.size(); i++) {
			ClassMetadata classMetadata = (ClassMetadata) list.get(i);
			Class cls = classMetadata.getMappedClass(EntityMode.POJO);
			hm.put(cls.getName(), cls);
		}
		return hm;
	}

	private void retriveClassDepth(ClassMetadata classMetadata, int depth) {
		// Class cls = classMetadata.getMappedClass(EntityMode.POJO);
		Class cls = classMetadata.getMappedClass(EntityMode.POJO);
//		if (!BaseEntity.class.isAssignableFrom(cls)) {
//			return;
//		}
		/**
		 * 由于BaseCompany和Company对应于同一个表，所以恢复数据时可能造成数据的丢失(当BaseCompany在Company之后时)
		 * 所以在这里只取其一:Company,由于Company的信息比较全
		 */
//		|| cls
//				.getName()
//				.equals(
//						"com.bestway.bcus.custombase.entity.parametercode.Gbtobig")
		if (cls.getName().equals(
				"com.bestway.common.authority.entity.BaseCompany")) {
			return;
		}
		if (hmClassDepth.get(cls.getName()) == null) {
			hmClassDepth.put(cls.getName(), new Integer(depth));
		} else {
			Integer historyDepth = (Integer) hmClassDepth.get(cls.getName());
			if (historyDepth.intValue() < depth) {
				hmClassDepth.put(cls.getName(), new Integer(depth));
			}
		}
		depth++;
		Type[] propertyTypes = classMetadata.getPropertyTypes();
		int propertyNum = propertyTypes.length;
		for (int i = 0; i < propertyNum; i++) {
			Type propertyType = propertyTypes[i];
			ClassMetadata propertyClassMetadata = (ClassMetadata) hmClassMetadata
					.get(propertyType.getReturnedClass().getName());
			if (propertyClassMetadata != null) {
				retriveClassDepth(propertyClassMetadata, depth);
			}
		}
	}

	/**
	 * 初始化class元数据
	 */
	private List initClassMetadata() {
		hmClassDepth.clear();
		List list = new ArrayList();
		try {
			list.addAll(sessionFactory.getAllClassMetadata().values());
		} catch (HibernateException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ClassMetadata classMetadata = (ClassMetadata) list.get(i);
			hmClassMetadata.put(classMetadata.getMappedClass(EntityMode.POJO)
					.getName(), classMetadata);
		}
		return list;
	}

	/**
	 * 将关联的class按照层次深度排序
	 */
	private List sortResult() {
		List lsClassDepth = new ArrayList();
		Iterator iterator = hmClassDepth.keySet().iterator();
		while (iterator.hasNext()) {
			ReferenceClassDepth referenceClassDepth = new ReferenceClassDepth();
			String className = iterator.next().toString();
			referenceClassDepth.setClassName(className);
			referenceClassDepth.setDepth(Integer.parseInt(hmClassDepth.get(
					className).toString()));
			lsClassDepth.add(referenceClassDepth);
		}
		Collections.sort(lsClassDepth);
		List lsResult = new ArrayList();
//		lsResult.add("com.bestway.bcus.custombase.entity.parametercode.Wrap");
		for (int i = 0; i < lsClassDepth.size(); i++) {
			ReferenceClassDepth referenceClassDepth = (ReferenceClassDepth) lsClassDepth
					.get(i);
			String className = referenceClassDepth.getClassName();
			lsResult.add(className);
			// System.out.println(className + "------------"
			// + referenceClassDepth.getDepth());
		}
		return lsResult;
	}
}