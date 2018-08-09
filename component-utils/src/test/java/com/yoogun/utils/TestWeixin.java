package com.yoogun.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class TestWeixin {

    private final String loadSecretUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww1ed33d6697dd507a&corpsecret=6RoLdNk52ln9aq72XIOjt9XqBCVxJBoIA5fNcyn0dHc";
    private final String ACCESS_TOKEN = "ThPkBpBHcTQjr6XNm8QhsGcFcUcUiKMEeuxG648J7fALq-l9dgWuw1BMDI-Glshlycco8fxKZOtp_5niKaUXmSr7vKOxVaRQN2czCqPH6YkvXYW6fUkkxiJADcMhWpf0GAIkzcpumxLPObw6b51vvani-FgXElZKnnuhU7XZat7v6Bcyy-2RQjZa4qZM_gU33kDihclM8AtYrDeahnhD9A";
    private final String sendMsgUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + ACCESS_TOKEN;

    @Test
    public void testGetSecretUrl() {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        InputStream is = null;
        try {
            client = HttpClients.createDefault();
            HttpGet get = new HttpGet(loadSecretUrl);
            response = client.execute(get);
            is = response.getEntity().getContent();
            List<String> lines = IOUtils.readLines(is, "utf-8");
            String res = lines.get(0);
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(res, Map.class);
            System.out.println(map.get("access_token"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
    }

    @Test
    public void testSendMsg() {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        InputStream is = null;
        try {
            client = HttpClients.createDefault();
            HttpPost post = new HttpPost (sendMsgUrl);
            post.addHeader("Content-Type", "application/json");
            String msg = "1234567890!abcdefg^测试信息哈哈哈！！！";
            String data = "{" +
                    "\"touser\" : \"ErPang_1|XiaoYuLiuJun_1\"," +
                    "\"agentid\" : \"1000002\"," +
                    "\"msgtype\" : \"text\"," +
                    "\"text\" : {" +
                    "\"content\" : \"" + msg + "\"}," +
                    "\"safe\" : 0" +
                    "}";
            post.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
            response = client.execute(post);
            is = response.getEntity().getContent();
            List<String> lines = IOUtils.readLines(is, "utf-8");
            lines.forEach(System.out::println);
//            String res = lines.get(0);
//            ObjectMapper mapper = new ObjectMapper();
//            Map map = mapper.readValue(res, Map.class);
//            System.out.println(map.get("access_token"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
    }
}
