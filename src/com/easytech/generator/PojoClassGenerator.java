package com.easytech.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.easytech.generator.PojoMethodGenerator.PojoMethod;
import com.easytech.staticvalues.PojoStaticValues;
import com.easytech.staticvalues.PojoStaticValues.DataTypeEnum;

public class PojoClassGenerator implements PojoStaticValues{
	private PojoClassGenerator(){}
	
	public static Pojo buildPojo(final String p_strClassName, final Set<String> p_argumentSet) throws Exception{
//		System.out.println("Mapping member name to dataType...");
		final Map<String,DataTypeEnum> argToTypeMap = createMemberToTypeMap(p_argumentSet);
		
		final Pojo pojoClazz = new Pojo();
//		System.out.println("Setting the class Declaration....");
		pojoClazz.setDeclaration(p_strClassName);
//		System.out.println("Setting Argument Data Map...");
		pojoClazz.setArgDataMap(argToTypeMap);
		pojoClazz.setConstructor(buildConstructor(p_strClassName));
		pojoClazz.setMemberList(buildMembers(argToTypeMap));
		pojoClazz.setMethodList(buildMethods(argToTypeMap));
		pojoClazz.buildRepresentation();
		return pojoClazz;
	}
	
	public static PojoMethod buildConstructor(final String p_clazzName){
		return PojoMethodGenerator.getInstance(PojoMethodTypeEnum.CONSTRUCTOR, p_clazzName, null);
	}
	
	public static List<Member> buildMembers(final Map<String,DataTypeEnum> p_nameToTypeMap){
		final List<Member> memberList = new ArrayList<>();
		for (String memberName : p_nameToTypeMap.keySet()){
			memberList.add(PojoMemberGenerator.getInstance(memberName, p_nameToTypeMap.get(memberName)));
		}
		return memberList;
	}
	
	/**
	 * Used to loop through all of the class members and create getter/setter methods based off of their name and dataType.
	 * @param nameToTypeMap member name to dataType map
	 * @return {@code List<PojoMethod>} List of Methods
	 */
	public static List<PojoMethod> buildMethods(final Map<String, DataTypeEnum> nameToTypeMap){
		List<PojoMethod> methodList = new LinkedList<>();
		for (String memberName : nameToTypeMap.keySet()){
			DataTypeEnum eType = nameToTypeMap.get(memberName);
			methodList.add(PojoMethodGenerator.getInstance(PojoMethodTypeEnum.GET, memberName, eType));
			methodList.add(PojoMethodGenerator.getInstance(PojoMethodTypeEnum.SET, memberName, eType));
		}
		return methodList;
	}
	
	/**
	 * @param p_ArgumentList
	 * @return Map<String, DataTypeEnum> map member name to DataTypeEnum ex. get_foo_string map<getFoo,String>
	 */
	public static Map<String,DataTypeEnum> createMemberToTypeMap(final Set<String> p_argumentSet) throws Exception{
		final Map<String,DataTypeEnum> argToTypeMap = new HashMap<>();
		for (String arg : p_argumentSet){
			DataTypeEnum eType = DataTypeEnum.getType(arg);
			
			if (DataTypeEnum.UNSPECIFIED.equals(eType)){
				throw new Exception("Illegal Member parameter\n\n"
						+ DEFAULT_ERROR_MESSAGE + "\n"
						+ "Use the following DataType extensions:\n"
						+ DataTypeEnum.getOptions());
			}
			
			// remove the type info 
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
			
			// FIXME: doing the storage of the member names this way does not allow for the same name with different data type
			// do i want to allow same name with different data type
			argToTypeMap.put(nameBuilder.toString(), eType);
		}
		return argToTypeMap;
	}
}

class Pojo{
	private static String NEWLINE = "\n";
	private static String NEWLINE2x = "\n\n";
	private Map<String,DataTypeEnum> m_nameToTypeMap = new HashMap<>();
	private String m_strRepresentation;
	private String m_strDeclaration;
	private List<PojoMethod> m_MethodList;
	private List<Member> m_MemberList;
	private PojoMethod m_strConstructor;
	
	protected Pojo(){}
	
	public PojoMethod getConstructor(){
		return this.m_strConstructor;
	}
	
	public void setConstructor(final PojoMethod p_strConstructor){
		this.m_strConstructor = p_strConstructor;
	}
	
	public List<Member> getMemberList(){
		return this.m_MemberList;
	}
	
	public void setMemberList(final List<Member> p_MemberList){
		this.m_MemberList = p_MemberList;
	}
			
	public List<PojoMethod> getMethodList(){
		return this.m_MethodList;
	}
	
	public void setMethodList(final List<PojoMethod> p_MethodList){
		this.m_MethodList = p_MethodList;
	}
	
	public Map<String,DataTypeEnum> getArgDataMap(){
		return this.m_nameToTypeMap;
	}
	
	public void setArgDataMap(final Map<String, DataTypeEnum> map){
		this.m_nameToTypeMap = map;
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
		
		Iterator<Member> itrMember = this.getMemberList().iterator();
		while (itrMember.hasNext()){
			Member member = itrMember.next();
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
}