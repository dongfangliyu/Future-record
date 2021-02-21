package top.goodz.future.enums;

import top.goodz.future.exception.CommonException;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author zyj
 *  * @createTime： 
 *  * @version： 1.0
 *  
 */
public enum ErrorCodeEnum  {


    SUCCESS("0","成功"),
    ERROR("-1","失败"),


    EMAIL_SEND_PASSAGEWAY_CLOSED("107001", "邮箱发送通道关闭");


    private String code;

    private String message;

    ErrorCodeEnum(String code,String message){
        this.code = code;
        this.message = message;
    }
    public  String getCode(){return code;}
    public String getMessage(){return message;}

    public void throwEcxeption() throws CommonException {

       new RuntimeException();
    }

}
