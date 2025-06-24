package vn.leoo.common.util.formatter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import vn.leoo.common.util.StringUtils;

public class NumberFormatter {
	private NumberFormat nf = null;

	private String thousand_sep = " ";
//	private String comma_sep = ","; // Ngan cach phan nguyen cua so VD: 123,456,789	
	private String dot_sep = "."; // Ngan cach phan thap phan cua so VD: 123,456,789.12
	private String precision = "0";

	static AtomicLong atomic = new AtomicLong();
	static Random rand = new Random();

	public NumberFormatter() throws Exception {
		try {
			Locale lo = Locale.ENGLISH;

			nf = NumberFormat.getNumberInstance(lo);
//			nf9 = NumberFormat.getNumberInstance(lo);
//			nf9.setMaximumFractionDigits(9);
//			nf.setMaximumFractionDigits(2);
//			nf.setMinimumFractionDigits(2);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	// Ham lam tron so : int precision do chinh xac
	public static double round(double val, int precision) {
		double powprecision = Math.pow(10, precision);
		return Math.floor((val * powprecision) + 0.5) / powprecision;
	}

	// Convert double to str
	public String num2str(double number) {
		String so = nf.format(number);
		so = so.replace(",", "");
		return so;
	}

	public String num2str(long number) {
		String so = nf.format(number);
		so = so.replace(",", "");
		return so;
	}

	public static Long str2ToLong(String str) {
		try {
			return Long.valueOf(str);
		} catch (NumberFormatException ex) {
			return 0L;
		}
	}

	public Number str2num(String str) throws Exception {
		if (StringUtils.isNull(str))
			return null;

		Number result = null;
		try {
			result = nf.parse(str);
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
		return result;
	}

	public static Double str2Double(String str) {
		if (StringUtils.isNull(str))
			return 0D;

		Double result = 0D;
		try {
			result = Double.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int str2Int(String str) {
		if (StringUtils.isNull(str))
			return 0;

		int result = 0;

		try {
			if (str.indexOf(".") > -1) {
				Double d = str2Double(str);
				result = d.intValue();
			} else
				result = Integer.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static BigDecimal obj2BigDecimal(Object obj) {
		BigDecimal result = new BigDecimal(0);
		try {
			if (obj == null)
				return null;
			// DecimalFormat df = new DecimalFormat("#.##");
			if (obj instanceof Double) {
				Double db = (Double) obj;
				result = new BigDecimal(db, MathContext.DECIMAL64);
			} else {
				String str = String.valueOf(obj);
				result = new BigDecimal(str, MathContext.DECIMAL64);
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static Double obj2Double(Object obj, boolean notNull) {
		Double result = 0D;
		try {
			if (obj == null) {
				if (notNull)
					return 0D;
				else
					return null;
			}
			if (obj instanceof Double) {
				result = (Double) obj;
			} else if (obj instanceof String) {
				String str = String.valueOf(obj);
				result = Double.valueOf(str);
			} else if (obj instanceof BigDecimal) {
				String str = String.valueOf(obj);
				result = Double.valueOf(str);
			}
		} catch (Exception e) {

		}
		return result;
	}

	public static int obj2Int(Object obj) {
		if (obj == null)
			return 0;
		int result = 0;

		try {
			String str = String.valueOf(obj);
			if (str.indexOf(".") > -1) {
				Double d = str2Double(str);
				result = d.intValue();
			} else
				result = Integer.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String number2String(double num) throws Exception {
		int x = (int) Math.pow(10, Integer.parseInt(this.precision));
		int intSo = 0;
		int cents = 0;
		String formatStr = "";
		intSo = (int) Math.floor(num * x + 0.50000000001);
		cents = intSo % x;
		if (cents < 0)
			cents = cents * -1;

		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(this.thousand_sep.charAt(0));
		dfs.setDecimalSeparator(this.dot_sep.charAt(0));

		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(dfs);
		df.setMaximumFractionDigits(Integer.parseInt(this.precision));
		if (cents != 0) {
			df.setMinimumFractionDigits(Integer.parseInt(this.precision));
		}
		formatStr = df.format(num).replace(" ", "");
		// System.out.println(formatStr);
		return formatStr;
	}

	public static String getGuID() throws Exception {
		String randomUUIDString = "";
		try {
			UUID uuid = UUID.randomUUID();
			randomUUIDString = uuid.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomUUIDString;
	}

	public static String numberToCurrencyVnd(Object number) {
		if (number == null)
			return "";

		try {
			BigDecimal value;

			if (number instanceof Integer) {
				value = BigDecimal.valueOf((Integer) number);
			} else if (number instanceof Double) {
				value = BigDecimal.valueOf((Double) number);
			} else if (number instanceof BigDecimal) {
				value = (BigDecimal) number;
			} else if (number instanceof String) {
				String str = ((String) number).trim();
				if (str.isEmpty())
					return "";
				value = new BigDecimal(str);
			} else {
				return "";
			}

			// Format theo locale Việt Nam
			@SuppressWarnings("deprecation")
			Locale vietnam = new Locale("vi", "VN");
			NumberFormat formatter = NumberFormat.getCurrencyInstance(vietnam);

			return formatter.format(value);
		} catch (Exception ex) {
			return "";
		}
	}
	/*
	 * public static String number2CurrencyVnd(Object number) { try {
	 * if(number==null) return "";
	 * 
	 * //Locale currentLocale = new Locale("fr", "FR"); Locale currentLocale = new
	 * Locale("de", "DE"); //Locale currentLocale = new Locale("fr", "FR"); String
	 * result=""; NumberFormat numberFormatter =
	 * NumberFormat.getNumberInstance(currentLocale); if(number instanceof Integer)
	 * { Integer quantity = (Integer)number; result =
	 * numberFormatter.format(quantity); } else if(number instanceof Double) {
	 * Double amount = (Double)number; Integer quantity = (int)Math.round(amount);
	 * result = numberFormatter.format(quantity); } else if(number instanceof
	 * BigDecimal) { Double amount = ((BigDecimal) number).doubleValue(); Integer
	 * quantity = (int)Math.round(amount); result =
	 * numberFormatter.format(quantity); } else if(number instanceof String) {
	 * String str=(String)number; if(StringUtils.isNull(str)) return "";
	 * 
	 * if(str.indexOf(".")>-1) { Double amount = Double.parseDouble(str); result =
	 * numberFormatter.format(amount); } else { Integer quantity =
	 * Integer.parseInt(str); result = numberFormatter.format(quantity); } } return
	 * result; } catch(Exception ex) { return ""; } }
	 */

	public static void displayCurrency(Locale currentLocale) {
		BigDecimal amount = new BigDecimal("9876543.21");
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		String currencyOut = currencyFormatter.format(amount);

		System.out.println(currencyOut + "   (" + currentLocale.toString() + ")");
	}

	public static void displayPercent(Locale currentLocale) {
        double percent = 0.75;
        NumberFormat percentFormatter = NumberFormat.getPercentInstance(currentLocale);
        percentFormatter.setMinimumFractionDigits(0); // không có số thập phân, ví dụ: 75%
        String percentOut = percentFormatter.format(percent);

        System.out.println(percentOut + "   (" + currentLocale.toString() + ")");
    }


}
