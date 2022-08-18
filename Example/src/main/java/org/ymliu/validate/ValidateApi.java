package org.ymliu.validate;

import java.util.Comparator;
import java.util.IllegalFormatException;
import java.util.List;

import org.ymliu.validate.action.ValidateActionEnum;
import org.ymliu.validate.bean.ItemValidate;
import org.ymliu.validate.bean.ProductValidate;
import org.ymliu.validate.bean.ValidateRule;

public class ValidateApi
{
	/**
	 * The method is called by TestCase.
	 *
	 * @param iv    ItemValidate Object.
	 * @param rules ValidateRule List, allow unsorted list, it will be resorted.
	 * @param value value to be valid.
	 * @return validate result.
	 */
	public ValidateResult validate(ItemValidate iv, List<ValidateRule> rules, String value)
	{
		if (null == iv || null == rules || rules.size() == 0)
		{
			return ValidateResult.FAILURE_NO_RULES;
		}

		// Sort ValidateRule list on key "ruleOrder".
		rules.sort(Comparator.comparingInt(ValidateRule::getRuleOrder));

		// Start validate
		boolean ret;
		try
		{
			for (ValidateRule rule : rules)
			{
				// Really valid.
				ret = ValidateActionEnum.getAction(rule.getMethodType()).validate(value, rule.getRuleText(), iv.getDataType());

				// TODO: how to improve performance only tow conditions below ?
				// Then check valid-result one by one rule to improve performance.

				// Logic is AND, return NOT PASSED when any result was false.
				if (iv.getLogic() == 'A' && !ret)
				{
					return new ValidateResult(-1, this.getErrMsg(rule.getTemplate(), rule.getRuleText(), value));
				}

				// Logic is OR, return PASSED when any result was true
				if (iv.getLogic() == 'O' && ret)
				{
					return ValidateResult.PASSED;
				}
			}
		}
		catch (IllegalFormatException | NumberFormatException e)
		{
			return ValidateResult.FAILURE_FORMAT;
		}
		catch (RuntimeException e)
		{
			return ValidateResult.FAILURE_RUNTIME;
		}
		catch (Exception e)
		{
			return new ValidateResult(-200, "代码错误：\n" + e.getMessage());
		}

		// Default Validate result:
		// If logic is A (AND), all valid passed, return PASSED!
		// If logic is O (OR),  all valid not passed, return error from template
		return iv.getLogic() == 'A' ? ValidateResult.PASSED : new ValidateResult(-1, this.getErrMsg(iv.getTemplate(), "", value));
	}

	public ValidateResult validate(String productId, String itemId, String value)
	{
		// TODO: Get ProductValidate by  productId, Maybe it was ignores.
		ProductValidate pv = null;
		// TODO: Get ItemValidate by itemId
		ItemValidate iv = null;
		// TODO: Get ValidateRule LIST (Sorted/Unsorted) by itemId;
		List<ValidateRule> rules = null;

		return this.validate(iv, rules, value);
	}


	/**
	 * Fill warningTemplate with src and ruleText to show to user.
	 *
	 * @param template      Template
	 * @param ruleText      ruleText.
	 * @param originalValue The value to be valid.
	 * @return ErrorMessage filled by originalValue and ruleText with warningTemplate.
	 */
	private String getErrMsg(String template, String ruleText, String originalValue)
	{
		final String s = "%s";
		int pos, lastPos = 0;
		int count = 0;
		while (lastPos + 1 < template.length() || count > 2)
		{
			pos = template.indexOf(s, lastPos);
			if (pos == -1) {break;}

			count++;
			lastPos = pos + 1;
		}

		switch (count)
		{
			case 1:
				return String.format(template, originalValue);
			case 2:
				return String.format(template, originalValue, ruleText);
			case 0:
			default:
				return template.replace(s, "％ｓ");
		}
	}
}
