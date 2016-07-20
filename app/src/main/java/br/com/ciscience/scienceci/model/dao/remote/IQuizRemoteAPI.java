package br.com.ciscience.scienceci.model.dao.remote;

import java.util.List;

import br.com.ciscience.scienceci.model.entity.impl.Quiz;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public interface IQuizRemoteAPI {

    @GET("quiz/current")
    Observable<List<Quiz>> getQuiz(
            @Header("token") String token
            );

    @GET("quiz/{quiz_id}")
    Observable<Quiz> getQuiz(@Path("quiz_id") Long id);

}
