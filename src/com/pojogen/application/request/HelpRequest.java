package com.pojogen.application.request;

import java.util.List;
import java.util.Map;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.PojoStaticValues;

public class HelpRequest implements IRequest {

	@Override
	public void process() throws Exception {
		System.out.println(PojoStaticValues.DEFAULT_HELP_MESSAGE + "\n\n"
				+ DataTypeEnum.getOptions());
	}

	@Override
	public IRequest setArgumentMap(Map<ArgumentPartEnum, List<String>> p_argumentMap) throws Exception {
		return this;
	}

}
