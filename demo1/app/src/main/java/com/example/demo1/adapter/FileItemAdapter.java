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

import java.text.DecimalFormat;
import java.util.ArrayList;
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
                fileIcon.setImageResource(R.drawable.file_word);
                break;
            case "docx":
                fileIcon.setImageResource(R.drawable.file_word);
                break;
            case "ppt":
                fileIcon.setImageResource(R.drawable.file_ppt);
                break;
            case "pdf":
                fileIcon.setImageResource(R.drawable.file_pdf);
                break;
            case "zip":
                fileIcon.setImageResource(R.drawable.file_zip);
                break;
            default:
                fileIcon.setImageResource(R.drawable.file_empty);
                break;
        }
        fileName.setText(file.getFileName());
        fileUploadDate.setText(file.getPostTime());
        fileUploaderId .setText(file.getPosterId());
        fileUploaderName.setText(file.getPosterName());
        long fileSize = file.getFileSize();
        DecimalFormat df = new DecimalFormat("#.0");
        String size = null;
        if(fileSize >= 1024 && fileSize < 1048576) {
            size = df.format((fileSize/1024.0)) + "KB";
        } else if(fileSize >= 1048576) {
            size = df.format((fileSize/1024.0/1024.0)) + "MB";
        } else {
            size = Long.toString(fileSize) + "B";
        }
        fileLength.setText(size);
        return view;
    }
}
