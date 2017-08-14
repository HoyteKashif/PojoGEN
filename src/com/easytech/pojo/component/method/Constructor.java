package com.easytech.pojo.component.method;

import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

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
		// TODO Auto-generated method stub
		return null;
	}
}
