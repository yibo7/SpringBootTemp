package com.parvar.fullnode.utils.encrypt;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {

  public MD5Encrypt() {

  }

  private final static String[] hexDigits = {
      "0", "1", "2", "3", "4", "5", "6", "7",
      "8", "9", "a", "b", "c", "d", "e", "f"};

  /**
   * 转换字节数组为16进制字串
   * @param b 字节数组
   * @return 16进制字串
   */
  public static String byteArrayToString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));//若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
//      resultSb.append(byteToNumString(b[i]));//使用本函数则返回加密结果的10进制数字字串，即全数字形式
    }
    return resultSb.toString();
  }

  private static String byteToNumString(byte b) {

    int _b = b;
    if (_b < 0) {
      _b = 256 + _b;
    }

    return String.valueOf(_b);
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0) {
      n = 256 + n;
    }
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  public static String MD5Encode(String origin) {
    String resultString = null;

    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      //GBK 设置后，可以在与C#里的中文内容保持一样的md5值
      resultString = byteArrayToString(md.digest(resultString.getBytes("GBK")));
    }
    catch (Exception ex) {
    	ex.printStackTrace();
    }
    return resultString;
  }

  /**
   * 获取该输入流的MD5值
   *
   * @param is
   * @return
   * @throws NoSuchAlgorithmException
   * @throws IOException
   */
  public static String getMD5ByStream(InputStream is) throws NoSuchAlgorithmException, IOException {
    StringBuffer md5 = new StringBuffer();
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] dataBytes = new byte[1024];

    int nread = 0;
    while ((nread = is.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    };
    byte[] mdbytes = md.digest();

    // convert the byte to hex format
    for (int i = 0; i < mdbytes.length; i++) {
      md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    return md5.toString();
  }
}

