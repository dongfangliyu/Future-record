package top.goodz.future.pdf;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.feigin.IFutureFileClient;
import top.goodz.future.pdf.request.ArbDefendantResponse;
import top.goodz.future.pdf.request.ArbProsecutorResponse;
import top.goodz.future.response.CommonResponse;
import top.goodz.future.utils.ChkUtil;
import top.goodz.future.utils.DateUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成pdf
 *
 * @author yajun.zhang
 */
public class PdfUtil {

    @Autowired
    private IFutureFileClient futureFileClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfUtil.class);
    private static final String USERDIR = "user.dir";
    private static final String SLASH = "/";

    /**
     * @param savePath     保存路径
     * @param templateName 模板名称
     * @param variables    模板需要的参数
     * @throws IOException
     * @throws TemplateException
     * @throws @author           zhuliang @createTime： 2018年1月26日 上午11:48:49
     * @Description: 生成pdf前，先生成html
     */
    public static boolean generator(String savePath, String templateName, Map<String, Object> variables)
            throws Exception {
        // 生成html
        String htmlStr = HtmlGenerator.htmlGenerate(templateName, variables);
        // System.out.println(htmlStr);
        //给 数字〇  加宋体的样式
        htmlStr = htmlStr.replaceAll("二〇", "<span class='sim'>二〇</span>");
        OutputStream out = new FileOutputStream(savePath);
        return PdfGenerator.generate(htmlStr, out);
    }

    /**
     * @param savePath  保存路径
     * @param variables 模板需要的参数
     * @param filePath  文件路径
     * @throws IOException
     * @throws TemplateException
     * @throws @author           zhangding @createTime： 2018年7月05日 上午11:48:49
     * @Description: 生成pdf前，先生成html (从FastDfs 下载ftl文件 然后在 读取 ftl )
     */
    public static boolean generatorFastDfs(String savePath, Map<String, Object> variables, String filePath)
            throws Exception {
        // 生成html
        String htmlStr = HtmlGenerator.htmlGenerateFastDfs(variables, filePath);
        OutputStream out = new FileOutputStream(savePath);
        return PdfGenerator.generate(htmlStr, out);
    }

    public static boolean generatorHtml(String savePath, String htmlStr, Map<String, Object> variables)
            throws Exception {
        // 生成html
        OutputStream out = new FileOutputStream(savePath);
        return PdfGenerator.generate(htmlStr, out);
    }

    /**
     * @param fileName       文件名
     * @param templateName   ftl模版名称
     * @param paramMap       ftl需要参数
     * @param arbitralInfoId 案件id
     * @return
     * @throws @author zhuliang @createTime： 2017年12月28日 下午3:25:44
     * @Description: 生成pdf通知书并上传到minio
     */
    public static String uploadPdf(String fileName, String templateName, Map<String, Object> paramMap,
                                   String arbitralInfoId) {
        // 项目根目录
        String systemPath = System.getProperty(USERDIR) + SLASH;
        // pdf临时保存路径
        String directoryName = systemPath + "pdf" + SLASH + arbitralInfoId + SLASH + DateUtil.getCurrentTime(DateUtil.STYLE_3) + SLASH; // 临时保存路径
        // 找到文件夹目录，如不存在就创建
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File deleteFile = null;
        try {
            // 生成PDF成功
            if (PdfUtil.generator(directoryName + fileName, templateName, paramMap)) {
                // 找到生成好的PDF文件
                FileInputStream inputStream = new FileInputStream(directoryName + fileName);
                // 上传至fastDFS文件服务器
//				String filePath = FastdfsFileUtil.fastDFSClient.upload(inputStream, fileName, null);
//				// 关闭流
//				inputStream.close();
//				deleteFile = new File(directoryName + fileName);
//				// 上传成功 返回路径
//				if (ChkUtil.isNotEmpty(filePath)) {
//					return filePath;
//				}
                MultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputStream);
                // 上传到服务器
                CommonResponse<String> filePathResponse = MinioFileUtil.fileClient.upLoad(FutureConstant.MINIO_BUCKET_NAME_PDF, file);
                // 关闭流
                inputStream.close();
                deleteFile = new File(directoryName + fileName);
                // 上传成功 返回路径
                if (ChkUtil.isNotEmpty(filePathResponse) && ChkUtil.isNotEmpty(filePathResponse.getData())) {
                    return filePathResponse.getData();
                }
            }
        } catch (Exception e) {
            LOGGER.error("生成pdf异常" + e.getMessage() + "：{}" + e);
        } finally {
            if (ChkUtil.isNotEmpty(deleteFile) && deleteFile.exists()) {
                deleteFile.delete();
            }
            // 删除目录
            if (ChkUtil.isNotEmpty(directory) && directory.exists()) {
                directory.deleteOnExit();
            }
        }
        return "";
    }

    /**
     * @param fileName       文件名
     * @param paramMap       ftl需要参数
     * @param arbitralInfoId 案件id
     * @param filePath       上传成功返回 路径
     * @return
     * @throws @author Yajun.Zhang
     * @Description: 生成pdf通知书并上传到FastDfs (FastDfs 下载 ftl文件然后 生成 pdf)
     */
    public static String uploadPdfFastDfs(String fileName, Map<String, Object> paramMap, String arbitralInfoId,
                                          String filePath) {
        // 项目根目录
        String systemPath = System.getProperty(USERDIR) + SLASH;
        // pdf临时保存路径
        String directoryName = systemPath + "pdf" + SLASH + arbitralInfoId + SLASH
                + DateUtil.getCurrentTime(DateUtil.STYLE_3) + SLASH; // 临时保存路径
        // 找到文件夹目录，如不存在就创建
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File deleteFile = null;
        try {
            // 生成PDF
            if (PdfUtil.generatorFastDfs(directoryName + fileName, paramMap, filePath)) {
                // 找到生成好的PDF文件
                FileInputStream inputStream = new FileInputStream(directoryName + fileName);
                // 上传至FTP
//				String filePathFast = FastDFSUtil.fastDFSClient.upload(inputStream, fileName, null);
//				// 关闭流
//				inputStream.close();
//				deleteFile = new File(directoryName + fileName);
//				// 上传成功 返回路径
//				if (ChkUtil.isNotEmpty(filePathFast)) {
//					return filePathFast;
//				}
                MultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputStream);
                // 上传到服务器
                CommonResponse<String> filePathResponse = MinioFileUtil.fileClient.upLoad(FutureConstant.MINIO_BUCKET_NAME_PDF, file);
                // 关闭流
                inputStream.close();
                deleteFile = new File(directoryName + fileName);
                // 上传成功 返回路径
                if (ChkUtil.isNotEmpty(filePathResponse) && ChkUtil.isNotEmpty(filePathResponse.getData())) {
                    return filePathResponse.getData();
                }
            }
        } catch (Exception e) {
            LOGGER.error("生成pdf异常:{}", e.getMessage() + "：{}", e);
        } finally {
            if (ChkUtil.isNotEmpty(deleteFile) && deleteFile.exists()) {
                deleteFile.delete();
            }
            // 删除目录
            if (ChkUtil.isNotEmpty(directory) && directory.exists()) {
                directory.deleteOnExit();
            }
        }
        return "";
    }

    /**
     * @param fileName
     * @param htmlStr
     * @param paramMap
     * @param arbitralInfoId
     * @return
     * @throws @author Yajun.Zhang
     * @Description: 生成pdf 上传到 ftp上
     */
    public String uploadPdfHtml(String fileName, String htmlStr, Map<String, Object> paramMap,
                                String arbitralInfoId) {
        // 项目根目录
        String systemPath = System.getProperty(USERDIR) + SLASH;
        // pdf临时保存路径
        String directoryName = systemPath + "pdf" + SLASH; // 临时保存路径
        // 找到文件夹目录，如不存在就创建
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File deleteFile = null;
        try {
            // 生成PDF
            if (PdfUtil.generatorHtml(directoryName + fileName, htmlStr, paramMap)) {
                // ftp保存路径
                // 找到生成好的PDF文件
                FileInputStream inputStream = new FileInputStream(directoryName + fileName);
                // 上传至fastDFS文件服务器
//				String filePath = FastDFSUtil.fastDFSClient.upload(inputStream, fileName, null);
                MultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputStream);
                // 上传到服务器
                CommonResponse<String> filePathResponse = MinioFileUtil.fileClient.upLoad(FutureConstant.MINIO_BUCKET_NAME_PDF, file);
                // 关闭流
                inputStream.close();
                deleteFile = new File(directoryName + fileName);
                // 上传成功 返回路径
                if (ChkUtil.isNotEmpty(filePathResponse) && ChkUtil.isNotEmpty(filePathResponse.getData())) {
                    return filePathResponse.getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("生成pdf异常：{}" + e);
        } finally {
            if (ChkUtil.isNotEmpty(deleteFile) && deleteFile.exists()) {
                deleteFile.delete();
            }
            // 删除目录
            if (ChkUtil.isNotEmpty(directory) && directory.exists()) {
                directory.deleteOnExit();
            }
        }
        return "";
    }

    /**
     * @param fileName       文件名
     * @param templateName   ftl模版名称
     * @param paramMap       ftl需要参数
     * @param arbitralInfoId 案件id
     * @param directoryName  pdf临时保存路径
     * @return
     * @throws @author Yajun.Zhang
     * @Description: 生成pdf通知书并上传到ftp pdf临时保存路径不会删除
     */
    public static String uploadLocalPdf(String fileName, String templateName, Map<String, Object> paramMap,
                                        String arbitralInfoId, String directoryName) {
        try {
            // 找到文件夹目录，如不存在就创建
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // 生成PDF
            if (PdfUtil.generator(directoryName + fileName, templateName, paramMap)) {
                // ftp保存路径
                // 找到生成好的PDF文件
                FileInputStream inputStream = new FileInputStream(directoryName + fileName);
                // 上传至fastDFS
//				String filePath = FastDFSUtil.fastDFSClient.upload(inputStream, fileName, null);
//				// 关闭流
//				inputStream.close();
//				// 上传成功 返回ftp路径
//				if (ChkUtil.isNotEmpty(filePath)) {
//					return filePath;
//				}
                MultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputStream);
                // 上传到服务器
                CommonResponse<String> filePathResponse = MinioFileUtil.fileClient.upLoad(FutureConstant.MINIO_BUCKET_NAME_PDF, file);
                // 关闭流
                inputStream.close();
                // 上传成功 返回ftp路径
                if (ChkUtil.isNotEmpty(filePathResponse) && ChkUtil.isNotEmpty(filePathResponse.getData())) {
                    return filePathResponse.getData();
                }
            }
        } catch (Exception e) {
            LOGGER.error("生成pdf异常");
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    public static void main(String[] args) {
//		String  htmlStr= "<html>"+
//				"<head>"+
//				"<meta charset ='utf-8'></meta>"+
//				"<style> "+
//				"* {margin: 0;padding: 0;border: 0;font-size: 20px;}"+
//				" body {  font-family: FangSong_GB2312;text-indent:2em;}"+
//				" .wrap {width: 700px;margin: 0 auto;}"+
//				".ql-container {box-sizing: border-box;font-family:FangSong_GB2312;font-size: 20px;height: 100%;margin: 0px;position: relative;}"+
//				".ql-container.ql-disabled .ql-tooltip {visibility: hidden;}"+
//				".ql-container.ql-disabled .ql-editor ul[data-checked] > li::before {pointer-events: none;}"+
//				".ql-clipboard {left: -100000px;height: 1px;overflow-y: hidden;position: absolute;top: 50%;}.ql-clipboard p {margin: 0;padding: 0;}"+
//				".ql-editor {box-sizing: border-box;line-height: 1.42;height: 100%;outline: none;overflow-y: auto;padding: 12px 15px;tab-size: 4;-moz-tab-size: 4;text-align: left;white-space: pre-wrap;word-wrap: break-word;}.ql-editor > * {cursor: text;}.ql-editor p,.ql-editor ol,.ql-editor ul,.ql-editor pre,.ql-editor blockquote,.ql-editor h1,.ql-editor h2,.ql-editor h3,.ql-editor h4,.ql-editor h5,.ql-editor h6 {margin: 0;padding: 0;counter-reset: list-1 list-2 list-3 list-4 list-5 list-6 list-7 list-8 list-9;}"+
//				".ql-editor ol,.ql-editor ul {padding-left: 1.5em;}.ql-editor ol > li,.ql-editor ul > li {list-style-type: none;}.ql-editor ul > li::before {content: '2';}.ql-editor ul[data-checked=true],.ql-editor ul[data-checked=false] {pointer-events: none;}.ql-editor ul[data-checked=true] > li *,.ql-editor ul[data-checked=false] > li * {pointer-events: all;}.ql-editor ul[data-checked=true] > li::before,.ql-editor ul[data-checked=false] > li::before {color: #777;cursor: pointer;pointer-events: all;}.ql-editor ul[data-checked=true] > li::before {content: '±1';}.ql-editor ul[data-checked=false] > li::before {content: '±0';}.ql-editor li::before {display: inline-block;white-space: nowrap;width: 1.2em;}.ql-editor li:(.ql-direction-rtl)::before {margin-left: -1.5em;margin-right: 0.3em;text-align: right;}.ql-editor li.ql-direction-rtl::before {margin-left: 0.3em;margin-right: -1.5em;}"+
//				".ql-editor ol li,.ql-editor ul li {padding-left: 1.5em;}.ql-editor ol li.ql-direction-rtl,.ql-editor ul li.ql-direction-rtl {padding-right: 1.5em;}"+
//				".ql-editor ol li {counter-reset: list-1 list-2 list-3 list-4 list-5 list-6 list-7 list-8 list-9;counter-increment: list-0;}.ql-editor ol li:before {content: counter(list-0, decimal) '. ';}.ql-editor ol li.ql-indent-1 {counter-increment: list-1;}.ql-editor ol li.ql-indent-1:before {content: counter(list-1, lower-alpha) '. ';}.ql-editor ol li.ql-indent-1 {counter-reset: list-2 list-3 list-4 list-5 list-6 list-7 list-8 list-9;}.ql-editor ol li.ql-indent-2 {counter-increment: list-2;}.ql-editor ol li.ql-indent-2:before {content: counter(list-2, lower-roman) '. ';}.ql-editor ol li.ql-indent-2 {counter-reset: list-3 list-4 list-5 list-6 list-7 list-8 list-9;}"+
//				".ql-editor ol li.ql-indent-3 {counter-increment: list-3;}.ql-editor ol li.ql-indent-3:before {content: counter(list-3, decimal) '. ';}.ql-editor ol li.ql-indent-3 {counter-reset: list-4 list-5 list-6 list-7 list-8 list-9;}.ql-editor ol li.ql-indent-4 {counter-increment: list-4;}.ql-editor ol li.ql-indent-4:before {content: counter(list-4, lower-alpha) '. ';}.ql-editor ol li.ql-indent-4 {counter-reset: list-5 list-6 list-7 list-8 list-9;}.ql-editor ol li.ql-indent-5 {counter-increment: list-5;}.ql-editor ol li.ql-indent-5:before {content: counter(list-5, lower-roman) '. ';}"+
//				".ql-editor ol li.ql-indent-5 {counter-reset: list-6 list-7 list-8 list-9;}.ql-editor ol li.ql-indent-6 {counter-increment: list-6;}.ql-editor ol li.ql-indent-6:before {content: counter(list-6, decimal) '. ';}"+
//				".ql-editor ol li.ql-indent-6 {counter-reset: list-7 list-8 list-9;}.ql-editor ol li.ql-indent-7 {counter-increment: list-7;}.ql-editor ol li.ql-indent-7:before {content: counter(list-7, lower-alpha) '. ';}"+
//				".ql-editor ol li.ql-indent-7 {counter-reset: list-8 list-9;}.ql-editor ol li.ql-indent-8 {counter-increment: list-8;}.ql-editor ol li.ql-indent-8:before {content: counter(list-8, lower-roman) '. ';}"+
//				".ql-editor ol li.ql-indent-8 {counter-reset: list-9;}.ql-editor ol li.ql-indent-9 { counter-increment: list-9;}.ql-editor ol li.ql-indent-9:before {content: counter(list-9, decimal) '. ';}"+
//				".ql-editor .ql-indent-1 {padding-left: 3em;}.ql-editor li.ql-indent-1 {padding-left: 4.5em;}.ql-editor .ql-indent-1.ql-direction-rtl.ql-align-right {padding-right: 3em;}"+
//				".ql-editor li.ql-indent-1.ql-direction-rtl.ql-align-right {padding-right: 4.5em;}.ql-editor .ql-indent-2 {padding-left: 6em;}.ql-editor li.ql-indent-2 {padding-left: 7.5em;}.ql-editor .ql-indent-2.ql-direction-rtl.ql-align-right {padding-right: 6em;}.ql-editor li.ql-indent-2.ql-direction-rtl.ql-align-right {padding-right: 7.5em;}.ql-editor .ql-indent-3 {padding-left: 9em;}.ql-editor li.ql-indent-3 {padding-left: 10.5em;}.ql-editor .ql-indent-3.ql-direction-rtl.ql-align-right {padding-right: 9em;}.ql-editor li.ql-indent-3.ql-direction-rtl.ql-align-right {padding-right: 10.5em;}.ql-editor .ql-indent-4 {padding-left: 12em;}.ql-editor li.ql-indent-4 { padding-left: 13.5em;}.ql-editor .ql-indent-4.ql-direction-rtl.ql-align-right {padding-right: 12em;}.ql-editor li.ql-indent-4.ql-direction-rtl.ql-align-right {padding-right: 13.5em;}"+
//				".ql-editor .ql-indent-5 {padding-left: 15em;}.ql-editor li.ql-indent-5 {padding-left: 16.5em;}.ql-editor .ql-indent-5.ql-direction-rtl.ql-align-right {padding-right: 15em;}.ql-editor li.ql-indent-5.ql-direction-rtl.ql-align-right {padding-right: 16.5em;}.ql-editor .ql-indent-6 {padding-left: 18em;}.ql-editor li.ql-indent-6 {padding-left: 19.5em;}.ql-editor .ql-indent-6.ql-direction-rtl.ql-align-right {padding-right: 18em;}.ql-editor li.ql-indent-6.ql-direction-rtl.ql-align-right {padding-right: 19.5em;}"+
//				".ql-editor .ql-indent-7 {padding-left: 21em;}.ql-editor li.ql-indent-7 {padding-left: 22.5em;}"+
//				".ql-editor .ql-indent-7.ql-direction-rtl.ql-align-right {padding-right: 21em;}.ql-editor li.ql-indent-7.ql-direction-rtl.ql-align-right {padding-right: 22.5em;}"+
//				".ql-editor .ql-indent-8 {padding-left: 24em;}.ql-editor li.ql-indent-8 {padding-left: 25.5em;}.ql-editor .ql-indent-8.ql-direction-rtl.ql-align-right {padding-right: 24em;}"+
//				".ql-editor li.ql-indent-8.ql-direction-rtl.ql-align-right {padding-right: 25.5em;}.ql-editor .ql-indent-9 {padding-left: 27em;}.ql-editor li.ql-indent-9 { padding-left: 28.5em;}.ql-editor .ql-indent-9.ql-direction-rtl.ql-align-right {padding-right: 27em;}.ql-editor li.ql-indent-9.ql-direction-rtl.ql-align-right {padding-right: 28.5em;}"+
//				".ql-editor .ql-video {display: block;max-width: 100%;}.ql-editor .ql-video.ql-align-center {margin: 0 auto;}.ql-editor .ql-video.ql-align-right {margin: 0 0 0 auto;}"+
//				".ql-editor .ql-bg-black {background-color: #000;}.ql-editor .ql-bg-red {background-color: #e60000;}.ql-editor .ql-bg-orange {background-color: #f90;}.ql-editor .ql-bg-yellow {background-color: #ff0;}.ql-editor .ql-bg-green {background-color: #008a00;}.ql-editor .ql-bg-blue {background-color: #06c;}.ql-editor .ql-bg-purple {background-color: #93f;}"+
//				".ql-editor .ql-color-white {color: #fff;}.ql-editor .ql-color-red {color: #e60000;}.ql-editor .ql-color-orange {color: #f90;}.ql-editor .ql-color-yellow {color: #ff0;}"+
//				".ql-editor .ql-color-green {color: #008a00;}.ql-editor .ql-color-blue {color: #06c;}.ql-editor .ql-color-purple {color: #93f;}.ql-editor .ql-font-serif {font-family: Georgia, Times New Roman, serif;}"+
//				".ql-editor .ql-font-monospace {font-family: Monaco, Courier New, monospace;}.ql-editor .ql-size-small {font-size: 0.75em;}.ql-editor .ql-size-large {font-size: 1.5em;}"+
//				".ql-editor .ql-size-huge {font-size: 2.5em;}.ql-editor .ql-direction-rtl {direction: rtl;text-align: inherit;}.ql-editor .ql-align-center {text-align: center;}.ql-editor .ql-align-justify {text-align: justify;}"+
//				".ql-editor .ql-align-right {text-align: right;}.ql-editor.ql-blank::before {color: rgba(0,0,0,0.6);content: attr(data-placeholder);font-style: italic;left: 15px;pointer-events: none;position: absolute;right: 15px;}"+
//				" .ql-font-SimSun{font-family:SimSun;}.ql-font-FangSong{font-family:FangSong_GB2312;}.ql-size-erhao{font-size:29px;}.ql-size-sanhao{font-size:21px;}.ql-size-xiaosan{font-size:20px;}"+
//				"@page {size: 8.5in 11in;@bottom-center {font-family :SimSun;content: counter(page) '/' counter(pages);}} "+
//				"#pagenumber:before {content: counter(page);}"+
//				".blank {text-indent:2em;}"+
//				".clearfloat {clear:both;}"+
//				"</style>"+
//				"<title>南平仲裁委员会</title>"+
//				"<link rel='shortcut icon' href='/olap/dist/favicon.ico'></link>"+
//				"</head>"+
//				"<body><div class='wrap'><div class='ql-snow'><div class='ql-editor'><h1 class='ql-align-center'><strong class='ql-size-erhao ql-font-SimSun'>北&#160;海&#160;国&#160;际&#160;仲&#160;裁&#160;院</strong></h1><h1 class='ql-align-center'><strong class='ql-size-erhao ql-font-SimSun'>裁&#160;决&#160;书</strong></h1><p class='ql-align-right'>&#160;<span class='ql-size-xiaosan ql-font-FangSong'>(2018)北国仲字第123号</span></p><p class='ql-align-center'><br /></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;申请人：</span><span class='ql-font-FangSong ql-size-xiaosan'>朱亮，天津市&#160;天津城区&#160;和平区，上海市宝山区，朱亮，经理</span><span class='ql-font-FangSong ql-size-xiaosan'>。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;被申请人：</span><span class='ql-font-FangSong ql-size-xiaosan'>自动化测试个人名称1，男，1989-01-31，340203198901310542，自动化测试个人地址1</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;南平仲裁委员会（以下简称本院）根据申请人提交的《仲裁申请书》，以及申请人与被申请人签订的《</span><span class='ql-font-FangSong ql-size-xiaosan'>朱亮合同</span><span class='ql-font-FangSong ql-size-xiaosan'>》的仲裁条款，于</span><span class='ql-font-FangSong ql-size-xiaosan'>2018-08-21</span><span class='ql-font-FangSong ql-size-xiaosan'>受理当事人双方之间的“</span><span class='ql-font-FangSong ql-size-xiaosan'>网络购物合同纠纷</span><span class='ql-font-FangSong ql-size-xiaosan'>”案。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;根据《</span><span class='ql-font-FangSong ql-size-xiaosan'>朱亮合同</span><span class='ql-font-FangSong ql-size-xiaosan'>》的约定，本案适用简易程序书面审理。根据本院院长指定,本案依法组成</span><span class='ql-font-FangSong ql-size-xiaosan'>测试用仲裁员</span><span class='ql-font-FangSong ql-size-xiaosan'>为仲裁员的独任仲裁庭，并指定</span><span class='ql-font-FangSong ql-size-xiaosan'>自动化办案秘书</span><span class='ql-font-FangSong ql-size-xiaosan'>担任秘书。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;仲裁庭依法对本案进行书面审理。本案现已审理终结。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;现就本案案情和仲裁庭意见及裁决分述如下：</span></p><h2 class='ql-align-center'><br /></h2><h2 class='ql-align-center'><strong class='ql-font-FangSong ql-size-sanhao'>一、案&#160;情</strong></h2><p><br /></p><p><span class='ql-size-xiaosan ql-font-FangSong'>&#160;&#160;&#160;&#160;这是自动化仲裁请求所依据的事实和理由</span></p><p><span class='ql-size-xiaosan ql-font-FangSong'>&#160;&#160;&#160;&#160;申请人仲裁请求:</span></p><p><span class='ql-size-xiaosan ql-font-FangSong'>&#160;&#160;&#160;&#160;这是自动化申请仲裁所依据的仲裁协议</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;申请人举证如下：</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;证据1：附件名称&#160;证明:附件内容</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;</span><span class='ql-font-FangSong ql-size-xiaosan'>。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;被申请人称：</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;</span><span class='ql-font-FangSong ql-size-xiaosan'>证据1：bhyz.jpg</span></p><p><span>&#160;&#160;&#160;&#160;</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;被申请人举证如下：</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;证据1：bhyz.jpg</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;</span><span class='ql-font-FangSong ql-size-xiaosan'>。</span></p><h2 class='ql-align-center'><br /></h2><h2 class='ql-align-center'><strong class='ql-font-FangSong ql-size-sanhao'>二、仲裁庭意见</strong></h2><p><br /></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;仲裁庭认为:申请人与被申请人一签订的</span><span class='ql-font-FangSong ql-size-xiaosan'>《</span><span class='ql-font-FangSong ql-size-xiaosan'>朱亮合同</span><span class='ql-font-FangSong ql-size-xiaosan'>》</span><span class='ql-font-FangSong ql-size-xiaosan'>是双方当事人的真实意思表示，内容没有违反法律、行政法规的禁止性规定，应认定合法有效，对双方当事人均具有约束力。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;根据申请人提供的</span><span class='ql-font-FangSong ql-size-xiaosan'>证据1：附件名称,</span><span class='ql-font-FangSong ql-size-xiaosan'>等证据，认定被申请人一现仍拖欠申请人</span><span class='ql-font-FangSong ql-size-xiaosan'>110</span><span class='ql-font-FangSong ql-size-xiaosan'>元。由于被申请人逾期未还款，已构成违约，根据合同法有关规定应承担违约责任，参照《最高人民法院关于审理民间借贷案件适用法律若干问题的规定》第二十九条规定：“借贷双方对逾期利率有约定的，从其约定，但以不超过日利率24%为限”，申请人主张以日利率24%计算罚息符合法律规定，故仲裁庭认为被申请人一应以未偿还借款本金为基数，按日利率24%的标准，从逾期还款之日起至借款本金清偿之日止向申请人支付罚息。其中，计至</span><span class='ql-font-FangSong ql-size-xiaosan'>2018-03-02</span><span class='ql-font-FangSong ql-size-xiaosan'>的利息金额为</span><span class='ql-font-FangSong ql-size-xiaosan'>25.4</span><span class='ql-font-FangSong ql-size-xiaosan'>元。罚息，以</span><span class='ql-font-FangSong ql-size-xiaosan'>110</span><span class='ql-font-FangSong ql-size-xiaosan'>元为基数，按日利率</span><span class='ql-font-FangSong ql-size-xiaosan'>0.1%</span><span class='ql-font-FangSong ql-size-xiaosan'>的标准，自</span><span class='ql-font-FangSong ql-size-xiaosan'>2018-03-03</span><span class='ql-font-FangSong ql-size-xiaosan'>起计算至借款本金清偿之日止。</span></p><h2 class='ql-align-center'><br /></h2><h2 class='ql-align-center'><strong class='ql-size-sanhao ql-font-FangSong'>三、裁&#160;决</strong></h2><p class='ql-align-center'><br /></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;依照《中华人民共和国合同法》第一百零七条、第二百零五条、第二百零六条、第二百零七条和《中华人民共和国仲裁法》第三十九条之规定，仲裁庭裁决如下:</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;一、被申请人应在本裁决书送达后10日内向申请人偿还借款本息</span><span class='ql-font-FangSong ql-size-xiaosan'>135.4</span><span class='ql-font-FangSong ql-size-xiaosan'>元、罚息(罚息:从</span><span class='ql-font-FangSong ql-size-xiaosan'>2018-03-03</span><span class='ql-font-FangSong ql-size-xiaosan'>起的罚息，以本金</span><span class='ql-font-FangSong ql-size-xiaosan'>110</span><span class='ql-font-FangSong ql-size-xiaosan'>元为基数按照日利率</span><span class='ql-font-FangSong ql-size-xiaosan'>0.1%</span><span class='ql-font-FangSong ql-size-xiaosan'>标准计算至借款本金清偿之日止)</span><span class='ql-font-FangSong ql-size-xiaosan'>23.289</span><span class='ql-font-FangSong ql-size-xiaosan'>元。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;&#160;&#160;&#160;二、本案仲裁费用</span><span class='ql-font-FangSong ql-size-xiaosan'>410.9</span><span class='ql-font-FangSong ql-size-xiaosan'>元由被申请人承担，申请人已垫付，被申请人在履行上述义务时一并支付给申请人。</span></p><p><span class='ql-font-FangSong ql-size-xiaosan'>&#160;如义务人未能按本裁决指定的期间履行给付金钱的义务，应当依照《中华人民共和国民事诉讼法》第二百五十三条之规定加倍支付迟延履行期间的债务利息。权利人可在本裁决规定的履行期限最后一日起两年内，向有管辖权的人民法院申请强制执行。</span></p><p class='ql-indent-1'><span class='ql-font-FangSong ql-size-xiaosan'>本裁决为终局裁决，自作出之日起即发生法律效力。</span></p><p><br /></p><p><br /></p><p class='ql-indent-6 ql-align-center'><span class='ql-font-FangSong ql-size-xiaosan'>独任仲裁员:</span><span class='ql-font-FangSong ql-size-xiaosan'>测试用仲裁员</span></p><p class='ql-indent-6'><br /></p><p class='ql-indent-6 ql-align-center'><span class='ql-size-xiaosan ql-font-FangSong'>二<span class='ql-font-SimSun'>〇</span>一八年八月二十一日</span></p><p class='ql-indent-6 ql-align-center'><span class='ql-size-xiaosan ql-font-FangSong'>&#160;秘&#160;&#160;&#160;&#160;&#160;书:</span><span class='ql-size-xiaosan ql-font-FangSong'>&#160;自动化办案秘书</span></p><p class='ql-indent-1'><br /></p></div>"+
//				"</div>"+
//				"</div>"+
//				"</body>"+
//				"</html>";
        List<ArbProsecutorResponse> arbProsecutors = new ArrayList<>();
        ArbProsecutorResponse pro = new ArbProsecutorResponse();
        pro.setName("name");
        pro.setSex("1");
        pro.setType(1);
        pro.setIdNum("idNum");
        pro.setAdress("adress");
        pro.setNation("nation");
        pro.setBirthday("birthday");
        pro.setAgentRealname("agentRealname");
        arbProsecutors.add(pro);
        pro = new ArbProsecutorResponse();
        pro.setName("name");
        pro.setCoLegalPerson("setCoLegalPerson");
        pro.setIdNum("idnum");
        pro.setAdress("adress");
        pro.setType(2);
        arbProsecutors.add(pro);
        List<ArbDefendantResponse> arbDefendants = new ArrayList<>();
        ArbDefendantResponse def = new ArbDefendantResponse();
        def.setName("ZYJ");
        def.setCoLegalPerson("123");
        def.setIdNum("DFLY001");
        def.setAdress("adress");
        def.setType(2);
        def.setAgentRealname("agentRealname");
        arbDefendants.add(def);
        def = new ArbDefendantResponse();
        def.setName("zhangyajun");
        def.setSex("1");
        def.setType(1);
        def.setIdNum("DFLY0001");
        def.setAdress("中国 上海");
        def.setNation("nation");
        def.setBirthday("birthday");
        def.setAgentRealname("agentRealname");
        arbDefendants.add(def);
        Map<String, Object> map = new HashMap<>();
        map.put("arbNumber", "DFLY0000001");
        map.put("arbProsecutors", arbProsecutors);
        map.put("defendants", arbDefendants);
        map.put("arbName", "arbName");
        map.put("arbProsecutorName", "东方鲤鱼团队");
        map.put("arbDefendantName", "广大用户");
        map.put("arbApplyTime", "2020-05-05");
        map.put("procedureName", "zhangyajun");
        map.put("arbProsecutorArbitratorName", "张亚军");
        map.put("arbDefendantArbitratorName", "张亚军");
        map.put("arbArbitratorName", "张亚军");
        map.put("arbApplication", "张亚军");
        map.put("arbSecretaryName", "张亚军");
        map.put("finalTime", "2020-05-05");

//		Map<String, Object> variables = new HashMap<>();
//		variables.put("arbProsecutorName", "申请人");
//		variables.put("arbNumber", "（2018）北国仲字第xx号");
//		variables.put("arbArbitrator", "独任仲裁员");
//		variables.put("today", DateToUpperChinese.toChinese(DateUtil.getCurrentTime("yyyy-MM-dd")));
//
//		variables.put("arbArbitratorName", "独任仲裁员"); // 仲裁员名称
//		variables.put("arbArbitratorType", 2); // 仲裁程序类型：1-独任，2-合议
//
//		variables.put("arbDefendantName", "被申请人");
//		variables.put("arbName", "案 由");
//		variables.put("shouldTime", "应到时间");
//		variables.put("shouldAddress", "应到处所");
//		variables.put("arbSecretaryName", "办案秘书姓名");
//		variables.put("arbProsecutorAddress", "*申请人地址*");
//		variables.put("arbProsecutorJob", "申请人职务：");
//		variables.put("defendantAddress", "*被申请人地址*");
//		variables.put("arbApplication", "*事实与理由*");
//		variables.put("arbArbitratorRecord", "记录员 ");
//		variables.put("dateTime", "201x年xx月xx日  x午xx时xx分至xx时`分");
//		variables.put("arbApplyTime", "201x年xx月");
//		variables.put("acceptCost", "123");
//		variables.put("handleCost", "123");
//		variables.put("arbArbitrateMoney", "123");
//		variables.put("space", "&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;");
//		variables.put("pictureElectronicSignature", PdfUtil.class.getResource("/").toString() + "static/public/img/electronicSignature.png");				// 电子签章图片
//		variables.put("prosecutorAttachments", "申请人证据");
//		uploadPdfHtml("测试.pdf", htmlStr, variables, "");

//		PdfUtil.uploadPdf("预缴仲裁费用通知单.pdf", "applyPayment.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("组庭通知书（独任）—申请人.pdf", "soleProsecutorDiscussionTribunalNotice.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("组庭通知书（独任）—被申请人.pdf", "soleDefendantDiscussionTribunalNotice.ftl", variables, "879798987");
//		PdfUtil.up("仲裁风险提示书.pdf", "arbitrationRiskReminder.ftl", variables, "879798987");
        PdfUtil.up("项目须知.pdf", "mediationDocumentsPanel.ftl", map, "879798987");
//		PdfUtil.up("选定仲裁员及仲裁庭组成方式的函申请人.pdf", "selectionProsecutorArbitratorsAndArbitralTribunals.ftl", variables, "879798987");
//		PdfUtil.up("选定仲裁员及仲裁庭组成方式的函被申请人.pdf", "selectionDefendantArbitratorsAndArbitralTribunals.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("开庭通知申请人.pdf", "openCourtNoticeProsecutor.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("开庭通知被申请人.pdf", "openCourtNoticeDefendant.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("当事人仲裁权利和义务告知书被申请人.pdf", "defendantRightsAndObligationsArbitration.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("开  庭  笔  录（简易）.pdf", "courtRecordSimple.ftl", variables, "879798987");
//		PdfUtil.uploadPdf("开  庭  笔  录（简易 缺席审理）.pdf", "courtRecordSimpleAbsent.ftl", variables, "879798987");
    }

    public static String up(String fileName, String templateName, Map<String, Object> paramMap,
                            String arbitralInfoId) {
        try {
            // 项目根目录
            String systemPath = System.getProperty(USERDIR) + SLASH;
            // pdf临时保存路径
            String directoryName = systemPath + "pdf" + SLASH + arbitralInfoId + SLASH + DateUtil.getCurrentTime(DateUtil.STYLE_3) + SLASH; // 临时保存路径
            // 找到文件夹目录，如不存在就创建
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // 生成PDF成功
            if (PdfUtil.generator(directoryName + fileName, templateName, paramMap)) {
            }
        } catch (Exception e) {
            LOGGER.error("生成pdf异常" + e.getMessage() + "：{}" + e);
        }
        return "";
    }
}
