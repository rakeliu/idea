package org.ymliu.example.quality.bean;

/**
 * 校验方法Bean。
 */
public class ValidateMethod
{
	private final String id;
	private final String methodType;
	private final String methodDesc;
	private final String regex; // TODO ignore now, to be used in the future.

	public ValidateMethod(String id)
	{
		// Get properties from DB according id.
		this.id = id;

		// TODO read DB to set properties.
		this.methodType = "EQ";
		this.methodDesc = "等于";
		this.regex = "*";
	}

	public ValidateMethod(String id, String methodType, String methodDesc){
		// TODO Example
		this.id=id;
		this.methodType=methodType;
		this.methodDesc=methodDesc;
		this.regex="*";
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
}
