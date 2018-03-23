package com.daqsoft.utils;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * @Title: 加密工具
 * @Author: lyl
 * @Date: 2018/02/06 11:31
 * @Description: TODO
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */

public class DesUtils {
    private static String strDefaultKey = "TWeuN7Oi1yvdiXHy";
    private static String endDefaultKey = "Z68AR8vytpjGs7W5";
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);

        for(int i = 0; i < iLen; ++i) {
            int intTmp;
            for(intTmp = arrB[i]; intTmp < 0; intTmp += 256) {
                ;
            }

            if(intTmp < 16) {
                sb.append("0");
            }

            sb.append(Integer.toString(intTmp, 16));
        }

        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];

        for(int i = 0; i < iLen; i += 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }

        return arrOut;
    }

    public DesUtils() throws Exception {
        this(strDefaultKey);
    }

    public DesUtils(String strKey) throws Exception {
        this.encryptCipher = null;
        this.decryptCipher = null;
        Security.addProvider(new SunJCE());
        Key key = this.getKey(strKey.getBytes());
        this.encryptCipher = Cipher.getInstance("DES");
        this.encryptCipher.init(1, key);
        this.decryptCipher = Cipher.getInstance("DES");
        this.decryptCipher.init(2, key);
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return this.encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(this.encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return this.decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(this.decrypt(hexStr2ByteArr(strIn)));
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for(int key = 0; key < arrBTmp.length && key < arrB.length; ++key) {
            arrB[key] = arrBTmp[key];
        }

        SecretKeySpec var4 = new SecretKeySpec(arrB, "DES");
        return var4;
    }

    public static String encryptOneStr(String str) throws Exception {
        DesUtils des = new DesUtils(strDefaultKey);
        return des.encrypt(str);
    }

    public static String encryptTwoStr(String str) throws Exception {
        DesUtils des = new DesUtils(endDefaultKey);
        return des.encrypt(str);
    }

    public static String encryptTwiceStr(String str) throws Exception {
        return encryptTwoStr(encryptOneStr(str));
    }

    public static String decryptOneStr(String str) throws Exception {
        DesUtils des = new DesUtils(endDefaultKey);
        return des.decrypt(str);
    }

    public static String decryptTwoStr(String str) throws Exception {
        DesUtils des = new DesUtils(strDefaultKey);
        return des.decrypt(str);
    }

    public static String decryptTwiceStr(String str) throws Exception {
        return decryptTwoStr(decryptOneStr(str));
    }


    public static void main(String[] args) {
        try {
            String e = "123456789";
            String s = encryptOneStr(e);
            String s1 = encryptTwoStr(s);
            String twiceStr = encryptTwiceStr(e);
            System.out.println("第一次加密结果： " + s);
            System.out.println("第二次加密结果： " + s1);
            System.out.println("二次加密结果：" + twiceStr);
            String s2 = decryptOneStr(s1);
            String s3 = decryptTwoStr(s2);
            String twiceStr1 = decryptTwiceStr(s1);
            System.out.println("第一次解密结果： " + s2);
            System.out.println("第二次解密结果： " + s3);
            System.out.println("两次解密结果" + twiceStr1);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }
}

