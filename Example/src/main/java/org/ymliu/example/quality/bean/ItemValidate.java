package org.ymliu.example.quality.bean;

import java.util.List;

/**
 * 数据项校验
 */
public class ItemValidate implements IValid
{
	private final String id;
	private final String itemId;
	private final char dataType; // S-String, N-BigDecimal, D-Datetime
	private final char logic; // A: AND; O: OR
	private final String error;

	private List<ValidateRule> rules; //

	public ItemValidate(String id)
	{
		// Initialize all properties from db according "id".
		this.id = id;

		// TODO read properties from db.
		this.itemId = "abc";
		this.dataType = 'S';
		this.logic = 'A';
		this.error = "警告";
	}

	public ItemValidate(String id, String itemId, char dataType, char logic, String error)
	{
		// TODO example.
		this.id = id;
		this.itemId = itemId;
		this.dataType = dataType;
		this.logic = logic;
		this.error = error;
	}

	@Override
	public boolean valid()
	{
		boolean result = logic == 'A';
		for (ValidateRule rule : rules)
		{
			boolean ret = rule.valid();

			if (logic == 'A')
			{
				result &= ret;
			}
			else
			{
				result |= ret;
			}

			if (!result){
				return false;
			}
		}
		return true;
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

	public String getError()
	{
		return error;
	}

	public char getDataType()
	{
		return dataType;
	}
}