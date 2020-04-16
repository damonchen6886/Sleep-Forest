package com.CS5520.sleepforest.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.CS5520.sleepforest.R;
import com.CS5520.sleepforest.WhiteNoisePlayer;

public class SlideshowFragment extends Fragment {

    // private Context cont;
    private SlideshowViewModel slideshowViewModel;
    ListView musicList;
    final String[] listContent = {"waves 10 minutes", "waves 60 minutes",
            "clothes dryer 10 minutes", "clothes dryer 60 minutes",
            "heater 10 minutes", "heater 60 minutes"};
    final int[] ids = {R.raw.wn01_10min, R.raw.wn01_60min,
            R.raw.wn02_10min, R.raw.wn02_60min,
            R.raw.wn03_10min, R.raw.wn03_60min};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // cont = getActivity().getApplicationContext();
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // ListView and Adapter
        musicList = (ListView)root.findViewById(R.id.musicList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listContent);
        musicList.setAdapter(adapter);
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), WhiteNoisePlayer.class);
                myIntent.putExtra("soundFile", ids[position]);
                startActivity(myIntent);
            }
        });
        return root;
    }
}