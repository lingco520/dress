/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.basisdata;

import com.daqsoft.commons.responseentity.BaseResponse;
import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.entity.dataconfig.DataBasisConfig;
import com.daqsoft.service.dataimitate.DataBasisConfigService;
import com.daqsoft.service.dataimitate.DataBasisImitateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Title: DataImitateController
 * @Author: tanggm
 * @Date: 2018/01/30 9:22
 * @Description: TODO 数据模拟系统
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/permitted/basisData")
public class BasisDataAjaxController extends BaseResponseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(BasisDataAjaxController.class);
    @Autowired
    private DataBasisConfigService dataBasisConfigService;
    @Autowired
    private DataBasisImitateService dataBasisImitateService;
    /**
     * 保存实时人数
     * @return
     */
    @RequestMapping(value = "/saveDataScenicTimelyPopulation", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveDataScenicTimelyPopulation(@Valid @ModelAttribute(value = "dto") DataBasisConfig dto, HttpServletRequest request) {
        Date sTime = new Date();
        // 获取是否删除变量
        String isDeleteHistory = request.getParameter("isDeleteHistory");
        String vcode = getVcode(request);
        try {
            if (dto.getId() == null) {
                dto.setVcode(vcode);
                dto.setUpdateTime(new Date());
                dto.setOperateAccount(getAccount(request));
            } else {
                dto.setUpdateTime(new Date());
            }
            long totalDataScenicTimelyPopulationCount = dataBasisImitateService.saveDataScenicTimelyPopulation(dto, isDeleteHistory,
                    dto.getDstpStartDate(), dto.getDstpEndDate(), vcode, dto.getDstpName(), dto.getDstpTotalMin(),
                    dto.getDstpTotalMax(), dto.getDstpSource(), dto.getDstpType(), dto.getDstpRandomMin(), dto.getDstpRandomMax());
            Date eTime = new Date();
            String msg = "实时人数数据生成成功，共生成记录数" + totalDataScenicTimelyPopulationCount + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("实时人数记录数：" + totalDataScenicTimelyPopulationCount);
            return ResponseBuilder.custom().success(msg).build();
        } catch (Exception e){
            LOGGER.error("实时人数数据生成失败:", e);
            return ResponseBuilder.custom().failed("数据生成失败", 1).build();
        }
    }
    /**
     * 保存线下票务
     * @return
     */
    @RequestMapping(value = "/saveDataOfflineTicket", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveDataOfflineTicket(@Valid @ModelAttribute(value = "dto") DataBasisConfig dto, HttpServletRequest request) {
        Date sTime = new Date();
        String vcode = getVcode(request);
        // 获取是否删除变量
        String isDeleteHistory = request.getParameter("isDeleteHistory");
        try {
            if (dto.getId() == null) {
                dto.setVcode(vcode);
                dto.setUpdateTime(new Date());
                dto.setOperateAccount(getAccount(request));
            } else {
                dto.setUpdateTime(new Date());
            }
            long totalDataOfflineTicketCount = dataBasisImitateService.saveDataOfflineTicket(dto, isDeleteHistory,
                    dto.getDofftStartDate(), dto.getDofftEndDate(), vcode, dto.getDofftName(), dto.getDofftPrice(),
                    dto.getDofftNumber(), dto.getDofftRandomMin(), dto.getDofftRandomMax());
            Date eTime = new Date();
            String msg = "线下票务数据生成成功，共生成记录数" + totalDataOfflineTicketCount + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("线下票务：" + totalDataOfflineTicketCount);
            return ResponseBuilder.custom().success(msg).build();
        } catch (Exception e){
            LOGGER.error("线下票务数据生成失败:", e);
            return ResponseBuilder.custom().failed("线下票务数据生成失败", 1).build();
        }
    }
    /**
     * 保存线上票务
     * @return
     */
    @RequestMapping(value = "/saveDataOnlineTicket", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveDataOnlineTicket(@Valid @ModelAttribute(value = "dto") DataBasisConfig dto, HttpServletRequest request) {
        Date sTime = new Date();
        String vcode = getVcode(request);
        // 获取是否删除变量
        String isDeleteHistory = request.getParameter("isDeleteHistory");
        try {
            if (dto.getId() == null) {
                dto.setVcode(vcode);
                dto.setUpdateTime(new Date());
                dto.setOperateAccount(getAccount(request));
            } else {
                dto.setUpdateTime(new Date());
            }
            long totalDataOnlineTicketCount = dataBasisImitateService.saveDataOnlineTicket(dto, isDeleteHistory,
                    dto.getDontStartDate(), dto.getDontEndDate(), vcode, dto.getDontName(), dto.getDontPrice(),
                    dto.getDontNumber(), dto.getDontRandomMin(), dto.getDontRandomMax());
            Date eTime = new Date();
            String msg = "线上票务数据生成成功，共生成记录数" + totalDataOnlineTicketCount + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("线上票务：" + totalDataOnlineTicketCount);
            return ResponseBuilder.custom().success(msg).build();
        } catch (Exception e){
            LOGGER.error("线上票务数据生成失败:", e);
            return ResponseBuilder.custom().failed("线上票务数据生成失败", 1).build();
        }
    }
    /**
     * 保存车流量
     * @return
     */
    @RequestMapping(value = "/saveDataTvpmVehiclelogPart", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveDataTvpmVehiclelogPart(@Valid @ModelAttribute(value = "dto") DataBasisConfig dto, HttpServletRequest request) {
        Date sTime = new Date();
        String vcode = getVcode(request);
        // 获取是否删除变量
        String isDeleteHistory = request.getParameter("isDeleteHistory");
        try {
            if (dto.getId() == null) {
                dto.setVcode(vcode);
                dto.setUpdateTime(new Date());
                dto.setOperateAccount(getAccount(request));
            } else {
                dto.setUpdateTime(new Date());
            }
            long totalDataTvpmVehiclelogPartCount = dataBasisImitateService.saveDataTvpmVehiclelogPart(dto, isDeleteHistory,
                    dto.getDtvpStartDate(), dto.getDtvpEndDate(), vcode, dto.getDtvpRegion(), dto.getDtvpRandomMin(), dto.getDtvpRandomMax());
            Date eTime = new Date();
            String msg = "车流量数据生成成功，共生成记录数" + totalDataTvpmVehiclelogPartCount + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("车流量：" + totalDataTvpmVehiclelogPartCount);
            return ResponseBuilder.custom().success(msg).build();
        } catch (Exception e){
            LOGGER.error("车流量数据生成失败:", e);
            return ResponseBuilder.custom().failed("车流量数据生成失败", 1).build();
        }
    }
    /**
     * 保存实时停车位
     * @return
     */
    @RequestMapping(value = "/saveDataTimelyParking", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveDataTimelyParking(@Valid @ModelAttribute(value = "dto") DataBasisConfig dto, HttpServletRequest request) {
        Date sTime = new Date();
        String vcode = getVcode(request);
        // 获取是否删除变量
        String isDeleteHistory = request.getParameter("isDeleteHistory");
        try {
            if (dto.getId() == null) {
                dto.setVcode(vcode);
                dto.setUpdateTime(new Date());
                dto.setOperateAccount(getAccount(request));
            } else {
                dto.setUpdateTime(new Date());
            }
            long totalDataTimelyParkingCount = dataBasisImitateService.saveDataTimelyParking(dto, isDeleteHistory,
                    dto.getDtpStartDate(), dto.getDtpEndDate(), vcode, dto.getDtpParkingId(), dto.getDtpTotal(),
                    dto.getDtpUsedMin(), dto.getDtpUsedMax(), dto.getDtpRandomMin(), dto.getDtpRandomMax());
            Date eTime = new Date();
            String msg = "实时停车位数据生成成功，共生成记录数" + totalDataTimelyParkingCount + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("实时停车位：" + totalDataTimelyParkingCount);
            return ResponseBuilder.custom().success(msg).build();
        } catch (Exception e){
            LOGGER.error("实时停车位数据生成失败:", e);
            return ResponseBuilder.custom().failed("实时停车位数据生成失败", 1).build();
        }
    }
    /**
     * 保存停车场结算
     * @return
     */
    @RequestMapping(value = "/saveDataParkingCheckout", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse saveDataParkingCheckout(@Valid @ModelAttribute(value = "dto") DataBasisConfig dto, HttpServletRequest request) {
        Date sTime = new Date();
        String vcode = getVcode(request);
        // 获取是否删除变量
        String isDeleteHistory = request.getParameter("isDeleteHistory");
        try {
            if (dto.getId() == null) {
                dto.setVcode(vcode);
                dto.setUpdateTime(new Date());
                dto.setOperateAccount(getAccount(request));
            } else {
                dto.setUpdateTime(new Date());
            }
            long totalDataParkingCheckoutCount = dataBasisImitateService.saveDataParkingCheckout(dto, isDeleteHistory,
                    dto.getDpcStartDate(), dto.getDpcEndDate(), vcode, dto.getDpcParkingId(), dto.getDpcRegion(),
                    dto.getDpcRandomMin(), dto.getDpcRandomMax());
            Date eTime = new Date();
            String msg = "停车场结算数据生成成功，共生成记录数" + totalDataParkingCheckoutCount + "条，耗时" + new BigDecimal(eTime.getTime()-sTime.getTime()).divide(new BigDecimal(1000),0, BigDecimal.ROUND_HALF_UP) + "秒";
            LOGGER.info("停车场结算：" + totalDataParkingCheckoutCount);
            return ResponseBuilder.custom().success(msg).build();
        } catch (Exception e){
            LOGGER.error("停车场结算数据生成失败:", e);
            return ResponseBuilder.custom().failed("停车场结算数据生成失败", 1).build();
        }
    }
    /**
     * 查询停车场数据
     * @param parkingId 停车场ID
     * @return
     */
    @RequestMapping(value = "/getParkingById", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse getParkingById(Long parkingId) {
        try {
            Map parking = dataBasisConfigService.getParkingById(parkingId);
            return ResponseBuilder.custom().success().data(parking).build();
        } catch (Exception e){
            LOGGER.error("查询停车场数据错误:", e);
            return ResponseBuilder.custom().failed("查询停车场数据错误", 1).build();
        }
    }
}
