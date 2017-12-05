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

		private static int maxClazzLength() {
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
			StringBuilder sb = new StringBuilder();

			// add the Java Data Type
			for (DataTypeEnum dataType : values()) {
				if (!DataTypeEnum.UNSPECIFIED.equals(dataType)) {
					sb.append(dataType.getClazz());

					int i = dataType.getClazz().length();
					while (i++ < maxClazzLength() + 1) {
						sb.append(" ");
					}
					sb.append("|" + PojoStaticValues.TAB2X);
					sb.append(dataType.name().toLowerCase());
					sb.append("\n");
					lstDataTypes.add(sb.toString());

					sb = new StringBuilder();
				}
			}

			// create space between the type extension and the Java Data Type
			int maxLineLength = -1;
			for (String strType : lstDataTypes) {
				final int length = strType.length();
				if (length > maxLineLength) {
					maxLineLength = length;
				}
			}

			// add the extension (name of ENUM)
			final StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("DataType Options:\n");
			for (Iterator<String> itrTypes = lstDataTypes.iterator(); itrTypes.hasNext();) {
				String strType = itrTypes.next();
				int i = 0;
				while (i++ < maxLineLength + 1) {
					strBuilder.append("-");
				}

				strBuilder.append("\n");
				strBuilder.append(strType);

				if (!itrTypes.hasNext()) {
					int c = 0;
					while (c++ < maxLineLength + 1) {
						strBuilder.append("-");
					}
				}
			}

			return strBuilder.toString();
		}
	}
}