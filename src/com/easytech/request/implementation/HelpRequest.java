package com.easytech.request.implementation;

import java.util.List;
import java.util.Map;

import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

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
