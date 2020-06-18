package com.trans.actional.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by lenovo on 2019/11/24.
 */
@Data
@ToString
public class Classes {
    private int id;
    private String name;
    private Teacher teacher;
    private List<Student> students;
}
