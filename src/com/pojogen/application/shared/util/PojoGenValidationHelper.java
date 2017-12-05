package com.pojogen.application.shared.util;

import java.util.List;

/**
 * 
 * @author Kashif Hoyte
 *
 */
public class PojoGenValidationHelper {

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
