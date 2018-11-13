package cn.yangchi.chichi_core.net;

import android.content.Context;

import java.io.File;
import java.util.WeakHashMap;

import cn.yangchi.chichi_core.net.callback.IError;
import cn.yangchi.chichi_core.net.callback.IFailure;
import cn.yangchi.chichi_core.net.callback.IRequest;
import cn.yangchi.chichi_core.net.callback.ISuccess;
import cn.yangchi.chichi_core.net.callback.RequestCallBacks;
import cn.yangchi.chichi_core.net.download.DownloadHandler;
import cn.yangchi.chichi_core.ui.LoaderStyle;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private LoaderStyle LOADER_STYLE;
    private Context CONTEXT;
    private File FILE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;


    public RestClient(String url,
                      WeakHashMap<String, Object> mParams,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError ierror,
                      File file,
                      RequestBody body,
                      Context context
            , LoaderStyle loaderStyle,
                      String download_dir,
                      String extension,
                      String name) {
        this.URL = url;
        FILE = file;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        PARAMS.putAll(mParams);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.iError = ierror;
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
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

        if (LOADER_STYLE != null) {
//            ChiChiLoader.showLoading(CONTEXT,LOADER_STYLE.name());
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
            case POST_RAW:
                call = restService.postRaw(URL, BODY);
                break;
            case PUT_RAW:
                call = restService.postRaw(URL, BODY);
                break;
            case UPLOAD:
                RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName());
                call = RestCreator.getRestService().upload(URL, body);
                break;
            default:
                break;
        }

//        if (call == null) {
        call.enqueue(getRequestCallBack());
//        }
    }

    private Callback<String> getRequestCallBack() {

        return new RequestCallBacks(
                REQUEST, SUCCESS, FAILURE, iError, LOADER_STYLE
        );

    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("parmas must not be null?");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("parmas must not be null?");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownloadHandler(URL,REQUEST,SUCCESS,FAILURE,iError,DOWNLOAD_DIR,EXTENSION,NAME).handleDownload();
    }


}
