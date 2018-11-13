package cn.yangchi.chichi_core.net;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.sax.SAXTransformerFactory;

import cn.yangchi.chichi_core.app.ChiChi_Mall;
import cn.yangchi.chichi_core.app.ConfigType;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 创建了retrofit,okhttp,和接口类
 * 使用各自的hold进行管理
 */
public class RestCreator {

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder {
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static RestService getRestService() {
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
        private static final OkHttpClient.Builder BUILDER=new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) ChiChi_Mall.getConfigurations().get(ConfigType.INTERCEPTORS);

        private static OkHttpClient.Builder addIntercepter(){
            if (INTERCEPTORS != null && INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addIntercepter()
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

}
