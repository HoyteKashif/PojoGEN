package com.pojogen.application.pojo.component;

import java.util.Objects;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.PojoStaticValues;

public class PojoMember {
	private final DataTypeEnum m_eDataType;
	private final String strRepresentation;

	public PojoMember(final DataTypeEnum p_eDataType, final String p_strRepresentation) {
		this.m_eDataType = p_eDataType;
		this.strRepresentation = p_strRepresentation;
	}

	public DataTypeEnum getDataType() {
		return this.m_eDataType;
	}

	@Override
	public String toString() {
		return strRepresentation;
	}

	public static final class PojoMemberBuilder {
		private static final String SPACE = " ";
		private static final String PRIVATE = "private";

		public static PojoMember getMember(final String p_strMemberName, final DataTypeEnum p_eDataType) {
			Objects.nonNull(p_strMemberName);
			Objects.nonNull(p_eDataType);

			final String strDeclaration = createDeclaration(p_strMemberName, p_eDataType);
			return new PojoMember(p_eDataType, strDeclaration);
		}

		private static String createDeclaration(final String p_strMemberName, final DataTypeEnum p_eDataType) {
			final String suffix = p_eDataType.getSuffix();
			final String memberName = String.format("m_%s%s", suffix, p_strMemberName);

			return PojoStaticValues.TAB + PRIVATE + SPACE + p_eDataType.getClazz() + SPACE + memberName + ";";
		}
	}
}