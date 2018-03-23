package com.daqsoft.clientUtils.sceenConfig;

import com.daqsoft.clientUtils.domain.Item;
import com.daqsoft.clientUtils.domain.Slide;
import com.daqsoft.clientUtils.domain.SlideVideos;
import com.daqsoft.clientUtils.rest.CreateConfString;
import com.daqsoft.pinyin.HanYuPinYin;
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
 * @description 轮播配置
 * @date 2018-02-06
 */
public class SlideVideosConfig {
    /**
     * 获取滚屏
     *
     * @param pool
     * @param vcode
     * @return
     */
    public String getSlideVideos(ConnectionPool pool, String vcode, int monitor) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SlideVideos SlideVideos = new SlideVideos();
        List<Slide> slides = new LinkedList<>();
        Slide slide = new Slide();
        String sql = "";
        //区分海螺沟和其他景区
        if ("38252f24c95a1d455b52942b3da41834".equals(vcode)) {
            slide.setTab("index");
            sql = " SELECT jq.ID id , jq.NAME name,jq.IMAGE image, jq.OUTIPADDRESS outipaddress, jq.OUTPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?   AND jq.status = 1  order by jq.ID DESC  ";
        } else {
            slide.setTab("videos");
            //区分内外网字段IP
            if(monitor == 1) {
                sql = " SELECT jq.ID id , jq.NAME name,jq.IMAGE image, jq.OUTIPADDRESS outipaddress, jq.OUTPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?   AND jq.status = 1 order by jq.ID DESC ";
//            sql = " SELECT jq.ID id , jq.NAME name,jq.IMAGE image, jq.OUTIPADDRESS outipaddress, jq.OUTPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?   AND jq.status = 1 AND jq.slide = 1 order by jq.ID DESC ";
            } else {
                sql = " SELECT jq.ID id , jq.NAME name,jq.IMAGE image, jq.INIPADDRESS outipaddress, jq.INPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?   AND jq.status = 1 order by jq.ID DESC ";
            }
        }

        slides.add(slide);
        List<Item> items = new LinkedList<>();
        slide.setItems(items);
        SlideVideos.setSlides(slides);

        try {

            connection = pool.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vcode);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String pinyin = new HanYuPinYin().getHanyuPinyinFirstChar(name);
                String spinyin = pinyin;
                String outipaddress = resultSet.getString("outipaddress");
                String outport = resultSet.getString("outport");
                String simplename = resultSet.getString("simplename");
                String uname = resultSet.getString("uname");
                String pwd = resultSet.getString("pwd");
                String numcode = resultSet.getString("numcode");
                String rank = resultSet.getString("rank");
                String nowStatus = resultSet.getString("nowStatus");
                String status = resultSet.getString("status");
                String image = resultSet.getString("image");

                Item item = new Item(id, name, image, "1", numcode, outipaddress, simplename, outport, pwd, uname, rank, spinyin);
                items.add(item);
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

        return CreateConfString.createSlideVideos(SlideVideos);
    }


}
