package cn.yangchi.chichi_core.net;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import cn.yangchi.chichi_core.app.ChiChi_Mall;
import cn.yangchi.chichi_core.app.ConfigType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//处理网络的创造类
public class RestCreator {

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder{
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    //创建retrofit
    private static final class RetrofitHolder {
        //baseurl
        private static final String BASE_URL = (String) ChiChi_Mall.getConfigurations().get(ConfigType.API_HOST.name());

        public static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    //创建okhtttp
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

}
