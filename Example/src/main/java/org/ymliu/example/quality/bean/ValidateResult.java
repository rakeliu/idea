package org.ymliu.example.quality.bean;

/**
 * Validate Result Bean.
 */
public class ValidateResult
{
	/**
	 * Default Passed validate result, to improve performance.
	 */
	public static final ValidateResult PASSED = new ValidateResult(0,null);
	/**
	 * Default Failure validate result, unknown error occurs.
	 */
	public static final ValidateResult FAILURE_UNKNOWN = new ValidateResult(-255, "未知错误");
	public static final ValidateResult FAILURE_NO_RULES = new ValidateResult(-254, "未定义校验规则");
	private final int code; // 0 - passed, not pass otherwise.
	private final String message; // error message, null for code == 0.

	public ValidateResult(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public int getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		return "ValidateResult{" + "code=" + code + ", message='" + message + '\'' + '}';
	}
}
