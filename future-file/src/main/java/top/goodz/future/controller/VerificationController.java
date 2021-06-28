package top.goodz.future.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.goodz.future.controller.model.SlideAuthResponse;
import top.goodz.future.controller.model.SlideCheckResult;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.CaptchaService;
import top.goodz.future.service.model.request.SlideAuthEntity;
import top.goodz.future.service.model.response.SlideCheckResultVO;

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
        response.setOriginImage(entitiy.getOriginImage());
        response.setShadeImage(entitiy.getShadeImage());
        response.setX(entitiy.getX());
        response.setY(entitiy.getY());
        response.setUuidOne(entitiy.getId());

        return response;
    }


    @ApiOperation(value = "验证", notes = "验证")
    @RequestMapping(value = "/verification", method = {RequestMethod.GET, RequestMethod.POST})
    public CommonResponse<SlideCheckResult> verification(String uuid, String x, String y) {
        SlideCheckResultVO check = captchaService.check(uuid, Integer.valueOf(x), Integer.valueOf(y));

        return CommonResponse.responseOf(convert2CheckResultResponse(check));
    }

    private SlideCheckResult convert2CheckResultResponse(SlideCheckResultVO check) {
        SlideCheckResult checkResult = new SlideCheckResult();
        checkResult.setAuthId(check.getAuthId());
        checkResult.setResult(check.isResult());

        return checkResult;
    }

}
