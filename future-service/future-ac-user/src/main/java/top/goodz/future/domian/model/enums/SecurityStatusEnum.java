package top.goodz.future.domian.model.enums;

/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */
public enum SecurityStatusEnum {


    INIT(1,"待验证"),
    SUCCESS(2,"成功"),
    FAILED(3,"失败"),;


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

    SecurityStatusEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
