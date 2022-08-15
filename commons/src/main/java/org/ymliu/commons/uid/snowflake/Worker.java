package org.ymliu.commons.uid.snowflake;

import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

public class Worker
{
	private final long workerId; // 记录该节点的id（也需是 docker container id），不超过10 bits。
	private final ReentrantLock lock; // 互斥公平锁，确保并发安全。
	private long timeStamp; // 记录上一次生成id的时间戳。
	private long number; // 当前毫秒已生成的id序号（从0开始累加），1ms 内最多4096个id。

	public Worker(long workerId)
	{
		if (workerId < 0 || workerId > Const.WorkerMax)
		{
			throw new RuntimeException("worker ID excess of quantity.");
		}

		this.workerId = workerId;
		this.lock = new ReentrantLock(true);
		this.timeStamp = 0;
		this.number = 0;
	}

	public long getWorkerId()
	{
		return this.workerId;
	}

	/**
	 * 生成一个新ID
	 *
	 * @return long 雪花算法的 ID。
	 */
	public long getId()
	{
		// 最关键的一点，加锁！！！
		lock.lock();

		long now = Calendar.getInstance().getTimeInMillis(); // 获取当前时间戳，纳秒转为毫秒。
		if (timeStamp == now)
		{
			number++; // 同一毫秒内，序号累加。

			// 累加后是否越界？
			if (number > Const.NumberMax)
			{
				// 如果当前节点在1ms内生成的id数已超过上限，则需等待1ms后再继续生成。
				while (now <= timeStamp)
				{
					now = Calendar.getInstance().getTimeInMillis();
				}
				number = 0;
				timeStamp = 0;
			}
		}
		else
		{
			// 如果当前时间与上一次生成id的时间不一致，则需要重置计数器和时间。
			number = 0;
			timeStamp = now;
		}

		long id = ((now - Const.EPOCH) << Const.TimeShift) | (workerId << Const.WorkerShift) | number;
		lock.unlock();
		return id;
	}
}