package com.daqsoft.utils.client;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 */

//@Component
@Configurable
@EnableScheduling
public class PerCentUtil {
    public static String percent(String num) {
        String intNumber = num.substring(0,num.indexOf("."));
        if(intNumber.equals("0")){
            intNumber="1";
        }
        return intNumber;
    }

}
