package org.ymliu.example.quality;

import java.util.Arrays;
import java.util.IllegalFormatException;
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
		//ItemValidate iv = new ItemValidate("itemId", "itemId11", 'N', 'A', "error");
		//ItemValidate iv = new ItemValidate("itemId", "itemId11", 'S', 'A', "error");
		ItemValidate iv = new ItemValidate("itemId", "itemId11", 'D', 'A', "error");

		// TODO Get MethodType by ValidateRule.getMethodType()
		List<ValidateRule> rules = this.getRules(productId, itemId);

		// NOTE: The code below are FORMAL code, not TEST code.
		if (null == rules || rules.size() == 0)
		{
			// false
			return ValidateResult.FAILURE_NO_RULES;
		}
		boolean result = iv.getLogic() == 'A';
		try
		{
			for (ValidateRule rule : rules)
			{
				boolean ret = ValidateActionEnum.getAction(rule.getMethodType()).doValid(itemValue, rule.getRuleText()
						, iv.getDataType());
				if (iv.getLogic() == 'A') {result &= ret; /* AND */}
				else {result |= ret; /* OR */}

				if (!result) {return new ValidateResult(-1, getErrMsg(itemValue, rule));}
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
			return new ValidateResult(-200, e.getMessage());
		}
		return ValidateResult.PASSED;   // passed
	}

	private List<ValidateRule> getRules(String productId, String itemId)
	{
		// TODO get ValidateRule(s) from DB.<ValidateRule> by productId & itemId
		ValidateRule[] arrayRules = new ValidateRule[]{
				// itemValue = "6.0"
				//				new ValidateRule("", "", 0, "EQ", "EQ", "6.000000", "校验数据 [ %s ] 必须是 [ %s ] "),
				//				new ValidateRule("", "", 1, "NE", "NE", "7", "校验数据 [ %s ] 不能等于 [ %s ] "),
				//				new ValidateRule("", "", 2, "LT", "LT", "100.0", "校验数据 [ %s ] 必须小于 [ %s ] "),
				//				new ValidateRule("", "", 3, "LE", "LE", "6", "校验数据 [ %s ] 必须小于或等于 [ %s ] "),
				//				new ValidateRule("", "", 4, "GT", "GT", "5", "校验数据 [ %s ] 必须大于 [ %s ] "),
				//				new ValidateRule("", "", 5, "GE", "GE", "5", "校验数据 [ %s ] 必须大于或等于 [ %s ] "),
				//				new ValidateRule("", "", 6, "IN", "IN", "1|3|7|6", "校验数据 [ %s ] 应是 [ %s ] 其中一个"),
				//				new ValidateRule("", "", 7, "NI", "NI", "1|3|7", "校验数据 [ %s ] 不应属于 [ %s ] 其中一个"),
				//				new ValidateRule("", "", 8, "IN", "NN", "", "校验数据 [ %s ] 不能为空"),
				//				new ValidateRule("", "", 9, "GTLT", "GTLT", "5|8", "校验数据 [ %s ] 需在( %s )之间"),
				//				new ValidateRule("", "", 10, "GELT", "GELT", "6.0|8", "校验数据 [ %s ] 需在[ %s )之间"),
				//				new ValidateRule("", "", 11, "GTLE", "GTLE", "5.9|8", "校验数据 [ %s ] 需在( %s ]之间"),
				//				new ValidateRule("", "", 12, "GELE", "GELE", "6|8", "校验数据 [ %s ] 需在 [ %s ] 之间"),
				//				new ValidateRule("", "", 13, "REGEX", "REGEX", "[0-9]{1}\\.[0-9]{1}", "校验数据 [ %s ]
				//				需符合正则表达式 [ %s ]" + " "),

				// itemValue = "2022-07-1"
				new ValidateRule("", "", 0, "EQ", "EQ", "2022-07-01", "校验数据 [ %s ] 必须是 [ %s ] "),
				new ValidateRule("", "", 1, "NE", "NE", "2022-07-2", "校验数据 [ %s ] 不能等于 [ %s ] "),
				new ValidateRule("", "", 2, "LT", "LT", "2022-9-30", "校验数据 [ %s ] 必须小于 [ %s ] "),
				new ValidateRule("", "", 3, "LE", "LE", "-3d", "校验数据 [ %s ] 必须小于或等于 [ %s ] "),
				/*
				new ValidateRule("", "", 4, "GT", "GT", "5", "校验数据 [ %s ] 必须大于 [ %s ] "),
				new ValidateRule("", "", 5, "GE", "GE", "5", "校验数据 [ %s ] 必须大于或等于 [ %s ] "),
				new ValidateRule("", "", 6, "IN", "IN", "1|3|7|6", "校验数据 [ %s ] 应是 [ %s ] 其中一个"),
				new ValidateRule("", "", 7, "NI", "NI", "1|3|7", "校验数据 [ %s ] 不应属于 [ %s ] 其中一个"),
				new ValidateRule("", "", 8, "IN", "NN", "", "校验数据 [ %s ] 不能为空"),
				new ValidateRule("", "", 9, "GTLT", "GTLT", "5|8", "校验数据 [ %s ] 需在( %s )之间"),
				new ValidateRule("", "", 10, "GELT", "GELT", "6|8", "校验数据 [ %s ] 需在[ %s )之间"),
				new ValidateRule("", "", 11, "GTLE", "GTLE", "5.9|8", "校验数据 [ %s ] 需在( %s ]之间"),
				new ValidateRule("", "", 12, "GELE", "GELE", "6|8", "校验数据 [ %s ] 需在 [ %s ] 之间"),
				new ValidateRule("", "", 13, "REGEX", "REGEX", "[0-9]{2}", "校验数据 [ %s ] 需符合正则表达式 [ %s ] "),*/
				};

		// Sort by ruleOrder and return as List<>.
		return Arrays.stream(arrayRules).sorted((rule1, rule2) -> rule1.getRuleOrder() - rule2.getRuleOrder()).collect(Collectors.toList());
	}

	private String getErrMsg(String src, ValidateRule rule)
	{
		final String s = " %s ";
		int pos, lastPos = 0;
		int count = 0;
		String template = rule.getWarningTemplate();
		while (lastPos + 1 < template.length())
		{
			pos = template.indexOf(s, lastPos);
			if (pos == -1) {break;}
			count++;
			lastPos = pos + 1;
		}

		switch (count)
		{
			case 0:
				return template;
			case 1:
				return String.format(template, src);
			case 2:
				return String.format(template, src, rule.getRuleText());
			default:
				return template;
		}
	}
}
