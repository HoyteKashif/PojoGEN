package com.pojogen.application.pojo.factory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.pojogen.application.pojo.component.Constructor;
import com.pojogen.application.pojo.component.Getter.Builder;
import com.pojogen.application.pojo.component.PojoMethod;
import com.pojogen.application.pojo.component.PojoMethod.MethodType;
import com.pojogen.application.pojo.component.Setter;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

/**
 * 
 * @author Kashif Hoyte
 *
 */
public final class PojoMethodFactory {
	public static PojoMethod getMethod(final MethodType methodType, final String name, final DataTypeEnum dataType) {
		Objects.requireNonNull(methodType);

		switch (methodType) {
		case CONSTRUCTOR:
			return new Constructor(name);
		case GETTER:
			return Builder.getMethod(name, dataType);
		case SETTER:
			return new Setter(name, dataType);
		default:
			throw new IllegalArgumentException();
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
		for (final Entry<String, DataTypeEnum> entry : nameToTypeMap.entrySet()) {
			methods.add(getMethod(MethodType.GETTER, entry.getKey(), entry.getValue()));
			methods.add(getMethod(MethodType.SETTER, entry.getKey(), entry.getValue()));
		}
		return methods;
	}

	private PojoMethodFactory() {
	}
}
