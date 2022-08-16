package org.ymliu.example.quality.bean;

/**
 * 校验规则，一条。
 *
 * @author LiuYamin
 */
public class ValidateRule
{
	private final String id;
	private final String itemId;
	private final int ruleOrder;
	private final String methodId;
	private final String methodType;
	private final String ruleText;
	private final String warningTemplate;


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
		this.warningTemplate ="未定义错误信息";
	}

	public ValidateRule(String id, String itemId, int ruleOrder, String methodId, String methodType, String ruleText, String warningTemplate)
	{
		// TODO example
		this.id = id;
		this.itemId = itemId;
		this.ruleOrder = ruleOrder;
		this.methodId = methodId;
		this.methodType = methodType;
		this.ruleText = ruleText;
		this.warningTemplate = warningTemplate;
	}

	public String getId()
	{
		return id;
	}

	public String getItemId()
	{
		return itemId;
	}

	public int getRuleOrder()
	{
		return ruleOrder;
	}

	public String getMethodId()
	{
		return methodId;
	}

	public String getMethodType()
	{
		return methodType;
	}

	public String getRuleText()
	{
		return ruleText;
	}

	public String getWarningTemplate()
	{
		return warningTemplate;
	}
}
