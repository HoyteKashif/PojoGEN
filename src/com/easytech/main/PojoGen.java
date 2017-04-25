package com.easytech.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.easytech.generator.PojoClassGenerator;
import com.easytech.staticvalues.PojoStaticValues;

public class PojoGen implements PojoStaticValues{
	
	enum ArgumentKeyEnum{
		CLASS_PART, MEMBER_PART, SAMPLE_PART, HELP_PART, OUTPUT_DIRECTORY_PART
	}
	
	public static void main(String[] args){
		try{
			processRequest( getArgumentMap(args));
		}catch (final Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	private static void processRequest(Map<ArgumentKeyEnum,Set<String>> p_argMap) throws Exception{
		if (isHelpRequest(p_argMap)){
			processHelpRequest(p_argMap);
		}else{
			processPojoGenRequest(p_argMap);
		}
	}
	
	//FIXME: only use this to verify the contents to the help and sample parts
	private static boolean isHelpRequest(Map<ArgumentKeyEnum,Set<String>> p_argMap) throws Exception{
		if (p_argMap.containsKey(ArgumentKeyEnum.HELP_PART)){
			return true;
		} else if (!p_argMap.containsKey(ArgumentKeyEnum.HELP_PART) && p_argMap.containsKey(ArgumentKeyEnum.SAMPLE_PART)){
			throw new Exception(DEFAULT_HELP_MESSAGE);
		}
		return false;
	}
	
	private static void processPojoGenRequest(Map<ArgumentKeyEnum,Set<String>> p_argMap) throws Exception{
		// check whether class_part and member part were supplied but only if it is a request for a POJO and not a sample or help text
		validateArguments(p_argMap);	

		//FIXME: possibly a better way to do it is by removing the type parameters
		String strClassName = "";
		if (!p_argMap.get(ArgumentKeyEnum.CLASS_PART).isEmpty()){
			strClassName = p_argMap.get(ArgumentKeyEnum.CLASS_PART).iterator().next();
		}

		System.out.println(PojoClassGenerator.buildPojo( strClassName, p_argMap.get(ArgumentKeyEnum.MEMBER_PART)));
	}
	
	private static void processHelpRequest(Map<ArgumentKeyEnum,Set<String>> p_argMap) throws Exception{
		if (p_argMap.containsKey(ArgumentKeyEnum.SAMPLE_PART)){
			outputSamplePOJO();
		}else{
			outputHelpMessage();
		}
	}
	
	//FIXME: only use this to verify the contents of the class and member parts
	private static void validateArguments(Map<ArgumentKeyEnum,Set<String>> p_argMap)throws Exception{
		if (p_argMap.isEmpty()){
			throw new Exception(DEFAULT_ERROR_MESSAGE);
		} else if (!p_argMap.containsKey(ArgumentKeyEnum.CLASS_PART) || !p_argMap.get(ArgumentKeyEnum.CLASS_PART).isEmpty()){
			throw new Exception("Missing class part.\n"
					+ DEFAULT_ERROR_MESSAGE);
		} else if (!p_argMap.containsKey(ArgumentKeyEnum.MEMBER_PART) || !p_argMap.get(ArgumentKeyEnum.MEMBER_PART).isEmpty()){
			throw new Exception("Missing member part.\n"
					+ DEFAULT_ERROR_MESSAGE);
		}
	}
	
	//FIXME: make it so that this method only splits up the arguments into a map and checking the parameters 
	// should happen in another method
	private static Map<ArgumentKeyEnum,Set<String>> getArgumentMap(String[] args) throws Exception{
		Map<ArgumentKeyEnum,Set<String>> argMap = new HashMap<>();
		ArgumentKeyEnum argKey = null;
		for (String arg : args){
			if (arg.startsWith("-")){
				if (arg.equals("-help")){
					argKey = ArgumentKeyEnum.HELP_PART;
				}else if (arg.equals("-sample")){
					argKey = ArgumentKeyEnum.SAMPLE_PART;
				}else if (arg.equals("-o")){
					argKey = ArgumentKeyEnum.OUTPUT_DIRECTORY_PART;
				}else if (arg.equals("-c")){
					argKey = ArgumentKeyEnum.CLASS_PART;
				}else if (arg.equals("-p")){
					argKey = ArgumentKeyEnum.MEMBER_PART;
				}else{
					throw new Exception("Illegal arguments.\n"
							+ DEFAULT_ERROR_MESSAGE);
				}
				argMap.put(argKey, new HashSet<String>());
			}else{
				/** associate date to it parameter part **/
				if (null != argKey && !argMap.isEmpty()){
					Set<String> prevSet = argMap.get(argKey);
					prevSet.add(arg);
					argMap.put(argKey, prevSet);
				}else{
					throw new Exception("Illegal program flow.");
				}
			}
		}
		return argMap;
	}
	
	//TODO: Implement it but not sure how it will fit into the structure
	// ~ possibly accept the POJO and output it to the file
	private static void outputToDirectory(){
		
	}
	
	//TODO: Add to the work-flow only when the user gives has a help part in the arguments input
	private static void outputHelpMessage(){
		System.out.println(DEFAULT_ERROR_MESSAGE + "\n"
				+ "Use the following DataType extensions:\n"
				+ DataTypeEnum.getOptions());
	}
	
	//TODO: Place this after all the checks have been done as the second to last part before outputting the POJO to a file
	private static void outputSamplePOJO() throws Exception{
		final Set<String> sampleMembers = new HashSet<>();
		sampleMembers.add("fizz_string");
		sampleMembers.add("buzz_string");
		sampleMembers.add("fizz_buzz_bigdecimal");
		
		System.out.println("ex: PojoGen -c FizzBuzz -p fizz_string buzz_string fizz_buzz_bigdecimal\n");
		System.out.println(PojoClassGenerator.buildPojo( "FizzBuzz", sampleMembers));
	}
}
