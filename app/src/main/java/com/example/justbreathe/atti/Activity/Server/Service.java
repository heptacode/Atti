package com.example.justbreathe.atti.Activity.Server;

import com.example.justbreathe.atti.Activity.Object.LoginData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    @FormUrlEncoded
    @POST("/signin")
    Call<LoginData> signin(@Field("email") String id, @Field("passwd") String pw);

    @FormUrlEncoded
    @POST("/email")
    Call<Boolean> email(@Field("email") String email);

    @FormUrlEncoded
    @POST("/signup")
    Call<Boolean> signup(@Field("korean") boolean korean,@Field("name") String name,@Field("email") String email, @Field("passwd") String passwd);
}
