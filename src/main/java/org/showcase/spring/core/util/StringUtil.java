package org.showcase.spring.core.util;

public class StringUtil {
   public static boolean isEmpty(String value){
     return   value==null|| "".equals(value);
    }
    public static boolean isNotEMpty(String value){
        return   !isEmpty(value);
    }

    public static String lowerCaseFirst(String str) {
        return lowerCase(str.substring(0, 1)) + str.substring(1);
    }
    public static String upperCaseFirst(String str) {
        return upperCase(str.substring(0, 1)) + str.substring(1);
    }
    public static String upperCase(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
    public static String lowerCase(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public static void main(String[] args) {
        System.out.println(lowerCase("ABC"));
    }
}
