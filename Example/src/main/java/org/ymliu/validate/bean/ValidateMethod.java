package org.ymliu.validate.bean;

/**
 * 校验方法Bean。
 */
public class ValidateMethod
{
	private String id;
	private String methodType;
	private String methodDesc;
	private String regex; // TODO ignore now, to be used in the future.

	public ValidateMethod(String id)
	{
		this.id = id;
	}

	public ValidateMethod(String id, String methodType, String methodDesc, String regex)
	{
		this.id = id;
		this.methodType = methodType;
		this.methodDesc = methodDesc;
		this.regex = regex;
	}

	public String getId()
	{
		return id;
	}

	public String getMethodType()
	{
		return methodType;
	}

	public String getMethodDesc()
	{
		return methodDesc;
	}

	public String getRegex()
	{
		return regex;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setMethodType(String methodType)
	{
		this.methodType = methodType;
	}

	public void setMethodDesc(String methodDesc)
	{
		this.methodDesc = methodDesc;
	}

	public void setRegex(String regex)
	{
		this.regex = regex;
	}
}
