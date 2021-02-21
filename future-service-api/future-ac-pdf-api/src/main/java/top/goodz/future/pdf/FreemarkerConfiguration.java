package top.goodz.future.pdf;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfiguration {

	private static Configuration config = null;

	/**
	 * Static initialization.
	 *
	 * Initialize the configuration of Freemarker.
	 */
	static{
		config = new Configuration(Configuration.getVersion());
		config.setClassForTemplateLoading(FreemarkerConfiguration.class, "freemarker/template");
		config.setDefaultEncoding("UTF-8");
		config.setObjectWrapper(new DefaultObjectWrapper(Configuration.getVersion()));
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	public static Configuration getConfiguration(){
		return config;
	}
}
