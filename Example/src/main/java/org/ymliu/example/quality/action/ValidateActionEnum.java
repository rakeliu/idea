package org.ymliu.example.quality.action;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public enum ValidateActionEnum
{
	/**
	 * Equal.
	 */
	EQ("EQ", (String src, String ruleText, char dataType, int... scale) -> {
		{
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
				return decs.length > 0 && decs[0].compareTo(dec) == 0;
			}

			if (dataType == 'D')
			{
				// Datetime
				Date[] dates = parseValue(ruleText, Date.class);
				Date date = convertDate(src);
				return dates.length > 0 && dates[0].equals(date);
			}
			return false;
		}
	}),
	/**
	 * Not Equal.
	 */
	NE("NE", (String src, String ruleText, char dataType, int... scale) -> {
		{
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
				return decs.length > 0 && decs[0].compareTo(dec) != 0;
			}

			if (dataType == 'D')
			{
				// Datetime
				Date[] dates = parseValue(ruleText, Date.class);
				Date date = convertDate(src);
				return dates.length > 0 && !dates[0].equals(date);
			}
			return false;
		}
	});

	private static final SimpleDateFormat[] innerSdfArray = new SimpleDateFormat[]{new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"), new SimpleDateFormat("yyyy/MM/dd"), new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS"), new SimpleDateFormat("yy-MM-dd"), new SimpleDateFormat("yy-MM-dd hh:mm:ss.SSS"), new SimpleDateFormat("yy/MM/dd"), new SimpleDateFormat("yy/MM/dd hh:mm:ss.SSS")};
	private final String name;
	private final ValidInterface action;

	ValidateActionEnum(String name, ValidInterface action)
	{
		this.name = name;
		this.action = action;
	}

	public static ValidateActionEnum getAction(String name)
	{
		for (ValidateActionEnum e : ValidateActionEnum.values())
		{
			if (e.name.equals(name))
			{
				return e;
			}
		}
		throw new RuntimeException("Validation Action [" + name + "] not found!");
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
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
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
					BigDecimal dec = new BigDecimal(s[i]);
					newArray[i] = clazz.cast(dec);
				}

				if (clazz == Date.class)
				{
					newArray[i] = clazz.cast(convertDate(s[i]));
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
		Date date = null;
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

	public boolean doValid(String src, String ruleText, char dataType, int... scale)
	{
		return action.valid(src, ruleText, dataType, scale);
	}
}
