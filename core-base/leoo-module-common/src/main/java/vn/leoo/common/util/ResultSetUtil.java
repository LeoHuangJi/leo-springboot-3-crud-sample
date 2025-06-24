package vn.leoo.common.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.util.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;

import oracle.sql.TIMESTAMP;

public class ResultSetUtil {

	protected static DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		ResultSetUtil.dataSource = dataSource;
	}

	public static <T> List<T> selectQuery(Class<T> type, String query)
			throws SQLException, IllegalArgumentException, ParseException {
		List<T> list = new ArrayList<T>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rst = stmt.executeQuery(query)) {

					ResultSetMetaData rsmd = rst.getMetaData();
					int columnCount = rsmd.getColumnCount();

					List<String> columnsName = new ArrayList<>(columnCount);
					for (int i = 1; i <= columnCount; i++) {
						String name = rsmd.getColumnName(i);
						columnsName.add(name.toLowerCase());
					}

					while (rst.next()) {
						@SuppressWarnings("deprecation")
						T t = type.newInstance();
						loadResultSetIntoObject(rst, t, columnsName);// Point 4
						list.add(t);
					}
				}
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
			}
		}
		return list;
	}

	

	public static <T> List<T> resultSetToList(ResultSet rst, Class<T> type)
			throws SQLException, IllegalArgumentException, ParseException {

		List<T> list = new ArrayList<T>();
		try {
			ResultSetMetaData rsmd = rst.getMetaData();
			int columnCount = rsmd.getColumnCount();

			List<String> columnsName = new ArrayList<>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				columnsName.add(name);
			}

			while (rst.next()) {

				// for (int i = 0; i < columnCount; i++) {
				// System.out.println(columnsName.get(i) + ":" + rst.getObject(i + 1) + "----");
				// }
				@SuppressWarnings("deprecation")
				T t = type.newInstance();

				loadResultSetIntoObject(rst, t, columnsName);// Point 4
				list.add(t);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
		}
		return list;
	}

	public static void loadResultSetIntoObject(ResultSet rst, Object object, List<String> availableColumns)
			throws IllegalArgumentException, IllegalAccessException, SQLException, ParseException {
		Class<?> zclass = object.getClass();

		for (Field field : zclass.getDeclaredFields()) {
			field.setAccessible(true);
			DBTable column = field.getAnnotation(DBTable.class);

			if (column == null || StringUtils.isEmpty(column.columnName()))
				continue;
			if (!availableColumns.contains(column.columnName().toUpperCase()))
				continue;

			Object value = rst.getObject(column.columnName());
			if (!ObjectUtils.isEmpty(value)) {

				Class<?> type = field.getType();
				// System.out.println("----:" + field.getType());
				if (isPrimitive(type)) {
					if (!(value instanceof BigInteger)) {

						Class<?> boxed = boxPrimitiveClass(type);
						if (type == Timestamp.class) {
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = df.parse(value.toString());
							Timestamp ts = new Timestamp(date.getTime());
							value = boxed.cast(ts);

						}
						if (type == Clob.class) {
							String dta=null;
							try {
								dta = StringUtils.clob2String((Clob)value);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							value = boxed.cast(dta);

						}
						if (type == Long.class || type == long.class) {
						    if (value instanceof Number number) {
						        value = number.longValue();
						    } else if (value instanceof String str) {
						        value = Long.parseLong(str);
						    } else {
						        throw new IllegalArgumentException("Cannot convert type " + value.getClass() + " to Long");
						    }
						}
						if (type == Integer.class || type == int.class) {
						    if (value instanceof Number number) {
						        value = number.intValue();
						    } else if (value instanceof String str) {
						        value = Integer.parseInt(str);
						    } else {
						        throw new IllegalArgumentException("Cannot convert type " + value.getClass() + " to Integer");
						    }
						}
						if (type == BigDecimal.class) {
						    if (value instanceof BigDecimal) {
						        // OK
						    } else if (value instanceof Number number) {
						        value = BigDecimal.valueOf(number.doubleValue());
						    } else if (value instanceof String str) {
						        value = new BigDecimal(str);
						    } else {
						        throw new IllegalArgumentException("Cannot convert type " + value.getClass() + " to BigDecimal");
						    }
						}
						else {
							value = boxed.cast(value);
						}

					} else {
						value = ((BigInteger) value).longValue();
					}

				}
				field.set(object, value);
			}
		}
	}

	public static boolean isPrimitive(Class<?> type) {
		return (type == int.class || type == long.class || type == double.class || type == float.class
				|| type == boolean.class || type == byte.class || type == char.class || type == short.class
				|| type == Timestamp.class || type == TIMESTAMP.class || type == BigDecimal.class|| type == Clob.class|| type == Blob.class
				|| type == Long.class|| type == Integer.class);
	}

	public static Class<?> boxPrimitiveClass(Class<?> type) {
		if (type == int.class) {
			return Integer.class;
		}
		if (type == Integer.class) {
			return Integer.class;
		}
		else if (type == long.class) {
			return Long.class;
		} else if (type == Long.class) {
			return Long.class;
		} else if (type == BigDecimal.class) {
			return BigDecimal.class;
		} else if (type == double.class) {
			return Double.class;
		} else if (type == float.class) {
			return Float.class;
		} else if (type == boolean.class) {
			return Boolean.class;
		} else if (type == byte.class) {
			return Byte.class;
		} else if (type == char.class) {
			return Character.class;
		} else if (type == short.class) {
			return Short.class;
		} else if (type == TIMESTAMP.class) {
			return Timestamp.class;
		} else if (type == Timestamp.class) {
			return Timestamp.class;
		} else if (type == Clob.class) {
			return Clob.class;
		} else if (type == Blob.class) {
			return Blob.class;
		} else {
			String string = "class '" + type.getName() + "' is not a primitive";
			throw new IllegalArgumentException(string);
		}
	}
}
