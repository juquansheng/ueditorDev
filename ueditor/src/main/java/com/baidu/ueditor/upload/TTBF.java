package com.baidu.ueditor.upload;

import com.baidu.ueditor.TTBFUtils.Result;
import com.baidu.ueditor.TTBFUtils.TTBFHttpUtils;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 *@describe
 *@author  ttbf
 *@date  2018/9/5
 */
public class TTBF {

    public static State saveBinaryFile(byte[] data, String path) {

        try {
            InputStream is = new ByteArrayInputStream(data);
            MultipartFile file = new MockMultipartFile("temp.jpg","temp.jpg","", is);
            //上传fdfs
            System.out.println(file.getName());
            Result result = TTBFHttpUtils.post("http://ttbf.malaxiaoyugan.com/yuuki/file/fileupload", is, file.getName());

            BaseState state = new BaseState(true);
            state.putInfo("size", data.length);
            state.putInfo("url", result.getData());
            state.putInfo("title", "ttbf");
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }


    /**
     *
     * @param is
     * @param path 传过来的文件名（只用来区分文件得类型）
     * @param maxSize
     * @return
     */
    public static State saveFileByInputStream(InputStream is, String path, long maxSize) {

        try {



            //上传fdfs
            System.out.println("file:开始上传");
            Result result = TTBFHttpUtils.post("http://ttbf.malaxiaoyugan.com/yuuki/file/fileupload", is, path);
            System.out.println("result"+result.toString());
            BaseState state = new BaseState(true);
            state.putInfo("size", is.available());
            state.putInfo("url", result.getData());
            state.putInfo("title", "ttbf");
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseState(false, AppInfo.MAX_SIZE);
        }
    }


    public static State saveFileByInputStream(InputStream is, String path) {
        try {

            //上传fdfs
            Result result = TTBFHttpUtils.post("http://ttbf.malaxiaoyugan.com/yuuki/file/fileupload", is, path);
            BaseState state = new BaseState(true);
            state.putInfo("size", is.available());
            state.putInfo("url", result.getData());
            state.putInfo("title", "ttbf");
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseState(false, AppInfo.MAX_SIZE);
        }
    }

}
