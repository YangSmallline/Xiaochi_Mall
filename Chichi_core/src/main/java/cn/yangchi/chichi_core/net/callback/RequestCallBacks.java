package cn.yangchi.chichi_core.net.callback;

import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallBacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError IERROR;

    public RequestCallBacks(IRequest REQUEST, ISuccess SUCCESS, IFailure FAILURE, IError iError) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.IERROR = iError;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        //成功
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                    Log.i("tag", "onResponse: "+response.body());
                }
            }
        }else {
            //失败
            if (IERROR != null) {
                IERROR.onError(response.code(),response.message());
                Log.i("tag", "IERROR: "+response.body());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }
}
