
package top.goodz.future.gateway.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 * @author zhangyajun
 */
public class AuthProvider {

    public static String TARGET = "/**";
    public static String REPLACEMENT = "";
    public static String OLAP_AUTH_KEY = "olap-Auth";
    public static String AUTH_KEY = "Authorization";
    private static List<String> defaultSkipUrl = new ArrayList<>();

    static {
        defaultSkipUrl.add("/api/captcha/**");
        defaultSkipUrl.add("api/auth/api/login");
        defaultSkipUrl.add("api/file/api/verifyImage");
        defaultSkipUrl.add("/actuator/**");

        defaultSkipUrl.add("/css/**");
        defaultSkipUrl.add("/js/**");
        defaultSkipUrl.add("/ruoyi/**");
        defaultSkipUrl.add("/ajax/**");
        defaultSkipUrl.add("/**/webjars/**");
    }

    /**
     * 默认无需鉴权的API
     */
    public static List<String> getDefaultSkipUrl() {

        return defaultSkipUrl;
    }

    private void test(){

    }

}
