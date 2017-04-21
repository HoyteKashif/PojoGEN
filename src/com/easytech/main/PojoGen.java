package com.easytech.main;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.easytech.generator.PojoClassGenerator;
import com.easytech.staticvalues.PojoStaticValues;



public class PojoGen implements PojoStaticValues{
	
	enum ArgumentKeyEnum{
		CLASS_PART, MEMBER_PART
	}
	
	public static void main(String[] args){
		try{
//			System.out.println("Parsing command line arguments");
			Map<ArgumentKeyEnum,Set<String>> argMap = getArgumentMap(args);
//			System.out.println("Validating command line arguments");
			validateArguments(argMap);
			
			//FIXME: possibly a better way to do it is by removing the type parameters
			String strClassName = "";
			if (!argMap.get(ArgumentKeyEnum.CLASS_PART).isEmpty()){
				strClassName = argMap.get(ArgumentKeyEnum.CLASS_PART).iterator().next();
			}
//			System.out.println("Starting Pojo Generation");
			System.out.println(PojoClassGenerator.buildPojo( strClassName, argMap.get(ArgumentKeyEnum.MEMBER_PART)));
//			System.out.println(argMap);
		}catch (final Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	private static void validateArguments(Map<ArgumentKeyEnum,Set<String>> p_argMap)throws Exception{
		if (p_argMap.isEmpty()){
			throw new Exception(DEFAULT_ERROR_MESSAGE);
		} else if (!p_argMap.containsKey(ArgumentKeyEnum.CLASS_PART)){
			throw new Exception("Missing class part.\n"
					+ DEFAULT_ERROR_MESSAGE);
		} else if (!p_argMap.containsKey(ArgumentKeyEnum.MEMBER_PART)){
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
				if(arg.equals("-help")){
					System.out.println(DEFAULT_ERROR_MESSAGE + "\n"
						+ "Use the following DataType extensions:\n"
						+ DataTypeEnum.getOptions());
					System.exit(0);
				}else if(arg.equals("-sample")){
					Set<String> sampleMembers = new HashSet<>();
					sampleMembers.add("fizz_string");
					sampleMembers.add("buzz_string");
					sampleMembers.add("fizz_buzz_bigdecimal");
					System.out.println("ex: PojoGen -c FizzBuzz -p fizz_string buzz_string fizz_buzz_bigdecimal\n");
					System.out.println(PojoClassGenerator.buildPojo( "FizzBuzz", sampleMembers));
					System.exit(0);
				}else if(arg.equals("-c")){
					argKey = ArgumentKeyEnum.CLASS_PART;
				}else if(arg.equals("-p")){
					argKey = ArgumentKeyEnum.MEMBER_PART;
				}else{
					throw new Exception("Illegal arguments.\n"
							+ DEFAULT_ERROR_MESSAGE);
				}
				argMap.put(argKey, new HashSet<String>());
			}else{
				if (!argMap.isEmpty()){
					Set<String> prevSet = argMap.get(argKey);
					prevSet.add(arg);
					argMap.put(argKey, prevSet);
				}
			}
		}
		return argMap;
	}
	
}
