package mytest.mybatis.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mytest.mybatis.dao.TestDao;

public class TestDaoImpl implements TestDao {
	private static final Logger logger = LoggerFactory.getLogger(TestDaoImpl.class);

	@Override
	public String testAutowired(String tid) {
		logger.warn("{} testAutowired", tid);
		return "testdao 1.0.0";
	}

}
