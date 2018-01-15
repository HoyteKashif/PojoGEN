package com.pojogen.application.shared.util;

import java.util.Arrays;
import java.util.List;

import com.pojogen.application.request.IRequest;
import com.pojogen.application.request.PojoGenRequest;

public final class PojoStaticValues {

	public final static String TAB = "    ";
	public final static String TAB2X = TAB + TAB;

	public final static String DEFAULT_HELP_MESSAGE = "Usage:" + "\n" + TAB
			+ "PojoGen -c <classname> -p <membername_datatype> ... <membername_datatype>" + "\n" + TAB + "PojoGen -help"
			+ "\n" + TAB + "PojoGen -sample";
	public final static String DEFAULT_ERROR_MESSAGE = "ex:\n" + TAB + "PojoGen -c ClassName -p member_name_datatype";

	public enum PojoMethodTypeEnum {
		SET, GET, CONSTRUCTOR
	}

	public static List<IRequest> getRequestTypes() {
		return Arrays.asList(new PojoGenRequest());
	}

	private PojoStaticValues() {
	};
}
