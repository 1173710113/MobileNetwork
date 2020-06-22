package com.example.demo1.domain;

import com.example.demo1.util.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Discussion extends DataSupport implements Serializable {

    private String discussionId;
    private String posterId;
    private String posterName;
    private String courseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;
    private String title;
    private String content;
    private int replyCount;

}