package top.goodz.future.assess.controller;/**
 *  * @Description: 
 *  * @throws 
 *  * @author zhangyajun$
 *  * @createTime： 2021-5-18$ 22:13$ 
 *  * @version： 2.1
 *  
 */

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.goodz.future.application.process.csv.CSVImportProcessService;
import top.goodz.future.application.process.excel.ExcelImportProcessService;
import top.goodz.future.domain.listener.TaskDataImportListener;
import top.goodz.future.domain.listener.model.AutoTaskDataVO;
import top.goodz.future.domain.service.AutoCreateDataService;
import top.goodz.future.enums.ErrorCodeEnum;
import top.goodz.future.response.CommonResponse;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2021/5/18 22:13
 */

@RestController
@Api("导入文件RestController")
@RequestMapping("/api/import")
public class ExcelImportController {

    @Autowired
    private ExcelImportProcessService excelImportProcessService;

    @Autowired
    private AutoCreateDataService autoCreateDataService;

    @Autowired
    private CSVImportProcessService csvImportProcessService;


    @PostMapping("/importExcelData")
    @ApiOperation("导入excel文件")
    public CommonResponse importData(@RequestParam("file") MultipartFile file) throws IOException {

        // 处理上传文件
        if (file.isEmpty()) {
            return CommonResponse.responseOf(ErrorCodeEnum.ERROR.getCode(), "上传失败，请选择文件");
        }

        if (!"xsl".equals(file.getName().substring(file.getName().lastIndexOf("."))) ||
                !"xlsx".equals(file.getName().substring(file.getName().lastIndexOf(".")))) {
            return CommonResponse.responseOf(ErrorCodeEnum.ERROR.getCode(), "上传失败，请选择正确的文件格式");
        }

        // 流导入
        InputStream inputStream = file.getInputStream();

        inputStream = new BufferedInputStream(file.getInputStream());
        // 监听器
        TaskDataImportListener listener = new TaskDataImportListener(autoCreateDataService);
        // 读取excel
        EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, AutoTaskDataVO.class), listener);

        // 查看是否有异常
        Map<String, Object> maps = listener.getAnalysisMap();
        String temp = "";
        if (maps.size() > 0) {
            for (Map.Entry<String, Object> map : maps.entrySet()) {
                temp += map.getValue() + "\n";
            }
        }

        // 返回信息
        HashMap<String, String> hashMap = new HashMap<>();
        //   hashMap.put("success",listener.getList().size()+"");
        hashMap.put("error", temp);

        inputStream.close();

        return CommonResponse.isSuccess();
    }


    @PostMapping("/importCSVData")
    @ApiOperation("导入CSV文件")
    public CommonResponse importCsvData(@RequestParam("file") MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();


        csvImportProcessService.CSVImportData(inputStream);

        return CommonResponse.isSuccess();
    }
}
