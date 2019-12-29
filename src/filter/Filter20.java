package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//@WebFilter(filterName = "Filter20",urlPatterns = "/*")
public class Filter20 implements Filter {
    private Set<String> toExclude = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        toExclude.add("/");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter20 _Encoding begins");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        //获得请求的资源
        String path = request.getRequestURI();
        System.out.println("请求的资源为:"+path);
        //获得请求的类型
        String method = request.getMethod();
        //若uri含有"/login"，不设置字符编码
        if(!toExclude.contains(path)){
            if(("post-put").contains(method)){
                System.out.println(method);
                request.setCharacterEncoding("UTF-8");
                System.out.println("设置请求字符为UTF-8");
            }
            //设置所有的请求类型的响应字符为utf8
            response.setContentType("text/html;charset=UTF-8");
            System.out.println("设置响应字符编码为UTF-8");
            //method是 "POST"或“PUT”，则请求和响应字符编码
        }
        //放行，执行其他过滤器，若执行完所有过滤器，则执行原请求
        chain.doFilter(req, resp);
        System.out.println("Filter20_Encoding ends");
    }

    public void destroy() {
    }

}
