package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cmfz_ar")
public class AR {
    @Id
    private String id;
    @Column(name = "admin_id")
    private String adminId;
    @Column(name = "role_id")
    private String roleId;
}
