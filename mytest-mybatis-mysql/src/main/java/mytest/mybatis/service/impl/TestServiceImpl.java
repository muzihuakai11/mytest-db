package mytest.mybatis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mytest.mybatis.dao.TestDao;
import mytest.mybatis.service.TestService;

public class TestServiceImpl implements TestService {
	private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	private TestDao testDao;

	@Override
	public String testAutowired(String tid) {
		log.warn("{} testServiceImpl", tid);
		return "testServieImpl " + testDao.testAutowired(tid);
	}

}
