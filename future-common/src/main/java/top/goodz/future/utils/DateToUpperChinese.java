package top.goodz.future.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期转成中文
 * @author Yajun.Zhang
 */
public class DateToUpperChinese {
	
	private static final String[] NUMBERS = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	/** 通过 yyyy-MM-dd 得到中文大写格式 yyyy MM dd 日期 */
	public static synchronized String toChinese(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(getSplitDateStr(str, 0)).append("年").append(getSplitDateStr(str, 1)).append("月")
				.append(getSplitDateStr(str, 2)).append("日");
		return sb.toString();
	}

	/** 分别得到年月日的大写 默认分割符 "-" */
	public static String getSplitDateStr(String str, int unit) {
		// unit是单位 0=年 1=月 2日
		String[] DateStr = str.split("-");
		if (unit > DateStr.length)
			unit = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < DateStr[unit].length(); i++) {
			if ((unit == 1 || unit == 2) && Integer.valueOf(DateStr[unit]) > 9) {
				sb.append(convertNum(DateStr[unit].substring(0, 1))).append("十")
						.append(convertNum(DateStr[unit].substring(1, 2)));
				break;
			} else {
				sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
			}
		}
		if (unit == 1 || unit == 2) {
			return sb.toString().replaceAll("^一", "").replace("〇", "");
		}
		return sb.toString();

	}

	/** 转换数字为大写 */
	private static String convertNum(String str) {
		return NUMBERS[Integer.valueOf(str)];
	}

	/** 判断是否是零或正整数 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static void main(String args[]) {

		System.out.println(toChinese("2018-10-30"));

	}
}
