package com.example.demo1.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.LocalFile;
import com.example.demo1.domain.XFile;
import com.example.demo1.util.MyFileProvider;
import com.example.demo1.util.OpenFileUtils;
import com.example.demo1.util.TimeUtil;
import com.hjq.toast.ToastUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.ViewHolder> {

    private List<XFile> mFileList;
    private IOnDownLoadListener onDownLoadListener;
    private HashMap<Integer, Integer> map = new HashMap<>();
    ArrayList<ViewHolder> mHolders = new ArrayList<>();
    int count = 0;

    public FileRecyclerAdapter(List<XFile> fileList) {
        mFileList = fileList;
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView fileIcon;
        TextView fileNameText, posterNameText, postDateText, fileSizeText;
        CircleImageView signImage;
        ProgressBar progressBar;
        boolean flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIcon = (ImageView)itemView.findViewById(R.id.file_icon);
            fileNameText = (TextView)itemView.findViewById(R.id.file_name);
            posterNameText = (TextView)itemView.findViewById(R.id.file_uploader_name);
            postDateText = (TextView)itemView.findViewById(R.id.file_upload_date);
            fileSizeText = (TextView)itemView.findViewById(R.id.file_length);
            signImage = (CircleImageView)itemView.findViewById(R.id.file_item_sign);
            progressBar = (ProgressBar)itemView.findViewById(R.id.file_progress);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        mHolders.add(holder);
        count++;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        map.put(position, mHolders.indexOf(holder));
        final XFile file = mFileList.get(position);
        holder.progressBar.setVisibility(View.GONE);
        holder.fileNameText.setText(file.getFileName());
        holder.postDateText.setText(TimeUtil.parseTime(file.getPostTime()));
        holder.posterNameText.setText(file.getPosterName());
        //大小设置
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
        holder.fileSizeText.setText(size);
        //图标设置
        String type = file.getFileName().split("\\.")[1];
        switch (type){
            case "doc":
                holder.fileIcon.setImageResource(R.drawable.file_word);
                break;
            case "docx":
                holder.fileIcon.setImageResource(R.drawable.file_word);
                break;
            case "ppt":
                holder.fileIcon.setImageResource(R.drawable.file_ppt);
                break;
            case "pdf":
                holder.fileIcon.setImageResource(R.drawable.file_pdf);
                break;
            case "zip":
                holder.fileIcon.setImageResource(R.drawable.file_zip);
                break;
            default:
                holder.fileIcon.setImageResource(R.drawable.file_empty);
                break;
        }

        final List<LocalFile> list = DataSupport.where("fileId = ?", file.getFileId()).find(LocalFile.class);
        if(list != null && list.size()>= 1) {
            holder.flag = true;
            holder.signImage.setVisibility(View.VISIBLE);
        } else {
            holder.flag = false;
            holder.signImage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不是本地文件
                if(!holder.flag) {
                    if(onDownLoadListener != null) {
                        onDownLoadListener.onDownload(file, position);
                    }
                } else {
                    LocalFile localFile = list.get(0);
                    File file1 = new File(localFile.getFilePath());
                    try{
                        System.out.println(localFile.getFilePath());
                        OpenFileUtils.openFile(holder.itemView.getContext(), file1);
                    return;}catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    public void setOnDownLoadListener(IOnDownLoadListener onDownLoadListener) {
        this.onDownLoadListener = onDownLoadListener;
    }

    public interface IOnDownLoadListener{
        void onDownload(XFile file, int position);
    }

    public void setChange(int position, int progress) {
        ViewHolder holder = mHolders.get(map.get(position));
        holder.progressBar.setVisibility(View.VISIBLE);
        Log.e("FileItem", Integer.toString(progress));
        if(progress < 100) {
            holder.progressBar.setProgress(progress);
        } else {
            try {
                Log.e("File", "set100");
                holder.progressBar.setProgress(progress);
                Thread.sleep(500);
                holder.progressBar.setVisibility(View.GONE);
                holder.signImage.setVisibility(View.VISIBLE);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
