package cn.yangchi.chichi_core.net;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import cn.yangchi.chichi_core.net.callback.IError;
import cn.yangchi.chichi_core.net.callback.IFailure;
import cn.yangchi.chichi_core.net.callback.IRequest;
import cn.yangchi.chichi_core.net.callback.ISuccess;
import cn.yangchi.chichi_core.ui.LoaderStyle;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {

    private String mUrl = null;
    private static final WeakHashMap<String, Object> mParams = RestCreator.getParams();
    private IRequest mRequest = null;
    private ISuccess mSuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mloaderStyle = null;
    private File mFile = null;
    private String download_dir = null;
    private String extension = null;
    private String name = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder setParams(WeakHashMap<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    public final RestClientBuilder setParams(String key, Object value) {
        //retrofit不能传入空的键值对
        mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder file(File mFile) {
        this.mFile = mFile;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        File file = new File(filePath);
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mIError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        this.mIFailure = failure;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mloaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mloaderStyle = LoaderStyle.BallClipRotateIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, mParams, mRequest, mSuccess, mIFailure, mIError, mFile, mBody, mContext, mloaderStyle, download_dir, extension, name);
    }
}
