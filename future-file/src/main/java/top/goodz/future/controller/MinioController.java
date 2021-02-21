package top.goodz.future.controller;

import io.minio.PutObjectOptions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.service.MinioService;
import top.goodz.future.service.model.request.UploadMateData;
import top.goodz.future.service.model.request.UploadObjectRequest;
import top.goodz.future.utils.random.RandomUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description minio 控制层
 * @Author Yajun.Zhang
 * @Date 2020/5/2 14:23
 */
@RestController
@RequestMapping("/api")
@Api(tags = "minio 服务 api")
public class MinioController {


    @Autowired
    private MinioService minioService;


    private final Long FILESIZE = 1024L * 1024 * 5;



    @GetMapping("/list")
    public CommonResponse list(@RequestParam("a") String a){
        System.out.println(a);
        return CommonResponse.responseOf(ErrorCodeEnum.SUCCESS.getCode(),a);
    }

    @ApiOperation(value = "图片上传通用接口", notes = "图片上传通用接口")
    @RequestMapping(value = "/upLoad", method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse upLoad(@RequestParam("key") String key,  @RequestPart(value = "file",required = false) MultipartFile file) {
        CommonResponse<Object> response = new CommonResponse<>();
        try {
            //验证文件名称
            String filename = file.getOriginalFilename();
            String ext = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
            if (!"jpg".equals(ext) && !"png".equals(ext) && !"pdf".equals(ext) && !"svg".equals(ext) && !"mp4".equals(ext)) {
                response.setCode(ErrorCodeEnum.ERROR.getCode());
                response.setMsg(FutureConstant.ONLY_FILE_SUPPORT);
                return response;
            }
            //验证文件大小
            long fileSize = file.getSize();
            if (fileSize >= FILESIZE) {
                response.setCode(ErrorCodeEnum.ERROR.getCode());
                response.setMsg(FutureConstant.FILE_SIZE_MAX);
                return response;
            }
            // 生成指定格式的文件名
            String randString = RandomUtil.randomCharString(6);
            String fileMinioName = key + "-" + randString + "-" + filename.substring(0, filename.lastIndexOf(".")) + "-" + fileSize +"-" + ext;
          //     String fileMinioName = key+ "-"+  filename.substring(0, filename.lastIndexOf(".")) + "-" + ext;
            String minioKey = uploadToMinio(key, file, fileMinioName);
            return CommonResponse.responseOf(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage(), minioKey);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.responseOf(ErrorCodeEnum.ERROR.getCode(), ErrorCodeEnum.ERROR.getMessage());
        }

    }


    @ApiOperation(value = "图片预览", notes = "图片预览")
    @RequestMapping(value = "/minio/view/{key}", method = RequestMethod.GET)
    public void getView(@PathVariable @ApiParam("定义存储桶位置") String key, HttpServletResponse response) {
        minioService.getView(key, response);
    }


    private String uploadToMinio(String keyName, MultipartFile file, String fileMinioName) throws IOException {
        UploadObjectRequest request = new UploadObjectRequest();
        request.setBucketName(keyName);  // key 是有意义的，要和minio 服务上的桶名一致
        request.setKey(fileMinioName);
        request.setInputStream(file.getInputStream());
        PutObjectOptions options = new PutObjectOptions(file.getSize(), file.getSize() + FILESIZE);
        UploadMateData mateData = new UploadMateData();
        options.setContentType(file.getContentType());
        mateData.setContentLength(file.getSize());
        mateData.setContentType(file.getContentType());
        mateData.setOptions(options);
        request.setMateDate(mateData);
        String key = minioService.upload(request);
        return key;


    }
}
