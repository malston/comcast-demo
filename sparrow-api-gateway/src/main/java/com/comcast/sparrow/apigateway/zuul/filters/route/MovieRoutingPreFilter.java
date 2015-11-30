package com.comcast.sparrow.apigateway.zuul.filters.route;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator.ProxyRouteSpec;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
@RefreshScope
public class MovieRoutingPreFilter extends ZuulFilter {

	private final ProxyRouteLocator routeLocator;

	private final UrlPathHelper urlPathHelper;

	@Value("${catalog.genreIdRange}")
	private String genreIdRange;
	
	@Value("${catalog.serviceId}")
	private String catalogServiceId;
	
	@Autowired
	public MovieRoutingPreFilter(ProxyRouteLocator routeLocator) {
		this.routeLocator = routeLocator;
		this.urlPathHelper = new UrlPathHelper();
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		final String requestURI = this.urlPathHelper.getPathWithinApplication(ctx
				.getRequest());
		ProxyRouteSpec route = this.routeLocator.getMatchingRoute(requestURI);
		return StringUtils.startsWithIgnoreCase(route.getPath(), "/movies");
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		final String requestURI = this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
		ProxyRouteSpec route = this.routeLocator.getMatchingRoute(requestURI);
		System.out.println("URI ----" + requestURI);
		System.out.println("Matching Route ----" + route.getPath());

		String pathInfo = route.getPath();
		
		String[] tokens = StringUtils.tokenizeToStringArray(pathInfo, "/");
		
		final String genreMlId = tokens[2];
		
		List<String> genreMlIdRange = Arrays.asList(StringUtils.commaDelimitedListToStringArray(genreIdRange));

		if (genreMlIdRange.contains(genreMlId)) {
			String location = catalogServiceId;
			ctx.set("serviceId", location);
			ctx.setRouteHost(null);
			ctx.setSendZuulResponse(true);
		}

		return null;
	}

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 5;
	}
}
