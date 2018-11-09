package cn.yangchi.yangchi_mall

import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.yangchi.chichi_core.delegates.ChiChiDelegate
import cn.yangchi.chichi_core.net.RestClient
import cn.yangchi.chichi_core.net.callback.IError
import cn.yangchi.chichi_core.net.callback.IFailure
import cn.yangchi.chichi_core.net.callback.ISuccess

class ExampleDelegate : ChiChiDelegate() {

    override fun setLayout(): Any {
        return R.layout.delegate_example;
    }

    override fun onBindView(saveInstancestate: Bundle?, rootview: View?) {

        test()
    }

    fun test() {
      RestClient.builder()
                .url("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
                .failure(object : IFailure {
                    override fun onFailure() {
                    }
                })
                .setParams("", "")
                .failure(object :IFailure{
                    override fun onFailure() {
                        Toast.makeText(context,"失败",Toast.LENGTH_SHORT).show()
                    }

                })
                .error(object : IError {
                    override fun onError(code: Int, msg: String) {
                        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                    }
                })
                .success(object : ISuccess {
                    override fun onSuccess(response: String) {
                        Toast.makeText(context,response,Toast.LENGTH_SHORT).show()
                    }
                })
                .build()
                .get()
    }

}