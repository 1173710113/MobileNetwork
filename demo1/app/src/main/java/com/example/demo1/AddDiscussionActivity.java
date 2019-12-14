package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo1.domain.Discussion;
import com.example.demo1.util.JSONUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddDiscussionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        Button addButton = (Button)this.findViewById(R.id.add_discussion_button);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        String title = ((TextView)findViewById(R.id.add_discussion_title)).getText().toString();
        String content = ((TextView)findViewById(R.id.add_discussion_content)).getText().toString();
        Discussion discussion = new Discussion(null, "1173710113", "滕文杰", "01", "2019-12-13 15:00:00", title, content, 0);
        JSONObject object = JSONUtil.DiscussionParseJSON(discussion);
        HttpUtil.sendHttpRequest("http://10.0.2.2:8081/mobile/discussion/add", object, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
