package com.mimehoo.reader.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    /**
     * md5 加密
     * @param source 原始字符串
     * @param salt 盐值
     * @return 加密后的字符串
     */
    public static String md5Digest(String source, Integer salt) {
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] + salt);
        }
        String target = new String(chars);
        return DigestUtils.md5Hex(target);
    }
}
