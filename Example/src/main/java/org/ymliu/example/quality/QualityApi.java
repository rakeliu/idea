package org.ymliu.example.quality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.ymliu.example.quality.bean.ItemValidate;
import org.ymliu.example.quality.bean.ProductValidate;
import org.ymliu.example.quality.bean.ValidateRule;
import org.ymliu.example.quality.validate.AbstractValidateMethod;
import org.ymliu.example.quality.validate.ValidateMethodEq;

public class QualityApi
{
	public String valid(String productId, String itemId, String itemValue)
	{
		// Initialize ValidateMethod collections first.
		this.initAbstractValidate();

		// TODO: Get ProductValidate by  productId
		ProductValidate pv = new ProductValidate("productId", "ProductID1", "itemId11", 0);
		// TODO: Get ItemValidate by itemId
		ItemValidate iv = new ItemValidate("itemId", "itemId11", 'N', 'A', "error");

		// Get MethodType by ValidateRule.getMethodType()
		List<ValidateRule> rules = this.getRules(productId, itemId);
		if (null == rules || rules.size() == 0)
		{
			// false
			return iv.getError();
		}
		boolean result = iv.getLogic() == 'A';
		for (ValidateRule rule : rules)
		{
			AbstractValidateMethod method = AbstractValidateMethod.getValidateMethod(rule.getMethodType());
			boolean ret = method.doValid(itemValue, rule.getRuleText(), iv.getDataType());

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
				return iv.getError();
			}
		}
		return null;
	}

	private List<ValidateRule> getRules(String productId, String itemId)
	{
		// TODO get ValidateRule(s) from DB.<ValidateRule> by productId & itemId
		List<ValidateRule> rules = new ArrayList<>(2);

		// Attention: sort by ruleOrder.
		ValidateRule[] arrayRules = new ValidateRule[2];
		arrayRules[0] = new ValidateRule("RuleId", "itemId11", 0, "MethodEQ", "EQ", "5");
		arrayRules[1] = new ValidateRule("RuleId", "itemId11", 1, "MethodEQ", "EQ", "6");

		// Sort by ruleOrder and return as List<>.
		return Arrays.stream(arrayRules).sorted((rule1, rule2) -> rule1.getRuleOrder() - rule2.getRuleOrder()).collect(Collectors.toList());
	}

	private void initAbstractValidate(){
		new ValidateMethodEq("1");
	}
}
