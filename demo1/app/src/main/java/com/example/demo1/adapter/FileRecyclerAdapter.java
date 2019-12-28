package com.example.demo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.LocalFile;
import com.example.demo1.domain.XFile;
import com.hjq.toast.ToastUtils;

import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.ViewHolder> {

    private List<XFile> mFileList;
    private IOnDownLoadListener onDownLoadListener;

    public FileRecyclerAdapter(List<XFile> fileList) {
        mFileList = fileList;
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView fileIcon;
        TextView fileNameText, posterNameText, postDateText, fileSizeText;
        CircleImageView signImage;
        boolean flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIcon = (ImageView)itemView.findViewById(R.id.file_icon);
            fileNameText = (TextView)itemView.findViewById(R.id.file_name);
            posterNameText = (TextView)itemView.findViewById(R.id.file_uploader_name);
            postDateText = (TextView)itemView.findViewById(R.id.file_upload_date);
            fileSizeText = (TextView)itemView.findViewById(R.id.file_length);
            signImage = (CircleImageView)itemView.findViewById(R.id.file_item_sign);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final XFile file = mFileList.get(position);
        holder.fileNameText.setText(file.getFileName());
        holder.postDateText.setText(file.getPostTime());
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

        List<LocalFile> list = DataSupport.where("fileId = ?", file.getFileId()).find(LocalFile.class);
        if(list != null && list.size()>= 1) {
            holder.flag = true;
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
                        onDownLoadListener.onDownload(file);
                    }
                } else {
                    ToastUtils.show("文件已在本地");
                    return;
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
        void onDownload(XFile file);
    }
}
