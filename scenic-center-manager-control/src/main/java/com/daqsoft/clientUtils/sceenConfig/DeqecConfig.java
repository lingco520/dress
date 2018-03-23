package com.daqsoft.clientUtils.sceenConfig;

import com.daqsoft.clientUtils.domain.*;
import com.daqsoft.clientUtils.rest.CreateConfString;
import org.apache.tomcat.jdbc.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lyl
 * @version V1.0.0
 * @description 大屏配置
 * @date 2018-02-06
 */
public class DeqecConfig {

    /**
     * @description 获取deqec 大屏配置
     * @author lyl
     * @version V1.0.0
     * @date 2017-06-22
     */
    public String getDeqec(ConnectionPool pool, String vcode) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Daqec daqec = new Daqec();


        List<Widgts> widgtsList = new LinkedList<>();


        try {
            connection = pool.getConnection();
            //获取模板数据
            String sql = " SELECT name,tab,row,col ,slide FROM sys_screen_widgts WHERE  vcode= ? AND status = 1 ORDER BY sort ASC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vcode);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String tab = resultSet.getString("tab");
                String row = resultSet.getString("row");
                String col = resultSet.getString("col");
                int sider = resultSet.getInt("slide");
                Widgts widgts = new Widgts();
                widgts.setName(name);
                widgts.setTab(tab);
                widgts.setRow(row);
                widgts.setCol(col);
                widgts.setSlide(String.valueOf(sider));
                widgtsList.add(widgts);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

//        ------------
        for (Widgts widgts : widgtsList) {


            String tab = widgts.getTab();
            try {

                connection = pool.getConnection();
                //获取模块数局
                String sql = " SELECT name , url , id , screen , screen_max  screenMax, slide FROM sys_screen_conf_detail s WHERE s.navkey = ? AND s.vcode = ? AND status = 1 ORDER BY sort ASC ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, tab);
                preparedStatement.setString(2, vcode);

                ResultSet resultSet = preparedStatement.executeQuery();
                List<Widgt> widgtList = new LinkedList<>();

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String url = resultSet.getString("url");

                    if (url.length() > 6) {

                        //判断连接类型
                        if (url.contains("?")) {

                            url = url.trim() + "&client=true";
                        } else {
                            url = url.trim() + "?client=true";

                        }
                    } else {

                    }
                    String id = resultSet.getString("id");
                    String screen = resultSet.getString("screen");
                    String screenMax = resultSet.getString("screenMax");
                    int slide = resultSet.getInt("slide");

                    Widgt widgt = new Widgt();


                    widgt.setName(name);
                    widgt.setScreen(screen);
                    widgt.setUrl(url);
                    widgt.setScreenMax(screenMax);
                    widgt.setId(id);
                    widgt.setSlide(String.valueOf(slide));
                    widgtList.add(widgt);
                }

                widgts.setWidgt(widgtList);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {

                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            daqec.setWidgts(widgtsList);


        }

        //返回实体xml
        return CreateConfString.createScreenConf(daqec);
    }
}
