package com.easytech.generator;

import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

public class PojoMemberGenerator {
	private static final String SPACE = " ";
	private static final String PRIVATE = "private";
	private PojoMemberGenerator(){}
	
	public static Member getInstance(final String p_strMemberName, final DataTypeEnum p_eDataType){
		final String strDeclaration = createDeclaration( p_strMemberName, p_eDataType);
		return new Member(strDeclaration);
	}
	
	private static String createDeclaration(final String p_strMemberName, final DataTypeEnum p_eDataType){
		final String suffix = p_eDataType.getSuffix();
		final String memberName = String.format("m_%s%s", suffix, p_strMemberName);
		
		return PojoStaticValues.TAB + PRIVATE + SPACE + p_eDataType.getClazz() + SPACE + memberName + ";";
	}
}

class Member{
	private String strRepresentation;
	Member(final String p_strRepresentation){
		this.strRepresentation = p_strRepresentation;
	}
	
	@Override
	public String toString(){
		return strRepresentation;
	}
}