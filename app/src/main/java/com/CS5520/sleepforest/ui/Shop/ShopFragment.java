package com.CS5520.sleepforest.ui.Shop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.CS5520.sleepforest.R;
import com.CS5520.sleepforest.Shop;
import com.CS5520.sleepforest.ShopListner;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private ShopListner shopListner;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        Button shopTree1 = root.findViewById(R.id.tree1);
        Button shopTree2 = root.findViewById(R.id.tree2);
        Button shopTree3 = root.findViewById(R.id.tree3);
        Button shopTree4 = root.findViewById(R.id.tree4);
        shopTree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopListner.sendToHome(1);
                Toast.makeText(getContext(),"congratulation! the tree1 successfully Purchased",Toast.LENGTH_SHORT).show();
            }
        });
        shopTree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopListner.sendToHome(2);
                Toast.makeText(getContext(),"congratulations! the tree2 successfully Purchased",Toast.LENGTH_SHORT).show();
            }
        });
        shopTree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopListner.sendToHome(3);
                Toast.makeText(getContext(),"congratulation! the tree3 successfully Purchased",Toast.LENGTH_SHORT).show();
            }
        });
        shopTree4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopListner.sendToHome(4);
                Toast.makeText(getContext(),"congratulation! the tree4 successfully Purchased",Toast.LENGTH_SHORT).show();
            }
        });

        shopViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Button tree1 = root.findViewById
//    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        shopListner = (ShopListner) activity;




    }
}