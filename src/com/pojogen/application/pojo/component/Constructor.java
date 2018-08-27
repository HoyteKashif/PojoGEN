package com.pojogen.application.pojo.component;

import static com.pojogen.application.shared.util.PojoStaticValues.TAB;

public class Constructor{
	private String clzName;

	private Constructor(final String p_clzName) {
		this.clzName = p_clzName;
	}

	@Override
	public String toString() {
		return TAB + "public " + clzName + "(){\n" + TAB + "}";
	}

	public static class ConsructorMethodBuilder {
		public static Constructor getMethod(final String p_strClazzName) {
			return new Constructor(p_strClazzName);
		}
	}
}