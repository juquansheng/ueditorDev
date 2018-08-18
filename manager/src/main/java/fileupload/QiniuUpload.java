package fileupload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * test
 *
 * @author cexy
 * @description:
 * @date 2018/8/16
 * @time 10:33
 */
public class QiniuUpload {
    private String ak;
    private String sk;
    private String bucketName;
    private String prefix;
    private Auth auth;
    public UploadManager uploadManager;

    public QiniuUpload(String ak, String sk, String bucketName, String host) {
        this.ak = ak;
        this.sk = sk;
        this.bucketName = bucketName;
        this.prefix = "http://" + host + "/";
        auth = Auth.create(ak, sk);
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        uploadManager = new UploadManager(c);
    }

    public QiniuUpload() {
        this.ak = "**********************";
        this.sk = "**********************";
        this.bucketName = "*************";
        this.prefix = "******************";
        auth = Auth.create(ak, sk);
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        uploadManager = new UploadManager(c);
    }

    /**
     * 获取覆盖上传token
     *
     * @param bucketName
     * @param key
     * @return
     */
    public String getUpToken(String bucketName, String key) {
        return auth.uploadToken(bucketName, key);
    }


    public String upload(String key, byte[] bs){
        try {
            //调用put方法上传
            Response res = uploadManager.put(bs, key, getUpToken(bucketName, key));
            //打印返回的信息
            String body = res.bodyString();
            JSONObject object = JSON.parseObject(body);
            String key1 = object.getString("key");
            if (key1 == null || key1.length() <= 0) {
                throw new UploadException("无效的key:" + key1);
            }
            return prefix + key1;
        } catch (QiniuException e) {
            Response r = e.response;
            String rbody = "";
            try {
                //响应的文本信息
                rbody = r.bodyString();
            } catch (QiniuException e1) {
                rbody = r.toString();
            }
            throw new UploadException(e);
        }
    }

/*    public static void main(String[] args){
        QiniuUpload qiniuUpload = new QiniuUpload();
        System.out.println(qiniuUpload.upload("123.txt", "123456".getBytes()));

    }*/
}
