package com.example.digitaldocs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.digitaldocs.R;
import com.example.digitaldocs.utilities.LineItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays the data collected from the receipt
 */

public class ImageTakenActivity extends AppCompatActivity {

    private TextView totalPriceText;
    private TextView abn;
    private TextView companyName;
    private ListView listView;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_image_taken);
        totalPriceText = findViewById(R.id.totalPrice);
        abn = findViewById(R.id.abn);
        companyName = findViewById(R.id.companyName);
        Intent intent1 = getIntent();
        String totalPrice = intent1.getExtras().getString("total");
        totalPriceText.setText("Total Price of all Items "+"$"+totalPrice);
        abn.setText("The ABN of this reciept is: " +" " +intent1.getExtras().getString("abn"));
        listView = findViewById(R.id.lineView);
        String comp = intent1.getExtras().getString("company");
        ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)  intent1.getExtras().get("list");
        System.out.println("The size of the list is" + list.size());

        if (intent1.getExtras().get("abn").equals("Algorithm could not detect a valid ABN")) {
            String reciept = "This reciept has no ABN";
            companyName.setText(reciept);
        } else {
            companyName.setText("Products have been purchased from: " + intent1.getExtras().getString("company"));
        }

        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i<list.get(0).size(); ++i)
        {
            arrayList.add(list.get(0).get(i) + " " + "Price " + "$"+ list.get(1).get(i));
        }
        items = new String[arrayList.size()];
        for(int i = 0; i<arrayList.size(); ++i)
        {
            items[i] = arrayList.get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item_layout,items);
        listView.setAdapter(adapter);



    }

}