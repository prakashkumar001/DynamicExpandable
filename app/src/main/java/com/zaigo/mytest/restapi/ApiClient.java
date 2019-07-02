package com.zaigo.mytest.restapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

   // http://dev.madeatworkhouse.co.uk/oakmed/oakmed/htdocs/api/token/token


    private static Retrofit retrofit = null;
    private static String URL_BASE = "https://organicfoodsandcafe.com/nr_butchery/wp-content/themes/tuscany/nrbutchery/";
   // public static String URL_BASE = "http://cassportal.com/webservice/";


  //  public static String URL_BASE = "http://hmsilks.com/cassadmin/webservice/";

    public static Retrofit getClientArrayParse() {
        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/
      /*  OkHttpClient client = new OkHttpClient.Builder()

                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build();*/


        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(5, TimeUnit.MINUTES);
        client.readTimeout(5, TimeUnit.MINUTES);
        client.writeTimeout(5, TimeUnit.MINUTES);


        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE).client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}