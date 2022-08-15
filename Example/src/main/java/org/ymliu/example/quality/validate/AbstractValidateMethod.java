package org.ymliu.example.quality.validate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractValidateMethod
{
	private static final Map<String, AbstractValidateMethod> map = new HashMap<>(0);

	public static AbstractValidateMethod getValidateMethod(String method)
	{
		if (!map.containsKey(method))
		{
			throw new RuntimeException("ValidateMethod \"" + method + "\" not found");
		}

		return map.get(method);
	}

	private final String id;
	private String methodType; // eg: EQ, NE, LT...

	public AbstractValidateMethod(String id)
	{
		// Get all properties from db according to id,

		// TODO example
		this.id = id;
	}

	/**
	 * Validate whether the src is validated with ruleText, their data type were "dataType".
	 * If "dataType" is "N", convert to Big Decimal
	 *
	 * @param src
	 * @param ruleText
	 * @param dataType
	 * @param scale
	 * @return
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

	/**
	 * 拆分规则 value字段，拆分为数组形式，数据类型为 vType字段（String | BigDecimal | Datetime）
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	protected <T> T[] parseValue(String ruleText, final Class<T> clazz)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		// Type check!
		// Only String or BigDecimal supported.
		if (clazz == String.class || clazz == BigDecimal.class || clazz == Date.class)
		{
			String[] s = Arrays.stream(ruleText.split("\\|")).filter(str -> str != null && !"".equals(str)).toArray(String[]::new);

			@SuppressWarnings("unchecked") T[] newArray = (T[]) new Object[s.length];
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
					// NOTE: date format must be "yyyy-MM-dd hh:mm:ss.SSS".
					try
					{
						newArray[i] = clazz.cast(sdf.parse(s[i]));
					}
					catch (ParseException e)
					{
						throw new RuntimeException(e);
					}
				}
			}
			return newArray;
		}
		throw new RuntimeException("Unsupported Class Type");
	}
}
