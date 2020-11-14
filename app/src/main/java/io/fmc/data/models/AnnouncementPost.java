package io.fmc.data.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Kevin Phillips and Sunday Akinsete on 2/19/17.
 */

public class AnnouncementPost implements Serializable{
    private String eventlocation;
    private String ImageUrl;
    private String eventdate;
    private String eventtitle;
    private String foo;
    private String bar;
    private String type;
    private @ServerTimestamp Long postdate;
//    private Long postdate;

    public AnnouncementPost() {
    }

//    public AnnouncementPost(String eventlocation, String imageUrl,
//                            String eventdate, String eventtitle,
//                            Long postdate) {
//        this.eventlocation = eventlocation;
//        this.ImageUrl = imageUrl;
//        this.eventdate = eventdate;
//        this.eventtitle = eventtitle;
//        this.postdate = postdate;
//    }

    public String getTitle() {
        return eventtitle;
    }

    public String getContent() {
        return eventlocation;
    }

    public boolean getVideo_url() {
        return false;
    }

    public String getText() {
        return eventlocation;
    }

    public enum Type {
        VIDEO,TEXT
    }

    public AnnouncementPost.Type getContentType() {
        if("video".equals(getType())) {
            return AnnouncementPost.Type.VIDEO;
        }else{
            return AnnouncementPost.Type.TEXT;
        }
    }
    public String getType() {
        return type;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;





    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventlocation() {
        return eventlocation;
    }

    public void setEventlocation(String eventlocation) {
        this.eventlocation = eventlocation;
    }

    public String getEventtitle() {
        return eventtitle;
    }

    public void setEventtitle(String eventtitle) {
        this.eventtitle = eventtitle;
    }

    public String getDate() {
        SimpleDateFormat sfd = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());
        return sfd.format(postdate);
    }

//    public void setPostdate(Date postdate) {
//        this.postdate = postdate;
//    }
//    public void setPostdate(Long postdate) {
//        this.postdate = postdate;
//    }

    public static String getTimeDate(long timestamp) {
        try {
            Date netDate = (new Date(timestamp));
            SimpleDateFormat sfd = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "date error";
        }
    }
    public static String getTimeDate(String timestamp) {
        return timestamp;
    }
    public HashMap<String, Object> getLiked() {
        return liked;
    }

    public void setLiked(HashMap<String, Object> liked) {
        this.liked = liked;
    }

    HashMap<String,Object> liked = new HashMap<>();
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("photo", ImageUrl);
        result.put("title", eventtitle);
        result.put("timestamp", ServerValue.TIMESTAMP);
        result.put("description", eventlocation);
//        result.put("type", type);
//        result.put("video_url", video_url);
        return result;
    }

    public java.util.Map<String, String> getpostdate() {
        return ServerValue.TIMESTAMP;
    }

//
//    @Exclude
//    public Long getPostdateLong() {
//        return postdate;
//    }

}
