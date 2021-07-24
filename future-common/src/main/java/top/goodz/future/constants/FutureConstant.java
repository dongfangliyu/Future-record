package top.goodz.future.constants;

/**
 * @Description TODO
 * @Author Yajun.Zhang
 * @Date 2020/5/2 20:44
 */
public class FutureConstant {


    public static final String ONLY_FILE_SUPPORT = "only support : jpg,png,fdg,svg";
    public static final String FILE_SIZE_MAX = "file size max 5M";

    /*
     minio 桶名
     */
    public static final String MINIO_BUCKET_NAME_FUTURE = "future";
    public static final String MINIO_BUCKET_NAME_USER = "user";
    public static final String MINIO_BUCKET_NAME_KYC = "kyc";
    public static final String MINIO_BUCKET_NAME_PDF = "pdf";


    // =====公共状态===== //
    // 启用
    public static final int COMMONT_STATUS_SUCCESS = 1;
    // 停用
    public static final int COMMONT_STATUS_NO_SUCCESS = 2;
    // 是否删除  是
    public static final int COMMONT_IS_DELETE_SUCCESS = 1;
    // 是否删除  否
    public static final int COMMONT_IS_DELETE_NO_SUCCESS = 2;

    /**
     * 公章图片ID
     */
    public static final String OLAP_BH_EMAIL_PICTURE_ID = "image";

    /**
     * 公章图片ID
     */
    public static final String OLAP_NP_EMAIL_PICTURE_ID = "image";


    public static final String OLAP_BH_EMAIL_PICTURE_ADDRESS = System.getProperty("user.dir") + "\\future1.png";

    public static final String OLAP_BH_EMAIL_PICTURE_FILE = System.getProperty("user.dir") + "\\future2.png";
    public static final String OLAP_BH_EMAIL_PICTURE_TITLE = System.getProperty("user.dir") + "\\future3.png";


    public static final String OLAP_NP_EMAIL_PICTURE_ADDRESS = System.getProperty("user.dir") + "\\future1.png";


    public static final String OLAP_FTP_PATH = "FUTURE/";

    public static final String SLASH = "/";

    /**
     * 标识
     */
    public static final String FUTURE_COMMISSION = "FUTURE";
    /**
     *邮件签名
     */
    public static final String FUTURE_AC_NAME = "东方鲤鱼团队";

    public static final String OLAP_NP_OTHER_ADDRESS = "东方鲤鱼团队：中国 上海 , 联系邮箱：dongfangliyu@gmail.com";


    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";
}
