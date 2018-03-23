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
 * @description 导航条配置
 * @date 2018-02-06
 */
public class AppsConfig {

    /**
     * 获取apps 导航条
     *
     * @param pool
     * @param vcode
     * @return
     */
    public String getApps(ConnectionPool pool, String vcode) {


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Apps apps = new Apps();
        try {
            connection = pool.getConnection();
            //查询导航条数据
            String sql = " SELECT id, name, url, type newWindow, sys, icon  FROM sys_client_url WHERE vcode =  ?  AND status = 1 ORDER BY rank  ASC ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vcode);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AppItem> list = new LinkedList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String url = resultSet.getString("url");
                String newWindow = resultSet.getString("newWindow");
                String sys = resultSet.getString("sys");
                String icon = resultSet.getString("icon");
                AppItem appItem = new AppItem(url, name, newWindow, icon, sys, id);

                list.add(appItem);
            }
            //添加到实体里
            apps.setAppItem(list);
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

        //返回XML
        return CreateConfString.createTopUrl(apps);
    }
}
