package top.goodz.future.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 功能说明：HTTP操作类 典型用法： 特殊用法：
 *
 * @author panye 修改人: 修改原因： 修改时间： 修改内容： 创建日期：2015-6-10 Copyright zzl-apt
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     *  功能说明：POST 请求得到数据  panye  2015-6-10  @param   @return
     *   返回得到的结果集，如果出现异常或者连接超时 则返回空  @throws  该方法可能抛出的异常，异常的类型、含义。 最后修改时间：
     * 修改人：panye 修改内容： 修改注意点：
     */
    public static String sendPost(String url, Map<String, Object> paramMap) {
        HttpPost httpRequset = null;
        // 取得HttpClient
        HttpClient httpClient = null;
        // 使用NameValuePair来保存要传递的Post参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // 加载参数
        if (paramMap != null) {
            Set<String> keys = paramMap.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                params.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
            }
        }
        try {
            httpRequset = new HttpPost(url);
            httpClient = new DefaultHttpClient();
            // 设置字符集
            HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
            // 请求httpRequset
            httpRequset.setEntity(httpentity);
            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 100000);
            // 取得HttpResponse
            HttpResponse httpResponse = httpClient.execute(httpRequset);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return "";
            }
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {
                return EntityUtils.toString(httpResponse.getEntity());
            }

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "";
        } finally {
            if (httpRequset != null) {

                httpRequset.releaseConnection();
            }
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();

            }
        }

        return "";
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url          发送请求的 URL
     * @param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, LinkedHashMap<String, Object> paramMap) {
        String result = "";
        String param = "";
        BufferedReader in = null;
        try {
            // 加载参数
            if (!ChkUtil.isEmpty(paramMap)) {
                Set<String> keys = paramMap.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    String value = ChkUtil.isEmpty(paramMap.get(key)) ? null : paramMap.get(key).toString();
                    if (!ChkUtil.isEmpty(value))
                        param += key + "=" + value + "&";
                }
                param = param.substring(0, param.length() - 1);
            }

            String urlName = url + "?" + param;
            URL realUrl = new URL(urlName);

            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("contentType", "UTF-8");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
//			Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.warn(ex.getMessage(), ex);
            }
        }
        return result;
    }

	/*public static void main(String[] args) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("wd","cf");
		try {
			// sendPost("http://v3.zzlgroup.com/erpv3_client/notice/boc/toNoticeTransferEfBoc.html",paramMap);
			 System.out.println(sendGet("www.baidu.com",map));
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}*/

}
