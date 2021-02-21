package top.goodz.future.exception;

import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.enums.ExceptionCode;

/**
 * @description：业务全局异常处理类
 * @throws 
 * @createTime：2020/5/20
  * @return
 */
public class ServiceException extends RuntimeException {



    private  String errorCode;

    private  String errorMessage;


    public ServiceException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getMessage();
    }

    public ServiceException(ExceptionCode errorCodeEnum) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMessage = errorCodeEnum.getMsg();
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

  @Override
    public String toString(){
        return "";
  }
}
