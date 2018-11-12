package cn.yangchi.chichi_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import cn.yangchi.chichi_core.app.ChiChi_Mall;

public class DimenUtil {

    //得到屏幕的宽
    public static int getScreenWidth(){
        final Resources resources=ChiChi_Mall.getApplication().getResources();

        final DisplayMetrics dm=resources.getDisplayMetrics();

        return dm.widthPixels;
    }
    //得到屏幕的高
    public static int getScreenHeight(){
        final Resources resources=ChiChi_Mall.getApplication().getResources();

        final DisplayMetrics dm=resources.getDisplayMetrics();

        return dm.heightPixels;
    }
}
