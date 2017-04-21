package com.easytech.generator;

import com.easytech.staticvalues.PojoStaticValues;

public class PojoMethodGenerator implements PojoStaticValues{

	public static PojoMethod getInstance(final PojoMethodTypeEnum p_eMethodType, final String p_methodName, final DataTypeEnum p_dataType) {
		if (PojoMethodTypeEnum.GET.equals(p_eMethodType)){
			return Getter.getInstance(p_methodName, p_dataType);
		}else if (PojoMethodTypeEnum.SET.equals(p_eMethodType)){
			return Setter.getInstance(p_methodName, p_dataType);
		}else{
			return Constructor.getInstance(p_methodName);
		}
	}
	
	interface PojoMethod{
	}
	
	private static class Constructor implements PojoMethod{
		private String m_strRepresentation;
		private Constructor(final String p_strRepresentation){
			this.m_strRepresentation = p_strRepresentation;
		}
		
		public static Constructor getInstance(final String p_clazzName){
			return new Constructor(String.format(TAB + "public %s(){\n" + TAB + "}", p_clazzName));
		}
		
		@Override
		public String toString(){
			return this.m_strRepresentation;
		}
	}
	
	private static class Setter implements PojoMethod{
		private String strRepresentation;
		
		private Setter( final String p_strRepresentation){
			strRepresentation = p_strRepresentation;
		}
		
		@Override
		public String toString(){
			return strRepresentation;
		}
		
		
		public static Setter getInstance(final String p_strFieldName, final DataTypeEnum p_eDataType){
			StringBuilder sb = new StringBuilder();
			sb.append(getDeclaration(p_strFieldName, p_eDataType) + "{\n");
			sb.append(getStatement(p_strFieldName, p_eDataType) + "\n");
			sb.append(TAB + "}");
			return new Setter(sb.toString()); 
		}
		
		private static String getDeclaration(final String p_strFieldName, final DataTypeEnum p_eDataType){
			final String dataType = p_eDataType.getClazz();
			final String suffix = p_eDataType.getSuffix();
			final String methodName = String.format("set%s", p_strFieldName);
			final String paramName = String.format("p_%s%s", suffix, p_strFieldName);
			
			return String.format(TAB + "public %1$s %2$s(final %1$s %3$s)", dataType, methodName, paramName);
		}
		
		private static String getStatement(final String p_strFieldName, final DataTypeEnum p_eDataType){
			final String suffix = p_eDataType.getSuffix();
			final String memberName = String.format("m_%s%s", suffix, p_strFieldName);
			final String paramName = String.format("p_%s%s", suffix, p_strFieldName);
			
			return String.format(TAB2X + "this.%s = %s;" , memberName, paramName);
		}
	}
	
	private static class Getter implements PojoMethod{
		private final String strRepresentation;
		
		private Getter(final String p_strRepresentation){
			this.strRepresentation = p_strRepresentation;
		}
		
		@Override
		public String toString(){
			return strRepresentation;
		}
		
		public static Getter getInstance(final String p_strFieldName, final DataTypeEnum p_eReturnType){
			String strDeclaration = createDeclaration(p_strFieldName, p_eReturnType);
			String strStatement = createStatementBlock(p_strFieldName, p_eReturnType);
			String strMethod = createMethod(strDeclaration, strStatement);
			return new Getter(strMethod);
		}
		
		private static String createMethod(final String p_strDeclaration, final String p_strStatement){
			final StringBuilder sb = new StringBuilder();
			sb.append(p_strDeclaration + "{\n");
			sb.append(p_strStatement + "\n");
			sb.append(TAB + "}");
			return sb.toString(); 
		}
		
		private static String createDeclaration(final String p_strFieldName, final DataTypeEnum p_eReturnType){
			final String returnType = p_eReturnType.getClazz();
			final String methodName = String.format("get%s", p_strFieldName);
			
			return String.format(TAB + "public %1$s %2$s()", returnType, methodName);
		}
		
		private static String createStatementBlock(final String p_strFieldName, final DataTypeEnum p_eReturnType){
			final String suffix = p_eReturnType.getSuffix();
			final String memberName = String.format("m_%s%s", suffix, p_strFieldName);
			
			return String.format(TAB2X + "return %s;" , memberName);
		}
	}
}
