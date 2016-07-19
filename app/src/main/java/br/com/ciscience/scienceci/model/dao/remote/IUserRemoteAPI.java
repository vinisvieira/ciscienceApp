package br.com.ciscience.scienceci.model.dao.remote;

import br.com.ciscience.scienceci.model.entity.impl.Student;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public interface IUserRemoteAPI {

    @FormUrlEncoded
    @POST("login/student")
    Observable<Student> login(
            @Field("email") String email,
            @Field("password") String password
    );

}
