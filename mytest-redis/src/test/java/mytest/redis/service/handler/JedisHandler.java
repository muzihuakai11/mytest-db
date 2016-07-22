package mytest.redis.service.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mytest.redis.service.util.ConversionUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisHandler {
	private JedisPool jedisPool = null;
	private Jedis jedis = null;
//	private String password = "foobared";
	
	private String eventId = "720p@0@xxxxS_2059a0b8e15f";
//	private String fileName = eventId + "-3735.ts";

	@Before
	public void init() {
		jedisPool = new JedisPool(new JedisPoolConfig(), "172.29.29.191");
		jedis = jedisPool.getResource();
//		jedis.auth(password);
	}

	@After
	public void destroy() {
		if (jedis != null) {
			jedis.close();
		}
		jedisPool.destroy();
	}

	/**
	 * 查询出所有的匹配key的对所有的文件名列表
	 */
	@Test
	public void getFileNameSet() {
		Map<String, String> fileMap = jedis.hgetAll(eventId);
		for (String fileName : fileMap.keySet()) {
			System.out.println(fileName);
			System.out.println(fileMap.get(fileName));
		}
	}

	/**
	 * 写入文件和
	 * 对应的key map中
	 */
	@Test
	public void setFile() {
		Map<byte[], byte[]> map = new HashMap<>();
		for (int i = 0; i < 5; i++) {
			File file = new File("F:\\GitProject\\mytest-db\\mytest-redis\\src\\test\\resources\\tsfile\\"
					+ "000000-" + (i+1) + ".ts");
			//key
			map.put(("key" + i).getBytes(), String.format("%s00000%s-3.%s-%s", "000000", i, i, i).getBytes());
			//file
//			jedis.hmset(eventId.getBytes(), map);
			jedis.set(String.format("%s00000%s", "000000", i).getBytes(), ConversionUtil.serialize(file));
		}
		jedis.hmset(eventId.getBytes(), map);
	}

//	@Test
//	public void setFileIndex() {
//		for(int i = 1; i <= 5; i++) {
//			jedis.hset(eventId, "key" + i, "");
//		}
//	}

	@Test
	public void delFileIndex() {
		jedis.del(eventId);
	}

	@Test
	public void delOneFileIndex() {
		jedis.hdel(eventId, "key0", "key1", "key2", "key3", "key4");
	}

	@Test
	public void getFileIndex() {
		Map<String, String> map = jedis.hgetAll(eventId);
		List<String> fileNameList = new ArrayList<String>(map.values());
		System.out.println(fileNameList);
		for (String fileName : fileNameList) {
			System.out.println(fileName);
		}
	}

	/**
	 * 获取文件
	 */
	@Test
	public void getFile() {
		byte[] fileBytes = jedis.get(String.format("%s00000%s", "000000", 2).getBytes());
		System.out.println(fileBytes);
		if (null != fileBytes) {
			ConversionUtil.stringToFile(fileBytes);
		}
//		File file = (File) ConversionUtil.deserialize(fileStr.getBytes());
//		System.out.println(file);
	}

	/**
	 * 获取文件列表
	 */
	@Test
	public void getFileSet() {
		Collection<byte[]> fileNames = jedis.hgetAll(eventId.getBytes()).values();
		for (byte[] bytes : fileNames) {
			System.out.println(new String(bytes));
		}
	}

	@Test
	public void deleteFileIndex() {
		jedis.del(eventId.getBytes());
//		jedis.hdel(eventId, fileName);
	}

	@Test
	public void delTSFileAll() {
		for (int i = 0; i < 5; i++) {
			jedis.del(String.format("%s-%s", eventId, i).getBytes());
		}
	}

	@Test
	public void getSessionAll() {
		Map<byte[], byte[]> session = jedis.hgetAll("session".getBytes());
		for (byte[] value : session.values()) {
			System.out.println(new String(value));
		}
	}

	@Test
	public void delSessionAll() {
		Set<String> sessionSet = jedis.hgetAll("session").keySet();
		for (String sessionName : sessionSet) {
			jedis.del(sessionName);
		}
		jedis.del("session");
		
	}

	@Test
	public void delSessionDeviceInfo() {
		jedis.del(eventId);
	}

	@Test
	public void sysTest() {
		System.out.println(System.currentTimeMillis());
	}

}
