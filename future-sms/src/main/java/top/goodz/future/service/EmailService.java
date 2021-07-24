package top.goodz.future.service;

import top.goodz.future.controller.request.email.EmailListRequest;
import top.goodz.future.controller.response.EmailResponse;

import java.util.List;

/**
 * @Description
 * @Author Yajun.Zhang
 * @Date 2020/5/4 13:12
 */
public interface EmailService {


    List<EmailResponse> sendEmailsList(EmailListRequest emailListRequest);
}
