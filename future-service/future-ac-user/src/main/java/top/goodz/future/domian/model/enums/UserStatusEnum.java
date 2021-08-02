package top.goodz.future.domian.model.enums;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public enum UserStatusEnum {


    INIT(0,"初始化"),
    ACTIVE(1,"激活"),
    FREEZE(2,"冻结");


    private int  code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;

    UserStatusEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
