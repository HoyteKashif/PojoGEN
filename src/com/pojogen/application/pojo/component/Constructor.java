package com.pojogen.application.pojo.component;

import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.PojoStaticValues;

public class Constructor implements PojoMethod {
	private String strClazzName;
	private Constructor(final String p_strClazzName){	
		this.strClazzName = p_strClazzName;
	}
	
	@Override
	public String toString(){
		return String.format(PojoStaticValues.TAB + "public %s(){\n" + PojoStaticValues.TAB + "}", strClazzName);
	}
	
	public static class ConsructorMethodBuilder{
		public static Constructor getMethod(final String p_strClazzName){
			return new Constructor(p_strClazzName);
		}
	}

	@Override
	public DataTypeEnum getDataType() {
		return null;
	}
}
