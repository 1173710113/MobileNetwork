package com.example.demo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class FileListAdapter extends BaseAdapter {
    private List<DataHolder> list;
    private Context context;
    private LayoutInflater inflater;
    public FileListAdapter(Context context, List<DataHolder> list) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.uplode_file, null);
            holder.fileName = (TextView)convertView.findViewById(R.id.file_name);
            holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkbox_file);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.fileName.setText((String)list.get(position).fileName);
        holder.checkBox.setChecked(list.get(position).checked);
        return convertView;
    }
}
