package org.ymliu.example.quality.bean;

import java.util.List;

/**
 * 产品校验
 */
public class ProductValidate implements IValid
{
	private final String id;
	private final String productId;
	private final String itemId;
	private final int itemOrder;
	private List<ItemValidate> list;

	public ProductValidate(String id)
	{
		// Get all properties from DB according to id.
		this.id = id;

		// TODO read db to set properties.
		this.productId = "Product";
		this.itemId = "Item1";
		this.itemOrder = 0;
	}

	public ProductValidate(String id, String productId, String itemId, int itemOrder)
	{
		// TODO example
		this.id = id;
		this.productId = productId;
		this.itemId = itemId;
		this.itemOrder = itemOrder;
	}

	public boolean valid(){
		boolean ret = true;
		for (ItemValidate item : list){
			ret &= item.valid();
		}
		return ret;
	}

	public String getId()
	{
		return id;
	}

	public String getProductId()
	{
		return productId;
	}

	public String getItemId()
	{
		return itemId;
	}

	public int getItemOrder()
	{
		return itemOrder;
	}
}
