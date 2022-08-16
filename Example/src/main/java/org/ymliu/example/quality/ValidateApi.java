package org.ymliu.example.quality;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.ymliu.example.quality.action.ValidateActionEnum;
import org.ymliu.example.quality.bean.ItemValidate;
import org.ymliu.example.quality.bean.ProductValidate;
import org.ymliu.example.quality.bean.ValidateResult;
import org.ymliu.example.quality.bean.ValidateRule;

public class ValidateApi
{
	public ValidateResult valid(String productId, String itemId, String itemValue)
	{
		// TODO: Get ProductValidate by  productId
		ProductValidate pv = new ProductValidate("productId", "ProductID1", "itemId11", 0);
		// TODO: Get ItemValidate by itemId
		ItemValidate iv = new ItemValidate("itemId", "itemId11", 'N', 'A', "error");

		// TODO Get MethodType by ValidateRule.getMethodType()
		List<ValidateRule> rules = this.getRules(productId, itemId);

		// NOTE: The code below are FORMAL code, not TEST code.
		if (null == rules || rules.size() == 0)
		{
			// false
			return ValidateResult.FAILURE_NO_RULES;
		}
		boolean result = iv.getLogic() == 'A';
		for (ValidateRule rule : rules)
		{
			boolean ret = ValidateActionEnum.getAction(rule.getMethodType()).doValid(itemValue, rule.getRuleText(), iv.getDataType());
			if (iv.getLogic() == 'A')
			{
				// AND
				result &= ret;
			}
			else
			{
				// OR
				result |= ret;
			}

			if (!result)
			{
				String[] template = rule.getWarningTemplate().split("\\|");
				int code = Integer.parseInt(template[0]);
				String message = template[1].contains("[%s]") ? String.format(template[1], itemValue) : template[1];
				return new ValidateResult(code, message);
			}
		}
		return ValidateResult.PASSED;   // passed
	}

	private List<ValidateRule> getRules(String productId, String itemId)
	{
		// TODO get ValidateRule(s) from DB.<ValidateRule> by productId & itemId
		ValidateRule[] arrayRules = new ValidateRule[2];
		arrayRules[0] = new ValidateRule("RuleId", "itemId11", 1, "MethodEQ", "EQ", "5", "-1|校验数据[%s]不等于[5]");
		arrayRules[1] = new ValidateRule("RuleId", "itemId11", 0, "MethodNE", "NE", "6", "-2|校验数据[%s]不能等于[6]");

		// Sort by ruleOrder and return as List<>.
		return Arrays.stream(arrayRules).sorted((rule1, rule2) -> rule1.getRuleOrder() - rule2.getRuleOrder()).collect(Collectors.toList());
	}
}
