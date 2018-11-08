package cn.yangchi.yangchi_mall;

import android.app.Application;

import com.joanzapata.iconify.fontawesome.*;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import cn.yangchi.chichi.ec.icon.FontEcModul;
import cn.yangchi.chichi_core.app.ChiChi_Mall;

public class ExmpleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ChiChi_Mall.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModul())
                .widthAPiHost("http://127.0.0.1")
                .configure();
    }
}
