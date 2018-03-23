/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller.client.forecastwarning;

import com.daqsoft.commons.responseentity.ResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: TestResController
 * @Author: tanggm
 * @Date: 2018/03/21 12:46
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping(value = "/test")
public class TestResController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Object test() throws JsonProcessingException {
        Map map = new HashMap<>();
        map.put("aa", "afdsfd");
        return ResponseBuilder.custom().success("success",0).data(map).build();
    }
}
