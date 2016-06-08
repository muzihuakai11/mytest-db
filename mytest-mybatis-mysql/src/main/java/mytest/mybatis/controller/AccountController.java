package mytest.mybatis.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mytest.mybatis.controller.base.BaseController;

@RestController(value = "/account")
public class AccountController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<?> login(Map<String, String> paramMap) throws Exception {
		String tid = String.valueOf(System.currentTimeMillis());
		log.info("{} login", tid);
		String response = null;
		return returnJsonObject(response);
	}

}
