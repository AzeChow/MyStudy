/*
 * Created on 2004-9-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.logic.EncLogic;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.message.MessageFunctions;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class ExportReceiptCustomLogic {

	private EncDao encDao = null;
	private EncLogic encLogic = null;
	private EmsEdiTrDao emsEdiTrDao = null;
	private ExportMessageLogic exportMessageLogic = null;
	private String ENT_CENTER = "5200";
	private String registerNo = "99970001";
	private String YLRH18 = "";
	private final Log logger = LogFactory.getLog(getClass());
	private String DD07b = "";
	private Hashtable gbHash = null;
	private Company currCompany = null;

	private String ediNo = "xxxx"; // EDI编号
	private String addree = "61.145.126.185"; // EDI地址
	private String ediuser = "xxxx"; // EDI用户名
	private String edipassword = "xxxx"; // EDI密码
	private String bgFileName = ""; // 报关单文件名称

	public EncLogic getEncLogic() {
		return encLogic;
	}

	public void setEncLogic(EncLogic encLogic) {
		this.encLogic = encLogic;
	}

	private String getRegisterNo() {
		return ediNo;
	}

	private void infTojHsTable(List list) {
		if (gbHash == null) {
			gbHash = new Hashtable();
		}
		for (int i = 0; i < list.size(); i++) {
			Gbtobig gb = (Gbtobig) list.get(i);
			gbHash.put(gb.getBigname(), gb.getName());
		}
	}

	private String changeStr(String s) {
		if (s == null) {
			return s;
		}
		if (CommonUtils.getIsBigToGBK() != null
				&& CommonUtils.getIsBigToGBK().equals("1")) {
			String yy = "";
			char[] xxx = s.toCharArray();
			for (int i = 0; i < xxx.length; i++) {
				String z = String.valueOf(xxx[i]);
				if (String.valueOf(xxx[i]).getBytes().length == 2) {
					if (gbHash.get(String.valueOf(xxx[i])) != null) {
						z = (String) gbHash.get(String.valueOf(xxx[i]));
					}
				}
				yy = yy + z;
			}
			return yy;
		}
		return s;
	}

	/**
	 * 1为进口，2为出口
	 */
	public File exportCustomBill(BaseCustomsDeclaration head, String billType,
			List ls) throws Exception {

		// 写到本地 sendBgd

		head.setIsSend(new Boolean(true)); // 保存是否已经申报
		head.setIsCheck(new Boolean(false)); // 保存是否检查通过
		List lines = exportCustom(head, billType, ls);
		String fileName = genFileName(head, billType);
		File file = new File(CommonUtils.getSendBgd() + File.separator
				+ fileName);
		FileOutputStream fos = new FileOutputStream(file);
		for (int i = 0; i < lines.size(); i++) {
			String str = (String) lines.get(i);
			fos.write(new String((str + "\n").getBytes(), "GBK").getBytes());//
			// fos.write("\n".getBytes());
		}
		fos.close();
		String messageCode = "";
		if (billType.equals("1")) {
			messageCode = "201";
		} else if (billType.equals("2")) {
			messageCode = "202";
		} else
			messageCode = "";
		// 输出到查询信息中
		String bigSeqnum = head.getSerialNumber()!=null?head.getSerialNumber().toString():"";
		exportMessageLogic.saveCustomsMessageQuery(MessageQuery.SENDTYPE, file
				.getName(), head.getEmsHeadH2k(), messageCode, head
				.getPreCustomsDeclarationCode(), "", "", "", bigSeqnum);
		return file;
	}

	public List exportCustom(BaseCustomsDeclaration head, String billType,
			List ls) throws Exception {
		currCompany = encDao.findCurrCompany();
		if (CommonUtils.getIsBigToGBK() != null
				&& CommonUtils.getIsBigToGBK().equals("1")) {
			infTojHsTable(ls);
		}
		List<String> lines = new Vector<String>();
		int projectType = getProjectType(head);
		setEdiParameter(projectType);
		List bodies = encDao.findCustomsDeclarationCommInfo(head, projectType);
		lines.add(changeStr(writeFirstLine(billType)));//进出口类型
		lines.add(changeStr(writeRELATIVEID(head)));//关联报关单号
		lines.add(changeStr(writeRELATIVEMANUALNO(head)));//关联手册号
		lines.add(changeStr(writeHPH2KENTRYID(head)));//报关单号
		lines.add(changeStr(writePAPER(head)));//有无纸报关
		lines.add(changeStr(writeMemos(head))); // 备注1：head.getMemos()
		lines.add(changeStr(writeWarehouse(head)));//保税仓库
		lines.add(changeStr(writePredock(head)));//码头
		lines.add(changeStr(writeTraf(head)));//运输方式、运输工具
		lines.add(changeStr(writeVOYAGENO(head)));//海运报关单
		lines.add(changeStr(writeHead(billType, head, bodies.size(),
				projectType)));
		for (int i = 0; i < bodies.size(); i++) {
			lines.add(changeStr(writeBody(
					(BaseCustomsDeclarationCommInfo) bodies.get(i), head)));
		}
		// write 集装箱 NO
		/*
		 * {write 集装箱 NO IDD27: 555555555552(2) IDD40: 5555555555510002A6
		 * ,3333333333310001A1 mstr := fieldbyname('IDD27').asstring; //集装箱号 if
		 * length(mstr) > 12 then begin mstr1 := fieldbyname('IDD40').asstring;
		 * //其余集装箱号 i := pos('(', mstr); i := strtoint(copy(mstr, 13, i - 13));
		 * //集装箱个数 mstrlist := TStringList.Create; mstr3 := 'J' +
		 * PadNumALL(inttostr(i), 3); //集装箱头 mstr4 := mstr3 +
		 * PadR(FieldByName('idd43').AsString, 10) +
		 * PadR(FieldByName('idd44').AsString, 15) + PadR(GConfigContact, 10) +
		 * PadR(GConfigTel, 15); while pos(',', mstr1) > 0 do begin //增加集装箱
		 * mstrlist.Add(copy(mstr1, 1, 21)); //7-25 mstr1 := copy(mstr1, 23,
		 * length(mstr1) - 22); //7-25 end; if length(mstr1) = 21 then
		 * mstrlist.Add(mstr1);
		 * 
		 * WriteLN(ExFile,'CONTAINER'+PadNumALL(inttostr(i), 4)); for j:=0 to
		 * mstrlist.Count -1 do begin
		 * WriteLN(ExFile,GetContainer12(mstrlist[j])); end;
		 * 
		 * end else begin //集装箱为0 WriteLN(ExFile, 'CONTAINER0000');
		 */
		String mstr = head.getContainerNum(); // 集装箱号
		if (mstr != null && mstr.length() > 12) {
			int i = mstr.indexOf('(');
			i = Integer.parseInt(mstr.substring(12, i)); // 集装箱个数
			List mstrlist = this.encDao.findContainerByCustomsDeclaration(head,
					projectType);// 所有的集装箱
			lines.add(changeStr("CONTAINER"
					+ formatVariant(String.valueOf(i), "9(4)"))); // 集装箱报文标示符(X(4))
			System.out.println("-- mstrlist.size=:" + mstrlist.size());

			for (int j = 0; j < mstrlist.size(); j++) {
				String containerCode = ((BaseCustomsDeclarationContainer) mstrlist
						.get(j)).getContainerCode();
				System.out.println("-- containerCode:" + containerCode);
				String srtJzx = ((BaseCustomsDeclarationContainer) mstrlist
						.get(j)).getSrtJzx().getCode();
				System.out.println("-- srtJzx:" + srtJzx);
				lines.add(changeStr(getContainer12(containerCode, srtJzx))); //
			}
		} else {
			lines.add("CONTAINER0000");
		}
		// 写随附单证
		// 每个随附单证一行（73字节）：
		// <FORM_CERTIFICATE > + 18位"预录入号" + 一位随附单证类型代码
		// + 32位随附单证号（左对齐，不足后补空格）。
		/*
		 * sDD70 := DD70;//copy(DD70,41,30); nPos := pos(',',sDD70); while
		 * nPos>0 do begin sCert := copy(sDD70,1,nPos-1);
		 * WriteFreeTextLN(sCert); Delete(sDD70,1,nPos); nPos := pos(',',sDD70);
		 * end; WriteFreeTextLN(sDD70);
		 */
		String sDD70 = head.getCertificateCode(); // A:1111,W*2222/23332/232
		if (sDD70 != null && !sDD70.equals("")) {
			if (sDD70.indexOf("：") > 0) {
				sDD70 = sDD70.replaceAll("：", ":");
			}
			if (sDD70.indexOf("＊") > 0) {
				sDD70 = sDD70.replaceAll("＊", ":");
			}
			if (sDD70.indexOf("*") > 0) {
				sDD70 = getSplitStr(sDD70);
			}
			if (sDD70.indexOf("，") > 0) {
				sDD70 = sDD70.replaceAll("，", ",");
			}
			String[] tempArray = sDD70.split(",");
			for (int i = 0; i < tempArray.length; i++) {
				String[] tempArray2 = tempArray[i].split(":");
				String type = "";
				String code = "";
				if (tempArray[i].indexOf(":") != 1 || tempArray2.length < 2) {
					break;
				} else {
					type = tempArray2[0];
					code = tempArray2[1];
				}
				lines.add(changeStr(formatVariant("<FORM_CERTIFICATE    >"
						+ YLRH18 + formatVariant(type, "X(1)")
						+ formatVariant(code, "X(32)"), "X(73)")));
			}
		}
		// 报关员姓名
		lines.add(changeStr(formatVariant("<DECRNAME>"
				+ substring(head.getCustomser(), 0, 10) + "</DECRNAME>",
				"X(70)")));
		// Writeln:<DECRTEL>+报关员电话+</DECRTEL> 15 (telephone)
		lines
				.add(changeStr(formatVariant("<DECRTEL>"
						+ substring(head.getTelephone(), 0, 15) + "</DECRTEL>",
						"X(70)")));
		// Writeln:<OWNERNAME>+企业负责人+</OWNERNAME> 10 (公司实体.联系人)
		lines.add(changeStr(formatVariant("<OWNERNAME>"
				+ substring(((Company) CommonUtils.getCompany()).getBuOwner(),
						0, 10) + "</OWNERNAME>", "X(70)")));
		// Writeln:<ENTTEL>+企业联系电话+</ENTTEL> 15 (公司实体.联系电话)
		lines.add(changeStr(formatVariant("<ENTTEL>"
				+ substring(((Company) CommonUtils.getCompany()).getTel(), 0,
						15) + "</ENTTEL>", "X(70)")));
		// if 运输工具<>''并且copy(运输工具，1，1)='@'
		if (head.getConveyance() != null
				&& !head.getConveyance().trim().equals("")
				&& substring(head.getConveyance(), 0, 1).equals("@")) {
			// Writeln:<OTRAFCODE>+境外运输工具编码+</OTRAFCODE> 15
			lines.add(changeStr(formatVariant("<OTRAFCODE>"
					+ substring(head.getOverseasConveyanceCode(), 0, 15)
					+ "</OTRAFCODE>", "X(70)")));
			// Writeln:<OTRAFNAME>+境外运输工具名称+</OTRAFNAME> 30
			lines.add(changeStr(formatVariant("<OTRAFNAME>"
					+ substring(head.getOverseasConveyanceName(), 0, 30)
					+ "</OTRAFNAME>", "X(70)")));
			// Writeln:<OTRAFVOYNO>+境外运输工具航次+</OTRAFVOYNO> 12
			lines.add(changeStr(formatVariant("<OTRAFVOYNO>"
					+ substring(head.getOverseasConveyanceFlights(), 0, 12)
					+ "</OTRAFVOYNO>", "X(70)")));
			// Writeln:<OTRAFBILNO>+境外运输工具提单号+</OTRAFBILNO> 20
			lines
					.add(changeStr(formatVariant(
							"<OTRAFBILNO>"
									+ substring(
											head
													.getOverseasConveyanceBillOfLading(),
											0, 20) + "</OTRAFBILNO>", "X(70)")));
			// Writeln:<ITRAFMODE>+境内运输方式+</ITRAFMODE> 1 (domesticTransferMode)
			if (head.getDomesticTransferMode() != null) {
				lines.add(changeStr(formatVariant("<ITRAFMODE>"
						+ substring(head.getDomesticTransferMode().getCode(),
								0, 1) + "</ITRAFMODE>", "X(70)")));
			} else {
				lines.add(changeStr(formatVariant("<ITRAFMODE>"
						+ "</ITRAFMODE>", "X(70)")));
			}
			// Writeln:<ITRAFCODE>+境内运输工具编码+</ITRAFCODE> 13
			lines.add(changeStr(formatVariant("<ITRAFCODE>"
					+ substring(head.getDomesticConveyanceCode(), 0, 13)
					+ "</ITRAFCODE>", "X(70)")));
			// Writeln:<ITRAFNAME>+境内运输工具名称+</ITRAFNAME> 30
			lines.add(changeStr(formatVariant("<ITRAFNAME>"
					+ substring(head.getDomesticConveyanceName(), 0, 30)
					+ "</ITRAFNAME>", "X(70)")));
			// Writeln:<ITRAFVOYNO>+境内运输工具航次+</ITRAFVOYNO> 12
			lines.add(changeStr(formatVariant("<ITRAFVOYNO>"
					+ substring(head.getDomesticConveyanceFlights(), 0, 12)
					+ "</ITRAFVOYNO>", "X(70)")));
		} else {
			// Writeln:<OTRAFCODE></OTRAFCODE>
			lines.add(formatVariant("<OTRAFCODE>" + "</OTRAFCODE>", "X(70)"));
			// Writeln:<OTRAFNAME></OTRAFNAME>
			lines.add(formatVariant("<OTRAFNAME>" + "</OTRAFNAME>", "X(70)"));
			// Writeln:<OTRAFVOYNO></OTRAFVOYNO>
			lines.add(formatVariant("<OTRAFVOYNO>" + "</OTRAFVOYNO>", "X(70)"));
			// Writeln:<OTRAFBILNO></OTRAFBILNO>
			lines.add(formatVariant("<OTRAFBILNO>" + "</OTRAFBILNO>", "X(70)"));
			// Writeln:<ITRAFMODE></ITRAFMODE>
			lines.add(formatVariant("<ITRAFMODE>" + "</ITRAFMODE>", "X(70)"));
			// Writeln:<ITRAFCODE></ITRAFCODE>
			lines.add(formatVariant("<ITRAFCODE>" + "</ITRAFCODE>", "X(70)"));
			// Writeln:<ITRAFNAME></ITRAFNAME>
			lines.add(formatVariant("<ITRAFNAME>" + "</ITRAFNAME>", "X(70)"));
			// Writeln:<ITRAFVOYNO></ITRAFVOYNO>
			lines.add(formatVariant("<ITRAFVOYNO>" + "</ITRAFVOYNO>", "X(70)"));
		}

		// Writeln:<HPCUSFIE>+装卸码头+</HPCUSFIE> 2 (predock.code)
		if (head.getPredock() != null) {
			lines.add(changeStr(formatVariant("<HPCUSFIE>"
					+ substring(head.getPredock().getCode(), 0, 2)
					+ "</HPCUSFIE>", "X(70)")));
		} else {
			lines.add(formatVariant("<HPCUSFIE>" + "</HPCUSFIE>", "X(70)"));
		}

		// Writeln:<TRACTYPE>+车辆拖架类型+</TRACTYPE> 5
		List mstrlist = this.encDao.findContainerByCustomsDeclaration(head,
				projectType);
		if (mstrlist.size() >= 1
				&& ((BaseCustomsDeclarationContainer) mstrlist.toArray()[0])
						.getSrttj() != null) {
			lines.add(changeStr(formatVariant("<TRACTYPE>"
					+ formatVariant(substring(
							((BaseCustomsDeclarationContainer) mstrlist
									.toArray()[0]).getSrttj().getCode(), 0, 5),
							"X(5)") + "<TRACTYPE>", "X(70)")));
		} else {
			lines.add(formatVariant("<TRACTYPE>" + formatVariant(null, "X(5)")
					+ "<TRACTYPE>", "X(70)"));
		}

		// Writeln:<TRACICNO>+车辆IC卡号，目前无用(0000000000)+</TRACICNO> 10
		lines.add(formatVariant("<TRACICNO>0000000000</TRACICNO>", "X(70)"));

		return lines;

	}

	private int getProjectType(BaseCustomsDeclaration head) {
		if (head instanceof CustomsDeclaration) {
			return ProjectType.BCUS;
		} else if (head instanceof BcsCustomsDeclaration) {
			return ProjectType.BCS;
		} else if (head instanceof DzscCustomsDeclaration) {
			return ProjectType.DZSC;
		}
		return ProjectType.BCUS;
	}

	// 报关单报文名永远是：00 + 17位edi预录号 + .imp/exp（如：0099960001044000123.imp）
	private String genFileName(BaseCustomsDeclaration head, String type) {
		try {
			String sfilename1 = bgFileName.substring(1, bgFileName.length());
			if (type.equals("1")) {
				return "00" + sfilename1 + ".imp";
			} else {
				return "00" + sfilename1 + ".exp";
			}
		} catch (Exception e) {
			throw new RuntimeException("生成文件名出错。无法生成报文");
		}
		/*
		 * String sfilename1 =
		 * substring(registerNo+head.getCustoms().getCode(),3,5); try {
		 * sfilename1 += substring(nowToDate(), 3, 4); //formatVariant(new
		 * Date(), "YYYYMMDD") sfilename1 +=
		 * formatVariant(head.getPreCustomsDeclarationCode(),"X(6)"); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}

	private String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start) {
			return new String(str.getBytes(), start, str.getBytes().length
					- start);
		}
		return "";
	}

	private String getSplitStr(String xx) {
		String before = "";
		String after = "";
		if (xx.indexOf("*") > 0) {
			// before = substring(xx,0,xx.indexOf("*"));
			// after = substring(xx,xx.indexOf("*")+1,xx.length());
			before = xx.substring(0, xx.indexOf("*")).trim();
			after = xx.substring(xx.indexOf("*") + 1, xx.length()).trim();
		}
		return before + ":" + after;

	}

	/*
	 * function GetContainer12(OrgContNo:string): string; function
	 * GetContSpec(Sepc4:string): string; begin Result := '0'; if Sepc4='0001'
	 * then Result := '1' else if Sepc4='0002' then Result := '2' else if
	 * Sepc4='0003' then Result := '3' else if Sepc4='0004' then Result := '4'
	 * else if Sepc4='0005' then Result := '5' else if Sepc4='0006' then Result
	 * := '6' else if Sepc4='0007' then Result := '7' else if Sepc4='0008' then
	 * Result := '8' else if Sepc4='0009' then Result := '9' else if
	 * Sepc4='0010' then Result := 'A' else if Sepc4='0011' then Result := 'B'
	 * else if Sepc4='0012' then Result := 'C' end; begin Result :=
	 * Copy(OrgContNo,1,11) + GetContSpec(Copy(OrgContNo,13,4)); end;
	 */
	// 获取12位集装箱号
	private String getContainer12(String containerCode, String orgContNo) {
		return containerCode.substring(0, 11) + getContSpec(orgContNo);
	}

	private String getContSpec(String sepc4) {
		String result = "";
		if (sepc4 == null || sepc4.equals("")) {
			return "";
		}
		if (sepc4.trim().equals("0001"))
			result = "1";
		else if (sepc4.trim().equals("0002"))
			result = "2";
		else if (sepc4.trim().equals("0003"))
			result = "3";
		else if (sepc4.trim().equals("0004"))
			result = "4";
		else if (sepc4.trim().equals("0005"))
			result = "5";
		else if (sepc4.trim().equals("0006"))
			result = "6";
		else if (sepc4.trim().equals("0007"))
			result = "7";
		else if (sepc4.trim().equals("0008"))
			result = "8";
		else if (sepc4.trim().equals("0009"))
			result = "9";
		else if (sepc4.trim().equals("0010"))
			result = "A";
		else if (sepc4.trim().equals("0011"))
			result = "B";
		else if (sepc4.trim().equals("0012"))
			result = "C";

		return result;
	}

	private String writeBody(BaseCustomsDeclarationCommInfo body,
			BaseCustomsDeclaration head) throws Exception {
		String sBody = "";
		// 项号
		sBody += formatVariant(body.getSerialNumber(), "9(5)");
		// 附加编号 copy(9,2)
		sBody += formatVariant(substring(body.getComplex().getCode(), 8, 10),
				"X(8)");
		// 商品编号 copy(1,8)
		sBody += formatVariant(substring(body.getComplex().getCode(), 0, 8),
				"X(8)");
		// 归类标志
		sBody += " ";
		// 商品名称
		sBody += formatVariant(body.getCommName(), "X(30)");
		// 规格型号
		sBody += formatVariant(body.getCommSpec(), "X(30)");
		// 产销国
		if (body.getCountry() != null) {
			sBody += formatVariant(body.getCountry().getCode(), "X(3)");
		} else {
			sBody += formatVariant("", "X(3)");
		}
		// 手册商品项目序号 (表头.emsHeadH2k.code)
		sBody += formatVariant(body.getCommSerialNo(), "9(5)");
		// 数量 9(15).9(3)
		sBody += formatVariant(body.getCommAmount(), "9(15).9(3)");
		// 申报计量单位
		if (body.getUnit() != null) {
			sBody += formatVariant(body.getUnit().getCode(), "X(3)");
		} else {
			sBody += formatVariant("", "X(3)");
		}
		// 申报计量单位与法定单位比例因子 9(7).9(3) (第一法定单位比例因子)
		sBody += formatVariant(Double.valueOf(DouTodou(body.getFirstAmount())
				/ DouTodou(body.getCommAmount())), "9(7).9(3)");
		// 申报数量
		sBody += formatVariant(body.getFirstAmount(), "9(15).9(3)");
		// 第二数量
		sBody += formatVariant(body.getSecondAmount(), "9(15).9(3)");
		// 第二计量单位
		if (body.getSecondLegalUnit() != null) {
			sBody += formatVariant(body.getSecondLegalUnit().getCode(), "X(3)");
		} else {
			sBody += formatVariant("", "X(3)");
		}
		// 成交币制
		if (head.getCurrency() != null) {
			sBody += formatVariant(head.getCurrency().getCode(), "X(3)");
		} else {
			sBody += formatVariant("", "X(3)");
		}
		// 申报单价 9(14).9(3) (commUnitPrice)
		sBody += formatVariant(body.getCommUnitPrice(), "9(14).9(4)");
		// 成交总价 9(16).9(3) (commTotalPrice)
		sBody += formatVariant(body.getCommTotalPrice(), "9(16).9(2)");
		// 征减免税 x(1) (levyMode.code)
		if (body.getLevyMode() != null) {
			sBody += formatVariant(body.getLevyMode().getCode(), "X(1)");
		} else {
			sBody += formatVariant("", "X(1)");
		}
		// 用途 x(2) (uses.code)
		if (body.getUses() != null) {
			sBody += formatVariant(body.getUses().getCode(), "X(2)");
		} else {
			sBody += formatVariant("", "X(2)");
		}
		// 第一计量单位 x(3)
		if (body.getLegalUnit() != null) {
			sBody += formatVariant(body.getLegalUnit().getCode(), "X(3)");
		} else {
			sBody += formatVariant("", "X(3)");
		}
		// 货号 x(30) (commSerialNo)
		sBody += formatVariant(body.getMaterielNo(), "X(30)");
		// 版本 x(10) (materielNo)
		sBody += formatVariant(body.getVersion(), "X(10)");
		return sBody;
	}

	private double DouTodou(Double value) {
		if (value == null) {
			return 0;
		}
		return value.doubleValue();
	}

	/**
	 * @param billType
	 * @param head
	 * @throws Exception
	 */
	private String writeHead(String billType, BaseCustomsDeclaration head,
			int num, int projectType) throws Exception {
		String thirdLine = "0";
		YLRH18 = "";
		String YLRH = "0";
		if (head.getPreCustomsDeclarationCode() != null
				&& !head.getPreCustomsDeclarationCode().equals("")) {
			YLRH = head.getPreCustomsDeclarationCode();// 预录入号
		}
		/*
		 * if (Length(YLRH) > 1) then begin YLRH := strRight(YLRH, 6); end else
		 * begin YLRH := GetNewBGDNO; end;
		 */
		String unicode = head.getUnificationCode(); // 统一编号
		if (Integer.parseInt(YLRH) > 0) {
			YLRH = formatVariant(YLRH, "X(6)");
		} else {
			YLRH = encDao.getMaxPreCustomsDeclarationCode(); // 得到新的预录入号
			head.setPreCustomsDeclarationCode(YLRH);
			// encDao.saveCustomsDeclaration(head);
		}
		// YLRH := CommInfo.RegisterNO +
		// copy(IntToStr(Qu_Title.fieldbyname('kacode').AsInteger + 50), 3, 2) +
		// FormatDatetime('yy', date)[2] + YLRH;
		String sTemp = formatVariant(registerNo, "X(8)");
		String dcode = substring(head.getDeclarationCustoms().getCode().trim(),
				2, 4);
		if (billType.equals("1")) { // 进口
			sTemp += formatVariant(dcode, "X(2)");
		} else { // 出口
			sTemp += formatVariant(String.valueOf(Integer.valueOf(dcode) + 50),
					"X(2)");
		}
		sTemp += substring(nowToDate(), 3, 4); // formatVariant(new Date(),
		// "YYYYMMDD")
		YLRH = sTemp + YLRH;
		head.setTempPreCode(YLRH); // 保存预录入号
		encDao.saveCustomsDeclaration(head);
		/*
		 * {18位是"预录入号"是: 在没有"统一编号"时：Z + 17位edi预录号（如：Z99960001044000123）;
		 * 如果已有"统一编号"：18位"统一编号"。} if unicode='' then YLRH18 := 'Z'+PadR(YLRH,17)
		 * else YLRH18 := PadR(unicode,18);
		 */

		if (unicode == null || unicode.equals("")) {
			YLRH18 = 'Z' + formatVariant(YLRH, "X(17)");
			bgFileName = YLRH18;// 报关单文件名称
		} else {
			YLRH18 = formatVariant(unicode, "X(18)");
			bgFileName = 'Z' + formatVariant(YLRH, "X(17)");// 报关单文件名称
		}

		// Write(Exfile, '0'+YLRH18); //预申报编号
		thirdLine += YLRH18;
		// 海关编号
		thirdLine += formatVariant("0", "9(14)") + " ";
		// 运输方式代码
		if (head.getTransferMode() != null
				&& !head.getTransferMode().getCode().trim().equals("")) {
			thirdLine += substring(head.getTransferMode().getCode(), 0, 1);
		} else {
			thirdLine += " ";
		}
		// 进出口岸
		if (head.getCustoms() != null) {
			thirdLine += formatVariant(head.getCustoms().getCode(), "X(4)");
		} else {
			thirdLine += formatVariant("", "X(4)");
		}
		// 指运港 进口(装货港)，出口(指运港) （portOfLoadingOrUnloading.code）
		if (head.getPortOfLoadingOrUnloading() != null) {
			thirdLine += formatVariant(head.getPortOfLoadingOrUnloading()
					.getCode(), "X(4)");
		} else {
			thirdLine += formatVariant("", "X(4)");
		}
		// 运输工具及名称 @+13位运输工具+12个空格 （conveyance）
		thirdLine += formatVariant(head.getConveyance(), "X(26)"); // 运输工具共26位
		// (经营范围对象.经营单位编码)(从经营范围中获得经营单位编码与名称和加工单位编码与名称)
		/*
		 * EmsEdiTrHead emsEdiTrHead = (EmsEdiTrHead) emsEdiTrDao
		 * .findEmsEdiTrHead().get(0);
		 */
		// 经营单位编码
		// thirdLine += formatVariant(currCompany.getBuCode(), "X(10)");
		thirdLine += formatVariant(head.getTradeCode(), "X(10)");
		// edit by kcb 2008/11/5
		// 经营单位名称
		// thirdLine += formatVariant(currCompany.getBuName(), "X(30)");
		thirdLine += formatVariant(head.getTradeName(), "X(30)");
		// edit by kcb 2008/11/5

		// 企业性质
		if (currCompany.getOwnerType() != null) {
			thirdLine += formatVariant(currCompany.getOwnerType().getCode(),
					"X(1)");
		} else {
			thirdLine += formatVariant("", "X(1)");
		}

		// 境内目的地
		if (head.getDomesticDestinationOrSource() != null) {
			thirdLine += formatVariant(head.getDomesticDestinationOrSource()
					.getCode(), "X(5)");
		} else {
			thirdLine += formatVariant("", "X(5)");
		}
		// 收货单位编码
		thirdLine += formatVariant(currCompany.getCode(), "X(10)");
		// 收货单位名称
		thirdLine += formatVariant(currCompany.getName(), "X(30)");

		// 申报单位编码
		thirdLine += formatVariant(((Company) head.getDeclarationCompany())
				.getCode(), "X(10)");
		// 申报单位名称
		thirdLine += formatVariant(((Company) head.getDeclarationCompany())
				.getName(), "X(30)");

		// 合同号
		thirdLine += formatVariant(head.getContract(), "X(20)");
		// 内销比率
		thirdLine += formatVariant(null, "9(5)");
		// 提单或运单号
		thirdLine += formatVariant(head.getBillOfLading(), "X(20)");
		// 贸易国别
		if (head.getCountryOfLoadingOrUnloading() != null) {
			thirdLine += formatVariant(head.getCountryOfLoadingOrUnloading()
					.getCode(), "X(3)");
		} else {
			thirdLine += formatVariant("", "X(3)");
		}
		// 贸易方式
		if (head.getTradeMode() != null) {
			thirdLine += formatVariant(head.getTradeMode().getCode(), "X(4)");
		} else {
			thirdLine += formatVariant("", "X(4)");
		}
		// 征免性质类
		if (head.getLevyKind() != null) {
			thirdLine += formatVariant(head.getLevyKind().getCode(), "X(3)");
		} else {
			thirdLine += formatVariant("", "X(3)");
		}
		// 纳税方式
		thirdLine += "0";
		// 成交方式
		if (head.getTransac() != null) {
			thirdLine += formatVariant(head.getTransac().getCode(), "X(1)");
		} else {
			thirdLine += formatVariant("", "X(1)");
		}
		// 外汇来源->折合成标准集装箱数
		thirdLine += formatVariant(
				getNormalizeContainer(head.getContainerNum()), "X(2)");
		// 收结汇方式
		if (billType.equals("1")) {
			thirdLine += " ";
		} else {
			thirdLine += formatVariant(head.getBalanceMode().getCode(), "X(1)");
		}
		// 运费标记
		thirdLine += formatVariant(head.getFreightageType(), "X(1)");
		// 杂费标记
		thirdLine += formatVariant(head.getIncidentalExpensesType(), "X(1)");
		// 保险费标记
		thirdLine += formatVariant(head.getInsuranceType(), "X(1)");
		// 运费币制
		if (head.getCurrency() != null) {
			thirdLine += formatVariant(head.getCurrency().getCode(), "X(3)");
		} else {
			thirdLine += formatVariant("", "X(3)");
		}
		// 运费/率
		thirdLine += formatVariant(head.getFreightage(), "9(6).9(3)");
		// 杂费币制
		if (head.getCurrency() != null) {
			thirdLine += formatVariant(head.getCurrency().getCode(), "X(3)");
		} else {
			thirdLine += formatVariant("", "X(3)");
		}
		// 杂费/率
		thirdLine += formatVariant(head.getIncidentalExpenses(), "9(6).9(3)");
		// 保险费币制
		if (head.getCurrency() != null) {
			thirdLine += formatVariant(head.getCurrency().getCode(), "X(3)");
		} else {
			thirdLine += formatVariant("", "X(3)");
		}
		// 保险费/率
		thirdLine += formatVariant(head.getInsurance(), "9(6).9(3)");
		// 件数
		thirdLine += formatVariant(head.getCommodityNum(), "9(10)");
		// 毛重
		thirdLine += formatVariant(head.getGrossWeight(), "9(8).9(1)");
		// 净重
		thirdLine += formatVariant(head.getNetWeight(), "9(8).9(1)");
		// 许可证编号
		thirdLine += formatVariant(head.getLicense(), "X(20)");
		// 批准文号
		thirdLine += formatVariant(head.getAuthorizeFile(), "X(20)");
		// 备案号 if 贸易方式="一般贸易" 12个空格 else 合同号 x(12)
		if (head.getTradeMode() != null
				&& head.getTradeMode().getName().equals("一般贸易")) {
			thirdLine += formatVariant(null, "X(12)");
		} else
			thirdLine += formatVariant(head.getEmsHeadH2k(), "X(12)");
		// 进出日期 进口：2个空格+x(8) 出口：10个0 (impExpDate)
		if (billType.equals("1")) {
			thirdLine += "  " + formatVariant(head.getImpExpDate(), "YYYYMMDD");
		} else {
			thirdLine += formatVariant("0", "9(10)");
		}
		// 录入员编号
		thirdLine += "     ";// 5个空格
		// 包装种类
		if (head.getWrapType() != null) {
			thirdLine += formatVariant(head.getWrapType().getName(), "X(6)");
		} else {
			thirdLine += formatVariant(null, "X(6)");
		}
		// 随附单据
		thirdLine += formatVariant(head.getAttachedBill(), "X(7)");
		// 备注1
		String memos = head.getMemos();
		if (memos != null && substring(memos, 0, 2).equals("#1")) {
			memos = substring(memos, 2, memos.getBytes().length);
		}
		memos+=head.getPredock().getCode()
		     +(head.getCertificateCode()==null?"":head.getCertificateCode());
		thirdLine += formatVariant(memos, "X(70)");
//		thirdLine += formatVariant(memos, "X(30)");//edit by cjb 2009.11.20
//		// 码头代码
//		thirdLine += formatVariant(head.getPredock().getCode(), "X(10)");
//		// 备注2
//		/*
//		 * if ByteType(MemoText,30)=mbLeadByte then Result :=
//		 * PadR(copy(MemoText, 1, 29), 30) else Result := PadR(copy(MemoText, 1,
//		 * 30), 30);
//		 */
//		// 备注1--30
//		thirdLine += formatVariant(substring(head.getCertificateCode(), 0, 30),
//				"X(30)");
		// 商品项数
		thirdLine += formatVariant(String.valueOf(num), "9(5)");
		// 申报日期
		thirdLine += "  " + nowToDate();
		// 缴款单位标识
		thirdLine += " ";
		return thirdLine;
	}

	private String getNormalizeContainer(String container) {
		if (container == null || container.getBytes().length <= 1) {
			return "0";
		} else {
			if (container.indexOf("(") < 0 || container.indexOf(")") < 0
					|| container.indexOf("(") + 1 == container.indexOf(")")) {
				return "0";
			}
			String xx = container.substring(container.indexOf("(") + 1,
					container.indexOf(")"));
			if (xx.equals("")) {
				xx = "0";
			}
			return xx;
		}
	}

	public String nowToDate() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		Date date1 = new Date();
		String str1 = formater.format(date1);
		return str1;
	}

	private String formatVariant(Object obj, String sFormat) throws Exception {
		return MessageFunctions.formatVariant(obj, sFormat);
	}

	/**
	 * 保税仓库
	 */
	private String writeWarehouse(BaseCustomsDeclaration head) throws Exception {
		String fiveLine = "";
		// WriteFreeText050110('I',Qu_Title); //进口:I,出口:E,特殊:O
		// 2005-1-10增加4行自由文本
		// <HP_BONDEDNO>32位保税仓库</HP_BONDEDNO>（后补空格）
		// <HPCUSFIE>装卸码头（2）</HPCUSFIE>（后补空格）
		// <HP_TRAF>50位运输工具名称</HP_TRAF>
		// <HP_VOYAGENO>32位航次号</HP_VOYAGENO>
		/*
		 * Writeln(ExFile,PadR( Format('<HP_BONDEDNO>%s</HP_BONDEDNO>'
		 * ,[PadR(Qu_Title.FieldByName(IEType+'DD71').asString,32)]) ,70));
		 */

		String sNote = MessageFunctions.formatVariant(
				head.getBondedWarehouse(), "X(32)"); // 有问题，无仓库
		fiveLine += MessageFunctions.formatVariant("<HP_BONDEDNO>" + sNote
				+ "</HP_BONDEDNO>", "X(70)");
		return fiveLine;
	}

	/**
	 * 码头
	 */
	private String writePredock(BaseCustomsDeclaration head) throws Exception {
		String sixLine = "";
		/*
		 * Writeln(ExFile,PadR( Format('<HPCUSFIE>%s</HPCUSFIE>'
		 * ,[PadR(Qu_Title.FieldByName('POCode').asString,2)]) ,70));
		 */
		String predock = "";
		if (head.getPredock() != null) {
			predock = head.getPredock().getCode();
		}
		String sNote = MessageFunctions.formatVariant(predock, "X(2)");
		sixLine += MessageFunctions.formatVariant("<HPCUSFIE>" + sNote
				+ "</HPCUSFIE>", "X(70)");
		return sixLine;
	}

	/**
	 * 运输方式、运输工具
	 */
	private String writeTraf(BaseCustomsDeclaration head) throws Exception {
		String seventhLine = "";
		/*
		 * {2.2.1.9 <HP_TRAFNAME>和</HP_VOYAGENO>行 " 每行共70字节 "
		 * <HP_TRAF>50位运输工具名称</HP_TRAF>（后补空格），内容必需50位，不足后补空格 "
		 * <HP_VOYAGENO>32位航次号</HP_VOYAGENO>，内容必需32位，不足后补空格 " 这两行必须同时出现。 "
		 * 实际上:这两个内容是将报关单头的traf_name内容拆分而来,并且长度扩展了。 "
		 * 一般情况下：HP_TRAFNAME的内容就等于报关单头的traf_name的内容
		 * ,HP_VOYAGENO的内容为空，除了：对于海运报关单（traf_mode
		 * =2）,报关单头的traf_name是这样填的：船名/航次，这种情况下
		 * ，HP_TRAFNAME的内容是船名，HP_VOYAGENO的内容是航次。"/"当然丢弃。 "
		 * 实际上程序可以这样处理："运输工具名称"仍然有用户输入
		 * ，但长度扩展了，在生成报文时，如果该字段含有"/",则"/"前的内容写入HP_TRAFNAME
		 * ,而"/"后的内容写入HP_VOYAGENO。可能用户界面和数据库中字段长度须扩展。 "
		 * 报文中：报关单头的traf_name最好保持和以前一样,字段长度截取指定长度。 } DD06 :=
		 * Qu_Title.FieldByName('twcode').asString; DD07 :=
		 * Qu_Title.FieldByName(IEType+'DD07').asString; nPos := 0; if DD06='2'
		 * then nPos := pos('/',DD07); //海运报关单（traf_mode=2）
		 * 
		 * //分割 船名/航次 if nPos=0 then begin DD07a := DD07; DD07b := ''; end else
		 * begin DD07a := copy(DD07,1,nPos-1); DD07b := copy(DD07,nPos+1,255);
		 * end;
		 * 
		 * Writeln(ExFile,PadR( Format('<HP_TRAF>%s</HP_TRAF>'
		 * ,[PadR(DD07a,50)]) ,70)); Writeln(ExFile,PadR(
		 * Format('<HP_VOYAGENO>%s</HP_VOYAGENO>' ,[PadR(DD07b,32)]) ,70));
		 */
		String DD06 = "";
		if (head.getTransferMode() != null) {
			DD06 = head.getTransferMode().getCode();// 运输方式代码
		}
		String DD07 = "";
		if (head.getConveyance() != null) {
			DD07 = head.getConveyance();// 运输工具名称
		}

		int nPos = 0;
		if (DD06.equals("2")) { // 海运报关单
			nPos = DD07.indexOf("/");
		}
		String DD07a = "";
		DD07b = "";
		if (nPos <= 0) {
			DD07a = DD07;
			DD07b = "";
		} else {
			DD07a = DD07.substring(0, nPos).trim();
			// substring(DD07,0,nPos);
			DD07b = DD07.substring(nPos + 1, DD07.length()).trim();
			// substring(DD07,nPos+1,DD07.length());
		}
		String sNote = MessageFunctions.formatVariant(DD07a, "X(50)");
		seventhLine += MessageFunctions.formatVariant("<HP_TRAF>" + sNote
				+ "</HP_TRAF>", "X(70)");
		return seventhLine;
	}

	/**
	 * 海运报关单
	 */
	private String writeVOYAGENO(BaseCustomsDeclaration head) throws Exception {
		String eighth = "";
		String sNote = MessageFunctions.formatVariant(DD07b, "X(32)");
		eighth += MessageFunctions.formatVariant("<HP_VOYAGENO>" + sNote
				+ "</HP_VOYAGENO>", "X(70)");
		return eighth;
	}

	/**
	 * 备注内容
	 */
	private String writeMemos(BaseCustomsDeclaration head) throws Exception {
		// <HP_NOTE_S>最多255字节note_s</HP_NOTE_S>（后补空格）

		/*
		 * WriteLN(ExFile, PadR('<HP_NOTE_S>' +
		 * getNote_S(Qu_Title.fieldbyname('idd30').asstring) +'</HP_NOTE_S>',
		 * 300));
		 */

		// 获取H2000备注自由文本
		/*
		 * function getNote_S(DD30:string):string; begin Result :=
		 * PadR(copy(DD30,1,30),30) + PadR(copy(DD30,71,185),185); end;
		 */
		// 取全部
		String noteLine = "";
		String smemo = head.getMemos();
		if (smemo != null && substring(smemo, 0, 2).equals("#1")) {
			smemo = substring(smemo, 2, smemo.getBytes().length);
		}
		if (smemo == null) {
			smemo = "";
		}
		/*
		 * String sNote =
		 * MessageFunctions.formatVariant(substring(smemo.trim(),0,30),"X(30)")
		 * +
		 * MessageFunctions.formatVariant(substring(smemo.trim(),70,255),"X(185)"
		 * );
		 */
		String sNote = MessageFunctions.formatVariant(substring(smemo.trim(),
				0, 225), "X(225)");
		noteLine += MessageFunctions.formatVariant("<HP_NOTE_S>" + sNote
				+ "</HP_NOTE_S>", "X(300)");
		return noteLine;
	}

	/**
	 * 有无纸报关
	 */
	private String writePAPER(BaseCustomsDeclaration head) throws Exception {
		// <HP_NO_PAPER_FLAG>1位标志</HP_NO_PAPER_FLAG>（后补空格）。"W"表示无纸，"0"表示有纸
		// WriteLN(ExFile, PadR('<HP_NO_PAPER_FLAG>'
		// + ifThen(Qu_Title.fieldbyname('idd69').asstring<>'W','0','W')
		// +'</HP_NO_PAPER_FLAG>', 70));
		String secondLineAfter = "";
		String tag = "W";
		if (head.getIsNoBumf() == null || !head.getIsNoBumf().equals("W")) {
			tag = "0";
		}
		secondLineAfter += MessageFunctions.formatVariant("<HP_NO_PAPER_FLAG>"
				+ tag + "</HP_NO_PAPER_FLAG>", "X(70)");
		return secondLineAfter;
	}

	/**
	 * 报关单号
	 */
	private String writeHPH2KENTRYID(BaseCustomsDeclaration head)
			throws Exception {
		// 在表头行前加一行自由文本（第2行）,70字节
		// WriteLN(ExFile,
		// PadR('<HPH2KENTRYID>'+PadLZ(DD67,18)+'</HPH2KENTRYID>', 70));
		// DD67 := Qu_Title.fieldbyname('IDD67').asstring; //H2K入库报关单号
		String secondLine = "";
		if (head.getCustomsDeclarationCode() != null
				&& !head.getCustomsDeclarationCode().equals("")) {
			secondLine += MessageFunctions.formatVariant("<HPH2KENTRYID>"
					+ MessageFunctions.formatVariant(head
							.getCustomsDeclarationCode(), "X(18)")
					+ "</HPH2KENTRYID>", "X(70)");
		} else {
			secondLine += MessageFunctions.formatVariant("<HPH2KENTRYID>"
					+ MessageFunctions.formatVariant(head
							.getCustomsDeclarationCode(), "9(18)")
					+ "</HPH2KENTRYID>", "X(70)");
		}
		return secondLine;
	}
	/**
	 * 关联手册号
	 */
	private String writeRELATIVEMANUALNO(BaseCustomsDeclaration head)
			throws Exception {
		String s = "";
			s += MessageFunctions.formatVariant("<RELATIVE_MANUAL_NO>"
					+ MessageFunctions.formatVariant(head
							.getRelativeManualNo(), "X(12)")
					+ "</RELATIVE_MANUAL_NO>", "X(70)");
		return s;
	}
	/**
	 * 关联报关单号
	 */
	private String writeRELATIVEID(BaseCustomsDeclaration head)
			throws Exception {
		String s = "";
			s += MessageFunctions.formatVariant("<RELATIVE_ID>"
					+ MessageFunctions.formatVariant(head
							.getRelativeId(), "X(18)")
					+ "</RELATIVE_ID>", "X(70)");
		return s;
	}
	/**
	 * 第一行
	 */
	private String writeFirstLine(String billType) throws Exception {
		String sFirstLine = "";
		// Write(Exfile, 'CNC201');
		if (billType.equals("1")) { // 进口：CNC201 出口:CNC202
			sFirstLine += "CNC201";
		} else if (billType.equals("2")) {
			sFirstLine += "CNC202";
		} else {
			throw new RuntimeException("无效的报关单类型：" + billType);
		}

		registerNo = "9997" + getRegisterNo();
		// Write(Exfile, PadR('CNC' + CommInfo.EDICenter + CommInfo.EntFlag +
		// CommInfo.RegisterNO, 18));
		String line = "CNC" + ENT_CENTER + "1"
				+ formatVariant(registerNo, "X(8)");
		sFirstLine += MessageFunctions.formatVariant(line, "X(18)");
		// Write(ExFile, PadR('CNC' + CommInfo.EDICenter, 18));
		sFirstLine += formatVariant("CNC" + ENT_CENTER, "X(18)");
		// Write(ExFile, FormatDateTime('YYYYMMDD', Now));
		// Write(ExFile, FormatDateTime('HHNN', Now));
		sFirstLine += MessageFunctions
				.formatVariant(new Date(), "YYYYMMDDHHMM");
		// Write(ExFile, CNCVer);
		// const CNCVer='007';
		sFirstLine += "007";
		// Write(ExFile, PadR('1', 14));
		sFirstLine += MessageFunctions.formatVariant("1", "X(14)");
		return sFirstLine;
	}

	/**
	 * @return Returns the emsEdiTrDao.
	 */
	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * @param emsEdiTrDao
	 *            The emsEdiTrDao to set.
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/**
	 * @return Returns the encDao.
	 */
	public EncDao getEncDao() {
		return encDao;
	}

	/**
	 * @param encDao
	 *            The encDao to set.
	 */
	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}

	/**
	 * 得到回执内容
	 */
	public List getMessageInfo(String fileName) {
		List list = new ArrayList();
		File file = new File(CommonUtils.getFinallyBgd() + File.separator
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
	 * 通知单回执处理
	 * 
	 * @param file
	 * @return
	 */
	public void processReturnAdvoiceMessage(File file, int projectType) {
		if (file == null || file.length() == 0) {
			return;
		}
		String MessageType = "";
		try {
			exportMessageLogic.saveCustomsMessageQuery(MessageQuery.RECVTYPE,
					file.getName(), "", "", "", "", "", MessageType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 报关单回执处理
	 * 
	 * @param file
	 * @return
	 */
	public void processReturnMessage(File file, int projectType) {
		// 移动文件
		File destFile = new File(CommonUtils.getFinallyBgd() + File.separator
				+ file.getName());
		if (file == null || file.length() == 0) {
			moveFile(file, destFile);// 将不正常的文件移动到处理完的目录下
			return;
		}
		String MessageType = "";
		InputStream messageStream = null;
		try {
			messageStream = new FileInputStream(file);
		} catch (Exception e) {
			// throw new RuntimeException(e);
			moveFile(file, destFile); // 将不正常的文件移动到处理完的目录下
			return;
		}
		try {
			List messageLines = getMessageLines(messageStream);
			if (messageLines == null ) {
				moveFile(file, destFile); // 将不正常的文件移动到处理完的目录下
				return;
			}
			if(messageLines.size()<5){
				String message = "\n回执："+file.getName()+"格式异常！\n";
				throw new RuntimeException(message);
			}
			GetCustomsReceiptMessage receipt = new GetCustomsReceiptMessage(
					messageLines); // 回执对象
			String sDeclaraCode = receipt.getDeclaraCode(); // 报关单号
			System.out.println("--报关单号:" + sDeclaraCode);
			String sUniteCode = receipt.getUniteCode(); // 统一编号
			System.out.println("--统一编号:" + sUniteCode);
			String sNoticeDate = receipt.getNoticeDate();//回执通知日期
			String sReturnSign = receipt.getReturnSign(); // 标记
			System.out.println("--标记:" + sReturnSign);
			String sYlrh = getYLRH(file); // 预录入号
			System.out.println("--预录入号:" + sYlrh);
			System.out.println("--标志：" + sReturnSign);

			if (sReturnSign.equals("3")) {
				MessageType = "电子口岸报关单入库回执";
				updateBgdUnicode(sYlrh, sDeclaraCode, sUniteCode, projectType);
			} else if (sReturnSign.equals("L")) {
				MessageType = "H2000报关单入库回执";
				sNoticeDate = null;
				updateBgdH2k(sYlrh, sDeclaraCode, sUniteCode, sNoticeDate,false, projectType);//true
			} else if (sReturnSign.equals("G") || sReturnSign.equals("F")
					|| sReturnSign.equals("W")) {
				MessageType = "H2000报关单审核回执";
				if(!sReturnSign.equals("G")){
					sNoticeDate = null;
				}
				updateBgdH2k(sYlrh, sDeclaraCode, sUniteCode, sNoticeDate,false,
						projectType);
			} else if (sReturnSign.equals("E")) {
				MessageType = "H2000报关单退单回执";
			} else {
				MessageType = "H2000报关单回执";
			}
			moveFile(file, destFile);
			String bigSeqnum = "";
			MessageQuery messageQuery = exportMessageLogic.getMessageQueryByYlrh(sYlrh, sDeclaraCode);
			if(messageQuery!=null){
				bigSeqnum = messageQuery.getBigSeqnum();
			}
			
			// 输出到查询信息中
			exportMessageLogic.saveCustomsMessageQuery(MessageQuery.RECVTYPE,
					file.getName(), "", receipt.getMessageCode(), sYlrh,
					sUniteCode, sDeclaraCode, MessageType,bigSeqnum);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 异常文件处理
	public void exceptionfilesdeal(String filename) {
		// 移动文件
		File destFile = new File(CommonUtils.getFinallyBgd() + File.separator
				+ filename);

		File sourceFile = new File(CommonUtils.getRecvBgd() + File.separator
				+ filename);
		moveFile(sourceFile, destFile);
	}

	// 移动文件
	private void moveFile(File fSrc, File fDest) {
		try {
			if (fSrc.exists()) {
				FileUtils.copyFile(fSrc, fDest);
				fSrc.delete();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * //写电子口岸入库回执的统一编号
	 * 
	 * @param sYlrh
	 * @param sDeclaraCode
	 * @param sUniteCode
	 */
	public void updateBgdUnicode(String sYlrh, String sDeclaraCode,
			String sUniteCode, int projectType) {
		if (sUniteCode == null || sUniteCode.equals("")) {
			return;
		}
		sYlrh = substring(sYlrh, 11, 17);
		if (sYlrh != null && !sYlrh.equals("")) {
			List dataList = encDao.findCustomsDeclarationbyYlrh(sYlrh,
					projectType);
			if (dataList != null && dataList.size() > 0) {
				BaseCustomsDeclaration customs = (BaseCustomsDeclaration) dataList
						.get(0);
				customs.setUnificationCode(sUniteCode); // 统一编号
				// customs.setIsCheck(new Boolean(false)); //检查标志
				encDao.saveCustomsDeclaration(customs);
			}
		}
	}

	/**
	 * //写H2K入库回执(sType='67'),H2K审核回执(sType='32')的报关单编号
	 * 
	 * @param sYlrh
	 * @param sDeclaraCode
	 * @param sUniteCode
	 */
	public void updateBgdH2k(String sYlrh, String sDeclaraCode,
			String sUniteCode, String sNoticeDate, boolean isIn, int projectType) {
		if (sUniteCode == null || sUniteCode.equals("")) {
			return;
		}
		System.out.println("sylrh:" + sYlrh);
		List dataList = encDao.findCustomsDeclarationbyUniteCode(sUniteCode,
				projectType);
		// List dataList =
		// encDao.findCustomsDeclarationbyYlrh(sYlrh,projectType);
		if (dataList != null && !dataList.isEmpty()) {
			BaseCustomsDeclaration customs = (BaseCustomsDeclaration) dataList
					.get(0);
			if (isIn) {// 写H2K入库回执
				customs.setCustomsDeclarationCodeinHouse(sDeclaraCode);// 入库报关单号
			} else {// H2K审核回执
				customs.setCustomsDeclarationCode(sDeclaraCode);
				customs.setEffective(new Boolean(true)); // 生效
			}
			customs.setIsCheck(new Boolean(false)); // 检查标志
			if(sNoticeDate!=null && !"".equals(sNoticeDate)){
				   sNoticeDate=sNoticeDate.replaceAll("T", "");
				   DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
				   Date date;
					try {
						date = df.parse(sNoticeDate);
						customs.setDeclarationDate(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
			// encDao.saveCustomsDeclaration(customs);
			this.encLogic.saveCustomsDeclaration(customs);
		}
	}

	/**
	 * 得到回执预录入号
	 * 
	 * @param file
	 * @return
	 */
	private String getYLRH(File file) {
		String filename = file.getName();
		int beginch = filename.indexOf("9997");
		try {
			return substring(filename, beginch, beginch + 17);
		} catch (Exception e) {
			return "";
		}
	}

	// 读取回执报文到messageLines
	private List getMessageLines(InputStream messageStream) {
		InputStreamReader fr = new InputStreamReader(messageStream);
		LineNumberReader lnr = new LineNumberReader(fr);
		List messageLines = new Vector();
		String line;
		try {
			while ((line = lnr.readLine()) != null) {
				if (!line.trim().equals("")) { // 去掉空行
					messageLines.add(line);
				}
			}
			fr.close();
			lnr.close();
		} catch (Exception ex) {
			// throw new RuntimeException(ex);
			return null;// 不正常文件
		}

		return messageLines;
	}

//	/**
//	 * 上传报文
//	 * 
//	 * @param files
//	 */
//	public void ftpUpload(List files, int projectType) {
//		setEdiParameter(projectType);
//		FTPClient ftpClient =  FptClientFactory.getFTPClient(addree, 21, ediuser, edipassword,true,CommonUtils.getAclUser().getId());//new FTPClient();
//		try {	
//		
//			if (files == null || files.size() < 0) {
//				logger.info("Login upload empty files.size()<0!");
//				// System.out.println("Login upload empty files.size()<0!");
//				throw new RuntimeException("上传文件为空！，操作被终止。");
//			}
//			System.out.println("files.size():" + files.size());
//
//			for (int i = 0; i < files.size(); i++) {
//				File file = null;
//				try {
//					System.out
//							.println("--begin: ftpClient.changeWorkingDirectory(send_temp);");
//					ftpClient.changeWorkingDirectory("send_temp");
//					System.out
//							.println("--end: ftpClient.changeWorkingDirectory(send_temp);");
//					file = (File) files.get(i);
//					System.out.println("file:" + file.getName());
//					InputStream is = new FileInputStream(file);
//					System.out
//							.println("--begin:ftpClient.storeFile(file.getName(),is)");
//					ftpClient.storeFile(file.getName(), is);
//					System.out
//							.println("--end:ftpClient.storeFile(file.getName(),is)");
//					is.close();
//				} catch (Exception ex) {
//					System.out.println("not find send_temp");
//					ex.printStackTrace();
//					throw new RuntimeException("找不到目录send_temp失败。");
//				}
//				if (ftpClient.changeToParentDirectory()) {
//					String src = "send_temp"+"/"+ file.getName();
//					String dest = "bgd_in" + "/" + file.getName();
//					System.out.println("--begin: ftpClient.rename(src,dest)");
//					if(!ftpClient.rename(src, dest)){
//						throw new RuntimeException("移动上传文件时失败。");
//					}
//					System.out.println("--end: ftpClient.rename(src,dest)");
//					logger.info("成功上传了文件：" + file.getName() + "。");
//
//					// 移动文件 Edit by xxm 2006-11-24 将发送的报文备份到（回执处理完的目录下）
//					File destFile = new File(CommonUtils.getFinallyBgd()
//							+ File.separator + file.getName());
//					moveFile(file, destFile);
//					// file.delete();
//				} else {
//					System.out
//							.println("changeToParentDirectory error 未能切换到父级目录，上传文件");
//					throw new RuntimeException("未能切换到父级目录，上传文件："
//							+ file.getName() + "时失败。");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	/**
//	 * 
//	 * @param fileNames
//	 * @return 下载的文件列表
//	 */
//	public List ftpDownload(int projectType) {
//		setEdiParameter(projectType);
//		List downloadFiles = new Vector();
//		FTPClient ftpClient = FptClientFactory.getFTPClient(addree, 21, ediuser, edipassword,true,CommonUtils.getAclUser().getId());//new FTPClient();
//		try {
//			
//			if (ftpClient.changeWorkingDirectory("bgd_out")) {
//				FTPFile[] ftpFiles = ftpClient.listFiles();
//
//				Thread.currentThread().sleep(3000);
//				for (int i = 0; i < ftpFiles.length; i++) {
//					if (!ftpFiles[i].isFile()) {
//						continue;
//					}
//					File file = new File(CommonUtils.getRecvBgd()
//							+ File.separator + ftpFiles[i].getName());
//					System.out.println(file.getName());
//					if (!file.exists()) {
//						if (!file.createNewFile()) {
//							throw new RuntimeException("未能建立新文件："
//									+ file.getName() + "。");
//						}
//					}
//					FileOutputStream fos = new FileOutputStream(file);
//					//文件下载
//					int num =1;//下载次数，下载回执不合要求，请求三次
//					do{
//						if (ftpClient.retrieveFile("/bgd_out/"+ ftpFiles[i].getName(), fos)) {
//							//判断下载成功的回执行数是否大于要求行5
//							if(getFileCount(file)<5){
//								num ++ ;
//							}else{
//								num =4;
//								ftpClient.deleteFile("/bgd_out/"+ ftpFiles[i].getName());
//								downloadFiles.add(file);
//								logger.info("成功下载" + ftpFiles[i].getName() + "文件。");
//								break;
//							}
//						}else {
//							throw new RuntimeException("未能下载文件"+ ftpFiles[i].getName() + "。");
//						}
//						if(num==3){
//							logger.info( "服务器上的回执："+ftpFiles[i].getName() +"\t 格式不正确...");
//						}
//					}while(num<=3);
////					if (ftpClient.retrieveFile("/bgd_out/"+ ftpFiles[i].getName(), fos)) {
////						ftpClient.deleteFile("/bgd_out/"+ ftpFiles[i].getName());
////						downloadFiles.add(file);
////						logger.info("成功下载" + ftpFiles[i].getName() + "文件。");
////					} else {
////						throw new RuntimeException("未能下载文件"
////								+ ftpFiles[i].getName() + "。");
////					}
//					
//					fos.close();
//				}
//			} else {
//				throw new RuntimeException("未能变更目录到" + CommonUtils.getRecvBgd());
//			}
//			return downloadFiles;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	/**
	 * 取得文件行数
	 * @param file
	 * @return
	 */
	public int getFileCount(File file){
		BufferedReader  fileReader = null;
		try{
			fileReader =new  BufferedReader(new FileReader(file));
			int count =0;
			while(fileReader.readLine()!=null){
				count++;
			}
			return count;
		}catch(Exception e){
			return 0;
		}finally{
			try {
				fileReader.close();
			} catch (IOException e) {
				
			}
		}
	}

//	/**
//	 * 检查是否存在下载通知
//	 */
//	public String checkFtpAdviceInfo(int projectType) {
//		setEdiParameter(projectType);
//		FTPClient ftpClient = FptClientFactory.getFTPClient(addree, 21, ediuser, edipassword,true,CommonUtils.getAclUser().getId());//new FTPClient();
//		try {			
//			if (ftpClient.changeWorkingDirectory("send_out")) {
//				FTPFile[] ftpFiles = ftpClient.listFiles();
//				for (int i = 0; i < ftpFiles.length; i++) {
//					if (!ftpFiles[i].isFile()) {
//						continue;
//					}
//					return ftpFiles[i].getName();
//				}
//			} else {
//				throw new RuntimeException("未找到[send_out]目录!");
//			}
//			return "";
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	/**
//	 * 下载的通知信息
//	 * 
//	 * @param fileNames
//	 * @return
//	 */
//	public List ftpDownloadAdviceInfo(int projectType, String storefile) {
//		setEdiParameter(projectType);
//		List downloadFiles = new Vector();
//		FTPClient ftpClient =  FptClientFactory.getFTPClient(addree, 21, ediuser, edipassword,true,CommonUtils.getAclUser().getId());//new FTPClient();
//		try {		
//			
//			if (ftpClient.changeWorkingDirectory("send_out")) {
//				FTPFile[] ftpFiles = ftpClient.listFiles();
//				Thread.currentThread().sleep(3000);
//				for (int i = 0; i < ftpFiles.length; i++) {
//					if (!ftpFiles[i].isFile()) {
//						continue;
//					}
//					FileOutputStream fos = new FileOutputStream(storefile);
//					if (ftpClient.retrieveFile("/send_out/"
//							+ ftpFiles[i].getName(), fos)) {
//						ftpClient.deleteFile("/send_out/"
//								+ ftpFiles[i].getName());
//						downloadFiles.add(new File(storefile));
//						logger.info("成功下载" + ftpFiles[i].getName() + "文件。");
//					} else {
//						throw new RuntimeException("未能下载文件"
//								+ ftpFiles[i].getName() + "。");
//					}
//					fos.close();
//				}
//			} else {
//				throw new RuntimeException("未能变更目录到" + CommonUtils.getRecvBgd());
//			}
//			return downloadFiles;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	public File[] getCustomBillSendFiles() {
		File path = new File(CommonUtils.getSendBgd());
		return path.listFiles();
	}

	public File[] getCustomBillRecvFiles() {
		File path = new File(CommonUtils.getRecvBgd());
		return path.listFiles();
	}

	/**
	 * @return Returns the exportMessageLogic.
	 */
	public ExportMessageLogic getExportMessageLogic() {
		return exportMessageLogic;
	}

	/**
	 * @param exportMessageLogic
	 *            The exportMessageLogic to set.
	 */
	public void setExportMessageLogic(ExportMessageLogic exportMessageLogic) {
		this.exportMessageLogic = exportMessageLogic;
	}

	// 返回EDI号码
	private void setEdiParameter(int projectType) {
		currCompany = encDao.findCurrCompany();
		if (projectType == ProjectType.BCUS) {
			ediNo = currCompany.getBcusbcusEDICode();// BCUS_EDI
			addree = currCompany.getBcusftpAddress();
			ediuser = currCompany.getBcusftpUser();
			edipassword = currCompany.getBcusftpPassword();
		} else if (projectType == ProjectType.BCS) {
			ediNo = currCompany.getBcsEDICode();
			addree = currCompany.getBcsFtpAddress();
			ediuser = currCompany.getBcsFtpUser();
			edipassword = currCompany.getBcsFtpPassword();
		} else if (projectType == ProjectType.DZSC) {
			ediNo = currCompany.getDzscEDICode();
			addree = currCompany.getDzscFtpAddress();
			ediuser = currCompany.getDzscFtpUser();
			edipassword = currCompany.getDzscFtpPassword();
		}
	}
}