package mytest.mybatis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mytest.mybatis.controller.base.BaseController;
import mytest.mybatis.service.TestService;

@RestController
public class TestController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;

	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public ResponseEntity<?> getVersion() {
		log.info("get version 1.0.0");
		return returnJsonObject("version 1.0.0");
	}

	@RequestMapping(value = "/testAutowired", method = RequestMethod.GET)
	public String testAutowired() {
		String tid = String.valueOf(System.currentTimeMillis());
		log.warn("{} testAutowired begin", tid);
		String result = testService.testAutowired(tid);
		log.warn("{} testAutowired end result={}", tid, result);
		return testService.testAutowired(tid);
	}
	
}
