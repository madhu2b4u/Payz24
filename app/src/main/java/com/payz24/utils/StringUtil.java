package com.payz24.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by mahesh on 1/8/2018.
 */

public class StringUtil {

    public static String join(List<String> parts, String delim) {
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < parts.size(); index++) {
            String part = parts.get(index);
            result.append(part);
            if (delim != null && index < parts.size() - 1) {
                result.append(delim);
            }
        }
        return result.toString();
    }

    public static String getHash25(String key, String algorithmName) {
        MessageDigest digest;
        String hash = "";
        try {
            digest = MessageDigest.getInstance(algorithmName);
            digest.update(key.getBytes());
            hash = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }
}
