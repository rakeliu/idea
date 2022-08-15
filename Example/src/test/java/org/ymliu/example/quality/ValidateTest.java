package org.ymliu.example.quality;

import org.ymliu.example.quality.bean.ItemValidate;
import org.ymliu.example.quality.bean.ProductValidate;

public class ValidateTest
{
	public static void main(String args[])
	{
		QualityApi api = new QualityApi();

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
