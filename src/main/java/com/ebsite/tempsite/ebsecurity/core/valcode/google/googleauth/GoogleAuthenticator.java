package com.ebsite.tempsite.ebsecurity.core.valcode.google.googleauth;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * google身份验证码
 *
 * @author 蔡齐盛
 * @create 2017-12-09 18:38
 **/
public class GoogleAuthenticator {

    // 生成的key长度( Generate secret key length)
    public static final int SECRET_SIZE = 10;

    public static final String SEED = "g8GjEvTbW5oVSV7avL47357438reyhreyuryetredLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";
    // Java实现随机数算法
    public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
    // 最多可偏移的时间
    int window_size = 3; // default 3 - max 17

    /**
     * set the windows size. This is an integer value representing the number of
     * 设置窗口大小。这是一个表示数字的整数值
     * 30 second windows we allow The bigger the window, the more tolerant of
     * 我们允许30秒的窗户窗户越大，宽容越大
     * clock skew we are.
     *时钟歪斜，我们是。
     * @param s
     *            window size - must be >=1 and <=17. Other values are ignored
     *            窗口大小 - 必须> = 1和<= 17。其他值将被忽略
     */
    public void setWindowSize(int s) {
        if (s >= 1 && s <= 17)
            window_size = s;
    }

    /**
     * Generate a random secret key. This must be saved by the server and
     * 生成一个随机密钥。这必须由服务器和
     * associated with the users account to verify the code displayed by Google
     * 与用户帐户相关联，以验证Google显示的代码
     * Authenticator. The user must register this secret on their device.
     * 验证器。用户必须在他们的设备上注册这个秘密。
     *
     * 生成一个随机秘钥
     *
     * @return secret key
     * @返回密钥
     */
    public static String generateSecretKey() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            sr.setSeed(Base64.decodeBase64(SEED));
            byte[] buffer = sr.generateSeed(SECRET_SIZE);
            Base32 codec = new Base32();
            byte[] bEncodedKey = codec.encode(buffer);
            String encodedKey = new String(bEncodedKey);
            return encodedKey;
        } catch (NoSuchAlgorithmException e) {
            // should never occur... configuration error
            //不应该发生...配置错误
        }
        return null;
    }

    /**
     * Return a URL that generates and displays a QR barcode. The user scans
     * 返回一个生成并显示QR条形码的URL。用户扫描
     * this bar code with the Google Authenticator application on their
     * 这个条码带有Google Authenticator应用程序
     * smartphone to register the auth code. They can also manually enter the
     * 智能手机注册认证代码。他们也可以手动输入
     * secret if desired
     * 如果需要，请保密
     *
     * @param user
     *            user id (e.g. fflinstone)
     * @param host
     *            host or system that the code is for (e.g. myapp.com)
     * @param secret
     *            the secret that was previously generated for this user
     * @return the URL for the QR code to scan
     */
    public static String getQRBarcodeURL(String user, String host, String secret) {
        String format = "http://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";
        return String.format(format, user, host, secret);
    }

    /**
     * 生成一个google身份验证器，识别的字符串，只需要把该方法返回值生成二维码扫描就可以了。
     *
     * @param user
     *            账号
     * @param secret
     *            密钥
     * @return
     */
    public static String getQRBarcode(String user, String secret,String siteName) {
        String format = "otpauth://totp/%s?secret=%s&issuer=%s";
        return String.format(format, user, secret,siteName);
    }

    /**
     * Check the code entered by the user to see if it is valid 验证code是否合法
     *检查用户输入的代码是否有效验证码是否合法
     * @param secret
     *            The users secret.
     *            用户的Key
     * @param code
     *            The code displayed on the users device
     *            用户设备上显示的代码
     *            The time in msec (System.currentTimeMillis() for example)
     *            以毫秒为单位的时间（例如System.currentTimeMillis（））
     * @return
     */
    public boolean check_code(String secret, long code, long timeMsec) {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        // convert unix msec time into a 30 second "window"
        //将unix毫秒时间转换成30秒的“窗口”
        // this is per the TOTP spec (see the RFC for details)
        //这是按照TOTP规范（详情请参阅RFC）
        long t = (timeMsec / 1000L) / 30L;
        // Window is used to check codes generated in the near past.
        // Window用于检查过去生成的代码。
        // You can use this value to tune how far you're willing to go.
        //你可以用这个值来调整你愿意去的距离。
        //计算在偏移的时间内是否生效
        for (int i = -window_size; i <= window_size; ++i) {
            long hash;
            try {
                //实时操作
                hash = verify_code(decodedKey, t);
                //之前操作
                //hash = verify_code(decodedKey, t+i);
            } catch (Exception e) {
                // Yes, this is bad form - but
                //是的，这是不好的形式 - 但是
                // the exceptions thrown would be rare and a static
                //抛出的异常将是罕见的，一个静态的
                // configuration problem
                //配置问题
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
                // return false;
            }
            if (hash == code) {
                return true;
            }
        }
        // The validation code is invalid.
        //验证码无效
        return false;
    }

    private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        //我们用了很久，因为Java没有unsigned int。
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            //我们正在处理有符号的字节：
            // we just keep the first byte.
            //我们只保留第一个字节
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }
}