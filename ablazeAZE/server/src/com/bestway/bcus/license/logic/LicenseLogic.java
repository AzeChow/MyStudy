/*
 * Created on 2004-12-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license.logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcus.license.ClientInfoCache;
import com.bestway.bcus.license.LicenseInfoCache;
import com.bestway.bcus.license.entity.ClientInfo;
import com.bestway.bcus.license.entity.LicenseDetailInfo;
import com.bestway.bcus.license.entity.LicenseInfo;
import com.bestway.bcus.license.entity.TempLicenseInfo;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.common.CommonUtils;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class LicenseLogic {

	/**
	 * 请求刷新在服务器端注册的客户端信息(客户端每5分钟刷新一次) 如果某个客户端的信息超过10分钟没有刷新,则删除
	 * 
	 * @param clientInfo
	 */
	public void refreshLoginedClientInfo(ClientInfo clientInfo) {
		clientInfo.setLastCallTime(new Date());
		ClientInfoCache.addClientInfo(clientInfo);
		List list = ClientInfoCache.getClientInfo();
		int size = list.size();
		for (int i = size - 1; i >= 0; i--) {
			ClientInfo info = (ClientInfo) list.get(i);
			long beginTime = info.getLastCallTime().getTime();
			long endTime = (new Date()).getTime();
			long deff = endTime - beginTime;
			if (deff > 600000) {
				list.remove(i);
			}
		}
	}

	/**
	 * 请求通过licese验证(在客户端登陆时)
	 * 
	 * @param clientInfo
	 * @return
	 */
	public ClientInfo loginClientInfo(ClientInfo clientInfo) {
		clientInfo.setClientIP(CommonUtils.getIp());
		int result = checkLicense(clientInfo);
		if (result > -1) {
			clientInfo.setClientLoginTime(new Date());
			clientInfo.setLastCallTime(new Date());
			ClientInfoCache.addClientInfo(clientInfo);
			return clientInfo;
		} else {
			return null;
		}
	}

	/**
	 * 判断是否有Llicense文件
	 * 
	 * @param clientInfo
	 * @return
	 */
	public boolean checkHasLicense(ClientInfo clientInfo) {
		clientInfo.setClientIP(CommonUtils.getIp());
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		LicenseDetailInfo detail = null;
		if (clientInfo.getCompanyName() != null && licenseInfo != null
				&& licenseInfo.getHmDetailInfo() != null) {
			detail = (LicenseDetailInfo) licenseInfo.getHmDetailInfo().get(
					clientInfo.getCompanyName());
		}
		if (licenseInfo == null || detail == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 请求通过licese验证(在客户端登陆时)
	 * 
	 * @param clientInfo
	 * @return
	 */
	public int checkLicense(ClientInfo clientInfo) {
		clientInfo.setClientIP(CommonUtils.getIp());
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		LicenseDetailInfo detail = null;
		int clientNum = 1;
		Map<String, Set<ClientInfo>> hm = this.getLoginedInfoGroup();
		Set<ClientInfo> clientSet = hm.get(clientInfo.getCompanyName());
		int loginedNum = (clientSet == null ? 0 : clientSet.size());
		if (clientSet != null && clientSet.contains(clientInfo)) {
			loginedNum--;
			// System.out.println("sdddddddddddddddddddrrrrrrrrrrrrrr");
		}
		// System.out.println(clientInfo.getClientIP()+"----"+clientInfo.getCompanyName());
		if (clientInfo.getCompanyName() != null && licenseInfo != null
				&& licenseInfo.getHmDetailInfo() != null) {
			detail = (LicenseDetailInfo) licenseInfo.getHmDetailInfo().get(
					clientInfo.getCompanyName());
			if (detail != null) {
				Boolean isProbationalEdition = detail.getIsProbationalEdition();
				if (isProbationalEdition != null) {
					if (isProbationalEdition.booleanValue()
							&& detail.getMaturityDate() != null) {
						long maturityTime = detail.getMaturityDate().getTime();
						long nowTime = (new Date()).getTime();
						if ((nowTime - maturityTime) > 0) {
							return LicenseInfo.DATE_OUT;
						}
					}
				}
				if (detail.getClientNum() != null) {
					clientNum = detail.getClientNum().intValue();
				}
			}
		}
		boolean isClientNumOut = (loginedNum >= clientNum);
		if (isClientNumOut) {
			if (licenseInfo == null || detail == null) {
				return LicenseInfo.NO_LICENSE;
			} else {
				return LicenseInfo.CLINT_NUM_OUT;
			}
		} else {
			return LicenseInfo.PASSED;
		}
	}

	/**
	 * 请求删除在服务器端注册的客户端信息(在客户端退出时)
	 * 
	 * @param clientInfo
	 */
	public void removeLoginedClientInfo(ClientInfo clientInfo) {
		clientInfo.setClientIP(CommonUtils.getIp());
		ClientInfoCache.removeClientInfo(clientInfo);
	}

	/**
	 * 取得license信息
	 * 
	 * @return
	 */
	public LicenseInfo getLicenseInfo() {
		return LicenseInfoCache.getInstance().getLicenseInfo();
	}

	/**
	 * 有给客户授权的模块
	 * 
	 * @return
	 */
	public List getHasModules(String companyName) {
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		if (licenseInfo == null) {
			return new ArrayList();
		}
		LicenseDetailInfo detail = null;
		if (licenseInfo.getHmDetailInfo() != null) {
			detail = (LicenseDetailInfo) licenseInfo.getHmDetailInfo().get(
					companyName);
		}
		if (detail != null && detail.getHasModules() != null) {
			return detail.getHasModules();

		}
		return new ArrayList();
	}

	/**
	 * 检查是否有权利使用深加工结转的内部管理功能
	 * 
	 * @return
	 */
	public boolean checkFptManagePermisson(String companyName) {
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		if (licenseInfo == null) {
			return true;
		}
		LicenseDetailInfo detail = null;
		if (licenseInfo.getHmDetailInfo() != null) {
			detail = (LicenseDetailInfo) licenseInfo.getHmDetailInfo().get(
					companyName);
		}
		if (detail != null && detail.getHasModules() != null) {
			return detail.getFptManageType() == 0;

		}
		return true;
	}

	/**
	 * 检查是否有权利使用DB导入接口
	 * 
	 * @return
	 */
	public boolean checkDBImportPermisson(String companyName) {
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		if (licenseInfo == null) {
			return true;
		}
		LicenseDetailInfo detail = null;
		if (licenseInfo.getHmDetailInfo() != null) {
			detail = (LicenseDetailInfo) licenseInfo.getHmDetailInfo().get(
					companyName);
		}
		if (detail != null && detail.getHasModules() != null) {
			return (detail.getDataImportType() == 0 || detail
					.getDataImportType() == 1);

		}
		return true;
	}

	/**
	 * 检查是否有权利使用文本导入接口
	 * 
	 * @return
	 */
	public boolean checkFileImportPermisson(String companyName) {
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		if (licenseInfo == null) {
			return true;
		}
		LicenseDetailInfo detail = null;
		if (licenseInfo.getHmDetailInfo() != null) {
			detail = (LicenseDetailInfo) licenseInfo.getHmDetailInfo().get(
					companyName);
		}
		if (detail != null && detail.getHasModules() != null) {
			return (detail.getDataImportType() == 0 || detail
					.getDataImportType() == 2);

		}
		return true;
	}

	class LicenseFileFilter implements FileFilter {
		private List list = new ArrayList();

		LicenseFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}
	}

	private void removeLicenseFile() {
		File file = new File(".");// new File("license.lns");
		File[] files = file.listFiles(new LicenseFileFilter("lns"));
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				File lnsFile = files[i];
				lnsFile.delete();
			}
		}
	}

	/**
	 * 从客户端上传license文件到服务器端
	 * 
	 * @param fileContent
	 */
	public void uploadLicenseFileToServer(byte[] fileContent) {
		removeLicenseFile();
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(fileContent));
		} catch (IOException e1) {
			throw new RuntimeException(e1.getMessage());
		}
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(new File(
					"license.lns")));
			out.writeObject(in.readObject());
			out.writeObject(in.readObject());
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e2) {
				throw new RuntimeException(e2.getMessage());
			}
		}
		LicenseInfoCache.getInstance().refreshLicenseInfo();
	}

	private String formatStringByLength(String s, int len) {
		if (s == null) {
			s = "";
		}
		int m = len - s.length();
		for (int i = 0; i < m; i++) {
			s += " ";
		}
		return s;
	}

	private Map<String, Set<ClientInfo>> getLoginedInfoGroup() {
		List list = ClientInfoCache.getClientInfo();
		Map<String, Set<ClientInfo>> hm = new HashMap<String, Set<ClientInfo>>();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			ClientInfo info = (ClientInfo) list.get(i);
			Set<ClientInfo> clientSet = hm.get(info.getCompanyName());
			if (clientSet == null) {
				clientSet = new HashSet<ClientInfo>();
			}
			clientSet.add(info);
			hm.put(info.getCompanyName(), clientSet);
		}
		return hm;
	}

	/**
	 * 获取注册的license详细信息
	 * 
	 * @return
	 */
	public List getLicenseInfoString() {
		List<TempLicenseInfo> lsResult = new ArrayList<TempLicenseInfo>();
		LicenseInfo licenseInfo = LicenseInfoCache.getInstance()
				.getLicenseInfo();
		if (licenseInfo == null) {
			return lsResult;
		} else {
			List lsCompanies = licenseInfo.getCompanies();
			if (lsCompanies == null) {
				return lsResult;
			}
			for (int i = 0; i < lsCompanies.size(); i++) {
				TempLicenseInfo temp = new TempLicenseInfo();
				String companyName = lsCompanies.get(i).toString();
				temp.setCompanyName(companyName);
				Map mDetails = licenseInfo.getHmDetailInfo();
				if (mDetails != null) {
					LicenseDetailInfo detail = (LicenseDetailInfo) mDetails
							.get(companyName);
					if (detail != null) {
						if (detail.getIsProbationalEdition() != null) {
							if (detail.getIsProbationalEdition().booleanValue()) {
								temp.setEdition("试用版");
								if (detail.getMaturityDate() != null) {
									temp.setMaturityDate(DateFormat
											.getDateInstance(DateFormat.SHORT)
											.format(detail.getMaturityDate()));
								}
							} else {
								temp.setEdition("正式版");
							}
						}
						if (detail.getClientNum() != null) {
							temp.setMaxNum(detail.getClientNum().toString());
						}
					}
				}
				Map<String, Set<ClientInfo>> hm = this.getLoginedInfoGroup();
				Set<ClientInfo> clientSet = hm.get(companyName);
				String currentNum = "0";
				String loginedIP = "";
				if (clientSet != null) {
					currentNum = String.valueOf(clientSet.size());
					List lsTemp = new ArrayList();
					lsTemp.addAll(clientSet);
					for (int j = 0; j < lsTemp.size(); j++) {
						ClientInfo clientInfo = (ClientInfo) lsTemp.get(j);
						loginedIP += (clientInfo.getClientIP() + ";");
					}
				}
				temp.setLoginedNum(currentNum);
				temp.setLoginedIP(loginedIP);
				lsResult.add(temp);
			}
		}
		return lsResult;
	}
}