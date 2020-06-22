package com.example.demo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Course extends DataSupport implements Serializable {
    private String courseId;
    private String name;
    private String teacherId;
    private String teacherName;
    private int maxVol;
    private String destination;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private int realVol;
    private String code;



}
