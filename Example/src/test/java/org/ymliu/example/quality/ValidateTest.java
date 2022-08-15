package org.ymliu.example.quality;

public class ValidateTest
{
	public static void main(String args[])
	{
		ValidateApi api = new ValidateApi();

		String ret = api.valid("productId", "itemId", "5");

		if (null == ret)
		{
			System.out.println("All validations passed!");
		}
		else
		{
			System.out.printf("ret = %s\n", ret);
		}
	}
}
