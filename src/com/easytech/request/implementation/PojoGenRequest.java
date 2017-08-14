package com.easytech.request.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import com.easytech.pojo.component.generator.Pojo;
import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

/**
 * Class used to represent a request from the user to create a JAVA class or object model (POJO).
 *  
 * @author WizardOfOz
 *
 */
public class PojoGenRequest implements IRequest {
	Map<ArgumentPartEnum, List<String>> m_argumentMap;

	public PojoGenRequest() {
	}

	@Override
	public void process() throws Exception {

		String strClassName = "";
		List<String> lstClassPartValues = m_argumentMap.get(ArgumentPartEnum.CLASS_PART);
		if (!lstClassPartValues.isEmpty()) {
			strClassName = lstClassPartValues.get(0);
		}

		outputToCurrentWorkingDirectory(strClassName, Pojo.PojoBuilder.getPojo(strClassName, m_argumentMap.get(ArgumentPartEnum.MEMBER_PART)).toString().getBytes());
	}

	/**
	 * This method determines whether or not the user has submitted all the values necessary 
	 * to allow for this request to be processed. If the user has not included a value necessary for 
	 * this request an error message is output to the command line.
	 */
	@Override
	public IRequest setArgumentMap(final Map<ArgumentPartEnum, List<String>> p_argumentMap) throws Exception {
		if (p_argumentMap.isEmpty()) {
			throw new Exception(PojoStaticValues.DEFAULT_ERROR_MESSAGE);
		} else if (!p_argumentMap.containsKey(ArgumentPartEnum.CLASS_PART)
				|| p_argumentMap.get(ArgumentPartEnum.CLASS_PART).isEmpty()) {
			throw new Exception("Missing class part.\n" + PojoStaticValues.DEFAULT_ERROR_MESSAGE);
		} else if (!p_argumentMap.containsKey(ArgumentPartEnum.MEMBER_PART)
				|| p_argumentMap.get(ArgumentPartEnum.MEMBER_PART).isEmpty()) {
			throw new Exception("Missing member part.\n" + PojoStaticValues.DEFAULT_ERROR_MESSAGE);
		} else if (!DataTypeEnum.validTypes(p_argumentMap.get(ArgumentPartEnum.MEMBER_PART))) {
			throw new Exception("Illegal Member parameter\n\n" + PojoStaticValues.DEFAULT_ERROR_MESSAGE + "\n"
					+ DataTypeEnum.getOptions());
		}

		// passed all the test so set it
		this.m_argumentMap = p_argumentMap;
		return this;
	}
	
	/**
	 * Used to output data to the current working Directory.
	 * @param p_strClassname
	 * @param p_Bytes
	 * @throws Exception
	 */
	private void outputToCurrentWorkingDirectory(final String p_strClassname, final byte[] p_Bytes) throws Exception {

		try {
			final String cwd = (String) System.getProperties().get("user.dir");
			final String separator = (String) System.getProperties().getProperty("file.separator");
			final String filename = cwd + separator + p_strClassname + ".java";
			
			Files.write(new File(filename).toPath(), p_Bytes, StandardOpenOption.CREATE_NEW);
		} catch (final IOException e) {
			throw new Exception("Error when trying to write the Pojo to a file.", e);
		}
	}
}
