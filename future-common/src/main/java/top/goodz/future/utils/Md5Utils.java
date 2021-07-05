package top.goodz.future.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/7/4 21:05
 */
public class Md5Utils {

        /**利用MD5进行加密*/
        public static String EncoderByMd5(String str)  {
            //确定计算方法
            MessageDigest md5= null;
            String newstr= null;
            try {
                md5 = MessageDigest.getInstance("MD5");

            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newstr =   base64en.encode(md5.digest(str.getBytes("utf-8")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return newstr;
        }

        /**判断用户密码是否正确
         *newpasswd 用户输入的密码
         *oldpasswd 正确密码*/
        public static boolean checkPassword(String newPassword,String oldPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
            if(EncoderByMd5(newPassword).equals(oldPassword))
                return true;
            else
                return false;
        }

}
