
package top.goodz.future.constants;


/**
 * 通用常量
 *
 * @author zhangyajun
 */
public interface Constant {

    /**
     * nacos dev 地址
     */
    String NACOS_DEV_ADDR = "127.0.0.1:8848";

    /**
     * nacos test 地址
     */
    String NACOS_SIT_ADDR = "172.16.10.39:8848";

    /**
     * nacos prod 地址
     */
    String NACOS_PROD_ADDR = "122.112.137.83:8848";

    /**
     * nacos test 地址
     */
    String NACOS_TEST_ADDR = "172.30.0.48:8848";

    /**
     * sentinel dev 地址
     */
    String SENTINEL_DEV_ADDR = "127.0.0.1:8858";

    /**
     * sentinel prod 地址
     */
    String SENTINEL_PROD_ADDR = "172.30.0.58:8858";

    /**
     * sentinel test 地址
     */
    String SENTINEL_TEST_ADDR = "172.30.0.58:8858";

    /**
     * sword 系统名
     */
    String SWORD_NAME = "sword";

    /**
     * saber 系统名
     */
    String SABER_NAME = "saber";

    /**
     * 顶级父节点id
     */
    Long TOP_PARENT_ID = 0L;

    /**
     * 顶级父节点名称
     */
    String TOP_PARENT_NAME = "顶级";


    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 自增单据号前缀
     */
    String SEQ_NUMBER_PREFIX = "CONTRACT";

    /**
     * 动态获取nacos地址
     *
     * @param profile 环境变量
     * @return addr
     */
    static String nacosAddr(String profile) {
        switch (profile) {
            case (AppConstant.PROD_CODE):
                return NACOS_PROD_ADDR;
            case (AppConstant.SIT_CODE):
                return NACOS_SIT_ADDR;
            case (AppConstant.TEST_CODE):
                return NACOS_TEST_ADDR;
            default:
                return NACOS_DEV_ADDR;
        }
    }

    /**
     * 动态获取sentinel地址
     *
     * @param profile 环境变量
     * @return addr
     */
    static String sentinelAddr(String profile) {
        switch (profile) {
//			case (AppConstant.PROD_CODE):
//				return SENTINEL_PROD_ADDR;
            case (AppConstant.TEST_CODE):
                return SENTINEL_TEST_ADDR;
            default:
                return SENTINEL_DEV_ADDR;
        }
    }

}
