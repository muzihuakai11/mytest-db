package redistest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPubSub;

/**
 * ��Ϣ�����뷢��
 * 
 * @author Administrator
 *
 */
public class RedisPubSubTest extends BaseTest {
	/**
	 * ��������ʱ��
	 */
	@Test
	public void testRedisPubSubPool() {
		RedisPubSub pubSub = new RedisPubSub();
		logger.info("testRedisPubSubPool:��ʼ���̳߳ش���");
		//pubSub.proceed(jedis.getClient(), "news.share", "news.blog");
		pubSub.proceedWithPatterns(jedis.getClient(), "news.*");
	}
}

class RedisPubSub extends JedisPubSub {
	Logger logger = LoggerFactory.getLogger(RedisPubSub.class);

	
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
		logger.info("onMessage:channel[{}], message[{}]",channel,message);
	}

	
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
		logger.info("onPMessage:pattern[{}], channel[{}], message[{}]",pattern,channel,message);
	}

	
	public void onSubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onSubscribe(channel, subscribedChannels);
		logger.info("onSubscribe: channel[{}],subscribedChannels[{}]", channel, subscribedChannels);

	}

	
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onUnsubscribe(channel, subscribedChannels);
		logger.info("onUnsubscribe: channel[{}],subscribedChannels[{}]", channel, subscribedChannels);

	}

	
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPUnsubscribe(pattern, subscribedChannels);
		logger.info("onPUnsubscribe: pattern[{}],subscribedChannels[{}]", pattern, subscribedChannels);
	}

	
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onPSubscribe(pattern, subscribedChannels);
		logger.info("onPSubscribe: pattern[{}],subscribedChannels[{}]", pattern, subscribedChannels);
	}

}