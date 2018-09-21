package com.pojogen.application.pojo.component;

import static com.pojogen.application.shared.util.PojoStaticValues.TAB;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

public final class Constructor implements PojoMethod {
	private String clzName;

	public Constructor(final String p_clzName) {
		this.clzName = p_clzName;
	}

	@Override
	public String toString() {
		return TAB + "public " + clzName + "(){\n" + TAB + "}";
	}

	@Override
	public DataTypeEnum getDataType() {
		// XXX this needs to change, maybe create a different interface to use
		// when the method being created is a constructor
		return null;
	}
}