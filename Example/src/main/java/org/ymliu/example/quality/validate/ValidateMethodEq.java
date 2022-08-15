package org.ymliu.example.quality.validate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ValidateMethodEq extends AbstractValidateMethod
{
	public ValidateMethodEq(String id)
	{
		super(id);
		this.setMethodType("EQ");
	}

	/**
	 * Validate the src is equals ruleText.
	 * If dataType is S (String)， call Strings.Equals.
	 * If dataType is N (Decimal), call Decimal.compareTo == 0, and setScale.
	 * NOTE:
	 * if dataType is N (Decimal), setScale reduces scale.
	 *
	 * @param src
	 * @param ruleText
	 * @param dataType
	 * @param scale
	 * @return
	 */
	@Override
	public boolean doValid(String src, String ruleText, char dataType, int... scale)
	{
		if (dataType == 'S')
		{
			// String
			String[] strings = this.parseValue(ruleText, String.class);
			return strings.length > 0 && Objects.equals(strings[0], src);
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = this.parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			return decs.length > 0 && decs[0].compareTo(dec) == 0;
		}

		if (dataType == 'D'){
			// Datetime
			Date[] dates = this.parseValue(ruleText, Date.class);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date;
			try
			{
				date = sdf.parse(src);
			}
			catch (ParseException e)
			{
				// TODO log exception.
				System.out.printf("runtime exception: %s", e);
				return false;
			}
			return dates.length>0 && dates[0].equals(date);
		}


		return false;
	}
}
