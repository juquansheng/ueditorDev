package fileupload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.ProxyStorageManager;

import java.io.InputStream;

/**
 * test
 *
 * @author cexy
 * @description:
 * @date 2018/8/16
 * @time 11:33
 */
public class MyTest implements ProxyStorageManager {
    @Override
    public State saveBinaryFile(byte[] data, String path) {
        try {
            return Upload2Qiniu.upload(data, path);
        } catch (Exception e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }

    @Override
    public State saveFileByInputStream(InputStream is, String path, long maxSize) {

        try {
            return Upload2Qiniu.upload(is, path, maxSize);
        } catch (Exception e) {
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }

    @Override
    public State saveFileByInputStream(InputStream is, String path) {
        return null;
    }

}
