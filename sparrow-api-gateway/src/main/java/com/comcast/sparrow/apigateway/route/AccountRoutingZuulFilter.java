package com.comcast.sparrow.apigateway.route;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulHeaders;
import com.netflix.zuul.context.RequestContext;

/**
 * @author psingh200
 *
 */
@Component
public class AccountRoutingZuulFilter extends ZuulFilter {

	private final ProxyRouteLocator routeLocator;

	private final UrlPathHelper urlPathHelper;

	private final ZuulProperties zuulProperties;

	private final String accountNumberPattern;

	private final String accountServiceId;

	private final String telephoneNumberPattern;

	private final String telephoneServiceId;

	@Autowired
	public AccountRoutingZuulFilter(ProxyRouteLocator routeLocator, ZuulProperties zuulProperties,
			@Value(value = "accountNumberPattern") String accountNumberPattern, @Value(value = "accountNumber.serviceId") String accountServiceId,
			@Value(value = "telephoneNumberPattern") String telephoneNumberPattern, @Value(value = "telephoneNumber.serviceId") String telephoneServiceId) {
		this.routeLocator = routeLocator;
		this.urlPathHelper = new UrlPathHelper();
		this.zuulProperties = zuulProperties;
		this.accountNumberPattern = accountNumberPattern;
		this.accountServiceId = accountServiceId;
		this.telephoneNumberPattern = telephoneNumberPattern;
		this.telephoneServiceId = telephoneServiceId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		final String accountNumber = request.getParameter("accountNumber");
		final String telephoneNumber = request.getParameter("telephoneNumber");
		System.out.println("***************Zuul Proxy accountNumber = '" + accountNumber + "'");
		System.out.println("***************Zuul Proxy telephoneNumber = '" + telephoneNumber + "'");

		return accountNumber != null && telephoneNumber != null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		final String requestURI = this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
		System.out.println("URI ----" + requestURI);

		HttpServletRequest request = ctx.getRequest();

		for (Enumeration<String> headerNames = request.getHeaderNames(); headerNames.hasMoreElements();) {
			System.out.println("Zuul Proxy Header route: " + headerNames.nextElement());
		}
		final String accountNumber = request.getParameter("accountNumber");
		final String telephoneNumber = request.getParameter("telephoneNumber");

		if (StringUtils.startsWithIgnoreCase(accountNumber, accountNumberPattern)) {
			String location = this.accountServiceId;
			ctx.setRouteHost(null);
//			ctx.setRouteHost(getUrl(location));
			ctx.addOriginResponseHeader("X-Zuul-ServiceId", location);
			ctx.addOriginResponseHeader("X-Zuul-Service", location);
		} else if (StringUtils.startsWithIgnoreCase(telephoneNumber, telephoneNumberPattern)) {
			String location = this.telephoneServiceId;
			ctx.setRouteHost(null);
//			ctx.setRouteHost(getUrl(location));
			ctx.addOriginResponseHeader("X-Zuul-ServiceId", location);
			ctx.addOriginResponseHeader("X-Zuul-Service", location);
		}
		
		if (this.zuulProperties.isAddProxyHeaders()) {
			ctx.addZuulRequestHeader("X-Forwarded-Host",
					ctx.getRequest().getServerName() + ":"
							+ String.valueOf(ctx.getRequest().getServerPort()));
			ctx.addZuulRequestHeader(ZuulHeaders.X_FORWARDED_PROTO,
					ctx.getRequest().getScheme());
//			if (StringUtils.hasText(route.getPrefix())) {
//				ctx.addZuulRequestHeader("X-Forwarded-Prefix", route.getPrefix());
//			}
		}

		return null;
	}

//	private void customRouting(RequestContext ctx) throws IOException {
//		// TODO Auto-generated method stub
//
//		HttpServletRequest request = ctx.getRequest();
//
//		System.out.println("---------------------111-----------------" + request.getContextPath());
//
//		System.out.println("---------------------111-----------------" + request.getRequestURL());
//
//		StringBuilder stringBuilder = new StringBuilder();
//
//		InputStream xml = request.getInputStream();
//		InputStream inputStream = request.getInputStream();
//		BufferedReader bufferedReader = null;
//		if (inputStream != null) {
//			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//			char[] charBuffer = new char[128];
//			int bytesRead = -1;
//			while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
//				stringBuilder.append(charBuffer, 0, bytesRead);
//			}
//		} else {
//			stringBuilder.append("");
//		}
//
//		System.out.println("--------------------stringBuilder-------stringBuilder-----------" + stringBuilder);
//
//		ObjectMapper mapper = new ObjectMapper();
//		Customer customer = mapper.readValue(stringBuilder.toString(), Customer.class);
//
//		System.out.println("--------------------customer-------customer-----------" + customer.getAccountNumber()
//				+ " -- Customer TN" + customer.getTn());
//
//		if (org.apache.commons.lang3.StringUtils.startsWith(customer.getTn(), "303")) {
//
//			final String requestURI = "/customer";//
//			this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
//			ProxyRouteSpec route = this.routeLocator.getMatchingRoute(requestURI);
//
//			ctx.put("requestURI", route.getPath());
//			ctx.put("proxy", route.getId());
//
//			System.out.println(
//					"--------------------ProxyRouteSpec-------route.getLocation-----------" + route.getLocation());
//
//			System.out.println("--------------------ProxyRouteSpec-------route.getPath-----------" + route.getPath());
//
//			System.out.println(
//					"--------------------ProxyRouteSpec-------route.getPath-----------" + route.getRetryable());
//
//			if (route != null) {
//				String location = route.getLocation();
//
//				if (route.getRetryable() != null) {
//					ctx.put("retryable", route.getRetryable());
//				}
//
//				if (location.startsWith("http:") || location.startsWith("https:")) {
//					ctx.setRouteHost(getUrl(location));
//					ctx.addOriginResponseHeader("X-Zuul-Service", location);
//				} else if (location.startsWith("forward:")) {
//					ctx.set("forward.to",
//							StringUtils.cleanPath(location.substring("forward:".length()) + route.getPath()));
//					ctx.setRouteHost(null);
//
//				}
//
//			}
//
//		} else if (org.apache.commons.lang3.StringUtils.startsWith(customer.getTn(), "505")) {
//
//			final String requestURI = "/store";//
//			this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
//			ProxyRouteSpec route = this.routeLocator.getMatchingRoute(requestURI);
//
//			ctx.put("requestURI", route.getPath());
//			ctx.put("proxy", route.getId());
//			System.out.println(
//					"--------------------ProxyRouteSpec-------route.getLocation-----------" + route.getLocation());
//
//			System.out.println("--------------------ProxyRouteSpec-------route.getPath-----------" + route.getPath());
//
//			System.out.println(
//					"--------------------ProxyRouteSpec-------route.getPath-----------" + route.getRetryable());
//
//			if (route != null) {
//				String location = route.getLocation();
//
//				if (route.getRetryable() != null) {
//					ctx.put("retryable", route.getRetryable());
//				}
//
//				if (location.startsWith("http:") || location.startsWith("https:")) {
//					ctx.setRouteHost(getUrl(location));
//					ctx.addOriginResponseHeader("X-Zuul-Service", location);
//				} else if (location.startsWith("forward:")) {
//					ctx.set("forward.to",
//							StringUtils.cleanPath(location.substring("forward:".length()) + route.getPath()));
//					ctx.setRouteHost(null);
//
//				}
//
//			}
//
//		}
//
//		/*
//		 * ctx.put("requestURI", route.getPath()); ctx.put("proxy",
//		 * route.getId());
//		 */
//
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		return "route";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.netflix.zuul.ZuulFilter#filterOrder()
	 */
	@Override
	public int filterOrder() {
		return 5;
	}

	private URL getUrl(String target) {
		try {
			return new URL(target);
		} catch (MalformedURLException ex) {
			throw new IllegalStateException("Target URL is malformed", ex);
		}
	}
}
