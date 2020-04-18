package com.CS5520.sleepforest.ui.Shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.CS5520.sleepforest.R;
import com.CS5520.sleepforest.Shop;
import com.CS5520.sleepforest.ShopListner;

import java.util.List;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private ShopListner shopListner;


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        shopViewModel.insertCoin(new Shop(1, 0));
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_shop, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        final Button shopTree1 = root.findViewById(R.id.tree1);
        final Button shopTree2 = root.findViewById(R.id.tree2);
        final Button shopTree3 = root.findViewById(R.id.tree3);
        final Button shopTree4 = root.findViewById(R.id.tree4);
        final Button shopTree5 = root.findViewById(R.id.tree5);
        final TextView coins = root.findViewById(R.id.currentCoin);

       // coins.setText(getArguments().getInt("coins") + "");

//        shopViewModel.deleteCoin();
//        shopViewModel.insertCoin(new Shop(1, 8000));
        shopViewModel.updateCoin(getArguments().getInt("coins"));
        shopViewModel.getCoins().observe(getViewLifecycleOwner(),new Observer<List<Shop>>() {
            @Override
            public void onChanged(@Nullable List<Shop> s) {
                if (s.size() >0){


                   coins.setText(s.get(0).getTotalCoins() + "");
                    shopListner.sendToHomeCoins(s.get(0).getTotalCoins());

                }
            else{
                shopViewModel.insertCoin(new Shop(1, 0));
                }}});
       // final int current = shopViewModel.getCoins().getValue().get(0).getTotalCoins();
        shopTree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopViewModel.getCoins().getValue().get(0).getTotalCoins() < 600){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                shopListner.sendToHome(1);
                shopTree1.setClickable(false);
                Toast.makeText(getContext(),"Congratulation! An apple tree successfully purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 600 + "");
                shopViewModel.updateCoin(-600);
            }
        });
        shopTree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopViewModel.getCoins().getValue().get(0).getTotalCoins()< 800){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                shopListner.sendToHome(2);
                shopTree2.setClickable(false);
                Toast.makeText(getContext(),"Congratulations! A baobab tree successfully purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 800 + "");
                shopViewModel.updateCoin(-800);
            }
        });
        shopTree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopViewModel.getCoins().getValue().get(0).getTotalCoins() < 1200){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                shopListner.sendToHome(3);
                shopTree3.setClickable(false);
                Toast.makeText(getContext(),"Congratulation! A cactus successfully purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 1200 + "");
                shopViewModel.updateCoin(-1200);
            }
        });
        shopTree4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopViewModel.getCoins().getValue().get(0).getTotalCoins() < 2000){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
               }

                shopListner.sendToHome(4);
                shopTree4.setClickable(false);
                Toast.makeText(getContext(),"Congratulation! A sunflower successfully purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 2000 + "");
                shopViewModel.updateCoin(-2000);
            }
        });

        shopTree5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopViewModel.getCoins().getValue().get(0).getTotalCoins()< 10000){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                popUpContack();
            //  shopListner.sendToHome(5);
              Toast.makeText(getContext(),"Congratulation! A REAL Haloxylon ammodendron successfully purchased",Toast.LENGTH_SHORT).show();
               // coins.setText(Integer.parseInt(coins.getText().toString()) - 10000 + "");
                shopViewModel.updateCoin(-10000);
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

    public void popUpContack() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View contactView = inflater.inflate(R.layout.contact_dialog, null);
        builder.setView(contactView);

        final AlertDialog alertDialog = builder.create();

        alertDialog.show();
        Button okButton = contactView.findViewById(R.id.contact);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

    }


}