package com.bestway.bcus.message.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.message.entity.ReceiptResult;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ModifyMarkState;

/**
 * @author xxm
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ReceiptResultDao extends BaseDao {

	public List<Object> commonSearch(String className, Hashtable<String, Object> conditions) {
		String sql = "from " + className + " a";
		Set<String> keySet = conditions.keySet();

		Object[] params = new Object[keySet.size()];

		if (keySet.size() > 0) {
			Iterator<String> it = keySet.iterator();
			int i = 0;
			String preporty = null;
			while (it.hasNext()) {
				preporty = it.next();
				if (i == 0) {
					sql += " where a." + preporty + "=?";
				} else {
					sql += " and a." + preporty + "=?";
				}
				params[i] = conditions.get(preporty);
				
				i++;
			}
		}
		List<Object> result = this.find(sql, params);

		return result;
	}
	
	/**
	 * 查询全部未修改的电子账册单耗key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findEmsBomKey() {
		StringBuilder hql = new StringBuilder();
		hql.append("select ")
			.append("	CONCAT(CONCAT(cast(a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as string), '/')," +
					"	CONCAT(CONCAT(cast(a.emsHeadH2kVersion.version as string), '/'), cast(a.seqNum as string))), " +
					"	a.id ")
			.append("from EmsHeadH2kBom a ")
			.append("where a.modifyMark = ? and a.company.id = ? ");
		return find(hql.toString(), new Object[]{ModifyMarkState.UNCHANGE, CommonUtils.getCompany().getId()});
	}
	
	/**
	 * 更新bom
	 * @param exgSeqNum
	 * @param version
	 * @param imgSeqNum
	 */
	public void updateEmsHead2kBomStateByBomKey(Object[] ids) {
		
		StringBuilder updateHql = new StringBuilder("update EmsHeadH2kBom a set a.modifyMark = ?"
				+ "where a.id in ('");
		for (int i = 0; i < ids.length; i++) {
			updateHql.append("','" + ids[i]);
		}
		updateHql.append("')");
		
		batchUpdateOrDelete(updateHql.toString(), ModifyMarkState.ADDED);
	}

	public void commonSaveObject(Object obj) {
		this.saveOrUpdate(obj);
	}

	public List findReceiptResults() {
		return this.find("from ReceiptResult");
	}

	public void saveReceiptResult(ReceiptResult receiptResult) {
		this.saveOrUpdate(receiptResult);
	}

	/**
	 * 得到未处理报文
	 */
	public List getUnprocess(String fileName, String type) {
		List list = new ArrayList();
		File file = new File(("Bill".equals(type) ? CommonUtils
				.getRecvBillDir() : CommonUtils.getRecvDir())
				+ File.separator + fileName);
		InputStream message;
		try {
			message = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件不存在！");
		}
		String line;
		try {
			InputStreamReader fr = new InputStreamReader(message, "GBK");
			LineNumberReader lnr = new LineNumberReader(fr);
			while ((line = lnr.readLine()) != null) {
				list.add(line);
			}
			fr.close();
			lnr.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return list;
	}

	/**
	 * 得到处理报文
	 */
	public List getprocess(String fileName) {
		List list = new ArrayList();
		File file = new File(CommonUtils.getFinallyDir() + File.separator
				+ fileName);
		InputStream message;
		try {
			message = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件不存在！");
		}
		String line;
		InputStreamReader fr = new InputStreamReader(message);
		LineNumberReader lnr = new LineNumberReader(fr);
		try {
			while ((line = lnr.readLine()) != null) {
				list.add(line);
			}
			fr.close();
			lnr.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return list;
	}

	
	/**
	 * 返回参数设定
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	public String getBpara(int type) {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}
}
