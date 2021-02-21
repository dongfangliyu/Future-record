package top.goodz.future.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.controller.model.SlideAuthResponse;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.CaptchaService;
import top.goodz.future.service.model.request.SlideAuthEntity;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Description 验证码controller
 * @Author Yajun.Zhang
 * @Date 2020/6/11 22:19
 */
@RestController
@Api("验证码api")
@RequestMapping("/api")
public class VerificationController {

    @Autowired
    private CaptchaService captchaService;


    @ApiOperation(value = "获取滑动图片", notes = "获取滑动图片")
    @RequestMapping(value = "/verifyImage", method = RequestMethod.GET)
    public CommonResponse<SlideAuthResponse> loadSlide() {

        SlideAuthEntity entitiy = captchaService.createSlideCaptchaVerification();

        SlideAuthResponse response = convert2Response(entitiy);

        return CommonResponse.responseOf(response);
    }

    private SlideAuthResponse convert2Response(SlideAuthEntity entitiy) {

        SlideAuthResponse response = new SlideAuthResponse();

        response.setCutoutImage(entitiy.getCutoutImage());
        response.setOriginImage(entitiy.getCutoutImage());
        response.setShadeImage(entitiy.getShadeImage());
        response.setX(entitiy.getX());
        response.setY(entitiy.getY());
        response.setUuidOne(entitiy.getId());

        return response;
    }


}
