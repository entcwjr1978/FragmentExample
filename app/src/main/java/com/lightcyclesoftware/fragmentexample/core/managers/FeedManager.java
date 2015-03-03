package com.lightcyclesoftware.fragmentexample.core.managers;

import com.lightcyclesoftware.fragmentexample.com.lightcyclesoftware.fragmentexample.core.entities.Culture;
import com.lightcyclesoftware.fragmentexample.com.lightcyclesoftware.fragmentexample.core.entities.Feed;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaCategoriesResponse;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaCulturesResponse;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaSubCategoriesResponse;

import java.util.List;

import retrofit.http.Query;
import rx.Observable;

    /**
    * Created by ewilliams on 2/23/15.
    */
    /*
         A facade for which Fragments and Activities can use to
         get the data needed to display without understanding
         how the data is retrieved
     */
public interface FeedManager {
    Observable<List<Culture>> getCultures();
    Observable<FeedzillaCategoriesResponse> getCategories(String cultureCode, String order);
    Observable<FeedzillaSubCategoriesResponse> getSubCategories(String cultureCode, String order);
}

