package com.example.demo1.util;

import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Reply;

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

    public static Discussion JSONParseDiscussion(JSONObject object) {
        try {
            String id = object.getString("id");
            String  posterId = object.getString("posterId");
            String posterName = object.getString("posterName");
            String courseId = object.getString("courseId");
            String postTime = object.getString("postTime");
            String title = object.getString("title");
            String content = object.getString("content");
            int replyCount = object.getInt("replyCount");
            Discussion discussion = new Discussion(id, posterId, posterName, courseId, postTime, title, content, replyCount);
            return discussion;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Reply JSONParseReply(JSONObject object) {
        try {
            String id = object.getString("id");
            String replyDiscussion = object.getString("replyDiscussion");
            String  posterId = object.getString("posterId");
            String posterName = object.getString("posterName");
            String time = object.getString("time");
            String content = object.getString("content");
            Reply reply = new Reply(id, replyDiscussion, posterId, posterName, time, content);
            return reply;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
