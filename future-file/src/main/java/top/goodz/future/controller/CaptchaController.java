package top.goodz.future.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.controller.model.CaptchaResponse;
import top.goodz.future.response.CommonResponse;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Description 验证码接口
 * @Author Yajun.Zhang
 * @Date 2020/8/9 18:00
 */

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer producer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping(value = "/captchaImage")
    @ApiOperation(value = "获取验证码图片", notes = "获取验证码图片")
    public CommonResponse<CaptchaResponse> kaptchaImage(@RequestParam("type") String type) {

        BufferedImage image = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        String code = null;
        if ("CHAR".equals(type)) {
            // 生成文字验证码
            code = producer.createText();
            // 生成图片验证码
            image = producer.createImage(code);
        }
        if ("MATH".equals(type)) {
            String capText = captchaProducerMath.createText();
            String capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }

        // 保存验证码 redis 3分钟
        redisTemplate.opsForValue().set(FutureConstant.KAPTCHA_SESSION_KEY + "|", code, 60 * 3);
        try {
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (Exception e) {

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        CaptchaResponse captchaResponse= new CaptchaResponse();

        captchaResponse.setCaptchaImage(Base64Utils.encodeToString(out.toByteArray()));

        return CommonResponse.responseOf(captchaResponse);
    }


    @ApiOperation(value = "生成字母验证码", notes = "生成字母验证码")
    @GetMapping(value = "/verifyCode/{uuid}/{type}")
    public void captcha(@ApiParam(required = true, name = "uuid", value = "验证码的uuid") @PathVariable("uuid") String uuid,
                        @ApiParam(required = true, name = "type", value = "验证码类型 MATH ,CHAR ") @PathVariable("type") String type,
                        HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        BufferedImage image = null;

        String code = null;
        if ("CHAR".equals(type)) {
            // 生成文字验证码
            code = producer.createText();
            // 生成图片验证码
            image = producer.createImage(code);
        }
        if ("MATH".equals(type)) {
            String capText = captchaProducerMath.createText();
            String capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }

        // 保存验证码 redis 3分钟
        redisTemplate.opsForValue().set(FutureConstant.KAPTCHA_SESSION_KEY + "|" + uuid, code, 60 * 3);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
    }

}
