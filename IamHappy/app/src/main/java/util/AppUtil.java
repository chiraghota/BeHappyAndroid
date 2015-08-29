package util;

/**
 * Created by Prateek on 29/08/15.
 */
public class AppUtil {

    public static boolean getNullCheck(String s){
        if(s == null || s.length() == 0 || s.equals("null")){
            return false;
        }
        return true;
    }
}
