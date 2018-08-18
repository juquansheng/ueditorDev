package com.baidu.ueditor.upload;

import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

public final class Base64Uploader {

    @SuppressWarnings("unchecked")
    public static State save(HttpServletRequest request, Map<String, Object> conf) {
        String filedName = (String) conf.get("fieldName");
        String fileName = request.getParameter(filedName);
        byte[] data = decode(fileName);

        long maxSize = (Long) conf.get("maxSize");

        if (!validSize(data, maxSize)) {
            return new BaseState(false, AppInfo.MAX_SIZE);
        }

        String suffix = FileType.getSuffix("JPG");

        String savePath = PathFormat.parse((String) conf.get("savePath"),
                (String) conf.get("filename"));

        savePath = savePath + suffix;
        String rootPath = ConfigManager.getRootPath(request, conf);
        String physicalPath = rootPath + savePath;


        State storageState = null;
        if ((Boolean) conf.get("defaultUpload")) {
            storageState = StorageManager.saveBinaryFile(data, physicalPath);
            storageState.putInfo("url", PathFormat.format(savePath));
        } else {
            Class clazz;
            try {
                clazz = Class.forName(conf.get("proxyPath").toString());
                Method m = clazz.getMethod("saveBinaryFile", byte[].class, String.class);
                storageState = (State) m.invoke(clazz.newInstance(), data, savePath);//相当于t.show("Tom",20)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (storageState != null && storageState.isSuccess()) {
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }

        return storageState;
    }

    private static byte[] decode(String content) {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte[] data, long length) {
        return data.length <= length;
    }

}