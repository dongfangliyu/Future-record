package top.goodz.future.infra.feigin.model;

import lombok.Data;

/**
 * 邮箱请求参数
 *
 * @author zhangyajun
 */
@Data
public class FutureEmailRequest {


    private static final long serialVersionUID = 1L;

    /**
     * 发送email请求参数
     */
    private FutureEmailData data;

    /**
     * 接口名
     */
    private String channelsName;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 邮件ID, 用来多线程发送邮件时作为区分标识
     */
    private String emailId;

}
