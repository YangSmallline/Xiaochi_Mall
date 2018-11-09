package cn.yangchi.chichi_core.net;

import java.util.Map;
import java.util.WeakHashMap;

import cn.yangchi.chichi_core.net.callback.IError;
import cn.yangchi.chichi_core.net.callback.IFailure;
import cn.yangchi.chichi_core.net.callback.IRequest;
import cn.yangchi.chichi_core.net.callback.ISuccess;
import cn.yangchi.chichi_core.net.callback.RequestCallBacks;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError iError;
    private final RequestBody BODY;

    public RestClient(String url,
                      WeakHashMap<String, Object> mParams,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError ierror,
                      RequestBody body) {
        this.URL = url;
        PARAMS.putAll(mParams);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.iError = ierror;
        this.BODY = body;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService restService = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        switch (method) {
            case GET:
                call = restService.get(URL, PARAMS);
                break;
            case POST:
                call = restService.post(URL, PARAMS);
                break;
            case PUT:
                call = restService.put(URL, PARAMS);
                break;
            case DELETE:
                call = restService.delete(URL, PARAMS);
                break;
            default:
                break;
        }

//        if (call == null) {
            call.enqueue(getRequestCallBack());
//        }
    }

    private Callback<String> getRequestCallBack(){

        return new RequestCallBacks(
                REQUEST,SUCCESS,FAILURE,iError
        );

    }

    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        request(HttpMethod.POST);
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void put(){
        request(HttpMethod.PUT);
    }



}
