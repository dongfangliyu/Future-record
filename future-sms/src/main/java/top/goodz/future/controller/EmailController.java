package top.goodz.future.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.controller.request.FutureEmailData;
import top.goodz.future.controller.request.email.EmailData;
import top.goodz.future.controller.request.email.EmailListRequest;
import top.goodz.future.controller.request.email.EmailRequest;
import top.goodz.future.controller.response.EmailResponse;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.EmailService;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 发送邮件功能
 * @Author Yajun.Zhang
 * @Date 2020/5/4 1:55
 */
@RestController
@RequestMapping("/api/email")
@Api(tags = "邮箱/手机服务api")
public class EmailController {


    @Autowired
    EmailService emailService;

    @ApiOperation(value = "邮件批量发送", notes = "邮件批量发送")
    @RequestMapping(value = "/batchSend", method = RequestMethod.POST)
    public CommonResponse<List<EmailResponse>> sendEmailsList(@RequestBody EmailRequest  request) throws MessagingException {
        EmailListRequest emailListRequest = new EmailListRequest();

        List<EmailRequest> list = new ArrayList<>();

        // 邮件发送请求对象
        EmailRequest email = null;

        EmailData data = null;
        for (int i = 0; i < 1; i++) {
            email = new EmailRequest();
            data = new EmailData();
            // 为邮件发送请求对象 设置 接口名称
            email.setChannelsName(request.getChannelsName());
            // 为邮件发送请求对象 设置 操作人i
            email.setCreator(request.getCreator());
            data.setContent(request.getData().getContent());
            // 收件人的邮箱
            data.setToMail(request.getData().getToMail());
            // 为邮件数据对象 设置 案件idi
            data.setServiceId("业务标识" + request.getData().getServiceId());
            // 为邮件数据对象 设置 静态资源id
            data.setSubject(request.getData().getSubject());
            MimeBodyPart bodyPart = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(FutureConstant.OLAP_BH_EMAIL_PICTURE_TITLE));
            bodyPart.setDataHandler(dh);
            bodyPart.setContentID("OLAP_BH_EMAIL_PICTURE_TITLE");
            data.setRscId(FutureConstant.OLAP_BH_EMAIL_PICTURE_ID);
            // 为邮件数据对象 设置 静态资源路径
            data.setRscPath(FutureConstant.OLAP_BH_EMAIL_PICTURE_ADDRESS);
            // 为邮件发送请求对象 设置 邮件数据对象
            email.setData(data);
            email.setEmailId(request.getEmailId());
            list.add(email);
        }
        emailListRequest.setEmailRequests(list);
        emailListRequest.setOlapName("future 研发团队");

        FutureEmailData futureEmailData = new FutureEmailData();

        futureEmailData.setHost("smtp.163.com");
        futureEmailData.setPort("465");
        futureEmailData.setPassword("GYNAOKQTXRIEJFZP");
        futureEmailData.setUsername("zhangyajun_nb@163.com");
        emailListRequest.setFutureEmailData(futureEmailData);

        List<EmailResponse> responses = emailService.sendEmailsList(emailListRequest);

        return CommonResponse.responseOf(responses);
    }

}
