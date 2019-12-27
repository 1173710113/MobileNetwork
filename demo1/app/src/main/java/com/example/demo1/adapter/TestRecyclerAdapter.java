package com.example.demo1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.TestDetailActivity;
import com.example.demo1.domain.Score;
import com.example.demo1.domain.Test;
import com.example.demo1.util.TimeUtil;
import com.hjq.toast.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private Map<Test, Integer> mMap;
    private List<Test> mTestList;

    public TestRecyclerAdapter(Map<Test, Integer> map, List<Test> testList) {
        mMap = map;
        mTestList = testList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, countText, timeText;
        CircleImageView signImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView)itemView.findViewById(R.id.test_recycler_item_title);
            countText = (TextView)itemView.findViewById(R.id.test_recycler_item_count);
            timeText  = (TextView)itemView.findViewById(R.id.test_recycler_item_time);
            signImage = (CircleImageView)itemView.findViewById(R.id.test_recycler_item_sign);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Test test = mTestList.get(position);
        holder.titleText.setText(test.getName());
        final String type = holder.itemView.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("type", null);
        switch (type) {
            case "学生":
                holder.countText.setVisibility(View.GONE);
                String id = holder.itemView.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("id", null);
                if(id != null) {
                    List<Score> result = DataSupport.where("testId = ? and studentId = ?", test.getId(), id).find(Score.class);
                    if(result != null && result.size() != 0) {
                        holder.signImage.setVisibility(View.GONE);
                        holder.countText.setVisibility(View.VISIBLE);
                        holder.countText.setText(result.get(0).getScore());
                        holder.countText.setTextColor(Color.parseColor("#448AFF"));
                        holder.countText.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                    }
                }
                break;
            case "教师":
                holder.countText.setText(mMap.get(test).toString());
                break;
        }
        String startDate = test.getStartTime();
        final int state;
        if(TimeUtil.isAfter(startDate)) {
            //未开始
            holder.signImage.setImageResource(R.color.primary_light);
            holder.timeText.setText(test.getStartTime());
            state = 0;
        } else {
            String endDate = test.getEndTime();
            if(TimeUtil.isAfter(endDate)) {
                //正在进行
                holder.signImage.setImageResource(R.color.primary_dark);
                holder.timeText.setText(test.getEndTime());
                state = 1;
            } else {
                holder.signImage.setImageResource(R.color.secondary_text);
                holder.timeText.setText(test.getEndTime());
                state = 2;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "教师":
                        Intent intent = new Intent(holder.itemView.getContext(), TestDetailActivity.class);
                        intent.putExtra("test", test);
                        intent.putExtra("count", mMap.get(test).toString());
                        holder.itemView.getContext().startActivity(intent);
                        break;
                    case  "学生":
                        switch (state){
                            case 0:
                                ToastUtils.show("小测未开始");
                                break;
                            case 1:
                                Intent intent1 = new Intent(holder.itemView.getContext(), TestDetailActivity.class);
                                intent1.putExtra("test", test);
                                intent1.putExtra("count", mMap.get(test).toString());
                                holder.itemView.getContext().startActivity(intent1);
                                break;
                            case 2:
                                ToastUtils.show("小测已结束");
                                break;
                        }
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTestList.size();
    }

}
