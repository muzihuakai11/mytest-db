package mytest.mybatis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mytest.mybatis.model.User;
import mytest.mybatis.service.AccountService;

public class AccountServiceImpl implements AccountService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String login(String tid, User user) {
		log.info(tid);
		return "success";
	}

}
