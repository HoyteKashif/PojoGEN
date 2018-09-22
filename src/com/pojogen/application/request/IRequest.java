package com.pojogen.application.request;

import java.util.List;
import java.util.Map;

import com.pojogen.application.pojo.component.Pojo;

/**
 * Class used to represent a request from the user which is made of two
 * behaviors or functions, process() performs the work needed to be done in
 * order to satisfy the request and setArgumentMap() performs the initial setup
 * needed to make the process() method work.
 * 
 * @author Kashif Hoyte
 *
 */
public interface IRequest {
	public enum CommandLineArgument {
		CLASS("-c"), MEMBER("-m"), SAMPLE("-s"), HELP("-h"), OUTPUT_DIRECTORY("-o");
		final String value;

		private CommandLineArgument(final String p_value) {
			value = p_value;
		}

		public String getValue() {
			return value;
		}

		public static CommandLineArgument getArgument(final String data) {
			for (CommandLineArgument arg : values()) {
				if (arg.value.equalsIgnoreCase(data))
					return arg;
			}

			return null;
		}
	}

	Pojo process() throws Exception;

	IRequest setArgumentMap(final Map<CommandLineArgument, List<String>> p_argumentMap) throws Exception;
}
