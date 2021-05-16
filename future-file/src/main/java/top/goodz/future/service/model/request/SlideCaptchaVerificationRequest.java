package top.goodz.future.service.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

/**
 * @Description
 * @Author Yajun.Zhang
 * @Date 2020/6/14 0:12
 */
@Data
public class SlideCaptchaVerificationRequest {

    private InputStream originImg;

    private InputStream templateImg;

    private InputStream borderImg;

    private String originImgType;

    private String templateImgType;

    private String borderImgType;

    private String type;

}
