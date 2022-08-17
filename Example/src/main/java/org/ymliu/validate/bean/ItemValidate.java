package org.ymliu.validate.bean;

/**
 * 数据项校验
 */
public class ItemValidate
{
	private String id;
	private String itemId;
	private char dataType; // S-String, N-BigDecimal, D-Datetime
	private char logic; // A: AND; O: OR
	private String template;


	public ItemValidate(String id)
	{
		// Initialize all properties from db according "id".
		this.id = id;
	}

	public ItemValidate(String id, String itemId, char dataType, char logic, String template)
	{
		this.id = id;
		this.itemId = itemId;
		this.dataType = dataType;
		this.logic = logic;
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

	public char getLogic()
	{
		return logic;
	}

	public String getTemplate()
	{
		return template;
	}

	public char getDataType()
	{
		return dataType;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	public void setDataType(char dataType)
	{
		this.dataType = dataType;
	}

	public void setLogic(char logic)
	{
		this.logic = logic;
	}

	public void setTemplate(String template)
	{
		this.template = template;
	}
}
