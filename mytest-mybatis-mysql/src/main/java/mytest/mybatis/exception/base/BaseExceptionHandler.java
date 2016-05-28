package mytest.mybatis.exception.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

	@ExceptionHandler({Exception.class})
	public HttpEntity<?> handleException(Exception e) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json;charset=UTF-8");
		HttpStatus status = HttpStatus.OK;
		log.error("exception ", e);

		return new ResponseEntity<Object>("", headers, status);
	}

}
