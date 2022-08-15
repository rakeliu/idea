package org.ymliu.example.quality;

public enum ValidateType
{
	Eq,  // 等于
	Gt, // 大于
	Ge, // 大于等于
	Lt, // 小于
	Le, // 小于等于
	Range1, // 范围，存在于开区间
	Range2, // 范围，存在于闭区间
	Range3, // 范围，存在于半开半闭区间
	Range4, // 范围，存在于半闭半开区间
	In, // 范围，枚举类型
	Exist, // 存在，检测非零、非空
	Regex; // 正则表达式
}
