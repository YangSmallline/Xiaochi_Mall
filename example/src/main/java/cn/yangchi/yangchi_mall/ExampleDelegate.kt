package cn.yangchi.yangchi_mall

import android.os.Bundle
import android.view.View
import cn.yangchi.chichi_core.delegates.ChiChiDelegate

class ExampleDelegate:ChiChiDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_example
    }

    override fun onBindView(saveInstancestate: Bundle?, rootview: View?) {
    }
}