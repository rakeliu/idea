package org.ymliu.commons.uid.snowflake;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SnowFlakeManager
{
	private static final SnowFlakeManager INSTANCE = new SnowFlakeManager();

	public static SnowFlakeManager getInstance()
	{
		return INSTANCE;
	}

	private final Map<Long, Worker> map;

	private SnowFlakeManager()
	{
		map = new HashMap<>();
	}

	public synchronized Worker getWorker(long workerId)
	{
		if (workerId > Const.WorkerMax || workerId < 0)
		{
			return null;
		}

		if (!map.containsKey(workerId))
		{
			map.put(workerId, new Worker(workerId));
		}

		return map.get(workerId);
	}

	protected synchronized void addWorker(Worker worker)
	{
		map.put(worker.getWorkerId(), worker);
	}

	/**
	 * 解开一个SnowFlake的ID，获得原始序号，即number。
	 *
	 * @param uid snowFlake id
	 * @return long
	 */
	public static Bean UnWrap(long uid)
	{
		Date date = new Date();
		long time = ((uid & (-1 << Const.TimeShift)) >> Const.TimeShift) + Const.EPOCH;
		date.setTime(time);

		long workerId = (uid & ~(-1 << Const.TimeShift)) >> Const.WorkerShift;

		long number = uid & ~(-1 << Const.WorkerShift);

		return new Bean(date, workerId, number);
	}
}
