package com.pojogen.application.shared.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Kashif Hoyte
 *
 */
public class PojoDataTypeHelper {
	public enum DataTypeEnum {
		_BIGDECIMAL("java.math.BigDecimal", "l"), 
		_TIMESTAMP("java.sql.Timestamp", "ts"), 
		_DATE("java.sql.Date","dt"), 
		_INTEGER("Integer", "i"), 
		_STRING("String", "str"), 
		_FLOAT("Float", "fl"), 
		_DOUBLE("Double","dl"), 
		_LONG("Long", "lg"), 
		_BIGDECIMAL_COLLECTION("java.util.Collection<java.math.BigDecimal>","colbd"), 
		_STRING_COLLECTION("java.util.Collection<String>","colstr"), 
		_TIMESTAMP_COLLECTION("java.util.Collection<java.sql.Timestamp>","colts"), 
		_DATE_COLLECTION("java.util.Collection<java.sql.Date>","coldt"), 
		_FLOAT_COLLECTION("java.util.Collection<Float>","colfl"), 
		_LONG_COLLECTION("java.util.Collection<Long>","collg"), 
		_INTEGER_COLLECTION("java.util.Collection<Integer>","coli"), 
		_DOUBLE_COLLECTION("java.util.Collection<Double>","coldl"), 
		_COLLECTION("java.util.Collection","col"), 
		UNSPECIFIED("Object", "o");

		private final String clazz;
		private final String namingSuffix;

		DataTypeEnum(final String p_clazz, final String p_namingSuffix) {
			this.clazz = p_clazz;
			this.namingSuffix = p_namingSuffix;
		}

		public String getSuffix() {
			return namingSuffix;
		}

		public String getClazz() {
			return clazz;
		}

		public static DataTypeEnum getTypeByClazz(final String p_strData) {
			if (Objects.isNull(p_strData) || p_strData.isEmpty()) {
				return null;
			}

			for (DataTypeEnum dataType : values()) {
				if (dataType.getClazz().equalsIgnoreCase(p_strData.trim())) {
					return dataType;
				}
			}
			return UNSPECIFIED;
		}

		public static List<String> getTypes() {
			final List<String> lstTypes = new ArrayList<>();

			for (DataTypeEnum eType : values()) {
				lstTypes.add(eType.getClazz());
			}

			return lstTypes;
		}

		public static DataTypeEnum getType(final String p_strData) {
			if (Objects.isNull(p_strData) || p_strData.isEmpty()){
				return null;
			}
			
			for (DataTypeEnum dataType : values()) {
				if (p_strData.trim().toUpperCase().endsWith(dataType.name()))
					return dataType;
			}
			return UNSPECIFIED;
		}

		public static boolean validTypes(final List<String> p_setTypes) {
			for (String arg : p_setTypes) {
				if (UNSPECIFIED.equals(getType(arg))) {
					return false;
				}
			}
			return true;
		}

		public static int maxClazzLength() {
			int max = -1;
			for (DataTypeEnum dataType : values()) {
				int length = dataType.getClazz().length();
				if (length > max) {
					max = length;
				}
			}
			return max;
		}

		public static String getOptions() {
			final List<String> lstDataTypes = new ArrayList<>();

			// get the length of the longest class with 2 tabs
			final int iMaxClazzWithTabLength = maxClazzLength() + PojoStaticValues.TAB.length();

			// add the Java Data Type
			for (DataTypeEnum eType : values()) {
				if (!DataTypeEnum.UNSPECIFIED.equals(eType)) {
					lstDataTypes.add(String.format("%-" + iMaxClazzWithTabLength + "s|%s", eType.getClazz(),
							PojoStaticValues.TAB + eType.name()));
				}
			}

			// add the extension (name of ENUM)
			final StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("DataType Options:\n");

			final int iLineLength = getMaxLength(lstDataTypes);
			final String lineSeparator = StringHelper.multiplyString("-", iLineLength);
			for (Iterator<String> itrTypes = lstDataTypes.iterator(); itrTypes.hasNext();) {
				final String line = itrTypes.next();
				if (itrTypes.hasNext()) {
					strBuilder.append(String.format("\n%s\n%s", line, StringHelper.replaceCharAt(lineSeparator, iMaxClazzWithTabLength, "+")));
				} else {
					strBuilder.append(String.format("\n%s\n%s", line, lineSeparator));
				}
			}

			return strBuilder.toString();
		}
	}

	public static int getMaxLength(final List<String> p_lstStrings) {
		int max = 0;
		for (String string : p_lstStrings) {
			if (max < string.length()) {
				max = string.length();
			}
		}
		return max;
	}
}
