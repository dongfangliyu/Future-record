package top.goodz.future.gateway.dynamic;/**
 *  * @Description: 
 *  * @throws 
 *  * @author $
 *  * @createTime： $ $ 
 *  * @version： 2.1
 *  
 */

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/9/9 23:01
 */


public interface NacosConstant {
    String NACOS_ADDR = "127.0.0.1:8848";
    String NACOS_CONFIG_PREFIX = "ac";
    String NACOS_CONFIG_FORMAT = "yaml";
    String NACOS_CONFIG_JSON_FORMAT = "json";
    String NACOS_CONFIG_REFRESH = "true";
    String NACOS_CONFIG_GROUP = "DEFAULT_GROUP";

    static String dataId(String appName, String profile) {
        return dataId(appName, profile, "yaml");
    }

    static String dataId(String appName, String profile, String format) {
        return appName + "-" + profile + "." + format;
    }

}
