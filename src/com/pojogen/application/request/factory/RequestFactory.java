package com.pojogen.application.request.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.pojogen.application.request.HelpRequest;
import com.pojogen.application.request.IRequest;
import com.pojogen.application.request.IRequest.CommandLineArgument;
import com.pojogen.application.request.PojoGenRequest;
import com.pojogen.application.request.SampleRequest;

/**
 * Factory class used to create a Request object based on the inputs parsed into
 * an argument map.
 * 
 * @author Kashif Hoyte
 *
 */
public final class RequestFactory {

	private RequestFactory() {
	}

	public static IRequest createRequest(final String[] p_arrArgs) {
		try {
			return createRequest(createArgumentMap(p_arrArgs));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method used to create a request object dependent on the values passed
	 * into the function via the command line as parameters.
	 * 
	 * @param p_argumentMap
	 * @return
	 * @throws Exception
	 */
	public static IRequest createRequest(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {

		if (containsHelpRequest(p_argMap)) {
			return new HelpRequest();
		} else if (containsSampleRequest(p_argMap)) {
			return new SampleRequest();
		} else if (containsPojoRequest(p_argMap)) {
			return new PojoGenRequest().setArgumentMap(p_argMap);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private static boolean containsHelpRequest(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {
		Objects.requireNonNull(p_argMap);

		return p_argMap.size() == 1 && p_argMap.containsKey(CommandLineArgument.HELP);
	}

	private static boolean containsSampleRequest(final Map<CommandLineArgument, List<String>> p_argMap)
			throws Exception {
		Objects.requireNonNull(p_argMap);

		return p_argMap.size() == 1 && p_argMap.containsKey(CommandLineArgument.SAMPLE);
	}

	private static boolean containsPojoRequest(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {
		Objects.requireNonNull(p_argMap);

		return p_argMap.size() == 2 && p_argMap.containsKey(CommandLineArgument.CLASS)
				&& p_argMap.containsKey(CommandLineArgument.MEMBER);
	}

	/**
	 * Used to create a {@code Map} linking the Argument part type to the value
	 * parsed from the input passed in via the common line.
	 * 
	 * @param args
	 *            Array of arguments retrieved from the command line
	 * @return {@code Map<ArgumentPartEnum, List<String>}
	 * @throws Exception
	 */
	private static Map<CommandLineArgument, List<String>> createArgumentMap(final String[] args) throws Exception {
		if (null == args || args.length < 1) {
			HelpRequest.throwRequest();
		}

		final Map<CommandLineArgument, List<String>> argMap = new HashMap<>();
		for (String arg : args) {
			if (!arg.startsWith("-")) {
				HelpRequest.throwRequest();
			}
			argMap.put(CommandLineArgument.getArgument(arg), new ArrayList<String>());
		}
		return argMap;
	}
}
