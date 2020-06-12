package com.example.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseAdapter extends android.widget.BaseAdapter {
    ArrayList<message>m ;
    Context context ;
    public BaseAdapter (Context context , ArrayList<message>m){
        this.context=context;
        this.m=m;
    }
    @Override
    public int getCount() {
        return m.size();
    }

    @Override
    public Object getItem(int position) {
        return m.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_message,null);
        TextView t = view.findViewById(R.id.single_message);
        TextView date = view.findViewById(R.id.time_text_view);
        t.setText(m.get(position).getRsala());
        date.setText(m.get(position).getDate());
        return view;
    }
}
