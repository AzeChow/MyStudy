
/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.common.MaterielType;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SortInnerMergeNo {

    private CommonBaseCodeDao commonBaseCodeDao;

    public CommonBaseCodeDao getCommonBaseCodeDao() {
        return commonBaseCodeDao;
    }

    public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
        this.commonBaseCodeDao = commonBaseCodeDao;
    }

    /**
     * 检查数据为四位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4:
     * 选择的行号在选择的行中,不能进行重排
     */
    public int checkDataForFourInnerMergeSort(List selectedRows, int toRowNumber) {
        int max = 0;
        int min = 0;
        int maxValue = 0;
        String type = "";
        if (selectedRows.size() <= 0) {
            return -1;
        }
        type = ((InnerMergeData) selectedRows.get(0)).getImrType();
        maxValue = this.commonBaseCodeDao.findFourInnerMergeNo(type);
        for (int i = 0; i < selectedRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) selectedRows.get(i);
            if (data.getHsFourNo() == null) {
                return -3;
            }
            int currentValue = data.getHsFourNo().intValue();
            if (i == 0) {
                max = currentValue;
                min = max;
                continue;
            }
            if (max < currentValue) {
                max = currentValue;
            }
            if (min > currentValue) {
                min = currentValue;
            }
        }
        if (toRowNumber > maxValue || toRowNumber <= 0) {
            return -2;
        }
        if (min <= toRowNumber && max >= toRowNumber) {
            return -4;
        }
        return 0;
    }

    /**
     * 检查数据为十位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4:
     * 选择的行号在选择的行中,不能进行重排
     */
    public int checkDataForTenInnerMergeSort(List selectedRows, int toRowNumber) {
        int max = 0;
        int min = 0;
        int maxValue = 0;
        String type = "";
        if (selectedRows.size() <= 0) {
            return -1;
        }
        type = ((InnerMergeData) selectedRows.get(0)).getImrType();
        maxValue = this.commonBaseCodeDao.findTenInnerMergeNo(type);
        for (int i = 0; i < selectedRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) selectedRows.get(i);
            if (data.getHsAfterTenMemoNo() == null) {
                return -3;
            }
            int currentValue = data.getHsAfterTenMemoNo().intValue();
            if (i == 0) {
                max = currentValue;
                min = max;
                continue;
            }
            if (max < currentValue) {
                max = currentValue;
            }
            if (min > currentValue) {
                min = currentValue;
            }
        }
        if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
            toRowNumber += 100000;
            if (toRowNumber <= 100000) {
                return -2;
            }
        }
        if (toRowNumber > maxValue || toRowNumber <= 0) {
            return -2;
        }
        if (min <= toRowNumber && max >= toRowNumber) {
            return -4;
        }
        return 0;
    }

    /**
     * 检查数据为十位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 在多个四位归并类别中不能重排 -4:
     * 重排行数超出范围
     */
    public int checkTenInnerMergeDataInFourMerge(List selectedRows,
            int toRowNumber) {
        int min = 0;
        int max = 0;
        int fourQuantity = 1;
        int temp = 0;
        boolean hasFourInnerData = false;
        if (selectedRows.size() <= 0) {
            return -1;
        }
        List fourList = new ArrayList();
        String type = ((InnerMergeData) selectedRows.get(0)).getImrType();
        List list = this.commonBaseCodeDao.findInnerMergeDataByType(type);
        for (int i = 0; i < selectedRows.size(); i++) {
            Integer fourMemoNo = ((InnerMergeData) selectedRows.get(i))
                    .getHsFourNo();
            for (int j = 0; j < list.size(); j++) {
                InnerMergeData data = (InnerMergeData) list.get(j);
                if (data.getHsFourNo() == null) {
                    if (fourMemoNo == null
                            && data.getHsAfterTenMemoNo() != null) {
                        fourList.add(data);
                        list.remove(j);
                        j--;
                    }
                    continue;
                }
                if (data.getHsFourNo().equals(fourMemoNo)) {
                    fourList.add(data);
                    list.remove(j);
                    j--;
                    hasFourInnerData = true; //说明有四们归并数据
                }
            }
        }
        if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
            toRowNumber += 100000;
        }
        for (int i = 0; i < fourList.size(); i++) {
            InnerMergeData data = (InnerMergeData) fourList.get(i);
            if (data.getHsAfterTenMemoNo() == null) {
                continue;
                //return -2;//选中的有空行,不能重排
            }
            int tenCurrentValue = data.getHsAfterTenMemoNo().intValue();
            int fourCurrentValue = 0;
            if (data.getHsFourNo() == null) {
                fourCurrentValue = Integer.MAX_VALUE;
            } else {
                fourCurrentValue = data.getHsFourNo().intValue();
            }
            if (i == 0) {
                min = tenCurrentValue;
                max = tenCurrentValue;
                temp = fourCurrentValue;
                continue;
            }
            if (temp != fourCurrentValue) {
                fourQuantity++;
            }
            if (max < tenCurrentValue) {
                max = tenCurrentValue;
            }
            if (min > tenCurrentValue) {
                min = tenCurrentValue;
            }
        }
        if (fourQuantity > 1) {
            return -3;//在多个四位归并类别中不能重排
        }
        //if (hasFourInnerData) { //有四位归并数据时才叛乱下面这个条件
            if (toRowNumber > max || toRowNumber < min) {
                return -4;//重排行数超出范围
            }
        //}
        return 0;
    }

    private boolean inSelectedNo(int[] selectedNoes, int no) {
        for (int i = 0; i < selectedNoes.length; i++) {
            if (selectedNoes[i] == no) {
                return true;
            }
        }
        return false;
    }

    private boolean inSelectedNo(Object[] selectedNoes, int no) {
        int[] temp = new int[selectedNoes.length];
        for (int i = 0; i < selectedNoes.length; i++) {
            temp[i] = Integer.parseInt(selectedNoes[i].toString());
        }
        return inSelectedNo(temp, no);
    }

    private int getMaxTenNo(Hashtable ht, int toNo) {
        if (ht.get(Integer.valueOf(toNo).toString() + "max") == null) {
            int m = toNo - 1;
            if (m < 0) {
                return -1;
            }
            return getMaxTenNo(ht, m);
        } else {
            return Integer
                    .valueOf(
                            ht.get(Integer.valueOf(toNo).toString() + "max")
                                    .toString()).intValue();
        }

    }

    private int getMinTenNo(Hashtable ht, int toNo) {
        if (ht.get(Integer.valueOf(toNo).toString() + "min") == null) {
            int m = toNo + 1;
            if (m > ht.size() + 100) {
                return -1;
            }
            return getMinTenNo(ht, m);
        } else {
            return Integer
                    .valueOf(
                            ht.get(Integer.valueOf(toNo).toString() + "min")
                                    .toString()).intValue();
        }

    }

    public void reSortMergeFourNo(List selectedRows, List allRows, int toNo) {
        int offset = 0;
        int[] selectedNo = null;
        int minFourNo = 0;//获取选择行中的最小4位序号。
        int maxFourNo = 0;//获取选择行中的最大4位序号。
        int minTenNo = -1;//获取选择行中的最小10位序号。
        int maxTenNo = -1;//获取选择行中的最大10位序号。
        int selectedRowNum = 0;
        int intTemp = -1;//临时变量。为取得所选择的4位序号时候做比较用。
        int n = 0;//临时变量，为取得所选择的4位序号时候做hashtable的键值。
        String materielType = "";
        Hashtable ht = new Hashtable();
        Hashtable htTenNo = new Hashtable();
        for (int i = 0; i < selectedRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) selectedRows.get(i);
            if (i == 0) {
                minFourNo = data.getHsFourNo().intValue();
                maxFourNo = data.getHsFourNo().intValue();
                ht.put(Integer.valueOf(n++), data.getHsFourNo());
                intTemp = data.getHsFourNo().intValue();
                materielType = data.getImrType();
                continue;
            }
            if (intTemp != data.getHsFourNo().intValue()) {
                ht.put(Integer.valueOf(n++), data.getHsFourNo());
                intTemp = data.getHsFourNo().intValue();
            }
            if (minFourNo > data.getHsFourNo().intValue()) {
                minFourNo = data.getHsFourNo().intValue();
            }
            if (maxFourNo < data.getHsFourNo().intValue()) {
                maxFourNo = data.getHsFourNo().intValue();
            }
        }
        //取得偏移量
        offset = minFourNo - toNo;
        if (offset < 0) {
            offset = maxFourNo - toNo;
        }
        selectedRowNum = ht.size();
        selectedNo = new int[selectedRowNum];
        for (int i = 0; i < selectedRowNum; i++) {
            selectedNo[i] = Integer.valueOf(
                    ht.get(Integer.valueOf(i)).toString()).intValue();
        }
        for (int i = 0; i < allRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) allRows.get(i);
            if (data.getHsFourNo() == null) {
                continue;
            }
            if (data.getHsFourNo().equals(Integer.valueOf(toNo))) {
                if (minTenNo < 0) {
                    minTenNo = data.getHsAfterTenMemoNo().intValue();
                }
                if (maxTenNo < 0) {
                    maxTenNo = data.getHsAfterTenMemoNo().intValue();
                }
                if (minTenNo > data.getHsAfterTenMemoNo().intValue()) {
                    minTenNo = data.getHsAfterTenMemoNo().intValue();
                }
                if (maxTenNo < data.getHsAfterTenMemoNo().intValue()) {
                    maxTenNo = data.getHsAfterTenMemoNo().intValue();
                }
            } else {
                if (htTenNo.get(data.getHsFourNo().toString() + "max") == null) {
                    htTenNo.put(data.getHsFourNo().toString() + "max", data
                            .getHsAfterTenMemoNo());
                    htTenNo.put(data.getHsFourNo().toString() + "min", data
                            .getHsAfterTenMemoNo());
                } else {
                    int maxAfterTenMemoNo = Integer.parseInt(htTenNo.get(
                            data.getHsFourNo().toString() + "max").toString());
                    int minAfterTenMemoNo = Integer.parseInt(htTenNo.get(
                            data.getHsFourNo().toString() + "min").toString());
                    if (maxAfterTenMemoNo < data.getHsAfterTenMemoNo()
                            .intValue()) {
                        htTenNo.put(data.getHsFourNo().toString() + "max", data
                                .getHsAfterTenMemoNo());
                    }
                    if (minAfterTenMemoNo > data.getHsAfterTenMemoNo()
                            .intValue()) {
                        htTenNo.put(data.getHsFourNo().toString() + "min", data
                                .getHsAfterTenMemoNo());
                    }
                }
            }
            int currentNo = data.getHsFourNo().intValue();
            if (inSelectedNo(selectedNo, currentNo)) {
                data.setHsFourNo(Integer.valueOf(currentNo - offset));
                commonBaseCodeDao.saveInnerMergeData(data);
            } else {
                if (offset > 0) {
                    if (currentNo >= toNo && currentNo < minFourNo) {
                        data.setHsFourNo(Integer.valueOf(currentNo
                                + selectedRowNum));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }

                } else {
                    if (currentNo <= toNo && currentNo > maxFourNo) {

                        data.setHsFourNo(Integer.valueOf(currentNo
                                - selectedRowNum));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }
                }
            }
        }

        sortTenInner(allRows, 1, false);

    }

    /**
     * 1。 取得所有行的值。
     * 
     * @param allRows
     * @return
     */
    private int[] getAllTenMergeRows(List allRows) {
        int[] result = new int[allRows.size()];
        InnerMergeData data = null;
        for (int i = 0; i < allRows.size(); i++) {
            data = (InnerMergeData) allRows.get(i);
            if (data.getHsAfterTenMemoNo() != null) {
                result[i] = data.getHsAfterTenMemoNo().intValue();
            }
        }
        Arrays.sort(result);
        //		ArrayUtils.in;
        return result;
    }

    /**
     * 2。取得所有被选种的行
     * 
     * @param selectedRows
     * @return
     */
    private int[] getSelectedTenMergeRows(List selectedRows) {
        int[] result = new int[selectedRows.size()];
        InnerMergeData data = null;
        for (int i = 0; i < selectedRows.size(); i++) {
            data = (InnerMergeData) selectedRows.get(i);
            if (data.getHsAfterTenMemoNo() != null) {
                result[i] = data.getHsAfterTenMemoNo().intValue();
            }
        }
        Arrays.sort(result);
        return result;
    }

    private int[][] getSelectedRowsIndex(int[] selectedRowsTenNo, int[] allRows) {
        int[][] result = new int[selectedRowsTenNo.length][2];
        for (int i = 0; i < selectedRowsTenNo.length; i++) {
            result[i][0] = selectedRowsTenNo[i];
            result[i][1] = ArrayUtils.indexOf(allRows, selectedRowsTenNo[i]);
        }
        return result;
    }

    private Object getInnerMergeDataByTenNo(List selectedRows, int tenNo) {
        InnerMergeData data = null;
        for (int i = 0; i < selectedRows.size(); i++) {
            data = (InnerMergeData) selectedRows.get(i);
            if (data.getHsAfterTenMemoNo().intValue() == tenNo) {
                return data;
            }
        }
        return data;
    }

    private int[] allTenMergeRows   = null;

    private int[] selectedRowsTenNo = null;

    private List[] getBlockFromSelectedRows(List selectedRows, List allRows) {
        allTenMergeRows = getAllTenMergeRows(allRows);
        selectedRowsTenNo = getSelectedTenMergeRows(selectedRows);
        int[][] selectedRowsIndex = getSelectedRowsIndex(selectedRowsTenNo,
                allTenMergeRows);
        int currentIndex = -1;
        int n = 0;
        for (int i = 0; i < selectedRowsIndex.length; i++) {
            if (i == 0) {
                currentIndex = selectedRowsIndex[i][1];
                n++;
                continue;
            }
            if (selectedRowsIndex[i][1] - currentIndex > 1) {
                n++;
            }
            currentIndex = selectedRowsIndex[i][1];
        }
        List[] result = new List[n];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ArrayList();
        }
        n = 0;
        for (int i = 0; i < selectedRowsIndex.length; i++) {
            if (i == 0) {
                currentIndex = selectedRowsIndex[i][1];
                n++;
                result[n - 1].add(getInnerMergeDataByTenNo(selectedRows,
                        selectedRowsIndex[i][0]));
                continue;
            }
            if (selectedRowsIndex[i][1] - currentIndex > 1) {
                n++;
            }
            result[n - 1].add(getInnerMergeDataByTenNo(selectedRows,
                    selectedRowsIndex[i][0]));
            currentIndex = selectedRowsIndex[i][1];
        }
        return result;
    }

    private int getToMergerNo(int currentMergeNo, int offset, String type) {
        //		int n = 0;
        //		int currentIndex = -1;
        //		int tempNo = currentMergeNo;
        //		if (offset > 0) {
        //			currentIndex = ArrayUtils.indexOf(allTenMergeRows, tempNo);
        //			for (int i = currentIndex; i >= 0; i--) {
        //				if (allTenMergeRows[i] != tempNo) {
        //					n++;
        //					tempNo = allTenMergeRows[i];
        //					if (n == offset) {
        //						return tempNo;
        //					}
        //				}
        //			}
        //		} else {
        //			currentIndex = ArrayUtils.indexOf(allTenMergeRows, tempNo);
        //			for (int i = currentIndex; i < allTenMergeRows.length; i++) {
        //				if (allTenMergeRows[i] != tempNo) {
        //					n--;
        //					tempNo = allTenMergeRows[i];
        //					if (n == offset) {
        //						return tempNo;
        //					}
        //				}
        //			}
        //		}
        //		if (n > 0) {
        //			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
        //				return 100001;
        //			} else {
        //				return 1;
        //			}
        //		} else {
        //			return allTenMergeRows[allTenMergeRows.length-1]+(n-offset);
        //		}
        return currentMergeNo - offset;
    }

    private void reSortMergeTenNoForEachBlock(List selectedRows, List allRows,
            int offset) {
        int minNo = 0;//获取选择行中的最小序号。
        int maxNo = 0;//获取选择行中的最大序号。
        String type = "";
        for (int i = 0; i < selectedRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) selectedRows.get(i);
            if (i == 0) {
                minNo = data.getHsAfterTenMemoNo().intValue();

                maxNo = data.getHsAfterTenMemoNo().intValue();
                type = data.getImrType();

                continue;
            }

            if (minNo > data.getHsAfterTenMemoNo().intValue()) {
                minNo = data.getHsAfterTenMemoNo().intValue();
            }
            if (maxNo < data.getHsAfterTenMemoNo().intValue()) {
                maxNo = data.getHsAfterTenMemoNo().intValue();
            }
        }
        int[] selectedTenMergeRows = getSelectedTenMergeRows(selectedRows);

        int toNo = 0;
        if (offset >= 0) {
            toNo = getToMergerNo(minNo, offset, type);
        } else {
            toNo = getToMergerNo(maxNo, offset, type);
        }
        //取得偏移量
        offset = minNo - toNo;
        if (offset < 0) {
            offset = maxNo - toNo;
        }
        for (int i = 0; i < allRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) allRows.get(i);
            if (data.getHsAfterTenMemoNo() == null) {
                continue;
            }
            int currentNo = data.getHsAfterTenMemoNo().intValue();
            if (inSelectedNo(selectedTenMergeRows, currentNo)) {
                data.setHsAfterTenMemoNo(Integer.valueOf(currentNo - offset));
                commonBaseCodeDao.saveInnerMergeData(data);
            } else {
                if (offset > 0) {
                    if (currentNo >= toNo && currentNo < minNo) {
                        data.setHsAfterTenMemoNo(Integer.valueOf(currentNo
                                + selectedRows.size()));
                    }
                    commonBaseCodeDao.saveInnerMergeData(data);
                } else {
                    if (currentNo <= toNo && currentNo > minNo) {
                        data.setHsAfterTenMemoNo(Integer.valueOf(currentNo
                                - selectedRows.size()));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }
                }
            }
        }
    }

    private int getNumBetweenBeginEndIndex(int begin, int end) {
        int n = 0;
        int tempNo = -1;
        for (int i = begin; i <= end; i++) {
            if (i == begin) {
                n++;
                tempNo = allTenMergeRows[i];
                continue;
            }
            if (tempNo != allTenMergeRows[i]) {
                n++;
                tempNo = allTenMergeRows[i];
            }
        }
        return n - 1;
    }

    public void reSortMergeTenNo(List selectedRows, List allRows, int toNo) {
        if (selectedRows.size() < 1) {
            return;
        }
        List[] blockFromSelectedRows = getBlockFromSelectedRows(selectedRows,
                allRows);
        List firstBlock = blockFromSelectedRows[0];
        int offset = 0;
        int minNo = 0;//获取选择行中的最小序号。
        int maxNo = 0;//获取选择行中的最大序号。
        for (int i = 0; i < firstBlock.size(); i++) {
            InnerMergeData data = (InnerMergeData) firstBlock.get(i);
            if (i == 0) {
                minNo = data.getHsAfterTenMemoNo().intValue();
                maxNo = data.getHsAfterTenMemoNo().intValue();
                continue;
            }
            if (minNo > data.getHsAfterTenMemoNo().intValue()) {
                minNo = data.getHsAfterTenMemoNo().intValue();
            }
            if (maxNo < data.getHsAfterTenMemoNo().intValue()) {
                maxNo = data.getHsAfterTenMemoNo().intValue();
            }
        }
        //取得偏移量
        //		int toIndex = ArrayUtils.indexOf(allTenMergeRows, toNo);
        //		if (toIndex > -1) {
        //			if (ArrayUtils.indexOf(allTenMergeRows, minNo) < toIndex) {
        //				offset = -getNumBetweenBeginEndIndex(ArrayUtils.indexOf(
        //						allTenMergeRows, minNo), toIndex);
        //			} else {
        //				offset = getNumBetweenBeginEndIndex(toIndex, ArrayUtils
        //						.indexOf(allTenMergeRows, minNo));
        //			}
        //		} else {
        //			if (toNo < minNo) {
        //				int tempIndex = ArrayUtils.indexOf(allTenMergeRows, toNo);
        //				int n = 0;
        //				while (tempIndex < 0) {
        //					n++;
        //					toNo++;
        //					tempIndex = ArrayUtils.indexOf(allTenMergeRows, toNo);
        //				}
        //				offset = n
        //						+ getNumBetweenBeginEndIndex(tempIndex, ArrayUtils
        //								.indexOf(allTenMergeRows, minNo));
        //			} else {
        //				int tempIndex = ArrayUtils.indexOf(allTenMergeRows, toNo);
        //				int n = 0;
        //				while (tempIndex < 0) {
        //					n++;
        //					toNo--;
        //					tempIndex = ArrayUtils.indexOf(allTenMergeRows, toNo);
        //				}
        //				offset = -(n + getNumBetweenBeginEndIndex(ArrayUtils.indexOf(
        //						allTenMergeRows, minNo), tempIndex));
        //			}
        //		}
        offset = minNo - toNo;
        for (int i = 0; i < blockFromSelectedRows.length; i++) {
            reSortMergeTenNoForEachBlock(blockFromSelectedRows[i], allRows,
                    offset);
        }
    }

    /**
     * 十码重排
     */
    public void reSortMergeTenNo2(List selectedRows, int toNo) {
        int offset = 0;
        int[] selectedNo = null;
        int minNo = 0;//获取选择行中的最小序号。
        int maxNo = 0;//获取选择行中的最大序号。
        int selectedRowNum = 0;
        int n = 0;//临时变量，为取得所选择的4位序号时候做hashtable的键值。
        boolean isSemiFinishedProduct = false;//是否是半成品
        Hashtable ht = new Hashtable();
        Set set = new HashSet();
        List minMaxBetweenList = new ArrayList();//在选中行的之间的list

        String type = ((InnerMergeData) selectedRows.get(0)).getImrType();
        List allRows = this.commonBaseCodeDao.findInnerMergeDataByType(type);

        for (int i = 0; i < selectedRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) selectedRows.get(i);
            int currentValue = data.getHsAfterTenMemoNo().intValue();
            if (i == 0) {
                minNo = data.getHsAfterTenMemoNo().intValue();
                maxNo = minNo;
                ht.put(data.getHsAfterTenMemoNo(), data.getHsAfterTenMemoNo());
                continue;
            }
            if (ht.get(data.getHsAfterTenMemoNo()) == null) {
                ht.put(data.getHsAfterTenMemoNo(), data.getHsAfterTenMemoNo());
            }
            if (minNo > data.getHsAfterTenMemoNo().intValue()) {
                minNo = data.getHsAfterTenMemoNo().intValue();
            }
            if (maxNo < data.getHsAfterTenMemoNo().intValue()) {
                maxNo = data.getHsAfterTenMemoNo().intValue();
            }
        }
        if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
            toNo += 100000;
            isSemiFinishedProduct = true;
        }

        //取得偏移量 判断向上还是向下
        offset = minNo - toNo;

        //
        // 获得选择行的数据并按升序排列
        //
        selectedRowNum = ht.size();
        selectedNo = new int[selectedRowNum];
        int j = 0;
        for (Enumeration e = ht.elements(); e.hasMoreElements();) {
            Integer integer = (Integer) e.nextElement();
            selectedNo[j] = integer.intValue();
            j++;
        }
        Arrays.sort(selectedNo);      

        //
        // 重排记录
        //
        for (int i = 0; i < allRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) allRows.get(i);
            if (data.getHsAfterTenMemoNo() == null) {
                continue;
            }
            int currentNo = data.getHsAfterTenMemoNo().intValue();

            if (offset > 0) {// 向上重排
                if (ht.get(data.getHsAfterTenMemoNo()) != null) {//重排选中的行数据
                    for (int k = 0; k < selectedNo.length; k++) {
                        if (currentNo == selectedNo[k]) {
                            int toRowNo = toNo + k;
                            data.setHsAfterTenMemoNo(Integer.valueOf(toRowNo));
                        }
                    }
                    commonBaseCodeDao.saveInnerMergeData(data);
                } else {//重排未选中的行数据
                    if (selectedRowNum == 0) {
                        continue;
                    }
                    if (currentNo >= toNo && currentNo < minNo) {
                        data.setHsAfterTenMemoNo(Integer.valueOf(currentNo
                                + selectedRowNum));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    } else if (currentNo > minNo && currentNo < maxNo) {                       
                        minMaxBetweenList.add(data);
                    }
                }
            } else { //向下重排
                if (ht.get(data.getHsAfterTenMemoNo()) != null) {//重排选中的行数据
                    for (int k = 0; k < selectedNo.length; k++) {
                        if (currentNo == selectedNo[k]) {
                            int toRowNo = toNo - (selectedNo.length - 1 - k);
                            data.setHsAfterTenMemoNo(Integer.valueOf(toRowNo));
                        }
                    }
                    commonBaseCodeDao.saveInnerMergeData(data);
                } else {
                    if (selectedRowNum == 0) {
                        continue;
                    }
                    if (currentNo <= toNo && currentNo > maxNo) {
                        data.setHsAfterTenMemoNo(Integer.valueOf(currentNo
                                - selectedRowNum));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    } else if (currentNo > minNo && currentNo < maxNo) {                        
                        minMaxBetweenList.add(data);
                    }
                }
            }
        }
        //
        // 排列选中之间的数据记录
        //
        if (offset > 0) {// 向上重排 
            n = maxNo;
            for (int i = maxNo - 1; i > minNo; i--) {        
                boolean isFlag = false;
                for(int l= 0;l<minMaxBetweenList.size();l++){
                    InnerMergeData data = (InnerMergeData) minMaxBetweenList.get(l);
                    int currentNo = data.getHsAfterTenMemoNo().intValue();
                    if(currentNo == i){                        
                        data.setHsAfterTenMemoNo(new Integer(n));
                        commonBaseCodeDao.saveInnerMergeData(data);  
                        isFlag = true;
                    }
                }    
                if(isFlag == true){
                    n--;                   
                }
            }
        } else { //向下重排
            n = minNo;
            for (int i = minNo + 1 ; i < maxNo ; i++) {        
                boolean isFlag = false;
                for(int l= 0;l<minMaxBetweenList.size();l++){
                    InnerMergeData data = (InnerMergeData) minMaxBetweenList.get(l);
                    int currentNo = data.getHsAfterTenMemoNo().intValue();
                    if(currentNo == i){                        
                        data.setHsAfterTenMemoNo(new Integer(n));
                        commonBaseCodeDao.saveInnerMergeData(data);  
                        isFlag = true;
                    }
                }    
                if(isFlag == true){
                    n++;                   
                }
            }
        }       
    }

    /**
     * 四码重排
     * 
     * @param selectedRows
     * @param blockRows
     * @param toNo
     */
    public void reSortMergeFourNo2(List selectedRows, int toNo) {
        int offset = 0;
        int[] selectedRowsNo = null;
        int minNo = 0;//获取选择行中的最小序号。
        int maxNo = 0;//获取选择行中的最大序号。
        int selectedRowNum = 0;
        int n = 0;//临时变量，为取得所选择的4位序号时候做hashtable的键值。
        boolean isSemiFinishedProduct = false;//是否是半成品
        Hashtable ht = new Hashtable();
        Set toRows = new HashSet();

        String type = ((InnerMergeData) selectedRows.get(0)).getImrType();
        List allRows = this.commonBaseCodeDao.findInnerMergeDataByType(type);

        for (int i = 0; i < selectedRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) selectedRows.get(i);
            int currentValue = data.getHsFourNo().intValue();
            if (i == 0) {
                minNo = data.getHsFourNo().intValue();
                maxNo = minNo;
                ht.put(data.getHsFourNo(), data.getHsFourNo());
                continue;
            }
            if (ht.get(data.getHsFourNo()) == null) {
                ht.put(data.getHsFourNo(), data.getHsFourNo());
            }
            if (minNo > data.getHsFourNo().intValue()) {
                minNo = data.getHsFourNo().intValue();
            }
            if (maxNo < data.getHsFourNo().intValue()) {
                maxNo = data.getHsFourNo().intValue();
            }
        }
        if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
            //toNo += 100000;
            isSemiFinishedProduct = true;
        }

        //取得偏移量 判断向上还是向下
        offset = minNo - toNo;

        //
        // 获得选择行的数据并按升序排列
        //
        selectedRowNum = ht.size();
        selectedRowsNo = new int[selectedRowNum];
        int j = 0;
        for (Enumeration e = ht.elements(); e.hasMoreElements();) {
            Integer integer = (Integer) e.nextElement();
            selectedRowsNo[j] = integer.intValue();
            j++;
        }
        Arrays.sort(selectedRowsNo);

        //
        // selectedRowNum = selectedRowNum - (不存在 toRows 的个数)
        //
        for (int i = 0; i < allRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) allRows.get(i);
            if (data.getHsFourNo() == null) {
                continue;
            }
            int currentNo = data.getHsFourNo().intValue();
            if (offset > 0) {//向上
                if (ht.get(data.getHsFourNo()) != null) {//排列选中的行
                    for (int k = 0; k < selectedRowsNo.length; k++) {
                        if (currentNo == selectedRowsNo[k]) {
                            int toRowNo = toNo + k;
                            toRows.add(new Integer(toRowNo));
                            break;
                        }
                    }
                }
            } else {//向下
                if (ht.get(data.getHsFourNo()) != null) {//排列选中的行
                    for (int k = 0; k < selectedRowsNo.length; k++) {
                        if (currentNo == selectedRowsNo[k]) {
                            int toRowNo = toNo
                                    - (selectedRowsNo.length - 1 - k);
                            toRows.add(new Integer(toRowNo));
                            break;
                        }
                    }
                }
            }
        }
        selectedRowNum = this.commonBaseCodeDao.findExistToFourCodeCount(type,
                toRows);

        for (int i = 0; i < allRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) allRows.get(i);
            if (data.getHsFourNo() == null) {
                continue;
            }
            int currentNo = data.getHsFourNo().intValue();
            if (offset > 0) {//向上
                if (ht.get(data.getHsFourNo()) != null) {//排列选中的行
                    for (int k = 0; k < selectedRowsNo.length; k++) {
                        if (currentNo == selectedRowsNo[k]) {
                            int toRowNo = toNo + k;
                            data.setHsFourNo(Integer.valueOf(toRowNo));
                        }
                    }
                    commonBaseCodeDao.saveInnerMergeData(data);
                } else {//排列不选中的行
                    if (selectedRowNum == 0) {
                        continue;
                    }
                    if (currentNo >= toNo && currentNo < minNo) {
                        data.setHsFourNo(Integer.valueOf(currentNo
                                + selectedRowNum));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    } else if (currentNo > minNo && currentNo < maxNo) {
                        data.setHsFourNo(Integer.valueOf(currentNo
                                + (selectedRowNum - 1)));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }
                }
            } else {//向下
                if (ht.get(data.getHsFourNo()) != null) {//排列选中的行
                    for (int k = 0; k < selectedRowsNo.length; k++) {
                        if (currentNo == selectedRowsNo[k]) {
                            int toRowNo = toNo
                                    - (selectedRowsNo.length - 1 - k);

                            data.setHsFourNo(Integer.valueOf(toRowNo));
                        }
                    }
                    commonBaseCodeDao.saveInnerMergeData(data);
                } else {//排列不选中的行
                    if (selectedRowNum == 0) {
                        continue;
                    }
                    if (currentNo <= toNo && currentNo > maxNo) {
                        data.setHsFourNo(Integer.valueOf(currentNo
                                - selectedRowNum));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    } else if (currentNo > minNo && currentNo < maxNo) {
                        data.setHsFourNo(Integer.valueOf(currentNo
                                - (selectedRowNum - 1)));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }
                }
            }
        }
        forceSortForFour(allRows, 1);
        if (isSemiFinishedProduct == true) {
            sortTenInner(allRows, 100001, false);
        } else {
            sortTenInner(allRows, 1, false);
        }
    }

    /**
     * 强制 sort 为四位
     */
    private void forceSortForFour(List allRows, int sortI) {
        int[] sortArray = null;
        int n = 0;
        Hashtable hs = new Hashtable();
        List ls = new ArrayList();
        List list = new ArrayList();
        list.addAll(allRows);
        for (int i = 0; i < list.size(); i++) {
            InnerMergeData data = (InnerMergeData) list.get(i);
            if (data.getHsFourNo() == null) {
                continue;
            }
            int currentValue = data.getHsFourNo().intValue();
            if (i == 0) {
                hs.put(data.getHsFourNo(), data.getHsFourNo());
                ls.add(data.getHsFourNo());
                continue;
            }
            if (hs.get(data.getHsFourNo()) == null) {
                hs.put(data.getHsFourNo(), data.getHsFourNo());
            }
            ls.add(data.getHsFourNo());
        }

        sortArray = new int[hs.size()];
        int k = 0;
        for (Enumeration e = hs.elements(); e.hasMoreElements();) {
            Integer integer = (Integer) e.nextElement();
            sortArray[k] = integer.intValue();
            k++;
        }
        Arrays.sort(sortArray);

        for (int i = 0; i < sortArray.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                InnerMergeData data = (InnerMergeData) list.get(j);
                if (data.getHsFourNo() == null) {
                    continue;
                }
                if (data.getHsFourNo().intValue() == sortArray[i]) {
                    list.remove(j);
                    j--;
                    if (!data.getHsFourNo().equals(Integer.valueOf(sortI))) {
                        data.setHsFourNo(Integer.valueOf(sortI));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }
                }
            }
            sortI++;
        }
    }

    /**
     * 十位强制排序 isFourNoNullBefore == true是在前方显示的十位,反之在后
     */
    private void sortTenInner(List allRows, int startSortValue,
            boolean isFourNoNullBefore) {
        boolean isFirst = true;
        int n = 0;
        int[] sortArray = null;
        Hashtable hs = new Hashtable();
        List[] lists = null;
        for (int i = 0; i < allRows.size(); i++) {
            InnerMergeData data = (InnerMergeData) allRows.get(i);
            if (data.getHsFourNo() == null) {
                if (isFirst == true) {
                    hs.put("null", "null");
                    isFirst = false;
                }
                continue;
            }
            int currentValue = data.getHsFourNo().intValue();
            if (i == 0) {
                hs.put(data.getHsFourNo(), data.getHsFourNo());
                continue;
            }
            if (hs.get(data.getHsFourNo()) == null) {
                hs.put(data.getHsFourNo(), data.getHsFourNo());
            }
        }
        sortArray = new int[hs.size()];
        if (hs.get("null") != null) {
            int k = 0;
            for (Enumeration e = hs.elements(); e.hasMoreElements();) {
                Object obj = e.nextElement();
                if (obj instanceof Integer) {
                    sortArray[k] = ((Integer) obj).intValue();
                    k++;
                }
            }
            if (!isFourNoNullBefore) {
                sortArray[sortArray.length - 1] = Integer.MAX_VALUE;
            } else {
                sortArray[sortArray.length - 1] = Integer.MIN_VALUE;
            }
            Arrays.sort(sortArray);
            lists = new List[sortArray.length];
            for (int i = 0; i < lists.length; i++) {
                lists[i] = new ArrayList();
            }
            for (int i = 0; i < sortArray.length; i++) {
                for (int j = 0; j < allRows.size(); j++) {
                    InnerMergeData data = (InnerMergeData) allRows.get(j);
                    if (data.getHsFourNo() == null) {
                        if (i == 0) {
                            lists[lists.length - 1].add(data);
                        }
                        continue;
                    }
                    if (data.getHsFourNo().intValue() == sortArray[i]) {
                        lists[i].add(data);
                    }
                }
            }
        } else {
            int k = 0;
            for (Enumeration e = hs.elements(); e.hasMoreElements();) {
                sortArray[k] = ((Integer) e.nextElement()).intValue();
                k++;
            }
            Arrays.sort(sortArray);
            lists = new List[sortArray.length];
            for (int i = 0; i < lists.length; i++) {
                lists[i] = new ArrayList();
            }
            for (int i = 0; i < sortArray.length; i++) {
                for (int j = 0; j < allRows.size(); j++) {
                    InnerMergeData data = (InnerMergeData) allRows.get(j);
                    if (data.getHsFourNo() == null) {
                        continue;
                    }
                    if (data.getHsFourNo().intValue() == sortArray[i]) {
                        lists[i].add(data);
                    }
                }
            }
        }
        for (int i = 0; i < lists.length; i++) {
            startSortValue = forceSort(lists[i], startSortValue);
        }
    }

    /**
     * 强制十码 sort
     */
    private int forceSort(List list, int sortI) {
        int[] sortArray = null;
        int n = 0;
        Hashtable hs = new Hashtable();
        for (int i = 0; i < list.size(); i++) {
            InnerMergeData data = (InnerMergeData) list.get(i);
            if (data.getHsAfterTenMemoNo() == null) {
                continue;
            }
            int currentValue = data.getHsAfterTenMemoNo().intValue();
            if (i == 0) {
                hs.put(data.getHsAfterTenMemoNo(), data.getHsAfterTenMemoNo());
                continue;
            }
            if (hs.get(data.getHsAfterTenMemoNo()) == null) {
                hs.put(data.getHsAfterTenMemoNo(), data.getHsAfterTenMemoNo());
            }
        }

        sortArray = new int[hs.size()];
        int k = 0;
        for (Enumeration e = hs.elements(); e.hasMoreElements();) {
            Integer integer = (Integer) e.nextElement();
            sortArray[k] = integer.intValue();
            k++;
        }
        Arrays.sort(sortArray);

        for (int i = 0; i < sortArray.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                InnerMergeData data = (InnerMergeData) list.get(j);
                if (data.getHsAfterTenMemoNo() == null) {
                    continue;
                }
                if (data.getHsAfterTenMemoNo().intValue() == sortArray[i]) {
                    list.remove(j);
                    j--;
                    if (!data.getHsAfterTenMemoNo().equals(
                            Integer.valueOf(sortI))) {
                        data.setHsAfterTenMemoNo(Integer.valueOf(sortI));
                        commonBaseCodeDao.saveInnerMergeData(data);
                    }
                }
            }
            sortI++;
        }
        return sortI;
    }
}