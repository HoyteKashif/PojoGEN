package com.pojogen.application.shared.util;

public class StringHelper {

	public final static String EMPTY_STRING = "";
	
	public static boolean isEmpty(final String p_strData) {
		return !hasText(p_strData);
	}

	public static boolean hasText(final String p_strData) {
		if (null == p_strData) {
			return false;
		}
		
		if (p_strData.isEmpty()) {
			return false;
		}
		if (p_strData.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public static String rightPadString(final String p_strData, final int p_iCount) {
		return p_strData;
	}

	public static String multiplyString(final String p_strData, final int p_iCount) {
		if (p_iCount == 0) {
			return EMPTY_STRING;
		}
		if (p_iCount == 1) {
			return p_strData;
		}
		return p_strData.concat(multiplyString(p_strData, p_iCount - 1));
	}

	public static String replaceCharAt(final String p_strData, final int p_iCharIndex, final String p_strReplacement) {
		if (hasText(p_strData)) {
			return p_strData.substring(0, p_iCharIndex) + p_strReplacement + p_strData.substring(p_iCharIndex + 1);
		}
		return null;
	}

}
