package com.pojogen.application.pojo.factory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.pojogen.application.pojo.component.Constructor.ConsructorMethodBuilder;
import com.pojogen.application.pojo.component.Getter.GetMethodBuilder;
import com.pojogen.application.pojo.component.PojoMethod;
import com.pojogen.application.pojo.component.Setter.SetMethodBuilder;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.PojoStaticValues.PojoMethodTypeEnum;

import static com.pojogen.application.shared.util.PojoStaticValues.PojoMethodTypeEnum.GETTER;
import static com.pojogen.application.shared.util.PojoStaticValues.PojoMethodTypeEnum.SETTER;

/**
 * 
 * @author Kashif Hoyte
 *
 */
public class PojoMethodFactory {
	public static PojoMethod getMethod(final PojoMethodTypeEnum methodType, final String name,
			final DataTypeEnum dataType) {
		Objects.requireNonNull(methodType);

		if (GETTER == methodType) {
			return GetMethodBuilder.getMethod(name, dataType);
		} else if (SETTER == methodType) {
			return SetMethodBuilder.getMethod(name, dataType);
		} else {
			return ConsructorMethodBuilder.getMethod(name);
		}
	}

	/**
	 * Used to loop through all of the class members and create getter/setter
	 * methods based off of their name and dataType.
	 * 
	 * @param nameToTypeMap
	 *            member name to dataType map
	 * @return {@code List<PojoMethod>} List of Methods
	 */
	public static List<PojoMethod> getMethods(final Map<String, DataTypeEnum> nameToTypeMap) {
		final List<PojoMethod> methods = new LinkedList<>();
		for (final String name : nameToTypeMap.keySet()) {
			DataTypeEnum eType = nameToTypeMap.get(name);
			methods.add(getMethod(GETTER, name, eType));
			methods.add(getMethod(SETTER, name, eType));
		}
		return methods;
	}
}
