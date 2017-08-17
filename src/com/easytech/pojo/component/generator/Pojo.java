package com.easytech.pojo.component.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.easytech.pojo.component.factory.PojoMethodFactory;
import com.easytech.pojo.component.generator.PojoMember.PojoMemberBuilder;
import com.easytech.pojo.component.method.PojoMethod;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;
import com.easytech.staticvalues.PojoStaticValues.PojoMethodTypeEnum;

/**
 * Class used to represent a Plain old JAVA Object (POJO).
 * POJO is made of members/properties and their getter or setter methods.
 * 
 * @author WizardOfOz
 *
 */
public class Pojo{
	private static String NEWLINE = "\n";
	private static String NEWLINE2x = "\n\n";
	private String m_strRepresentation;
	private String m_strDeclaration;
	private List<PojoMethod> m_MethodList;
	private List<PojoMember> m_MemberList;
	private PojoMethod m_Constructor;
	
	private Pojo(){}
	
	public PojoMethod getConstructor(){
		return this.m_Constructor;
	}
	
	public void setConstructor(final PojoMethod p_strConstructor){
		this.m_Constructor = p_strConstructor;
	}
	
	public List<PojoMember> getMemberList(){
		return this.m_MemberList;
	}
	
	public void setMemberList(final List<PojoMember> p_MemberList){
		this.m_MemberList = p_MemberList;
	}
			
	public List<PojoMethod> getMethodList(){
		return this.m_MethodList;
	}
	
	public void setMethodList(final List<PojoMethod> p_MethodList){
		this.m_MethodList = p_MethodList;
	}
	
	public String getDeclaration(){
		return m_strDeclaration;
	}
	
	public void setDeclaration(final String p_strClassName){
		this.m_strDeclaration = String.format("public class %s", p_strClassName);
	}
	
	
	
	public void buildRepresentation(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.m_strDeclaration + "{" + NEWLINE);
		
		Iterator<PojoMember> itrMember = this.getMemberList().iterator();
		while (itrMember.hasNext()){
			PojoMember member = itrMember.next();
			if (itrMember.hasNext()){
				sb.append(member + NEWLINE);
			}else{
				sb.append(member + NEWLINE2x);
			}
		}
		
		// add the constructor at the beginning of the list
		this.getMethodList().add(0, this.getConstructor());
		
		Iterator<PojoMethod> itrMethod = this.getMethodList().iterator();
		while (itrMethod.hasNext()){
			PojoMethod method = itrMethod.next();
			if (itrMethod.hasNext()){
				sb.append(method + NEWLINE2x);
			}else{
				sb.append(method + NEWLINE);
			}
		}
		sb.append("}");
		
		this.m_strRepresentation = sb.toString();
	}
	
	@Override
	public String toString(){
		return m_strRepresentation;
	}
	
	/**
	 * Main Builder used to assemble a POJO object.
	 * @author WizardOfOz
	 *
	 */
	public static class PojoBuilder{
		
		private PojoBuilder(){}
		public static Pojo getPojo(final String p_strClazzName, final List<String> p_argumentSet) throws Exception{
			final Map<String, DataTypeEnum> memberNameToTypeMap = createMemberToTypeMap(p_argumentSet);
			final List<PojoMember> members =  buildMembers(memberNameToTypeMap); 
			final PojoMethod constructor = PojoMethodFactory.getMethod(PojoMethodTypeEnum.CONSTRUCTOR, p_strClazzName, null);
			final List<PojoMethod> methods =  PojoMethodFactory.getMethods(memberNameToTypeMap);
			
			//TODO: add a list of imports instead of the long names for each class
			
			final Pojo pojoClazz = new Pojo();
			pojoClazz.setDeclaration(p_strClazzName);
			pojoClazz.setConstructor(constructor);
			pojoClazz.setMemberList(members);
			pojoClazz.setMethodList(methods);
			pojoClazz.buildRepresentation();
			return pojoClazz;
		}
		
		public static PojoMethod buildConstructor(final String p_clazzName){
			return PojoMethodFactory.getMethod(PojoMethodTypeEnum.CONSTRUCTOR, p_clazzName, null);
		}
		
		public static List<PojoMember> buildMembers(final Map<String, DataTypeEnum> p_nameToTypeMap){
			final List<PojoMember> memberList = new ArrayList<>();
			for (String memberName : p_nameToTypeMap.keySet()){
				memberList.add(PojoMemberBuilder.getMember(memberName, p_nameToTypeMap.get(memberName)));
			}
			return memberList;
		}
		
		/**
		 * @param p_ArgumentList
		 * @return Map<String, DataTypeEnum> map member name to DataTypeEnum ex. get_foo_string map<getFoo,String>
		 */
		public static Map<String,DataTypeEnum> createMemberToTypeMap(final List<String> p_argumentSet) throws Exception{
			final Map<String,DataTypeEnum> argToTypeMap = new HashMap<>();
			for (String arg : p_argumentSet){
				DataTypeEnum eType = DataTypeEnum.getType(arg);
				
				// remove Java DataType extension 
				arg = arg.substring(0, arg.toUpperCase().lastIndexOf(eType.name()));
				
				// CamelCase member name
				String[] arrName = arg.split("_");
				StringBuilder nameBuilder = new StringBuilder();
				for (String namePart : arrName){
					if (!namePart.isEmpty() && Character.isAlphabetic(namePart.charAt(0))){
						// capitalize the first character
						nameBuilder.append(String.valueOf(namePart.charAt(0)).toUpperCase());
						// add the rest
						nameBuilder.append(namePart.substring(1, namePart.length()).toLowerCase());
					}
				}
				
				String memberName = nameBuilder.toString();
				if (argToTypeMap.containsKey(memberName)){
					throw new IllegalArgumentException("Attempt to use non unique member/function name.");
				} 
				
				argToTypeMap.put(memberName, eType);
			}
			return argToTypeMap;
		}
	}
}