package com.easytech.pojo.component.factory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.easytech.pojo.component.method.Constructor.ConsructorMethodBuilder;
import com.easytech.pojo.component.method.Getter.GetMethodBuilder;
import com.easytech.pojo.component.method.PojoMethod;
import com.easytech.pojo.component.method.Setter.SetMethodBuilder;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;
import com.easytech.staticvalues.PojoStaticValues.PojoMethodTypeEnum;

public class PojoMethodFactory {
	public static PojoMethod getMethod(final PojoMethodTypeEnum p_eMethodType,final String p_methodName, final DataTypeEnum p_dataType){
		Objects.requireNonNull(p_eMethodType);
		
		if (PojoMethodTypeEnum.GET.equals(p_eMethodType)){
			return GetMethodBuilder.getMethod(p_methodName, p_dataType);
		}else if (PojoMethodTypeEnum.SET.equals(p_eMethodType)){
			return SetMethodBuilder.getMethod(p_methodName, p_dataType);
		}else {
			return ConsructorMethodBuilder.getMethod(p_methodName);
		}
	}
	
	/**
	 * Used to loop through all of the class members and create getter/setter methods based off of their name and dataType.
	 * @param nameToTypeMap member name to dataType map
	 * @return {@code List<PojoMethod>} List of Methods
	 */
	public static List<PojoMethod> getMethods(final Map<String, DataTypeEnum> nameToTypeMap){
		List<PojoMethod> methodList = new LinkedList<>();
		for (String memberName : nameToTypeMap.keySet()){
			DataTypeEnum eType = nameToTypeMap.get(memberName);
			methodList.add(getMethod(PojoMethodTypeEnum.GET, memberName, eType));
			methodList.add(getMethod(PojoMethodTypeEnum.SET, memberName, eType));
		}
		return methodList;
	}
}
