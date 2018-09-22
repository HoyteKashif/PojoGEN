package com.pojogen.application.shared.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.pojogen.application.request.IRequest.CommandLineArgument;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

public class RequestHelper {
	private void validateArgumentMap(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {
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

	public static boolean containsHelpRequest(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {
		if (null == p_argMap || p_argMap.isEmpty()) {
			return false;
		}

		if (p_argMap.size() > 1) {
			return false;
		}

		return p_argMap.containsKey(CommandLineArgument.HELP);
	}

	public static boolean containsSampleRequest(final Map<CommandLineArgument, List<String>> p_argMap)
			throws Exception {
		if (null == p_argMap || p_argMap.size() != 1) {
			return false;
		}

		return p_argMap.containsKey(CommandLineArgument.SAMPLE);
	}

	public static boolean containsPojoRequest(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {
		if (null == p_argMap || p_argMap.size() != 2) {
			return false;
		}

		return p_argMap.containsKey(CommandLineArgument.CLASS) && p_argMap.containsKey(CommandLineArgument.MEMBER);
	}
}
