package com.daqsoft.controller.test;

import com.daqsoft.controller.BaseResponseController;
import com.daqsoft.framework.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/test")
public class PicTestController extends BaseResponseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PicTestController.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final String PATH = "test/";

    /**
     * 返回景区门票的页面,页面通过ajax再请求数据
     *
     * @return 模板页面
     */
    @GetMapping(Constants.LISTACTION)
    public String list(HttpServletRequest request) {
        HashMap map = new HashMap();
        request.setAttribute("dto", map);
        return PATH + "test_input";
    }

}
