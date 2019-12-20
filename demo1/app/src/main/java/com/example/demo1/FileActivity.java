package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Xfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo1.adapter.FileItemAdapter;
import com.example.demo1.domain.User;
import com.example.demo1.domain.XFile;
import com.example.demo1.util.DownloadUtil;
import com.example.demo1.util.FileUtil;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.PermissionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.demo1.util.FileUtil.getRealPathFromURI;

public class FileActivity extends AppCompatActivity {
    private String path,uploadfile;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final List<XFile> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new XFile("计算机导论1.ppt", "计算机导论1.ppt", new User("1173710113", null, null, "滕文杰", null, null), "11.20", "45.0kb"));
        }
        FileItemAdapter adapter = new FileItemAdapter(
                FileActivity.this, R.layout.file_item, data
        );
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XFile file = data.get(position);
                String url = "http://10.0.2.2:8081/mobile/file/download/?fileName=SWC开发文档模版.docx";
                //请求之前获得文件读写权限
                PermissionUtil.getReadWriteExternalPermission(FileActivity.this);
                DownloadUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath(), "SWC开发文档模版.docx", new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(File file) {
                        Log.d("File", "文件下载完成:" + file.getAbsolutePath());
                    }

                    @Override
                    public void onDownloading(int progress) {
                            Log.d("File", "文件正在下载：" + progress);
                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        Log.d("File", "文件下载失败");
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filemanage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                /*Intent intent = new Intent(FileActivity.this, UplodaFileActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("File", "返回的数据：" + data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            //使用第三方应用打开
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                path = uri.getPath();
                file = new File(path);
                uploadfile = file.getName();
                Log.w("File", "getName===" + uploadfile);
                Toast.makeText(this, path + "11111", Toast.LENGTH_SHORT).show();
                return;
            }
            //4.4以后
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                // 获取文件路径
                path = FileUtil.getPath(this, uri);
                Log.w("File", path);
                file = new File(path);
                // 获得文件名
                uploadfile = file.getName();
                Log.w("File", "getName===" + uploadfile);
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(this, uri);
                Log.w("File", path);
                Toast.makeText(FileActivity.this, path + "222222", Toast.LENGTH_SHORT).show();
            }
        }
        String url = "http://10.0.2.2:8081/mobile/file/upload";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", uploadfile, RequestBody.create(MediaType.parse("*/*"), file)) // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")和我们之前说的那个type其实是一样的
                .addFormDataPart("poster","1173710113")
                .build();
        HttpUtil.postFile(url, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

}
