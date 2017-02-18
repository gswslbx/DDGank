package gswslbx.ddgank.utils.retrofit;

import gswslbx.ddgank.bean.Bing;
import gswslbx.ddgank.bean.GanHuo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Gswslbx on 2017/2/18.
 */

public interface DataService {

    //获取bing的每日图片
    @GET("HPImageArchive.aspx")
    Observable<Bing> getBing(
            @Query("format") String format,
            @Query("idx") int idx,
            @Query("n") int n
    );

    //可自定义获取gank的数据
    @GET("data/{type}/{count}/{page}")
    Observable<GanHuo> getGanHuo(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );

    //获取gank的搜索的数据
    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Observable<GanHuo> getSearch(
            @Path("key") String key,
            @Path("count") int count,
            @Path("page") int page);

    //获取gank的随机图片
    @GET("random/data/福利/{number}")
    Observable<GanHuo> getRandomFuli(@Path("number") int number);
}
