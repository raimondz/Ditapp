package cl.apd.ditapp.network;

import cl.apd.ditapp.model.Login;
import cl.apd.ditapp.model.Respuesta;
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

    @FormUrlEncoded
    @POST("/solicitud")
    Call<Respuesta> solicitud(
            @Field("rut") String rut,
            @Field("tramite") String password,
            @Field("sucursal") int id,
            @Field("hora") String hora);
}
