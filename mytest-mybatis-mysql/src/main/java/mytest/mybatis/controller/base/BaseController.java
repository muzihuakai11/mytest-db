package mytest.mybatis.controller.base;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

	protected ResponseEntity<Object> returnJsonObject(Object obj){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<Object>(obj, headers, HttpStatus.OK);
	}

}
