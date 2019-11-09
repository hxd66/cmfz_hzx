package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_user")
public class User {
    @Id
    private String id;
    @Column(name = "head_img")
    private String headImg;
    private String dharma;
    private String username;
    private String sex;
    private String province;
    private String city;
    private String sign;
    private String phone;
    private String passowrd;
    private String salt;
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @Column(name = "last_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateDate;
    private String status;
    @Column(name = "guru_id")
    private String guruId;
    @Transient
    private Integer amount;

}
