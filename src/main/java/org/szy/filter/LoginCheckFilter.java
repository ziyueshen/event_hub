package org.szy.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.szy.common.BaseContext;
import org.szy.common.R;
import org.szy.utils.JwtUtils;

import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    static AntPathMatcher PATH_MATCH = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin != null ? origin : "*");

        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, X-Requested-With, remember-me, x-token");

        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setHeader("Access-Control-Max-Age", "1800");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        // get request URI
        String urlReq = request.getRequestURI();
        log.info("Check request：{}", urlReq);
        String token = request.getHeader("x-token");

        // urls we don't block
        String[] urls = {
                "/user/login",
                "/common/upload",
                "/common/download"
        };
        for (String url : urls) {
            if (PATH_MATCH.match(url, urlReq)) {
                log.info("No need to block： {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }
        }
        // check token
        if (token != null && !JwtUtils.isTokenExpired(token)) {
            String userId = JwtUtils.getClaimsByToken(token).getSubject();
            BaseContext.setCurrentId(Long.valueOf(userId));

            filterChain.doFilter(request, response);
            return;
        }
        log.info("Not log in");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
}
