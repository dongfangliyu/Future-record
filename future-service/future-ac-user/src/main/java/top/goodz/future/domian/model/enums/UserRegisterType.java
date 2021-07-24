package top.goodz.future.domian.model.enums;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public enum UserRegisterType {


    EMAIL(1,"邮箱"),
    PHONE(2,"手机");


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

    UserRegisterType(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

}
