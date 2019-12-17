package com.hrm.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @ClassName MySecurity
 * @Description shiro加密等工具类
 * @Version 1.0
 **/
public class MySecurity {
    private static int hashIterations=5;

    /**
     * @Description md5加密，加密内容source,带盐加密salt，指定加密次数：hashIterations
     * @Param [source, salt]
     * @return java.lang.String
     **/
    public static String encryptUserPassword(String source, String salt){
        return new Md5Hash(source, salt, hashIterations).toBase64();
    }
}