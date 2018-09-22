package com.pojogen.application.request;

import java.util.List;
import java.util.Map;

import com.pojogen.application.cli.PojoGenCLI;
import com.pojogen.application.pojo.component.Pojo;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.StaticValues;

public final class HelpRequest implements IRequest {

	@Override
	public Pojo process() throws Exception {

		System.out.println(StaticValues.DEFAULT_HELP_MESSAGE + "\n\n" + DataTypeEnum.getOptions());

		return null;
	}

	@Override
	public IRequest setArgumentMap(final Map<CommandLineArgument, List<String>> p_argumentMap) throws Exception {
		return this;
	}

	public static void throwRequest() {
		PojoGenCLI.main(new String[] { CommandLineArgument.HELP.getValue() });

		System.exit(0);
	}
}
