package top.goodz.future.pdf;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.goodz.future.constants.FutureConstant;
import top.goodz.future.utils.ChkUtil;
import top.goodz.future.utils.DateUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * 根据ftl模板 生成 html 生成工具类
 *
 * @author zhuliang
 */
public class HtmlGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerator.class);
	private static final String FTLTEMPLATE = ".ftl";
	private static final String SLASH = "/";

	/**
	 * @Description 根据本地的ftl模板生成html
	 * @param template	ftl模板文件名称
	 * @param variables	参数
	 * @return html内容
	 * @author Seif Zheng
	 * @createTime 2020/3/4 17:47
	 * @version 2.1
	 */
	public static String htmlGenerate(String template, Map<String, Object> variables)
			throws IOException, TemplateException {
		Configuration config = FreemarkerConfiguration.getConfiguration();
		//config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		//config.setDefaultEncoding("UTF-8");
		Template tp = config.getTemplate(template);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		//tp.setEncoding("UTF-8");
		tp.process(variables, writer);
		String htmlStr = stringWriter.toString();
		writer.flush();
		writer.close();
		return htmlStr;
	}

	/**
	 * @Description 从FastDFS下载ftl文件, 根据ftl模板生成html
	 * @param variables	参数
	 * @param filePath	ftl文件地址
	 * @return html内容
	 * @author Seif Zheng
	 * @createTime 2020/3/4 17:45
	 * @version 2.1
	 */
	public static String htmlGenerateFastDfs(Map<String, Object> variables, String filePath) {
		String htmlStr = "";

		// 指定下载ftl文件的目录，不存在就创建
		String systemPath = System.getProperty("user.dir") + SLASH;
		String destinationFolder = systemPath + FutureConstant.OLAP_FTP_PATH + "fastFtl" + SLASH;
		File directory = new File(destinationFolder);
		if (!directory.exists() || directory.isDirectory()) {
			directory.mkdirs();
		}

		String fileName = DateUtil.getCurrentTime(DateUtil.STYLE_10) + FTLTEMPLATE;
		File deleteFile = null;
		try {
			// 下载ftl文件 todo
			//FastdfsFileUtilNp.fileClient.fileDownLoadDestinationFolder(filePath, fileName, destinationFolder);
			// 载入下载的ftl文件所在目录，创建新的freemarker模板引擎配置
			Configuration config = new Configuration(Configuration.getVersion());
			config.setDirectoryForTemplateLoading(new File(destinationFolder));
			config.setDefaultEncoding("UTF-8");
			// 获取模板
			Template tp = config.getTemplate(fileName);
			// 使用缓冲字符流生成html
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			tp.process(variables, writer);
			htmlStr = stringWriter.toString();
			// 刷新并关闭流
			writer.flush();
			writer.close();
			// ftl模板文件
			deleteFile = new File(destinationFolder + fileName);
		} catch (Exception e) {
			LOGGER.error("fastDFS下载ftl失败", e);
		}
		// 删除ftl模板文件
		if (ChkUtil.isNotEmpty(deleteFile) && deleteFile.exists()) {
			deleteFile.delete();
		}
		// 删除ftl模板文件所在目录
		if (ChkUtil.isNotEmpty(directory) && directory.exists()) {
			directory.deleteOnExit();
		}
		return htmlStr;
	}

	public static String blankHtmlGenerate(String path) throws IOException, TemplateException {
		Configuration config = FreemarkerConfiguration.getConfiguration();
		config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		config.setDefaultEncoding("UTF-8");
		Template tp = config.getTemplate(path);
		return tp.toString();
	}

	//public static void main(String[] args) throws IOException, TemplateException {
	//	 Map<String, Object> variables = new HashMap<>();
	//	variables.put("arbNumber", "案件编号");
	//	variables.put("arbProsecutorName", "申请人名称");
	//	variables.put("arbApplyTime", "2019年11月6日");
	//	variables.put("arbDefendantName","被申请人名称");
	//	variables.put("arbName","民事案件纠纷");
	//	variables.put("acceptCost","180.00");
	//	variables.put("handleCost","120.00");
	//	variables.put("arbArbitrateMoney","300.00");
	//	variables.put("arbSecretaryName","郑雪峰");
	//	variables.put("today",DateUtil.getCurrentTime());
	//	System.out.println(HtmlGenerator.htmlGenerate("applyPayment.ftl", variables));
	//}

}
