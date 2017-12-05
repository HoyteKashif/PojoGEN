package com.easytech.pojo.component.method;

import java.util.Objects;

import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

public class Setter implements PojoMethod {
	private final DataTypeEnum m_eDataType;
	private final String m_strDeclaration;
	private final String m_strBody;
	private Setter(final DataTypeEnum p_eDataType, final String p_strDeclaration, final String p_strBody){
		this.m_eDataType = p_eDataType;
		this.m_strDeclaration = p_strDeclaration;
		this.m_strBody = p_strBody;
	}
	
	@Override
	public DataTypeEnum getDataType(){
		return this.m_eDataType;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(m_strDeclaration + "{\n");
		sb.append(m_strBody + "\n");
		sb.append(PojoStaticValues.TAB + "}");
		return sb.toString();  
	}
	
	public static class SetMethodBuilder{
		public static Setter getMethod(final String p_strFieldName, final DataTypeEnum p_eDataType){
			Objects.requireNonNull(p_strFieldName);
			Objects.requireNonNull(p_eDataType);
			return new Setter( p_eDataType, declaration(p_strFieldName, p_eDataType), body(p_strFieldName, p_eDataType));
		}
		
		private static String declaration(final String p_strFieldName, final DataTypeEnum p_eDataType){
			final String dataType = p_eDataType.getClazz();
			final String suffix = p_eDataType.getSuffix();
			final String methodName = String.format("set%s", p_strFieldName);
			final String paramName = String.format("p_%s%s", suffix, p_strFieldName);
			
			return String.format(PojoStaticValues.TAB + "public void %2$s(final %1$s %3$s)", dataType, methodName, paramName);
		}
		
		private static String body(final String p_strFieldName, final DataTypeEnum p_eDataType){
			final String suffix = p_eDataType.getSuffix();
			final String memberName = String.format("m_%s%s", suffix, p_strFieldName);
			final String paramName = String.format("p_%s%s", suffix, p_strFieldName);
			
			return String.format(PojoStaticValues.TAB2X + "this.%s = %s;" , memberName, paramName);
		}
	
	}
}
