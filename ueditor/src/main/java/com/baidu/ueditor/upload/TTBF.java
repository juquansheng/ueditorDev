package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.toByteArray;

/**
 *@describe
 *@author  ttbf
 *@date  2018/9/5
 */
public class TTBF implements ProxyStorageManager {
    FastFileStorageClient fastFileStorageClient;

    @Autowired
    public TTBF(FastFileStorageClient fastFileStorageClient) {
        this.fastFileStorageClient = fastFileStorageClient;
    }
    public TTBF() {

    }

    @Override
    public State saveBinaryFile(byte[] data, String path) {

        try {
            InputStream is = new ByteArrayInputStream(data);

            //上传fdfs
            String fileName = this.upload(is);
            BaseState state = new BaseState(true);
            state.putInfo("size", data.length);
            state.putInfo("url", "http://file.malaxiaoyugan.com/group1/M00/00/00/" + fileName);
            state.putInfo("title", fileName);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }

    @Override
    public State saveFileByInputStream(InputStream is, String path, long maxSize) {

        try {
            byte[] bytes = toByteArray(is);
            if (bytes.length > maxSize) {
                return new BaseState(false, AppInfo.MAX_SIZE);
            }
            //上传fdfs
            String fileName = this.upload(is);
            System.out.println("file:"+fileName);
            BaseState state = new BaseState(true);
            state.putInfo("size", bytes.length);
            state.putInfo("url", "http://file.malaxiaoyugan.com/group1/M00/00/00/" + fileName);
            state.putInfo("title", fileName);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }

    @Override
    public State saveFileByInputStream(InputStream is, String path) {
        try {
            byte[] bytes = toByteArray(is);
            //上传fdfs
            String fileName = this.upload(is);
            System.out.println("file:"+fileName);
            BaseState state = new BaseState(true);
            state.putInfo("size", bytes.length);
            state.putInfo("url", "http://file.malaxiaoyugan.com/group1/M00/00/00/" + fileName);
            state.putInfo("title", fileName);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseState(false, AppInfo.IO_ERROR);
        }
    }

    /**
     * 单个文件上传
     * @param inputStream
     * @return
     */
    private  String upload(InputStream inputStream) {
        try {
            int available = inputStream.available();
            System.out.println("available"+available);
            MultipartFile file = new MockMultipartFile("temp.jpg","temp.jpg","", inputStream);
            System.out.println("flie"+file.getSize());
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file
                            .getSize(),
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf
                            (".") + 1, file.getOriginalFilename().length()), null);
            String flieName = storePath.getFullPath().substring(17, storePath.getFullPath().length());
            String filePath = "http://file.malaxiaoyugan.com/group1/M00/00/00/" + flieName;
            return flieName;
        } catch (IOException e) {
            throw new UploadException(e);
        }
    }
}
