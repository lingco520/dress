/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.apisecurity;

import com.alibaba.fastjson.JSONObject;
import com.daqsoft.commons.responseentity.DataResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Title: ApiSecurity
 * @Author: tanggm
 * @Date: 2018/03/21 11:31
 * @Description: TODO API权限验证
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class ApiSecurity {
    private static final Logger LOGGER = LoggerFactory.getLogger (ApiSecurity.class);
    public static void main(String[] args) throws IOException {
        //请求URL
        String httpUri = "http://192.168.2.11:20187/information/getDuty?vcode=b2c6fd6866b647bccbc037a352472f79";
        //请求appKey
        String clientId = "0cb6e6f7ad";
        //请求appSecret
        String secretId = "838c8d04a3934db2b81615b0f8";
        //数据获取
        query (httpUri, clientId, secretId);

    }
    /**
     * API接口签名调用测试方法,接口签名参数按ascii码表对参数进行排序
     * ,排序后将<code>clientId</code>添加到参数最前面
     * <code>secretId</code>添加到末尾进行参数混合.对混合后参数进行has1摘要计算,
     * 最后摘要值作为签名<code>sign</code>参数添加到url
     */
    public static void query(String queryUrl, String clientId, String secretId) throws IOException {

        String query = URIUtil.getQuery (queryUrl);
        Map<String, String> params = getUrlParams (query);

        Long timestamp = (System.currentTimeMillis () / 1000);

        //将clientId放入到请求参数中,作为签名参数的一部分
        params.put ("clientId", clientId);
        //添加时间戳
        params.put ("timestamp", String.valueOf (timestamp));
        //对key进行字典排序
        String[] arrayKeys = params.keySet ().toArray (new String[0]);
        Arrays.sort (arrayKeys);
        LOGGER.debug ("Sorted keys:'{}'", Arrays.toString (arrayKeys));

        StringBuilder builder = new StringBuilder ();
        Stream.of (arrayKeys).forEach (e -> builder.append (e).append (params.get (e)));

        //密钥直接添加到末尾,不用加参数名
        builder.append (secretId);

        //对请求参数进入sha1摘要计算同时转换成16进制并转换成大写
        String signature = DigestUtils.sha1Hex (builder.toString ()).toUpperCase ();
        LOGGER.debug ("Encrypt signature:{}", signature);
        //url参数编码处理
        UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl (queryUrl);
        uriComponents.queryParam ("sign", signature);
        uriComponents.queryParam ("clientId", clientId);
        uriComponents.queryParam ("timestamp", timestamp);
        String requestUrl = uriComponents.build ().encode ().toString ();
        LOGGER.info ("Request url:{}", requestUrl);

        //发送post请求
        HttpClient client = HttpClients.createDefault();
        HttpUriRequest httpPost = new HttpPost(requestUrl);

        //指定客户端接收类型
        httpPost.setHeader ("Accept","application/json");
        HttpResponse response = client.execute (httpPost);
        HttpEntity entity = response.getEntity ();
        if (entity != null) {
            String result = EntityUtils.toString (entity);
            LOGGER.debug ("result :{}", result);
            DataResponse dataResponse = JSONObject.parseObject (result, DataResponse.class);
            LOGGER.debug ("result code:{}", dataResponse.getCode ());
            LOGGER.info ("result message:{}", dataResponse.getMessage ());
        }
        EntityUtils.consumeQuietly (entity);
    }
    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return 参数map
     */
    private static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<>(10);
        String[] params = param.split ("&");
        for (String param1 : params) {
            String[] p = param1.split ("=");
            if (p.length == 2) {
                map.put (p[0], p[1]);
            }
        }
        return map;
    }
}
