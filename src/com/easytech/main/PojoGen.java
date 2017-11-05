package com.easytech.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.easytech.request.factory.RequestFactory;
import com.easytech.request.implementation.IRequest.ArgumentPartEnum;
import com.easytech.staticvalues.PojoStaticValues;

public class PojoGen {
	
	public static void main(String[] args){
		try{			
			RequestFactory.createRequest(createArgumentMap(args)).process();
		}catch (final Exception e){
			try {
				RequestFactory.createRequest(Collections.singletonMap(ArgumentPartEnum.HELP_PART, Collections.emptyList())).process();
			} catch (Exception e1) {
				System.out.println("Unable to process the Pojo Request");
			}
		}
	}
	
	/**
	 * Used to create a {@code Map} linking the Argument part type to the value parsed 
	 * from the input passed in via the common line.
	 * 
	 * @param p_arrArgs Array of arguments retrieved from the command line
	 * @return {@code Map<ArgumentPartEnum, List<String>}
	 * @throws Exception
	 */
	private static Map<ArgumentPartEnum,List<String>> createArgumentMap(final String[] p_arrArgs) throws Exception{
		if (p_arrArgs.length < 1 || !p_arrArgs[0].startsWith("-")){
			return Collections.singletonMap(ArgumentPartEnum.HELP_PART, Collections.emptyList());
		}
		
		final Map<ArgumentPartEnum,List<String>> argumentMap = new HashMap<>();
		ArgumentPartEnum argPart = null;
		for (String arg : p_arrArgs){
			if (arg.startsWith("-")){
				argPart = ArgumentPartEnum.getPart(arg);
				
				if (ArgumentPartEnum.ILLEGAL_PART.equals(argPart)){
					throw new Exception("Illegal arguments.\n"
							+ PojoStaticValues.DEFAULT_ERROR_MESSAGE);
				}
				
				argumentMap.put(argPart, new ArrayList<String>());
			}else{
				/** associate date to it parameter part **/
				Objects.requireNonNull(argPart, "Non-Null Argument Part is required.");
				
				argumentMap.get(argPart).add(arg);
			}
		}
		
		return argumentMap;
	}
}
