package com.daqsoft.service.dataimitate;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.entity.businessData.BusinessData;
import com.daqsoft.mapper.resource.BusinessDataMapper;
import com.daqsoft.service.businessdata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Title: 业务数据模拟service
 * @Author: lyl
 * @Date: 2018/02/28 10:13
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
public class BusinessDataService {
    @Autowired
    private BusinessDataMapper businessDataMapper;
    @Autowired
    private BusDataService busDataService;
    @Autowired
    private TravelDataService travelDataService;
    @Autowired
    private HolidayDataService holidayDataService;
    @Autowired
    private FutureTicketDataService futureTicketDataService;
    @Autowired
    private FuturePeopleDataService futurePeopleDataService;
    @Autowired
    private WebCommentDataService webCommentDataService;
    @Autowired
    private AqiDataService aqiDataService;
    @Autowired
    private OxygenDataService oxygenDataService;
    @Autowired
    private AirHumidityDataService airHumidityDataService;
    @Autowired
    private RainDataService rainDataService;
    @Autowired
    private UltDataService ultDataService;
    @Autowired
    private HydrologyDataService hydrologyDataService;
    @Autowired
    private ThunderDataService thunderDataService;
    @Autowired
    private DataImitateService dataImitateService;
    @Autowired
    private PortrayalService portrayalService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private LingeringService lingeringService;
    @Autowired
    private PassengerDayService passengerDayService;
    @Autowired
    private RoadTrafficDataService roadTrafficService;

    /**
     * 保存模拟数据
     *
     * @param vcode
     * @param dto
     * @throws Exception
     */
    @Transactional
    public Long saveBusinessData(String vcode, BusinessData dto) throws Exception {
        //统计生成总条数
        Long count = 0L;
        // 先保存页面数据配置项与配置值
        dataImitateService.saveDataImitate(vcode, dto);
        // 下面对各个业务模块的数据进行数据模拟
        //模拟网评数据
        if ("1".equals(dto.getWpSure())) {
            long num = webCommentDataService.saveWebCommentData(dto.getStartDate(),
                    dto.getEndDate(), dto.getWpMinNum(), dto.getWpMaxNum(), vcode);
            count += num;
        }

        //模拟aqi数据
        if ("1".equals(dto.getAqiSure())) {
            long aqiNum = aqiDataService.saveAqiData(dto.getStartDate(),
                    dto.getEndDate(), dto.getAqiMinNum(), dto.getAqiMaxNum(), vcode);
            count += aqiNum;
        }

        //模拟负氧离子数
        if ("1".equals(dto.getOxySure())) {
            long oxygenNum = oxygenDataService.saveOxyGenData(dto.getStartDate(),
                    dto.getEndDate(), dto.getOxyMinNum(), dto.getOxyMaxNum(), vcode);
            count += oxygenNum;
        }

        //模拟景区空气湿度
        if ("1".equals(dto.getAirHumSure())) {
            long airHumNum = airHumidityDataService.saveAirHumidityData(dto.getStartDate(),
                    dto.getEndDate(), dto.getAirHumMinNum(), dto.getAirHumMaxNum(), vcode);
            count += airHumNum;
        }

        //模拟降水量
        if ("1".equals(dto.getRainSure())) {
            long rainNum = rainDataService.saveRainData(dto.getStartDate(),
                    dto.getEndDate(), dto.getRainMinNum(), dto.getRainMaxNum(), vcode);
            count += rainNum;
        }

        //模拟紫外线
        if ("1".equals(dto.getRainSure())) {
            long ultNum = ultDataService.saveUltData(dto.getStartDate(),
                    dto.getEndDate(), dto.getUltMinNum(), dto.getUltMaxNum(), vcode);
            count += ultNum;
        }

        //模拟水文数据
        if ("1".equals(dto.getTemperatureSure())) {
            long waterNum = hydrologyDataService.saveHydrology(dto, vcode);
            count += waterNum;
        }

        //模拟雷电数据
        if ("1".equals(dto.getThunderSure())) {
            long thunderNum = thunderDataService.saveThunder(dto.getStartDate(),
                    dto.getEndDate(), dto.getThunderLongitude(), dto.getThunderLatitude(), vcode);
            count += thunderNum;
        }

        //模拟五年大巴车数据
        if ("1".equals(dto.getBusSure())) {
            long fiveBusData = busDataService.saveBusData(dto.getStartDate(),
                    dto.getOneYear(), dto.getTwoYear(), dto.getThreeYear(), dto.getFourYear(), dto.getFiveYear(), vcode);
            count += fiveBusData;
        }

        //模拟旅行社数据
        if ("1".equals(dto.getTravelTeamSure())) {
            long travelData = travelDataService.saveTravelData(dto.getStartDate(),
                    dto.getEndDate(), dto.getTravelTeamMinNum(), dto.getTravelTeamMaxNum(), vcode);
            count += travelData;
        }
        //模拟节假日预测数据
        if ("1".equals(dto.getHolidaySure())) {
            long holidayData = holidayDataService.saveHolidayData(dto.getSpring(),
                    dto.getTombSweeping(), dto.getLabour(), dto.getDragon(),
                    dto.getMidAutumn(), dto.getNational(), dto.getNewYear(), vcode);
            count += holidayData;
        }
        //模拟未来票务数据
        if ("1".equals(dto.getOfflineSure())) {
            long futureTicket = futureTicketDataService.saveFutureTicketData(dto.getStartDate(),
                    dto.getEndDate(), dto.getOnlineMin(), dto.getOnlineMax(),
                    dto.getOfflineMin(), dto.getOfflineMax(), vcode);
            count += futureTicket;
        }
        //未来客流预测&未来6天客流分析
        if ("1".equals(dto.getPredictSure())) {
            long futurePeople = futurePeopleDataService.saveFuturePeopleData(dto.getStartDate(),
                    dto.getEndDate(), dto.getPredictMin(), dto.getPredictMax(), vcode);
            count += futurePeople;
        }
        //游客画像模拟
        if ("1".equals(dto.getPortrayalSure())) {
            long portrayal = portrayalService.savePortrayal(dto.getStartDate(),
                    dto.getEndDate(), dto.getManRatioMin(), dto.getManRatioMax(),
                    dto.getPortrayalAmountMin(), dto.getPortrayalAmountMax(), vcode);
            count += portrayal;
        }
        //团队数量模拟
        if ("1".equals(dto.getTeamSure())) {
            long teamNum = teamService.saveTeam(dto.getStartDate(),
                    dto.getEndDate(), dto.getTeamAmountMin(), dto.getTeamAmountMax(), vcode);
            count += teamNum;
        }
        //流量模拟
        if (!"".equals(dto.getFlowHourSureStr()) && dto.getFlowHourSureStr() != null) {
            //是否模拟判断
            String flowStr = dto.getFlowHourSureStr();
            String[] isSimulating = flowStr.split(",");
            //流量类型
            String[] source = dto.getFlowSource().split(",");
            //模拟最小
            String[] minNum = dto.getFlowHourMin().split(",");
            //模拟最大
            String[] maxNum = dto.getFlowHourMax().split(",");
            //删除重复时间段数据
            for (int i = 0; i < isSimulating.length; i++) {
                if ("1".equals(isSimulating[i])) {
                    flowService.deleteHistory(dto.getStartDate(), dto.getEndDate(), source[i], vcode);
                }
            }
            //新增模拟数据
            for (int i = 0; i < isSimulating.length; i++) {
                if ("1".equals(isSimulating[i])) {
                    long flowNum = flowService.saveFlow(dto.getStartDate(),
                            dto.getEndDate(), source[i], minNum[i], maxNum[i], vcode);
                    count += flowNum;
                }
            }
        }
        //游客驻留时长模拟
        if ("1".equals(dto.getLingeringSure())) {
            long lingeringNum = lingeringService.saveLingering(dto, vcode);
            count += lingeringNum;
        }
        //模拟景区日客流趋势数据
        if ("1".equals(dto.getPassengerDaySure())) {
            long passengerNum = passengerDayService.savePassengerDay(dto.getStartDate(),
                    dto.getEndDate(), dto.getEnterMin(), dto.getEnterMax(),
                    dto.getLeaveMin(), dto.getLeaveMax(), vcode);
            count += passengerNum;
        }
        //模拟交通预警信息
        if ("1".equals(dto.getTrafficSure())) {
            String roadTraffic = dto.getRoadTraffic();
            if (!StringUtil.isEmpty(roadTraffic)) {
                roadTrafficService.deleteHistory(dto.getStartDate(), dto.getEndDate(), vcode);
                long passengerNum = roadTrafficService.saveData(dto.getStartDate(),
                        dto.getEndDate(), roadTraffic, vcode);
                count += passengerNum;
            }
        }
        return count;
    }

    /**
     * 数据回显
     *
     * @param vcode
     * @return
     */
    public List<Map> getDataImitateList(String vcode) {
        return businessDataMapper.getDataImitateList(vcode);
    }

    /**
     * 查询Html模板的Hkey列表
     *
     * @return
     */
    public List<String> getTemplateHkeyList() {
        return businessDataMapper.getTemplateHkeyList();
    }

    /**
     * 查询Html模板的Hkey列表
     *
     * @param vcode
     * @return
     */
    public List<String> getHkeyList(String vcode) {
        return businessDataMapper.getHkeyList(vcode);
    }
}