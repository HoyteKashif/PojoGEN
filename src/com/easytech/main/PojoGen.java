package com.easytech.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				if (null != argPart){
					argumentMap.get(argPart).add(arg);
				}else{
					throw new Exception("Illegal program flow.");
				}
			}
		}
		
		return argumentMap;
	}
}
