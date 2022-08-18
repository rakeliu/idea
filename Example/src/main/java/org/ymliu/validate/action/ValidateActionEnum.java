package org.ymliu.validate.action;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Enumeration for Validate Action, implements the interface ActionInterface on each enumeration.
 * EQ - Equal
 * NE - Not Equal
 * LT - Less Than
 * LE - Less Than or Equal
 * GT - Grater Than
 * GE - Grater Than or Equal
 * IN - In (xx,xx,xx) collection.
 * NI - Not In (xx,xx,xx) collection.
 * NN - Not Null, alias for Exist, include "not null", "", 0, size()!=0, length !=0.
 * LTGT - Range of (min, max)
 * LEGT - Range of [min, max)
 * LTGE - Range of (min, max]
 * LEGE - Range of [min, max]
 * REGEX - Regular expression matching check.
 */
public enum ValidateActionEnum
{
	/**
	 * Equal, x == value.
	 */
	EQ("EQ", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			return strings.length > 0 && Objects.equals(strings[0], src);
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length > 0)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
				return dec.compareTo(decs[0]) == 0;
			}
			return false;
			//return decs.length > 0 && decs[0].compareTo(dec) == 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			return dates.length > 0 && dates[0].equals(date);
		}
		return false;
	}),
	/**
	 * Not Equal, x != value.
	 */
	NE("NE", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			return strings.length > 0 && !Objects.equals(strings[0], src);
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length > 0)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
				return dec.compareTo(decs[0]) != 0;
			}
			return false;
			//return decs.length > 0 && decs[0].compareTo(dec) != 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			return dates.length > 0 && !dates[0].equals(date);
		}
		return false;
	}),

	/**
	 * Less Than, x < value.
	 */
	LT("LT", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length == 0 || null == strings[0])
			{
				return false;
			}
			if (null == src)
			{
				return true;
			}
			return src.compareTo(strings[0]) < 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			//return decs.length > 0 && dec.compareTo(decs[0]) < 0;
			if (decs.length > 0)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
				return dec.compareTo(decs[0]) < 0;
			}
			return false;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			return dates.length > 0 && date.compareTo(dates[0]) < 0;
		}
		return false;
	}),
	/**
	 * Less or Equal, x <= value.
	 */
	LE("LE", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (null == src) {return true;}
			if (strings.length == 0 || null == strings[0]) {return false;}
			return src.compareTo(strings[0]) <= 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			//return decs.length > 0 && dec.compareTo(decs[0]) <= 0;
			if (decs.length > 0)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
				return dec.compareTo(decs[0]) <= 0;
			}
			return false;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			return dates.length > 0 && date.compareTo(dates[0]) <= 0;
		}
		return false;
	}),

	/**
	 * Grater Than, x > value.
	 */
	GT("GT", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (null == src) {return false;}
			if (strings.length == 0 || null == strings[0]) {return false;}
			return src.compareTo(strings[0]) > 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			//return decs.length > 0 && dec.compareTo(decs[0]) > 0;
			if (decs.length > 0)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
				return dec.compareTo(decs[0]) > 0;
			}
			return false;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			return dates.length > 0 && date.compareTo(dates[0]) > 0;
		}
		return false;
	}),
	/**
	 * Grater Than, x >= value.
	 */
	GE("GE", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (null == src) {return strings.length == 0 || null == strings[0];}
			if (strings.length == 0 || null == strings[0]) {return true;}
			return src.compareTo(strings[0]) >= 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			// return decs.length > 0 && dec.compareTo(decs[0]) >= 0;
			if (decs.length > 0)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
				return dec.compareTo(decs[0]) >= 0;
			}
			return false;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			return dates.length > 0 && date.compareTo(dates[0]) >= 0;
		}
		return false;
	}),
	/**
	 * IN, x in (xx,xx,xx), x equals any item of collection.
	 */
	IN("IN", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length == 0) {return null == src;}
			if (null == src) {return false;}
			for (String s : strings)
			{
				if (Objects.equals(src, s)) {return true;}
			}
			return false;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length == 0) {return false;}
			for (BigDecimal d : decs)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				d = d.setScale(maxScale, RoundingMode.UNNECESSARY);
				if (dec.compareTo(d) == 0) {return true;}
			}
			return false;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			if (dates.length == 0) {return false;}
			for (Date d : dates)
			{
				if (Objects.equals(date, d)) {return true;}
			}
			return false;
		}
		return false;
	}),
	/**
	 * Not In, x not in (xx,xx,xx), x does not equal any item of collection.
	 */
	NI("NI", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length == 0) {return null != src;}
			if (null == src) {return false;}
			for (String s : strings)
			{
				if (Objects.equals(src, s)) {return false;}
			}
			return true;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length == 0) {return true;}
			for (BigDecimal d : decs)
			{
				int maxScale = Math.max(decs[0].scale(), dec.scale());
				dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
				d = d.setScale(maxScale, RoundingMode.UNNECESSARY);
				if (dec.compareTo(d) == 0) {return false;}
				//if (Objects.equals(dec, d)) {return false;}
			}
			return true;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			if (dates.length == 0) {return false;}
			for (Date d : dates)
			{
				if (Objects.equals(date, d)) {return false;}
			}
			return true;
		}
		return false;
	}),
	/**
	 * Not Null, x != null and x.trim() has character.
	 * ignore dataType check.
	 */
	NN("NN", (String src, String ruleText, char dataType, int... scale) -> {
		// ignore dataType check.
		return null != src && src.trim().length() > 0;
		// TODO: Unchecked Numeric cast error.
	}),
	/**
	 * Grater Than & Less Than, min < x < max.
	 */
	GTLT("GTLT", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length < 2) {return false;}
			if (null == src) {return false;}
			return src.compareTo(strings[0]) > 0 && src.compareTo(strings[1]) < 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length < 2) {return true;}
			int maxScale = Math.max(decs[0].scale(), decs[1].scale());
			maxScale = Math.max(maxScale, dec.scale());
			dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[1] = decs[1].setScale(maxScale, RoundingMode.UNNECESSARY);
			return dec.compareTo(decs[0]) > 0 && dec.compareTo(decs[1]) < 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			if (dates.length < 2) {return false;}
			return date.compareTo(dates[0]) > 0 && date.compareTo(dates[1]) < 0;
		}
		return false;
	}),
	/**
	 * Grater or Equal & Less Than, min <= x < max.
	 */
	GELT("GELT", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length < 2) {return false;}
			if (null == src) {return false;}
			return src.compareTo(strings[0]) >= 0 && src.compareTo(strings[1]) < 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length < 2) {return true;}
			int maxScale = Math.max(decs[0].scale(), decs[1].scale());
			maxScale = Math.max(maxScale, dec.scale());
			dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[1] = decs[1].setScale(maxScale, RoundingMode.UNNECESSARY);
			return dec.compareTo(decs[0]) >= 0 && dec.compareTo(decs[1]) < 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			if (dates.length < 2) {return false;}
			return date.compareTo(dates[0]) >= 0 && date.compareTo(dates[1]) < 0;
		}
		return false;
	}),
	/**
	 * Grater Than & Less or Equal, min < x <= max.
	 */
	GTLE("GTLE", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length < 2) {return false;}
			if (null == src) {return false;}
			return src.compareTo(strings[0]) > 0 && src.compareTo(strings[1]) <= 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length < 2) {return true;}
			int maxScale = Math.max(decs[0].scale(), decs[1].scale());
			maxScale = Math.max(maxScale, dec.scale());
			dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[1] = decs[1].setScale(maxScale, RoundingMode.UNNECESSARY);
			return dec.compareTo(decs[0]) > 0 && dec.compareTo(decs[1]) <= 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			if (dates.length < 2) {return false;}
			return date.compareTo(dates[0]) > 0 && date.compareTo(dates[1]) <= 0;
		}
		return false;
	}),
	/**
	 * Grater or Equal & Less or Equal, min <= x <= max.
	 */
	GELE("GELE", (String src, String ruleText, char dataType, int... scale) -> {
		if (dataType == 'S')
		{
			// String
			String[] strings = parseValue(ruleText, String.class);
			if (strings.length < 2) {return false;}
			if (null == src) {return false;}
			return src.compareTo(strings[0]) >= 0 && src.compareTo(strings[1]) <= 0;
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			if (decs.length < 2) {return true;}
			int maxScale = Math.max(decs[0].scale(), decs[1].scale());
			maxScale = Math.max(maxScale, dec.scale());
			dec = dec.setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[0] = decs[0].setScale(maxScale, RoundingMode.UNNECESSARY);
			decs[1] = decs[1].setScale(maxScale, RoundingMode.UNNECESSARY);
			return dec.compareTo(decs[0]) >= 0 && dec.compareTo(decs[1]) <= 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = parseValue(ruleText, Date.class);
			Date date = convertDate(src);
			if (dates.length < 2) {return false;}
			return date.compareTo(dates[0]) >= 0 && date.compareTo(dates[1]) <= 0;
		}
		return false;
	}),
	/**
	 * Regular expression check.
	 */
	REGEX("REGEX", (String src, String ruleText, char dataType, int... scale) -> {
		if (null == src)
		{return false;}
		boolean ret = false;
		try
		{
			ret = Pattern.matches(ruleText, src);
		}
		catch (PatternSyntaxException ignored)
		{
		}
		return ret;
	});

	private static final String regexRelativeDate = "[+\\-]?[1-9][0-9]*[YyMmDdWw]";
	private static final SimpleDateFormat[] innerSdfArray = new SimpleDateFormat[]{
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"),
			new SimpleDateFormat("yyyy/MM/dd"),
			new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS"),
			new SimpleDateFormat("yy-MM-dd"),
			new SimpleDateFormat("yy-MM-dd hh:mm:ss.SSS"),
			new SimpleDateFormat("yy/MM/dd"),
			new SimpleDateFormat("yy/MM/dd hh:mm:ss.SSS")
	};
	private final String name;
	private final ActionInterface action;

	ValidateActionEnum(String name, ActionInterface action)
	{
		this.name = name;
		this.action = action;
	}

	public static ValidateActionEnum getAction(String name)
	{
		for (ValidateActionEnum e : ValidateActionEnum.values())
		{
			if (e.name.equals(name)) {return e;}
		}
		throw new RuntimeException("Validation Action [" + name + "] not found!");
	}

	/**
	 * Whether the name of action in array "actionNames" exists in Enumeration.
	 *
	 * @param actionNames array of actionName.
	 * @throws RuntimeException If any actionName does not exist, throw it.
	 */
	public static void CheckActions(String[] actionNames) throws RuntimeException
	{
		if (null == actionNames || actionNames.length == 0) {return;}

		boolean ret;
		for (String name : actionNames)
		{
			ret = false;
			for (ValidateActionEnum action : ValidateActionEnum.values())
			{
				if (action.name.equals(name))
				{
					ret = true;
					break;
				}
			}

			if (!ret)
			{
				throw new RuntimeException("ValidateAction [" + name + "] NOT defined.");
			}
		}
	}

	/**
	 * Parse "ruleText" into array split by '|', and convert array to Generic Type <T> with passed type Class<T>.
	 *
	 * @param ruleText To be split by '|' into array.
	 * @param clazz    To indicate the generic type of <T>, only supports String, BigDecimal, java.util.Date now.
	 *                 More types be supported in the future.
	 * @param <T>      Generic type.
	 * @return Array of generic <T>.
	 */
	private static <T> T[] parseValue(String ruleText, final Class<T> clazz)
	{
		// Type check!
		// Only String or BigDecimal supported.
		if (clazz == String.class || clazz == BigDecimal.class || clazz == Date.class)
		{
			String[] s = Arrays.stream(ruleText.split("\\|")).filter(str -> str != null && !"".equals(str)).toArray(String[]::new);

			@SuppressWarnings("unchecked") T[] newArray = (T[]) Array.newInstance(clazz, s.length);
			for (int i = 0; i < s.length; i++)
			{
				if (clazz == String.class)
				{
					newArray[i] = clazz.cast(s[i]);
				}

				if (clazz == BigDecimal.class)
				{
					BigDecimal dec = new BigDecimal(s[i].trim());
					newArray[i] = clazz.cast(dec);
				}

				if (clazz == Date.class)
				{
					newArray[i] = clazz.cast(convertDate(s[i].trim()));
				}
			}
			return newArray;
		}
		throw new RuntimeException("Unsupported Class Type");
	}

	/**
	 * Try more date format to convert String to Date.
	 *
	 * @param src Date string with one of more formats.
	 * @return java.util.Date.
	 */
	private static Date convertDate(String src)
	{
		if (null == src || src.trim().length() == 0)
		{
			throw new NullPointerException("empty string cannot be convert to date");
		}

		// Relative Date.
		if (src.matches(regexRelativeDate))
		{
			// Relative Date.
			String sType = src.substring(src.length() - 1).toUpperCase();
			String s = src.substring(0, src.length() - 1);
			int i = Integer.parseInt(s);
			Calendar calendar = Calendar.getInstance();
			switch (sType)
			{
				case "Y":
					calendar.add(Calendar.YEAR, i); /* year */
					break;
				case "M":
					calendar.add(Calendar.MONTH, i); /* month */
					break;
				case "D":
					calendar.add(Calendar.DATE, i); /* day, day-of-month */
					break;
				case "W":
					calendar.add(Calendar.WEEK_OF_YEAR, i); /* week */
					break;
			}
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		}

		// Absolute Date, convert date by each format.
		Date date;
		for (SimpleDateFormat sdf : innerSdfArray)
		{
			try
			{
				date = sdf.parse(src);
			}
			catch (ParseException e)
			{
				continue;
			}
			return date;
		}
		throw new RuntimeException("Invalid date format: " + src);
	}

	public boolean validate(String src, String ruleText, char dataType, int... scale)
	{
		return action.doValidate(src, ruleText, dataType, scale);
	}
}
