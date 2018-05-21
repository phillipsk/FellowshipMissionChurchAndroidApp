package io.fmc.data.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akinsete on 2/19/17.
 */

public class Announcement implements Serializable{

    public enum Type {
        VIDEO,TEXT
    }

    String photo;
    String title;
    String description;
    String type;
    String content;


    Type contentType;

    public Object getLikes() {
        return likes;
    }

    public void setLikes(Object likes) {
        this.likes = likes;
    }

    Object likes;

    public HashMap<String, Object> getLiked() {
        return liked;
    }

    public void setLiked(HashMap<String, Object> liked) {
        this.liked = liked;
    }

    HashMap<String,Object> liked = new HashMap<>();

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content_type;

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    long created_at;

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    String video_url;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String picture) {
        this.photo = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Type getContentType() {
        if("video".equals(getType())) {
            return Type.VIDEO;
        }else{
            return Type.TEXT;
        }
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("photo", photo);
        result.put("title", title);
        result.put("timestamp", ServerValue.TIMESTAMP);
        result.put("description", description);
        result.put("type", type);
        result.put("video_url", video_url);
        return result;
    }

}
