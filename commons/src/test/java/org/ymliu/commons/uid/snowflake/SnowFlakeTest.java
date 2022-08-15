package org.ymliu.commons.uid.snowflake;

import java.text.SimpleDateFormat;

public class SnowFlakeTest
{

	public static void main(String[] args)
	{

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		for (int i = 0; i < 5; i++)
		{
			SnowFlakeTest test = new SnowFlakeTest();
			//test.setWorkerId(i);
			final int j = i;
			new Thread(() -> {
				long id = 0;
				for (int k = 0; k < 10000; k++)
				{
					id = SnowFlakeManager.getInstance().getWorker(j).getId();
					Bean bean = SnowFlakeManager.UnWrap(id);
					System.out.printf("%2d - %3d: %d  %s %4d %6d\n", j, k, id, sdf.format(bean.getDate()), bean.getWorkerId(), bean.getNumber());
				}
			}).start();
			//new Thread(test).start();

			try
			{
				Thread.sleep((long) (Math.random()*5));
			}
			catch (InterruptedException e)
			{
				throw new RuntimeException(e);
			}
		}

	}
}
