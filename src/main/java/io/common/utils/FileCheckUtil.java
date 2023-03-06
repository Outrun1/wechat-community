package io.common.utils;

import io.common.exception.CommunityException;

/**
 * @author 
 * 
 */
public class FileCheckUtil {

    public static void checkSize(long maxSize, long size) {
        // 单位 M
        int len = 1024 * 1024;
        if(size > (maxSize * len)){
            throw new CommunityException("上传文件超出规定大小");
        }
    }
}
