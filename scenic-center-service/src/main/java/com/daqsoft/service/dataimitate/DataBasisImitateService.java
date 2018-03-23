/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.dataimitate;

import com.daqsoft.constants.BusinessConstants;
import com.daqsoft.entity.dataconfig.DataBasisConfig;
import com.daqsoft.mapper.resource.DataBasisConfigMapper;
import com.daqsoft.mapper.resource.DataBasisImitateMapper;
import com.daqsoft.utils.DataImitateUtil;
import com.daqsoft.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: TimelyPopulationService
 * @Author: tanggm
 * @Date: 2018/02/05 11:51
 * @Description: TODO 基础数据模拟--实时人数模拟模块
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class DataBasisImitateService {
    @Autowired
    private DataBasisImitateMapper dataBasisImitateMapper;
    @Autowired
    private DataBasisConfigMapper dataBasisConfigMapper;

    /**
     * 实时人数-data_scenic_timely_population
     * 生成实时人数模拟数据方法，根据参数的不同，执行数据生成
     *
     * @param dto  数据配置
     * @param isDeleteHistory  是否覆盖删除原有数据，只是模拟数据
     * @param startDate        记录模拟开始时间
     * @param endDate          记录模拟结束时间
     * @param vcode            景区编码
     * @param dstpNameStr      景区名称
     * @param dstpTotalMinStr  单票人数范围最小值
     * @param dstpTotalMaxStr  单票人数范围最小值
     * @param dstpSourceStr    票务类型
     * @param dstpTypeStr      类型: 1 进园，2 出园
     * @param dstpRandomMinStr 实时人数记录数范围最小值
     * @param dstpRandomMaxStr 实时人数记录范围最大值
     * @throws Exception
     */
    public long saveDataScenicTimelyPopulation(DataBasisConfig dto,  String isDeleteHistory, Date startDate, Date endDate, String vcode,
                                               String dstpNameStr, String dstpTotalMinStr, String dstpTotalMaxStr,
                                               String dstpSourceStr, String dstpTypeStr, String dstpRandomMinStr, String dstpRandomMaxStr) throws Exception {
        if (dto.getId() == null) {
            dataBasisConfigMapper.insert(dto);
        } else {
            dataBasisConfigMapper.updateByPrimaryKey(dto);
        }
        // 总记录数
        long totalRecord = 0;
        if (!StringUtils.isEmpty(dstpNameStr)) {
            // 拆分数据
            String[] dstpNames = dstpNameStr.split(",");
            if (dstpNames == null || dstpNames.length < 0) {
                return totalRecord;
            }
            // 在此次写入数据之前，先做删除操作,根据vcode和数据模拟时间范围删除
            if ("1".equals(isDeleteHistory)) {
                String stime = DataImitateUtil.getYmdStr(startDate) + " 00:00:00";
                String etime = DataImitateUtil.getYmdStr(endDate) + " 23:59:59";
                String dataType = BusinessConstants.DATA_TYPE;
                dataBasisImitateMapper.deleteOldDataScenicTimelyPopulation(vcode, stime, etime, dataType);
            }
            // 多行模拟数据插入
            String[] dstpTotalMins = dstpTotalMinStr.split(",");
            String[] dstpTotalMaxs = dstpTotalMaxStr.split(",");
            String[] dstpSources = dstpSourceStr.split(",");
            String[] dstpTypes = dstpTypeStr.split(",");
            String[] dstpRandomMins = dstpRandomMinStr.split(",");
            String[] dstpRandomMaxs = dstpRandomMaxStr.split(",");
            int maxInsert = 6000;
            int k = 0;
            // 查询景区最大承载量
            Integer maxQuantity = 50000;
            List<Integer> maxQuantityList = dataBasisImitateMapper.getScenicMaxQuantity(vcode);
            if (maxQuantityList.size() > 0) {
                maxQuantity = maxQuantityList.get(0);
            }
            // 数据每天开始时间点，根据正式数据推送的数据时间节点考虑，手动代码定死,如果后期需要，可以在页面上进行配置
            String startHMS = "06:00:00";
            // 数据每天结束时间点
            String endHMS = "18:00:00";
            String headSql = "INSERT INTO jq_scenic_timely_population (NAME, TEMPLYQUANTITY, MAXQUANTITY, TIME, SOURCE," +
                    " TICKET, SYSTEMTIME, RESOURCECODE, RESOURCEvcode, VCODE, TOTAL, YEAR, timeym, TYPE, DATA_TYPE) VALUES ";
            StringBuffer insertSql = new StringBuffer(headSql);
            for (int i = 0; i < dstpNames.length; i++) {
                // 数据时间天数跨度
                List<String> dateList = DateUtil.getSubDateList(startDate, endDate);
                for (String date : dateList) {
                    // 获取每天应该模拟的数据条数
                    int dayCounts = DataImitateUtil.getRandom(Integer.parseInt(dstpRandomMins[i]), Integer.parseInt(dstpRandomMaxs[i]));
                    // 获取每条记录之间的相差秒数
                    int recordSecond = DataImitateUtil.getRecordSecond(dayCounts, DataImitateUtil.getSecSub(startHMS, endHMS));
                    String startTime = date + " " + startHMS;
                    for (int j = 0; j < dayCounts; j++) {
                        // 时间，记录添加时间 模拟成一致
                        String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * j);
                        // 每张票的人数
                        int total = DataImitateUtil.getRandom(Integer.parseInt(dstpTotalMins[i]), Integer.parseInt(dstpTotalMaxs[i]));
                        insertSql.append("('");
                        insertSql.append(dstpNames[i]);
                        insertSql.append("', '");
                        insertSql.append("");
                        insertSql.append("', '");
                        insertSql.append(maxQuantity);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(dstpSources[i]);
                        insertSql.append("', '");
                        insertSql.append("0");
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append("");
                        insertSql.append("', '");
                        insertSql.append("");
                        insertSql.append("', '");
                        insertSql.append(vcode);
                        insertSql.append("', '");
                        insertSql.append(total);
                        insertSql.append("', '");
                        insertSql.append(DataImitateUtil.getYStr(date));
                        insertSql.append("', '");
                        insertSql.append(DataImitateUtil.getYmStr(date));
                        insertSql.append("', '");
                        insertSql.append(dstpTypes[i]);
                        insertSql.append("', '");
                        insertSql.append(BusinessConstants.DATA_TYPE);
                        insertSql.append("')");
                        insertSql.append(",");
                        k++;
                        // 经过测试，数据一次性大概峰值是 30000 条
                        if (k >= maxInsert) {
                            dataBasisImitateMapper.saveDataScenicTimelyPopulation(insertSql.substring(0, insertSql.length() - 1));
                            totalRecord += k;
                            k = 0;
                            insertSql = new StringBuffer(headSql);
                        }
                    }
                }
            }
            if (k > 0) {
                dataBasisImitateMapper.saveDataScenicTimelyPopulation(insertSql.substring(0, insertSql.length() - 1));
                totalRecord += k;
            }
        }
        return totalRecord;
    }

    /**
     * 线下票务-jq_ticket
     * 线下票务模拟数据方法
     *
     * @param dto  数据配置
     * @param isDeleteHistory   是否覆盖删除原有数据，只是模拟数据
     * @param startDate         记录模拟开始时间
     * @param endDate           记录模拟结束时间
     * @param vcode             景区编码
     * @param dofftNameStr      线下票务票名称
     * @param dofftPriceStr     票单价
     * @param dofftNumberStr    票数量
     * @param dofftRandomMinStr 线下票务记录范围最小值
     * @param dofftRandomMaxStr 线下票务记录范围最大值
     * @throws Exception
     */
    public long saveDataOfflineTicket(DataBasisConfig dto,  String isDeleteHistory, Date startDate, Date endDate, String vcode,
                                      String dofftNameStr, String dofftPriceStr, String dofftNumberStr,
                                      String dofftRandomMinStr, String dofftRandomMaxStr) throws Exception {
        if (dto.getId() == null) {
            dataBasisConfigMapper.insert(dto);
        } else {
            dataBasisConfigMapper.updateByPrimaryKey(dto);
        }
        // 总记录数
        long totalRecord = 0;
        if (!StringUtils.isEmpty(dofftNameStr)) {
            // 拆分数据
            String[] dofftNames = dofftNameStr.split(",");
            if (dofftNames == null || dofftNames.length < 0) {
                return totalRecord;
            }
            if ("1".equals(isDeleteHistory)) {
                // 在此次写入数据之前，先做删除操作,根据vcode和数据模拟时间范围删除
                String stime = DataImitateUtil.getYmdStr(startDate) + " 00:00:00";
                String etime = DataImitateUtil.getYmdStr(endDate) + " 23:59:59";
                String dataType = BusinessConstants.DATA_TYPE;
                dataBasisImitateMapper.deleteOldDataOfflineTicket(vcode, stime, etime, dataType);
            }

            // 多行模拟数据插入
            String[] dofftPrices = dofftPriceStr.split(",");
            String[] dofftNumbers = dofftNumberStr.split(",");
            String[] dofftRandomMins = dofftRandomMinStr.split(",");
            String[] dofftRandomMaxs = dofftRandomMaxStr.split(",");
            // 一次写入数据操作最大值，根据数据库配置不同而异
            int maxInsert = 6000;
            int k = 0;
            // 数据每天开始时间点，根据正式数据推送的数据时间节点考虑，手动代码定死,如果后期需要，可以在页面上进行配置
            String startHMS = "06:00:00";
            // 数据每天结束时间点
            String endHMS = "20:00:00";
            String headSql = "INSERT INTO jq_ticket (MONEY, COUNT, TOTALPRICE, TYPE, TIME, SYSTEMTIME, VCODE, DATA_TYPE) VALUES ";
            StringBuffer insertSql = new StringBuffer(headSql);
            for (int i = 0; i < dofftNames.length; i++) {
                // 数据时间天数跨度
                List<String> dateList = DateUtil.getSubDateList(startDate, endDate);
                for (String date : dateList) {
                    // 获取每天应该模拟的数据条数
                    int dayCounts = DataImitateUtil.getRandom(Integer.parseInt(dofftRandomMins[i]), Integer.parseInt(dofftRandomMaxs[i]));
                    // 获取每条记录之间的相差秒数
                    int recordSecond = DataImitateUtil.getRecordSecond(dayCounts, DataImitateUtil.getSecSub(startHMS, endHMS));
                    String startTime = date + " " + startHMS;
                    for (int j = 0; j < dayCounts; j++) {
                        // 时间，记录添加时间 模拟成一致
                        String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * j);
                        // 每张票的总价
                        BigDecimal totalMoney = BigDecimal.ZERO;
                        if (dofftNumbers[i] != null) {
                            totalMoney = new BigDecimal(dofftPrices[i]).multiply(new BigDecimal(dofftNumbers[i])).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        insertSql.append("('");
                        insertSql.append(dofftPrices[i]);
                        insertSql.append("', '");
                        insertSql.append(dofftNumbers[i]);
                        insertSql.append("', '");
                        insertSql.append(totalMoney);
                        insertSql.append("', '");
                        insertSql.append(dofftNames[i]);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(vcode);
                        insertSql.append("', '");
                        insertSql.append(BusinessConstants.DATA_TYPE);
                        insertSql.append("')");
                        insertSql.append(",");
                        k++;
                        // 经过测试，数据一次性大概峰值是 30000 条
                        if (k >= maxInsert) {
                            dataBasisImitateMapper.saveDataOfflineTicket(insertSql.substring(0, insertSql.length() - 1));
                            totalRecord += k;
                            k = 0;
                            insertSql = new StringBuffer(headSql);
                        }
                    }
                }
            }
            if(k > 0){
                dataBasisImitateMapper.saveDataOfflineTicket(insertSql.substring(0, insertSql.length() - 1));
                totalRecord += k;
            }
        }
        return totalRecord;
    }

    /**
     * 线上票务-jq_ota_data
     * 线上票务模拟数据方法
     *
     * @param dto  数据配置
     * @param isDeleteHistory  是否覆盖删除原有数据，只是模拟数据
     * @param startDate        记录模拟开始时间
     * @param endDate          记录模拟结束时间
     * @param vcode            景区编码
     * @param dontNameStr      线上票务票名称
     * @param dontPriceStr     票单价
     * @param dontNumberStr    票数量
     * @param dontRandomMinStr 线上票务记录范围最小值
     * @param dontRandomMaxStr 线上票务记录范围最大值
     * @throws Exception
     */
    public long saveDataOnlineTicket(DataBasisConfig dto,  String isDeleteHistory, Date startDate, Date endDate,
                                     String vcode, String dontNameStr, String dontPriceStr,
                                     String dontNumberStr, String dontRandomMinStr, String dontRandomMaxStr) throws Exception {
        if (dto.getId() == null) {
            dataBasisConfigMapper.insert(dto);
        } else {
            dataBasisConfigMapper.updateByPrimaryKey(dto);
        }
        // 总记录数
        long totalRecord = 0;
        if (!StringUtils.isEmpty(dontNameStr)) {
            // 拆分数据
            String[] dontNames = dontNameStr.split(",");
            if (dontNames == null || dontNames.length < 0) {
                return totalRecord;
            }
            if ("1".equals(isDeleteHistory)) {
                // 在此次写入数据之前，先做删除操作,根据vcode和数据模拟时间范围删除
                String stime = DataImitateUtil.getYmdStr(startDate) + " 00:00:00";
                String etime = DataImitateUtil.getYmdStr(endDate) + " 23:59:59";
                String dataType = BusinessConstants.DATA_TYPE;
                dataBasisImitateMapper.deleteOldDataOnlineTicket(vcode, stime, etime, dataType);
            }

            // 多行模拟数据插入
            String[] dontPrices = dontPriceStr.split(",");
            String[] dontNumbers = dontNumberStr.split(",");
            String[] dontRandomMins = dontRandomMinStr.split(",");
            String[] dontRandomMaxs = dontRandomMaxStr.split(",");
            // 一次写入数据操作最大值，根据数据库配置不同而异
            int maxInsert = 6000;
            int k = 0;
            // 数据每天开始时间点，根据正式数据推送的数据时间节点考虑，手动代码定死,如果后期需要，可以在页面上进行配置
            String startHMS = "00:00:00";
            // 数据每天结束时间点
            String endHMS = "23:59:59";
            String headSql = "INSERT INTO jq_ota_data (COUNT, NAME, TIME, USETIME, UNITPRICE, TOTALPRICE, VCODE, DATA_TYPE) VALUES ";
            StringBuffer insertSql = new StringBuffer(headSql);
            for (int i = 0; i < dontNames.length; i++) {
                // 数据时间天数跨度
                List<String> dateList = DateUtil.getSubDateList(startDate, endDate);
                for (String date : dateList) {
                    // 获取每天应该模拟的数据条数
                    int dayCounts = DataImitateUtil.getRandom(Integer.parseInt(dontRandomMins[i]), Integer.parseInt(dontRandomMaxs[i]));
                    // 获取每条记录之间的相差秒数
                    int recordSecond = DataImitateUtil.getRecordSecond(dayCounts, DataImitateUtil.getSecSub(startHMS, endHMS));
                    String startTime = date + " " + startHMS;
                    for (int j = 0; j < dayCounts; j++) {
                        // 时间，记录添加时间 模拟成一致
                        String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * j);
                        // 每张票的总价
                        BigDecimal totalMoney = BigDecimal.ZERO;
                        if (dontNumbers[i] != null) {
                            totalMoney = new BigDecimal(dontPrices[i]).multiply(new BigDecimal(dontNumbers[i])).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        insertSql.append("('");
                        insertSql.append(dontNumbers[i]);
                        insertSql.append("', '");
                        insertSql.append(dontNames[i]);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(dontPrices[i]);
                        insertSql.append("', '");
                        insertSql.append(totalMoney);
                        insertSql.append("', '");
                        insertSql.append(vcode);
                        insertSql.append("', '");
                        insertSql.append(BusinessConstants.DATA_TYPE);
                        insertSql.append("')");
                        insertSql.append(",");
                        k++;
                        // 经过测试，数据一次性大概峰值是 30000 条
                        if (k >= maxInsert) {
                            dataBasisImitateMapper.saveDataOnlineTicket(insertSql.substring(0, insertSql.length() - 1));
                            totalRecord += k;
                            k = 0;
                            insertSql = new StringBuffer(headSql);
                        }
                    }
                }
            }
            if(k > 0){
                dataBasisImitateMapper.saveDataOnlineTicket(insertSql.substring(0, insertSql.length() - 1));
                totalRecord += k;
            }
        }
        return totalRecord;
    }

    /**
     * 车流量-jq_tvpm_vehiclelog_part
     * 车流量模拟数据方法
     *
     * @param dto  数据配置
     * @param isDeleteHistory  是否覆盖删除原有数据，只是模拟数据
     * @param startDate        记录模拟开始时间
     * @param endDate          记录模拟结束时间
     * @param vcode            景区编码
     * @param dtvpRegionStr    车辆所在地region
     * @param dtvpRandomMinStr 车流量记录范围最小值
     * @param dtvpRandomMaxStr 车流量记录范围最大值
     * @throws Exception
     */
    public long saveDataTvpmVehiclelogPart(DataBasisConfig dto,  String isDeleteHistory, Date startDate, Date endDate,
                                           String vcode, String dtvpRegionStr, String dtvpRandomMinStr,
                                           String dtvpRandomMaxStr) throws Exception {
        if (dto.getId() == null) {
            dataBasisConfigMapper.insert(dto);
        } else {
            dataBasisConfigMapper.updateByPrimaryKey(dto);
        }
        // 总记录数
        long totalRecord = 0;
        if (!StringUtils.isEmpty(dtvpRandomMinStr)) {
            // 拆分数据
            String[] dtvpRandomMins = dtvpRandomMinStr.split(",");
            if (dtvpRandomMins == null || dtvpRandomMins.length < 0) {
                return totalRecord;
            }
            if ("1".equals(isDeleteHistory)) {
                // 在此次写入数据之前，先做删除操作,根据vcode和数据模拟时间范围删除
                String stime = DataImitateUtil.getYmdStr(startDate) + " 00:00:00";
                String etime = DataImitateUtil.getYmdStr(endDate) + " 23:59:59";
                String dataType = BusinessConstants.DATA_TYPE;
                dataBasisImitateMapper.deleteOldDataTvpmVehiclelogPart(vcode, stime, etime, dataType);
            }

            // 多行模拟数据插入
            String[] dtvpRegions = dtvpRegionStr.split(",");
            String[] dtvpRandomMaxs = dtvpRandomMaxStr.split(",");
            // 一次写入数据操作最大值，根据数据库配置不同而异
            int maxInsert = 5000;
            int k = 0;
            // 数据每天开始时间点，根据正式数据推送的数据时间节点考虑，手动代码定死,如果后期需要，可以在页面上进行配置
            String startHMS = "00:00:00";
            // 数据每天结束时间点
            String endHMS = "23:59:59";
            String headSql = "INSERT INTO jq_tvpm_vehiclelog_part (ACCESSDATE, ACCESSTIME, VEHICLENO, MARK, vehicleType, " +
                    "source, CITY, PROVINCE, VCODE, DATA_TYPE) VALUES ";
            StringBuffer insertSql = new StringBuffer(headSql);
            for (int i = 0; i < dtvpRandomMins.length; i++) {
                // 数据时间天数跨度
                List<String> dateList = DateUtil.getSubDateList(startDate, endDate);
                // 获取所选地区所在省份下的所有城市列表
                String[] regions = dtvpRegions[i].split("@");
                // 取最小的一个地区等级的region
                String region = regions[regions.length-1];
                if (StringUtils.isEmpty(region)) {
                    throw new Exception("模拟数据城市未选择");
                }
                String subRegion = region.substring(0, 2);
                // 查询景区地理位置所在省份
                String provinceName = dataBasisImitateMapper.getProvinceName(subRegion);
                // 查询景区所在城市名称，用于数据权重的模拟
                String cityName = dataBasisImitateMapper.getCityName(region.substring(0, 4));
                List<Map> cityCarMap = dataBasisImitateMapper.getCityCarMap(cityName);
                // 查询城市 车牌号列表
                List<Map> cityCarList = dataBasisImitateMapper.getCityCarList(provinceName);
                int citySize = cityCarList.size();
                for (String date : dateList) {
                    // 获取每天应该模拟的数据条数
                    int dayCounts = DataImitateUtil.getRandom(Integer.parseInt(dtvpRandomMins[i]), Integer.parseInt(dtvpRandomMaxs[i]));
                    // 获取每条记录之间的相差秒数
                    int recordSecond = DataImitateUtil.getRecordSecond(dayCounts, DataImitateUtil.getSecSub(startHMS, endHMS));
                    String startTime = date + " " + startHMS;
                    for (int j = 0; j < dayCounts; j++) {
                        // 这里对数据模拟，景区所在市 车辆模拟数量是 总的一半
                        String scenicProvinceName = "";
                        String scenicCityName = "";
                        String scenicCarNum = "";
                        if (citySize > 0 && i % 2 == 0) {
                            // 另外一半模拟其它城市随机数据
                            int cityIndex = DataImitateUtil.getRandom(0, (citySize - 1));
                            Map cityMap = cityCarList.get(cityIndex);
                            scenicProvinceName = cityMap.get("province") + "";
                            scenicCityName = cityMap.get("city") + "";
                            scenicCarNum = cityMap.get("vehicleno") + "";
                        } else {
                            // 取第一条，有可能一个城市名有多条记录，车牌号不一样
                            if (cityCarMap != null && cityCarMap.size() > 0) {
                                scenicProvinceName = cityCarMap.get(0).get("province") + "";
                                scenicCityName = cityCarMap.get(0).get("city") + "";
                                scenicCarNum = cityCarMap.get(0).get("vehicleno") + "";
                            }
                        }
                        String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * j);
                        insertSql.append("('");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append(scenicCarNum + DataImitateUtil.getCarNumber());
                        insertSql.append("', '");
                        insertSql.append("00001");
                        insertSql.append("', '");
                        insertSql.append("0");
                        insertSql.append("', '");
                        insertSql.append("1");
                        insertSql.append("', '");
                        insertSql.append(scenicCityName);
                        insertSql.append("', '");
                        insertSql.append(scenicProvinceName);
                        insertSql.append("', '");
                        insertSql.append(vcode);
                        insertSql.append("', '");
                        insertSql.append(BusinessConstants.DATA_TYPE);
                        insertSql.append("')");
                        insertSql.append(",");
                        k++;
                        // 经过测试，数据一次性大概峰值是 30000 条
                        if (k >= maxInsert) {
                            dataBasisImitateMapper.saveDataTvpmVehiclelogPart(insertSql.substring(0, insertSql.length() - 1));
                            totalRecord += k;
                            k = 0;
                            insertSql = new StringBuffer(headSql);
                        }
                    }
                }
            }
            // 判断是否追加有数据行记录，不然只有sql头保存会报错
            if (k > 0) {
                dataBasisImitateMapper.saveDataTvpmVehiclelogPart(insertSql.substring(0, insertSql.length() - 1));
                totalRecord += k;
            }
        }
        return totalRecord;
    }

    /**
     * 实时停车位-jq_timely_parking
     * 实时停车位模拟数据方法
     *
     * @param dto  数据配置
     * @param isDeleteHistory 是否覆盖删除原有数据，只是模拟数据
     * @param startDate       记录模拟开始时间
     * @param endDate         记录模拟结束时间
     * @param vcode           景区编码
     * @param dtpParkingIdStr 停车场ID
     * @param dtpTotalStr     车位总数
     * @param dtpUsedMinStr   已停车位数范围最小值
     * @param dtpUsedMaxStr   已停车位数范围最大值(必须小于等于总停车位)
     * @param dtpRandomMinStr 实时停车位记录范围最小值
     * @param dtpRandomMaxStr 实时停车位记录范围最大值
     * @throws Exception
     */
    public long saveDataTimelyParking(DataBasisConfig dto,  String isDeleteHistory, Date startDate, Date endDate, String vcode,
                                      String dtpParkingIdStr, String dtpTotalStr, String dtpUsedMinStr,
                                      String dtpUsedMaxStr, String dtpRandomMinStr, String dtpRandomMaxStr) throws Exception {
        if (dto.getId() == null) {
            dataBasisConfigMapper.insert(dto);
        } else {
            dataBasisConfigMapper.updateByPrimaryKey(dto);
        }
        // 总记录数
        long totalRecord = 0;
        if (!StringUtils.isEmpty(dtpRandomMinStr)) {
            // 拆分数据
            String[] dtpRandomMins = dtpRandomMinStr.split(",");
            if (dtpRandomMins == null || dtpRandomMins.length < 0) {
                return totalRecord;
            }
            if ("1".equals(isDeleteHistory)) {
                // 在此次写入数据之前，先做删除操作,根据vcode和数据模拟时间范围删除
                String stime = DataImitateUtil.getYmdStr(startDate) + " 00:00:00";
                String etime = DataImitateUtil.getYmdStr(endDate) + " 23:59:59";
                String dataType = BusinessConstants.DATA_TYPE;
                dataBasisImitateMapper.deleteOldDataTimelyParking(vcode, stime, etime, dataType);
            }

            // 多行模拟数据插入
            String[] dtpParkingIds = dtpParkingIdStr.split(",");
            String[] dtpTotals = dtpTotalStr.split(",");
            String[] dtpUsedMins = dtpUsedMinStr.split(",");
            String[] dtpUsedMaxs = dtpUsedMaxStr.split(",");
            String[] dtpRandomMaxs = dtpRandomMaxStr.split(",");
            // 一次写入数据操作最大值，根据数据库配置不同而异
            int maxInsert = 6000;
            int k = 0;
            // 数据每天开始时间点，根据正式数据推送的数据时间节点考虑，手动代码定死,如果后期需要，可以在页面上进行配置
            String startHMS = "00:00:00";
            // 数据每天结束时间点
            String endHMS = "23:59:59";
            String headSql = "INSERT INTO jq_timely_parking (PAKING_ID, USED, SURPLUS, TIME, SOURCE, VEHICLETYPE, " +
                    "SORT, DATA_TYPE, VCODE) VALUES ";
            StringBuffer insertSql = new StringBuffer(headSql);
            for (int i = 0; i < dtpRandomMins.length; i++) {
                // 数据时间天数跨度
                List<String> dateList = DateUtil.getSubDateList(startDate, endDate);
                for (String date : dateList) {
                    // 获取每天应该模拟的数据条数
                    int dayCounts = DataImitateUtil.getRandom(Integer.parseInt(dtpRandomMins[i]), Integer.parseInt(dtpRandomMaxs[i]));
                    // 获取每条记录之间的相差秒数
                    int recordSecond = DataImitateUtil.getRecordSecond(dayCounts, DataImitateUtil.getSecSub(startHMS, endHMS));
                    String startTime = date + " " + startHMS;
                    for (int j = 0; j < dayCounts; j++) {
                        // 时间，记录添加时间 模拟成一致
                        String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * j);
                        // 已使用
                        int used = DataImitateUtil.getRandom(Integer.parseInt(dtpUsedMins[i]), Integer.parseInt(dtpUsedMaxs[i]));
                        // 剩余
                        int surplus = Integer.parseInt(dtpTotals[i]) - used;
                        if (surplus < 0) {
                            surplus = 0;
                        }
                        insertSql.append("('");
                        insertSql.append(dtpParkingIds[i]);
                        insertSql.append("', '");
                        insertSql.append(used);
                        insertSql.append("', '");
                        insertSql.append(surplus);
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        insertSql.append("1");
                        insertSql.append("', '");
                        insertSql.append("小型车");
                        insertSql.append("', '");
                        insertSql.append("0");
                        insertSql.append("', '");
                        insertSql.append(BusinessConstants.DATA_TYPE);
                        insertSql.append("', '");
                        insertSql.append(vcode);
                        insertSql.append("')");
                        insertSql.append(",");
                        k++;
                        // 经过测试，数据一次性大概峰值是 30000 条
                        if (k >= maxInsert) {
                            dataBasisImitateMapper.saveDataTimelyParking(insertSql.substring(0, insertSql.length() - 1));
                            totalRecord += k;
                            k = 0;
                            insertSql = new StringBuffer(headSql);
                        }
                    }
                }
            }
            if(k > 0){
                dataBasisImitateMapper.saveDataTimelyParking(insertSql.substring(0, insertSql.length() - 1));
                totalRecord += k;
            }
        }
        return totalRecord;
    }

    /**
     * 停车场结算-jq_tvpm_checkout
     * 停车场结算模拟数据方法
     *
     * @param dto  数据配置
     * @param isDeleteHistory 是否覆盖删除原有数据，只是模拟数据
     * @param startDate       记录模拟开始时间
     * @param endDate         记录模拟结束时间
     * @param vcode           景区编码
     * @param dpcParkingIdStr 停车场ID
     * @param dpcRegionStr    车辆所在城市region
     * @param dpcRandomMinStr 停车场结算记录最小值
     * @param dpcRandomMaxStr 停车场结算记录最大值
     * @throws Exception
     */
    public long saveDataParkingCheckout(DataBasisConfig dto,  String isDeleteHistory, Date startDate, Date endDate, String vcode,
                                        String dpcParkingIdStr, String dpcRegionStr, String dpcRandomMinStr,
                                        String dpcRandomMaxStr) throws Exception {
        if (dto.getId() == null) {
            dataBasisConfigMapper.insert(dto);
        } else {
            dataBasisConfigMapper.updateByPrimaryKey(dto);
        }
        // 总记录数
        long totalRecord = 0;
        if (!StringUtils.isEmpty(dpcRandomMinStr)) {
            // 拆分数据
            String[] dpcRandomMins = dpcRandomMinStr.split(",");
            if (dpcRandomMins == null || dpcRandomMins.length < 0) {
                return totalRecord;
            }
            if ("1".equals(isDeleteHistory)) {
                // 在此次写入数据之前，先做删除操作,根据vcode和数据模拟时间范围删除
                String stime = DataImitateUtil.getYmdStr(startDate) + " 00:00:00";
                String etime = DataImitateUtil.getYmdStr(endDate) + " 23:59:59";
                String dataType = BusinessConstants.DATA_TYPE;
                dataBasisImitateMapper.deleteOldDataParkingCheckout(vcode, stime, etime, dataType);
            }

            // 多行模拟数据插入
            String[] dpcParkingIds = dpcParkingIdStr.split(",");
            String[] dpcRegions = dpcRegionStr.split(",");
            String[] dpcRandomMaxs = dpcRandomMaxStr.split(",");
            // 一次写入数据操作最大值，根据数据库配置不同而异
            int maxInsert = 4500;
            int k = 0;
            // 数据每天开始时间点，根据正式数据推送的数据时间节点考虑，手动代码定死,如果后期需要，可以在页面上进行配置
            String startHMS = "00:00:00";
            // 数据每天结束时间点
            String endHMS = "23:59:59";
            String headSql = "INSERT INTO jq_tvpm_checkout (PARKID, PAYMENTMONEY, DEDUCTIONMONEY, TOTALMONEY, ENTRANCETIME," +
                    " EXITTIME, ENTRANCEVEHICLENO, EXITVEHICLENO, VEHICLETYPE, memo, YEAR, DAY, city, province, DATA_TYPE, VCODE) VALUES ";
            StringBuffer insertSql = new StringBuffer(headSql);
            for (int i = 0; i < dpcRandomMins.length; i++) {
                // 数据时间天数跨度
                List<String> dateList = DateUtil.getSubDateList(startDate, endDate);
                // 获取所选地区所在省份下的所有城市列表
                String[] regions = dpcRegions[i].split("@");
                // 取最小的一个地区等级的region
                String region = regions[regions.length-1];
                if (StringUtils.isEmpty(region)) {
                    throw new Exception("模拟数据城市未选择");
                }

                String subRegion = region.substring(0, 2);
                // 查询景区地理位置所在省份
                String provinceName = dataBasisImitateMapper.getProvinceName(subRegion);
                // 查询景区所在城市名称，用于数据权重的模拟
                String cityName = dataBasisImitateMapper.getCityName(region.substring(0, 4));
                List<Map> cityCarMap = dataBasisImitateMapper.getCityCarMap(cityName);
                // 查询城市 车牌号列表
                List<Map> cityCarList = dataBasisImitateMapper.getCityCarList(provinceName);
                int citySize = cityCarList.size();
                for (String date : dateList) {
                    // 获取每天应该模拟的数据条数
                    int dayCounts = DataImitateUtil.getRandom(Integer.parseInt(dpcRandomMins[i]), Integer.parseInt(dpcRandomMaxs[i]));
                    // 获取每条记录之间的相差秒数
                    int recordSecond = DataImitateUtil.getRecordSecond(dayCounts, DataImitateUtil.getSecSub(startHMS, endHMS));
                    String startTime = date + " " + startHMS;
                    for (int j = 0; j < dayCounts; j++) {
                        // 这里对数据模拟，景区所在市 车辆模拟数量是 总的一半
                        String scenicProvinceName = "";
                        String scenicCityName = "";
                        String scenicCarNum = "";
                        if (citySize > 0 && i % 2 == 0) {
                            // 另外一半模拟其它城市随机数据
                            int cityIndex = DataImitateUtil.getRandom(0, (citySize - 1));
                            Map cityMap = cityCarList.get(cityIndex);
                            scenicProvinceName = cityMap.get("province") + "";
                            scenicCityName = cityMap.get("city") + "";
                            scenicCarNum = cityMap.get("vehicleno") + "";
                        } else {
                            // 取第一条，有可能一个城市名有多条记录，车牌号不一样
                            if (cityCarMap != null && cityCarMap.size() > 0) {
                                scenicProvinceName = cityCarMap.get(0).get("province") + "";
                                scenicCityName = cityCarMap.get(0).get("city") + "";
                                scenicCarNum = cityCarMap.get(0).get("vehicleno") + "";
                            }
                        }
                        String time = DataImitateUtil.getDateAddSecond(startTime, recordSecond * j);
                        // 车牌号，进、出一致
                        String carNum = scenicCarNum + DataImitateUtil.getCarNumber();
                        insertSql.append("('");
                        insertSql.append(dpcParkingIds[i]);
                        insertSql.append("', '");
                        insertSql.append("0");
                        insertSql.append("', '");
                        insertSql.append("0");
                        insertSql.append("', '");
                        insertSql.append("0");
                        insertSql.append("', '");
                        insertSql.append(time);
                        insertSql.append("', '");
                        // 进入停车场 time 和出停车场时间间隔
                        insertSql.append(DataImitateUtil.getDateAddSecond(time, DataImitateUtil.getRandom(4 * 60 * 60, 6 * 60 * 60)));
                        insertSql.append("', '");
                        insertSql.append(carNum);
                        insertSql.append("', '");
                        insertSql.append(carNum);
                        insertSql.append("', '");
                        insertSql.append("小型车");
                        insertSql.append("', '");
                        insertSql.append("");
                        insertSql.append("', '");
                        insertSql.append(DataImitateUtil.getYStr(time));
                        insertSql.append("', '");
                        insertSql.append(DataImitateUtil.getYmdStr(time));
                        insertSql.append("', '");
                        insertSql.append(scenicCityName);
                        insertSql.append("', '");
                        insertSql.append(scenicProvinceName);
                        insertSql.append("', '");
                        insertSql.append(BusinessConstants.DATA_TYPE);
                        insertSql.append("', '");
                        insertSql.append(vcode);
                        insertSql.append("')");
                        insertSql.append(",");
                        k++;
                        // 经过测试，数据一次性大概峰值是 30000 条
                        if (k >= maxInsert) {
                            dataBasisImitateMapper.saveDataTvpmVehiclelogPart(insertSql.substring(0, insertSql.length() - 1));
                            totalRecord += k;
                            k = 0;
                            insertSql = new StringBuffer(headSql);
                        }
                    }
                }
            }
            if(k > 0){
                dataBasisImitateMapper.saveDataTvpmVehiclelogPart(insertSql.substring(0, insertSql.length() - 1));
                totalRecord += k;
            }
        }
        return totalRecord;
    }
}
