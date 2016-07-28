package br.com.ciscience.scienceci.model.dao.remote.impl;

import br.com.ciscience.scienceci.model.dao.remote.IRankingRemoteAPI;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.util.MyOkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public class RankingRemoteAPI {

    private static RankingRemoteAPI sInstance;
    private IRankingRemoteAPI mIRankingRemoteAPI;

    private RankingRemoteAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(MyOkHttpClient.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mIRankingRemoteAPI = retrofit.create(IRankingRemoteAPI.class);
    }

    public static RankingRemoteAPI getInstance() {
        if (sInstance == null) {
            synchronized (RankingRemoteAPI.class) {
                if (sInstance == null) {
                    sInstance = new RankingRemoteAPI();
                }
            }
        }
        return sInstance;
    }

    public IRankingRemoteAPI getAPI() {
        return this.mIRankingRemoteAPI;
    }

}
