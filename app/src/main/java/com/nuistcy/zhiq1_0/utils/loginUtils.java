package com.nuistcy.zhiq1_0.utils;

public class loginUtils {

    public static String generateLoginRequest(String a,String b){
        String ans = new String("LOG#");
        ans += "(";
        ans += a;
        ans += ")";
        ans += "(";
        ans += b;
        ans += ")";
        return ans;
    }

}
