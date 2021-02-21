package top.goodz.future.exception;

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

    private final String errorId;

    private final String errorMessgee;

    private final int statusCode;

    public CommonException(String errorId, String errorMessgee, int statusCode) {
        this.errorId = errorId;
        this.errorMessgee = errorMessgee;
        this.statusCode = statusCode;
    }

    public String getErrorId() {
        return errorId;
    }


    public String getErrorMessgee() {
        return errorMessgee;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "CommonException{" +
                "DEFAULT_STATUS_CODE=" + DEFAULT_STATUS_CODE +
                ", errorId='" + errorId + '\'' +
                ", errorMessgee='" + errorMessgee + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }

}
