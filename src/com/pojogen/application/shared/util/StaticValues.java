package com.pojogen.application.shared.util;

import java.util.Arrays;
import java.util.List;

import com.pojogen.application.request.IRequest;
import com.pojogen.application.request.IRequest.CommandLineArgument;
import com.pojogen.application.request.PojoGenRequest;

public final class StaticValues {

	public final static String TAB = "    ";
	public final static String TAB2X = TAB + TAB;
	public static final String SPACE = " ";
	public final static String DEFAULT_HELP_MESSAGE = "Usage:" + "\n" + TAB
			+ String.format(
					"PojoGen %s <classname> %s <membername_datatype> ... <membername_datatype> \n%sPojoGen %s \n%sPojoGen %s",
					CommandLineArgument.CLASS.getValue(), CommandLineArgument.MEMBER.getValue(), TAB,
					CommandLineArgument.HELP.getValue(), TAB, CommandLineArgument.SAMPLE.getValue());

	public final static String DEFAULT_ERROR_MESSAGE = "ex:\n" + TAB
			+ String.format("PojoGen %s ClassName %s member_name_datatype", CommandLineArgument.CLASS.getValue(),
					CommandLineArgument.MEMBER.getValue());

	

	public static List<IRequest> getRequestTypes() {
		return Arrays.asList(new PojoGenRequest());
	}

	private StaticValues() {
	}
}
