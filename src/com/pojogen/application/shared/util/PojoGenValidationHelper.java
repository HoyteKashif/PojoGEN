package com.pojogen.application.shared.util;

import java.util.List;

/**
 * 
 * @author Kashif Hoyte
 *
 */
public class PojoGenValidationHelper {

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

	public static boolean containsNonNull(final List<String> p_lstData) {
		if (null == p_lstData)
			return false;

		for (String arg : p_lstData) {
			if (null == arg) {
				return false;
			}
		}
		return true;
	}
}
