package vn.leoo.common.util.formatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.leoo.common.util.StringUtils;

public class DateFormatter {
	public static Timestamp str2time(String str)  {
		if (StringUtils.isNull(str))
			return null;
		
		Timestamp result = null;
		SimpleDateFormat sdfTime1 =new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		SimpleDateFormat sdfTime2 =new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		SimpleDateFormat sdfTime3 =new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(str.indexOf("-")>0)
				result = new Timestamp(sdfTime1.parse(str).getTime());
			else if(str.indexOf("/")>0){
				try{
					if(str.length()==10){
						str=str+" 00:00:00";
						result = new Timestamp(sdfTime2.parse(str).getTime());
						return result;
					}
					else
					{
						result = new Timestamp(sdfTime2.parse(str).getTime());
						return result;
					}
				}
				catch(Exception e)
				{
				}
				try{
					result = new Timestamp(sdfTime3.parse(str).getTime());
					return result;
				}
				catch(Exception e)
				{
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public static Date str2Date(String strDate)  {
		if (StringUtils.isNull(strDate))
			return null;
		
		Date result = null;
		String str=strDate;
		SimpleDateFormat sdfTime1 =new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdfTime2 =new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(str.indexOf(" ")>0)
				str=str.split(" ")[0];
			if(str.indexOf("-")>0)
				result = sdfTime1.parse(str);
			else if(str.indexOf("/")>0){
				result = sdfTime2.parse(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public static Date str2Date(String strDate, String format){
		SimpleDateFormat parser = new SimpleDateFormat(format);
		try {
			return parser.parse(strDate);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static String date2Str(Date date,String format){
		SimpleDateFormat parser = new SimpleDateFormat(format);
		try {
			return parser.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String date2strDateTime(Date date) throws Exception{
		SimpleDateFormat sdfTime1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		try {
			if (date == null)
				return null;
			String str=sdfTime1.format(date);
			return str;
		} catch (Exception e) {
			throw new Exception(e.toString());
		}		
	}

	public static Timestamp str2Timestamp(String strDate) throws Exception {
		String str=strDate.replace("/", "-");
		if(str.indexOf("-")<0)
			return null;
		String dateFormat="dd-MMM-yyyy HH:mm:ss";
		SimpleDateFormat formaterD = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		if (StringUtils.isNull(str))
			return null;
		Timestamp result = null;
		try {
			result = new Timestamp(formaterD.parse(str).getTime());
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
		return result;
	}
	
	public static String timestamp2Str(Timestamp ts) throws Exception {
		String str = ts.toString();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			Date fechaNueva = format.parse(str);
			format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
			str = format.format(fechaNueva);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;
	}
	
	public static Timestamp date2Timestamp(Date date) throws Exception{
		Timestamp time = null;
		try{
			time = new Timestamp(date.getTime());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return time;
	}
	
	public static int getYear(Date date){
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			return year;
		}
		catch(Exception ex)
		{
			return 0;
		}
	}
	
	public static String getSimpleDateFormat(String date_source) throws Exception{
		String simple_date = "";		 
		String year = "";
		String month = "";
		String day = "";
		String separator = "/"; // Separator for date
		String dtSep = " "; // Separator for date time
		String[] dates = null;
		
		if(date_source.contains("-")){
			separator = "-";
		}
		int iTS = date_source.lastIndexOf("T");
		if(iTS==10 || iTS==11){
			dtSep = "T";
		}
		
		dates = date_source.split(separator);
		year = dates[0];
		month = dates[1];
		if(year.length() < 4){
			day = year;
			if(day.length()==2 && month.length()==2){
				simple_date = "dd"+separator+"MM"+separator+"yyyy";
				if(Integer.parseInt(month) > 12)
					simple_date = "MM"+separator+"dd"+separator+"yyyy";
			}else if(day.length()==3 && month.length()==2){
				simple_date = "MMM"+separator+"dd"+separator+"yyyy";
			}else if(day.length()==2 && month.length()==3){
				simple_date = "dd"+separator+"MMM"+separator+"yyyy";
			}
		}else if(year.length() == 4){
			int iDayStart = date_source.lastIndexOf(separator);
			int iDayEnd = date_source.lastIndexOf(dtSep);
			if(iDayEnd < 0){
				iDayEnd = date_source.length();
			}
			day = date_source.substring(iDayStart+1, iDayEnd);
			
			if(day.length()==2 && month.length()==2){
				simple_date = "yyyy"+separator+"MM"+separator+"dd";
				if(Integer.parseInt(month) > 12)
					simple_date = "yyyy"+separator+"dd"+separator+"MM";
			}else if(day.length()==3 && month.length()==2){
				simple_date = "yyyy"+separator+"dd"+separator+"MMM";
			}else if(day.length()==2 && month.length()==3){
				simple_date = "yyyy"+separator+"MMM"+separator+"dd";
			}
		}
		if(date_source.contains(":")){
			if(StringUtils.isNotEmpty(dtSep)) {
				dtSep = "'"+ dtSep + "'";
			}
			simple_date += dtSep + "HH:mm:ss";
		}
		if(date_source.contains("+")){
			simple_date += "+";
		}else if(date_source.contains("-")){
			simple_date += "-";
		}
		
		return simple_date;
	}
	
	public static String localDate2Str(LocalDate date,String format){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		try {
			return date.format(formatter);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String localDateTime2Str(LocalDateTime date,String format){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		try {
			return date.format(formatter);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static LocalDateTime str2LocalDateTime(String strDate, String format){
		if(StringUtils.isNotEmpty(strDate)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			LocalDateTime dateTime = LocalDateTime.parse(strDate, formatter);
			
			return dateTime;
		}
		
		return null;
	}
	
//	public static void main(String[] args) {
//		try {
//			String date_source = "2004-12-14T00:00:00+07:00";
//			String simple_date = DateFormatter.getSimpleDateFormat(date_source);	
//			System.out.println(simple_date);
//			SimpleDateFormat sdf = new SimpleDateFormat(simple_date, Locale.ENGLISH); //"yyyy-MM-dd'T'HH:mm:ss"
//			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//	        Date date = sdf.parse(date_source);
//	        System.out.println(date.toString());
//	        
//			/*String strNgayHd = DateFormatter.date2Str(new Date(), Constants.DATE_FORMAT);
//			String arrNgay[] = strNgayHd.split("-");
//			strNgayHd = "Ng\u00E0y " + arrNgay[0] + " th\u00E1ng " + arrNgay[1] + " n\u0103m " + arrNgay[2];
//			System.out.println(strNgayHd);*/
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//	}
}
