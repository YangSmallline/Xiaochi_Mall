package cn.yangchi.yangchi_mall

import cn.yangchi.chichi_core.activities.ProxyActivity
import cn.yangchi.chichi_core.delegates.BaseDelegate

class ExampleActivity : ProxyActivity() {

    override fun setRootDelegate(): BaseDelegate {
        return ExampleDelegate()
    }

}
