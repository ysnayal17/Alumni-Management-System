package com.alumnimanagement.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class HttpRequestFilterConfig extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
        httpResponse.setHeader(
                "Access-Control-Allow-Headers",
                "Content-Type, Accept, X-Requested-With, userId, Authorization, Refresh-Token");

//        try {
//            String encodedToken = httpRequest.getHeader(GlobalConstants.Header.AUTHORIZATION);
//            String host = httpRequest.getHeader(GlobalConstants.Header.HOST);
//            if (!host.contains(GlobalConstants.Header.DEV_MODE) && !host.contains(GlobalConstants.Header.CORE_MICROSERVICE) && StringUtil.nonNullOrEmpty(encodedToken)) {
//                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Request originated from unauthorised host");
//            } else if (StringUtil.nonNullOrEmpty(encodedToken) && tokenBlackListService.isTokenExpired(encodedToken)) {
//                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token revoked");
//            } else {
//                if (!httpRequest.getRequestURI().startsWith("/swagger-ui.html")) {
//                    if (StringUtil.nonNullOrEmpty(encodedToken)) {
//                        ExecutionContext.current().init(SecurityContextHolder.getContext().getAuthentication());
//                    } else {
//                        ExecutionContext.current().init(httpRequest);
//                    }
//                }
//                httpResponse.setHeader(GlobalConstants.REQUEST_ID, ExecutionContext.current().getRequestId());
                chain.doFilter(request, response);
//            }
//        } catch (UnAuthorizedException ex) {
//            throw ex;
//        } catch (Exception u) {
//            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, u.getLocalizedMessage());
//        }
    }
}
