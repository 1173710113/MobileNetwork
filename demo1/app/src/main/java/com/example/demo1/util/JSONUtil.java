package com.example.demo1.util;

import com.example.demo1.domain.Discussion;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
    public static JSONObject DiscussionParseJSON(Discussion discussion) {
        JSONObject object = new JSONObject();
        try {
            object.put("id",discussion.getId());
            object.put("posterId", discussion.getPosterId());
            object.put("posterName", discussion.getPosterName());
            object.put("courseId", discussion.getCourseId());
            object.put("postTime", discussion.getPostTime());
            object.put("title", discussion.getTitle());
            object.put("content", discussion.getContent());
            object.put("replyCount", discussion.getReplyCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
