package top.goodz.future.exception;

import top.goodz.future.enums.ErrorCodeEnum;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author zhangyajun
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public class CommonException extends RuntimeException {


    private final int DEFAULT_STATUS_CODE = 500;

    private String errorId;

    private String errorMessgee;

    private Object data;

    public CommonException(String errorId, String errorMessgee, Object object) {
        this.errorId = errorId;
        this.errorMessgee = errorMessgee;
        this.data = object;
    }

    public CommonException(ErrorCodeEnum errorCodeEnum) {
        this.errorId = errorCodeEnum.getCode();
        this.errorMessgee = errorCodeEnum.getMessage();

    }


    public String getErrorId() {
        return errorId;
    }


    public String getErrorMessgee() {
        return errorMessgee;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonException{" +
                "DEFAULT_STATUS_CODE=" + DEFAULT_STATUS_CODE +
                ", errorId='" + errorId + '\'' +
                ", errorMessgee='" + errorMessgee + '\'' +
                ", statusCode=" + data +
                '}';
    }

}
