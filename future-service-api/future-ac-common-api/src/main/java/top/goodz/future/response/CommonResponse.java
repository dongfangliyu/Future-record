package top.goodz.future.response;

import java.io.Serializable;

public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = 7223184927894637746L;


    // 是否成功
    private boolean success = true;
    // 返回消息
    private String msg = "success";
    // 返回编码
    private String code = "0";
    // 返回内容
    private T data;

    public CommonResponse() {

    }

    public CommonResponse(boolean success, String code, String msg) {
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public CommonResponse(boolean success, String msg, String code, T data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static class Builder<T> {
        private boolean success;
        private String msg;
        private String code;
        private T data;

        public Builder(boolean success, String msg, String code, T data) {
            this.success = success;
            this.msg = msg;
            this.code = code;
            this.data = data;
        }

        public Builder<T> withMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder<T> withCode(String code) {
            this.code = code;
            return this;
        }

        public CommonResponse<T> build() {
            return new CommonResponse<T>(this);
        }
    }


    public static <T> CommonResponse<T> responseOf(String errorCode, String message, T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.code = errorCode;
        response.msg = message;
        response.data = data;
        return response;
    }

    public static <T> CommonResponse<T> responseOf(String errorCode, String message) {
        CommonResponse<T> response = new CommonResponse<>();
        response.code = errorCode;
        response.msg = message;
        return response;
    }

    public static <T> CommonResponse<T> responseOf(T data) {
        CommonResponse<T> response = new CommonResponse<>();
        response.data = data;
        return response;
    }

    public CommonResponse(Builder<T> b) {
        if (b == null) {
            return;
        }
        this.success = b.success;
        this.msg = b.msg;
        this.code = b.code;
        this.data = b.data;
    }

    public static CommonResponse isSuccess() {
        CommonResponse response = new CommonResponse<>();
        response.setCode("0");
        return response;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    @Override
    public String toString() {
        return "CommonResponse [success=" + success + ", msg=" + msg + ", code=" + code + ", data=" + data + "]";
    }
}
