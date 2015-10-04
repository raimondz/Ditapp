package cl.apd.ditapp.network;

import cl.apd.ditapp.model.Login;
import cl.apd.ditapp.model.Respuesta;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface MainRest {

    @FormUrlEncoded
    @POST("/cliente/login")
    Call<Login> doLogin(
            @Field("rut") String rut,
            @Field("pass") String password,
            @Field("gcm") String gcm);


    @FormUrlEncoded
    @POST("/solicitud")
    Call<Respuesta> solicitud(
            @Field("rut") String rut,
            @Field("tramite") String password,
            @Field("sucursal") int id,
            @Field("hora") String hora);
}
