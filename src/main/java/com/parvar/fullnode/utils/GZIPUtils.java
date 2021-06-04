package com.parvar.fullnode.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

/**
 * 使用GZIP进行压缩和解压缩（GZIPOutputStream，GZIPInputStream）
 *
 * @author 蔡齐盛
 * @create 2018-01-18 15:06
 **/
@Slf4j
public class GZIPUtils {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            log.error("gzip compress error.", e);
        }
        return out.toByteArray();
    }

    /**
     * GZIP解压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            log.error("gzip uncompress error.", e);
        }

        return out.toByteArray();
    }

    /**
     * @param bytes
     * @return
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }

    /**
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            log.error("gzip uncompress to string error.", e);
        }
        return null;
    }

//    public static void main(String[] args) {
//        String str =
//                "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";
//        System.out.println("原长度：" + str.length());
//        System.out.println("压缩后字符串：" + GZIPUtils.compress(str).toString().length());
//        System.out.println("解压缩后字符串：" + StringUtils.newStringUtf8(GZIPUtils.uncompress(GZIPUtils.compress(str))));
//        System.out.println("解压缩后字符串：" + GZIPUtils.uncompressToString(GZIPUtils.compress(str)));
//    }

    /**
     * 二进制读取
     */
    public static byte[] toByteArray(Object obj) {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] result = new byte[0];
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            result = bos.toByteArray();
        } catch (IOException e) {
            log.error("出现异常，方法名：toByteArray，异常信息："+e.getMessage());
        } finally {
            if (oos != null && bos != null) {
                try {
                    oos.flush();
                    oos.close();
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    log.error("二进制流读取错误：", e.getMessage());
                }
            }
        }

        return result;
    }

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     */
    public static byte[] compress(byte[] data) {

        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            log.error("出现异常，方法名：compress，异常信息："+e.getMessage());
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                log.error("出现异常，方法名：compress，异常信息："+e.getMessage());
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            log.error("出现异常，方法名：decompress，异常信息："+e.getMessage());
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                log.error("出现异常，方法名：decompress，异常信息："+e.getMessage());
            }
        }

        decompresser.end();
        return output;
    }

}
