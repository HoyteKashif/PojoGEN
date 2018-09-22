package com.pojogen.application.pojo.component;

import static com.pojogen.application.shared.util.StaticValues.TAB;
import static com.pojogen.application.shared.util.StaticValues.TAB2X;
import static java.util.Objects.requireNonNull;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

public final class Setter implements PojoMethod {
	private final String name;
	private final DataTypeEnum dataType;

	public Setter(final String p_name, final DataTypeEnum p_dataType) {
		this.name = requireNonNull(p_name);
		this.dataType = requireNonNull(p_dataType);
	}

	@Override
	public DataTypeEnum getDataType() {
		return dataType;
	}

	@Override
	public String toString() {
		final StringBuilder method = new StringBuilder();
		method.append(declaration(name, dataType) + "{\n");
		method.append(body(name, dataType) + "\n");
		method.append(TAB + "}");
		return method.toString();
	}

	private static String declaration(final String p_strFieldName, final DataTypeEnum p_eDataType) {
		final String dataType = p_eDataType.getClazz();
		final String suffix = p_eDataType.getSuffix();
		final String methodName = String.format("set%s", p_strFieldName);
		final String paramName = parameter(suffix, p_strFieldName);

		return String.format(TAB + "public void %2$s(final %1$s %3$s)", dataType, methodName, paramName);
	}

	private static String body(final String p_strFieldName, final DataTypeEnum p_eDataType) {
		final String suffix = p_eDataType.getSuffix();
		final String memberName = String.format("m_%s%s", suffix, p_strFieldName);
		final String paramName = parameter(suffix, p_strFieldName);

		return String.format(TAB2X + "this.%s = %s;", memberName, paramName);
	}

	private static String parameter(final String suffix, final String fieldName) {
		return String.format("p_%s%s", suffix, fieldName);
	}
}
