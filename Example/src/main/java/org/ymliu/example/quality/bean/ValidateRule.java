package org.ymliu.example.quality.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验规则，一条。
 *
 * @author LiuYamin
 */
public class ValidateRule implements IValid
{
	private final String id;
	private final String itemId;
	private final int ruleOrder;
	private final String methodId;
	private final String methodType;
	private final String ruleText;


	public ValidateRule(String id)
	{
		// Get all properties from db according to id.
		this.id = id;

		// TODO read from DB to set properties.
		this.itemId = "ItemID";
		this.ruleOrder = 0;
		this.methodId = "MethodID";
		this.methodType = "EQ";
		this.ruleText = "ruleText";
	}

	public ValidateRule(String id, String itemId, int ruleOrder, String methodId, String methodType, String ruleText)
	{
		// TODO example
		this.id = id;
		this.itemId = itemId;
		this.ruleOrder = ruleOrder;
		this.methodId = methodId;
		this.methodType = methodType;
		this.ruleText = ruleText;
	}

}
