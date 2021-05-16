package top.goodz.future.service.model.enums;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author zhangyajun$
 *  * @createTime： 2021-4-22$ $ 
 *  * @version： 2.1
 *  
 */
public enum CaptchaAuthStatus {

    INIT(1, "初始化"),
    AUTH(2, "已完成");


    private int code;
    private String desc;

    CaptchaAuthStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }


    public String getDesc() {
        return desc;
    }


}
