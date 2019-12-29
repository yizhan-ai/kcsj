package controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Manager;
import service.ManagerService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@WebServlet("/manager.ctl")
public class ManagerController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
       response.setContentType("text/html;charset=UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String manager_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Manager对象
        Manager managerToAdd = JSON.parseObject(manager_json, Manager.class);

        //响应
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //增加加Manager对象
            ManagerService.getInstance().add(managerToAdd);
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
            ManagerService.getInstance().delete(id);
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
        String manager_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Manager对象
        Manager managerToAdd = JSON.parseObject(manager_json, Manager.class);

        //响应
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //增加加Manager对象
            ManagerService.getInstance().update(managerToAdd);
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
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有系对象，否则响应id指定的系对象
            if(id_str == null){
                responseManagers(response);
            }else {
                int id = Integer.parseInt(id_str);
                responseManager(id, response);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            response.getWriter().println(message);
        }
    }

    //响应一个Manager对象
    private void responseManager(int id, HttpServletResponse response)
            throws IOException, SQLException {
        //根据id查找manager
        Manager manager = ManagerService.getInstance().find(id);
        //将Manager对象转换为JSON型
        String manager_json = JSON.toJSONString(manager);
        //响应
        response.getWriter().println(manager_json);
    }
    //响应所有Manager对象
    private void responseManagers(HttpServletResponse response)
            throws IOException, SQLException {
        //获得所有Manager对象
        Collection<Manager> managers = ManagerService.getInstance().findAll();
        //将Manager对象转换为JSON型
        String managers_str = JSON.toJSONString(managers, SerializerFeature.DisableCircularReferenceDetect);
        //响应
        response.getWriter().println(managers_str);
    }
}