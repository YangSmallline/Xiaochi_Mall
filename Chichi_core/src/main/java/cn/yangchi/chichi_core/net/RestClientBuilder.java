package cn.yangchi.chichi_core.net;

import java.security.Key;
import java.util.Map;
import java.util.WeakHashMap;

import cn.yangchi.chichi_core.net.callback.IError;
import cn.yangchi.chichi_core.net.callback.IFailure;
import cn.yangchi.chichi_core.net.callback.IRequest;
import cn.yangchi.chichi_core.net.callback.ISuccess;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {

    private  String mUrl;
    private  static final WeakHashMap<String,Object> mParams=RestCreator.getParams();
    private  IRequest mRequest;
    private  ISuccess mSuccess;
    private  IFailure mIFailure;
    private  IError mIError;
    private  RequestBody mBody;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url){
        this.mUrl=url;
        return this;
    }
    public final RestClientBuilder setParams(WeakHashMap<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    public final RestClientBuilder setParams(String key,Object value){
        //retrofit不能传入空的键值对
        mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }
    public final RestClientBuilder onRequest(IRequest request){
        this.mRequest=request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success){
        this.mSuccess=success;
        return this;
    }
    public final RestClientBuilder error(IError error){
        this.mIError=error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.mIFailure=failure;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl, mParams, mRequest, mSuccess, mIFailure, mIError, mBody);
    }
}
