package top.goodz.future.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChkUtil {
    private static final String unknown = "unknown";
    public static final String EXP_A_Z_A = "[a-z]*";
    public static final String EXP_A_Z = "[A-Z]*";
    public static final String EXP_A_Z_A_Z = "[a-zA-Z]*";
    public static final String EXP_0_9 = "[0-9]*";
    public static final String EXP_0_10 = "[0-9]+(\\.[0-9]+)?";   //  所有正数包括小数
    public static final String EXP_5_13 = "^\\d{5,13}$";
    public static final String EXP_0_9_A_Z = "[0-9a-z]*";
    public static final String EXP_0_9_A_Z_A_Z = "[0-9a-zA-Z]*";
    public static final String EXP_0_9_A_Z_D_W = "[0-9a-z_]*";
    public static final String EXP_EMAIL = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    public static final String EXP_PRICE = "^([1-9]\\d+|[1-9])(\\.\\d\\d?)*$";
    public static final String EXP_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    public static final String EXP_TEL = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)+\\d{7,8}$";
    public static final String EXP_POSTALCODE = "^[0-9]{6}$";

    public static final String EXP_IP = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    public static final String EXP_URL = "^[a-zA-z]+://[^><\"' ]+";
    public static final String EXP_DATE = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}";
    public static final String EXP_DATETIME = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
    public static final String EXP_DATETIMESECOND = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
    public static final String DATESTRING_TAIL = "000000000";
    public static final String EXP_DATETIMESECOND2 = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[.]{1}[0-9]{1,3}";
    public static final String EXP_JAVA_SCRIPT_ONE = "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']";
    public static final String EXP_JAVA_SCRIPT_TWO = "script";

    public static final String EXP_USERNAME = "^[a-zA-Z0-9_-]{4,30}$";
    public static final String EXP_PWD = "^(?![^a-zA-Z]+$)(?!\\D+$).{6,16}$";

    public static final String EXP_NAME = "^[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*$";
    public static final String EXP_IC_C18 = "^[0-9Xx]{18}$";
    public static final String EXP_IC_C15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";

    public static final String EXP_CAPTCHA = "^[0-9A-Za-z]{4}$";
    public static final String EXP_CAPTCHA_PHONEMSG = "^[0-9]{6}$";
    public static final String EXP_REALNAME = "^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$";

    public static final String EXP_CAR_NO = "^[A-Z_0-9]{5}$"; // 车牌号后5位
    public static final String EXP_NUMBER = "^[0-9]{1,}$";

    public static final String EXP_BANKCARD = "^[0-9]{10,19}$";

    public static final String EXP_SQL_INJECT_ONE = "(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    public static final String[] disableUsernames = new String[]{"51bel", "bel", "beier", "zhongzilian", "zzl",
            "admin", "test"};

    public static final String EXP_WEIXIN = "^[a-zA-Z\\d_-]{6,20}$";

    public static final String EXP_YYYY_MM_DD = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))?$";

    public static void main(String[] args) {

        System.out.println(isLicense18("91210231693282716J"));

    }

    /**
     * 营业执照 统一社会信用代码（15位）
     *
     * @param license
     * @return
     */
    public static boolean isLicense15(String license) {
        if (isEmpty(license)) {
            return false;
        }
        if (license.length() != 15) {
            return false;
        }

        String businesslicensePrex14 = license.substring(0, 14);// 获取营业执照注册号前14位数字用来计算校验码
        String businesslicense15 = license.substring(14, license.length());// 获取营业执照号的校验码
        char[] chars = businesslicensePrex14.toCharArray();
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
        }
        getCheckCode(ints);
        if (businesslicense15.equals(getCheckCode(ints) + "")) {// 比较 填写的营业执照注册号的校验码和计算的校验码是否一致
            return true;
        }
        return false;
    }

    /**
     * 获取 营业执照注册号的校验码
     *
     * @param ints
     * @return
     */
    private static int getCheckCode(int[] ints) {
        if (null != ints && ints.length > 1) {
            int ti = 0;
            int si = 0;// pi|11+ti
            int cj = 0;// （si||10==0？10：si||10）*2
            int pj = 10;// pj=cj|11==0?10:cj|11
            for (int i = 0; i < ints.length; i++) {
                ti = ints[i];
                pj = (cj % 11) == 0 ? 10 : (cj % 11);
                si = pj + ti;
                cj = (0 == si % 10 ? 10 : si % 10) * 2;
                if (i == ints.length - 1) {
                    pj = (cj % 11) == 0 ? 10 : (cj % 11);
                    return pj == 1 ? 1 : 11 - pj;
                }
            }
        } // end if
        return -1;
    }

    /**
     * 营业执照 统一社会信用代码（18位）
     *
     * @param license
     * @return
     */
    public static boolean isLicense18(String license) {
        if (isEmpty(license)) {
            return false;
        }
        if (license.length() != 18) {
            return false;
        }
        String regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";
        if (!license.matches(regex)) {
            return false;
        }
        String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";
        int[] ws = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
        String[] codes = new String[2];
        codes[0] = license.substring(0, license.length() - 1);
        codes[1] = license.substring(license.length() - 1, license.length());
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += str.indexOf(codes[0].charAt(i)) * ws[i];
        }
        int c18 = 31 - (sum % 31);
        if (c18 == 31) {
            c18 = 'Y';
        } else if (c18 == 30) {
            c18 = '0';
        }
        if (str.charAt(c18) != codes[1].charAt(0)) {
            return false;
        }
        return true;
    }

    public static boolean isIcCard(String ic) {

        return chkRight(ic, EXP_IC_C15) || chkRight(ic, EXP_IC_C18);

    }

    /**
     *  函数功能说明  替换HTML特殊字符  panye  2014-11-24   修改内容   @param   @return     @throws 
     */
    public static String replaceHtmlJs(String s) {
        if (isEmpty(s))
            return s;

        s = s.replace("&", "&amp");
        s = s.replace("'", "''");
        s = s.replace("<", "&lt");
        s = s.replace(">", "&gt");
        s = s.replace("chr(60)", "&lt");
        s = s.replace("chr(37)", "&gt");
        s = s.replace("\"", "&quot");
        s = s.replace(";", ";");
        s = s.replace("\n", "<br/>");
        s = s.replace(" ", "&nbsp");
        return s;
    }

    /**
     *  后台校验  panye  2014-7-3   修改内容   @param  obj校验对象  @param  regExp 正则
     *  @return   正确 返回true  @throws 
     */
    public static boolean chkRight(Object obj, String regExp) {
        if (isEmpty(obj))
            return false;

        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(obj.toString().trim());
        return matcher.matches();
    }

    /**
     *  函数功能说明  字符串长度验证  panye  2014-9-24   修改内容   @param   @return     @throws 
     */
    public static boolean chkStrLength(int length, String... regExps) {
        if (isEmpty(regExps))
            return true;

        for (String str : regExps) {
            if (!chkStrLength(length, str))
                return false;
        }
        return true;
    }

    /**
     *  函数功能说明  字符串长度验证  panye  2014-9-24   修改内容   @param   @return     @throws 
     */
    public static boolean chkStrLength(int length, String str) {
        if (str != null && str.length() > length) {
            return false;
        }
        return true;
    }

    /**
     *  SQL参数注入   panye  2014-7-3   修改内容   @param regExps 校验数组  @return     @throws 
     */
    public static boolean chkParamSqlInject(String... regExps) {
        if (isEmpty(regExps))
            return true;

        for (String string : regExps) {
            if (!chkParamSqlInject(string))
                return false;
        }
        return true;
    }

    /**
     * SQL参数注入  panye  2014-7-3   修改内容   @param   @return     @throws 
     */
    private static boolean chkParamSqlInject(String regExp) {

        if (ChkUtil.isEmpty(regExp))
            return true;

        regExp = regExp.replaceAll("%", "");
        Pattern sqlPattern = Pattern.compile(EXP_SQL_INJECT_ONE, Pattern.CASE_INSENSITIVE);
        String param = "";
        try {
            param = URLDecoder.decode(regExp, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (sqlPattern.matcher(param).find())
            return false;

        return true;
    }

    // /**
    // *
    // *  判断一些特殊字符  panye  
    // * 2014-6-26   
    // * 修改内容   
    // * @param   
    // * @return     
    // * @throws 
    // */
    // public static boolean chkSqlInject(Object... objs) {
    // // for (Object obj : objs) {
    // // if(!chkSqlInject(obj)){
    // // return false;
    // // }
    // // }
    // return true;
    // }

    /**
     *  同种类型对象 单独正则验证   panye   2014-6-26    修改内容    @param  objs 需要验证的数组对象  @param 
     * regExp 验证正则  @return boolean       @throws 
     */
    public static boolean chkRight(Object[] objs, String regExp) {
        for (Object obj : objs) {
            if (!chkRight(obj, regExp))
                return false;
        }
        return true;
    }

    /**
     *  验证多种正则    panye   2014-6-25    修改内容   
     *
     * @param obj 需要验证的对象  @param regExps 正则数组  @param @return     
     *  @return boolean      
     * @throws null
     */
    public static boolean chkRigth(Object obj, String... regExps) {
        for (String reg : regExps) {
            if (!chkRight(obj, reg))
                return false;
        }
        return true;
    }

    public static boolean isEmptys(Object... args) {
        for (Object data : args) {
            if ((data == null) || ("".equals(data.toString().trim()))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Object data) {
        return (data == null) || ("".equals(data.toString().trim()));
    }

    public static boolean isNotEmpty(Object data) {
        return (data != null) && !("".equals(data.toString().trim())) && !("null".equals(data.toString().trim()));
    }

    public static boolean isEmpty(List data) {
        if (data == null || data.size() == 0 || data.get(0) == null)
            return true;

        return false;
    }

    public static boolean isNotEmpty(List data) {
        if (data != null && data.size() > 0 && data.get(0) != null)
            return true;

        return false;
    }

    public static boolean isStr(String str) {
        return !isEmpty(str);
    }

    public static boolean isMoney(String str) {
        if (isEmpty(str))
            return false;
        try {
            Double.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInteger(String str) {
        if (isEmpty(str))
            return false;
        try {
            Integer.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLong(String str) {
        if (isEmpty(str))
            return false;
        try {
            Long.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        if (isEmpty(str))
            return false;
        try {
            Double.valueOf(str);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isDisableUsername(String username) {
        if (isEmpty(username)) {
            return true;
        }
        for (String disableUsername : disableUsernames) {
            if (username.toLowerCase().indexOf(disableUsername) != -1) {
                return true;
            }
        }

        return false;
    }

    /**
     *  函数功能说明  过滤html标签，获取文本值  关福旺 2015-10-10 19:43:38  修改内容   @param   @return   
     *  @throws 
     */
    public static String filterHtml(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_nbsp;
        Matcher m_nbsp;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

            String regEx_nbsp = "&nbsp;"; // 定义&nbsp;标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_nbsp = Pattern.compile(regEx_nbsp, Pattern.CASE_INSENSITIVE);
            m_nbsp = p_nbsp.matcher(htmlStr);
            htmlStr = m_nbsp.replaceAll(""); // 过滤nbsp标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符串
    }

    // 判断输入的String类型是否全是数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(EXP_0_10);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = "";
        try {
            ip = request.getHeader("X-Forwarded-For");

            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = (String) ips[index];
                    if (!(unknown.equalsIgnoreCase(strIp))) {
                        ip = strIp;
                        break;
                    }
                }
            }
        } catch (Exception e) {

        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
