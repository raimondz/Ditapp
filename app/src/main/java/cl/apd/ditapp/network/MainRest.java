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
    Call<Respuesta> doLogin(
            @Field("rut") String rut,
            @Field("password") String password,
            @Field("gcm") String gcm);

    @FormUrlEncoded
    @POST("/solicitud")
    Call<Respuesta> solicitud(
            @Field("rut") String rut,
            @Field("tramite") String password,
            @Field("sucursal") int id,
            @Field("hora") String hora);

    @FormUrlEncoded
    @POST("/cliente")
    Call<Respuesta> createUser(
            @Field("rut") String rut,
            @Field("correo") String mail,
            @Field("telefono") String phone
    );
}
