package lws.banksystem.client.cryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {

    public static String crypt(String message) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = messageDigest.digest(message.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
