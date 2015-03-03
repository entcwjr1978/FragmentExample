package com.lightcyclesoftware.fragmentexample.webservices;

/**
 * Created by ewilliams on 2/23/15.
 */
import com.lightcyclesoftware.fragmentexample.com.lightcyclesoftware.fragmentexample.core.entities.Culture;

import java.util.List;

import retrofit.Profiler;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.subjects.PublishSubject;


public interface FeedzillaClient {

    public class Factory {
        public static FeedzillaClient create(PublishSubject<NetworkActivity> networkSubject) {
            return new RestAdapter.Builder()
                    .setEndpoint("http://api.feedzilla.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    //.setRequestInterceptor(request -> {
                    //    request.addQueryParam("APPID", "d7a15a2cfa97fe82b402d8da342eec37");
                    //})
                    .setErrorHandler(retrofitError -> {
                        networkSubject.onError(retrofitError);
                        return retrofitError;
                    })
                    .setProfiler(new Profiler() {
                        public Object beforeCall() {
                            networkSubject.onNext(NetworkActivity.STARTED);
                            return null;
                        }

                        public void afterCall(RequestInformation requestInformation, long l, int i, Object o) {
                            networkSubject.onNext(NetworkActivity.ENDED);
                        }
                    })
                    .build()
                    .create(FeedzillaClient.class);
        }
    }

    @GET("/v1/cultures.json")
    Observable<List<Culture>> getCultures();

    @GET("/v1/categories.json")
    Observable<FeedzillaCategoriesResponse> getCategories(@Query("culture_code") String cultureCode, @Query("culture_code") String order);

    @GET("/v1/subcategories.json")
    Observable<FeedzillaSubCategoriesResponse> getSubCategories(@Query("culture_code") String cultureCode, @Query("culture_code") String order);

    @GET("/v1/categories/{category_id}/articles.json")
    Observable<FeedzillaArticlesResponse> getArticles(@Path("category_id") int categoryId, @Query("count") int count,@Query("since") String since, @Query("order") String order, @Query("client_source") String clientSource, @Query("title_only") int titleOnly);

    @GET("/v1/categories/{category_id}/articles/search.json")
    Observable<FeedzillaArticlesResponse> searchArticles(@Path("category_id") int categoryId, @Query("count") int count,@Query("since") String since, @Query("order") String order, @Query("client_source") String clientSource, @Query("title_only") int titleOnly);

}
