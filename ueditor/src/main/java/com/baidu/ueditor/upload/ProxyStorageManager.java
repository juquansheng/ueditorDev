package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.State;

import java.io.File;
import java.io.InputStream;

/**
 * ProxyStroryManager
 *
 * @author cexy
 * @description:
 * @date 2018/8/16
 * @time 11:29
 */

public interface ProxyStorageManager {
    State saveBinaryFile(byte[] data, String path);

    State saveFileByInputStream(InputStream is, String path, long maxSize);

    State saveFileByInputStream(InputStream is, String path);
}
