package top.goodz.future.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.controller.request.FutureEmailData;
import top.goodz.future.controller.request.email.EmailListRequest;
import top.goodz.future.controller.request.email.EmailRequest;
import top.goodz.future.controller.response.EmailResponse;
import top.goodz.future.service.EmailService;
import top.goodz.future.task.EmailListTask;
import top.goodz.future.utils.ChkUtil;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * @Description 发送邮箱实现类
 * @Author Yajun.Zhang
 * @Date 2020/5/4 13:14
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    /**
     * 支持多线程发送邮箱
     *
     * @param emailListRequest
     * @return
     */
    @Override
    public List<EmailResponse> sendEmailsList(EmailListRequest emailListRequest) {
        logger.info("多线程发送邮件开始, 待发送邮件数量为{}", emailListRequest.getEmailRequests().size());
        long startTime = System.currentTimeMillis();
        // 创建一个线程池, 使用有界的任务队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 300, 600, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        // 定义线程返回值集合
        List<Future<EmailResponse>> resultList = new ArrayList<>();
        // 获取仲裁邮箱
        FutureEmailData futureEmailData = emailListRequest.getFutureEmailData();
        String from = futureEmailData.getUsername();
        JavaMailSender sender = getJavaMailSender(futureEmailData);
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            FileSystemResource file = new FileSystemResource(FutureConstant.OLAP_BH_EMAIL_PICTURE_TITLE);
            mimeMessageHelper.addAttachment("future3.png", file);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 提交线程任务
        for (EmailRequest request : emailListRequest.getEmailRequests()) {
            if (StringUtils.isEmpty(request.getEmailId())) {
                continue;
            }
            Future<EmailResponse> result = executor.submit(new EmailListTask(request, emailListRequest.getOlapName(), sender, from));
            resultList.add(result);
        }
        // 关闭线程池
        executor.shutdown();

        // 返回值集合, future.get()方法会产生阻塞, 等到线程返回结果
        List<EmailResponse> response = new ArrayList<>();
        //统计发送成功邮件数量
        int successTotal = 0;
        //统计发送失败邮件数量
        int failTotal = 0;
        // 获取线程返回
        for (Future<EmailResponse> future : resultList) {
            try {
                EmailResponse emailResponse = future.get();
                if (ChkUtil.isNotEmpty(emailResponse)) {
                    response.add(emailResponse);
                    successTotal++;
                }
            } catch (Exception e) {
                failTotal++;
                logger.error("多线程发送邮件失败", e);
            }
        }
        long costTime = System.currentTimeMillis() - startTime;
        logger.info("多线程发送邮件结束, 发送成功邮件数量为{}", successTotal);
        logger.info("总耗时{}毫秒", costTime);
        return response;
    }


    private JavaMailSenderImpl getJavaMailSender(FutureEmailData email) {
        if (ChkUtil.isEmpty(email)) {
            return null;
        }
        String host = email.getHost();                // 邮箱服务器地址
        String username = email.getUsername();        // 发件人邮箱地址
        String password = email.getPassword();        // 发件人邮箱密码
        String sslPort = email.getPort();            // 端口号
        // 创建邮箱发送实例
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setUsername(username);
        javaMailSenderImpl.setPassword(password);
        javaMailSenderImpl.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.port", sslPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSenderImpl.setJavaMailProperties(properties);
        return javaMailSenderImpl;
    }
}
