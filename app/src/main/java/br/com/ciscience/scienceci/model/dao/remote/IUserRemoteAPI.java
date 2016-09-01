package br.com.ciscience.scienceci.model.dao.remote;

import br.com.ciscience.scienceci.model.entity.impl.Student;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("student/katana")
    Observable<Student> getByToken(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("student/recovery")
    Observable<Void> recoveryPassword(
            @Field("email") String email,
            @Header("recoveryKey") String recoveryKey
    );

    @Multipart
    @POST("student/avatar/mobile")
    Observable<Void> changeAvatar(
            @Part MultipartBody.Part file,
            @Field("token") String token
    );

}
