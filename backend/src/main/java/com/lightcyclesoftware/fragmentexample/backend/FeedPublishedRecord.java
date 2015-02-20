package com.lightcyclesoftware.fragmentexample.backend;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by ewilliams on 2/17/15.
 */
public class FeedPublishedRecord {


    @Id
    Long id;

    @Index
    private Date feedPublishedDate;
    // you can add more fields...

    public FeedPublishedRecord() {
    }

    public Date getfeedPublishedDate() {
        return feedPublishedDate;
    }

    public void setfeedPublishedDate(Date feedPublishedDate) {
        this.feedPublishedDate = feedPublishedDate;
    }
}
