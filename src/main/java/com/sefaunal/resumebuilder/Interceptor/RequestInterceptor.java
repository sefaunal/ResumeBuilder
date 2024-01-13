package com.sefaunal.resumebuilder.Interceptor;

import com.sefaunal.resumebuilder.Utils.CommonUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
public class RequestInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        // Log user's mail address if logged in
        LOG.info("Username: {}", CommonUtils.getUserInfo());

        // Log requested method type
        String method = request.getMethod();
        LOG.info("Requested Method Type: " + method);

        // Log requested URI
        String uri = request.getRequestURI();
        LOG.info("Requested URI: " + uri);

        // Log user's environment
        LOG.info("User's Environment: " + CommonUtils.getUserEnvironment(request));

        // Log user's IP address
        LOG.info("User's IP Address: {}", CommonUtils.getIpAddress(request));

        return true;
    }
}