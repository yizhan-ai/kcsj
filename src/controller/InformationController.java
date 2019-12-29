package controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Information;
import service.InformationService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@WebServlet("/information.ctl")
public class InformationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String information_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Information informationToAdd = JSON.parseObject(information_json, Information.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Department对象
        try {
            InformationService.getInstance().add(informationToAdd);
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
            InformationService.getInstance().delete(id);
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
        String Information_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Information InformationToAdd = JSON.parseObject(Information_json, Information.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Department对象对应的记录
        try {
            InformationService.getInstance().update(InformationToAdd);
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
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null和school_id = null, 表示响应所有系对象
            if (id_str == null) {
                responseInformations(response);
            } else {
                    int id = Integer.parseInt(id_str);
                    responseInformation(id, response);
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
    private void responseInformation(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Information information = InformationService.getInstance().find(id);
        String information_json = JSON.toJSONString(information);

        //响应information_json到前端
        response.getWriter().println(information_json);
    }
    //响应SchoolId=1的所有系
    private void responseFindAllByManager(int manager_id, HttpServletResponse response)
            throws IOException,SQLException{
        //根据School_id获得相对应的所有系
        Collection<Information> rooms = InformationService.getInstance().findAllByManager(manager_id);
        //SerializerFeature是个枚举类型,消除对同一对象循环引用的问题,默认为false
        String informations_json = JSON.toJSONString(rooms, SerializerFeature.DisableCircularReferenceDetect);
        //响应rooms_json到前端
        response.getWriter().println(informations_json);
    }
    //响应所有学院对象
    private void responseInformations(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Information> informations = InformationService.getInstance().findAll();
        String informations_json = JSON.toJSONString(informations, SerializerFeature.DisableCircularReferenceDetect);

        //响应informations_json到前端
        response.getWriter().println(informations_json);
    }
}
