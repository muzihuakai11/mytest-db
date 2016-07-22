package redistest;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class BaseTest {
	static Logger logger = LoggerFactory.getLogger(BaseTest.class);
	// IP
	final static String HOST_IP = "192.168.20.119";
	// post
	final static int HOST_POST = 6379;
	// password
	final static String PASSWORD = "123456";
	// jedis
	static Jedis jedis = null;

	static ShardedJedis shardedJedis = null;

	static ShardedJedisPool shardedJedisPool = null;

	static JedisPoolConfig config = null;

	static JedisPool jedisPool = null;

	@BeforeClass
	public static void beforeInit() {
		logger.info("beforeInit:��ʼ");
		JedisShardInfo jedisShardInfo = new JedisShardInfo(HOST_IP, HOST_POST);
		jedisShardInfo.setPassword(PASSWORD);
		jedisShardInfo.setSoTimeout(0);
		jedisShardInfo.setConnectionTimeout(0);
		
		JedisShardInfo jedisShardInfo120 = new JedisShardInfo(HOST_IP, HOST_POST);
		jedisShardInfo120.setPassword(PASSWORD);
		jedisShardInfo120.setSoTimeout(0);
		jedisShardInfo120.setConnectionTimeout(0);	
		
		JedisShardInfo jedisShardInfo121 = new JedisShardInfo(HOST_IP, HOST_POST);
		jedisShardInfo121.setPassword(PASSWORD);
		jedisShardInfo121.setSoTimeout(0);
		jedisShardInfo121.setConnectionTimeout(0);	
		
		jedis = new Jedis(jedisShardInfo);

		List<JedisShardInfo> shards = Arrays.asList(jedisShardInfo,jedisShardInfo120,jedisShardInfo121);

		shardedJedis = new ShardedJedis(shards);

		shardedJedisPool = new ShardedJedisPool(new JedisPoolConfig(), shards);

		config = new JedisPoolConfig();

		config.setMinIdle(100);

		config.setMaxIdle(1000);

		config.setMaxTotal(10000);

		config.setTestOnBorrow(true);
		
		jedisPool=new JedisPool(config,HOST_IP, HOST_POST,0,PASSWORD);
	}

	@AfterClass
	public static void afterDestroy() {
		jedis.disconnect();

		shardedJedis.disconnect();

		shardedJedisPool.destroy();
		
		jedisPool.destroy();
		logger.info("afterDestroy������");
	}
}
