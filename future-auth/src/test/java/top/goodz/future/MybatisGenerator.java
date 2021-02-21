package top.goodz.future;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @Description 逆向工程
 * @Author Yajun.Zhang
 * @Date 2020/6/10 22:47
 */
public class MybatisGenerator {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> list = new ArrayList<>();

        boolean ovr = true;

        File file = ResourceUtils.getFile("classpath:generatorConfig.xml");
        try {

            ConfigurationParser configurationParser = new ConfigurationParser(list);

            Configuration configuration = configurationParser.parseConfiguration(file);

            DefaultShellCallback callback = new DefaultShellCallback(ovr);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, list);

            myBatisGenerator.generate(null);

            list.forEach(System.out::println);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
