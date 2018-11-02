package luyuan.com.exhibition.utils;

import android.content.Context;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class ScreenUtil {
    public static int getStateBarHeight(Context context){
        Class c = null;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

}
