package cn.yangchi.chichi_core.net.download

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import cn.yangchi.chichi_core.app.ChiChi_Mall
import cn.yangchi.chichi_core.net.callback.IRequest
import cn.yangchi.chichi_core.net.callback.ISuccess
import cn.yangchi.chichi_core.util.FileUtil
import okhttp3.ResponseBody
import java.io.File
import java.util.*
import java.util.concurrent.Executor

class SaveFileTask : AsyncTask<Objects, Void, File> {

    lateinit var REQUEST: IRequest
    lateinit var SUCCESS: ISuccess

    constructor(REQUEST: IRequest, SUCCESS: ISuccess) : super() {
        this.REQUEST = REQUEST
        this.SUCCESS = SUCCESS
    }

    override fun doInBackground(vararg params: Objects?): File {
        var downloadDir: String = params[0] as String
        var extension: String = params[1] as String
        var body: ResponseBody = params[2] as ResponseBody
        var name: String = params[3] as String

        var ins = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loader"
        }

        if (extension == null || extension.equals("")) {
            downloadDir = ""
        }

        if (name == null) {
            return FileUtil.writeToDisk(ins, downloadDir, extension.toUpperCase(), extension)
        } else {
            return FileUtil.writeToDisk(ins, downloadDir, name)
        }
    }

    override fun onPostExecute(file: File?) {
        super.onPostExecute(file)
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file?.path!!)
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd()
        }

        file?.let {
            autoInstallApk(file)
        }

    }

    fun autoInstallApk(file: File) {
        if (FileUtil.getExtension(file.path).equals("apk")) {
            var install = Intent()
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            install.setAction(Intent.ACTION_VIEW)
            install.setDataAndType(Uri.fromFile(file), "app")
            ChiChi_Mall.getApplication().startActivity(install)
        }
    }

    fun executeOnExecutor(threadPoolExecutor: Executor, download_dir: String, extension: String, responseBody: ResponseBody, name: String) {
    }
}