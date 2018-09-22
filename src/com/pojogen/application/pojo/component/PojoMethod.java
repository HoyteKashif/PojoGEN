package com.pojogen.application.pojo.component;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

public interface PojoMethod {
	public static enum MethodType {
		SETTER, GETTER, CONSTRUCTOR
	}

	DataTypeEnum getDataType();
}
