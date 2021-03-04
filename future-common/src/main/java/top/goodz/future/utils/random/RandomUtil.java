package top.goodz.future.utils.random;

import java.util.Random;

/**
 * 生成随机数
 *
 * @author zhangyajun
 */
public class RandomUtil {
    /**
     * 产生随机字符串 *
     */
    private static Random randGen = null;
    private static Random randGenChar = null;
    private static char[] numbersAndLetters = null;
    private static char[] numbersAndLettersChar = null;

    /**
     * 生成随机字符串【0-9】
     *
     * @param length 长度
     * @return
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
        }
        return new String(randBuffer);
    }

    /**
     * 生成随机字符串【0-9】【a-z】【A-Z】
     *
     * @param length 长度
     * @return
     */
    public static final String randomCharString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGenChar == null) {
            randGenChar = new Random();
            numbersAndLettersChar = ("0123456789abcdefghijklmnopqrstuvwxyz"
                    + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLettersChar[randGenChar.nextInt(71)];
        }
        return new String(randBuffer);
    }

    /**
     * 创建token
     *
     * @return
     */
    public static synchronized String createToke() {
        return new java.math.BigInteger(165, new Random()).toString(36).toUpperCase();
    }
}
