package com.example.demo1.util;

import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Homework;
import com.example.demo1.domain.Question;
import com.example.demo1.domain.Reply;
import com.example.demo1.domain.Score;
import com.example.demo1.domain.TeacherCourse;
import com.example.demo1.domain.Test;
import com.example.demo1.domain.XFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static Course JSONParseCourse(JSONObject object) {
        try {
            String id = object.getString("id");
            String name = object.getString("name");
            String teacherId = object.getString("teacherId");
            String teacherName = object.getString("teacherName");
            int maxVol = object.getInt("maxVol");
            String destination = object.getString("destination");
            String startTime = object.getString("startTime");
            String endTime = object.getString("endTime");
            int realVol = object.getInt("realVol");
            Course course = new Course(id, name, teacherId, teacherName, maxVol, destination, startTime, endTime, realVol);
            return course;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject CourseParseJSON(Course course) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", course.getId());
            object.put("name", course.getName());
            object.put("teacherId", course.getTeacherId());
            object.put("teacherName", course.getTeacherName());
            object.put("maxVol", course.getMaxVol());
            object.put("destination", course.getDestination());
            object.put("startTime", course.getStartTime());
            object.put("endTime",course.getEndTime());
            object.put("realVol", course.getRealVol());
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject replyParseJSON(Reply reply) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", reply.getId());
            object.put("replyDiscussion", reply.getReplyDiscussion());
            object.put("posterId", reply.getPosterId());
            object.put("posterName", reply.getPosterName());
            object.put("time", reply.getTime());
            object.put("content", reply.getContent());
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static XFile JSONParseXFile(JSONObject object) {
        try {
            String filePath = object.getString("filePath");
            String fileName = object.getString("fileName");
            long fileSize = object.getLong("fileSize");
            String posterId = object.getString("posterId");
            String posterName = object.getString("posterName");
            String courseId = object.getString("courseId");
            String postTime = object.getString("postTime");
            XFile file = new XFile(filePath, fileName, fileSize, posterId, posterName, courseId, postTime);
            return file;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Homework JSONParseHomework(JSONObject object) {
        try {
            String id = object.getString("id");
            String posterId = object.getString("posterId");
            String posterName = object.getString("posterName");
            String title = object.getString("title");
            String content = object.getString("content");
            String deadline = object.getString("deadline");
            String postTime = object.getString("postTime");
            String courseId = object.getString("courseId");
            Homework homework = new Homework(id, posterId, posterName, title, content, deadline, postTime, courseId);
            return homework;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject HomeworkParseJSON(Homework homework) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", homework.getId());
            object.put("posterId", homework.getPosterId());
            object.put("posterName", homework.getPosterName());
            object.put("title", homework.getTitle());
            object.put("content", homework.getContent());
            object.put("deadline", homework.getDeadline());
            object.put("postTime", homework.getPostTime());
            object.put("courseId", homework.getCourseId());
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TeacherCourse> JSONParseTeacherCourse(String data) {
        List<TeacherCourse> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String teacherId = object.getString("teacherId");
                String teacherName = object.getString("teacherName");
                int maxVol = object.getInt("maxVol");
                String destination = object.getString("destination");
                String startTime = object.getString("startTime");
                String endTime = object.getString("endTime");
                int realVol = object.getInt("realVol");
                String code = object.getString("code");
                TeacherCourse course = new TeacherCourse(id, name, teacherId, teacherName, maxVol, destination, startTime, endTime, realVol, code);
                list.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static JSONObject QuestionParseJSON(Question question){
        JSONObject object = new JSONObject();
        try {
            object.put("id", question.getId());
            object.put("content", question.getContent());
            object.put("answer", question.getAnswer());
            object.put("testId", question.getTestId());
            object.put("score", question.getScore());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static List<Test> JSONParseTest(String data) {
        List<Test> testList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("id");
                String name = object.getString("name");
                String startTime = object.getString("startTime");
                String endTime = object.getString("endTime");
                String course_id = object.getString("course_id");
                Test test = new Test(id, name, startTime,   endTime, course_id);
                testList.add(test);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testList;
    }

    public static List<Question> JSOONParseQuestion(String data) {
        List<Question> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                System.out.println(object.toString());
                String id = object.getString("id");
                String content = object.getString("content");
                String answer = object.getString("answer");
                String testId = object.getString("testId");
                String score = object.getString("score");
                Question question = new Question(id, content, answer, testId, score);
                list.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static JSONObject ScoreParseJSON(Score score) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", score.getId());
            object.put("testId", score.getTestId());
            object.put("studentId", score.getStudentId());
            object.put("score", score.getScore());
            object.put("everyScore", score.getEveryScore());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Score JSONParseScore(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String id = object.getString("id");
            String testId = object.getString("testId");
            String studentId = object.getString("studentId");
            String score = object.getString("score");
            String everyScore = object.getString("everyScore");
            return new Score(id, testId, studentId, score, everyScore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
