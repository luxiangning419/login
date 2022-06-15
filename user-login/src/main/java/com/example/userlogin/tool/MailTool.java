package com.example.userlogin.tool;/*
 *
 * @Author: luxiangning
 * @date:  2022/6/14 下午12:56
 * @Description TODO
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailTool {
    private static final String PATTEN = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\\\.[a-zA-Z0-9]+";

    public static boolean checkEmail(String email) {
        try {
            final Pattern pattern = Pattern.compile(PATTEN);
            final Matcher mat = pattern.matcher(email);
            return mat.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
