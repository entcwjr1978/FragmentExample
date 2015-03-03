package com.lightcyclesoftware.fragmentexample.core.managers;

import com.lightcyclesoftware.fragmentexample.com.lightcyclesoftware.fragmentexample.core.entities.Culture;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaCategoriesResponse;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaClient;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaCulturesResponse;
import com.lightcyclesoftware.fragmentexample.webservices.FeedzillaSubCategoriesResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ewilliams on 2/24/15.
 */
public class CoordinatedFeedManager implements FeedManager {

    private FeedzillaClient feedzillaClient;

    @Inject
    public CoordinatedFeedManager(FeedzillaClient feedzillaClient) {
        this.feedzillaClient = feedzillaClient;
    }

    @Override
    public Observable<List<Culture>> getCultures() {
        return feedzillaClient.getCultures();
    }

    @Override
    public Observable<FeedzillaCategoriesResponse> getCategories(String cultureCode, String order) {
        return feedzillaClient.getCategories(cultureCode, order);
    }

    @Override
    public Observable<FeedzillaSubCategoriesResponse> getSubCategories(String cultureCode, String order) {
        return feedzillaClient.getSubCategories(cultureCode, order);
    }
}
