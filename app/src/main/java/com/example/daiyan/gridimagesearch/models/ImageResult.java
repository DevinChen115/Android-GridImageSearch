package com.example.daiyan.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by daiyan on 8/2/15.
 */
public class ImageResult implements Serializable{
    //private static final long serialVersionUID = 7077393082637000915L;
    public String fullUrl;
    public String thumbUrl;
    public String title;

    // new ImageResult(.. raw item json ..)
    public ImageResult(JSONObject json){
        try{
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    // Take an Array of JSON images and return arraylist of image results
    // ImageResult.fromJSONArray([..., ...])
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for(int i=0; i<array.length(); i++){
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        return results;
    }










}
