package org.ymliu.commons.uid.snowflake;

import java.util.Calendar;

public final class Const
{
	/**
	 * 每个集群下的每个节点，1ms内可生成id序号的二进制位数。
	 */
	static final int NumberBits = 12;

	/**
	 * 每台机器（节点）编号的二进制位数，最大可有2^10=1024个节点，每个节点1ms内可生成2^NumberBits=4096个ID。
	 */
	static final int WorkerBits = 10;

	/* 节点和序号最大值，这里算法很巧妙：
	   -1的二进制补码为0xFF...FF；
	   执行左移n位后，高位全1，低n位全0；
	   再取反，就得到低n位全1，即2^n-1。*/

	/**
	 * 最大节点数， 0 - 1024。
	 */
	static final long WorkerMax = ~(-1 << WorkerBits);

	/**
	 * 每毫秒最大生成数，0 - 4096。
	 */
	static final long NumberMax = ~(-1 << NumberBits);

	/**
	 * 时间戳向左偏移量。
	 */
	static final int TimeShift = WorkerBits + NumberBits;

	/**
	 * 节点ID向左偏移量。
	 */
	static final int WorkerShift = NumberBits;

	/**
	 * 41bits 作为时间戳数值，大约68年用完。
	 * 假设从2000-1-1开始开发系统，如果不减去2000-1-1的时间戳，那么白白浪费20多年的时间戳。
	 * 这个参数一旦定义且开始生成ID后千万不能修改了，不然可能会生成重复ID。
	 * 此处从 2022-1-1 00:00:00.000000 UTC 开始， **禁止修改**
	 */
	static final long EPOCH;

	static
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(2022, Calendar.JANUARY, 0);
		EPOCH = calendar.getTimeInMillis();
	}

	/**
	 * 常量对象，不可实例化。
	 */
	private Const() {}
}
