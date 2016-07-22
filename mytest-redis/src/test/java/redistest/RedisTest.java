package redistest;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runners.MethodSorters;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;

/**
 * ����
 * 
 * @author Administrator
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTest extends BaseTest {

	/**
	 * set
	 */
	@Test
	public void redisSetNormal() {
		logger.info("redisSetNormal:��ʼ");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			logger.info("redisSetNormal {} set {}", i, jedis.set("key_" + i, "value_" + i));
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * Transactions
	 */
	@Test
	public void redisTransactions() {
		logger.info("redisTransactions:��ʼ");
		long start = System.currentTimeMillis();
		Transaction tx = jedis.multi();
		for (int i = 0; i < 100; i++) {
			logger.info("redisTransactions {} set {}", i, tx.set("key_" + i, "value_" + i));
			// if (i == 50)
			// tx.discard();
		}
		List<Object> results = tx.exec();
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * Pipelining
	 */
	@Test
	public void redisPipelining() {
		logger.info("redisPipelining:��ʼ");
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		for (int i = 0; i < 100; i++) {
			logger.info("redisPipelining {} set {}", i, pipeline.set("key_" + i, "value_" + i));
		}
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * PipeliningTrans
	 */
	@Test
	public void redisPipelinTrans() {
		logger.info("redisPipelinTrans:��ʼ");
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		for (int i = 0; i < 100; i++) {
			logger.info("redisPipelinTrans {} set {}", i, pipeline.set("key_" + i, "value_" + i));
		}
		pipeline.exec();
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * ShardNormal
	 */
	@Test
	public void redisShardNormal() {
		logger.info("redisShardNormal:��ʼ");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			logger.info("redisShardNormal {} set {}", i, shardedJedis.set("key_" + i, "value_" + i));
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * ShardNormal
	 */
	@Test
	public void redisShardPipelined() {
		logger.info("redisShardPipelined:��ʼ");
		long start = System.currentTimeMillis();
		ShardedJedisPipeline pipeline = shardedJedis.pipelined();
		for (int i = 0; i < 100; i++) {
			logger.info("redisShardPipelined {} set {}", i, pipeline.set("key_" + i, "value_" + i));
		}
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * ShardNormal
	 */
	@Test
	public void redisShardSimplePool() {
		logger.info("redisShardSimplePool:��ʼ");
		long start = System.currentTimeMillis();
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		for (int i = 0; i < 100; i++) {
			logger.info("redisShardSimplePool {} set {}", i, shardedJedis.set("key_" + i, "value_" + i));
		}
		shardedJedisPool.returnResource(shardedJedis);
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * redisShardSimplePush
	 */
	@Test
	public void redisShardSimpleLPush() {
		logger.info("redisShardSimplePush:��ʼ");
		long start = System.currentTimeMillis();
		// ShardedJedis shardedJedisPool = pool.getResource();
		for (int i = 0; i < 100; i++) {
			logger.info("redisShardSimplePush {} set {}", i, shardedJedis.lpush("key_push_list", "value_" + i));
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * redisShardSimplePush
	 */
	@Test
	public void redisShardSimpleRPush() {
		logger.info("redisShardSimpleRPush:��ʼ");
		long start = System.currentTimeMillis();
		// ShardedJedis shardedJedisPool = pool.getResource();
		for (int i = 0; i < 100; i++) {
			logger.info("redisShardSimpleRPush {} set {}", i, shardedJedis.rpush("key_push_list", "value_" + i));
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * redisShardSimpleLRange
	 */
	@Test
	public void redisShardSimpleLRange() {
		logger.info("redisShardSimpleLRange:��ʼ");
		long start = System.currentTimeMillis();
		// ShardedJedis shardedJedisPool = pool.getResource();
		for (int i = 0; i < 100; i++) {
			logger.info("redisShardSimpleLRange {} get {}", i, shardedJedis.lrange("key_push_list", i, i));
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	@Test
	public void test1Normal() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			String result = jedis.set("n" + i, "n" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("Simple SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test2Trans() {
		long start = System.currentTimeMillis();
		Transaction tx = jedis.multi();
		for (int i = 0; i < 100; i++) {
			tx.set("t" + i, "t" + i);
		}
		// System.out.println(tx.get("t1000").get());

		List<Object> results = tx.exec();
		long end = System.currentTimeMillis();
		System.out.println("Transaction SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test3Pipelined() {
		Pipeline pipeline = jedis.pipelined();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			pipeline.set("p" + i, "p" + i);
		}
		// System.out.println(pipeline.get("p1000").get());
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("Pipelined SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test4combPipelineTrans() {
		long start = System.currentTimeMillis();
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();
		for (int i = 0; i < 100; i++) {
			pipeline.set("" + i, "" + i);
		}
		pipeline.exec();
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("Pipelined transaction: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test5shardNormal() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			String result = shardedJedis.set("sn" + i, "n" + i);
		}
		long end = System.currentTimeMillis();
		System.out.println("Simple@Sharing SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test6shardpipelined() {
		ShardedJedisPipeline pipeline = shardedJedis.pipelined();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			pipeline.set("sp" + i, "p" + i);
		}
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		System.out.println("Pipelined@Sharing SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test7shardSimplePool() {
		ShardedJedis one = shardedJedisPool.getResource();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			String result = one.set("spn" + i, "n" + i);
		}
		long end = System.currentTimeMillis();
		shardedJedisPool.returnResource(one);
		System.out.println("Simple@Pool SET: " + ((end - start) / 1000.0) + " seconds");
	}

	@Test
	public void test8shardPipelinedPool() {
		ShardedJedis one = shardedJedisPool.getResource();

		ShardedJedisPipeline pipeline = one.pipelined();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			pipeline.set("sppn" + i, "n" + i);
		}
		List<Object> results = pipeline.syncAndReturnAll();
		long end = System.currentTimeMillis();
		shardedJedisPool.returnResource(one);
		System.out.println("Pipelined@Pool SET: " + ((end - start) / 1000.0) + " seconds");
	}
	
	
	
	/**
	 * set
	 */
	@Test
	public void redisPubList() {
		logger.info("redisPubList:��ʼ");
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			jedis.publish("news.share", "hello world share" + i);
			jedis.publish("news.blog", "hello world blog" + i);
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}

	/**
	 * set
	 */
	@Test
	public void redisPubListPool() {
		logger.info("redisPubListPool:��ʼ");
		long start = System.currentTimeMillis();
		Jedis jedis = null;
		for (int i = 0; i < 100; i++) {
			try {
				jedis = jedisPool.getResource();
				jedis.publish("news.share", "share" + i);
				jedis.publish("news.blog", "blog" + i);
				jedis.publish("news.demo", "demo" + i);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("ERROR:", e);
			} finally {
				if (jedis != null)
					jedisPool.returnResource(jedis);
			}
		}
		long end = System.currentTimeMillis();
		logger.info("����ʱ��SET:{} ��", (end - start) / 1000.0);
	}
}
