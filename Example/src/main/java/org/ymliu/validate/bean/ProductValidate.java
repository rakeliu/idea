package org.ymliu.validate.bean;

/**
 * 产品校验
 */
public class ProductValidate
{
	private String id;
	private String productId;
	private String itemId;
	private int itemOrder;

	public ProductValidate(String id)
	{
		// Get all properties from DB according to id.
		this.id = id;
	}

	public ProductValidate(String id, String productId, String itemId, int itemOrder)
	{
		this.id = id;
		this.productId = productId;
		this.itemId = itemId;
		this.itemOrder = itemOrder;
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

	public void setId(String id)
	{
		this.id = id;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public void setItemOrder(int itemOrder)
	{
		this.itemOrder = itemOrder;
	}
}
