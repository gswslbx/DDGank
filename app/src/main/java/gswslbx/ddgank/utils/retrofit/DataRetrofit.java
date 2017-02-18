package gswslbx.ddgank.utils.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gswslbx on 2017/2/18.
 */

public class DataRetrofit {
    private static final String BING_API = "http://www.bing.com/";
    private static final String GANK_API = "http://gank.io/api/";

    private static Retrofit retrofit;

    //
    public static Retrofit getRetrofitBing() {
        if (retrofit != null) {
            retrofit = null;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BING_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    //
    public static Retrofit getRetrofitGank() {
        if (retrofit != null) {
            retrofit = null;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(GANK_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
