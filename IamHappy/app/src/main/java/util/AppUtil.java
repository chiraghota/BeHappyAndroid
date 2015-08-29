package util;

import android.content.Context;
import android.widget.Toast;

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

    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }
}
