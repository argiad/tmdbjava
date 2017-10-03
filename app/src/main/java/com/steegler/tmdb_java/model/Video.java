package com.steegler.tmdb_java.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Video respond data model
 *
 * Created by argi on 10/2/17.
 */

public class Video implements Serializable{

    private String id;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public static Video build(JSONObject jsonObject) throws JSONException{
        Video video = new Video();
        video.id = jsonObject.optString("id");
        video.key = jsonObject.optString("key");
        video.name = jsonObject.optString("name");
        video.site = jsonObject.optString("site");
        video.size = jsonObject.optInt("size");
        video.type = jsonObject.optString("type");
        return video;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
