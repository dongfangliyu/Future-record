package top.goodz.future.response;

import lombok.Data;

/**
 * @author : Yajun.Zhang
 * @date : 2020-01-15 15:15
 * @Description :
 */
@Data
public class EmailResponse extends CommonResponse {

    /**
     * 邮件ID, 用来多线程发送邮件时作为区分标识
     */
    private String emailId;

    /**
     * 通讯层次是否成功
     */
    private int logsCommStatus;

    /**
     * 业务状态是否成功,1-成功 2-失败
     */
    private int logsBusinessStatus;

    /**
     * 请求内容
     */
    private String requestContent;

    /**
     * 响应内容
     */
    private String responseContext;

    /**
     * 请求时间
     */
    private String requestTime;

    /**
     * 响应时间
     */
    private String responseTime;

    /**
     * 案件id
     */
    private String arbitralInfoId;
}
