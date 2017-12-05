package com.easytech.request.factory;

import java.util.List;
import java.util.Map;

import com.easytech.request.implementation.HelpRequest;
import com.easytech.request.implementation.IRequest;
import com.easytech.request.implementation.IRequest.ArgumentPartEnum;
import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.request.implementation.PojoGenRequest;
import com.easytech.request.implementation.SampleRequest;

/**
 * Factory class used to create a Request object based on the inputs parsed into 
 * an argument map.
 * 
 * @author WizardOfOz
 *
 */
public class RequestFactory {
	
	/**
	 * Method used to create a request object dependent on the values passed into the function
	 * via the command line as parameters.
	 * 
	 * @param p_argumentMap
	 * @return
	 * @throws Exception
	 */
	public static IRequest createRequest(final Map<ArgumentPartEnum,List<String>> p_argumentMap) throws Exception{
		final boolean hasHelpKey = p_argumentMap.containsKey(ArgumentPartEnum.HELP_PART);
		final boolean hasSampleKey = p_argumentMap.containsKey(ArgumentPartEnum.SAMPLE_PART);
	    final boolean hasClassKey = PojoStaticValues.containsNonNull(p_argumentMap.get(ArgumentPartEnum.CLASS_PART)) ;
	    final boolean hasMemberKey = PojoStaticValues.containsNonNull(p_argumentMap.get(ArgumentPartEnum.MEMBER_PART));
		
		if (hasHelpKey && !hasSampleKey && !hasClassKey && !hasMemberKey){
			return new HelpRequest();
		}
		else if (!hasHelpKey && hasSampleKey && !hasClassKey && !hasMemberKey){
			return new SampleRequest();
		}
		else if (!hasHelpKey && !hasSampleKey && hasClassKey && hasMemberKey){
			return new PojoGenRequest().setArgumentMap(p_argumentMap);
		}	
		else {
			throw new IllegalStateException();
		}
	}
}
