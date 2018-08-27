package com.pojogen.application.pojo.component;

import static java.util.Objects.requireNonNull;

import static javax.lang.model.element.Modifier.PRIVATE;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.PojoStaticValues;

public class PojoMember {
	private static final String SPACE = " ";
	private final DataTypeEnum dataType;
	private final String name;

	public PojoMember(final String p_name, final DataTypeEnum p_dataType) {
		this.name = requireNonNull(p_name);
		this.dataType = requireNonNull(p_dataType);
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}

	@Override
	public String toString() {
		final String suffix = dataType.getSuffix();
		final String memberName = String.format("m_%s%s", suffix, name);

		return PojoStaticValues.TAB + PRIVATE + SPACE + dataType.getClazz() + SPACE + memberName + ";";
	}
}