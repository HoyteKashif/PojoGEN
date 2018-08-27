package com.pojogen.application.pojo.component;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pojogen.application.pojo.factory.PojoMethodFactory;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

import static com.pojogen.application.shared.util.PojoStaticValues.PojoMethodTypeEnum.CONSTRUCTOR;

/**
 * Class used to represent a Plain old JAVA Object (POJO). POJO is made of
 * members/properties and their getter or setter methods.
 * 
 * @author Kashif Hoyte
 *
 */
public class Pojo {
	private static final String NEWLINE = "\n";
	private static final String NEWLINE2x = "\n\n";
	private String m_strPojoClassName;
	private String m_strDeclaration;
	private List<PojoMethod> m_MethodList;
	private List<PojoMember> m_MemberList;
	private PojoMethod m_Constructor;

	private Pojo(final String p_strPojoClassName) {
		this.m_strPojoClassName = p_strPojoClassName;
	}

	public String getPojoClassName() {
		return m_strPojoClassName;
	}

	public PojoMethod getConstructor() {
		return this.m_Constructor;
	}

	public void setConstructor(final PojoMethod p_strConstructor) {
		this.m_Constructor = p_strConstructor;
	}

	public List<PojoMember> getMemberList() {
		return this.m_MemberList;
	}

	public void setMemberList(final List<PojoMember> p_MemberList) {
		this.m_MemberList = p_MemberList;
	}

	public List<PojoMethod> getMethodList() {
		return this.m_MethodList;
	}

	public void setMethodList(final List<PojoMethod> p_MethodList) {
		this.m_MethodList = p_MethodList;
	}

	public String getDeclaration() {
		return m_strDeclaration;
	}

	public void setDeclaration(final String p_strClassName) {
		this.m_strDeclaration = String.format("public class %s", p_strClassName);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.m_strDeclaration + "{" + NEWLINE);

		Iterator<PojoMember> itrMember = this.getMemberList().iterator();
		while (itrMember.hasNext()) {
			PojoMember member = itrMember.next();
			if (itrMember.hasNext()) {
				sb.append(member + NEWLINE);
			} else {
				sb.append(member + NEWLINE2x);
			}
		}

		// add the constructor at the beginning of the list
		this.getMethodList().add(0, this.getConstructor());

		Iterator<PojoMethod> itrMethod = this.getMethodList().iterator();
		while (itrMethod.hasNext()) {
			PojoMethod method = itrMethod.next();
			if (itrMethod.hasNext()) {
				sb.append(method + NEWLINE2x);
			} else {
				sb.append(method + NEWLINE);
			}
		}
		sb.append("}");

		return sb.toString();
	}

	public static final class Builder {
		private final String clzName;
		private final Map<String, DataTypeEnum> memberToTypeMap;

		public Builder(final String p_clzName, final List<String> p_argumentSet) throws Exception {
			this(p_clzName, memberToTypeMap(p_argumentSet));
		}

		public Builder(final String p_clzName, final Map<String, DataTypeEnum> p_memberToTypeMap) {
			this.clzName = requireNonNull(p_clzName);
			this.memberToTypeMap = requireNonNull(p_memberToTypeMap);
		}

		public String getClassName() {
			return clzName;
		}

		public Map<String, DataTypeEnum> getMemberTypeMap() {
			return memberToTypeMap;
		}

		public Pojo build() {
			final List<PojoMember> members = members(memberToTypeMap);
			final PojoMethod constructor = PojoMethodFactory.getMethod(CONSTRUCTOR, clzName, null);
			final List<PojoMethod> methods = PojoMethodFactory.getMethods(memberToTypeMap);

			// TODO: add a list of imports instead of the long names for each
			// class

			final Pojo pojoClazz = new Pojo(clzName);
			pojoClazz.setDeclaration(clzName);
			pojoClazz.setConstructor(constructor);
			pojoClazz.setMemberList(members);
			pojoClazz.setMethodList(methods);
			return pojoClazz;
		}
	}

	private static List<PojoMember> members(final Map<String, DataTypeEnum> p_nameToTypeMap) {
		final List<PojoMember> members = new ArrayList<>();
		for (String name : p_nameToTypeMap.keySet()) {
			members.add(new PojoMember(name, p_nameToTypeMap.get(name)));
		}
		return members;
	}

	public static Map<String, DataTypeEnum> memberToTypeMap(final List<String> p_argumentSet) throws Exception {
		final Map<String, DataTypeEnum> argToTypeMap = new HashMap<>();
		for (String arg : p_argumentSet) {
			DataTypeEnum eType = DataTypeEnum.getType(arg);

			// remove Java DataType extension
			arg = arg.substring(0, arg.toUpperCase().lastIndexOf(eType.name()));

			// CamelCase member name
			String[] arrName = arg.split("_");
			final StringBuilder nameBuilder = new StringBuilder();
			for (String namePart : arrName) {
				if (!namePart.isEmpty() && Character.isAlphabetic(namePart.charAt(0))) {
					// capitalize the first character
					nameBuilder.append(String.valueOf(namePart.charAt(0)).toUpperCase());
					// add the rest
					nameBuilder.append(namePart.substring(1, namePart.length()).toLowerCase());
				}
			}

			String memberName = nameBuilder.toString();
			if (argToTypeMap.containsKey(memberName)) {
				throw new IllegalArgumentException("Attempt to use non unique member/function name.");
			}

			argToTypeMap.put(memberName, eType);
		}
		return argToTypeMap;
	}
}