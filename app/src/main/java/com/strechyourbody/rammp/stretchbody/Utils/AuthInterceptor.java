package com.strechyourbody.rammp.stretchbody.Utils;

import android.content.Context;

import com.strechyourbody.rammp.stretchbody.Services.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {

    private Context context;

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", new SessionManager(this.getContext()).getJWTToken())
                .header("Content-Type", "application/json")
                .method(original.method(),original.body())
                .build();
        return chain.proceed(request);
    }
}