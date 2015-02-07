/*
 * Created on 2006-1-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.tools.entity;

import java.io.Serializable;

/**
 * 实体信息
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EntityInfo implements Serializable {
	/**
	 * 名称
	 */
  private String Name;
//  private String LongName;
  /**
   * 取得数量
   */
  private long FetchCount;
  /**
   * 载入数量
   */
  private long LoadCount;
  /**
   * 插入数量
   */
  private long InsertCount;
  /**
   * 删除数量
   */
  private long DeleteCount;
  /**
   * 更新数量
   */
  private long UpdateCount;
  /**
   * 
   */
  private long OptimisticFailureCount;
/**
 * @return Returns the optimisticFailureCount.
 */
public long getOptimisticFailureCount() {
	return OptimisticFailureCount;
}
/**
 * @param optimisticFailureCount The optimisticFailureCount to set.
 */
public void setOptimisticFailureCount(long optimisticFailureCount) {
	OptimisticFailureCount = optimisticFailureCount;
}
/**
 * @return Returns the fetchCount.
 */
public long getFetchCount() {
	return FetchCount;
}
/**
 * @param fetchCount The fetchCount to set.
 */
public void setFetchCount(long fetchCount) {
	FetchCount = fetchCount;
}
/**
 * @return Returns the loadCount.
 */
public long getLoadCount() {
	return LoadCount;
}
/**
 * @param loadCount The loadCount to set.
 */
public void setLoadCount(long loadCount) {
	LoadCount = loadCount;
}
///**
// * @return Returns the longName.
// */
//public String getLongName() {
//	return LongName;
//}
///**
// * @param longName The longName to set.
// */
//public void setLongName(String longName) {
//	LongName = longName;
//}
/**
 * @return Returns the name.
 */
public String getName() {
	return Name;
}
/**
 * @param name The name to set.
 */
public void setName(String name) {
	Name = name;
}
/**
 * @return Returns the recreateCount.
 */
public long getInsertCount() {
	return InsertCount;
}
/**
 * @param recreateCount The recreateCount to set.
 */
public void setInsertCount(long recreateCount) {
	InsertCount = recreateCount;
}
/**
 * @return Returns the removeCount.
 */
public long getDeleteCount() {
	return DeleteCount;
}
/**
 * @param removeCount The removeCount to set.
 */
public void setDeleteCount(long removeCount) {
	DeleteCount = removeCount;
}

/**
 * @return Returns the updateCount.
 */
public long getUpdateCount() {
	return UpdateCount;
}
/**
 * @param updateCount The updateCount to set.
 */
public void setUpdateCount(long updateCount) {
	UpdateCount = updateCount;
}
}
