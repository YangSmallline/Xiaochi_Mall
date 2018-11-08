package cn.yangchi.chichi_core.app;

import android.hardware.camera2.params.InputConfiguration;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

public class Configurator {

    private static final HashMap<String, Object> CHICHI_CONFIGS = new HashMap<String, Object>();

    private static final ArrayList<IconFontDescriptor> ICONS=new ArrayList<>();

    private Configurator(){
        //一开始肯定是没有初始化好的
        CHICHI_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE=new Configurator();
    }

    final HashMap<String,Object> getChiCHiConfigs(){
        return CHICHI_CONFIGS;
    }

    //代表已经初始化好了
    public final void configure(){
        initICons();
        CHICHI_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    //加入配置主机host
    public final Configurator widthAPiHost(String host){
        CHICHI_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    //检查是否配置成功
    public final void checkConfiguaration(){
        final boolean isReady= (boolean) CHICHI_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuaration is not ready,please call .configure end init()");
        }
    }

    public final <T> T getConfiguartion(Enum<ConfigType> key){
        checkConfiguaration();
        return (T) CHICHI_CONFIGS.get(key);
    }

    private void initICons(){
        if (ICONS.size() > 0) {
            Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

}
