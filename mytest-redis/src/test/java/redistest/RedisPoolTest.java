package redistest;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisPoolTest extends BaseTest {
	/**
	 * redisIncr
	 */
	public void redisIncr(Jedis jedis) {
		// logger.info("redisIncr {}", jedis.incr("incrTest"));
		jedis.incr("incrTest");
	}

	/**
	 * 
	 * ���ʲ���(���ó�)
	 * 
	 * @param count
	 * 
	 */
	public void redisNoPool(int count) {
		logger.info("redisSetNormal:��ʼ");
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			try {
				jedis.connect();
				redisIncr(jedis);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("ERROR:", e);
			} 
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��:{} ��", (end - start) / 1000.0);
	}

	/**
	 * 
	 * ���ʲ���(�ó�)
	 * 
	 * @param count
	 * 
	 */
	public void redisWithPool(int count) {
		logger.info("redisWithPool:��ʼ");
		long start = System.currentTimeMillis();
		Jedis jedis = null;
		for (int i = 0; i < count; i++) {
			try {
				jedis = jedisPool.getResource();
				redisIncr(jedis);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("ERROR:", e);
			} finally {
				if (jedis != null)
					jedisPool.returnResource(jedis);
			}
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��:{} ��", (end - start) / 1000.0);
	}

	/**
	 * 
	 * ��������
	 * 
	 * @param paiallel������
	 * 
	 * @param countÿ������ѭ������
	 * 
	 * @param isPool�Ƿ����̳߳�
	 * 
	 */
	public void redisPaiallelIsPool(int paiallel, int count, boolean isPool) {
		logger.info("redisPaiallelIsPool:��ʼ");
		long start = System.currentTimeMillis();
		Thread[] ts = new Thread[paiallel];
		// �øö���֤���̶߳�������̲߳��˳�
		CountDownLatch cd = new CountDownLatch(paiallel);
		for (int i = 0; i < paiallel; i++) {
			ts[i] = new Thread(new WorkerNoPool(cd, count, isPool));
			ts[i].start();
		}
		try {
			cd.await();// �ȴ��������߳����
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��:{} ��", (end - start) / 1000.0);
	}

	class WorkerNoPool implements Runnable {
		private CountDownLatch cd;
		private int count;
		private boolean isPool;

		public WorkerNoPool(CountDownLatch cd, int count, boolean isPool) {
			this.cd = cd;
			this.count = count;
			this.isPool = isPool;
		}

		public void run() {
			try {
				if (isPool) {
					redisWithPool(count);
				} else {
					redisNoPool(count);
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("ERROR:", e);
			} finally {
				cd.countDown();
			}
		}
	}

	/**
	 * ��������ʱ��
	 */
	@Test
	public void testRedisPool() {
		logger.info("redisTestPool:��ʼ���̳߳ش���");
		redisPaiallelIsPool(100, 1000, false);
		logger.info("redisTestPool:��ʼ���̳߳ش���");
		redisPaiallelIsPool(100, 1000, true);
	}
}
