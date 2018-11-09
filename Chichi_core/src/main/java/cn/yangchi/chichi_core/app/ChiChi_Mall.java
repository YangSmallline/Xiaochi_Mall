package cn.yangchi.chichi_core.app;


import android.content.Context;

import java.util.HashMap;

public final class ChiChi_Mall {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context);
        return Configurator.getInstance();
    }

    public static HashMap<String, Object> getConfigurations(){
        return Configurator.getInstance().getChiCHiConfigs();
    }

    public Context getApplication(){
        return (Context) Configurator.getInstance().getChiCHiConfigs().get(ConfigType.APPLICATION_CONTEXT.name());
    }

}
