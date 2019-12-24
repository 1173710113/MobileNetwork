package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo1.adapter.FileItemAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.XFile;
import com.example.demo1.listener.ProgressRequestListener;
import com.example.demo1.listener.UIProgressRequestListener;
import com.example.demo1.util.DownloadUtil;
import com.example.demo1.util.FileProgressUtil;
import com.example.demo1.util.FileUtil;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.PermissionUtil;
import com.example.demo1.util.UIUpdateUtilImp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.demo1.util.FileUtil.getRealPathFromURI;

public class FileActivity extends AppCompatActivity {
    private String path, uploadfile;
    private List<XFile> fileList = new ArrayList<>();
    private Course course;
    private String TAG = "FileUpload";
    private UIUpdateUtilImp uiUpdateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        course = (Course) getIntent().getSerializableExtra("course");
        uiUpdateList = new UIUpdateUtilImp() {
            @Override
            public void onUIUpdate() {
                showList();
            }
        };
        Log.e(TAG, "Create");
        initFileList();
    }

    private void showList() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        FileItemAdapter adapter = new FileItemAdapter(
                FileActivity.this, R.layout.file_item, fileList
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XFile file = fileList.get(position);
                String fileName = file.getFileName();
                String url = "http://10.0.2.2:8081/mobile/file/download/" + fileName + "/" + course.getId();
                //请求之前获得文件读写权限
                PermissionUtil.getReadWriteExternalPermission(FileActivity.this);
                DownloadUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath(), fileName, new DownloadUtil.OnDownloadListener() {
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
        final ProgressDialog progressDialog = new ProgressDialog(FileActivity.this);
        progressDialog.setTitle("文件");
        progressDialog.setMessage("正在上传");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        File file = null;
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
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(this, uri);
                Log.w("File", path);
            }

            final ProgressRequestListener progressListener = new ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    Log.e(TAG, "bytesWrite:" + bytesWritten);
                    Log.e(TAG, "contentLength:" + contentLength);
                    Log.e(TAG, (100 * bytesWritten) / contentLength + "% done");
                    Log.e(TAG, "done:" + done);
                    Log.e(TAG, "=============================================");
                }
            };

            final UIProgressRequestListener uiProgressRequestListener = new UIProgressRequestListener() {
                @Override
                public void onUIRequestProgress(long bytesWrite, long contentLength, boolean done) {
                    Log.e(TAG, "bytesWrite:" + bytesWrite);
                    Log.e(TAG, "contentLength:" + contentLength);
                    Log.e(TAG, (100 * bytesWrite) / contentLength + "% done");
                    Log.e(TAG, "done:" + done);
                    Log.e(TAG, "=============================================");
                    if (!done) {
                        int progress = (int) (100 * bytesWrite / contentLength);
                        progressDialog.setProgress(progress);
                    } else {
                        progressDialog.setProgress(100);
                        progressDialog.dismiss();
                        Toast.makeText(FileActivity.this, path + "上传成功", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", uploadfile, RequestBody.create(MediaType.parse("*/*"), file)) // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")和我们之前说的那个type其实是一样的
                    .addFormDataPart("posterId", getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", "ERROR"))
                    .addFormDataPart("courseId", course.getId())
                    .build();
            String url = "http://10.0.2.2:8081/mobile/file/upload";
            final Request request = new Request.Builder().url(url).post(FileProgressUtil.addProgressRequestBody(requestBody, uiProgressRequestListener)).build();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "error", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e(TAG, response.body().string());
                    initFileList();
                }
            });
        }

    }

    private void initFileList() {
        String url = "http://10.0.2.2:8081/mobile/file/query/" + course.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "初始化文件列表");
                fileList.clear();
                String data = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        XFile file = JSONUtil.JSONParseXFile(object);
                        fileList.add(file);
                    }
                    uiUpdateList.onUpdate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
