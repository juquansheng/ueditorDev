package fileupload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 文件上传至Qinniu服务器
 *
 * @author cexy
 * @create 2017-06-28 10:20
 **/
public class Upload2Qiniu {

    public static BaseState upload(InputStream is, String path, long maxSize) throws Exception {
        QiniuUpload qiniuUpload = new QiniuUpload();
        byte[] bytes = toByteArray(is);
        if (bytes.length > maxSize) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }
        String filePath = qiniuUpload.upload(path, bytes);
        BaseState state = new BaseState(true);
        state.putInfo("size", bytes.length);
        state.putInfo("url", filePath);
        state.putInfo("title", path);
        return state;
    }

    public static BaseState upload(byte[] data, String path) throws Exception {
        QiniuUpload qiniuUpload = new QiniuUpload();
        String filePath = qiniuUpload.upload(path, data);
        BaseState state = new BaseState(true);
        state.putInfo("size", data.length);
        state.putInfo("url", filePath);
        state.putInfo("title", path);
        return state;
    }

    private static byte[] toByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[5120];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

}
