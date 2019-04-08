package com.example.tosik.live_information_android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int resourcedId;
    private ArrayList<ArrayList<String>> items;
    private ViewHolder holder;
    private ColorStack color;

    static class ViewHolder {
        TextView textView;
        TextView button;
    }

    CustomAdapter(Context context, int resourcedId, ArrayList items,ColorStack color) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourcedId = resourcedId;
        this.items = items;
        this.color = color;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(resourcedId, parent, false);

            holder = new ViewHolder();

            holder.textView = convertView.findViewById(R.id.music_text);
            holder.button = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(items.get(position).get(0));
        holder.button.setText(items.get(position).get(1));
        setColor();

        return convertView;
    }

    private void setColor(){
        holder.textView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        holder.button.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}