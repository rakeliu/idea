package org.ymliu.example.quality.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Validate Action is Equal(EQ).
 */
public class ValidateActionEQ extends ValidateAction
{
	public ValidateActionEQ(String id)
	{
		super(id);
		this.setMethodType("EQ");
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
			Date date = this.convertDate(src);
			return dates.length>0 && dates[0].equals(date);
		}

		return false;
	}
}
