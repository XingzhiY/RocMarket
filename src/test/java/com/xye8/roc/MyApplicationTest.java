package com.xye8.roc;

import com.xye8.roc.utils.XmlParser;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 测试类
 */
@SpringBootTest
class MyApplicationTest {
//    @Resource
//    XmlParser xmlParser;

    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(newPassword);
    }

    @Test
    void contextLoads() {
        System.out.println("hellp========================================-------------=-=-=-=-=-");
        String url = "https://cdcs.ur.rochester.edu/XMLQuery.aspx?id=XML&div=1&term=Fall%202024&dept=CSC";

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String id = "XML";
            String div = "1";
            String term = "Fall 2024";
            String dept = "CSC";
            URI uri = new URIBuilder("https://cdcs.ur.rochester.edu/XMLQuery.aspx")
                    .addParameter("id", id)
                    .addParameter("div", div)
                    .addParameter("term", term)
                    .addParameter("dept", dept)
                    .build();

            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(uri).build();
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            httpclient.execute(httpGet, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity1 = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                if (entity1 != null) {
                    // 将实体内容转为字符串
                    String result = EntityUtils.toString(entity1);
//                    System.out.println("Response content: " + result);
                    XmlParser.parse(result);
                }


                System.out.println(entity1);
                EntityUtils.consume(entity1);
                return null;
            });

            ClassicHttpRequest httpPost = ClassicRequestBuilder.post("http://httpbin.org/post").setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("username", "vip"), new BasicNameValuePair("password", "secret")))).build();
            httpclient.execute(httpPost, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity2 = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
                return null;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
