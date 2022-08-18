package org.ymliu.validate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TryCodeTest
{
	public static void main(String[] args) throws Exception
	{
		String str = "-365D";
		String regex = "[+\\-]?[1-9][0-9]*[YyMmDdWw]";
		if (str.matches(regex)){
			String s = str.substring(0,str.length()-1);

			System.out.printf("YMD: %s\n", s);

			Calendar calendar = Calendar.getInstance();
			Date d1 = calendar.getTime();

			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.add(Calendar.DATE, Integer.parseInt(s));
			Date d2 = calendar.getTime();

			System.out.printf("Date1 = %d    Date2 = %d\n",d1.getTime(),d2.getTime());
		}else{
			System.out.println("no match");
		}

		// SDF
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2000-1-1");
		System.out.printf("date: %s    %d",date,date.getTime());
	}
}
