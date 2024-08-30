package com.xye8.roc.service.impl;

import com.xye8.roc.utils.XmlParser;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceImplTest {
    @Resource
    XmlParser xmlParser;


    @Test
    void contextLoadsMultiDeptSingleSchool() {
        System.out.println("Running tests for multiple departments...");

        // List of department codes extracted from the images
        String[] departments = {"AAAS", "ACC", "AHST", "ANTH", "ARBC", "ASLA", "AME", "AMST", "ASTR", "ATHS", "BCSC", "BIOL", "BLST", "BME", "BUS", "CASC", "CGRK", "CHE", "CHEM", "CHIN", "CIS", "CLST", "CLTR", "CSC", "CTSC", "CSSP", "CVSC", "DANC", "DSCC", "DH", "DMST", "EAS", "ECE", "ECON", "EDAB", "ECSC", "EHUM", "ELIP", "ENG", "ENGL", "ENT", "ERG", "FIN", "FMST", "FREN", "GBA", "GHOC", "GRMN", "GSWS", "GREK", "GSW", "HBRW", "HIST", "HLSC", "IEP", "INTR", "ITAL", "JPNS", "JWST", "KORE", "LATN", "LAW", "LING", "LTST", "MATH", "ME", "MKT", "MSC", "MUSC", "NAVS", "NSC", "NSCI", "OPT", "PEC", "PHLT", "PHIL", "PHYS", "POLS", "PORT", "PPS", "PPFL", "PREC", "PSYC", "RELC", "RUSS", "SABR", "SAR", "SEAS", "SNKT", "SOCI", "SPAN", "STAT", "SUST", "TEC", "TEE", "TEM", "TME", "TEO", "TURK", "WRTG", "5MTH", "INTD", "WMST"};
        String id = "XML";
        String div = "1";
        String term = "Fall 2024";

        //        String types = "Lecture";

        for (String dept : departments) {

            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

                URI uri = new URIBuilder("https://cdcs.ur.rochester.edu/XMLQuery.aspx").addParameter("id", id).addParameter("div", div).addParameter("term", term).addParameter("dept", dept).build();

                ClassicHttpRequest httpGet = ClassicRequestBuilder.get(uri).build();
                httpclient.execute(httpGet, response -> {
//                    System.out.println("Processing department: " + dept);
//                    System.out.println(response.getCode() + " " + response.getReasonPhrase());
                    final HttpEntity entity1 = response.getEntity();
                    if (entity1 != null) {
                        String result = EntityUtils.toString(entity1);
                        xmlParser.parse(result);
                    }
                    EntityUtils.consume(entity1);
                    return null;
                });

            } catch (Exception e) {
                System.out.println("Error processing department: " + dept);
                e.printStackTrace();
            }
        }
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
            String types = "Lecture";
            URI uri = new URIBuilder("https://cdcs.ur.rochester.edu/XMLQuery.aspx").addParameter("id", id).addParameter("div", div).addParameter("term", term).addParameter("dept", dept).addParameter("ddlTypes", types).build();

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
                    xmlParser.parse(result);
                }


                //                System.out.println(entity1);
                EntityUtils.consume(entity1);
                return null;
            });

            //            ClassicHttpRequest httpPost = ClassicRequestBuilder.post("http://httpbin.org/post").setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("username", "vip"), new BasicNameValuePair("password", "secret")))).build();
            //            httpclient.execute(httpPost, response -> {
            //                System.out.println(response.getCode() + " " + response.getReasonPhrase());
            //                final HttpEntity entity2 = response.getEntity();
            //                // do something useful with the response body
            //                // and ensure it is fully consumed
            //                EntityUtils.consume(entity2);
            //                return null;
            //            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}