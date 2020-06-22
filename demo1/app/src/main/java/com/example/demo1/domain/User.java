package com.example.demo1.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class User implements Serializable {
    private String userId;
    private String password;
    private String type;
    private String name;
    private String sex;
    private String iconPath;
}
