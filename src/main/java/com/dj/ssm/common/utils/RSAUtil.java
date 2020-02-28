package com.dj.ssm.common.utils;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSAUtil {
	/*私玥
	 * MIICXQIBAAKBgQDlOJu6TyygqxfWT7eLtGDwajtNFOb9I5XRb6khyfD1Yt3YiCgQ
	WMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76xFxdU6jE0NQ+Z+zEdhUTooNR
	aY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4gwQco1KRMDSmXSMkDwIDAQAB
	AoGAfY9LpnuWK5Bs50UVep5c93SJdUi82u7yMx4iHFMc/Z2hfenfYEzu+57fI4fv
	xTQ//5DbzRR/XKb8ulNv6+CHyPF31xk7YOBfkGI8qjLoq06V+FyBfDSwL8KbLyeH
	m7KUZnLNQbk8yGLzB3iYKkRHlmUanQGaNMIJziWOkN+N9dECQQD0ONYRNZeuM8zd
	8XJTSdcIX4a3gy3GGCJxOzv16XHxD03GW6UNLmfPwenKu+cdrQeaqEixrCejXdAF
	z/7+BSMpAkEA8EaSOeP5Xr3ZrbiKzi6TGMwHMvC7HdJxaBJbVRfApFrE0/mPwmP5
	rN7QwjrMY+0+AbXcm8mRQyQ1+IGEembsdwJBAN6az8Rv7QnD/YBvi52POIlRSSIM
	V7SwWvSK4WSMnGb1ZBbhgdg57DXaspcwHsFV7hByQ5BvMtIduHcT14ECfcECQATe
	aTgjFnqE/lQ22Rk0eGaYO80cc643BXVGafNfd9fcvwBMnk0iGX0XRsOozVt5Azil
	psLBYuApa66NcVHJpCECQQDTjI2AQhFc1yRnCU/YgDnSpJVm1nASoRUnU8Jfm3Oz
	uku7JUXcVpt08DFSceCEX9unCuMcT72rAQlLpdZir876*/
//	private static String REQUEST_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALuYr8WlpQbDmQ1Q" + "O5Wz0f85+UhIH1GHbIwCXU+LoPIX2bqHkzE3B3ZIMSvbCWT5pErv0lyM+7X77TSU" + "42kUC36UysQEu9MwdlVD6RaBVfT6MYoJpvvGgq+VlM82OU3P03e7iDoppsENchlw" + "lr93x6xm/MuyzzRI1BTz0H6+rU1lAgMBAAECgYAdcXN1A/CIxT5KVqNjdZuqAUFc" + "1OUFeMnSl7RpfbK/DHtByXGSsd5b9Cyzg2dQD9Z3ZHiRyhbfkzDBpfSjU2ASMrR0" + "xHGLdJx7DSLd3k75ifF/DECAQA/CzIfiCKa9IgA2Cj//OVLcxjGAw4iEnE9Umsa7" + "n2wyR8bouDarHwifwQJBAPT4IcVaChm0Q8GEZwtyf/FIS0kEQ+u6r2zHja4Nlz+s" + "rR3EwggtV+Me9v2xyJfpcO/mbVyOCdaav17Wr7yD+tkCQQDECzFh0cjviS6wFw8Z" + "+vSWv4IBtGBVbhVB30LQFBMg96b3Av89pULpKRl2mIEVOtfo5IhLaGWhiB4z4Jar" + "QhdtAkEAlZcAaFc3W8LsrTuBAUiGQHz5HDlykHyLq02gguzhs4xqmocQRZYK2TKL"
//            + "eRgbekifIp//oElMULRmsC9BWUju4QJAL5qwMRqp+lCLf8L5rctcnUZ/oT5Vrij/" + "DHHUXYaiZnz8lDqsFCIPL2MFheDeZ3NUfn8QAY+mLiVJgDtnGsr/uQJAPmlbiqyY" + "oqrN0Th78PnzOiOmJokLSzOKfg9xpyp4Ae5dGk2tyHsaieIdT+otGAnib5suxodr" + "cSTlMXhIikLimg==";

	private static String REQUEST_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMNMLgA3SCwibHTmPipu5qPLVv4DykZb2/JsTpQ3EBZa12ik3Wi+VJDHBxnSDpAOuIIiBAVKBfXt2TBz5s8YoX2tzKlh9q2oj5kROmyWo1wEGoeW43pDLyH8yMIAlyMtUVQx+lsbv3JhIEj+i9UNBiQz27A1fm57NXgEr8GwjylNyArsoEe5W6lxlOMC2yhhoYUjekgckzvdnhyRfa2x+5D0tdUY31v4nfarIwko3MqE3mZ4R/eUSI+w1Rn9Avq67340yItnciZM/LDHjYe7DGTPA2QBM7dfayMy/wqgAEL9SoTOjGO4P++nMd1cyiu2DJJsZWgl9o1R67VYHF1byZAgMBAAECggEABIQijtKtqxST6TGWOBPGdDeWZI8lhOoeDY0zJIxelHvw9XOJBFMnRI4ZBR/ZkofMDmuC/G99ZvUIUBcYpKu3FgOfvwrM4kU9ykQpDONJ/4c81i9rJHGujmwFy7GwfDQ09uVdnJm4A0L/q5PzHD4BKFkgIOj/qNlnxnbEc5BvXljub8Lx8bIWQitR2aCC9OTJSzBC/5954PZVaE2gDDu5kfL1de7+LuMv6V8Zq9GbMBNZ+QGzKFmOu4Ok/p19LUbR/7VF5anuqDNrvjVe6lSH4TiTL3x5CFBzqXag9LJ248fYIy1JWjHcC3bkUM2l6RBiGFoYZpqlDLMMP+ldH1YAAQKBgQD+f6cohGyi5chJycursl+h15CZ7pAFFUdXkK7xe+OUERkp2XLxyzJEXPPWLKxu414NGBVFSdOdWuiJ0HhOvWcNcZ4X/jxn7hz+natGyqNOGeZdQGVQXO8M5XwqST8RGzvIyBOjXpWx3jUx0+TZlVgztu5UU9TA9sFyvGiGQ0pcmQKBgQCNCICSgaGRqc7t9leVHK4Gv2pmjAyBfSYQAPBj/jiw7OZQ8c5YgyesCTF88yFMqyJ4i+fAmR9lzpZkq6lKDfqAeaAVgOrNGJ8jUlpjsIjtra3z2OmdTS/3RVfGT89k/y2JwEdGMgVaJzSbbddlccNS0XCAel2ffsxfHaden6JgAQKBgQDV9ghY3gxfX4Pl1UvwC8Su0gZ2A8T9IQibq74Is7l/MGjRcElc0xJtuyAc/e3yRCE8LPUlDY5lippZ0vRybnYHLXBWh53NWrjxryjiGVb2QHwWBh+nUpp/YyfifTYScazvfpZUER8VLNXYEGckLpS61Z7FMr15hXrZtWDDECuicQKBgDye7PUMd3BvNx0eCCN2Am+wE4IkrLN0Ico7PRkeCywxiAY/Cd0N8x2FjhoRP7z+yoBaUrF8n/xYwlhfWRMV0xkqLl9P3J1v+edq3KOBHDpdrynjkPcmWHnG+qWpZJIbBW3ykjBgC+DmfTxaCH34Wmbc06jF8DxAeoRf8LNBlsABAoGAD0qbkWNklF22ptmSgHdQOVLGSZPhjbW29449d/uHG3L/co/Zm6ihn+2qywf8BLs//LrNY7GSAqidmIIxRyzbFa+hneCPzbt5lhm82OV1hBVl02FrdqyVu4ALwYSE89+Tp4pqeSyPdCHqpyimH6z0J18hKbbvIAOgdiy3F5OyiCk=";
    /**
     * 解密算法
     * cryptograph:密文
     */
    public static String decryptRequestParamValue(String cryptograph) throws Exception {
        PrivateKey key = stringToPrivateKey(REQUEST_PRIVATE_KEY);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }
	
    public static PrivateKey stringToPrivateKey(String s) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] c = null;
        KeyFactory keyFact = null;
        PrivateKey returnKey = null;

        try {

            c = decoder.decodeBuffer(s);
            keyFact = KeyFactory.getInstance("RSA");
        } catch (Exception e) {

            System.out.println("Error in first try catch of stringToPrivateKey");
            e.printStackTrace();
        }

        PKCS8EncodedKeySpec x509KeySpec = new PKCS8EncodedKeySpec(c);
        try { // the next line causes the crash
            returnKey = keyFact.generatePrivate(x509KeySpec);
        } catch (Exception e) {

            System.out.println("Error in stringToPrivateKey");
            e.printStackTrace();
        }

        return returnKey;

    }

}
