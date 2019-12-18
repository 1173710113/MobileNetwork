package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo1.R;
import com.example.demo1.domain.XFile;

import java.util.List;

public class FileItemAdapter extends ArrayAdapter<XFile> {
    private int resourceId;

    public FileItemAdapter(Context context, int textViewResourceId, List<XFile> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        XFile file = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView fileIcon = (ImageView)view.findViewById(R.id.file_icon);
        TextView fileName = (TextView)view.findViewById(R.id.file_name);
        TextView fileUploadDate = (TextView)view.findViewById(R.id.file_upload_date);
        TextView fileUploaderId = (TextView)view.findViewById(R.id.file_uploader_id);
        TextView fileUploaderName = (TextView)view.findViewById(R.id.file_uploader_name);
        TextView fileLength = (TextView)view.findViewById(R.id.file_length);
        String type = file.getFileName().split("\\.")[1];
        switch (type){
            case "doc":
                fileIcon.setImageResource(R.drawable.doc);
                break;
            case "ppt":
                fileIcon.setImageResource(R.drawable.ppt);
                break;
            case "pdf":
                fileIcon.setImageResource(R.drawable.pdf);
                break;
            default:
                fileIcon.setImageResource(R.drawable.blank);
                break;
        }
        fileName.setText(file.getFileName());
        fileUploadDate.setText(file.getPostTime());
        fileUploaderId .setText(file.getPoster().getId());
        fileUploaderName.setText(file.getPoster().getName());
        fileLength.setText(file.getLength());
        return view;
    }
}
