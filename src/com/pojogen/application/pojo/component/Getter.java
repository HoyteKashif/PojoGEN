package com.pojogen.application.pojo.component;

import java.util.Objects;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.StaticValues;

public class Getter implements PojoMethod {
	private final DataTypeEnum m_eReturnType;
	private final String strDeclaration;
	private final String strBody;

	private Getter(final DataTypeEnum p_eReturnType, final String p_strDeclaration, final String p_strBody) {
		this.m_eReturnType = p_eReturnType;
		this.strDeclaration = p_strDeclaration;
		this.strBody = p_strBody;
	}

	@Override
	public DataTypeEnum getDataType() {
		return this.m_eReturnType;
	}

	@Override
	public String toString() {
		final StringBuilder method = new StringBuilder();
		method.append(strDeclaration + "{\n");
		method.append(strBody + "\n");
		method.append(StaticValues.TAB + "}");
		return method.toString();
	}

	public static class Builder {
		public static Getter getMethod(final String p_strFieldName, final DataTypeEnum p_eReturnType) {
			Objects.requireNonNull(p_strFieldName);
			Objects.requireNonNull(p_eReturnType);

			return new Getter(p_eReturnType, declaration(p_strFieldName, p_eReturnType),
					body(p_strFieldName, p_eReturnType));
		}

		private static String declaration(final String p_strFieldName, final DataTypeEnum p_eReturnType) {
			final String returnType = p_eReturnType.getClazz();
			final String methodName = String.format("get%s", p_strFieldName);

			return String.format(StaticValues.TAB + "public %1$s %2$s()", returnType, methodName);
		}

		private static String body(final String p_strFieldName, final DataTypeEnum p_eReturnType) {
			final String suffix = p_eReturnType.getSuffix();
			final String memberName = String.format("m_%s%s", suffix, p_strFieldName);

			return String.format(StaticValues.TAB2X + "return %s;", memberName);
		}
	}
}
