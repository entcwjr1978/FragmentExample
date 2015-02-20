package com.lightcyclesoftware.fragmentexample.backend;


import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.ThreadManager;
import com.sun.syndication.io.FeedException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import static com.lightcyclesoftware.fragmentexample.backend.OfyService.ofy;

/**
 * Created by ewilliams on 2/17/15.
 */
public class FeedObserver implements ServletContextListener {

    private static final String API_KEY = System.getProperty("gcm.api.key");
    private ScheduledExecutorService scheduler;
    private static final Logger log = Logger.getLogger(FeedObserver.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor(ThreadManager.currentRequestThreadFactory());
        scheduler.scheduleAtFixedRate(new FeedUpdateTask(), 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }




    public class FeedUpdateTask extends TimerTask {
        public void run() {
            try {
                checkForFeedChange(new URL("http://feeds.feedburner.com/blogspot/hsDu"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FeedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkForFeedChange(URL feedUrl) throws IOException, FeedException {
        sendMessage("update");

        /*
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build (new XmlReader(feedUrl));
        List<FeedPublishedRecord> records = ofy().load().type(FeedPublishedRecord.class).limit(1).list();

        if (records.size() == 1) {
            Date previousDate = records.get(0).getfeedPublishedDate();
            if (feed.getPublishedDate().after(previousDate)) {
                //send message to update feed
                MessagingEndpoint messagingEndpoint = new MessagingEndpoint();
                messagingEndpoint.sendMessage("update");

                //update previous feed record in storage
                ofy().delete().entity(records.get(0)).now();
                FeedPublishedRecord wFeedPublishedRecord = new FeedPublishedRecord();
                wFeedPublishedRecord.setfeedPublishedDate(feed.getPublishedDate());
                ofy().save().entity(wFeedPublishedRecord).now();
                return true;
            }
        }
        */
        return false;
    }

    public void sendMessage(@Named("message") String message) throws IOException {
        if (message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", message).build();
        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(10).list();
        for (RegistrationRecord record : records) {
            Result result = sender.send(msg, record.getRegId(), 5);
            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getRegId());
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                    record.setRegId(canonicalRegId);
                    ofy().save().entity(record).now();
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                } else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }
    }
}
