package org.ymliu.commons.uid.snowflake;

import java.util.Date;

public final class Bean
{
	private final Date date ;
	private final long workerId;
	private final long number;

	public Bean(Date date, long workerId, long number)
	{
		this.date = date;
		this.workerId = workerId;
		this.number = number;
	}

	public Date getDate()
	{
		return date;
	}

	public long getWorkerId()
	{
		return workerId;
	}

	public long getNumber()
	{
		return number;
	}
}
