package com.daqsoft.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @Author znb
 * @Date 2016/6/2
 */
public class ValidateUtil {

    public static final String ID_CARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";

    public static final String E_MAIL = "^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$";

    public static final String PHONE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    public static final String QQ = "^\\d{5,10}$";

    public static final String NUMBER = "^\\d+$";

    public static final String ENGLISH = "^[A-Za-z]+$";

//    public static final String DATE = "\\d{4}-(0[1-9]|1[1-2])-(0[1-9]|2[0-9]|3[0-1])";

    public static final String DATE = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))";

    public static final String MONTH = "\\d{4}-(0[1-9]|1[0-2])";

    public static final String SIMPLEMONTH = "^(0?[1-9]|1[0-2])$";

    public static final String MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static final String ELEVEN_NUMBER = "^[\\d]{11}$";

    public static final String REGION_CODE = "^[\\d]{6}$";

    public static final String YEAR = "\\d{4}";

    //季度格式zf，2017_1    2017第一季度
    public static final String QUARTER = "\\d{4}_([1-4])";

    public static boolean Validate(String str, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean flag = matcher.matches();
        return flag;
    }

}
