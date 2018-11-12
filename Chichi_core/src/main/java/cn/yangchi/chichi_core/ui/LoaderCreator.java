package cn.yangchi.chichi_core.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

public class LoaderCreator {

    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String tpye, Context context) {

        //新建一个view
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        //获得view
        if (LOADING_MAP.get(tpye)==null){
            final Indicator indicator = getIndicator(tpye);
            LOADING_MAP.put(tpye, indicator);
        }

        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(tpye));

        return avLoadingIndicatorView;
    }


    //通过类名反射得到drawable
    private static Indicator getIndicator(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        if (!name.contains(".")) {

            final String defaultPackageName=AVLoadingIndicatorView.class.getPackage().getName();

            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        drawableClassName.append(name);

        try {
            final Class<?> drawableClass=Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
