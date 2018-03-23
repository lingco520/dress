package com.daqsoft.clientUtils.sceenConfig;

import com.daqsoft.clientUtils.domain.*;
import com.daqsoft.clientUtils.rest.CreateConfString;
import com.daqsoft.pinyin.HanYuPinYin;
import org.apache.tomcat.jdbc.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author lyl
 * @version V1.0.0
 * @description 监控配置
 * @date 2018-02-06
 */
public class ScenicsConfig {

    /**
     * 先搞
     * 非海螺沟
     *
     * @return
     */
    public String getNotHLGScenics(ConnectionPool pool, String vcode, int monitor) {


        Connection connection = null;

        PreparedStatement preparedStatement = null;
        List<Scenic> scenicList = new LinkedList<>();

        try {
            connection = pool.getConnection();
            //统计有多少监控分组
            String sqlGroupNum = " SELECT  count(a.groupName) num from (SELECT groupName from jq_monitor where vcode = ? AND status = 1 GROUP BY groupName) a";
            preparedStatement = connection.prepareStatement(sqlGroupNum);
            preparedStatement.setString(1, vcode);
            ResultSet resultSetGrounp = preparedStatement.executeQuery();
            String groupName = "";
            while (resultSetGrounp.next()) {
                groupName = resultSetGrounp.getString("num");
            }
            int num = Integer.parseInt(groupName);

            //按照分组查询监控
            connection = pool.getConnection();
            String group = " SELECT groupName from jq_monitor where vcode = ? AND status = 1 GROUP BY groupName";
            preparedStatement = connection.prepareStatement(group);
            preparedStatement.setString(1, vcode);
            ResultSet resultSetGrounps = preparedStatement.executeQuery();
            while (resultSetGrounps.next()) {
                String gn = resultSetGrounps.getString("groupName");

                //item 级别
                List<Item> itemList = new LinkedList<>();

                try {
                    connection = pool.getConnection();
                    //获取监控类表数据，区分内外网
                    String sql = "";
                    if (monitor == 1) {
                        if(num == 0) {
                            sql = " SELECT jq.ID id , jq.NAME name, jq.IMAGE image,jq.OUTIPADDRESS outipaddress, jq.OUTPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?   AND jq.status = 1 order by jq.ID DESC ";
                        }else {
                            sql = " SELECT jq.ID id , jq.NAME name, jq.IMAGE image,jq.OUTIPADDRESS outipaddress, jq.OUTPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ? AND jq.groupName = ? AND jq.status = 1 order by jq.ID DESC ";
                        }
                        } else {
                        if(num == 0) {
                            sql = " SELECT jq.ID id , jq.NAME name, jq.IMAGE image,jq.INIPADDRESS outipaddress, jq.INPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?   AND jq.status = 1 order by jq.ID DESC ";
                        }else {
                            sql = " SELECT jq.ID id , jq.NAME name, jq.IMAGE image,jq.INIPADDRESS outipaddress, jq.INPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ? AND jq.groupName = ? AND jq.status = 1 order by jq.ID DESC ";
                        }
                        }
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, vcode);
                    if(num > 0) {
                        preparedStatement.setString(2, gn);
                    }
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
                        itemList.add(item);
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


//        -------------

                //获取监控数量
                Scenic scenic = new Scenic();
                scenic.setNormal("1");
                scenic.setCode(vcode);
                scenic.setItem(itemList);

                //监控数量
                try {
                    connection = pool.getConnection();
                    String sql = "";
                    //分组统计数量
                    if(num > 0) {
                        sql = "SELECT count(id)  count FROM jq_monitor WHERE vcode = ? AND groupName = ? AND  status = 1 ";
                    }else {
                        sql = "SELECT count(id)  count FROM jq_monitor WHERE vcode = ? AND  status = 1 ";
                    }
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, vcode);
                    if(num > 0) {
                        preparedStatement.setString(2, gn);
                    }

                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        scenic.setMonitorCount(count + "");
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

                //景区名称
                try {
                    connection = pool.getConnection();
                //按是否有分组进行查询
                    if(num == 0) {
                        String sql = " SELECT scenic_name  FROM sys_user WHERE VCODE = ? ";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, vcode);

                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            String name = resultSet.getString("scenic_name");
                            scenic.setName(name);
                            String pinyin = new HanYuPinYin().getHanyuPinyinFirstChar(name);
                            scenic.setSpinyin(pinyin);
                            //TODO 此处要加拼音
                        }
                    }else {
                        scenic.setName(gn);
                        String pinyin = new HanYuPinYin().getHanyuPinyinFirstChar(gn);
                        scenic.setSpinyin(pinyin);
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
                scenicList.add(scenic);
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


//        -----------------  城市 编码  数量
        List<City> cityList = new LinkedList<>();

        try {
            connection = pool.getConnection();
            String sql = " SELECT r.REGION region , r.name name FROM sys_region r  LEFT JOIN jq_scenic_areas m ON r.REGION=m.ADMINISTRATIVE_DIVISION WHERE m.VCODE= ?  ";
//            String sql = " SELECT r.REGION region , r.name name FROM sys_region r  LEFT JOIN sys_manager_l m ON r.REGION=m.region WHERE m.VCODE= ?  ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vcode);


            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String region = resultSet.getString("region");
                String name = resultSet.getString("name");
//                name = "云台山景区";
                City city = new City();
                city.setName(name);
                city.setRegion(region);
                city.setScenicCount(1 + "");
                city.setScenic(scenicList);
                cityList.add(city);
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

        Scenics scenics = new Scenics();
        scenics.setCity(cityList);

        return CreateConfString.createMonitors(scenics);


    }


    /**
     * 再搞
     * 海螺沟
     */
    public String getHLGScenics(ConnectionPool pool, String vcode, int monitor) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Set<String> scenicNameSet = new HashSet<>();
//监控分组
        try {
            connection = pool.getConnection();
            String sql = "SELECT jq.NAME name FROM  jq_monitor  jq WHERE jq.vcode = ? AND jq.status = 1 ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vcode);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String s = name.split("\\)")[0].split("\\(")[1];

                scenicNameSet.add(s);
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
//        -----------

        List<Scenic> scenicList = new LinkedList<>();
        for (String scenicName : scenicNameSet) {

            Scenic scenic = new Scenic();
            List<Item> itemList = new LinkedList<>();
            try {
                connection = pool.getConnection();
                String sql = " SELECT jq.ID id , jq.NAME name, jq.OUTIPADDRESS outipaddress, jq.OUTPORT outport , d.SIMPLENAME simplename, jq.UNAME uname, jq.pwd pwd, jq.numcode numcode, jq.rank rank, jq.nowStatus nowStatus, jq.status status FROM  jq_monitor jq LEFT JOIN sys_dict d ON jq.BRAND = d.SKEY WHERE jq.vcode = ?  AND jq.NAME LIKE concat(?,?,?)   AND jq.status = 1";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, vcode);
//                preparedStatement.setString(2, key);
                preparedStatement.setString(2, "%");
                preparedStatement.setString(3, scenicName);
                preparedStatement.setString(4, "%");
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


                    int indexOf = name.indexOf(")");

                    String name2 = name.substring(indexOf + 1, name.length());

                    Item item = new Item(id, name2, numcode, status, nowStatus, outipaddress, simplename, outport, pwd, uname, rank, spinyin);
                    itemList.add(item);
                }
                scenic.setItem(itemList);
                scenic.setNormal("1");
                scenic.setName(scenicName);
                scenic.setCode(vcode);
                scenic.setMonitorCount(itemList.size() + "");
                String pinyin = new HanYuPinYin().getHanyuPinyinFirstChar(scenicName);
                scenic.setSpinyin(pinyin);
                //TODO 此处要加拼音


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
            scenicList.add(scenic);
        }


        List<City> cityList = new LinkedList<>();

        try {
            connection = pool.getConnection();
            String sql = " SELECT r.REGION region , r.name name FROM sys_region r  LEFT JOIN sys_manager m ON r.REGION=m.region WHERE m.VCODE= ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vcode);


            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String region = resultSet.getString("region");
                String name = resultSet.getString("name");
                City city = new City();
                city.setName("海螺沟");
                city.setRegion(region);
                city.setScenicCount(1 + "");
                city.setScenic(scenicList);
                cityList.add(city);
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
        Scenics scenics = new Scenics();
        scenics.setCity(cityList);


        return CreateConfString.createMonitors(scenics);
    }
}
