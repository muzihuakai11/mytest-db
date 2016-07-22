package mytest.redis.service.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber extends JedisPubSub {
	private final Logger log = LoggerFactory.getLogger(JedisSubscriber.class);
	
	@Override
	public void onMessage(String channel, String message) {
		
	}

	
}
