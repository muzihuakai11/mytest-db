package mytest.mybatis.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 5903923373236512229L;
	/**
	 * tid每个请求标识
	 */
	String tid;
	/**
	 * 错误码
	 */
	String errorCode;

	public ServiceException(String tid, String errorCode) {
		this.tid = tid;
		this.errorCode = errorCode;
	}

	public ServiceException(String tid, String errorCode, String errorMsg) {
		super(errorMsg);
		this.tid = tid;
		this.errorCode = errorCode;
	}

	public String getTid() {
		return tid;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
