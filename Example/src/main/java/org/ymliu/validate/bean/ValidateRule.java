package org.ymliu.validate.bean;

/**
 * 校验规则，一条。
 *
 * @author LiuYamin
 */
public class ValidateRule
{
	private String id;
	private String itemId;
	private int ruleOrder;
	private String methodId;
	private String methodType;
	private String ruleText;
	private String template;


	public ValidateRule(String id)
	{
		this.id = id;
	}

	public ValidateRule(String id, String itemId, int ruleOrder, String methodId, String methodType, String ruleText, String template)
	{
		this.id = id;
		this.itemId = itemId;
		this.ruleOrder = ruleOrder;
		this.methodId = methodId;
		this.methodType = methodType;
		this.ruleText = ruleText;
		this.template = template;
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

	public String getTemplate()
	{
		return template;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public void setRuleOrder(int ruleOrder)
	{
		this.ruleOrder = ruleOrder;
	}

	public void setMethodId(String methodId)
	{
		this.methodId = methodId;
	}

	public void setMethodType(String methodType)
	{
		this.methodType = methodType;
	}

	public void setRuleText(String ruleText)
	{
		this.ruleText = ruleText;
	}

	public void setTemplate(String template)
	{
		this.template = template;
	}
}
