package io.fmc.data.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ChurchEvent {

    String imageURL;
    String eventDate;
    String eventLocation;
    String eventTitle;
    long date;
    long postdate;


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static String getTimeDate(long timestamp) {
        try {
            Date netDate = (new Date(timestamp));
            SimpleDateFormat sfd = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "date error";
        }
    }

    public HashMap<String, Object> getLiked() {
        return liked;
    }

    public void setLiked(HashMap<String, Object> liked) {
        this.liked = liked;
    }

    HashMap<String,Object> liked = new HashMap<>();
}