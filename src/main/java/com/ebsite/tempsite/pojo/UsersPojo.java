package com.ebsite.tempsite.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "eb_users")
public class UsersPojo implements BaseModel<Long> {

    private static final long serialVersionUID = 8364043666681169043L;
    @Id
    @Column(name = "Id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String userPass;
    private Date addTime;
    private int version;
    private int roleId;
    @Column(columnDefinition = "char")
    private String mdwu;
    private Integer siteUserId;
    private String realName;
    private String googleKey;
    private String userEmail;
    private String mobileNumber;

    @Column(columnDefinition = "smallint")
    private Integer userStatus;

    @Transient
    private String role;
    @Override
    public Long getDataId() {
        return id;
    }
}