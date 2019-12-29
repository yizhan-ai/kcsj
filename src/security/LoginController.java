package security;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String user_json = JSONUtil.getJSON(request);
        User userToLogin = JSON.parseObject(user_json, User.class);
        JSONObject message = new JSONObject();
        try {
            User user = UserService.getInstance().login(userToLogin);
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.getWriter().println(JSON.toJSONString(user));
                return;
            }
            message.put("message", "用户名或密码错误");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }

        response.getWriter().println(message);
    }
    }

