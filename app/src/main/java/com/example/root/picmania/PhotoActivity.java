package com.example.root.picmania;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.supportedfiles.PhotoCommentViewAdapter;
import com.example.supportedfiles.PhotoComments;
import com.raweng.built.BuiltUser;

import java.util.List;

/**
 * Created by Utsav on 4/28/2015.
 */
public class PhotoActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private List<PhotoComments> dataItems;
    private PhotoCommentViewAdapter adapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Position", " " + position);
    }

    public void updateData(){

        dataItems.add(new PhotoComments("user1","comment1"));
        dataItems.add(new PhotoComments("user2","comment2"));
        dataItems.add(new PhotoComments("user3","comment3"));
        dataItems.add(new PhotoComments("user4","comment4"));

        adapter = new PhotoCommentViewAdapter(this,dataItems);
        ListView listView = (ListView)findViewById(R.id.commentListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
}
