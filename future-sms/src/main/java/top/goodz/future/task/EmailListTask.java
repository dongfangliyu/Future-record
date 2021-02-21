package top.goodz.future.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.request.email.EmailData;
import top.goodz.future.request.email.EmailFileData;
import top.goodz.future.request.email.EmailRequest;
import top.goodz.future.response.EmailResponse;
import top.goodz.future.utils.ChkUtil;
import top.goodz.future.utils.DateUtil;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

/**
 * @author : zhangyajun
 * @date : 2020-05-15 11:23
 * @Description :
 */
public class EmailListTask implements Callable<EmailResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailListTask.class);


    /**
     * 邮箱请求参数
     */
    private EmailRequest emailRequest;

    /**
     * 所属仲裁委
     */
    private String olapName;

    /**
     * 发送邮件工具类
     */
    private JavaMailSender sender;

    /**
     * 配置发送名称
     */
    private String from;


    public EmailListTask(EmailRequest emailRequest, String olapName, JavaMailSender sender, String from) {
        this.emailRequest = emailRequest;
        this.olapName = olapName;
        this.sender = sender;
        this.from = from;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public EmailResponse call() throws Exception {
        EmailResponse commonResponse = null;
        if (ChkUtil.isNotEmpty(emailRequest) && ChkUtil.isNotEmpty(olapName)) {
            commonResponse = sendEmails(emailRequest, olapName, sender, from);
        }
        return commonResponse;
    }


    public EmailResponse sendEmails(EmailRequest email, String olapName, JavaMailSender sender, String from) {
        long startTime = System.currentTimeMillis();
        EmailResponse response = new EmailResponse();
        response.setSuccess(false);
        response.setCode(ErrorCodeEnum.ERROR.getCode());
        response.setMsg(ErrorCodeEnum.ERROR.getMessage());
        response.setEmailId(email.getEmailId());

        // 通讯层次是否成功,1-成功 2-失败
        int logsCommStatus = FutureConstant.COMMONT_STATUS_SUCCESS;
        // 业务状态是否成功,1-成功 2-失败
        int logsBusinessStatus = FutureConstant.COMMONT_STATUS_NO_SUCCESS;
        // 请求内容
        String requestContent = email.toString();
        // 响应内容
        String responseContext = "";
        // 请求时间
        String requestTime = DateUtil.getCurrentTime();
        // 响应时间
        String responseTime = "";
        // 发送通道 打开
        if (ChkUtil.isNotEmpty(email) && ChkUtil.isNotEmpty(email.getData())) {
            try {
                response.setArbitralInfoId(email.getData().getArbitralInfoId());
                EmailData data = email.getData();

                javax.mail.internet.MimeMessage mimeMessage = sender.createMimeMessage();
                MimeMessageHelper messageHelper = null;
                // 不可为空
                if (ChkUtil.isNotEmpty(data.getToMail()) || ChkUtil.isNotEmpty(data.getContent())) {
                    // 收件人的邮箱
                    String toMail = data.getToMail();
                    // 邮箱内容
                    String content = data.getContent();
                    messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    // 自定义发件人昵称
                    String nick = "";
                    try {
                        nick = MimeUtility.encodeText(olapName);
                    } catch (UnsupportedEncodingException e) {
                        e.getMessage();
                    }
                    messageHelper.setFrom(new InternetAddress(nick + " <" + from + ">"));
                    messageHelper.setTo(toMail);
                    messageHelper.setSubject(data.getSubject());
                    messageHelper.setText(content, true);
                    // 发送文件
                    if (ChkUtil.isNotEmpty(data.getFiles())) {
                        // 多附件
                        for (EmailFileData file : data.getFiles()) {
                            String fileNameNew = MimeUtility.encodeText(file.getFileName());
                            MimeBodyPart mimeBodyPart = new MimeBodyPart();
                            DataSource dataSource = new FileDataSource(file.getFilPath());
                            mimeBodyPart.setDataHandler(new DataHandler(dataSource));
                            mimeBodyPart.setFileName(fileNameNew);
                            messageHelper.addAttachment(fileNameNew, dataSource);
                        }
                    }
                    //有静态图片要发送
                    if (ChkUtil.isNotEmpty(data.getRscId()) && ChkUtil.isNotEmpty(data.getRscPath())) {
                        FileSystemResource res = new FileSystemResource(new File(data.getRscPath()));
                        messageHelper.addInline(data.getRscId(), res);
                    }
                    // 开始发送邮件
                    sender.send(mimeMessage);
                    response.setCode(ErrorCodeEnum.SUCCESS.getCode());
                    response.setMsg(ErrorCodeEnum.SUCCESS.getMessage());
                    response.setSuccess(true);
                    responseContext = "邮件已经发送";
                }
            } catch (MessagingException e) {
                LOGGER.error("发送html邮件时发生异常:" + e.getMessage(), e);
                logsCommStatus = FutureConstant.COMMONT_STATUS_NO_SUCCESS;
                logsBusinessStatus = FutureConstant.COMMONT_STATUS_NO_SUCCESS;
                responseContext = "发送html邮件时发生异常:" + e.getMessage();
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("发送html邮件时处理乱码失败:" + e.getMessage(), e);
                logsCommStatus = FutureConstant.COMMONT_STATUS_NO_SUCCESS;
                logsBusinessStatus = FutureConstant.COMMONT_STATUS_NO_SUCCESS;
                responseContext = "发送html邮件时处理乱码失败:" + e.getMessage();
            } catch (Exception e) {
                LOGGER.error("发生异常:" + e.getMessage(), e);
            }
            // 不管发送成功还是失败，设置日志参数
            response.setLogsCommStatus(logsCommStatus);
            response.setLogsBusinessStatus(logsBusinessStatus);
            response.setRequestContent(requestContent);
            response.setResponseContext(responseContext);
            response.setRequestTime(requestTime);
            response.setResponseTime(DateUtil.getCurrentTime());
        } else {
            response.setCode(ErrorCodeEnum.EMAIL_SEND_PASSAGEWAY_CLOSED.getCode());
            response.setMsg(ErrorCodeEnum.EMAIL_SEND_PASSAGEWAY_CLOSED.getMessage());
            response.setSuccess(false);
            LOGGER.info("邮件发送通道关闭");
        }
        long costTime = System.currentTimeMillis() - startTime;
        LOGGER.info("单个发送邮件，请求结束.[耗时:{}毫秒],返回结果:{}", costTime, response);
        return response;
    }
}
