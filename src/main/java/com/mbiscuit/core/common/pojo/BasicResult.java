package com.mbiscuit.core.common.pojo;

public class BasicResult<T> {

    private static final String SUCCESS_CODE = "0";
    private static final String DEFAULT_FAIL_CODE = "-1";

    /**
     * 返回状态码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private BasicResult() {
    }

    BasicResult(String code) {
        this.code = code;
    }

    /**
     * 成功
     *
     * @return
     */
    public static BasicResult success() {
        BasicResult basicResult = new BasicResult();
        basicResult.setCode(SUCCESS_CODE);
        return basicResult;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BasicResult<T> success(T data, String message) {
        BasicResult<T> basicResult = new BasicResult<>();
        basicResult.setCode(SUCCESS_CODE);
        basicResult.setData(data);
        basicResult.setMessage(message);
        return basicResult;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BasicResult<T> success(T data) {
        BasicResult<T> basicResult = new BasicResult<>();
        basicResult.setCode(SUCCESS_CODE);
        basicResult.setData(data);
        return basicResult;
    }

    /**
     * 失败
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BasicResult<T> fail(String message) {
        BasicResult<T> basicResult = new BasicResult<>();
        basicResult.setMessage(message);
        basicResult.setCode(DEFAULT_FAIL_CODE);
        return basicResult;
    }

    /**
     * 失败
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BasicResult<T> fail(String code, String message) {
        BasicResult<T> basicResult = new BasicResult<>();
        basicResult.setMessage(message);
        basicResult.setCode(code);
        return basicResult;
    }
}
