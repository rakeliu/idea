package org.ymliu.example.quality.action;

public interface ActionInterface
{
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
	boolean valid(String src, String ruleText, char dataType, int... scale);
}
