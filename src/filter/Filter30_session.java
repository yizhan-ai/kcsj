package filter;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//@WebFilter(filterName = "Filter 30",urlPatterns = "/*")
public class Filter30_session implements Filter {
    private Set<String> toExclude = new HashSet<>();
    public void init(FilterConfig config) throws ServletException {
        toExclude.add("/login.ctl");
        toExclude.add("userRoleAss.ctl");
        toExclude.add("/logout.ctl");
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter30_session begins");
        HttpServletRequest request1 = (HttpServletRequest)request;
        HttpServletResponse response1 = (HttpServletResponse)response;
        String path = request1.getRequestURI();
        JSONObject message = new JSONObject();
        if(!toExclude.contains(path)) {
            HttpSession session = request1.getSession(false);
            if (session==null || session.getAttribute("user")==null){
                message.put("message","您没有登录，请登录");
                response1.getWriter().println(message);
                System.out.println("您没有登录，请登录");
                return;
            }
        }
        chain.doFilter(request,response);
        System.out.println("Filter30_session ends");
    }
    public void destroy() {

    }
}
