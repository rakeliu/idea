package org.ymliu.validate;

import java.util.Arrays;
import java.util.List;

import org.ymliu.validate.bean.ItemValidate;
import org.ymliu.validate.bean.ValidateResult;
import org.ymliu.validate.bean.ValidateRule;

public class ValidateTest
{
	public static void main(String[] args)
	{
		//ValidateResult result = api.valid("productId", "itemId", "6.00");

		ValidateApi api = new ValidateApi();
		ValidateResult result = null;
		ItemValidate iv = null;

		// Valid Date
		iv = new ItemValidate("", "", 'D', 'A', "");
		result = api.validate(iv, getRulesDate(), "2022-7-1");
		System.out.printf("Validate Date:  code = %d, message = %s\n", result.getCode(), result.getMessage());

		// Valid Numeric
		iv = new ItemValidate("", "", 'N', 'A', "");
		result = api.validate(iv, getRulesNumber(), "7");
		System.out.printf("Validate Numeric:  code = %d, message = %s\n", result.getCode(), result.getMessage());

		// Valid String
		iv = new ItemValidate("", "", 'S', 'O', "");
		result = api.validate(iv, getRulesNumber(), "7");
		System.out.printf("Validate String:  code = %d, message = %s\n", result.getCode(), result.getMessage());
	}

	private static List<ValidateRule> getRulesDate()
	{
		ValidateRule[] arrayRules = new ValidateRule[]{
				new ValidateRule("", "", 0, "EQ", "EQ", "2022-07-01", "校验数据 [ %s ] 必须是 [ %s ] "),
				new ValidateRule("", "", 1, "NE", "NE", "2022-07-2", "校验数据 [ %s ] 不能等于 [ %s ] "),
				new ValidateRule("", "", 2, "LT", "LT", "2022-9-30", "校验数据 [ %s ] 必须小于 [ %s ] "),
				new ValidateRule("", "", 3, "LE", "LE", "-3d", "校验数据 [ %s ] 必须小于或等于 [ %s ] "),
				new ValidateRule("", "", 4, "GT", "GT", "-2m", "校验数据 [ %s ] 必须大于 [ %s ] "),
				new ValidateRule("", "", 5, "GE", "GE", "-10w", "校验数据 [ %s ] 必须大于或等于 [ %s ] "),
				new ValidateRule("", "", 6, "IN", "IN", "22/7/1|2022-7-1", "校验数据 [ %s ] 应是 [ %s ] 其中一个"),
				new ValidateRule("", "", 7, "NI", "NI", "1d|-2y|-3w", "校验数据 [ %s ] 不应属于 [ %s ] 其中一个"),
				new ValidateRule("", "", 8, "IN", "NN", "", "校验数据 [ %s ] 不能为空"),
				new ValidateRule("", "", 9, "GTLT", "GTLT", "-1y|2022-8-3", "校验数据 [ %s ] 需在( %s )之间"),
				new ValidateRule("", "", 10, "GELT", "GELT", "-1y|1D", "校验数据 [ %s ] 需在[ %s )之间"),
				new ValidateRule("", "", 11, "GTLE", "GTLE", "-1y|1D", "校验数据 [ %s ] 需在( %s ]之间"),
				new ValidateRule("", "", 12, "GELE", "GELE", "-2M|1D", "校验数据 [ %s ] 需在 [ %s ] 之间 %s"),
				new ValidateRule("", "", 13, "REGEX", "REGEX", "^[12]+.*[0-9]$", "校验数据 [ %s ] 需符合正则表达式 [ %s ]"),
				};
		return Arrays.asList(arrayRules);
	}

	private static List<ValidateRule> getRulesNumber()
	{
		ValidateRule[] arrayRules = new ValidateRule[]{
				new ValidateRule("", "", 0, "EQ", "EQ", "6.000000", "校验数据 [ %s ] 必须是 [ %s ] "),
				new ValidateRule("", "", 1, "NE", "NE", "7", "校验数据 [ %s ] 不能等于 [ %s ] "),
				new ValidateRule("", "", 2, "LT", "LT", "100.0", "校验数据 [ %s ] 必须小于 [ %s ] "),
				new ValidateRule("", "", 3, "LE", "LE", "6", "校验数据 [ %s ] 必须小于或等于 [ %s ] "),
				new ValidateRule("", "", 4, "GT", "GT", "5", "校验数据 [ %s ] 必须大于 [ %s ] "),
				new ValidateRule("", "", 5, "GE", "GE", "5", "校验数据 [ %s ] 必须大于或等于 [ %s ] "),
				new ValidateRule("", "", 6, "IN", "IN", "1|3|7|6", "校验数据 [ %s ] 应是 [ %s ] 其中一个"),
				new ValidateRule("", "", 7, "NI", "NI", "1|3|7", "校验数据 [ %s ] 不应属于 [ %s ] 其中一个"),
				new ValidateRule("", "", 8, "IN", "NN", "", "校验数据 [ %s ] 不能为空"),
				new ValidateRule("", "", 9, "GTLT", "GTLT", "5|8", "校验数据 [ %s ] 需在( %s )之间"),
				new ValidateRule("", "", 10, "GELT", "GELT", "6.0|8", "校验数据 [ %s ] 需在[ %s )之间"),
				new ValidateRule("", "", 11, "GTLE", "GTLE", "5.9|8", "校验数据 [ %s ] 需在( %s ]之间"),
				new ValidateRule("", "", 12, "GELE", "GELE", "6|8", "校验数据 [ %s ] 需在 [ %s ] 之间"),
				new ValidateRule("", "", 13, "REGEX", "REGEX", "[0-9]{1}\\.[0-9]{1}", "校验数据 [ %s ]需符合正则表达式 [ %s ]"),
				};

		return Arrays.asList(arrayRules);
	}
}
