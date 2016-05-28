package mytest.mybatis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mytest.mybatis.controller.base.BaseController;

@RestController
public class TestController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public ResponseEntity<?> getVersion() {
		log.info("get version");
		return returnJsonObject("version 1.0.0");
	}
	
}
