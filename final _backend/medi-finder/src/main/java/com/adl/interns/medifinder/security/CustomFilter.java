package com.adl.interns.medifinder.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public class CustomFilter implements Filter{
		
		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			//
		}

		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
				throws IOException, ServletException {
				HttpServletResponse response = (HttpServletResponse) res;
				HttpServletRequest request = (HttpServletRequest) req;
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
				response.setHeader("Access-Control-Max-Age", "3600");
				response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type,X_MSISDN,X-MSISDN,HTTP_X_MSISDN,X-UP-CALLING-LINE-ID,X_UP_CALLING_LINE_ID,HTTP_X_UP_CALLING_LINE_ID,X_WAP_NETWORK_CLIENT_MSISDN,MSISDN,msisdn");
				if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
					response.setStatus(HttpServletResponse.SC_OK);
				} else {
					chain.doFilter(req, res);
				}
			}
			

		public void destroy() {
			//
		}

	}
	
	
	
	
	


