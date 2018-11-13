package cn.yangchi.chichi_core.net;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RestService {

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody requestBody);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String,Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody requestBody);

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String,Object> params);

    //retrofit默认的下载方式是把文件下载到内存里,然后一次性写入文件里,但是容易造成oom
    @Streaming//它的意思就是边下载边写入文件,避免文件写入过大,造成oom
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String,Object> params);


    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
}
