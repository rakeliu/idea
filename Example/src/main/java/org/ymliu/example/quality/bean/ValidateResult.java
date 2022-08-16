package org.ymliu.example.quality.bean;

/**
 * Validate Result Bean.
 */
public class ValidateResult
{
	/**
	 * Default Passed validate result, to improve performance.
	 */
	public static final ValidateResult PASSED = new ValidateResult(0, "校验通过");
	/**
	 * Default Failure validate result, unknown error occurs.
	 */
	public static final ValidateResult FAILURE_UNKNOWN = new ValidateResult(-255, "未知错误。");
	public static final ValidateResult FAILURE_RUNTIME = new ValidateResult(-254, "未知错误，代码未覆盖。");
	public static final ValidateResult FAILURE_NO_RULES = new ValidateResult(-253, "未定义校验规则。");
	public static final ValidateResult FAILURE_FORMAT = new ValidateResult(-252, "数据格式错误。");
	private final int code; // 0 - passed, -1 - valid not pass, other - system error.
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
