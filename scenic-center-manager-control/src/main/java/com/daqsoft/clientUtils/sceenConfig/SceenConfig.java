package com.daqsoft.clientUtils.sceenConfig;

import com.daqsoft.entity.clientEntity.DBConfig;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lyl
 * @version V1.0.0
 * @description 客户端配置
 * @date 2018-02-06
 */
public class SceenConfig {

    /**
     * 客户端配置
     *
     * @param dbConfig
     * @param vcode
     * @return
     */
    public Map<String, String> getSceenconfig(DBConfig dbConfig, String vcode, int monitor) {

        Map<String, String> map = new HashMap<>();
        //获取DB资源
        DataSource dataSource = new DBService().getDataSource(dbConfig);

        String apps = null;
        String deqec = null;
        String scenics = null;
        String slideVideos = null;

        try {
            //创建连接池，分别获取相应类别的XML配置
            ConnectionPool pool = dataSource.createPool();
            apps = new AppsConfig().getApps(pool, vcode); //导航条
            deqec = new DeqecConfig().getDeqec(pool, vcode);  //大屏模板
            slideVideos = new SlideVideosConfig().getSlideVideos(pool, vcode, monitor);  //论播监控

            //监控 通过Vcode区分海螺沟和其他景区 TODO
            if ("ac1d7d12306c5e60c89cfe9e6fc75027".equals(vcode)) {
                scenics = new ScenicsConfig().getHLGScenics(pool, vcode, monitor);
            } else {
                scenics = new ScenicsConfig().getNotHLGScenics(pool, vcode, monitor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dataSource.close();
            map.put("apps", apps);
            map.put("deqec", deqec);
            map.put("scenics", scenics);
            map.put("slideVideos", slideVideos);
            return map;
        }


    }


}
