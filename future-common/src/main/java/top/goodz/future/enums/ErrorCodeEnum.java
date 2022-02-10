package top.goodz.future.enums;

import top.goodz.future.exception.CommonException;
import top.goodz.future.exception.ServiceException;

import javax.sql.rowset.serial.SerialException;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author Yajun.Zhang
 *  * @createTime： 
 *  * @version： 1.0
 *  
 */
public enum ErrorCodeEnum {

    /**
     * F01010001
     * F 表示用户端可见
     * 第一位 注册:01 登陆02 通用03
     * 第二： 01 表示业务异常  02 表示 系统异常
     * 第三：0001 异常编码
     */

    SUCCESS("0", "成功"),
    ERROR("-1", "失败"),

    SLIDE_CAPTCHA_ERROR("F03020112", "图形验证码异常"),

    EMAIL_SEND_PASSAGEWAY_CLOSED("F03010001", "邮箱发送通道关闭"),

    NOT_REPEAT_AUTHENTICATION("F03010002", "请勿重复认证"),
    EMAIL_CODE_EXPIRE("F03010003", "邮箱验证码已过期"),
    CODE_ERROR("F03010004", "验证码错误"),
    SECURITY_FAILED("F03010005", "请重新发起验证"),
    SECURITY_MFA_FAILED("F03010006", "MFA安全认证缺失"),

    EMAIL_EXIST("F01010001", "该邮箱已经存在"),
    PASSWORD_FORMAT_ERROR("F01010002", "密码格式错误"),
    PASSWORD_ERROR("F01010003", "密码错误"),
    PASSWORD_OR_ACCOUNT_ERROR("F01010004", "账户或密码错误"),

    ;


    private String code;

    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void throwEcxeption() {

        throw new ServiceException(getCode(),getMessage());

    }

}
