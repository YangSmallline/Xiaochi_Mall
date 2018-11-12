package cn.yangchi.chichi_core.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import cn.yangchi.chichi_core.R;
import cn.yangchi.chichi_core.util.DimenUtil;

public class ChiChiLoader {
    private static String TAG = ChiChiLoader.class.getSimpleName();
    private static final int LOADER_SIZE_SCALE=8;
    private static final int LOADER_OFFEST_SCALE=8;
    private static final String DEFAULT_LOADER=LoaderStyle.BallClipRotateIndicator.name();

    private static final ArrayList<Dialog> LOADERS = new ArrayList<>();

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context,type.name());
    }

    public static void showLoading(Context context, String tpye) {
        //如果是dialog这个类显示你在webview和其他view上显示会报错
        AppCompatDialog dialog= new AppCompatDialog(context);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(tpye, context);

        dialog.setContentView(avLoadingIndicatorView);

        int devicewidth=DimenUtil.getScreenWidth();

        int deviceHeight=DimenUtil.getScreenHeight();

        final Window dialogwindow=dialog.getWindow();

        if (dialogwindow != null) {
            WindowManager.LayoutParams lp=dialogwindow.getAttributes();
            lp.width=devicewidth/LOADER_SIZE_SCALE;
            lp.height=deviceHeight/LOADER_SIZE_SCALE;
            lp.height=lp.height+deviceHeight/LOADER_OFFEST_SCALE;
            lp.gravity=Gravity.CENTER;
        }

        LOADERS.add(dialog);

        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context,DEFAULT_LOADER);
    }


    public static void stopLoading() {
        for (Dialog dialog : LOADERS) {
            Log.i("tag", "stopLoading1: "+dialog.toString());
            if (dialog != null) {
               dialog.dismiss();
            }
        }
    }
}
