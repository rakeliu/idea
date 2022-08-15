package org.ymliu.example.quality;

import org.ymliu.example.quality.bean.ItemValidate;
import org.ymliu.example.quality.bean.ProductValidate;

public class ValidateTest
{
	public static void main(String args[]){
		ProductValidate p1 = new ProductValidate("product", "ProductID1", "ItemID11",0);

		ItemValidate i11 = new ItemValidate("item", "ItemID11", 'S', 'A', "error");
	}
}
