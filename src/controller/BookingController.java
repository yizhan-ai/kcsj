package controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Booking;
import service.BookingService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@WebServlet("/booking.ctl")
public class BookingController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String booking_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Booking bookingToAdd = JSON.parseObject(booking_json, Booking.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Department对象
        try {
            BookingService.getInstance().add(bookingToAdd);
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
            BookingService.getInstance().delete(id);
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
        String booking_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Booking对象
        Booking bookingToAdd = JSON.parseObject(booking_json, Booking.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Department对象对应的记录
        try {
            BookingService.getInstance().update(bookingToAdd);
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
              responseBookings(response);
            } else {
                //若teacherId = null且id!=null，响应id对应的系对象
                if(adminId==null) {
                   int id = Integer.parseInt(id_str);
                    responseBooking(id, response);
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
    private void responseBooking(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学院
        Booking booking = BookingService.getInstance().find(id);
        String booking_json = JSON.toJSONString(booking);

        //响应department_json到前端
        response.getWriter().println(booking_json);
    }
    //响应所有学院对象
    private void responseBookings(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学院
        Collection<Booking> bookings = BookingService.getInstance().findAll();
        String bookings_json = JSON.toJSONString(bookings, SerializerFeature.DisableCircularReferenceDetect);

        //响应departments_json到前端
        response.getWriter().println(bookings_json);
    }
}
