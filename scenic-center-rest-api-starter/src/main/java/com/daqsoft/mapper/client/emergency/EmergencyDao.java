package com.daqsoft.mapper.client.emergency;

import com.daqsoft.vo.client.madeVoBean.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: caol.
 * @Date: Created in 2017/6/6.
 * @Version: V3.0.0.
 * @describe:应急管理dao
 */
@Repository
public interface EmergencyDao {
    List<emergencyFullMegVo> emergencyFullMeg(Map map);

    List<RealPeopleChoiceTimeVo> emergencyFullMegPerson(Map map);

    List<RealPeopleChoiceTimeVo> emergencyYearByCjs(Map map);

    List<EmergencyByTypeListVO> emergencyByTypeList(Map map);

    Integer emergencyByTypeListCount(Map map);

    Integer emergencyCountByYear(Map map);

    List<RealPeopleChoiceTimeVo> emergencyMonthByCj(Map map);

    Integer emergencySmallYear(Map map);

    Integer emergencySmallCountList(Map map);

    List<EmergencyInfoListVo> getEmergencyInfoList(Map<String, Object> pd);


    Object getEmergencyInfo(String id);

    void savaEmergencyDealInfo(Map<String, Object> parMap);

    DutyPersonEmerVo getDutyPersonEmergency(String resourcecode);

}
