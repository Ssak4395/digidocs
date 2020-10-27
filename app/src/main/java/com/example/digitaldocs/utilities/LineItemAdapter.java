package com.example.digitaldocs.utilities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.digitaldocs.model.ReceiptEntity;

import java.util.ArrayList;

public class LineItemAdapter extends ArrayAdapter<ReceiptEntity> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    public LineItemAdapter(@NonNull Context context, int resource, @NonNull ReceiptEntity[] objects) {
        super(context, resource, objects);
    }
}
