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
import com.CS5520.sleepforest.ShopListner;

import org.w3c.dom.Text;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private ShopListner shopListner;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_shop, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        Button shopTree1 = root.findViewById(R.id.tree1);
        Button shopTree2 = root.findViewById(R.id.tree2);
        Button shopTree3 = root.findViewById(R.id.tree3);
        Button shopTree4 = root.findViewById(R.id.tree4);
        Button shopTree5 = root.findViewById(R.id.tree5);
        final TextView coins = root.findViewById(R.id.currentCoin);
        coins.setText(getArguments().getInt("coins") + "");
        final int current = Integer.parseInt(coins.getText().toString());
        shopTree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < 600){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                shopListner.sendToHome(1);
                Toast.makeText(getContext(),"congratulation! the tree1 successfully Purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 600 + "");
            }
        });
        shopTree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < 800){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                shopListner.sendToHome(2);
                Toast.makeText(getContext(),"congratulations! the tree2 successfully Purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 800 + "");
            }
        });
        shopTree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < 1200){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                shopListner.sendToHome(3);
                Toast.makeText(getContext(),"congratulation! the tree3 successfully Purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 1200 + "");
            }
        });
        shopTree4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < 2000){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }

                shopListner.sendToHome(4);
                Toast.makeText(getContext(),"congratulation! the tree4 successfully Purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 2000 + "");
            }
        });

        shopTree5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current < 10000){
                    Toast.makeText(getContext(),":( Need more coins",Toast.LENGTH_SHORT).show();
                    return ;
                }
                popUpContack();
                Toast.makeText(getContext(),"congratulation! the tree5 successfully Purchased",Toast.LENGTH_SHORT).show();
                coins.setText(Integer.parseInt(coins.getText().toString()) - 10000 + "");
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