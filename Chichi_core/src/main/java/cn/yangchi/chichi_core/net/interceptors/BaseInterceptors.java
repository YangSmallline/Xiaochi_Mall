package cn.yangchi.chichi_core.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseInterceptors implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

    protected LinkedHashMap<String,String> getUrlParameters(Chain chain) {
        final HttpUrl url=chain.request().url();
        int size=url.querySize();
        final LinkedHashMap<String,String> params=new LinkedHashMap<>();

        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    protected String getUrlParameters(Chain chain, String key) {
        final Request request=chain.request();
        return request.url().queryParameter(key);
    }

//    protected LinkedHashMap<String, String> getBodyparameters(Chain chain) {
//
//    }
}
