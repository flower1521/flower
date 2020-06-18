package com.trans.actional.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by lenovo on 2019/11/24.
 */
@Data
@ToString
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;
    private String realName;
}
