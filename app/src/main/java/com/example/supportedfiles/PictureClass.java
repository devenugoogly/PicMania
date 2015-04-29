package com.example.supportedfiles;

import android.graphics.drawable.Drawable;

/**
 * Created by ashish on 4/28/2015.
 */
public class PictureClass {
    public Drawable thumbnail;
    public String name;
    public String album;
    public String caption;
    public String location;

    public PictureClass(String name,String caption,Drawable thumbnail){
        this.name = name;
        this.caption = caption;
        this.thumbnail = thumbnail;
    }
}
