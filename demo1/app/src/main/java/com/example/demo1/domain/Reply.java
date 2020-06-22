package com.example.demo1.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.litepal.crud.DataSupport;

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
public class Reply extends DataSupport {
    private String replyId;
    private String discussionId;
    private String posterId;
    private String posterName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
    private String content;
}
