package com.baidu.ueditor.TTBFUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.InputStream;

public class TTBFHttpUtils {

    public static Result post(String url,InputStream inputStream,String fileName){
        CloseableHttpClient client = HttpClients.createDefault();
        //构建POST请求   请求地址请更换为自己的。
        //1)
        HttpPost post = new HttpPost(url);
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //第一个参数为 相当于 Form表单提交的file框的name值 第二个参数就是我们要发送的InputStream对象了
            //第三个参数是文件名
            //3)
            builder.addBinaryBody("file", inputStream, ContentType.create("multipart/form-data"), fileName);
            //4)构建请求参数 普通表单项
            StringBody stringBody = new StringBody("12",ContentType.MULTIPART_FORM_DATA);
            builder.addPart("id",stringBody);
            HttpEntity entity = builder.build();
            post.setEntity(entity);
            //发送请求
            HttpResponse response = client.execute(post);
            entity = response.getEntity();
            //将响应请求的对象转换成实体形式的字符形式的参数
            String data = EntityUtils.toString(response.getEntity(), "utf-8");
            //创建一个json对象
            JSONObject jsonObject=new JSONObject(data);
            Result result = new Result();

            result.setStatus(jsonObject.getInt("status"));
            result.setMessage(jsonObject.getString("message"));
            result.setData(jsonObject.getString("data"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result();
            result.setStatus(401);
            return result;
        }

    }

}
