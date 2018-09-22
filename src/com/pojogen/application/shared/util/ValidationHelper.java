package com.pojogen.application.shared.util;

import java.util.List;
import java.util.Map;

import com.pojogen.application.request.IRequest.CommandLineArgument;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

/**
 * 
 * @author Kashif Hoyte
 *
 */
public final class ValidationHelper {

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

	public static void requireValidArgumentMap(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {
		if (p_argMap.isEmpty()) {
			throw new Exception(StaticValues.DEFAULT_ERROR_MESSAGE);
		} else if (!p_argMap.containsKey(CommandLineArgument.CLASS)
				|| p_argMap.get(CommandLineArgument.CLASS).isEmpty()) {
			throw new Exception("Missing class part.\n" + StaticValues.DEFAULT_ERROR_MESSAGE);
		} else if (!p_argMap.containsKey(CommandLineArgument.MEMBER)
				|| p_argMap.get(CommandLineArgument.MEMBER).isEmpty()) {
			throw new Exception("Missing member part.\n" + StaticValues.DEFAULT_ERROR_MESSAGE);
		} else if (!DataTypeEnum.validTypes(p_argMap.get(CommandLineArgument.MEMBER))) {
			throw new Exception("Illegal Member parameter\n\n" + StaticValues.DEFAULT_ERROR_MESSAGE + "\n"
					+ DataTypeEnum.getOptions());
		}
	}

	private ValidationHelper() {
	}
}
