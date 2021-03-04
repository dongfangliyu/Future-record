package top.goodz.future.gateway.filter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.server.ServerWebExchange;

@AllArgsConstructor
public class RemoteUtil {
	
	private static final String unknown = "unknown";

	private final DiscoveryClient discoveryClient;
	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(ServerWebExchange exchange) {
		String ip = "";
		try {
			ip = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
				if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
					ip = exchange.getRequest().getHeaders().getFirst("Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
					ip = exchange.getRequest().getHeaders().getFirst("WL-Proxy-Client-IP");
				}
				if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
					ip = exchange.getRequest().getHeaders().getFirst("HTTP_CLIENT_IP");
				}
				if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
					ip = exchange.getRequest().getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
				}
				if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
					ip = exchange.getRequest().getRemoteAddress().getHostString();
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
			e.printStackTrace();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}

}
