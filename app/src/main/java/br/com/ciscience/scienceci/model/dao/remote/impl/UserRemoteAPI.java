package br.com.ciscience.scienceci.model.dao.remote.impl;

import com.google.gson.Gson;

import br.com.ciscience.scienceci.model.dao.remote.IUserRemoteAPI;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.util.MyOkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public class UserRemoteAPI {

    private static UserRemoteAPI sInstance;
    private IUserRemoteAPI mIUserRemoteAPI;

    private UserRemoteAPI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(MyOkHttpClient.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mIUserRemoteAPI = retrofit.create(IUserRemoteAPI.class);
    }

    public static UserRemoteAPI getInstance() {
        if (sInstance == null) {
            synchronized (UserRemoteAPI.class) {
                if (sInstance == null) sInstance = new UserRemoteAPI();
            }
        }
        return sInstance;
    }

    public IUserRemoteAPI getAPI() {
        return this.mIUserRemoteAPI;
    }

}
