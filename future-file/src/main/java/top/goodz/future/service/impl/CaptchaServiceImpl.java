package top.goodz.future.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.enums.ExceptionCode;
import top.goodz.future.exception.ServiceException;
import top.goodz.future.service.CaptchaService;
import top.goodz.future.service.model.enums.CaptchaAuthStatus;
import top.goodz.future.service.model.request.SlideAuthEntity;
import top.goodz.future.service.model.request.SlideCaptchaVerificationRequest;
import top.goodz.future.service.model.response.SlideCheckResultVO;
import top.goodz.future.service.repository.FileRepository;
import top.goodz.future.service.repository.SlideCaptcheRepository;
import top.goodz.future.service.utils.ImageVerificationUtil;
import top.goodz.future.utils.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * @Description 图片滑动实现类
 * @Author Yajun.Zhang
 * @Date 2020/6/12 23:19
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);

    @Value("${verefication.templateImageFileType}")
    private String templateImageFileType;

    @Value("${verefication.borderImageFileType}")
    private String borderImageFileType;

    @Value("${verefication.originImageFileType}")
    private String originImageFileType;

    @Value("${verefication.originImageNum}")
    private int originImageNum;

    private static final String TEMPLATE_KEY = "template";

    private static final String BORDER_KEY = "border";

    private static final String BUCKET_NAME = "future";

    private static int threshold = 5;

    private int captchaInitExpireSeconds = 60;

    private int captchaAuthExpireSeconds = 60;


    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SlideCaptcheRepository captcheRepository;


    @Override
    public SlideAuthEntity createSlideCaptchaVerification() {
        return createSlideAuth(buildCreateParam());
    }

    @Override
    public SlideCheckResultVO check(String authId, Integer x, Integer y) {
        SlideAuthEntity slideAuthEntity = captcheRepository.load(authId);

        if (Objects.isNull(slideAuthEntity)) {
            ErrorCodeEnum.SLIDE_CAPTCHA_ERROR.throwEcxeption();
        }

        boolean checkResult = checkResult(slideAuthEntity, x, y);

        SlideCheckResultVO result = new SlideCheckResultVO();
        result.setAuthId(slideAuthEntity.getId());
        result.setResult(checkResult);

        if (checkResult) {
            captcheRepository.updateAuthStatus(authId, String.valueOf(CaptchaAuthStatus.AUTH.getCode()));

            captcheRepository.updateExpireTime(authId, captchaAuthExpireSeconds * 1000l);

            return result;
        }

        captcheRepository.deleteAuthEntity(authId);

        return result;
    }

    @Override
    public SlideCheckResultVO auth(String authId) {
        SlideAuthEntity slideAuthEntity = captcheRepository.load(authId);

        return convert2SlideCapthaVO(slideAuthEntity);

    }

    private SlideCheckResultVO convert2SlideCapthaVO(SlideAuthEntity slideAuthEntity) {
        SlideCheckResultVO resultVO = new SlideCheckResultVO();

        if (Objects.isNull(slideAuthEntity)) {
            resultVO.setResult(false);
            return resultVO;
        }

        if (!(slideAuthEntity.getStatus().equals(CaptchaAuthStatus.AUTH.getCode())) || System.currentTimeMillis() > slideAuthEntity.getExpireTimestemp()) {
            resultVO.setResult(false);
            return resultVO;
        }
        resultVO.setResult(true);
        return resultVO;
    }

    private boolean checkResult(SlideAuthEntity slideAuthEntity, Integer x, Integer y) {
        return (Math.abs(x - slideAuthEntity.getX()) <= threshold) && y == slideAuthEntity.getY();
    }

    private SlideAuthEntity createSlideAuth(SlideCaptchaVerificationRequest request) {
        File templateTempFile = null;
        File originTempFile = null;
        try {
            templateTempFile = new File("./templateTempFile");
            originTempFile = new File("./originTempFile");

            if (!templateTempFile.exists()) {
                templateTempFile.createNewFile();
                LOGGER.info("templateFile {} create!", "templateTempFile");
            }

            if (!originTempFile.exists()) {
                originTempFile.createNewFile();
                LOGGER.info("templateFile {} create!", "originTempFile");
            }

            //模板缓存
            byte[] templateBytes = FileUtils.toByteArray(request.getTemplateImg());
            //原图缓存
            byte[] originBytes = FileUtils.toByteArray(request.getOriginImg());
            // 各复制一份
            byte[] templateCache = new byte[templateBytes.length];
            byte[] originCache = new byte[originBytes.length];

            System.arraycopy(templateBytes, 0, templateCache, 0, templateBytes.length);
            System.arraycopy(originBytes, 0, originCache, 0, originBytes.length);

            FileUtils.FileCopy(new ByteArrayInputStream(templateBytes), templateTempFile);
            FileUtils.FileCopy(new ByteArrayInputStream(originBytes), originTempFile);

            BufferedImage verificationImage = ImageIO.read(new ByteArrayInputStream(originCache));
            //读取模板图
            BufferedImage readTemplateImage = ImageIO.read(new ByteArrayInputStream(templateCache));

            //读取描边图
            InputStream border = request.getBorderImg();
            BufferedImage borderImage = ImageIO.read(border);
            //获取原图感兴趣区域
            int[] coordinate = ImageVerificationUtil.generateCutoutCoordinates(verificationImage, readTemplateImage);

            int x = coordinate[0];
            int y = coordinate[1];

            String[] coutout = ImageVerificationUtil.pitctureTemplateCutout(originTempFile, request.getOriginImgType(),
                    templateTempFile, request.getTemplateImgType(), x, y);

            String coutoutImg = ImageVerificationUtil.cutoutImageEdge(coutout[2], borderImage, request.getBorderImgType());

            return createSlideAuthResponse(x, y, coutoutImg, coutout[0], coutout[1]);
        } catch (Exception e) {
            LOGGER.error("create template exception {}", e.getMessage());
            throw new ServiceException(ExceptionCode.DEF_ERROR);
        } finally {
            if (templateTempFile != null && templateTempFile.exists()) {
                templateTempFile.delete();
                LOGGER.info("templateTempFile  delete  successfully");
            }

            if (originTempFile != null && originTempFile.exists()) {
                originTempFile.delete();
                LOGGER.info("originTempFile delete successfully");
            }
        }

    }

    private SlideAuthEntity createSlideAuthResponse(int x, int y, String coutoutImg, String originImg, String shadeImg) {

        SlideAuthEntity entity = new SlideAuthEntity();

        entity.setId(UUID.randomUUID().toString());
        entity.setShadeImage(String.valueOf(CaptchaAuthStatus.INIT.getCode())); // 1 初始化 2 已使用
        entity.setExpireTimestemp(System.currentTimeMillis() + captchaAuthExpireSeconds * 1000L);
        entity.setX(x);
        entity.setY(y);
        entity.setCutoutImage(coutoutImg);
        entity.setOriginImage(originImg);
        entity.setShadeImage(shadeImg);

        captcheRepository.save(entity);

        return entity;
    }

    private SlideCaptchaVerificationRequest buildCreateParam() {
        String chooseImgId = chooseImgId();

        InputStream img = fileRepository.loadStream(chooseImgId + "-" + originImageFileType);
        InputStream templateImage = fileRepository.loadStream(BUCKET_NAME + "-" + TEMPLATE_KEY + "-" + templateImageFileType);
        InputStream borderImage = fileRepository.loadStream(BUCKET_NAME + "-" + BORDER_KEY + "-" + borderImageFileType);

        SlideCaptchaVerificationRequest req = new SlideCaptchaVerificationRequest();
        req.setOriginImg(img);
        req.setTemplateImg(templateImage);
        req.setBorderImg(borderImage);

        req.setOriginImgType(originImageFileType);
        req.setTemplateImgType(templateImageFileType);
        req.setBorderImgType(borderImageFileType);

        req.setType("SLIDE");
        return req;
    }


    private String chooseImgId() {
        Random random = new Random(System.currentTimeMillis());
        int no = random.nextInt(originImageNum);
        if (no == 0) {
            no++;
        }
        return BUCKET_NAME + "-" + no;
    }


}
