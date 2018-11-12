package cn.yangchi.chichi_core.net.callback;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import cn.yangchi.chichi_core.ui.ChiChiLoader;
import cn.yangchi.chichi_core.ui.LoaderStyle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallBacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError IERROR;
    private final LoaderStyle LOADERSTYLE;
    private static final Handler HANDLER=new Handler();

    public RequestCallBacks(IRequest REQUEST, ISuccess SUCCESS, IFailure FAILURE, IError iError,LoaderStyle LOADERSTYLE) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.IERROR = iError;
        this.LOADERSTYLE = LOADERSTYLE;

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
        if (LOADERSTYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ChiChiLoader.stopLoading();
                }
            },1000);
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

//        if (LOADERSTYLE != null) {
//            HANDLER.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ChiChiLoader.stopLoading();
//                }
//            },1000);
//        }
    }
}
