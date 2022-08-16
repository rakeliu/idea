package org.ymliu.example.quality.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Validate Action is Not Equal (NE).
 */
@Deprecated
public class ValidateActionNE extends ValidateAction
{

	public ValidateActionNE(String id)
	{
		super(id);
		this.setMethodType("NE");
	}

	@Override
	public boolean doValid(String src, String ruleText, char dataType, int... scale)
	{
		if (dataType == 'S')
		{
			// String
			String[] strings = this.parseValue(ruleText, String.class);
			return strings.length > 0 && !Objects.equals(strings[0], src);
		}

		if (dataType == 'N')
		{
			// BigDecimal
			BigDecimal[] decs = this.parseValue(ruleText, BigDecimal.class);
			BigDecimal dec = new BigDecimal(src);
			return decs.length > 0 && decs[0].compareTo(dec) != 0;
		}

		if (dataType == 'D')
		{
			// Datetime
			Date[] dates = this.parseValue(ruleText, Date.class);
			Date date = this.convertDate(src);
			return dates.length > 0 && !dates[0].equals(date);
		}

		return false;
	}
}
