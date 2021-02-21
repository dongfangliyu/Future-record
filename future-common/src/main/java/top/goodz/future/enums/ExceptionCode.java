package top.goodz.future.enums;

/**
 *  * @Description:  全局异常枚举
 *  * @throws 
 *  * @author zhangyajun
 *  * @createTime：
 *  * @version： 1.0
 *  
 */


import top.goodz.future.exception.ServiceException;

/***
 * 异常码 共 8 位
 *
 * 0 :  F 前台错误
 * 0 : 1 系统错误  2 业务错误
 * 00 : 场景：01 注册  02 登录  03 级粗服务异常
 * 00 : 功能
 * 00 : 异常分支
 *
 *
 *
 */
public enum ExceptionCode {


    IO_IMAGE_ERROR("F2030101", "生成滑动图片IO异常"),

    DEF_ERROR("F1010101", "初始化错误"),

    PARAM_ERROR("F1010102", "参数缺失");

    private String code;

    private String msg;

    ExceptionCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void throwException() {
        throw new ServiceException(getCode(), getMsg());
    }


}
