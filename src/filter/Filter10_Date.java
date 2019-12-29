package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//对指定下的目录进行过滤
//@WebFilter(filterName = "Filter 10",
//urlPatterns = "/*")
public class Filter10_Date implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter10_Date begins");
        HttpServletRequest request = (HttpServletRequest)req;
        String path = request.getRequestURI();
        //创建Date对象，声明变量指向它，获得时间
        Date date = new Date();
        //将日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);
        System.out.println(path+" "+time);
        //放行，执行其他过滤器，若执行完所有过滤器，则执行原请求
        chain.doFilter(req, resp);
        System.out.println("Filter10_Date ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
