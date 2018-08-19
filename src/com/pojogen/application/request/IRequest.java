package com.pojogen.application.request;

import java.util.List;
import java.util.Map;

import com.pojogen.application.pojo.component.Pojo;

/**
 * Class used to represent a request from the user which is made of two
 * behaviors or functions, process() performs the work needed to be done in order to satisfy the 
 * request and setArgumentMap() performs the initial setup needed to make the process() method work.
 * 
 * @author Kashif Hoyte
 *
 */
public interface IRequest{
	public enum ArgumentPartEnum{
		CLASS_PART("-c"), MEMBER_PART("-m"), SAMPLE_PART("-sample"), HELP_PART("-help"), OUTPUT_DIRECTORY_PART("-o"), ILLEGAL_PART("ILLEGAL PART");
		final String strKey;
		
		private ArgumentPartEnum(final String p_strKey){
			strKey = p_strKey;
		}
		
		public String getKey(){
			return strKey;
		}
		
		public static ArgumentPartEnum getPart(final String strData){
			for (ArgumentPartEnum partEnum : values()){
				if (!ILLEGAL_PART.equals(partEnum) && partEnum.getKey().equals(strData)){
					return partEnum;
				}
			}
			
			return ArgumentPartEnum.ILLEGAL_PART;
		}
	}
	
	Pojo process() throws Exception;
	IRequest setArgumentMap(final Map<ArgumentPartEnum,List<String>> p_argumentMap) throws Exception;
}
