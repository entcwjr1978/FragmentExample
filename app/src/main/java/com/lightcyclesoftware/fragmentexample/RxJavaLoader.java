package com.lightcyclesoftware.fragmentexample;

import android.content.Context;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ewilliams on 2/22/15.
 */
public class RxJavaLoader extends Loader {

    private List<Message> mData;
    /**
     * Stores away the application context associated with context. Since Loaders can be used
     * across multiple activities it's dangerous to store the context directly.
     *
     * @param context used to retrieve the application context.
     */

    /**
        Loader implementation that utilizes RxJava library for Asynchronous tasks
     */

    public RxJavaLoader(Context context) {
        super(context);
    }

    @Override
    public void onStartLoading() {
        getMessages();
    }

    @Override
    public void onStopLoading() {

    }

    @Override
    public void onForceLoad() {
        getMessages();
    }

    @Override
    public void onReset() {
        getMessages();
    }


    private void releaseResources(List<Message> data) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }

    private Observable<List<Message>> getMessages() {
        return Observable.create(
                new Observable.OnSubscribe<List<Message>>() {
                    @Override
                    public void call(Subscriber<? super List<Message>> subscriber) {
                        List<Message> data = new ArrayList<Message>();
                        AndroidSaxFeedParser wAndroidSaxFeedParser = new AndroidSaxFeedParser("http://feeds.abcnews.com/abcnews/usheadlines");
                        data = wAndroidSaxFeedParser.parse();
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    }
                }
        );
    }
}
