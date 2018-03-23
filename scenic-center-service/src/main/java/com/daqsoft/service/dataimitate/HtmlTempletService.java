package com.daqsoft.service.dataimitate;

import com.daqsoft.mapper.resource.HtmlTempletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: 业务数据模板存储service
 * @Author: lyl
 * @Date: 2018/02/26 11:54
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */
@Service
@Transactional
public class HtmlTempletService {
    @Autowired
    private HtmlTempletMapper htmlTempletMapper;

    /**
     * 保存模板信息
     *
     * @param keys  key标识数组
     * @param names 名称数组
     * @throws Exception
     */
    public void save(String[] keys, String[] names) throws Exception {
        int kLength = keys.length;
        int nLength = names.length;
        //判断key,name,content是否一一对应，否则页面代码有问题不保存
        if (kLength > 0 && nLength > 0 && kLength == nLength) {
            htmlTempletMapper.emptyTable();
            //双循环，防止单模块字段过多形成多行记录代码，将多行代码放在同一个key里面
            for (int i = 0; i < keys.length; i++) {
                Map map = new HashMap<>();
                for (int j = i + 1; j < keys.length; j++) {
                    //不重复 跳出循环
                    if (! keys[i].equals(keys[j])) {
                        break;
                    } else {
                        // 对外层循环控制，重复几次，++ 几次
                        i++;
                    }
                }
                map.put("name", names[i]);
                map.put("key", keys[i]);
                map.put("id", i + 1);
                htmlTempletMapper.insert(map);
            }
        } else {
            throw new Exception("保存失败");
        }
    }
}