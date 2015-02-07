/*
 * Created on 2004-12-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.bestway.bcus.license.entity.LicenseDetailInfo;
import com.bestway.bcus.license.entity.LicenseInfo;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LicenseInfoCache {
	private static LicenseInfoCache licenseInfoCache = null;

	private LicenseInfo licenseInfo = null;

	public static LicenseInfoCache getInstance() {
		if (licenseInfoCache == null) {
			licenseInfoCache = new LicenseInfoCache();
			Security.removeProvider("SunJCE");
			Security
					.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		return licenseInfoCache;
	}

	public void setLicenseInfo(LicenseInfo licenseInfo) {
		this.licenseInfo = licenseInfo;
	}

	public synchronized LicenseInfo getLicenseInfo() {
		File file = new File(".");// new File("license.lns");
		File[] files = file.listFiles(new LicenseFileFilter("lns"));
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				File lnsFile = files[i];
				return getLicenseInfo(lnsFile);
			}
		}
		// if (file.exists()) {
		// return getLicenseInfo(file);
		// } else {
		// return null;
		// }
		return null;
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

	// public LicenseInfo getLicenseInfo(String companyName) {
	// File file = new File("license.lns");
	// if (file.exists()) {
	// LicenseInfo info = getLicenseInfo(file);
	// if (info.getCompanies() != null
	// && info.getCompanies().contains(companyName)) {
	// return info;
	// } else {
	// return null;
	// }
	// } else {
	// return null;
	// }
	// }

	public synchronized LicenseInfo getLicenseInfo(File file) {
		// if (licenseInfo == null) {
		try {
			SAXBuilder sb = new SAXBuilder();
			Document doc = null;
			InputStream stream = this.readFromFileAndDecrpt(file);
			if (stream == null) {
				return null;
			}
			licenseInfo = new LicenseInfo();
			doc = sb.build(stream);
			Element root = doc.getRootElement();
			Element companies = root.getChild("Companies");
			if (companies != null) {
				List comapnyNames = companies.getChildren("CompanyName");
				List<String> tempList = new ArrayList<String>();
				Map<String, LicenseDetailInfo> hmDetailInfo = new HashMap<String, LicenseDetailInfo>();
				int size = comapnyNames.size();
				for (int i = 0; i < size; i++) {
					LicenseDetailInfo detail = new LicenseDetailInfo();
					Element company = (Element) comapnyNames.get(i);
					String companyName = company.getAttribute("name")
							.getValue();
					tempList.add(companyName);
					Element beginDate = company.getChild("BeginDate");
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					if (beginDate != null) {
						if (!beginDate.getText().trim().equals("")) {
							detail.setBeginDate(dateFormat.parse(beginDate
									.getText().trim()));
						}
					}

					Element maturityDate = company.getChild("MaturityDate");
					if (maturityDate != null) {
						if (!maturityDate.getText().trim().equals("")) {
							detail.setMaturityDate(dateFormat
									.parse(maturityDate.getText().trim()));
						}
					}
					Element clientNum = company.getChild("ClientNum");
					if (clientNum != null) {
						if (clientNum.getText().trim() != null) {
							detail.setClientNum(Integer.valueOf(clientNum
									.getText().trim()));
						}
					}
					Element isProbationalEditon = company
							.getChild("IsProbationalEdition");
					if (isProbationalEditon != null) {
						if (!isProbationalEditon.getText().trim().equals("")) {
							detail.setIsProbationalEdition(Boolean
									.valueOf(isProbationalEditon.getText()
											.trim()));
						}
					}
					Element fptManageType = company.getChild("FptManageType");		
					
					if (fptManageType != null
							&& !"".equals(fptManageType.getText().trim())) {				
						detail.setFptManageType(Integer.parseInt(fptManageType
								.getText().trim()));
					}
					Element dataImportType = company.getChild("DataImportType");					
					if (dataImportType != null
							&& !"".equals(dataImportType.getText().trim())) {
						detail.setDataImportType(Integer.parseInt(dataImportType
								.getText().trim()));	
					}
					Element exceptingModules = company
							.getChild("ExceptingModules");
					if (exceptingModules != null) {
						List moduleNames = exceptingModules
								.getChildren("ModuleName");
						List lsModuleNames = new ArrayList();
						int moduleSize = moduleNames.size();
						for (int m = 0; m < moduleSize; m++) {
							String moduleName = ((Element) moduleNames.get(m))
									.getText().trim();
							// System.out.println(companyName + "----------"
							// + moduleName);
							lsModuleNames.add(moduleName);
						}
						detail.setHasModules(lsModuleNames);
					}
					hmDetailInfo.put(companyName, detail);
				}
				licenseInfo.setCompanies(tempList);
				licenseInfo.setHmDetailInfo(hmDetailInfo);
			}
			Element createDate = root.getChild("CreateDate");
			if (createDate != null) {
				licenseInfo.setCreateDate(createDate.getText().trim());
			}			
			// Element macAddress=root.getChild("MacAddress");
			// if(macAddress!=null){
			// licenseInfo.setMacAddress(macAddress.getText().trim());
			// }
		} catch (Exception ex) {
			licenseInfo = null;
			ex.printStackTrace();
			throw new RuntimeException("读取注册文件出错");			
		}
		// }
		return licenseInfo;
	}

	public synchronized LicenseInfo refreshLicenseInfo() {
		this.licenseInfo = null;
		return this.getLicenseInfo();
	}

	public void encryptAndWriteToFile(String fileName, String fileContext) {
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		Key key = kg.generateKey();
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		byte[] outputArray = null;
		try {
			outputArray = fileContext.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e5) {
			e5.printStackTrace();
		}
		try {
			outputArray = cipher.doFinal(outputArray);
			fileContext = (new BASE64Encoder()).encode(outputArray);
			outputArray = (new BASE64Decoder()).decodeBuffer(fileContext);
		} catch (IllegalBlockSizeException e3) {
			e3.printStackTrace();
		} catch (BadPaddingException e3) {
			e3.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(fileName));
			out.writeObject(outputArray);
			out.writeObject(key);
			out.flush();
			out.close();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
	}

	private InputStream readFromFileAndDecrpt(String fileName) {
		return readFromFileAndDecrpt(new File(fileName));
	}

	private synchronized InputStream readFromFileAndDecrpt(File file) {
		ByteArrayInputStream inputStream = null;
		try {
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream(file));
			byte[] fileContext = (byte[]) input.readObject();
			Key key = (Key) input.readObject();
			input.close();
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			fileContext = cipher.doFinal(fileContext);
			String stemp = new String(fileContext, "UTF-8");
			inputStream = new ByteArrayInputStream(fileContext);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException("读取注册文件出错");
			// return null;
		}
		return inputStream;
	}

	public String getLicenseInfoString() {
		LicenseInfo licenseInfo = this.refreshLicenseInfo();
		StringBuffer licenseMsg = new StringBuffer("License 信息:");
		// if (licenseInfo == null) {
		// licenseMsg.append("无license文件信息");
		// } else {
		// if (licenseInfo.getIsProbationalEdition() != null) {
		// if (licenseInfo.getIsProbationalEdition().booleanValue()) {
		// licenseMsg.append("试用版 ");
		// if (licenseInfo.getMaturityDate() != null) {
		// licenseMsg.append("到期日:"
		// + DateFormat.getDateInstance(DateFormat.SHORT)
		// .format(licenseInfo.getMaturityDate()));
		// }
		// } else {
		// licenseMsg.append("正式版 ");
		// }
		// }
		// if (licenseInfo.getClientNum() != null) {
		// licenseMsg.append("客户端最大允许数:"
		// + licenseInfo.getClientNum().toString());
		// }
		// }
		return licenseMsg.toString();
	}
}