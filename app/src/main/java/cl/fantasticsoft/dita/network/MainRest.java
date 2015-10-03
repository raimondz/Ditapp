package cl.fantasticsoft.dita.network;

import cl.fantasticsoft.dita.model.Login;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface MainRest {

    @FormUrlEncoded
    @POST("/mobile/user/dologin")
    Call<Login> doLogin(
            @Field("mail") String email,
            @Field("password") String password,
            @Field("gcm") String gcm);

    @FormUrlEncoded
    @POST("/mobile/user/dologout")
    Call<Login> doLogout(@Field("token") String token);
}
