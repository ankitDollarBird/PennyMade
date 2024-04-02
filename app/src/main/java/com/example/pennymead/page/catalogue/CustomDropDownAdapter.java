package com.example.pennymead.page.catalogue;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pennymead.R;

import java.util.List;

public class CustomDropDownAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mItems;
    int mSelectedPosition = -1;

    public CustomDropDownAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mItems = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.filters_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(mItems.get(position));

        if (position == mSelectedPosition) {
            textView.setBackgroundColor(convertView.getResources().getColor(R.color.primary_color));
            textView.setTextColor(convertView.getResources().getColor(R.color.text_white_color));
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT);
            textView.setTextColor(convertView.getResources().getColor(R.color.text_primary_black_color));
        }

        return convertView;
    }
    public void setSelectedPosition(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

}