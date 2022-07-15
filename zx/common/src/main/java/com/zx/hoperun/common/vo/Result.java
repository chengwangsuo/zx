package com.zx.hoperun.common.vo;

public class Result<T> {

    /**
     * @Fields code : 返回状态码
     */
    private String code;

    /**
     * @Fields msg : 返回消息描述
     */
    private String msg;

    /**
     * @Fields data : 返回实体数据
     */
    private T data;

    /**
     * <p>Title: </p>
     * <p>Description: 异常返回时的返回构造描述</p>
     *
     * @param code
     * @param msg
     */
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
