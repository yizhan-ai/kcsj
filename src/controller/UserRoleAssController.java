package controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.UserRoleAss;
import service.UserRoleAssService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@WebServlet("/userRoleAss.ctl")
public class UserRoleAssController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
       response.setContentType("text/html;charset=UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String userRoleAss_json = JSONUtil.getJSON(request);
        //将JSON字串解析为UserRoleAss对象
        UserRoleAss userRoleAssToAdd = JSON.parseObject(userRoleAss_json, UserRoleAss.class);

        //响应
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //增加加UserRoleAss对象
            UserRoleAssService.getInstance().add(userRoleAssToAdd);
            message.put("message", "增加成功");
            response.getWriter().println(message);
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        //将id转换为int型
        int id = Integer.parseInt(id_str);
        //响应
        //设置页面内容是html，编码格式是utf8
        response.setContentType("text/html;charset=UTF8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //到数据库表中删除对应的学院
            UserRoleAssService.getInstance().delete(id);
           // message.put("data",null);
            message.put("message", "删除成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String userRoleAss_json = JSONUtil.getJSON(request);
        //将JSON字串解析为UserRoleAss对象
        UserRoleAss userRoleAssToAdd = JSON.parseObject(userRoleAss_json, UserRoleAss.class);

        //响应
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //增加加UserRoleAss对象
            UserRoleAssService.getInstance().update(userRoleAssToAdd);
            message.put("message","更改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        String userId = request.getParameter("userId");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有系对象，否则响应id指定的系对象
            if(id_str == null&&userId == null){
                responseUserRoleAsss(response);
            }else {
                if(userId==null) {
                    int id = Integer.parseInt(id_str);
                    responseUserRoleAss(id, response);
                }else {
                    responseAllByUser(Integer.parseInt(userId), response);
                }
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            response.getWriter().println(message);
        }
    }

    private void responseAllByUser(int userId, HttpServletResponse response)
            throws IOException, SQLException {
        //根据id查找userRoleAss
        UserRoleAss userRoleAss = UserRoleAssService.getInstance().findByUser(userId);
        //将UserRoleAss对象转换为JSON型
        String userRoleAss_json = JSON.toJSONString(userRoleAss);
        //响应
        response.getWriter().println(userRoleAss_json);
    }
    //响应一个UserRoleAss对象
    private void responseUserRoleAss(int id, HttpServletResponse response)
            throws IOException, SQLException {
        //根据id查找userRoleAss
        UserRoleAss userRoleAss = UserRoleAssService.getInstance().find(id);
        //将UserRoleAss对象转换为JSON型
        String userRoleAss_json = JSON.toJSONString(userRoleAss);
        //响应
        response.getWriter().println(userRoleAss_json);
    }
    //响应所有UserRoleAss对象
    private void responseUserRoleAsss(HttpServletResponse response)
            throws IOException, SQLException {
        //获得所有UserRoleAss对象
        Collection<UserRoleAss> userRoleAsss = UserRoleAssService.getInstance().findAll();
        //将UserRoleAss对象转换为JSON型
        String userRoleAsss_str = JSON.toJSONString(userRoleAsss, SerializerFeature.DisableCircularReferenceDetect);
        //响应
        response.getWriter().println(userRoleAsss_str);
    }
}