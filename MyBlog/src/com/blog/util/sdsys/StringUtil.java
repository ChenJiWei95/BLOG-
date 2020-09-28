package com.blog.util.sdsys;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
	private static final String algorithm = "MD5";
	public static Pattern orderAmountPattern = Pattern.compile("\\d{1,16}");

	public static Pattern orderNoPattern = Pattern.compile("[\\w,_,-]{1,50}");

	public static Pattern mobileNoPattern = Pattern.compile("^(13[0-9]|15[0-9]|18[0-9])\\d{8}$");

	public static Pattern ratePattern = Pattern
			.compile("^((([1-9]{1}\\d{0,1}))|([0]{1}))((\\.(\\d){1,2}))?$|(100|100.0|100.00)");

	public static Pattern emailPattern = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$");

	public static Pattern ipAndPortPattern = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}:\\d{1,5}");

	public static final String DEFAULT_CHARSET = "utf-8";

	public static String getNullableResult(byte[] returnValue) {
		String str = null;
		try {
			if ((returnValue != null) || (returnValue.length != 0)) {
				str = new String(returnValue, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String getNullableResult(Object input) {
		return input == null ? "" : input.toString();
	}

	public static byte[] getByteArrayFromStr(String str) {
		byte[] bytes = null;
		try {
			if (StringUtils.isNotBlank(str)) {
				bytes = str.getBytes("utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static byte[] getbytefromobject(Object oo) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bs);
			oos.writeObject(oo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bs.toByteArray();
	}

	public static boolean isNull(Object object) {
		if ((object instanceof String)) {
			return isEmpty(String.valueOf(object));
		}
		return object == null;
	}

	public static boolean isEmpty(String src) {
		if ((null == src) || ("".equals(src))) {
			return true;
		}
		return false;
	}

	public static String toString(Object obj) {
		if (null == obj) {
			return null;
		}
		return String.valueOf(obj);
	}

	public static String capFirst(String src) {
		if (isEmpty(src)) {
			return src;
		}
		if (src.length() == 1) {
			return src.toUpperCase();
		}
		String first = src.substring(0, 1);
		first = first.toUpperCase();
		return first + src.substring(1);
	}

	public static String uncapFirst(String src) {
		if (isEmpty(src)) {
			return src;
		}
		if (src.length() == 1) {
			return src.toLowerCase();
		}
		String first = src.substring(0, 1);
		first = first.toLowerCase();
		return first + src.substring(1);
	}

	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	public static String replace(String text, String repl, String with, int max) {
		if (text == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0;
		int end = text.indexOf(repl, start);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			max--;
			if (max == 0) {
				break;
			}
			end = text.indexOf(repl, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String replaceVariable(String src, Map value) {
		int len = src.length();
		StringBuffer buf = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			char c = src.charAt(i);
			if (c == '$') {
				i++;
				StringBuffer key = new StringBuffer();
				char temp = src.charAt(i);
				while (temp != '}') {
					if (temp != '{') {
						key.append(temp);
					}
					i++;
					temp = src.charAt(i);
				}
				String variable = (String) value.get(key.toString());
				if (null == variable) {
					buf.append("");
				} else {
					buf.append(variable);
				}
			} else {
				buf.append(c);
			}
		}
		return buf.toString();
	}

	public static String relaceCapitalWith_AndLowercase(String src) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isLowerCase(c)) {
				buf.append(c);
			} else if (Character.isUpperCase(c)) {
				buf.append('_').append(Character.toLowerCase(c));
			} else {
				buf.append(c);
			}
		}
		return buf.toString();
	}

	public static String replace_AndLowercaseWithCapital(String src) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if ('_' == c) {
				i++;
				if (i < src.length() - 1) {
					c = src.charAt(i);
					buf.append(Character.toUpperCase(c));
				}
			} else {
				buf.append(c);
			}
		}
		return buf.toString();
	}

	public static void appendSignPara(StringBuffer buf, String key, String value) {
		if (!isEmpty(value)) {
			buf.append(key).append('=').append(value).append('&');
		}
	}

	public static void appendLastSignPara(StringBuffer buf, String key, String value) {
		if (!isEmpty(value)) {
			buf.append(key).append('=').append(value);
		}
	}

	public static void appendUrlPara(StringBuffer buf, String key, String value) {
		if (!isEmpty(value)) {
			try {
				buf.append(key).append('=').append(URLEncoder.encode(value, "utf-8")).append('&');
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public static void appendLastUrlPara(StringBuffer buf, String key, String value) {
		if (!isEmpty(value)) {
			try {
				buf.append(key).append('=').append(URLEncoder.encode(value, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public static void appendUrlParaForGetting(StringBuffer buf, String key, String value) {
		if (!isEmpty(value)) {
			try {
				buf.append(key).append('=').append(URLEncoder.encode(value, "utf-8")).append('&');
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			buf.append(key).append('=').append('&');
		}
	}

	public static String stuffString(String src, int len, boolean stuffHead, char padding) {
		if (len <= 0) {
			return src;
		}
		if (isEmpty(src)) {
			src = "";
		}
		int srcLen = src.length();
		StringBuffer buf = new StringBuffer(len);
		int paddingLen = len - srcLen;
		for (int i = 0; i < paddingLen; i++) {
			buf.append(padding);
		}
		if (stuffHead) {
			buf.append(src);
		} else {
			buf.insert(0, src);
		}
		return buf.toString();
	}

	public static String padDataWithLen(String data) {
		int len = data.length();
		String lenStr = String.valueOf(len);
		if (len < 10) {
			lenStr = "0" + lenStr;
		}
		return lenStr + data;
	}

	public static boolean isNumber(String data) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(data);
		return isNum.matches();
	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isMoney(String money) {
		String[] tmpMoney = money.split("[.]");
		String tmp = "";
		for (int i = 0; i < tmpMoney.length; i++) {
			tmp = tmp + tmpMoney[i];
		}

		if (!isNumber(tmp)) {
			return false;
		}
		if (!money.contains(".")) {
			return false;
		}
		for (int i = 0; i < tmpMoney.length; i++) {
			if (i == 1) {
				tmp = "";
				tmp = tmp + tmpMoney[i];
			}
		}

		if (tmp.length() != 2) {
			return false;
		}

		return true;
	}

	public static String parseNotifyResponse(String notifyMerchantResponse) {
		if (isEmpty(notifyMerchantResponse)) {
			return null;
		}
		int index1 = notifyMerchantResponse.indexOf("<pickupUrl>");
		if (index1 < 0) {
			return null;
		}
		int index2 = notifyMerchantResponse.indexOf("</pickupUrl>", index1);
		if (index2 < 0) {
			return null;
		}
		index1 += "<pickupUrl>".length();
		return notifyMerchantResponse.substring(index1, index2);
	}

	public static String stringTrim(String value) {
		if ("null".equals(value)) {
			return null;
		}
		return value.trim();
	}

	public static String fillLLVAR(String str) {
		if ((str == null) || ("null".equals(str))) {
			return null;
		}
		String lenTemp = "";
		int length = str.length();
		if (length == 0)
			return "00";
		if (length < 10)
			lenTemp = "0";
		if ((length >= 10) && (length < 100)) {
			lenTemp = "";
		}
		StringBuffer sb = new StringBuffer("").append(lenTemp).append(length).append(str);
		return sb.toString();
	}

	public static String fillLLLVAR(String str) {
		if ((str == null) || ("null".equals(str))) {
			return null;
		}
		String lenTemp = "";
		int length = str.length();
		if (length == 0)
			return "000";
		if (length < 10)
			lenTemp = "00";
		if ((length >= 10) && (length < 100))
			lenTemp = "0";
		if ((length >= 100) && (length < 1000)) {
			lenTemp = "";
		}
		StringBuffer sb = new StringBuffer("").append(lenTemp).append(length).append(str);
		return sb.toString();
	}

	public static String fillString(String string, char filler, int totalLength, boolean atEnd) {
		byte[] tempbyte = null;
		try {
			tempbyte = string.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		int currentLength = tempbyte.length;
		int delta = totalLength - currentLength;
		for (int i = 0; i < delta; i++) {
			if (atEnd) {
				string = string + filler;
			} else {
				string = filler + string;
			}
		}
		return string;
	}

	public static String getTagValue(String strXML, String tagName) {
		if ((null == tagName) || ("".equals(tagName))) {
			return "";
		}
		String startTag = "<" + tagName + ">";
		String endTag = "</" + tagName + ">";
		if ((null == strXML) || (strXML.indexOf(startTag) == -1) || (strXML.indexOf(endTag) == -1)) {
			return "";
		}

		return strXML.substring(strXML.indexOf(startTag) + startTag.length(), strXML.indexOf(endTag));
	}

	public static String getTagNameAndValue(String strXML, String tagName) {
		if ((null == tagName) || ("".equals(tagName))) {
			return "";
		}
		String startTag = "<" + tagName + ">";
		String endTag = "</" + tagName + ">";
		if ((null == strXML) || (strXML.indexOf(startTag) == -1) || (strXML.indexOf(endTag) == -1)) {
			return "";
		}
		return strXML.substring(strXML.indexOf(startTag), strXML.indexOf(endTag) + endTag.length());
	}

	public static int getBytes(String data) {
		byte[] tempbyte = null;
		try {
			tempbyte = data.getBytes("gbk");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return tempbyte.length;
	}

	public static boolean checkLength(String data, int maxLength) {
		if (getBytes(data) > maxLength)
			return false;
		return true;
	}

	public static String encodePassword(String password) {
		if (StringUtils.isNotBlank(password)) {
			byte[] unencodedPassword = password.getBytes();

			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (Exception e) {
				e.printStackTrace();
			}
			md.reset();

			md.update(unencodedPassword);

			byte[] encodedPassword = md.digest();
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < encodedPassword.length; i++) {
				if ((encodedPassword[i] & 0xFF) < 16) {
					buf.append("0");
				}
				buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
			}
			return buf.toString();
		}
		return null;
	}

	public static boolean isPatternMatcher(Pattern p, String matherStr) {
		Matcher m = p.matcher(matherStr);
		return m.matches();
	}

	public static String unescapeXml(String strXML, String tagName) {
		if (isEmpty(tagName)) {
			return strXML;
		}
		String tagNameAndValue = getTagNameAndValue(strXML, tagName);
		if (isEmpty(tagNameAndValue)) {
			return strXML;
		}
		tagNameAndValue = StringEscapeUtils.unescapeXml(tagNameAndValue);

		String regex = "<" + tagName + ">.*</" + tagName + ">";
		return strXML.replaceAll(regex, tagNameAndValue);
	}
}
