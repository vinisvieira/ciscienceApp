package br.com.ciscience.scienceci.model.dao.remote;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public interface IQuizRemoteAPI {

    @GET("quiz")
    void getQuiz();

    @GET("quiz/{quiz_id}")
    void getQuiz(@Path("quiz_id") Long id);

}
