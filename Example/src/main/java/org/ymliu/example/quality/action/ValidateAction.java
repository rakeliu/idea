package org.ymliu.example.quality.action;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Validate Action.
 * The method doValid validates item's data with ruleText.
 * The action is implemented in subclass named with ValidateActionXX, and "XX" is:
 *   EQ - Equal
 *   NE - Not Equal
 *   LT - Less Than
 *   LE - Less Than or Equal
 *   GT - Grater Than
 *   GE - Grater Than or Equal
 *   IN - In (xx,xx,xx) collection.
 *   NN - Not Null, include "not null", "", 0, size()!=0, length !=0.
 *
 */
public abstract class ValidateAction
{
	private static final Map<String, ValidateAction> map = new HashMap<>(0);

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

	public ValidateAction(String id)
	{
		// Get all properties from db according to id,

		// TODO example
		this.id = id;
	}

	private final String id;
	private String methodType; // eg: EQ, NE, LT...

	public static ValidateAction getAction(String method)
	{
		if (!map.containsKey(method))
		{
			throw new RuntimeException("ValidateMethod \"" + method + "\" not found");
		}

		return map.get(method);
	}

	/**
	 * Validate whether the src is validated with ruleText, their data type were "dataType".
	 * If "dataType" is "N", convert to Big Decimal
	 *
	 * @param src the value of product item's value.
	 * @param ruleText validate ruleText, standard value/range to valid.
	 * @param dataType S - String, N - BigDecimal, D - Datetime.
	 * @param scale scale when "dataType" is "N", reserved for the future.
	 * @return true - valid passed, false - otherwise.
	 */
	public abstract boolean doValid(String src, String ruleText, char dataType, int... scale);

	protected void setMethodType(String methodType)
	{
		if (this.methodType != null) {return;}

		// ATTENTION, put this object into static map.
		this.methodType = methodType;
		map.put(this.methodType, this);
	}

	public String getMethodType()
	{
		return methodType;
	}

	public String getId()
	{
		return id;
	}

	/**
	 * Parse "ruleText" into array split by '|', and convert array to Generic Type <T> with passed type Class<T>.
	 *
	 * @param ruleText To be split by '|' into array.
	 * @param clazz To indicate the generic type of <T>, only supports String, BigDecimal, java.util.Date now.
	 *              Much types be supported in the future.
	 * @param <T> Generic type.
	 * @return Array of generic <T>.
	 */
	protected <T> T[] parseValue(String ruleText, final Class<T> clazz)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		// Type check!
		// Only String or BigDecimal supported.
		if (clazz == String.class || clazz == BigDecimal.class || clazz == Date.class)
		{
			String[] s = Arrays.stream(ruleText.split("\\|")).filter(str -> str != null && !"".equals(str)).toArray(String[]::new);

			@SuppressWarnings("unchecked")
			T[] newArray= (T[]) Array.newInstance(clazz, s.length);
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
					newArray[i] = clazz.cast(this.convertDate(s[i]));
				}
			}
			return newArray;
		}
		throw new RuntimeException("Unsupported Class Type");
	}

	/**
	 * Try more date format to convert String to Date.
	 * @param src Date string with one of more formats.
	 * @return java.util.Date.
	 */
	protected Date convertDate(String src){
		Date date = null;
		for (SimpleDateFormat sdf : innerSdfArray){
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
		throw new RuntimeException("Invalid date format: "+src);
	}
}
