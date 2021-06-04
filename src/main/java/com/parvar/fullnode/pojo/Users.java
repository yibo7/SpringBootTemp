package com.parvar.fullnode.pojo;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "Id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String userPass;
    private Date addTime;
    private int version;
    private int roleId;
    @Column(columnDefinition = "char")
    private String mdwu;
    private String realName;
    private String userEmail;
    private String mobileNumber;
    @Column(columnDefinition = "smallint")
    private Integer userStatus;
    @Transient
    private String role;

}