package com.pojogen.application.request.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.pojogen.application.request.HelpRequest;
import com.pojogen.application.request.IRequest;
import com.pojogen.application.request.IRequest.ArgumentPartEnum;
import com.pojogen.application.request.PojoGenRequest;
import com.pojogen.application.request.SampleRequest;
import com.pojogen.application.shared.util.PojoGenValidationHelper;
import com.pojogen.application.shared.util.PojoStaticValues;

/**
 * Factory class used to create a Request object based on the inputs parsed into
 * an argument map.
 * 
 * @author Kashif Hoyte
 *
 */
public class RequestFactory {

	public static IRequest createRequest(final String[] p_arrArgs) throws Exception
	{
		return createRequest(createArgumentMap(p_arrArgs));
	}
	
	/**
	 * Used to create a {@code Map} linking the Argument part type to the value
	 * parsed from the input passed in via the common line.
	 * 
	 * @param p_arrArgs
	 *            Array of arguments retrieved from the command line
	 * @return {@code Map<ArgumentPartEnum, List<String>}
	 * @throws Exception
	 */
	private static Map<ArgumentPartEnum, List<String>> createArgumentMap(final String[] p_arrArgs) throws Exception {
		if (null == p_arrArgs || p_arrArgs.length < 1 || !p_arrArgs[0].startsWith("-")) {
			return Collections.singletonMap(ArgumentPartEnum.HELP_PART, Collections.emptyList());
		}

		final Map<ArgumentPartEnum, List<String>> argumentMap = new HashMap<>();
		ArgumentPartEnum argPart = null;
		for (String arg : p_arrArgs) {
			if (arg.startsWith("-")) {
				argPart = ArgumentPartEnum.getPart(arg);

				if (ArgumentPartEnum.ILLEGAL_PART.equals(argPart)) {
					throw new Exception("Illegal arguments.\n" + PojoStaticValues.DEFAULT_ERROR_MESSAGE);
				}

				argumentMap.put(argPart, new ArrayList<String>());
			} else {
				/** associate date to it parameter part **/
				Objects.requireNonNull(argPart, "Non-Null Argument Part is required.");

				argumentMap.get(argPart).add(arg);
			}
		}
		return argumentMap;
	}
	
	/**
	 * Method used to create a request object dependent on the values passed
	 * into the function via the command line as parameters.
	 * 
	 * @param p_argumentMap
	 * @return
	 * @throws Exception
	 */
	public static IRequest createRequest(final Map<ArgumentPartEnum, List<String>> p_argumentMap) throws Exception {
		final boolean hasHelpKey = p_argumentMap.containsKey(ArgumentPartEnum.HELP_PART);
		final boolean hasSampleKey = p_argumentMap.containsKey(ArgumentPartEnum.SAMPLE_PART);
		final boolean hasClassKey = PojoGenValidationHelper.containsNonNull(p_argumentMap.get(ArgumentPartEnum.CLASS_PART));
		final boolean hasMemberKey = PojoGenValidationHelper.containsNonNull(p_argumentMap.get(ArgumentPartEnum.MEMBER_PART));

		if (hasHelpKey && !hasSampleKey && !hasClassKey && !hasMemberKey) {
			return new HelpRequest();
		} else if (!hasHelpKey && hasSampleKey && !hasClassKey && !hasMemberKey) {
			return new SampleRequest();
		} else if (!hasHelpKey && !hasSampleKey && hasClassKey && hasMemberKey) {
			return new PojoGenRequest().setArgumentMap(p_argumentMap);
		} else {
			throw new IllegalStateException();
		}
	}

}
