package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cmfz_admin")
public class Admin {
    @Id
    private String id;
    @Column(name = "admin_name")
    private String adminName;
    private String password;
    @Column(name = "admin_nickname")
    private String adminNickName;
    private String salt;
}
