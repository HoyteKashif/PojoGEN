package com.easytech.staticvalues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface  PojoStaticValues {

	public final static String TAB = "    ";
	public final static String TAB2X = TAB + TAB;

	public final static String DEFAULT_HELP_MESSAGE = "use PojoGen -help -sample to see an example of a POJO.";
	public final static String DEFAULT_ERROR_MESSAGE = "ex:PojoGen -c ClassName -p member_name_datatype";

	public enum PojoMethodTypeEnum{
		SET,GET,CONSTRUCTOR
	}
	
	public static enum DataTypeEnum{
		_BIGDECIMAL("java.math.BigDecimal", "l"),
		_TIMESTAMP("java.sql.Timestamp", "ts"),
		_DATE("java.sql.Date", "dt"),
		_INTEGER("Integer", "i"),
		_STRING("String", "str"),
		_FLOAT("Float", "fl"),
		_DOUBLE("Double", "dl"),
		_LONG("Long", "lg"),
		_BIGDECIMAL_COLLECTION("java.util.Collection<java.math.BigDecimal>", "colbd"),
		_STRING_COLLECTION("java.util.Collection<String>", "colstr"),
		_TIMESTAMP_COLLECTION("java.util.Collection<java.sql.Timestamp>", "colts"),
		_DATE_COLLECTION("java.util.Collection<java.sql.Date>", "coldt"),
		_FLOAT_COLLECTION("java.util.Collection<Float>", "colfl"),
		_LONG_COLLECTION("java.util.Collection<Long>", "collg"),
		_INTEGER_COLLECTION("java.util.Collection<Integer>", "coli"),
		_DOUBLE_COLLECTION("java.util.Collection<Double>", "coldl"),
		_COLLECTION("java.util.Collection", "col"),
		UNSPECIFIED("Object", "o");
		
		private final String clazz;
		private final String namingSuffix;
		DataTypeEnum(final String p_clazz, final String p_namingSuffix){
			this.clazz = p_clazz;
			this.namingSuffix = p_namingSuffix;
		}
		
		public String getSuffix(){
			return namingSuffix;
		}
		
		public String getClazz(){
			return clazz;
		}
		
		public static DataTypeEnum getType(final String p_strData){
			for (DataTypeEnum dataType : values()){
				if(p_strData.toUpperCase().endsWith(dataType.name()))
					return dataType;
			}
			return UNSPECIFIED;
		}
		
		private static int maxClazzLength(){
			int max = -1;
			for (DataTypeEnum dataType : values()){
				int length = dataType.getClazz().length();
				if (length > max){
					max = length;
				}
			}
			return max;
		}
		
		public static String getOptions(){
			List<String> lstDataTypes = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			for (DataTypeEnum dataType : values()){
				if (!DataTypeEnum.UNSPECIFIED.equals(dataType)){
					sb.append(dataType.getClazz());
					
					int i = dataType.getClazz().length();
					while (i++ < maxClazzLength() + 1){
						sb.append(" ");
					}
					sb.append("|" + TAB2X);
					sb.append(dataType.name().toLowerCase());
					sb.append("\n");
					lstDataTypes.add(sb.toString());
					
					sb = new StringBuilder();
				}
			}
			
			int maxLineLength = -1;
			for (String strType : lstDataTypes){
				int length = strType.length();
				if (length > maxLineLength){
					maxLineLength = length;
				}
			}
			
			StringBuilder strBuilder = new StringBuilder();
			for (Iterator<String> iterator = lstDataTypes.iterator(); iterator.hasNext();){
				String strType = iterator.next();
				int i = 0;
				while (i++ < maxLineLength + 1){
					strBuilder.append("-");
				}
				
				strBuilder.append("\n");
				strBuilder.append(strType);
				
				if (!iterator.hasNext()){
					int c = 0;
					while (c++ < maxLineLength + 1){
						strBuilder.append("-");
					}
				}
			}
			
			return strBuilder.toString();
		}
	}
}
