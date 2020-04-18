package com.CS5520.sleepforest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.CS5520.sleepforest.Music;
import com.CS5520.sleepforest.R;

import java.util.ArrayList;

public class MusicAdapter extends ArrayAdapter<Music> {

    private Context context;
    private ArrayList<Music> list;

    public MusicAdapter(Context context, int resource, ArrayList<Music> music) {
        super(context, resource, music);
        this.context = context;
        this.list = music;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.music_list_item,parent,false);

        Music music = list.get(position);

        TextView musicName = (TextView)listItem.findViewById(R.id.musicName);
        musicName.setText(music.getName());
        TextView musicTotalTime = (TextView)listItem.findViewById(R.id.musicTotalTime);
        musicTotalTime.setText(music.getTime());

        return listItem;

    }
}
