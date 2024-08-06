package com.santho.lms.helper;

public class ArrayToStringHelper {
    public static String arrToString(char[] arr){
        StringBuilder sb = new StringBuilder();
        for(char ch:arr)
            sb.append(ch);
        return sb.toString();
    }
}
