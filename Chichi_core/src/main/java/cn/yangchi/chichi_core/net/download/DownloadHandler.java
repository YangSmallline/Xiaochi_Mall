package cn.yangchi.chichi_core.net.download;

import android.os.AsyncTask;

import java.util.WeakHashMap;

import cn.yangchi.chichi_core.net.RestCreator;
import cn.yangchi.chichi_core.net.callback.IError;
import cn.yangchi.chichi_core.net.callback.IFailure;
import cn.yangchi.chichi_core.net.callback.IRequest;
import cn.yangchi.chichi_core.net.callback.ISuccess;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError IERROR;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;


    public DownloadHandler(String url,
                           IRequest request,
                           ISuccess success,
                           IFailure failure,
                           IError ierror,
                           String download_dir,
                           String extension,
                           String name) {
        URL = url;
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        IERROR = ierror;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
    }

    public void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator
                .getRestService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
//                            task.execute(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                            //这里一定要注意判断，否则文件下载不全
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (IERROR != null) {
                                IERROR.onError(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                            RestCreator.getParams().clear();
                        }
                    }
                });
    }
}
