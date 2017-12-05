package com.easytech.pojogen.model;

import java.util.Map;

import com.easytech.request.implementation.IRequest;
import com.easytech.request.implementation.PojoGenRequest;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

public class PojoGenRequestModel {

	private final IRequest oRequest = new PojoGenRequest();
	private String strClazzName;
	private Map<String, DataTypeEnum> mapNameToDataType;
	
	public IRequest getRequest()
	{
		return oRequest;
	}
	
	public void setClazzname(final String p_strClazzName) {
		this.strClazzName = p_strClazzName;
	}

	public String getClazzName() {
		return strClazzName;
	}

	public void setMemberMap(final Map<String, DataTypeEnum> p_mapNameToDataType) {
		this.mapNameToDataType = p_mapNameToDataType;
	}

	public Map<String, DataTypeEnum> getMemberMap() {
		return mapNameToDataType;
	}
}
