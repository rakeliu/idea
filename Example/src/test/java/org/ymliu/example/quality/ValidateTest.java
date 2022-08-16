package org.ymliu.example.quality;

import org.ymliu.example.quality.bean.ValidateResult;

public class ValidateTest
{
	public static void main(String args[])
	{
		ValidateApi api = new ValidateApi();

		ValidateResult result = api.valid("productId", "itemId", "6");

		System.out.printf("code = %d, message = %s", result.getCode(), result.getMessage());
	}
}
