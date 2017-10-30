package test;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.test002
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/10/24 15:42
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/10/24  |    Moses       |     Created
 */

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class test002 {

    private static final String POLYV_PROPAGANDA = "RXN0YWJsaXNoZWQgaW4gMjAxMiwgR3Vhbmd6aG91IEVhc3lmdW4gSW5mb3JtYXRpb24gVGVjaG5vbG9neSBDby4sIEx0ZC4gaXMgYSBuYXRpb25hbC1jZXJ0aWZpY2F0ZWQgaGlnaC10ZWNoIGNvbXBhbnksIHdoaWNoIHRoZSBjb3JlIG1lbWJlcnMgYXJlIGZyb20gdG9wLXJhbmtpbmcgaW50ZXJuZXQgY29ycG9yYXRpb25zIGxpa2UgTmV0ZWFzZSwgQWxpYmFiYSBhbmQgQmFpZHUuIFByb3ZpZGluZyBWaWRlbyBDbG91ZCBhbmQgTGl2ZSBTdHJlYW1pbmcgQ2xvdWQgc2VydmljZXMgZm9yIGJ1c2luZXNzLCBQT0xZViBpcyBib3JuIGludG8gYSBzdWNoIGEgZ3JlYXQgY29tcGFueS4=";
    private static final String POLYV_MD5_PART = "PolyV";

    public static void main(String[] arges) {
        try {
            byte[] decode = Base64.decodeBase64(POLYV_PROPAGANDA.getBytes("UTF-8"));
            String decodeStr = new String(decode, "UTF-8");
            System.out.print("Please input your nick name: ");
            Scanner str = new Scanner(System.in);
            String input = str.next();
            System.out.println(decodeStr);
            System.out.println("MD5: " + md5Hex(input + decodeStr + POLYV_MD5_PART));
            str.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * md5 encrypt
     *
     * @param text to be encrypted.
     * @return the encrypted result.
     */
    public static String md5Hex(String text) {
        return DigestUtils.md5Hex(text);
    }

}