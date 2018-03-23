package com.daqsoft.vo.client.madeVoBean;

        import java.io.Serializable;

/**
 * @author chenxia
 * @version V3.0.0
 * @date 2017-06-08 14:07.
 * @Description 游客构成返回模型
 */
public class JqticketGcSeal implements Serializable {
    private  String name;
    private  long number;
    public JqticketGcSeal(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getNumber() {
        return number;
    }
    public void setNumber(long number) {
        this.number = number;
    }
}