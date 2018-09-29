package luyuan.com.exhibition.utils;

import android.content.Context;

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

}
