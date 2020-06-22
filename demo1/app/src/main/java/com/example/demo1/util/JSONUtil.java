package com.example.demo1.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Homework;
import com.example.demo1.domain.Question;
import com.example.demo1.domain.Reply;
import com.example.demo1.domain.Score;
import com.example.demo1.domain.Test;
import com.example.demo1.domain.User;
import com.example.demo1.domain.XFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JSONUtil {

    public static JSONObject DiscussionParseJSON(Discussion discussion) {
        JSONObject object = JSON.parseObject(JSON.toJSONString(discussion));
        return object;
    }

    public static Discussion JSONParseDiscussion(JSONObject object) {
        Discussion discussion = JSON.parseObject(object.toJSONString(), Discussion.class);
        return discussion;
    }

    public static Reply JSONParseReply(JSONObject object) {
        Reply reply = JSON.parseObject(object.toJSONString(), Reply.class);
        return reply;
    }

    public static Course JSONParseCourse(JSONObject object) {
       Course course = JSON.parseObject(object.toJSONString(), Course.class);
        return course;
    }

    public static List<Course> JSONParseCourses(String data){
        List<Course> list = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(data);
        for(int i=0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            list.add(JSON.parseObject(object.toJSONString(), Course.class));
        }
        return list;
    }
    public static JSONObject CourseParseJSON(Course course) {
        JSONObject object = JSON.parseObject(JSON.toJSONString(course));
        return object;
    }

    public static JSONObject replyParseJSON(Reply reply) {
        JSONObject object = JSON.parseObject(JSON.toJSONString(reply));
        return object;
    }

    public static XFile JSONParseXFile(JSONObject object) {
        XFile file = JSON.parseObject(object.toJSONString(), XFile.class);
        return file;
    }

    public static Homework JSONParseHomework(JSONObject object) {
       Homework homework = JSON.parseObject(object.toJSONString(),Homework.class);
        return homework;
    }

    public static JSONObject HomeworkParseJSON(Homework homework) {
       JSONObject object = JSON.parseObject(JSON.toJSONString(homework));
        return object;
    }

    public static JSONObject QuestionParseJSON(Question question){
        JSONObject object = JSON.parseObject(JSON.toJSONString(question));
        return object;
    }

    public static List<Test> JSONParseTest(String data) {
        List<Test> testList = new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                testList.add(JSON.parseObject(object.toJSONString(), Test.class));
            }
        return testList;
    }

    public static List<Question> JSOONParseQuestion(String data) {
        List<Question> list = new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(data);
            for(int i=0; i < jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(JSON.parseObject(object.toJSONString(), Question.class));
            }
        return list;
    }

    public static JSONObject ScoreParseJSON(Score score) {
        JSONObject object = JSON.parseObject(JSON.toJSONString(score));
        return object;
    }

    public static Score JSONParseScore(String data) {
       Score score = JSON.parseObject(data, Score.class);
        return score;
    }

    public static JSONObject UserParseJSON(User user) {
       JSONObject object = JSON.parseObject(JSON.toJSONString(user));
        return object;
    }

    public static List<XFile> JSONParseFileList(String data) {
        List<XFile> list = new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (int i = 0; i <jsonArray.size(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                XFile file = JSONParseXFile(object);
                list.add(file);
            }
        return list;
    }
}
