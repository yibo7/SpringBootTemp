package com.ebsite.tempsite.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * Flog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "logs")
@Data
public class LogsPojo implements BaseModel<Integer> {
    private static final long serialVersionUID = -4272233926891165917L;

    @Id
    @Column(name = "id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "tinyint")
    private Integer logType;
    private Date addDate;
    @Column(columnDefinition = "char")
    private String ipAddr;
    @Column(columnDefinition = "bigint")
    private long addTimeint;
    private Integer userId;
    private String userName;
    @Override
    public Integer getDataId() {
        return id;
    }
}