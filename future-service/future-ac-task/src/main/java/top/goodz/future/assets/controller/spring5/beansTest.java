package top.goodz.future.assets.controller.spring5;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import top.goodz.future.assets.controller.spring5.model.User;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/3/6 16:00
 */


public class beansTest {


    public static void main(String[] args) {
            //通过上下文获取bean  xml 方式
            BeanFactory context = new XmlBeanFactory(new ClassPathResource("/xml/spring- bean.xml"));
            User user = context.getBean("user23",User.class);


            System.out.printf(user + "");

    }

}
