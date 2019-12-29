package controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.CheckIn;
import service.CheckInService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@WebServlet("/checkIn.ctl")
public class CheckInController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String checkIn_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        CheckIn checkInToAdd = JSON.parseObject(checkIn_json, CheckIn.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Department对象
        try {
            CheckInService.getInstance().add(checkInToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            CheckInService.getInstance().delete(id);
            message.put("message", "删除成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            e.printStackTrace();
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
        String checkIn_json = JSONUtil.getJSON(request);
        //将JSON字串解析为CheckIn对象
        CheckIn checkInToAdd = JSON.parseObject(checkIn_json, CheckIn.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Department对象对应的记录
        try {
            CheckInService.getInstance().update(checkInToAdd);
            message.put("message", "更改成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            e.printStackTrace();
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
        //读取参数teacher_id
        String adminId = request.getParameter("adminId");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null和teacherId = null, 表示响应所有系对象
            if (id_str == null&&adminId==null) {
              responseCheckIns(response);
            } else {
                //若teacherId = null且id!=null，响应id对应的系对象
                if(adminId==null) {
                   int id = Integer.parseInt(id_str);
                    responseCheckIn(id, response);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    //响应一个学院对象
    private void responseCheckIn(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        CheckIn checkIn = CheckInService.getInstance().find(id);
        String checkIn_json = JSON.toJSONString(checkIn);

        //响应department_json到前端
        response.getWriter().println(checkIn_json);
    }
    //响应所有学院对象
    private void responseCheckIns(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<CheckIn> checkIns = CheckInService.getInstance().findAll();
        String checkIns_json = JSON.toJSONString(checkIns, SerializerFeature.DisableCircularReferenceDetect);

        //响应departments_json到前端
        response.getWriter().println(checkIns_json);
    }
}
