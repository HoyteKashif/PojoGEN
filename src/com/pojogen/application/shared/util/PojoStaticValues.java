package com.pojogen.application.shared.util;

import java.util.Arrays;
import java.util.List;

import com.pojogen.application.request.IRequest;
import com.pojogen.application.request.IRequest.ArgumentPartEnum;
import com.pojogen.application.request.PojoGenRequest;

public final class PojoStaticValues {

	public final static String TAB = "    ";
	public final static String TAB2X = TAB + TAB;

	public final static String DEFAULT_HELP_MESSAGE = "Usage:" + "\n" + TAB
			+ String.format(
					"PojoGen %s <classname> %s <membername_datatype> ... <membername_datatype> \n%sPojoGen %s \n%sPojoGen %s",
					ArgumentPartEnum.CLASS_PART.getKey(), ArgumentPartEnum.MEMBER_PART.getKey(), TAB,
					ArgumentPartEnum.HELP_PART.getKey(), TAB, ArgumentPartEnum.SAMPLE_PART.getKey());

	public final static String DEFAULT_ERROR_MESSAGE = "ex:\n" + TAB
			+ String.format("PojoGen %s ClassName %s member_name_datatype", ArgumentPartEnum.CLASS_PART.getKey(),
					ArgumentPartEnum.MEMBER_PART.getKey());

	public enum PojoMethodTypeEnum {
		SETTER, GETTER, CONSTRUCTOR
	}

	public static List<IRequest> getRequestTypes() {
		return Arrays.asList(new PojoGenRequest());
	}

	private PojoStaticValues() {
	}
}
