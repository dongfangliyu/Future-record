package top.goodz.future.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.goodz.future.enums.ExceptionCode;
import top.goodz.future.exception.ServiceException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

/**
 *  * @description：滑动图片工具类
 *  * @throws 
 *  * @author  YaJun.Zhang
 *  * @createTime：2020/5/20
 *  * @version：  1.0
 *
 * @return  
 */
public class ImageVerificationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageVerificationUtil.class);


    private static final int DEFAULT_IMAGE_WIDTH = 339;

    private static final int DEFAULT_IMAGE_HEIGHT = 210;


    /**
     *  * @description：获取请求对象
     *  * @throws 
     *  * @createTime：2020/5/20
     *
     * @return  
     */
    protected static HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected static HttpServletResponse getResponse() {

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     *  * @description：生成感兴趣坐标
     *  * @throws 
     *  * @author  YaJun.Zhang
     *  * @createTime：2020/5/20
     *  * @version：  1.0
     *
     * @return  
     */
    public static int[] generateCutoutCoordinates(BufferedImage image, BufferedImage templateImage) {

        int x, y;
        //抠图高度
        int templateImageWidthwidth = templateImage.getWidth();

        int templateImageHeight = templateImage.getHeight();

        Random random = new Random(System.currentTimeMillis());

        x = random.nextInt(DEFAULT_IMAGE_WIDTH - templateImageWidthwidth) % (DEFAULT_IMAGE_WIDTH - templateImageWidthwidth - templateImageWidthwidth + 1) + templateImageWidthwidth;

        y = random.nextInt(DEFAULT_IMAGE_HEIGHT - templateImageWidthwidth) % (DEFAULT_IMAGE_HEIGHT - templateImageWidthwidth - templateImageWidthwidth + 1) + templateImageWidthwidth;

        if (templateImageHeight - DEFAULT_IMAGE_HEIGHT >= 0) {

            y = random.nextInt(10);
        }

        return new int[]{x, y};

    }

    public static String cutoutImageEdge(String cutoutImageString, BufferedImage borderImage, String borderImageFileType) throws ServiceException {

        ByteArrayInputStream byteArrayInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byte[] bytes = Base64Utils.decodeFromString(cutoutImageString);
            byteArrayInputStream = new ByteArrayInputStream(bytes);

            // 读取图片
            BufferedImage cutoutImage = ImageIO.read(byteArrayInputStream);

            //获取模板颜色矩阵 对颜色处理
            int[][] borderImageMatrix = getMatrix(borderImage);
            for (int i = 0; i < borderImageMatrix.length; i++) {
                for (int j = 0; j < borderImageMatrix[0].length; j++) {
                    int rgb = borderImage.getRGB(i, j);
                    if (rgb < 0) {
                        cutoutImage.setRGB(i, j, -7237488);
                    }
                }
            }

            byteArrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(cutoutImage, borderImageFileType, byteArrayOutputStream);

            // 新模板图描边处理后转为二进制字符串
            byte[] cutoutImageBytes = byteArrayOutputStream.toByteArray();

            //加密为base64
            String cutoutImageStr = Base64Utils.encodeToString(cutoutImageBytes);
            return cutoutImageStr;


        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ExceptionCode.IO_IMAGE_ERROR);
        } finally {
            try {
                byteArrayInputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage(), e);
                throw new ServiceException(ExceptionCode.IO_IMAGE_ERROR);
            }
        }

    }

    /**
     *  * @description：根据模板源 ，生成遮罩层 和 裁剪图
     *  * @throws 
     *  * @author  YaJun.Zhang
     *  * @createTime：2020/5/20
     *  * @version：  1.0
     *
     * @return  
     */
    public static String[] pitctureTemplateCutout(File originImageFile, String originImageType, File templateImageFile, String templeateImageType, int x, int y) throws ServiceException {
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            BufferedImage templateImage = ImageIO.read(templateImageFile);

            BufferedImage originImage = ImageIO.read(originImageFile);
            //图片属性  宽度高度
            int templateImageWidth = templateImage.getWidth();
            int templateImageHeight = templateImage.getHeight();

            BufferedImage cutoutImage = new BufferedImage(templateImageWidth, templateImageHeight, templateImage.getType());

            //根据坐标生成感兴趣区域
            BufferedImage bufferedImage = getInteresArea(x, y, templateImageWidth, templateImageHeight, originImageFile, originImageType);

            //生成切图
            cutoutImage = cutoutImageByTemplateImage(bufferedImage, templateImage, cutoutImage);

            //图片绘图
            int blod = 5;

            Graphics2D graphics = cutoutImage.createGraphics();

            graphics.setBackground(Color.white);

            //设置抗锯齿属性
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setStroke(new BasicStroke(blod, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics.drawImage(cutoutImage, 0, 0, null);
            graphics.dispose();

            //原图生成这遮罩层
            BufferedImage shadeImage = generateShadeByTemplateImage(originImage, templateImage, x, y);

            byteArrayOutputStream = new ByteArrayOutputStream();
            //图片转为二进制
            ImageIO.write(originImage, originImageType, byteArrayOutputStream);
            byte[] originImageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.reset();

            //图片加密为BASE64
            String originImageString = Base64Utils.encodeToString(originImageBytes);

            ImageIO.write(shadeImage, templeateImageType, byteArrayOutputStream);

            //转为二进制字符串
            byte[] shadeImageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.reset();

            String shadeImageString = Base64Utils.encodeToString(shadeImageBytes);

            ImageIO.write(cutoutImage, templeateImageType, byteArrayOutputStream);

            //转为二进制
            byte[] cutoutImageBytes = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.reset();

            //加密为base64
            String cutoutImageString = Base64Utils.encodeToString(cutoutImageBytes);

            return new String[]{originImageString, shadeImageString, cutoutImageString};


        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ExceptionCode.IO_IMAGE_ERROR);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new ServiceException(ExceptionCode.IO_IMAGE_ERROR);
            }
        }
    }

    private static BufferedImage generateShadeByTemplateImage(BufferedImage originImage, BufferedImage templateImage, int x, int y) throws IOException {

        //根据原图  创建支持alpha 通道rgb图片
        BufferedImage shadeImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int[][] originImageMatix = getMatrix(originImage);

        int[][] templateImageMatix = getMatrix(templateImage);

        //原图拷贝到遮罩层
        for (int i = 0; i < originImageMatix.length; i++) {
            for (int j = 0; j < originImageMatix[0].length; j++) {
                int rgb = originImage.getRGB(i, j);

                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));

                //无透明处理
                rgb = r + (g << 8) + (b << 16) + (255 << 24);
                shadeImage.setRGB(i, j, rgb);

            }
        }
        //对遮罩层根据模板进行处理
        for (int i = 0; i < templateImageMatix.length; i++) {
            for (int j = 0; j < templateImageMatix[0].length; j++) {
                int rgb = templateImage.getRGB(i, j);

                //对源文件进行（x+i,y+j） 坐标透明处理
                if (rgb != 16777215 && rgb < 0) {
                    int originRGB = shadeImage.getRGB(x + i, y + j);

                    int r = (0xff & originRGB);
                    int g = (0xff & (originRGB >> 8));
                    int b = (0xff & (originRGB >> 16));

                    r = r / 2;
                    g = g / 2;

                    b = 1;


                    originRGB = r + (g << 8) + (b << 16) + (1000 << 24);

                    //对遮罩层透明处理
                    shadeImage.setRGB(x + i, y + j, originRGB);

                    //对遮罩层上颜色
                    //      shadeImage.setRGB();
                }
            }
        }

        return shadeImage;
    }

    private static BufferedImage cutoutImageByTemplateImage(BufferedImage bufferedImage, BufferedImage templateImage, BufferedImage cutoutImage) {

        getMatrix(bufferedImage);
        //获取矩阵
        int[][] templateImageMatrix = getMatrix(templateImage);

        for (int i = 0; i < templateImageMatrix.length; i++) {
            for (int j = 0; j < templateImageMatrix[0].length; j++) {
                int rgb = templateImageMatrix[i][j];

                if (rgb != 16777215 && rgb < 0) {
                    cutoutImage.setRGB(i, j, bufferedImage.getRGB(i, j));
                }
            }
        }

        return cutoutImage;


    }


    private static int[][] getMatrix(BufferedImage bufferedImage) {

        int[][] matrix = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                matrix[i][j] = bufferedImage.getRGB(i, j);
            }
        }
        return matrix;


    }


    private static BufferedImage getInteresArea(int x, int y, int templateImageWidth, int templateImageHeight, File originImage, String originImageType) {

        ImageInputStream imageInputStream = null;

        try {
            Iterator<ImageReader> imageReaderIterator = ImageIO.getImageReadersByFormatName(originImageType);

            ImageReader imageReader = imageReaderIterator.next();
            //获取图片流
            imageInputStream = ImageIO.createImageInputStream(originImage);
            //顺序读写输入流
            imageReader.setInput(imageInputStream, true);

            ImageReadParam imageReadParam = imageReader.getDefaultReadParam();

            //生成范围整列
            Rectangle rectangle = new Rectangle(x, y, templateImageWidth, templateImageHeight);

            imageReadParam.setSourceRegion(rectangle);

            BufferedImage bufferedImage = imageReader.read(0, imageReadParam);

            return bufferedImage;


        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ExceptionCode.IO_IMAGE_ERROR);
        } finally {
            try {
                imageInputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new ServiceException(ExceptionCode.IO_IMAGE_ERROR);
            }
        }


    }

}
