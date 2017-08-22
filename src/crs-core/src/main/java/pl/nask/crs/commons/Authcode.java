package pl.nask.crs.commons;

import java.util.Random;

public class Authcode {
    private static char[] charArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static int CODE_LENGTH = 12;

    public static String createCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = charArray[random.nextInt(charArray.length)];
            sb.append(c);
        }
        return sb.toString();
    }

}
