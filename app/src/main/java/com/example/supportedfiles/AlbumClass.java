package com.example.supportedfiles;

import android.graphics.drawable.Drawable;

import com.example.root.picmania.R;

/**
 * Created by root on 4/27/15.
 */
public class AlbumClass {

    public final Drawable icon;
    public final String title;
    public final String description;
    public AlbumClass(String title,String description,Drawable icon){
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

}
