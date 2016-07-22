package mytest.redis.service.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPublisher {
	private final static String redisHost = "172.29.29.191";
	private final Jedis jedis;
	private final String channel;
	
	public JedisPublisher(Jedis jedis, String channel) {
		this.jedis = jedis;
		this.channel = channel;
	}

	public void start() {
		for (int i = 0 ; i < 100; i++) {
			jedis.publish(channel, "pub " + i);
		}
	}

	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(config, redisHost, 6379, 0);
		Jedis jedis = jedisPool.getResource();
		jedis.publish("FLOWSTOP", "720p@0@xxxxS_camera1 000001000000");
		jedis.close();
		jedisPool.destroy();
		System.out.println("ssss");
	}
}
