package com.atguigu.gmall.util;
import	java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpclientUtil {
    public static String doGet(String url){
        //创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建http Get请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response=null;
        try {
            //执行请求
            response=httpClient.execute(httpGet);
            //判断返回状态是否为200
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();// 获取响应实体
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);//关闭 HttpEntity的流
                httpClient.close();
                return result;
            }
            httpClient.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static String doPost(String url, Map<String,String> paramMap){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response=null;
        try {
            if(paramMap!=null){
                List<BasicNameValuePair> list=new ArrayList<>();
                for (Map.Entry<String,String> entry:paramMap.entrySet()){
                    list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                }
                HttpEntity httpEntity = new UrlEncodedFormEntity(list, "UTF-8");
                httpPost.setEntity(httpEntity);
            }
            // 执行请求
            response = httpClient.execute(httpPost);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                httpClient.close();
                return result;
            }
            httpClient.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
