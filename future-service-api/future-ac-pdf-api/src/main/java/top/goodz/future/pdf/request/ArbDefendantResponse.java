package top.goodz.future.pdf.request;

import java.io.Serializable;

/**
 * 被申请人信息
 *
 * @author zhuliang
 */
public class ArbDefendantResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idNum;

    /**
     * 地址
     */
    private String adress;

    /**
     * 送达地址
     */
    private String arrivedAdress;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 传真
     */
    private String fax;

    /**
     * 公司法人名称
     */
    private String coLegalPerson;

    /**
     * 法人职务
     */
    private String coJob;
    /**
     * 民族
     */
    private String nation;
    /**
     * 身份证地址
     */
    private String pIdNumAddress;

    /**
     * 法人电话
     */
    private String coPhone;

    /**
     * 公司邮箱
     */
    private String coEmail;

    /**
     * 邮编
     */
    private String coZipcode;

    /**
     * 传真
     */
    private String coFax;

    /**
     * 个人类型：1.个人，2.公司
     */
    private Integer type;
    /**
     * 案件查看密码
     */
    private String arbitralPwd;

    //性别
    private String sex;

    /**
     * 生日
     */
    private String birthday;


    //代理人姓名
    private String agentRealname;
    //代理人职业
    private String agentJob;

    public String getpIdNumAddress() {
        return pIdNumAddress;
    }

    public void setpIdNumAddress(String pIdNumAddress) {
        this.pIdNumAddress = pIdNumAddress;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArbitralPwd() {
        return arbitralPwd;
    }

    public void setArbitralPwd(String arbitralPwd) {
        this.arbitralPwd = arbitralPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCoLegalPerson() {
        return coLegalPerson;
    }

    public void setCoLegalPerson(String coLegalPerson) {
        this.coLegalPerson = coLegalPerson;
    }

    public String getCoJob() {
        return coJob;
    }

    public void setCoJob(String coJob) {
        this.coJob = coJob;
    }

    public String getCoPhone() {
        return coPhone;
    }

    public void setCoPhone(String coPhone) {
        this.coPhone = coPhone;
    }

    public String getCoEmail() {
        return coEmail;
    }

    public void setCoEmail(String coEmail) {
        this.coEmail = coEmail;
    }

    public String getCoZipcode() {
        return coZipcode;
    }

    public void setCoZipcode(String coZipcode) {
        this.coZipcode = coZipcode;
    }

    public String getCoFax() {
        return coFax;
    }

    public void setCoFax(String coFax) {
        this.coFax = coFax;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAgentRealname() {
        return agentRealname;
    }

    public void setAgentRealname(String agentRealname) {
        this.agentRealname = agentRealname;
    }

    public String getAgentJob() {
        return agentJob;
    }

    public void setAgentJob(String agentJob) {
        this.agentJob = agentJob;
    }

    public String getArrivedAdress() {
        return arrivedAdress;
    }

    public void setArrivedAdress(String arrivedAdress) {
        this.arrivedAdress = arrivedAdress;
    }

    @Override
    public String toString() {
        return "ArbDefendantResponse [name=" + name + ", idNum=" + idNum + ", adress=" + adress + ", arrivedAdress="
                + arrivedAdress + ", phone=" + phone + ", email=" + email + ", postalCode=" + postalCode + ", fax="
                + fax + ", coLegalPerson=" + coLegalPerson + ", coJob=" + coJob + ", nation=" + nation
                + ", pIdNumAddress=" + pIdNumAddress + ", coPhone=" + coPhone + ", coEmail=" + coEmail + ", coZipcode="
                + coZipcode + ", coFax=" + coFax + ", type=" + type + ", arbitralPwd=" + arbitralPwd + ", sex=" + sex
                + ", birthday=" + birthday + ", agentRealname=" + agentRealname + ", agentJob=" + agentJob + "]";
    }
}
