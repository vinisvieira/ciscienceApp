package br.com.ciscience.scienceci.model.dao.remote.impl;

import br.com.ciscience.scienceci.model.dao.remote.IQuizRemoteAPI;
import br.com.ciscience.scienceci.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public class QuizRemoteAPI {

    private static QuizRemoteAPI sInstance;
    private IQuizRemoteAPI mIQuizRemoteAPI;

    private QuizRemoteAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mIQuizRemoteAPI = retrofit.create(IQuizRemoteAPI.class);
    }

    public static QuizRemoteAPI getInstance() {
        if (sInstance == null) {
            synchronized (QuizRemoteAPI.class) {
                if (sInstance == null) sInstance = new QuizRemoteAPI();
            }
        }
        return sInstance;
    }

    public IQuizRemoteAPI getAPI() {
        return this.mIQuizRemoteAPI;
    }

}
