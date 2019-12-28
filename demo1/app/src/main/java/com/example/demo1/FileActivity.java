package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;

import com.example.demo1.adapter.FileRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.LocalFile;
import com.example.demo1.domain.XFile;
import com.example.demo1.listener.ProgressRequestListener;
import com.example.demo1.listener.UIProgressRequestListener;
import com.example.demo1.util.DownloadUtil;
import com.example.demo1.util.FileProgressUtil;
import com.example.demo1.util.FileUtil;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.PermissionUtil;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

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
    private RecyclerView recyclerView;
    private FileRecyclerAdapter adapter;
    private String TAG = "FileUpload";
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_1);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
        //MyNavView.initNavView(FileActivity.this, FileActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(FileActivity.this, FileActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
            @Override
            public void onNameChange(final String name) {
                View view = navView.getHeaderView(0);
                final TextView nameText = view.findViewById(R.id.main_nav_header_name);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nameText.setText(name);
                    }
                });
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }

        course = (Course) getIntent().getSerializableExtra("course");
        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FileRecyclerAdapter(fileList);
        recyclerView.setAdapter(adapter);
        adapter.setOnDownLoadListener(new FileRecyclerAdapter.IOnDownLoadListener() {
            @Override
            public void onDownload(final XFile targetFile, final int position) {
                String fileName = targetFile.getFileName();
                String courseId = course.getId();
                String url = "http://10.0.2.2:8081/mobile/file/download/" + fileName + "/" + courseId;
                //请求之前获得文件读写权限
                PermissionUtil.getReadWriteExternalPermission(FileActivity.this);
                DownloadUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath(), fileName, new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final File file) {
                        Log.d("File", "文件下载完成:" + file.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LocalFile localFile = new LocalFile(targetFile.getFileId(), file.getAbsolutePath(), targetFile.getFileName());
                                localFile.save();
                                ToastUtils.show("文件下载完成");
                                adapter.notifyItemChanged(position);
                            }
                        });
                    }

                    @Override
                    public void onDownloading(final int progress) {
                        Log.d("File", "文件正在下载：" + progress);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setChange(position, progress);
                            }
                        });

                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        Log.d("File", "文件下载失败");
                    }
                });
            }
        });
        queryFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_toolbar_add:
                PermissionUtil.getReadWriteExternalPermission(FileActivity.this);
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

        File file = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
         return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(FileActivity.this);
        progressDialog.setTitle("文件");
        progressDialog.setMessage("正在上传");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
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
                        //Toast.makeText(FileActivity.this, path + "上传成功", Toast.LENGTH_SHORT).show();
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
            final File finalFile = file;
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "error", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String data = response.body().string();
                    Log.e(TAG, data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LocalFile localFile = new LocalFile(data, path, finalFile.getName());;
                            localFile.save();
                            ToastUtils.show("文件上传完成");
                            adapter.notifyDataSetChanged();
                        }
                    });
                    queryFile();
                }
            });
        }

    }

    private void queryFile() {
        String url = "http://10.0.2.2:8081/mobile/file/query/" + course.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.show("请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "初始化文件列表");
                if (response.code() == 200) {
                    String data = response.body().string();
                    final List<XFile> list = JSONUtil.JSONParseFileList(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fileList.clear();
                            fileList.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    ToastUtils.show("请求失败");
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        queryFile();
        mDrawerLayout.closeDrawers();
    }

}
