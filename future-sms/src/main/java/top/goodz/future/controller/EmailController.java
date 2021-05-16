package top.goodz.future.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.request.FutureEmailData;
import top.goodz.future.request.email.EmailData;
import top.goodz.future.request.email.EmailListRequest;
import top.goodz.future.request.email.EmailRequest;
import top.goodz.future.response.EmailResponse;
import top.goodz.future.service.EmailService;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 发送邮件功能
 * @Author Yajun.Zhang
 * @Date 2020/5/4 1:55
 */
@RestController
@RequestMapping("/send")
@Api(tags = "邮箱/手机服务api")
public class EmailController {


    @Autowired
    EmailService emailService;

    @ApiOperation(value = "邮件批量发送", notes = "邮件批量发送")
    @RequestMapping(value = "/email/sendEmailsList", method = RequestMethod.POST)
    public List<EmailResponse> sendEmailsList() {
        EmailListRequest emailListRequest = new EmailListRequest();

        List<EmailRequest> list = new ArrayList<>();

        // 邮件发送请求对象
        EmailRequest email = null;

        EmailData data = null;
        for (int i = 0; i < 1; i++) {
            email = new EmailRequest();
            data = new EmailData();
            // 为邮件发送请求对象 设置 接口名称
            email.setChannelsName("batchDeliveryDocuments" + i);
            // 为邮件发送请求对象 设置 操作人i
            email.setCreator("工号 001");
            data.setContent("<h2 bach>申明:</h2> " + "<span sytle = 'font-size:30px;'>此邮件为 future 项目开发阶段测试使用！</span>");
            // 收件人的邮箱
            data.setToMail("1339811979@qq.com");
            // 为邮件数据对象 设置 案件idi
            data.setArbitralInfoId("案件id" + i);
            // 为邮件数据对象 设置 静态资源id
            //异常测试
            data.setSubject("future 团队");
            MimeBodyPart bodyPart = new MimeBodyPart();
            DataHandler dh = new DataHandler(new FileDataSource(FutureConstant.OLAP_BH_EMAIL_PICTURE_TITLE));
            data.setRscId(FutureConstant.OLAP_BH_EMAIL_PICTURE_ID);
            // 为邮件数据对象 设置 静态资源路径
            data.setRscPath(FutureConstant.OLAP_BH_EMAIL_PICTURE_ADDRESS);
            // 为邮件发送请求对象 设置 邮件数据对象
            email.setData(data);
            email.setEmailId("1" + i);
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

        return emailService.sendEmailsList(emailListRequest);
    }

}
