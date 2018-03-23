package com.daqsoft.clientUtils.sceenConfig;

import com.daqsoft.entity.clientEntity.DBConfig;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * @author lyl
 * @version V1.0.0
 * @description 数据库
 * @date 2018-02-06
 */
public class DBService {

    /**
     * 连接数据库
     *
     * @param dbConfig 数据库配置
     * @return 连接数据库
     */
    public DataSource getDataSource(DBConfig dbConfig) {


        DataSource dataSource = new DataSource();

        dataSource.setUrl(dbConfig.getDb_url());
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(dbConfig.getDb_username());
        dataSource.setPassword(dbConfig.getDb_password());


        return dataSource;
    }
}
