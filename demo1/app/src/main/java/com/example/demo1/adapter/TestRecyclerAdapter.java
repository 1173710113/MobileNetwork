package com.example.demo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.Test;
import com.example.demo1.util.TimeUtil;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Test test = mTestList.get(position);
        holder.titleText.setText(test.getName());
        holder.countText.setText(mMap.get(test).toString());
        String startDate = test.getStartTime();
        if(TimeUtil.isAfter(startDate)) {
            holder.signImage.setImageResource(R.color.primary_light);
            holder.timeText.setText(test.getStartTime());
        } else {
            String endDate = test.getEndTime();
            if(TimeUtil.isAfter(endDate)) {
                holder.signImage.setImageResource(R.color.primary_dark);
                holder.timeText.setText(test.getEndTime());
            } else {
                holder.signImage.setImageResource(R.color.secondary_text);
                holder.timeText.setText(test.getEndTime());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTestList.size();
    }
}
