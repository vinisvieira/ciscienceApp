package br.com.ciscience.scienceci.model.dao.remote;

import java.util.List;

import br.com.ciscience.scienceci.model.entity.impl.Student;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public interface IRankingRemoteAPI {

    @GET("student/ranking/mobile?random=132123123")
    Observable<List<Student>> getRanking(
            @Header("token") String token
    );

}
