package com.trans.actional.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lenovo on 2019/11/23.
 */
@Entity
@Table(name = "t_account")
@Data
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;//编号
    @Column(name = "username")
    private String username;//用户名
    @Column(name = "balance")
    private float balance;//余额
}
