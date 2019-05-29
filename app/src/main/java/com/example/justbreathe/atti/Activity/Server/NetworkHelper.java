package com.example.justbreathe.atti.Activity.Server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {
    final static String url = "https://sunrin.hyunwoo.dev/mocon/atti";

    public static Retrofit retrofit;

    public static Service getInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Service.class);
    }

}
