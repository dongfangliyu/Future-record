package top.goodz.future.utils;

import com.alibaba.fastjson.JSONException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Created by chen jie on 2016/6/1
 */
public class HttpCloseableUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpCloseableUtils.class);
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int CONNECT_MAX_TIMEOUT = 10 * 1000;
	private static final int CONNECTION_REQUEST_MAX_TIMEOUT = 10 * 1000;
	private static final int SOCKET_MAX_TIMEOUT = 5 * 1000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		/*
		 * 从连接池中获取连接的超时时间，假设：连接池中已经使用的连接数等于setMaxTotal，新来的线程在等待1*1000
		 * 后超时，错误内容：org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting
		 * for connection from pool
		 */
		configBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_MAX_TIMEOUT);
		/*
		 * 这定义了通过网络与服务器建立连接的超时时间。
		 * Httpclient包中通过一个异步线程去创建与服务器的socket连接，这就是该socket连接的超时时间，
		 * 此处设置为2秒。假设：访问一个IP，192.168.10.100，这个IP不存在或者响应太慢，那么将会返回
		 * java.net.SocketTimeoutException: connect timed out
		 */
		configBuilder.setConnectTimeout(CONNECT_MAX_TIMEOUT);
		/*
		 * 指的是连接上一个url，获取response的返回等待时间，假设：url程序中存在阻塞、或者response
		 * 返回的文件内容太大，在指定的时间内没有读完，则出现 java.net.SocketTimeoutException: Read timed out
		 */
		configBuilder.setSocketTimeout(SOCKET_MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param apiUrl
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String apiUrl) throws IOException {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String apiUrl, Map<String, Object> params) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			System.out.println(response.toString());
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	public static boolean isEmpty(Object data) {
		return (data == null) || ("".equals(data.toString().trim()));
	}

	/**
	 * 向指定 URL 发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param paramMap
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, LinkedHashMap<String, String> paramMap) {
		String result = "";
		String param = "";
		BufferedReader in = null;
		try {
			// 加载参数
			if (!isEmpty(paramMap)) {
				Set<String> keys = paramMap.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = String.valueOf(it.next());
					String value = isEmpty(paramMap.get(key)) ? null : paramMap.get(key);
					if (!isEmpty(value)) {
						param += key + "=" + value + "&";
					}
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

	/**
	 * 向指定 URL 发送GET方法的请求，带header信息
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param paramMap
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendGetAddHeader(String url, LinkedHashMap<String, Object> paramMap, String headerKey,
			String headerValue) {
		String result = "";
		String param = "";
		BufferedReader in = null;
		try {
			// 加载参数
			if (ChkUtil.isNotEmpty(paramMap) && paramMap.size() > 0) {
				Set<String> keys = paramMap.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = String.valueOf(it.next());
					Object value = isEmpty(paramMap.get(key)) ? null : paramMap.get(key);
					if (!isEmpty(value)) {
						param += key + "=" + value + "&";
					}
				}
				param = param.substring(0, param.length() - 1);
			}

			String urlName = url + "?" + param;
			System.out.println(urlName);
			URL realUrl = new URL(urlName);

			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("contentType", "UTF-8");
			conn.setRequestProperty(headerKey, headerValue);
			// 建立实际的连接
			conn.connect();
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

	public static String sendPutAddHeader(String path, Object obj, String headerKey, String headerValue)
			throws IOException, JSONException {
		// 创建连接
		URL url = new URL(path);
		HttpURLConnection connection;
		StringBuffer sbuffer = null;
		try {
			// 添加 请求内容
			connection = (HttpURLConnection) url.openConnection();
			// 设置http连接属性
			connection.setDoOutput(true);// http正文内，因此需要设为true, 默认情况下是false;
			connection.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
			connection.setRequestMethod("PUT"); // 可以根据需要 提交 GET、POST、DELETE、PUT等http提供的功能
			// connection.setUseCaches(false);//设置缓存，注意设置请求方法为post不能用缓存
			// connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");// 设定 请求格式
																								// json，也可以设定xml格式的
			connection.setRequestProperty("Accept-Charset", "utf-8"); // 设置编码语言
			connection.setRequestProperty("Connection", "keep-alive"); // 设置连接的状态
			connection.setRequestProperty("Transfer-Encoding", "chunked");// 设置传输编码
			connection.setRequestProperty("Content-Length", obj.toString().getBytes().length + ""); // 设置文件请求的长度  
			connection.setReadTimeout(10000);// 设置读取超时时间  
			connection.setConnectTimeout(10000);// 设置连接超时时间  
			connection.setRequestProperty(headerKey, headerValue);
			connection.connect();
			OutputStream out = connection.getOutputStream();// 向对象输出流写出数据，这些数据将存到内存缓冲区中  
			out.write(obj.toString().getBytes()); // out.write(new String("测试数据").getBytes()); //刷新对象输出流，将任何字节都写入潜在的流中  
			out.flush();
			// 关闭流对象,此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中  
			out.close();
			// 读取响应  
			if (connection.getResponseCode() == 200) {
				// 从服务器获得一个输入流
				InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());// 调用HttpURLConnection连接对象的getInputStream()函数,
																									// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
				BufferedReader reader = new BufferedReader(inputStream);
				String lines;
				sbuffer = new StringBuffer("");
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbuffer.append(lines);
				}
				reader.close();
			} else {
				logger.info("接口调用失败");
			}
			// 断开连接  
			connection.disconnect();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return sbuffer.toString();
	}

	/**
	 * @Description: NameValuePair方式的put请求
	 * @param url
	 * @param list
	 * @param headerKey
	 * @param headerValue
	 * @return
	 * @throws IOException 
	 * @throws 
	 * @author yajun.Zhang 
	 * @createTime： 2019年8月22日 上午11:10:48
	 */
	public static String doPut(String url, List<NameValuePair> list, String headerKey, String headerValue) throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(connMgr).build();
		String httpStr = null;
		HttpPut httpPut = new HttpPut(url);
		CloseableHttpResponse response = null;
		UrlEncodedFormEntity entity = null;
		try {
			httpPut.setConfig(requestConfig);
			httpPut.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPut.addHeader("Accept-Language", "zh-cn");
			httpPut.addHeader(headerKey, headerValue);
			entity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
			httpPut.setEntity(entity);
			response = httpClient.execute(httpPut);
			HttpEntity httpEntity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 返回结果
		return httpStr;
	}

	/**
	 * 向指定 URL 发送GET方法的请求，带header信息
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param strs
	 *            请求参数，请求参数应该是 /{abc}/{def} 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendGetSlashAddHeader(String url, List<String> strs, String headerKey, String headerValue) {
		String result = "";
		String param = "";
		BufferedReader in = null;
		try {
			// 加载参数
			if (!isEmpty(strs)) {
				for (String str : strs) {
					param = "/" + str;
				}
			}
			String urlName = url + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("contentType", "UTF-8");
			conn.setRequestProperty(headerKey, headerValue);
			// 建立实际的连接
			conn.connect();
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

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 * @throws IOException
	 */
	public static String doPostMap(String apiUrl, Map<String, String> params) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			System.out.println(response.toString());
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String apiUrl, Object json, String charset, long receiveTimeout) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 請求超時
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), charset);// 解决中文乱码问题
			stringEntity.setContentEncoding(charset);
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, charset);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 * @throws IOException
	 */
	public static String doPostJSON(String apiUrl, Object json) throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connMgr).build();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式，新增一个header
	 * 
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 * @throws IOException
	 */
	public static String doPostJSONAddHeader(String apiUrl, Object json, String headerKey, String headerValue)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connMgr).build();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.addHeader(headerKey, headerValue);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}
	
	
	public static String doPostNameValuePairAddHeader(String apiUrl, List<NameValuePair> list, String headerKey, String headerValue)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connMgr).build();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		UrlEncodedFormEntity entity = null;
		try {
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.addHeader("Accept-Language", "zh-cn");
			httpPost.addHeader(headerKey, headerValue);
			entity = new UrlEncodedFormEntity(list, Charset.forName("UTF-8"));
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(httpEntity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}
	
//	public static void main(String[] args) throws Exception {
//		List<NameValuePair> list = new ArrayList<NameValuePair>();
//		list.add(new BasicNameValuePair("client_id", "saber"));					
//		list.add(new BasicNameValuePair("tenantId", "000000"));					
//		list.add(new BasicNameValuePair("channelCode", "zw0001"));					
//		list.add(new BasicNameValuePair("username", "15305530219"));					
//		list.add(new BasicNameValuePair("password", "admin"));					
//		list.add(new BasicNameValuePair("grant_type", "password"));					
//		list.add(new BasicNameValuePair("scope", "all"));					
//		list.add(new BasicNameValuePair("type", "account"));					
//		doPostNameValuePairAddHeader("http://localhost:8100/oauth/token", list, "authorization" , "Basic c2FiZXI6c2FiZXJfc2VjcmV0");
//	}
	
	/**
	 * @Description: delete请求
	 * @param apiUrl
	 * @param headerKey
	 * @param headerValue
	 * @return
	 * @throws IOException 
	 * @throws 
	 * @author yajun.Zhang 
	 * @createTime： 2019年8月22日 上午11:24:45
	 */
	public static String doDeleteAddHeader(String apiUrl, String headerKey, String headerValue)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connMgr).build();
		String httpStr = null;
		HttpDelete httpDelete = new HttpDelete(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpDelete.setConfig(requestConfig);
			httpDelete.addHeader(headerKey, headerValue);
			response = httpClient.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * @Description: 
	 * @param apiUrl
	 * @param json
	 * @param headerKey
	 * @param headerValue
	 * @return
	 * @throws IOException 
	 * @throws 
	 * @author yajun.Zhang 
	 * @createTime： 2019年8月22日 下午2:48:01
	 */
	public static String doPostFormddHeader(String apiUrl, Object json, String headerKey, String headerValue)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(connMgr).build();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setHeader(headerKey, headerValue);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			System.out.println("http请求返回码：" + response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
			logger.info("http最原始返回数据为{}", httpStr);
		} catch (Exception e) {
			String errMsg = "{\"code\":\"-1\",\"error\":\"请求异常\"}";
			logger.error(errMsg, e);
			httpStr = errMsg;
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error("请求IO异常", e);
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");

			logger.info("http最原始返回数据为{}", httpStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param xml
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSLXml(String apiUrl, Object xml) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(xml.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("text/xml");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");

			logger.info("http最原始返回数据为{}", httpStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSLOnHeader(String apiUrl, Object json, Map<String, String> header) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;
		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			if (header != null && !header.isEmpty()) {
				Set<String> keys = header.keySet();
				for (String key : keys) {
					httpPost.setHeader(key, header.get(key));
				}
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
			logger.info("http最原始返回数据为{}", httpStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			// 创建SSL连接套接字工厂
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}
}
