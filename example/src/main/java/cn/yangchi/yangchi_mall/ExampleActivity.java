package cn.yangchi.yangchi_mall;

import cn.yangchi.chichi_core.activities.ProxyActivity;
import cn.yangchi.chichi_core.delegates.BaseDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public BaseDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
