package top.goodz.future.enums;

import top.goodz.future.exception.CommonException;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author Yajun.Zhang
 *  * @createTime： 
 *  * @version： 1.0
 *  
 */
public enum ErrorCodeEnum  {


    SUCCESS("0","成功"),
    ERROR("-1","失败"),

    SLIDE_CAPTCHA_ERROR("F2020112","图形验证码异常"),

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
