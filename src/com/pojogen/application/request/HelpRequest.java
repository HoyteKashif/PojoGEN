package com.pojogen.application.request;

import java.io.PrintStream;
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

	public static void main(String[] args) {

//		System.out.println("{" + String.format("%-10s", "kashif") + "}");
//		System.out.println("{" + String.format("%-10s", "damali") + "}");
//		System.out.println(Locale.getDefault());
		
		PrintStream stream = System.out.format("{%-10s}", "damali");
		stream.println();
		PrintStream str = System.out.format("{%-10s}", "kashif");
		str.println();
		
//		final int iMaxLength = DataTypeEnum.maxClazzLength() + 4;
//		for (DataTypeEnum eType : DataTypeEnum.values()) {
//			final String output = String.format("%-" + iMaxLength + "s", eType.getClazz());
//			System.out.println(output + "|");
//			
//		}
	}
}
