/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.materialapply.dao.MaterialApplyDao;
import com.bestway.dzsc.materialapply.entity.MaterialApply;

/**
 * com.bestway.dzsc.dzscmanage.logic.DzscHandInnerLogic
 * 
 * @author Administrator
 */

public class DzscHandInnerLogic {

	private DzscInnerMergeDao dzscInnerMergeDao = null;

	private MaterialApplyDao materialApplyDao = null;

	/**
	 * 获取dzscDao
	 * 
	 * @return dzbaDao.
	 */
	public DzscInnerMergeDao getDzscInnerMergeDao() {
		return dzscInnerMergeDao;
	}

	/**
	 * 设置
	 * 
	 * @param dzbaDao
	 */
	public void setDzscInnerMergeDao(DzscInnerMergeDao dzbaDao) {
		this.dzscInnerMergeDao = dzbaDao;
	}

	public MaterialApplyDao getMaterialApplyDao() {
		return materialApplyDao;
	}

	public void setMaterialApplyDao(MaterialApplyDao materialApplyDao) {
		this.materialApplyDao = materialApplyDao;
	}

	/**
	 * 检查所选择的数据能否进行10位归并 如果数据有效则并且归并后的10位商品编码全部为空返回0，归并后的10位商品编码只要有一不为空返回1。；否则，
	 * 如果有编码不同的数据返回-1； 申报计量单位不同返回-2； 商品名称不同返回-3； 如果全部归并的话 返回-4；
	 * 如果选择的数据的备案序号不同返回-5。
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return int
	 */
	public int checkDataForTenMerge(List list) {

		// Complex complex = null;
		// CalUnit calUnit = null;
		// String materielName = "";
		// Integer memoNo = Integer.valueOf(-1);
		//
		// int n = 0;
		// for (int i = 0; i < list.size(); i++) {
		// DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
		// if (data.getTenComplex() != null) {
		// n++;
		// }
		// if (i == 0) {
		// complex = data.getMateriel().getComplex();
		// calUnit = data.getMateriel().getCalUnit();
		// materielName = data.getMateriel().getFactoryName();
		// memoNo = data.getHsAfterTenMemoNo();
		// continue;
		// }
		//
		// if (data.getMateriel().getComplex() != null) {
		// if (complex != null) {
		// if (!data.getMateriel().getComplex().equals(complex)) {
		// return -1;
		// }
		// }
		// complex = data.getMateriel().getComplex();
		// }
		// if (data.getMateriel().getCalUnit() != null) {
		// if (calUnit != null) {
		// if (!data.getMateriel().getCalUnit().equals(calUnit)) {
		// return -2;
		// }
		// }
		// calUnit = data.getMateriel().getCalUnit();
		// }
		// if (data.getMateriel().getFactoryName() != null
		// && !data.getMateriel().getFactoryName().equals("")) {
		// if (materielName != null && !materielName.equals("")) {
		// if (!data.getMateriel().getFactoryName().toLowerCase()
		// .equals(materielName.toLowerCase())) {
		// return -3;
		// }
		// }
		// materielName = data.getMateriel().getFactoryName();
		// }
		// if (data.getHsAfterTenMemoNo() != null) {
		// if (memoNo != null) {
		// if (!memoNo.equals(data.getHsAfterTenMemoNo())) {
		// return -5;
		// }
		// }
		// memoNo = data.getHsAfterTenMemoNo();
		// }
		// }
		// if (n == list.size()) {
		// return -4;
		// }
		// if (n > 0) {
		// return 1;
		// } else {
		// return 0;
		// }
		return 0;
	}

	/**
	 * 抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findAllDzscTenInnerMerge(String imrType, boolean isChange) {
		if (isChange) {
			List lsNotChange = this.dzscInnerMergeDao.findDzscTenInnerMerge(
					imrType, false);
			List lsChanged = this.dzscInnerMergeDao.findDzscTenInnerMerge(
					imrType, true);
			for (int i = 0; i < lsChanged.size(); i++) {
				DzscTenInnerMerge changedTenMerge = (DzscTenInnerMerge) lsChanged
						.get(i);
				for (int j = lsNotChange.size() - 1; j >= 0; j--) {
					DzscTenInnerMerge tenMerge = (DzscTenInnerMerge) lsNotChange
							.get(j);
					if (tenMerge.getTenSeqNum().equals(
							changedTenMerge.getTenSeqNum())) {
						lsNotChange.remove(j);
					}
				}
			}
			lsNotChange.addAll(lsChanged);
			return lsNotChange;
		} else {
			return this.dzscInnerMergeDao.findDzscTenInnerMerge(imrType, false);
		}
	}

	/**
	 * 抓取4码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findAllDzscFourInnerMerge(String imrType, boolean isChange) {
		if (isChange) {
			List lsNotChange = this.dzscInnerMergeDao
					.findFourInnerMergeDataByType(imrType, false);
			List lsChanged = this.dzscInnerMergeDao
					.findFourInnerMergeDataByType(imrType, true);
			for (int i = 0; i < lsChanged.size(); i++) {
				DzscFourInnerMerge changedTenMerge = (DzscFourInnerMerge) lsChanged
						.get(i);
				for (int j = lsNotChange.size() - 1; j >= 0; j--) {
					DzscFourInnerMerge tenMerge = (DzscFourInnerMerge) lsNotChange
							.get(j);
					if (tenMerge.getFourSeqNum().equals(
							changedTenMerge.getFourSeqNum())) {
						lsNotChange.remove(j);
					}
				}
			}
			lsNotChange.addAll(lsChanged);
			return lsNotChange;
		} else {
			return this.dzscInnerMergeDao.findFourInnerMergeDataByType(imrType,
					false);
		}
	}

	/**
	 * 10位商品归并
	 * 
	 * @param list
	 * @param tenInnerMerge
	 * @param isChange
	 */
	public void tenInnerMerge(List list, DzscTenInnerMerge tenInnerMerge,
			boolean isChange) {
		if (list.size() < 1) {
			return;
		}
		List<DzscTenInnerMerge> lsRmTenInnerMerge = new ArrayList<DzscTenInnerMerge>();
		Unit beforeUnit = null;
		String imrType = ((DzscInnerMergeData) list.get(0)).getImrType();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (beforeUnit == null) {
				beforeUnit = data.getMateriel().getPtUnit();
			} else {
				if (!beforeUnit.equals(data.getMateriel().getPtUnit())) {
					throw new RuntimeException("你选择的归并前物料的单位不一致，所以不能归并成同一商品");
				}
			}
		}
		if (isChange) {
			if (tenInnerMerge.getId() != null
					&& !"".equals(tenInnerMerge.getId().trim())) {
				DzscTenInnerMerge temp = this.dzscInnerMergeDao
						.findDzscTenInnerMerge(tenInnerMerge, imrType, true);
				if (temp == null) {
					temp = this.dzscInnerMergeDao
							.findDeleteDzscTenInnerMerge(tenInnerMerge);
					if (temp == null) {
						try {
							tenInnerMerge = (DzscTenInnerMerge) BeanUtils
									.cloneBean(tenInnerMerge);
							tenInnerMerge.setId(null);
							this.dzscInnerMergeDao
									.saveDzscTenInnerMerge(tenInnerMerge);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						tenInnerMerge = temp;
						tenInnerMerge
								.setTenModifyMark(ModifyMarkState.UNCHANGE);
						this.dzscInnerMergeDao
								.saveDzscTenInnerMerge(tenInnerMerge);
					}
				} else {
					if (!tenInnerMerge.equals(temp)) {
						tenInnerMerge = temp;
					}
				}
			} else {
				tenInnerMerge.setTenModifyMark(ModifyMarkState.ADDED);
				this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
			}
		} else {
			if (tenInnerMerge.getId() == null
					|| "".equals(tenInnerMerge.getId().trim())) {
				tenInnerMerge.setTenModifyMark(ModifyMarkState.ADDED);
			} else {
				DzscTenInnerMerge temp = this.dzscInnerMergeDao
						.findDzscTenInnerMerge(tenInnerMerge, imrType, true);
				if (temp != null) {
					if (ModifyMarkState.DELETED.equals(temp.getTenModifyMark())) {
						temp.setTenModifyMark(ModifyMarkState.UNCHANGE);
						this.dzscInnerMergeDao.saveDzscTenInnerMerge(temp);
					}
				} else {
					temp = this.dzscInnerMergeDao
							.findDeleteDzscTenInnerMerge(tenInnerMerge);
					if (temp != null) {
						this.deleteTenInnerMerge(temp);
					}
				}
			}
			this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		}
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() != null
					&& !lsRmTenInnerMerge.contains(data.getDzscTenInnerMerge())) {
				lsRmTenInnerMerge.add(data.getDzscTenInnerMerge());
			}
			if (isChange
					&& ModifyMarkState.UNCHANGE.equals(data
							.getBeforeModifyMark())) {
				data.setBeforeModifyMark(ModifyMarkState.MODIFIED);
			}
			data.setDzscTenInnerMerge(tenInnerMerge);
			this.dzscInnerMergeDao.saveDzscInnerMergeData(data);
		}
		if (isChange) {
			for (DzscTenInnerMerge rmTenInnerMerge : lsRmTenInnerMerge) {
				if (ModifyMarkState.ADDED.equals(rmTenInnerMerge
						.getTenModifyMark())) {
					if (this.dzscInnerMergeDao
							.findDzscInnerMergeCount(rmTenInnerMerge) <= 0) {
						this.deleteTenInnerMerge(rmTenInnerMerge);
					}
				} else {
					if (checkBeforeIsAllDelete(imrType, rmTenInnerMerge)) {
						rmTenInnerMerge
								.setTenModifyMark(ModifyMarkState.DELETED);
						this.dzscInnerMergeDao
								.saveDzscTenInnerMerge(rmTenInnerMerge);
						System.out
								.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
					} else {
						if (this.dzscInnerMergeDao
								.findDzscInnerMergeCount(rmTenInnerMerge) <= 0) {
							this.deleteTenInnerMerge(rmTenInnerMerge);
						}
					}
				}
			}
		} else {
			for (DzscTenInnerMerge rmTenInnerMerge : lsRmTenInnerMerge) {
				if (this.dzscInnerMergeDao
						.findDzscInnerMergeCount(rmTenInnerMerge) <= 0) {
					this.deleteTenInnerMerge(rmTenInnerMerge);
				}
			}
		}
	}

	/**
	 * 查询10码是否是删处状态
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	private boolean checkBeforeIsAllDelete(String imrType,
			DzscTenInnerMerge tenInnerMerge) {
		DzscTenInnerMerge temp = this.dzscInnerMergeDao.findDzscTenInnerMerge(
				tenInnerMerge, imrType, false);
		if (temp != null) {
			// List lsChange = this.dzscInnerMergeDao
			// .findDzscInnerMergeData(tenInnerMerge);
			List lsChange = this.dzscInnerMergeDao.findDzscInnerMergeData(
					imrType, true);
			// if (lsChange.size() <= 0) {
			// return true;
			// }
			List lsNotChange = this.dzscInnerMergeDao
					.findDzscInnerMergeData(temp);
			Map<String, Integer> hmChanged = new HashMap<String, Integer>();
			for (int i = 0; i < lsChange.size(); i++) {
				DzscInnerMergeData changedInnerMerge = (DzscInnerMergeData) lsChange
						.get(i);
				hmChanged
						.put(changedInnerMerge.getMateriel().getPtNo(),
								changedInnerMerge.getDzscTenInnerMerge()
										.getTenSeqNum());
			}
			for (int i = 0; i < lsNotChange.size(); i++) {
				DzscInnerMergeData notChangeInnerMerge = (DzscInnerMergeData) lsNotChange
						.get(i);
				if (hmChanged.get(notChangeInnerMerge.getMateriel().getPtNo()) == null
						|| notChangeInnerMerge.getDzscTenInnerMerge()
								.getTenSeqNum().equals(
										hmChanged.get(notChangeInnerMerge
												.getMateriel().getPtNo()))) {
					return false;
				}
			}
		} else {
			return this.dzscInnerMergeDao
					.findDzscInnerMergeCountExceptDeleted(tenInnerMerge) <= 0;
		}
		return true;
	}

	/**
	 * 10位商品归并
	 * 
	 * @param list
	 * @param tenInnerMerge
	 * @param isChange
	 */
	public List editTenInnerMerge(DzscTenInnerMerge tenInnerMerge,
			boolean isChange) {
		if (isChange) {
			tenInnerMerge.setTenModifyMark(ModifyMarkState.MODIFIED);
		}
		this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		return this.dzscInnerMergeDao.findDzscInnerMergeData(tenInnerMerge);
	}

	/**
	 * 删处10码归并
	 * 
	 * @param tenInnerMerge
	 */
	private void deleteTenInnerMerge(DzscTenInnerMerge tenInnerMerge) {
		this.dzscInnerMergeDao.deleteDzscTenInnerMerge(tenInnerMerge);
		DzscFourInnerMerge fourInnerMerge = tenInnerMerge
				.getDzscFourInnerMerge();
		if (fourInnerMerge != null) {
			if (this.dzscInnerMergeDao
					.findDzscTenInnerMergeCount(fourInnerMerge) <= 0) {
				this.dzscInnerMergeDao.deleteDzscFourInnerMerge(fourInnerMerge);
			}
		}
	}

	/**
	 * 四码修改
	 */
	public List editFourInnerMerge(DzscFourInnerMerge fourInnerMerge) {
		if (fourInnerMerge == null) {
			return new ArrayList();
		}
		this.dzscInnerMergeDao.saveDzscFourInnerMerge(fourInnerMerge);
		return this.dzscInnerMergeDao.findDzscInnerMergeData(fourInnerMerge);
	}

	/**
	 * 撤消10位归并
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	public void unDoTenInnerMerge(List list) {
		if (list.size() <= 0) {
			return;
		}
		String imrType = ((DzscInnerMergeData) list.get(0)).getImrType();
		List lsUnDoTenInnerMerge = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() != null
					&& !lsUnDoTenInnerMerge.contains(data
							.getDzscTenInnerMerge())) {
				lsUnDoTenInnerMerge.add(data.getDzscTenInnerMerge());
			}
			if (data.getStateMark().equals(DzscState.CHANGE)) {
				data.setBeforeModifyMark(ModifyMarkState.DELETED);
			} else {
				data.setDzscTenInnerMerge(null);
			}
			dzscInnerMergeDao.saveDzscInnerMergeData(data);
		}
		for (int i = 0; i < lsUnDoTenInnerMerge.size(); i++) {
			DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) lsUnDoTenInnerMerge
					.get(i);
			if (ModifyMarkState.ADDED.equals(tenInnerMerge.getTenModifyMark())) {
				if (this.dzscInnerMergeDao
						.findDzscInnerMergeCount(tenInnerMerge) <= 0) {
					// this.dzscInnerMergeDao
					// .deleteDzscTenInnerMerge(tenInnerMerge);
					this.deleteTenInnerMerge(tenInnerMerge);
				}
			} else {
				// if (this.dzscInnerMergeDao
				// .findDzscInnerMergeCountExceptDeleted(tenInnerMerge) <= 0) {
				if (checkBeforeIsAllDelete(imrType, tenInnerMerge)) {
					tenInnerMerge.setTenModifyMark(ModifyMarkState.DELETED);
					this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
				}
			}
		}
	}

	/**
	 * 检查能否进行撤消10位归并动作。 如果允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消。
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return int 允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消
	 */
	public int checkDataForUndoTenInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() != null
					&& data.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
				return -1;
			}
		}
		return 0;
	}

	/**
	 * 检查所选择的数据能否进行4位归并。 如果检查结果允许归并 返回0； 如果所选择的数据其中有没有经过10位归并的 返回 -1；
	 * 如果所选择的数据的10位商品编码的前4位不同的返回 -2； 如果所选择的已经归并过的数据有不同编码序号的 返回-3; 如果全部已归并返回-4。
	 * 
	 * @param list
	 *            是InnerMergeData型，归并数据
	 * @return int
	 */
	public int checkDataForFourInnerMerge(List list) {
		/*
		 * String frontFourData = ""; Integer memoNo = Integer.valueOf(-1); int
		 * n = 0; for (int i = 0; i < list.size(); i++) { InnerMergeData data =
		 * (InnerMergeData) list.get(i); if (data.getHsAfterComplex() == null) {
		 * return -1; } if (data.getHsFourCode() != null &&
		 * !data.getHsFourCode().trim().equals("")) { n++; }
		 * 
		 * if (i == 0) {
		 * 
		 * frontFourData = data.getHsAfterComplex().getCode().substring(0, 4);
		 * memoNo = data.getHsFourNo(); continue; } if
		 * (!frontFourData.equals(data.getHsAfterComplex().getCode()
		 * .substring(0, 4))) { return -2; } else { frontFourData =
		 * data.getHsAfterComplex().getCode().substring(0, 4); } if
		 * (data.getHsFourNo() != null) { if (memoNo != null) { if
		 * (!memoNo.equals(data.getHsFourNo())) { return -3; } } memoNo =
		 * data.getHsFourNo(); } } if (n == list.size()) { return -4; } if (n >
		 * 0) { return 1; } else { return 0; }
		 */

		return 0;
	}

	/**
	 * 4位商品归并 10位商品编码的前4位相同，并且属于同一类商品。
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @param isNew
	 *            如果为true代表list中的数据重新归并到一新类型中， 如果为false，list中的数据归并到
	 *            list中数据已有的类型中。
	 */
	public void fourInnerMerge(List list) {
		if (list.size() < 1) {
			return;
		}
		DzscInnerMergeData tendata = (DzscInnerMergeData) list.get(0);
		String type = tendata.getImrType(); // 类型
		DzscFourInnerMerge fourInnerMerge = null;
		int memoNo = dzscInnerMergeDao.findMaxInnerMergeNo(type, "fourSeqNum");
		fourInnerMerge = new DzscFourInnerMerge();
		fourInnerMerge.setFourSeqNum(Integer.valueOf(memoNo));
		fourInnerMerge.setFourComplex(tendata.getDzscTenInnerMerge()
				.getTenComplex().getCode().substring(0, 4)); // 注意电子备案归并四码后加四个"0"
		fourInnerMerge.setFourPtName(tendata.getDzscTenInnerMerge()
				.getTenPtName());
		fourInnerMerge.setFourPtSpec(tendata.getDzscTenInnerMerge()
				.getTenPtSpec());
		fourInnerMerge.setFourUnit(tendata.getDzscTenInnerMerge().getTenUnit());// 来自常用单位
		this.dzscInnerMergeDao.saveDzscFourInnerMerge(fourInnerMerge);
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() == null) {
				continue;
			}
			data.getDzscTenInnerMerge().setDzscFourInnerMerge(fourInnerMerge);
			dzscInnerMergeDao
					.saveDzscTenInnerMerge(data.getDzscTenInnerMerge());
		}
	}
	/**
	 * 补充4码归并
	 * @param list
	 * @param fourInnerMerge
	 * @return
	 */
	public void addFourInnerMerge(List list ,DzscFourInnerMerge fourInnerMerge){
		List<DzscTenInnerMerge> lsTen=new ArrayList<DzscTenInnerMerge>();
		for(int i=0;i<list.size();i++){
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if(lsTen.contains(data.getDzscTenInnerMerge())){
				continue;
			}
			DzscTenInnerMerge tenInnerMerge=data.getDzscTenInnerMerge();
			if(tenInnerMerge!=null){
				tenInnerMerge.setDzscFourInnerMerge(fourInnerMerge);
				this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
				lsTen.add(tenInnerMerge);
			}
		}
	}

	/**
	 * 撤消4位商品归并。
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	public void undoFourInnerMerge(List list) {
		List lsUndoFour = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() != null
					&& data.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
				if (!lsUndoFour.contains(data.getDzscTenInnerMerge()
						.getDzscFourInnerMerge())) {
					lsUndoFour.add(data.getDzscTenInnerMerge()
							.getDzscFourInnerMerge());
				}
				data.getDzscTenInnerMerge().setDzscFourInnerMerge(null);
				dzscInnerMergeDao.saveDzscTenInnerMerge(data
						.getDzscTenInnerMerge());
			}
		}
		for (int i = 0; i < lsUndoFour.size(); i++) {
			DzscFourInnerMerge fourInnerMerge = (DzscFourInnerMerge) lsUndoFour
					.get(i);
			if (this.dzscInnerMergeDao
					.findDzscTenInnerMergeCount(fourInnerMerge) <= 0) {
				this.dzscInnerMergeDao.deleteDzscFourInnerMerge(fourInnerMerge);
			}
		}
	}

	/**
	 * 判断是否已经归并
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return boolean true表示已经归并
	 */
	public boolean isInnerMerger(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getDzscTenInnerMerge() != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除手册归并数据
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	public void deleInnerMergerForMateriel(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getBeforeModifyMark().equals(ModifyMarkState.ADDED)
					|| data.getBeforeModifyMark().equals(
							ModifyMarkState.UNCHANGE)) {
				this.dzscInnerMergeDao.deleteDzscInnerMergeData(data);
			} else {
				data.setBeforeModifyMark(ModifyMarkState.DELETED);
				this.dzscInnerMergeDao.saveDzscInnerMergeData(data);
			}
		}
	}

	/**
	 * 删除物料的内部归并，并且设置这些物料不再用于内部归并
	 * 
	 * @param list
	 */
	public void forbidInnerMergeForMateriel(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (data.getBeforeModifyMark().equals(ModifyMarkState.ADDED)
					|| data.getBeforeModifyMark().equals(
							ModifyMarkState.UNCHANGE)) {
				this.dzscInnerMergeDao.deleteDzscInnerMergeData(data);
			} else {
				data.setBeforeModifyMark(ModifyMarkState.DELETED);
				this.dzscInnerMergeDao.saveDzscInnerMergeData(data);
			}
			MaterialApply materialApply = this.materialApplyDao
					.findMaterialApplyByMaterialId(data.getMateriel().getId());
			if (materialApply != null) {
				materialApply.setIsForbidMerge(true);
				this.materialApplyDao.saveMaterialApply(materialApply);
			}
		}
	}

	/**
	 * 转抄归并数据
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	public void copyInnerMergerForMateriel(List list) {
		if (list.size() < 1) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			DzscInnerMergeData copyData = new DzscInnerMergeData();
			copyData.setImrType(data.getImrType());
			// copyData.setSeqNum(Integer.valueOf(dzbaDao.findInnerMergeNo(data.getImrType(),data.getHead(),"seqNum")));
			// copyData.setMateriel(data.getMateriel());
			// copyData.setPtNo(data.getPtNo());
			// copyData.setFactoryName(data.getFactoryName());
			// copyData.setFactorySpec(data.getFactorySpec());
			// copyData.setUnit(data.getUnit());
			// copyData.setPtPrice(data.getPtPrice());
			copyData.setCompany(CommonUtils.getCompany());
			copyData.setBeforeModifyMark(DzscState.APPLY);
			dzscInnerMergeDao.saveDzscInnerMergeData(copyData);
		}
	}

	public List findInnerMergedTenSeqNum(String meterialYype, String sFields,
			Object obj) {
		// int size = 0;
		// ArrayList resultList = new ArrayList();
		// Set set = new HashSet();
		return dzscInnerMergeDao.findInnerMergedTenSeqNum(meterialYype,
				sFields, obj);
		// for (int i = 0; i < list.size(); i++) {
		// size = set.size();
		// set.add(((DzscInnerMergeData) list.get(i)).getDzscTenInnerMerge()
		// .getTenSeqNum());
		// if (set.size() != size) {
		// resultList.add(list.get(i));
		// }
		// }
		// return resultList;

	}

	public List findInnerMergedTenSeqNum(String type) {
		// int size = 0;
		// ArrayList resultList = new ArrayList();
		// Set set = new HashSet();
		// List list = dzscInnerMergeDao.findInnerMergedTenSeqNum(type);
		// for (int i = 0; i < list.size(); i++) {
		// size = set.size();
		// set.add(((DzscInnerMergeData) list.get(i)).getDzscTenInnerMerge()
		// .getTenSeqNum());
		// if (set.size() != size) {
		// resultList.add(list.get(i));
		// }
		// }
		// return resultList;
		return dzscInnerMergeDao.findInnerMergedTenSeqNum(type);
	}

}
