package org.ymliu.example.quality;

import java.math.BigDecimal;

/**
 * 校验规则，一条。
 *
 * @author LiuYamin
 */
public class ValidateRule
{
	private long id;
	private String name;
	private String dataType;
	private int dataScale;
	private ValidateType type;    // 校验类型，用枚举
	private String value1;
	private String value2;
	private int critical;

	public ValidateRule(long id){
		// Get all properties from db according to id,
		// and convert type to enumerate type;
	}

	/**
	 * 校验
	 *
	 * @param srcData 需校验的原数据。
	 * @return critical
	 */
	public int validate(Object srcData)
	{
		Object innerData = null;
		switch (dataType)
		{
			case "N":
				// 数据类型，用BigDecimal转换。
				BigDecimal dec = new BigDecimal(srcData.toString());
				if (dataScale > 0)
				{
					dec.setScale(dataScale);
				}
				innerData = dec;
				break;
			case "S":
				// 字符串
				String s = srcData.toString();
				innerData = s;
				break;
			default:
				return 0;
		}

		switch (type){
			case Eq:
				return doValidEq(innerData)?0:critical;
		}
	}

	private boolean doValidEq(Object obj){
		if (obj.getClass()==String.class){
			return value1.equals(obj);
		}
		if (obj.getClass()==BigDecimal.class){
			BigDecimal srcDec = (BigDecimal) obj;
			BigDecimal destDec = new BigDecimal(value1);
			if (dataScale>=0){
				destDec.setScale(dataScale);
			}
			return destDec.compareTo(srcDec)==0;
		}
	}
}
