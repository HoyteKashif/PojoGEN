package com.pojogen.application.request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import com.pojogen.application.pojo.component.Pojo;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.StaticValues;

/**
 * Class used to represent a request from the user to create a JAVA class or
 * object model (POJO).
 * 
 * @author Kashif Hoyte
 *
 */
public class PojoGenRequest implements IRequest {
	Map<CommandLineArgument, List<String>> argumentMap;

	public PojoGenRequest() {
	}

	@Override
	public Pojo process() throws Exception {

		String className = "";
		final List<String> classParts = argumentMap.get(CommandLineArgument.CLASS);
		if (!classParts.isEmpty()) {
			className = classParts.get(0);
		}

		return new Pojo.Builder(className, argumentMap.get(CommandLineArgument.MEMBER)).build();
	}

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

	/**
	 * This method determines whether or not the user has submitted all the
	 * values necessary to allow for this request to be processed. If the user
	 * has not included a value necessary for this request an error message is
	 * output to the command line.
	 */
	@Override
	public IRequest setArgumentMap(final Map<CommandLineArgument, List<String>> p_argMap) throws Exception {

		validateArgumentMap(p_argMap);

		// passed all the test so set it
		this.argumentMap = p_argMap;

		return this;
	}

	/**
	 * Used to output data to the current working Directory.
	 * 
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
